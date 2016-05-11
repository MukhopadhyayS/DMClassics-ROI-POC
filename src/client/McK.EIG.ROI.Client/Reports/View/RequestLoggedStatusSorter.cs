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
    /// RequestLoggedStatusSorter
    /// </summary>
    public class RequestLoggedStatusSorter : IReportSorter
    {
        #region Fields

        private int columnCount;
        private int column;
        private int sortFieldIndex;
        private string reportFileName;
        private const string firstReport = "RequestWithLoggedStatus_0.rpt";
        private const string secondReport = "RequestWithLoggedStatus_1.rpt";

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

            if (filterIndex == 2) return null;

            columnCount = (filterIndex == 0)? 1 : 5;
            this.reportFileName = reportName;             
            
            string infoText = info.Text.Replace("\n", "");
            infoText = infoText.Replace("\r", "");
            if (filterIndex != 0)
            {
                if (string.Equals(infoText, ROIConstants.Facility, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(0);
                }
                if (string.Equals(infoText, ROIConstants.RequestorType, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(1);
                }
                if (string.Equals(infoText, ROIConstants.RequestorName, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(2);
                }
                if (string.Equals(infoText, ROIConstants.DateCreated, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(3);
                }
                if (string.Equals(infoText, ROIConstants.RequestId, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(4);
                }
            }
            else
            {
                Hashtable crystalReportHt = new Hashtable();

                if (string.Equals(infoText, ROIConstants.RequestorType, StringComparison.CurrentCultureIgnoreCase))
                {
                    this.column = 0;
                    crystalReportHt.Add(ROIConstants.RequestorTypeFormula, ROIConstants.RequestorType);
                    CalculateSortFieldIndex(reportFileName);
                }

                return crystalReportHt;
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
            CalculateSortFieldIndex(reportFileName);

            Hashtable crystalReportHt = new Hashtable();

            string facility      = string.Empty;
            string requestorType = string.Empty;            
            string requestorName = string.Empty;
            string dateCreated   = string.Empty;
            string requestId     = string.Empty;
                        
            
            switch (column)
            {
                case 0: facility      = ROIConstants.Facility; break;
                case 1: requestorType = ROIConstants.RequestorType; break;
                case 2: requestorName = ROIConstants.RequestorName; break;
                case 3: dateCreated   = ROIConstants.DateCreated; break;
                case 4: requestId     = ROIConstants.RequestId; break;            
            }

            crystalReportHt.Clear();
            crystalReportHt.Add(ROIConstants.FacilityFormula, facility);
            crystalReportHt.Add(ROIConstants.RequestorTypeFormula, requestorType);
            crystalReportHt.Add(ROIConstants.RequestorNameFormula, requestorName);
            crystalReportHt.Add(ROIConstants.DateCreatedFormula, dateCreated);
            crystalReportHt.Add(ROIConstants.RequestIdFormula, requestId);
           
            return crystalReportHt;

        }

        /// <summary>
        /// Method used to calculate sort field index.
        /// </summary>
        /// <param name="reportFileName"></param>
        private void CalculateSortFieldIndex(string reportFileName)
        {
            if (reportFileName.Equals(firstReport))
            {
                this.sortFieldIndex = column + 1;
            }
            else
            {
                this.sortFieldIndex = column + 2;
            }            
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
