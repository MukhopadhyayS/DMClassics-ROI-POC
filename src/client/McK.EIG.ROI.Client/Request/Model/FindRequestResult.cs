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
    /// Class holds the Request search result details.
    /// </summary>    
    public class FindRequestResult
    {
        #region Fields

        //holds the Maximum count Exceeded.
        private bool maxCountExceeded;

        //holds the collection of request search result details
        private Collection<RequestDetails> requestSearchResult;

        #endregion

        #region Properties

        /// <summary>
        ///This property is used to get or sets the MaxCountExceeded.
        /// </summary>
        public bool MaxCountExceeded
        {
            get { return maxCountExceeded; }
            set { maxCountExceeded = value; }
        }


        /// <summary>
        /// This property is used to get or sets the request search result details.
        /// </summary>
        public Collection<RequestDetails> RequestSearchResult
        {
            get
            {
                if (requestSearchResult == null)
                {
                    requestSearchResult = new Collection<RequestDetails>();
                }
                return requestSearchResult;
            }
        }

        #endregion
    }
}
