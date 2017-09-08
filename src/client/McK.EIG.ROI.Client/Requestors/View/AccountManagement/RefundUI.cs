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
using System.ComponentModel;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;
using System.Globalization;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;
using McK.EIG.ROI.Client.Web_References.ROIRequestorWS;
using System.Text.RegularExpressions;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    /// <summary>
    /// Maintains requestor profile
    /// </summary>
    public partial class RefundUI : ROIBaseUI
    {
        #region Fields

        private RequestorDetails requestorDetails;
        private RequestorRefundDetail outputRequestorRefundDetail;
		//CR# 380979
        private double amountToRefund;
        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the controls
        /// </summary>
        public RefundUI()
        {
            InitializeComponent();
            
        }

        /// <summary>
        /// Initialize the action handler
        /// </summary>
        /// <param name="selectRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public RefundUI(EventHandler selectRequestorHandler, EventHandler cancelHandler, IPane pane, RequestorDetails requestorDetails,
                        double amountToRefund)
            : this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
            this.requestorDetails = requestorDetails;
            this.amountToRefund = amountToRefund;
        }


        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, requiredLabel);
            SetLabel(rm, amountLabel);
            SetLabel(rm, noteLabel);
            SetLabel(rm, dateLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
        }

        /// <summary>
        /// Gets the localized key for UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets the localized key for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        public void SetData(object data)
        {
            RequestInvoiceDetail requestInvoiceDetail = (RequestInvoiceDetail)data;
            //CR#379006
            amountTextBox.TextChanged -= new EventHandler(amountTextBox_TextChanged);
            amountTextBox.Text = requestInvoiceDetail.PayAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            amountTextBox.TextChanged += new EventHandler(amountTextBox_TextChanged);
            noteTextBox.Text = requestInvoiceDetail.Description;
            //CR#377295
            refundDateTimePicker.Value = Convert.ToDateTime(requestInvoiceDetail.ModifiedDate, System.Threading.Thread.CurrentThread.CurrentUICulture);
            amountTextBox.Enabled = false;
            noteTextBox.Enabled = false;
            refundDateTimePicker.Enabled = false;
            saveButton.Enabled = false;
            saveAndOutputLetterButton.Enabled = false;
            //CR# 377,296 - refund button is disabled while viewing Refund line item
            cancelButton.Enabled = true;
        }

        //CR#377173
        private bool ValidatePaymentAmount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            double balanceAmount = 0.0;
            string amount = amountTextBox.Text.Trim();
            if (amount.Contains("$"))
            {
                amountTextBox.Text = Convert.ToString(amount.Substring(1, amountTextBox.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            if (!double.TryParse(amountTextBox.Text.Trim(), out balanceAmount) || balanceAmount < 0 ||
                     !Regex.IsMatch(string.Format("{0:0.00}", balanceAmount), RequestTransaction.ValidAmountFormat))
            {
                errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidRefundAmoutFormat));
                return false;
            }
            errorProvider.Clear();
            return true;
        }

        
        public void CleanUp()
        {

        }

        private void EnableEvents()
        {

        }

        private void DisableEvents()
        {

        }

        private void MarkDirty(object sender, EventArgs e)
        {

        }

        private static void HideFooterButtons(RequestorInfoUI infoUI)
        {
            infoUI.HideDeleteRequestorButton = true;
            infoUI.HideCreateRequestButton = true;
        }

        /// <summary>
        /// Occurs when user clicked on Save button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            errorProvider.Clear();
            SaveRefundDetail(true);
        }

        private bool SaveRefundDetail(bool isSaveButtonClicked)
        {
            try
            {
                // validates the refund dialog
                if (!validateRefundDialog())
                {
                    return false;
                }
                double refundAmount = ParseDouble(amountTextBox.Text);
                if (this.amountToRefund < refundAmount)
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("RefundAmount.Title");
                    string okButtonText = rm.GetString("RefundAmount.OkButton");
                    string messageText = rm.GetString("RefundAmount.Message");

                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                    return false;
                }
                RequestorRefundDetail requestorRefundDetail = new RequestorRefundDetail();
                requestorRefundDetail.RequestorID = requestorDetails.Id;
                requestorRefundDetail.RequestorName = requestorDetails.Name;
                requestorRefundDetail.RequestorType = requestorDetails.TypeName;
                requestorRefundDetail.RefundAmount = refundAmount;
                requestorRefundDetail.Note = noteTextBox.Text.Trim();
                requestorRefundDetail.RefundDate = Convert.ToDateTime(refundDateTimePicker.Value.ToString());
                outputRequestorRefundDetail = requestorRefundDetail;
                RequestorCache.RemoveKey(requestorDetails.Id);
                ((Form)(this.Parent)).DialogResult = DialogResult.OK;
                if (isSaveButtonClicked)
                {
                    RequestorController.Instance.CreateRequestorRefund(requestorRefundDetail, false);
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            return true;
        }

        /// <summary>
        ///  validates the refund dialog
        /// </summary>
        /// <returns></returns>
        private bool validateRefundDialog()
        {
            if (String.IsNullOrEmpty(amountTextBox.Text)) 
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidRefundAmount));
                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                return false;                
            }

            if (String.IsNullOrEmpty(noteTextBox.Text))
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(noteTextBox, rm.GetString(ROIErrorCodes.InvalidRefundNote));
                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                return false;
            }

            if (refundDateTimePicker.Value > DateTime.Now)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(refundDateTimePicker, rm.GetString(ROIErrorCodes.InvalidRefundDate));
                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                return false;
            }

            return true;
        }

        /// <summary>
        /// Occurs when save and output letter button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveAndOutputLetterButton_Click(object sender, EventArgs e)
        {
            errorProvider.Clear();
            try
            {
                if (SaveRefundDetail(false))
                {
                    OutputRefundDialog outputRefundDialog = new OutputRefundDialog(Pane);
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                    Form dialog = ROIViewUtility.ConvertToForm(null,
                                                               rm.GetString("title." + outputRefundDialog.Name),
                                                               outputRefundDialog);
                    Collection<LetterTemplateDetails> letterTemplateList = ROIAdminController.Instance.RetrieveAllLetterTemplates();

                    IList<LetterTemplateDetails> statementTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, LetterType.RequestorStatement.ToString());
                    IList<LetterTemplateDetails> refundTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, LetterType.Other.ToString());

                    long defaultstatementId = ROIViewUtility.RetrieveDefaultId(statementTemplates);
                    long defaultrefundId = ROIViewUtility.RetrieveDefaultId(refundTemplates);
                    outputRefundDialog.PrePopulate(statementTemplates, refundTemplates, defaultstatementId, defaultrefundId,
                                                   outputRequestorRefundDetail);
                    dialog.ShowDialog();
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        ///  Occurs when a key is pressed while the amount textbox has focus.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void amountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// Occurs when note textbox text is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void noteTextBox_TextChanged(object sender, EventArgs e)
        {
          //CR#377163
            double refundAmount=0;
            if (amountTextBox.Text != "")
                refundAmount = ParseDouble(amountTextBox.Text.ToString());
            else
                refundAmount = 0;
            saveButton.Enabled = refundAmount > 0 && noteTextBox.Text.Trim().Length > 0;
            saveAndOutputLetterButton.Enabled = refundAmount > 0 && noteTextBox.Text.Trim().Length > 0;
        }

        /// <summary>
        /// Occurs when amount textbox text is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void amountTextBox_TextChanged(object sender, EventArgs e)
        { 
            //CR#377173
            if (!ValidatePaymentAmount())
            {
                saveButton.Enabled = false;
                saveAndOutputLetterButton.Enabled = false;
                return; 
            }
            double refundAmount = Convert.ToDouble(amountTextBox.Text.Trim().Length > 0);
            saveButton.Enabled = refundAmount > 0 && noteTextBox.Text.Trim().Length > 0;
            saveAndOutputLetterButton.Enabled = refundAmount > 0 && noteTextBox.Text.Trim().Length > 0;
        }

        private void amountTextBox_Leave(object sender, EventArgs e)
        {
            string amount = amountTextBox.Text.Trim();
            if (amount == "") return;
            if (!ValidatePaymentAmount())
            {
                saveButton.Enabled = false;
                saveAndOutputLetterButton.Enabled = false;
                return;
            }
            if (!string.IsNullOrEmpty(amount))
            {
                //CR#380507
                double refundAmount = 0;
                if ((amount.Length != 0) && (amount.Contains("$")))
                    refundAmount = Convert.ToDouble(amount.Trim().Substring(1, amount.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                else
                    refundAmount = Convert.ToDouble(amount);
                amountTextBox.TextChanged -= new EventHandler(amountTextBox_TextChanged);
                amountTextBox.Text = refundAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                amountTextBox.TextChanged += new EventHandler(amountTextBox_TextChanged);
            }
        }

        private double ParseDouble(string strAmount)
        {
            double amount = 0;
            try
            {
                if ((strAmount.Length != 0) && (strAmount.Contains("$")))
                {
                    amount = Convert.ToDouble(strAmount.Trim().Substring(1, strAmount.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                else
                {
                    amount = double.Parse(strAmount);
                    amount = Math.Round(amount, 2);
                }
            }
            catch (Exception)
            {
                amount = 0.0;
            }
            return amount;
        }
     
        #endregion
    }
}
