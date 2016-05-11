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
    /// <summary>
    /// Defines version details of a document.
    /// </summary>
    [Serializable]
    public class VersionDetails : BaseRecordItem
    {
        [Serializable]
        private class VersionSorter : IComparer<int>
        {
            public virtual int Compare(int x, int y)
            {
                return -1 * x.CompareTo(y);
            }
        }

        #region Fields

        private static VersionSorter sorter;
        private int versionNumber;
        private SortedList<int, PageDetails> pages;

        #endregion


        #region Constructor

        /// <summary>
        /// VersionDetails
        /// </summary>
        public VersionDetails() {}

        /// <summary>
        /// VersionDetails
        /// </summary>
        /// <param name="versionNumber"></param>
        public VersionDetails(int versionNumber) 
        {
            VersionNumber = versionNumber;
        }
      
        #endregion

        #region Properties

        /// <summary>
        /// Holds version number.
        /// </summary>
        public int VersionNumber
        {
            get 
            { 
                return (versionNumber == 0) ? 9999999 : versionNumber ; 
            }
            set { versionNumber = value; }
        }

        /// <summary>
        /// Holds collections of pages.
        /// </summary>
        public SortedList<int, PageDetails> Pages
        {
            get
            {
                if (pages == null)
                {
                    pages = new SortedList<int, PageDetails>();
                }

                return pages;
            }            
        }

        /// <summary>
        /// Name
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
        /// CompareProperty
        /// </summary>
        public override IComparable CompareProperty
        {
            get { return VersionNumber; }
        }

        /// <summary>
        /// Key
        /// </summary>
        public override string Key
        {
            get { return Convert.ToString(VersionNumber, System.Threading.Thread.CurrentThread.CurrentUICulture); }
        }

        public static IComparer<int> CustomSorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new VersionSorter();
                }
                return sorter;
            }
        }

        #endregion

    }
}
