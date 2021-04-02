using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;


//NUnit
using NUnit.Framework;

//McK

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
   /// <summary>
   /// Test class for ConfigureExternalSources Controller
   /// </summary>
    [TestFixture] 
   public class TestConfigureExternalSourcesController
    {
        private Collection<ExternalSource> externalSources;
        
        [SetUp]
        public void Initialize()
        {
           externalSources = new Collection<ExternalSource>();
           if (UserData.Instance.IsLdapEnabled)
           {
               UserData.Instance.UserId = ConfigurationManager.AppSettings["UserId"];
               UserData.Instance.DomainPassword =  ConfigurationManager.AppSettings["DomainPassword"];
               UserData.Instance.Domain =  ConfigurationManager.AppSettings["Domain"];
               UserData.Instance.NewPassword = ConfigurationManager.AppSettings["NewPassword"];
           }
           else
           {
               UserData.Instance.UserId = ConfigurationManager.AppSettings["HpfUserId"];
               UserData.Instance.Password = ConfigurationManager.AppSettings["HpfPassword"];
               UserData.Instance.NewPassword =  ConfigurationManager.AppSettings["NewPassword"];
           }
           externalSources = ROIAdminController.Instance.DisplayExternalSources();
        }

       /// <summary>
       /// Test case to retrieve all external sources
       /// </summary>
       [Test]
       public void Test01RetrieveAllExternalSources()
       {
           Collection<ExternalSource> external_Sources =new Collection<ExternalSource>();
           external_Sources = ROIAdminController.Instance.DisplayExternalSources();
           ExternalSource externalSource=new ExternalSource();
           externalSource= external_Sources[0];
           Assert.IsInstanceOfType(typeof(string), externalSource.Description);
           Assert.IsInstanceOfType(typeof(string), externalSource.Description);
           Collection<ConnectionProperty> properties= new Collection<ConnectionProperty>();
           ConnectionProperty property;

           if (externalSource.ConnectionProperties.Count > 0)
           {
               property = new ConnectionProperty();
               property.ConfigKey = externalSource.ConnectionProperties[0].ConfigKey;
               property.ConfigValue = externalSource.ConnectionProperties[0].ConfigValue;
               properties.Add(property);
           }
           if (externalSource.ConnectionProperties != null)
           {
               Assert.IsInstanceOfType(typeof(Collection<ConnectionProperty>), properties);
           }
       }

        /// <summary>
        /// Test case to update external source
        /// </summary>
        [Test]
        public void Test02updateExternalSource()
        {
            ExternalSource externalSource=new ExternalSource();
            externalSource = externalSources[0];
            externalSource.Description = "Test description";
            ExternalSource updatedExtSource = new ExternalSource();
           bool response= ROIAdminController.Instance.updateExternalSource(externalSource);
           if (response)
           {

               Collection<ExternalSource> external_Sources = new Collection<ExternalSource>();
               external_Sources = ROIAdminController.Instance.DisplayExternalSources();
               updatedExtSource = external_Sources[0];
               Assert.AreEqual(externalSource.Description.Trim(), updatedExtSource.Description.Trim());
           }
            
        }

        /// <summary>
        /// Test case to update configuration properties
        /// </summary>
        [Test]
        public void Test03updateConfigProperties()
        {
            ExternalSource externalSource=new ExternalSource();
            externalSource = externalSources[0];
            ExternalSource updatedExtSource = new ExternalSource();
            if(externalSource.ConnectionProperties.Count>0)
            {
                externalSource.ConnectionProperties[0].ConfigValue = "Test Property Value";
                bool response = ROIAdminController.Instance.updateConfigProperties(externalSource);
                if (response)
                {
                    Collection<ExternalSource> external_Sources = new Collection<ExternalSource>();
                    external_Sources = ROIAdminController.Instance.DisplayExternalSources();
                    
                    updatedExtSource = external_Sources[0];
                    Assert.AreEqual(externalSource.ConnectionProperties[0].ConfigValue, updatedExtSource.ConnectionProperties[0].ConfigValue);
                }
                
     
            }

        }

        /// <summary>
        /// Test case for updating ExternalSource with empty provider description.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04updateExternalSourcewithValidation1()
        {
            ExternalSource externalSource = new ExternalSource();
            externalSource = externalSources[0];
            externalSource.Description = "";

            bool response = ROIAdminController.Instance.updateExternalSource(externalSource);
                    
       }

        /// <summary>
        /// Test case for updating ConnectionProperty with empty Config value.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04updateConnectionPropertywithValidation2()
        {
            ExternalSource externalSource = new ExternalSource();
            externalSource = externalSources[0];
            if (externalSource.ConnectionProperties.Count > 0)
            {
                ConnectionProperty property = new ConnectionProperty();
                property = externalSource.ConnectionProperties[0];
                property.ConfigValue = "";
                bool response = ROIAdminController.Instance.updateConfigProperties(externalSource);
            }
        }



    }
}
