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
//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for AssociatedFeetypes.    
    /// </summary>
    [TestFixture]
    public class TestAssociatedFeetypes
    {
        // Holds the object of model class.
        private AssociatedFeeType associatedFeeType;

        [SetUp]
        public void Initialize()
        {
            associatedFeeType = new AssociatedFeeType();
        }

        [TearDown]
        public void Dispose()
        {
            associatedFeeType = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for AssociatedFeeType Id.
        /// </summary>
        [Test]
        public void TestAssociationId()
        {
            long inputAssociatedId = 100;
            associatedFeeType.AssociationId = inputAssociatedId;
            long outputAssociatedId = associatedFeeType.AssociationId;
            Assert.AreEqual(inputAssociatedId, outputAssociatedId);
        }

        /// <summary>
        /// Test case for AssociatedFeeType Name.
        /// </summary>
        [Test]
        public void TestAssociatedFeeTypeName()
        {
            string inputAssociatedFeeTypeName = "Sample1";
            associatedFeeType.Name = inputAssociatedFeeTypeName;
            string outputAssociatedFeeTypeName = associatedFeeType.Name;
            Assert.AreEqual(inputAssociatedFeeTypeName, outputAssociatedFeeTypeName);
        }


        /// <summary>
        /// Test case for AssociatedFeeType Id.
        /// </summary>
        [Test]
        public void TestAssociatedFeeTypeId()
        {
            long inputAssociatedFeeTypeId = 1;
            associatedFeeType.FeeTypeId = inputAssociatedFeeTypeId;
            long outputAssociatedFeeTypeId = associatedFeeType.FeeTypeId;
            Assert.AreEqual(inputAssociatedFeeTypeId, outputAssociatedFeeTypeId);
        }


        /// <summary>
        /// Test case for AssociatedBillingTemplate Id.
        /// </summary>
        [Test]
        public void TestAssociatedBillingTemplateId()
        {
            long inputAssociatedBillingTemplateId = 1;
            associatedFeeType.BillingTemplateId = inputAssociatedBillingTemplateId;
            long outputAssociatedBillingTemplateId = associatedFeeType.BillingTemplateId;
            Assert.AreEqual(inputAssociatedBillingTemplateId, outputAssociatedBillingTemplateId);
        }



        /// <summary>
        /// Test case for AssociatedFeeType recordversion.
        /// </summary>
        [Test]
        public void TestAssociatedFeeTypeRecordVersion()
        {
            int recordVersionId = 0;
            associatedFeeType.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, associatedFeeType.RecordVersionId);
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), associatedFeeType.GetHashCode());
        }

        #endregion
    }
}
