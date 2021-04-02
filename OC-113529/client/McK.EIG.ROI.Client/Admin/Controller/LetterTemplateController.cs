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
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;
using System.Web;

using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.FileTransfer.Model;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {

        #region Fields
        
        private const string IsAssociated_Tag        = "is-associated";
        private const string RecordVersionId_Tag     = "record-version";
        private const string HasNotesTag             = "has_notes";
        public const string LetterTemplateDocumentId = "DOC_ID";
        public const string LetterTemplateFileName   = "FILE_NAME";

        private Collection<LetterTemplateDetails> letterTemplates;
        
        #endregion

        #region Methods

        #region Service Methods
        /// <summary>
        /// This method will create a new letterTemplate.
        /// </summary>
        /// <param name="letterTemplateDetails"></param>
        /// <returns></returns>
        public LetterTemplateDetails CreateLetterTemplate(LetterTemplateDetails letterTemplateDetails, EventHandler progressHandler)
        {
            letterTemplateDetails.Normalize();
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateCreate(letterTemplateDetails))
            {
                throw validator.ClientException;
            }
            try
            {
                letterTemplateDetails = UploadFile(letterTemplateDetails, progressHandler);
            }
            catch (System.Net.WebException cause)
            {   
                throw new ROIException(ROIErrorCodes.LetterTemplateUploadFailed, cause);
            }          
            LetterTemplate serverLetterTemplate = MapModel(letterTemplateDetails);
            object[] requestParams = new object[] { serverLetterTemplate };
            object response = ROIHelper.Invoke(roiAdminService, "createLetterTemplate", requestParams);
            long letterTemplateId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);
            letterTemplateDetails.Id = letterTemplateId;
            letterTemplates = null;
            return letterTemplateDetails;
        }
        
        /// <summary>
        /// This method will update an existing letterTemplate.
        /// </summary>
        /// <param name="letterTemplateDetails"></param>
        public LetterTemplateDetails UpdateLetterTemplate(LetterTemplateDetails letterTemplateDetails, EventHandler progressHandler)
        {
            letterTemplateDetails.Normalize();
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdate(letterTemplateDetails))
            {
                throw validator.ClientException;
            }
            if (letterTemplateDetails.DoUpload)
            {
                try
                {
                    letterTemplateDetails = UploadFile(letterTemplateDetails, progressHandler);
                }
                catch (System.Net.WebException cause)
                {
                    throw new ROIException(ROIErrorCodes.LetterTemplateUploadFailed, cause);
                }                
            }
            LetterTemplate serverLetterTemplate = MapModel(letterTemplateDetails);
            object[] requestParams = new object[] { serverLetterTemplate };

            ROIHelper.Invoke(roiAdminService, "updateLetterTemplate", requestParams);
            LetterTemplateDetails uploadTemplateDetails = MapModel((LetterTemplate)requestParams[0]);
            uploadTemplateDetails.IsUploadSuccess = letterTemplateDetails.IsUploadSuccess;
            letterTemplates = null;
            return uploadTemplateDetails;
        }

        /// <summary>
        /// The method will upload the file.
        /// </summary>
        /// <param name="letterTemplateDetails"></param>
        /// <param name="progressHandler"></param>
        private static LetterTemplateDetails UploadFile(LetterTemplateDetails letterTemplateDetails, EventHandler progressHandler)
        {
            string serveletPath = PrepareServeletUrl(letterTemplateDetails);

            FileUploadHandler uploadHandler = new FileUploadHandler(serveletPath);            
            uploadHandler.ChunkEnabled = Convert.ToBoolean(ConfigurationManager.AppSettings["ChunkEnabled"], System.Threading.Thread.CurrentThread.CurrentCulture);            
            uploadHandler.FileTransferEvent += progressHandler; 

            letterTemplateDetails.IsUploadSuccess = uploadHandler.FileUpload(letterTemplateDetails.FilePath);

            if (letterTemplateDetails.IsUploadSuccess)
            {
                string[] documentId = uploadHandler.GetCheckInId().Split(ROIConstants.StatusReasonDelimiter.ToCharArray(), StringSplitOptions.RemoveEmptyEntries);
                               
                letterTemplateDetails.DocumentId = Convert.ToInt64(documentId[0], System.Threading.Thread.CurrentThread.CurrentCulture);
                letterTemplateDetails.HasNotes = Convert.ToBoolean(documentId[1], System.Threading.Thread.CurrentThread.CurrentCulture);   
                letterTemplateDetails.DoUpload = false;
            }
            uploadHandler.FileTransferEvent -= progressHandler;
            return letterTemplateDetails;
        }
 
        /// <summary>
        /// Create the Url to upload the LetterTemplate file.
        /// </summary>
        /// <param name="letterTemplateDetails"></param>
        /// <returns></returns>
        private static string PrepareServeletUrl(LetterTemplateDetails letterTemplateDetails)
        {
            string serveletPath = McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("ROI", ConfigurationManager.AppSettings["FileUploadServletUrl"]);
            serveletPath += PrepareParams("?", LetterTemplateDocumentId, Convert.ToString(letterTemplateDetails.DocumentId, System.Threading.Thread.CurrentThread.CurrentCulture));
            serveletPath += PrepareParams("&", ROIConstants.OwnerId, Convert.ToString(letterTemplateDetails.Id, System.Threading.Thread.CurrentThread.CurrentCulture));
            serveletPath += PrepareParams("&", ROIConstants.OwnerType, ConfigurationManager.AppSettings["FileOwnerType"]);
            serveletPath += PrepareParams("&", LetterTemplateFileName, letterTemplateDetails.FileName);
            serveletPath += PrepareParams("&", ROIConstants.Timestamp, DateTime.Now.ToShortTimeString());
            serveletPath += PrepareParams("&", ROIConstants.UserId, UserData.Instance.UserInstanceId.ToString(System.Threading.Thread.CurrentThread.CurrentCulture));
            //CR# 382989
            if (UserData.Instance.IsLdapEnabled)
            {
                serveletPath += PrepareParams("&", ROIConstants.UserName, HttpUtility.UrlEncode(UserData.Instance.Domain) + "\\" + HttpUtility.UrlEncode(UserData.Instance.DomainUserName)
                                                                          + "~" + HttpUtility.UrlEncode(UserData.Instance.UserId));
                serveletPath += PrepareParams("&", ROIConstants.SecretWord, HttpUtility.UrlEncode(UserData.Instance.DomainSecretWord));
            }
            else
            {
                serveletPath += PrepareParams("&", ROIConstants.UserName, HttpUtility.UrlEncode(UserData.Instance.UserId));
                serveletPath += PrepareParams("&", ROIConstants.SecretWord, HttpUtility.UrlEncode(UserData.Instance.SecretWord));
            }
            serveletPath += PrepareParams("&", ROIConstants.AppId, ROIConstants.ROIDomainSource);
            return serveletPath;            
        }

        /// <summary>
        /// Appends separator and key to the value.
        /// </summary>
        /// <param name="separotor">separator</param>
        /// <param name="key">Key</param>
        /// <param name="value">value</param>
        /// <returns>String</returns>
        private static string PrepareParams(string separator, string key, string value)
        {
            return  separator + key + "=" + HttpUtility.HtmlEncode(value);
        }

        /// <summary>
        /// This method will download the file.
        /// </summary>
        /// <param name="letterTemplateDetails"></param>
        /// <param name="filePath"></param>
        public static void DownloadLetterTemplate(LetterTemplateDetails letterTemplateDetails, string filePath)
        {
            Hashtable serverParameter = new Hashtable(3);
            serverParameter.Add(ROIConstants.User, UserData.Instance.UserInstanceId);
            serverParameter.Add(ROIConstants.SecretWord, UserData.Instance.SecretWord);
            
            string ischunkEnabled = ConfigurationManager.AppSettings["ChunkEnabled"];
            string blockSize = ConfigurationManager.AppSettings["BlockSize"];

            ischunkEnabled = string.IsNullOrEmpty(ischunkEnabled) ? "false" : ischunkEnabled;
            serverParameter.Add(ROIConstants.ChunkEnabled,ischunkEnabled );
            if (Convert.ToBoolean(ischunkEnabled, System.Threading.Thread.CurrentThread.CurrentCulture))
            {
                serverParameter.Add(ROIConstants.BlockSize, blockSize);
            }
            
            DownloadContent fileDownloadContent = new DownloadContent();
            fileDownloadContent.ContentId = Convert.ToString(letterTemplateDetails.DocumentId, System.Threading.Thread.CurrentThread.CurrentCulture);

            serverParameter.Add(LetterTemplateDocumentId, fileDownloadContent.ContentId);
            serverParameter.Add(ROIConstants.OwnerType, "LetterTemplate");

            ROIFileDownloadController.Instance.DownloadFile(fileDownloadContent,
                                                            filePath,
                                                            serverParameter,null);
        }

        /// <summary>
        /// Delete LetterTemplate for the given Id.
        /// </summary>
        /// <param name="letterTemplateId"></param>
        public void DeleteLetterTemplate(long letterTemplateId)
        {
            object[] requestParams = new object[] { letterTemplateId };
            ROIHelper.Invoke(roiAdminService, "deleteLetterTemplate", requestParams);
            letterTemplates = null;
        }

        /// <summary>
        /// Returns a list of Letter Template.
        /// </summary>
        /// <returns></returns>
        public Collection<LetterTemplateDetails> RetrieveAllLetterTemplates()
        {  
            if (letterTemplates == null)
            {
                object response = ROIHelper.Invoke(roiAdminService, "retrieveAllLetterTemplates", new object[0]);
                letterTemplates = MapModel((LetterTemplate[])response);                            
            }         
            return letterTemplates;
        }

        /// <summary>
        /// Returns LetterTemplate Details for the given LetterTemplate ID.
        /// </summary>
        /// <param name="LetterTemplateId"></param>
        /// <returns></returns>
        public LetterTemplateDetails GetLetterTemplate(long letterTemplateId)
        {
            object[] requestParams = new object[] { letterTemplateId };
            object response = ROIHelper.Invoke(roiAdminService, "retrieveLetterTemplate", requestParams);
            LetterTemplateDetails clientLetterTemplateDetails = MapModel((LetterTemplate)response);

            return clientLetterTemplateDetails;
        }

        public bool HasLetterTemplate(LetterType letterType)
        {
            object response = ROIHelper.Invoke(roiAdminService, "hasLetterTemplate", 
                                               new object[] { letterType.ToString() });
            return (bool)response;
        }

        #endregion

        #region Model Mapper

        /// <summary>
        /// Convert Server Letter Template to Client Letter Template.
        /// </summary>
        /// <param name="serverLetterTemplate"></param>
        /// <returns></returns>
        private static LetterTemplateDetails MapModel(LetterTemplate serverLetterTemplate)
        {  
            LetterTemplateDetails clientLetterTemplate = new LetterTemplateDetails();
            clientLetterTemplate.Id               = serverLetterTemplate.letterTemplateId;
            clientLetterTemplate.IsDefault        = serverLetterTemplate.isDefault;
            try
            {
                clientLetterTemplate.LetterType = (LetterType)Enum.Parse(typeof(LetterType),
                                                                                  serverLetterTemplate.letterType, true);
            }
            catch (Exception e)
            { 
            }
            clientLetterTemplate.HasNotes         = serverLetterTemplate.hasNotes;
            clientLetterTemplate.Name             = serverLetterTemplate.name;
            clientLetterTemplate.Description      = serverLetterTemplate.description;
            clientLetterTemplate.FileName         = serverLetterTemplate.uploadFile;
            clientLetterTemplate.DocumentId       = serverLetterTemplate.docId;
            clientLetterTemplate.RecordVersionId  = serverLetterTemplate.recordVersion;
            return clientLetterTemplate;
        }

        /// <summary>
        /// Convert client Letter Template to server Letter Template.
        /// </summary>
        /// <param name="clientLetterTemplate"></param>
        /// <returns></returns>
        private static LetterTemplate MapModel(LetterTemplateDetails clientLetterTemplate)
        {   
            LetterTemplate serverLetterTemplate = new LetterTemplate();

            serverLetterTemplate.letterTemplateId = clientLetterTemplate.Id;
            serverLetterTemplate.isDefault        = clientLetterTemplate.IsDefault;
            serverLetterTemplate.letterType       = Convert.ToString(clientLetterTemplate.LetterType, System.Threading.Thread.CurrentThread.CurrentCulture);
            serverLetterTemplate.name             = clientLetterTemplate.Name;
            serverLetterTemplate.description      = clientLetterTemplate.Description;
            serverLetterTemplate.uploadFile       = clientLetterTemplate.FileName;
            serverLetterTemplate.docId            = clientLetterTemplate.DocumentId;
            serverLetterTemplate.recordVersion    = clientLetterTemplate.RecordVersionId;
            serverLetterTemplate.hasNotes         = clientLetterTemplate.HasNotes;
            return serverLetterTemplate;
        }

        /// <summary>
        /// Convert server Letter Template list to client Letter Template list.
        /// </summary>
        /// <param name="serverLetterTemplates"></param>
        /// <returns></returns>
        private static Collection<LetterTemplateDetails> MapModel(LetterTemplate[] serverLetterTemplates)
        {
            Collection<LetterTemplateDetails> clientLetterTemplates = new Collection<LetterTemplateDetails>();
            LetterTemplateDetails clientLetterTemplate;
            foreach (LetterTemplate serverLetterTemplate in serverLetterTemplates)
            {
                clientLetterTemplate = MapModel(serverLetterTemplate);
                clientLetterTemplates.Add(clientLetterTemplate);
            }
            return clientLetterTemplates;
        }

        #endregion

        #endregion
    }
}
