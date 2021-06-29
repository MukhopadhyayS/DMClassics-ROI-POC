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
using System.Collections.ObjectModel;
using System.Globalization;
using System.Net;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Controller;
using McK.EIG.ROI.Client.Reports.Model;

using McK.EIG.Common.Utility.View;

using McK.EIG.Common.Utility.Logging;
using System.Collections.Generic;
namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportUI
    /// </summary>
    public partial class ReportUI : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ReportUI));     

        private IReportCriteria reportCriteriaUI;
        private EventHandler cmbReportTypeChanged;
        private EventHandler dateRangeChanged;
        private EventHandler inputFieldChanged;
		private string facilityCode;
        private string facilities;
        private string userNames;
        private string fromStatusValue;
        private string toStatusValue;
        private string fromDateRange;
        private string toDateRange;

        #endregion

        #region Constructor

        public ReportUI()
        {
            InitializeComponent();
            this.Text = Title;
            cmbReportTypeChanged = new System.EventHandler(cmbReportType_SelectedIndexChanged);
            dateRangeChanged = new EventHandler(dateRange_TextChanged);
            inputFieldChanged = new EventHandler(inputField_TextChanged);
            PopulateReportTypes();
        }

        #endregion
       
        #region Methods

        /// <summary>
        /// Populates all report types.
        /// </summary>
        public void PopulateReportTypes()
        {
            DisableEvents();

                IList reportTypes = EnumUtilities.ToList(typeof(ReportType));
                Dictionary<Enum, string> dict = new Dictionary<Enum, string>(reportTypes.Count);
                int j = 0;
                
                foreach(KeyValuePair<Enum, string> enums in reportTypes)
                {
                    dict.Add(enums.Key,enums.Value);
                    
                }
                List<string> ar = new List<string>(reportTypes.Count);
                foreach (KeyValuePair<Enum, string> valuekey in dict)
                {
                     ar.Add(valuekey.Value);
                }
                ar.Sort();
                Dictionary<Enum, string> dict1 = new Dictionary<Enum, string>(reportTypes.Count);
                foreach (KeyValuePair<Enum, string> valuekey in dict)
                {
                    if (valuekey.Value == "--Select Report--")
                    {
                        dict1.Add(valuekey.Key, valuekey.Value);
                        break;
                    }

                }
                foreach (string  arString in ar)
                {
                    foreach (KeyValuePair<Enum, string> valuekey in dict)
                    {
                        if (arString == valuekey.Value && arString != "--Select Report--")
                        {
                            dict1.Add(valuekey.Key, valuekey.Value);
                        }

                    }

                }
                reportTypes.Clear();

                foreach(KeyValuePair<Enum, string> final in dict1)
                {
                   reportTypes.Add(final);
                }
                reportTypeComboBox.DataSource = reportTypes;

            reportTypeComboBox.DisplayMember = "value";
            reportTypeComboBox.ValueMember  = "key";
            ClearControls();
            EnableEvents();
            reportTypeComboBox.Focus();
        }

        /// <summary>
        /// Clear Control Values.
        /// </summary>
        public void ClearControls()
        {
            EnableRunButton(false);
            if (reportCriteriaUI != null)
            {
                reportCriteriaUI.ClearControls();
                errorProvider.Clear();
            }
        }

        /// <summary>
        /// Enabled events
        /// </summary>
        private void EnableEvents()
        {
            reportTypeComboBox.SelectedIndexChanged += cmbReportTypeChanged;
            ReportEvents.DateRangeChanged += dateRangeChanged;
            ReportEvents.InputFieldChanged += inputFieldChanged;
        }

        /// <summary>
        /// Disable events
        /// </summary>
        private void DisableEvents()
        {
            reportTypeComboBox.SelectedIndexChanged -= cmbReportTypeChanged;
            ReportEvents.DateRangeChanged -= dateRangeChanged;
            ReportEvents.InputFieldChanged -= inputFieldChanged;
        }

        /// <summary>
        /// Report type combobox selection changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cmbReportType_SelectedIndexChanged(object sender, EventArgs e)
        {
            ReportEvents.OnClearEditor(Pane, e);
            EnableRunButton(false);
            reportCriteriaUI = ReportHelper.GetCriteriaUI((ReportType)reportTypeComboBox.SelectedValue);
            AddCriteriaUI(reportCriteriaUI);
            resetButton.Enabled = !(reportTypeComboBox.SelectedIndex == 0);
        }

        private void dateRange_TextChanged(object sender, EventArgs e)
        {
            ValidatePrimaryFields();
        }
        private void inputField_TextChanged(object sender, EventArgs e)
        {
            ValidatePrimaryFields();
        }

        /// <summary>
        /// Add the criteria ui to LSP.
        /// </summary>
        /// <param name="reportCriteriaUI"></param>
        private void AddCriteriaUI(IReportCriteria reportCriteriaUI)
        {
            reportCriteriaPanel.Controls.Clear();
            if (reportCriteriaUI != null)
            {
                ROIBaseUI criteriaUI = (ROIBaseUI)reportCriteriaUI;
                criteriaUI.SetPane(Pane);   
                criteriaUI.SetExecutionContext(Context);
                criteriaUI.Localize();
                reportCriteriaUI.PrePopulate();
                Control reportCriteriaUIControl = (Control)reportCriteriaUI;
                reportCriteriaUIControl.Dock = DockStyle.Fill;
                reportCriteriaUIControl.TabIndex = 2;
                reportCriteriaPanel.Controls.Add(reportCriteriaUIControl);
            }
        }

        /// <summary>
        /// Button run click event.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnRunReport_Click(object sender, EventArgs e)
        {
            
            
            ROIViewUtility.MarkBusy(true);
            errorProvider.Clear();
            string csvFilePath = null;
            ReportDetails reportDetails = new ReportDetails();
            Hashtable parameters = CollectData();
            if (Convert.ToInt16(parameters[ROIConstants.ReportId]) != 14)
            {
            	int reportId = Convert.ToInt16(parameters[ROIConstants.ReportId], System.Threading.Thread.CurrentThread.CurrentUICulture);
            	if ( reportId != 3 && reportId != 6)
            	{
                	if (!parameters.Contains(ROIConstants.ReportStartDate)) return;
                }
            }

            csvFilePath = GenerateReport(parameters);

            if (string.IsNullOrEmpty(csvFilePath)) return;
          
            reportDetails.ReportType = (ReportType)reportTypeComboBox.SelectedValue;
            reportDetails.FilePath   = csvFilePath;


            ApplicationEventArgs ae = new ApplicationEventArgs(reportDetails, this);
            ReportEvents.OnRunReport(Pane, ae);
            ROIViewUtility.MarkBusy(false);
        }

        public string GenerateReport(Hashtable parameters)
        {
            string csvFilePath = string.Empty;
            log.EnterFunction();

            Collection<ExceptionData> errors = new Collection<ExceptionData>();
            //ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

            try
            {
                csvFilePath = ROIReportController.Instance.GenerateReport(UserData.Instance.UserId, parameters);
            }
            catch (WebException cause)
            {
                log.FunctionFailure(cause);
                errors.Add(new ExceptionData(ROIErrorCodes.ReportGenerationFailure));
                ROIViewUtility.Handle(Context, new ROIException(errors));
                return string.Empty;
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                return string.Empty;
            }
            log.ExitFunction();
            return csvFilePath;
        }

        /// <summary>
        /// Get error controls
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            return ((ROIBaseUI)reportCriteriaUI).GetErrorControl(error);
        }    

        /// <summary>
        /// Collect all criteria data into a hashtable.
        /// </summary>
        /// <returns></returns>
        public Hashtable CollectData()
        {
            Hashtable parameters = new Hashtable();

            parameters.Add(ROIConstants.ReportId, Convert.ToInt32((ReportType)reportTypeComboBox.SelectedValue, System.Threading.Thread.CurrentThread.CurrentUICulture));
            parameters.Add(ROIConstants.TransId, Guid.NewGuid());
            parameters.Add(ROIConstants.UserInstanceId, UserData.Instance.UserInstanceId);
            reportCriteriaUI.GetData(parameters);
            facilityCode = Convert.ToString(parameters[ROIConstants.ReportFacilities], System.Threading.Thread.CurrentThread.CurrentUICulture);
            facilities = ROIViewUtility.OriginalString(Convert.ToString(parameters[ROIConstants.ReportFacilityNames], System.Threading.Thread.CurrentThread.CurrentUICulture));
            userNames = Convert.ToString(parameters[ROIConstants.ReportUserNames], System.Threading.Thread.CurrentThread.CurrentUICulture);
            fromStatusValue = Convert.ToString(parameters[ROIConstants.FromStatusValue], System.Threading.Thread.CurrentThread.CurrentUICulture);
            toStatusValue = Convert.ToString(parameters[ROIConstants.ToStatusValue], System.Threading.Thread.CurrentThread.CurrentUICulture);
            fromDateRange = Convert.ToString(parameters[ROIConstants.ReportFromDateRangeValue], System.Threading.Thread.CurrentThread.CurrentUICulture);
            toDateRange = Convert.ToString(parameters[ROIConstants.ReportToDateRangeValue], System.Threading.Thread.CurrentThread.CurrentUICulture);
            return parameters;
        }

        /// <summary>
        /// Enable run report button
        /// </summary>
        /// <param name="enable"></param>
        public void EnableRunButton(bool enable)
        {
            runReportButton.Enabled = enable;
        }

        /// <summary>
        /// Localizes UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, reportNameLabel);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, resetButton);
            SetLabel(rm, runReportButton);
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, resetButton);
            SetTooltip(rm, toolTip, runReportButton);
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + Pane.GetType().Name;
        }

        /// <summary>
        /// Clears the data and reset to default values.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void resetButton_Click(object sender, EventArgs e)
        {
            reportTypeComboBox.SelectedIndex = 0;
        }

        /// <summary>
        /// Validate required search criteria
        /// </summary>
        internal void ValidatePrimaryFields()
        {
          bool enable = reportCriteriaUI.ValidatePrimaryFields();
          EnableRunButton(enable);
          ReportEvents.OnLSPParamsChanged(Pane, null);
        }

        public void SelectReport(string subReport)
        {
            switch (subReport)
            {
                case ROIConstants.AccountingOfDisclosure    : reportTypeComboBox.SelectedIndex = 2; break;
                case ROIConstants.AccountReceivableAging    : reportTypeComboBox.SelectedIndex = 1; break;
                case ROIConstants.DocumentsReleasedByMRN    : reportTypeComboBox.SelectedIndex = 4; break;
                case ROIConstants.RequestStatusSummary      : reportTypeComboBox.SelectedIndex = 13; break;
                case ROIConstants.RequestDetailReport       : reportTypeComboBox.SelectedIndex = 12; break;
                case ROIConstants.OutstandingInvoiceBalances: reportTypeComboBox.SelectedIndex = 6; break;
                case ROIConstants.PendingAgingSummary       : reportTypeComboBox.SelectedIndex = 7; break;
                case ROIConstants.RequestsWithLoggedStatus  : reportTypeComboBox.SelectedIndex = 14; break;
                case ROIConstants.ProcessRequestSummary     : reportTypeComboBox.SelectedIndex = 10; break;
                case ROIConstants.PostedPaymentReport      : reportTypeComboBox.SelectedIndex = 8; break;
                case ROIConstants.TurnaroundTimes           : reportTypeComboBox.SelectedIndex = 17; break;
                case ROIConstants.SalesTaxReport            : reportTypeComboBox.SelectedIndex = 16; break;
				case ROIConstants.MUReleaseTurnaroundTime: reportTypeComboBox.SelectedIndex = 5; break;
                case ROIConstants.PreBillReport: reportTypeComboBox.SelectedIndex = 9; break;
                case ROIConstants.Productivity: reportTypeComboBox.SelectedIndex = 11; break;
                //CR#377452
                case ROIConstants.BillableUnbillable: reportTypeComboBox.SelectedIndex = 3; break;
                case ROIConstants.ROIBilling: reportTypeComboBox.SelectedIndex = 15; break;
            }
        }

        /// <summary>
        /// Sets set accept button.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = runReportButton;

            if (reportCriteriaUI != null)
            {
                if (reportCriteriaUI.GetType().IsAssignableFrom(typeof(DocumentsReleasedByMrnUI)))
                {
                    ((DocumentsReleasedByMrnUI)(reportCriteriaUI)).MrnTextBox.Focus();
                    return;
                }
            }

            if (reportTypeComboBox.SelectedIndex == 0)
            {
                reportTypeComboBox.Focus();
            }
        }

        #endregion

        #region Properties

        public static string Title
        {
            get { return "Reports"; }
        }

        public string FacilityCode
        {
            get { return facilityCode; }            
        }

        public string Facilities
        {
            get { return facilities; }
        }

        public string UserNames
        {
            get { return userNames; }
        }
        
        public string FromStatusValue
        {
            get { return fromStatusValue; }
        }

        public string ToStatusValue
        {
            get { return toStatusValue; }
        }

        public string FromDateRangeValue
        {
            get { return fromDateRange; }
        }

        public string ToDateRangeValue
        {
            get { return toDateRange; }
        }
        

        #endregion
    }
}
