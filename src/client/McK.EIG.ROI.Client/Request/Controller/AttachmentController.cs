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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;
using System.IO;
using System.Text;
using System.Xml;
using System.Security.AccessControl;

using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.Common.FileTransfer.Model;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.Client.Web_References.BillingWS;
using McK.EIG.Common.FileTransfer.Controller.Upload;


namespace McK.EIG.ROI.Client.Request.Controller
{
    /// <summary>
    /// Attachment Level Controller.
    /// </summary>
    public partial class AttachmentController : ROIController
    {
        private Log log = LogFactory.GetLogger(typeof(AttachmentController));

        #region Fields
        private static object syncRoot = new Object();

        //private static variable of BillingController
        private static volatile AttachmentController attachmentController;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the Request Service.
        /// </summary>
        public AttachmentController()
        {
            // reset the logfactory, so that it will continue to log to ROI application log
 	    // CR fix for 347793
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

        }

        #endregion

        #region Service Methods

        /// <summary>
        /// This method will download the file.
        /// </summary>
        /// <param name="attachmentID"></param>
        /// <param name="attachmentExt"></param>
        public string DownloadAttachment(string attachmentId, string attachmentExt, EventHandler progressHandler)
        {
            // contact ROI server to download the attachment
            log.EnterFunction();

            string TempPath = "";
            bool isAppDataWriteAccess = false;

            try
            {
                string addExt = attachmentExt.StartsWith(".") ? attachmentExt : "." + attachmentExt;
                TempPath = Path.Combine(Environment.CurrentDirectory, "temp");
                log.Info("Downloading attachment to temporary location: " + TempPath);

                String filePath = TempPath + "\\" + attachmentId + addExt;

                // make sure you can write to the file path
                isAppDataWriteAccess = this.isWriteAccess(TempPath);
                if (!isAppDataWriteAccess)
                {
                    throw new ROIException("ROI.13.3.16", TempPath);
                }

                Hashtable serverParameter = new Hashtable(3);

                if (UserData.Instance.IsLdapEnabled)
                {
                    serverParameter.Add(ROIConstants.UserName, UserData.Instance.Domain + "\\" + UserData.Instance.DomainUserName + "~" + UserData.Instance.UserId);
                    serverParameter.Add(ROIConstants.Password, UserData.Instance.DomainPassword);
                }
                else
                {
                    serverParameter.Add(ROIConstants.UserName, UserData.Instance.UserId);
                    serverParameter.Add(ROIConstants.Password, UserData.Instance.Password);
                }

                serverParameter.Add("CHUNKENABLED", "true");
                serverParameter.Add("BLOCKSIZE", "10485760");
                serverParameter.Add("SOURCE", "Attachment");
                serverParameter.Add("OWNER_TYPE", "FileDownloader");
                serverParameter.Add("FILE_NAME", attachmentId);
                DownloadContent fileDownloadContent = new DownloadContent();

                log.Info("Downloading attachment content...");
                ROIFileDownloadController.Instance.DownloadFile(fileDownloadContent,
                                                                filePath,
                                                                serverParameter, progressHandler);
                log.Info("Attachment content downloaded successfully.");

                log.ExitFunction();
                return filePath;
            }
            catch (ROIException roiException)
            {
                throw roiException;
            }
            catch (Exception)
            {
                if (!isAppDataWriteAccess)
                    //unable to access temp path folder
                    throw new ROIException("ROI.13.3.16");
                else
                    //unable to retrieve content
                    throw new ROIException("ROI.13.3.17");
            }
            log.Info("Attachment content NULL.");
            log.ExitFunction();
            return (null);
        }

        private bool isWriteAccess(string AppDatadirectoryPath)
        {
            bool isWriteAccess = false; 
            try {
                AuthorizationRuleCollection collection = Directory.GetAccessControl(AppDatadirectoryPath).GetAccessRules(true, true, typeof(System.Security.Principal.NTAccount)); 
                foreach (FileSystemAccessRule rule in collection) 
                { 
                    if (rule.AccessControlType == AccessControlType.Allow) 
                    { 
                        isWriteAccess = true; break; 
                    } 
                } 
            } 
            catch (UnauthorizedAccessException) 
            { isWriteAccess = false; } 
            catch (Exception) 
            { isWriteAccess = false; } 
            return isWriteAccess; 
        }
        #endregion

        #region Map Model

        #endregion

        #region Properties

        /// <summary>
        /// Get the attachmentController Instance
        /// </summary>
        public new static AttachmentController Instance
        {
            get
            {
                if (attachmentController == null)
                {
                    lock (syncRoot)
                    {
                        if (attachmentController == null)
                        {
                            attachmentController = new AttachmentController ();
                        }
                    }
                }
                return attachmentController;
            }
        }

        #endregion

    }
}
