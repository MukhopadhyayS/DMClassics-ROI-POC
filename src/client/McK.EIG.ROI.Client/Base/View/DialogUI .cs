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
using System.Configuration;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Dialog Box that display the message.
    /// </summary>
    public partial class DialogUI : Form
    {
        #region Fields

        private const string ObjectIsNull = "ObjectNull";
        private const string Error        = "Error";
        private const string DialogTitle = "dialogTitle.";

        #endregion

        #region Constructor

        public DialogUI(string message, ExecutionContext context, string errorType, string errorCode)
        {     
            InitializeComponent();
            this.Shown += new EventHandler(DialogUI_Shown);
            messageTextBox.SelectionChanged += new EventHandler(messageTextBox_SelectionChanged);
            ResourceManager rm;
            //Added for CR# 341396 & 346831
            string dialogTitle = (string.IsNullOrEmpty(errorType)) ? DialogTitle : errorType + "." + DialogTitle;
            // -------------
            try
            {
                if (context == null)
                {
                    MessageBox.Show(ConfigurationManager.AppSettings[ObjectIsNull],
                                    ConfigurationManager.AppSettings[Error], 
                                    MessageBoxButtons.OK, 
                                    MessageBoxIcon.Error,
                                    MessageBoxDefaultButton.Button1,
                                    0);
                }
                else
                {
                    rm = context.CultureManager.GetCulture(CultureType.Message.ToString());
                    picError.Image = (Image)global::McK.EIG.ROI.Client.Resources.images.Error;
                    message = string.IsNullOrEmpty(message.Trim()) ? rm.GetString(ROIErrorCodes.SystemError) : message;
                    messageTextBox.Text = message;
                    rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    this.Text = rm.GetString(dialogTitle + this.GetType().Name);
                    okButton.Text = ROIErrorCodes.ConnectFailure.Equals(errorCode) ? rm.GetString("LogoffButton" + "." + this.GetType().Name) : rm.GetString(okButton.Name + "." + this.GetType().Name);
                    messageTextBox.DeselectAll();
                    okButton.Focus();
                }
            }
            catch (MissingManifestResourceException ex)
            {
                MessageBox.Show(ex.Message, 
                                ConfigurationManager.AppSettings[Error], 
                                MessageBoxButtons.OK, 
                                MessageBoxIcon.Error,
                                MessageBoxDefaultButton.Button1, 
                                0);
            }
        }
        public DialogUI(string message, ExecutionContext context, bool invalidDirectoryFlag)
        {
            InitializeComponent();
            this.Shown += new EventHandler(DialogUI_Shown);
            messageTextBox.SelectionChanged += new EventHandler(messageTextBox_SelectionChanged);
            ResourceManager rm;
            // Added for CR# 341396
            string dialogTitle = (invalidDirectoryFlag) ? "invalidDir." + DialogTitle : DialogTitle;
            // -------------
            try
            {
                if (context == null)
                {
                    MessageBox.Show(ConfigurationManager.AppSettings[ObjectIsNull],
                                    ConfigurationManager.AppSettings[Error],
                                    MessageBoxButtons.OK,
                                    MessageBoxIcon.Error,
                                    MessageBoxDefaultButton.Button1,
                                    0);
                }
                else
                {
                    rm = context.CultureManager.GetCulture(CultureType.Message.ToString());
                    picError.Image = (Image)global::McK.EIG.ROI.Client.Resources.images.Error;
                    message = string.IsNullOrEmpty(message.Trim()) ? rm.GetString(ROIErrorCodes.SystemError) : message;
                    messageTextBox.Text = message;
                    rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    this.Text = rm.GetString(dialogTitle + this.GetType().Name);
                    okButton.Text = rm.GetString(okButton.Name + "." + this.GetType().Name);

                    messageTextBox.DeselectAll();
                    okButton.Focus();
                }
            }
            catch (MissingManifestResourceException ex)
            {
                MessageBox.Show(ex.Message,
                                ConfigurationManager.AppSettings[Error],
                                MessageBoxButtons.OK,
                                MessageBoxIcon.Error,
                                MessageBoxDefaultButton.Button1,
                                0);
            }
        }
        private void messageTextBox_SelectionChanged(object sender, EventArgs e)
        {
            messageTextBox.DeselectAll();
            okButton.Focus();
        }

        private void DialogUI_Shown(object sender, EventArgs e)
        {
            messageTextBox.DeselectAll();
            okButton.Focus();
        }
        #endregion

        #region Methods
        private void btnOK_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        #endregion
    }
}
