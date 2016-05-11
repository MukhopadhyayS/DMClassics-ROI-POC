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
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.Letters
{
    public class LetterInfoMCP : ROIBasePane
    {
        #region Fields

        private LetterInfoUI letterInfoUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the CommentInfoUI.
        /// </summary>
        protected override void InitView()
        {
            letterInfoUI = new LetterInfoUI();
            letterInfoUI.Dock = DockStyle.Fill;

            letterInfoUI.ApplySecurityRights();
        }

        /// <summary>
        /// Prepopulates the invoice histories.
        /// </summary>
        /// <param name="requestId"></param>
        public void PrePopulate(RequestDetails requestDetails, ReleaseDetails releaseDetails)
        {
            letterInfoUI.PrePopulate(requestDetails, releaseDetails);
        }
        
        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of LetterInfoUI
        /// </summary>    
        public override Component View
        {
            get { return letterInfoUI; }
        }

        #endregion   
    }
}
