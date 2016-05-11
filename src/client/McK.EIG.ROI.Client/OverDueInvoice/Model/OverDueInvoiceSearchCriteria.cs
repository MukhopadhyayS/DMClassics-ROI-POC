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
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;

namespace McK.EIG.ROI.Client.OverDueInvoice.Model
{
    /// <summary>
    /// Class holds the past due invoice search criteria details.
    /// </summary>
    public class OverDueInvoiceSearchCriteria 
    {
        #region Fields

        private IList<string> facilityCode;
        private IList<long> requestorType;
        //CR#378949
        private IList<string> requestorTypeName;
        private string requestorName;
        private int maxrecord;
        private int from;
        private int to;

        #endregion

        #region Methods

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            requestorName = (requestorName != null && requestorName.Trim().Length != 0) ? requestorName.Trim() : null;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets facility code.
        /// </summary>
        public IList<string> FacilityCode
        {
            get { return facilityCode; }
            set { facilityCode = value; }
        }

        /// <summary>
        /// Gets or sets requestor type.
        /// </summary>
        public IList<long> RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }

        //CR#378949
        /// <summary>
        /// Gets or sets requestor type Names.
        /// </summary>
        public IList<string> RequestorTypeName
        {
            get { return requestorTypeName; }
            set { requestorTypeName = value; }
        }

        /// <summary>
        /// Gets or sets requestor name.
        /// </summary>
        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }

        /// <summary>
        /// Gets or sets overdue from day value.
        /// </summary>
        public int From
        {
            get { return from; }
            set { from = value; }
        }

        /// <summary>
        /// Gets or sets overdue from day value.
        /// </summary>
        public int To
        {
            get { return to; }
            set { to = value; }
        }

        /// <summary>
        /// Gets or sets max record value.
        /// </summary>
        public int MaxRecord
        {
            get { return maxrecord; }
            set { maxrecord = value; }
        }

        #endregion
    }
}
