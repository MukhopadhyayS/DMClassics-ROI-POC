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

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;
using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportRSP
    /// </summary>
    public class ReportRSP : ROIRightSidePane
    {

        #region Fields

        private ReportEditor reportEditor;

        private EventHandler runReport;
        private EventHandler clearEditor;
        private EventHandler paramsChanged;

        #endregion

        #region Methods

        /// <summary>
        /// Subscribes the events.
        /// </summary>
        protected override void Subscribe()
        {
            runReport     = new EventHandler(Process_RunReport);
            clearEditor   = new EventHandler(Process_ClearEditor);
            paramsChanged = new EventHandler(Process_LSPParamsChanged);

            ReportEvents.RunReport        += runReport;
            ReportEvents.ClearEditor      += clearEditor;
            ReportEvents.LSPParamsChanged += paramsChanged;

        }

        /// <summary>
        /// Unsubscribes the events.
        /// </summary>
        protected override void Unsubscribe()
        {
            ReportEvents.RunReport        -= runReport;
            ReportEvents.ClearEditor      -= clearEditor;
            ReportEvents.LSPParamsChanged -= paramsChanged;
        }

        /// <summary>
        /// Sets Report editor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RunReport(object sender, EventArgs e)
        {
            if (reportEditor == null)
            {
                reportEditor = new ReportEditor();
            }
            reportEditor.ReportDetails = (ReportDetails)(e as ApplicationEventArgs).Info;
            SetCurrentEditor(reportEditor);
        }

        /// <summary>
        /// Clears editor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_ClearEditor(object sender, EventArgs e)
        {
            if (reportEditor != null)
            {
                reportEditor.Cleanup();
            }
        }

        /// <summary>
        /// Disables the UI.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_LSPParamsChanged(object sender, EventArgs e)
        {
            if (reportEditor != null)
            {
                ((ReportViewerUI)reportEditor.MCP.View).Enabled = false;
                ((ReportViewerUI)reportEditor.MCP.View).RetrieveFooterUI().Enabled = false;
            }
        }

        #endregion
    }
}
