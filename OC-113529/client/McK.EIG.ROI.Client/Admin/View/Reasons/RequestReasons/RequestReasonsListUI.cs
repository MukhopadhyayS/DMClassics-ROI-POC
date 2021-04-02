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

namespace McK.EIG.ROI.Client.Admin.View.Reasons.RequestReasons
{
    public partial class RequestReasonsListUI : AdminBaseListUI
    {
        #region Fields
        
        private const string ReasonNameColumn  = "reasonName";
        private const string AttributeColumn   = "attribute";
        private const string DisplayTextColumn = "displayText";

        #endregion

        #region Constructor
        
        public RequestReasonsListUI()
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
            //Add columns to gridview.
            grid.AddTextBoxColumn(ReasonNameColumn, "Reason Name", "Name", 150);
            grid.AddTextBoxColumn(AttributeColumn, "Attribute", "AttributeName", 150);
            DataGridViewTextBoxColumn dgvDisplayColumn = grid.AddTextBoxColumn(DisplayTextColumn,
                                                                                "Display Text",
                                                                                "DisplayText",
                                                                                ColumnWidth);
            dgvDisplayColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDisplayColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }

        /// <summary>
        /// Checks whether the request reason is seed
        /// </summary>
        protected override bool CanDelete(object data)
        {
            ReasonDetails reasonDetails = (ReasonDetails)data;
            return (!(reasonDetails.Id < 0));
        }

        protected override object RefreshEntityData(object data)
        {
            ReasonDetails reasons = (ReasonDetails)data;
            reasons = ROIAdminController.Instance.GetReason(reasons.Id);
            return reasons;
        }

        protected override void DeleteEntity(object data)
        {
            ReasonDetails reasonToDelete = (ReasonDetails)data;
            ROIAdminController.Instance.DeleteReason(reasonToDelete.Id);
        }

        /// <summary>
        /// Set culture text to the control
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            SetLabel(rm, deleteButton);
            SetLabel(rm, createButton);
            countLabel.Tag = rm.GetString("countLabel." + GetType().Name);
            UpdateRowCount();

            grid.Columns[ReasonNameColumn].HeaderText  = rm.GetString("reasonName.columnHeader");
            grid.Columns[AttributeColumn].HeaderText   = rm.GetString("attribute.columnHeader");
            grid.Columns[DisplayTextColumn].HeaderText = rm.GetString("displayText.columnHeader");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteButton);
            SetTooltip(rm, toolTip, createButton);
        }

        /// <summary>
        /// Gets the Localize key of UI controls to show the tooltip.
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
        /// Gets the key of delete confirm message.
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.RequestReasonDeleteMessage; }
        }

        #endregion
    }
}
