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
    /// Model contains the invoice charge details of the request
    /// </summary>
    [Serializable]
    public class InvoiceAndDocumentDetails
    {
        #region Fields

        private DocumentInfoList documentInfos;
        private InvoiceChargeDetails invoiceChargeDetailsInfo;
        private string documentName;

        #endregion

        #region Methods

        public DocumentInfoList DocumentInfos
        {
            get 
            {
                if (documentInfos == null)
                {
                    documentInfos = new DocumentInfoList();
                }
                return documentInfos;
            }
            set { documentInfos = value; }
        }

        public InvoiceChargeDetails InvoiceChargeDetailsInfo
        {
            get
            {
                if (invoiceChargeDetailsInfo == null)
                {
                    invoiceChargeDetailsInfo = new InvoiceChargeDetails();
                }
                return invoiceChargeDetailsInfo;
            }
            set { invoiceChargeDetailsInfo = value; }
        }

        public string DocumentName
        {
            get { return documentName; }
            set { documentName = value; }
        }

        #endregion

    }
}
