using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;


//NUnit
using NUnit.Framework;

//McK

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for ConnectionProperty
    /// </summary>
    [TestFixture]
    public class TestConnectionProperties
    {
        private ConnectionProperty connectionProperty;

        [SetUp]
        public void Initialize()
        {
            connectionProperty = new ConnectionProperty();
        }

        /// <summary>
        /// Test case for ConnectionProperty ConfigKey
        /// </summary>
        [Test]
        public void TestConnectionPropertyConfigKey()
        {
            string inputConfigKey = "Database password";
            connectionProperty.ConfigKey = inputConfigKey;
            string outputConfigKey = connectionProperty.ConfigKey;
            Assert.AreEqual(inputConfigKey,outputConfigKey);
        }

        /// <summary>
        /// Test case for ConnectionProperty ConfigValue
        /// </summary>
        [Test]
        public void TestConnectionPropertyConfigValue()
        {
            string inputConfigValue = "Password";
            connectionProperty.ConfigValue = inputConfigValue;
            string outputConfigValue = connectionProperty.ConfigValue;
            Assert.AreEqual(inputConfigValue,outputConfigValue);
        }

        /// <summary>
        /// Test case for ConnectionProperty Equals
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(connectionProperty.Equals(connectionProperty));
        }
    }
}
