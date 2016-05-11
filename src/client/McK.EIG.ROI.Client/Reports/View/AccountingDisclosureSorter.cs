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
    /// AccountingDisclosureSorter
    /// </summary>
    public class AccountingDisclosureSorter : IReportSorter
    {
        #region Fields

        private int columnCount;
        private int column;
        private int sortFieldIndex;
        private string reportFileName;
        private const string firstReport = "AccountingDisclosure_0.rpt";

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

            columnCount = 5;
            this.reportFileName = reportName; 
            string infoText = info.Text.Replace("\n", "");
            infoText = infoText.Replace("\r", "").Trim();

            if (string.Equals(infoText, ROIConstants.DisclosureDate, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(0);
            }
            if (string.Equals(infoText, ROIConstants.Facility, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(1);
            }
            if (string.Equals(infoText, ROIConstants.Mrn, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(2);
            }
            if (string.Equals(infoText, ROIConstants.RequestorName, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(3);
            }

            if (string.Equals(infoText, ROIConstants.RequestReason, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(4);
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

            string disclosureDate  = string.Empty;
            string facility        = string.Empty;
            string mrn             = string.Empty;
            string requestorName   = string.Empty;
            string requestReason   = string.Empty;            
            
            switch (column)
            {
                case 0: disclosureDate = ROIConstants.DisclosureDate; break;
                case 1: facility = ROIConstants.Facility; break;
                case 2: mrn = ROIConstants.Mrn; break;
                case 3: requestorName = ROIConstants.RequestorName; break;
                case 4: requestReason = ROIConstants.RequestReason; break;
            }

            crystalReportHt.Clear();
            crystalReportHt.Add(ROIConstants.FacilityFormula, facility);
            crystalReportHt.Add(ROIConstants.DisclosureDateFormula, disclosureDate);
            crystalReportHt.Add(ROIConstants.MrnFormula, mrn);
            crystalReportHt.Add(ROIConstants.RequestorNameFormula, requestorName);
            crystalReportHt.Add(ROIConstants.RequestReasonFormula, requestReason);            
           
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
                this.sortFieldIndex = column + 3;
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

