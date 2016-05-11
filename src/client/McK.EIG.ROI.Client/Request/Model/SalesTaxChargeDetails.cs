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

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Model contains sales tax charge details
    /// </summary>
    [Serializable]
    public class SalesTaxChargeDetails
    {
        #region Fields

        private bool hasSalesTax;
        private bool isCustomFee;
        private string chargeName;
        private double amount;
        private double taxAmount;
        private string key;
        private bool hasBillingTier;

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the sales tax is applicable or not.
        /// </summary>
        public bool HasSalesTax
        {
            get { return hasSalesTax; }
            set { hasSalesTax = value; }
        }

        /// <summary>
        /// Gets or sets the type of the fee.
        /// </summary>
        public bool IsCustomFee
        {
            get { return isCustomFee; }
            set { isCustomFee = value; }
        }

        /// <summary>
        /// Gets or sets the charge name;
        /// </summary>
        public string ChargeName
        {
            get { return chargeName; }
            set { chargeName = value; }
        }

        /// <summary>
        /// Gets or sets the charge amount
        /// </summary>
        public double Amount
        {
            get { return amount; }
            set { amount = value; }
        }

        /// <summary>
        /// Gets or sets the charge tax amount
        /// </summary>
        public double TaxAmount
        {
            get { return taxAmount; }
            set { taxAmount = value; }
        }

        /// <summary>
        /// Gets or sets the unique name of each element
        /// </summary>
        public string Key
        {
            get { return key; }
            set { key = value; }
        }

        public bool HasBillingTier
        {
            get { return hasBillingTier; }
            set { hasBillingTier = value; }
        }

        #endregion
    }
}
