using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    [Serializable]
    public class RequestorHistoryDetail : ROIModel
    {
        #region Fields
        private long id;
        private string type;
        private string creatorName;
        private string createdDate;
        private string template;
        private string requestSecure;
        private string queueSecure;
        private string invoiceDueDate;
        private double balance;
        private double invoiceBalance;
        private string aging;
        private string status;
        private string prebillStatus;
        private long requestId;
        private bool isMigrated;

        #endregion Fields

        #region Properties
        public long RequestId
        {
            get { return requestId; }
            set { requestId = value; }
        }

        public double InvoiceBalance
        {
            get { return invoiceBalance; }
            set { invoiceBalance = value; }
        }

        public string PrebillStatus
        {
            get { return prebillStatus; }
            set { prebillStatus = value; }
        }
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }
        public string Type
        {
            get { return type; }
            set { type = value; }
        }
        public string CreatorName
        {
            get { return creatorName; }
            set { creatorName = value; }
        }
        public string CreatedDate
        {
            get{return createdDate;}
            set{createdDate=value;}
        }
        public string Template
        {
            get { return template; }
            set { template = value; }
        }
        public string RequestSecretWord
        {
            get{return requestSecure;}
            set{requestSecure=value;}
        }
        public string QueueSecretWord
        {
            get{ return queueSecure;}
            set{queueSecure=value;}
        }
        public string PlainQueueSecretWord
        {
            get { return ROIController.DecryptAES(queueSecure); }
            set { queueSecure = ROIController.EncryptAES(value); }
        }
        public string PlainRequestSecure
        {
            get { return ROIController.DecryptAES(requestSecure); }
            set { requestSecure = ROIController.EncryptAES(value); }
        }
        public string InvoiceDueDate
        {
            get{return invoiceDueDate;}
            set{invoiceDueDate=value;}
        }
        public double Balance
        {
            get{return balance;}
            set{balance=value;}
        }
        public string Aging
        {
            get{return aging;}
            set{aging=value;}
        }
        public string Status 
        {
            get { return status; }
            set { status = value; }
        }

        public bool IsMigrated
        {
            get { return isMigrated; }
            set { isMigrated = value; }
        }

        #endregion Properties

    }
}
