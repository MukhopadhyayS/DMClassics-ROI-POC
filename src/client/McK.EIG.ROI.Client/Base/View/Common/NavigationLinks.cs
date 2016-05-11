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
using System.Globalization;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public partial class NavigationLinks : FlowLayoutPanel
    {
        #region Fields

        public event EventHandler NavigationLinkClick;

        private GradientLabel previousSelection;
        
        #endregion

        #region Constructor

        public NavigationLinks()
        {
            InitContainer();
            Height = 0;
        }

        #endregion

        #region Methods

        private void InitContainer()
        {
            this.FlowDirection = FlowDirection.TopDown;
            this.Layout += new LayoutEventHandler(NavigationLinks_Layout);
        }

        private void NavigationLinks_Layout(object sender, LayoutEventArgs e)
        {
            // as recommended : http://connect.microsoft.com/VisualStudio/feedback/ViewFeedback.aspx?FeedbackID=112563
            if (Controls.Count == 0) return;
            Controls[0].Dock = DockStyle.None;
            for (int i = 1; i < Controls.Count; i++)
            {
                Controls[i].Dock = DockStyle.Top;
            }
            Controls[0].Width = DisplayRectangle.Width - Controls[0].Margin.Horizontal;
        }

        public void Localize(string link, string text)
        {
            GradientLabel label = (GradientLabel)this.Controls[link];
            label.Text = text;
        }

        public void AddLink(ILink link)
        {
            GradientLabel label = CreateNavigationLink(link);
            label.Click += new EventHandler(OnLinkClick);
            label.Anchor = AnchorStyles.Top | AnchorStyles.Right;
            this.Controls.Add(label);
            Height += label.Height;
        }

        private GradientLabel CreateNavigationLink(ILink link)
        {
            GradientLabel label = new GradientLabel();
            label.Width = Width;
            label.Name  = link.Key;
            label.Text  = link.DisplayText.PadLeft(link.DisplayText.Length + 5);
            label.Tag   = link;
            return label;
        }

        private void OnLinkClick(object sender, EventArgs e)
        {
            if (NavigationLinkClick != null)
            {
                GradientLabel currentSelection = sender as GradientLabel;
                 NavigationLinkClick(this, new NavigationEventArgs(currentSelection.Tag as ILink, GetPreviousSelection()));
            }
        }
        
        private ILink GetPreviousSelection()
        {
            if (previousSelection != null)
            {
                return previousSelection.Tag as ILink;
            }
            else
            {
                return null;
            }
        }

        public bool RemoveLink(ILink link)
        {
            if (!this.Controls.ContainsKey(link.Key))
            {
                return false;
            }

            this.Controls.RemoveByKey(link.Key);
            return true;
        }

        /// <summary>
        /// Clean up the Selected Navigation links.
        /// </summary>
        /// <param name="this"></param>
        public void ClearSelection()
        {
            if (previousSelection != null)
            {
                previousSelection.IsSelected = false;
                previousSelection = null;
            }
        }

        public bool SelectLink(string key)
        {
            if (!this.Controls.ContainsKey(key))
            {
                return false;
            }
            Select(Controls[key] as GradientLabel);
            return true;
        }

        public bool SelectLink(ILink link)
        {
            return SelectLink(link.Key);
        }

        private void Select(GradientLabel link)
        {
            if (previousSelection == link)
            {
                return;
            }
            link.IsSelected = true;
            if (previousSelection != null)
            {
                previousSelection.IsSelected = false;
            }

            previousSelection = link;
            Refresh();
        }

        public void EnableLink(string key, bool isEnable)
        {
            (Controls[key] as GradientLabel).Enabled = isEnable;
        }

        public void EnableLink(int index, bool isEnable)
        {
            (Controls[index] as GradientLabel).Enabled = isEnable;
        }

        #endregion
    }

    public interface ILink
    {
        string Key { get; set;}
        string DisplayText { get; set;}
        object Tag { get; set;}
    }

    public class NavigationEventArgs : EventArgs
    {
        private ILink selected;
        private ILink previous;

        public NavigationEventArgs(ILink selected, ILink previous)
        {
            this.selected = selected;
            this.previous = previous;
        }

        public ILink Selected
        {
            get { return selected; }
        }

        public ILink Previous
        {
            get { return previous; }
        }
    }
}


