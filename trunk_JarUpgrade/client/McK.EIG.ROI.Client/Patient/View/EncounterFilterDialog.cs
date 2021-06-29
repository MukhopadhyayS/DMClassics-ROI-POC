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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Patient.View
{
    public partial class EncounterFilterDialog : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;

        private const string CheckBoxColumn  = "checkBoxColumn";
        private const string EncounterColumn = "encounter";
        private const string StatusColumn    = "patientStatus";
        private const string FacilityColumn  = "facility";
        private const string TypeColumn      = "patientType";
        private const string DateColumn      = "admitDate";
        private const string EncounterIDColumn = "encounterIDColumn";

        private string count;

        private IList<string> selectedEncounters;

        private EventHandler filterHandler;
        private CancelEventHandler cancelHandler;
        private MouseEventHandler rowDoubleClickHandler;
        
        #endregion

        #region Methods

        /// <summary>
        /// This constructor collects selected encounters
        /// </summary>
        /// <param name="closeEventHandler"></param>
        /// <param name="filterHandler"></param>
        /// <param name="selectedEncounters"></param>
        public EncounterFilterDialog(CancelEventHandler closeEventHandler, EventHandler filterHandler, IList<string> selectedEncounters)
            : this()
        {
            cancelHandler           = closeEventHandler;
            this.filterHandler      = filterHandler; 
            this.selectedEncounters = selectedEncounters;
        }

        /// <summary>
        /// It initializes the UI and grid.
        /// </summary>
        public EncounterFilterDialog()
        {
            InitializeComponent();
            InitGrid();
        }

        /// <summary>
        /// It initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            DataGridViewCheckBoxColumn dgvCheckColumn    = grid.AddCheckBoxColumn(CheckBoxColumn, string.Empty, string.Empty, 30);
            DataGridViewTextBoxColumn dgvEncounterColumn = grid.AddTextBoxColumn(EncounterColumn, "Encounter", "EncounterId", 270);
            DataGridViewTextBoxColumn dgvStatusColumn    = grid.AddTextBoxColumn(StatusColumn, "Patient Status", "PatientStatus", 150);
            DataGridViewTextBoxColumn dgvFacilityColumn  = grid.AddTextBoxColumn(FacilityColumn, "Facility", "Facility", 100);
            DataGridViewTextBoxColumn dgvTypeColumn      = grid.AddTextBoxColumn(TypeColumn, "Patient Type", "PatientType", 100);
            DataGridViewTextBoxColumn dgvDateColumn      = grid.AddTextBoxColumn(DateColumn, "Admit Date", "DateOfService", 112);
            DataGridViewTextBoxColumn dgvEncounterIDColumn = grid.AddTextBoxColumn(EncounterIDColumn, "Encounter", "EncounterId", 0);
            dgvEncounterIDColumn.Visible = false;
            dgvDateColumn.AutoSizeMode                   = DataGridViewAutoSizeColumnMode.Fill;

            dgvCheckColumn.TrueValue = true;
            dgvCheckColumn.FalseValue = false;

            dgvEncounterColumn.ReadOnly = true;
            dgvStatusColumn.ReadOnly    = true;
            dgvTypeColumn.ReadOnly      = true;
            dgvDateColumn.ReadOnly      = true;
            dgvFacilityColumn.ReadOnly  = true;
            grid.MultiSelect = false;

            rowDoubleClickHandler = new MouseEventHandler(grid_RowDoubleClick);
        }
        
        /// <summary>
        /// It sets all encounters to a grid.
        /// </summary>
        /// <param name="encounters"></param>
        public void SetData(Collection<EncounterDetails> encounters)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string selfPayEncounter = rm.GetString("Self Pay Encounter");
            int index = 0;
            foreach (EncounterDetails encounter in encounters)
            {
                grid.Rows.Insert(index, new object[] { selectedEncounters.Contains(encounter.EncounterId + ROIConstants.Delimiter + encounter.Facility),
                (encounter.IsSelfPay) ? encounter.EncounterId + selfPayEncounter : encounter.EncounterId,
                                                       encounter.PatientService, 
                                                       encounter.Facility, 
                                                       encounter.PatientType, 
                                                       (encounter.AdmitDate.HasValue)?encounter.AdmitDate.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture):null,
                                                       encounter.EncounterId
                                                      }

                                );
                index++;
            }

            grid.Sort(grid.Columns[0], ListSortDirection.Descending);
            countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, count, grid.Rows.Count);
            grid.CellContentClick += new DataGridViewCellEventHandler(grid_CellContentClick);
            grid.MouseDoubleClick += rowDoubleClickHandler;
            filterOnEncounterButton.Enabled = selectedEncounters.Count > 0;
        }

		/// <summary>
        /// Occurs when user clicks on any editable field in grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>     
        private void grid_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            filterOnEncounterButton.Enabled = false;

            foreach (DataGridViewRow row in grid.Rows)
            {
                DataGridViewCheckBoxCell checkBoxCell = (DataGridViewCheckBoxCell)row.Cells[CheckBoxColumn];

                if (e.RowIndex == row.Index)
                {
                    checkBoxCell.Value = !Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                if (Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture))
                {
                    filterOnEncounterButton.Enabled = true;
                    break;
                }
            }
        }

        /// <summary>
        /// Event occurs when the user Double click in the grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_RowDoubleClick(object sender, MouseEventArgs e)
        {
            DataGridViewCheckBoxCell checkBoxCell = (DataGridViewCheckBoxCell)grid.SelectedRows[0].Cells[CheckBoxColumn];
            checkBoxCell.Value = !Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture);
            GetSelectedEncounters();
        }
       
        /// <summary>
        /// Close this dialog and turn on the encounter filter if it was not previously on. 
        /// Filter to only show data in the calling screens that relates to the encounters check in the grid on this dialog box
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void filterOnEncounterButton_Click(object sender, EventArgs e)
        {
            GetSelectedEncounters();
            filterHandler(selectedEncounters, e);
            cancelHandler(this.ParentForm, null);
        }

        /// <summary>
        /// Collectes all checked encounters in the grid.
        /// </summary>
        private void GetSelectedEncounters()
        {
            selectedEncounters.Clear();
            foreach (DataGridViewRow row in grid.Rows)
            {
                if ((bool)row.Cells[CheckBoxColumn].Value)
                {
                    selectedEncounters.Add(Convert.ToString(row.Cells[EncounterIDColumn].Value, System.Threading.Thread.CurrentThread.CurrentUICulture) + 
                                           ROIConstants.Delimiter +
                                           Convert.ToString(row.Cells[FacilityColumn].Value, System.Threading.Thread.CurrentThread.CurrentUICulture));
                }
            }
            filterOnEncounterButton.Enabled = selectedEncounters.Count > 0;
        }

        /// <summary>
        /// Dismiss this dialog and turn off the encounter filter. The calling screens will show data related to all encounters.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void filterOffEncounterButton_Click(object sender, EventArgs e)
        {
            selectedEncounters.Clear();
            filterHandler(selectedEncounters, e);
            cancelHandler(this.ParentForm, null);
        }

        /// <summary>
        /// Close this dialog and do not change the current status of the filter.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            cancelHandler(this.ParentForm, null);
        }

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            SetLabel(grid, rm, EncounterColumn);
            SetLabel(grid, rm, StatusColumn);
            SetLabel(grid, rm, FacilityColumn);
            SetLabel(grid, rm, TypeColumn);
            SetLabel(grid, rm, DateColumn);            

            SetLabel(rm, filterOnEncounterButton);
            SetLabel(rm, filterOffEncounterButton);
            SetLabel(rm, cancelButton);

            count = rm.GetString("countLabel.EncounterFilterDialog");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, filterOnEncounterButton);
            SetTooltip(rm, toolTip, filterOffEncounterButton);
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
