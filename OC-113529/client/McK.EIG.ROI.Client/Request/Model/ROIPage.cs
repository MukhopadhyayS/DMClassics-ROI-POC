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
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// This class is used to hold ROI Page.
    /// </summary>
    [Serializable]
    public class ROIPage
    {
        private long pageSequence;
        private bool isSelfPayEncounter;

        /// <summary>
        /// This property is used to get or set the page sequence
        /// </summary>
        public long PageSequence
        {
            get { return pageSequence; }
            set { pageSequence = value; }
        }

        /// <summary>
        /// This property is used to get or set the is self pay encounter
        /// </summary>
        public bool IsSelfEncounter
        {
            get { return isSelfPayEncounter; }
            set { isSelfPayEncounter = value; }
        }
    }
}

