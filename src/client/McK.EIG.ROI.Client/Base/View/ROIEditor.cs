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
using McK.EIG.ROI.Client.Admin.View;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Base implementation to hold Header Pane and MCP.
    /// </summary>
    public abstract class ROIEditor : ROIBaseContainerPane
    {
        
        #region Fields

        /// <summary>
        /// Horizontal split container.
        /// </summary>
        internal ROISplitContainer hOuterSplitContainer;

        #endregion

        #region Methods
        /// <summary>
        /// Returns all the children type of ROIEditor.
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            Type mcpType = MCPType;
            Type odpType = ODPType;
            if (odpType == null)
            {
                return new Type[] { typeof(HeaderPane),
                                    mcpType,
                };
            }
            else
            {
                return new Type[] { typeof(HeaderPane),
                                    mcpType,
                                    odpType
                };
            }
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

            // if count > 2 , ODP has to be added.
            if (SubPanes.Count > 2)
            {
                if (!typeof(IFooterProvider).IsAssignableFrom(ODP.View.GetType()))
                {
                    hOuterSplitContainer.Panel2.Padding = new Padding(6);
                }
                hOuterSplitContainer.Panel2.Controls.Add(GetLowerComponent());
            }
            else
            {
                hOuterSplitContainer.Panel2Collapsed = true;
            }
        }

        private Control GetUpperComponent()
        {
            UpperComponent upperComp = new UpperComponent();
            upperComp.Dock = DockStyle.Fill;
            //upperComp.TabStop = false;
            
            Control header = HeaderPane.View as Control;
            header.Dock    = DockStyle.Fill;
            header.TabStop   = false;
            upperComp.HeaderPanel.Controls.Add(header);

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

        private Control GetLowerComponent()
        {
            LowerComponent lowerComp = new LowerComponent();
            lowerComp.Dock = DockStyle.Fill;           

            Control odpView = (Control)ODP.View as Control;
            odpView.Dock = DockStyle.Fill;
            //odpView.TabStop = true; // True for admin module
            lowerComp.ODPPanel.Controls.Add(odpView);

            //lowerComp.FooterPanel.Parent is "Bottom Panel"
            lowerComp.FooterPanel.Parent.Visible = false;

            if (typeof(IFooterProvider).IsAssignableFrom(odpView.GetType()))
            {
                Control footerUI = (odpView as IFooterProvider).RetrieveFooterUI();               
                lowerComp.FooterPanel.Controls.Add(footerUI);
                lowerComp.FooterPanel.Parent.Visible = true;
            }

            return lowerComp;
        }

        public override void Localize()
        {
            HeaderPane hPane = (HeaderPane)SubPanes[0];
            HeaderUI header = (HeaderUI)hPane.View;
            ResourceManager uiTextRM = CultureManager.GetCulture(CultureType.UIText.ToString()); 
            header.Title = uiTextRM.GetString(TitleText);
            header.Information = uiTextRM.GetString(InfoText);
            if (header.Title == "Release && Invoice")
            {
                Font fontobj = new Font("Arial", 8);
                header.FontInformation = fontobj;
            }
            base.Localize();
        }
 
        public virtual void PrePopulate()
        {
        }

        public override void Cleanup()
        {
            View.Dispose();
            base.Cleanup();
        }

       #endregion

        #region Properties

        public virtual IPane HeaderPane
        {
            get { return SubPanes[0]; }
        }

        public virtual IPane MCP
        {
            get { return SubPanes[1]; }
        }

        public virtual IPane ODP
        {
            get { return SubPanes[2]; }
        }

        /// <summary>
        /// Returns the view of ROIEditor.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get { return hOuterSplitContainer; }
        }

        protected virtual Nullable<Size> MCPAutoScrollMargin
        {
            get { return new Size(320, 85); }
        }

        protected virtual Nullable<Size> MCPAutoScrollMinSize
        {
            get { return new Size(320, 85); }
        }

        protected virtual Padding ODPPadding
        {
            get { return new Padding(0, 0, 0, 0); }
        }

        public UpperComponent UpperComponent
        {
            get
            {
                return hOuterSplitContainer.Panel1.Controls[0] as UpperComponent;
            }
        }

        /// <summary>
        /// Has to be overridden.
        /// </summary>
        /// <returns></returns>
        protected abstract string TitleText { get; }
        protected abstract string InfoText { get; }
        protected abstract Type MCPType { get; }
        protected abstract Type ODPType { get; }

        #endregion
    }
}
