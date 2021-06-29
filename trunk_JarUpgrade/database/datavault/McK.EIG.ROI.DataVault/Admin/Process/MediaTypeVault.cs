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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// Media Type Data Vault
    /// </summary>
    public class MediaTypeVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(MediaTypeVault));

        #region Fields

        //DataBase Fields
        private const string RefId         = "Ref_ID";
        private const string MTName        = "Media_Type_Name";
        private const string MTDescription = "Media_Type_Desc";
        private const string SeedData      = "IsSeed";

        private Hashtable mediaTypeHT;

        private VaultMode modeType;

        #endregion

        #region Constructor

        public MediaTypeVault()
        {
            mediaTypeHT = new Hashtable(new VaultComparer());            
            CacheAllSeedData();
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Media Type Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {            
            log.EnterFunction();
            long recordCount;
            try
            {
                recordCount = 1;
                while (reader.Read())
                {
                    MediaTypeDetails details = new MediaTypeDetails();
                    details.Name = Convert.ToString(reader[MTName], CultureInfo.CurrentCulture).Trim();
                    details.Description = Convert.ToString(reader[MTDescription], CultureInfo.CurrentCulture).Trim();
                    string mediaTypeRefID = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();
                    
                    
                    if (modeType == VaultMode.Create)
                    {
                        bool isSeed = (Convert.ToString(reader[SeedData],
                                       CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                       false : Convert.ToBoolean(reader[SeedData], CultureInfo.CurrentCulture);
                        CreateMediaType(details, isSeed, mediaTypeRefID, recordCount);
                    }
                    else
                    {
                        UpdateMediaType(details, mediaTypeRefID, recordCount);
                    }
                    recordCount++;
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
            return mediaTypeHT;
        }

        /// <summary>
        /// Get all Seed data.
        /// </summary>
        private void CacheAllSeedData()
        {
            log.EnterFunction();
            try
            {
                BillingAdminController billingController = BillingAdminController.Instance;
                Collection<MediaTypeDetails> mediTypeDetails = billingController.RetrieveAllMediaTypes();
                foreach (MediaTypeDetails mediaTypeDetail in mediTypeDetails)
                {
                    MediaTypeDetails mediaType = billingController.GetMediaType(mediaTypeDetail.Id);
                    mediaTypeHT.Add(mediaTypeDetail.Name.Trim(), mediaType);
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message, cause);
            }
            log.ExitFunction();
        }

        private void CreateMediaType(MediaTypeDetails mediaTypedetails, bool isSeed, string mediaTypeRefID, long recordCount)
        {
            log.EnterFunction();
            if (isSeed)
            {
                MediaTypeDetails details = mediaTypeHT[mediaTypedetails.Name] as MediaTypeDetails;
                if (details == null)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidSeedData, mediaTypedetails.Name, EntityName);
                    log.Debug(message);
                    throw new VaultException(message);
                }

                details.Description = mediaTypedetails.Description;
                details = UpdateMediaTypeDetail(details, recordCount);
                mediaTypeHT.Remove(mediaTypedetails.Name);
                mediaTypeHT.Add(mediaTypeRefID, details);
            }
            else
            {   
                //Save the MediaType details.
                mediaTypeHT.Add(mediaTypeRefID, SaveMediaTypeDetail(mediaTypedetails, recordCount));
            }
            log.ExitFunction();
        }

        private void UpdateMediaType(MediaTypeDetails details, string mediaTypeRefID,  long recordCount)
        {
            log.EnterFunction();
            if (!mediaTypeHT.ContainsKey(mediaTypeRefID))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, mediaTypeRefID, EntityName + "_" + VaultMode.Create);
                log.Debug(message);
                throw new VaultException(message);
            }
            MediaTypeDetails mediaTypeDetails = (MediaTypeDetails)mediaTypeHT[mediaTypeRefID];

            if (mediaTypeDetails.Id > 0)
            {
                mediaTypeDetails.Name = details.Name;
            }
            mediaTypeDetails.Description = details.Description;
            //update
            mediaTypeDetails = UpdateMediaTypeDetail(mediaTypeDetails, recordCount);
            mediaTypeHT[mediaTypeRefID] = mediaTypeDetails;
            log.ExitFunction();
        }


        /// <summary>
        /// Passes the media type object to the BillingAdminController for further process
        /// </summary>
        /// <param name="mediaTypes">MediaType object.</param>
        private MediaTypeDetails SaveMediaTypeDetail(MediaTypeDetails mediaTypes, long recordCount)
        {
            log.EnterFunction();
            try
            {   
                log.Debug(string.Format(CultureInfo.CurrentCulture, 
                                        DataVaultConstants.ProcessStartTag,  
                                        recordCount, 
                                        DateTime.Now));

                //Call the BillingAdminController to save the MediaType details.
                long id = BillingAdminController.Instance.CreateMediaType(mediaTypes);
                mediaTypes.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return mediaTypes;
        }

        private MediaTypeDetails UpdateMediaTypeDetail(MediaTypeDetails mediaType, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                        DataVaultConstants.ProcessStartTag,
                                        recordCount,
                                        DateTime.Now));

                //Call the BillingAdminController to save the MediaType details.
                mediaType = BillingAdminController.Instance.UpdateMediaType(mediaType);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }

            log.ExitFunction();
            return mediaType;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get
            {
                return DataVaultConstants.MediaType;
            }
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
