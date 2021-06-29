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
using System.Configuration;
using System.Collections;
using System.Globalization;
using System.Net;

using McK.EIG.Common.FileTransfer.Controller.Download;
using McK.EIG.Common.FileTransfer.Services;
using McK.EIG.Common.Reports.Controller;
using McK.EIG.Common.Utility.WebServices;
using McK.EIG.Common.Utility.WebServices.Encryption;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Reports.Controller
{
    /// <summary>
    /// ROIReportController
    /// </summary>
    public partial class ROIReportController : ReportController
    {

        #region Fields

        #endregion

        #region Constructor

        public ROIReportController() : this(McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("ROI", ConfigurationManager.AppSettings["ReportServletURL"])) { }

        public ROIReportController(string resourceLocator) : base(resourceLocator) { }

        #endregion


        #region Methods
        
        /// <summary>
        /// Generates reports.
        /// </summary>
        /// <param name="fileId"></param>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public new string GenerateReport(string fileId, Hashtable parameters)
        {

            ReportValidator validator = new ReportValidator();
            if (!validator.Validate(parameters))
            {
                throw validator.ClientException;
            }

            if (UserData.Instance.IsLdapEnabled)
            {
                parameters.Add(ROIConstants.User, UserData.Instance.Domain + "\\" + UserData.Instance.DomainUserName + "~" + UserData.Instance.UserId);
                parameters.Add(ROIConstants.SecretWord, UserData.Instance.DomainSecretWord);
            }
            else
            {
                parameters.Add(ROIConstants.User, UserName);
                parameters.Add(ROIConstants.SecretWord, Password);
            }
         
            parameters.Add(ROIConstants.Ticket, Ticket);
            parameters.Add(FileDownloader.PARAMETER_FILE_ID, fileId);
            parameters.Add(FileDownloader.PARAMETER_TIMESTAMP, TimeStamp);
            parameters.Add(FileDownloader.PARAMETER_REVISION, string.Empty);
            parameters.Add(FileDownloader.PARAMETER_BLOCKSIZE, Convert.ToString(BlockSize, System.Threading.Thread.CurrentThread.CurrentUICulture));
            parameters.Add(ROIConstants.CacheDirectory, CacheDirectory);
            parameters.Add(ROIConstants.ChunkEnabled, ConfigurationManager.AppSettings["ChunkEnabled"]);
            parameters.Add(ROIConstants.UserName, UserName);
            parameters.Add(ROIConstants.AppId, ROIConstants.ROIDomainSource);
            string filePath = base.GenerateReport(fileId, parameters, (CookieContainer)McK.EIG.ROI.Client.Base.Controller.ApplicationSession.Instance.GetSessionValue(ROIWebServiceBase.COOKIE_CONTAINER_KEY));
            return filePath;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns User name
        /// </summary>
        protected override string UserName
        {
            get { return UserData.Instance.UserId; }
        }

        /// <summary>
        /// Returns password.
        /// </summary>
        protected override string Password
        {
            get { return UserData.Instance.SecretWord; }
        }

        /// <summary>
        /// Returns block size.
        /// </summary>
        protected override long BlockSize
        {
            get { return Convert.ToInt64(ConfigurationManager.AppSettings["BlockSize"], System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }

        /// <summary>
        /// Returns ticket
        /// </summary>
        protected override string Ticket
        {
            get { return null ; }
        }

        /// <summary>
        /// Returns cache directory
        /// </summary>
        protected override string CacheDirectory
        {
            get { return Convert.ToString(ConfigurationManager.AppSettings["CacheFolder"], System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }

        /// <summary>
        /// Returns Time stamp
        /// </summary>
        protected override string TimeStamp
        {
            get { return CipherData.CreateRawTimeStamp(DateTime.Now).ToString(); }
        }

        private static object syncRoot = new Object();
        private static volatile ROIReportController reportController;

        /// <summary>
        /// Get the ROIReportController Instance
        /// </summary>
        public static ROIReportController Instance
        {
            get
            {
                if (reportController == null)
                {
                    lock (syncRoot)
                    {
                        if (reportController == null)
                        {
                            reportController = new ROIReportController();
                            reportController.Init();
                        }
                    }
                }
                return reportController;
            }
        }

        #endregion


    }
}
