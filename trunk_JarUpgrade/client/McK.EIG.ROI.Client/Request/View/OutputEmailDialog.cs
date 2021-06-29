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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Request.View
{
    public partial class OutputEmailDialog : ROIBaseUI
    {

        #region Fields

        private const string OutputDialog = "OutputDialog";
        private bool releaseDialog;
        private String requestorEmail;
        private OutputDestinationDetails outputDestinationDetails;
        private OutputPropertyDetails outputPropertyDetails;
        private static SplashScreen splash;

        #endregion

        #region Constructor

        private OutputEmailDialog()
        {
            InitializeComponent();

            //initialize context menus
            this.initMessageContextMenu();
            this.initSubjectContextMenu();        
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputEmailDialog(IPane pane): this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
        }

        #endregion

        #region Methods

        public static void CloseSplashScreen()
        {
            if (splash != null)
            {
                splash.Close();
                splash.Dispose();
                splash = null;
            }
        }

        /// <summary>
        /// Localize UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            this.Name = rm.GetString("title." + GetType().Name);

            SetLabel(rm, emailToLabel);
            SetLabel(rm, emailSubjectLabel);
            SetLabel(rm, emailMessageLabel);
            SetLabel(rm, passwordLabel);
            SetLabel(rm, nameLabel);
            SetLabel(rm, statusLabel);
            SetLabel(rm, typeLabel);            
            SetLabel(rm, EmailGroupBox);            
            SetLabel(rm, addDocumentGroupBox);
            SetLabel(rm, headerCheckBox);
            SetLabel(rm, footerCheckBox);
            SetLabel(rm, watermarkCheckBox);

            okButton.Text = rm.GetString("okButton.DialogUI");
            cancelButton.Text = rm.GetString("cancelButton.DialogUI");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, emailToTextBox);
            SetTooltip(rm, toolTip, secretWordTextBox);
            SetTooltip(rm, toolTip, okButton);
            SetTooltip(rm, toolTip, cancelButton);

        }

        /// <summary>
        /// PrePopulate the destination list.
        /// </summary>
        public void PrePopulate(OutputPropertyDetails outputPropertyDetails, int selectedIndex)
        {
            this.outputPropertyDetails = outputPropertyDetails;

            ArrayList properties = new ArrayList();

            foreach (OutputDestinationDetails propertyDetails in outputPropertyDetails.OutputDestinationDetails)
            {
                properties.Add(new KeyValuePair<string, OutputDestinationDetails>(propertyDetails.Name, propertyDetails));
            }

            emailComboBox.DisplayMember = "key";
            emailComboBox.ValueMember = "value";
            emailComboBox.DataSource = properties;
            emailComboBox.SelectedIndex = selectedIndex;

            okButton.Enabled = emailComboBox.Items.Count > 0;
        }

        /// <summary>
        /// Gets or sets the requestor's email.
        /// </summary>
        public string RequestorEmail
        {
            get { return emailToTextBox.Text; }
            set { emailToTextBox.Text = requestorEmail = value; }
        }

        /// <summary>
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == nameLabel || control == statusLabel || control == typeLabel)
            {
                return base.GetLocalizeKey(control) + "." + OutputDialog;
            }

            return base.GetLocalizeKey(control);
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name; 
        }

        /// <summary>
        /// Occurs when user make changes to email(destination) combobox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void emailComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            
            outputDestinationDetails = (OutputDestinationDetails)emailComboBox.SelectedValue;

            secretWordTextBox.Text        = outputDestinationDetails.SecuredSecretWord;
            statusTextLabel.Text        = outputDestinationDetails.Status;
            typeTextLabel.Text          = outputDestinationDetails.Type;

            //set subject and body
            PropertyDefinition subProp = new PropertyDefinition();
            subProp.PropertyName = ROIConstants.EmailSubjectKey; 
            PropertyDefinition msgProp = new PropertyDefinition();
            msgProp.PropertyName = ROIConstants.EmailMessageKey;

            //set override property
            PropertyDefinition subOverrideProp = new PropertyDefinition();
            subOverrideProp.PropertyName = ROIConstants.EmailSubMgsOverrideKey;
            PropertyDefinition bodyOverrideProp = new PropertyDefinition();
            bodyOverrideProp.PropertyName = ROIConstants.EmailBodyMgsOverrideKey;

            if (outputDestinationDetails.PropertyDefinitions.Count > 0)
            {
                int indexProp = outputDestinationDetails.PropertyDefinitions.IndexOf(subProp);
                if (indexProp > -1)
                {
                    emailSubjectTextBox.Text = outputDestinationDetails.PropertyDefinitions[indexProp].DefaultValue;
                }
                indexProp = outputDestinationDetails.PropertyDefinitions.IndexOf(msgProp);
                if (indexProp > -1)
                {
                    emailMessageTextBox.Text = outputDestinationDetails.PropertyDefinitions[indexProp].DefaultValue.Replace("\n", Environment.NewLine);
                }

                bool overrideSubMsg = false;
                bool overrideBodyMsg = false;
                string overrideValue = null;

                indexProp = outputDestinationDetails.PropertyDefinitions.IndexOf(subOverrideProp);

                if (indexProp > -1)
                {
                    overrideValue = outputDestinationDetails.PropertyDefinitions[indexProp].DefaultValue;
                }
                if (!string.IsNullOrEmpty(overrideValue))
                {
                    if (overrideValue.ToLower().Equals("true"))
                    {
                        overrideSubMsg = true;
                    }
                }

                indexProp = outputDestinationDetails.PropertyDefinitions.IndexOf(bodyOverrideProp);
                overrideValue = null;

                if (indexProp > -1)
                {
                    overrideValue = outputDestinationDetails.PropertyDefinitions[indexProp].DefaultValue;
                }
                if (!string.IsNullOrEmpty(overrideValue))
                {
                    if (overrideValue.ToLower().Equals("true"))
                    {
                        overrideBodyMsg = true;
                    }
                }

                if (!overrideSubMsg)
                {
                    emailSubjectTextBox.ReadOnly = true;
                }
                else
                {
                    emailSubjectTextBox.ReadOnly = false;
                 }

                if (!overrideBodyMsg)
                {
                     emailMessageTextBox.ReadOnly = true;
                }
                else
                {
                     emailMessageTextBox.ReadOnly = false;
                }
            }


            watermarkTextBox.Text       = outputPropertyDetails.OutputViewDetails.Watermark;
            headerCheckBox.Visible      = outputPropertyDetails.OutputViewDetails.IsHeader;
            footerCheckBox.Visible      = outputPropertyDetails.OutputViewDetails.IsFooter;
            headerCheckBox.Enabled      = outputPropertyDetails.OutputViewDetails.IsHeaderEnabled;
            if (headerCheckBox.Enabled)
            {
                headerCheckBox.Checked = true;
            }
            footerCheckBox.Enabled      = outputPropertyDetails.OutputViewDetails.IsFooterEnabled;
            if (footerCheckBox.Enabled)
            {
                footerCheckBox.Checked = true;
            }
            watermarkCheckBox.Visible   = outputPropertyDetails.OutputViewDetails.IsWatermark;
            watermarkTextBox.Visible    = outputPropertyDetails.OutputViewDetails.IsWatermark;
            watermarkCheckBox.Checked   = outputPropertyDetails.OutputViewDetails.IsWatermark;
            addDocumentGroupBox.Visible = releaseDialog;

            if (releaseDialog)
            {
                addDocumentGroupBox.Visible = ((outputPropertyDetails.OutputViewDetails.IsHeaderEnabled) || 
                                              (outputPropertyDetails.OutputViewDetails.IsFooterEnabled) ||
                                              (outputPropertyDetails.OutputViewDetails.IsWatermark));
            }            
        }

        /// <summary>
        /// Occurs when user clicks on "Ok" button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void okButton_Click(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);
            this.ParentForm.DialogResult = DialogResult.None;

            validatePrimaryFields();
            outputPropertyDetails.OutputViewDetails.IsHeader         = headerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsFooter         = footerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsWatermark      = watermarkCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsHeaderEnabled  = headerCheckBox.Enabled;
            outputPropertyDetails.OutputViewDetails.IsFooterEnabled  = footerCheckBox.Enabled;            
            outputPropertyDetails.OutputViewDetails.Watermark        = watermarkTextBox.Text;

            outputDestinationDetails.EmailAddr = emailToTextBox.Text;
            outputDestinationDetails.SecuredSecretWord = ROIController.EncryptAES(secretWordTextBox.Text);
            outputDestinationDetails.IsEncryptedPassword = true;


            //set subject and body
            PropertyDefinition subProp = new PropertyDefinition();
            subProp.PropertyName = ROIConstants.EmailSubjectKey;
            PropertyDefinition msgProp = new PropertyDefinition();
            msgProp.PropertyName = ROIConstants.EmailMessageKey;

            int indexProp = outputDestinationDetails.PropertyDefinitions.IndexOf(subProp);
            if (outputDestinationDetails.PropertyDefinitions.Count > 0)
            {
                outputDestinationDetails.PropertyDefinitions[indexProp].SelectedValue = emailSubjectTextBox.Text;
                indexProp = outputDestinationDetails.PropertyDefinitions.IndexOf(msgProp);
                outputDestinationDetails.PropertyDefinitions[indexProp].SelectedValue = emailMessageTextBox.Text.Replace(Environment.NewLine, "\n");
            }


            outputPropertyDetails.OutputDestinationDetails.Clear();
            outputPropertyDetails.OutputDestinationDetails.Add(outputDestinationDetails);
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the user presses the Enter key.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = okButton;
        }

        /// <summary>
        /// Sets the shortcut keys.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OutputEmailDialog_KeyDown(object sender, KeyEventArgs e)
        {
            if (ActiveControl.GetType() != typeof(TextBox))
            {
                switch (e.KeyCode)
                {
                    case Keys.T:
                        emailToTextBox.Focus();
                        break;
                    case Keys.A:
                        passwordLabel.Focus();
                        break;
                    case Keys.N:
                        emailComboBox.Focus();
                        break;                    
                    case Keys.H:
                        headerCheckBox.Focus();
                        break;
                    case Keys.F:
                        footerCheckBox.Focus();
                        break;
                    case Keys.W:
                        watermarkCheckBox.Focus();
                        break;
                }
            }
        }

        private void validatePrimaryFields()
        {
            Boolean hasErrors = false;
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

            errorProvider.Clear();
            this.ParentForm.DialogResult = DialogResult.None;

            if (outputDestinationDetails.PasswordRequired)
            {
                if (string.IsNullOrEmpty(secretWordTextBox.Text))
                {
                    hasErrors = true;
                    errorProvider.SetError(secretWordTextBox, rm.GetString(ROIErrorCodes.FilePasswordEmpty));
                }
            }
            if (! string.IsNullOrEmpty(emailToTextBox.Text.Trim()))
            {
                if (!Validator.Validate(emailToTextBox.Text.Trim(), ROIConstants.EmailValidation))
                {
                    hasErrors = true;
                    errorProvider.SetError(emailToTextBox, rm.GetString(ROIErrorCodes.InvalidEmail));
                }
            }
            else 
            {
                hasErrors = true;
                errorProvider.SetError(emailToTextBox, rm.GetString(ROIErrorCodes.InvalidEmail));
            }
            
            if (hasErrors)
            {
                this.ParentForm.DialogResult = DialogResult.None;
            }
            else
            {
                this.ParentForm.DialogResult = DialogResult.OK;
            }

            if (splash == null && hasErrors==false)
            {
                splash = new SplashScreen();
                splash.BringToFront();
                splash.TopMost = true;
                splash.Show();
            }

        }
        #endregion

        #region EventHandlers

        private void initMessageContextMenu()
        {
            EventHandler emailMessageContextHandler = new EventHandler(process_MessageTextBoxMenu);
            ContextMenu ctMenu = initRequestParametersContextMenu(emailMessageContextHandler);
            emailMessageTextBox.ContextMenu = ctMenu;
        }

        private void initSubjectContextMenu()
        {
            EventHandler emailSubjectontextHandler = new EventHandler(process_SubjectTextBoxMenu);
            ContextMenu ctMenu = initRequestParametersContextMenu(emailSubjectontextHandler);
            emailSubjectTextBox.ContextMenu = ctMenu;
        }

        private ContextMenu initRequestParametersContextMenu(EventHandler eventHandler)
        {
            //Used older ContextMenu Option versus ToolStrip to maintain styling

            ContextMenu ctMenu = new ContextMenu();
            MenuItem reqParmMenu = new MenuItem("Request Parameters");
            reqParmMenu.MenuItems.Add(new MenuItem("Release Date", eventHandler));
            reqParmMenu.MenuItems.Add(new MenuItem("Request Date", eventHandler));
            reqParmMenu.MenuItems.Add(new MenuItem("Request Id", eventHandler));
            ctMenu.MenuItems.Add(reqParmMenu);
            ctMenu.MenuItems.Add("-");
            ctMenu.MenuItems.Add(new MenuItem("Cut", eventHandler));
            ctMenu.MenuItems.Add(new MenuItem("Copy", eventHandler));
            ctMenu.MenuItems.Add(new MenuItem("Paste", eventHandler));
            ctMenu.MenuItems.Add(new MenuItem("Select All", eventHandler));
            return ctMenu;
        }

        private void process_SubjectTextBoxMenu(object sender, EventArgs e)
        {
            process_ReqParmContextMenuOptions(emailSubjectTextBox, sender);
        }

        private void process_MessageTextBoxMenu(object sender, EventArgs e)
        {
            process_ReqParmContextMenuOptions(emailMessageTextBox, sender);
        }

        private void process_ReqParmContextMenuOptions(TextBox textBox, object sender)
        {
            MenuItem menuItem = (MenuItem)sender;
            if (((MenuItem)sender).Text.Equals("Request Id"))
            {
                textBox.Paste(ROIConstants.EmailRequestId);
            }
            if (menuItem.Text.Equals("Request Date"))
            {
                textBox.Paste(ROIConstants.EmailRequestDate);
            }
            if (menuItem.Text.Equals("Release Date"))
            {
                textBox.Paste(ROIConstants.EmailReleaseDate);
            }
            else if (menuItem.Text.Equals("Cut"))
            {
                textBox.Cut();
            }
            else if (menuItem.Text.Equals("Copy"))
            {
                textBox.Copy();
            }
            else if (menuItem.Text.Equals("Paste"))
            {
                textBox.Paste();
            }
            else if (menuItem.Text.Equals("Select All"))
            {
                textBox.SelectAll();
            }
            else if (menuItem.Text.Equals("Undo"))
            {
                textBox.Undo();
            }
            else if (menuItem.Text.Equals("Clear"))
            {
                textBox.Clear();
            }
        }


        #endregion      
 
        #region Properties

        /// <summary>
        /// Gets or sets the release dialog.
        /// </summary>
        public bool ReleaseDialog
        {
            get { return releaseDialog; }
            set { releaseDialog = value; }
        }

        /// <summary>
        /// Gets the output property details.
        /// </summary>
        public OutputPropertyDetails OutputPropertyDetails
        {
            get { return outputPropertyDetails; }
        }

        #endregion       
    }
}
