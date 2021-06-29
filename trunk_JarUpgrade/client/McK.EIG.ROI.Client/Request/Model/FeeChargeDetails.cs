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
    /// Model contains fee charge details
    /// </summary>
    [Serializable]
    public class FeeChargeDetails 
    {
        # region Fields

        public const string FeeChargeKey = "FeeCharge";
        public const string AmountKey    = "amount";
        public const string FeeTypeKey   = "feetype";
        public const string IsCustomKey = "is-custom-fee";
        public const string FeeChargeTaxAmountKey = "salestax-amount";
        public const string HasSalesTaxKey = "has-salestax";

        //CR377173
        public const string AmountFormat = "^((\\d{1,13})|(\\d{1,13}\\.\\d{1,2})|(\\.\\d{1,2}))$";

        private double amount;

        private string feeType;

        private bool isCustomFee;

        private double taxAmount;

        private bool hasSalesTax;

        private string key;
        
        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the amount.
        /// </summary>
        public double Amount
        {
            get { return amount; }
            set { amount = value; }
        }

        /// <summary>
        /// Gets or sets the sort fee type name.
        /// </summary>
        public string FeeType
        {
            get { return feeType; }
            set { feeType = value; }
        }

        public bool IsCustomFee
        {
            get { return isCustomFee; }
            set { isCustomFee = value; }
        }

        public double TaxAmount
        {
            get { return taxAmount; }
            set { taxAmount = value; }
        }

        public bool HasSalesTax
        {
            get { return hasSalesTax; }
            set { hasSalesTax = value; }
        }

        public string Key
        {
            get { return key; }
            set { key = value; }
        }

        #endregion
    }
}
