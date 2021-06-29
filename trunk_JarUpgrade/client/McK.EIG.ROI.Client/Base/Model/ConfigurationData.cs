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
using System.Text;

namespace McK.EIG.ROI.Client.Base.Model
{
    /// <summary>    
    /// Class to hold the HPF configuration Data    
    /// </summary>
    public class ConfigurationData
    {
        #region Fields

        public const string PrivateKey              = "ci.security.privateKey";
        public const string SecureRequiredKey     = "ci.security.password";
        public const string TokenRequiredKey        = "ci.security.token";
        public const string EpnPrefixKey            = "empi.prefix";
        public const string EpnEnabledKey           = "empi.enabled";
        public const string LoadLevelerKey          = "LOAD_LEVELER";
        public const string LoadLevelerUrlKey       = "LOAD_LEVELER_URL";
        public const string ContentWebServiceUrlKey = "CONTENT_WEB_SERVICE_URL";
        public const string PageCountUrlKey         = "CALCPGCOUNT_WEB_SERVICE_URL";

        /// <summary>
        /// Singleton instance.
        /// </summary>
        private static ConfigurationData instance = new ConfigurationData();

        /// <summary>
        /// Holds private key
        /// </summary>
        private string privateKeyToken;

        /// <summary>
        /// True if password is enabled
        /// </summary>
        private bool passwordEnabled;

        /// <summary>
        /// True if security token is enabled
        /// </summary>
        private bool tokenEnabled;

        /// <summary>
        /// True if epn is enabled
        /// </summary>
        private bool epnEnabled;

        /// <summary>
        /// Hold epn prefix value
        /// </summary>
        private string epnPrefix;

        /// <summary>
        /// Holds load leveler.
        /// </summary>
        private string loadLeveler;

        /// <summary>
        /// Holds load leveler.
        /// </summary>
        private Uri loadLevelerUrl;

        /// <summary>
        /// Holds content web service URL
        /// </summary>
        private Uri contentWebServiceUrl;

        ///<summary>
        ///Holds calcpgcount URL
        ///</summary>
        private Uri pageCountUrl;

        #endregion

        #region Properties

        #region PrivateKey
        /// <summary>
        /// Gets or sets the private key token
        /// </summary>
        public string PrivateKeyToken
        {
            get { return privateKeyToken; }
            set { privateKeyToken = value; }
        }
        #endregion

        #region PasswordEnabled
        /// <summary>
        /// Gets or sets the password enabled.
        /// </summary>
        public bool PasswordEnabled
        {
            get { return passwordEnabled; }
            set { passwordEnabled = value; }
        }

        #endregion

        #region TokenEnabled
        /// <summary>
        /// Gets or sets the token enabled.
        /// </summary>
        public bool TokenEnabled
        {
            get { return tokenEnabled; }
            set { tokenEnabled = value; }
        }

        #endregion

        #region EpnEnabled
        /// <summary>
        /// Gets or sets the epn enabled.
        /// </summary>
        public bool EpnEnabled
        {
            get { return epnEnabled; }
            set { epnEnabled = value; }
        }

             #endregion

        #region EpnPrefix
        /// <summary>
        /// Gets or sets the epn prefix.
        /// </summary>
        public string EpnPrefix
        {
            get { return epnPrefix; }
            set { epnPrefix = value ?? string.Empty; }
        }

        #endregion

        #region LoadLeveler
        /// <summary>
        /// Gets or sets the load leveler
        /// </summary>
        public string LoadLeveler
        {
            get { return loadLeveler; }
            set { loadLeveler = value; }
        }
        #endregion

        #region LoadLevelerUrl
        /// <summary>
        /// Gets or sets the loadLeveler Url
        /// </summary>
        public Uri LoadLevelerUrl
        {
            get { return loadLevelerUrl; }
            set { loadLevelerUrl = value; }
        }
        #endregion

        #region ContentWebServiceUrl
        /// <summary>
        /// Gets or sets the loadLeveler Url
        /// </summary>
        public Uri ContentWebServiceUrl
        {
            get { return contentWebServiceUrl; }
            set { contentWebServiceUrl = value; }
        }
        #endregion

        #region PageCountUrl

        /// <summary>
        /// Gets or sets the loadLeveler Url
        /// </summary>
        public Uri PageCountUrl
        {
            get { return pageCountUrl; }
            set { pageCountUrl = value; }
        }

        #endregion

        #endregion

        #region Methods

        /// <summary>
        /// Get user data instance from singleton.
        /// </summary>
        /// <returns></returns>
        public static ConfigurationData Instance
        {
            get { return ConfigurationData.instance; }
        }

        #endregion
    }
}
