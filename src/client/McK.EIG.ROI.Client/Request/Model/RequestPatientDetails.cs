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
using System.Globalization;
using System.Collections.ObjectModel;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Request.Model
{   
    /// <summary>
    /// RequestPatientDetails
    /// </summary>
    [Serializable]
    public class RequestPatientDetails : BaseRequestItem
    {
        #region Fields
        
        //Patient
        public const string IdKey              = "id";
        public const string NameKey            = "name";
        public const string MRNKey             = "mrn";
        public const string GenderKey          = "gender";
        public const string EPNKey             = "epn";
        public const string SSNKey             = "ssn";
        public const string DOBKey             = "dob";
        public const string FacilityKey        = "facility";
        public const string FacilityTypeKey    = "is-freeformfacility";
        public const string FirstNameKey       = "firstname";
        public const string LastNameKey        = "lastname";
        private const string SupplementalIdKey = "supplemental-id";
        
        public const string IsVipKey             = "is-vip";
        public const string IsLockedPatientKey   = "patientLocked";
        public const string IsLockedEncounterKey = "encounterLocked";

        private const string HPFPatientElement    = "<{0} {1}=\"{2}\" {3}=\"{4}\">";
        private const string NonHpfPatientElement = "<{0} {1}=\"{2}\">";

        //item
        public const string ItemKey      = "item";
        private const string ItemElement = "<{0} {1}=\"{2}\" {3}=\"{4}\">";
        private const string SourceKey   = "source";
        private const string TypeKey     = "type";
        
        private string gender;
        private string facilityCode;
        private string epn;
        private long id;
        private long patientSeq;
        private string ssn;
        private string mrn;
        private bool patientLocked;
        private string fullName;
        private bool isHpf;
        private bool isVip;
        private bool encounterLocked;
        private Nullable<DateTime> dob;
        
        private RequestEncounterDetails defaultEncounter;

        private RequestGlobalDocuments globalDocs;
        private RequestNonHpfDocuments nonHpfDocs;
        private RequestAttachments attachments;

        private long supplementalIdField;
        private long requestorIdField;
        private long requestIdField;

        //holds the FacilityType
        private FacilityType facilityType;

        private bool isFreeformFacility;

        private Dictionary<long, bool> pageStatus;
        private string lastName;
        private string firstName;
        private string address1;
        private string address2;
        private string address3;
        private string city;
        private string state;
        private string postalCode;
        private string workPhone;
        private string homePhone;
        

        #endregion

        private class RequestPatientSorter : IComparer<RequestPatientDetails>
        {
            #region IComparer<RequestPatientDetails> Members

            public int Compare(RequestPatientDetails x, RequestPatientDetails y)
            {
                return x.SorterKey.CompareTo(y.SorterKey);
            }

            #endregion
        }

        private static RequestPatientSorter sorter;

        public static IComparer<RequestPatientDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new RequestPatientSorter();
                }
                return sorter;
            }
        }

        public override IComparer<string> DefaultSorter
        {
            get
            {
                return RequestEncounterDetails.RequestEncounterSorter;
            }
        }


        #region Constructors

        public RequestPatientDetails() 
        {
            defaultEncounter = new RequestEncounterDetails();

            defaultEncounter.EncounterId = "0";
            globalDocs = new RequestGlobalDocuments(this);
            nonHpfDocs = new RequestNonHpfDocuments(this);
            attachments = new RequestAttachments(this);
        }

        public RequestPatientDetails(PatientDetails recordPatient): this()
        { 
            Id       = recordPatient.Id;
            FullName = recordPatient.FullName;
            //SOGI OC-111171
            // gender   = (recordPatient.Gender != McK.EIG.ROI.Client.Base.Model.Gender.None) ? EnumUtilities.GetDescription(recordPatient.Gender):string.Empty;
            gender = recordPatient.GenderDesc;
            mrn      = recordPatient.MRN;
            epn      = recordPatient.EPN;
            ssn      = recordPatient.SSN;
            dob      = recordPatient.DOB;
            facilityCode = recordPatient.FacilityCode;
            IsFreeformFacility = recordPatient.IsFreeformFacility;
            isHpf    = recordPatient.IsHpf;
            isVip    = recordPatient.IsVip;
            patientLocked = recordPatient.PatientLocked;
            encounterLocked = recordPatient.EncounterLocked;
            lastName = recordPatient.LastName;
            firstName = recordPatient.FirstName;
            if (recordPatient.Address != null)
            {
                address1 = recordPatient.Address.Address1;
                address2 = recordPatient.Address.Address2;
                address3 = recordPatient.Address.Address3;
                city = recordPatient.Address.City;
                state = recordPatient.Address.State;
                postalCode = recordPatient.Address.PostalCode;
            }
            
            homePhone = recordPatient.HomePhone;
            workPhone = recordPatient.WorkPhone;
        }

        #endregion

        #region Methods

        public override bool Equals(object obj)
        {
            if (object.ReferenceEquals(this, obj)) return true;

            return (this.GetType() == obj.GetType()) &&
                   (Key == ((RequestPatientDetails)obj).Key);
        }

        public override int GetHashCode()
        {
            return this.GetType().GetHashCode() + Key.GetHashCode();
        }
        
        public bool? CheckedState
        {
            get
            {
                if (SelectedForRelease.HasValue &&
                    GlobalDocument.SelectedForRelease.HasValue &&
                    NonHpfDocument.SelectedForRelease.HasValue &&
                    Attachment.SelectedForRelease.HasValue)
                {
                    if ((SelectedForRelease.Value || GetChildren.Count == 0) &&
                        (GlobalDocument.SelectedForRelease.Value || GlobalDocument.GetChildren.Count == 0) &&
                        (NonHpfDocument.SelectedForRelease.Value || NonHpfDocument.GetChildren.Count == 0) &&
                        (Attachment.SelectedForRelease.Value || Attachment.GetChildren.Count == 0))
                    {
                        return true;
                    }
                    else if (!SelectedForRelease.Value &&
                        !GlobalDocument.SelectedForRelease.Value &&
                        !NonHpfDocument.SelectedForRelease.Value &&
                        !Attachment.SelectedForRelease.Value)
                    {
                        return false;
                    }
                }
                return null;
            }
        }

       
        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the RequestPatientDetails Id.
        /// </summary>
        public long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets the RequestPatientDetails Id.
        /// </summary>
        public long PatientSeq
        {
            get { return patientSeq; }
            set { patientSeq = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Patient Name.
        /// </summary>
        public string FullName
        {
            get { return fullName; }
            set { fullName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the IsHpf value.
        /// </summary>
        public bool IsHpf
        {
            get { return isHpf; }
            set { isHpf = value; }
        }

        /// <summary>
        /// This property is used to get or sets the IsVip Value.
        /// </summary>
        public bool IsVip
        {
            get { return isVip; }
            set {isVip = value; }
        }

        /// <summary>
        /// This property is used to get or sets the DOB.
        /// </summary>
        public Nullable<DateTime> DOB
        {
            get { return dob; }
            set 
            { 
                dob = value;
            }
        }

        /// <summary>
        /// This property is used to get or sets the Request Patient Gender.
        /// </summary>
        public string Gender
        {
            get { return gender; }
            set { gender = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Patient Facility.
        /// </summary>
        public string FacilityCode
        {
            get { return facilityCode; }
            set { facilityCode = value; }
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
        /// This property is used to get or sets the Request Patient SSN.
        /// </summary>
        public string SSN
        {
            get {  return ssn; }
            set { ssn = value; }
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
        /// This property is used to get HasDocuments value.
        /// </summary>
        public bool HasDocuments
        {
            get
            {
                return (GetChildren.Count > 0 || NonHpfDocument.GetChildren.Count > 0 || Attachment.GetChildren.Count > 0 || GlobalDocument.GetChildren.Count > 0);
            }
        }

        /// <summary>
        /// This property is used to get or sets the Request Global Document.
        /// </summary>
        public RequestGlobalDocuments GlobalDocument
        {
            set { globalDocs = value; }
            get { return globalDocs; }
        }

        /// <summary>
        /// This property is used to get or sets the Request NonHpf Document.
        /// </summary>
        public RequestNonHpfDocuments NonHpfDocument
        {
            set { nonHpfDocs = value; }
            get { return nonHpfDocs; }
        }

        /// <summary>
        /// This property is used to get or sets the Request Attachment.
        /// </summary>
        public RequestAttachments Attachment
        {
            set { attachments = value; }
            get { return attachments; }
        }
        
        /// <summary>
        /// This property is used to get or sets the patient locked status
        /// </summary>
        public bool IsLockedPatient
        {
            get { return patientLocked; }
            set { patientLocked = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient DOB
        /// </summary>
        public string FormattedDOB
        {
            get
            {
                if (dob.HasValue)
                {
                    return dob.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                return null;
            }
        }

        /// <summary>
        /// This property is used to get the name.
        /// </summary>
        public override string Name
        {
            get { return FullName; }
        }

        /// <summary>
        /// This property is used to get the key.
        /// </summary>
        public override string Key
        {
            get 
            {
                if (IsHpf)
                {
                    //return Convert.ToString(id, System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + mrn + "." + facility;
                    //Shahm
                    return mrn.Trim() + "." + facilityCode.Trim();
                }
                return Convert.ToString(id, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
        }


        /// <summary>
        /// This property is used to get or sets the patient facility type.
        /// </summary>
        public FacilityType FacilityType
        {
            get { return facilityType; }
            set { facilityType = value; }
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
        /// Returns only relased patient
        /// </summary>
        public RequestPatientDetails ReleasedItems
        {
            get
            {
                RequestPatientDetails patient = null;

                if (IsHpf)
                {
                    //HPF docuemnts
                    List<BaseRequestItem> encounters = new List<BaseRequestItem>(GetChildren);
                    encounters = encounters.FindAll(delegate(BaseRequestItem encounter) { return (!encounter.SelectedForRelease.HasValue || encounter.SelectedForRelease.Value); });
                    //patient = (RequestPatientDetails)ROIViewUtility.DeepClone(this);
                    patient = Clone();
                    foreach (RequestEncounterDetails encounter in encounters)
                    {
                        patient.AddChild(encounter.ReleasedItems);
                    }
                    //Global documents
                    List<BaseRequestItem> golbalDocuments = new List<BaseRequestItem>(GlobalDocument.GetChildren);
                    golbalDocuments = golbalDocuments.FindAll(delegate(BaseRequestItem golbalDocument) { return (!golbalDocument.SelectedForRelease.HasValue || golbalDocument.SelectedForRelease.Value); });
                    patient.GlobalDocument.ClearChildren();
                    foreach (RequestDocumentDetails globalDoc in golbalDocuments)
                    {
                        patient.GlobalDocument.AddChild(globalDoc.ReleasedItems);
                    }
                }
               
                // Non Hpf documents
                //List<BaseRequestItem> nonHpfDocuments = new List<BaseRequestItem>(NonHpfDocument.GetChildren);
                //nonHpfDocuments = nonHpfDocuments.FindAll(delegate(BaseRequestItem nonHpfDocument) 
                //                                          { 
                //                                              if (!nonHpfDocument.IsReleased && nonHpfDocument.SelectedForRelease.HasValue)
                //                                              {
                //                                                  nonHpfDocument.IsReleased =  nonHpfDocument.SelectedForRelease.Value;
                //                                              }
                                                              
                //                                              return (!nonHpfDocument.SelectedForRelease.HasValue || nonHpfDocument.SelectedForRelease.Value); 
                //                                          });

                List<BaseRequestItem> nonHpfEncounters = new List<BaseRequestItem>(NonHpfDocument.GetChildren);
                nonHpfEncounters = nonHpfEncounters.FindAll(delegate(BaseRequestItem nonHpfEncounter) 
                                                          {   
                                                              return (!nonHpfEncounter.SelectedForRelease.HasValue || nonHpfEncounter.SelectedForRelease.Value); 
                                                          });
                List<BaseRequestItem> attachmentEncounters = new List<BaseRequestItem>(Attachment.GetChildren);
                attachmentEncounters = attachmentEncounters.FindAll(delegate(BaseRequestItem attachmentEncounter)
                {
                    return (!attachmentEncounter.SelectedForRelease.HasValue || attachmentEncounter.SelectedForRelease.Value);
                });


                if(!IsHpf)
                {
                    patient = Clone();
                }

                patient.NonHpfDocument.ClearChildren();
                if (nonHpfEncounters != null)
                {
                    if (nonHpfEncounters.Count > 0)
                    {
                        if (nonHpfEncounters[0].GetType().Equals(typeof(RequestNonHpfEncounterDetails)))
                        {
                            foreach (RequestNonHpfEncounterDetails nonHpfEncounter in nonHpfEncounters)
                            {
                                patient.NonHpfDocument.AddChild(nonHpfEncounter.ReleasedItems);
                            }
                        }
                        else
                        {
                            foreach (RequestNonHpfDocumentDetails nonHpfDocument in nonHpfEncounters)
                            {
                                patient.NonHpfDocument.AddChild(nonHpfDocument);
                            }
                        }
                    }
                }

                patient.Attachment.ClearChildren();
                if (attachmentEncounters != null)
                {
                    if (attachmentEncounters.Count > 0)
                    {
                        if (attachmentEncounters[0].GetType().Equals(typeof(RequestAttachmentEncounterDetails)))
                        {
                            foreach (RequestAttachmentEncounterDetails attachmentEncounter in attachmentEncounters)
                            {
                                patient.Attachment.AddChild(attachmentEncounter.ReleasedItems);
                            }
                        }                        
                    }                   
                }

                return patient;
            }
        }

        private RequestPatientDetails Clone()
        {
            RequestPatientDetails patient = new RequestPatientDetails();
            patient.DOB = dob;
            patient.EncounterLocked = encounterLocked;
            patient.EPN = epn;
            patient.FacilityCode = facilityCode;
            patient.FullName = fullName;
            patient.Gender = gender;
            patient.Id = id;
            patient.IsHpf = isHpf;
            patient.IsLockedPatient = patientLocked;
            patient.RecordVersionId = RecordVersionId;
            patient.SelectedForRelease = SelectedForRelease;
            patient.SSN = ssn;
            patient.MRN = mrn;
            patient.IsVip = isVip;
            patient.IsFreeformFacility = IsFreeformFacility;
            patient.FirstName = firstName;
            patient.LastName = lastName;
            return patient;
        }

        public bool EncounterLocked
        {
            set { encounterLocked = value; }
            get { return encounterLocked; }
        }

        public string SorterKey
        {
            get 
            { 
                return (UserData.Instance.EpnEnabled) 
                          ? (fullName + "." + epn) 
                          : (fullName + "." + facilityCode + "." + mrn); 
            }
        }

        public long SupplementalId
        {
            get { return supplementalIdField; }
            set { supplementalIdField = value; }
        }

        /// <remarks/>
        public long RequestorId
        {
            get { return requestorIdField; }
            set { requestorIdField = value; }
        }

        /// <remarks/>
        public long RequestId
        {
            get { return requestIdField; }
            set { requestIdField = value; }
        }

        /// <summary>
        /// Gets or sets the list of pages details.
        /// </summary>
        public Dictionary<long, bool> PageStatus
        {
            get
            {
                if (pageStatus == null)
                {
                    pageStatus = new Dictionary<long, bool>();
                }
                return pageStatus;
            }
        }

        public string LastName
        {
            get { return lastName; }
            set { lastName = value; }
        }

        public string FirstName
        {
            get { return firstName; }
            set { firstName = value; }
        }

        public string Address1
        {
            get { return address1; }
            set { address1 = value; }
        }

        public string Address2
        {
            get { return address2; }
            set { address2 = value; }
        }

        public string Address3
        {
            get { return address3; }
            set { address3 = value; }
        }

        public string City
        {
            get { return city; }
            set { city = value; }
        }

        public string State
        {
            get { return state; }
            set { state = value; }
        }

        public string PostalCode
        {
            get { return postalCode; }
            set { postalCode = value; }
        }

        public string HomePhone
        {
            get { return homePhone; }
            set { homePhone = value; }
        }

        public string WorkPhone
        {
            get { return workPhone; }
            set { workPhone = value; }
        }
    
        #endregion
    }
}
