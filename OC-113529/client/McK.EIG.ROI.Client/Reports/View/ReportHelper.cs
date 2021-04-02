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
using System.Configuration;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;

using CrystalDecisions.CrystalReports.Engine;
using CrystalDecisions.Shared;


namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportHelper
    /// </summary>
    public static class ReportHelper
    {
        #region Fields

        private const string fileExtension = "_{0}.rpt";
        private static string separator = "\"";
        
        #endregion

        #region Methods

        /// <summary>
        /// Gets the report file name for the selected report type.
        /// </summary>
        /// <param name="reportType"></param>
        /// <returns></returns>
        public static string GetReportFileName(ReportType reportType)
        {
            return reportType + fileExtension;
        }

        /// <summary>
        /// Gets the report criteria UI for the selected report type
        /// </summary>
        /// <param name="reportType"></param>
        /// <returns></returns>
        public static IReportCriteria GetCriteriaUI(ReportType reportType)
        {
            ReportBaseCriteriaUI.reportInputFieldisRequired = false;
            ReportBaseCriteriaUI.isOutstandingReport = false;
            switch (reportType)
            {
                case ReportType.RequestWithLoggedStatus   : { return new RequestLoggedStatusUI(); }
                case ReportType.RequestDetailReport       : { return new RequestDetailsUI(); }
                case ReportType.PendingAgingSummary       : { return new PendingAgingSummaryUI(); }
                case ReportType.RequestStatusSummary      : { return new RequestStatusSummaryUI(); }
                case ReportType.OutstandingInvoiceBalance : { ReportBaseCriteriaUI.reportInputFieldisRequired = true;
                                                              ReportBaseCriteriaUI.isOutstandingReport = true;  
                                                              return new OutstandingInvoiceBalanceUI(); }
                case ReportType.AccountReceivableAging    : { return new AccountReceivableAgingUI(); }
                case ReportType.ProcessRequestSummary     : { return new ProcessedRequestSummaryUI(); }
                case ReportType.PostedPaymentsReport    : { return new PostedPaymentReportUI(); }
                case ReportType.DocumentsReleasedByMRN    : { return new DocumentsReleasedByMrnUI(); }
                case ReportType.TurnaroundTimes           : { return new TurnaroundTimeUI(); }
                case ReportType.AccountingDisclosure      : { return new AccountingOfDisclosureUI(); }
                case ReportType.SalesTaxReport            : { ReportBaseCriteriaUI.reportInputFieldisRequired = true; 
                                                              return new SalesTaxReportUI(); }
				 case ReportType.MUReleaseTurnaroundTimeReport   : { return new MUReleaseTurnaroundTimeUI(); }
                 case ReportType.PrebillReport: { return new PreBillReportUI(); }
                 case ReportType.ProductivityReport: { return new ProductivityUI(); }
                 case ReportType.BillableAndUnBillableReport: { return new BillableAndUnBillableReportUI(); }					  
                 case ReportType.ROIBillingReport: { return new ROIBillingUI(); }

                default: return null;
            }
        }

        public static IList GetFilters(ReportType reportType)
        {
            switch (reportType)
            {
                case ReportType.RequestWithLoggedStatus   : { return EnumUtilities.ToList(typeof(ReportLoggedStatusFilter)); }
                case ReportType.RequestDetailReport       : { return EnumUtilities.ToList(typeof(RequestDetailsFilter)); }
                case ReportType.RequestStatusSummary      : { return EnumUtilities.ToList(typeof(RequestStatusSummaryFilter)); }
                case ReportType.PendingAgingSummary       : { return EnumUtilities.ToList(typeof(PendingAgingSummaryFilter)); }
                case ReportType.OutstandingInvoiceBalance : { return EnumUtilities.ToList(typeof(OutstandingInvoiceBalanceFilter)); }
                case ReportType.AccountReceivableAging    : { return EnumUtilities.ToList(typeof(AccountReceivableAgingFilter)); }
                case ReportType.PostedPaymentsReport      : { return EnumUtilities.ToList(typeof(PostedPaymentReportFilter)); }
                case ReportType.DocumentsReleasedByMRN    : { return EnumUtilities.ToList(typeof(DocumentsReleasedByMRNFilter)); }
                case ReportType.TurnaroundTimes           : { return EnumUtilities.ToList(typeof(TurnaroundTimeFilter)); }
                case ReportType.AccountingDisclosure      : { return EnumUtilities.ToList(typeof(AccountingDisclosureFilter)); }
                case ReportType.SalesTaxReport            : { return EnumUtilities.ToList(typeof(SalesTaxReportFilter)); }
                case ReportType.ProcessRequestSummary     : { return EnumUtilities.ToList(typeof(ProcessedRequestSummaryFilter)); }
				case ReportType.MUReleaseTurnaroundTimeReport: { return EnumUtilities.ToList(typeof(MUReleaseTurnaroundTimeFilter)); };
                case ReportType.PrebillReport: { return EnumUtilities.ToList(typeof(PreBillFilter)); };
                case ReportType.ProductivityReport: { return EnumUtilities.ToList(typeof(ProductivityFilter)); };
                case ReportType.BillableAndUnBillableReport: { return EnumUtilities.ToList(typeof(BillableAndUnBillabaleFilter)); };
                default: return null;
            }
        }

        /// <summary>
        /// Hides the tab or Crystal Report viewer. It takes CrystalReportViewer as an argument
        /// and a boolean value which is to make visible/hide tab
        /// </summary>
        /// <param name="viewer"></param>
        /// <param name="visible"></param>
        public static void HideReportTabs(Control viewer, bool visible)
        {
            if (viewer == null)
            {
                return;
            }
            foreach (Control control in viewer.Controls)
            {
                if (control is CrystalDecisions.Windows.Forms.PageView)
                {
                    TabControl tab = (control).Controls[0] as TabControl;
                    if (!visible)
                    {
                        tab.ItemSize = new System.Drawing.Size(0, 1);
                        tab.SizeMode = TabSizeMode.Fixed;
                        tab.Appearance = TabAppearance.Buttons;
                    }
                    else
                    {
                        tab.ItemSize = new System.Drawing.Size(67, 18);
                        tab.SizeMode = TabSizeMode.Normal;
                        tab.Appearance = TabAppearance.Normal;
                    }
                }
            }
        }

        /// <summary>
        /// Creates DSN
        /// </summary>
        /// <param name="reportDetails"></param>
        public static void CreateDataSource(string filePath)
        {
            ReportUtility.SetRegistryValue(ConfigurationManager.AppSettings["ReportDsn"], Directory.GetParent(filePath).ToString());
        }

        /// <summary>
        /// Renames the input csv file name to the rpt.
        /// </summary>
        public static void RenameCsvFile(ReportDetails reportDetails)
        {
            string inputFilePath = Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), reportDetails.ReportType + ".csv");
            if (Validator.Validate(inputFilePath, ROIConstants.FilepathValidation) && File.Exists(inputFilePath))
            {
                File.Delete(inputFilePath);
            }
            if (Validator.Validate(reportDetails.FilePath, ROIConstants.FilepathValidation) && File.Exists(reportDetails.FilePath))
            {
                File.Move(reportDetails.FilePath, inputFilePath);
            }
            switch (reportDetails.ReportType)
            {
                case ReportType.MUReleaseTurnaroundTimeReport:
                    {
                        MUReleaseTurnaroundTimeUI muReleaseTATUI = new MUReleaseTurnaroundTimeUI();
                        string subtitleFilePath = Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), "Header_" + reportDetails.ReportType + ".csv");
                        muReleaseTATUI.CreateSubtitleFile(subtitleFilePath);
                        break;
                    }
                case ReportType.ProductivityReport:
                    {
                        ProductivityUI prodUI = new ProductivityUI();
                        string subtitleFilePath = Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), "Header_" + reportDetails.ReportType + ".csv");
                        prodUI.CreateSubtitleFile(subtitleFilePath);
                        break;
                    }
                case ReportType.PostedPaymentsReport:
                    {
                        PostedPaymentReportUI postedPayUI = new PostedPaymentReportUI();
                        string subtitleFilePath = Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), "Header_" + reportDetails.ReportType + ".csv");
                        postedPayUI.CreateSubtitleFile(subtitleFilePath);
                        break;
                    }
                case ReportType.PrebillReport:
                    {
                        PreBillReportUI preBillUI = new PreBillReportUI();
                        string subtitleFilePath = Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), "Header_" + reportDetails.ReportType + ".csv");
                        preBillUI.CreateSubtitleFile(subtitleFilePath);
                        break;
                    }
                case ReportType.ROIBillingReport:
                    {
                        ROIBillingUI roiBillUI = new ROIBillingUI();
                        string subtitleFilePath = Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), "Header_" + reportDetails.ReportType + ".csv");
                        roiBillUI.CreateSubtitleFile(subtitleFilePath);
                        break;
                    }
                //CR#377377
                case ReportType.OutstandingInvoiceBalance:
                    {
                        OutstandingInvoiceBalanceUI outstandingUI = new OutstandingInvoiceBalanceUI();
                        string subtitleFilePath = Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), "Balance_" + reportDetails.ReportType + ".csv");
                        outstandingUI.CreateSubtitleFile(subtitleFilePath);
                        break;
                    }
            }

        }


        /// <summary>
        /// Sets the Report Formula values
        /// </summary>
        /// <param name="formulaTable">List of formulae</param>
        /// <param name="reportClass">Report to set the formula</param>
        public static void SetFormula(Hashtable formulaTable, ReportDocument reportDocument)
        {
            if (formulaTable == null)
            {
                return;
            }
            if (reportDocument == null)
            {
                return;
            }
            IDictionaryEnumerator formulaEnumerator = formulaTable.GetEnumerator();
            while (formulaEnumerator.MoveNext())
            {
                foreach (FormulaFieldDefinition formulaField in reportDocument.DataDefinition.FormulaFields)
                {
                    int checkFormula = string.Compare(Convert.ToString(formulaField.FormulaName, System.Threading.Thread.CurrentThread.CurrentUICulture),
                                                      Convert.ToString(formulaEnumerator.Key, System.Threading.Thread.CurrentThread.CurrentUICulture),
                                                      true,
                                                      System.Threading.Thread.CurrentThread.CurrentUICulture);
                    if (checkFormula == 0)
                    {
                        formulaField.Text = separator +
                            Convert.ToString(formulaEnumerator.Value, System.Threading.Thread.CurrentThread.CurrentUICulture) + separator;
                        break;
                    }
                }
            }

        }

        internal static IReportSorter GetSorter(ReportType reportType)
        {
            switch (reportType)
            {
                case ReportType.TurnaroundTimes          : { return new TurnaroundSorter(); }
                case ReportType.OutstandingInvoiceBalance: { return new OutstandingInvoiceBalanceSorter(); }
                case ReportType.ProcessRequestSummary    : { return new ProcessRequestSummarySorter(); }
                case ReportType.RequestDetailReport      : { return new RequestDetailsSorter(); }
                case ReportType.RequestWithLoggedStatus  : { return new RequestLoggedStatusSorter(); }
                case ReportType.AccountingDisclosure     : { return new AccountingDisclosureSorter(); }
                case ReportType.DocumentsReleasedByMRN   : { return new DocumentsReleasedByMRNSorter(); }
                case ReportType.PostedPaymentsReport     : { return new PostedPaymentReportSorter(); }
                case ReportType.PrebillReport            : { return new PreBillReportSorter(); }
                default: return null;
            }
          
        }

        /// <summary>
        /// Gets the selected formatter export type.
        /// </summary>
        /// <param name="exportType"></param>
        /// <returns></returns>
        public static ExportFormatType GetExportFormatType(ExportFormat exportFormat)
        {
            switch (exportFormat)
            {   
                case ExportFormat.Html: { return ExportFormatType.HTML40; }
                case ExportFormat.Excel:  { return ExportFormatType.Excel; }
                case ExportFormat.Rtf:  { return ExportFormatType.RichText; }
                case ExportFormat.Csv: { return ExportFormatType.CharacterSeparatedValues; }
                default: { return ExportFormatType.PortableDocFormat; }
            }
        }


        /// <summary>
        /// Create folder structure for export type "HTML"
        /// </summary>
        /// <param name="exportOptions"></param>
        /// <param name="exportPath"></param>
        public static void ConfigureHtml(ExportOptions exportOptions, string exportPath, string HTMLFileName)
        {
            HTMLFormatOptions html40FormatOptions = new HTMLFormatOptions();
            html40FormatOptions.HTMLBaseFolderName = exportPath;
            html40FormatOptions.HTMLFileName = HTMLFileName;
            exportOptions.FormatOptions = html40FormatOptions;
        }

        public static void UpdateHtmlFile(ReportDetails reportDetails, int reportIndex, string exportPath)
        {
            if (Validator.Validate(exportPath, ROIConstants.FilepathValidation))
            {
                string newText = "<HTML><style>@media print {body {zoom:95%;}}</style>";
                string filePath = Path.Combine
                                  (Path.Combine(exportPath,
                                                reportDetails.ReportType + "_" + reportIndex),
                                   reportDetails.ReportType + ".htm");
                StreamReader sr = new StreamReader(filePath);
                String fileContent = sr.ReadToEnd();
                sr.Close();
                StreamWriter sw = new StreamWriter(filePath);
                sw.WriteLine(fileContent.Replace("<HTML>", newText));
                sw.Close();
            }
        }

        internal static void ConfigureExcel(ExportOptions exportOptions)
        {
            ExcelFormatOptions excelFormatOptions = new ExcelFormatOptions();
            excelFormatOptions.ExcelTabHasColumnHeadings = true;
            excelFormatOptions.ExportPageHeadersAndFooters = ExportPageAreaKind.OncePerReport;
            excelFormatOptions.ExcelUseConstantColumnWidth = false;
            excelFormatOptions.ExcelAreaType = AreaSectionKind.WholeReport;
            //ExcelDataOnlyFormatOptions exportFormatOptions = ExportOptions.CreateDataOnlyExcelFormatOptions();
            //exportFormatOptions.ExcelAreaType = AreaSectionKind.Detail;
            //exportFormatOptions.ExportPageHeaderAndPageFooter = true;
            exportOptions.FormatOptions = excelFormatOptions;
        }

        public static KeyValuePair<Enum, string> GetEnum(Enum value)
        {
            return new KeyValuePair<Enum, string>(value, EnumUtilities.GetDescription(value));
        }


        #endregion

    }
}
