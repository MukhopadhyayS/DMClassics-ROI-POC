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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Used to group collection of Navigation links.
    /// </summary>
    public class ReasonsMaintenancePane : NavigationPane
    {
        #region Methods

        protected override void ProcessNavigation(string selected, ApplicationEventArgs ae)
        {
            if (selected == ROIConstants.AdminReasonRequest)
            {
                AdminEvents.OnNavigateReasonRequest(this, ae);
            }
            else if (selected == ROIConstants.AdminReasonAdjustment)
            {
                AdminEvents.OnNavigateReasonAdjustment(this, ae);
            }
            else if (selected == ROIConstants.AdminReasonRequestStatus)
            {
                AdminEvents.OnNavigateReasonStatus(this, ae);
            }
        }

        #endregion

        #region Properties

        protected override Collection<string> LinkKeys
        {
            get
            {
                Collection<string> links = new Collection<string>();                
                links.Add(ROIConstants.AdminReasonAdjustment);
                links.Add(ROIConstants.AdminReasonRequest);
                links.Add(ROIConstants.AdminReasonRequestStatus);
                return links;
            }
        }

        public override string Title
        {
            get { return ROIConstants.AdminReasons; }
        }

        #endregion
    }
}
