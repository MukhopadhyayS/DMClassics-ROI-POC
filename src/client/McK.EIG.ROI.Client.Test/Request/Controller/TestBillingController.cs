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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;
using System.IO;
using System.Reflection;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Test.Requestors.Controller;

using McK.EIG.ROI.Client.Web_References.BillingWS;
using McK.EIG.ROI.Client.Web_References.BillingCoreWS;
using McK.EIG.ROI.Client.Web_References.OutputServiceWS;

namespace McK.EIG.ROI.Client.Test.Request.Controller
{
    /// <summary>
    /// Test case for Billing Controller.
    /// </summary>
    [TestFixture]
    [Category("TestBillingController")]
    public class TestBillingController : TestBase
    {
        #region Fields

        private BillingController billingController;
        private RequestController requestController;
        private ROIAdminController roiAdminController;
        private BillingAdminController billingAdminController;

        private RequestDetails requestDetails;
        private RequestorTypeDetails requestorTypeDetails;
        private BillingTierDetails billingTierDetails;
        private ReleaseDetails releaseDetails;
        private PreBillInvoiceDetails preBillInvoiceDetails;
        private DeliveryMethodDetails shippingDeliveryMethod;
        private ReleaseAndPreviewInfo releaseAndPreviewInfo;

        private DocumentInfo invoiceDoumentInfo;
        private DocumentInfo preBillDocumentInfo;
        private DocumentInfo letterDocumentInfo;

        private OutputDestinationDetails outputDestinationDetails;
        private long pageWeight;
        private long id;
        private Collection<LetterTemplateDetails> letterTemplates;

        private InvoiceAndDocumentDetails invoiceAndDocumentDetails;

        private const string OutputType = "type";

        #endregion

        #region Methods

        #region NUnit

        [SetUp]
        public void Init()
        {
            billingController = BillingController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            billingController = null;
        }
        #endregion

        public void Initlogon()
        {
            billingController = BillingController.Instance;
            requestController = RequestController.Instance;
            roiAdminController = ROIAdminController.Instance;
            billingAdminController = BillingAdminController.Instance;

            letterTemplates = roiAdminController.RetrieveAllLetterTemplates();

            //Create Request.            
            requestDetails = CreateRequest();

            requestDetails.Requestor = RequestorController.Instance.RetrieveRequestor(requestDetails.RequestorId, false);
            //Get the Requestor Type Details for the created Request.
            requestorTypeDetails = roiAdminController.GetRequestorType(requestDetails.Requestor.Type);

            billingTierDetails = billingAdminController.GetBillingTier(requestorTypeDetails.HpfBillingTier.Id);

            shippingDeliveryMethod = CreateDeliveryMethod();
            pageWeight = GetPageWeight();

        }
        /// <summary>
        /// Test Save Request Core Charges
        /// </summary>
        [Test]
        public void Test01SaveRequestCoreCharges()
        {
            Initlogon();
            RequestCoreChargeDetails requestCore = new RequestCoreChargeDetails();
            requestCore.RequestId = requestDetails.Id;
            requestCore.BillingLoc = requestDetails.DefaultFacility.FacilityCode;
            requestCore.PreviouslyReleasedCost = 10;
            requestCore.ReleaseDate = DateTime.Now;
            requestCore.ReleasedCost = 20;
            requestCore.TotalPages = 10;
            requestCore.TotalRequestCost = 30;
            requestCore.BalanceDue = 20;
            requestCore.TotalPagesReleased = 5;
            requestCore.SalestaxPercentageForBillingLoc = requestDetails.DefaultFacility.TaxPercentage;
            requestCore.BillingType = "AD";
            requestCore.ApplySalesTax = true;
            requestCore.BillingLocCode = requestDetails.DefaultFacility.FacilityCode;
            requestCore.BillingLocName = requestDetails.DefaultFacility.FacilityName;
            requestCore.CreditAdjustmentAmount = 2;
            requestCore.DebitAdjustmentAmount = 3;
            requestCore.OriginalBalance = 5;
            requestCore.PaymentAmount = 3;
            requestCore.SalestaxTotalAmount = 1;

            List<DocumentChargeDetails> documentChargeDetails = new List<DocumentChargeDetails>();

            DocumentChargeDetails documentChargeDetail = new DocumentChargeDetails();
            documentChargeDetail.Amount = 10;
            documentChargeDetail.Copies = 2;
            documentChargeDetail.BillingTier = "Electornic";
            documentChargeDetail.TotalPages = 10;
            documentChargeDetail.Pages = 5;
            documentChargeDetail.BillingTierId = 1;
            documentChargeDetail.ReleaseCount = 1;
            documentChargeDetail.IsElectronic = true;
            documentChargeDetail.HasSalesTax = true;
            documentChargeDetail.TaxAmount = 2;
            documentChargeDetail.RemoveBaseCharge = true;
            documentChargeDetails.Add(documentChargeDetail);

            requestCore.BillingCharge.DocumentCharge = documentChargeDetails;

            List<FeeChargeDetails> feeChargeDetails = new List<FeeChargeDetails>();

            FeeChargeDetails feeChargeDetail = new FeeChargeDetails();
            feeChargeDetail.Amount = 10;
            feeChargeDetail.IsCustomFee = false;
            feeChargeDetail.FeeType = "Test Fee";
            feeChargeDetail.HasSalesTax = true;
            feeChargeDetail.TaxAmount = 2;
            feeChargeDetails.Add(feeChargeDetail);
            requestCore.BillingCharge.FeeCharge = feeChargeDetails;

            requestCore.ShippingInfo.ShippingCharge = 25;
            requestCore.ShippingInfo.ShippingAddress = new AddressDetails();
            requestCore.ShippingInfo.ShippingAddress.PostalCode = "600001";
            requestCore.ShippingInfo.AddressType = RequestorAddressType.Main;
            requestCore.ShippingInfo.ShippingAddress.State = "TN";
            requestCore.ShippingInfo.ShippingWebAddress = shippingDeliveryMethod.Url.ToString();
            requestCore.ShippingInfo.ShippingAddress.Address1 = "address1";
            requestCore.ShippingInfo.ShippingAddress.Address2 = "address2";
            requestCore.ShippingInfo.ShippingAddress.Address3 = "address3";
            requestCore.ShippingInfo.TrackingNumber = "EZ111034565";
            requestCore.ShippingInfo.ShippingAddress.City = "City";
            requestCore.ShippingInfo.WillReleaseShipped = true;
            requestCore.ShippingInfo.OutputMethod = OutputMethod.Print;
            requestCore.ShippingInfo.ShippingMethodId = shippingDeliveryMethod.Id;

            RequestController.Instance.SaveRequestCoreCharges(requestCore);
            Assert.IsNotNull(requestCore);
        }

        /// <summary>
        /// Test Create Release
        /// </summary>
        [Test]
        public void Test02CreateRelease()
        {
            
            releaseDetails = new ReleaseDetails();

            RequestBillingInfo requestBillInfoSave = new RequestBillingInfo();
            requestBillInfoSave.RequestId = requestDetails.Id;
            requestBillInfoSave.BillingLocName = "AD";
            requestBillInfoSave.BillingLocCode = "AD";
            requestBillInfoSave.BalanceDue = 10;
            requestBillInfoSave.BillingType = "AD";
            requestBillInfoSave.CreditAdjustmentAmount = 2;
            requestBillInfoSave.DebitAdjustmentAmount = 1;
            requestBillInfoSave.OriginalBalance = 10;
            requestBillInfoSave.PaymentAmount = 2;

            requestBillInfoSave.PreviouslyReleasedCost = 0;
            requestBillInfoSave.ReleaseDate = DateTime.Now;
            requestBillInfoSave.ReleasedCost = 10;
            requestBillInfoSave.SalestaxTotalAmount = 0.90;
            requestBillInfoSave.TotalPages = 3;
            requestBillInfoSave.TotalPagesReleased = 0;
            requestBillInfoSave.TotalRequestCost = 10.90;
            requestBillInfoSave.ApplySalesTax = true;
            requestBillInfoSave.SalestaxPercentageForBillingLoc = 10;
            List<SalesTaxReasons> taxReasonList = new List<SalesTaxReasons>();
            SalesTaxReasons taxReason = new SalesTaxReasons();
            taxReason.Id = requestDetails.Id;
            taxReason.Reason = "First Release";
            taxReason.CreatedDate = DateTime.Now;
            taxReasonList.Add(taxReason);
                       
            requestBillInfoSave.SalesTaxReasonsList = taxReasonList;

            List<DocumentChargeDetails> list = new List<DocumentChargeDetails>();
            DocumentChargeDetails doc = new DocumentChargeDetails();
            doc.Copies = 2;
            doc.BillingTier = billingTierDetails.Name;
            doc.TotalPages = 3;
            doc.Pages = 3;
            doc.Amount = 2;
            doc.BillingTierId = billingTierDetails.Id;
            doc.ReleaseCount = 1;
            doc.IsElectronic = true;
            doc.RemoveBaseCharge = true;
            doc.TaxAmount = 1;
            doc.HasSalesTax = true;
            list.Add(doc);
            
            requestBillInfoSave.BillingCharge.DocumentCharge = list;

            List<FeeChargeDetails> list1 = new List<FeeChargeDetails>();

            FeeChargeDetails fee = new FeeChargeDetails();
            fee.Amount = 7;
            fee.IsCustomFee = true;
            fee.FeeType = "Custome FeeType";
            fee.TaxAmount = 1;
            fee.HasSalesTax = true;
            list1.Add(fee);
            
            requestBillInfoSave.BillingCharge.FeeCharge = list1;

            requestBillInfoSave.ShippingInfo.ShippingCharge = 25;
            requestBillInfoSave.ShippingInfo.ShippingAddress = new AddressDetails();
            requestBillInfoSave.ShippingInfo.ShippingAddress.PostalCode = "600000";
            requestBillInfoSave.ShippingInfo.AddressType = RequestorAddressType.Main;

            requestBillInfoSave.ShippingInfo.ShippingAddress.State = "TN";
            requestBillInfoSave.ShippingInfo.ShippingWebAddress = shippingDeliveryMethod.Url.ToString();
            requestBillInfoSave.ShippingInfo.ShippingAddress.Address1 = "address1";
            requestBillInfoSave.ShippingInfo.ShippingAddress.Address2 = "address2";
            requestBillInfoSave.ShippingInfo.ShippingAddress.Address3 = "address3";
            requestBillInfoSave.ShippingInfo.TrackingNumber = "EZ111034565";
            requestBillInfoSave.ShippingInfo.ShippingAddress.City = "City";
            requestBillInfoSave.ShippingInfo.ShippingAddress.CountryCode = "Ind";
            requestBillInfoSave.ShippingInfo.ShippingAddress.CountryName = "India";
            requestBillInfoSave.ShippingInfo.ShippingAddress.NewCountry = false;
            requestBillInfoSave.ShippingInfo.ShippingAddress.CountrySeq = 1;
            requestBillInfoSave.ShippingInfo.WillReleaseShipped = true;
            requestBillInfoSave.ShippingInfo.OutputMethod = OutputMethod.Print;
            requestBillInfoSave.ShippingInfo.ShippingMethodId = shippingDeliveryMethod.Id;
            releaseDetails.ShippingDetails = requestBillInfoSave.ShippingInfo;
            BillingValidator validate = new BillingValidator();
            validate.ValidateShipping(releaseDetails);

            List<RequestCoreChargesInvoiceDetail> reqCoreInvoiceList = new List<RequestCoreChargesInvoiceDetail>();
            RequestCoreChargesInvoiceDetail reqChargesDet = new RequestCoreChargesInvoiceDetail();

            List<RequestTransaction> reqtrList = new List<RequestTransaction>();

            reqChargesDet = new RequestCoreChargesInvoiceDetail();
            reqChargesDet.releaseCost = 2;
            reqChargesDet.invoiceCreatedDt = DateTime.Now;
            reqChargesDet.requestCoreDeliveryChargesId = 1;

            RequestTransaction transaction = new RequestTransaction();
            transaction.Id = 1;
            reqChargesDet.requestCoreDeliveryChargesId = transaction.Id;
            reqChargesDet.ReqTransaction.Add(transaction);
            reqCoreInvoiceList.Add(reqChargesDet);

            requestBillInfoSave.ReqCoreChargesInvoiceDetail = reqCoreInvoiceList;

            Assert.IsNotNull(requestBillInfoSave);
        }

        /// <summary>
        /// Test create Prebill
        /// </summary>
        [Test]
        public void Test03CreateInvoiceOrPrebill()
        {
            preBillInvoiceDetails = new PreBillInvoiceDetails();

            preBillInvoiceDetails.Release.Id = releaseDetails.Id;
            preBillInvoiceDetails.Release.RequestId = requestDetails.Id;
            LetterTemplateDetails preBillLetterTemplate = RetrieveDocumentID(LetterType.PreBill);
            preBillInvoiceDetails.LetterTemplateId = preBillLetterTemplate.DocumentId;
            preBillInvoiceDetails.LetterTemplateName = preBillLetterTemplate.Name;
            preBillInvoiceDetails.BalanceDue = "123.45";
            preBillInvoiceDetails.TotalPagesPerRelease = 5;
            preBillInvoiceDetails.Release.ShippingDetails = releaseDetails.ShippingDetails;
            preBillInvoiceDetails.DateCreated = DateTime.Today;

            ReleasedPatientDetails releasedPatient;
            foreach (RequestPatientDetails requestPatientDetails in releaseDetails.ReleasedPatients.Values)
            {
                releasedPatient = new ReleasedPatientDetails();
                preBillInvoiceDetails.Release.ReleasedPatients.Add(requestPatientDetails.Key, releasedPatient);
            }

            foreach (DocumentChargeDetails documentChargeDetails in releaseDetails.DocumentCharges)
            {
                preBillInvoiceDetails.Release.DocumentCharges.Add(documentChargeDetails);
            }

            foreach (FeeChargeDetails feeChargeDetails in releaseDetails.FeeCharges)
            {
                preBillInvoiceDetails.Release.FeeCharges.Add(feeChargeDetails);
            }

            foreach (RequestTransaction requestTransaction in releaseDetails.RequestTransactions)
            {
                preBillInvoiceDetails.Release.RequestTransactions.Add(requestTransaction);
            }

            preBillInvoiceDetails.RequestStatus = "Logged";
            preBillInvoiceDetails.StatusReasons = "Reason1" + ROIConstants.StatusReasonDelimiter + "Reason2";

            Collection<NotesDetails> notes = new Collection<NotesDetails>();

            NotesDetails note1 = new NotesDetails();

            note1.Id = 1;
            note1.DisplayText = "Test1";

            NotesDetails note2 = new NotesDetails();

            note2.Id = 2;
            note2.DisplayText = "Test2";

            preBillInvoiceDetails.Notes.Add(note1.DisplayText);
            preBillInvoiceDetails.Notes.Add(note2.DisplayText);

            preBillInvoiceDetails.Notes.Add("Freeform1");
            preBillInvoiceDetails.Notes.Add("Freeform2");
            preBillInvoiceDetails.InvoiceType = LetterType.PreBill.ToString();

            InvoiceAndDocumentDetails invoiceAndDocumentDetails = new InvoiceAndDocumentDetails();
            InvoiceOrPrebillPreviewInfo InvoiceInfo = new InvoiceOrPrebillPreviewInfo();
            InvoiceInfo.Amountpaid = 2;
                    
            InvoiceInfo.BaseCharge = 4;
            InvoiceInfo.InvoiceBalanceDue = 2;
                    
            InvoiceInfo.InvoiceBillingLocCode = "AD";
            InvoiceInfo.InvoiceBillinglocName = "AD";
            InvoiceInfo.InvoicePrebillDate = DateTime.Now;
            InvoiceInfo.InvoiceSalesTax = 4;
            InvoiceInfo.LetterTemplateFileId = preBillLetterTemplate.DocumentId;
            InvoiceInfo.LetterTemplateName = preBillLetterTemplate.Name;
            int i = 0;
            InvoiceInfo.Notes = new string[preBillInvoiceDetails.Notes.Count];
            foreach (string note in preBillInvoiceDetails.Notes)
            {
                InvoiceInfo.Notes[i] = preBillInvoiceDetails.Notes[i];
                i++;
            }
                                   
            InvoiceInfo.OutputMethod = "Print";
            InvoiceInfo.OverwriteDueDate = true;
            
            InvoiceInfo.QueuePassword = "1234";
            InvoiceInfo.RequestCoreId = requestDetails.Id;
            InvoiceInfo.RequestStatus = "Logged";
            InvoiceInfo.RequestDate = DateTime.Now;
            InvoiceInfo.InvoiceDueDate = DateTime.Now;
            InvoiceInfo.ResendDate = DateTime.Now;
            InvoiceInfo.Type = "DOCX";
            InvoiceInfo.LetterType = "test";
            invoiceAndDocumentDetails = requestController.InvoiceOrPrebillPreview(InvoiceInfo);
            this.invoiceAndDocumentDetails = invoiceAndDocumentDetails;
            Assert.IsNotNull(invoiceAndDocumentDetails);

            string filePath = Path.GetTempPath() + BillingController.DirectoryPath;

            if (Directory.Exists(filePath))
            {
                string[] files = Directory.GetFiles(filePath);

                foreach (string path in files)
                {
                    File.Delete(path);
                }
                Directory.Delete(filePath);
            }

            filePath = BillingController.DownloadLetterTemplate(invoiceAndDocumentDetails.DocumentName, null);            

            Assert.IsNotNull(filePath);
        }

        /// <summary>
        /// Test create Release and Preview
        /// </summary>
        [Test]
        public void Test04CreateReleaseAndPreview()
        {
            
            ReleaseCore releaseCore = new ReleaseCore();
            releaseCore.requestId = requestDetails.Id;
            LetterTemplateDetails coverLetterTemplate = RetrieveDocumentID(LetterType.CoverLetter);
            releaseCore.coverLetterFileId = coverLetterTemplate.DocumentId;
            releaseCore.coverLetterRequired = true;
            releaseCore.invoiceDueDays = 0;
            releaseCore.invoiceRequired = true;
            LetterTemplateDetails invoiceLetterTemplate = RetrieveDocumentID(LetterType.Invoice);
            releaseCore.invoiceTemplateId = invoiceLetterTemplate.Id;
            releaseCore.notes = new string[] { "Notes1", "Notes2" };

            InvoiceOrPrebillAndPreviewInfo invoiceOrPrebillAndPreviewInfo = new InvoiceOrPrebillAndPreviewInfo();

            invoiceOrPrebillAndPreviewInfo.amountpaid = 2;
            invoiceOrPrebillAndPreviewInfo.baseCharge = 10;
            invoiceOrPrebillAndPreviewInfo.invoiceBalanceDue = 8;
            invoiceOrPrebillAndPreviewInfo.invoiceBillingLocCode = "AD";
            invoiceOrPrebillAndPreviewInfo.invoiceBillinglocName = "AD";
            invoiceOrPrebillAndPreviewInfo.invoiceDueDate = DateTime.Now;
            invoiceOrPrebillAndPreviewInfo.invoiceDueDays = 0;
            invoiceOrPrebillAndPreviewInfo.invoicePrebillDate = DateTime.Now;
            invoiceOrPrebillAndPreviewInfo.invoiceSalesTax = 1;
            invoiceOrPrebillAndPreviewInfo.letterTemplateFileId = invoiceLetterTemplate.DocumentId;
            invoiceOrPrebillAndPreviewInfo.letterTemplateName = invoiceLetterTemplate.Name;
            invoiceOrPrebillAndPreviewInfo.notes = new string[] { "Notes1", "Notes2" };
            invoiceOrPrebillAndPreviewInfo.outputMethod = OutputMethod.Print.ToString();
            invoiceOrPrebillAndPreviewInfo.type = "DOCX";
            invoiceOrPrebillAndPreviewInfo.queuePassword = "1234";
            invoiceOrPrebillAndPreviewInfo.requestCoreId = requestDetails.Id;
            invoiceOrPrebillAndPreviewInfo.requestStatus = "Logged";
            invoiceOrPrebillAndPreviewInfo.overwriteDueDate = true;
            invoiceOrPrebillAndPreviewInfo.requestDate = DateTime.Now;
            invoiceOrPrebillAndPreviewInfo.resendDate = DateTime.Now;
            invoiceOrPrebillAndPreviewInfo.letterType = LetterType.Invoice.ToString();
            
            List<RequestCoreDeliveryChargesAdjustmentPayment> adjustmentPayments = new List<RequestCoreDeliveryChargesAdjustmentPayment>();
            RequestCoreDeliveryChargesAdjustmentPayment requestCoreDeliveryChargesAdjustmentPayment;
            requestCoreDeliveryChargesAdjustmentPayment = new RequestCoreDeliveryChargesAdjustmentPayment();
            requestCoreDeliveryChargesAdjustmentPayment.description = "By check";
            requestCoreDeliveryChargesAdjustmentPayment.transactionType = "Payment";
            requestCoreDeliveryChargesAdjustmentPayment.isDebit = false;
            requestCoreDeliveryChargesAdjustmentPayment.amount = 2;
            requestCoreDeliveryChargesAdjustmentPayment.paymentMode = "By Check";
            requestCoreDeliveryChargesAdjustmentPayment.paymentDate = DateTime.Now;
            requestCoreDeliveryChargesAdjustmentPayment.newlyAdded = true;
            adjustmentPayments.Add(requestCoreDeliveryChargesAdjustmentPayment);
            invoiceOrPrebillAndPreviewInfo.autoAdjustments = adjustmentPayments.ToArray();
            
            releaseCore.invoiceOrPrebillAndPreviewInfo = invoiceOrPrebillAndPreviewInfo;
            
            ReleaseAndPreviewInfo response = billingController.CreateReleaseAndPreview(releaseCore, false, 0);
            releaseAndPreviewInfo = response;
            Assert.IsNotNull(response);
            Assert.IsTrue(response.releaseId > 0);
        }

        /// <summary>
        /// Test to retreive the release
        /// </summary>
        [Test]
        public void Test05RetrieveRelease()
        {
            ReleaseDetails release = releaseDetails;
            release.DocumentCharges.Clear();
            release.FeeCharges.Clear();
            release.RequestTransactions.Clear();

            RequestBillingInfo billingInfo = requestController.RetrieveRequestBillingPaymentInfo(requestDetails.Id);
            Assert.IsNotNull(billingInfo);
            Assert.IsTrue(billingInfo.BillingCharge.DocumentCharge.Count > 0);
            Assert.IsTrue(billingInfo.BillingCharge.FeeCharge.Count > 0);
        }

        /// <summary>
        /// Test Is Display billing Info
        /// </summary>
        [Test]
        public void Test06IsDisplayBillingInfo()
        {
            BillingController.Instance.IsDisplayBillingInfo(requestDetails.Id, false);
        }

        /// <summary>
        /// Test Create Audit And Events For Auto Apply To Invoice
        /// </summary>
        [Test]
        public void Test07CreateAuditAndEventsForAutoApplyToInvoice()
        {
            BillingController.Instance.CreateAuditAndEventsForAutoApplyToInvoice(requestDetails.Id, 
                                this.invoiceAndDocumentDetails.DocumentInfos.DocumentInfoCollection[0].Id);
            Assert.IsTrue(requestDetails.Id > 0);
        }
                
        /// <summary>
        /// Test case for retrieving request patient release histories.
        /// </summary>
        [Test]
        public void Test07RetrieveReleaseHistory()
        {
            ReleaseHistoryDetails releaseHistories = billingController.RetrieveReleaseHistoryList(requestDetails.Id,"Test","Self Pay Encounter");
            Assert.IsTrue(releaseHistories.ReleaseItems.Count > 0);
        }

        /// <summary>
        /// Test Retrieve charge history
        /// </summary>
        [Test]
        public void Test08RetrieveChargeHistory()
        {
            Collection<ChargeHistoryDetails> chargeHistoryDetails = billingController.RetrieveChargeHistory(requestDetails.Id);
            Assert.IsTrue(chargeHistoryDetails.Count > 0);
        }

        /// <summary>
        /// Retrieve Released Document charges
        /// </summary>
        [Test]
        public void Test09RetrieveReleasedDocumentCharges()
        {
            List<DocumentChargeDetails> documentChargeDetails = requestController.RetrieveReleasedDocumentCharges(requestDetails.Id);
            Assert.IsTrue(documentChargeDetails.Count > 0);
        }

        /// <summary>
        /// Test create Prebill
        /// </summary>
        [Test]
        public void Test10CreateInvoice()
        {
            LetterTemplateDetails invoiceLetter = RetrieveDocumentID(LetterType.Invoice);
            preBillInvoiceDetails.LetterTemplateId = invoiceLetter.DocumentId;
            preBillInvoiceDetails.LetterTemplateName = invoiceLetter.Name;
            preBillInvoiceDetails.InvoiceType = LetterType.Invoice.ToString();
            InvoiceDet invoiceDetails = new InvoiceDet();
            invoiceDetails.BaseCharge = 10;
            invoiceDetails.AmountPaid = 0;
            invoiceDetails.BalanceDue = 10;
            invoiceDetails.BillingLocationCode = "AD";
            invoiceDetails.BillingLocationName = "AD";
            invoiceDetails.SalesTax = 1;
            preBillInvoiceDetails.InvoiceDueDate = DateTime.Now.AddDays(-10);
            preBillInvoiceDetails.Invoice = invoiceDetails;
            Assert.IsNotNull(invoiceAndDocumentDetails);

            string filePath = BillingController.DownloadLetterTemplate(this.invoiceAndDocumentDetails.DocumentName, null);

            Assert.IsNotNull(filePath);
        }

        /// <summary>
        /// Test Retrieve/Update Adjustment Payment invoices
        /// </summary>
        [Test]
        public void Test12RetrieveAdjPayInvoices()
        {
            InvoiceChargeDetailsList invoiceChargeDetailsList = new InvoiceChargeDetailsList();
            invoiceChargeDetailsList = requestController.RetrieveInvoicesAndAdjPay(preBillInvoiceDetails.Release.RequestId);
            invoiceChargeDetailsList.CurrentRequestBalanceDue += 1;
            Assert.IsTrue(invoiceChargeDetailsList != null);
        }

        /// <summary>
        /// Test Create Letter
        /// </summary>
        [Test]
        public void Test13CreateLetter()
        {
            string filePath = Path.GetTempPath() + BillingController.DirectoryPath;

            if (Directory.Exists(filePath))
            {
                string[] files = Directory.GetFiles(filePath);

                foreach (string path in files)
                {
                    File.Delete(path);
                }
                Directory.Delete(filePath);
            }

            LetterTemplateDetails otherLetter = RetrieveDocumentID(LetterType.CoverLetter);
            preBillInvoiceDetails.LetterTemplateId = otherLetter.DocumentId;
            preBillInvoiceDetails.LetterTemplateName = otherLetter.Name;
            preBillInvoiceDetails.InvoiceType = LetterType.Other.ToString();
            string[] notes = {"test1","test2"};
            letterDocumentInfo = BillingController.Instance.CreateLetter(preBillInvoiceDetails.LetterTemplateId, requestDetails.Id, notes);

            CommentDetails details = new CommentDetails();

            details.RequestId = requestDetails.Id;
            details.EventType = EventType.LetterSend;
            details.EventRemarks = "From Test Case";
            RequestController.Instance.CreateComment(details);

            Assert.IsNotNull(letterDocumentInfo);
        }

         /// <summary>
        /// Test update invoice output properties
        /// </summary>
        [Test]
        public void Test14updateInvoiceOutputProperties()
        {
            BillingController.Instance.updateInvoiceOutputProperties(letterDocumentInfo.Id, letterDocumentInfo.Id, releaseAndPreviewInfo.releaseId, true, true, false, "1234", OutputMethod.Print.ToString());
            Assert.IsNotNull(letterDocumentInfo);
        }
        /// <summary>
        /// Test delete letter
        /// </summary>
        [Test]
        public void Test15DeleteLetter()
        {
            BillingController.Instance.DeleteLetter(letterDocumentInfo.Id, requestDetails.Id);
        }

        /// <summary>
        /// Test Create Letter with empty data.
        /// </summary>
        [Test]
        public void Test16CreateLetterWithEmptyData()
        {
            string filePath = Path.GetTempPath() + BillingController.DirectoryPath;

            LetterTemplateDetails otherLetter = RetrieveDocumentID(LetterType.CoverLetter);
            preBillInvoiceDetails.LetterTemplateId = otherLetter.DocumentId;
            preBillInvoiceDetails.LetterTemplateName = otherLetter.Name;
            preBillInvoiceDetails.InvoiceType = LetterType.Other.ToString();

            preBillInvoiceDetails.Release.DocumentCharges.Clear();
            preBillInvoiceDetails.Release.FeeCharges.Clear();
            preBillInvoiceDetails.Release.RequestTransactions.Clear();
            preBillInvoiceDetails.Release.ReleasedPatients.Clear();

            preBillInvoiceDetails.RequestStatus = "Logged";
            
            preBillInvoiceDetails.Release.ShippingDetails = null;

            string[] notes = {"test1","test2"};

            DocumentInfo documentInfo =  BillingController.Instance.CreateLetter(preBillInvoiceDetails.LetterTemplateId, requestDetails.Id, notes);
            Assert.IsNotNull(documentInfo);
        }

        /// <summary>
        /// Test Create Letter with empty data.
        /// </summary>
        [Test]
        public void Test17CreateLetterWithEmptyData()
        {
            string filePath = Path.GetTempPath() + BillingController.DirectoryPath;

            LetterTemplateDetails otherLetter = RetrieveDocumentID(LetterType.CoverLetter);
            preBillInvoiceDetails.LetterTemplateId = otherLetter.DocumentId;
            preBillInvoiceDetails.LetterTemplateName = otherLetter.Name;
            preBillInvoiceDetails.InvoiceType = LetterType.Other.ToString();

            preBillInvoiceDetails.Release.DocumentCharges.Clear();
            preBillInvoiceDetails.Release.FeeCharges.Clear();
            preBillInvoiceDetails.Release.RequestTransactions.Clear();
            preBillInvoiceDetails.Release.ReleasedPatients.Clear();

            preBillInvoiceDetails.RequestStatus = "Logged";

            ShippingInfo shippingDetails = new ShippingInfo();

            preBillInvoiceDetails.Release.ShippingDetails = shippingDetails;
            preBillInvoiceDetails.Release.ShippingDetails.ShippingAddress = null;

            string[] notes = { "test1", "test2" };

            DocumentInfo documentInfo = BillingController.Instance.CreateLetter(preBillInvoiceDetails.LetterTemplateId, requestDetails.Id, notes);

            Assert.IsNotNull(documentInfo);
        }

        /// <summary>
        /// Test case for fetching request details.
        /// </summary>
        [Test]
        public void Test19RetrieveRequest()
        {
            RequestDetails request = RequestController.Instance.RetrieveRequest(requestDetails.Id, false);
            bool partiallyReleased = request.IsPartiallyReleased;
            bool patientWithoutRelease = request.HasPatientWithNoReleaseRecords;
            Assert.IsNotNull(request);
        }


        /// <summary>
        /// Test case for retrieve past invoice details
        /// </summary>
        [Test]
        public void Test20RetrievePastInvoiceDetails()
        {
            Collection<PastInvoiceDetails> pastInvoiceDetails = billingController.RetrievePastInvoiceDetails(requestDetails.Id);
            Assert.IsTrue(pastInvoiceDetails.Count > 0);
        }

         /// <summary>
        /// Test query destination
        /// </summary>
        [Test]
        public void Test21RetrieveDestinations()
        {
            OutputPropertyDetails outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
            Collection<OutputDestinationDetails> outputDestinations = outputPropertyDetails.OutputDestinationDetails;
            outputDestinationDetails = outputDestinations[0];
            Assert.IsNotNull(outputPropertyDetails);
        }

        /// <summary>
        /// Test submit output request.
        /// </summary>
        [Test]
        public void Test22SubmitOutputRequestWithPrint()
        {
            OutputRequestDetails outputRequestDetails = new OutputRequestDetails(requestDetails.Id, releaseDetails.Id, "test", requestDetails.ReceiptDate);
            outputRequestDetails.OutputDestinationDetails = outputDestinationDetails;

            //Build ROI request part

            RequestPartDetails roiRequestPartDetails = new RequestPartDetails();

            roiRequestPartDetails.ContentId = "";
            roiRequestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails roiPropertyDetails = new PropertyDetails();            
            //roiPropertyDetails.FileIds = invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Type + "." + 
                                         //invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Id;
            roiRequestPartDetails.PropertyLists.Add(roiPropertyDetails);

            outputRequestDetails.RequestParts.Add(roiRequestPartDetails);
       
            //Build HPF request part

            RequestPartDetails hpfRequestPartDetails = new RequestPartDetails();
            hpfRequestPartDetails.ContentSource = OutputPropertyDetails.HpfContentSource;

            SortedList<string, ReleasedPatientDetails> releasedPatients = releaseDetails.ReleasedPatients;

            foreach (ReleasedPatientDetails releasedPatientDetails in releasedPatients.Values)
            {
                IList<BaseRequestItem> encounters = releasedPatientDetails.GetChildren;

                foreach (RequestEncounterDetails enc in encounters)
                {
                    foreach (RequestDocumentDetails doc in enc.GetChildren)
                    {
                        PropertyDetails propertyDetails = new PropertyDetails();

                        propertyDetails.PatientName = releasedPatientDetails.Name;
                        propertyDetails.MRN = releasedPatientDetails.MRN;
                        propertyDetails.Facility = releasedPatientDetails.FacilityCode;
                        propertyDetails.Encounter = enc.Name;
                        propertyDetails.DocumentName = doc.Name;
                        propertyDetails.DocumentSubtitle = doc.Subtitle;
                        propertyDetails.DocumentType = doc.DocType;
                        propertyDetails.Key = doc.DocumentId + "-" + doc.DocTypeId;
                        if (!string.IsNullOrEmpty(doc.ChartOrder))
                        {
                            propertyDetails.Key += "-" + doc.ChartOrder;
                        }
                        if (doc.DocumentDateTime.HasValue)
                        {
                            propertyDetails.DocumentDateTime = doc.DocumentDateTime;
                        }

                        foreach (RequestVersionDetails version in doc.GetChildren)
                        {
                            string IMNetIds = "";
                            string pages = "";

                            foreach (RequestPageDetails page in version.GetChildren)
                            {
                                IMNetIds += page.IMNetId + ",";
                                pages += page.Name + ",";
                            }

                            propertyDetails.IMNetIds = IMNetIds.TrimEnd(',');
                            propertyDetails.Pages = pages.TrimEnd(',');
                        }
                        hpfRequestPartDetails.PropertyLists.Add(propertyDetails);
                    }
                }
            }

            outputRequestDetails.RequestParts.Add(hpfRequestPartDetails);

            OutputViewDetails outputViewDetails = new OutputViewDetails();
            outputViewDetails.IsHeaderEnabled = true;
            outputViewDetails.IsHeader = true;
            outputViewDetails.IsPageSeparator = true;
            outputViewDetails.IsWatermark = true;
            outputViewDetails.Watermark = "Test";

            int jobStatus = (int)OutputController.Instance.SubmitOutputRequest(outputRequestDetails, DestinationType.Print,
                                                                                outputViewDetails, true);
            Assert.IsNotNull(jobStatus);
        }


        /// <summary>
        /// Test submit output request.
        /// </summary>
        [Test]
        public void Test23SubmitOutputRequestWithFile()
        {
            OutputRequestDetails outputRequestDetails = new OutputRequestDetails(requestDetails.Id,
                                                                                 releaseDetails.Id,
                                                                                 "test",
                                                                                 requestDetails.ReceiptDate);

            OutputPropertyDetails outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
            Collection<OutputDestinationDetails> outputDestinations = outputPropertyDetails.OutputDestinationDetails;

            outputRequestDetails.OutputDestinationDetails = outputDestinations[0];
            outputRequestDetails.OutputDestinationDetails.Media = "CD";
            outputRequestDetails.OutputDestinationDetails.Password = "Test";
            //Build ROI request part

            RequestPartDetails roiRequestPartDetails = new RequestPartDetails();

            roiRequestPartDetails.ContentId = "";
            roiRequestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails roiPropertyDetails = new PropertyDetails();
            //roiPropertyDetails.FileIds = invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Type + "." +
            //                             invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Id;
            roiRequestPartDetails.PropertyLists.Add(roiPropertyDetails);

            outputRequestDetails.RequestParts.Add(roiRequestPartDetails);

            //Build HPF request part

            RequestPartDetails hpfRequestPartDetails = new RequestPartDetails();
            hpfRequestPartDetails.ContentSource = OutputPropertyDetails.HpfContentSource;

            SortedList<string, ReleasedPatientDetails> releasedPatients = releaseDetails.ReleasedPatients;

            foreach (ReleasedPatientDetails releasedPatientDetails in releasedPatients.Values)
            {
                IList<BaseRequestItem> encounters = releasedPatientDetails.GetChildren;

                foreach (RequestEncounterDetails enc in encounters)
                {
                    foreach (RequestDocumentDetails doc in enc.GetChildren)
                    {
                        PropertyDetails propertyDetails = new PropertyDetails();

                        propertyDetails.PatientName = releasedPatientDetails.Name;
                        propertyDetails.MRN = releasedPatientDetails.MRN;
                        propertyDetails.Facility = releasedPatientDetails.FacilityCode;
                        propertyDetails.Encounter = enc.Name;
                        propertyDetails.DocumentName = doc.Name;
                        propertyDetails.DocumentSubtitle = doc.Subtitle;
                        propertyDetails.DocumentType = doc.DocType;

                        foreach (RequestVersionDetails version in doc.GetChildren)
                        {
                            string IMNetIds = "";
                            string pages = "";

                            foreach (RequestPageDetails page in version.GetChildren)
                            {
                                IMNetIds += page.IMNetId + ",";
                                pages += page.Name + ",";
                            }

                            propertyDetails.IMNetIds = IMNetIds.TrimEnd(',');
                            propertyDetails.Pages = pages.TrimEnd(',');
                        }
                        hpfRequestPartDetails.PropertyLists.Add(propertyDetails);
                    }
                }
            }
            OutputViewDetails outputViewDetails = new OutputViewDetails();

            outputRequestDetails.RequestParts.Add(hpfRequestPartDetails);
            int jobStatus = (int)OutputController.Instance.SubmitOutputRequest(outputRequestDetails, DestinationType.File,
                                                                                outputViewDetails, false);
            Assert.IsNotNull(jobStatus);
        }

        /// <summary>
        /// Test submit output request.
        /// </summary>
        [Test]
        public void Test24SubmitOutputRequestWithFax()
        {
            OutputRequestDetails outputRequestDetails = new OutputRequestDetails(requestDetails.Id,
                                                                                    releaseDetails.Id,
                                                                                    "test",
                                                                                    requestDetails.ReceiptDate);

            OutputPropertyDetails outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.Fax.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
            Collection<OutputDestinationDetails> outputDestinations = outputPropertyDetails.OutputDestinationDetails;

            outputRequestDetails.OutputDestinationDetails = outputDestinations[0];
            outputRequestDetails.OutputDestinationDetails.Fax = "5163";

            //Build ROI request part

            RequestPartDetails roiRequestPartDetails = new RequestPartDetails();

            roiRequestPartDetails.ContentId = "";
            roiRequestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails roiPropertyDetails = new PropertyDetails();
            //roiPropertyDetails.FileIds = invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Type + "." +
            //                             invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Id;
            roiRequestPartDetails.PropertyLists.Add(roiPropertyDetails);

            outputRequestDetails.RequestParts.Add(roiRequestPartDetails);

            //Build HPF request part

            RequestPartDetails hpfRequestPartDetails = new RequestPartDetails();
            hpfRequestPartDetails.ContentSource = OutputPropertyDetails.HpfContentSource;

            SortedList<string, ReleasedPatientDetails> releasedPatients = releaseDetails.ReleasedPatients;

            foreach (ReleasedPatientDetails releasedPatientDetails in releasedPatients.Values)
            {
                IList<BaseRequestItem> encounters = releasedPatientDetails.GetChildren;

                foreach (RequestEncounterDetails enc in encounters)
                {
                    foreach (RequestDocumentDetails doc in enc.GetChildren)
                    {
                        PropertyDetails propertyDetails = new PropertyDetails();

                        propertyDetails.PatientName = releasedPatientDetails.Name;
                        propertyDetails.MRN = releasedPatientDetails.MRN;
                        propertyDetails.Facility = releasedPatientDetails.FacilityCode;
                        propertyDetails.Encounter = enc.Name;
                        propertyDetails.DocumentName = doc.Name;
                        propertyDetails.DocumentSubtitle = doc.Subtitle;
                        propertyDetails.DocumentType = doc.DocType;

                        foreach (RequestVersionDetails version in doc.GetChildren)
                        {
                            string IMNetIds = "";
                            string pages = "";

                            foreach (RequestPageDetails page in version.GetChildren)
                            {
                                IMNetIds += page.IMNetId + ",";
                                pages += page.Name + ",";
                            }

                            propertyDetails.IMNetIds = IMNetIds.TrimEnd(',');
                            propertyDetails.Pages = pages.TrimEnd(',');
                        }
                        hpfRequestPartDetails.PropertyLists.Add(propertyDetails);
                    }
                }
            }

            outputRequestDetails.RequestParts.Add(hpfRequestPartDetails);
            OutputViewDetails outputViewDetails = new OutputViewDetails();
            int jobStatus = (int)OutputController.Instance.SubmitOutputRequest(outputRequestDetails, DestinationType.Fax,
                                                                                outputViewDetails, false);
            Assert.IsNotNull(jobStatus);
        }

        /// <summary>
        /// Test submit output request.
        /// </summary>
        [Test]
        public void Test25SubmitOutputRequestWithDisc()
        {
            OutputRequestDetails outputRequestDetails = new OutputRequestDetails(requestDetails.Id,
                                                                                    releaseDetails.Id,
                                                                                    "test",
                                                                                    requestDetails.ReceiptDate);

            OutputPropertyDetails outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.Disc.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
            Collection<OutputDestinationDetails> outputDestinations = outputPropertyDetails.OutputDestinationDetails;

            outputRequestDetails.OutputDestinationDetails = outputDestinations[0];
            outputRequestDetails.OutputDestinationDetails.DiscType = "Disc";

            //Build ROI request part

            RequestPartDetails roiRequestPartDetails = new RequestPartDetails();

            roiRequestPartDetails.ContentId = "";
            roiRequestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails roiPropertyDetails = new PropertyDetails();
            //roiPropertyDetails.FileIds = invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Type + "." +
            //                             invoiceDoumentInfo.DocumentInfos.DocumentInfoCollection[0].Id;
            roiRequestPartDetails.PropertyLists.Add(roiPropertyDetails);

            outputRequestDetails.RequestParts.Add(roiRequestPartDetails);

            //Build HPF request part

            RequestPartDetails hpfRequestPartDetails = new RequestPartDetails();
            hpfRequestPartDetails.ContentSource = OutputPropertyDetails.HpfContentSource;

            SortedList<string, ReleasedPatientDetails> releasedPatients = releaseDetails.ReleasedPatients;

            foreach (ReleasedPatientDetails releasedPatientDetails in releasedPatients.Values)
            {
                IList<BaseRequestItem> encounters = releasedPatientDetails.GetChildren;

                foreach (RequestEncounterDetails enc in encounters)
                {
                    foreach (RequestDocumentDetails doc in enc.GetChildren)
                    {
                        PropertyDetails propertyDetails = new PropertyDetails();

                        propertyDetails.PatientName = releasedPatientDetails.Name;
                        propertyDetails.MRN = releasedPatientDetails.MRN;
                        propertyDetails.Facility = releasedPatientDetails.FacilityCode;
                        propertyDetails.Encounter = enc.Name;
                        propertyDetails.DocumentName = doc.Name;
                        propertyDetails.DocumentSubtitle = doc.Subtitle;
                        propertyDetails.DocumentType = doc.DocType;

                        foreach (RequestVersionDetails version in doc.GetChildren)
                        {
                            string IMNetIds = "";
                            string pages = "";

                            foreach (RequestPageDetails page in version.GetChildren)
                            {
                                IMNetIds += page.IMNetId + ",";
                                pages += page.Name + ",";
                            }

                            propertyDetails.IMNetIds = IMNetIds.TrimEnd(',');
                            propertyDetails.Pages = pages.TrimEnd(',');
                        }
                        hpfRequestPartDetails.PropertyLists.Add(propertyDetails);
                    }
                }
            }
            RequestPartDetails requestPartDetails = BuildRequestPartDetails();
            outputRequestDetails.RequestParts.Add(hpfRequestPartDetails);
            outputRequestDetails.RequestParts.Add(requestPartDetails);
            OutputViewDetails outputViewDetails = new OutputViewDetails();
            int jobStatus = (int)OutputController.Instance.SubmitOutputRequest(outputRequestDetails, DestinationType.Disc,
                                                                                outputViewDetails, false);
            Assert.IsNotNull(jobStatus);
        }

        private RequestPartDetails BuildRequestPartDetails()
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();
            requestPartDetails.ContentId = string.Empty;
            requestPartDetails.ContentSource = OutputPropertyDetails.RequestContentSource;
            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.RequestID = requestDetails.Id;
            propertyDetails.RequestorName = requestDetails.Requestor.Name;
            propertyDetails.RequestCreated = DateTime.Now;
            propertyDetails.RequestCompleted = DateTime.Now.ToString();
            propertyDetails.OutputNotes = "Test" ;
            propertyDetails.TotalPageCount = 10;
            propertyDetails.PatientCount = 1;
            propertyDetails.EncounterCount = 2;
            propertyDetails.PatientName = "Test";
            propertyDetails.DOB = DateTime.Now.ToString();
            propertyDetails.MRN = "FralickM1";
            propertyDetails.Encounter = "FralickE1001";
            propertyDetails.AdmitDate = DateTime.Now.ToString();
            propertyDetails.DischargeDate = DateTime.Now.ToString();
            propertyDetails.PatientType = "CLN";
            
            requestPartDetails.PropertyLists.Add(propertyDetails);
            return requestPartDetails;
        }



        /// <summary>
        /// Delete PreBill.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test26DeletePreBill()
        {
            //BillingController.Instance.DeletePreBill(preBillDocumentInfo.Id);
            BillingController.Instance.ViewInvoiceHistory(LetterType.PreBill.ToString(), preBillDocumentInfo.Id);
        }

        /// <summary>
        /// Delete Invoice.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test27DeleteInvoice()
        {
            BillingController.Instance.DeleteInvoiceOrPreBill(this.invoiceAndDocumentDetails.DocumentInfos.DocumentInfoCollection[0].Id);
            BillingController.Instance.ViewInvoiceHistory(LetterType.Invoice.ToString(), this.invoiceAndDocumentDetails.DocumentInfos.DocumentInfoCollection[0].Id);
        }

        /// <summary>
        /// Delete Letter.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test28DeleteLetter()
        {
            BillingController.Instance.DeleteLetter(letterDocumentInfo.Id,requestDetails.Id);
        }

        /// <summary>
        /// Test case for shipping validation
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test29CreateReleaseValidateShippingInfo()
        {
            ShippingInfo shippingInfo = new ShippingInfo();
            AddressDetails shippingAddress = new AddressDetails();
            shippingAddress.State = "State%@#";
            shippingInfo.ShippingAddress = shippingAddress;
            releaseDetails.ShippingDetails = shippingInfo;
            //long id = billingController.CreateReleaseItem(releaseDetails);
        }

        /// <summary>
        /// Test case for shipping validation
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test30UpdateReleaseValidateShippingInfo()
        {
            ShippingInfo shippingInfo = new ShippingInfo();
            AddressDetails shippingAddress = new AddressDetails();
            shippingAddress.State = "State%@#";
            shippingInfo.ShippingAddress = shippingAddress;
            releaseDetails.ShippingDetails = shippingInfo;
            //ReleaseDetails updatedRelease = billingController.UpdateReleaseItem(releaseDetails);
        }

        /// <summary>
        /// Test case for deleting draft release
        /// </summary>
        [Test]
        public void Test31DeleteDraftRelease()
        {
            Test02CreateRelease();
            requestDetails.DeleteRelease = true;
            requestDetails.Requestor.IsActive = true;
            requestDetails = RequestController.Instance.UpdateRequest(requestDetails);
            Assert.IsFalse(requestDetails.HasDraftRelease);
        }

        /// <summary>
        /// Test Cancel Release
        /// </summary>
        [Test]
        public void Test32CancelRelease()
        {
            Initlogon();
            releaseDetails = new ReleaseDetails();
            releaseDetails.RequestId = 1;
            billingController.CancelRelease(releaseDetails.Id, 3);
        }

        #region Prerequisit Methods

        public double GetBillingTierAmount(int pages, int copies)
        {
            double amount = 0.00;

            if (pages == 0)
            {
                return amount;
            }

            double price = billingTierDetails.BaseCharge;
            int pageTierCount = billingTierDetails.PageTiers.Count;
            if (pageTierCount > 0)
            {
                PageLevelTierDetails lastPageTier = billingTierDetails.PageTiers[pageTierCount - 1];

                if (pages > lastPageTier.EndPage)
                {
                    price += billingTierDetails.OtherPageCharge;
                }
                else
                {
                    foreach (PageLevelTierDetails pageTier in billingTierDetails.PageTiers)
                    {
                        if (pages >= pageTier.StartPage && pages <= pageTier.EndPage)
                        {
                            price += pageTier.PricePerPage;
                            break;
                        }
                    }
                }
            }
            price *= copies;
            return price;
        }

        private DeliveryMethodDetails CreateDeliveryMethod()
        {
            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();

            deliveryMethod.Name = DateTime.Now.Ticks.ToString();
            deliveryMethod.Description = "Mail";
            deliveryMethod.Url = new Uri("http://www.usps1.com");
            deliveryMethod.IsDefault = true;

            long id = ROIAdminController.Instance.CreateDeliveryMethod(deliveryMethod);
            deliveryMethod.Id = id;
            return deliveryMethod;
        }

        private long GetPageWeight()
        {
            PageWeightDetails pagedetails = ROIAdminController.Instance.RetrieveWeight();
            return pagedetails.PageWeight;
        }

        public RequestDetails CreateRequest()
        {
            TestRequestorController requestorController = new TestRequestorController();
            requestorController.Test01CreateRequestor();

            RequestDetails newRequest = new RequestDetails();
            newRequest.Requestor = requestorController.RequestorDetails;
            newRequest.RequestorName = requestorController.RequestorDetails.LastName + ", " + requestorController.RequestorDetails.FirstName;
            newRequest.RequestorType = requestorController.RequestorDetails.Type;
            newRequest.RequestorTypeName = requestorController.RequestorDetails.Name.ToString();
            newRequest.RequestorWorkPhone = requestorController.RequestorDetails.WorkPhone;
            newRequest.Requestor.TypeName = "Insurance";
            newRequest.IsReleased = false;
            newRequest.RequestorContactName = "Jhon Smith";
            newRequest.RequestorContactPhone = "20612100";
            newRequest.RequestorFax = "2423423423";
            newRequest.RequestorHomePhone = "2699901";
            newRequest.RequestorWorkPhone = "8990777";
            newRequest.RequestReason = "Logged";
            newRequest.ReceiptDate = new DateTime(2007, 08, 08);
            newRequest.Status = RequestStatus.Logged;
            newRequest.StatusChanged = DateTime.Now.Date;
            newRequest.StatusReason = "Logged Reason1";
            newRequest.RequestorId = requestorController.RequestorDetails.Id;
            newRequest.BillToAddressLine = "No.30:sai colony";
            newRequest.BillToCity = "chennai";
            newRequest.BillToContactNameFirst = "John";
            newRequest.BillToContactNameLast = "Smith";
            newRequest.BillToPostalCode = "605111";
            newRequest.BillToState = "TN";

            newRequest = RequestController.Instance.CreateRequest(newRequest);
            RequestPatientDetails patientDetails = RetrievePatientDetails();

            RequestNonHpfDocumentDetails nonHpfDoc1 = new RequestNonHpfDocumentDetails();
            nonHpfDoc1.Comment = "Non Hpf doc1";
            nonHpfDoc1.Id = 1;
            nonHpfDoc1.DateOfService = DateTime.Now.Date;
            nonHpfDoc1.DocType = "History $ Physical";
            nonHpfDoc1.Facility = "A";
            nonHpfDoc1.Encounter = "1000";
            nonHpfDoc1.Department = "Cardio";
            nonHpfDoc1.Subtitle = "History $ Physical";
            nonHpfDoc1.PageCount = 4;
            nonHpfDoc1.SelectedForRelease = true;

            patientDetails.NonHpfDocument.AddDocument(nonHpfDoc1);

            nonHpfDoc1.Department = "Radiology";
            patientDetails.NonHpfDocument.AddDocument(nonHpfDoc1);

            RequestNonHpfDocumentDetails nonHpfDoc2 = new RequestNonHpfDocumentDetails();
            nonHpfDoc2.Comment = "Non Hpf document2";
            nonHpfDoc2.Id = 3;
            nonHpfDoc2.DateOfService = DateTime.Now.Date;
            nonHpfDoc2.DocType = "Discharge Summary";
            nonHpfDoc2.Facility = "A";
            nonHpfDoc2.Encounter = "1000";
            nonHpfDoc2.Department = "Radiology";
            nonHpfDoc2.Subtitle = "Discharge Summary";
            nonHpfDoc2.PageCount = 4;
            nonHpfDoc2.SelectedForRelease = true;

            patientDetails.NonHpfDocument.AddDocument(nonHpfDoc2);
            
            newRequest.Patients.Add(patientDetails.Key, patientDetails);

            newRequest = RequestController.Instance.UpdateRequest(newRequest);

            return newRequest;
        }

        private RequestPatientDetails RetrievePatientDetails()
        {
            PatientDetails hpfPatient = new PatientDetails();
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();

            patientSearchCriteria.FirstName = TestBase.PatientFirstName;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = TestBase.PatientFacility;
            patientSearchCriteria.MRN = string.Empty;
            patientSearchCriteria.SSN = string.Empty;
            patientSearchCriteria.Encounter = string.Empty;
            patientSearchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            FindPatientResult result = PatientController.Instance.FindPatient(patientSearchCriteria);
            foreach (PatientDetails patientDetails in result.PatientSearchResult)
            {
                if (patientDetails.IsHpf)
                {
                    hpfPatient = patientDetails;
                    break;
                }
            }
            
            RequestPatientDetails requestPatientDetails = GetDocumentForRelease(hpfPatient);
            return requestPatientDetails;
        }

        private RequestPatientDetails GetDocumentForRelease(PatientDetails patientDetails)
        {
            patientDetails = PatientController.Instance.RetrieveHpfDocuments(patientDetails);
            RequestPageDetails requestPageDetails = null;
            RequestVersionDetails requestVersionDetails = null;
            RequestDocumentDetails requestDocDetails = null;
            RequestEncounterDetails requestEncounterDetails = null;
            RequestPatientDetails requestPatientDetails = new RequestPatientDetails(patientDetails);
            bool hasMorePages = false;
            bool check = false;

            //Added for getting custom fields of an encounter.
            PatientController.Instance.GetCustomFields(patientDetails.Encounters);

            foreach (EncounterDetails encounterDetails in patientDetails.Encounters)
            {
                requestEncounterDetails = new RequestEncounterDetails(encounterDetails);
                foreach (DocumentDetails docDetails in encounterDetails.Documents.Values)
                {
                    requestDocDetails = new RequestDocumentDetails(docDetails);
                    foreach (VersionDetails versionDetails in docDetails.Versions.Values)
                    {
                        requestVersionDetails = new RequestVersionDetails(versionDetails);
                        foreach (PageDetails pageDetails in versionDetails.Pages.Values)
                        {
                            requestPageDetails = new RequestPageDetails(pageDetails);
                            requestPageDetails.SelectedForRelease = true;
                            if (!hasMorePages)
                            {
                                AddPages(requestVersionDetails, new RequestPageDetails(pageDetails), 1);
                                AddPages(requestVersionDetails, new RequestPageDetails(pageDetails), 2);
                                AddPages(requestVersionDetails, new RequestPageDetails(pageDetails), 3);
                                AddPages(requestVersionDetails, new RequestPageDetails(pageDetails), 4);
                                AddPages(requestVersionDetails, new RequestPageDetails(pageDetails), 7);
                                AddPages(requestVersionDetails, new RequestPageDetails(pageDetails), 8);
                                AddPages(requestVersionDetails, new RequestPageDetails(pageDetails), 9);
                                requestVersionDetails.GetChildren[0].SelectedForRelease = false;
                                hasMorePages = true;
                            }

                            requestVersionDetails.AddChild(requestPageDetails);
                            break;
                        }
                        
                        requestDocDetails.AddChild(requestVersionDetails);
                        requestPatientDetails.GlobalDocument.AddChild(requestDocDetails);
                        break;
                    }
                    requestPatientDetails.GlobalDocument.AddChild(requestDocDetails);
                    requestEncounterDetails.AddChild(requestDocDetails);
                    break;
                }
                requestPatientDetails.AddChild(requestEncounterDetails);
                break;
            }
            bool? checkedState = requestPatientDetails.CheckedState;
            
            return requestPatientDetails;
        }

        private void AddPages(RequestVersionDetails version, RequestPageDetails page, int pageNumber)
        {
            page.PageNumber = pageNumber;
            page.SelectedForRelease = true;
            version.AddChild(page);
        }

        public const string UploadFileName = @"\TestLetterTemplate.pdf";
        public const string DirectoryPath = @"FileUpload_DownloadTest";

        string path = Path.GetTempPath() + DirectoryPath;     

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        /// <summary>
        /// Get the file from resource and save it in the temporary location.
        /// </summary>
        private void CreateTempFile()
        {
            Assembly assembly = Assembly.GetExecutingAssembly();
            Stream stream = assembly.GetManifestResourceStream(assembly.GetName().Name + ".Resources.Test.pdf");

            Stream sw = new FileStream(path + UploadFileName, FileMode.CreateNew, FileAccess.ReadWrite);

            byte[] bytes = new byte[stream.Length];
            int numBytesToRead = (int)stream.Length;
            int numBytesRead;
            while ((numBytesRead = stream.Read(bytes, 0, numBytesToRead)) > 0)
            {
                sw.Write(bytes, 0, numBytesRead);
            }
            stream.Close();
            sw.Close();
        }


        public LetterTemplateDetails RetrieveDocumentID(LetterType letterType)
        {
            foreach (LetterTemplateDetails details in letterTemplates)
            {
                if (details.LetterType == letterType)
                {
                    return details;
                }
            }

            return new LetterTemplateDetails();
        }


        /// <summary>
        /// Dummy method to handle the progress bar event.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_LetterType(object sender, EventArgs e)
        {
        }

        #endregion

        #endregion

        #region Properties

        public ReleaseDetails ReleaseInfo
        {
            get
            {
                return releaseDetails;
            }
        }

        #endregion

        private string GetConfigProperty(string property)
        {
            return ConfigurationManager.AppSettings[property];
        }

        private void SetConfigProperty(string property, string value)
        {
            ConfigurationManager.AppSettings.Set(property, value);
        }

        public InvoiceAndDocumentDetails InvoiceAndDocumentDetails
        {
            get { return invoiceAndDocumentDetails; }
             
        }

        public RequestDetails RequestDetails
        {
            get { return requestDetails; }
        }

    }
}
