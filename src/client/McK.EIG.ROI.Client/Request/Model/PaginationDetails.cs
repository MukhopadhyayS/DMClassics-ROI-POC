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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    public class PaginationDetails 
    {
        # region Fields

        private int startFrom;

        private int size;

        private bool isDescending;

        private string sortColumn;

        private string domainType;
        
        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the is starts from.
        /// </summary>
        public int StartFrom
        {
            get { return startFrom; }
            set { startFrom = value; }
        }

        /// <summary>
        /// Gets or sets the is size.
        /// </summary>
        public int Size
        {
            get { return size; }
            set { size = value; }
        }

        /// <summary>
        /// Gets or sets the is desc.
        /// </summary>
        public bool IsDescending
        {
            get { return isDescending; }
            set { isDescending = value; }
        }
                 
        /// <summary>
        /// Gets or sets the sort column name.
        /// </summary>
        public string SortColumn
        {
            get { return sortColumn; }
            set { sortColumn = value; }
        }

        /// <summary>
        /// Gets or sets the domain type.
        /// </summary>
        public string DomainType
        {
            get { return domainType; }
            set { domainType = value; }
        }

        #endregion
    }
}
