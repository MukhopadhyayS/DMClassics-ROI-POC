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
using System.Drawing;
using System.Resources;
using System.Windows.Forms;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Billing.SalesTaxPerFacility
{
    /// <summary>
    /// The class represent the SalesTaxPerFacility ListUI.
    /// </summary>
    public partial class SalesTaxPerFacilityListUI : AdminBaseListUI
    {        
        #region Fields

        private const string ImageColumn       = "ImageColumn";
        private const string FacilityColumn    = "Facility";
        private const string TaxColumn         = "Tax";
        private const string DescriptionColumn = "Description";
        

        #endregion

        #region Constructor

        public SalesTaxPerFacilityListUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the datagrid.
        /// </summary>
        protected override void InitGrid()
        {
            //Add columns to gridview.
            DataGridViewImageColumn dgvImageColumn = grid.AddImageColumn(ImageColumn, string.Empty, "Image", 75);

            dgvImageColumn.DefaultCellStyle.NullValue = null;

            grid.AddTextBoxColumn(FacilityColumn, "Facility", "FacilityName", 150);
            DataGridViewTextBoxColumn dgvTaxColumn = grid.AddTextBoxColumn(TaxColumn, "Tax Percentage", "TaxPercentage", 150);
            dgvTaxColumn.DefaultCellStyle.Format = "n2";
            
            DataGridViewTextBoxColumn dgvDescriptionColumn = grid.AddTextBoxColumn(DescriptionColumn, "Description", "Description", ColumnWidth);

            dgvDescriptionColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDescriptionColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }

           
        /// <summary>
        /// Method add the configured sales tax rate
        /// </summary>
        /// <param name="data"></param>
        public override void AddRow(object data)
        {
            TaxPerFacilityDetails taxPerFacilityDetails = (TaxPerFacilityDetails)data;

            if (taxPerFacilityDetails.IsDefault)
            {
                ComparableCollection<TaxPerFacilityDetails> gridDetails = (ComparableCollection<TaxPerFacilityDetails>)grid.Items;
                UpdateTaxPerFacilityDefaultValue(gridDetails);
            }
            base.AddRow(taxPerFacilityDetails);
        }

        /// <summary>
        /// Method update configured tax rate value.
        /// </summary>
        /// <param name="data"></param>
        public override void UpdateRow(object data)
        {
            TaxPerFacilityDetails taxPerFacilityDetails = (TaxPerFacilityDetails)data;

            if (taxPerFacilityDetails.IsDefault)
            {                
                ComparableCollection<TaxPerFacilityDetails> taxPerFacilities = (ComparableCollection<TaxPerFacilityDetails>)grid.Items;
                UpdateTaxPerFacilityDefaultValue(taxPerFacilities);
            }
            base.UpdateRow(taxPerFacilityDetails);
        }

        /// <summary>
        /// To update the tax rate default value.
        /// </summary>
        /// <param name="gridTaxPerFacilities"></param>        
        private static void UpdateTaxPerFacilityDefaultValue(ComparableCollection<TaxPerFacilityDetails> taxPerFacilities)
        {
            foreach (TaxPerFacilityDetails taxPerFacilityDetails in taxPerFacilities)
            {
                taxPerFacilityDetails.IsDefault = false;                
            }
        }

        /// <summary>
        /// Delete the configured tax rate.
        /// </summary>
        /// <param name="data"></param>
        protected override void DeleteEntity(object data)
        {
            TaxPerFacilityDetails taxPerFacilityDetails = (TaxPerFacilityDetails)data;
            BillingAdminController.Instance.DeleteTaxPerFacility(taxPerFacilityDetails.Id);
        }

        /// <summary>
        /// Retrieve details of the selected facility in the grid
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        protected override object RefreshEntityData(object data)
        {
            TaxPerFacilityDetails taxDetails = (TaxPerFacilityDetails)data;            
            taxDetails = BillingAdminController.Instance.RetrieveTaxPerFacility(taxDetails.Id);
            //FacilityDetails facility = FacilityDetails.GetFacility(taxDetails.FacilityCode, FacilityType.Hpf);
            //taxDetails.FacilityName = facility != null ? facility.Name : string.Empty;
            return taxDetails;            
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
            grid.Columns[ImageColumn].HeaderText       = rm.GetString("default.columnHeader");
            grid.Columns[FacilityColumn].HeaderText    = rm.GetString("salesTaxFacility.columnHeader");
            grid.Columns[TaxColumn].HeaderText         = rm.GetString("TaxPercentage.columnHeader"); 
            grid.Columns[DescriptionColumn].HeaderText = rm.GetString("description.columnHeader");
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteButton);
            SetTooltip(rm, toolTip, createButton);            
        }

        /// <summary>
        /// Gets the LocalizeKey of UI controls to show tooltip message.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }


        public override void EnableCreate()
        {
            base.EnableCreate();            
        }

        public override void DisableCreate()
        {
            base.DisableCreate();            
        }
        
        #endregion

        #region Properties
        
        /// <summary>
        /// Gets the key of delete confirm message
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.SalesTaxFacilityDeleteMessage; }
        }

        #endregion
    }
}
