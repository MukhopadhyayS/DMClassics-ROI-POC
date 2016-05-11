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
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Holds the list UI
    /// </summary>
    public abstract class AdminMCP : ROIBasePane
    {
        #region Fields

        internal AdminBaseListUI adminListUI;

        private EventHandler odpDataChangeHandler;
        private EventHandler saveCreateHandler;
        private EventHandler saveModifyHandler;
        private EventHandler cancelCreateHandler;
        private EventHandler cancelModifyHandler;

        #endregion

        #region Methods
        /// <summary>
        /// Initializes the View of list pane.
        /// </summary>
        protected override void InitView()
        {
            adminListUI = View as AdminBaseListUI;
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            odpDataChangeHandler = new EventHandler(Process_DataChange);
            saveCreateHandler    = new EventHandler(Process_OnCreate);
            saveModifyHandler    = new EventHandler(Process_OnModify);
            cancelCreateHandler  = new EventHandler(Process_OnCancelCreate);
            cancelModifyHandler  = new EventHandler(Process_OnCancelModify);

            AdminEvents.ODPDataChange += odpDataChangeHandler;
            AdminEvents.Create        += saveCreateHandler;
            AdminEvents.Modify        += saveModifyHandler;
            AdminEvents.CancelCreate  += cancelCreateHandler;
            AdminEvents.CancelModify  += cancelModifyHandler;
        }

        /// <summary>
        /// Event unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            AdminEvents.Create        -= saveCreateHandler;
            AdminEvents.Modify        -= saveModifyHandler;
            AdminEvents.CancelCreate  -= cancelCreateHandler;
            AdminEvents.CancelModify  -= cancelModifyHandler;
            AdminEvents.ODPDataChange -= odpDataChangeHandler;
        }

        /// <summary>
        /// Occurs when the Save Button is Clicked in the Admin ODP UserControl during create.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_OnCreate(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            adminListUI.AddRow(ae.Info);
        }

        /// <summary>
        /// Occurs when the Save Button is Clicked in the Admin ODP UserControl during update.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_OnModify(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            adminListUI.UpdateRow(ae.Info);
        }

        /// <summary>
        /// Occurs when the Cancel Button is Clicked in the Admin ODP UserControl during create.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_OnCancelCreate(object sender, EventArgs e)
        {
            adminListUI.CancelCreate(sender, e);
        }

        /// <summary>
        /// Occurs when the Cancel Button is Clicked in the Admin ODP UserControl during update.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_OnCancelModify(object sender, EventArgs e)
        {
            adminListUI.EnableDelete();
            adminListUI.EnableCreate();
        }

        /// <summary>
        /// Occurs when the the ODP fields are being edited
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_DataChange(object sender, EventArgs e)
        {
            adminListUI.DisableCreate();
            adminListUI.DisableDelete();
        }

        #endregion

    }
}
