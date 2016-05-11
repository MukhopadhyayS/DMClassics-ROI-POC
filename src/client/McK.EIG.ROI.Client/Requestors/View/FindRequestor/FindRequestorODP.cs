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

using McK.EIG.ROI.Client.Base.View;

using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Requestors.View.FindRequestor
{
    /// <summary>
    /// Holds RequestorlistUI.
    /// </summary>
    public class FindRequestorODP : ROIBasePane
    {
        #region Fields

        private EventHandler createRequestor;
        private EventHandler RequestorSearched;
        private EventHandler SearchReset;
        private EventHandler RequestorDeleted;
        private EventHandler RequestorUpdated;
      
        private FindRequestorListUI requestorListUI;

        #endregion

        #region Methods

        protected override void InitView()
        {
            requestorListUI = new FindRequestorListUI();
            requestorListUI.ApplySecurityRights();
        }
      
        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            createRequestor   = new EventHandler(Process_CreateRequestor);
            RequestorSearched = new EventHandler(Process_FindRequestor);
            SearchReset       = new EventHandler(Process_ResetButtonClick);
            RequestorDeleted  = new EventHandler(Process_DeleteButtonClick);
            RequestorUpdated  = new EventHandler(Process_UpdateRequestor);

            RequestorEvents.CreateRequestor   += createRequestor;
            RequestorEvents.RequestorSearched += RequestorSearched;
            RequestorEvents.ResetSearch       += SearchReset;
            RequestorEvents.RequestorDeleted  += RequestorDeleted;
            RequestorEvents.RequestorUpdated  += RequestorUpdated;
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestorEvents.CreateRequestor   -= createRequestor;
            RequestorEvents.RequestorSearched -= RequestorSearched;
            RequestorEvents.ResetSearch       -= SearchReset;
            RequestorEvents.RequestorDeleted  -= RequestorDeleted;
            RequestorEvents.RequestorUpdated  -= RequestorUpdated;
        }

        private void Process_CreateRequestor(object sender, EventArgs e)
        {
            if (ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            requestorListUI.ClearList();
        }

        /// <summary>
        /// Event occurs when Find Requestor button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_FindRequestor(object sender, EventArgs e)
        {
            if (ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            requestorListUI.SetData(ae.Info);
        }


        private void Process_ResetButtonClick(object sender, EventArgs e)
        {
            if(ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            requestorListUI.SetData(ae.Info);
        }


        private void Process_DeleteButtonClick(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                requestorListUI.DeleteRow(ae.Info);
            }
        }

        private void Process_UpdateRequestor(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                requestorListUI.UpdatedRow(ae.Info);
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of FindRequestorODP.
        /// </summary>   
        public override Component View
        {
            get { return requestorListUI; }
        }

        #endregion

    }
}
