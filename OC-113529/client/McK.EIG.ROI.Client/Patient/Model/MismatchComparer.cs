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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Patient.Model
{
    /// <summary>
    /// FieldMatchComparer
    /// </summary>
    public class FieldMatchComparer : IComparer<PatientDetails>
    {
        public int Compare(PatientDetails x, PatientDetails y)
        {
            int count = 0;

            if (x.FullName == y.FullName)
            {
                count ++;
            }

            if (x.DOB != null)
            {
                if (x.DOB == y.DOB)
                {
                    count++;
                }
            }
            if (!string.IsNullOrEmpty(x.SSN))
            {
                if (x.SSN == y.SSN)
                {
                    count++;
                }
            }
            if (UserData.Instance.EpnEnabled)
            {
                if (!string.IsNullOrEmpty(x.EPN))
                {
                    if (x.EPN == y.EPN)
                    {
                        count++;
                    }
                }
            }
            else
            {
                if (!string.IsNullOrEmpty(x.MRN))
                {
                    if (x.MRN == y.MRN)
                    {
                        count++;
                    }
                }

            }
            return count;
        }

    }
}
