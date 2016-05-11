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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    /// <summary>
    /// This class is used to hold FindRequestorResult Criteria.
    /// </summary>
    public class FindRequestorResult
    {
        #region Fields

        private bool maxCountExceeded;
        private long requestorTypeId;
        private Collection<RequestorDetails> requestorSearchResult;
        
        #endregion

        #region Constructors
        
        public FindRequestorResult()
        {

        }
        
        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets MaxCountExceeded Value
        /// </summary>
        public bool MaxCountExceeded
        {
            get { return maxCountExceeded; }
            set { maxCountExceeded = value; }
        }

        /// <summary>
        /// This property is used to get or sets the collection of requestordetails.
        /// </summary>
        public Collection<RequestorDetails> RequestorSearchResult
        {
            get 
            {
                if (requestorSearchResult == null)
                {
                    requestorSearchResult = new Collection<RequestorDetails>();
                }
                return requestorSearchResult;
            }
        }

        public long RequestorTypeId
        {
            get { return requestorTypeId; }
            set { requestorTypeId = value;}
        }
        
        #endregion
    }
}
