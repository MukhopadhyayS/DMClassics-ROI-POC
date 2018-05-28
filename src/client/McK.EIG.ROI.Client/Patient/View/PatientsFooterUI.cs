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
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Patient.View
{
    /// <summary>
    /// PatientsFooterUI
    /// </summary>
    public partial class PatientsFooterUI : ROIBaseUI
    {
        #region Fields

        private Control pageActions;
        private IPatientPageContext patientPageContext;
        private Collection<PatientDetails> selectedPatients;

        private EventHandler createRequestHandler;
        private EventHandler patientRequestHandler;
        #endregion

        #region Constructor
        /// <summary>
        /// It initializes the UI.
        /// </summary>
        public PatientsFooterUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Constructor initializes class level varaibles.
        /// </summary>
        /// <param name="patientPageContent"></param>
        public PatientsFooterUI(IPatientPageContext patientPageContext): this()
        {
            this.patientPageContext = patientPageContext;

            createRequestHandler = new EventHandler(Process_CreateRequest);
            patientRequestHandler = new EventHandler(Process_PatientRequest);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, patientRequestButton);
            SetLabel(rm, createRequestButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, patientRequestButton);
            SetTooltip(rm, toolTip, createRequestButton);
        }

        /// <summary>
        /// Occurs when user clicks patientRequestButton, checks for patient mismatch and checks for matching requestors.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void patientReqButton_Click(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = new ApplicationEventArgs(sender, e);
            if (TransientDataValidator.Validate(ae))
            {
                patientRequestHandler(sender, e);
            }
        }

        private void Process_CreateRequest(object sender, EventArgs e)
        {
            CreateRequestInfo createRequestInfo = new CreateRequestInfo(new RequestDetails(), patientPageContext.CancelCreateRequestHandler);
            RequestPatientDetails requestPatient;
            foreach (PatientDetails patient in patientPageContext.Patients)
            {
                requestPatient = new RequestPatientDetails(patient);
                if (!createRequestInfo.Request.Patients.ContainsKey(requestPatient.Key))
                {
                    createRequestInfo.Request.Patients.Add(requestPatient.Key, requestPatient);
                }
            }
            fire(createRequestInfo);
        }

        private void Process_PatientRequest(object sender, EventArgs e)
        {
            selectedPatients = patientPageContext.Patients;
            //Collection<PatientDetails> mismatchPatients = new Collection<PatientDetails>();
            SortedList<string, PatientDetails> mismatchPatients = new SortedList<string, PatientDetails>();
            if (selectedPatients.Count > 1)
            {
                PatientDetails basePatient = selectedPatients[0];
                for (int index = 1; index < selectedPatients.Count; index++)
                {
                    if (PatientDetails.FieldMatchComparer.Compare(basePatient, selectedPatients[index]) < 2)
                    {
                        mismatchPatients.Add(selectedPatients[index].Key, selectedPatients[index]);
                    }
                }
            }
            if (mismatchPatients.Count > 0)
            {
                ShowMismatchPatientUI(mismatchPatients);
            }
            else
            {
                FindMatchingRequestors();
            }
        }

        private void createRequestButton_Click(object sender, EventArgs e)
        {
            createRequestHandler(sender, e);
        }

        /// <summary>
        /// Invokes server call for getting matching requestors.
        /// </summary>
        private void FindMatchingRequestors()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                if (UserData.Instance.EpnEnabled)
                {
                    foreach (PatientDetails pdetails in selectedPatients)
                    {
                        if ((pdetails.EPN == null) || (UserData.Instance.EpnPrefix == String.Empty))
                        {
                            pdetails.EPN = pdetails.EPN;
                        }
                        else
                        {                          
                            if (!(pdetails.EPN.Contains(UserData.Instance.EpnPrefix)))
                                pdetails.EPN = UserData.Instance.EpnPrefix + pdetails.EPN;
                        }
                        
                    }
                }
                Collection<RequestorDetails> requestors = PatientController.Instance.RetrieveMatchingRequestors(selectedPatients);
                ROIViewUtility.MarkBusy(false);

                if (requestors.Count > 0)
                {
                    ShowMatchingRequestorUI(requestors);
                }
                else
                {
                    ShowNoMatchingRequestorUI();
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
        /// Open model popup dialog - NoMatchingRequestor Dialog
        /// </summary>
        private void ShowNoMatchingRequestorUI()
        {
            NoMatchingRequestorUI noMatchingRequestorUI = new NoMatchingRequestorUI(Pane);
            CreateRequestInfo createRequestInfo = new CreateRequestInfo(new RequestDetails(), 
                                                                        patientPageContext.CancelCreateRequestHandler);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            noMatchingRequestorUI.Header = new HeaderUI();
            noMatchingRequestorUI.Header.Title = rm.GetString("noMatchingRequestor.header.title");
            noMatchingRequestorUI.Header.Information = rm.GetString("noMatchingRequestor.header.info");
            noMatchingRequestorUI.InfoLabel.Text = rm.GetString("noMatchingRequestor.info");

            noMatchingRequestorUI.SetData(createRequestInfo, selectedPatients);

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("noMatchingRequestor.titlebar.title"),
                                                     noMatchingRequestorUI);
            DialogResult result = form.ShowDialog(this);

            if (result == DialogResult.OK)
            {
                fire(noMatchingRequestorUI.CreateRequestInfo);
            }

            form.Close();
            form.Dispose();

        }

        /// <summary>
        /// Open model popup dialog - CreateMatchingRequestorDialogUI
        /// </summary>
        /// <param name="requestors"></param>
        private void ShowMatchingRequestorUI(Collection<RequestorDetails> requestors)
        {
            MatchingRequestorUI matchingRequestorUI = new MatchingRequestorUI(Pane);
       
            CreateRequestInfo createRequestInfo = new CreateRequestInfo(new RequestDetails(), patientPageContext.CancelCreateRequestHandler);

            RequestPatientDetails requestPatient;
            foreach (PatientDetails patient in selectedPatients)
            {
                requestPatient = new RequestPatientDetails(patient);
                if (!createRequestInfo.Request.Patients.ContainsKey(requestPatient.Key))
                {
                    createRequestInfo.Request.Patients.Add(requestPatient.Key, requestPatient);
                }
            }
            matchingRequestorUI.SetData(createRequestInfo, requestors);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("matchingRequestorUI.titlebar.title"), matchingRequestorUI);

            DialogResult result = form.ShowDialog(this);

            if (result == DialogResult.OK)
            {
                fire(matchingRequestorUI.CreateRequestInfo);
            }
            else if (result == DialogResult.Retry)
            {
                ShowCreateNewPatientRequestorDialog();
            }
            form.Close();
            form.Dispose();
        }

        /// <summary>
        /// Show Create New Patient Requestor Dialog
        /// </summary>
        private void ShowCreateNewPatientRequestorDialog()
        {
            NoMatchingRequestorUI noMatchingRequestorUI = new NoMatchingRequestorUI(Pane);
            CreateRequestInfo createRequestInfo = new CreateRequestInfo(new RequestDetails(),
                                                                        patientPageContext.CancelCreateRequestHandler);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            noMatchingRequestorUI.Header = new HeaderUI();
            noMatchingRequestorUI.Header.Title       = rm.GetString("createNewPatientRequestor.header.title");
            noMatchingRequestorUI.Header.Information = rm.GetString("createNewPatientRequestor.header.info");

            noMatchingRequestorUI.InfoLabel.Text = rm.GetString("createNewPatientRequestor.info");

            noMatchingRequestorUI.SetData(createRequestInfo, selectedPatients);
            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("createNewPatientRequestor.titlebar.title"), noMatchingRequestorUI);
            DialogResult result = form.ShowDialog(this);

            if (result == DialogResult.OK)
            {
                fire(noMatchingRequestorUI.CreateRequestInfo);
            }

            form.Close();
            form.Dispose();
        }

        /// <summary>
        /// Open model popup dialog - PotentialMismatchDialogUI
        /// </summary>
        private void ShowMismatchPatientUI(SortedList<string, PatientDetails> potentialMismatchPatients)
        {
            DialogResult result;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            MismatchPatientUI mismatchPatientUI = new MismatchPatientUI(Pane);
        
            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("title.MismatchPatientUI"), mismatchPatientUI);

            mismatchPatientUI.SetData(selectedPatients, potentialMismatchPatients);

            result = form.ShowDialog(this);

            form.Close();
            form.Dispose();

            if (result == DialogResult.OK)
            {
                selectedPatients = mismatchPatientUI.SelectedPatients;
                FindMatchingRequestors();
            }
        }

        private void fire(CreateRequestInfo createRequestInfo)
        {
            RequestEvents.OnCreateRequest(this, new ApplicationEventArgs(createRequestInfo, this));
        }


        #endregion

        #region Properties

        /// <summary>
        /// PageActions
        /// </summary>
        public Control PageActions
        {
            get { return pageActions; }
            set
            {
                pageActions = value;
                if (pageActions != null)
                {
                    leftPanel.Controls.Add(pageActions);
                }
            }
        }

        /// <summary>
        /// PatientRequestButton
        /// </summary>
        public Button PatientRequestButton
        {
            get { return patientRequestButton; }
        }
        
        public ToolTip ToolTip
        {
            get { return toolTip; }
        }

        /// <summary>
        /// CreateRequestButton
        /// </summary>
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

        public EventHandler PatientRequestHandler
        {
            get { return patientRequestHandler; }
        }

        #endregion

       
    }
}
