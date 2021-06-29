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
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Request.View;


namespace McK.EIG.ROI.Client.Requestors.View.RequestHistory
{
    public class RequestHistoryMCP : ROIBasePane 
    {
        #region Fields

        private RequestHistoryUI requestHistoryUI;

        private EventHandler requestDeleted;
        private EventHandler requestUpdated;
        private EventHandler requestCreated;

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the view of RequestHistoryListUI
        /// </summary>
        protected override void InitView()
        {
            requestHistoryUI = new RequestHistoryUI();
            requestHistoryUI.ApplySecurityRights();
        }

        /// <summary>
        /// Prepopulates the data.
        /// </summary>
        /// <param name="requestorDetails"></param>
        public void PrePopulate(RequestorDetails requestorDetails)
        {
            requestHistoryUI.SetData(requestorDetails);
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            requestDeleted = new EventHandler(Process_RequestDeleted);
            requestUpdated = new EventHandler(Process_RequestUpdated);
            requestCreated = new EventHandler(Process_RequestCreated);

            RequestEvents.RequestDeleted += requestDeleted;
            RequestEvents.RequestUpdated += requestUpdated;
            RequestEvents.RequestInfoCreated += requestCreated;

        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestEvents.RequestDeleted -= requestDeleted;
            RequestEvents.RequestUpdated -= requestUpdated;
            RequestEvents.RequestInfoCreated -= requestCreated;
        }

        /// <summary>
        /// Event occurs when request info created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestCreated(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                requestHistoryUI.AddRow(ae.Info);
            }
        }

        /// <summary>
        /// Event occurs when request info got updated 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestUpdated(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                requestHistoryUI.UpdateRow(ae.Info);
            }
        }

        /// <summary>
        /// Event occurs when Request is Deleted.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestDeleted(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                requestHistoryUI.DeleteRow(ae.Info);
            }
        }
        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of RequestHistoryListUI
        /// </summary>
        public override Component View
        {
            get { return requestHistoryUI; }
        }

        #endregion
    }
}
