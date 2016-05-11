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
using System.ComponentModel;

namespace McK.EIG.ROI.Client.Request.Model
{
    public enum EventType
    {
        //Reverse the write off amount status was changed from ‘Denied’ or ‘Canceled’
        [Description("ADJUSTMENT_POSTED")]
        AdjustmentPosted,

        [Description("AUTO_ADJUSTMENT_POSTED")]
        AutoAdjustmentPosted,

        [Description("COMMENT_ADDED")]
        CommentAdded,

        [Description("DOCUMENTS_RELEASED")]
        DocumentsReleased,

        [Description("DOCUMENTS_RE_RELEASED")]
        DocumentsRereleased,

        [Description("DOCUMENTS_RESEND")]
        DocumentsResend,

        [Description("INVOICE_SEND")]
        InvoiceSend,

        [Description("PRE_BILL_SENT")]
        PreBillSend,

        [Description("LETTER_SENT")]
        LetterSend,

        // When the status was changed to ‘Denied’ or ‘Canceled’ 
        //credit adjustment to be applied to write-off the balance due.
        [Description("WRITE_OFF")]
        WriteOff,

        [Description("CHANGE_OF_STATUS")]
        ChangeOfStatus,

        [Description("SALESTAX_CHANGES")]
        SalesTaxChanges,

        [Description("CHANGE_OF_BILLING_LOCATION")]
        ChangeOfBillingLocation,

        [Description("OVERWRITE_INVOICE_DUE_DAYS")]
        OverwriteInvoiceDueDays,

        [Description("OVERDUE_INVOICE_SENT")]
        OverDueInvoiceSent,

        [Description("INVOICE_RESENT")]
        InvoiceResent
    }    

    /// <summary>
    /// Class holds the event history details.
    /// </summary>      
    public class RequestEventHistoryDetails
    {
        #region Fields

        //holds the Request Id.
        private long requestId;

        //holds the Event Date.
        private Nullable<DateTime> eventDate;

        //holds the Request Event.
        private string requestEvent;

        //holds the originator.
        private string originator;

        //holds the originatorId.
        private long originatorId;

        //holds the event remarks.
        private string eventRemarks;

        //holds the event type
        private EventType eventType;

        #endregion

        #region Properties

        /// <summary>
        ///This property is used to get or sets the RequestId.
        /// </summary>
        public long RequestId
        {
            get { return requestId; }
            set { requestId = value; }
        }

        /// <summary>
        ///This property is used to get or sets the EventDate.
        /// </summary>
        public Nullable<DateTime> EventDate
        {
            get { return eventDate; }
            set { eventDate = value; }
        }

        /// <summary>
        ///This property is used to get or sets the RequestEvent.
        /// </summary>
        public string RequestEvent
        {
            get { return requestEvent; }
            set { requestEvent = value; }
        }

        /// <summary>
        ///This property is used to get or sets the Originator.
        /// </summary>
        public string Originator
        {
            get { return originator; }
            set { originator = value; }
        }

        /// <summary>
        ///This property is used to get or sets the OriginatorId.
        /// </summary>
        public long OriginatorId
        {
            get { return originatorId; }
            set { originatorId = value; }
        }

        /// <summary>
        ///This property is used to get or sets the EventRemarks.
        /// </summary>
        public string EventRemarks
        {
            get { return eventRemarks; }
            set { eventRemarks = value; }
        }

        /// <summary>
        /// This property is used to get or set the request event type
        /// </summary>
        public EventType EventType
        {
            get { return eventType; }
            set
            {
                eventType = value;                
            }
        }
        #endregion
    }
}
