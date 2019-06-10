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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;


namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    public partial class PersonalInfoHpfUI : ROIBaseUI, IPersonalInfoUI
    {
        #region Fields

        //private const string CustomDateFormat = "MMMM dd, yyyy";

        private EventHandler dirtyDataHandler;
        private EventHandler activeHandler;
        private EventHandler inActiveHandler;
        
        private bool isDirty;
        private System.Collections.Generic.List<GenderDetails> GenderDetails;
        private static List<GenderDetails> GenderListDetails;
        #endregion

        #region Constructor

        public PersonalInfoHpfUI()
        {
            InitializeComponent();

            dirtyDataHandler = new EventHandler(MarkDirty);
            activeHandler = new EventHandler(OnActive);
            inActiveHandler = new EventHandler(OnInActive);
            
            
           // dobDateTimePicker.Format = DateTimePickerFormat.Custom;
            //dobDateTimePicker.CustomFormat = CustomDateFormat;            
            if (UserData.Instance.EpnEnabled)
            {
                epnPanel.Visible = true;
            }
            else
            {
                epnPanel.Visible = false;
            }            
            dobDateTimePicker.MaxDate = System.DateTime.Now;
            dobDateTimePicker.Value = DateTime.Today.Date;
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
            PatientDetails patientMethod;
            if (appendTo == null)
            {
                patientMethod = new PatientDetails();
            }
            else
            {
                patientMethod = appendTo as PatientDetails;
            }

            patientMethod.LastName = lastPatientNameTextBox.Text.Trim();
            patientMethod.FirstName = firstPatientNameTextBox.Text.Trim();
            //patientMethod.Gender = (Gender)genderComboBox.SelectedValue;
            //SOGI OC-111171
            patientMethod.GenderDesc = genderComboBox.Text;

            patientMethod.DOB = dobDateTimePicker.Value;
            return patientMethod;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            lastPatientNameTextBox.TextChanged  += dirtyDataHandler;
            firstPatientNameTextBox.TextChanged += dirtyDataHandler;
            genderComboBox.SelectedValueChanged += dirtyDataHandler;
            dobDateTimePicker.TextChanged       += dirtyDataHandler;

            ssnTextBox.Enter += activeHandler;
            ssnTextBox.Leave += inActiveHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            lastPatientNameTextBox.TextChanged  -= dirtyDataHandler;
            firstPatientNameTextBox.TextChanged -= dirtyDataHandler;
            genderComboBox.SelectedValueChanged -= dirtyDataHandler;
            dobDateTimePicker.TextChanged       -= dirtyDataHandler;

            ssnTextBox.Enter -= activeHandler;
            ssnTextBox.Leave -= inActiveHandler;
        }

        /// <summary>
        /// Occurs when user changes Payment Name or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            IsDirty = true;
            ((PatientInfoUI)this.Pane.View).DirtyDataHandler(sender, e);
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

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, lastNameLabel);
            SetLabel(rm, firstNameLabel);
            SetLabel(rm, genderLabel);
            SetLabel(rm, dobLabel);
            SetLabel(rm, ssnLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, mrnLabel);
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearData()
        {
           
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
                lastPatientNameTextBox.Text = patientDetail.LastName;
                firstPatientNameTextBox.Text = patientDetail.FirstName;
                GenderListDetails = new System.Collections.Generic.List<GenderDetails>();
                GenderListDetails = ROIController.Instance.RetrieveGenderList();
                // genderComboBox.SelectedValue = patientDetail.Gender;
                //SOGI OC-111171
                genderComboBox.Text = patientDetail.GenderDesc;
                if (!String.IsNullOrEmpty(patientDetail.GenderDesc))
                {
                    patientDetail.GenderCode = ROIController.FetchGenderCodeOrDesc(patientDetail.GenderDesc, GenderListDetails);
                }
                //GenderDetails = new System.Collections.Generic.List<GenderDetails>();
                //GenderDetails = ROIController.Instance.RetrieveGenderList();
                //int i = 0;
                //foreach (GenderDetails genderList in GenderDetails)
                //{
                //    if (patientDetail.GenderDesc == GenderDetails[i].GenderDesc)
                //    {
                //        patientDetail.GenderCode = GenderDetails[i].GenderCode;

                //        break;
                //    }
                //    i++;
                //}

                if (patientDetail.DOB.HasValue)
                {
                    dobDateTimePicker.CustomFormat = "MM/dd/yyyy";
                    dobDateTimePicker.Format = DateTimePickerFormat.Custom;
                    dobDateTimePicker.Value = patientDetail.DOB.Value;
                }
                else
                {
                    //dobDateTimePicker.Format = DateTimePickerFormat.Custom;
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
					dobDateTimePicker.CustomFormat = rm.GetString(ROIConstants.SelectDate);
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
                FacilityDetails facility = FacilityDetails.GetFacility(patientDetail.FacilityCode, patientDetail.FacilityType);
                if (facility != null)
                {
                    facilityComboBox.SelectedItem = facility;
                }
                mrnTextBox.Text = patientDetail.MRN;

                lockedPatientIcon.Visible = patientDetail.PatientLocked || patientDetail.EncounterLocked;
                vipPatientIcon.Visible = patientDetail.IsVip;
            }
            EnableEvents();
        }

        /// <summary>
        /// Populate Gender Value
        /// </summary>
        /// <param name="gender"></param>
        public void PrePopulate()
        {
            DisableEvents();
            //SOGI
            // IList gender = EnumUtilities.ToList(typeof(Gender));
            //PopulateGender(gender);
            PopulateGender();
            PopulateFacilities();
            EnableEvents();
        }

        private void PopulateFacilities()
        {
            facilityComboBox.Items.Clear();
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                facilityComboBox.Items.Add(fac);
            }

            facilityComboBox.Items.Insert(0, new FacilityDetails("Select Facility", "Select Facility", FacilityType.NonHpf));
            facilityComboBox.DisplayMember = "Name";
            facilityComboBox.ValueMember = "Code";

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
        public string PatientLastName
        {
            get { return lastPatientNameTextBox.Text.Trim(); }
        }

        public string PatientFirstName
        {
            get { return firstPatientNameTextBox.Text.Trim(); }
        }

        #endregion

    }
}
