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

#region namespace

using System;
using System.Collections;
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.OverDueInvoice.Controller;
using McK.EIG.ROI.Client.OverDueInvoice.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Request.View.BillingPayment;

#endregion

namespace McK.EIG.ROI.Client.OverDueInvoice.View
{
    public partial class OutputInvoiceDialog : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ReleaseDialog));

        private HeaderUI header;
        private Collection<NotesDetails> configureNotes;
        private ROIProgressBar fileTransferProgress;        
        private OutputPropertyDetails outputPropertyDetails;        
             
        private DocumentInfo documentInfo;
        private DocumentInfoList documentInfoList;
        private int maxLength;

        internal static EventHandler ProgressHandler;                

        private PreviewOverDueInvoices previewOverDueInvoices;
        private Hashtable requestorInvoices; // For requestor grouping.

        // requestor invoices from pastdueinvoice grid order.
        private List<RequestorInvoicesDetails> requestorInvoiceList;         

        //to maintain the grid sorted order
        private ArrayList requestorIds;
        private const string DialogName = "Output Invoice";

		// CR#359258 Add variable to hold the selected invoices and requestor letter's count
        private int outputInvoicesCount;
        private int outputRequestorLettersCount;

        #endregion

        #region Constructor

        private OutputInvoiceDialog()
        {
            InitializeComponent();
            header = InitHeaderUI();
        }       

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputInvoiceDialog(IPane pane) : this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
        }
       
        #endregion

        #region Methods
        
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
        /// Register events.
        /// </summary>
        public void EnableEvents()
        {
            printButton.Click += new EventHandler(printButton_Click);
            saveButton.Click += new EventHandler(saveButton_Click);
            cancelButton.Click +=new EventHandler(cancelButton_Click);
            requestorLetterComboBox.SelectedIndexChanged += new EventHandler(requestorLetterComboBox_SelectedIndexChanged);
            dateRangeComboBox.SelectedIndexChanged += new EventHandler(dateRangeComboBox_SelectedIndexChanged);
            requestorLetterCheckBox.CheckedChanged += new EventHandler(requestorLetterCheckBox_CheckedChanged);
            invoiceCheckBox.CheckedChanged += new EventHandler(invoiceCheckBox_CheckedChanged);
			// CR#359303
            previewCheckBox.CheckedChanged += new EventHandler(previewCheckBox_CheckedChanged);
        }

        /// <summary>
        /// Prepopulates the letter templates.
        /// </summary>
        public void PrePopulate(IList<LetterTemplateDetails> requestorLetterTemplates,
                                IList<LetterTemplateDetails> invoiceTemplates,
                                long defaultRequestorLetterId,
                                ArrayList requestorIds,
                                long defaultInvoiceId,
                                Hashtable requestorInvoices,
                                List<RequestorInvoicesDetails> requestorInvoiceList,
                                int invoiceCount)
        {
            EnableEvents();
            LetterTemplateDetails forSelect = new LetterTemplateDetails();
            forSelect.Id = 0;
            forSelect.Name = GetLocalizedString("letterTemplateText");

            requestorLetterTemplates.Insert(0, forSelect);
            invoiceTemplates.Insert(0, forSelect);

            if (requestorLetterTemplates.Count > 1)
            {
                requestorLetterComboBox.DataSource = requestorLetterTemplates;
                requestorLetterComboBox.DisplayMember = "Name";
                requestorLetterComboBox.ValueMember = "DocumentId";
                requestorLetterComboBox.SelectedValue = defaultRequestorLetterId;
            }
            else
            {
                requestorLetterMessageLabel.Visible = true;
                requestorLetterPanel.Visible = false;
            }
           

			// CR#359258 Set the invoices and requestor letters count
            outputInvoicesCount = invoiceCount;
            outputRequestorLettersCount = requestorInvoices.Count;

			//Set the default value as zero
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            invoiceCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                     GetLocalizedString(GetType().Name + ".header.info"),
                                     rm.GetString("zeroString"));

            requestorCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                       GetLocalizedString(GetType().Name + ".requestorCountLabel"),
                                       rm.GetString("zeroString"));

            requestorLetterComboBox.Enabled        = true;
            dateRangeComboBox.Enabled              = true;
            requestorLetterNotesGroupPanel.Enabled = true;
            requestorLetterCheckBox.Checked        = true; 

            this.requestorInvoices = new Hashtable();
            this.requestorInvoices = requestorInvoices;

            this.requestorInvoiceList = new List<RequestorInvoicesDetails>();
            this.requestorInvoiceList = requestorInvoiceList;

            this.requestorIds = new ArrayList();
            this.requestorIds = requestorIds;

            invoiceCheckBox.Checked = false;
            previewCheckBox.Checked = true;

            if (!previewCheckBox.Checked)
            {
                printButton.Enabled = IsAllowed(ROISecurityRights.ROIPrintFax);
                saveButton.Enabled = IsAllowed(ROISecurityRights.ROIExportToPDF);
            }
            PopulateDateRange();

        }


        /// <summary>
        /// 375,961 - Fix
        /// gets the default value to be set for the DateRange Combobox.
        /// </summary>
        private DateRange retrieveDefaultDateRange()
        {
            ICollection values = requestorInvoices.Values;
            if (null == values || values.Count <= 0)
            {
                return DateRange.None;
            }

            long greatestInvoiceAge = 0;
            foreach (RequestorInvoicesDetails invoice in values)
            {
                if (greatestInvoiceAge < invoice.GreatestInvoiceAge) {
                    greatestInvoiceAge = invoice.GreatestInvoiceAge;
                }
            }

            if (greatestInvoiceAge <= 30)
            {
                return DateRange.DAYS_30;
            }
            else if (greatestInvoiceAge > 30 && greatestInvoiceAge <= 60) 
            {
                return DateRange.DAYS_60;
            }
            else if (greatestInvoiceAge > 60 && greatestInvoiceAge <= 90)
            {
                return DateRange.DAYS_90;
            }
            else if (greatestInvoiceAge > 90 && greatestInvoiceAge <= 120)
            {
                return DateRange.DAYS_120;
            }
            else 
            {
                return DateRange.ALL;
            }
        }

        /// <summary>
        /// Delete selected freeform notes
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DeleteFreeformReason(object sender, EventArgs e)
        {
            FreeformUI freeformUI = (FreeformUI)sender;

            FlowLayoutPanel notesPanel;

            notesPanel = requestorLetterNotesPanel;

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
        /// Create a freeform UI.
        /// </summary>
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
                freeformUI.Width = requestorLetterNotesPanel.Width;
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
        /// Localize the UI text.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            header.Title = rm.GetString(GetType().Name + ".header.title");

            SetLabel(rm, headerLabel);
            SetLabel(rm, addLetterGroupBox);
            SetLabel(rm, requestorLetterGroupBox);
            SetLabel(rm, requestorLetterCheckBox);
            SetLabel(rm, invoiceCheckBox);
            SetLabel(rm, requestorLetterLabel);

            SetLabel(rm, requestorLetterHeaderLabel);
            SetLabel(rm, createFreeformButton);

            SetLabel(rm, requiredLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, previewCheckBox);
            SetLabel(rm, printButton);
            SetLabel(rm, dateRangeLabel);
            SetLabel(rm, requestorLetterMessageLabel);

            ProgressHandler = new EventHandler(Process_NotifyProgress);
            InitProgress();
        }

        /// <summary>
        /// Method initilize the progress bar.
        /// </summary>
        private void InitProgress()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            fileTransferProgress = new ROIProgressBar();
            progressPanel.Controls.Add(fileTransferProgress);
            progressPanel.BringToFront();
            fileTransferProgress.BringToFront();
            fileTransferProgress.MessageText.Text = rm.GetString("ProgressMessage");
            fileTransferProgress.MessageText.BringToFront();
            fileTransferProgress.PercentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
            progressPanel.Visible = false;
        }

        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            ShowProgress((FileTransferEventArgs)e);
        }

        /// <summary>
        /// Method call the progress bar to display.
        /// </summary>
        /// <param name="e"></param>
        public void ShowProgress(FileTransferEventArgs e)
        {
            switch (e.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    progressPanel.Visible = true;
                    fileTransferProgress.Visible = true;
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.InProgress:
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.Finish:
                    fileTransferProgress.ShowProgress(e);
                    fileTransferProgress.Visible = false;
                    progressPanel.Visible = false;
                    break;
            }
        }

        /// <summary>
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if ((control == requiredLabel))
            {
                return base.GetLocalizeKey(control);
            }
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets localized key of UI controls tooltip.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
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


        /// <summary>
        /// Populate the pre defined LetterType in letterTypeComboBox.
        /// </summary>
        /// <param name="requestStatus"></param>
        public void PopulateDateRange()
        {
            IList dateRange = EnumUtilities.ToList(typeof(DateRange));
            dateRangeComboBox.DataSource = dateRange;
            dateRangeComboBox.ValueMember = "key";
            dateRangeComboBox.DisplayMember = "value";

            // 375,961 - Fix
            DateRange defaultDateRange = retrieveDefaultDateRange();
            dateRangeComboBox.SelectedValue = defaultDateRange;
        }

        private void requestorLetterComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
            if (requestorLetterComboBox.SelectedIndex > 0)
            {   
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)requestorLetterComboBox.SelectedItem;
                if (letterTemplate.HasNotes)
                {
                    PopulateNotes(LetterType.RequestorStatement);
                    requestorLetterNotesGroupPanel.Visible = true;
                }
                else
                {
                    requestorLetterNotesGroupPanel.Visible = false;
                }
            }
            else
            {
                requestorLetterNotesGroupPanel.Visible = false;
            }
        }

        private void dateRangeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
        }

        /// <summary>
        /// Occurs when user clicks on output requestor letter checkbox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void requestorLetterCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
            if (requestorLetterCheckBox.Checked)
            {
				// CR#359258 Set the requestor count
                requestorCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                           GetLocalizedString(GetType().Name + ".requestorCountLabel"),
                                           outputRequestorLettersCount);
                requestorLetterComboBox.Enabled = true;                
                requestorLetterNotesGroupPanel.Enabled = true;
                dateRangeComboBox.Enabled = true;
            }
            else
            {
				// CR#359258 Set the default value as zero for requestor count
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());                
                requestorCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                           GetLocalizedString(GetType().Name + ".requestorCountLabel"),
                           rm.GetString("zeroString"));
                requestorLetterComboBox.Enabled = false;
                requestorLetterNotesGroupPanel.Enabled = false;
                dateRangeComboBox.Enabled = false;
            }
        }

        /// <summary>
        /// Occurs when user click on invoice checkbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
            if (invoiceCheckBox.Checked)
            {
                // CR#359258 Set the invoice count
                invoiceCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                         GetLocalizedString(GetType().Name + ".header.info"),
                                         outputInvoicesCount);
            }
            else
            {
                // CR#359258 Set the default value as zero for invoice count
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                invoiceCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                         GetLocalizedString(GetType().Name + ".header.info"),
                                         rm.GetString("zeroString"));
            }
        }

        /// <summary>
        /// Occurs when user click on preview checkbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void previewCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
        }

        /// <summary>
        /// Enable buttons.
        /// </summary>
        private void EnablePreviewPrintSaveButtons()
        {
            EnableButtons(false);            
            if (((requestorLetterCheckBox.Checked && requestorLetterComboBox.SelectedIndex > 0 && dateRangeComboBox.SelectedIndex > 0) || !requestorLetterCheckBox.Checked) &&
                (requestorLetterCheckBox.Checked || invoiceCheckBox.Checked))
            {
                EnableButtons(true);
            }
            else
            {
                EnableButtons(false);
            }
        }
       
        /// <summary>
        /// Enable buttons.
        /// </summary>
        private void EnableButtons(bool enable)
        {
            printButton.Enabled = enable;
            saveButton.Enabled = enable;
            previewCheckBox.Enabled = enable;
			// CR#359303
            if (enable && !previewCheckBox.Checked)
            {
                printButton.Enabled = IsAllowed(ROISecurityRights.ROIPrintFax);
                saveButton.Enabled = IsAllowed(ROISecurityRights.ROIExportToPDF);
            }        

        }
        
        /// <summary>
        /// Populate configuration notes.        
        /// </summary>
        /// <param name="letterType"></param>
        private void PopulateNotes(LetterType letterType)
        {
            ROIViewUtility.MarkBusy(true);

            FlowLayoutPanel notesPanel = null;

            if (configureNotes == null)
            {
                configureNotes = ROIAdminController.Instance.RetrieveAllConfigureNotes();
            }

            if (configureNotes.Count > 0)
            {
                if (letterType == LetterType.RequestorStatement)
                {
                    notesPanel = requestorLetterNotesPanel;
                }
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
        /// Occurs when user clicks on Cover Letter Create Freeform Note.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void createFreeformButton_Click(object sender, EventArgs e)
        {
            CreateFreeform(LetterType.CoverLetter.ToString());
            requestorLetterNotesPanel.HorizontalScroll.Value = 0;
        }

        /// <summary>
        /// Occurs when user clicks on Invoice Create Freeform Note.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceCreateFreeformButton_Click(object sender, EventArgs e)
        {
            CreateFreeform(LetterType.Invoice.ToString());
        }

        /// <summary>
        /// Creates the freeform UI.
        /// </summary>
        /// <param name="letterType"></param>
        private void CreateFreeform(string letterType)
        {
            Panel notesPanel = null;

            if (letterType == LetterType.CoverLetter.ToString())
            {
                notesPanel = requestorLetterNotesPanel;
            }

            FreeformUI freeformUI = CreateFreeformUI(true, notesPanel);
        
            int notesCount = notesPanel.Controls.Count;
            if (notesCount % 2 != 0)
            {
                freeformUI.BackColor = Color.FromArgb(236, 238, 245);
            }
            freeformUI.SetFocus();
        }

        /// <summary>
        /// Occurs when user clicks on cancel button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
        }

        /// <summary>
        /// Occurs when print button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void printButton_Click(object sender, EventArgs e)
        {
            OutputOverDueInvoices(sender);
        }

        /// <summary>
        /// Occurs when save button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            OutputOverDueInvoices(sender);
        }

        /// <summary>
        /// Output overdue invoices.
        /// </summary>
        private void OutputOverDueInvoices(object sender)
        {
            try
            {
                errorProvider.Clear();
                documentInfo = null;
                documentInfoList = null;

                List<string> requestorLetterNotes = null;
                List<string> invoiceNotes = null;

                if (requestorLetterCheckBox.Checked)
                {
                    requestorLetterNotes = AddNotes(requestorLetterNotesPanel);
                }

                LetterTemplateDetails summaryLetterTemplate = (LetterTemplateDetails)requestorLetterComboBox.SelectedItem;

                string requestorLetterTemplateName = (summaryLetterTemplate != null) ? summaryLetterTemplate.Name : string.Empty;

                previewOverDueInvoices = new PreviewOverDueInvoices();
                previewOverDueInvoices.RequestorStatementDetail = new Requestors.Model.RequestorStatementDetail();
                if (dateRangeComboBox.SelectedValue.ToString().Equals("ALL"))
                {
                    previewOverDueInvoices.RequestorStatementDetail.DateRange = DateRange.ALL;
                }
                else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_30"))
                {
                    previewOverDueInvoices.RequestorStatementDetail.DateRange = DateRange.DAYS_30;
                }
                else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_60"))
                {
                    previewOverDueInvoices.RequestorStatementDetail.DateRange = DateRange.DAYS_60;
                }
                else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_90"))
                {
                    previewOverDueInvoices.RequestorStatementDetail.DateRange = DateRange.DAYS_90;
                }
                else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_120"))
                {
                    previewOverDueInvoices.RequestorStatementDetail.DateRange = DateRange.DAYS_120;
                }
                else if (dateRangeComboBox.SelectedValue.ToString().Equals("YEAR_TO_DATE"))
                {
                    previewOverDueInvoices.RequestorStatementDetail.DateRange = DateRange.YEAR_TO_DATE;
                }

                if (requestorLetterCheckBox.Checked)
                {
                    if (requestorLetterComboBox.SelectedIndex > 0)
                    {
                        previewOverDueInvoices.RequestorLetterTemplateId = summaryLetterTemplate.DocumentId;
                    }
                }
                previewOverDueInvoices.IsPreview = true; // Do not save preview invoices into DB

                if (requestorLetterNotes != null)
                {
                    foreach (string note in requestorLetterNotes)
                    {
                        previewOverDueInvoices.RequestorLetterNotes.Add(note);
                    }
                }

                if (invoiceNotes != null)
                {
                    foreach (string note in invoiceNotes)
                    {
                        previewOverDueInvoices.InvoiceNotes.Add(note);
                    }
                }

                // Requestor letter is included and save as File is selected for output
                if ((sender == saveButton) || (sender == printButton && requestorLetterCheckBox.Checked && requestorLetterComboBox.SelectedIndex > 0))
                {
                    // Loop thru the grouped requestor invoices. Arraylist contains sorted order of invoice ids.
                    foreach (long id in requestorIds)
                    {
                        previewOverDueInvoices.RequestorInvoicesList.Add((RequestorInvoicesDetails)requestorInvoices[id]);
                    }
                }
                else
                {
                    // Loop thru the requestor invoices ( Not Grouped )
                    foreach (RequestorInvoicesDetails requestorInvoicesDetails in this.requestorInvoiceList)
                    {
                        previewOverDueInvoices.RequestorInvoicesList.Add(requestorInvoicesDetails);
                    }
                }

                DialogResult result = DialogResult.None;
                previewOverDueInvoices.IsOutputInvoice = invoiceCheckBox.Checked;

                if (previewCheckBox.Checked)
                {
                    ROIViewUtility.MarkBusy(true);
                    documentInfoList = OverDueInvoiceController.Instance.PreviewOverDueInvoices(previewOverDueInvoices);
                    ROIViewUtility.MarkBusy(false);
                    DestinationType destinationType = (sender == printButton) ? DestinationType.Print : DestinationType.File;
                    result = ShowViewer(documentInfoList.Name, destinationType);
                }
                else
                {
                    ROIViewer viewer = new ROIViewer(Pane, LetterType.OverdueInvoice.ToString(), DialogName);
                    viewer.ReleaseDialog = false;
                    viewer.OverDueDialog = true;
                    if (sender == printButton)
                    {
                        result = viewer.ShowPrintDialog(this);
                    }
                    else
                    {
                        result = viewer.ShowFileDialog(this);
                    }
                    outputPropertyDetails = viewer.OutputPropertyDetails;
                }

                // TODO - To check if user has permission for print or save
                if (result == DialogResult.OK)
                {
                    Hashtable requestParts = new Hashtable();

                    OutputRequestDetails outputRequestDetails = new OutputRequestDetails(0, 0, string.Empty, null);
                    OutputViewDetails outputViewDetails = new OutputViewDetails();

                    outputRequestDetails.OutputDestinationDetails = outputPropertyDetails.OutputDestinationDetails[0];
                    outputViewDetails = outputPropertyDetails.OutputViewDetails;

                    // Update queue password only if the output destination type is file.
                    if (!string.IsNullOrEmpty(outputRequestDetails.OutputDestinationDetails.SecuredSecretWord))
                    {
                        previewOverDueInvoices.PropertiesMap.Add(ROIConstants.QueueSecretWord, outputRequestDetails.OutputDestinationDetails.SecuredSecretWord);
                    }
                    if (string.Compare(OutputPropertyDetails.OutputDestinationDetails[0].Type, DestinationType.File.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        previewOverDueInvoices.PropertiesMap.Add(ROIConstants.OutputMethod, "PDF");
                    }
                    else
                    {
                        previewOverDueInvoices.PropertiesMap.Add(ROIConstants.OutputMethod, outputRequestDetails.OutputDestinationDetails.Type);
                    }


                    if (requestorLetterCheckBox.Checked)
                    {
                        if (requestorLetterComboBox.SelectedIndex > 0)
                        {
                            previewOverDueInvoices.PropertiesMap.Add(PreBillInvoiceDetails.RequestorLetterTemplateIdKey, summaryLetterTemplate.Id);
                            previewOverDueInvoices.PropertiesMap.Add(PreBillInvoiceDetails.RequestorLetterTemplateNameKey, summaryLetterTemplate.Name);
                        }
                    }

                    previewOverDueInvoices.IsPreview = false; // Save the preview invoices into DB
                    documentInfoList = OverDueInvoiceController.Instance.PreviewOverDueInvoices(previewOverDueInvoices);

                    if (documentInfoList != null)
                    {
                        requestParts = BuildROIRequestPartDetails(documentInfoList, sender);
                    }

                    IDictionaryEnumerator requestPartList = requestParts.GetEnumerator();
                    while (requestPartList.MoveNext())
                    {
                        outputRequestDetails.RequestParts.Add((RequestPartDetails)requestPartList.Value);
                    }

                    outputRequestDetails.IsGroupingEnabled = (requestorLetterCheckBox.Checked && requestorLetterComboBox.SelectedIndex > 0) || (sender == saveButton);

                    //To identify the output method from the previe overdue invoice viewer if preview checkbox is checked
                    DestinationType destinationType = new DestinationType();
                    if (previewCheckBox.Checked)
                    {
                        destinationType = outputRequestDetails.OutputDestinationDetails.Type.Equals("FILE") ? DestinationType.File : DestinationType.Print;
                    }
                    else
                    {
                        destinationType = (sender == printButton) ? DestinationType.Print : DestinationType.File;
                    }

                    OutputController.Instance.SubmitOutputRequest(outputRequestDetails, destinationType,
                                                                              outputViewDetails, false, sender == saveButton);

                    //Audit the regenerated invoices with or without summary letter.
                    RegeneratedInvoiceAndLetterAudit(requestorInvoiceList, requestorLetterTemplateName,
                                                     outputRequestDetails.OutputDestinationDetails.Type);
                }

                if (result == DialogResult.Cancel)
                {
                    ((Form)(this.Parent)).Visible = true;
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                }
                else if (result == DialogResult.OK)
                {
                    ((Form)(this.Parent)).DialogResult = DialogResult.OK;
                    ((Form)(this.Parent)).Close();
                }
                else if (result == DialogResult.None)
                {
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                ((Form)(this.Parent)).Visible = true;
                ((Form)(this.Parent)).DialogResult = DialogResult.None;
            }

        }

        /// <summary>
        /// Audit the regenerated invoices with or without summary letter
        /// </summary>
        /// <param name="requestorInvoices"></param>
        private void RegeneratedInvoiceAndLetterAudit(List<RequestorInvoicesDetails> requestorInvoices,
                                                      string requestorLetterTemplateName,
                                                      string outputType)
        {
            Collection<CommentDetails> commentDetails = new Collection<CommentDetails>();
            CommentDetails details;

            AuditEvent auditEvent = new AuditEvent();

            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvent.EventId = 1;
            auditEvent.Facility = ROIConstants.FacilityName;
            // ToDo - Check automatically save null value if we are not set Mrn & Encounter 
            auditEvent.Mrn = null;
            auditEvent.Encounter = null;

            StringBuilder auditComment = new StringBuilder();            

            if (invoiceCheckBox.Checked)
            {
                string eventMessage;
                auditComment.Append("Invoices were regenerated for: ");
                foreach (RequestorInvoicesDetails reqInvoicesDetails in requestorInvoices)
                {
                    //Make an entry into Event History for each request
                    //create the event log in event table
                    details = new CommentDetails();
                    details.RequestId = reqInvoicesDetails.RequestId;
                    details.EventType = EventType.OverDueInvoiceSent;                        
                    //end
                    string invoiceList = string.Empty;
                    foreach (long invoiceId in reqInvoicesDetails.InvoiceIds)
                    {
                        invoiceList += invoiceId + ",";
                    }
                    invoiceList = invoiceList.TrimEnd(',');
                    auditComment.Append(invoiceList + " of request " + reqInvoicesDetails.RequestId + " was regenerated. ");
                    eventMessage = "Invoices were regenerated for: " + invoiceList + " of request " + reqInvoicesDetails.RequestId + " was regenerated.";
                    details.EventRemarks = eventMessage;
                    commentDetails.Add(details);
                }
                
            }
            if (requestorLetterCheckBox.Checked)
            {
                if (requestorLetterComboBox.SelectedIndex > 0)
                {
                    auditComment.Append(requestorLetterTemplateName + ",Requestor letter was output for: ");
                    string requestorNames = string.Empty;
                    foreach (RequestorInvoicesDetails reqInvoicesDetails in requestorInvoices)
                    {
                        requestorNames += reqInvoicesDetails.RequestorName + ",";
                    }
					//CR#359224
                    auditComment.Append(requestorNames.TrimEnd(new char[]{','}));
                }
            }

            auditEvent.ActionCode = BillingPaymentInfoUI.RetrieveActionCode(outputType);
            auditEvent.Comment = auditComment.ToString();            

            try
            {
                Application.DoEvents();
                ROIController.Instance.CreateAuditEntry(auditEvent);
                RequestController.Instance.CreateCommentsList(commentDetails);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
            }
        }

        /// <summary>
        /// Build ROI request part details.
        /// </summary>
        /// <param name="fileName"></param>
        private Hashtable BuildROIRequestPartDetails(DocumentInfoList documentInfoList, object sender)
        {   
            RequestPartDetails requestPartDetails = null;
            PropertyDetails propertyDetails;
            int documentInfoListCount = 0;

            Hashtable requestParts = new Hashtable();
            foreach (DocumentInfo docInfo in documentInfoList.DocumentInfoCollection)
            {
                documentInfoListCount++;

                if (requestorLetterComboBox.SelectedIndex != 0 || sender == saveButton) // Requestor Grouping
                {
                    if (!requestParts.ContainsKey(docInfo.RequestorGroupingKey))
                    {
                        requestPartDetails = new RequestPartDetails();
                        requestPartDetails.ContentId = docInfo.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                        requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

                        propertyDetails = new PropertyDetails();
                        propertyDetails.FileIds = docInfo.Type + "." + docInfo.Id + ",";                        

                        if (!string.IsNullOrEmpty(docInfo.RequestorGroupingKey))
                        {
                            propertyDetails.RequestorGrouping = docInfo.RequestorGroupingKey;
                        }

                        requestPartDetails.PropertyLists.Add(propertyDetails);

                        requestParts[docInfo.RequestorGroupingKey] = requestPartDetails;
                    }
                    else
                    {
                        requestPartDetails = (RequestPartDetails)requestParts[docInfo.RequestorGroupingKey];
                        propertyDetails = requestPartDetails.PropertyLists[0];
                        propertyDetails.FileIds += docInfo.Type + "." + docInfo.Id + ",";
                    }
                }
                else
                {   
                    if (documentInfoListCount == 1)
                    {
                        requestPartDetails = new RequestPartDetails();
                        requestPartDetails.ContentId = docInfo.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                        requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;
                        propertyDetails = new PropertyDetails();
                        propertyDetails.FileIds = docInfo.Type + "." + docInfo.Id + ",";
                        requestPartDetails.PropertyLists.Add(propertyDetails);
                    }
                    else
                    {
                        propertyDetails = requestPartDetails.PropertyLists[0];
                        propertyDetails.FileIds += docInfo.Type + "." + docInfo.Id + ",";
                    }
                }
            }

            if (requestorLetterComboBox.SelectedIndex != 0 || sender == saveButton) // Requestor Grouping
            {
                IDictionaryEnumerator requestPartList = requestParts.GetEnumerator();
                while (requestPartList.MoveNext())
                {
                    RequestPartDetails requestPart = (RequestPartDetails)requestPartList.Value;
                    requestPart.PropertyLists[0].FileIds = requestPart.PropertyLists[0].FileIds.TrimEnd(new char[] { ',' });
                }
            }
            else
            {
                requestParts[0] = requestPartDetails;
            }
            return requestParts;
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the user presses the Enter key.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = saveButton;
        }       

        /// <summary>
        /// Add nodes into notes collection.
        /// </summary>
        /// <param name="notesPanel"></param>
        /// <param name="notes"></param>
        /// <param name="freeformNotes"></param>
        private List<string> AddNotes(FlowLayoutPanel notesPanel)
        {
            List<string> notes = new List<string>();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

            ((Form)(this.Parent)).DialogResult = DialogResult.OK;            

            if (notesPanel.Enabled)
            {
                foreach (FreeformUI freeformUI in notesPanel.Controls)
                {
                    NotesDetails note;
                    if (freeformUI.Checked)
                    {
                        if (!freeformUI.IsFreeform)
                        {
                            note = new NotesDetails();
                            note.Id = freeformUI.NoteId;
                            note.DisplayText = freeformUI.FreeformCheckBox.Text.Replace("&&", "&");
                            notes.Add(note.DisplayText);
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
                                if (freeformUI.FreeformTextBox.Text.Trim().Length > 0)
                                {
                                    notes.Add(freeformUI.FreeformTextBox.Text);
                                }
                            }
                        }
                    }
                }
            }
            return notes;           
        }      
       
        /// <summary>
        /// Shows the viewer.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        /// <param name="fileName"></param>
        /// <returns></returns>
        public DialogResult ShowViewer(string fileName, DestinationType destinationType)
        {
            ROIViewer viewer = new ROIViewer(Pane, LetterType.OverdueInvoice.ToString(), DialogName);
            
            viewer.ReleaseDialog = false;
            //set the destination type before set the value for OverDueDialog property to check the security rights for 
            //the selected output destination type
            viewer.DestinationType = destinationType;
            viewer.OverDueDialog = true;            

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                                                                                
            Form dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + GetType().Name + ".preview"), viewer);

            string filePath = string.Empty;
            try
            {
                filePath = BillingController.DownloadLetterTemplate(fileName, ProgressHandler);

                TimeSpan timeSpan = new TimeSpan(0, 0, 1);
                viewer.PDFDocumentViewer.SerialNumber = "PDFVW4WIN-ENMBG-1CA2A-9Z3DV-RVH0Y-24K1M";
                if (ROIViewUtility.WaitForFileInUse(filePath, timeSpan))
                {
                    //CR#391890
                    if (Path.GetExtension(filePath) == ".pdf")
                    {
                        viewer.PDFPageViewer.Visible = true;
                        viewer.DocumentViewer.Visible = false;
                        viewer.PDFDocumentViewer.Load(filePath);
                    }
                    else
                    {
                        viewer.PDFPageViewer.Visible = false;
                        viewer.DocumentViewer.Visible = true;
                        viewer.DocumentViewer.Url = new Uri(filePath);
                    }  
                }

                ((Form)(this.Parent)).Visible = false;                
                DialogResult result = dialog.ShowDialog(((Form)(this.Parent)));

                outputPropertyDetails = viewer.OutputPropertyDetails;
                return result;
            }
            catch (IOException cause)
            {
                log.FunctionFailure(cause);
                ROIException fileAlreadyOpen = new ROIException(ROIErrorCodes.FileAlreadyOpen);
                ROIViewUtility.Handle(Context, fileAlreadyOpen);
            }
            return DialogResult.Cancel;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the header panel.
        /// </summary>
        public Panel TopPanel
        {
            get { return topPanel; }
        }

        public bool IsInvoiced
        {
            get { return invoiceCheckBox.Checked; }
        }

        public OutputPropertyDetails OutputPropertyDetails
        {
            get { return outputPropertyDetails; }
        }      

        /// <summary>
        /// Gets the document infomation
        /// </summary>
        public DocumentInfo DocumentInfo
        {
            get { return documentInfo; }
        }

        /// <summary>
        /// Gets the document infomation list
        /// </summary>
        public DocumentInfoList DocumentInfoCollection
        {
            get { return documentInfoList; }
        }

        /// <summary>
        /// Gets all the overdue invoices.
        /// </summary>
        public PreviewOverDueInvoices PreviewOverDueInvoiceList
        {
            get
            {
                if (previewOverDueInvoices == null)
                {
                    return new PreviewOverDueInvoices();
                }
                return previewOverDueInvoices;
            }
        }

        #endregion
    }
}
