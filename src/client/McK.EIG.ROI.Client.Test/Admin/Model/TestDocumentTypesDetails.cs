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

using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for DocumentTypesDetails.
    /// </summary>
    [TestFixture]
    public class TestDocumentTypesDetails
    {
        private DocumentTypesDetails documentTypesDetails;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>

        [SetUp]
        public void Initialize()
        {
            documentTypesDetails = new DocumentTypesDetails();
        }

        /// <summary>
        /// Dispose DocumentTypes Details.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            documentTypesDetails = null;
        }

        /// <summary>
        /// Test case for Disclosure Document Types Id.
        /// </summary>
        [Test]
        public void TestDisclosureDocumentTypesId()
        {
            long inputDocumentTypeId = 500;
            documentTypesDetails.Id = inputDocumentTypeId;
            long outputDocumentTypeId = documentTypesDetails.Id;
            Assert.AreEqual(inputDocumentTypeId, outputDocumentTypeId);
        }
        
        /// <summary>
        /// Test case for Disclosure Document Types Name.
        /// </summary>
        [Test]
        public void TestDisclosureDocumentTypesName()
        {
            string inputDocumentTypesName  = "Disclosure Document";
            documentTypesDetails.Name      = inputDocumentTypesName;
            string outputDocumentTypesName = documentTypesDetails.Name;
            Assert.AreEqual(outputDocumentTypesName, inputDocumentTypesName);
        }

        /// <summary>
        /// Test case for DocumentDesignationType.
        /// </summary>
        [Test]
        public void TestDocumentDesignationType()
        {
            DocumentDesignationType inputDesignationType = DocumentDesignationType.Disclosure;
            documentTypesDetails.Type = inputDesignationType;
            DocumentDesignationType outputDesignationType = documentTypesDetails.Type;
            Assert.AreEqual(outputDesignationType, inputDesignationType);
        }

        /// <summary>
        /// Test case for CodesetDetails details.
        /// </summary>
        [Test]
        public void TestCodeSetDetails()
        {
            CodeSetDetails inputCodeSetDetails = new CodeSetDetails();
            inputCodeSetDetails.Id = 100;
            inputCodeSetDetails.Description = "<DisclosureDocument>";
            CodeSetDetails outputCodeSetDetails = new CodeSetDetails();
            outputCodeSetDetails.Id = inputCodeSetDetails.Id;
            outputCodeSetDetails.Description = inputCodeSetDetails.Description;
            Assert.AreEqual(inputCodeSetDetails,outputCodeSetDetails);
        }

        /// <summary>
        /// Test case for CodesetID.
        /// </summary>
        [Test]
        public void TestCodeSetId()
        {
            long input = 555;
            documentTypesDetails.CodeSetId = input;
            Assert.AreEqual(input, documentTypesDetails.CodeSetId);            
        }

        /// <summary>
        /// Test case for IsDisclosure.
        /// </summary>
        [Test]
        public void TestIsDisclosure()
        {
            bool inputIsDisclosure = true;
            documentTypesDetails.IsDisclosure = inputIsDisclosure;
            bool outputIsDisclosure = documentTypesDetails.IsDisclosure;
            Assert.AreEqual(inputIsDisclosure, outputIsDisclosure);
        }

        /// <summary>
        /// Test case for IsAuthorization.
        /// </summary>
        [Test]
        public void TestIsAuthorization()
        {
            bool inputIsAuthorization = true;
            documentTypesDetails.IsAuthorization = inputIsAuthorization;
            bool outputIsAuthorization = documentTypesDetails.IsAuthorization;
            Assert.AreEqual(inputIsAuthorization, outputIsAuthorization);
        }

        /// <summary>
        /// Test case for IsMUDocumentType.
        /// </summary>
        [Test]
        public void TestIsMUDocumentType()
        {
            bool inputIsMUDocumentType = true;
            documentTypesDetails.IsMUDocumentType = inputIsMUDocumentType;
            bool outputIsMUDocumentType = documentTypesDetails.IsMUDocumentType;
            Assert.AreEqual(inputIsMUDocumentType, outputIsMUDocumentType);
        }

        /// <summary>
        /// Test case for MUDocumentType.
        /// </summary>
        [Test]
        public void TestMUDocumentType()
        {
            string inputMUDocumentType = "Disclosure Document";
            documentTypesDetails.MUDocumentType = inputMUDocumentType;
            string outputMUDocumentType = documentTypesDetails.MUDocumentType;
            Assert.AreEqual(outputMUDocumentType, inputMUDocumentType);
        }

        /// <summary>
        /// Test case for Clone Method.
        /// </summary>
        [Test]
        public void TestClone()
        {
            documentTypesDetails = documentTypesDetails.Clone();
            Assert.IsNotNull(documentTypesDetails);
        }

    }
}
