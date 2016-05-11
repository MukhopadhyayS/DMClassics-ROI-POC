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
using System.Globalization;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Requestors.Model
{

    /// <summary>
    /// This enum contians address types of a requestor.
    /// </summary>
    public enum AddressType
    {
           MainAddress, 
           AlternateAddress 
    }

    public enum DateRange
    {
        [Description("Please Select")]
        None = 0,
        [Description("30 Days")]
        DAYS_30 = 1,
        [Description("60 Days")]
        DAYS_60 = 2,
        [Description("90 Days")]
        DAYS_90 = 3,
        [Description("120 Days")]
        DAYS_120 = 4,
        [Description("Year to Date")]
        YEAR_TO_DATE = 5,
        [Description("All")]
        ALL = 6
    }


    public enum AdjustmentType
    {
        [Description("Please Select")]
        PleaseSelect = 0,
        [Description("Billing Adjustment")]
        BILLING_ADJUSTMENT = 1,
        [Description("Customer Goodwill Adjustment")]
        CUSTOMER_GOODWILL_ADJUSTMENT = 2,
        [Description("Bad Debt Adjustment")]
        BAD_DEBT_ADJUSTMENT = 3,
    }

    /// <summary>
    /// This class is used to hold RequestorDetails Info.
    /// </summary>
    [Serializable]
    public class RequestorDetails : ROIModel
    {

        private class RequestorDetailsSorter : IComparer<RequestorDetails>
        {
            #region IComparer<RequestorDetails> Members

            public int Compare(RequestorDetails x, RequestorDetails y)
            {
                return new StringLogicalComparer().Compare(x.name, y.name);
            }

            #endregion
        }

        private static RequestorDetailsSorter sorter;

        public static IComparer<RequestorDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new RequestorDetailsSorter();
                }
                return sorter;
            }
        }


        #region Fields

        private long id;

        private long type;

        private string typeName;

        private string name;

        private string lastName;

        private string firstName;

        private string patientEpn;

        private string patientSsn;

        private string patientMrn;

        private string patientFacilityCode;

        private FacilityType patientFacilityType;

        private Nullable<DateTime> patientDob;

        private string dob;

        private bool prePaymentRequired;

        private bool letterRequired;

        private string homePhone;

        private string workPhone;

        private string cellPhone;

        private string email;

        private string fax;

        private string contactName;

        private string contactPhone;

        private string contactEmail;

        private AddressDetails mainAddress;

        private AddressDetails altAddress;

        private string status;

        private bool isFreeformFacility;

        private bool isAssociated;

        private bool isActive;

        //holds the Formatted Date Formate
        private Nullable<DateTime> formattedDateTime;

        private bool hasSalesTax;

        #endregion

        #region Constructor

        public RequestorDetails()
        {
        }

        #endregion

        #region Methods

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            name            = string.IsNullOrEmpty(name) ? string.Empty : name.Trim();
            lastName        = string.IsNullOrEmpty(lastName) ? string.Empty : lastName.Trim();
            firstName       = string.IsNullOrEmpty(firstName) ? string.Empty : firstName.Trim();
            homePhone       = string.IsNullOrEmpty(homePhone) ? string.Empty : homePhone.Trim();
            workPhone       = string.IsNullOrEmpty(workPhone) ? string.Empty : workPhone.Trim();
            cellPhone       = string.IsNullOrEmpty(cellPhone) ? string.Empty : cellPhone.Trim();
            email           = string.IsNullOrEmpty(email) ? string.Empty : email.Trim();
            fax             = string.IsNullOrEmpty(fax) ? string.Empty : fax.Trim();
            contactName     = string.IsNullOrEmpty(contactName) ? string.Empty : contactName.Trim();
            contactPhone    = string.IsNullOrEmpty(contactPhone) ? string.Empty : contactPhone.Trim();
            contactEmail    = string.IsNullOrEmpty(contactEmail) ? string.Empty : contactEmail.Trim();

            if (mainAddress != null)
            {
                mainAddress.Normalize();
            }
            if (altAddress != null)
            {
                altAddress.Normalize();
            }
        }

        #endregion
        
        #region Properties

        /// <summary>
        /// This property is used to get or sets the Requestor Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Name.
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Last Name.
        /// </summary>
        public string LastName
        {
            get { return lastName; }
            set { lastName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor First Name.
        /// </summary>
        public string FirstName
        {
            get { return firstName; }
            set { firstName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Full Name.
        /// </summary>
        public string FullName
        {
            get { return name; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Type.
        /// </summary>
        public long Type
        {
            get { return type; }
            set 
            { 
                type = value;
            }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor Type Name.
        /// </summary>
        public string TypeName
        {
            get { return typeName; }
            set { typeName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient EPN.
        /// </summary>
        public string PatientEPN
        {
            get { return patientEpn; }
            set { patientEpn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient SSN.
        /// </summary>
        public string PatientSSN
        {
            get { return patientSsn; }
            set  { patientSsn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient MRN.
        /// </summary>
        public string PatientMRN
        {
            get { return patientMrn; }
            set { patientMrn = value;}
        }

        /// <summary>
        /// This property is used to get or sets the Patient Facility.
        /// </summary>
        public string PatientFacilityCode
        {
            get { return patientFacilityCode; }
            set { patientFacilityCode = value; }
        }

        /// <summary>
        /// This property is used to get or sets the PatientFacilityType.
        /// </summary>
        public FacilityType PatientFacilityType
        {
            get { return patientFacilityType; }
            set { patientFacilityType = value; }
        }


        /// <summary>
        /// his property is used to get or sets the patient facility type is free form or not.
        /// </summary>
        public bool IsFreeformFacility
        {
            get
            {
                if (patientFacilityType == McK.EIG.ROI.Client.Base.Model.FacilityType.NonHpf)
                {
                    isFreeformFacility = true;
                }
                else
                {
                    isFreeformFacility = false;
                }

                return isFreeformFacility;
            }
            set
            {
                if (value)
                {
                    patientFacilityType = McK.EIG.ROI.Client.Base.Model.FacilityType.NonHpf;
                }
                else
                {
                    patientFacilityType = McK.EIG.ROI.Client.Base.Model.FacilityType.Hpf;
                }

                isFreeformFacility = value;
            }
        }

        /// <summary>
        /// This property is used to get the masked value of patient's ssn 
        /// if ssn is been masked.
        /// </summary>
        public string MaskedPatientSSN
        {
            get 
            { 
                return (UserData.Instance.IsSSNMasked) 
                       ? ROIViewUtility.ApplyMask(patientSsn) 
                       : patientSsn; 
            }
        }

        /// <summary>
        /// This property is used to get or sets the Patient DOB.
        /// </summary>
        public Nullable<DateTime> PatientDob
        {
            get { return patientDob; }
            set 
            {
                patientDob = value;
                formattedDateTime = patientDob;
            }
        }

        public string Dob
        {
            get { return dob; }
            set { dob = value; }
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


        /// <summary>
        /// This property is used to get or sets whether the PrePayment is required.
        /// </summary>
        public bool PrepaymentRequired
        {
            get { return prePaymentRequired; }
            set { prePaymentRequired = value; }
        }

        /// <summary>
        /// This property is used to get or sets whether the Letter is required
        /// </summary>
        public bool LetterRequired
        {
            get { return letterRequired; }
            set { letterRequired = value; }
        }

        /// <summary>
        /// This property is used to get or sets Requestors Home Phone Number.
        /// </summary>
        public string HomePhone
        {
            get { return homePhone; }
            set { homePhone = value; }
        }

        /// <summary>
        /// This property is used to get or sets Requestors Work Phone Number.
        /// </summary>
        public string WorkPhone
        {
            get { return workPhone; }
            set { workPhone = value; }
        }

        /// <summary>
        /// This property is used to get or sets Requestors Cell Phone Number.
        /// </summary>
        public string CellPhone
        {
            get { return cellPhone; }
            set { cellPhone = value; }
        }

        /// <summary>
        /// This property is used to get or sets Requestors Email.
        /// </summary>
        public string Email
        {
            get { return email; }
            set { email = value; }
        }

        /// <summary>
        /// This property is used to get or sets Requestors Fax Number.
        /// </summary>
        public string Fax
        {
            get { return fax; }
            set { fax = value; }
        }

        /// <summary>
        /// This property is used to get or sets Contact Name.
        /// </summary>
        public string ContactName
        {
            get { return contactName; }
            set { contactName = value; }
        }

        /// <summary>
        /// This property is used to get or sets Contact Phone.
        /// </summary>
        public string ContactPhone
        {
            get { return contactPhone; }
            set { contactPhone = value; }
        }

        /// <summary>
        /// This property is used to get or sets Contact Email.
        /// </summary>
        public string ContactEmail
        {
            get { return contactEmail; }
            set { contactEmail = value; }
        }

        /// <summary>
        /// This Property is used to get or sets Address information.
        /// </summary> 
        public AddressDetails MainAddress
        {
            get { return mainAddress; }
            set { mainAddress = value; }
        }

        /// <summary>
        /// This Property is used to get or sets Address information.
        /// </summary> 
        public AddressDetails AltAddress
        {
            get { return altAddress; }
            set { altAddress = value; }
        }

        /// <summary>
        /// This Property is used to get or sets phone.
        /// </summary>
        public string Phone
        {
            get { return (type < 0) ? homePhone : workPhone; }
        }

        /// <summary>
        /// This property is used to get or sets isAssociated.
        /// </summary>
        public bool IsAssociated
        {
            get { return isAssociated; }
            set { isAssociated = value; }
        }

        /// <summary>
        /// This property is used to get or sets status of a requestor.
        /// </summary>
        public bool IsActive
        {
            get { return isActive; }
            set 
            {
                isActive = value;
                status = isActive ? ROIConstants.RequestorActive : ROIConstants.RequestorInactive;
            }
        }

        /// <summary>
        /// This property is used to get or sets status info text to display in the grid.
        /// </summary>
        public string Status
        {
            get { return status; }
            set { status = value; }
        }

        public const int PatientRequestorType = -1;
        public bool IsPatientRequestor
        {
            get { return (type == PatientRequestorType); }
        }

        /// <summary>
        /// This property is used to get or sets the salestax is applicable for the requestor.
        /// </summary>
        public bool HasSalesTax
        {
            get { return hasSalesTax; }
            set { hasSalesTax = value; }
        }

        #endregion
    }
}
