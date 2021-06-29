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

using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    /// <summary>
    /// This enum contains requestor status types.
    /// All - Active and Inactive
    /// </summary>
    public enum RequestorStatusType
    {
        All,
        Active,
        Inactive
    }

    /// <summary>
    /// This class is used to hold FindRequestorCriteria info.
    /// </summary>
    public class FindRequestorCriteria
    {
        #region Fields

        private bool allRequestors;

        private string name;

        private string lastName;

        private string firstName;

        private string patientEpn;

        private Nullable<DateTime> patientDob;

        private string patientMrn;

        private string patientFacilityCode;

        private bool isFreeformFacility;

        private string patientSsn;

        private int maxRecord;

        private long requestorTypeId;

        private Nullable<bool> activeRequestor;

        private string requestorTypeName;

        private bool isPatientRequestor;


        
        #endregion

        #region Constructors

        public FindRequestorCriteria()
        {
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the filterby option.
        /// </summary>
        public bool AllRequestors
        {
            get { return allRequestors; }
            set { allRequestors = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestor name.
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestor last name.
        /// </summary>
        public string LastName
        {
            get { return lastName; }
            set { lastName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the requestor first name.
        /// </summary>
        public string FirstName
        {
            get { return firstName; }
            set { firstName = value; }
        }

        /// <summary>
        /// This property is used to get or sets the PatientEPN.
        /// </summary>
        public string PatientEPN
        {
            get { return patientEpn; }
            set { patientEpn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the PatientDOB
        /// </summary>
        public Nullable<DateTime> PatientDob
        {
            get { return patientDob; }
            set { patientDob = value; }
        }
        
        /// <summary>
        /// This property is used to get or sets the PatientSSN.
        /// </summary>
        public string PatientSSN
        {
            get { return patientSsn; }
            set { patientSsn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the PatientMRN.
        /// </summary>
        public string PatientMRN
        {
            get { return patientMrn; }
            set { patientMrn = value; }
        }

        /// <summary>
        /// This property is used to get or sets the PatientFacility.
        /// </summary>
        public string PatientFacilityCode
        {
            get { return patientFacilityCode; }
            set { patientFacilityCode = value; }
        }

        /// <summary>
        /// his property is used to get or sets the patient facility type is free form or not.
        /// </summary>
        public bool IsFreeformFacility
        {
            get { return isFreeformFacility; }
            set { isFreeformFacility = value;}
        }

        /// <summary>
        /// This property is used to get or sets the MaxRecord value.
        /// </summary>
        public int MaxRecord
        {
            get { return maxRecord; }
            set { maxRecord = value; }
        }

        /// <summary>
        /// This property is used to get or sets the Requestor type.
        /// </summary>
        public long RequestorTypeId
        {
            get { return requestorTypeId; }
            set { requestorTypeId = value; }
        }

        /// <summary>
        /// This property is used to get or sets the status.
        /// </summary>
        public Nullable<bool> ActiveRequestor
        {
            get { return activeRequestor; }
            set { activeRequestor = value; }
        }

        public string RequestorTypeName
        {
            get { return requestorTypeName; }
            set { requestorTypeName = value; }
        }

        public bool IsPatientRequestor
        {
            get { return isPatientRequestor; }
            set { isPatientRequestor = value; }
        }

        #endregion
    }
}
