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
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{

    /// <summary>
    /// Test class for Requestor Adjustment Details
    /// </summary>
    [TestFixture]
    class TestRequestorAdjustmentDetails
    {
        //Holds the object of model class.
        private RequestorAdjustmentDetails requestorAdjustmentDetails;

        [SetUp]
        public void Initialize()
        {
            requestorAdjustmentDetails = new RequestorAdjustmentDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestorAdjustmentDetails = null;
        }

        /// <summary>
        /// Test case for Adj Id.
        /// </summary>
        [Test]
        public void TestAdjId()
        {
            long inputAdjId = 100;
            requestorAdjustmentDetails.AdjId = inputAdjId;
            long outputAdjId = requestorAdjustmentDetails.AdjId;
            Assert.AreEqual(inputAdjId, outputAdjId);
        }

        /// <summary>
        /// Test case for Requestor Seq.
        /// </summary>
        [Test]
        public void TestRequestorSeq()
        {
            long inputRequestorSeq = 100;
            requestorAdjustmentDetails.RequestorSeq = inputRequestorSeq;
            long outputRequestorSeq = requestorAdjustmentDetails.RequestorSeq;
            Assert.AreEqual(inputRequestorSeq, outputRequestorSeq);
        }

        /// <summary>
        /// Test case for Reason.
        /// </summary>
        [Test]
        public void TestReason()
        {
            string inputReason = "Test Reason";
            requestorAdjustmentDetails.Reason = inputReason;
            string outputReason = requestorAdjustmentDetails.Reason;
            Assert.AreEqual(inputReason, outputReason);
        }

        /// <summary>
        /// Test case for Amount.
        /// </summary>
        [Test]
        public void TestAmount()
        {
            double inputAmount = 100;
            requestorAdjustmentDetails.Amount = inputAmount;
            double outputAmount = requestorAdjustmentDetails.Amount;
            Assert.AreEqual(inputAmount, outputAmount);
        }

        /// <summary>
        /// Test case for UnappliedAmount.
        /// </summary>
        [Test]
        public void TestUnappliedAmount()
        {
            double inputUnappliedAmount = 100;
            requestorAdjustmentDetails.UnappliedAmount = inputUnappliedAmount;
            double outputUnappliedAmount = requestorAdjustmentDetails.UnappliedAmount;
            Assert.AreEqual(inputUnappliedAmount, outputUnappliedAmount);
        }

        /// <summary>
        /// Test case for Adjustment Date.
        /// </summary>
        [Test]
        public void TestAdjustmentDate()
        {
            string inputAdjustmentDate = DateTime.Now.ToString();
            requestorAdjustmentDetails.AdjustmentDate = inputAdjustmentDate;
            string outputAdjustmentDate = requestorAdjustmentDetails.AdjustmentDate;
            Assert.AreEqual(inputAdjustmentDate, outputAdjustmentDate);
        }

        
        /// <summary>
        /// Test case for Note.
        /// </summary>
        [Test]
        public void TestNote()
        {
            string inputNote = "Test Note";
            requestorAdjustmentDetails.Note = inputNote;
            string outputNote = requestorAdjustmentDetails.Note;
            Assert.AreEqual(inputNote, outputNote);
        }

        /// <summary>
        /// Test case for Requestor Name.
        /// </summary>
        [Test]
        public void TestRequestorName()
        {
            string inputRequestorName = "Test Requestor Name";
            requestorAdjustmentDetails.RequestorName = inputRequestorName;
            string outputRequestorName = requestorAdjustmentDetails.RequestorName;
            Assert.AreEqual(inputRequestorName, outputRequestorName);
        }

        /// <summary>
        /// Test case for Requestor Type.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            string inputRequestorType = "Test Requestor Type";
            requestorAdjustmentDetails.RequestorType = inputRequestorType;
            string outputRequestorType = requestorAdjustmentDetails.RequestorType;
            Assert.AreEqual(inputRequestorType, outputRequestorType);
        }

        /// <summary>
        /// Test case for Is Remove Adjustment.
        /// </summary>
        [Test]
        public void TestIsRemoveAdjustment()
        {
            bool inputIsRemoveAdjustment = true;
            requestorAdjustmentDetails.IsRemoveAdjustment = inputIsRemoveAdjustment;
            bool outputIsRemoveAdjustment = requestorAdjustmentDetails.IsRemoveAdjustment;
            Assert.AreEqual(inputIsRemoveAdjustment, outputIsRemoveAdjustment);
        }

        /// <summary>
        /// Test case for Adjustment Type.
        /// </summary>
        [Test]
        public void TestAdjustmentType()
        {
            AdjustmentType inputAdjustmentType = new AdjustmentType();
            requestorAdjustmentDetails.AdjustmentType = inputAdjustmentType;
            AdjustmentType outputAdjustmentType = requestorAdjustmentDetails.AdjustmentType;
            Assert.AreEqual(inputAdjustmentType, outputAdjustmentType);
        }

    }
}
