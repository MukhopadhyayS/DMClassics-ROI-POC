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

using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for TaxPerFacilityComparer.
    /// </summary>
    [TestFixture]
    class TestTaxPerFacilityComparer : TaxPerFacilityComparer
    {
        private TaxPerFacilityComparer taxPerFacilityComparer;

        [SetUp]
        public void Initialize()
        {
            taxPerFacilityComparer = new TaxPerFacilityComparer();
        }

        [TearDown]
        public void Dispose()
        {
            taxPerFacilityComparer = null;
        }

        /// <summary>
        /// Test case for GetPropertyValue.
        /// </summary>
        [Test]
        public void TestGetPropertyValue()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();
            taxPerFacilityDetails.Id = 101;

            PropertyDescriptorCollection PDC = TypeDescriptor.GetProperties(new Guid());
            PropertyDescriptor propertyDescriptor = PDC[System.Reflection.MethodBase.GetCurrentMethod().Name];

            Object object1 = GetPropertyValue(taxPerFacilityDetails, propertyDescriptor);
            Object object2 = GetPropertyValue(taxPerFacilityDetails, propertyDescriptor);
            Assert.AreSame(object1, object2);
        }

    }
}
