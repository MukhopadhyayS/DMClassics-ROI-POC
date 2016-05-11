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
using McK.EIG.ROI.Client.Reports.Model;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportEditor
    /// </summary>
    public class ReportEditor : ROIReportEditor
    {
        #region Fields

        private ReportDetails reportDetails;

        #endregion

        #region Methods

        /// <summary>
        /// Returns all the children type of ROIEditor.
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            return new Type[] { MCPType };
        }

        public override void PrePopulate()
        {
            ((ReportMCP)MCP).PrePopulate();
            MCP.SetData(reportDetails);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds ReportMCP.
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(ReportMCP); }
        }

        /// <summary>
        /// Report Model Object
        /// </summary>
        public ReportDetails ReportDetails
        {
            get { return reportDetails; }
            set { reportDetails = value; }
        }
        #endregion 

    }
}
