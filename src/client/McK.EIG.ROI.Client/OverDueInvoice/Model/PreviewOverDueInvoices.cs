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
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.OverDueInvoice.Model
{
    /// <summary>
    /// Model contains the details of selected requestor summary letter templates and invoices
    /// </summary>
    public class PreviewOverDueInvoices
    {
        # region Fields

        private long invoiceTemplateId;
        private long requestorLetterTemplateId;
        private List<RequestorInvoicesDetails> requestorInvoicesList;
        private bool isPreview;

        private List<string> requestorLetterNotes;
        private List<string> invoiceNotes;
        private Hashtable propertiesMap;
        private RequestorStatementDetail requestorStatementDetail;
        private bool isOutputInvoice;

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the invoice template Id.
        /// </summary>
        public long InvoiceTemplateId
        {
            get { return invoiceTemplateId; }
            set { invoiceTemplateId = value; }
        }

        /// <summary>
        /// Gets or sets the requestor letter template Id.
        /// </summary>
        public long RequestorLetterTemplateId
        {
            get { return requestorLetterTemplateId; }
            set { requestorLetterTemplateId = value; }
        }

        /// <summary>
        /// Gets the requestor with associated invoices.
        /// </summary>
        public List<RequestorInvoicesDetails> RequestorInvoicesList
        {
            get
            {
                if (requestorInvoicesList == null)
                {
                    requestorInvoicesList = new List<RequestorInvoicesDetails>();
                }
                return requestorInvoicesList;
            }          
        }

        /// <summary>
        /// Gets or sets the preview overdue invoice or not.
        /// </summary>
        public bool IsPreview
        {
            get { return isPreview; }
            set { isPreview = value; }
        }

        /// <summary>
        /// Property holds the admin configured requestor letter notes.
        /// </summary>
        public List<string> RequestorLetterNotes
        {   
            get
            {
                if (requestorLetterNotes == null)
                {
                    requestorLetterNotes = new List<string>();
                }
                return requestorLetterNotes;
            }
        }        

        /// <summary>
        /// Property holds the admin configured invoice letter notes.
        /// </summary>
        public List<string> InvoiceNotes
        {
            get
            {
                if (invoiceNotes == null)
                {
                    invoiceNotes = new List<string>();
                }
                return invoiceNotes;
            }
        }

        /// <summary>
        /// Property holds the output properties like output methods and queue password.
        /// </summary>
        public Hashtable PropertiesMap
        {
            get
            {
                if (propertiesMap == null)
                {
                    propertiesMap = new Hashtable();
                }
                return propertiesMap;
            }
        }

        public RequestorStatementDetail RequestorStatementDetail
        {
            get { return requestorStatementDetail; }
            set { requestorStatementDetail = value; }
        }

        public bool IsOutputInvoice
        {
            get { return isOutputInvoice; }
            set { isOutputInvoice = value; }
        }

        #endregion
    }
}
