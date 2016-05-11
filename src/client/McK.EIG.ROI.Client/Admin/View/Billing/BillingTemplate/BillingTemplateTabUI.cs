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
using System.Collections.Specialized;
using System.Collections.ObjectModel;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTemplate
{
    /// <summary>
    /// Display the BillingTemplateODP UI.
    /// </summary>
    public partial class BillingTemplateTabUI : ROIBaseUI, IAdminBaseTabUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;
        private ItemCheckEventHandler feeTypeDirtyDataHandler;
        private ListDictionary feeTypes;
        private int selectedFeeTypeCount;
        private bool enableSave;


        #endregion

        #region Constructor
        /// <summary>
        /// Initialize UserInterface component of BillingTemplateODP
        /// </summary>
        public BillingTemplateTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
            feeTypeDirtyDataHandler = new ItemCheckEventHandler(MarkDirty);
        }
        
        #endregion
        
        #region Methods

        public void PopulateFeeTypes(ListDictionary feeTypes)
        {
            this.feeTypes = feeTypes;

            Collection<AssociatedFeeType> associatedFeeTypes = new Collection<AssociatedFeeType>();
            AssociatedFeeType associatedFeeType;
            foreach (FeeTypeDetails feeType in feeTypes.Values)
            {
                associatedFeeType           = new AssociatedFeeType();
                associatedFeeType.FeeTypeId = feeType.Id;
                associatedFeeType.Name      = feeType.Name;
                associatedFeeTypes.Add(associatedFeeType);
            }
            feeTypesCheckedListBox.DataSource    = associatedFeeTypes;
            feeTypesCheckedListBox.DisplayMember = "Name";
            feeTypesCheckedListBox.SelectedIndex = -1;
            feeTypesCheckedListBox.Refresh();
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            nameTextBox.Text = string.Empty;
            AssociatedFeeType associatedFeeType;
            for (int index=0; index<=feeTypesCheckedListBox.Items.Count-1; index++)
            {
                associatedFeeType = feeTypesCheckedListBox.Items[index] as AssociatedFeeType;
                associatedFeeType.AssociationId = 0;
                associatedFeeType.RecordVersionId = 0;
                feeTypesCheckedListBox.SetItemChecked(index, false);
            }
            nameTextBox.Focus();
        }

        /// <summary>
        /// Gets the BillingTemplateDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            BillingTemplateDetails billingTemplate = (appendTo == null) ? new BillingTemplateDetails()
                                                                        : appendTo as BillingTemplateDetails;
            billingTemplate.Name = nameTextBox.Text.Trim();
            
            billingTemplate.AssociatedFeeTypes.Clear();
            billingTemplate.FeeTypesValue = string.Empty;
            if (feeTypesCheckedListBox.CheckedItems.Count > 0)
            {
                foreach (AssociatedFeeType feeType in feeTypesCheckedListBox.CheckedItems)
                {
                    billingTemplate.AssociatedFeeTypes.Add(feeType);
                }
            }
            
            return billingTemplate;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            BillingTemplateDetails billingTemplateDetails = data as BillingTemplateDetails;

            if (billingTemplateDetails != null)
            {
                nameTextBox.Text = billingTemplateDetails.Name;
                AssociatedFeeType associatedFeeTypeItem;
                int index;
                
                foreach (AssociatedFeeType associatedFeeType in billingTemplateDetails.AssociatedFeeTypes)
                {
                    index = feeTypesCheckedListBox.FindStringExact((feeTypes[associatedFeeType.FeeTypeId] as FeeTypeDetails).Name);
                    if (index == -1) 
                    { 
                        continue;
                    }

                    
                    feeTypesCheckedListBox.SetItemChecked(index, true);

                    // need to hold the server values of association id and its record version in the combo items
                    // however these will be cleared in ClearControls() which will be called before populating ODP data
                    associatedFeeTypeItem = feeTypesCheckedListBox.Items[index] as AssociatedFeeType;
                    associatedFeeTypeItem.AssociationId = associatedFeeType.AssociationId;
                    associatedFeeTypeItem.RecordVersionId = associatedFeeType.RecordVersionId;
                }
            }
            else
            {
                nameTextBox.ReadOnly = false;
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
                case ROIErrorCodes.BillingTemplateNameAlreadyExist  : return nameTextBox;
                case ROIErrorCodes.BillingTemplateNameEmpty         : return nameTextBox;
                case ROIErrorCodes.BillingTemplateNameMaxLength     : return nameTextBox;
                case ROIErrorCodes.BillingTemplateIsAssociated      : return nameTextBox;
                case ROIErrorCodes.BillingTemplateFeeTypeEmpty      : return feeTypesCheckedListBox; 
            }

            return null;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the BillingTemplateODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged          += dirtyDataHandler;
            feeTypesCheckedListBox.ItemCheck += feeTypeDirtyDataHandler;            
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the BillingTemplateODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged          -= dirtyDataHandler;
            feeTypesCheckedListBox.ItemCheck -= feeTypeDirtyDataHandler;            
        }

        /// <summary>
        /// Occurs when user changes TemplateName or FeeTypes.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
     
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim())
                || selectedFeeTypeCount == 0)
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
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, nameLabel);
            SetLabel(rm, feeTypesLabel);
        }          

        private void feeTypesCheckedListBox_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            if (e.NewValue == CheckState.Checked)
            {
                selectedFeeTypeCount++;                
            }
            else
            {
                selectedFeeTypeCount--;                
            }
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
