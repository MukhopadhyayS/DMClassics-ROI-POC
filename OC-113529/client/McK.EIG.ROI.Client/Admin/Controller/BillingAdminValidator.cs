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

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Validates biling admin model entity
    /// </summary>
    public partial class BillingAdminValidator : BaseROIValidator
    {
        //Media Type
        protected const int MediaNameMaxLength = 30;
        protected const int MediaDescriptionMaxLength = 256;

        //FeeType
        protected const int FeeNameMaxLength = 256;

        //PaymentMethod
        protected const int PaymentNameMaxLength = 20;
        protected const int PaymentDescriptionMaxLength = 256;

        //Billing Tier
        protected const int BillingTierNameLength = 256;
        protected const int BillingTierBaseChargeMaxLength = 6;
        
 		//Billing Template
        protected const int BillingTemplateMaxLength = 256;

        //Sales Tax Per Facility
        protected const int SalesTaxDescriptionMaxLength = 256;
        protected const int SalesTaxPercentageMaxLength = 6;
    }
}
