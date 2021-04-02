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
using System.Drawing;
using System.Data;
using System.Linq;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Request.View
{
    /// <summary>
    /// Class Output Disc dialog
    /// </summary>
    public partial class OutputDiscDialog : ROIBaseUI
    {
        public enum DiscMediaType
        {
            [Description("CD")]
            CD = 0,
            [Description("DVD")]
            DVD = 1
        }
        private Collection<NotesDetails> configureNotes;
        private int maxLength;
        private OutputDestinationDetails outputDestinationDetails;
        private OutputPropertyDetails outputPropertyDetails;
        private OutputDestinationDetails outputDestinationDetailsForDisc;
        private OutputPropertyDetails outputPropertyDetailsForDisc;
        private OutputPropertiesDialog outputPropertyDialog;
        private Hashtable properties;
        private DialogResult propertyResult;
        private string outputNotes;
        private static SplashScreen splash;
        
        public OutputDiscDialog()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputDiscDialog(IPane pane) : this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
            PopulateNotes();
        }
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

            SetLabel(rm, releaseOnlyNonHPFDocumentLabel);
            SetLabel(rm, discGroupBox);
            SetLabel(rm, discNameLabel);
            SetLabel(rm, discTypeLabel);
            SetLabel(rm, passwordLabel);
            SetLabel(rm, discLabelGroupBox);
            SetLabel(rm, templateLabel);
            SetLabel(rm, createNotesLabel);
            SetLabel(rm, createFreeFormNoteButton);
            SetLabel(rm, notesLabel);
            SetLabel(rm, headerGroupBox);
            SetLabel(rm, headerCheckBox);
            SetLabel(rm, footerCheckBox);
            SetLabel(rm, waterMarkCheckBox);
            SetLabel(rm, printGroupBox);
            SetLabel(rm, printNameLabel);
            SetLabel(rm, statusLabel);
            SetLabel(rm, typeLabel);
            SetLabel(rm, whereLabel);
            SetLabel(rm, commentLabel);
            SetLabel(rm, okButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, propertiesButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, discNameComboBox);
            SetTooltip(rm, toolTip, discTypeComboBox);
            SetTooltip(rm, toolTip, secretWordTextBox);
            SetTooltip(rm, toolTip, templateComboBox);
            SetTooltip(rm, toolTip, createFreeFormNoteButton);
            SetTooltip(rm, toolTip, okButton);
            SetTooltip(rm, toolTip, cancelButton);
            SetTooltip(rm, toolTip, propertiesButton);
        }

        /// <summary>
        /// Localize UI Controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
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
        /// Method to populate Notes
        /// </summary>
        private void PopulateNotes()
        {
            ROIViewUtility.MarkBusy(true);

            FlowLayoutPanel notesPanel = null;

            if (configureNotes == null)
            {
                configureNotes = ROIAdminController.Instance.RetrieveAllConfigureNotes();
            }

            if (configureNotes.Count > 0)
            {
                notesPanel = notesFlowLayoutPanel;
                
                notesPanel.Controls.Clear();
                maxLength = configureNotes[0].DisplayText.Length;
                for (int count = 1; count < configureNotes.Count; count++)
                {
                    if (configureNotes[count].DisplayText.Length > maxLength)
                    {
                        maxLength = configureNotes[count].DisplayText.Length;
                    }
                }
                foreach (NotesDetails configureNote in configureNotes)
                {
                    FreeformUI freeformUI = CreateFreeformUI(false, notesPanel);
                    freeformUI.NoteId = configureNote.Id;
                    freeformUI.FreeformCheckBox.Text = configureNote.DisplayText.Replace("&", "&&");
                    if (maxLength > 75)
                    {
                        freeformUI.Width = maxLength * 6;
                    }
                }
            }
            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Method to create Free Form UI
        /// </summary>
        /// <param name="isFreeformNote"></param>
        /// <param name="notesPanel"></param>
        /// <returns></returns>
        private FreeformUI CreateFreeformUI(bool isFreeformNote, Panel notesPanel)
        {
            FreeformUI freeformUI = new FreeformUI();

            freeformUI.SetExecutionContext(Context);
            freeformUI.SetPane(Pane);
            freeformUI.Localize();
            freeformUI.DeleteFreeformHandler = new EventHandler(DeleteFreeformReason);

            freeformUI.IsFreeform = isFreeformNote;
            if (maxLength > 75)
            {
                freeformUI.Width = maxLength * 6;
            }
            else
            {
                freeformUI.Width = notesFlowLayoutPanel.Width;
            }
            notesPanel.Controls.Add(freeformUI);

            int notesCount = notesPanel.Controls.Count;

            if (!isFreeformNote)
            {
                freeformUI.DeleteImage.Visible = false;
                freeformUI.FreeformTextBox.Visible = false;
                freeformUI.IsFreeform = false;

                if (notesCount % 2 != 0)
                {
                    freeformUI.BackColor = Color.FromArgb(236, 238, 245);
                    freeformUI.FreeformTextBox.BackColor = Color.FromArgb(236, 238, 245);
                }
            }
            else
            {
                if (notesCount % 2 != 0)
                {
                    freeformUI.BackColor = Color.FromArgb(236, 238, 245);
                }
            }
            return freeformUI;
        }

        /// <summary>
        /// Method to delete Free Form notes
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DeleteFreeformReason(object sender, EventArgs e)
        {
            FreeformUI freeformUI = (FreeformUI)sender;

            FlowLayoutPanel notesPanel;


                notesPanel = notesFlowLayoutPanel;
            

            int index = notesPanel.Controls.IndexOf(freeformUI);
            notesPanel.Controls.RemoveAt(index);

            for (int count = index; count < notesPanel.Controls.Count; count++)
            {
                if (count % 2 == 0)
                {
                    notesPanel.Controls[count].BackColor = Color.FromArgb(236, 238, 245);
                }
                else
                {
                    notesPanel.Controls[count].BackColor = Color.White;
                }
            }
        }

        /// <summary>
        /// Method to create Free form notes
        /// </summary>
        private void CreateFreeform()
        {
            Panel notesPanel;

            notesPanel = notesFlowLayoutPanel;

            FreeformUI freeformUI = CreateFreeformUI(true, notesPanel);

            int notesCount = notesPanel.Controls.Count;
            if (notesCount % 2 != 0)
            {
                freeformUI.BackColor = Color.FromArgb(236, 238, 245);
            }
            freeformUI.SetFocus();
        }

        /// <summary>
        /// Method to prepopulate Destination List
        /// </summary>
        /// <param name="outputPropertyDetailsForDisc"></param>
        /// <param name="outputPropertyDetails"></param>
        public void PrePopulate(OutputPropertyDetails outputPropertyDetailsForDisc, OutputPropertyDetails outputPropertyDetails)
        {

            if (PatientInfo.RequestPatientInfoUI.IsResend)
            {
                printGroupBox.Enabled = false;
                PatientInfo.RequestPatientInfoUI.IsResend = false;
            }

            if (BillingPaymentInfoUI.isOnlyNonHPFDocuments)
            {
                discGroupBox.Enabled = false;
                releaseOnlyNonHPFDocumentLabel.Visible = true;
            }
            else
            {
                discGroupBox.Enabled = true;
                releaseOnlyNonHPFDocumentLabel.Visible = false;
            }

            this.outputPropertyDetailsForDisc = outputPropertyDetailsForDisc;
            this.outputPropertyDetails = outputPropertyDetails;
            ArrayList propertiesForDisc = new ArrayList();

            foreach (OutputDestinationDetails propertyDetails in outputPropertyDetailsForDisc.OutputDestinationDetails)
            {
                propertiesForDisc.Add(new KeyValuePair<string, OutputDestinationDetails>(propertyDetails.Name, propertyDetails));
            }
            discNameComboBox.DisplayMember = "key";
            discNameComboBox.ValueMember = "value";
            discNameComboBox.DataSource = propertiesForDisc;

            ArrayList properties = new ArrayList();

            foreach (OutputDestinationDetails propertyDetails in outputPropertyDetails.OutputDestinationDetails)
            {
                properties.Add(new KeyValuePair<string, OutputDestinationDetails>(propertyDetails.Name, propertyDetails));
            }
            printNameComboBox.DisplayMember = "key";
            printNameComboBox.ValueMember = "value";
            printNameComboBox.DataSource = properties;

            IList discmediaType = EnumUtilities.ToList(typeof(DiscMediaType));

            discTypeComboBox.DisplayMember = "value";
            discTypeComboBox.ValueMember = "key";
            discTypeComboBox.DataSource = discmediaType;
            discTypeComboBox.Text = outputDestinationDetailsForDisc.Media;
            OutputPrintDetails(outputPropertyDetailsForDisc);
            okButton.Enabled = propertiesButton.Enabled = printNameComboBox.Items.Count > 0;
        }

        /// <summary>
        /// Populates device templates
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void discNameComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            ArrayList possibleValues = new ArrayList();
            outputDestinationDetailsForDisc = (OutputDestinationDetails)discNameComboBox.SelectedValue;
            foreach (PropertyDefinition propertyDefinition in outputDestinationDetailsForDisc.PropertyDefinitions)
            {
                if ("labelTemplate" == propertyDefinition.PropertyName)
                {
                    foreach (string possibleValue in propertyDefinition.PossibleValues)
                    {
                        possibleValues.Add(possibleValue);
                    }
                    possibleValues.Insert(0, "None");
                    templateComboBox.DataSource = possibleValues;
                    break;
                }
            }

            secretWordTextBox.Text = ROIController.DecryptAES(outputDestinationDetailsForDisc.SecuredSecretWord);
            templateComboBox.Text = string.IsNullOrEmpty(outputDestinationDetailsForDisc.TemplateName)? "None" : outputDestinationDetailsForDisc.TemplateName;
            discTypeComboBox.Text = outputDestinationDetailsForDisc.Media;
            errorProvider.Clear();
        }

        /// <summary>
        /// Occurs when user make changes on printer combobox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void printNameComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            outputDestinationDetails = (OutputDestinationDetails)printNameComboBox.SelectedValue;

            statusValueLabel.Text = outputDestinationDetails.Status;
            typeValueLabel.Text = outputDestinationDetails.Type;
            whereValueLabel.Text = outputDestinationDetails.Where;
            commentValueLabel.Text = outputDestinationDetails.Comment;
        }

        /// <summary>
        /// Display header, footer and watermark details
        /// </summary>
        /// <param name="outputPropertyDetailsForDisc"></param>
        private void OutputPrintDetails(OutputPropertyDetails outputPropertyDetailsForDisc)
        {
            waterMarkTextBox.Text = outputPropertyDetailsForDisc.OutputViewDetails.Watermark;
            headerCheckBox.Visible = outputPropertyDetailsForDisc.OutputViewDetails.IsHeader;
            footerCheckBox.Visible = outputPropertyDetailsForDisc.OutputViewDetails.IsFooter;
            headerCheckBox.Enabled = outputPropertyDetailsForDisc.OutputViewDetails.IsHeaderEnabled;
            if (headerCheckBox.Enabled)
            {
                headerCheckBox.Checked = true;
            }
            footerCheckBox.Enabled = footerCheckBox.Checked = outputPropertyDetailsForDisc.OutputViewDetails.IsFooterEnabled;
            if (footerCheckBox.Enabled)
            {
                footerCheckBox.Checked = true;
            }
            waterMarkCheckBox.Visible = outputPropertyDetails.OutputViewDetails.IsWatermark;
            waterMarkTextBox.Visible = outputPropertyDetails.OutputViewDetails.IsWatermark;
            waterMarkCheckBox.Checked = outputPropertyDetails.OutputViewDetails.IsWatermark;
        }

        /// <summary>
        /// Occurs when user clicks on properties button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void propertiesButton_Click(object sender, EventArgs e)
        {
            
            outputPropertyDialog = new OutputPropertiesDialog(Pane);
            
            outputPropertyDialog.OutputDestinationType = DestinationType.Print;
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString("title." + DestinationType.Print.ToString() + ".properties"),
                                                       outputPropertyDialog);

            dialog.BackColor = Color.FromArgb(236, 233, 216);
            outputPropertyDialog.IsDisc = true;
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
        /// Create free form notes
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void createFreeFormNoteButton_Click(object sender, EventArgs e)
        {
            CreateFreeform();
            notesFlowLayoutPanel.HorizontalScroll.Value = 0;
        }

        /// <summary>
        /// Occurs when user clicks on "Ok" button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void okButton_Click(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);
            Boolean hasErrors = false;
            
            this.ParentForm.DialogResult = DialogResult.OK;
            if (outputDestinationDetailsForDisc.PasswordRequired)
            {
                if (string.IsNullOrEmpty(secretWordTextBox.Text.Trim()))
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                    this.ParentForm.DialogResult = DialogResult.None;
                    errorProvider.SetError(secretWordTextBox, rm.GetString(ROIErrorCodes.FilePasswordEmpty));
                    hasErrors = true;
                    return;
                }
            }
            if (splash == null && hasErrors==false)
            {
                splash = new SplashScreen();
                splash.BringToFront();
                splash.TopMost = true;
                splash.Show();
            }
            /*
            if (("Active").Equals(outputDestinationDetailsForDisc.Status))
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                string messageText = rm.GetString("INVALID_DISC_DEVICE");
                DialogUI errorDialog = new DialogUI(messageText, Context, "invalidDiscDevice", string.Empty);
                errorDialog.ShowDialog();
                this.ParentForm.DialogResult = DialogResult.None;
                return;
            }
             * */
 
            if (propertyResult == DialogResult.OK)
            {
                outputDestinationDetails.PropertyDefinitions.Clear();
                foreach (PropertyDefinition propertyDefinition in outputPropertyDialog.PropertyDefinitions)
                {
                    outputDestinationDetails.PropertyDefinitions.Add(propertyDefinition);
                }
            }
            outputPropertyDetails.OutputViewDetails = ReleasedDocuments(outputPropertyDetails, true);
            outputPropertyDetailsForDisc.OutputViewDetails = ReleasedDocuments(outputPropertyDetailsForDisc, false);

            outputPropertyDetails.OutputDestinationDetails.Clear();
            outputPropertyDetails.OutputDestinationDetails.Add(outputDestinationDetails);

            outputPropertyDetailsForDisc.OutputDestinationDetails.Clear();
            outputDestinationDetailsForDisc.SecuredSecretWord = !string.IsNullOrEmpty(secretWordTextBox.Text) ? ROIController.EncryptAES(secretWordTextBox.Text.Trim()) : string.Empty;
            outputDestinationDetailsForDisc.IsEncryptedPassword = true;
            outputDestinationDetailsForDisc.DiscType = !string.IsNullOrEmpty(discTypeComboBox.Text) ? discTypeComboBox.Text.Trim() : string.Empty;
            outputDestinationDetailsForDisc.TemplateName = !string.IsNullOrEmpty(templateComboBox.Text) ? templateComboBox.Text.Trim() : string.Empty;

            RetrieveNotes();
            if (outputNotes.Length > 0)
            {
                outputNotes = outputNotes.Substring(0, outputNotes.Length - 2);
            }
            outputDestinationDetailsForDisc.OutputNotes = outputNotes;
            outputPropertyDetailsForDisc.OutputDestinationDetails.Add(outputDestinationDetailsForDisc);
        }

        /// <summary>
        /// Sets the output view details
        /// </summary>
        /// <param name="outputPropertyDetails"></param>
        /// <param name="isPrint"></param>
        /// <returns></returns>
        private OutputViewDetails ReleasedDocuments(OutputPropertyDetails outputPropertyDetails, bool isPrint)
        {
            outputPropertyDetails.OutputViewDetails.IsHeader = headerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsFooter = footerCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsWatermark = waterMarkCheckBox.Checked;
            outputPropertyDetails.OutputViewDetails.IsHeaderEnabled = headerCheckBox.Enabled;
            outputPropertyDetails.OutputViewDetails.IsFooterEnabled = footerCheckBox.Enabled;
            outputPropertyDetails.OutputViewDetails.Watermark = waterMarkTextBox.Text;
            if (isPrint)
            {
                outputPropertyDetails.OutputViewDetails.Where = whereValueLabel.Text;
                outputPropertyDetails.OutputViewDetails.Comment = commentValueLabel.Text;
            }
            return outputPropertyDetails.OutputViewDetails;
        }

        /// <summary>
        /// Retrieve all the selected notes
        /// </summary>
        private void RetrieveNotes()
        {
            StringBuilder  outputNotes = new StringBuilder();
            StringBuilder  freeFormNotes = new StringBuilder();
            foreach (FreeformUI freeformUI in notesFlowLayoutPanel.Controls)
            {
                NotesDetails note;
                if (freeformUI.Checked)
                {
                    if (!freeformUI.IsFreeform)
                    {
                        note = new NotesDetails();
                        note.Id = freeformUI.NoteId;
                        note.DisplayText = freeformUI.FreeformCheckBox.Text.Replace("&&", "&");
                        note.DisplayText = note.DisplayText + "; ";
                        outputNotes.Append(note.DisplayText);
                    }
                    else
                    {
                        if (!Validator.Validate(freeformUI.FreeformTextBox.Text, ROIConstants.AllCharacters))
                        {
                            //errorProvider.SetError(freeformUI.FreeformTextBox, rm.GetString(ROIErrorCodes.InvalidName));
                            ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        }
                        else
                        {
                            if (freeformUI.FreeformTextBox.Text.Trim().Length > 0)
                            {
                                freeformUI.FreeformTextBox.Text = freeformUI.FreeformTextBox.Text + "; ";
                                outputNotes.Append(freeformUI.FreeformTextBox.Text);
                            }
                        }
                    }
                }
            }
            this.outputNotes = outputNotes.ToString();
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the user presses the Enter key.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = okButton;
        }

        /// <summary>
        /// Get the output properties for disc
        /// </summary>
        public OutputPropertyDetails OutputPropertyDetailsForDisc
        {
            get { return outputPropertyDetailsForDisc; }
        }

        /// <summary>
        /// Get the output properties for print
        /// </summary>
        public OutputPropertyDetails OutputPropertyDetails
        {
            get { return outputPropertyDetails; }
        }
    }
}
