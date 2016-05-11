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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.Client.Patient.View.PatientRecords;
using McK.EIG.ROI.Client.Base.Model;
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Patient.View
{
    /// <summary>
    /// EncounterDetailDialogUI
    /// </summary>
    public partial class EncounterDetailDialogUI : ROIBaseUI
    {

        #region Fields

        private HeaderUI header;

        private const string EncounterColumn      = "encounter";
        private const string MrnColumn            = "mrn";
        private const string AdmittedColumn       = "admitted";
        private const string DischargeColumn      = "discharge";
        private const string DispositionColumn    = "disposition";
        private const string PatientServiceColumn = "patientservice";
        private const string ClassColumn          = "class";
        private const string BalanceColumn        = "balance";
        private const string ClinicColumn         = "clinic";
        private const string SelfPayColumn        = "selfPay";

        private string mrn;

        #endregion

        #region Constructor
        /// <summary>
        /// EncounterDetailDialogUI
        /// </summary>
        public EncounterDetailDialogUI(IPane pane, string mrn)
        {
            InitializeComponent();
            SetPane(pane);
            SetExecutionContext(pane.Context);
            InitGrid();
            Localize();
            this.mrn = mrn;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Intitializes grid
        /// </summary>
        private void InitGrid()
        {
            grid.AddTextBoxColumn(EncounterColumn, "Encounter", "encounterid", 90);
            grid.AddTextBoxColumn(MrnColumn, "MRN", "mrn", 75);
            grid.AddTextBoxColumn(AdmittedColumn, "Admitted", "admitted", 80);
            grid.AddTextBoxColumn(DischargeColumn, "Discharge", "discharge", 100);
            grid.AddTextBoxColumn(PatientServiceColumn, "PatientService", "patientservice", 110);
            grid.AddTextBoxColumn(DispositionColumn, "Disposition", "disposition", 100);
            grid.AddTextBoxColumn(ClassColumn, "Class", "class", 75);
            grid.AddTextBoxColumn(BalanceColumn, "Balance", "balance", 100);
            grid.AddTextBoxColumn(ClinicColumn, "Clinic", "clinic", 100)/*.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill*/;
            DataGridViewImageColumn selfPayImageColumn = grid.AddImageColumn(SelfPayColumn, "Self Pay", "IsSelfPay", 100);
            selfPayImageColumn.DefaultCellStyle.NullValue = null;
            selfPayImageColumn.SortMode = DataGridViewColumnSortMode.Automatic;
            selfPayImageColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;
            selfPayImageColumn.CellTemplate.Style.Padding = new System.Windows.Forms.Padding(20, 0, 0, 0);
            selfPayImageColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            grid.MultiSelect = false;
        }

        /// <summary>
        /// Sets data to the grid
        /// </summary>
        /// <param name="encoutners"></param>
        /// <param name="selectdEncoutners"></param>
        public void SetData(Collection<EncounterDetails> encounters, string facility)                            
        {
            grid.SelectionChanged += new EventHandler(grid_SelectionChanged);
            Collection<string> securityIds = new Collection<string>();

            securityIds.Add(ROISecurityRights.EncounterCustomInfo);            

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            if (ROIViewUtility.IsAllowed(securityIds, true, facility))
            {
                encounters = PatientController.Instance.GetCustomFields(encounters);
            }
            else
            {
                customFieldInfo.Text = rm.GetString("encounterCustomField");
            }


            List<EncounterDetails> selectedEncounters = new List<EncounterDetails>(encounters);
            selectedEncounters.Sort(EncounterDetails.Sorter);

            Collection<string> customAttributes = new Collection<string>();

            foreach (EncounterDetails encounter in selectedEncounters)
            {
                foreach (string customFieldName in encounter.CustomFields.Keys)
                {
                    if (!customAttributes.Contains(customFieldName))
                    {
                        customAttributes.Add(customFieldName);
                    }
                }
            }

            foreach (string header in customAttributes)
            {
                grid.AddTextBoxColumn(header, header, header, 100);
            }
            
            grid.Columns[grid.ColumnCount - 1].Width = ColumnWidth;

            object[] values;
            int index = 0;

            foreach (EncounterDetails encounter in selectedEncounters)
            {
                values = new object[grid.Columns.Count];

                values[0] = encounter.EncounterId;
                values[1] = mrn;
                values[2] = encounter.AdmitDate.HasValue ? encounter.AdmitDate.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) : string.Empty;
                values[3] = encounter.DischargeDate.HasValue ? encounter.DischargeDate.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) : string.Empty;
                values[4] = encounter.PatientService;
                values[5] = encounter.Disposition;
                values[6] = encounter.FinancialClass;
                values[7] = encounter.Balance;
                values[8] = encounter.Clinic;
                values[9] = (encounter.IsSelfPay) ? ROIImages.BillableIcon : null;

                for (int count = 10; count <= grid.Columns.Count - 1; count++)
                {
                    if (encounter.CustomFields.ContainsKey(grid.Columns[count].Name))
                    {
                        values[count] = encounter.CustomFields[grid.Columns[count].Name];
                    }
                    else
                    {
                        values[count] = string.Empty;
                    }
                }
                
                grid.Rows.Insert(index, values);
                index++;
            }
            if (grid.Rows.Count == 1)
            {
                countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("encounter.EncounterDetailDialogUI"), grid.Rows.Count);
            }
            else
            {
                countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("encounters.EncounterDetailDialogUI"), grid.Rows.Count);
            }
            grid.Sort(grid.Columns[AdmittedColumn], System.ComponentModel.ListSortDirection.Descending);
        }

        private void grid_SelectionChanged(object sender, EventArgs e)
        {
            foreach (DataGridViewRow row in grid.Rows)
            {
                if (ROIImages.SelectedBillableIcon.Equals(row.Cells[SelfPayColumn].Value))
                {
                    row.Cells[SelfPayColumn].Value = ROIImages.BillableIcon;
                    break;
                }
            }
            if (ROIImages.BillableIcon.Equals(grid.SelectedRows[0].Cells[SelfPayColumn].Value))
            {
                grid.SelectedRows[0].Cells[SelfPayColumn].Value = ROIImages.SelectedBillableIcon;
            }
        }

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(grid, rm, EncounterColumn);
            SetLabel(grid, rm, MrnColumn);
            SetLabel(grid, rm, AdmittedColumn);
            SetLabel(grid, rm, DischargeColumn);
            SetLabel(grid, rm, DispositionColumn);
            SetLabel(grid, rm, ClassColumn);
            SetLabel(grid, rm, BalanceColumn);
            SetLabel(grid, rm, ClinicColumn);
            SetLabel(grid, rm, SelfPayColumn);
            SetLabel(rm, countLabel);
            SetLabel(rm, closeButton);
            //Tooltip
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, closeButton);
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
        /// Closes the window.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void closeButton_Click(object sender, EventArgs e)
        {            
            ((Form)this.Parent).Close();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns herder
        /// </summary>
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

        /// <summary>
        /// To fill the last column width.
        /// </summary>
        public int ColumnWidth
        {
            get
            {
                int sum = 0;
                foreach (DataGridViewColumn column in grid.Columns)
                {
                    sum += column.Width;
                }
                sum = grid.Width - sum;
                sum = grid.Columns[grid.Columns.Count - 1].Width + sum - 3;
                return sum;
            }
        }

        #endregion
    }
}
