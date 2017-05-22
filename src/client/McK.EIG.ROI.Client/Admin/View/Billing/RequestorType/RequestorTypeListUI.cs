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
using System.Collections.ObjectModel;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Billing.RequestorType
{
    /// <summary>
    /// This class is used to list the requestor types in the grid
    /// </summary>
    public partial class RequestorTypeListUI : AdminBaseListUI
    {
        #region Fields

        private const string NameColumn = "requestortype";
        private const string SalesTaxColumn = "salestax";
        private const string RecordViewColumn = "recordview";
        private const string HPFBillingTierColumn = "hpfBillingtier";
        private const string NonHPFBillingTierColumn = "nonHpfBillingtier";
        private const string BillingTemplateColumn = "billingtemplate";
        private const string InvoiceColumn = "invoiceOptional";

        private Hashtable htbillingTiers;
        private Hashtable htbillingTemplates;

        #endregion

        #region Constructor

        public RequestorTypeListUI()
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
            grid.AddImageColumn("ImageColumn", String.Empty, "Image", 25);

            grid.AddTextBoxColumn(NameColumn, "Requestor Type", "Name", 150);            
            grid.AddTextBoxColumn(RecordViewColumn, "Record View", "RecordViewName", 150);
            grid.AddTextBoxColumn(HPFBillingTierColumn, "HPF Billing Tier", "HPFBillingTierName", 150);
            grid.AddTextBoxColumn(NonHPFBillingTierColumn, "Non-HPF Billing Tier", "NonHPFBillingTierName", 150);
            grid.AddTextBoxColumn(SalesTaxColumn, "Sales Tax", "SalesTax", 90);
            grid.AddTextBoxColumn(InvoiceColumn, "Invoice Optional", "InvoiceOptional", 90);
            DataGridViewTextBoxColumn dgvBillingTemplateColumn = grid.AddTextBoxColumn(BillingTemplateColumn,
                                                                                       "Billing Template",
                                                                                       "billingTemplateValues",
                                                                                       ColumnWidth);
            dgvBillingTemplateColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
        }

        /// <summary>
        /// Checks whether the requestortype is seed or associated with requestor 
        /// </summary>
        /// <param name="selectedMediaType"></param>
        /// <returns></returns>
        protected override bool CanDelete(object data)
        {
            RequestorTypeDetails requestorType = (RequestorTypeDetails)data;
            return !(requestorType.Id < 0 || requestorType.IsAssociated);
        }

        /// <summary>
        /// Refresh entity data
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        protected override object RefreshEntityData(object data)
        {
            RequestorTypeDetails requestorType = (RequestorTypeDetails)data;
            requestorType = ROIAdminController.Instance.GetRequestorType(requestorType.Id);
            return requestorType;
        }

        /// <summary>
        /// Delete the Requestor Type
        /// </summary>
        /// <param name="selectedRow"></param>
        protected override void DeleteEntity(object data)
        {
            RequestorTypeDetails requestorTypeToDelete = (RequestorTypeDetails)data;
            ROIAdminController.Instance.DeleteRequestorType(requestorTypeToDelete.Id);
        }

        public override void EnableDelete()
        {
            if (grid.SelectedRows.Count == 0) return;
            RequestorTypeDetails requestorType = (RequestorTypeDetails)grid.SelectedRows[0].DataBoundItem;
            if (requestorType.Id > 0)
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

            grid.Columns[NameColumn].HeaderText = rm.GetString("requestorTypeName.columnHeader");
            grid.Columns[RecordViewColumn].HeaderText = rm.GetString("recordView.columnHeader");
            grid.Columns[HPFBillingTierColumn].HeaderText = rm.GetString("hpfBillingTier.columnHeader");
            grid.Columns[NonHPFBillingTierColumn].HeaderText = rm.GetString("nonHpfBillingTier.columnHeader");
            grid.Columns[BillingTemplateColumn].HeaderText = rm.GetString("billingTemplate.columnHeader");
            grid.Columns[SalesTaxColumn].HeaderText = rm.GetString("salesTax.columnHeader");
            grid.Columns[InvoiceColumn].HeaderText = rm.GetString("invoiceOptional.columnHeader");

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

        /// <summary>
        /// Retrieve Eletronic Billing Tiers
        /// </summary>
        /// <param name="billingTierDetails"></param>
        public void CacheBillingTiers(Collection<BillingTierDetails> billingTiers)
        {
            htbillingTiers = new Hashtable(billingTiers.Count);

            foreach (BillingTierDetails billingTier in billingTiers)
            {
                htbillingTiers.Add(billingTier.Id, billingTier);
            }
        }

        /// <summary>
        /// Retrieve Billing Templates
        /// </summary>
        /// <param name="billingTemplateDetails"></param>
        public void CacheBillingTemplates(Collection<BillingTemplateDetails> billingTemplates)
        {
            htbillingTemplates = new Hashtable(billingTemplates.Count);

            foreach (BillingTemplateDetails billingTemplate in billingTemplates)
            {
                htbillingTemplates.Add(billingTemplate.Id, billingTemplate);
            }
        }

        /// <summary>
        /// Update Billing Tier Name in grid
        /// </summary>
        /// <param name="requestorType"></param>
        /// <returns></returns>
        private RequestorTypeDetails UpdateBillingTierName(RequestorTypeDetails requestorType)
        {
            requestorType.HpfBillingTier.Name = GetBillingTierName(requestorType.HpfBillingTier.Id);            
            requestorType.NonHpfBillingTier.Name = GetBillingTierName(requestorType.NonHpfBillingTier.Id);           
                   
            return requestorType;
        }

        /// <summary>
        /// Update Billing Templates Name in grid
        /// </summary>
        /// <param name="requestorType"></param>
        /// <returns></returns>
        private RequestorTypeDetails UpdateBillingTemplateNames(RequestorTypeDetails requestorType)
        {
            requestorType.BillingTemplateValues = string.Empty;

            SortedList sortedBillingTemplates = new SortedList();

            BillingTemplateDetails billingTemplate = null;
            foreach (AssociatedBillingTemplate associatedBillingTemplate in requestorType.AssociatedBillingTemplates)
            {
                billingTemplate = (BillingTemplateDetails)htbillingTemplates[associatedBillingTemplate.BillingTemplateId];
                sortedBillingTemplates.Add(billingTemplate.Name, billingTemplate.Name);
            }

            foreach (string bTemplateName in sortedBillingTemplates.Keys)
            {
                requestorType.BillingTemplateValues += bTemplateName + ", ";
            }
            requestorType.BillingTemplateValues = requestorType.BillingTemplateValues.Trim().TrimEnd(',');
            return requestorType;
        }

        private string GetBillingTierName(long id)
        {
            return (htbillingTiers[id] as BillingTierDetails).Name.Trim();
        }

        /// <summary>
        /// Sets the data into grid
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            ComparableCollection<RequestorTypeDetails> requestorTypes = data as ComparableCollection<RequestorTypeDetails>;
            foreach (RequestorTypeDetails requestorType in requestorTypes)
            {
                UpdateBillingTemplateNames(requestorType);              
                UpdateBillingTierName(requestorType);              
            }
            base.SetData(requestorTypes);
        }

        /// <summary>
        /// Add a new Requestor Type details into grid
        /// </summary>
        /// <param name="data"></param>
        public override void AddRow(object data)
        {
            RequestorTypeDetails requestorTypeDetails = data as RequestorTypeDetails;
            UpdateBillingTemplateNames(requestorTypeDetails);
            UpdateBillingTierName(requestorTypeDetails);
            base.AddRow(requestorTypeDetails);
        }

        /// <summary>
        /// Update Requestor Type details into grid
        /// </summary>
        /// <param name="data"></param>
        public override void UpdateRow(object data)
        {
            RequestorTypeDetails requestorTypeDetails = data as RequestorTypeDetails;
            UpdateBillingTemplateNames(requestorTypeDetails);
            UpdateBillingTierName(requestorTypeDetails);
            base.UpdateRow(requestorTypeDetails);
        }

        #endregion

        #region Properties

        /// <summary>
        /// gets the key of delete confirm message
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.RequestorTypeDeleteMessage; }
        }
        #endregion
    }
}

