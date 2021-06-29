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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// This Class is used to hold feeType info
    /// </summary>
    [Serializable]
    public class FeeTypeDetails : ROIModel
    {
        #region Fields

        //holds the id
        private long id;

        //holds the name
        private string name;

        //holds the desc
        private string desc;

        //holds the amount
        private double amount;

        //holds the isAssociated flag
        private bool isAssociated;

        //hold the SalesTax
        private string salesTax;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new FeeTypeDetails instance
        /// </summary>
        public FeeTypeDetails() 
        {
        }

        #endregion

        #region Properties

        #region Id

        /// <summary>
        /// This property is used to get or sets the feeType Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        #endregion

        #region Name

        /// <summary>
        /// This property is used to get or sets the feeType Name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        #endregion

        #region Description

        /// <summary>
        /// This property is used to get or sets the feeType Description.
        /// </summary>
        public string Description
        {
            get { return CarriageReturn(desc ?? string.Empty); }
            set { desc = value; }
        }


        #endregion

        #region Amount

        /// <summary>
        /// This property is used to get or sets the feeType amount.
        /// </summary>
        public double Amount
        {
            get { return amount; }
            set { amount = value; }
        }

        #endregion

        #region SalesTax

        public string SalesTax
        {
            get { return salesTax; }
            set { salesTax = value; }
        }

        #endregion

        #region IsAssociated

        /// <summary>
        /// This property is used to get or sets isAssociated.
        /// </summary>
        public bool IsAssociated
        {
            get { return isAssociated; }
            set { isAssociated = value; }
        }

        #endregion

        #endregion
    }
}
