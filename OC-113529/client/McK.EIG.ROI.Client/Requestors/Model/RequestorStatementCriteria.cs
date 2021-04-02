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
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    /// <summary>
    /// This class is used to hold RequestorStatementDetail Info.
    /// </summary>
    [Serializable]
    public class RequestorStatementDetail
    {
        private long requestorId;
        private long templateFileId;
        private string templateName;
        private string outputMethod;
        private string queueSecure;
        private DateRange dateRange;
        private string[] notes;
        private long[] pastInvIds;

        /// <summary>
        /// Holds the requestor id
        /// </summary>
        public long RequestorId
        {
            get { return requestorId; }
            set { requestorId = value; }
        }

        /// <summary>
        /// Holds the Template File Id
        /// </summary>
        public long TemplateFileId
        {
            get { return templateFileId; }
            set { templateFileId = value; }
        }

        /// <summary>
        /// Holds the Template Name
        /// </summary>
        public string TemplateName
        {
            get { return templateName; }
            set { templateName = value; }
        }

        /// <summary>
        /// Holds the Output Method
        /// </summary>
        public string OutputMethod
        {
            get { return outputMethod; }
            set { outputMethod = value; }
        }

        /// <summary>
        /// Holds the Queue Password
        /// </summary>
        public string QueueSecretWord
        {
            get { return queueSecure; }
            set { queueSecure = value; }
        }

        /// <summary>
        /// Holds the DateRange
        /// </summary>
        public DateRange DateRange
        {
            get { return dateRange; }
            set { dateRange = value; }
        }

        /// <summary>
        /// Holds the notes
        /// </summary>
        public string[] Notes
        {
            get { return notes; }
            set { notes = value; }
        }

        /// <summary>
        /// Holds the past invoice Id's
        /// </summary>
        public long[] PastInvIds
        {
            get { return pastInvIds; }
            set { pastInvIds = value; }
        }
    }
}
