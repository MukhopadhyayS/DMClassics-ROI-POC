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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Model contains request invoice details
    /// </summary>
    [Serializable]
    public class PreBillInvoiceDetails : ROIModel
    {   
        public const string LetterTemplateNameKey = "lettertemplate-name";
        public const string TemplateIdKey = "lettertemplate-id";
        public const string RequestorLetterTemplateIdKey = "requestorlettertemplate-id";       
        public const string RequestorLetterTemplateNameKey = "requestorlettertemplate-name";

        #region Fields

        private long id;

        private long letterTemplateId;

        private string letterTemplateName;

        private int totalPagesPerRelease;

        private string balanceDue;        

        private string invoiceType;

        private string requestStatus;

        private string statusReasons;

        private Nullable<DateTime> releaseDate;

        private Nullable<DateTime> dateCreated;

        private Collection<string> notes;

        private ReleaseDetails release;

        private InvoiceDet invoice;

        private RequestorDetails requestor;

        private string queueSecure;
        
        private string outputMethod;

        private Nullable<DateTime> invoiceduedate;

        private bool overwriteinvoicedue;

        private string salesTaxPercentage;

        private bool isInvoice;
        private bool isLetter;
        private bool isPastInvoice;
        private List<long> pastInvoiceIds;
        private Dictionary<string, string> properties;

        #endregion

        #region Properties

        /// <summary>
        /// Get or sets the id of the release
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// Gets or sets the letter template id.
        /// </summary>
        public long LetterTemplateId
        {
            get { return letterTemplateId; }
            set { letterTemplateId = value; }
        }

        /// <summary>
        /// Gets or sets the letter template name.
        /// </summary>
        public string LetterTemplateName
        {
            get { return letterTemplateName; }
            set { letterTemplateName = value; }
        }

        /// <summary>
        /// Gets or sets type (Invoice/PreBill)
        /// </summary>
        public string InvoiceType
        {
            get { return invoiceType; }
            set { invoiceType = value; }
        }

        /// <summary>
        /// Gets or sets request status.
        /// </summary>
        public string RequestStatus
        {
            get { return requestStatus; }
            set { requestStatus = value; }
        }

        /// <summary>
        /// Gets or sets request status reasons
        /// </summary>
        public string StatusReasons
        {
            get { return statusReasons; }
            set { statusReasons = value; }
        }

        /// <summary>
        /// Gets or sets the balance due.
        /// </summary>
        public string BalanceDue
        {
            get { return balanceDue; }
            set { balanceDue = value; }
        }

        /// <summary>
        /// Gets or sets the total pages per release.
        /// </summary>
        public int TotalPagesPerRelease
        {
            get { return totalPagesPerRelease; }
            set { totalPagesPerRelease = value; }
        }       

        /// <summary>
        /// Gets or sets the release date
        /// </summary>
        public Nullable<DateTime> ReleaseDate
        {
            get { return releaseDate; }
            set { releaseDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the date
        /// </summary>
        public Nullable<DateTime> DateCreated
        {
            get { return dateCreated; }
            set { dateCreated = value; }
        }

        /// <summary>
        /// Property holds the configure notes collection
        /// </summary>
        public Collection<string> Notes
        {
            get
            {
                if (notes == null)
                {
                    notes = new Collection<string>();
                }

                return notes;
            }
        }

        /// <summary>
        /// Property holds the release details
        /// </summary>
        public ReleaseDetails Release
        {
            get
            {
                if (release == null)
                {
                    release = new ReleaseDetails();
                }
                return release;
            }
            set { release = value; }            
        }

        /// <summary>
        /// Property holds the invoice details
        /// </summary>
        public InvoiceDet Invoice
        {
            get
            {
                if (invoice == null)
                {
                    invoice = new InvoiceDet();
                }
                return invoice;
            }
            set { invoice = value; }
        }

        /// <summary>
        /// Property holds the requestor details
        /// </summary>
        public RequestorDetails Requestor
        {
            get
            {
                if (requestor == null)
                {
                    requestor = new RequestorDetails();
                }
                return requestor;
            }
            set { requestor = value; }
        }

        /// <summary>
        /// Holds details of the queue password
        /// </summary>
        public string QueueSecretWord
        {
            get { return queueSecure; }
            set { queueSecure = value; }
        }

        /// <summary>
        /// Holds details of the output(shipping) method
        /// </summary>
        public string OutputMethod
        {
            get { return outputMethod; }
            set { outputMethod = value; }
        }

        /// <summary>
        /// Holds details of the calculated invoice due date 
        /// </summary>
        public Nullable<DateTime> InvoiceDueDate
        {
            get { return invoiceduedate; }
            set { invoiceduedate = value; }
        }

        /// <summary>
        /// Holds status of invoice due date is overwrite or not
        /// </summary>
        public bool OverwriteInvoiceDue
        {
            get { return overwriteinvoicedue; }
            set { overwriteinvoicedue = value; }
        }

        /// <summary>
        /// Gets or sets is invoice or not.
        /// </summary>
        public bool IsInvoice
        {
            get { return isInvoice; }
            set { isInvoice = value; }
        }

        /// <summary>
        /// Gets or sets is letter or not.
        /// </summary>
        public bool IsLetter
        {
            get { return isLetter; }
            set { isLetter = value; }
        }

        /// <summary>
        /// Gets or sets is past invoice or not.
        /// </summary>
        public bool IsPastInvoice
        {
            get { return isPastInvoice; }
            set { isPastInvoice = value; }
        }

        /// <summary>
        /// Returns the list of past invoice ids.
        /// </summary>
        public List<long> PastInvoiceIds
        {
            get
            {
                if (pastInvoiceIds == null)
                {
                    pastInvoiceIds = new List<long>();
                }
                return pastInvoiceIds;
            }
            set { pastInvoiceIds = value; }
        }

        /// <summary>
        /// Gets the release properties
        /// </summary>
        public Dictionary<string, string> Properties
        {
            get
            {
                if (properties == null)
                {
                    properties = new Dictionary<string, string>();
                }
                return properties;
            }
        }

        public string SalesTaxPercentage
        {
            get { return salesTaxPercentage; }
            set { salesTaxPercentage = value; }
        }
        #endregion
     
    }
}
