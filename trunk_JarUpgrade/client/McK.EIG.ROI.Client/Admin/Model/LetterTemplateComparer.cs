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
using System.ComponentModel;

using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// LetterTemplateComparer
    /// </summary>
    public class LetterTemplateComparer : GenericComparer
    {
        protected override object GetPropertyValue(object from, PropertyDescriptor descriptor)
        {
            LetterTemplateDetails letterTemplate = (LetterTemplateDetails)from;
            if ("LetterTypeName".Equals(descriptor.Name))
            {
                return letterTemplate.CompositeKey;
            }
            return ("Image".Equals(descriptor.Name)) ? !letterTemplate.IsDefault : base.GetPropertyValue(from, descriptor);
        }
    }
}
