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
using System.Text;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.Common.Utility.WebServices;

using Microsoft.Web.Services3.Security.Tokens;


namespace McK.EIG.ROI.Client.Base.Model
{
    /// <summary>
    /// Class to hold the user credentials.
    /// NOTE: COPIED FROM HECM
    /// </summary>
    public class UserSecurities
    {
        #region Fields

        private Dictionary<string, bool> rights;

        public void Add(string securityId, bool isAllowed)
        {
            if (!Rights.ContainsKey(securityId))
            {
                Rights.Add(securityId, isAllowed);
            }
        }

        public bool IsAllowed(string securityId)
        {
            return !rights.ContainsKey(securityId) ? false : rights[securityId];
        }

        public Dictionary<string, bool> Rights
        {
            get
            {
                if (rights == null)
                {
                    rights = new Dictionary<string, bool>();
                }
                return rights;
            }
        }

        #endregion
    }
}
