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
using System.Collections.ObjectModel;
using System.Configuration;
using System.Collections.Generic;
using System.Globalization;

//NUnit
using NUnit.Framework;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test cases for disclosure document type.
    /// </summary>
    [TestFixture]
    public class TestDisclosureDocTypesController : TestBase
    {
        #region Fields

        private ROIAdminController roiAdminController;
        private Collection<DocumentTypesDetails> documentTypes;
        
        private long codeSetId;
        private string codeSetName;
        private string fromName;
        private string toName;

        #endregion

        #region Construcor

        public TestDisclosureDocTypesController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            roiAdminController = ROIAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            roiAdminController = null;
        }                

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for retrieve all code sets.
        /// </summary>
        [Test]
        public void Test01RetrieveAllCodeSets()
        {
            Collection<CodeSetDetails> codeSetDetails = roiAdminController.RetrieveAllCodeSets();
            if (codeSetDetails.Count > 0)
            {
                CodeSetDetails codeset = new CodeSetDetails();
                Assert.IsInstanceOfType(typeof(long),codeset.Id);
                codeSetId = codeSetDetails[0].Id;
                codeSetName = codeSetDetails[0].Description;
                Assert.IsInstanceOfType(typeof(string), codeset.Description);
            }
        }

        /// <summary>
        /// Test case for retreiving document types for the particular codeset.
        /// </summary>
        [Test]
        public void Test02RetrieveDocumentTypesWithValidInput()
        {
            Collection <DocumentTypesDetails> docTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            if (docTypes.Count > 0)
            {
                DocumentTypesDetails docTypeDetails = docTypes[0];
                Assert.IsInstanceOfType(typeof(long), docTypeDetails.Id);
                Assert.IsInstanceOfType(typeof(string), docTypeDetails.Name);
                Assert.IsInstanceOfType(typeof(DocumentDesignationType), docTypeDetails.Type);
                Assert.IsInstanceOfType(typeof(long), docTypeDetails.CodeSetId);
            }
        }

        /// <summary>
        /// Test case for designated doc type with valid input.
        /// </summary>
        [Test]
        public void Test03DesignatedDocTypeWithValidInput()
        {
            documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            Assert.IsTrue(documentTypes.Count > 0);          
        }

        /// <summary>
        /// Test case for designated doc type with invalid input.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04DesignatedDocTypeWithInvalidInput()
        {
            long codeSetId = 0;
            documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            Assert.IsTrue(documentTypes.Count == 0);
        }

        /// <summary>
        /// Test case for Retrieve document type with invalid input.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05DesignateDocumentTypesWithInvalidInput()
        {
            long codeSetId = 0;
            int counter = 0;
            Collection<DocumentTypesDetails> documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            Collection<DocTypeAuditDetails> docTypeAuditDetails =  new Collection<DocTypeAuditDetails>();
            DocTypeAuditDetails doctypeAudit;
 
            foreach (DocumentTypesDetails docType in documentTypes)
            {
                fromName = docType.MUDocumentType;
                toName = docType.MUDocumentType;
                if (counter == 0)
                {
                    docType.IsAuthorization = true;

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.MUDocumentType;
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType  = "MU";
                    
                    docTypeAuditDetails.Add(doctypeAudit);
                }
                else if (counter == 1)
                {
                    docType.IsDisclosure = true;

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.IsDisclosure.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType  = "Disclosure Type";                    
                    docTypeAuditDetails.Add(doctypeAudit);

                }                
                else
                {
                    break;
                }
                counter++;
            }
            roiAdminController.DesignateDocumentTypes(-91, documentTypes, docTypeAuditDetails);   
        }

        /// <summary>
        /// Test case for Retrieve document type with valid input.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06WithoutAuthRequest()
        {            
            Collection<CodeSetDetails> codeSetDetails = roiAdminController.RetrieveAllCodeSets();
            Collection<DocumentTypesDetails> documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetDetails[0].Id);
            Collection<DocTypeAuditDetails> docTypeAuditDetails = new Collection<DocTypeAuditDetails>();
            DocTypeAuditDetails doctypeAudit;
            
            //Remove if already auth request is selected for the selected code set.
            List<DocumentTypesDetails> list1 = new List<DocumentTypesDetails>(documentTypes);
            list1.ForEach(delegate(DocumentTypesDetails doc) { doc.IsAuthorization = false; });

            foreach (DocumentTypesDetails docType in documentTypes)
            {                
                docType.IsDisclosure = true;

                doctypeAudit = new DocTypeAuditDetails();
                doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                doctypeAudit.ToValue = docType.IsDisclosure.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                doctypeAudit.DocName = docType.Name;
                doctypeAudit.CodeSetName = "AD";
                doctypeAudit.DocType = "Disclosure Type";
                docTypeAuditDetails.Add(doctypeAudit);

                break;
            }
            roiAdminController.DesignateDocumentTypes(codeSetId, documentTypes, docTypeAuditDetails);
        }

        /// <summary>
        /// Test case for Retrieve document type with valid input.
        /// </summary>
        [Test]        
        public void Test07DesignateDocumentTypesWithValidInput()
        {
            int counter = 0;
            Collection<CodeSetDetails> codeSetDetails = roiAdminController.RetrieveAllCodeSets();
            codeSetId = codeSetDetails[0].Id;
            codeSetName = codeSetDetails[0].Description;
            Collection<DocumentTypesDetails> documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            Collection<DocTypeAuditDetails> docTypeAuditDetails = new Collection<DocTypeAuditDetails>();
            DocTypeAuditDetails doctypeAudit;
            
            foreach (DocumentTypesDetails docType in documentTypes)
            {
                fromName = docType.MUDocumentType;
                toName = docType.MUDocumentType;
                if (counter == 0)
                {
                    docType.IsAuthorization = true;

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.MUDocumentType;
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType = "MU";

                    docTypeAuditDetails.Add(doctypeAudit);

                }
                else if (counter == 1)
                {
                    docType.IsDisclosure = true;

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.IsDisclosure.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType = "Disclosure Type";
                    docTypeAuditDetails.Add(doctypeAudit);
                }
                else
                {
                    break;
                }
                counter++;
            }
            roiAdminController.DesignateDocumentTypes(codeSetId, documentTypes, docTypeAuditDetails);
        }

        /// <summary>
        /// Test case for Retrieve document type with valid input and save the document type.
        /// </summary>
        [Test]
        public void Test08DocumentTypesWithValidInput()
        {
            int counter = 0;
            Collection<CodeSetDetails> codeSetDetails = roiAdminController.RetrieveAllCodeSets();
            codeSetId = codeSetDetails[0].Id;
            codeSetName = codeSetDetails[0].Description;
            Collection<DocumentTypesDetails> documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            Collection<DocTypeAuditDetails> docTypeAuditDetails = new Collection<DocTypeAuditDetails>();
            DocTypeAuditDetails doctypeAudit;

            foreach (DocumentTypesDetails docType in documentTypes)
            {
                fromName = docType.MUDocumentType;
                toName = docType.MUDocumentType;
                if (counter == 0)
                {
                    docType.IsDisclosure = true;
                    docType.IsAuthorization = true;

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.MUDocumentType;
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType = "MU";

                    docTypeAuditDetails.Add(doctypeAudit);
                    
                }
                else if (counter == 1)
                {
                    docType.IsDisclosure = true;

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.IsDisclosure.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType = "Disclosure Type";
                    docTypeAuditDetails.Add(doctypeAudit);

                }               
                else
                {
                    break;
                }
                counter++;
            }
            roiAdminController.DesignateDocumentTypes(codeSetId, documentTypes, docTypeAuditDetails);
        }

        /// <summary>
        /// Test case for Retrieve document type with valid input and save the document type.
        /// </summary>
        [Test]
        public void Test09DesignateDocumentTypesWithValidInput()
        {
            int counter = 0;
            Collection<CodeSetDetails> codeSetDetails = roiAdminController.RetrieveAllCodeSets();
            codeSetId = codeSetDetails[0].Id;
            codeSetName = codeSetDetails[0].Description;
            Collection<DocumentTypesDetails> documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            Collection<DocTypeAuditDetails> docTypeAuditDetails = new Collection<DocTypeAuditDetails>();
            DocTypeAuditDetails doctypeAudit;
           
            foreach (DocumentTypesDetails docType in documentTypes)
            {
                fromName = docType.MUDocumentType;
                toName = docType.MUDocumentType;
                if (counter == 0)
                {
                    Assert.IsTrue(docType.IsDisclosure);
                    Assert.IsTrue(docType.IsAuthorization);

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.MUDocumentType;
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType = "MU";

                    docTypeAuditDetails.Add(doctypeAudit);
                   
                }
                else if (counter == 1)
                {                    
                    Assert.IsTrue(docType.IsDisclosure);

                    doctypeAudit = new DocTypeAuditDetails();
                    doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                    doctypeAudit.ToValue = docType.IsDisclosure.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    doctypeAudit.DocName = docType.Name;
                    doctypeAudit.CodeSetName = "AD";
                    doctypeAudit.DocType = "Disclosure Type";
                    docTypeAuditDetails.Add(doctypeAudit);

                }               
                else
                {
                    break;
                }
                counter++;
            }
            roiAdminController.DesignateDocumentTypes(codeSetId, documentTypes, docTypeAuditDetails);
        }

        /// <summary>
        /// Test case for Retrieve document type with valid input and save the document type.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10DocumentTypesWithInValidInput()
        {   
            Collection<CodeSetDetails> codeSetDetails = roiAdminController.RetrieveAllCodeSets();
            codeSetId = codeSetDetails[0].Id;
            codeSetName = codeSetDetails[0].Description;
            Collection<DocumentTypesDetails> documentTypes = roiAdminController.RetrieveDocumentTypes(codeSetId);
            Collection<DocTypeAuditDetails> docTypeAuditDetails = new Collection<DocTypeAuditDetails>();
            DocTypeAuditDetails doctypeAudit;
         
            foreach (DocumentTypesDetails docType in documentTypes)
            {
                fromName = docType.MUDocumentType;
                toName = docType.MUDocumentType;
                docType.IsAuthorization = true;

                doctypeAudit = new DocTypeAuditDetails();
                doctypeAudit.FromValue = docType.CodeSetId + ROIConstants.Delimiter + docType.Id;
                doctypeAudit.ToValue = docType.MUDocumentType;
                doctypeAudit.DocName = docType.Name;
                doctypeAudit.CodeSetName = "AD";
                doctypeAudit.DocType = "MU";

                docTypeAuditDetails.Add(doctypeAudit);
                
            }
            roiAdminController.DesignateDocumentTypes(codeSetId, documentTypes, docTypeAuditDetails);
        }

        #endregion
    } 
}
