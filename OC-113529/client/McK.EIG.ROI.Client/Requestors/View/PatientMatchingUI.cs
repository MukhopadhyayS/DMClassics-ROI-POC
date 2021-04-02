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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Requestors.View
{
    /// <summary>
    /// PatientMatchingUI
    /// </summary>
    public partial class PatientMatchingUI : ROIBaseUI
    {

        #region Fields
       
        private const string CheckBoxColumn = "checkBoxColumn";
        private const string PatientNameColumn = "patientName";
        private const string DateColumn = "dateOfBirth";
        private const string SSNColumn = "ssn";
        private const string FacilityColumn = "facility";
        private const string MRNColumn = "mrn";
        private const string EPNColumn = "epn";

        private HeaderUI header;

        private RequestDetails request;

        private EventHandler selectionHandler;
        private EventHandler cancelHandler;

        #endregion

        #region Constructor

        /// <summary>
        /// It initializes the UI and grid.
        /// </summary>
        public PatientMatchingUI()
        {
            InitializeComponent();
            InitGrid();
        }

        /// <summary>
        /// This constructor collects selected reqeuestor
        /// </summary>
        /// <param name="closeEventHandler"></param>        
        /// <param name="selectedEncounters"></param>
        public PatientMatchingUI(EventHandler selectionHandler, EventHandler cancelHandler, IPane pane) : this()           
        {
            this.selectionHandler = selectionHandler;
            this.cancelHandler = cancelHandler;

            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
        }

        #endregion

        #region Methods

        private void InitGrid() 
        {

            grid.AddCheckBoxColumn(CheckBoxColumn, string.Empty, string.Empty, 30);
            grid.AddTextBoxColumn(PatientNameColumn, "Patient Name", "Name", 150).ReadOnly = true;
            grid.AddTextBoxColumn(DateColumn, "Date of Birth", "FormattedDOB", 150).ReadOnly = true;
            DataGridViewTextBoxColumn dgvSSNColumn = grid.AddTextBoxColumn(SSNColumn,
                                                                           "SSN",
                                                                           "MaskedSSN",
                                                                           150);
            dgvSSNColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvSSNColumn.ReadOnly = true;
            
            grid.AddTextBoxColumn(FacilityColumn, "Facility", "FacilityCode", 100);
            
            DataGridViewTextBoxColumn dgvMrnColumn = grid.AddTextBoxColumn(MRNColumn, "MRN", "MRN", 112);
            dgvMrnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            DataGridViewTextBoxColumn dgvEpnColumn = grid.AddTextBoxColumn(EPNColumn, "EPN", "EPn", 122);
            dgvEpnColumn.Visible = UserData.Instance.EpnEnabled;
            dgvEpnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
        }

        
        /// <summary>
        /// It sets all patients to a grid.
        /// </summary>
        /// <param name="encounters"></param>
        public void SetData(RequestDetails request, Collection<PatientDetails> matchingPatients)
        {
            this.request = request;

            countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countLabel.Text, matchingPatients.Count);

            //ComparableCollection<PatientDetails> list = new ComparableCollection<PatientDetails>(matchingPatients);
            //grid.SetItems(list);
            grid.ClearSelection();
            int index = 0;
            foreach (PatientDetails patient in matchingPatients)
            {
                grid.Rows.Insert(index, new object[] { false,
                                                       patient.Name,
                                                       patient.DOB.HasValue?patient.DOB.Value.ToShortDateString():null,
                                                       patient.MaskedSSN, 
                                                       patient.FacilityCode, 
                                                       patient.MRN,
                                                       patient.EPN 
                                                      }
                               );


                grid.Rows[index].Tag = patient;
                index++;
            }

            request.Patients.Clear();
            continueWithSelectedPatientsButton.Enabled = false;
        }
        
        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(grid, rm, DateColumn);
            SetLabel(grid, rm, FacilityColumn);
            SetLabel(grid, rm, SSNColumn);
            SetLabel(grid, rm, MRNColumn);
            SetLabel(grid, rm, PatientNameColumn);

            SetLabel(rm, continueWithSelectedPatientsButton);
            SetLabel(rm, continueWithNoPatientButton);
            SetLabel(rm, cancelButton);

            countLabel.Text = rm.GetString("countLabel.PatientMatchingUI");

            extraInfoLabel.Text = rm.GetString("extraInfoLabel");
            SetLabel(rm, extraInfoLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, continueWithSelectedPatientsButton);
            SetTooltip(rm, toolTip, continueWithNoPatientButton);
            SetTooltip(rm, toolTip, cancelButton);
        }
       
        /// <summary>
        /// Get Localize key.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            cancelHandler(this, null);
        }

        private void continueWithSelectedPatientsButton_Click(object sender, EventArgs e)
        {
            selectionHandler(this, new BaseEventArgs(request));
        }

        private void continueWithNoPatientButton_Click(object sender, EventArgs e)
        {
            request.Patients.Clear();
            selectionHandler(this, new BaseEventArgs(request));
        }

        private void grid_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 0)
            {
                RequestPatientDetails patient = new RequestPatientDetails((PatientDetails)grid.Rows[e.RowIndex].Tag);

                if ((bool)grid.Rows[e.RowIndex].Cells[0].Value)
                {
                    request.Patients.Add(patient.Key, patient);
                }
                else
                {
                    request.Patients.Remove(patient.Key);
                }

                continueWithSelectedPatientsButton.Enabled = (request.Patients.Count > 0);
            }
        }

        private void grid_CurrentCellDirtyStateChanged(object sender, EventArgs e)
        {
            if (grid.IsCurrentCellDirty)
            {
                grid.CommitEdit(DataGridViewDataErrorContexts.Commit);
            }
        }


        #endregion

        #region Properties

        internal HeaderUI Header
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
