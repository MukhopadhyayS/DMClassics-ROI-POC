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
using System.Collections;
using System.Collections.Generic;
using System.Globalization;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Patient.Model
{
    [Serializable]
    public class PageDetails : BaseRecordItem
    {
        #region Fields

        private string imNetId;
        private int logicalPageNumber;
        private int contentCount;
        private int contentPageNumber;

        #endregion

        #region Constructor

        public PageDetails() {}

        public PageDetails(string imNetId, int pageNumber, int contentNumber)
            : this(imNetId, pageNumber, contentNumber, 1)
        {
        }

        public PageDetails(string imNetId, int pageNumber, int contentNumber, int contentPageNumber)
        {
            this.imNetId = imNetId.Trim();
            this.logicalPageNumber = pageNumber;
            this.contentCount = contentNumber;
            this.contentPageNumber = contentPageNumber;
        }

        #endregion

        #region Methods

        public override IComparable CompareProperty
        {
            get { return logicalPageNumber; }
        }

        public override string Key
        {
            get
            {
                return PageNumber.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + ContentPageNumber.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + IMNetId;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds imNetId of a page.
        /// </summary>
        public string IMNetId
        {
            get { return imNetId; }
            set { imNetId = value.Trim(); }
        }

        /// <summary>
        /// Holds the logical page number of a page within a document/version.
        /// </summary>
        public int PageNumber
        {
            get { return logicalPageNumber; }
            set  {  logicalPageNumber = value; }
        }

        /// <summary>
        /// Holds content page number of a imnet page.
        /// </summary>
        public int ContentPageNumber
        {
            get { return contentPageNumber; }
            set { contentPageNumber = value; }
        }

        /// <summary>
        /// Holds content count of an imnet page.
        /// </summary>
        public int ContentCount
        {
            get { return contentCount; }
            set { contentCount = value; }
        }

        public override string Name
        {
            get
            {
                return ROIConstants.PagePrefix +  PageNumber.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
        }

        #endregion
    }
}
