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
    ///  Test class for ImageIdComparer
    /// </summary>
    [TestFixture]
    public class TestImageIdComparer
    {
        #region Methods

        /// <summary>
        /// Test case for comparing two media type objects.
        /// </summary>
        [Test]
        public void TestCompare()
        {
            MediaTypeDetails mediaType1 = new MediaTypeDetails();
            mediaType1.Id = -1;
            mediaType1.Name = "Media Type" + GetUniqueId();
            mediaType1.Description = "Media Type1";

            MediaTypeDetails mediaType2 = new MediaTypeDetails();
            mediaType2.Id = 1;
            mediaType2.Name = "Media Type" + GetUniqueId();
            mediaType2.Description = "Media Type2";

            ListSortDescription listSortDescription = new ListSortDescription(TypeDescriptor.GetProperties(typeof(MediaTypeDetails))["Name"], ListSortDirection.Ascending);
            ListSortDescription[] listSortDescriptions = new ListSortDescription[] { listSortDescription };
            ListSortDescriptionCollection sorts = new ListSortDescriptionCollection(listSortDescriptions);            
            ImageIdComparer comparer = new ImageIdComparer();
            comparer.SetSortDescriptions(sorts);
            comparer.Compare(mediaType1, mediaType2);            
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        #endregion
    }
}
