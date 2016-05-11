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
    /// This Class is used to hold billingTemplate Associated Feetypes info
    /// </summary>
    [Serializable]
    public class AssociatedFeeType
    {
        #region Fields

        //holds the associationId
        private long associationId;

        //holds the AssociatedFeeTypeId
        private long feeTypeId;

        //holds the BillingTemplateId
        private long billingTemplateId;

        //holds the AssociatedFeeType Name
        private string name;

        //holds the AssociatedFeeType RecordVersion
        private int recordVersionId;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new BillingTemplate Details instance
        /// </summary>
        public AssociatedFeeType()
        {
        }

        #endregion      

        #region Properties

        #region Id

        /// <summary>
        /// This property is used to get or sets the billingTemplate Associated FeeType Id.
        /// </summary>
        public long AssociationId
        {
            get { return associationId; }
            set { associationId = value; }
        }

        #endregion

        #region FeeTypeId

        /// <summary>
        /// This property is used to get or sets the Feetype Id.
        /// </summary>
        public long FeeTypeId
        {
            get { return feeTypeId; }
            set { feeTypeId = value; }
        }

        #endregion

        #region BillingTemplateId

        /// <summary>
        /// This property is used to get or sets the BillingTemplate Id.
        /// </summary>
        public long BillingTemplateId
        {
            get { return billingTemplateId; }
            set { billingTemplateId = value; }
        }
       
        #endregion  
     
        #region Name

        /// <summary>
        /// This property is used to get or sets the feeType name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        #endregion  

        #region RecordVersionId

        /// <summary>
        /// This property is used to get or sets the feeType recordversion id
        /// </summary>
        public int RecordVersionId
        {
            get { return recordVersionId; }
            set { recordVersionId = value; }
        }

        #endregion 
        
        #endregion
    }
}
