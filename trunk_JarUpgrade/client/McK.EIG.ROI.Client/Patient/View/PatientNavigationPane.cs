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
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Patient.View
{
    public class PatientNavigationPane : NavigationPane
    {
        #region Fields

        private EventHandler createNewPatient;
        private EventHandler modifyPatient;
        private EventHandler patientDeleted;
        private EventHandler patientCreated;

        private bool canAccessRequest;

        #endregion

        #region Methods

        protected override void InitView()
        {
            base.InitView();

            navigationLinks.EnableLink(ROIConstants.PatientsInformation, ROIViewUtility.IsAllowed(ROISecurityRights.MasterPatientIndexSearch));

            canAccessRequest = (ROIViewUtility.IsAllowed(ROISecurityRights.ROIViewRequest) ||
                                ROIViewUtility.IsAllowed(ROISecurityRights.ROICreateRequest) ||
                                ROIViewUtility.IsAllowed(ROISecurityRights.ROIModifyRequest));

            EnableLinks(false);
        }

        protected override void Subscribe()
        {
            base.Subscribe();
            createNewPatient = new EventHandler(Process_CreateNewPatient);
            modifyPatient    = new EventHandler(Process_PatientSelected);
            patientDeleted   = new EventHandler(Process_PatientDeleted);
            patientCreated   = new EventHandler(Process_PatientCreated);

            PatientEvents.CreatePatient += createNewPatient;
            PatientEvents.PatientSelected += modifyPatient;
            PatientEvents.PatientDeleted  += patientDeleted;
            PatientEvents.PatientCreated  += patientCreated;
        }

        protected override void Unsubscribe()
        {
            base.Unsubscribe();
            PatientEvents.CreatePatient   -= createNewPatient;
            PatientEvents.PatientSelected -= modifyPatient;
            PatientEvents.PatientDeleted  -= patientDeleted;
            PatientEvents.PatientCreated  -= patientCreated;
        }

        private void Process_CreateNewPatient(object sender, EventArgs e)
        {
            if (((IPane)sender).ParentPane.ParentPane == null) return;
            navigationLinks.EnableLink(ROIConstants.PatientsInformation, true);
            navigationLinks.EnableLink(ROIConstants.PatientsRecords, false);
            navigationLinks.EnableLink(ROIConstants.PatientsRequestHistory, false);
            navigationLinks.SelectLink(ROIConstants.PatientsInformation);
            PatientEvents.OnNavigatePatientInfo(sender, e);
        }

        private void Process_PatientSelected(object sender, EventArgs e)
        {
            EnableLinks(true);

             //Patient Information editor will be initialized again

            PatientRSP rsp = (PatientRSP)((PatientModulePane)ParentPane.ParentPane).RSP;
           // if (!typeof(McK.EIG.ROI.Client.Patient.View.FindPatient.FindPatientODP).IsAssignableFrom(sender.GetType()))
            {
                //if (rsp.FindEditor != null)
                //{
                //    rsp.FindEditor.Cleanup();
                //    rsp.FindEditor = null;
                //}

                if (rsp.InfoEditor != null)
                {
                    rsp.InfoEditor.Cleanup();
                    rsp.InfoEditor = null;
                }

                if (rsp.RecordsEditor != null)
                {
                    rsp.RecordsEditor.Cleanup();
                    rsp.RecordsEditor = null;
                }

                if (rsp.HistoryEditor != null)
                {
                    rsp.HistoryEditor.Cleanup();
                    rsp.HistoryEditor = null;
                }
            }

            navigationLinks.SelectLink(ROIConstants.PatientsInformation);
            PatientEvents.OnNavigatePatientInfo(sender, e);
        }

        private void Process_PatientCreated(object sender, EventArgs e)
        {
            EnableLinks(true);
        }

        private void Process_PatientDeleted(object sender, EventArgs e)
        {
            EnableLinks(false);
            navigationLinks.SelectLink(ROIConstants.FindPatients);
            PatientEvents.OnNavigateFindPatient(sender, e);
        }

        private void EnableLinks(bool enable)
        {           
            navigationLinks.EnableLink(ROIConstants.PatientsInformation,    enable);
            navigationLinks.EnableLink(ROIConstants.PatientsRecords,        enable);
            navigationLinks.EnableLink(ROIConstants.PatientsRequestHistory, enable && canAccessRequest);            
        }

        protected override void ProcessNavigation(string selected, ApplicationEventArgs ae)
        {
            if (selected == ROIConstants.FindPatients)
            {
                PatientEvents.OnNavigateFindPatient(this, ae);
                EnableLinks(false);
            }
            else if (selected == ROIConstants.PatientsInformation)
            {
                PatientEvents.OnNavigatePatientInfo(this, ae);
            }
            else if (selected == ROIConstants.PatientsRecords)
            {
                PatientEvents.OnNavigatePatientRecords(this, ae);
            }
            else if (selected == ROIConstants.PatientsRequestHistory)
            {
                PatientEvents.OnNavigateRequestHistory(this, ae);
            }
        }

        #endregion

        #region Properties

        protected override Collection<string> LinkKeys
        {
            get
            {
                Collection<string> links = new Collection<string>();
                links.Add(ROIConstants.FindPatients);
                links.Add(ROIConstants.PatientsInformation);
                links.Add(ROIConstants.PatientsRecords);
                links.Add(ROIConstants.PatientsRequestHistory);
                return links;
            }
        }

        public override string Title
        {
            get { return ROIConstants.PatientsModuleName; }
        }

        #endregion
    }
}
