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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class LogOnUI : ROIBaseUI
    {
        #region Fields

        internal ErrorProvider errorProvider;
        private EventHandler textChanged;
        private EventHandler logOnHandler;
        private EventHandler changePasswordHandler;
        private bool isLdapEnabled;

        #endregion

        #region Constructor

        public LogOnUI(EventHandler logOnHandler, EventHandler changePasswordHandler, bool isLdapEnabled)
        {
            InitializeComponent();
            this.logOnHandler = logOnHandler;
            this.changePasswordHandler = changePasswordHandler;
            this.isLdapEnabled = isLdapEnabled;
            InitErrorProvider();
            EnableEvents();
            EnableButtons(false);

            if (!isLdapEnabled)
            {
                lockPictureBox.Image = ROIImages.LogonImage;
            }
            else
            {
                headerLabel.Visible = false;
                lockPictureBox.Image = ROIImages.LogonImage;
                flowLayoutPanel.Padding = new Padding(0, 0, 0, 60);
                outerWhitePanel.BorderStyle = BorderStyle.None;
                outerWhitePanel.BackColor = System.Drawing.Color.FromArgb(246, 246, 246);
            }

            EnableDomainList();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes error provider
        /// </summary>
        private void InitErrorProvider()
        {
            errorProvider = new ErrorProvider();
            errorProvider.BlinkStyle = ErrorBlinkStyle.NeverBlink;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the LogOnUI local events
        /// </summary>
        private void EnableEvents()
        {
            textChanged += new EventHandler(Process_TextChanged);
            userIdTextBox.TextChanged += textChanged;
            PasswordTextBox.TextChanged += textChanged;
            domainComboBox.SelectedIndexChanged += textChanged;

            userIdTextBox.KeyDown += new KeyEventHandler(userIdTextBox_KeyDown);
            PasswordTextBox.KeyDown += new KeyEventHandler(passwordTextBox_KeyDown);
            logonButton.Click += new EventHandler(logonButton_Click);
            changePasswordButton.Click += new EventHandler(changePasswordButton_Click);
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, headerLabel);
            if (!isLdapEnabled)
            {
                SetLabel(rm, userIdLabel);
                SetLabel(rm, passwordLabel);
                SetLabel(rm, domainLabel);
                SetLabel(rm, logonButton);
            }
            else
            {
                userIdLabel.Text = rm.GetString(userIdLabel.Name + ".DSLogOnUI");
                passwordLabel.Text = rm.GetString(passwordLabel.Name + ".DSLogOnUI");
                domainLabel.Text = rm.GetString(domainLabel.Name + ".DSLogOnUI");
                logonButton.Text = rm.GetString(logonButton.Name + ".DSLogOnUI");
            }
            SetLabel(rm, requiredLabel);
            SetLabel(rm, changePasswordButton);
            SetLabel(rm, cancelButton);
        }

        /// <summary>
        /// Gets UI controls name to localize
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == requiredLabel)
            {
                return base.GetLocalizeKey(control);
            }
            return control.Name + "." + GetType().Name;
        }


        /// <summary>
        /// Occurs when a key is pressed while the userid textbox has focus.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void userIdTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            //To prevent the Undo operation.
            if ((e.KeyData == (Keys.Z | Keys.Control)) || (e.KeyData == (Keys.Back | Keys.Alt)))
            {
                userIdTextBox.ClearUndo();
            }
            if (e.KeyValue == 13 && userIdTextBox.Text.Length > 0 && !userIdTextBox.Text.Contains(" "))
            {
                PasswordTextBox.Focus();
            }
            e.Handled = false;
        }

        /// <summary>
        /// Occurs when a key is pressed while the password control has focus.
        /// /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void passwordTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                logOnHandler(logonButton, e);
            }
            //To prevent the Undo operation.
            if ((e.KeyData == (Keys.Z | Keys.Control)) || (e.KeyData == (Keys.Back | Keys.Alt)))
            {
                PasswordTextBox.ClearUndo();
            }
            e.Handled = false;
        }

        /// <summary>
        /// Gets the data from UI.
        /// </summary>
        /// <returns></returns>
        internal UserData GetData()
        {
            errorProvider.Clear();
            UserData.Instance.Reset(false);
            UserData.Instance.UserId = userIdTextBox.Text;
            UserData.Instance.LDAPUserId = userIdTextBox.Text;
            if (UserData.Instance.IsLdapEnabled)
            {
                UserData.Instance.DomainSecretWord = PasswordTextBox.Text;
                UserData.Instance.Domain = domainComboBox.SelectedItem.ToString();
            }
            else
            {
                UserData.Instance.SecretWord = ROIController.EncryptPassword(PasswordTextBox.Text);
            }

            return UserData.Instance;
        }

        /// <summary>
        /// Occurs when user changed the text in userid or password control.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// //Process
        private void Process_TextChanged(object sender, EventArgs e)
        {
            if ((string.IsNullOrEmpty(userIdTextBox.Text.Trim()) ||
                string.IsNullOrEmpty(PasswordTextBox.Text.Trim())) ||
                (UserData.Instance.IsLdapEnabled ? string.IsNullOrEmpty(domainComboBox.SelectedItem.ToString()) : false))
            {
                EnableButtons(false);
            }
            else
            {
                EnableButtons(true);
            }
        }

        /// <summary>
        /// Enable the buttons.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableButtons(bool enable)
        {
            logonButton.Enabled = enable;
            changePasswordButton.Enabled = enable;
            if (logonButton.Enabled)
            {
                this.ParentForm.AcceptButton = logonButton;
            }
        }

        private void EnableDomainList()
        {
            pictureBox1.Visible = domainComboBox.Visible = domainLabel.Visible = UserData.Instance.IsLdapEnabled;
            if (UserData.Instance.IsLdapEnabled)
            {
                changePasswordButton.Visible = false;
                logonButton.Location = new System.Drawing.Point(317, 209);
                if (UserData.Instance.DomainList.Count == 0 || UserData.Instance.DomainList.Count > 1)
                {
                    UserData.Instance.DomainList.Insert(0, string.Empty);
                }
                domainComboBox.DataSource = UserData.Instance.DomainList;
                if (!string.IsNullOrEmpty(ConfigurationManager.AppSettings["DomainName"]))
                {
                    domainComboBox.SelectedItem = ConfigurationManager.AppSettings["DomainName"];
                }
                else
                {
                    domainComboBox.SelectedIndex = 0;
                }

            }
            else
            {
                changePasswordButton.Visible = true;
            }
        }

        /// <summary>
        /// Occurs when user clicks on Logon button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void logonButton_Click(object sender, EventArgs e)
        {
            WinDSSConfig winDSSObj = new WinDSSConfig();
            winDSSObj.DefaultSetup();
            OCSecurityWrapper.loadDll();
            logOnHandler(logonButton, e);
        }

        /// <summary>
        /// Occurs when user clicks on Change Password button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void changePasswordButton_Click(object sender, EventArgs e)
        {
            GetData();
            OCSecurityWrapper.loadDll();
            changePasswordHandler(null, null);
        }

        /// <summary>
        /// Occurs when user clicks on Cancel button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            (Pane as LogOnPane).LogOnForm.Close();
        }

        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.UserIdEmpty: return userIdTextBox;
                case ROIErrorCodes.SecretWordEmpty: return PasswordTextBox;
                case ROIErrorCodes.InvalidDomain: return domainComboBox;
            }
            return null;
        }

        internal void SelectControl()
        {
            if (userIdTextBox.Focused)
            {
                userIdTextBox.SelectAll();
            }
            else
            {
                PasswordTextBox.SelectAll();
            }
        }

        #endregion
    }
}
