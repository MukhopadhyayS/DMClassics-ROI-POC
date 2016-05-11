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
using System.Configuration;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Patient.View.FindPatient
{
    /// <summary>
    /// Display the PatientSearchUI.
    /// </summary>
    public partial class PatientSearchUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(PatientSearchUI));

        #region Fields

       // private const string CustomDateFormat = "MMMM dd, yyyy";
        private const string SelectOption = "  /  /    ";

        private EventHandler keyPressHandler;
        private EventHandler newPatientHandler;
        private EventHandler activeHandler;
        private EventHandler inActiveHandler;       

        private FindPatientCriteria searchCriteria;
        private FindPatientResult searchResult;

        private static bool isError;

        #endregion

        #region Constructor

        /// <summary>
        /// Initializes the Patient Search UI.
        /// </summary>
        public PatientSearchUI()
        {
            InitializeComponent();            
            SetSsnFormat();
            Init();
        }

        #endregion

        #region Methods

        private void Init()
        {
            moreInfoImage1.Image = ROIImages.MoreInfoIcon;
            moreInfoImage2.Image = ROIImages.MoreInfoIcon;            
            epnPanel.Visible     = UserData.Instance.EpnEnabled;
            prefixLabel.Text     = (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() : string.Empty;
            toolTip.SetToolTip(prefixLabel, (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() : string.Empty);

            activeHandler        = new EventHandler(OnActive);
            inActiveHandler      = new EventHandler(OnInActive);
            keyPressHandler      = new EventHandler(Process_KeyPressedHandler);
            newPatientHandler    = new EventHandler(DefaultNewPatientHandler);
            EnableEvents();
        }

        /// <summary>
        /// Populate the Facility
        /// </summary>
        public void PrePopulate()
        {
            SetDOBDateNullValue();
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                facilityComboBox.Items.Add(fac);
            }

            facilityComboBox.Items.Insert(0, new FacilityDetails(ROIConstants.AllFacilities, ROIConstants.AllFacilities, FacilityType.Hpf));
            facilityComboBox.DisplayMember = "Name";
            facilityComboBox.ValueMember = "Code";
            facilityComboBox.SelectedIndex = 0;
            lastNameTextBox.Focus();
            errorTextLabel.Text = string.Empty;
            
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the presses the Enter key
        /// </summary>
        public override void SetAcceptButton()
        {
            if (this.ParentForm != null && this.ParentForm.AcceptButton != findPatientButton)
            {
               this.ParentForm.AcceptButton = findPatientButton;
               ClearSearchCriteria();
            }
        }

        public void AddFacility(string newFacility)
        {
            if (newFacility == null) return;

            if (facilityComboBox.FindStringExact(newFacility) == -1)
            {
                facilityComboBox.Items.Add(newFacility);
            }
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control == moreInfoImage1 || control == moreInfoImage2)
            {
                return "moreInfoImage";
            }
            return base.GetLocalizeKey(control, toolTip);
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            //UI-Text
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, lastNameLabel);
            SetLabel(rm, firstNameLabel);
            SetLabel(rm, dobLabel);
            SetLabel(rm, ssnLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, encounterLabel);
            SetLabel(rm, mrnLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, findPatientButton);
            SetLabel(rm, newPatientButton);
            SetLabel(rm, resetButton);

            //ToolTip
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, lastNameTextBox);
            SetTooltip(rm, toolTip, firstNameTextBox);
            SetTooltip(rm, toolTip, dobTextBox);
            SetTooltip(rm, toolTip, moreInfoImage1);
            SetTooltip(rm, toolTip, ssnTextBox);
            SetTooltip(rm, toolTip, facilityComboBox);
            SetTooltip(rm, toolTip, moreInfoImage2);
            SetTooltip(rm, toolTip, encounterTextBox);
            SetTooltip(rm, toolTip, mrnTextBox);
            SetTooltip(rm, toolTip, epnTextBox);
            SetTooltip(rm, toolTip, findPatientButton);
            SetTooltip(rm, toolTip, newPatientButton);
            SetTooltip(rm, toolTip, resetButton);
        }     

        /// <summary>
        /// Enable the Events
        /// </summary>
        private void EnableEvents()
        {
            lastNameTextBox.TextChanged  += keyPressHandler;
            firstNameTextBox.TextChanged += keyPressHandler;
            ssnTextBox.TextChanged       += keyPressHandler;
            encounterTextBox.TextChanged += keyPressHandler;
            mrnTextBox.TextChanged       += keyPressHandler;
            epnTextBox.TextChanged       += keyPressHandler;
            dobTextBox.TextChanged       += keyPressHandler;
            facilityComboBox.SelectedIndexChanged += keyPressHandler;
            
            ssnTextBox.Enter += activeHandler;
            ssnTextBox.Leave += inActiveHandler;
        }     

        /// <summary>
        /// Disable the Events
        /// </summary>
        private void DisableEvents()
        {
            lastNameTextBox.TextChanged  -= keyPressHandler;
            firstNameTextBox.TextChanged -= keyPressHandler;
            ssnTextBox.TextChanged       -= keyPressHandler;
            encounterTextBox.TextChanged -= keyPressHandler;
            mrnTextBox.TextChanged       -= keyPressHandler;
            epnTextBox.TextChanged       -= keyPressHandler;
            dobTextBox.TextChanged       -= keyPressHandler;
           
            facilityComboBox.SelectedIndexChanged -= keyPressHandler;

            ssnTextBox.Enter    -= activeHandler;
            ssnTextBox.Leave    -= inActiveHandler;
        }
        
        /// <summary>
        /// Event invoked when ever there is any change in the value 
        /// of Search criteria. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void Process_KeyPressedHandler(object sender, EventArgs e)
        {
            findPatientButton.Enabled = PatientValidator.ValidatePrimarySearchFields(GetData()) &&
                                        (ssnTextBox.MaskedTextProvider.AssignedEditPositionCount == 0 ||
                                        ssnTextBox.MaskedTextProvider.AssignedEditPositionCount > 2);            
            //errorTextLabel.Text       = string.Empty;
            //errorTextLabel.Visible = !findPatientButton.Enabled;
            //if (Context != null)
            //{
            //    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            //    if (!findPatientButton.Enabled)
            //    {
            //        if (facilityComboBox.SelectedIndex == 0)
            //        {
            //            //if (dobTextBox.IsValidDate)
            //            //{
            //            //    errorTextLabel.Text = rm.GetString(errorTextLabel.Name + "." + dobTextBox.Name);
            //            //}
            //        }
            //        else
            //        {
            //            //if (dobTextBox.IsValidDate)
            //            //{
            //            //    errorTextLabel.Text = rm.GetString(errorTextLabel.Name + "." + dobTextBox.Name);                            
            //            //}
            //            else
            //            {
            //                if (facilityComboBox.SelectedIndex != 0)
            //                {
            //                    errorTextLabel.Text = rm.GetString(errorTextLabel.Name + "." + facilityComboBox.Name);
            //                }
            //            }
            //        }
            //    }             
            //}            
        }

        /// <summary>
        /// Occurs when the control becomes active control of the view
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnActive(object sender, EventArgs e)
        {
            ROIViewUtility.RemoveMask(ssnTextBox);
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
                ROIViewUtility.ApplyMask(ssnTextBox);
            }
        }

        /// <summary>
        /// Clear control values.
        /// </summary>
        private void ClearControls()
        {
            lastNameTextBox.Text           = string.Empty;
            firstNameTextBox.Text          = string.Empty;
            ssnTextBox.Clear();
            encounterTextBox.Text          = string.Empty;
            mrnTextBox.Text                = string.Empty;
            epnTextBox.Text                = string.Empty;
            facilityComboBox.SelectedIndex = 0;
            SetDOBDateNullValue();
            lastNameTextBox.Focus(); 
            errorTextLabel.Text = string.Empty;
            errorProvider.Clear();
        }

        /// <summary>
        /// Gets the FeeTypeDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        private FindPatientCriteria GetData()
        {
            searchCriteria = new FindPatientCriteria();
            searchCriteria.LastName  = lastNameTextBox.Text.Trim();
            searchCriteria.FirstName = firstNameTextBox.Text.Trim();
            if (dobTextBox.Text.Trim() != SelectOption.Trim() && dobTextBox.IsValidDate)
            {
                searchCriteria.Dob = Convert.ToDateTime(dobTextBox.FormattedDate, CultureInfo.InvariantCulture);
            }
            searchCriteria.SSN = (ssnTextBox.MaskedTextProvider.AssignedEditPositionCount > 0) ? ssnTextBox.Text.Trim() : string.Empty;
            if (facilityComboBox.Items.Count > 0)
            {
                if (facilityComboBox.SelectedIndex == 0)
                {
                    searchCriteria.FacilityCode = null;
                }
                else
                {
                    FacilityDetails facilityDetails = (FacilityDetails)facilityComboBox.SelectedItem;
                    searchCriteria.FacilityCode = facilityDetails.Code;
                    searchCriteria.FacilityType = facilityDetails.Type;                    
                }                
            }

            searchCriteria.Encounter = encounterTextBox.Text.Trim();
            searchCriteria.MRN       = mrnTextBox.Text.Trim();
            searchCriteria.EPN       = (epnTextBox.Text.Trim().Length > 0 && UserData.Instance.EpnPrefix != null)
                                       ? UserData.Instance.EpnPrefix.Trim() + epnTextBox.Text.Trim()
                                       : epnTextBox.Text.Trim();
            searchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            return searchCriteria;
        }
       
        /// <summary>
        /// Clears the searchcritia
        /// </summary>
        public void ClearSearchCriteria()
        {
            if (searchResult != null)
            {
                if (searchResult.PatientSearchResult.Count == 0)
                {
                    resetButton.PerformClick();
                }
            }
        }
        /// <summary>
        /// Search for Patient.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void findPatientButton_Click(object sender, EventArgs e)
        {
            IsError = false;
            findPatientButton.Focus();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            errorProvider.Clear();
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
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
                    else if(!ROIViewUtility.IsValidDate(dobTextBox.Text.Trim()))
                    {
                        IsError = true;
                        string errorMessage = rm.GetString(ROIErrorCodes.InvalidSqlDate);
                        errorProvider.SetError(dobTextBox, errorMessage);                        
                    }
                }

                searchResult = PatientController.Instance.FindPatient(GetData());
                ApplicationEventArgs ae = new ApplicationEventArgs(searchResult, this);
                PatientEvents.OnPatientSearched(Pane, ae);
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
        /// Occurs when user clicked on facility combobox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void facilityComboBox_Click(object sender, EventArgs e)
        {
            if (facilityComboBox.Items.Count - 1 != UserData.Instance.Facilities.Count)// + UserData.Instance.FreeformFacilities.Count)
            {
                facilityComboBox.Items.Clear();
                PrePopulate();
            }
        }

        /// <summary>
        /// Occurs When New Patient Button Invoked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void newPatientButton_Click(object sender, EventArgs e)
        {
            ClearControls();
            NewPatientHandler(sender, e);
        }

        private void DefaultNewPatientHandler(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = new ApplicationEventArgs(null, this);
            PatientEvents.OnCreatePatient(Pane, ae);
        }

        /// <summary>
        /// Occurs When Reset Button Invoked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void resetButton_Click(object sender, EventArgs e)
        {
            ClearControls();
            findPatientButton.Enabled = false;
            ApplicationEventArgs ae = new ApplicationEventArgs(null, this);
            PatientEvents.OnSearchReset(Pane, ae);
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
                case ROIErrorCodes.InvalidLastName  : return lastNameTextBox;
                case ROIErrorCodes.InvalidFirstName : return firstNameTextBox;
                case ROIErrorCodes.InvalidSsnSearch : return ssnTextBox;
                case ROIErrorCodes.InvalidMrn       : return mrnTextBox;
                case ROIErrorCodes.InvalidEpn       : return epnTextBox;
                case ROIErrorCodes.InvalidEncounter : return encounterTextBox;                
                case ROIErrorCodes.IncorrectDate    : return dobTextBox;                
            }
            return null;
        }

        private void SetSsnFormat()
        {
            ssnTextBox.Mask = "999-99-9999";
            ssnTextBox.PromptChar = 'x';
            ssnTextBox.TextMaskFormat = MaskFormat.IncludeLiterals;
            ssnTextBox.AllowPromptAsInput = false;            
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
                newPatientButton.Enabled = false;
            }
        }

        #endregion

        #region Properties

        public FindPatientCriteria SearchCriteria
        {
            get { return searchCriteria; }
        }

        public Button NewPatientButton
        {
            get { return newPatientButton; }
        }

        public static bool IsError
        {
            get { return isError; }
            set { isError = value; }
        }

        #endregion

        #region Event Wiring Properties

        public EventHandler NewPatientHandler
        {
            get { return newPatientHandler; }
            set { newPatientHandler = value; }
        }

        #endregion                
    }
}
