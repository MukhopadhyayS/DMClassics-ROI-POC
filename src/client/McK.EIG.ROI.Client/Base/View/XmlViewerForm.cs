using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Configuration;
using System.IO;
using System.Xml;

using McK.EIG.ROI.Client.Request.View.BillingPayment;
using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.Common.Audit.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class XmlViewerForm : Form
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(AttachmentViewer));
        private ROIProgressBar fileTransferProgress;
        internal static EventHandler ProgressHandler;
        IPane tempPane;

        #endregion

        #region Constructor

        public XmlViewerForm()
        {
            InitializeComponent();
        }

        public XmlViewerForm(IPane pane, string letterType)
        {
            tempPane = pane;
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            ProgressHandler = new EventHandler(Process_NotifyProgress);
            InitializeComponent();        
        }

        #endregion

        #region Methods

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

        /// <summary>
        /// Method initilize the progress bar.
        /// </summary>
        private void InitProgress()
        {
            fileTransferProgress = new ROIProgressBar();
            fileTransferProgress.Location = new Point((((this.Width) / 2) - 50) - fileTransferProgress.Width / 2,
                                                     ((this.Height) / 2) - fileTransferProgress.Height / 2);
            this.Controls.Add(fileTransferProgress);
            fileTransferProgress.BringToFront();
            fileTransferProgress.MessageText.BringToFront();
            fileTransferProgress.PercentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
        }

        /// <summary>
        /// Retrieves the attachment content from the ROI Server
        /// Shows the attachment viewer.
        /// </summary>
        /// <param name="patient"></param>
        /// <param name="attachment"></param>
        /// <param name="attachmentID"></param>
        /// <param name="attachmentExt"></param>
        /// <returns></returns>
        public DialogResult DisplayAttachment(PatientDetails patient, AttachmentDetails attachment, string attachmentID, string attachmentExt)
        {
            log.EnterFunction();
            this.StartPosition = FormStartPosition.CenterParent;
            this.MinimizeBox = false;
            this.MaximizeBox = false;
            this.Show();
            InitProgress();
            string filePath = string.Empty;
            try
            {
                this.Cursor = Cursors.WaitCursor;
                log.Info("Fetching attachment content from server");
                // fetch the content from ROI Server
                AttachmentController attachmentController = new AttachmentController();
                filePath = attachmentController.DownloadAttachment(attachmentID, attachmentExt, ProgressHandler);

                if (!String.IsNullOrEmpty(filePath) && Validator.Validate(filePath, ROIConstants.FilepathValidation) && filePath.StartsWith(Environment.CurrentDirectory))
                {
                    log.Info("Attachment content fetched from server, displaying attachment");
                    using (StreamReader sr = new StreamReader(filePath))
                    {
                        txtXML.Text = sr.ReadToEnd();
                        txtXML.Text=FormatXml(txtXML.Text);   
                    }
                    txtXML.ReadOnly = true;
                    txtXML.SelectionStart= 0;
                    txtXML.SelectionLength = 0;
                    this.Cursor = Cursors.Arrow;
                    this.Visible = false;
                    DialogResult result = this.ShowDialog();
                    log.Info("Auditing view attachment event");

                    //Audit the attachment viewed in the ROI viewer
                    AuditEvent auditEvent = new AuditEvent();
                    auditEvent.ActionCode = ROIConstants.ViewActionCode;
                    auditEvent.UserId = UserData.Instance.UserInstanceId;
                    auditEvent.EventStart = System.DateTime.Now;
                    auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                    auditEvent.EventId = 1;
                    auditEvent.Facility = (patient.FacilityCode.Equals("")) ? "E_P_R_S" : patient.FacilityCode;
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
                    return DialogResult.Cancel;
                }
                else
                {
                    log.Error("***ERROR: Attachment: Unable to fetch content, closing the viewer ***");
                    throw new ROIException("ROI.13.3.17");                    
                }
            }
            catch (ROIException roiException)
            {
                if (roiException.Message.Equals(ROIErrorCodes.Unknown))
                {
                    ROIViewUtility.Handle(tempPane, new ROIException("ROI.13.3.17"));
                }
                else
                {
                    ROIViewUtility.Handle(tempPane, roiException);
                }
                log.FunctionFailure(roiException);
                this.Close();
                return DialogResult.Cancel;
            }
            catch (Exception cause)
            {

                ROIViewUtility.Handle(tempPane, new ROIException("ROI.13.3.17"));
                log.FunctionFailure(cause);
                this.Close();
                return DialogResult.Cancel;
            }
            finally
            {
                // delete the temp file
                if (!String.IsNullOrEmpty(filePath) && Validator.Validate(filePath, ROIConstants.FilepathValidation) && filePath.StartsWith(Environment.CurrentDirectory))
                {
                    try
                    {
                        // A.
                        // Try to delete the file.
                        log.Info("Deleting cached attachment content");
                        try
                        {
                            File.Delete(filePath);
                        }
                        catch (Exception excpt)
                        {
                            log.Error("Unable to delete cached file " + filePath + " Error : " + excpt.Message);
                        }
                        log.Info("Deleted cached attachment content successfully");
                    }
                    catch (IOException ioexcp)
                    {
                        // B.
                        // We couldn't delete the file.
                        log.Error("Could not delete cached file" + filePath + ". Error" + ioexcp.Message);
                    }

                }
                log.ExitFunction();
            }
            return DialogResult.Cancel;
        }

        /// <summary>
        /// Converts unformatted xml content string to formatted xml. 
        /// </summary>
        /// <param name="sUnformattedXml"></param>
        /// <returns></returns>
        private string FormatXml(string sUnformattedXml)
        {

            //load unformatted xml into a dom
            XmlDocument xd = new XmlDocument();
            xd.XmlResolver = null;
            xd.LoadXml(sUnformattedXml);

            //stringbuilder which will hold the formatted xml
            StringBuilder sb = new StringBuilder();

            //pumps the formatted xml into the StringBuilder created above
            StringWriter sw = new StringWriter(sb);

            //does the formatting
            XmlTextWriter xtw = null;

            try
            {
                //point the xtw at the StringWriter
                xtw = new XmlTextWriter(sw);

                //we want the output formatted
                xtw.Formatting = Formatting.Indented;

                //get the dom to dump its contents into the xtw 
                xd.WriteTo(xtw);
            }
            finally
            {
                //clean up even if error
                if (xtw != null)
                    xtw.Close();
            }

            //return the formatted xml
            return sb.ToString();
        }

        #endregion

    }
}