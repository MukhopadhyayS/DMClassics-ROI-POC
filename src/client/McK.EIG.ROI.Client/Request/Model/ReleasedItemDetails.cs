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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Class holds the relased item information
    /// </summary>
    [Serializable]
    public class ReleasedItemDetails
    {
        #region Fields

        private Nullable<DateTime> releasedDate;

        private string encounter;

        private string documentVersionSubtitle;

        private string pages;

        private string shippingAddress;

        private string shippingMethod;

        private string trackingNumber;

        private long userId;

        private string userName;

        private ReleasedPatientDetails releasePatient;

        private string patientName;

        private int pageCount;

        private string queueSecure;

        private string requestSecure;

        //private bool isPrintable;

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the released date.
        /// </summary>
        public Nullable<DateTime> ReleasedDate
        {
            get { return releasedDate; }
            set { releasedDate = value; }
        }

        /// <summary>
        /// Gets or sets the encounter.
        /// </summary>
        public string Encounter
        {
            get { return encounter; }
            set { encounter = value; }
        }

        /// <summary>
        /// Gets or sets the document information.
        /// </summary>
        public string DocumentVersionSubtitle
        {
            get { return documentVersionSubtitle; }
            set { documentVersionSubtitle = value; }
        }

        /// <summary>
        /// Gets or sets the document pages released.
        /// </summary>
        public string Pages
        {
            get { return pages; }
            set { pages = value; }
        }

        /// <summary>
        /// Gets or sets the shipping address.
        /// </summary>
        public string ShippingAddress
        {
            get { return ROIModel.CarriageReturn(shippingAddress ?? string.Empty); }
            set { shippingAddress = value; }
        }

        /// <summary>
        /// Gets or sets the shipping method.
        /// </summary>
        public string ShippingMethod
        {
            get { return shippingMethod; }
            set { shippingMethod = value; }
        }

        /// <summary>
        /// Gets or sets the traking number.
        /// </summary>
        public string TrackingNumber
        {
            get { return trackingNumber; }
            set { trackingNumber = value; }
        }

        /// <summary>
        /// Gets or sets the user id.
        /// </summary>
        public long UserId
        {
            get { return userId; }
            set { userId = value; }
        }

        /// <summary>
        /// Gets or sets the user name.
        /// </summary>
        public string UserName
        {
            get { return userName; }
            set { userName = value; }
        }

        /// <summary>
        /// Gets or sets the patient who released.
        /// </summary>
        public ReleasedPatientDetails ReleasePatient
        {
            get { return releasePatient; }
            set { releasePatient = value; }
        }

        /// <summary>
        /// Gets or sets the patient key of id, mrn and facility.
        /// </summary>
        public string Key
        {
            get { return ReleasePatient.Key; }
        }

        /// <summary>
        /// Gets or sets the patient name.
        /// </summary>
        public string PatientName
        {
            get { return patientName; }
            set { patientName = value; }
        }

        /// <summary>
        /// Gets or sets the page count for the release.
        /// </summary>
        public int PageCount
        {
            get { return pageCount; }
            set { pageCount = value; }
        }

        /// <summary>
        /// Gets or sets the request password
        /// </summary>
        public string RequestSecretWord
        {
            get { return requestSecure; }
            set { requestSecure = value; }
        }

        /// <summary>
        /// Gets or sets the queue password
        /// </summary>
        public string QueueSecretWord
        {
            get { return queueSecure; }
            set { queueSecure = value; }
        }

        public string PlainQueueSecretWord
        {
            get { return ROIController.DecryptAES(queueSecure); }
            set { queueSecure = ROIController.EncryptAES(value); }
        }

        public string PlainRequestSecretWord
        {
            get { return ROIController.DecryptAES(requestSecure); }
            set { requestSecure = ROIController.EncryptAES(value); }
        }


        /// <summary>
        /// Gets or sets the attachment is printable or not
        ///// </summary>
        //public bool IsPrintable
        //{
        //    get { return isPrintable; }
        //    set { isPrintable = value; }
        //}

        #endregion
    }
}
