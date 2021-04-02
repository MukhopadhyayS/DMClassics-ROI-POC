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
using System.ComponentModel;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Base implementation to hold MCP for Reports Module.
    /// </summary>
    public abstract class ROIReportEditor : ROIEditor
    {
        #region Methods
        /// <summary>
        /// Returns all the children type of ROIEditor.
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            Type mcpType = MCPType;
            return new Type[] {mcpType};
        }

        /// <summary>
        ///  Initializes the Split Container.
        /// </summary>
        protected override void InitComponent()
        {
            hOuterSplitContainer = new ROISplitContainer(true, true);
            hOuterSplitContainer.Orientation = Orientation.Horizontal;
            hOuterSplitContainer.Dock = DockStyle.Fill;
            hOuterSplitContainer.BackColor = Color.FromArgb(221, 231, 253);
            hOuterSplitContainer.SplitterDistance = 70;            
            hOuterSplitContainer.Panel1.Padding = new Padding(1);

            //Filling ODP backcolor
            hOuterSplitContainer.Panel2.AutoScroll = true;
            hOuterSplitContainer.Panel2.AutoScrollMinSize = new Size(450, 150);
            hOuterSplitContainer.Panel2.BackColor = Color.FromArgb(201, 203, 203);
           
            hOuterSplitContainer.Panel1.Controls.Add(GetUpperComponent());

            hOuterSplitContainer.Panel2Collapsed = true;
        }

        private Control GetUpperComponent()
        {
            UpperComponent upperComp = new UpperComponent();
            upperComp.Dock = DockStyle.Fill;

            upperComp.HeaderPanel.Visible = false;

            Control mcpView = MCP.View as Control;
            mcpView.Dock    = DockStyle.Fill;
            mcpView.Padding = new Padding(0, 0, 0, 0);

            if (MCPAutoScrollMargin.HasValue)
            {
                upperComp.MCPPanel.AutoScrollMargin = MCPAutoScrollMargin.Value;
            }

            if (MCPAutoScrollMinSize.HasValue)
            {
                upperComp.MCPPanel.AutoScrollMinSize = MCPAutoScrollMinSize.Value;
            }

            upperComp.MCPPanel.Controls.Add(mcpView);

            //upperComp.FooterPanel.Parent is "Bottom Panel"
            upperComp.FooterPanel.Parent.Visible = false;

            if (typeof(IFooterProvider).IsAssignableFrom(mcpView.GetType()))
            {
                Control footerUI = (mcpView as IFooterProvider).RetrieveFooterUI();
                footerUI.Dock = DockStyle.Fill;
                upperComp.FooterPanel.Controls.Add(footerUI);
                upperComp.FooterPanel.Parent.Visible = true;
            }
            return upperComp;
        }

        public override void Localize()
        {
           base.SubPanes[0].Localize();
        }

        protected override string InfoText
        {
            get { return null; }
        }

        protected override string TitleText
        {
            get { return null; }
        }

        protected override Type ODPType
        {
            get { return null; }
        }

        public override IPane MCP
        {
            get { return SubPanes[0]; }
        }

       #endregion

    }
}
