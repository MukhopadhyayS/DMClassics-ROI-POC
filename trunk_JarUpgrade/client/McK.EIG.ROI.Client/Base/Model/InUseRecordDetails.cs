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

namespace McK.EIG.ROI.Client.Base.Model
{
    [Serializable]
    public class InUseRecordDetails
    {

        #region Fields

        private string userId;

        private string applicationId;

        private string objectId;

        private string objectType;

        #endregion

        #region Properties

        /// <summary>
        /// The user who is accessing the record.
        /// </summary>
        public string UserId
        {
            get { return userId; }
            set { userId = value; }
        }

        /// <summary>
        /// Application id
        /// </summary>
        public string ApplicationId
        {
            get { return applicationId; }
            set { applicationId= value; }
        }

        /// <summary>
        /// Object id
        /// </summary>
        public string ObjectId
        {
            get { return objectId; }
            set { objectId = value; }
        }


        /// <summary>
        /// Object id
        /// </summary>
        public string ObjectType
        {
            get { return objectType; }
            set { objectType = value; }
        }

        #endregion
    }
}
