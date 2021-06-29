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
using System.Windows.Forms;
using System.Windows.Forms.VisualStyles;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    /// <summary>
    /// GroupBox control that provides functionality to 
    /// allow it to be collapsed.
    /// </summary>
    [ToolboxBitmap(typeof(CollapsibleGroupBoxEX))]
    public partial class CollapsibleGroupBoxEX : GroupBox
    {
        #region Fields

        private Rectangle mToggleRect;
        private Boolean mCollapsed;
        private Boolean mBResizingFromCollapse;

        private const int mCollapsedHeight = 20;
        private Size mFullSize;

        #endregion

        #region Events & Delegates

        /// <summary>Fired when the Collapse Toggle button is pressed</summary>
        //public delegate void CollapseBoxClickedEventHandler(object sender);
        public event EventHandler CollapseBoxClickedEvent;

        #endregion

        #region Constructor

        public CollapsibleGroupBoxEX()
        {
            mToggleRect = new Rectangle(8, 2, 11, 11);
        }

        #endregion

        #region Public Properties

        [Browsable(false), DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        public int FullHeight
        {
            get { return mFullSize.Height; }
        }

        [DefaultValue(false), Browsable(false), DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        public bool IsCollapsed
        {
            get { return mCollapsed; }
            set
            {
                if (value != mCollapsed)
                {
                    mCollapsed = value;

                    if (!value)
                        // Expand
                        this.Size = mFullSize;
                    else
                    {
                        // Collapse
                        mBResizingFromCollapse = true;
                        this.Height = mCollapsedHeight;
                        mBResizingFromCollapse = false;
                    }

                    foreach (Control c in Controls)
                    {
                        c.Visible = !value;
                    }

                    Invalidate();
                }
            }
        }

        #endregion

        #region Overrides

        protected override void OnMouseUp(MouseEventArgs e)
        {
            if (mToggleRect.Contains(e.Location))
                ToggleCollapsed();
            else
                base.OnMouseUp(e);
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            HandleResize();
            DrawGroupBox(e.Graphics);
            DrawToggleButton(e.Graphics);
        }

        #endregion

        #region Implementation

        void DrawGroupBox(Graphics g)
        {
            // Get windows to draw the GroupBox
            Rectangle bounds = new Rectangle(ClientRectangle.X, ClientRectangle.Y + 6, ClientRectangle.Width, ClientRectangle.Height - 6);
            GroupBoxRenderer.DrawGroupBox(g, bounds, Enabled ? GroupBoxState.Normal : GroupBoxState.Disabled);

            // Text Formating positioning & Size

            int iTextPos = (bounds.X + 8) + mToggleRect.Width + 2;
            int iTextSize = (int)g.MeasureString(Text, this.Font).Width;
            iTextSize = iTextSize < 1 ? 1 : iTextSize;
            //int iEndPos = iTextPos + iTextSize + 1;

            // Draw a line to cover the GroupBox border where the text will sit
            //g.DrawLine(SystemPens.Control, i_textPos, bounds.Y, i_endPos, bounds.Y);

            g.DrawRectangle(SystemPens.Control, bounds.X + 15, bounds.Y, iTextSize, 2);
            g.FillRectangle(SystemBrushes.Control, bounds.X + 15, bounds.Y, iTextSize, 2);
            // Draw the GroupBox text
            using (SolidBrush drawBrush = new SolidBrush(Color.FromArgb(0, 70, 213)))
                g.DrawString(Text, this.Font, drawBrush, iTextPos, 0);

        }

        void DrawToggleButton(Graphics g)
        {
            if (IsCollapsed)
                g.DrawImage(this.imageList.Images[1], mToggleRect);
            else
                g.DrawImage(this.imageList.Images[0], mToggleRect);
        }

        void ToggleCollapsed()
        {
            IsCollapsed = !IsCollapsed;

            if (CollapseBoxClickedEvent != null)
                CollapseBoxClickedEvent(this, null);
        }

        void HandleResize()
        {
            if (!mBResizingFromCollapse && !mCollapsed)
                mFullSize = this.Size;
        }

        #endregion
    }
}
