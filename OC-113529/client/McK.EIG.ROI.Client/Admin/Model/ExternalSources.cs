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
using System.Collections.ObjectModel;
using McK.EIG.ROI.Client.Base.Model;
namespace McK.EIG.ROI.Client.Admin.Model
{

    /// <summary>
    /// This Class is used to hold ExternalSource info
    /// </summary>
    [Serializable]
    public class ExternalSource
    {
        #region Fields

        private int sourceId;
        private string sourceName;
        private string providerName;
        private string description;
        private Collection<ConnectionProperty> connectionProperties;

        #endregion

        #region Properties

        public int SourceId
        {
            get
            {
                return sourceId;
            }
            set
            {
                sourceId = value;
            }
        }

         /// <summary>
        /// This property is used to get or set the ExternalSource SourceName.
        /// </summary>
        public string SourceName
        {
            get
            {
                return sourceName;
            }
            set
            {
                sourceName = value;
            }
        }

        /// <summary>
        /// This property is used to get or set the ExternalSource ProviderName.
        /// </summary>        
        public string ProviderName
        {
            get
            {
                return providerName;
            }
            set
            {
                providerName = value;
            }
        }

        /// <summary>
        /// This property is used to get or set the ExternalSource Description.
        /// </summary>
        public string Description
        {
            get
            {
                return description;
            }
            set
            {
                description = value;
            }
        }

        /// <summary>
        /// This property is used to get the ConnectionProperty collection.
        /// </summary>
        public Collection<ConnectionProperty> ConnectionProperties
        {
            get
            {

                if (connectionProperties == null)
                {
                    connectionProperties = new Collection<ConnectionProperty>();
                }
                return connectionProperties;
            }
        }

        #endregion

        #region Methods

        public ExternalSource Clone()
        {
            return (ExternalSource)this.MemberwiseClone();
        }

        #endregion
    }
}
