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
using McK.EIG.ROI.Client.Base.Model;
namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for ROISecurityRights
    /// </summary>
    [TestFixture]
    class TestROISecurityRights
    {
        private ROISecurityRights roiSecurityRights;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            roiSecurityRights = new ROISecurityRights();
        }

        /// <summary>
        /// Dispose roiErrorCodes.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            roiSecurityRights = null;
        }
    }
}
