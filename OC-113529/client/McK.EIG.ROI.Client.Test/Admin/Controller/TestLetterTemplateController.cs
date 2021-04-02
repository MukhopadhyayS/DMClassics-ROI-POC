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
using System.IO;
using System.Reflection;

//NUnit
using NUnit.Framework;

//McK

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.View.Other.LetterTemplate;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test Cases for LetterTemplate Controller.
    /// </summary>
    [TestFixture]
    public class TestLetterTemplateController : TestBase
    {

        #region Fields

        private const string DirectoryPath    = @"FileUpload_DownloadTest";
        private const string UploadFileName   = @"\TestLetterTemplate.rtf";
        private const string DownloadFileName = @"\TestDownloadLetterTemplate.rtf";
        private static EventHandler fileTransfer;
        private string path;

        private long id;
        private ROIAdminController roiAdminController;
        private LetterTemplateDetails letterTemplateDetails;
        private FileStream uploadFileStream;

        #endregion

        #region Constructor

        public TestLetterTemplateController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            path = Path.GetTempPath() + DirectoryPath;
            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);
            }

            if (File.Exists(path + UploadFileName))
            {
                File.SetAttributes(path + UploadFileName, FileAttributes.Normal);
                File.Delete(path + UploadFileName);
            }
            CreateTempFile();

            letterTemplateDetails = new LetterTemplateDetails();
            fileTransfer = new EventHandler(Process_LetterType);

            letterTemplateDetails.IsDefault = true;
            letterTemplateDetails.LetterType = LetterType.Invoice;
            letterTemplateDetails.Name = "Test Letter" + TestBiilingAdminController.GetUniqueId();
            letterTemplateDetails.FilePath = path + UploadFileName;
            letterTemplateDetails.Description = "Test Letter";
            letterTemplateDetails.DoUpload = false;
            letterTemplateDetails.DocumentId = 0;
        }

        #endregion

        #region Methods

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

        /// <summary>
        /// Get the file from resource and save it in the temporary location.
        /// </summary>
        private void CreateTempFile()
        {           
            FileStream fs = new FileStream(path + UploadFileName, FileMode.CreateNew, FileAccess.ReadWrite);
            StreamWriter sw = new StreamWriter(fs);
            sw.WriteLine("Test RTF file, created to execute the Test Case.");
            sw.Close();
            fs.Close();
        }

        [TestFixtureTearDown]
        public void LetterTemplateDispose()
        {
            base.LogOff();
            SetConfigProperty("FileOwnerType", "LetterTemplate");
            if (uploadFileStream != null)
            {
                uploadFileStream.Close();
            }
            string filePath = path + UploadFileName;
            if (File.Exists(filePath))
            {
                File.SetAttributes(filePath, FileAttributes.Normal);
                File.Delete(filePath);
            }
            if(Directory.Exists(path))
            {
                Directory.Delete(path,true);
            }
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for Creation of default LetterTemplate.
        /// </summary>
        [Test]
        public void Test01CreateLetterTemplate()
        {
            LetterTemplateDetails details = roiAdminController.CreateLetterTemplate(letterTemplateDetails, fileTransfer);
            id = details.Id;
            Assert.AreEqual(true, details.IsUploadSuccess);
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        ///  Test Create Letter Template With same Letter Type, different Name
        /// </summary>
        [Test]
        public void Test02CreateLetterTemplateWithExistingName()
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.IsDefault   = letterTemplateDetails.IsDefault;
            templateDetails.LetterType  = letterTemplateDetails.LetterType;
            templateDetails.Name = letterTemplateDetails.Name + TestBiilingAdminController.GetUniqueId();
            templateDetails.FilePath    = path + UploadFileName;            
            templateDetails.DoUpload = false;
            templateDetails.DocumentId = 0;
            templateDetails = roiAdminController.CreateLetterTemplate(templateDetails, fileTransfer);
            Assert.IsTrue(templateDetails.Id > 0);
        }       

        /// <summary>
        /// Get the Letter Template for the given Letter Template id.
        /// </summary>
        [Test]
        public void Test03GetLetterTemplate()
        {            
            letterTemplateDetails = roiAdminController.GetLetterTemplate(id);

            Assert.IsInstanceOfType(typeof(long), letterTemplateDetails.Id);
            Assert.IsInstanceOfType(typeof(LetterType), letterTemplateDetails.LetterType); 
            Assert.IsInstanceOfType(typeof(string), letterTemplateDetails.Name);
            Assert.IsInstanceOfType(typeof(string), letterTemplateDetails.Description);            
            Assert.IsInstanceOfType(typeof(bool), letterTemplateDetails.IsDefault);
            Assert.IsInstanceOfType(typeof(string), letterTemplateDetails.FileName);
        }

        /// <summary>
        /// Tese case for update Letter Template.
        /// </summary>
        [Test]
        public void Test04UpdateLetterTemplate()
        {
            letterTemplateDetails.IsDefault = false;
            letterTemplateDetails.Description = "Test Letter Template Modified.";
            letterTemplateDetails.DoUpload = false;
            LetterTemplateDetails updatedLetterTemplate = roiAdminController.UpdateLetterTemplate(letterTemplateDetails, fileTransfer);
            Assert.AreNotEqual(letterTemplateDetails.RecordVersionId, updatedLetterTemplate.RecordVersionId);
            letterTemplateDetails = updatedLetterTemplate;
        }

        /// <summary>
        /// Tese case for update Letter Template with updated file path.
        /// </summary>
        [Test]
        public void Test05UpdateLetterTemplateWithUpload()
        {
            letterTemplateDetails.FilePath = path + UploadFileName;
            letterTemplateDetails.DoUpload = true;
            LetterTemplateDetails updatedLetterTemplate = roiAdminController.UpdateLetterTemplate(letterTemplateDetails, fileTransfer);
            Assert.AreNotEqual(letterTemplateDetails.RecordVersionId, updatedLetterTemplate.RecordVersionId);
            Assert.IsTrue(updatedLetterTemplate.IsUploadSuccess);
            letterTemplateDetails = updatedLetterTemplate;
        }

        /// <summary>
        /// Test case for retrieve all Letter Templates.
        /// </summary>
        [Test]
        public void Test06RetrieveAllLetterTemplates()
        {
            Collection<LetterTemplateDetails> letterTemplateDetails = roiAdminController.RetrieveAllLetterTemplates();

            if (letterTemplateDetails.Count > 0)
            {
                LetterTemplateDetails letterTemplate = letterTemplateDetails[0];

                Assert.IsInstanceOfType(typeof(long), letterTemplate.Id);
                Assert.IsInstanceOfType(typeof(LetterType), letterTemplate.LetterType);
                Assert.IsInstanceOfType(typeof(string), letterTemplate.Name);
                Assert.IsInstanceOfType(typeof(string), letterTemplate.Description);
                Assert.IsInstanceOfType(typeof(bool), letterTemplate.IsDefault);
                Assert.IsInstanceOfType(typeof(string), letterTemplate.FileName);
            }
        }

        /// <summary>
        /// Test case for get LetterTemplate with non Existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07GetLetterTemplateWithNonExistingId()
        {
            LetterTemplateDetails LetterTemplate = roiAdminController.GetLetterTemplate(0);
        }

        /// <summary>
        /// Test case for Creating Letter Template with, Letter Type = Other and 
        /// Default = true;
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08CreateLetterTemplate()
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.IsDefault = true;
            templateDetails.LetterType = LetterType.Other;
            templateDetails.Name = "Test Letter" + TestBiilingAdminController.GetUniqueId();
            templateDetails.FilePath = path + UploadFileName;
            templateDetails.Description = "Test Letter";
            templateDetails.DoUpload = false;
            templateDetails.DocumentId = 0;

            LetterTemplateDetails details = roiAdminController.CreateLetterTemplate(templateDetails, fileTransfer);
        }

        /// <summary>
        ///  Test Create Letter Template With Existing Name
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09CreateLetterTemplateWithExistingName()
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.LetterType  = letterTemplateDetails.LetterType;
            templateDetails.Name        = letterTemplateDetails.Name;
            templateDetails.Description = letterTemplateDetails.Description;
            templateDetails.FilePath    = letterTemplateDetails.FilePath;
            templateDetails.IsDefault   = true;
            roiAdminController.CreateLetterTemplate(templateDetails, fileTransfer);
        }

        /// <summary>
        /// Test case for create LetterTemplate with validation.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10CreateLetterTemplateWithValidation()
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.Name = string.Empty;
            templateDetails.FilePath = string.Empty;            
            roiAdminController.CreateLetterTemplate(templateDetails, fileTransfer);
        }
    
        /// <summary>
        /// Test case for create LetterTemplate with validation.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test11CreateTemplateWithValidation()
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            string templateName = "Template Name";
            while (templateName.Length <= 50)
            {
                templateName += templateName;
            }
            templateDetails.Name = templateName;

            string fileName = "Tempalte_Test";
            while (fileName.Length <= 256)
            {
                fileName += fileName;
            }
            fileName += ".pdf";
            templateDetails.FileName = fileName;

            string description = "Test Description field";
            while (description.Length <= 256)
            {
                description += description;
            }
            templateDetails.Description = description;

            File.Create(path + @"\temp.doc");

            string filePath = path + @"\temp.doc";
            templateDetails.FilePath = filePath;

            roiAdminController.CreateLetterTemplate(templateDetails, fileTransfer);
        }

        /// <summary>
        /// Test case for update LetterTemplate invalid file path.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test12UpdateLetterTemplateWithValidation()
        {

            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.IsDefault = true;
            templateDetails.LetterType = LetterType.Invoice;
            templateDetails.Name = "Test Letter" + TestBiilingAdminController.GetUniqueId();            
            templateDetails.Description = "Test Letter";
            templateDetails.DoUpload = true;
            string filePath = path + @"\Test.rtf";
            templateDetails.FilePath = filePath;

            roiAdminController.UpdateLetterTemplate(templateDetails, fileTransfer);            
        }

        /// <summary>
        /// Test case for update LetterTemplate with filename of maximum 256 character
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test13UpdateLetterTemplateWithValidation()
        {

            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.IsDefault = true;
            templateDetails.LetterType = LetterType.Invoice;
            templateDetails.Name = "Test Letter" + TestBiilingAdminController.GetUniqueId();
            templateDetails.Description = "Test Letter";
            templateDetails.DoUpload = true;
            templateDetails.Description = "Test value";
            string fileName = "Test1";
            while (fileName.Length <= 256)
            {
                fileName += fileName;
            }
            templateDetails.FileName = fileName + ".pdf";
            string filePath = path + templateDetails.FileName;
            templateDetails.FilePath = filePath;

            roiAdminController.UpdateLetterTemplate(templateDetails, fileTransfer);
        }        

        /// <summary>
        /// Test case for delete Letter Template with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test14DeleteNonExistingLetterTemplate()
        {
            roiAdminController.DeleteLetterTemplate(0);
        }

         /// <summary>
        /// Download the Letter Template for the given Letter Template id.
        /// </summary>
        [Test]
        public void Test15DownloadLetterTemplate()
        {            
            letterTemplateDetails = roiAdminController.GetLetterTemplate(id);
            ROIAdminController.DownloadLetterTemplate(letterTemplateDetails, 
                                                      path + DownloadFileName);
        }

        /// <summary>
        /// Download the Letter Template for the given Letter Template id with existing filename.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test16DownloadLetterTemplateWithNonExistingDocID()
        {
            letterTemplateDetails = roiAdminController.GetLetterTemplate(id);
            letterTemplateDetails.DocumentId = 0;
            ROIAdminController.DownloadLetterTemplate(letterTemplateDetails,
                                                      path + DownloadFileName);
        }

        /// <summary>
        /// Download the Letter Template for the given Letter Template id with Empty filepath.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test17DownloadLetterTemplate()
        {
            letterTemplateDetails = roiAdminController.GetLetterTemplate(id);
            letterTemplateDetails.DocumentId = 0;
            ROIAdminController.DownloadLetterTemplate(letterTemplateDetails, null);
        }

        /// <summary>
        /// Download the Letter Template for the given Letter Template id.
        /// </summary>
        [Test]
        public void Test18DownloadLetterTemplate()
        {
            letterTemplateDetails = roiAdminController.GetLetterTemplate(id);            
            bool ChunkEnabled = !Convert.ToBoolean(GetConfigProperty("ChunkEnabled"));
            SetConfigProperty("ChunkEnabled", ChunkEnabled.ToString());
            ROIAdminController.DownloadLetterTemplate(letterTemplateDetails,
                                                      path + "Test_Download_Template.rtf");
            SetConfigProperty("ChunkEnabled", (!ChunkEnabled).ToString());
        }

        /// <summary>
        /// Test Case for File Create with file owner type as Empty.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test19CreateLetterWithInvalidOwnerType()
        {
            SetConfigProperty("FileOwnerType", string.Empty);
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            templateDetails.IsDefault = letterTemplateDetails.IsDefault;
            templateDetails.LetterType = letterTemplateDetails.LetterType;
            templateDetails.Name = letterTemplateDetails.Name + TestBiilingAdminController.GetUniqueId();
            templateDetails.FilePath = path + UploadFileName;
            templateDetails.DoUpload = false;
            templateDetails.DocumentId = 0;
            roiAdminController.CreateLetterTemplate(templateDetails, fileTransfer);
        }

        /// <summary>
        /// Test Case for File Update with file owner type as Empty.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test20UpdateLetterWithInvalidOwnerType()
        {
            SetConfigProperty("FileOwnerType", string.Empty);
            LetterTemplateDetails templateDetails = roiAdminController.GetLetterTemplate(id);
            templateDetails.FilePath = path + UploadFileName;
            templateDetails.DoUpload = true;
            LetterTemplateDetails updatedLetterTemplate = roiAdminController.UpdateLetterTemplate(templateDetails, fileTransfer);
        }

        /// <summary>
        /// Test case for deletion of selected Letter Template.
        /// </summary>
        [Test]
        public void Test21DeleteLetterTemplate()
        {
            roiAdminController.DeleteLetterTemplate(id);
        }

        /// <summary>
        /// Test Case for PDF File Validation.
        /// When file in Open Mode
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test22CreateLetterTemplateWithValidation()
        {
            string filePath = path + UploadFileName;
            if (!File.Exists(filePath))
            {
                CreateTempFile();
            }
            uploadFileStream = new FileStream(filePath, FileMode.Open);
            letterTemplateDetails.FilePath = filePath;            
            roiAdminController.CreateLetterTemplate(letterTemplateDetails, fileTransfer);
        }      

        /// <summary>
        /// Test Case for PDF File Validation.
        /// When file is in Readonly Mode.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test23CreateLetterTemplateWithValidation()
        {
            string filePath = path + UploadFileName;

            if (!File.Exists(filePath))
            {
                CreateTempFile();
            }            
            File.SetAttributes(filePath, FileAttributes.ReadOnly);
            letterTemplateDetails.FilePath = filePath;
            roiAdminController.CreateLetterTemplate(letterTemplateDetails, fileTransfer);
        }

        /// <summary>
        /// Test Case for HasLetterTemplate
        /// </summary>
        [Test]
        public void Test24HasLetterTemplate()
        {
             Assert.IsTrue(ROIAdminController.Instance.HasLetterTemplate(LetterType.Invoice));
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

        /// <summary>
        /// Dummy method to handle the progress bar event.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_LetterType(object sender, EventArgs e)
        {            
        }

        #endregion
    }
}
