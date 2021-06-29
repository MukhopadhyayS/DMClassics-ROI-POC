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
using System.Drawing;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Requestors.Model;
using System.Resources;
  

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    public class RequestPatientInfoEditor : ROIEditor
    {
        #region Fields

        private RequestDetails request;

        private RequestPatientHeaderUI patientheaderUI;
        private RequestNonPatientHeaderUI nonPatientHeaderUI;

        private EventHandler requestUpdated;

        private const string CancelDeniedRequestDialogTitile = "CancelDeniedRequestDialog.Title";
        private const string CancelDeniedReuestDialogMessage = "CancelDeniedRequestDialog.Message";
        private const string CancelDeniedRequestDialogOkButton = "CancelDeniedRequestDialog.OkButton";
        private const string CancelDeniedRequestDialogOkButtonToolTip = "CancelDeniedRequestDialog.OkButton";

        #endregion

        #region Constructor

        public RequestPatientInfoEditor() { }

        #endregion
        
        #region Methods

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
            HeaderUI headerUI = SubPanes[0].View as HeaderUI;
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
            if(typeof(RequestPatientInfoMCP).IsAssignableFrom(sender.GetType())) return;

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

            ((RequestPatientInfoUI)MCP.View).UpdateRequestInfo(request);
           
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

        public override void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);

                //Populates RecordViews
                ((RequestPatientInfoMCP)MCP).PrePopulate(UserData.Instance.RecordViews);
                SetData(request);
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

        public override void SetData(object data)
        {
            RequestDetails requestInfo = (ParentPane as RequestRSP).InfoEditor.Request;
            if (request.RecordVersionId <= requestInfo.RecordVersionId)
            {
                request = requestInfo;
            }
            MCP.SetData(request);
            ((Control)View).Enabled = !request.IsLocked;
        }

        public void CheckRequestorIsActive()
        {
            RequestDetails request = (ParentPane as RequestRSP).InfoEditor.Request;
            ((Control)View).Enabled = !request.IsLocked && request.Requestor.IsActive &&
                                       request.Status != RequestStatus.Canceled && request.Status != RequestStatus.Denied;
            if (!request.Requestor.IsActive)
            {
                (((RequestRSP)ParentPane).InfoEditor.MCP.View as RequestInfoUI).ShowInactiveRequestorDialog();
            }
            if (request.Status == RequestStatus.Denied || request.Status == RequestStatus.Canceled)
            {
                DisplayCancelDeniedRequestDialog();
            }
        }
        public void DisplayCancelDeniedRequestDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messgeText = rm.GetString(CancelDeniedReuestDialogMessage);
            string titleText = rm.GetString(CancelDeniedRequestDialogTitile);
            string okButtonText = rm.GetString(CancelDeniedRequestDialogOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(CancelDeniedRequestDialogOkButtonToolTip);

            ROIViewUtility.ConfirmChanges(titleText, messgeText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);

        }
      
    

        public override void Cleanup()
        {
            // do nothing since this instance need to reused
            base.Cleanup();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the PatientInfoEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "request.patientinfo.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the PatientInfoEditor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get 
            {
                //if (request == null || request.Id == 0)
                //{
                //    return "request.patientinfo.header.newinfo";
                //}

                return "request.patientinfo.header.editinfo";
            }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(RequestPatientInfoMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return null; }
        }

        public RequestDetails Request
        {
            get { return request; }
            set { request = value; }
        }

        #endregion
    }
}
