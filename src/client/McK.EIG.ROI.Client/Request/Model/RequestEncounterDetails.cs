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
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// RequestEncounterDetails
    /// </summary>
    [Serializable]
    public class RequestEncounterDetails : BaseRequestItem
    {
        [Serializable]
        private class RequestEncounterDetailSorter : IComparer<string>
        {
            #region IComparer<RequestEncounterDetailSorter> Members

            public int Compare(string x, string y)
            {
                return -1 * x.CompareTo(y);
            }

            #endregion
        }

        #region Fields
        
        //encounter
        private static RequestEncounterDetailSorter sorter;

        public const string FacilityKey      = "facility";
        private const string PatientTypeKey   = "patientType";
        private const string PatientStatusKey = "patientService";
        private const string AdmitDateKey     = "admitDate";
        private const string DischargeDateKey = "discharge-date";
        private const string DeficiencyKey    = "hasDeficiency";
        private const string VIPKey            = "is-vip";
        private const string LockedKey         = "is-locked";
        private const string EncounterElement = "<{0} {1}=\"{2}\">";

        public const string IdKey = "id";

        private bool isLocked;
        private bool isVip;
        private bool hasDeficiency;
        private string patientType;
        private string encounterId;
        private long patientSeq;
        private long encounterSeq;
        private string mrn;
        private string facility;
        private string patientService;
        private Nullable<DateTime> admitDate;
        private Nullable<DateTime> dischargeDate;
        private bool isSelfPay;
        private string selfPayEncounterID;

        #endregion

        #region Constructors

        public RequestEncounterDetails() { }

        public RequestEncounterDetails(EncounterDetails recordEncounter)
        {
            encounterId    = recordEncounter.EncounterId;
            facility       = recordEncounter.Facility;
            patientType    = recordEncounter.PatientType;
            hasDeficiency  = recordEncounter.HasDeficiency;
            isLocked       = recordEncounter.IsLocked;
            isVip          = recordEncounter.IsVip;
            patientService = recordEncounter.PatientService;
            isSelfPay      = recordEncounter.IsSelfPay;
            selfPayEncounterID = recordEncounter.SelfPayEncounterID;

            if (recordEncounter.AdmitDate.HasValue)
            {
                admitDate = recordEncounter.AdmitDate.Value;
            }

            if (recordEncounter.DischargeDate.HasValue)
            {
                dischargeDate = recordEncounter.DischargeDate.Value;
            }
        }

        #endregion
    
        #region Properties

        /// <summary>
        /// Gets or Sets the EncounterId.
        /// </summary>
        public string EncounterId
        {
            get { return encounterId;}
            set { encounterId = value; }
        }

        /// <summary>
        /// Gets or Sets the EncounterId.
        /// </summary>
        public long EncounterSeq
        {
            get { return encounterSeq; }
            set { encounterSeq = value; }
        }

        /// <summary>
        /// Gets or sets the patient sequence.
        /// </summary>
        public long PatientSeq
        {
            get { return patientSeq; }
            set { patientSeq = value; }
        }

        /// <summary>
        /// Gets or Sets the Facility.
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }

        /// <summary>
        /// Gets or Sets the Mrn.
        /// </summary>
        public string Mrn
        {
            get { return mrn; }
            set { mrn = value; }
        }

        /// <summary>
        /// Gets or Sets the Patient Type.
        /// </summary>
        public string PatientType
        {
            get { return patientType; }
            set { patientType = value; }
        }

        /// <summary>
        /// Gets or Sets the Admit Date.
        /// </summary>
        public Nullable<DateTime> AdmitDate
        {
            get { return admitDate; }
            set { admitDate = value; }
        }

        /// <summary>
        /// Gets or Sets the IsDeficiency Value.
        /// </summary>
        public bool HasDeficiency
        {
            get { return hasDeficiency; }
            set { hasDeficiency = value; }
        }

        /// <summary>
        /// Defines whether the encounter is VIP or not.
        /// </summary>
        public bool IsVip
        {
            get { return isVip; }
            set { isVip = value; }
        }

        /// <summary>
        /// Defines whether the encounter is locked or not.
        /// </summary>
        public bool IsLocked
        {
            get { return isLocked; }
            set {isLocked = value; }
        }

        /// <summary>
        /// Holds patient service of encounter.
        /// </summary>
        public string PatientService
        {
            get { return patientService; }
            set { patientService = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Gets or Sets the discharge Date.
        /// </summary>
        public Nullable<DateTime> DischargeDate
        {
            get { return dischargeDate; }
            set { dischargeDate = value; }
        }

        /// <summary>
        /// Gets the Image.
        /// </summary>
        public override System.Drawing.Image Icon
        {
            get { return ROIImages.EncounterIcon; }
        }

        /// <summary>
        /// Gets the Name.
        /// </summary>
        public override string Name
        {
            get 
            {
                if (IsSelfPay)
                {
                    return selfPayEncounterID;
                }
                else
                {
                    return encounterId;
                }
            }
        }

        /// <summary>
        /// Gets the Key.
        /// </summary>
        public override string Key
        {
            get 
            {
                if (IsSelfPay)
                {
                    return "0." + selfPayEncounterID + "." + facility;
                }
                else
                {
                    return "0." + encounterId + "." + facility;
                }
            }
        }

        /// <summary>
        /// Returns only released encounter
        /// </summary>
        public RequestEncounterDetails ReleasedItems
        {
            get
            {
                List<BaseRequestItem> documents = new List<BaseRequestItem>(GetChildren);
                documents = documents.FindAll(delegate(BaseRequestItem document) { return (!document.SelectedForRelease.HasValue || document.SelectedForRelease.Value); });
                //RequestEncounterDetails encounter = (RequestEncounterDetails)ROIViewUtility.DeepClone(this);
                RequestEncounterDetails encounter = Clone();
                foreach (RequestDocumentDetails document in documents)
                {
                    encounter.AddChild(document.ReleasedItems);
                }

                return encounter;
            }
        }

        private RequestEncounterDetails Clone()
        {
            RequestEncounterDetails encounter = new RequestEncounterDetails();
            encounter.AdmitDate = admitDate;
            encounter.DischargeDate = dischargeDate;
            encounter.EncounterId = encounterId;
            encounter.Facility = facility;
            encounter.HasDeficiency = hasDeficiency;
            encounter.IsLocked = isLocked;
            encounter.IsVip = isVip;
            encounter.PatientService = patientService;
            encounter.PatientType = patientType;
            encounter.RecordVersionId = RecordVersionId;
            encounter.SelectedForRelease = SelectedForRelease;
            encounter.IsSelfPay = isSelfPay;
            encounter.SelfPayEncounterID = selfPayEncounterID;

            return encounter;
        }

        public override IComparer<string> DefaultSorter
        {
            get { return RequestDocumentDetails.CustomSorter; }
        }

        public static IComparer<string> RequestEncounterSorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new RequestEncounterDetailSorter();
                }
                return sorter;
            }
        }

        public bool IsSelfPay
        {
            get { return isSelfPay; }
            set { isSelfPay = value; }
        }

        public string SelfPayEncounterID
        {
            get { return selfPayEncounterID; }
            set { selfPayEncounterID = value; }
        }

        #endregion
    }
}
