using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Admin.Model;
using System.Collections.ObjectModel;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    public class AdjustmentInfoDetail
    {
        #region Fields
        private Collection<ReasonDetails> reasonsList;
        private RequestorAdjustmentDetails reqAdjustmentDetails;
        private Collection<RequestInvoiceDetail> requestorInvoicesList;

        #endregion

        #region Properties
        public Collection<ReasonDetails> ReasonsList
        {
            get
            {
                if (reasonsList == null)
                {
                    reasonsList = new Collection<ReasonDetails>();    
                }
                return reasonsList;
            }
            set
            {
                reasonsList = value;        
            }
        }
        public RequestorAdjustmentDetails ReqAdjustmentDetails
        {
            get
            {
                if (reqAdjustmentDetails == null)
                {
                    reqAdjustmentDetails = new RequestorAdjustmentDetails();
                }
                return reqAdjustmentDetails;
            }
            set
            {
                reqAdjustmentDetails = value;
            }
        }
        public Collection<RequestInvoiceDetail> RequestorInvoicesList
        {
            get 
            {
                if (requestorInvoicesList == null)
                {
                    requestorInvoicesList = new Collection<RequestInvoiceDetail>();
                }
                return requestorInvoicesList;
            }
            set 
            {
                requestorInvoicesList = value;
            }
        }

        #endregion

    }
}
