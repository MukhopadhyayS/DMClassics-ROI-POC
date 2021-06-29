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
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.OverDueInvoice.Model
{
    public class RequestorInvoicesDetails
    {
        # region Fields

        private List<long> invoiceIds; 
        private List<long> requestIds;
        private string requestorName;
        private string requestorType;
        private long requestorId;
        private long requestId;
        private long greatestInvoiceAge;

        #endregion

        #region Properties

        /// <summary>
        /// Gets the invoice Ids.
        /// </summary>
        public IList<long> InvoiceIds
        {
            get
            {
                if (invoiceIds == null)
                {
                    invoiceIds = new List<long>();
                }
                return invoiceIds;
            }            
        }

        /// <summary>
        /// Gets or sets requestor Id.
        /// </summary>
        public long RequestorId
        {
            get { return requestorId; }
            set { requestorId = value; }
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
        /// Gets or sets requestor type.
        /// </summary>
        public string RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }

        /// <summary>
        /// Gets or sets request Id.
        /// </summary>
        public long RequestId
        {
            get { return requestId; }
            set { requestId = value; }
        }

        /// <summary>
        /// Gets or sets greatestInvoiceAge.
        /// </summary>
        public long GreatestInvoiceAge
        {
            get { return greatestInvoiceAge; }
            set { greatestInvoiceAge = value; }
        }

        #endregion
    }
}
