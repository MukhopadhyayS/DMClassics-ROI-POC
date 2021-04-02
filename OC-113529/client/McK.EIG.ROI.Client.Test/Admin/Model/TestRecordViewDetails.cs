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
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using System.Collections.ObjectModel;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for RequestorTypedetails.   
    /// </summary>
    [TestFixture]
    public class TestRecordViewDetails
    {
        #region Fields

        // Holds the object of model class.
        private RecordViewDetails recordViewDetails;

        #endregion

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            recordViewDetails = new RecordViewDetails();
        }

        [TearDown]
        public void Dispose()
        {
            recordViewDetails = null;
        }

        #endregion

        #region Test Methods

        /// <summary>
        /// Test case for Record View name.
        /// </summary>
        [Test]
        public void TestRecordViewName()
        {
            string inputRecordViewName = "RecordViewName";
            recordViewDetails.Name = inputRecordViewName;
            string outputRecordViewName = recordViewDetails.Name;
            Assert.AreEqual(inputRecordViewName, outputRecordViewName);
        }


        /// <summary>
        /// Test method for DocTypes.
        /// </summary>
        [Test]
        public void TestDocTypes()
        {
            Assert.IsNotNull(recordViewDetails.DocTypes);
        }

        #endregion        
    }
}
