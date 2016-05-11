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
using System.Drawing.Drawing2D;
using System.Windows.Forms;
using System.ComponentModel;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    /// <summary>
    /// ROILabel
    /// </summary>
    public partial class ROILabel : Label
    {
        private Color normalBorderColor;
        private Color gradientFrom;
        private Color gradientTo;
        private LinearGradientMode flowDirection;

        public ROILabel()
        {
            InitializeComponent();
            this.Padding = new Padding(0, 0, 0, 0);
            this.Margin = this.Padding;
            this.TextAlign = ContentAlignment.MiddleLeft;
            this.AutoEllipsis = true;
            this.Dock = DockStyle.Fill;
            this.AutoSize = false;
        }

        public ROILabel(Color gradientFrom, Color gradientTo)
            : this()
        {
            this.gradientFrom = gradientFrom;
            this.gradientTo = gradientTo;
        }


        [DefaultValue("Color.FromArgb(234, 234, 234)")]
        public Color NormalBorderColor
        {
            get { return normalBorderColor; }
            set { normalBorderColor = value; }
        }
        
        [DefaultValue("Color.FromArgb(255, 255, 255)")]
        public Color GradientFrom
        {
            get { return gradientFrom; }
            set { gradientFrom = value; this.Invalidate(); }
        }

        [DefaultValue("Color.FromArgb(234, 234, 234)")]
        public Color GradientTo
        {
            get { return gradientTo; }
            set { gradientTo = value; this.Invalidate(); }
        }

        public LinearGradientMode FlowDirection
        {
            get { return flowDirection; }
            set { flowDirection = value; this.Invalidate(); }
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            Rectangle rect = new Rectangle(0, 0, this.Width, this.Height);
            LinearGradientBrush gradBrush = new LinearGradientBrush(rect,
                                                                    gradientFrom,
                                                                    gradientTo,
                                                                    FlowDirection);
            Graphics g = e.Graphics;
            g.FillRectangle(gradBrush, rect);
            Brush b = Brushes.Black;

            e.Graphics.DrawRectangle(new Pen(Color.LightGray),
                                     e.ClipRectangle.Left,
                                     e.ClipRectangle.Top,
                                     e.ClipRectangle.Width - 1,
                                     e.ClipRectangle.Height);

            g.DrawString(Text, Font, b, new Point(Location.X, Location.Y + (this.Height/4)));  
        }


       
    }
}



