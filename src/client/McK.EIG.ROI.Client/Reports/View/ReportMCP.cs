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

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportMCP
    /// </summary>
    public class ReportMCP : ROIBasePane
    {
        #region Fields

        private ReportViewerUI reportViewerUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize th Report Details UI.
        /// </summary>        
        protected override void InitView()
        {
            reportViewerUI = new ReportViewerUI();
        }

        /// <summary>
        /// Prepopulates filter options
        /// </summary>
        public void PrePopulate()
        {
            reportViewerUI.PrePopulate();
        }

        /// <summary>
        /// Set data
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            reportViewerUI.SetData(data);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of Report details MCP.
        /// </summary>        
        public override Component View
        {
            get { return reportViewerUI; }
        }

        #endregion

       
    }
}
