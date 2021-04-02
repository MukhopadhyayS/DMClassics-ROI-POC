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

namespace McK.EIG.ROI.Client.Admin.View.Billing.FeeTypes
{
    public partial class FeeTypeListUI : AdminBaseListUI
    {
        #region Fields

        private const string NameColumn        = "feename";
        private const string DescriptionColumn = "description";
        private const string AmountColumn      = "amount";
        private const string SalesTaxColumn    = "salestax";

        #endregion

        #region Constructor

        public FeeTypeListUI()
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
            grid.AddTextBoxColumn(NameColumn, "Fee Name", "Name", 150);
            
            DataGridViewTextBoxColumn dgvAmountColumn = grid.AddTextBoxColumn(AmountColumn, 
                                                                              "Amount", 
                                                                              "Amount", 
                                                                              150);
            dgvAmountColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvAmountColumn.DefaultCellStyle.Format = "c";
            dgvAmountColumn.Tag = "FeeType";

            grid.AddTextBoxColumn(SalesTaxColumn, "Sales Tax", "SalesTax", 90);

            DataGridViewTextBoxColumn dgvDescColumn = grid.AddTextBoxColumn(DescriptionColumn,
                                                                                "Description",
                                                                                "Description",
                                                                                ColumnWidth);
            dgvDescColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDescColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }

        /// <summary>
        /// Checks whether the mediatype is seed or associated with billing tier.
        /// </summary>
        /// <param name="selectedMediaType"></param>
        /// <returns></returns>
        protected override bool CanDelete(object data)
        {
            FeeTypeDetails feeType = (FeeTypeDetails)data;
            return (!feeType.IsAssociated);
        }

        protected override object RefreshEntityData(object data)
        {
            FeeTypeDetails feeType = (FeeTypeDetails)data;
            feeType = BillingAdminController.Instance.RetrieveFeeType(feeType.Id);
            return feeType;
        }

        /// <summary>
        /// Delete the feetype
        /// </summary>
        /// <param name="selectedRow"></param>
        protected override void DeleteEntity(object data)
        {
            FeeTypeDetails feeTypeToDelete = (FeeTypeDetails)data;
            BillingAdminController.Instance.DeleteFeeType(feeTypeToDelete.Id);
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
            
            grid.Columns[NameColumn].HeaderText = rm.GetString("feeName.columnHeader");
            grid.Columns[DescriptionColumn].HeaderText = rm.GetString("description.columnHeader");
            grid.Columns[AmountColumn].HeaderText = rm.GetString("feeName.AmountColumnHeader");
            grid.Columns[SalesTaxColumn].HeaderText = rm.GetString("salesTax.columnHeader");            

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
            get { return ROIErrorCodes.FeeTypeDeleteMessage; }
        }

        #endregion
    }
}
