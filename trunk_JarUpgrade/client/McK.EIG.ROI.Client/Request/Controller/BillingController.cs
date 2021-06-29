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
using System.IO;
using System.Text;
using System.Xml;

using McK.EIG.Common.FileTransfer.Model;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.Client.Web_References.BillingCoreWS;
using client = McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.Controller
{
    /// <summary>
    /// Billing Level Controller.
    /// </summary>
    public partial class BillingController : ROIController
    {
        #region Fields

        private static object syncRoot = new Object();

        //private static variable of BillingController
        private static volatile BillingController billingController;

        private BillingCoreServiceWse billingCoreService;

        public const string UploadFileName = @"\PreBillInvoiceLetterTemplate";
        private const string documentType   = "DOCX";
        private const string ownerType      = "FileDownloader";        
        private const string Reason         = "reason";
        public const string DirectoryPath   = @"PreBillInvoiceTemplates";

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the Request Service.
        /// </summary>
        private BillingController()
        {   
            billingCoreService = new BillingCoreServiceWse();
        }

        #endregion

        #region Service Methods

        /// <summary>
        /// Create the release and invoice, cover letter preview.
        /// </summary>
        /// <param name="releaseCore"></param>
        /// <returns></returns>
        public ReleaseAndPreviewInfo CreateReleaseAndPreview(ReleaseCore releaseCore, bool isApplied,double AppliedAmount)
        {
            object[] requestParams = new object[] { releaseCore ,isApplied,AppliedAmount};
            object response = ROIHelper.Invoke(billingCoreService, "createReleaseAndPreviewInfo", requestParams);
            return (ReleaseAndPreviewInfo)response;
        }
        //
        /// <summary>
        /// Updates the Invoice/prebill balance each time the balance is changed.
        /// </summary>
        /// <param name="InvoiceBalance"></param>
        /// <param name="InvoiceId"></param>
        /// <returns></returns>
        public void updateInvoiceBalance(long invoiceId, double invoiceBalance)
        {
            object[] requestParams = new object[] {invoiceId,invoiceBalance};
            ROIHelper.Invoke(billingCoreService, "updateInvoiceBalance",requestParams);

        }

        /// <summary>
        /// Deletes the release.
        /// </summary>
        /// <param name="releaseID"></param>
        /// <param name="InvoiceID"></param>
        public void CancelRelease(long releaseID, long InvoiceID)
        {
            object[] requestParams = new object[] { releaseID, InvoiceID };
            ROIHelper.Invoke(billingCoreService, "cancelRelease", requestParams);
        }       
        
        public void updateInvoiceOutputProperties(long invoiceId, long letterId, long releaseId, bool forInvoice,
                                                  bool forLetter, bool forRelease, string queuePassword, string outputMethod)
        {
            InvoiceAndLetterOutputProperties InvoiceAndLetterOutputProperties = MapModel(invoiceId, letterId, releaseId, forInvoice, 
                                                                                         forLetter, forRelease, queuePassword, outputMethod);
            object[] requestParams = new object[] { InvoiceAndLetterOutputProperties };
            ROIHelper.Invoke(billingCoreService, "updateInvoiceOutputProperties", requestParams);
        }
      
        /// <summary>
        /// Sets the request status as draft release.
        /// </summary>
        /// <param name="requestId"></param>
        public void IsDisplayBillingInfo(long requestId, bool isEnableReleaseInvoiceLink)
        {
            object[] requestParams = new object[] { requestId, isEnableReleaseInvoiceLink };
            ROIHelper.Invoke(billingCoreService, "setDisplayBillingInfo", requestParams);            
        }

        /// <summary>
        /// Method used to retrieve the charge histories based on request id
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        public Collection<ChargeHistoryDetails> RetrieveChargeHistory(long requestId)
        {
            object[] requestParams = new object[] { requestId };
            object response = ROIHelper.Invoke(billingCoreService, "retrieveChargeHistory", requestParams);
            return MapModel((ChargeHistory[])response);
        }

        /// <summary>
        /// Method to retrieve all request histories for a specified request Id.
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        public ReleaseHistoryDetails RetrieveReleaseHistoryList(long requestId, string requestPassword, string selfPayEncounter)
        {
            object[] requestParams = new object[] { requestId };
            object response = ROIHelper.Invoke(billingCoreService, "retrieveReleaseHistoryList", requestParams);
            return MapModel((ReleaseHistoryList)response, requestPassword,selfPayEncounter);
        }

        /// <summary>
        /// Method used to delete invoice/preBill.
        /// </summary>
        /// <param name="invoiceId"></param>
        public void DeleteInvoiceOrPreBill(long invoiceId)
        {
            object[] requestParams = new Object[] { invoiceId };
            ROIHelper.Invoke(billingCoreService , "cancelInvoiceOrPrebillAndPreview", requestParams);
        }

        /// <summary>
        /// Method used to delete letter
        /// </summary>
        /// <param name="preBillId"></param>
        public void DeleteLetter(long letterId, long requestId)
        {
            object[] requestParams = new Object[] { requestId, letterId };
            ROIHelper.Invoke(billingCoreService, "cancelCoverLetter", requestParams);
        }

        /// <summary>
        /// Create letter.
        /// </summary>
        public DocumentInfo CreateLetter(long letterTemplateId, long requestId, string[] notes)
        {
            LetterInfo letterInfo = new LetterInfo();
            letterInfo.letterTemplateId = letterTemplateId;
            letterInfo.requestId = requestId;
            letterInfo.notes = notes;
            letterInfo.type = documentType;
            object[] requestParams = new Object[] { letterInfo };
            object response = ROIHelper.Invoke(billingCoreService, "createLetterAndPreview", requestParams);
            return MapModel((McK.EIG.ROI.Client.Web_References.BillingCoreWS.DocInfo)response);
        }

	    /// <summary>
        /// Retrieve past invoice details.
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        public Collection<PastInvoiceDetails> RetrievePastInvoiceDetails(long requestId)
        {
            object[] requestParams = new object[] { requestId };
            object response = ROIHelper.Invoke(billingCoreService, "retrievePastInvoices", requestParams);
            return MapModel(response as McK.EIG.ROI.Client.Web_References.BillingCoreWS.PastInvoice[]);
        }


        /// <summary>
        /// View invoice from Invoice History
        /// </summary>
        /// <param name="invoiceType"></param>
        /// <param name="invoiceTypeId"></param>
        /// <returns></returns>
        public DocumentInfo ViewInvoiceHistory(string invoiceType, long invoiceTypeId)
        {
            object[] requestParams = new object[] { invoiceTypeId, documentType };
            object response = ROIHelper.Invoke(billingCoreService, "viewInvoice", requestParams);           
            return MapModel((McK.EIG.ROI.Client.Web_References.BillingCoreWS.DocInfo)response);
        }

        /// <summary>
        /// This method will download the file.
        /// </summary>
        /// <param name="letterTemplateDetails"></param>
        /// <param name="filePath"></param>
        public static string DownloadLetterTemplate(string fileName, EventHandler progressHandler)
        {
            Hashtable serverParameter = new Hashtable();

            serverParameter.Add(ROIConstants.User, UserData.Instance.UserInstanceId);
            serverParameter.Add(ROIConstants.SecretWord, UserData.Instance.SecretWord);

            serverParameter.Add("FILE_NAME", fileName);
            serverParameter.Add("OWNER_TYPE", ownerType);

            string ischunkEnabled = ConfigurationManager.AppSettings["ChunkEnabled"];
            string blockSize = ConfigurationManager.AppSettings["BlockSize"];

            ischunkEnabled = string.IsNullOrEmpty(ischunkEnabled) ? "false" : ischunkEnabled;
            serverParameter.Add(ROIConstants.ChunkEnabled, ischunkEnabled);
            if (Convert.ToBoolean(ischunkEnabled, System.Threading.Thread.CurrentThread.CurrentUICulture))
            {
                serverParameter.Add(ROIConstants.BlockSize, blockSize);
            }
            string filePath = Path.Combine(Path.Combine(Environment.CurrentDirectory, "temp") , DirectoryPath);

            if (!Directory.Exists(filePath))
            {
                Directory.CreateDirectory(filePath);
            }
            
            DateTime currentDateTime = DateTime.Now;
            filePath += UploadFileName + "_" + currentDateTime.Year + "_" +
                            +currentDateTime.Month + "_" +
                            +currentDateTime.Day + "_" +
                            +currentDateTime.Hour + "_" +
                            +currentDateTime.Minute + "_" +
                            +currentDateTime.Second + "_" +
                            + currentDateTime.Millisecond + ".pdf";

            DownloadContent fileDownloadContent = new DownloadContent();

            ROIFileDownloadController.Instance.DownloadFile(fileDownloadContent,
                                                            filePath,
                                                             serverParameter, progressHandler);

            return filePath;
        }

        /// <summary>
        /// Method to create audit and event for auto apply for the invoice
        /// </summary>
        /// <param name="requestID"></param>
        /// <param name="invoiceID"></param>
        public void CreateAuditAndEventsForAutoApplyToInvoice(long requestID, long invoiceID)
        {
            object[] requestParams = new object[] {requestID, invoiceID,};
            ROIHelper.Invoke(billingCoreService, "autoApplyToInvoice", requestParams);   
        }

     

        #endregion

        #region MapModel
       
        public static ReleaseCore MapModel(ReleaseDetails releaseDetails)
        {
            ReleaseCore releaseCore = new ReleaseCore();
            releaseCore.requestId = releaseDetails.RequestId;
            // fill in other details in releaseCore
            return releaseCore;
        }

	    /// <summary>
        /// Converts the server model into client.
        /// </summary>
        /// <param name="pastInvoices"></param>
        /// <returns></returns>
        private static Collection<PastInvoiceDetails> MapModel(McK.EIG.ROI.Client.Web_References.BillingCoreWS.PastInvoice[] pastInvoices)
        {
            Collection<PastInvoiceDetails> pastInvoiceList = new Collection<PastInvoiceDetails>();
            PastInvoiceDetails pastInvoiceDetails;
            foreach (McK.EIG.ROI.Client.Web_References.BillingCoreWS.PastInvoice pastInvoice in pastInvoices)
            {
                pastInvoiceDetails = new PastInvoiceDetails();
                pastInvoiceDetails.InvoiceId = pastInvoice.invoiceId;
                pastInvoiceDetails.CreatedDate = null;
                if (!string.IsNullOrEmpty(pastInvoice.createdDate))
                {
                    pastInvoiceDetails.CreatedDate = Convert.ToDateTime(pastInvoice.createdDate, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                pastInvoiceDetails.Amount = pastInvoice.amount;
                pastInvoiceList.Add(pastInvoiceDetails);
            }
            return pastInvoiceList;
        }

        private static ReleaseHistoryDetails MapModel(McK.EIG.ROI.Client.Web_References.BillingCoreWS.ReleaseHistoryList historyList, string requestPassword, string selfPayEncounter)
        {
            ReleaseHistoryDetails clientReleaseHistory = new ReleaseHistoryDetails();

            ReleaseHistoryPatient[] releasedPatientsField = historyList.releasedPatients;
            ReleaseHistoryItem[] historyFields = historyList.history;

            ReleasedPatientDetails patientDetails;
            ReleasedItemDetails releasedItem;

            if (releasedPatientsField != null && releasedPatientsField.Length > 0)
            {
                foreach (ReleaseHistoryPatient releasedPatient in releasedPatientsField)
                {
                    patientDetails = new ReleasedPatientDetails();
                    //not null, according to DB fields
                    patientDetails.MRN = releasedPatient.mrn;
                    patientDetails.EPN = releasedPatient.epn;
                    patientDetails.Id = releasedPatient.patientSeq;
                    patientDetails.PatientSeq = releasedPatient.patientSeq;
                    string key = Convert.ToString(releasedPatient.patientSeq);
                    //fields below can be null
                    //DE7989:Fix for ROI DOB issue(For a particular Date of birth i.e 03/24/1943,the date on Release history screen was being displayed as 03/23/1943)
                    patientDetails.DOB = releasedPatient.dob.AddHours(1);

                    patientDetails.SSN = releasedPatient.ssn;
                    patientDetails.EncounterLocked = Convert.ToBoolean(releasedPatient.encounterLocked == null ? "false" : releasedPatient.encounterLocked);
                    patientDetails.FacilityCode = releasedPatient.facility;
                    patientDetails.Gender = releasedPatient.gender;
                    patientDetails.IsVip = (releasedPatient.vip == "1" ? true : false);
                    if (!string.IsNullOrEmpty(releasedPatient.name))
                    {
                        patientDetails.FullName = releasedPatient.name.TrimEnd();
                    }
                    patientDetails.IsLockedPatient = Convert.ToBoolean(releasedPatient.patientLocked == null ? "false" : releasedPatient.patientLocked);
                    //add a patient to collection
                    clientReleaseHistory.ReleasedPatients.Add(key, patientDetails);
                }
            }

            int totalCount = 0;
            int totalPageCount = 0;
            string address;
            bool isPatientExist = false;
            if (historyFields != null && historyFields.Length > 0)
            {
                foreach (ReleaseHistoryItem item in historyFields)
                {
                    releasedItem = new ReleasedItemDetails();
                    releasedItem.ReleasedDate = item.datetime;
                    if (!string.IsNullOrEmpty(item.enctr))
                    {
                        releasedItem.Encounter = (item.isSelfPay) ? (item.enctr.TrimEnd() + selfPayEncounter) : item.enctr.TrimEnd();
                    }
                    releasedItem.DocumentVersionSubtitle = item.documentVersionSubtitle;
                    releasedItem.Pages = item.pages;
                    try
                    {
                        releasedItem.PageCount = Convert.ToInt32(item.pages);
                    }
                    catch (Exception e) {
                        throw (e);
                    }
                    releasedItem.TrackingNumber = item.trackingNumber;
                    releasedItem.ShippingMethod = item.shippingMethod;                    
                    releasedItem.UserName = item.userName;
                    releasedItem.UserId = item.userId;
                    if (!string.IsNullOrEmpty(item.patientName))
                    {
                        releasedItem.PatientName = item.patientName.TrimEnd();
                    }
                    releasedItem.QueueSecretWord = item.queuePassword;
                    if (!string.IsNullOrEmpty(item.queuePassword))
                    {
                        releasedItem.RequestSecretWord = item.requestPassword;
                    }
                    address = string.Empty;

                    if (!string.IsNullOrEmpty(item.address1))
                    {
                        address += item.address1 + Environment.NewLine;
                    }

                    if (!string.IsNullOrEmpty(item.address2))
                    {
                        address += item.address2 + Environment.NewLine;
                    }

                    if (!string.IsNullOrEmpty(item.address3))
                    {
                        address += item.address3 + Environment.NewLine;
                    }

                    if (!string.IsNullOrEmpty(item.city))
                    {
                        address += item.city + ROIConstants.addressDivider;
                    }

                    if (!string.IsNullOrEmpty(item.state))
                    {
                        address += item.state + Environment.NewLine;
                    }

                    if (!string.IsNullOrEmpty(item.zipcode))
                    {
                        address += item.zipcode;
                    }
                    
                    if (OutputMethod.Disc.ToString() != item.outputMethod)
                    {
                        releasedItem.ShippingAddress = address;
                    }

                    //todo put id as a key
                    if (!clientReleaseHistory.UserNames.Contains(item.userId))
                    {
                        clientReleaseHistory.UserNames.Add(item.userId, item.userName);
                    }

                    if (!clientReleaseHistory.ReleaseDate.Contains(item.datetime))
                    {
                        clientReleaseHistory.ReleaseDate.Add(item.datetime, item.datetime);
                    }

                    totalCount++;
                    try
                    {
                        totalPageCount += Convert.ToInt32(item.pages);
                    }
                    catch (Exception e) {
                        throw (e);
                    }

                    string patientKey = Convert.ToString(item.patientSeq);
                    isPatientExist = clientReleaseHistory.ReleasedPatients.ContainsKey(patientKey);
                    if (isPatientExist) {
                        releasedItem.ReleasePatient = clientReleaseHistory.ReleasedPatients[patientKey];
                        clientReleaseHistory.ReleasedPatients[patientKey].ReleaseItems.Add(releasedItem);
                    
                    }
                    clientReleaseHistory.ReleaseItems.Add(releasedItem);
                }
            }
            clientReleaseHistory.TotalCount = totalCount;
            clientReleaseHistory.TotalPageCount = totalPageCount;
            return clientReleaseHistory;
        }

        /// <summary>
        /// Converts server model into client.
        /// </summary>
        /// <param name="documentInfo"></param>
        /// <returns></returns>
        private static DocumentInfo MapModel(McK.EIG.ROI.Client.Web_References.BillingWS.DocInfo documentInfo)
        {
            DocumentInfo clientDocumentInfo = new DocumentInfo();
            clientDocumentInfo.Id = documentInfo.id;
            clientDocumentInfo.Name = documentInfo.name;
            clientDocumentInfo.Type = documentInfo.type;
            return clientDocumentInfo;
        }

        /// <summary>
        /// Converts server model into client.
        /// </summary>
        /// <param name="documentInfo"></param>
        /// <returns></returns>
        private static DocumentInfo MapModel(McK.EIG.ROI.Client.Web_References.BillingCoreWS.DocInfo documentInfo)
        {
            DocumentInfo clientDocumentInfo = new DocumentInfo();
            clientDocumentInfo.Id = documentInfo.id;
            clientDocumentInfo.Name = documentInfo.name;
            clientDocumentInfo.Type = documentInfo.type;
            return clientDocumentInfo;
        }        

        /// <summary>
        /// converts the server model into client
        /// </summary>
        /// <param name="chargeHistory"></param>
        /// <returns></returns>
        private static Collection<ChargeHistoryDetails> MapModel(ChargeHistory[] chargeHistory)
        {
            Collection<ChargeHistoryDetails> chargeHistories = new Collection<ChargeHistoryDetails>();
            ChargeHistoryDetails client;
            foreach (ChargeHistory server in chargeHistory)
            {
                client = new ChargeHistoryDetails();
                client.DocumentChargeTotal = server.totalDocumentCharge;
                client.FeeChargeTotal = server.totalFeeCharge;
                client.ReleasedDate = server.releaseDate.ToString();
                client.ShippingCharge = server.totalShippingCharge;
                client.SalesTaxTotal = server.totalSalesTax;
                client.UnBillableAmount = server.unbillableAmount;
                chargeHistories.Add(client);
            }
            return chargeHistories;
        }

        public InvoiceAndLetterOutputProperties MapModel(long invoiceID, long LetterID, long releaseId, bool forInvoice, bool forLetter,
                                                         bool forRelease, string queuePassword, string outputMethod)
        {
            InvoiceAndLetterOutputProperties invoiceAndLetterOutputProperties = new InvoiceAndLetterOutputProperties();
            invoiceAndLetterOutputProperties.invoiceId = invoiceID;
            invoiceAndLetterOutputProperties.letterId = LetterID;
            invoiceAndLetterOutputProperties.forInvoice = forInvoice;
            invoiceAndLetterOutputProperties.forLetter = forLetter;
            invoiceAndLetterOutputProperties.queuePassword = queuePassword;
            invoiceAndLetterOutputProperties.outputMethod = outputMethod;
            invoiceAndLetterOutputProperties.releaseId = releaseId;
            invoiceAndLetterOutputProperties.forRelease = forRelease;
            return invoiceAndLetterOutputProperties;
        }       

        /// <summary>
        /// Create a PreBillInvoiceDetails model
        /// </summary>
        /// <param name="letterTemplateId"></param>
        /// <param name="invoiceType"></param>
        /// <param name="notes"></param>
        /// <param name="freeFormNotes"></param>
        /// <returns></returns>
        private static void CreatePreBillInvoiceDetails(PreBillInvoiceDetails preBillInvoiceDetails, 
                                                        long letterTemplateId, 
                                                        string letterTemplateName,
                                                        string invoiceType,
                                                        List<string> notes)                                                        
        {
            preBillInvoiceDetails.LetterTemplateId = letterTemplateId;
            preBillInvoiceDetails.LetterTemplateName = letterTemplateName;
            preBillInvoiceDetails.InvoiceType = invoiceType;

            preBillInvoiceDetails.Notes.Clear();

            foreach (string note in notes)
            {
                preBillInvoiceDetails.Notes.Add(note);
            }
        }
       
        #endregion

        #region Properties

        /// <summary>
        /// Get the BillingController Instance
        /// </summary>
        public new static BillingController Instance
        {
            get
            {
                if (billingController == null)
                {
                    lock (syncRoot)
                    {
                        if (billingController == null)
                        {
                            billingController = new BillingController();
                        }
                    }
                }
                return billingController;
            }
        }

        #endregion
    }
}
