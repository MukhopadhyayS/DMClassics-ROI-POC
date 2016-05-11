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
using System.Drawing;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public class ROISplitContainer : SplitContainer
    {
        #region Constructor
        public ROISplitContainer(bool panel1Paint, bool panel2Paint)
        {
           
            this.SplitterWidth = 4;
            this.Dock = DockStyle.Fill;
            this.BackColor = Color.FromArgb(221, 231, 253);
            this.Panel1.BackColor = Color.FromArgb(255, 255, 255);
            this.Panel2.BackColor = Color.FromArgb(255, 255, 255);
            this.SplitterDistance = 40;
            this.Panel1.Padding = new Padding(1);
            this.Panel2.Padding = new Padding(1);
            this.TabIndex = 0;
            this.TabStop = false;
            if (panel1Paint == true)
            {
                this.Panel1.Paint += new PaintEventHandler(ROISplitBorderPaint);
            }
            if (panel2Paint == true)
            {
                this.Panel2.Paint += new PaintEventHandler(ROISplitBorderPaint);
            }

        }
        #endregion

        #region Methods
        internal void ROISplitBorderPaint(object sender, PaintEventArgs e)
        {
            Control tempPanel = (Control)sender;
            ControlPaint.DrawBorder(e.Graphics,
                                    tempPanel.ClientRectangle,
                                    Color.FromArgb(145, 167, 180),
                                    ButtonBorderStyle.Solid);
        }
        #endregion
    }
}
