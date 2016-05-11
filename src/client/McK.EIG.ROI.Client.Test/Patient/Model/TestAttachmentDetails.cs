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
    /// Test class for AttachmentDetails
    /// </summary>
    [TestFixture]
    public class TestAttachmentDetails
    {
        private AttachmentDetails attachment;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            attachment = new AttachmentDetails();
        }

        /// <summary>
        /// Dispose PatientDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            attachment = null;
        }

        /// <summary>
        /// Test method for Attachment ID.
        /// </summary>
        [Test]
        public void TestAttachmentId()
        {
            long inputId = 2;
            attachment.Id = inputId;
            long outputId = attachment.Id;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Test method for Patient ID.
        /// </summary>
        [Test]
        public void TestPatientId()
        {
            long inputPatientId = 121;
            attachment.PatientId = inputPatientId;
            long outputPatientId = attachment.PatientId;
            Assert.AreEqual(inputPatientId, outputPatientId);
        }

        /// <summary>
        /// Test method for Attachment Type.
        /// </summary>
        [Test]
        public void TestAttachmentType()
        {
            string inputName = "Local File";
            attachment.AttachmentType = inputName;
            string outputName = attachment.AttachmentType;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for Subtitle
        /// </summary>
        [Test]
        public void TestAttachmentSubtitle()
        {
            string inputName = "History";
            attachment.Subtitle = inputName;
            string outputName = attachment.Subtitle;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for PageCount
        /// </summary>
        [Test]
        public void TestAttachmentPageCount()
        {
            int pageCount = 3;
            attachment.PageCount = pageCount;
            Assert.AreEqual(pageCount, attachment.PageCount);
        }

        /// <summary>
        /// Test method for Attachment Encounter.
        /// </summary>
        [Test]
        public void TestAttachmentEncounter()
        {
            string inputEncounter = "ENC1001";
            attachment.Encounter = inputEncounter;
            string outputEncounter = attachment.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Test method for Document Data of Service.
        /// </summary>
        [Test]
        public void TestAttachmentDateOfService()
        {
            DateTime inputDateTime = DateTime.Now;
            attachment.DateOfService = inputDateTime;
            DateTime outputDateTime = attachment.DateOfService.Value;
            Assert.AreEqual(inputDateTime, outputDateTime);
        }

        /// <summary>
        /// Test method for Attachment Data of Service.
        /// </summary>
        [Test]
        public void TestAttachmentDate()
        {
            DateTime inputDateTime = DateTime.Now;
            attachment.AttachmentDate = inputDateTime;
            DateTime outputDateTime = attachment.AttachmentDate.Value;
            Assert.AreEqual(inputDateTime, outputDateTime);
        }

        /// <summary>
        /// Test method for Facility.
        /// </summary>
        [Test]
        public void TestAttachmentFacility()
        {
            string inputFacility = "Facility1";
            attachment.FacilityCode = inputFacility;
            string outputFacility = attachment.FacilityCode;
            Assert.AreEqual(inputFacility, outputFacility);
        }

        /// <summary>
        /// Test method for Comment.
        /// </summary>
        [Test]
        public void TestAttachmentComment()
        {
            string inputComment = "Comment1";
            attachment.Comment = inputComment;
            string outputComment = attachment.Comment;
            Assert.AreEqual(inputComment, outputComment);
        }

        /// <summary>
        /// Test method for GetHashCode with ID = 0.
        /// </summary>
        [Test]
        public void TestGetHashCodeWithoutID()
        {
            Assert.IsInstanceOfType(typeof(int), attachment.GetHashCode());
        }

        /// <summary>
        /// Test method for PatientFacility .
        /// </summary>
        [Test]
        public void TestPatientFacility()
        {
            string inputName = "IE";
            attachment.PatientFacility = inputName;
            string outputName = attachment.PatientFacility;
            Assert.AreEqual(inputName, outputName);
        }


        /// <summary>
        /// Test method for Patient FacilityType.
        /// </summary>
        [Test]
        public void TestIsFreeformFacility()
        {
            bool inputIsFreeFrom = true;
            attachment.IsFreeformFacility = inputIsFreeFrom;
            bool outputFreeForm = attachment.IsFreeformFacility;
            Assert.AreEqual(inputIsFreeFrom, outputFreeForm);
        }

        /// <summary>
        /// Test method for patient mrn.
        /// </summary>
        [Test]
        public void TestMrn()
        {
            string inputName = "123";
            attachment.PatientMrn = inputName;
            string outputName = attachment.PatientMrn;
            Assert.AreEqual(inputName, outputName);
        }

        /// <summary>
        /// Test method for patient key.
        /// </summary>
        [Test]
        public void TestPatientKey()
        {
            string inputName = "123";
            attachment.PatientKey = inputName;
            string outputName = attachment.PatientKey;
            Assert.AreEqual(inputName, outputName);
        }


        /// <summary>
        /// Test method for icon
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), attachment.Icon);
        }

        /// <summary>
        /// Test case for document Released
        /// </summary>
        [Test]
        public void TestIsReleased()
        {
            attachment.IsReleased = true;
            bool inputIsReleased = attachment.IsReleased;
            Assert.AreEqual(inputIsReleased, attachment.IsReleased);
        }
        /// <summary>
        /// Test case for attachment file details
        /// </summary>
        [Test]
        public void TestAttachmentFileDetails()
        {
            attachment.FileDetails = new AttachmentFileDetails();
            Assert.IsInstanceOfType(typeof(AttachmentFileDetails), attachment.FileDetails);
        }

        [Test]
        public void TestSorter()
        {
            AttachmentDetails obj = new AttachmentDetails();
            obj.AttachmentType = "Local File";
            obj.Id = 1;
            obj.Encounter = "ENC1001";
            obj.DateOfService = DateTime.Now;

            AttachmentDetails obj2 = new AttachmentDetails();
            obj2.AttachmentType = "CCD/CCR";
            obj2.Id = 2;
            obj2.Encounter = "ENC1002";
            obj2.DateOfService = DateTime.Now.AddMonths(-1);

            int result = AttachmentDetails.Sorter.Compare(obj.Key, obj2.Key);
            Assert.AreEqual(1, result);
        }

        [Test]
        public void TestAttachmentSequence()
        {
            long inputAttachmentSeq = 2;
            attachment.AttachmentSeq = inputAttachmentSeq;
            long outputAttachmentSeq = attachment.AttachmentSeq;
            Assert.AreEqual(inputAttachmentSeq, outputAttachmentSeq);
        }

        [Test]
        public void TestFacilityType()
        {
            FacilityType inputFacilityType = FacilityType.NonHpf;
            attachment.FacilityType = inputFacilityType;
            FacilityType outputFacilityType = attachment.FacilityType;
            Assert.AreEqual(inputFacilityType, outputFacilityType);
        }

        [Test]
        public void TestIsPatientFreeformFacility()
        {
            bool inputPatientIsFreeFrom = true;
            attachment.IsPatientFreeFormFacility = inputPatientIsFreeFrom;
            bool outputPatientFreeForm = attachment.IsPatientFreeFormFacility;
            Assert.AreEqual(inputPatientIsFreeFrom, outputPatientFreeForm);
        }

        [Test]
        public void TestAttachmentMode()
        {
            AttachmentMode inputAttachmentMode = AttachmentMode.Create;
            attachment.Mode = inputAttachmentMode;
            AttachmentMode outputAttachmentMode = attachment.Mode;
            Assert.AreEqual(inputAttachmentMode, outputAttachmentMode);
        }

        [Test]
        public void TestIsDeleted()
        {
            bool inputIsDeleted = true;
            attachment.IsDeleted = inputIsDeleted;
            bool outputIsDeleted = attachment.IsDeleted;
            Assert.AreEqual(inputIsDeleted, outputIsDeleted);
        }

        [Test]
        public void TestAttachmentName()
        {
            attachment.FileDetails.FileType = "PDF";
            Assert.IsNotNull(attachment.Name);
        }

        [Test]
        public void TestAttachmentKey()
        {
            attachment.AttachmentType = "Local File";
            Assert.IsNotNull(attachment.Key);
        }

        [Test]
        public void TestCompareProperty()
        {
            attachment.AttachmentType = "Local File";
            Assert.IsNotNull(attachment.CompareProperty);
        }

        [Test]
        public void TestAttachmentVolume()
        {
            string inputAttachmentVolume = "output";
            attachment.Volume = inputAttachmentVolume;
            string outputAttachmentVolume = attachment.Volume;
            Assert.AreEqual(inputAttachmentVolume, outputAttachmentVolume);
        }

        [Test]
        public void TestAttachmentPath()
        {
            string inputAttachmentPath = "49b";
            attachment.Path = inputAttachmentPath;
            string outputAttachmentPath = attachment.Path;
            Assert.AreEqual(inputAttachmentPath, outputAttachmentPath);
        }
    }
}
