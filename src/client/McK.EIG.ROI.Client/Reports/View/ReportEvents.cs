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

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportEvents
    /// </summary>
    public static class ReportEvents
    {
        #region Fields

        public static event EventHandler InputFieldChanged;
        public static event EventHandler DateRangeChanged;
        public static event EventHandler RunReport;
        public static event EventHandler ClearEditor;
        public static event EventHandler LSPParamsChanged;

        #endregion

        #region Methods

        /// <summary>
        /// Occurs when Request Information Click on LSP.        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="ae"></param>
        internal static void OnRunReport(object sender, EventArgs e)
        {
            if (RunReport != null)
            {
                RunReport(sender, e);
            }
        }

        internal static void OnClearEditor(object sender, EventArgs e)
        {
            if (ClearEditor != null)
            {
                ClearEditor(sender, e);
            }
        }

        internal static void OnLSPParamsChanged(object sender, EventArgs e)
        {
            if (LSPParamsChanged != null)
            {
                LSPParamsChanged(sender, e);
            }
        }

        internal static void OnDateRangeChanged(object sender, EventArgs e)
        {
            if (DateRangeChanged != null)
            {
                DateRangeChanged(sender, e);
            }
        }
        internal static void OnInputFieldChanged(object sender, EventArgs e)
        {
            if (InputFieldChanged != null)
            {
                InputFieldChanged(sender, e);
            }
        }
        
        #endregion
    }
}
