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
    public class TestPropertyDefinition
    {
        #region Fields

        PropertyDefinition propertyDefinition;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            propertyDefinition = new PropertyDefinition();
        }

        [TearDown]
        public void Dispose()
        {
            propertyDefinition = null;
        }

        #endregion

        /// <summary>
        /// Test the constructor
        /// </summary>
        [Test]
        public void TestConstructor()
        {
            PropertyDefinition propertyDefinition = new PropertyDefinition("WATERMARK", "TEST");
            Assert.AreEqual(propertyDefinition.PropertyName, "WATERMARK");
            Assert.AreEqual(propertyDefinition.DefaultValue, "TEST");
        }

        /// <summary>
        /// Test the label
        /// </summary>
        [Test]
        public void TestLabel()
        {
            string label = "Port";
            propertyDefinition.Label = label;
            Assert.AreEqual(label, propertyDefinition.Label);
        }

        /// <summary>
        /// Test the property name
        /// </summary>
        [Test]
        public void TestPropertyName()
        {
            string propertyName = "cdSizeMb";
            propertyDefinition.PropertyName = propertyName;
            Assert.AreEqual(propertyName, propertyDefinition.PropertyName);
        }

        /// <summary>
        /// Test the description
        /// </summary>
        [Test]
        public void TestDescription()
        {
            string description = "Enter the maximum size limit for XS Media Type";
            propertyDefinition.Description = description;
            Assert.AreEqual(description, propertyDefinition.Description);
        }

        /// <summary>
        /// Test the data type
        /// </summary>
        [Test]
        public void TestDataType()
        {
            string dataType = "NUMBER";
            propertyDefinition.DataType = dataType;
            Assert.AreEqual(dataType, propertyDefinition.DataType);
        }

        /// <summary>
        /// Test the default value
        /// </summary>
        [Test]
        public void TestDefaultValue()
        {
            string defaultValue = "703";
            propertyDefinition.DefaultValue = defaultValue;
            Assert.AreEqual(defaultValue, propertyDefinition.DefaultValue);
        }

        /// <summary>
        /// Test the possible values
        /// </summary>
        [Test]
        public void TestPossibleValues()
        {
            Assert.IsNotNull(propertyDefinition.PossibleValues);
        }

        #endregion
    }
}
