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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.View
{
    public static class RequestEvents
    {

        #region Fields

        //LSP Events
        public static event EventHandler NavigateFindRequest;
        public static event EventHandler NavigateRequestInfo;
        public static event EventHandler NavigatePatientInfo;
        public static event EventHandler NavigateEventHistory;
        public static event EventHandler NavigateComment;
        public static event EventHandler NavigateLetters;
        public static event EventHandler NavigateReleaseHistory;
        public static event EventHandler NavigateBillingPayment;
        public static event EventHandler NavigateInvoiceHistory;

        //MCP Events.        
        public static event EventHandler CreateRequest;
        public static event EventHandler CancelCreateRequest;
        public static event EventHandler ResetSearch;
        public static event EventHandler RequestSearched;
        public static event EventHandler InvoiceSelected;
        
        //MCP Comment
        public static event EventHandler CommentCreated;

        //ODP Data Change.
        public static event EventHandler RequestDeleted;
        public static event EventHandler RequestInfoCreated;
        public static event EventHandler RequestSelected;
        public static event EventHandler RequestUpdated;
        
        //Billing 
        public static event EventHandler BillingSelected;
        public static event EventHandler ReleaseCostChanged;
        public static event EventHandler RequestReleased;
        public static event EventHandler DocTaxChanged;
        public static event EventHandler StdFeeTaxChanged;
        public static event EventHandler CustomFeeTaxChanged;
        public static event EventHandler TotalTaxChanged;
        public static event EventHandler ReleaseInfoUIChanged;
        public static event EventHandler ReleaseCostUpdated;
        public static event EventHandler ApplyTaxChanged;
        public static event EventHandler ChargeAmountChanged;

        //DSR change
        public static event EventHandler DocumentsTreeChanged;

        //Account Management, Add Payment & Add Adjustment Event wiring
        public static event EventHandler AccountManagementGridReferesh;      


        #endregion

        #region Methods

        /// <summary>
        /// Occurs when Find Request Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateFindRequest(object sender, EventArgs e)
        {
            if (NavigateFindRequest != null)
            {
                NavigateFindRequest(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Request Information Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateRequestInfo(object sender, EventArgs e)
        {
            if (NavigateRequestInfo != null)
            {
                NavigateRequestInfo(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Patient Information Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigatePatientInfo(object sender, EventArgs e)
        {
            if (NavigatePatientInfo != null)
            {
                NavigatePatientInfo(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Release History link Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        internal static void OnNavigateReleaseHistory(object sender, EventArgs e)
        {
            if (NavigateReleaseHistory != null)
            {
                NavigateReleaseHistory(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Comment Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateComment(object sender, EventArgs e)
        {
            if (NavigateComment != null)
            {
                NavigateComment(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Comment Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateLetters(object sender, EventArgs e)
        {
            if (NavigateLetters != null)
            {
                NavigateLetters(sender, e);
            }
        }

        

        /// <summary>
        /// Occurs when Billing & Payment Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateBillingPayment(object sender, EventArgs e)
        {
            if (NavigateBillingPayment != null)
            {
                NavigateBillingPayment(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Invoice History Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateInvoiceHistory(object sender, EventArgs e)
        {
            if (NavigateInvoiceHistory != null)
            {
                NavigateInvoiceHistory(sender, e);
            }
        }


        /// <summary>
        /// Occurs when Event History Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateEventHistory(object sender, EventArgs e)
        {
            if (NavigateEventHistory != null)
            {
                NavigateEventHistory(sender, e);
            }
        }

        /// <summary>
        /// Occurs when user clicks create request button in FindRequest MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.RequestInformation, e));
            CreateRequest(sender, e);
        }

        /// <summary>
        /// Occurs when user clicks cancel button in Request Information.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnCancelCreateRequest(object sender, EventArgs e)
        {
            if (CancelCreateRequest != null)
            {
                CancelCreateRequest(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Request Deleted.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestDeleted(object sender, EventArgs e)
        {
            if (RequestDeleted != null)
            {
                RequestDeleted(sender, e);

            }
        }

        /// <summary>
        /// Occurs when Request Info created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestInfoCreated(object sender, EventArgs e)
        {
            if (RequestInfoCreated != null)
            {
                RequestInfoCreated(sender, e);
            }
        }       

        /// <summary>
        /// Occurs When Reset Button is Clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnSearchReset(object sender, EventArgs e)
        {
            if (ResetSearch != null)
            {
                ResetSearch(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Save button invoked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnCommentCreated(object sender, EventArgs e)
        {
            if (CommentCreated != null)
            {
                CommentCreated(sender, e);
            }
        }

        /// <summary>
        /// Occurs when FindRequest Button is invoked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestSearched(object sender, EventArgs e)
        {
            if (RequestSearched != null)
            {
                RequestSearched(sender, e);
            }
        }

        internal static void OnRequestSelected(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.RequestInformation, e));
            RequestSelected(sender, e);
        }

        /// <summary>
        /// Occurs when request info is being updated
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestUpdated(object sender, EventArgs e)
        {
            if (RequestUpdated != null)
            {
                RequestUpdated(sender, e);
            }
        }

        /// <summary>
        /// Occurs when release cost is being updated
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnReleaseCostChanged(object sender, EventArgs e)
        {
            if (ReleaseCostChanged != null)
            {
                ReleaseCostChanged(sender, e);
            }
        }

        /// <summary>
        /// occurs when charge amount for document, fee & custom type is changed in billing screen
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnChargeAmountChanged(object sender, EventArgs e)
        {
            if (ChargeAmountChanged != null)
            {
                ChargeAmountChanged(sender, e);
            }
        }

        /// <summary>
        /// occurs when apply tax checkbox state is changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnApplyTaxChanged(object sender, EventArgs e)
        {
            if (ApplyTaxChanged != null)
            {
                ApplyTaxChanged(sender, e);
            }
        }

        internal static void OnTotalTaxChanged(object sender, EventArgs e)
        {
            if (TotalTaxChanged != null)
            {
                TotalTaxChanged(sender, e);
            }
        }

        internal static void OnReleaseInfoUIChanged(object sender, EventArgs e)
        {
            if (ReleaseInfoUIChanged != null)
            {
                ReleaseInfoUIChanged(sender, e);
            }
        }
        internal static void OnReleaseCostUpdated(object sender, EventArgs e)
        {
            if (ReleaseCostUpdated != null)
            {
                ReleaseCostUpdated(sender, e);
            }
        }

        /// <summary>
        /// Occurs when release cost is being updated
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnBillingSelected(object sender, EventArgs e)
        {
            if (BillingSelected != null)
            {
                BillingSelected(sender, e);
            }
        }
        
        /// <summary>
        /// Occurs when selected documents are relased
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestReleased(object sender, EventArgs e)
        {
            if (RequestReleased != null)
            {
                RequestReleased(sender, e);
            }
        }

        /// <summary>
        /// Occurs when documents are being changed on DSR
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnDsrChanged(object sender, EventArgs e)
        {
            if (DocumentsTreeChanged != null)
            {
                DocumentsTreeChanged(sender, e);
            }
        }

        /// <summary>
        /// Occurs when selected grid item in invoice history MCP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnInvoiceSelected(object sender, EventArgs e)
        {
            if (InvoiceSelected != null)
            {
                InvoiceSelected(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Account managment, Add Payment and Add Adjustment button clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnAccountManagementGridRefresh(object sender, EventArgs e)
        {
            if (AccountManagementGridReferesh != null)
            {
                AccountManagementGridReferesh(sender, e);
            }
        }
         

        #endregion

    }
}
