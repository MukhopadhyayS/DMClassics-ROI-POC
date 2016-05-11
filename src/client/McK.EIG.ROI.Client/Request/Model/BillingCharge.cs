using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Request.Model
{
    public class BillingCharge
    {
        #region Fields

        private List<DocumentChargeDetails> documentCharge;
        private List<FeeChargeDetails> feeCharge;

        #endregion

        #region Properties

        public List<DocumentChargeDetails> DocumentCharge       
        {
            get 
            {
                if (documentCharge == null)
                {
                    return new List<DocumentChargeDetails>();
                }
                return documentCharge; 
            }
            set { documentCharge = value; }
        }

        public List<FeeChargeDetails> FeeCharge
        {
            get
            {
                if (feeCharge == null)
                {
                    return new List<FeeChargeDetails>();
                }
                return feeCharge;
            }
            set { feeCharge = value; }
        }

        #endregion
    }
}
