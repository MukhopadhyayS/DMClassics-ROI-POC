#region Copyright © 2008 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/*
 * Use of this material is governed by a license agreement. 
 * This material contains confidential, proprietary and trade 
 * secret information of McKesson Information Solutions and is 
 * protected under United States and international copyright and 
 * other intellectual property laws. Use, disclosure, 
 * reproduction, modification, distribution, or storage in a 
 * retrieval system in any form or by any means is prohibited 
 * without the prior express written permission of McKesson 
 * Information Solutions.
 */
#endregion

using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Patient.Model
{
    /// <summary>
    /// Mode
    /// </summary>
    public enum AttachmentMode
    {
        None,
        Create,
        Edit,
        Delete
    }


    /// <summary>
    /// Class that maintains Non-HPF attachment related information
    /// 
    /// </summary>
    [Serializable]
    public class AttachmentDetails : BaseRecordItem
    {
        [Serializable]
        private class AttachmentSorter : IComparer<string>
        {
            public virtual int Compare(string x, string y)
            {
                if ((x.Contains(ROIConstants.Delimiter) && y.Contains(ROIConstants.Delimiter)))
                {
                    long id1 = Convert.ToInt64(x.Substring(x.IndexOf(ROIConstants.Delimiter) + ROIConstants.Delimiter.Length));
                    long id2 = Convert.ToInt64(y.Substring(y.IndexOf(ROIConstants.Delimiter) + ROIConstants.Delimiter.Length));
                    return (id2.CompareTo(id1));
                }
                else
                return x.CompareTo(y);
            }
        }

        #region Fields

        public const string IdKey                  = "id";
        private const string AttachmentTypeKey     = "attachment-type";
        private const string EncounterKey          = "encounter";
        private const string FacilityKey           = "facility";
        private const string FacilityTypeKey       = "is-freeformfacility";
        private const string CommentKey            = "comment";
        private const string DateOfServiceKey = "date-of-service";
        private const string AttachmentDateKey = "attachment-date";
        private const string SubtitleKey = "subtitle";
        private const string PageCountKey          = "page-count";
        private const string DeletedKey = "is-deleted";
        private const string DateTime24hrFormat = "MM/dd/yyyy HH:mm:ss";

        private const string AttachmentElement = "<{0} {1}=\"{2}\">";
        private const string AttachmentTypeDetailElement = "<{0}>";

        private long id;
        private long attachmentSeq;
        private long patientId;
        private string patientFacility;
        private string patientMrn;
        private string patientKey;

        private string attachmentType;
        private string encounter;
        private Nullable<DateTime> dateOfService;
        private Nullable<DateTime> attachmentDate;
        private string facilityCode;
        private FacilityType facilityType;
        private bool isFreeformFacility;
        private bool isPatientFreeFormFacility;
        private string comment;

        private int pageCount;
        private string subtitle;
        private AttachmentFileDetails fileDetails;
        private Nullable<DateTime> dateReceived;
        private string volume;
        private string documentSource;
        private string path;

        /// <summary>
        /// Holds list of attachment detail key/values
        /// </summary>
        private Dictionary<string, string> attachmentTypeDetails;


        private AttachmentMode mode;

        private bool isReleased;
        private bool isDeleted;

        #endregion

        #region Constructor

        #endregion

        #region Methods

        public AttachmentDetails()
        {
            //dateOfService = DateTime.Today;
            fileDetails = new AttachmentFileDetails();
            attachmentTypeDetails = new Dictionary<string, string>();
            IsDeleted = false;
        }

        public override bool Equals(object obj)
        {
            return (id == 0) ? object.ReferenceEquals(this, obj) : base.Equals(obj);
        }

        public override int GetHashCode()
        {
            if (id == 0)
            {
                string s = (attachmentType + encounter + dateOfService + facilityCode + comment);
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
            attachmentType = attachmentType.Trim();
            facilityCode = facilityCode.Trim();
            comment = comment.Trim();
            subtitle   = subtitle.Trim();
            fileDetails.Normalize();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the Id of an Attachment
        /// </summary>
        public long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// Gets or sets the Id of an Attachment
        /// </summary>
        public long AttachmentSeq
        {
            get { return attachmentSeq; }
            set { attachmentSeq = value; }
        }

        /// <summary>
        /// Gets or sets the Attachment Type of Attachment
        /// </summary>
        public string AttachmentType
        {
            get { return attachmentType; }
            set { attachmentType = value; }
        }

        /// <summary>
        /// Gets or sets the encounter value of an Attachment
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

        /// <summary>
        /// Gets or sets the DateOfService value
        /// </summary>
        public Nullable<DateTime> DateOfService
        {
            get { return dateOfService; }
            set { dateOfService = value; }
        }

        /// <summary>
        /// Gets or sets the attachment date value
        /// </summary>
        public Nullable<DateTime> AttachmentDate
        {
            get { return attachmentDate; }
            set { attachmentDate = value; }
        }

        /// <summary>
        /// Gets or sets the facility 
        /// </summary>
        public string FacilityCode
        {
            get { return facilityCode; }
            set { facilityCode = value; }
        }

        /// <summary>
        /// Gets or sets the facility type 
        /// </summary>
        public FacilityType FacilityType
        {
            get { return facilityType; }
            set { facilityType = value; }
        }


        public bool IsFreeformFacility
        {
            get
            {
                isFreeformFacility = facilityType == McK.EIG.ROI.Client.Base.Model.FacilityType.NonHpf ? true : false;
                return isFreeformFacility;
            }
            set
            {
                facilityType = value ? McK.EIG.ROI.Client.Base.Model.FacilityType.NonHpf : McK.EIG.ROI.Client.Base.Model.FacilityType.Hpf;
                isFreeformFacility = value;
            }
        }

        /// <summary>
        /// Gets or sets the comment 
        /// </summary>
        public string Comment
        {
            get { return comment; }
            set { comment = value; }
        }

        public long PatientId
        {
            get { return patientId; }
            set { patientId = value; }
        }

        public string PatientFacility
        {
            get { return patientFacility; }
            set { patientFacility = value; }
        }

        public bool IsPatientFreeFormFacility
        {
            get { return isPatientFreeFormFacility; }
            set { isPatientFreeFormFacility = value; }
        }

        public string PatientMrn
        {
            get { return patientMrn; }
            set { patientMrn = value; }
        }

        public string PatientKey
        {
            get { return patientKey; }
            set { patientKey = value; }
        }

        /// <summary>
        /// Gets or sets the Page Count 
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Gets or sets the Subtitle 
        /// </summary>
        public string Subtitle
        {
            get { return subtitle; }
            set { subtitle = value ?? string.Empty; }
        }

        public AttachmentMode Mode
        {
            get { return mode; }
            set { mode = value; }
        }

        public bool IsReleased
        {
            get { return isReleased; }
            set { isReleased = value; }
        }

        public bool IsDeleted
        {
            get { return isDeleted; }
            set { isDeleted = value; }
        }

        public AttachmentFileDetails FileDetails
        {
            get { return fileDetails; }
            set { fileDetails = value; }
        }


        #endregion

        public override string Name
        {
            get 
            {
                string attachmentNamewithExt = "";
                if (!string.IsNullOrEmpty(FileDetails.FileType))
                {
                    attachmentNamewithExt = FileDetails.DateReceived == null ? FileDetails.FileType.ToUpper() + " - " + FileDetails.Extension : FileDetails.FileType.ToUpper() + " - " + FileDetails.DateReceived.Value.ToString(DateTime24hrFormat) + " - " + FileDetails.Extension;
                }
                else
                {
                    attachmentNamewithExt = FileDetails.DateReceived == null ? AttachmentType.ToUpper() + " - " + FileDetails.Extension : AttachmentType.ToUpper() + " - " + FileDetails.DateReceived.Value.ToString(DateTime24hrFormat) + " - " + FileDetails.Extension;
                }
                return attachmentNamewithExt; 
            }
        }

        public override string Key
        {
            get { return attachmentType + ROIConstants.Delimiter + id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }

        private static IComparer<string> sorter;
        public new static IComparer<string> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new AttachmentSorter();
                }
                return sorter;
            }
        }

        public override IComparable CompareProperty
        {
            //get { return dateOfService.Value.ToShortDateString(); }
            get { return attachmentType; }
        }

        public override System.Drawing.Image Icon
        {
            get { return ROIImages.DocumentIcon; }
        }


        // <summary>
        // Add non empty attachmentType detail entry
        // contains key/value for detail form:  ie. additional ccr/ccd details, etc.
        // </summary>
        // <param name="freeForm"></param>
        public void AddAttachmentTypeDetail(string key, string value)
        {
            key = key.Trim();
            value = value.Trim();
            if (key == null || value == null)
            {
                return;
            }

            if (attachmentTypeDetails.ContainsKey(key))
            {
                attachmentTypeDetails[key] = value;
            }
            else
            {
                attachmentTypeDetails.Add(key.Trim(), value.Trim());
            }
        }

        public string GetAttachmentTypeDetail(string key) 
        {
            string value = string.Empty;

            if (attachmentTypeDetails.TryGetValue(key, out value) ) 
            {
                return value;
            }

            return string.Empty;
        }

        public string Volume
        {
            get { return volume; }
            set { volume = value; }
        }

        public string DocumentSource
        {
            get { return documentSource; }
            set { documentSource = value; }
        }
        public string Path
        {
            get { return path; }
            set { path = value; }
        }
    }
}
