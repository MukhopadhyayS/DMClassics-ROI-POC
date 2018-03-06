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
using System.Configuration;
using System.Globalization;
using System.Text;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.Client.Web_References.MedicalRecordWS;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;
using McK.EIG.ROI.Client.Web_References.DocumentWS;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    public partial class PatientController
    {
        #region Fields

        private static ArrayList docTypeIds = new ArrayList();

        #endregion

        # region Methods

        #region Service Methods
        /// <summary>
        /// Retrieves all global documents and hpf documents for a hpf patient.
        /// </summary>
        /// <param name="patient"></param>
        /// <returns></returns>
        public PatientDetails RetrieveHpfDocuments(PatientDetails patient)
        {
            if (PatientDetailsCache.IsKeyExist(patient.MRN + patient.facilityCode))
            {
                return PatientDetailsCache.GetPatDetails(patient.MRN + patient.facilityCode);
            }
            else
            {
                object[] requestParams = new object[] { patient.MRN, patient.FacilityCode, ROIConstants.DateFormat, true };
                object response = HPFWHelper.Invoke(medicalRecordService, "getMedicalRecordForMrnFacility", PrepareHPFWParams(requestParams));
                                
                PatientDetails patDetails = MapModel((medicalRecord)response, patient);
                //PatientDetailsCache.AddData(patient.MRN + patient.facilityCode, patDetails);
                return patDetails;
            }
        }

        /// <summary>
        /// Retrieves all encounters of hpf patient with all facilities.
        /// </summary>
        /// <param name="patient"></param>
        /// <returns></returns>
        public PatientDetails RetrieveHpfEncounters(PatientDetails patient)
        {
            if (PatientDetailsCache.IsKeyExist(patient.MRN + patient.facilityCode))
            {
                return PatientDetailsCache.GetPatDetails(patient.MRN + patient.facilityCode);
            }
            else
            {
                object[] requestParams = new object[] { patient.MRN, patient.FacilityCode, ROIConstants.DateFormat, false };
                object response = HPFWHelper.Invoke(medicalRecordService, "getMedicalRecord", PrepareHPFWParams(requestParams));
                PatientDetails patDetails = MapModel((medicalRecord)response, patient);
                PatientDetailsCache.AddData(patient.MRN + patient.facilityCode, patDetails);
                return patDetails;
            }
        }   

        public PatientDetails RetrieveEncounters(PatientDetails patient)
        {
            if (PatientDetailsCache.IsKeyExist(patient.MRN + patient.facilityCode))
            {
                return PatientDetailsCache.GetPatDetails(patient.MRN + patient.facilityCode);
            }
            else
            {
                object[] requestParams = new object[] { patient.MRN, patient.FacilityCode, ROIConstants.DateTimeFormat, false };
                object response = HPFWHelper.Invoke(medicalRecordService, "getMedicalRecordForMrnFacility", PrepareHPFWParams(requestParams));
                PatientDetails patDetails = MapModel((medicalRecord)response, patient);
                PatientDetailsCache.AddData(patient.MRN + patient.facilityCode, patDetails);
                return patDetails;
            }
        }
        /// <summary>
        /// Retrieves pages for a document.
        /// </summary>
        /// <param name="document"></param>
        /// <returns></returns>
        //private document RetrievePagesForDocument(document doc)
        //{
        //    object[] requestParams = new object[] { Convert.ToInt32(doc.docId), true };         
        //    object response = HPFWHelper.Invoke(medicalRecordService, "getPagesForDocument", PrepareHPFWParams(requestParams));
        //    doc.versions = ((document)response).versions;
        //    return doc;
        //}

        private documentList RetrievePagesForDocuments(string docIds)
        {
            documentList docList = new documentList();
            object[] requestParams = new object[] { docIds, true };
            object response = HPFWHelper.Invoke(medicalRecordService, "getPagesForDocuments", PrepareHPFWParams(requestParams));
            docList = ((documentList)response);
            return docList;
        }

        public Collection<EncounterDetails> GetCustomFields(Collection<EncounterDetails> encounters)
        {
            object[] requestParams; 
            int index = 0;
            foreach(EncounterDetails encounter in encounters)
            {
                requestParams = new object[] {1, encounter.EncounterId, encounter.Facility};                
                object response = HPFWHelper.Invoke(medicalRecordService, "getCustomFields", PrepareHPFWParams(requestParams));
                if (response != null)
                {
                    MapModel(encounters[index], (customFields)response);
                }
                index++; 
            }
            return encounters;
        }

        /// <summary>
        /// Retrieve IMnetIds.
        /// </summary>
        /// <returns></returns>
        public string RetrieveImnetsByImnet(string imNetId)
        {
            object[] requestParams = new object[] { imNetId };
            if (documentInfoService == null)
            {
                documentInfoService = new DocumentInfoServiceWse();
            }
            object response = HPFWHelper.Invoke(documentInfoService, "getImnetsByImnet", PrepareHPFWParams(requestParams));
            return (string)response;
        }

        #endregion

        #region MapModel

        /// <summary>
        /// Adds custom fields to the encounter.
        /// </summary>
        /// <param name="encounter"></param>
        /// <param name="items"></param>
        private static void MapModel(EncounterDetails encounter, customFields customFields)
        {
            encounter.CustomFields.Clear();
            if (customFields.items != null)
            {
                //encounter.CustomFields.Add(new KeyValuePair<string, string>("test", "test"));
                foreach (item item in customFields.items)
                {
                    encounter.CustomFields.Add(item.name, item.value);
                }
            }
        }


        /// <summary>
        ///  Coverts server global documents and encouters to client global documents and encouters
        /// </summary>
        /// <param name="server"></param>
        /// <param name="client"></param>
        /// <returns></returns>
        private PatientDetails MapModel(medicalRecord server, PatientDetails clientPatient)
        {   
            if (server != null)
            {
                clientPatient.Encounters.Clear();
                docTypeIds = ROIAdminController.Instance.RetrieveDocumentTypesByDesignation(DocumentDesignationType.Disclosure.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture));
                MapModel((document[])server.globalDocuments, clientPatient);
                MapModel((encounter[])server.encounters, clientPatient);
            }
            return clientPatient;
        }

        /// <summary>
        /// Converts server global documents to client global documents.
        /// </summary>
        /// <param name="serverGlobalDocs"></param>
        /// <param name="clientGlobalDocs"></param>
        private void MapModel(document[] serverGlobalDocs, PatientDetails clientPatient)
        {
            PatientGlobalDocument clientGlobalDoc = new PatientGlobalDocument();
            documentList documentList;
            if (serverGlobalDocs != null)
            {
                if (serverGlobalDocs.Length > 0)
                {
                    StringBuilder sb = new StringBuilder();
                    foreach (document serverDoc in serverGlobalDocs)
                    {
                        sb.Append(serverDoc.docId).Append(",");
                    }

                    string documentIds = sb.ToString().TrimEnd(',');
                    documentList = RetrievePagesForDocuments(documentIds);

                    int counter = 0;
                    foreach (document serverDocument in serverGlobalDocs)
                    {
                        if (serverDocument.docId == documentList.documents[counter].docId)
                        {
                            serverDocument.versions = documentList.documents[counter].versions;
                            DocumentDetails clientDocument = MapModel(serverDocument);
                            clientDocument.Parent = clientGlobalDoc;
                            clientGlobalDoc.AddDocument(clientDocument);
                        }
                        counter++;
                    }
                }
            }
            clientPatient.GlobalDocument = clientGlobalDoc;
            clientPatient.GlobalDocument.Parent = clientPatient;
        }

        /// <summary>
        /// Converts server document to client document.
        /// </summary>
        /// <param name="server"></param> 
        /// <returns></returns>
        private static DocumentDetails MapModel(document server)
        {
            DocumentDetails clientDocument = new DocumentDetails();            
            
            clientDocument.DocumentType = DocumentType.CreateDocType(Convert.ToInt64(server.docTypeId, System.Threading.Thread.CurrentThread.CurrentUICulture),
                                                                     server.docName.Trim(),
                                                                     server.subtitle,
                                                                     server.dateTime,
                                                                     server.chartOrder,
                                                                     docTypeIds.Contains(Convert.ToInt64(server.docTypeId, System.Threading.Thread.CurrentThread.CurrentUICulture)),
                                                                     server.docId);
            clientDocument.DocumentId = server.docId;
            //CR#365589
            if (!string.IsNullOrEmpty(server.dateTime))
            {
                clientDocument.DocumentDateTime = Convert.ToDateTime(server.dateTime, System.Threading.Thread.CurrentThread.CurrentUICulture); //CR#365558
            }

            if (clientDocument != null)
            {
                MapModel(server.versions, clientDocument.Versions, clientDocument);
            }

            return clientDocument;
        }

        /// <summary>
        /// Converts server encounters to client encounters.
        /// </summary>
        /// <param name="serverEncounters"></param>
        /// <param name="clientEncounters"></param>
        private void MapModel(encounter[] serverEncounters, PatientDetails clientPatient)
        {
            DateTime t1 = DateTime.Now;
            int documentCount = Convert.ToInt32(ConfigurationManager.AppSettings["DocumentCount"]);

            if (serverEncounters != null)
            {
                StringBuilder documentIDs = new StringBuilder();
                Hashtable documentHashTable = new Hashtable();
                int counter = 0;

                foreach (encounter serverEncounter in serverEncounters)
                {
                    if (serverEncounter.locked)
                    {
                        if (!UserData.Instance.HasAccess(ROISecurityRights.AccessLockedRecords)) continue;
                    }

                    if (serverEncounter.vip)
                    {
                        if (!UserData.Instance.HasAccess(ROISecurityRights.ROIVipStatus)) continue;
                    }

                    if (serverEncounter.documents != null)
                    {
                        if (serverEncounter.documents.Length > 0)
                        {
                            foreach (document serverDocument in serverEncounter.documents)
                            {
                                documentIDs.Append(serverDocument.docId).Append(",");
                                counter++;
                                if (counter == documentCount)
                                {
                                    addDocumentToHashTable(documentIDs, documentHashTable);
                                    documentIDs.Clear();
                                    counter = 0;
                                }
                            }
                        }
                    }
                }


                addDocumentToHashTable(documentIDs, documentHashTable);

                foreach (encounter serverEncounter in serverEncounters)
                {
                    DocumentDetails clientDocument;
                    EncounterDetails clientEncounter = new EncounterDetails(serverEncounter.encounterNumber.Trim(),
                                                                    serverEncounter.patientType,
                                                                    serverEncounter.patientService,
                                                                    serverEncounter.facilityCode,
                                                                    serverEncounter.admitDate,
                                                                    serverEncounter.dischargeDate,
                                                                    serverEncounter.vip,
                                                                    serverEncounter.locked,
                                                                    serverEncounter.hasDeficiency,
                                                                    serverEncounter.clinic,
                                                                    serverEncounter.disposition,
                                                                    serverEncounter.financialClass,
                                                                    serverEncounter.balance,
                                                                    serverEncounter.selfPay
                                                                    );

                    if (serverEncounter.documents != null)
                    {
                        if (serverEncounter.documents.Length > 0)
                        {
                            foreach (document serverDocument in serverEncounter.documents)
                            {
                                document doc = (document)documentHashTable[serverDocument.docId];
                                serverDocument.versions = doc.versions;
                                clientDocument = MapModel(serverDocument);
                                clientDocument.Parent = clientEncounter;
                                clientEncounter.AddDocument(clientDocument);
                            }
                        }
                    }
                    clientEncounter.Parent = clientPatient;
                    clientPatient.Encounters.Add(clientEncounter);
                }
            }
            DateTime t2 = DateTime.Now;
            TimeSpan timeSpan = t2.Subtract(t1);
            string strTimeSpan = timeSpan.Hours + ":" + timeSpan. Minutes + ":" + timeSpan.Seconds + "," + timeSpan.Milliseconds;
            log.Debug("Total Time taken : " + strTimeSpan);
        }

        private void addDocumentToHashTable(StringBuilder documentIDs, Hashtable documentHashTable)
        {
            string documentIds = documentIDs.ToString().TrimEnd(',');
            if (!(string.IsNullOrEmpty(documentIds)))
            {
                DateTime t1 = DateTime.Now;
                
                documentList documentList = RetrievePagesForDocuments(documentIds);

                DateTime t2 = DateTime.Now;
                TimeSpan timeSpan = t2.Subtract(t1);
                string strTimeSpan = timeSpan.Hours + ":" + timeSpan.Minutes + ":" + timeSpan.Seconds + "," + timeSpan.Milliseconds;
                log.Debug("Each ServiceCall Time taken : " + strTimeSpan);
                foreach (document document in documentList.documents)
                {
                    documentHashTable.Add(document.docId, document);
                }
            }
        }

        /// <summary>
        /// Converts server versions to client versions.
        /// </summary>
        /// <param name="serverVersions"></param>
        /// <param name="clientVersions"></param>
        private static void MapModel(version[] serverVersions, SortedList<int, VersionDetails> clientVersions, DocumentDetails clientDoc)
        {
            if (serverVersions != null)
            {
                foreach (version serverVersion in serverVersions)
                {
                    VersionDetails clientVersion = MapModel(serverVersion, clientDoc);
                    clientVersions.Add(clientVersion.VersionNumber, clientVersion);
                }
            }
        }

        /// <summary>
        /// Converts server version to client version.
        /// </summary>
        /// <param name="serverVersions"></param>
        /// <param name="clientVersions"></param>
        private static VersionDetails MapModel(version serverVersion, DocumentDetails clientDoc)
        {
            VersionDetails clientVersion = new VersionDetails();

            clientVersion.VersionNumber = serverVersion.versionNumber;
            clientVersion.Parent        = clientDoc;

            MapModel((page[])serverVersion.pages, clientVersion.Pages, clientVersion);

            return clientVersion;
        }

        /// <summary>
        /// Converts server pages to client pages.
        /// </summary>
        /// <param name="serverVersions"></param>
        /// <param name="clientVersions"></param>
        private static void MapModel(page[] serverPages, SortedList<int, PageDetails> clientPages, VersionDetails clientVersion)
        {
            if (serverPages != null)
            {
                PageDetails clientPage;
				int incrementalKey = 0;
                foreach (page serverPage in serverPages)
                {
                    if (serverPage.contentCount > 1)
                    {
                        for (int count = 1; count <=serverPage.contentCount; count++)
                        {
                            clientPage = new PageDetails(serverPage.imnet.Trim(), incrementalKey + 1, serverPage.contentCount, count);
                            clientPage.Parent = clientVersion;
                            clientPages.Add(incrementalKey++, clientPage);
                        }
                    }
                    else
                    {
                        clientPage = new PageDetails(serverPage.imnet.Trim(), incrementalKey + 1, serverPage.contentCount, 1);
                        clientPage.Parent = clientVersion;
                        clientPages.Add(incrementalKey++, clientPage);
                    }
                }
            }
        }

       

        #endregion

        #endregion
    }
}
