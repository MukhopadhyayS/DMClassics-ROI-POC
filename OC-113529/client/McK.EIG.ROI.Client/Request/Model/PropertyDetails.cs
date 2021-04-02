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
    /// Model contains the Property Details
    /// </summary>
    [Serializable]
    public class PropertyDetails
    {
        #region Fields

        //public const string PatientNameKey = "p";
        //public const string MrnKey = "m";
        //public const string FacilityKey = "f";
        //public const string EncounterKey = "e";
        //public const string DocumentNameKey = "d";
        //public const string IMNetKey = "i";
        //public const string PageNumberKey = "n";
        //public const string PageCountKey = "pgCnt";

        //public const string FileNamesKey    = "FILE_NAMES";
        public const string FileIdsSecure      = "FILE_IDS";
        public const string FileTypesSecure = "FILE_TYPES";        
        //public const string DocSubtitleKey = "ds";
        //public const string DocTypeKey = "docType";
        //public const string ContentTypeKey = "CONTENT_TYPE";
        //public const string AttachDateKey = "a_dt";
        //public const string FileExtsKey = "FILE_EXTS";
        public const string RequestorGroupingSecure = "requestor.grouping.key";
        public const string RequestorGroupingEnabled = "requestor.grouping.enabled";
        //CR#365589
        //public const string DocIdKey = "docId";
        //public const string DocDateTimeKey = "ddt";

        //US4865
        public const string RequestIDSecure = "#label.request.id";
        public const string RequestorNameSecure = "#label.requestor.name";
        public const string RequestCreatedSecure = "#label.request.created.date";
        public const string RequestCompletedSecure = "#label.request.fulfilled.date";
        public const string TotalPageCountSecure = "#label.released.doc.count";
        public const string NotesSecure = "#label.released.notes";

        public const string PatientNameSecureForDisc = "#label.patient.name";
        public const string PatientDOBSecure = "#label.patient.dob";
        public const string PatientMRNSecure = "#label.patient.mrn";

        public const string EncounterNumberSecure = "#label.enc.num";
        public const string AdmitDateSecure = "#label.enc.admit.date";
        public const string DischargeDateSecure = "#label.enc.discharge.date";
        public const string PatientTypeSecure = "#label.enc.patient.type";

        public const string EnounterCountSecure = "#label.enc.count";
        public const string PatientCountSecure = "#label.patient.count";

        public const string DiscLabelTemplate = "disc.label.template.name";

        public const string ReleaseNumForDiscSecure = "output.disc.releaseNum";
        public const string ReleaseIDForDiscSecure = "output.disc.requestId";
        public const string RequestSecureForDisc = "output.file.request.password";
        public const string DiscMediaSecure = "output.disc.media";
        public const string DiscQueueValueSecure = "output.disc.q.password";
        public const string IsEncryptedValueSecure = "output.password.encrypted";
        public const string RequestDateForDiscSecure = "output.disc.request.date";

        private string patientName;

        private string mrn;

        private string epn;

        private string facility;

        private string encounter;

        private string documentName;

        private string imNetIds;

        private string pages;

        private string pageCnt;

        private string fileIds;

        private string fileNames;

        private string docSubtitle;

        private string docType;

        private string contentType;

        private string attachDate;

        private string fileExts;

        private string fileTypes;

        private bool isRequestorGrouping;

        private string requestorGrouping;
        //CR#365589
        private string key;
        private Nullable<DateTime> documentDateTime;

        private string dOB;
        private string admitDate;
        private string dischargeDate;
        private string patientType;

        private long requestID;
        private string requestorName;
        private Nullable<DateTime> requestCreated;
        private string requestCompleted;
        private long totalPageCount;
        private string freeFormNotes;
        private string outputNotes;
        private int encounterCount;
        private int patientCount;
        private string pageIds;
       
        #endregion
        
        #region Properties

        /// <summary>
        /// This property is used to get or sets the Request Patient Name.
        /// </summary>
        public string PatientName
        {
            get { return patientName; }
            set { patientName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Patient EPN.
        /// </summary>
        public string EPN
        {
            get { return epn; }
            set { epn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Patient MRN.
        /// </summary>
        public string MRN
        {
            get { return mrn; }
            set { mrn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the facility.
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }

        /// <summary>
        /// This property is used to get or sets encounter.
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

        /// <summary>
        /// This property is used to get or sets the document name.
        /// </summary>
        public string DocumentName
        {
            get { return documentName; }
            set { documentName = value; }
        }

        /// <summary>
        /// Holds imNetIds.
        /// </summary>
        public string IMNetIds
        {
            get { return imNetIds; }
            set { imNetIds = value; }
        }

        /// <summary>
        /// Holds the list of pages.
        /// </summary>
        public string Pages
        {
            get { return pages; }
            set { pages = value; }
        }

        /// <summary>
        /// Holds the number of pages.
        /// </summary>
        public string PageCount
        {
            get { return pageCnt; }
            set { pageCnt = value; }
        }
       
        /// <summary>
        /// This property is used to get or sets the file ids
        /// </summary>
        public string FileIds
        {
            get { return fileIds; }
            set { fileIds = value; }
        }

        /// <summary>
        /// This property is used to get or sets the file names
        /// </summary>
        public string FileNames
        {
            get { return fileNames; }
            set { fileNames = value; }
        }

        /// <summary>
        /// This property is used to get or sets the file extensions
        /// </summary>
        public string FileExts
        {
            get { return fileExts; }
            set { fileExts = value; }
        }

        /// <summary>
        /// This property is used to get or sets the file types
        /// </summary>
        public string FileTypes
        {
            get { return fileTypes; }
            set { fileTypes = value; }
        }
        
        /// <summary>
        /// This property is used to get or sets the file ids
        /// </summary>
        public string DocumentSubtitle
        {
            get { return docSubtitle; }
            set { docSubtitle = value; }
        }

        /// <summary>
        /// This property is used to get or sets the file ids
        /// </summary>
        public string DocumentType
        {
            get { return docType; }
            set { docType = value; }
        }

        /// <summary>
        /// This property is used to get or sets the content type
        ///   this property can be used to define the content type 
        /// </summary>
        public string ContentType
        {
            get { return contentType; }
            set { contentType = value; }
        }

        /// <summary>
        /// This property is used to get or sets the content attach date
        /// </summary>
        public string AttachDate
        {
            get { return attachDate; }
            set { attachDate = value; }
        }

        //CR#365589
        public string Key
        {
            get { return key; }
            set { key = value; }
        }
        
        /// <summary>
        /// CR#365588 - Holds document created DateTime
        /// </summary>
        public Nullable<DateTime> DocumentDateTime
        {
            get { return documentDateTime; }
            set { documentDateTime = value; }
        }


        /// <summary>
        /// This property is used to get or sets the requestor grouping is enabled.
        /// </summary>
        public bool IsRequestorGrouping
        {
            get { return isRequestorGrouping; }
            set { isRequestorGrouping = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor grouping key
        /// </summary>
        public string RequestorGrouping
        {
            get { return requestorGrouping; }
            set { requestorGrouping = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient Date of birth.
        /// </summary>
        public string DOB
        {
            get { return dOB; }
            set { dOB = value; }
        }

        /// <summary>
        /// This property is used to get or sets the admit date.
        /// </summary>
        public string AdmitDate
        {
            get { return admitDate; }
            set { admitDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the discharge date.
        /// </summary>
        public string DischargeDate
        {
            get { return dischargeDate; }
            set { dischargeDate = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient type.
        /// </summary>
        public string PatientType
        {
            get { return patientType; }
            set { patientType = value; }
        }

        /// <summary>
        /// This property is used to get or sets the request ID.
        /// </summary>
        public long RequestID
        {
            get { return requestID; }
            set { requestID = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestor name.
        /// </summary>
        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the request created.
        /// </summary>
        public Nullable<DateTime> RequestCreated
        {
            get { return requestCreated; }
            set { requestCreated = value; }
        }

        /// <summary>
        /// This property is used to get or sets the request completed.
        /// </summary>
        public string RequestCompleted
        {
            get { return requestCompleted; }
            set { requestCompleted = value; }
        }

        /// <summary>
        /// This property is used to get or sets the total page count.
        /// </summary>
        public long TotalPageCount
        {
            get { return totalPageCount; }
            set { totalPageCount = value; }
        }

        /// <summary>
        /// This property is used to get or sets the output notes.
        /// </summary>
        public string OutputNotes
        {
            get { return outputNotes; }
            set { outputNotes = value; }
        }

        /// <summary>
        /// This property is used to get or sets the encounter count.
        /// </summary>
        public int EncounterCount
        {
            get { return encounterCount; }
            set { encounterCount = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient count.
        /// </summary>
        public int PatientCount
        {
            get { return patientCount; }
            set { patientCount = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient count.
        /// </summary>
        public string PageIds
        {
            get { return pageIds; }
            set { pageIds = value; }
        }

        #endregion
    }
}
