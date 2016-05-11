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

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// PlaceHolder for Application Level Events.
    /// </summary>
    public static class ROIEvents
    {
        #region Fields

        public static event EventHandler LogOnClick;
        public static event EventHandler NavigationChange;
        public static event EventHandler EditorChange;
        
        #endregion

        #region Methods

        internal static void OnLogOnClick(object sender, EventArgs e)
        {
            if (LogOnClick != null)
            {
                LogOnClick(sender, e);
            }
        }

        /// <summary>
        /// Occurs when the navigation is change in menu/lsp. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal static void OnNavigationChange(object sender, EventArgs e)
        {
            if (NavigationChange != null)
            {
                NavigationChange(sender, e);
            }
        }

        internal static void OnEditorChange(object sender, EventArgs e)
        {
            if (EditorChange != null)
            {
                EditorChange(sender, e);
            }
        }

        #endregion 
      
    }
}
