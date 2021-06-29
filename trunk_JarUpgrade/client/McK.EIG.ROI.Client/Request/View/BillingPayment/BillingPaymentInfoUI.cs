#region Copyright © 2010-2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
using System.Drawing;
using System.IO;
using System.Resources;
using System.Text;
using System.Globalization;
using System.Windows.Forms;
using System.Security;

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
using McK.EIG.ROI.Client.Request.View.RequestInfo;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Web_References.BillingCoreWS;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    
    /// <summary>
    /// BillingPaymentInfoUI which contains billing related UI controls
    /// </summary>
    public partial class BillingPaymentInfoUI : ROIBaseUI, IFooterProvider
    {
        #region Fields

        private const string PleaseSelect = "Please Select";
        private const string pastInvoice = "pastInvoice";
        private const double DefaultTaxAmount = 0.0;

        private Log log = LogFactory.GetLogger(typeof(BillingPaymentInfoUI));

        internal static EventHandler ProgressHandler;

        private ROIProgressBar fileTransferProgress;

        private BillingPaymentActionUI billingPaymentActionUI;
        InvoiceChargeDetailsList inVoiceChargeDetailsList;

        private bool isDirty;
        private bool doSave;
        private int releasedPageCount;
        private int unreleasedCount;

        private RequestDetails request;
        private ReleaseDetails release;
        private ReleaseDetails releaseCore;
        private Collection<ChargeHistoryDetails> chargeHistories;

        private OutputPropertyDetails outputPropertyDetails;        
        private OutputViewDetails outputViewDetails;
        private DestinationType destinationType;

        private int previouslyReleasedPages;
        private static int printableAttachmentCount;
        private static int nonPrintableAttachmentCount;
        private static int hpfDocumentCount;        
        private bool isApplyTaxStateChanged;
        private bool oldSalesTaxStatus;        
        private string previousFacilityName;
        private TaxPerFacilityDetails defaultFacility;        

        private const string InvoiceIdColumn = "invoiceId";
        private bool isRevert = false;
        private bool isSave = false;
        private bool isSetdata = false;
        private RequestBillingInfo requestBillingInfo = new RequestBillingInfo();
        ReleaseDetails releaseRev = new ReleaseDetails();
        private List<CountryCodeDetails> countryCodeDetails;
        //To audit the billing tier apply tax entry to be persisted in the DB, when user apply the billing tier sales tax for the first time
        //and also if there is any changes in billing tier sales tax for the subsequent save call should also persist
        private static bool applyTax;
        ReleaseDialog releaseDialog;
        private bool hasRights;
        public static bool isOnlyNonHPFDocuments = false;
        public double balanceDuePrebill;
        private RequestPatients requestPatients;

        #endregion

        public double BalanceDuePreBill
        {
            get { return balanceDuePrebill; }
            set { balanceDuePrebill = value; }
        }

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public BillingPaymentInfoUI()
        {
            InitializeComponent();
            CreateActionUI(); 
            
        }

        #endregion
        
        #region Methods

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
        /// Apply Localization for UI controls
        /// </summary>
        public override void Localize()
        {   
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            //SetLabel(rm, billingShippingGroupBox);            
            SetLabel(rm, shippingTabPage);
            SetLabel(rm, TaxSummaryTabPage);
            SetLabel(rm, billingLocationLabel);
            SetLabel(rm, totalBalanceDueGroupBox);
            SetLabel(rm, pendingReleaseCostTotalLabel);
            SetLabel(rm, previousReleaseCostLabel);
            SetLabel(rm, chargeHistoryButton);
            SetLabel(rm, totalRequestCostLabel);
            SetLabel(rm, unAppliedAdjAndPayLabel);
            SetLabel(rm, adjustmentPaymentLabel);
            SetLabel(rm, applyTaxLabel);
         //   SetLabel(rm, totalSalesTaxLabel);
            SetLabel(rm, balanceDueLabel);
			//CR#359276 - Add new UI attribute to show the sum of current invoice balances
            SetLabel(rm, invoicedAmountLabel);
            SetLabel(rm, discountsLabel);
            
            billingPaymentActionUI.Localize();
            documentChargeUI.Localize();
            feeChargeUI.Localize();
            shippingInfoUI.Localize();            
            salesTaxSummaryUI.Localize();

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, chargeHistoryButton);

            ProgressHandler = new EventHandler(Process_NotifyProgress);
            InitProgress();
        }

        /// <summary>
        /// Gets the localized key of the control
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + Pane.GetType().Name; 
        }

        /// <summary>
        /// Gets the localized key for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + Pane.GetType().Name; 
        }

        /// <summary>
        /// Sets the pane object in children
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);

            billingPaymentActionUI.SetPane(Pane);
            billingPaymentActionUI.SetExecutionContext(Context);

            documentChargeUI.SetPane(Pane);
            documentChargeUI.SetExecutionContext(Context);

            feeChargeUI.SetPane(Pane);
            feeChargeUI.SetExecutionContext(Context);            

            shippingInfoUI.SetPane(Pane);
            shippingInfoUI.SetExecutionContext(Context);

            salesTaxSummaryUI.SetPane(Pane);
            salesTaxSummaryUI.SetExecutionContext(Context);
        }

        /// <summary>
        /// Sets the actionUI for the footer.
        /// </summary>
        private void CreateActionUI()
        {
            billingPaymentActionUI = new BillingPaymentActionUI();

            billingPaymentActionUI.SaveButton.Click      += new EventHandler(saveButton_Click);
            billingPaymentActionUI.RevertButton.Click    += new EventHandler(revertButton_Click);
            billingPaymentActionUI.PreBillButton.Click   += new EventHandler(prebillButton_Click);
            billingPaymentActionUI.InvoiceButton.Click   += new EventHandler(invoiceButton_Click);
            billingPaymentActionUI.RereleaseButton.Click += new EventHandler(ReReleaseButton_Click);
            billingPaymentActionUI.ReleaseButton.Click   += new EventHandler(ReleaseButton_Click);
            billingPaymentActionUI.AccountManagementButton.Click += new EventHandler(AccountManagementButton_Click);
            billingPaymentActionUI.AddPaymentButton.Click += new EventHandler(AddPaymentButton_Click);
            billingPaymentActionUI.AddAdjustmentButton.Click += new EventHandler(AddAdjustmentButton_Click);
            shippingSalesTaxTabControl.SelectedIndexChanged += new EventHandler(shippingSalesTaxTabControl_SelectedIndexChanged);

            EnableButtons(false);
        }

        private void AddAdjustmentButton_Click(object sender, EventArgs e)
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                EventHandler selectRequestorHandler = new EventHandler(Process_SelectPayment);
                EventHandler cancelHandler = new EventHandler(Process_CancelPayment);
                McK.EIG.ROI.Client.Requestors.View.AccountManagement.AdjustmentsUI adjustmentUI = new McK.EIG.ROI.Client.Requestors.View.AccountManagement.AdjustmentsUI(selectRequestorHandler, cancelHandler, Pane);
                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("adjustments.titlebar.text"), adjustmentUI);
                Collection<PaymentMethodDetails> paymentMethods = BillingAdminController.Instance.RetrieveAllPaymentMethods(false);
                Collection<RequestInvoiceDetail> requestInvoiceDetailsList = RequestController.Instance.RetrieveRequestInvoices(request.Id);
                AdjustmentInfoDetail adjInfoDetail = RequestorController.Instance.RetrieveAdjustmentInfo(request.Requestor.Id);
                adjInfoDetail.RequestorInvoicesList = requestInvoiceDetailsList;
                double adjPay = 0;
                double balance = 0;
                foreach (RequestInvoiceDetail requestInvoiceDetail in adjInfoDetail.RequestorInvoicesList)
                {
                    adjPay += requestInvoiceDetail.AdjPay;
                    balance += requestInvoiceDetail.Balance;
                }
                if (adjInfoDetail != null)
                {
                    adjustmentUI.SetData(adjInfoDetail, request.Requestor.Id, request.Requestor);
                }

                DialogResult result = form.ShowDialog(this);

                if (result.ToString() != "Cancel")
                {

                    release.CreditAdjustmentTotal += adjustmentUI.TotalAdjustmentAmount;
                    release.UnAppliedTotal += Math.Abs(adjustmentUI.UnAppliedAdjustmentAmount);
                    UnAppliedAdjustmentPaymentTotal += Math.Abs(adjustmentUI.UnAppliedAdjustmentAmount);
                    double unAppliedAmount = RetrieveUnAppliedAmount(request.Id);
                    //double unAppliedAmount = Convert.ToDouble(unAppliedAdjAndPayValueLabel.Text.Trim().Substring(1, unAppliedAdjAndPayValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    unAppliedAdjAndPayValueLabel.Text = ReleaseDetails.FormattedAmount(unAppliedAmount);
                    double appliedAmount = 0;
                    if (adjustmentUI.TotalAdjustmentAmount == 0)
                    {
                        appliedAmount = Convert.ToDouble(adjPaymentTotalValueLabel.Text.Trim().Substring(1, adjPaymentTotalValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    }
                    else
                    {
                        appliedAmount = adjPay;
                    }
                    //adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(adjustmentUI.TotalAdjustmentAmount + appliedAmount);
                    adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(adjustmentUI.TotalAdjustmentAmount + appliedAmount);
                    double balanceDue = Convert.ToDouble(balanceDueValueLabel.Text.Trim().Substring(1, balanceDueValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    //balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue - adjustmentUI.TotalAdjustmentAmount);
                    //balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balance - adjustmentUI.TotalAdjustmentAmount);
                    if ((releaseDialog != null) || (request.Status == RequestStatus.Completed) || (request.Status == RequestStatus.PreBilled))
                    {
                        balanceDuePrebill = balanceDue - adjustmentUI.TotalAdjustmentAmount;                        
                        balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue - adjustmentUI.TotalAdjustmentAmount);
                    }
                    else
                    {
                        balanceDuePrebill = balanceDue;
                        balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue);
                    }
                    ApplicationEventArgs ae = new ApplicationEventArgs(request.Requestor, this);
                    RequestEvents.OnAccountManagementGridRefresh(Pane, ae);
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        private void AddPaymentButton_Click(object sender, EventArgs e)
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                EventHandler selectRequestorHandler = new EventHandler(Process_SelectPayment);
                EventHandler cancelHandler = new EventHandler(Process_CancelPayment);
                McK.EIG.ROI.Client.Requestors.View.AccountManagement.PaymentUI paymentUI = new McK.EIG.ROI.Client.Requestors.View.AccountManagement.PaymentUI(selectRequestorHandler, cancelHandler, Pane);
                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("payments.titlebar.text"), paymentUI);
                Collection<PaymentMethodDetails> paymentMethods = BillingAdminController.Instance.RetrieveAllPaymentMethods(false);
                Collection<RequestInvoiceDetail> requestInvoiceDetailsList = RequestController.Instance.RetrieveRequestInvoices(request.Id);
                double adjPay = 0;
                double balance = 0;
                foreach (RequestInvoiceDetail requestInvoiceDetail in requestInvoiceDetailsList)
                {
                    adjPay += requestInvoiceDetail.AdjPay;
                    balance += requestInvoiceDetail.Balance;
                }
                ComparableCollection<RequestInvoiceDetail> comparableRequestInvoiceDetailsList = new ComparableCollection<RequestInvoiceDetail>(requestInvoiceDetailsList);
                paymentUI.PrePopulate(paymentMethods, comparableRequestInvoiceDetailsList, request.Requestor.Id, true, request.Requestor);
                DialogResult result= form.ShowDialog(this);
                if (result.ToString() != "Cancel")
                {

                    release.PaymentTotal += paymentUI.TotalApplyAmount;
                    release.InvoicesBalanceDue -= paymentUI.TotalApplyAmount;
                    // requestorInvoiceList.PaymentAmount = Convert.ToDouble(AmountTextBox.Text.Trim().Substring(1, AmountTextBox.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    double invoiceAmount;
                    if (totalInvoicedCostValueLabel.Text.Contains("("))
                    {
                        invoiceAmount = 0;
                    }
                    else
                    {
                        invoiceAmount = Convert.ToDouble(totalInvoicedCostValueLabel.Text.Trim().Substring(1, totalInvoicedCostValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    }
                    double totalInvoiceAmount = invoiceAmount - paymentUI.TotalApplyAmount;
                    totalInvoicedCostValueLabel.Text = ReleaseDetails.FormattedAmount(totalInvoiceAmount);
                    double appliedAmount = 0;
                    if (paymentUI.TotalApplyAmount == 0)
                    {
                        appliedAmount = Convert.ToDouble(adjPaymentTotalValueLabel.Text.Trim().Substring(1, adjPaymentTotalValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture); ;
                    }
                    else
                    {
                        appliedAmount = adjPay;
                    }
                    //adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(release.PaymentTotal + release.CreditAdjustmentTotal + appliedAmount);
                    adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(paymentUI.TotalApplyAmount + appliedAmount);

                    double balanceDue = Convert.ToDouble(balanceDueValueLabel.Text.Trim().Substring(1, balanceDueValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    //balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue - paymentUI.TotalApplyAmount + appliedAmount);
                    //US16834 - changes to Include requests in the pre-bill status on the payments popup.
                    if ((releaseDialog != null) || (request.Status == RequestStatus.Completed) ||(request.Status == RequestStatus.PreBilled) )
                    {
                        balanceDuePrebill = balanceDue - paymentUI.TotalApplyAmount;
                        balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue - paymentUI.TotalApplyAmount);
                    }
                    else
                    {
                        balanceDuePrebill = balanceDue;
                        balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue); 
                    }

                    release.UnAppliedTotal += Math.Abs(release.PaymentTotal - paymentUI.TotalPaymentAmount);
                    UnAppliedAdjustmentPaymentTotal += Math.Abs(release.PaymentTotal - paymentUI.TotalPaymentAmount);

                    //double unAppliedAmount = Convert.ToDouble(unAppliedAdjAndPayValueLabel.Text.Trim().Substring(1, unAppliedAdjAndPayValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    double unAppliedAmount = RetrieveUnAppliedAmount(request.Id);
                    unAppliedAdjAndPayValueLabel.Text = ReleaseDetails.FormattedAmount(unAppliedAmount);
                    //unAppliedAdjAndPayValueLabel.Text = ReleaseDetails.FormattedAmount(Math.Abs(paymentUI.UnAppliedAmountTotal));
                    ApplicationEventArgs ae = new ApplicationEventArgs(request.Requestor, this);
                    RequestEvents.OnAccountManagementGridRefresh(Pane, ae);
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        private void Process_SelectPayment(object sender, EventArgs e)
        {

        }
        private void Process_CancelPayment(object sender, EventArgs e)
        {

        }

        private void AccountManagementButton_Click(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.FindRequestor, e));
            ApplicationEventArgs ae = new ApplicationEventArgs(request.Requestor, this);
            McK.EIG.ROI.Client.Requestors.View.RequestorEvents.OnAccountManagementSelected(Pane, ae);
            RequestEvents.OnAccountManagementGridRefresh(Pane, ae);
        }

        /// <summary>
        /// Occurs when user click the Billing or Shipping or Summary tab 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void shippingSalesTaxTabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            Hashtable taxItemStates = salesTaxSummaryUI.RetrieveTaxItemStates();
            if (string.Compare(shippingSalesTaxTabControl.SelectedTab.Name, "taxsummarytabpage", true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)            
            {
                SetSalesTaxSummaryUI(taxItemStates);
            }
        }

        /// <summary>
        /// Set the data into the grid which is currently available in the request delivery charges XML or in the UI.
        /// </summary>
        private void SetSalesTaxSummaryUI(Hashtable taxItemStates)
        {
            SalesTaxChargeDetails salesTaxChargeDetails;         
            Collection<DocumentChargeDetails> documentCharges = (Collection<DocumentChargeDetails>)documentChargeUI.GetData(null);
            ReleaseDetails releaseDetails = (ReleaseDetails)feeChargeUI.GetData(ROIViewUtility.DeepClone(release));
            releaseCore = (ReleaseDetails)feeChargeUI.GetData(ROIViewUtility.DeepClone(release));

            //clear the sales tax grid before adding any elements
            salesTaxSummaryUI.SalesTaxCharges.Clear();

            foreach (DocumentChargeDetails documentCharge in documentCharges)
            {
                salesTaxChargeDetails = new SalesTaxChargeDetails();
                salesTaxChargeDetails.ChargeName = documentCharge.BillingTier;
                salesTaxChargeDetails.Amount = documentCharge.Amount;

                //CR# 359331. Hence the sales tax amount needs to be displayed in the grid instead of 0.0 while user select/deselect the checkbox, 
                //TaxAmount is set outside of the below if conditions.

                // CR# 377,374 - Fix 
                //salesTaxChargeDetails.TaxAmount = Math.Round(documentCharge.Amount * request.TaxPercentage / 100, 2);
                salesTaxChargeDetails.TaxAmount = ROIViewUtility.RoundOffValue(documentCharge.Amount * request.TaxPercentage / 100, 2);

                //set the key for each doc type charge with doc type name added with constant ".Doc"
                salesTaxChargeDetails.Key = documentCharge.BillingTier + ROIConstants.SalesTaxDocFeeKey;

                // Whenever cell content of grid is clicked, this property is set to true. 
                //once this set to true, we set the hassalestax value of salestax elements from the hashtable
                if (salesTaxSummaryUI.IsGridItemChanged && documentCharge.BillingTierDetail.SalesTax.Equals("No"))
                {
                    salesTaxChargeDetails.HasBillingTier = documentCharge.BillingTierDetail.SalesTax.Equals("Yes") ? false : true;
                    // New request || New or Edit mode if Apply tax checkbox state changed
                    if (isApplyTaxStateChanged)
                    {
                        salesTaxChargeDetails.HasSalesTax = documentCharge.BillingTierDetail.SalesTax.Equals("Yes") ? true : false; //Existing request                       
                    }
                    else
                    {
                        salesTaxChargeDetails.HasSalesTax = documentCharge.HasSalesTax; //Existing request                       
                    }
                }
                if (salesTaxSummaryUI.IsGridItemChanged && documentCharge.BillingTierDetail.SalesTax.Equals("Yes"))
                {
                    salesTaxChargeDetails.HasSalesTax = (bool)taxItemStates[salesTaxChargeDetails.Key];                    
                }
                else
                {
                    salesTaxChargeDetails.HasBillingTier = documentCharge.BillingTierDetail.SalesTax.Equals("Yes") ? false : true;
                    // New request || New or Edit mode if Apply tax checkbox state changed
                    if (isApplyTaxStateChanged)
                    {
                        salesTaxChargeDetails.HasSalesTax = documentCharge.BillingTierDetail.SalesTax.Equals("Yes") ? true : false; //Existing request                       
                    }
                    else
                    {
                        salesTaxChargeDetails.HasSalesTax = documentCharge.HasSalesTax; //Existing request                       
                    }
                }
                salesTaxSummaryUI.SalesTaxCharges.Add(salesTaxChargeDetails);
            }

            foreach (FeeChargeDetails feeCharge in releaseDetails.FeeCharges)
            {
                salesTaxChargeDetails = new SalesTaxChargeDetails();
                salesTaxChargeDetails.ChargeName = feeCharge.FeeType;
                salesTaxChargeDetails.IsCustomFee = feeCharge.IsCustomFee;
                salesTaxChargeDetails.Amount = feeCharge.Amount;
                // CR# 377,374 - Fix 
                //salesTaxChargeDetails.TaxAmount = Math.Round(feeCharge.Amount * request.TaxPercentage / 100, 2);
                salesTaxChargeDetails.TaxAmount = ROIViewUtility.RoundOffValue(feeCharge.Amount * request.TaxPercentage / 100, 2);

                //if feecharge is customfee
                if (feeCharge.IsCustomFee)
                {
                    //if feecharge is custom fee, then key value comes from the feecharge model itself. we dont need to set the key here..
                    salesTaxChargeDetails.Key = feeCharge.Key;

                    if (salesTaxSummaryUI.IsGridItemChanged)
                    {
                        salesTaxChargeDetails.HasSalesTax = (taxItemStates[salesTaxChargeDetails.Key] != null) ? 
                                                            (bool)taxItemStates[salesTaxChargeDetails.Key] : false;                        
                    }
                    else
                    {
                        // New request || New or Edit mode if Apply tax checkbox state changed
                        bool isApplyTaxSet = isApplyTaxStateChanged;
                        salesTaxChargeDetails.HasSalesTax = isApplyTaxSet ? false : feeCharge.HasSalesTax;                        
                    }
                }
                else
                {
                    Hashtable feetypes = feeChargeUI.AdminFeeTypeValues;
                    salesTaxChargeDetails.Key = feeCharge.FeeType + ROIConstants.SalesTaxStdFeeKey;
                    bool adminFeeTypeTax = feetypes[salesTaxChargeDetails.Key] != null ? 
                                           feetypes[salesTaxChargeDetails.Key].ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes") : false;                    
                    
                    if(salesTaxSummaryUI.IsGridItemChanged)
                    {
                        salesTaxChargeDetails.HasSalesTax = (bool)taxItemStates[salesTaxChargeDetails.Key];                        
                    }
                    else
                    {
                        // New request || New or Edit mode if Apply tax checkbox state changed
                        if (isApplyTaxStateChanged)
                        {
                            salesTaxChargeDetails.HasSalesTax = (applyTaxCheckBox.Checked) ? adminFeeTypeTax : false;                            
                        }
                        else
                        {
                            salesTaxChargeDetails.HasSalesTax = feeCharge.HasSalesTax;                            
                        }
                    }
                }                
                salesTaxSummaryUI.SalesTaxCharges.Add(salesTaxChargeDetails);
            }
            salesTaxSummaryUI.SetTotalTaxAmount();
            salesTaxSummaryUI.SetTotalChargeAmount();
        }

        /// <summary>
        /// Save the release info
        /// </summary>
        /// <returns></returns>
        public bool Save()
        {
            try
            {
                errorProvider.Clear();
                isSave = true;

                if (!(feeChargeUI.NoError && shippingInfoUI.NoError))
                {
                    return false;
                }

                StringBuilder selectedChargeNames = new StringBuilder();
                StringBuilder deselectedChargeNames = new StringBuilder();
                StringBuilder auditMessage = new StringBuilder();
                bool isChargesSelected = false;
                //CR#359297. The below bool variable is used to check whether the sales tax elements are override or not
                bool isTaxElementOverride = false;

                auditMessage.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                  ROIConstants.SalesTaxMessage,
                                                 (salesTaxSummaryUI.ApplyTaxState) ? 
                                                  ROIConstants.Selected: ROIConstants.Removed, 
                                                  "the request : ", 
                                                  request.Id)).Append(";");

                //set the salestaxsumaryUI from the hashtable values which contains the current selection of items in the grid
                Hashtable taxItemStates = salesTaxSummaryUI.RetrieveTaxItemStates();
                SetSalesTaxSummaryUI(taxItemStates);

                ROIViewUtility.MarkBusy(true);
                ReleaseDetails releaseDetails = (ReleaseDetails)GetData(ROIViewUtility.DeepClone(release));
                releaseDetails.DocChargeTaxTotal = 0.0;

                //save the tax override status of each fee element
                //Compare the Document charges in the release object with the document charges in the sales tax grid
                foreach (DocumentChargeDetails docChargeDetails in releaseDetails.DocumentCharges)
                {
                    foreach (SalesTaxChargeDetails salesTaxChargeDetails in salesTaxSummaryUI.SalesTaxCharges)
                    {
                        string[] docName = salesTaxChargeDetails.Key.Split('.');

                        if (docName[0] != null && docName[1] != null && 
                            docName[0].Equals(docChargeDetails.BillingTier) && docName[1].Equals("Doc"))
                        {
                            
                            //CR#359297. To find whether the sales tax is override since the last save
                            RequestCoreChargeDetails requestCore = new RequestCoreChargeDetails();

                            if (request.HasSalesTax != applyTax)
                            {
                                isTaxElementOverride = true;
                            }
                           docChargeDetails.HasSalesTax = salesTaxChargeDetails.HasSalesTax;
                            docChargeDetails.TaxAmount = salesTaxChargeDetails.HasSalesTax ? salesTaxChargeDetails.TaxAmount : DefaultTaxAmount;
                            releaseDetails.DocChargeTaxTotal += salesTaxChargeDetails.HasSalesTax ? salesTaxChargeDetails.TaxAmount : DefaultTaxAmount;

                            //To get the selected/deselected document charge names for audit
                            if (docChargeDetails.HasSalesTax && request.HasSalesTax)
                            {
                                selectedChargeNames.Append(docChargeDetails.BillingTier).Append(",");
                            }
                            else
                            {
                                deselectedChargeNames.Append(docChargeDetails.BillingTier).Append(",");
                            }

                            break;
                        }
                    }
                }

                //check whether any document charges has been selected
                if (selectedChargeNames.Length > 0)
                {                    
                    isChargesSelected = true;
                    auditMessage.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,                                                        
                                                      ROIConstants.SalesTaxDocMessage,
                                                      ROIConstants.Applied,
                                                      selectedChargeNames));
                }

                //check whether any document charges has been deselected
                if (deselectedChargeNames.Length > 0)
                {
                    string deselectChargeNames = deselectedChargeNames.ToString().TrimEnd(new char[] { ',' });
                    auditMessage.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SalesTaxDocMessage,
                                                 ROIConstants.BillingTierRemoved, deselectChargeNames));
                    auditMessage.Append(";");
                }
                else if(isChargesSelected)
                {
                    string message = auditMessage.ToString().TrimEnd(new char[] { ',' });
                    auditMessage.Remove(0, auditMessage.Length);
                    auditMessage.Append(message).Append(";");                    
                }
                
                
                StringBuilder selectedFeeNames = new StringBuilder();
                StringBuilder deselectedFeeNames = new StringBuilder();
                selectedChargeNames = selectedChargeNames.Remove(0, selectedChargeNames.Length);
                deselectedChargeNames = deselectedChargeNames.Remove(0, deselectedChargeNames.Length);

                //Compare the fee types in the release object with the fee types in the sales tax grid
                releaseDetails.CustomChargeTaxTotal = 0.0;
                releaseDetails.FeeChargeTaxTotal = 0.0;

                foreach (FeeChargeDetails feeChargeDetails in releaseDetails.FeeCharges)
                {
                    foreach (SalesTaxChargeDetails salesTaxChargeDetails in salesTaxSummaryUI.SalesTaxCharges)
                    {
                        string[] feeName = salesTaxChargeDetails.Key.Split('.');
                        if (feeChargeDetails.IsCustomFee)
                        {
                            if (feeChargeDetails.Key.Equals(salesTaxChargeDetails.Key))
                            {
                                //CR#359297 To find whether the sales tax is override since the last save
                                if (feeChargeDetails.HasSalesTax != salesTaxChargeDetails.HasSalesTax)
                                {
                                    isTaxElementOverride = true;
                                }

                                feeChargeDetails.HasSalesTax = salesTaxChargeDetails.HasSalesTax;
                                feeChargeDetails.TaxAmount = salesTaxChargeDetails.HasSalesTax ? salesTaxChargeDetails.TaxAmount : DefaultTaxAmount;
                                releaseDetails.CustomChargeTaxTotal += salesTaxChargeDetails.HasSalesTax ? salesTaxChargeDetails.TaxAmount : DefaultTaxAmount;

                                //For audit the selected/deselected custom fee types
                                if (feeChargeDetails.HasSalesTax)
                                {
                                    selectedChargeNames.Append(feeChargeDetails.FeeType).Append(",");
                                }
                                else
                                {
                                    deselectedChargeNames.Append(feeChargeDetails.FeeType).Append(",");
                                }
                                break;
                            }
                        }
                        else
                        {
                            if (feeName[0].Equals(feeChargeDetails.FeeType) && feeName[1].Equals("Std"))
                            {
                                //CR#359297 To find whether the sales tax is override since the last save
                                if (feeChargeDetails.HasSalesTax != salesTaxChargeDetails.HasSalesTax)
                                {
                                    isTaxElementOverride = true;
                                }

                                feeChargeDetails.HasSalesTax = salesTaxChargeDetails.HasSalesTax;
                                feeChargeDetails.TaxAmount = salesTaxChargeDetails.HasSalesTax ? salesTaxChargeDetails.TaxAmount : DefaultTaxAmount;
                                releaseDetails.FeeChargeTaxTotal += salesTaxChargeDetails.HasSalesTax ? salesTaxChargeDetails.TaxAmount : DefaultTaxAmount;

                                //For audit the selected/deselected standard fee types
                                if (feeChargeDetails.HasSalesTax)
                                {
                                    selectedFeeNames.Append(feeChargeDetails.FeeType).Append(",");
                                }
                                else
                                {
                                    deselectedFeeNames.Append(feeChargeDetails.FeeType).Append(",");
                                }
                                break;
                            }
                        }
                    }
                }

                isChargesSelected = false;

                //check whether any fee types are selected
                if (selectedFeeNames.Length > 0)
                {
                    isChargesSelected = true;
                    auditMessage.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SalesTaxStdFeeMessage,
                                                 ROIConstants.Selected, selectedFeeNames));
                }

                //check whether any fee types are deselected
                if (deselectedFeeNames.Length > 0)
                {
                    string deselectFeeNames = deselectedFeeNames.ToString().TrimEnd(new char[] { ',' });
                    auditMessage.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SalesTaxStdFeeMessage,
                                                 ROIConstants.Removed, deselectFeeNames));
                    auditMessage.Append(";");
                }
                else if (isChargesSelected)
                {
                    string message = auditMessage.ToString().TrimEnd(new char[] { ',' });
                    auditMessage.Remove(0, auditMessage.Length);
                    auditMessage.Append(message).Append(";");
                }

                //check whether any custom fee types are selected
                if (selectedChargeNames.Length > 0)
                {
                    isChargesSelected = true;
                    auditMessage.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SalesTaxCusFeeMessage,
                                                 ROIConstants.Selected, selectedChargeNames));
                }

                //check whether any custom fee types are deselected
                if (deselectedChargeNames.Length > 0)
                {
                    string deselectChargeNames = deselectedChargeNames.ToString().TrimEnd(new char[]{','});
                    auditMessage.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SalesTaxCusFeeMessage,
                                                 ROIConstants.Removed, deselectChargeNames));
                }
                else if (isChargesSelected)
                {
                    string message = auditMessage.ToString().TrimEnd(new char[] { ',' });
                    auditMessage.Remove(0, auditMessage.Length);
                    auditMessage.Append(message);
                }

                ////CR#359297. call audit & event creation method if auditmessages & eventmessages are not empty
                if (isTaxElementOverride && auditMessage.Length > 0)
                {
                    string message = auditMessage.ToString().TrimEnd(new char[] { ';' });
                    createSalesTaxAuditEventMessage(message, request.Id, true);
                }
                

                if (request.DefaultFacility.FacilityName != null)
                {
                    //call audit & event creation method if user changed the billing location
                    if (!previousFacilityName.Equals(request.DefaultFacility.FacilityName) &&
                        !request.DefaultFacility.FacilityName.Equals(PleaseSelect) &&
                        !previousFacilityName.Equals(PleaseSelect))
                    {
                        string message = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.ChangeOfBillingLocationMessage,
                                                       previousFacilityName, request.DefaultFacility.FacilityName);
                        createSalesTaxAuditEventMessage(message, request.Id, false);
                    }
                }

                if((shippingInfoUI.IsPrint || shippingInfoUI.IsFax) && request.RetrieveNonPrintableCount() > 0)
                {
                    releaseDetails.NonPrintableQueueSelected = shippingInfoUI.NonPrinableComboBox.SelectedIndex > 0;
                }				
                if (releaseDetails.Id == 0)
                {
                    //releaseDetails.Id = BillingController.Instance.CreateReleaseItem(releaseDetails);
                }
                else
                {
                   //releaseDetails = BillingController.Instance.UpdateReleaseItem(releaseDetails);
                }                

                release = releaseDetails;
             
                if(!release.IsReleased)
                {
                    request.DraftRelease = release;
                }

                //For Saving Balance due
                request.BalanceDue = TotalRequestCost - AdjustmentPaymentTotal + TotalTaxAmount;
                request.BalanceDue = Math.Round(BalanceDue, 2);
                
                UserData.Instance.DefaultFacility = request.DefaultFacility;
                //for revert functionality
                defaultFacility = request.DefaultFacility;                
                oldSalesTaxStatus = request.HasSalesTax;
                
                 SortedList<string, RequestPatientDetails>tempPatientList = new SortedList<string, RequestPatientDetails>();
                 //for (int count = 0; count < request.Patients.Count; count++)
                 //{
                 //    tempPatientList.Add(request.Patients.Keys[count], request.Patients.Values[count]);
                 //}
                ReleaseDetails olddraftRelease = new ReleaseDetails();
                olddraftRelease = request.DraftRelease;
                TaxPerFacilityDetails tempDefaultFacility = new TaxPerFacilityDetails();
                tempDefaultFacility = request.DefaultFacility;
                double invoiceAutoAdjustment, invoiceBaseCharge;
                invoiceAutoAdjustment = request.InvoiceAutoAdjustment;
                invoiceBaseCharge = request.InvoiceBaseCharge;                
                request = RequestController.Instance.UpdateRequest(request);
                request.InvoiceAutoAdjustment = invoiceAutoAdjustment;
                request.InvoiceBaseCharge = invoiceBaseCharge;
                request.DefaultFacility = tempDefaultFacility;
                request.DraftRelease = olddraftRelease;
                bool isFirstPatient = true;
                Application.DoEvents();

                if(requestPatients ==null)
                    requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);

                foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                {
                    if (!request.Patients.ContainsKey(requestPatientDetails.Key))
                        request.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                    else
                    {
                        log.Info("UPMC - found duplicate key while saving the billing location - " + requestPatientDetails.Key);
                    }

                }
                for (int count = 0;count<tempPatientList.Count;count++)
                {
                    //request.Patients.Add(tempPatientList.Keys[count], tempPatientList.Values[count]);
                    if (isFirstPatient)
                    {
                        request.PatientNames = tempPatientList.Values[count].Name;
                        isFirstPatient = false;
                    }
                    else
                    {
                        request.PatientNames += (":" + tempPatientList.Values[count].Name);
                    }
                }
                request.HasSalesTax = oldSalesTaxStatus;
				// CR#365397
                if (defaultFacility != null && !string.IsNullOrEmpty(defaultFacility.Name) && !request.IsInvoiced)
                {
                    request.DefaultFacility = defaultFacility;
                }

                RequestEvents.OnRequestUpdated(Pane, new ApplicationEventArgs(request, this));                                
                request.NewTransactions.Clear();

                // CR# 359257.
                salesTaxSummaryUI.IsGridItemChanged = false;
                IsApplyTaxStateChanged = false;
                bool hasDocuments = false;
                if (release.ROIPages.Count > 0 || release.SupplementalAttachmentsSeqField.Count > 0 ||
                    release.SupplementalDocumentsSeqField.Count > 0 || release.SupplementarityAttachmentsSeqField.Count > 0 ||
                    release.SupplementarityDocumentsSeqField.Count > 0)
                {
                    hasDocuments = true;
                }
                documentChargeUI.SetData((Collection<DocumentChargeDetails>)ROIViewUtility.DeepClone(release.DocumentCharges), hasDocuments);
                feeChargeUI.SetData(release, false, request.HasDraftRelease);

                BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
                billingEditor.Release = release;
                billingEditor.Request = request;
                
                RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
                rsp.InfoEditor.Request = request;
                if (rsp.PatientInfoEditor != null)
                {
                    rsp.PatientInfoEditor.Request = request;
                    ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).UpdateRequestInfo(request);
                }
                                
                if (shippingInfoUI.AddressSelected == false)
                {
                    shippingInfoUI.SetData(release.ShippingDetails, countryCodeDetails);
                }

                DisableControlsForReleasedRequest();
                isDirty = false;
                doSave = false;

                EnableButtons(false);

                EnablePreBillButton(true);
                EnableReleaseButton(true);
                EnableInvoiceButton(true);

                release.IsUnbillable = IsUnbillable();
                IsReleaseEnable = billingPaymentActionUI.ReleaseButton.Enabled;
                IsReReleaseEnable = billingPaymentActionUI.RereleaseButton.Enabled;
                SaveAndBillInfo(release, request);
                //SaveInfo(release, request);                
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                return false;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return true;
        }

        private bool IsUnbillable()
        {
            return UnbillablecheckBox.Checked;
        }



        /// <summary>
        /// Disable the charges controls while performing save immediately after release
        /// </summary>
        private void DisableControlsForReleasedRequest()
        {
            if (!salesTaxSummaryUI.IsEnabled && !shippingInfoUI.IsEnabled)
            {
                DisableBillingInfo();
            }
        }

        /// <summary>
        /// Method to create audit & event message for change of sales tax state and billing location.
        /// </summary>
        /// <param name="auditmessage"></param>
        /// <param name="requestId"></param>
        /// <param name="isSalesTaxOverride"></param>
        private void createSalesTaxAuditEventMessage(string auditmessage,long requestId, bool isSalesTaxOverride)
        {           

            //create the audit log in audit table
            AuditEvent auditEvent = new AuditEvent();

            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvent.EventId = 1;
            auditEvent.Facility = ROIConstants.FacilityName;
            auditEvent.ActionCode = ROIConstants.RequestModificationActionCode;
            auditEvent.Comment = auditmessage.ToString();
            try
            {
                Application.DoEvents();
                ROIController.Instance.CreateAuditEntry(auditEvent);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
            }

            //create the event log in event table
            CommentDetails details = new CommentDetails();
            details.RequestId = requestId;
            details.EventType = (isSalesTaxOverride) ? EventType.SalesTaxChanges : EventType.ChangeOfBillingLocation;
            details.EventRemarks = auditmessage.ToString();
            Application.DoEvents();
            RequestController.Instance.CreateComment(details);
        }
        
        /// <summary>
        /// Occurs when user click save button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            Save();
        }


        private void Release(EventType eventType)
        {
            try
            {
                errorProvider.Clear();
                Application.DoEvents();
                ROIViewUtility.MarkBusy(true);
                if (doSave)
                {
                    Save();
                }
                releaseDialog= new ReleaseDialog(Pane, this);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                Form dialog = ROIViewUtility.ConvertToForm(null,
                                                           rm.GetString("title." + releaseDialog.Name),
                                                           releaseDialog);

                Collection<LetterTemplateDetails> letterTemplateList = ROIAdminController.Instance.RetrieveAllLetterTemplates();

                IList<LetterTemplateDetails> coverLetterTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, LetterType.CoverLetter.ToString());
                IList<LetterTemplateDetails> invoiceTemplates = ROIViewUtility. RetrieveLetterTemplates(letterTemplateList, LetterType.Invoice.ToString());
                IList<LetterTemplateDetails> requestorLetterTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, LetterType.RequestorStatement.ToString());

                long defaultCoverLetterId = ROIViewUtility.RetrieveDefaultId(coverLetterTemplates);
                long defaultInvoiceId = ROIViewUtility.RetrieveDefaultId(invoiceTemplates);
                long defaultRequestorLetterID = ROIViewUtility.RetrieveDefaultId(requestorLetterTemplates);

                PreBillInvoiceDetails preBillInvoiceDetails = CreatePreBillInvoiceDetails(0, string.Empty, new Collection<NotesDetails>(), new Collection<string>(), string.Empty, 0, false, true);

                DestinationType destinationType = RetrieveDestinationType();
                releaseDialog.PrePopulate(coverLetterTemplates, requestorLetterTemplates, invoiceTemplates, defaultCoverLetterId, defaultRequestorLetterID,
                                          defaultInvoiceId, release.TotalPages, preBillInvoiceDetails,
                                          destinationType, request, eventType,
                                          billingLocationComboBox.SelectedIndex > 0, release, request.DefaultFacility.Code as String, request.DefaultFacility.Name);
                if (shippingInfoUI.IsDisc)
                {
                    IsOnlyNonHPFDocuments(request);
                }
                DialogResult result = dialog.ShowDialog();

                if (result == DialogResult.OK)
                {
                    CommentDetails details = null;
                    Collection<RequestPartDetails> nonPrintableAttachmentReqParts = null;
                    // Start Non-Printable attachment file dialog
                    if ((shippingInfoUI.IsPrint || shippingInfoUI.IsFax) && request.RetrieveNonPrintableCount() > 0)
                    {
                        OutputRequestDetails outputNonPrintableRequest = new OutputRequestDetails(request.Id, release.Id,
                                                                         request.RequestSecretWord, request.ReceiptDate);

                        result = DialogResult.OK;
                        ROIViewer viewer = new ROIViewer(Pane, string.Empty, GetType().Name);
                        viewer.ReleaseDialog = true;
                        int selectedIndex = shippingInfoUI.NonPrinableComboBox.SelectedIndex;
                        selectedIndex = selectedIndex > 0 ? selectedIndex - 1 : selectedIndex;
                        result = viewer.ShowFileDialog(this, selectedIndex);

                        if (result == DialogResult.OK)
                        {
                            OutputPropertyDetails outputPropertyDetails = viewer.OutputPropertyDetails;

                            nonPrintableAttachmentReqParts = BuildAttachmentRequestPartDetails(release.ReleasedPatients, false);

                            outputNonPrintableRequest.OutputDestinationDetails = outputPropertyDetails.OutputDestinationDetails[0];
                            OutputViewDetails nonPrintableOutputViewDetails = outputPropertyDetails.OutputViewDetails;

                            //Queue and Request passwords will be updated after MarkAsRelease method call
                            if (!string.IsNullOrEmpty(outputNonPrintableRequest.OutputDestinationDetails.SecuredSecretWord))
                            {
                                release.QueueSecretWord = outputNonPrintableRequest.OutputDestinationDetails.SecuredSecretWord;
                            }
                            // Always file dialog
                            release.RequestSecretWord = request.RequestSecretWord;

                            foreach (RequestPartDetails reqPartDetail in nonPrintableAttachmentReqParts)
                            {
                                if (reqPartDetail.PropertyLists.Count > 0)
                                {
                                    outputNonPrintableRequest.RequestParts.Add(reqPartDetail);
                                }
                            }

                            if (outputNonPrintableRequest.RequestParts.Count > 0)
                            {
                                outputNonPrintableRequest.RequestParts.Add(BuildROIReleasePartDetails(releaseDialog.ReleaseAndPreviewDetails));

                                //set release property types to ATTACHMENT ONLY
                                foreach (RequestPartDetails reqPartDetail in outputNonPrintableRequest.RequestParts)
                                {
                                    if (OutputPropertyDetails.ReleaseContentSource.Equals(reqPartDetail.ContentSource))
                                    {
                                        PropertyDetails tmpPropertyDetail = reqPartDetail.PropertyLists[0];
                                        tmpPropertyDetail.FileTypes = OutputPropertyDetails.AttachmentContentSource;
                                    }
                                }

                                Application.DoEvents();
                                long jobStatus = OutputController.Instance.SubmitOutputRequest(outputNonPrintableRequest, DestinationType.File,
                                                                                         nonPrintableOutputViewDetails, true);

                                //Eventlog for Release and ReRelease operation

                                details = new CommentDetails();

                                details.RequestId = request.Id;
                                details.EventType = eventType;

                                if (eventType == EventType.DocumentsReleased)
                                {
                                    details.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.ReleasedEventmessage,
                                                           release.ReleasedPatients.Count, nonPrintableAttachmentCount, DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
                                }
                                else if (eventType == EventType.DocumentsRereleased)
                                {
                                    details.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.RereleasedEventmessage,
                                                                         release.ReleasedPatients.Count, nonPrintableAttachmentCount, DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
                                }

                                Application.DoEvents();
                                RequestController.Instance.CreateComment(details);
                            }
                        }
                    }
                    OutputRequestDetails outputRequestDetails;
                    OutputRequestDetails outputRequestDetailsForDisc = null;
                    // End of Non-Printable attachment file dialog
                    if (shippingInfoUI.IsDisc)
                    {
                        IsOnlyNonHPFDocuments(request);
                        if (!isOnlyNonHPFDocuments)
                        {
                            outputRequestDetailsForDisc = BuildRequestPart(true, false);
                            //RequestPartDetails requestPart = BuildRequestPartDetails(release.ReleasedPatients, releaseDialog.OutputPropertyDetailsForDisc, request, release);
                            //outputRequestDetailsForDisc.RequestParts.Add(requestPart);
                        }
                        outputRequestDetails = BuildRequestPart(false, true);
                    }
                    else
                    {

                        outputRequestDetails = BuildRequestPart(false, false);
                    }
                    string actionCode = RetrieveActionCode(outputRequestDetails.OutputDestinationDetails.Type);
                    string actionCodeForDisc = string.Empty;
                    if (shippingInfoUI.IsDisc)
                    if (shippingInfoUI.IsDisc && !isOnlyNonHPFDocuments)
                    {
                        actionCodeForDisc = RetrieveActionCode(outputRequestDetailsForDisc.OutputDestinationDetails.Type);
                    }

                    long invoiceId = 0;
                    if (outputRequestDetails.RequestParts.Count > 0)
                    {
                        
                        if (releaseDialog.ReleaseAndPreviewDetails != null)
                        {
                            if (releaseDialog.ReleaseAndPreviewDetails.docInfoList != null)
                            {
                                foreach (DocInfo documentInfo in releaseDialog.ReleaseAndPreviewDetails.docInfoList.docInfos)
                                {
                                    if (string.Compare(documentInfo.type, LetterType.Invoice.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                                    {
                                        invoiceId = documentInfo.id;
                                    }
                                }
                            }
                        }

                        //DO NOT ADD ROI INFO TO OUTPUTREQUESTDETAILS IF OUTPUT IS DISC (Invoice, CoverLetter, etc)
                        RequestPartDetails roiReleasePartDetails = BuildROIReleasePartDetails(releaseDialog.ReleaseAndPreviewDetails);
                        if (shippingInfoUI.IsDisc)
                        {
                            outputRequestDetailsForDisc.RequestParts.Add(roiReleasePartDetails);    
                        }
                        else
                        {
                            outputRequestDetails.RequestParts.Add(roiReleasePartDetails);
                        }
                        
                        try
                        {
                            Application.DoEvents();
                            long jobStatus = OutputController.Instance.SubmitOutputRequest(outputRequestDetails, RetrieveDestinationType(),
                                                                                         outputViewDetails, true);

                            if (shippingInfoUI.IsDisc)
                            {
                                if (!isOnlyNonHPFDocuments)
                                {
                                    Application.DoEvents();
                                    jobStatus = OutputController.Instance.SubmitOutputRequest(outputRequestDetailsForDisc, RetrieveDestinationType(),
                                                                                             outputViewDetails, true);
                                }
                            }
                            releaseDialog.CreateInvoiceEvent();

                            if (releaseDialog.IsInvoiced)
                            {
                                long[] invoiceIdArr = new long[] { invoiceId };
                                CreateInvoiceAuditEvent(invoiceIdArr, request.Id, false);
                            }

                            if (releaseDialog.IsPastInvoice)
                            {
                                CreateInvoiceAuditEvent(releaseDialog.RetrieveAllSelectedPastInvoices(InvoiceIdColumn).ToArray(), request.Id, true);
                            }

                            if (jobStatus == -200)
                            {
                                ShowReleaseUnsuccessfulDialog();
                                return;
                            }
                        }
                        catch (ROIException cause)
                        {
                            ROIViewUtility.Handle(Context, cause);
                            if (releaseDialog.ReleaseAndPreviewDetails != null && releaseDialog.ReleaseAndPreviewDetails.docInfoList != null)
                            {
                                ReleaseDialog.CancelRelease(releaseDialog.ReleaseAndPreviewDetails);
                            }
                            return;
                        }
                    }
                    /*try
                    {
                        if (releaseDialog.AppliedAmount > 0)
                        {
                            BillingController.Instance.CreateAuditAndEventsForAutoApplyToInvoice(request.Id, invoiceId);
                        }
                    }
                    catch (ROIException cause)
                    {
                        ROIViewUtility.Handle(Context, cause);
                    }*/
                    foreach (DocInfo docInfo in releaseDialog.ReleaseAndPreviewDetails.docInfoList.docInfos)
                    {
                        if (docInfo.name.ToLower().Contains("invoice"))
                        {
                            CreateInvoiceAuditEvent(docInfo, actionCode);
                        }
                    }

                    //Audit the Release and Rerelease event

                    AuditEvent auditEvent = new AuditEvent();

                    auditEvent.UserId = UserData.Instance.UserInstanceId;
                    auditEvent.EventStart = System.DateTime.Now;
                    auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                    auditEvent.EventId = 1;
                    auditEvent.Facility = ROIConstants.FacilityName;

                    string faxNumber = string.Empty;

                    if (outputRequestDetails.OutputDestinationDetails.Type.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == OutputMethod.Fax.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
                    {
                        faxNumber = outputRequestDetails.OutputDestinationDetails.Fax;
                    }

                    //CR#365598
                    List<AuditEvent> auditEvents = new List<AuditEvent>();
                    auditEvents = PrepareAuditEvent(release.ReleasedPatients, faxNumber, eventType,
                                                    outputRequestDetails.OutputDestinationDetails.Type,
                                                    outputRequestDetails.OutputDestinationDetails.Name,
                                                    outputRequestDetails.OutputDestinationDetails.EmailAddr,
                                                    request.Id, actionCode, releaseDialog.IsInvoiced,
                                                    (outputRequestDetailsForDisc != null) ? outputRequestDetailsForDisc.OutputDestinationDetails.Name :
                                                                                          string.Empty,
                                                    actionCodeForDisc, shippingInfoUI.IsDisc,
                                                    (outputRequestDetailsForDisc != null) ? outputRequestDetailsForDisc.OutputDestinationDetails.DiscType :
                                                                                            string.Empty,
                                                    (outputRequestDetailsForDisc != null) ? outputRequestDetailsForDisc.OutputDestinationDetails.TemplateName :
                                                                                            string.Empty);

                    if (auditEvents != null)
                    {
                        if (auditEvents.Count > 0)
                        {
                    try
                    {
                        Application.DoEvents();
                        ROIController.Instance.CreateAuditEntryList(auditEvents);
                    }
                    catch (ROIException cause)
                    {
                        log.FunctionFailure(cause);
                            }
                        }
                    }

                    if (outputRequestDetails.RequestParts.Count > 0)
                    {
                        //Eventlog for Release and Rerelease operation

                        details = new CommentDetails();

                        details.RequestId = request.Id;
                        details.EventType = eventType;

                        int totalReleasedPages = 0;

                        if (shippingInfoUI.IsFile)
                        {
                            totalReleasedPages = release.TotalPages;
                        }
                        else
                        {
                            totalReleasedPages = release.TotalPages - nonPrintableAttachmentCount;
                        }
                            if (eventType == EventType.DocumentsReleased)
                            {
                                details.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.ReleasedEventmessage,
                                    request.ReleasedItems.Count, totalReleasedPages, (shippingInfoUI.IsDisc) ?
                                    ((outputRequestDetailsForDisc != null) ? outputRequestDetailsForDisc.OutputDestinationDetails.Type : "DISC") 
                                    : outputRequestDetails.OutputDestinationDetails.Type);
                            }
                            else if (eventType == EventType.DocumentsRereleased)
                            {
                                details.EventRemarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.RereleasedEventmessage,
                                                                     request.ReleasedItems.Count, totalReleasedPages, (shippingInfoUI.IsDisc) ?
                                    ((outputRequestDetailsForDisc != null) ? outputRequestDetailsForDisc.OutputDestinationDetails.Type : "DISC") 
                                    : outputRequestDetails.OutputDestinationDetails.Type);
                            }


                            Application.DoEvents();
                            RequestController.Instance.CreateComment(details);

						if (release.RequestSecretWord == null)
							release.RequestSecretWord = "";
                        RequestDetails req = request;



                    }

                    if (eventType == EventType.DocumentsReleased && !releaseDialog.IsInvoiced)
                    {
                        release.TotalPagesReleased = release.TotalPages + previouslyReleasedPages;
                    }
                    else if (eventType == EventType.DocumentsRereleased)
                    {
                        release.TotalPagesReleased = previouslyReleasedPages;
                    }
					//CR#359276 - Set this to zero after invoice has created
                    request.InvoiceAutoAdjustment = request.InvoiceBaseCharge = 0;
                    release.InvoicesBalanceDue += preBillInvoiceDetails.Invoice.BalanceDue;
                    billingLocationComboBox.Enabled = false;
                    UnbillablecheckBox.Enabled = false;
                    MarkAsRelease(releaseDialog.RequestStatus, releaseDialog.IsInvoiced, preBillInvoiceDetails.Invoice.BaseCharge,
                                  preBillInvoiceDetails.Invoice.SalesTax);

 					//audit and event entry if invoice is selected together with release operation
                    if (releaseDialog.IsInvoiced && releaseDialog.IsOverWriteDueDays)
                    {
                        string comment = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.OverwriteInvoiceDueDaysMessage,
                                                           releaseDialog.OldDueDays, releaseDialog.DueDays);
                        auditEvent.Comment = comment;
                        auditEvent.ActionCode = ROIConstants.RequestModificationActionCode;

                        try
                        {
                            Application.DoEvents();
                            ROIController.Instance.CreateAuditEntry(auditEvent);
                        }
                        catch (ROIException cause)
                        {
                            log.FunctionFailure(cause);
                        }

                        details.EventType = EventType.OverwriteInvoiceDueDays;
                        details.EventRemarks = comment;
                        Application.DoEvents();
                        RequestController.Instance.CreateComment(details);
                    }
                    release.InvoicesSalesTaxAmount += salesTaxSummaryUI.TotalTaxAmount;
                    isDirty = false;
                    EnableButtons(false);
                    //EnablePreBillButton(false);
                    EnableReleaseButton(false);
                    EnableInvoiceButton(false);

                    DisableBillingInfo();                    

                    UpdatePendingReleaseCost();

                    newChargeTaxValueLabel.Text = ((PendingReleaseCost == 0.0) ? ReleaseDetails.FormattedAmount(0.0) :
                                                  ReleaseDetails.FormattedAmount(salesTaxSummaryUI.TotalTaxAmount)) + "   )";
                }
                ApplicationEventArgs ae = new ApplicationEventArgs(request.Requestor, this);
                RequestEvents.OnAccountManagementGridRefresh(Pane, ae);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                OutputFileDialog.CloseSplashScreen();
                OutputPrintDialog.CloseSplashScreen();
                OutputFaxDialog.CloseSplashScreen();
                OutputEmailDialog.CloseSplashScreen();
                OutputDiscDialog.CloseSplashScreen();
                ROIViewUtility.MarkBusy(false);
            }
        }

        private OutputRequestDetails BuildRequestPart(bool isDisc, bool isPrint)
        {
            OutputRequestDetails outputRequestDetails = new OutputRequestDetails(request.Id, releaseDialog.ReleaseAndPreviewDetails.releaseId,
                                                                request.RequestSecretWord, request.ReceiptDate);

                    outputRequestDetails.OutputDestinationDetails = releaseDialog.OutputPropertyDetails.OutputDestinationDetails[0];
                    if (isDisc) outputRequestDetails.OutputDestinationDetails = releaseDialog.OutputPropertyDetailsForDisc.OutputDestinationDetails[0];
                    outputViewDetails = releaseDialog.OutputPropertyDetails.OutputViewDetails;

                    //Queue and Request passwords will be updated after MarkAsRelease method call
                    if (!string.IsNullOrEmpty(outputRequestDetails.OutputDestinationDetails.SecuredSecretWord))
                    {
                        release.QueueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                    }
                    if (destinationType == DestinationType.File)
                    {
                        release.RequestSecretWord = request.RequestSecretWord;
                    }

                    RequestPartDetails requestPartDetails = null;
                    if (!isDisc)
                    {
                        if (releaseDialog.ReleaseAndPreviewDetails.docInfoList != null)
                        {
                            requestPartDetails = BuildROIRequestPartDetails(releaseDialog.ReleaseAndPreviewDetails, releaseDialog.DocId, request.Requestor.Id,
                                                                             request.Requestor.TypeName, request.Requestor.Name);
                            outputRequestDetails.RequestParts.Add(requestPartDetails);
                        }
                    }
                    RequestPartDetails requestPartDetails1 = null;
                    if (!isPrint)
                    {
                        requestPartDetails1 = BuildHpfRequestPartDetails(release.ReleasedPatients);
                    }
                    if (requestPartDetails1 != null)
                    if (requestPartDetails1.PropertyLists.Count > 0)
                    {
                        outputRequestDetails.RequestParts.Add(requestPartDetails1);
                    }

                    //Add Attachments to output request
                    if (!isPrint)
                    {
                        Collection<RequestPartDetails> attachmentReqParts =
                        BuildAttachmentRequestPartDetails(release.ReleasedPatients, true);
                        AddRequestPartToOutputRequest(attachmentReqParts, outputRequestDetails);

                        if ((shippingInfoUI.IsFile || (shippingInfoUI.IsEmail) || (shippingInfoUI.IsDisc && isDisc)) && request.RetrieveNonPrintableCount() > 0)
                        {
                            attachmentReqParts = BuildAttachmentRequestPartDetails(release.ReleasedPatients, false);
                            AddRequestPartToOutputRequest(attachmentReqParts, outputRequestDetails);
                        }
                    }

                    if (isDisc)
                    {
                         RequestPartDetails discRequestPart = BuildRequestPartDetails(release.ReleasedPatients, releaseDialog.OutputPropertyDetails, request, release);
                         outputRequestDetails.RequestParts.Add(discRequestPart);
                    }

                    return outputRequestDetails;
        }

        /// <summary>
        /// Method to Build Request Parts
        /// </summary>
        /// <returns></returns>
        public RequestPartDetails BuildRequestPartDetails(SortedList<string, ReleasedPatientDetails> releasedPatients, 
                                                          OutputPropertyDetails outputPropertyDetailsForDisc, RequestDetails request, 
                                                          ReleaseDetails release)
        {
            int patientCount = 0;
            RequestPartDetails requestPartDetails = new RequestPartDetails();
            requestPartDetails.ContentId = string.Empty;
            requestPartDetails.ContentSource = OutputPropertyDetails.RequestContentSource;
            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.RequestID = request.Id;
            propertyDetails.RequestorName = request.Requestor.Name;
            propertyDetails.RequestCreated = request.DateCreated;
            propertyDetails.RequestCompleted = DateTime.Now.ToString("MM/dd/yyyy HH:mm", DateTimeFormatInfo.InvariantInfo);
            propertyDetails.OutputNotes = outputPropertyDetailsForDisc.OutputDestinationDetails[0].OutputNotes;
            propertyDetails.TotalPageCount = release.TotalPages;

            propertyDetails.EncounterCount = 0;
            propertyDetails.Encounter = "";

            foreach (RequestPatientDetails patient in request.ReleasedItems.Values)
            {
                bool isNewPatient = true;


                foreach (RequestAttachmentEncounterDetails attachment in patient.Attachment.GetChildren)
                {
                    //if (attachment.SelectedForRelease.Value)
                    //{
                        if (isNewPatient)
                        {
                            patientCount++;
                            PropertyDetails tempPropertyDetails = GetPropertyDetails(patient, outputPropertyDetailsForDisc);            
                            propertyDetails.PatientName = propertyDetails.PatientName + tempPropertyDetails.PatientName;
                            propertyDetails.DOB = propertyDetails.DOB + tempPropertyDetails.DOB;
                            propertyDetails.MRN = propertyDetails.MRN + tempPropertyDetails.MRN;
                        }
                        if (!string.IsNullOrEmpty(attachment.Encounter) && !propertyDetails.Encounter.Contains(attachment.Encounter + ","))
                        {
                            propertyDetails.Encounter = propertyDetails.Encounter + attachment.Encounter + ", ";
                            propertyDetails.AdmitDate = propertyDetails.AdmitDate + ROIConstants.notApplicable + ", ";
                            propertyDetails.DischargeDate = propertyDetails.DischargeDate + ROIConstants.notApplicable + ", ";
                            propertyDetails.PatientType = propertyDetails.PatientType + ROIConstants.notApplicable + ", ";
                            propertyDetails.EncounterCount++;
                        }

                    //}
                    isNewPatient = false;
                }

                IList<BaseRequestItem> encounters = patient.GetChildren;
                
                foreach (RequestEncounterDetails enc in encounters)
                {
                    //if (enc.SelectedForRelease.Value)
                    //{
                        if (isNewPatient)
                        {
                            patientCount++;
                            PropertyDetails tempPropertyDetails = GetPropertyDetails(patient, outputPropertyDetailsForDisc);
                            propertyDetails.PatientName = propertyDetails.PatientName + tempPropertyDetails.PatientName;
                            propertyDetails.DOB = propertyDetails.DOB + tempPropertyDetails.DOB;
                            propertyDetails.MRN = propertyDetails.MRN + tempPropertyDetails.MRN;
                        }
                        isNewPatient = false;

                        if (!string.IsNullOrEmpty(enc.EncounterId) && !propertyDetails.Encounter.Contains(enc.EncounterId + ","))
                        {
                            propertyDetails.Encounter = propertyDetails.Encounter + enc.EncounterId + ", ";

                            if (enc.AdmitDate.HasValue)
                            {
                                string admitDate = Convert.ToDateTime(enc.AdmitDate).ToString("MM/dd/yyyy", CultureInfo.InvariantCulture);
                                propertyDetails.AdmitDate = propertyDetails.AdmitDate + admitDate + ", ";
                            }
                            else
                            {
                                propertyDetails.AdmitDate = propertyDetails.AdmitDate + ROIConstants.notApplicable + ", ";
                            }
                           
                            if (enc.DischargeDate.HasValue)
                            {
                                string dischargeDate = Convert.ToDateTime(enc.DischargeDate).ToString("MM/dd/yyyy", CultureInfo.InvariantCulture);
                                propertyDetails.DischargeDate = propertyDetails.DischargeDate + dischargeDate + ", ";
                            }
                            else
                            {
                                propertyDetails.DischargeDate = propertyDetails.DischargeDate + ROIConstants.notApplicable + ", ";
                            }

                            if (!string.IsNullOrEmpty(enc.PatientType))
                                propertyDetails.PatientType = propertyDetails.PatientType + enc.PatientType + ", ";
                            else
                                propertyDetails.PatientType = propertyDetails.PatientType + ROIConstants.notApplicable + ", ";

                            propertyDetails.EncounterCount++;
                        }
                    //}
                }
                foreach (RequestDocumentDetails globalDocuments in patient.GlobalDocument.GetChildren)
                {
                    //if (globalDocuments.SelectedForRelease.Value)
                    //{
                        if (isNewPatient)
                        {
                            patientCount++;
                            PropertyDetails tempPropertyDetails = GetPropertyDetails(patient, outputPropertyDetailsForDisc);
                            propertyDetails.PatientName = propertyDetails.PatientName + tempPropertyDetails.PatientName;
                            propertyDetails.DOB = propertyDetails.DOB + tempPropertyDetails.DOB;
                            propertyDetails.MRN = propertyDetails.MRN + tempPropertyDetails.MRN;
                        }

                        isNewPatient = false;
                    //}
                }

                foreach (RequestNonHpfEncounterDetails nonHPFDocuments in patient.NonHpfDocument.GetChildren)
                {
                    if (!string.IsNullOrEmpty(nonHPFDocuments.Encounter) && !propertyDetails.Encounter.Contains(nonHPFDocuments.Encounter + ","))
                    {
                        propertyDetails.Encounter = propertyDetails.Encounter + nonHPFDocuments.Encounter + ", ";
                        propertyDetails.AdmitDate = propertyDetails.AdmitDate + ROIConstants.notApplicable + ", ";
                        propertyDetails.DischargeDate = propertyDetails.DischargeDate + ROIConstants.notApplicable + ", ";
                        propertyDetails.PatientType = propertyDetails.PatientType + ROIConstants.notApplicable + ", ";
                        propertyDetails.EncounterCount++;
                    }
                }
            }
            propertyDetails.PatientName = !string.IsNullOrEmpty(propertyDetails.PatientName) ? propertyDetails.PatientName.Substring(0, propertyDetails.PatientName.Length - 2) : 
                                                                                                string.Empty;
            propertyDetails.DOB = !string.IsNullOrEmpty(propertyDetails.DOB) ? propertyDetails.DOB.Substring(0, propertyDetails.DOB.Length - 2) :
                                                                                            string.Empty;

            propertyDetails.MRN = !string.IsNullOrEmpty(propertyDetails.MRN) ? propertyDetails.MRN.Substring(0, propertyDetails.MRN.Length - 2) :
                                                                                string.Empty;
            propertyDetails.Encounter = !string.IsNullOrEmpty(propertyDetails.Encounter) ? propertyDetails.Encounter.Substring(0, propertyDetails.Encounter.Length - 2) :
                                                                                string.Empty;

            propertyDetails.AdmitDate = !string.IsNullOrEmpty(propertyDetails.AdmitDate) ? propertyDetails.AdmitDate.Substring(0, propertyDetails.AdmitDate.Length - 2) :
                                                                                            string.Empty;


            propertyDetails.DischargeDate = !string.IsNullOrEmpty(propertyDetails.DischargeDate) ? propertyDetails.DischargeDate.Substring(0, propertyDetails.DischargeDate.Length - 2) :
                                                                                            string.Empty;

            propertyDetails.PatientType = !string.IsNullOrEmpty(propertyDetails.PatientType) ? propertyDetails.PatientType.Substring(0, propertyDetails.PatientType.Length - 2) :
                                                                                string.Empty;
            propertyDetails.PatientCount = patientCount;
            
            requestPartDetails.PropertyLists.Add(propertyDetails);
            return requestPartDetails;
        }

        /// <summary>
        /// Mehtod to Display Maksed MRN
        /// </summary>
        /// <param name="mrn"></param>
        /// <param name="maskedLength"></param>
        /// <returns></returns>
        private string MRNMasking(string mrn, int maskedLength)
        {
            string maskedMRN;
            int mrnLength = mrn.Length;
            if (maskedLength >= mrnLength)
            {
                maskedMRN = new string('*', mrnLength);
            }
            else
            {
                string maskedValue = mrn.Substring(0, maskedLength);
                string unMaskedValue = mrn.Substring(maskedLength, (mrnLength - maskedLength));
                string maskedString = new string('*', maskedValue.Length);
                maskedMRN = maskedString + unMaskedValue;
            }
            return maskedMRN;
        }

        /// <summary>
        /// Retrieve the property details
        /// </summary>
        /// <param name="releasedPatient"></param>
        /// <param name="outputPropertyDetailsForDisc"></param>
        /// <returns></returns>
        private PropertyDetails GetPropertyDetails(RequestPatientDetails requestPatient, OutputPropertyDetails outputPropertyDetailsForDisc)
        {
            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.PatientName = requestPatient.LastName + " " + requestPatient.FirstName + ", ";
            if (requestPatient.DOB.HasValue)
            {
                string dob = Convert.ToDateTime(requestPatient.DOB).ToString("MM/dd/yyyy", CultureInfo.InvariantCulture);
                propertyDetails.DOB = dob + ", ";
            }
            if (!string.IsNullOrEmpty(requestPatient.MRN))
            {
                propertyDetails.MRN = requestPatient.MRN + ", ";
                if (outputPropertyDetailsForDisc.OutputViewDetails.IsMRNMasking)
                {
                    if (outputPropertyDetailsForDisc.OutputViewDetails.MRNMaskingValue > 0)
                    {
                        propertyDetails.MRN = MRNMasking(requestPatient.MRN,
                                              outputPropertyDetailsForDisc.OutputViewDetails.MRNMaskingValue) + ", ";
                    }
                }
            }
            return propertyDetails;
        }

        /// <summary>
        /// Retrieve the encounter count
        /// </summary>
        /// <param name="releasedPatients"></param>
        /// <returns></returns>
        private int GetEncounterCount(SortedList<string, ReleasedPatientDetails> releasedPatients)
        {
            int encounterCount = 0;
            foreach (ReleasedPatientDetails releasedPatient in releasedPatients.Values)
            {
                IList<BaseRequestItem> encounters = releasedPatient.GetChildren;
                foreach (RequestEncounterDetails enc in encounters)
                {
                    if (enc.SelectedForRelease.Value)
                    {
                        encounterCount++;
                    }
                }
                foreach (RequestDocumentDetails globalDocument in releasedPatient.GlobalDocument.GetChildren)
                {
                    if (globalDocument.SelectedForRelease.Value)
                    {
                        encounterCount++;
                    }
                }
            }
            return encounterCount; 
        }

        /// <summary>
        /// Method to check the whether the released patients contains only NonHPFDocuments
        /// </summary>
        /// <param name="releasedPatients"></param>
        /// <returns></returns>
        private bool IsOnlyNonHPFDocuments(RequestDetails requestDetails)
        {
            int nonHPFDocumentCount = 0;
            int encounterDocumentCount = 0;
            int attachmentCount = 0;
            int globalDocumentCount = 0;
            foreach (RequestPatientDetails patient in requestDetails.ReleasedItems.Values)
            {
                attachmentCount += patient.Attachment.GetChildren.Count;
                globalDocumentCount += patient.GlobalDocument.GetChildren.Count;
                nonHPFDocumentCount += patient.NonHpfDocument.GetChildren.Count;
                encounterDocumentCount += patient.GetChildren.Count;
            }
            if (nonHPFDocumentCount != 0 && encounterDocumentCount == 0 && attachmentCount == 0 && globalDocumentCount == 0)
            {
                isOnlyNonHPFDocuments = true;
            }
            else
            {
                isOnlyNonHPFDocuments = false;
            }
            return isOnlyNonHPFDocuments;
        }
        /// <summary>
        /// Method to generate Invoice Audit Event
        /// </summary>
        /// <param name="documentInfo"></param>
        /// <param name="actionCode"></param>
        private void CreateInvoiceAuditEvent(DocInfo documentInfo, string actionCode)
        {
            AuditEvent auditEvent = new AuditEvent();

            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvent.EventId = 1;
            auditEvent.Comment = documentInfo.name + "," + documentInfo.id + " for request " + request.Id;
            auditEvent.ActionCode = actionCode;

            try
            {
                Application.DoEvents();
                ROIController.Instance.CreateAuditEntry(auditEvent);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
            }
        }

        private void ShowErrorDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString("SOURCE_INVALID");
            DialogUI errorDialog = new DialogUI(messageText, Context, "invalidSource", string.Empty);
            errorDialog.ShowDialog();
        }

        /// <summary>
        /// Add request parts to output request
        /// </summary>
        /// <param name="attachmentReqParts"></param>
        /// <param name="outputRequestDetails"></param>
        public static void AddRequestPartToOutputRequest(Collection<RequestPartDetails> attachmentReqParts,
                                                         OutputRequestDetails outputRequestDetails)
        {
            foreach (RequestPartDetails reqPartDetail in attachmentReqParts)
            {
                if (reqPartDetail.PropertyLists.Count > 0)
                {
                    outputRequestDetails.RequestParts.Add(reqPartDetail);
                }
            }
        }

        /// <summary>
        /// Show unsuccessful output release dialog
        /// </summary>
        private void ShowReleaseUnsuccessfulDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString("releaseUnsuccessfulDialog.title");
            string okButtonText = rm.GetString("okButton.DialogUI");
            string messageText = rm.GetString("releaseUnsuccessfulDialog.MessageText");
            string okButtonToolTip = "";
            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }


        #region Audit message preparation

        //  CR#365598 - Audit event object creation logic is modified to support this CR requirements.
        /// <summary>
        /// Audit event object is created based on the following groups
        /// Global Documents
        /// Encounter Documents
        /// Non-HPF Documents
        /// </summary>
        /// <param name="releasedPatients"></param>
        /// <param name="faxNumber"></param>
        /// <param name="releaseType"></param>
        /// <param name="outputMethod"></param>
        /// <param name="queueName"></param>
        /// <param name="requestId"></param>
        /// <param name="actionCode"></param>
        /// <param name="isInvoiced"></param>
        /// <returns></returns>
        public static List<AuditEvent> PrepareAuditEvent(SortedList<string, ReleasedPatientDetails> releasedPatients, string faxNumber,
                                                         EventType releaseType, string outputMethod, string queueName, string emailAddress, long requestId,
                                                         string actionCode, bool isInvoiced, 
                                                         string queueNameForDisc,string actionCodeForDisc,bool isDisc, string discType, string templateName)
        {
            List<AuditEvent> auditEventList = new List<AuditEvent>();
            string mrn = string.Empty;
            string encounter = string.Empty;
            string facility = string.Empty;
            string nullValue = "NULL";
            bool hasPrint = false;
            AuditEvent auditEvent;

            if (releasedPatients != null)
            {
                string HPFPatientMessageHeader = string.Empty;
                string NonHPFPatientMessageHeader = string.Empty;
                string auditmessage = string.Empty;
            
                string messageHeaderForDisc = string.Empty;

                StringBuilder auditComment = new StringBuilder();
                StringBuilder HPFPatientAuditMessage = new StringBuilder();
                StringBuilder NonHPFPatientAuditMessage = new StringBuilder();

                if (releaseType == EventType.DocumentsReleased)
                {
                    HPFPatientMessageHeader = ((isInvoiced) && (!isDisc))? ROIConstants.ReleaseAuditMessageWithInvoice : ROIConstants.ReleaseAuditMessage;
                    NonHPFPatientMessageHeader = ROIConstants.ReleaseAuditMessage;
                    if (isDisc)
                        messageHeaderForDisc = ROIConstants.ReleaseAuditMessageForDisc;
                }
                else if (releaseType == EventType.DocumentsRereleased)
                {
                    HPFPatientMessageHeader = ((isInvoiced) && (!isDisc)) ? ROIConstants.RereleaseAuditMessageWithInvoice : ROIConstants.RereleaseAuditMessage;
                    NonHPFPatientMessageHeader = ROIConstants.RereleaseAuditMessage;
                    if (isDisc)
                        messageHeaderForDisc = ROIConstants.RereleaseAuditMessageForDisc;
                }
                else if (releaseType == EventType.DocumentsResend)
                {
                    HPFPatientMessageHeader = ROIConstants.ResendAuditMessage;
                    NonHPFPatientMessageHeader = ROIConstants.ResendAuditMessageForNonHpf;
                    if (isDisc)
                        messageHeaderForDisc = ROIConstants.ResendAuditMessageForDisc;
                }

                HPFPatientMessageHeader = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, HPFPatientMessageHeader, requestId, outputMethod, queueName);
                NonHPFPatientMessageHeader = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, NonHPFPatientMessageHeader, requestId, outputMethod, queueName);
                if (isDisc)
                {
                    templateName = (templateName == "None") ? "<none>" : templateName;
                    messageHeaderForDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, messageHeaderForDisc, requestId, queueNameForDisc, discType, templateName);
                }
                
                foreach (ReleasedPatientDetails releasedPatientDetails in releasedPatients.Values)
                {
                    int pages = 0;
                    //Global documents
                    foreach (RequestDocumentDetails doc in releasedPatientDetails.ReleasedItems.GlobalDocument.GetChildren)
                    {
                        foreach (RequestVersionDetails version in doc.GetChildren)
                        {
                            foreach (RequestPageDetails page in version.GetChildren)
                            {
                                pages++;
                            }
                        }
                    }
                    if (pages > 0)
                    {
                        if (!isDisc)
                        {
                            auditEvent = PrepareAuditEvent(actionCode, releasedPatientDetails.MRN, nullValue, releasedPatientDetails.FacilityCode);
                            auditEvent.Comment = CreateAuditComment(HPFPatientMessageHeader, pages, outputMethod, faxNumber, emailAddress);
                            auditEventList.Add(auditEvent);
                        } 
                        else
                        {
                            hasPrint = true;
                            auditEvent = PrepareAuditEvent(actionCodeForDisc, releasedPatientDetails.MRN, nullValue, releasedPatientDetails.FacilityCode);
                            string writtenToDisc;
                            
                            if (pages == 1) 
                                writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SinglePage, pages) + ROIConstants.WrittenToDisc;
                            else
                                writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.NumberOfPages, pages) + ROIConstants.WrittenToDisc;

                            auditEvent.Comment = messageHeaderForDisc + writtenToDisc;
                            auditEventList.Add(auditEvent);
                        }
                    }
                    pages = 0;

                    //HPF documents
                    foreach (RequestEncounterDetails enc in releasedPatientDetails.ReleasedItems.GetChildren)
                    {
                        pages = 0;
                        foreach (RequestDocumentDetails doc in enc.GetChildren)
                        {
                            foreach (RequestVersionDetails version in doc.GetChildren)
                            {
                                foreach (RequestPageDetails page in version.GetChildren)
                                {
                                    pages++;
                                }
                            }
                        }
                        if (pages > 0)
                        {
                            if (!isDisc)
                            {
                                auditEvent = PrepareAuditEvent(actionCode, releasedPatientDetails.MRN, enc.EncounterId, releasedPatientDetails.FacilityCode);
                                auditEvent.Comment = CreateAuditComment(HPFPatientMessageHeader, pages, outputMethod, faxNumber, emailAddress);
                                auditEventList.Add(auditEvent);
                            }

                            else
                            {
                                hasPrint = true;
                                auditEvent = PrepareAuditEvent(actionCodeForDisc, releasedPatientDetails.MRN, enc.EncounterId, releasedPatientDetails.FacilityCode);
                                string writtenToDisc;

                                if (pages == 1)
                                    writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SinglePage, pages) + ROIConstants.WrittenToDisc;
                                else
                                    writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.NumberOfPages, pages) + ROIConstants.WrittenToDisc;

                                auditEvent.Comment = messageHeaderForDisc + writtenToDisc;
                                auditEventList.Add(auditEvent);
                            }
                        }
                    }

                    pages = 0;

                    //NonHpf documents
                    foreach (RequestNonHpfDocumentDetails doc in releasedPatientDetails.ReleasedItems.NonHpfDocument.GetChildren)
                    {
                        if (doc.PageCount > 0)
                        {
                            string messageHeader = string.Empty;

                            messageHeader = (releasedPatientDetails.IsHpf) ? HPFPatientMessageHeader : NonHPFPatientMessageHeader;
                            mrn = (!string.IsNullOrEmpty(releasedPatientDetails.MRN)) ? releasedPatientDetails.MRN : nullValue;
                            encounter = (string.IsNullOrEmpty(doc.Encounter) || doc.Encounter.Equals("N/A")) ? nullValue : doc.Encounter;
                            facility = (!string.IsNullOrEmpty(releasedPatientDetails.FacilityCode)) ? releasedPatientDetails.FacilityCode : null;

                            if (!isDisc)
                            {
                                auditEvent = PrepareAuditEvent(actionCode, mrn, encounter, facility);
                                auditEvent.Comment = CreateAuditComment(messageHeader, doc.PageCount, outputMethod, faxNumber, emailAddress);
                                auditEventList.Add(auditEvent);
                            }
                            else
                            {
                                hasPrint = true;
                                auditEvent = PrepareAuditEvent(actionCodeForDisc, mrn, encounter, facility);

                                string writtenToDisc;

                                if (doc.PageCount == 1)
                                    writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SinglePage, doc.PageCount) + ROIConstants.WrittenToDisc;
                                else
                                    writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.NumberOfPages, doc.PageCount) + ROIConstants.WrittenToDisc;

                                auditEvent.Comment = messageHeaderForDisc + writtenToDisc;
                                auditEventList.Add(auditEvent);
                            }

                        }
                    }

                    pages = 0;
                    //Attachments
                    foreach (RequestAttachmentDetails tmpAttachment in releasedPatientDetails.Attachment.GetChildren)
                    {
                        pages = tmpAttachment.PageCount;
                        if (pages > 0)
                        {
                            if (!isDisc)
                            {
                                auditmessage = releasedPatientDetails.Name + "-"
                                                + releasedPatientDetails.EPN + "-"
                                                + releasedPatientDetails.MRN + "-"
                                                + tmpAttachment.Name;
                                string messageHeader = string.Empty;
                                messageHeader = (releasedPatientDetails.IsHpf) ? HPFPatientMessageHeader : NonHPFPatientMessageHeader;
                                mrn = (!string.IsNullOrEmpty(releasedPatientDetails.MRN)) ? releasedPatientDetails.MRN : nullValue;
                                encounter = (tmpAttachment.Name.Equals("N/A")) ? nullValue : tmpAttachment.Name;
                                facility = (!string.IsNullOrEmpty(releasedPatientDetails.FacilityCode)) ? releasedPatientDetails.FacilityCode : null;
                                auditEvent = PrepareAuditEvent(actionCode, mrn, encounter, facility);
                                //auditEvent.Comment = CreateAuditComment(messageHeader, pages, outputMethod, faxNumber, emailAddress);
                                auditEvent.Comment = messageHeader + auditmessage;
                                auditEventList.Add(auditEvent);
                            }
                            else
                            {
                                hasPrint = true;
                                auditEvent = PrepareAuditEvent(actionCodeForDisc, mrn, encounter, facility);

                                string writtenToDisc;

                                if (pages == 1)
                                    writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SinglePage, pages) + ROIConstants.WrittenToDisc;
                                else
                                    writtenToDisc = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.NumberOfPages, pages) + ROIConstants.WrittenToDisc;

                                auditEvent.Comment = messageHeaderForDisc + writtenToDisc;
                                auditEventList.Add(auditEvent);
                            }
                            pages = 0;
                        }
                    }

                    if (hasPrint && releaseType != EventType.DocumentsResend)
                    {
                        hasPrint = false;
                        auditEvent = PrepareAuditEvent(actionCode, releasedPatientDetails.MRN, nullValue, releasedPatientDetails.FacilityCode);
                        auditEvent.Comment = HPFPatientMessageHeader + ROIConstants.InvoiceGenerated;
                        auditEventList.Add(auditEvent);
                    }

                } // End of patient Looping


            } // End of IF condition.

            return auditEventList;
        }

        //  CR#365598
        /// <summary>
        /// This method is used to prepare audit object
        /// </summary>
        public static AuditEvent PrepareAuditEvent(string actionCode, string MRN, string Encounter, string Facility)
        {
            AuditEvent auditEvent = new AuditEvent();

            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvent.EventId = 1;
            auditEvent.ActionCode = actionCode;
            auditEvent.Mrn = MRN;
            auditEvent.Encounter = Encounter;
            auditEvent.Facility = Facility;

            return auditEvent;
        }

        //  CR#365598
        /// <summary>
        /// Method is used to Create audit comment while release/rerelease/resend the documents
        /// </summary>
        /// <param name="messageHeader">Audit message header information</param>
        /// <param name="pages">No.Of pages released ( Encounter/GlobalDocuments/Non-HPFDocuments )</param>
        /// <param name="outputMethod">Type of the output methods (Print/File/Fax )</param>
        /// <param name="faxNumber">fax number</param>
        /// <returns></returns>
        private static string CreateAuditComment(string messageHeader, int pages, string outputMethod, string faxNumber, string emailAddress)
        {
            StringBuilder auditComment = new StringBuilder();

            auditComment.Append(messageHeader);

            if (pages == 1)
            {
                auditComment.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SinglePage, pages));
            }
            else
            {
                auditComment.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.NumberOfPages, pages));
            }

            if (outputMethod.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == OutputMethod.Print.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
            {
                auditComment.Append(ROIConstants.Printed);
            }
            if (outputMethod.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == OutputMethod.Email.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
            {
                auditComment.Append(ROIConstants.Emailed + "to " + emailAddress + ".");
            }
            else if (outputMethod.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) == OutputMethod.Fax.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
            {
                auditComment.Append(ROIConstants.Faxed);
                if (!string.IsNullOrEmpty(faxNumber))
                {
                    string todayDate = System.DateTime.Today.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                    auditComment.Append(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.FaxedTo, todayDate, faxNumber));
                }
            }
            else
            {
                auditComment.Append(ROIConstants.ExportToPDF);
            }

            return auditComment.ToString();
        }

        public static string RetrieveActionCode(string outputMethod)
        {
            string actionCode = string.Empty;

            if (!string.IsNullOrEmpty(outputMethod))
            {
                if (string.Compare(outputMethod, DestinationType.Print.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                {
                    actionCode = ROIConstants.PrintActioncode;
                }
				// CR#359224
                else if (string.Compare(outputMethod, DestinationType.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                {
                    actionCode = ROIConstants.FaxActioncode;
                }
                else if (string.Compare(outputMethod, DestinationType.Email.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                {
                    actionCode = ROIConstants.EmailActioncode;
                }
                else if (string.Compare(outputMethod, DestinationType.Disc.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                {
                    actionCode = ROIConstants.DiscActionCode;
                }
                else
                {
                    actionCode = ROIConstants.FileActioncode;
                }
            }
            return actionCode;
        }


        #endregion

        /// <summary>
        /// Occurs when user click release button
        /// Mark the draft as release
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ReReleaseButton_Click(object sender, EventArgs e)
        {
            if (CheckSelfPayEncounter())
            {
                if (ShowConfirmReleaseDialog())
                {
                    Release(EventType.DocumentsRereleased);
                }
            }
            else
            {
                Release(EventType.DocumentsRereleased);
            }
        }

        private void ReleaseButton_Click(object sender, EventArgs e)
        {
            if (CheckSelfPayEncounter())
            {
                if (ShowConfirmReleaseDialog())
                {
                    Release(EventType.DocumentsReleased);
                }
            }
            else
            {
                Release(EventType.DocumentsReleased);
            }
            
        }

        //CR#367152 - Reset the total pages and release count values
        private void ReviseBillingTierTotalPages()
        {
            foreach (DocumentChargeDetails docCharge in release.DocumentCharges)
            {
                docCharge.TotalPages += (docCharge.Pages * docCharge.Copies);
                docCharge.ReleaseCount += 1;
            }
        }

        private void MarkAsRelease(bool changeStatusAsComplete, bool isInvoiced, double baseCharge, double salesTax)
        {
            ROIViewUtility.MarkBusy(true);
            release.IsReleased = true;
            ReviseBillingTierTotalPages();
            //release = BillingController.Instance.Release(release);
            if (request.ReleasedItems.Values.Count == 0) return;
            request.DraftRelease = null;
            request.Releases.Add(release);
            request.IsReleased = true;

            ReleasedPatientDetails releasePatient;
            release.ReleasedPatients.Clear();
            foreach (RequestPatientDetails patient in request.ReleasedItems.Values)
            {
                releasePatient = new ReleasedPatientDetails();
                release.ReleasedPatients.Add(patient.Key, releasePatient.AssignRequestToReleasedPatient(patient));
            }   

            if (changeStatusAsComplete)
            {   
                //if (ChangeRequestStatusUI.CheckRequestReleaseStatus(request, Context))
                //{
                    request.Status = RequestStatus.Completed;
                    request.StatusReason = string.Empty; //Clear all the previos status reasons
                    request.CompletedDate = DateTime.Now;
                //}
            }

            ReleaseTreeUI.MarkAsFalseForReleasedDocuments(request);
            if (isInvoiced)
            {
                request.OriginalBalance += baseCharge;
                request.SalesTaxAmount += salesTax;
				//CR#365397
                if (!request.IsInvoiced)
                {
                    request.IsInvoiced = true;
                }
            }
            SortedList<string, RequestPatientDetails> tempPatientList = new SortedList<string, RequestPatientDetails>();
            for (int count = 0; count < request.Patients.Count; count++)
            {
                tempPatientList.Add(request.Patients.Keys[count], request.Patients.Values[count]);
            }

            ReleaseDetails olddraftRelease = new ReleaseDetails();
            TaxPerFacilityDetails tempDefaultFacility = new TaxPerFacilityDetails();
            tempDefaultFacility = request.DefaultFacility;
            olddraftRelease = request.DraftRelease;
            request = RequestController.Instance.UpdateRequest(request);
            request.DefaultFacility = tempDefaultFacility;
            request.DraftRelease = olddraftRelease;

            request.Releases.Add(release);
            RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
            rsp.InfoEditor.Request = request;
            if (rsp.PatientInfoEditor != null)
            {
                rsp.PatientInfoEditor.Request = request;
                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).MarkAsRelease();

                //TODO: Need to revisit the logic to update the latest values in Patient Information screen
                Application.DoEvents();

                RequestPatientsCache.RemoveKey(request.Id);
                //if (requestPatients == null)
                requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);

                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).PageStatus = requestPatients.PageStatus;
                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).AttachmentStatus = requestPatients.AttachmentStatus;
                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).NonHPFDocumentStatus = requestPatients.NonHpfDocumentStatus;
                if (request.Patients.Count > 0)
                {
                    request.Patients.Clear();
                }
                foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                {
                    ReleasedPatientDetails releasedPatient = new ReleasedPatientDetails();
                    request.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                }
            }
            //for (int count = 0; count < tempPatientList.Count; count++)
            //{
            //    request.Patients.Add(tempPatientList.Keys[count], tempPatientList.Values[count]);
            //}
			//CR# 376962
            bool isFirstPatient = true;
            for (int count = 0; count < request.Patients.Count; count++)
            {
                if (isFirstPatient)
                {
                    request.PatientNames = request.Patients.Values[count].Name;
                    isFirstPatient = false;
                }
                else
                {
                    request.PatientNames += (":" + request.Patients.Values[count].Name);
                }
            }
            RequestEvents.OnRequestUpdated(Pane, new ApplicationEventArgs(request, this));
            RequestEvents.OnRequestReleased(Pane, new ApplicationEventArgs(request, this));
            
            BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
            billingEditor.Release = release;
            billingEditor.Request = request;

            //RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
            //rsp.InfoEditor.Request = request;
          /*  if (rsp.PatientInfoEditor != null)
            {
                rsp.PatientInfoEditor.Request = request;
                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).MarkAsRelease();

                //TODO: Need to revisit the logic to update the latest values in Patient Information screen
                RequestPatients requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);
                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).PageStatus = requestPatients.PageStatus;
                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).AttachmentStatus = requestPatients.AttachmentStatus;
                ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).NonHPFDocumentStatus = requestPatients.NonHpfDocumentStatus;
            } */

            Application.DoEvents();
            chargeHistories = BillingController.Instance.RetrieveChargeHistory(request.Id);
            chargeHistoryButton.Visible = (request.ReleaseCount > 0);            
        }
       
        /// <summary>
        /// Build HPF request part details with released patients
        /// </summary>
        /// <returns></returns>
        public static RequestPartDetails BuildHpfRequestPartDetails(SortedList<string, ReleasedPatientDetails> releasedPatients)
        {
            hpfDocumentCount = 0;
            RequestPartDetails requestPartDetails = new RequestPartDetails();
            requestPartDetails.ContentId = string.Empty;
            requestPartDetails.ContentSource = OutputPropertyDetails.HpfContentSource;

            //SortedList<string, ReleasedPatientDetails> releasedPatients = release.ReleasedPatients;

            foreach (ReleasedPatientDetails releasedPatientDetails in releasedPatients.Values)
            {
                IList<BaseRequestItem> documents = releasedPatientDetails.GlobalDocument.GetChildren;

                foreach (RequestDocumentDetails doc in documents)
                {
                    BuildPropertyLists(doc, releasedPatientDetails, requestPartDetails, string.Empty, null);                    
                }

                IList<BaseRequestItem> encounters = releasedPatientDetails.GetChildren;
                foreach (RequestEncounterDetails enc in encounters)
                {
                    foreach (RequestDocumentDetails doc in enc.GetChildren)
                    {
                        BuildPropertyLists(doc, releasedPatientDetails, requestPartDetails, enc.EncounterId, enc);                        
                    }
                }               
            }
            return requestPartDetails;      
        }

         /// <summary>
        /// Build HPF request part details with released patients
        /// </summary>
        /// <returns></returns>
        public static Collection<RequestPartDetails> BuildAttachmentRequestPartDetails(SortedList<string, ReleasedPatientDetails> releasedPatients, bool isPrintableAttachments)
        {
            printableAttachmentCount = 0;
            nonPrintableAttachmentCount = 0;
            Collection<RequestPartDetails> printableAttachmentReqPartList = new Collection<RequestPartDetails>();
            Collection<RequestPartDetails> nonPrintableAttachmentReqPartList = new Collection<RequestPartDetails>();

            foreach (ReleasedPatientDetails releasedPatientDetails in releasedPatients.Values)
            {
                //IList<BaseRequestItem> attachments = releasedPatientDetails.Attachment.GetChildren;
                foreach (RequestAttachmentDetails attachment in releasedPatientDetails.Attachment.GetChildren)
                {
                    if (attachment.SelectedForRelease.HasValue && (bool)attachment.SelectedForRelease)
                    {
                        RequestPartDetails requestPartDetails = new RequestPartDetails();
                        requestPartDetails.ContentId = string.Empty;
                        requestPartDetails.ContentSource = OutputPropertyDetails.AttachmentContentSource;
                        BuildPropertyLists(attachment, releasedPatientDetails, requestPartDetails, attachment.Name);
                        if (attachment.IsPrintable)
                        {
                            printableAttachmentCount += attachment.PageCount;
                            printableAttachmentReqPartList.Add(requestPartDetails);
                        }
                        else
                        {
                            nonPrintableAttachmentCount += attachment.PageCount;
                            nonPrintableAttachmentReqPartList.Add(requestPartDetails);
                        }
                    }
                }
            }
            if (isPrintableAttachments)
            {
                return printableAttachmentReqPartList;
            }
            return nonPrintableAttachmentReqPartList;
        }

        /// <summary>
        /// Build HPF request part details with released patients
        /// </summary>
        /// <returns></returns>
        public static Collection<RequestPartDetails> BuildAttachmentRequestPartDetails(SortedList<string, ReleasedPatientDetails> releasedPatients)
        {
            return BuildAttachmentRequestPartDetails(releasedPatients, true);            
        }

        /// <summary>
        /// Build the properties
        /// </summary>
        /// <param name="doc"></param>
        /// <param name="releasedPatientDetails"></param>
        /// <param name="requestPartDetails"></param>
        /// <param name="encounterName"></param>
        internal static void BuildPropertyLists(RequestDocumentDetails doc,
                                               ReleasedPatientDetails releasedPatientDetails,
                                               RequestPartDetails requestPartDetails,
                                               string encounterName, RequestEncounterDetails enc)                                              
        {
            PropertyDetails propertyDetails = new PropertyDetails();

            propertyDetails.PatientName      = releasedPatientDetails.Name;
            propertyDetails.MRN              = releasedPatientDetails.MRN;
            propertyDetails.Facility         = releasedPatientDetails.FacilityCode;
            propertyDetails.Encounter        = encounterName;
            propertyDetails.DocumentName     = doc.Name;
            propertyDetails.DocumentSubtitle = doc.Subtitle;
            propertyDetails.DocumentType     = doc.DocType;
            propertyDetails.Key              = doc.OutputKey; //CR#365588
            propertyDetails.DocumentDateTime = doc.DocumentDateTime; //CR#36588
            propertyDetails.DOB       		 = releasedPatientDetails.DOB.ToString();
            if (enc != null)
            {
                propertyDetails.AdmitDate = enc.AdmitDate.ToString();
                propertyDetails.DischargeDate = enc.DischargeDate.ToString();
            }
 
            foreach (RequestVersionDetails version in doc.GetChildren)
            {
                string IMNetIds = "";
                string pages = "";

                foreach (RequestPageDetails page in version.GetChildren)
                {   
                    if (page.SelectedForRelease.HasValue && (bool)page.SelectedForRelease)
                    {
                        IMNetIds += page.IMNetId + ",";
                        pages += page.PageNumberRequested.ToString() + ",";
                        hpfDocumentCount += 1;
                    }
                }

                propertyDetails.IMNetIds = IMNetIds.TrimEnd(',');
                propertyDetails.Pages = pages.TrimEnd(',');
            }

            if (!(string.IsNullOrEmpty(propertyDetails.Pages)))
            {
                requestPartDetails.PropertyLists.Add(propertyDetails);
            }

        }


        /// <summary>
        /// Build the properties
        /// </summary>
        /// <param name="doc"></param>
        /// <param name="releasedPatientDetails"></param>
        /// <param name="requestPartDetails"></param>
        /// <param name="encounterName"></param>
        internal static void BuildPropertyLists(RequestAttachmentDetails attachment,
                                               ReleasedPatientDetails releasedPatientDetails,
                                               RequestPartDetails requestPartDetails,
                                               string encounterName)                                               
        {
            PropertyDetails propertyDetails = new PropertyDetails();

            propertyDetails.PatientName = releasedPatientDetails.Name;
            propertyDetails.MRN = releasedPatientDetails.MRN;
            propertyDetails.Facility = releasedPatientDetails.FacilityCode;
            propertyDetails.Encounter = encounterName;
            propertyDetails.DocumentName = attachment.Name;
            propertyDetails.DocumentSubtitle = attachment.Subtitle;
            propertyDetails.DOB = releasedPatientDetails.DOB.ToString();
            //set document type to fileType if it is not empty OTHERWISE set document type to attachmentType
            if (attachment.FileType.Equals(string.Empty))
            {
                propertyDetails.DocumentType = attachment.AttachmentType.ToUpper();
            }
            else
            {
                propertyDetails.DocumentType = attachment.FileType.ToUpper();
            }

            propertyDetails.FileIds = attachment.FileId;
            propertyDetails.FileNames = attachment.FileName;
            propertyDetails.FileExts = attachment.FileExt;
            propertyDetails.FileTypes = attachment.FileType;
            propertyDetails.ContentType = ROIConstants.AttachmentDomainType;
            if (attachment.FileAttachDate.HasValue)
            {
                propertyDetails.AttachDate = attachment.FileAttachDate.Value.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
            }
            propertyDetails.PageCount  = Convert.ToString(attachment.PageCount);
            requestPartDetails.PropertyLists.Add(propertyDetails);

        }

        /// <summary>
        /// Build ROI request part details with CoverLetter or Invoice
        /// </summary>
        /// <param name="fileName"></param>
        private static RequestPartDetails BuildROIRequestPartDetails(DocumentInfo documentInfo)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();

            //Unique content id is used for coverletter is included or not
            if (string.Compare(documentInfo.Type, LetterType.Invoice.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) != 0)
            {
                requestPartDetails.ContentId = documentInfo.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            else
            {
                requestPartDetails.ContentId = string.Empty;
            }
            requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails propertyDetails = new PropertyDetails();            
            propertyDetails.FileIds   = documentInfo.Type + "." + documentInfo.Id;

            requestPartDetails.PropertyLists.Add(propertyDetails);

            return requestPartDetails;
        }

        /// <summary>
        /// Build ROI request part details with CoverLetter || Invoice || PastInvoice
        /// </summary>
        /// <param name="fileName"></param>
        private static RequestPartDetails BuildROIRequestPartDetails(ReleaseAndPreviewInfo releaseAndPreviewInfo, long statementId, long requestorId, string requestorType, string requestorName)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();
            PropertyDetails propertyDetails = new PropertyDetails();
            requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            if (releaseAndPreviewInfo.docInfoList == null) return requestPartDetails;

            foreach (DocInfo documentInfo in releaseAndPreviewInfo.docInfoList.docInfos)
            {
                if (string.IsNullOrEmpty(requestPartDetails.ContentId))
                {
                    requestPartDetails.ContentId = documentInfo.id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                if (documentInfo.type.Equals("RequestorLetter"))
                {
                    propertyDetails.FileIds += documentInfo.type + "." + statementId + ",";
                    propertyDetails.RequestorGrouping = requestorId + "-" + requestorType + "-" + requestorName;
                }
                else
                {
                    propertyDetails.FileIds += documentInfo.type + "." + documentInfo.id + ",";
                }
                
            }
            propertyDetails.FileIds = propertyDetails.FileIds.TrimEnd(new char[] { ',' });
            requestPartDetails.PropertyLists.Add(propertyDetails);
            return requestPartDetails;
        }

        /// <summary>
        /// Build ROI request part details with CoverLetter And Invoice
        /// </summary>
        /// <param name="fileName"></param>
        private static RequestPartDetails BuildROIRequestPartDetails(DocumentInfoList documentInfoList)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();

            //Unique content id is used for coverletter is included or not
            requestPartDetails.ContentId = documentInfoList.DocumentInfoCollection[0].Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.FileIds = documentInfoList.DocumentInfoCollection[0].Type + "." + documentInfoList.DocumentInfoCollection[0].Id;
            propertyDetails.FileIds += "," + documentInfoList.DocumentInfoCollection[1].Type + "." + documentInfoList.DocumentInfoCollection[1].Id;

            requestPartDetails.PropertyLists.Add(propertyDetails);

            return requestPartDetails;
        }

        //for release and rerealease
        private static RequestPartDetails BuildROIReleasePartDetails(ReleaseAndPreviewInfo releaseAndPreviewInfo)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();
            if (releaseAndPreviewInfo != null)
            {
                PropertyDetails propertyDetails = new PropertyDetails();
                
                requestPartDetails.ContentSource = OutputPropertyDetails.ReleaseContentSource;
                //set content id as 0
                requestPartDetails.ContentId = releaseAndPreviewInfo.releaseId.ToString();
                //instead of release page sequence
                propertyDetails.FileIds = OutputPropertyDetails.ReleaseContentSource + "." + releaseAndPreviewInfo.releaseId;
                //set default file types for this release (Attachments and MPF Documents)
                propertyDetails.FileTypes = OutputPropertyDetails.AttachmentContentSource + "||" + OutputPropertyDetails.HpfContentSource;
                requestPartDetails.PropertyLists.Add(propertyDetails);
            }
            return requestPartDetails;
        }
        

        private void DisableBillingInfo()
        {
            documentChargeUI.DisableDocumentCharges();
            feeChargeUI.DisableFeeCharges();
            shippingInfoUI.DisableShippingInfo();
            salesTaxSummaryUI.DisableSalesTaxSummaryUI();
          //  billingLocationComboBox.Enabled = false;
        }

        /// <summary>
        /// Reverts the data
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void revertButton_Click(object sender, EventArgs e) 
        {
            isRevert = true;
            IsDirty = false;
            errorProvider.Clear();            
            request.DefaultFacility = defaultFacility;
            request.HasSalesTax = oldSalesTaxStatus;
            salesTaxSummaryUI.PrePopulate(request);
            salesTaxSummaryUI.IsGridItemChanged = false;
            SetData(((BillingPaymentInfoEditor)Pane.ParentPane).Release);
            isApplyTaxStateChanged = false;
            Hashtable taxItemStates = salesTaxSummaryUI.RetrieveTaxItemStates();
            SetSalesTaxSummaryUI(taxItemStates);
            UpdatePendingReleaseCost();
            EnableButtons(false);
        }

        /// <summary>
        /// Mark the data as dirty
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void MarkDirty(object sender, EventArgs e)
        {   
            IsDirty = true;
            EnableButtons(true);
            EnablePreBillButton(false);
            EnableReleaseButton(false);
            EnableInvoiceButton(false);
            
        }

        /// <summary>
        /// Method to change the IsGridItemChanged property value of salestax summary grid
        /// </summary>
        internal void ChangeGridItemChangedStatus()
        {
            salesTaxSummaryUI.IsGridItemChanged = false;
            SetSalesTaxSummaryUI(new Hashtable());
        }        

        /// <summary>
        /// Verify the DSR tree by user selected any unrelased pages
        /// </summary>
        private void PreviouslyReleased()
        {
            releasedPageCount = 0;
            unreleasedCount = 0;
            release.ReleasedPatients.Clear();
            //Retrieve the request patients if the user directly comes from request information screen to release and invoice screen.
            if (request.Patients.Count > 0)
            {
                request.Patients.Clear();
                if (release.ReleasedPatients.Count == 0)
                {
                    //RequestPatients requestPatients;

                    RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
                    rsp.InfoEditor.Request = request;
                    if (rsp.PatientInfoEditor != null)
                    {
                        rsp.PatientInfoEditor.Request = request;
                        requestPatients = ((RequestPatientInfoUI)rsp.PatientInfoEditor.MCP.View).requestPatients;
                    }
                    else
                    {
                        Application.DoEvents();
                        if (requestPatients == null)
                            requestPatients = RequestController.Instance.RetrieveRequestPatients(request.Id);
                    }
                    foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                    {
                        ReleasedPatientDetails releasedPatient = new ReleasedPatientDetails();
                        release.ReleasedPatients.Add(requestPatientDetails.Key, releasedPatient.AssignRequestToReleasedPatient(requestPatientDetails));
                        request.Patients.Add(requestPatientDetails.Key, (RequestPatientDetails)ROIViewUtility.DeepClone(requestPatientDetails));
                    }
                }
                foreach (RequestPatientDetails patient in request.Patients.Values)
                {
                    RetrieveReleasedUnReleasedPageCount(patient);
                }
                if (release.ROIPages.Count == 0 && release.SupplementarityAttachmentsSeqField.Count == 0 && release.SupplementarityDocumentsSeqField.Count == 0 && 
                    release.SupplementalAttachmentsSeqField.Count == 0 && release.SupplementalDocumentsSeqField.Count == 0)
                {
                    RetrieveDocumentSequesnce(request);
                }
            }
            else
            {
                RequestDetails requestDetails = (RequestDetails)ROIViewUtility.DeepClone(request);
                Application.DoEvents();
                if (requestPatients == null)
                    requestPatients = RequestController.Instance.RetrieveRequestPatients(requestDetails.Id);

                foreach (RequestPatientDetails requestPatientDetails in requestPatients.RequestPatientList)
                {
                    requestDetails.Patients.Add(requestPatientDetails.Key, requestPatientDetails);
                    ReleasedPatientDetails releasedPatient = new ReleasedPatientDetails();
                    release.ReleasedPatients.Add(requestPatientDetails.Key, releasedPatient.AssignRequestToReleasedPatient(requestPatientDetails));
                    request.Patients.Add(requestPatientDetails.Key, (RequestPatientDetails)ROIViewUtility.DeepClone(requestPatientDetails));                        
                }
                foreach (RequestPatientDetails patient in requestDetails.Patients.Values)
                {
                    RetrieveReleasedUnReleasedPageCount(patient);  
                }

                RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
                rsp.InfoEditor.Request = request;
                RetrieveDocumentSequesnce(requestDetails);
            }
        }

        private void RetrieveDocumentSequesnce(RequestDetails tempRequestDetails)
        {
            release.ROIPages.Clear();
            release.SupplementarityAttachmentsSeqField.Clear();
            release.SupplementarityDocumentsSeqField.Clear();
            release.SupplementalAttachmentsSeqField.Clear();
            release.SupplementalDocumentsSeqField.Clear();
            int hpfcount = 0;
            Dictionary<long, int> nonHpfBillingTiersIds = new Dictionary<long, int>();
            //compile list of "selected for release" documents to support release - sequences are mapped to the "release"
            List<ROIPage> roiPages = release.ROIPages;
            //List<long> roiPagesSeqField = release.RoiPagesSeqField;
            List<long> supplementarityAttachmentsSeqField = release.SupplementarityAttachmentsSeqField;
            List<long> supplementarityDocumentsSeqField = release.SupplementarityDocumentsSeqField;
            List<long> supplementalAttachmentsSeqField = release.SupplementalAttachmentsSeqField;
            List<long> supplementalDocumentsSeqField = release.SupplementalDocumentsSeqField;
            ROIPage roiPage = new ROIPage();
            foreach (RequestPatientDetails patient in tempRequestDetails.ReleasedItems.Values)
            {
                foreach (RequestEncounterDetails enc in patient.GetChildren)
                {
                    if (enc.SelectedForRelease == true)
                    {
                        foreach (RequestDocumentDetails doc in enc.GetChildren)
                        {
                            if (doc.SelectedForRelease == true)
                            {
                                foreach (RequestVersionDetails ver in doc.GetChildren)
                                {
                                    if (ver.SelectedForRelease == true)
                                    {
                                        foreach (RequestPageDetails page in ver.GetChildren)
                                        {
                                            if (page.SelectedForRelease == true)
                                                hpfcount++;
                                            roiPage = new ROIPage();
                                            roiPage.PageSequence = page.PageSeq;
                                            roiPage.IsSelfEncounter = enc.IsSelfPay ? true : false;
                                            roiPages.Add(roiPage);
                                            //roiPagesSeqField.Add(page.PageSeq);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                foreach (RequestDocumentDetails doc in patient.GlobalDocument.GetChildren)
                {
                    if (doc.SelectedForRelease == true)
                    {
                        foreach (RequestVersionDetails ver in doc.GetChildren)
                        {
                            if (ver.SelectedForRelease == true)
                            {
                                foreach (RequestPageDetails page in ver.GetChildren)
                                {
                                    if (page.SelectedForRelease == true)
                                        hpfcount++;
                                    roiPage = new ROIPage();
                                    roiPage.PageSequence = page.PageSeq;
                                    roiPage.IsSelfEncounter = false;
                                    roiPages.Add(roiPage);
                                }
                            }
                        }
                    }
                }

                foreach (KeyValuePair<long, bool> page in patient.PageStatus)
                {
                    if (page.Value)
                    {
                        hpfcount++;
                    }

                }
            }
            foreach (ReleasedPatientDetails releasedPatient in release.ReleasedPatients.Values)
            {
                foreach (RequestNonHpfDocumentDetails nonHpfDoc in releasedPatient.NonHpfDocument.GetChildren)
                {
                    MapSelectedForReleaseSeq(nonHpfDoc.IsHPF, nonHpfDoc.SelectedForRelease.Value,
                                                nonHpfDoc.DocumentSeq, supplementarityDocumentsSeqField, supplementalDocumentsSeqField);

                    if (nonHpfBillingTiersIds.ContainsKey(nonHpfDoc.BillingTier))
                    {
                        nonHpfBillingTiersIds[nonHpfDoc.BillingTier] += nonHpfDoc.PageCount;
                    }
                    else
                    {
                        nonHpfBillingTiersIds.Add(nonHpfDoc.BillingTier, nonHpfDoc.PageCount);
                    }
                }
                foreach (RequestAttachmentDetails tmpAttachment in releasedPatient.Attachment.GetChildren)
                {
                    MapSelectedForReleaseSeq(tmpAttachment.IsHPF, tmpAttachment.SelectedForRelease.Value,
                                                    tmpAttachment.AttachmentSeq, supplementarityAttachmentsSeqField, supplementalAttachmentsSeqField);
                    if (nonHpfBillingTiersIds.ContainsKey(tmpAttachment.BillingTier))
                    {
                        nonHpfBillingTiersIds[tmpAttachment.BillingTier] += tmpAttachment.PageCount;
                    }
                    else
                    {
                        nonHpfBillingTiersIds.Add(tmpAttachment.BillingTier, tmpAttachment.PageCount);
                    }
                }
            }
        }

        private void MapSelectedForReleaseSeq(bool isHpf, bool selectedForRelease, long seq, List<long> hpfSeqList, List<long> nonHpfSeqList)
        {
            if (selectedForRelease)
            {
                if (isHpf)
                {
                    hpfSeqList.Add(seq);
                }
                else
                {
                    nonHpfSeqList.Add(seq);
                }
            }
        }

        private void RetrieveReleasedUnReleasedPageCount(RequestPatientDetails patient)
        {
            pagesReleased(patient);
            pagesReleased(patient.GlobalDocument);
            pagesReleased(patient.NonHpfDocument);
            pagesReleased(patient.Attachment);
        }

        private void pagesReleased(BaseRequestItem item)
        {
            if (typeof(RequestPageDetails).IsAssignableFrom(item.GetType())
                || typeof(RequestNonHpfDocumentDetails).IsAssignableFrom(item.GetType())
                || typeof(RequestAttachmentDetails).IsAssignableFrom(item.GetType()))
            {
                if (item.SelectedForRelease == true && item.IsReleased == false)
                {
                    unreleasedCount++;
                }
                else if (item.SelectedForRelease == true && item.IsReleased == true)
                {
                    releasedPageCount++;
                }
            }

            foreach (BaseRequestItem child in item.GetChildren)
            {
                pagesReleased(child);
            }
        }

        public void SaveRequestCoreCharges(ReleaseDetails release, RequestDetails request)
        {
            this.request = request;
            SaveAndBillInfo(release, request);
        }

        public void SaveAndBillInfo(ReleaseDetails release, RequestDetails request)
        {
            RequestCoreChargeDetails requestCore = new RequestCoreChargeDetails();
            requestCore.RequestId = request.Id;
            requestCore.BillingLoc = request.DefaultFacility.FacilityCode;
            requestCore.PreviouslyReleasedCost = release.PreviouslyReleasedCost;
            requestCore.ReleaseDate = release.ReleaseDate;
            requestCore.ReleasedCost = release.ReleaseCost;
            requestCore.TotalPages = release.TotalPages;
            if (IsReleaseEnable)
            {
                requestCore.TotalPages = release.TotalPages + previouslyReleasedPages;
            }
            if (IsReReleaseEnable)
            {
                requestCore.TotalPages = previouslyReleasedPages;
            }
            requestCore.TotalRequestCost = release.TotalRequestCost;
            requestCore.BalanceDue = release.BalanceDue;
            release.TotalPagesReleased = release.TotalPages;
            requestCore.TotalPagesReleased = release.TotalPagesReleased;
            requestCore.SalestaxPercentageForBillingLoc = request.DefaultFacility.TaxPercentage;
            requestCore.BillingType = release.BillingType;
            requestCore.ApplySalesTax = request.HasSalesTax;
            requestCore.BillingLocCode = request.DefaultFacility.FacilityCode == null || request.DefaultFacility.FacilityCode.Equals("Please Select") ? string.Empty : request.DefaultFacility.FacilityCode;
            requestCore.BillingLocName = request.DefaultFacility.FacilityName == null || request.DefaultFacility.FacilityName.Equals("Please Select") ? string.Empty : request.DefaultFacility.FacilityName;
            //requestCore.BillingTypeIdForFeeCharge=
            requestCore.CreditAdjustmentAmount = release.CreditAdjustmentTotal;
            requestCore.DebitAdjustmentAmount = release.DebitAdjustmentTotal;
            requestCore.OriginalBalance = request.OriginalBalance;
            requestCore.PaymentAmount = release.PaymentTotal;
            requestCore.SalestaxTotalAmount = salesTaxSummaryUI.TotalTaxAmount;
            requestCore.IsUnbillable = release.IsUnbillable;



            List<DocumentChargeDetails> list = new List<DocumentChargeDetails>();
            foreach (DocumentChargeDetails docCharge in release.DocumentCharges)
            {
                DocumentChargeDetails doc = new DocumentChargeDetails();
                doc.Amount = docCharge.Amount;
                doc.Copies = docCharge.Copies;
                doc.BillingTier = docCharge.BillingTier;
                doc.TotalPages = release.TotalPages;
                doc.Pages = docCharge.Pages;
                doc.BillingTierId = docCharge.BillingTierId;
                doc.ReleaseCount = docCharge.ReleaseCount;
                doc.IsElectronic = docCharge.IsElectronic;
                //doc.HasSalesTax = docCharge.HasSalesTax;
                doc.HasSalesTax = (!requestCore.ApplySalesTax) ? false : docCharge.HasSalesTax;
                //doc.TaxAmount = docCharge.TaxAmount;
                doc.TaxAmount = (!requestCore.ApplySalesTax) ? 0.0 : docCharge.TaxAmount;
                doc.RemoveBaseCharge = docCharge.RemoveBaseCharge;
                list.Add(doc);

            }

            requestCore.BillingCharge.DocumentCharge = list;


            List<FeeChargeDetails> list1 = new List<FeeChargeDetails>();
            foreach (FeeChargeDetails feeCharge in release.FeeCharges)
            {
                FeeChargeDetails fee = new FeeChargeDetails();
                fee.Amount = feeCharge.Amount;
                fee.IsCustomFee = feeCharge.IsCustomFee;
                fee.FeeType = feeCharge.FeeType;
                fee.HasSalesTax = feeCharge.HasSalesTax;
                fee.TaxAmount = feeCharge.TaxAmount;
                list1.Add(fee);
            }
            requestCore.BillingCharge.FeeCharge = list1;

            if (release.ShippingDetails != null)
            {
                requestCore.ShippingInfo.ShippingCharge = release.ShippingDetails.ShippingCharge;
                requestCore.ShippingInfo.ShippingAddress = new AddressDetails();
                requestCore.ShippingInfo.ShippingAddress.PostalCode = release.ShippingDetails.ShippingAddress.PostalCode;
                requestCore.ShippingInfo.AddressType = release.ShippingDetails.AddressType;
                requestCore.ShippingInfo.ShippingAddress.State = release.ShippingDetails.ShippingAddress.State;
                requestCore.ShippingInfo.ShippingWebAddress = release.ShippingDetails.ShippingWebAddress;
                requestCore.ShippingInfo.ShippingAddress.Address1 = release.ShippingDetails.ShippingAddress.Address1;
                requestCore.ShippingInfo.ShippingAddress.Address2 = release.ShippingDetails.ShippingAddress.Address2;
                requestCore.ShippingInfo.ShippingAddress.Address3 = release.ShippingDetails.ShippingAddress.Address3;
                requestCore.ShippingInfo.TrackingNumber = release.ShippingDetails.TrackingNumber;
                if (shippingInfoUI.IsUpdateRequestorAddress == false || shippingInfoUI.AddressType == null)
                    requestCore.ShippingInfo.AddressType = release.ShippingDetails.AddressType;
                else
                    requestCore.ShippingInfo.AddressType = (RequestorAddressType)Enum.Parse(typeof(RequestorAddressType), shippingInfoUI.AddressType);
                requestCore.ShippingInfo.ShippingAddress.City = release.ShippingDetails.ShippingAddress.City;
                requestCore.ShippingInfo.WillReleaseShipped = release.ShippingDetails.WillReleaseShipped;
                requestCore.ShippingInfo.OutputMethod = release.ShippingDetails.OutputMethod;
                requestCore.ShippingInfo.NonPrintableAttachmentQueue = release.ShippingDetails.NonPrintableAttachmentQueue;
                requestCore.ShippingInfo.ShippingMethodId = release.ShippingDetails.ShippingMethodId;
                requestCore.ShippingInfo.ShippingMethod = release.ShippingDetails.ShippingMethod;
            }

            List<SalesTaxReasons> taxReasonList = new List<SalesTaxReasons>();
            if (release != null)
            {
                foreach (SalesTaxReasons reason in release.TaxReasons)
                {
                    SalesTaxReasons taxReason = new SalesTaxReasons();
                    taxReason.Id = request.Id;
                    taxReason.Reason = reason.Reason;
                    taxReason.CreatedDate = reason.CreatedDate;
                    taxReasonList.Add(taxReason);
                }
            }
            requestCore.SalesTaxReasonsList = taxReasonList;

             RequestController.Instance.SaveRequestCoreCharges(requestCore);
             if (IsPrePopulate)
             {
                PopulateBillingLocation();
             }
        }

        private void SetInvoiceChargeDetailsList(RequestBillingInfo reqBillInfo)
        {
            List<InvoiceChargeDetails> invoiceChargesList = new List<InvoiceChargeDetails>(reqBillInfo.ReqCoreChargesInvoiceDetail.Count);
            InvoiceChargeDetails invoiceCharges;
            List<RequestTransaction> reqTransList = new List<RequestTransaction>();
            int i = 0;
            foreach (RequestCoreChargesInvoiceDetail reqCoreDetail in reqBillInfo.ReqCoreChargesInvoiceDetail)
            {
                invoiceCharges = new InvoiceChargeDetails();
                invoiceCharges.BalanceDue = reqCoreDetail.releaseCost;
                invoiceCharges.BillingLocationCode = reqBillInfo.BillingLocCode;
                invoiceCharges.BillingLocationName = reqBillInfo.BillingLocName;
                invoiceCharges.CreatedDate = ((DateTime) reqCoreDetail.invoiceCreatedDt).Date;
                invoiceCharges.Id = reqCoreDetail.requestCoreDeliveryChargesId;                
                invoiceCharges.TaxCharge = reqBillInfo.SalestaxTotalAmount;                
                invoiceCharges.TotalCreditAdjustment = reqBillInfo.CreditAdjustmentAmount;
                invoiceCharges.TotalDebitAdjustment = reqBillInfo.DebitAdjustmentAmount;
                invoiceCharges.TotalPayment = reqBillInfo.PaymentAmount;
                
                foreach (RequestTransaction reqTransaction in reqCoreDetail.ReqTransaction)
                {
                    RequestTransaction reqTrans = new RequestTransaction();

                    reqTrans.IsNewlyAdded = reqTransaction.IsNewlyAdded;
                    reqTrans.ReasonName = reqTransaction.ReasonName;
                    reqTrans.AdjustmentPaymentType = reqTransaction.AdjustmentPaymentType;
                    reqTrans.Id = reqTransaction.Id;

                    reqTrans.Amount = reqTransaction.Amount;
                    reqTrans.CreatedDate = reqTransaction.CreatedDate;
                    reqTrans.Description = reqTransaction.Description;
                    reqTrans.IsDebit = reqTransaction.IsDebit;
                    reqTrans.TransactionType = reqTransaction.TransactionType;
                    reqTrans.PaymentMode = reqTransaction.PaymentMode;

                    i++;
                    invoiceCharges.RequestTransactions.Add(reqTrans);
                    
                }

                invoiceChargesList.Add(invoiceCharges);

            }
            inVoiceChargeDetailsList = new InvoiceChargeDetailsList();
            inVoiceChargeDetailsList.InvoiceCharges = invoiceChargesList;

        }

        public ReleaseDetails RevertInfo(RequestDetails request)
        {
            ReleaseDetails releaseInfo = new ReleaseDetails();
            requestBillingInfo = RequestController.Instance.RetrieveRequestBillingPaymentInfo(request.Id);
            if (requestBillingInfo == null)
                return releaseInfo;


            SetInvoiceChargeDetailsList(requestBillingInfo);

            releaseInfo.BalanceDue = requestBillingInfo.BalanceDue;
            releaseInfo.InvoicesSalesTaxAmount = requestBillingInfo.InvoicesSalesTaxAmount;

            request.DefaultFacility.FacilityCode = requestBillingInfo.BillingLocCode.Equals(string.Empty) ? "Please Select" : requestBillingInfo.BillingLocCode;
            request.DefaultFacility.FacilityName = requestBillingInfo.BillingLocName.Equals(string.Empty) ? "Please Select" : requestBillingInfo.BillingLocName;
            request.DefaultFacility.TaxPercentage = requestBillingInfo.SalestaxPercentageForBillingLoc;
            releaseInfo.BillingType = requestBillingInfo.BillingType;
            //releaseInfo.BillingTypeIdForFeeCharge = requestBillingInfo.BillingTypeIdForFeeCharge;
            releaseInfo.CreditAdjustmentTotal = requestBillingInfo.CreditAdjustmentAmount;
            releaseInfo.DebitAdjustmentTotal = requestBillingInfo.DebitAdjustmentAmount;
            releaseInfo.PaymentTotal = requestBillingInfo.PaymentAmount;

            request.OriginalBalance = requestBillingInfo.OriginalBalance;            
            request.HasSalesTax = requestBillingInfo.ApplySalesTax;
            applyTax = requestBillingInfo.ApplySalesTax;
            request.InvoiceBaseCharge = requestBillingInfo.InvoiceBaseCharge;
            request.InvoiceAutoAdjustment = requestBillingInfo.InvoiceAutoAdjustment;

            releaseInfo.PreviouslyReleasedCost = requestBillingInfo.PreviouslyReleasedCost;
            releaseInfo.ReleaseDate = requestBillingInfo.ReleaseDate;
            releaseInfo.ReleaseCost = requestBillingInfo.ReleasedCost;
            releaseInfo.SalesTaxTotalAmount = requestBillingInfo.SalestaxTotalAmount;
            releaseInfo.TotalPages = requestBillingInfo.TotalPages;
            releaseInfo.TotalPagesReleased = requestBillingInfo.TotalPagesReleased;
            releaseInfo.TotalRequestCost = requestBillingInfo.TotalRequestCost;
            releaseInfo.RequestId = requestBillingInfo.RequestId;

            releaseInfo.IsReleased = requestBillingInfo.IsReleased;
            releaseInfo.IsInvoiced = requestBillingInfo.IsInvoiced;
            releaseInfo.InvoicesBalanceDue = requestBillingInfo.InvoicesBalanceDue;
            releaseInfo.UnAppliedTotal = requestBillingInfo.UnAppliedAmount;
            UnAppliedAdjustmentPaymentTotal = requestBillingInfo.UnAppliedAmount;
            releaseInfo.IsUnbillable = requestBillingInfo.IsUnbillable;

            List<DocumentChargeDetails> docChargeList = new List<DocumentChargeDetails>(requestBillingInfo.BillingCharge.DocumentCharge.Count);
            DocumentChargeDetails docCharge;
            for (int i = 0; i < requestBillingInfo.BillingCharge.DocumentCharge.Count; i++)
            {
                docCharge = new DocumentChargeDetails();
                docCharge.Amount = requestBillingInfo.BillingCharge.DocumentCharge[i].Amount;
                docCharge.Copies = requestBillingInfo.BillingCharge.DocumentCharge[i].Copies;
                docCharge.BillingTier = requestBillingInfo.BillingCharge.DocumentCharge[i].BillingTier;
                docCharge.TotalPages = requestBillingInfo.BillingCharge.DocumentCharge[i].TotalPages;
                docCharge.Pages = requestBillingInfo.BillingCharge.DocumentCharge[i].Pages;
                docCharge.BillingTierId = requestBillingInfo.BillingCharge.DocumentCharge[i].BillingTierId;
                docCharge.ReleaseCount = requestBillingInfo.BillingCharge.DocumentCharge[i].ReleaseCount;
                docCharge.IsElectronic = requestBillingInfo.BillingCharge.DocumentCharge[i].IsElectronic;
                docCharge.RemoveBaseCharge = requestBillingInfo.BillingCharge.DocumentCharge[i].RemoveBaseCharge;
                docCharge.HasSalesTax = requestBillingInfo.BillingCharge.DocumentCharge[i].HasSalesTax;
                docChargeList.Add(docCharge);
            }

            foreach (DocumentChargeDetails doc in docChargeList)
                releaseInfo.DocumentCharges.Add(doc);

            List<FeeChargeDetails> feeChargeList = new List<FeeChargeDetails>(requestBillingInfo.BillingCharge.FeeCharge.Count);
            
            for (int i = 0; i < requestBillingInfo.BillingCharge.FeeCharge.Count; i++)
            {
                FeeChargeDetails feeCharge = new FeeChargeDetails();
                feeCharge.Amount = requestBillingInfo.BillingCharge.FeeCharge[i].Amount;
                feeCharge.IsCustomFee = requestBillingInfo.BillingCharge.FeeCharge[i].IsCustomFee;
                feeCharge.FeeType = requestBillingInfo.BillingCharge.FeeCharge[i].FeeType;
                feeCharge.HasSalesTax = requestBillingInfo.BillingCharge.FeeCharge[i].HasSalesTax;
                feeChargeList.Add(feeCharge);
            }
            foreach (FeeChargeDetails fee in feeChargeList)
                releaseInfo.FeeCharges.Add(fee);

            releaseInfo.ShippingDetails=new ShippingInfo();
            releaseInfo.ShippingDetails.ShippingAddress = new AddressDetails();
            //if (requestBillingInfo.ShippingInfo.AddressType.ToString()=="")
            //{
                releaseInfo.ShippingDetails.AddressType = requestBillingInfo.ShippingInfo.AddressType;
                releaseInfo.ShippingDetails.ShippingAddress = requestBillingInfo.ShippingInfo.ShippingAddress;
            //}

            releaseInfo.ShippingDetails.WillReleaseShipped = requestBillingInfo.ShippingInfo.WillReleaseShipped;
            if (requestBillingInfo.ShippingInfo.NonPrintableAttachmentQueue != null)
            {
                releaseInfo.ShippingDetails.NonPrintableAttachmentQueue = requestBillingInfo.ShippingInfo.NonPrintableAttachmentQueue;
            }

            releaseInfo.ShippingDetails.OutputMethod = requestBillingInfo.ShippingInfo.OutputMethod;
            
            releaseInfo.ShippingDetails.ShippingCharge = requestBillingInfo.ShippingInfo.ShippingCharge;
            releaseInfo.ShippingDetails.ShippingMethod = requestBillingInfo.ShippingInfo.ShippingMethod;
            releaseInfo.ShippingDetails.ShippingMethodId = requestBillingInfo.ShippingInfo.ShippingMethodId;

            if(requestBillingInfo.ShippingInfo.ShippingWebAddress!=null)
            releaseInfo.ShippingDetails.ShippingWebAddress = requestBillingInfo.ShippingInfo.ShippingWebAddress;

            releaseInfo.ShippingDetails.ShippingWeight = requestBillingInfo.ShippingInfo.ShippingWeight;
            releaseInfo.ShippingDetails.TrackingNumber = requestBillingInfo.ShippingInfo.TrackingNumber;
            releaseInfo.ShippingDetails.NonPrintableAttachmentQueue = requestBillingInfo.ShippingInfo.NonPrintableAttachmentQueue;

            if (requestBillingInfo.SalesTaxReasonsList.Count != 0)
            {
                foreach (SalesTaxReasons taxReason in requestBillingInfo.SalesTaxReasonsList)
                {
                    releaseInfo.TaxReasons.Add(taxReason);
                }
            }

            return releaseInfo;

        }

        /// <summary>
        /// Sets the data
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            if (data == null)
            {   
                EnableButtons(false);
                EnablePreBillButton(false);
                EnableReleaseButton(false);
                EnableInvoiceButton(false);
                billingPaymentActionUI.Enabled = this.Enabled = false;               
                isDirty = false;
                return;
            }
            Application.DoEvents();
            hasRights = RequestController.Instance.HasSercuirtyRights();
            isSetdata = true;
            this.release = (ReleaseDetails)ROIViewUtility.DeepClone(data);
            PreviouslyReleased();            

            chargeHistories = new Collection<ChargeHistoryDetails>();           
            if (request.ReleaseCount > 0)
            {
                chargeHistories = BillingController.Instance.RetrieveChargeHistory(request.Id);
                previouslyReleasedPages = this.release.TotalPagesReleased;
            }
            
            ReleaseDetails release = (ReleaseDetails)ROIViewUtility.DeepClone(this.release);
            salesTaxSummaryUI.IsGridItemChanged = false;

            //if (request.HasDraftRelease || isRevert)
            {
                releaseRev = this.release;
            }

            //if (releaseRev.DocumentCharges.Count != 0 && releaseRev.DocumentCharges != release.DocumentCharges)
            //{
            //    //release.DocumentCharges.Clear();
            //    int i = 0;
            //    if (release.DocumentCharges.Count > 0)
            //    {
            //        foreach (DocumentChargeDetails doc in releaseRev.DocumentCharges)
            //        {
            //            release.DocumentCharges[i].HasSalesTax = doc.HasSalesTax;
            //        }
            //    }
            //}
            bool hasDocuments = false;
            if (release.ROIPages.Count > 0 || release.SupplementalAttachmentsSeqField.Count > 0 ||
                release.SupplementalDocumentsSeqField.Count > 0 || release.SupplementarityAttachmentsSeqField.Count > 0 ||
                release.SupplementarityDocumentsSeqField.Count > 0)
            {
                hasDocuments = true;
            }
            documentChargeUI.SetData(release.DocumentCharges, hasDocuments);
			// CR#359078
            if (releaseRev.FeeCharges.Count != 0)
            {
                release.FeeCharges.Clear();
                foreach (FeeChargeDetails feeCharge in releaseRev.FeeCharges)
                {
                    release.FeeCharges.Add(feeCharge);
                }
            }
            if (releaseRev.BillingType != null)
            {
                release.BillingType = releaseRev.BillingType;
            }
            feeChargeUI.SetData(release, false, request.HasDraftRelease);            

            if (request.DefaultFacility != null)
            {                
                // Initial loading of this screen, the defaultFacility object is created
                defaultFacility = (defaultFacility != null) ? defaultFacility : new TaxPerFacilityDetails();
                salesTaxSummaryUI.PrePopulate(request);
                // While performing the revert and Dont save and proceed operation, the value of the defaultFacility will be assigned and
                // to reset the changes made in the UI and load the data from the XML
                if (!string.IsNullOrEmpty(defaultFacility.FacilityCode))
                {
                    request.DefaultFacility = defaultFacility;
                    request.HasSalesTax = oldSalesTaxStatus;
                    salesTaxSummaryUI.IsGridItemChanged = false;
                    isApplyTaxStateChanged = false;
                }
				else
                {
                    defaultFacility.FacilityCode = string.Empty;
                    defaultFacility.FacilityName = string.Empty;
                }

                ////If the billing location was removed from admin after invoice, that blling location also added into the combo-box
                //if (adjustmentPaymentInfoUI.InvoiceAdjustmentGrid.RowCount > 0 &&
                //    !billingLocationComboBox.Items.Contains(request.DefaultFacility))
                //{
                //    billingLocationComboBox.Items.Add(request.DefaultFacility);
                //}
                //PopulateFacilities();
                if (billingLocationComboBox.Items.Contains(request.DefaultFacility))
                {
                    this.billingLocationComboBox.SelectedIndexChanged -= new System.EventHandler(this.billingLocationComboBox_SelectedIndexChanged);
                    billingLocationComboBox.SelectedItem = request.DefaultFacility;
                    //CR#374377
                        //PopulateFacilities();
                        billingLocationComboBox.Text = request.DefaultFacility.FacilityName;
                    this.billingLocationComboBox.SelectedIndexChanged += new System.EventHandler(this.billingLocationComboBox_SelectedIndexChanged);
                    previousFacilityName = request.DefaultFacility.FacilityName;
                    defaultFacility = request.DefaultFacility;
                }
                else
                {
                    previousFacilityName = PleaseSelect;
                    billingLocationComboBox.SelectedIndex = 0;
                    request.DefaultFacility.TaxPercentage = 0.0;
                }
            }
            if (releaseRev.ShippingDetails != null && releaseRev.ShippingDetails != release.ShippingDetails)
            {
                this.release.ShippingDetails = release.ShippingDetails = releaseRev.ShippingDetails;
            }
            else
            {
                this.release.ShippingDetails = releaseRev.ShippingDetails;
            }
            shippingInfoUI.NonPrintableAttachmentCount = request.RetrieveNonPrintableCount();
            shippingInfoUI.SetData(release.ShippingDetails, countryCodeDetails);            
            oldSalesTaxStatus = request.HasSalesTax;

            if (releaseRev.TaxReasons != null && release.TaxReasons.Count==0 && releaseRev.TaxReasons.Count!=0)
            {
                foreach (SalesTaxReasons taxReason in releaseRev.TaxReasons)
                {
                    release.TaxReasons.Add(taxReason);
                }
            }

            salesTaxSummaryUI.SetData(release, request.HasSalesTax);
            Hashtable taxItemStates = salesTaxSummaryUI.RetrieveTaxItemStates();
            SetSalesTaxSummaryUI(taxItemStates);            
            chargeHistoryButton.Visible = (request.ReleaseCount > 0);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            billingPaymentActionUI.RequestLockedImage.Visible = request.IsLocked;
            if (request.IsLocked)
            {
                toolTip.SetToolTip(billingPaymentActionUI.RequestLockedImage,
                                   string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString(billingPaymentActionUI.RequestLockedImage.Name), request.InUseRecord.UserId));
            }

            this.release.TotalPages = documentChargeUI.TotalPages;
            release.Details = null;


            if (release.IsReleased || (request.Status == RequestStatus.Denied ||
                                       request.Status == RequestStatus.Canceled))
            {
                DisableBillingInfo();
                UpdatePendingReleaseCost();
            }

            UnbillableCheckBoxChecked(release.IsUnbillable);

            //hasSalesTax = request.HasSalesTax;
            billingPaymentActionUI.Enabled = this.Enabled = IsAllowed(ROISecurityRights.ROICreateRequest) &&
                                                            IsAllowed(ROISecurityRights.ROIModifyRequest);
            EnableButtons(false);
            
            EnablePreBillButton(true);
            EnableReleaseButton(true);
            EnableInvoiceButton(true);
            isDirty = false;

            if (!doSave && !release.IsReleased)
            {
                doSave = true;
            }
            double balanceDue = TotalRequestCost - AdjustmentPaymentTotal;
            balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue);
            
            //if (adjustmentPaymentInfoUI.InvoiceAdjustmentGrid.RowCount > 0 && Math.Round(BalanceDue, 2) != adjustmentPaymentInfoUI.InitialBalanceDue)
            //{
            //    billingPaymentActionUI.SaveButton.Enabled = true;
            //    //EnablePreBillButton(false);
            //    EnableReleaseButton(false);
            //    EnableInvoiceButton(false);
            //}
            isSetdata = false;
            if (!isRevert)
            {

                releaseCore.DocumentCharges.Clear();
                foreach (DocumentChargeDetails docChargeDet in release.DocumentCharges)
                {
                    releaseCore.DocumentCharges.Add(docChargeDet);
                }
                releaseCore.ShippingDetails = releaseRev.ShippingDetails;
                if (releaseCore.TaxReasons.Count == 0 && releaseRev.TaxReasons.Count!=0)
                {
                    foreach (SalesTaxReasons reason in releaseRev.TaxReasons)
                    {
                        releaseCore.TaxReasons.Add(reason);
                    }
                }
               //SaveAndBillInfo(releaseCore);

            }
            billingLocationComboBox.Enabled = request.ReleaseCount > 0 ? false : true;
            UnbillablecheckBox.Enabled = request.ReleaseCount > 0 ? false : true;

            newChargeTaxValueLabel.Text = ((PendingReleaseCost == 0.0) ? ReleaseDetails.FormattedAmount(0.0) : ReleaseDetails.FormattedAmount(salesTaxSummaryUI.TotalTaxAmount)) + "   )";
            EnableUnBillableLabel();
        }

        private void EnableUnBillableLabel()
        {
            if (UnbillablecheckBox.Checked)
            {
                newChargeTaxLabel.Visible = true;
                totalInvoicedTaxLabel.Visible = true;
                pendingReleaseCostTaxLabel.Visible = false;
                requestCostTaxLabel.Visible = false;
                newChargeTaxValueLabel.Visible = false;
                totalInvoicedTaxValueLabel.Visible = false;
            }
            else
            {
                newChargeTaxLabel.Visible = false;
                totalInvoicedTaxLabel.Visible = false;
                pendingReleaseCostTaxLabel.Visible = true;
                requestCostTaxLabel.Visible = true;
                newChargeTaxValueLabel.Visible = true;
                totalInvoicedTaxValueLabel.Visible = true;
            }
        }

        /// <summary>
        /// Gets the release data
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        private object GetData(object data)
        {
            ReleaseDetails release = (data == null) ? new ReleaseDetails() : (ReleaseDetails)data;

            //Additional Release Info
            release.ReleaseCost = PendingReleaseCost;
            release.PreviouslyReleasedCost = PreviouslyReleasedCost;
            release.AdjustmentPaymentTotal = AdjustmentPaymentTotal;
            release.TotalPages = documentChargeUI.TotalPages;
            //if (request.Releases.Count == 0)
            //{
            //    release.TotalPagesReleased = 0;
            //}
            //else
            //{
            //    release.TotalPagesReleased = release.TotalPages + previouslyReleasedPages;
            //}

            release.RequestId = request.Id;
            //release.IsReleased = false;

            //Request transaction (Adjustment && Payment)
            release.RequestTransactions.Clear();            
            release.AdjustmentPaymentTotal = AdjustmentPaymentTotal;
            //CR#366859 - Break up sales tax for Totals and Balance Due for pending and previously released costs
            release.SalesTaxTotalAmount = (!release.IsReleased) ? TotalTaxAmount : salesTaxSummaryUI.TotalTaxAmount;
            release.BalanceDue = BalanceDue;
            release.TotalRequestCost = TotalRequestCost;

            //Fee charge
            release.FeeCharges.Clear();
            feeChargeUI.GetData(release);

            //Shipping Info
            shippingInfoUI.GetData(release);

            if (shippingInfoUI.IsUpdateRequestorAddress)
            {
                ShippingInfo shippingInfo = release.ShippingDetails;
                if (shippingInfoUI.AddressType == "Alternate")
                {
                    request.Requestor.AltAddress.Address1 = shippingInfo.ShippingAddress.Address1;
                    request.Requestor.AltAddress.Address2 = shippingInfo.ShippingAddress.Address2;
                    request.Requestor.AltAddress.Address3 = shippingInfo.ShippingAddress.Address3;
                    request.Requestor.AltAddress.City = shippingInfo.ShippingAddress.City;
                    request.Requestor.AltAddress.State = shippingInfo.ShippingAddress.State;
                    request.Requestor.AltAddress.PostalCode = shippingInfo.ShippingAddress.PostalCode;
                }
                else
                {
                request.Requestor.MainAddress.Address1 = shippingInfo.ShippingAddress.Address1;
                request.Requestor.MainAddress.Address2 = shippingInfo.ShippingAddress.Address2;
                request.Requestor.MainAddress.Address3 = shippingInfo.ShippingAddress.Address3;
                request.Requestor.MainAddress.City = shippingInfo.ShippingAddress.City;
                request.Requestor.MainAddress.State = shippingInfo.ShippingAddress.State;
                request.Requestor.MainAddress.PostalCode = shippingInfo.ShippingAddress.PostalCode;
                }
                request.Requestor = RequestorController.Instance.UpdateRequestor(request.Requestor);
                
            }

            //if (!documentChargeUI.IsEnabled && (request.Status != RequestStatus.Canceled && 
            //                                 request.Status != RequestStatus.Denied))
            //{
            //    release.ShippingDetails.ShippingCharge = 0;
            //    release.ShippingDetails.TrackingNumber = string.Empty;
            //    release.ShippingDetails.ShippingWeight = 0;
            //    release.ShippingDetails.ShippingMethodId = 0;
            //    release.FeeChargeTotal = 0;
            //    return release;
            //}

            //Document charge
            //CR# 368485 - Document charge amount gets 0.0 after invoice and release the document
            Collection<DocumentChargeDetails> tempDocChargeDetails = (Collection<DocumentChargeDetails>)ROIViewUtility.DeepClone(release.DocumentCharges);
            release.DocumentCharges.Clear();
            documentChargeUI.GetData(release.DocumentCharges);
            release.DocumentChargeTotal = documentChargeUI.DocumentCharge;
            //CR# 368485 - Document charge amount gets 0.0 after invoice and release the document
            RestoreDocumentChargeTotalPages(tempDocChargeDetails, release.DocumentCharges);

            //Reasons
            release.TaxReasons.Clear();
            salesTaxSummaryUI.GetData(release);
            return release;
        }

        //CR# 368485 - Document charge amount gets 0.0 after invoice and release the document
        /// <summary>
        /// Restore the total pages of document charges after clearing
        /// </summary>
        /// <param name="tempDocChargeDetails"></param>
        private void RestoreDocumentChargeTotalPages(Collection<DocumentChargeDetails> tempDocChargeDetails, Collection<DocumentChargeDetails> docChargeDetails)
        {
            foreach (DocumentChargeDetails docCharge in docChargeDetails)
            {
                foreach (DocumentChargeDetails tempDocCharge in tempDocChargeDetails)
                {
                    if (docCharge.BillingTierId == tempDocCharge.BillingTierId)
                    {
                        docCharge.TotalPages = tempDocCharge.TotalPages;
                        docCharge.ReleaseCount = tempDocCharge.ReleaseCount;
                        break;
                    }
                }
            }
        }
        private static bool isPrePopulate;
        public static bool IsPrePopulate
        {
            get { return isPrePopulate; }
            set { isPrePopulate = value;}
        }
        /// <summary>
        /// PrePopulates admin entities
        /// </summary>
        /// <param name="request"></param>
        /// <param name="chargeHistories"></param>
        public void PrePopulate(RequestDetails request)
        {
            this.request = request;
            //CR#374377
            requestBillingInfo = RequestController.Instance.RetrieveRequestBillingPaymentInfo(request.Id);
            
            IsPrePopulate = false;
            if (requestBillingInfo == null)
            {
                isPrePopulate = true;
            }
            if (requestBillingInfo != null)
            {
                UnAppliedAdjustmentPaymentTotal = requestBillingInfo.UnAppliedAmount;
                request.DefaultFacility.FacilityCode = requestBillingInfo.BillingLocCode.Equals(string.Empty) ? "Please Select" : requestBillingInfo.BillingLocCode;
                request.DefaultFacility.FacilityName = requestBillingInfo.BillingLocName.Equals(string.Empty) ? "Please Select" : requestBillingInfo.BillingLocName;
                request.DefaultFacility.TaxPercentage = requestBillingInfo.SalestaxPercentageForBillingLoc;
            }

            BillingPaymentInfoDetails billingPaymentInfoDetails = BillingAdminController.Instance.RetrieveBillingAndPaymentInfo(request.RequestorType);

            RequestorTypeDetails requestorType = billingPaymentInfoDetails.RequestorTypeDetails;

            //Collection<BillingTierDetails> billingTiers = BillingAdminController.Instance.RetrieveAllBillingTiers();
            //List<BillingTierDetails> billingTierList = new List<BillingTierDetails>(billingTiers);
            //billingTierList = billingTierList.FindAll(delegate(BillingTierDetails billingTier) 
            //                                         { return ( billingTier.Name != ROIConstants.Electronic && 
            //                                                    billingTier.Id != requestorType.HpfBillingTier.Id ); 
            //                                         });
            //billingTiers = new Collection<BillingTierDetails>(billingTierList);

            Collection<BillingTemplateDetails> billingtemplates = billingPaymentInfoDetails.BillingTemplateDetails;

            Collection<FeeTypeDetails> feeTypes = billingPaymentInfoDetails.FeeTypeDetails;

            PopulateBillingTemplates(requestorType, billingtemplates, feeTypes);
            //PopulateFacilities();

            Collection<DeliveryMethodDetails> deliveryMethods = billingPaymentInfoDetails.DeliveryMethodDetails;
            Collection<PaymentMethodDetails> paymentMethods = billingPaymentInfoDetails.PaymentMethodDetails;
            Collection<ReasonDetails> adjustmentReasons = billingPaymentInfoDetails.ReasonDetails;
            PageWeightDetails pageWightDetails = billingPaymentInfoDetails.PageWeightDetails;
            countryCodeDetails = billingPaymentInfoDetails.CountryCodeDetails;
            //CR# - 349862
            OutputPropertyDetails outputPropertyDetails = null;
            try
            {
                Application.DoEvents();
                outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                log.FunctionFailure(cause);
            }
            finally
            {
                PopulateFacilities();
                shippingInfoUI.PrePopulate(deliveryMethods, request.Requestor, outputPropertyDetails, request.RetrieveNonPrintableCount(), pageWightDetails);                

                //If the user doesnt have access to the mapped billing location for a request and invoice is not created, 
                //then display the sales tax elements as 0 in salestaxsummary UI
                if (request.DefaultFacility != null)
                {
                    if (!billingLocationComboBox.Items.Contains(request.DefaultFacility) && !request.IsInvoiced)
                    {
                        request.DefaultFacility.TaxPercentage = 0.0;
                    }
                }

                salesTaxSummaryUI.PrePopulate(request);
            }
            //CR# - 349862
        }

        /// <summary>
        /// Populate the billing location.
        /// </summary>
        private void PopulateBillingLocation()
        {
            requestBillingInfo = RequestController.Instance.RetrieveRequestBillingPaymentInfo(request.Id);
            request.DefaultFacility.FacilityCode = requestBillingInfo.BillingLocCode.Equals(string.Empty) ? "Please Select" : requestBillingInfo.BillingLocCode;
            request.DefaultFacility.FacilityName = requestBillingInfo.BillingLocName.Equals(string.Empty) ? "Please Select" : requestBillingInfo.BillingLocName;
            request.DefaultFacility.TaxPercentage = requestBillingInfo.SalestaxPercentageForBillingLoc;
            UnAppliedAdjustmentPaymentTotal = requestBillingInfo.UnAppliedAmount;
            IsPrePopulate = false;
        }

        /// <summary>
        /// Pre-Populate the facilities...
        /// </summary>
        private void PopulateFacilities()
        {
            //CR#374377
            Collection<TaxPerFacilityDetails> taxPerFacilities;
            billingLocationComboBox.Items.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string pleaseSelect = rm.GetString(ROIConstants.PleaseSelect);
            if (UserData.Instance.IsLdapEnabled)
            {
                Application.DoEvents();
                taxPerFacilities = BillingAdminController.Instance.RetrieveAllTaxPerFacilities(UserData.Instance.HpfUserId);
            }
            else
            {
                Application.DoEvents();
                taxPerFacilities = BillingAdminController.Instance.RetrieveAllTaxPerFacilities(UserData.Instance.UserId);
            }
            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();
            taxPerFacilityDetails.FacilityCode = pleaseSelect;
            taxPerFacilityDetails.FacilityName = pleaseSelect;
            this.billingLocationComboBox.SelectedIndexChanged -= new System.EventHandler(this.billingLocationComboBox_SelectedIndexChanged);            
            
            foreach (TaxPerFacilityDetails fac in taxPerFacilities)
            {
                billingLocationComboBox.Items.Add(fac);
                // CR#365397
				if (request.DefaultFacility != null && !string.IsNullOrEmpty(request.DefaultFacility.Name) 
                    && !request.IsInvoiced && fac.Equals(request.DefaultFacility))
                {
                    request.DefaultFacility = fac;
                }
            }
            billingLocationComboBox.Items.Insert(0, taxPerFacilityDetails);
            billingLocationComboBox.DisplayMember = "FacilityName";
            billingLocationComboBox.ValueMember = "FacilityCode";
            billingLocationComboBox.SelectedIndex = 0;
            this.billingLocationComboBox.SelectedIndexChanged += new System.EventHandler(this.billingLocationComboBox_SelectedIndexChanged);

        }

        /// <summary>
        /// Method to be executed when billing location combo box is changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void billingLocationComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {            
            request.DefaultFacility = (TaxPerFacilityDetails)billingLocationComboBox.SelectedItem;
            salesTaxSummaryUI.FacilityTaxPercentage = request.TaxPercentage;
            documentChargeUI.CalculateTax(request.TaxPercentage);
            feeChargeUI.CalculateTax(request.TaxPercentage);
            salesTaxSummaryUI.PrePopulate(request);
            Hashtable taxItemStates = salesTaxSummaryUI.RetrieveTaxItemStates();
            SetSalesTaxSummaryUI(taxItemStates);
        }


        /// <summary>
        /// Populates billing templates
        /// </summary>
        /// <param name="requestorType"></param>
        private void PopulateBillingTemplates(RequestorTypeDetails requestorType, Collection<BillingTemplateDetails> billingtemplates, Collection<FeeTypeDetails> feeTypes)
        {
            Hashtable htBillingTempaltes = new Hashtable(billingtemplates.Count);
            foreach (BillingTemplateDetails template in billingtemplates)
            {
                htBillingTempaltes.Add(template.Id, template);
            }
            Collection<BillingTemplateDetails> reqTypeBillingTypes = new Collection<BillingTemplateDetails>();
            
            BillingTemplateDetails billingTemplate;
            BillingTemplateDetails defaultBillingTemplate = null;
            foreach (AssociatedBillingTemplate associatedBillingTemplate in requestorType.AssociatedBillingTemplates)
            {
                if(htBillingTempaltes.ContainsKey(associatedBillingTemplate.BillingTemplateId))
                {
                    billingTemplate = (BillingTemplateDetails)htBillingTempaltes[associatedBillingTemplate.BillingTemplateId];
                    reqTypeBillingTypes.Add(billingTemplate);
                    if (associatedBillingTemplate.IsDefault)
                    {
                        defaultBillingTemplate = billingTemplate;
                    }
                }
            }

            feeChargeUI.PrePopulate(reqTypeBillingTypes, feeTypes, defaultBillingTemplate);
            
        }

        /// <summary>
        /// Enables the buttons
        /// </summary>
        /// <param name="enable"></param>
        private void EnableButtons(bool enable)
        {
            billingPaymentActionUI.SaveButton.Enabled = enable;// && documentChargeUI.TotalPages > 0;
            billingPaymentActionUI.RevertButton.Enabled = enable;// && documentChargeUI.TotalPages > 0;
        }

        private void UnbillableCheckBoxChecked(bool check)
        {
            UnbillablecheckBox.Checked = check;
        }

        private void EnableReleaseButton(bool enable)
        {
            if ((shippingInfoUI.IsPrint || shippingInfoUI.IsFax) && request.RetrieveNonPrintableCount() > 0)
            {
                enable = shippingInfoUI.NonPrinableComboBox.SelectedIndex > 0;
            }

            billingPaymentActionUI.ReleaseButton.Enabled    = (enable &&
                                                              unreleasedCount > 0 &&
                                                              releasedPageCount == 0 &&
                                                              //(release.Id > 0  || request.IsReleased) && 
                                                              !release.IsReleased &&
                                                              documentChargeUI.IsEnabled &&
                                                              documentChargeUI.TotalPages > 0 &&
                                                              (IsAllowed(ROISecurityRights.ROIPrintFax) || IsAllowed(ROISecurityRights.ROIExportToPDF)) &&               
                                                              (release.ShippingDetails != null && release.ShippingDetails.OutputMethod != OutputMethod.None) && billingLocationComboBox.SelectedIndex > 0);
            billingPaymentActionUI.RereleaseButton.Enabled = (enable &&
                                                                          releasedPageCount > 0 &&
                                                                          unreleasedCount == 0 &&
                                                                          //(release.Id > 0 || request.IsReleased) &&
                                                                          !release.IsReleased &&
                                                                          documentChargeUI.IsEnabled &&
                                                                          documentChargeUI.TotalPages > 0 &&
                                                                          (IsAllowed(ROISecurityRights.ROIPrintFax) || IsAllowed(ROISecurityRights.ROIExportToPDF)) &&
                                                                          (release.ShippingDetails != null && release.ShippingDetails.OutputMethod != OutputMethod.None));

            //CR#377567
            
            if (hasRights == false)
                billingPaymentActionUI.ReleaseButton.Enabled = billingPaymentActionUI.RereleaseButton.Enabled = false;
        }

        private void EnablePreBillButton(bool enable)
        {
            billingPaymentActionUI.PreBillButton.Enabled    = (enable &&
                                                               release.Id > 0 &&
                                                               documentChargeUI.TotalPages > 0 &&
                                                               (request.Status != RequestStatus.Canceled &&
                                                               request.Status != RequestStatus.Denied));
        }

        private void EnableInvoiceButton(bool enable)
        {
            billingPaymentActionUI.PreBillButton.Enabled = (enable &&
                                                            (release.Id > 0 || (request.HasDraftRelease || (request.ReleaseCount > 0 ? true : false))) &&
                                                            documentChargeUI.TotalPages > 0);
            billingPaymentActionUI.InvoiceButton.Enabled =  Math.Round(BalanceDue - release.InvoicesBalanceDue, 2) > 0 && (enable &&
                                                            (release.Id > 0 || (request.HasDraftRelease || (request.ReleaseCount > 0 ? true : false))) &&
                                                            documentChargeUI.TotalPages > 0);

            //if (isSave && (release.ShippingDetails.ShippingMethodId > 0))
            //if ((isSave || isSetdata) &&  (shippingInfoUI.ShippingMethodSelected||releaseRev.ShippingDetails.OutputMethod.ToString()!=string.Empty))
    //        if((isSave&&shippingInfoUI.ShippingMethodSelected))
    //        {
    //            bool con1 = (Math.Round(BalanceDue - adjustmentPaymentInfoUI.RetrieveAllInvoiceBalanceDue(), 2) > 0);
    //            bool con2 = Math.Round(BalanceDue, 2) > 0;
    //            bool con3 = true;
    //            bool con4 = documentChargeUI.TotalPages > 0;
    //            billingPaymentActionUI.PreBillButton.Enabled =
    //billingPaymentActionUI.InvoiceButton.Enabled = con1 && con2
    //                                                && (enable &&
    //                                                (con3 || request.IsReleased) && con4);
    //        }
    //        else if (releaseRev.ShippingDetails != null)
    //        {
    //            if (isSetdata && releaseRev.ShippingDetails.OutputMethod!=OutputMethod.None)
    //            {

    //                bool con1 = (Math.Round(BalanceDue - adjustmentPaymentInfoUI.RetrieveAllInvoiceBalanceDue(), 2) > 0);
    //                bool con2 = Math.Round(BalanceDue, 2) > 0;
    //                bool con3 = true;
    //                bool con4 = documentChargeUI.TotalPages > 0;
    //                billingPaymentActionUI.PreBillButton.Enabled =
    //    billingPaymentActionUI.InvoiceButton.Enabled = con1 && con2
    //                                                    && (enable &&
    //                                                    (con3 || request.IsReleased) && con4);
    //            }
    //        }
        }
                                                                                                                                                                                                   
        /// <summary>
        /// Updates the pending release cost
        /// </summary>
        public void UpdatePendingReleaseCost()
        {
            if (release == null) return;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            double pendingReleaseCost = (release.IsReleased) ? 0 : PendingReleaseCost;
            ////pendingReleaseCostLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
            //                                           rm.GetString(pendingReleaseCostLabel.Name + "." + Pane.GetType().Name),
            //                                           ReleaseDetails.FormattedAmount(pendingReleaseCost));

            pendingReleaseCostValueLabel.Text = ReleaseDetails.FormattedAmount(pendingReleaseCost);

            shippingInfoUI.UpdateShippingWeight(documentChargeUI.TotalPages);
            UpdateBalance();
        }
        
        /// <summary>
        /// Update total tax value
        /// </summary>
        public void UpdateTotalTax()
        {
            if (release == null) return;
            newChargeTaxValueLabel.Text = ReleaseDetails.FormattedAmount(salesTaxSummaryUI.TotalTaxAmount) + "   )";
            UpdatePendingReleaseCost();
            //UpdateBalanceDue();
        }

        /// <summary>
        /// set the applytax checkbox value based on the apply tax checkbox value
        /// </summary>
        public void UpdateApplyTaxState()
        {
            applyTaxCheckBox.Checked = salesTaxSummaryUI.ApplyTaxState;
        }


        /// <summary>
        /// update the sales tax amount if user changed the copies or amount value in Document charge, Fee and Custom type 
        /// </summary>
        public void UpdateTaxAmount()
        {
            Hashtable taxItemStates = salesTaxSummaryUI.RetrieveTaxItemStates();
            SetSalesTaxSummaryUI(taxItemStates);
        }
        public void UpdateBalance()
        {
            previousReleaseCostValueLabel.Text = ReleaseDetails.FormattedAmount(PreviouslyReleasedCost);

            double totalRequestCost = TotalRequestCost;
            totalRequestCostValueLabel.Text = ReleaseDetails.FormattedAmount(totalRequestCost);

            //Total Request Cost
            double invoiceAmount = release.InvoicesBalanceDue;
            totalInvoicedCostValueLabel.Text = ReleaseDetails.FormattedAmount(invoiceAmount);
            invoicedAmountValueLabel.ForeColor = invoiceAmount < 0 ? Color.FromArgb(32, 125, 41) : Color.Black;
            double unAppliedAdjPayTotal = RetrieveUnAppliedAmount(request.Id);
            double adjustmentTotal = AdjustmentPaymentTotal;
            double balanceDue = BalanceDue;
            unAppliedAdjAndPayValueLabel.Text = ReleaseDetails.FormattedAmount(unAppliedAdjPayTotal);
            Collection<RequestInvoiceDetail> reqInvoices = RequestController.Instance.RetrieveRequestInvoices(request.Id);
                if (reqInvoices.Count < 1)
                {
                    adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(adjustmentTotal);
                    balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue);
                }
                else
                {
                    foreach (RequestInvoiceDetail reqInv in reqInvoices)
                    {
                        if ((reqInv.InvoiceType == "Prebill" || reqInv.InvoiceType == "Invoice") && (reqInv.RequestId == request.Id))
                        {
                            totalRequestCost = reqInv.Charges;
                            balanceDue = totalRequestCost - reqInv.PayAdjTotal;
                            if(balanceDue < 0)
                            {
                                double amt = Math.Abs(balanceDue);
                                balanceDue = 0;
                            }
                            //BillingController.Instance.updateInvoiceBalance(reqInv.Id, balanceDue);
                            adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(reqInv.PayAdjTotal);
                            balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue);
                        }
                    }
                }
                    if (balanceDue == 0)
                    {
                        balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.Black;
                    }
                    else if (balanceDue < 0)
                    {
                        balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.FromArgb(32, 125, 41);
                    }
                    else
                    {
                        balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.Red;
                    }
                    discountsValueLabel.Text = ReleaseDetails.FormattedAmount(UnbillableAmount);
                    totalInvoicedTaxValueLabel.Text = ReleaseDetails.FormattedAmount(UnbillableAmount);

                }
            
        

        /// <summary>
        /// Update the balance due
        /// </summary>
        private void UpdateBalanceDue()
        {
            //Previouly Released Cost
            previousReleaseCostValueLabel.Text = ReleaseDetails.FormattedAmount(PreviouslyReleasedCost);
            
            double totalRequestCost = TotalRequestCost;
            totalRequestCostValueLabel.Text = ReleaseDetails.FormattedAmount(totalRequestCost);

            //Total Request Cost
            double invoiceAmount = release.InvoicesBalanceDue;
            totalInvoicedCostValueLabel.Text = ReleaseDetails.FormattedAmount(invoiceAmount);
            invoicedAmountValueLabel.ForeColor = invoiceAmount < 0 ? Color.FromArgb(32, 125, 41) : Color.Black;

            //CR#374377
            //RequestBillingInfo tempInfo = new RequestBillingInfo();
            //tempInfo = RequestController.Instance.RetrieveRequestBillingPaymentInfo(request.Id);
            //this.release.CreditAdjustmentTotal = tempInfo.CreditAdjustmentAmount;
            //this.release.DebitAdjustmentTotal = tempInfo.DebitAdjustmentAmount;
            //this.release.PaymentTotal = requestBillingInfo.PaymentAmount;

            //ReleaseDetails tempRelease = new ReleaseDetails();
            //tempRelease = RevertInfo(request);
            //this.release.CreditAdjustmentTotal = tempRelease.CreditAdjustmentTotal;
            //this.release.DebitAdjustmentTotal = tempRelease.DebitAdjustmentTotal;
            //this.release.PaymentTotal = tempRelease.PaymentTotal;

            //Adjustment Total
            double adjustmentTotal = AdjustmentPaymentTotal;
            //double unAppliedAdjPayTotal = UnAppliedAdjustmentPaymentTotal;
            double unAppliedAdjPayTotal = RetrieveUnAppliedAmount(request.Id);
            double balanceDue = BalanceDue;
            double balance;
            
            Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(request.RequestorId);
            if (releaseDialog != null)
            {
                UpdateUIWithDetails();
            }
            else
            {
                unAppliedAdjAndPayValueLabel.Text = ReleaseDetails.FormattedAmount(unAppliedAdjPayTotal);
                if ((reqInvoices.Count < 1) || (request.Status == RequestStatus.Logged) || (request.Status == RequestStatus.Completed))
                {
                    adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(adjustmentTotal);
                    balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(balanceDue);
                }
                else
                {
                    foreach (RequestInvoiceDetail reqInv in reqInvoices)
                    {
                        if ((reqInv.InvoiceType == "Prebill") && (reqInv.RequestId == request.Id))
                        {
                            adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(reqInv.PayAdjTotal);
                            balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(reqInv.Balance);
                        }
                    }
                }
            }              
            

            adjPaymentTotalValueLabel.ForeColor = (adjustmentTotal < 0) ? Color.FromArgb(32, 125, 41) : Color.Black;
            double invoiceBaseCharge = Math.Round(BalanceDue - release.InvoicesBalanceDue, 2);
            

            


            //Added for CR#377438
            //List<RequestorUnappliedAmountDetail> ReqstrUnApplied = new List<RequestorUnappliedAmountDetail>();
            //ReqstrUnApplied = RequestorController.Instance.RetrieveUnappliedAmountDetails(request.Id);
            //unAppliedAdjPayTotal = 0;
            //foreach (RequestorUnappliedAmountDetail req in ReqstrUnApplied)
            //{
            //    if (req.Type == "Unapplied Payment")
            //        unAppliedAdjPayTotal+=req.Amount;
            //    else if (req.Type == "Unapplied Adjustment")
            //        unAppliedAdjPayTotal+=req.Amount;
            //}

            //if (releaseDialog != null)
            //{
            //    if (releaseDialog.AppliedAmount > 0)
            //    {
            //        if (unAppliedAdjPayTotal >= release.InvoicesBalanceDue)
            //        {
            //            unAppliedAdjPayTotal -= release.InvoicesBalanceDue;
            //        }
            //        else
            //        {
            //        unAppliedAdjPayTotal = 0.0;
            //        }
            //    }
            //}


            //Apply Tax
            this.applyTaxCheckBox.CheckedChanged -= new System.EventHandler(this.applyTaxCheckBox_CheckedChanged);
            applyTaxCheckBox.Checked = request.HasSalesTax;
            applyTaxLabel.Visible = false; // request.HasSalesTax;
            this.applyTaxCheckBox.CheckedChanged += new System.EventHandler(this.applyTaxCheckBox_CheckedChanged);

            ////CR359256 - Add "Invoiced Amount" to show the sum of current invoice balances
            //double invoiceAmount = release.InvoicesBalanceDue;
            //invoicedAmountValueLabel.Text = ReleaseDetails.FormattedAmount(invoiceAmount);
            //invoicedAmountValueLabel.ForeColor = invoiceAmount < 0 ? Color.FromArgb(32, 125, 41) : Color.Black;
            discountsValueLabel.Text = ReleaseDetails.FormattedAmount(UnbillableAmount);

            
            if (balanceDue == 0)
            {
                balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.Black;
            }
            else if (balanceDue < 0)
            {
                balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.FromArgb(32, 125, 41);
            }
            else
            {
                balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.Red;
            }

            //Invioce Sales Tax Amount
            totalInvoicedTaxValueLabel.Text = ((!salesTaxSummaryUI.IsEnabled) ? ReleaseDetails.FormattedAmount(release.InvoicesSalesTaxAmount) :
                                               ReleaseDetails.FormattedAmount(release.InvoicesSalesTaxAmount + salesTaxSummaryUI.TotalTaxAmount)) + "   )";
        }

        //US16834 - changes to Include requests in the pre-bill status on the payments popup.
        private void updateBalancePrebill()
        {            
            double UnbillableAmt = 0;
            double totalAppliedAmount=0;
            double totalBalanceDue;
            double totalUnappliedAmount= 0;            
            double totalUnAppliedPaymentAmount = 0.0;
            double totalUnAppliedAdjustmentAmount = 0.0;         


            Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(request.RequestorId);
            ComparableCollection<RequestInvoiceDetail> invList = new ComparableCollection<RequestInvoiceDetail>(reqInvoices);
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = (ComparableCollection<RequestInvoiceDetail>)invList;

            foreach (RequestInvoiceDetail reqInv in requestInvoiceDetailList)
            {
                if (reqInv.InvoiceType.Equals("Unapplied Payment") || reqInv.InvoiceType.Equals("Unapplied Adjustment"))
                {
                    //totalUnAppliedAdjustmentAmount += Math.Abs(reqInv.AdjAmount);
                    //totalUnAppliedPaymentAmount += Math.Abs(reqInv.AdjPay);
                    totalUnappliedAmount += Math.Abs(reqInv.Balance);
                    totalAppliedAmount = reqInv.AppliedAmount;
                    UnbillableAmt = reqInv.UnBillableAmount;
                    //totalUnappliedAmount = totalUnAppliedAdjustmentAmount + totalUnAppliedPaymentAmount;
                }
                else if (reqInv.InvoiceType.Equals("Prebill"))
                {
                    totalUnAppliedAdjustmentAmount += Math.Abs(reqInv.AdjAmount);
                    totalUnAppliedPaymentAmount += Math.Abs(reqInv.AdjPay);
                    totalAppliedAmount = totalUnAppliedAdjustmentAmount + totalUnAppliedPaymentAmount;
                    UnbillableAmt = reqInv.UnBillableAmount;
                    totalUnappliedAmount = Math.Abs(reqInv.PayAdjTotal + reqInv.PayAmount);
                }                                               
            }
                 
                   

            double totalRequestCost = TotalRequestCost;
            previousReleaseCostValueLabel.Text = ReleaseDetails.FormattedAmount(PreviouslyReleasedCost);
            totalRequestCostValueLabel.Text = ReleaseDetails.FormattedAmount(totalRequestCost);
            

            //Total Request Cost
            double invoiceAmount = release.InvoicesBalanceDue;
            totalInvoicedCostValueLabel.Text = ReleaseDetails.FormattedAmount(invoiceAmount);
            invoicedAmountValueLabel.ForeColor = invoiceAmount < 0 ? Color.FromArgb(32, 125, 41) : Color.Black;
            adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(totalAppliedAmount);
            unAppliedAdjAndPayValueLabel.Text = ReleaseDetails.FormattedAmount(totalUnappliedAmount);
            //balanceDue_Prebill = totalBalanceDue
            totalBalanceDue = totalRequestCost - totalAppliedAmount;
            balanceDuePrebill = totalBalanceDue; ;
            balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(totalBalanceDue);
            if (totalBalanceDue == 0)
            {
                balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.Black;
            }
            else if (totalBalanceDue < 0)
            {
                balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.FromArgb(32, 125, 41);
            }
            else
            {
                balanceDueLabel.ForeColor = balanceDueValueLabel.ForeColor = Color.Red;
            }
            discountsValueLabel.Text = ReleaseDetails.FormattedAmount(UnbillableAmount);
            totalInvoicedTaxValueLabel.Text = ReleaseDetails.FormattedAmount(UnbillableAmount);

        }

        public void UpdateUIWithDetails()
        {
            double requestammount;
            requestBillingInfo = RequestController.Instance.RetrieveRequestBillingPaymentInfo(request.Id);
            if (request.Status == RequestStatus.PreBilled)
            { requestammount = requestBillingInfo.TotalRequestCost; }
            else
            { requestammount = requestBillingInfo.InvoiceBaseCharge; }
            double paymentammt = requestBillingInfo.PaymentAmount;
            double creditadjamnt = requestBillingInfo.CreditAdjustmentAmount;
            double balance;
            adjPaymentTotalValueLabel.Text = ReleaseDetails.FormattedAmount(paymentammt + creditadjamnt);

            double balanceDue = Convert.ToDouble(balanceDueValueLabel.Text.Trim().Substring(1, balanceDueValueLabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
            balanceDuePrebill = balanceDue;
            if (balanceDue > 0)
            {
                balance = requestammount - (paymentammt + creditadjamnt);
                balanceDuePrebill = balance;
                balanceDueValueLabel.Text = ReleaseDetails.FormattedAmount(requestammount - (paymentammt + creditadjamnt));
            }

                    unAppliedAdjAndPayValueLabel.Text = ReleaseDetails.FormattedAmount(requestBillingInfo.UnAppliedAmount);
        }
        /// <summary>
        /// Occurs when user clicks the Charge History button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void chargeHistoryButton_Click(object sender, EventArgs e)
        {
            
            ChargeHistoryDialogUI chargeHistoryDialog = new ChargeHistoryDialogUI(Pane);
            chargeHistoryDialog.SetData(chargeHistories);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form chargeHistoryform = ROIViewUtility.ConvertToForm(null, rm.GetString("chargehistory.title"), chargeHistoryDialog);
            DialogResult result = chargeHistoryform.ShowDialog(this);
            if (result == DialogResult.OK)
            {
                chargeHistoryform.Close();
                chargeHistoryform.Dispose();
            }
        }

        /// <summary>
        /// Occurs when user clicks on PreBill button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void prebillButton_Click(object sender, EventArgs e)
        {
            try
            {
                if (billingLocationComboBox.SelectedIndex == 0)
                {
                    throw new ROIException(ROIErrorCodes.BillingLocationNotSelected);
                }
                DisplayPreBillInvoiceUI(LetterType.PreBill.ToString());
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                } 
            }
            finally
            {
                OutputFileDialog.CloseSplashScreen();
                OutputPrintDialog.CloseSplashScreen();
                OutputFaxDialog.CloseSplashScreen();
                OutputEmailDialog.CloseSplashScreen();
                OutputDiscDialog.CloseSplashScreen();
               
            }

        }

        /// <summary>
        ///  Occurs when user clicks on Invoice button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceButton_Click(object sender, EventArgs e)
        {
            try
            {
                if (billingLocationComboBox.SelectedIndex == 0)
                {
                    throw new ROIException(ROIErrorCodes.BillingLocationNotSelected);
                }

                if (doSave)
                {
                    Save();
                }

                DisplayPreBillInvoiceUI(LetterType.Invoice.ToString());	            
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }               
            }
        }

        /// <summary>
        /// Reset the invoice previous balance due after save
        /// </summary>
        /// <param name="invoiceChargeDetailsList"></param>
        private void ResetInvoicePreviousBalanceDue(InvoiceChargeDetailsList invoiceChargeDetailsList)
        {
            foreach (InvoiceChargeDetails invoiceChargeDetail in invoiceChargeDetailsList.InvoiceCharges)
            {
                invoiceChargeDetail.PreviousBalanceDue = invoiceChargeDetail.BalanceDue;
            }
        }

        /// <summary>
        /// This ApplyTax checkbox is currently in readonly mode.The behaviour of this checkbox is based on the ApplyTax checkbox
        /// which is placed in SalesTaxSummary tab. Whenever user checked/unchecked the ApplyTax checkbox in SalesTaxSummary tab,
        /// this will also changed. Based on this checkbox the business logic is work.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void applyTaxCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            isApplyTaxStateChanged = true;            
            //hasSalesTax = applyTaxCheckBox.Checked ? true : false;            
            request.HasSalesTax = applyTaxCheckBox.Checked;
            applyTaxLabel.Visible = false; // applyTaxCheckBox.Checked;
            salesTaxSummaryUI.FacilityTaxPercentage = request.TaxPercentage;
            documentChargeUI.CalculateTax(request.TaxPercentage);
            feeChargeUI.CalculateTax(request.TaxPercentage);
            salesTaxSummaryUI.IsGridItemChanged = false;            
            Hashtable taxItemStates = salesTaxSummaryUI.RetrieveTaxItemStates();
            SetSalesTaxSummaryUI(taxItemStates);
            
        }        

        /// <summary>
        /// Displays the PreBill/Invoice UI.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        private void DisplayPreBillInvoiceUI(string letterTemplateType)
        {

            long invoiceId;
            long letterId = 0;
            bool forInvoice;
            bool forLetter = false;
            string queueSecretWord = string.Empty;
            string outputMethodName = string.Empty;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Collection<LetterTemplateDetails> letterTemplateList = ROIAdminController.Instance.RetrieveAllLetterTemplates();

            IList<LetterTemplateDetails> letterTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, letterTemplateType);

            PreBillInvoiceBaseUI preBillInvoiceBaseUI;

            if (letterTemplates.Count > 0)
            {
                if (letterTemplateType == LetterType.PreBill.ToString())
                {
                    preBillInvoiceBaseUI = new PreBillUI(Pane);
                }
                else
                {
                    preBillInvoiceBaseUI = new InvoiceUI(Pane);
                    preBillInvoiceBaseUI.PopulateInvoiceDueDays();
                }

                Form dialog = ROIViewUtility.ConvertToForm(null,
                                                           rm.GetString("title." + letterTemplateType),
                                                           preBillInvoiceBaseUI);

                long defaultLetterId = 0;
                List<LetterTemplateDetails> list = new List<LetterTemplateDetails>(letterTemplates);
                DocumentInfo documentInfo;
                InvoiceAndDocumentDetails invoiceAndDocumentDetails = null;

                IList<LetterTemplateDetails> defaultTemplates = list.FindAll(delegate(LetterTemplateDetails item) { return item.IsDefault == true; });
                if (defaultTemplates.Count > 0)
                {
                    defaultLetterId = defaultTemplates[0].DocumentId;
                }

                preBillInvoiceBaseUI.Localize();
                preBillInvoiceBaseUI.PrePopulate(letterTemplates, defaultLetterId);

                DialogResult result = dialog.ShowDialog(this);

                dialog.Close();
                
                if (result == DialogResult.OK)
                {
                    PreBillInvoiceDetails prebillInvoiceDetails = CreatePreBillInvoiceDetails(preBillInvoiceBaseUI.LetterTemplateId,
                                                                                              preBillInvoiceBaseUI.LetterTemplateName,
                                                                                              preBillInvoiceBaseUI.NotesList,
                                                                                              preBillInvoiceBaseUI.FreeformNotesList,
                                                                                              letterTemplateType,
                                                                                              letterTemplateType == LetterType.Invoice.ToString() ? preBillInvoiceBaseUI.DueDays : 0,
                                                                                              letterTemplateType == LetterType.Invoice.ToString() ? preBillInvoiceBaseUI.IsOverWriteDueDays : false, false);

                    //CR#359276 - Add automatic adjustment transaction for the reduced amount from the current invoice
                    prebillInvoiceDetails.Release.RequestTransactions.Clear();
                    if (request.InvoiceAutoAdjustment > 0)
                    {
                        RequestTransaction txn = new RequestTransaction();
                        txn.CreatedDate = DateTime.Now;
                        txn.ReasonName = string.Empty;
                        txn.Description = ROIConstants.AutoCreditAdjustmentText;
                        txn.TransactionType = TransactionType.AutoAdjustment;
                        txn.IsDebit = false;
                        txn.Amount = Math.Round(-request.InvoiceAutoAdjustment, 2);
                        txn.AdjustmentPaymentType = AdjustmentPaymentType.Automatic;
                        prebillInvoiceDetails.Release.RequestTransactions.Add(txn);
                    }
					//Set the Adjustment and Payment total
                    //prebillInvoiceDetails.Release.CreditAdjustmentTotal = -request.InvoiceAutoAdjustment;
                    //prebillInvoiceDetails.Release.PaymentTotal = 0;
                    //prebillInvoiceDetails.Release.AdjustmentTotal = -request.InvoiceAutoAdjustment;
                    //prebillInvoiceDetails.Release.AdjustmentPaymentTotal = prebillInvoiceDetails.Release.AdjustmentTotal +
                    //                                                       prebillInvoiceDetails.Release.PaymentTotal;
                  
                    //Filling the parameters for Invoice flow
                    InvoiceOrPrebillPreviewInfo InvoiceInfo = new InvoiceOrPrebillPreviewInfo();
                    InvoiceInfo.Amountpaid = Math.Round(-request.InvoiceAutoAdjustment, 2);

                    InvoiceInfo.BaseCharge = prebillInvoiceDetails.Invoice.BaseCharge;
                    //US16834 - changes to Include requests in the pre-bill status on the payments popup.
                    if (letterTemplateType != "PreBill")
                        InvoiceInfo.InvoiceBalanceDue = prebillInvoiceDetails.Invoice.BalanceDue;
                    else
                        InvoiceInfo.InvoiceBalanceDue = 0;
                    InvoiceInfo.BaseCharge = prebillInvoiceDetails.Invoice.BaseCharge;
                    InvoiceInfo.InvoiceBillingLocCode = request.DefaultFacility.FacilityCode as String;
                    InvoiceInfo.InvoiceBillinglocName = request.DefaultFacility.FacilityName;
                    if (letterTemplateType != "PreBill")
                        InvoiceInfo.InvoiceDueDays = preBillInvoiceBaseUI.DueDays;
                    else
                        InvoiceInfo.InvoiceDueDays = 0;
                    InvoiceInfo.InvoicePrebillDate = DateTime.Now;
                    InvoiceInfo.InvoiceSalesTax = prebillInvoiceDetails.Release.SalesTaxTotalAmount;
                    InvoiceInfo.LetterTemplateFileId = prebillInvoiceDetails.LetterTemplateId;
                    InvoiceInfo.LetterTemplateName = prebillInvoiceDetails.LetterTemplateName;
                    int i = 0;
                    InvoiceInfo.Notes=new string[prebillInvoiceDetails.Notes.Count];
                    foreach (string note in prebillInvoiceDetails.Notes)
                    {
                        InvoiceInfo.Notes[i]= prebillInvoiceDetails.Notes[i];
                        i++;
                    }
                    InvoiceInfo.OutputMethod = release.ShippingDetails.OutputMethod.ToString();
                    InvoiceInfo.OverwriteDueDate = prebillInvoiceDetails.OverwriteInvoiceDue;
                    InvoiceInfo.QueueSecretWord = prebillInvoiceDetails.QueueSecretWord;//prebillInvoiceDetails.Release.QueuePassword;
                    InvoiceInfo.RequestCoreId = release.RequestId;
                    InvoiceInfo.RequestStatus = prebillInvoiceDetails.RequestStatus;//request.Status.ToString ();
                    InvoiceInfo.RequestDate = DateTime.Now;
                    InvoiceInfo.InvoiceDueDate = DateTime.Now;//prebillInvoiceDetails.InvoiceDueDate;
                    InvoiceInfo.ResendDate = DateTime.Now;
                    InvoiceInfo.Type = "DOCX";
                    InvoiceInfo.LetterType = letterTemplateType;
                    InvoiceInfo.WillInvoiceShipped = true;

                    if (prebillInvoiceDetails.Release.RequestTransactions.Count > 0)
                    {                        
                        InvoiceInfo.RequestTransaction = new RequestTransaction();
                        InvoiceInfo.RequestTransaction.CreatedDate = prebillInvoiceDetails.Release.RequestTransactions[0].CreatedDate;
                        InvoiceInfo.RequestTransaction.ReasonName = prebillInvoiceDetails.Release.RequestTransactions[0].ReasonName;
                        InvoiceInfo.RequestTransaction.Description = prebillInvoiceDetails.Release.RequestTransactions[0].Description;
                        InvoiceInfo.RequestTransaction.TransactionType = prebillInvoiceDetails.Release.RequestTransactions[0].TransactionType;
                        InvoiceInfo.RequestTransaction.IsDebit = prebillInvoiceDetails.Release.RequestTransactions[0].IsDebit;
                        InvoiceInfo.RequestTransaction.Amount = prebillInvoiceDetails.Release.RequestTransactions[0].Amount;
                        InvoiceInfo.RequestTransaction.AdjustmentPaymentType = prebillInvoiceDetails.Release.RequestTransactions[0].AdjustmentPaymentType;                        
                    }
                    RequestorCache.RemoveKey(request.RequestorId);
                    if (letterTemplateType == LetterType.PreBill.ToString())
                    {   
                        invoiceAndDocumentDetails = RequestController.Instance.InvoiceOrPrebillPreview(InvoiceInfo);
                        //documentInfo = BillingController.Instance.CreatePreBill(prebillInvoiceDetails);
                        documentInfo = invoiceAndDocumentDetails.DocumentInfos.DocumentInfoCollection[0];
                        result = ShowViewer(letterTemplateType, documentInfo, false);
                    }
                    else
                    {
                        prebillInvoiceDetails.IsInvoice = true; // Do not add request transaction tag while invoicing.
                        //invoiceAndDocumentDetails = BillingController.Instance.CreateInvoiceDetails(prebillInvoiceDetails);
                        invoiceAndDocumentDetails = RequestController.Instance.InvoiceOrPrebillPreview(InvoiceInfo);
                        documentInfo = invoiceAndDocumentDetails.DocumentInfos.DocumentInfoCollection[0];
                        result = ShowViewer(letterTemplateType, documentInfo, false);
                    }

                    if (result == DialogResult.Cancel)
                    {
                        //if (letterTemplateType == LetterType.PreBill.ToString())
                        //{
                            BillingController.Instance.DeleteInvoiceOrPreBill(documentInfo.Id);
                        //}
                        //else
                        //{
                        //    BillingController.Instance.DeleteInvoice(documentInfo.Id);
                        //}
                    }
                    else
                    {
                        //If user made invoice from billing and payment screen successfully,
                        //the invoice button will be disabed
                        // CR#364029 Disable the billing location combobox once invoice is created
                        if (letterTemplateType == LetterType.Invoice.ToString())
                        {   
                            //billingPaymentActionUI.InvoiceButton.Enabled = false;
                            //billingPaymentActionUI.PreBillButton.Enabled = false;
                            billingLocationComboBox.Enabled = false;
                            UnbillablecheckBox.Enabled = false;
                        }
                        OutputRequestDetails outputRequestDetails = new OutputRequestDetails(request.Id,
                                                                       Convert.ToInt64(DateTime.Now.ToString("ddMMyyyyhhmmssms", System.Threading.Thread.CurrentThread.CurrentUICulture), System.Threading.Thread.CurrentThread.CurrentUICulture),
                                                                       request.RequestSecretWord, request.ReceiptDate);
                        outputRequestDetails.OutputDestinationDetails = outputPropertyDetails.OutputDestinationDetails[0];
                        outputRequestDetails.RequestParts.Add(BuildROIRequestPartDetails(documentInfo));
                      
                        prebillInvoiceDetails.Id = documentInfo.Id;
                        invoiceId = documentInfo.Id;
                        forInvoice = true;
                        prebillInvoiceDetails.QueueSecretWord = String.Empty;

                        string outputMethod = outputRequestDetails.OutputDestinationDetails.Type;
                        if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.File.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            prebillInvoiceDetails.QueueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                            queueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                            outputMethod = OutputMethod.SaveAsFile.ToString();
                            outputMethodName = OutputMethod.SaveAsFile.ToString();
                        }

                        if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            outputMethodName = OutputMethod.Fax.ToString();
                        }

                        if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Print.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            outputMethodName = OutputMethod.Print.ToString();
                        }

                        if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.Email.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            prebillInvoiceDetails.QueueSecretWord = outputRequestDetails.OutputDestinationDetails.SecuredSecretWord;
                            outputMethodName = OutputMethod.Email.ToString();
                        }

                        prebillInvoiceDetails.OutputMethod = outputMethod;

                        if (letterTemplateType == LetterType.PreBill.ToString())
                        {
                            Application.DoEvents();
                            BillingController.Instance.updateInvoiceOutputProperties(invoiceId, 0, 0, true, false, false, queueSecretWord, outputMethodName);                                                                                    
                        }
                        if (releaseDialog != null )
                        {
                            outputRequestDetails.RequestParts.Add(BuildROIReleasePartDetails(releaseDialog.ReleaseAndPreviewDetails));
                        }
                        Application.DoEvents();
                        long jobStatus = OutputController.Instance.SubmitOutputRequest(outputRequestDetails, destinationType,
                                                                       outputPropertyDetails.OutputViewDetails,
                                                                       false);                        

                        //Audit Invoice and Prebill
                        AuditEvent auditEvent = new AuditEvent();

                        auditEvent.UserId       = UserData.Instance.UserInstanceId;
                        auditEvent.EventStart   = System.DateTime.Now;
                        auditEvent.EventStatus  = (long)AuditEvent.EventStatusId.Success;
                        auditEvent.EventId      = 1;
                        auditEvent.Comment      = documentInfo.Name + "," + documentInfo.Id + " for request " + request.Id;
                        auditEvent.ActionCode   = RetrieveActionCode(outputMethod);

                        try
                        {
                            Application.DoEvents();
                            ROIController.Instance.CreateAuditEntry(auditEvent);
                        }
                        catch (ROIException cause)
                        {
                            log.FunctionFailure(cause);
                        }

                        //Events added for Invoice and Prebill
                        CommentDetails details = new CommentDetails();

                        details.RequestId = request.Id;

                        if (letterTemplateType == LetterType.PreBill.ToString())
                        {
                            details.EventType = EventType.PreBillSend;
                        }
                        else
                        {
                            details.EventType = EventType.InvoiceSend;
                        }

                        StringBuilder notes = new StringBuilder();

                            foreach (NotesDetails eventNotes in preBillInvoiceBaseUI.NotesList)
                            {
                                notes.Append(eventNotes.DisplayText + ",");
                            }

                            foreach (string eventNotes in preBillInvoiceBaseUI.FreeformNotesList)
                            {
                                notes.Append(eventNotes + ",");
                            }

                        string outputName;
                        if(outputMethod.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) ==  OutputMethod.Fax.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture))
                        {
                            outputName = outputRequestDetails.OutputDestinationDetails.Fax;
                        }
                        else
                        {
                            outputName = outputRequestDetails.OutputDestinationDetails.Name;
                        }
                        
                        if (!string.IsNullOrEmpty(notes.ToString()))
                        {
                            details.EventRemarks = documentInfo.Name.Substring(0,documentInfo.Name.LastIndexOf('.')) + ": " +
                                                   notes.ToString().TrimEnd(',') + ", output method: " +
                                                   outputMethod + ", " + outputName;
                        }
                        else
                        {
                            details.EventRemarks = documentInfo.Name.Substring(0, documentInfo.Name.LastIndexOf('.')) + ", output method: " +                                                    
                                                   outputMethod + ", " + outputName;
                        }                       

                            Application.DoEvents();
                            RequestController.Instance.CreateComment(details);
                            //US16834 - changes to Include requests in the pre-bill status on the payments popup.   
                            updateBalancePrebill();                            

                        //Audit and event entry if invoice due days is overridden
                        if (letterTemplateType == LetterType.Invoice.ToString() && preBillInvoiceBaseUI.IsOverWriteDueDays)
                        {
                            string comment = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.OverwriteInvoiceDueDaysMessage,
                                                               preBillInvoiceBaseUI.OldDueDays, preBillInvoiceBaseUI.DueDays);
                            auditEvent.Comment = comment;
                            auditEvent.ActionCode = ROIConstants.RequestModificationActionCode;

                            try
                            {
                                Application.DoEvents();
                                ROIController.Instance.CreateAuditEntry(auditEvent);
                            }
                            catch (ROIException cause)
                            {
                                log.FunctionFailure(cause);
                            }

                            details.EventType = EventType.OverwriteInvoiceDueDays;
                            details.EventRemarks = comment;
                            Application.DoEvents();
                            RequestController.Instance.CreateComment(details);
                        }

                        if (letterTemplateType == LetterType.Invoice.ToString())
                        {
                            long[] invoiceIdList = new long[] { documentInfo.Id };
                            CreateInvoiceAuditEvent(invoiceIdList, request.Id, false);
                        }

                        if (jobStatus == -200)
                        {
                            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                            string titleText = rm.GetString("letterUnsuccessfulDialog.title");
                            string okButtonText = rm.GetString("okButton.DialogUI");
                            string messageText = rm.GetString("letterUnsuccessfulDialog.MessageText");
                            string okButtonToolTip = "";
                            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                            return;
                        }

                        if (letterTemplateType == LetterType.PreBill.ToString())
                        {
                            request.Status = RequestStatus.PreBilled;
                            request.StatusReason = string.Empty; //Clear all the previos status reasons
                        }
                        else
                        {
                            request.OriginalBalance += prebillInvoiceDetails.Invoice.BaseCharge;
                            request.SalesTaxAmount += prebillInvoiceDetails.Invoice.SalesTax;
                            //CR#359276 - Reset the valuies to zero after invoice has created
                            request.InvoiceAutoAdjustment = request.InvoiceBaseCharge = 0;
                            // CR#365397
                            //if (!request.IsInvoiced)
                            //{
                            request.IsInvoiced = true;
                            //}
                        }
                    
                        SortedList<string, RequestPatientDetails> tempPatientList = new SortedList<string, RequestPatientDetails>();
                        for (int count = 0; count < request.Patients.Count; count++)
                        {
                            tempPatientList.Add(request.Patients.Keys[count], request.Patients.Values[count]);
                        }


                        ReleaseDetails olddraftRelease = new ReleaseDetails();
                        olddraftRelease = request.DraftRelease;
                        TaxPerFacilityDetails tempDefaultFacility = new TaxPerFacilityDetails();
                        tempDefaultFacility = request.DefaultFacility;
                        request = RequestController.Instance.UpdateRequest(request);
                        request.DefaultFacility = tempDefaultFacility;
                        request.DraftRelease = olddraftRelease;

                        for (int count = 0; count < tempPatientList.Count; count++)
                        {
                            request.Patients.Add(tempPatientList.Keys[count], tempPatientList.Values[count]);
                        }
                        request.HasSalesTax = oldSalesTaxStatus;
                        RequestEvents.OnRequestUpdated(Pane, new ApplicationEventArgs(request, this));

                        RequestRSP rsp = (RequestRSP)Pane.ParentPane.ParentPane;
                        rsp.BillingPaymentInfoEditor.Request = request;
                        rsp.InfoEditor.Request = request;
                        if (rsp.PatientInfoEditor != null)
                        {
                            rsp.PatientInfoEditor.Request = request;
                        }                        
                    }
                }
            }
            else
            {
                rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                string titleText = rm.GetString("noTemplate.title");
                string okButtonText = rm.GetString("okButton.DialogUI");
                string messageText = rm.GetString(letterTemplateType + ".MessageText");
                string okButtonToolTip = "";
                ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
            }
        }

        //make Audit and event entry if sales tax was applied for individual element while invoice creation
        //make Audit and event entry if existing invoices are picked while release or re-release
        public void CreateInvoiceAuditEvent(long[] invoiceIds, long requestId, bool isinvoiceResent)
        {
            ROIViewUtility.MarkBusy(true);
            AuditEvent auditEvent = new AuditEvent();
            CommentDetails details = new CommentDetails();

            string remarks = string.Empty;

            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvent.EventId = 1;
            details.RequestId = requestId;

            if (!isinvoiceResent)
            {                
                foreach (SalesTaxChargeDetails salesTaxDetail in salesTaxSummaryUI.SalesTaxCharges)
                {
                    if (salesTaxDetail.HasSalesTax)
                    {
                        remarks += string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SalesTaxApplyMsgForInvoice,
                                                 salesTaxDetail.TaxAmount.ToString("C",System.Threading.Thread.CurrentThread.CurrentUICulture), salesTaxDetail.ChargeName);
                        remarks += ",";
                    }
                }
                remarks = remarks.TrimEnd(new char[] { ',' });
                remarks += string.IsNullOrEmpty(remarks) ? string.Empty : ". ";
                remarks += string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.SalesTaxAmountMsgForInvoice,
                                         salesTaxSummaryUI.TotalTaxAmount.ToString("C",System.Threading.Thread.CurrentThread.CurrentUICulture), invoiceIds[0]);
                auditEvent.Comment = remarks;
                auditEvent.ActionCode = ROIConstants.RequestModificationActionCode;

                try
                {
                    Application.DoEvents();
                    ROIController.Instance.CreateAuditEntry(auditEvent);
                }
                catch (ROIException cause)
                {
                    log.FunctionFailure(cause);
                }
                
                details.EventType = EventType.InvoiceSend;
                details.EventRemarks = remarks;
                Application.DoEvents();
                RequestController.Instance.CreateComment(details);
            }
            else
            {                
                if (invoiceIds.Length > 0)
                {
                    string resendingInvoices = string.Empty;
                    foreach (long id in invoiceIds)
                    {
                        resendingInvoices += id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + ",";
                    }
                    resendingInvoices = resendingInvoices.TrimEnd(new char[] { ',' });
                    remarks = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.InvoiceResentMsg, resendingInvoices, DateTime.Now);

                    auditEvent.Comment = remarks;
                    auditEvent.ActionCode = ROIConstants.RequestModificationActionCode;

                    try
                    {
                        Application.DoEvents();
                        ROIController.Instance.CreateAuditEntry(auditEvent);
                    }
                    catch (ROIException cause)
                    {
                        log.FunctionFailure(cause);
                    }

                    details.EventType = EventType.InvoiceResent;
                    details.EventRemarks = remarks;
                    Application.DoEvents();
                    RequestController.Instance.CreateComment(details);
                }
            }
        }

        /// <summary>
        /// Shows the viewer.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        /// <param name="fileName"></param>
        /// <param name="releaseDialog"></param>
        /// <returns></returns>
        public DialogResult ShowViewer(string letterTemplateType, DocumentInfo documentInfo, bool releaseDialog)
        {
            ROIViewer viewer = new ROIViewer(Pane, letterTemplateType, GetType().Name);

            viewer.ReleaseDialog      = releaseDialog;
            viewer.DestinationType    = RetrieveDestinationType();
            viewer.RequestorFax       = request.RequestorFax;            

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + letterTemplateType + ".preview"), viewer);

            string filePath = string.Empty;
            try
            {
                filePath = BillingController.DownloadLetterTemplate(documentInfo.Name, ProgressHandler);

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
                        viewer.DocumentViewer.Visible = true;
                        viewer.PDFPageViewer.Visible = false;
                        viewer.DocumentViewer.Url = new Uri(filePath);
                    }                    
                }

                DialogResult result = dialog.ShowDialog(this);

                outputPropertyDetails = viewer.OutputPropertyDetails;

                if (!releaseDialog)
                {
                    destinationType = viewer.DestinationType;
                }

                dialog.Close();
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

        public static DestinationType RetrieveDestinationType(ShippingInfo shippingDetails)
        {
            DestinationType destinationType = DestinationType.Print;

            if (shippingDetails.OutputMethod == OutputMethod.Print)
            {
                destinationType = DestinationType.Print;
            }
            else if (shippingDetails.OutputMethod == OutputMethod.Fax)
            {
                destinationType = DestinationType.Fax;
            }
            else if (shippingDetails.OutputMethod == OutputMethod.SaveAsFile)
            {
                destinationType = DestinationType.File;
            }
            else if (shippingDetails.OutputMethod == OutputMethod.Email)
            {
                destinationType = DestinationType.Email;
            }
            else if (shippingDetails.OutputMethod == OutputMethod.Disc)
            {
                destinationType = DestinationType.Disc;
            }

            return destinationType;
        }

        private DestinationType RetrieveDestinationType()
        {
            return RetrieveDestinationType(release.ShippingDetails);
        }
        
        /// <summary>
        /// Create a prebillinvoice model.
        /// </summary>
        /// <param name="letterTemplateId"></param>
        /// <param name="notes"></param>
        /// <param name="freeformNotes"></param>
        /// <param name="letterType"></param>
        /// <param name="duedays"</param>
        /// <param name="isOverWriteDueDays"></param>
        /// <returns></returns>
        public PreBillInvoiceDetails CreatePreBillInvoiceDetails(long letterTemplateId, string letterTemplateName,
                                                                 Collection<NotesDetails> notes,
                                                                 Collection<string> freeformNotes,
                                                                 string letterType,
                                                                 int duedays,
                                                                 bool isOverWriteDueDays, bool isCalledFromRelease)
        {
            PreBillInvoiceDetails preBillInvoiceDetails = new PreBillInvoiceDetails();
            
            preBillInvoiceDetails.LetterTemplateId               = letterTemplateId;
            preBillInvoiceDetails.LetterTemplateName             = letterTemplateName;
            preBillInvoiceDetails.InvoiceType                    = letterType;
            preBillInvoiceDetails.TotalPagesPerRelease           = release.TotalPages;
            preBillInvoiceDetails.Release                        = release;
            preBillInvoiceDetails.Release.RequestId              = request.Id;
            preBillInvoiceDetails.BalanceDue                     = BalanceDue.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            preBillInvoiceDetails.Release.TotalRequestCost       = Convert.ToDouble(TotalRequestCost, System.Threading.Thread.CurrentThread.CurrentUICulture);
            preBillInvoiceDetails.Release.PreviouslyReleasedCost = Convert.ToDouble(PreviouslyReleasedCost, System.Threading.Thread.CurrentThread.CurrentUICulture);
            preBillInvoiceDetails.Release.ReleaseCost            = (release.IsReleased) ? 0 : PendingReleaseCost;
            preBillInvoiceDetails.Requestor                      = request.Requestor;
            preBillInvoiceDetails.DateCreated                    = request.DateCreated;
            preBillInvoiceDetails.RequestStatus                  = EnumUtilities.GetDescription(request.Status);
            preBillInvoiceDetails.StatusReasons                  = request.StatusReason;
            preBillInvoiceDetails.SalesTaxPercentage             = request.TaxPercentage.ToString("n2", System.Threading.Thread.CurrentThread.CurrentUICulture);
            preBillInvoiceDetails.Invoice = CreateInvoiceDetails(isCalledFromRelease);

            if (letterType == LetterType.Invoice.ToString())
            {   
                preBillInvoiceDetails.InvoiceDueDate = DateTime.Now.AddDays(duedays);
                preBillInvoiceDetails.OverwriteInvoiceDue = isOverWriteDueDays;                
            }

            foreach (NotesDetails note in notes)
            {
                preBillInvoiceDetails.Notes.Add(note.DisplayText);
            }
            foreach (string freeformNote in freeformNotes)
            {
                if (freeformNote.Trim().Length > 0)
                {                    
                    preBillInvoiceDetails.Notes.Add(freeformNote);
                }
            }            
            return preBillInvoiceDetails;
        }

        private InvoiceDet CreateInvoiceDetails(bool isCalledFromRelease)
        {
            double prebillBalanceDue = Math.Round(release.InvoicesBalanceDue, 2);
            double invoiceBalanceDue = Math.Round(BalanceDue - release.InvoicesBalanceDue, 2);
            InvoiceDet invoiceDetails = new InvoiceDet();            
            invoiceDetails.AmountPaid = 0;
            invoiceDetails.BalanceDue = invoiceBalanceDue;
            invoiceDetails.BaseCharge = release.TotalRequestCost;
            if (IsUnbillable())
            {
                invoiceDetails.BalanceDue = 0;
                invoiceDetails.BaseCharge = PendingReleaseCost;
            }
            invoiceDetails.BillingLocationCode = ((TaxPerFacilityDetails)billingLocationComboBox.SelectedItem).FacilityCode;
            invoiceDetails.BillingLocationName = ((TaxPerFacilityDetails)billingLocationComboBox.SelectedItem).FacilityName;
            invoiceDetails.SalesTax = salesTaxSummaryUI.TotalTaxAmount;
            return invoiceDetails;
        }

        /// <summary>
        /// Retrieve all letter templates.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        /// <returns></returns>
        private static IList<LetterTemplateDetails> RetrieveLetterTemplates(Collection<LetterTemplateDetails> letterTemplateList, string letterTemplateType)
        {
            List<LetterTemplateDetails> list = new List<LetterTemplateDetails>(letterTemplateList);
            List<LetterTemplateDetails> letterTemplates = list.FindAll(delegate(LetterTemplateDetails item) { return item.LetterType.ToString() == letterTemplateType; });

            return letterTemplates;
        }

        private static long RetrieveDefaultId(IList<LetterTemplateDetails> letterTemplates)
        {
            long defaultId = 0;

            List<LetterTemplateDetails> list = new List<LetterTemplateDetails>(letterTemplates);
            List<LetterTemplateDetails> defaultTemplates = list.FindAll(delegate(LetterTemplateDetails item) { return item.IsDefault == true; });

            if (defaultTemplates.Count > 0)
            {
                defaultId = defaultTemplates[0].DocumentId;
            }

            return defaultId;
        }

        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = billingPaymentActionUI.SaveButton;            
        }

        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {  
                case ROIErrorCodes.InvalidMainState: return shippingInfoUI.GetErrorControl(error);
                case ROIErrorCodes.InvalidMainCity: return shippingInfoUI.GetErrorControl(error);
                case ROIErrorCodes.InvalidMainZip: return shippingInfoUI.GetErrorControl(error);
                case ROIErrorCodes.BillingLocationNotSelected: return billingLocationComboBox;
            }
            return null;
        }

        /// <summary>
        /// Method to display confirm dialog to release the document
        /// </summary>
        /// <returns></returns>
        private bool ShowConfirmReleaseDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString("Self Pay Message");

            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString("Self Pay Title");
            string okButtonText = rm.GetString("Self Pay Ok");
            string cancelButtonText = rm.GetString("Self Pay Cancel");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString("Self Pay Ok Tool Tip");
            string cancelButtonToolTip = rm.GetString("Self Pay Cancel Tool Tip");

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip,
                                                 cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Check the encounter contains self pay.
        /// </summary>
        /// <returns></returns>
        private bool  CheckSelfPayEncounter()
        {
            bool selfPayEncounter = false;
            RequestDetails tempRequest = request;
            foreach (RequestPatientDetails patient in tempRequest.ReleasedItems.Values)
            {
                foreach (RequestEncounterDetails enc in patient.GetChildren)
                {
                    if (enc.IsSelfPay)
                    {
                        selfPayEncounter = true;
                        return selfPayEncounter;
                    }
                }
            }
            return selfPayEncounter;
        }

        private double RetrieveUnAppliedAmount(long requestID)
        {
            double totalUnAppliedAmount = 0;
            List<RequestorUnappliedAmountDetail> reqAmtList
                    = new List<RequestorUnappliedAmountDetail>();
            reqAmtList = RequestorController.Instance.RetrieveUnappliedAmountDetails(requestID);
            foreach (RequestorUnappliedAmountDetail req in reqAmtList)
            {
                if (req.Type == "Unapplied Payment")
                    totalUnAppliedAmount += req.Amount;
                else if (req.Type == "Unapplied Adjustment")
                    totalUnAppliedAmount += req.Amount;
            }
            return totalUnAppliedAmount;
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return billingPaymentActionUI;
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, true))
            {
                this.Enabled = false;
                billingPaymentActionUI.Enabled = false;
            }

            billingPaymentActionUI.AddAdjustmentButton.Enabled = IsAllowed(ROISecurityRights.ROIAdjustCharges);
            billingPaymentActionUI.AddPaymentButton.Enabled = IsAllowed(ROISecurityRights.ROIPostPayment);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets true if dirty data available.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set
            {
                isDirty = value;
            }
        }

        /// <summary>
        /// Pending release cost
        /// </summary>
        public double PendingReleaseCost
        {
            get 
            {
                //Once the user made a release he/she can allowed to do any adjustment charges, 
                //and the charges will be stored as new draft. In this case the Pending release cost shoul be 0
                //if (!documentChargeUI.IsEnabled && (request.Status != RequestStatus.Canceled &&
                 //                                 request.Status != RequestStatus.Denied))
                    if (!documentChargeUI.IsEnabled)
                {
                    return 0;
                }
                return Math.Round(documentChargeUI.DocumentCharge + feeChargeUI.FeeCharge +
                                  shippingInfoUI.ShippingCharge + salesTaxSummaryUI.TotalTaxAmount, 2);
            }
        }

        /// <summary>
        /// Total request cost - sum of pending release cost and previously released cost
        /// </summary>
        public double TotalRequestCost
        {
            get 
            {
                return PendingReleaseCost + PreviouslyReleasedCost;
            }
        }

        public double TotalTaxAmount
        {
            get
            {
                double taxTotal = 0;
				//CR#366859 -  Seperate Sales Tax cost from Pending Release Cost
				if (!documentChargeUI.IsEnabled && (request.Status != RequestStatus.Canceled &&
                                  request.Status != RequestStatus.Denied))
                {
                    newChargeTaxValueLabel.Text = ReleaseDetails.FormattedAmount(0.0) + "   )";
                    return 0;
                }
                if (salesTaxSummaryUI != null)
                {
                   taxTotal = salesTaxSummaryUI.TotalTaxAmount;
                }
                return taxTotal;
            }
        }

        /// <summary>
        /// Adjsutment total
        /// </summary>
        public double AdjustmentPaymentTotal
        {
            get { return release.CreditAdjustmentTotal + release.DebitAdjustmentTotal + release.PaymentTotal; }
        }

        private double unAppliedAdjustmentPaymentTotal;
        public double UnAppliedAdjustmentPaymentTotal
        {
            //get { return release.UnAppliedTotal; }
            get { return unAppliedAdjustmentPaymentTotal; }
            set { unAppliedAdjustmentPaymentTotal = value; }
        }

        /// <summary>
        /// Balance due- ((TotalRequestCost - PaymentTotal) +/- AdjustmentTotal)
        /// </summary>
        public double BalanceDue
        {
            get 
            {
                release.BalanceDue = TotalRequestCost - AdjustmentPaymentTotal - UnbillableAmount;
                return release.BalanceDue;
            }
        }

        public double UnbillableAmount
        {
            get
            {
                if (IsUnbillable())
                {
                    return TotalRequestCost;
                }
                return 0;
            }
        }

        /// <summary>
        /// Previously released Cost
        /// </summary>
        public double PreviouslyReleasedCost
        {
            get
            {
                double previouslyReleasedCost = 0;
                if (chargeHistories != null)
                {
                    foreach (ChargeHistoryDetails chargeHistory in chargeHistories)
                    {
                        previouslyReleasedCost += chargeHistory.TotalReleaseCost;
                    }
                }

                return Math.Round(previouslyReleasedCost, 2);
            }
        }

        /// <summary>
        /// Retrieves the printable attachment count
        /// </summary>
        public static int PrintableAttachmentPageCount
        {
            get
            {
                return printableAttachmentCount;
            }
        }

        /// <summary>
        /// Retrieves the non printable attachment count
        /// </summary>
        public static int NonPrintableAttachmentPageCount
        {
            get
            {
                return nonPrintableAttachmentCount;
            }
        }

       
        /// <summary>
        /// Retrieves the printable hpf document count
        /// </summary>
        public static int HpfDocumentCount
        {
            get
            {
                return hpfDocumentCount;
            }
        }

        public bool IsApplyTaxStateChanged
        {
            get { return isApplyTaxStateChanged; }
            set { isApplyTaxStateChanged = value; }
        }

        public string PreviousFacilityName
        {
            get { return previousFacilityName; }
            set { previousFacilityName = value; }
        }


        private bool isReleaseEnable;
        public bool IsReleaseEnable
        {
            get { return isReleaseEnable; }
            set { isReleaseEnable = value; }
        }

        private bool isReReleaseEnable;
        public bool IsReReleaseEnable
        {
            get { return isReReleaseEnable; }
            set { isReReleaseEnable = value; }
        }

        #endregion

        private void UnbillablecheckBox_CheckedChanged(object sender, EventArgs e)
        {
            UpdateBalanceDue();
            MarkDirty(this, e);
            EnableUnBillableLabel();
        }
    }
}