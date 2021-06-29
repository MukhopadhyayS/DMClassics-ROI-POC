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
    public enum Mode
    {
        None,
        Create,
        Edit,
        Delete
    }


    /// <summary>
    /// Class that maintains Non-HPF document related information
    /// 
    /// CR#285,853 – Enhance non-HPF document support.
    /// </summary>
    [Serializable]
    public class NonHpfDocumentDetails : BaseRecordItem
    {
        [Serializable]
        private class NonHpfDocumentSorter : IComparer<string>
        {
            public virtual int Compare(string x, string y)
            {
                return x.CompareTo(y);
            }
        }

        #region Fields

        public const string IdKey                  = "id";
        private const string NameKey               = "name";
        private const string EncounterKey          = "encounter";
        private const string FacilityKey           = "facility";
        private const string FacilityTypeKey       = "is-freeformfacility";
        private const string DepartmentKey         = "department";
        private const string CommentKey            = "comment";
        private const string DateOfServiceKey      = "date-of-service";
        private const string SubtitleKey           = "subtitle";
        private const string PageCountKey          = "page-count";
      
        private const string NonHpfDocumentElement = "<{0} {1}=\"{2}\">";

        private long id;
        private long documentSeq;
        private long patientId;
        private string patientFacility;
        private string patientMrn;
        private string patientKey;

        private string docType;
        private string encounter;
        private Nullable<DateTime> dateOfService;
        private string facilityCode;
        private FacilityType facilityType;
        private bool isFreeformFacility;
        private bool isPatientFreeFormFacility;
        private string department;
        private string comment;

        private int pageCount;
        private string subtitle;

        private Mode mode;

        private bool isReleased;

        #endregion

        #region Constructor

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
                string s = (docType + encounter + dateOfService + facilityCode + department + comment);
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
            facilityCode   = facilityCode.Trim();
            comment    = comment.Trim();
            subtitle   = subtitle.Trim();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the Id of Non-HPF document
        /// </summary>
        public long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// Gets or sets the Id of Non-HPF document
        /// </summary>
        public long DocumentSeq
        {
            get { return documentSeq; }
            set { documentSeq = value; }
        }

        /// <summary>
        /// Gets or sets the Document Type of Non-HPF document
        /// </summary>
        public string DocType
        {
            get { return docType; }
            set { docType = value; }
        }

        /// <summary>
        /// Gets or sets the encounter value of Non-HPF document
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
        /// Gets or sets the facility of Non-HPF document
        /// </summary>
        public string FacilityCode
        {
            get { return facilityCode; }
            set { facilityCode = value; }
        }

        /// <summary>
        /// Gets or sets the facility type of Non-HPF document
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
        /// Gets or sets the department of Non-HPF document
        /// </summary>
        public string Department
        {
            get { return department; }
            set { department = value; }
        }

        /// <summary>
        /// Gets or sets the department of Non-HPF document
        /// </summary>
        public bool IsPatientFreeFormFacility
        {
            get { return isPatientFreeFormFacility; }
            set { isPatientFreeFormFacility = value; }
        }

        /// <summary>
        /// Gets or sets the comment of Non-HPF document
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
        /// Gets or sets the Page Count of Non-HPF document
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Gets or sets the Subtitle of Non-HPF document
        /// </summary>
        public string Subtitle
        {
            get { return subtitle; }
            set { subtitle = value ?? string.Empty; }
        }

        public Mode Mode
        {
            get { return mode; }
            set { mode = value; }
        }

        public bool IsReleased
        {
            get { return isReleased; }
            set { isReleased = value; }
        }

        #endregion

        public override string Name
        {
            get { return docType; }
        }

        public override string Key
        {
            //get { return docType + ROIConstants.Delimiter + id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
            get { return id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }

        private static IComparer<string> sorter;
        public new static IComparer<string> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new NonHpfDocumentSorter();
                }
                return sorter;
            }
        }

        public override IComparable CompareProperty
        {
            get { return dateOfService.Value.ToShortDateString(); }
        }

        public override System.Drawing.Image Icon
        {
            get { return ROIImages.DocumentIcon; }
        }
    }
}
