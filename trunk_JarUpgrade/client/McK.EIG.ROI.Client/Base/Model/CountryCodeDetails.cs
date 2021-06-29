using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Base.Model
{
    public class CountryCodeDetails
    {
        private string countryCode;

        private string countryName;

        private bool defaultCountry;

        private long countrySeq;

        public string CountryCode
        {
            get { return countryCode; }
            set { countryCode = value; }
        }

        public string CountryName
        {
            get { return countryName; }
            set { countryName = value; }
        }

        public bool DefaultCountry
        {
            get { return defaultCountry; }
            set { defaultCountry = value; }
        }

        public long CountrySeq
        {
            get { return countrySeq; }
            set { countrySeq = value; }
        }
    }
}
