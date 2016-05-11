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
using System.Collections.Generic;
using System.Xml.XPath;
using System.Xml;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Class that holds history details of the release
    /// </summary>
    public class ReleaseHistoryDetails  
    {
        #region Fields

        private int totalCount;

        private int totalPageCount;

        private SortedList<string, ReleasedPatientDetails> releasePatients;

        private SortedList userNames;

        private SortedList releaseDate;

        private Collection<ReleasedItemDetails> releaseItems;
        
        #endregion 

        #region Properties

        /// <summary>
        /// Gets or sets the total count of the release histories.
        /// </summary>
        public int TotalCount
        {
            get { return totalCount;}
            set { totalCount = value;}
        }

        /// <summary>
        /// Gets or sets the total page count of the release histories.
        /// </summary>
        public int TotalPageCount
        {
            get { return totalPageCount; }
            set { totalPageCount = value; }
        }

        /// <summary>
        /// Gets or sets the released patients.
        /// </summary>
        public SortedList<string, ReleasedPatientDetails> ReleasedPatients
        {
            get 
            {
                if (releasePatients == null)
                {
                    releasePatients = new SortedList<string, ReleasedPatientDetails>();
                }
                return releasePatients;
            }            
        }

        /// <summary>
        /// Holds list of usernames
        /// </summary>
        public SortedList UserNames
        {
            get 
            {
                if (userNames == null)
                {
                    userNames = new SortedList();
                }
                return userNames;
            }            
        }

        /// <summary>
        /// Holds list of released dates
        /// </summary>
        public SortedList ReleaseDate
        {
            get 
            {
                if (releaseDate == null)
                {
                    releaseDate = new SortedList();
                }
                return releaseDate;
            }            
        }

        /// <summary>
        /// Gets or sets the released item details.
        /// </summary>
        public Collection<ReleasedItemDetails> ReleaseItems
        {
            get 
            {
                if (releaseItems == null)
                {
                    releaseItems = new Collection<ReleasedItemDetails>();
                }
                return releaseItems; 
            }           
        }

        #endregion

    }
}
