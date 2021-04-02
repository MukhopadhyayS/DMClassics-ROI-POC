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
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Resources;
using System.Text;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public partial class AmountTextBoxClass : UserControl
    {
		#region Constructor
        public AmountTextBoxClass()
        {
            InitializeComponent();            
        }
		#endregion		

		#region Methods
        /// <summary>
        /// Restrict the user to enter only Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void amountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        private void amountTextBox_Enter(object sender, EventArgs e)
        {
            amountTextBox.Select(0, amountTextBox.Text.Length);
        }

        private void amountTextBox_Leave(object sender, EventArgs e)
        {
            this.alertImg.Visible = false;
        }        

		#endregion		

		#region Properties
        public PictureBox AlertImage
        {
            get
            {
                return alertImg;
            }
        }
        #endregion
    }
}
