using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    public class RequestorAdjustmentDetails
    {
        #region Fields
        private long requestorSeq;
        private string reason;
        private Double amount;
        private Double unappliedAmount;
        private string adjustmentDate;
        private String note;
        private AdjustmentType adjustmentType;
        private long adjId;
        private string requestorName;
        private string requestorType;
        private bool isRemoveAdjustment;
        private bool isPrebillAdjustment;
        #endregion

        #region Properties

        public long AdjId
        {
            get { return adjId; }
            set { adjId = value; }
        }
        public long RequestorSeq
        {
            get { return requestorSeq; }
            set { requestorSeq = value; }
        }
        public string Reason
        {
            get { return reason; }
            set { reason = value; }
        }
        public Double Amount
        {
            get { return amount; }
            set { amount = value; }
        }
        public Double UnappliedAmount 
        {
            get { return unappliedAmount; }
            set{unappliedAmount = value; }

        }
        public string AdjustmentDate 
        {
            get { return adjustmentDate; }
            set { adjustmentDate = value; }
        }
        public string Note
        {
            get { return note; }
            set { note = value; }
        }
        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }

        public string RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }
        public bool IsRemoveAdjustment
        {
            get { return isRemoveAdjustment; }
            set { isRemoveAdjustment = value; }
        }
        public AdjustmentType AdjustmentType
        {
            get { return adjustmentType; }
            set { adjustmentType = value; }
        }

        /// <remarks/>
        public bool IsPrebillAdjustment
        {
            get { return isPrebillAdjustment; }
            set { isPrebillAdjustment = value; }            
        }
        #endregion
    }
}
