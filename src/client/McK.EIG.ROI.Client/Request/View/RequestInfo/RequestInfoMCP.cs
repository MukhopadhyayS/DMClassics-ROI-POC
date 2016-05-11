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
using System.Collections.ObjectModel;
using System.ComponentModel;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    public class RequestInfoMCP : ROIBasePane, ITransientDataApprover 
    {
        #region Fields

        private RequestInfoUI requestInfoUI;

        #endregion

        #region Methods

        protected override void InitView()
        {
            requestInfoUI = new RequestInfoUI();
            //requestInfoUI.ApplySecurityRights();
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
        /// Sets the Request collection from datastore.
        /// </summary>
        public void PrePopulate(object data)
        {
            ROIViewUtility.MarkBusy(true);
            Collection<ReasonDetails> reasons = (data == null) ?
                new Collection<ReasonDetails>() : data as Collection<ReasonDetails>;

            if (reasons != null)
            {
                requestInfoUI.PrePopulate(reasons);
            }
        }

        public override void SetData(object data)
        {
            requestInfoUI.SetData(data);
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
            if (!requestInfoUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                requestInfoUI.IsDirty = false;
                if (!((RequestInfoUI)View).Save())
                {
                    requestInfoUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {
                RequestDetails request = ((RequestInfoEditor)ParentPane).Request;
                if (request != null && request.Id > 0)
                {
                    ((RequestInfoUI)this.View).SetData(request);
                }
                requestInfoUI.IsDirty = false;
                return true;
            }

            return false;
        }

        #endregion

        #region Properties

        public override Component View
        {
            get { return requestInfoUI; }
        }

        #endregion
    }
}
