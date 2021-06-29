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
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// RequestGlobalDocuments
    /// </summary>
    [Serializable]
    public class RequestGlobalDocuments : BaseRequestItem
    {
        #region Constructor

        /// <summary>
        /// Constructor
        /// </summary>
        public RequestGlobalDocuments() {}
        public RequestGlobalDocuments(RequestPatientDetails patient) 
        {
            base.Parent = patient;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds name of Global docs
        /// </summary>
        public override string Name
        {
            get { return ROIConstants.GlobalDocument; }
        }

        /// <summary>
        /// Holds key of Global docs
        /// </summary>
        public override string Key
        {
            get { return Name; }
        }

        /// <summary>
        /// Icon
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get
            {
                return ROIImages.GlobalDocIcon;
            }
        }

        #endregion
    }
}
