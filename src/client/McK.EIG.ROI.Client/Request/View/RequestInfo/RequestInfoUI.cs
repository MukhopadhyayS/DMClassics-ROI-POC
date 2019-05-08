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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;
using McK.EIG.ROI.Client.Request.View.BillingPayment;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    /// <summary>
    /// This class holds Request Information UI
    /// </summary>
    public partial class RequestInfoUI : ROIBaseUI, IFooterProvider  
    {

        #region Fields

        private Log log = LogFactory.GetLogger(typeof(RequestInfoUI));

        private const string SelectOption = "  /  /    ";

        //Delete dialog 
        private const string DeleteRequestDialogMessageKey          = "A501";
        private const string DeleteRequestDialogTitle               = "dialog.confirm";
        private const string DeleteRequestDialogOkButton            = "deleteButton";
        private const string DeleteRequestDialogCancelButton        = "dontDeleteButton";
        private const string DeleteRequestDialogOkButtonToolTip     = "DeleteRequestDialog.OkButton";
        private const string DeleteRequestDialogCancelButtonToolTip = "DeleteRequestDialog.CancelButton";

        private const string OverrideRequestInfoDialogTitle               = "OverrideRequestInfoDialog.Title";
        private const string OverrideRequestInfoDialogMessage             = "OverrideRequestInfoDialog.Message";
        private const string OverrideRequestInfoDialogOkButton            = "OverrideRequestInfoDialog.OkButton";
        private const string OverrideRequestInfoDialogOkButtonToolTip     = "OverrideRequestInfoDialog.OkButtonToolTip";
        private const string OverrideRequestInfoDialogCancelButton        = "OverrideRequestInfoDialog.CancelButton";
        private const string OverrideRequestInfoDialogCancelButtonToolTip = "OverrideRequestInfoDialog.CancelButtonToolTip";

        private const string InactiveRequestorDialogTitle = "InactiveRequestorDialog.Title";
        private const string InactiveRequestorDialogMessage = "InactiveRequestorDialog.Message";
        private const string InactiveRequestorDialogOkButton = "InactiveRequestorDialog.OkButton";
        private const string InactiveRequestorDialogOkButtonToolTip = "InactiveRequestorDialog.OkButton";

        private const string CancelDeniedRequestDialogTitile          = "CancelDeniedRequestDialog.Title";
        private const string CancelDeniedReuestDialogMessage          = "CancelDeniedRequestDialog.Message";
        private const string CancelDeniedRequestDialogOkButton        = "CancelDeniedRequestDialog.OkButton";
        private const string CancelDeniedRequestDialogOkButtonToolTip = "CancelDeniedRequestDialog.OkButton";

        private const string ContinueButton = "continueButton";
        private const string NoneSelected = "noneSelected";

        private const string WriteOffEventMessage = "A credit adjustment of {0} was applied to write-off the balance due.";
        private const string ReverseWriteOffEventMessage = "A {0} adjustment of {1} was entered. Reason: {2}";
        private const string AuthReceivedStausChanged = "Status updated to Logged from Auth-Received";
        private const string AuthReceivedDenyStausChanged = "Status updated to Logged from Auth-Received Denied";

        
        private RequestInfoActionUI requestInfoActionUI;
        private SelectRequestorUI selectRequestor;

        private EventHandler dirtyDataHandler;

        private bool isDirty;
        private bool changedRequestorIsPatient;
        private bool requestorChanged;
        private bool isAuthReceived;
        private bool isAuthReceivedDenied;
        private Collection<ReasonDetails> reasonDetails;

        private RequestDetails request;

        //private bool isLoaded;
        
        #endregion

        #region Constructor

        public RequestInfoUI()
        {
            InitializeComponent();

            dirtyDataHandler             = new EventHandler(MarkDirty);

            receiptDateTimePicker.DropDownAlign = LeftRightAlignment.Right; 
            CreateActionUI();
        }

        #endregion
       
        #region IFooterProvider Members

        /// <summary>
        /// Sets the footerUI in the pane.
        /// </summary>
        /// <returns></returns>
        public Control RetrieveFooterUI()
        {
            return requestInfoActionUI;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Sets the actionUI for the footer.
        /// </summary>
        private void CreateActionUI()
        {
            requestInfoActionUI = new RequestInfoActionUI();

            requestInfoActionUI.SaveButton.Click   += new EventHandler(saveButton_Click);
            requestInfoActionUI.RevertButton.Click += new EventHandler(revertButton_Click);
            requestInfoActionUI.DeleteButton.Click += new EventHandler(deleteButton_Click);
            requestInfoActionUI.ViewAuthRequestButton.Click += new EventHandler(ViewAuthReqButton_Click);

            EnableButtons(false);
        }

        /// <summary>
        /// Locallizes all controls in the UI
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, idLabel);
            SetLabel(rm, reasonLabel);
            SetLabel(rm, receiptDateLabel);
            SetLabel(rm, dateCreatedLabel);
            SetLabel(rm, lastUpdatedLabel);
            SetLabel(rm, updatedByLabel);            

            SetLabel(rm, reasonGroupBox);
            SetLabel(rm, currentStatusLabel);
            SetLabel(rm, currentReasonLabel);
            SetLabel(rm, updateReqStatusButton);

            SetLabel(rm, requestorGroupBox);
            SetLabel(rm, requestorNameLabel);
            SetLabel(rm, requestorTypeLabel);
            SetLabel(rm, selectReqButton);
            SetLabel(rm, viewEditRequestorButton);
            SetLabel(rm, changeRequestorButton);

            SetLabel(rm, contactInfoGroupBox);
            SetLabel(rm, requestorHomePhoneLabel);
            SetLabel(rm, requestorWorkPhoneLabel);
            SetLabel(rm, cellPhoneLabel);
            SetLabel(rm, faxLabel);
            SetLabel(rm, contactNameLabel);
            SetLabel(rm, contactPhoneLabel);
            SetLabel(rm, requestInfoActionUI.ViewAuthRequestButton);
            SetLabel(rm, requestInfoActionUI.SaveButton);
            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, updateReqStatusButton);
            SetTooltip(rm, toolTip, selectReqButton);
            SetTooltip(rm, toolTip, viewEditRequestorButton);
            SetTooltip(rm, toolTip, changeRequestorButton);
            SetTooltip(rm, toolTip, homePhoneTextBox);
            SetTooltip(rm, toolTip, workPhoneTextBox);
            SetTooltip(rm, toolTip, cellPhoneTextBox);
            SetTooltip(rm, toolTip, faxTextBox);
            SetTooltip(rm, toolTip, contactNameTextBox);
            SetTooltip(rm, toolTip, contactPhoneTextBox);
            SetTooltip(rm, toolTip, requestInfoActionUI.DeleteButton);
            SetTooltip(rm, toolTip, requestInfoActionUI.ViewAuthRequestButton);


            requestInfoActionUI.Localize();
            SetReceiptDateNullValue();
        }
        /// <summary>
        /// Page marks as dirty, if user does any changes.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void MarkDirty(object sender, EventArgs e)
        {
            isDirty = true;

            if ((reasonCombo.SelectedIndex == 0)
                || (receiptDateTimePicker.Value.ToString().Trim() == SelectOption.Trim())
                || (request.Requestor == null))
            {
                requestInfoActionUI.SaveButton.Enabled = false;
                requestInfoActionUI.RevertButton.Enabled = true;
            }
            else
            {
                if (request.Requestor.Id != 0 && request.Status.ToString() != "Unknown")
                {
                    EnableButtons(true);
                }
            }
            if (request.Status.ToString() == "Unknown")
            {
                isDirty = false;
            }
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (request == null || request.Id == 0)
            {
                if (control.Name == "revertButton")
                {
                    return "cancelButton." + Pane.GetType().Name;
                }
            }

            return control.Name + "." + Pane.GetType().Name;
        }

        /// <summary>
        /// Method to display the icon with inline error.
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.RequestRequestorNameEmpty : //fall through;
                case ROIErrorCodes.RequestReasonEmpty        : return reasonCombo;                
                case ROIErrorCodes.RequestCurrentReasonEmpty : return currentReasonValueLabel;
                case ROIErrorCodes.InvalidHomePhone          : return homePhoneTextBox;
                case ROIErrorCodes.InvalidWorkPhone          : return workPhoneTextBox;
                case ROIErrorCodes.InvalidCellPhone          : return cellPhoneTextBox;
                case ROIErrorCodes.InvalidFax                : return faxTextBox;
                case ROIErrorCodes.InvalidContactName        : return contactNameTextBox;
                case ROIErrorCodes.InvalidContactPhone       : return contactPhoneTextBox;
                case ROIErrorCodes.InvalidDate               : return receiptDateTimePicker;
                case ROIErrorCodes.RequestInvalidReceiptDate : return receiptDateTimePicker;
                case ROIErrorCodes.RequestReceiptDateEmpty   : return receiptDateTimePicker;
                case ROIErrorCodes.InvalidDateValue          : return receiptDateTimePicker;
            }
            return null;
        }

        /// <summary>
        /// It sets the pane which holds the Request information UI
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            requestInfoActionUI.SetPane(pane);
            requestInfoActionUI.SetExecutionContext(Context);
        }

        /// <summary>
        /// Sets the data in controls.
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
           
            request = (RequestDetails)ROIViewUtility.DeepClone(data);
            if (request.Id == 0)
            {
                SetNewRequestInfo(request, rm);
                requestInfoActionUI.RevertButton.Enabled = true;
            }
            else if (request.Requestor == null)
            {
                viewEditRequestorButton.Visible = false;
                changeRequestorButton.Visible = false;
                requestorNameValueLabel.Text = rm.GetString(NoneSelected);
                contactInfoGroupBox.Enabled = false;
                SetAuthRequestInfo(rm);
                IsDirty = false;
            }
            else
            {
                SetExistingRequestInfo(request, rm);
                EnableButtons(false);
                IsDirty = false;
            }

            EnableEvents();
            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, requestInfoActionUI.SaveButton);
            SetTooltip(rm, toolTip, requestInfoActionUI.RevertButton);
            
            requestInfoActionUI.RequestLockedImage.Visible = request.IsLocked;
            if (request.IsLocked)
            {
                toolTip.SetToolTip(requestInfoActionUI.RequestLockedImage,
                                   string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString(requestInfoActionUI.RequestLockedImage.Name), request.InUseRecord.UserId));
            }

            requestInfoActionUI.ViewAuthRequestButton.Enabled = !string.IsNullOrEmpty(request.AuthRequest);

            ApplySecurityRights();
            if (request.Status == RequestStatus.Denied || request.Status == RequestStatus.Canceled)
            {                
                ShowCancelDeniedRequestDialog();
                rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                requestInfoActionUI.RevertButton.Text = rm.GetString("cancelButton");
                requestInfoActionUI.RevertButton.Enabled = true;                
            }
        }

        /// <summary>
        /// Sets the information for new request
        /// </summary>
        /// <param name="request"></param>
        /// <param name="rm"></param>
        private void SetNewRequestInfo(RequestDetails request, ResourceManager rm)
        {
            //Added for facility integration in request
            if (request == null || request.Id == 0)
            {
                FacilityDetails facility = UserData.Instance.DefaultFacility;
                if (facility != null)
                {
                    TaxPerFacilityDetails userFacility = new TaxPerFacilityDetails();
                    userFacility.Id = facility.Id;                    
                    userFacility.Name = facility.Name;
                    userFacility.Type = facility.Type;
                    userFacility.Code = facility.Code;
                    userFacility.TaxPercentage = facility.TaxPercentage;
                    userFacility.IsDefault = facility.IsDefault;
                    request.DefaultFacility = userFacility;
                }            
            }            

            // These two if statements will be usefull when the user navigates from
            // patientInfo screen to requestInfo screen
            PopulateRequestReasonCombo();
            if (request.RequestReason != null)
            {
                reasonCombo.SelectedValue = request.RequestReason;
            }

            if (request.ReceiptDate != null)
            {
                receiptDateTimePicker.Value = request.ReceiptDate.Value;
            }

            statusValueLabel.Text = request.Status.ToString();
            
            setDeleteButton(false);
            requestInfoActionUI.RevertButton.Text = rm.GetString("cancelButton");
            
            if (requestorNameValueLabel.Text.Length == 0)
            {
                requestorNameValueLabel.Text = rm.GetString(NoneSelected);
            }

            reasonCombo.Enabled = true;
            if (reasonCombo.Enabled)
            {
                CheckMigratedRequest();
            }
            selectReqButton.Visible = true;
            viewEditRequestorButton.Visible = false;
            changeRequestorButton.Visible   = false;
            contactInfoGroupBox.Enabled     = false;
            IsDirty = false;
            RequestorDetails requestor = request.Requestor;
            if (requestor != null)
            {
                if (requestor.Id == 0)
                {
                    ShowSaveNewPatientRequestorDialog(requestor);
                }
                else
                {
                   PopulateRequestorInfo(requestor, true);
                   dirtyDataHandler(null, null);
                }
            }
        }
		
		//CR# 380843
        private void PopulateRequestReasonCombo()
        {
            if (!ROIConstants.MigrationRequest.Equals(request.RequestReason))
            {
                foreach (ReasonDetails reason in reasonDetails)
                {
                    if (ROIConstants.MigrationRequest.Equals(reason.Name))
                    {
                        reasonDetails.Remove(reason);
                        break;
                    }
                }
            }

            reasonCombo.DataSource = reasonDetails;
        }

        /// <summary>
        /// It can be reused exisitng request info and auth request.
        /// </summary>
        private void SetAuthRequestInfo(ResourceManager rm)
        { 
            receiptDateTimePicker.Value = request.ReceiptDate.Value;
            idValueLabel.Text = request.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            PopulateRequestReasonCombo();
            reasonCombo.SelectedText = request.RequestReason;
            dateCreatedValueLabel.Text = request.DateCreated.Value.ToShortDateString();
            lastUpdatedValueLabel.Text = request.LastUpdated.Value.ToString();
            updatedByValueLabel.Text = request.UpdatedBy;
            reasonCombo.Text = request.RequestReason;
            reasonCombo.Enabled = !(request.Status == RequestStatus.Canceled ||
                                    request.Status == RequestStatus.Completed ||
                                    request.Status == RequestStatus.Denied);

            if (request.Status == RequestStatus.AuthReceived || request.Status == RequestStatus.AuthReceivedDenied)
            {
                if (request.Status == RequestStatus.AuthReceived)
                {
                    isAuthReceived = true;
                }
                else
                {
                    isAuthReceivedDenied = true;
                }
                request.Status = RequestStatus.Logged;
                reasonCombo.Enabled = true;              
            }
            SetLabel(rm, requestInfoActionUI.RevertButton);
            PopulateStatusReasons();
            setDeleteButton(!(request.ReleaseCount > 0 || request.Status == RequestStatus.Canceled || request.Status == RequestStatus.Denied));
			if (reasonCombo.Enabled)
            {
            	CheckMigratedRequest();
			}
        }


        /// <summary>
        /// Sets the information for existing request
        /// </summary>
        /// <param name="request"></param>
        /// <param name="rm"></param>
        private void SetExistingRequestInfo(RequestDetails request, ResourceManager rm)
        {
            SetAuthRequestInfo(rm);
            PopulateRequestorInfo(request.Requestor, false);

            receiptDateTimePicker.Enabled = receiptDateTimePicker.Enabled = !(request.Status == RequestStatus.Canceled ||
                                                                           request.Status == RequestStatus.Denied);
            selectReqButton.Visible   = false;
            viewEditRequestorButton.Visible = true;
            viewEditRequestorButton.Enabled = !(request.Status == RequestStatus.Canceled ||
                                                request.Status == RequestStatus.Denied);
            changeRequestorButton.Visible = true;
            changeRequestorButton.Enabled = !(request.ReleaseCount > 0 || request.Status == RequestStatus.Canceled ||
                                              request.Status == RequestStatus.Denied);
            if (changeRequestorButton.Enabled)
            {
                EnableChangeRequestorButton();
            }
            contactInfoGroupBox.Enabled = !(request.Status == RequestStatus.Canceled ||
                                            request.Status == RequestStatus.Denied);            
        }

        /// <summary>
        /// Sets the value and null value for receipt date.
        /// </summary>
        private void SetReceiptDateNullValue()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
        }

        /// <summary>
        /// It gets data from the UI for save.
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            RequestDetails requestToSave = (appendTo == null) ? new RequestDetails() : appendTo as RequestDetails;
            
            if (reasonCombo.SelectedIndex > 0)
            {
                requestToSave.RequestReason = reasonCombo.Text;                
                requestToSave.RequestReasonAttribute = ((ReasonDetails)reasonCombo.SelectedItem).Attribute.ToString();
            }

            if (receiptDateTimePicker.Value.ToString().Trim() != SelectOption.Trim())
            {
                requestToSave.ReceiptDateText = receiptDateTimePicker.Value.ToString(ROIConstants.DateTimeFormat, CultureInfo.InvariantCulture);
            }
            else if (requestToSave.ReceiptDate != null && receiptDateTimePicker.Value.ToString().Trim() == SelectOption.Trim())
            {
                requestToSave.ReceiptDate = null;
            }

            ////Added for facility integration in request
            //if (facilityComboBox.SelectedIndex > 0)
            //{
            //    FacilityDetails facility = (FacilityDetails)facilityComboBox.SelectedItem;
            //    requestToSave.DefaultFacility = facility;
            //    if (requestToSave.Id == 0)
            //    {
            //        UserData.Instance.DefaultFacility = facility;
            //    }
            //}
                
            requestToSave.RequestorName         = requestorNameValueLabel.Text = requestorNameValueLabel.Text.Trim();
            requestToSave.RequestorTypeName     = requestorTypeValueLabel.Text = requestorTypeValueLabel.Text.Trim();
            requestToSave.RequestorHomePhone    = homePhoneTextBox.Text = homePhoneTextBox.Text.Trim();
            requestToSave.RequestorWorkPhone    = workPhoneTextBox.Text = workPhoneTextBox.Text.Trim();
            requestToSave.RequestorCellPhone    = cellPhoneTextBox.Text = cellPhoneTextBox.Text.Trim();
            requestToSave.RequestorFax          = faxTextBox.Text = faxTextBox.Text.Trim();
            requestToSave.RequestorContactName  = contactNameTextBox.Text = contactNameTextBox.Text.Trim();
            requestToSave.RequestorContactPhone = contactPhoneTextBox.Text = contactPhoneTextBox.Text.Trim();
            
            if (requestToSave.Id == 0)
            {
                requestToSave.DateCreated = DateTime.Now;
            }
            //requestToSave.LastUpdated = DateTime.Now;
            //requestToSave.UpdatedBy   = UserData.Instance.FullName;

            return requestToSave;
        }

        /// <summary>
        /// Enables the control events.
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            requestorNameValueLabel.TextChanged   += dirtyDataHandler;
            patientValueLabel.TextChanged         += dirtyDataHandler;
            reasonCombo.SelectedIndexChanged      += dirtyDataHandler;
            homePhoneTextBox.TextChanged          += dirtyDataHandler;
            workPhoneTextBox.TextChanged          += dirtyDataHandler;
            cellPhoneTextBox.TextChanged          += dirtyDataHandler;
            faxTextBox.TextChanged                += dirtyDataHandler;
            contactNameTextBox.TextChanged        += dirtyDataHandler;
            contactPhoneTextBox.TextChanged       += dirtyDataHandler;
            receiptDateTimePicker.ValueChanged += dirtyDataHandler;
        }

        /// <summary>
        /// Disables the control events.
        /// </summary>
        private void DisableEvents()
        {
            requestorNameValueLabel.TextChanged   -= dirtyDataHandler;
            patientValueLabel.TextChanged         -= dirtyDataHandler;
            reasonCombo.SelectedIndexChanged      -= dirtyDataHandler;
            homePhoneTextBox.TextChanged          -= dirtyDataHandler;
            workPhoneTextBox.TextChanged          -= dirtyDataHandler;
            cellPhoneTextBox.TextChanged          -= dirtyDataHandler;
            faxTextBox.TextChanged                -= dirtyDataHandler;
            contactNameTextBox.TextChanged        -= dirtyDataHandler;
            contactPhoneTextBox.TextChanged       -= dirtyDataHandler;
            receiptDateTimePicker.ValueChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Clears all the controls data.
        /// </summary>
        private void ClearControls()
        {
            errorProvider.Clear();

            idValueLabel.Text          = String.Empty;
            dateCreatedValueLabel.Text = String.Empty;
            lastUpdatedValueLabel.Text = String.Empty;
            updatedByValueLabel.Text   = String.Empty;
            homePhoneTextBox.Text      = String.Empty;
            workPhoneTextBox.Text      = String.Empty;
            cellPhoneTextBox.Text      = String.Empty;
            faxTextBox.Text            = String.Empty;
            contactNameTextBox.Text    = String.Empty;
            contactPhoneTextBox.Text   = String.Empty;

            SetReceiptDateNullValue();
            //reasonCombo.SelectedIndex = 0;
        }

        /// <summary>
        /// Enables the buttons
        /// </summary>
        /// <param name="enable"></param>
        private void EnableButtons(bool enable)
        {
            if (request != null && request.IsOldRequest)
            {
                isDirty = false;
                requestInfoActionUI.SaveButton.Enabled = false;
                requestInfoActionUI.RevertButton.Enabled = false;
            }
            else
            {
                requestInfoActionUI.SaveButton.Enabled = enable;
                requestInfoActionUI.RevertButton.Enabled = enable;
            }
        }

        /// <summary>
        /// It prepopulates reasons and requestor status data.
        /// </summary>
        /// <param name="requestorTypes"></param>
        internal void PrePopulate(Collection<ReasonDetails> reasons)
        {
            DisableEvents();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            ReasonDetails forSelect = new ReasonDetails();
            forSelect.Id = 0;
            forSelect.Name = rm.GetString("pleaseSelectCombo");
            reasons.Insert(0, forSelect);
            reasonDetails = reasons;

            reasonCombo.DisplayMember = "Name";
            reasonCombo.ValueMember = "Name";
            //reasonCombo.DataSource = reasons; 
            EnableEvents();
        }

        public bool Save()
        {
            errorProvider.Clear();
            ROIViewUtility.MarkBusy(true);
            try
            {
                RequestDetails requestDetails = GetData(ROIViewUtility.DeepClone(request)) as RequestDetails;
                RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;

                if (requestDetails.Requestor != null && !requestDetails.Requestor.IsActive)
                {
                    ShowInactiveRequestorDialog();
                    return false;
                }

                if (rsp.InfoEditor.Request.Status != RequestStatus.Completed && request.Status == RequestStatus.Completed)
                {
                    requestDetails.CompletedDate = DateTime.Now;
                }

                RequestValidator validator = new RequestValidator();
                if (!validator.ValidateRequestInfo(requestDetails))
                {
                    throw validator.ClientException;
                }
                if (reasonCombo.SelectedIndex == 0)
                {
                    throw new ROIException(ROIErrorCodes.RequestReasonEmpty);
                }
                if (requestDetails.Id == 0)
                {
                    string requestSecretWord = RequestController.Instance.RetrieveGeneratedPassword();
                    if (!string.IsNullOrEmpty(requestSecretWord))
                    {
                        requestDetails.RequestSecretWord = requestSecretWord;
                    }

                    requestDetails = RequestController.Instance.CreateRequest(requestDetails);
                    requestDetails.UpdatedBy = UserData.Instance.FullName;
                    requestDetails.LastUpdated = DateTime.Now;

                    idValueLabel.Text = requestDetails.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    lastUpdatedValueLabel.Text = (requestDetails.LastUpdated.HasValue) ? requestDetails.LastUpdated.Value.ToShortDateString() : string.Empty;
                    dateCreatedValueLabel.Text = (requestDetails.DateCreated.HasValue) ? requestDetails.DateCreated.Value.ToShortDateString() : string.Empty;
                    updatedByValueLabel.Text = UserData.Instance.FullName;
                    
                    reasonCombo.Enabled = true;                    
                    contactInfoGroupBox.Enabled = true;

                    setDeleteButton(true);
              
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                    requestInfoActionUI.RevertButton.Text = rm.GetString(requestInfoActionUI.RevertButton.Name); 

                    //Update the header information on request information editor.
                    HeaderPane header = (HeaderPane)rsp.InfoEditor.HeaderPane;
                    ((HeaderUI)header.View).Information = rm.GetString("requestinfo.header.editinfo");

                    rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                    toolTip.SetToolTip(requestInfoActionUI.RevertButton,  rm.GetString(requestInfoActionUI.RevertButton.Name + 
                                                                          "." + Pane.GetType().Name));

                    RequestController.Instance.CreateInUseRecord(ROIConstants.RequestDomainType, requestDetails.Id);
                    RequestEvents.OnRequestInfoCreated(Pane, new ApplicationEventArgs(requestDetails, this));
                }
                else
                {

                    if ((rsp.InfoEditor.Request.Status != RequestStatus.Canceled && 
                        rsp.InfoEditor.Request.Status != RequestStatus.Denied) &&
                        (requestDetails.Status == RequestStatus.Canceled || 
                         requestDetails.Status == RequestStatus.Denied))
                    {
                        DoWriteOff(requestDetails);
                    }
                    else if((rsp.InfoEditor.Request.Status == RequestStatus.Canceled || 
                        rsp.InfoEditor.Request.Status == RequestStatus.Denied) &&
                        (requestDetails.Status != RequestStatus.Canceled && 
                         requestDetails.Status != RequestStatus.Denied))
                    {
                        ReverseWriteOffAmount(requestDetails);
                    }

                    if (!request.IsReleased && requestorChanged)
                    {
                        ReviseBillingCharges(requestDetails, changedRequestorIsPatient);
                    }

                    requestDetails.DeleteRelease = changedRequestorIsPatient;

                    if (isAuthReceived || isAuthReceivedDenied)
                    {
                        //Log the event history for Auth-Received or Auth-Received Denied
						//CR# 382391
                        //CommentDetails eventDetails = new CommentDetails();
                        //eventDetails.RequestId = requestDetails.Id;
                        //eventDetails.EventType = EventType.ChangeOfStatus;
                        //if (isAuthReceived)
                        //{
                        //    eventDetails.EventRemarks = AuthReceivedStausChanged;
                        //}
                        //else
                        //{
                        //    eventDetails.EventRemarks = AuthReceivedDenyStausChanged;
                        //}

                        //RequestController.Instance.CreateComment(eventDetails);
                        isAuthReceived = false;
                        isAuthReceivedDenied = false;
                    }
                    SortedList<string, RequestPatientDetails> tempPatientList = new SortedList<string, RequestPatientDetails>();
                    if (requestDetails.Patients.Count == 0)
                    {
                        Application.DoEvents();
                        RequestPatients requestPatients = RequestController.Instance.RetrieveRequestPatients(requestDetails.Id);
                        foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                        {
                            requestDetails.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                        }
                    }
                    if (requestDetails.Patients.Count > 0)
                    {
                        for (int count = 0; count < requestDetails.Patients.Count; count++)
                        {
                            tempPatientList.Add(requestDetails.Patients.Keys[count], requestDetails.Patients.Values[count]);
                        }
                    }
                    requestDetails = RequestController.Instance.UpdateRequest(requestDetails);
                    
                    if (tempPatientList.Count > 0)
                    {
                        for (int count = 0; count < tempPatientList.Count; count++)
                        {
                            requestDetails.Patients.Add(tempPatientList.Keys[count], tempPatientList.Values[count]);
                        }
						//CR# 376962
                        bool isFirstPatient = true;
                        for (int count = 0; count < tempPatientList.Count; count++)
                        {
                            if (isFirstPatient)
                            {
                                request.PatientNames = tempPatientList.Values[count].Name;
                                isFirstPatient = false;
                            }
                            else
                            {
                                request.PatientNames += (":" + tempPatientList.Values[count].Name);
                            }
                        }
                    }
                    
                    updatedByValueLabel.Text = requestDetails.UpdatedBy;
                    lastUpdatedValueLabel.Text = requestDetails.LastUpdated.ToString();                    
                    if (changedRequestorIsPatient)
                    {
                        rsp.InfoEditor.Request = request;
                        if (rsp.PatientInfoEditor != null)
                        {
                            rsp.PatientInfoEditor.Cleanup();
                            rsp.PatientInfoEditor = null;
                        }

                        if (rsp.BillingPaymentInfoEditor != null)
                        {
                            rsp.BillingPaymentInfoEditor.Cleanup();
                            rsp.BillingPaymentInfoEditor = null;
                        }

                        changedRequestorIsPatient = false;
                        //RequestEvents.OnRequestInfoCreated(Pane, new ApplicationEventArgs(requestDetails, this));
                        RequestEvents.OnDsrChanged(Pane, new ApplicationEventArgs(false, this));
                    }

                    RequestEvents.OnRequestUpdated(Pane, new ApplicationEventArgs(requestDetails, this));
                }
                if (reasonCombo.Enabled)
                {
                    CheckMigratedRequest();
                }
                request = requestDetails;
                ((RequestInfoEditor)Pane.ParentPane).Request = request;

                if (request.Status == RequestStatus.Canceled || request.Status == RequestStatus.Denied)
                {
                    RequestInfoCancelStatus(false);
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    requestInfoActionUI.RevertButton.Text = rm.GetString("cancelButton");
                    requestInfoActionUI.RevertButton.Enabled = true;
                }
                else
                {
                    RequestInfoLoggegStatus();
                }
                selectReqButton.Visible = false;
                viewEditRequestorButton.Visible = true;
                changeRequestorButton.Visible = true;

                isDirty = false;
                EnableButtons(false);
                if (request.RecordVersionId == 0)
                {
                    ApplySecurityRights();
                }
                setDeleteButton(true);
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }                
                return false;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return true;
        }

        private void RequestInfoCancelStatus(bool enable)
        {
            reasonCombo.Enabled = enable;
            if (reasonCombo.Enabled)
            {
                CheckMigratedRequest();
            }
            receiptDateTimePicker.Enabled = enable;
            receiptDateTimePicker.Enabled = enable;
            viewEditRequestorButton.Enabled = enable;
            changeRequestorButton.Enabled = enable;
            if (changeRequestorButton.Enabled)
            {
                EnableChangeRequestorButton();
            }
            contactInfoGroupBox.Enabled = enable;
            setDeleteButton(enable);
        }

		//CR# 380843
        private void EnableChangeRequestorButton()
        {
            if (!string.IsNullOrEmpty(request.RequestorTypeName))
            {
                if (ROIConstants.MigrationRequest.Equals(request.RequestorTypeName))
                {
                    changeRequestorButton.Enabled = false;
                }
            }
        }
        private void RequestInfoLoggegStatus()
        {
            reasonCombo.Enabled = !(request.Status == RequestStatus.Completed);
            if (reasonCombo.Enabled)
            {
                CheckMigratedRequest();
            }
            receiptDateTimePicker.Enabled = true;
            receiptDateTimePicker.Enabled = true;
            changeRequestorButton.Enabled = !(request.ReleaseCount > 0);
            if (changeRequestorButton.Enabled)
            {
                EnableChangeRequestorButton();
            }
            viewEditRequestorButton.Enabled = true;
            contactInfoGroupBox.Enabled = true;
            setDeleteButton(!(request.ReleaseCount > 0));
        }

        private void setDeleteButton(bool enable)
        {
            bool b = IsAllowed(ROISecurityRights.ROIDeleteRequest) && enable && (request.ReleaseCount > 0 ? false : true);
            requestInfoActionUI.DeleteButton.Visible = true;
            requestInfoActionUI.DeleteButton.Enabled = b;
        }

        private static void DoWriteOff(RequestDetails request)
        {
            RequestDetails requestDetails =  RequestController.Instance.RetrieveRequest(request.Id, false);
            if (!request.HasDraftRelease || requestDetails.BalanceDue <= 0) return;

            //As per ROI-WH15.1.1 there is no write off adjustment done against the invoices and request level
            //ReleaseDetails release = BillingController.Instance.RetrieveReleaseInfo(request.DraftRelease);
            //RequestTransaction txn = new RequestTransaction();
            //txn.Amount = -(request.BalanceDue);
            //txn.CreatedDate = DateTime.Now;
            //txn.Description = ROIConstants.WriteOff;
            //txn.TransactionType = TransactionType.Adjustment;
            //txn.IsDebit = false;
            //release.RequestTransactions.Add(txn);            
            //BillingController.Instance.UpdateReleaseItem(release);

            RequestTransaction transaction = new RequestTransaction();
            transaction.Amount = request.BalanceDue;
            //Log the event history for writeoff
            CommentDetails eventDetails = new CommentDetails();
            eventDetails.RequestId = request.Id;
            eventDetails.EventType = EventType.WriteOff;
            eventDetails.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, WriteOffEventMessage, ReleaseDetails.FormattedAmount(transaction.Amount));

            RequestController.Instance.CreateComment(eventDetails);

            request.BalanceDue = 0;
        }

        private static void ReverseWriteOffAmount(RequestDetails request)
        {
            if (!request.HasDraftRelease || request.BalanceDue != 0) return;

            //As per ROI-WH15.1.1 there is no write off adjustment done against the invoices and request level
            //ReleaseDetails release = BillingController.Instance.RetrieveReleaseInfo(request.DraftRelease);            
            //ComparableCollection<RequestTransaction> txns = new ComparableCollection<RequestTransaction>(release.RequestTransactions, new RequestTransactionComparer());
            //if (txns.Count == 0) return;
            //txns.ApplySort(TypeDescriptor.GetProperties(typeof(RequestTransaction))["FormattedDate"], ListSortDirection.Descending);
            //RequestTransaction writeoffTxn = null;
            //foreach (RequestTransaction trn in txns)
            //{
            //    if (trn.Description == ROIConstants.WriteOff)
            //    {
            //        writeoffTxn = trn;
            //        break;
            //    }
            //}
            //if (writeoffTxn == null) return;
            //RequestTransaction txn = new RequestTransaction();
            //txn.Amount = -(writeoffTxn.Amount);
            //txn.CreatedDate = DateTime.Now;
            //txn.Description = ROIConstants.ReverseWriteOff;
            //txn.TransactionType = TransactionType.Adjustment;
            //txn.IsDebit = true;
            //release.RequestTransactions.Add(txn);            
            //BillingController.Instance.UpdateReleaseItem(release);

            RequestBillingInfo requestBillingInfo = RequestController.Instance.RetrieveRequestBillingPaymentInfo(request.Id);

            List<RequestCoreChargesInvoiceDetail> list =  requestBillingInfo.ReqCoreChargesInvoiceDetail;

            double requestBalanceDue = 0;
            foreach (RequestCoreChargesInvoiceDetail invoiceDetails in list)
            {
                requestBalanceDue += (invoiceDetails.releaseCost - invoiceDetails.paymentAmount);
            }

            RequestTransaction transaction = new RequestTransaction();
            if (requestBalanceDue != 0)
            {
                transaction.Amount = requestBalanceDue;
            }
            else
            {
                transaction.Amount = requestBillingInfo.BalanceDue;
            }
            transaction.Description = ROIConstants.ReverseWriteOff;

            //Log the event history for reversing writeoff
            CommentDetails eventDetails = new CommentDetails();
            eventDetails.RequestId = request.Id;
            eventDetails.EventType = EventType.AdjustmentPosted;
            eventDetails.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ReverseWriteOffEventMessage,
                                                      "Positive", ReleaseDetails.FormattedAmount(transaction.Amount), transaction.Description);

            RequestController.Instance.CreateComment(eventDetails);

            //request.BalanceDue = -(writeoffTxn.Amount);
            request.BalanceDue = -(requestBalanceDue);            
        }

        private static void ReviseBillingCharges(RequestDetails request, bool changedRequestorIsPatient)
        {

            RequestorTypeDetails requestorType = ROIAdminController.Instance.GetRequestorType(request.RequestorType);

            int hpfcount = 0;
            BillingPaymentInfoUI billingPaymentInfoUI = new BillingPaymentInfoUI();


            Hashtable billingTiers = new Hashtable(2);
            BillingTierDetails hpfBillingTier = BillingAdminController.Instance.GetBillingTier(requestorType.HpfBillingTier.Id);
            BillingTierDetails nonHpfBillingTier = BillingAdminController.Instance.GetBillingTier(requestorType.NonHpfBillingTier.Id);
            billingTiers.Add(hpfBillingTier.Id, hpfBillingTier);
            if (!billingTiers.ContainsKey(nonHpfBillingTier.Id))
            {
                billingTiers.Add(nonHpfBillingTier.Id, nonHpfBillingTier);
            }

            SetDefaultNonHpfBillingTier(request, nonHpfBillingTier.Id);

            if (!request.HasDraftRelease || changedRequestorIsPatient) return;

            ReleaseDetails release = billingPaymentInfoUI.RevertInfo(request);
            if (request.Patients.Count == 0)
            {
                Application.DoEvents();
                RequestPatients requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);
                foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                {
                    request.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                }
            }

            Dictionary<long, int> nonHpfBillingTiersIds = new Dictionary<long, int>();
            foreach (RequestPatientDetails patient in request.Patients.Values)
            {
                foreach (RequestNonHpfEncounterDetails nonHpfEncounter in patient.NonHpfDocument.GetChildren)
                {
                    foreach (RequestNonHpfDocumentDetails nonHpfDoc in nonHpfEncounter.GetChildren)
                    {
                        nonHpfDoc.BillingTier = requestorType.NonHpfBillingTier.Id;

                        if (nonHpfBillingTiersIds.ContainsKey(nonHpfDoc.BillingTier))
                        {
                            nonHpfBillingTiersIds[nonHpfDoc.BillingTier] += nonHpfDoc.PageCount;
                        }
                        else
                        {
                            nonHpfBillingTiersIds.Add(nonHpfDoc.BillingTier, nonHpfDoc.PageCount);
                        }
                    }
                }

                foreach (RequestAttachmentEncounterDetails attachmentEncounter in patient.Attachment.GetChildren)
                {
                    foreach (RequestAttachmentDetails tmpAttachment in attachmentEncounter.GetChildren)
                    {
                        tmpAttachment.BillingTier = requestorType.NonHpfBillingTier.Id;

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

            }

            release.DocumentCharges.Clear();
            RequestPatientInfoUI.UpdateDocumentCharges(release, nonHpfBillingTiersIds, 
                                                       requestorType, billingTiers,
                                                       request.ReleaseCount > 0 ? true : false, null, hpfcount);                                                      

            double totalAmount = 0;
            foreach (DocumentChargeDetails documentCharge in release.DocumentCharges)
            {
                documentCharge.Amount = UpdateBillingTierAmount(documentCharge.Pages,
                                                                documentCharge.Copies,
                                                                (BillingTierDetails)billingTiers[documentCharge.BillingTierId]);

                totalAmount += documentCharge.Amount;
            }

            Collection<FeeChargeDetails> feeCharges = (Collection<FeeChargeDetails>)ROIViewUtility.DeepClone(release.FeeCharges);
            release.FeeCharges.Clear();
            double oldFeeChargeTotal = 0;
            foreach (FeeChargeDetails feeCharge in feeCharges)
            {
                if (!feeCharge.IsCustomFee)
                {
                    oldFeeChargeTotal += feeCharge.Amount;
                }
                else
                {
                    release.FeeCharges.Add(feeCharge);
                }
            }

            double feeChargeTotal = ReviseFeeCharges(requestorType, release, oldFeeChargeTotal);

            double oldDocumentCharge = release.DocumentChargeTotal;
            double releaseCost = release.ReleaseCost;
            double balanceDue = release.BalanceDue;

            releaseCost = (releaseCost - oldDocumentCharge - oldFeeChargeTotal) + totalAmount + feeChargeTotal;
            balanceDue = (balanceDue - oldDocumentCharge - oldFeeChargeTotal) + totalAmount + feeChargeTotal;

            release.DocumentChargeTotal = totalAmount;
            release.ReleaseCost = releaseCost;
            release.TotalRequestCost = releaseCost;
            release.BalanceDue = balanceDue;
            request.BalanceDue = balanceDue;

            billingPaymentInfoUI.SaveAndBillInfo(release, request);
           // BillingController.Instance.UpdateReleaseItem(release);

        }

        public static double ReviseFeeCharges(RequestorTypeDetails requestorType,
                                            ReleaseDetails release, double oldFeeCharge)
        {

            double feeChargeTotal = 0;
            BillingTemplateDetails defaultBillingType = null;
            List<AssociatedBillingTemplate> billingTemplates = new List<AssociatedBillingTemplate>(requestorType.AssociatedBillingTemplates);
            AssociatedBillingTemplate associatedBillingTemplate =  billingTemplates.Find(delegate(AssociatedBillingTemplate billingTemplate) {return billingTemplate.IsDefault;});
            
            if (associatedBillingTemplate != null)
            {
                defaultBillingType = BillingAdminController.Instance.GetBillingTemplate(associatedBillingTemplate.BillingTemplateId);
                FeeTypeDetails feeType;
                FeeChargeDetails feeCharge;
                foreach (AssociatedFeeType associatedFeeType in defaultBillingType.AssociatedFeeTypes)
                {
                    feeType = BillingAdminController.Instance.RetrieveFeeType(associatedFeeType.FeeTypeId);

                    feeCharge = new FeeChargeDetails();
                    feeCharge.FeeType = feeType.Name;
                    feeCharge.Amount = feeType.Amount;
                    feeCharge.IsCustomFee = false;

                    release.FeeCharges.Add(feeCharge);

                    feeChargeTotal += feeType.Amount;
                }
            }

            release.FeeChargeTotal = (release.FeeChargeTotal - oldFeeCharge) + feeChargeTotal;

            return feeChargeTotal;
        }

        private static void SetDefaultNonHpfBillingTier(RequestDetails request, long nonHpfBillingTier)
        {
            foreach (RequestPatientDetails patient in request.Patients.Values)
            {
                foreach (RequestNonHpfEncounterDetails nonHpfEncounter in patient.NonHpfDocument.GetChildren)
                {
                    foreach (RequestNonHpfDocumentDetails nonHpfDoc in nonHpfEncounter.GetChildren)
                    {
                        nonHpfDoc.BillingTier = nonHpfBillingTier;
                    }
                }
                foreach (RequestAttachmentEncounterDetails attachmentEncounter in patient.Attachment.GetChildren)
                {
                    foreach (RequestAttachmentDetails tmpAttachment in attachmentEncounter.GetChildren)
                    {
                        tmpAttachment.BillingTier = nonHpfBillingTier;
                    }
                }
            }
        }

        /// <summary>
        /// Updates documentcharge amount based on pages and copies entered by user.
        /// </summary>
        /// <param name="pages"></param>
        /// <param name="copies"></param>
        public static double UpdateBillingTierAmount(int pages, int copies, BillingTierDetails billingTier)
        {
            if (pages == 0)
            {
                return 0;
            }

            long totalpages = pages * copies;

            long totalPagesForBillingTier = totalpages;

            long pageRange = (totalPagesForBillingTier - totalpages + 1);

            double price = billingTier.BaseCharge;
            int pageTierCount = billingTier.PageTiers.Count;
            if (pageTierCount > 0)
            {
                foreach (PageLevelTierDetails pageTier in billingTier.PageTiers)
                {
                    if (!(pageRange >= pageTier.StartPage && pageRange <= pageTier.EndPage))
                    {
                        continue;
                    }

                    if (totalPagesForBillingTier > pageTier.EndPage)
                    {
                        price += (pageTier.PricePerPage * (pageTier.EndPage + 1 - pageRange));
                        pageRange = pageTier.EndPage + 1;
                    }
                    else
                    {
                        price += (pageTier.PricePerPage * (totalPagesForBillingTier + 1 - pageRange));
                    }

                    if (totalPagesForBillingTier >= pageTier.StartPage && totalPagesForBillingTier <= pageTier.EndPage)
                    {
                        break;
                    }
                }

                PageLevelTierDetails lastPageTier = billingTier.PageTiers[pageTierCount - 1];
                if (totalPagesForBillingTier > lastPageTier.EndPage)
                {
                    price += (billingTier.OtherPageCharge * (totalPagesForBillingTier + 1 - pageRange));
                }
            }

            return price;
        }

        /// <summary>
        /// Saves the data. Checks for non unique existing requestor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            Save();
        }

        /// <summary>
        ///  Deletes the selected Request.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void deleteButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();

            if (ShowConfirmDeleteDialog())
            {
                DisableEvents();
                ROIViewUtility.MarkBusy(true);
                try
                {
                    if (request.IsReleased)
                    {
                        throw new ROIException(ROIErrorCodes.RequestCannotDelete);
                    }
                    RequestController.Instance.DeleteRequest(request.Id);

                    ApplicationEventArgs ae = new ApplicationEventArgs(request, this);
                    RequestEvents.OnRequestDeleted(Pane, ae);
                }
                catch (ROIException cause)
                {
                    log.FunctionFailure(cause);
                    ROIViewUtility.Handle(Context, cause);
                }
                finally
                {
                    ROIViewUtility.MarkBusy(false);
                    log.ExitFunction();
                }

                EnableEvents();
            }
        }

        private bool ShowConfirmDeleteDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString(DeleteRequestDialogMessageKey);

            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString(DeleteRequestDialogTitle);
            string okButtonText = rm.GetString(DeleteRequestDialogOkButton);
            string cancelButtonText = rm.GetString(DeleteRequestDialogCancelButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(DeleteRequestDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(DeleteRequestDialogCancelButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip,
                                                 cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Discard any changes since last save and revert fields to the last saved state.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void revertButton_Click(object sender, EventArgs e)
        {            
            if ((Pane.ParentPane as RequestInfoEditor).Request.Status == RequestStatus.Canceled ||
                (Pane.ParentPane as RequestInfoEditor).Request.Status == RequestStatus.Denied)
            {
                ApplicationEventArgs ae = new ApplicationEventArgs(request, this);
                RequestEvents.OnRequestDeleted(Pane, ae);
                return;
            }
            if (request == null || request.Id == 0)
            {
                isDirty = false;
                Process_CancelCreateRequest(sender, e);
            }
            else
            {
                DisableEvents();
                EnableButtons(false);
                ClearControls();
                if (changedRequestorIsPatient)
                {
                    //RequestEvents.OnRequestorChangedToPatient(Pane, new ApplicationEventArgs(request, this));
                    changedRequestorIsPatient = false;
                }

                if (changedRequestorIsPatient)
                {
                    //RequestEvents.OnRequestorChangedToPatient(Pane, new ApplicationEventArgs(request, this));
                    changedRequestorIsPatient = false;
                }

                SetData((Pane.ParentPane as RequestInfoEditor).Request);
                EnableEvents();
            }
            IsDirty = false;
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            RequestInfoEditor requestInfoEditor = (RequestInfoEditor)Pane.ParentPane;
            EventHandler cancelCreateRequestHandler = requestInfoEditor.CreateRequestInfo.CancelCreateRequestHandler;
            if (cancelCreateRequestHandler != null)
            {
                ((RequestRSP)requestInfoEditor.ParentPane).InfoEditor = null;
                cancelCreateRequestHandler(sender, e);
            }

            RequestEvents.OnRequestDeleted(Pane, new ApplicationEventArgs(null, this));
        }

        /// <summary>
        /// Occurs when the select requestor button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void selectReqButton_Click(object sender, EventArgs e)
        {
            ShowSelectRequestorDialog(null);
        }

        /// <summary>
        /// Method that shows select requestor dialog when the requestor is null, 
        /// other wise change requestor dialog will be shown
        /// </summary>
        /// <param name="requestor"></param>
        private void ShowSelectRequestorDialog(RequestorDetails requestor)
        {
            
            EventHandler selectRequestorHandler = new EventHandler(Process_SelectRequestor);
            EventHandler cancelHandler = new EventHandler(Process_CancelSelectRequestorDialog);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            selectRequestor = new SelectRequestorUI(selectRequestorHandler, cancelHandler, Pane);
            selectRequestor.Header = new HeaderUI();
            selectRequestor.Header.Title = rm.GetString("selectrequestor.header.title");
            selectRequestor.Header.Information = rm.GetString("selectrequestor.header.info");
            selectRequestor.SetData(request);
            if (requestor != null)
            {
                selectRequestor.InitRequestorInfoTabPage(requestor);
            }
            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("selectrequestor.titlebar.text"), selectRequestor);
            form.FormClosing += delegate { selectRequestor.CleanUp(); };
            form.ShowDialog(this);
        }

        /// <summary>
        /// Adds the seelcted requesor to the request
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SelectRequestor(object sender, EventArgs e)
        {
            
            BaseEventArgs be = (BaseEventArgs)e;
            SelectRequestorUI selectRequestor = (SelectRequestorUI)sender;

            RequestorDetails requestor = (RequestorDetails)be.Info;
            if (requestor.IsPatientRequestor && string.IsNullOrEmpty(request.AuthRequest))
            {
                if (selectRequestor.Matching)
                {
                    request.AddPatient(selectRequestor.MatchedPatient);
                    Process_CancelSelectRequestorDialog(sender, null);
                }
                else
                {
                    ShowPatientMatchingDialog(requestor);
                }
            }
            else
            {
                Process_CancelSelectRequestorDialog(sender, null);
            }

            PopulateRequestorInfo(requestor, true);
        }

        /// <summary>
        /// Close the SelectRequestor dialog
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CancelSelectRequestorDialog(object sender, EventArgs e)
        {
            ((SelectRequestorUI)sender).ParentForm.Close();
            selectRequestor = null;
        }
        
        /// <summary>
        /// Occurs when View/Edit Requestor button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void viewEditReqButton_Click(object sender, EventArgs e)
        {
            EventHandler saveRequestorHandler = new EventHandler(Process_SaveRequestor);
            EventHandler cancelHandler = new EventHandler(Process_CancelViewEditRequestorDialog);

            ViewEditRequestorUI viewRequestor = new ViewEditRequestorUI(saveRequestorHandler, cancelHandler, Pane);
            viewRequestor.InitRequestorInfoTabPage(request.Requestor, request.IsReleased);
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form form =  ROIViewUtility.ConvertToForm(null, rm.GetString("vieweditrequestor.titlebar.text"), viewRequestor);
            form.FormClosing += delegate { viewRequestor.CleanUp(); };
            form.ShowDialog(this);
        }

        /// <summary>
        /// Handler is user to save the requestor information
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SaveRequestor(object sender, EventArgs e)
        {
            BaseEventArgs be = (BaseEventArgs)e;
            ViewEditRequestorUI viewEditRequestorUI = (ViewEditRequestorUI)sender;
            RequestorDetails requestor = (RequestorDetails)be.Info;
            if (viewEditRequestorUI.ChangedRequestorIsPatient && string.IsNullOrEmpty(request.AuthRequest))
            {
                ShowPatientMatchingDialog(requestor);
                if (!changedRequestorIsPatient)
                {
                    changedRequestorIsPatient = viewEditRequestorUI.ChangedRequestorIsPatient;
                }
            }

            bool doUpdate = viewEditRequestorUI.DoUpdateContactAndAddressInfo;

            RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;

            if(!requestorChanged)
            {
                requestorChanged =  (rsp.InfoEditor.Request.RequestorType != requestor.Type);
            }

            rsp.InfoEditor.Request.Requestor = requestor;
            rsp.InfoEditor.Request.RequestorId = requestor.Id;
            rsp.InfoEditor.Request.RequestorType = requestor.Type;
            rsp.InfoEditor.Request.RequestorTypeName = requestor.TypeName;

            PopulateRequestorInfo(requestor, doUpdate);
            dirtyDataHandler(sender, e);
        }

        private void Process_CancelViewEditRequestorDialog(object sender, EventArgs e)
        {
            ((ViewEditRequestorUI)sender).ParentForm.Close();
        }

        private void changeReqButton_Click(object sender, EventArgs e)
        {
            
            EventHandler changetRequestorHandler = new EventHandler(Process_ChangeRequestor);
            EventHandler cancelHandler = new EventHandler(Process_CancelChangeRequestorDialog);
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            selectRequestor = new SelectRequestorUI(changetRequestorHandler, cancelHandler, Pane);
            selectRequestor.Header = new HeaderUI();
            selectRequestor.Header.Title = rm.GetString("changerequestor.header.title");
            selectRequestor.Header.Information = rm.GetString("changerequestor.header.info");
            selectRequestor.SetData(request);

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("changerequestor.titlebar.text"), selectRequestor);
            form.FormClosing += delegate { selectRequestor.CleanUp(); };
            form.ShowDialog(this);
        }

        private void Process_ChangeRequestor(object sender, EventArgs e)
        {
            BaseEventArgs be = (BaseEventArgs)e;
            SelectRequestorUI requestorUI = (SelectRequestorUI)sender;
            RequestorDetails changedRequestor = (RequestorDetails)be.Info;
            bool doUpdate = requestorUI.DoUpdateContactAndAddressInfo;

            if (changedRequestor.IsPatientRequestor && string.IsNullOrEmpty(request.AuthRequest))
            {
                if (requestorUI.Matching)
                {
                    request.Patients.Clear();
                    request.AddPatient(requestorUI.MatchedPatient);
                    changedRequestorIsPatient = true;
                    Process_CancelSelectRequestorDialog(sender, null);
                }
                else
                {

                    ShowPatientMatchingDialog(changedRequestor);
                }
            }
            else
            {   
                Process_CancelSelectRequestorDialog(sender, null);
                if (changedRequestorIsPatient)
                {
                    changedRequestorIsPatient = false;
                }
            }

            if (!requestorChanged)
            {
                requestorChanged = ((request.RequestorId != changedRequestor.Id) ||
                                    (request.RequestorType != changedRequestor.Type));
            }

            //If user changed the requestor then the the corresponding requestor type's sales tax status will be set in the request
            if (requestorChanged)
            {
                request.HasSalesTax = changedRequestor.HasSalesTax;
            }

            PopulateRequestorInfo(changedRequestor, doUpdate);
            dirtyDataHandler(sender, e);
        }

        private void Process_CancelChangeRequestorDialog(object sender, EventArgs e)
        {
            ((SelectRequestorUI)sender).ParentForm.Close();
            selectRequestor = null;
        }

        /// <summary>
        /// Occurs when user click the Update Request Status button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void updateReqStatusButton_Click(object sender, EventArgs e)
        {

            EventHandler changeRequestStatusHandler = new EventHandler(Process_ChangeRequestStatus);
            EventHandler cancelHandler              = new EventHandler(Process_CancelChangeRequestStatusDialog);

            ChangeRequestStatusUI changeRequestStatus = new ChangeRequestStatusUI(changeRequestStatusHandler, cancelHandler, Pane);
            ROIViewUtility.MarkBusy(true);
            changeRequestStatus.SetData(request);
            ROIViewUtility.MarkBusy(false);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            ROIViewUtility.ConvertToForm(null, rm.GetString("changerequeststatus.title"), changeRequestStatus).ShowDialog(this);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_ChangeRequestStatus(object sender, EventArgs e)
        {
            Process_CancelChangeRequestStatusDialog(sender, null);

            BaseEventArgs be = (BaseEventArgs)e;
            RequestDetails requestInfo = (RequestDetails)be.Info;
            request = requestInfo;
            PopulateStatusReasons();
            dirtyDataHandler(sender, e);
        }

        private void PopulateStatusReasons()
        {
            if (RequestDetails.IsReasonRequired(request.Status))
            {
                groupBoxRequiredImg.Visible = true;
                statusRequiredImg.Visible = false;
                reasonGroupBox.Text = reasonGroupBox.Text.PadLeft(reasonGroupBox.Text.Trim().Length + 6);
            }
            else
            {
                reasonGroupBox.Text = reasonGroupBox.Text.Trim();
                groupBoxRequiredImg.Visible = false;
                statusRequiredImg.Visible = true;
            }

            statusValueLabel.Text = EnumUtilities.GetDescription(request.Status);

            if (request.StatusReason != null)
            {
                string[] reasons = request.StatusReason.Split(ROIConstants.StatusReasonDelimiter.ToCharArray(),
                                                                  StringSplitOptions.RemoveEmptyEntries);

                currentReasonValueLabel.Text = string.Empty;
                foreach (string reason in reasons)
                {
                    currentReasonValueLabel.Text += reason + Environment.NewLine;
                }
            }
        }

        /// <summary>
        /// Close the Update Request Status Dialog
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CancelChangeRequestStatusDialog(object sender, EventArgs e)
        {
            ((ChangeRequestStatusUI)sender).ParentForm.Close();
        }
		
		/// <summary>
        /// Occurs when the user is new patient requestor.
        /// </summary>
        /// <param name="requestorInfo"></param>
        private void ShowSaveNewPatientRequestorDialog(RequestorDetails requestorInfo)
        {

            EventHandler saveNewPatientRequestorHandler = new EventHandler(Process_SaveNewPatientRequestor);
            EventHandler cancelHandler = new EventHandler(Process_CancelSaveNewPatientRequestorDialog);

            SaveNewPatientRequestorUI requestorProfile = new SaveNewPatientRequestorUI(saveNewPatientRequestorHandler, cancelHandler, Pane);
            requestorProfile.InitRequestorInfoTabPage(requestorInfo);
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("savenewpatientrequestor.title"), requestorProfile);
            form.FormClosing += delegate { requestorProfile.CleanUp(); };
            form.ShowDialog(this);
        }

        /// <summary>
        /// Process the new patient requestor
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SaveNewPatientRequestor(object sender, EventArgs e)
        {
            ((SaveNewPatientRequestorUI)sender).ParentForm.Close();
            BaseEventArgs be = (BaseEventArgs)e;
            PopulateRequestorInfo((RequestorDetails)be.Info, true);
            dirtyDataHandler(sender, e);
        }

        private void Process_CancelSaveNewPatientRequestorDialog(object sender, EventArgs e)
        {
            ((SaveNewPatientRequestorUI)sender).ParentForm.Close();
            Process_CancelCreateRequest(sender, e);
        }
        
        private void ShowPatientMatchingDialog(RequestorDetails requestorInfo)
        {

            EventHandler patientSelectionHandler = new EventHandler(Process_PatientSelection);
            EventHandler cancelHandler           = new EventHandler(Process_CancelPatientMatchingDialog);

            PatientMatchingUI patientMatchingDialogUI = new PatientMatchingUI(patientSelectionHandler, cancelHandler, Pane);
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            patientMatchingDialogUI.Header = new HeaderUI();
            patientMatchingDialogUI.Header.Title = rm.GetString("selectpatient.header.title");
            patientMatchingDialogUI.Header.Information = rm.GetString("selectpatient.header.info");
            
            try
            {                
                ROIViewUtility.MarkBusy(true);
                request.Requestor = requestorInfo;
                FindPatientResult searchPatientResult = RequestorController.Instance.SearchMatchingPatients(requestorInfo);
                patientMatchingDialogUI.SetData((RequestDetails)ROIViewUtility.DeepClone(request), searchPatientResult.PatientSearchResult);
                ROIViewUtility.ConvertToForm(null, rm.GetString("selectpatient.title"), patientMatchingDialogUI).ShowDialog(this);
            }
            catch (ROIException cause)
            {       
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        private void Process_PatientSelection(object sender, EventArgs e)
        {

            Process_CancelPatientMatchingDialog(sender, null);
            if (selectRequestor != null)
            {
                Process_CancelSelectRequestorDialog(selectRequestor, null);
            }

            BaseEventArgs bae = (BaseEventArgs)e;
            RequestDetails matchedRequest = (RequestDetails)bae.Info;

            if (request.Id == 0)
            {
                request.Requestor = matchedRequest.Requestor;

                foreach (RequestPatientDetails patient in matchedRequest.Patients.Values)
                {
                    if (!request.Patients.ContainsKey(patient.Key))
                    {
                        request.Patients.Add(patient.Key, patient);
                    }
                }
            }
            else
            {
                changedRequestorIsPatient = true;
                request = matchedRequest;
                //RequestEvents.OnRequestorChangedToPatient(Pane, new ApplicationEventArgs(null, this));
            }

            //PopulateRequestorInfo(request.Requestor, true);
        }

        private void Process_CancelPatientMatchingDialog(object sender, EventArgs e)
        {
            ((PatientMatchingUI)sender).ParentForm.Close();
        }
       

        /// <summary>
        /// Method to populate the requestor information.
        /// </summary>
        /// <param name="requestorInfo"></param>
        private void PopulateRequestorInfo(RequestorDetails requestorInfo, bool requestorChanged)
        {
            request.Requestor = requestorInfo;

            request.RequestorId = requestorInfo.Id;
            request.RequestorType = requestorInfo.Type;

            request.RequestorName = requestorInfo.FullName;
            request.RequestorTypeName = requestorInfo.TypeName;

            requestorNameValueLabel.Text = request.RequestorName;
            toolTip.SetToolTip(requestorNameValueLabel, requestorNameValueLabel.Text);
            requestorTypeValueLabel.Text = request.RequestorTypeName;
            toolTip.SetToolTip(requestorTypeValueLabel, requestorTypeValueLabel.Text);
            
            request.CertificationLetterRequired = requestorInfo.LetterRequired;
            request.PrepayRequired = requestorInfo.PrepaymentRequired;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            patientValueLabel.Text = string.Empty;
            if (requestorInfo.IsPatientRequestor)
            {
                patientValueLabel.Text += rm.GetString("dobShortLabel") + " ";
                patientValueLabel.Text += (!requestorInfo.PatientDob.HasValue) ? string.Empty : requestorInfo.PatientDob.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                patientValueLabel.Text += "      " + rm.GetString("ssnLabel") + " " + ROIViewUtility.ApplyMask(requestorInfo.PatientSSN);
            }

            if (requestorChanged)
            {
                faxTextBox.Text          = request.RequestorFax          = requestorInfo.Fax;
                homePhoneTextBox.Text    = request.RequestorHomePhone    = requestorInfo.HomePhone;
                workPhoneTextBox.Text    = request.RequestorWorkPhone    = requestorInfo.WorkPhone;
                cellPhoneTextBox.Text    = request.RequestorCellPhone    = requestorInfo.CellPhone;
                contactNameTextBox.Text  = request.RequestorContactName  = requestorInfo.ContactName;
                contactPhoneTextBox.Text = request.RequestorContactPhone = requestorInfo.ContactPhone;
            }
            else
            {
                faxTextBox.Text          = request.RequestorFax;
                homePhoneTextBox.Text    = request.RequestorHomePhone;
                workPhoneTextBox.Text    = request.RequestorWorkPhone;
                cellPhoneTextBox.Text    = request.RequestorCellPhone;
                contactNameTextBox.Text  = request.RequestorContactName;
                contactPhoneTextBox.Text = request.RequestorContactPhone;
            }
            contactInfoGroupBox.Enabled = true;
        }

        /// <summary>
        /// Sets accept button.
        /// </summary>
        public override void SetAcceptButton()
        {
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = requestInfoActionUI.SaveButton;
            }
            //if (isLoaded)
            //{
            //    RefreshData();
            //}
            //isLoaded = true;
        }

        /// <summary>
        /// Refresh the request reasons.
        /// </summary>
        private void RefreshData()
        {
            ((RequestInfoEditor)(this.Pane.ParentPane)).PrePopulate();
        }

        /// <summary>
        /// Opens AuthRequest pages in winDss viewer.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ViewAuthReqButton_Click(object sender, EventArgs e)
        {
            WinDocumentViewer viewer = new WinDocumentViewer();
            IList<PageDetails> pages = new List<PageDetails>();
            //request.AuthRequest = "IMAGES1   05980700001";
            string IMNetIds = PatientController.Instance.RetrieveImnetsByImnet(request.AuthRequest.Trim());
            //CR# 356,927
            DocumentDetails document = new DocumentDetails();
			//CR# 380555
            if (!string.IsNullOrEmpty(request.AuthRequestDocumentDateTime))
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


        public static bool ShowOverrideRequestInformationDialog(ExecutionContext context)
        {
            ResourceManager rm = context.CultureManager.GetCulture(CultureType.Message.ToString());

            rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());

            string titleText = rm.GetString(OverrideRequestInfoDialogTitle);
            string messageText = rm.GetString(OverrideRequestInfoDialogMessage);
            string okButtonText = rm.GetString(OverrideRequestInfoDialogOkButton);
            string cancelButtonText = rm.GetString(OverrideRequestInfoDialogCancelButton);

            rm = context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(OverrideRequestInfoDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(OverrideRequestInfoDialogCancelButtonToolTip);
            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText,
                                          okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        public void ShowInactiveRequestorDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(InactiveRequestorDialogMessage);
            string titleText = rm.GetString(InactiveRequestorDialogTitle);
            string okButtonText = rm.GetString(InactiveRequestorDialogOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(InactiveRequestorDialogOkButtonToolTip);

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        private void ShowCancelDeniedRequestDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messgeText = rm.GetString(CancelDeniedReuestDialogMessage);
            string titleText = rm.GetString(CancelDeniedRequestDialogTitile);
            string okButtonText = rm.GetString(CancelDeniedRequestDialogOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(CancelDeniedRequestDialogOkButtonToolTip);

            ROIViewUtility.ConfirmChanges(titleText, messgeText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }
       

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            if (request.Id > 0 && !IsAllowed(ROISecurityRights.ROIModifyRequest))
            {
                contactInfoGroupBox.Enabled = false;
                reasonGroupBox.Enabled = false;
                changeRequestorButton.Enabled = selectReqButton.Enabled = false;
                receiptDateTimePicker.Enabled = false;
                reasonCombo.Enabled = false;
                requestInfoActionUI.Enabled = false;
            }
        }

        public void CheckMigratedRequest()
        {
            if (ROIConstants.MigrationRequest.Equals(reasonCombo.Text))
            {
                reasonCombo.Enabled = false;
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
                if (!isDirty)
                {
                    EnableEvents();
                }
            }
        }

        #endregion
    }
}