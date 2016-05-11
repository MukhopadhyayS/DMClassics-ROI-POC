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

namespace McK.EIG.ROI.Client.OverDueInvoice.View
{
    /// <summary>
    /// Placeholder to hold all events related to Past due invoice module.
    /// </summary>
    public static class OverDueInvoiceEvents
    {
        #region Fields

        //LSP Events
        public static event EventHandler NavigatePastDueInvoiceByFacility;
        
        //MCP Events
        public static event EventHandler ResetSearch;
        public static event EventHandler InvoiceSearched;

        #endregion

        #region Methods
        
        /// <summary>
        /// Occurs when Find Past Due Invoice By Facility Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigatePastDueInvoiceByFacility(object sender, EventArgs e)
        {
            if (NavigatePastDueInvoiceByFacility != null)
            {
                // Occurs when Find past due invoice navigation link is clicked
                NavigatePastDueInvoiceByFacility(sender, e);
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
                ResetSearch(sender,e);
            }
        }

        /// <summary>
        /// Occurs when FindInvoice Button is invoked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnInvoiceSearched(object sender, EventArgs e)
        {
            if (InvoiceSearched != null)
            {
                InvoiceSearched(sender, e);                
            }
        }

        #endregion
    }
}