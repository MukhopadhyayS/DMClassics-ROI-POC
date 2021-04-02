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
    /// ConfigureNotes Data Vault
    /// </summary>
    public class ConfigureNotesVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(ConfigureNotesVault));

        #region Fields

        //DataBase Fields
        private const string RefId            = "Ref_ID";
        private const string NotesName        = "Name";
        private const string NotesDisplayText = "Display_Text";

        private VaultMode modeType;

        private Hashtable configureHT ;

        #endregion

        #region Constructor

        public ConfigureNotesVault()
        {
            configureHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Configure Notes Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            
            long recordcount;
            try
            {
                recordcount = 1;
                NotesDetails notesDetail;
                while (reader.Read())
                {
                    string configerRefId    = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                    notesDetail             = new NotesDetails();                    
                    notesDetail.Name        = reader[NotesName].ToString();
                    notesDetail.DisplayText = reader[NotesDisplayText].ToString();

                    //Save the Configure Notes object.
                    configureHT.Add(configerRefId, SaveConfigureNotesDetail(notesDetail, recordcount));

                    recordcount++;
                }
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new ROIException(DataVaultErrorCodes.OdbcError, cause);
            }            
            finally
            {
                reader.Close();
                reader.Dispose();
            }
            log.ExitFunction();
            return configureHT;
        }

        /// <summary>
        /// Passes the Configure Notes object to the ROIAdminController for further process
        /// </summary>
        /// <param name="mediaTypes">MediaType object.</param>
        private NotesDetails SaveConfigureNotesDetail(NotesDetails noteDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the ROIAdminController to save the ConfigureNotes Object.
                long id = ROIAdminController.Instance.CreateConfigureNotes(noteDetail);
                noteDetail.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return noteDetail;
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
                return DataVaultConstants.ConfigureNotes;
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
