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
using System.Collections.ObjectModel;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Model contains the RequestPartDetails
    /// </summary>
    [Serializable]
    public class RequestPartDetails
    {
        #region Fields

        private string contentId;

        private string contentSource;

        private Collection<PropertyDetails> propertyLists;

        #endregion

        #region Properties

        public string ContentId
        {
            get { return contentId; }
            set { contentId = value; }
        }

        public string ContentSource
        {
            get { return contentSource; }
            set { contentSource = value; }
        }

        /// <summary>
        /// Gets the collection of request parts
        /// </summary>
        public Collection<PropertyDetails> PropertyLists
        {
            get
            {
                if (propertyLists == null)
                {
                    propertyLists = new Collection<PropertyDetails>();
                }
                return propertyLists;
            }
        }

        #endregion
    }
}
