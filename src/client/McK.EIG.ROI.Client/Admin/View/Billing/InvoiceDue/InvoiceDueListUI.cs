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

//System Namespace
using System;
using System.Globalization;
using System.Resources;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Windows.Forms;

//McKesson Namespace
using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.InvoiceDue
{
    /// <summary>
    /// Class display the UI controls of Invoicedue
    /// </summary>
    public partial class InvoiceDueListUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(InvoiceDueListUI));

        #region Fields
               
        private EventHandler dirtyDataHandler;
        private EventHandler customTextHandler;
        private InvoiceDueDetails invoiceDueDetails;
        private ArrayList dueDaysList;

        #endregion

        #region Constructor
        
        /// <summary>
        /// Initialize the UI controls.
        /// </summary>
        public InvoiceDueListUI()
        {
            log.EnterFunction();

            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
            customTextHandler = new EventHandler(Process_CustomTextHandler);
            PopulateInvoiceDueDays();
        }

        #endregion

        #region Methods

        ///<summary>
        ///This mehod is used to populate the configurable days of duedate dropdown field
        ///</summary>
        public void PopulateInvoiceDueDays()
        {
            DisableEvents();
            dueDaysList = (ArrayList)ROIViewUtility.DeepClone(UserData.Instance.InvoiceDueDays);            
            dueDaysList.Add(new KeyValuePair<int, string>(ROIConstants.InvoiceDueCustomValue, ROIConstants.InvoiceDueCustomAttribute));
            dueDaysComboBox.DataSource = dueDaysList;            
            dueDaysComboBox.DisplayMember = "value";
            dueDaysComboBox.ValueMember = "key";            
            ClearControls();
            EnableEvents();
            dueDaysComboBox.Focus();            
        }


        /// <summary>
        ///  This method is used to enable(subscribe)the InvoiceDueMCPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            dueDaysComboBox.SelectedIndexChanged += dirtyDataHandler;
            customDateTextBox.TextChanged += customTextHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the InvoiceDueMCPUI local events
        /// </summary>
        public void DisableEvents()
        {
            dueDaysComboBox.SelectedIndexChanged -= dirtyDataHandler;
            customDateTextBox.TextChanged -= customTextHandler;
        }

        /// <summary>
        /// Occurs when the user changes the invoicedue dropdown.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            DisableEvents();
            saveButton.Enabled = !string.IsNullOrEmpty(dueDaysComboBox.SelectedValue.ToString());

            if (string.IsNullOrEmpty(customDateTextBox.Text.Trim()) && 
                int.Parse(dueDaysComboBox.SelectedValue.ToString(), System.Threading.Thread.CurrentThread.CurrentCulture) == ROIConstants.InvoiceDueCustomValue)
            {
                saveButton.Enabled = false;
            }

            customDateTextBox.Enabled = (int.Parse(dueDaysComboBox.SelectedValue.ToString(), System.Threading.Thread.CurrentThread.CurrentCulture) == ROIConstants.InvoiceDueCustomValue) ?
                                         true : false;
            daysLabel.Enabled = (int.Parse(dueDaysComboBox.SelectedValue.ToString(), System.Threading.Thread.CurrentThread.CurrentCulture) == ROIConstants.InvoiceDueCustomValue) ?
                                         true : false;
            customDateTextBox.Text = string.Empty;
            cancelButton.Enabled = true;
            errorProvider.Clear();
            (Pane as InvoiceDueMCP).OnDataDirty(sender, e);
            EnableEvents();
        }

        /// <summary>
        /// Occurs when the user changes the custom days text.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CustomTextHandler(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(customDateTextBox.Text.Trim()))
            {
                saveButton.Enabled = false;
            }
            else
            {
                saveButton.Enabled = true;
            }

            cancelButton.Enabled = true;
            (Pane as InvoiceDueMCP).OnDataDirty(sender, e);
        }

        /// <summary>
        /// Set the invoice due days.
        /// </summary>
        /// <param name="data">InvoiceDueDetails</param>
        public void SetData(object data)
        {            
            DisableEvents();
            ClearControls();
            invoiceDueDetails = data as InvoiceDueDetails;
            //if the saved duedays is not present in the userdata duedays get from global params 
            //then custom value in dropdown is enabled and the duedays value is set in custom text field
            invoiceDueDetails.IsCustomDue = !UserData.Instance.InvoiceDueDays.Contains
                                            (new KeyValuePair<int,string>(invoiceDueDetails.DueDateInDays,
                                            invoiceDueDetails.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentCulture)));

            if (invoiceDueDetails.IsCustomDue)
            {
                customDateTextBox.Enabled = true;
                daysLabel.Enabled = true;
                customDateTextBox.Text = invoiceDueDetails.DueDateInDays.ToString(System.Threading.Thread.CurrentThread.CurrentCulture);              
                dueDaysComboBox.SelectedValue = ROIConstants.InvoiceDueCustomValue;
            }
            else
            {
                dueDaysComboBox.SelectedValue = invoiceDueDetails.DueDateInDays;
                customDateTextBox.Enabled = false;
                daysLabel.Enabled = false;
            }

            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            EnableEvents();
            dueDaysComboBox.Focus();            
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = saveButton;
            }
        }

        /// <summary>
        /// Gets the InvoiceDueDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public InvoiceDueDetails GetData(object appendTo)
        {
            InvoiceDueDetails dueDatedays = appendTo as InvoiceDueDetails;
            int days = int.Parse(dueDaysComboBox.SelectedValue.ToString(), System.Threading.Thread.CurrentThread.CurrentCulture);

            if (dueDatedays == null)
            {
                dueDatedays = new InvoiceDueDetails();
            }

            try
            {
                if (days == ROIConstants.InvoiceDueCustomValue)
                {
                    dueDatedays.DueDateInDays = int.Parse(customDateTextBox.Text, System.Threading.Thread.CurrentThread.CurrentCulture);
                    dueDatedays.IsCustomDue = true;
                }
                else
                {
                    dueDatedays.DueDateInDays = days;
                    dueDatedays.IsCustomDue = false;
                }
            }
            catch (FormatException cause)
            {
                throw new ROIException(ROIErrorCodes.InvoiceDueFormat, cause);
            }

            return dueDatedays;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearControls()
        {            
            errorProvider.Clear();
            customDateTextBox.Text = string.Empty;
        }

        /// <summary>
        /// Apply culture and set the tooltip.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, dueDateLabel);
            SetLabel(rm, customDateLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);
            SetLabel(rm, daysLabel);
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Return the key for the control.
        /// </summary>
        /// <param name="control">Control</param>
        /// <param name="toolTip">ToolTip</param>
        /// <returns>Key for localization</returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Return the control for the error code specified
        /// </summary>
        /// <param name="errorCode">ErrorCode</param>
        /// <returns>Control</returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.InvoiceDueFormat: return customDateTextBox;
                default: return null;
            }
        }
        /// <summary>
        /// save the due days attribute for invoice due generation
        /// </summary>
        /// <returns></returns>
        public bool Save()
        {
            log.EnterFunction();
            errorProvider.Clear();
            ROIViewUtility.MarkBusy(true);

            try
            {
                InvoiceDueDetails dueDetails = ROIViewUtility.DeepClone(invoiceDueDetails) as InvoiceDueDetails;
                dueDetails = GetData(dueDetails);
                dueDetails = ROIAdminController.Instance.UpdateInvoiceDueDays(dueDetails);
                (Pane as InvoiceDueMCP).IsDirty = false;
                invoiceDueDetails = dueDetails;
                saveButton.Enabled = false;
                cancelButton.Enabled = false;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
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
            log.ExitFunction();
            return true;
        }

        /// <summary>
        /// Save Button Click in InvoiceDueMCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnSave_Click(object sender, EventArgs e)
        {
            Save();
        }

        /// <summary>
        /// Cancel button click in InvoiceDueMCP
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnCancel_Click(object sender, EventArgs e)
        {
            CancelInvoiceDue();
        }
        
        /// <summary>
        /// this method is used to cancel the changes made and reset the saved data in UI
        /// </summary>
        public void CancelInvoiceDue()
        {
            (Pane as InvoiceDueMCP).IsDirty = false;
            errorProvider.Clear();
            cancelButton.Enabled = false;
            saveButton.Enabled = false;
            SetData(invoiceDueDetails);
        }
        
        /// <summary>
        /// Event occurs when the custom due days TextBox text is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void customDateTextField_KeyPress(object sender, KeyPressEventArgs e)
        {
            int KeyCode = (int)e.KeyChar;
            if (!(KeyCode >= 48 && KeyCode <= 57) && KeyCode != 8)
            {
                e.Handled = true;
            }
        }

        #endregion
    }
}
