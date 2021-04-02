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
using System.Collections;
using System.Globalization;

namespace McK.EIG.ROI.DataVault.Base
{
    /// <summary>
    /// Class for CaseInsensitive Comparasion in Hashtable.
    /// </summary>
    public class VaultComparer : IEqualityComparer
    {
        #region Fields

        CaseInsensitiveComparer comparer;

        #endregion

        #region Constructor

        public VaultComparer()
        {
            comparer = CaseInsensitiveComparer.DefaultInvariant;
        }

        #endregion

        #region Method
      
        public new bool Equals(object x, object y)
        {
            if (comparer.Compare(x, y) == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public int GetHashCode(object obj)
        {
            return obj.ToString().ToLower(CultureInfo.CurrentCulture).GetHashCode();
        }

        #endregion
    }
}
