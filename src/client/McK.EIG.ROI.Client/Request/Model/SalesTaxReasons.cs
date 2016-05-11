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
using System.ComponentModel;
using System.Globalization;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// SalesTaxReasonComparer
    /// </summary>
    public class SalesTaxReasonComparer : GenericComparer
    {
        protected override object GetPropertyValue(object from, PropertyDescriptor descriptor)
        {
            return "FormattedDate".Equals(descriptor.Name) ? ((SalesTaxReasons)from).CreatedDate : base.GetPropertyValue(from, descriptor);
        }
    }

    /// <summary>
    /// Model contains the add/ remove reasons of tax of the request
    /// </summary>
    [Serializable]
    public class SalesTaxReasons : ROIModel
    {
        # region Fields

        public const string SalesTaxReasonKey = "salestax-reason";
        public const string CreatedDateKey = "date";
        public const string ReasonKey = "reason";

        private long id;

        private DateTime createdDate;        

        private string reason;                       

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        public DateTime CreatedDate
        {
            get { return createdDate; }
            set { createdDate = value; }
        }

        public string FormattedDate
        {
            get { return createdDate.ToShortDateString(); }
        }        

        /// <summary>
        /// Gets or sets the description.
        /// </summary>
        public string Reason
        {
            get 
            {
                return string.IsNullOrEmpty(reason) ? string.Empty : reason;
            }
            set { reason = value; }
        }
        
        #endregion
    }
}
