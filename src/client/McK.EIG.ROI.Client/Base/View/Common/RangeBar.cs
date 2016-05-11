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
using System.Globalization;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    /// <summary>
	/// Summary description for UserControl1.
	/// </summary>
    public class RangeBar : System.Windows.Forms.UserControl
    {
        private System.ComponentModel.IContainer components;
        // delegate to handle range changed
        public delegate void RangeChangedEventHandler(object sender, EventArgs e);

        // delegate to handle range is changing
        public delegate void RangeChangingEventHandler(object sender, EventArgs e);

        public RangeBar()
        {
            // Dieser Aufruf ist für den Windows Form-Designer erforderlich.
            InitializeComponent();
        }

        /// <summary> 
        /// Die verwendeten Ressourcen bereinigen.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code
        /// <summary> 
        /// Erforderliche Methode für die Designerunterstützung. 
        /// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
        /// </summary>
        private void InitializeComponent()
        {
            this.panel1 = new System.Windows.Forms.Panel();
            this.maxTextBox = new System.Windows.Forms.TextBox();
            this.minTextBox = new System.Windows.Forms.TextBox();
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.label9 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            this.flowLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.maxTextBox);
            this.panel1.Controls.Add(this.minTextBox);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 40);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(211, 22);
            this.panel1.TabIndex = 35;
            // 
            // maxTextBox
            // 
            this.maxTextBox.Dock = System.Windows.Forms.DockStyle.Right;
            this.maxTextBox.Location = new System.Drawing.Point(171, 0);
            this.maxTextBox.MaxLength = 4;
            this.maxTextBox.Name = "maxTextBox";
            this.maxTextBox.Size = new System.Drawing.Size(40, 20);
            this.maxTextBox.TabIndex = 2;
            this.maxTextBox.Leave += new System.EventHandler(this.minTextBox_Leave);
            this.maxTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.maxTextBox_KeyPress);
            this.maxTextBox.Enter += new System.EventHandler(this.minTextBox_Leave);
            // 
            // minTextBox
            // 
            this.minTextBox.Dock = System.Windows.Forms.DockStyle.Left;
            this.minTextBox.Location = new System.Drawing.Point(0, 0);
            this.minTextBox.MaxLength = 3;
            this.minTextBox.Name = "minTextBox";
            this.minTextBox.Size = new System.Drawing.Size(31, 20);
            this.minTextBox.TabIndex = 1;
            this.minTextBox.Leave += new System.EventHandler(this.minTextBox_Leave);
            this.minTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.minTextBox_KeyPress);
            this.minTextBox.Enter += new System.EventHandler(this.minTextBox_Leave);
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.AutoSize = true;
            this.flowLayoutPanel1.Controls.Add(this.label9);
            this.flowLayoutPanel1.Controls.Add(this.label10);
            this.flowLayoutPanel1.Controls.Add(this.label11);
            this.flowLayoutPanel1.Controls.Add(this.label12);
            this.flowLayoutPanel1.Controls.Add(this.label13);
            this.flowLayoutPanel1.Controls.Add(this.label14);
            this.flowLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(211, 13);
            this.flowLayoutPanel1.TabIndex = 36;
            this.flowLayoutPanel1.WrapContents = false;
            // 
            // label9
            // 
            this.label9.Location = new System.Drawing.Point(3, 0);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(27, 13);
            this.label9.TabIndex = 35;
            this.label9.Text = "0";
            // 
            // label10
            // 
            this.label10.Location = new System.Drawing.Point(36, 0);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(30, 13);
            this.label10.TabIndex = 36;
            this.label10.Text = "30";
            // 
            // label11
            // 
            this.label11.Location = new System.Drawing.Point(72, 0);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(30, 13);
            this.label11.TabIndex = 37;
            this.label11.Text = "60";
            // 
            // label12
            // 
            this.label12.Location = new System.Drawing.Point(108, 0);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(30, 13);
            this.label12.TabIndex = 38;
            this.label12.Text = "90";
            // 
            // label13
            // 
            this.label13.Location = new System.Drawing.Point(144, 0);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(30, 13);
            this.label13.TabIndex = 39;
            this.label13.Text = "120";
            // 
            // label14
            // 
            this.label14.Location = new System.Drawing.Point(180, 0);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(32, 13);
            this.label14.TabIndex = 40;
            this.label14.Text = "120+";
            // 
            // RangeBar
            // 
            this.BackColor = System.Drawing.SystemColors.Window;
            this.Controls.Add(this.flowLayoutPanel1);
            this.Controls.Add(this.panel1);
            this.Name = "RangeBar";
            this.Size = new System.Drawing.Size(211, 62);
            this.Load += new System.EventHandler(this.OnLoad);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.OnPaint);
            this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.OnMouseMove);
            this.Leave += new System.EventHandler(this.OnLeave);
            this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.OnMouseDown);
            this.Resize += new System.EventHandler(this.OnResize);
            this.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.OnKeyPress);
            this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.OnMouseUp);
            this.SizeChanged += new System.EventHandler(this.OnSizeChanged);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.flowLayoutPanel1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void minTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            int KeyCode = (int)e.KeyChar;
            if (!(KeyCode >= 48 && KeyCode <= 57) && KeyCode != 8)
            {
                e.Handled = true;
            }
        }

        private void maxTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            int KeyCode = (int)e.KeyChar;
            if (!(KeyCode >= 48 && KeyCode <= 57) && KeyCode != 8)
            {
                e.Handled = true;
            }
        }

        #endregion

        public enum ActiveMarkType { None, Left, Right };
        public enum RangeBarOrientation { Horizontal, Vertical };
        public enum TopBottomOrientation { Top, Bottom, Both };

        private Color colorInner = Color.LightGreen;
        private Color colorRange = Color.FromKnownColor(KnownColor.Black);
        private Color colorShadowLight = Color.FromKnownColor(KnownColor.GrayText);
        private Color colorShadowDark = Color.FromKnownColor(KnownColor.ControlDarkDark);
        private int sizeShadow = 1;
        private double Minimum = 0;
        private double Maximum = 10;
        private double rangeMin = 3;
        private double rangeMax = 5;
        private ActiveMarkType ActiveMark = ActiveMarkType.None;


        private RangeBarOrientation orientationBar = RangeBarOrientation.Horizontal; // orientation of range bar
        private TopBottomOrientation orientationScale = TopBottomOrientation.Bottom;
        private int BarHeight = 8;		// Height of Bar
        private int MarkWidth = 8;		// Width of mark knobs
        private int MarkHeight = 24;	// total height of mark knobs
        private int TickHeight = 0;	// Height of axis tick
        private int numAxisDivision = 10;

        private int PosL = 0, PosR = 0;	// Pixel-Position of mark buttons
        private int XPosMin, XPosMax;

        private Point[] LMarkPnt = new Point[3];
        private Point[] RMarkPnt = new Point[3];

        private bool MoveLMark = false;
        private bool MoveRMark = false;

        double left;
        private Panel panel1;
        private TextBox maxTextBox;
        private TextBox minTextBox;
        private FlowLayoutPanel flowLayoutPanel1;
        private Label label14;
        private Label label13;
        private Label label12;
        private Label label11;
        private Label label10;
        private Label label9;
        double right;
        //------------------------------------
        // Properties
        //------------------------------------

        /// <summary>
        /// set or get tick height
        /// </summary>
        public int HeightOfTick
        {
            set
            {
                TickHeight = value;
                TickHeight = 0;
                Invalidate();
                Update();
            }
            get
            {
                return TickHeight;
            }
        }

        /// <summary>
        /// set or get mark knob height
        /// </summary>
        public int HeightOfMark
        {
            set
            {
                MarkHeight = Math.Max(BarHeight + 2, value);
                Invalidate();
                Update();
            }
            get
            {
                return MarkHeight;
            }
        }


        /// <summary>
        /// set/get height of mark
        /// </summary>
        public int HeightOfBar
        {
            set
            {
                BarHeight = Math.Min(value, MarkHeight - 2);
                Invalidate();
                Update();
            }
            get
            {
                return BarHeight;
            }

        }

        /// <summary>
        /// set or get range bar orientation
        /// </summary>
        public RangeBarOrientation Orientation
        {
            set
            {
                orientationBar = value;
                Invalidate();
                Update();
            }
            get
            {
                return orientationBar;
            }
        }

        /// <summary>
        /// set or get scale orientation
        /// </summary>
        public TopBottomOrientation ScaleOrientation
        {
            set
            {
                orientationScale = value;
                Invalidate();
                Update();
            }
            get
            {
                return orientationScale;
            }
        }

        /// <summary>
        ///  set or get right side of range
        /// </summary>
        public int RangeMaximum
        {
            set
            {
                rangeMax = (double)value;
                if (rangeMax < Minimum)
                    rangeMax = Minimum;
                else if (rangeMax > Maximum)
                    rangeMax = Maximum;
                if (rangeMax < rangeMin)
                    rangeMax = rangeMin;
                Range2Pos();
                Invalidate(true);
            }
            get { return (int)rangeMax; }
        }


        /// <summary>
        /// set or get left side of range
        /// </summary>
        public int RangeMinimum
        {
            set
            {
                rangeMin = (double)value;
                if (rangeMin < Minimum)
                    rangeMin = Minimum;
                else if (rangeMin > Maximum)
                    rangeMin = Maximum;
                if (rangeMin > rangeMax)
                    rangeMin = rangeMax;
                Range2Pos();
                Invalidate(true);
            }
            get
            {
                return (int)rangeMin;
            }
        }


        /// <summary>
        /// set or get right side of total range
        /// </summary>
        public int TotalMaximum
        {
            set
            {
                Maximum = (double)value;
                if (rangeMax > Maximum)
                    rangeMax = Maximum;
                Range2Pos();
                Invalidate(true);
            }
            get { return (int)Maximum; }
        }


        /// <summary>
        /// set or get left side of total range
        /// </summary>
        public int TotalMinimum
        {
            set
            {
                Minimum = (double)value;
                if (rangeMin < Minimum)
                    rangeMin = Minimum;
                Range2Pos();
                Invalidate(true);
            }
            get { return (int)Minimum; }
        }


        /// <summary>
        /// set or get number of divisions
        /// </summary>
        public int DivisionNumber
        {
            set
            {
                numAxisDivision = value;
                Refresh();
            }
            get { return numAxisDivision; }
        }


        /// <summary>
        /// set or get color of inner range
        /// </summary>
        public Color InnerColor
        {
            set
            {
                colorInner = value;
                Refresh();
            }
            get { return colorInner; }
        }


        /// <summary>
        /// set selected range
        /// </summary>
        /// <param name="left">left side of range</param>
        /// <param name="right">right side of range</param>
        public void SelectRange(int left, int right)
        {
            RangeMinimum = left;
            RangeMaximum = right;
            Range2Pos();
            Invalidate(true);
        }


        /// <summary>
        /// set range limits
        /// </summary>
        /// <param name="left">left side of range limit</param>
        /// <param name="right">right side of range limit</param>
        public void SetRangeLimit(double left, double right)
        {
            Minimum = left;
            Maximum = right;
            Range2Pos();
            Invalidate(true);
        }


        // paint event reaction
        private void OnPaint(object sender, System.Windows.Forms.PaintEventArgs e)
        {
            int h = this.Height;
            int w = this.Width;
            int baryoff, markyoff, tickyoff1, tickyoff2;
            double dtick;
            int tickpos;
            Pen penShadowLight = new Pen(colorShadowLight);
            Pen penShadowDark = new Pen(colorShadowDark);
            SolidBrush brushShadowLight = new SolidBrush(colorShadowLight);
            SolidBrush brushShadowDark = new SolidBrush(colorShadowDark);
            SolidBrush brushInner;
            SolidBrush brushRange = new SolidBrush(colorRange);
            SolidBrush brushFill = new SolidBrush(Color.LightGray);

            if (this.Enabled == true)
                brushInner = new SolidBrush(colorInner);
            else
                brushInner = new SolidBrush(Color.FromKnownColor(KnownColor.GrayText));

            // range
            XPosMin = MarkWidth + 1;
            if (this.orientationBar == RangeBarOrientation.Horizontal)
                XPosMax = w - MarkWidth - 1;
            else
                XPosMax = h - MarkWidth - 1;

            // range check
            if (PosL < XPosMin) PosL = XPosMin;
            if (PosL > XPosMax) PosL = XPosMax;
            if (PosR > XPosMax) PosR = XPosMax;
            if (PosR < XPosMin) PosR = XPosMin;

            Range2Pos();


            if (this.orientationBar == RangeBarOrientation.Horizontal)
            {

                baryoff = (h - BarHeight) / 2;
                markyoff = baryoff + (BarHeight - MarkHeight) / 2 - 1;

                // total range bar frame			
                e.Graphics.FillRectangle(brushShadowLight, 0, baryoff, w - 1, sizeShadow);	// top
                e.Graphics.FillRectangle(brushShadowLight, 0, baryoff, sizeShadow, BarHeight - 1);	// left
                e.Graphics.FillRectangle(brushShadowLight, 0, baryoff + BarHeight - 1 - sizeShadow, w - 1, sizeShadow);	// bottom
                e.Graphics.FillRectangle(brushShadowLight, w - 1 - sizeShadow, baryoff, sizeShadow, BarHeight - 1);	// right

                e.Graphics.FillRectangle(brushFill, 0, baryoff, w - 1, BarHeight - 1);


                // inner region
                e.Graphics.FillRectangle(brushInner, PosL, baryoff + sizeShadow, PosR - PosL, BarHeight - 1 - 2 * sizeShadow);

                // Skala
                if (orientationScale == TopBottomOrientation.Bottom)
                {
                    tickyoff1 = tickyoff2 = baryoff + BarHeight + 2;
                }
                else if (orientationScale == TopBottomOrientation.Top)
                {
                    tickyoff1 = tickyoff2 = baryoff - TickHeight - 4;
                }
                else
                {
                    tickyoff1 = baryoff + BarHeight + 2;
                    tickyoff2 = baryoff - TickHeight - 4;
                }

                if (numAxisDivision > 1)
                {
                    dtick = (double)(XPosMax - XPosMin) / (double)numAxisDivision;
                    for (int i = 0; i < numAxisDivision + 1; i++)
                    {
                        tickpos = (int)Math.Round((double)i * dtick);
                        if (orientationScale == TopBottomOrientation.Bottom
                            || orientationScale == TopBottomOrientation.Both)
                        {
                            e.Graphics.DrawLine(penShadowDark, MarkWidth + 1 + tickpos,
                                tickyoff1,
                                MarkWidth + 1 + tickpos,
                                tickyoff1 + TickHeight);
                        }
                        if (orientationScale == TopBottomOrientation.Top
                            || orientationScale == TopBottomOrientation.Both)
                        {
                            e.Graphics.DrawLine(penShadowDark, MarkWidth + 1 + tickpos,
                                tickyoff2,
                                MarkWidth + 1 + tickpos,
                                tickyoff2 + TickHeight);
                        }
                    }
                }

                // left mark knob				
                LMarkPnt[0].X = ((2 * PosL) - MarkWidth) / 2; LMarkPnt[0].Y = markyoff - 5;
                LMarkPnt[1].X = PosL; LMarkPnt[1].Y = markyoff + MarkHeight - 5;
                LMarkPnt[2].X = PosL - MarkWidth; LMarkPnt[2].Y = markyoff + MarkHeight - 5;

                e.Graphics.FillPolygon(brushRange, LMarkPnt);

                // right mark knob
                RMarkPnt[0].X = ((2 * PosR) + MarkWidth) / 2; RMarkPnt[0].Y = markyoff - 5;
                RMarkPnt[1].X = PosR; RMarkPnt[1].Y = markyoff + MarkHeight - 5;
                RMarkPnt[2].X = PosR + MarkWidth; RMarkPnt[2].Y = markyoff + MarkHeight - 5;
                e.Graphics.FillPolygon(brushRange, RMarkPnt);

                if (MoveLMark)
                {
                    left = rangeMin;
                }

                if (MoveRMark)
                {
                    right = rangeMax;
                }

            }
            else // vertical bar
            {
                baryoff = (w + BarHeight) / 2;
                markyoff = baryoff - BarHeight / 2 - MarkHeight / 2;
                if (orientationScale == TopBottomOrientation.Bottom)
                {
                    tickyoff1 = tickyoff2 = baryoff + 2;
                }
                else if (orientationScale == TopBottomOrientation.Top)
                {
                    tickyoff1 = tickyoff2 = baryoff - BarHeight - 2 - TickHeight;
                }
                else
                {
                    tickyoff1 = baryoff + 2;
                    tickyoff2 = baryoff - BarHeight - 2 - TickHeight;
                }

                // total range bar frame			
                e.Graphics.FillRectangle(brushShadowDark, baryoff - BarHeight, 0, BarHeight, sizeShadow);	// top
                e.Graphics.FillRectangle(brushShadowDark, baryoff - BarHeight, 0, sizeShadow, h - 1);	// left				
                e.Graphics.FillRectangle(brushShadowLight, baryoff, 0, sizeShadow, h - 1);	// right
                e.Graphics.FillRectangle(brushShadowLight, baryoff - BarHeight, h - sizeShadow, BarHeight, sizeShadow);	// bottom

                // inner region
                e.Graphics.FillRectangle(brushInner, baryoff - BarHeight + sizeShadow, PosL, BarHeight - 2 * sizeShadow, PosR - PosL);

                // Skala
                if (numAxisDivision > 1)
                {
                    dtick = (double)(XPosMax - XPosMin) / (double)numAxisDivision;
                    for (int i = 0; i < numAxisDivision + 1; i++)
                    {
                        tickpos = (int)Math.Round((double)i * dtick);
                        if (orientationScale == TopBottomOrientation.Bottom
                            || orientationScale == TopBottomOrientation.Both)
                            e.Graphics.DrawLine(penShadowDark,
                                tickyoff1,
                                MarkWidth + 1 + tickpos,
                                tickyoff1 + TickHeight,
                                MarkWidth + 1 + tickpos
                                );
                        if (orientationScale == TopBottomOrientation.Top
                            || orientationScale == TopBottomOrientation.Both)
                            e.Graphics.DrawLine(penShadowDark,
                                tickyoff2,
                                MarkWidth + 1 + tickpos,
                                tickyoff2 + TickHeight,
                                MarkWidth + 1 + tickpos
                                );
                    }
                }

                // left(upper) mark knob				
                LMarkPnt[0].Y = PosL - MarkWidth; LMarkPnt[0].X = markyoff + MarkHeight / 3;
                LMarkPnt[1].Y = PosL; LMarkPnt[1].X = markyoff;
                LMarkPnt[2].Y = PosL; LMarkPnt[2].X = markyoff + MarkHeight;
                LMarkPnt[3].Y = PosL - MarkWidth; LMarkPnt[3].X = markyoff + 2 * MarkHeight / 3;
                LMarkPnt[4].Y = PosL - MarkWidth; LMarkPnt[4].X = markyoff;
                e.Graphics.FillPolygon(brushRange, LMarkPnt);
                e.Graphics.DrawLine(penShadowDark, LMarkPnt[3].X, LMarkPnt[3].Y, LMarkPnt[2].X, LMarkPnt[2].Y); // right shadow
                e.Graphics.DrawLine(penShadowLight, LMarkPnt[0].X - 1, LMarkPnt[0].Y, LMarkPnt[3].X - 1, LMarkPnt[3].Y); // top shadow				
                e.Graphics.DrawLine(penShadowLight, LMarkPnt[0].X - 1, LMarkPnt[0].Y, LMarkPnt[1].X - 1, LMarkPnt[1].Y); // left shadow
                if (PosL < PosR)
                    e.Graphics.DrawLine(penShadowDark, LMarkPnt[1].X, LMarkPnt[1].Y, LMarkPnt[2].X, LMarkPnt[2].Y); // lower shadow
                if (ActiveMark == ActiveMarkType.Left)
                {
                    e.Graphics.DrawLine(penShadowLight, markyoff + MarkHeight / 3, PosL - MarkWidth / 2, markyoff + 2 * MarkHeight / 3, PosL - MarkWidth / 2); // active mark
                    e.Graphics.DrawLine(penShadowDark, markyoff + MarkHeight / 3, PosL - MarkWidth / 2 + 1, markyoff + 2 * MarkHeight / 3, PosL - MarkWidth / 2 + 1); // active mark			
                }

                // right mark knob
                RMarkPnt[0].Y = PosR + MarkWidth; RMarkPnt[0].X = markyoff + MarkHeight / 3;
                RMarkPnt[1].Y = PosR; RMarkPnt[1].X = markyoff;
                RMarkPnt[2].Y = PosR; RMarkPnt[2].X = markyoff + MarkHeight;
                RMarkPnt[3].Y = PosR + MarkWidth; RMarkPnt[3].X = markyoff + 2 * MarkHeight / 3;
                RMarkPnt[4].Y = PosR + MarkWidth; RMarkPnt[4].X = markyoff;
                e.Graphics.FillPolygon(brushRange, RMarkPnt);
                e.Graphics.DrawLine(penShadowDark, RMarkPnt[2].X, RMarkPnt[2].Y, RMarkPnt[3].X, RMarkPnt[3].Y); // lower right shadow
                e.Graphics.DrawLine(penShadowDark, RMarkPnt[0].X, RMarkPnt[0].Y, RMarkPnt[1].X, RMarkPnt[1].Y); // upper shadow
                e.Graphics.DrawLine(penShadowDark, RMarkPnt[0].X, RMarkPnt[0].Y, RMarkPnt[3].X, RMarkPnt[3].Y); // right shadow
                if (PosL < PosR)
                    e.Graphics.DrawLine(penShadowLight, RMarkPnt[1].X, RMarkPnt[1].Y, RMarkPnt[2].X, RMarkPnt[2].Y); // left shadow
                if (ActiveMark == ActiveMarkType.Right)
                {
                    e.Graphics.DrawLine(penShadowLight, markyoff + MarkHeight / 3, PosR + MarkWidth / 2 - 1, markyoff + 2 * MarkHeight / 3, PosR + MarkWidth / 2 - 1); // active mark
                    e.Graphics.DrawLine(penShadowDark, markyoff + MarkHeight / 3, PosR + MarkWidth / 2, markyoff + 2 * MarkHeight / 3, PosR + MarkWidth / 2); // active mark				
                }

                if (MoveLMark)
                {
                    Font fontMark = new Font("Arial", MarkWidth);
                    SolidBrush brushMark = new SolidBrush(colorShadowDark);
                    StringFormat strformat = new StringFormat();
                    strformat.Alignment = StringAlignment.Near;
                    strformat.LineAlignment = StringAlignment.Center;
                    e.Graphics.DrawString(rangeMin.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), fontMark, brushMark, tickyoff1 + TickHeight + 2, PosL, strformat);
                    left = rangeMin;
                }

                if (MoveRMark)
                {
                    Font fontMark = new Font("Arial", MarkWidth);
                    SolidBrush brushMark = new SolidBrush(colorShadowDark);
                    StringFormat strformat = new StringFormat();
                    strformat.Alignment = StringAlignment.Near;
                    strformat.LineAlignment = StringAlignment.Center;
                    e.Graphics.DrawString(rangeMax.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), fontMark, brushMark, tickyoff1 + TickHeight, PosR, strformat);
                    right = rangeMax;
                }
            }
            minTextBox.Text = (RangeMinimum > 120) ? "120" : RangeMinimum.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            maxTextBox.Text = (RangeMaximum > 120) ? "120+" : RangeMaximum.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            
        }


        // mouse down event
        private void OnMouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            if (this.Enabled)
            {
                Rectangle LMarkRect = new Rectangle(
                    Math.Min(LMarkPnt[0].X, LMarkPnt[1].X),		// X
                    Math.Min(LMarkPnt[0].Y, LMarkPnt[0].Y),		// Y
                    Math.Abs(LMarkPnt[2].X - LMarkPnt[0].X),		// width
                    Math.Max(Math.Abs(LMarkPnt[0].Y - LMarkPnt[0].Y), Math.Abs(LMarkPnt[0].Y - LMarkPnt[1].Y)));	// height
                Rectangle RMarkRect = new Rectangle(
                    Math.Min(RMarkPnt[0].X, RMarkPnt[2].X),		// X
                    Math.Min(RMarkPnt[0].Y, RMarkPnt[1].Y),		// Y
                    Math.Abs(RMarkPnt[0].X - RMarkPnt[2].X),		// width
                    Math.Max(Math.Abs(RMarkPnt[2].Y - RMarkPnt[0].Y), Math.Abs(RMarkPnt[1].Y - RMarkPnt[0].Y)));		// height

                if (LMarkRect.Contains(e.X, e.Y))
                {
                    this.Capture = true;
                    MoveLMark = true;
                    ActiveMark = ActiveMarkType.Left;
                    Invalidate(true);
                }

                if (RMarkRect.Contains(e.X, e.Y))
                {
                    this.Capture = true;
                    MoveRMark = true;
                    ActiveMark = ActiveMarkType.Right;
                    Invalidate(true);
                }
            }
        }


        // mouse up event
        private void OnMouseUp(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            if (this.Enabled)
            {
                this.Capture = false;

                MoveLMark = false;
                MoveRMark = false;

                Invalidate();

                OnRangeChanged(EventArgs.Empty);
            }
        }


        // mouse move event
        private void OnMouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            if (this.Enabled)
            {                
                Rectangle LMarkRect = new Rectangle(
                    Math.Min(LMarkPnt[0].X, LMarkPnt[1].X),		// X
                    Math.Min(LMarkPnt[0].Y, LMarkPnt[0].Y),		// Y
                    Math.Abs(LMarkPnt[2].X - LMarkPnt[0].X),		// width
                    Math.Max(Math.Abs(LMarkPnt[0].Y - LMarkPnt[0].Y), Math.Abs(LMarkPnt[0].Y - LMarkPnt[1].Y)));	// height
                Rectangle RMarkRect = new Rectangle(
                    Math.Min(RMarkPnt[0].X, RMarkPnt[2].X),		// X
                    Math.Min(RMarkPnt[0].Y, RMarkPnt[1].Y),		// Y
                    Math.Abs(RMarkPnt[0].X - RMarkPnt[2].X),		// width
                    Math.Max(Math.Abs(RMarkPnt[2].Y - RMarkPnt[0].Y), Math.Abs(RMarkPnt[1].Y - RMarkPnt[0].Y)));		// height

                if (LMarkRect.Contains(e.X, e.Y) || RMarkRect.Contains(e.X, e.Y))
                {
                    if (this.orientationBar == RangeBarOrientation.Horizontal)
                        this.Cursor = Cursors.SizeWE;
                    else
                        this.Cursor = Cursors.SizeNS;
                }
                else this.Cursor = Cursors.Arrow;

                if (MoveLMark)
                {
                    if (this.orientationBar == RangeBarOrientation.Horizontal)
                        this.Cursor = Cursors.SizeWE;
                    else
                        this.Cursor = Cursors.SizeNS;
                    if (this.orientationBar == RangeBarOrientation.Horizontal)
                        PosL = e.X;
                    else
                        PosL = e.Y;
                    if (PosL < XPosMin)
                        PosL = XPosMin;
                    if (PosL > XPosMax)
                        PosL = XPosMax;
                    if (PosR < PosL)
                        PosR = PosL;
                    Pos2Range();
                    ActiveMark = ActiveMarkType.Left;
                    Invalidate(true);

                    OnRangeChanging(EventArgs.Empty);
                }
                else if (MoveRMark)
                {
                    if (this.orientationBar == RangeBarOrientation.Horizontal)
                        this.Cursor = Cursors.SizeWE;
                    else
                        this.Cursor = Cursors.SizeNS;
                    if (this.orientationBar == RangeBarOrientation.Horizontal)
                        PosR = e.X;
                    else
                        PosR = e.Y;
                    if (PosR > XPosMax)
                        PosR = XPosMax;
                    if (PosR < XPosMin)
                        PosR = XPosMin;
                    if (PosL > PosR)
                        PosL = PosR;
                    Pos2Range();
                    ActiveMark = ActiveMarkType.Right;
                    Invalidate(true);

                    OnRangeChanging(EventArgs.Empty);
                }
            }
        }


        /// <summary>
        ///  transform pixel position to range position
        /// </summary>
        private void Pos2Range()
        {
            int w;
            int posw;

            if (this.orientationBar == RangeBarOrientation.Horizontal)
                w = this.Width;
            else
                w = this.Height;
            posw = w - 2 * MarkWidth - 2;

            rangeMin = Minimum + (int)Math.Round((double)(Maximum - Minimum) * (double)(PosL - XPosMin) / (double)posw);
            rangeMax = Minimum + (int)Math.Round((double)(Maximum - Minimum) * (double)(PosR - XPosMin) / (double)posw);
        }


        /// <summary>
        ///  transform range position to pixel position
        /// </summary>
        private void Range2Pos()
        {
            int w;
            int posw;

            if (this.orientationBar == RangeBarOrientation.Horizontal)
                w = this.Width;
            else
                w = this.Height;
            posw = w - 2 * MarkWidth - 2;

            PosL = XPosMin + (int)Math.Round((double)posw * (double)(rangeMin - Minimum) / (double)(Maximum - Minimum));
            PosR = XPosMin + (int)Math.Round((double)posw * (double)(rangeMax - Minimum) / (double)(Maximum - Minimum));
        }


        /// <summary>
        /// method to handle resize event
        /// </summary>
        /// <param name="sender">object that sends event to resize</param>
        /// <param name="e">event parameter</param>
        private void OnResize(object sender, System.EventArgs e)
        {
            //Range2Pos();
            Invalidate(true);
        }


        /// <summary>
        /// method to handle key pressed event
        /// </summary>
        /// <param name="sender">object that sends key pressed event</param>
        /// <param name="e">event parameter</param>
        private void OnKeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
        {
            if (this.Enabled)
            {
                if (ActiveMark == ActiveMarkType.Left)
                {
                    if (e.KeyChar == '+')
                    {
                        rangeMin++;
                        if (rangeMin > Maximum)
                            rangeMin = Maximum;
                        if (rangeMax < rangeMin)
                            rangeMax = rangeMin;
                        OnRangeChanged(EventArgs.Empty);
                    }
                    else if (e.KeyChar == '-')
                    {
                        rangeMin--;
                        if (rangeMin < Minimum)
                            rangeMin = Minimum;
                        OnRangeChanged(EventArgs.Empty);
                    }
                }
                else if (ActiveMark == ActiveMarkType.Right)
                {
                    if (e.KeyChar == '+')
                    {
                        rangeMax++;
                        if (rangeMax > Maximum)
                            rangeMax = Maximum;
                        OnRangeChanged(EventArgs.Empty);
                    }
                    else if (e.KeyChar == '-')
                    {
                        rangeMax--;
                        if (rangeMax < Minimum)
                            rangeMax = Minimum;
                        if (rangeMax < rangeMin)
                            rangeMin = rangeMax;
                        OnRangeChanged(EventArgs.Empty);
                    }
                }
                Invalidate(true);
            }
        }


        private void OnLoad(object sender, System.EventArgs e)
        {
            // use double buffering
            SetStyle(ControlStyles.DoubleBuffer, true);
            SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            SetStyle(ControlStyles.UserPaint, true);
            this.RangeMaximum = 0;
            this.RangeMinimum = 0;
            this.minTextBox.Text = this.maxTextBox.Text = "0";
            this.HeightOfMark = 15;
            this.TotalMaximum = 160;
            this.InnerColor = Color.CornflowerBlue;
            this.DivisionNumber = 5;
        }

        private void OnSizeChanged(object sender, System.EventArgs e)
        {
            Invalidate(true);
            Update();
        }

        private void OnLeave(object sender, System.EventArgs e)
        {
            ActiveMark = ActiveMarkType.None;
        }


        public event RangeChangedEventHandler RangeChanged; // event handler for range changed
        public event RangeChangedEventHandler RangeChanging; // event handler for range is changing

        public virtual void OnRangeChanged(EventArgs e)
        {
            if (RangeChanged != null)
                RangeChanged(this, e);
            if (RangeMaximum > 120)
            {
                RangeMaximum = 160;
            }
        }

        public virtual void OnRangeChanging(EventArgs e)
        {
            if (RangeChanging != null)
                RangeChanging(this, e);
        }

        public TextBox MaxTextBox
        {
            get { return maxTextBox; }
        }

        public TextBox MinTextBox
        {
            get { return minTextBox; }
        }

        public double Left
        {
            get { return left; }
            set { left = value; }
        }

        public double Right
        {
            get { return right; }
            set { right = value; }
        }

        private void minTextBox_Leave(object sender, EventArgs e)
        {
            int min, max;
            try
            {
                min = int.Parse(minTextBox.Text, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (min > 120)
                    min = 120;
                this.RangeMinimum = min;
            }
            catch (FormatException)
            {
                min = minTextBox.Text != "120+" ? 0 : 120;
            }
            try
            {
                max = int.Parse(maxTextBox.Text, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (max > 120)
                {
                    maxTextBox.Text = "120+";
					//CR#359254
                    max = 160;
                }
                if (max >= min)
                    this.RangeMaximum = max;
            }
            catch (FormatException)
            {
                maxTextBox.Text = "120+";
            }
        }
    }
}
