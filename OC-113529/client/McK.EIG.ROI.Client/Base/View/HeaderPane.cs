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
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Builds a generic Header.
    /// </summary>
    public class HeaderPane : ROIBasePane
    {
        #region Fields

        private HeaderUI headerUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the View.
        /// </summary>
        protected override void InitView()
        {
            headerUI = new HeaderUI();
            headerUI.Dock = DockStyle.Fill;
        }
     
        #endregion

        #region Properties

        /// <summary>
        /// Gets the view of Header.
        /// </summary>
        public override Component View
        {
            get { return headerUI; }
        }

        #endregion
    }
}
