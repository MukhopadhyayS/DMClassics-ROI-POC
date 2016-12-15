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
using System.Globalization;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.Controller;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{

    /// <summary>
    /// Maintains Request Status and Status reasons
    /// </summary>
    public partial class ChangeRequestStatusUI : ROIBaseUI
    {
        #region Fields

        private const string PartiallyReleasedDialogTitle               = "PartiallyReleasedDialog.Title";
        private const string PartiallyReleasedDialogOkButton            = "PartiallyReleasedDialog.OkButton";
        private const string PartiallyReleasedDialogCancelButton        = "PartiallyReleasedDialog.CancelButton";
        private const string PartiallyReleasedDialogOkButtonToolTip     = "PartiallyReleasedDialog.OkButton";
        private const string PartiallyReleasedDialogCancelButtonToolTip = "PartiallyReleasedDialog.CancelButton";
        private const string PartiallyReleasedDialogMessage             = "PartiallyReleasedDialog.Message";

        private const string PatientWithoutReleaseDialogTitle               = "PatientWithoutReleaseDialog.Title";
        private const string PatientWithoutReleaseDialogOkButton            = "PatientWithoutReleaseDialog.OkButton";
        private const string PatientWithoutReleaseDialogCancelButton        = "PatientWithoutReleaseDialog.CancelButton";
        private const string PatientWithoutReleaseDialogOkButtonToolTip     = "PatientWithoutReleaseDialog.OkButton";
        private const string PatientWithoutReleaseDialogCancelButtonToolTip = "PatientWithoutReleaseDialog.CancelButton";
        private const string PatientWithoutReleaseDialogMessage             = "PatientWithoutReleaseDialog.Message";

        private const string NoDocumentsReleasedDialogTitle           = "NoDocumentsReleasedDialog.Title";
        private const string NoDocumentsReleasedDialogOkButton        = "NoDocumentsReleasedDialog.OkButton";
        private const string NoDocumentsReleasedDialogOkButtonToolTip = "NoDocumentsReleasedDialog.OkButton";
        private const string NoDocumentsReleasedDialogMessage         = "NoDocumentsReleasedDialog.Message";

        private HeaderUI header;
        private EventHandler cancelHandler;
        private EventHandler changeRequestStatusHandler;
        private EventHandler statusComboChanaged;
        private RequestDetails request;

        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI conrtrols
        /// </summary>
        public ChangeRequestStatusUI()
        {
            InitializeComponent();
            statusComboChanaged = new EventHandler(statusCombo_SelectedIndexChanged);
        }

        /// <summary>
        /// Initialize the action handlers
        /// </summary>
        /// <param name="changeRequestStatusHandler"></param>
        /// <param name="cancelHandler"></param>
        public ChangeRequestStatusUI(EventHandler changeRequestStatusHandler, EventHandler cancelHandler, IPane pane)
            : this()
        {
            this.changeRequestStatusHandler = changeRequestStatusHandler;
            this.cancelHandler = cancelHandler;
        
            SetPane(pane);
            SetExecutionContext(pane.Context);
            CreateDialogHeader();
            Localize();
        }

        #endregion

        #region Methods
        
        /// <summary>
        /// Creates Dialog Header
        /// </summary>
        private void CreateDialogHeader()
        {
            Header = new HeaderUI();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Header.Title = rm.GetString("changerequeststatus.header.title");
            Header.Information = rm.GetString("changerequeststatus.header.info");
        }

        /// <summary>
        /// Apply Localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, statusLabel);
            SetLabel(rm, addFreeFormReasonButton);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, reminderLabel);
            SetLabel(rm, reasonsGroupBox);
            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, cancelButton);
            SetTooltip(rm, toolTip, addFreeFormReasonButton);
        }

        /// <summary>
        /// Get Localized key for Ui controls.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Get Localize key for showing tooltip.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control.Name == "requiredLabel")
            {
                return base.GetLocalizeKey(control);
            }

            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Add new freeform reason into reason panel
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void addFreeFormReasonButton_Click(object sender, EventArgs e)
        {
            FreeformReasonUI freeformReason = CreateFreeformReason();
            reasonPanel.Controls.Add(freeformReason);
            freeformReason.DirtyDataHandler += new EventHandler(statusReasonDirtyData);
            freeformReason.SetFocus();
            saveButton.Enabled = false;
        }

        private void statusReasonDirtyData(object sender, EventArgs e)
        {
            EnableButtons(true);
        }

        /// <summary>
        /// Create new freform reason
        /// </summary>
        /// <returns></returns>
        private FreeformReasonUI CreateFreeformReason()
        {
            FreeformReasonUI freeFormReason = new FreeformReasonUI();
            freeFormReason.SetExecutionContext(Context);
            freeFormReason.SetPane(Pane);
            freeFormReason.Localize();
            freeFormReason.DeleteFreeformReasonHandler = new EventHandler(DeleteFreeformReason);
            freeFormReason.Checked = true;
            if (statusCombo.SelectedValue != null)
            {
                freeFormReason.ImageRequired = RequestDetails.IsReasonRequired((RequestStatus)statusCombo.SelectedValue);
            }

            return freeFormReason;
        }

        /// <summary>
        /// Delete selected freeform reason 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DeleteFreeformReason(object sender, EventArgs e)
        {
            FreeformReasonUI freeFormReason = (FreeformReasonUI)sender;
            int index = reasonPanel.Controls.IndexOf(freeFormReason);
            reasonPanel.Controls.RemoveAt(index);
        }

        /// <summary>
        /// Close the dialog
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            cancelHandler(this, null);
        }

        /// <summary>
        /// handler is used to update the request status
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                ((Form)(this.Parent)).DialogResult = DialogResult.OK;
                RequestDetails requestInfo = (RequestDetails)GetData(ROIViewUtility.DeepClone(request));

                if (((Form)(this.Parent)).DialogResult == DialogResult.OK)
                {
                    //RequestValidator clientValidator = new RequestValidator();
                    //if (!clientValidator.ValidateRequestStatus(requestInfo))
                    //{
                    //    throw clientValidator.ClientException;
                    //}

                    if (requestInfo.Id >= 0 && requestInfo.Status == RequestStatus.Completed)
                    {
                        if (!CheckRequestReleaseStatus(requestInfo, Context))
                        {
                            //Show the Change request status dialog window.
                            ((Form)(this.Parent)).DialogResult = DialogResult.None;
                            return;
                        }
                    }
                    else if (request.Id >= 0 && request.Status == RequestStatus.Completed)
                    {
                        if (!(requestInfo.Status == RequestStatus.Logged || requestInfo.Status == RequestStatus.Pended))
                        {
                            SetData(request);
                            return;
                        }
                    }

                    if (request.Status != requestInfo.Status)
                    {
                        request.StatusChanged = DateTime.Now.Date;
                    }
                    this.request = requestInfo;
                    changeRequestStatusHandler(this, new BaseEventArgs(request));
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
        }

        /// <summary>
        /// Checks the release status of the request
        /// </summary>
        /// <param name="releaseStaus"></param>
        /// <returns></returns>
        public bool CheckRequestReleaseStatus(RequestDetails request, ExecutionContext context)
        {
            if (!(request.ReleaseCount > 0))
            {
                ShowMessageForNothingRelease(context);
                return false;
            }
            else if (request.IsPartiallyReleased)
            {
                return ShowMessageForPartiallyReleased(context);
            }

            else if (HasPatientWithNoReleaseRecords)
            {
                return ShowMessageForPatientWithoutRelease(context);
            }

            return true;
        }
        
        private bool HasPatientWithNoReleaseRecords
        {
            get
            {
                ReleaseDetails releaseDetails = new ReleaseDetails();

                Application.DoEvents();
                RequestPatients requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);
                request.Patients.Clear();

                foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                {
                    request.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                    ReleasedPatientDetails releasedPatient = new ReleasedPatientDetails();
                    releaseDetails.ReleasedPatients.Add(requestPatientDetails.Key, releasedPatient.AssignRequestToReleasedPatient(requestPatientDetails));
                    request.Releases.Add(releaseDetails);
                }
                
                bool hasReleaseDocuments;
                foreach (RequestPatientDetails patient in request.Patients.Values)
                {
                    hasReleaseDocuments = false;
                    foreach (ReleaseDetails release in request.Releases)
                    {
                        if (release.ReleasedPatients.ContainsKey(patient.Key))
                        {
                            hasReleaseDocuments = true;
                            break;
                        }
                    }

                    if (!hasReleaseDocuments)
                    {
                        return true;
                    }
                }
                return false;
            }
                
        }

        /// <summary>
        /// Show the dialog when no documents released for this request
        /// </summary>
        /// <returns></returns>
        private static bool ShowMessageForNothingRelease(ExecutionContext context)
        {
            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(NoDocumentsReleasedDialogMessage);
            string titleText = rm.GetString(NoDocumentsReleasedDialogTitle);
            string okButtonText = rm.GetString(NoDocumentsReleasedDialogOkButton);

            rm = context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(NoDocumentsReleasedDialogOkButtonToolTip);
            
            return !ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Show the dialog when documents are partially released for this request
        /// </summary>
        /// <returns></returns>
        private static bool ShowMessageForPartiallyReleased(ExecutionContext context)
        {

            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(PartiallyReleasedDialogMessage);
            string titleText = rm.GetString(PartiallyReleasedDialogTitle);
            string okButtonText = rm.GetString(PartiallyReleasedDialogOkButton);
            string cancelButtonText = rm.GetString(PartiallyReleasedDialogCancelButton);

            rm = context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(PartiallyReleasedDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(PartiallyReleasedDialogCancelButtonToolTip); 
            
            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Show the dialog if one or more patients with no documents specified for release for this request
        /// </summary>
        /// <returns></returns>
        private static bool ShowMessageForPatientWithoutRelease(ExecutionContext context)
        {

            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(PatientWithoutReleaseDialogMessage);
            string titleText = rm.GetString(PatientWithoutReleaseDialogTitle);
            string okButtonText = rm.GetString(PatientWithoutReleaseDialogOkButton);
            string cancelButtonText = rm.GetString(PatientWithoutReleaseDialogCancelButton);

            rm = context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(PatientWithoutReleaseDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(PatientWithoutReleaseDialogCancelButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        private void EnableButtons(bool isEnable)
        {
            bool hasReasons = false;
            if (statusCombo.SelectedValue == null) return;
            if (RequestDetails.IsReasonRequired((RequestStatus)statusCombo.SelectedValue))
            {
                foreach (Control reasonCtrl in reasonPanel.Controls)
                {
                    if (typeof(CheckBox).IsAssignableFrom(reasonCtrl.GetType()))
                    {
                        if ((reasonCtrl as CheckBox).Checked)
                        {
                            hasReasons = true;                            
                        }
                    }
                    if (typeof(FreeformReasonUI).IsAssignableFrom(reasonCtrl.GetType()))
                    {
                        FreeformReasonUI freeformReasonUI = reasonCtrl as FreeformReasonUI;
                        if (freeformReasonUI.Checked)
                        {
                            if (freeformReasonUI.ReasonName.Trim().Length > 0)
                            {
                                hasReasons = true;
                            }
                        }
                    }
                }
                saveButton.Enabled = hasReasons;
            }
            else
            {
                saveButton.Enabled = isEnable;
            }
        }
            
        /// <summary>
        /// Populate all Request Status 
        /// </summary>
        private void PopulateRequestStatus()
        {
            DisableEvents();
            IList requestStatuses = EnumUtilities.ToList(typeof(RequestStatus));

            requestStatuses.RemoveAt(0);
            RequestDetails requestDetails = (RequestDetails)(Pane.ParentPane as RequestInfoEditor).Request;
            RemoveStatus(requestStatuses, RequestStatus.AuthReceived);
            RemoveStatus(requestStatuses, RequestStatus.AuthReceivedDenied);
            RemoveStatus(requestStatuses, RequestStatus.PreBilled);
            RemoveStatus(requestStatuses, RequestStatus.Unknown);
            if (!ROIViewUtility.IsAllowed(ROISecurityRights.ROICancelRequest) || request.ReleaseCount > 0 && !requestDetails.Status.Equals(RequestStatus.Logged)
                && !requestDetails.Status.Equals(RequestStatus.Pended))
            {
                RemoveStatus(requestStatuses, RequestStatus.Canceled);
            }

            if (!ROIViewUtility.IsAllowed(ROISecurityRights.ROIPendingRequest))
            {
                RemoveStatus(requestStatuses, RequestStatus.Pended);
            }

            if (!ROIViewUtility.IsAllowed(ROISecurityRights.ROIDenyRequest) || request.ReleaseCount > 0 && !requestDetails.Status.Equals(RequestStatus.Logged)
                && !requestDetails.Status.Equals(RequestStatus.Pended) && !requestDetails.Status.Equals(RequestStatus.Logged))
            {
                RemoveStatus(requestStatuses, RequestStatus.Denied);
            }

            statusCombo.DataSource = requestStatuses;
            statusCombo.DisplayMember = "value";
            statusCombo.ValueMember = "key";
            EnableEvents();
        }

        private static void RemoveStatus(IList requestStatuses, RequestStatus status)
        {
            requestStatuses.RemoveAt(requestStatuses.IndexOf(new KeyValuePair<Enum, string>(status,
                                                                                          EnumUtilities.GetDescription(status))));
        }

        private void EnableEvents()
        {
            statusCombo.SelectedIndexChanged += statusComboChanaged;
        }

        private void DisableEvents()
        {
            statusCombo.SelectedIndexChanged -= statusComboChanaged;
        }

        /// <summary>
        /// Sets the data in UI
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            RequestDetails request = data as RequestDetails;
            if (request != null)
            {
                this.request = request;
                PopulateRequestStatus();
                if (request.Status == RequestStatus.Unknown)
                {
                    statusCombo.SelectedValue = RequestStatus.Logged;
                    PopulateFreeFormEntities(RequestStatus.Logged);
                    EnableButtons(true);
                    return;
                }
                statusCombo.SelectedValue = request.Status;
                if (statusCombo.SelectedValue == null)
                {
                    statusCombo.DropDownStyle = ComboBoxStyle.DropDown;
                    statusCombo.Text = EnumUtilities.GetDescription(request.Status);
                    return;
                }
                PopulateFreeFormEntities(request.Status);
                EnableButtons(false);
            }
        }

        /// <summary>
        /// Gets the data
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            RequestDetails requestInfo = data as RequestDetails;

            if (statusCombo.SelectedValue == null) return requestInfo;

            requestInfo.Status = (RequestStatus)statusCombo.SelectedValue;
           
            StringBuilder reasonBuilder = new StringBuilder();
            CheckBox reasonCheckBox;
            FreeformReasonUI freeformReason;
            requestInfo.FreeformReasons = string.Empty;
            errorProvider.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString()); 

            foreach (Control reasonCtrl in reasonPanel.Controls)
            {
                if (typeof(CheckBox).IsAssignableFrom(reasonCtrl.GetType()))
                {
                    reasonCheckBox = reasonCtrl as CheckBox;
                    if (reasonCheckBox.Checked)
                    {
                        reasonBuilder.Append(reasonCheckBox.Text).Append(ROIConstants.StatusReasonDelimiter);
                    }
                }

                if (typeof(FreeformReasonUI).IsAssignableFrom(reasonCtrl.GetType()))
                {
                    freeformReason = reasonCtrl as FreeformReasonUI;
                    if (freeformReason.Checked && freeformReason.ReasonName.Trim().Length > 0)
                    {   
                        if (!Validator.Validate(freeformReason.ReasonTextBox.Text, ROIConstants.NameValidation))
                        {
                            errorProvider.SetError(freeformReason.ReasonTextBox, rm.GetString(ROIErrorCodes.InvalidName));
                            ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        }
                        else
                        {
                            reasonBuilder.Append(freeformReason.ReasonName.Trim()).Append(ROIConstants.StatusReasonDelimiter);
                            requestInfo.FreeformReasons = requestInfo.FreeformReasons + freeformReason.ReasonName.Trim() + ROIConstants.StatusReasonDelimiter;
                        }
                    }
                }
            }

            requestInfo.StatusReason = reasonBuilder.ToString().TrimEnd(ROIConstants.StatusReasonDelimiter.ToCharArray());

            return requestInfo;
        }

        /// <summary>
        /// Occurs when user change the selection in status combo box.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void statusCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (statusCombo.SelectedValue == null) return;

            RequestStatus status = (RequestStatus)statusCombo.SelectedValue;

            if (status == RequestStatus.Pended || status == RequestStatus.Denied
                                               || status == RequestStatus.Canceled)
            {
                reminderLabel.Visible = !ROIAdminController.Instance.HasLetterTemplate(LetterType.Other);
            }
            else
            {
                reminderLabel.Visible = false;
            }

            //if (status == RequestStatus.Completed && 
            //    request.Status != RequestStatus.Completed)
            //{
            //    if (!CheckRequestReleaseStatus(request, Context))
            //    {
            //        statusCombo.Text = request.Status.ToString();
            //        status = request.Status;
            //        PopulateFreeFormEntities(status);
            //        EnableButtons(true);
            //        return;
            //    }
            //}
            //else if (request.Id != 0 && request.Status == RequestStatus.Completed)
            //{
            //    if (!(status == RequestStatus.Logged || status == RequestStatus.Pended))
            //    {
            //        statusCombo.Text = RequestStatus.Completed.ToString();
            //        status = RequestStatus.Completed;
            //        PopulateFreeFormEntities(status);
            //        EnableButtons(true);
            //        return;
            //    }
            //}

            PopulateFreeFormEntities(status);
            EnableButtons(true);
        }

        private void PopulateFreeFormEntities(RequestStatus status)
        {

            string[] serverStatusReasons = ROIAdminController.Instance.RetrieveReasonsByStatus(Convert.ToInt32(status, System.Threading.Thread.CurrentThread.CurrentUICulture));
            PopulateStatusReason(serverStatusReasons);

            if (RequestDetails.IsReasonRequired(status))
            {
                groupBoxRequiredImg.Visible = true;
                reasonsGroupBox.Text = reasonsGroupBox.Text.PadLeft(reasonsGroupBox.Text.Trim().Length + 4);
            }
            else
            {
                reasonsGroupBox.Text = reasonsGroupBox.Text.Trim();
                groupBoxRequiredImg.Visible = false;
            }
        }

        /// <summary>
        /// Populate the reasons
        /// </summary>
        /// <param name="serverStatusReasons"></param>
        private void PopulateStatusReason(string[] serverStatusReasons)
        {
            reasonPanel.SuspendLayout();
            serverStatusReasons = (serverStatusReasons == null) ? new string[0] : serverStatusReasons;
            string[] clientStatusResons = (request.StatusReason == null) 
                                 ? new string[0]
                                 : request.StatusReason.Split(ROIConstants.StatusReasonDelimiter.ToCharArray(), StringSplitOptions.RemoveEmptyEntries);
            string[] freeFormStatusResons = (request.FreeformReasons == null)
                                 ? new string[0]
                                 : request.FreeformReasons.Split(ROIConstants.StatusReasonDelimiter.ToCharArray(), StringSplitOptions.RemoveEmptyEntries);
            
            reasonPanel.Controls.Clear();

            CheckBox reasonCheckBox;
            foreach (string serverReason in serverStatusReasons)
            {
                reasonCheckBox = new CheckBox();
                reasonCheckBox.AutoSize = true;
                reasonCheckBox.Text = serverReason;
                reasonPanel.Controls.Add(reasonCheckBox);
                int index = Array.IndexOf(clientStatusResons, serverReason) ;
                if (index != -1)
                {
                    reasonCheckBox.Checked = true;
                    Array.Clear(clientStatusResons, index, 1);
                }
                reasonCheckBox.CheckedChanged += new EventHandler(statusReasonDirtyData);
            }

            FreeformReasonUI freeformReason;
            if (request.Status == ((RequestStatus)statusCombo.SelectedValue))
            {
                foreach (string clientReason in clientStatusResons)
                {
                    if (clientReason != null)
                    {
                        freeformReason = CreateFreeformReason();
                        freeformReason.ReasonName = clientReason;
                        freeformReason.Checked = true;
                        reasonPanel.Controls.Add(freeformReason);
                        freeformReason.DirtyDataHandler += new EventHandler(statusReasonDirtyData);
                    }
                }
            }
            else
            {
                foreach (string clientReason in freeFormStatusResons)
                {
                    if (clientReason != null)
                    {
                        freeformReason = CreateFreeformReason();
                        freeformReason.ReasonName = clientReason;
                        freeformReason.Checked = true;
                        reasonPanel.Controls.Add(freeformReason);
                        freeformReason.DirtyDataHandler = new EventHandler(statusReasonDirtyData);
                    }
                }
            }
            reasonPanel.ResumeLayout();
        }

        #endregion

        #region Properties

        public HeaderUI Header
        {
            get { return header; }
            set 
            { 
              header = value;
              header.Dock = DockStyle.Fill;
              topPanel.Controls.Add(header);
            }
        }

        #endregion
    }
}
