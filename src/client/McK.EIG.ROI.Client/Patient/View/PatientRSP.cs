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
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.View.FindPatient;
using McK.EIG.ROI.Client.Patient.View.PatientInfo;
using McK.EIG.ROI.Client.Patient.View.PatientRecords;
using McK.EIG.ROI.Client.Patient.View.RequestHistory;

namespace McK.EIG.ROI.Client.Patient.View
{
    /// <summary>
    /// Used to hold an Editor.
    /// </summary>
    public class PatientRSP : ROIRightSidePane
    {
        #region Fields

        private EventHandler navigateFindPatientHandler;
        private EventHandler navigatePatientInfoHandler;
        private EventHandler navigatePatientRecordsHandler;
        private EventHandler navigateRequestHistoryHandler;

        private FindPatientEditor findEditor;
        private PatientInfoEditor infoEditor;
        private PatientRecordsEditor recordsEditor;
        private RequestHistoryEditor requestHistoryEditor;

        private EventHandler createRequestHandler;
        private EventHandler patientRequestHandler;

        #endregion

        #region Methods

        /// <summary>
        ///  Event subscription.
        /// </summary>
        protected override void Subscribe()
        {
            navigateFindPatientHandler = new EventHandler(Process_NavigateFindPatient);
            navigatePatientInfoHandler = new EventHandler(Process_NavigatePatientInfo);
            navigatePatientRecordsHandler = new EventHandler(Process_NavigatePatientRecords);
            navigateRequestHistoryHandler = new EventHandler(Process_NavigateRequestHistory);

            createRequestHandler = new EventHandler(Process_CreateRequest);
            patientRequestHandler = new EventHandler(Process_PatientRequest);
            
            PatientEvents.NavigatePatientInfo += navigatePatientInfoHandler;
            PatientEvents.NavigateFindPatient += navigateFindPatientHandler;
            PatientEvents.NavigatePatientRecords += navigatePatientRecordsHandler;
            PatientEvents.NavigateRequestHistory += navigateRequestHistoryHandler;

            PatientEvents.CreateRequest += createRequestHandler;
            PatientEvents.PatientRequest += patientRequestHandler;
        }

        /// <summary>
        ///  Event unsubscription.
        /// </summary>
        protected override void Unsubscribe()
        {
            PatientEvents.NavigateFindPatient -= navigateFindPatientHandler;
            PatientEvents.NavigatePatientInfo -= navigatePatientInfoHandler;
            PatientEvents.NavigatePatientRecords -= navigatePatientRecordsHandler;
            PatientEvents.NavigateRequestHistory -= navigateRequestHistoryHandler;

            PatientEvents.CreateRequest -= createRequestHandler;
            PatientEvents.PatientRequest -= patientRequestHandler;
        }

        /// <summary>
        /// Process the event of FindPatient click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateFindPatient(object sender, EventArgs e)
        {
            if (findEditor != null && findEditor == currentEditor)  return;

            bool init = (findEditor == null);

            if (init)
            {
                findEditor = new FindPatientEditor();
            }

            SetCurrentEditor(findEditor, init);
            //infoEditor = null;
            //recordsEditor = null;
            //requestHistoryEditor = null;
        }

        /// <summary>
        /// Process the event of PatientInfo click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigatePatientInfo(object sender, EventArgs e)
        {
            if (infoEditor != null && infoEditor == currentEditor) return;

            bool init = (infoEditor == null);

            if (init)
            {
                infoEditor = new PatientInfoEditor();
            }

            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if(ae.Info is PatientDetails)
            {
                infoEditor.Patient = (PatientDetails)ae.Info;
            }
            
            SetCurrentEditor(infoEditor, init);
            infoEditor.SetData(infoEditor.Patient);
        }

        protected override void ClearCurrentEditor(BasePane currentEditor, BasePane newEditor)
        {
            if (currentEditor == null) return;

            if (newEditor == FindEditor)
            {
                if (infoEditor != null)
                {
                    infoEditor.Cleanup();
                    infoEditor = null;
                }

                if (recordsEditor != null)
                {
                    recordsEditor.Cleanup();
                    recordsEditor = null;
                }

                if (requestHistoryEditor != null)
                {
                    requestHistoryEditor.Cleanup();
                    requestHistoryEditor = null;
                }
            }
        }

        /// <summary>
        ///  Process the event of Patient Records click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigatePatientRecords(object sender, EventArgs e)
        {
            if (recordsEditor != null && recordsEditor == currentEditor) return;

            bool init = (recordsEditor == null);

            if (init)
            {
                recordsEditor = new PatientRecordsEditor();
            }

            SetCurrentEditor(recordsEditor, init);
        }

        /// <summary>
        ///  Process the event of Patient Request History click on LSP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NavigateRequestHistory(object sender, EventArgs e)
        {
            if (requestHistoryEditor != null && requestHistoryEditor == currentEditor) return;

            bool init = (requestHistoryEditor == null);

            if (init)
            {
                requestHistoryEditor = new RequestHistoryEditor();
            }

            SetCurrentEditor(requestHistoryEditor, init);
        }

        private void Process_CreateRequest(object sender, EventArgs e)
        {
            if(typeof(PatientInfoUI).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((PatientInfoUI)currentEditor.MCP.View).Footer.CreateRequestHandler(sender, e);
            }
            else if (typeof(PatientRecordsMCPView).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((PatientRecordsMCPView)currentEditor.MCP.View).Footer.CreateRequestHandler(sender, e);
            }
            else if (typeof(RequestHistoryUI).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((RequestHistoryUI)currentEditor.MCP.View).Footer.CreateRequestHandler(sender, e);
            }
        }

        private void Process_PatientRequest(object sender, EventArgs e)
        {
            if (typeof(PatientInfoUI).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((PatientInfoUI)currentEditor.MCP.View).Footer.PatientRequestHandler(sender, e);
            }
            else if (typeof(PatientRecordsMCPView).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((PatientRecordsMCPView)currentEditor.MCP.View).Footer.PatientRequestHandler(sender, e);
            }
            else if (typeof(RequestHistoryUI).IsAssignableFrom(currentEditor.MCP.View.GetType()))
            {
                ((RequestHistoryUI)currentEditor.MCP.View).Footer.PatientRequestHandler(sender, e);
            }
        }

        #endregion
        
        #region Properties

        public FindPatientEditor FindEditor
        {
            get { return findEditor;  }
            set { findEditor = value; }
        }

        public PatientInfoEditor InfoEditor
        {
            get { return infoEditor; }
            set { infoEditor = value; }
        }

        public PatientRecordsEditor RecordsEditor
        {
            get { return recordsEditor; }
            set { recordsEditor = value; }
        }

        public RequestHistoryEditor HistoryEditor
        {
            get { return requestHistoryEditor; }
            set { requestHistoryEditor = value; }
        }

        #endregion
    }
}
