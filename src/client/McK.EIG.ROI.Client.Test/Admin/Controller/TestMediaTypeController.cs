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
using System.Drawing;

//NUnit
using NUnit.Framework;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test cases for MediaType Controller
    /// </summary>
    [TestFixture]
    [Category("TestMediaTypeController")]
    public class TestMediaTypeController : TestBase
    {
        #region Fields

        private BillingAdminController billingAdminController;
        private long id;
        private string name;
        private string desc;
        private MediaTypeDetails newMediaType;

        #endregion Fields

        #region Constructor

        public TestMediaTypeController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            name = "Microfilm" + TestBiilingAdminController.GetUniqueId();
            desc = "Microfilm";
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

        #region TestMethods

        /// <summary>
        ///  Creates the mediatype
        /// </summary>
        [Test]
        public void Test01CreateMediaType()
        {
            MediaTypeDetails mediaType = new MediaTypeDetails();

            mediaType.Name = name;
            mediaType.Description = desc;
            mediaType.IsAssociated = false;

            id = billingAdminController.CreateMediaType(mediaType);
            mediaType.Id = id;
            newMediaType = mediaType;
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the mediatype for the given mediaTypeId
        /// </summary>
        [Test]
        public void Test02GetMediaType()
        {
            MediaTypeDetails mediaType = billingAdminController.GetMediaType(id);

            Assert.AreEqual(newMediaType.Id, mediaType.Id);
            Assert.AreEqual(newMediaType.Name, mediaType.Name);
            Assert.AreEqual(newMediaType.Description, mediaType.Description);
            Assert.AreEqual(newMediaType.IsAssociated, mediaType.IsAssociated);
        }

        /// <summary>
        ///  Update the mediatype
        /// </summary>
        [Test]
        public void Test03UpdateMediaType()
        {
            MediaTypeDetails mediaType = new MediaTypeDetails();
            mediaType.Id = id;
            mediaType.Name = name;
            mediaType.Description = desc+ " vx";
            mediaType.IsAssociated = false;

            MediaTypeDetails updatedMediaType =  billingAdminController.UpdateMediaType(mediaType);
            Assert.AreNotEqual(mediaType.RecordVersionId, updatedMediaType.RecordVersionId);
        }

        /// <summary>
        ///  Deletes a selected mediatype
        /// </summary>
        [Test]
        public void Test04DeleteMediaType()
        {
            billingAdminController.DeleteMediaType(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Get all the MediaTypes
        /// </summary>
        [Test]
        public void Test05RetrieveAllMediaTypes()
        {
            Collection<MediaTypeDetails> mediaTypes = billingAdminController.RetrieveAllMediaTypes();
            if(mediaTypes.Count > 0)
            {
                MediaTypeDetails mediaType = mediaTypes[0];
                Assert.IsInstanceOfType(typeof(long), mediaType.Id);
                Assert.IsInstanceOfType(typeof(string), mediaType.Name);
                Assert.IsInstanceOfType(typeof(string), mediaType.Description);
                Assert.IsInstanceOfType(typeof(bool), mediaType.IsAssociated);
                Assert.IsInstanceOfType(typeof(Image), mediaType.Image);
            }

            Collection<MediaTypeDetails> mediaTypesList = billingAdminController.RetrieveAllMediaTypes(false);
            if (mediaTypesList.Count > 0)
            {
                MediaTypeDetails mediaType = mediaTypesList[0];
                Assert.IsInstanceOfType(typeof(long), mediaType.Id);
                Assert.IsInstanceOfType(typeof(string), mediaType.Name);
            }
        }

        /// <summary>
        /// Test case for Update Seed mediatype
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06UpdateSeedMediaType()
        {
            Collection<MediaTypeDetails> mediaTypes = null;

            mediaTypes = billingAdminController.RetrieveAllMediaTypes();

            foreach (MediaTypeDetails mediaType in mediaTypes)
            {
                if (mediaType.Id < 0)
                {
                    mediaType.Name = "Electronic - Seed Media type";
                    billingAdminController.UpdateMediaType(mediaType);
                }
            }
        }

        /// <summary>
        /// Test case for get mediatype with non Existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07GetMediaTypeWithNonExistingId()
        {
            long mediaTypeId = 0;
            MediaTypeDetails mediaType = billingAdminController.GetMediaType(mediaTypeId);
        }

        /// <summary>
        ///  Test Create mediatype With Existing Name
        /// </summary>
        //[Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08CreateMediaTypeWithExistingName()
        {
            MediaTypeDetails mediaType = new MediaTypeDetails();

            mediaType.Name = name;
            mediaType.Description = desc;
            mediaType.IsAssociated = false;

            id = billingAdminController.CreateMediaType(mediaType);
        }

        /// <summary>
        /// Test Create mediatype With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09CreateMediaTypeWithValidation()
        {
            MediaTypeDetails mediaType = new MediaTypeDetails();
            string mediaName = name;
            while (mediaName.Length <= 30)
            {
                mediaName += mediaName;
            }
            mediaType.Name = mediaName;

            string mediaDesc = desc;
            while (mediaDesc.Length <= 256)
            {
                mediaDesc += mediaDesc;
            }

            mediaType.Description = mediaDesc;
            billingAdminController.CreateMediaType(mediaType);
        }

        /// <summary>
        ///  Test Create mediatype With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10UpdateMediaTypeWithValidation()
        {
            MediaTypeDetails mediaType = new MediaTypeDetails();
            string mediaName = name;
            while (mediaName.Length <= 30)
            {
                mediaName += mediaName;
            }
            mediaType.Name = mediaName;

            string mediaDesc = desc;
            while (mediaDesc.Length <= 256)
            {
                mediaDesc += mediaDesc;
            }

            mediaType.Description = mediaDesc;
            billingAdminController.UpdateMediaType(mediaType);
        }
        
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test11CreateMediaTypeWithEmptyName()
        {
            MediaTypeDetails mediaType = new MediaTypeDetails();
            mediaType.Name = string.Empty;
            billingAdminController.CreateMediaType(mediaType);
        }

        /// <summary>
        /// test case for delete media type with non existing id
        /// </summary>
        [ExpectedException(typeof(ROIException))]
        [Test]
        public void Test12DeleteNonExistingMediaType()
        {
            billingAdminController.DeleteMediaType(0);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            MediaType serverMediaType = null;
            MediaTypeDetails clientMediaType = BillingAdminController.MapModel(serverMediaType);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            MediaTypeDetails clientMediaType = null;
            MediaType serverMediaType = BillingAdminController.MapModel(clientMediaType);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            MediaType[] mediaTypes = null;
            Collection<MediaTypeDetails> clientMediaTypeList = BillingAdminController.MapModel(mediaTypes);
        }

        #endregion
    }
}
