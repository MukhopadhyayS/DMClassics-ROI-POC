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
using System.Collections.Specialized;

using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Admin.View.Reasons.StatusReasons
{
    /// <summary>
    /// Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class StatusReasonsEditor : AdminEditor
    {
        #region Methods

        /// <summary>
        /// Prepopulate the values in odp and mcp.
        /// </summary>
        public override void PrePopulate()
        {   
            StatusReasonsODP odp = (StatusReasonsODP)SubPanes[2];
            odp.PrePopulateStatus();
            
            StatusReasonsMCP mcp = (StatusReasonsMCP)SubPanes[1];
            mcp.PrePopulate();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the StatusReasonsEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "statusreason.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the StatusReasonsEditor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "statusreason.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(StatusReasonsMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return typeof(StatusReasonsODP); }
        }

        #endregion
    }
}
