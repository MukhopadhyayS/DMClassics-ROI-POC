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

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Requestors.Model;


namespace McK.EIG.ROI.Client.Requestors.View.RequestHistory
{
    public class RequestHistoryEditor : ROIEditor
    {
        #region Fields

        private RequestorDetails requestorInfo;
        private RequestorHeaderUI requestorHeaderUI;

        private EventHandler requestorUpdated;

        #endregion

        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();

            RequestHistoryUI requestHistoryUI = (RequestHistoryUI)SubPanes[1].View;
            requestHistoryUI.AddListPanel();

            requestorInfo = ((RequestorRSP)ParentPane).InfoEditor.RequestorInfo;
            HeaderUI headerUI = SubPanes[0].View as HeaderUI;
            headerUI.Parent.Size = new System.Drawing.Size(128, 100);
            requestorHeaderUI = new RequestorHeaderUI();
            requestorHeaderUI.PopulateRequestorInformation(requestorInfo, Context);
            headerUI.HeaderExtension = requestorHeaderUI;
        }

        public override void PrePopulate()
        {
            try
            {
                ((RequestHistoryMCP)MCP).PrePopulate(requestorInfo);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        /// <summary>
        /// Localize Header UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            requestorHeaderUI.Localize(Context);
        }

        public override void Cleanup()
        {
            base.Cleanup();
            View.Dispose();
            // do nothing since this instance need to reused
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        protected override void Subscribe()
        {
            requestorUpdated = new EventHandler(Process_UpdateRequestorInfo);
            RequestorEvents.RequestorUpdated += requestorUpdated;
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestorEvents.RequestorUpdated -= requestorUpdated;
        }

        /// <summary>
        /// Updates the patient's information on patient information box
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateRequestorInfo(object sender, EventArgs e)
        {
            RequestorDetails requestor = (RequestorDetails)((ApplicationEventArgs)e).Info;
            requestorHeaderUI.PopulateRequestorInformation(requestor, Context);
            ((RequestHistoryUI)MCP.View).Footer.CreateRequestButton.Enabled = requestor.IsActive;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the title text of the request history.
        /// </summary>
        protected override string TitleText
        {
            get { return "requestor.requesthistory.header.title"; }
        }

        /// <summary>
        /// Returns the title information of the request history.
        /// </summary>
        protected override string InfoText
        {
            get { return "requestor.requesthistory.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(RequestHistoryMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        protected override Type ODPType
        {
            get { return null; }
        }

        #endregion
    }
}
