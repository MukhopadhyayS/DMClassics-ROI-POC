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
using McK.EIG.ROI.Client.Request.View.FindRequest;

namespace McK.EIG.ROI.Client.Request.View.FindRequest
{
    /// <summary>
    /// Holds the Request List UI.
    /// </summary>
    public class FindRequestODP : ROIBasePane
    {
        #region Fields

        private EventHandler createRequest;
        private EventHandler searchReset;
        private EventHandler requestSearched;

        private RequestListUI requestListUI;

        #endregion

        #region Methods

        protected override void InitView()
        {
            requestListUI = new RequestListUI();
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            createRequest   = new EventHandler(Process_CreateRequest);
            searchReset     = new EventHandler(Process_SearchReset);
            requestSearched = new EventHandler(Process_RequestSearched);

            RequestEvents.CreateRequest   += createRequest;
            RequestEvents.ResetSearch     += searchReset;
            RequestEvents.RequestSearched += requestSearched;
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestEvents.CreateRequest   -= createRequest;
            RequestEvents.ResetSearch     -= searchReset;
            RequestEvents.RequestSearched -= requestSearched;
        }

        /// <summary>
        /// Event occurs when New Request button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreateRequest(object sender, EventArgs e)
        {
            requestListUI.ClearList();
        }

        /// <summary>
        /// Event occurs when Reset button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SearchReset(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            requestListUI.SetData(ae.Info);
        }

        /// <summary>
        /// Event occurs when Find Request button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestSearched(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            requestListUI.SetData(ae.Info);
        }

        #endregion

        #region Properites

        public override Component View
        {
            get { return requestListUI; }
        }

        #endregion
    }
}
