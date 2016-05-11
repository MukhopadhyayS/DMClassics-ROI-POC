using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Patient.Model
{
    public class RetrieveCCDDetails
    {
        private string retrieveccdkey;
        private string retriveccdvalue;
        

        public RetrieveCCDDetails(string retrieveccdkey, string retriveccdvalue)
        {
            this.retrieveccdkey = retrieveccdkey;
            this.retriveccdvalue = retriveccdvalue;
        }

        public string retrieveCCDKey
        {
            get { return retrieveccdkey; }
            set { retrieveccdkey = value; }
        }

        public string retriveCCDValue
        {
            get { return retriveccdvalue; }
            set { retriveccdvalue = value; }
        }

        public RetrieveCCDDetails Clone()
        {
            return (RetrieveCCDDetails)this.MemberwiseClone();
        }

    }
}
