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
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Web_References.BillingCoreWS;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    public partial class StatementDialog : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(StatementDialog));

        private const string CheckBoxColumn = "checkBoxColumn";
        private const string RegeneratedInvoiceIdColumn = "regeneratedInvoiceId";
        private const string InvoiceIdColumn = "invoiceId";
        private const string CreatedDateColumn = "createdDate";
        private const string AmountColumn = "amount";

        private HeaderUI header;
        private Collection<NotesDetails> configureNotes;
        private ROIProgressBar fileTransferProgress;
        private OutputPropertyDetails outputPropertyDetails;

        private bool isCancelClicked;
        private int maxLength;

        internal static EventHandler ProgressHandler;
        private EIGCheckedColumnHeader dgvColumnHeader;
        private RequestorDetails requestor;

        private DocumentInfo documentInfo;
        private DocumentInfoList documentInfoList;
        private const string DialogName = "Statement Dialog";

        #endregion

        #region Constructor

        private StatementDialog()
        {
            InitializeComponent();
            header = InitHeaderUI();
            InitGrid();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public StatementDialog(EventHandler selectRequestorHandler, EventHandler cancelHandler, IPane pane)
            : this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
            EnablePastDueGridEvents();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            dgvColumnHeader = new EIGCheckedColumnHeader();
            DataGridViewCheckBoxColumn dgvCheckColumn = pastInvoiceGrid.AddCheckBoxColumn(CheckBoxColumn, string.Empty, "InvoiceSelected", 20);
            dgvCheckColumn.HeaderCell = dgvColumnHeader;
            dgvCheckColumn.Resizable = DataGridViewTriState.False;
            dgvCheckColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            dgvCheckColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.None;

            pastInvoiceGrid.AddTextBoxColumn(InvoiceIdColumn, "Invoice #", "InvoiceId", 85);
            DataGridViewTextBoxColumn dgvCreatedDateColumn = pastInvoiceGrid.AddTextBoxColumn(CreatedDateColumn, "Date | Time", "CreatedDate", 140);
            dgvCreatedDateColumn.DefaultCellStyle.Format = System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.ShortDatePattern + ' ' +
                                                           System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.LongTimePattern.Replace(":ss", "");
            DataGridViewTextBoxColumn dgvInvoiceAmountColumn = pastInvoiceGrid.AddTextBoxColumn(AmountColumn, "Invoice Amount", "Amount", 75);
            dgvInvoiceAmountColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopRight;
            dgvInvoiceAmountColumn.DefaultCellStyle.Format = "C";
            dgvInvoiceAmountColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            pastInvoiceGrid.Enabled = false;
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
        /// This method is used to enable the Release dialog local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
        }

        public void EnablePastDueGridEvents()
        {
            pastInvoiceCheckBox.CheckedChanged += new EventHandler(pastInvoiceCheckBox_CheckedChanged);
            pastInvoiceGrid.CellContentClick += new DataGridViewCellEventHandler(pastInvoiceGrid_CellContentClick);
            pastInvoiceGrid.ColumnHeaderMouseClick += new DataGridViewCellMouseEventHandler(pastInvoiceGrid_ColumnHeaderMouseClick);
        }

        /// <summary>
        /// This method is used to disable the invoice dialog local events
        /// </summary>
        public void DisableEvents()
        {

        }

        /// <summary>
        ///  Occurs when the user clicks a column header.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pastInvoiceGrid_ColumnHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            // If column header is changed then grid cell content click event triggered.
            // But in the grid cell content click the row index is -1. So that output button is always disabled.
            bool checkAll = dgvColumnHeader.CheckAll;
            pastInvoiceGrid.CellContentClick -= new DataGridViewCellEventHandler(pastInvoiceGrid_CellContentClick);
            if (e.ColumnIndex == 0)
            {
                for (int i = 0; i < pastInvoiceGrid.Rows.Count; i++)
                {
                    pastInvoiceGrid.Rows[i].Cells[0].Value = checkAll;
                }
            }
            pastInvoiceGrid.CellContentClick += new DataGridViewCellEventHandler(pastInvoiceGrid_CellContentClick);
            EnableContinueButton();
        }

        /// <summary>
        ///  Occurs when the content within a cell is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pastInvoiceGrid_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex > 0)
                return;

            int checkedRowCount = 0;
            foreach (DataGridViewRow row in pastInvoiceGrid.Rows)
            {
                DataGridViewCheckBoxCell checkBoxCell = (DataGridViewCheckBoxCell)row.Cells[0];

                if (Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture))
                {
                    checkedRowCount += 1;
                }

                if (e.RowIndex == row.Index)
                {
                    checkBoxCell.Value = !Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture);

                    if (Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        checkedRowCount += 1;
                    }

                    if (!Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        checkedRowCount -= 1;
                    }
                }
            }
            foreach (DataGridViewRow row in pastInvoiceGrid.Rows)
            {
                DataGridViewCheckBoxCell checkBoxCell = (DataGridViewCheckBoxCell)row.Cells[0];
                if (checkBoxCell.Value == null || checkBoxCell.Value.Equals(false))
                {
                    dgvColumnHeader.CheckAll = false;
                    pastInvoiceGrid.Invalidate();
                    break;
                }
                dgvColumnHeader.CheckAll = true;
                pastInvoiceGrid.Invalidate();
            }
            EnableContinueButton();
        }

        /// <summary>
        ///  Occurs when the past invoice checkbox is checked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pastInvoiceCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            bool unChecked = false;
            EnableContinueButton();
            pastInvoiceGrid.Enabled = pastInvoiceCheckBox.Checked;
            if (!pastInvoiceCheckBox.Checked)
            {
                for (int i = 0; i < pastInvoiceGrid.Rows.Count; i++)
                {
                    pastInvoiceGrid.Rows[i].Cells[0].Value = unChecked;
                }
                dgvColumnHeader.CheckAll = unChecked;
                pastInvoiceGrid.Invalidate();
            }

        }

        /// <summary>
        /// Populate past invoices
        /// </summary>
        public void PopulatePastInvoices()
        {
            Collection<PastInvoiceDetails> pastInvoices = RequestorController.Instance.RetrieveRequestorPastInvoiceDetails(requestor.Id);
            ComparableCollection<PastInvoiceDetails> list = new ComparableCollection<PastInvoiceDetails>(pastInvoices);
            list.ApplySort(TypeDescriptor.GetProperties(typeof(PastInvoiceDetails))["CreatedDate"], ListSortDirection.Descending);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(PastInvoiceDetails))["CreatedDate"], ListSortDirection.Descending);
            pastInvoiceGrid.SetItems((IFunctionCollection)list);
            pastInvoiceCheckBox.Enabled = pastInvoiceGrid.Rows.Count > 0;
        }

        /// <summary>
        /// Delete selected freeform notes
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DeleteFreeformReason(object sender, EventArgs e)
        {
            FreeformUI freeformUI = (FreeformUI)sender;

            FlowLayoutPanel notesPanel = null;

            if (freeformUI.Parent.Equals(coverLetterNotesPanel))
            {
                notesPanel = coverLetterNotesPanel;
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
                freeformUI.Width = coverLetterNotesPanel.Width;
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
            SetLabel(rm, statementGroupBox);
            SetLabel(rm, statementMessageLabel);
            SetLabel(rm, statementCheckBox);
            SetLabel(rm, statementLabel);

            SetLabel(rm, statementHeaderLabel);
            SetLabel(rm, createFreeformButton);
            SetLabel(rm, pastInvoiceGroupBox);
            SetLabel(rm, pastInvoiceCheckBox);

            SetLabel(rm, requestorLabel);
            SetLabel(rm, dateRangeLabel);

            SetLabel(rm, requiredLabel);
            SetLabel(rm, continueButton);
            SetLabel(rm, cancelButton);

            //rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            //SetTooltip(rm, toolTip, continueButton);
            //SetTooltip(rm, toolTip, cancelButton);
            //SetTooltip(rm, toolTip, createFreeformButton);

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


        public void PrePopulate(IList<LetterTemplateDetails> requestorLetterTemplates, long defaultRequestorLetterId,RequestorDetails requestorDetails)
        {
            LetterTemplateDetails forSelect = new LetterTemplateDetails();
            forSelect.Id = 0;
            forSelect.Name = GetLocalizedString("letterTemplateText");

            requestorLetterTemplates.Insert(0, forSelect);

            if (requestorLetterTemplates.Count > 1)
            {
                statementComboBox.DataSource = requestorLetterTemplates;
                statementComboBox.DisplayMember = "Name";
                statementComboBox.ValueMember = "DocumentId";
                statementComboBox.SelectedValue = defaultRequestorLetterId;
                statementCheckBox.Checked = true;
                statementComboBox.Enabled = true;
                dateRangecomboBox.Enabled = true;
            }
            else
            {
                statementMessageLabel.Visible = true;
                statementPanel.Visible = false;
                statementComboBox.Enabled = false;
                dateRangecomboBox.Enabled = false;
            }

            requestor = requestorDetails;
            requestorTypeLabel.Text = requestor.TypeName + ", " + requestor.FullName;
            PopulateDateRange();
            PopulatePastInvoices();
            
        }

        /// <summary>
        /// Populate the pre defined LetterType in letterTypeComboBox.
        /// </summary>
        /// <param name="requestStatus"></param>
        public void PopulateDateRange()
        {
            IList dateRange = EnumUtilities.ToList(typeof(McK.EIG.ROI.Client.Requestors.Model.DateRange));
            dateRangecomboBox.DataSource = dateRange;
            dateRangecomboBox.ValueMember = "key";
            dateRangecomboBox.DisplayMember = "value";
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
        /// Enables the continue button.
        /// </summary>
        private void EnableContinueButton()
        {
            continueButton.Enabled = false;

            if (statementCheckBox.Checked)
            {
                continueButton.Enabled = statementComboBox.SelectedIndex > 0 && dateRangecomboBox.SelectedIndex > 0;
                if (!continueButton.Enabled) return;
            }


            if (pastInvoiceCheckBox.Checked)
            {
                continueButton.Enabled = RetrieveAllSelectedPastInvoices(InvoiceIdColumn).Count > 0;
                if (!continueButton.Enabled) return;
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
                if (letterType == LetterType.CoverLetter)
                {
                    notesPanel = coverLetterNotesPanel;
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
                notesPanel = coverLetterNotesPanel;
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
        /// Return the control which needs to be show the error icon
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {

            }
            return null;
        }

        /// <summary>
        /// Returns all the selected past invoices
        /// </summary>
        /// <returns></returns>
        internal List<long> RetrieveAllSelectedPastInvoices(string columnName)
        {
            List<long> regeneratedInvoiceIds = new List<long>();

            foreach (DataGridViewRow row in pastInvoiceGrid.Rows)
            {
                if (row.Cells[CheckBoxColumn].Value != null && (bool)row.Cells[CheckBoxColumn].Value)
                {
                    regeneratedInvoiceIds.Add((long)row.Cells[columnName].Value);
                }
            }

            return regeneratedInvoiceIds;
        }


        private void HandleSecurityRights(string errorCode)
        {
            ROIException printFaxRights = new ROIException(errorCode);
            ROIViewUtility.Handle(Context, printFaxRights);
            ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
            ((Form)(this.Parent)).Close();
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the user presses the Enter key.
        /// </summary>
        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = continueButton;
        }

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
        public DialogResult ShowViewer(string fileName, string letterType)
        {
            ROIViewer viewer = new ROIViewer(Pane, letterType, DialogName);

            viewer.ReleaseDialog = false;
            viewer.DestinationType = DestinationType.Print;
            viewer.DisplayContinueButton = false;
            viewer.RequestorEmail = requestor.Email;
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

        public void CleanUp()
        {

        }

        private void createFreeformButton_Click(object sender, EventArgs e)
        {
            CreateFreeform(LetterType.CoverLetter.ToString());
            coverLetterNotesPanel.HorizontalScroll.Value = 0;
        }


        private void statementCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
            if (statementCheckBox.Checked)
            {
                statementComboBox.Enabled = true;
                statementNotesGroupPanel.Enabled = true;
                dateRangecomboBox.Enabled = true;
            }
            else
            {
                statementComboBox.Enabled = false;
                statementNotesGroupPanel.Enabled = false;
                dateRangecomboBox.Enabled = false;
            }
        }

        private void continueButton_Click(object sender, EventArgs e)
        {
            try
            {
                documentInfo = null;
                documentInfoList = null;
                long docId = 0;
                List<string> requestorLetterNotes = null;
                RequestorStatementDetail requestorStatementDetail = new RequestorStatementDetail();
                if (statementCheckBox.Checked)
                {
                    requestorLetterNotes = AddNotes(coverLetterNotesPanel);
                    LetterTemplateDetails summaryLetterTemplate = (LetterTemplateDetails)statementComboBox.SelectedItem;
                    string requestorLetterTemplateName = (summaryLetterTemplate != null) ? summaryLetterTemplate.Name : string.Empty;
                    requestorStatementDetail.TemplateFileId = summaryLetterTemplate.DocumentId;
                    requestorStatementDetail.TemplateName = summaryLetterTemplate.Name;
                    if (dateRangecomboBox.SelectedValue.ToString().Equals("ALL"))
                    {
                        requestorStatementDetail.DateRange = McK.EIG.ROI.Client.Requestors.Model.DateRange.ALL;
                    }
                    else if (dateRangecomboBox.SelectedValue.ToString().Equals("DAYS_30"))
                    {
                        requestorStatementDetail.DateRange = McK.EIG.ROI.Client.Requestors.Model.DateRange.DAYS_30;
                    }
                    else if (dateRangecomboBox.SelectedValue.ToString().Equals("DAYS_60"))
                    {
                        requestorStatementDetail.DateRange = McK.EIG.ROI.Client.Requestors.Model.DateRange.DAYS_60;
                    }
                    else if (dateRangecomboBox.SelectedValue.ToString().Equals("DAYS_90"))
                    {
                        requestorStatementDetail.DateRange = McK.EIG.ROI.Client.Requestors.Model.DateRange.DAYS_90;
                    }
                    else if (dateRangecomboBox.SelectedValue.ToString().Equals("DAYS_120"))
                    {
                        requestorStatementDetail.DateRange = McK.EIG.ROI.Client.Requestors.Model.DateRange.DAYS_120;
                    }
                    else if (dateRangecomboBox.SelectedValue.ToString().Equals("YEAR_TO_DATE"))
                    {
                        requestorStatementDetail.DateRange = McK.EIG.ROI.Client.Requestors.Model.DateRange.YEAR_TO_DATE;
                    }
                    if (requestorLetterNotes != null)
                    {
                        requestorStatementDetail.Notes = requestorLetterNotes.ToArray();
                    }
                }
                if (pastInvoiceCheckBox.Checked)
                {
                    requestorStatementDetail.PastInvIds = RetrieveAllSelectedPastInvoices(InvoiceIdColumn).ToArray();
                }
                requestorStatementDetail.RequestorId = this.requestor.Id;

                documentInfoList = RequestorController.Instance.GenerateRequestorStatement(requestorStatementDetail);
                foreach (DocumentInfo info in documentInfoList.DocumentInfoCollection)
                {
                    docId = info.Id;
                }
                DialogResult result = DialogResult.None;
                string letterType = LetterType.RequestorStatement.ToString();
                result = ShowViewer(documentInfoList.Name, letterType);
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
                        requestorStatementDetail.OutputMethod = OutputMethod.SaveAsFile.ToString();
                        requestorStatementDetail.QueueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                        destinationType = DestinationType.File;
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        requestorStatementDetail.OutputMethod = OutputMethod.Fax.ToString();
                        destinationType = DestinationType.Fax;
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Print.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        requestorStatementDetail.OutputMethod = OutputMethod.Print.ToString();
                        destinationType = DestinationType.Print;
                    }

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Email.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        requestorStatementDetail.OutputMethod = OutputMethod.Email.ToString();
                        requestorStatementDetail.QueueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                        destinationType = DestinationType.Email;
                    }

                    if (statementCheckBox.Checked)
                    {
                        docId = RequestorController.Instance.CreateRequestorStatement(requestorStatementDetail);
                    }
                    DocumentInfoList documentInfoLists = new DocumentInfoList();
                    DocumentInfo docInfo = new DocumentInfo();
                    docInfo.Id = docId;
                    docInfo.Name = ROIConstants.RequestorLetter;
                    docInfo.Type = ROIConstants.RequestorLetter;
                    documentInfoLists.DocumentInfoCollection.Add(docInfo);
                    Hashtable requestParts = new Hashtable();
                    RequestPartDetails requestPartDetails = new RequestPartDetails();
                    requestPartDetails.ContentId = docInfo.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;
                    PropertyDetails propertyDetails = new PropertyDetails();
                    propertyDetails.RequestorGrouping = requestor.Id + "-" + requestor.TypeName + "-" + requestor.Name;
                    propertyDetails.FileIds = docInfo.Type + "." + docInfo.Id + ",";

                    if ( requestorStatementDetail.PastInvIds != null )
                    {
                        if (requestorStatementDetail.PastInvIds.Length > 0)
                        {
                            foreach (long pastId in requestorStatementDetail.PastInvIds)
                            {
                                propertyDetails.FileIds += "Invoice." + pastId + ",";
                            }
                        }
                    }
                    requestPartDetails.PropertyLists.Add(propertyDetails);
                    outputRequestDetails.RequestParts.Add(requestPartDetails);
                    outputRequestDetails.IsGroupingEnabled = statementCheckBox.Checked && statementComboBox.SelectedIndex > 0;
                    Application.DoEvents();
                    OutputController.Instance.SubmitOutputRequest(outputRequestDetails, destinationType,
                                                                              outputViewDetails, false, false);

                }
                if (result == DialogResult.Cancel)
                {
                    ((Form)(this.Parent)).Visible = true;
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

        private void statementComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
            if (statementComboBox.SelectedIndex > 0)
            {
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)statementComboBox.SelectedItem;
                if (letterTemplate.HasNotes)
                {
                    PopulateNotes(LetterType.CoverLetter);
                    statementNotesGroupPanel.Visible = true;
                }
                else
                {
                    statementNotesGroupPanel.Visible = false;
                }
            }
            else
            {
                statementNotesGroupPanel.Visible = false;
            }
        }

        private void dateRangecomboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
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

        #endregion

    }
}
