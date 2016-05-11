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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Configuration;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Requestors.View.FindRequestor
{
    /// <summary>
    /// Display the FindRequestorSearchUI.
    /// </summary>
    public partial class FindRequestorSearchUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(FindRequestorSearchUI));

        #region Fields

        private const string SelectOption = "  /  /    ";

        private const string NoPatientMatchingDialogTitle = "NoPatientMatchingDialog.Title";
        private const string NoPatientMatchingDialogMessage = "NoPatientMatchingDialog.Message";
        private const string NoPatientMatchingDialogOkButton = "NoPatientMatchingDialog.OkButton";
        private const string NoPatientMatchingDialogOkButtonToolTip = "NoPatientMatchingDialog.OkButton";

        //private const string CustomDateFormat = "MMMM dd, yyyy";        
        private string allRequestorTypes;

        private EventHandler keyPressHandler;
        private EventHandler newRequestorHandler;
        private EventHandler activeHandler;
        private EventHandler inActiveHandler;

        private FindRequestorResult searchResult;
        private static bool isError;
		private static bool isFindRequestor;

        #endregion

        #region Constructor

        /// <summary>
        /// Initilize the Find Requestor Search UI.
        /// </summary>
        public FindRequestorSearchUI()
        {
            InitializeComponent();
            SetSsnFormat();
            Init();            
        }

        #endregion

        #region Methods

        private void Init()
        {
            //dobDatePicker.Format = DateTimePickerFormat.Custom;
            //dobDatePicker.CustomFormat = CustomDateFormat;            

            dobMoreInfo.Image = ROIImages.MoreInfoIcon;
            prefixLabel.Text = (UserData.Instance.EpnPrefix != null) ? 
                                    UserData.Instance.EpnPrefix.Trim() : string.Empty;
            toolTip.SetToolTip(prefixLabel, (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() : string.Empty);

            recentRequestorsRadioButton.Checked = true;
            keyPressHandler      = new EventHandler(Process_KeyPressedHandler);
            newRequestorHandler  = new EventHandler(DefaultNewRequestorHandler);
            activeHandler        = new EventHandler(OnActive);
            inActiveHandler      = new EventHandler(OnInActive);
            
            recentRequestorsRadioButton.Focus();
            EnableEvents();
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, filterByLabel);
            SetLabel(rm, requestorTypeLabel);
            SetLabel(rm, requestorNameLabel);
            SetLabel(rm, requestorFirstNameLabel);
            SetLabel(rm, requestorLastNameLabel);
            SetLabel(rm, patientEpnLabel);
            SetLabel(rm, patientDobLabel);
            SetLabel(rm, patientSsnLabel);
            SetLabel(rm, patientMrnLabel);
            SetLabel(rm, patientFacilityLabel);
            SetLabel(rm, allRequestorsRadioButton);
            SetLabel(rm, recentRequestorsRadioButton);
            SetLabel(rm, findRequestorButton);
            SetLabel(rm, newRequestorButton);
            SetLabel(rm, resetButton);
            SetLabel(rm, requestorStatusLabel);


            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, allRequestorsRadioButton);
            SetTooltip(rm, toolTip, recentRequestorsRadioButton);
            SetTooltip(rm, toolTip, dobTextBox);
            SetTooltip(rm, toolTip, dobMoreInfo);
            SetTooltip(rm, toolTip, findRequestorButton);
            SetTooltip(rm, toolTip, newRequestorButton);
            SetTooltip(rm, toolTip, patientEpnTextBox);
            SetTooltip(rm, toolTip, patientSsnTextBox);
            SetTooltip(rm, toolTip, patientMrnTextBox);
            SetTooltip(rm, toolTip, patientFacilityComboBox);
            SetTooltip(rm, toolTip, requestorNameTextBox);
            SetTooltip(rm, toolTip, requestorLastNameTextBox);
            SetTooltip(rm, toolTip, requestorFirstNameTextBox);
            SetTooltip(rm, toolTip, requestorTypeComboBox);
            SetTooltip(rm, toolTip, resetButton);
            SetTooltip(rm, toolTip, statusCombo);
        }

        /// <summary>
        /// Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Enable the Events
        /// </summary>
        private void EnableEvents()
        {
            requestorNameTextBox.TextChanged             += keyPressHandler;
            requestorLastNameTextBox.TextChanged         += keyPressHandler;
            requestorFirstNameTextBox.TextChanged        += keyPressHandler;
            patientEpnTextBox.TextChanged                += keyPressHandler;
            patientSsnTextBox.TextChanged                += keyPressHandler;
            patientMrnTextBox.TextChanged                += keyPressHandler;
            patientFacilityComboBox.SelectedIndexChanged += keyPressHandler;
            dobTextBox.TextChanged                       += keyPressHandler;
            requestorTypeComboBox.SelectedIndexChanged   += keyPressHandler;
            statusCombo.SelectedIndexChanged             += keyPressHandler;
            
            patientSsnTextBox.Enter += activeHandler;
            patientSsnTextBox.Leave += inActiveHandler;
        }

        /// <summary>
        /// Enable the Events
        /// </summary>
        private void DisableEvents()
        {
            requestorNameTextBox.TextChanged             -= keyPressHandler;
            requestorLastNameTextBox.TextChanged         -= keyPressHandler;
            requestorFirstNameTextBox.TextChanged        -= keyPressHandler;
            patientEpnTextBox.TextChanged                -= keyPressHandler;
            patientSsnTextBox.TextChanged                -= keyPressHandler;
            patientMrnTextBox.TextChanged                -= keyPressHandler;
            patientFacilityComboBox.SelectedIndexChanged -= keyPressHandler;
            dobTextBox.TextChanged                       -= keyPressHandler;
            requestorTypeComboBox.SelectedIndexChanged   -= keyPressHandler;
            statusCombo.SelectedIndexChanged             -= keyPressHandler;

            patientSsnTextBox.Enter -= activeHandler;
            patientSsnTextBox.Leave -= inActiveHandler;
        }

        /// <summary>
        /// Event invoked when ever there is any change in the value 
        /// of Search criteria. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_KeyPressedHandler(object sender, EventArgs e)
        {
            DisableEvents();
            FindRequestorCriteria findRequestorCriteria = GetData();
            //findRequestorButton.Enabled = RequestorValidator.ValidateRequestorSearch(findRequestorCriteria) &&
            //                              (patientSsnTextBox.MaskedTextProvider.AssignedEditPositionCount == 0 ||
            //                              patientSsnTextBox.MaskedTextProvider.AssignedEditPositionCount > 2);
            
            findRequestorButton.Enabled = RequestorValidator.ValidateRequestorSearch(findRequestorCriteria);

            RequestorTypeDetails requestorType = requestorTypeComboBox.SelectedItem as RequestorTypeDetails;
            if (IsPatientFieldsChanged(findRequestorCriteria) || requestorType.Id < 0)
            {
                requestorNameTextBox.Enabled = false;
                patientInfoPanel.Enabled = requestorLastNameTextBox.Enabled =
                requestorFirstNameTextBox.Enabled = epnPanel.Enabled = true;
                requestorNameTextBox.Text = string.Empty;
            }
            else if (!string.IsNullOrEmpty(findRequestorCriteria.Name) || requestorType.Id > 0)
            {
                EnableNonPatientRequestorFields();
            }
            else
            {
                requestorNameTextBox.Enabled = requestorFirstNameTextBox.Enabled =
                requestorLastNameTextBox.Enabled = patientInfoPanel.Enabled = epnPanel.Enabled = true;
            }

            if (IsPatientFieldsChanged(findRequestorCriteria) && requestorType.Id > 0)
            {
                EnableNonPatientRequestorFields();
            }
            EnableEvents();
        }

        private bool IsPatientFieldsChanged(FindRequestorCriteria findRequestorCriteria)
        {
            return (!string.IsNullOrEmpty(findRequestorCriteria.FirstName) ||
                    !string.IsNullOrEmpty(findRequestorCriteria.LastName) ||
                    (UserData.Instance.EpnEnabled ? !string.IsNullOrEmpty(findRequestorCriteria.PatientEPN) : false) ||
                    (dobTextBox.Text.Trim() != SelectOption.Trim()) ||
                    !string.IsNullOrEmpty(findRequestorCriteria.PatientSSN) ||
                    !string.IsNullOrEmpty(findRequestorCriteria.PatientMRN) ||
                    !string.IsNullOrEmpty(findRequestorCriteria.PatientFacilityCode));
        }

        private void EnableNonPatientRequestorFields()
        {
            requestorNameTextBox.Enabled = true;
            patientInfoPanel.Enabled = requestorLastNameTextBox.Enabled =
            requestorFirstNameTextBox.Enabled = epnPanel.Enabled = false;
            requestorLastNameTextBox.Text = requestorFirstNameTextBox.Text =
            patientEpnTextBox.Text = patientSsnTextBox.Text = patientMrnTextBox.Text = string.Empty;
            SetDOBDateNullValue();
            patientFacilityComboBox.SelectedIndex = 0;
        }
        

        /// <summary>
        /// Occurs when the control becomes active control of the view
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnActive(object sender, EventArgs e)
        {
            ROIViewUtility.RemoveMask(patientSsnTextBox);
        }

        /// <summary>
        /// Occurs when the control is no longer the active control of the view 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnInActive(object sender, EventArgs e)
        {
            if (UserData.Instance.IsSSNMasked)
            {
                ROIViewUtility.ApplyMask(patientSsnTextBox);
            }
        }

        /// <summary>
        /// Occurs When DobDatePicker value gets changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void dobDatePicker_ValueChanged(object sender, EventArgs e)
        {
            //dobDatePicker.CustomFormat = CustomDateFormat;
        }

        /// <summary>
        /// Get the Requestor Details.
        /// </summary>
        /// <returns></returns>
        internal FindRequestorCriteria GetData()
        {
            FindRequestorCriteria findRequestorCriteria = new FindRequestorCriteria();
            findRequestorCriteria.AllRequestors         = allRequestorsRadioButton.Checked;

            findRequestorCriteria.RequestorTypeId = (requestorTypeComboBox.SelectedItem as RequestorTypeDetails).Id;
            findRequestorCriteria.RequestorTypeName = (requestorTypeComboBox.SelectedItem as RequestorTypeDetails).Name;
            findRequestorCriteria.LastName = requestorLastNameTextBox.Text.Trim();
            findRequestorCriteria.FirstName = requestorFirstNameTextBox.Text.Trim();
            findRequestorCriteria.Name = requestorNameTextBox.Text.Trim();

            if (dobTextBox.Text.Trim() != SelectOption.Trim() && dobTextBox.IsValidDate)
            {
                findRequestorCriteria.PatientDob = Convert.ToDateTime(dobTextBox.FormattedDate, CultureInfo.InvariantCulture);
            }  
            if (patientEpnTextBox.Text.Trim().Length > 0)
            {
                findRequestorCriteria.PatientEPN = (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() + patientEpnTextBox.Text.Trim()
                                                                                         : patientEpnTextBox.Text.Trim();
            }
            findRequestorCriteria.PatientSSN = (patientSsnTextBox.MaskedTextProvider.AssignedEditPositionCount > 0) ? patientSsnTextBox.Text.Trim() : string.Empty;
            findRequestorCriteria.PatientMRN          = patientMrnTextBox.Text.Trim();
            findRequestorCriteria.PatientFacilityCode = (patientFacilityComboBox.SelectedIndex == 0 || patientFacilityComboBox.SelectedItem == null) ? null : ((FacilityDetails)(patientFacilityComboBox.SelectedItem)).Code;

            if ((patientFacilityComboBox.SelectedIndex == 0 || patientFacilityComboBox.SelectedItem == null))
            {
                findRequestorCriteria.IsFreeformFacility = true;
            }
            else
            {
                findRequestorCriteria.IsFreeformFacility = ((FacilityDetails)(patientFacilityComboBox.SelectedItem)).Type == FacilityType.NonHpf ? true : false;
            }
            
            findRequestorCriteria.MaxRecord           = Context.Configuration.GetPropertyAsInt32("MaximumSearchResult");

            if (statusCombo.SelectedIndex > 0)
            {
                findRequestorCriteria.ActiveRequestor = (statusCombo.SelectedValue.Equals(RequestorStatusType.Active))
                                                            ? true : false;
            }

            findRequestorCriteria.IsPatientRequestor = !(requestorNameTextBox.Enabled);

            return findRequestorCriteria;
        }
       
        /// <summary>
        /// Occurs when Reset button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void resetButton_Click(object sender, EventArgs e)
        {
            ClearData();
            //Commented for performance
            //PopulateRequestorTypes();
            findRequestorButton.Enabled = false;
            ApplicationEventArgs ae = new ApplicationEventArgs(null, this);
            RequestorEvents.OnResetSearch(Pane, ae);
            patientInfoPanel.Enabled = true;
            epnPanel.Enabled = true;
            requestorFirstNameTextBox.Enabled = true;
            requestorLastNameTextBox.Enabled = true;
            requestorNameTextBox.Enabled = true;
        }       

        /// <summary>
        /// Clear Control Values.
        /// </summary>
        private void ClearData()
        {
            recentRequestorsRadioButton.Checked     = true;
            requestorNameTextBox.Text               = string.Empty;
            requestorLastNameTextBox.Text           = string.Empty;
            requestorFirstNameTextBox.Text          = string.Empty;
            patientEpnTextBox.Text                  = string.Empty;
            patientSsnTextBox.Clear();
            patientMrnTextBox.Text                  = string.Empty;
            patientFacilityComboBox.SelectedIndex   = 0;
            requestorTypeComboBox.SelectedIndex     = 0;
            statusCombo.SelectedIndex               = 0;
            SetDOBDateNullValue();
            errorProvider.Clear();
            requestorTypeComboBox.Focus();
        }

        /// <summary>
        /// Clears the searchcritia
        /// </summary>
        public void ClearSearchCriteria()
        {
            if (searchResult != null)
            {
                if (searchResult.RequestorSearchResult.Count == 0)
                {
                    resetButton.PerformClick();
                }
            }
        }

        /// <summary>
        /// Occurs When Find Requestor Button Clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void findRequestorButton_Click(object sender, EventArgs e)
        {
            IsError = false;
            errorProvider.Clear();
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            try
            {
                if (dobTextBox.Text.Trim() != SelectOption.Trim())
                {
                    if (!dobTextBox.IsValidDate)
                    {
                        IsError = true;
                        string errorMessage = rm.GetString(ROIErrorCodes.InvalidDate);
                        errorProvider.SetError(dobTextBox, errorMessage);
                    }
                    else if (!ROIViewUtility.IsValidDate(dobTextBox.Text.Trim()))
                    {
                        IsError = true;
                        string errorMessage = rm.GetString(ROIErrorCodes.InvalidSqlDate);
                        errorProvider.SetError(dobTextBox, errorMessage);
                    }
                }

                searchResult = RequestorController.Instance.FindRequestor(GetData());
                searchResult.RequestorTypeId = (requestorTypeComboBox.SelectedItem as RequestorTypeDetails).Id;
                ApplicationEventArgs ae = new ApplicationEventArgs(searchResult, this);
                RequestorEvents.OnRequestorSearched(Pane, ae);
            }
            catch (ROIException cause)
            {                
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        /// <summary>
        /// Fill Requestor Type in Combo box.
        /// </summary>
        /// <param name="requestorTypeDetails"></param>
        /// 
        public void PrePopulate(Collection<RequestorTypeDetails> requestorTypes)
        {
            DisableEvents();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            allRequestorTypes = rm.GetString("requestorTypeText." + this.GetType().Name);

            RequestorTypeDetails forSelect = new RequestorTypeDetails();
            forSelect.Id = 0;
            forSelect.Name = allRequestorTypes;
            requestorTypes.Insert(0, forSelect);
                       
            requestorTypeComboBox.DisplayMember = "Name";
            requestorTypeComboBox.ValueMember = "Id";
            if (IsFindRequestor)
            {
                foreach (RequestorTypeDetails requestorType in requestorTypes)
                {
                    if (ROIConstants.MigrationRequest.Equals(requestorType.Name))
                    {
                        requestorTypes.Remove(requestorType);
                        break;
                    }
                }
            }
            IsFindRequestor = false;
            requestorTypeComboBox.DataSource = requestorTypes;

            requestorTypeComboBox.SelectedItem = forSelect;

            IList requestorStatus = EnumUtilities.ToList(typeof(RequestorStatusType));
            statusCombo.DataSource = requestorStatus;
            statusCombo.DisplayMember = "value";
            statusCombo.ValueMember = "key";
            statusCombo.SelectedItem = 0;
            //SetAcceptButton();
            requestorTypeComboBox.Focus();

            PopulateFacilities();
            SetDOBDateNullValue();
            EnableEvents();
        }

        private void PopulateFacilities()
        {
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                patientFacilityComboBox.Items.Add(fac);
            }

            patientFacilityComboBox.Items.Insert(0, new FacilityDetails(ROIConstants.AllFacilities, ROIConstants.AllFacilities, FacilityType.Hpf));
            patientFacilityComboBox.DisplayMember = "Name";
            patientFacilityComboBox.ValueMember = "Code";
            patientFacilityComboBox.SelectedIndex = 0;           
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the presses the Enter key
        /// </summary>
        public override void SetAcceptButton()
        {
            if (this.ParentForm != null && this.ParentForm.AcceptButton != findRequestorButton)
            {
                this.ParentForm.AcceptButton = findRequestorButton;
                ClearSearchCriteria();
            }
        }

        /// <summary>
        /// occurs When Selected value gets changed in the combobox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void requestorTypeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            bool patientTypeSelected = (requestorTypeComboBox.SelectedItem as RequestorTypeDetails).Id <= 0;

            if (patientTypeSelected)
            {
                epnPanel.Visible = UserData.Instance.EpnEnabled;
                prefixLabel.Visible = true;
                patientInfoPanel.Enabled = true;
            }
            else
            {
                patientEpnTextBox.Text                = string.Empty;
                patientSsnTextBox.Clear();
                patientFacilityComboBox.SelectedIndex = 0;
                patientMrnTextBox.Text                = string.Empty;
                SetDOBDateNullValue();
                epnPanel.Visible = UserData.Instance.EpnEnabled && patientTypeSelected;
                patientInfoPanel.Enabled = false;
            }
        }

        /// <summary>
        /// Occurs When New Requestor Button Invoked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void newRequestorButton_Click(object sender, EventArgs e)
        {
            //ClearData();
            NewRequestorHandler(sender, e);
        }

        private void DefaultNewRequestorHandler(object sender, EventArgs e)
        {
            FindRequestorCriteria requestorCriteria = GetData();

            if (requestorCriteria.RequestorTypeId == RequestorDetails.PatientRequestorType)
            {
                Collection<PatientDetails> patients = RetrieveMatchingPatients(requestorCriteria);
                if (patients != null)
                {
                    RequestorDetails requestor = ShowCreateNewPatientRequestorDialog(patients);
                    if (requestor != null)
                    {
                        ApplicationEventArgs ae = new ApplicationEventArgs(requestor, this);
                        RequestorEvents.OnCreateRequestor(Pane, ae);
                    }
                }
            }
            else
            {
                ClearData();
                ApplicationEventArgs ae = new ApplicationEventArgs(null, this);
                RequestorEvents.OnCreateRequestor(Pane, ae);
            }
        }

        public Collection<PatientDetails> RetrieveMatchingPatients(FindRequestorCriteria requestorCriteria)
        {
            
            bool hasSearchParams = !(string.IsNullOrEmpty(requestorCriteria.FirstName) &&
                                       string.IsNullOrEmpty(requestorCriteria.LastName) &&
                                       !requestorCriteria.PatientDob.HasValue &&
                                       string.IsNullOrEmpty(requestorCriteria.PatientEPN) &&
                                       string.IsNullOrEmpty(requestorCriteria.PatientFacilityCode) &&
                                       string.IsNullOrEmpty(requestorCriteria.PatientMRN) &&
                                       string.IsNullOrEmpty(requestorCriteria.PatientSSN));

            if (hasSearchParams)
            {
                RequestorDetails searchCriteria = new RequestorDetails();
                searchCriteria.FirstName = requestorCriteria.FirstName;
                searchCriteria.LastName = requestorCriteria.LastName;
                searchCriteria.Name = requestorCriteria.LastName + ", " + requestorCriteria.FirstName;
                searchCriteria.Name = searchCriteria.Name.Trim(' ', ',');
                searchCriteria.PatientEPN = requestorCriteria.PatientEPN;
                searchCriteria.PatientFacilityCode = requestorCriteria.PatientFacilityCode;
                searchCriteria.PatientSSN = requestorCriteria.PatientSSN;
                searchCriteria.PatientMRN = requestorCriteria.PatientMRN;
                searchCriteria.Type = requestorCriteria.RequestorTypeId;
                
                if (requestorCriteria.PatientDob.HasValue)
                {
                    searchCriteria.PatientDob = requestorCriteria.PatientDob.Value;
                }

                FindPatientResult searchPatientResult = RequestorController.Instance.SearchMatchingPatients(searchCriteria);

                return searchPatientResult.PatientSearchResult;
            }
            else
            {
                ShowNoPatientMatchingDialog();
                return null;
            }
        }

        public void ShowNoPatientMatchingDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(NoPatientMatchingDialogMessage);
            string titleText = rm.GetString(NoPatientMatchingDialogTitle);
            string okButtonText = rm.GetString(NoPatientMatchingDialogOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(NoPatientMatchingDialogOkButtonToolTip);

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Show Create New Patient Requestor Dialog
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        private RequestorDetails ShowCreateNewPatientRequestorDialog(Collection<PatientDetails> patients)
        {
            CreatePatientRequestorUI createPatientRequestorUI = new CreatePatientRequestorUI(Pane);
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            createPatientRequestorUI.Header = new HeaderUI();
            createPatientRequestorUI.Header.Title = rm.GetString("createPatientRequestor.header.title");
            createPatientRequestorUI.Header.Information = rm.GetString("createPatientRequestor.header.info");

            createPatientRequestorUI.InfoLabel.Text = rm.GetString("createPatientRequestor.info");



            createPatientRequestorUI.SetData(patients);

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("createPatientRequestor.titlebar.title"), createPatientRequestorUI);
            DialogResult result = form.ShowDialog(this);

            RequestorDetails requestor = null;
            if (result == DialogResult.OK)
            {
                PatientDetails patient = createPatientRequestorUI.SelectedPatient;
                requestor = CreatePatientRequestorUI.ConvertToRequestor(patient);
            }

            form.Close();
            form.Dispose();

            return requestor;
        }

        /// <summary>
        /// Sets the value and null value for DOB.
        /// </summary>
        private void SetDOBDateNullValue()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            dobTextBox.Text = rm.GetString(ROIConstants.DefaultDate);
        }


        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.InvalidName                    : return requestorNameTextBox;
                case ROIErrorCodes.InvalidLastName                : return requestorLastNameTextBox;
                case ROIErrorCodes.InvalidFirstName               : return requestorFirstNameTextBox;
                case ROIErrorCodes.InvalidEpn                     : return patientEpnTextBox;
                case ROIErrorCodes.InvalidSsnSearch               : return patientSsnTextBox;
                case ROIErrorCodes.RequestorSsnLengthLimit        : return patientSsnTextBox;
                case ROIErrorCodes.InvalidMrn                     : return patientMrnTextBox;
                case ROIErrorCodes.RequestorMrnLengthLimit        : return patientMrnTextBox;
                case ROIErrorCodes.InvalidFacility                : return patientFacilityComboBox;
                case ROIErrorCodes.IncorrectDate                  : return dobTextBox;
                case ROIErrorCodes.RequestorFirstNameLength       : return requestorFirstNameTextBox;
                case ROIErrorCodes.RequestorLastNameLength        : return requestorLastNameTextBox;
                case ROIErrorCodes.RequestorFirstLastNameLength   : return requestorFirstNameTextBox;
                case ROIErrorCodes.RequestorLastFirstNameLength   : return requestorLastNameTextBox;                
                case ROIErrorCodes.RequestorPatientEpnLenghtLimit : return patientEpnTextBox;
            }
            return null;
        }

        /// <summary>
        /// Occurs when user clicked on facility combobox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void patientFacilityComboBox_Click(object sender, EventArgs e)
        {
            if (patientFacilityComboBox.Items.Count - 1 != UserData.Instance.Facilities.Count)// + UserData.Instance.FreeformFacilities.Count)
            {   
                patientFacilityComboBox.Items.Clear();
                PopulateFacilities();   
            }
        }

        private void SetSsnFormat()
        {
            patientSsnTextBox.Mask = "999-99-9999";
            patientSsnTextBox.PromptChar = 'x';
            patientSsnTextBox.TextMaskFormat = MaskFormat.IncludeLiterals;
            patientSsnTextBox.AllowPromptAsInput = false;
        }
        
        #endregion

        #region Security Rights

        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, false))
            {
                newRequestorButton.Enabled = false;
            }
        }

        #endregion

        #region Event wiring properties

        public EventHandler NewRequestorHandler
        {
            get { return newRequestorHandler; }
            set { newRequestorHandler = value; }
        }

        #endregion

        #region Properties

        public static bool IsError
        {
            get { return isError; }
            set { isError = value; }
        }

        public static bool IsFindRequestor
        {
            get { return isFindRequestor; }
            set { isFindRequestor = value; }
        }

        #endregion
    }
}
