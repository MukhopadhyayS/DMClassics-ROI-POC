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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Configuration;
using System.Globalization;
using System.Web;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using System.Web.Services.Protocols;
using McK.EIG.Common.Utility.Logging;

using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.FileTransfer.Model;

using McK.EIG.ROI.Client.Web_References.CcdConversionServiceWS;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    public partial class PatientController
    {
        private const string logMethodName = "Calling Webservice: {0}, Method:{1}";
        private const string logParams = "Parameter: ";
        private Log log = LogFactory.GetLogger(typeof(PatientController));
        #region Methods

        #region Service Methods

        public AttachmentFileDetails UploadCCRCCDXMLAttachment(String fileName, EventHandler progressHandler)
        {
            AttachmentFileDetails tmpFileDetails = new AttachmentFileDetails();

            String uuid = "none";

            System.IO.FileInfo fi = new System.IO.FileInfo(fileName);
            if (string.IsNullOrEmpty(fi.Extension))
                throw new ROIException(ROIErrorCodes.AttachmentExtensionEmpty);
            uuid = UploadFile(fileName, progressHandler);

            tmpFileDetails.Uuid = uuid;

            
            tmpFileDetails.Extension = fi.Extension.Substring(1);
            tmpFileDetails.FileName = fi.Name.Replace(fi.Extension, "");
            
            tmpFileDetails.Printable = false;

            if (!string.IsNullOrEmpty(tmpFileDetails.Extension))
                if (tmpFileDetails.Extension.ToUpper() == "PDF")
                    tmpFileDetails.Printable = true;

            //Call GenerateCcrCcdPdf method to validate XML and retreive file type
            AttachmentFileDetails ccrccdFile = GenerateCcrCcdPdf(uuid);

            tmpFileDetails.FileType = string.IsNullOrEmpty(ccrccdFile.FileType) ? ccrccdFile.FileType : ccrccdFile.FileType.ToUpper();
            return tmpFileDetails;
        }


        public AttachmentFileDetails UploadAttachment(String fileName, EventHandler progressHandler)
        {
            String uuid = "none";
            AttachmentFileDetails tmpFileDetails = new AttachmentFileDetails();

            System.IO.FileInfo fi = new System.IO.FileInfo(fileName);
            if (string.IsNullOrEmpty(fi.Extension))
                throw new ROIException(ROIErrorCodes.AttachmentExtensionEmpty);
            uuid = UploadFile(fileName, progressHandler);
            tmpFileDetails.Uuid = uuid;            
            tmpFileDetails.Extension  = fi.Extension.Substring(1);
            tmpFileDetails.FileName = fi.Name.Replace(fi.Extension,"");
            tmpFileDetails.FileType = string.Empty;

            //TODO:  should default mime-type to?
            // application/octet-stream

            return tmpFileDetails;
         }

        public string getExternalSource(string facility)
        {
                object[] requestParams = new object[] { facility };
                if (ccdConversionService == null)
                {
                    ccdConversionService = new CcdConversionServiceWse();
                }
                object response = ROIHelper.Invoke(ccdConversionService, "getExternalSourceNameForFacility", requestParams);
                return (string)response;
        }

        public List<object> UploadAttachmentFromSource(Collection<RetrieveCCDDetails> retrieveccdDetails)
        {
            List<object> files = new List<object>();

            AttachmentFileDetails fileDetails = new AttachmentFileDetails();
            AttachmentFileDetails tmpFileDetails = new AttachmentFileDetails();

            RetrieveCCDDto[] serverCCDDto = MapModel(retrieveccdDetails);
            object[] requestParams = new object[] { serverCCDDto };
            try
            {
                if (ccdConversionService == null)
                {
                    ccdConversionService = new CcdConversionServiceWse();
                }
                log.Debug(logMethodName, ccdConversionService.GetType().Name, "retrieveCCD");
                log.Debug(logParams + "Collection<RetrieveCCDDetails>");
                RetrieveCCDDetails ccDDetails;
                for (int j = 0; j < retrieveccdDetails.Count; j++)
                {
                    ccDDetails = retrieveccdDetails[j].Clone();
                    foreach (PropertyDescriptor descriptor in TypeDescriptor.GetProperties(ccDDetails))
                    {
                        log.Debug("\t     [{0} = {1}]", descriptor.Name, descriptor.GetValue(ccDDetails));
                    }
                }
                object response = ROIHelper.Invoke(ccdConversionService, "retrieveCCD", requestParams);
                Collection<CcdDocument> ccdDocuments = new Collection<CcdDocument>();
                if (response != null)
                {
                    ccdDocuments = MapModel((CcdDocument[])response);
                }
                else
                {
                    //Return an empty list, if no file is found for the encounter
                    return files;
                }
                if (ccdDocuments != null && ccdDocuments.Count > 0)
                {
                    if (ccdDocuments.Count == 1 && ccdDocuments[0].type.ToUpper() == "XML")
                    {
                        tmpFileDetails.Uuid = ccdDocuments[0].fileName;
                        tmpFileDetails.PageCount = ccdDocuments[0].pageNumber;
                        tmpFileDetails.Extension = ccdDocuments[0].type;
                        tmpFileDetails.FileType = ccdDocuments[0].documentType;
                        tmpFileDetails.ServiceDate = ccdDocuments[0].serviceDate;
                        tmpFileDetails.DateReceived = ccdDocuments[0].receivedDate;
                        tmpFileDetails.Printable = false;
                        files.Add(tmpFileDetails);
                        requestParams = new object[] { tmpFileDetails.Uuid };
                        log.Debug(logMethodName, ccdConversionService.GetType().Name, "ccdConvert");
                        log.Debug(logParams + tmpFileDetails.Uuid);
                        response = ROIHelper.Invoke(ccdConversionService, "ccdConvert", requestParams);
                        if (response == null)
                        {
                            throw new ROIException(ROIErrorCodes.CCRCCDConvertError);
                        }
                        else
                        {
                            fileDetails.Extension = "pdf";
                            fileDetails.Uuid = ((ccdConvertResult)response).fileName;
                            fileDetails.PageCount = ((ccdConvertResult)response).pageNumber;
                            fileDetails.FileType = ((ccdConvertResult)response).type;
                            fileDetails.ServiceDate = tmpFileDetails.ServiceDate;
                            fileDetails.Printable = false;

                            if (!string.IsNullOrEmpty(fileDetails.Extension))
                                if (fileDetails.Extension.ToUpper() == "PDF")
                                    fileDetails.Printable = true;
                            if (fileDetails.Uuid == null ||
                                fileDetails.Uuid.Length == 0)
                            {
                                throw new ROIException(ROIErrorCodes.CCRCCDConvertError);
                            }
                        }
                        files.Add(fileDetails);
                    }
                    else
                    {
                        AttachmentFileDetails tmpFile;
                        foreach (CcdDocument ccdDoc in ccdDocuments)
                        {
                            tmpFile = new AttachmentFileDetails();
                            tmpFile.Uuid = ccdDoc.fileName;
                            tmpFile.PageCount = ccdDoc.pageNumber;
                            tmpFile.Extension = ccdDoc.type;
                            tmpFile.FileType = ccdDoc.documentType;
                            tmpFile.ServiceDate = ccdDoc.serviceDate;
                            tmpFile.DateReceived = ccdDoc.receivedDate;
                            tmpFile.Printable = (ccdDoc.type != "xml");
                            files.Add(tmpFile);
                        }
                    }

                }
              }
            catch (SoapException ex)
            {
                throw ex;
            }
            catch (Exception e)
            {
                throw e;
            }

            return files;
        }
        /// <summary>
        /// The method will generate PDF for CCR/CCD attachment.
        /// </summary>
        /// <param name="attachmentDetails"></param>
        /// <param name="progressHandler"></param>
        public AttachmentFileDetails GenerateCcrCcdPdf(String uuid)
        {
            AttachmentFileDetails tmpFileDetails = new AttachmentFileDetails();

            try
            {
                if (ccdConversionService == null)
                {
                    ccdConversionService = new CcdConversionServiceWse();
                }

                object[] requestParams = new object[] { uuid };
                object response = ROIHelper.Invoke(ccdConversionService, "ccdConvert", requestParams);
                if (response == null)
                {
                    throw new ROIException(ROIErrorCodes.CCRCCDConvertError);
                }
                else
                {
                    ccdConvertResult result = ((ccdConvertResult)response);
                    tmpFileDetails.Extension = "pdf";

                    //TODO:  what is the default naming convention for generated ccr ccd files?  who should
                    //       create fileName - client/server?
                    tmpFileDetails.FileName = "McKesson Generated CCRCCD";
                    tmpFileDetails.Uuid = result.fileName;
                    tmpFileDetails.PageCount = result.pageNumber;
                    tmpFileDetails.FileType = result.type;

                    tmpFileDetails.Printable = false;

                    if (!string.IsNullOrEmpty(tmpFileDetails.Extension))
                        if(string.Compare(tmpFileDetails.Extension, "PDF", true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                            tmpFileDetails.Printable = true;

                    if (tmpFileDetails.Uuid == null ||
                        tmpFileDetails.Uuid.Length == 0)
                    {
                        throw new ROIException(ROIErrorCodes.CCRCCDConvertError);
                    }
                }
            }
            catch (Exception)
            {
                throw new ROIException(ROIErrorCodes.CCRCCDConvertError);
            }

            return tmpFileDetails;

        }

        /// <summary>
        /// The method will upload the attachment.
        /// </summary>
        /// <param name="attachmentDetails"></param>
        /// <param name="progressHandler"></param>
        private static String UploadFile(String fileName, EventHandler progressHandler)
        {
            string checkInId="None";

            try
            {
                string serveletPath = PrepareServletUrl();
                FileUploadHandler uploadHandler = new FileUploadHandler(serveletPath);
                uploadHandler.ChunkEnabled = Convert.ToBoolean(ConfigurationManager.AppSettings["ChunkEnabled"], System.Threading.Thread.CurrentThread.CurrentUICulture);
                uploadHandler.FileTransferEvent += progressHandler;

                if (uploadHandler.FileUpload(fileName))
                {
                    checkInId = uploadHandler.GetCheckInId();
                }

                uploadHandler.FileTransferEvent -= progressHandler;

                if (checkInId == null ||
                    checkInId.Length == 0)
                {
                    throw new ROIException(ROIErrorCodes.AttachmentUploadError);
                }
            }
            catch
            {
                throw new ROIException(ROIErrorCodes.AttachmentUploadError);
            }

            return checkInId;
        }

        /// <summary>
        /// Create the Url to upload the Attachment file.
        /// </summary>
        /// <param name="letterTemplateDetails"></param>
        /// <returns></returns>
        private static string PrepareServletUrl()
        {
            string serveletPath = McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("ROI", ConfigurationManager.AppSettings["AttachmentUploadServletUrl"]);
            serveletPath += PrepareParams("?", ROIConstants.Timestamp, DateTime.Now.ToShortTimeString());
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
            return separator + key + "=" + HttpUtility.HtmlEncode(value);
        }

        #endregion

        public RetrieveCCDDto[] MapModel(Collection<RetrieveCCDDetails> client)
        {
            RetrieveCCDDto[] serverList = new RetrieveCCDDto[client.Count];
            int i = 0;

            foreach (RetrieveCCDDetails clientCCDDetails in client)
            {
                serverList[i] = new RetrieveCCDDto();
                serverList[i].retrieveCCDKey = clientCCDDetails.retrieveCCDKey;
                serverList[i].retrieveCCDValue = clientCCDDetails.retriveCCDValue;
                i++;
            }
            return serverList;
        }
        public static Collection<CcdDocument> MapModel(CcdDocument[] ccdDocuments)
        {
            if (ccdDocuments == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            Collection<CcdDocument> ccdDocumentList = new Collection<CcdDocument>();
            CcdDocument tempCCDDocument;
            foreach (CcdDocument ccdDocument in ccdDocuments)
            {
                tempCCDDocument = (CcdDocument)ccdDocument;
                ccdDocumentList.Add(tempCCDDocument);
            }
            return ccdDocumentList;
        }
        #endregion
    }
}
