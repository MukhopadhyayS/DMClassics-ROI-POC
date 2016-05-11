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
using System.Collections.ObjectModel;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.View.Comments;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.EventHistory
{
    /// <summary>
    /// Holds the Request Comment Info UI
    /// </summary>
    public class EventHistoryMCP : ROIBasePane
    {
        #region Fields

        private EventHistoryListUI eventHistoryListUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the CommentInfoUI.
        /// </summary>
        protected override void InitView()
        {
            eventHistoryListUI = new EventHistoryListUI();
            eventHistoryListUI.ApplySecurityRights();
        } 

        public void PrePopulate(Collection<RequestEventHistoryDetails> data, string[] requestEvents)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                if (data != null)
                {
                    ComparableCollection<RequestEventHistoryDetails> list = new ComparableCollection<RequestEventHistoryDetails>(data);
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestEventHistoryDetails))["EventDate"], ListSortDirection.Descending);
                    eventHistoryListUI.PrePopulate(list,requestEvents);                    
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

        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of CommentInfoUI.
        /// </summary>    
        public override Component View
        {
            get { return eventHistoryListUI; }
        }

        #endregion
    }
}
