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
    public class RequestAttachmentDetails : BaseRequestItem
    {
        #region Fields
       
        private const string DateTime24hrFormat = "MM/dd/yyyy HH:mm:ss";     

        private long id;
        private string patientKey;
        private long patientSeq;
        private bool isHPF;
        private long supplementalID;
        private long attachmentSeq;
        private long sequenceNumber;
        private string attachmentType;
        private string fileId;
        private string fileExt;
        private string fileName;
        private string fileType;
        private string encounter;
        private Nullable<DateTime> dateOfService;
        private Nullable<DateTime> fileAttachDate;
        private Nullable<DateTime> dateReceived;
        private string facility;
        private string comment;
        private bool? selectedForRelease;
        private bool isPrintable;
        private int createdBy;

        private long patientID;
        private string patientMRN;
        private string patientFacility;

        private long billingTier;
        private int pageCount;
        private string subtitle;
        private long billingTierStatus;
        private string uuid;
        private string volume;
        private string path;
        private bool isPatientFreeFormFacility;
        private string externalSource;
        #endregion

        #region Constructors

        public RequestAttachmentDetails()
        {
            selectedForRelease = true;
        }

        public RequestAttachmentDetails(AttachmentDetails tmpAttachment)
        {

            Id = tmpAttachment.Id;
            attachmentType = tmpAttachment.AttachmentType;
            fileId = tmpAttachment.FileDetails.Uuid;
            fileName = tmpAttachment.FileDetails.FileName;
            fileType = tmpAttachment.FileDetails.FileType;
            isPrintable = tmpAttachment.FileDetails.Printable;
            fileExt = tmpAttachment.FileDetails.Extension;
            if (tmpAttachment.FileDetails.DateReceived != null)
                dateReceived = tmpAttachment.FileDetails.DateReceived;
            encounter = tmpAttachment.Encounter;
            facility = tmpAttachment.FacilityCode;
            dateOfService = tmpAttachment.DateOfService;
            fileAttachDate  = tmpAttachment.AttachmentDate;
            comment = tmpAttachment.Comment;
            pageCount = tmpAttachment.PageCount;
            RecordVersionId = tmpAttachment.RecordVersionId;
            PatientKey = tmpAttachment.PatientKey;
            subtitle = tmpAttachment.Subtitle;
            patientID = tmpAttachment.PatientId;
            patientMRN = tmpAttachment.PatientMrn;
            patientFacility = tmpAttachment.PatientFacility;
            uuid = tmpAttachment.FileDetails.Uuid;
            volume = tmpAttachment.Volume;
            path = tmpAttachment.Path;
            isPatientFreeFormFacility = tmpAttachment.IsPatientFreeFormFacility;
            ExternalSource = tmpAttachment.DocumentSource;
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
                string s = (attachmentType + encounter + dateOfService + facility + comment);
                return s.GetHashCode();
            }
            return base.GetHashCode();
        }

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            encounter = encounter.Trim();
            attachmentType = attachmentType.Trim();
            facility = facility.Trim();
            comment = comment.Trim();
            subtitle = subtitle.Trim();
            fileName = fileName.Trim();
            fileExt = fileExt.Trim();
            fileType = fileType.Trim();
        }
        
        #endregion

        #region Properties
        public string ExternalSource
        {
            get { return externalSource; }
            set { externalSource = value; }
        }

        /// <summary>
        /// Holds id 
        /// </summary>
        public long Id
        {
            get { return id; }
            set { id = value; }
        }
	
		/// <summary>
        /// Holds Patient Seq
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
        /// Holds supplemtalID
        /// </summary>
        public long SupplementalID
        {
            get { return supplementalID; }
            set { supplementalID = value; }
        }
	
		/// <summary>
        /// Holds attachmentSeq
        /// </summary>
        public long AttachmentSeq
        {
            get { return attachmentSeq; }
            set { attachmentSeq = value; }
        }

        /// <summary>
        /// Holds file id 
        /// </summary>
        public string FileId
        {
            get { return fileId; }
            set { fileId = value; }
        }

        /// <summary>
        /// Holds file name 
        /// </summary>
        public string FileName
        {
            get { return fileName; }
            set { fileName = value; }
        }


        /// <summary>
        /// Holds file type 
        /// </summary>
        public string FileType
        {
            get { return fileType; }
            set { fileType = value; }
        }

        /// <summary>
        /// Holds file extension 
        /// </summary>
        public string FileExt
        {
            get { return fileExt; }
            set { fileExt = value; }
        }


        /// <summary>
        /// Holds file is printable
        /// </summary>
        public bool IsPrintable
        {
            get { return isPrintable; }
            set { isPrintable = value; }
        }

        /// <summary>
        /// Holds AttachmentType 
        /// </summary>
        public string AttachmentType
        {
            get { return attachmentType; }
            set { attachmentType = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds encounter name 
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = (value == null) ? string.Empty : value; ; }
        }

        /// <summary>
        /// Holds data of service 
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

        /// <summary>
        /// Holds data of service 
        /// </summary>
        public Nullable<DateTime> DateReceived
        {
            get { return dateReceived; }
            set { dateReceived = value; }
        }

        /// <summary>
        /// Holds file attachment date
        /// </summary>
        public Nullable<DateTime> FileAttachDate
        {
            get { return fileAttachDate; }
            set { fileAttachDate = value; }
        }

        /// <summary>
        /// /// <summary>
        /// Holds facility 
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
        /// Holds comment 
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
        /// Holds created user 
        /// </summary>
        public int CreatedBy
        {
            get { return createdBy; }
            set { createdBy = value; }
        }

        /// <summary>
        /// Gets or Sets the billing tier id 
        /// </summary>
        public long BillingTier
        {
            get { return billingTier; }
            set { billingTier = value; }
        }

        /// <summary>
        /// Gets or Sets the page count 
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Gets or Sets the subtitle 
        /// </summary>
        public string Subtitle
        {
            get { return subtitle; }
            set { subtitle = (value == null) ? string.Empty : value; ; }
        }

        /// <summary>
        /// Holds name 
        /// </summary>
        public override string Name
        {
            get 
            {
                string attachmentNamewithExt = "";
                if (!string.IsNullOrEmpty(FileType))
                {
                   attachmentNamewithExt = dateReceived == null ? FileType.ToUpper() + " - " + FileExt : FileType.ToUpper() + " - " + dateReceived.Value.ToString(DateTime24hrFormat) + " - " + FileExt;
                }
                else
                {
                   attachmentNamewithExt = dateReceived == null ? AttachmentType.ToUpper() + " - " + FileExt : AttachmentType.ToUpper() + " - " + dateReceived.Value.ToString(DateTime24hrFormat) + " - " + FileExt;
                }
                return attachmentNamewithExt; 
            }
        }

        /// <summary>
        /// Holds unique key representaion 
        /// </summary>
        public override string Key
        {
            get { return attachmentType + ROIConstants.Delimiter + id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }


        /// <summary>
        /// Gets the Icon
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get { return ROIImages.DocumentIcon; }
        }
	
		/// <summary>
        /// Holds Patientid 
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

        public string Uuid
        {
            get { return uuid; }
            set { uuid = value; }
        }

        public string Volume
        {
            get { return volume; }
            set { volume = value; }
        }

        public string Path
        {
            get { return path; }
            set { path = value; }
        }

        public bool IsPatientFreeFormFacility
        {
            get { return isPatientFreeFormFacility; }
            set { isPatientFreeFormFacility = value; }
        }

        
        #endregion
    }
}
