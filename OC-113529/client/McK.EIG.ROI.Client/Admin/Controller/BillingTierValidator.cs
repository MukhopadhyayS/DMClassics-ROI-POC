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
using System.Collections.ObjectModel;
using System.Globalization;
using System.Text.RegularExpressions;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class BillingAdminValidator
    {

        #region Methods

        /// <summary>
        /// Validates the fields before creating new billing tier.
        /// </summary>
        /// <param name="billingTierDetail"></param>
        /// <returns></returns>
        public bool ValidateCreate(BillingTierDetails billingTierDetail)
        {
            ValidatePrimaryFields(billingTierDetail);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updaiing billing tier.
        /// </summary>
        /// <param name="billingTierDetail"></param>
        /// <returns></returns>
        
        public bool ValidateUpdate(BillingTierDetails billingTierDetail)
        {
            ValidatePrimaryFields(billingTierDetail);
            return NoErrors;
        }

        /// <summary>
        /// Validates the primary fields for BillingTierDetail 
        /// </summary>
        /// <param name="billingTierDetail"></param>
        public void ValidatePrimaryFields(BillingTierDetails billingTierDetail)
        {
            if (billingTierDetail.Name.Length == 0 || (!Validator.Validate(billingTierDetail.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.BillingTierNameEmpty);
            }

            if (billingTierDetail.Name.Length > BillingTierNameLength)
            {
                AddError(ROIErrorCodes.BillingTierNameMaxLength);
            }

            if (billingTierDetail.MediaType.Id == 0)
            {
                AddError(ROIErrorCodes.MediaTypeNameEmpty);
            }

            bool isMatch;
            if (billingTierDetail.BaseCharge != 0)
            {
                isMatch = Regex.IsMatch(Convert.ToString(billingTierDetail.BaseCharge, System.Threading.Thread.CurrentThread.CurrentCulture),
                                        "^((\\d{1,3})|(\\d{1,3}\\.\\d{1,2})|(\\.\\d{1,2}))$");
                if (!isMatch)
                {
                    AddError(ROIErrorCodes.BillingTierBaseChargeIsNotValid);
                }
            }

            if (billingTierDetail.OtherPageCharge != 0)
            {
                isMatch = Regex.IsMatch(Convert.ToString(billingTierDetail.OtherPageCharge, System.Threading.Thread.CurrentThread.CurrentCulture),
                                        "^((\\d{1,2})|(\\d{1,2}\\.\\d{1,2})|(\\.\\d{1,2}))$");
                if (!isMatch)
                {
                    AddError(ROIErrorCodes.BillingTierOtherPageChargeIsNotValid);
                }
            }

            ValidatePageTierGroup(billingTierDetail);
        }

        /// <summary>
        /// Validates all page level tiers
        /// </summary>
        /// <param name="billingTierDetail"></param>
        public void ValidatePageTierGroup(BillingTierDetails billingTierDetail)
        {
            int index = 0;

            foreach (PageLevelTierDetails pageLevelTierDetail in billingTierDetail.PageTiers)
            {
                ValidatePagetierFields(pageLevelTierDetail, index);

                if (!Regex.IsMatch(Convert.ToString(pageLevelTierDetail.PricePerPage, System.Threading.Thread.CurrentThread.CurrentCulture),
                                        "^((\\d{1,2})|(\\d{1,2}\\.\\d{1,2})|(\\.\\d{1,2}))$"))
                {
                    AddError(ROIErrorCodes.PageTierInvalidPriceFormat, "pricePerPage." + index);
                }

               
                if (index != 0)
                {
                    ValidatePageTierSequeceOrder(index, billingTierDetail.PageTiers);
                }
                
                index++;
            }

            index = 0;

            foreach (PageLevelTierDetails pageLevelTierDetail in billingTierDetail.PageTiers)
            {
                ValidatePageTiersDuplication(index, billingTierDetail.PageTiers);
                index++;
            }

        }

        /// <summary>
        /// Validates the sequence order of page tiers
        /// </summary>
        /// <param name="index"></param>
        /// <param name="pageTiers"></param>
        private void ValidatePageTierSequeceOrder(int index, Collection<PageLevelTierDetails> pageTiers)
        {
            if (pageTiers[index - 1].EndPage + 1 != pageTiers[index].StartPage)
            {
                AddError(ROIErrorCodes.PageTiersNotSequential, "fromPage." + index);
                AddError(ROIErrorCodes.PageTiersNotSequential, "toPage." + (index - 1));
            }
        }

        /// <summary>
        /// Checks for duplication of page rate tier.
        /// It checks the current page rate tier with next following page rate tiers. 
        /// </summary>
        /// <param name="index"></param>
        /// <returns></returns>
        private void ValidatePageTiersDuplication(int index, Collection<PageLevelTierDetails> pageTiers)
        {
           
            PageLevelTierDetails currentPageLevelTier = pageTiers[index];
            PageLevelTierDetails nextPageLevelTier;

            for (int idx = index+1; idx < pageTiers.Count; idx++)
            {
                nextPageLevelTier = pageTiers[idx];

                if (currentPageLevelTier.StartPage == nextPageLevelTier.StartPage
                      && currentPageLevelTier.EndPage == nextPageLevelTier.EndPage)
                {
                    AddError(ROIErrorCodes.PageTiersExist, "fromPage." + idx);
                }
            }
        }

        /// <summary>
        /// validates page tiers and empry or invalid.
        /// </summary>
        /// <param name="pageLevelTierDetail"></param>
        /// <param name="index"></param>
        private void ValidatePagetierFields(PageLevelTierDetails pageLevelTierDetail, int index)
        {
            if (index == 0)
            {
                if (pageLevelTierDetail.StartPage != 1)
                {
                    AddError(ROIErrorCodes.PageTierEmptyOrInvalid, "fromPage." + index);
                }
            }

            if (pageLevelTierDetail.StartPage == 0 || pageLevelTierDetail.EndPage == 0)
            {
                AddError(ROIErrorCodes.PageTierEmptyOrInvalid, "toPage." + index);
            }
            if (pageLevelTierDetail.StartPage > pageLevelTierDetail.EndPage)
            {
                AddError(ROIErrorCodes.PageTierEmptyOrInvalid, "toPage." + index);
            }
        }

        #endregion
    }
}
