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
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// Reports LSP.
    /// </summary>
    public class ReportLSP : ROILeftSidePane
    {
        #region Fields

        private EventHandler navigationHandler;

        #endregion

        #region Methods

        /// <summary>
        /// Returns all the children type of LSP.
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            return new Type[] { typeof(ReportPane) };
        }

        internal override bool CanProcess(object sender, ApplicationEventArgs ae)
        {
            return (sender is ReportPane);
        }

        protected override void Subscribe()
        {
            navigationHandler = new EventHandler(OnNavigationChange);
            ROIEvents.NavigationChange += navigationHandler;
        }

        protected override void Unsubscribe()
        {
            ROIEvents.NavigationChange -= navigationHandler;
        }

        private void OnNavigationChange(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = e as ApplicationEventArgs;
            if (!CanProcess(SubPanes[0], ae)) return;
            string selectedReport = ae.Info as string;
            if (selectedReport.StartsWith(ROIConstants.ReportsModuleName))
            {
                ((CollapsibleBar)View).Expand(0);
                (SubPanes[0].View as ReportUI).SelectReport(selectedReport);
            }
        }

        public void ApplyDefault(string subModule)
        {
            if (subModule.StartsWith(ROIConstants.ReportsModuleName))
            {
                ((CollapsibleBar)View).Expand(0);
                (SubPanes[0].View as ReportUI).SelectReport(subModule);
            }
        }

        #endregion

    }
}
