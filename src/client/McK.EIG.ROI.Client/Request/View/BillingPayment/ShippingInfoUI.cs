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
using System.Collections;
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Globalization;
using System.Resources;
using System.Text.RegularExpressions;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Requestors.Controller;
using System.Drawing;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Class which is used to maintain the shipping information
    /// </summary>
    public partial class ShippingInfoUI : ROIBaseUI
    {
        #region Fields

        private const string CustomShippingAddressTitle               = "CustomShippingAddressDialog.Title";
        private const string CustomShippingAddressMessage             = "CustomShippingAddressDialog.Message";
        private const string CustomShippingAddressOkButton            = "CustomShippingAddressDialog.OkButton";
        private const string CustomShippingAddressIgnoreButton        = "CustomShippingAddressDialog.IgnoreButton";
        private const string CustomShippingAddressCancelButton        = "CustomShippingAddressDialog.CancelButton";
        private const string CustomShippingAddressOkButtonToolTip     = "CustomShippingAddressDialog.OkButton";
        private const string CustomShippingAddressIgnoreButtonToolTip = "CustomShippingAddressDialog.IgnoreButton";
        private const string CustomShippingAddressCancelButtonToolTip = "CustomShippingAddressDialog.CancelButton";

        //CR#375009
       public static Int32 nonPrintableIndex;

        private EventHandler updateShippingChargeHandler;
        private EventHandler dirtyDataHandler;
        private EventHandler shippingAddressChangeHandler;
        private EventHandler outputMethodChangedHandler;

        private RequestorDetails requestor;
        private ShippingInfo shippingInfo;

        private string shippingUrl;
        private decimal shippingWeight;
        private double shippingCharge;
        private long totalPages;
        private long pageWeight;
        private bool isDirty;
        private bool isAddressChanged;
        private bool isUpdateRequestorAddress;
        private int nonPrintableAttachmentCount;
        public int NonPrintableAttachmentCount
        {
            get { return nonPrintableAttachmentCount; }
            set { nonPrintableAttachmentCount = value; }
        }
        //ROI16.0 zipcode
        private System.Collections.Generic.List<CountryCodeDetails> CountryDetails;
        private int prevValue;
        private bool selectedCountry;
        private bool defaultCountry;
        private string addType;

        #endregion

        #region Constructor

        public ShippingInfoUI()
        {
            InitializeComponent();
            InitEvents();
            shippingPanel.Enabled = true;
            noError = true;
            isEnabled = true;
            selectedCountry = false;
            defaultCountry = false;
        }
        
        #endregion

        #region Methods

        /// <summary>
        /// Initialize the events
        /// </summary>
        private void InitEvents()
        {
            updateShippingChargeHandler  = new EventHandler(Process_UpdateShippingCharge);
            dirtyDataHandler             = new EventHandler(MarkDirty);
            shippingAddressChangeHandler = new EventHandler(Process_ShippingAddressChanged);
            outputMethodChangedHandler   = new EventHandler(Process_OutputMethodChanged);
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, alternateAddressRadioButton);
            SetLabel(rm, mainAddressRadioButton);
            SetLabel(rm, outputMethodLabel);
            SetLabel(rm, populateAddressLabel);
            SetLabel(rm, printRadioButton);
            SetLabel(rm, saveAsRadioButton);
            SetLabel(rm, faxRadioButton);
            SetLabel(rm, emailRadioButton);
            SetLabel(rm, NonPrintableLabel);
            SetLabel(rm, releaseShippedLabel);
            SetLabel(rm, resetAddressButton);
            SetLabel(rm, shippingMethodLabel);
            SetLabel(rm, shippingChargesLabel);
            SetLabel(rm, shippingInfoGroupBox);
            SetLabel(rm, shippingWeightLabel);
            SetLabel(rm, trackingNumberLabel);
            SetLabel(rm, webUrlLabel);            
            SetLabel(rm, yesRadioButton);
            SetLabel(rm, noRadioButton);
            SetLabel(rm, dollarLabel);
            SetLabel(rm, discRadioButton);

            //Address
            SetLabel(rm, address1Label);
            SetLabel(rm, address2Label);
            SetLabel(rm, address3Label);
            SetLabel(rm, cityLabel);
            SetLabel(rm, stateLabel);
            SetLabel(rm, zipLabel);
            SetLabel(rm, countrylabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, resetAddressButton);
            SetTooltip(rm, toolTip, address1TextBox);
            SetTooltip(rm, toolTip, address2TextBox);
            SetTooltip(rm, toolTip, address3TextBox);
            SetTooltip(rm, toolTip, cityTextBox);
            SetTooltip(rm, toolTip, stateTextBox);
            SetTooltip(rm, toolTip, zipTextBox);
            SetTooltip(rm, toolTip, trackingNumberTextBox);
            SetTooltip(rm, toolTip, shippingChargesTextBox);
        }

        protected override string GetLocalizeKey(Control control)
        {
            if (string.Compare(control.Name, zipLabel.Name, true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
            {
                return control.Name + "." + GetType().Name;
            }
            return control.Name;
        }

        public bool ShippingMethodSelected
        {
            get { return (saveAsRadioButton.Checked || printRadioButton.Checked ||
            faxRadioButton.Checked || emailRadioButton.Checked);}
        }

        public bool AddressSelected
        {
            get { return (mainAddressRadioButton.Checked || alternateAddressRadioButton.Checked); }
        }

        public string AddressType
        {
            get
            {
                return addType;
            }
            set { addType = value; }
        }

        /// <summary>
        /// Gets the localized key of the control
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name; 
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        private void EnableEvents()
        {
            printRadioButton.CheckedChanged  += outputMethodChangedHandler;
            saveAsRadioButton.CheckedChanged += outputMethodChangedHandler;
            faxRadioButton.CheckedChanged    += outputMethodChangedHandler;
            emailRadioButton.CheckedChanged += outputMethodChangedHandler;
            discRadioButton.CheckedChanged += outputMethodChangedHandler;

            shippingChargesTextBox.TextChanged += updateShippingChargeHandler;

            trackingNumberTextBox.TextChanged           += dirtyDataHandler;
            shippingMethodComboBox.SelectedIndexChanged += dirtyDataHandler;
            nonPrintableComboBox.SelectedIndexChanged += dirtyDataHandler;

            //Address fields
            address1TextBox.TextChanged     += shippingAddressChangeHandler;
            address2TextBox.TextChanged     += shippingAddressChangeHandler;
            address3TextBox.TextChanged     += shippingAddressChangeHandler;
            cityTextBox.TextChanged         += shippingAddressChangeHandler;
            stateTextBox.TextChanged        += shippingAddressChangeHandler;
            zipTextBox.TextChanged          += shippingAddressChangeHandler;
            requestCountryComboBox.SelectedIndexChanged += shippingAddressChangeHandler;

            requestCountryComboBox.SelectedIndexChanged += dirtyDataHandler;
            
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        private void DisableEvents()
        {
            printRadioButton.CheckedChanged  -= outputMethodChangedHandler;
            saveAsRadioButton.CheckedChanged -= outputMethodChangedHandler;
            faxRadioButton.CheckedChanged    -= outputMethodChangedHandler;
            emailRadioButton.CheckedChanged -= outputMethodChangedHandler;
            discRadioButton.CheckedChanged -= outputMethodChangedHandler;

            shippingChargesTextBox.TextChanged -= updateShippingChargeHandler;

            trackingNumberTextBox.TextChanged -= dirtyDataHandler;
            shippingMethodComboBox.SelectedIndexChanged -= dirtyDataHandler;

            //Address fields
            address1TextBox.TextChanged     -= shippingAddressChangeHandler;
            address2TextBox.TextChanged     -= shippingAddressChangeHandler;
            address3TextBox.TextChanged     -= shippingAddressChangeHandler;
            cityTextBox.TextChanged         -= shippingAddressChangeHandler;
            stateTextBox.TextChanged        -= shippingAddressChangeHandler;
            zipTextBox.TextChanged          -= shippingAddressChangeHandler;
            requestCountryComboBox.SelectedIndexChanged -= shippingAddressChangeHandler;

            requestCountryComboBox.SelectedIndexChanged -= dirtyDataHandler;
            
        }

        /// <summary>
        /// Mark the data as dirty state
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            IsDirty = true;
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
        }

        /// <summary>
        /// Disable the Main and Alternate readio buttons if user changed 
        /// the shipping addresss
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_ShippingAddressChanged(object sender, EventArgs e)
        {
            isAddressChanged = true;
            if (mainAddressRadioButton.Checked)
                addType = "Main";
            else if (alternateAddressRadioButton.Checked)
                addType = "Alternate";
            mainAddressRadioButton.Checked = alternateAddressRadioButton.Checked = false;
            mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = false;
            resetAddressButton.Enabled = true;
            dirtyDataHandler(sender, e);
        }

        /// <summary>
        /// Update the pending release cost after changing the shipping charge by user
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateShippingCharge(object sender, EventArgs e)
        {
            errorProvider.Clear();
            if (!double.TryParse(shippingChargesTextBox.Text, out this.shippingCharge) || shippingCharge < 0 ||
                !Regex.IsMatch(Convert.ToString(shippingCharge, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.AmountFormat))
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(shippingChargesTextBox, rm.GetString(ROIErrorCodes.InvalidShippingCharge));
                shippingCharge = 0;
                noError = false;
            }
            else
            {   
                noError = true;
            }

            RequestEvents.OnReleaseCostUpdated(Pane, null);
            dirtyDataHandler(sender, e);
        }
        
        private void shippingChargesTextBox_Leave(object sender, EventArgs e)
        {
            if (noError)
            {
                shippingChargesTextBox.Text = ROIViewUtility.FormattedAmount(shippingCharge);
            }
        }

        private void Process_OutputMethodChanged(object sender, EventArgs e)
        {   
            isAddressChanged = false;
            //CR348606
            NonPrinableComboBox.Enabled = requiredImg.Visible = nonPrintableAttachmentCount > 0;
            if (printRadioButton.Checked)
            {
                EnableReleaseShippedButtons(true);
                shippingWeightLabel.Visible = shippingWeightValueLabel.Visible = true;                
            }
            else if (saveAsRadioButton.Checked)
            {
                EnableReleaseShippedButtons(true);
                shippingWeightLabel.Visible = shippingWeightValueLabel.Visible = false;
                nonPrintableComboBox.Enabled = requiredImg.Visible = false;                
            }
            else if (faxRadioButton.Checked)
            {
                DisableEvents();
                ClearDataForFaxEmail();
                EnableEvents();
                EnableReleaseShippedButtons(false);
                shippingInfoGroupBox.Enabled = false;                
            }
            else if (emailRadioButton.Checked)
            {
                DisableEvents();
                ClearDataForFaxEmail();
                EnableEvents();
                EnableReleaseShippedButtons(false);
                shippingInfoGroupBox.Enabled = false;
                nonPrintableComboBox.Enabled = requiredImg.Visible = false;
            }
            else if (discRadioButton.Checked)
            {
                DisableEvents();
                ClearDataForFaxEmail();
                EnableEvents();
                EnableReleaseShippedButtons(false);
                shippingInfoGroupBox.Enabled = false;
                nonPrintableComboBox.Enabled = requiredImg.Visible = false;
            }

            dirtyDataHandler(sender, e);
        }

        /// <summary>
        /// Occurs when yes radio button is been checked
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void yesRadioButton_CheckedChanged(object sender, EventArgs e)
        {
            shippingInfoGroupBox.Enabled = true;

            if (shippingInfo!= null && shippingInfo.AddressType != RequestorAddressType.None)
            {
                SetShippingAddressInfo();
            }
            else
            {
                mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = true;
                mainAddressRadioButton.Checked = true;
            }
            SelectDefaultShippingMethod();
            isAddressChanged = false;
        }

        private void SelectDefaultShippingMethod()
        {
            if (shippingMethodComboBox.Items.Count == 0) return;
            Collection<DeliveryMethodDetails> shippingMethods = (Collection<DeliveryMethodDetails>)shippingMethodComboBox.DataSource;
            List<DeliveryMethodDetails> shippingMethodList = new List<DeliveryMethodDetails>(shippingMethods);
            DeliveryMethodDetails defaultShippingMethod = shippingMethodList.Find(delegate(DeliveryMethodDetails shiipingMethod) { return shiipingMethod.IsDefault; });

            if (defaultShippingMethod != null)
            {
                shippingMethodComboBox.SelectedItem = defaultShippingMethod;
            }
        }

        /// <summary>
        /// Occurs when no radio button is been checked
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void noRadioButton_CheckedChanged(object sender, EventArgs e)
        {   
            ClearShippingInfo();
            shippingInfoGroupBox.Enabled = false;
            isAddressChanged = false;
        }

        private void EnableReleaseShippedButtons(bool enable)
        {
            yesRadioButton.Enabled = noRadioButton.Enabled = enable;
        }

        /// <summary>
        /// Occurs when alternate radion button is been checked
        /// Populates requestor's alternate address details
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void alternateAddressRadioButton_CheckedChanged(object sender, EventArgs e)
        {
            isAddressChanged = false;
            if (alternateAddressRadioButton.Checked)
            {
                DisableEvents();
                PopulateAddressDetails(requestor.AltAddress, true);
                resetAddressButton.Enabled = false;
                MarkDirty(sender, e);
                EnableEvents();
            }
            else
            {
                mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = true;
            }
        }

        /// <summary>
        /// Occurs when main radion button is been checked
        /// Populates requestor's main address details
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void mainAddressRadioButton_CheckedChanged(object sender, EventArgs e)
        {
            isAddressChanged = false;
            if (mainAddressRadioButton.Checked)
            {
                DisableEvents();
                PopulateAddressDetails(requestor.MainAddress, true);                
                resetAddressButton.Enabled = false;
                MarkDirty(sender, e);
                EnableEvents();
            }
            else
            {
                mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = true;
            }
        }

        /// <summary>
        /// Gets the shipping info
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            ReleaseDetails release = (ReleaseDetails)data;
            shippingInfo = new ShippingInfo();

            //CR#375009
           nonPrintableIndex = nonPrintableComboBox.SelectedIndex;

            if (printRadioButton.Checked)
            {
                shippingInfo.OutputMethod = OutputMethod.Print;
            }
            else if (saveAsRadioButton.Checked)
            {
                shippingInfo.OutputMethod = OutputMethod.SaveAsFile;
            }
            else if (faxRadioButton.Checked)
            {
                shippingInfo.OutputMethod = OutputMethod.Fax;
            }
            else if (emailRadioButton.Checked)
            {
                shippingInfo.OutputMethod = OutputMethod.Email;
            }
            else if (discRadioButton.Checked)
            {
                shippingInfo.OutputMethod = OutputMethod.Disc;
            }
            if (nonPrintableIndex > 0)
            {
                shippingInfo.NonPrintableAttachmentQueue = nonPrintableComboBox.Text;
            }
                      

            if (yesRadioButton.Checked)
            {
                shippingInfo.WillReleaseShipped = true;
                if (mainAddressRadioButton.Checked)
                {
                    shippingInfo.AddressType = RequestorAddressType.Main;
                }
                else if (alternateAddressRadioButton.Checked)
                {
                    shippingInfo.AddressType = RequestorAddressType.Alternate;
                }
                else
                {
                    shippingInfo.AddressType = RequestorAddressType.Custom;
                }

                shippingInfo.ShippingAddress = GetShippingAddressDetails();

                if (shippingMethodComboBox.SelectedIndex != 0)
                {
                    shippingInfo.ShippingMethod = shippingMethodComboBox.Text;
                    shippingInfo.ShippingMethodId = Convert.ToInt32(shippingMethodComboBox.SelectedValue, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }

                if (!string.IsNullOrEmpty(shippingUrl))
                {
                    shippingInfo.ShippingWebAddress = shippingUrl;
                }

                shippingInfo.TrackingNumber = trackingNumberTextBox.Text.Trim();
                shippingInfo.ShippingCharge = shippingCharge;

                if (!saveAsRadioButton.Checked)
                {
                    shippingInfo.ShippingWeight = shippingWeight;
                }

                if(isAddressChanged)
                {
                    DialogResult result = ShowCustomShippingAddressDialog();
                    isAddressChanged = false;
                    if (result == DialogResult.Cancel)
                    {
                        mainAddressRadioButton.Checked = true;
                        mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = true;
                        shippingInfo.AddressType = RequestorAddressType.Main;
                        shippingInfo.ShippingAddress = GetShippingAddressDetails();
                    }
                }
            }
            else
            {
                if (requestor.MainAddress != null)
                {
                    AddressDetails address = new AddressDetails();

                    address.Address1 = requestor.MainAddress.Address1;
                    address.Address2 = requestor.MainAddress.Address2;
                    address.Address3 = requestor.MainAddress.Address3;
                    address.City = requestor.MainAddress.City;
                    address.State = requestor.MainAddress.State;
                    address.PostalCode = requestor.MainAddress.PostalCode;
                    int i = 0;
                    foreach (CountryCodeDetails countryList in CountryDetails)
                    {
                        if (requestor.MainAddress.CountryCode == CountryDetails[i].CountryCode)
                        {
                            address.CountryName = CountryDetails[i].CountryName;                            
                           // break;
                        }
                    //    else if (CountryDetails[i].CountryCode == "US")
                    //    {
                    //        requestCountryComboBox.SelectedItem = CountryDetails[i].CountryName;
                    //        break;
                    //    }
                        i++;
                    }
                    shippingInfo.AddressType = RequestorAddressType.Main;
                    shippingInfo.ShippingAddress = address;
                }
            }

            release.ShippingDetails = shippingInfo;
            return shippingInfo;
        }

        /// <summary>
        /// Updates the shipping weight based total pages given
        /// </summary>
        /// <param name="totalPages"></param>
        public void UpdateShippingWeight(long totalPages)
        {
            if (!saveAsRadioButton.Checked)
            {
                this.totalPages = totalPages;
                if (totalPages > 0)
                {
                    decimal weight = Convert.ToDecimal(totalPages, System.Threading.Thread.CurrentThread.CurrentUICulture) / Convert.ToDecimal(pageWeight, System.Threading.Thread.CurrentThread.CurrentUICulture);
                    shippingWeight = Math.Round(weight, 2);
                }
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                shippingWeightValueLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString(shippingWeightValueLabel.Name), shippingWeight);
            }
        }

        /// <summary>
        /// Clears control values
        /// </summary>
        private void ClearData()
        {
            errorProvider.Clear();
            printRadioButton.Checked = saveAsRadioButton.Checked = false;
            yesRadioButton.Enabled = noRadioButton.Enabled = false;
            yesRadioButton.Checked = noRadioButton.Checked = false;
            emailRadioButton.Checked = faxRadioButton.Checked = false;
            discRadioButton.Checked = false;
            ClearShippingInfo();
        }
        private void ClearDataForFaxEmail()
        {
            errorProvider.Clear();
            printRadioButton.Checked = saveAsRadioButton.Checked = false;
            yesRadioButton.Enabled = noRadioButton.Enabled = false;
            yesRadioButton.Checked = noRadioButton.Checked = false;
            
            ClearShippingInfo();
        }

        private void ClearShippingInfo()
        {
            //Address details

            mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = false;
            mainAddressRadioButton.Checked = alternateAddressRadioButton.Checked = false;

            address1TextBox.Text = string.Empty;
            address2TextBox.Text = string.Empty;
            address3TextBox.Text = string.Empty;
            cityTextBox.Text = string.Empty;
            stateTextBox.Text = string.Empty;
            zipTextBox.Text = string.Empty;

            trackingNumberTextBox.Text = string.Empty;
            shippingChargesTextBox.Text = ROIViewUtility.FormattedAmount(0);

            shippingMethodComboBox.SelectedIndex = 0;
            if (nonPrintableComboBox.SelectedIndex != -1)
            {
              //  nonPrintableComboBox.SelectedIndex = 0;
                //CR#375009
              nonPrintableComboBox.SelectedIndex = nonPrintableIndex;
                
            }
            shippingUrlInfoPanel.Visible = false;
            isAddressChanged = false;
        }

        /// <summary>
        /// Sets the data
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data, List<CountryCodeDetails> countryCodeDetails)
        {
            DisableEvents();
            ClearData();
            EnableEvents();
            CountryDetailsList(countryCodeDetails);

            if (data != null)
            {
                shippingInfo = (ShippingInfo)data;

                switch (shippingInfo.OutputMethod)
                {
                    case OutputMethod.Print: printRadioButton.Checked = true; break;
                    case OutputMethod.SaveAsFile: saveAsRadioButton.Checked = true; break;
                    case OutputMethod.Fax: faxRadioButton.Checked = true; break;
                    case OutputMethod.Email: emailRadioButton.Checked = true; break;
                    case OutputMethod.Disc: discRadioButton.Checked = true; break;
                }

                yesRadioButton.Checked = shippingInfo.WillReleaseShipped;
                noRadioButton.Checked = !shippingInfo.WillReleaseShipped;

                shippingMethodComboBox.SelectedValue = shippingInfo.ShippingMethodId;
                shippingChargesTextBox.Text = ROIViewUtility.FormattedAmount(shippingInfo.ShippingCharge); //(System.Threading.Thread.CurrentThread.CurrentUICulture);
                trackingNumberTextBox.Text = shippingInfo.TrackingNumber;
                shippingWeight = shippingInfo.ShippingWeight;
                if (shippingInfo.ShippingAddress.CountryName != null)
                {
                    requestCountryComboBox.SelectedItem = shippingInfo.ShippingAddress.CountryName;
                }
                if (shippingInfo.NonPrintableAttachmentQueue != null)
                {
                    ArrayList prop = (ArrayList)nonPrintableComboBox.DataSource;
                    int idx = 0;
                    foreach (KeyValuePair<string, OutputDestinationDetails> item in prop)
                    {
                        if (item.Key == shippingInfo.NonPrintableAttachmentQueue)
                        {
                            nonPrintableComboBox.SelectedIndex = idx;
                            break;
                        }
                        idx++;
                    }
                }
                SetShippingAddressInfo();
                UpdateShippingWeight(totalPages);
            }
            ApplySecurityRights();
        }
        private void CountryDetailsList(List<CountryCodeDetails> CountryCodeDetails)
        {
            //int i = 0;
            CountryDetails = CountryCodeDetails;
            string defaultcontrystr = string.Empty;
            requestCountryComboBox.Items.Clear();
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                requestCountryComboBox.Items.Add(countryList.CountryName);
                if (countryList.DefaultCountry)
                {
                    defaultcontrystr = countryList.CountryName;
                }
                //else if (CountryDetails[i].CountryCode == "US")
                //{
                //    defaultCountry = true;
                //    requestCountryComboBox.SelectedItem = CountryDetails[i].CountryName;
                //    defaultCountry = false;
                //    break;
                //}
            }

            
            defaultCountry = true;
            requestCountryComboBox.SelectedItem = defaultcontrystr;
            defaultCountry = false;
                //break;
            
        }
        private void requestCountryComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!defaultCountry)
            {
                if (requestCountryComboBox.SelectedIndex != prevValue)
                {
                    selectedCountry = true;
                }
            }
            prevValue = requestCountryComboBox.SelectedIndex;

        }
        private void ApplySecurityRights()
        {
            if (!IsAllowed(ROISecurityRights.ROIPrintFax))
            {
                printRadioButton.Enabled = false;
                faxRadioButton.Enabled = false;
            }

            if (!IsAllowed(ROISecurityRights.ROIExportToPDF))
            {
                saveAsRadioButton.Enabled = false;
            }

            if (!IsAllowed(ROISecurityRights.ROIEmail))
            {
                emailRadioButton.Enabled = false;
            }

            if (!IsAllowed(ROISecurityRights.ROIReleaseToDisc))
            {
                discRadioButton.Enabled = false;
            }
        }

        /// <summary>
        /// Set Shipping address details
        /// </summary>
        private void SetShippingAddressInfo()
        {
            switch (shippingInfo.AddressType)
            {

                case McK.EIG.ROI.Client.Request.Model.RequestorAddressType.Main:
                    {
                        PopulateAddressDetails(requestor.MainAddress);
                        mainAddressRadioButton.Checked = true;
                        mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = true;
                        break;
                    }
                case McK.EIG.ROI.Client.Request.Model.RequestorAddressType.Alternate:
                    {
                        alternateAddressRadioButton.Checked = true;
                        mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = true;
                        break;
                    }
                case McK.EIG.ROI.Client.Request.Model.RequestorAddressType.Custom:
                    {
                        PopulateAddressDetails(shippingInfo.ShippingAddress);
                        resetAddressButton.Enabled = true;
                        mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = false;
                        break;
                    }
            }
        }

        /// <summary>
        /// Populates address details
        /// </summary>
        /// <param name="address"></param>
        private void PopulateAddressDetails(AddressDetails address, bool isDisableEvents)
        {
            if (isDisableEvents)
            {
                DisableEvents();
            }
            if (address == null) return;

            address1TextBox.Text = address.Address1;
            address2TextBox.Text = address.Address2;
            address3TextBox.Text = address.Address3;
            cityTextBox.Text = address.City;
            stateTextBox.Text = address.State;            
            int i = 0;
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                if ( address.CountrySeq == CountryDetails[i].CountrySeq)
                {
                    requestCountryComboBox.Text = CountryDetails[i].CountryName;
                    address.NewCountry = selectedCountry;
                    break;
                }
                i++;
            }
              zipTextBox.Text = address.PostalCode;
           
            if (isDisableEvents)
            {
                EnableEvents();
            }
        }


        /// <summary>
        /// Populates address details
        /// </summary>
        /// <param name="address"></param>
        private void PopulateAddressDetails(AddressDetails address)
        {
            PopulateAddressDetails(address, true);
        }

        /// <summary>
        /// Gets shipping address details
        /// </summary>
        /// <returns></returns>
        private AddressDetails GetShippingAddressDetails()
        {
            AddressDetails address = new AddressDetails();
            address.Address1 = address1TextBox.Text.Trim();
            address.Address2 = address2TextBox.Text.Trim();
            address.Address3 = address3TextBox.Text.Trim();
            address.City     = cityTextBox.Text.Trim();
            address.State    = stateTextBox.Text.Trim();
            address.PostalCode = zipTextBox.Text.Trim();
            int i = 0;
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                if (requestCountryComboBox.Text == CountryDetails[i].CountryName)
                {
                    address.CountryName = CountryDetails[i].CountryName;
                    address.CountryCode = CountryDetails[i].CountryCode;
                    break;
                }
                i++;
            }
         
            return address;
        }

        /// <summary>
        /// Populates availed shipping methods in combo box
        /// </summary>
        /// <param name="shippingMethods"></param>
        /// <param name="requestor"></param>
        public void PrePopulate(Collection<DeliveryMethodDetails> shippingMethods, RequestorDetails requestor,
                                OutputPropertyDetails outputPropertyDetails, int nonPrintableAttachmentCount, PageWeightDetails pageWightDetails)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            DeliveryMethodDetails shippingMethod = new DeliveryMethodDetails();
            shippingMethod.Id = 0;
            shippingMethod.Name = rm.GetString("pleaseSelectCombo");
            shippingMethods.Insert(0, shippingMethod);

            shippingMethodComboBox.DataSource = shippingMethods;
            shippingMethodComboBox.DisplayMember = "Name";
            shippingMethodComboBox.ValueMember = "Id";

            pageWeight = pageWightDetails.PageWeight;

            this.requestor = requestor;

            //CR# - 349862
            if (outputPropertyDetails != null)
            {
                ArrayList properties = new ArrayList();
                properties.Add(new KeyValuePair<string, OutputDestinationDetails>("Please Select", new OutputDestinationDetails()));

                foreach (OutputDestinationDetails propertyDetails in outputPropertyDetails.OutputDestinationDetails)
                {
                    properties.Add(new KeyValuePair<string, OutputDestinationDetails>(propertyDetails.Name, propertyDetails));
                }
                nonPrintableComboBox.DisplayMember = "key";
                nonPrintableComboBox.ValueMember = "value";
                nonPrintableComboBox.DataSource = properties;                
            }
            //CR# - 349862

            this.nonPrintableAttachmentCount = nonPrintableAttachmentCount;
            //CR348606
            NonPrinableComboBox.Enabled = requiredImg.Visible = nonPrintableAttachmentCount > 0;
        }

        /// <summary>
        /// Occurs when combo selection has been changed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void shippingMethodComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            DeliveryMethodDetails shippingMethod = (DeliveryMethodDetails)shippingMethodComboBox.SelectedItem;
            if (shippingMethod.Url != null)
            {
                shippingUrlInfoPanel.Visible = true;

                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                string Url = rm.GetString(webUrlLabel.Name);
                shippingUrl = shippingMethod.Url.OriginalString;
                webUrlLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, Url, shippingMethod.Url.OriginalString);
                webUrlLabel.LinkArea = new LinkArea(webUrlLabel.Text.IndexOf(shippingMethod.Url.OriginalString),
                                                     shippingMethod.Url.OriginalString.Length);
            }
            else
            {
                shippingUrlInfoPanel.Visible = false;
            }
        }

        /// <summary>
        /// Occurs when user clicks on shipping Url
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void webUrlLabel_Click(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(shippingUrl))
            {
                SHDocVw.InternetExplorer internetExplorer = new SHDocVw.InternetExplorer();
                object Empty = 0;
                object URL = shippingUrl;

                internetExplorer.Visible = true;
                internetExplorer.Navigate2(ref URL, ref Empty, ref Empty, ref Empty, ref Empty); 
            }
        }

        /// <summary>
        /// Reset the address details again(With default main address will be selected)
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void resetAddressButton_Click(object sender, EventArgs e)
        {
            requestor = RequestorController.Instance.RetrieveRequestor(requestor.Id, false);
            mainAddressRadioButton.Enabled = alternateAddressRadioButton.Enabled = true;
            resetAddressButton.Enabled = false;
            mainAddressRadioButton.Checked = true;
        }

        /// <summary>
        /// It will restrict user to enter other characters apart from Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void shippingChargesTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// Shows delete confirmation dialog
        /// </summary>
        /// <returns></returns>
        private DialogResult ShowCustomShippingAddressDialog()
        {
            if (!isAddressChanged) return DialogResult.Cancel;
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(CustomShippingAddressMessage);
            string titleText = rm.GetString(CustomShippingAddressTitle);            
            string okButtonText = rm.GetString(CustomShippingAddressOkButton);
            string ignoreButtonText = rm.GetString(CustomShippingAddressIgnoreButton);
            string cancelButtonText = rm.GetString(CustomShippingAddressCancelButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(CustomShippingAddressOkButtonToolTip);
            string ignoreButtonToolTip = rm.GetString(CustomShippingAddressIgnoreButtonToolTip);
            string cancelButtonToolTip = rm.GetString(CustomShippingAddressCancelButtonToolTip);

            DialogResult result = ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, ignoreButtonText,
                                                  cancelButtonText, okButtonToolTip, ignoreButtonToolTip,
                                                  cancelButtonToolTip, ROIDialogIcon.Alert);
            if (result == DialogResult.Ignore)
            {
                isUpdateRequestorAddress = true;
            }

            else if (result == DialogResult.OK)
            {
                isUpdateRequestorAddress = false;
            }

            return result;
        }

        /// <summary>
        /// Returns control.
        /// </summary>
        /// <param name="error"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            if (error.ErrorCode == ROIErrorCodes.InvalidMainCity)
            {
                return cityTextBox;
            }
            if (error.ErrorCode == ROIErrorCodes.InvalidMainState)
            {
                return stateTextBox;
            }
         /* if (error.ErrorCode == ROIErrorCodes.InvalidMainZip)
            {
                return zipTextBox;
            }   */        

            return null;
        }

        public void DisableShippingInfo()
        {
            shippingPanel.Enabled = false;
            isEnabled = false;
        }

        // Added for CR#356633 
        /// <summary>
        /// Event occurs when zip textbox is changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void zipTextBox_TextChanged(object sender, EventArgs e)
        {
            
        }

        private void requiredImg_VisibleChanged(object sender, EventArgs e)
        {
            Point PrintablePostn = new Point();
            if (requiredImg.Visible == false)
            {
                PrintablePostn.X = 30;
                PrintablePostn.Y = 76;
                NonPrintableLabel.Location = PrintablePostn;
            }
            if (requiredImg.Visible == true)
            {
                PrintablePostn.X = 43;
                PrintablePostn.Y = 76;
                NonPrintableLabel.Location = PrintablePostn;
            }
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

        public double ShippingCharge
        {
            get { return shippingCharge; }
        }

        private bool noError;
        public bool NoError
        {
            get { return noError; }
        }

        public bool IsUpdateRequestorAddress
        {
            get
            {
                return isUpdateRequestorAddress;
            }
        }

        /// <summary>
        /// Gets non printable combobox control
        /// </summary>
        public ComboBox NonPrinableComboBox
        {
            get
            {
                return nonPrintableComboBox;
            }
        }

        /// <summary>
        /// Retrieves the status of print radio button
        /// </summary>
        public bool IsPrint
        {
            get
            {
                return printRadioButton.Checked;
            }
        }

        /// <summary>
        /// Retreives the status of file radio button
        /// </summary>
        public bool IsFile
        {
            get
            {
                return saveAsRadioButton.Checked;
            }
        }

        /// <summary>
        /// Retrieves the status of fax radio button
        /// </summary>
        public bool IsFax
        {
            get
            {
                return faxRadioButton.Checked;
            }
        }

        /// <summary>
        /// Retrieves the status of email radio button
        /// </summary>
        public bool IsEmail
        {
            get
            {
                return emailRadioButton.Checked;
            }
        }

        public bool IsDisc
        {
            get
            {
                return discRadioButton.Checked;
            }
        }

        private bool isEnabled;
        public bool IsEnabled
        {
            get { return isEnabled; }
        }

        #endregion        

       
      
    }
}
