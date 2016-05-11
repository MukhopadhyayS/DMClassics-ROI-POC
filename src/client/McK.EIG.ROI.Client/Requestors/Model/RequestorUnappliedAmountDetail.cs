using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
// ashish
namespace McK.EIG.ROI.Client.Requestors.Model
{
    public class RequestorUnappliedAmountDetail
    {
        private string type;
        private double amount;

        public string Type
        {
            get { return type; }
            set { type = value; }
        }
        public double Amount
        {
            get { return amount; }
            set { amount= value; }
        }
    }
}
