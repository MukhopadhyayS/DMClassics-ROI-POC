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

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Reasons.RequestReasons
{
    /// <summary>
    /// Used to group Header Pane MCP and ODP
    /// </summary>
    public class RequestReasonsEditor : AdminEditor
    {
        #region Methods

        /// <summary>
        /// Prepopulate values in MCP and ODP
        /// </summary>
        public override void PrePopulate()
        {
            RequestReasonsODP odp = (RequestReasonsODP)SubPanes[2];
            odp.PrePopulate();
            RequestReasonsMCP mcp = (RequestReasonsMCP)SubPanes[1];
            mcp.PrePopulate();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the RequestReasonEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "requestreason.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the RequestReasonEditor.
        /// </summary>
        protected override string InfoText
        {
            get { return "requestreason.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(RequestReasonsMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP
        /// </summary>
        protected override Type ODPType
        {
            get { return typeof(RequestReasonsODP); }
        }  

        #endregion
    }
}
