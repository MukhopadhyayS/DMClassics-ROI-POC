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
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Request.Model
{

    /// <summary>
    /// Model contains the release details of the request
    /// </summary>
    [Serializable]
    public class ChargeHistoryDetails
    {
        # region Fields

        private double documentChargeTotal;

        private double feeChargeTotal;

        private double shippingCharge;

        private double salesTaxTotal;

        private string releasedDate;

        private double unBillableAmount;

        #endregion

        #region Properties

        public double FeeChargeTotal
        {
            get { return feeChargeTotal; }
            set { feeChargeTotal = value; }
        }

        public double DocumentChargeTotal
        {
            get { return documentChargeTotal; }
            set { documentChargeTotal = value; }
        }

        public double ShippingCharge
        {
            get { return shippingCharge; }
            set { shippingCharge = value; }
        }

        public string ReleasedDate
        {
            get { return releasedDate; }
            set { releasedDate = value; }
        }

        public double TotalReleaseCost
        {
            get { return documentChargeTotal + feeChargeTotal + shippingCharge + salesTaxTotal; }
        }

        public double SalesTaxTotal
        {
            get { return salesTaxTotal; }
            set { salesTaxTotal = value; }
        }

        public double UnBillableAmount
        {
            get { return unBillableAmount; }
            set { unBillableAmount = value; }
        }

        #endregion
    }
}
