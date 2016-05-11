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
using System.Collections.Specialized;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Reasons.StatusReasons
{
    /// <summary>
    /// This class used to list the StatusReasons in grid.
    /// </summary>
    public partial class StatusReasonsListUI : AdminBaseListUI
    {
        #region Fields

        private const string ReasonNameColumn    = "reasonname";
        private const string DisplayTextColumn   = "displaytext";
        private const string RequestStatusColumn = "requeststatus";

        #endregion

        #region Constructor

        public StatusReasonsListUI()
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
            grid.AddTextBoxColumn(RequestStatusColumn, "Request Status", "RequestStatusText", 150);
            grid.AddTextBoxColumn(ReasonNameColumn, "Reason Name", "Name", 150);
            DataGridViewTextBoxColumn dgvDisplayTextColumn = grid.AddTextBoxColumn(DisplayTextColumn,
                                                                                "Display Text",
                                                                                "DisplayText",
                                                                                ColumnWidth);
            dgvDisplayTextColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
        }

       

        protected override object RefreshEntityData(object data)
        {
            ReasonDetails statusReasons = (ReasonDetails)data;
            statusReasons = ROIAdminController.Instance.GetReason(statusReasons.Id);
            return statusReasons;
        }

        /// <summary>
        /// Delete the status reason
        /// </summary>
        /// <param name="selectedRow"></param>
        protected override void DeleteEntity(object data)
        {
            ReasonDetails statusReasonsToDelete = (ReasonDetails)data;
            ROIAdminController.Instance.DeleteReason(statusReasonsToDelete.Id);
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

            grid.Columns[ReasonNameColumn].HeaderText    = rm.GetString("reasonName.columnHeader");
            grid.Columns[RequestStatusColumn].HeaderText = rm.GetString("reasonRequestStatus.columnHeader");
            grid.Columns[DisplayTextColumn].HeaderText   = rm.GetString("displayText.columnHeader");

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
            get { return ROIErrorCodes.StatusReasonDeleteMessage; }
        }

        #endregion
    }
}
