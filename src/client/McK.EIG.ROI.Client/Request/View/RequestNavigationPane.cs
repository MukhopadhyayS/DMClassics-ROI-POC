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
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.FindRequest;

namespace McK.EIG.ROI.Client.Request.View
{
    /// <summary>
    /// RequestNavigationPane
    /// </summary>
    public class RequestNavigationPane : NavigationPane 
    {
        #region Fields

        private EventHandler createNewRequest;
        private EventHandler requestDeleted;
        private EventHandler requestInfoCreated;
        private EventHandler requestSelected;
        private EventHandler cancelCreateRequest;        
        private EventHandler billingSelected;
        private EventHandler dsrChanged;
        private EventHandler requestUpdated;
        
        #endregion

        #region Methods

        /// <summary>
        /// Initializes view.
        /// </summary>
        protected override void InitView()
        {
            base.InitView();
            EnableLinks(false);
        }

        /// <summary>
        /// Subscribes events
        /// </summary>
        protected override void Subscribe()
        {
            base.Subscribe();

            createNewRequest          = new EventHandler(Process_CreateNewRequest);
            requestDeleted            = new EventHandler(Process_RequestDeleted);
            requestInfoCreated        = new EventHandler(Process_RequestInfoCreated);
            requestUpdated            = new EventHandler(Process_RequestUpdated);
            requestSelected           = new EventHandler(Process_RequestSelected);
            cancelCreateRequest       = new EventHandler(Process_CancelCreateRequest);            
            billingSelected           = new EventHandler(Process_BillingSelected);
            dsrChanged = new EventHandler(Process_DsrChanged);
            

            RequestEvents.CreateRequest             += createNewRequest;
            RequestEvents.RequestDeleted            += requestDeleted;
            RequestEvents.RequestUpdated            += requestUpdated;
            RequestEvents.RequestInfoCreated        += requestInfoCreated;
            RequestEvents.RequestSelected 	        += requestSelected;
            RequestEvents.CancelCreateRequest       += cancelCreateRequest;            
            RequestEvents.BillingSelected           += billingSelected;
            RequestEvents.DocumentsTreeChanged += dsrChanged;
        }

        /// <summary>
        /// Unsubscribes evetns
        /// </summary>
        protected override void Unsubscribe()
        {
            base.Unsubscribe();

            RequestEvents.CreateRequest             -= createNewRequest;
            RequestEvents.RequestDeleted            -= requestDeleted;
            RequestEvents.RequestInfoCreated        -= requestInfoCreated;
            RequestEvents.RequestUpdated            -= requestUpdated;
			RequestEvents.RequestSelected 	        -= requestSelected;
            RequestEvents.CancelCreateRequest       -= cancelCreateRequest;            
            RequestEvents.BillingSelected           -= billingSelected;
            RequestEvents.DocumentsTreeChanged -= dsrChanged;
        }

        /// <summary>
        /// Enable and select the Request Information lsp link
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreateNewRequest(object sender, EventArgs e)
        {
            EnableLinks(false);
            navigationLinks.EnableLink(ROIConstants.RequestInformation, true);
            navigationLinks.EnableLink(ROIConstants.RequestPatientInformation, false);
            navigationLinks.SelectLink(ROIConstants.RequestInformation);

            //Request Information editor will be initialized again when user elects to create a request 
            //from either requestor or patients screen
            RequestRSP rsp = (RequestRSP)((RequestModulePane)ParentPane.ParentPane).RSP;
            if (rsp.InfoEditor != null)
            {
                rsp.InfoEditor.Cleanup();
                rsp.InfoEditor = null;
            }

            if (rsp.PatientInfoEditor != null)
            {
                rsp.PatientInfoEditor.Cleanup();
                rsp.PatientInfoEditor = null;
            }

            RequestEvents.OnNavigateRequestInfo(this, e);
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(this, e);
        }

        /// <summary>
        /// Navigate to request patient info editor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestInfoCreated(object sender, EventArgs e)
        {
            navigationLinks.EnableLink(ROIConstants.RequestPatientInformation, true);

            EnableLinks(true);

            //Disable links if the selected request has no release
            DisableLinksRequestWithNoRelease((ApplicationEventArgs)e);
        }

        /// <summary>
        /// Occurs when request is being updated
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestUpdated(object sender, EventArgs e)
        {
            //Disable links if the selected request has no release
            navigationLinks.EnableLink(ROIConstants.RequestPatientInformation, true);
            EnableLinks(true);

            DisableLinksRequestWithNoRelease((ApplicationEventArgs)e);
        }

	    private void Process_RequestSelected(object sender, EventArgs e)
        {
            navigationLinks.EnableLink(ROIConstants.RequestInformation, true);

            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            //For Auth request links will be disabled.
            if (((RequestDetails)ae.Info).Status.ToString() == "Unknown")
            {
                navigationLinks.EnableLink(ROIConstants.RequestPatientInformation, false);
                EnableLinks(false);
                //return;
            }
            if (((RequestDetails)ae.Info).Requestor != null && ((RequestDetails)ae.Info).Status.ToString() != "Unknown")
            {
                navigationLinks.EnableLink(ROIConstants.RequestPatientInformation, true);
                EnableLinks(true);
            }

            //Request Information editor will be initialized again when user elects to create a request 
            //from either requestor or patients screen
            RequestRSP rsp = (RequestRSP)((RequestModulePane)ParentPane.ParentPane).RSP;
            if(!typeof(FindRequestODP).IsAssignableFrom(sender.GetType()))
            {
                if (rsp.InfoEditor != null)
                {
                    rsp.InfoEditor.Cleanup();
                    rsp.InfoEditor = null;
                }

                if (rsp.PatientInfoEditor != null)
                {
                    rsp.PatientInfoEditor.Cleanup();
                    rsp.PatientInfoEditor = null;
                }

                if (rsp.CommentEditor != null)
                {
                    rsp.CommentEditor.Cleanup();
                    rsp.CommentEditor = null;
                }

                if (rsp.EventHistoryEditorProp != null)
                {
                    rsp.EventHistoryEditorProp.Cleanup();
                    rsp.EventHistoryEditorProp = null;
                }

                if (rsp.ReleaseHistoryEditorProp != null)
                {
                    rsp.ReleaseHistoryEditorProp.Cleanup();
                    rsp.ReleaseHistoryEditorProp = null;
                }

                if (rsp.LetterInfoEditorProp != null)
                {
                    rsp.LetterInfoEditorProp.Cleanup();
                    rsp.LetterInfoEditorProp = null;
                }
            }

            //Disable links if the selected request has no release
            DisableLinksRequestWithNoRelease(ae);

            navigationLinks.SelectLink(ROIConstants.RequestInformation);
            RequestEvents.OnNavigateRequestInfo(sender, e);
        }

        /// <summary>
        /// Navigate to find request editor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestDeleted(object sender, EventArgs e)
        {
            EnableLinks(false);
            navigationLinks.EnableLink(ROIConstants.RequestInformation, false);
            navigationLinks.EnableLink(ROIConstants.RequestPatientInformation, false);
            navigationLinks.SelectLink(ROIConstants.FindRequest);
            RequestEvents.OnNavigateFindRequest(sender, e);
        }

        private void Process_BillingSelected(object sender, EventArgs e)
        {
            EnableLinks(true);
            navigationLinks.SelectLink(ROIConstants.BillingAndPayment);
            RequestEvents.OnNavigateBillingPayment(sender, e);
        }

        private void Process_DsrChanged(object sender, EventArgs e)
        {
            navigationLinks.EnableLink(ROIConstants.BillingAndPayment, (bool)((ApplicationEventArgs)e).Info);
        }

        /// <summary>
        /// Process the LSP navigation link click.
        /// </summary>
        /// <param name="selected"></param>
        /// <param name="ae"></param>
        protected override void ProcessNavigation(string selected, ApplicationEventArgs ae)
        {
            if (selected == ROIConstants.FindRequest)
            {
                RequestEvents.OnNavigateFindRequest(this, ae);
                navigationLinks.EnableLink(ROIConstants.RequestInformation, false);
                navigationLinks.EnableLink(ROIConstants.RequestPatientInformation, false);
                EnableLinks(false); //to false
            }
            else if (selected == ROIConstants.RequestInformation)
            {
                RequestEvents.OnNavigateRequestInfo(this, ae);
            }
            else if (selected == ROIConstants.RequestPatientInformation)
            {
                RequestEvents.OnNavigatePatientInfo(this, ae);
            }
            else if (selected == ROIConstants.BillingAndPayment)
            {
                RequestEvents.OnNavigateBillingPayment(this, ae);
            }
            else if (selected == ROIConstants.ReleaseHistory)
            {
                RequestEvents.OnNavigateReleaseHistory(this, ae);
            }
            else if (selected == ROIConstants.EventHistory)
            {
                RequestEvents.OnNavigateEventHistory(this, ae);
            }
            else if (selected == ROIConstants.Comments)
            {
                RequestEvents.OnNavigateComment(this, ae);
            }
            else if (selected == ROIConstants.Letters)
            {
                RequestEvents.OnNavigateLetters(this, ae);
            }
        }

        /// <summary>
        /// Enables LSP navigation links.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableLinks(bool enable)
        {
            navigationLinks.EnableLink(ROIConstants.BillingAndPayment, enable);
            navigationLinks.EnableLink(ROIConstants.ReleaseHistory, enable);
            navigationLinks.EnableLink(ROIConstants.EventHistory, enable);
            navigationLinks.EnableLink(ROIConstants.Comments, enable);
            navigationLinks.EnableLink(ROIConstants.Letters, enable);
        }

        private void DisableLinksRequestWithNoRelease(ApplicationEventArgs ae)
        {
            RequestDetails request = (RequestDetails)ae.Info;
            if (request.ReleaseCount == 0 && (!request.HasDraftRelease))
            {
                navigationLinks.EnableLink(ROIConstants.BillingAndPayment, false);
                //navigationLinks.EnableLink(ROIConstants.InvoiceHistory, false);
                //navigationLinks.EnableLink(ROIConstants.ReleaseHistory, false);
            }
        }

        #endregion

        #region Properties

        protected override Collection<string> LinkKeys
        {
            get
            {
                Collection<string> links = new Collection<string>();
                links.Add(ROIConstants.FindRequest);
                links.Add(ROIConstants.RequestInformation);
                links.Add(ROIConstants.RequestPatientInformation);
                links.Add(ROIConstants.BillingAndPayment);
                links.Add(ROIConstants.ReleaseHistory);
                links.Add(ROIConstants.EventHistory);
                links.Add(ROIConstants.Comments);
                links.Add(ROIConstants.Letters);
                return links;
            }
        }

        public override string Title
        {
            get { return ROIConstants.RequestsModuleName; }
        }

        #endregion
    }
}
