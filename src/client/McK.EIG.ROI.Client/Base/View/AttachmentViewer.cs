/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/


using System;
using System.Collections.Generic;
using System.Configuration;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using System.Resources;
using System.Text;
using System.Windows.Forms;
using McK.EIG.ROI.Client.Request.Controller;
using System.IO;
using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.Common.FileTransfer.Controller.Upload;
using System.Drawing;
using System.Configuration;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class AttachmentViewer : ROIViewer 
    {
        private Log log = LogFactory.GetLogger(typeof(AttachmentViewer));
        private ROIProgressBar fileTransferProgress;
        internal static EventHandler ProgressHandler;

        #region Constructor

        public AttachmentViewer(IPane pane, string letterType,string dialogName)
            : base(pane, letterType, dialogName)
        {
            // reset the logfactory, so that it will continue to log to ROI application log
            // CR fix for 347793
			LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            DisplayContinueButton = false;
            DisplayCancelButton = false;
            InitProgress();
            ProgressHandler = new EventHandler(Process_NotifyProgress);
        }

        #endregion

        #region methods
        /// Retrieves the attachment content from the ROI Server 
        /// Shows the attachment viewer.
        /// </summary>
        /// <param name="fileName"></param>
        /// <returns></returns>
        public DialogResult DisplayAttachment(PatientDetails patient, AttachmentDetails attachment, string attachmentId, string attachmentExt)
        {
            log.EnterFunction();

            this.ReleaseDialog  = true;

            Form dialog = ROIViewUtility.ConvertToForm(null, "AttachmentViewer", this);

            string filePath = string.Empty;
            try
            {
                this.DisplayContinueButton = false;
                this.DisplayCancelButton = false;
                dialog.UseWaitCursor = true;
                dialog.Show();

                log.Info("Fetching attachment content from server");
                // fetch the content from ROI Server
                AttachmentController attachmentController = new AttachmentController ();
                filePath = attachmentController.DownloadAttachment(attachmentId, attachmentExt, ProgressHandler);

                if (!String.IsNullOrEmpty(filePath) && Validator.Validate(filePath, ROIConstants.FilepathValidation))
                {
                    log.Info("Attachment content fetched from server, displaying attachment");
                    this.PDFDocumentViewer.SerialNumber = "PDFVW4WIN-ENMBG-1CA2A-9Z3DV-RVH0Y-24K1M";
                    //CR#391890
                    if (Path.GetExtension(filePath) == ".pdf")
                    {
                        this.PDFPageViewer.Visible = true;
                        this.DocumentViewer.Visible = false;
                       
                        this.PDFDocumentViewer.Load(filePath);

                    }
                    else
                    {
                        this.PDFPageViewer.Visible = false;
                        this.DocumentViewer.Visible = true;
                        this.DocumentViewer.Url = new Uri(filePath);
                    }
                    
                    dialog.UseWaitCursor = false;

                    dialog.Visible = false;

                    DialogResult result = dialog.ShowDialog();
                    dialog.Close();

                    log.Info("Auditing view attachment event");

                    //Audit the attachment viewed in the ROI viewer
                    AuditEvent auditEvent = new AuditEvent();

                    auditEvent.ActionCode = ROIConstants.ViewActionCode;
                    auditEvent.UserId = UserData.Instance.UserInstanceId;
                    auditEvent.EventStart = System.DateTime.Now;
                    auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                    auditEvent.EventId = 1;
                    auditEvent.Facility = (string.IsNullOrEmpty(patient.FacilityCode)) ? "E_P_R_S" : patient.FacilityCode;
                    auditEvent.Mrn = patient.MRN;
                    auditEvent.Encounter = attachment.Encounter;

                    auditEvent.Comment = attachment.Name + ", subtitle: " + attachment.Subtitle + ", attachment - " + attachment.FileDetails.FileName + "." + attachment.FileDetails.Extension + " accessed by a 3rd party application.";

                    try
                    {
                        Application.DoEvents();
                        ROIController.Instance.CreateAuditEntry(auditEvent);
                    }
                    catch (ROIException cause)
                    {
                        log.FunctionFailure(cause);
                    }
                    return result;
                }
                else
                {
                    throw new ROIException("ROI.13.3.17");
                    log.Error("***ERROR: Attachment: Unable to fetch content, closing the viewer ***");
                    dialog.Close();
                    return DialogResult.Cancel;
                }
            }
            catch (ROIException roiException)
            {
                if (roiException.Message.Equals(ROIErrorCodes.Unknown))
                {
                    ROIViewUtility.Handle(Context, new ROIException("ROI.13.3.17"));
                }
                else
                {
                    ROIViewUtility.Handle(Context, roiException);
                }
                log.FunctionFailure(roiException);
                dialog.Close();
                return DialogResult.Cancel;
            }
            catch (Exception cause)
            {
                
                ROIViewUtility.Handle(Context, new ROIException("ROI.13.3.17"));
                log.FunctionFailure(cause);
                dialog.Close();
                return DialogResult.Cancel;
            }
            finally
            {
                // delete the temp file
                if (!String.IsNullOrEmpty(filePath))
                {
                    try
                    {
                        // A.
                        // Try to delete the file.
                        log.Info("Deleting cached attachment content");
                        try
                        {
                            System.GC.Collect();
                            System.GC.WaitForPendingFinalizers();
                            File.Delete(filePath);
                        }
                        catch (Exception excpt)
                        {
                            log.Error("Unable to delete cached file " + filePath + " Error : " + excpt.Message );
                        }
                        log.Info("Deleted cached attachment content successfully");
                    }
                    catch (IOException ioexcp)
                    {
                        // B.
                        // We couldn't delete the file.
                        log.Error ("Could not delete cached file" + filePath + ". Error" + ioexcp.Message );
                    }
                    
                }
                log.ExitFunction();
            }
            return DialogResult.Cancel;
        }

        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            this.ShowProgress((FileTransferEventArgs)e);
        }

        /// <summary>
        /// Method initilize the progress bar.
        /// </summary>
        private void InitProgress()
        {
            fileTransferProgress = new ROIProgressBar();
            fileTransferProgress.Location = new Point(((this.Width / 2) - 50) - fileTransferProgress.Width / 2,
                                                     (this.Height / 2) - fileTransferProgress.Height / 2);
            this.Controls.Add(fileTransferProgress);
            fileTransferProgress.BringToFront();
            fileTransferProgress.MessageText.BringToFront();
            fileTransferProgress.PercentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
        }

        /// <summary>
        /// Method call the progress bar to display.
        /// </summary>
        /// <param name="e"></param>
        public void ShowProgress(FileTransferEventArgs e)
        {
            switch (e.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    fileTransferProgress.Visible = true;
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.InProgress:
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.Finish:
                    fileTransferProgress.ShowProgress(e);
                    fileTransferProgress.Visible = false;
                    break;
            }
        }

        #endregion

    }
}
