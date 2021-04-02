using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    public class RequestorInvoicesListDetail
    {
        private List<RequestInvoiceDetail> reqInvoiceList;
        public List<RequestInvoiceDetail> ReqInvoiceList
        {
              get
              {
                  if (reqInvoiceList == null)
                    {
                        return new List<RequestInvoiceDetail>();
                    }
                  return reqInvoiceList;
              }
            set { reqInvoiceList = value; }
        }
    }
}
