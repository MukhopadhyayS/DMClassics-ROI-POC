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
using System.Drawing.Drawing2D;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public partial class ROIPanel : Panel
    {
        private int borderWidth = 1;
        private int shadowOffSet = 5;
        private int roundCornerRadius = 15;
        private Color borderColor = Color.Gray;

        public ROIPanel()
        {
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            this.SetStyle(ControlStyles.ResizeRedraw, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
            InitializeComponent();
        }

        public ROIPanel(IContainer container)
        {
            container.Add(this);

            InitializeComponent();
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaintBackground(e);

            int tmpShadowOffSet = Math.Min(Math.Min(shadowOffSet, this.Width - 2), this.Height - 2);
            int tmpSoundCornerRadius = Math.Min(Math.Min(roundCornerRadius, this.Width - 2), this.Height - 2);

            if (this.Width > 1 && this.Height > 1)
            {
                e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;

                Rectangle rect = new Rectangle(0, 0, this.Width - tmpShadowOffSet - 1,
                                               this.Height - tmpShadowOffSet - 1);

                Rectangle rectShadow = new Rectangle(tmpShadowOffSet, tmpShadowOffSet,
                                                     this.Width - tmpShadowOffSet - 1, this.Height - tmpShadowOffSet - 1);

                GraphicsPath graphPathShadow = PanelGraphics.GetRoundPath(rectShadow, tmpSoundCornerRadius);
                GraphicsPath graphPath = PanelGraphics.GetRoundPath(rect, tmpSoundCornerRadius);

                if (tmpSoundCornerRadius > 0)
                {
                    using (PathGradientBrush gBrush = new PathGradientBrush(graphPathShadow))
                    {
                        gBrush.WrapMode = WrapMode.Clamp;
                        ColorBlend colorBlend = new ColorBlend(3);
                        colorBlend.Colors = new Color[]{Color.Transparent,
                                                        Color.FromArgb(180, Color.DimGray),
                                                        Color.FromArgb(180, Color.DimGray)};

                        colorBlend.Positions = new float[] { 0f, .1f, 1f };

                        gBrush.InterpolationColors = colorBlend;
                        e.Graphics.FillPath(gBrush, graphPathShadow);
                    }
                }

                // Draw backgroup               
                SolidBrush brush = new SolidBrush(Color.FromArgb(236, 238, 245));

                e.Graphics.FillPath(brush, graphPath);
                e.Graphics.DrawPath(new Pen(Color.FromArgb(180, this.borderColor), borderWidth), graphPath);
            }
        }

        // PanelGraphics class
        private static class PanelGraphics
        {
            public static GraphicsPath GetRoundPath(Rectangle r, int depth)
            {
                GraphicsPath graphPath = new GraphicsPath();

                graphPath.AddArc(r.X, r.Y, depth, depth, 180, 90);
                graphPath.AddArc(r.X + r.Width - depth, r.Y, depth, depth, 270, 90);
                graphPath.AddArc(r.X + r.Width - depth, r.Y + r.Height - depth, depth, depth, 0, 90);
                graphPath.AddArc(r.X, r.Y + r.Height - depth, depth, depth, 90, 90);
                graphPath.AddLine(r.X, r.Y + r.Height - depth, r.X, r.Y + depth / 2);

                return graphPath;
            }
        }
    }
}
