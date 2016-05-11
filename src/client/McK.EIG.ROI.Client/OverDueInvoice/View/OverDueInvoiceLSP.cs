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

namespace McK.EIG.ROI.Client.OverDueInvoice.View
{
    /// <summary>
    /// PastDueInvoiceLSP
    /// </summary>
    public class OverDueInvoiceLSP : ROILeftSidePane
    {
        #region Methods

        protected override Type[] GetChildrenTypes()
        {
            return new Type[] { typeof(OverDueInvoiceNavigationPane) };
        }

        internal override bool CanProcess(object sender, ApplicationEventArgs ae)
        {
            if ((sender is OverDueInvoiceNavigationPane)) return true;

            string action = ae.Info as string;
            return (sender is MenuPane && action.StartsWith(ROIConstants.OverDueInvoiceModuleName));
        }

        #endregion
    }
}
