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

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// UpperComponent
    /// </summary>
    public partial class UpperComponent : UserControl
    {
        #region Constructor

        public UpperComponent()
        {
            InitializeComponent();
            this.GotFocus += new EventHandler(UpperComponent_GotFocus);
        }

        /// <summary>
        /// When user control got focus, if all child controls of this are disabled then the focus takes to next active control of the parent form. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        void UpperComponent_GotFocus(object sender, EventArgs e)
        {
            if (this.ParentForm == null) return;
            this.ParentForm.SelectNextControl(this.ParentForm.ActiveControl, true, true, true, true);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the footer panel
        /// </summary>
        public Panel FooterPanel
        {
            get { return footerPanel; }
        }

        public Panel BottomPanel
        {
            get { return bottomPanel; }
        }

        /// <summary>
        /// Gets the MCP panel
        /// </summary>
        public Panel MCPPanel
        {
            get { return mcpPanel; }
        }

        /// <summary>
        /// Gets the Header panel
        /// </summary>
        public Panel HeaderPanel
        {
            get { return headerPanel; }
        }

        #endregion
    }
}
