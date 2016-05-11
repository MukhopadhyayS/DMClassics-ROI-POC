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
using System.ComponentModel;
using System.Drawing;
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for AttachmentFileDetails
    /// </summary>
    [TestFixture]
    public class TestAttachmentFileDetails
    {
        private AttachmentFileDetails attachmentFileDetails;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            attachmentFileDetails = new AttachmentFileDetails();
        }

        /// <summary>
        /// Dispose AttachmentFileDetails.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            attachmentFileDetails = null;
        }

        /// <summary>
        /// Test method for FileUuid.
        /// </summary>
        [Test]
        public void TestFileUuid()
        {
            string inputUuid = "1234";
            attachmentFileDetails.Uuid = inputUuid;
            string outputUuid = attachmentFileDetails.Uuid;
            Assert.AreEqual(inputUuid, outputUuid);
        }
        /// <summary>
        /// Test method for PageCount
        /// </summary>
        [Test]
        public void TestPageCount()
        {
            int pageCount = 3;
            attachmentFileDetails.PageCount = pageCount;
            Assert.AreEqual(pageCount, attachmentFileDetails.PageCount);
        }

        /// <summary>
        /// Test method for FileExtension.
        /// </summary>
        [Test]
        public void TestFileExtension()
        {
            string inputExtension = "PDF";
            attachmentFileDetails.Extension = inputExtension;
            string outputExtension = attachmentFileDetails.Extension;
            Assert.AreEqual(inputExtension, outputExtension);
        }


        /// <summary>
        /// Test method for FileName.
        /// </summary>
        [Test]
        public void TestFileName()
        {
            string inputFileName = "test";
            attachmentFileDetails.FileName = inputFileName;
            string outputFileName = attachmentFileDetails.FileName;
            Assert.AreEqual(inputFileName, outputFileName);
        }


        /// <summary>
        /// Test method for Filetype.
        /// </summary>
        [Test]
        public void TestFileType()
        {
            string inputFileType = "PDF";
            attachmentFileDetails.FileType = inputFileType;
            string outputFileType = attachmentFileDetails.FileType;
            Assert.AreEqual(inputFileType, outputFileType);
        }

    }
}
