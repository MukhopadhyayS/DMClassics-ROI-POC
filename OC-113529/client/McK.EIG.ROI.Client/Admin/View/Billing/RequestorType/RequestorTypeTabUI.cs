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
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;


namespace McK.EIG.ROI.Client.Admin.View.Billing.RequestorType
{
    /// <summary>
    /// Display the RequestorTypeODPUI.
    /// </summary>
    public partial class RequestorTypeTabUI : ROIBaseUI, IAdminBaseTabUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;

        private string recordViewName;

        private Hashtable htBillingTemplates;
        private ComparableCollection<AssociatedBillingTemplate> selectedBillingTemplate;
        private bool enableSave;

        private bool canAccessRecordView;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize UserInterface component of RequestorTypeODP
        /// </summary>
        public RequestorTypeTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
            billingTemplateCheckedListBox.ItemCheck += new ItemCheckEventHandler(billingTemplateCheckedListBox_ItemCheck);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Populate Billing Templates value
        /// </summary>
        /// <param name="billingTemplateDetails"></param>
        public void PopulateBillingTemplates(Collection<BillingTemplateDetails> billingTemplates)
        {
            DisableEvents();

            Collection<AssociatedBillingTemplate> associatedBillingTemplates = new Collection<AssociatedBillingTemplate>();            
            AssociatedBillingTemplate associatedBillingTemplate;            
         
            htBillingTemplates = new Hashtable(billingTemplates.Count);

            foreach (BillingTemplateDetails billingTemplate in billingTemplates)
            {
                associatedBillingTemplate = new AssociatedBillingTemplate();                
                associatedBillingTemplate.BillingTemplateId = billingTemplate.Id;
                associatedBillingTemplate.Name = billingTemplate.Name;                
                associatedBillingTemplates.Add(associatedBillingTemplate);           
                htBillingTemplates.Add(billingTemplate.Id, billingTemplate);
            }

            billingTemplateCheckedListBox.Visible = (associatedBillingTemplates.Count > 0);
            billingTemplateLabel.Visible = !(associatedBillingTemplates.Count > 0);

            if (associatedBillingTemplates.Count > 0)
            {
                billingTemplateCheckedListBox.DataSource = associatedBillingTemplates;
                billingTemplateCheckedListBox.DisplayMember = "Name";
                billingTemplateCheckedListBox.ValueMember   = "BillingTemplateId";
                billingTemplateCheckedListBox.SelectedIndex = -1;
                billingTemplateCheckedListBox.Refresh();
            }
            else
            {
                billingTemplateLabel.Text = "No billing templates exist.";
            }


            AssociatedBillingTemplate forSelect = new AssociatedBillingTemplate();

            selectedBillingTemplate = new ComparableCollection<AssociatedBillingTemplate>();
            selectedBillingTemplate.Add(forSelect);

            forSelect.BillingTemplateId = 0;
            forSelect.Name = " " + GetLocalizedString("requestorTypeText." + this.GetType().Name);

			defaultComboBox.Sorted = true;
			
            BindingSource source = new BindingSource();
            source.DataSource = selectedBillingTemplate;
            defaultComboBox.DataSource = source;

            defaultComboBox.DisplayMember = "Name";
            defaultComboBox.ValueMember = "BillingTemplateId";
         
            EnableEvents();
        }

        /// <summary>
        /// Populate Billing Tiers Value
        /// </summary>
        /// <param name="billingTierDetails"></param>
        public void PopulateBillingTiers(Collection<BillingTierDetails> billingTierList)
        {
            DisableEvents();
            
            //Populates Non-HPF Electronic billing tiers
            List<BillingTierDetails> billingTiers = new List<BillingTierDetails>(billingTierList);
            billingTiers.Sort(BillingTierDetails.Sorter);

            BillingTierDetails forSelect = new BillingTierDetails();
            forSelect.Id = 0;
            forSelect.Name = GetLocalizedString("requestorTypeText." + this.GetType().Name);
            billingTiers.Insert(0, forSelect);
            
            nonHpfBillingTierComboBox.DisplayMember = "Name";
            nonHpfBillingTierComboBox.ValueMember   = "Id";
            nonHpfBillingTierComboBox.DataSource    = billingTiers;

            //Populates HPF Electronic billing tiers
            billingTiers = FilterElectronicBillingTiers(billingTierList);
            billingTiers.Sort(BillingTierDetails.Sorter);
            billingTiers.Insert(0, forSelect);

            hpfBillingTierComboBox.DisplayMember = "Name";
            hpfBillingTierComboBox.ValueMember   = "Id";
            hpfBillingTierComboBox.DataSource    = billingTiers;
            
            EnableEvents();
        }

        /// <summary>
        /// Filters the billing tier associated with electronic media type.
        /// </summary>
        /// <param name="billingTierList"></param>
        /// <returns></returns>
        private static List<BillingTierDetails> FilterElectronicBillingTiers(Collection<BillingTierDetails> billingTiers)
        {
            List<BillingTierDetails> billingTierList = new List<BillingTierDetails>(billingTiers);
            return billingTierList.FindAll(delegate(BillingTierDetails item) { return item.MediaTypeName.Equals(ROIConstants.Electronic); });
        }

        /// <summary>
        /// Populate Record Views Value
        /// </summary>
        /// <param name="recordViews"></param>
        public void PopulateRecordViews(Collection<RecordViewDetails> recordViews)
        {
            DisableEvents();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            RecordViewDetails forSelect = new RecordViewDetails();
            forSelect.Name = " " + rm.GetString("requestorTypeText." + this.GetType().Name);
            recordViewComboBox.Items.Add(forSelect.Name.ToString());

            if (recordViews.Count == 0)
            {
                ShowRecordViewDialog();
                return;
            }

            foreach (RecordViewDetails recordViewDetails in recordViews)
            {
                recordViewComboBox.Items.Add(Convert.ToString(recordViewDetails.Name,System.Threading.Thread.CurrentThread.CurrentUICulture));
            }

            canAccessRecordView = true;

            EnableEvents();
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            nameTextBox.Text = string.Empty;
            AssociatedBillingTemplate associatedBillingTemplate;

            for (int index = 0; index <= billingTemplateCheckedListBox.Items.Count - 1; index++)
            {
                associatedBillingTemplate = billingTemplateCheckedListBox.Items[index] as AssociatedBillingTemplate;

                associatedBillingTemplate.IsDefault = false;
                associatedBillingTemplate.AssociationId = 0;
                associatedBillingTemplate.RecordVersionId = 0;

                billingTemplateCheckedListBox.SetItemChecked(index, false);
            }

            recordViewComboBox.Items.Remove(recordViewName);
            if(recordViewComboBox.Items.Count  > 0)
            {
                recordViewComboBox.SelectedIndex = 0;
            }
            hpfBillingTierComboBox.SelectedIndex = 0;
            nonHpfBillingTierComboBox.SelectedIndex = 0;
            salesTaxCheckBox.Checked = false;
            InvoiceCheckBox.Checked = false;
            nameTextBox.Focus();

        }

        /// <summary>
        /// Gets the RequestorTypeDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            RequestorTypeDetails requestorType = (appendTo == null) ? new RequestorTypeDetails()
                                                                    : appendTo as RequestorTypeDetails;
            requestorType.Name = nameTextBox.Text.Trim();

            requestorType.AssociatedBillingTemplates.Clear();
            requestorType.BillingTemplateValues = string.Empty;

            if (billingTemplateCheckedListBox.CheckedItems.Count > 0)
            {
                foreach (AssociatedBillingTemplate billingTemplate in billingTemplateCheckedListBox.CheckedItems)
                {
                    AssociatedBillingTemplate associatedTemplate = defaultComboBox.SelectedItem as AssociatedBillingTemplate;
                    billingTemplate.IsDefault = (billingTemplate == associatedTemplate);
                    requestorType.AssociatedBillingTemplates.Add(billingTemplate);
                }
            }

            RecordViewDetails recordViewDetails = new RecordViewDetails();

            if (recordViewComboBox.SelectedIndex != 0)
            {
                recordViewDetails.Name = recordViewComboBox.SelectedItem.ToString();
            }
            else
            {
                recordViewDetails.Name = string.Empty;
            }

            requestorType.RecordViewDetails = recordViewDetails;
            requestorType.HpfBillingTier = (BillingTierDetails)hpfBillingTierComboBox.SelectedItem;
            requestorType.NonHpfBillingTier = (BillingTierDetails)nonHpfBillingTierComboBox.SelectedItem;
            requestorType.SalesTax = (salesTaxCheckBox.Checked) ? ROIConstants.Yes : ROIConstants.No;
            requestorType.InvoiceOptional=(InvoiceCheckBox.Checked)? ROIConstants.Yes : ROIConstants.No;

            return requestorType;
        }
       
        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            recordViewComboBox.Enabled = canAccessRecordView;

            //Clear default billing template list
            selectedBillingTemplate.Clear();
            if (defaultComboBox.Items.Count == 0)
            {
                AssociatedBillingTemplate forSelect = new AssociatedBillingTemplate();
                forSelect.BillingTemplateId = 0;
                forSelect.Name = " " + GetLocalizedString("requestorTypeText." + this.GetType().Name);
                selectedBillingTemplate.Insert(0, forSelect);
            }

            RequestorTypeDetails requestorTypeDetails = data as RequestorTypeDetails;

            if (requestorTypeDetails != null)
            {
                AssociatedBillingTemplate associatedBillingTemplateItem;
                int index;

                nameTextBox.Text     = requestorTypeDetails.Name;
                nameTextBox.Enabled  = requestorTypeDetails.Id >= 0;
                recordViewComboBox.Items.Remove(recordViewName); 
                index = recordViewComboBox.FindStringExact(requestorTypeDetails.RecordViewDetails.Name);

                if (index == -1)
                {
                    recordViewComboBox.Items.Add(requestorTypeDetails.RecordViewDetails.Name);
                    recordViewName = requestorTypeDetails.RecordViewDetails.Name;
                    recordViewComboBox.SelectedItem = requestorTypeDetails.RecordViewDetails.Name;
                }
                else
                {
                    recordViewComboBox.SelectedIndex = index;
                }

                hpfBillingTierComboBox.SelectedValue    = requestorTypeDetails.HpfBillingTier.Id;
                nonHpfBillingTierComboBox.SelectedValue = requestorTypeDetails.NonHpfBillingTier.Id;
                salesTaxCheckBox.Checked = !(string.IsNullOrEmpty(requestorTypeDetails.SalesTax)) 
                                             ? (requestorTypeDetails.SalesTax.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes")) : false;
                InvoiceCheckBox.Checked = !(string.IsNullOrEmpty(requestorTypeDetails.InvoiceOptional))
                                            ? (requestorTypeDetails.InvoiceOptional.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes")) : false;

                BillingTemplateDetails billingTemplateDetails;
                string defaultItem = string.Empty;
                foreach (AssociatedBillingTemplate associatedBillingTemplate in requestorTypeDetails.AssociatedBillingTemplates)
                {
                    billingTemplateDetails = htBillingTemplates[associatedBillingTemplate.BillingTemplateId] as BillingTemplateDetails;

                    index = billingTemplateCheckedListBox.FindStringExact(billingTemplateDetails.Name);
                    if (index == -1)
                    {
                        continue;
                    }

                    billingTemplateCheckedListBox.SetItemChecked(index, true);
                    billingTemplateCheckedListBox.SetSelected(index, true);

                    // need to hold the server values of association id and its record version in the combo items
                    // however these will be cleared in ClearControls() which will be called before populating ODP data
                    associatedBillingTemplateItem = billingTemplateCheckedListBox.Items[index] as AssociatedBillingTemplate;

                    associatedBillingTemplateItem.AssociationId = associatedBillingTemplate.AssociationId;
                    associatedBillingTemplateItem.RecordVersionId = associatedBillingTemplate.RecordVersionId;
                    associatedBillingTemplateItem.IsDefault = associatedBillingTemplate.IsDefault;

                    if (associatedBillingTemplate.IsDefault)
                    {
                        defaultItem = billingTemplateDetails.Name;
                    }
                }

                index = defaultComboBox.FindStringExact(defaultItem);
                if (index > 0)
                {
                    defaultComboBox.SelectedIndex = index;
                }

                if (requestorTypeDetails.Id >= 0)
                {
                    nameTextBox.Focus();
                }

                defaultComboBox.Enabled = (billingTemplateCheckedListBox.CheckedItems.Count > 0);
            }
            else
            {
                nameTextBox.Enabled = true;
            }
            EnableEvents();
        }

        /// <summary>
        /// Returns the respective control for the given error code.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.RequestorTypeNameAlreadyExist : //fall through
                case ROIErrorCodes.RequestorTypeSeedData         : //fall through
                case ROIErrorCodes.RequestorTypeNameEmpty        : //fall through
                case ROIErrorCodes.RequestorTypeNameMaxLength    : return nameTextBox;

                case ROIErrorCodes.RequestorTypeHpfBillingTierEmpty    : return hpfBillingTierComboBox;
                case ROIErrorCodes.RequestorTypeNonHpfBillingTierEmpty : return nonHpfBillingTierComboBox;

                case ROIErrorCodes.RecordViewNameEmpty : return recordViewComboBox;
            }

            return null;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the RequestorTypeODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();

            nameTextBox.TextChanged                        += dirtyDataHandler;
            recordViewComboBox.SelectedIndexChanged        += dirtyDataHandler;
            hpfBillingTierComboBox.SelectedIndexChanged    += dirtyDataHandler;
            nonHpfBillingTierComboBox.SelectedIndexChanged += dirtyDataHandler;
            billingTemplateCheckedListBox.Click            += dirtyDataHandler;
            defaultComboBox.SelectedIndexChanged           += dirtyDataHandler;
            salesTaxCheckBox.CheckedChanged                += dirtyDataHandler;
            InvoiceCheckBox.CheckedChanged                 += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the RequestorTypeODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged                        -= dirtyDataHandler;
            recordViewComboBox.SelectedIndexChanged        -= dirtyDataHandler;
            hpfBillingTierComboBox.SelectedIndexChanged    -= dirtyDataHandler;
            nonHpfBillingTierComboBox.SelectedIndexChanged -= dirtyDataHandler;
            billingTemplateCheckedListBox.Click            -= dirtyDataHandler;
            defaultComboBox.SelectedIndexChanged           -= dirtyDataHandler;
            salesTaxCheckBox.CheckedChanged                -= dirtyDataHandler;
            InvoiceCheckBox.CheckedChanged                 -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when user changes any RequestorType data
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim()) ||
                recordViewComboBox.SelectedIndex == 0 ||
                hpfBillingTierComboBox.SelectedIndex == 0 ||
                nonHpfBillingTierComboBox.SelectedIndex == 0)
            {
                enableSave = false;
            }
            else
            {
                enableSave = true;
            }

            (Pane as AdminBaseODP).MarkDirty(sender, e);
        }

        /// <summary>
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == assignLabel || control == defaultLabel)
            {
                return base.GetLocalizeKey(control);
            }
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, nameLabel);
            SetLabel(rm, recordViewLabel);
            SetLabel(rm, hpfBillingTierLabel);
            SetLabel(rm, nonHpfBillingTierLabel);
            SetLabel(rm, assignLabel);
            SetLabel(rm, defaultLabel);
            SetLabel(rm, billingTemplateGroupBox);
            SetLabel(rm, salesTaxLabel);
            SetLabel(rm, InvoiceLabel);
        }

        private void billingTemplateCheckedListBox_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            AssociatedBillingTemplate associatedBillingTemplate = (AssociatedBillingTemplate)billingTemplateCheckedListBox.Items[e.Index];
            AssociatedBillingTemplate defaultTemplate = defaultComboBox.SelectedItem as AssociatedBillingTemplate;

            if (e.NewValue == CheckState.Checked)
            {
                if (!selectedBillingTemplate.Contains(associatedBillingTemplate))
                {
                    selectedBillingTemplate.Add(associatedBillingTemplate);
                }
            }
            else
            {
                if (defaultComboBox.SelectedItem == associatedBillingTemplate)
                {
                    defaultComboBox.SelectedIndex = 0;
                }
                selectedBillingTemplate.Remove(associatedBillingTemplate);
            }

            defaultComboBox.SelectedItem = defaultTemplate;
            defaultComboBox.Enabled = (selectedBillingTemplate.Count > 1);
        }
        
        private string GetLocalizedString(string key)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string pleaseSelect = rm.GetString(key);
            return pleaseSelect;
        }

        private bool ShowRecordViewDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(ROIConstants.RecordViewMessage);
            string titleText = rm.GetString(ROIConstants.RecordViewTitle);
            string okButtonText = rm.GetString(ROIConstants.RecordViewOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(ROIConstants.RecordViewOkButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets enable save value.
        /// </summary>
        public bool EnableSave
        {
            get { return enableSave; }
        }

        #endregion    
    }
}
