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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
     /// <summary>
     /// This class is used to carry the configure notes details.
     /// </summary>
    [Serializable]
    public class NotesDetails : ROIModel
    {
        #region Fields

        private long id;
        private string name;
        private string displayText;

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets notes ID.
        /// </summary>
        public override long Id
        {
            get
            {
                return id;
            }
            set
            {
                id = value;
            }
        }

        /// <summary>
        /// This property is used to get or sets the name.
        /// </summary>
        public string Name
        {
            get
            {
                return name;
            }
            set
            {
                name = value;
            }
        }

        /// <summary>
        /// This property is used to get or sets the display text.
        /// </summary>
        public string DisplayText
        {
            get
            {
                return displayText;
            }
            set
            {
                displayText = value;
            }
        }

        #endregion
    }
}
