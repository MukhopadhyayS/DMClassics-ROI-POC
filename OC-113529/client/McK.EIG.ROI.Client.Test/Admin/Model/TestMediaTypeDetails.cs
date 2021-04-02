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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for MediaTypeDetails.    
    /// </summary>
    [TestFixture]
    public class TestMediaTypeDetails
    {
        // Holds the object of model class.
        private MediaTypeDetails mediaType;        

        [SetUp]
        public void Initialize()
        {
            mediaType = new MediaTypeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            mediaType = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for MediaType Id.
        /// </summary>
        [Test]
        public void TestMediaTypeId()
        {
            long inputMediaTypeId = 100;
            mediaType.Id = inputMediaTypeId;
            long outputMediaTypeId = mediaType.Id;
            Assert.AreEqual(inputMediaTypeId, outputMediaTypeId);
        }

        /// <summary>
        /// Test case for MediaType Name.
        /// </summary>
        [Test]
        public void TestMediaTypeName()
        {
            string inputMediaTypeName = "Sample1";
            mediaType.Name = inputMediaTypeName;
            string outputMediaTypeName = mediaType.Name;
            Assert.AreEqual(inputMediaTypeName, outputMediaTypeName);
        }

        /// <summary>
        /// Test case for MediaType Desc.
        /// </summary>
        [Test]
        public void TestMediaTypeDesc()
        {
            string inputMediaTypeDescription = "Desc1";
            mediaType.Description = inputMediaTypeDescription;
            string outputMediaTypeDesc = mediaType.Description;
            Assert.AreEqual(inputMediaTypeDescription, outputMediaTypeDesc);
            mediaType.Description = null;
            Assert.AreEqual(string.Empty, mediaType.Description);
        }

        /// <summary>
        /// Test case for MediaType IsAssociated.
        /// </summary>
        [Test]
        public void TestMediaTypeIsAssociated()
        {
            bool inputStatus = true;
            mediaType.IsAssociated = inputStatus;
            bool outputStatus = mediaType.IsAssociated;
            Assert.AreEqual(inputStatus, outputStatus);
        }

        /// <summary>
        /// Test case for MediaType Image.
        /// </summary>
        [Test]
        public void TestMediaTypeImage()
        {
            mediaType.Image = null;
            Assert.IsInstanceOfType(typeof(Image), mediaType.Image);
        }

        /// <summary>
        /// Test case for MediaType recordversion.
        /// </summary>
        [Test]
        public void TestMediaTypeRecordVersion()
        {
            int recordVersionId = 0;
            mediaType.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, mediaType.RecordVersionId);
        }

        /// <summary>
        /// Test case for MediaType Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(mediaType.Equals(mediaType));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), mediaType.GetHashCode());
        }

        /// <summary>
        /// Test case for validator
        /// </summary>
        [Test]
        public void TestValidator()
        {
            Assert.AreEqual(true, Validator.Validate(null, null));
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        #endregion
    }
}
