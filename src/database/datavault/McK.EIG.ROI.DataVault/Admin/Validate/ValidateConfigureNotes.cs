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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateConfigureNotes:IVault
    {
        #region Fields

        //DataBase Fields
        private const string RefId = "Ref_ID";
        private const string NotesName = "Name";
        private const string NotesDisplayText = "Display_Text";

        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Configure Notes Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            ArrayList configureNotesList = new ArrayList();
            StringBuilder errorMessage = new StringBuilder();
            long recordCount = 1;
            bool isHeaderExist = false;
            string configureNotesRefID = string.Empty;

            while (reader.Read())
            {
                try
                {
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    NotesDetails notesDetail = new NotesDetails();
                    notesDetail.Name = reader[NotesName].ToString();
                    notesDetail.DisplayText = reader[NotesDisplayText].ToString();

                    configureNotesRefID = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();

                    //Configure Notes Validation
                    ROIAdminValidator validator = new ROIAdminValidator();
                    bool check = validator.ValidateCreate(notesDetail);
                    if (!check)
                    {
                        errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                    }
                    if (configureNotesList.Contains(configureNotesRefID))
                    {
                        errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                    }
                    else
                    {
                        configureNotesList.Add(configureNotesRefID);
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
                            "Configure Notes",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(configureNotesRefID, recordCount, cause.Message);
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
                return DataVaultConstants.ConfigureNotes;
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
