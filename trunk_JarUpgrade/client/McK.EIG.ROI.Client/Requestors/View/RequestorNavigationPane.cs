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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Requestors.View
{
    /// <summary>
    /// Used to group Collection of Navigation Links.
    /// </summary>
    public class RequestorNavigationPane : NavigationPane
    {
        #region Fields

        private EventHandler CreateNewRequestor;
        private EventHandler RequestorSelected;
        private EventHandler RequestorDeleted;
        private EventHandler RequestorCreated;
        private EventHandler AccountManagementSelected;
        private EventHandler AccountHistorySelected;

        private bool canAccessRequest;
        #endregion

        #region Methods

        /// <summary>
        /// Initializes view.
        /// </summary>
        protected override void InitView()
        {
            base.InitView();

            //Apply security rights
            navigationLinks.EnableLink(ROIConstants.FindRequestor, ROIViewUtility.IsAllowed(ROISecurityRights.MasterPatientIndexSearch));
            canAccessRequest = (ROIViewUtility.IsAllowed(ROISecurityRights.ROIViewRequest)  ||
                                ROIViewUtility.IsAllowed(ROISecurityRights.ROICreateRequest) ||
                                ROIViewUtility.IsAllowed(ROISecurityRights.ROIModifyRequest));
            EnableLinks(false);
        }

        /// <summary>
        /// Subscribes events
        /// </summary>
        protected override void Subscribe()
        {
            base.Subscribe();

            CreateNewRequestor = new EventHandler(Process_CreateNewRequestor);
            RequestorSelected  = new EventHandler(Process_RequestorSelected);
            RequestorDeleted   = new EventHandler(Process_RequestorDeleted);
            RequestorCreated   = new EventHandler(Process_RequestorCreated);
            AccountManagementSelected = new EventHandler(Process_AccountManamentSelected);
            AccountHistorySelected = new EventHandler(Process_AccountHistorySelected);

            RequestorEvents.CreateRequestor   += CreateNewRequestor;
            RequestorEvents.RequestorCreated  += RequestorCreated;
            RequestorEvents.RequestorSelected += RequestorSelected;
            RequestorEvents.RequestorDeleted  += RequestorDeleted;
            RequestorEvents.AccountManagementSelected += AccountManagementSelected;
        }

        /// <summary>
        /// Unsubscribes evetns
        /// </summary>
        protected override void Unsubscribe()
        {
            base.Unsubscribe();

            RequestorEvents.CreateRequestor   -= CreateNewRequestor;
            RequestorEvents.RequestorCreated  -= RequestorCreated;
            RequestorEvents.RequestorSelected -= RequestorSelected;
            RequestorEvents.RequestorDeleted  -= RequestorDeleted;
            RequestorEvents.AccountManagementSelected -= AccountManagementSelected;
        }

        /// <summary>
        /// Enables LSP navigation links and requestor informaion editor will display
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreateNewRequestor(object sender, EventArgs e)
        {
            if (((IPane)sender).ParentPane.ParentPane == null) return;

            navigationLinks.EnableLink(ROIConstants.RequestorInformation, true);
            navigationLinks.EnableLink(ROIConstants.RequestorRequestHistory, false);
            navigationLinks.SelectLink(ROIConstants.RequestorInformation);
            RequestorEvents.OnNavigateRequestorInfo(this, e);
        }

        /// <summary>
        /// Once the requestor is created enable Request History navigation link 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestorCreated(object sender, EventArgs e)
        {
            navigationLinks.EnableLink(ROIConstants.RequestorRequestHistory, true && canAccessRequest);
            //navigationLinks.EnableLink(ROIConstants.RequestorLetterHistory, true);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountManagement, true);
            navigationLinks.SelectLink(ROIConstants.RequestorInformation);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountHistory,true);
            RequestorEvents.OnNavigateRequestorInfo(this, e);
        }

        /// <summary>
        /// Enables LSP navigation links and requestor informaion editor will display with selected requestor info.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestorSelected(object sender, EventArgs e)
        {
            navigationLinks.EnableLink(ROIConstants.RequestorInformation, true);
            navigationLinks.EnableLink(ROIConstants.RequestorRequestHistory, true && canAccessRequest);
            //navigationLinks.EnableLink(ROIConstants.RequestorLetterHistory, true);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountManagement, true);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountHistory, true);
            navigationLinks.SelectLink(ROIConstants.RequestorInformation);
            RequestorEvents.OnNavigateRequestorInfo(this, e);
        }

        /// <summary>
        /// Enables LSP navigation links and account history will display with selected requestor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_AccountHistorySelected(object sender, EventArgs e)
        {
            navigationLinks.EnableLink(ROIConstants.RequestorInformation, true);
            navigationLinks.EnableLink(ROIConstants.RequestorRequestHistory, true && canAccessRequest);
            //navigationLinks.EnableLink(ROIConstants.RequestorLetterHistory, true);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountManagement, true);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountHistory, true);
            //RequestorEvents.OnNavigateRequestorInfo(this, e);
            RequestorEvents.OnNavigateAccountHistory(this, e);
            navigationLinks.SelectLink(ROIConstants.RequestorAccountManagement);

        }




        /// <summary>
        /// Enables LSP navigation links and account management will display with selected requestor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_AccountManamentSelected(object sender, EventArgs e)
        {
            navigationLinks.EnableLink(ROIConstants.RequestorInformation, true);
            navigationLinks.EnableLink(ROIConstants.RequestorRequestHistory, true && canAccessRequest);
            //navigationLinks.EnableLink(ROIConstants.RequestorLetterHistory, true);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountManagement, true);            
            navigationLinks.EnableLink(ROIConstants.RequestorAccountHistory, true);
            RequestorEvents.OnNavigateRequestorInfo(this, e);
            RequestorEvents.OnNavigateAccountManagement(this, e);
            navigationLinks.SelectLink(ROIConstants.RequestorAccountManagement);
        }

        /// <summary>
        /// Navigate to find requestor editor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestorDeleted(object sender, EventArgs e)
        {
            EnableLinks(false);
            navigationLinks.SelectLink(ROIConstants.FindRequestor);
            RequestorEvents.OnNavigateFindRequestor(sender, e);
        }

        /// <summary>
        /// Process the LSP naviagation link click.
        /// </summary>
        /// <param name="selected"></param>
        /// <param name="ae"></param>
        protected override void ProcessNavigation(string selected, ApplicationEventArgs ae)
        {
            if (selected == ROIConstants.FindRequestor)
            {
                RequestorEvents.OnNavigateFindRequestor(this, ae);
                EnableLinks(false);
            }
            else if (selected == ROIConstants.RequestorInformation)
            {
                RequestorEvents.OnNavigateRequestorInfo(this, ae);
            }
            else if (selected == ROIConstants.RequestorRequestHistory)
            {
                RequestorEvents.OnNavigateRequestHistory(this, ae);
            }
            //else if (selected == ROIConstants.RequestorLetterHistory)
            //{
            //    RequestorEvents.OnNavigateRequestorLetterHistory(this, ae);
            //}
            else if (selected == ROIConstants.RequestorAccountManagement)
            {
                RequestorEvents.OnNavigateAccountManagement(this, ae);
            }
            else if (selected == ROIConstants.RequestorAccountHistory)
            {
                RequestorEvents.OnNavigateAccountHistory(this,ae);
            }
        }

        /// <summary>
        /// Enables LSP navigation links.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableLinks(bool enable)
        {
            navigationLinks.EnableLink(ROIConstants.RequestorInformation, enable);
            navigationLinks.EnableLink(ROIConstants.RequestorRequestHistory, enable && canAccessRequest);
            //navigationLinks.EnableLink(ROIConstants.RequestorLetterHistory, enable);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountManagement, enable);
            navigationLinks.EnableLink(ROIConstants.RequestorAccountHistory, enable);
        }

        #endregion

        #region Properties

        protected override Collection<string> LinkKeys
        {
            get
            {
                Collection<string> links = new Collection<string>();
                links.Add(ROIConstants.FindRequestor);
                links.Add(ROIConstants.RequestorInformation);
                links.Add(ROIConstants.RequestorRequestHistory);
                //links.Add(ROIConstants.RequestorLetterHistory);
                links.Add(ROIConstants.RequestorAccountManagement);
                links.Add(ROIConstants.RequestorAccountHistory);
                return links;
            }
        }

        public override string Title
        {
            get { return ROIConstants.RequestorModuleName; }
        }

        #endregion
    }
}
