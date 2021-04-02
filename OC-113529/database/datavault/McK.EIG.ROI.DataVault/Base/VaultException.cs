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

#region Namespace

using System;
using System.Runtime.Serialization;

#endregion


namespace McK.EIG.ROI.DataVault.Base
{
    [Serializable]
    public class VaultException : Exception
    {
        #region Constructor

        public VaultException() {}

        public VaultException(string message) : base(message) { }

        public VaultException(string message, Exception cause) : base(message, cause) { }

        protected VaultException(SerializationInfo info, StreamingContext context) : base(info, context) {}

        #endregion
    }
}
