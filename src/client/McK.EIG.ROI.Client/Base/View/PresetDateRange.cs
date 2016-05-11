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
    public partial class PresetDateRange : ROIBaseUI
    {
        #region Fields
        
        private const string CustomDateFormat = "MM/dd/yyyy";

        private Nullable<DateTime> fromDate;
        private Nullable<DateTime> toDate;
        private string dateRangeOption;
        public event EventHandler PresetDateRangeHandler;
        private string emptyDate = " ";
        private bool isValidDateRange;

        #endregion

        #region Constructor

        public PresetDateRange()
        {
            InitializeComponent();
            rangeCombo.SelectedIndex = 0;
            fromNullableDTP.Value = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, 0, 0, 0);
            toNullableDTP.Value   = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, 23, 59, 59);

            //fromNullableDTP.Format = DateTimePickerFormat.Custom;
            //fromNullableDTP.CustomFormat = CustomDateFormat;
            //toNullableDTP.Format = DateTimePickerFormat.Custom;
            //toNullableDTP.CustomFormat = CustomDateFormat;

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
            dateRangeOption = rangeCombo.SelectedItem.ToString();
            this.fromNullableDTP.ValueChanged -= new System.EventHandler(this.fromNullableDTP_ValueChanged);
            this.toNullableDTP.ValueChanged -= new System.EventHandler(this.toNullableDTP_ValueChanged);
            switch (rangeCombo.SelectedIndex)
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
                        fromNullableDTP.DateValue = DateTime.Today.AddDays(-29);
                        break;
                    }
                case 2:
                    {
                        EnableDateControls(false);
                        fromNullableDTP.DateValue = DateTime.Today.AddDays(-59);
                        break;
                    }
                case 3:
                    {
                        EnableDateControls(false);
                        fromNullableDTP.DateValue = DateTime.Today.AddDays(-89);
                        break;
                    }
                case 4:
                    {
                        EnableDateControls(true);
                        SetFromDateNullValue();
                        SetToDateNullValue();
                        IsValidDateRange = false;
                        break;
                    }
            }

            //In this scenario, if we use ternary operator using DateTime, error occurs as "Type of conditional expression  
            //cannot be determined because there is no implicit conversion between '<null>' and 'System.DateTime'"

            if (rangeCombo.SelectedIndex == 0 || rangeCombo.SelectedIndex == 4)
            {
                fromNullableDTP.Value = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, 0, 0, 0);
                FromDate = null;
                ToDate = null;
            }
            else
            {
                toNullableDTP.DateValue = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, 23, 59, 59);
                FromDate = (DateTime)fromNullableDTP.DateValue;
                ToDate = (DateTime)toNullableDTP.DateValue;
            }
            
            if (rangeCombo.SelectedIndex < 4 && PresetDateRangeHandler != null)
            {
                PresetDateRangeHandler(this, e);
            }

            this.fromNullableDTP.ValueChanged += new System.EventHandler(this.fromNullableDTP_ValueChanged);
            this.toNullableDTP.ValueChanged += new System.EventHandler(this.toNullableDTP_ValueChanged);
        }

        /// <summary>
        /// Invoked when From Date changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void fromNullableDTP_ValueChanged(object sender, EventArgs e)
        {
            if (fromNullableDTP.DateValue != null)
            {
                if (((DateTime)fromNullableDTP.DateValue).Date <= DateTime.Today)
                {
                    errorProvider.Clear();
                    if (toNullableDTP.DateValue != null)
                    {
                        if (((DateTime)toNullableDTP.DateValue).Date <= DateTime.Today)
                        {
                            ValidateDateRange(sender, e);
                        }
                        else
                        {
                            errorProvider.Clear();
                            ShowInlineError(toNullableDTP, RetrieveMessage(ROIErrorCodes.FutureDateSelected)); 
                        }
                    }
                }
                else
                {
                    ShowInlineError(fromNullableDTP, RetrieveMessage(ROIErrorCodes.FutureDateSelected));
                }
            }
        }

        /// <summary>
        /// Invoked when To Date changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void toNullableDTP_ValueChanged(object sender, EventArgs e)
        {
            if (toNullableDTP.DateValue != null)
            {
                DateTime dateTime = ((DateTime)toNullableDTP.DateValue).Date;
                toNullableDTP.DateValue = new DateTime(dateTime.Year, dateTime.Month, dateTime.Day, 23, 59, 59);
                if (((DateTime)toNullableDTP.DateValue).Date <= DateTime.Today)
                {
                    errorProvider.Clear();
                    if (fromNullableDTP.DateValue != null)
                    {
                        if (((DateTime)fromNullableDTP.DateValue).Date <= DateTime.Today)
                        {
                            ValidateDateRange(sender, e);
                        }
                        else
                        {
                            ShowInlineError(fromNullableDTP, RetrieveMessage(ROIErrorCodes.FutureDateSelected));
                        }
                    }
                }
                else
                {
                    ShowInlineError(toNullableDTP, RetrieveMessage(ROIErrorCodes.FutureDateSelected));
                }
            }
        }

        /// <summary>
        /// Set From Date value
        /// </summary>
        private void SetFromDateNullValue()
        {
            fromNullableDTP.Value = DateTime.Now.Date;
            fromNullableDTP.DateValue = emptyDate;
            fromNullableDTP.NullValue = emptyDate;
        }

        /// <summary>
        /// Set To date value.
        /// </summary>
        private void SetToDateNullValue()
        {
            toNullableDTP.DateValue = DateTime.Now.Date;
            toNullableDTP.DateValue = emptyDate;
            toNullableDTP.NullValue = emptyDate;
        }

        /// <summary>
        /// Enable or Disable the From and To Date Picker.
        /// </summary>
        /// <param name="status"></param>
        private void EnableDateControls(bool status)
        {
            fromNullableDTP.Enabled = status;
            toNullableDTP.Enabled   = status;
        }

        /// <summary>
        /// Validate the From and To Date.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ValidateDateRange(object sender, EventArgs e)
        {
            if (((DateTime)fromNullableDTP.DateValue).Date <= ((DateTime)toNullableDTP.DateValue).Date)
            {
                errorProvider.Clear();
                FromDate = (DateTime)fromNullableDTP.DateValue;
                ToDate   = (DateTime)toNullableDTP.DateValue;
                if (PresetDateRangeHandler != null)
                    PresetDateRangeHandler(this, e);
                IsValidDateRange = true;
            }
            else
            {
                if (sender.Equals(toNullableDTP))
                {
                    ShowInlineError(toNullableDTP, RetrieveMessage(ROIErrorCodes.FromDateInvalid));
                }
                else
                {
                    ShowInlineError(fromNullableDTP, RetrieveMessage(ROIErrorCodes.ToDateInvalid));
                }
            }
        }

        //Reset the PresetDate Range Value
        public void Reset()
        {
            rangeCombo.SelectedIndex = 0;
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
            SetTooltip(rm, toolTip, rangeCombo);
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control == rangeCombo)
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
            errorProvider.SetError(ctrl, message);
        }

        private void fromNullableDTP_Enter(object sender, EventArgs e)
        {
            ROIViewUtility.SetCurrentDate(fromNullableDTP);
        }

        private void fromNullableDTP_MouseUp(object sender, MouseEventArgs e)
        {
            ROIViewUtility.SetCurrentDate(fromNullableDTP);
        }

        private void toNullableDTP_Enter(object sender, EventArgs e)
        {
            ROIViewUtility.SetCurrentDate(toNullableDTP);
        }

        private void toNullableDTP_MouseUp(object sender, MouseEventArgs e)
        {
            ROIViewUtility.SetCurrentDate(toNullableDTP);
        }

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
            set { dateRangeOption = value; }
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
