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
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for RequestorDetails
    /// </summary>
    [TestFixture]
    class TestRequestAttachmentDetails
    {
        //Holds the object of model class.
        private RequestAttachmentDetails requestAttachmentDetails;

        [SetUp]
        public void Initialize()
        {
            requestAttachmentDetails = new RequestAttachmentDetails();
            AttachmentDetails attacmentDetails = new AttachmentDetails();
            attacmentDetails.Id = 2345;
            attacmentDetails.AttachmentDate = DateTime.Now;
            requestAttachmentDetails = new RequestAttachmentDetails(attacmentDetails);
        }

        [TearDown]
        public void Dispose()
        {
            requestAttachmentDetails = null;
        }

        #region TestMethods

        /// <summary>
        /// Test case for Id.
        /// </summary>
        [Test]
        public void TestId()
        {
            long inputId = 100;
            requestAttachmentDetails.Id = inputId;
            long outputId = requestAttachmentDetails.Id;
            requestAttachmentDetails.Id = 0;
            bool test = requestAttachmentDetails.Equals(new RequestAttachmentDetails());
            Assert.NotNull(test);
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test case for Patient Seq.
        /// </summary>
        [Test]
        public void TestPatientSeq()
        {
            long inputPatientSeq = 100;
            requestAttachmentDetails.PatientSeq = inputPatientSeq;
            requestAttachmentDetails.Normalize();
            long outputPatientSeq = requestAttachmentDetails.PatientSeq;
            Assert.AreEqual(inputPatientSeq, outputPatientSeq);
        }

        /// <summary>
        /// Test case for Is HPF.
        /// </summary>
        [Test]
        public void TestIsHPF()
        {
            bool inputIsHPF = true;
            requestAttachmentDetails.IsHPF = inputIsHPF;
            bool outputIsHPF = requestAttachmentDetails.IsHPF;
            Assert.AreEqual(inputIsHPF, outputIsHPF);
        }

        /// <summary>
        /// Test case for Supplemental ID.
        /// </summary>
        [Test]
        public void TestSupplementalID()
        {
            long inputSupplementalID = 101;
            requestAttachmentDetails.SupplementalID = inputSupplementalID;
            long outputSupplementalID = requestAttachmentDetails.SupplementalID;
            Assert.AreEqual(inputSupplementalID, outputSupplementalID);
        }

        /// <summary>
        /// Test case for Attachment Seq.
        /// </summary>
        [Test]
        public void TestAttachmentSeq()
        {
            long inputAttachmentSeq = 102;
            requestAttachmentDetails.AttachmentSeq = inputAttachmentSeq;
            long outputAttachmentSeq = requestAttachmentDetails.AttachmentSeq;
            Assert.AreEqual(inputAttachmentSeq, outputAttachmentSeq);
        }

        /// <summary>
        /// Test case for File Id.
        /// </summary>
        [Test]
        public void TestFileId()
        {
            string inputFileId = "111";
            requestAttachmentDetails.FileId = inputFileId;
            string outputFileId = requestAttachmentDetails.FileId;
            Assert.AreEqual(inputFileId, outputFileId);
        }

        /// <summary>
        /// Test case for File Name.
        /// </summary>
        [Test]
        public void TestFileName()
        {
            string inputFileName = "testFile";
            requestAttachmentDetails.FileName = inputFileName;
            string outputFileName = requestAttachmentDetails.FileName;
            Assert.AreEqual(inputFileName, outputFileName);
        }


        /// <summary>
        /// Test case for File Type.
        /// </summary>
        [Test]
        public void TestFileType()
        {
            string inputFileType = "XML";
            requestAttachmentDetails.FileType = inputFileType;
            string outputFileType = requestAttachmentDetails.FileType;
            Assert.AreEqual(inputFileType, outputFileType);
        }

        /// <summary>
        /// Test case for File Ext.
        /// </summary>
        [Test]
        public void TestFileExt()
        {
            string inputFileExt = ".xml";
            requestAttachmentDetails.FileExt = inputFileExt;
            string outputFileExt = requestAttachmentDetails.FileExt;
            Assert.AreEqual(inputFileExt, outputFileExt);
        }

        /// <summary>
        /// Test case for Is Printable.
        /// </summary>
        [Test]
        public void TestIsPrintable()
        {
            bool inputIsPrintable = false;
            requestAttachmentDetails.IsPrintable = inputIsPrintable;
            bool outputIsPrintable = requestAttachmentDetails.IsPrintable;
            Assert.AreEqual(inputIsPrintable, outputIsPrintable);
        }

        /// <summary>
        /// Test case for Attachment Type.
        /// </summary>
        [Test]
        public void TestAttachmentType()
        {
            string inputAttachmentType = "DOC";
            requestAttachmentDetails.AttachmentType = inputAttachmentType;
            string outputAttachmentType = requestAttachmentDetails.AttachmentType;
            Assert.AreEqual(inputAttachmentType, outputAttachmentType);
        }

        /// <summary>
        /// Test case for Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputEncounter = "test";
            requestAttachmentDetails.Encounter = inputEncounter;
            string outputEncounter = requestAttachmentDetails.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }


        /// <summary>
        /// Test case for Date Of Service.
        /// </summary>
        [Test]
        public void TestDateOfService()
        {
            Nullable<DateTime> inputDateOfService = DateTime.Now;
            requestAttachmentDetails.DateOfService = inputDateOfService;
            Nullable<DateTime> outputDateOfService = requestAttachmentDetails.DateOfService;
            Assert.AreEqual(inputDateOfService, outputDateOfService);
        }

        /// <summary>
        /// Test case for DateReceived.
        /// </summary>
        [Test]
        public void TestDateReceived()
        {
            Nullable<DateTime> inputDateReceived = DateTime.Now;
            requestAttachmentDetails.DateReceived = inputDateReceived;
            Nullable<DateTime> outputDateReceived = requestAttachmentDetails.DateReceived;
            Assert.AreEqual(inputDateReceived, outputDateReceived);
        }

        /// <summary>
        /// Test case for FileAttachDate.
        /// </summary>
        [Test]
        public void TestFileAttachDate()
        {
            Nullable<DateTime> inputFileAttachDate = DateTime.Now;
            requestAttachmentDetails.FileAttachDate = inputFileAttachDate;
            Nullable<DateTime> outputFileAttachDate = requestAttachmentDetails.FileAttachDate;
            Assert.AreEqual(inputFileAttachDate, outputFileAttachDate);
        }

        /// <summary>
        /// Test case for Facility.
        /// </summary>
        [Test]
        public void TestFacility()
        {
            string inputFacility = "A";
            requestAttachmentDetails.Facility = inputFacility;
            string outputFacility = requestAttachmentDetails.Facility;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test case for PatientKey.
        /// </summary>
        [Test]
        public void TestPatientKey()
        {
            string inputPatientKey = "XYZ";
            requestAttachmentDetails.PatientKey = inputPatientKey;
            string outputPatientKey = requestAttachmentDetails.PatientKey;
            Assert.AreEqual(inputPatientKey, outputPatientKey);
        }

        /// <summary>
        /// Test case for Comment.
        /// </summary>
        [Test]
        public void TestComment()
        {
            string inputComment = "TEST COMMENT";
            requestAttachmentDetails.Comment = inputComment;
            string outputComment = requestAttachmentDetails.Comment;
            Assert.AreEqual(inputComment, outputComment);
        }

        /// <summary>
        /// Test case for SequenceNumber.
        /// </summary>
        [Test]
        public void TestSequenceNumber()
        {
            long inputSequenceNumber = 123;
            requestAttachmentDetails.SequenceNumber = inputSequenceNumber;
            long outputSequenceNumber = requestAttachmentDetails.SequenceNumber;
            Assert.AreEqual(inputSequenceNumber, outputSequenceNumber);
        }

        /// <summary>
        /// Test case for SelectedForRelease.
        /// </summary>
        [Test]
        public void TestSelectedForRelease()
        {
            Nullable<bool> inputSelectedForRelease = true;
            requestAttachmentDetails.SelectedForRelease = inputSelectedForRelease;
            Nullable<bool> outputSelectedForRelease = requestAttachmentDetails.SelectedForRelease;
            Assert.AreEqual(inputSelectedForRelease, outputSelectedForRelease);
        }

        /// <summary>
        /// Test case for CreatedBy.
        /// </summary>
        [Test]
        public void TestCreatedBy()
        {
            int inputCreatedBy = 234;
            requestAttachmentDetails.CreatedBy = inputCreatedBy;
            int outputCreatedBy = requestAttachmentDetails.CreatedBy;
            Assert.AreEqual(inputCreatedBy, outputCreatedBy);
        }

        /// <summary>
        /// Test case for BillingTier.
        /// </summary>
        [Test]
        public void TestBillingTier()
        {
            long inputBillingTier = 234;
            requestAttachmentDetails.BillingTier = inputBillingTier;
            long outputBillingTier = requestAttachmentDetails.BillingTier;
            Assert.AreEqual(inputBillingTier, outputBillingTier);
        }

        /// <summary>
        /// Test case for PageCount.
        /// </summary>
        [Test]
        public void TestPageCount()
        {
            int inputPageCount = 234;
            requestAttachmentDetails.PageCount = inputPageCount;
            int outputPageCount = requestAttachmentDetails.PageCount;
            Assert.AreEqual(inputPageCount, outputPageCount);
        }

        /// <summary>
        /// Test case for Subtitle.
        /// </summary>
        [Test]
        public void TestSubtitle()
        {
            string inputSubtitle = "Test Title";
            requestAttachmentDetails.Subtitle = inputSubtitle;
            string outputSubtitle = requestAttachmentDetails.Subtitle;
            Assert.AreEqual(inputSubtitle, outputSubtitle);
        }

        /// <summary>
        /// Test case for PatientID.
        /// </summary>
        [Test]
        public void TestPatientID()
        {
            long inputPatientID = 1234;
            requestAttachmentDetails.PatientID = inputPatientID;
            long outputPatientID = requestAttachmentDetails.PatientID;
            Assert.AreEqual(inputPatientID, outputPatientID);
        }

        /// <summary>
        /// Test case for PatientMRN.
        /// </summary>
        [Test]
        public void TestPatientMRN()
        {
            string inputPatientMRN = "987654321";
            requestAttachmentDetails.PatientMRN = inputPatientMRN;
            string outputPatientMRN = requestAttachmentDetails.PatientMRN;
            Assert.AreEqual(inputPatientMRN, outputPatientMRN);
        }

        /// <summary>
        /// Test case for PatientFacility.
        /// </summary>
        [Test]
        public void TestPatientFacility()
        {
            string inputPatientFacility = "AD";
            requestAttachmentDetails.PatientFacility = inputPatientFacility;
            string outputPatientFacility = requestAttachmentDetails.PatientFacility;
            Assert.AreEqual(inputPatientFacility, outputPatientFacility);
        }

        /// <summary>
        /// Test case for BillingTierStatus.
        /// </summary>
        [Test]
        public void TestBillingTierStatus()
        {
            long inputBillingTierStatus = 6;
            requestAttachmentDetails.BillingTierStatus = inputBillingTierStatus;
            long outputBillingTierStatus = requestAttachmentDetails.BillingTierStatus;
            Assert.AreEqual(inputBillingTierStatus, outputBillingTierStatus);
        }

        /// <summary>
        /// Test case for Uuid.
        /// </summary>
        [Test]
        public void TestUuid()
        {
            string inputUuid = "3214";
            requestAttachmentDetails.Uuid = inputUuid;
            string outputUuid = requestAttachmentDetails.Uuid;
            Assert.AreEqual(inputUuid, outputUuid);
        }

        /// <summary>
        /// Test case for Volume.
        /// </summary>
        [Test]
        public void TestVolume()
        {
            string inputVolume = "D";
            requestAttachmentDetails.Volume = inputVolume;
            string outputVolume = requestAttachmentDetails.Volume;
            Assert.AreEqual(inputVolume, outputVolume);
        }

        /// <summary>
        /// Test case for Path.
        /// </summary>
        [Test]
        public void TestPath()
        {
            string inputPath = "D:\test";
            requestAttachmentDetails.Path = inputPath;
            string outputPath = requestAttachmentDetails.Path;
            Assert.AreEqual(inputPath, outputPath);
        }

        /// <summary>
        /// Test case for IsPatientFreeFormFacility.
        /// </summary>
        [Test]
        public void TestIsPatientFreeFormFacility()
        {
            bool inputIsPatientFreeFormFacility = true;
            requestAttachmentDetails.IsPatientFreeFormFacility = inputIsPatientFreeFormFacility;
            bool outputIsPatientFreeFormFacility = requestAttachmentDetails.IsPatientFreeFormFacility;
            Assert.AreEqual(inputIsPatientFreeFormFacility, outputIsPatientFreeFormFacility);
        }

        /// <summary>
        /// Test case for GetHashCode.
        /// </summary>
        [Test]
        public void TestGetHashCode()
        {
            requestAttachmentDetails.Id = 0;
            requestAttachmentDetails.GetHashCode();
            Assert.NotNull(requestAttachmentDetails);
        }

        /// <summary>
        /// Test case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            requestAttachmentDetails.FileType = "";
            string name1 = requestAttachmentDetails.Name;
            requestAttachmentDetails.FileType = "test";
            string name2 = requestAttachmentDetails.Name;
            Assert.NotNull(requestAttachmentDetails);
        }

        /// <summary>
        /// Test case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            string key = requestAttachmentDetails.Key;
            Assert.NotNull(key);
        }

        /// <summary>
        /// Test case for Icon.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            System.Drawing.Image Icon = requestAttachmentDetails.Icon;
            Assert.NotNull(Icon);
        }

        #endregion
    }
}
