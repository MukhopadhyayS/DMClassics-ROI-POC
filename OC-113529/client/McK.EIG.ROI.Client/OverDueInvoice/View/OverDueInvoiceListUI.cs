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
using System.Text;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.OverDueInvoice.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using System.IO;

namespace McK.EIG.ROI.Client.OverDueInvoice.View
{
    /// <summary>
    /// PastDueInvoiceListUI
    /// </summary>
    public partial class OverDueInvoiceListUI : ROIBaseUI
    {
        #region Fields

        private const string NoSearchPerformed       = ".noSearchPerformed";
        private const string MaxCountExceeded        = ".moreOverDueInvoiceFound";
        private const string ZeroOverDueInvoiceFound = ".noOverDueInvoiceFound";
        private const string OverDueInvoiceFound     = ".overDueInvoiceFound";
        private const string OverDueInvoicesFound    = ".overDueInvoicesFound";

        protected const string CheckBoxColumn      = "checkBoxColumn";
        protected const string RequestIdColumn     = "requestId";
        protected const string FacilityColumn      = "facilityName";
        protected const string InvoiceNumberColumn = "invoiceNumber";
        protected const string OverDueAmountColumn = "overDueAmount";
        protected const string OverDueDaysColumn   = "overDueDays";
        protected const string RequestorNameColumn = "requestorName";
        protected const string PhoneNumberColumn   = "phoneNumber";
        protected const string RequestorTypeColumn = "requestorType";
        protected const string RequestorIdColumn   = "requestorId";
        protected const string InvoiceAgeColumn    = "invoiceAge";
        private const string UiName = "Overdue Invoice";

        private int selectedInvoicesCount;
        private bool isMaxCountExceeded;

        private MouseEventHandler rowDoubleClickHandler;        
        internal EIGCheckedColumnHeader dgvColumnHeader;
        private List<RequestorInvoicesDetails> requestorInvoiceList;

        private bool isProgress;        

        private ArrayList requestorIds;

        #endregion

        #region Constructor

        /// <summary>
        /// PastDueInvoiceListUI
        /// </summary>
        public OverDueInvoiceListUI()
        {
            InitializeComponent();
            InitGrid();
            InitEvents();
            outputButton.Enabled = viewButton.Enabled = false;
            
        }

        #endregion

        #region Methods

        public virtual void InitGrid()
        {
            //Needs to be implemented by child class.
        }

        /// <summary>
        /// Initializes the grid event.
        /// </summary>
        public void InitEvents()
        {
            rowDoubleClickHandler = new MouseEventHandler(grid_RowDoubleClick);
            pastDueInvoiceGrid.KeyDown += new KeyEventHandler(grid_KeyDown);       
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, viewButton);
            SetLabel(rm, outputButton);
            ResetCountLabelMessage();
        }

        /// <summary>
        /// Sets the data into datagrid DataSource object
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            dgvColumnHeader.CheckAll = false;
            if (data == null)
            {
                ClearList();
                DisableButton();
                ResetCountLabelMessage();
                EnableEvents();
                return;
            }
            EnableUI(true);
            OverDueInvoiceSearchResult searchResult = data as OverDueInvoiceSearchResult;
            isMaxCountExceeded = searchResult.MaxCountExceeded;
            PopulateSearchResult(searchResult);
            UpdateRowCount();
            SelectFirstRow();
            EnableEvents();
            viewButton.Focus();
        }

        /// <summary>
        /// Bind Data to the grid.
        /// </summary>
        /// <param name="invoiceSearchResult"></param>
        private void PopulateSearchResult(OverDueInvoiceSearchResult invoiceSearchResult)
        {
            ComparableCollection<OverDueInvoiceDetails> list = new ComparableCollection<OverDueInvoiceDetails>(invoiceSearchResult.SearchResult);
            list.ApplySort(TypeDescriptor.GetProperties(typeof(OverDueInvoiceDetails))[DefaultSort], ListSortDirection.Descending);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(OverDueInvoiceDetails))[DefaultSort], ListSortDirection.Descending);            
            pastDueInvoiceGrid.SetItems(list);
            pastDueInvoiceGrid.ClearSelection(); // clear the initial selection to generate event for ODP population 
            outputButton.Enabled = false;
        }

        /// <summary>
        /// Used it for tab order.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableUI(bool enable)
        {
            this.Enabled = enable;
        }

        /// <summary>
        /// When user selects a row in grid and pressing ENTER key, Request Info screen will be displayed. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_KeyDown(object sender, KeyEventArgs e)
        {
            if ((Keys)e.KeyCode == Keys.Enter)
            {
                viewButton.PerformClick();
                e.Handled = true;
            }
        }

        /// <summary>
        /// Overrides the localize key.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == searchCountLabel)
            {
                return control.Name + NoSearchPerformed;
            }
            else if((control == viewButton) || (control == outputButton))
            {
                return control.Name + ".OverDueInvoiceListUI"; 
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Resets the past due invoice result grid search count message.
        /// </summary>
        protected void ResetCountLabelMessage()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, searchCountLabel);
        }

        /// <summary>
        /// Register events.
        /// </summary>
        protected void EnableEvents()
        {
            DisableEvents();
            pastDueInvoiceGrid.MouseDoubleClick += rowDoubleClickHandler;            
            pastDueInvoiceGrid.CellContentClick +=new DataGridViewCellEventHandler(pastDueInvoiceGrid_CellContentClick);
            
            pastDueInvoiceGrid.ColumnHeaderMouseClick += new DataGridViewCellMouseEventHandler(grid_ColumnHeaderMouseClick);
            
            viewButton.Click += new EventHandler(viewButton_Click);
            outputButton.Click += new EventHandler(outputButton_Click);
        }

        /// <summary>
        /// UnRegister events.
        /// </summary>
        protected void DisableEvents()
        {   
            pastDueInvoiceGrid.MouseDoubleClick -= rowDoubleClickHandler;
            pastDueInvoiceGrid.CellContentClick -= new DataGridViewCellEventHandler(pastDueInvoiceGrid_CellContentClick);
            pastDueInvoiceGrid.ColumnHeaderMouseClick -= new DataGridViewCellMouseEventHandler(grid_ColumnHeaderMouseClick);
         
            viewButton.Click -= new EventHandler(viewButton_Click);
            outputButton.Click -= new EventHandler(outputButton_Click);
        }

        private void EnableDisableButtons()
        {            
            if (pastDueInvoiceGrid.SelectedRows.Count <= 0) return;
            if (!isProgress)
            {
                isProgress = true;
                pastDueInvoiceGrid.EndEdit();
                bool outputEnable = false;
                bool checkAllEnable = true;
                foreach (DataGridViewRow row in pastDueInvoiceGrid.Rows)
                {
                    if (Boolean.Parse(row.Cells[0].Value.ToString()))
                    {
                        if (!outputEnable) outputEnable = true;
                    }
                    else
                    {
                        if (checkAllEnable) checkAllEnable = false;
                    }
                }
                outputButton.Enabled = outputEnable;
                dgvColumnHeader.CheckAll = checkAllEnable;
                pastDueInvoiceGrid.Invalidate();
                isProgress = false;
            }
        }

        /// <summary>
        /// Occurs when user clicks on checkbox cell.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pastDueInvoiceGrid_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {   
            EnableDisableButtons();
        }        

        /// <summary>
        /// occurs when user clicks on columnheader of grid
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_ColumnHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            // If column header is changed then grid cell content click event triggered.
            // But in the grid cell content click the row index is -1. So that output button is always disabled.
            pastDueInvoiceGrid.CellContentClick -= new DataGridViewCellEventHandler(pastDueInvoiceGrid_CellContentClick);
            bool checkAll = dgvColumnHeader.CheckAll;
            if (e.ColumnIndex == 0)
            {
                for (int i = 0; i < pastDueInvoiceGrid.Rows.Count; i++)
                {
                    pastDueInvoiceGrid.Rows[i].Cells[CheckBoxColumn].Value = dgvColumnHeader.CheckAll;                    
                }				
                //CR# 359306
                pastDueInvoiceGrid.RefreshEdit();
                //CR#359166
                outputButton.Enabled = dgvColumnHeader.CheckAll && pastDueInvoiceGrid.Rows.Count > 0;
            }

            pastDueInvoiceGrid.CellContentClick += new DataGridViewCellEventHandler(pastDueInvoiceGrid_CellContentClick);            
        }
    
        /// <summary>
        /// Selects the first row in datagridview
        /// </summary>
        protected void SelectFirstRow()
        {
            if (pastDueInvoiceGrid.Rows.Count > 0)
            {
                pastDueInvoiceGrid.Rows[0].Selected = true;
                EnableButton();
                return;
            }
            DisableButton();
            UpdateRowCount();
        }

        /// <summary>
        /// Enable the Buttons in ODP based on row selection condition.
        /// </summary>
        protected void EnableButton()
        {
            int count = pastDueInvoiceGrid.SelectedRows.Count;
            viewButton.Enabled = (count == 1);
        }

        /// <summary>
        /// Disable the button.
        /// </summary>
        protected void DisableButton()
        {
            viewButton.Enabled = false;
        }

        /// <summary>
        /// Occurs when the user Double click in the grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_RowDoubleClick(object sender, MouseEventArgs e)
        {            
            EnableDisableButtons();            
            if (pastDueInvoiceGrid.SelectedRows.Count <= 0) return;
			// CR# 359304
            DataGridView.HitTestInfo hitTestInfo = pastDueInvoiceGrid.HitTest(e.X, e.Y);
            if (hitTestInfo.Type != DataGridViewHitTestType.ColumnHeader)
            {
                viewButton.PerformClick();
            }
        }

        /// <summary>
        /// Update the Rowcount Label
        /// </summary>
        protected void UpdateRowCount()
        {
            int rowCount = pastDueInvoiceGrid.Rows.Count;
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            if (isMaxCountExceeded)
            {
                searchCountLabel.Text = rm.GetString(searchCountLabel.Name + MaxCountExceeded);
            }
            else
            {
                if (rowCount == 0)
                {
                    searchCountLabel.Text = rm.GetString(searchCountLabel.Name + ZeroOverDueInvoiceFound);
                }
                else if (rowCount == 1)
                {
                    string countText = rm.GetString(searchCountLabel.Name + OverDueInvoiceFound);
                    searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
                else if (rowCount > 1)
                {
                    string countText = rm.GetString(searchCountLabel.Name + OverDueInvoicesFound);
                    searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
            }
        }

        /// <summary>
        ///  Occurs when output button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void outputButton_Click(object sender, EventArgs e)
        {
            try
            {
                Hashtable requestorInvoices = (Hashtable)GetData();
                OutputInvoiceDialog outputInvoiceDialog = new OutputInvoiceDialog(Pane);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                Form dialog = ROIViewUtility.ConvertToForm(null,
                                                           rm.GetString("title." + outputInvoiceDialog.Name),
                                                           outputInvoiceDialog);

                Collection<LetterTemplateDetails> letterTemplateList = ROIAdminController.Instance.RetrieveAllLetterTemplates();

                IList<LetterTemplateDetails> RequestorLetterTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, LetterType.RequestorStatement.ToString());
                IList<LetterTemplateDetails> invoiceTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, LetterType.Invoice.ToString());

                long defaultRequestorLetterId = ROIViewUtility.RetrieveDefaultId(RequestorLetterTemplates);
                long defaultInvoiceId = ROIViewUtility.RetrieveDefaultId(invoiceTemplates);
                outputInvoiceDialog.PrePopulate(RequestorLetterTemplates, invoiceTemplates, defaultRequestorLetterId, requestorIds,
                                                defaultInvoiceId, requestorInvoices, requestorInvoiceList, selectedInvoicesCount);
                dialog.ShowDialog();
                dialog.Close();
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// Occurs when view button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void viewButton_Click(object sender, EventArgs e)
        {
            OverDueInvoiceDetails overDueInvoiceDetails = (OverDueInvoiceDetails)pastDueInvoiceGrid.SelectedRows[0].DataBoundItem;
            DocumentInfo documentInfo = BillingController.Instance.ViewInvoiceHistory("Invoice", overDueInvoiceDetails.InvoiceNumber);

            ROIViewer viewer = new ROIViewer(Pane, LetterType.OverdueInvoice.ToString(), UiName);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title.Invoice.preview") +
                                                             " " + overDueInvoiceDetails.InvoiceNumber, viewer);
            string filePath = string.Empty;
           
            filePath = BillingController.DownloadLetterTemplate(documentInfo.Name, null);
            viewer.PDFDocumentViewer.SerialNumber = "PDFVW4WIN-ENMBG-1CA2A-9Z3DV-RVH0Y-24K1M";
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
            viewer.ReleaseDialog = true;
            viewer.DisplayContinueButton = false;
            dialog.ShowDialog(this);
            dialog.Close();           
        }
        
        /// <summary>
        /// Gets all the selected invoices in the overdue invoice grid
        /// </summary>
        /// <returns></returns>
        private object GetData()
        {
            selectedInvoicesCount = 0;
            Hashtable requestorInvoices = new Hashtable();
            requestorInvoiceList = new List<RequestorInvoicesDetails>();
            requestorIds = new ArrayList();
            RequestorInvoicesDetails requestorInvoicesDetails;
            RequestorInvoicesDetails requestorInvoiceDet;
            
            foreach (DataGridViewRow row in pastDueInvoiceGrid.Rows)
            {
                requestorInvoicesDetails = new RequestorInvoicesDetails();
                requestorInvoiceDet = new RequestorInvoicesDetails();

                if (row.Cells[CheckBoxColumn].Value != null && (bool)row.Cells[CheckBoxColumn].Value)
                {
                    selectedInvoicesCount++;
                    requestorInvoicesDetails.RequestorId = (long)row.Cells[RequestorIdColumn].Value;
                    requestorInvoicesDetails.RequestId = (long)row.Cells[RequestIdColumn].Value;
                    requestorInvoiceDet.RequestId = requestorInvoicesDetails.RequestId;
                    requestorInvoicesDetails.RequestorName = (string)row.Cells[RequestorNameColumn].Value;
                    requestorInvoiceDet.RequestorName = requestorInvoicesDetails.RequestorName;
                    requestorInvoicesDetails.RequestorType = (string)row.Cells[RequestorTypeColumn].Value;
                    requestorInvoicesDetails.InvoiceIds.Add((long)row.Cells[InvoiceNumberColumn].Value);
                    
                    //Start - Store Non Grouping of the requestor invoices 
                    requestorInvoiceDet.InvoiceIds.Add((long)row.Cells[InvoiceNumberColumn].Value);
                    requestorInvoiceList.Add(requestorInvoiceDet);
                    //End - Store Non Grouping of the requestor invoices 

                    if (!requestorInvoices.ContainsKey(requestorInvoicesDetails.RequestorId))
                    {
                        requestorInvoicesDetails.GreatestInvoiceAge = ((long)row.Cells[InvoiceAgeColumn].Value);
                        requestorInvoices[requestorInvoicesDetails.RequestorId] = requestorInvoicesDetails;
                        //To maintain the grid sorted order to output
                        requestorIds.Add(requestorInvoicesDetails.RequestorId);
                    }
                    else
                    {
                        if (((RequestorInvoicesDetails)requestorInvoices[requestorInvoicesDetails.RequestorId]).GreatestInvoiceAge < ((long)row.Cells[InvoiceAgeColumn].Value))
                        {
                            ((RequestorInvoicesDetails)requestorInvoices[requestorInvoicesDetails.RequestorId]).GreatestInvoiceAge = ((long)row.Cells[InvoiceAgeColumn].Value);
                        }

                        ((RequestorInvoicesDetails)requestorInvoices[requestorInvoicesDetails.RequestorId]).InvoiceIds.
                        Add((long)row.Cells[InvoiceNumberColumn].Value);                   
                    }
                }
            }
            
            return requestorInvoices;
        }

        /// <summary>
        /// Clears the grid
        /// </summary>
        protected void ClearList()
        {
            pastDueInvoiceGrid.Rows.Clear();
            isMaxCountExceeded = false;
            UpdateRowCount();
            EnableButton();
            EnableUI(false);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the default sort column.
        /// </summary>
        protected virtual string DefaultSort
        {
            get { return "BillingLocation"; }
        }

        #endregion
    }
}
