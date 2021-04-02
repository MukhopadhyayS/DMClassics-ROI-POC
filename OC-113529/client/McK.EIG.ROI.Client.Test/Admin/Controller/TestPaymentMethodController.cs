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
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test case for PaymentMethod Controller.
    /// </summary>
    [TestFixture]
    [Category("TestPaymentMethodController")]
    public class TestPaymentMethodController : TestBase
    {
        #region Fields
       
        private long id;
        private string name;
        private string desc;
        private bool display;
        private BillingAdminController billingAdminController;

        #endregion Fields

        #region Construtor

        public TestPaymentMethodController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            name = "Debit" + TestBiilingAdminController.GetUniqueId();
            desc = "DebitCard";
            display = true;
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            billingAdminController = BillingAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            billingAdminController = null;
        }

        #endregion

        #region Test Methods

        /// <summary>
        /// Creates the Payment method details.
        /// </summary>
        [Test]
        public void Test01CreatePaymentMethod()
        {
            PaymentMethodDetails paymentMethod = new PaymentMethodDetails();

            paymentMethod.Name = name;
            paymentMethod.Description = desc;
            paymentMethod.IsDisplay = display;
            
            id = billingAdminController.CreatePaymentMethod(paymentMethod);
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the PaymentMethod for the given PaymentMethodId
        /// </summary>
        [Test]
        public void Test02GetPaymentMethod()
        {
            PaymentMethodDetails paymentMethod = null;

            paymentMethod = billingAdminController.GetPaymentMethod(id);

            Assert.IsInstanceOfType(typeof(long), paymentMethod.Id);
            Assert.IsInstanceOfType(typeof(string), paymentMethod.Name);
            Assert.IsInstanceOfType(typeof(string), paymentMethod.Description);
            Assert.IsInstanceOfType(typeof(bool), paymentMethod.IsDisplay);
        }

        /// <summary>
        ///  Update the PaymentMethod
        /// </summary>
        [Test]
        public void Test03UpdatePaymentMethod()
        {
            PaymentMethodDetails paymentMethod = new PaymentMethodDetails();

            paymentMethod.Id = id;
            paymentMethod.Name = name;
            paymentMethod.Description = desc + " Test update";
            paymentMethod.IsDisplay = false;
            billingAdminController.UpdatePaymentMethod(paymentMethod);

        }

        /// <summary>
        ///  Deletes a selected PaymentMethod
        /// </summary>
        [Test]
        public void Test04DeletePaymentMethod()
        {
            billingAdminController.DeletePaymentMethod(id);
            Assert.IsTrue(true);
        }

       
        /// <summary>
        /// Get all the PaymentMethods
        /// </summary>
        [Test]
        public void Test05RetrieveAllPaymentMethods()
        {
            Collection<PaymentMethodDetails> paymentMethods = billingAdminController.RetrieveAllPaymentMethods();
            
            foreach (PaymentMethodDetails paymentMethod in paymentMethods)
            {
                Assert.IsInstanceOfType(typeof(long), paymentMethod.Id);
                Assert.IsInstanceOfType(typeof(string), paymentMethod.Name);
                Assert.IsInstanceOfType(typeof(string), paymentMethod.Description);
                Assert.IsInstanceOfType(typeof(bool), paymentMethod.IsDisplay);
            }
        }
        
        /// <summary>
        /// Test case for get PaymentMethod with invalid id
        /// </summary>
        
        [ExpectedException(typeof(ROIException))]
        public void Test07GetPaymentMethodWithInValidId()
        {
            long paymentMethodId = 0;
            PaymentMethodDetails paymentMethod = billingAdminController.GetPaymentMethod(paymentMethodId);
        }

        /// <summary>
        ///  Test Create payment method With Existing Name
        /// </summary>
        //[Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08CreatePaymentMethodWithExistingName()
        {
            PaymentMethodDetails paymentMethod = new PaymentMethodDetails();

            paymentMethod.Name = name;
            paymentMethod.Description = desc;
            paymentMethod.IsDisplay = false;

            id = billingAdminController.CreatePaymentMethod(paymentMethod);
        }

        /// <summary>
        /// Test Create payment method With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09CreatePaymentMethodWithValidation()
        {
            PaymentMethodDetails paymentMethod = new PaymentMethodDetails();
            string paymentName = name;
            while (paymentName.Length <= 20)
            {
                paymentName += paymentName;
            }
            paymentMethod.Name = paymentName;

            string paymentDesc = desc;
            while (paymentDesc.Length <= 256)
            {
                paymentDesc += paymentDesc;
            }

            paymentMethod.Description = paymentDesc;
            billingAdminController.CreatePaymentMethod(paymentMethod);
        }

        /// <summary>
        ///  Test Create payment method With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10UpdatePaymentMethodWithValidation()
        {
            PaymentMethodDetails paymentMethod = new PaymentMethodDetails();
            string paymentName = name;
            while (paymentName.Length <= 20)
            {
                paymentName += paymentName;
            }
            paymentMethod.Name = paymentName;

            string paymentDesc = desc;
            while (paymentDesc.Length <= 256)
            {
                paymentDesc += paymentDesc;
            }

            paymentMethod.Description = paymentDesc;
            billingAdminController.UpdatePaymentMethod(paymentMethod);
        }

        /// <summary>
        ///  Test Create PaymentMethod with null value
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test11CreatePaymentMethodWithNullValue()
        {
            PaymentMethodDetails paymentMethod = new PaymentMethodDetails();
            paymentMethod.Name = string.Empty;
            billingAdminController.CreatePaymentMethod(paymentMethod);
        }


        /// <summary>
        /// Test case for delete Payment Method with invalid id
        /// </summary>

        public void Test12DeleteNonExistingPaymentMethod()
        {
            billingAdminController.DeletePaymentMethod(0);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            PaymentMethod serverPaymentMethod = null;
            PaymentMethodDetails clientPaymentMethod = BillingAdminController.MapModel(serverPaymentMethod);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            PaymentMethodDetails clientPaymentMethod = null;
            PaymentMethod serverPaymentMethod = BillingAdminController.MapModel(clientPaymentMethod);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            PaymentMethod[] paymentMethods = null;
            Collection<PaymentMethodDetails> clientPaymentMethodList = BillingAdminController.MapModel(paymentMethods);
        }

        #endregion
    }
}
