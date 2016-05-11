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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
    public enum ReasonType
    {
        Adjustment = 0,
        Request = 1,
        Status = 2
    }

    public enum RequestStatus
    {
        [Description("Unknown")]
        Unknown = -1,
        [Description("Please Select")]
        None = 0,
        [Description("Auth-Received")]
        AuthReceived = 1,        
        [Description("Logged")]
        Logged = 2,
        [Description("Completed")]
        Completed = 3,
        [Description("Denied")]
        Denied = 4,
        [Description("Canceled")]
        Canceled = 5,
        [Description("Pended")]
        Pended = 6,
        [Description("Pre-Billed")]
        PreBilled = 7,
        [Description("Auth Received Denied")]
        AuthReceivedDenied = 8
    }

    public enum RequestAttr
    {
        [Description("TPO - Treatment Payment Operation")]
        Tpo = 0,
        [Description("Non TPO - Non Treatment Payment Operation")]
        NonTpo = 1
    }

    [Serializable]
    public class ReasonDetails : ROIModel
    {
        #region Fields

        //Request Reason        
        public const string RequestListTpo = "TPO";
        public const string RequestListNonTpo = "Non-TPO";
        public const string RequestListBoth = "TPO and Non-TPO";

        private long id;
        private string name;
        private string displayText;
        private ReasonType type;
        private RequestAttr attribute;
        private RequestStatus requestStatus;
        private string attributeName;
        private string requestStatusText;

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or set the request reason id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or set the request reason name.
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public string DisplayText
        {
            get { return CarriageReturn(displayText); }
            set { displayText = value; }
        }

        /// <summary>
        /// This property is used to get or set the request reason type.
        /// </summary>
        public ReasonType Type
        {
            get { return type; }
            set { type = value; }
        }

        /// <summary>
        /// This property is used to get or set the request reason attribute.
        /// </summary>
        public RequestAttr Attribute
        {
            get { return attribute; }
            set
            {
                attribute = value;

                switch (attribute)
                {
                    case RequestAttr.Tpo: attributeName = RequestListTpo; break;
                    case RequestAttr.NonTpo: attributeName = RequestListNonTpo; break;                    
                }
            }
        }

        /// <summary>
        /// This property is used to get or set the request reason attribute in grid.
        /// </summary>
        public string AttributeName
        {
            get { return attributeName; }
            set { attributeName = value; }
        }

        /// <summary>
        /// This property is used to get or set the request reason status.
        /// </summary>
        public RequestStatus RequestStatus
        {
            get { return requestStatus; }
            set
            {
                requestStatus = value;
                requestStatusText = EnumUtilities.GetDescription(requestStatus);
            }
        }

        public string RequestStatusText
        {
            get { return requestStatusText; }
            set { requestStatusText = value; }
        }

        #endregion
    }
}
