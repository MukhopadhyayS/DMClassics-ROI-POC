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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Requestors.View.FindRequestor
{
    public partial class FindRequestorActionUI : ROIBaseUI
    {
        #region Constructor

        public FindRequestorActionUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods
        /// <summary>
        /// Localizes all the controls.
        /// </summary>        
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            SetLabel(rm, viewEditButton);
            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            
            SetTooltip(rm, toolTip, viewEditButton);
        }

        #endregion

        #region Properties

        public Button ViewEditButton
        {
            get { return viewEditButton; }
        }
        

        #endregion

    }
}
