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
using System.Collections;
using System.Collections.ObjectModel;
using System.Resources;
using System.ComponentModel;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;


namespace McK.EIG.ROI.Client.Base.View
{
    public abstract class NavigationPane : ROIBasePane
    {

        #region Fields

        private Log log = LogFactory.GetLogger(typeof(NavigationPane));

        private Hashtable links;
        internal NavigationLinks navigationLinks;
        private EventHandler navigationClickHandler;

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the View of Collapsible Panel.
        /// </summary>
        protected override void InitView()
        {
            ResourceManager rm = CultureManager.GetCulture(CultureType.UIText.ToString());
            navigationLinks = new NavigationLinks();
            navigationLinks.Name = Title;
            navigationLinks.Text = rm.GetString(Title);

            links = new Hashtable();
            if (LinkKeys != null)
            {
                ILink link;
                foreach (string key in LinkKeys)
                {
                    link = new ROINavigationLink(key, rm.GetString(key));
                    navigationLinks.AddLink(link);
                    links.Add(link.Key, link);
                }
            }
        }

        protected virtual ILink DefaultLink
        {
            get 
            {
                if (LinkKeys == null)
                {
                    return null;
                }
                return links[LinkKeys[0]] as ILink; 
            }
        }

        public void ApplyDefault()
        {
            SelectLink(DefaultLink.Key, null);
        }

        public void ApplyDefault(string subModule)
        {
            SelectLink(subModule, null);
        }

        protected override void Subscribe()
        {
            navigationClickHandler = new EventHandler(OnNavigationClick);

            navigationLinks.NavigationLinkClick += navigationClickHandler;
        }

        protected override void Unsubscribe()
        {
            navigationLinks.NavigationLinkClick -= navigationClickHandler;
        }

        /// <summary>
        /// Occurs when navigation link clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnNavigationClick(object sender, EventArgs e)
        {
            NavigationEventArgs args = (NavigationEventArgs)e;
            ApplicationEventArgs ae = new ApplicationEventArgs(args.Selected.Key, this);

            try
            {
                if (TransientDataValidator.Validate(ae))
                {
                    ROIEvents.OnNavigationChange(this, ae);
                }
            }
            catch(InvalidOperationException cause)
            {
                log.Debug(cause);
                ROIEvents.OnNavigationChange(this, ae);
            }
        }

        public bool SelectLink(string key, ApplicationEventArgs ae)
        {
            bool linkPresent = navigationLinks.SelectLink(key);
            if (linkPresent)
            {
                ProcessNavigation(key, ae);
            }
            else
            {
                navigationLinks.ClearSelection();
            }
            return linkPresent;
        }
        
        protected abstract void ProcessNavigation(string selected, ApplicationEventArgs ae);

        #endregion

        #region Properties

        /// <summary>
        /// Gets the view of NavigationPane.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get { return navigationLinks; }
        }

        public abstract string Title { get; }
        protected abstract Collection<string> LinkKeys { get; }

        #endregion
    }
}
