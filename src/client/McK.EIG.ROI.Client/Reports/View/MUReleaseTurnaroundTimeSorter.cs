using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using CrystalDecisions.CrystalReports.Engine;
using CrystalDecisions.Shared;
using CrystalDecisions.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Reports.View
{
    class MUReleaseTurnaroundTimeSorter : IReportSorter
    {
        #region Fields

        private int columnCount;
        private int column;
        private int sortFieldIndex;

        #endregion

        #region Methods

        // <summary>
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

            columnCount = 2;

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
            if (string.Equals(infoText, ROIConstants.TAHours, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(0);
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

            string facility = string.Empty;
            string TAhours = string.Empty;

            switch (column)
            {
                case 0: facility = ROIConstants.Facility; break;
                case 3: TAhours = ROIConstants.TAHours; break;

            }

            crystalReportHt.Clear();
            crystalReportHt.Add(ROIConstants.FacilityFormula, facility);
            crystalReportHt.Add(ROIConstants.TAHoursFormula, TAhours);

            return crystalReportHt;
        }

        #endregion

        #region Fields
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
