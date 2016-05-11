using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

using McK.EIG.ROI.Client.Base.Model;
namespace McK.EIG.ROI.Client.Request.Model
{
    public class RequestBillingInfo:RequestCoreChargeDetails
    {
        #region Fields

        private List<RequestCoreChargesInvoiceDetail> reqCoreChargesInvoiceDetail;
        private List<SalesTaxReasons> salesTaxReasonsList;

        #endregion
        public RequestBillingInfo()
        {
            //invoiceChargeList = new List<RequestCoreChargesInvoiceDetail>();
        }
        #region Properties
        public List<RequestCoreChargesInvoiceDetail> ReqCoreChargesInvoiceDetail
        {
            get
            {
                if (reqCoreChargesInvoiceDetail == null)
                {
                    reqCoreChargesInvoiceDetail = new List<RequestCoreChargesInvoiceDetail>();
                }
                return reqCoreChargesInvoiceDetail;
            }
            set { reqCoreChargesInvoiceDetail = value; }
        }
        public List<SalesTaxReasons> SalesTaxReasonsList
        {
            get
            {
                if (salesTaxReasonsList == null)
                {
                    salesTaxReasonsList = new List<SalesTaxReasons>();
                }
                return salesTaxReasonsList;
            }
            set
            {
                salesTaxReasonsList = value;
            }
        }

        #endregion


    }
}
