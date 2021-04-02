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

namespace McK.EIG.ROI.Client.Patient.Model
{
    /// <summary>
    /// Model contains Deficiency Details.
    /// </summary>
    public class DeficiencyDetails
    {
        #region Fields

        private string physician;
        private string type;
        private string document;
        private string reason;
        private string status;
        private string lockedBy;
        private bool   isLinked;

        #endregion

        #region Properties

        /// <summary>
        /// Holds physician detail
        /// </summary>
        public string Physician
        {
            get { return physician; }
            set 
            { 
                physician = value;
                if (physician != null)
                {
                    isLinked = (physician.Trim().Length > 0);
                }
            }
        }

        /// <summary>
        /// Holds deficiency type detail
        /// </summary>
        public string Type
        {
            get { return type; }
            set { type = value; }
        }

        /// <summary>
        /// Holds document 
        /// </summary>
        public string Document
        {
            get { return document; }
            set { document = value; }
        }

        /// <summary>
        /// Holds reason detail.
        /// </summary>
        public string Reason
        {
            get { return reason; }
            set { reason = value; }
        }

        /// <summary>
        /// Holds status detail
        /// </summary>
        public string Status
        {
            get { return status; }
            set { status = value; }
        }

        /// <summary>
        /// Holds locked by detail
        /// </summary>
        public string LockedBy
        {
            get { return lockedBy; }
            set { lockedBy = value; }
        }

        /// <summary>
        /// Holds linked state.
        /// </summary>
        public bool IsLinked
        {
            get { return isLinked; }
            set { isLinked = value; }
        }

        #endregion
    }
}
