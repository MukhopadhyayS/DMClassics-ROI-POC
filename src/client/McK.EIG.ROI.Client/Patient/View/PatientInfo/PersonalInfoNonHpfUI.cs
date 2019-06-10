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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Controller;
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    public partial class PersonalInfoNonHpfUI : ROIBaseUI, IPersonalInfoUI 
    {
        #region Fields

      //  private const string CustomDateFormat = "MMMM dd, yyyy";
        private const string SelectOption = "  /  /    ";

        private EventHandler dirtyDataHandler;
        private EventHandler activeHandler;
        private EventHandler inActiveHandler;        

        private bool isDirty;
        private System.Collections.Generic.List<GenderDetails> GenderDetails;
        #endregion

        #region Constructor

        public PersonalInfoNonHpfUI()
        {
            InitializeComponent();
            SetSsnValue();
            
            dirtyDataHandler     = new EventHandler(MarkDirty);
            activeHandler        = new EventHandler(OnActive);
            inActiveHandler      = new EventHandler(OnInActive);
            
            epnPanel.Visible = UserData.Instance.EpnEnabled;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Gets the PatientDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            PatientDetails patientInfo = (appendTo == null) ? new PatientDetails()
                                           : appendTo as PatientDetails;

            patientInfo.IsVip = vipCheckBox.Checked;            
            patientInfo.FirstName = firstPatientNameTextBox.Text;
            patientInfo.LastName = lastPatientNameTextBox.Text;
            patientInfo.FullName = patientInfo.LastName + ", " + patientInfo.FirstName;

            //patientInfo.Gender = (Gender)genderComboBox.SelectedValue;
            //SOGI OC-111171
            patientInfo.GenderDesc = genderComboBox.Text;
            int i = 0;
            foreach (GenderDetails genderList in GenderDetails)
            {
                if (genderComboBox.Text == GenderDetails[i].GenderDesc)
                {
                    patientInfo.GenderCode = GenderDetails[i].GenderCode;
                   
                    break;
                }
                i++;
            }

            if (dobTextBox.Text.Trim() != SelectOption.Trim())
            {
                patientInfo.PatientDob = dobTextBox.Text;
            }
            else if(patientInfo.DOB !=  null && dobTextBox.Text.Trim() == SelectOption.Trim())
            {
                patientInfo.DOB = null;
            }
            patientInfo.SSN = (ssnTextBox.MaskedTextProvider.AssignedEditPositionCount > 0) ? ssnTextBox.Text : string.Empty;
            patientInfo.EPN = (epnTextBox.Text.Trim().Length > 0 && UserData.Instance.EpnPrefix != null)
                              ? UserData.Instance.EpnPrefix.Trim() + epnTextBox.Text.Trim()
                              : epnTextBox.Text.Trim();

            AddFacility(facilityComboBox.Text.Trim());

            if (String.IsNullOrEmpty(facilityComboBox.Text.Trim()) || facilityComboBox.Text.Trim().Equals("Select Facility"))
            {
                facilityComboBox.SelectedIndex = 0;
            }
            patientInfo.FacilityCode = (facilityComboBox.SelectedIndex == 0) ? null : ((FacilityDetails)(facilityComboBox.SelectedItem)).Code;
            patientInfo.FacilityType = (facilityComboBox.SelectedIndex == 0) ? FacilityType.NonHpf : ((FacilityDetails)(facilityComboBox.SelectedItem)).Type;
            patientInfo.MRN = mrnTextBox.Text;
            //patientInfo.GenderValue = genderComboBox.Text;

            return patientInfo;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            
            firstPatientNameTextBox.TextChanged += dirtyDataHandler;
            lastPatientNameTextBox.TextChanged  += dirtyDataHandler;
            mrnTextBox.TextChanged  += dirtyDataHandler;
            ssnTextBox.TextChanged  += dirtyDataHandler;
            epnTextBox.TextChanged  += dirtyDataHandler;
            dobTextBox.TextChanged  += dirtyDataHandler;

            vipCheckBox.CheckStateChanged += dirtyDataHandler;

            facilityComboBox.SelectedIndexChanged += dirtyDataHandler;
            facilityComboBox.TextChanged          += dirtyDataHandler;
            genderComboBox.SelectedValueChanged   += dirtyDataHandler;

            ssnTextBox.Enter          += activeHandler;
            ssnTextBox.Leave          += inActiveHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            firstPatientNameTextBox.TextChanged -= dirtyDataHandler;
            lastPatientNameTextBox.TextChanged  -= dirtyDataHandler;
            mrnTextBox.TextChanged  -= dirtyDataHandler;
            ssnTextBox.TextChanged  -= dirtyDataHandler;
            epnTextBox.TextChanged  -= dirtyDataHandler;
            dobTextBox.TextChanged  -= dirtyDataHandler;

            vipCheckBox.CheckStateChanged -= dirtyDataHandler;

            facilityComboBox.SelectedIndexChanged -= dirtyDataHandler;
            facilityComboBox.TextChanged          -= dirtyDataHandler;
            genderComboBox.SelectedValueChanged   -= dirtyDataHandler;

            ssnTextBox.Enter          -= activeHandler;
            ssnTextBox.Leave          -= inActiveHandler;
        }

        /// <summary>
        /// Occurs when user changes Payment Name or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            IsDirty = true;
            ((PatientInfoUI)Pane.View).DirtyDataHandler(sender, e);
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
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return base.GetLocalizeKey(control);
        }
        
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.PatientFirstNameEmpty     : return firstPatientNameTextBox;
                case ROIErrorCodes.PatientLastNameEmpty      : return lastPatientNameTextBox;
                case ROIErrorCodes.PatientFirstNameMaxLength : return firstPatientNameTextBox;
                case ROIErrorCodes.PatientLastNameMaxLength  : return lastPatientNameTextBox;
                case ROIErrorCodes.InvalidFirstName : return firstPatientNameTextBox;
                case ROIErrorCodes.InvalidLastName  : return lastPatientNameTextBox;
                case ROIErrorCodes.InvalidSsn       : return ssnTextBox;
                case ROIErrorCodes.InvalidEpn       : return epnTextBox;
                case ROIErrorCodes.InvalidMrn       : return mrnTextBox;
                case ROIErrorCodes.InvalidFacility  : return facilityComboBox;
                case ROIErrorCodes.IncorrectDate    : return dobTextBox;
                case ROIErrorCodes.InvalidDate      : return dobTextBox;
                case ROIErrorCodes.InvalidSqlDate   : return dobTextBox;
                case ROIErrorCodes.InvalidDateValue : return dobTextBox;
            }
            return null;
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + Pane.GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, firstNameLabel);
            SetLabel(rm, lastNameLabel);
            SetLabel(rm, genderLabel);
            SetLabel(rm, dobLabel);
            SetLabel(rm, ssnLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, mrnLabel);
            SetLabel(rm, vipCheckBox);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, vipCheckBox);
            SetTooltip(rm, toolTip, firstPatientNameTextBox);
            SetTooltip(rm, toolTip, lastPatientNameTextBox);
            SetTooltip(rm, toolTip, genderComboBox);
            SetTooltip(rm, toolTip, ssnTextBox);
            SetTooltip(rm, toolTip, epnTextBox);
            SetTooltip(rm, toolTip, facilityComboBox);
            SetTooltip(rm, toolTip, mrnTextBox);
            SetTooltip(rm, toolTip, dobTextBox);

            SetDOBNullValue();
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearData()
        {            
            vipCheckBox.Checked = false;
            firstPatientNameTextBox.Text = string.Empty;
            lastPatientNameTextBox.Text = string.Empty;
            genderComboBox.SelectedIndex = 0;
            SetDOBNullValue();
            ssnTextBox.Clear();
            epnTextBox.Text = string.Empty;
            facilityComboBox.SelectedIndex = (facilityComboBox.Items.Count > 0) ? 0 : -1;
            mrnTextBox.Text = string.Empty;
            if (UserData.Instance.EpnEnabled && UserData.Instance.EpnPrefix.Length > 0)
            {
                epnTextBox.MaxLength = Convert.ToInt32(ROIConstants.EpnLengthWhenEpnConfigure, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            lastPatientNameTextBox.Focus();
            errorProvider.Clear();
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></p
        /// aram>
        public void SetData(object data)
        {
            DisableEvents();
            ClearData();
           
            PatientDetails patientDetail = data as PatientDetails;

            if (patientDetail != null)
            {
                vipCheckBox.Checked = patientDetail.IsVip;
                firstPatientNameTextBox.Text = patientDetail.FirstName;
                lastPatientNameTextBox.Text = patientDetail.LastName;
                //genderComboBox.SelectedValue = patientDetail.Gender;
                //SOGI OC-111171
                //genderComboBox.SelectedValue = patientDetail.GenderDesc;
                genderComboBox.SelectedItem = patientDetail.GenderDesc;
                if (patientDetail.DOB.HasValue)
                {
                    dobTextBox.Text = patientDetail.DOB.Value.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                }
                ssnTextBox.Text = patientDetail.SSN;
                if (UserData.Instance.IsSSNMasked)
                {
                    ROIViewUtility.ApplyMask(ssnTextBox);
                }

                if (patientDetail.EPN != null)
                {
                    if (!string.IsNullOrEmpty(UserData.Instance.EpnPrefix) &&
                        UserData.Instance.EpnPrefix.Trim().Length > 0 &&
                        patientDetail.EPN.StartsWith(UserData.Instance.EpnPrefix.Trim(),StringComparison.CurrentCultureIgnoreCase))
                    {
                        epnTextBox.Text = patientDetail.EPN.Substring(UserData.Instance.EpnPrefix.Length);
                    }
                    else
                    {
                        epnTextBox.Text = patientDetail.EPN;
                    }
                }

                if (String.IsNullOrEmpty(patientDetail.FacilityCode))
                {
                    facilityComboBox.SelectedIndex = 0;
                }
                else
                {
                   
                    FacilityDetails facility = FacilityDetails.GetFacility(patientDetail.FacilityCode, patientDetail.FacilityType);
                    if (facility != null)
                    {
                        if (facilityComboBox.Items.Contains(facility))
                        {
                            facilityComboBox.SelectedItem = facility;
                        }
                    }
               
                }
                mrnTextBox.Text = patientDetail.MRN;
            }            
            EnableEvents();
        }

        /// <summary>
        /// Set From Date value
        /// </summary>
        private void SetDOBNullValue()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());            
            dobTextBox.Text = rm.GetString(ROIConstants.DefaultDate);
        }

        public void AddFacility(string newFacility)
        {
            if (string.IsNullOrEmpty(newFacility)) return;

            if (facilityComboBox.SelectedItem == null)
            {
                int selectedIndex = facilityComboBox.FindStringExact(newFacility);

                if (selectedIndex != -1)
                {
                    facilityComboBox.SelectedItem = facilityComboBox.Items[selectedIndex];
                    return;
                }

                FacilityDetails facility = new FacilityDetails(newFacility, newFacility, FacilityType.NonHpf);
                facilityComboBox.Items.Add(facility);
                facilityComboBox.SelectedItem = facility;
            }
        }

        /// <summary>
        /// Populate Gender Value
        /// </summary>
        /// <param name="gender"></param>
        public void PrePopulate()
        {
            DisableEvents();
            //SOGI OC-111171
            //IList gender = EnumUtilities.ToList(typeof(Gender));
            //PopulateGender(gender);
            PopulateGender();
            PopulateFacilities();
            SetDOBNullValue();
            EnableEvents();
            vipCheckBox.Visible = UserData.Instance.HasAccess(ROISecurityRights.ROIVipStatus);
        }

        private void PopulateFacilities()
        {
            facilityComboBox.Items.Clear();
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                facilityComboBox.Items.Add(fac);
            }

            facilityComboBox.Items.Insert(0, new FacilityDetails("Select Facility", "Select Facility", FacilityType.NonHpf));
            facilityComboBox.DisplayMember = "name";
            facilityComboBox.ValueMember = "code";

            facilityComboBox.SelectedIndex = 0;
        }
        //SOGI OC-111171
        private void PopulateGender()
        {
            GenderDetails = new System.Collections.Generic.List<GenderDetails>();
            GenderDetails = ROIController.Instance.RetrieveGenderList();
            int i = 0;

            foreach (GenderDetails genderList in GenderDetails)
            {
                genderComboBox.Items.Add(GenderDetails[i].GenderDesc);
                ++i;
            }
        }
        //private void PopulateGender(IList gender)
        //{
        //    genderComboBox.DataSource = gender;
        //    genderComboBox.DisplayMember = "value";
        //    genderComboBox.ValueMember = "key";
        //    genderComboBox.SelectedIndex = 0;
        //    genderComboBox.Refresh();
        //}

        public void ShowNonUniqueIcon(bool showIcon)
        {
            nameNonUniqueIcon.Visible = showIcon;
        }

        public void SetFocusOnName()
        {
            lastPatientNameTextBox.Focus();
        }

        private void SetSsnValue()
        {
            ssnTextBox.Mask = "999-99-9999";
            ssnTextBox.PromptChar = 'x';
            ssnTextBox.TextMaskFormat = MaskFormat.IncludePromptAndLiterals;
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

        /// <summary>
        /// Returns patient name entered in the textbox
        /// </summary>
        public string PatientFirstName
        {
            get { return firstPatientNameTextBox.Text.Trim(); }
        }

        public string PatientLastName
        {
            get { return lastPatientNameTextBox.Text.Trim(); }
        }

        #endregion       

    }
}
