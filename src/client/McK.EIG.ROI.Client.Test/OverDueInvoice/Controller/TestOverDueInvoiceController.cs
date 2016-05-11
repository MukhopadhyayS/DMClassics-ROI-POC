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
using System.Linq;
using System.Text;


//NUnit
using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Test.Request.Controller;
using McK.EIG.ROI.Client.OverDueInvoice.Model;
using McK.EIG.ROI.Client.OverDueInvoice.Controller;

namespace McK.EIG.ROI.Client.Test.OverDueInvoice.Controller
{
    /// <summary>
    /// Test case for OverDueInvoiceController.
    /// </summary>
    [TestFixture]
    [Category("TestOverDueInvoiceController")]
    public class TestOverDueInvoiceController : TestBase
    {
        private ROIAdminController roiAdminController;
        private OverDueInvoiceController overDueInvoiceController;
        private Collection<LetterTemplateDetails> letterTemplates;
        private OverDueInvoiceSearchResult overDueInvoiceSearchResult;
        private IList<string> facilityCodes;
        private IList<long> requestorType;

        private RequestDetails requestDetails;
        [SetUp]
        public void Init()
        {
            overDueInvoiceController = OverDueInvoiceController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            overDueInvoiceController = null;
        }      
 
        /// <summary>
        /// Test Save Request Core Charges
        /// </summary>
        [Test]
        public void Test01FindOverDueInvoices()
        {
            OverDueInvoiceSearchCriteria searchCriteria = new OverDueInvoiceSearchCriteria();
            List<FacilityDetails> facilityDetailsList = new List<FacilityDetails>(UserData.Instance.SortedFacilities);
            TestBillingController testBillingController = new TestBillingController();
            searchCriteria.FacilityCode = new List<string>();
            foreach (FacilityDetails facilityCode in facilityDetailsList)
            {
                searchCriteria.FacilityCode.Add(facilityCode.Code);
            }
            Collection<RequestorTypeDetails>  requestorTypes = ROIAdminController.Instance.RetrieveAllRequestorTypes(false);
            searchCriteria.RequestorType = new List<long>();
            searchCriteria.RequestorTypeName = new List<string>();
            foreach (RequestorTypeDetails requestor in requestorTypes)
            {
                searchCriteria.RequestorType.Add(requestor.Id);
                searchCriteria.RequestorTypeName.Add(requestor.Name);
            }
            facilityCodes = searchCriteria.FacilityCode;
            requestorType = searchCriteria.RequestorType;
            searchCriteria.From = 0;
            searchCriteria.To = 120;
            overDueInvoiceSearchResult = overDueInvoiceController.FindOverDueInvoices(searchCriteria);
            Assert.IsTrue(overDueInvoiceSearchResult.SearchResult.Count > 0);
        }

        /// <summary>
        /// Test Preview OverDue Invoice
        /// </summary>
        [Test]
        public void Test02PreviewOverDueInvoices()
        {
            roiAdminController = ROIAdminController.Instance;
            letterTemplates = roiAdminController.RetrieveAllLetterTemplates();
            PreviewOverDueInvoices previewOverDueInvoices = new PreviewOverDueInvoices();
            previewOverDueInvoices.RequestorStatementDetail = new McK.EIG.ROI.Client.Requestors.Model.RequestorStatementDetail();
            previewOverDueInvoices.RequestorStatementDetail.DateRange = DateRange.ALL;
            LetterTemplateDetails preBillLetterTemplate = RetrieveDocumentID(LetterType.Invoice);
            previewOverDueInvoices.RequestorLetterTemplateId = preBillLetterTemplate.DocumentId;

            Hashtable requestorInvoices = new Hashtable();
            RequestorInvoicesDetails requestorInvoicesDetails = new RequestorInvoicesDetails();
            RequestorInvoicesDetails requestorInvoiceDet = new RequestorInvoicesDetails();
            List<RequestorInvoicesDetails> requestorInvoiceList = new List<RequestorInvoicesDetails>();

            ArrayList requestorIds = new ArrayList();

            requestorInvoicesDetails.RequestorId = overDueInvoiceSearchResult.SearchResult[0].RequestorId;
            requestorInvoicesDetails.RequestId = overDueInvoiceSearchResult.SearchResult[0].RequestId;
            requestorInvoiceDet.RequestId = overDueInvoiceSearchResult.SearchResult[0].RequestId;
            requestorInvoicesDetails.RequestorName = overDueInvoiceSearchResult.SearchResult[0].RequestorName;
            requestorInvoiceDet.RequestorName = overDueInvoiceSearchResult.SearchResult[0].RequestorName;
            requestorInvoicesDetails.RequestorType = overDueInvoiceSearchResult.SearchResult[0].RequestorType;
            requestorInvoicesDetails.InvoiceIds.Add(overDueInvoiceSearchResult.SearchResult[0].Id);
            requestorInvoiceDet.InvoiceIds.Add(overDueInvoiceSearchResult.SearchResult[0].Id);
            requestorInvoiceList.Add(requestorInvoiceDet);
            requestorInvoicesDetails.GreatestInvoiceAge = 0;
            requestorInvoices[requestorInvoicesDetails.RequestorId] = requestorInvoicesDetails;
            requestorIds.Add(requestorInvoicesDetails.RequestorId);
            foreach (long id in requestorIds)
            {
                previewOverDueInvoices.RequestorInvoicesList.Add((RequestorInvoicesDetails)requestorInvoices[id]);
            }
            previewOverDueInvoices.IsPreview = true;
            
            previewOverDueInvoices.PropertiesMap.Add(ROIConstants.QueuePassword, 1234);
            

            previewOverDueInvoices.PropertiesMap.Add(ROIConstants.OutputMethod, "PDF");

            previewOverDueInvoices.PropertiesMap.Add(PreBillInvoiceDetails.RequestorLetterTemplateIdKey, preBillLetterTemplate.Id);
            previewOverDueInvoices.PropertiesMap.Add(PreBillInvoiceDetails.RequestorLetterTemplateNameKey, preBillLetterTemplate.Name);

            DocumentInfoList documentInfoList = overDueInvoiceController.PreviewOverDueInvoices(previewOverDueInvoices);
            Assert.IsTrue(documentInfoList.DocumentInfoCollection.Count > 0);
            Collection<CommentDetails> commentDetails = new Collection<CommentDetails>();
            CommentDetails commentDetail = new CommentDetails();

            commentDetail.RequestId = requestorInvoicesDetails.RequestId;
            commentDetail.EventType = EventType.OverDueInvoiceSent;
            //end
            string invoiceList = string.Empty;
            foreach (long invoiceId in requestorInvoicesDetails.InvoiceIds)
            {
                invoiceList += invoiceId + ",";
            }
            invoiceList = invoiceList.TrimEnd(',');
            StringBuilder auditComment = new StringBuilder();
            auditComment.Append(invoiceList + " of request " + requestorInvoicesDetails.RequestId + " was regenerated. ");
            string eventMessage = "Invoices were regenerated for: " + invoiceList + " of request " + requestorInvoicesDetails.RequestId + " was regenerated.";
            commentDetail.EventRemarks = eventMessage;
            commentDetails.Add(commentDetail);
            RequestController.Instance.CreateCommentsList(commentDetails);
            Assert.IsNotNull(commentDetails);
        }

        /// <summary>
        /// Test Validate Primary Fields
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test03ValidatePrimaryFields()
        {
            OverDueInvoiceSearchCriteria searchCriteria = new OverDueInvoiceSearchCriteria();
            searchCriteria.RequestorName = "test;";
            OverDueInvoiceValidator overDueInvoiceValidator = new OverDueInvoiceValidator();
            Assert.IsFalse(overDueInvoiceValidator.ValidateOverDueInvoiceSearch(searchCriteria));
        }

        /// <summary>
        /// Test Validate Primary Fields
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04ValidatePrimaryFields()
        {
            OverDueInvoiceSearchCriteria searchCriteria = new OverDueInvoiceSearchCriteria();
            searchCriteria.RequestorName = "test;";
            searchCriteria.FacilityCode = facilityCodes;
            searchCriteria.RequestorType = requestorType;
            overDueInvoiceSearchResult = overDueInvoiceController.FindOverDueInvoices(searchCriteria);
            Assert.IsNotNull(searchCriteria);
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
    }
}
