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
    /// Test class for AssociatedBillingTemplates.    
    /// </summary>
    [TestFixture]
    public class TestAssociatedBillingTemplates
    {
        // Holds the object of model class.
        private AssociatedBillingTemplate associatedBillingTemplate;

        [SetUp]
        public void Initialize()
        {
            associatedBillingTemplate = new AssociatedBillingTemplate();
        }

        [TearDown]
        public void Dispose()
        {
            associatedBillingTemplate = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for AssociatedRequestorType Id.
        /// </summary>
        [Test]
        public void TestAssociationId()
        {
            long inputAssociatedId = 100;
            associatedBillingTemplate.AssociationId = inputAssociatedId;
            long outputAssociatedId = associatedBillingTemplate.AssociationId;
            Assert.AreEqual(inputAssociatedId, outputAssociatedId);
        }

        /// <summary>
        /// Test case for BillingTemplateId
        /// </summary>
        [Test]
        public void TestBillingTemplateId()
        {
            long inputBillingTemplateId = 101;
            associatedBillingTemplate.BillingTemplateId = inputBillingTemplateId;
            long outputBillingTemplateId = associatedBillingTemplate.BillingTemplateId;
            Assert.AreEqual(inputBillingTemplateId, outputBillingTemplateId);
        }

        /// <summary>
        /// Test case for IsDefault
        /// </summary>
        [Test]
        public void TestIsDefault()
        {
            bool inputIsDefault = true;
            associatedBillingTemplate.IsDefault = inputIsDefault;
            bool outputIsDefault = associatedBillingTemplate.IsDefault;
            Assert.AreEqual(inputIsDefault, outputIsDefault);
        }

        /// <summary>
        /// Test case for AssociatedBillingTemplate Id.
        /// </summary>
        [Test]
        public void TestAssociatedBillingTemplateId()
        {
            long inputAssociatedBillingTemplateId = 1;
            associatedBillingTemplate.BillingTemplateId = inputAssociatedBillingTemplateId;
            long outputAssociatedBillingTemplateId = associatedBillingTemplate.BillingTemplateId;
            Assert.AreEqual(inputAssociatedBillingTemplateId, outputAssociatedBillingTemplateId);
        }

        /// <summary>
        /// Test case for AssociatedBillingTemplate Name.
        /// </summary>
        [Test]
        public void TestAssociatedBillingTemplateName()
        {
            string inputBillingTemplateName = "Billing Template Name";
            associatedBillingTemplate.Name = inputBillingTemplateName;
            string outputBillingTemplateName = associatedBillingTemplate.Name;
            Assert.AreEqual(inputBillingTemplateName, outputBillingTemplateName);
        }


        /// <summary>
        /// Test case for AssociatedBillingTemplate recordversion.
        /// </summary>
        [Test]
        public void TestAssociatedBillingTemplateRecordVersion()
        {
            int recordVersionId = 0;
            associatedBillingTemplate.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, associatedBillingTemplate.RecordVersionId);
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), associatedBillingTemplate.GetHashCode());
        }

        #endregion
    }
}
