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
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using System.Collections.Generic;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// RequestPageDetails
    /// </summary>
    [Serializable]
    public class RequestPageDetails : BaseRequestItem
    {
        [Serializable]
        private class RequestPageDetailsSorter : StringLogicalComparer
        {
            #region IComparer<RequestPageDetailsSorter> Members

            public override int Compare(string x, string y)
            {
                return base.Compare(x, y);
            }

            #endregion
        }

        #region Fields

        private static RequestPageDetailsSorter sorter;
        private const string NumberKey             = "number";
        private const string IMNetIdKey            = "imnet-id";
        private const string ContentCountKey       = "content-count";
        private const string SelectedForReleaseKey = "selected-for-release";
        private const string IsReleasedKey         = "is-released";
        private const string PageElement = "<{0} {1}=\"{2}\" {3}=\"{4}\" {5}=\"{6}\" {7}=\"{8}\" {9}=\"{10}\"/>";
        
        private string imNetId;
        private int pageNumber;
        private long patientSeq; //To use the patient sequence for the global documents pages
        private long versionSeq;
        private long pageSeq;
        private long contentCount;
        private List<RequestPageDetails> pageDetails;
        private bool? selectedForRelease;
        private bool isGloablDocumentPage;
        private int pageNumberRequested;

        #endregion

        #region Constructor

        public RequestPageDetails() { }

        public RequestPageDetails(PageDetails recordPage)
        {
            IMNetId = recordPage.IMNetId.Trim();
            PageNumber = recordPage.PageNumber;
            ContentCount = recordPage.ContentCount;
            PageNumberRequested = recordPage.ContentPageNumber;
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

        public List<RequestPageDetails> PageDetails
        {
            get { return pageDetails; }
            set { pageDetails = value; }
        }

        /// <summary>
        /// Holds page number of a page.
        /// </summary>
        public int PageNumber
        {
            get { return pageNumber; }
            set { pageNumber = value; }
        }

        /// <summary>
        /// Holds content page number requested.
        /// </summary>
        public int PageNumberRequested
        {
            get { return pageNumberRequested; }
            set { pageNumberRequested = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient sequence of the global documents pages.
        /// </summary>
        public long PatientSeq
        {
            get { return patientSeq; }
            set { patientSeq = value; }
        }

        /// <summary>
        /// Holds the version sequence.
        /// </summary>
        public long VersionSeq
        {
            get { return versionSeq; }
            set { versionSeq = value; }
        }

        /// <summary>
        /// Holds the page sequence;
        /// </summary>
        public long PageSeq
        {
            get { return pageSeq; }
            set { pageSeq = value; }
        }
        /// <summary>
        /// Holds content number of a page.
        /// </summary>
        public long ContentCount
        {
            get { return contentCount; }
            set { contentCount = value; }
        }

        public override Nullable<bool> SelectedForRelease
        {
            get { return selectedForRelease; }
            set { selectedForRelease = (value.Value) ? true : false; }
        }

        /// <summary>
        /// Holds the isGlobalDocumentPage
        /// </summary>
        public bool IsGlobalDocumentPage
        {
            get { return isGloablDocumentPage; }
            set { isGloablDocumentPage = value; }
        }

        public override string Name
        {
            get { return ROIConstants.PagePrefix + PageNumber.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }

        public override string Key
        {
            get { return PageNumber.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + PageNumberRequested.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + "." + IMNetId; }
        }

        public static IComparer<string> RequestPageSorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new RequestPageDetailsSorter();
                }
                return sorter;
            }
        }

        #endregion
    }
}
