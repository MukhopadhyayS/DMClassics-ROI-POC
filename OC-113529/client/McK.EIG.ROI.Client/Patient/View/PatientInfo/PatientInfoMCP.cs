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

using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    public class PatientInfoMCP : ROIBasePane, ITransientDataApprover 
    {
        #region Fields

        private PatientInfoUI patientInfoUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the view of PatientInfoUI.
        /// </summary>
        protected override void InitView()
        {
            patientInfoUI = new PatientInfoUI();
            patientInfoUI.ApplySecurityRights();
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            TransientDataValidator.AddApprover(this);
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            TransientDataValidator.RemoveApprover(this);
        }

        public void EnableEvents()
        {
            patientInfoUI.EnableEvents();
        }

        public void DisableEvents()
        {
            patientInfoUI.DisableEvents();
        }

        /// <summary>
        /// Load the prerequest data
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                patientInfoUI.PrePopulate();
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
        /// Sets the data into view.
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            patientInfoUI.SetData(data);
        }

        #endregion

        #region ITransientDataApprover Members
        /// <summary>
        /// Check wether the data is modified and display the
        /// confirm data loss dialog box wether for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true if user click OK button in dialog box.</returns>
        public bool Approve(ApplicationEventArgs ae)
        {
            if (!patientInfoUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                patientInfoUI.IsDirty = false;
                if (!((PatientInfoUI)this.View).Save(null, null))
                {
                    patientInfoUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {                
                patientInfoUI.CancelPatientHandler(this, ae);
                patientInfoUI.IsDirty = false;
                return true;
            }

            return false;
        }

        #endregion

        #region Properties

        /// <summary>
        ///  Returns the view of PatientInfoMCP.
        /// </summary>
        public override Component View
        {
            get { return patientInfoUI; }
        }

        #endregion
    }
}
