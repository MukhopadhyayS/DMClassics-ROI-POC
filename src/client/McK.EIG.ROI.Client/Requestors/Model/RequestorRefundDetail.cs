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
    public class RequestorRefundDetail
    {
        private long requestorId;
        private string requestorName;
        private string requestorType;
        private string note;
        private System.DateTime refundDate;
        private double refundAmount;
        private RequestorStatementDetail requestorStatementDetail;
        private string[] notes;
        private long templateId;
        private string templateName;
        private string outputMethod;
        private string queueSecure;

        public long RequestorID
        {
            get { return requestorId; }
            set { requestorId = value; }
        }

        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }

        public string RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }

        public string Note
        {
            get { return note; }
            set { note = value; }
        }

        public System.DateTime RefundDate
        {
            get { return refundDate; }
            set { refundDate = value; }
        }

        public double RefundAmount
        {
            get { return refundAmount; }
            set { refundAmount = value; }
        }

        /// <summary>
        /// Holds the notes
        /// </summary>
        public string[] Notes
        {
            get { return notes; }
            set { notes = value; }
        }

        public RequestorStatementDetail RequestorStatementDetail
        {
            get { return requestorStatementDetail; }
            set { requestorStatementDetail = value; }
        }

        public long TemplateId
        {
            get { return templateId; }
            set { templateId = value; }
        }

        public string TemplateName
        {
            get { return templateName; }
            set { templateName = value; }
        }

        public string OutputMethod
        {
            get { return outputMethod; }
            set { outputMethod = value; }
        }

        public string QueueSecureWord
        {
            get { return queueSecure; }
            set { queueSecure = value; }
        }
    }
}
