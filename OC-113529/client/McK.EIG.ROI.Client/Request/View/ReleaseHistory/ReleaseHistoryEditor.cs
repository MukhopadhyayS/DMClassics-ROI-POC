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
using System.Drawing;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Request.View.ReleaseHistory
{   
    /// <summary>
    /// Groups the Header Pane and MCP.
    /// </summary>
    public class ReleaseHistoryEditor : ROIEditor
    {
        #region Fields

        private RequestPatientHeaderUI patientHeaderUI;
        private RequestNonPatientHeaderUI nonPatientHeaderUI;
        private RequestDetails requestDetails;

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the MCP and HeaderUI.
        /// </summary>
        protected override void InitComponent()
        {
            base.InitComponent();
            
            requestDetails = (ParentPane as RequestRSP).InfoEditor.Request;
            HeaderUI headerUI = SubPanes[0].View as HeaderUI;
            if (requestDetails.Requestor.TypeName == ROIConstants.RequestorPatient)
            {
                patientHeaderUI = new RequestPatientHeaderUI();
                headerUI.HeaderExtension = patientHeaderUI;
                headerUI.Parent.Size = new Size(headerUI.Parent.Width, patientHeaderUI.Height + 15);
            }
            else
            {
                nonPatientHeaderUI = new RequestNonPatientHeaderUI();
                headerUI.HeaderExtension = nonPatientHeaderUI;
                headerUI.Parent.Size = new Size(headerUI.Parent.Width, nonPatientHeaderUI.Height + 5);
            }
        }

        /// <summary>
        /// Localizes the MCP and HeaderUI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            if (requestDetails.Requestor.TypeName == ROIConstants.RequestorPatient)
            {
                patientHeaderUI.Localize(Context);
                patientHeaderUI.PopulateRequestorInfo(requestDetails);
            }
            else
            {
                nonPatientHeaderUI.Localize(Context);
                nonPatientHeaderUI.PopulateRequestorInfo(requestDetails);
            }
        }

        /// <summary>
        /// PrePopulates the Release Histories in ReleaseHistoryListUI.
        /// </summary>
        public override void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
               
                ((ReleaseHistoryMCP)MCP).PrePopulate(requestDetails.Id, requestDetails.RequestSecretWord);
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

        protected override string TitleText
        {
            get { return "releasehistory.header.title"; }
        }

        protected override string InfoText
        {
            get { return "releaseHistory.header.info"; }
        }

        protected override Type MCPType
        {
            get { return typeof(ReleaseHistoryMCP); }
        }

        protected override Type ODPType
        {
            get { return null; }
        }

        #endregion
    }
}
