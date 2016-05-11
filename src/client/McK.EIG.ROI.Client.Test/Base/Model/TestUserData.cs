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
using System.Collections.Generic;
using System.Collections.ObjectModel;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for UserData.
    /// </summary>
    [TestFixture]
    public class TestUserData
    {
        #region Fields

        private UserData userData;       

        #endregion

        #region Setup

        [SetUp]
        public void Init()
        {
            userData = UserData.Instance;         
        }

        #endregion

        #region TearDown

        [TearDown]
        public void Dispose()
        {
            userData = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Unit test case for UserId
        /// </summary>
        /// <returns></returns>
        [Test]
        public void TestUserId()
        {
            userData.UserId = "admin";
            Assert.AreEqual("admin", userData.UserId);
        }

        /// <summary>
        /// Unit test case for FullName
        /// </summary>
        /// <returns></returns>
        [Test]
        public void TestFullname()
        {
            userData.FullName = "admin";
            Assert.AreEqual("admin", userData.FullName);
        }

        /// <summary>
        /// Unit test case for Password
        /// </summary>
        /// <returns></returns>
        [Test]
        public void TestPassword()
        {
            userData.Password = "admin";
            Assert.AreEqual("admin", userData.Password);
        }

        /// <summary>
        /// unit test case to test the user locked or unlocked
        /// </summary>
        [Test]
        public void TestLocked()
        {
            userData.Locked = true;
            Assert.IsTrue(userData.Locked);
            userData.Locked = false;
            Assert.IsFalse(userData.Locked);
        }

        /// <summary>
        /// unit test case to test whetherthe user is lockedPermenantly or unlocked
        /// </summary>
        [Test]
        public void TestLockedOut()
        {
            userData.LockedOut = true;
            Assert.IsTrue(userData.LockedOut);
            userData.LockedOut = false;
            Assert.IsFalse(userData.LockedOut);
        }

        /// <summary>
        /// unit test to test the password expiry date
        /// </summary>
        [Test]
        public void TestNoOfDaysPasswordExpires()
        {
            userData.NumberDaysPasswordExpires = 15;
            Assert.AreEqual(15, userData.NumberDaysPasswordExpires);
        }

        /// <summary>
        /// unit test to test the password expiring property.
        /// </summary>
        [Test]
        public void TestPasswordExpiring()
        {
            userData.PasswordExpiring = true;
            Assert.IsTrue(userData.PasswordExpiring);
            userData.PasswordExpiring = false;
            Assert.IsFalse(userData.PasswordExpiring);
        }

        /// <summary>
        /// unit test to test the password expired property.
        /// </summary>
        [Test]
        public void TestPasswordExpired()
        {
            userData.PasswordExpired = true;
            Assert.IsTrue(userData.PasswordExpired);
            userData.PasswordExpired = false;
            Assert.IsFalse(userData.PasswordExpired);
        }

        /// <summary>
        /// test case to test number of times user had tried logon.
        /// </summary>
        [Test]
        public void TestNoOfFailedLogOnAttempts()
        {
            userData.NoOfFailedLogOnAttempts = 3;
            Assert.AreEqual(3, userData.NoOfFailedLogOnAttempts);
        }

        /// <summary>
        /// test case to test number of times user had tried reset his password.
        /// </summary>
        [Test]
        public void TestNoOfFailedResetAttempts()
        {
            userData.NoOfFailedResetAttempts = 3;
            Assert.AreEqual(3, userData.NoOfFailedResetAttempts);            
        }

        /// <summary>
        /// test case to test the number of days password expires
        /// </summary>
        [Test]
        public void TestNumberDaysPasswordExpires()
        {
            userData.NumberDaysPasswordExpires = 3;
            Assert.AreEqual(3, userData.NumberDaysPasswordExpires);

        }

        /// <summary>
        /// test case to test ticket Id
        /// </summary>
        [Test]
        public void TestTicket()
        {
            userData.Ticket = "101fff";
            Assert.AreEqual("101fff", userData.Ticket);
        }

        /// <summary>
        /// test case to test additional info about the user
        /// </summary>
        [Test]
        public void TestAdditionalInfo()
        {
            string inputValue = "Authorized user";
            userData.AdditionalInfo = inputValue;
            string outputValue = userData.AdditionalInfo;
            Assert.AreEqual(inputValue, outputValue);
        }

        /// <summary>
        /// test case to test whether the user has logged on for first time. 
        /// </summary>
        [Test]
        public void TestFirstTimeUser()
        {
            userData.FirstTimeUser = true;
            Assert.IsTrue(userData.FirstTimeUser);
            userData.FirstTimeUser = false;
            Assert.IsFalse(userData.FirstTimeUser);            
        }

        /// <summary>
        /// test case for staff log on sequence
        /// </summary>
        [Test]
        public void TestStaffLogOnSeq()
        {
            userData.StaffLogOnSeq = 10;
            Assert.AreEqual(10, userData.StaffLogOnSeq);
        }

        /// <summary>
        /// test case for invalid count
        /// </summary>
        [Test]
        public void TestInvalidLogonCount()
        {
            userData.InvalidLogOnCount = 1;
            Assert.AreEqual(1, userData.InvalidLogOnCount);
        }

        /// <summary>
        /// Test case for idle time
        /// </summary>
        [Test]
        public void TestIdleTime()
        {
            userData.IdleTime = 500000;
            Assert.AreEqual(500000, userData.IdleTime);
        }

        /// <summary>
        /// Test case for idle specification state
        /// </summary>
        [Test]
        public void TestIdleSpecified()
        {
            userData.IdleSpecified = true;
            Assert.AreEqual(true, userData.IdleSpecified);
        }
        /// <summary>
        /// test case to test security token
        /// </summary>
        [Test]
        public void TestSecurityToken()
        {
            //first time getting the token
            if (userData.IsLdapEnabled)
            {
                userData.UserId = "ADMIN";
                userData.Password = "TEST";
                userData.DomainUserName = "ais";
                userData.Domain = "EIGQCLAB";
                userData.DomainPassword = "TEST";
                Assert.IsNotNull(userData.SecurityToken);

                //old token is invalid b/c of userid or pwd change
                userData.DomainPassword = "1234";
                userData.Password = "1234";
                Assert.IsNotNull(userData.SecurityToken);
            }
            else
            {
                userData.UserId = "ADMIN";
                userData.Password = "TEST";

                Assert.IsNotNull(userData.SecurityToken);

                //old token is invalid b/c of userid or pwd change
                userData.Password = "1234";
                Assert.IsNotNull(userData.SecurityToken);
            }
            Assert.IsNotNull(userData.SecurityToken);
            //resuse the token
            Assert.IsNotNull(userData.SecurityToken);
        }
        
        /// <summary>
        /// To test epn enabled or not.
        /// </summary>
        [Test]
        public void TestEpnEnabled()
        {
            userData.ConfigurationData = ConfigurationData.Instance;
            userData.EpnEnabled = true;
            Assert.IsTrue(userData.EpnEnabled);
            userData.EpnEnabled = false;
            Assert.IsFalse(userData.EpnEnabled);            
        }

        /// <summary>
        /// To test User Instance id.
        /// </summary>
        [Test]
        public void TestUserInstanceId()
        {
            userData.UserInstanceId = 5;
            Assert.AreEqual(5, userData.UserInstanceId);
        }

        /// <summary>
        /// To test Epn prefix.
        /// </summary>
        [Test]
        public void TestEpnPrefix()
        {
            userData.ConfigurationData = ConfigurationData.Instance;
            userData.EpnPrefix = "Test";
            Assert.AreEqual("Test", userData.EpnPrefix);
        }

        /// <summary>
        /// To test facilities
        /// </summary>
        [Test]
        public void TestFacilities()
        {
            UserData.Instance.Facilities.Add(new FacilityDetails("IE", "IE1", FacilityType.Hpf));
            UserData.Instance.Facilities.Add(new FacilityDetails("AWL", "AWL1", FacilityType.Hpf));
            Assert.IsTrue(userData.Facilities.Count > 0);       
        }

        /// <summary>
        /// To test sorted facilities
        /// </summary>
        [Test]
        public void TestSortedFacilities()
        {
            UserData.Instance.Facilities.Add(new FacilityDetails("IE", "IE1", FacilityType.Hpf));
            UserData.Instance.Facilities.Add(new FacilityDetails("AWL", "AWL1", FacilityType.Hpf));
            Assert.IsTrue(UserData.Instance.SortedFacilities.Count > 0);
        }
        
        ///// <summary>
        ///// To test freeform facilities
        ///// </summary>
        //[Test]
        //public void TestFreeForm()
        //{
        //    Assert.IsNotNull(userData.FreeformFacilities); 
        //}

        /// <summary>
        /// To test security facilities
        /// </summary>
        [Test]
        public void TestSecurityFacilities()
        {
            Assert.IsNotNull(userData.Security);            
        }
       
        /// <summary>
        /// To test the HasAccess method with security id as a parameter
        /// </summary>
        [Test]
        public void TestHasAccessWithSecurityId()
        {   
            Assert.IsNotNull(userData.HasAccess("715"));
        }

        /// <summary>
        /// To test the Reset method 
        /// </summary>
        [Test]
        public void TestReset()
        {
            userData.Reset();
            Assert.AreEqual(0, userData.Facilities.Count);
            //Assert.AreEqual(0, userData.FreeformFacilities.Count);
            Assert.IsFalse(userData.PasswordExpired);
        }

        ///// <summary>
        ///// To test the AddFreeformFacility
        ///// </summary>
        //[Test]
        //public void TestAddFreeformFacility()
        //{
        //    int freeformFacilityCount = userData.FreeformFacilities.Count;
        //    userData.AddFreeformFacility("TestFreeformFacilityName");
        //    Assert.AreEqual(freeformFacilityCount + 1, userData.FreeformFacilities.Count);
        //}

        ///// <summary>
        ///// To test the AddFreeformFacility
        ///// </summary>
        //[Test]
        //public void TestAddFreeformFacilityWithNull()
        //{
        //    int freeformFacilityCount = userData.FreeformFacilities.Count;
        //    userData.AddFreeformFacility(null);
        //    Assert.AreEqual(freeformFacilityCount, userData.FreeformFacilities.Count);
        //}


        /// <summary>
        /// To test the User data set instance method
        /// </summary>
        [Test]
        public void TestSetInstance()
        {
            UserData userdata = UserData.Instance;
            UserData.SetInstance(userdata);
            Assert.AreSame(userdata, UserData.Instance);
            UserData.SetInstance(null);
            Assert.IsNotNull(UserData.Instance);
        }

        /// <summary>
        /// Test case for SSNMasked
        /// </summary>
        [Test]
        public void TestSSNMasked()
        {
            userData.IsSSNMasked = true;
            Assert.IsTrue(userData.IsSSNMasked);
        }

        /// <summary>
        /// Test case for IsLdapEnabled
        /// </summary>
        [Test]
        public void TestIsLdapEnabled()
        {
            userData.IsLdapEnabled = true;
            Assert.IsTrue(userData.IsLdapEnabled);
        }

        /// <summary>
        /// Test case for IsSelfMappingEnabled
        /// </summary>
        [Test]
        public void TestIsSelfMappingEnabled()
        {
            userData.IsSelfMappingEnabled = true;
            Assert.IsTrue(userData.IsSelfMappingEnabled);
        }

        /// <summary>
        /// Test case for domain list
        /// </summary>
        [Test]
        public void TestDomainList()
        {
            UserData.Instance.DomainList.Add("domain1");
            Assert.IsTrue(UserData.Instance.DomainList.Count > 0);
        }

        /// <summary>
        /// Test case for Hpf User Id
        /// </summary>
        [Test]
        public void TestHpfUserId()
        {
            UserData.Instance.HpfUserId = "user";
            Assert.AreEqual("user", UserData.Instance.HpfUserId);
        }

        /// <summary>
        /// Test case for Hpf Password
        /// </summary>
        [Test]
        public void TestHpfPassword()
        {
            UserData.Instance.HpfPassword = "pwd";
            Assert.AreEqual("pwd", UserData.Instance.HpfPassword);
        }

        /// <summary>
        /// test case to New password
        /// </summary>
        [Test]
        public void TestNewPassword()
        {
            string inputNewPassword = "1324";
            userData.NewPassword = inputNewPassword;
            string outputNewPassword = userData.NewPassword;
            Assert.AreEqual(inputNewPassword, outputNewPassword);
        }

        /// <summary>
        /// test case to default facility
        /// </summary>
        [Test]
        public void TestDefaultFacility()
        {
            FacilityDetails inputFacilityDetails = new FacilityDetails();
            userData.DefaultFacility = inputFacilityDetails;
            FacilityDetails outputFacilityDetails = userData.DefaultFacility;
            Assert.IsNotNull(outputFacilityDetails);
        }

        /// <summary>
        /// test case to Invoice Due Days
        /// </summary>
        [Test]
        public void TestInvoiceDueDays()
        {
            ArrayList inputInvoiceDueDays = new ArrayList();
            userData.InvoiceDueDays = inputInvoiceDueDays;
            ArrayList outputInvoiceDueDays = userData.InvoiceDueDays;
            Assert.IsNotNull(outputInvoiceDueDays);
        }
        #endregion

    }
}

