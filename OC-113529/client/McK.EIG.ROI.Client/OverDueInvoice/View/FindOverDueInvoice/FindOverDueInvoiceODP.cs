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
using System.ComponentModel;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.OverDueInvoice.View.FindOverDueInvoice
{
    /// <summary>
    /// Holds the PastDueInvoice OPD.
    /// </summary>
    public class FindOverDueInvoiceODP : ROIBasePane
    {
        #region Fields

        private EventHandler searchReset;
        private EventHandler invoiceSearched;

        private FindOverDueInvoiceListUI pastDueInvoiceListUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilizes the Past Due Invoice List UI.
        /// </summary>
        protected override void InitView()
        {
            pastDueInvoiceListUI = new FindOverDueInvoiceListUI();            
        }

        /// <summary>
        /// Register events.
        /// </summary>
        protected override void Subscribe()
        {
            searchReset     = new EventHandler(Process_SearchReset);
            invoiceSearched = new EventHandler(Process_InvoiceSearched);

            OverDueInvoiceEvents.ResetSearch     += searchReset;
            OverDueInvoiceEvents.InvoiceSearched += invoiceSearched;
        }

        /// <summary>
        ///  Unregister events.
        /// </summary>
        protected override void Unsubscribe()
        {
            OverDueInvoiceEvents.ResetSearch        -= searchReset;
            OverDueInvoiceEvents.InvoiceSearched    -= invoiceSearched;
        }

        /// <summary>
        /// Occurs when Reset button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SearchReset(object sender, EventArgs e)
        {
            if (ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            pastDueInvoiceListUI.SetData(ae.Info);
        }

        /// <summary>
        /// Occurs when Find Invoice button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_InvoiceSearched(object sender, EventArgs e)
        {
            if (ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            pastDueInvoiceListUI.SetData(ae.Info);
        }
        
        #endregion

        #region Properties

        /// <summary>
        /// Gets the past due invoice list UI.
        /// </summary>        
        public override Component View
        {
            get { return pastDueInvoiceListUI; }
        }

        #endregion
    }
}
