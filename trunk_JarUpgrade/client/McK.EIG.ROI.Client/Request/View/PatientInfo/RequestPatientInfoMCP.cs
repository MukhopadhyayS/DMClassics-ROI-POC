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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    public class RequestPatientInfoMCP : ROIBasePane, ITransientDataApprover 
    {
        #region Fields

        private RequestPatientInfoUI requestPatientInfoUI;

        #endregion

        #region Methods

        protected override void InitView()
        {
            requestPatientInfoUI = new RequestPatientInfoUI();
            requestPatientInfoUI.ApplySecurityRights();
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

        public override void SetData(object data)
        {
            requestPatientInfoUI.SetData(data);
        }

        internal void PrePopulate(object data)
        {
            requestPatientInfoUI.PrePopulate(data);
        }

        #endregion

        #region ITransientDataApprover Members
        /// <summary>
        /// Check wether the data is modified and display the
        /// confirm data loss dialog box for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true, if user click OK button in dialog box.</returns>
        public bool Approve(ApplicationEventArgs ae)
        {
            requestPatientInfoUI.UnsubscribePatientSelection();
            if (!requestPatientInfoUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {   
                requestPatientInfoUI.IsDirty = false;
                if (!((RequestPatientInfoUI)this.View).Save(true))
                {
                    requestPatientInfoUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {
                requestPatientInfoUI.SetData(((RequestPatientInfoEditor)ParentPane).Request);
                
                requestPatientInfoUI.IsDirty = false;
                
                bool enableBillingLink = requestPatientInfoUI.ShowPreviousRelease ||
                                             requestPatientInfoUI.SaveBillButton.Enabled;
                RequestEvents.OnDsrChanged(this, new ApplicationEventArgs(enableBillingLink, this));
                if (ROIConstants.BillingAndPayment.Equals(ae.Info))
                {
                    return enableBillingLink;
                }

                return true;
            }

            return false;
        }

        #endregion

        #region Properties

        public override Component View
        {
            get { return requestPatientInfoUI; }
        }

        #endregion

    }
}
