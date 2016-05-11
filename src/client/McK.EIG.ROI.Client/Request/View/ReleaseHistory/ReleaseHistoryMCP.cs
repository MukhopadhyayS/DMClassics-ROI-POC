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
using System.Resources;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.ReleaseHistory
{
    public class ReleaseHistoryMCP : ROIBasePane
    {
        #region Fields

        private ReleaseHistoryListUI releaseHistoryListUI;

        #endregion

        #region Methods

        protected override void InitView()
        {
            releaseHistoryListUI = new ReleaseHistoryListUI();
            releaseHistoryListUI.ApplySecurityRights();
        }

        /// <summary>
        /// Prepopulates the Release History details.
        /// </summary>
        /// <param name="requestId"></param>
        public void PrePopulate(long requestId, string requestPassword)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string selfPayEncounter = rm.GetString("Self Pay Encounter");
            ReleaseHistoryDetails releaseHistoryDetails = BillingController.Instance.RetrieveReleaseHistoryList(requestId, requestPassword,selfPayEncounter);
            releaseHistoryListUI.PrePopulate(releaseHistoryDetails);
        }

        #endregion

        #region Properties

        public override Component View
        {
            get { return releaseHistoryListUI; }
        }

        #endregion
    }
}
