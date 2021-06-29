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
using System.ComponentModel;
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for ConfigureNotesDetails.    
    /// </summary>
    [TestFixture]
    public class TestNotesDetails
    {
        // Holds the object of model class.
        private NotesDetails noteDetails;

        [SetUp]
        public void Initialize()
        {
            noteDetails = new NotesDetails();
        }

        [TearDown]
        public void Dispose()
        {
            noteDetails = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for ConfigureNotes Id.
        /// </summary>
        [Test]
        public void TestConfigureNotesId()
        {
            long inputConfigureNotesId = 100;
            noteDetails.Id = inputConfigureNotesId;
            long outputConfigureNotesId = noteDetails.Id;
            Assert.AreEqual(inputConfigureNotesId, outputConfigureNotesId);
        }

        /// <summary>
        /// Test case for ConfigureNotes Name.
        /// </summary>
        [Test]
        public void TestConfigureNotesName()
        {
            string inputConfigureNotesName = "Sample1";
            noteDetails.Name = inputConfigureNotesName;
            string outputConfigureNotesName = noteDetails.Name;
            Assert.AreEqual(inputConfigureNotesName, outputConfigureNotesName);
        }

        /// <summary>
        /// Test case for ConfigureNotes Desc.
        /// </summary>
        [Test]
        public void TestConfigureNotesDisplayText()
        {
            string inputConfigureNotesDisplayText = "Desc1";
            noteDetails.DisplayText = inputConfigureNotesDisplayText;
            string outputConfigureNotesDisplayText = noteDetails.DisplayText;
            Assert.AreEqual(inputConfigureNotesDisplayText, outputConfigureNotesDisplayText);            
        }

        
        
        /// <summary>
        /// Test case for ConfigureNotes recordversion.
        /// </summary>
        [Test]
        public void TestConfigureNotesRecordVersion()
        {
            int recordVersionId = 0;
            noteDetails.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, noteDetails.RecordVersionId);
        }

        /// <summary>
        /// Test case for ConfigureNotes Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(noteDetails.Equals(noteDetails));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), noteDetails.GetHashCode());
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        #endregion

    }
}
