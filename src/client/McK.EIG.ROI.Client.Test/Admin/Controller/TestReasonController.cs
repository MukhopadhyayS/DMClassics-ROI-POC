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
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    [TestFixture]
    public class TestReasonController : TestBase
    {
        #region Fields

        private ROIAdminController roiAdminController;
        private ReasonDetails adjustmentDetails;
        private ReasonDetails requestDetails;
        private ReasonDetails requestStatusDetails;

        private long id;

        #endregion

        #region Constructor

        public TestReasonController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            requestDetails = new ReasonDetails();
            requestDetails.Name = "Hospital Request" + TestBiilingAdminController.GetUniqueId();
            requestDetails.DisplayText = "Hospital Requests" + TestBiilingAdminController.GetUniqueId();
            requestDetails.Type = ReasonType.Request;
            requestDetails.Attribute = RequestAttr.Tpo;

            adjustmentDetails = new ReasonDetails();
            adjustmentDetails.Name = "Billing Mistake" + TestBiilingAdminController.GetUniqueId();
            adjustmentDetails.DisplayText = "Billing Mistake" + TestBiilingAdminController.GetUniqueId();
            adjustmentDetails.Type = ReasonType.Adjustment;

            requestStatusDetails = new ReasonDetails();

            requestStatusDetails.RequestStatus = RequestStatus.Denied;
            requestStatusDetails.Name = "Revocation" + TestBiilingAdminController.GetUniqueId();
            requestStatusDetails.DisplayText = "Revocation" + TestBiilingAdminController.GetUniqueId();
            requestStatusDetails.Type = ReasonType.Status;
        }

        #endregion

        #region Nunit

        [SetUp]
        public void Init()
        {
            roiAdminController = ROIAdminController.Instance;
        }

        [TearDown]
        public void TearDown()
        {
            roiAdminController = null;
        }

        #endregion

        #region Test Methods

        #region Request Reason
        /// <summary>
        /// Test case for Create Reasons.
        /// </summary>
        [Test]
        public void Test01CreateRequestReasons()
        {
            ReasonDetails reasonDetails = new ReasonDetails();

            reasonDetails.Type = ReasonType.Request;
            reasonDetails.Name = requestDetails.Name;
            reasonDetails.Attribute = RequestAttr.Tpo;
            reasonDetails.DisplayText = requestDetails.DisplayText;

            id = roiAdminController.CreateReason(reasonDetails);
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the Reason for the given id.
        /// </summary>
        [Test]
        public void Test02GetRequestReasons()
        {
            requestDetails = roiAdminController.GetReason(id);

            Assert.IsInstanceOfType(typeof(ReasonType), requestDetails.Type);
            Assert.IsInstanceOfType(typeof(long), requestDetails.Id);
            Assert.IsInstanceOfType(typeof(string), requestDetails.DisplayText);
            Assert.IsInstanceOfType(typeof(RequestAttr), requestDetails.Attribute);
        }

        /// <summary>
        /// Test case for Update Reasons.
        /// </summary>
        [Test]
        public void Test03UpdateRequestReasons()
        {
            requestDetails.DisplayText = requestDetails.DisplayText + "test";
            requestDetails.Attribute = RequestAttr.Tpo;
            ReasonDetails updateReasons = roiAdminController.UpdateReason(requestDetails);
            Assert.AreNotEqual(requestDetails.RecordVersionId, updateReasons.RecordVersionId);
            requestDetails = updateReasons;
        }

        /// <summary>
        /// Test case for Update Reasons.
        /// </summary>
        [Test]
        public void Test04UpdateRequestReasons()
        {
            requestDetails.DisplayText = requestDetails.DisplayText;
            requestDetails.Attribute = RequestAttr.NonTpo;
            ReasonDetails updateReasons = roiAdminController.UpdateReason(requestDetails);
            Assert.AreNotEqual(requestDetails.RecordVersionId, updateReasons.RecordVersionId);
        }

        /// <summary>
        /// Retrieve all reasons.
        /// </summary>
        [Test]
        public void Test05RetrieveAllRequestReasons()
        {
            Collection<ReasonDetails> reasonDetails = roiAdminController.RetrieveAllReasons(ReasonType.Request);
            if (reasonDetails.Count > 0)
            {
                ReasonDetails reasons = reasonDetails[0];
                Assert.IsInstanceOfType(typeof(long), reasons.Id);
                Assert.IsInstanceOfType(typeof(Enum), reasons.Attribute);
                Assert.IsInstanceOfType(typeof(string), reasons.DisplayText);
            }
        }

        /// <summary>
        /// Test case for get reasons with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06GetRequestReasonsWithNonExistingId()
        {
            long reasonId = 0;
            ReasonDetails reasons = roiAdminController.GetReason(reasonId);
        }

        /// <summary>
        /// Test Case for create reasons with existing name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07CreateRequestReasonsWithExistingName()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Type = requestDetails.Type;
            reasonDetails.Name = requestDetails.Name;
            reasonDetails.Attribute = RequestAttr.NonTpo;
            reasonDetails.DisplayText = requestDetails.DisplayText;

            id = roiAdminController.CreateReason(reasonDetails);
        }

        /// <summary>
        /// Test case for create reasons with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08CreateRequestReasonsWithEmptyName()
        {
            ReasonDetails reasons = new ReasonDetails();
            reasons.Name = string.Empty;
            reasons.Type = requestDetails.Type;
            reasons.DisplayText = requestDetails.DisplayText + "test";
            reasons.Attribute = RequestAttr.Tpo;
            roiAdminController.CreateReason(reasons);
        }

        /// <summary>
        /// Test case for delete reasons with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09DeleteRequestNonExistingReasons()
        {
            roiAdminController.DeleteReason(0);
        }

        /// <summary>
        /// Deletes the selected reason.
        /// </summary>
        [Test]
        public void Test10DeleteRequestReasons()
        {
            roiAdminController.DeleteReason(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for validation on request reason display text length
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test34RequestReasonsWithMaxDisplayText()
        {
            ReasonDetails reasons = new ReasonDetails();
            reasons.Name = "ReasonName";
            reasons.Type = requestDetails.Type;
            string DisplayText = "Attorney requests patient documents";

            do
                DisplayText += DisplayText;
            while (DisplayText.Length < 250);

            reasons.DisplayText = DisplayText;
            reasons.Attribute = RequestAttr.Tpo;
            roiAdminController.CreateReason(reasons);
        }

        #endregion

        #region Adjustment Reason

        /// <summary>
        /// Test case for Create Reasons.
        /// </summary>
        [Test]
        public void Test11CreateAdjustmentReasons()
        {
            id = roiAdminController.CreateReason(adjustmentDetails);
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the Reason for the given id.
        /// </summary>
        [Test]
        public void Test12GetAdjustmentReasons()
        {
            adjustmentDetails = roiAdminController.GetReason(id);

            Assert.IsInstanceOfType(typeof(ReasonType), adjustmentDetails.Type);
            Assert.IsInstanceOfType(typeof(long), adjustmentDetails.Id);
            Assert.IsInstanceOfType(typeof(string), adjustmentDetails.DisplayText);
        }

        /// <summary>
        /// Test case for Update Reasons.
        /// </summary>
        [Test]
        public void Test13UpdateAdjustmentReasons()
        {
            adjustmentDetails.DisplayText = adjustmentDetails.DisplayText + "test";
            ReasonDetails updateReasons = roiAdminController.UpdateReason(adjustmentDetails);
            Assert.AreNotEqual(adjustmentDetails.RecordVersionId, updateReasons.RecordVersionId);
        }

        /// <summary>
        /// Retrieve all reasons.
        /// </summary>
        [Test]
        public void Test14RetrieveAllAdjustmentReasons()
        {
            Collection<ReasonDetails> reasonsDetails = roiAdminController.RetrieveAllReasons(ReasonType.Adjustment);
            if (reasonsDetails.Count > 0)
            {
                ReasonDetails reasons = reasonsDetails[0];
                Assert.IsInstanceOfType(typeof(long), reasons.Id);
                Assert.IsInstanceOfType(typeof(string), reasons.DisplayText);
            }
        }

        /// <summary>
        /// Test case for get reasons with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test15GetAdjustmentReasonsWithNonExistingId()
        {
            long reasonId = 0;
            ReasonDetails reasons = roiAdminController.GetReason(reasonId);
        }

        /// <summary>
        /// Test Case for create reasons with existing name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test16CreateAdjustmentReasonsWithExistingName()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Name = adjustmentDetails.Name;
            reasonDetails.DisplayText = adjustmentDetails.DisplayText + TestBiilingAdminController.GetUniqueId();
            reasonDetails.Type = ReasonType.Adjustment;

            id = roiAdminController.CreateReason(reasonDetails);
        }

        /// <summary>
        /// Test Case for create reasons with existing display test.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test17CreateAdjustmentReasonsWithExistingDisplayText()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Name = adjustmentDetails.Name + TestBiilingAdminController.GetUniqueId();
            reasonDetails.DisplayText = adjustmentDetails.DisplayText;
            reasonDetails.Type = ReasonType.Adjustment;

            id = roiAdminController.CreateReason(reasonDetails);
        }

        /// <summary>
        /// Test case for create reasons with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test18CreateAdjustmentReasonsWithEmptyName()
        {
            ReasonDetails reasons = new ReasonDetails();
            reasons.Name = string.Empty;
            reasons.DisplayText = adjustmentDetails.DisplayText;
            reasons.Type = ReasonType.Adjustment;
            roiAdminController.CreateReason(reasons);
        }

        /// <summary>
        /// Test case for create reasons with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test19CreateAdjustmentReasonsWithEmptyDisplayText()
        {
            ReasonDetails reasons = new ReasonDetails();
            reasons.Name = adjustmentDetails.Name;
            reasons.DisplayText = string.Empty;
            reasons.Type = ReasonType.Adjustment;
            roiAdminController.CreateReason(reasons);
        }

        /// <summary>
        /// Deletes the selected reason.
        /// </summary>
        [Test]
        public void Test20DeleteAdjustmentReasons()
        {
            roiAdminController.DeleteReason(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for validation on adjustment reason display text length
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test21AdjustmentReasonsWithMaxDisplayText()
        {
            ReasonDetails reasons = new ReasonDetails();
            reasons.Name = adjustmentDetails.Name;
            reasons.Type = ReasonType.Adjustment;
            string DisplayText = "Attorney requests patient documents";

            do
                DisplayText += DisplayText;
            while (DisplayText.Length < 250);

            reasons.DisplayText = DisplayText;
            roiAdminController.CreateReason(reasons);
        }

        #endregion

        #region Request Status Reason

        /// <summary>
        /// Test case for Create Request Status Reasons.
        /// </summary>
        [Test]
        public void Test21CreateStatusReasons()
        {
            id = roiAdminController.CreateReason(requestStatusDetails);
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the Request Status Reason for the given id.
        /// </summary>
        [Test]
        public void Test22GetStatusReasons()
        {
            requestStatusDetails = roiAdminController.GetReason(id);

            Assert.IsInstanceOfType(typeof(ReasonType), requestStatusDetails.Type);
            Assert.IsInstanceOfType(typeof(long), requestStatusDetails.Id);
            Assert.IsInstanceOfType(typeof(string), requestStatusDetails.DisplayText);
            Assert.IsInstanceOfType(typeof(string), requestStatusDetails.Name);
            Assert.IsInstanceOfType(typeof(Enum), requestStatusDetails.RequestStatus);
        }

        /// <summary>
        /// Test case for Update Request Status Reasons.
        /// </summary>
        [Test]
        public void Test23UpdateStatusReasons()
        {
            requestStatusDetails.DisplayText = requestStatusDetails.DisplayText + "test";
            ReasonDetails updateReasons = roiAdminController.UpdateReason(requestStatusDetails);
            Assert.AreNotEqual(requestStatusDetails.RecordVersionId, updateReasons.RecordVersionId);
        }

        /// <summary>
        /// Retrieve all reasons.
        /// </summary>
        [Test]
        public void Test24RetrieveAllStatusReasons()
        {
            Collection<ReasonDetails> reasonDetails = roiAdminController.RetrieveAllReasons(ReasonType.Status);
            if (reasonDetails.Count > 0)
            {
                ReasonDetails reasons = reasonDetails[0];
                Assert.IsInstanceOfType(typeof(long), reasons.Id);
                Assert.IsInstanceOfType(typeof(string), reasons.DisplayText);
                Assert.IsInstanceOfType(typeof(string), reasons.Name);
                Assert.IsInstanceOfType(typeof(Enum), reasons.RequestStatus);
            }
        }

        /// <summary>
        /// Test case for get status reasons with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test25GetStatusReasonsWithNonExistingId()
        {
            long reasonId = 0;
            ReasonDetails reasons = roiAdminController.GetReason(reasonId);
        }

        /// <summary>
        /// Test Case for create status reasons with existing name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test26CreateStatusReasonsWithExistingName()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Name = requestStatusDetails.Name;
            reasonDetails.DisplayText = requestStatusDetails.DisplayText + TestBiilingAdminController.GetUniqueId();
            reasonDetails.RequestStatus = requestDetails.RequestStatus;
            reasonDetails.Type = ReasonType.Status;

            id = roiAdminController.CreateReason(reasonDetails);
        }

        /// <summary>
        /// Test Case for create status reasons with existing display test.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test27CreateStatusReasonsWithExistingDisplayText()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Name = requestStatusDetails.Name + TestBiilingAdminController.GetUniqueId();
            reasonDetails.DisplayText = requestStatusDetails.DisplayText;
            reasonDetails.RequestStatus = requestDetails.RequestStatus;
            reasonDetails.Type = ReasonType.Status;

            id = roiAdminController.CreateReason(reasonDetails);
        }

        /// <summary>
        /// Test Case for create status reasons with existing Request Status.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test28CreateStatusReasonsWithExistingRequestStatus()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Name = requestStatusDetails.Name + TestBiilingAdminController.GetUniqueId();
            reasonDetails.DisplayText = requestStatusDetails.DisplayText + TestBiilingAdminController.GetUniqueId();
            reasonDetails.RequestStatus = RequestStatus.None;
            reasonDetails.Type = ReasonType.Status;

            id = roiAdminController.CreateReason(reasonDetails);
        }

        /// <summary>
        /// Test case for create status reasons with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test29CreateStatusReasonsWithEmptyName()
        {
            ReasonDetails reasons = new ReasonDetails();

            reasons.Name = string.Empty;
            reasons.DisplayText = requestStatusDetails.DisplayText;
            reasons.RequestStatus = RequestStatus.Canceled;
            reasons.Type = ReasonType.Status;
            roiAdminController.CreateReason(reasons);
        }

        /// <summary>
        /// Test case for create status reasons with empty display test.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test30CreateStatusReasonsWithEmptyDisplayText()
        {
            ReasonDetails reasons = new ReasonDetails();

            reasons.Name = requestStatusDetails.Name;
            reasons.DisplayText = string.Empty;
            reasons.RequestStatus = RequestStatus.Completed;
            reasons.Type = ReasonType.Status;
            roiAdminController.CreateReason(reasons);
        }

        /// <summary>
        /// Test case for create status reasons with empty request status.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test31CreateStatusReasonsWithEmptyRequestStatus()
        {
            ReasonDetails reasons = new ReasonDetails();

            reasons.Name = requestStatusDetails.Name;
            reasons.DisplayText = requestStatusDetails.DisplayText;
            reasons.RequestStatus = RequestStatus.None;
            reasons.Type = ReasonType.Status;
            roiAdminController.CreateReason(reasons);
        }
        [Test]
        public void Test32RetrieveReasonsByStatus()
        {
            string [] status = roiAdminController.RetrieveReasonsByStatus(Convert.ToInt32(RequestStatus.Denied));
            Assert.AreNotEqual(0, status.Length);
        }

        /// <summary>
        /// Deletes the selected status reason.
        /// </summary>
        [Test]
        public void Test33DeleteStatusReasons()
        {
            roiAdminController.DeleteReason(id);
            Assert.IsTrue(true);
        }

        #endregion

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            Reason[] serverReasons = null;
            Collection<ReasonDetails> clientReasonList = ROIAdminController.MapModel(serverReasons);
        }

        /// <summary>
        ///  Test Reason Name With maximum character
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestReasonWithValidation()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            string reasonName = adjustmentDetails.Name;
            while (reasonName.Length <= 256)
            {
                reasonName += reasonName;
            }
            reasonDetails.Name = reasonName;

            string reasonDisplayText = adjustmentDetails.DisplayText;
            while (reasonDisplayText.Length <= 40)
            {
                reasonDisplayText += reasonDisplayText;
            }

            reasonDetails.DisplayText = reasonDisplayText;
            roiAdminController.UpdateReason(reasonDetails);
        }

        /// <summary>
        ///  Test Reason Status Field validation
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestReasonStatusValidation()
        {
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Name = "Wrong Patient";
            reasonDetails.DisplayText = "Wrong Patient Requested";
            reasonDetails.Type = ReasonType.Status;
            reasonDetails.RequestStatus = RequestStatus.None;
            roiAdminController.UpdateReason(reasonDetails);
        }       
        # endregion
    }
}
