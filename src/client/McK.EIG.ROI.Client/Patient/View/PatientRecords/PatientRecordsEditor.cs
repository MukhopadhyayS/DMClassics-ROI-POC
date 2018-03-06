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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.View;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    /// <summary>
    /// PatientRecordsEditor
    /// </summary>
    public class PatientRecordsEditor : ROIEditor
    {
        #region Fields

        private EventHandler patientUpdated;
        private EventHandler requestReleased;

        private PatientDetails patient;
        private PatientHeaderUI patientHeaderUI;

        #endregion

        #region Constructor

        public PatientRecordsEditor() { }

        #endregion

        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();

            patient = ((PatientRSP)ParentPane).InfoEditor.Patient;
            HeaderUI headerUI = SubPanes[0].View as HeaderUI;
            patientHeaderUI = new PatientHeaderUI();
            patientHeaderUI.PopulatePatientInformation(patient);
            headerUI.HeaderExtension = patientHeaderUI;
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
        /// Subscribe the events
        /// </summary>
        protected override void Subscribe()
        {
            patientUpdated = new EventHandler(Process_UpdatePatientInfo);
            requestReleased = new EventHandler(Process_RequestReleased);

            PatientEvents.PatientUpdated += patientUpdated;
            RequestEvents.RequestReleased += requestReleased;
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        protected override void Unsubscribe()
        {
            PatientEvents.PatientUpdated -= patientUpdated;
            RequestEvents.RequestReleased -= requestReleased;
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
            ((PatientRecordsMCPView)MCP.View).UpdatePatient(patient);
        }

        private void Process_RequestReleased(object sender, EventArgs e)
        {

            try
            {
                if (patient.IsHpf)
                {
//                    patient = PatientController.Instance.RetrieveSupplementalDocuments(patient);
                }
                else
                {
//                    patient = PatientController.Instance.RetrieveSupplementalInfo(patient.Id);
                }

                ((PatientRecordsMCPView)MCP.View).UpdatePatient(patient);

            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// PrePopulate RecordViews for this user.
        /// </summary>
        public override void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);

                PatientRecordsMCP patientRecordsMCP = SubPanes[1] as PatientRecordsMCP;

                Collection<RecordViewDetails> recordViews;

                if (patient.IsHpf)
                {
                    recordViews = UserData.Instance.RecordViews;
                    //Pre Populate RecordViews
                    patientRecordsMCP.PrePopulate(recordViews);

                    //if (ROIViewUtility.IsAllowed(ROISecurityRights.RecordViews))
                    //{
                       patient =  PatientController.Instance.RetrieveHpfDocuments(patient);
                       PatientDetailsCache.AddData(patient.MRN + patient.facilityCode, patient);
                    //}

//                    PatientController.Instance.RetrieveSupplementalDocuments(patient);
                }
                else
                {
                    patientRecordsMCP.EnableEvents();
                }

                patientRecordsMCP.SetData(patient);
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
        /// Cleanup
        /// </summary>
        public override void Cleanup()
        {
            base.Cleanup();
            base.View.Dispose();
            // nothing to unsubscribe and current view will be disposed when the Editor is recreated
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the Patient Records Editor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "patientrecords.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the Patient Records Editor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "patientrecords.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(PatientRecordsMCP); }
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
        /// Holds patient info object.
        /// </summary>
        public PatientDetails Patient
        {
            get { return patient; }
            set { patient = value; }
        }

        #endregion
    }
}
