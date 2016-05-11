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

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;


namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    public class PatientRecordsMCP : ROIBasePane, ITransientDataApprover 
    {
        #region Fields

        private PatientRecordsMCPView patientRecordsUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the view of patientRecordsUI.
        /// </summary>
        protected override void InitView()
        {
            patientRecordsUI = new PatientRecordsMCPView();
            patientRecordsUI.ApplySecurityRights();
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
            patientRecordsUI.EnableEvents();
        }

        public void DisableEvents()
        {
            patientRecordsUI.DisableEvents();
        }

        /// <summary>
        /// Load the Prerequest data
        /// </summary>
        public void PrePopulate(object data)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                patientRecordsUI.PrePopulate(data);
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
            patientRecordsUI.SetData(data);
        }
        #endregion

        #region ITransientDataApprover Members
        /// <summary>
        /// Check wether the data is modified and display the
        /// confirm data loss dialog box whether for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true if user click OK button in dialog box.</returns>
        public bool Approve(ApplicationEventArgs ae)
        {
            if (!patientRecordsUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                patientRecordsUI.IsDirty = false;
                if (!((PatientRecordsMCPView)this.View).SaveSupplemental())
                {
                    patientRecordsUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {
                patientRecordsUI.SetData((this.ParentPane as PatientRecordsEditor).Patient);
                patientRecordsUI.IsDirty = false;
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
            get { return patientRecordsUI; }
        }

        #endregion
    }
}
