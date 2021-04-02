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
using System.Drawing;
using System.Data;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class SelfMappingUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;

        private EventHandler textChanged;

        private const string ExitSelfMappingTitle = "selfMappingUI.title";
        private const string ExitSelfMappingHeaderText = "ExitSelfMappingUI.Header";
        private const string ExitSelfMappingMessage = "ExitSelfMappingUI.Message";
        private const string ExitSelfMappingYesButton = "ExitSelfMappingUI.YesButton";
        private const string ExitSelfMappingNoButton = "ExitSelfMappingUI.NoButton";
        private const string ExitSelfMappingYesButtonToolTip = "ExitSelfMappingUI.YesButton";
        private const string ExitSelfMappingNoButtonToolTip = "ExitSelfMappingUI.NoButton";

        private const int InvalidHPFUserMapping = 7;

        #endregion

        #region Constructor

        public SelfMappingUI()
        {
            InitializeComponent();
        }

        public SelfMappingUI(ExecutionContext context)
            : this()
        {
            SetExecutionContext(context);

            SetHeader();
            EnableEvents();
        }
        #endregion

        #region Methods

        /// <summary>
        /// Sets the header info
        /// </summary>
        private void SetHeader()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Header = new HeaderUI();
            Header.Title = rm.GetString("selfMappingUI.header.title");
            Header.Information = rm.GetString("selfMappingUI.header.info");
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, userNameLabel);
            SetLabel(rm, passwordLabel);
            SetLabel(rm, addMappingButton);
            SetLabel(rm, exitButton);
            SetLabel(rm, requiredLabel);
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        private void EnableEvents()
        {
            textChanged += new EventHandler(Process_TextChanged);

            userNameTextBox.TextChanged += textChanged;
            secretWordTextBox.TextChanged += textChanged;

            userNameTextBox.KeyDown += new KeyEventHandler(userNameTextBox_KeyDown);
            secretWordTextBox.KeyDown += new KeyEventHandler(passwordTextBox_KeyDown);
        }

        private void GetData()
        {
            UserData.Instance.HpfUserId   = userNameTextBox.Text;
            UserData.Instance.HpfSecretWord = secretWordTextBox.Text;
        }

        /// <summary>
        /// Occurs when a key is pressed while the userid textbox has focus.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void userNameTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            //To prevent the Undo operation.
            if ((e.KeyData == (Keys.Z | Keys.Control)) || (e.KeyData == (Keys.Back | Keys.Alt)))
            {
                userNameTextBox.ClearUndo();
            }
            if (e.KeyValue == 13 && userNameTextBox.Text.Length > 0 && !userNameTextBox.Text.Contains(" "))
            {
                secretWordTextBox.Focus();
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
                //ShowSuccessMappingDialog();
            }
            //To prevent the Undo operation.
            if ((e.KeyData == (Keys.Z | Keys.Control)) || (e.KeyData == (Keys.Back | Keys.Alt)))
            {
                secretWordTextBox.ClearUndo();
            }
            e.Handled = false;
        }

        /// <summary>
        /// Occurs when user changed the text in userid or password control.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// //Process
        private void Process_TextChanged(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(userNameTextBox.Text.Trim()) || string.IsNullOrEmpty(secretWordTextBox.Text.Trim()))
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
            addMappingButton.Enabled = enable;
            if (addMappingButton.Enabled)
            {
                this.ParentForm.AcceptButton = addMappingButton;
            }
        }

        /// <summary>
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        private void exitButton_Click(object sender, EventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(ExitSelfMappingMessage);
            string titleText = rm.GetString(ExitSelfMappingTitle);
            string headerText = rm.GetString(ExitSelfMappingHeaderText);
            string yesButtonText = rm.GetString(ExitSelfMappingYesButton);
            string noButtonText = rm.GetString(ExitSelfMappingNoButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string yesButtonToolTip = rm.GetString(ExitSelfMappingYesButtonToolTip);
            string noButtonToolTip = rm.GetString(ExitSelfMappingNoButtonToolTip);

            if (ROIViewUtility.ConfirmChanges(titleText, headerText, messageText, yesButtonText, noButtonText, yesButtonToolTip, noButtonToolTip, ROIDialogIcon.Alert, DefaultSelectButton.No))
            {
                this.ParentForm.DialogResult = DialogResult.Cancel;
            }
        }

        private void addMappingButton_Click(object sender, EventArgs e)
        {
            try
            {
                string muserName = userNameTextBox.Text;
                string mSecretWord = secretWordTextBox.Text;
                ROIViewUtility.MarkBusy(true);
                GetData();
                ROIController.Instance.UserSelfMapping(muserName);                
                ROIViewUtility.MarkBusy(false);
                LogOnPane logOnPane = new LogOnPane();
                if (LogOnPane.CheckValidateCode)
                {                    
                    logOnPane.ShowDialog(UserData.Instance.SignInstate, Context);
                }
                if (UserData.Instance.UserId == null)
                {
                    logOnPane.ShowDialog(InvalidHPFUserMapping, Context);                    
                }
                else
                {
                    if (UserData.Instance.UserId != ROIConstants.UserMappingRequired && UserData.Instance.SignInstate == 0)
                    {
                        this.ParentForm.DialogResult = DialogResult.OK;
                    }

                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.UserIdEmpty: return userNameTextBox;
                case ROIErrorCodes.SecretWordEmpty: return secretWordTextBox;
            }
            return null;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets the header
        /// </summary>
        public HeaderUI Header
        {
            get { return header; }
            set
            {
                header = value;
                header.Dock = DockStyle.Fill;
                topPanel.Controls.Add(header);
            }
        }

        #endregion

    }
}
