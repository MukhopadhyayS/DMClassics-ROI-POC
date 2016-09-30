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
using System.Collections.ObjectModel;
using System.Drawing;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.Controller;

namespace McK.EIG.ROI.Client.Request.View.EventHistory
{
    /// <summary>
    ///  Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class EventHistoryEditor : ROIEditor
    {
        #region Fields

        private RequestPatientHeaderUI patientheaderUI;
        private RequestNonPatientHeaderUI nonPatientHeaderUI;
        private RequestDetails requestDetails;

        #endregion

        #region Method

        /// <summary>
        /// Initilize the MCP, ODP and HeaderUI.
        /// </summary>
        protected override void InitComponent()
        {
            base.InitComponent();
            base.hOuterSplitContainer.SplitterDistance = 40;

            requestDetails = (ParentPane as RequestRSP).InfoEditor.Request;
            HeaderUI headerUI = SubPanes[0].View as HeaderUI;
            if (requestDetails.Requestor.IsPatientRequestor)
            {
                patientheaderUI = new RequestPatientHeaderUI();
                headerUI.HeaderExtension = patientheaderUI;
                headerUI.Parent.Size = new Size(headerUI.Parent.Width, patientheaderUI.Height + 15);
            }
            else
            {
                nonPatientHeaderUI = new RequestNonPatientHeaderUI();
                headerUI.HeaderExtension = nonPatientHeaderUI;
                headerUI.Parent.Size = new Size(headerUI.Parent.Width, nonPatientHeaderUI.Height + 5);
            }
        }

        /// <summary>
        /// Localize the MCP, ODP and HeaderUI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            if (requestDetails.Requestor.IsPatientRequestor)
            {
                patientheaderUI.Localize(Context);
                patientheaderUI.PopulateRequestorInfo(requestDetails);
            }
            else
            {
                nonPatientHeaderUI.Localize(Context);
                nonPatientHeaderUI.PopulateRequestorInfo(requestDetails);
            }            
        }

        /// <summary>
        /// PrePopulate the Comment in RequestCommentListUI.
        /// </summary>
        public override void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);                

                EventHistoryMCP eventHistoryMCP = MCP as EventHistoryMCP;

                Collection<RequestEventHistoryDetails> eventHistoryList = RequestController.Instance.RetrieveEventHistory(requestDetails.Id);

                string[] requestEvents = RequestController.Instance.RetrieveRequestEvents();

                eventHistoryMCP.PrePopulate(eventHistoryList,requestEvents);      
         
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

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the request comment.
        /// </summary>
        protected override string TitleText
        {
            get { return "requesteventhistory.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the request comment.
        /// </summary>
        protected override string InfoText
        {
            get { return "requesteventhistory.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(EventHistoryMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        protected override Type ODPType
        {
            get { return null; }
        }

        /// <summary>
        /// Resize the MCP Panel Auto Scroll minimum size
        /// </summary>
        protected override Nullable<Size> MCPAutoScrollMinSize
        {
            get{ return new Size(320, 60); }
        }

        /// <summary>
        /// Resize the MCP Panel Auto Scroll margin
        /// </summary>
        protected override Nullable<Size> MCPAutoScrollMargin
        {
            get { return new Size(320, 60); }
        }

        /// <summary>
        /// Hold the request details.
        /// </summary>
        public RequestDetails RequestInfo
        {
            get { return requestDetails; }
            set { requestDetails = value; }
        }

        #endregion
    }
}
