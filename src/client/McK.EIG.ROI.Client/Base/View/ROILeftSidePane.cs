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
using System.Collections.ObjectModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// LSP  
    /// </summary>
    public abstract class ROILeftSidePane : ROIBaseContainerPane
    {
        #region Fields
       
        private CollapsibleBar cPanelBar;
        private EventHandler navigationHandler;

        #endregion

        #region Methods

        /// <summary>
        ///  Initializes the Panel.
        /// </summary>
        protected override void InitComponent()
        {
            
            cPanelBar = new CollapsibleBar();
            cPanelBar.Padding = new Padding(0, 0, 0, 3);
            cPanelBar.Dock = DockStyle.Fill;
            cPanelBar.Paint += new PaintEventHandler(cPanelBar_Paint);

            Collection<Control> linkPanels = new Collection<Control>();
            for (int i = 0; i < SubPanes.Count; i++)
            {
                //pane = (NavigationPane)SubPanes[i].View;
                linkPanels.Add((Control)SubPanes[i].View);
            }
            
            cPanelBar.AddControls(linkPanels);

            StatusStrip st = new StatusStrip();
            st.Dock        = DockStyle.Bottom;
            st.RightToLeft = RightToLeft.Yes;
            st.SizingGrip  = false;
            st.BackColor   = Color.Transparent; 
            
            ToolStripStatusLabel jobsLabel = new ToolStripStatusLabel();
            jobsLabel.Image = McK.EIG.ROI.Client.Resources.images.print;
            WebBrowser browser = new WebBrowser();
            if (browser.Version.Major == 11)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                jobsLabel.Click += delegate { MessageBox.Show(rm.GetString("MonitorOutputJobIE11NotSupported.Message")); };
            }
            else
            {
                jobsLabel.Click += delegate { ShowMonitorOutputJobDialog(); };
            }
            browser = null;
           
            //jobsLabel.Click += delegate { ShowMonitorOutputJobDialog(); };
            jobsLabel.Spring = false;
            jobsLabel.ImageAlign = ContentAlignment.MiddleRight;
            jobsLabel.DisplayStyle = ToolStripItemDisplayStyle.Image;
            jobsLabel.BackgroundImageLayout = ImageLayout.Center;
            st.Items.Add(jobsLabel);
            cPanelBar.Controls.Add(st);
            cPanelBar.Refresh();
        }

        private void cPanelBar_Paint(object sender, PaintEventArgs e)
        {
            Control control = sender as Control;
            e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;

            Rectangle rectangle = new Rectangle(0, 0, control.Width, control.Height);
            Color startColor = Color.FromArgb(239, 242, 251);
            Color endColor = Color.FromArgb(195, 208, 237);

            LinearGradientBrush brush = new LinearGradientBrush(rectangle, startColor, endColor, LinearGradientMode.Vertical);
            GraphicsPath path = new GraphicsPath();

            path.AddRectangle(rectangle);
            e.Graphics.FillPath(brush, path);

        }

        public void ApplyDefault()
        {
            cPanelBar.Expand(0);
            if (typeof(NavigationPane).IsAssignableFrom(SubPanes[0].GetType()))
            {
                ((NavigationPane)SubPanes[0]).ApplyDefault();
            }
        }

        protected override void Subscribe()
        {
            navigationHandler = new EventHandler(OnNavigationChange);
            ROIEvents.NavigationChange += navigationHandler;
        }

        protected override void Unsubscribe()
        {
            ROIEvents.NavigationChange -= navigationHandler;
        }

        private void OnNavigationChange(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = e as ApplicationEventArgs;

            if (!CanProcess(sender, ae)) return;

            string link = ae.Info as string;

            NavigationPane pane;
            for (int i = 0; i < SubPanes.Count; i++)
            {
                pane = (NavigationPane)SubPanes[i];
                if (pane.SelectLink(link, ae))
                {
                    cPanelBar.Expand(i);
                }
            }
        }

        internal abstract bool CanProcess(object sender, ApplicationEventArgs ae);

        /// <summary>
        /// Show the monitor output job dialog
        /// </summary>
        public void ShowMonitorOutputJobDialog()
        {
            MonitorOutputJobsUI monitorOutputJobDialog = new MonitorOutputJobsUI(ParentPane);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form monitorJobdialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString(monitorOutputJobDialog.GetType().Name + ".titlebar.title"),
                                                       monitorOutputJobDialog);
            monitorOutputJobDialog.SetData(false);
            DialogResult result = monitorJobdialog.ShowDialog();
            if ((result == DialogResult.OK) || (result == DialogResult.Cancel))
            {
                monitorJobdialog.Close();
            }
        }

        #endregion

        #region Properties
        /// <summary>
        /// Gets the view of LSP.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get { return cPanelBar; }
        }

        #endregion

    }
}
