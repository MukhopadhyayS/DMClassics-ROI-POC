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
using System.Collections;
using System.Configuration;
using System.IO;
using System.Net;
using System.Web;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.Common.FileTransfer.Controller.Download;
using McK.EIG.Common.FileTransfer.Model;
using McK.EIG.Common.FileTransfer.Services;
using McK.EIG.Common.Utility.WebServices;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// To Download the file from server.
    /// </summary>
    public class ROIFileDownloadController : FileDownloadController<DefaultFileCache, DownloadContent>
    {
        #region Fields

        private static object syncRoot = new Object();
        private static volatile ROIFileDownloadController roiFileDownloadController;        
       
        #endregion

        #region Constructor

        public ROIFileDownloadController()
            : base(McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("ROI", ConfigurationManager.AppSettings["FileDownloadServletUrl"]))
        {
            FileCache.CacheEnabled = false;
        }

        #endregion

        #region Methods
     
        /// <summary>
        /// Method download the file in the specified file path.
        /// </summary>
        /// <param name="item">Icontent</param>
        /// <param name="filePath">File Path to save the file.</param>
        /// <param name="serverParams">Server Params</param>
        public void DownloadFile(IContent content, string filePath, Hashtable serverParams, EventHandler progressHandler)
        {
            FileStream fileStream = null;
            try
            {
                /// Can't double check the filepath here because sometimes the path will be what the user selected from the local computer vs the temp folder of the applications
               if (Validator.Validate(filePath, ROIConstants.FilepathValidation) )
                {
                    string directoryName = Path.GetDirectoryName(filePath);
                    if (Directory.Exists(directoryName))
                    {
                        try {
                            System.GC.Collect();
                            System.GC.WaitForPendingFinalizers();
                            File.Delete(filePath);
                        }
                        catch (Exception ex) {

                        }
                    }
                }

                fileStream = new FileStream(filePath, FileMode.CreateNew, FileAccess.Write);

                DownloadFile(content,
                            new BinaryWriter(fileStream),
                            serverParams,
                            (CookieContainer)ApplicationSession.Instance.GetSessionValue(ROIWebServiceBase.COOKIE_CONTAINER_KEY),progressHandler);

              
            }
            catch (Exception cause)
            {
                throw new ROIException(ROIErrorCodes.Unknown, cause);
            }
            finally
            {
                if (fileStream != null)
                {
                    fileStream.Close();
                }
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Get the ROIController Instance
        /// </summary>
        public static ROIFileDownloadController Instance
        {
            get
            {
                lock (syncRoot)
                {
                    if (roiFileDownloadController == null)
                        roiFileDownloadController = new ROIFileDownloadController();
                }
                return roiFileDownloadController;
            }
        }

        #endregion
    }
}
