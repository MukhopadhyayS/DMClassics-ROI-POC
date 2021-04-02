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
    [Serializable]
    public class PaymentMethodDetails : ROIModel
    {

        #region Fields

        //holds the id
        private long id;

        //holds the name
        private string name;

        //holds the description
        private string desc;

        //holds the display value
        private bool isDisplay;

        #endregion

        #region Constructor
        /// <summary>
        /// Create an new PaymentMethodDetails instance
        /// </summary>
        public PaymentMethodDetails() 
        {
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the payment method Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets the payment method Name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// This property is used to get or sets the payment method Description.
        /// </summary>
        public string Description
        {
            get { return CarriageReturn(desc ?? string.Empty); }
            set { desc = value; }
        }

        /// <summary>
        /// This property is used to get or sets the display value.
        /// </summary>
        public bool IsDisplay
        {
            get { return isDisplay; }
            set { isDisplay = value; }
        }

        #endregion
    }
}
