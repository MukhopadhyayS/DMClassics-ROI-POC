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
    /// OutstandingInvoiceBalanceSorter
    /// </summary>
    public class OutstandingInvoiceBalanceSorter : IReportSorter
    {
        #region Fields

        private int columnCount;
        private int column;
        private int sortFieldIndex;
        private string reportFileName;
        private const string firstReport = "OutstandingInvoiceBalance_0.rpt";
        private const string secondReport = "OutstandingInvoiceBalance_1.rpt";

        #endregion

        #region Methods

        public Hashtable PrepareParams(ObjectInfo info, int filterIndex, string reportName)
        {
            if (string.IsNullOrEmpty(info.Text))
            {
                return null;
            }

            columnCount = (filterIndex == 0) ? 3 : 10;
            this.reportFileName = reportName;            
            
            string infoText = info.Text.Replace("\n", "");
            infoText = infoText.Replace("\r", "");
            if (infoText.Equals("Invoice#"))
            {
                infoText = "Invoice No.";
            }
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
                //if (string.Equals(infoText, ROIConstants.InvoiceDate, StringComparison.CurrentCultureIgnoreCase))
                //{
                //    return SortFields(3);
                //}
                if (string.Equals(infoText, ROIConstants.InvoiceNo, StringComparison.CurrentCultureIgnoreCase))
                {   
                    return SortFields(6);
                }
                if (string.Equals(infoText, ROIConstants.TotalCost, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(5);
                }
                if (string.Equals(infoText, ROIConstants.TotalAmountPaid, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(6);
                }
                if (string.Equals(infoText, ROIConstants.BalanceDue, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(7);
                }
                if (string.Equals(infoText, ROIConstants.DaysOutstanding, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(8);
                }
                if (string.Equals(infoText, ROIConstants.RequestId, StringComparison.CurrentCultureIgnoreCase))
                {
                    return SortFields(5);
                }
            }
            else
            {
                Hashtable crystalReportHt = new Hashtable();

                if (string.Equals(infoText, ROIConstants.Facility, StringComparison.OrdinalIgnoreCase))
                {
                    this.column = 0;
                    this.sortFieldIndex = column;
                    crystalReportHt.Add(ROIConstants.FacilityFormula, ROIConstants.Facility);
                    return crystalReportHt;
                }

                if (string.Equals(infoText, ROIConstants.RequestorType, StringComparison.OrdinalIgnoreCase))
                {
                    this.column = 1;                    
                    this.sortFieldIndex = column;
                    crystalReportHt.Add(ROIConstants.RequestorTypeFormula, ROIConstants.RequestorType);
                    return crystalReportHt;
                }
            }
            return null;
        }

        private Hashtable SortFields(int column)
        {
            this.column = column;
            this.sortFieldIndex = column;
           
            Hashtable crystalReportHt = new Hashtable();

            string facility        = string.Empty;
            string requestorType   = string.Empty;
            string requestorName   = string.Empty;
            string invoiceDate     = string.Empty;
            string invoiceNo       = string.Empty;
            string totalCost       = string.Empty;
            string totalAmount     = string.Empty;
            string balanceDue      = string.Empty;
            string daysOutStanding = string.Empty;
            string requestId       = string.Empty;
           
            switch (column)
            {
                case 0: facility        = ROIConstants.Facility; break;
                case 1: requestorType   = ROIConstants.RequestorType; break;
                case 2: requestorName   = ROIConstants.RequestorName; break;
                case 3: invoiceDate     = ROIConstants.InvoiceDate; break;
                case 4: invoiceNo       = ROIConstants.InvoiceNo; break;
                case 5: totalCost       = ROIConstants.TotalCost; break;
                case 6: totalAmount     = ROIConstants.TotalAmountPaid; break;
                case 7: balanceDue      = ROIConstants.BalanceDue; break;
                case 8: daysOutStanding = ROIConstants.DaysOutstanding; break;
                case 9: requestId       = ROIConstants.RequestId; break;
            }

            crystalReportHt.Clear();

            crystalReportHt.Add(ROIConstants.FacilityFormula, facility);
            crystalReportHt.Add(ROIConstants.RequestorTypeFormula, requestorType);
            crystalReportHt.Add(ROIConstants.RequestorNameFormula, requestorName);
            crystalReportHt.Add(ROIConstants.InvoiceDateFormula, invoiceDate);
            crystalReportHt.Add(ROIConstants.InvoiceNoFormula, invoiceNo);
            crystalReportHt.Add(ROIConstants.TotalCostFormula, totalCost);
            crystalReportHt.Add(ROIConstants.TotalAmountPaidFormula, totalAmount);
            crystalReportHt.Add(ROIConstants.BalanceDueFormula, balanceDue);
            crystalReportHt.Add(ROIConstants.DaysOutstandingFormula, daysOutStanding);
            crystalReportHt.Add(ROIConstants.RequestIdFormula, requestId);

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
