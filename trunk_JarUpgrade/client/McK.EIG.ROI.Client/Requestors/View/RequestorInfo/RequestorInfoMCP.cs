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
using System.ComponentModel;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Requestors.View.RequestorInfo
{
    public class RequestorInfoMCP : ROIBasePane, ITransientDataApprover
    {
        #region Fields
        
        private RequestorInfoUI reqInfoUI;

        #endregion

        #region Methods

        protected override void InitView()
        {
            reqInfoUI = new RequestorInfoUI();
            reqInfoUI.ApplySecurityRights();
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

        /// <summary>
        /// Gets the Requestor collection from datastore.
        /// </summary>
        public void PrePopulate(object data)
        {
            ROIViewUtility.MarkBusy(true);
            Collection<RequestorTypeDetails> requestorTypes = (data == null) ?
                new Collection<RequestorTypeDetails>() : data as Collection<RequestorTypeDetails>;
         
            if (requestorTypes != null)
            {
                reqInfoUI.PrePopulate(requestorTypes);
            }
        }

        public override void SetData(object data)
        {
            reqInfoUI.SetData(data);
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
            if (!reqInfoUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                reqInfoUI.IsDirty = false;
                if (!((RequestorInfoUI)this.View).Save(null, null))
                {
                    reqInfoUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {                
                reqInfoUI.CancelRequestorHandler(this, ae);
                reqInfoUI.IsDirty = false;
                return true;
            }

            return false;

        }

        #endregion

        #region Properties

        public override Component View
        {
            get { return reqInfoUI; }
        }

        #endregion

    }
}
