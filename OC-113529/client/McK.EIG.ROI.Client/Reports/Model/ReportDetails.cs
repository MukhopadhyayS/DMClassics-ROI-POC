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
using System.ComponentModel;

namespace McK.EIG.ROI.Client.Reports.Model
{
    /// <summary>
    /// Defines list of all reports
    /// </summary>
    public enum ReportType
    {
        [Description("--Select Report--")]
        None = 0,
        [Description("Accounting of Disclosure")]
        AccountingDisclosure = 1,
        [Description("Account Receivable Aging")]
        AccountReceivableAging = 2,
        [Description("Documents Released by MRN")]
        DocumentsReleasedByMRN = 3,
        [Description("Request Status Summary")]
        RequestStatusSummary = 4,
        [Description("Request Detail Report")]
        RequestDetailReport = 5,
        [Description("Outstanding Invoice Balances")]
        OutstandingInvoiceBalance = 6,
        [Description("Pending Aging Summary")]
        PendingAgingSummary = 7,
        [Description("Requests with Logged Status")]
        RequestWithLoggedStatus = 8,
        [Description("Processed Request Summary")]
        ProcessRequestSummary = 9,
        [Description("Posted Payments Report")]
        PostedPaymentsReport = 10,
        [Description("Turn Around Times")]
        TurnaroundTimes = 11,
        [Description("Sales Tax Report")]
        SalesTaxReport = 12,
        //CR#377839
		[Description("MU Release Turn Around Times Report")]
        MUReleaseTurnaroundTimeReport = 13,
        [Description("Prebill Report")]
        PrebillReport = 14,
        [Description("Productivity Report")]
        ProductivityReport = 15,
        [Description("Billable/Unbillable Report")]
        BillableAndUnBillableReport = 16,
        [Description("ROI Billing Report")]
        ROIBillingReport = 17,
    }

    /// <summary>
    /// List of filter types of Report type "Requests with Logged Status"
    /// </summary>
    public enum ReportLoggedStatusFilter 
    {
        [Description("Requestor Type")]
        RequestorType = 0,
        [Description("Requestor Type, Requestor Name")]
        RequestorName = 1,
        [Description("Requestor Type, Requestor Name, Encounter")]
        Encounter = 2,
        [Description("Summary")]
        Summary = 3
    }

    /// <summary>
    /// List of filter types of Report type "Request details"
    /// </summary>
    public enum RequestDetailsFilter
    {
        [Description("Requestor Type")]
        RequestorType = 0,
        [Description("Requestor Type, Encounter")]
        Encounter = 1,
        [Description("Requestor Type, Encounter & Request ID")]
        RequestId = 2,
        [Description("Summary")]
        Summary = 3,
    }

    /// <summary>
    /// List of filter types of Report type "Pending Aging Summary"
    /// </summary>
    public enum PendingAgingSummaryFilter
    {
        [Description("Requestor Type")]
        RequestorType = 0,
        [Description("Requestor Type, Requestor Name")]
        RequestorName = 1,
    }
   
    /// <summary>
    /// List of filter types of Report type "Requests Status Summary"
    /// </summary>
    public enum RequestStatusSummaryFilter
    {
        [Description("Requestor Type")]
        RequestorType = 0,
        [Description("Requestor Type, Requestor Name")]
        RequestorName = 1,
        [Description("Requestor Type, Requestor Name, Request ID")]
        RequestId = 2, 
        [Description("Summary")]
        Summary = 3
    }

    /// <summary>
    /// List of filter types of Report type "Outstanding Invoice Balance"
    /// </summary>
    public enum OutstandingInvoiceBalanceFilter
    {

        [Description("Summary - Requestor Type")]
        RequestorName = 0,
        [Description("Detailed - Requestor Name")]
        InvoiceDate = 1,
        [Description("Detailed - Request ID")]
        PatientName = 2,
        [Description("Detailed - Patient Name")]
        InvoiceNo = 3,
    }

    /// <summary>
    /// List of filter types of Report type "Account Receivable Aging"
    /// </summary>
    public enum AccountReceivableAgingFilter
    {
        [Description("Requestor Name")]
        RequestorName = 0,
        [Description("Requestor Name, Aging")]
        Aging = 1,
    }

    /// <summary>
    /// List of filter types of Report type "Posted Payments summary"
    /// </summary>
    public enum PostedPaymentReportFilter
    {
        [Description("Posted By")]
        PostedBy = 0,
        [Description("Posted By, Billing Location")]
        BillingLocation = 1,
    }

    public enum TurnaroundTimeFilter
    {
        [Description("Summary - Requestor Type")]
        Summary = 0,
        [Description("Detailed - Request ID")]
        Detail = 1,       
    }

    public enum SalesTaxReportFilter
    {
        [Description("Summary-Facility")]
        SummaryByFacility = 0,
        [Description("Detailed by-Requestor")]
        DetailedByRequestor = 1,
        [Description("Detailed by-Invoice")]
        DetailedByInvoice = 2,
    }

    public enum ProcessedRequestSummaryFilter
    {
        [Description("Requestor Type")]
        RequestorType = 0,

        [Description("Summary")]
        Summary = 1,
    }
    /// <summary>
    /// List of filter types of Report type "Documents Released By MRN"
    /// </summary>
    public enum DocumentsReleasedByMRNFilter
    {
        [Description("Patient Name")]
        PatientName   = 0,        
        [Description("Patient Name, Requestor Type")]
        RequestorType = 1,
        [Description("Patient Name, Requestor Type, Document")]
        DocumentName  = 3,
    }

    /// <summary>
    /// List of filter types of Report type "Accounting of Disclosure"
    /// </summary>
    public enum AccountingDisclosureFilter
    {
        [Description("Disclosure Date")]
        DisclosureDate = 0,
        [Description("Disclosure Date, Documents Released")]
        DocumentsReleased = 1,
    }

    public enum MUReleaseTurnaroundTimeFilter
    {
        [Description("Details")]
        Details = 0,
        [Description("Totals Per Facility")]
        TotalsPerFacility = 1,
    }
    /// <summary>
    /// List of options in date selection
    /// </summary>
    public enum ReportDateRange
    {
        [Description("Today's Date")]
        CustomDate = 0,
        [Description("Custom Date")]
        TodayDate = 1,
        [Description("Current Week")]
        CurrentWeek = 2,
        [Description("Current Month")]
        CurrentMonth = 3,
        [Description("Previous Month")]
        PreviousMonth = 4,
        [Description("3 Months Prior")]
        ThreeMonthsPrior = 5,
        [Description("12 Months Period")]
        TwelvemonthsPeriod = 6,
        [Description("Year to Date")]
        YearToDate = 7,
        [Description("Previous Year")]
        PreviousYear = 8
    }


    public enum PreBillFilter
    {
        [Description("Summary - Requestor Type")]
        SummaryReqType = 0,
        [Description("Detailed - Requestor name")]
        DetailedReqName = 1,
        [Description("Detailed - Request ID")]
        DetailedReqID = 2,
    }

    /// <summary>
    /// List of status to status options,
    /// </summary>
    public enum StatusToStatus
    {
        [Description("Please select")]
        None = 0,
        [Description("Received to Logged")]
        ReceivedToLogged = 1,
        [Description("Received to Pended")]
        ReceivedToPended = 2,
        [Description("Received to Completed")]
        ReceivedToComplete = 3,
        [Description("Received to Pre-bill")]
        ReceivedToPreBill = 4,
        [Description("Logged to Pended")]
        LoggedToPended = 5,
        [Description("Logged to Completed")]
        LoggedToComplete = 6,
        [Description("Logged to Pre-billed")]
        LoggedToPreBill = 7
    }

    /// <summary>
    /// List of Report export formats.
    /// </summary>
    public enum ExportFormat
    {
        [Description("PDF")]
        Pdf = 0,
        [Description("XLS")]
        Excel = 1,
        [Description("RTF (Microsoft word - Editable)")]
        Rtf = 2,        
        [Description("HTML")]
        Html = 3,
        [Description("CSV")]
        Csv = 4
    }

    public enum RequestReportAttr
    {   
        [Description("TPO")]
        Tpo = 0,
        [Description("Non-TPO")]
        NonTpo = 1,
        [Description("Both")]
        Both = 2
    }

    public enum BalanceDueOperator
    {
        [Description("At Least")]
        AtLeast = 0,
        [Description("At Most")]
        AtMost = 1,
        [Description("Equals")]
        Equals = 2
    }

    public enum FromStatus
    {
        [Description("Please select")]
        None = 0,
        [Description("Auth Request Received")]
        AuthRequestReceived = 1,
        [Description("Auth Request Denied")]
        AuthRequestDenied = 2,
        [Description("Received")]
        Received = 3,
        [Description("Logged")]
        Logged = 4,
        [Description("Pended")]
        Pended = 5,
        [Description("Canceled")]
        Canceled = 6,
        [Description("Denied")]
        Denied = 7,
        [Description("Pre-billed")]
        Prebilled = 8,
        [Description("Completed")]
        Completed = 9
    }

    public enum ToStatus
    {
        [Description("Please select")]
        None = 0,
        [Description("Logged")]
        Logged = 1,
        [Description("Pended")]
        Pended = 2,
        [Description("Canceled")]
        Canceled = 3,
        [Description("Denied")]
        Denied = 4,
        [Description("Pre-billed")]
        Prebilled = 5,
        [Description("Completed ")]
        Completed = 6
    }

    public enum ProductivityFilter
    {
        [Description("Username")]
        FacUserPag = 0,
        [Description("Requestor Type")]
        FacReqType = 1,
        [Description("Request ID,Page Type")]
        FacReqPgType = 2,
        [Description("Request ID,MRN")]
        FacReqMRN = 3
    }

    public enum BillableAndUnBillable
    {
        [Description("Billable")]
        Billable = 0,
        [Description("Unbillable")]
        UnBillable = 1,
        //CR#379926
        //[Description("Both")]
        //Both = 2
    }

    public enum BillableAndUnBillabaleFilter
    {
        [Description("Facility,Billable & Unbillable")]
        FacilityBillableAndUnBillable = 0,
        [Description("Facility,Requestor Type,Request ID")]
        BillableAndUnBillableReqType = 1
    }
    /// <summary>
    /// ReportDetails
    /// </summary>
    public class ReportDetails
    {
        #region Fields

        private string filePath;
        private ReportType reportType;

        #endregion

        #region Properties
        /// <summary>
        /// Holds file path
        /// </summary>
        public string FilePath
        {
            get { return filePath; }
            set { filePath = value; }
        }

        /// <summary>
        /// Holds report type
        /// </summary>
        public ReportType ReportType
        {
            get { return reportType; }
            set { reportType = value; }
        }

        #endregion

    }
}
