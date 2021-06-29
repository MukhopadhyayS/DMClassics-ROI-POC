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
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.Model
{
    [Serializable]
    public class RequestNonHpfDocumentDetails : BaseRequestItem
    {
        #region Fields
        
        private long id;
        private long documentSeq;
        private long supplementalID;
        private long patientSeq;
        private bool isHPF;
        private string patientKey;
        private long sequenceNumber;
        private string docType;
        private string encounter;
        private Nullable<DateTime> dateOfService;
        private string facility;
        private string department;
        private string comment;
        private bool? selectedForRelease;
        private int createdBy;
        private long patientID;
        private string patientMRN;
        private string patientFacility;


        private long billingTier;
        private int pageCount;
        private string subtitle;

        private long billingTierStatus;
        private bool isPatientFreeFormFacility;

        #endregion

        #region Constructors

        public RequestNonHpfDocumentDetails()
        {
            selectedForRelease = true;
        }

        public RequestNonHpfDocumentDetails(NonHpfDocumentDetails nonHpfDocument)
        {
            
            Id              = nonHpfDocument.Id;
            docType         = nonHpfDocument.Name;
            encounter       = nonHpfDocument.Encounter;
            facility        = nonHpfDocument.FacilityCode;
            department      = nonHpfDocument.Department;
            dateOfService   = nonHpfDocument.DateOfService;
            comment         = nonHpfDocument.Comment;
            pageCount       = nonHpfDocument.PageCount;
            RecordVersionId = nonHpfDocument.RecordVersionId;
            PatientKey      = nonHpfDocument.PatientKey;
            subtitle        = nonHpfDocument.Subtitle;
            patientID       = nonHpfDocument.PatientId;
            patientMRN      = nonHpfDocument.PatientMrn;
            patientFacility = nonHpfDocument.PatientFacility;
            isPatientFreeFormFacility = nonHpfDocument.IsPatientFreeFormFacility;
            
            selectedForRelease = true;
        }

        #endregion

        #region Methods

        public override bool Equals(object obj)
        {
            return (id == 0) ? object.ReferenceEquals(this, obj) : base.Equals(obj);
        }

        public override int GetHashCode()
        {
            if (id == 0)
            {
                string s = (docType + encounter + dateOfService + facility + department + comment);
                return s.GetHashCode();
            }
            return base.GetHashCode();
        }

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            encounter  = encounter.Trim();
            docType    = docType.Trim();
            department = department.Trim();
            facility   = facility.Trim();
            comment    = comment.Trim();
            subtitle   = subtitle.Trim();
        }
        
        #endregion

        #region Properties

        /// <summary>
        /// Holds id of Non-HPF document.
        /// </summary>
        public long Id
        {
            get { return id; }
            set { id = value; }
        }

		/// <summary>
        /// Holds documentSeq
        /// </summary>
        public long DocumentSeq
        {
            get { return documentSeq; }
            set { documentSeq = value; }
        }
        
		/// <summary>
        /// Holds supplementalID
        /// </summary>
        public long SupplementalID
        {
            get { return supplementalID; }
            set { supplementalID = value; }
        }
		
		/// <summary>
        /// Holds patientSeq
        /// </summary>
        public long PatientSeq
        {
            get { return patientSeq; }
            set { patientSeq = value; }
        }
		
		/// <summary>
        /// Holds isHPF
        /// </summary>
        public bool IsHPF
        {
            get { return isHPF; }
            set { isHPF = value; }
        }

        /// <summary>
        /// Holds DocType of Non-HPF document.
        /// </summary>
        public string DocType
        {
            get { return docType; }
            set { docType = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds encounter name of Non-HPF document.
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = (value == null) ? string.Empty : value; ; }
        }

        /// <summary>
        /// Holds data of service of Non-HPF document.
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

        /// <summary>
        /// /// <summary>
        /// Holds facility of Non-HPF document.
        /// </summary>
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = (value == null) ? string.Empty : value; }
        }

        public string PatientKey
        {
            get { return patientKey; }
            set { patientKey = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds department of Non-HPF document.
        /// </summary>
        public string Department
        {
            get { return department; }
            set { department = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds comment of Non-HPF document.
        /// </summary>
        public string Comment
        {
            get { return comment; }
            set { comment = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds SequenceNumber of a page.
        /// </summary>
        public long SequenceNumber
        {
            get { return sequenceNumber; }
            set { sequenceNumber = value; }
        }

        /// <summary>
        /// 
        /// </summary>
        public override Nullable<bool> SelectedForRelease
        {
            get { return selectedForRelease; }
            set { selectedForRelease = (value.Value) ? true : false; }
        }

        /// <summary>
        /// Holds created user of Non-HPF document.
        /// </summary>
        public int CreatedBy
        {
            get { return createdBy; }
            set { createdBy = value; }
        }

        /// <summary>
        /// Gets or Sets the billing tier id of Non-HPF document.
        /// </summary>
        public long BillingTier
        {
            get { return billingTier; }
            set { billingTier = value; }
        }

        /// <summary>
        /// Gets or Sets the page count of Non-HPF document.
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Gets or Sets the subtitle of Non-HPF document.
        /// </summary>
        public string Subtitle
        {
            get { return subtitle; }
            set { subtitle = (value == null) ? string.Empty : value; ; }
        }

        /// <summary>
        /// Holds name of Non-HPF document.
        /// </summary>
        public override string Name
        {
            get { return DocType; }
        }

        /// <summary>
        /// Holds unique key representaion of Non-HPF document.
        /// </summary>
        public override string Key
        {
            //get { return dateOfService.Value.ToString(ROIConstants.SortDateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
            //get { return docType + ROIConstants.Delimiter+ id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
            //CR#376,924 - Total pages in DSR tree shows wrongly
            get { return id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }


        /// <summary>
        /// Gets the Icon
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get { return ROIImages.DocumentIcon; }
        }

		/// <summary>
        /// Holds patientID
        /// </summary>
        public long PatientID
        {
            get { return patientID; }
            set { patientID = value; }
        }
		
		/// <summary>
        /// Holds patientMRN
        /// </summary>
        public string PatientMRN
        {
            get { return patientMRN; }
            set { patientMRN = value; }
        }

		/// <summary>
        /// Holds patientFacility
        /// </summary>
        public string PatientFacility
        {
            get { return patientFacility; }
            set { patientFacility = value; }
        }
		/// <summary>
        /// Holds BillingTierStatus
        /// </summary>
        public long BillingTierStatus
        {
            get { return billingTierStatus; }
            set { billingTierStatus = value; }
        }

        public bool IsPatientFreeFormFacility
        {
            get { return isPatientFreeFormFacility; }
            set { isPatientFreeFormFacility = value; }
        }

        #endregion
    }
}
