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
using System.Drawing;
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Patient.Model
{   
    [Serializable]
    public class PatientDetails : BaseRecordItem
    {
        private class PatientDetailsSorter : IComparer<PatientDetails>
        {
            #region IComparer<PatientDetails> Members

            public int Compare(PatientDetails x, PatientDetails y)
            {
                string custField1 = x.name + x.Key;
                string custField2 = y.name + y.Key;
                return custField1.CompareTo(custField2);
            }

            #endregion
        }

        private static PatientDetailsSorter sorter;

        public static IComparer<PatientDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new PatientDetailsSorter();
                }
                return sorter;
            }
        }

        private static FieldMatchComparer fieldMatchComparer;

        public static IComparer<PatientDetails> FieldMatchComparer
        {
            get
            {
                if (fieldMatchComparer == null)
                {
                    fieldMatchComparer = new FieldMatchComparer();
                }
                return fieldMatchComparer;
            }
        }

        #region Fields

        public const string IdKey        = "id";
        public const string NameKey      = "name";
        public const string MRNKey       = "mrn";
        public const string GenderKey    = "gender";
        public const string EPNKey       = "epn";
        public const string SSNKey       = "ssn";
        public const string DOBKey       = "dob";
        public const string FacilityKey  = "facility";
        public const string FacilityTypeKey = "is-freeformfacility";
        public const string FreeformFacilityKey = "freeformfacility";
        public const string FirstNameKey = "firstname";
        public const string LastNameKey  = "lastname";
        public const string IsVipKey     = "vip";
        public const string EncounterKey = "encounter";

        private const string NonHpfDocumentIdKey = "nonhpf-document-recent-seq";
        private const string AttachmentIdKey = "attachment-recent-seq";

        private const string HomePhoneKey = "home-phone";
        private const string WorkPhoneKey = "work-phone";
        private const string AddressKey    = "address";
        private const string Address1Key   = "address1";
        private const string Address2Key   = "address2";
        private const string Address3Key   = "address3";
        private const string CityKey       = "city";
        private const string StateKey      = "state";
        private const string PostalCodeKey = "postalcode";

        private const string NonHpfPatientElement = "<{0} {1}=\"{2}\" {3}=\"{4}\">";
        private const string HPFPatientElement    = "<{0} {1}=\"{2}\" {3}=\"{4}\">";
        
        
        //holds the id
        public long id;

        //holds the name
        public string name;

        //holds the firstName
        public string firstName;

        //holds the last name
        public string lastName;

        //holds the home phone
        public string homePhone;

        //holds the work phone
        public string workPhone;

        //holds the VIP value
        public bool isVip;

        //holds the gender 
        //public Gender gender;
        //SOGI OC-111171
        private string genderDesc;
        //private GenderDetails genderValue;
        private string genderCode;
        //holds the SSN
        public string ssn;

        //holds the EPN
        public string epn;

        //holds the Facility
        public string facilityCode;

        //holds the FacilityType
        public FacilityType facilityType;

        public bool isFreeformFacility;

        //holds the Mrn
        public string mrn;

        //holds the DOB
        public Nullable<DateTime> dob;

        //holds the patientDob upto validator
        public string patientDob;

        //holds the HPF value
        public bool isHpf;

        //holds the requests value
        public bool hasRequests;

        //holds the Patient Locked status
        public bool patientLocked;

        //holds the Encounter Locked status
        public bool encounterLocked;

        public AddressDetails address;

        //Image to display in grid
        private Image lockedImage;

        //Image to display in grid
        private Image vipImage;

        //holds the Formatted Date Formate
        private Nullable<DateTime> formattedDateTime;

        private PatientGlobalDocument globalDoc;
        
        private Collection<EncounterDetails> encounters;
        
        private PatientNonHpfDocument nonHpfDoc;

        private long nonHpfDocumentRecentSeq;

        private PatientAttachment attachment;

        private long attachmentRecentSeq;

        private string auditMessage;

        #endregion

        #region Constructor

        public PatientDetails()
        {
            globalDoc = new PatientGlobalDocument();
            nonHpfDoc = new PatientNonHpfDocument(this);
            attachment = new PatientAttachment(this);
        }

        #endregion

        #region Methods

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            //name = string.IsNullOrEmpty(name) ? string.Empty : name.Trim();
            firstName = string.IsNullOrEmpty(firstName) ? string.Empty : firstName.Trim();
            lastName = string.IsNullOrEmpty(lastName) ? string.Empty : lastName.Trim();
            ssn  = string.IsNullOrEmpty(ssn) ? string.Empty : ssn.Trim();
            epn  = string.IsNullOrEmpty(epn) ? string.Empty : epn.Trim();
            mrn  = string.IsNullOrEmpty(mrn) ? string.Empty : mrn.Trim();
            homePhone = string.IsNullOrEmpty(homePhone) ? string.Empty : homePhone.Trim();
            workPhone = string.IsNullOrEmpty(workPhone) ? string.Empty : workPhone.Trim();
           
            if (address != null)
            {
                address.Normalize();
            }

            foreach (NonHpfEncounterDetails encounter in NonHpfDocuments.GetChildren)
            {
                foreach (NonHpfDocumentDetails document in encounter.GetChildren)
                {
                    document.Normalize();
                }
            }
            foreach (AttachmentEncounterDetails encounter in Attachments.GetChildren)
            {
                foreach (AttachmentDetails attachment in encounter.GetChildren)
                {
                    attachment.Normalize();
                }
            }
        }

        public override bool Equals(object obj)
        {
            if (object.ReferenceEquals(this, obj)) return true;

            return (this.GetType() == obj.GetType()) &&
                   (Key == ((PatientDetails)obj).Key);
        }

        public override int GetHashCode()
        {
            return this.GetType().GetHashCode() + Key.GetHashCode();
        }

        private void CreatePatientAddress(StringBuilder builder)
        {
            if (address == null) return;
            
            builder.Append("<").Append(AddressKey).Append(">").
                    Append(CreateElement(Address1Key, address.Address1)).
                    Append(CreateElement(Address2Key, address.Address2)).
                    Append(CreateElement(Address3Key, address.Address3)).
                    Append(CreateElement(CityKey, address.City)).
                    Append(CreateElement(StateKey, address.State)).
                    Append(CreateElement(PostalCodeKey, address.PostalCode)).
                    Append("</").Append(AddressKey).Append(">");
        }
        
        /// <summary>
        /// Parse patient's address details
        /// </summary>
        /// <param name="addressNode"></param>
        private void ParsePatientAddress(XmlElement addressNode)
        {
            if (addressNode == null) return;

            address = new AddressDetails();
            address.Address1   = GetNodeValue(addressNode, Address1Key);
            address.Address2   = GetNodeValue(addressNode, Address2Key);
            address.Address3   = GetNodeValue(addressNode, Address3Key);
            address.City       = GetNodeValue(addressNode, CityKey);
            address.State      = GetNodeValue(addressNode, StateKey);
            address.PostalCode = GetNodeValue(addressNode, PostalCodeKey);
        }

        /// <summary>
        /// Prepares create audit message for supplemental
        /// </summary>
        /// <param name="item"></param>
        /// <returns></returns>
        public static string PrepareAuditMessageForCreateSupplemental(BaseRecordItem item)
        {
            PatientDetails patient = (PatientDetails)item;
            StringBuilder sb = new StringBuilder();
            return sb.Append("ROI user ").Append(UserData.Instance.UserId).
                      Append(" created a new non-HPF patient ").Append(patient.Name).ToString();
        }

        /// <summary>
        /// Prepares update audit message for supplemental 
        /// </summary>
        /// <param name="newPatient"></param>
        /// <param name="oldPatient"></param>
        /// <returns></returns>
        public static string PrepareAuditMessageForUpdateSupplemental(PatientDetails newPatient, PatientDetails oldPatient)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(UserData.Instance.UserId).Append(" modified the patient's information ").
               Append(oldPatient.Name).Append(", ");
            if (oldPatient.Address != null)
            {
                sb.Append(oldPatient.Address.ToString()).Append(", ");
            }
            sb.Append(Convert.ToString(oldPatient.IsVip, System.Threading.Thread.CurrentThread.CurrentUICulture)).Append(" to ").
               Append(newPatient.Name).Append(", ");

            if (newPatient.Address != null)
            {
                sb.Append(newPatient.Address.ToString()).Append(", ");
            }
            sb.Append(Convert.ToString(newPatient.IsVip, System.Threading.Thread.CurrentThread.CurrentUICulture));

            return sb.ToString();
        }

        /// <summary>
        /// Prepares create audit message for supplemental
        /// </summary>
        /// <param name="item"></param>
        /// <returns></returns>
        public static string PrepareAuditMessageForDeleteSupplemental(BaseRecordItem item)
        {
            PatientDetails patient = (PatientDetails)item;
            StringBuilder sb = new StringBuilder();
            return sb.Append("ROI user ").Append(UserData.Instance.UserId).
                      Append(" deleted a non-HPF patient ").Append(patient.Name).ToString();
        }

        #endregion

        #region Properties
        /// <summary>
        /// This property is used to get or sets the patient id.
        /// </summary>
        public long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient's First Name
        /// </summary>
        public string FirstName
        {
            get { return firstName; }
            set { firstName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient's last Name
        /// </summary>
        public string LastName
        {
            get { return lastName; }
            set { lastName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient's Name
        /// </summary>
        public string FullName
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient home phone
        /// </summary>
        public string HomePhone
        {
            get { return homePhone; }
            set { homePhone = value; }
         }

        /// <summary>
        /// This property is used to get or sets the patient work phone
        /// </summary>
        public string WorkPhone
        {
            get { return workPhone; }
            set { workPhone = value; }
        }

        /// <summary>
        /// This property is used to get or set the request patient gender.
        /// </summary>
        //public Gender Gender
        //{
        //    get { return gender; }
        //    set { gender = value; }
        //} 
        //SOGI OC-111171
        public string GenderDesc
        {
            get { return genderDesc; }
            set { genderDesc = value; }
        }
        public string GenderCode
        {
            get { return genderCode; }
            set { genderCode = value; }
        }


        /// <summary>
        /// This property is used to get or sets the patient ssn
        /// </summary>
        public string SSN
        {
            get { return ssn; }
            set  { ssn = value; }
        }

        /// <summary>
        /// This property is used to get the masked value of patient's ssn 
        /// if ssn is been masked.
        /// </summary>
        public string MaskedSSN
        {
            get 
            { 
                return (UserData.Instance.IsSSNMasked) 
                       ? ROIViewUtility.ApplyMask(ssn) 
                       : ssn ; 
            }
        }

        /// <summary>
        /// This property is used to get or sets the patient epn
        /// </summary>
        public string EPN
        {
            get { return epn; }
            set { epn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient facility
        /// </summary>
        public string FacilityCode
        {
            get { return facilityCode; }
            set { facilityCode = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient facility type.
        /// </summary>
        public FacilityType FacilityType
        {
            get { return facilityType; }
            set {facilityType = value;}
        }

        /// <summary>
        /// his property is used to get or sets the patient facility type is free form or not.
        /// </summary>
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
        /// This property is used to get or sets the patient mrn
        /// </summary>
        public string MRN
        {
            get { return mrn; }
            set { mrn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient VIP status
        /// </summary>
        public bool IsVip
        {
            get { return isVip; }
            set
            {
                isVip = value;
                if (isVip)
                {
                    vipImage = ROIImages.VipIcon;
                }
            }
        }

        /// <summary>
        /// This property is used to get or sets the patient locked status
        /// </summary>
        public bool PatientLocked
        {
            get { return patientLocked; }
            set 
            { 
                patientLocked = value;
                if (patientLocked)
                {
                    lockedImage = ROIImages.LockedPatientIcon;
                }
            }
        }

        /// <summary>
        /// This property is used to get or sets the encounter locked status
        /// </summary>
        public bool EncounterLocked
        {
            get { return encounterLocked; }
            set 
            { 
                encounterLocked = value;
                if (encounterLocked)
                {
                    lockedImage = ROIImages.LockedPatientIcon;
                }
            }
        }

        /// <summary>
        /// This property is used to get or sets the patient has request
        /// </summary>
        public bool HasRequests
        {
            get { return hasRequests; }
            set { hasRequests = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient DOB
        /// </summary>
        public Nullable<DateTime> DOB
        {
            get { return dob; }
            set 
            { 
                dob = value;
                formattedDateTime = dob;
            }
        }

        public string PatientDob
        {
            get { return patientDob; }
            set { patientDob = value; }

        }

        /// <summary>
        /// This property is used to get or sets the patient hpf status
        /// </summary>
        public bool IsHpf
        {
            get { return isHpf; }
            set { isHpf = value; }
        }

        public AddressDetails Address
        {
            get { return address; }
            set { address = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient locked status.
        /// </summary>
        public Image LockedImage
        {
            get { return lockedImage; }
        }

        /// <summary>
        /// This property is used to get or sets the patient vip status.
        /// </summary>
        public Image VipImage
        {
            get { return vipImage; }
        }

        /// <summary>
        /// This property is used to get or sets the patient DOB
        /// </summary>
        public string FormattedDOB
        {
            get
            {
                if (formattedDateTime.HasValue)
                {
                    return formattedDateTime.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                return null;
            }
        }

        public PatientGlobalDocument GlobalDocument
        {
            get { return globalDoc; }
            set { globalDoc = value; }
        }


        public Collection<EncounterDetails> Encounters
        {
            get
            {
                if (encounters == null)
                {
                    encounters = new Collection<EncounterDetails>();
                }
                return encounters;
            }
        }

        public PatientNonHpfDocument NonHpfDocuments
        {
            get { return nonHpfDoc; }
            set { nonHpfDoc = value; }
        }

        public long NonHpfDocumentRecentSeq
        {
            get { return nonHpfDocumentRecentSeq; }
            set { nonHpfDocumentRecentSeq = value; }
        }

        public PatientAttachment Attachments
        {
            get { return attachment; }
            set { attachment = value; }
        }

        public long AttachmentRecentSeq
        {
            get { return attachmentRecentSeq; }
            set { attachmentRecentSeq = value; }
        }



        /// <summary>
        /// Gets or sets the message for auditing
        /// </summary>
        public string AuditMessage
        {
            get { return auditMessage; }
            set { auditMessage = value; }
        }

        public override string Key
        {
            get 
            {
                if (IsHpf)
                {
                    //return Convert.ToString(id, System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + mrn + "." + facility;
                    return mrn + "." + facilityCode;
                }
                return Convert.ToString(id, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
        }

        public override string Name
        {
            get { return name; }
        }

        public override IComparable CompareProperty
        {
            get { return null; }
        }

        private NonHpfDocumentDetails modifiedNonHpfDocument;
        public NonHpfDocumentDetails ModifiedNonHpfDocument
        {
            get { return modifiedNonHpfDocument; }
            set { modifiedNonHpfDocument = value; }
        }

        private AttachmentDetails modifiedAttachment;
        public AttachmentDetails ModifiedAttachment
        {
            get { return modifiedAttachment; }
            set { modifiedAttachment = value; }
        }

        #endregion
    }
}
