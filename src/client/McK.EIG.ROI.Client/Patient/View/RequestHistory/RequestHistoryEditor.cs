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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Patient.View.RequestHistory
{
    public class RequestHistoryEditor : ROIEditor
    {
        #region Fields

        private EventHandler patientUpdated;

        private PatientDetails patientInfo;
        private PatientHeaderUI patientHeaderUI;

        #endregion

        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();

            RequestHistoryUI requestHistoryUI = (RequestHistoryUI)MCP.View;
            requestHistoryUI.AddListPanel();

            patientInfo = ((PatientRSP)ParentPane).InfoEditor.Patient;
            HeaderUI headerUI = HeaderPane.View as HeaderUI;
            patientHeaderUI = new PatientHeaderUI();
            patientHeaderUI.PopulatePatientInformation(patientInfo);
            headerUI.HeaderExtension = patientHeaderUI;
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        protected override void Subscribe()
        {
            patientUpdated = new EventHandler(Process_UpdatePatientInfo);
            PatientEvents.PatientUpdated += patientUpdated;
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        protected override void Unsubscribe()
        {
            PatientEvents.PatientUpdated -= patientUpdated;
        }

        /// <summary>
        /// Updates the patient's information on patient information box
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdatePatientInfo(object sender, EventArgs e)
        {
            PatientDetails patient = (PatientDetails)((ApplicationEventArgs)e).Info;
            patientHeaderUI.PopulatePatientInformation(patient);
        }

        public override void PrePopulate()
        {
            ROIViewUtility.MarkBusy(true);

            if (patientInfo.IsHpf)
            {
                patientInfo = PatientController.Instance.RetrieveHpfEncounters(patientInfo);
            }

            ROIViewUtility.MarkBusy(false);

            ((RequestHistoryMCP)MCP).PrePopulate(patientInfo);
        }

        /// <summary>
        /// Localize Header UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            patientHeaderUI.Localize(Context);
        }

        /// <summary>
        /// Cleanup
        /// </summary>
        public override void Cleanup()
        {
            base.Cleanup();
            base.View.Dispose();
            //base.Cleanup();
            // nothing to unsubscribe and current view will be disposed when the Editor is recreated
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the title text of the request history.
        /// </summary>
        protected override string TitleText
        {
            get { return "patient.requesthistory.header.title"; }
        }

        /// <summary>
        /// Returns the title information of the request history.
        /// </summary>
        protected override string InfoText
        {
            get { return "patient.requesthistory.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(RequestHistoryMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        protected override Type ODPType
        {
            get { return null; }
        }

        public PatientDetails PatientInfo
        {
            get { return patientInfo; }
        }

        #endregion
    }
}
