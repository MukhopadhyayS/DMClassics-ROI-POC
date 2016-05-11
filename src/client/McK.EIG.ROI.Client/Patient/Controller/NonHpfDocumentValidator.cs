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
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    /// <summary>
    /// Class Validate the NonHpfDocument Information.
    /// </summary>
    public partial class PatientValidator : BaseROIValidator
    {
        #region Methods

        /// <summary>
        /// Validates the NonHpfDocument info primary fields.
        /// </summary>
        /// <param name="nonHpfDocument"></param>
        public void ValidatePrimaryFields(NonHpfDocumentDetails nonHpfDocument)
        {
            if (nonHpfDocument.PageCount <= 0)
            {
                AddError(ROIErrorCodes.NonHpfDocumentInvalidPageCount);
            }

            //if (!nonHpfDocument.DateOfService.HasValue)
            //{
            //    AddError(ROIErrorCodes.NonHpfDocumentDateOfServiceEmpty);
            //}

            if (nonHpfDocument.FacilityCode.Length == 0)
            {
                AddError(ROIErrorCodes.NonHpfDocumentFacilityEmpty);
            }

            if (nonHpfDocument.DocType.Length == 0)
            {
                AddError(ROIErrorCodes.NonHpfDocumentNameEmpty);
            }

            if (nonHpfDocument.DateOfService != null && nonHpfDocument.DateOfService > DateTime.Now.Date)
            {
                AddError(ROIErrorCodes.InvalidDateOfService);
            }

            if (!Validator.Validate(nonHpfDocument.Subtitle, ROIConstants.NameValidation))
            {
                AddError(ROIErrorCodes.InvalidName);
            }

            if (!Validator.Validate(nonHpfDocument.Encounter, ROIConstants.EncounterValidation))
            {
                AddError(ROIErrorCodes.InvalidEncounter);
            }
            ValidatePrimaryFields(nonHpfDocument.FacilityCode, ROIConstants.FacilityValidation, ROIErrorCodes.InvalidFacility);
        }

        /// <summary>
        /// Validates the fields before updating NonHpfDocument.
        /// </summary>
        /// <param name="nonHpfDocument"></param>
        /// <returns></returns>
        public bool Validate(NonHpfDocumentDetails nonHpfDocument)
        {
            ValidatePrimaryFields(nonHpfDocument);
            return NoErrors;
        }

        /// <summary>
        /// validates the fields before updating NonHpfDocument.
        /// </summary>
        /// <param name="nonHpfDocuments"></param>
        /// <returns></returns>
        public void Validate(IList<BaseRecordItem> nonHpfDocuments)
        {
            foreach (NonHpfDocumentDetails doc in nonHpfDocuments)
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
