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
using System.Globalization;
using log4net;
using log4net.Config;

namespace McK.EIG.ROI.DataVault.Base
{
    /// <summary>
    /// VaultLogger
    /// </summary>
    public sealed class VaultLogger
    {
        #region Properties

        private log4net.ILog _log;
        private static VaultLogger log;
        private const string divider = "-----------------------------------------------------------------";

        #endregion

        #region Constructor

        /// <summary>
        /// Private contructor to achieve singleton.
        /// </summary>
        /// <param name="loggerName">The name of the logger configuration.</param>
        private VaultLogger(string loggerName)
        {
            _log = LogManager.GetLogger(loggerName);
        }
        #endregion

        #region Methods

        /// <summary>
        /// returns an instance of this class
        /// </summary>        
        public static VaultLogger GetInstance(string message)
        {
            //check if the singleton instance is already created.
            //if no create it
            if (log == null)
            {
                log = new VaultLogger(message);
            }
            //return the singleton instance of this class
            return log;
        }

        /// <summary>
        /// Log the message at the info level.
        /// </summary>        
        /// <param name="message">Extra Information that need to be added.</param>
        public void Info(string message)
        {
            _log.Info(message);
        }

        /// <summary>
        /// Log the message at the debug level. 
        /// </summary>
        /// <param name="message">Extra Information that need to be added.</param>
        public void Debug(string message)
        {
            _log.Debug(message);
        }

        /// <summary>
        /// Add the divider line.
        /// </summary>
        public void DividerLine()
        {
            _log.Debug(divider);
        }

        #endregion
    }
}

