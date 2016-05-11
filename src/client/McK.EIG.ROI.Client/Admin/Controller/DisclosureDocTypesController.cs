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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.ComponentModel;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Web_References.ConfigurationWS;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {   
        #region Methods

        /// <summary>
        /// Returns a list of CodeSets.
        /// </summary>
        /// <returns>List of CodeSets</returns>
        public Collection<CodeSetDetails> RetrieveAllCodeSets()
        {   
            object response = HPFWHelper.Invoke(configurationService, "getCodeSet", PrepareHPFWParams(new Object[0]));
            Collection<CodeSetDetails> codeSets = MapModel((codeSet)response);
            return codeSets;
        }
        
        /// <summary>
        /// Returns the MU document names.
        /// </summary>
        /// <returns></returns>
        public string[] GetMUDocNames()
        {
            ArrayList MUDocNames = new ArrayList();
            log.Debug(logMethodName, roiAdminService.GetType().Name, "retrieveMUDocTypes"); 
            object response = ROIHelper.Invoke(roiAdminService, "retrieveMUDocTypes", new object[0]);
            return response as string[];
        }

        /// <summary>
        /// Retrieve all document types from HPF and document designation type from ROI
        /// </summary>
        /// <param name="codeSetId"></param>
        /// <returns></returns>
        public Collection<DocumentTypesDetails> RetrieveDocumentTypes(long codeSetId)
        {   
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateCodeSet(codeSetId))
            {
                throw validator.ClientException;
            }

            object[] requestsParams = new Object[] { codeSetId };
            log.Debug(logMethodName, roiAdminService.GetType().Name, "retrieveDesignations");
            log.Debug(logParams + codeSetId);
            object response = ROIHelper.Invoke(roiAdminService, "retrieveDesignations", requestsParams);

            Hashtable documentDesignationIds = MapModel((Designation[])response);

            requestsParams = new object[] { codeSetId.ToString(System.Threading.Thread.CurrentThread.CurrentCulture) };
            log.Debug(logMethodName, configurationService.GetType().Name, "getDocumentTypesByCodeId");
            log.Debug(logParams + codeSetId.ToString(System.Threading.Thread.CurrentThread.CurrentCulture));
            response = HPFWHelper.Invoke(configurationService, "getDocumentTypesByCodeId", PrepareHPFWParams(requestsParams));

            
            Collection<DocumentTypesDetails> clientDocumentTypesDetails = MapModel((documentTypes)response, documentDesignationIds);

            return clientDocumentTypesDetails;
        }

        /// <summary>
        /// Method to store all designated document types for a codeset.
        /// </summary>
        /// <param name="documentTypeDetails"></param>
        /// <returns></returns>
        public void DesignateDocumentTypes(long codeSetId, Collection<DocumentTypesDetails> documentTypes, Collection<DocTypeAuditDetails> doctypeAuditColl)
        {   
            McK.EIG.ROI.Client.Web_References.ROIAdminWS.Designation[] serverDocumentType = MapModel(documentTypes);
            McK.EIG.ROI.Client.Web_References.ROIAdminWS.DocTypeAudit[] doctypeAudit = MapModel(doctypeAuditColl);
            //CR#370979 Modified
            object[] requestParams = new object[] { codeSetId, serverDocumentType, doctypeAudit };
            log.Debug(logMethodName, roiAdminService.GetType().Name, "designateDocumentTypes");
            log.Debug(logParams + "Collection<DocumentTypesDetails>");
            DocumentTypesDetails docTypeDetail;
            for (int j = 0; j < documentTypes.Count; j++)
            {
                docTypeDetail = documentTypes[j].Clone();
                foreach (PropertyDescriptor descriptor in TypeDescriptor.GetProperties(docTypeDetail))
                {
                    log.Debug("\t     [{0} = {1}]", descriptor.Name, descriptor.GetValue(docTypeDetail));
                }
            }
            ROIHelper.Invoke(roiAdminService, "designateDocumentTypes", requestParams); 
        }

        public ArrayList RetrieveDocumentTypesByDesignation(string designationType)
        {
            object[] requestParams = new object[] { designationType};
            log.Debug(logMethodName, roiAdminService.GetType().Name, "retrieveDocTypeIdsByDesignation");
            log.Debug(logParams + designationType);
            Designation designation = (Designation) ROIHelper.Invoke(roiAdminService, "retrieveDocTypeIdsByDesignation", requestParams);
            ArrayList docTypeIds = new ArrayList();
            if (designation.docTypeIds != null)
            {
                foreach (long docTypeId in designation.docTypeIds)
                {
                    docTypeIds.Add(docTypeId);
                }
            }
            return docTypeIds;
        }

        #endregion

        #region Model Mapping

        /// <summary>
        /// Convert server to client document types.
        /// </summary>
        /// <param name="serverDocumentTypes"></param>
        /// <returns></returns>
        private static DocumentTypesDetails MapModel(item serverDocumentType, Hashtable documentDesignationIds)
        {   
            DocumentTypesDetails clientDocumentType = new DocumentTypesDetails();

            clientDocumentType.Id = Convert.ToInt64(serverDocumentType.value, System.Threading.Thread.CurrentThread.CurrentCulture);
            clientDocumentType.Name = serverDocumentType.name;

            Collection<long> disclosureIds        = (Collection<long>)documentDesignationIds[DocumentDesignationType.Disclosure.ToString()];
            Collection<long> authorizedRequestIds = (Collection<long>)documentDesignationIds[DocumentDesignationType.Authorize.ToString()];
            Collection<long> muRequestIds         = (Collection<long>)documentDesignationIds[DocumentDesignationType.mu.ToString()];
           
            if (disclosureIds != null)
            {
                if (disclosureIds.Contains(clientDocumentType.Id))
                {
                    clientDocumentType.IsDisclosure = true;
                    clientDocumentType.Type = DocumentDesignationType.Disclosure;
                }
            }

            if (authorizedRequestIds != null)
            {
                if (authorizedRequestIds.Contains(clientDocumentType.Id))
                {
                    clientDocumentType.IsAuthorization = true;
                    clientDocumentType.Type = DocumentDesignationType.Authorize;  

                    if (clientDocumentType.IsDisclosure)
                    {
                        clientDocumentType.Type = DocumentDesignationType.Both;                       
                    }                    
                }
            }
            
            if (muRequestIds != null)
            {
                if (muRequestIds.Contains(clientDocumentType.Id))
                {
                    clientDocumentType.IsMUDocumentType = true;
                    clientDocumentType.Type = DocumentDesignationType.mu;
                    if (documentDesignationIds[DocumentDesignationType.mu.ToString()+ clientDocumentType.Id].ToString() != null)
                   {
                      clientDocumentType.MUDocumentType = documentDesignationIds[DocumentDesignationType.mu.ToString()+ clientDocumentType.Id].ToString();
                   }
                }
            }

            if (!(clientDocumentType.IsDisclosure && clientDocumentType.IsAuthorization && clientDocumentType.IsMUDocumentType))
            {
                clientDocumentType.Type = DocumentDesignationType.None;                
            }          
            return clientDocumentType;
        }

        /// <summary>
        /// Convert client to server document types.
        /// </summary>
        /// <param name="clientDocumentTypes"></param>
        /// <returns>serverDocumentTypes</returns>
        private static Designation[] MapModel(Collection<DocumentTypesDetails> clientDocumentTypes)
        {
            List<DocumentTypesDetails> list = new List<DocumentTypesDetails>(clientDocumentTypes);            

            List<DocumentTypesDetails> authList = list.FindAll(delegate(DocumentTypesDetails item) { return item.IsAuthorization; });
            List<DocumentTypesDetails> disclosureList = list.FindAll(delegate(DocumentTypesDetails item) { return item.IsDisclosure; });
            List<DocumentTypesDetails> muDocsList = list.FindAll(delegate(DocumentTypesDetails item) { return item.IsMUDocumentType; });

            Designation[] serverDocumentTypes = new Designation[3];            

            serverDocumentTypes[0] = new Designation();
            serverDocumentTypes[0].docTypeIds = new long[authList.Count];
            serverDocumentTypes[0].type = DocumentDesignationType.Authorize.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentCulture);

            serverDocumentTypes[1] = new Designation();
            serverDocumentTypes[1].docTypeIds = new long[disclosureList.Count];
            serverDocumentTypes[1].type = DocumentDesignationType.Disclosure.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentCulture);

            serverDocumentTypes[2] = new Designation();
            serverDocumentTypes[2].docTypeIds = new long[muDocsList.Count];
            serverDocumentTypes[2].type = DocumentDesignationType.mu.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentCulture);

            int index = 0;
            foreach (DocumentTypesDetails docType in authList)
            {
                serverDocumentTypes[0].docTypeIds[index] = docType.Id;
                index++;
            }

            index = 0;

            foreach (DocumentTypesDetails docType in disclosureList)
            {
                serverDocumentTypes[1].docTypeIds[index] = docType.Id;
                index++;
            }

            index = 0;
            serverDocumentTypes[2].muDocTypes = new MUDocTypeDto[muDocsList.Count];
            foreach (DocumentTypesDetails docType in muDocsList)
            {
                serverDocumentTypes[2].docTypeIds[index] = docType.Id;
                serverDocumentTypes[2].muDocTypes[index] = new MUDocTypeDto();
                serverDocumentTypes[2].muDocTypes[index].muDocId=docType.Id;
                serverDocumentTypes[2].muDocTypes[index].muDocName = docType.MUDocumentType;
                index++;
            }

            return serverDocumentTypes;
        }

        /// <summary>
        /// Convert server to client document type.
        /// </summary>
        /// <param name="serverDocumentTypes"></param>
        /// <param name="documentDesignationIds"></param>
        /// <returns></returns>
        private static Collection<DocumentTypesDetails> MapModel(documentTypes serverDocumentTypes, Hashtable documentDesignationIds)
        {
            Collection<DocumentTypesDetails> clientDocumentTypes = new Collection<DocumentTypesDetails>();
            if (serverDocumentTypes.items != null)
            {
                foreach (item serverDocumentType in serverDocumentTypes.items)
                {
                    clientDocumentTypes.Add(MapModel(serverDocumentType, documentDesignationIds));
                }
            }
            return clientDocumentTypes;
        }     

        /// <summary>
        /// Retrieves document type ids from the ROI server
        /// </summary>
        /// <param name="serverDocumentType"></param>
        /// <returns>clientDocumentTypes</returns>
        private static Hashtable MapModel(Designation[] serverDocumentTypes)
        {
            Hashtable documentDesignationIds      = new Hashtable();

            Collection<long> disclosureIds;       
            Collection<long> authorizedRequestIds;
            Collection<long> muRequestIds;
            
            foreach (Designation serverDocumentType in serverDocumentTypes)
            {
                if (serverDocumentType.type != null)
                {
                    if (string.Compare(serverDocumentType.type, DocumentDesignationType.Disclosure.ToString(), true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0)
                    {
                        disclosureIds = new Collection<long>(serverDocumentType.docTypeIds);
                        documentDesignationIds[DocumentDesignationType.Disclosure.ToString()] = disclosureIds;
                    }
                    if (string.Compare(serverDocumentType.type, DocumentDesignationType.Authorize.ToString(), true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0)
                    {
                        authorizedRequestIds = new Collection<long>(serverDocumentType.docTypeIds);                        
                        documentDesignationIds[DocumentDesignationType.Authorize.ToString()] = authorizedRequestIds;
                    }
                    if (string.Compare(serverDocumentType.type, DocumentDesignationType.mu.ToString(), true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0)
                    {
                        muRequestIds = new Collection<long>(serverDocumentType.docTypeIds);
                        documentDesignationIds[DocumentDesignationType.mu.ToString()] = muRequestIds;
                        if (serverDocumentType.muDocTypes != null)
                        {
                            for (int i = 0; i < serverDocumentType.muDocTypes.Length; i++)
                            {
                               documentDesignationIds[DocumentDesignationType.mu.ToString()+ serverDocumentType.muDocTypes[i].muDocId] = serverDocumentType.muDocTypes[i].muDocName;
                            }
                        }
                    }
                }
            }
            return documentDesignationIds;
        }      

        /// <summary>
        /// Convert server to client CodeSetlist
        /// </summary>
        /// <param name="serverCodeSets"></param>
        /// <returns></returns>
        private static Collection<CodeSetDetails> MapModel(codeSet serverCodeSets)
        {
            Collection<CodeSetDetails> clientCodeSets = new Collection<CodeSetDetails>();
            foreach (item item in serverCodeSets.items)
            {
                CodeSetDetails clientCodeSet = new CodeSetDetails();
                clientCodeSet.Id = Convert.ToInt64(item.name, System.Threading.Thread.CurrentThread.CurrentCulture);
                clientCodeSet.Description = item.value;
                clientCodeSets.Add(clientCodeSet);
            }
            return clientCodeSets;
        }       

        private static DocTypeAudit[] MapModel(Collection<DocTypeAuditDetails> DocTypeAudits)
        {
            DocTypeAudit[] docAudit = new DocTypeAudit[DocTypeAudits.Count];
            int index = 0;

            foreach (DocTypeAuditDetails item in DocTypeAudits)
            {
                docAudit[index] = new DocTypeAudit();
                docAudit[index].fromValue = item.FromValue;
                docAudit[index].toValue = item.ToValue;
                docAudit[index].codeSetName = item.CodeSetName;
                docAudit[index].docType = item.DocType;
                docAudit[index].docName = item.DocName;
                index++;
            }
            return docAudit;
        }
        #endregion
    }
}
