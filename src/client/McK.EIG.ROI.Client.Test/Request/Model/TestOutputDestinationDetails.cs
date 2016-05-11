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
    public class TestOutputDestinationDetails
    {
        #region Fields

        private OutputDestinationDetails outputDestinationDetails;       

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            outputDestinationDetails = new OutputDestinationDetails();
        }

        [TearDown]
        public void Dispose()
        {
            outputDestinationDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the name.
        /// </summary>
        [Test]
        public void TestName()
        {
            string name = "Canon";
            outputDestinationDetails.Name = name;
            Assert.AreEqual(name, outputDestinationDetails.Name);
        }

        /// <summary>
        /// Test the fax.
        /// </summary>
        [Test]
        public void TestFax()
        {
            string fax = "404.338.5555";
            outputDestinationDetails.Fax = fax;
            Assert.AreEqual(fax, outputDestinationDetails.Fax);
        }

        /// <summary>
        /// Test the media.
        /// </summary>
        [Test]
        public void TestMedia()
        {
            string media = "CD";
            outputDestinationDetails.Media = media;
            Assert.AreEqual(media, outputDestinationDetails.Media);
        }

        /// <summary>
        /// Test the password.
        /// </summary>
        [Test]
        public void TestPassword()
        {
            string password = "Password1234";
            outputDestinationDetails.Password = password;
            Assert.AreEqual(password, outputDestinationDetails.Password);
        }

        /// <summary>
        /// Test case for password required.
        /// </summary>
        [Test]
        public void TestPasswordRequired()
        {   
            outputDestinationDetails.PasswordRequired = true;
            Assert.IsTrue(outputDestinationDetails.PasswordRequired);
        }   
       
        /// <summary>
        /// Test the Status.
        /// </summary>
        [Test]
        public void TestStatus()
        {
            string status = "Ready";
            outputDestinationDetails.Status = status;
            Assert.AreEqual(status, outputDestinationDetails.Status);
        }

        /// <summary>
        /// Test the Type.
        /// </summary>
        [Test]
        public void TestType()
        {
            string type = "Canon";
            outputDestinationDetails.Type = type;
            Assert.AreEqual(type, outputDestinationDetails.Type);
        }

        /// <summary>
        /// Test the Where.
        /// </summary>
        [Test]
        public void TestWhere()
        {
            string where = "111.111.11.11";
            outputDestinationDetails.Where = where;
            Assert.AreEqual(where, outputDestinationDetails.Where);
        }

        /// <summary>
        /// Test the Comment.
        /// </summary>
        [Test]
        public void TestComment()
        {
            string comment = "Comment";
            outputDestinationDetails.Comment = comment;
            Assert.AreEqual(comment, outputDestinationDetails.Comment);
        }

        /// <summary>
        /// Test the Property definitions
        /// </summary>
        [Test]
        public void TestPropertyDefinitions()
        {
            Assert.IsNotNull(outputDestinationDetails.PropertyDefinitions);
        }

        /// <summary>
        /// Test the Id
        /// </summary>
        [Test]
        public void TestId()
        {
            int input = 12;
            outputDestinationDetails.Id = input;
            Assert.AreEqual(input, outputDestinationDetails.Id);
        }

        // <summary>
        /// Test the EmailAddr
        /// </summary>
        [Test]
        public void TestEmailAddr()
        {
            string input = "test@xxx.com";
            outputDestinationDetails.EmailAddr = input;
            Assert.AreEqual(input, outputDestinationDetails.EmailAddr);
        }

        /// <summary>
        /// Test the deviceID
        /// </summary>
        [Test]
        public void TestDeviceID()
        {
            int input = 111;
            outputDestinationDetails.DeviceID = input;
            Assert.AreEqual(input, outputDestinationDetails.DeviceID);
        }

        /// <summary>
        /// Test the discType
        /// </summary>
        [Test]
        public void TestDiscType()
        {
            string input = "DVD";
            outputDestinationDetails.DiscType = input;
            Assert.AreEqual(input, outputDestinationDetails.DiscType);
        }

        /// <summary>
        /// Test the templateName
        /// </summary>
        [Test]
        public void TestTemplateName()
        {
            string input = "Test1";
            outputDestinationDetails.TemplateName = input;
            Assert.AreEqual(input, outputDestinationDetails.TemplateName);
        }

        /// <summary>
        /// Test the output notes
        /// </summary>
        [Test]
        public void TestOutputNotes()
        {
            string input = "Test";
            outputDestinationDetails.OutputNotes = input;
            Assert.AreEqual(input, outputDestinationDetails.OutputNotes);
        }


        #endregion
    }
}
