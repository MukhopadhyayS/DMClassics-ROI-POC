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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTemplate
{
    /// <summary>
    /// This class used to list the billingtemplate in grid.
    /// </summary>
    /// 
    public partial class BillingTemplateListUI : AdminBaseListUI
    {       

        #region Fields

        private const string TemplateNameColumn = "templatename";
        private const string FeeTypesColumn     = "feetypes";

        private ListDictionary feeTypes;
        #endregion
        
        #region Constructor

        public BillingTemplateListUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the BillingTemplate datagrid
        /// </summary>
        protected override void InitGrid()
        {
            
            //Add columns to gridview.            
            grid.AddTextBoxColumn(TemplateNameColumn, "Template Name", "Name", 150);            
            DataGridViewTextBoxColumn dgvDescColumn = grid.AddTextBoxColumn(FeeTypesColumn,
                                                                                "FeeTypes",
                                                                                "FeeTypesValue",
                                                                                ColumnWidth);
            dgvDescColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDescColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }

        /// <summary>
        /// Checks whether the billingTemplate is associated with Request and Requstor Type.
        /// </summary>
        /// <param name="selectedbillingTemplate"></param>
        /// <returns></returns>
        protected override bool CanDelete(object data)
        {
            BillingTemplateDetails billingTemplate = (BillingTemplateDetails)data;
            return !(billingTemplate.IsAssociated);
        }

        protected override object RefreshEntityData(object data)
        {
            BillingTemplateDetails billingTemplate = (BillingTemplateDetails)data;
            billingTemplate = BillingAdminController.Instance.GetBillingTemplate(billingTemplate.Id);
            return billingTemplate;
        }

        /// <summary>
        /// Delete the selected billingTemplate from grid
        /// </summary>
        /// <param name="selectedRow"></param>
        protected override void DeleteEntity(object data)
        {
            BillingTemplateDetails billingTemplateToDelete = (BillingTemplateDetails)data;
            BillingAdminController.Instance.DeleteBillingTemplate(billingTemplateToDelete.Id);
        }
        
        public override void EnableDelete()
        {
            if (grid.SelectedRows.Count == 0) return;
            BillingTemplateDetails billingTemplate = (BillingTemplateDetails) grid.SelectedRows[0].DataBoundItem;           
            if (billingTemplate.Id > 0)
            {
                base.EnableDelete();
            }
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

            grid.Columns[TemplateNameColumn].HeaderText = rm.GetString("billingtemplate.templateName");
            grid.Columns[FeeTypesColumn].HeaderText = rm.GetString("billingtemplate.feeTypes");

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

        public void CacheFeeTypes(ListDictionary feeTypes)
        {
            this.feeTypes = feeTypes;
        }

        private BillingTemplateDetails UpdateFeeTypesName(BillingTemplateDetails billingTemplate)
        {
            billingTemplate.FeeTypesValue = string.Empty;

            ArrayList listFeeTypes = new ArrayList();

            FeeTypeDetails feeTypeDetail = null;
            foreach (AssociatedFeeType associatedFeeType in billingTemplate.AssociatedFeeTypes)
            {
                feeTypeDetail = (FeeTypeDetails)feeTypes[associatedFeeType.FeeTypeId];
                listFeeTypes.Add(feeTypeDetail.Name);
            }
            listFeeTypes.Sort();

            for (int count = 0; count < listFeeTypes.Count; count++)
            {
                billingTemplate.FeeTypesValue += listFeeTypes[count] + ", ";
            }

            billingTemplate.FeeTypesValue = billingTemplate.FeeTypesValue.Trim().TrimEnd(',');
            
            return billingTemplate;
        }

        public override void SetData(object data)
        {
            ComparableCollection<BillingTemplateDetails> billingTemplates = (ComparableCollection<BillingTemplateDetails>)data;
            foreach (BillingTemplateDetails billingTemplate in billingTemplates)
            {
                UpdateFeeTypesName(billingTemplate);
            }            
            base.SetData(billingTemplates);
            if (grid.RowCount > 0)
            {
                grid.SelectRow(0);
            }
            if (feeTypes.Count == 0)
            {
                DisableCreate();
            }            
        }

        public override void AddRow(object data)
        {
            base.AddRow(UpdateFeeTypesName(data as BillingTemplateDetails));
        }

        public override void UpdateRow(object data)
        {
            base.UpdateRow(UpdateFeeTypesName(data as BillingTemplateDetails));
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the key of delete confirm message
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.BillingTemplateNameDeleteMessage; }
        }

        #endregion

    }
}
