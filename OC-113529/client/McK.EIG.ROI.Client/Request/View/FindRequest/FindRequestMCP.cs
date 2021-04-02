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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.View.FindRequest;

namespace McK.EIG.ROI.Client.Request.View.FindRequest
{ 
    /// <summary>
    /// Holds the Request Search UI.
    /// </summary>
    public class FindRequestMCP : ROIBasePane
    {
        #region Fields

        private RequestSearchUI requestSearchUI;

        private EventHandler requestDeleted;
        private EventHandler requestUpdated;

        #endregion

        #region Methods

        /// <summary>
        /// Subscribes the events
        /// </summary>
        protected override void Subscribe()
        {
            requestDeleted = new EventHandler(Process_RequestDeleted);
            requestUpdated = new EventHandler(Process_RequestUpdated);

            RequestEvents.RequestDeleted += requestDeleted;
            RequestEvents.RequestUpdated += requestUpdated;
        }

        /// <summary>
        /// UnSubscribes the events
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestEvents.RequestDeleted -= requestDeleted;
            RequestEvents.RequestUpdated -= requestUpdated;
        }

        /// <summary>
        /// Initilize th Request Search UI.
        /// </summary>        
        protected override void InitView()
        {
            requestSearchUI = new RequestSearchUI();
            requestSearchUI.ApplySecurityRights();
        }

        /// <summary>
        /// Event occurs when request info got updated 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestUpdated(object sender, EventArgs e)
        {
            requestSearchUI.RefreshSearch();
        }

        /// <summary>
        /// Event occurs when Request is Deleted.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestDeleted(object sender, EventArgs e)
        {
            requestSearchUI.RefreshSearch();
        }


        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of FindRequest MCP.
        /// </summary>        
        public override Component View
        {
            get { return requestSearchUI; }
        }

        #endregion
    }
}
