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
using System.Collections;
using System.Data;
using System.Globalization;
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateLetterTemplates:IVault
    {
        #region Fields

        //DBFields
        private const string RefId = "Ref_ID";
        private const string IsDefault = "Default";
        private const string LetterTemplateType = "Letter_Type";
        private const string LetterName = "Name";
        private const string Description = "Description";
        private const string UploadFilePath = "Upload_File";
        private const string SystemSeed = "IsSystemSeed";


        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Letter Templates Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            LetterTemplateDetails letterTemplateDetails;

            long recordCount = 1;
            bool isHeaderExist = false;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList letterTemplateList = new ArrayList();
            string letterTemplateRefId = string.Empty;

            while (reader.Read())
            {
                errorMessage.Remove(0, errorMessage.ToString().Length);
                try
                {
                    try
                    {
                        letterTemplateRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                        letterTemplateDetails = new LetterTemplateDetails();
                        bool isSystemSeed = (Convert.ToString(reader[SystemSeed],
                                         CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                         false : Convert.ToBoolean(reader[SystemSeed], CultureInfo.CurrentCulture);
                        letterTemplateDetails = new LetterTemplateDetails();

                        letterTemplateDetails.IsDefault = reader[IsDefault].ToString().Trim().Length != 0 ?
                                                            Convert.ToBoolean(reader[IsDefault], CultureInfo.CurrentCulture) :
                                                            false;
                        switch (reader[LetterTemplateType].ToString())
                        {
                            case "Cover Letter":
                                letterTemplateDetails.LetterType = LetterType.CoverLetter;
                                break;
                            case "Invoice":
                                letterTemplateDetails.LetterType = LetterType.Invoice;
                                break;
                            case "None":
                                letterTemplateDetails.LetterType = LetterType.Invoice;
                                break;
                            case "Other":
                                letterTemplateDetails.LetterType = LetterType.Other;
                                break;
                            case "PreBill":
                                letterTemplateDetails.LetterType = LetterType.PreBill;
                                break;
                            default:                                
                                errorMessage.Append("Invalid Letter Type.");
                                break;
                        }
                        letterTemplateDetails.Name = Convert.ToString(reader[LetterName],
                                                                             CultureInfo.CurrentCulture).Trim();
                        letterTemplateDetails.Description = Convert.ToString(reader[Description],
                                                                             CultureInfo.CurrentCulture).Trim();
                        letterTemplateDetails.FilePath = Convert.ToString(reader[UploadFilePath],
                                                                             CultureInfo.CurrentCulture).Trim();


                        if (letterTemplateList.Contains(letterTemplateRefId))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            letterTemplateList.Add(letterTemplateRefId);
                        }
                        

                        //LetterTemplate Validation
                        ROIAdminValidator validator = new ROIAdminValidator();
                        bool check;
                        if (isSystemSeed)
                        {                            
                            check = validator.ValidateUpdate(letterTemplateDetails);
                        }
                        else
                        {
                            letterTemplateDetails.FileName = "Test.rtf";
                            check = validator.ValidateCreate(letterTemplateDetails);
                        }
                        if (!check)
                        {
                            errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                        }
                    }
                    catch (InvalidCastException cause)
                    {
                        errorMessage.Append(cause.Message);
                    }

                    catch (ArgumentException cause)
                    {
                        errorMessage.Append(cause.Message);
                    }

                    if (errorMessage.ToString().Trim().Length > 0)
                    {
                        throw new VaultException(errorMessage.ToString().Trim());
                    }
                }
                catch (VaultException cause)
                {
                    if (!isHeaderExist)
                    {
                        isHeaderExist = true;
                        ValidateUtility.WriteLog("Admin",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateAdminModule : DataVaultConstants.UpdateAdminModule,
                            "Letter Templates",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(letterTemplateRefId, recordCount, cause.Message);
                }                
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get
            {
                return DataVaultConstants.LetterTemplate;
            }
        }

        /// <summary>
        /// Return the mode type.
        /// </summary>
        public VaultMode ModeType
        {
            get
            {
                return vaultModeType;
            }
            set
            {
                vaultModeType = value;
            }
        }


        #endregion
    }
}
