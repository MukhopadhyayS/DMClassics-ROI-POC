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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Patient.Model
{
    public enum SortBy
    {
        Encounter,
        Document
    }

    public enum Filter
    { 
        On,
        Off
    }

    [Serializable]
    public class EncounterDetails : BaseRecordItem
    { 

        private class EncounterSorter : IComparer<EncounterDetails>
        {
            public virtual int Compare(EncounterDetails x, EncounterDetails y)
            {
                return -1 * x.CompareProperty.CompareTo(y.CompareProperty);
            }
        }

        private static IComparer<EncounterDetails> sorter;
        public static IComparer<EncounterDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new EncounterSorter();
                }
                return sorter;
            }
        }

        #region Fields

        private string encounterId;
        private string facility;
        private string patientType;
        private string patientService;
        private Nullable<DateTime> admitDate;
        private bool isVip;
        private bool isLocked;
        private bool isDeficiency;
        private Nullable<DateTime> dischargeDate;

        private string clinic;
        private string disposition;
        private string financialClass;
        private string balance;
        private SortedList<string, string> customFields;
        private SortedList<DocumentType, DocumentDetails> documents;
        private bool isSelfPay;
        private string selfPayEncounterID;

        #endregion

        #region Constructor

        public EncounterDetails(){}

        /// <summary>
        /// Constructor collects all model detail and creates an object.
        /// </summary>
        /// <param name="encounterId"></param>
        /// <param name="patientType"></param>
        /// <param name="facility"></param>
        /// <param name="date"></param>
        /// <param name="isVip"></param>
        /// <param name="isLocked"></param>
        /// <param name="isDeficiency"></param>
        public EncounterDetails( string encounterId, 
                                 string patientType,
                                 string patientService, 
                                 string facility, 
                                 string admitDate,
                                 string dischargeDate,
                                 bool isVip, 
                                 bool isLocked, 
                                 bool isDeficiency,
                                 string clinic,
                                 string disposition, 
                                 string financialClass,
                                 string balance,
                                 bool isSelfPay   
                                )
        {
            IsSelfPay = isSelfPay;
            SelfPayEncounterID = encounterId;
            EncounterId         = encounterId;
            PatientType         = patientType;
            Facility            = facility;
            PatientService      = patientService;
            this.clinic         = clinic;
            this.patientService = patientService;
            this.disposition    = disposition;
            this.financialClass = financialClass;
            this.balance        = balance;


            if (admitDate != null)
            {
                AdmitDate = Convert.ToDateTime(admitDate, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            if (dischargeDate != null)
            {
                DischargeDate = Convert.ToDateTime(dischargeDate, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            IsVip        = isVip;
            IsLocked     = isLocked;
            HasDeficiency = isDeficiency;
        }
       
        #endregion

        #region Methods

        /// <summary>
        /// Adds documents to the encounter.
        /// </summary>
        /// <param name="doc"></param>
        public void AddDocument(DocumentDetails doc)
        {
            if (this.Documents.ContainsKey(doc.DocumentType)) return;
            this.Documents.Add(doc.DocumentType, doc);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds encoutner id.
        /// </summary>
        public string EncounterId
        {
            get { return encounterId; }
            set { encounterId = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds patient type.
        /// </summary>
        public string PatientType
        {
            get { return patientType; }
            set { patientType = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds facility where this encounter belongs.
        /// </summary>
        public string Facility
        {
            get { return facility; }
            set { facility = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds patient service.
        /// </summary>
        public string PatientService
        {
            get { return patientService; }
            set { patientService = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds encounter's admit date.
        /// </summary>
        public Nullable<DateTime> AdmitDate
        {
            get { return admitDate; }
            set { admitDate = value; }
        }

        /// <summary>
        /// Gets or Sets the HasDeficiency value of encounter./// If anyone of the documents has deficiency then it will be updated in the encounter level as true.
        /// </summary>
        public bool HasDeficiency
        {
            get { return isDeficiency; }
            set { isDeficiency = value; }
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
            set { isLocked = value; }
        }

        /// <summary>
        /// Encounter's discharge date
        /// </summary>
        public Nullable<DateTime> DischargeDate
        {
            get { return dischargeDate; }
            set { dischargeDate = value; }
        }

        /// <summary>
        /// Holds clinic where this encounter belongs.
        /// </summary>
        public string Clinic
        {
            get { return clinic; }
            set { clinic = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds disposition where this encounter belongs.
        /// </summary>
        public string Disposition
        {
            get { return disposition; }
            set { disposition = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds financialClass where this encounter belongs.
        /// </summary>
        public string FinancialClass
        {
            get { return financialClass; }
            set { financialClass = (value == null) ? string.Empty : value; }
        }

        /// <summary>
        /// Holds disposition where this encounter belongs.
        /// </summary>
        public string Balance
        {
            get { return balance; }
            set { balance = (value == null) ? string.Empty : value; }
        }

        public SortedList<string, string> CustomFields
        {
            get
            {
                if (customFields == null)
                    customFields = new SortedList<string, string>();
                return customFields;
            }
        }

        public override IComparable CompareProperty
        {
            get
            {
                return Key;
            }
        }

        /// <summary>
        /// Contains collection of documents under a encounter.
        /// </summary>
        public SortedList<DocumentType, DocumentDetails> Documents
        {
            get
            {
                if (documents == null)
                    documents = new SortedList<DocumentType, DocumentDetails>(DocumentType.Sorter);
                return documents;
            }
        }

        public bool HasDisclosureDocuments
        {
            get
            {
                foreach (DocumentType docType in documents.Keys)
                {
                    if (docType.IsDisclosure)
                    {
                        return true;
                    }
                }
                return false;
            }
        }

        public override System.Drawing.Image Icon
        {
            get { return ROIImages.EncounterIcon; }
        }

        public override string Key
        {
            get 
            {
                if (IsSelfPay)
                {
                    if(admitDate.HasValue )
                        return admitDate.Value.ToString("yyyy/MM/dd", CultureInfo.CurrentCulture) + "." + selfPayEncounterID + "." + facility;
                    else
                        return "0." + selfPayEncounterID + "." + facility;
                }
                else
                {
                    if (admitDate.HasValue)
                        return admitDate.Value.ToString("yyyy/MM/dd", CultureInfo.CurrentCulture) + "." + encounterId + "." + facility;
                    else
                        return "0." + encounterId + "." + facility;
                }
            }
        }

        public override string Name
        {
            get
            {
                if (IsSelfPay)
                {
                    return selfPayEncounterID.ToString();
                }
                else
                {
                    return encounterId.ToString();
                }
            }
        }

        /// <summary>
        /// Holds the IS self pay details
        /// </summary>
        public bool IsSelfPay
        {
            get { return isSelfPay; }
            set { isSelfPay = value; }
        }

        /// <summary>
        /// Hold the self pay encounter ID
        /// </summary>
        public string SelfPayEncounterID
        {
            get { return selfPayEncounterID; }
            set { selfPayEncounterID = value; }
        }

        #endregion

    }
}
