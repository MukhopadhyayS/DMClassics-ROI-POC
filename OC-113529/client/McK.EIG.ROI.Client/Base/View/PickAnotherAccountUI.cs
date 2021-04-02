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
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using System.Resources;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class PickAnotherAccountUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;

        private EventHandler textChanged;

        #endregion

        #region Constructor

        public PickAnotherAccountUI()
        {
            InitializeComponent();
        }

        public PickAnotherAccountUI(ExecutionContext context) : this()
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
            Header.Title = rm.GetString("pickAnotherAccountUI.header.title");            
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, mappedAccountLabel);
            SetLabel(rm, userNameLabel);
            SetLabel(rm, passwordLabel);
            SetLabel(rm, addMappingButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);
        }

        private void GetData()
        {
            UserData.Instance.HpfUserId = userNameTextBox.Text;
            UserData.Instance.HpfSecretWord = secretWordTextBox.Text;
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
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
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

        private void addMappingButton_Click(object sender, EventArgs e)
        {
            try
            {
                string mun = userNameTextBox.Text;
                ROIViewUtility.MarkBusy(true);
                GetData();
                ROIController.Instance.UserSelfMapping(mun);
                ROIViewUtility.MarkBusy(false);
                

                if (LogOnPane.CheckValidateCode)
                {
                    LogOnPane logOnPane = new LogOnPane();
                    logOnPane.ShowDialog(UserData.Instance.SignInstate, Context);                    
                    return;
                }
                if (UserData.Instance.UserId != ROIConstants.UserMappingRequired && UserData.Instance.SignInstate == 0)
                {
                    this.ParentForm.DialogResult = DialogResult.OK;
                }
                if (UserData.Instance.SignInstate != 0)
                {
                    this.ParentForm.DialogResult = DialogResult.Cancel;
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

        #region Properties

        /// <summary>
        /// This property is used to get the user listbox control.
        /// </summary>
        public ListBox UserListBox
        {
            get { return userListBox; }
        }       

        //public string SelectedUser
        //{
        //    get
        //    {
        //        return string.IsNullOrEmpty(UserListBox.SelectedItem.ToString()) ? UserListBox.SelectedItem.ToString() : string.Empty;
        //    }
        //}

        #endregion  

        #endregion
    }
}
