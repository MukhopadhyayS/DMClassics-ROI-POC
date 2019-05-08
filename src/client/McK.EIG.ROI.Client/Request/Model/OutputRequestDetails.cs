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
using System.Globalization;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Model contains the OutputRequestDetails
    /// </summary>
    [Serializable]
    public class OutputRequestDetails
    {
        #region Fields

        private OutputDestinationDetails outputDestinationDetails;

        private Collection<RequestPartDetails> requestParts;
        private Nullable<DateTime> requestDate;
        private long requestId;
        private long releaseId;
        private string requestSecure;

        private bool isGroupingEnabled;

        #endregion

        #region Properties        

        public OutputRequestDetails(long requestId,
                                    long releaseId,
                                    string requestPassword,
                                    Nullable<DateTime> requestDate)
        {
            this.requestId = requestId;
            this.releaseId = releaseId;
            this.requestSecure = requestPassword;
            this.requestDate = requestDate;
        }

        public OutputDestinationDetails OutputDestinationDetails
        {
            get { return outputDestinationDetails; }
            set { outputDestinationDetails = value; }
        }

        /// <summary>
        /// Gets or sets the collection of request parts
        /// </summary>
        public Collection<RequestPartDetails> RequestParts
        {
            get
            {
                if (requestParts == null)
                {
                    requestParts = new Collection<RequestPartDetails>();
                }
                return requestParts;
            }
        }

        /// <summary>
        /// Gets or sets the is grouping enabled or not
        /// </summary>
        public bool IsGroupingEnabled
        {
            get { return isGroupingEnabled; }
            set { isGroupingEnabled = value; }
        }

        public String RequestDateTextNoFormat
        {
            get
            {
                return (requestDate.HasValue)
                    ? requestDate.Value.ToString(ROIConstants.DateNoSeparatorFormat, System.Threading.Thread.CurrentThread.CurrentUICulture)
                    : string.Empty;
            }
        }

        public String RequestDateTextFormatted
        {
            get
            {
                return (requestDate.HasValue)
                    ? requestDate.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture)
                    : string.Empty;
            }
        }

        public long RequestId
        {
            get { return requestId; }
        }

        public long ReleaseId
        {
            get { return releaseId; }
        }

        public string RequestSecretWord
        {
            get { return requestSecure; }
        }

        public Nullable<DateTime> RequestDate
        {
            get { return requestDate; }
        }

        #endregion
    }
}
