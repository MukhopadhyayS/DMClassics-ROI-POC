using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace McK.EIG.ROI.Client.Base.Model
{
    public class GenderDetails
    {
        public string genderCode;
        public string genderDesc;
        //private List<GenderDetails> genderCodeDetails;
        public string GenderCode
        {
            get { return genderCode; }
            set { genderCode = value; }
        }

        public string GenderDesc
        {
            get { return genderDesc; }
            set { genderDesc = value; }
        }

        public List<GenderDetails> genderInfo
        {
            get
            {

                if (genderInfo == null)
                {
                    genderInfo = new List<GenderDetails>();
                }
                return genderInfo;
            }
            set { genderInfo = value; }
        }
    }
}
