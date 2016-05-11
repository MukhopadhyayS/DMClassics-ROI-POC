using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using NUnit.Framework;

using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.OverDueInvoice.Model;
namespace McK.EIG.ROI.Client.Test.OverDueInvoice.Model
{
    /// <summary>
    /// 
    /// </summary>
    [TestFixture]
    public class TestPreviewOverDueInvoices
    {
        private PreviewOverDueInvoices previewOverDueInvoices;
        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            previewOverDueInvoices = new PreviewOverDueInvoices();
        }

        /// <summary>
        /// Dispose OverDue Invoice Search Criteria.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            previewOverDueInvoices = null;
        }

        /// <summary>
        /// Test method for Invoice Template ID.
        /// </summary>
        [Test]
        public void TestInvoiceTemplateId()
        {
            long inputInvoiceTemplateId = 2;
            previewOverDueInvoices.InvoiceTemplateId = inputInvoiceTemplateId;
            long outputInvoiceTemplateId = previewOverDueInvoices.InvoiceTemplateId;
            Assert.AreEqual(inputInvoiceTemplateId, outputInvoiceTemplateId);
        }

        /// <summary>
        /// Test method for Requestor Letter Template ID.
        /// </summary>
        [Test]
        public void TestRequestorLetterTemplateId()
        {
            long inputRequestorLetterTemplateId = 2;
            previewOverDueInvoices.RequestorLetterTemplateId = inputRequestorLetterTemplateId;
            long outputRequestorLetterTemplateId = previewOverDueInvoices.RequestorLetterTemplateId;
            Assert.AreEqual(inputRequestorLetterTemplateId, outputRequestorLetterTemplateId);
        }

        /// <summary>
        /// Test method for Requestor Invoice List.
        /// </summary>
        [Test]
        public void TestRequestorInvoiceList()
        {
            RequestorInvoicesDetails requestorInvoiceDetails = new RequestorInvoicesDetails();
            previewOverDueInvoices.RequestorInvoicesList.Add(requestorInvoiceDetails);
            Assert.IsNotNull(previewOverDueInvoices.RequestorInvoicesList);
        }

        /// <summary>
        /// Test method for Invoice Preview.
        /// </summary>
        [Test]
        public void TestInvoicePreview()
        {
            bool inputIsPreview = true;
            previewOverDueInvoices.IsPreview = inputIsPreview;
            bool outputIsPreview = previewOverDueInvoices.IsPreview;
            Assert.AreEqual(inputIsPreview, outputIsPreview);
        }

        /// <summary>
        /// Test method for Requestor Letter Notes.
        /// </summary>
        [Test]
        public void TestRequestorLetterNotes()
        {
            previewOverDueInvoices.RequestorLetterNotes.Add("Notes");
            Assert.IsNotNull(previewOverDueInvoices.RequestorLetterNotes);
        }

        /// <summary>
        /// Test method for Requestor Letter Notes.
        /// </summary>
        [Test]
        public void TestInvoiceNotes()
        {
            previewOverDueInvoices.InvoiceNotes.Add("Notes");
            Assert.IsNotNull(previewOverDueInvoices.InvoiceNotes);
        }

        /// <summary>
        /// Test method for TestRequestor Statement Details.
        /// </summary>
        [Test]
        public void TestRequestorStatementDetails()
        {
            RequestorStatementDetail inputrequestorStatementDetail = new RequestorStatementDetail();
            previewOverDueInvoices.RequestorStatementDetail = inputrequestorStatementDetail;
            RequestorStatementDetail outputrequestorStatementDetail = previewOverDueInvoices.RequestorStatementDetail;
            Assert.AreEqual(inputrequestorStatementDetail, outputrequestorStatementDetail);
        }

        /// <summary>
        /// Test method for TestRequestor Statement Details.
        /// </summary>
        [Test]
        public void TestIsOutputInvoice()
        {
            bool inputIsOutputInvoice = true;
            previewOverDueInvoices.IsOutputInvoice= inputIsOutputInvoice;
            bool outputIsOutputInvoice = previewOverDueInvoices.IsOutputInvoice;
            Assert.AreEqual(inputIsOutputInvoice, outputIsOutputInvoice);
        }

        /// <summary>
        /// Test method for Properties Map.
        /// </summary>
        [Test]
        public void TestPropertiesMap()
        {
            Hashtable inputPropertiesMap = new Hashtable();
            previewOverDueInvoices.PropertiesMap.Add(inputPropertiesMap, "1234");
            Hashtable outputPropertiesMap = previewOverDueInvoices.PropertiesMap;
            Assert.IsNotNull(outputPropertiesMap);
        }
    }
}
