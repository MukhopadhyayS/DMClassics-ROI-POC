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
    public class TestOutputPropertyDetails
    {
        #region Fields

        private OutputPropertyDetails outputPropertyDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            outputPropertyDetails = new OutputPropertyDetails();
        }

        [TearDown]
        public void Dispose()
        {
            outputPropertyDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the name.
        /// </summary>
        [Test]
        public void TestOutputDestinationDetails()
        {
            Assert.IsNotNull(outputPropertyDetails.OutputDestinationDetails);           
        }

        /// <summary>
        /// Test the fax.
        /// </summary>
        [Test]
        public void TestOutputViewDetails()
        {   
            OutputViewDetails outputViewDetails = new OutputViewDetails();
            outputPropertyDetails.OutputViewDetails = outputViewDetails;
            outputPropertyDetails.OutputViewDetails.IsHeader = true;
            Assert.IsTrue(outputPropertyDetails.OutputViewDetails.IsHeader);
        }

        /// <summary>
        /// Test media type
        /// </summary>
        [Test]
        public void TestMediaType()
        {
            outputPropertyDetails.MediaType = "CD";
            Assert.AreEqual("CD", outputPropertyDetails.MediaType);
        }    

        #endregion
    }
}
