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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{    
    /// <summary>
    /// This Class is used to hold Page Weight info
    /// </summary>
    [Serializable]
    public class SSNMaskDetails : ROIModel
    {
        #region Fields

        private long id;
        private bool isMasking;

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets ID.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets Masking detail.
        /// </summary>
        public bool IsMasking
        {
            get { return isMasking; }
            set { isMasking = value; }
        }

        #endregion
    }
}
