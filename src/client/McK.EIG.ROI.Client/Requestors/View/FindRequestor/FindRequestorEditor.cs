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
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Requestors.View.FindRequestor
{
    /// <summary>
    /// Used to group Header Pane and MCP/ODP
    /// </summary>
    public class FindRequestorEditor : ROIEditor
    {
        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();
            base.hOuterSplitContainer.SplitterDistance = 50;

            //Apply security rights
            ((Control)View).Enabled = ROIViewUtility.IsAllowed(ROISecurityRights.MasterPatientIndexSearch);            
        }

        public override void Cleanup()
        {
            // do nothing since this instance need to reused
        }

        public override void PrePopulate()
        {
            Collection<RequestorTypeDetails> requestorTypes = ROIAdminController.Instance.RetrieveAllRequestorTypes(false);
            ((FindRequestorMCP)MCP).PrePopulate(requestorTypes);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the FindRequestorEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "findrequestor.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the FindRequestorEditor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "findrequestor.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(FindRequestorMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return typeof(FindRequestorODP); }
        }

        public RequestorDetails SelectedRequestor
        {
            get { return (ODP.View as FindRequestorListUI).SelectedRequestor; }
        }

        #endregion
    }
}
