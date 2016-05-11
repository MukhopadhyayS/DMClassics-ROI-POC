using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
    [Serializable]
    public class DocTypeAuditDetails : ROIModel
    {
        private long id;
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }
        private string docType;

        public string DocType
        {
            get { return docType; }
            set { docType = value; }
        }
        private string docName;

        public string DocName
        {
            get { return docName; }
            set { docName = value; }
        }
        private string fromValue;

        public string FromValue
        {
            get { return fromValue; }
            set { fromValue = value; }
        }
        private string toValue;

        public string ToValue
        {
            get { return toValue; }
            set { toValue = value; }
        }
        private string codeSetName;

        public string CodeSetName
        {
            get { return codeSetName; }
            set { codeSetName = value; }
        }

        public DocTypeAuditDetails()
        {
        }
    }
}
