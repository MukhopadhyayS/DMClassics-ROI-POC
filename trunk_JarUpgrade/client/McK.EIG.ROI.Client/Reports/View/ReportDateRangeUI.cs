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
using System.ComponentModel;
using System.Resources;
using System.Text;
using System.Globalization;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Reports.Model;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportDateRangeUI
    /// </summary>
    public partial class ReportDateRangeUI : ROIBaseUI
    {

        #region Fields

        private bool isValidDateRange;
        private const string CustomDateFormat = "MM/dd/yyyy";
        private IFormatProvider theCultureInfo = new System.Globalization.CultureInfo("en-GB", true);

        #endregion

        #region Constructor
        /// <summary>
        /// Initializes the UI
        /// </summary>
        public ReportDateRangeUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods
        /// <summary>
        /// Populated date options.
        /// </summary>
        public void Populate()
        {
            IList dateRangeTypes = EnumUtilities.ToList(typeof(ReportDateRange));

            presetRangeComboBox.DataSource = dateRangeTypes;
            presetRangeComboBox.DisplayMember = "value";
            presetRangeComboBox.ValueMember = "key";
            presetRangeComboBox.SelectedIndex = 0;
            presetRangeComboBox.Refresh();
        }

        /// <summary>
        /// Date combo box selection changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void presetRangeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            ReportEvents.OnLSPParamsChanged(Pane, e);
            int index = presetRangeComboBox.SelectedIndex;
            DateTime startDateValue = DateTime.Today.Date;
            DateTime endDateValue = DateTime.Now.Date;

            EnableDateControls(false);

            switch (index)
            {
                case 0:
                    {
                        fromCustomDate.Text = startDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = endDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
                case 1:
                    {
                        fromCustomDate.Text = startDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = endDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        IsValidDateRange = true;
                        EnableDateControls(true);
                        break;
                    }
                case 2:
                    {
                        int weekDay = (int)DateTime.Now.DayOfWeek;
                        fromCustomDate.Text = DateTime.Now.AddDays(weekDay * -1).ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = endDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
                case 3:
                    {
                        int day = (int)System.DateTime.Now.Day;
                        fromCustomDate.Text = DateTime.Now.AddDays(-(day - 1)).Date.ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = endDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
                case 4:
                    {
                        startDateValue = startDateValue.AddMonths(-1);
                        fromCustomDate.Text = startDateValue.AddDays(-(startDateValue.Day - 1)).ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = DateTime.Now.AddDays(-(endDateValue.Day)).ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
                case 5:
                    {
                        fromCustomDate.Text = DateTime.Now.AddMonths(-3).ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = endDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
                case 6:
                    {
                        fromCustomDate.Text = DateTime.Now.AddMonths(-12).ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = endDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
                case 7:
                    {
                        fromCustomDate.Text = new DateTime(startDateValue.Year, 1, 1).ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = endDateValue.ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
                case 8:
                    {
                        fromCustomDate.Text = (new DateTime((endDateValue.Year - 1), 1, 1)).ToString("MM/dd/yyyy", theCultureInfo);
                        toCustomDate.Text = (new DateTime(startDateValue.Year, 1, 1)).AddDays(-1).ToString("MM/dd/yyyy", theCultureInfo);
                        break;
                    }
            }
        }

        internal void ClearControls()
        {
            presetRangeComboBox.SelectedIndex = 0;
            EnableDateControls(false);

        }

        /// <summary>
        /// Enables or disables start and end time pickers
        /// </summary>
        /// <param name="enable"></param>
        private void EnableDateControls(bool enable)
        {
            fromCustomDate.Enabled = enable;
            toCustomDate.Enabled = enable;
        }

        /// <summary>
        /// Localizes the UI
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, dateRangeLabel);
            SetLabel(rm, presetRangeLabel);
            SetLabel(rm, startDateLabel);
            SetLabel(rm, endDateLabel);
        }

        /// <summary>
        /// Gets start date and end date.
        /// </summary>
        /// <param name="reportCriteria"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            Hashtable parameters = (Hashtable)appendTo;
           
            if (!CheckFutureValidate(fromCustomDate.Text, toCustomDate.Text)) 
            {
                ROIViewUtility.MarkBusy(false);
                return false; 
            }

            parameters.Add(ROIConstants.ReportStartDate, ROIViewUtility.GetFormattedDate(fromCustomDate.Text));
            parameters.Add(ROIConstants.ReportEndDate, ROIViewUtility.GetFormattedDate(toCustomDate.Text));
            parameters.Add(ROIConstants.ReportFromDateRangeValue, ROIViewUtility.GetFormattedDate(fromCustomDate.Text));
            parameters.Add(ROIConstants.ReportToDateRangeValue, ROIViewUtility.GetFormattedDate(toCustomDate.Text));

            return parameters;
        }

        #endregion

        #region Properties

        public MaskedEditDateControl EndDateTimePicker
        {
            get
            { 
                return toCustomDate; 
            }

        #endregion
        }

        private void SetError(string fromDate, string toDate)
        {
            isValidDateRange = true;
            DateTime? fromDateTime = null;
            try
            {
                if (!ROIViewUtility.IsValidFormat(fromDate))
                {
                    throw new FormatException();
                }
                else
                {
                    fromDateTime = DateTime.ParseExact(ROIViewUtility.GetFormattedDate(fromDate), CustomDateFormat, theCultureInfo);
                    ShowInlineError(fromCustomDate, "");
                }
            }
            catch (FormatException)
            {
                ShowInlineError(toCustomDate, "");
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
                return;
            }

            //if (fromDateTime.Value > DateTime.Today)
            //{
            //    ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.FutureDateSelected));
            //    IsValidDateRange = false;
            //    return;
            //}

            if (toDate.Equals("  /  /"))
            {
                isValidDateRange = false;
                return;
            }
            DateTime? toDateTime = null;
            try
            {
                if (!ROIViewUtility.IsValidFormat(toDate))
                {
                    throw new FormatException();
                }
                else
                {
                    toDateTime = DateTime.ParseExact(ROIViewUtility.GetFormattedDate(toDate), CustomDateFormat, theCultureInfo);
                    ShowInlineError(toCustomDate, "");
                }
            }
            catch (FormatException)
            {
                ShowInlineError(fromCustomDate, "");
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
                return;
            }


            //if (toDateTime.Value > DateTime.Today)
            //{
            //    ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.FutureDateSelected));
            //    IsValidDateRange = false;
            //    return;
            //}

            if (fromDateTime.Value > toDateTime.Value)
            {
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.StartDateInvalids));
                IsValidDateRange = false;
            }
            if (toDateTime.Value < fromDateTime.Value)
            {
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.EndDateInvalids));
                IsValidDateRange = false;
            }


            if (isValidDateRange)
            {
                ShowInlineError(fromCustomDate, "");
                ShowInlineError(toCustomDate, "");
            }
        }



        public bool CheckFutureValidate(string fromDate, string toDate)
        {
            DateTime? fromDateTime = null;
            isValidDateRange = true;
            try
            {
                fromDateTime = DateTime.ParseExact(ROIViewUtility.GetFormattedDate(fromDate), CustomDateFormat, theCultureInfo);            
            }
            catch (FormatException)
            {
                ShowInlineError(toCustomDate, "");
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
                return IsValidDateRange;
            }

            DateTime? toDateTime = null;

            try
            {
                toDateTime = DateTime.ParseExact(ROIViewUtility.GetFormattedDate(toDate), CustomDateFormat, theCultureInfo);
            }
            catch (FormatException)
            {
                ShowInlineError(fromCustomDate, "");
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
                return IsValidDateRange;
            }

            if (ROIViewUtility.IsValidDate(ROIViewUtility.GetFormattedDate(fromDate)))
            {
                ShowInlineError(fromCustomDate, "");
                toDateTime = DateTime.ParseExact(ROIViewUtility.GetFormattedDate(fromDate), CustomDateFormat, theCultureInfo);
            }
            else
            {
                ShowInlineError(toCustomDate, "");
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.InvalidSqlDate));
                IsValidDateRange = false;
                return IsValidDateRange;
            }

            if (ROIViewUtility.IsValidDate(ROIViewUtility.GetFormattedDate(toDate)))
            {
                ShowInlineError(toCustomDate, "");
                toDateTime = DateTime.ParseExact(ROIViewUtility.GetFormattedDate(toDate), CustomDateFormat, theCultureInfo);
            }
            else
            {
                ShowInlineError(fromCustomDate, "");
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.InvalidSqlDate));
                IsValidDateRange = false;
                return IsValidDateRange;
            }


            if (fromDateTime.Value > DateTime.Today)
            {
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.FutureDateSelected));
                IsValidDateRange = false;
                return IsValidDateRange;
            }

            if (toDateTime.Value > DateTime.Today)
            {
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.FutureDateSelected));
                IsValidDateRange = false;
                return IsValidDateRange;
            }
            return isValidDateRange;
        }

        private void ShowInlineError(Control ctrl, string message)
        {
            errorProvider.SetError(ctrl, message);
        }
        private string RetrieveMessage(string key)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            return rm.GetString(key);
        }
        /// <summary>
        /// Gets or sets the valid date range.
        /// </summary>
        public bool IsValidDateRange
        {
            get { return isValidDateRange; }
            set { isValidDateRange = value; }
        }


        private void startDateTimePicker_TextChanged(object sender, EventArgs e)
        {
            try
            {
                string fromdate = fromCustomDate.Text.Replace(" ", "");
                if ((!string.IsNullOrEmpty(fromdate) && !fromdate.Equals("//")) && fromdate.Length > 9)
                {
                    SetError(fromdate, toCustomDate.Text);
                }
            }
            catch (FormatException)
            {
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
            }
            ReportEvents.OnDateRangeChanged(Pane, e);
        }

        private void endDateTimePicker1_TextChanged(object sender, EventArgs e)
        {
            try
            {
                string todate = toCustomDate.Text.Replace(" ", "");
                if ((!string.IsNullOrEmpty(todate) && !todate.Equals("//")) && todate.Length > 9)
                {
                    SetError(fromCustomDate.Text, todate);
                }
            }
            catch (FormatException)
            {
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
            }
            ReportEvents.OnDateRangeChanged(Pane, e);
        }
    }
}
