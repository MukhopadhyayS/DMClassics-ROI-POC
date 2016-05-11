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

namespace McK.EIG.ROI.Client.Base.Model
{
    
    public enum DataType
    {
        Integer,
        Double,
        Boolean,
        String,
        Date
    }

    public enum Condition
    {
        Equal,
        GreaterThan,
        LessThan,
        Like,
        In,
        Between
    }

    /// <summary>
    /// ROIModel.
    /// </summary>
    [Serializable]
    public abstract class ROIModel
    {
        #region Fields
        
        private int recordVersionId;
        
        #endregion

        #region Methods

        public override bool Equals(object obj)
        {
            if (object.ReferenceEquals(this, obj)) return true;

            return (this.GetType() == obj.GetType()) &&
                   (Id == ((ROIModel)obj).Id) ;
        }

        public override int GetHashCode()
        {
            return this.GetType().GetHashCode() + Id.GetHashCode();
        }

        public static string CarriageReturn(string value)
        {
            if (String.IsNullOrEmpty(value)) return value;
            value = value.Replace("\r\n", "\n");
            value = value.Replace("\r", "\n");
            value = value.Replace("\n", "\r\n");
            return value;
        }

        #endregion

        #region Properties

        public abstract long Id
        {
            get;
            set;
        }

        /// <summary>
        /// Holds the record version id
        /// </summary>
        public int RecordVersionId
        {
            get { return recordVersionId; }
            set { recordVersionId = value; }
        }

        #endregion

    }
}
