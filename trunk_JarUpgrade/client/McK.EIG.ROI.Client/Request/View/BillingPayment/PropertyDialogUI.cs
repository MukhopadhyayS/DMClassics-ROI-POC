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
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    public partial class PropertyDialogUI : ROIBaseUI
    {
        #region Constructor

        private PropertyDialogUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public PropertyDialogUI(IPane pane): this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);                     
        }

        #endregion
    }
}
