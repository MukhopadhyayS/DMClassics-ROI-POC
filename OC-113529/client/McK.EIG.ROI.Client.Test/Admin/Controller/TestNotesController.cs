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
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test cases for configure notes Controller
    /// </summary>
    [TestFixture]
    [Category("TestConfigureNotesController")]
    public class TestNotesController : TestBase
    { 
        #region Fields

        private ROIAdminController roiAdminController;
        private long id;
        private string name;
        private string displayText; 
        #endregion

        #region Constructor

        public TestNotesController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            name = "NotesName" + TestBiilingAdminController.GetUniqueId();
            displayText = "displaytext";
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

        #region Test Methods

        /// <summary>
        ///  Creates the configure notes
        /// </summary>
        [Test]
        public void Test01CreateConfigureNotes()
        {
            NotesDetails noteDetails = new NotesDetails();

            noteDetails.Name = name;
            noteDetails.DisplayText = displayText;
            

            id = roiAdminController.CreateConfigureNotes(noteDetails);
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the configure notes for the given notes id
        /// </summary>
        [Test]
        public void Test02GetConfigureNotes()
        {
            NotesDetails noteDetails = roiAdminController.GetNote(id);

            Assert.IsInstanceOfType(typeof(long), noteDetails.Id);
            Assert.IsInstanceOfType(typeof(string), noteDetails.Name);
            Assert.IsInstanceOfType(typeof(string), noteDetails.DisplayText);            
        }

        /// <summary>
        ///  Update the configure notes
        /// </summary>
        [Test]
        public void Test03UpdateConfigureNotes()
        {
            NotesDetails noteDetails = new NotesDetails();
            noteDetails.Id = id;
            noteDetails.Name = name;
            noteDetails.DisplayText = displayText+ " vx";
            NotesDetails updatedConfigureNotes = roiAdminController.UpdateConfigureNotes(noteDetails);
            Assert.AreEqual(noteDetails.Id, updatedConfigureNotes.Id);
        }
        /// <summary>
        /// Get all the ConfigureNotess
        /// </summary>
        [Test]
        public void Test04RetrieveAllConfigureNotess()
        {
            Collection<NotesDetails> noteDetails = roiAdminController.RetrieveAllConfigureNotes();

            if (noteDetails.Count > 0)
            {
                NotesDetails noteDetail = noteDetails[0];
                Assert.IsInstanceOfType(typeof(long), noteDetail.Id);
                Assert.IsInstanceOfType(typeof(string), noteDetail.Name);
                Assert.IsInstanceOfType(typeof(string), noteDetail.DisplayText);
            }
        }

        /// <summary>
        ///  Deletes a selected configure notes
        /// </summary>
        [Test]
        public void Test05DeleteConfigureNotes()
        {
            roiAdminController.DeleteNote(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for get configure notes with non Existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06GetConfigureNotesWithNonExistingId()
        {
            long noteDetailsId = 0;
            NotesDetails noteDetails = roiAdminController.GetNote(noteDetailsId);
        }

        /// <summary>
        ///  Test Create configure notes With Existing Name
        /// </summary>
        //[Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07CreateConfigureNotesWithExistingName()
        {
            NotesDetails noteDetails = new NotesDetails();

            noteDetails.Name = name;
            noteDetails.DisplayText = displayText;

            id = roiAdminController.CreateConfigureNotes(noteDetails);
        }

        /// <summary>
        /// Test Create configure notes With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08CreateConfigureNotesWithValidation()
        {
            NotesDetails noteDetails = new NotesDetails();
            string noteName = name;
            while (noteName.Length <= 30)
            {
                noteName += noteName;
            }
            noteDetails.Name = noteName;

            string displaytext = displayText;
            while (displaytext.Length <= 256)
            {
                displaytext += displayText;
            }

            noteDetails.DisplayText = displaytext;
            roiAdminController.CreateConfigureNotes(noteDetails);
        }

        /// <summary>
        ///  Test Create configure notes With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09UpdateConfigureNotesWithValidation()
        {
            NotesDetails noteDetails = new NotesDetails();
            string noteName = name;
            while (noteName.Length <= 30)
            {
                noteName += noteName;
            }
            noteDetails.Name = noteName;

            string noteText =  displayText;
            while (noteText.Length <= 256)
            {
                noteText += noteText;
            }

            noteDetails.DisplayText = noteText;
            roiAdminController.UpdateConfigureNotes(noteDetails);
        }

        /// <summary>
        /// test case for delete configure note with non existing id
        /// </summary>
        [ExpectedException(typeof(ROIException))]
        [Test]
        public void Test10DeleteNonExistingConfigureNotes()
        {
            roiAdminController.DeleteNote(0);
        }

        /// <summary>
        /// test case for create configure note with invalid input 
        /// </summary>
        [ExpectedException(typeof(ROIException))]
        [Test]
        public void Test11CreateNotesWithEmptyValue()
        {
            NotesDetails notesDetails = new NotesDetails();
            notesDetails.Id = 102;
            notesDetails.Name = "";
            notesDetails.DisplayText = "";
            roiAdminController.CreateConfigureNotes(notesDetails);   
        }

        /// <summary>
        /// test case for create configure note with invalid input 
        /// </summary>
        [ExpectedException(typeof(ROIException))]
        [Test]
        public void Test12CreateNotesWithInvalidInput()
        {
            NotesDetails notesDetails = new NotesDetails();
            notesDetails.Id = 101;
            notesDetails.Name = "1234567890123456789012345678901234567890";
            for(int i = 0; i <= 1001; i++) 
            {
                notesDetails.DisplayText += i.ToString();
            }
            
            roiAdminController.CreateConfigureNotes(notesDetails);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            Note[] noteDetails = null;
            Collection<NotesDetails> clientConfigureNotesList = ROIAdminController.MapModel(noteDetails);
        }

        #endregion
    }
}
