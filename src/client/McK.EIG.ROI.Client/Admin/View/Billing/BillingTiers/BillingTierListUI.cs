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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{
    public partial class BillingTierListUI : AdminBaseListUI 
    {
        #region Fields

        private const string NameColumn        = "tiername";
        private const string MediaTypeColumn   = "mediatype";
        private const string DescriptionColumn = "description";

        private Collection<MediaTypeDetails> mediaTypes;

        #endregion

        # region Constructor

        public BillingTierListUI()
        {
            InitializeComponent();
        }

        # endregion

        # region Methods

        /// <summary>
        /// Initialize the datagrid
        /// </summary>
        protected override void InitGrid()
        {
            //Add columns to grid view.
            grid.AddImageColumn("Image", String.Empty, "Image", 25);
            grid.AddTextBoxColumn(NameColumn, "TierName", "Name", 150);
            grid.AddTextBoxColumn(MediaTypeColumn, "Mediatype", "MediaTypeName", 150);
            DataGridViewTextBoxColumn dgvDescColumn = grid.AddTextBoxColumn(DescriptionColumn,
                                                                                "Description",
                                                                                "Description",
                                                                                ColumnWidth);
            dgvDescColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDescColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }

        /// <summary>
        /// Check whether the billing tier is seed or associated with media type.
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        protected override bool CanDelete(object data)
        {
            BillingTierDetails billingTier = (BillingTierDetails)data;
            return !((billingTier.Id < 0) || billingTier.IsAssociated);
        }

        /// <summary>
        /// Refresh Entity data.
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        protected override object RefreshEntityData(object data)
        {
            BillingTierDetails billingTierDetail = (BillingTierDetails)data;
            billingTierDetail = BillingAdminController.Instance.GetBillingTier(billingTierDetail.Id);
            return billingTierDetail;
        }

        /// <summary>
        /// Delete the billing tier.
        /// </summary>
        /// <param name="data"></param>
        protected override void DeleteEntity(object data)
        {
            BillingTierDetails billingTierToDelete = (BillingTierDetails)data;
            BillingAdminController.Instance.DeleteBillingTier(billingTierToDelete.Id);
        }

        /// <summary>
        /// Set Culture Text to the contol.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, deleteButton);
            SetLabel(rm, createButton);
            countLabel.Tag = rm.GetString("countLabel." + GetType().Name);
            UpdateRowCount();

            grid.Columns[NameColumn].HeaderText        = rm.GetString("tierName.columnHeader");
            grid.Columns[MediaTypeColumn].HeaderText   = rm.GetString("mediaType.columnHeader");
            grid.Columns[DescriptionColumn].HeaderText = rm.GetString("description.columnHeader");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteButton);
            SetTooltip(rm, toolTip, createButton);
        }

        /// <summary>
        /// Get the Localize key of UI control to show tooltip message.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="tooltip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Cache MediaTypes locally.
        /// </summary>
        /// <param name="mediaTypes"></param>
        public void CacheMediaTypes(Collection<MediaTypeDetails> mediaTypes)
        {
            this.mediaTypes = mediaTypes;
        }

        #endregion

        #region Properties

        /// <summary>
        /// gets the key of delete confirm message
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.BillingTierDeleteMessage; }
        }

        #endregion 
    }
}
