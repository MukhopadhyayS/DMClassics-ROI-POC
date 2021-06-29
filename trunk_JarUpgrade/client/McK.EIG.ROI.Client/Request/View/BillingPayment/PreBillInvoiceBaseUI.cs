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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Viewer.View;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    public partial class PreBillInvoiceBaseUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;
        private Collection<NotesDetails> notesList;
        private Collection<string> freeformNotesList;
        private int dueDays;
        private int maxLength;        
        private InvoiceDueDetails invoiceDueDays;
        private EventHandler preBillinvoiceHandler;        

        #endregion

        #region Constructor

        public PreBillInvoiceBaseUI()
        {
            InitializeComponent();
            header = InitHeaderUI();
            notesList = new Collection<NotesDetails>();
            freeformNotesList = new Collection<string>();
            HideInvoiceDueCombo();       
            preBillinvoiceHandler = new EventHandler(Process_PreBillInvoiceHandler);
        }

        #endregion

        #region Methods

        /// <summary>
        /// This method is used to enable the invoice dialog local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            templateComboBox.SelectedIndexChanged += preBillinvoiceHandler;
            invoiceDueTextBox.TextChanged += preBillinvoiceHandler;
            invoiceDueTextBox.Leave += Process_InvoiceDueTextBoxHandler;
        }

        /// <summary>
        /// This method is used to disable the invoice dialog local events
        /// </summary>
        public void DisableEvents()
        {
            templateComboBox.SelectedIndexChanged -= preBillinvoiceHandler;
            invoiceDueTextBox.TextChanged -= preBillinvoiceHandler;
            invoiceDueTextBox.Leave -= Process_InvoiceDueTextBoxHandler;
        }

        /// <summary>
        /// occurs when the user changes the due days combo or custom date text or template combo
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_PreBillInvoiceHandler(object sender, EventArgs e)
        {
            //check the template combo box value to enable continue button
            if (templateComboBox.SelectedIndex > 0)
            {
                continueButton.Enabled = true;
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)templateComboBox.SelectedItem;
                if (letterTemplate.HasNotes)
                {
                    EnableButtons(true);
                }
                else
                {
                    EnableButtons(false);
                }
                //if the letter template type is invoice the below code will execute
                if (LetterTemplateType == LetterType.Invoice.ToString())
                {
                    continueButton.Enabled = invoiceDueTextBox.Text.Trim().Length > 0;
                }
            }
            else
            {
                EnableButtons(false);
                continueButton.Enabled = false;
            }
        }

        /// <summary>
        /// Event occurs when user leaves the invoice due text field
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_InvoiceDueTextBoxHandler(object sender, EventArgs e)
        {
            if (invoiceDueTextBox.Text.Trim().Length == 0)
            {
                invoiceDueTextBox.Text = invoiceDueDays.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
        }

        /// <summary>
        /// Event occurs when the custom due days TextBox text is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceDueTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            int KeyCode = (int)e.KeyChar;
            if (KeyCode == 27)
            {
                invoiceDueTextBox.Text = invoiceDueDays.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            if (!(KeyCode >= 48 && KeyCode <= 57) && KeyCode != 8)
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// This method is used to show or hide the invoice due days combo based on pre-bill or invoice
        /// </summary>
        public void HideInvoiceDueCombo()
        {
            if (LetterTemplateType == LetterType.PreBill.ToString())
            {
                invoiceDueTextBox.Visible = false;
                invoiceDueLabel.Visible = false;
                daysLabel.Visible = false;
                pictureBox3.Visible = false;
            }
            else
            {
                invoiceDueTextBox.Visible = true;                   
                invoiceDueLabel.Visible = true;
                daysLabel.Visible = true;
            }
        }

        ///<summary>
        ///This mehod is used to populate the configured due days
        ///</summary>
        public void PopulateInvoiceDueDays()
        {            
            invoiceDueDays = ROIAdminController.Instance.RetrieveInvoiceDueDays();
            DisableEvents();
            invoiceDueTextBox.Text = invoiceDueDays.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);         
        }

        /// <summary>
        /// Creates HeaderUI
        /// </summary>
        private HeaderUI InitHeaderUI()
        {
            header = new HeaderUI();            
            header.Dock = DockStyle.Fill;
            TopPanel.Controls.Add(header);
            return header;
        }

        /// <summary>
        /// Populate configuration notes.
        /// </summary>
        /// <param name="templates"></param>
        public void PopulateNotes()
        {
            ROIViewUtility.MarkBusy(true);

            Collection<NotesDetails> notes = ROIAdminController.Instance.RetrieveAllConfigureNotes();
            maxLength = (notes.Count > 0) ? notes[0].DisplayText.Length : 0;
            for (int count = 1; count < notes.Count; count++)
            {
                if (notes[count].DisplayText.Length > maxLength)
                {
                    maxLength = notes[count].DisplayText.Length;
                }
            }
            foreach (NotesDetails note in notes)
            {
                FreeformUI freeformUI = CreateFreeformUI(false);
                freeformUI.NoteId = note.Id;
                freeformUI.FreeformCheckBox.Text = note.DisplayText.Replace("&", "&&");
                if (maxLength > 100)
                {
                    freeformUI.Width = maxLength * 6;
                }
            }

            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Prepopulates the letter templates.
        /// </summary>
        public void PrePopulate(IList<LetterTemplateDetails> letterTemplates, long defaultLetterId)
        {
            EnableEvents();
            LetterTemplateDetails forSelect = new LetterTemplateDetails();
            forSelect.Id = 0;
            forSelect.Name = GetLocalizedString("letterTemplateText");
            letterTemplates.Insert(0, forSelect);

            templateComboBox.DataSource = letterTemplates;
            templateComboBox.DisplayMember = "Name";
            templateComboBox.ValueMember = "DocumentId";
            templateComboBox.SelectedValue = defaultLetterId;

            PopulateNotes();            
            if (defaultLetterId == 0)
            {
                EnableButtons(false);
            }
        }

        /// <summary>
        /// Disable all the controls.
        /// </summary>
        public void DisableControls()
        {
            templateComboBox.Enabled = false;           
            notesGroupPanel.Visible = false;
        }

        /// <summary>
        /// Delete selected freeform notes
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DeleteFreeformReason(object sender, EventArgs e)
        {
            FreeformUI freeformUI = (FreeformUI)sender;
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
        /// Add a new freeform note.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void createFreeformButton_Click(object sender, EventArgs e)
        {   
            FreeformUI freeformUI = CreateFreeformUI(true);

            freeformUI.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
        
            int notesCount = notesPanel.Controls.Count;
            if (notesCount % 2 != 0)
            {
                freeformUI.BackColor = Color.FromArgb(236, 238, 245);
            }            
            freeformUI.SetFocus();
        }

        /// <summary>
        /// Create a freeform UI.
        /// </summary>
        /// <returns></returns>
        private FreeformUI CreateFreeformUI(bool isFreeformNote)
        {
            FreeformUI freeformUI = new FreeformUI();

            freeformUI.SetExecutionContext(Context);
            freeformUI.SetPane(Pane);
            freeformUI.Localize();
            freeformUI.DeleteFreeformHandler = new EventHandler(DeleteFreeformReason);

            freeformUI.IsFreeform = isFreeformNote;
            freeformUI.Width = notesPanel.Width;
            notesPanel.Controls.Add(freeformUI);
            if (maxLength > 110)
            {
                freeformUI.Width = maxLength * 6;
            }
            else
            {
                freeformUI.Width = notesPanel.Width;
            }

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
        /// Occurs when user clicks on continue button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void continueButton_Click(object sender, EventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

            NotesDetails note;
            errorProvider.Clear();
            NotesList.Clear();
            freeformNotesList.Clear();
            ((Form)(this.Parent)).DialogResult = DialogResult.OK;

            if (notesPanel.Visible)
            {
                foreach (FreeformUI freeformUI in notesPanel.Controls)
                {
                    if (freeformUI.Checked)
                    {
                        if (!freeformUI.IsFreeform)
                        {
                            note = new NotesDetails();
                            note.Id = freeformUI.NoteId;
                            note.DisplayText = freeformUI.FreeformCheckBox.Text.Replace("&&", "&"); ;
                            notesList.Add(note);
                        }
                        else
                        {
                            if (!Validator.Validate(freeformUI.FreeformTextBox.Text, ROIConstants.AllCharacters))
                            {
                                errorProvider.SetError(freeformUI.FreeformTextBox, rm.GetString(ROIErrorCodes.InvalidName));
                                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                            }
                            else
                            {
                                freeformNotesList.Add(freeformUI.FreeformTextBox.Text);
                            }
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Enables the buttons
        /// </summary>
        /// <param name="enable"></param>
        public void EnableButtons(bool enable)
        {  
            notesGroupPanel.Visible = enable;
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the user presses the Enter key.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = continueButton;
        }

        /// <summary>
        /// Localize the UI text.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            header.Title = rm.GetString(LetterTemplateType + ".header.title");
            header.Information = rm.GetString(LetterTemplateType + ".header.info");

            SetLabel(rm, templateLabel);
            SetLabel(rm, notesLabel);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, createFreeformButton);
            SetLabel(rm, continueButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, invoiceDueLabel);
            SetLabel(rm, daysLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, continueButton);
            SetTooltip(rm, toolTip, cancelButton);
            SetTooltip(rm, toolTip, createFreeformButton);
        }

        /// <summary>
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == continueButton)
            {
                return control.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Gets localize key.
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        private string GetLocalizedString(string key)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string pleaseSelect = rm.GetString(key);
            return pleaseSelect;
        }       

        #endregion

        #region Properties

        /// <summary>
        /// Gets the header panel.
        /// </summary>
        public Panel TopPanel
        {
            get
            {
                return topPanel;
            }
        }

        /// <summary>
        /// Gets the selected letter template id.
        /// </summary>
        public long LetterTemplateId
        {
            get
            {
                return (long)templateComboBox.SelectedValue;
            }          
        }

        /// <summary>
        /// Gets the selected letter template's name.
        /// </summary>
        public string LetterTemplateName
        {
            get
            {
                return (templateComboBox.SelectedIndex > 0 && templateComboBox.SelectedItem != null) 
                        ? ((LetterTemplateDetails)templateComboBox.SelectedItem).Name 
                        : string.Empty;
            }
        }

        /// <summary>
        /// Gets the selected notes.
        /// </summary>
        public Collection<NotesDetails> NotesList
        {
            get
            {
                return notesList;
            }
        }

        /// <summary>
        /// Gets the selected freeform notes.
        /// </summary>
        public Collection<string> FreeformNotesList
        {
            get
            {
                return freeformNotesList;
            }
        }

        /// <summary>
        /// Gets the letter template type
        /// </summary>
        public virtual string LetterTemplateType
        {
            get
            {
                return null;
            }
        }

        /// <summary>
        /// Gets the invoice due days
        /// </summary>
        public int DueDays
        {
            get 
            {
                dueDays = int.Parse(invoiceDueTextBox.Text.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), System.Threading.Thread.CurrentThread.CurrentUICulture);
                return dueDays;
            }
        }

        /// <summary>
        /// Gets the status of invoice due days overwrite
        /// </summary>
        public bool IsOverWriteDueDays
        {
            get
            {
                return DueDays != invoiceDueDays.DueDateInDays;                
            }
        }

        /// <summary>
        /// Gets the previous configured due days value
        /// </summary>
        public int OldDueDays
        {
            get { return invoiceDueDays.DueDateInDays; }
        }

        /// <summary>
        /// Get the selected letter template id
        /// </summary>
        public long LetterTemplateSeqId
        {
            get { return ((LetterTemplateDetails)templateComboBox.SelectedItem).Id; }
        }

        /// <summary>
        /// Get the selected Letter template Document Id
        /// </summary>
        public long LetterTemplateDocumentId
        {
            get { return ((LetterTemplateDetails)templateComboBox.SelectedItem).DocumentId; }
        }
        #endregion      
    }
}
