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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Dialog which shows charge history details of the request
    /// </summary>
    public partial class ChargeHistoryDialogUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public ChargeHistoryDialogUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public ChargeHistoryDialogUI(IPane pane) : this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);

            Localize();
            SetHeader();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localiztion for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            //SetLabel(rm, previouslyReleasedCostLabel);
            SetLabel(rm, closeButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, closeButton);
        }

        /// <summary>
        /// Gets the localized key of UI control
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets the localized tooltip key of UI control
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Sets the header info
        /// </summary>
        private void SetHeader()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Header = new HeaderUI();
            Header.Title = rm.GetString("chargehistory.header.title");
            Header.Information = rm.GetString("chargehistory.header.info");
        }

        /// <summary>
        /// Sets the charge history details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            Collection<ChargeHistoryDetails> chargeHistories = (Collection<ChargeHistoryDetails>)data;
            
            ChargeHistoryUI chargeHistoryUI;
            double totalPreviouslyReleasedCost = 0;
            double documentChargeTotal = 0;
            double feeChargeTotal = 0;
            double shippingChargeTotal = 0;
            double salesTaxTotal = 0;
            double unBillableAmountTotal = 0;
            foreach (ChargeHistoryDetails chargeHistory in chargeHistories)
            {
                chargeHistoryUI = new ChargeHistoryUI(Pane);
                chargeHistoryUI.SetData(chargeHistory);
                totalPreviouslyReleasedCost += chargeHistory.TotalReleaseCost;

                flowLayoutPanel1.Controls.Add(chargeHistoryUI);
                documentChargeTotal += chargeHistory.DocumentChargeTotal;
                feeChargeTotal += chargeHistory.FeeChargeTotal;
                shippingChargeTotal += chargeHistory.ShippingCharge;
                salesTaxTotal += chargeHistory.SalesTaxTotal;
                unBillableAmountTotal += chargeHistory.UnBillableAmount;
                
            }
            // CR#366859 - To calculate the total charge details
            ChargeHistoryDetails totalChargeDetails = new ChargeHistoryDetails();
            totalChargeDetails.DocumentChargeTotal = documentChargeTotal;
            totalChargeDetails.FeeChargeTotal = feeChargeTotal;
            totalChargeDetails.ShippingCharge = shippingChargeTotal;
            totalChargeDetails.SalesTaxTotal = salesTaxTotal;
            totalChargeDetails.UnBillableAmount = unBillableAmountTotal;
            chargeHistoryUI = new ChargeHistoryUI(Pane);
            chargeHistoryUI.LocalizeTotalSummary();
            chargeHistoryUI.SetData(totalChargeDetails);
            chargeHistoryUI.LocalizeTotalRequestSummary();
            flowLayoutPanel1.Controls.Add(chargeHistoryUI);
            //previouslyReleasedCostValueLabel.Text = ReleaseDetails.FormattedAmount(totalPreviouslyReleasedCost);

        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets the header
        /// </summary>
        public HeaderUI Header
        {
            get { return header; }
            set
            {
                header = value;
                header.Dock = DockStyle.Fill;
                topPanel.Controls.Add(header);
            }
        }

        #endregion
    }
}
