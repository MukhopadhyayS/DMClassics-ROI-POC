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
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Validates disclosure document Types model data.
    /// </summary>
    public partial class ROIAdminValidator
    {
        #region Methods

        /// <summary>
        /// Validates the codeset 
        /// </summary>
        /// <param name="feeType"></param>
        public bool ValidateCodeSet(long codeSet)
        {
            if (codeSet <= 0)
            {
                AddError(ROIErrorCodes.CodeSetIdInvalid);
            }
            return NoErrors;
        }

        public bool ValidateAuthorization(Collection<DocumentTypesDetails> documentTypes)
        {
            List<DocumentTypesDetails> list = new List<DocumentTypesDetails>(documentTypes);
            List<DocumentTypesDetails> authList = list.FindAll(delegate(DocumentTypesDetails item) { return item.IsAuthorization; });
            if (authList.Count > 1)
            {
                AddError(ROIErrorCodes.MultipleAuthSelected);
            }
            if (authList.Count == 0)
            {
                AddError(ROIErrorCodes.NoAuthRequestSelected);                
            }
            return NoErrors;
        }

        #endregion
    }
}
