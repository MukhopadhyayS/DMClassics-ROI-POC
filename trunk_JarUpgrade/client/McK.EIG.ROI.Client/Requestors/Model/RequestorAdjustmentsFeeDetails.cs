using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    public class RequestorAdjustmentsFeeDetails
    {
        #region Fields
        private string feeName;
        private Double amount;
        private Double salesTaxAmount;
        private string feeType;
        private bool isTaxable;

        #endregion

        #region Properties

        public string FeeName
        {
            get { return feeName; }
            set { feeName = value; }
        }

        public Double Amount
        {
            get { return amount; }
            set { amount = value; }
        }
        public Double SalesTaxAmount
        {
            get { return salesTaxAmount; }
            set { salesTaxAmount = value; }
        }
        public string FeeType
        {
            get { return feeType; }
            set { feeType = value; }
        }
        public bool IsTaxable
        {
            get { return isTaxable; }
            set { isTaxable = value; }
        }


        #endregion 

    }
}
