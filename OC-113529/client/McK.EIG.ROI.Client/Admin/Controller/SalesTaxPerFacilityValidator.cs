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
using System.Text.RegularExpressions;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class BillingAdminValidator
    {
        
        #region Methods

        /// <summary>
        /// Validates the primary fields for configuring a tax rate
        /// </summary>
        /// <param name="deliveryMethod"></param>
        public void ValidatePrimaryFields(TaxPerFacilityDetails facilityDetail)
        {            
            if (facilityDetail.FacilityCode == null || facilityDetail.FacilityCode.Length == 0)
            {
                AddError(ROIErrorCodes.SalesTaxFacilityEmpty);
            }

            if (Convert.ToString(facilityDetail.TaxPercentage, System.Threading.Thread.CurrentThread.CurrentCulture).Trim().Length > SalesTaxPercentageMaxLength)
            {
                AddError(ROIErrorCodes.SalesTaxPercentageMaxLength);
            }
            if (Convert.ToString(facilityDetail.TaxPercentage, System.Threading.Thread.CurrentThread.CurrentCulture).Trim().Length == 0)
            {
                AddError(ROIErrorCodes.SalesTaxPercentageEmpty);
            }

            if (facilityDetail.TaxPercentage >= 0 && facilityDetail.TaxPercentage <= 100)
            {
                bool isMatch;
                isMatch = Regex.IsMatch(Convert.ToString(facilityDetail.TaxPercentage, System.Threading.Thread.CurrentThread.CurrentCulture),
                                        "^((\\d{1,2})|(\\d{1,2}\\.\\d{1,2})|(\\.\\d{1,2})|(100|100\\.0|100\\.00))$");
                if (!isMatch)
                {
                    AddError(ROIErrorCodes.SalesTaxPercentageIsNotValid);
                }
            }
            else
            {
                AddError(ROIErrorCodes.SalesTaxPercentageIsNotValid);
            }
            if (facilityDetail.Description.Trim().Length > SalesTaxDescriptionMaxLength)
            {
                AddError(ROIErrorCodes.SalesTaxDescMaxLength);
            }

        }

        /// <summary>
        /// Validates the fields before creating new tax rate for a facility.
        /// </summary>
        /// <param name="deliveryMethod"></param>
        /// <returns></returns>
        public bool ValidateCreate(TaxPerFacilityDetails facilityDetail)
        {
            ValidatePrimaryFields(facilityDetail);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updating tax rate for a facility.
        /// </summary>
        /// <param name="deliveryMethod"></param>
        /// <returns></returns>
        public bool ValidateUpdate(TaxPerFacilityDetails facilityDetail)
        {
            ValidatePrimaryFields(facilityDetail);
            return NoErrors;
        }

        #endregion
    }
}
