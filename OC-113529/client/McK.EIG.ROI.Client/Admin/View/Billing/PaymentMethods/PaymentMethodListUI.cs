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

namespace McK.EIG.ROI.Client.Admin.View.Billing.PaymentMethods
{
    /// <summary>
    /// List the payment methods
    /// </summary>
    public partial class PaymentMethodListUI : AdminBaseListUI
    {
        #region Fields

        private const string NameColumn        = "paymentmethodname";
        private const string DescriptionColumn = "description";
        private const string DisplayColumn     = "display";

        #endregion

        #region Constructor

        public PaymentMethodListUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        protected override void InitGrid()
        {
            grid.AddTextBoxColumn(NameColumn, "Payment Method Name", "Name", 150);
            DataGridViewTextBoxColumn dgvDescColumn = grid.AddTextBoxColumn(DescriptionColumn,
                                                                                "Description",
                                                                                "Description",
                                                                                ColumnWidth);
            dgvDescColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDescColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }

        /// <summary>
        /// Checks whether the payment method is seed.
        /// </summary>
        /// <param name="selectedMediaType"></param>
        /// <returns></returns>
        protected override bool CanDelete(object data)
        {
            PaymentMethodDetails paymentMethod = (PaymentMethodDetails)data;
            return !(paymentMethod.Id < 0);
        }

        protected override object RefreshEntityData(object data)
        {
            PaymentMethodDetails paymentMethod = (PaymentMethodDetails)data;
            paymentMethod = BillingAdminController.Instance.GetPaymentMethod(paymentMethod.Id);
            return paymentMethod;
        }

        /// <summary>
        /// Delete the payment method
        /// </summary>
        /// <param name="selectedRow"></param>
        protected override void DeleteEntity(object data)
        {
            PaymentMethodDetails paymentMethodToDelete = (PaymentMethodDetails)data;
            BillingAdminController.Instance.DeletePaymentMethod(paymentMethodToDelete.Id);
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

            grid.Columns[NameColumn].HeaderText = rm.GetString("paymentMethod.columnHeader");
            grid.Columns[DescriptionColumn].HeaderText = rm.GetString("description.columnHeader");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteButton);
            SetTooltip(rm, toolTip, createButton);
        }

        /// <summary>
        /// Enables the delete button depends on system/user data.
        /// </summary>
        public override void EnableDelete()
        {
            if (grid.SelectedRows.Count == 0) return;
            PaymentMethodDetails paymentMethod = (PaymentMethodDetails)grid.SelectedRows[0].DataBoundItem;
            if (paymentMethod.Id > 0)
            {
                base.EnableDelete();
            }
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
        /// Gets the delete confirm message key.
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.PaymentMethodDeleteMessage; }
        }

        #endregion
    }
}
