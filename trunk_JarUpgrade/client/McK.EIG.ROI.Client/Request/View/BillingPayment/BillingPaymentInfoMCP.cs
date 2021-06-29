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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// MCP which holds the view of BillingPaymentInfoUI
    /// </summary>
    public class BillingPaymentInfoMCP : ROIBasePane, ITransientDataApprover 
    {
        #region Fields

        private BillingPaymentInfoUI billingPaymentInfoUI;

        private EventHandler updatePendingReleaseCostHandler;        

        private EventHandler updateTotalTaxHandler;

        private EventHandler updateReleaseinfoUI;

        private EventHandler updateBillingApplyTax;

        private EventHandler updateTaxAmount;

        private EventHandler UpdateReleaseCost;

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the view
        /// </summary>
        protected override void InitView()
        {
            billingPaymentInfoUI = new BillingPaymentInfoUI();
            billingPaymentInfoUI.ApplySecurityRights();
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            TransientDataValidator.AddApprover(this);

            updatePendingReleaseCostHandler = new EventHandler(Process_UpdatePendingReleaseCost);
            updateTotalTaxHandler = new EventHandler(Process_UpdateTotalTax);
            updateBillingApplyTax = new EventHandler(Process_UpdateBillingApplyTax);
            updateReleaseinfoUI = new EventHandler(Process_UpdatereleaseInfoUI);
            updateTaxAmount = new EventHandler(Process_UpdateTaxAmount);
            UpdateReleaseCost = new EventHandler(Process_UpdateReleaseCost);

            RequestEvents.ReleaseInfoUIChanged += updateReleaseinfoUI;
            RequestEvents.ReleaseCostChanged += updatePendingReleaseCostHandler;            
            RequestEvents.TotalTaxChanged += updateTotalTaxHandler;
            RequestEvents.ApplyTaxChanged += updateBillingApplyTax;
            RequestEvents.ChargeAmountChanged += updateTaxAmount;
            RequestEvents.ReleaseCostUpdated += UpdateReleaseCost;
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            TransientDataValidator.RemoveApprover(this);

            RequestEvents.ReleaseCostChanged -= updatePendingReleaseCostHandler;            
            RequestEvents.TotalTaxChanged -= updateTotalTaxHandler;
            RequestEvents.ApplyTaxChanged -= updateBillingApplyTax;
            RequestEvents.ChargeAmountChanged -= updateTaxAmount;
            RequestEvents.ReleaseInfoUIChanged -= updateReleaseinfoUI;
            RequestEvents.ReleaseCostUpdated -= UpdateReleaseCost;
        }

        /// <summary>
        /// updates the peding release cost
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdatePendingReleaseCost(object sender, EventArgs e)
        {
            billingPaymentInfoUI.UpdatePendingReleaseCost();
        }
        private void Process_UpdateReleaseCost(object sender, EventArgs e)
        {
            billingPaymentInfoUI.UpdateBalance();
        }

        private void Process_UpdateTotalTax(object sender, EventArgs e)
        {
            billingPaymentInfoUI.UpdateTotalTax();
        }
        
        private void Process_UpdatereleaseInfoUI(object sender, EventArgs e)
        {
            billingPaymentInfoUI.UpdateUIWithDetails();
        }

        private void Process_UpdateBillingApplyTax(object sender, EventArgs e)
        {
            billingPaymentInfoUI.UpdateApplyTaxState();
        }

        private void Process_UpdateTaxAmount(object sender, EventArgs e)
        {
            billingPaymentInfoUI.UpdateTaxAmount();
        }

        /// <summary>
        /// Sets the release data
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            billingPaymentInfoUI.SetData(data);
        }

        /// <summary>
        /// Prepopulates the datas
        /// </summary>
        /// <param name="request"></param>
        /// <param name="chargeHistories"></param>
        public void PrePopulate(RequestDetails request)
        {
            billingPaymentInfoUI.PrePopulate(request);
        }

        #endregion

        #region ITransientDataApprover Members

        /// <summary>
        /// Check wether the data is modified and display the
        /// confirm data loss dialog box for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true, if user click OK button in dialog box.</returns>
        public bool Approve(ApplicationEventArgs ae)
        {
            if (!billingPaymentInfoUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                billingPaymentInfoUI.IsDirty = false;
                if (!((BillingPaymentInfoUI)this.View).Save())
                {
                    billingPaymentInfoUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {                
                ((BillingPaymentInfoUI)this.View).SetData(((BillingPaymentInfoEditor)ParentPane).Release);
                billingPaymentInfoUI.IsDirty = false;
                return true;
            }

            return false;
        }

        #endregion

        #region Properties

        /// <summary>
        /// View of BillingPaymentInfoUI
        /// </summary>
        public override Component View
        {
            get { return billingPaymentInfoUI; }
        }

        #endregion

    }
}
