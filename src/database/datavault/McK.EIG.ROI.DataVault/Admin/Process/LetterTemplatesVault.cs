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
using System.Data;
using System.Data.Common;
using System.Globalization;

using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// LetterTemplate Data Vault.
    /// </summary>
    public class LetterTemplatesVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(LetterTemplateDetails));

        #region Fields

        //DataBase Fields
        private const string RefId              = "Ref_ID";
        private const string IsDefault          = "Default";
        private const string LetterTemplateType = "Letter_Type";
        private const string LetterName         = "Name";
        private const string Description        = "Description";
        private const string UploadFilePath     = "Upload_File";
        private const string SystemSeed         = "IsSystemSeed";

        internal static EventHandler ProgressHandler;
        Hashtable letterTemplateHT;

        private VaultMode modeType;

        #endregion

        #region Constructor

        public LetterTemplatesVault()
        {
            //Initilize the required variables.
            Init();
            CacheAllLetterTemplate();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the required variables.
        /// </summary>
        private void Init()
        {
            ProgressHandler = new EventHandler(Process_NotifyProgress);
            letterTemplateHT = new Hashtable(new VaultComparer());            
        }

        /// <summary>
        /// System Load the Letter Template Vault.
        /// </summary>
        /// <param name="reader"></param>
        /// <returns></returns>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();

            try
            {
                LetterTemplateDetails letterTemplateDetails;                
                long recordcount = 1;                

                while (reader.Read())
                {
                    string letterRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                    bool isSystemSeed = (Convert.ToString(reader[SystemSeed],
                                         CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                         false : Convert.ToBoolean(reader[SystemSeed], CultureInfo.CurrentCulture);
                    letterTemplateDetails = new LetterTemplateDetails();

                    letterTemplateDetails.IsDefault   = reader[IsDefault].ToString().Trim().Length != 0 ?
                                                        Convert.ToBoolean(reader[IsDefault], CultureInfo.CurrentCulture) :
                                                        false;
                    switch (reader[LetterTemplateType].ToString())
                    {
                        case "Cover Letter":
                            letterTemplateDetails.LetterType = LetterType.CoverLetter;
                            break;
                        case "Invoice":
                            letterTemplateDetails.LetterType = LetterType.Invoice;
                            break;
                        case "None":
                            letterTemplateDetails.LetterType = LetterType.Invoice;
                            break;
                        case "Other":
                            letterTemplateDetails.LetterType = LetterType.Other;
                            break;
                        case "PreBill":
                            letterTemplateDetails.LetterType = LetterType.PreBill;
                            break;
                    } 
                    letterTemplateDetails.Name        = Convert.ToString(reader[LetterName],
                                                                         CultureInfo.CurrentCulture).Trim();
                    letterTemplateDetails.Description = Convert.ToString(reader[Description],
                                                                         CultureInfo.CurrentCulture).Trim();
                    letterTemplateDetails.FilePath    = Convert.ToString(reader[UploadFilePath],
                                                                         CultureInfo.CurrentCulture).Trim();

                    if (isSystemSeed)
                    {
                        LetterTemplateDetails templateDetails = letterTemplateHT[letterTemplateDetails.Name] as LetterTemplateDetails;
                        if (templateDetails == null)
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidSystemSeedData, letterTemplateDetails.Name, EntityName);
                            log.Debug(message);
                            throw new VaultException(message);
                        }    
                        templateDetails.IsDefault = letterTemplateDetails.IsDefault;
                        templateDetails.LetterType = letterTemplateDetails.LetterType;
                        templateDetails.Name = letterTemplateDetails.Name;
                        templateDetails.Description = letterTemplateDetails.Description;
                        if (letterTemplateDetails.FilePath.Trim().Length > 0)
                        {
                            templateDetails.FilePath = letterTemplateDetails.FilePath;
                            templateDetails.DoUpload = true;
                        }
                        else
                        {
                            templateDetails.DoUpload = false;
                        }

                        templateDetails = UpdateLetterTemplate(templateDetails, recordcount);
                        letterTemplateHT.Remove(letterTemplateDetails.Name);

                        letterTemplateHT.Add(letterRefId, templateDetails);
                    }
                    else
                    {
                        //Save the Requestor Type.
                        letterTemplateHT.Add(letterRefId, SaveLetterTemplate(letterTemplateDetails, recordcount));
                    }
                    
                    recordcount++;
                }
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (ArgumentException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (InvalidCastException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            finally
            {
                reader.Close();
            }
            log.ExitFunction();
            return letterTemplateHT;
        }

        private void CacheAllLetterTemplate()
        {
            log.EnterFunction();
            try
            {
                ROIAdminController adminController = ROIAdminController.Instance;
                Collection<LetterTemplateDetails> letterTemplateDetails = adminController.RetrieveAllLetterTemplates();
                foreach (LetterTemplateDetails templateDetails in letterTemplateDetails)
                {
                    LetterTemplateDetails details = adminController.GetLetterTemplate(templateDetails.Id);
                    letterTemplateHT.Add(templateDetails.Name.Trim(), details);
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message, cause);
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Passes the LetterTemplate object to the ROIAdminController for further process
        /// </summary>
        /// <param name="reasonDetail"></param>
        /// <param name="recordCount"></param>
        /// <returns></returns>
        private LetterTemplateDetails SaveLetterTemplate(LetterTemplateDetails letterTemplateDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                        DataVaultConstants.ProcessStartTag,
                                        recordCount,
                                        DateTime.Now));

                //Call the ROIAdminController to save the RequestReason
                letterTemplateDetails = ROIAdminController.Instance.CreateLetterTemplate(letterTemplateDetails, ProgressHandler);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return letterTemplateDetails;
        }

        private LetterTemplateDetails UpdateLetterTemplate(LetterTemplateDetails letterTemplateDetails, long recordCount)
        {
            LetterTemplateDetails templateDetails = new LetterTemplateDetails();
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));

                //Call the ROIAdminController to save the RequestReason
                templateDetails = ROIAdminController.Instance.UpdateLetterTemplate(letterTemplateDetails, ProgressHandler);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return templateDetails;
        }

        /// <summary>
        /// Progress Notify the file upload.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            FileTransferEventArgs fileTransferEvent = (FileTransferEventArgs)e;
            switch (fileTransferEvent.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    log.Debug(fileTransferEvent.TransferStatus.ToString());
                    log.Debug(DataVaultConstants.TotalFileSize + fileTransferEvent.FileSize);
                    break;
                case FileTransferEventArgs.Status.InProgress:
                    log.Debug(DataVaultConstants.TransferredFileSize + fileTransferEvent.TransferredSize);
                    log.Debug(fileTransferEvent.TransferStatus.ToString());
                    break;
                case FileTransferEventArgs.Status.Finish:
                    log.Debug(DataVaultConstants.TransferredFileSize + fileTransferEvent.TransferredSize);
                    log.Debug(fileTransferEvent.TransferStatus.ToString());
                    break;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity na    me.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.LetterTemplate; }
        }

        /// <summary>
        /// Return the mode type.
        /// </summary>
        public VaultMode ModeType
        {
            get
            {
                return modeType;
            }
            set
            {
                modeType = value;
            }
        }
        #endregion
    }
}
                    
