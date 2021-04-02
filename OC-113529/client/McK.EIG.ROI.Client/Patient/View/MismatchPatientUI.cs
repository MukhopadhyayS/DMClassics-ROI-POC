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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Patient.View
{
    /// <summary>
    /// PotentialMismatchPatientDialog
    /// </summary>
    public partial class MismatchPatientUI : ROIBaseUI
    {
        #region Fields

        private const string CheckedColumn      = "checked";
        private const string PatientNameColumn  = "patientName";
        private const string DOBColumn          = "dataofBirth";
        private const string SSNColumn          = "ssn";
        private const string FacilityColumn     = "facility";
        private const string MRNColumn          = "mrn";
        private const string EPNColumn          = "epn";

        private HeaderUI header;

        private Collection<PatientDetails> selectedPatients;
        private SortedList<string, PatientDetails> mismatchPatients;
        
        #endregion

        #region Constructor

        /// <summary>
        /// It initializes the UI and grid.
        /// </summary>
        public MismatchPatientUI()
        {
            InitializeComponent();
            InitGrid();
            selectedPatients = new Collection<PatientDetails>();
        }

        /// <summary>
        /// Constructor assign values to class level variables. 
        /// </summary>
        /// <param name="patientRequestorMatchingHandler"></param>
        /// <param name="patients"></param>
        /// <param name="pane"></param>
        public MismatchPatientUI(IPane pane): this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            header = InitHeaderUI();
            Localize();
        }

        #endregion

        #region Methods


        /// <summary>
        /// It initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            grid.AddCheckBoxColumn(CheckedColumn, string.Empty, string.Empty, 30);
            grid.AddTextBoxColumn(PatientNameColumn, "Patient Name", "Name", 250).ReadOnly = true;
            DataGridViewTextBoxColumn dgvDOBColumn = grid.AddTextBoxColumn(DOBColumn, "Date of Birth", "DOB", 150);
            dgvDOBColumn.ReadOnly = true;                       
            DataGridViewTextBoxColumn dgvSSNColumn = grid.AddTextBoxColumn(SSNColumn,
                                                                            "SSN",
                                                                            (UserData.Instance.IsSSNMasked) ? "MaskedSSN" : "SSN",
                                                                            150);
            dgvSSNColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvSSNColumn.ReadOnly = true;
            grid.AddTextBoxColumn(FacilityColumn, "Facility", "FacilityCode", 100).ReadOnly = true;
            grid.AddTextBoxColumn(MRNColumn, "MRN", "MRN", 122).ReadOnly = true;
            grid.Columns[MRNColumn].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            grid.AddTextBoxColumn(EPNColumn, "EPN", "EPN", 122).AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            grid.Columns[EPNColumn].ReadOnly = true;
            grid.Columns[EPNColumn].Visible = UserData.Instance.EpnEnabled;
        }

        /// <summary>
        /// Populates all the selected patients in the grid. 
        /// In that mismatched patients will be highlighted in Red colour
        /// </summary>
        /// <param name="selectedPatients"></param>
        /// <param name="mismatchPatients"></param>
        public void SetData(Collection<PatientDetails> selectedPatients, SortedList<string, PatientDetails> mismatchPatients)
        {
            this.mismatchPatients = mismatchPatients;

            ComparableCollection<PatientDetails> list = new ComparableCollection<PatientDetails>(selectedPatients);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(PatientDetails))["Name"], ListSortDirection.Ascending);
            int index = 0;
            foreach (PatientDetails patient in list)
            {
                grid.Rows.Insert(index, new object[] { false,
                                                       patient.Name,
                                                       (patient.DOB.HasValue) ? patient.DOB.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) : null, 
                                                       patient.MaskedSSN,
                                                       patient.FacilityCode, 
                                                       patient.MRN,
                                                       patient.EPN
                                                      }
                               );

                
                grid.Rows[index].Tag = patient;

                if (index != 0)
                {
                    //foreach (PatientDetails mismatchPatient in mismatchPatients)
                    //{
                    //    if (PatientDetails.FieldMatchComparer.Compare(patient, mismatchPatient) < 2)
                    //    {
                    //        grid.Rows[index].DefaultCellStyle.BackColor = Color.Red;
                    //    }
                    //}
                    if (mismatchPatients.ContainsKey(patient.Key))
                    {   
                        grid.Rows[index].DefaultCellStyle.BackColor = Color.Red;
                    }
                }
                index++;
            }

            //grid.SetItems((IFunctionCollection)list);
            grid.Refresh();

            countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countLabel.Text, grid.Rows.Count);

           // grid.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
        }

        //void grid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        //{
        //    foreach (DataGridViewRow row in grid.Rows)
        //    {
        //        if (mismatchPatients.Contains((PatientDetails)row.DataBoundItem))
        //        {
        //            row.DefaultCellStyle.BackColor = Color.Red;
        //        }
        //    }
        //}

    
        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(grid, rm, PatientNameColumn);
            SetLabel(grid, rm, DOBColumn);
            SetLabel(grid, rm, SSNColumn);
            SetLabel(grid, rm, FacilityColumn);
            SetLabel(grid, rm, MRNColumn);
            SetLabel(grid, rm, EPNColumn);
            SetLabel(rm, continueSelectedPatientsButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, patientMismatchInfoLabel);
            SetLabel(rm, countLabel);
            countLabel.Text = rm.GetString("countLabel.PotentialMismatchPatients");

            header.Title = rm.GetString("MismatchPatientUI.header.title");
            header.Information = rm.GetString("MismatchPatientUI.header.info");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, continueSelectedPatientsButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Creates HeaderUI
        /// </summary>
        private HeaderUI InitHeaderUI()
        {
            header = new HeaderUI();
            header.Dock = DockStyle.Fill;
            topPanel.Controls.Add(header);
            return header;
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

        /// <summary>
        /// Occurs when user checks the checkbox, selected rows will be added to selectedPatients collection. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 0)
            {
                if ((bool)grid.Rows[e.RowIndex].Cells[CheckedColumn].Value)
                {
                    selectedPatients.Add(grid.Rows[e.RowIndex].Tag as PatientDetails);
                }
                else
                {
                    selectedPatients.Remove(grid.Rows[e.RowIndex].Tag as PatientDetails);
                }

                continueSelectedPatientsButton.Enabled = selectedPatients.Count > 0;
            }
        }

        /// <summary>
        /// Occurs when check box state changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_CurrentCellDirtyStateChanged(object sender, EventArgs e)
        {
            if (grid.IsCurrentCellDirty)
            {
                grid.CommitEdit(DataGridViewDataErrorContexts.Commit);
            }
        }

        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = continueSelectedPatientsButton;
        }

        #endregion

        #region Properties 

        public Collection<PatientDetails> SelectedPatients
        {
            get { return selectedPatients; }
        }


        #endregion
    }
}

