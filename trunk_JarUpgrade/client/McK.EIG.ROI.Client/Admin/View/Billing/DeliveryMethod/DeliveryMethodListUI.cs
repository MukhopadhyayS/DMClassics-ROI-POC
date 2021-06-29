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
using System.ComponentModel;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Billing.DeliveryMethod
{
    public partial class DeliveryMethodListUI : AdminBaseListUI
    {
        
        #region Fields

        private const string ImageColumn       = "ImageColumn";
        private const string NameColumn        = "Name";
        private const string DescriptionColumn = "Description";
        private const string UrlColumn         = "Url";

        #endregion

        #region Constructor

        public DeliveryMethodListUI()
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

            DataGridViewImageColumn dgvImageColumn = grid.AddImageColumn(ImageColumn, string.Empty, "Image", 75);   
     
            dgvImageColumn.DefaultCellStyle.NullValue = null;

            grid.AddTextBoxColumn(NameColumn, "Name", "Name", 150);
            grid.AddTextBoxColumn(DescriptionColumn, "Description", "Description", 250);
            DataGridViewTextBoxColumn dgvUrlColumn = grid.AddTextBoxColumn(UrlColumn, "Url", "Url", ColumnWidth);

            dgvUrlColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
        }

        /// <summary>
        /// For updating image for default delivery method (Clean up the image if there is any default delivery method and update)
        /// </summary>
        /// <param name="data"></param>
        public override void UpdateRow(object data)
        {
            DeliveryMethodDetails deliveryMethodDetail = (DeliveryMethodDetails)data;

            if (deliveryMethodDetail.IsDefault)
            {
                deliveryMethodDetail.IsDefault = true;
                ComparableCollection<DeliveryMethodDetails> deliveryMethods = (ComparableCollection<DeliveryMethodDetails>)grid.Items;
                UpdateDefaultDeliveryMethod(deliveryMethods);
            }
            base.UpdateRow(deliveryMethodDetail);
        }

        private static void UpdateDefaultDeliveryMethod(ComparableCollection<DeliveryMethodDetails> deliveryMethods)
        {
            foreach (DeliveryMethodDetails deliveryMethodDetail in deliveryMethods)
            {
                deliveryMethodDetail.IsDefault = false;
            }
        }

        /// <summary>
        /// For adding image for default delivery method.
        /// </summary>
        /// <param name="data"></param>
        public override void AddRow(object data)
        {
            DeliveryMethodDetails deliveryMethodDetail = (DeliveryMethodDetails)data;

            if (deliveryMethodDetail.IsDefault)
            {
                ComparableCollection<DeliveryMethodDetails> deliveryMethods = (ComparableCollection<DeliveryMethodDetails>)grid.Items;
                UpdateDefaultDeliveryMethod(deliveryMethods);
            }

            base.AddRow(deliveryMethodDetail);
        }

        /// <summary>
        /// Check whether the data is seed data.
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        protected override bool CanDelete(object data)
        {
            DeliveryMethodDetails selectedMethod = (DeliveryMethodDetails)data;
            return (!(selectedMethod.Id < 0));
        }

        protected override object RefreshEntityData(object data)
        {
            DeliveryMethodDetails deliveryMethod = (DeliveryMethodDetails)data;
            deliveryMethod = ROIAdminController.Instance.GetDeliveryMethod(deliveryMethod.Id);
            return deliveryMethod;
        }

        /// <summary>
        /// Delete the delivery Method
        /// </summary>
        /// <param name="data"></param>
        protected override void DeleteEntity(object data)
        {
            DeliveryMethodDetails deliveryMethodToDelete = (DeliveryMethodDetails)data;
            ROIAdminController.Instance.DeleteDeliveryMethod(deliveryMethodToDelete.Id); 
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

            grid.Columns[ImageColumn].HeaderText = rm.GetString("default.columnHeader");
            grid.Columns[NameColumn].HeaderText = rm.GetString("name.columnHeader");
            grid.Columns[DescriptionColumn].HeaderText = rm.GetString("description.columnHeader");
            grid.Columns[UrlColumn].HeaderText = rm.GetString("url.columnHeader");

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
            DeliveryMethodDetails deliveryMethod = (DeliveryMethodDetails)grid.SelectedRows[0].DataBoundItem;
            if (deliveryMethod.Id > 0)
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

        /// <summary>
        /// Sets the data into datagrid's DataSource object
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            ComparableCollection<DeliveryMethodDetails> list = new ComparableCollection<DeliveryMethodDetails>((IFunctionCollection)data, new DeliveryMethodComparer());
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(DeliveryMethodDetails))["Name"], ListSortDirection.Ascending);
            grid.SetItems(list);
            InitEvents();
            if (grid.Items.Count == 0)
            {
                EnableCreate();
                DisableDelete();
                createButton.Focus();
                AdminEvents.OnMCPEmptyListing(null, null);
            }
            else
            {
                grid.SelectRow(0);
            }
        }

        #endregion

        #region Properties
        
        /// <summary>
        /// gets the key of delete confirm message
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.DeliveryMethodDeleteMessage; }
        }

        #endregion

    }
}
