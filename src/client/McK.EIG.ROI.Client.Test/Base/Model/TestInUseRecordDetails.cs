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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for InUseRecordDetails
    /// </summary>
    [TestFixture]
    public class TestInUseRecordDetails
    {
        private InUseRecordDetails inUseRecord;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            inUseRecord = new InUseRecordDetails();
        }

        /// <summary>
        /// Dispose AddressDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            inUseRecord = null;
        }

        /// <summary>
        /// Test method for user Id.
        /// </summary>
        [Test]
        public void TestUserId()
        {
            string userId = "admin";
            inUseRecord.UserId = userId;
            Assert.AreEqual(userId, inUseRecord.UserId);
        }

        /// <summary>
        /// Test method for Application Id.
        /// </summary>
        [Test]
        public void TestApplicationId()
        {
            string applicationId = ROIController.ApplicationId;
            inUseRecord.ApplicationId = applicationId;
            Assert.AreEqual(applicationId, inUseRecord.ApplicationId);
        }

        /// <summary>
        /// Test method for object Id.
        /// </summary>
        [Test]
        public void TestObjectId()
        {
            string objectId = "1";
            inUseRecord.ObjectId = objectId;
            Assert.AreEqual(objectId, inUseRecord.ObjectId);
        }

        /// <summary>
        /// Test method for object type.
        /// </summary>
        [Test]
        public void TestObjectType()
        {
            string objectType = ROIConstants.RequestDomainType;
            inUseRecord.ObjectType = objectType;
            Assert.AreEqual(objectType, inUseRecord.ObjectType);
        }
    }
}
