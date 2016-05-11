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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    public partial class DeficiencyDialog : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;

        private const string PhysicianColumn = "physician";
        private const string TypeColumn      = "type";
        private const string DocumentColumn  = "document";
        private const string ReasonColumn    = "reason";
        private const string StatusColumn    = "status";
        private const string LockedByColumn  = "lockedBy";
        
        private EventHandler radioButtonCheckedChanged;

        #endregion

        #region Constructor

        /// <summary>
        /// It initializes the UI and grid.
        /// </summary>
        public DeficiencyDialog()
        {
            InitializeComponent();
            InitGrid();            
            EnableEvents();
        }

        #endregion

        #region Methods

        /// <summary>
        /// It initializes the grid.
        /// </summary>
        private void InitGrid()
        {
           grid.AddTextBoxColumn(PhysicianColumn, "Physician", "Physician", 150);
           grid.AddTextBoxColumn(TypeColumn, "Type", "Type", 75);
           grid.AddTextBoxColumn(DocumentColumn, "Document", "Document", 175);
           grid.AddTextBoxColumn(ReasonColumn, "Reason", "Reason", 100);
           grid.AddTextBoxColumn(StatusColumn, "Status", "Status", 75);
           DataGridViewTextBoxColumn dgvLockedByColumn = grid.AddTextBoxColumn(LockedByColumn, "Locked By", "LockedBy", 150);
           dgvLockedByColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
           DataGridViewTextBoxColumn dgvLinkedColumn    = grid.AddTextBoxColumn("", "", "IsLinked", 0);
           dgvLinkedColumn.Visible = false;
        }

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            //UI Text
            SetLabel(grid, rm, PhysicianColumn);
            SetLabel(grid, rm, TypeColumn);
            SetLabel(grid, rm, DocumentColumn);
            SetLabel(grid, rm, ReasonColumn);
            SetLabel(grid, rm, StatusColumn);
            SetLabel(grid, rm, LockedByColumn);

            SetLabel(rm, displayLabel);
            SetLabel(rm, allRadioButton);
            SetLabel(rm, linkedRadioButton);
            SetLabel(rm, unLinkedRadioButton);
            SetLabel(rm, closeButton);
            SetLabel(rm, countLabel);

            //Tooltip
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, closeButton);
        }

        /// <summary>
        /// Get Localize key.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == countLabel)
            {
                return countLabel.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control);
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
        /// Enable the event.
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            radioButtonCheckedChanged = new EventHandler(Process_RadioButtonCheckedChanged);
            allRadioButton.CheckedChanged      += radioButtonCheckedChanged;
            linkedRadioButton.CheckedChanged   += radioButtonCheckedChanged;
            unLinkedRadioButton.CheckedChanged += radioButtonCheckedChanged;
        }

        /// <summary>
        /// Disable the event.
        /// </summary>
        private void DisableEvents()
        {
            allRadioButton.CheckedChanged      -= radioButtonCheckedChanged;
            linkedRadioButton.CheckedChanged   -= radioButtonCheckedChanged;
            unLinkedRadioButton.CheckedChanged -= radioButtonCheckedChanged;
        }

        /// <summary>
        /// It sets all dificincies to a grid.
        /// </summary>
        /// <param name="encounters"></param>        
        public void SetData(Collection<DeficiencyDetails> deficiencies)
        {
            ComparableCollection<DeficiencyDetails> list = new ComparableCollection<DeficiencyDetails>(deficiencies);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(DeficiencyDetails))["Document"], ListSortDirection.Ascending);
            grid.SetItems(list);
            grid.ClearSelection();
            UpdateCount();
        }

        /// <summary>
        /// Grid Count Update.
        /// </summary>
        private void UpdateCount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());           
            if (grid.Rows.Count == 0)
            {
                countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("noDeficienciesFound.DeficiencyDialog"), grid.Rows.Count);
            }
            else if (grid.Rows.Count == 1)
            {
                countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("deficiency.DeficiencyDialog"), grid.Rows.Count);
            }
            else if (grid.Rows.Count > 1)
            {
                countLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("deficiencies.DeficiencyDialog"), grid.Rows.Count);
            }
        }

        private void Process_RadioButtonCheckedChanged(object sender, EventArgs e)
        {
            if (allRadioButton.Checked)
            {
                grid.RemoveFilter();                
            }
            else if (linkedRadioButton.Checked)
            {
                grid.Filter = "IsLinked='true'";
            }
            else if (unLinkedRadioButton.Checked)
            {
                grid.Filter = "IsLinked='false'";
            }            
            if (grid.Rows.Count > 0)
            {
                grid.Rows[0].Selected = true;
            }
            UpdateCount();
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
