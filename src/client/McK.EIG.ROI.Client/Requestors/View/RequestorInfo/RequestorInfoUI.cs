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
using System.Collections.ObjectModel;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Controller;

using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Requestors.View.RequestorInfo
{

    /// <summary>
    /// This class holds Requestor information UI
    /// </summary>
    public partial class RequestorInfoUI : ROIBaseUI, IRequestorPageContext, IFooterProvider
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(RequestorInfoUI));

        private const string SelectOption = "  /  /    ";

        private const string NonUniqueRequestorDialogTitle               = "NonUniqueRequestorDialog.Title";
        private const string NonUniqueRequestorDialogOkButton            = "NonUniqueRequestorDialog.OkButton";
        private const string NonUniqueRequestorDialogCancelButton        = "NonUniqueRequestorDialog.CancelButton";
        private const string NonUniqueRequestorDialogOkButtonToolTip     = "NonUniqueRequestorDialog.OkButton";
        private const string NonUniqueRequestorDialogCancelButtonToolTip = "NonUniqueRequestorDialog.CancelButton";

        private const string NonUniqueRequestorDialogMessageKey = "A301";
        private const string DeleteRequestorDialogMessageKey    = "A302";

        private const string DeleteRequestorDialogTitle               = "DeleteRequestorDialog.Title";
        private const string DeleteRequestorDialogOkButton            = "DeleteRequestorDialog.OkButton";
        private const string DeleteRequestorDialogCancelButton        = "DeleteRequestorDialog.CancelButton";
        private const string DeleteRequestorDialogOkButtonToolTip     = "DeleteRequestorDialog.OkButton";
        private const string DeleteRequestorDialogCancelButtonToolTip = "DeleteRequestorDialog.CancelButton";

        private const string CustomDateFormat = "MMMM dd, yyyy";

        private RequestorInfoActionUI requestorInfoActionUI;
        private RequestorDetails requestor;
        //ROI16.0 zipcode
        private System.Collections.Generic.List<CountryCodeDetails> CountryDetails;
            private int prevMainValue;
            private int prevAltValue;
            private bool selectedMainCountry;
            private bool selectedAltCountry;
        private bool defaultCountry;

        private EventHandler dirtyDataHandler;
        private EventHandler saveRequestorHandler;
        private EventHandler cancelRequestorHandler;
        private EventHandler activeHandler;
        private EventHandler inActiveHandler;
        private bool isDirty;

        private bool contactAddressUpdated;

        private Collection<RequestorTypeDetails> requestorTypeDetails;
        
        #endregion

        #region Methods
        /// <summary>
        /// Constructor
        /// </summary>
        public RequestorInfoUI()
        {
            InitializeComponent();

            dirtyDataHandler       = new EventHandler(MarkDirty);
            activeHandler          = new EventHandler(OnActive);
            inActiveHandler        = new EventHandler(OnInActive);
            saveRequestorHandler   = new EventHandler(DefaultSaveRequestorHandler);
            cancelRequestorHandler = new EventHandler(DefaultCancelRequestorHandler);
            selectedMainCountry = false;
            selectedAltCountry = false;
            defaultCountry = false;

            CreateActionButtons();
            SetSsnFormat();

            prefixLabel.Text = (UserData.Instance.EpnPrefix != null) ?
                                  UserData.Instance.EpnPrefix.Trim() : string.Empty;
            toolTip.SetToolTip(prefixLabel, (UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix.Trim() : string.Empty);
        }

        /// <summary>
        /// It creates all action events in the UI
        /// </summary>
        private void CreateActionButtons()
        {
            requestorFooterUI = new RequestorFooterUI(this);
            requestorFooterUI.Dock = DockStyle.Fill;            

            requestorInfoActionUI = new RequestorInfoActionUI();            

            requestorInfoActionUI.SaveButton.Click   += new EventHandler(saveButton_Click);
            requestorInfoActionUI.DeleteButton.Click += new EventHandler(deleteButton_Click);
            requestorInfoActionUI.RevertButton.Click += new EventHandler(revertButton_Click);

            EnableButtons(false);            

            requestorFooterUI.PageActions = requestorInfoActionUI;            
        }

        /// <summary>
        /// Locallizes all controls in the UI
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, paymentRequiredCheckBox);
            SetLabel(rm, letterRequiredCheckBox);
            SetLabel(rm, patientEpnLabel);
            SetLabel(rm, patientDobLabel);
            SetLabel(rm, patientSsnLabel);
            SetLabel(rm, patientMrnLabel);
            SetLabel(rm, patientFacilityLabel);
            SetLabel(rm, reqNameLabel);
            SetLabel(rm, reqLastNameLabel);
            SetLabel(rm, reqFirstNameLabel);
            SetLabel(rm, typeLabel);
            SetLabel(rm, requestorHomePhoneLabel);
            SetLabel(rm, requestorWorkPhoneLabel);
            SetLabel(rm, cellPhoneLabel);
            SetLabel(rm, emailLabel);
            SetLabel(rm, faxLabel);
            SetLabel(rm, contactNameLabel);
            SetLabel(rm, contactPhoneLabel);
            SetLabel(rm, contactEmailLabel);
            SetLabel(rm, faxLabel);
            SetLabel(rm, faxLabel);
            SetLabel(rm, contactInfoGroupBox);
            SetLabel(rm, statusCheckBox);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, paymentRequiredCheckBox);
            SetTooltip(rm, toolTip, letterRequiredCheckBox);
            SetTooltip(rm, toolTip, requestorTypeCombo);
            SetTooltip(rm, toolTip, nameTextBox);
            SetTooltip(rm, toolTip, reqLastNameTextBox);
            SetTooltip(rm, toolTip, reqFirstNameTextBox);
            SetTooltip(rm, toolTip, epnTextBox);
            SetTooltip(rm, toolTip, ssnTextBox);
            SetTooltip(rm, toolTip, mrnTextBox);
            SetTooltip(rm, toolTip, facilityComboBox);            
            SetTooltip(rm, toolTip, dobTextBox);
            SetTooltip(rm, toolTip, homePhoneTextBox);
            SetTooltip(rm, toolTip, cellPhoneTextBox);
            SetTooltip(rm, toolTip, workPhoneTextBox);
            SetTooltip(rm, toolTip, contactNameTextBox);
            SetTooltip(rm, toolTip, contactPhoneTextBox);
            SetTooltip(rm, toolTip, contactEmailTextBox);
            SetTooltip(rm, toolTip, contactPhoneTextBox);
            SetTooltip(rm, toolTip, emailTextBox);
            SetTooltip(rm, toolTip, faxTextBox);
            SetTooltip(rm, toolTip, statusCheckBox);

            mainAddressGroupUI.Localize();
            alternateAddressGroupUI.Localize();
            requestorFooterUI.Localize();
            requestorInfoActionUI.Localize();
            SetDOBNullValue();
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + Pane.GetType().Name;
        }

        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.RequestorNameEmpty         : // fall through;
                case ROIErrorCodes.RequestorTypeNameMaxLength : return nameTextBox;
                case ROIErrorCodes.RequestorFirstNameEmpty    : // fall through;
                case ROIErrorCodes.RequestorFirstNameMaxLength: return reqFirstNameTextBox;
                case ROIErrorCodes.RequestorLastNameEmpty     : // fall through;
                case ROIErrorCodes.RequestorLastNameMaxLength : return reqLastNameTextBox;
                case ROIErrorCodes.RequestorTypeEmpty         : return requestorTypeCombo;
                case ROIErrorCodes.InvalidSsn                 : return ssnTextBox;
                case ROIErrorCodes.InvalidEpn                 :  return epnTextBox;
                case ROIErrorCodes.InvalidMrn                 : return mrnTextBox;
                case ROIErrorCodes.InvalidFacility            : return facilityComboBox;
                case ROIErrorCodes.InvalidHomePhone           : return homePhoneTextBox;
                case ROIErrorCodes.InvalidWorkPhone           : return workPhoneTextBox;
                case ROIErrorCodes.InvalidCellPhone           : return cellPhoneTextBox;
                case ROIErrorCodes.InvalidEmail: return emailTextBox;
                case ROIErrorCodes.InvalidContactPhone        : return contactPhoneTextBox;
                case ROIErrorCodes.InvalidFax                 : return faxTextBox;
                case ROIErrorCodes.InvalidContactName         : return contactNameTextBox;
                case ROIErrorCodes.InvalidContactEmail        : return contactEmailTextBox;
                case ROIErrorCodes.InvalidAltState            : return alternateAddressGroupUI.GetErrorControl(error); 
                case ROIErrorCodes.InvalidAltCity             : return alternateAddressGroupUI.GetErrorControl(error); 
                case ROIErrorCodes.InvalidAltZip              : return alternateAddressGroupUI.GetErrorControl(error);
                case ROIErrorCodes.InvalidMainState           : return mainAddressGroupUI.GetErrorControl(error);
                case ROIErrorCodes.InvalidMainCity            : return mainAddressGroupUI.GetErrorControl(error);
                case ROIErrorCodes.InvalidMainZip             : return mainAddressGroupUI.GetErrorControl(error);
                case ROIErrorCodes.IncorrectDate		      : return dobTextBox;
                case ROIErrorCodes.InvalidDate                : return dobTextBox;
                case ROIErrorCodes.InvalidDateValue           : return dobTextBox;
            }

            return null;
        }

        /// <summary>
        /// Set From Date value
        /// </summary>
        private void SetDOBNullValue()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            dobTextBox.Text = rm.GetString(ROIConstants.DefaultDate);
        }

        /// <summary>
        /// It sets the pane which holds the Requestor information UI
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            requestorFooterUI.SetPane(pane);
            mainAddressGroupUI.SetPane(pane);
            alternateAddressGroupUI.SetPane(pane);

            requestorInfoActionUI.SetPane(pane);

            mainAddressGroupUI.SetExecutionContext(Context);
            alternateAddressGroupUI.SetExecutionContext(Context);
            requestorFooterUI.SetExecutionContext(Context);
            requestorInfoActionUI.SetExecutionContext(Context);
        }

        /// <summary>
        /// It sets the data in controls.
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearData();

            if (data != null)
            {
                requestor = data as RequestorDetails;

                paymentRequiredCheckBox.Checked = requestor.PrepaymentRequired;
                letterRequiredCheckBox.Checked = requestor.LetterRequired;
                PopulateRequestorType();
                requestorTypeCombo.SelectedValue = requestor.Type;
                requestor.TypeName = requestorTypeCombo.Text;
                if (requestor.IsPatientRequestor)
                {
                    reqFirstNameTextBox.Text = requestor.FirstName;
                    reqLastNameTextBox.Text = requestor.LastName;
                }
                else
                {
                    nameTextBox.Text = requestor.Name;
                }
                homePhoneTextBox.Text = requestor.HomePhone;
                workPhoneTextBox.Text = requestor.WorkPhone;
                cellPhoneTextBox.Text = requestor.CellPhone;
                emailTextBox.Text = requestor.Email;
                contactNameTextBox.Text = requestor.ContactName;
                contactPhoneTextBox.Text = requestor.ContactPhone;
                contactEmailTextBox.Text = requestor.ContactEmail;
                statusCheckBox.Checked = !requestor.IsActive;
                faxTextBox.Text = requestor.Fax;
                int i = 0;
                foreach (CountryCodeDetails countryList in CountryDetails)
                {
                    if (requestor.MainAddress.CountrySeq == CountryDetails[i].CountrySeq)
                    {
                        mainAddressGroupUI.RequestorConfigCountrycomboBox.Text = CountryDetails[i].CountryName;
                        requestor.MainAddress.CountryName = CountryDetails[i].CountryName;
                        requestor.MainAddress.NewCountry = selectedMainCountry;
                        requestor.MainAddress.CountrySeq = CountryDetails[i].CountrySeq;
                        break;
                    }
                    //else if (CountryDetails[i].CountrySeq == 272)
                    //{
                    //    mainAddressGroupUI.RequestorConfigCountrycomboBox.Text = CountryDetails[i].CountryName;
                    //    requestor.MainAddress.CountryName = CountryDetails[i].CountryName;
                    //    requestor.MainAddress.NewCountry = selectedMainCountry;
                    //    requestor.MainAddress.CountrySeq = CountryDetails[i].CountrySeq;
                    //    break;
                    //}
                    i++;
                }
                int j = 0;
                //check the null validation for AltAddress
                if (requestor.AltAddress != null)
                {
                    foreach (CountryCodeDetails countryList in CountryDetails)
                    {
                        if (requestor.AltAddress.CountrySeq == CountryDetails[j].CountrySeq)
                        {
                            alternateAddressGroupUI.RequestorConfigCountrycomboBox.Text = CountryDetails[j].CountryName;
                            requestor.AltAddress.CountryName = CountryDetails[j].CountryName;
                            requestor.AltAddress.NewCountry = selectedAltCountry;
                            requestor.AltAddress.CountrySeq = CountryDetails[j].CountrySeq;
                            break;
                        }
                        //else if (CountryDetails[j].CountrySeq == 272)
                        //{
                        //    alternateAddressGroupUI.RequestorConfigCountrycomboBox.Text = CountryDetails[j].CountryName;
                        //    requestor.AltAddress.CountryName = CountryDetails[j].CountryName;
                        //    requestor.AltAddress.NewCountry = selectedAltCountry;
                        //    requestor.AltAddress.CountrySeq = CountryDetails[j].CountrySeq;
                        //    break;
                        //}
                        j++;
                    }
                }
               
                mainAddressGroupUI.SetData(requestor);
                alternateAddressGroupUI.SetData(requestor);

                //requestorTypeCombo.Enabled = false;
                statusCheckBox.Enabled = true;
                requestorInfoActionUI.DeleteButton.Enabled = true;
                requestorInfoActionUI.DeleteButton.Visible = (!requestor.IsAssociated);
                requestorFooterUI.createRequestButton.Enabled = requestor.IsActive;
                
                DisplayPatientFields(requestor);
                DisableControls(requestor.IsActive);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                EnableButtons(false);
                IsDirty = false;
                if (requestor.Id > 0)
                {   
                    toolTip.SetToolTip(requestorInfoActionUI.RevertButton, rm.GetString(requestorInfoActionUI.RevertButton.Name + "." + Pane.GetType().Name));
                }
                else
                {
                    rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    requestorInfoActionUI.RevertButton.Text = rm.GetString("cancelButton", System.Threading.Thread.CurrentThread.CurrentUICulture);

                    rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                    toolTip.SetToolTip(requestorInfoActionUI.RevertButton, rm.GetString(requestorInfoActionUI.RevertButton.Name + "." + Pane.GetType().Name + ".CreateNew"));
                    EnableButtons(true);
                    requestorInfoActionUI.DeleteButton.Enabled = false;
                    requestorFooterUI.CreateRequestButton.Enabled = false;
                    IsDirty = true;
                }


                //If the selected requestor is inactive then
                //disable the menu item "Create Request with Requestor" in Requestor menu
                RequestorEvents.OnRequestorStatusChanged(Pane,
                                                         new ApplicationEventArgs(requestor.IsActive, this));


            }
            else
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                requestorInfoActionUI.RevertButton.Text = rm.GetString("cancelButton", System.Threading.Thread.CurrentThread.CurrentUICulture);
                requestorInfoActionUI.RevertButton.Enabled = true;
                requestorInfoActionUI.DeleteButton.Enabled = false;
                IsDirty = false;
                PopulateRequestorType();
                rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                toolTip.SetToolTip(requestorInfoActionUI.RevertButton, rm.GetString(requestorInfoActionUI.RevertButton.Name + "." + Pane.GetType().Name + ".CreateNew"));
            }
            EnableEvents();
            requestorTypeCombo.Focus();
        }
    
        /// <summary>
        /// It gets data from the UI for save.
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            CheckContactAddressUpdated();
            RequestorDetails requestor = (appendTo == null) ? new RequestorDetails() : appendTo as RequestorDetails;
            requestor.MainAddress = new AddressDetails();
            requestor.AltAddress = new AddressDetails();
            requestor.PrepaymentRequired = paymentRequiredCheckBox.Checked;
            requestor.LetterRequired     = letterRequiredCheckBox.Checked;
            requestor.Type               = Convert.ToInt64(requestorTypeCombo.SelectedValue, 
                                                           System.Threading.Thread.CurrentThread.CurrentUICulture);
            requestor.TypeName           = requestorTypeCombo.Text;

            if (requestor.IsPatientRequestor)
            {
                requestor.FirstName = reqFirstNameTextBox.Text;
                requestor.LastName  = reqLastNameTextBox.Text;
                requestor.Name = requestor.LastName + ", " + requestor.FirstName;
            }
            else
            {
                requestor.Name = nameTextBox.Text;
            }            
            requestor.HomePhone          = homePhoneTextBox.Text;
            requestor.WorkPhone          = workPhoneTextBox.Text;
            requestor.CellPhone          = cellPhoneTextBox.Text;
            requestor.Email              = emailTextBox.Text;
            requestor.Fax                = faxTextBox.Text;
            requestor.ContactName        = contactNameTextBox.Text;
            requestor.ContactPhone       = contactPhoneTextBox.Text;
            requestor.ContactEmail       = contactEmailTextBox.Text;
            requestor.IsActive           = ! statusCheckBox.Checked;
            RequestorTypeDetails requestorTypeDetail = (RequestorTypeDetails)requestorTypeCombo.SelectedItem;
            if (Convert.ToInt64(requestorTypeCombo.SelectedValue, System.Threading.Thread.CurrentThread.CurrentUICulture) < 0)
            {
                requestor.PatientEPN = (epnTextBox.Text.Trim().Length > 0 && UserData.Instance.EpnEnabled)
                                        ? UserData.Instance.EpnPrefix.Trim() + epnTextBox.Text.Trim()
                                        : epnTextBox.Text.Trim();

                requestor.PatientSSN = (ssnTextBox.MaskedTextProvider.AssignedEditPositionCount > 0) ? ssnTextBox.Text.Trim() : string.Empty;
                requestor.PatientMRN = mrnTextBox.Text.Trim();
                requestor.PatientFacilityCode = (facilityComboBox.SelectedIndex == 0) ? null : ((FacilityDetails)(facilityComboBox.SelectedItem)).Code;

                if ((facilityComboBox.SelectedIndex == 0 || facilityComboBox.SelectedItem == null))
                {
                    requestor.IsFreeformFacility = true;
                }
                else
                {
                    requestor.IsFreeformFacility = ((FacilityDetails)(facilityComboBox.SelectedItem)).Type == FacilityType.NonHpf ? true : false;
                }


                if (dobTextBox.Text.Trim() != SelectOption.Trim())
                {
                    requestor.Dob = dobTextBox.Text;
                }
                else if (requestor.PatientDob != null && dobTextBox.Text.Trim() == SelectOption.Trim())
                {
                    requestor.PatientDob = null;
                }
                if (requestorTypeDetail.SalesTax != null)
                {
                    requestor.HasSalesTax = requestorTypeDetail.SalesTax.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes");
                }
            }
            else
            {
                requestor.PatientDob = null;
                requestor.PatientEPN = string.Empty;
                requestor.PatientSSN = string.Empty;
                requestor.PatientMRN = string.Empty;
                requestor.PatientFacilityCode = string.Empty;
                if (requestorTypeDetail.SalesTax != null)
                {
                    requestor.HasSalesTax = requestorTypeDetail.SalesTax.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes");
                }
            }

            mainAddressGroupUI.GetData(requestor);            
            int i = 0;
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                if (mainAddressGroupUI.RequestorConfigCountrycomboBox.Text == CountryDetails[i].CountryName)
                {
                    requestor.MainAddress.CountryCode = CountryDetails[i].CountryCode;
                    requestor.MainAddress.CountrySeq = CountryDetails[i].CountrySeq;
                    break;
                }
                i++;
            }
            alternateAddressGroupUI.GetData(requestor);
            int j = 0;
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                if (alternateAddressGroupUI.RequestorConfigCountrycomboBox.Text  == CountryDetails[j].CountryName)
                {
                    requestor.AltAddress.CountryCode = CountryDetails[j].CountryCode;
                    requestor.AltAddress.CountrySeq = CountryDetails[j].CountrySeq;
                    break;
                }
                j++;
            }
            return requestor;
        }

        /// <summary>
        /// Disables controls if the requestor status is InActive.
        /// </summary>
        /// <param name="enable"></param>
        public void DisableControls(bool enable)
        {
            paymentRequiredCheckBox.Enabled = enable;
            letterRequiredCheckBox.Enabled  = enable;
            nameTextBox.Enabled             = enable;
            reqLastNameTextBox.Enabled      = enable;
            reqFirstNameTextBox.Enabled     = enable;
            requestorTypeCombo.Enabled      = enable;
            if (requestorTypeCombo.Enabled)
            {
                EnableRequestorTypeCombo();
            }
            mainAddressGroupUI.Enabled      = enable;
            alternateAddressGroupUI.Enabled = enable;
            contactInfoGroupBox.Enabled     = enable;

            bool isPatientTypeSelected = ((requestorTypeCombo.SelectedItem as RequestorTypeDetails).Id == -1);
            epnPanel.Enabled = (enable && isPatientTypeSelected && UserData.Instance.EpnEnabled);
            patientPanel.Enabled = (isPatientTypeSelected && enable);
        }


        /// <summary>
        /// It enables the control events.
        /// </summary>
        public void EnableEvents()
        {
            requestorTypeCombo.SelectedIndexChanged += dirtyDataHandler;
            paymentRequiredCheckBox.CheckedChanged  += dirtyDataHandler;
            letterRequiredCheckBox.CheckedChanged   += dirtyDataHandler;
            nameTextBox.TextChanged                 += dirtyDataHandler;
            reqLastNameTextBox.TextChanged          += dirtyDataHandler;
            reqFirstNameTextBox.TextChanged         += dirtyDataHandler;
            epnTextBox.TextChanged                  += dirtyDataHandler;
            dobTextBox.TextChanged                  += dirtyDataHandler;
            ssnTextBox.TextChanged                  += dirtyDataHandler;
            mrnTextBox.TextChanged                  += dirtyDataHandler;
            facilityComboBox.SelectedIndexChanged   += dirtyDataHandler;
            homePhoneTextBox.TextChanged            += dirtyDataHandler;
            workPhoneTextBox.TextChanged            += dirtyDataHandler;
            cellPhoneTextBox.TextChanged            += dirtyDataHandler;
            emailTextBox.TextChanged                += dirtyDataHandler;
            faxTextBox.TextChanged                  += dirtyDataHandler;
            contactNameTextBox.TextChanged          += dirtyDataHandler;
            contactPhoneTextBox.TextChanged         += dirtyDataHandler;
            contactEmailTextBox.TextChanged         += dirtyDataHandler;
            statusCheckBox.CheckedChanged           += dirtyDataHandler;

            ssnTextBox.Enter += activeHandler;
            ssnTextBox.Leave += inActiveHandler;

            alternateAddressGroupUI.EnableEvents();
            mainAddressGroupUI.EnableEvents();
        }

        /// <summary>
        /// It disables the control events.
        /// </summary>
        private void DisableEvents()
        {
            requestorTypeCombo.SelectedIndexChanged -= dirtyDataHandler;
            paymentRequiredCheckBox.CheckedChanged  -= dirtyDataHandler;
            letterRequiredCheckBox.CheckedChanged   -= dirtyDataHandler;
            nameTextBox.TextChanged                 -= dirtyDataHandler;
            reqLastNameTextBox.TextChanged          -= dirtyDataHandler;
            reqFirstNameTextBox.TextChanged         -= dirtyDataHandler;
            epnTextBox.TextChanged                  -= dirtyDataHandler;
            dobTextBox.TextChanged                  -= dirtyDataHandler;
            ssnTextBox.TextChanged                  -= dirtyDataHandler;
            mrnTextBox.TextChanged                  -= dirtyDataHandler;
            facilityComboBox.SelectedIndexChanged   -= dirtyDataHandler;
            homePhoneTextBox.TextChanged            -= dirtyDataHandler;
            workPhoneTextBox.TextChanged            -= dirtyDataHandler;
            cellPhoneTextBox.TextChanged            -= dirtyDataHandler;
            emailTextBox.TextChanged                -= dirtyDataHandler;
            faxTextBox.TextChanged                  -= dirtyDataHandler;
            contactNameTextBox.TextChanged          -= dirtyDataHandler;
            contactPhoneTextBox.TextChanged         -= dirtyDataHandler;
            contactEmailTextBox.TextChanged         -= dirtyDataHandler;
            statusCheckBox.CheckedChanged           -= dirtyDataHandler;

            ssnTextBox.Enter -= activeHandler;
            ssnTextBox.Leave -= inActiveHandler;

            alternateAddressGroupUI.DisableEvents();
            mainAddressGroupUI.DisableEvents();
        }


        /// <summary>
        /// It clears the controls data.
        /// </summary>
        private void ClearData()
        {
            errorProvider.Clear();

            nameTextBox.Text = String.Empty;
            reqFirstNameTextBox.Text = string.Empty;
            reqLastNameTextBox.Text = string.Empty;
            epnTextBox.Text = String.Empty;
            SetDOBNullValue();
            ssnTextBox.Clear();
            mrnTextBox.Text                 = String.Empty;
            facilityComboBox.SelectedIndex  = 0;
            homePhoneTextBox.Text           = String.Empty;
            workPhoneTextBox.Text           = String.Empty;
            cellPhoneTextBox.Text           = String.Empty;
            emailTextBox.Text               = String.Empty;
            faxTextBox.Text                 = String.Empty;
            contactNameTextBox.Text         = String.Empty;
            contactPhoneTextBox.Text        = String.Empty;
            contactEmailTextBox.Text        = String.Empty;
            paymentRequiredCheckBox.Checked = false;
            letterRequiredCheckBox.Checked = false;

            //requestorTypeCombo.SelectedIndex = 0;
            mainAddressGroupUI.ClearData();
            alternateAddressGroupUI.ClearData();
            isDirty = false;

            patientPanel.Enabled = false;

            if (UserData.Instance.EpnEnabled && UserData.Instance.EpnPrefix.Length > 0)
            {
                epnTextBox.MaxLength = Convert.ToInt32(ROIConstants.EpnLengthWhenEpnConfigure, System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            SetDOBNullValue();
        }

        private void CheckContactAddressUpdated()
        {
            if (requestor != null)
            {
                requestor.Normalize();
                contactAddressUpdated = requestor.HomePhone != homePhoneTextBox.Text || requestor.WorkPhone != workPhoneTextBox.Text || requestor.CellPhone.ToString() != cellPhoneTextBox.Text ||
                                            requestor.Email != emailTextBox.Text || requestor.Fax != faxTextBox.Text || requestor.ContactPhone != contactPhoneTextBox.Text || requestor.ContactName != contactNameTextBox.Text ||
                                            requestor.ContactEmail != contactEmailTextBox.Text || mainAddressGroupUI.IsDirty || alternateAddressGroupUI.IsDirty;
            }
        }

        /// <summary>
        /// It makes page as dirty if user do any changes
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        internal void MarkDirty(object sender, EventArgs e)
        {
            bool isPatientTypeSelected = ((requestorTypeCombo.SelectedItem as RequestorTypeDetails).Id == -1);
            IsDirty = true;
            if ((requestorTypeCombo.SelectedIndex == 0)
                || ((isPatientTypeSelected) ? (string.IsNullOrEmpty(reqFirstNameTextBox.Text.Trim()) ||
                string.IsNullOrEmpty(reqLastNameTextBox.Text.Trim())) : string.IsNullOrEmpty(nameTextBox.Text.Trim())))
            {
                requestorInfoActionUI.SaveButton.Enabled = false;
            }
            else
            {
                EnableButtons(true);
            }
            //DisableEvents();
        }

        /// <summary>
        /// It enables the buttons
        /// </summary>
        /// <param name="enable"></param>
        private void EnableButtons(bool enable)
        {
            requestorInfoActionUI.SaveButton.Enabled = enable;
            requestorInfoActionUI.RevertButton.Enabled = enable;
        }

        /// <summary>
        /// Occurs when the control becomes active control of the view
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnActive(object sender, EventArgs e)
        {
            ROIViewUtility.RemoveMask(ssnTextBox);
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
                ROIViewUtility.ApplyMask(ssnTextBox);
            }
        }

        /// <summary>
        /// It prepopulates requestor types and requestor status data.
        /// </summary>
        /// <param name="requestorTypes"></param>
        internal void PrePopulate(Collection<RequestorTypeDetails> requestorTypes)
        {
    
            DisableEvents();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            RequestorTypeDetails forSelect = new RequestorTypeDetails();
            forSelect.Id = 0;
            forSelect.Name = rm.GetString("selectReqType." + Pane.GetType().Name);
            requestorTypes.Insert(0, forSelect);
            requestorTypeDetails = requestorTypes;
            //requestorTypeCombo.DataSource = requestorTypes;
            requestorTypeCombo.DisplayMember = "Name";
            requestorTypeCombo.ValueMember = "Id";

            PopulateFacilites();
            CountryDetailsList();
            
            EnableEvents();
        }

		//CR# 380843
        private void PopulateRequestorType()
        {
            if ((requestor == null) || (!ROIConstants.MigrationRequest.Equals(requestor.TypeName)))
            {
                foreach (RequestorTypeDetails requestorType in requestorTypeDetails)
                {
                    if (ROIConstants.MigrationRequest.Equals(requestorType.Name))
                    {
                        requestorTypeDetails.Remove(requestorType);
                        break;
                    }
                }
            }
            requestorTypeCombo.DataSource = requestorTypeDetails;
        }

        private void CountryDetailsList()
        {
            CountryDetails = new System.Collections.Generic.List<CountryCodeDetails>();
            CountryDetails = ROIController.Instance.RetrieveCountryList();
            //int i = 0;
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                mainAddressGroupUI.RequestorConfigCountrycomboBox.Items.Add(countryList.CountryName);
                alternateAddressGroupUI.RequestorConfigCountrycomboBox.Items.Add(countryList.CountryName);
                if (countryList.DefaultCountry)
                {
                    mainAddressGroupUI.IsDefaultSel = alternateAddressGroupUI.IsDefaultSel = true;
                    mainAddressGroupUI.SetReqComboText = countryList.CountryName;
                    alternateAddressGroupUI.SetReqComboText = countryList.CountryName;
                    mainAddressGroupUI.IsDefaultSel = alternateAddressGroupUI.IsDefaultSel = false;
                    //break;
                }
                //else if (CountryDetails[i].CountryCode == "US")
                //{
                //    mainAddressGroupUI.IsDefaultSel = alternateAddressGroupUI.IsDefaultSel = true;
                //    mainAddressGroupUI.SetReqComboText = CountryDetails[i].CountryName;
                //    alternateAddressGroupUI.SetReqComboText = CountryDetails[i].CountryName;
                //    mainAddressGroupUI.IsDefaultSel = alternateAddressGroupUI.IsDefaultSel = false;
                //    break;                
                //}
            }
               
        }

        private void PopulateFacilites()
        {
            foreach (FacilityDetails fac in UserData.Instance.SortedFacilities)
            {
                facilityComboBox.Items.Add(fac);
            }

            facilityComboBox.Items.Insert(0, new FacilityDetails("Select Facility", "Select Facility", FacilityType.NonHpf));
            facilityComboBox.DisplayMember = "Name";
            facilityComboBox.ValueMember = "Code";
            facilityComboBox.SelectedIndex = 0;
        }

        internal bool Save(object sender, EventArgs e)
        {
            errorProvider.Clear();
            ROIViewUtility.MarkBusy(true);
            try
            {
                SaveRequestorHandler(sender, e);

                IsDirty = false;
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

        /// <summary>
        /// Saves the data. Checks for non unique existing requestor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            requestorInfoActionUI.SaveButton.Focus();
            Save(sender, e);
        }

        private void DefaultSaveRequestorHandler(object sender, EventArgs e)
        {
            RequestorDetails requestorDetails = SaveRequestor();
            if (requestorDetails != null)
            {
                EnableNavigationLinks(requestorDetails);
                SetData(requestorDetails);
            }
        }

        public RequestorDetails SaveRequestor()
        {
            RequestorDetails requestorDetails = GetData(ROIViewUtility.DeepClone(requestor)) as RequestorDetails;
            if (RequestorController.Instance.CheckDuplicateName(requestorDetails))
            {
                if (!DisplayNonUniqueRequestorDialog())
                {
                    if (requestorDetails.IsPatientRequestor)
                    {
                        reqLastNameTextBox.Focus();
                    }
                    else
                    {
                        nameTextBox.Focus();
                    }
                    return null;
                }
                nonUniqueRequestorIcon.Visible = true;
            }
            else
            {
                nonUniqueRequestorIcon.Visible = false;
            }

            if (requestorDetails.Id == 0)
            {
                requestorDetails.Id = RequestorController.Instance.CreateRequestor(requestorDetails);
            }
            else
            {
                string requestorName = requestorDetails.TypeName;
                requestorDetails = RequestorController.Instance.UpdateRequestor(requestorDetails);
                requestorDetails.TypeName = requestorName;
                RequestorEvents.OnRequestorUpdated(Pane, new ApplicationEventArgs(requestorDetails, null));
            }

            (Pane.ParentPane as RequestorInfoEditor).RequestorInfo = requestorDetails as RequestorDetails;

            isDirty = false;
            requestorTypeCombo.Enabled = false;
            requestorDetails.TypeName = requestorTypeCombo.Text;
            return requestorDetails;
        }

        private void EnableNavigationLinks(RequestorDetails requestorDetails)
        {
            if (requestorDetails != null)
            {
                RequestorEvents.OnRequestorCreated(this, null);
            }
        }

        /// <summary>
        ///  Deletes the selected Requestor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void deleteButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            if (ShowConfirmDeleteRequestorDialog() && !CheckRequestorHasRequest())
            {
                DisableEvents();
                ROIViewUtility.MarkBusy(true);
                try
                {
                    DeleteEntity(requestor);
                    IsDirty = false;
                    ApplicationEventArgs ae = new ApplicationEventArgs(requestor, this);
                    RequestorEvents.OnRequestorDeleted(Pane, ae);
                    EnableEvents();
                }
                catch (ROIException cause)
                {
                    log.FunctionFailure(cause);
                    ROIViewUtility.Handle(Context, cause);
                }
                finally
                {
                    ROIViewUtility.MarkBusy(false);
                    log.ExitFunction();
                }
            }
        }

        /// <summary>
        /// Delete a patient 
        /// </summary>
        private static void DeleteEntity(object data)
        {
            RequestorDetails requestorToDelete = (RequestorDetails)data;
            RequestorController.Instance.DeleteRequestor(requestorToDelete.Id);
        }

         /// <summary>
        /// Show the confirmation dialog
        /// </summary>
        /// <returns></returns>
        private bool ShowConfirmDeleteRequestorDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString(DeleteRequestorDialogMessageKey);

            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText        = rm.GetString(DeleteRequestorDialogTitle);
            string okButtonText     = rm.GetString(DeleteRequestorDialogOkButton);
            string cancelButtonText = rm.GetString(DeleteRequestorDialogCancelButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip     = rm.GetString(DeleteRequestorDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(DeleteRequestorDialogCancelButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Shows the dialog when user has confirmed to delete a patient 
        /// but the system has detected that the patient has since been included on a request
        /// </summary>
        /// <returns></returns>
        private bool CheckRequestorHasRequest()
        {

            try
            {
                ROIViewUtility.MarkBusy(true);
                requestor = RequestorController.Instance.RetrieveRequestor(requestor.Id, false);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            if (requestor.IsAssociated)
            {
                return ShowNonDeletedRequestorDialog();
            }

            return false;
        }

        /// <summary>
        /// Shows the dialog when user has confirmed to delete a requestor 
        /// but the system has detected that the patient has since been included on a request
        /// </summary>
        /// <returns></returns>
        private bool ShowNonDeletedRequestorDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText   = rm.GetString("NonDeletedRequestorDialog.Title");
            string okButtonText= rm.GetString("NonDeletedRequestorDialog.OkButton");
            string messageText = rm.GetString("NonDeletedRequestorDialog.Message");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString("NonDeletedRequestorDialog.OkButton");

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Error);

            return true;
        }

        /// <summary>
        /// Discard any changes since last save and revert fields to the last saved state.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DefaultCancelRequestorHandler(object sender, EventArgs e)
        {
            if (requestor == null || requestor.Id == 0)
            {
                RequestorEvents.OnRequestorDeleted(Pane, new ApplicationEventArgs(null, this));
            }
            else
            {
                RevertRequestorInfo();
            }
        }

        public void RevertRequestorInfo()
        {
            SetData(requestor);
            EnableButtons(false);
        }

        /// <summary>
        /// Discard any changes since last save and revert fields to the last saved state.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void revertButton_Click(object sender, EventArgs e)
        {
            CancelRequestorHandler(sender, e);
            IsDirty = false;
            requestorTypeCombo.Focus();
        }

        /// <summary>
        /// It displays non unique requestor dialog.
        /// </summary>
        /// <returns></returns>
        private bool DisplayNonUniqueRequestorDialog()
        {
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString(NonUniqueRequestorDialogMessageKey);

            rm                      = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText        = rm.GetString(NonUniqueRequestorDialogTitle);
            string okButtonText     = rm.GetString(NonUniqueRequestorDialogOkButton);
            string cancelButtonText = rm.GetString(NonUniqueRequestorDialogCancelButton);

            rm                         = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip     = rm.GetString(NonUniqueRequestorDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(NonUniqueRequestorDialogCancelButtonToolTip);

            return (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Error));
        }


        /// <summary>
        ///  If requestor type is patient then patient details will be enabled.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void requestorTypeCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (requestorTypeCombo.Text == "Patient")
            {
                DisplayPatientFields(requestor);
                patientReqPanel.Visible = true;
                nonPatientReqPanel.Visible = false;
            }
            else
            {
                DisplayPatientFields(null);
                nonPatientReqPanel.Visible = true;
                patientReqPanel.Visible = false;
            }
        }

        private void DisplayPatientFields(RequestorDetails requestor)
        {
            bool isPatientTypeSelected = ((requestorTypeCombo.SelectedItem as RequestorTypeDetails).Id == -1);
            
            epnPanel.Visible = (isPatientTypeSelected && UserData.Instance.EpnEnabled);
            epnPanel.Enabled = epnPanel.Visible;
            patientPanel.Enabled = isPatientTypeSelected;
            SetDOBNullValue();

            if (isPatientTypeSelected && requestor != null)
            {
                prefixLabel.Text = (UserData.Instance.EpnPrefix != null) ?
                                    UserData.Instance.EpnPrefix.Trim() : string.Empty;

                ssnTextBox.Text = requestor.PatientSSN;
                mrnTextBox.Text = requestor.PatientMRN;               

                if (String.IsNullOrEmpty(requestor.PatientFacilityCode))
                {
                    facilityComboBox.SelectedIndex = 0;
                }
                else
                {
                    FacilityDetails facility = FacilityDetails.GetFacility(requestor.PatientFacilityCode, requestor.PatientFacilityType);

                    if (facility != null)
                    {
                        if (facilityComboBox.Items.Contains(facility))
                        {
                            facilityComboBox.SelectedItem = facility;
                        }
                    }
                }

                if (UserData.Instance.IsSSNMasked)
                {
                    ROIViewUtility.ApplyMask(ssnTextBox);
                }

                if (requestor.PatientEPN != null)
                {
                    if (!string.IsNullOrEmpty(UserData.Instance.EpnPrefix) &&
                         UserData.Instance.EpnPrefix.Trim().Length > 0 &&
                         requestor.PatientEPN.StartsWith(UserData.Instance.EpnPrefix.Trim(), StringComparison.CurrentCultureIgnoreCase))
                    {
                        epnTextBox.Text = requestor.PatientEPN.Substring(UserData.Instance.EpnPrefix.Length);
                    }
                    else
                    {
                        epnTextBox.Text = requestor.PatientEPN;
                    }
                }

                if (requestor.PatientDob.HasValue)
                {
                    dobTextBox.Text = requestor.PatientDob.Value.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
            }
            else
            {
                epnTextBox.Text = string.Empty;
                ssnTextBox.Text = string.Empty;
                mrnTextBox.Text = string.Empty;
                if (facilityComboBox.Items.Count > 0)
                {
                    facilityComboBox.SelectedIndex = 0;
                }
            }            
        }

        public override void SetAcceptButton()
        {
            if (this.ParentForm != null)
            {
                this.ParentForm.AcceptButton = requestorInfoActionUI.SaveButton;
                if (requestor != null)
                {
                    requestorInfoActionUI.DeleteButton.Visible = (!requestor.IsAssociated);
                }
            }
        }

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
                PopulateFacilites();
            }
        }

        private void SetSsnFormat()
        {
            ssnTextBox.Mask = "999-99-9999";
            ssnTextBox.PromptChar = 'x';
            ssnTextBox.TextMaskFormat = MaskFormat.IncludePromptAndLiterals;
            ssnTextBox.AllowPromptAsInput = false;
        }

        private void EnableRequestorTypeCombo()
        {
            if (ROIConstants.MigrationRequest.Equals(requestorTypeCombo.Text))
            {
                requestorTypeCombo.Enabled = false;
            }
        }

        #endregion

        #region Security Rights

        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, false))
            {   
                requestorFooterUI.Enabled = false;
                this.Enabled = false;
            }

            HideCreateRequestButton = !IsAllowed(ROISecurityRights.ROICreateRequest);
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
                if (!isDirty)
                {
                    EnableEvents();
                }
            }
        }

        /// <summary>
        /// Sets the value to true if requestor's contact or address information is changed
        /// </summary>
        
        public bool ContactAddressUpdated
        {
            get { return contactAddressUpdated; }
            set { contactAddressUpdated = value; }
        }

        public RequestorDetails RequestorInfo
        {
            get { return requestor; }
            set { requestor = value; }
        }

        public RequestorFooterUI Footer
        {
            get { return requestorFooterUI; }
        }

        #endregion  

        #region Event wiring properties

        public EventHandler SaveRequestorHandler
        {
            get { return saveRequestorHandler; }
            set { saveRequestorHandler = value; }
        }

        public EventHandler CancelRequestorHandler
        {
            get { return cancelRequestorHandler; }
            set { cancelRequestorHandler = value; }
        }

        public EventHandler DirtyDataHandler
        {
            get { return dirtyDataHandler; }
            set { dirtyDataHandler = value; }
        }

        public bool HideCreateRequestButton
        {
            get { return requestorFooterUI.CreateRequestButton.Visible; }
            set { requestorFooterUI.CreateRequestButton.Visible = !value; }
        }

        public bool HideDeleteRequestorButton
        {
            get { return requestorInfoActionUI.DeleteButton.Visible; }
            set { requestorInfoActionUI.DeleteButton.Visible = !value; }
        }

        public Color FooterPanelBackColor
        {
            get { return requestorFooterUI.FooterPanel.BackColor; }
            set { requestorFooterUI.FooterPanel.BackColor = value; }
        }

        public bool HideFooter
        {
            get { return requestorFooterUI.FooterPanel.Visible; }
            set 
            { 
                requestorFooterUI.FooterPanel.Visible = !value;
                requestorFooterUI.BackColor = Color.White;
            }
        }

        public CheckBox InactiveCheckBox
        {
            get { return statusCheckBox; }
        }

        public string RequestorTypeName
        {
            get { return (requestorTypeCombo.SelectedItem as RequestorTypeDetails).Name; }
        }

        public bool IsSaveButtonEnabled
        {
            get { return requestorInfoActionUI.SaveButton.Enabled; }
        }

        public Panel InfoPanel
        {
            get { return infoPanel; }
        }

        public ComboBox RequestorTypeCombo
        {
            get { return requestorTypeCombo; }
        }

        #endregion

        #region IRequestorPageContent Members

        public RequestorDetails Requestor
        {
            get { return requestor; }
        }

        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.RequestorInformation, e));
        }
        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return requestorFooterUI;
        }

        #endregion
    }
  
}
