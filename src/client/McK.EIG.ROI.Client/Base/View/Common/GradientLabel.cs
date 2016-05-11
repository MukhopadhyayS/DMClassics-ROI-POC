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

namespace McK.EIG.ROI.Client.Base.View.Common
{
    /// <summary>
    /// GradientLabel
    /// </summary>
    public partial class GradientLabel : LinkLabel
    {
        public const int LinkHeight = 23;

        internal static Image SelectedImage;

        private Color normalBorderColor;
        private Color selectedBorderColor;
        private Color gradientFrom;
        private Color gradientTo;

        private bool isSelected;

        public GradientLabel() : this(Color.White, Color.Orange, Color.FromArgb(145, 167, 180), Color.Orange) { }

        public GradientLabel(Color gradientFrom, Color gradientTo) : this(gradientFrom, gradientTo, Color.Blue, Color.Orange) { }

        public GradientLabel(Color gradientFrom, Color gradientTo, Color normalBorderColor, Color selectedBorderColor)
        {
            InitializeComponent();

            this.normalBorderColor = normalBorderColor;
            this.selectedBorderColor = selectedBorderColor;
            this.gradientFrom = gradientFrom;
            this.gradientTo = gradientTo;

            this.Height = LinkHeight;
            this.Padding = new Padding(0, 0, 0, 0);
            this.Margin = this.Padding;
            this.LinkBehavior = LinkBehavior.NeverUnderline;
            this.BackColor = Color.White;
            this.LinkColor = Color.Black;
            this.LinkArea = new LinkArea(0, 0);
            this.ActiveLinkColor = Color.Black;
            this.VisitedLinkColor = Color.Black;
            this.TextAlign = ContentAlignment.MiddleLeft;
            this.ImageAlign = ContentAlignment.MiddleLeft;
            this.AutoEllipsis = true;

            this.Paint += new PaintEventHandler(LinkBorderPaint);
            this.Resize += new EventHandler(LinkResize);
        }

        public Color NormalBorderColor
        {
            get { return normalBorderColor; }
            set { normalBorderColor = value; }
        }

        public Color SelectedBorderColor
        {
            get { return selectedBorderColor; }
            set { selectedBorderColor = value; }
        }

        public Color GradientFrom
        {
            get { return gradientFrom; }
            set { gradientFrom = value; }
        }

        public Color GradientTo
        {
            get { return gradientTo; }
            set { gradientTo = value; }
        }

        public bool IsSelected
        {
            set
            {
                isSelected = value;
                if (isSelected)
                {
                    this.Font = new Font("Arial", 9, FontStyle.Bold);
                    this.Image = SelectedImage;
                }
                else
                {
                    this.Font = new Font("Arial", 9, FontStyle.Regular);
                    this.Image = null;
                }
            }

            get
            {
                return isSelected;
            }
        }

        private void LinkResize(object sender, EventArgs e)
        {
            this.Invalidate();
        }

        private void LinkBorderPaint(object sender, PaintEventArgs e)
        {
            Control tempLink = (Control)sender;
            Color borderColor = (isSelected) ? selectedBorderColor : normalBorderColor;
            ControlPaint.DrawBorder(e.Graphics,
                                    tempLink.ClientRectangle,
                                    borderColor, 0, ButtonBorderStyle.Solid,
                                    borderColor, 0, ButtonBorderStyle.Solid,
                                    borderColor, 0, ButtonBorderStyle.Solid,
                                    borderColor, 1, ButtonBorderStyle.Solid);
        }

        protected override void OnPaintBackground(PaintEventArgs pevent)
        {
            if (isSelected)
            {
                LinearGradientBrush brush = new LinearGradientBrush(pevent.ClipRectangle, gradientFrom, gradientTo, LinearGradientMode.Horizontal);
                pevent.Graphics.FillRectangle(brush, pevent.ClipRectangle);

                if (Image != null)
                {
                    int x;
                    int y;
                    if (ImageAlign == ContentAlignment.MiddleLeft)
                    {
                        x = 10;
                        y = (pevent.ClipRectangle.Height - Image.Height) / 2;
                    }
                    else
                    { // assume MiddleRight
                        x = pevent.ClipRectangle.Width - Image.Width;
                        y = (pevent.ClipRectangle.Height - Image.Height) / 2;
                    }
                    pevent.Graphics.DrawImage(Image, x, y);
                }
            }
            else
            {
                base.OnPaintBackground(pevent);
            }
        }
    }
}



