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
using System.Collections.ObjectModel;
using System.Collections.Generic;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    /// <summary>
    /// Class Validate the Patient Information.
    /// </summary>
    public partial class PatientValidator : BaseROIValidator
    {
        private const int PatientNameMaxLength = 256;

        #region Methods

        /// <summary>
        /// Validates the patient info primary fields.
        /// </summary>
        /// <param name="paymentMethod"></param>
        public void ValidatePrimaryFields(PatientDetails patientInfo)
        {
            if (patientInfo.FirstName.Length == 0)
            {
                AddError(ROIErrorCodes.PatientFirstNameEmpty);
            }

            if (patientInfo.LastName.Length == 0)
            {
                AddError(ROIErrorCodes.PatientLastNameEmpty);
            }

            if (patientInfo.FirstName.Length > PatientNameMaxLength)
            {
                AddError(ROIErrorCodes.PatientFirstNameMaxLength);
            }

            if (patientInfo.LastName.Length > PatientNameMaxLength)
            {
                AddError(ROIErrorCodes.PatientLastNameMaxLength);
            }

            ValidatePrimaryFields(patientInfo.LastName, ROIConstants.NameValidation, ROIErrorCodes.InvalidLastName);
            ValidatePrimaryFields(patientInfo.FirstName, ROIConstants.NameValidation, ROIErrorCodes.InvalidFirstName);
            ValidatePrimaryFields(patientInfo.SSN, ROIConstants.SsnValidation, ROIErrorCodes.InvalidSsn);
            ValidatePrimaryFields(patientInfo.EPN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidEpn);
            ValidatePrimaryFields(patientInfo.MRN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidMrn);
            ValidatePrimaryFields(patientInfo.FacilityCode, ROIConstants.FacilityValidation, ROIErrorCodes.InvalidFacility);

            if (patientInfo.PatientDob != null)
            {
                if (!ROIViewUtility.IsValidFormat(patientInfo.PatientDob))
                {
                    AddError(ROIErrorCodes.InvalidDate);
                }
                else if (!ROIViewUtility.IsValidDate(patientInfo.PatientDob))
                {
                    AddError(ROIErrorCodes.InvalidDateValue);
                }
                else
                {
                    patientInfo.DOB = Convert.ToDateTime(patientInfo.PatientDob, System.Globalization.CultureInfo.InvariantCulture);
                }
            }            

            if (patientInfo.DOB != null && patientInfo.DOB > DateTime.Now.Date)
            {
                AddError(ROIErrorCodes.IncorrectDate);
            }            

            if (patientInfo.Address != null)
            {
                ValidatePrimaryFields(patientInfo.Address.City, ROIConstants.CityValidation, ROIErrorCodes.InvalidMainCity);
                ValidatePrimaryFields(patientInfo.Address.State, ROIConstants.Alphanumeric, ROIErrorCodes.InvalidMainState);
               // ValidatePrimaryFields(patientInfo.Address.PostalCode, ROIConstants.ZipValidation, ROIErrorCodes.InvalidMainZip);
                ValidatePrimaryFields(patientInfo.HomePhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidHomePhone);
                ValidatePrimaryFields(patientInfo.WorkPhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidWorkPhone);
            }
        }

        /// <summary>
        /// Validates the fields before updating patient info.
        /// </summary>
        /// <param name="paymentMethod"></param>
        /// <returns></returns>
        public bool Validate(PatientDetails patientInfo)
        {
            if (!patientInfo.IsHpf)
            {
                ValidatePrimaryFields(patientInfo);
            }

            foreach (NonHpfEncounterDetails encounter in patientInfo.NonHpfDocuments.GetChildren)
            {
                Validate(encounter.GetChildren);
            }

            foreach (AttachmentEncounterDetails encounter in patientInfo.Attachments.GetChildren)
            {
                ValidateAttachments(encounter.GetChildren);
            }
           
            return NoErrors;
        }

        internal bool ValidateSearchFields(FindPatientCriteria searchCriteria)
        {
            ValidatePrimaryFields(searchCriteria.LastName, ROIConstants.NameValidation, ROIErrorCodes.InvalidLastName);
            ValidatePrimaryFields(searchCriteria.FirstName, ROIConstants.NameValidation, ROIErrorCodes.InvalidFirstName);
            //ValidatePrimaryFields(searchCriteria.SSN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidSsn);
            ValidatePrimaryFields(searchCriteria.MRN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidMrn);
            ValidatePrimaryFields(searchCriteria.EPN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidEpn);
            ValidatePrimaryFields(searchCriteria.Encounter, ROIConstants.NameValidation, ROIErrorCodes.InvalidEncounter);

            if (!string.IsNullOrEmpty(searchCriteria.SSN))
            {
                if (searchCriteria.SSN.Split('-', ' ')[0].Trim().Length < 3)
                {
                    AddError(ROIErrorCodes.InvalidSsnSearch);
                }
            }

            if (searchCriteria.Dob != null && searchCriteria.Dob > DateTime.Now.Date)
            {
                AddError(ROIErrorCodes.IncorrectDate);
            }            

            return NoErrors;
        }

        private void ValidatePrimaryFields(string field, string validCharSet, string errorCode)
        {
            if (!Validator.Validate(field, validCharSet))
            {
                AddError(errorCode);
            }
        }

        /// <summary>
        /// Validate the Patient Search Criteria.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public static bool ValidatePrimarySearchFields(FindPatientCriteria searchCriteria)
        {
            int minimumLength   = 3; 
            int lastNameLength  = (searchCriteria.LastName != null) ? searchCriteria.LastName.Length : 0;
            int firstNameLength = (searchCriteria.FirstName != null)? searchCriteria.FirstName.Length : 0;
            int ssnLength       = (searchCriteria.SSN != null)? searchCriteria.SSN.Trim('-',' ').Length : 0;
            int mrnLength       = (searchCriteria.MRN != null)? searchCriteria.MRN.Length : 0;
            int epnLength       = (searchCriteria.EPN != null) ? searchCriteria.EPN.Length : 0;
            int encounterLength = (searchCriteria.Encounter != null) ? searchCriteria.Encounter.Length : 0;
            Nullable<DateTime> selectedDate = searchCriteria.Dob;
            int totalNameLength = lastNameLength + firstNameLength;

            if (!string.IsNullOrEmpty(searchCriteria.EPN))
            {
                if (searchCriteria.EPN.Length > 2)
                {
                    if (UserData.Instance.EpnPrefix != null)
                    {
                        if (!string.IsNullOrEmpty(UserData.Instance.EpnPrefix) && searchCriteria.EPN.StartsWith(UserData.Instance.EpnPrefix.Trim()))
                        {
                            epnLength = searchCriteria.EPN.Substring(UserData.Instance.EpnPrefix.Length).Length;
                        }
                    }
                }
            }                                 

            if (lastNameLength >= minimumLength 
                || firstNameLength >= minimumLength
                || ssnLength >= minimumLength 
                || mrnLength >= minimumLength 
                || epnLength >= minimumLength 
                || encounterLength >= minimumLength
                || (selectedDate != null && (searchCriteria.FacilityCode != null))
                || totalNameLength >= minimumLength)
            {
                return true;
            }
            return false;
        }

        #endregion
    }
}
