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
using System.IO;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for LetterTemplateComparer
    /// </summary>
    [TestFixture]
    public class TestLetterTemplateComparer
    {
        #region Methods

        /// <summary>
        /// Test case for comparing two media type objects.
        /// </summary>
        [Test]
        public void TestCompare()
        {
            LetterTemplateDetails letterTemplate1 = new LetterTemplateDetails();
            letterTemplate1.IsDefault = true;
            letterTemplate1.LetterType = LetterType.Invoice;
            letterTemplate1.Name = "Test Letter" + GetUniqueId();
            letterTemplate1.Description = "Test Letter";

            LetterTemplateDetails letterTemplate2 = new LetterTemplateDetails();
            letterTemplate2.IsDefault = false;
            letterTemplate2.LetterType = LetterType.CoverLetter;
            letterTemplate2.Name = "Letter" + GetUniqueId();
            letterTemplate2.Description = "Test Letter";

            ListSortDescription listSortDescription = new ListSortDescription(TypeDescriptor.GetProperties(typeof(LetterTemplateDetails))["LetterTypeName"], ListSortDirection.Ascending);
            ListSortDescription[]  listSortDescriptions = new ListSortDescription[] { listSortDescription };
            ListSortDescriptionCollection sorts = new ListSortDescriptionCollection(listSortDescriptions);
            LetterTemplateComparer comparer = new LetterTemplateComparer();
            comparer.SetSortDescriptions(sorts);

            int result = comparer.Compare(letterTemplate1, letterTemplate2);
            Assert.AreEqual(1, result);

            listSortDescription = new ListSortDescription(TypeDescriptor.GetProperties(typeof(LetterTemplateDetails))["Image"], ListSortDirection.Ascending);
            listSortDescriptions = new ListSortDescription[] { listSortDescription };
            sorts = new ListSortDescriptionCollection(listSortDescriptions);            
            comparer = new LetterTemplateComparer();
            comparer.SetSortDescriptions(sorts);
            
            result = comparer.Compare(letterTemplate1, letterTemplate2);
            Assert.AreEqual(-1, result);
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        #endregion
    }
}
