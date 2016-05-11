#region Copyright © 2008 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
 * Use of this material is governed by a license agreement. 
 * This material contains confidential, proprietary and trade 
 * secret information of McKesson Information Solutions and is 
 * protected under United States and international copyright and 
 * other intellectual property laws. Use, disclosure, 
 * reproduction, modification, distribution, or storage in a 
 * retrieval system in any form or by any means is prohibited 
 * without the prior express written permission of McKesson 
 * Information Solutions.
 */
#endregion

using System;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Text;
using System.Windows.Forms;
using System.Globalization;
using System.Resources;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Base.View
{
    public partial class CustomDateRange : ROIBaseUI
    {
        #region Fields
        
        private const string CustomDateFormat = "MM/dd/yyyy";
        private Nullable<DateTime> fromDate;
        private Nullable<DateTime> toDate;
        private string dateRangeOption;
        public event EventHandler PresetDateRangeHandler;
        private string emptyDate = " ";
        private bool isValidDateRange;
        private IFormatProvider theCultureInfo = new System.Globalization.CultureInfo("en-GB", true);

        #endregion

        #region Constructor

        public CustomDateRange()
        {
            InitializeComponent();
            customRangeCombo.SelectedIndex = 0;
            //fromCustomDate.Text= new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, 0, 0, 0).ToString();
            //toCustomDate.Text = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, 23, 59, 59).ToString();


            //fromCustomDate.Format = DateTimePickerFormat.Custom;
            //fromCustomDate.CustomFormat = CustomDateFormat;
            //toCustomDate.Format = DateTimePickerFormat.Custom;
            //toCustomDate.CustomFormat = CustomDateFormat;

            SetFromDateNullValue();
            SetToDateNullValue();
        }

        #endregion

        #region Methods
        
        /// <summary>
        /// Invoked when Date Range Combobox changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void rangeCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
            dateRangeOption = customRangeCombo.SelectedItem.ToString();
            this.fromCustomDate.TextChanged -= new System.EventHandler(this.fromCustomDate_ValueChanged);
            this.toCustomDate.TextChanged -= new System.EventHandler(this.toCustomDate_ValueChanged);
            switch (customRangeCombo.SelectedIndex)
            {
                case 0:
                    {
                        EnableDateControls(false);
                        SetFromDateNullValue();
                        SetToDateNullValue();
                        IsValidDateRange = false;
                        break;
                    }
                case 1:
                    {
                        EnableDateControls(false);
                        fromCustomDate.Text = DateTime.Today.AddDays(-30).ToString(CustomDateFormat, theCultureInfo);
                        break;
                    }
                case 2:
                    {
                        EnableDateControls(false);
                        fromCustomDate.Text = DateTime.Today.AddDays(-60).ToString(CustomDateFormat, theCultureInfo);
                        break;
                    }
                case 3:
                    {
                        EnableDateControls(false);
                        fromCustomDate.Text = DateTime.Today.AddDays(-90).ToString(CustomDateFormat, theCultureInfo);
                        break;
                    }
                case 4:
                    {
                        EnableDateControls(true);
                        fromCustomDate.Text = emptyDate;
                        toCustomDate.Text = emptyDate;
                        SetFromDateNullValue();
                        SetToDateNullValue();
                        IsValidDateRange = false;
                        break;
                    }
            }

            //In this scenario, if we use ternary operator using DateTime, error occurs as "Type of conditional expression  
            //cannot be determined because there is no implicit conversion between '<null>' and 'System.DateTime'"

            if (customRangeCombo.SelectedIndex == 0 || customRangeCombo.SelectedIndex == 4)
            {
               // fromCustomDate.Text = DateTime.Now.Date.ToString(CustomDateFormat);
                FromDate = null;
                ToDate = null;
                errorProvider.Clear();
            }
            else
            {
                toCustomDate.Text = DateTime.Now.Date.ToString(CustomDateFormat, theCultureInfo);
                FromDate = DateTime.ParseExact(fromCustomDate.Text, CustomDateFormat, theCultureInfo);
                ToDate = DateTime.ParseExact(toCustomDate.Text, CustomDateFormat, theCultureInfo);
            }
            
            if (customRangeCombo.SelectedIndex < 4 && PresetDateRangeHandler != null)
            {
                PresetDateRangeHandler(this, e);
            }

            this.toCustomDate.Leave -= new System.EventHandler(this.toCustomDate_ValueChanged);
            this.fromCustomDate.Leave -= new System.EventHandler(this.fromCustomDate_ValueChanged);
            this.toCustomDate.Leave += new System.EventHandler(this.toCustomDate_ValueChanged);
            this.fromCustomDate.Leave += new System.EventHandler(this.fromCustomDate_ValueChanged);
        }



        /// <summary>
        /// Invoked when From Date changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void fromCustomDate_ValueChanged(object sender, EventArgs e)
        {
            try
            {
                string fromdate = fromCustomDate.Text.Replace(" ", "");
                int checkLength = 9;
                string[] date = fromdate.Split(new char[] { '/' });
                if ((!string.IsNullOrEmpty(fromdate) && !fromdate.Equals("//")) && (fromdate.Length >= checkLength || date[2].Length>=2))
                {
                    SetError(fromdate, toCustomDate.Text);
                }
            }
            catch (FormatException)
            {
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
            }

        }

        /// <summary>
        /// Invoked when To Date changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void toCustomDate_ValueChanged(object sender, EventArgs e)
        {
            try
            {
                string todate = toCustomDate.Text.Replace(" ", "");
                int checkLength = 9;
                string[] date = todate.Split(new char[] { '/' });
                if ((!string.IsNullOrEmpty(todate) && !todate.Equals("//")) && (todate.Length >= checkLength || date[2].Length >= 2))
                {
                    SetError(fromCustomDate.Text, todate);
                }
            }
            catch (FormatException)
            {
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.InvalidDate));
                IsValidDateRange = false;
            }
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
                    ShowInlineError(fromCustomDate , "");
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
                ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.ToDateInvalid));
                IsValidDateRange = false;
            }
            if (toDateTime.Value < fromDateTime.Value)
            {
                ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.FromDateInvalid));
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

        /// <summary>
        /// Set From Date value
        /// </summary>
        private void SetFromDateNullValue()
        {
            fromCustomDate.Text = DateTime.Now.Date.ToString(CustomDateFormat, theCultureInfo);
            fromCustomDate.Text = emptyDate;
        }

        /// <summary>
        /// Set To date value.
        /// </summary>
        private void SetToDateNullValue()
        {
            toCustomDate.Text = DateTime.Now.Date.ToString(CustomDateFormat, theCultureInfo);
            toCustomDate.Text = emptyDate;            
        }

        /// <summary>
        /// Enable or Disable the From and To Date Picker.
        /// </summary>
        /// <param name="status"></param>
        private void EnableDateControls(bool status)
        {
            fromCustomDate.Enabled = status;
            toCustomDate.Enabled   = status;
        }

        /// <summary>
        /// Validate the From and To Date.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        //private void ValidateDateRange(object sender, EventArgs e)
        //{
        //    if ((DateTime.ParseExact(fromCustomDate.Text, CustomDateFormat, theCultureInfo)).Date <= (DateTime.ParseExact(toCustomDate.Text, CustomDateFormat, theCultureInfo)).Date)
        //    {
        //        errorProvider.Clear();
        //        FromDate = DateTime.ParseExact(fromCustomDate.Text, CustomDateFormat, theCultureInfo);
        //        ToDate = DateTime.ParseExact(toCustomDate.Text, CustomDateFormat, theCultureInfo);
        //        if (PresetDateRangeHandler != null)
        //            PresetDateRangeHandler(this, e);
        //        IsValidDateRange = true;
        //    }
        //    else
        //    {
        //        if (sender.Equals(toCustomDate))
        //        {
        //            ShowInlineError(toCustomDate, RetrieveMessage(ROIErrorCodes.FromDateInvalid));
        //        }
        //        else
        //        {
        //            ShowInlineError(fromCustomDate, RetrieveMessage(ROIErrorCodes.ToDateInvalid));
        //        }
        //        IsValidDateRange = false;
        //    }
        //}

        //Reset the PresetDate Range Value
        public void Reset()
        {
            customRangeCombo.SelectedIndex = 0;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, fromLabel);
            SetLabel(rm, toLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, customRangeCombo);
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control == customRangeCombo)
            {
                return control.Name + "." +GetType().Name;
            }
            return base.GetLocalizeKey(control, toolTip);
        }

        private string RetrieveMessage(string key)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            return rm.GetString(key);
        }

        private void ShowInlineError(Control ctrl, string message)
        {
            if (message.Length > 0)
            {
                errorProvider.SetError(ctrl, "");
            }
            errorProvider.SetError(ctrl, message);
        }

        //private void fromCustomDate_Enter(object sender, EventArgs e)
        //{
        //    //ROIViewUtility.SetCurrentDate(fromCustomDate);
        //}

        //private void fromCustomDate_MouseUp(object sender, MouseEventArgs e)
        //{
        //    //ROIViewUtility.SetCurrentDate(fromCustomDate);
        //}

        //private void toCustomDate_Enter(object sender, EventArgs e)
        //{
        //   // ROIViewUtility.SetCurrentDate(toCustomDate);
        //}

        //private void toCustomDate_MouseUp(object sender, MouseEventArgs e)
        //{
        //   // ROIViewUtility.SetCurrentDate(toCustomDate);
        //}

        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the From Date
        /// </summary>
        public Nullable<DateTime> FromDate
        {
            get { return fromDate; }
            set { fromDate = value; }
        }

        /// <summary>
        /// Gets or sets the To Date
        /// </summary>
        public Nullable<DateTime> ToDate
        {
            get { return toDate; }
            set { toDate = value; }
        }

        /// <summary>
        /// Gets or sets the Date option
        /// </summary>
        public String  DateRangeOption
        {
            get { return dateRangeOption; }
            set 
            { 
                dateRangeOption = value;
            }
        }

        /// <summary>
        /// Gets or sets the valid date range.
        /// </summary>
        public bool IsValidDateRange
        {
            get { return isValidDateRange; }
            set { isValidDateRange = value; }
        }

        #endregion
    }
}
