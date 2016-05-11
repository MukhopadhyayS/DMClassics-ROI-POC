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
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Text.RegularExpressions;
using System.Globalization;

namespace McK.EIG.ROI.Client.Base.View.Common
{
    public partial class MaskedEditDateControl : MaskedTextBox
    {
        #region Fields
                
        private int startPosition;        
        private bool isValidDate;
        private string formattedDate;
        
        #endregion

        #region Constructor

        public MaskedEditDateControl(): base()
        {            
            this.Mask = "AA/AA/AAAA";            
            this.PromptChar = ' ';
            this.InsertKeyMode = InsertKeyMode.Overwrite;
        }

        #endregion

        #region Private methods/properties

        private void GetFormattedDate(int position)
        {
            try
            {
                string[] str = this.Text.Split('/');
                str[position] = Convert.ToInt32(str[position], CultureInfo.InvariantCulture).ToString("00", CultureInfo.InvariantCulture);
                this.Text = str[0] + "/" + str[1] + "/" + str[2];
            }
            catch (FormatException)
            {
                return;
            }
        }     


        #endregion

        #region Public properties

        public bool IsValidDate
        {
            get { return isValidDate; }
            set { isValidDate = value; }
        }

        public string FormattedDate
        {
            get { return formattedDate; }
            set { formattedDate = value; }
        }

        #endregion

        #region Events

        protected override void OnEnter(EventArgs e)
        {
            SendKeys.Send("{HOME}");
            this.Mask = "00/00/0000";
            base.OnEnter(e);
        }

        protected override void OnKeyPress(KeyPressEventArgs e)
        {
            startPosition = this.SelectionStart;
            if (e.KeyChar == '/')
            {
                if (startPosition == 0 || startPosition == 1 || startPosition == 2)
                {
                    GetFormattedDate(0);
                    this.SelectionStart = 3;
                }
                else if (startPosition == 3 || startPosition == 4 || startPosition == 5)
                {
                    GetFormattedDate(1);
                    this.SelectionStart = 6;
                }
                else
                {
                    this.SelectionStart = 0;
                }
            }
            base.OnKeyPress(e);
        }

        protected override void OnGotFocus(EventArgs e)
        {
            this.BeginInvoke((MethodInvoker)delegate()
            {
                this.Select(0, 0);
            });                  
            base.OnGotFocus(e);
        }

        
        protected override void OnMouseClick(MouseEventArgs e)
        {
            this.BeginInvoke((MethodInvoker)delegate()
            {
                this.Select(0, 0);
            });                  
            base.OnMouseClick(e);
        }

        protected override void OnKeyDown(KeyEventArgs e)
        {
            string defaultValue = "  /  /    ";
            if (this.Text.Trim() == defaultValue.Trim())
            {
                e.Handled = false;
                base.OnKeyDown(e);
                return;
            }
            int location = this.SelectionStart;
            MaskedTextProvider provider = this.MaskedTextProvider;
            if (e.KeyCode == Keys.Delete || e.KeyCode == Keys.Back)
            {
                e.Handled = true;                
            }

            if (e.KeyCode == Keys.Delete || this.SelectionLength > 0)// && (e.KeyCode != Keys.BrowserBack)|| (e.KeyCode != Keys.ShiftKey))
            {
                if (this.SelectionLength > 0)
                {
                    string[] str = this.SelectedText.Split('/');
                    int length = 0;
                    for (int count = 0; count < str.Length; count++)
                    {
                        length += str[count].Length;
                    }
                    for (int count = 0; count < length; count++)
                    {
                        if (provider[location + count] == '/')
                        {
                            length++;
                        }                        
                        provider.Replace(' ', location + count);

                        if (Control.ModifierKeys != Keys.Shift)
                        {
                            this.Text = provider.ToString();
                        }
                    }

                    if (Control.ModifierKeys != Keys.Shift)
                    {
                        this.SelectionStart = location;
                    }
                 }
                else if (this.Text.Length > location)
                {
                    if (this.Text[location] != '/')
                    {
                        provider.Replace(' ', location);
                        this.Text = provider.ToString();
                        this.SelectionStart = location;
                    }
                }
              }
            else if (e.KeyCode == Keys.Back && location > 0)
            {
                if (this.Text.Length >= location && this.Text[location - 1] != '/')
                {
                    provider.Replace(' ', location - 1);
                    this.Text = provider.ToString();
                    this.SelectionStart = location - 1;
                }
                else
                {
                    if (location > 0)
                    {
                        this.SelectionStart = location - 1;
                    }
                }
            }                       
        }

        protected override void OnTextChanged(EventArgs e)
        {
            try
            {
                formattedDate = ROIViewUtility.GetFormattedDate(this.Text);
                isValidDate = true;
            }
            catch (FormatException)
            {
                isValidDate = false;
                formattedDate = null;
            }
            base.OnTextChanged(e);
        }

        protected override void OnLeave(EventArgs e)
        {
            if(isValidDate)
            {
                this.Text = formattedDate;
            }
            base.OnLeave(e);
        }

        #endregion
    }
}
