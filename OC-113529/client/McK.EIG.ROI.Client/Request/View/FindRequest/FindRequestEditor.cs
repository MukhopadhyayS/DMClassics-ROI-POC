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
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Request.View.FindRequest
{
    /// <summary>
    /// Holds the Find Request  Editor.
    /// </summary>
    /// 
    public class FindRequestEditor : ROIEditor
    {

        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();
            base.hOuterSplitContainer.SplitterDistance = 60;
            hOuterSplitContainer.Panel2.Padding = new Padding(1);

            RequestListUI requestListUI = (RequestListUI)SubPanes[2].View;
            requestListUI.AddListPanel();
            HeaderUI headerUI = SubPanes[0].View as HeaderUI;
            headerUI.Parent.Size = new Size(headerUI.Parent.Width, headerUI.Height + 15);

            //Apply securtiy rights
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROIViewRequest);
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            ((Control)View).Enabled = ROIViewUtility.IsAllowed(securityRightIds, false);
        }

        public override void Cleanup()
        {
            // do nothing since this instance need to reused.
        }

        /// <summary>
        /// PrePopulate the requestor types in PatientSearchUI.
        /// </summary>
        public override void PrePopulate()
        {
            ROIViewUtility.MarkBusy(true);

            try
            {
                Collection<RequestorTypeDetails> requestorTypes = ROIAdminController.Instance.RetrieveAllRequestorTypes(false);
                (MCP.View as RequestSearchUI).PrePopulate(requestorTypes);
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
            get { return "findRequest.header.title"; }
        }

        protected override string InfoText
        {
            get { return "findRequest.header.info"; }
        }

        protected override Type MCPType
        {
            get { return typeof(FindRequestMCP); }
        }

        protected override Type ODPType
        {
            get { return typeof(FindRequestODP); }
        }

        #endregion 
    }
}
