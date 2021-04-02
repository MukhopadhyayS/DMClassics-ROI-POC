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
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View
{
    public partial class OutputFileDialog : ROIBaseUI
    {
        public enum OutputMediaType
        {
            [Description("CD")]
            CD = 0,
            [Description("DVD")]
            DVD = 1,
            [Description("Other/Unlimited Size")]
            Other = 2
        }

        #region Fields
       
        private const string OutputDialog = "OutputDialog";
        private bool releaseDialog;
        private bool overdueDialog; 
        private OutputDestinationDetails outputDestinationDetails;
        private OutputPropertyDetails outputPropertyDetails;
        private static SplashScreen splash;
       

        #endregion

        #region Constructor

        private OutputFileDialog()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputFileDialog(IPane pane): this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
        }

        #endregion

        public static void CloseSplashScreen()
        {
            if (splash != null)
            {
                splash.Close();
                splash.Dispose();
                splash = null;
            }
        }

        #region Methods

        /// <summary>
        /// Localize UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            this.Name = rm.GetString("title." + GetType().Name);

            SetLabel(rm, mediaLabel);
            SetLabel(rm, passwordLabel);
            SetLabel(rm, nameLabel);
            SetLabel(rm, statusLabel);
            SetLabel(rm, typeLabel);            
            SetLabel(rm, PDFGroupBox);            
            SetLabel(rm, addDocumentGroupBox);
            SetLabel(rm, headerCheckBox);
            SetLabel(rm, footerCheckBox);
            SetLabel(rm, watermarkCheckBox);

            okButton.Text = rm.GetString("okButton.DialogUI");
            cancelButton.Text = rm.GetString("cancelButton.DialogUI");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, mediaComboBox);
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
            int idx = 0;
            string mediaTypeValue = "CD";
            foreach (OutputDestinationDetails propertyDetails in outputPropertyDetails.OutputDestinationDetails)
            {
                properties.Add(new KeyValuePair<string, OutputDestinationDetails>(propertyDetails.Name, propertyDetails));
                if(idx == selectedIndex) {
                    mediaTypeValue = propertyDetails.Media;
                }
                idx++;
            }

            fileComboBox.DisplayMember = "key";
            fileComboBox.ValueMember = "value";
            fileComboBox.DataSource = properties;
            fileComboBox.SelectedIndex = selectedIndex;

            IList mediaType = EnumUtilities.ToList(typeof(OutputMediaType));

            mediaComboBox.DisplayMember = "value";
            mediaComboBox.ValueMember = "key";
            mediaComboBox.DataSource = mediaType;

            switch (mediaTypeValue)
            {
                case "CD":
                    mediaComboBox.SelectedIndex = 0;
                    break;
                case "DVD":
                    mediaComboBox.SelectedIndex = 1;
                    break;
                case "Other/Unlimited Size":
                    mediaComboBox.SelectedIndex = 2;
                    break;
            }
            mediaComboBox.Refresh();

            okButton.Enabled = fileComboBox.Items.Count > 0;
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
        /// Occurs when user make changes file combobox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void fileComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            outputDestinationDetails = (OutputDestinationDetails)fileComboBox.SelectedValue;

            secretWordTextBox.Text        = ROIController.DecryptAES(outputDestinationDetails.SecuredSecretWord);
            statusTextLabel.Text        = outputDestinationDetails.Status;
            typeTextLabel.Text          = outputDestinationDetails.Type;           
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
            if (overdueDialog)
            {
                addDocumentGroupBox.Visible = true;
                headerCheckBox.Visible = false;
                footerCheckBox.Visible = false;
                watermarkCheckBox.Visible = watermarkCheckBox.Enabled = true;
                watermarkTextBox.Visible = watermarkTextBox.Enabled = true;
            }

            if (mediaComboBox.Items.Count > 0)
            {
                switch (outputDestinationDetails.Media)
                {
                    case "CD":
                        mediaComboBox.SelectedIndex = 0;
                        break;
                    case "DVD":
                        mediaComboBox.SelectedIndex = 1;
                        break;
                    case "Other/Unlimited Size":
                        mediaComboBox.SelectedIndex = 2;
                        break;
                }
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
            Boolean hasErrors = false;
            
                       
            if (outputDestinationDetails.PasswordRequired)
            {
                if (string.IsNullOrEmpty(secretWordTextBox.Text))
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                    this.ParentForm.DialogResult = DialogResult.None;
                    errorProvider.SetError(secretWordTextBox, rm.GetString(ROIErrorCodes.FilePasswordEmpty));
                    hasErrors = true;
                }
            }
            else
            {
                this.ParentForm.DialogResult = DialogResult.OK;
            }
            if (splash == null && hasErrors == false)
            {
                splash = new SplashScreen();
                splash.BringToFront();
                splash.TopMost = true;
                splash.Show();
            }
            outputPropertyDetails.OutputViewDetails.IsHeader         = headerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsFooter         = footerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsWatermark      = watermarkCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsHeaderEnabled  = headerCheckBox.Enabled;
            outputPropertyDetails.OutputViewDetails.IsFooterEnabled  = footerCheckBox.Enabled;            
            outputPropertyDetails.OutputViewDetails.Watermark        = watermarkTextBox.Text;

            string mediaType = ((OutputMediaType)mediaComboBox.SelectedValue).ToString();
            switch(mediaType)
            {
                case "CD": outputDestinationDetails.Media = EnumUtilities.GetDescription(OutputMediaType.CD); break;
                case "DVD": outputDestinationDetails.Media = EnumUtilities.GetDescription(OutputMediaType.DVD); break;
                case "Other": outputDestinationDetails.Media = EnumUtilities.GetDescription(OutputMediaType.Other); break;

            }
            outputDestinationDetails.SecuredSecretWord = ROIController.EncryptAES(secretWordTextBox.Text);
            outputDestinationDetails.IsEncryptedPassword = true;
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
        private void OutputFileDialog_KeyDown(object sender, KeyEventArgs e)
        {
            if (ActiveControl.GetType() != typeof(TextBox))
            {
                switch (e.KeyCode)
                {
                    case Keys.M:
                        mediaComboBox.Focus();
                        break;
                    case Keys.A:
                        passwordLabel.Focus();
                        break;
                    case Keys.N:
                        fileComboBox.Focus();
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
        /// Gets or sets the overdue dialog.
        /// </summary>
        public bool OverdueDialog
        {
            get { return overdueDialog; }
            set { overdueDialog = value; }
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
