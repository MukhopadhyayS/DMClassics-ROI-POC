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

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Controller;

using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.View;
using McK.EIG.ROI.Client.Patient.View.FindPatient;

namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    public partial class PatientInfoUI : ROIBaseUI, IFooterProvider, IPatientPageContext
    {
        private Log log = LogFactory.GetLogger(typeof(PatientInfoUI));        
        
        #region Fields

        private const string NonUniquePatientDialogTitle               = "NonUniquePatientDialog.Title";
        private const string NonUniquePatientDialogOkButton            = "NonUniquePatientDialog.OkButton";
        private const string NonUniquePatientDialogOkButtonToolTip     = "NonUniquePatientDialog.OkButton";
        private const string NonUniquePatientDialogCancelButtonToolTip = "NonUniquePatientDialog.CancelButton";
        private const string NonUniquePatientDialogMessageKey          = "A201";
        
        private const string DeletePatientDialogMessageKey          = "A202";
        private const string DeletePatientDialogTitle               = "DeletePatientDialog.Title";
        private const string DeletePatientDialogOkButton            = "DeletePatientDialog.OkButton";
        private const string DeletePatientDialogOkButtonToolTip     = "DeletePatientDialog.OkButton";
        private const string DeletePatientDialogCancelButtonToolTip = "DeletePatientDialog.CancelButton";

        private PatientInfoActionUI patientInfoActionUI;
        private PatientsFooterUI patientsFooterUI;
        private IPersonalInfoUI personalInfoUI;

        private EventHandler dirtyDataHandler;
        private EventHandler savePatientHandler;
        private EventHandler cancelPatientHandler;
        //ROI16.0 zipcode
        private System.Collections.Generic.List<CountryCodeDetails> CountryDetails;
        private int prevValue;
        private bool selectedCountry;
        private bool defaultCountry;

        private PatientDetails patient;

        private bool isDirty;

        #endregion

        #region Methods

        public PatientInfoUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
            selectedCountry = false;
            defaultCountry = false;
            CreateActionButtons();
        }

        private void CreateActionButtons()
        {
            patientsFooterUI = new PatientsFooterUI(this);
            patientsFooterUI.Dock = DockStyle.Fill;
           
            patientInfoActionUI = new PatientInfoActionUI();

            patientInfoActionUI.SaveButton.Click   += new EventHandler(saveButton_Click);
            patientInfoActionUI.DeleteButton.Click += new EventHandler(deleteButton_Click);
            patientInfoActionUI.RevertButton.Click += new EventHandler(revertButton_Click);
            savePatientHandler = new EventHandler(DefaultSavePatientHandler);
            cancelPatientHandler = new EventHandler(DefaultCancelPatientHandler);

            patientInfoActionUI.SaveButton.Enabled   = false;
            patientInfoActionUI.RevertButton.Enabled = false;
            patientInfoActionUI.DeleteButton.Enabled = false;
            
            patientsFooterUI.PageActions = patientInfoActionUI;
        }

        public void AddPersonalInfoPanel(bool isHpf)
        {
            personalInfoPanel.Controls.Clear();
            if (isHpf)
            {
                personalInfoUI = new PersonalInfoHpfUI();
            }
            else
            {
                personalInfoUI = new PersonalInfoNonHpfUI();
            }
            personalInfoPanel.Controls.Add((Control)personalInfoUI);
        }

        /// <summary>
        /// Gets the PatientDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            PatientDetails patientInfo = (appendTo == null) ? new PatientDetails() 
                                          : appendTo as PatientDetails;

            patientInfo = (PatientDetails)personalInfoUI.GetData(patientInfo);
            
            AddressDetails addressDetails = new AddressDetails();
            addressDetails.Address1       = address1TextBox.Text;
            addressDetails.Address2       = address2TextBox.Text;
            addressDetails.Address3       = address3TextBox.Text;
            addressDetails.City           = cityTextBox.Text;
            addressDetails.State          = stateTextBox.Text;
            addressDetails.PostalCode     = zipTextBox.Text;
            addressDetails.CountryName = patientcountryCodeComboBox.Text;
            addressDetails.NewCountry = selectedCountry;
            int i = 0;
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                if (patientcountryCodeComboBox.Text == CountryDetails[i].CountryName)
                {
                    addressDetails.CountryCode = CountryDetails[i].CountryCode;
                    addressDetails.CountrySeq = CountryDetails[i].CountrySeq;
                    break;
                }
                i++;
            }
                    
            patientInfo.Address           = addressDetails;
           
            patientInfo.HomePhone = homePhoneTextBox.Text;
            patientInfo.WorkPhone = workPhoneTextBox.Text;

            return patientInfo;
        }
       
        /// <summary>
        ///  This method is used to enable(subscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            
            address1TextBox.TextChanged     += dirtyDataHandler;
            address2TextBox.TextChanged     += dirtyDataHandler;
            address3TextBox.TextChanged     += dirtyDataHandler;
            cityTextBox.TextChanged         += dirtyDataHandler;
            stateTextBox.TextChanged        += dirtyDataHandler;
            zipTextBox.TextChanged          += dirtyDataHandler;

            patientcountryCodeComboBox.SelectedIndexChanged += dirtyDataHandler;
            
            //Added for CR# 356633
            zipTextBox.TextChanged += new EventHandler(zipTextBox_TextChanged);

            homePhoneTextBox.TextChanged += dirtyDataHandler;
            workPhoneTextBox.TextChanged += dirtyDataHandler;

            if (personalInfoUI != null)
            {
                personalInfoUI.EnableEvents();
            }
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the PaymentMethodODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            address1TextBox.TextChanged     -= dirtyDataHandler;
            address2TextBox.TextChanged     -= dirtyDataHandler;
            address3TextBox.TextChanged     -= dirtyDataHandler;
            cityTextBox.TextChanged         -= dirtyDataHandler;
            stateTextBox.TextChanged        -= dirtyDataHandler;
            zipTextBox.TextChanged          -= dirtyDataHandler;
            
            //Added for CR# 356633
            zipTextBox.TextChanged -= new EventHandler(zipTextBox_TextChanged);

            homePhoneTextBox.TextChanged -= dirtyDataHandler;
            workPhoneTextBox.TextChanged -= dirtyDataHandler;

            personalInfoUI.DisableEvents();
        }

        private void EnableButtons(bool enable)
        {
            patientInfoActionUI.SaveButton.Enabled = enable;
            patientInfoActionUI.RevertButton.Enabled = enable;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            personalInfoUI.ClearData();

            address1TextBox.Text    = string.Empty;
            address2TextBox.Text    = string.Empty;
            address3TextBox.Text    = string.Empty;
            cityTextBox.Text        = string.Empty;
            stateTextBox.Text       = string.Empty;
            zipTextBox.Text         = string.Empty;
            homePhoneTextBox.Text   = string.Empty;
            workPhoneTextBox.Text   = string.Empty;                    

            errorProvider.Clear();
        }

        public override Control GetErrorControl(ExceptionData error)
        {  
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.PatientFirstNameEmpty:
                case ROIErrorCodes.PatientLastNameEmpty:
                case ROIErrorCodes.PatientFirstNameMaxLength:
                case ROIErrorCodes.PatientLastNameMaxLength:
                case ROIErrorCodes.InvalidFirstName:
                case ROIErrorCodes.InvalidLastName:
                case ROIErrorCodes.InvalidSsn: 
                case ROIErrorCodes.InvalidEpn:
                case ROIErrorCodes.IncorrectDate:
                case ROIErrorCodes.InvalidDate:
                case ROIErrorCodes.InvalidSqlDate:
                case ROIErrorCodes.InvalidDateValue:
                case ROIErrorCodes.InvalidMrn: return personalInfoUI.GetErrorControl(error);
                case ROIErrorCodes.InvalidFacility: return personalInfoUI.GetErrorControl(error);

                case ROIErrorCodes.InvalidMainCity : return cityTextBox;
                case ROIErrorCodes.InvalidMainState: return stateTextBox;
                case ROIErrorCodes.InvalidMainZip  : return zipTextBox;
                case ROIErrorCodes.InvalidHomePhone: return homePhoneTextBox;
                case ROIErrorCodes.InvalidWorkPhone: return workPhoneTextBox;                

            }
            return null;
        }
               

        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            personalInfoUI.SetExecutionContext(Context);
            personalInfoUI.SetPane(Pane);

            patientsFooterUI.SetExecutionContext(Context);
            patientsFooterUI.SetPane(Pane);

            patientInfoActionUI.SetExecutionContext(Context);
            patientInfoActionUI.SetPane(Pane);
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></p
        /// aram>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();
            
            patient = data as PatientDetails;
            if (patient != null)
            {
                personalInfoUI.SetData(patient);

                if (patient.Address != null)
                {
                    AddressDetails address = patient.Address;

                    address1TextBox.Text = address.Address1;
                    address2TextBox.Text = address.Address2;
                    address3TextBox.Text = address.Address3;
                    cityTextBox.Text = address.City;
                    stateTextBox.Text = address.State;
                    zipTextBox.Text = address.PostalCode;                                     
                    int i = 0;
                    foreach (CountryCodeDetails countryList in CountryDetails)
                    {
                        if (address.CountryCode == CountryDetails[i].CountryCode)
                        {
                            patientcountryCodeComboBox.Text= CountryDetails[i].CountryName;                            
                            break;
                        }
                        //else if (CountryDetails[i].CountryCode == "US")
                        //{
                        //    patientcountryCodeComboBox.Text = CountryDetails[i].CountryName;                            
                        //    break;
                        //}
                        i++;
                    }
                    isDirty = false;                 
                }

                homePhoneTextBox.Text = patient.HomePhone;
                workPhoneTextBox.Text = patient.WorkPhone;

                if (patient.IsHpf)
                {
                    personalInfoPanel.Enabled = false;
                    contactGroupBox.Enabled = false;
                    addressGroupBox.Enabled = false;
                    patientInfoActionUI.SaveButton.Enabled = false;
                    patientInfoActionUI.RevertButton.Enabled = false;
                }
                EnableButtons(false);
                patientInfoActionUI.DeleteButton.Enabled = true;
                patientInfoActionUI.DeleteButton.Visible = !(patient.HasRequests || patient.IsHpf);

                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                toolTip.SetToolTip(patientInfoActionUI.RevertButton, rm.GetString(patientInfoActionUI.RevertButton.Name + "." + Pane.GetType().Name));

                EnableRequestButtons(true);
                if (patient.IsHpf)
                {
                    patientsFooterUI.PatientRequestButton.Focus();
                }
            }
            else
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                patientInfoActionUI.RevertButton.Text = rm.GetString("cancelButton", System.Threading.Thread.CurrentThread.CurrentUICulture);
                patientInfoActionUI.RevertButton.Enabled = true;
                patientInfoActionUI.DeleteButton.Enabled = false;
                
                rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                toolTip.SetToolTip(patientInfoActionUI.RevertButton, rm.GetString(patientInfoActionUI.RevertButton.Name + "." + Pane.GetType().Name + ".CreateNew"));
                EnableRequestButtons(false);
            }
            EnableEvents();
        }

        /// <summary>
        /// Set the .
        /// </summary>
        /// <param name="data"></param>
        public void PrePopulate()
        {
            DisableEvents();
            personalInfoUI.PrePopulate();
            CountryDetailsList();
            EnableEvents();
        }

        private void CountryDetailsList()
        {
            CountryDetails = new System.Collections.Generic.List<CountryCodeDetails>();
            CountryDetails = ROIController.Instance.RetrieveCountryList();
            int i = 0;
            foreach (CountryCodeDetails countryList in CountryDetails)
            {
                patientcountryCodeComboBox.Items.Add(CountryDetails[i].CountryName);
                if (CountryDetails[i].DefaultCountry)
                {
                    defaultCountry = true;
                    patientcountryCodeComboBox.Text = CountryDetails[i].CountryName;
                    defaultCountry = false;
                    //break;
                }
                //else if (CountryDetails[i].CountryCode == "US")
                //{
                //    defaultCountry = true;
                //    patientcountryCodeComboBox.Text = CountryDetails[i].CountryName;
                //    defaultCountry = false;
                //    break;                 
                //}
                ++i;
            }
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

        /// <summary>
        /// Localize
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, address1Label);
            SetLabel(rm, address2Label);
            SetLabel(rm, address3Label);
            SetLabel(rm, cityLabel);
            SetLabel(rm, stateLabel);
            SetLabel(rm, zipLabel);
            SetLabel(rm, homePhoneLabel);
            SetLabel(rm, workPhoneLabel);
            SetLabel(rm, addressGroupBox);
            SetLabel(rm, contactGroupBox);
            SetLabel(rm, countrylabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, address1TextBox);
            SetTooltip(rm, toolTip, address2TextBox);
            SetTooltip(rm, toolTip, address3TextBox);
            SetTooltip(rm, toolTip, cityTextBox);
            SetTooltip(rm, toolTip, stateTextBox);
            SetTooltip(rm, toolTip, zipTextBox);
            SetTooltip(rm, toolTip, homePhoneTextBox);
            SetTooltip(rm, toolTip, workPhoneTextBox);
            
            personalInfoUI.Localize();
            patientsFooterUI.Localize();
            patientInfoActionUI.Localize();
        }

        private void revertButton_Click(object sender, EventArgs e)
        {
            IsDirty = false;
            CancelPatientHandler(sender, e);
        }

        private void DefaultCancelPatientHandler(object sender, EventArgs e)
        {   
            if (patient == null)
            {
                PatientEvents.OnPatientDeleted(Pane, new ApplicationEventArgs(null, this));
            }
            else
            {
                RevertPatientInfo();
            }
        }

        public void RevertPatientInfo()
        {
            SetData(patient);
            EnableButtons(false);            
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            try
            {    
                if (ShowConfirmDeletePatientDialog() && !CheckPatientHasRequest())
                {
                    DisableEvents();
                    ROIViewUtility.MarkBusy(true);
                    PatientController.Instance.DeleteSupplementalPatient(patient.Id);
                    IsDirty = false;
                    ApplicationEventArgs ae = new ApplicationEventArgs(patient, this);
                    PatientEvents.OnPatientDeleted(Pane, ae);
                    EnableEvents();
                }
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

        /// <summary>
        /// Show the confirmation dialog
        /// </summary>
        /// <returns></returns>
        private bool ShowConfirmDeletePatientDialog()
        {
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString(DeletePatientDialogMessageKey);

            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString(DeletePatientDialogTitle);
            string okButtonText = rm.GetString(DeletePatientDialogOkButton);
            string cancelButtonText = rm.GetString("cancelButton");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(DeletePatientDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(DeletePatientDialogCancelButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Shows the dialog when user has confirmed to delete a patient 
        /// but the system has detected that the patient has since been included on a request
        /// </summary>
        /// <returns></returns>
        private bool CheckPatientHasRequest()
        {
            
            try
            {
                ROIViewUtility.MarkBusy(true);
//                patient = PatientController.Instance.RetrieveSupplementalInfo(patient.Id);
            }
            catch(ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            if (patient.HasRequests)
            {
                return ShowNonDeletedPatientDialog();
            }

            return false;
        }

        /// <summary>
        /// Shows the dialog when user has confirmed to delete a patient 
        /// but the system has detected that the patient has since been included on a request
        /// </summary>
        /// <returns></returns>
        private bool ShowNonDeletedPatientDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString("NonDeletedPatientDialog.Title");
            string okButtonText = rm.GetString("NonDeletedPatientDialog.OkButton");
            string messageText = rm.GetString("NonDeletedPatientDialog.Message");
            
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString("NonDeletedPatientDialog.OkButton");

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Error);

            return true;
        }

        /// <summary>
        /// Validate PatientInfo.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private static bool IsNonUniquePatient(PatientDetails patientInfo)
        {
            return PatientController.Instance.CheckDuplicateName(patientInfo);
        }

        private bool DisplayNonUniquePatientDialog()
        {
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString(NonUniquePatientDialogMessageKey);

            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString(NonUniquePatientDialogTitle);
            string okButtonText = rm.GetString(NonUniquePatientDialogOkButton);
            string cancelButtonText = rm.GetString("cancelButton");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(NonUniquePatientDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(NonUniquePatientDialogCancelButtonToolTip);

            return (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Error));
        }

        internal bool Save(object sender, EventArgs e)
        {
            log.EnterFunction();
            errorProvider.Clear();
            ROIViewUtility.MarkBusy(true);
            try
            {
                SavePatientHandler(sender, e);
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
                log.ExitFunction();
            }
            return true;
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            patientInfoActionUI.SaveButton.Focus();
            Save(sender,e);
        }

        private void DefaultSavePatientHandler(object sender, EventArgs e)
        {
            PatientDetails patientDetails = SavePatient();
            
            if (patientDetails != null)
            {
                SetData(patientDetails);
                EnableNavigationLinks();
                //PatientRSP patientRSP = Pane.ParentPane.ParentPane as PatientRSP;
                //(patientRSP.FindEditor.MCP.View as PatientSearchUI).AddFacility((patientDetails as PatientDetails).Facility);
                EnableRequestButtons(true);
            }
        }

        public PatientDetails SavePatient()
        {

            PatientDetails patientDetails = GetData(ROIViewUtility.DeepClone(patient)) as PatientDetails;
            PersonalInfoNonHpfUI nonHpfUI = (PersonalInfoNonHpfUI)personalInfoUI;

            if (IsNonUniquePatient(patientDetails))
            {
                if (!DisplayNonUniquePatientDialog())
                {
                    nonHpfUI.SetFocusOnName();
                    return null;
                }
                nonHpfUI.ShowNonUniqueIcon(true);
            }
            else
            {
                nonHpfUI.ShowNonUniqueIcon(false);
            }

            if (patient == null)
            {
                patientDetails.AuditMessage = PatientDetails.PrepareAuditMessageForCreateSupplemental(patientDetails);
                patientDetails.Id = PatientController.Instance.CreateSupplementalPatient(patientDetails);
            }
            else
            {
                patientDetails.AuditMessage = PatientDetails.PrepareAuditMessageForUpdateSupplemental(patientDetails, 
                                                                                                      (Pane.ParentPane as PatientInfoEditor).Patient);
                PatientController.Instance.UpdateSupplementalPatient(patientDetails);
                PatientEvents.OnPatientUpdated(Pane, new ApplicationEventArgs(patientDetails, null));
            }

            IsDirty = false;
           
            (Pane.ParentPane as PatientInfoEditor).Patient = patientDetails;

            return patientDetails;
        }

        private void EnableNavigationLinks()
        {
            if (patient != null)
            {
                PatientEvents.OnPatientCreated(this, null);
            }
        }

        internal void MarkDirty(object sender, EventArgs e)
        {
            IsDirty = true;
            patientInfoActionUI.RevertButton.Enabled = true;
            patientInfoActionUI.SaveButton.Enabled = (personalInfoUI.PatientFirstName.Length > 0) &&
                                                     (personalInfoUI.PatientLastName.Length > 0);
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.PatientsInformation, e));
        }

        private void EnableRequestButtons(bool enable)
        {
            patientsFooterUI.CreateRequestButton.Enabled = enable;
            patientsFooterUI.PatientRequestButton.Enabled = enable;
            patientInfoActionUI.DeleteButton.Enabled = enable;
        }


        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = patientInfoActionUI.SaveButton;
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

            if (!IsAllowed(securityRightIds, false))
            {   
                patientsFooterUI.Enabled = false;
                this.Enabled  = false;
            }

            HidePatientRequestButton = HideCreateRequestButton = !IsAllowed(ROISecurityRights.ROICreateRequest);
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

        public PatientDetails Patient
        {
            get { return patient; }
            set { patient = value; }
        }

        public PatientsFooterUI Footer
        {
            get { return patientsFooterUI; }
        }

        #endregion

        #region Event wiring properties

        public EventHandler SavePatientHandler
        {
            get { return savePatientHandler; }
            set { savePatientHandler = value; }
        }

        public EventHandler CancelPatientHandler
        {
            get { return cancelPatientHandler; }
            set { cancelPatientHandler = value; }
        }

        public EventHandler DirtyDataHandler
        {
            get { return dirtyDataHandler; }
            set { dirtyDataHandler = value; }
        }

        public bool HideCreateRequestButton
        {
            get { return patientsFooterUI.CreateRequestButton.Visible; }
            set { patientsFooterUI.CreateRequestButton.Visible = !value; }
        }

        public bool HidePatientRequestButton
        {
            get { return patientsFooterUI.PatientRequestButton.Visible; }
            set { patientsFooterUI.PatientRequestButton.Visible = !value; }
        }

        public bool HideDeleteNonHpfPatientButton
        {
            get { return patientInfoActionUI.DeleteButton.Visible; }
            set { patientInfoActionUI.DeleteButton.Visible = !value; }
        }

        public bool HideFooter
        {
            get { return patientsFooterUI.FooterPanel.Visible; }
            set 
            { 
                patientsFooterUI.FooterPanel.Visible = !value;
                patientsFooterUI.BackColor = Color.White;
            }
        }

        public Color FooterPanelBackColor
        {
            get { return patientsFooterUI.FooterPanel.BackColor; }
            set { patientsFooterUI.FooterPanel.BackColor = value; }
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return patientsFooterUI;
        }

        #endregion

        #region IPatientPageContext Members

        public Collection<PatientDetails> Patients
        {
            get
            {
                Collection<PatientDetails> selectedPatients = new Collection<PatientDetails>();
                selectedPatients.Add(patient);
                return selectedPatients;
            }
        }

        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        #endregion

        private void patientcountryCodeComboBox_SelectedIndexChanged(object sender, EventArgs e)
            {   if(!defaultCountry)
                {
                    if (patientcountryCodeComboBox.SelectedIndex != prevValue)
                    {
                        selectedCountry = true;
                    }                
                }
                prevValue = patientcountryCodeComboBox.SelectedIndex;
        }
    }
}
