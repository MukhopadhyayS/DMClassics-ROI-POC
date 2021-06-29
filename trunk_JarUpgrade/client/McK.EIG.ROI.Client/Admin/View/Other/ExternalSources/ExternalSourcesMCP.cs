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
using System.Collections;
using System.Collections.ObjectModel;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Admin.View.Configuration.ExternalSources
{
   public class ExternalSourcesMCP : ROIBasePane 
    {
        #region Fields

        private static ExternalSourcesListUI externalSourceUI;
        private EventHandler odpDataChangeHandler;
        private EventHandler cancelModifyHandler;
       
        #endregion

        #region Methods

        /// <summary>
        /// Initilize the view of ExternalSourcesMCPUI.
        /// </summary>
        protected override void InitView()
        {
            externalSourceUI = new ExternalSourcesListUI();
        }

        protected override void Subscribe()
        {
            odpDataChangeHandler = new EventHandler(Process_DataChange);
            cancelModifyHandler = new EventHandler(Process_OnCancelModify);
            AdminEvents.ODPDataChange += odpDataChangeHandler;
            AdminEvents.CancelModify += cancelModifyHandler;
        }

        protected override void Unsubscribe()
        {            
            AdminEvents.ODPDataChange -= odpDataChangeHandler;
            AdminEvents.CancelModify -= cancelModifyHandler;
        }
               
        /// <summary>
        /// Load the External Sources
        /// </summary>
        public void PrePopulate()
        {
                ROIViewUtility.MarkBusy(true);
                Collection<ExternalSource> externalsources = ROIAdminController.Instance.DisplayExternalSources();
                
                if (externalsources != null)
                {
                    externalSourceUI.SetData(externalsources);
                }
                ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Sets the data into view.
        /// </summary>
        /// <param name="data"></param>
        public void SetData(Collection<ExternalSource> data)
        {
            externalSourceUI.SetData(data);
        }

        private void Process_OnCancelModify(object sender, EventArgs e)
        {
            externalSourceUI.EnableButton();            
        }

        private void Process_DataChange(object sender, EventArgs e)
        {
            externalSourceUI.DisableButton();            
        }

       
        #endregion

        #region Properties

        /// <summary>
        ///  Returns the view of ExternalSourcesMCP.
        /// </summary>
        public override Component View
        {
            get { return externalSourceUI; }
        }
        #endregion
    }
}
