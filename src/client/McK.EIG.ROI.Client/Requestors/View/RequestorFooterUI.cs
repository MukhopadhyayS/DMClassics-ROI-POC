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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Requestors.View
{
    
    /// <summary>
    /// This class holds requestor footer details.
    /// </summary>
    public partial class RequestorFooterUI : ROIBaseUI
    {
        #region Fields

        private Control pageActions;
        private IRequestorPageContext requestorPageContext;
        private CreateRequestInfo createRequestInfo;

        private EventHandler createRequestHandler;
        
        #endregion

        #region Constructor

        public RequestorFooterUI()
        {
            InitializeComponent(); 
        }
        
        /// <summary>
        /// Initializes the UI.
        /// </summary>
        public RequestorFooterUI(IRequestorPageContext requestorPageContent) :this()
        {
            this.requestorPageContext = requestorPageContent;
            createRequestHandler = new EventHandler(Process_CreateRequest);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Applies localization for the footer controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, createRequestButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, createRequestButton);
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        private void createRequestButton_Click(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = new ApplicationEventArgs(sender, e);
            if (TransientDataValidator.Validate(ae))
            {
                createRequestHandler(sender, e);
            }
        }

        private void Process_CreateRequest(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);

            try
            {
                RequestorDetails selectedRequestor = requestorPageContext.Requestor;

                RequestDetails newRequest = new RequestDetails();
                newRequest.Requestor = selectedRequestor;
                createRequestInfo = new CreateRequestInfo(newRequest, requestorPageContext.CancelCreateRequestHandler);

                if (selectedRequestor.IsPatientRequestor)
                {

                    FindPatientResult searchPatientResult = RequestorController.Instance.SearchMatchingPatients(selectedRequestor);
                    PatientMatchingUI patientMatchingDialogUI = CreatePatientRequestorMatchingUI();
                    patientMatchingDialogUI.SetData(createRequestInfo.Request, searchPatientResult.PatientSearchResult);
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    Form dialog = ROIViewUtility.ConvertToForm(null,
                                                                 rm.GetString("patientMatchingDialogUI.Header.Title"),
                                                                 patientMatchingDialogUI);

                    dialog.ShowDialog();
                }
                else
                {
                    fire(createRequestInfo);
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

        private void fire(CreateRequestInfo cri)
        {
            RequestEvents.OnCreateRequest(this, new ApplicationEventArgs(cri, this));
        }

        private PatientMatchingUI CreatePatientRequestorMatchingUI()
        {

            PatientMatchingUI patientMatchingDialogUI = new PatientMatchingUI(new EventHandler(Process_PatientSelection), new EventHandler(Process_CancelDialog), Pane);
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            patientMatchingDialogUI.Header = new HeaderUI();
            patientMatchingDialogUI.Header.Title = rm.GetString("patientMatching.header.title");
            patientMatchingDialogUI.Header.Information = rm.GetString("patientMatching.header.info");
            return patientMatchingDialogUI;
        }

        private void Process_PatientSelection(object sender, EventArgs e)
        {

            Process_CancelDialog(sender, null);

            BaseEventArgs bae = (BaseEventArgs)e;
            createRequestInfo.Request = (RequestDetails)bae.Info;
            fire(createRequestInfo);
        }

        private void Process_CancelDialog(object sender, EventArgs e)
        {
            ((PatientMatchingUI)sender).ParentForm.Close();
        }

        #endregion

        #region Properties
        /// <summary>
        /// This property holds requestor footer controls.
        /// </summary>
        public Control PageActions
        {
            get { return pageActions; }
            set
            {
                pageActions = value;
                if (pageActions != null)
                {
                    pageActions.Dock = DockStyle.Fill;
                    leftPanel.Controls.Add(pageActions);
                }
            }
        }

        public Button CreateRequestButton
        {
            get { return createRequestButton; }
        }

        public Panel FooterPanel
        {
            get { return footerPanel; }
        }

        public EventHandler CreateRequestHandler
        {
            get { return createRequestHandler; }
        }
        #endregion
    }
}
