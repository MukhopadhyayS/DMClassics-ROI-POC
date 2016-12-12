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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View.AccountManagement;
using McK.EIG.ROI.Client.Requestors.View.FindRequestor;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;
using McK.EIG.ROI.Client.Requestors.View.RequestHistory;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Requestors.View.AccountHistory;

namespace McK.EIG.ROI.Client.Requestors.View
{
    public class RequestorRSP : ROIRightSidePane
    {
        #region Fields
        
        private EventHandler navigateFindRequestor;
        private EventHandler navigateRequestorInfo;
        private EventHandler navigateRequestHistory;
        private EventHandler navigateAccountManagement;
        private EventHandler navigateAccountHistory;
        private EventHandler createRequest;

        private FindRequestorEditor findEditor;
        private RequestorInfoEditor infoEditor;
        private RequestHistoryEditor requestHistoryEditor;
        private AccountManagementEditor accountManagementEditor;
        private AccountHistoryEditor accountHistoryEditor;
      
        #endregion

        #region Methods

        /// <summary>
        /// Event Subscription.
        /// </summary>
        protected override void Subscribe()
        {
            navigateFindRequestor  = new EventHandler(Process_NavigateFindRequestor);
            navigateRequestorInfo  = new EventHandler(Process_NavigateRequestorInformation);
            navigateRequestHistory = new EventHandler(Process_NavigateRequestHistory);
            navigateAccountManagement = new EventHandler(Process_NavigateAccountManagement);
            navigateAccountHistory = new EventHandler(Process_NavigateAccountHistory);


            RequestorEvents.NavigateFindRequestor  += navigateFindRequestor;
            RequestorEvents.NavigateRequestorInfo  += navigateRequestorInfo;
            RequestorEvents.NavigateRequestHistory += navigateRequestHistory;
            RequestorEvents.NavigateAccountManagement += navigateAccountManagement;
            RequestorEvents.NavigateAccountHistory += navigateAccountHistory;

            createRequest = new EventHandler(Process_CreateRequest);
            RequestorEvents.CreateRequest += createRequest;
        }

        /// <summary>
        /// Event Unsubscription.
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestorEvents.NavigateFindRequestor  -= navigateFindRequestor;
            RequestorEvents.NavigateRequestorInfo  -= navigateRequestorInfo;
            RequestorEvents.NavigateRequestHistory -= navigateRequestHistory;
            RequestorEvents.NavigateAccountManagement -= navigateAccountManagement;

            RequestorEvents.CreateRequest -= createRequest;
        }

        /// <summary>
        /// Process the event of FindRequestor click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateFindRequestor(object sender, EventArgs e)
        {
            if (findEditor != null && currentEditor == findEditor) return;
            
            bool init = (findEditor == null);
            
            if (init)
            {
                findEditor = new FindRequestorEditor();
            }
            SetCurrentEditor(findEditor, init);
            //infoEditor = null;
            //requestHistoryEditor = null;
            //accountManagementEditor = null;
            //accountHistoryEditor = null;
        }

        /// <summary>
        /// Process the event of Requestor Information Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateRequestorInformation(object sender, EventArgs e)
        {
            if (infoEditor != null && infoEditor == currentEditor) return;

            bool init = (infoEditor == null);

            if (init)
            {
                infoEditor = new RequestorInfoEditor();
            }

            //Set the Requestor Info if the eventargs is RequestorDetails
            if (((ApplicationEventArgs)e).Info is RequestorDetails)
            {
                infoEditor.RequestorInfo = ((ApplicationEventArgs)e).Info as RequestorDetails;
            }
            SetCurrentEditor(infoEditor, init);
        }

         /// <summary>
        /// Process the event of Request History click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateRequestHistory(object sender, EventArgs e)
        {
            if (requestHistoryEditor != null && requestHistoryEditor == currentEditor) return;
            if (requestHistoryEditor != null)
            {
                requestHistoryEditor.Cleanup();
                requestHistoryEditor = null;
            }
            bool init = (requestHistoryEditor == null);

            if (init)
            {
                requestHistoryEditor = new RequestHistoryEditor();
            }

            SetCurrentEditor(requestHistoryEditor, init);
        }


        /// <summary>
        /// Process the event of Requestor Account Management on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateAccountManagement(object sender, EventArgs e)
        {
            if (accountManagementEditor != null && accountManagementEditor == currentEditor) return;
            if (accountManagementEditor != null)
            {
                accountManagementEditor.Cleanup();
                accountManagementEditor = null;
            }
            bool init = (accountManagementEditor == null);
           
            if (init)
            {
                accountManagementEditor = new AccountManagementEditor();
            }

            SetCurrentEditor(accountManagementEditor, init);

        }        

        /// <summary>
        /// Process the event of Requestor Account Management on LSP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateAccountHistory(object sender, EventArgs e)
        {
            if (accountHistoryEditor != null && accountHistoryEditor == currentEditor) return;

            if (accountHistoryEditor != null)
            {
                accountHistoryEditor.Cleanup();
                accountHistoryEditor = null;
            }

            bool init = (accountHistoryEditor == null);

            if (init)
            {
                accountHistoryEditor = new AccountHistoryEditor();
            }

            SetCurrentEditor(accountHistoryEditor, init); 
        }
        
        private void RetrieveRequestorInfo(RequestorDetails requestor)
        {
            try
            {
                requestor = RequestorController.Instance.RetrieveRequestor(requestor.Id, false);
                infoEditor.RequestorInfo = requestor;
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        private void Process_CreateRequest(object sender, EventArgs e)
        {
            if (typeof(RequestorInfoUI).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((RequestorInfoUI)currentEditor.MCP.View).Footer.CreateRequestHandler(sender, e);
            }
            if (typeof(RequestHistoryUI).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((RequestHistoryUI)currentEditor.MCP.View).Footer.CreateRequestHandler(sender, e);
            }
        }

        protected override void ClearCurrentEditor(BasePane currentEditor, BasePane newEditor)
        {
            if (findEditor == null) return;

            if (newEditor == findEditor)
            {
                if (infoEditor != null)
                {
                    infoEditor.Cleanup();
                    infoEditor = null;
                }

                if (requestHistoryEditor != null)
                {
                    requestHistoryEditor.Cleanup();
                    requestHistoryEditor = null;
                }

                if (accountManagementEditor != null)
                {
                    accountManagementEditor.Cleanup();
                    accountManagementEditor = null;
                }
                if (accountHistoryEditor != null)
                {
                    accountHistoryEditor.Cleanup();
                    accountHistoryEditor = null;
                }
            }
        }

        #endregion

        #region Properties

        public FindRequestorEditor FindEditor
        {
            get { return findEditor; }
        }

        public RequestorInfoEditor InfoEditor
        {
            get { return infoEditor; }
        }

        #endregion
    }
}
