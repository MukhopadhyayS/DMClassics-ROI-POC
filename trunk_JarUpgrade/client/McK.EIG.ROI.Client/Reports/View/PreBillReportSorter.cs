using System;
using System.Collections;

using CrystalDecisions.CrystalReports.Engine;
using CrystalDecisions.Shared;
using CrystalDecisions.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Reports.View
{
    class PreBillReportSorter : IReportSorter
    {

        private int columnCount;
        private int column;
        private int sortFieldIndex;
        private const string firstReport = "PreBillReport_0.rpt";

        public Hashtable PrepareParams(ObjectInfo info, int filterIndex, string reportName)
        {
            return null;
            if (string.IsNullOrEmpty(info.Text))
            {
                return null;
            }

            columnCount = 2;

            string infoText = info.Text.Replace("\n", "");
            infoText = infoText.Replace("\r", "");

            if (string.Equals(infoText, ROIConstants.RequestorType, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(0);

            }
            if (string.Equals(infoText, ROIConstants.RequestStatus, StringComparison.OrdinalIgnoreCase))
            {
                return SortFields(1);
            }
            return null;

        }
        private Hashtable SortFields(int column)
        {
            this.column = column;
            this.sortFieldIndex = column;

            Hashtable crystalReportHt = new Hashtable();

            string requestorTypeText = string.Empty;

            switch (column)
            {
                case 0: requestorTypeText = ROIConstants.RequestorType; break;
            }

            crystalReportHt.Clear();
            crystalReportHt.Add(ROIConstants.RequestorTypeFormula, requestorTypeText);

            return crystalReportHt;
        }
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
    }
}