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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// This Class is used to hold billingTemplate info
    /// </summary>
    [Serializable]
    public class BillingTemplateDetails : ROIModel
    {
        #region Fields

        //holds the id
        private long id;

        //holds the name
        private string name;

        //holds the isAssociated flag
        private bool isAssociated;        

        //holds the FeeTypes comma seprated value
        private string feeTypesValue;

        //holds the FeeTypes collection
        private Collection<AssociatedFeeType> associatedFeeTypes;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new BillingTemplate Details instance
        /// </summary>
        public BillingTemplateDetails()
        {
        }

        #endregion
      
        #region Properties

        #region Id

        /// <summary>
        /// This property is used to get or sets the billingTemplate Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        #endregion

        #region Name

        /// <summary>
        /// This property is used to get or sets the billingTemplate Name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        #endregion

        #region AssociatedFeeType

        /// <summary>
        /// This property is used to get or sets the Associated FeeTypes collection.
        /// </summary>
        public Collection<AssociatedFeeType> AssociatedFeeTypes
        {
            get {

                if (associatedFeeTypes == null)
                {
                    associatedFeeTypes = new Collection<AssociatedFeeType>();
                }
                return associatedFeeTypes;
            }
        }

        public string FeeTypesValue
        {
            get { return feeTypesValue; }
            set { feeTypesValue = value; }
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
