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
using System.Globalization;
using System.Text.RegularExpressions;

using McK.EIG.ROI.Client.Base.Controller;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Request.Controller
{
    public class RequestValidator : BaseROIValidator
    {
        #region Fields

        private const int CommentDescriptionMaxLength = 1000;
        private const int PatientMinimumLength = 3;
        private const int MinimumLength = 2;

        #endregion

        #region Methods

        public bool ValidateRequest(RequestDetails request)
        {
            ValidateRequestInfo(request);
            
            return NoErrors;
        }

        /// <summary>
        /// Validates request info.
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        public bool ValidateRequestInfo(RequestDetails request)
        {
			// Needs to be revisited.
            if (request.RequestReason == null)
            {
                AddError(ROIErrorCodes.RequestReasonEmpty);
            }

            if (request.ReceiptDateText != null)
            {
                    request.ReceiptDate = Convert.ToDateTime(request.ReceiptDateText, System.Globalization.CultureInfo.InvariantCulture);
             
            }             

            if (request.ReceiptDate != null && request.ReceiptDate > DateTime.Now)
            {
                AddError(ROIErrorCodes.RequestInvalidReceiptDate);
            }

            if ((!request.ReceiptDate.HasValue) && (string.IsNullOrEmpty(request.ReceiptDateText)))
            {
                AddError(ROIErrorCodes.RequestReceiptDateEmpty);
            }

            if (request.Requestor == null)
            {
                AddError(ROIErrorCodes.RequestRequestorNameEmpty);
            }
            else
            {
                if (!request.Requestor.IsActive)
                {
                    AddError(ROIErrorCodes.RequestorIsInactive);
                }
            }

            if (request.Status == RequestStatus.Canceled || request.Status == RequestStatus.Pended || request.Status == RequestStatus.Denied)
            {
                if (request.StatusReason == null || request.StatusReason.Length == 0)
                {
                    AddError(ROIErrorCodes.RequestCurrentReasonEmpty);
                }
            }

            ValidatePrimaryFields(request.RequestorHomePhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidHomePhone);
            ValidatePrimaryFields(request.RequestorWorkPhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidWorkPhone);
            ValidatePrimaryFields(request.RequestorCellPhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidCellPhone);
            ValidatePrimaryFields(request.RequestorFax, ROIConstants.FaxValidation, ROIErrorCodes.InvalidFax);
            ValidatePrimaryFields(request.RequestorContactName, ROIConstants.NameValidation, ROIErrorCodes.InvalidContactName);
            ValidatePrimaryFields(request.RequestorContactPhone, ROIConstants.PhoneValidation, ROIErrorCodes.InvalidContactPhone);

            return NoErrors;
        }

        /// <summary>
        /// Validate Comment detatils.
        /// </summary>
        /// <param name="commentDetails"></param>
        /// <returns></returns>
        public bool ValidateComment(CommentDetails commentDetails)
        {
            ValidatePrimaryFields(commentDetails);
            return NoErrors;
        }

        internal bool ValidateSearchFields(FindRequestCriteria searchCriteria)
        {
            ValidatePrimaryFields(searchCriteria.PatientLastName, ROIConstants.NameValidation, ROIErrorCodes.InvalidLastName);
            ValidatePrimaryFields(searchCriteria.PatientFirstName, ROIConstants.NameValidation, ROIErrorCodes.InvalidFirstName);
            //ValidatePrimaryFields(searchCriteria.PatientSsn, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidSsn);
            ValidatePrimaryFields(searchCriteria.MRN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidMrn);
            ValidatePrimaryFields(searchCriteria.Encounter, ROIConstants.NameValidation, ROIErrorCodes.InvalidEncounter);
            ValidatePrimaryFields(searchCriteria.EPN, ROIConstants.SsnEpnValidation, ROIErrorCodes.InvalidEpn);
            ValidatePrimaryFields(searchCriteria.RequestorName, ROIConstants.NameValidation, ROIErrorCodes.InvalidRequestorName);
            ValidatePrimaryFields(searchCriteria.InvoiceNumber, ROIConstants.Numeric, ROIErrorCodes.InvalidInvoiceNumber);
            ValidatePrimaryFields(searchCriteria.RequestId, ROIConstants.Numeric, ROIErrorCodes.InvalidRequestId);

            if (!string.IsNullOrEmpty(searchCriteria.BalanceDue))
            {
                double amount;
                if (!double.TryParse(searchCriteria.BalanceDue, out amount) ||
                   !Regex.IsMatch(Convert.ToString(amount, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.AmountFormat))
                {
                   AddError(ROIErrorCodes.InvalidBalanceDue);                    
                }
            }
            if (!string.IsNullOrEmpty(searchCriteria.PatientSsn))
            {
                if (searchCriteria.PatientSsn.Split('-',' ')[0].Trim().Length < 3)
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
        ///  Validate Comment detatils.
        /// </summary>
        /// <param name="commentDetails"></param>
        private void ValidatePrimaryFields(CommentDetails commentDetails)
        {
            if (string.IsNullOrEmpty(commentDetails.EventRemarks))
            {
                AddError(ROIErrorCodes.RequestCommentEmpty);
            }
            else if (commentDetails.EventRemarks.Length > CommentDescriptionMaxLength)
            {
                AddError(ROIErrorCodes.RequestCommentMaxLength);
            }
        }

        /// <summary>
        /// Validate the Find Request Search Criteria.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public static bool ValidatePrimarySearchFields(FindRequestCriteria searchCriteria)
        {
            int lastNameLength      = (searchCriteria.PatientLastName != null) ? searchCriteria.PatientLastName.Length : 0;
            int firstNameLength     = (searchCriteria.PatientFirstName != null) ? searchCriteria.PatientFirstName.Length : 0;
            int dobLength           = (searchCriteria.Dob.HasValue) ? searchCriteria.Dob.Value.ToString().Length : 0;
            int fromDateLength      = (searchCriteria.FromDate.HasValue) ? searchCriteria.FromDate.Value.ToString().Length : 0;
            int toDateLength        = (searchCriteria.ToDate.HasValue) ? searchCriteria.ToDate.Value.ToString().Length : 0;
            int ssnLength           = (searchCriteria.PatientSsn != null) ? searchCriteria.PatientSsn.Trim('-', ' ').Length : 0;
            int mrnLength           = (searchCriteria.MRN != null) ? searchCriteria.MRN.Length : 0;
            int epnLength           = (searchCriteria.EPN != null) ? searchCriteria.EPN.Length : 0;
            int encounterLength     = (searchCriteria.Encounter != null) ? searchCriteria.Encounter.Length : 0;
            int requestorNameLength = (searchCriteria.RequestorName != null) ? searchCriteria.RequestorName.Length : 0;
            long requestorType      = searchCriteria.RequestorType;            
            string requestStatus    = searchCriteria.RequestStatus;
            int balanceDue          = (searchCriteria.BalanceDue != null) ? searchCriteria.BalanceDue.Length : 0;
            string requestReason    = searchCriteria.RequestReason;
            string invoiceNumber    = (searchCriteria.InvoiceNumber != null) ? searchCriteria.InvoiceNumber : string.Empty;
            string requestId        = (searchCriteria.RequestId != null) ? searchCriteria.RequestId : string.Empty;
            string facility         =  searchCriteria.Facility;
            
            int customFromDate      = (searchCriteria.CustomFromDate != null) ? searchCriteria.CustomFromDate.Length : 0;
            int customToDate        = (searchCriteria.CustomToDate != null) ? searchCriteria.CustomToDate.Length : 0;
            int completedFromDateLength = (searchCriteria.CompletedFromDate != null) ? searchCriteria.CompletedFromDate.Length : 0;
            int completedToDateLength   = (searchCriteria.CompletedToDate != null) ? searchCriteria.CompletedToDate.Length : 0;
            int totalNameLength         = lastNameLength + firstNameLength;


            if (lastNameLength >= PatientMinimumLength
                || firstNameLength >= PatientMinimumLength
                || (dobLength > 0 && (facility != null))
                || (fromDateLength > 0 && toDateLength > 0)
                || (completedFromDateLength > 0 && completedToDateLength > 0)
                || requestorType != 0
                //validate the request status search condition
                || (requestStatus != null && (!requestStatus.Equals("None"))) 
                || ssnLength >= PatientMinimumLength
                || mrnLength >= PatientMinimumLength
                || epnLength >= PatientMinimumLength
                || encounterLength >= PatientMinimumLength
                || requestorNameLength >= MinimumLength
                || invoiceNumber.Length > 0
                || requestId.Length > 0
                || totalNameLength >= PatientMinimumLength
                || balanceDue > 0
                || (customFromDate > 0 && customToDate > 0)
                || requestReason != null)
            {
                return true;
            }
            return false;
        }


        /// <summary>
        /// Validate Comment detatils.
        /// </summary>
        /// <param name="commentDetails"></param>
        /// <returns></returns>
        public bool ValidateRequestStatus(RequestDetails request)
        {
            if (request.Id == 0 && request.Status != RequestStatus.Logged)
            {
                AddError(ROIErrorCodes.InvalidStatusForNewRequest);
            }

            return NoErrors;
        }

     
        #endregion
    }
}
