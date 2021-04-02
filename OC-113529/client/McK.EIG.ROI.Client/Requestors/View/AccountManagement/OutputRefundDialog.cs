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
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Request.View.BillingPayment;

using McK.EIG.ROI.Client.Web_References.ROIRequestorWS;

#endregion

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    public partial class OutputRefundDialog : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ReleaseDialog));

        private HeaderUI header;
        private Collection<NotesDetails> configureNotes;
        private ROIProgressBar fileTransferProgress;
        private OutputPropertyDetails outputPropertyDetails;
        private DocInfoList docInfoList;
        
        private DocumentInfoList documentInfoList;
        private int maxLength;

        internal static EventHandler ProgressHandler;
        private bool isCancelClicked;
        private RequestorRefundDetail outputRequestorRefundDetail;
        private const string DialogName = "Output Refund";

        private int letterCount;


        #endregion

        #region Constructor

        private OutputRefundDialog()
        {
            InitializeComponent();
            header = InitHeaderUI();
			//CR#377220	
            refundLetterCheckBox.Checked = true;
            statementLetterComboBox.Enabled = false;
            dateRangeComboBox.Enabled = false;
            statementLetterNotesGroupPanel.Enabled = false;
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public OutputRefundDialog(IPane pane)
            : this()
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
            cancelButton.Click += new EventHandler(cancelButton_Click);
            refundLetterComboBox.SelectedIndexChanged += new EventHandler(refundLetterComboBox_SelectedIndexChanged);
            refundLetterCheckBox.CheckedChanged += new EventHandler(refundLetterCheckBox_CheckedChanged);
            outputStatementCheckBox.CheckedChanged += new EventHandler(statementLetterCheckBox_CheckedChanged);
            statementLetterComboBox.SelectedIndexChanged += new EventHandler(statementLetterComboBox_SelectedIndexChanged);
            dateRangeComboBox.SelectedIndexChanged += new EventHandler(dateRangeComboBox_SelectedIndexChanged);
        }

        /// <summary>
        /// Prepopulates the letter templates.
        /// </summary>
        public void PrePopulate(IList<LetterTemplateDetails> statementTemplates, IList<LetterTemplateDetails> refundTemplates,
                                long defaultstatementId, long defaultrefundId, RequestorRefundDetail outputRequestorRefundDetail)
                                
        {
            this.outputRequestorRefundDetail = outputRequestorRefundDetail;
            EnableEvents();
            LetterTemplateDetails forSelect = new LetterTemplateDetails();
            forSelect.Id = 0;
            forSelect.Name = GetLocalizedString("letterTemplateText");

            statementTemplates.Insert(0, forSelect);
            refundTemplates.Insert(0, forSelect);

            if (statementTemplates.Count > 1)
            {
                statementLetterComboBox.DataSource = statementTemplates;
                statementLetterComboBox.DisplayMember = "Name";
                statementLetterComboBox.ValueMember = "DocumentId";
                statementLetterComboBox.SelectedValue = defaultstatementId;
            }
            else
            {
                statementLetterMessageLabel.Visible = true;
                statementLetterPanel.Visible = false;
            }

            if (refundTemplates.Count > 1)
            {
                refundLetterComboBox.DataSource = refundTemplates;
                refundLetterComboBox.DisplayMember = "Name";
                refundLetterComboBox.ValueMember = "DocumentId";
                refundLetterComboBox.SelectedValue = defaultrefundId;
            }
            else
            {
                refundLetterMessageLabel.Visible = true;
                refundLetterPanel.Visible = false;
            }
            refundLetterCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                           GetLocalizedString(GetType().Name + ".refundLetterCountLabel"),
                                           0);
            PopulateDateRange();
        }

        /// <summary>
        /// Populate the pre defined LetterType in letterTypeComboBox.
        /// </summary>
        /// <param name="requestStatus"></param>
        public void PopulateDateRange()
        {
            IList dateRange = EnumUtilities.ToList(typeof(McK.EIG.ROI.Client.Requestors.Model.DateRange));
            dateRangeComboBox.DataSource = dateRange;
            dateRangeComboBox.ValueMember = "key";
            dateRangeComboBox.DisplayMember = "value";
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

            if (freeformUI.Parent.Equals(refundLetterNotesPanel))
            {
                notesPanel = refundLetterNotesPanel;
            }
            else
            {
                notesPanel = statementLetterNotesPanel;
            }

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
                freeformUI.Width = refundLetterNotesPanel.Width;
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
            statementLetterCreateFreeformButton.Text = rm.GetString("createFreeformButton.OutputInvoiceDialog");

            SetLabel(rm, headerLabel);
            SetLabel(rm, addLetterGroupBox);
            SetLabel(rm, refundLetterGroupBox);
            SetLabel(rm, statementLetterGroupBox);
            SetLabel(rm, refundLetterCheckBox);
            SetLabel(rm, outputStatementCheckBox);
            SetLabel(rm, refundLetterLabel);
            SetLabel(rm, statementLetterLabel);
            SetLabel(rm, dateRangeLabel);

            SetLabel(rm, refundLetterHeaderLabel);
            SetLabel(rm, statementLetterHeaderLabel);
            SetLabel(rm, refundLetterCreateFreeformButton);

            SetLabel(rm, requiredLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, printButton);
            SetLabel(rm, refundLetterMessageLabel);

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
        /// Occurs when user changes the statement Letter template.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void statementLetterComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
            if (statementLetterComboBox.SelectedIndex > 0)
            {
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)statementLetterComboBox.SelectedItem;
                if (letterTemplate.HasNotes)
                {
                    PopulateNotes(LetterType.Invoice);
                    statementLetterNotesGroupPanel.Visible = true;
                }
                else
                {
                    statementLetterNotesGroupPanel.Visible = false;
                }
            }
            else
            {
                statementLetterNotesGroupPanel.Visible = false;
            }
        }

        /// <summary>
        /// Occurs when user changes the statement Letter template.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void dateRangeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
        }

        private void refundLetterComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
            if (refundLetterComboBox.SelectedIndex > 0)
            {
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)refundLetterComboBox.SelectedItem;
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

        /// <summary>
        /// Occurs when user clicks on output requestor letter checkbox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void refundLetterCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            
            EnablePreviewPrintSaveButtons();
            if (refundLetterCheckBox.Checked)
            {
                letterCount = outputStatementCheckBox.Checked ? 2 : 1;
                refundLetterCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                           GetLocalizedString(GetType().Name + ".refundLetterCountLabel"),
                                           letterCount);
                refundLetterComboBox.Enabled = true;
                requestorLetterNotesGroupPanel.Enabled = true;
            }
            else
            {
                letterCount = outputStatementCheckBox.Checked ? 1 : 0;
                refundLetterCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                           GetLocalizedString(GetType().Name + ".refundLetterCountLabel"),
                           letterCount);
                refundLetterComboBox.Enabled = false;
                requestorLetterNotesGroupPanel.Enabled = false;
            }
        }

        /// <summary>
        /// Occurs when user click on invoice checkbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void statementLetterCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnablePreviewPrintSaveButtons();
            if (outputStatementCheckBox.Checked && statementLetterComboBox.Items.Count > 0)
            {
                statementLetterComboBox.Enabled = true;
				//CR#377220	
                dateRangeComboBox.Enabled = true;
                statementLetterNotesGroupPanel.Enabled = true;
            }
            else
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                statementLetterComboBox.Enabled = false;
				//CR#377220	
                dateRangeComboBox.Enabled = false;
                statementLetterNotesGroupPanel.Enabled = false;
            }

            if (outputStatementCheckBox.Checked)
            {
                letterCount = refundLetterCheckBox.Checked ? 2 : 1;
                refundLetterCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                           GetLocalizedString(GetType().Name + ".refundLetterCountLabel"),
                                           letterCount);
            }
            else
            {
                letterCount = refundLetterCheckBox.Checked ? 1 : 0;
                refundLetterCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                           GetLocalizedString(GetType().Name + ".refundLetterCountLabel"),
                           letterCount);
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
            if (((refundLetterCheckBox.Checked && refundLetterComboBox.SelectedIndex > 0) || !refundLetterCheckBox.Checked) &&
                ((outputStatementCheckBox.Checked && statementLetterComboBox.SelectedIndex > 0 && dateRangeComboBox.SelectedIndex > 0) || !outputStatementCheckBox.Checked) &&
                (refundLetterCheckBox.Checked || outputStatementCheckBox.Checked))
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
            if (enable)
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
                    notesPanel = refundLetterNotesPanel;
                }
                else
                {
                    notesPanel = statementLetterNotesPanel;
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
            refundLetterNotesPanel.HorizontalScroll.Value = 0;
        }

        /// <summary>
        /// Occurs when user clicks on Invoice Create Freeform Note.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceCreateFreeformButton_Click(object sender, EventArgs e)
        {
            CreateFreeform(LetterType.Invoice.ToString());
            statementLetterNotesPanel.HorizontalScroll.Value = 0;
        }

        /// <summary>
        /// Creates the freeform UI.
        /// </summary>
        /// <param name="letterType"></param>
        private void CreateFreeform(string letterType)
        {
            Panel notesPanel;

            if (letterType == LetterType.CoverLetter.ToString())
            {
                notesPanel = refundLetterNotesPanel;
            }
            else
            {
                notesPanel = statementLetterNotesPanel;
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
            OutputRefund(sender);
        }

        /// <summary>
        /// Occurs when save button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            OutputRefund(sender);
        }

        private void OutputRefund(object sender)
        {
            try
            {
                long docId = 0;
                List<string> refundNotes = null;
                List<string> statementNotes = null;
                RequestorRefundDetail requestorRefundDetail = new RequestorRefundDetail();
                requestorRefundDetail.RequestorID = outputRequestorRefundDetail.RequestorID;
                requestorRefundDetail.RequestorName = outputRequestorRefundDetail.RequestorName;
                requestorRefundDetail.RequestorType = outputRequestorRefundDetail.RequestorType;
                requestorRefundDetail.RefundAmount = outputRequestorRefundDetail.RefundAmount;
                requestorRefundDetail.RefundDate = outputRequestorRefundDetail.RefundDate;
				requestorRefundDetail.Note = outputRequestorRefundDetail.Note;
                if (refundLetterCheckBox.Checked)
                {
                    refundNotes = AddNotes(refundLetterNotesPanel);
                    LetterTemplateDetails refundLetterTemplate = (LetterTemplateDetails)refundLetterComboBox.SelectedItem;
                    requestorRefundDetail.TemplateId = refundLetterTemplate.DocumentId;
                    requestorRefundDetail.TemplateName = refundLetterTemplate.FileName;
                    if (refundNotes != null)
                    {
                        requestorRefundDetail.Notes = refundNotes.ToArray();
                    }
                }

                RequestorStatementDetail requestorStatementDetail = new RequestorStatementDetail();

                if (outputStatementCheckBox.Checked)
                {
                    statementNotes = AddNotes(statementLetterNotesPanel);
                    LetterTemplateDetails summaryLetterTemplate = (LetterTemplateDetails)statementLetterComboBox.SelectedItem;
                    string requestorLetterTemplateName = (summaryLetterTemplate != null) ? summaryLetterTemplate.Name : string.Empty;
                    requestorStatementDetail.TemplateFileId = summaryLetterTemplate.DocumentId;
                    requestorStatementDetail.TemplateName = summaryLetterTemplate.FileName;
                    requestorStatementDetail.DateRange = (McK.EIG.ROI.Client.Requestors.Model.DateRange)Enum.Parse(typeof(McK.EIG.ROI.Client.Requestors.Model.DateRange), dateRangeComboBox.SelectedValue.ToString());
                    if (statementNotes != null)
                    {
                        requestorStatementDetail.Notes = statementNotes.ToArray();
                    }
                    requestorStatementDetail.RequestorId = outputRequestorRefundDetail.RequestorID;
                    requestorRefundDetail.RequestorStatementDetail = requestorStatementDetail;
                }

                DocumentInfoList documentInfoList = RequestorController.Instance.ViewRequestorRefund(requestorRefundDetail);

                DialogResult result = DialogResult.None;
                string letterType = LetterType.RequestorStatement.ToString();
                DestinationType senderdestinationType = (sender == printButton) ? DestinationType.Print : DestinationType.File;
                result = ShowViewer(documentInfoList.Name, letterType, senderdestinationType);

                //CR#377445
                if (result == DialogResult.Cancel)
                {
                    ((Form)(this.Parent)).Visible = true;
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                }

                if (result == DialogResult.OK)
                {
                    OutputRequestDetails outputRequestDetails = new OutputRequestDetails(0, 0, string.Empty, null);
                    OutputViewDetails outputViewDetails = new OutputViewDetails();

                    outputRequestDetails.OutputDestinationDetails = outputPropertyDetails.OutputDestinationDetails[0];
                    outputViewDetails = outputPropertyDetails.OutputViewDetails;

                    string outputMethod = outputRequestDetails.OutputDestinationDetails.Type;
                    DestinationType destinationType = new DestinationType();
                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.File.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        destinationType = DestinationType.File;

                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        destinationType = DestinationType.Fax;
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Print.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        destinationType = DestinationType.Print;
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Email.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        destinationType = DestinationType.Email;
                    }
                    requestorRefundDetail.OutputMethod = outputRequestDetails.OutputDestinationDetails.Type;
                    requestorRefundDetail.QueueSecureWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                    if (outputStatementCheckBox.Checked)
                    {
                        requestorRefundDetail.RequestorStatementDetail.OutputMethod = outputRequestDetails.OutputDestinationDetails.Type;
                        requestorRefundDetail.RequestorStatementDetail.QueueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                    }
                    DocInfoList docInfoList = RequestorController.Instance.CreateRequestorRefund(requestorRefundDetail, true);
                    foreach (DocInfo info in docInfoList.docInfos)
                    {
                        outputRequestDetails.RequestParts.Add(BuildROIRequestPartDetails(info));
                    }
                    Application.DoEvents();
                    OutputController.Instance.SubmitOutputRequest(outputRequestDetails, destinationType,
                                                                  outputViewDetails, false, false);

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
        /// Build ROI request part details.
        /// </summary>
        /// <param name="fileName"></param>
        private static RequestPartDetails BuildROIRequestPartDetails(DocInfo documentInfo)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();

            //Unique content id is used for coverletter is included or not
            if (string.Compare(documentInfo.type, LetterType.Invoice.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) != 0)
            {
                requestPartDetails.ContentId = documentInfo.id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            else
            {
                requestPartDetails.ContentId = string.Empty;
            }
            requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.FileIds = documentInfo.type + "." + documentInfo.id;

            requestPartDetails.PropertyLists.Add(propertyDetails);

            return requestPartDetails;
        }

        /// <summary>
        /// Shows the viewer.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        /// <param name="fileName"></param>
        /// <returns></returns>
        public DialogResult ShowViewer(string fileName, string letterType, DestinationType senderdestinationType)
        {
            ROIViewer viewer = new ROIViewer(Pane, letterType, DialogName);

            viewer.ReleaseDialog = false;
            viewer.DestinationType = senderdestinationType;
            viewer.OverDueDialog = true;
            viewer.UiName = "Output Refund"; 
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

                DialogResult result = dialog.ShowDialog(this);

                outputPropertyDetails = viewer.OutputPropertyDetails;
                isCancelClicked = viewer.IsCancelClicked;
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


        #endregion

        #region Properties

        /// <summary>
        /// Gets the header panel.
        /// </summary>
        public Panel TopPanel
        {
            get { return topPanel; }
        }
        #endregion
        
    }
}
