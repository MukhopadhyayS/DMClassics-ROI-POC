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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Globalization;
using System.Net;
using System.Resources;
using System.Windows.Forms;
using System.Xml;

using McK.EIG.Common.Audit.Controller;
using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View.Common.Tree;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Request.View.BillingPayment;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    public partial class RequestPatientInfoUI : ROIBaseUI, IFooterProvider
    {
        
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(RequestPatientInfoUI));

        private const string DuplicateRecordsDialogTitle            = "DuplicateRecordsDialog.Title";
        private const string DuplicateRecordsDialogOkButton         = "DuplicateRecordsDialog.OkButton";
        private const string DuplicateRecordsDialogDialogMessageKey = "A303";

        private const string ReleasedRecordsDialogTitle            = "ReleasedRecordsDialog.Title";
        private const string ReleasedRecordsDialogOkButton         = "ReleasedRecordsDialog.OkButton";
        private const string ReleasedRecordsDialogDialogMessageKey = "A304";
        private const string DialogName = "Request Patient Info";

        private bool isDirty;
        private PatientInfoActionUI patientInfoActionUI;
        private RequestDetails request;
        public Dictionary<long, bool> PageStatus;
        public Dictionary<long, bool> NonHPFDocumentStatus;
        public Dictionary<long, bool> AttachmentStatus;

        private EventHandler dirtyDataHandler;

        private RequestorTypeDetails requestorType;
        private Collection<BillingTierDetails> billingTiers;
        private Hashtable htBillingTiers;
        private PatientDetails patientInfo;
        private ReleaseDetails releaseDetails;    // CR#359303  
        private List<long> deletedPatients;
        private List<long> deletedDARSupplementalPatients;
        private bool hasFirstRowSelection; //CR#375064 
        private bool isPatientAddedRemoved; //CR#375064
            
        public RequestPatients requestPatients;

        private RequestDetails tempRequestDetails;
        
        public static bool IsResend = false;
        #endregion

        #region Constructor

        public RequestPatientInfoUI()
        {
            InitializeComponent();
            
            dirtyDataHandler = new EventHandler(MarkDirty);
            CreateActionUI();
            CreatePatientListActionUI();
            CreatePatientRecordsActionUI();
            loadingCircle.Visible = false;
            patientRecordsViewUI.Enabled = false;
            patientRecordsViewUI.ShowButtons = true;
            EnableUI(false);
        }

        #endregion
        
        #region Methods

        private void EnableUI(bool enable)
        {
            this.Enabled = enable;
            patientInfoActionUI.Enabled = enable;
        }

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, selectedPatientsGroupBox);

            patientRecordsViewUI.Localize();
            dsrTreeUI.Localize();
            patientInfoActionUI.Localize();
            patientListUI.Localize();
        }

        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);

            patientRecordsViewUI.SetPane(Pane);
            patientRecordsViewUI.SetExecutionContext(Context);
            
            dsrTreeUI.SetPane(Pane);
            dsrTreeUI.SetExecutionContext(Context);
            
            patientInfoActionUI.SetPane(Pane);
            patientInfoActionUI.SetExecutionContext(Context);
            
            patientListUI.SetPane(Pane);
            patientListUI.SetExecutionContext(Context);
        }

        /// <summary>
        /// Sets the actionUI for the footer.
        /// </summary>
        private void CreateActionUI()
        {
            patientInfoActionUI = new PatientInfoActionUI();

            patientInfoActionUI.SaveButton.Click        += new EventHandler(saveButton_Click);
            patientInfoActionUI.SaveBillButton.Click    += new EventHandler(saveBillButton_Click);
            patientInfoActionUI.RevertButton.Click      += new EventHandler(revertButton_Click);
            patientInfoActionUI.ViewAuthRequestButton.Click +=new EventHandler(ViewAuthReqButton_Click);

            EnableButtons(false);
        }

        private void CreatePatientListActionUI()
        {
            patientListUI.AddPatientButton.Click        += new EventHandler(addPatientButton_Click);
            patientListUI.AddAnotherPatientButton.Click += new EventHandler(addAnotherPatientButton_Click);
            patientListUI.ViewEditButton.Click          += new EventHandler(viewEditPatientButton_Click);
            patientListUI.RemovePatientButton.Click     += new EventHandler(removePatientButton_Click);
            patientListUI.PatientSelectionHandler       = new EventHandler(Process_PatientSelected);
        }

        private void CreatePatientRecordsActionUI()
        {
            patientRecordsViewUI.AddButton.Click    += new EventHandler(addButton_Click);
            patientRecordsViewUI.AddAllButton.Click += new EventHandler(addAllButton_Click);
            patientRecordsViewUI.PatientRecordsTreeView.SelectionChanged += new EventHandler(tree_SelectionChanged);
        }

        internal void MarkDirty(object sender, EventArgs e)
        {
            isDirty = true;
            EnableButtons(true);

            dsrTreeUI.PreviouslyReleased();
            dsrTreeUI.ResendButton.Enabled = (patientListUI.Grid.RowCount > 0 && !dsrTreeUI.HasPreviouslyReleased);
            //CR#377567
            bool hasRights = true;
            Application.DoEvents();
            hasRights = RequestController.Instance.HasSercuirtyRights();
            if (hasRights == false)
                dsrTreeUI.ResendButton.Enabled = false;
            EnableSaveBillButton();
        }

        private void EnableSaveBillButton()
        {
            SaveBillButton.Enabled = (patientListUI.Grid.RowCount > 0 && dsrTreeUI.HasRecordsChecked);

            bool enableBillingLink = true;
            if (dsrTreeUI.ReleasedPageCount > 0 && dsrTreeUI.UnreleasedPageCount > 0)
            {
                patientInfoActionUI.SaveButton.Enabled = SaveBillButton.Enabled = false;
                enableBillingLink = false;
            }

            if (dsrTreeUI.DeletedPageCount > 0)
            {
                patientInfoActionUI.SaveButton.Enabled = SaveBillButton.Enabled = false;
                enableBillingLink = false;
            }

            //showPreviousRelease = request.IsReleased && !SaveBillButton.Enabled;
            showPreviousRelease = request.IsReleased && !request.HasDraftRelease && !SaveBillButton.Enabled;
            enableBillingLink = enableBillingLink && (showPreviousRelease || request.HasDraftRelease || SaveBillButton.Enabled);
            enableBillingLink = enableBillingLink && (showPreviousRelease || (request.HasDraftRelease && dsrTreeUI.HasRecordsChecked) || SaveBillButton.Enabled) || (request.ReleaseCount > 0);
            enableBillingLink = (request.Patients.Count > 0 || tempRequestDetails.Patients.Count > 0) && enableBillingLink;
            if (request.IsOldRequest)
            {
                enableBillingLink = false;
                SaveBillButton.Enabled = false;
            }

            RequestEvents.OnDsrChanged(Pane, new ApplicationEventArgs(enableBillingLink, this));
        }

        public void SetData(object data)
        {
            EnableUI(true);
            request = (RequestDetails)data;            

            RequestDetails requestDetails = (RequestDetails)ROIViewUtility.DeepClone(request);

            //BillingPaymentInfoUI billingPayment = new BillingPaymentInfoUI();

            Application.DoEvents();
            requestPatients = RequestController.Instance.RetrieveRequestPatients(requestDetails.Id);
            PageStatus = requestPatients.PageStatus;
            NonHPFDocumentStatus = requestPatients.NonHpfDocumentStatus;
            AttachmentStatus = requestPatients.AttachmentStatus;
            
            requestDetails.Patients.Clear();
            tempRequestDetails = new RequestDetails();
            tempRequestDetails.Patients.Clear();
            foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
            {
                requestDetails.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                tempRequestDetails.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
            }

            requestorType = ROIAdminController.Instance.GetRequestorType(requestDetails.RequestorType);
			//CR# 366249 set the default record view for the selected requestor type            
			int index = patientRecordsViewUI.RecordViewCombobox.FindStringExact(requestorType.RecordViewDetails.Name);
            patientRecordsViewUI.ReqId = request.Id;
            if (requestDetails.Patients != null)
            {
                foreach (KeyValuePair<string, RequestPatientDetails> req in requestDetails.Patients)
                {
                    patientRecordsViewUI.Patients.Add(req.Value);
                }
            }
            if (index != -1)
            {
                patientRecordsViewUI.RecordViewCombobox.SelectedIndex = index;
            }

            // CR#359303
            if (requestDetails.Releases != null && requestDetails.ReleaseCount > 0)
            {
                //releaseDetails = BillingController.Instance.RetrieveReleaseInfo(requestDetails.Releases[0]);

                //Naved Commented - Shipping details can be obtained from request details
                //releaseDetails = billingPayment.RevertInfo(requestDetails);

                if (releaseDetails != null && releaseDetails.ShippingDetails != null)
                {                    
                    dsrTreeUI.OutputMethod = releaseDetails.ShippingDetails.OutputMethod;
                }
            }
            
            //Naved Commented - redundant
            //requestorType = ROIAdminController.Instance.GetRequestorType(requestDetails.RequestorType);

            patientRecordsViewUI.AddAllButton.Enabled = false;
            patientRecordsViewUI.PatientRecordsTreeView.Model = null;
            patientRecordsViewUI.PatientRecordsTreeView.Refresh();
            
            dsrTreeUI.SetData(requestDetails);
            dsrTreeUI.EnableButtons();
            dsrTreeUI.ResendButton.Click -= new EventHandler(ResendButton_Click);
            dsrTreeUI.ResendButton.Click += new EventHandler(ResendButton_Click);

            if (!hasFirstRowSelection)
            {
                patientListUI.SetData(requestDetails.Patients);
            }
            
            int itemCount = patientListUI.Grid.Items.Count;
            patientListUI.AddPatientButton.Visible =        (itemCount == 0);
            patientListUI.ViewEditButton.Visible =          (itemCount > 0);
            patientListUI.AddAnotherPatientButton.Visible = (itemCount > 0);
            patientListUI.RemovePatientButton.Visible =     (itemCount > 0);

            patientRecordsViewUI.Enabled = (itemCount > 0);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            patientInfoActionUI.RequestLockedImage.Visible = request.IsLocked;
            if (request.IsLocked)
            {
                toolTip.SetToolTip(patientInfoActionUI.RequestLockedImage,
                                   string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString(patientInfoActionUI.RequestLockedImage.Name), request.InUseRecord.UserId));
            }

            patientInfoActionUI.ViewAuthRequestButton.Enabled = !string.IsNullOrEmpty(request.AuthRequest);
            EnableButtons(false);

            SaveBillButton.Enabled = (itemCount > 0 && dsrTreeUI.HasRecordsChecked && !request.IsOldRequest && (dsrTreeUI.DeletedPageCount==0));
            showPreviousRelease = (!request.HasDraftRelease && !SaveBillButton.Enabled);

            ApplySecurityRights();
        }

        private void ResendButton_Click(object sender, EventArgs e)
        {
            try
            {
                
                ROIViewUtility.MarkBusy(true);
                IsResend = true;
                ROIViewer viewer = new ROIViewer(Pane, string.Empty, DialogName);
                viewer.ReleaseDialog = true;
                viewer.RequestorFax = request.RequestorFax;
                viewer.RequestorEmail = request.Requestor.Email;

                RequestDetails requestDetails = (RequestDetails)ROIViewUtility.DeepClone(dsrTreeUI.ReleaseTreeModel.Request);
                if (releaseDetails == null)
                {
                    foreach (ReleaseDetails details in requestDetails.Releases)
                    {
                        releaseDetails = details;
                    }
                }


                if (releaseDetails == null)
                {
                    releaseDetails = new ReleaseDetails();
                }

                RequestBillingInfo reqBillInfo = RequestController.Instance.RetrieveRequestBillingPaymentInfo(request.Id);
                releaseDetails.ShippingDetails = reqBillInfo.ShippingInfo;                

                bool isDisc = (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Disc);

                SortedList<string, ReleasedPatientDetails> releasedPatients = new SortedList<string, ReleasedPatientDetails>();
                ReleasedPatientDetails releasedPatient = null;

                int nonHPFDocumentCount = 0;
                int encounterDocumentCount = 0;
                int attachmentCount = 0;
                int globalDocumentCount = 0;

                foreach (RequestPatientDetails patient in requestDetails.ReleasedItems.Values)
                {
                    attachmentCount += patient.Attachment.GetChildren.Count;
                    globalDocumentCount += patient.GlobalDocument.GetChildren.Count;
                    nonHPFDocumentCount += patient.NonHpfDocument.GetChildren.Count;
                    encounterDocumentCount += patient.GetChildren.Count;

                    releasedPatient = new ReleasedPatientDetails();
                    releasedPatients.Add(patient.Key, releasedPatient.AssignRequestToReleasedPatient(patient));
                }

                if (nonHPFDocumentCount != 0 && encounterDocumentCount == 0 && attachmentCount == 0 && globalDocumentCount == 0)
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string messageString = rm.GetString("releaseOnlyNonHPFDocumentLabel.OutputDiscDialog");
                    string okButtonText = "OK";
                    ROIViewUtility.ConfirmChanges("Message", messageString, okButtonText, "", ROIDialogIcon.Info);
                    return;
                }

                
                DialogResult result = DialogResult.OK;

                if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Print)
                {
                    result = viewer.ShowPrintDialog(this);
                }
                else if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Fax)
                {
                    result = viewer.ShowFaxDialog(this);
                }
                else if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.SaveAsFile)
                {
                    result = viewer.ShowFileDialog(this);
                }
                else if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Email)
                {
                    result = viewer.ShowEmailDialog(this);
                }
                else if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Disc)
                {
                    result = viewer.ShowDiscDialog(this);
                    IsResend = false;
                }
                OutputPropertyDetails outputPropDetails = (isDisc) ? viewer.OutputPropertyDetailsForDisc : viewer.OutputPropertyDetails;
                if (result == DialogResult.OK)
                {
                    CommentDetails details = null;
                    // Start Non-Printable attachment file dialog
                    if ((releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Print ||
                        releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Fax) && requestDetails.RetrieveNonPrintableCount() > 0)                        
                    {
                        OutputRequestDetails outputNonPrintableRequest = new OutputRequestDetails(request.Id, releaseDetails.Id,
                                                                         request.RequestPassword, request.ReceiptDate);

                        result = DialogResult.OK;
                        viewer = new ROIViewer(Pane, string.Empty, DialogName);
                        viewer.ReleaseDialog = true;
                        result = viewer.ShowFileDialog(this);

                        if (result == DialogResult.OK)
                        {
                            OutputPropertyDetails fileOutputPropDetails = (isDisc) ? viewer.OutputPropertyDetailsForDisc : viewer.OutputPropertyDetails;
                            //check the method BuildAttachmentRequestPartDetails
                            Collection<RequestPartDetails> atttachmentRequestParts = new Collection<RequestPartDetails>();
                            atttachmentRequestParts.Add(GetPatientAttachmentRequestPart(releasedPatients, true, false));
                            atttachmentRequestParts.Add(GetPatientAttachmentRequestPart(releasedPatients, false, false));

                            outputNonPrintableRequest.OutputDestinationDetails = fileOutputPropDetails.OutputDestinationDetails[0];
                            OutputViewDetails nonPrintableOutputViewDetails = fileOutputPropDetails.OutputViewDetails;

                            // Need to save Request and Queue level passwords

                            ////Queue and Request passwords will be updated after MarkAsRelease method call
                            //if (!string.IsNullOrEmpty(outputNonPrintableRequest.OutputDestinationDetails.Password))
                            //{
                            //    release.QueuePassword = outputNonPrintableRequest.OutputDestinationDetails.Password;
                            //}
                            //// Always file dialog
                            //release.RequestPassword = request.RequestPassword;

                            foreach (RequestPartDetails reqPartDetail in atttachmentRequestParts)
                            {
                                if (reqPartDetail.PropertyLists.Count > 0)
                                {
                                    outputNonPrintableRequest.RequestParts.Add(reqPartDetail);
                                }
                            }

                            if (outputNonPrintableRequest.RequestParts.Count > 0)
                            {
                                //Event log for resend event
                                details = new CommentDetails();

                                details.RequestId = request.Id;
                                details.EventType = EventType.DocumentsResend;

                                details.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.ResendEventmessage,
                                                                     releasedPatients.Count,
                                                                     BillingPaymentInfoUI.NonPrintableAttachmentPageCount,
                                                                     DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
                                RequestController.Instance.CreateComment(details);
                                Application.DoEvents();
                                long jobStatus = OutputController.Instance.SubmitOutputRequest(outputNonPrintableRequest, DestinationType.File,
                                                                                         nonPrintableOutputViewDetails, true);
                            }
                        }
                    }

                    // End of Non-Printable attachment file dialog
                    
                    RequestPartDetails requestPartDetails = BillingPaymentInfoUI.BuildHpfRequestPartDetails(releasedPatients);

                    OutputRequestDetails outputRequestDetails = new OutputRequestDetails(request.Id,
                                                                                         releaseDetails.Id,
                                                                                         request.RequestPassword,
                                                                                         request.ReceiptDate);

                    outputRequestDetails.OutputDestinationDetails = outputPropDetails.OutputDestinationDetails[0];
                    OutputViewDetails outputViewDetails = outputPropDetails.OutputViewDetails;

                    if (requestPartDetails.PropertyLists.Count > 0)
                    {
                        outputRequestDetails.RequestParts.Add(requestPartDetails);
                    }

                    Collection<RequestPartDetails> requestParts = new Collection<RequestPartDetails>();
                    requestParts.Add(GetHpfDocumentsRequestPart(releasedPatients));
                    requestParts.Add(GetPatientAttachmentRequestPart(releasedPatients, true, true));
                    requestParts.Add(GetPatientAttachmentRequestPart(releasedPatients, false, true));

                    //Add Attachments to output request
                    //Collection<RequestPartDetails> attachmentReqParts =
                        //BillingPaymentInfoUI.BuildAttachmentRequestPartDetails(releasedPatients, true);
                    //BillingPaymentInfoUI.AddRequestPartToOutputRequest(attachmentReqParts, outputRequestDetails);

                    if ((releaseDetails.ShippingDetails.OutputMethod == OutputMethod.SaveAsFile
                        || releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Email
                        || releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Disc) && request.RetrieveNonPrintableCount() > 0)
                    {
                        //attachmentReqParts = BillingPaymentInfoUI.BuildAttachmentRequestPartDetails(releasedPatients, false);
                        //BillingPaymentInfoUI.AddRequestPartToOutputRequest(attachmentReqParts, outputRequestDetails);
                        requestParts.Add(GetPatientAttachmentRequestPart(releasedPatients, true, false));
                        requestParts.Add(GetPatientAttachmentRequestPart(releasedPatients, false, false));
                    }
                    BillingPaymentInfoUI.AddRequestPartToOutputRequest(requestParts, outputRequestDetails);
                   
                    //Audit the resend event

                    AuditEvent auditEvent = new AuditEvent();

                    auditEvent.UserId = UserData.Instance.UserInstanceId;
                    auditEvent.EventStart = System.DateTime.Now;
                    auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                    auditEvent.EventId = 1;
                    auditEvent.Facility = ROIConstants.FacilityName;

                    string faxNumber = string.Empty;

                    if (outputRequestDetails.OutputDestinationDetails.Type.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == DestinationType.Print.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        auditEvent.ActionCode = ROIConstants.PrintActioncode;
                    }

                    if (outputRequestDetails.OutputDestinationDetails.Type.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == DestinationType.Fax.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        faxNumber = outputRequestDetails.OutputDestinationDetails.Fax;
                        auditEvent.ActionCode = ROIConstants.FaxActioncode;
                    }

                    if (outputRequestDetails.OutputDestinationDetails.Type.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == DestinationType.File.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        auditEvent.ActionCode = ROIConstants.FileActioncode;
                    }
                    if (outputRequestDetails.OutputDestinationDetails.Type.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == DestinationType.Email.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        auditEvent.ActionCode = ROIConstants.EmailActioncode;
                    }
                    if (outputRequestDetails.OutputDestinationDetails.Type.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == DestinationType.Disc.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        auditEvent.ActionCode = ROIConstants.DiscActionCode;
                    }

                    List<AuditEvent> auditEvents = new List<AuditEvent>();

                    //CR#365598 - New arguments are added. 
                    //1.Action code
                    //2.IsInvoiced - Default is false while resend
                    auditEvents = BillingPaymentInfoUI.PrepareAuditEvent(releasedPatients, faxNumber, EventType.DocumentsResend,
                                                                            outputRequestDetails.OutputDestinationDetails.Type,
                                                                            outputRequestDetails.OutputDestinationDetails.Name,
                                                                            outputRequestDetails.OutputDestinationDetails.EmailAddr,
                                                                            request.Id, auditEvent.ActionCode, false,
                                                                            //Disc Properties
                                                                            outputRequestDetails.OutputDestinationDetails.Name,
                                                                            auditEvent.ActionCode, isDisc,
                                                                            outputRequestDetails.OutputDestinationDetails.DiscType,
                                                                            outputRequestDetails.OutputDestinationDetails.TemplateName);

                    try
                    {
                        Application.DoEvents();
                        ROIController.Instance.CreateAuditEntryList(auditEvents);
                    }
                    catch (ROIException cause)
                    {
                        log.FunctionFailure(cause);
                    }

                    if (outputRequestDetails.RequestParts.Count > 0)
                    {
                        //Event log for resend event
                        details = new CommentDetails();

                        details.RequestId = request.Id;
                        details.EventType = EventType.DocumentsResend;

                        int totalReleasedPages = 0;
                        string outputMethod = string.Empty;

                        if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.SaveAsFile)
                        {
                            totalReleasedPages = BillingPaymentInfoUI.HpfDocumentCount + BillingPaymentInfoUI.PrintableAttachmentPageCount +
                                                 BillingPaymentInfoUI.NonPrintableAttachmentPageCount;
                            outputMethod = DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture);
                        }
                        else if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Email)
                        {
                            totalReleasedPages = BillingPaymentInfoUI.HpfDocumentCount + BillingPaymentInfoUI.PrintableAttachmentPageCount +
                                                 BillingPaymentInfoUI.NonPrintableAttachmentPageCount;
                            outputMethod = DestinationType.Email.ToString().ToUpper();
                        }
                        else
                        {
                            totalReleasedPages = BillingPaymentInfoUI.HpfDocumentCount + BillingPaymentInfoUI.PrintableAttachmentPageCount;
                            outputMethod = releaseDetails.ShippingDetails.OutputMethod.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture);
                        }

                        details.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.ResendEventmessage,
                                                             releasedPatients.Count, totalReleasedPages, outputMethod);

                        RequestController.Instance.CreateComment(details);
                        if (releaseDetails.ShippingDetails.OutputMethod == OutputMethod.Disc)
                        {
                            BillingPaymentInfoUI billingPaymentInfoUI = new BillingPaymentInfoUI();
                            RequestPartDetails requestPart = billingPaymentInfoUI.BuildRequestPartDetails(releasedPatients, viewer.OutputPropertyDetailsForDisc, request, releaseDetails);
                            // build part requst part
                            outputRequestDetails.RequestParts.Add(requestPart);
                        }
                        Application.DoEvents();
                        long jobStatus = OutputController.Instance.SubmitOutputRequest(outputRequestDetails,
                                                                                      BillingPaymentInfoUI.RetrieveDestinationType(releaseDetails.ShippingDetails),
                                                                                      outputViewDetails, true); //Change needs

                        if (jobStatus == -200)
                        {
                            return;
                        }
                    }
                    dsrTreeUI.ResendButton.Enabled = false;
                    ReleaseTreeUI.MarkAsFalseForReleasedDocuments(dsrTreeUI.ReleaseTreeModel.Request);
                    dsrTreeUI.ReleaseTreeModel.Refresh();
                    //dirtyDataHandler(sender, e);
                    Save(true);
                    BillingController.Instance.IsDisplayBillingInfo(request.Id, false);
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }           
            finally
            {
                IsResend = false;
                OutputFileDialog.CloseSplashScreen();
                OutputPrintDialog.CloseSplashScreen();
                OutputFaxDialog.CloseSplashScreen();
                OutputEmailDialog.CloseSplashScreen();
                OutputDiscDialog.CloseSplashScreen();
                ROIViewUtility.MarkBusy(false);
            }
        }
        
        /// <summary>
        /// retrieves the request part for the hpf patient documents
        /// </summary>
        /// <param name="releasedPatients"></param>
        /// <returns></returns>
        private RequestPartDetails GetHpfDocumentsRequestPart(SortedList<string, ReleasedPatientDetails> releasedPatients)
        {

            RequestPartDetails requestPartDetails = new RequestPartDetails();
            requestPartDetails.ContentId = string.Empty;
            requestPartDetails.ContentSource = OutputPropertyDetails.ResendMPFContentSource;

            string pageSeq = string.Empty;
            foreach (ReleasedPatientDetails releasedPatient in releasedPatients.Values)
            {
                IList<BaseRequestItem> encounters = releasedPatient.GetChildren;
                foreach (RequestEncounterDetails enc in encounters)
                {
                    foreach (RequestDocumentDetails doc in enc.GetChildren)
                    {
                        foreach (RequestVersionDetails ver in doc.GetChildren)
                        {
                            foreach (RequestPageDetails page in ver.GetChildren)
                            {
                                if (page.SelectedForRelease.HasValue && (bool)page.SelectedForRelease)
                                {
                                    pageSeq = pageSeq + page.PageSeq + ",";
                                }
                            }
                        }
                    }
                }
                foreach (RequestDocumentDetails globalDocument in releasedPatient.GlobalDocument.GetChildren)
                {
                    foreach (RequestVersionDetails ver in globalDocument.GetChildren)
                    {
                        foreach (RequestPageDetails page in ver.GetChildren)
                        {
                            if (page.SelectedForRelease.HasValue && (bool)page.SelectedForRelease)
                            {
                                pageSeq = pageSeq + page.PageSeq + ",";
                            }
                        }
                    }
                }
            }
            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.PageIds = pageSeq.TrimEnd(',');
            requestPartDetails.PropertyLists.Add(propertyDetails);
            return requestPartDetails;
        }

        /// <summary>
        /// retrieves the request parts for the attachment
        /// </summary>
        /// <param name="releasedPatients"></param>
        /// <param name="isHpf"></param>
        /// <returns></returns>
        private RequestPartDetails GetPatientAttachmentRequestPart(SortedList<string, ReleasedPatientDetails> releasedPatients, 
                                                                    bool isHpf, 
                                                                    bool isPrintableAttachments)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();
            requestPartDetails.ContentId = string.Empty;
            if (isHpf)
            {
                requestPartDetails.ContentSource = OutputPropertyDetails.ResendMPFAttachmentContentSource;
            }
            else
            {
                requestPartDetails.ContentSource = OutputPropertyDetails.ResendNonMpfAttachmentContentSource;
            }

            string attachmentSeq = string.Empty;
            foreach (ReleasedPatientDetails releasedPatient in releasedPatients.Values)
            {
                if ((isHpf && !releasedPatient.IsHpf) || (!isHpf && releasedPatient.IsHpf))
                {
                    continue;
                }
                foreach (RequestAttachmentDetails tmpAttachment in releasedPatient.Attachment.GetChildren)
                {
                    if ((isPrintableAttachments && !tmpAttachment.IsPrintable) || (!isPrintableAttachments && tmpAttachment.IsPrintable))
                    {
                        continue;
                    }

                    if (tmpAttachment.SelectedForRelease.HasValue && (bool)tmpAttachment.SelectedForRelease)
                    {
                        attachmentSeq = attachmentSeq + tmpAttachment.AttachmentSeq + ",";
                    }
                }
            }

            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.PageIds = attachmentSeq.TrimEnd(',');
            requestPartDetails.PropertyLists.Add(propertyDetails);
            return requestPartDetails;
        }

        internal bool Save(bool doUpdateDraftRelease)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);

                if (dsrTreeUI.DeletedPageCount > 0)
                {
                    dsrTreeUI.ShowRemoveDeletedPagesDialog();
                    return false;
                }

                if (dsrTreeUI.ReleasedPageCount > 0 && dsrTreeUI.UnreleasedPageCount > 0)
                {
                    dsrTreeUI.ShowResendOrReleaseDialog();
                    return false;
                }

                RequestDetails requestToSave = dsrTreeUI.ReleaseTreeModel.Request;

                if (requestToSave.Patients.Count == 0)
                {
                    requestToSave.DeleteRelease = requestToSave.HasDraftRelease;
                    request.BalanceDue = 0;
                }

                UpdateRequestPatients updateRequestPatients = new UpdateRequestPatients();
                updateRequestPatients.DeleteList = dsrTreeUI.DeleteList;
                if (deletedPatients != null)  // HPF patient list grid.
                {
                    foreach (long patientSequence in deletedPatients)
                    {
                        updateRequestPatients.DeleteList.DeletedPatientList.Add(patientSequence);
                    }
                }
                if (deletedDARSupplementalPatients != null)
                {
                    foreach (long patientSequence in deletedDARSupplementalPatients)
                    {
                        updateRequestPatients.DeleteList.DeleteDARSupplementalPatients.Add(patientSequence);
                    }
                }
             
                foreach (RequestPatientDetails patient in requestToSave.Patients.Values)
                {
                    if (patient.PatientSeq == 0)
                    {
                        updateRequestPatients.RequestPatients.Add(patient);
                        continue;
                    }
                    
                    //New global document is added into existing patient.
                    foreach (RequestDocumentDetails globalDoc in patient.GlobalDocument.GetChildren)
                    {
                        if (globalDoc.DocumentSeq == 0)
                        {   
                            globalDoc.IsGlobalDocument = true;
                            globalDoc.PatientSeq = patient.PatientSeq;
                            updateRequestPatients.RequestDocuments.Add(globalDoc);
                            continue;
                        }

                        foreach (RequestVersionDetails version in globalDoc.GetChildren)
                        {
                            if (version.VersionSeq == 0)
                            {
                                version.DocumentSeq = globalDoc.DocumentSeq;
                                version.DocumentId = globalDoc.DocumentId;
                                version.IsGlobalDocumentVersion = true;
                                version.PatientSeq = patient.PatientSeq;
                                updateRequestPatients.RequestVersions.Add(version);
                                continue;
                            }
                            foreach (RequestPageDetails page in version.GetChildren)
                            {
                                if (page.PageSeq == 0)
                                {
                                    page.VersionSeq = version.VersionSeq;
                                    page.IsGlobalDocumentPage = true;
                                    page.PatientSeq = patient.PatientSeq;
                                    updateRequestPatients.RequestPages.Add(page);
                                }
                                else
                                {
                                    if (!PageStatus[page.PageSeq].Equals(page.SelectedForRelease))
                                    {
                                        updateRequestPatients.RequestPages.Add(page);
                                    }
                                }
                            }
                        }
                    }                    

                    foreach (RequestEncounterDetails encounter in patient.GetChildren)
                    {
                        if (encounter.EncounterSeq == 0)
                        {
                            encounter.PatientSeq = patient.PatientSeq;
                            encounter.Mrn = patient.MRN;
                            updateRequestPatients.RequestEncounters.Add(encounter);
                            continue;
                        }
                        foreach (RequestDocumentDetails document in encounter.GetChildren)
                        {
                            if (document.DocumentSeq == 0)
                            {
                                document.EncounterSeq = encounter.EncounterSeq;
                                document.PatientSeq = patient.PatientSeq;
                                document.Mrn = patient.MRN;
                                document.Facility = patient.FacilityCode;
                                document.Encounter = encounter.EncounterId;
                                updateRequestPatients.RequestDocuments.Add(document);
                                continue;
                            } 
                            foreach (RequestVersionDetails version in document.GetChildren)
                            {
                                if (version.VersionSeq == 0)
                                {
                                    version.DocumentSeq = document.DocumentSeq;
                                    version.DocumentId = document.DocumentId;
                                    updateRequestPatients.RequestVersions.Add(version);
                                    continue;
                                }                               

                                foreach (RequestPageDetails page in version.GetChildren)
                                {   
                                    if (page.PageSeq == 0)
                                    {
                                        page.VersionSeq = version.VersionSeq;
                                        updateRequestPatients.RequestPages.Add(page);
                                    }
                                    else
                                    {   
                                        if (!PageStatus[page.PageSeq].Equals(page.SelectedForRelease))
                                        {
                                            updateRequestPatients.RequestPages.Add(page);                                           
                                        }
                                    }
                                }
                            }
                        }
                    }

                    bool isAddedDocuments = false;
                    foreach (RequestNonHpfEncounterDetails nonHPFEncounterDetails in patient.NonHpfDocument.GetChildren)
                    {
                        isAddedDocuments = false;
                        foreach (RequestNonHpfDocumentDetails nonHPFDocument in nonHPFEncounterDetails.GetChildren)
                        {
                            nonHPFDocument.IsPatientFreeFormFacility = patient.IsFreeformFacility;
                            if (nonHPFDocument.DocumentSeq == 0)
                            {
                                nonHPFDocument.PatientSeq = patient.PatientSeq;
                                if (!isAddedDocuments)
                                {
                                    updateRequestPatients.RequestNonHPFEncounters.Add(nonHPFEncounterDetails);
                                    isAddedDocuments = true;
                                }
                            }
                            else if (nonHPFDocument.BillingTier != nonHPFDocument.BillingTierStatus)
                            {
                                if (!isAddedDocuments)
                                {
                                    updateRequestPatients.RequestNonHPFEncounters.Add(nonHPFEncounterDetails);
                                    isAddedDocuments = true;
                                }
                            }
                            else
                            {
                                if (!NonHPFDocumentStatus[nonHPFDocument.DocumentSeq].Equals(nonHPFDocument.SelectedForRelease))
                                {
                                    if (!isAddedDocuments)
                                    {
                                        updateRequestPatients.RequestNonHPFEncounters.Add(nonHPFEncounterDetails);
                                        isAddedDocuments = true;
                                    }
                                }
                            }
                        }                        
                    }
                    bool isAddedAttachments = false;
                    foreach (RequestAttachmentEncounterDetails requestAttachmentEncounter in patient.Attachment.GetChildren)
                    {
                        isAddedAttachments = false;
                        foreach (RequestAttachmentDetails requestAttachment in requestAttachmentEncounter.GetChildren)
                        {
                            requestAttachment.IsPatientFreeFormFacility = patient.IsFreeformFacility;
                            if (requestAttachment.AttachmentSeq == 0)
                            {
                                requestAttachment.PatientSeq = patient.PatientSeq;
                                if (!isAddedAttachments)
                                {
                                    updateRequestPatients.RequestAttachmentEncounterDetails.Add(requestAttachmentEncounter);
                                    isAddedAttachments = true;
                                }
                            }
                            else if (requestAttachment.BillingTier != requestAttachment.BillingTierStatus)
                            {
                                if (!isAddedAttachments)
                                {
                                    updateRequestPatients.RequestAttachmentEncounterDetails.Add(requestAttachmentEncounter);
                                    isAddedAttachments = true;
                                }
                            }
                            else
                            {
                                if (!AttachmentStatus[requestAttachment.AttachmentSeq].Equals(requestAttachment.SelectedForRelease))
                                {
                                    if (!isAddedAttachments)
                                    {
                                        updateRequestPatients.RequestAttachmentEncounterDetails.Add(requestAttachmentEncounter);
                                        isAddedAttachments = true;
                                    }
                                }
                            }
                        }                        
                    }
                }

                requestPatients = RequestController.Instance.SaveRequestPatients(updateRequestPatients, request.Id);
                dsrTreeUI.DeleteList.Clear();
                PageStatus = requestPatients.PageStatus;
                NonHPFDocumentStatus = requestPatients.NonHpfDocumentStatus;
                AttachmentStatus = requestPatients.AttachmentStatus;
               
                //TODO: Update Request LOV creation needs to verify
                //requestToSave = RequestController.Instance.UpdateRequest(requestToSave);

                requestToSave.Patients.Clear();
                bool isFirstPatient = true;
                foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                {
                    requestToSave.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                    patientListUI.UpdatePatient(requestPatientDetails);
                    if (isFirstPatient)
                    {
                        requestToSave.PatientNames = requestPatientDetails.Name;
                        isFirstPatient = false;
                    }
                    else
                    {
                        requestToSave.PatientNames += (":" + requestPatientDetails.Name);
                    }
                }
                if (patientListUI.SelectedPatient != null)
                {
                    if (patientListUI.SelectedPatient.PatientSeq != 0)
                    {
                        patientListUI.UpdatePatient(patientListUI.SelectedPatient);
                    }
                }
                this.request = requestToSave;
                dsrTreeUI.ReleaseTreeModel.Request = requestToSave;
                //CR 374865
                dsrTreeUI.ReleaseTreeModel.Refresh();

                //ApplicationEventArgs ae = new ApplicationEventArgs(this.request, this);
                ApplicationEventArgs ae = new ApplicationEventArgs(ROIViewUtility.DeepClone(this.request), this);
                //RequestEvents.OnRequestPatientInfoCreated(Pane, ae);
                RequestEvents.OnRequestUpdated(Pane, ae);

                if ((doUpdateDraftRelease && request.HasDraftRelease) || request.ReleaseCount > 0)
                {
                    //Naved Commented  - added
                   releaseDetails = UpdateReleaseDraft(doUpdateDraftRelease);
                }

                isDirty = false;
                EnableButtons(false);

                //SaveBillButton.Enabled = (patientListUI.Grid.RowCount > 0 && dsrTreeUI.HasRecordsChecked);
                EnableSaveBillButton();

                RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
                rsp.InfoEditor.Request = rsp.PatientInfoEditor.Request = ((RequestDetails)ROIViewUtility.DeepClone(dsrTreeUI.ReleaseTreeModel.Request));
                if (dsrTreeUI.ReleasedPageCount == 0 && dsrTreeUI.UnreleasedPageCount == 0)
                {
                    BillingController.Instance.IsDisplayBillingInfo(request.Id, false);
                }

            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                return false;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return true;
        }

        private RequestVersionDetails CreateVersionForGlobalDocuments(long documentId)
        {
            RequestVersionDetails requestVersion = new RequestVersionDetails();
            requestVersion.VersionNumber = 0;
            requestVersion.DocumentId = documentId;
            requestVersion.DocumentSeq = 0;
            return requestVersion;
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            Save(true);
        }

        private void saveBillButton_Click(object sender, EventArgs e)
        {
            //Fix for the issue, After creation of first pre - bill, on adding more documents, the no of pages gets displayed wrong and feecharge doesn't get displayed.
            if (SaveButton.Enabled == true)
            { Save(true); }
            else
            { Save(false); }

            //ReleaseDetails release =  UpdateReleaseDraft(false);

            //if(releaseDetails == null)
                releaseDetails = UpdateReleaseDraft(false);

            UnsubscribePatientSelection();
            RequestEvents.OnBillingSelected(Pane, new ApplicationEventArgs(releaseDetails, this));
        }

        public ReleaseDetails UpdateReleaseDraft(bool doUpdateDraftRelease)
        {
            ReleaseDetails release = null;
            BillingPaymentInfoUI billingPayment = new BillingPaymentInfoUI();
            try
            {
                ROIViewUtility.MarkBusy(true);
                RequestDetails request = (RequestDetails)ROIViewUtility.DeepClone(this.request);
                if ((dsrTreeUI.ReleasedPageCount == 0 && dsrTreeUI.UnreleasedPageCount == 0) || (!dsrTreeUI.HasRecordsChecked))
                {
                    BillingController.Instance.IsDisplayBillingInfo(request.Id, false);
                }
                else
                {
                    BillingController.Instance.IsDisplayBillingInfo(request.Id, true);
                }
                if (request.HasDraftRelease)
                {
                    //release = request.DraftRelease;
                    //release.RequestId = request.Id;
                    //if (release.Id > 0)
                    if (request.Id > 0)
                    {
                        release = billingPayment.RevertInfo(request);
                        //release = BillingController.Instance.RetrieveReleaseInfo(release);
                        release.DocumentCharges.Clear();
                    }
                }
                else
                {
                    release = new ReleaseDetails();
                    release.RequestId = request.Id;

                    if (request.ReleaseCount > 0)
                    {
                        release = billingPayment.RevertInfo(request);
                        //release = BillingController.Instance.RetrieveLatestReleaseInfo(release);
                        release.FeeCharges.Clear();
                        release.BillingType = "0";
                        if (release.ShippingDetails != null)
                        {
                            release.ShippingDetails.ShippingCharge = 0;
                            release.ShippingDetails.TrackingNumber = string.Empty;
                        }
                    }
                }

                release.Details = string.Empty;
                ReleasedPatientDetails releasePatient;
                release.ReleasedPatients.Clear();
                int hpfcount = 0;
                Dictionary<long, int> nonHpfBillingTiersIds = new Dictionary<long, int>();
                //compile list of "selected for release" documents to support release - sequences are mapped to the "release"
                List<ROIPage> roiPages = release.ROIPages;
                ROIPage roiPage = new ROIPage();
                //List<long> roiPagesSeqField = release.RoiPagesSeqField;
                List<long> supplementarityAttachmentsSeqField = release.SupplementarityAttachmentsSeqField;
                List<long> supplementarityDocumentsSeqField = release.SupplementarityDocumentsSeqField;
                List<long> supplementalAttachmentsSeqField = release.SupplementalAttachmentsSeqField;
                List<long> supplementalDocumentsSeqField = release.SupplementalDocumentsSeqField;

                foreach (RequestPatientDetails patient in request.ReleasedItems.Values)
                {
                    //releasePatient = (ReleasedPatientDetails)patient;
                    //sachin
                    foreach (RequestEncounterDetails enc in patient.GetChildren)
                    {
                        if (enc.SelectedForRelease == true)
                        {
                            foreach (RequestDocumentDetails doc in enc.GetChildren)
                            {
                                if (doc.SelectedForRelease == true)
                                {
                                    foreach (RequestVersionDetails ver in doc.GetChildren)
                                    {
                                        if (ver.SelectedForRelease == true)
                                        {
                                            foreach (RequestPageDetails page in ver.GetChildren)
                                            {
                                                if (page.SelectedForRelease == true)
                                                    hpfcount++;
                                                roiPage = new ROIPage();
                                                roiPage.PageSequence = page.PageSeq;
                                                roiPage.IsSelfEncounter = enc.IsSelfPay ? true : false;
                                                roiPages.Add(roiPage);
                                                //roiPagesSeqField.Add(page.PageSeq);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                    foreach (RequestDocumentDetails doc in patient.GlobalDocument.GetChildren)
                    {
                        if (doc.SelectedForRelease == true)
                        {
                            foreach (RequestVersionDetails ver in doc.GetChildren)
                            {
                                if (ver.SelectedForRelease == true)
                                {
                                    foreach (RequestPageDetails page in ver.GetChildren)
                                    {
                                        if (page.SelectedForRelease == true)
                                            hpfcount++;
                                        roiPage = new ROIPage();
                                        roiPage.PageSequence = page.PageSeq;
                                        roiPage.IsSelfEncounter = false;
                                        roiPages.Add(roiPage);
                                        //roiPagesSeqField.Add(page.PageSeq);
                                    }
                                }
                            }
                        }
                    }

                    foreach (KeyValuePair<long, bool> page in patient.PageStatus)
                    {
                        if (page.Value)
                        {
                            hpfcount++;
                        }

                    }

                    releasePatient = new ReleasedPatientDetails();
                    release.ReleasedPatients.Add(patient.Key, releasePatient.AssignRequestToReleasedPatient(patient));
                }
                    //release.Details += patient.ToXml();
                    //foreach (RequestNonHpfEncounterDetails nonHpfEncounter in patient.Attachment.GetChildren)
                    foreach (ReleasedPatientDetails releasedPatient in release.ReleasedPatients.Values)
                    {
                        foreach (RequestNonHpfDocumentDetails nonHpfDoc in releasedPatient.NonHpfDocument.GetChildren)
                        {
                                MapSelectedForReleaseSeq(nonHpfDoc.IsHPF, nonHpfDoc.SelectedForRelease.Value,
                                                            nonHpfDoc.DocumentSeq, supplementarityDocumentsSeqField, supplementalDocumentsSeqField);

                                if (nonHpfBillingTiersIds.ContainsKey(nonHpfDoc.BillingTier))
                                {
                                    nonHpfBillingTiersIds[nonHpfDoc.BillingTier] += nonHpfDoc.PageCount;
                                }
                                else
                                {
                                    nonHpfBillingTiersIds.Add(nonHpfDoc.BillingTier, nonHpfDoc.PageCount);
                                }
                        }
                        foreach (RequestAttachmentDetails tmpAttachment in releasedPatient.Attachment.GetChildren)
                        {
                            MapSelectedForReleaseSeq(tmpAttachment.IsHPF, tmpAttachment.SelectedForRelease.Value,
                                                            tmpAttachment.AttachmentSeq, supplementarityAttachmentsSeqField, supplementalAttachmentsSeqField);
                            if (nonHpfBillingTiersIds.ContainsKey(tmpAttachment.BillingTier))
                            {
                                nonHpfBillingTiersIds[tmpAttachment.BillingTier] += tmpAttachment.PageCount;
                            }
                            else
                            {
                                nonHpfBillingTiersIds.Add(tmpAttachment.BillingTier, tmpAttachment.PageCount);
                            }
                        }
                    }
                    if (release.ReleasedPatients.Count == 0)
                    {
                        if (request.ReleaseCount > 0)
                        {
                            ReleasedPatientDetails releasedPatient;
                            Application.DoEvents();

                        //Naved Commented
                            //requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);
                            foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                            {

                                releasedPatient = new ReleasedPatientDetails();
                                release.ReleasedPatients.Add(requestPatientDetails.Key, releasedPatient.AssignRequestToReleasedPatient(requestPatientDetails));
                            }
                        }
                    }
                if (release.ReleasedPatients.Count > 0)
                {
                    List<DocumentChargeDetails> previousReleaseDocumentCharges = null;                   
                    if (request.ReleaseCount > 0)
                    {
                        previousReleaseDocumentCharges = new List<DocumentChargeDetails>(GetPreviouReleaseDocumentCharges(request));
                    }
                    
                    UpdateDocumentCharges(release, nonHpfBillingTiersIds,
                                         requestorType, htBillingTiers, request.ReleaseCount > 0 ? true : false,
                                         previousReleaseDocumentCharges, hpfcount);

                    billingPayment.SaveRequestCoreCharges(release, request);
                }
                else
                {
                    release.DocumentCharges.Clear();
                    release.TotalPages = 0;
                }

                if (release.Id > 0)
                {
                    release.RequestId = request.Id;
                    
                    this.request.DraftRelease = release;
                    dsrTreeUI.ReleaseTreeModel.Request = this.request;

                    RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
                    rsp.InfoEditor.Request        = 
                    rsp.PatientInfoEditor.Request = (RequestDetails)ROIViewUtility.DeepClone(this.request);                    
                }
                
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
       
            return release;
        }

        private static IList<DocumentChargeDetails> GetPreviouReleaseDocumentCharges(RequestDetails request)
        {
            List<DocumentChargeDetails> documentChargeDetails = new List<DocumentChargeDetails>();
            SortedList<long, DocumentChargeDetails> documentCharges = new SortedList<long, DocumentChargeDetails>();
            documentChargeDetails = RequestController.Instance.RetrieveReleasedDocumentCharges(request.Id);
            foreach (DocumentChargeDetails documentCharge in documentChargeDetails)
            {
                documentCharges.Add(documentCharge.BillingTierId, documentCharge);
            }
            return documentCharges.Values;
        }

        private static DocumentChargeDetails GetDocumentChargeForBillingTier(List<DocumentChargeDetails> documentCharges, long billingTier, bool isReleased)
        {
            DocumentChargeDetails documentCharge = documentCharges.Find(delegate(DocumentChargeDetails docCharge) { return docCharge.BillingTierId == billingTier; });
            if (documentCharge != null && isReleased)
            {
                documentCharge.RemoveBaseCharge = true;
            }
            return (documentCharge == null) ? new DocumentChargeDetails() :  (DocumentChargeDetails)ROIViewUtility.DeepClone(documentCharge);
        }    

        
        internal static void UpdateDocumentCharges(ReleaseDetails release,
                                          Dictionary<long, int> nonHpfBillingTiersIds,
                                          RequestorTypeDetails requestorType,
                                          Hashtable htBillingTiers, bool isReleased,
                                          List<DocumentChargeDetails> previousReleaseDocumentCharges, int hpfcount)
        {
            //requestorType = ROIAdminController.Instance.GetRequestorType(request.RequestorType);
            //          Collection<DocumentChargeDetails> documentCharges = (Collection<DocumentChargeDetails>)ROIViewUtility.DeepClone(release.DocumentCharges);
            if (previousReleaseDocumentCharges == null)
            {
                previousReleaseDocumentCharges = new List<DocumentChargeDetails>();
            }

            release.DocumentCharges.Clear();
            DocumentChargeDetails documentCharge;
            BillingTierDetails billingTier;
            release.TotalPages = 0;
            int releasedHpfDocuments = hpfcount;
            if (releasedHpfDocuments > 0)
            {
                billingTier = (BillingTierDetails)htBillingTiers[requestorType.HpfBillingTier.Id];
                documentCharge = GetDocumentChargeForBillingTier(previousReleaseDocumentCharges, billingTier.Id, isReleased);
                documentCharge.BillingTierId = billingTier.Id;
                documentCharge.Pages = releasedHpfDocuments;
                documentCharge.IsElectronic = true;
                documentCharge.BillingTier = billingTier.Name;
                documentCharge.Copies = 1;
                //documentCharge.HasSalesTax = !(string.IsNullOrEmpty(requestorType.SalesTax))
                //                                   ? requestorType.SalesTax.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes") : false;
                documentCharge.HasSalesTax = billingTier.SalesTax.Equals("Yes") ? true : false;

                //documentCharge.TotalPages += (documentCharge.Pages * documentCharge.Copies);

                release.TotalPages = documentCharge.Pages * documentCharge.Copies;

                AddDocumentCharge(release, documentCharge);
            }

            foreach (long billingTierId in nonHpfBillingTiersIds.Keys)
            {

                billingTier = (BillingTierDetails)htBillingTiers[billingTierId];

                documentCharge = GetDocumentChargeForBillingTier(previousReleaseDocumentCharges, billingTier.Id, isReleased);
                documentCharge.BillingTierId = billingTier.Id;
                documentCharge.Pages = nonHpfBillingTiersIds[billingTierId];
                documentCharge.BillingTier = billingTier.Name;
                documentCharge.Copies = 1;
                //documentCharge.HasSalesTax = !(string.IsNullOrEmpty(requestorType.SalesTax))
                //               ? requestorType.SalesTax.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes") : false;
                documentCharge.HasSalesTax = billingTier.SalesTax.Equals("Yes") ? true : false;
                //documentCharge.TotalPages += documentCharge.Copies * documentCharge.Pages;

                release.TotalPages += documentCharge.Copies * documentCharge.Pages;
                AddDocumentCharge(release, documentCharge);
            }

            if ((hpfcount == 0) && (previousReleaseDocumentCharges != null) && (release.DocumentCharges.Count == 0))
            {
                foreach (DocumentChargeDetails documentChargeDetails in previousReleaseDocumentCharges)
                {
                    AddDocumentCharge(release, documentChargeDetails);
                    documentChargeDetails.BillingTierDetail = (BillingTierDetails)htBillingTiers[requestorType.HpfBillingTier.Id];
                    documentChargeDetails.BillingTier = documentChargeDetails.BillingTierDetail.Name;
                }
            }
        }

        private static void AddDocumentCharge(ReleaseDetails release, DocumentChargeDetails documentCharge)
        {
            if (release.DocumentCharges.Count > 0)
            {
                bool isUpdated = false;
                foreach (DocumentChargeDetails docCharge in release.DocumentCharges)
                {
                    if (docCharge.BillingTierId == documentCharge.BillingTierId)
                    {
                        docCharge.Pages += documentCharge.Pages;
                        documentCharge.TotalPages += documentCharge.Pages;
                        isUpdated = true;
                        break;
                    }
                }
                if (!isUpdated)
                {
                    release.DocumentCharges.Add(documentCharge);
                }
            }
            else
            {
                release.DocumentCharges.Add(documentCharge);
            }
        }

        private void revertButton_Click(object sender, EventArgs e)
        {
            isDirty = false;
            if (request.Id > 0)
            {
                if (dsrTreeUI.DeleteList != null)
                {
                    if ((dsrTreeUI.DeleteList.DeleteDARSupplementalPatients.Count != 0) || (dsrTreeUI.DeleteList.DeletedDocuments.Count != 0) ||
                        (dsrTreeUI.DeleteList.DeletedEncounters.Count != 0) || (dsrTreeUI.DeleteList.DeletedPages.Count != 0) ||
                        (dsrTreeUI.DeleteList.DeletedPatientList.Count != 0) || (dsrTreeUI.DeleteList.DeletedPatients.Count != 0) ||
                        (dsrTreeUI.DeleteList.DeletedVersions.Count != 0) || (dsrTreeUI.DeleteList.DeleteSupplementalAttachments.Count != 0) ||
                        (dsrTreeUI.DeleteList.DeletesupplementalDocuments.Count != 0) || (dsrTreeUI.DeleteList.DeletesupplementalPatients.Count != 0) ||
                        (dsrTreeUI.DeleteList.DeleteSupplementaryAttachments.Count != 0) || (dsrTreeUI.DeleteList.DeleteSupplementaryDocuments.Count != 0))
                    {
                        dsrTreeUI.DeleteList.Clear();
                    }
                }
                if (deletedPatients != null)
                {
                    deletedPatients.Clear();
                }
                SetData((Pane.ParentPane as RequestPatientInfoEditor).Request);
                if (request.IsOldRequest)
                {
                    RequestEvents.OnDsrChanged(Pane, new ApplicationEventArgs(false, this));
                }
                else
                {
                    RequestEvents.OnDsrChanged(Pane, new ApplicationEventArgs((showPreviousRelease || SaveBillButton.Enabled), this));
                }

            }
            else
            {
                RequestEvents.OnCancelCreateRequest(null, new ApplicationEventArgs(ROIConstants.RequestInformation, null));
            }
        }

        private void addButton_Click(object sender, EventArgs e)
        {
            if (patientRecordsViewUI.PatientRecordsTreeView.SelectedNodes.Count > 0)
            {
                IList<PageDetails> pages = new Collection<PageDetails>();
                IList<BaseRecordItem> nonHpfDocs = new Collection<BaseRecordItem>();
                IList<BaseRecordItem> attachments = new Collection<BaseRecordItem>();

                if (DeletedAttachmentSelected(patientRecordsViewUI.PatientRecordsTreeView.SelectedNodes))
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("deletedAttachment.Title");
                    string okButtonText = rm.GetString("okButton.DialogUI");
                    string messageText = rm.GetString("deletedAttachment.Message");

                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
                }

                foreach (TreeNodeAdv node in patientRecordsViewUI.PatientRecordsTreeView.SelectedNodes)
                {
                     if (node.Tag is PatientNonHpfDocument || 
                        node.Tag is NonHpfEncounterDetails || 
                        node.Tag is NonHpfDocumentDetails)
                    {

                        GetNonHpfDocuments(node, nonHpfDocs);                        
                    }
                    else if  (node.Tag is PatientAttachment ||
                         node.Tag is AttachmentEncounterDetails ||
                         node.Tag is AttachmentDetails)
                    {
                        GetAttachments(node, attachments);
                    }
                    else
                    {
                        GetPages(node, pages);
                    }
                }

                bool flag = dsrTreeUI.ReleaseTreeModel.AddPages(pages, nonHpfDocs, attachments, requestorType.NonHpfBillingTier.Id);                
                if (!flag)
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("duplicateRecordsDialog.Title");
                    string okButtonText = rm.GetString("okButton.DialogUI");
                    string messageText = rm.GetString("duplicateRecordsDialog.Message");

                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
                }
                dsrTreeUI.EnableButtons();
                dsrTreeUI.EnableFilterButtons();
            }

            dirtyDataHandler(null, null);
        }


        /// <summary>
        /// Determines if deleted records are selected to be added to DSR
        /// </summary>
        /// <param name="selectedNode"></param>
        private bool DeletedAttachmentSelected(ReadOnlyCollection<TreeNodeAdv> selectedNodes)
        {
            bool deletedAttachments = false;
            foreach (TreeNodeAdv node in selectedNodes)
            {
                if (node.Tag is AttachmentDetails)
                {
                    AttachmentDetails tmpAttachment = node.Tag as AttachmentDetails;
                    if (tmpAttachment.IsDeleted)
                    {
                        deletedAttachments = true;
                    }
                }
                
                if (deletedAttachments)
                {
                    break;
                }

                foreach (TreeNodeAdv childNode in node.Children)
                {
                    deletedAttachments = deletedAttachments || DeletedAttachmentSelected(node.Children);
                    if (deletedAttachments)
                    {
                        break;
                    }
                }

            }


            return deletedAttachments;
        }

        /// <summary>
        /// Gets NonHpf documents for selected node
        /// </summary>
        /// <param name="selectedNode"></param>
        /// <param name="nonHpfDocuments"></param>
	    private void GetNonHpfDocuments(TreeNodeAdv selectedNode, IList<BaseRecordItem> nonHpfDocuments)
        {
            if (typeof(NonHpfDocumentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
            {
                NonHpfDocumentDetails nonHpfDocument = (NonHpfDocumentDetails)selectedNode.Tag;
                if(!nonHpfDocuments.Contains(nonHpfDocument))
                {
                    nonHpfDocuments.Add(nonHpfDocument);
                }
            }

            foreach (TreeNodeAdv node in selectedNode.Children)
            {
                GetNonHpfDocuments(node, nonHpfDocuments);
            }
        }

        /// <summary>
        /// Gets attachments for selected node
        /// </summary>
        /// <param name="selectedNode"></param>
        /// <param name="tmpAttachments"></param>
        private void GetAttachments(TreeNodeAdv selectedNode, IList<BaseRecordItem> tmpAttachments)
        {
            if (typeof(AttachmentDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
            {
                AttachmentDetails tmpAttachment = (AttachmentDetails)selectedNode.Tag;
                if (!tmpAttachments.Contains(tmpAttachment) && !tmpAttachment.IsDeleted)
                {
                    tmpAttachments.Add(tmpAttachment);
                }
            }

            foreach (TreeNodeAdv node in selectedNode.Children)
            {
                GetAttachments(node, tmpAttachments);
            }
        }

        private void GetPages(TreeNodeAdv selectedNode, IList<PageDetails> pages)
        {
            if (typeof(PageDetails).IsAssignableFrom(selectedNode.Tag.GetType()))
            {
                PageDetails page = (PageDetails)selectedNode.Tag;
                if(!pages.Contains(page))
                {
                    pages.Add(selectedNode.Tag as PageDetails);
                }
            }

            foreach (TreeNodeAdv node in selectedNode.Children)
            {
                GetPages(node, pages);
            }
        }

        private void tree_SelectionChanged(object sender, EventArgs e)
        {
            patientRecordsViewUI.AddButton.Enabled = patientRecordsViewUI.PatientRecordsTreeView.SelectedNodes.Count > 0;
        }


        private void addAllButton_Click(object sender, EventArgs e)
        {
            IList<PageDetails> pages = new Collection<PageDetails>();
            IList<BaseRecordItem> nonHpfDocs = new Collection<BaseRecordItem>();
            IList<BaseRecordItem> attachments = new Collection<BaseRecordItem>();

            foreach (TreeNodeAdv node in patientRecordsViewUI.PatientRecordsTreeView.Root.Children)
            {
                if (node.Tag is PatientGlobalDocument)
                {
                    GetPages(node, pages);
                }
                if (node.Tag is DocumentType)
                {
                    GetPages(node, pages);
                }
                if (node.Tag is EncounterDetails)
                {
                    GetPages(node, pages);
                }
                if (node.Tag is PatientNonHpfDocument)
                {
                    GetNonHpfDocuments(node, nonHpfDocs);
                } 
                if (node.Tag is PatientAttachment)
                {
                    GetAttachments(node, attachments);
                }
            }

            bool flag = dsrTreeUI.ReleaseTreeModel.AddPages(pages, nonHpfDocs, attachments, requestorType.NonHpfBillingTier.Id);
            if (!flag)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                string titleText = rm.GetString("duplicateRecordsDialog.Title");
                string okButtonText = rm.GetString("okButton.DialogUI");
                string messageText = rm.GetString("duplicateRecordsDialog.Message");

                ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
            }
            dsrTreeUI.EnableButtons();
            dsrTreeUI.EnableFilterButtons();

            dirtyDataHandler(null, null);
        }

        /// <summary>
        /// Shows Add Patient dialog to add a patient to this new request
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void addPatientButton_Click(object sender, EventArgs e)
        {
            ShowAddPatientDialog();   
        }

        /// <summary>
        /// Shows Add Patient dialog to add another patient to this request
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void addAnotherPatientButton_Click(object sender, EventArgs e)
        {
            ShowAddPatientDialog();
        }

        /// <summary>
        /// Shows Add patient Dialog
        /// </summary>
        private void ShowAddPatientDialog()
        {
            EventHandler addPatientHandler = new EventHandler(Process_AddPatient);
            EventHandler cancelHandler = new EventHandler(Process_CancelAddPatientDialog);

            AddPatientUI addPatientUI = new AddPatientUI(addPatientHandler, cancelHandler, Pane, request.Requestor.IsPatientRequestor);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("addpatient.titlebar.text"), addPatientUI);
            form.FormClosing += delegate { addPatientUI.CleanUp(); };

            form.ShowDialog(this);
            
        }

        /// <summary>
        /// To view or edit the selected patientï¿½s profile.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void viewEditPatientButton_Click(object sender, EventArgs e)
        {

            EventHandler savePatientHandler = new EventHandler(Process_SavePatient);
            EventHandler cancelHandler      = new EventHandler(Process_CancelViewEditPatientDialog);

            ViewEditPatientUI viewPatientUI = new ViewEditPatientUI(savePatientHandler, cancelHandler, Pane);
            RequestPatientDetails requestPatient = patientListUI.SelectedPatient;
            PatientDetails patient = new PatientDetails();
            patient.Id = requestPatient.Id;
            patient.IsHpf = requestPatient.IsHpf;
            patient.MRN = requestPatient.MRN;
            patient.FacilityCode = requestPatient.FacilityCode;

            viewPatientUI.InitPatientInfoTabPage(patient);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("vieweditpatient.titlebar.text"), viewPatientUI);
            form.FormClosing += delegate { viewPatientUI.CleanUp() ; };
            form.ShowDialog(this);
        }

        /// <summary>
        /// Handler is used to save tha patient's information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SavePatient(object sender, EventArgs e)
        {
            PatientDetails patient = (PatientDetails)((BaseEventArgs)e).Info;
            patientInfo = (PatientDetails)ROIViewUtility.DeepClone(patient);
            ///CR#374906 - Start
            patient = PatientController.Instance.RetrieveSuppmentalDocuments(patientInfo);
            patient = PatientController.Instance.RetrieveSuppmentalAttachments(patientInfo);
            ///End
            patientRecordsViewUI.SetData(patient);

            if(dsrTreeUI.ReleaseTreeModel.Request.Patients.ContainsKey(patient.Key))
            {
                RequestPatientDetails requestPatient = dsrTreeUI.ReleaseTreeModel.Request.Patients[patient.Key];
                UpdatePatientDetails(requestPatient, patient);
                dsrTreeUI.ReleaseTreeModel.Request.Patients[requestPatient.Key] = requestPatient;
                patientListUI.UpdatePatient(requestPatient);
                dirtyDataHandler(null, null);
            }
        }

        /// <summary>
        /// Update RequestPatientDetails object after saving the supplemental patient
        /// </summary>
        /// <param name="pateint"></param>
        private static void UpdatePatientDetails(RequestPatientDetails requestPatient, PatientDetails patient)
        {
            requestPatient.Id = patient.Id;
            requestPatient.FullName = patient.FullName;
            //CR#381298
            requestPatient.FirstName = patient.FirstName;
            requestPatient.LastName = patient.LastName;
            requestPatient.Gender = (patient.Gender != McK.EIG.ROI.Client.Base.Model.Gender.None)
                                ? EnumUtilities.GetDescription(patient.Gender)
                                : string.Empty;
            requestPatient.MRN = patient.MRN;
            requestPatient.EPN = patient.EPN;
            requestPatient.SSN = patient.SSN;
            requestPatient.DOB = patient.DOB;
            requestPatient.FacilityCode = patient.FacilityCode;
            requestPatient.IsHpf = patient.IsHpf;
            requestPatient.IsVip = patient.IsVip;
            requestPatient.IsLockedPatient = patient.PatientLocked;
            requestPatient.EncounterLocked = patient.EncounterLocked;
        }


        /// <summary>
        /// Close the View Edit Patient dialog
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CancelViewEditPatientDialog(object sender, EventArgs e)
        {
            ((ViewEditPatientUI)sender).ParentForm.Close();
        }
        
        /// <summary>
        /// Remove the selected patient from this request. 
        /// If you have released records for the selected patient they cannot be removed. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void removePatientButton_Click(object sender, EventArgs e)
        {
            isPatientAddedRemoved = true;
            if (deletedPatients == null)
            {
                deletedPatients = new List<long>();
            }
            if (deletedDARSupplementalPatients == null)
            {
                deletedDARSupplementalPatients = new List<long>();
            }
            RequestPatientDetails requestPatientDetails = patientListUI.RemovePatient();
            dsrTreeUI.ReleaseTreeModel.Request.Patients.Remove(requestPatientDetails.Key);
            if (requestPatientDetails.PatientSeq != 0)
            {
                if (requestPatientDetails.IsHpf)
                {
                    deletedPatients.Add(requestPatientDetails.PatientSeq);
                }
                else
                {
                    deletedDARSupplementalPatients.Add(requestPatientDetails.PatientSeq);
                }
            }
            dsrTreeUI.ReleaseTreeModel.Refresh();
            dsrTreeUI.EnableButtons();
            dsrTreeUI.EnableFilterButtons();
            
            if (patientListUI.Grid.Items.Count == 0)
            {
                patientRecordsViewUI.PatientRecordsTreeView.Model = null;
                patientRecordsViewUI.PatientRecordsTreeView.Refresh();
                patientRecordsViewUI.Enabled = false;
            }

            dirtyDataHandler(null, null);
            patientInfoActionUI.RevertButton.Enabled = true;
            isPatientAddedRemoved = true;
        }

        /// <summary>
        /// Enables the buttons
        /// </summary>
        /// <param name="enable"></param>
        public void EnableButtons(bool enable)
        {
            if (request != null && request.IsOldRequest)
            {
                isDirty = false;
                patientInfoActionUI.SaveButton.Enabled = false;
                patientInfoActionUI.RevertButton.Enabled = enable;
            }
            else
            {
                patientInfoActionUI.SaveButton.Enabled = enable;
                patientInfoActionUI.RevertButton.Enabled = enable;
            }
           // patientInfoActionUI.SaveBillButton.Enabled = true && dsrTreeUI.HasRecordsChecked;
        }

        /*
        private BackgroundWorker bgWorker;
        private void InitializeBackgoundWorker()
        {
            bgWorker = new BackgroundWorker();
            bgWorker.DoWork += new DoWorkEventHandler(bgWorker_DoWork);
            bgWorker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(bgWorker_RunWorkerCompleted);
            //bgWorker.ProgressChanged += new ProgressChangedEventHandler(bgWorker_ProgressChanged);
        }

        private void bgWorker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            PatientDetails selectedPatient = e.Result as PatientDetails;
            if (selectedPatient.Encounters.Count > 0 || 
                selectedPatient.GlobalDocument.Documents.Count > 0 || 
                selectedPatient.NonHpfDocuments.GetChildren.Count > 0)
            {
                //FIXED for CR 291,650
                patientRecordsViewUI.Enabled = true;
                patientRecordsViewUI.SelectedEncounters = new List<string>();
                foreach (EncounterDetails encounter in selectedPatient.Encounters)
                {
                    patientRecordsViewUI.SelectedEncounters.Add(encounter.EncounterId);
                }
            }
            else
            {
                patientRecordsViewUI.Enabled = false;
            }

            patientRecordsViewUI.SetData(selectedPatient);
            patientRecordsViewUI.EnableEvents();
            patientRecordsViewUI.EnableEditDocumentButton = false;
            patientRecordsViewUI.EnableNewDocumentButton = false;

            ROIViewUtility.MarkBusy(false);

            loadingCircle.Active = false;
            loadingCircle.Visible = false;

            EnableButtons(true);

            //Disable the button if the patient has had documents released.
            patientListUI.RemovePatientButton.Enabled = !dsrTreeUI.ReleaseTreeModel.Request.Patients[selectedPatient.Key].IsReleased;
        }

        private PatientDetails DoLongWork(RequestPatientDetails selectedPatient)
        {
            PatientDetails patientDetails = new PatientDetails();
            patientDetails.Id       = selectedPatient.Id;
            patientDetails.MRN      = selectedPatient.MRN;
            patientDetails.Facility = selectedPatient.Facility;
            patientDetails.IsHpf    = selectedPatient.IsHpf;

            try
            {
                ROIViewUtility.MarkBusy(true);
                if (selectedPatient.IsHpf)
                {
                    //Retrieves only patient Infomation
                    patientDetails = PatientController.Instance.RetrieveHpfPatient(patientDetails.MRN, patientDetails.Facility);
                    //Retrieves only patient's Hpf Docs
                    patientDetails = PatientController.Instance.RetrieveHpfDocuments(patientDetails);

                    //Retrieves only patient's NonHpf Docs
                    patientDetails = PatientController.Instance.RetrieveSupplementalDocuments(patientDetails);
                }
                else
                {
                    //Retrieves only patient Infomation
                    patientDetails = PatientController.Instance.RetrieveSupplementalInfo(patientDetails.Id);
                }

                
                //EnableButtons(false);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return patientDetails;
        }

        private void bgWorker_DoWork(object sender, DoWorkEventArgs e)
        {
            e.Result = DoLongWork((RequestPatientDetails)e.Argument);
        }
        */
        
        private void Process_PatientSelected(object sender, EventArgs e)
        {
            //loadingCircle.Visible = true;
            //loadingCircle.Active = true;

            //InitializeBackgoundWorker();
            RequestPatientDetails selectedPatient = (RequestPatientDetails) ((BaseEventArgs)e).Info;
            
            patientRecordsViewUI.Enabled = false;

            //bgWorker.RunWorkerAsync(selectedPatient);

            PatientDetails patient = new PatientDetails();
            patient.Id = selectedPatient.Id;
            patient.MRN = selectedPatient.MRN;
            patient.FacilityCode = selectedPatient.FacilityCode;
            patient.IsHpf = selectedPatient.IsHpf;

            string key = patient.Key;

            bool isValidHpfPatient = true;
            try
            {
                ROIViewUtility.MarkBusy(true);
                if (selectedPatient.IsHpf)
                {
                    //Retrieves only patient Infomation
                    patient = PatientController.Instance.RetrieveHpfPatient(patient.MRN, patient.FacilityCode);
                    if (patient.MRN == null || patient.FacilityCode == null)
                    {
                        isValidHpfPatient = false;
                        hasFirstRowSelection = true;
                        throw new ROIException(ROIErrorCodes.InvalidHpfPatientAssociated);
                    }                    
                    patient = PatientController.Instance.RetrieveHpfDocuments(patient);
                    //shahm
                    patient = PatientController.Instance.RetrieveSuppmentarityDocuments(patient);
                    patient = PatientController.Instance.RetrieveSuppmentarityAttachments(patient);
                    PatientDetailsCache.AddData(patient.MRN + patient.facilityCode, patient);
                }
                else
                {   
                    patient = PatientController.Instance.RetrieveSupplementalPatient(patient.Id, false);
                    patient = PatientController.Instance.RetrieveSuppmentalDocuments(patient);
                    patient = PatientController.Instance.RetrieveSuppmentalAttachments(patient);
                    isValidHpfPatient = true;
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {

                if (!isValidHpfPatient && isPatientAddedRemoved)
                {
                    if (dsrTreeUI.ReleaseTreeModel.Request != null &&
                        dsrTreeUI.ReleaseTreeModel.Request.Patients.ContainsKey(key))
                    {
                        dsrTreeUI.ReleaseTreeModel.Request.Patients.Remove(key);
                        SetData(dsrTreeUI.ReleaseTreeModel.Request);
                        isDirty = true;
                        EnableButtons(true);
                    }
                }
                else
                {

                    if (patient.Encounters.Count > 0 ||
                        patient.GlobalDocument.Documents.Count > 0 ||
                        patient.NonHpfDocuments.GetChildren.Count > 0 ||
                        patient.Attachments.GetChildren.Count > 0)
                    {
                        //FIXED for CR 291,650
                        patientRecordsViewUI.Enabled = true;
                        patientRecordsViewUI.AddAllButton.Enabled = true;
                        patientRecordsViewUI.SelectedEncounters = new List<string>();
                        foreach (EncounterDetails encounter in patient.Encounters)
                        {
                            patientRecordsViewUI.SelectedEncounters.Add(encounter.EncounterId + 
                                                                        ROIConstants.Delimiter + 
                                                                        encounter.Facility);
                        }
                    }
                    else
                    {
                        patientRecordsViewUI.Enabled = true;
                    }

                    patientRecordsViewUI.EnableEvents();
                    patientInfo = (PatientDetails)ROIViewUtility.DeepClone(patient);
                    patientRecordsViewUI.SetData(patient);


                    patientRecordsViewUI.EnableEditDocumentButton = false;
                    //patientRecordsViewUI.EnableNewDocumentButton = false;

                    //loadingCircle.Active = false;
                    //loadingCircle.Visible = false;
                    if (isDirty)
                    {
                        EnableButtons(true);
                    }

                    //Disable the button if the patient has had documents released.
                    if (dsrTreeUI.ReleaseTreeModel.Request != null)
                    {
                        if (dsrTreeUI.ReleaseTreeModel.Request.Patients.ContainsKey(patient.Key))
                        {
                            dsrTreeUI.ReleaseTreeModel.Request.Patients[patient.Key].Id = patient.Id;
                            patientListUI.RemovePatientButton.Enabled = !dsrTreeUI.ReleaseTreeModel.Request.Patients[patient.Key].IsReleased;
                        }
                    }

                    patientListUI.AddPatientButton.Visible = (patientListUI.Grid.Rows.Count == 0);
                    patientListUI.ViewEditButton.Visible = (patientListUI.Grid.SelectedRows.Count == 1);
                    patientListUI.AddAnotherPatientButton.Visible = (patientListUI.Grid.Rows.Count > 0);
                }

                ROIViewUtility.MarkBusy(false);
            }
        }

        /// <summary>
        ///This is used to add the selected patient to the request
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_AddPatient(object sender, EventArgs e)
        {
            isPatientAddedRemoved = true;
            Collection<PatientDetails> selectedPatients = (Collection<PatientDetails>)((BaseEventArgs)e).Info;
            if (request.Requestor.IsPatientRequestor && !UserData.Instance.EpnEnabled)
            {
                FindMisMatch(selectedPatients);
            }
            else
            {
                AddSelectedPatientsToRequest(selectedPatients);
            }
            isDirty = true;
            dirtyDataHandler(null, null);
            isPatientAddedRemoved = false;
        }

        /// <summary>
        /// Show Mismatch dialog when selected patients do not to seem to match
        /// </summary>
        /// <param name="potentialMismatchPatients"></param>
        /// <param name="selectedPatients"></param>
        private void ShowMismatchPatientUI(SortedList<string, PatientDetails> potentialMismatchPatients, Collection<PatientDetails> selectedPatients)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            MismatchPatientUI mismatchPatientUI = new MismatchPatientUI(Pane);


            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                         rm.GetString("title.MismatchPatientUI"),
                                                         mismatchPatientUI);

            mismatchPatientUI.SetData(selectedPatients, potentialMismatchPatients);

            DialogResult result = dialog.ShowDialog(this);
            dialog.Close();

            if (result == DialogResult.OK)
            {
                AddSelectedPatientsToRequest(mismatchPatientUI.SelectedPatients);
            }
        }

        /// <summary>
        /// Finds the matching criteria
        /// </summary>
        /// <param name="selectedPatients"></param>
        private void FindMisMatch(Collection<PatientDetails> selectedPatients)
        {
            SortedList<string, PatientDetails> mismatchPatients = new SortedList<string, PatientDetails>();

            if (selectedPatients.Count > 1)
            {
                PatientDetails basePatient = selectedPatients[0];

                for (int index = 1; index < selectedPatients.Count; index++)
                {
                    if (PatientDetails.FieldMatchComparer.Compare(basePatient, selectedPatients[index]) < 2)
                    {
                        mismatchPatients.Add(selectedPatients[index].Key, selectedPatients[index]);
                    }
                }
            }

            if (mismatchPatients.Count > 0)
            {
                ShowMismatchPatientUI(mismatchPatients, selectedPatients);
            }
            else
            {
                AddSelectedPatientsToRequest(selectedPatients);
            }
        }

        /// <summary>
        /// Add the selected patients to the request
        /// </summary>
        /// <param name="selectedPatients"></param>
        private void AddSelectedPatientsToRequest(Collection<PatientDetails> selectedPatients)
        {
            RequestPatientDetails requestPatient = null;
            bool hasPatients = (patientListUI.Grid.SelectedRows.Count > 0);

            foreach (PatientDetails patient in selectedPatients)
            {
                requestPatient = dsrTreeUI.ReleaseTreeModel.Request.AddPatient(patient);
                patientListUI.AddPatient(requestPatient);
            }

            if (requestPatient == null)
            {
                patientListUI.Grid.SelectRow(0, false);
            }

            if (requestPatient != null && !hasPatients)
            {
                patientListUI.Grid.SelectItem(requestPatient, true);

                patientListUI.AddAnotherPatientButton.Visible = true;
                patientListUI.RemovePatientButton.Visible = true;
                patientListUI.ViewEditButton.Visible = true;
            }
            else
            {
                //Recently added patient will be seelcted
                patientListUI.Grid.SelectItem(requestPatient);
            }
        }

        /// <summary>
        /// Close the Add Patient Dialog
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CancelAddPatientDialog(object sender, EventArgs e)
        {
            ((AddPatientUI)sender).ParentForm.Close();
        }

        internal void PrePopulate(object data)
        {
            patientRecordsViewUI.PopulateRecordViews(data);

            billingTiers = BillingAdminController.Instance.RetrieveAllBillingTiers();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            BillingTierDetails forSelect = new BillingTierDetails();
            forSelect.Id = 0;
            forSelect.Name = rm.GetString("pleaseSelectCombo");
            billingTiers.Insert(0, forSelect);

            htBillingTiers = new Hashtable(billingTiers.Count);
            foreach (BillingTierDetails billingTier in billingTiers)
            {
                htBillingTiers.Add(billingTier.Id, billingTier);
            }
        }

        /// <summary>
        /// Mark the released records
        /// </summary>
        public void MarkAsRelease()
        {
            request = (RequestDetails)ROIViewUtility.DeepClone(((RequestPatientInfoEditor)Pane.ParentPane).Request);
            dsrTreeUI.SetData(request);
            MarkSupplementalDocumentsAsReleased();

            if (patientListUI.Grid.SelectedRows.Count > 0)
            {
                RequestPatientDetails selectedPatient = (RequestPatientDetails)patientListUI.Grid.SelectedRows[0].DataBoundItem;
                if (request.Patients.ContainsKey(selectedPatient.Key))
                {
                    patientListUI.RemovePatientButton.Enabled = !request.Patients[selectedPatient.Key].IsReleased;
                }
            }

            showPreviousRelease = true;
            EnableButtons(false);
            SaveBillButton.Enabled = dsrTreeUI.HasRecordsChecked && (dsrTreeUI.DeletedPageCount==0);
            isDirty = false;
        }

        public void UpdateRequestInfo(RequestDetails requestInfo)
        {
            request = (RequestDetails)ROIViewUtility.DeepClone(requestInfo);
            dsrTreeUI.ReleaseTreeModel.Request = request;
            dsrTreeUI.ReleaseTreeModel.Refresh();

            if (requestorType.Id != request.RequestorType)
            {
                requestorType = ROIAdminController.Instance.GetRequestorType(request.RequestorType);
            }
        }

        /// <summary>
        /// Marks the supplemental documents as relased once the request is relased
        /// </summary>
        private void MarkSupplementalDocumentsAsReleased()
        {
            try
            {

                PatientDetails selectedPatient = patientRecordsViewUI.patTreeModel.PatientInfo;
                if (selectedPatient.NonHpfDocuments.GetChildren.Count == 0) return;

                if (selectedPatient.IsHpf)
                {
//                    selectedPatient = PatientController.Instance.RetrieveSupplementalDocuments(selectedPatient);
                }
                else
                {
//                    selectedPatient = PatientController.Instance.RetrieveSupplementalInfo(selectedPatient.Id);
                }

                patientRecordsViewUI.patTreeModel.PatientInfo = selectedPatient;
                patientRecordsViewUI.patTreeModel.Refresh();
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// Non hpf document save.
        /// </summary>
        /// <returns></returns>
        public bool SaveSupplemental()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                PatientDetails patient = patientRecordsViewUI.patTreeModel.PatientInfo;

                //Prepare audit message uses the base Patient object (not the local patient object above) to compare the
                //old values with the modified values to generate proper audit message.
                patient.AuditMessage = PatientNonHpfDocument.PrepareAuditMessage(patientRecordsViewUI.ModifiedNonHpfDocument,
                                                                                  Patient.NonHpfDocuments);

                if (patient.IsHpf && patient.Id == 0)
                {
                    patient = PatientController.Instance.RetrieveSuppmentarityDocuments(patient);
//                    patient.Id = PatientController.Instance.CreateSupplemental(patient);
                    if (dsrTreeUI.ReleaseTreeModel.Request.Patients.ContainsKey(patient.Key))
                    {
                        UpdatePatientDetails(dsrTreeUI.ReleaseTreeModel.Request.Patients[patient.Key], 
                                             patient);
                    }
                }
                else
                {
                    patient = PatientController.Instance.RetrieveSuppmentalDocuments(patient);
                }

                patientRecordsViewUI.patTreeModel.PatientInfo = patient;
                patientInfo = patient;
                patientRecordsViewUI.patTreeModel.Refresh();
                patientRecordsViewUI.EnableEditDocumentButton = false;
                dirtyDataHandler(null, null);
            }
            catch (ROIException cause)
            {
              ROIViewUtility.Handle(Context, cause);
              return false;
            }
            finally
            {
                patientRecordsViewUI.SetData(ROIViewUtility.DeepClone(patientInfo));
                ROIViewUtility.MarkBusy(false);
            }
            return true;
        }

        public bool SaveAttachmentSupplemental()
        {
            PatientDetails patient = patientRecordsViewUI.patTreeModel.PatientInfo;
            try
            {
                ROIViewUtility.MarkBusy(true);

                patient.AuditMessage = PatientAttachment.PrepareAuditMessage(patientRecordsViewUI.ModifiedAttachment);
                patient.ModifiedAttachment = patientRecordsViewUI.ModifiedAttachment;

                if (patient.IsHpf && patient.Id == 0)
                {
                    patient = PatientController.Instance.RetrieveSuppmentarityAttachments(patient);
                    if (dsrTreeUI.ReleaseTreeModel.Request.Patients.ContainsKey(patient.Key))
                    {
                        UpdatePatientDetails(dsrTreeUI.ReleaseTreeModel.Request.Patients[patient.Key],
                                             patient);
                    }
                }
                else
                {
                    patient = PatientController.Instance.RetrieveSuppmentalAttachments(patient);
                }

                patientRecordsViewUI.patTreeModel.PatientInfo = patient;
                patientInfo = patient;
                patientRecordsViewUI.patTreeModel.Refresh();
                patientRecordsViewUI.EnableEditDocumentButton = false;
                dirtyDataHandler(null, null);
            }
            finally
            {
                patient.ModifiedAttachment = null;
                patientRecordsViewUI.SetData(ROIViewUtility.DeepClone(patientInfo));
                ROIViewUtility.MarkBusy(false);
            }
            return true;
        }
		

		public override void SetAcceptButton()
        {
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = patientInfoActionUI.SaveButton;
                patientListUI.AddPatientButton.Focus();
            }
        }
		
        public void UnsubscribePatientSelection()
        {
            patientListUI.DisableEvents();
        }

        private void ViewAuthReqButton_Click(object sender, System.EventArgs e)
        {
            WinDocumentViewer viewer = new WinDocumentViewer();
            IList<PageDetails> pages = new List<PageDetails>();
            //request.AuthRequest = "IMAGES1   05980700001";
            string IMNetIds = PatientController.Instance.RetrieveImnetsByImnet(request.AuthRequest.Trim());
            //CR# 356,927
            DocumentDetails document = new DocumentDetails();
            if (null != request.AuthRequestDocumentDateTime)
            {
                document.DocumentDateTime = Convert.ToDateTime(request.AuthRequestDocumentDateTime, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            document.DocumentType = new DocumentType();
            document.DocumentType.Subtitle = request.AuthRequestSubtitle;
            document.DocumentType.Name = request.AuthRequestDocumentName;

            VersionDetails version = new VersionDetails();
            version.Parent = document;

            int index = 0;
            PageDetails page; 
            foreach (string IMNetId in IMNetIds.Split(','))
            {
                page = new PageDetails(IMNetId.Trim(), ++index, 1);
                page.Parent = version;
                pages.Add(page);
            }
            //DM-133 authorization request page navigation changes
            viewer.AuthReq(true);
            viewer.ViewPages(pages, request.Id);
        }

        private void MapSelectedForReleaseSeq(bool isHpf, bool selectedForRelease, long seq, List<long> hpfSeqList, List<long> nonHpfSeqList) 
        {
            if (selectedForRelease)
            {
                if (isHpf)
                {
                    hpfSeqList.Add(seq);
                }
                else
                {
                    nonHpfSeqList.Add(seq);
                }
            }
        }

        /// <summary>
        /// Holds patient info object.
        /// </summary>
        public PatientDetails Patient
        {
            get { return patientInfo; }
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            if (!IsAllowed(ROISecurityRights.ROIModifyRequest))
            {
                patientInfoActionUI.Enabled = false;
                patientRecordsViewUI.Enabled = false;
                dsrTreeUI.Enabled = false;
                patientListUI.AddPatientButton.Enabled =  patientListUI.AddAnotherPatientButton.Enabled = 
                                                          patientListUI.RemovePatientButton.Enabled = false;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets true if dirty data available.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set
            {
                isDirty = value;
            }
        }

        public Collection<BillingTierDetails> BillingTiers
        {
            get { return billingTiers; }
        }

        public Button SaveBillButton
        {
            get { return patientInfoActionUI.SaveBillButton; }
        }

        public Button SaveButton
        {
            get { return patientInfoActionUI.SaveButton; }
        }

        private bool showPreviousRelease;
        public bool ShowPreviousRelease
        {
            get { return showPreviousRelease; }
            set { showPreviousRelease = value; }
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return patientInfoActionUI;
        }

        #endregion

    }
}
