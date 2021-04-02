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
    /// Test class for PaginationDetails 
    /// </summary>
    [TestFixture]
    public class TestPaginationDetails
    {
        #region Fields

        private PaginationDetails paginationDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            paginationDetails = new PaginationDetails();
        }

        [TearDown]
        public void Dispose()
        {
            paginationDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the Size.
        /// </summary>
        [Test]
        public void TestSize()
        {
            int input = 1;
            paginationDetails.Size = input;
            int output = paginationDetails.Size;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the IsDesc.
        /// </summary>
        [Test]
        public void TestIsDesc()
        {
            paginationDetails.IsDescending = true;
            Assert.IsTrue(paginationDetails.IsDescending);
        }

        /// <summary>
        /// Test the SortColumn.
        /// </summary>
        [Test]
        public void TestSortColumn()
        {
            string inputColumnName = "Name";
            paginationDetails.SortColumn = inputColumnName;
            string outputColumnName = paginationDetails.SortColumn;
            Assert.AreEqual(inputColumnName, outputColumnName);
        }

        /// <summary>
        /// Test the StartFrom
        /// </summary>
        [Test]
        public void TestStartFrom()
        {
            int inputStartFrom = 10;
            paginationDetails.StartFrom = inputStartFrom;
            int outputStartFrom = paginationDetails.StartFrom;
            Assert.AreEqual(inputStartFrom, outputStartFrom);
        }

        /// <summary>
        /// Test the DomainType
        /// </summary>
        [Test]
        public void TestDomainType()
        {
            string input = "HPF";
            paginationDetails.DomainType = input;
            string output = paginationDetails.DomainType;
            Assert.AreEqual(input, input);
        }

        #endregion
    }
}
