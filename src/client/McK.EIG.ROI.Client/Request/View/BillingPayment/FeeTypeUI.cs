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
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Text.RegularExpressions;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Class maintains standard fee types
    /// </summary>
    public partial class FeeTypeUI : ROIBaseUI
    {
        #region Fields
        
        private EventHandler updateFeeTypeAmountHandler;
        private EventHandler feeTypeAmountHandler;

        private double amount;
        private double taxAmount;
        private double facilityTaxPercentage;
        private bool hasSalesTax;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public FeeTypeUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets Pane and Context variables
        /// </summary>
        /// <param name="pane"></param>
        /// <param name="updateFeeTypeAmountHandler"></param>
        public FeeTypeUI(IPane pane,EventHandler updateFeeTypeAmountHandler) : this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);

            this.updateFeeTypeAmountHandler = updateFeeTypeAmountHandler;
            feeTypeAmountHandler = new EventHandler(Process_FeeTypeAmountChanged);
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
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        private void EnableEvents()
        {
            amountTextBox.TextChanged += feeTypeAmountHandler;
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        private void DisableEvents()
        {
            amountTextBox.TextChanged -= feeTypeAmountHandler;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearData()
        {
            errorProvider.Clear();
            amountTextBox.Text = string.Empty;
            amountTextBox.Text = Convert.ToString(0, System.Threading.Thread.CurrentThread.CurrentUICulture);            
        }

        /// <summary>
        /// Sets the standard the feetype details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearData();
            EnableEvents();

            if (data != null)
            {
                FeeChargeDetails feeCharge = (FeeChargeDetails)data;
                this.hasSalesTax = feeCharge.HasSalesTax;                
                feeTypeLabel.Text = feeCharge.FeeType;
                toolTip.SetToolTip(feeTypeLabel, feeTypeLabel.Text);
                amountTextBox.Text = ROIViewUtility.FormattedAmount(feeCharge.Amount);                
            }
        }

        /// <summary>
        /// Updates std fee type amount
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_FeeTypeAmountChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
            //if (amountTextBox.Text.Length == 0) return;
            if (double.TryParse(amountTextBox.Text, out this.amount) &&
                Regex.IsMatch(Convert.ToString(amount, System.Threading.Thread.CurrentThread.CurrentUICulture), FeeChargeDetails.AmountFormat))
            {                
                //if (this.hasSalesTax != null && this.hasSalesTax.ToLower().Equals("yes"))
              updateFeeTypeAmountHandler(sender, e);
                noError = true;
            }
            else
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());                               
                errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidFeeChargeFormat));                
                if (double.TryParse(amountTextBox.Text, out this.amount))
                {
                    if (Convert.ToDouble(amountTextBox.Text, System.Threading.Thread.CurrentThread.CurrentUICulture) < 0)
                    {
                        errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidFeeCharge));
                    }
                }
                noError = false;
            }
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
        }

        private void amountTextBox_Leave(object sender, EventArgs e)
        {
            if (noError)
            {
                amountTextBox.Text = ROIViewUtility.FormattedAmount(amount);
                // TODO - Tax Rate will be calcualted based on request facility
                //if(hasSalesTax.ToLower().Equals("yes"))
                if(this.hasSalesTax)
                {
                    taxAmount = amount * facilityTaxPercentage / 100;
                }                
            }
        }

        /// <summary>
        /// Gets the sts fee type details
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            FeeChargeDetails feeCharge = (data == null) ? new FeeChargeDetails() : (FeeChargeDetails)data;
            double amount;
            feeCharge.FeeType = feeTypeLabel.Text;
            if (!double.TryParse(amountTextBox.Text, out amount))
            {
                amount = 0;
            }
            feeCharge.Amount = amount;
            feeCharge.TaxAmount = Amount * facilityTaxPercentage / 100;
            feeCharge.HasSalesTax = this.hasSalesTax;
            
            return feeCharge;
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
        /// Recalculate tax based on user select apply tax 
        /// </summary>
        public void CalculateTax(double taxPercentage)
        {
            facilityTaxPercentage = taxPercentage;   // If user uncheck apply tax and change the fee amount      
            if(this.hasSalesTax)
            {
                taxAmount = Amount * taxPercentage / 100;
            }            
        }
        
        #endregion

        #region Properties
        
        /// <summary>
        /// Standard fee type amount
        /// </summary>
        public double Amount
        {
            get { return amount; }
        }

        /// <summary>
        /// Standard fee type tax amount
        /// </summary>
        public double TaxAmount
        {
            get { return taxAmount; }
        }

        public double FacilityTaxPercentage
        {
            get { return facilityTaxPercentage; }
            set { facilityTaxPercentage = value; }
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
        #endregion

       

    }
}
