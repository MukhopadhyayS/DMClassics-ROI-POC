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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Request.Model
{

    /// <summary>
    /// RequestDetailsComparer
    /// </summary>
    public class RequestDetailsComparer : GenericComparer
    {
        protected override object GetPropertyValue(object from, PropertyDescriptor descriptor)
        {
            RequestDetails request = (RequestDetails)from;
            if ("ReceiptDate".Equals(descriptor.Name))
            {
                return new StringBuilder().Append((request.ReceiptDate.HasValue)
                                                   ? request.ReceiptDate.Value.ToString(ROIConstants.SorterDateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture)
                                                   : string.Empty).Append(ROIConstants.Delimiter).
                                           Append(request.Status.ToString()).Append(ROIConstants.Delimiter).
                                           Append(request.RequestorName).Append(ROIConstants.Delimiter).
                                           Append(request.RequestorTypeName).Append(ROIConstants.Delimiter).
                                           Append(request.PatientNames).Append(ROIConstants.Delimiter).
                                           Append((request.LastUpdated.HasValue)
                                                   ? request.LastUpdated.Value.ToString(ROIConstants.SorterDateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture)
                                                   : string.Empty).Append(ROIConstants.Delimiter).
                                           Append(request.UpdatedBy).Append(ROIConstants.Delimiter).
                                           Append(request.BalanceDue.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture)).ToString();
                             
            }
            else if ("Status".Equals(descriptor.Name))
            {
                return request.Status.ToString();
            }
            else if("LastUpdated".Equals(descriptor.Name))
            {
                return (request.LastUpdated.HasValue)
                                                   ? request.LastUpdated.Value.ToString(ROIConstants.SorterDateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture)
                                                   : string.Empty;
            }

            return base.GetPropertyValue(from, descriptor);
        }
    }

    /// <summary>
    /// This class is used to hold Request Details Info.
    /// </summary>
    [Serializable]
    public class RequestDetails : ROIModel
    {

        private class RequestDetailsSorter : IComparer<RequestDetails>
        {
            #region IComparer<RequestDetails> Members

            public int Compare(RequestDetails x, RequestDetails y)
            {
                return x.updatedBy.CompareTo(y.updatedBy);
            }

            #endregion
        }

        
        private static RequestDetailsSorter sorter;

        public static IComparer<RequestDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new RequestDetailsSorter();
                }
                return sorter;
            }
        }

        # region Fields

        public const string LastUpdatedKey = "REQUEST_UPDATED_DATE";
        public const string UpdatedByKey   = "REQUEST_UPDATED_BY";
        public const string NotApplicable = "NA";

        //Request
        public const string IdKey = "id";
        public const string RequestStatusKey = "status";
        public const string ReceiptDateKey = "receipt-date";
        public const string BalanceDueKey = "balance-due";
        public const string SubtitleKey = "subtitle";                  
        
        //Requestor
        public const string RequestorNameKey          = "name";
        public const string RequestorTypeNameKey      = "type-name";
        public const string RequestorTypeKey          = "type";
        public const string RequestorIdKey            = "id";        
      
        private const string RequestorElement         = "<{0} {1}=\"{2}\">";
        public const string PatientNameDelimiter      = ":";

		//CR#359276 - Add automatic adjustment transaction for the current invoice
        //public const string InvoiceBaseChargeKey      = "invoice-base-charge";
        //public const string InvoiceAutoAdjustmentKey  = "invoice-auto-adjustment";

        // CR#365397 To find whether the invoice is created or not for a request        
               
        private long id;
        
        private string requestReason;

        private string requestReasonAttribute;

        private string receiptDateText;

        private Nullable<DateTime> receiptDate;

        private Nullable<DateTime> dateCreated;

        private Nullable<DateTime> lastUpdated;

        private string updatedBy;

        private long requestorId;

        private string requestorName;

        private string requestorFirstName;

        private string requestorTypeName;

        private long requestorType;

        private string requestorHomePhone;

        private string requestorWorkPhone;

        private string requestorCellPhone;

        private string requestorFax;

        private string requestorContactName;

        private string requestorContactPhone;

        private bool hasSalesTax;

        private bool certificationLetterRequired;

        private bool prepayRequired;

        private bool isReleased;

        private RequestStatus status;

        private string statusReason;

        private bool requestorActive;

        private bool requestorFrequent;

        private bool requestorIsPatient;

        private string billToAddressLine;

        private string billToCity;

        private string billToState;

        private string billToPostalCode;

        private string billToContactNameLast;

        private string billToContactNameFirst;

        private Collection<String> encounters;

        private double balance;
        private double originalBalance;
        private double paymentAmount;
        private double creditAmount;
        private double debitAmount;
        private double salesTaxAmount;

        private string patientNames;

        private string completePatientNames;

        private string firstTwentyPatientNames;

        private string facility;

        //holds the Requestor object model
        private RequestorDetails requestorDetails;

        //holds the patient object model
        private SortedList<string, RequestPatientDetails> patientDetails;

        private string freeFormReasons;

        private string details;

        private Collection<ReleaseDetails> releases;

        private ReleaseDetails draftRelease;

        private bool isLocked;

        private Image inUseImage;

        private InUseRecordDetails inUseRecord;

        private Nullable<DateTime> statusChanged;

        private Collection<RequestTransaction> newTransactions;

        private string authRequest;

        private string authRequestSubtitle;

        private string authRequestDocumentName;

        private string authRequestDocumentDateTime;

        private string authRequestConversionSource;

        private Image lockedImage;

        private Image vipImage;

        private bool hasLockedPatient;

        private bool hasVipPatient;

        private bool hasBlockedRequestFacility;

        private bool hasMaskedRequestFacility;

        private int unauthorizedPatientCount;

        private Nullable<DateTime> completedDate;

        private string requestSecure;
      
        private TaxPerFacilityDetails defaultFacility;

        private bool isOldRequest;

        private double invoiceBaseCharge;//CR#359276 - Add automatic adjustment transaction for the current invoice

        private double invoiceAutoAdjustment;//CR#359276 - Add automatic adjustment transaction for the current invoice        

        private bool isInvoiced; // CR#365397 To check the invoice status of the request

        private bool hasDraftRelease;

        private long releaseCount;

        # endregion

        #region Methods

        public static bool IsReasonRequired(RequestStatus status)
        {
            return (status == RequestStatus.Denied || status == RequestStatus.Canceled || status == RequestStatus.Pended);
        }

        /// <summary>
        /// Retrieve non-printable attachment count
        /// </summary>
        /// <returns></returns>
        public int RetrieveNonPrintableCount()
        {
            int count = 0;
            if (patientDetails != null)
            {
                foreach (RequestPatientDetails requestPatientDetails in patientDetails.Values)
                {
                    if (requestPatientDetails.Attachment.RetrieveNonPrintableCount() > 0)
                    {
                        count += requestPatientDetails.Attachment.RetrieveNonPrintableCount();
                    }
                }
            }
            return count;
        }            

        public bool AddNonHpfDocument(IList<BaseRecordItem> nonHpfDocs, long nonHpfBillingTier)
        {
            RequestNonHpfDocumentDetails doc = null;
            RequestNonHpfEncounterDetails encounter;
            bool newlyAdded = true;
            foreach (NonHpfDocumentDetails nonHpfDoc in nonHpfDocs)
            {
                encounter = EnsurePath((NonHpfEncounterDetails)nonHpfDoc.Parent);
                doc = encounter.GetChild(nonHpfDoc.Key) as RequestNonHpfDocumentDetails;
                if(doc == null)
                {
                    doc = new RequestNonHpfDocumentDetails(nonHpfDoc);
                    doc.BillingTier = nonHpfBillingTier;
                    encounter.PageCount += doc.PageCount;
                    encounter.AddChild(doc);
                }
                else
                {
                    newlyAdded = false;
                }
            }

            return newlyAdded;
        }

        private RequestNonHpfEncounterDetails EnsurePath(NonHpfEncounterDetails encounter)
        {
            PatientNonHpfDocument nonHpfDocument = (PatientNonHpfDocument)encounter.Parent;
            RequestPatientDetails reqPatient = Patients[nonHpfDocument.Parent.Key];
            RequestNonHpfEncounterDetails requestNonHpfEnc = (RequestNonHpfEncounterDetails)reqPatient.NonHpfDocument.GetChild(encounter.Key);
            if (requestNonHpfEnc == null)
            {
                requestNonHpfEnc = new RequestNonHpfEncounterDetails(encounter);
                reqPatient.NonHpfDocument.AddChild(requestNonHpfEnc);
            }
            return requestNonHpfEnc;
        }

        public bool AddAttachment(IList<BaseRecordItem> attachments, long nonHpfBillingTier)
        {
            RequestAttachmentDetails requestAttachmentDetails = null;
            RequestAttachmentEncounterDetails encounter;
            bool newlyAdded = true;
            foreach (AttachmentDetails attachmentDetails in attachments)
            {
                encounter = EnsurePath((AttachmentEncounterDetails)attachmentDetails.Parent);

                requestAttachmentDetails = encounter.GetChild(attachmentDetails.Key) as RequestAttachmentDetails;
                if (requestAttachmentDetails == null)
                {
                    requestAttachmentDetails = new RequestAttachmentDetails(attachmentDetails);
                    requestAttachmentDetails.BillingTier = nonHpfBillingTier;
                    encounter.PageCount += requestAttachmentDetails.PageCount;
                    encounter.AddChild(requestAttachmentDetails);
                }
                else
                {
                    newlyAdded = false;
                }
            }

            return newlyAdded;
        }

        private RequestAttachmentEncounterDetails EnsurePath(AttachmentEncounterDetails encounter)
        {
            PatientAttachment tmpAttachment = (PatientAttachment)encounter.Parent;
            RequestPatientDetails reqPatient = Patients[tmpAttachment.Parent.Key];
            RequestAttachmentEncounterDetails requestAttachmentEnc = (RequestAttachmentEncounterDetails)reqPatient.Attachment.GetChild(encounter.Key);
            if (requestAttachmentEnc == null)
            {
                requestAttachmentEnc = new RequestAttachmentEncounterDetails(encounter);
                reqPatient.Attachment.AddChild(requestAttachmentEnc);
            }
            return requestAttachmentEnc;
        }

        public bool AddPage(PageDetails page)
        {
            RequestVersionDetails requestVersion = EnsurePath((VersionDetails)page.Parent);
            RequestPageDetails requestPage = (RequestPageDetails)requestVersion.GetChild(page.Key);

            if (requestPage != null)
            {
                return false;
            }
            requestPage = new RequestPageDetails(page);

            requestVersion.AddChild(requestPage);
            requestPage.SelectedForRelease = true;
            return true;
        }

        private RequestVersionDetails EnsurePath(VersionDetails version)
        {
            RequestDocumentDetails requestDoc = EnsurePath((DocumentDetails)version.Parent);
            RequestVersionDetails requestVersion = (RequestVersionDetails)requestDoc.GetChild(version.Key);
            if (requestVersion == null)
            {
                requestVersion = new RequestVersionDetails(version);
                requestDoc.AddChild(requestVersion);
            }
            return requestVersion;
        }

        private RequestDocumentDetails EnsurePath(DocumentDetails doc)
        {
            RequestDocumentDetails requestDoc = null;
            EncounterDetails enc = doc.Parent as EncounterDetails;
            if (enc != null)
            {
                RequestEncounterDetails requestEncounter = EnsurePath(enc);
                requestDoc = (RequestDocumentDetails)requestEncounter.GetChild(doc.Key);
                if (requestDoc == null)
                {
                    requestDoc = new RequestDocumentDetails(doc);
                    requestEncounter.AddChild(requestDoc);
                }
            }
            else
            {
                PatientGlobalDocument patientGlobal = (PatientGlobalDocument)doc.Parent;
                RequestPatientDetails requestPatient = Patients[patientGlobal.Parent.Key];
                requestDoc = (RequestDocumentDetails)requestPatient.GlobalDocument.GetChild(doc.Key);
                if (requestDoc == null)
                {
                    requestDoc = new RequestDocumentDetails(doc);
                    requestPatient.GlobalDocument.AddChild(requestDoc);
                }
            }
            return requestDoc;
        }

        private RequestEncounterDetails EnsurePath(EncounterDetails enc)
        {

            RequestPatientDetails requestPatient = Patients[enc.Parent.Key];
            string tempKeyVAlue = enc.Key;
            int a = tempKeyVAlue.IndexOf(".");
            string subkey = "0" + tempKeyVAlue.Substring(a);
            RequestEncounterDetails requestEncounter = (RequestEncounterDetails)requestPatient.GetChild(subkey);                        

            if (requestEncounter == null)
            {
                requestEncounter = new RequestEncounterDetails(enc);
                requestPatient.AddChild(requestEncounter);
            }
            return requestEncounter;
        }


        public RequestPatientDetails AddPatient(PatientDetails patient)
        {
            RequestPatientDetails requestPatient = null;            
            //Temporarily added for no duplicates
            if (Patients.ContainsKey(patient.Key))
            {
                requestPatient = Patients[patient.Key];
            }
            else
            {
                requestPatient = new RequestPatientDetails(patient);
                Patients.Add(patient.Key, requestPatient);
            }
            return requestPatient;
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the Request Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        public string RequestReason
        {
            get { return requestReason; }
            set { requestReason = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Reason attribute name.
        /// </summary>
        public string RequestReasonAttribute
        {
            get { return requestReasonAttribute; }
            set { requestReasonAttribute = value; }
        }

        /// <summary>
        /// This property is used to get or sets the receiptDate.
        /// </summary>
        public Nullable<DateTime> ReceiptDate
        {
            get { return receiptDate; }
            set { receiptDate = value; }
        }

        public string ReceiptDateText
        {
            get { return receiptDateText; }
            set { receiptDateText = value; }
        }

        /// <summary>
        /// This property is used to get or sets the dateCreated.
        /// </summary>
        public Nullable<DateTime> DateCreated
        {
            get { return dateCreated; }
            set { dateCreated = value; }
        }

        /// <summary>
        /// This property is used to get or sets the lastUpdated.
        /// </summary>
        public Nullable<DateTime> LastUpdated
        {
            get { return lastUpdated; }
            set { lastUpdated = value; }
        }

        /// <summary>
        /// This property is used to get or sets the updatedBy.
        /// </summary>
        public string UpdatedBy
        {
            get { return updatedBy; }
            set { updatedBy = value; }
        }

        /// <summary>
        /// Gets or sets the Requestor Id.
        /// </summary>
        public long RequestorId
        {
            get { return requestorId; }
            set { requestorId = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestorName.
        /// </summary>
        public string RequestorName
        {
            get { return hasMaskedRequestFacility ? ROIConstants.MaskedText : requestorName; }
            set { requestorName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestorName.
        /// </summary>
        public string RequestorFirstName
        {
            get { return requestorFirstName; }
            set { requestorFirstName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestorType name.
        /// </summary>
        public string RequestorTypeName
        {
            get { return hasMaskedRequestFacility ? ROIConstants.MaskedText : requestorTypeName; }
            set { requestorTypeName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestorType.
        /// </summary>
        public long RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestor Home Phone.
        /// </summary>
        public string RequestorHomePhone
        {
            get { return requestorHomePhone; }
            set { requestorHomePhone = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestor Work Phone.
        /// </summary>
        public string RequestorWorkPhone
        {
            get { return requestorWorkPhone; }
            set { requestorWorkPhone = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Cell Phone.
        /// </summary>
        public string RequestorCellPhone
        {
            get { return requestorCellPhone; }
            set { requestorCellPhone = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Fax.
        /// </summary>
        public string RequestorFax
        {
            get { return requestorFax; }
            set { requestorFax = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Contact Name.
        /// </summary>
        public string RequestorContactName
        {
            get { return requestorContactName; }
            set { requestorContactName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Contact Phone.
        /// </summary>
        public string RequestorContactPhone
        {
            get { return requestorContactPhone; }
            set { requestorContactPhone = value; }
        }
        
        public bool HasSalesTax
        {
            get { return hasSalesTax; }
            set { hasSalesTax = value; }
        }

        public bool CertificationLetterRequired
        {
            get { return certificationLetterRequired; }
            set { certificationLetterRequired = value; }
        }

        /// <summary>
        /// This property is used to get or sets the isReleased.
        /// </summary>
        public bool IsReleased
        {
            get { return isReleased; }
            set { isReleased = value; }
        }

        public RequestStatus Status
        {
            get { return status; }
            set { status = value; }
        }

        /// <summary>
        /// This property is used to get or sets the status reason.
        /// </summary>
        public string StatusReason
        {
            get { return statusReason; }
            set { statusReason = value; }
        }

        /// <summary>
        /// Gets or sets the balance.
        /// </summary>
        public double BalanceDue
        {
            get { return balance; }
            set { balance = value; }
        }

        /// <summary>
        /// Gets or sets the Original Balance.
        /// </summary>
        public double OriginalBalance
        {
            get { return originalBalance; }
            set { originalBalance = value; }
        }      

        /// <summary>
        /// Gets or sets the Payment Amount.
        /// </summary>
        public double PaymentAmount
        {
            get { return paymentAmount; }
            set { paymentAmount = value; }
        }

        /// <summary>
        /// Gets or sets the Credit Amount.
        /// </summary>
        public double CreditAmount
        {
            get { return creditAmount; }
            set { creditAmount = value; }
        }

        /// <summary>
        /// Gets or sets the Debit Amount.
        /// </summary>
        public double DebitAmount
        {
            get { return debitAmount; }
            set { debitAmount = value; }
        }

        /// <summary>
        /// Gets or sets the salesTax Amount.
        /// </summary>
        public double SalesTaxAmount
        {
            get { return salesTaxAmount; }
            set { salesTaxAmount = value; }
        }

        /// <summary>
        /// Gets or sets the list of encounters
        /// </summary>
        public Collection<String> Encounters
        {
            get
            {
                if (encounters == null)
                {
                    encounters = new Collection<String>();
                }
                return encounters;
            }
        }

        /// <summary>
        /// Get or sets the Requestor object model.
        /// </summary>
        public RequestorDetails Requestor
        {
            get { return requestorDetails; }
            set { requestorDetails = value; }
        }

        public bool RequestorActive
        {
            get { return requestorActive; }
            set { requestorActive = value; }
        }

        public bool RequestorFrequent
        {
            get { return requestorFrequent; }
            set { requestorFrequent = value; }
        }

        public bool PrepayRequired
        {
            get { return prepayRequired; }
            set { prepayRequired = value; }
        }

        public bool RequestorIsPatient
        {
            get { return requestorIsPatient; }
            set { requestorIsPatient = value; }
        }

        public string BillToAddressLine
        {
            get { return billToAddressLine; }
            set { billToAddressLine = value; }
        }

        public string BillToCity
        {
            get { return billToCity; }
            set { billToCity = value; }
        }

        public string BillToState
        {
            get { return billToState; }
            set { billToState = value; }
        }

        public string BillToPostalCode
        {
            get { return billToPostalCode; }
            set { billToPostalCode = value; }
        }

        public string BillToContactNameLast
        {
            get { return billToContactNameLast; }
            set { billToContactNameLast = value; }
        }

        public string BillToContactNameFirst
        {
            get { return billToContactNameFirst; }
            set { billToContactNameFirst = value; }
        }

        public string FreeformReasons
        {
            get { return freeFormReasons; }
            set { freeFormReasons = value; }
        }
   
        /// <summary>
        /// Gets or sets the patient Names.
        /// </summary>
        public string PatientNames
        {
            get { return hasMaskedRequestFacility ? ROIConstants.MaskedText : patientNames; }
            set { patientNames = value; }
        }

        /// <summary>
        /// Gets or sets the Complete patient Names.
        /// </summary>
        public string CompletePatientNames
        {
            get { return completePatientNames; }
            set { completePatientNames = value; }
        }

        public string FirstTwentyPatientNames
        {
            get { return hasMaskedRequestFacility ? ROIConstants.MaskedText : firstTwentyPatientNames; }
            set { firstTwentyPatientNames = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient object model.
        /// </summary>
        public SortedList<string, RequestPatientDetails> Patients
        {
            get 
            {
                if (patientDetails == null)
                {
                    patientDetails = new SortedList<string, RequestPatientDetails>();
                }
                return patientDetails; 
            }
        }

        /// <summary>
        /// Details of the reelase object
        /// </summary>
        public string Details
        {
            get { return details; }
            set { details = value; }
        }

        /// <summary>
        /// Releases of the request
        /// </summary>
        public Collection<ReleaseDetails> Releases
        {
            get
            {
                if (releases == null)
                {
                    releases = new Collection<ReleaseDetails>();
                }

                return releases;
            }
        }
       
        /// <summary>
        /// Return only the released patients
        /// </summary>
        public SortedList<string, RequestPatientDetails> ReleasedItems
        {

            get
            {
                List<RequestPatientDetails> patients = new List<RequestPatientDetails>(Patients.Values);
                patients = patients.FindAll(delegate(RequestPatientDetails patient) 
                                                    { 
                                                        return (patient.HasDocuments && (patient.CheckedState != false)); 
                                                    });
                SortedList<string, RequestPatientDetails> relasedPatients = new SortedList<string, RequestPatientDetails>();
                foreach (RequestPatientDetails patient in patients)
                {
                    relasedPatients.Add(patient.Key, patient.ReleasedItems);
                }

                return relasedPatients;
            }
        }

        /// <summary>
        /// Checks whether the request has draft release or not
        /// </summary>
        public bool HasDraftRelease
        {
            get { return hasDraftRelease; }
            set { hasDraftRelease = value; }
        }

        public long ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }

        /// <summary>
        /// Draft release of the request
        /// </summary>
        public ReleaseDetails DraftRelease
        {
            get { return draftRelease; }
            set { draftRelease = value; }
        }

        /// <summary>
        /// Gets or sets the value of islocked property
        /// </summary>
        public bool IsLocked
        {
            get { return isLocked; }
            set 
            { 
                isLocked = value; 
                inUseImage = (isLocked) ? ROIImages.InUseIcon : null;
            }
        }

        /// <summary>
        /// This property is used to get or sets the InUse image
        /// </summary>
        public Image InUseImage
        {
            get { return inUseImage; }
            set { inUseImage = value; }
        }

        public InUseRecordDetails InUseRecord
        {
            get { return inUseRecord; }
            set {inUseRecord = value; }
        }

        /// <summary>
        /// Gets the reasons list with comma separator.
        /// </summary>
        public string Reasons
        {
            get
            {
                 return (!string.IsNullOrEmpty(statusReason)) ? statusReason.Replace(ROIConstants.StatusReasonDelimiter, ", "): string.Empty;
            }            
        }

        /// <summary>
        /// Returns true if one or more patients with no documents specified for release otherwise false
        /// </summary>
        public bool HasPatientWithNoReleaseRecords
        {
            get
            {
                bool hasReleaseDocuments;
                foreach (RequestPatientDetails patient in Patients.Values)
                {
                    hasReleaseDocuments = false;
                    foreach (ReleaseDetails release in releases)
                    {
                        if (release.ReleasedPatients.ContainsKey(patient.Key))
                        {
                            hasReleaseDocuments = true;
                            break;
                        }
                    }

                    if (!hasReleaseDocuments)
                    {
                        return true;
                    }
                }

                return false;
            }
        }

        /// <summary>
        /// Returns true if documents are patrtially released otherwise false
        /// </summary>
        public bool IsPartiallyReleased
        {
            get
            {
                foreach (RequestPatientDetails patient in Patients.Values)
                {
                    if (patient.HasDocuments && patient.PartiallyReleased)
                    {
                        return true;
                    }
                }

                return false;
            }
        }

        public Nullable<DateTime> StatusChanged
        {
            get { return statusChanged; }
            set { statusChanged = value; }
        }

        public Collection<RequestTransaction> NewTransactions
        {
            get
            {
                if (newTransactions == null)
                {
                    newTransactions = new Collection<RequestTransaction>();
                }

                return newTransactions;
            }
        }

        public string AuthRequest
        {
            get { return authRequest; }
            set {  authRequest = value; }
        }

        public string AuthRequestSubtitle
        {
            get { return hasMaskedRequestFacility ? ROIConstants.MaskedText : authRequestSubtitle; }
            set { authRequestSubtitle = value; }
        }

        public string AuthRequestDocumentName
        {
            get { return authRequestDocumentName; }
            set { authRequestDocumentName = value; }
        }

        public string AuthRequestDocumentDateTime
        {
            get { return authRequestDocumentDateTime; }
            set { authRequestDocumentDateTime = value; }
        }

        public string AuthRequestConversionSource
        {
            get { return authRequestConversionSource; }
            set { authRequestConversionSource = value; }
        }   

        /// <summary>
        /// This property is used to get or sets the locked image
        /// </summary>
        public Image LockedImage
        {
            get { return lockedImage; }
            set { lockedImage = value; }
        }

        /// <summary>
        /// This property is used to get or sets the vip image
        /// </summary>
        public Image VipImage
        {
            get { return vipImage; }
            set {  vipImage = value; }
        }

        //Property defined for deleting the draft release 
        //which is associated to the request, if the value set to true
        private bool deleteRelease;
        public bool DeleteRelease
        {
            get { return deleteRelease; }
            set { deleteRelease = value; }
        }

        /// <summary>
        /// Gets os sets if the request has locked patient
        /// </summary>
        public bool HasLockedPatient
        {
            get { return hasLockedPatient; }
            set { hasLockedPatient = value; }
        }

        /// <summary>
        /// Gets os sets if the request has vip patient 
        /// </summary>
        public bool HasVipPatient
        {
            get { return hasVipPatient; }
            set { hasVipPatient = value; }
        }

        /// <summary>
        /// Gets or sets whether the request will show in result grid.
        /// </summary>
        public bool HasBlockedRequestFacility
        {
            get { return hasBlockedRequestFacility; }
            set { hasBlockedRequestFacility = value; }
        }

        /// <summary>
        /// Gets or sets whether the request has to be masked in result grid
        /// </summary>
        public bool HasMaskedRequestFacility
        {
            get { return hasMaskedRequestFacility; }
            set { hasMaskedRequestFacility = value; }
        }

        /// <summary>
        /// Gets or sets the number of unauthorized patients
        /// </summary>
        public int UnauthorizedRequest
        {
            get { return unauthorizedPatientCount; }
            set { unauthorizedPatientCount = value; }
        }

        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }

        public string RequestFacility
        {
            get { return Convert.ToString(id, System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + facility; }
        }

        /// <summary>
        /// This property is used to get or sets the completedDate.
        /// </summary>
        public Nullable<DateTime> CompletedDate
        {
            get { return completedDate; }
            set { completedDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the request level password for PDF queue.
        /// </summary>
        public string RequestSecretWord
        {
            get { return requestSecure; }
            set { requestSecure = value; }
        }

        public TaxPerFacilityDetails DefaultFacility
        {
            get { return defaultFacility; }
            set { defaultFacility = value; }
        }

        public double TaxPercentage
        {
            get
            {
                double taxRate = 0;
                if (DefaultFacility != null)
                {
                    //CR# 359331                
                    taxRate = DefaultFacility.TaxPercentage;
                    
                }
                return taxRate;
            }
        }

        public bool IsOldRequest
        {
            get { return isOldRequest; }
            set { isOldRequest = value; }
        }

        //CR#359276 - Hold the details of original invoice base charge
        public double InvoiceBaseCharge
        {
            get { return invoiceBaseCharge; }
            set { invoiceBaseCharge = value; }
        }

        //CR#359276 - Holds the details of autoadjustments made for the current invoice
        public double InvoiceAutoAdjustment
        {
            get { return invoiceAutoAdjustment; }
            set { invoiceAutoAdjustment = value; }
        }

        // CR#365397
		public bool IsInvoiced
        {
            get { return isInvoiced; }
            set { isInvoiced = value; }
        }

        #endregion       
    }
}
