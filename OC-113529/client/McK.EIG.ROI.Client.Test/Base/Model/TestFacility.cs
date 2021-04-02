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
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    [TestFixture]
    public class TestFacility
    {
        private FacilityDetails facility;

        [SetUp]
        public void Initialize()
        {
            facility = new FacilityDetails();
        }

        [TearDown]
        public void Dispose()
        {
            facility = null;
        }

        [Test]
        public void TestConstructor()
        {
            FacilityDetails facilityDetails = new FacilityDetails("Test", FacilityType.NonHpf);
            Assert.IsEmpty(facilityDetails.Name);
        }

        [Test]
        public void TestCode()
        {
            string inputCode = "IE";
            facility.Code = inputCode;
            string outputCode = facility.Code;
            Assert.AreEqual(inputCode, outputCode);
        }

        [Test]
        public void TestName()
        {
            string inputName = "IE";
            facility.Name = inputName;
            string outputName = facility.Name;
            Assert.AreEqual(inputName, outputName);
        }

        [Test]
        public void TestType()
        {
            FacilityType inputType = FacilityType.Hpf;
            facility.Type = inputType;
            FacilityType outputType = facility.Type;
            Assert.AreEqual(inputType, outputType);
        }

        [Test]
        public void TestEquals()
        {
            FacilityDetails facility = new FacilityDetails("Search1", "S1", FacilityType.Hpf);
            Assert.IsTrue(facility.Equals(facility));
            Assert.IsFalse(facility.Equals(System.DBNull.Value));
        }


        [Test]
        public void TestGetHashCode()
        {
            FacilityDetails facility = new FacilityDetails("Search1", "S1", FacilityType.Hpf);
            Assert.IsNotNull(facility.GetHashCode());
        }


        [Test]
        public void Test01GetFacilty()
        {
            UserData.Instance.Facilities.Add(new FacilityDetails("facHpf", "facHpf", FacilityType.Hpf));
            FacilityDetails facility = FacilityDetails.GetFacility("facHpf", FacilityType.Hpf);
            Assert.IsNotNull(facility);
            UserData.Instance.Facilities.Remove(facility);
        }

        [Test]
        public void Test02GetFacilty()
        {
            FacilityDetails facility = FacilityDetails.GetFacility("temp123", FacilityType.Hpf);
            Assert.IsNull(facility);
        }

    }
}
