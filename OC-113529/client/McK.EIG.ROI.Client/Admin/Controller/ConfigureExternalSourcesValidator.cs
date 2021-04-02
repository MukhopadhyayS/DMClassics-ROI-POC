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
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Validates External Source model data.
    /// </summary>
    public partial class ROIAdminValidator
    {
        private bool connectionPropertyErr=false;
        #region Methods

        /// <summary>
        /// Validates the Name/Description of External Source
        /// </summary>
        /// <param name="source"></param>
        /// <returns>Returns true if Name and Description fields are filled for ExternalSource,otherwise returns false</returns>
        public bool ValidateSourceFields(ExternalSource source)
        {
            if(source.SourceName.Trim().Length==0)
            {
                AddError(ROIErrorCodes.ExternalSourceNameinvalid);
            }
            if (source.Description.Trim().Length == 0)
            {
                AddError(ROIErrorCodes.Descriptioninvalid);
            }
            return NoErrors;
        }

        /// <summary>
        /// Validates the Name/Value of Connection Property
        /// </summary>
        /// <param name="connection"></param>
        /// <returns>Returns true if ConfigKey and ConfigValue fields are filled for Connection Properties,otherwise returns false</returns>
        public bool ValidatePropertyFields(ConnectionProperty connection)
        {
            if (connection.ConfigKey.Trim().Length==0)
            {
                AddError(ROIErrorCodes.PropertyNameinvalid);
                connectionPropertyErr = true;
            }
            if (connection.ConfigValue.Trim().Length == 0)
            {
                AddError(ROIErrorCodes.Propertyvalueinvalid);
                connectionPropertyErr = true;
            }

            return connectionPropertyErr;
         }

        /// <summary>
        /// Validates ExternalSource
        /// </summary>
        /// <param name="source"></param>
        /// <returns>Returns true if input fiels for ExternalSource are filled, or else returns false</returns>
        public bool ValidateUpdateSource(ExternalSource source)
        {
            ValidateSourceFields(source);
            ConnectionProperty property;
            for (int i = 0; i < source.ConnectionProperties.Count; i++)
            {
                property = new ConnectionProperty();
                property.ConfigKey = source.ConnectionProperties[i].ConfigKey;
                property.ConfigValue = source.ConnectionProperties[i].ConfigValue;
                ValidateUpdateProperty(property);
                if (connectionPropertyErr)
                    break;
                
            }
            return NoErrors;
        }

        /// <summary>
        /// Validates Connection Property
        /// </summary>
        /// <param name="property"></param>
        /// <returns>Returns true if input fiels for ConnectionProperty are filled, or else returns false</returns>
        public bool ValidateUpdateProperty(ConnectionProperty property)
        {
            ValidatePropertyFields(property);
            return NoErrors;
        }

        #endregion

    }
}
