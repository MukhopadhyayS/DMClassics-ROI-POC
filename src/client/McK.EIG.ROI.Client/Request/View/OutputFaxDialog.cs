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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.Controller;

namespace McK.EIG.ROI.Client.Request.View
{
    public partial class OutputFaxDialog : ROIBaseUI
    {
        #region Fields

        private const string OutputDialog = "OutputDialog";
        private bool releaseDialog;
        private string requestorFax;
        private OutputPropertyDetails outputPropertyDetails;
        private OutputDestinationDetails outputDestinationDetails;
        private static SplashScreen splash;

        #endregion

        #region Constructor

        private OutputFaxDialog()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputFaxDialog(IPane pane): this()
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
        /// Localize the UI Controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            this.Name = rm.GetString("title." + GetType().Name);

            SetLabel(rm, numberLabel);
            SetLabel(rm, nameLabel);
            SetLabel(rm, statusLabel);
            SetLabel(rm, typeLabel);
            SetLabel(rm, commentLabel);
            SetLabel(rm, whereLabel);

            SetLabel(rm, faxGroupBox);            
            SetLabel(rm, addDocumentGroupBox);
            SetLabel(rm, headerCheckBox);
            SetLabel(rm, footerCheckBox);
            SetLabel(rm, watermarkCheckBox);

            okButton.Text = rm.GetString("okButton.DialogUI");
            cancelButton.Text = rm.GetString("cancelButton.DialogUI");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, okButton);
            SetTooltip(rm, toolTip, cancelButton);
        }
     
        /// <summary>
        /// PrePopulate the destination list.
        /// </summary>
        public void PrePopulate(OutputPropertyDetails outputPropertyDetails)
        {
            this.outputPropertyDetails = outputPropertyDetails;

            ArrayList properties = new ArrayList();

            foreach (OutputDestinationDetails propertyDetails in outputPropertyDetails.OutputDestinationDetails)
            {
                properties.Add(new KeyValuePair<string, OutputDestinationDetails>(propertyDetails.Name, propertyDetails));
            }
            faxComboBox.DisplayMember = "key";
            faxComboBox.ValueMember = "value";
            faxComboBox.DataSource = properties;

            numberTextBox.Text = requestorFax;
        }

        /// <summary>
        /// Occurs when user make changes on fax combobox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void faxComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            outputDestinationDetails = (OutputDestinationDetails)faxComboBox.SelectedValue;

            statusTextLabel.Text        = outputDestinationDetails.Status;
            typeTextLabel.Text          = outputDestinationDetails.Type;
            whereTextLabel.Text         = outputDestinationDetails.Where;
            commentTextLabel.Text       = outputDestinationDetails.Comment;
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
            EnableButtons();
        }

        /// <summary>
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == nameLabel || control == statusLabel || control == typeLabel || control == commentLabel)
            {
                return base.GetLocalizeKey(control) + "." + OutputDialog;
            }

            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Localize the UI Tooltip.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Sets the shortcut keys.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OutputFaxDialog_KeyDown(object sender, KeyEventArgs e)
        {
            if (ActiveControl.GetType() != typeof(TextBox))
            {
                switch (e.KeyCode)
                {
                    case Keys.M:
                        numberTextBox.Focus();
                        break;
                    case Keys.N:
                        faxComboBox.Focus();
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

        private void okButton_Click(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);
            Boolean hasErrors = false;
            string faxNumber = numberTextBox.Text.Trim();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            ((Form)(this.Parent)).DialogResult = DialogResult.None;

            if (!string.IsNullOrEmpty(faxNumber))
            {
                if (!Validator.Validate(faxNumber, ROIConstants.FaxValidation))
                {
                    errorProvider.SetError(numberTextBox, rm.GetString(ROIErrorCodes.InvalidFax));
                    hasErrors = true;
                }
                else
                {
                    ((Form)(this.Parent)).DialogResult = DialogResult.OK;
                }
            }
            else
            {
                errorProvider.SetError(numberTextBox, rm.GetString(ROIErrorCodes.FaxNumberEmpty));
                hasErrors = true;
            }

            if (splash == null && hasErrors==false)
            {
                splash = new SplashScreen();
                splash.BringToFront();
                splash.TopMost = true;
                splash.Show();
            }
            outputPropertyDetails.OutputViewDetails.IsHeader            = headerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsFooter            = footerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsWatermark         = watermarkCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsHeaderEnabled     = headerCheckBox.Enabled;
            outputPropertyDetails.OutputViewDetails.IsFooterEnabled     = footerCheckBox.Enabled;            
            outputPropertyDetails.OutputViewDetails.Watermark           = watermarkTextBox.Text;
            outputPropertyDetails.OutputViewDetails.Where               = whereTextLabel.Text;
            outputPropertyDetails.OutputViewDetails.Comment             = commentTextLabel.Text;

            outputDestinationDetails.Fax = faxNumber;

            outputPropertyDetails.OutputDestinationDetails.Clear();
            outputPropertyDetails.OutputDestinationDetails.Add(outputDestinationDetails);
            EnableButtons();
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the user presses the Enter key.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = okButton;
        }

        private void numberTextBox_TextChanged(object sender, EventArgs e)
        {
            EnableButtons();
        }

        private void EnableButtons()
        {
            okButton.Enabled = numberTextBox.Text.Length > 0 && (faxComboBox.Items.Count > 0);
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
        /// Gets or sets the requestor fax.
        /// </summary>
        public string RequestorFax
        {
            get { return numberTextBox.Text; }
            set { numberTextBox.Text = requestorFax = value; }
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
