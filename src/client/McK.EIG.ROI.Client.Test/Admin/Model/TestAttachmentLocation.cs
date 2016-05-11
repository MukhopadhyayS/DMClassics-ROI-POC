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

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for AttachmentLocation.
    /// </summary>
    [TestFixture]
    class TestAttachmentLocation
    {
        private AttachmentLocation attachmentLocation;

        [SetUp]
        public void Initialize()
        {
            attachmentLocation = new AttachmentLocation();
        }

        [TearDown]
        public void Dispose()
        {
            attachmentLocation = null;
        }

        /// <summary>
        /// Test case for Id.
        /// </summary>
        [Test]
        public void TestId()
        {
            long inputId = 1;
            attachmentLocation.Id = inputId;
            long outputId = attachmentLocation.Id;
            Assert.AreEqual(inputId, outputId);
        }


        /// <summary>
        /// Test case for Location.
        /// </summary>
        [Test]
        public void TestLocation()
        {
            string inputLocation = "AD";
            attachmentLocation.Location = inputLocation;
            string outputLocation = attachmentLocation.Location;
            Assert.AreEqual(inputLocation, outputLocation);
        }
    }
}
