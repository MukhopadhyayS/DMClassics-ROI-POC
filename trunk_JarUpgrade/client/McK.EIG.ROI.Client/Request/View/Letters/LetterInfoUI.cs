#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
#endregion
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Net;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Audit.Controller;
using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Viewer.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.BillingPayment;

namespace McK.EIG.ROI.Client.Request.View.Letters
{
    public partial class LetterInfoUI : ROIBaseUI, IFooterProvider
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(LetterInfoUI));

        private EventHandler defaultDataChangedHandler;
        private LetterInfoActionUI letterInfoActionUI;
        private List<LetterTemplateDetails> otherLetterTemplates;
        private List<LetterTemplateDetails> coverLetterTemplates;

        private LetterTemplateDetails forSelect;                
        private RequestDetails request;
        private ReleaseDetails release;
        private ReleaseDetails auditReleaseInfo;

        private int maxLength;
        private long defaultCoverLetterId;        

        private ROIProgressBar fileTransferProgress;
        internal static EventHandler ProgressHandler;

        #endregion

        #region Constructor

        public LetterInfoUI()
        {
            InitializeComponent();
            CreateActionUI();
            defaultDataChangedHandler += new EventHandler(defaultDataChanged);
            EnableEvents();          
        }
       
        #endregion

        #region Methods

        /// <summary>
        /// Create a footer UI.
        /// </summary>
        private void CreateActionUI()
        {
            letterInfoActionUI = new LetterInfoActionUI();

            letterInfoActionUI.CreateLetterButton.Click += new EventHandler(CreateLetterButton_Click);
            letterInfoActionUI.CancelButton.Click       += new EventHandler(CancelButton_Click);
        }

        /// <summary>
        /// Enables the events.
        /// </summary>
        private void EnableEvents()
        {
            otherRadioButton.CheckedChanged       += new EventHandler(LetterTypeChanged);
            templateComboBox.SelectedIndexChanged += new EventHandler(templateComboBox_SelectedIndexChanged);
            createFreeformButton.Click            += new EventHandler(createFreeformButton_Click);           

            otherRadioButton.CheckedChanged         += defaultDataChangedHandler;
            templateComboBox.SelectedIndexChanged   += defaultDataChangedHandler;
            createFreeformButton.Click              += defaultDataChangedHandler;
        }

        /// <summary>
        /// Occurs when user changes on LetterInfo UI.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void defaultDataChanged(object sender, EventArgs e)
        {
            letterInfoActionUI.CancelButton.Enabled = true;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            SetLabel(rm, letterTypeLabel);
            SetLabel(rm, letterTemplateLabel);
            SetLabel(rm, notesHeaderLabel);
            SetLabel(rm, createFreeformButton);
            SetLabel(rm, otherRadioButton);
            SetLabel(rm, coverLetterRadioButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, createFreeformButton);

            letterInfoActionUI.Localize();

            ProgressHandler = new EventHandler(Process_NotifyProgress);
            InitProgress();
        }

        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            ShowProgress((FileTransferEventArgs)e);
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
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            fileTransferProgress = new ROIProgressBar();
            fileTransferProgress.Location = new Point(((outerPanel.Width / 2) - 50) - fileTransferProgress.Width / 2,
                                                     (outerPanel.Height / 2) - fileTransferProgress.Height / 2);
            outerPanel.Controls.Add(fileTransferProgress);
            fileTransferProgress.BringToFront();
            fileTransferProgress.MessageText.Text = rm.GetString("ProgressMessage");
            fileTransferProgress.MessageText.BringToFront();
            fileTransferProgress.PercentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
        }

        /// <summary>
        /// Gets localized key of UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets localized key of UI controls tooltip.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// It sets the pane which holds the Request information UI
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            letterInfoActionUI.SetPane(pane);
            letterInfoActionUI.SetExecutionContext(Context);
        }      
      
        /// <summary>
        /// Occurs when the template has changed by user.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void templateComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (templateComboBox.SelectedIndex > 0)
            {
                EnableButtons(true);
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)templateComboBox.SelectedItem;
                if (letterTemplate.HasNotes)
                {   
                    notesGroupPanel.Visible = true;
                }
                else
                {
                    notesGroupPanel.Visible = false;
                }
            }
            else
            {
                notesGroupPanel.Visible = false;
                EnableButtons(false);
            }
        }

        /// <summary>
        /// Enables the buttons
        /// </summary>
        /// <param name="enable"></param>
        public void EnableButtons(bool enable)
        {
            letterInfoActionUI.CreateLetterButton.Enabled = enable;
            letterInfoActionUI.CancelButton.Enabled       = enable;
        }

        /// <summary>
        /// Occurs when letter type is changed by the user.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void LetterTypeChanged(object sender, EventArgs e)
        {  
            if (coverLetterRadioButton.Checked)
            {   
                PopulateTemplates(coverLetterTemplates, LetterType.CoverLetter.ToString(), defaultCoverLetterId);
            }
            else
            {
                PopulateTemplates(otherLetterTemplates, LetterType.Other.ToString(), 0);
            }
        }

        /// <summary>
        /// Populate templates.
        /// </summary>
        /// <param name="letterTemplates"></param>
        /// <param name="letterType"></param>
        /// <param name="defaultId"></param>
        private void PopulateTemplates(List<LetterTemplateDetails> letterTemplates, string letterType, long defaultId)
        {
            if (letterTemplates.Count == 1)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                string titleText = rm.GetString("noTemplate.title");
                string okButtonText = rm.GetString("okButton.DialogUI");
                string messageText = rm.GetString(letterType + ".MessageText");
                string okButtonToolTip = "";
                ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
            }

            templateComboBox.DisplayMember = "Name";
            templateComboBox.ValueMember = "DocumentId";
            templateComboBox.DataSource = letterTemplates;
            templateComboBox.SelectedValue = defaultId;               
            
        }       

        /// <summary>
        /// Occurs when user clicks on CreateLetter button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CreateLetterButton_Click(object sender, EventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            List<string> notes = new List<string>();
            Collection<NotesDetails> notesList = new Collection<NotesDetails>();
            Collection<string> freeformNotesList = new Collection<string>();       
            string letterType = string.Empty;
            bool noError = true;
            NotesDetails note;

            errorProvider.Clear();
            notesList.Clear();
            freeformNotesList.Clear();

            LetterTemplateDetails letterTemplate = (LetterTemplateDetails)templateComboBox.SelectedItem;

            if(otherRadioButton.Checked)
            {
                letterType = LetterType.Other.ToString();
            }
            else
            {
                letterType = LetterType.CoverLetter.ToString();
            }

            if (notesPanel.Visible)
            {
                foreach (FreeformUI freeformUI in notesPanel.Controls)
                {
                    if (freeformUI.Checked)
                    {
                        if (!freeformUI.IsFreeform)
                        {
                            note = new NotesDetails();
                            note.Id = freeformUI.NoteId;                            
                            note.DisplayText = freeformUI.FreeformCheckBox.Text.Replace("&&", "&");
                            notes.Add(note.DisplayText);                            
                        }
                        else
                        {
                            if (!Validator.Validate(freeformUI.FreeformTextBox.Text, ROIConstants.AllCharacters))
                            {
                                errorProvider.SetError(freeformUI.FreeformTextBox, rm.GetString(ROIErrorCodes.InvalidName));
                                noError = false;
                            }
                            else
                            {
                                notes.Add(freeformUI.FreeformTextBox.Text);
                            }
                        }
                    }
                }
            }

            if (noError)
            {
                try
                {
                    //PreBillInvoiceDetails preBillInvoiceDetails = CreateLetterDetails(letterTemplate.DocumentId, letterTemplate.Name, notes, letterType);
                    //DocumentInfo documentInfo = BillingController.Instance.CreateLetter(preBillInvoiceDetails);
                    DocumentInfo documentInfo = BillingController.Instance.CreateLetter(letterTemplate.DocumentId, request.Id, notes.ToArray());
                    ShowViewer(letterType, documentInfo, letterTemplate.Name, notes);
                }
                catch (ROIException cause)
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                finally
                {
                    OutputFileDialog.CloseSplashScreen();
                    OutputPrintDialog.CloseSplashScreen();
                    OutputFaxDialog.CloseSplashScreen();
                    OutputEmailDialog.CloseSplashScreen();
                    OutputDiscDialog.CloseSplashScreen();
                    
                }
            }
        }

        /// <summary>
        /// Shows the viewer.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        /// <param name="invoiceTypeId"></param>
        public void ShowViewer(string letterTemplateType, DocumentInfo documentInfo, string letterName, List<string> notes)
        {
            long invoiceId = 0;
            long letterId;
            bool forInvoice = false;
            bool forLetter;
            string queueSecretWord = string.Empty;
            string outputMethod = string.Empty;

            ROIViewer viewer = new ROIViewer(Pane, letterTemplateType, GetType().Name);
            viewer.RequestorFax = request.RequestorFax;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog;

            if (letterTemplateType == LetterType.Invoice.ToString())
            {
                dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + letterTemplateType + ".preview"), viewer);
            }
            else
            {
                dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + letterTemplateType + ".preview"), viewer);
            }
            string filePath = string.Empty;
            try
            {
                filePath = BillingController.DownloadLetterTemplate(documentInfo.Name, ProgressHandler);

                TimeSpan timeSpan = new TimeSpan(0, 0, 1);
                viewer.PDFDocumentViewer.SerialNumber = "PDFVW4WIN-ENMBG-1CA2A-9Z3DV-RVH0Y-24K1M";
                if (ROIViewUtility.WaitForFileInUse(filePath, timeSpan))
                {
                    //CR#391890
                    if (Path.GetExtension(filePath) == ".pdf")
                    {
                        viewer.PDFPageViewer.Visible = true;
                        viewer.DocumentViewer.Visible = false;
                        viewer.PDFDocumentViewer.Load(filePath);
                    }
                    else
                    {
                        viewer.PDFPageViewer.Visible = false;
                        viewer.DocumentViewer.Visible = true;
                        viewer.DocumentViewer.Url = new Uri(filePath);
                    }                  
                }

                viewer.ReleaseDialog = false;

                DialogResult result = dialog.ShowDialog(this);

                dialog.Close();

                if (result == DialogResult.OK)
                {
                    RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
                    OutputRequestDetails outputRequestDetails = new OutputRequestDetails(rsp.InfoEditor.Request.Id,
                                                                                         Convert.ToInt64(DateTime.Now.ToString("ddMMyyyyhhmmssms", System.Threading.Thread.CurrentThread.CurrentUICulture), System.Threading.Thread.CurrentThread.CurrentUICulture),
                                                                                         string.Empty,
                                                                                         rsp.InfoEditor.Request.ReceiptDate);
                    outputRequestDetails.OutputDestinationDetails = viewer.OutputPropertyDetails.OutputDestinationDetails[0];
                    letterId = documentInfo.Id;
                    forLetter = true;
                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.File.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        queueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                        outputMethod = OutputMethod.SaveAsFile.ToString();
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        outputMethod = OutputMethod.Fax.ToString();
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Print.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        outputMethod = OutputMethod.Print.ToString();
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Email.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        queueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                        outputMethod = OutputMethod.Email.ToString();
                    }

                    Application.DoEvents();
                    BillingController.Instance.updateInvoiceOutputProperties(0, letterId, 0, false, true, false, queueSecretWord, outputMethod); 
                    
                    outputRequestDetails.RequestParts.Add(BuildROIRequestPartDetails(documentInfo));
                    Application.DoEvents();
                    long jobStatus = OutputController.Instance.SubmitOutputRequest(outputRequestDetails, viewer.DestinationType,
                                                                  viewer.OutputPropertyDetails.OutputViewDetails,
                                                                  false);

                    AuditEvent auditEvent = new AuditEvent();

                    auditEvent.UserId = UserData.Instance.UserInstanceId;
                    auditEvent.EventStart = System.DateTime.Now;
                    auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                    auditEvent.EventId = 1;
                    auditEvent.ActionCode = BillingPaymentInfoUI.RetrieveActionCode(outputRequestDetails.OutputDestinationDetails.Type);

                    StringBuilder auditmessage = new StringBuilder();

                    string subtitle = string.Empty;
                    string version = string.Empty;
                    // CR# 356,927 - Audit Issue Fix.
                    if (release.ReleasedPatients.Count == 0)
                    {
                        ReleasedPatientDetails releasePatient;
                        Application.DoEvents();
                        RequestPatients requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);
                        foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                        {

                            releasePatient = new ReleasedPatientDetails();
                            release.ReleasedPatients.Add(requestPatientDetails.Key, releasePatient.AssignRequestToReleasedPatient(requestPatientDetails));
                        }
                    }

                    foreach (ReleasedPatientDetails releasedPatientDetails in release.ReleasedPatients.Values)
                    {
                        //Global documents
                        foreach (RequestDocumentDetails doc in releasedPatientDetails.GlobalDocument.GetChildren)
                        {
                            subtitle = (string.IsNullOrEmpty(doc.Subtitle)) ? string.Empty : doc.Subtitle + "-";
                            foreach (RequestVersionDetails versionDetails in doc.GetChildren)
                            {
                                version = (string.IsNullOrEmpty(versionDetails.Name)) ? string.Empty : versionDetails.Name + "-";
                                foreach (RequestPageDetails page in versionDetails.GetChildren)
                                {
                                    auditmessage.Append(doc.DocType + "-" + subtitle + "-" + version + "-" + page.PageNumber + ",");
                                }
                            }
                        }

                        //HPF documents
                        foreach (RequestEncounterDetails enc in releasedPatientDetails.GetChildren)
                        {
                            foreach (RequestDocumentDetails doc in enc.GetChildren)
                            {
                                subtitle = (string.IsNullOrEmpty(doc.Subtitle)) ? string.Empty : doc.Subtitle + "-";
                                foreach (RequestVersionDetails versionDetails in doc.GetChildren)
                                {
                                    version = (string.IsNullOrEmpty(versionDetails.Name)) ? string.Empty : versionDetails.Name + "-";
                                    foreach (RequestPageDetails page in versionDetails.GetChildren)
                                    {
                                        auditmessage.Append(doc.DocType + "-" + subtitle + "-" + version + "-" + page.PageNumber + ",");
                                    }
                                }
                            }
                        }

                        //CR# 378,436 - Fix
                        //NonHpf documents
                        foreach (RequestNonHpfDocumentDetails doc in releasedPatientDetails.NonHpfDocument.GetChildren)
                        {
                            subtitle = (string.IsNullOrEmpty(doc.Subtitle)) ? string.Empty : doc.Subtitle + "-";
                            auditmessage.Append(doc.DocType + "-" + subtitle + doc.PageCount + ",");
                        }

                        //CR# 378,436 - Fix
                        //attachments
                        foreach (RequestAttachmentDetails attachment in releasedPatientDetails.Attachment.GetChildren)
                        {
                            subtitle = (string.IsNullOrEmpty(attachment.Subtitle)) ? string.Empty : attachment.Subtitle + "-";
                            auditmessage.Append(attachment.AttachmentType + "-" + subtitle + attachment.PageCount + ",");
                        }

                    }

                    string message = string.Empty;
                    if (!string.IsNullOrEmpty(auditmessage.ToString()))
                    {
                        message = auditmessage.ToString().TrimEnd(',');
                    }

                    if (!string.IsNullOrEmpty(message))
                    {
                        auditEvent.Comment = message;
                    }
                    else
                    {
                        auditEvent.Comment = ((LetterTemplateDetails)templateComboBox.SelectedItem).Name + ROIConstants.LetterGenerated + " " + request.Id;
                    }

                    try
                    {
                        Application.DoEvents();
                        ROIController.Instance.CreateAuditEntry(auditEvent);
                    }
                    catch (ROIException cause)
                    {
                        log.FunctionFailure(cause);
                    }
                    try
                    {
                        CreateEvent(letterName, notes);
                    }
                    catch (ROIException cause)
                    {
                        ROIViewUtility.Handle(Context, cause);
                    }
                    if (jobStatus == -200)
                    {
                        rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                        string titleText = rm.GetString("letterUnsuccessfulDialog.title");
                        string okButtonText = rm.GetString("okButton.DialogUI");
                        string messageText = rm.GetString("letterUnsuccessfulDialog.MessageText");
                        string okButtonToolTip = "";
                        ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                    }
                }
                else
                {
                    BillingController.Instance.DeleteLetter(documentInfo.Id, request.Id);
                }
            }
            catch (IOException cause)
            {
                log.FunctionFailure(cause);
                ROIException fileAlreadyOpen = new ROIException(ROIErrorCodes.FileAlreadyOpen);
                ROIViewUtility.Handle(Context, fileAlreadyOpen);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            } 
        }        

        private void CreateEvent(string letterName, List<string> letterNotes)
        {
            CommentDetails letterDetails = new CommentDetails();

                letterDetails.RequestId = request.Id;
                letterDetails.EventType = EventType.LetterSend;

                StringBuilder coverLetterNotes = new StringBuilder();
                foreach (string note in letterNotes)
                {
                    coverLetterNotes.Append(note + "~");
                }

                if (!string.IsNullOrEmpty(coverLetterNotes.ToString()))
                {
                    letterDetails.EventRemarks = letterName + ": " +
                                           coverLetterNotes.ToString().TrimEnd('~');
                }
                else
                {
                    letterDetails.EventRemarks = letterName;
                }
                RequestController.Instance.CreateComment(letterDetails);
        }

        /// <summary>
        /// Build ROI request part details with CoverLetter or Invoice
        /// </summary>
        /// <param name="fileName"></param>
        private static RequestPartDetails BuildROIRequestPartDetails(DocumentInfo documentInfo)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();

            requestPartDetails.ContentId = "";
            requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.FileIds   = documentInfo.Type + "." + documentInfo.Id;

            requestPartDetails.PropertyLists.Add(propertyDetails);

            return requestPartDetails;
        }

        /// <summary>
        /// Occurs when user clicks on Cancel button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CancelButton_Click(object sender, EventArgs e)
        {
            notesPanel.Controls.Clear();
            PopulateNotes();
            otherRadioButton.Checked = true;
            PopulateTemplates(otherLetterTemplates, LetterType.Other.ToString(), 0);
        }

        /// <summary>
        /// Create a prebillinvoice model.
        /// </summary>
        /// <param name="letterTemplateId"></param>
        /// <param name="notes"></param>
        /// <param name="freeformNotes"></param>
        /// <param name="letterType"></param>
        /// <returns></returns>
        public PreBillInvoiceDetails CreateLetterDetails(long letterTemplateId,
                                                         string letterTemplateName,
                                                         List<string> notes,
                                                         string letterType)
        {
            PreBillInvoiceDetails preBillInvoiceDetails = new PreBillInvoiceDetails();
            
            preBillInvoiceDetails.BalanceDue            = release.BalanceDue.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);            
            preBillInvoiceDetails.TotalPagesPerRelease  = release.TotalPages;
            preBillInvoiceDetails.Release               = auditReleaseInfo;
            preBillInvoiceDetails.Release.ReleaseDate   = null;
            preBillInvoiceDetails.Release.ReleasedPatients.Clear();

            RequestDetails requestDetails = (RequestDetails)ROIViewUtility.DeepClone(request);

            foreach (RequestPatientDetails requestPatientDetails in requestDetails.Patients.Values)
            {
                ReleasedPatientDetails releasedPatientDetails = new ReleasedPatientDetails();

                releasedPatientDetails.FullName     = requestPatientDetails.FullName;
                releasedPatientDetails.EPN          = requestPatientDetails.EPN;
                releasedPatientDetails.SSN          = requestPatientDetails.SSN;
                releasedPatientDetails.MRN          = requestPatientDetails.MRN;
                releasedPatientDetails.FacilityCode = requestPatientDetails.FacilityCode;

                preBillInvoiceDetails.Release.ReleasedPatients.Add(requestPatientDetails.Key, releasedPatientDetails);
            }
           
            preBillInvoiceDetails.RequestStatus         = EnumUtilities.GetDescription(request.Status);
            preBillInvoiceDetails.StatusReasons         = request.StatusReason;
            preBillInvoiceDetails.LetterTemplateId      = letterTemplateId;
            preBillInvoiceDetails.LetterTemplateName    = letterTemplateName;
            preBillInvoiceDetails.InvoiceType           = letterType;           
            preBillInvoiceDetails.Requestor             = request.Requestor;
            preBillInvoiceDetails.Release.RequestId     = request.Id;
            preBillInvoiceDetails.DateCreated           = request.DateCreated;

            foreach (string note in notes)
            {
                preBillInvoiceDetails.Notes.Add(note);
            }            

            if (request.HasDraftRelease)
            {
                BillingPaymentInfoUI billingPayment = new BillingPaymentInfoUI();
                billingPayment.RevertInfo(request);         
                preBillInvoiceDetails.Release.ShippingDetails.AddressType = request.DraftRelease.ShippingDetails.AddressType;
                preBillInvoiceDetails.Release.ShippingDetails.ShippingAddress = request.DraftRelease.ShippingDetails.ShippingAddress;
            }
            else
            {
                if (release.ShippingDetails != null)
                {
                    if (release.ShippingDetails.ShippingAddress == null)
                    {
                        AddressDetails address = new AddressDetails();

                        address.Address1 = request.Requestor.MainAddress.Address1;
                        address.Address2 = request.Requestor.MainAddress.Address2;
                        address.Address3 = request.Requestor.MainAddress.Address3;
                        address.City = request.Requestor.MainAddress.City;
                        address.State = request.Requestor.MainAddress.State;
                        address.PostalCode = request.Requestor.MainAddress.PostalCode;

                        preBillInvoiceDetails.Release.ShippingDetails.AddressType = RequestorAddressType.Main;
                        preBillInvoiceDetails.Release.ShippingDetails.ShippingAddress = address;
                    }
                }
            }
            return preBillInvoiceDetails;
        }

        /// <summary>
        /// Prepopulate the letter templates
        /// </summary>
        /// <param name="collection"></param>
        public void PrePopulate(RequestDetails requestDetails, ReleaseDetails releaseDetails)
        {
            IList<LetterTemplateDetails> defaultTemplates;

            Collection<LetterTemplateDetails> letterTemplates = ROIAdminController.Instance.RetrieveAllLetterTemplates();
            List<LetterTemplateDetails> list = new List<LetterTemplateDetails>(letterTemplates);

            request = requestDetails;
            release = releaseDetails;
            auditReleaseInfo = (ReleaseDetails)ROIViewUtility.DeepClone(release);

            otherLetterTemplates = list.FindAll(delegate(LetterTemplateDetails item) { return item.LetterType.ToString() == LetterType.Other.ToString(); });         
            coverLetterTemplates = list.FindAll(delegate(LetterTemplateDetails item) { return item.LetterType.ToString() == LetterType.CoverLetter.ToString(); });

            otherLetterTemplates.Sort(LetterTemplateDetails.Sorter);
            coverLetterTemplates.Sort(LetterTemplateDetails.Sorter);

            defaultTemplates = coverLetterTemplates.FindAll(delegate(LetterTemplateDetails item) { return item.IsDefault == true; });
            if (defaultTemplates.Count > 0)
            {
                defaultCoverLetterId = defaultTemplates[0].DocumentId;
            }

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            forSelect = new LetterTemplateDetails();
            forSelect.Id = 0;            
            forSelect.Name = rm.GetString("letterTemplateText");

            otherLetterTemplates.Insert(0, forSelect);
            coverLetterTemplates.Insert(0, forSelect);

            PopulateTemplates(otherLetterTemplates,LetterType.Other.ToString(),0);
            PopulateNotes();
            notesGroupPanel.Visible = false;
         
            EnableButtons(false);
            notesPanel.SelectNextControl(createFreeformButton, true, true, true, true);

            coverLetterRadioButton.Enabled = !(request.Status == RequestStatus.Canceled || request.Status == RequestStatus.Denied);
        }

        /// <summary>
        /// Populate configuration notes.
        /// </summary>
        /// <param name="templates"></param>
        public void PopulateNotes()
        {
            ROIViewUtility.MarkBusy(true);

            Collection<NotesDetails> notes = ROIAdminController.Instance.RetrieveAllConfigureNotes();
            maxLength = (notes.Count > 0) ? notes[0].DisplayText.Length : 0;
            for (int count = 1; count < notes.Count; count++)
            {
                if (notes[count].DisplayText.Length > maxLength)
                {
                    maxLength = notes[count].DisplayText.Length;
                }
            }
            foreach (NotesDetails note in notes)
            {
                FreeformUI freeformUI = CreateFreeformUI(false);
                freeformUI.NoteId = note.Id;
                freeformUI.FreeformCheckBox.Text = note.DisplayText.Replace("&", "&&");
                if (maxLength > 110)
                {
                    freeformUI.Width = maxLength * 6;
                }
            }

            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Occurs when user clicks on CreateFreeform button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void createFreeformButton_Click(object sender, EventArgs e)
        {
            FreeformUI freeformUI = CreateFreeformUI(true);

            freeformUI.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));

            int notesCount = notesPanel.Controls.Count;
            if (notesCount % 2 != 0)
            {
                freeformUI.BackColor = Color.FromArgb(236, 238, 245);
            }
            freeformUI.SetFocus();
            freeformUI.FreeformTextBox.TabStop = true;
        }

        /// <summary>
        /// Create a freeform UI.
        /// </summary>
        /// <returns></returns>
        private FreeformUI CreateFreeformUI(bool isFreeformNote)
        {
            FreeformUI freeformUI = new FreeformUI();

            freeformUI.SetExecutionContext(Context);
            freeformUI.SetPane(Pane);
            freeformUI.Localize();
            freeformUI.DeleteFreeformHandler = new EventHandler(DeleteFreeformReason);
            freeformUI.ReasonCheckedHandler = defaultDataChangedHandler;

            freeformUI.IsFreeform = isFreeformNote;            
            notesPanel.Controls.Add(freeformUI);
            if (maxLength > 110)
            {
                freeformUI.Width = maxLength * 6;
            }
            else
            {
                freeformUI.Width = notesPanel.Width;
            }

            int notesCount = notesPanel.Controls.Count;

            if (!isFreeformNote)
            {
                freeformUI.DeleteImage.Visible = false;
                freeformUI.FreeformTextBox.Visible = false;                
                freeformUI.IsFreeform = false;

                if (notesCount % 2 != 0)
                {
                    freeformUI.BackColor = Color.FromArgb(236, 238, 245);
                    freeformUI.FreeformTextBox.BackColor = Color.FromArgb(236, 238, 245);
                }
            }
            else
            {
                if (notesCount % 2 != 0)
                {
                    freeformUI.BackColor = Color.FromArgb(236, 238, 245);
                }
            }
            return freeformUI;
        }

        /// <summary>
        /// Delete selected freeform notes
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DeleteFreeformReason(object sender, EventArgs e)
        {
            FreeformUI freeformUI = (FreeformUI)sender;
            int index = notesPanel.Controls.IndexOf(freeformUI);
            notesPanel.Controls.RemoveAt(index);

            for (int count = index; count < notesPanel.Controls.Count; count++)
            {
                if (count % 2 == 0)
                {
                    notesPanel.Controls[count].BackColor = Color.FromArgb(236, 238, 245);
                }
                else
                {
                    notesPanel.Controls[count].BackColor = Color.White;
                }
            }
        }

        public override void SetAcceptButton()
        {
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = letterInfoActionUI.CreateLetterButton;
            }
        }

        #endregion           

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds))
            {
                this.Enabled = false;
            }
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return letterInfoActionUI;
        }

        #endregion
    }
}
