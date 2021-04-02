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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.View.FindPatient;

namespace McK.EIG.ROI.Client.Patient.View.FindPatient
{
    /// <summary>
    /// Holds the Patient List UI.
    /// </summary>
    public class FindPatientODP : ROIBasePane
    {
        #region Fields

        private EventHandler createPatient;
        private EventHandler searchReset;
        private EventHandler patientSearched;
        private EventHandler patientDeleted;
        private EventHandler patientUpdated;

        private PatientListUI patientListUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize th Patient List UI.
        /// </summary>
        protected override void InitView()
        {
            patientListUI = new PatientListUI();
            patientListUI.ApplySecurityRights();
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            createPatient   = new EventHandler(Process_CreatePatient);
            searchReset     = new EventHandler(Process_SearchReset);
            patientSearched = new EventHandler(Process_PatientSearched);
            patientDeleted  = new EventHandler(Process_PatientDeleted);
            patientUpdated  = new EventHandler(Process_UpdateNonHpfPatient);

            PatientEvents.CreatePatient   += createPatient;
            PatientEvents.ResetSearch     += searchReset;
            PatientEvents.PatientSearched += patientSearched;
            PatientEvents.PatientDeleted  += patientDeleted;
            PatientEvents.PatientUpdated  += patientUpdated;
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            PatientEvents.CreatePatient   -= createPatient;
            PatientEvents.ResetSearch     -= searchReset;
            PatientEvents.PatientSearched -= patientSearched;
            PatientEvents.PatientDeleted  -= patientDeleted;
            PatientEvents.PatientUpdated  -= patientUpdated;
        }

        /// <summary>
        /// Event occurs when New Patient button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreatePatient(object sender, EventArgs e)
        {
            if (ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            patientListUI.ClearList();
        }

        /// <summary>
        /// Event occurs when Reset button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_SearchReset(object sender, EventArgs e)
        {
            if (ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            patientListUI.SetData(ae.Info);
        }

        /// <summary>
        /// Event occurs when Find Patient button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_PatientSearched(object sender, EventArgs e)
        {
            if (ParentPane.ParentPane != ((IPane)sender).ParentPane.ParentPane) return;
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            patientListUI.SetData(ae.Info);
        }

        /// <summary>
        /// Event occurs when Patient is Deleted.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_PatientDeleted(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                patientListUI.DeleteRow(ae.Info);
            }
        }

        /// <summary>
        /// Event occurs when Patient is Deleted.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateNonHpfPatient(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (ae.Info != null)
            {
                patientListUI.UpdateRow(ae.Info);
            }
        }
        
        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of FindPatientODP.
        /// </summary>        
        public override Component View
        {
            get { return patientListUI; }
        }

        #endregion
    }
}
