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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Request.View.RequestInfo;


namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// BillingPaymentInfoEditor contains billing releated cotrols
    /// </summary>
    public class BillingPaymentInfoEditor : ROIEditor
    {
        #region Fields

        private RequestDetails request;
        private ReleaseDetails release;

        private RequestPatientHeaderUI patientheaderUI;
        private RequestNonPatientHeaderUI nonPatientHeaderUI;

        private EventHandler requestUpdated;
        
        #endregion
        
        #region Constructor

        public BillingPaymentInfoEditor() { }

        #endregion
        
        #region Methods

        /// <summary>
        /// Initialize the editor components
        /// </summary>
        protected override void InitComponent()
        {
            base.InitComponent();
            base.hOuterSplitContainer.SplitterDistance = 38;

            request = (ParentPane as RequestRSP).InfoEditor.Request;
            InitializeHeader();
        }

        /// <summary>
        /// Method used to initializez the header information.
        /// </summary>
        private void InitializeHeader()
        {
            HeaderUI headerUI = HeaderPane.View as HeaderUI;
            if (request.Requestor.IsPatientRequestor)
            {
                patientheaderUI = new RequestPatientHeaderUI();
                headerUI.HeaderExtension = patientheaderUI;
                headerUI.Parent.Size = new Size(headerUI.Parent.Width, patientheaderUI.Height + 15);
            }
            else
            {
                nonPatientHeaderUI = new RequestNonPatientHeaderUI();
                headerUI.HeaderExtension = nonPatientHeaderUI;
                headerUI.Parent.Size = new Size(headerUI.Parent.Width, nonPatientHeaderUI.Height + 5);
            }
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        protected override void Subscribe()
        {
            requestUpdated = new EventHandler(Process_UpdateRequestInfo);
            RequestEvents.RequestUpdated += requestUpdated;
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestEvents.RequestUpdated -= requestUpdated;
        }

        /// <summary>
        /// Updates the patient's information on patient information box
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateRequestInfo(object sender, EventArgs e)
        {
            request = (RequestDetails)((ApplicationEventArgs)e).Info;
            if (request.Requestor.IsPatientRequestor)
            {
                //if (patientheaderUI == null)
                //{
                //    InitializeHeader();
                //}
                InitializeHeader();
                patientheaderUI.Localize(Context);
                patientheaderUI.PopulateRequestorInfo(request);
            }
            else
            {
                //if (nonPatientHeaderUI == null)
                //{
                //    InitializeHeader();
                //}
                InitializeHeader();
                nonPatientHeaderUI.Localize(Context);
                nonPatientHeaderUI.PopulateRequestorInfo(request);
            }
        }


        /// <summary>
        /// Localize the MCP, ODP and HeaderUI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            if (request.Requestor.IsPatientRequestor)
            {
                patientheaderUI.Localize(Context);
                patientheaderUI.PopulateRequestorInfo(request);
            }
            else
            {
                nonPatientHeaderUI.Localize(Context);
                nonPatientHeaderUI.PopulateRequestorInfo(request);
            }
        }

        /// <summary>
        /// Prepopulates the datas in editor
        /// </summary>
        public override void PrePopulate()
        {
            
            //try
            //{
                ROIViewUtility.MarkBusy(true);
                request.Requestor = RequestorController.Instance.RetrieveRequestor(request.Requestor.Id, false);
                ((BillingPaymentInfoMCP)MCP).PrePopulate(request);
            //}
            //catch (ROIException cause)
            //{
            //    ROIViewUtility.Handle(Context, cause);
            //}
            //finally
            //{
            //    ROIViewUtility.MarkBusy(false);
            //}
        }

        /// <summary>
        /// Sets the release data in editor
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            BillingPaymentInfoUI billingPayment = new BillingPaymentInfoUI();
            if (typeof(ReleaseDetails).IsAssignableFrom(data.GetType()))
            {
                release = (ReleaseDetails)data;
            }
            else
            {
                // To update the released patients in release item.
                bool showPreviousRelease = true;
                RequestPatientInfoUI patientInfoUI = null;

                if (((RequestRSP)ParentPane).PatientInfoEditor != null)
                {
                    patientInfoUI = (RequestPatientInfoUI)(((RequestRSP)ParentPane).PatientInfoEditor).MCP.View;
                    showPreviousRelease = patientInfoUI.ShowPreviousRelease;
                }
                ReleaseDetails releaseDetails = null;
                if (request.HasDraftRelease)
                {
                    //releaseDetails = request.DraftRelease;
                    if (request.Id > 0)
                    {
                        releaseDetails = billingPayment.RevertInfo(request);
                        //releaseDetails = BillingController.Instance.RetrieveReleaseInfo(releaseDetails);
                    }
                }

                //if (releaseDetails != null && releaseDetails.TotalPages > 0)
                if (releaseDetails != null)
                {
                    release = releaseDetails;
                }
                else if (request.ReleaseCount > 0 && showPreviousRelease)
                {
                    release = billingPayment.RevertInfo(request);
                    //release = BillingController.Instance.RetrieveReleaseInfo(request.Releases[0]);
                }
                else
                {
                    if (patientInfoUI != null)
                    {
                        release = patientInfoUI.UpdateReleaseDraft(true);
                    }
                }
            }

            MCP.SetData(release);
            ((Control)View).Enabled = (!request.IsLocked && request.Requestor.IsActive &&
                                      request.Status != RequestStatus.Canceled && request.Status != RequestStatus.Denied);

            if (!request.Requestor.IsActive)
            {
                (((RequestRSP)ParentPane).InfoEditor.MCP.View as RequestInfoUI).ShowInactiveRequestorDialog();
            }
        }

        public override void Cleanup()
        {
            base.Cleanup();
            // do nothing since this instance need to reused
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the PatientInfoEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "billingPayment.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the PatientInfoEditor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get 
            {
                if (request.Releases.Count == 0 && (request.HasDraftRelease))
                {
                    return "billingPayment.header.newinfo";
                }

                return "billingPayment.header.editinfo";
            }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(BillingPaymentInfoMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return null; }
        }

        /// <summary>
        /// Request Info
        /// </summary>
        public RequestDetails Request
        {
            get { return request; }
            set { request = value; }
        }

        /// <summary>
        /// Based on selection either set latest release or draft release
        /// </summary>
        public ReleaseDetails Release
        {
            get { return release; }
            set { release = value; }
        }

        #endregion
    }
}
