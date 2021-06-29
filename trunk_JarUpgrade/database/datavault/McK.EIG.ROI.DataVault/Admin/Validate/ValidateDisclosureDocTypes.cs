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
using System.Collections.ObjectModel;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateDisclosureDocTypes:IVault
    {
        #region Fields

        //DataBase Fields
        private const string DisclosureCodeSet     = "Code Set";
        private const string DisclosureDocTypeName = "Document Type";
        private const string DisclosureDocRequired = "Disclosure_Doc_Req";
        private const string AuthorizationRequired = "Auth_Req";

        private Hashtable codeSetHT;
        private Hashtable documentDetailsHT;
        private Hashtable disclosureDocDetailsHT;

        private VaultMode vaultModeType;

        #endregion

        #region Constructor

        public ValidateDisclosureDocTypes()
        {
            Init();
            //Gets all CodeSet Details
            CacheAllCodeSets();
            //Get document for all Codeset
            CacheAllDocumentDetails();
        }

        private void Init()
        {
            codeSetHT = new Hashtable(new VaultComparer());
            documentDetailsHT = new Hashtable(new VaultComparer());
            disclosureDocDetailsHT = new Hashtable(new VaultComparer());
        }
        #endregion

        #region Methods

        /// <summary>
        /// System Load the Disclosure Document Types Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            long recordCount           = 1;
            bool isHeaderExist         = false;
            StringBuilder errorMessage = new StringBuilder();

            while (reader.Read())
            {
                errorMessage.Remove(0, errorMessage.ToString().Length);
                try
                {
                    try
                    {
                        string codeSetName;
                        string docName;
                        bool isDisclosureDocReq;
                        bool isAuthRequired;                     
                        codeSetName = Convert.ToString(reader[DisclosureCodeSet], CultureInfo.CurrentCulture);
                        docName = Convert.ToString(reader[DisclosureDocTypeName], CultureInfo.CurrentCulture);
                        isDisclosureDocReq = (Convert.ToString(reader[DisclosureDocRequired], CultureInfo.CurrentCulture).Trim().Length == 0)
                                             ? false
                                             : Convert.ToBoolean(reader[DisclosureDocRequired], CultureInfo.CurrentCulture);
                        isAuthRequired = (Convert.ToString(reader[AuthorizationRequired], CultureInfo.CurrentCulture).Trim().Length == 0)
                                             ? false
                                             : Convert.ToBoolean(reader[AuthorizationRequired], CultureInfo.CurrentCulture);
                        
                        long codeSetId = GetCodeSetID(codeSetName, recordCount, errorMessage);

                        ROIAdminValidator validator = new ROIAdminValidator();
                        bool checkCodeSet = validator.ValidateCodeSet(codeSetId);
                        if (!checkCodeSet)
                        {
                            throw new VaultException(DataVaultErrorCodes.InvalidCodeSetId);
                        }

                        DocumentTypesDetails docDetails = GetDocumentTypeDetails(codeSetId, docName, errorMessage, recordCount);
                        if (docDetails != null)
                        {
                            UpdateDocDetails(docDetails, isDisclosureDocReq, isAuthRequired);
                        }

                        //bool checkAuthorization=validator.ValidateAuthorization(
                    }
                    catch (ArgumentException cause)
                    {
                        errorMessage.Append(cause.Message);
                    }
                    catch (InvalidCastException cause)
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
                            "Disclosure Document Types",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(string.Empty, recordCount, cause.Message);
                }               
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            ValidateDisclosureDocDetail();
            return null;
        }

        /// <summary>
        /// Return all CodeSet Details in the HashTable.
        /// </summary>
        /// <returns></returns>
        private void CacheAllCodeSets()
        {
            Collection<CodeSetDetails> codeSetDetails = ROIAdminController.Instance.RetrieveAllCodeSets();
            if (codeSetDetails.Count != 0)
            {
                foreach (CodeSetDetails details in codeSetDetails)
                {
                    codeSetHT.Add(details.Description.Trim().ToLower(CultureInfo.CurrentCulture), details.Id);
                }
            }
        }

        /// <summary>
        /// Method get all CodeSet Document Details.
        /// </summary>
        /// <param name="codeSetHT">CodeSet Details</param>
        /// <returns>void</returns>
        private void CacheAllDocumentDetails()
        {
            foreach (string key in codeSetHT.Keys)
            {
                Collection<DocumentTypesDetails> docDetails = ROIAdminController.Instance.RetrieveDocumentTypes(Convert.ToInt64(codeSetHT[key], CultureInfo.CurrentCulture));
                documentDetailsHT.Add(codeSetHT[key], GetDocumentDetails(docDetails));
            }
        }

        /// <summary>
        /// Method get Document details of the specified codeset.
        /// </summary>
        /// <param name="docTypeDetails">DocumentTypeDetails.</param>
        /// <returns>CodeSet details hashtable.</returns>
        private static Hashtable GetDocumentDetails(Collection<DocumentTypesDetails> docTypeList)
        {
            Hashtable documentTypes = new Hashtable(new VaultComparer());
            foreach (DocumentTypesDetails document in docTypeList)
            {
                documentTypes.Add(document.Name.Trim().ToLower(CultureInfo.CurrentCulture), document);
            }
            return documentTypes;
        }

        /// <summary>
        /// Method return the CodeSetId for giver CodeSetName.
        /// </summary>
        /// <param name="codeSetName">CodeSetName</param>
        /// <returns>CodeSetId</returns>
        private long GetCodeSetID(string codeSetName, long recordCount, StringBuilder errorMessage)
        {
            if (!codeSetHT.ContainsKey(codeSetName))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.UnknownCodeSet, codeSetName, recordCount);
                errorMessage.Append(message);
                return 0;
            }
            return Convert.ToInt64(codeSetHT[codeSetName], CultureInfo.CurrentCulture);
        }

        private DocumentTypesDetails GetDocumentTypeDetails(long codeSetId, string docName, StringBuilder errorMessage, long recordCount)
        {
            Hashtable docDetailsHT = (Hashtable)documentDetailsHT[codeSetId];
            if (!docDetailsHT.ContainsKey(docName))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.UnknownDocumentName, docName, recordCount);
                errorMessage.Append(message);
                return null;
            }
            DocumentTypesDetails details = (DocumentTypesDetails)docDetailsHT[docName];
            details.CodeSetId = codeSetId;
            return details;
        }

        /// <summary>
        /// Method update the DocumentTypeDetails and add it to the collection.
        /// </summary>
        /// <param name="documentTypeDetails">DocumentTypeDetails</param>
        /// <param name="isDisclosure">IsDisclosure.</param>
        private void UpdateDocDetails(DocumentTypesDetails documentTypeDetails, bool isDisclosure, bool isAuthorization)
        {
            documentTypeDetails.IsDisclosure = isDisclosure;
            documentTypeDetails.Type = DocumentDesignationType.Disclosure;
            documentTypeDetails.IsAuthorization = isAuthorization;
            documentTypeDetails.IsDisclosure = isDisclosure;

            if (!disclosureDocDetailsHT.Contains(documentTypeDetails.CodeSetId))
            {
                Collection<DocumentTypesDetails> documentDetails = new Collection<DocumentTypesDetails>();
                documentDetails.Add(documentTypeDetails);
                disclosureDocDetailsHT.Add(documentTypeDetails.CodeSetId, documentDetails);
            }
            else
            {
                Collection<DocumentTypesDetails> docTypeDetail = (Collection<DocumentTypesDetails>)disclosureDocDetailsHT[documentTypeDetails.CodeSetId];
                docTypeDetail.Add(documentTypeDetails);
            }
        }

        /// <summary>
        /// Passes the Disclosure Document Type details object to the ROIAdminController for further process
        /// </summary>
        private void ValidateDisclosureDocDetail()
        {
            foreach (long key in disclosureDocDetailsHT.Keys)
            {                
                Collection<DocumentTypesDetails> details = (Collection<DocumentTypesDetails>)disclosureDocDetailsHT[key];
                ROIAdminValidator validator = new ROIAdminValidator();
                bool check = validator.ValidateAuthorization(details);
                if (!check)
                {
                    string codeSetkey = GetCodeSetKey(key);
                    ValidateUtility.WriteLog(codeSetkey, 0, Utility.GetErrorMessage(validator.ClientException));
                }
            }
        }

        private string GetCodeSetKey(long value)
        {
            foreach (string key in codeSetHT.Keys)
            {
                if (Convert.ToInt64(codeSetHT[key], CultureInfo.CurrentCulture) == value)
                {
                    return key;
                }
            }
            return string.Empty;
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
                return DataVaultConstants.DisclosureDocType;
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
