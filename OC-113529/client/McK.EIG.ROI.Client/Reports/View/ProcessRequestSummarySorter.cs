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
    /// ProcessRequestSummarySorter
    /// </summary>
    public class ProcessRequestSummarySorter : IReportSorter
    {
        #region Fields

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
            return null;
            if (string.IsNullOrEmpty(info.Text))
            {
                return null;
            }
           
            columnCount = 8;
            
            string infoText = info.Text.Replace("\n", "");
            infoText = infoText.Replace("\r", "");
            if (string.Equals(infoText, ROIConstants.Facility, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(0);
            }
            if (string.Equals(infoText, ROIConstants.RequestorType, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(1);
            }
            //if (string.Equals(infoText, ROIConstants.TotalProcessed, StringComparison.OrdinalIgnoreCase))
            //{
            //    return SortFields(2);
            //}
            if (string.Equals(infoText, ROIConstants.TotalCompleted, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(3);
            }
            if (string.Equals(infoText, ROIConstants.TotalPending, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(4);
            }
            if (string.Equals(infoText, ROIConstants.TotalCanceled, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(5);
            }
            if (string.Equals(infoText, ROIConstants.TotalDenied, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(6);
            }
            if (string.Equals(infoText, ROIConstants.TotalPreBill, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(7);
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

            string facility        = string.Empty;
            string requestorType   = string.Empty;            
            string totalProcessed  = string.Empty;
            string totalCompleted  = string.Empty;
            string totalPending    = string.Empty;
            string totalCanceled   = string.Empty;
            string totalDenied     = string.Empty;
            string totalPrebilled  = string.Empty;
            
            switch (column)
            {
                case 0: facility       = ROIConstants.Facility; break;
                case 1: requestorType  = ROIConstants.RequestorType; break;
                case 2: totalProcessed = ROIConstants.TotalProcessed; break;
                case 3: totalCompleted = ROIConstants.TotalCompleted; break;
                case 4: totalPending   = ROIConstants.TotalPending; break;
                case 5: totalCanceled  = ROIConstants.TotalCanceled; break;
                case 6: totalDenied    = ROIConstants.TotalDenied; break;
                case 7: totalPrebilled = ROIConstants.TotalPreBill; break;                
            }

            crystalReportHt.Clear();
            crystalReportHt.Add(ROIConstants.FacilityFormula,       facility);
            crystalReportHt.Add(ROIConstants.RequestorTypeFormula,  requestorType);
            crystalReportHt.Add(ROIConstants.TotalProcessedFormula, totalProcessed);
            crystalReportHt.Add(ROIConstants.TotalCompletedFormula, totalCompleted);
            crystalReportHt.Add(ROIConstants.TotalPendingFormula,   totalPending);
            crystalReportHt.Add(ROIConstants.TotalCanceledFormula,  totalCanceled);
            crystalReportHt.Add(ROIConstants.TotalDeniedFormula,    totalDenied);
            crystalReportHt.Add(ROIConstants.TotalPreBillFormula, totalPrebilled);
           
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
