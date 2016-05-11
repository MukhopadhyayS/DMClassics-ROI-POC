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
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.ComponentModel;
using System.Runtime.InteropServices;
using System.Globalization;
using System.Threading;
using System.Security.Permissions;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public class NullableDateTimePicker : DateTimePicker
    {
        #region Fields

        private bool isNull;
        private string nullValue;
        private DateTimePickerFormat format;
        private string customFormat;
        private bool isDeleteKeyPressed;

        #endregion

        #region Constructor
        
        public NullableDateTimePicker() : base()
        {
          base.Format = DateTimePickerFormat.Custom;
          NullValue = String.Empty;
          Format = DateTimePickerFormat.Short;
        }

        #endregion

        #region Public properties

        [Bindable(true)]
        [Browsable(false)]
        public object DateValue 
        {
          get 
          {
              if (isNull)
                  return null;
              else
                  return base.Value;
          }
          set 
          {
            if (value == null || value == DBNull.Value || value.GetType().Name == typeof(string).Name)
            {
              SetToNullValue();
            }
            else 
            {
              SetToDateTimeValue();
              base.Value = (DateTime)value;
            }
          }
        }

        [Browsable(true)]
        [DefaultValue(DateTimePickerFormat.Long), TypeConverter(typeof(Enum))]
        public new DateTimePickerFormat Format
        {
            get { return format; }
            set
            {
                format = value;
                if (!isNull)
                    SetFormat();
                OnFormatChanged(EventArgs.Empty);
            }
        }

        public new String CustomFormat
        {
          get { return customFormat; }
          set { customFormat = value; }
        }

        [Browsable(true)]
        [Category("Behavior")]
        [Description("The string used to display null values in the control")]
        [DefaultValue(" ")]
        public String NullValue
        {
          get { return nullValue; }
          set { nullValue = value; }
        }
        #endregion

        #region Private methods/properties

        private string FormatAsString
        {
            set
            {
                base.CustomFormat = value;
            }
        }

        private void SetFormat()
        {
            if (Format == DateTimePickerFormat.Custom)
            {
                FormatAsString = CustomFormat;
            }
            else 
            {
                CultureInfo ci = Thread.CurrentThread.CurrentCulture;
                DateTimeFormatInfo dtf = ci.DateTimeFormat;
                FormatAsString = dtf.ShortDatePattern;
            }
        }

        private void SetToNullValue()
        {
          isNull = true;
          base.CustomFormat = String.IsNullOrEmpty(nullValue) ? String.Empty : "'" + nullValue + "'";
        }

        private void SetToDateTimeValue()
        {
          if (isNull) 
          {
            SetFormat();
            isNull = false;
            base.OnValueChanged(new EventArgs());
          }
        }
        #endregion

        #region Events

        [SecurityPermission(SecurityAction.LinkDemand, Flags = SecurityPermissionFlag.UnmanagedCode)]
        protected override void WndProc(ref Message m)
        {
            if (isNull)
            {
                if (m.Msg == 0x4e)
                {
                    NMHDR nm = (NMHDR)m.GetLParam(typeof(NMHDR));
                    if (nm.Code == -746 || nm.Code == -722)
                        SetToDateTimeValue();
                }
            }
            base.WndProc(ref m);
        }

        [StructLayout(LayoutKind.Sequential)]
        private struct NMHDR
        {
            public IntPtr HwndFrom;
            public int IdFrom;
            public int Code;
        }

        protected override void OnKeyUp(KeyEventArgs e)
        {
          if (e.KeyCode == Keys.Delete)
          {
            this.DateValue = null;
            OnValueChanged(EventArgs.Empty);
            isDeleteKeyPressed = true;
          }
          base.OnKeyUp(e);
        }

        protected override void OnValueChanged(EventArgs eventargs)
        {
          base.OnValueChanged(eventargs);
        }

        protected override void OnEnter(EventArgs e)
        {
            base.OnEnter(e);
            if (isDeleteKeyPressed)
            {
                SendKeys.Send("{RIGHT}");
                isDeleteKeyPressed = false;
            }
        }

        protected override void OnMouseEnter(EventArgs e)
        {
            base.OnMouseEnter(e);
            if (isDeleteKeyPressed)
            {
                SendKeys.Send("{RIGHT}");
                isDeleteKeyPressed = false;
            }
        }

        #endregion
    }
}
