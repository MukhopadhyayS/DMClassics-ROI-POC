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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using McK.EIG.ROI.Client.Request.View.Comments;
using McK.EIG.ROI.Client.Request.View.EventHistory;
using McK.EIG.ROI.Client.Request.View.FindRequest;
using McK.EIG.ROI.Client.Request.View.Letters;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Request.View.ReleaseHistory;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;


namespace McK.EIG.ROI.Client.Request.View
{
    public class RequestRSP : ROIRightSidePane
    {
        #region Fields

        private EventHandler navigateFindRequest;
        private EventHandler navigateRequestInfo;
        private EventHandler navigateComment;
        private EventHandler navigateLetters;
        private EventHandler navigatePatientInfo;
        private EventHandler navigateEventHistory;
        private EventHandler navigateReleaseHistory;
        private EventHandler navigateBillingPayment;

        private FindRequestEditor searchRequestEditor;
        private RequestInfoEditor infoEditor;
        private RequestPatientInfoEditor patientInfoEditor;
        private BillingPaymentInfoEditor billingPaymentInfoEditor;

        private  LetterInfoEditor letterInfoEditor;
        private RequestCommentEditor requestCommentEditor;
        private EventHistoryEditor eventHistoryEditor;
        private ReleaseHistoryEditor releaseHistoryEditor;
        
        #endregion

        #region Methods

        /// <summary>
        /// Event Subscription.
        /// </summary>
        protected override void Subscribe()
        {
            navigateFindRequest     = new EventHandler(Process_NavigateFindRequest);
            navigateRequestInfo     = new EventHandler(Process_NavigateRequestInfo);
            navigateComment         = new EventHandler(Process_NavigateComment);
            navigateLetters         = new EventHandler(Process_NavigateLetters);
            navigatePatientInfo     = new EventHandler(Process_NavigatePatientInfo);
            navigateEventHistory    = new EventHandler(Process_NavigateEventHistory);
            navigateReleaseHistory  = new EventHandler(Process_NavigateReleaseHistory);
            navigateBillingPayment  = new EventHandler(Process_NavigateBillingPayment);

            RequestEvents.NavigateRequestInfo  += navigateRequestInfo;
            RequestEvents.NavigateComment      += navigateComment;
            RequestEvents.NavigateLetters      += navigateLetters;
            RequestEvents.NavigatePatientInfo  += navigatePatientInfo;
            RequestEvents.NavigateFindRequest  += navigateFindRequest;
            RequestEvents.NavigateEventHistory += navigateEventHistory;
            RequestEvents.NavigateReleaseHistory += navigateReleaseHistory;
            RequestEvents.NavigateBillingPayment += navigateBillingPayment;
        }

        /// <summary>
        /// Event Unsubscription.
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestEvents.NavigateFindRequest  -= navigateFindRequest;
            RequestEvents.NavigateRequestInfo  -= navigateRequestInfo;
            RequestEvents.NavigateComment      -= navigateComment;
            RequestEvents.NavigateLetters      -= navigateLetters;
            RequestEvents.NavigatePatientInfo  -= navigatePatientInfo;
            RequestEvents.NavigateEventHistory -= navigateEventHistory;
            RequestEvents.NavigateReleaseHistory -= navigateReleaseHistory;
            RequestEvents.NavigateBillingPayment -= navigateBillingPayment;
        }

        /// <summary>
        /// Process the event of Request Information Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateRequestInfo(object sender, EventArgs e)
        {
            if (infoEditor != null && infoEditor == currentEditor) return;

            bool init = (infoEditor == null);
            if (init)
            {
                infoEditor = new RequestInfoEditor();
            }
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            PrepareRequestInfo(ae.Info);
            SetCurrentEditor(infoEditor, init);
            infoEditor.SetData(infoEditor.Request);
        }

        private void PrepareRequestInfo(object data)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                if (typeof(CreateRequestInfo).IsAssignableFrom(data.GetType()))//For creating new request
                {
                    infoEditor.CreateRequestInfo = (CreateRequestInfo)data;
                    if (infoEditor.Request.Requestor != null && infoEditor.Request.Requestor.Id > 0)
                    {
                        infoEditor.Request.Requestor = RequestorController.Instance.RetrieveRequestor(infoEditor.Request.Requestor.Id, false);
                    }
                }
                else if (typeof(RequestDetails).IsAssignableFrom(data.GetType()))//For existing request
                {
                    infoEditor.Request = (RequestDetails)data;
                    if (infoEditor.Request.Requestor != null)
                    {
                        RequestorDetails requestor = RequestorController.Instance.RetrieveRequestor(infoEditor.Request.RequestorId, false);
                        infoEditor.Request.Requestor = requestor;
                        infoEditor.Request.RequestorId = requestor.Id;
                        infoEditor.Request.RequestorType = requestor.Type;
                        infoEditor.Request.RequestorTypeName = requestor.TypeName;
                        
                    }
                }
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


        /// <summary>
        /// Process the event of Request Information Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigatePatientInfo(object sender, EventArgs e)
        {
            if (patientInfoEditor != null && patientInfoEditor == currentEditor) return;

            bool init = (patientInfoEditor == null);
            if (init)
            {
                patientInfoEditor = new RequestPatientInfoEditor();
            }

            SetCurrentEditor(patientInfoEditor, init);
            patientInfoEditor.CheckRequestorIsActive();
            //ApplicationEventArgs ae = (ApplicationEventArgs)e;
            //patientInfoEditor.SetData(ae.Info);
        }

        /// <summary>
        /// Process the event of Request Information Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateComment(object sender, EventArgs ae)
        {
            if (requestCommentEditor != null && requestCommentEditor == currentEditor) return;

            bool init = (requestCommentEditor == null);
            if (init)
            {
                requestCommentEditor = new RequestCommentEditor();
            }

            SetCurrentEditor(requestCommentEditor,init);
        }

        /// <summary>
        /// Process the event of Request Information Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateLetters(object sender, EventArgs ae)
        {
            
            if (letterInfoEditor != null && letterInfoEditor == currentEditor) return;

            bool init = (letterInfoEditor == null);
            if (init)
            {
                letterInfoEditor = new LetterInfoEditor();
            }

            SetCurrentEditor(letterInfoEditor, init);
           //SetCurrentEditor(new LetterInfoEditor());
        }
        
        /// <summary>
        /// Process the event of Request Information Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateEventHistory(object sender, EventArgs ae)
        {
            if (eventHistoryEditor != null && eventHistoryEditor == currentEditor) return;

            bool init = (eventHistoryEditor == null);
            if (init)
            {
                eventHistoryEditor = new EventHistoryEditor();
            }

            SetCurrentEditor(eventHistoryEditor, init);

           //SetCurrentEditor(new EventHistoryEditor());
        }

        /// <summary>
        /// Process the event of Release History link Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateReleaseHistory(object sender, EventArgs e)
        {
            if (releaseHistoryEditor != null && releaseHistoryEditor == currentEditor) return;

            bool init = (releaseHistoryEditor == null);
            if (init)
            {
                releaseHistoryEditor = new ReleaseHistoryEditor();
            }

            SetCurrentEditor(releaseHistoryEditor, init);

           //SetCurrentEditor(new ReleaseHistoryEditor());
        }

        protected override void ClearCurrentEditor(BasePane currentEditor, BasePane newEditor)
        {
            if (currentEditor == null) return;


            if (currentEditor == billingPaymentInfoEditor && billingPaymentInfoEditor != null)
            {
                billingPaymentInfoEditor.Cleanup();
                billingPaymentInfoEditor = null;
            }

            if (newEditor == searchRequestEditor || newEditor == infoEditor)
            {  
                //changes for DM7011
                if (newEditor == searchRequestEditor)
                {
                    if (infoEditor != null)
                    {
                        infoEditor.Cleanup();
                        infoEditor = null;
                    }
                }
                if (patientInfoEditor != null)
                {
                    patientInfoEditor.Cleanup();
                    patientInfoEditor = null;
                }
                if (billingPaymentInfoEditor != null)
                {
                    billingPaymentInfoEditor.Cleanup();
                    billingPaymentInfoEditor = null;
                }

                if (letterInfoEditor != null)
                {
                    letterInfoEditor.Cleanup();
                    letterInfoEditor = null;
                }
                if (requestCommentEditor != null)
                {
                    requestCommentEditor.Cleanup();
                    requestCommentEditor = null;
                }
                if (eventHistoryEditor != null)
                {
                    eventHistoryEditor.Cleanup();
                    eventHistoryEditor = null;
                }
                if (releaseHistoryEditor != null)
                {
                    releaseHistoryEditor.Cleanup();
                    releaseHistoryEditor = null;
                }

            }
         }

        /// <summary>
        /// Process the event of Find Request Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateFindRequest(object sender, EventArgs e)
        {

            if (searchRequestEditor != null && searchRequestEditor == currentEditor) return;

            bool init = (searchRequestEditor == null);
            if (init)
            {
                searchRequestEditor = new FindRequestEditor();
            }

            SetCurrentEditor(searchRequestEditor, init);            
        }

        /// <summary>
        /// Process the event of Request Information Click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateBillingPayment(object sender, EventArgs e)
        {
            try
            {
                if (billingPaymentInfoEditor != null && billingPaymentInfoEditor == currentEditor) return;

                bool init = (billingPaymentInfoEditor == null);
                if (init)
                {
                    billingPaymentInfoEditor = new BillingPaymentInfoEditor();
                }

                SetCurrentEditor(billingPaymentInfoEditor, init); 


                //billingPaymentInfoEditor = new BillingPaymentInfoEditor();
                //SetCurrentEditor(billingPaymentInfoEditor);

                ApplicationEventArgs ae = (ApplicationEventArgs)e;
                billingPaymentInfoEditor.SetData(ae.Info);
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

        #endregion

        #region Properties

        public RequestInfoEditor InfoEditor
        {
            get { return infoEditor; }
            set { infoEditor = value; }
        }

        public BillingPaymentInfoEditor BillingPaymentInfoEditor
        {
            get { return billingPaymentInfoEditor; }
            set { billingPaymentInfoEditor = value; }
        }

        public RequestPatientInfoEditor PatientInfoEditor
        {
            get { return patientInfoEditor; }
            set { patientInfoEditor = value; }
        }

        public RequestCommentEditor CommentEditor
        {
            get { return requestCommentEditor; }
            set { requestCommentEditor = value; }
        }

        public EventHistoryEditor EventHistoryEditorProp
        {
            get { return eventHistoryEditor; }
            set { eventHistoryEditor = value; }
        }

        public ReleaseHistoryEditor ReleaseHistoryEditorProp
        {
            get { return releaseHistoryEditor; }
            set { releaseHistoryEditor = value; }
        }

        public LetterInfoEditor LetterInfoEditorProp
        {
            get { return letterInfoEditor; }
            set { letterInfoEditor = value; }
        }
        
        #endregion
    }
}
