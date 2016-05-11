#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
#endregion
using System;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Class holds the request search criteria details.
    /// </summary>    
    public class FindRequestCriteria 
    { 
        #region Fields

        //holds the First Name
        private string patientFirstName;

        //holds the Last Name
        private string patientLastName;

        //holds the Data of Birth
        private Nullable<DateTime> dob;

        //holds the SSN
        private string patientSsn;

        //holds the EPN
        private string epn;

        //holds the MRN
        private string mrn;

        //holds the Encounter
        private string encounter;

        //holds the Facility
        private string facility;

        //holds the Request Status
        private string requestStatus;

        //holds the Requestor Type
        private long requestorType;

        //holds the Requestor Name
        private string requestorName;

        //holds the request status reason.
        private string statusReason;

        //holds the Receipt Date
        private string receiptDate;

        private string receiptFromDate;
        private string receiptToDate;

        //holds the From Date
        private Nullable<DateTime> fromDate;

        //holds the To Date
        private Nullable<DateTime> toDate;

        //holds the Invoice Number
        private string invoiceNumber;

        //holds the Request ID
        private string requestId;

        //holds the MaximumRecord        
        private int maxrecord;
        
        // holds the IsSearch value
        private bool isSearch;

        //holds the requestor id
        private long requestorId;

        //holds the Non HPF Patient Id
        private long nonHpfPatientId;

        //holds the balance due
        private string balanceDue;

        //holds balance due operator
        private string balanceDueOperator;

        //holds request reason
        private string requestReason;

        //holds the Complete Date
        private string completedDate;

        //holds the From value of Completed Date
        private string completedFromDate;

        //holds the To value of Completed Date
        private string completedToDate;

        private string customFromDate;
        private string customToDate;
        private string customDate;
        private string requestorTypeName;



        #endregion

        #region Methods

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            patientLastName  = (patientLastName.Trim().Length != 0) ? patientLastName.Trim() : null;
            patientFirstName = (patientFirstName.Trim().Length != 0) ? patientFirstName.Trim() : null;
            patientSsn       = (patientSsn.Trim().Length != 0) ? patientSsn.Trim() : null;
            encounter = (encounter.Trim().Length != 0) ? encounter.Trim() : null;
            mrn       = (mrn.Trim().Length != 0) ? mrn.Trim() : null;
            epn       = (epn.Trim().Length != 0) ? epn.Trim() : null;
            requestorName = (requestorName.Trim().Length != 0) ? requestorName.Trim() : null;
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the Request search First Name.
        /// </summary>
        public string PatientFirstName
        {
            get { return patientFirstName; }
            set { patientFirstName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search Last Name.
        /// </summary>
        public string PatientLastName
        {
            get { return patientLastName; }
            set { patientLastName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search Date of Birth.
        /// </summary>
        public Nullable<DateTime> Dob
        {
            get { return dob; }
            set { dob = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search SSN.
        /// </summary>
        public string PatientSsn
        {
            get { return patientSsn; }
            set { patientSsn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search EPN.
        /// </summary>
        public string EPN
        {
            get { return epn; }
            set { epn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search MRN.
        /// </summary>
        public string MRN
        {
            get { return mrn; }
            set { mrn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search Encounter.
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search Facility.
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Status.
        /// </summary>
        public string RequestStatus
        {
            get { return requestStatus; }
            set { requestStatus = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Type.
        /// </summary>
        public long RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Name.
        /// </summary>
        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Status Reason.
        /// </summary>
        public string StatusReason
        {
            get { return statusReason; }
            set { statusReason = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Receipt Date
        /// </summary>
        public string ReceiptDate
        {
            get { return receiptDate; }
            set { receiptDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Receipt To Date
        /// </summary>
        public string ReceiptFromDate
        {
            get { return receiptFromDate; }
            set { receiptFromDate = value; }
        }
		
		/// <summary>
        /// This property is used to get or sets the Receipt To Date
        /// </summary>
        public string ReceiptToDate
        {
            get { return receiptToDate; }
            set { receiptToDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search From Date .
        /// </summary>
        public Nullable<DateTime> FromDate
        {
            get { return fromDate; }
            set { fromDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search To Date .
        /// </summary>
        public Nullable<DateTime> ToDate
        {
            get { return toDate; }
            set { toDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Invoice Number.
        /// </summary>
        public string InvoiceNumber
        {
            get { return invoiceNumber; }
            set { invoiceNumber = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request ID.
        /// </summary>
        public string RequestId
        {
            get { return requestId; }
            set { requestId = value; }
        }

        /// <summary>
        /// This property is used to get or sets the MaxRecord, that returned as Request Search result.
        /// </summary>
        public int MaxRecord
        {
            get { return maxrecord; }
            set { maxrecord = value; }
        }
        
        /// <summary>
        /// This property is used to get or sets the Request Search .
        /// </summary>

        public bool IsSearch
        {
            get { return isSearch; }
            set { isSearch = value; }
        }

        /// <summary>
        /// Gets or sets the RequestorId.
        /// </summary>
        public long RequestorId
        {
            get { return requestorId; }
            set { requestorId = value; }
        }

        /// <summary>
        /// Gets or sets the Non HPF Patient ID.
        /// </summary>
        public long NonHpfPatientId
        {
            get { return nonHpfPatientId; }
            set { nonHpfPatientId = value; }
        }

        /// <summary>
        /// This property is used to get or sets the balance due.
        /// </summary>
        public string BalanceDue
        {
            get { return balanceDue; }
            set { balanceDue = value; }
        }

        /// <summary>
        /// This property is used to get or sets the balancedue operator.
        /// </summary>
        public string BalanceDueOperator
        {
            get { return balanceDueOperator; }
            set { balanceDueOperator = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Reason.
        /// </summary>
        public string RequestReason
        {
            get { return requestReason; }
            set { requestReason = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Complete Date
        /// </summary>
        public string CompletedDate
        {
            get { return completedDate; }
            set { completedDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search From Date .
        /// </summary>
        public string CompletedFromDate
        {
            get { return completedFromDate; }
            set { completedFromDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request search To Date .
        /// </summary>
        public string CompletedToDate
        {
            get { return completedToDate; }
            set { completedToDate = value; }
        }

        public string CustomFromDate
        {
            get { return customFromDate; }
            set { customFromDate = value; }
        }

        public string CustomToDate
        {
            get { return customToDate; }
            set { customToDate = value; }
        }

        public string CustomDate
        {
            get { return customDate; }
            set { customDate = value; }
        }

        public string RequestorTypeName
        {
            get { return requestorTypeName; }
            set { requestorTypeName = value; }
        }
        #endregion
    }
}
