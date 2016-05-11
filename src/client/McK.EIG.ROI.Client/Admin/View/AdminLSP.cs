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

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// AdminLSP is a logical container which holds LSP Panes    
    /// </summary>
    public class AdminLSP : ROILeftSidePane
    {

        #region Methods
        /// <summary>
        /// Returns all the children type of LSP.        
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            return new Type[] { typeof(BillingMaintenancePane), 
                                typeof(ReasonsMaintenancePane),
                                typeof(OtherMaintenancePane),
                               // typeof(ConfigurationPane)
            };
        }

        internal override bool CanProcess(object sender, ApplicationEventArgs ae)
        {
            // verify admin module actions
            bool result = (sender is BillingMaintenancePane
                           || sender is ReasonsMaintenancePane
                           || sender is OtherMaintenancePane
                           || sender is ConfigurationPane);

            if (result) return true;

            // verify global actions
            string action = ae.Info as string;
            return (sender is MenuPane && action.StartsWith(ROIConstants.AdminModuleName));
        }

        public void ApplyDefault(string subModule)
        {
            if (subModule.StartsWith(ROIConstants.AdminBilling))
            {
                ((CollapsibleBar)View).Expand(0);
                (SubPanes[0] as BillingMaintenancePane).ApplyDefault(subModule);
            }
            else if (subModule.StartsWith(ROIConstants.AdminReasons))
            {
                ((CollapsibleBar)View).Expand(1);
                (SubPanes[1] as ReasonsMaintenancePane).ApplyDefault(subModule);
            }
            else if (subModule.StartsWith(ROIConstants.AdminOthers))
            {
                ((CollapsibleBar)View).Expand(2);
                (SubPanes[2] as OtherMaintenancePane).ApplyDefault(subModule);
            }
            else if (subModule.StartsWith(ROIConstants.AdminConfiguration))
            {
                ((CollapsibleBar)View).Expand(3);
                (SubPanes[3] as ConfigurationPane).ApplyDefault(subModule);
            }
        }

        #endregion

    }
}
