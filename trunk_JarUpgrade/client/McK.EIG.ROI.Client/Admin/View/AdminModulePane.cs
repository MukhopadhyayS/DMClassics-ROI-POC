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
using System.Drawing;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Used to group AdminLSP and AdminRSP.
    /// </summary>
    public class AdminModulePane : ROIModulePane
    {

        #region Methods

        /// <summary>
        /// Returns all the Children type of  AdminModulePane.
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            return new Type[] { typeof(AdminLSP), 
                                typeof(AdminRSP) 
            };
        }

        public void ApplyDefault(string subModule)
        {
            AdminLSP lsp = (AdminLSP)SubPanes[0];
            if (!string.IsNullOrEmpty(subModule))
            {
                lsp.ApplyDefault(subModule);
            }
            else
            {
                base.ApplyDefault();
            }
        }

        #endregion
    }
}

