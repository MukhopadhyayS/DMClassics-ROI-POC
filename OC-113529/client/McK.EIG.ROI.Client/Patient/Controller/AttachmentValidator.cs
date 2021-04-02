#region Copyright © 2008 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
 * Use of this material is governed by a license agreement. 
 * This material contains confidential, proprietary and trade 
 * secret information of McKesson Information Solutions and is 
 * protected under United States and international copyright and 
 * other intellectual property laws. Use, disclosure, 
 * reproduction, modification, distribution, or storage in a 
 * retrieval system in any form or by any means is prohibited 
 * without the prior express written permission of McKesson 
 * Information Solutions.
 */
#endregion

using System;
using System.Collections.ObjectModel;
using System.Collections.Generic;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    /// <summary>
    /// Class Validate the Attachment Information.
    /// </summary>
    public partial class PatientValidator : BaseROIValidator
    {
        #region Methods

        /// <summary>
        /// Validates the NonHpfAttachment info primary fields.
        /// </summary>
        /// <param name="tmpAttachment"></param>
        public void ValidatePrimaryFields(AttachmentDetails tmpAttachment)
        {

            if (tmpAttachment.AttachmentType.Length == 0)
            {
                AddError(ROIErrorCodes.AttachmentTypeEmpty);
            }

            if (!Validator.Validate(tmpAttachment.Subtitle, ROIConstants.NameValidation))
            {
                AddError(ROIErrorCodes.AttachmentInvalidTitle);
            }

            if (tmpAttachment.DateOfService != null && tmpAttachment.DateOfService > DateTime.Now.Date)
            {
                AddError(ROIErrorCodes.InvalidDateOfService);
            }

            if (tmpAttachment.FacilityCode.Length == 0)
            {
                AddError(ROIErrorCodes.AttachmentFacilityEmpty);
            }

            if (!Validator.Validate(tmpAttachment.Encounter, ROIConstants.EncounterValidation))
            {
                AddError(ROIErrorCodes.AttachmentInvalidEncounter);
            }

            if (tmpAttachment.PageCount <= 0 ||
                tmpAttachment.PageCount != tmpAttachment.FileDetails.PageCount)
            {
                AddError(ROIErrorCodes.AttachmentInvalidPageCount);
            }

            if (tmpAttachment.FileDetails.Uuid.Length == 0)
            {
                AddError(ROIErrorCodes.AttachmentNoServerFile);
            }

            if (tmpAttachment.FileDetails.FileName.Length == 0)
            {
                AddError(ROIErrorCodes.AttachmentFileNameEmpty);
            }

            if (tmpAttachment.FileDetails.Extension.Length == 0)
            {
                AddError(ROIErrorCodes.AttachmentExtensionEmpty);
            }

            ValidatePrimaryFields(tmpAttachment.FacilityCode, ROIConstants.FacilityValidation, ROIErrorCodes.InvalidFacility);
        }

        /// <summary>
        /// Validates the fields before updating Attachment.
        /// </summary>
        /// <param name="tmpAttachment"></param>
        /// <returns></returns>
        public bool Validate(AttachmentDetails tmpAttachment)
        {
            ValidatePrimaryFields(tmpAttachment);
            return NoErrors;
        }

        /// <summary>
        /// validates the fields before updating Attachments.
        /// </summary>
        /// <param name="tmpAttachments"></param>
        /// <returns></returns>
        /// 
        public void ValidateAttachments(IList<BaseRecordItem> tmpAttachments)
        {
            foreach (AttachmentDetails doc in tmpAttachments)
            {
                if (!Validate(doc))
                {
                    return;
                }
            }
        }

        #endregion
    }
}
