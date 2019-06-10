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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Reports.Model;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// AccountingofDisclosureUI
    /// </summary>
    public partial class AccountingOfDisclosureUI : ROIBaseUI, IReportCriteria
    {
        #region Fields

        private PatientDetails patientDetails;
        private FindPatientDialogUI findPatientDialogUI;
        private bool bRunEnable;

        #endregion

        #region Construcor

        public AccountingOfDisclosureUI()
        {
            InitializeComponent();            
            //CR337443
            epnLabel.Visible = UserData.Instance.EpnEnabled;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, reportCriteriaLabel);
            SetLabel(rm, nameLabel);
            SetLabel(rm, dobLabel);
            SetLabel(rm, genderLabel);
            SetLabel(rm, ssnLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, mrnLabel);
            SetLabel(rm, findPatientLabel);
            SetLabel(rm, patientInfoLabel);
            SetLabel(rm, requestTypeLabel);
            SetLabel(rm, selectPatientButton);
            reportDateRangeUI.SetExecutionContext(Context);
            reportDateRangeUI.SetPane(Pane);
            reportDateRangeUI.Localize();
        }

        private void EnableEvents()
        {
            requestTypeCombo.SelectedIndexChanged += new EventHandler(requestTypeCombo_SelectedIndexChanged);
        }

        private void DisableEvents()
        {
            requestTypeCombo.SelectedIndexChanged -= new EventHandler(requestTypeCombo_SelectedIndexChanged);
        }
        /// <summary>
        /// Gets the control name.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control != reportCriteriaLabel)
            {
                return control.Name + "." + this.GetType().Name;
            }

            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Gets the control for showing error info.
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            return reportDateRangeUI.EndDateTimePicker;
        }

        /// <summary>
        /// Clears the controls
        /// </summary>
        public void ClearControls()
        {
            patientNameLabel.Text    = string.Empty;
            patientDOBLabel.Text     = string.Empty;
            patientGenderLabel.Text  = string.Empty;
            patientSSNLabel.Text     = string.Empty;
            patientEPNLabel.Text     = string.Empty;
            facilityLabel.Text       = string.Empty;
            mrnLabel.Text            = string.Empty;
            requestTypeCombo.SelectedIndex = 1;
            reportDateRangeUI.ClearControls();
            EnableControls(false);
        }

        

        /// <summary>
        /// Validates primary search criteria.
        /// </summary>
        /// <returns></returns>
        public bool ValidatePrimaryFields()
        {
            return bRunEnable;
        }

        #region IReportCriteria Members

        /// <summary>
        /// Prepopulates the Request Types and data ranges.
        /// </summary>
        public void PrePopulate()
        {
            DisableEvents();
            IList requestAtrr = EnumUtilities.ToList(typeof(RequestReportAttr));            
            requestTypeCombo.DataSource = requestAtrr;
            requestTypeCombo.DisplayMember  = "value";
            requestTypeCombo.ValueMember    = "key";
            requestTypeCombo.SelectedIndex = 1;
            reportDateRangeUI.Populate();
            EnableEvents();
        }

        public object GetData(object parameters)
        {
            Hashtable ht = (Hashtable)parameters;
            ht.Add(ROIConstants.ReportFacilities, patientDetails.FacilityCode);
            ht.Add(ROIConstants.ReportMRN,  patientDetails.MRN);
            ht.Add(ROIConstants.ReportPatientId,  patientDetails.Id);
            ht.Add(ROIConstants.ReportRequestType,  requestTypeCombo.SelectedValue);
            ht.Add(ROIConstants.ReportPatientName, patientDetails.Name);
            reportDateRangeUI.GetData(ht);
            return parameters;
        }

        #endregion

        /// <summary>
        /// Select a patient button click
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void selectPatientButton_Click(object sender, EventArgs e)
        {
            ReportEvents.OnLSPParamsChanged(Pane, e);
            findPatientDialogUI = new FindPatientDialogUI(Pane);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("findPatient.header.title"), findPatientDialogUI);
            dialog.FormClosing += delegate { findPatientDialogUI.CleanUp(); };
            dialog.DialogResult = DialogResult.Cancel;
            ROIViewUtility.MarkBusy(true);
            DialogResult result = dialog.ShowDialog(this);

            if (result == DialogResult.OK)
            {
                patientDetails = findPatientDialogUI.SelectedPatient;
                patientNameLabel.Text = patientDetails.Name;

                bool isPatientVip = UserData.Instance.HasAccess(ROISecurityRights.ROIVipStatus);
                bool isPatientLocked = UserData.Instance.HasAccess(ROISecurityRights.AccessLockedRecords);

                if (patientDetails.DOB != null)
                {
                    string patientDateOfBirth = patientDetails.DOB.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                    patientDOBLabel.Text = ROIViewUtility.BuildAsterisk(patientDateOfBirth,
                                                                        patientDetails.IsVip, patientDetails.PatientLocked, isPatientVip, isPatientLocked);
                }
                else
                {
                    patientDOBLabel.Text = string.Empty;
                }
                //SOGI OC-111171
                //if (patientDetails.Gender != Gender.None)
                //{
                //    patientGenderLabel.Text = ROIViewUtility.BuildAsterisk(patientDetails.Gender.ToString(),
                //                                                        patientDetails.IsVip, patientDetails.PatientLocked, isPatientVip, isPatientLocked);
                //}
                //else
                //{
                //    patientGenderLabel.Text = string.Empty;
                //}
                patientGenderLabel.Text = ROIViewUtility.BuildAsterisk(patientDetails.GenderDesc,
                                                                       patientDetails.IsVip, patientDetails.PatientLocked, isPatientVip, isPatientLocked);
                                
                patientSSNLabel.Text      = ROIViewUtility.ApplyMask(patientDetails.SSN);
                //CR337443
                patientEPNLabel.Visible   = UserData.Instance.EpnEnabled;

                patientEPNLabel.Text      = ROIViewUtility.BuildAsterisk(patientDetails.EPN,
                                                                        patientDetails.IsVip, patientDetails.PatientLocked, isPatientVip, isPatientLocked);               
                patientFacilityLabel.Text = ROIViewUtility.BuildAsterisk(patientDetails.FacilityCode,
                                                                        patientDetails.IsVip, patientDetails.PatientLocked, isPatientVip, isPatientLocked);                                                                  
                patientMRNLabel.Text      = ROIViewUtility.BuildAsterisk(patientDetails.MRN,
                                                                        patientDetails.IsVip, patientDetails.PatientLocked, isPatientVip, isPatientLocked);
                
                ROIViewUtility.MarkBusy(false);
                EnableControls(true);
                ((ReportPane)this.Pane).ValidatePrimaryFields();
            }
        }

        /// <summary>
        /// Enable the controls.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableControls(bool enable)
        {
            requestTypeCombo.Enabled  = enable;
            reportDateRangeUI.Enabled = enable;
            bRunEnable = enable;
        }

        void requestTypeCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            ReportEvents.OnLSPParamsChanged(Pane, e);
        }

        #endregion
    }
}
