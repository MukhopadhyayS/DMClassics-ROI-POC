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
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Patient.Model
{
    /// <summary>
    /// Class holds the patient search criteria details.
    /// </summary>    
    public class FindPatientCriteria 
    {
        #region Fields

        //holds the First Name
        private string firstName;

        //holds the Last Name
        private string lastName;

        //holds the Data of Birth
        private Nullable<DateTime> dob;

        //holds the SSN
        private string ssn;

        //holds the EPN
        private string epn;

        //holds the MRN
        private string mrn;

        //holds the Encounter
        private string encounter;

        //holds the Facility
        private string facilityCode;

        //holds the MaximumRecord        
        private int maxrecord;

        //holds the facility type
        private FacilityType facilityType;

        #endregion

        #region Methods

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            lastName  = (lastName != null && lastName.Trim().Length != 0) ? lastName.Trim() : null;
            firstName = (firstName != null && firstName.Trim().Length != 0) ? firstName.Trim() : null;
            ssn       = (ssn != null && ssn.Trim().Length != 0) ? ssn.Trim() : null;
            encounter = (encounter != null && encounter.Trim().Length != 0) ? encounter.Trim() : null;
            mrn       = (mrn != null && mrn.Trim().Length != 0) ? mrn.Trim() : null;
            epn       = (epn != null && epn.Trim().Length != 0) ? epn.Trim() : null;            
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the Patient search First Name.
        /// </summary>
        public string FirstName
        {
            get { return firstName; }
            set { firstName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search Last Name.
        /// </summary>
        public string LastName
        {
            get { return lastName; }
            set { lastName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search Date of Birth.
        /// </summary>
        public Nullable<DateTime> Dob
        {
            get { return dob; }
            set { dob = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search SSN.
        /// </summary>
        public string SSN
        {
            get { return ssn; }
            set { ssn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search EPN.
        /// </summary>
        public string EPN
        {
            get { return epn; }
            set { epn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search MRN.
        /// </summary>
        public string MRN
        {
            get { return mrn; }
            set { mrn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search Encounter.
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search Facility.
        /// </summary>
        public string FacilityCode
        {
            get { return facilityCode; }
            set { facilityCode = value; }
        }

        /// <summary>
        /// This property is used to get or sets the MaxRecord, that returned as Patient Search result.
        /// </summary>
        public int MaxRecord
        {
            get { return maxrecord; }
            set { maxrecord = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Patient search Facility.
        /// </summary>
        public FacilityType FacilityType
        {
            get { return facilityType; }
            set { facilityType = value; }
        }

        #endregion
    }
}
