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
using System.IO;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using System.Collections.ObjectModel;
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test Class for LetterTemplateDetails DTO
    /// </summary>
    [TestFixture]
    public class TestLetterTemplateDetails : TestBase
    {
        #region Fields

        private LetterTemplateDetails lettertemplate;

        private string currentPath;
        private string filePath;

        #endregion

        #region Methods

        [SetUp]
        public void Initialize()
        {
            lettertemplate = new LetterTemplateDetails();
        }

        [TestFixtureSetUp]
        public void Init()
        {
            currentPath = Path.GetTempPath() + @"\FileUpload_DownloadTest";
            if (!Directory.Exists(currentPath))
            {
                Directory.CreateDirectory(currentPath);
            }
            filePath = currentPath + @"\Test_Template.rtf";
            if (File.Exists(filePath))
            {
                File.Delete(filePath);
            }

            FileStream fs = new FileStream(filePath, FileMode.CreateNew, FileAccess.Write);
            StreamWriter sw = new StreamWriter(fs);
            sw.WriteLine("This is the test File, to Excecute the Test Case");
            sw.Close();
            fs.Close();
        }

        [TestFixtureTearDown]
        public void InitDispose()
        {
            base.LogOff();
            if (File.Exists(filePath))
            {
                File.SetAttributes(filePath, FileAttributes.Normal);
                File.Delete(filePath);
            }
            if (Directory.Exists(currentPath))
            {
                Directory.Delete(currentPath, true);
            }
        }

        [TearDown]
        public void Dispose()
        {
            lettertemplate = null;
        }

        #region TestMethods

        /// <summary>
        /// Test case for Letter Template id.
        /// </summary>
        [Test]
        public void TestLetterTemplateId()
        {
            long inputLetterTemplateId = 1;
            lettertemplate.Id = inputLetterTemplateId;
            long outputLetterTemplateId = lettertemplate.Id;
            Assert.AreEqual(inputLetterTemplateId, outputLetterTemplateId);
        }

        /// <summary>
        /// Test case for Letter Template, Letter Type.
        /// </summary>
        [Test]
        public void TestLetterTemplateLetterType01()
        {
            LetterType inputType = LetterType.Invoice;
            lettertemplate.LetterType = inputType;            
            Assert.AreEqual(inputType, lettertemplate.LetterType);
            Assert.AreEqual(EnumUtilities.GetDescription(inputType), lettertemplate.LetterTypeName);
        }

        /// <summary>
        /// Test case for Letter Template, Letter Type.
        /// </summary>
        [Test]
        public void TestLetterTemplateLetterType02()
        {
            LetterType inputType = LetterType.CoverLetter;
            lettertemplate.LetterType = inputType;
            Assert.AreEqual(inputType, lettertemplate.LetterType);
            Assert.AreEqual(EnumUtilities.GetDescription(inputType), lettertemplate.LetterTypeName);
        }

        /// <summary>
        /// Test case for Letter Template, Letter Type.
        /// </summary>
        [Test]
        public void TestLetterTemplateLetterType03()
        {
            LetterType inputType = LetterType.None;
            lettertemplate.LetterType = inputType;
            Assert.AreEqual(inputType, lettertemplate.LetterType);            
        }

        /// <summary>
        /// Test case for Letter Template, Letter Type.
        /// </summary>
        [Test]
        public void TestLetterTemplateLetterType04()
        {
            LetterType inputType = LetterType.Other;
            lettertemplate.LetterType = inputType;
            Assert.AreEqual(inputType, lettertemplate.LetterType);
            Assert.AreEqual(EnumUtilities.GetDescription(inputType), lettertemplate.LetterTypeName);
        }

        /// <summary>
        /// Test case for Letter Template, Letter Type.
        /// </summary>
        [Test]
        public void TestLetterTemplateLetterType05()
        {
            LetterType inputType = LetterType.PreBill;
            lettertemplate.LetterType = inputType;
            Assert.AreEqual(inputType, lettertemplate.LetterType);
            Assert.AreEqual(EnumUtilities.GetDescription(inputType), lettertemplate.LetterTypeName);
        }

        /// <summary>
        /// Test case for Letter Template name
        /// </summary>
        [Test]
        public void TestLetterTemplateName()
        {
            string inputLetterTemplateName = "Cover Letter";
            lettertemplate.Name = inputLetterTemplateName;
            string outputLetterTemplateName = lettertemplate.Name;
            Assert.AreEqual(inputLetterTemplateName, outputLetterTemplateName);
        }

        /// <summary>
        /// Test case for Letter Template description.
        /// </summary>
        [Test]
        public void TestLetterTemplateDescription()
        {
            string inputLetterTemplateDescription = "Cover and Certification Letter";
            lettertemplate.Description = inputLetterTemplateDescription;
            string outputLetterTemplateDescription = lettertemplate.Description;
            Assert.AreEqual(inputLetterTemplateDescription, outputLetterTemplateDescription);
        }

        /// <summary>
        /// Test case for Letter Template FileName.
        /// </summary>
        [Test]
        public void TestLetterTemplateFileName()
        {
            string inputLetterTemplateFileName = "Test.rtf";
            lettertemplate.FileName = inputLetterTemplateFileName;
            string outputLetterTemplateFileName = lettertemplate.FileName;
            Assert.AreEqual(inputLetterTemplateFileName, outputLetterTemplateFileName);
        }

        /// <summary>
        /// Test case for Letter Template FileName.
        /// </summary>
        [Test]
        public void TestLetterTemplateFilePath()
        {
            string inputLetterTemplateFilePath = @"c:\Test.rtf";
            lettertemplate.FilePath = inputLetterTemplateFilePath;
            string outputLetterTemplateFilePath = lettertemplate.FilePath;
            Assert.AreEqual(inputLetterTemplateFilePath, outputLetterTemplateFilePath);
        }

        /// <summary>
        /// Test case for Letter Template default status
        /// </summary>
        [Test]
        public void TestLetterTemplateDefault()
        {
            bool inputIsDefault = true;
            lettertemplate.IsDefault = inputIsDefault;
            bool outputIsDefault = lettertemplate.IsDefault;
            Assert.AreEqual(inputIsDefault, outputIsDefault);
        }

        /// <summary>
        /// Test case for Letter Template Image.
        /// </summary>
        [Test]
        public void TestLetterTemplateImage()
        {
            lettertemplate.Image = null;
            Assert.IsNull(lettertemplate.Image);
        }

        /// <summary>
        /// Test case for LetterTemplate recordversion.
        /// </summary>
        [Test]
        public void TestLetterTemplateRecordVersion()
        {
            int recordVersionId = 0;
            lettertemplate.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, lettertemplate.RecordVersionId);
        }

        /// <summary>
        /// Test case for Letter Template DoUpload
        /// </summary>
        [Test]
        public void TestLetterTemplateDoUpload()
        {
            bool inputDoUpload = true;
            lettertemplate.DoUpload = inputDoUpload;
            bool outputDoUpload = lettertemplate.DoUpload;
            Assert.AreEqual(inputDoUpload, outputDoUpload);
        }

        /// <summary>
        /// Test case for LetterTemplate DocumentId.
        /// </summary>
        [Test]
        public void TestLetterTemplateDocumentId()
        {
            int inputdocumentID = 5;
            lettertemplate.DocumentId = inputdocumentID;
            Assert.AreEqual(inputdocumentID, lettertemplate.DocumentId);
        }

        /// <summary>
        /// Test case for Letter Template UploadSuccess
        /// </summary>
        [Test]
        public void TestLetterTemplateIsUpload()
        {
            bool inputIsUpload = true;
            lettertemplate.IsUploadSuccess = inputIsUpload;
            bool outputIsUpload = lettertemplate.IsUploadSuccess;
            Assert.AreEqual(inputIsUpload, outputIsUpload);
        }

        /// <summary>
        /// Test Case for Letter template sorted
        /// </summary>
        [Test]
        public void TestLetterTemplateSorter()
        {
            Collection<LetterTemplateDetails> letterTemplates = new Collection<LetterTemplateDetails>();
            LetterTemplateDetails letterTemplateDetails = new LetterTemplateDetails();
            letterTemplateDetails.Id = 1;
            letterTemplateDetails.Name = "Test1";
            letterTemplates.Add(letterTemplateDetails);
            letterTemplateDetails = new LetterTemplateDetails();
            letterTemplateDetails.Id = 2;
            letterTemplateDetails.Name = "Test2";
            letterTemplates.Add(letterTemplateDetails);
            List<LetterTemplateDetails> list = new List<LetterTemplateDetails>(letterTemplates);
            list.Sort(LetterTemplateDetails.Sorter);
            Assert.IsNotNull(LetterTemplateDetails.Sorter);            
        }
        
        /// <summary>
        /// Test case for Letter Template Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(lettertemplate.Equals(lettertemplate));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), lettertemplate.GetHashCode());
        }

        /// <summary>
        /// Test for Normalize.
        /// </summary>
        [Test]
        public void TestNormalizeWithFile()
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.Name        = "Test Template";
            templateDetails.Description = "Test Template Description";
            templateDetails.FilePath = filePath;
            templateDetails.Normalize();
        }

        /// <summary>
        /// Test for Normalize with unknown file name.
        /// </summary>
        [Test]
        public void TestNormalizeWithoutFile()
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.Name        = "Test Template";
            templateDetails.Description = "Test Template Description";            
            templateDetails.FilePath = null;
            templateDetails.Normalize();
        }

        /// <summary>
        /// Test case for HasNotes
        /// </summary>
        [Test]
        public void TestHasNotes()
        {
            bool hasNotes = true;
            lettertemplate.HasNotes = hasNotes;
            Assert.AreEqual(hasNotes, lettertemplate.HasNotes);
        }

        /// <summary>
        /// Test case for retrieving compositekey
        /// </summary>
        [Test]
        public void TestRetrieveCompositeKey()
        {
            Assert.IsTrue(lettertemplate.CompositeKey.Length > 0);
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        #endregion

        #endregion
    }
}
