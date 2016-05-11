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
    public partial class CollapsiblePanel : Panel
    {
        internal static Image ExpandedImage;
        internal static Image CollapsedImage;

        private GradientLabel header;
        private Control toCollapse;

        private bool isCollapsible;

        public CollapsiblePanel()
        {
            InitializeComponent();
        }

        private void InitializeComponent()
        {

            header = new GradientLabel(Color.White, Color.FromArgb(178, 194, 231));
            header.NormalBorderColor = Color.FromArgb(92, 117, 172);
            header.SelectedBorderColor = header.NormalBorderColor;
            header.Padding = new Padding(10, 0, 0, 0);
            header.IsSelected = true;
            header.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
            header.Dock = DockStyle.Top;
            header.ForeColor = Color.FromArgb(27, 81, 156);

            Height = header.Height;

            this.Controls.Add(header);

            header.Click += new EventHandler(ToggleCollapse);
            
        }

        private void ToggleCollapse(object sender, EventArgs e)
        {
            if (Contains(toCollapse))
            {
                DoCollapse();
            }
            else
            {
                DoExpand();
            }
        }

        private void DoExpand()
        {
            if (Controls.Contains(toCollapse)) return;

            Controls.Add(toCollapse);
            Height += toCollapse.Height;
            header.Image = ExpandedImage;
        }

        private void DoCollapse()
        {
            if (!Controls.Contains(toCollapse)) return;

            Controls.Remove(toCollapse);
            Height -= toCollapse.Height;
            header.Image = CollapsedImage;
        }

        public bool Expand
        {
            get { return Contains(toCollapse); }
            set
            {
                if (value)
                {
                    DoExpand();
                }
                else
                {
                    DoCollapse();
                }
            }
        }

        public Control Collapsible
        {
            get { return toCollapse;  }
            set
            {
                toCollapse = value;
                toCollapse.Dock = DockStyle.Bottom;
                header.Text = toCollapse.Text;
                if (Controls.Count > 1)
                {
                    Controls.RemoveAt(1);
                }
                DoExpand();
            }
        }

        public override string Text
        {
            get { return header.Text; }
            set { header.Text = value; }
        }

        public bool IsCollapsible
        {
            get
            {
                return isCollapsible;
            }
            set
            {
                isCollapsible = value;
                if (!isCollapsible)
                {
                    header.Image = null;
                    header.Click -= new EventHandler(ToggleCollapse);
                }
            }
        }

    }
}

