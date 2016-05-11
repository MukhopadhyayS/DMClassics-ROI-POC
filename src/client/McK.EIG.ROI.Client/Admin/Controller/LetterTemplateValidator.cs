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
using System.IO;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminValidator
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ROIAdminValidator));

        private const string FileFormat = ".docx";

        private FileStream fileStream;

        #endregion

        #region Methods

        /// <summary>
        /// Validates the primary fields for letter template
        /// </summary>
        /// <param name="letterTemplate"></param>
        public void ValidatePrimaryFields(LetterTemplateDetails letterTemplate)
        {
            if (letterTemplate.LetterType == LetterType.None)
            {
                AddError(ROIErrorCodes.LetterTemplateLetterTypeEmpty);
            }
            
            if (letterTemplate.LetterType == LetterType.Other && letterTemplate.IsDefault == true)
            {
                AddError(ROIErrorCodes.LetterTemplateTypeOtherSelected);
            }

            if (letterTemplate.Name.Length == 0 || (!Validator.Validate(letterTemplate.Name, ROIConstants.NameValidation)))
            {                
                AddError(ROIErrorCodes.LetterTemplateNameEmpty);
            }

            if (letterTemplate.Name.Length > LetterTemplateNameMaxLength)
            {
                AddError(ROIErrorCodes.LetterTemplateNameMaxLength);
            }

            if (letterTemplate.Description.Length > LetterTemplateDescriptionMaxLength)
            {
                AddError(ROIErrorCodes.LetterTemplateDescriptionMaxLength);
            }            
        }

        /// <summary>
        /// Validate the File fields.
        /// </summary>
        /// <param name="letterTemplate"></param>
        private void ValidateFileFields(LetterTemplateDetails letterTemplate)
        {
            if (letterTemplate.FilePath.Length == 0 || string.IsNullOrEmpty(letterTemplate.FileName))
            {
                AddError(ROIErrorCodes.LetterTemplateFilePathEmpty);
            }
            else if (letterTemplate.FileName.Length > LetterTemplateFileNameMaxLength)
            {
                AddError(ROIErrorCodes.LetterTemplateFileNameMaxLength);
            }
            if (!File.Exists(letterTemplate.FilePath))
            {
                AddError(ROIErrorCodes.LetterTemplateInvalidFilePath);
            }
            else
            {
                if (!string.IsNullOrEmpty(letterTemplate.FilePath) && Validator.Validate(letterTemplate.FilePath, ROIConstants.FilepathValidation))
                {
                    FileInfo file = new FileInfo(letterTemplate.FilePath);

                    if (file.Length == 0)
                    {
                        AddError(ROIErrorCodes.FileSizeIsZero);
                    }
                  
                    int i = string.Compare(file.Extension, FileFormat, true, System.Threading.Thread.CurrentThread.CurrentCulture);
                    if (i != 0)
                    {
                        AddError(ROIErrorCodes.LetterTemplateInvalidFileFormat);
                    }
                    try
                    {
                        fileStream = file.Open(FileMode.Open);
                    }
                    catch (IOException cause)
                    {
                        log.FunctionFailure(cause);
                        AddError(ROIErrorCodes.FileAlreadyOpen);
                    }
                    catch (UnauthorizedAccessException cause)
                    {
                        log.FunctionFailure(cause);
                        AddError(ROIErrorCodes.FileIsReadOnly);
                    }
                    finally
                    {
                        if (fileStream != null)
                        {
                            fileStream.Close();
                        }
                    }
                 }                
            }
        }

        /// <summary>
        /// Validates the fields before creating new letter template.
        /// </summary>
        /// <param name="letterTemplate">LetterTemplateDetails</param>
        /// <returns>Returns true if validation is successfull</returns>
        public bool ValidateCreate(LetterTemplateDetails letterTemplate)
        {
            ValidatePrimaryFields(letterTemplate);
            ValidateFileFields(letterTemplate);
            return NoErrors;
        }

        /// <summary>
        /// Validates the fields before updating the letter template.
        /// </summary>
        /// <param name="letterTemplate">LetterTemplateDetails</param>
        /// <returns>Returns true if validation is successfull</returns>
        public bool ValidateUpdate(LetterTemplateDetails letterTemplate)
        {
            ValidatePrimaryFields(letterTemplate);
            if (letterTemplate.DoUpload)
            {
                ValidateFileFields(letterTemplate);
            }
            return NoErrors;
        }

        #endregion
    }
}
