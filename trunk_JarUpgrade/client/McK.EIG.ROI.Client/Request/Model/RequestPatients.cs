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

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// RequestPatients
    /// </summary>
    [Serializable]
    public class RequestPatients 
    {
        #region Fields

        private List<RequestPatientDetails> requestPatients;
        private Dictionary<long, bool> pageStatus;
        private Dictionary<long, bool> nonHpfDocumentStatus;
        private Dictionary<long, bool> attachmentStatus;

        #endregion

        #region Constructors

        public RequestPatients() { }

        #endregion

        #region Properties

        public List<RequestPatientDetails> RequestPatientList
        {
            get
            {
                if (requestPatients == null)
                {
                    requestPatients = new List<RequestPatientDetails>();
                }
                return requestPatients;
            }
            set { requestPatients = value; }
        }

        public Dictionary<long, bool> PageStatus
        {
            get
            {
                if (pageStatus == null)
                {
                    pageStatus = new Dictionary<long, bool>();
                }
                return pageStatus;
            }
            set { pageStatus = value; }
        }

        public Dictionary<long, bool> NonHpfDocumentStatus
        {
            get
            {
                if (nonHpfDocumentStatus == null)
                {
                    nonHpfDocumentStatus = new Dictionary<long, bool>();
                }
                return nonHpfDocumentStatus;
            }
            set { nonHpfDocumentStatus = value; }
        }

        public Dictionary<long, bool> AttachmentStatus
        {
            get
            {
                if (attachmentStatus == null)
                {
                    attachmentStatus = new Dictionary<long, bool>();
                }
                return attachmentStatus;
            }
            set { attachmentStatus = value; }
        }

        #endregion
    }
}

