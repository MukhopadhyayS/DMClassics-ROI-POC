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
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Web_References.BillingCoreWS;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    public partial class ReleaseDialog : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ReleaseDialog));
        
        private const string CheckBoxColumn = "checkBoxColumn";
        private const string RegeneratedInvoiceIdColumn = "regeneratedInvoiceId";
        private const string InvoiceIdColumn = "invoiceId";
        private const string CreatedDateColumn = "createdDate";
        private const string AmountColumn = "amount";

        private HeaderUI header;
        private Collection<NotesDetails> configureNotes;
        private ROIProgressBar fileTransferProgress;
        private PreBillInvoiceDetails preBillInvoiceDetails;
        private OutputPropertyDetails outputPropertyDetails;
        private OutputPropertyDetails outputPropertyDetailsForDisc;
        private DestinationType destinationType;
        private InvoiceDueDetails invoiceDueDetails;

        private string requestorFax;
        private DocumentInfo documentInfo;
        private DocumentInfoList documentInfoList;        
        private ReleaseAndPreviewInfo releaseAndPreviewInfo;
        private bool isCancelClicked;
        private int maxLength;
        private static long requestId;
        private bool isBillingLocationSelected;
        private string requestorEmail;        

        private int dueDays;
        private string salesTaxPercentage;

        private int totalPagesForRelease;
        private EventType eventType;

        internal static EventHandler ProgressHandler;
        private EventHandler InvoiceDueDateTextHandler;
        private EventHandler InvoiceDueDateLeaveHandler;
        private EIGCheckedColumnHeader dgvColumnHeader;

		//CR#359276 - Add automatic adjustment transaction for the current invoice
        private double requestInvoiceAutoAdjustment;

		//CR#367826 -  Enhance the PreBill feature to behave like the Invoice except aging
        private bool hasPrebilledRequest;
        private double prebillBalanceDue;        

        private ReleaseDetails releaseDetails;
        private String defaultFacilityCode;
        private String defaultFacilityName;

        private long invoiceId;
        private long letterId;
        private long releaseId;
        private bool forInvoice;
        private bool forLetter;
        private bool forRelease;

        private string queueSecure;
        private string outputMethod;
        private long requestorId;
        private long docId;
        private double unappliedPayment;
        private double unappliedAdjustment;
        private bool isApplied;
        private double appliedAmount;
        private bool isUnbillable=false;
        private BillingPaymentInfoUI billingInfoUI;
        private double totalUnAppliedAdjustmentPayment;
        private List<string> invoiceNotes;
        private List<string> coverLetterNotes;
        private string coverLetterName;
        public bool invoiceflag = RequestorTypeDetails.invoiceOptionalFlag;
        private long defaultInvoiceLetterTemplateId;
        private string defaultInvoiceLetterTemplateName;
        #endregion

        #region Constructor

        private ReleaseDialog()
        {
            InitializeComponent();
            header = InitHeaderUI();
            InitGrid();
        }

        /// <summary>
        /// Sets Pane and Execution Context
        /// </summary>
        /// <param name="pane"></param>
        public ReleaseDialog(IPane pane,BillingPaymentInfoUI billingUI)
            : this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);
            Localize();
            InvoiceDueDateTextHandler = new EventHandler(Process_InvoiceDueDateTextHandler);
            InvoiceDueDateLeaveHandler = new EventHandler(Process_InvoiceDueDateLeaveHandler);
            EnablePastDueGridEvents();
            billingInfoUI = billingUI;
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
            invoiceDueTextBox.TextChanged += InvoiceDueDateTextHandler;
            invoiceDueTextBox.Leave += InvoiceDueDateLeaveHandler;            
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
            invoiceDueTextBox.TextChanged -= InvoiceDueDateTextHandler;
            invoiceDueTextBox.Leave -= InvoiceDueDateLeaveHandler;            
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
        /// occurs when the user changes the custom due days text        
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_InvoiceDueDateTextHandler(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(invoiceDueTextBox.Text.Trim()) || invoiceComboBox.SelectedIndex == 0)
            {
                continueButton.Enabled = false;
            }
            else
            {
                continueButton.Enabled = true;
            }
        }

        /// <summary>
        /// occurs when user leaves the invoice due text field.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_InvoiceDueDateLeaveHandler(object sender, EventArgs e)
        {
            if (invoiceDueTextBox.Text.Trim().Length == 0)
            {
                invoiceDueTextBox.Text = invoiceDueDetails.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
        }

        /// <summary>
        /// Event occurs when the custom due days TextBox text is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceDueDateTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            int KeyCode = (int)e.KeyChar;
            if (KeyCode == 27)
            {
                invoiceDueTextBox.Text = invoiceDueDetails.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            if (!(KeyCode >= 48 && KeyCode <= 57) && KeyCode != 8)
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// Prepopulates the letter templates.
        /// </summary>
        public void PrePopulate(IList<LetterTemplateDetails> coverLetterTemplates,
                                IList<LetterTemplateDetails> requestorLetterTemplates,
                                IList<LetterTemplateDetails> invoiceTemplates,
                                long defaultCoverLetterId,
                                long defaultRequestorLetterId,
                                long defaultInvoiceId,
                                int totalPages,
                                PreBillInvoiceDetails preBillInvoiceDetails,
                                DestinationType destinationType,
                                RequestDetails requestDetails, 
                                EventType eventType, bool isBillingLocationSelected,
                                ReleaseDetails releaseDetails, String defaultFacilityCode, String defaultFacilityName)
        {
           
            LetterTemplateDetails forSelect = new LetterTemplateDetails();
            if (!invoiceflag)
            {
                forSelect.Id = 0;
                forSelect.Name = GetLocalizedString("noneLetterTemplateText");
                coverLetterTemplates.Insert(0, forSelect);
                invoiceTemplates.Insert(0, forSelect);
                requestorLetterTemplates.Insert(0, forSelect);                
            }
            else
            {
                forSelect.Id = 0;
                forSelect.Name = GetLocalizedString("letterTemplateText");
                coverLetterTemplates.Insert(0, forSelect);
                invoiceTemplates.Insert(0, forSelect);
                requestorLetterTemplates.Insert(0, forSelect);
            }

                this.totalPagesForRelease = totalPages;
                this.eventType = eventType;

                this.releaseDetails = releaseDetails;
               
                if (coverLetterTemplates.Count > 1)
                {
                    coverLetterComboBox.DataSource = coverLetterTemplates;
                    coverLetterComboBox.DisplayMember = "Name";
                    coverLetterComboBox.ValueMember = "DocumentId";
                    coverLetterComboBox.SelectedValue = defaultCoverLetterId;
                }
                else
                {
                    coverLetterMessageLabel.Visible = true;
                    coverLetterPanel.Visible = false;
                }

                if (invoiceTemplates.Count > 1)
                {
                    invoiceComboBox.DataSource = invoiceTemplates;
                    invoiceComboBox.DisplayMember = "Name";
                    invoiceComboBox.ValueMember = "DocumentId";
                    invoiceComboBox.SelectedValue = defaultInvoiceId;
                    defaultInvoiceLetterTemplateId = invoiceTemplates[1].DocumentId;
                    defaultInvoiceLetterTemplateName = invoiceTemplates[1].Name;
                }
                else
                {
                    invoiceMessageLabel.Visible = true;
                    invoicePanel.Visible = false;
                }

                if (requestorLetterTemplates.Count > 1)
                {
                    statementComboBox.DataSource = requestorLetterTemplates;
                    statementComboBox.DisplayMember = "Name";
                    statementComboBox.ValueMember = "DocumentId";
                    statementComboBox.SelectedValue = defaultRequestorLetterId;
                }
                else
                {
                    statementMessageLabel.Visible = true;
                    statementPanel.Visible = false;
                }

                if (totalPages > 1)
                {
                    header.Information = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                   GetLocalizedString(GetType().Name + ".header.info"),
                                                   totalPages);
                }
                else
                {
                    header.Information = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                 GetLocalizedString(GetType().Name + ".header.info.single"),
                                                 totalPages);
                }

                coverLetterComboBox.Enabled = false;
                invoiceComboBox.Enabled = false;
                invoiceDueTextBox.Enabled = false;
                statementComboBox.Enabled = false;
                dateRangeComboBox.Enabled = false;

                coverLetterNotesGroupPanel.Enabled = false;
                invoiceNotesGroupPanel.Enabled = false;

                if (requestDetails.Status == Admin.Model.RequestStatus.Completed)
                {
                    yesRadioButton.Enabled = false;
                    noRadioButton.Enabled = false;
                    yesRadioButton.Checked = false;
                }

                this.preBillInvoiceDetails = preBillInvoiceDetails;
                this.requestorFax = requestDetails.RequestorFax;
                this.requestorEmail = requestDetails.Requestor.Email;
                this.destinationType = destinationType;
                requestId = requestDetails.Id;
                this.requestorId = requestDetails.Requestor.Id;
                salesTaxPercentage = requestDetails.TaxPercentage.ToString("n2", System.Threading.Thread.CurrentThread.CurrentUICulture);
                PopulateInvoiceDueDays();
                PopulatePastInvoices();
                //CR#359333. Invoice checkbox is checked by default when invoice is enabled
                //invoiceCheckBox.Enabled = invoiceCheckBox.Checked = enableInvoice || hasPrebilledRequest;            
                this.isBillingLocationSelected = isBillingLocationSelected;
                //CR#359276 - Add automatic adjustment transaction for the current invoice
                this.requestInvoiceAutoAdjustment = requestDetails.InvoiceAutoAdjustment;
                //CR#367826 -  Enhance the PreBill feature to behave like the Invoice except aging
                this.hasPrebilledRequest = hasPrebilledRequest;
                this.prebillBalanceDue = prebillBalanceDue;
                this.defaultFacilityCode = defaultFacilityCode;
                this.defaultFacilityName = defaultFacilityName;
                this.invoiceCheckBox.Checked = true;
                this.invoiceCheckBox.Enabled = false;
                PopulateDateRange();
                if (requestDetails.Status == Admin.Model.RequestStatus.PreBilled)
                {
                    this.invoiceCheckBox.Visible = false;
                    this.InvoiceCheckbox2.Visible = true;
                    this.InvoiceCheckbox2.Checked = true;
                    this.InvoiceCheckbox2.Enabled = false;
                }
                else
                {
                    this.invoiceCheckBox.Visible = true;
                    this.InvoiceCheckbox2.Visible = false;
                    this.invoiceCheckBox.Enabled = false;
                    this.invoiceCheckBox.Enabled = false;
                }
            
        }

        ///<summary>
        ///This mehod is used to populate the configurable days of duedate dropdown field
        ///</summary>
        public void PopulateInvoiceDueDays()
        {
            DisableEvents();
            invoiceDueDetails = ROIAdminController.Instance.RetrieveInvoiceDueDays();
            invoiceDueTextBox.Text = invoiceDueDetails.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
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
        /// Populate past invoices
        /// </summary>
        public void PopulatePastInvoices()
        {
            Collection<PastInvoiceDetails> pastInvoices = BillingController.Instance.RetrievePastInvoiceDetails(requestId);
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

            FlowLayoutPanel notesPanel;

            if (freeformUI.Parent.Equals(coverLetterNotesPanel))
            {
                notesPanel = coverLetterNotesPanel;
            }
            //CR#377224
            else if (freeformUI.Parent.Equals(statementNotesPanel))
            {
                notesPanel = statementNotesPanel;
            }
            else
            {
                notesPanel = invoiceNotesPanel;
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
            invoiceCreateFreeformButton.Text = rm.GetString("createFreeformButton.ReleaseDialog");

            SetLabel(rm, headerLabel);
            SetLabel(rm, statusLabel);
            SetLabel(rm, yesRadioButton);
            SetLabel(rm, noRadioButton);
            SetLabel(rm, addLetterGroupBox);
            SetLabel(rm, coverLetterGroupBox);
            SetLabel(rm, invoiceGroupBox);
            SetLabel(rm, coverLetterMessageLabel);
            SetLabel(rm, invoiceMessageLabel);
            SetLabel(rm, coverLetterCheckBox);
            SetLabel(rm, InvoiceCheckbox2);
            SetLabel(rm, invoiceCheckBox);
            SetLabel(rm, coverLetterLabel);
            SetLabel(rm, invoiceLabel);
            SetLabel(rm, dueDateLabel);
            SetLabel(rm, daysLabel);

            SetLabel(rm, coverLetterHeaderLabel);
            SetLabel(rm, invoiceHeaderLabel);
            SetLabel(rm, createFreeformButton);
            SetLabel(rm, invoiceHistoryGroupBox);
            SetLabel(rm, pastInvoiceCheckBox);           

            SetLabel(rm, requiredLabel);
            SetLabel(rm, continueButton);
            SetLabel(rm, cancelButton);

            SetLabel(rm, statementGroupBox);
            SetLabel(rm, statementCheckBox);
            SetLabel(rm, statementLabel);
            SetLabel(rm, statementHeaderLabel);
            SetLabel(rm, dateRangeLabel);
            SetLabel(rm, statementCreateFreeFormButton);
            SetLabel(rm, statementMessageLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, continueButton);
            SetTooltip(rm, toolTip, cancelButton);
            SetTooltip(rm, toolTip, createFreeformButton);
            SetTooltip(rm, toolTip, invoiceCreateFreeformButton);

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
            if ((control == requiredLabel) || (control == yesRadioButton) || (control == noRadioButton))
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
        /// Occurs when user changes the cover letter template.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void coverLetterComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
            if (coverLetterComboBox.SelectedIndex > 0)
            {
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)coverLetterComboBox.SelectedItem;
                if (letterTemplate.HasNotes)
                {
                    PopulateNotes(LetterType.CoverLetter);
                    coverLetterNotesGroupPanel.Visible = true;
                }
                else
                {
                    coverLetterNotesGroupPanel.Visible = false;
                }
            }
            else
            {
                coverLetterNotesGroupPanel.Visible = false;
            }
        }

        /// <summary>
        /// Occurs when user changes the invoice template.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
            if (invoiceComboBox.SelectedIndex > 0)
            {
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)invoiceComboBox.SelectedItem;
                if (letterTemplate.HasNotes)
                {
                    PopulateNotes(LetterType.Invoice);
                    invoiceNotesGroupPanel.Visible = true;
                }
                else
                {
                    invoiceNotesGroupPanel.Visible = false;
                }
            }
            else
            {
                invoiceNotesGroupPanel.Visible = false;
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
                    PopulateNotes(LetterType.RequestorStatement);
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


        private void dateRangeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
        }

        /// <summary>
        /// Occurs when user clicks on coverletter checkbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void coverLetterCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
            if (coverLetterCheckBox.Checked)
            {
                coverLetterComboBox.Enabled = true;
                coverLetterNotesGroupPanel.Enabled = true;
            }
            else
            {
                coverLetterComboBox.Enabled = false;
                coverLetterNotesGroupPanel.Enabled = false;
            }
        }

        /// <summary>
        /// Occurs when user click on invoice checkbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
            EnableEvents();
            if (invoiceCheckBox.Checked)
            {
                invoiceComboBox.Enabled = true;
                invoiceNotesGroupPanel.Enabled = true;
                invoiceDueTextBox.Enabled = true;
            }
            else
            {
                invoiceComboBox.Enabled = false;
                invoiceNotesGroupPanel.Enabled = false;
                invoiceDueTextBox.Enabled = false;
            }
        }

        private void statementCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            EnableContinueButton();
            if (statementCheckBox.Checked)
            {
                statementComboBox.Enabled = true;
                statementNotesGroupPanel.Enabled = true;
                dateRangeComboBox.Enabled = true;
                // CR#377222
                statementCreateFreeFormButton.Enabled = true;
            }
            else
            {
                statementComboBox.Enabled = false;
                statementNotesGroupPanel.Enabled = false;
                dateRangeComboBox.Enabled = false;
                // CR#377222
                statementCreateFreeFormButton.Enabled = false;
            }
        }

        /// <summary>
        /// Enables the continue button.
        /// </summary>
        private void EnableContinueButton()
        {
            continueButton.Enabled = true;
            string selected = this.invoiceComboBox.GetItemText(this.invoiceComboBox.SelectedItem);
            
            if (selected == "None")
                pastInvoiceCheckBox.Enabled = false;

            if (coverLetterCheckBox.Checked)
            {
                continueButton.Enabled = coverLetterComboBox.SelectedIndex > 0;
                if (!continueButton.Enabled) return;
            }

            if (statementCheckBox.Checked)
            {
                continueButton.Enabled = statementComboBox.SelectedIndex > 0 && dateRangeComboBox.SelectedIndex > 0 ;
                if (!continueButton.Enabled) return;
            }

            if (invoiceCheckBox.Checked)
            {
                if (invoiceComboBox.SelectedIndex > 0|| selected == "None")
                {
                    continueButton.Enabled = (!string.IsNullOrEmpty(invoiceDueTextBox.Text.Trim()));
                    if (!continueButton.Enabled) return;
                }
                else
                {
                    continueButton.Enabled = false;
                    return;
                }
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
                else if (letterType == LetterType.RequestorStatement)
                {
                    notesPanel = statementNotesPanel;
                }
                else
                {
                    notesPanel = invoiceNotesPanel;
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
            coverLetterNotesPanel.HorizontalScroll.Value = 0;
        }

        /// <summary>
        /// Occurs when user clicks on Invoice Create Freeform Note.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceCreateFreeformButton_Click(object sender, EventArgs e)
        {
            CreateFreeform(LetterType.Invoice.ToString());
            invoiceNotesPanel.HorizontalScroll.Value = 0;
        }

        private void statementCreateFreeFormButton_Click(object sender, EventArgs e)
        {
            CreateFreeform(LetterType.RequestorStatement.ToString());
            statementNotesPanel.HorizontalScroll.Value = 0;
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
                notesPanel = coverLetterNotesPanel;
            }
            else if (letterType == LetterType.RequestorStatement.ToString())
            {
                notesPanel = statementNotesPanel;
            }
            else
            {
                notesPanel = invoiceNotesPanel;
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
                case ROIErrorCodes.InvoiceNotSelected: return invoiceCheckBox;
                case ROIErrorCodes.BillingLocationNotSelectedRelease: return invoiceCheckBox;
            }
            return null;
        }

        /// <summary>
        /// Occurs when user clicks on continue button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void continueButton_Click(object sender, EventArgs e)
        {
            errorProvider.Clear();
            bool isError = false;
            try
            {                
                 ROIViewUtility.MarkBusy(true);
                //if invoice check is not checked
                if (invoiceCheckBox.Enabled && !invoiceCheckBox.Checked)
                {
                    throw new ROIException(ROIErrorCodes.InvoiceNotSelected);
                }
                else if (invoiceCheckBox.Enabled && !isBillingLocationSelected) //if billing location is not selected
                {
                    throw new ROIException(ROIErrorCodes.BillingLocationNotSelectedRelease);
                }
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                isError = true;
            }           

            
            documentInfo = null;
            documentInfoList = null;
            List<string> invoiceNotes = new List<string>();
            List<string> coverLetterNotes = new List<string>();
            List<string> statementLetterNotes = new List<string>();
			//CR#359259
            long coverLetterTemplateId = 0;
            long invoiceLetterTemplateId = 0;
            long statementLetterTemplateId = 0;
            string coverLetterTemplateName = string.Empty;
            string invoiceLetterTemplateName = string.Empty;
            string statementLetterTemplateName = string.Empty;

            if (coverLetterCheckBox.Checked)
            {
                AddNotes(coverLetterNotesPanel, coverLetterNotes);
                this.coverLetterNotes = coverLetterNotes;
                coverLetterTemplateId = (long)coverLetterComboBox.SelectedValue;
                coverLetterTemplateName = ((LetterTemplateDetails)coverLetterComboBox.SelectedItem).Name;
                coverLetterName = coverLetterTemplateName;
            }

            if (invoiceCheckBox.Checked)
            {
                AddNotes(invoiceNotesPanel, invoiceNotes);
                if (((LetterTemplateDetails)invoiceComboBox.SelectedItem).Name == "None")
                {
                    invoiceLetterTemplateId = defaultInvoiceLetterTemplateId;
                    invoiceLetterTemplateName = defaultInvoiceLetterTemplateName;
                }
                else {
                    invoiceLetterTemplateId = (long)invoiceComboBox.SelectedValue;
                    invoiceLetterTemplateName = ((LetterTemplateDetails)invoiceComboBox.SelectedItem).Name;
                }
                    if (eventType == EventType.DocumentsReleased)
                {
                    preBillInvoiceDetails.Release.TotalPagesReleased += totalPagesForRelease;
                }
            }

            if (isError)
            {
                ((Form)(this.Parent)).DialogResult = DialogResult.None;                
            }

            if (((Form)(this.Parent)).DialogResult == DialogResult.OK)
            {
                 DialogResult result = DialogResult.None;
                 
                 preBillInvoiceDetails.Release.ReleaseDate = DateTime.Now;

                 if(coverLetterCheckBox.Checked)
                 {   
                     preBillInvoiceDetails.IsLetter = true;
                 }
                 InvoiceOrPrebillPreviewInfo InvoiceInfo = null;
                 if(invoiceCheckBox.Checked)
                 {
                     //Added invoiceduedate and isoverwrite status in preBillInvoiceDetails object
                     preBillInvoiceDetails.InvoiceDueDate = DateTime.Now.AddDays(DueDays);
                     preBillInvoiceDetails.OverwriteInvoiceDue = IsOverWriteDueDays;
                     preBillInvoiceDetails.IsInvoice = true;
                     preBillInvoiceDetails.SalesTaxPercentage = salesTaxPercentage;

 					 //CR#359276 - Add automatic adjustment transaction for the current invoice
                     preBillInvoiceDetails.Release.RequestTransactions.Clear();
                     if (requestInvoiceAutoAdjustment > 0)
                     {
                         RequestTransaction txn = new RequestTransaction();
                         txn.CreatedDate = DateTime.Now;
                         txn.ReasonName = string.Empty;
                         txn.Description = ROIConstants.AutoCreditAdjustmentText;
                         txn.TransactionType = TransactionType.AutoAdjustment;
                         txn.IsDebit = false;
                         txn.Amount = Math.Round(-requestInvoiceAutoAdjustment, 2);
                         txn.AdjustmentPaymentType = AdjustmentPaymentType.Automatic;
                         txn.IsNewlyAdded = true;
                         preBillInvoiceDetails.Release.RequestTransactions.Add(txn);
                     }
					 //CR#367826 - Automatic debit adjustment for the newly created invoice
                     RequestTransaction newInvoiceTransaction = null;
                     if (prebillBalanceDue > 0)
                     {
                         newInvoiceTransaction = new RequestTransaction();
                         newInvoiceTransaction.CreatedDate = DateTime.Now;
                         newInvoiceTransaction.ReasonName = string.Empty;
                         newInvoiceTransaction.Description = ROIConstants.AutoDebitAdjustmentText;
                         newInvoiceTransaction.TransactionType = TransactionType.AutoAdjustment;
                         newInvoiceTransaction.IsDebit = true;
                         newInvoiceTransaction.Amount = Math.Round(prebillBalanceDue, 2);
                         newInvoiceTransaction.AdjustmentPaymentType = AdjustmentPaymentType.Automatic;
                         newInvoiceTransaction.IsNewlyAdded = true;
                         preBillInvoiceDetails.Release.RequestTransactions.Add(newInvoiceTransaction);
                         preBillInvoiceDetails.Release.DebitAdjustmentTotal = prebillBalanceDue;
                     }

                     //preBillInvoiceDetails.Release.CreditAdjustmentTotal = -requestInvoiceAutoAdjustment;
                     //preBillInvoiceDetails.Release.PaymentTotal = 0;
                     //preBillInvoiceDetails.Release.AdjustmentTotal = preBillInvoiceDetails.Release.CreditAdjustmentTotal +
                     //                                                preBillInvoiceDetails.Release.DebitAdjustmentTotal;

                     //preBillInvoiceDetails.Release.AdjustmentPaymentTotal = preBillInvoiceDetails.Release.AdjustmentTotal +
                     //                                                       preBillInvoiceDetails.Release.PaymentTotal;
                   
                     //Filling the parameters for Invoice flow
                     InvoiceInfo = new InvoiceOrPrebillPreviewInfo();
                     InvoiceInfo.Amountpaid = Math.Round(-requestInvoiceAutoAdjustment, 2);

                     InvoiceInfo.BaseCharge = releaseDetails.TotalRequestCost;                       
                     InvoiceInfo.InvoiceBillingLocCode = defaultFacilityCode;
                     InvoiceInfo.InvoiceBillinglocName = defaultFacilityName;
                     InvoiceInfo.InvoiceDueDays = DueDays;
                     
                     InvoiceInfo.InvoicePrebillDate = DateTime.Now;
                     InvoiceInfo.InvoiceSalesTax = preBillInvoiceDetails.Release.SalesTaxTotalAmount;
                     InvoiceInfo.LetterTemplateFileId = invoiceLetterTemplateId;
                     InvoiceInfo.LetterTemplateName = invoiceLetterTemplateName;
                     int i = 0;
                     InvoiceInfo.Notes = new string[invoiceNotes.Count];
                     foreach (string note in invoiceNotes)
                     {
                         InvoiceInfo.Notes[i] = invoiceNotes[i];
                         i++;
                     }
                     InvoiceInfo.OutputMethod = releaseDetails.ShippingDetails.OutputMethod.ToString();
                     InvoiceInfo.OverwriteDueDate = preBillInvoiceDetails.OverwriteInvoiceDue;
                     InvoiceInfo.QueueSecretWord = preBillInvoiceDetails.QueueSecretWord;
                     InvoiceInfo.RequestCoreId = releaseDetails.RequestId;
                     InvoiceInfo.RequestStatus = preBillInvoiceDetails.RequestStatus;

                     if (((LetterTemplateDetails)invoiceComboBox.SelectedItem).Name == "None")
                        InvoiceInfo.WillInvoiceShipped = false;
                     else
                        InvoiceInfo.WillInvoiceShipped = true;

                    //US16834 - changes to Include requests in the pre-bill status on the payments popup.
                    if (InvoiceInfo.RequestStatus == "Pre-Billed")
                     {
                        InvoiceInfo.InvoiceBalanceDue = Convert.ToDouble(billingInfoUI.balanceDueValueLabel.Text.Trim().Substring(1, billingInfoUI.balanceDueValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                        
                        //InvoiceInfo.InvoiceBalanceDue = preBillInvoiceDetails.Release.BalanceDue;
                    }
                     else
                     {
                         InvoiceInfo.InvoiceBalanceDue = preBillInvoiceDetails.Release.BalanceDue;
                     } 
                     InvoiceInfo.RequestDate = DateTime.Now;
                     InvoiceInfo.InvoiceDueDate = DateTime.Now;
                     InvoiceInfo.ResendDate = DateTime.Now;
                     InvoiceInfo.RequestTransaction = newInvoiceTransaction;
                     InvoiceInfo.Type = "DOCX";
                     InvoiceInfo.LetterType = LetterType.Invoice.ToString();
                     if (preBillInvoiceDetails.Release.RequestTransactions.Count > 0)
                     {
                         InvoiceInfo.RequestTransaction = new RequestTransaction();
                         InvoiceInfo.RequestTransaction.CreatedDate = preBillInvoiceDetails.Release.RequestTransactions[0].CreatedDate;
                         InvoiceInfo.RequestTransaction.ReasonName = preBillInvoiceDetails.Release.RequestTransactions[0].ReasonName;
                         InvoiceInfo.RequestTransaction.Description = preBillInvoiceDetails.Release.RequestTransactions[0].Description;
                         InvoiceInfo.RequestTransaction.TransactionType = preBillInvoiceDetails.Release.RequestTransactions[0].TransactionType;
                         InvoiceInfo.RequestTransaction.IsDebit = preBillInvoiceDetails.Release.RequestTransactions[0].IsDebit;
                         InvoiceInfo.RequestTransaction.Amount = preBillInvoiceDetails.Release.RequestTransactions[0].Amount;
                         InvoiceInfo.RequestTransaction.AdjustmentPaymentType = preBillInvoiceDetails.Release.RequestTransactions[0].AdjustmentPaymentType;
                     }
                 }
                 if(pastInvoiceCheckBox.Checked)
                 {
                     // CR# 375,961 - removed regenerated invoice, instead original invoice should be shown.
                     preBillInvoiceDetails.PastInvoiceIds = RetrieveAllSelectedPastInvoices(InvoiceIdColumn);
                     if (preBillInvoiceDetails.PastInvoiceIds.Count > 0)
                     {
                         preBillInvoiceDetails.IsPastInvoice = true;
                     }
                 }

                ReleaseCore releaseCore = new ReleaseCore();
                releaseCore.requestId = releaseDetails.RequestId;
                releaseCore.coverLetterFileId = coverLetterTemplateId;
                releaseCore.coverLetterRequired = coverLetterCheckBox.Checked;
                releaseCore.invoiceDueDays = DueDays;
                releaseCore.invoiceRequired = invoiceCheckBox.Checked;
                releaseCore.invoiceTemplateId = invoiceLetterTemplateId;
                releaseCore.notes = invoiceNotes.ToArray();
                if (coverLetterCheckBox.Checked)
                {
                    if (coverLetterNotes.Count > 0)
                    {
                        releaseCore.coverLetterNotes = coverLetterNotes.ToArray();
                    }
                }

                int pageCount = 0;
                releaseCore.roiPages = new ROIPages[releaseDetails.ROIPages.Count];
                ROIPages serverRoipages;
                foreach (ROIPage clientRoiPage in releaseDetails.ROIPages)
                {
                    serverRoipages = new ROIPages();
                    serverRoipages.pageSeq = clientRoiPage.PageSequence;
                    serverRoipages.selfPayEncounter = clientRoiPage.IsSelfEncounter;
                    releaseCore.roiPages[pageCount] = serverRoipages;
                    pageCount++;
                }
                
                releaseCore.supplementalDocumentsSeq = releaseDetails.SupplementalDocumentsSeqField.ConvertAll<long?>(x => x).ToArray();
                releaseCore.supplementalAttachmentsSeq = releaseDetails.SupplementalAttachmentsSeqField.ConvertAll<long?>(x => x).ToArray();
                releaseCore.supplementarityDocumentsSeq = releaseDetails.SupplementarityDocumentsSeqField.ConvertAll<long?>(x => x).ToArray();
                releaseCore.supplementarityAttachmentsSeq = releaseDetails.SupplementarityAttachmentsSeqField.ConvertAll<long?>(x => x).ToArray();

                releaseCore.pastDueInvoices = preBillInvoiceDetails.PastInvoiceIds.ConvertAll<long?>(x => x).ToArray();



                isApplied = false;
                AppliedAmount = 0;
                unappliedPayment = 0;
                unappliedAdjustment = 0;
                List<RequestorUnappliedAmountDetail> reqAmtList 
                    = new List<RequestorUnappliedAmountDetail>();
                reqAmtList = RequestorController.Instance.RetrieveUnappliedAmountDetails(requestId);
                foreach (RequestorUnappliedAmountDetail req in reqAmtList)
                {
                    if (req.Type == "Unapplied Payment")
                        unappliedPayment = req.Amount;
                    else if (req.Type == "Unapplied Adjustment")
                        unappliedAdjustment = req.Amount;
                }
                //US16834 - changes to Include requests in the pre-bill status on the payments popup.
                
                bool launchUnappliedPayAdj = false;

                if (((unappliedAdjustment != 0) || (unappliedPayment != 0)) && (InvoiceInfo.InvoiceBalanceDue != 0))
                {
                        totalUnAppliedAdjustmentPayment = unappliedAdjustment + unappliedPayment;
                        if (IsAllowed(ROISecurityRights.ROIPostPayment))
                        {
                            launchUnappliedPayAdj = true;
                            UnappliedAdjustmentUI unappliedPaymentUI = new UnappliedAdjustmentUI(Pane);
                            unappliedPaymentUI.Invoiceinfo = InvoiceInfo;
                            Form form = ROIViewUtility.ConvertToForm(null, "Unapplied Adjustments / Payments", unappliedPaymentUI);
                            unappliedPaymentUI.Setdata(unappliedPayment, unappliedAdjustment);                          
                        
                            DialogResult results = form.ShowDialog(this);
                            //User clicked on cancel button.
                            if (results == DialogResult.Cancel)
                            {
                                isApplied = false;
                                form.Close();

                            }
                            else //User selected No radio button on dialog. So do not do changes to invoice.
                                if (results == DialogResult.Ignore)
                                {
                                    isApplied = true;
                                    form.Close();
                                }
                                else //User selected Yes radio button
                                    if (results == DialogResult.OK)
                                    {
                                        double invoiceBalance = InvoiceInfo.InvoiceBalanceDue;
                                        if (InvoiceInfo.InvoiceBalanceDue > (unappliedAdjustment + unappliedPayment))
                                        {
                                            appliedAmount = (unappliedAdjustment + unappliedPayment);
                                        }
                                        else
                                        {
                                            appliedAmount = InvoiceInfo.InvoiceBalanceDue;
                                            
                                        }
                                        isApplied = true;
                                        AppliedAmount = appliedAmount;
                                        totalUnAppliedAdjustmentPayment = unappliedAdjustment + unappliedPayment - invoiceBalance;
                                        if (totalUnAppliedAdjustmentPayment < 0)
                                        {
                                            totalUnAppliedAdjustmentPayment = 0.0;
                                        }

                                    }
                    }
                }

                else
                {
                    totalUnAppliedAdjustmentPayment = unappliedAdjustment + unappliedPayment;
                }

                //User tried to access the Payment/Adj dialog and clicked on cancel.
                if (isApplied == false && launchUnappliedPayAdj == true)
                {
                    ((Form)(this.Parent)).DialogResult = DialogResult.None; 
                    return;
                }

                if (InvoiceInfo != null)
                {
                    //CR#376948
                    if (!isApplied)
                    {
                        //InvoiceInfo.BaseCharge = this.releaseDetails.TotalRequestCost;
                    }
                    InvoiceOrPrebillAndPreviewInfo server = RequestController.MapModel(InvoiceInfo);
                    releaseCore.invoiceOrPrebillAndPreviewInfo = server;
                }
                if (statementCheckBox.Checked)
                {
                    releaseCore.statementCriteria = new RequestorStatementCriteria();
                    AddNotes(statementNotesPanel, statementLetterNotes);
                    releaseCore.statementCriteria.templateFileId = (long)statementComboBox.SelectedValue;
                    releaseCore.statementCriteria.templateName = ((LetterTemplateDetails)statementComboBox.SelectedItem).Name;
                    int count = 0;
                    releaseCore.statementCriteria.notes = new string[statementLetterNotes.Count];
                    foreach (string note in statementLetterNotes)
                    {
                        releaseCore.statementCriteria.notes[count] = statementLetterNotes[count];
                        count++;
                    }
                    if (dateRangeComboBox.SelectedValue.ToString().Equals("ALL"))
                    {
                        releaseCore.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.BillingCoreWS.DateRange.ALL);
                    }
                    else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_30"))
                    {
                        releaseCore.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.BillingCoreWS.DateRange.DAYS_30);
                    }
                    else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_60"))
                    {
                        releaseCore.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.BillingCoreWS.DateRange.DAYS_60);
                    }
                    else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_90"))
                    {
                        releaseCore.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.BillingCoreWS.DateRange.DAYS_90);
                    }
                    else if (dateRangeComboBox.SelectedValue.ToString().Equals("DAYS_120"))
                    {
                        releaseCore.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.BillingCoreWS.DateRange.DAYS_120);
                    }
                    else if (dateRangeComboBox.SelectedValue.ToString().Equals("YEAR_TO_DATE"))
                    {
                        releaseCore.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.BillingCoreWS.DateRange.YEAR_TO_DATE);
                    }
                    if(pastInvoiceCheckBox.Checked)
                    {
                        releaseCore.statementCriteria.pastInvIds = preBillInvoiceDetails.PastInvoiceIds.ToArray();
                    }
                    releaseCore.statementCriteria.requestorId = this.requestorId;
                }
                try
                {
                    RequestorCache.RemoveKey(this.requestorId);
                    releaseAndPreviewInfo = BillingController.Instance.CreateReleaseAndPreview(releaseCore, isApplied, AppliedAmount);

                    releaseId = releaseAndPreviewInfo.releaseId;
                    forRelease = true;

                    if ((coverLetterCheckBox.Checked) || (invoiceCheckBox.Checked) || (pastInvoiceCheckBox.Checked))
                    {
                        /*invoiceAndDocumentDetails = BillingController.Instance.CreateInvoiceAndLetter(preBillInvoiceDetails, coverLetterTemplateId,
                                                                        invoiceLetterTemplateId,
                                                                        coverLetterTemplateName,
                                                                        invoiceLetterTemplateName,
                                                                        coverLetterNotes, invoiceNotes);*/                      
                        if (((LetterTemplateDetails)invoiceComboBox.SelectedItem).Name != "None")                       
                            result = ShowViewer(releaseAndPreviewInfo.docInfoList.name);                        
                    }

                    if ((!coverLetterCheckBox.Checked) && (!invoiceCheckBox.Checked) && (!pastInvoiceCheckBox.Checked)||(result==DialogResult.None))
                    {
                        //if (destinationType == DestinationType.Print || destinationType == DestinationType.Fax)
                        //{
                        //    if (!IsAllowed(ROISecurityRights.ROIPrintFax))
                        //    {
                        //        HandleSecurityRights(ROIErrorCodes.PrintFaxAccessDenied);
                        //        return;
                        //    }
                        //}
                        //else
                        //{
                        //    if (!IsAllowed(ROISecurityRights.ROIExportToPDF))
                        //    {
                        //        HandleSecurityRights(ROIErrorCodes.ExportToPdfDenied);
                        //        return;
                        //    }
                        //    if (!IsAllowed(ROISecurityRights.ROIEmail))
                        //    {
                        //        HandleSecurityRights(ROIErrorCodes.ExportToEmailDenied);
                        //        return;
                        //    }

                        //}
                        result = DialogResult.OK;

                        ROIViewer viewer = new ROIViewer(Pane, string.Empty, GetType().Name);
                        viewer.ReleaseDialog = true;
                        viewer.RequestorFax = requestorFax;
                        viewer.RequestorEmail = requestorEmail;

                        if (destinationType == DestinationType.Print)
                        {
                            result = viewer.ShowPrintDialog(this);
                        }
                        else if (destinationType == DestinationType.Fax)
                        {
                            result = viewer.ShowFaxDialog(this);
                        }
                        else if (destinationType == DestinationType.File)
                        {
                            result = viewer.ShowFileDialog(this);
                        }
                        else if (destinationType == DestinationType.Email)
                        {
                            result = viewer.ShowEmailDialog(this);
                        }
                        else if (destinationType == DestinationType.Disc)
                        {
                            result = viewer.ShowDiscDialog(this);
                        }
                        outputPropertyDetails = viewer.OutputPropertyDetails;
                        outputPropertyDetailsForDisc = viewer.OutputPropertyDetailsForDisc;
                    }

                    if (result == DialogResult.Cancel)
                    {
                        if (invoiceCheckBox.Checked && eventType == EventType.DocumentsReleased)
                        {
                            preBillInvoiceDetails.Release.TotalPagesReleased -= totalPagesForRelease;
                        }
                        if (isCancelClicked)
                        {
                            ((Form)(this.Parent)).Visible = true;
                            ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        }
                        else
                        {
                            ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
                        }
                        if (releaseAndPreviewInfo != null && releaseAndPreviewInfo.docInfoList != null)
                        {
                            CancelRelease(releaseAndPreviewInfo);
                        }
                    }
                    else if (result == DialogResult.OK)
                    {
                        if (string.Compare(OutputPropertyDetails.OutputDestinationDetails[0].Type, DestinationType.File.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            outputMethod = OutputMethod.SaveAsFile.ToString();
                            queueSecure = OutputPropertyDetails.OutputDestinationDetails[0].SecuredSecretWord;
                        }

                        if (string.Compare(OutputPropertyDetails.OutputDestinationDetails[0].Type, DestinationType.Print.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            outputMethod = OutputMethod.Print.ToString();
                        }

                        if (string.Compare(OutputPropertyDetails.OutputDestinationDetails[0].Type, DestinationType.Email.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            outputMethod = OutputMethod.Email.ToString();
                            queueSecure = OutputPropertyDetails.OutputDestinationDetails[0].SecuredSecretWord;
                        }

                        if (string.Compare(OutputPropertyDetails.OutputDestinationDetails[0].Type, DestinationType.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            outputMethod = OutputMethod.Fax.ToString();
                        }

                        if (destinationType == DestinationType.Disc)
                        {
                            outputMethod = OutputMethod.Disc.ToString();
                            queueSecure = OutputPropertyDetailsForDisc.OutputDestinationDetails[0].SecuredSecretWord;
                        }
                        
                        this.invoiceNotes = invoiceNotes;
                        if (invoiceCheckBox.Checked)
                        {
                            //update invoice password
                            //retrieve invoice docid                        

                            //Events added for Invoice and Prebill







                        }

                        if (releaseAndPreviewInfo.docInfoList != null)
                        {
                            foreach (DocInfo docInfo in releaseAndPreviewInfo.docInfoList.docInfos)
                            {
                                if (ROIConstants.CoverLetterType.Equals(docInfo.type))
                                {
                                    letterId = docInfo.id;
                                    forLetter = true;
                                }

                                if (string.Compare(docInfo.type, LetterType.Invoice.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                                {
                                    invoiceId = docInfo.id;
                                    forInvoice = true;
                                }
                            }
                        }
                        BillingController.Instance.updateInvoiceOutputProperties(invoiceId, letterId, releaseId, forInvoice,
                                                                                 forLetter, forRelease, queueSecure, outputMethod);
                        if (statementCheckBox.Checked)
                        {
                            RequestorStatementDetail requestorStatementDetail = new RequestorStatementDetail();
                            requestorStatementDetail.DateRange = (McK.EIG.ROI.Client.Requestors.Model.DateRange)Enum.Parse(typeof(McK.EIG.ROI.Client.Requestors.Model.DateRange), releaseCore.statementCriteria.dateRange.ToString(), true);
                            requestorStatementDetail.Notes = releaseCore.statementCriteria.notes;
                            requestorStatementDetail.OutputMethod = outputMethod;
                            requestorStatementDetail.QueueSecretWord = queueSecure;
                            requestorStatementDetail.PastInvIds = releaseCore.statementCriteria.pastInvIds;
                            requestorStatementDetail.RequestorId = releaseCore.statementCriteria.requestorId;
                            requestorStatementDetail.TemplateFileId = releaseCore.statementCriteria.templateFileId;
                            requestorStatementDetail.TemplateName = releaseCore.statementCriteria.templateName;
                            this.docId = RequestorController.Instance.CreateRequestorStatement(requestorStatementDetail);
                        }
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
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                    ROIViewUtility.Handle(Context, cause);
                }

            }
        }       
      

        public void CreateInvoiceEvent()
        {
            ROIViewUtility.MarkBusy(true);
            CommentDetails details = new CommentDetails();

            details.RequestId = requestId;
            details.EventType = EventType.InvoiceSend;

            StringBuilder notes = new StringBuilder();
            foreach (string note in invoiceNotes)
            {
                notes.Append(note + ",");
            }

            if (string.Compare(OutputPropertyDetails.OutputDestinationDetails[0].Type, DestinationType.Email.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
            {
                preBillInvoiceDetails.OutputMethod = OutputMethod.Email.ToString();
                preBillInvoiceDetails.QueueSecretWord = OutputPropertyDetails.OutputDestinationDetails[0].SecuredSecretWord;
            }

            string outputName;
            if (string.Compare(destinationType.ToString(), OutputMethod.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
            {
                outputName = OutputPropertyDetails.OutputDestinationDetails[0].Fax;
            }
            else
            {
                outputName = OutputPropertyDetails.OutputDestinationDetails[0].Name;
            }

            string documentName = string.Empty;

            if ((coverLetterCheckBox.Checked) || (invoiceCheckBox.Checked) || (pastInvoiceCheckBox.Checked))
            {
                documentName = releaseAndPreviewInfo.docInfoList.name;
            }

            DestinationType tempDestinationType = (destinationType == DestinationType.Disc) ? 
                    tempDestinationType = DestinationType.Print : 
                    tempDestinationType = destinationType;

            if (!string.IsNullOrEmpty(notes.ToString()))
            {
                details.EventRemarks = documentName.Substring(0, documentName.LastIndexOf('.')) + ": " +
                                       notes.ToString().TrimEnd(',') + ", output method: " +
                                       tempDestinationType + ", " + outputName;
            }
            else
            {
                details.EventRemarks = documentName.Substring(0, documentName.LastIndexOf('.')) + ", output method: " +
                                       tempDestinationType + ", " + outputName;
            }
            RequestController.Instance.CreateComment(details);
            if (coverLetterCheckBox.Checked)
            {
                CommentDetails letterDetails = new CommentDetails();

                letterDetails.RequestId = requestId;
                letterDetails.EventType = EventType.LetterSend;

                StringBuilder letterNotes = new StringBuilder();
                foreach (string note in coverLetterNotes)
                {
                    letterNotes.Append(note + "~");
                }
                string coverLetterName = this.coverLetterName;

                if (!string.IsNullOrEmpty(letterNotes.ToString()))
                {
                    letterDetails.EventRemarks = coverLetterName + ": " +
                                           letterNotes.ToString().TrimEnd('~');
                }
                else
                {
                    letterDetails.EventRemarks = coverLetterName;
                }
                RequestController.Instance.CreateComment(letterDetails);
            }
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

        /// <summary>
        /// Deletes the Letter or Invoice
        /// </summary>
        /// <param name="documentInfo"></param>
        public static void CancelRelease(ReleaseAndPreviewInfo releaseAndPreviewDetails)
        {
            if (releaseAndPreviewDetails.invoiceId > 0)
            {
                BillingController.Instance.CancelRelease(releaseAndPreviewDetails.releaseId, releaseAndPreviewDetails.invoiceId);
            }

            foreach (DocInfo docInfo in releaseAndPreviewDetails.docInfoList.docInfos)
            {
                if (string.Compare(docInfo.type, ROIConstants.CoverLetterType, true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                {
                    BillingController.Instance.DeleteLetter(docInfo.id, requestId);
                }
            }
        }

        /// <summary>
        /// Add nodes into notes collection.
        /// </summary>
        /// <param name="notesPanel"></param>
        /// <param name="notes"></param>
        /// <param name="freeformNotes"></param>
        private void AddNotes(FlowLayoutPanel notesPanel, List<string> notes)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            notes.Clear();            
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
                            note.DisplayText = freeformUI.FreeformCheckBox.Text.Replace("&&", "&"); ;
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
                                notes.Add(freeformUI.FreeformTextBox.Text);
                            }
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Shows the viewer.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        /// <param name="fileName"></param>
        /// <returns></returns>
        public DialogResult ShowViewer(string fileName)
        {
            ROIViewer viewer = new ROIViewer(Pane, string.Empty, GetType().Name);

            viewer.DestinationType = destinationType;
            viewer.ReleaseDialog = true;
            viewer.RequestorFax = requestorFax;
            viewer.RequestorEmail = requestorEmail;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + GetType().Name + ".preview")+" " +this.releaseAndPreviewInfo.invoiceId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), viewer);

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
                DialogResult result;
                if (billingInfoUI.UnbillableAmount == 0)
                    result = dialog.ShowDialog(((Form)(this.Parent)));
                else
                    result = viewer.ContinueClick();
                outputPropertyDetails = viewer.OutputPropertyDetails;
                outputPropertyDetailsForDisc = viewer.OutputPropertyDetailsForDisc;
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
        /// Gets the request status.
        /// </summary>
        public bool RequestStatus
        {
            get { return yesRadioButton.Checked; }
        }

        public bool IsInvoiced
        {
            get { return invoiceCheckBox.Checked; }
        }

        public bool IsPastInvoice
        {
            get { return pastInvoiceCheckBox.Checked; }
        }

        public bool IsCoverLetter
        {
            get { return coverLetterCheckBox.Checked; }
        }

        public OutputPropertyDetails OutputPropertyDetails
        {
            get { return outputPropertyDetails; }
        }

        public OutputPropertyDetails OutputPropertyDetailsForDisc
        {
            get { return outputPropertyDetailsForDisc; }
        }


        /// <summary>
        /// Gets the document infomation
        /// </summary>
        public DocumentInfo DocumentInfo
        {
            get { return documentInfo; }
        }

       public ReleaseAndPreviewInfo ReleaseAndPreviewDetails
        {
            get { return releaseAndPreviewInfo; }
        }

        /// <summary>
        /// Gets the document infomation list
        /// </summary>
        public DocumentInfoList DocumentInfoCollection
        {
            get { return documentInfoList; }
        }

        /// <summary>
        /// Gets the invoice due days
        /// </summary>
        public int DueDays
        {
            get
            {
                dueDays = int.Parse(invoiceDueTextBox.Text.ToString(),System.Threading.Thread.CurrentThread.CurrentUICulture);
                return dueDays;
            }
        }

        /// <summary>
        /// Gets the status of invoice due days is overwrite or not
        /// </summary>
        public bool IsOverWriteDueDays
        {
            get
            {
                return DueDays != invoiceDueDetails.DueDateInDays;
            }
        }

        /// <summary>
        /// Gets the admin configured invoice due days
        /// </summary>
        public int OldDueDays
        {
            get { return invoiceDueDetails.DueDateInDays; }
        }

        /// <summary>
        /// Get the selected letter template id
        /// </summary>
        public long LetterTemplateId
        {
            get { return ((LetterTemplateDetails)invoiceComboBox.SelectedItem).Id; }
        }

        /// <summary>
        /// Get the selected letter template document id
        /// </summary>
        public long LetterTemplateDocumentId
        {
            get { return ((LetterTemplateDetails)invoiceComboBox.SelectedItem).DocumentId; }
        }

        public string LetterTemplateName
        {
            get { return ((LetterTemplateDetails)invoiceComboBox.SelectedItem).Name; }
        }

        public long DocId
        {
            get { return this.docId; }
        }
        public double AppliedAmount
        {
            get { return appliedAmount; }
            set{appliedAmount=value;}
        }
        public double TotalUnAppliedAdjustmentPayment
        {
            get { return totalUnAppliedAdjustmentPayment; }
            set { totalUnAppliedAdjustmentPayment = value; }
        }
        #endregion

    }
}
