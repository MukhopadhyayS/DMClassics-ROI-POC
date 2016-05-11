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
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminValidator
    {
        #region Methods
        /// <summary>
        /// Validates the primary fields for configure notes.
        /// </summary>
        /// <param name="configureNotes"></param>
        private void ValidatePrimaryFields(NotesDetails notesDetails)
        {
            notesDetails.Name = notesDetails.Name.Trim();
            notesDetails.DisplayText = notesDetails.DisplayText.Trim();

            if (notesDetails.Name.Length == 0 || (!Validator.Validate(notesDetails.Name, ROIConstants.NameValidation)))
            {
                AddError(ROIErrorCodes.ConfigureNotesNameEmpty);
            }
            if (notesDetails.Name.Length > NotesNameMaxLength)
            {
                AddError(ROIErrorCodes.ConfigureNotesNameMaxLength);
            }
            if (notesDetails.DisplayText.Length == 0)
            {
                AddError(ROIErrorCodes.ConfigureNotesDisplayTextEmpty);
            }
            if (notesDetails.DisplayText.Length > NotesDisplayTextMaxLength)
            {
                AddError(ROIErrorCodes.ConfigureNotesDisplayTextMaxLength);
            }            
        }


        /// <summary>
        /// Validate the fields before creating configure notes..
        /// </summary>
        /// <param name="configureNotes"></param>
        /// <returns></returns>
        public bool ValidateCreate(NotesDetails notesDetails)
        {
            ValidatePrimaryFields(notesDetails);
            return NoErrors;
        }

        /// <summary>
        /// Validate the fields before upating  configure notes.
        /// </summary>
        /// <param name="configureNotes"></param>
        /// <returns></returns>
        public bool ValidateUpdate(NotesDetails notesDetails)
        {
            ValidatePrimaryFields(notesDetails);
            return NoErrors;
        }

        #endregion
    }
}
