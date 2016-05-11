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
using System.Configuration;
using System.Globalization;
using System.Resources;
using System.Text.RegularExpressions;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.RequestInfo;

namespace McK.EIG.ROI.Client.Request.View.FindRequest
{

    public enum BalanceDueOperator
    {
        [Description("At Least")]
        AtLeast = 0,
        [Description("At Most")]
        AtMost = 1,
        [Description("Equals")]
        Equal = 2
    }
    
    /// <summary>
    /// Display the Find Request UI.
    /// </summary>    
    public partial class RequestSearchUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(RequestSearchUI));

        #region Fields

        //private const string CustomDateFormat = "MMMM dd, yyyy";

        private EventHandler keyPressHandler;
        private EventHandler activeHandler;
        private EventHandler inActiveHandler;
        private KeyPressEventHandler numericKeyPressHandler;        
       
        private FindRequestCriteria searchCriteria;

        private FindRequestResult searchResult;
        private bool isLoaded;
        private bool RegID = false;
        private static bool isError;

        #endregion

        #region Constructor

        public RequestSearchUI()
        {
            InitializeComponent();
            SetSsnFormat();
            Init();            
        }

        #endregion

        #region Methods

        /// <summary>
        /// Search Initialization
        /// </summary>
        private void Init()
        {
            moreInfoImage1.Image = ROIImages.MoreInfoIcon;
            moreInfoImage2.Image = ROIImages.MoreInfoIcon;
            
            
            epnPanel.Visible = UserData.Instance.EpnEnabled;
            
            epnPrefixLabel.Text = (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() : string.Empty;
            toolTip.SetToolTip(epnPrefixLabel, (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() : string.Empty);
            
            keyPressHandler        = new EventHandler(Process_KeyPressedHandler);
            numericKeyPressHandler = new KeyPressEventHandler(Process_NumericKeyPress);
            activeHandler          = new EventHandler(OnActive);
            inActiveHandler        = new EventHandler(OnInActive);
            
            EnableEvents();
        }

        /// <summary>
        /// Populate the Facility, Request Status, RequestorType and set null value to the DOB field
        /// </summary>
        public void PrePopulate(Collection<RequestorTypeDetails> requestorTypes)
        {
            DisableEvents();

            PopulateBalanceDueOperators();
            PopulateRequestStatus();
            PopulateRequestReasons();
            PopulateFacility();            
            PopulateRequestorType(requestorTypes);
            //SetAcceptButton();
            SetDOBDateNullValue();

            EnableEvents();
        }
        
        /// <summary>
        /// Sets the button on the form that is clicked when the presses the Enter key
        /// </summary>
        public override void SetAcceptButton()
        {
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = findRequestButton;
            }
            RefreshData();
        }

        private void RefreshData()
        {   
            if (isLoaded)
            {
                ClearSearchCriteria();
                //((FindRequestEditor)(this.Pane.ParentPane)).PrePopulate();                
            }
            isLoaded = true;
        }

        /// <summary>
        /// Populates all requestor types
        /// </summary>
        /// <param name="requestorTypes"></param>
        private void PopulateRequestorType(Collection<RequestorTypeDetails> requestorTypes)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string allRequestorTypes = rm.GetString("requestorTypeText." + this.GetType().Name);

            RequestorTypeDetails forSelect = new RequestorTypeDetails();
            forSelect.Id = 0;
            forSelect.Name = allRequestorTypes;
            requestorTypes.Insert(0, forSelect);

            requestorTypeComboBox.DisplayMember = "Name";
            requestorTypeComboBox.ValueMember   = "Id";
            requestorTypeComboBox.DataSource = requestorTypes;
        }

        /// <summary>
        /// Populate balance due operator list.
        /// </summary>
        private void PopulateBalanceDueOperators()
        {
            IList balanceDueOperators = EnumUtilities.ToList(typeof(BalanceDueOperator));

            balanceDueComboBox.DisplayMember = "value";
            balanceDueComboBox.ValueMember = "key";
            balanceDueComboBox.DataSource = balanceDueOperators;
            balanceDueComboBox.SelectedIndex = 0;
        }

        /// <summary>
        /// Populate all request reasons.
        /// </summary>
        private void PopulateRequestReasons()
        {
            ROIViewUtility.MarkBusy(true);

            Collection<ReasonDetails> requestReasons = ROIAdminController.Instance.RetrieveAllReasons(ReasonType.Request);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            ReasonDetails reasonDetails = new ReasonDetails();
            reasonDetails.Id = 0;
            reasonDetails.Name = rm.GetString(ROIConstants.PleaseSelect);
            requestReasons.Insert(0, reasonDetails);

            requestReasonComboBox.DisplayMember = "Name";
            requestReasonComboBox.ValueMember   = "Name";
            requestReasonComboBox.DataSource    = requestReasons;
            requestReasonComboBox.SelectedIndex = 0;

            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Populates all request statuses
        /// </summary>
        private void PopulateRequestStatus()
        {
            IList requestStatus = EnumUtilities.ToList(typeof(RequestStatus));
            requestStatus.RemoveAt(0);
            requestStatus.Insert(0, new KeyValuePair<Enum, string>(RequestStatus.None, "All Statuses"));

            requestStatus.Insert(2, new KeyValuePair<Enum, string>(RequestStatus.AuthReceivedDenied, "Auth Received Denied"));
            requestStatus.RemoveAt(9);            

            requestStatus.RemoveAt(requestStatus.IndexOf(new KeyValuePair<Enum, string>(RequestStatus.Unknown,
                                                                                          EnumUtilities.GetDescription(RequestStatus.Unknown))));

            requestStatusComboBox.DisplayMember = "value";            
            requestStatusComboBox.ValueMember = "key";
            requestStatusComboBox.DataSource = requestStatus;
            requestStatusComboBox.SelectedIndex = 0;
        }

        /// <summary>
        /// Populates all facilities
        /// </summary>
        private void PopulateFacility()
        {
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                facilityComboBox.Items.Add(fac);
            }

            facilityComboBox.Items.Insert(0, new FacilityDetails(ROIConstants.AllFacilities, ROIConstants.AllFacilities, FacilityType.Hpf));
            facilityComboBox.DisplayMember = "Name";
            facilityComboBox.ValueMember = "Code";
            facilityComboBox.SelectedIndex = 0;
        }

        /// <summary>
        /// Sets the value and null value for DOB.
        /// </summary>
        private void SetDOBDateNullValue()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            dobTextBox.Text = rm.GetString(ROIConstants.DefaultDate);
        }

        /// <summary>
        /// Gets localize key of UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control == moreInfoImage1 || control == moreInfoImage2)
            {
                return "moreInfoImage";
            }
            else if (control == dobTextBox)
            {
                return control.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control, toolTip);
        }
        
        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            //UI-Text
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());            
            SetLabel(rm, patientLastNameLabel);
            SetLabel(rm, patientFirstNameLabel);
            SetLabel(rm, patientDobLabel);
            SetLabel(rm, patientSsnLabel);
            SetLabel(rm, mrnLabel);
            SetLabel(rm, encounterLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, requestStatusLabel);
            SetLabel(rm, requestorNameLabel);
            SetLabel(rm, requestorTypeLabel);
            SetLabel(rm, requestReasonLabel);
            SetLabel(rm, balanceDueLabel);
            SetLabel(rm, dollarLabel);
            SetLabel(rm, invoiceNumberLabel);
            SetLabel(rm, requestIdLabel);
            SetLabel(rm, findRequestButton);
            SetLabel(rm, newRequestButton);
            SetLabel(rm, resetRequestButton);
            SetLabel(rm, receiptDateLabel);
            SetLabel(rm, completeDateLabel);
            //presetDateRange.dateLabel.Visible = false;
            customDateRange.dateLabel.Visible = false;
            completeDateRange.dateLabel.Visible = false;
            

            //ToolTip
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, patientLastNameTextBox);
            SetTooltip(rm, toolTip, patientFirstNameTextBox);
            SetTooltip(rm, toolTip, dobTextBox);
            SetTooltip(rm, toolTip, moreInfoImage1);
            SetTooltip(rm, toolTip, patientSsnTextBox);
            SetTooltip(rm, toolTip, patientEncounterTextBox);
            SetTooltip(rm, toolTip, patientMrnTextBox);
            SetTooltip(rm, toolTip, patientEpnTextBox);

            SetTooltip(rm, toolTip, facilityComboBox);
            SetTooltip(rm, toolTip, moreInfoImage2);
            SetTooltip(rm, toolTip, requestStatusComboBox);
            SetTooltip(rm, toolTip, requestorNameTextBox);
            SetTooltip(rm, toolTip, invoiceNumberTextBox);
            SetTooltip(rm, toolTip, requestIdTextBox);
            SetTooltip(rm, toolTip, requestorTypeComboBox);
            SetTooltip(rm, toolTip, findRequestButton);
            SetTooltip(rm, toolTip, newRequestButton);
            SetTooltip(rm, toolTip, resetRequestButton);
            SetTooltip(rm, toolTip, requestReasonComboBox);

            customDateRange.Localize();
            completeDateRange.Localize();
        }

        
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
           // presetDateRange.SetExecutionContext(Context);
           // presetDateRange.SetPane(Pane);
            completeDateRange.SetExecutionContext(Context);
            completeDateRange.SetPane(Pane);
            customDateRange.SetExecutionContext(Context);
            customDateRange.SetPane(pane);
        }

        /// <summary>
        /// Enable the Events
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            patientLastNameTextBox.TextChanged  += keyPressHandler;
            patientFirstNameTextBox.TextChanged += keyPressHandler;
            dobTextBox.TextChanged              += keyPressHandler;
            patientSsnTextBox.TextChanged       += keyPressHandler;
            patientMrnTextBox.TextChanged       += keyPressHandler;
            patientEncounterTextBox.TextChanged += keyPressHandler;
            patientEpnTextBox.TextChanged       += keyPressHandler;
            requestorNameTextBox.TextChanged    += keyPressHandler;

            invoiceNumberTextBox.KeyPress       += numericKeyPressHandler;
            requestIdTextBox.KeyPress           += numericKeyPressHandler;

            balanceDueTextBox.TextChanged       += keyPressHandler;
            invoiceNumberTextBox.TextChanged    += keyPressHandler;
            requestIdTextBox.TextChanged        += keyPressHandler;

            facilityComboBox.SelectedIndexChanged          += keyPressHandler;
            requestorTypeComboBox.SelectedIndexChanged     += keyPressHandler;
            requestStatusComboBox.SelectedIndexChanged     += keyPressHandler;
            requestReasonComboBox.SelectedIndexChanged     += keyPressHandler;
//            presetDateRange.fromNullableDTP.ValueChanged   += keyPressHandler;
            //presetDateRange.toNullableDTP.ValueChanged     += keyPressHandler;
            //completeDateRange.fromNullableDTP.ValueChanged  += keyPressHandler;
            //completeDateRange.toNullableDTP.ValueChanged    += keyPressHandler;

            customDateRange.fromCustomDate.TextChanged   += keyPressHandler;
            customDateRange.toCustomDate.TextChanged     += keyPressHandler;
            completeDateRange.fromCustomDate.TextChanged += keyPressHandler;
            completeDateRange.toCustomDate.TextChanged   += keyPressHandler;


            patientSsnTextBox.Enter += activeHandler;
            patientSsnTextBox.Leave += inActiveHandler;

            balanceDueTextBox.Leave += new EventHandler(balanceDueTextBox_Leave);
        }

        /// <summary>
        /// Occurs when the control is no longer active in the form
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void balanceDueTextBox_Leave(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(balanceDueTextBox.Text))
            {
                double amount;
                errorProvider.Clear();
                if (!double.TryParse(balanceDueTextBox.Text, out amount) ||
                   !Regex.IsMatch(Convert.ToString(amount, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.AmountFormat))
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                    errorProvider.SetError(balanceDueTextBox, rm.GetString(ROIErrorCodes.InvalidBalanceDue));
                    return;
                }
            }
        }       

        ///// <summary>
        ///// Occurs when user clicks on request status combobox
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void requestStatusComboBox_SelectedIndexChanged(object sender, EventArgs e)
        //{
        //    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

        //    if (requestStatusComboBox.SelectedIndex > 0)
        //    {
        //        RequestStatus status = (RequestStatus)Enum.Parse(typeof(RequestStatus), requestStatusComboBox.SelectedValue.ToString());
        //        List<ReasonDetails> reasonList = new List<ReasonDetails>(reasons);

        //        reasonList = reasonList.FindAll(delegate(ReasonDetails item) { return item.RequestStatus == status; });

        //        ReasonDetails reasonDetails = new ReasonDetails();
        //        reasonDetails.Id = 0;
        //        reasonDetails.Name = rm.GetString(ROIConstants.PleaseSelect);
        //        reasonList.Insert(0, reasonDetails);

        //        requestReasonComboBox.DisplayMember = "Name";
        //        requestReasonComboBox.ValueMember = "Name";
        //        requestReasonComboBox.DataSource = reasonList;
        //        requestReasonComboBox.SelectedIndex = 0;
        //    }
        //    else
        //    {
        //        PopulateAllRequestStatusReasons();
        //    }
        //}

        /// <summary>
        /// Occurs when user clicked on facility combobox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void facilityComboBox_Click(object sender, EventArgs e)
        {
            if (facilityComboBox.Items.Count - 1 != UserData.Instance.Facilities.Count)// + UserData.Instance.FreeformFacilities.Count)
            {
                facilityComboBox.Items.Clear();
                PopulateFacility();
            }
        }      

        /// <summary>
        /// Disable the Events
        /// </summary>
        private void DisableEvents()
        {
            patientLastNameTextBox.TextChanged  -= keyPressHandler;
            patientFirstNameTextBox.TextChanged -= keyPressHandler;
            dobTextBox.TextChanged              -= keyPressHandler;
            patientSsnTextBox.TextChanged       -= keyPressHandler;
            patientMrnTextBox.TextChanged       -= keyPressHandler;
            patientEncounterTextBox.TextChanged -= keyPressHandler;
            patientEpnTextBox.TextChanged       -= keyPressHandler;
            requestorNameTextBox.TextChanged    -= keyPressHandler;

            invoiceNumberTextBox.KeyPress       -= numericKeyPressHandler;
            requestIdTextBox.KeyPress           -= numericKeyPressHandler;

            invoiceNumberTextBox.TextChanged    -= keyPressHandler;
            requestIdTextBox.TextChanged        -= keyPressHandler;

            facilityComboBox.SelectedIndexChanged          -= keyPressHandler;
            requestorTypeComboBox.SelectedIndexChanged     -= keyPressHandler;
            requestStatusComboBox.SelectedIndexChanged     -= keyPressHandler;
            requestReasonComboBox.SelectedIndexChanged     -= keyPressHandler;
            //presetDateRange.fromNullableDTP.ValueChanged   -= keyPressHandler;
            //presetDateRange.toNullableDTP.ValueChanged     -= keyPressHandler;
            //completeDateRange.fromNullableDTP.ValueChanged -= keyPressHandler;
            //completeDateRange.toNullableDTP.ValueChanged   -= keyPressHandler;
            customDateRange.fromCustomDate.TextChanged     -= keyPressHandler;
            customDateRange.toCustomDate.TextChanged       -= keyPressHandler;
            completeDateRange.fromCustomDate.TextChanged -= keyPressHandler;
            completeDateRange.toCustomDate.TextChanged -= keyPressHandler;

            patientSsnTextBox.Enter -= activeHandler;
            patientSsnTextBox.Leave -= inActiveHandler;
            balanceDueTextBox.Leave -= new EventHandler(balanceDueTextBox_Leave);

        }

        private void Process_NumericKeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back)
            {
                e.Handled = true;
            }
        }  

        /// <summary>
        /// Event invoked when ever there is any change in the value 
        /// of Search criteria. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void Process_KeyPressedHandler(object sender, EventArgs e)
        {
            //if (sender.Equals(presetDateRange.fromNullableDTP))
            //{
            //    presetDateRange.fromNullableDTP_ValueChanged(sender, e);
            //}
            //if (sender.Equals(presetDateRange.toNullableDTP))
            //{
            //    presetDateRange.toNullableDTP_ValueChanged(sender, e);
            //}

            if (sender.Equals(completeDateRange.fromCustomDate))
            {
                completeDateRange.fromCustomDate_ValueChanged(sender, e);
            }
            if (sender.Equals(completeDateRange.toCustomDate))
            {
                completeDateRange.toCustomDate_ValueChanged(sender, e);
            }

            if (sender.Equals(customDateRange.fromCustomDate))
            {
                customDateRange.fromCustomDate_ValueChanged(sender, e);
            }
            if (sender.Equals(customDateRange.toCustomDate))
            {
                customDateRange.toCustomDate_ValueChanged(sender, e);
            }

            bool isValidFields = RequestValidator.ValidatePrimarySearchFields(GetData());

            //CR# - 377569
            if (!RegID)
            {
                findRequestButton.Enabled = isValidFields;
            }
            if (customDateRange.DateRangeOption != "All" && !RegID)
            {
                findRequestButton.Enabled = customDateRange.IsValidDateRange && findRequestButton.Enabled;
            }
            if (completeDateRange.DateRangeOption != "All" && !RegID)
            {
                findRequestButton.Enabled = completeDateRange.IsValidDateRange && findRequestButton.Enabled;
            }
            if (customDateRange.DateRangeOption == "Custom Range" && completeDateRange.DateRangeOption == "Custom Range" && !RegID)
            { 
                findRequestButton.Enabled = (customDateRange.IsValidDateRange && completeDateRange.IsValidDateRange) && findRequestButton.Enabled;
            }
        }

        /// <summary>
        /// Occurs when control is focus into the balancedue textbox and user presses and releases a key.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void balanceDueTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// Occurs when the control becomes active control of the view
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnActive(object sender, EventArgs e)
        {
            ROIViewUtility.RemoveMask(patientSsnTextBox);
        }

        /// <summary>
        /// Occurs when the control is no longer the active control of the view 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnInActive(object sender, EventArgs e)
        {
            if (UserData.Instance.IsSSNMasked)
            {
                ROIViewUtility.ApplyMask(patientSsnTextBox);
            }
        }

        /// <summary>
        /// Gets the Find request search criteria object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        private FindRequestCriteria GetData()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            
            searchCriteria = new FindRequestCriteria();
            searchCriteria.PatientLastName = patientLastNameTextBox.Text.Trim();
            searchCriteria.PatientFirstName = patientFirstNameTextBox.Text.Trim();
            searchCriteria.PatientSsn = (patientSsnTextBox.MaskedTextProvider.AssignedEditPositionCount > 0) ? patientSsnTextBox.Text.Trim() : string.Empty;
            searchCriteria.Encounter = patientEncounterTextBox.Text.Trim();
            searchCriteria.MRN = patientMrnTextBox.Text.Trim();
            if(patientEpnTextBox.Text.Trim().Length > 0 && UserData.Instance.EpnPrefix != null)
            {
                searchCriteria.EPN  =  UserData.Instance.EpnPrefix.Trim() + patientEpnTextBox.Text.Trim();
            }
            else
            {
                searchCriteria.EPN  =  patientEpnTextBox.Text.Trim();
            }

            if (dobTextBox.Text.Trim() != rm.GetString(ROIConstants.DefaultDate).Trim() && dobTextBox.IsValidDate)
            {
                searchCriteria.Dob = Convert.ToDateTime(dobTextBox.FormattedDate, CultureInfo.InvariantCulture);
                
            }
            else
            {
                searchCriteria.Dob = null;
            }
            searchCriteria.Facility           = (facilityComboBox.SelectedIndex == 0) ? null : ((FacilityDetails)(facilityComboBox.SelectedItem)).Code;

            if (requestStatusComboBox.SelectedValue.ToString().Equals(RequestStatus.AuthReceived.ToString()))
            {
                searchCriteria.RequestStatus = EnumUtilities.GetDescription(RequestStatus.AuthReceived);
            }
            else
            {
                searchCriteria.RequestStatus = requestStatusComboBox.SelectedValue.ToString();
            }
            searchCriteria.RequestReason      = (requestReasonComboBox.SelectedIndex == 0) 
                                                ? null 
                                                : requestReasonComboBox.SelectedValue.ToString();

            if (!string.IsNullOrEmpty(balanceDueTextBox.Text.Trim()))
            {
                searchCriteria.BalanceDueOperator = balanceDueComboBox.SelectedValue.ToString();
                searchCriteria.BalanceDue = balanceDueTextBox.Text.Trim();
            }           
            
            if (requestorTypeComboBox.SelectedItem != null)
            {
                searchCriteria.RequestorType = Convert.ToInt64(requestorTypeComboBox.SelectedValue, System.Threading.Thread.CurrentThread.CurrentUICulture);
                searchCriteria.RequestorTypeName = (requestorTypeComboBox.SelectedItem as RequestorTypeDetails).Name;
            }
            searchCriteria.RequestorName = requestorNameTextBox.Text.Trim();
            
            try
            {
                invoiceNumberTextBox.Text = invoiceNumberTextBox.Text.Trim();
                searchCriteria.InvoiceNumber = invoiceNumberTextBox.Text;
                searchCriteria.RequestId = requestIdTextBox.Text;
            }
            catch (FormatException stringFormatException)
            {
                ROIViewUtility.Handle(Context, new ROIException(ROIErrorCodes.NotValidNumber, stringFormatException));
            }
            
            //if ((presetDateRange.DateRangeOption.Trim() == ROIConstants.AllOption.Trim())||
            //   ((presetDateRange.DateRangeOption.Trim() == ROIConstants.CustomRange.Trim()) && 
            //   (presetDateRange.fromNullableDTP.DateValue == null || presetDateRange.toNullableDTP.DateValue == null || 
            //   !presetDateRange.IsValidDateRange)))
            //{
            //    searchCriteria.ReceiptDate = null;
            //    searchCriteria.FromDate    = null;
            //    searchCriteria.ToDate      = null;
            //}
            //else
            //{
            //    searchCriteria.ReceiptDate = presetDateRange.DateRangeOption;
            //    searchCriteria.FromDate    = presetDateRange.FromDate;
            //    searchCriteria.ToDate      = presetDateRange.ToDate;
            //}

            if ((completeDateRange.DateRangeOption.Trim() == ROIConstants.AllOption.Trim()) ||
              ((completeDateRange.DateRangeOption.Trim() == ROIConstants.CustomRange.Trim()) &&
              (completeDateRange.fromCustomDate.Text == null ||
              completeDateRange.toCustomDate.Text == null ||
              !completeDateRange.IsValidDateRange)))
            {
                searchCriteria.CompletedDate     = null;
                searchCriteria.CompletedFromDate = null;
                searchCriteria.CompletedToDate   = null;
            }
            else
            {
                searchCriteria.CompletedDate     = completeDateRange.DateRangeOption;
                searchCriteria.CompletedFromDate = completeDateRange.fromCustomDate.Text;
                searchCriteria.CompletedToDate = completeDateRange.toCustomDate.Text;
            }


            if ((customDateRange.DateRangeOption.Trim() == ROIConstants.AllOption.Trim()) ||
                ((customDateRange.DateRangeOption.Trim() == ROIConstants.CustomRange.Trim()) &&
                (customDateRange.fromCustomDate.Text == null ||
                customDateRange.toCustomDate.Text == null ||
                !customDateRange.IsValidDateRange)))
            {
                searchCriteria.CustomDate = null;
                searchCriteria.CustomFromDate = null;
                searchCriteria.CustomToDate = null;
            }
            else
            {
                //searchCriteria.CompletedDate = customDateRange.DateRangeOption;
                searchCriteria.ReceiptDate = customDateRange.DateRangeOption;
                searchCriteria.ReceiptFromDate = customDateRange.fromCustomDate.Text;
                searchCriteria.ReceiptToDate = customDateRange.toCustomDate.Text;
                searchCriteria.CustomFromDate = customDateRange.fromCustomDate.Text;
                searchCriteria.CustomToDate = customDateRange.toCustomDate.Text;
            }
            
            searchCriteria.IsSearch  = true;
            searchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            
            return searchCriteria;
        }

        /// <summary>
        /// Clear control values.
        /// </summary>
        private void ClearControls()
        {
            patientFirstNameTextBox.Text             = string.Empty;
            patientLastNameTextBox.Text              = string.Empty;
            patientSsnTextBox.Clear();
            patientMrnTextBox.Text                   = string.Empty;
            patientEncounterTextBox.Text             = string.Empty;
            patientEpnTextBox.Text                   = string.Empty;
            facilityComboBox.SelectedIndex           = 0;
            SetDOBDateNullValue();
            requestStatusComboBox.SelectedIndex      = 0;
            requestReasonComboBox.SelectedIndex      = 0;
            balanceDueComboBox.SelectedIndex         = 0;
            requestorTypeComboBox.SelectedIndex      = 0;
            requestorNameTextBox.Text                = string.Empty;
            //presetDateRange.rangeCombo.SelectedIndex   = 0;
            //completeDateRange.rangeCombo.SelectedIndex = 0;
            //presetDateRange.Reset();
            //completeDateRange.Reset();
            invoiceNumberTextBox.Text                = string.Empty;
            requestIdTextBox.Text                    = string.Empty;
            balanceDueTextBox.Text                   = string.Empty;
            customDateRange.customRangeCombo.SelectedIndex = 0;
            customDateRange.Reset();
            completeDateRange.customRangeCombo.SelectedIndex = 0;
            completeDateRange.Reset();
        }

        /// <summary>
        /// Clears the searchcritia
        /// </summary>
        public void ClearSearchCriteria()
        {
            if (searchResult != null)
            {
                if (searchResult.RequestSearchResult.Count == 0)
                {
                    resetRequestButton.PerformClick();
                }
            }
        }

        internal void RefreshSearch()
        {
            if (RequestValidator.ValidatePrimarySearchFields(GetData()))
                findRequestButton.PerformClick();
        }
     

        private void FindRequestButtonClick(object sender, EventArgs e)
        {
            IsError = false;
            findRequestButton.Focus();
            ResourceManager rm;
            errorProvider.Clear();
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);           
            try
            {
                FindRequestCriteria criteria = GetData();
                rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                if (dobTextBox.Text.Trim() != rm.GetString(ROIConstants.DefaultDate).Trim())
                {
                    if (!dobTextBox.IsValidDate)
                    {
                        IsError = true;
                        rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                        string errorMessage = rm.GetString(ROIErrorCodes.InvalidDate);
                        errorProvider.SetError(dobTextBox, errorMessage);
                    }
                    else if (!ROIViewUtility.IsValidDate(dobTextBox.Text.Trim()))
                    {
                        IsError = true;
                        rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                        string errorMessage = rm.GetString(ROIErrorCodes.InvalidSqlDate);
                        errorProvider.SetError(dobTextBox, errorMessage);
                    }
                }

                if (((!string.IsNullOrEmpty(customDateRange.fromCustomDate.Text) && !customDateRange.fromCustomDate.Text.Equals("//")) && customDateRange.fromCustomDate.Text.Length > 9)
                    && ((!string.IsNullOrEmpty(customDateRange.toCustomDate.Text) && !customDateRange.toCustomDate.Text.Equals("//")) && customDateRange.toCustomDate.Text.Length > 9))
                { if (!customDateRange.CheckFutureValidate(customDateRange.fromCustomDate.Text, customDateRange.toCustomDate.Text)) { return; } }

                if (((!string.IsNullOrEmpty(completeDateRange.fromCustomDate.Text) && !completeDateRange.fromCustomDate.Text.Equals("//")) && completeDateRange.fromCustomDate.Text.Length > 9)
                && ((!string.IsNullOrEmpty(completeDateRange.toCustomDate.Text) && !completeDateRange.toCustomDate.Text.Equals("//")) && completeDateRange.toCustomDate.Text.Length > 9))
                { if (!completeDateRange.CheckFutureValidate(completeDateRange.fromCustomDate.Text, completeDateRange.toCustomDate.Text)) { return; } }
                
                searchResult = RequestController.Instance.FindRequest(criteria, null);                                     
                ApplicationEventArgs ae = new ApplicationEventArgs(searchResult, this);
                RequestEvents.OnRequestSearched(Pane, ae);
                //CR# - 377569
                RegID = false;
            }
            catch (ROIException cause)
            {   
                rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        private void newRequestButton_Click(object sender, EventArgs e)
        {
            errorProvider.Clear();
            ClearControls();
            
            CreateRequestInfo newRequest = new CreateRequestInfo(new RequestDetails(), new EventHandler(Process_CancelCreateRequest));
            ApplicationEventArgs ae = new ApplicationEventArgs(newRequest, this);
            RequestEvents.OnCreateRequest(Pane, ae);
        }

        private void resetButton_Click(object sender, EventArgs e)
        {
            errorProvider.Clear();

            DisableEvents();
            ClearControls();
            EnableEvents();

            findRequestButton.Enabled = false;
            ApplicationEventArgs ae = new ApplicationEventArgs(null, this);
            RequestEvents.OnSearchReset(Pane, ae);
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            RequestEvents.OnCancelCreateRequest(null, new ApplicationEventArgs(ROIConstants.FindRequest, null));
        }

        /// <summary>
        /// Method to display the icon with inline error.
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.InvalidLastName      : return patientLastNameTextBox;
                case ROIErrorCodes.InvalidFirstName     : return patientFirstNameTextBox;
                case ROIErrorCodes.InvalidSsnSearch     : return patientSsnTextBox;
                case ROIErrorCodes.InvalidMrn           : return patientMrnTextBox;
                case ROIErrorCodes.InvalidEncounter     : return patientEncounterTextBox;
                case ROIErrorCodes.InvalidEpn           : return patientEpnTextBox;
                case ROIErrorCodes.InvalidContactName   : return requestorNameTextBox;
                case ROIErrorCodes.InvalidBalanceDue    : return balanceDueTextBox;
                case ROIErrorCodes.InvalidInvoiceNumber : return invoiceNumberTextBox;
                case ROIErrorCodes.InvalidRequestId     : return requestIdTextBox;
                case ROIErrorCodes.InvalidRequestorName : return requestorNameTextBox;
                case ROIErrorCodes.IncorrectDate        : return dobTextBox;
            }
            return null;
        }

        private void SetSsnFormat()
        {
            patientSsnTextBox.Mask = "999-99-9999";
            patientSsnTextBox.PromptChar = 'x';
            patientSsnTextBox.TextMaskFormat = MaskFormat.IncludeLiterals;
            patientSsnTextBox.AllowPromptAsInput = false;
        }

        #endregion        

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            newRequestButton.Enabled = IsAllowed(ROISecurityRights.ROICreateRequest);
        }

        #endregion             

        #region Properties

        public static bool IsError
        {
            get { return isError; }
            set { isError = value; }
        }

        #endregion

        //CR# - 377569
        private void requestIdTextBox_TextChanged(object sender, EventArgs e)
        {
            if (requestIdTextBox.Text.StartsWith("0"))
            {
                findRequestButton.Enabled = false;
                RegID = true;
            }
            else
            {
                findRequestButton.Enabled = true;
                RegID = false;
            }
        }
    }
}
