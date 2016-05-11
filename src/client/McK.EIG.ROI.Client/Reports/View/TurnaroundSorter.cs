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
    /// TurnaroundSorter
    /// </summary>
    public class TurnaroundSorter : IReportSorter
    {
        #region Fields

        private string fromStatus;
        private string toStatus;
        private int columnCount;
        private int column;
        private int sortFieldIndex;

        #endregion

        #region Methods

        /// <summary>
        /// Prepares paramters to rpt file to apply sorting.
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
            
            columnCount = 6;
            
            string infoText = info.Text.Replace("\n", "");
            infoText = infoText.Replace("\r", "");

            if (string.Equals(infoText, ROIConstants.ROIUser, StringComparison.CurrentCultureIgnoreCase))
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
            if ((string.Equals(infoText, "# Logged", StringComparison.CurrentCultureIgnoreCase)
                || string.Equals(infoText, "# Received", StringComparison.CurrentCultureIgnoreCase)) && info.Name == "FromStatusValue1")
            {
                fromStatus = infoText;
                return SortFields(3);
            }

            if (string.Equals(infoText, "# Pended", StringComparison.CurrentCultureIgnoreCase)
                || string.Equals(infoText, "# Logged", StringComparison.CurrentCultureIgnoreCase)
                || string.Equals(infoText, "# Complete", StringComparison.CurrentCultureIgnoreCase)
                || string.Equals(infoText, "# Pre-bill", StringComparison.CurrentCultureIgnoreCase))
                
            {
                toStatus = infoText;
                return SortFields(4);
            }

            if (string.Equals(infoText, ROIConstants.AverageTat, StringComparison.CurrentCultureIgnoreCase))
            {
                return SortFields(5);
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

            string userText       = string.Empty;
            string reqTypeText    = string.Empty;
            string reqNameText    = string.Empty;
            string fromStatusText = string.Empty;
            string toStatustText  = string.Empty;
            string tatText        = string.Empty;

            switch (column)
            {
                case 0: userText       = ROIConstants.ROIUser; break;
                case 1: reqTypeText    = ROIConstants.RequestorType; break;
                case 2: reqNameText    = ROIConstants.RequestorName; break;
                case 3: fromStatusText = fromStatus; break;
                case 4: toStatustText  = toStatus; break;
                case 5: tatText        = ROIConstants.AverageTat; break;
            }

            crystalReportHt.Clear();
            crystalReportHt.Add(ROIConstants.ROIUserFormula, userText);
            crystalReportHt.Add(ROIConstants.RequestorTypeFormula, reqTypeText);
            crystalReportHt.Add(ROIConstants.RequestorNameFormula, reqNameText);
            crystalReportHt.Add(ROIConstants.FromStatusFormula, fromStatusText);
            crystalReportHt.Add(ROIConstants.ToStatusFormula, toStatustText);
            crystalReportHt.Add(ROIConstants.TatFormula, tatText);

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
