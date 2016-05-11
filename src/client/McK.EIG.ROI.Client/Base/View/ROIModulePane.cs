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
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    public abstract class ROIModulePane : ROIBaseContainerPane 
    {
        #region Fields

        private ROISplitContainer vSplit;
        private EventHandler editorChange;

        #endregion

        #region Methods


        /// <summary>
        /// Initilizes the PatientsModulePane.
        /// </summary>
        protected override void InitComponent()
        {
            vSplit = new ROISplitContainer(true, false);
            vSplit.Dock = DockStyle.Fill;
            vSplit.SplitterDistance = 30;

            IPane lsp = SubPanes[0];
            vSplit.Panel1.AutoScroll = true;
            vSplit.Panel1.AutoScrollMinSize = new Size(100, 250);
            vSplit.Panel1.AutoScrollMargin = new Size(100, 250);
            vSplit.Panel1.Controls.Add((Control)lsp.View);

            IPane rsp = SubPanes[1];
            vSplit.Panel2.Controls.Add((Control)rsp.View);
            vSplit.Panel2.Padding = new Padding(0);           
        }

        public virtual void ApplyDefault()
        {
            ROILeftSidePane lsp = (ROILeftSidePane)SubPanes[0];
            lsp.ApplyDefault();
        }

        protected override void Subscribe()
        {
            editorChange = new EventHandler(Process_OnEditorChange);
            ROIEvents.EditorChange += editorChange;
        }

        protected override void Unsubscribe()
        {
            base.Unsubscribe();
            ROIEvents.EditorChange -= editorChange;
        }

        private void Process_OnEditorChange(object sender, EventArgs e)
        {
            if (!(AllowEditorChange(sender, e))) return;

            IPane pane = (IPane)(e as ApplicationEventArgs).Info;

            vSplit.Panel2.SuspendLayout();

            vSplit.Panel2.Controls.Clear();

            vSplit.Panel2.Controls.Add((Control)pane.View);

            vSplit.Panel2.ResumeLayout(false);
            vSplit.Panel2.PerformLayout();

            vSplit.Panel2.Refresh();
        }

        internal virtual bool AllowEditorChange(object sender, EventArgs e)
        {
            // can allow if it is from the respective RSP of the module
            return (RSP.GetType().IsAssignableFrom(sender.GetType()));
        }

        #endregion

        #region Properties

        public ROIRightSidePane RSP
        {
            get { return (ROIRightSidePane)SubPanes[1]; }
        }

        public ROILeftSidePane LSP
        {
            get { return (ROILeftSidePane)SubPanes[0]; }
        }

        /// <summary>
        /// Returns the view of PatientsModulePane.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get { return vSplit; }
        }

        #endregion
    }
}
