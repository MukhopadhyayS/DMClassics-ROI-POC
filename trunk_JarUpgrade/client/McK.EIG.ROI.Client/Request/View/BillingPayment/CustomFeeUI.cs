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
using System.Globalization;
using System.Resources;
using System.Text.RegularExpressions;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Class maintains freeform feetypes
    /// </summary>
    public partial class CustomFeeUI : ROIBaseUI
    {
        #region Fields

        private const string DeleteCustomFeeTitle               = "DeleteCustomFeeDialog.Title";
        private const string DeleteCustomFeeMessage             = "DeleteCustomFeeDialog.Message";
        private const string DeleteCustomFeeOkButton            = "DeleteCustomFeeDialog.OkButton";
        private const string DeleteCustomFeeCancelButton        = "DeleteCustomFeeDialog.CancelButton";
        private const string DeleteCustomFeeOkButtonToolTip     = "DeleteCustomFeeDialog.OkButton";
        private const string DeleteCustomFeeCancelButtonToolTip = "DeleteCustomFeeDialog.CancelButton";

        private EventHandler deleteCustomFeeHandler;
        private EventHandler updateCustomFeeAmountHandler;
        private EventHandler customFeeTypeAmountHandler;        

        private double amount;
        private double taxAmount;
        private double facilityTaxPercentage;
        private bool hasSalesTax;
        private string key;
        private bool amountNoError = true;
        private bool descNoError = true;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public CustomFeeUI()
        {
            InitializeComponent();            
        }

        /// <summary>
        /// Sets the Pane and Context variables
        /// </summary>
        /// <param name="pane"></param>
        /// <param name="updateCustomFeeAmountHandler"></param>
        /// <param name="deleteCustomFeeHandler"></param>
        public CustomFeeUI(IPane pane, EventHandler updateCustomFeeAmountHandler, EventHandler deleteCustomFeeHandler):this()
        {

            SetPane(pane); 
            SetExecutionContext(Pane.Context);

            this.deleteCustomFeeHandler = deleteCustomFeeHandler;
            this.updateCustomFeeAmountHandler = updateCustomFeeAmountHandler;
            customFeeTypeAmountHandler = new EventHandler(Process_FeeTypeAmountChanged);            
            noError = true;
            //Added for sales tax integration
            BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
            facilityTaxPercentage = billingEditor.Request.TaxPercentage;               
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, dollarLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteImg);
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
        /// Sunscribe the events
        /// </summary>
        private void EnableEvents()
        {
            amountTextBox.TextChanged += customFeeTypeAmountHandler;
            customFeeTextBox.TextChanged += new EventHandler(customFeeTextBox_TextChanged);            
			// CR#358017
            customFeeTextBox.LostFocus += new EventHandler(customFeeTextBox_TextChanged);
            customFeeTextBox.Leave += customFeeTypeAmountHandler;            
        }

        private void customFeeTextBox_TextChanged(object sender, EventArgs e)
        {
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
			// CR#358017
            errorProvider.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            if (string.IsNullOrEmpty(customFeeTextBox.Text.Trim()))
            {
                errorProvider.SetError(customFeeTextBox, rm.GetString(ROIErrorCodes.InvalidCustomFeeDescription));
                descNoError = false;
            }
            else
            {
                descNoError = true;
            }
            SetErrorValue();
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        private void DisableEvents()
        {
            amountTextBox.TextChanged -= customFeeTypeAmountHandler;
            customFeeTextBox.TextChanged -= new EventHandler(customFeeTextBox_TextChanged);            
			// CR#358017
            customFeeTextBox.LostFocus -= new EventHandler(customFeeTextBox_TextChanged);
            customFeeTextBox.Leave -= customFeeTypeAmountHandler;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearData()
        {
            errorProvider.Clear();
            customFeeTextBox.Text = string.Empty;
            amountTextBox.Text = Convert.ToString(0, System.Threading.Thread.CurrentThread.CurrentUICulture);            
        }

        /// <summary>
        /// Sets freeform feeType details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearData();
            EnableEvents();

            if (data != null)
            {
                FeeChargeDetails customFeeCharge = (FeeChargeDetails)data;
                hasSalesTax = customFeeCharge.HasSalesTax;
                key = customFeeCharge.Key;
                customFeeTextBox.Text = customFeeCharge.FeeType;
                amountTextBox.Text    = ROIViewUtility.FormattedAmount(customFeeCharge.Amount);
                
            }
            
        }

        /// <summary>
        /// Gets custom fee charge details
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            FeeChargeDetails feeCharge = (data == null) ? new FeeChargeDetails() : (FeeChargeDetails)data;
			// CR#358017
            double amount;
            feeCharge.FeeType     = customFeeTextBox.Text.Trim();
            feeCharge.Amount = double.TryParse(amountTextBox.Text, out amount) ? amount : 0.0;            
            feeCharge.TaxAmount   = Amount * facilityTaxPercentage / 100;
            feeCharge.IsCustomFee = true;
            feeCharge.HasSalesTax = HasSalesTax;
            feeCharge.Key         = customFeeTextBox.Text.Trim() + Key;

            return feeCharge;
        }

        /// <summary>
        /// Deletes freeform feetype
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void deleteImg_Click(object sender, EventArgs e)
        {
            if (ShowConfirmDeleteCustomFeeDialog())
            {
                deleteCustomFeeHandler(this, e);
            }
        }

        /// <summary>
        /// Shows delete confirmation dialog
        /// </summary>
        /// <returns></returns>
        private bool ShowConfirmDeleteCustomFeeDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(DeleteCustomFeeMessage);
            string titleText = rm.GetString(DeleteCustomFeeTitle);
            string okButtonText = rm.GetString(DeleteCustomFeeOkButton);
            string cancelButtonText = rm.GetString(DeleteCustomFeeCancelButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(DeleteCustomFeeOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(DeleteCustomFeeCancelButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// It will restrict user to enter other characters apart from Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void amountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }
        
        /// <summary>
        /// Occurs when user has changed the fee type amount
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_FeeTypeAmountChanged(object sender, EventArgs e)
        {            
            customFee_errorProvider.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString()); 
            if (double.TryParse(amountTextBox.Text, out this.amount) &&
                Regex.IsMatch(Convert.ToString(amount,System.Threading.Thread.CurrentThread.CurrentUICulture), FeeChargeDetails.AmountFormat))
            {                
                updateCustomFeeAmountHandler(sender, e);
                amountNoError = true;
            }
            else
            {                
                customFee_errorProvider.BlinkStyle = ErrorBlinkStyle.NeverBlink;
                customFee_errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidFeeChargeFormat));                
                if (double.TryParse(amountTextBox.Text, out this.amount))
                {
                    if (Convert.ToDouble(amountTextBox.Text, System.Threading.Thread.CurrentThread.CurrentUICulture) < 0)
                    {
                        customFee_errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidFeeCharge));

                    }
                }                
                ((BillingPaymentInfoUI)Pane.View).Validate();
                amountNoError = false;
                SetErrorValue();
            }
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
        }

        private void amountTextBox_Leave(object sender, EventArgs e)
        {
            if (amountNoError)
            {
                amountTextBox.Text = ROIViewUtility.FormattedAmount(amount);
            }
            SetErrorValue();
        }

        private void SetErrorValue()
        {
            if (!amountNoError || !descNoError)
            {
                noError = false;
            }
            else
            {
                noError = true;
            }
        }

        /// <summary>
        /// Recalculate tax based on user select apply tax 
        /// </summary>
        public void CalculateTax(double taxPercentage)
        {
            facilityTaxPercentage = taxPercentage; // If user uncheck apply tax and change the custom fee amount
            taxAmount = Amount * taxPercentage / 100;            
        }        

        #endregion

        #region Properties
        
        /// <summary>
        /// Fee type amount
        /// </summary>
        public double Amount
        {
            get { return amount; }
        }

        public double TaxAmount
        {
            get { return taxAmount; }
        }

        public TextBox FeeTextBox
        {
            get { return customFeeTextBox; }
        }

        private bool noError;
        public bool NoError
        {
            get { return noError; }
        }

        public bool HasSalesTax
        {
            get { return hasSalesTax; }
        }

        public string Key
        {
            get { return key; }
            set { key = value; }
        }

        #endregion       
    }
}
