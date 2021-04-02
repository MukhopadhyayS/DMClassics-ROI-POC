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
using System.Globalization;
using System.Windows.Forms;
using System.Resources;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class ChangePasswordUI : ROIBaseUI
    { 
        #region Fields

        private const string success = "success";        
        
        #endregion

        #region Constructor

        private ChangePasswordUI()
        {
            InitializeComponent();            
            EnableEvents();
            EnableButtons(false);            
        }

        public ChangePasswordUI(ExecutionContext context) : this()           
        {           
            SetExecutionContext(context);
            currentPasswordTextBox.Focus();
        }

        #endregion

        #region Methods

        /// <summary>
        ///  This method is used to enable(subscribe)the ChangePasswordUI local events
        /// </summary>
        private void EnableEvents()
        { 
           currentPasswordTextBox.TextChanged += new EventHandler(ChangePassword_TextChanged);
           newPasswordTextBox.TextChanged     += new EventHandler(ChangePassword_TextChanged);
           confirmPasswordTextBox.TextChanged += new EventHandler(ChangePassword_TextChanged);
           currentPasswordTextBox.KeyDown     += new KeyEventHandler(currentPasswordTextBox_KeyDown);
           newPasswordTextBox.KeyDown         += new KeyEventHandler(newPasswordTextBox_KeyDown);
           confirmPasswordTextBox.KeyDown     += new KeyEventHandler(confirmPasswordTextBox_KeyDown);           
        }

        
       /// <summary>
       /// When user enters on the the current password textbox then the focus will shifted to new password textbox
       /// </summary>

        private void currentPasswordTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyValue == 13 && currentPasswordTextBox.Text.Length > 0 && !currentPasswordTextBox.Text.Contains(" "))
            {
                newPasswordTextBox.Focus();
            }
        }

        /// <summary>
        /// When user enters on the the new password textbox then the focus will shifted to confirm password textbox
        /// </summary>

        void newPasswordTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyValue == 13 && newPasswordTextBox.Text.Length > 0 && !newPasswordTextBox.Text.Contains(" "))
            {
                confirmPasswordTextBox.Focus();
            }
        }

        void confirmPasswordTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                okButton_Click(okButton, e);
            }
            e.Handled = false;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, currentPasswordLabel);
            SetLabel(rm, newPasswordLabel);
            SetLabel(rm, confirmPasswordLabel);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, okButton);
            SetLabel(rm, cancelButton);
        }

        /// <summary>
        ///  Localize the UI text.
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
        /// Occurs when user changed the text in current, new & confirm password controls.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ChangePassword_TextChanged(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(currentPasswordTextBox.Text.Trim()) ||
                  string.IsNullOrEmpty(newPasswordTextBox.Text.Trim()) ||
                  string.IsNullOrEmpty(confirmPasswordTextBox.Text.Trim()))
            {
                EnableButtons(false);
            }
            else
            {
                EnableButtons(true);
            }
        }

        /// <summary>
        /// Enable the buttons
        /// </summary>
        /// <param name="enable"></param>
        private void EnableButtons(bool enable)
        {
            okButton.Enabled = enable;
        }

        /// <summary>
        /// Occurs when user clicks on OK button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void okButton_Click(object sender, EventArgs e)
        {
            try
            {
                if (CheckMandatoryFields())
                {
                    ROIViewUtility.MarkBusy(true);
                    ROIController roiController = ROIController.Instance;
                    string encryptedSecretWord = ROIController.EncryptPassword(newPasswordTextBox.Text);
                    UserData.Instance.NewSecretWord = encryptedSecretWord;
                    if (UserData.Instance.ConfigurationData == null)
                    {
                        UserData.Instance.ConfigurationData = ConfigurationData.Instance;
                    }

                    if (string.Compare(roiController.ChangePassword(UserData.Instance), success, true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

                        string titleText = rm.GetString("changepasswordsuccess.title");
                        string messageText = rm.GetString("changepasswordsuccess.message");
                        ShowDialog(titleText, messageText);
                        this.ParentForm.DialogResult = DialogResult.OK;
                        UserData.Instance.SecretWord = encryptedSecretWord;
                    }
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }                
        }

        /// <summary>
        /// Validates input values
        /// </summary>
        /// <returns></returns>
        public bool CheckMandatoryFields()
        {
            bool flag = true;
            errorProvider.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());     

            if (string.IsNullOrEmpty(currentPasswordTextBox.Text))
            {
                errorProvider.SetError(currentPasswordTextBox, "Current Password cannot be empty");
                flag = false;
            }
            if (string.IsNullOrEmpty(newPasswordTextBox.Text))
            {
                errorProvider.SetError(newPasswordTextBox, "New Password cannot be empty");
                flag = false;
            }
            if (string.IsNullOrEmpty(confirmPasswordTextBox.Text))
            {
                errorProvider.SetError(confirmPasswordTextBox, "Confirm Password cannot be empty");
                flag = false;                
            }

            if (!string.IsNullOrEmpty(currentPasswordTextBox.Text))
            {
                if (!Validator.Validate(currentPasswordTextBox.Text, ROIConstants.AllCharacters))
                {
                    errorProvider.SetError(currentPasswordTextBox, rm.GetString(ROIErrorCodes.InvalidSecretWord));
                    flag = false;
                }
            }
            if (!string.IsNullOrEmpty(newPasswordTextBox.Text))
            {
                if (!Validator.Validate(newPasswordTextBox.Text, ROIConstants.AllCharacters))
                {
                    errorProvider.SetError(newPasswordTextBox, rm.GetString(ROIErrorCodes.InvalidSecretWord));
                    flag = false;
                }
            }
            if (!string.IsNullOrEmpty(confirmPasswordTextBox.Text))
            {
                if (!Validator.Validate(confirmPasswordTextBox.Text, ROIConstants.AllCharacters))
                {
                    errorProvider.SetError(confirmPasswordTextBox, rm.GetString(ROIErrorCodes.InvalidSecretWord));
                    flag = false;
                }
            }

            if (flag)
            {
                string titleText = rm.GetString("changepasswordfailed.title");
                
                if(!ROIController.EncryptPassword(currentPasswordTextBox.Text).Equals(UserData.Instance.SecretWord))
                {
                    string messageText = rm.GetString("CurrentPasswordDoNotMatch");                    
                    this.ParentForm.DialogResult = DialogResult.None;
                    ShowDialog(titleText, messageText);
                    currentPasswordTextBox.Select(0, currentPasswordTextBox.Text.Length);
                    return false;
                }

                if (currentPasswordTextBox.Text.Equals(newPasswordTextBox.Text))
                {
                    string messageText = rm.GetString("CurrentAndNewPasswordSame");
                    this.ParentForm.DialogResult = DialogResult.None;
                    ShowDialog(titleText, messageText);
                    newPasswordTextBox.Text = string.Empty;
                    confirmPasswordTextBox.Text = string.Empty;
                    newPasswordTextBox.Focus();
                    return false;
                }

                if (!newPasswordTextBox.Text.Equals(confirmPasswordTextBox.Text))
                {   
                    string messageText = rm.GetString("NewAndConfirmPasswordNotSame");
                    this.ParentForm.DialogResult = DialogResult.None;
                    ShowDialog(titleText, messageText);
                    newPasswordTextBox.Select(0, newPasswordTextBox.Text.Length);
                    return false;
                }
            }
            return flag;
        }
        
        /// <summary>
        /// Shows the dialog.
        /// </summary>
        /// <param name="titleText"></param>
        /// <param name="message"></param>
        private void ShowDialog(string titleText, string message)
        {   
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            ROIViewUtility.ConfirmChanges(titleText,
                                          message,
                                          rm.GetString("okButton.DialogUI"),
                                          string.Empty,
                                          ROIDialogIcon.Alert);           
        }

        #endregion

        private void ChangePasswordUI_Load(object sender, EventArgs e)
        {
            currentPasswordTextBox.Focus();
        }

    }
}
