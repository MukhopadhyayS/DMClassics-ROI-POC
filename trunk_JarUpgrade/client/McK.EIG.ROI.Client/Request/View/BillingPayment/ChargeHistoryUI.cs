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
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Class which maintains charge history details
    /// </summary>
    public partial class ChargeHistoryUI : ROIBaseUI
    {
        #region Constructor

        /// <summary>
        /// Initialize the UI component
        /// </summary>
        public ChargeHistoryUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets the Pane and Execution context
        /// </summary>
        /// <param name="pane"></param>
        public ChargeHistoryUI(IPane pane) : this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, feeChargeLabel);
            SetLabel(rm, documentChargeLabel);
            SetLabel(rm, shippingChargeLabel);
            SetLabel(rm, salesTaxLabel); //CR#366859 - Seperate Sales Tax cost from the document charges total
            SetLabel(rm, totalReleaseCostLabel);
            SetLabel(rm, unBillableAmountLabel);
        }

        /// <summary>
        /// Gets localized key of UI control
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Sets charge history details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            ChargeHistoryDetails chargeHistory = (ChargeHistoryDetails)data;
                     
            documentChargeValueLabel.Text   = FormattedAmount(chargeHistory.DocumentChargeTotal);
            feeChargeValueLabel.Text        = FormattedAmount(chargeHistory.FeeChargeTotal);
            shippingChargeValueLabel.Text   = FormattedAmount(chargeHistory.ShippingCharge);
            salesTaxValueLabel.Text         = FormattedAmount(chargeHistory.SalesTaxTotal); //CR#366859 - Seperate Sales Tax cost from the document charges total
            totalReleaseCostValueLabel.Text = FormattedAmount(chargeHistory.TotalReleaseCost);
            unBillableAmountValueLabel.Text = FormattedAmount(chargeHistory.UnBillableAmount);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            releaseDateValueLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString(releaseDateValueLabel.Name + "." + GetType().Name), Convert.ToDateTime(chargeHistory.ReleasedDate, CultureInfo.InvariantCulture).ToShortDateString());
        }
        /// <summary>
        /// //CR#366859 - Apply localization for UI controls  
        /// </summary>
        public void LocalizeTotalSummary()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            documentChargeLabel.Text = rm.GetString("documentChargesRequestTotalLabel.ChargeHistoryUI");
            feeChargeLabel.Text = rm.GetString("feeChargesRequestTotalLabel.ChargeHistoryUI");
            shippingChargeLabel.Text = rm.GetString("shippingChargesRequestTotalLabel.ChargeHistoryUI");
            salesTaxLabel.Text = rm.GetString("salesTaxRequestTotalLabel.ChargeHistoryUI");
            unBillableAmountLabel.Text = rm.GetString("unBillableAmountTotalLabel.ChargeHistoryUI");
            totalReleaseCostLabel.Text = rm.GetString("totalRequestCostLabel.ChargeHistoryUI");
        }

        /// <summary>
        /// //CR#366859 - Apply localization for UI controls  
        /// </summary>
        public void LocalizeTotalRequestSummary()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            releaseDateValueLabel.Text = rm.GetString("totalRequestSummaryLabel.ChargeHistoryUI");
        }

        /// <summary>
        /// Formatted amount
        /// </summary>
        /// <param name="amount"></param>
        /// <returns></returns>
        private static string FormattedAmount(double amount)
        {
            return amount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
        }
        #endregion
    }
}
