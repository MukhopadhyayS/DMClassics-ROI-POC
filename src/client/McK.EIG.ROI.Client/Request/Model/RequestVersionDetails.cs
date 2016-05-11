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
    /// Defines version details of a document.
    /// </summary>
    [Serializable]
    public class RequestVersionDetails : BaseRequestItem
    {
        [Serializable]
        private class RequestVersionSorter : StringLogicalComparer
        {
            public override int Compare(string x, string y)
            {
                return -1 * base.Compare(x, y);
            }
        }

        #region Fields

        private static RequestVersionSorter sorter;
        private const string NumberKey = "number";
        private const string VersionElement = "<{0} {1}=\"{2}\">";
        private long patientSeq; // To use the patient sequence for the global document version.
        private long documentId;
        private long versionNumber;
        private long documentSeq;
        private long versionSeq;
        private bool isGlobalDocumentVersion;

        private List<RequestPageDetails> requestPage;

        #endregion

        #region Constructor

        public RequestVersionDetails() { }

        public RequestVersionDetails(VersionDetails recordVersion)
        {
            versionNumber = recordVersion.VersionNumber;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Holds the Version Number.
        /// </summary>
        public long VersionNumber
        {
            get
            {
                return (versionNumber == 0) ? 9999999 : versionNumber;
            }
            set { versionNumber = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient sequence of the global documents version.
        /// </summary>
        public long PatientSeq
        {
            get { return patientSeq; }
            set { patientSeq = value; }
        }

        /// <summary>
        /// Holds the document Id
        /// </summary>
        public long DocumentId
        {
            get { return documentId; }
            set { documentId = value; }
        }

        /// <summary>
        /// Holds the document seq
        /// </summary>
        public long DocumentSeq            
        {
            get { return documentSeq; }
            set { documentSeq = value; }
        }

        /// <summary>
        /// Holds the Version Number Sequence.
        /// </summary>
        public long VersionSeq            
        {
            get { return versionSeq; }
            set { versionSeq = value; }
        }

        /// <summary>
        /// Holds the isGlobalDocumentVersion
        /// </summary>
        public bool IsGlobalDocumentVersion
        {
            get { return isGlobalDocumentVersion; }
            set { isGlobalDocumentVersion = value; }
        }

        /// <summary>
        /// Holds the Version Name.
        /// </summary>
        public override string Name
        {
            get
            {
                if (VersionNumber == 9999999)
                {
                    return ROIConstants.VersionPrefix + "0";
                }
                return ROIConstants.VersionPrefix + VersionNumber.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
        }


        /// <summary>
        /// Holds the Version Key.
        /// </summary>
        public override string Key
        {
            get { return VersionNumber + ""; }
        }

        /// <summary>
        /// Returns only released version
        /// </summary>
        public RequestVersionDetails ReleasedItems
        {
            get
            {
                List<BaseRequestItem> pages = new List<BaseRequestItem>(GetChildren);
                pages = pages.FindAll(delegate(BaseRequestItem page) 
                                      {
                                          if (!page.IsReleased && page.SelectedForRelease.HasValue)
                                          {
                                              page.IsReleased =  page.SelectedForRelease.Value;
                                          }
                                          return (!page.SelectedForRelease.HasValue || page.SelectedForRelease.Value); 
                                      });
                RequestVersionDetails version = Clone();//(RequestVersionDetails)ROIViewUtility.DeepClone(this);
                foreach (RequestPageDetails page in pages)
                {
                    version.AddChild(page);
                }

                return version;
            }
        }

        public override IComparer<string> DefaultSorter
        {
            get
            {
                return RequestPageDetails.RequestPageSorter;
            }
        }

        private RequestVersionDetails Clone()
        {
            RequestVersionDetails version = new RequestVersionDetails();
            version.VersionNumber      = versionNumber;
            version.SelectedForRelease = SelectedForRelease;
            version.RecordVersionId    = RecordVersionId;
            version.IsGlobalDocumentVersion = IsGlobalDocumentVersion;
            version.PatientSeq = PatientSeq;
            return version;
        }


        public static IComparer<string> CustomSorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new RequestVersionSorter();
                }
                return sorter;
            }
        }
        #endregion        
    }
}
