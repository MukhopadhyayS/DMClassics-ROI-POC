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
using System.Collections;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for EnumUtlities
    /// </summary>
    [TestFixture]
    public class TestEnumUtilities
    {
        #region TestMethods

        /// <summary>
        /// Test case to get the Description for the Enum specified.
        /// </summary>
        [Test]
        public void Test1GetDescription()
        {
            // Assert.IsInstanceOfType(typeof(string), EnumUtilities.GetDescription(RequestStatus.Completed));
            Assert.IsInstanceOfType(typeof(string), EnumUtilities.GetDescription(RequestAttr.Tpo));
        }
        /// <summary>
        /// Test case to get enum for the Description.
        /// </summary>
        [Test]        
        public void Test2EnumValueOf()
        {
            Assert.IsInstanceOfType(typeof(Enum), EnumUtilities.EnumValueOf("TPO - Treatment Payment Operation", typeof(RequestAttr)));
        }

        /// <summary>
        /// Test case to get enum for the for  unknown Description.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test3EnumValueOf()
        {
            Assert.IsInstanceOfType(typeof(Enum), EnumUtilities.EnumValueOf("test", typeof(RequestAttr)));
        }

        /// <summary>
        /// Test case for converting Enum Description to IList
        /// </summary>
        [Test]        
        public void Test4ToList()
        {
            Assert.IsInstanceOfType(typeof(IList), EnumUtilities.ToList(typeof(RequestAttr)));
        }

        /// <summary>
        /// Test case for converting Enum Description to IList
        /// </summary>
        [Test]
        public void Test5ToListValues()
        {
            Assert.IsInstanceOfType(typeof(IList), EnumUtilities.ToListWithValues(typeof(RequestAttr)));
        }

        #endregion
    }
}
