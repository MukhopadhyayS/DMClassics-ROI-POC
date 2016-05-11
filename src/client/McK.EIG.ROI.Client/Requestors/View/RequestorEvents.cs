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

namespace McK.EIG.ROI.Client.Requestors.View
{
    /// <summary>
    /// Place holders to hold all events related to Requestor Module.
    /// </summary>
    public static class RequestorEvents
    {
        #region Fields

        //LSP Events
        public static event EventHandler NavigateFindRequestor;
        public static event EventHandler NavigateRequestorInfo;
        public static event EventHandler NavigateRequestHistory;
        public static event EventHandler NavigateRequestorLetterHistory;
        public static event EventHandler NavigateAccountManagement;
        public static event EventHandler NavigateAccountHistory;

        //MCP Events.
        public static event EventHandler CreateRequestor;
        public static event EventHandler ResetSearch;
        public static event EventHandler RequestorSearched;
        
        //ODP Data Change.
        public static event EventHandler RequestorCreated;
        public static event EventHandler RequestorUpdated;
        public static event EventHandler RequestorDeleted;
        public static event EventHandler RequestorSelected;

        //From Menu Pane
        public static event EventHandler CreateRequest;
        public static event EventHandler RequestorStatusChanged;

        public static event EventHandler AccountManagementSelected;
        public static event EventHandler AccntHistorySelected;
               
        #endregion

        #region Methods
        /// <summary>
        /// Occurs when Find Requestor Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateFindRequestor(object sender, EventArgs e)
        {
            if (NavigateFindRequestor != null)
            {
                NavigateFindRequestor(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Requestor Information Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateRequestorInfo(object sender, EventArgs e)
        {
            if (NavigateRequestorInfo != null)
            {
                NavigateRequestorInfo(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Request History click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnNavigateRequestHistory(object sender, EventArgs e)
        {
            if (NavigateRequestHistory != null)
            {
                NavigateRequestHistory(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Requestor Letter History click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateRequestorLetterHistory(object sender, EventArgs e)
        {
            if (NavigateRequestorLetterHistory != null)
            {
                NavigateRequestorLetterHistory(sender, e);
            }
        }

         /// <summary>
        /// Occurs when Requestor Letter History click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateAccountManagement(object sender, EventArgs e)
        {
            if (NavigateAccountManagement != null)
            {
                NavigateAccountManagement(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Requestor Account History click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigateAccountHistory(object sender, EventArgs e)
        {
            if (NavigateAccountHistory != null)
            {
                NavigateAccountHistory(sender, e);
            }
        }

        /// <summary>
        /// Occurs when user clicks create requestor button in FindRequestor MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnCreateRequestor(object sender, EventArgs e)
        {
            if (CreateRequestor != null)
            {
                CreateRequestor(sender, e);
            }
        }

        /// <summary>
        /// Occurs when FindRequestor Button is invoked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestorSearched(object sender, EventArgs e)
        {
            if (RequestorSearched != null)
            {
                RequestorSearched(sender, e);
            }
        }

        /// <summary>
        /// Occurs When Reset Button is Clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnResetSearch(object sender, EventArgs e)
        {
            if (ResetSearch != null)
            {
                ResetSearch(sender, e);
            }
        }
        /// <summary>
        /// Occurs when user selects the requestor from the search rresult.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestorSelected(object sender, EventArgs e)
        {
            if (RequestorSelected != null)
            {
                RequestorSelected(sender, e);
            }
        }

        /// <summary>
        /// Occurs when user selects the Account Management from Billing & Payment screen
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnAccountManagementSelected(object sender, EventArgs e)
        {
            if (AccountManagementSelected != null)
            {
                AccountManagementSelected(sender, e);
            }
        }

        /// <summary>
        /// Occurs when Requestor Deleted.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestorDeleted(object sender, EventArgs e)
        {
            if (RequestorDeleted != null)
            {
                RequestorDeleted(sender, e);
            }
        }

        /// <summary>
        /// Occurs once Requestor is created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestorCreated(object sender, EventArgs e)
        {
            if (RequestorCreated != null)
            {
                RequestorCreated(sender, e);
            }
        }

        /// <summary>
        /// Occurs once Requestor is updated.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestorUpdated(object sender, EventArgs e)
        {
            if (RequestorUpdated != null)
            {
                RequestorUpdated(sender, e);
            }
        }

        /// <summary>
        /// Occurs when CreateRequest menuitem selected from Requestor Menu.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnCreateRequestWithRequestor(object sender, EventArgs e)
        {
            if (CreateRequest != null)
            {
                CreateRequest(sender, e);
            }
        }

        /// <summary>
        /// Occurs when requestor's status is changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnRequestorStatusChanged(object sender, EventArgs e)
        {
            if (RequestorStatusChanged != null)
            {
                RequestorStatusChanged(sender, e);
            }
        }

        #endregion
    }
}
