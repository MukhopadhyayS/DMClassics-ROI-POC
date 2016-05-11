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

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Web_References.ConfigurationWS;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;
using McK.EIG.ROI.Client.Web_References.CcdConversionServiceWS;
using McK.EIG.ROI.Client.WebReferences.ConfigureDaysWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// ROIAdminController.
    /// </summary>
    public partial class ROIAdminController : ROIController
    {
        #region Fields
        
        private static volatile ROIAdminController roiAdminController;

        private ROIAdminServiceWse roiAdminService;        
        private ConfigurationServiceWse configurationService;
        private CcdConversionServiceWse ccdconversionService;
        private ConfigureDaysServiceWse configureDaysService;

        private static object syncRoot = new Object();
        private const string logMethodName = "Calling Webservice: {0}, Method:{1}";
        private const string logParams = "Parameters:";

        #endregion

        #region Constructor

        /// <summary>
        /// ROIAdminController Constructor
        /// </summary>
        private ROIAdminController()
        {
            roiAdminService = new ROIAdminServiceWse();            
            configurationService = new ConfigurationServiceWse();
            ccdconversionService = new CcdConversionServiceWse();
            configureDaysService = new ConfigureDaysServiceWse();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Get the ROIAdminController Instance
        /// </summary>
        public new static ROIAdminController Instance
        {
            get
            {
                lock (syncRoot)
                {
                    if (roiAdminController == null)
                    {
                        roiAdminController = new ROIAdminController();
                    }
                }
                return roiAdminController;
            }
        }

        #endregion
    }
}
