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

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Requestors.Controller
{
    /// <summary>
    /// Class Validate the Requestor Information.
    /// </summary>
    public class RequestorValidator : BaseROIValidator
    {
        #region Fields

        private const int minimumLength = 3;
        private const int RequestorNameMaxLength = 256;

        #endregion

        /// <summary>
        /// Validate the Requestor Search Criteria.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public static bool ValidateRequestorSearch(FindRequestorCriteria searchCriteria)
        {
            int requestorNameLength = (searchCriteria.Name != null) ? searchCriteria.Name.Length : 0;
            int reqFirstNameLength  = (searchCriteria.FirstName != null) ? searchCriteria.FirstName.Length : 0;
            int reqLastNameLength   = (searchCriteria.LastName != null) ? searchCriteria.LastName.Length : 0;
            int patientEpnLength    = (searchCriteria.PatientEPN != null) ? searchCriteria.PatientEPN.Length : 0;
            int patientSsnLength    = (searchCriteria.PatientSSN != null) ? searchCriteria.PatientSSN.Trim('-',' ').Length : 0;
            int patientMrnLength    = (searchCriteria.PatientMRN != null) ? searchCriteria.PatientMRN.Length : 0;
            int totalNameLength     = reqFirstNameLength + reqLastNameLength;

            if (!string.IsNullOrEmpty(searchCriteria.PatientEPN))
            {
                if (searchCriteria.PatientEPN.Length > 2)
                {
                    patientEpnLength = CalculatePatientEpnLength(searchCriteria.PatientEPN);
                }
            }
            
            //All requestor and date is not a valid search criteria
            if (patientEpnLength >= minimumLength 
                || patientSsnLength >= minimumLength
                || patientMrnLength >= minimumLength 
                || reqFirstNameLength >= minimumLength 
                || reqLastNameLength >= minimumLength
                || totalNameLength >= minimumLength
                || requestorNameLength >= 2
                || (searchCriteria.RequestorTypeId != 0 && searchCriteria.PatientDob.HasValue)
                || !(searchCriteria.RequestorTypeId == 0 && !searchCriteria.ActiveRequestor.HasValue))
            {
                return true;
            }
            return false;
        }

        public static int CalculatePatientEpnLength(string PatientEPN)
        {
            int patientEpnLength = PatientEPN.Length;
            if (UserData.Instance.EpnPrefix != null)
            {
                if (!string.IsNullOrEmpty(UserData.Instance.EpnPrefix) && PatientEPN.StartsWith(UserData.Instance.EpnPrefix.Trim()))
                {
                    patientEpnLength = PatientEPN.Substring(UserData.Instance.EpnPrefix.Length).Length;
                }
            }
            return patientEpnLength;           
        }

        /// <summary>
        /// Validates requestor info.
        /// </summary>
        /// <param name="requestor"></param>
        /// <returns></returns>
        public bool Validate(RequestorDetails requestor)
        {
            ValidatePrimaryFields(requestor);
            return NoErrors;
        }

        /// <summary>
        /// Validating search criteria and showing error details.
        /// </summary>
        /// <param name="requestor"></param>
        /// <returns></returns>
        public bool ValidateSearchCriteria(FindRequestorCriteria searchCriteria)
        {
            if (searchCriteria.RequestorTypeId == -1)
            {
                ValidatePrimaryFields(searchCriteria.LastName, ROIConstants.NameValidation, ROIErrorCodes.InvalidLastName);
                ValidatePrimaryFields(searchCriteria.FirstName, ROIConstants.NameValidation, ROIErrorCodes.InvalidFirstName);
            }
            else
            {
                ValidatePrimaryFields(searchCriteria.Name, ROIConstants.NameValidation, ROIErrorCodes.InvalidName);
            }

            int reqFirstNameLength = (searchCriteria.FirstName != null) ? searchCriteria.FirstName.Length : 0;
            int reqLastNameLength = (searchCriteria.LastName != null) ? searchCriteria.LastName.Length : 0;

            if (!string.IsNullOrEmpty(searchCriteria.LastName) || (!string.IsNullOrEmpty(searchCriteria.FirstName)))
            {
                if (reqFirstNameLength + reqLastNameLength < 3)
                {
                    // Modified for CR# 344550
                    if (!string.IsNullOrEmpty(searchCriteria.FirstName) && !string.IsNullOrEmpty(searchCriteria.LastName))
                    {
                        AddError(ROIErrorCodes.RequestorFirstLastNameLength);
                        AddError(ROIErrorCodes.RequestorLastFirstNameLength);
                    }
                    else if (!string.IsNullOrEmpty(searchCriteria.FirstName))
                    {
                        AddError(ROIErrorCodes.RequestorFirstNameLength);
                    }
                    else if (!string.IsNullOrEmpty(searchCriteria.LastName))
                    {
                        AddError(ROIErrorCodes.RequestorLastNameLength);
                    }
                    //---------------
                }
            }

            //ValidatePrimaryFields(searchCriteria.PatientSSN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidSsn);
            ValidatePrimaryFields(searchCriteria.PatientEPN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidEpn);
            //ValidatePrimaryFields(searchCriteria.PatientMRN, ROIConstants.SsnEpnValidation, ROIErrorCodes.RequestorMrnLengthLimit);
            ValidatePrimaryFields(searchCriteria.PatientMRN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidMrn);
            ValidatePrimaryFields(searchCriteria.PatientFacilityCode, ROIConstants.NameValidation, ROIErrorCodes.InvalidName);
            
            if (!string.IsNullOrEmpty(searchCriteria.PatientMRN))
            {
                if (searchCriteria.PatientMRN.Length < 3)
                {
                    AddError(ROIErrorCodes.RequestorMrnLengthLimit);
                }
            }

            if((UserData.Instance.EpnEnabled) && (!string.IsNullOrEmpty(searchCriteria.PatientEPN)))
            {   
                if (CalculatePatientEpnLength(searchCriteria.PatientEPN) < 3)
                {
                    AddError(ROIErrorCodes.RequestorPatientEpnLenghtLimit);
                }
            }

            if (!string.IsNullOrEmpty(searchCriteria.PatientSSN))
            {
                if (searchCriteria.PatientSSN.Split('-', ' ')[0].Trim().Length < 3)
                {
                    AddError(ROIErrorCodes.InvalidSsnSearch);
                }
            }

            if (searchCriteria.PatientDob != null && searchCriteria.PatientDob > DateTime.Now.Date)
            {
                AddError(ROIErrorCodes.IncorrectDate);
            } 

            return NoErrors;
        }
         
        /// <summary>
        /// Validates requestor info.
        /// </summary>
        /// <param name="requestor"></param>
        private void ValidatePrimaryFields(RequestorDetails requestor)
        {
            if (requestor.IsPatientRequestor)
            {
                if (requestor.FirstName.Length == 0 || (!Validator.Validate(requestor.FirstName, ROIConstants.NameValidation)))
                {
                    AddError(ROIErrorCodes.RequestorFirstNameEmpty);
                }
                if (requestor.LastName.Length == 0 || (!Validator.Validate(requestor.LastName, ROIConstants.NameValidation)))
                {
                    AddError(ROIErrorCodes.RequestorLastNameEmpty);
                }
                if (requestor.FirstName.Length > RequestorNameMaxLength)
                {
                    AddError(ROIErrorCodes.RequestorFirstNameMaxLength);
                }
                if (requestor.LastName.Length > RequestorNameMaxLength)
                {
                    AddError(ROIErrorCodes.RequestorLastNameMaxLength);
                }
            }

            else
            {
                if (requestor.Name.Length == 0 || (!Validator.Validate(requestor.Name, ROIConstants.NameValidation)))
                {
                    AddError(ROIErrorCodes.RequestorNameEmpty);
                }
                if (requestor.Name.Length > RequestorNameMaxLength)
                {
                    AddError(ROIErrorCodes.RequestorNameMaxLength);
                }
            }

            ValidatePrimaryFields(requestor.PatientSSN, ROIConstants.SsnValidation, ROIErrorCodes.InvalidSsn);
            ValidatePrimaryFields(requestor.PatientMRN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidMrn);
            ValidatePrimaryFields(requestor.PatientEPN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidEpn);
            ValidatePrimaryFields(requestor.PatientFacilityCode, ROIConstants.FacilityValidation, ROIErrorCodes.InvalidFacility);
            ValidatePrimaryFields(requestor.AltAddress.City, ROIConstants.CityValidation, ROIErrorCodes.InvalidAltCity);
            ValidatePrimaryFields(requestor.AltAddress.State, ROIConstants.Alphanumeric, ROIErrorCodes.InvalidAltState);
            //ValidatePrimaryFields(requestor.AltAddress.PostalCode, ROIConstants.ZipValidation, ROIErrorCodes.InvalidAltZip);
            ValidatePrimaryFields(requestor.MainAddress.City, ROIConstants.CityValidation, ROIErrorCodes.InvalidMainCity);
            ValidatePrimaryFields(requestor.MainAddress.State, ROIConstants.Alphanumeric, ROIErrorCodes.InvalidMainState);
            //ValidatePrimaryFields(requestor.MainAddress.PostalCode, ROIConstants.ZipValidation, ROIErrorCodes.InvalidMainZip);

            ValidatePrimaryFields(requestor.HomePhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidHomePhone);
            ValidatePrimaryFields(requestor.WorkPhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidWorkPhone );
            ValidatePrimaryFields(requestor.CellPhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidCellPhone );
            ValidatePrimaryFields(requestor.Email, ROIConstants.EmailValidation, ROIErrorCodes.InvalidEmail);
            ValidatePrimaryFields(requestor.Fax, ROIConstants.FaxValidation, ROIErrorCodes.InvalidFax);

            ValidatePrimaryFields(requestor.ContactName, ROIConstants.NameValidation, ROIErrorCodes.InvalidContactName);
            ValidatePrimaryFields(requestor.ContactPhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidContactPhone);
            ValidatePrimaryFields(requestor.ContactEmail, ROIConstants.EmailValidation, ROIErrorCodes.InvalidContactEmail);            

            if (requestor.Type == 0)
            {
                AddError(ROIErrorCodes.RequestorTypeEmpty);
            }

            if (requestor.Dob != null)
            {
                if (!ROIViewUtility.IsValidFormat(requestor.Dob))
                {
                    AddError(ROIErrorCodes.InvalidDate);
                }
                else if(!ROIViewUtility.IsValidDate(requestor.Dob))
                {
                    AddError(ROIErrorCodes.InvalidDateValue);
                }
                else
                {
                    requestor.PatientDob = Convert.ToDateTime(requestor.Dob, System.Globalization.CultureInfo.InvariantCulture);
                }
            }           

            if (requestor.PatientDob != null && requestor.PatientDob > DateTime.Now.Date)
            {
                AddError(ROIErrorCodes.IncorrectDate);
            }                     
        }

        private void ValidatePrimaryFields(string field, string validCharSet, string errorCode)
        {
            if (!Validator.Validate(field, validCharSet))
            {
                AddError(errorCode);
            }
        }
    }
}
