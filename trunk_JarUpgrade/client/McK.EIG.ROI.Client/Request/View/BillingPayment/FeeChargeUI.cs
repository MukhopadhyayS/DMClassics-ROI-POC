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
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Class maintains feecharge details
    /// </summary>
    public partial class FeeChargeUI : ROIBaseUI
    {
        #region Fields

        private double billingTypeFeeCharge;
        private double customFeeCharge;

        private bool isFeeTypeScroll;
        private bool isCustomFeeTypeScroll;

        private Hashtable htFeeTypes;
        private Hashtable adminFeeTypeValues;

        private BillingTemplateDetails defaultBillingTemplate;

        private EventHandler billingTypeSelectionChangedHandler;

        private Collection<BillingTemplateDetails> requestorTypeBillingTypes;
        
        private double facilityTaxPercentage;

        int counter = 1;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public FeeChargeUI()
        {
            InitializeComponent();
            createCustomeFeeButton.Enabled = true;
            billingTypeComboBox.Enabled = true;
            billingTypeSelectionChangedHandler = new EventHandler(billingTypeCombo_SelectedIndexChanged);            
        }

        #endregion

        #region Methods

        /// <summary>
        /// Subscribe the events
        /// </summary>
        private void EnableEvents()
        {
            billingTypeComboBox.SelectedIndexChanged += billingTypeSelectionChangedHandler;
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        private void DisableEvents()
        {
            billingTypeComboBox.SelectedIndexChanged -= billingTypeSelectionChangedHandler;
        }

        /// <summary>
        /// Popualtes requestor billing types in combobox
        /// </summary>
        /// <param name="requestorTypeBillingTypes"></param>
        /// <param name="feeTypes"></param>
        /// <param name="defaultBillingTemplate"></param>
        public void PrePopulate(Collection<BillingTemplateDetails> requestorTypeBillingTypes, 
                                Collection<FeeTypeDetails> feeTypes, BillingTemplateDetails defaultBillingTemplate)
        {

            DisableEvents();
            this.defaultBillingTemplate = defaultBillingTemplate;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            BillingTemplateDetails forSelect = new BillingTemplateDetails();
            forSelect.Id = 0;
            forSelect.Name = rm.GetString(ROIConstants.FeeTypeCombo);
            requestorTypeBillingTypes.Insert(0, forSelect);

            this.requestorTypeBillingTypes = requestorTypeBillingTypes;
            billingTypeComboBox.DataSource = requestorTypeBillingTypes;
            billingTypeComboBox.DisplayMember = "Name";
            billingTypeComboBox.ValueMember = "Id";

            htFeeTypes = new Hashtable(feeTypes.Count);
            adminFeeTypeValues = new Hashtable(feeTypes.Count);
            foreach (FeeTypeDetails feeType in feeTypes)
            {
                htFeeTypes.Add(feeType.Id, feeType);
                adminFeeTypeValues.Add(feeType.Name + ROIConstants.SalesTaxStdFeeKey, feeType.SalesTax);
            }
            EnableEvents();
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearData()
        {
            stdFeeChargesFlowLayoutPanel.Controls.Clear();
            customFeeChargesFlowLayoutPanel.Controls.Clear();
            billingTypeFeeCharge = 0.00;
            customFeeCharge = 0.00;
            billingTypeComboBox.SelectedIndex = 0;
            SetTotalCustomFeeAmount();
            SetTotalStdFeeAmount();
        }
        
        /// <summary>
        /// Sets feecharge details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data, bool isPopulateFeeCharges, bool hasDraftRelease)
        {
            ReleaseDetails release = (ReleaseDetails)data;            
            ClearData();

            if (!hasDraftRelease && release.FeeCharges.Count == 0)
            {
                BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
                if (!(billingEditor.Request.ReleaseCount > 0 ? true : false))
                {
                    billingTypeComboBox.SelectedValue = (defaultBillingTemplate == null) ? 0 : defaultBillingTemplate.Id;
                    PopulateBillingTypeFeeCharges();
                }
            }
            else
            {
                DisableEvents();
                FeeTypeUI feeTypeUI;
                CustomFeeUI customFeeUI;
                wholeUITableLayoutPanel.SuspendLayout();
                foreach (FeeChargeDetails feeCharge in release.FeeCharges)
                {                    
                    if (!feeCharge.IsCustomFee)
                    {
                        feeTypeUI = CreateFeeType();
                        stdFeeChargesFlowLayoutPanel.Controls.Add(feeTypeUI);
                        feeTypeUI.SetData(feeCharge);
                    }
                    else
                    {
                        feeCharge.Key = ROIConstants.SalesTaxCusFeeKey + counter;
                        customFeeUI = CreateCustomFee();
                        customFeeChargesFlowLayoutPanel.Controls.Add(customFeeUI);
                        customFeeUI.SetData(feeCharge);
                        counter = counter + 1;
                    }
                }
                wholeUITableLayoutPanel.ResumeLayout();

                long releaseBillingType = Convert.ToInt64(release.BillingType, System.Threading.Thread.CurrentThread.CurrentUICulture);
                List<BillingTemplateDetails> billingTypes = new List<BillingTemplateDetails>(requestorTypeBillingTypes);
                billingTypes = billingTypes.FindAll( delegate(BillingTemplateDetails billingType) { return (billingType.Id == releaseBillingType); });

                if (billingTypes.Count > 0)
                {
                    billingTypeComboBox.SelectedValue = releaseBillingType;
                }
                else
                {
                    billingTypeComboBox.SelectedValue = (defaultBillingTemplate == null) ? 0 : defaultBillingTemplate.Id;
                }

                // To prevent the values coming from admin for fee types to show in UI after saving the fee types
                if (isPopulateFeeCharges)
                {
                    PopulateBillingTypeFeeCharges();
                }

                EnableEvents();
            }
            // CR#364045 change the width of the panel when vertical scroll exist
            if (stdFeeChargesFlowLayoutPanel.VerticalScroll.Visible && !isFeeTypeScroll)
            {
                isFeeTypeScroll = true;
                ResizeFeeTypeContents();                
            }
            else
            {
                stdFeeTypeHeaderTableLayoutPanel.ColumnStyles[1].Width = 88;
                isFeeTypeScroll = false;                
            }
            stdFeeTypeHeaderTableLayoutPanel.HorizontalScroll.Visible = false;

            if (customFeeChargesFlowLayoutPanel.VerticalScroll.Visible)
            {
                ResizeCustomFeeContents(82, 68, 21, true);                
            }
            else
            {
                ResizeCustomFeeContents(82, 47, 10, false);                
            }            
        }


        private void ResizeFeeTypeContents()
        {
            stdFeeTypeHeaderTableLayoutPanel.ColumnStyles[1].Width = 106;
            for (int i = 0; i < stdFeeChargesFlowLayoutPanel.Controls.Count; i++)
            {
                stdFeeChargesFlowLayoutPanel.Controls[i].Width = stdFeeChargesFlowLayoutPanel.Width - 18;
            }
            isFeeTypeScroll = false;
        }

        /// <summary>
        /// Gets feecharge details
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {

            ReleaseDetails release = (ReleaseDetails)data;
            release.FeeCharges.Clear();

            foreach (FeeTypeUI feeTypeUI in stdFeeChargesFlowLayoutPanel.Controls)
            {
                release.FeeCharges.Add((FeeChargeDetails)feeTypeUI.GetData(null));
            }

            foreach (CustomFeeUI customFeeUI in customFeeChargesFlowLayoutPanel.Controls)
            {
                release.FeeCharges.Add((FeeChargeDetails)customFeeUI.GetData(null));
            }

            release.BillingType = billingTypeComboBox.SelectedValue.ToString();
            release.FeeChargeTotal = FeeCharge;

            return release;
        }

        /// <summary>
        /// Apply localiztion for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, feeChargesGroupBox);
            SetLabel(rm, billingTypeLabel);
            SetLabel(rm, feeTypesColumnLabel);
            SetLabel(rm, amountColumnLabel);
            SetLabel(rm, customFeeColumnLabel);
            SetLabel(rm, customAmountColumnLabel);
            SetLabel(rm, createCustomeFeeButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, createCustomeFeeButton);
            
        }

        /// <summary>
        /// Gets the localized key of UI control
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Creates standard feetype UI
        /// </summary>
        /// <returns></returns>
        private FeeTypeUI CreateFeeType()
        {
            EventHandler updateFeeTypeAmountHandler = new EventHandler(Process_UpdateStdFeeTypeAmount);

            FeeTypeUI feeTypeUI = new FeeTypeUI(Pane, updateFeeTypeAmountHandler);

            feeTypeUI.Dock = DockStyle.Top;
            feeTypeUI.Margin = new Padding(0);
            feeTypeUI.Width = stdFeeChargesFlowLayoutPanel.Width;
            feeTypeUI.Localize();

            return feeTypeUI;
        }

        /// <summary>
        /// Update standard fee type amount
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateStdFeeTypeAmount(object sender, EventArgs e)
        {
            billingTypeFeeCharge = 0.00;

            foreach (FeeTypeUI feeTypeUI in stdFeeChargesFlowLayoutPanel.Controls)
            {
                billingTypeFeeCharge += feeTypeUI.Amount;                
            }

            SetTotalStdFeeAmount();
            RequestEvents.OnReleaseCostUpdated(Pane, null);


        }

        private void SetTotalStdFeeAmount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            double totalStdFeeAmount = billingTypeFeeCharge;
            totalStdFeeAmountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                       rm.GetString(totalStdFeeAmountLabel.Name + "." + GetType().Name),
                                                       totalStdFeeAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture));
        }

        /// <summary>
        /// Occurs when selection has been changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void billingTypeCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            ((BillingPaymentInfoUI)Pane.View).ChangeGridItemChangedStatus();
            PopulateBillingTypeFeeCharges();
            // CR#364045 change the width of the panel when vertical scroll exist
            if (stdFeeChargesFlowLayoutPanel.VerticalScroll.Visible && !isFeeTypeScroll)
            {
                isFeeTypeScroll = true;
                ResizeFeeTypeContents();
            }
            else
            {
                stdFeeTypeHeaderTableLayoutPanel.ColumnStyles[1].Width = 88;
                isFeeTypeScroll = false;
            }
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
        }

        /// <summary>
        /// Populates fee charge based on billing type selected
        /// </summary>
        private void PopulateBillingTypeFeeCharges()
        {
            BillingTemplateDetails billingTemplate = (BillingTemplateDetails)billingTypeComboBox.SelectedItem;
            FeeTypeDetails feeType;
            FeeChargeDetails feeCharge;
            FeeTypeUI feeTypeUI;
            stdFeeChargesFlowLayoutPanel.Controls.Clear();
            if (billingTemplate.Id == 0)
            {
                billingTypeFeeCharge = 0.00;                
                SetTotalStdFeeAmount();
                RequestEvents.OnReleaseCostChanged(Pane, null);
                RequestEvents.OnChargeAmountChanged(Pane, null);
            }
            //Added for sales tax integration
            BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
            facilityTaxPercentage = billingEditor.Request.TaxPercentage;
            bool isApplyTaxChecked = billingEditor.Request.HasSalesTax;
            foreach (AssociatedFeeType assoicatedFeeType in billingTemplate.AssociatedFeeTypes)
            {
                if (htFeeTypes.ContainsKey(assoicatedFeeType.FeeTypeId))
                {
                    feeType = (FeeTypeDetails)htFeeTypes[assoicatedFeeType.FeeTypeId];
                    feeCharge = new FeeChargeDetails();
                    feeCharge.Amount = feeType.Amount;
                    feeCharge.FeeType = feeType.Name;
                    
                    if (feeType.SalesTax.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes") && isApplyTaxChecked)
                    {
                        feeCharge.HasSalesTax = true;
                        feeCharge.TaxAmount = feeCharge.Amount * facilityTaxPercentage / 100;
                    }
                    else
                    {
                        feeCharge.HasSalesTax = false;
                    }

                    feeTypeUI = CreateFeeType();
                    stdFeeChargesFlowLayoutPanel.Controls.Add(feeTypeUI);
                    feeTypeUI.SetData(feeCharge);
                }
            }            
        }

        /// <summary>
        /// Create custom fee type
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void createCustomeFeeButton_Click(object sender, EventArgs e)
        {
            wholeUITableLayoutPanel.SuspendLayout();
            CustomFeeUI customFeeUI = CreateCustomFee();
            customFeeChargesFlowLayoutPanel.Controls.Add(customFeeUI);
            FeeChargeDetails feeChargeDetail = new FeeChargeDetails();
            feeChargeDetail.Key = ROIConstants.SalesTaxCusFeeKey + counter;
            counter = counter + 1;
            customFeeUI.SetData(feeChargeDetail);
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
            wholeUITableLayoutPanel.ResumeLayout();           
            customFeeUI.FeeTextBox.Focus();

            // CR#364045 change the width of the panel when vertical scroll exist
            if (customFeeChargesFlowLayoutPanel.VerticalScroll.Visible)
            {
                ResizeCustomFeeContents(82, 68, 21, true);               
            }
            else
            {
                ResizeCustomFeeContents(82, 47, 10, false);                
            }
        }

        private void ResizeCustomFeeContents(int amountWidth, int deleteImgWidth, int customFeewidth, bool isFeeType)
        {
            customFeeHeaderTableLayoutPanel.ColumnStyles[1].Width = amountWidth;
            customFeeHeaderTableLayoutPanel.ColumnStyles[2].Width = deleteImgWidth;

            for (int i = 0; i < customFeeChargesFlowLayoutPanel.Controls.Count; i++)
            {
                if (isFeeType)
                {
                    customFeeChargesFlowLayoutPanel.Controls[i].Width = customFeeChargesFlowLayoutPanel.Width - customFeewidth;
                }
                else
                {
                    customFeeChargesFlowLayoutPanel.Controls[i].Width = customFeeChargesFlowLayoutPanel.Width;
                }
            }
            customFeeChargesFlowLayoutPanel.HorizontalScroll.Visible = false;
        }

        /// <summary>
        /// Create custom fee type
        /// </summary>
        /// <returns></returns>
        private CustomFeeUI CreateCustomFee()
        {
            EventHandler updateFeeTypeAmountHandler = new EventHandler(Process_UpdateCustomFeeTypeAmount);
            EventHandler deleteCustomFeeHandler = new EventHandler(Process_DeleteCustomFee); 

            CustomFeeUI customFeesUI = new CustomFeeUI(Pane, updateFeeTypeAmountHandler, deleteCustomFeeHandler);            

            customFeesUI.Dock = DockStyle.Top;
            customFeesUI.Margin = new Padding(0);
            if (!isCustomFeeTypeScroll)
            {
                customFeesUI.Width = customFeeTableLayoutPanel.Width;
            }
            else
            {
                customFeesUI.Width = customFeeTableLayoutPanel.Width - 20;
            }
            customFeesUI.Localize();

            return customFeesUI;
        }

        /// <summary>
        /// Update custom fee type amount changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateCustomFeeTypeAmount(object sender, EventArgs e)
        {
            customFeeCharge = 0.00;

            foreach (CustomFeeUI customFeeUI in customFeeChargesFlowLayoutPanel.Controls)
            {
                customFeeCharge += customFeeUI.Amount;                
            }

            SetTotalCustomFeeAmount();
            RequestEvents.OnReleaseCostUpdated(Pane,null);
            //RequestEvents.OnReleaseCostChanged(Pane, null);
            //RequestEvents.OnChargeAmountChanged(Pane, null);
        }

        private void SetTotalCustomFeeAmount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            double totalCustomFeeAmount = customFeeCharge;
            totalCustomFeeAmountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                       rm.GetString(totalCustomFeeAmountLabel.Name + "." + GetType().Name),
                                                       totalCustomFeeAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture));  
        }

        /// <summary>
        /// Recalculate tax based on user select apply tax 
        /// </summary>
        public void CalculateTax(double taxPercentage)
        {
            foreach (FeeTypeUI feeTypeUI in stdFeeChargesFlowLayoutPanel.Controls)
            {
                feeTypeUI.CalculateTax(taxPercentage);
            }
            foreach (CustomFeeUI customFeeUI in customFeeChargesFlowLayoutPanel.Controls)
            {
                customFeeUI.CalculateTax(taxPercentage);
            }
            Process_UpdateStdFeeTypeAmount(null, null);
            Process_UpdateCustomFeeTypeAmount(null, null);
        }

        /// <summary>
        /// Deletes custom fee type
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_DeleteCustomFee(object sender, EventArgs e)
        {
            CustomFeeUI selectedCustomFeesUI = (CustomFeeUI)sender;
            customFeeChargesFlowLayoutPanel.Controls.Remove(selectedCustomFeesUI);

            Process_UpdateCustomFeeTypeAmount(sender, e);
            RequestEvents.OnReleaseCostChanged(Pane, null);            
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
            // CR#364045 change the width of the panel when vertical scroll exist
            if (customFeeChargesFlowLayoutPanel.VerticalScroll.Visible)
            {
                ResizeCustomFeeContents(81, 68, 21, true);                
            }
            else
            {
                ResizeCustomFeeContents(82, 47, 10, false);                
            }
        }

        public void DisableFeeCharges()
        {
            billingTypeComboBox.Enabled = false;
            foreach (FeeTypeUI stdFeeTypeUI in stdFeeChargesFlowLayoutPanel.Controls)
            {
                stdFeeTypeUI.Enabled = false;
            }

            createCustomeFeeButton.Enabled = false;
            foreach (CustomFeeUI customFeeUI in customFeeChargesFlowLayoutPanel.Controls)
            {
                customFeeUI.Enabled = false;
            }
        }


        #endregion

        #region Properties

        ///// <summary>
        ///// Fee Charge - Sum of standard fee charge and custom fee charge
        ///// </summary>
        //public double FeeCharge
        //{
        //    get { return billingTypeFeeCharge + billingTypeFeeTaxCharge + customFeeCharge + customFeeTaxCharge; }
        //}

        /// <summary>
        /// Fee Charge - Sum of standard fee charge and custom fee charge
        /// </summary>
        public double FeeCharge
        {
            get { return billingTypeFeeCharge + customFeeCharge; }
        }

        public double BillingTypeFeeCharge
        {
            get { return billingTypeFeeCharge; }
        } 

        public double CustomFeeCharge
        {
            get { return customFeeCharge; }
        }

        public bool NoError
        {
            get
            {
                foreach (CustomFeeUI customFeeUI in customFeeChargesFlowLayoutPanel.Controls)
                {
                    if (!customFeeUI.NoError)
                    {
                        return false;
                    }
                }

                foreach (FeeTypeUI feeTypeUI in stdFeeChargesFlowLayoutPanel.Controls)
                {
                    if (!feeTypeUI.NoError)
                    {
                        return false;
                    }
                }
                return true;
            }
        }

        public Hashtable AdminFeeTypeValues
        {
            get { return adminFeeTypeValues; }
        }

        #endregion
    }
}
