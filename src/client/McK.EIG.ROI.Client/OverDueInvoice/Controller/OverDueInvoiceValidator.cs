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

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.OverDueInvoice.Model;

namespace McK.EIG.ROI.Client.OverDueInvoice.Controller
{
    /// <summary>
    /// Class Validate the PastDueInvoiceValidator.
    /// </summary>
    public class OverDueInvoiceValidator : BaseROIValidator
    {   
        #region Methods

        /// <summary>
        /// Validate the OverDue Search Criteria.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public bool ValidateOverDueInvoiceSearch(OverDueInvoiceSearchCriteria searchCriteria)
        {
            int facilitiesLength = (searchCriteria.FacilityCode != null) ? searchCriteria.FacilityCode.Count : 0;
            int requestorTypeLength = (searchCriteria.RequestorType != null) ? searchCriteria.RequestorType.Count : 0;

            if (facilitiesLength > 0 && requestorTypeLength > 0)
            {
                //if (!string.IsNullOrEmpty(searchCriteria.RequestorName) && searchCriteria.RequestorName.Length < 1)
                //{
                //    return false;
                //}
                return true;
            }
            return false;
        }

        /// <summary>
        /// Validate overdue invoice search fields.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        internal bool ValidateSearchFields(OverDueInvoiceSearchCriteria searchCriteria)
        {
            if (!string.IsNullOrEmpty(searchCriteria.RequestorName))
            {
                ValidatePrimaryFields(searchCriteria.RequestorName, ROIConstants.NameValidation, ROIErrorCodes.InvalidRequestorName);
            }
            return NoErrors;
        }

        /// <summary>
        /// Validate overdue invoice search fields.
        /// </summary>
        /// <param name="field"></param>
        /// <param name="validCharSet"></param>
        /// <param name="errorCode"></param>
        private void ValidatePrimaryFields(string field, string validCharSet, string errorCode)
        {
            if (!Validator.Validate(field, validCharSet))
            {
                AddError(errorCode);
            }
        }

        #endregion
    }
}