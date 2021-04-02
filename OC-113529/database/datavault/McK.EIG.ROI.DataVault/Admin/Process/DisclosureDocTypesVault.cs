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
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;


namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// DisclosureDocument Type Data Vault
    /// </summary>
    public class DisclosureDocTypesVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(DisclosureDocTypesVault));

        #region Fields

        //DataBase Fields
        private const string DisclosureCodeSet     = "Code Set";
        private const string DisclosureDocTypeName = "Document Type";
        private const string DisclosureDocRequired = "Disclosure_Doc_Req";
        private const string AuthorizationRequired = "Auth_Req";

        private Hashtable codeSetHT;
        private Hashtable documentDetailsHT;
        private Hashtable disclosureDocDetailsHT;

        private VaultMode modeType;

        #endregion

        #region Constructor

        public DisclosureDocTypesVault()
        {
            Init();
            //Gets all CodeSet Details
            CacheAllCodeSets();
            //Get document for all Codeset
            CacheAllDocumentDetails();

        }

        #endregion

        #region Methods

        private void Init()
        {
            codeSetHT = new Hashtable(new VaultComparer());
            documentDetailsHT = new Hashtable(new VaultComparer());
            disclosureDocDetailsHT = new Hashtable(new VaultComparer());
        }

        /// <summary>
        /// System Load the Disclosure Document Type Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {            
            log.EnterFunction();            
            try
            {
                string codeSetName;
                string docName;
                bool isDisclosureDocReq;
                bool isAuthRequired;

                long recordcount = 1;

                while (reader.Read())
                {
                    codeSetName        = Convert.ToString(reader[DisclosureCodeSet], CultureInfo.CurrentCulture);
                    docName            = Convert.ToString(reader[DisclosureDocTypeName], CultureInfo.CurrentCulture);
                    isDisclosureDocReq = (Convert.ToString(reader[DisclosureDocRequired], CultureInfo.CurrentCulture).Trim().Length == 0)
                                         ? false
                                         : Convert.ToBoolean(reader[DisclosureDocRequired], CultureInfo.CurrentCulture);
                    isAuthRequired = (Convert.ToString(reader[AuthorizationRequired], CultureInfo.CurrentCulture).Trim().Length == 0)
                                         ? false
                                         : Convert.ToBoolean(reader[AuthorizationRequired], CultureInfo.CurrentCulture);
                    
                    recordcount++;
                    UpdateDisclosureDetails(codeSetName, docName, isDisclosureDocReq, isAuthRequired, recordcount);
                }
                SaveDisclosureDocDetail();
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (InvalidCastException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (FormatException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            finally
            {
                reader.Close();                
            }
            log.ExitFunction();
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
        /// Method will update the details for the specified codesetName and Documentname.
        /// </summary>
        /// <param name="codeSetName">CodeSetName</param>
        /// <param name="docName">DocumentName</param>
        /// <param name="isDisclosure">isDisclosure</param>
        private void UpdateDisclosureDetails(string codeSetName, string docName, bool isDisclosure, bool isAuthorization, long recordCount)
        {
            long codeSetId = GetCodeSetID(codeSetName, recordCount);
            DocumentTypesDetails documentTypeDetails = GetDocumentTypeDetails(codeSetId, docName, recordCount);
            UpdateDocDetails(documentTypeDetails, isDisclosure, isAuthorization, codeSetId);
        }

        /// <summary>
        /// Method update the DocumentTypeDetails and add it to the collection.
        /// </summary>
        /// <param name="documentTypeDetails">DocumentTypeDetails</param>
        /// <param name="isDisclosure">IsDisclosure.</param>
        private void UpdateDocDetails(DocumentTypesDetails documentTypeDetails, bool isDisclosure, bool isAuthorization, long codeSetId)
        {
            documentTypeDetails.IsDisclosure = isDisclosure;
            documentTypeDetails.Type = DocumentDesignationType.Disclosure;
            documentTypeDetails.IsAuthorization = isAuthorization;
            documentTypeDetails.IsDisclosure = isDisclosure;

            if (!disclosureDocDetailsHT.Contains(codeSetId))
            {
                Collection<DocumentTypesDetails> documentDetails = new Collection<DocumentTypesDetails>();
                documentDetails.Add(documentTypeDetails);
                disclosureDocDetailsHT.Add(codeSetId, documentDetails);
            }
            else
            {
                Collection<DocumentTypesDetails> docTypeDetail = (Collection<DocumentTypesDetails>)disclosureDocDetailsHT[codeSetId];
                docTypeDetail.Add(documentTypeDetails);
            }
        }

        /// <summary>
        /// Method returns the DocumentTypeDetails for the given CodeSetId and DocumentName.
        /// </summary>
        /// <param name="codeSetId">CodeSetId</param>
        /// <param name="docName">Document Name</param>
        /// <returns>DocumentTypeDetails</returns>
        private DocumentTypesDetails GetDocumentTypeDetails(long codeSetId, string docName, long recordCount)
        {
            Hashtable docDetailsHT = (Hashtable)documentDetailsHT[codeSetId];
            if (!docDetailsHT.ContainsKey(docName))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.UnknownDocumentName, docName, recordCount);
                throw new VaultException(message);
            }
            return (DocumentTypesDetails)docDetailsHT[docName];
        }

        /// <summary>
        /// Method return the CodeSetId for giver CodeSetName.
        /// </summary>
        /// <param name="codeSetName">CodeSetName</param>
        /// <returns>CodeSetId</returns>
        private long GetCodeSetID(string codeSetName, long recordCount)
        {
            if (!codeSetHT.ContainsKey(codeSetName))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.UnknownCodeSet, codeSetName, recordCount);
                throw new VaultException(message);
            }
            return Convert.ToInt64(codeSetHT[codeSetName], CultureInfo.CurrentCulture);
        }

        /// <summary>
        /// Passes the Disclosure Document Type details object to the ROIAdminController for further process
        /// </summary>
        private void SaveDisclosureDocDetail()
        {
            log.EnterFunction();
            try
            {
                //Call the ROIAdminController to save the Disclosure Document Type details.
                foreach (long key in disclosureDocDetailsHT.Keys)
                {
                    Collection<DocumentTypesDetails> details = (Collection<DocumentTypesDetails>)disclosureDocDetailsHT[key];
                    long codeSetId = Convert.ToInt64(key, CultureInfo.CurrentCulture);
                    ROIAdminController.Instance.DesignateDocumentTypes(codeSetId, details);
                }
                log.Debug(DataVaultConstants.ProcessEndTag);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
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
                return modeType;
            }
            set
            {
                modeType = value;
            }
        }
        #endregion

    }
}
