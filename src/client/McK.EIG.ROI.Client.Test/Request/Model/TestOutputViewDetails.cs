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
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{ 
    /// <summary>
    /// Test class for CommentDetails 
    /// </summary>
    [TestFixture]
    public class TestOutputViewDetails
    {
        #region Fields

        private OutputViewDetails outputViewDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            outputViewDetails = new OutputViewDetails();
        }

        [TearDown]
        public void Dispose()
        {   
            outputViewDetails = null;
        }

        #endregion

        /// <summary>
        /// Test Header.
        /// </summary>
        [Test]
        public void TestIsHeader()
        {
            bool isHeader = true;
            outputViewDetails.IsHeader = isHeader;
            Assert.IsTrue(outputViewDetails.IsHeader);
        }

        /// <summary>
        /// Test Footer.
        /// </summary>
        [Test]
        public void TestIsFooter()
        {
            bool isFooter = true;
            outputViewDetails.IsFooter = isFooter;
            Assert.IsTrue(outputViewDetails.IsFooter);
        }

        /// <summary>
        /// Test Watermark.
        /// </summary>
        [Test]
        public void TestIsWatermark()
        {
            bool isWatermark = true;
            outputViewDetails.IsWatermark = isWatermark;
            Assert.IsTrue(outputViewDetails.IsWatermark);
        }

        /// <summary>
        /// Test Header enabled
        /// </summary>
        [Test]
        public void TestIsHeaderEnabled()
        {
            bool isHeaderEnabled = true;
            outputViewDetails.IsHeaderEnabled = isHeaderEnabled;
            Assert.IsTrue(outputViewDetails.IsHeaderEnabled);
        }

        /// <summary>
        /// Test Footer enabled.
        /// </summary>
        [Test]
        public void TestIsFooterEnabled()
        {
            bool isFooterEnabled = true;
            outputViewDetails.IsFooterEnabled = isFooterEnabled;
            Assert.IsTrue(outputViewDetails.IsFooterEnabled);
        }

        /// <summary>
        /// Test Watermark..
        /// </summary>
        [Test]
        public void TestWatermark()
        {
            string watermark = "Confidential";
            outputViewDetails.Watermark = watermark;
            Assert.AreEqual(watermark, outputViewDetails.Watermark);
        }

        /// <summary>
        /// Test Collate.
        /// </summary>
        [Test]
        public void TestIsCollate()
        {
            bool isCollate = true;
            outputViewDetails.IsCollate = isCollate;
            Assert.IsTrue(outputViewDetails.IsCollate);
        }

        /// <summary>
        /// Test the Copies.
        /// </summary>
        [Test]
        public void TestCopies()
        {
            int copies = 2;
            outputViewDetails.Copies = copies;
            Assert.AreEqual(copies, outputViewDetails.Copies);
        }

        /// <summary>
        /// Test Page Separator.
        /// </summary>
        [Test]
        public void TestIsPageSeparator()
        {
            bool isPageSeparator = true;
            outputViewDetails.IsPageSeparator = isPageSeparator;
            Assert.IsTrue(outputViewDetails.IsPageSeparator);
        }

        /// <summary>
        /// Test the Where.
        /// </summary>
        [Test]
        public void TestWhere()
        {
            string where = "111.111.11.11";
            outputViewDetails.Where = where;
            Assert.AreEqual(where, outputViewDetails.Where);
        }

        /// <summary>
        /// Test the Comment.
        /// </summary>
        [Test]
        public void TestComment()
        {
            string comment = "Comment";
            outputViewDetails.Comment = comment;
            Assert.AreEqual(comment, outputViewDetails.Comment);
        }


        /// <summary>
        /// Test case for Is MRN Masking.
        /// </summary>
        [Test]
        public void TestIsMRNMaking()
        {
            outputViewDetails.IsMRNMasking = true;
            Assert.IsTrue(outputViewDetails.IsMRNMasking);
        }

        /// <summary>
        /// Test the MRN Masking Value.
        /// </summary>
        [Test]
        public void TestMRNMaskingValue()
        {
            int input = 2;
            outputViewDetails.MRNMaskingValue = input;
            Assert.AreEqual(input, outputViewDetails.MRNMaskingValue);
        }

        #endregion
    }
}
