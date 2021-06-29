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
using System.Configuration;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test Cases for Delivery Method Controller.
    /// </summary>
    [TestFixture]
    public class TestDeliveryMethodController : TestBase
    {

        #region Fields

        private ROIAdminController roiAdminController;

        private long id;
        private string name;
        private string description;
        private Uri url;

        #endregion

        #region Constructor

        public TestDeliveryMethodController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            name = "UPS" + TestBiilingAdminController.GetUniqueId();
            description = "UPS Ground";
            url = new Uri("http://www.apps.ups");
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            roiAdminController = ROIAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            roiAdminController = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for Creation of default delivery method.
        /// </summary>
        [Test]
        public void Test01CreationWithDefault()
        {
            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();

            deliveryMethod.Name = name;
            deliveryMethod.Description = description;
            deliveryMethod.Url = url;
            deliveryMethod.IsDefault = true;

            id = roiAdminController.CreateDeliveryMethod(deliveryMethod);
            Assert.IsTrue(id > 0);
        }


        /// <summary>
        /// Test case for creation of non-default delivery method.
        /// </summary>
        [Test]
        public void Test02CreateWithoutDefault()
        {
            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();

            deliveryMethod.Name = "TestWihtoutDefault";
            deliveryMethod.Description = description;
            deliveryMethod.Url = url;
            deliveryMethod.IsDefault = false;

            id = roiAdminController.CreateDeliveryMethod(deliveryMethod);
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the delivery method for the given delivery method id.
        /// </summary>
        [Test]
        public void Test03GetDeliveryMethod()
        {
            DeliveryMethodDetails deliveryMethod = null;
            deliveryMethod = roiAdminController.GetDeliveryMethod(id);

            Assert.IsInstanceOfType(typeof(long),   deliveryMethod.Id);
            Assert.IsInstanceOfType(typeof(string), deliveryMethod.Name);
            Assert.IsInstanceOfType(typeof(string), deliveryMethod.Description);
            Assert.IsInstanceOfType(typeof(Uri), 	deliveryMethod.Url);
            Assert.IsInstanceOfType(typeof(bool),   deliveryMethod.IsDefault);
        }

        /// <summary>
        /// Tese case for update delivery method.
        /// </summary>
        [Test]
        public void Test04UpdateDeliveryMethod()
        {
            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();

            deliveryMethod.Id = id;
            deliveryMethod.Name = "TestWihtoutDefault";
            deliveryMethod.Description = description + "test";
            deliveryMethod.Url = url;
            deliveryMethod.IsDefault = false;

            DeliveryMethodDetails updatedDeliveryMethod = roiAdminController.UpdateDeliveryMethod(deliveryMethod);
            Assert.AreNotEqual(deliveryMethod.RecordVersionId, updatedDeliveryMethod.RecordVersionId);
        } 

        /// <summary>
        /// Test case for retrieve all delivery methods.
        /// </summary>
        [Test]
        public void Test05RetrieveAllDeliveryMethods()
        {
            Collection<DeliveryMethodDetails> deliveryMethods = roiAdminController.RetrieveAllDeliveryMethods();

            if (deliveryMethods.Count > 0)
            {
                DeliveryMethodDetails deliveryMethod = deliveryMethods[0];
                Assert.IsInstanceOfType(typeof(long), deliveryMethod.Id);
                Assert.IsInstanceOfType(typeof(string), deliveryMethod.Name);
                Assert.IsInstanceOfType(typeof(string), deliveryMethod.Description);
                if(deliveryMethod.Url!= null) Assert.IsInstanceOfType(typeof(Uri), deliveryMethod.Url);
                Assert.IsInstanceOfType(typeof(bool), deliveryMethod.IsDefault);
           }
        }

        /// <summary>
        /// Test case for deletion of selected delivery method.
        /// </summary>
        [Test]
        public void Test06DeleteDeliveryMethod()
        {
            roiAdminController.DeleteDeliveryMethod(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for get deliverymethod with non Existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08GetDeliveryMethodWithNonExistingId()
        {
            DeliveryMethodDetails deliveryMethod = roiAdminController.GetDeliveryMethod(0);
        }

        /// <summary>
        ///  Test Create delivery method With Existing Name
        /// </summary>
        //[Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09CreqteDeliveryMethodWithExistingName()
        {
            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();
            deliveryMethod.Name = name; 
            deliveryMethod.Description = description;
            deliveryMethod.Url = url;
            deliveryMethod.IsDefault = true;

            id = roiAdminController.CreateDeliveryMethod(deliveryMethod);
        }
    
        /// <summary>
        /// Test case for create Delivery method with validation.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10CreateDeliveryMethodWithValidation()
        {
            
            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();
            string deliveryName = "testkkk";
            while (deliveryName.Length <= 20)
            {
                deliveryName += deliveryName;
            }
            deliveryMethod.Name = deliveryName;
            string url = this.url.OriginalString;
            while (url.Length <= 256)
            {
                url += url;
            }
            deliveryMethod.Url = new Uri(url);
            roiAdminController.CreateDeliveryMethod(deliveryMethod);
        }

        /// <summary>
        /// Test case for update Delivery method with validation
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test11UpdateDeliveryMethodWithValidation()
        {

            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();
            string deliveryName = name + "testkk";
            while (deliveryName.Length <= 20)
            {
                deliveryName += deliveryName;
            }
            deliveryMethod.Name = deliveryName;

            string url = this.url.OriginalString;
            while (url.Length <= 256)
            {
                url += url;
            }
            deliveryMethod.Url = new Uri(url);
            roiAdminController.UpdateDeliveryMethod(deliveryMethod);
        }

        /// <summary>
        /// Test case for create delivery method with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test12CreateDeliveryMethodWithEmptyName()
        {
            DeliveryMethodDetails deliveryMethod = new DeliveryMethodDetails();
            deliveryMethod.Name = string.Empty;
            deliveryMethod.Url = this.url;
            roiAdminController.CreateDeliveryMethod(deliveryMethod);
        }

        /// <summary>
        /// Test case for delete delivery method with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test13DeleteNonExistingDeliveryMethod()
        {
            roiAdminController.DeleteDeliveryMethod(0);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            DeliveryMethod serverDeliveryMethod = null;
            DeliveryMethodDetails clientDeliveryMethod = ROIAdminController.MapModel(serverDeliveryMethod);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            DeliveryMethodDetails clientDeliveryMethod = null;
            DeliveryMethod serverDeliveryMethod = ROIAdminController.MapModel(clientDeliveryMethod);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            DeliveryMethod[] deliveryMethod = null;
            Collection<DeliveryMethodDetails> deliveryMethodList = ROIAdminController.MapModel(deliveryMethod);
        }
     
        #endregion
    }
}
