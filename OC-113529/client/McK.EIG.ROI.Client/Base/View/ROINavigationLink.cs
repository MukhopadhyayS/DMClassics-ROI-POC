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

using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Base.View
{

    public class ROINavigationLink : ILink
    {
        #region Fields

        private string key;
        private string displayText;
        private object tag;

        #endregion

        #region Constructor

        public ROINavigationLink(string key, string displayText)
        {
            this.key = key;
            this.displayText = displayText;
        }

        #endregion

        #region Properties

        #region ILink Members

        public string Key
        {
            get { return key; }
            set { key = value; }
        }

        public string DisplayText
        {
            get { return displayText; }
            set { displayText = value; }
        }

        public object Tag
        {
            get { return tag; }
            set { tag = value; }
        }

        #endregion

        #endregion
    }
}
