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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Reasons.AdjustmentReasons
{
    /// <summary>
    /// This class used to list the adjustment reasons in grid.
    /// </summary>
    public partial class AdjustmentReasonsListUI : AdminBaseListUI
    {
        #region Fields

        private const string NameColumn        = "reasonsname";
        private const string DisplayTextColumn = "displaytext";

        #endregion

        #region Constructor

        public AdjustmentReasonsListUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the datagrid
        /// </summary>
        protected override void InitGrid()
        {
            grid.AddTextBoxColumn(NameColumn, "Reasons Name", "Name", 150);
            DataGridViewTextBoxColumn dgvDisplayTextColumn = grid.AddTextBoxColumn(DisplayTextColumn, 
                                                                                   "Display Text",
                                                                                   "DisplayText", 
                                                                                   ColumnWidth);
            dgvDisplayTextColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDisplayTextColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }
        /// <summary>
        /// This method is invoked when the row selection change and row updated
        /// in the grid.
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        protected override object RefreshEntityData(object data)
        {
            ReasonDetails adjustmentReason = (ReasonDetails)data;
            adjustmentReason = ROIAdminController.Instance.GetReason(adjustmentReason.Id);
            return adjustmentReason;
        }

        /// <summary>
        /// Delete Adjstment Reason.
        /// </summary>
        /// <param name="data"></param>
        protected override void DeleteEntity(object data)
        {
            ReasonDetails adjustmentReasonToDelete = (ReasonDetails)data;
            ROIAdminController.Instance.DeleteReason(adjustmentReasonToDelete.Id);
        }

        /// <summary>
        ///  Set Culture text to the controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, deleteButton);
            SetLabel(rm, createButton);
            countLabel.Tag = rm.GetString("countLabel." + GetType().Name);
            UpdateRowCount();

            grid.Columns[NameColumn].HeaderText = rm.GetString("reasonName.columnHeader");
            grid.Columns[DisplayTextColumn].HeaderText = rm.GetString("displayText.columnHeader");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteButton);
            SetTooltip(rm, toolTip, createButton);
        }

        /// <summary>
        /// Gets the LocalizeKey of UI controls to show tooltip message
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

        /// <summary>
        /// gets the key of delete confirm message
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.AdjustmentReasonDeleteMessage; }
        }
       
        #endregion
    }
}
