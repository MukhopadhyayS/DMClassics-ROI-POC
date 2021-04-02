using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Request.Model
{
    public class InvoiceOrPrebillPreviewInfo
    {
        private long requestCoreId;

        private string type;

        private long invoiceDueDays;

        private long letterTemplateFileId;

        private string letterTemplateName;

        private string requestStatus;

        private System.DateTime requestDate;

        private System.DateTime invoicePrebillDate;

        private System.DateTime invoiceDueDate;

        private System.DateTime resendDate;

        private string outputMethod;

        private string queueSecure;

        private bool overwriteDueDate;

        private double invoiceSalesTax;

        private double baseCharge;

        private string invoiceBillingLocCode;

        private string invoiceBillinglocName;

        private double invoiceBalanceDue;

        private double amountpaid;

        private string[] notes;

        private string letterType;

        private bool willInvoiceShipped;

        private List<RequestCoreDeliveryInvoicePatientsList> invoicePatients;

        private RequestTransaction requestTransaction;

        public long RequestCoreId
        {
            get { return requestCoreId; }
            set { requestCoreId = value; }
        }

        public string Type
        {
            get { return type; }
            set { type = value; }
        }

        public long InvoiceDueDays
        {
            get { return invoiceDueDays; }
            set { invoiceDueDays = value; }
        }

        public long LetterTemplateFileId
        {
            get { return letterTemplateFileId; }
            set { letterTemplateFileId = value; }
        }

        public string LetterTemplateName
        {
            get { return letterTemplateName; }
            set { letterTemplateName = value; }
        }

        public string RequestStatus
        {
            get { return requestStatus; }
            set { requestStatus = value; }
        }

        public System.DateTime RequestDate
        {
            get { return requestDate; }
            set { requestDate = value; }
        }

        public System.DateTime InvoicePrebillDate
        {
            get { return invoicePrebillDate; }
            set { invoicePrebillDate = value; }
        }

        public System.DateTime InvoiceDueDate
        {
            get { return invoiceDueDate; }
            set { invoiceDueDate = value; }
        }

        public System.DateTime ResendDate
        {
            get { return resendDate; }
            set { resendDate = value; }
        }

        public string OutputMethod
        {
            get { return outputMethod; }
            set { outputMethod = value; }
        }

        public string QueueSecretWord
        {
            get { return queueSecure; }
            set { queueSecure = value; }
        }

        public bool OverwriteDueDate
        {
            get { return overwriteDueDate; }
            set { overwriteDueDate = value; }
        }

        public double InvoiceSalesTax
        {
            get { return invoiceSalesTax; }
            set { invoiceSalesTax = value; }
        }

        public double BaseCharge
        {
            get { return baseCharge; }
            set { baseCharge = value; }
        }

        public string InvoiceBillingLocCode
        {
            get { return invoiceBillingLocCode; }
            set { invoiceBillingLocCode = value; }
        }

        public string InvoiceBillinglocName
        {
            get { return invoiceBillinglocName; }
            set { invoiceBillinglocName = value; }
        }

        public double InvoiceBalanceDue
        {
            get { return invoiceBalanceDue; }
            set { invoiceBalanceDue = value; }
        }

        public double Amountpaid
        {
            get { return amountpaid; }
            set { amountpaid = value; }
        }


        public string[] Notes
        {
            get { return notes; }
            set { notes = value; }
        }

        public string LetterType
        {
            get { return letterType; }
            set { letterType = value; }
        }

        public bool WillInvoiceShipped
        {
            get { return willInvoiceShipped; }
            set { willInvoiceShipped = value; }
        }

        public List<RequestCoreDeliveryInvoicePatientsList> InvoicePatients
        {
            get
            {
                if (invoicePatients == null)
                {
                    invoicePatients = new List<RequestCoreDeliveryInvoicePatientsList>();
                }
                return invoicePatients;
            }
            set { invoicePatients = value; }
        }

        public RequestTransaction RequestTransaction
        {
            get { return requestTransaction; }
            set { requestTransaction = value; }
        }        
    }
}
    

