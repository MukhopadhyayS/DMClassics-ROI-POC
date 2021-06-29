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

using CrystalDecisions.CrystalReports.Engine;
using CrystalDecisions.Shared;
using CrystalDecisions.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// PostedPaymentSummarySorter
    /// </summary>
    public class PostedPaymentReportSorter : IReportSorter
    {
        #region Fileds

        private int columnCount;
        private int column;
        private int sortFieldIndex;

        #endregion

        #region Methods

        /// <summary>
        /// Prepares parameters to rpt file to apply sorting.
        /// </summary>
        /// <param name="info"></param>
        /// <param name="filterIndex"></param>
        /// <returns></returns>
        public Hashtable PrepareParams(ObjectInfo info, int filterIndex, string reportName)
        {
            if (string.IsNullOrEmpty(info.Text))
            {
                return null;
            }
            
            columnCount = 4;
            
            string infoText = info.Text.Replace("\n", "");
            infoText = infoText.Replace("\r", "");

            if (string.Equals(infoText, ROIConstants.BillingLocation, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(0);
            }
            if (string.Equals(infoText, ROIConstants.PostedBy, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(1);
            }
            if (string.Equals(infoText, ROIConstants.RequestorType, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(2);
            }
            if (string.Equals(infoText, ROIConstants.RequestorName, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(3);
            }           

            return null;
        }


        /// <summary>
        /// Sort Fields
        /// </summary>
        /// <param name="column"></param>
        /// <returns></returns>
        private Hashtable SortFields(int column)
        {
            this.column = column;
            this.sortFieldIndex = column;

            Hashtable crystalReportHt = new Hashtable();

            string facility    = string.Empty;
            string postedBy = string.Empty;
            string requestorType = string.Empty;
            string requestorName = string.Empty;
            
            switch (column)
            {
                case 0: facility        = ROIConstants.Facility; break;
                case 1: postedBy        = ROIConstants.PostedBy; break;
                case 2: requestorType   = ROIConstants.RequestorType; break;
                case 3: requestorName   = ROIConstants.RequestorName; break;                
            }

            crystalReportHt.Clear();
            crystalReportHt.Add(ROIConstants.FacilityFormula, facility);
            crystalReportHt.Add(ROIConstants.PostedByFormula, postedBy);
            crystalReportHt.Add(ROIConstants.RequestorTypeFormula, requestorType);
            crystalReportHt.Add(ROIConstants.RequestorNameFormula, requestorName);

            return crystalReportHt;

        }

        #endregion

        #region Properties

        /// <summary>
        /// Number of column count in the report
        /// </summary>
        public int ColumnSortCount
        {
            get { return columnCount; }
        }

        /// <summary>
        /// Selected column for sorting
        /// </summary>
        public int SelectedColumnIndex
        {
            get { return column; }
        }

        public int SortFieldIndex
        {
            get { return sortFieldIndex; }
        }

        #endregion
    }
}
