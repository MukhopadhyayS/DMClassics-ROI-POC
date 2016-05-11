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
using System.Collections.Generic;

using System.Runtime.InteropServices;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// StringLogicalComparer
    /// </summary>
    [Serializable]
    public class StringLogicalComparer : IComparer<string>
    {
        internal static class NativeMethods
        {
            [DllImport("shlwapi.dll", CharSet = CharSet.Unicode, ExactSpelling = true)]
            public static extern int StrCmpLogicalW(string strA, string strB);
        }

        public virtual int Compare(string x, string y)
        {
            string text1 = x as string;
            if (text1 != null)
            {
                string text2 = y as string;
                if (text2 != null)
                {
                    return NativeMethods.StrCmpLogicalW(text1, text2);
                }
            }
            return Comparer.Default.Compare(x, y);
        }
    }
}
