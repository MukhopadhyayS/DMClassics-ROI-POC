using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Request.Model
{
    public class RequestCoreChargesInvoiceDetail
    {
        #region Fields

        private string testField;

        private long requestCoreDeliveryChargesIdField;

        private System.Nullable<System.DateTime> invoiceCreatedDtField;

        private double releaseCostField;

        private double paymentAmountField;

        private List<RequestTransaction> reqTransaction;

        private bool isInvoiced;

        #endregion

        #region Properties



        public List<RequestTransaction> ReqTransaction
        {
            get {
                if (reqTransaction == null)
                {
                    return reqTransaction = new List<RequestTransaction>();
                }
                return reqTransaction;
            }
            set { reqTransaction = value; }
        }


        /// <remarks/>
        public string test
        {
            get
            {
                return this.testField;
            }
            set
            {
                this.testField = value;
            }
        }

        /// <remarks/>
        public long requestCoreDeliveryChargesId
        {
            get
            {
                return this.requestCoreDeliveryChargesIdField;
            }
            set
            {
                this.requestCoreDeliveryChargesIdField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
        public System.Nullable<System.DateTime> invoiceCreatedDt
        {
            get
            {
                return this.invoiceCreatedDtField;
            }
            set
            {
                this.invoiceCreatedDtField = value;
            }
        }

        /// <remarks/>
        public double releaseCost
        {
            get
            {
                return this.releaseCostField;
            }
            set
            {
                this.releaseCostField = value;
            }
        }

        /// <remarks/>
        public double paymentAmount
        {
            get
            {
                return this.paymentAmountField;
            }
            set
            {
                this.paymentAmountField = value;
            }
        }

        public bool IsInvoiced
        {
            get { return isInvoiced; }
            set { isInvoiced = value; }
        }
        #endregion
    }
}
