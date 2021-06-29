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

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.Comments
{
    /// <summary>
    /// Holds the list UI
    /// </summary>
    public class RequestCommentODP : ROIBasePane
    {
        #region Fields

        private EventHandler createComment;
        
        private RequestCommentListUI requestCommentListUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the CommentListUI.
        /// </summary>
        protected override void InitView()
        {
            requestCommentListUI = new RequestCommentListUI();
            requestCommentListUI.ApplySecurityRights();
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            createComment = new EventHandler(Process_CreateComment);
            RequestEvents.CommentCreated += createComment;
        }

         /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestEvents.CommentCreated -= createComment;
        }

        /// <summary>
        /// PrePopulate Author and Comment.
        /// </summary>
        public void PrePopulate(Collection<CommentDetails> data)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                if (data != null)
                {
                    ComparableCollection<CommentDetails> list = new ComparableCollection<CommentDetails>(data);
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(CommentDetails))["EventDate"], ListSortDirection.Descending);
                    requestCommentListUI.PrePopulate(list);
                }
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
        /// Event invoked when comment created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreateComment(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = e as ApplicationEventArgs;
            requestCommentListUI.SetData(ae.Info as CommentDetails);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of CommentListUI.
        /// </summary>
        public override Component View
        {
            get { return requestCommentListUI; }
        }

        #endregion
    }
}
