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

namespace McK.EIG.ROI.Client.Request.View.Comments
{
    /// <summary>
    /// Holds the Request Comment Info UI
    /// </summary>
    public class RequestCommentMCP : ROIBasePane, ITransientDataApprover
    {
        #region Fields

        private RequestCommentInfoUI requestCommentInfoUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the CommentInfoUI.
        /// </summary>
        protected override void InitView()
        {
            requestCommentInfoUI = new RequestCommentInfoUI();
            requestCommentInfoUI.ApplySecurityRights();
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

        #region ITransientDataApprover Members
        /// <summary>
        /// Check wether the data is modified and display the
        /// confirm data loss dialog box wether for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true if user click OK button in dialog box.</returns>
        public bool Approve(ApplicationEventArgs ae)
        {           
            if (!requestCommentInfoUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                requestCommentInfoUI.IsDirty = false;
                if (!((RequestCommentInfoUI)this.View).Save())
                {
                    requestCommentInfoUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {                
                ((RequestCommentInfoUI)this.View).CancelComment();
                requestCommentInfoUI.IsDirty = false;
                return true;
            }

            return false;
        }

        #endregion

        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of CommentInfoUI.
        /// </summary>    
        public override Component View
        {
            get { return requestCommentInfoUI; }
        }

        #endregion
    }
}
