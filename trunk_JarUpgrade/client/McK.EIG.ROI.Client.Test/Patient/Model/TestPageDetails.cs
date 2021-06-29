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
    /// Test class for PageDetails
    /// </summary>
    [TestFixture]
    public class TestPageDetails
    {
        private PageDetails pageDetails;

        [SetUp]
        public void Initialize()
        {
            pageDetails = new PageDetails();
        }

        [TearDown]
        public void Dispose()
        {
            pageDetails = null;
        }

        /// <summary>
        /// Test method for PageNumber.
        /// </summary>
        [Test]
        public void Test01PageNumber()
        {
            int inputPageNumber = 1;
            pageDetails.PageNumber = inputPageNumber;
            int outputPageNumber = pageDetails.PageNumber;
            Assert.AreEqual(inputPageNumber, outputPageNumber);
            Assert.AreEqual(ROIConstants.PagePrefix + inputPageNumber.ToString(), pageDetails.Name);
        }

        /// <summary>
        /// Test method for imNetId.
        /// </summary>
        [Test]
        public void Test02imNetId()
        {
            string inputimNetId = "121";
            pageDetails.IMNetId = inputimNetId;
            string outputimNetId = pageDetails.IMNetId;
            Assert.AreEqual(inputimNetId, outputimNetId);
            string output = pageDetails.PageNumber.ToString() + "." + pageDetails.ContentPageNumber.ToString() + "." + pageDetails.IMNetId;
            Assert.AreEqual(output, pageDetails.Key);
        }

        
        /// <summary>
        /// Test method for ContentNumber.
        /// </summary>
        [Test]
        public void Test03ContentNumber()
        {
            int inputContentNumber = 121;
            pageDetails.ContentCount = inputContentNumber;
            int outputContentNumber = pageDetails.ContentCount;
            Assert.AreEqual(inputContentNumber, outputContentNumber);
        }

        /// <summary>
        /// Test method for ContentPageNumber.
        /// </summary>
        [Test]
        public void Test04ContentPageNumber()
        {
            int inputPageNumber = 1;
            pageDetails.PageNumber = inputPageNumber;

            int inputContentPageNumber = 2;
            pageDetails.ContentPageNumber = inputContentPageNumber;

            string inputimNetId = "121";
            pageDetails.IMNetId = inputimNetId;

            int outputContentPageNumber = pageDetails.ContentPageNumber;

            Assert.AreEqual(inputPageNumber, outputContentPageNumber);
            Assert.AreEqual(inputPageNumber, pageDetails.ContentPageNumber);

            string inputKey = inputPageNumber.ToString() + "." + inputContentPageNumber.ToString() + "." + inputimNetId;
            Assert.AreEqual(inputKey, pageDetails.Key);
            Assert.AreEqual(inputKey, pageDetails.CompareProperty);
        }

    }
}
