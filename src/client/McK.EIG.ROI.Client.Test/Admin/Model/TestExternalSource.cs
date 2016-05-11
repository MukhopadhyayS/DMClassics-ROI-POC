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
    /// Test class for ExternalSource
    /// </summary>
   [TestFixture]
    public class TestExternalSource
    {
      private ExternalSource externalSource;
       
       [SetUp]
       public void Initialize()
       {

           externalSource = new ExternalSource();
       }

        /// <summary>
        /// Test case for ExternalSource Plugin Name
        /// </summary>
        [Test]
        public void TestExternalSourcePluginName()
        {
            string inputSourceName = "HPF Provider";
            externalSource.SourceName = inputSourceName;
            string outputSourceName = externalSource.Description;
            Assert.AreEqual(inputSourceName, outputSourceName);
        }

        /// <summary>
        /// Test case for ExternalSource Provider Name
        /// </summary>
        [Test]
        public void TestExternalSourceProviderName()
        {
            string inputProviderName ="HPF Provider";
            externalSource.Description = inputProviderName;
            string outputProviderName = externalSource.Description;
            Assert.AreEqual(inputProviderName, outputProviderName);
        }

        /// <summary>
        /// Test case for ExternalSource Provider Description
        /// </summary>
        [Test]
        public void TestExternalSourceProviderDescription()
        {
            string inputProviderDesc = "Provider Description";
            externalSource.Description = inputProviderDesc;
            string outputProviderDesc = externalSource.Description;
            Assert.AreEqual(inputProviderDesc, outputProviderDesc);
        }

       /// <summary>
       /// Test case for ExternalSource ConnectionProperty
       /// </summary>
        [Test]
        public void TestExternalSourceConnectionProperty()
        {
            ConnectionProperty connection = new ConnectionProperty();
            connection.ConfigKey = "ConfigProperty";
            connection.ConfigValue = "ConfigValue";
            externalSource.ConnectionProperties.Add(connection);
            Assert.AreEqual(connection, externalSource.ConnectionProperties[0]);
        }

        /// <summary>
        /// Test case for ExternalSource Equals
       /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(externalSource.Equals(externalSource));
        }

       /// <summary>
       /// Test case for ExternalSource Clone
       /// </summary> 
       [Test]
       public void TestExternalSourceClone()
       {
           externalSource = externalSource.Clone();
           Assert.IsNotNull(externalSource);
       }
    }
}
