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

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View
{
    /// <summary>
    /// Shows the Patient's information
    /// </summary>
    public partial class RequestPatientListUI : ROIBaseUI
    {
        private const string NameColumn  = "name";
        private static string DefaultSsn = "xxx-xx-xxxx";

        private EventHandler patientSelectionHandler;
        private EIGDataGrid.RowSelectionHandler rowSelectionHandler;

        /// <summary>
        /// Initializes the UI controls
        /// </summary>
        public RequestPatientListUI()
        {
            InitializeComponent();
            InitGrid();
        }
        
        /// <summary>
        /// Initialize the grid
        /// </summary>
        private void InitGrid()
        {
            DataGridViewColumn nameColumn =  patientList.AddTextBoxColumn(NameColumn, "Name", "Name", 30);
            nameColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            patientList.SortEnabled = false;
            rowSelectionHandler = new EIGDataGrid.RowSelectionHandler(list_SelectionChanged);
        }

        /// <summary>
        /// Gets localized key of UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control is Label)
            {
                return base.GetLocalizeKey(control);
            }

            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets localized key for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, patientNameLabel);
            SetLabel(rm, genderLabel);
            SetLabel(rm, dobShortLabel);
            SetLabel(rm, ssnLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, mrnLabel);
            
            SetLabel(rm, addPatientButton);
            SetLabel(rm, viewEditPatientButton);
            SetLabel(rm, removePatientButton);
            SetLabel(rm, addAnotherPatientButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, addPatientButton);
            SetTooltip(rm, toolTip, viewEditPatientButton);
            SetTooltip(rm, toolTip, removePatientButton);
            SetTooltip(rm, toolTip, addAnotherPatientButton);
        }

        /// <summary>
        /// Sets the data
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            ClearData();

            epnLabel.Visible = epnValueLabel.Visible = UserData.Instance.EpnEnabled;
            SortedList<string, RequestPatientDetails> list = (SortedList<string, RequestPatientDetails>)data;
            List<RequestPatientDetails> patientsList = new List<RequestPatientDetails>(list.Values);
            patientsList.Sort(RequestPatientDetails.Sorter);
            
            DisableEvents();
            ComparableCollection<RequestPatientDetails> patients = new ComparableCollection<RequestPatientDetails>(patientsList);
            patients.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestPatientDetails))["Name"], ListSortDirection.Ascending);
            patientList.SetItems(patients);
            EnableEvents();
            bool hasPatients = patientList.Rows.Count > 0;
            if (hasPatients)
            {
                patientList.SelectRow(0);
            }
            else
            {
                SetNoneSelected();
            }
            RefreshHeaderText();
        }

        public void EnableEvents()
        {
            DisableEvents();
            patientList.SelectionHandler += rowSelectionHandler;
        }

        public void DisableEvents()
        {
            patientList.SelectionHandler -= rowSelectionHandler;
        }

        protected override void OnEnter(EventArgs e)
        {
            EnableEvents();
            base.OnEnter(e);
        }

        private void SetNoneSelected()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            nameValueLabel.Text = rm.GetString("noneSelected");
            ssnValueLabel.Text = DefaultSsn;
        }

        private void RefreshHeaderText()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            patientList.Columns[NameColumn].HeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                                       "{0} " + rm.GetString("patientCount." + GetType().Name), new object[] { patientList.RowCount });
        }

        /// <summary>
        /// Occurs when user selects the item in grid
        /// </summary>
        /// <param name="row"></param>
        private void list_SelectionChanged(DataGridViewRow row)
        {
            RequestPatientDetails patient = (RequestPatientDetails)row.DataBoundItem;
            PopulatePatientDetails(patient);
            patientSelectionHandler(this, new BaseEventArgs(patient));
        }

        //private void EnableButtons()
        //{
        //    removePatientButton.Enabled = (patientList.SelectedRows.Count == 1);
        //    viewEditPatientButton.Enabled = (patientList.SelectedRows.Count == 1);
        //    AddAnotherPatientButton.Enabled = (patientList.Rows.Count > 0);
        //    AddPatientButton.Visible = (patientList.Rows.Count == 0);
        //}

        /// <summary>
        /// Sets the personal information on selected patient
        /// </summary>
        /// <param name="patient"></param>
        private void PopulatePatientDetails(RequestPatientDetails patient)
        {
            ClearData();
            nameValueLabel.Text = patient.Name;
            toolTip.SetToolTip(nameValueLabel, nameValueLabel.Text);

            if (patient.DOB.HasValue)
            {
                dobValueLabel.Text = patient.FormattedDOB;
            }
            genderValueLabel.Text     = patient.Gender;
            ssnValueLabel.Text        = ROIViewUtility.ApplyMask(patient.SSN);
            mrnValueLabel.Text        = patient.MRN;
            facilityValueLabel.Text   = ROIViewUtility.ReplaceAmpersandForLabel(patient.FacilityCode);
            epnValueLabel.Text        = patient.EPN;
            vipPatientIcon.Visible    = patient.IsVip;
            lockedPatientIcon.Visible = patient.IsLockedPatient || patient.EncounterLocked;
        }

        /// <summary>
        /// Clear the data
        /// </summary>
        private void ClearData()
        {   
            patientList.Enabled = patientList.Rows.Count > 0;
            
            dobValueLabel.Text    = string.Empty;
            genderValueLabel.Text = string.Empty;
            ssnValueLabel.Text    = string.Empty;
            mrnValueLabel.Text    = string.Empty;
            epnValueLabel.Text    = string.Empty;
            facilityValueLabel.Text = string.Empty;

            vipPatientIcon.Visible = false;
            lockedPatientIcon.Visible = false;
        }

        public void AddPatient(RequestPatientDetails patient)
        {
            if (patientList.Items.Contains(patient)) return;

            patientList.AddItem(patient);
            RefreshHeaderText();
            EnableEvents();
        }

        public void UpdatePatient(RequestPatientDetails patient)
        {
            EnableEvents();
            patientList.UpdateItem(patient);
            PopulatePatientDetails(patient);
        }

        public RequestPatientDetails RemovePatient()
        {
            EnableEvents();
            RequestPatientDetails patient = (RequestPatientDetails)patientList.SelectedRows[0].DataBoundItem;
            patientList.DeleteItem(patient);
            RefreshHeaderText();

            if (patientList.Items.Count > 0)
            {
                patientList.ClearSelection();
                patientList.SelectItem(patientList.Items[0]);
            }
            else
            {
                ClearData();
                SetNoneSelected();
                removePatientButton.Visible = false;
                viewEditPatientButton.Visible = false;
                AddAnotherPatientButton.Visible = false;
                AddPatientButton.Visible = true;
            }

            return patient;
        }

        #region Properties 

        public Button ViewEditButton
        {
            get { return viewEditPatientButton; }
        }

        public Button AddPatientButton
        {
            get { return addPatientButton; }
        }

        public Button AddAnotherPatientButton
        {
            get { return addAnotherPatientButton; }
        }

        public Button RemovePatientButton
        {
            get { return removePatientButton; }
        }

        public EIGDataGrid Grid
        {
            get { return patientList; }
        }

        public RequestPatientDetails SelectedPatient
        {
            get
            {
                return (patientList.SelectedRows.Count == 0)
                        ? null
                        : (RequestPatientDetails)patientList.SelectedRows[0].DataBoundItem;
            }
        }

        public EventHandler PatientSelectionHandler
        {
            get { return patientSelectionHandler; }
            set { patientSelectionHandler = value; }
        }

        //public Panel ActionPanel
        //{
        //    get { return actionPanel; }
        //}

        #endregion
    }
}
