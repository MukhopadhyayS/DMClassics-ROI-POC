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
using System.Configuration;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.App
{
    public static class ROIAppManager
    {
        private static Log log = LogFactory.GetLogger(typeof(ROIAppManager));

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            ROIPane roiPane = null;
            try
            {
                roiPane = new ROIPane();
                //Win Xp look and feel
                Application.EnableVisualStyles();
                LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
                
                roiPane.Init(null);
                Application.ThreadException += delegate(object sender, System.Threading.ThreadExceptionEventArgs e)
                {   
                    ROIViewUtility.Handle(roiPane.Context, new ROIException(ROIErrorCodes.Unknown, e.Exception));
                    log.FunctionFailure(e.Exception);

                };

                Application.Run((Form)roiPane.View);
            }
            catch (Exception cause)
            {
                ROIViewUtility.Handle(roiPane.Context, new ROIException(ROIErrorCodes.Unknown, cause));
            }
        }
    }
}
