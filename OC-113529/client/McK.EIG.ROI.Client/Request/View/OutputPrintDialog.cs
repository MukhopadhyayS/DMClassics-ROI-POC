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
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;

namespace McK.EIG.ROI.Client.Request.View
{
    public partial class OutputPrintDialog : ROIBaseUI
    {    
        #region Fields

        private const string OutputDialog = "OutputDialog";
        private bool releaseDialog;
        private OutputDestinationDetails outputDestinationDetails;        
        private OutputPropertyDetails outputPropertyDetails;
        private OutputPropertiesDialog outputPropertyDialog;
        private Hashtable properties;
        private DialogResult propertyResult;
        private static SplashScreen splash;

        #endregion

        #region Constructor

        private OutputPrintDialog()
        {
            InitializeComponent();
        }
       
        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputPrintDialog(IPane pane) : this()
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
        /// Localize the UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            this.Name = rm.GetString("title." + GetType().Name );

            SetLabel(rm, nameLabel);
            SetLabel(rm, statusLabel);
            SetLabel(rm, typeLabel);
            SetLabel(rm, commentLabel);
            SetLabel(rm, whereLabel);
            SetLabel(rm, copiesLabel);
            SetLabel(rm, propertiesButton);
            SetLabel(rm, copiesGroupBox);
            SetLabel(rm, printerGroupBox);
            SetLabel(rm, addDocumentGroupBox);
            SetLabel(rm, headerCheckBox);
            SetLabel(rm, footerCheckBox);
            SetLabel(rm, watermarkCheckBox);
            SetLabel(rm, collateCheckBox);
            SetLabel(rm, pageSeparatorCheckBox);

            okButton.Text = rm.GetString("okButton.DialogUI");
            cancelButton.Text = rm.GetString("cancelButton.DialogUI");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, propertiesButton);
            SetTooltip(rm, toolTip, okButton);
            SetTooltip(rm, toolTip, cancelButton);

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
            printerComboBox.DisplayMember = "key";
            printerComboBox.ValueMember = "value";
            printerComboBox.DataSource = properties;

            addDocumentGroupBox.Visible = releaseDialog;
            okButton.Enabled = propertiesButton.Enabled = printerComboBox.Items.Count > 0;
        }

        /// <summary>
        /// Occurs when user clicks on copies
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void copiesUpDown_MouseClick(object sender, MouseEventArgs e)
        {
            collateCheckBox.Enabled = copiesUpDown.Value > 1;
        }

        /// <summary>
        /// Occurs when user clicks on properties button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void propertiesButton_Click(object sender, EventArgs e)
        {
            outputPropertyDialog = new OutputPropertiesDialog(Pane);
           
            outputPropertyDialog.OutputDestinationType =  DestinationType.Print;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            Form dialog = ROIViewUtility.ConvertToForm(null,                                                       
                                                       rm.GetString("title." + DestinationType.Print.ToString() + ".properties"),
                                                       outputPropertyDialog);

            dialog.BackColor = Color.FromArgb(236, 233, 216);

            outputPropertyDialog.PrePopulate(outputDestinationDetails.PropertyDefinitions, properties);
           
            DialogResult dialogResult = dialog.ShowDialog();            
            dialog.Close();
            if (dialogResult == DialogResult.OK)
            {
                properties = new Hashtable();
                propertyResult = DialogResult.OK;                
                foreach (PropertyDefinition propertyDefinition in outputPropertyDialog.PropertyDefinitions)
                {
                    properties.Add(propertyDefinition.PropertyName, propertyDefinition.DefaultValue);
                }
            }
            else
            {
                propertyResult = DialogResult.Cancel;
            }                
        }

        /// <summary>
        /// Occurs when user make changes on printer combobox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void printerComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            outputDestinationDetails  = (OutputDestinationDetails) printerComboBox.SelectedValue;          

            statusTextLabel.Text      = outputDestinationDetails.Status;
            typeTextLabel.Text        = outputDestinationDetails.Type;
            whereTextLabel.Text       = outputDestinationDetails.Where;
            commentTextLabel.Text     = outputDestinationDetails.Comment;
            watermarkTextBox.Text     = outputPropertyDetails.OutputViewDetails.Watermark;
            headerCheckBox.Visible    = outputPropertyDetails.OutputViewDetails.IsHeader;
            footerCheckBox.Visible    = outputPropertyDetails.OutputViewDetails.IsFooter;
            headerCheckBox.Enabled    = outputPropertyDetails.OutputViewDetails.IsHeaderEnabled;
            if (headerCheckBox.Enabled)
            {
                headerCheckBox.Checked = true;
            }
            footerCheckBox.Enabled    = footerCheckBox.Checked = outputPropertyDetails.OutputViewDetails.IsFooterEnabled;
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
        /// Sets the shortcut keys.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OutputPrintDialog_KeyDown(object sender, KeyEventArgs e)
        {
            if (ActiveControl.GetType() != typeof(TextBox))
            {
                switch (e.KeyCode)
                {
                    case Keys.N:
                        printerComboBox.Focus();
                        break;
                    case Keys.P:
                        propertiesButton.Focus();
                        break;
                    case Keys.S:
                        pageSeparatorCheckBox.Focus();
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
                    case Keys.C:
                        copiesUpDown.Focus();
                        break;
                    case Keys.O:
                        collateCheckBox.Focus();
                        break;
                }
            }
        }

        /// <summary>
        /// Occurs when user clicks on "Ok" button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void okButton_Click(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);
            if (splash == null)
            {
                splash = new SplashScreen();
                splash.BringToFront();
                splash.TopMost = true;
                splash.Show();
            }
            if (propertyResult == DialogResult.OK)
            {
                outputDestinationDetails.PropertyDefinitions.Clear();
                foreach (PropertyDefinition propertyDefinition in outputPropertyDialog.PropertyDefinitions)
                {
                    outputDestinationDetails.PropertyDefinitions.Add(propertyDefinition);
                }
            }

            outputPropertyDetails.OutputViewDetails.IsHeader           = headerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsFooter           = footerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsWatermark        = watermarkCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsHeaderEnabled    = headerCheckBox.Enabled;
            outputPropertyDetails.OutputViewDetails.IsFooterEnabled    = footerCheckBox.Enabled;                
            outputPropertyDetails.OutputViewDetails.Watermark          = watermarkTextBox.Text;
            outputPropertyDetails.OutputViewDetails.IsCollate          = collateCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.Copies             = (int)copiesUpDown.Value;
            outputPropertyDetails.OutputViewDetails.IsPageSeparator    = pageSeparatorCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.Where              = whereTextLabel.Text;
            outputPropertyDetails.OutputViewDetails.Comment            = commentTextLabel.Text;

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

        public OutputPropertyDetails OutputPropertyDetails
        {
            get { return outputPropertyDetails; }
        }
       
        #endregion
    }
}
