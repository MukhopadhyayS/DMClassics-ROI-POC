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
    /// Model contains fee charge details
    /// </summary>
    [Serializable]
    public class DocumentInfoList
    {
        #region Fields

        private string name;

        private Collection<DocumentInfo> documentInfos;      

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// Gets or sets the document info list
        /// </summary>
        public Collection<DocumentInfo> DocumentInfoCollection
        {
            get 
            {
                if (documentInfos == null)
                {
                    documentInfos = new Collection<DocumentInfo>();
                }
                return documentInfos;
            }
            
        }

        #endregion
    }
}
