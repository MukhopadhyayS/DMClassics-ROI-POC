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
using System.Collections.ObjectModel;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public partial class CollapsibleBar : Panel
    {
        private Collection<Control> collapsibleControls;

        private Panel innerPanel;

        private CollapsiblePanel expanded;

        public CollapsibleBar()
        {
            collapsibleControls = new Collection<Control>();
            innerPanel = new Panel();
            innerPanel.AutoSize = true;
            innerPanel.Dock = DockStyle.Top;
            this.Controls.Add(innerPanel);
        }

        private void InitializePanels(Collection<Control> collapsibles)
        {
            if (collapsibles.Count == 0) return;

            CollapsiblePanel cp;
            this.SuspendLayout();
            for (int i = 0; i < collapsibles.Count; i++)
            {

                cp = new CollapsiblePanel();
                cp.Name = collapsibles[i].Name;
                cp.Text = collapsibles[i].Text;
                cp.Collapsible = collapsibles[i];
                cp.Expand = false;

                Add(cp);
            }
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        public void Add(CollapsiblePanel cp)
        {
            if (innerPanel.Controls.Count > 0)
            {
                Panel spacer = new Panel();
                spacer.Name = "spacer." + Controls.Count;
                spacer.Height = Padding.Bottom;
                spacer.Dock = DockStyle.Bottom;
                innerPanel.Controls.Add(spacer);
            }

            cp.Dock = DockStyle.Bottom;
            innerPanel.Controls.Add(cp);

            collapsibleControls.Add(cp.Collapsible);
        }

        public void Expand(Control collapsible)
        {
            CollapsiblePanel cp = ((CollapsiblePanel)innerPanel.Controls[collapsible.Name]);
            DoExpand(cp);
        }

        private void DoExpand(CollapsiblePanel cp)
        {
            cp.Expand = true;

            if (expanded == cp) return;

            if (expanded != null)
            {
                expanded.Expand = false;
            }

            expanded = cp;
        }

        public void Expand(Int32 index)
        {
            checked { index = index * 2; }
            CollapsiblePanel cp = ((CollapsiblePanel)innerPanel.Controls[index]);
            DoExpand(cp);

            cp.IsCollapsible = (collapsibleControls.Count > 1);

            /* Collapse other Panels */
            foreach(Control control in collapsibleControls)
            {
                if (control.Name != cp.Name)
                {
                    CollapsiblePanel collapsible = ((CollapsiblePanel)innerPanel.Controls[control.Name]);
                    if (collapsible != null)
                    {
                        DoCollapse(collapsible);
                    }
                }
            }
        }

        public void Collapse(Control collapsible)
        {
            CollapsiblePanel cp = ((CollapsiblePanel)innerPanel.Controls[collapsible.Name]);
            DoCollapse(cp);
        }

        private void DoCollapse(CollapsiblePanel cp)
        {
            if (cp.Expand == false) return;
            cp.Expand = false;
            expanded = null;
        }

        public void Collapse(Int32 index)
        {
            checked { index = index * 2; }
            CollapsiblePanel cp = ((CollapsiblePanel)innerPanel.Controls[index]);
            DoCollapse(cp);
        }

        public void AddControls(Collection<Control> collapsibleControls)
        {
            InitializePanels(collapsibleControls);
        }

        public Collection<Control> CollapsibleControls
        {
            get { return collapsibleControls; }
        }
    }
}
