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
using System.Drawing;
using System.Globalization;
using System.Reflection;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Patient.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Builds the MainMenu for the application.
    /// </summary>
    public class MenuPane : ROIBasePane
    {
        #region Fields
        
        private const string MenuSuffix = ".menu";

        private MainMenu mainMenu;
        private ResourceManager uiTextRm;

        //Patient Events
        private EventHandler newPatient;
        private EventHandler modifyPatient;
        private EventHandler deletePatient;
        private EventHandler findPatientClick;
        private EventHandler patientCreated;

        //Requestor Events
        private EventHandler findRequestor;
        private EventHandler requestorInfo;
        private EventHandler deleteRequestor;
        private EventHandler createRequestor;
        private EventHandler requestorCreated;
        private EventHandler requestorStatusChanged;

        //Request Events
        private EventHandler requestSearched;
        private EventHandler requestSelected;
        private EventHandler createRequest;
        private EventHandler requestInfoCreated;  
        private EventHandler dsrChanged;
        private EventHandler requestUpdated;
        
        private Dictionary<string, long> inUseRecords;

        private Form helpDialog;

        private bool canAccessRequest;
        #endregion

        #region Methods
       
        /// <summary>
        /// Initializes the Menu.
        /// </summary>
        protected override void InitView()
        {
            MenuItem mi;
            MenuItem subMI;

            uiTextRm = CultureManager.GetCulture(CultureType.UIText.ToString());

            mainMenu = new MainMenu();
            mainMenu.Name = "MainMenu";

            //Securtiy menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.SecurityMenu,false);
            if (!UserData.Instance.IsLdapEnabled)
            {
                CreateMenuItem(uiTextRm, mi, ROIConstants.ChangePasswordMenu, false);
            }
            CreateMenuItem(uiTextRm, mi, ROIConstants.MonitorJobsMenu, false);    
            CreateMenuItem(uiTextRm, mi, ROIConstants.LogOffMenu, false);            
            CreateMenuItem(uiTextRm, mi, ROIConstants.ExitMenu, false);

            //Patient's menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.PatientsModuleName);
            CreateMenuItem(uiTextRm, mi, ROIConstants.FindPatients);            
            CreateMenuItem(uiTextRm, mi, ROIConstants.PatientsInformation).Enabled    = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.PatientsRecords).Enabled        = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.PatientsRequestHistory).Enabled = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.PatientCreateRequest).Enabled   = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.PatientRequest).Enabled         = false;
            
            //Requestor's menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.RequestorModuleName);
            CreateMenuItem(uiTextRm, mi, ROIConstants.FindRequestor);
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestorInformation).Enabled    = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestorRequestHistory).Enabled = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestorCreateRequest).Enabled  = false;
            //CreateMenuItem(uiTextRm, mi, ROIConstants.RequestorLetterHistory).Enabled  = false;
			CreateMenuItem(uiTextRm, mi, ROIConstants.RequestorAccountManagement).Enabled = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestorAccountHistory).Enabled = false;
            
            //Requests's menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.RequestsModuleName);
            CreateMenuItem(uiTextRm, mi, ROIConstants.FindRequest);
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestInformation).Enabled        = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestPatientInformation).Enabled = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.BillingAndPayment).Enabled         = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.ReleaseHistory).Enabled            = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.EventHistory).Enabled              = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.Comments).Enabled                  = false;
            CreateMenuItem(uiTextRm, mi, ROIConstants.Letters).Enabled                   = false;

            //Reports menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.ReportsModuleName);
            
            CreateMenuItem(uiTextRm, mi, ROIConstants.AccountReceivableAging);
            CreateMenuItem(uiTextRm, mi, ROIConstants.AccountingOfDisclosure);
            //CR#377452
            CreateMenuItem(uiTextRm, mi, ROIConstants.BillableUnbillable);
            CreateMenuItem(uiTextRm, mi, ROIConstants.DocumentsReleasedByMRN);
            CreateMenuItem(uiTextRm, mi, ROIConstants.MUReleaseTurnaroundTime);
            CreateMenuItem(uiTextRm, mi, ROIConstants.OutstandingInvoiceBalances);
            CreateMenuItem(uiTextRm, mi, ROIConstants.PendingAgingSummary);
            CreateMenuItem(uiTextRm, mi, ROIConstants.PostedPaymentReport);
            CreateMenuItem(uiTextRm, mi, ROIConstants.PreBillReport);
            CreateMenuItem(uiTextRm, mi, ROIConstants.ProcessRequestSummary);
            CreateMenuItem(uiTextRm, mi, ROIConstants.Productivity);
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestDetailReport);
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestStatusSummary);
            CreateMenuItem(uiTextRm, mi, ROIConstants.RequestsWithLoggedStatus);
            CreateMenuItem(uiTextRm, mi, ROIConstants.ROIBilling);
            CreateMenuItem(uiTextRm, mi, ROIConstants.SalesTaxReport);
            CreateMenuItem(uiTextRm, mi, ROIConstants.TurnaroundTimes);

            //Change the order of menu, display prior to Administartion menu
			//OverDue Invoice menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.OverDueInvoiceModuleName);
            subMI = CreateMenuItem(uiTextRm, mi, ROIConstants.FindOverDueInvoice);

            //Admin menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.AdminModuleName);
            subMI = CreateMenuItem(uiTextRm, mi, ROIConstants.AdminBilling);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingMediaType);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingTier);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingFeeType);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingTemplate);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingRequestorType);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingPageWeight);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingDeliveryMethod);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingPaymentMethod);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingInvoiceDue);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminBillingSalesTaxPerFacility);

            subMI = CreateMenuItem(uiTextRm, mi, ROIConstants.AdminReasons);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminReasonAdjustment);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminReasonRequest);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminReasonRequestStatus);

            subMI = CreateMenuItem(uiTextRm, mi, ROIConstants.AdminOthers);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminOtherDisclosureDoc);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminOtherLetterTemplate);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminOtherConfigureNotes);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminOtherSsnMasking);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminConfigurationExternalSources);
            //ROI16.0 zipcode
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminOtherConfigureCountry);
            CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminOtherConfigureDefaultUnbillableRequest);

            //subMI = CreateMenuItem(uiTextRm, mi, ROIConstants.AdminConfiguration);
            //CreateMenuItem(uiTextRm, subMI, ROIConstants.AdminConfigurationAttachment);

            //Help menu
            mi = CreateMenuItem(uiTextRm, mainMenu, ROIConstants.HelpMenu, false);
            subMI = CreateMenuItem(uiTextRm, mi, ROIConstants.ROIHelp, false);
            subMI.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                       subMI.Text,
                                       Assembly.GetEntryAssembly().GetName().Version.ToString());


            CreateMenuItem(uiTextRm, mi, ROIConstants.AboutROI, false);
 
            
            //Note : Commented localization menu as per BID
            //mi = CreateMenuItem(uiTextRm, mainMenu, LanguageMenu);
            //CreateMenuItem(uiTextRm, mi, LanguageEnglishMenu);
            //CreateMenuItem(uiTextRm, mi, LanguageFrenchMenu);

            //Apply Securtiy rights
            ApplySecurityRights();

            inUseRecords = new Dictionary<string, long>();
            helpDialog = new Form();
        }

        private MenuItem CreateMenuItem(ResourceManager uiTextRm, Menu parent, string menuName)
        {
            return CreateMenuItem(uiTextRm, parent, menuName, true);
        }
        
        private MenuItem CreateMenuItem(ResourceManager uiTextRm, Menu parent, string menuName, bool addSuffix)
        {
            MenuItem mi = new MenuItem();
            mi.Name = menuName;
            mi.Text = (addSuffix) ? uiTextRm.GetString(menuName + MenuSuffix) : uiTextRm.GetString(menuName);
            mi.Click += new EventHandler(OnMenuClick);
            mi.Tag = addSuffix;
            parent.MenuItems.Add(mi);
            return mi;
        }

        /// <summary>
        /// Occurs when the menu is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnMenuClick(object sender, EventArgs e)
        {
            MenuItem menuItem = sender as MenuItem;

            if (menuItem.Name == ROIConstants.AboutROI)
            {
                ShowROIHelpDialog();
                return;
            }

            if (menuItem.Name == ROIConstants.ROIHelp)
            {
                ((ROIPane)ParentPane).HelpRequested(null, null);
                return;
            }

            if (menuItem.Name == ROIConstants.PatientCreateRequest)
            {
                PatientEvents.OnCreateRequest(sender, e);
                return;
            }

            if (menuItem.Name == ROIConstants.PatientRequest)
            {
                PatientEvents.OnPatientRequesting(sender, e);
                return;
            }

            if (menuItem.Name == ROIConstants.RequestorCreateRequest)
            {
                RequestorEvents.OnCreateRequestWithRequestor(sender, e);
                return;
            }

            //Convert into application event.
            ApplicationEventArgs ae = new ApplicationEventArgs((menuItem).Name, e);

            //Transient data validation.
            if (!(((ROIPane)ParentPane).SupportedCultures.Contains((menuItem).Name)
                    || TransientDataValidator.Validate(ae)))
            {
                return;
            }

            if (menuItem.Name == ROIConstants.ChangePasswordMenu)
            {
                ChangePassword();
                ROIController.Instance.LogOff();
                Application.Restart();
                return;
            }

            if (menuItem.Name == ROIConstants.MonitorJobsMenu)
            {
                WebBrowser browser = new WebBrowser();
                if (browser.Version.Major == 11)
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    MessageBox.Show(rm.GetString("MonitorOutputJobIE11NotSupported.Message"));
                }
                else
                {
                    ShowMonitorOutputJobDialog();
                }
                browser = null;
                return;
            }

            if (menuItem.Name == ROIConstants.LogOffMenu)
            {
                try
                {
					//If true, application exit
                    ReleaseInUseRecords(true);
                    ROIController.Instance.LogOff();
                }
                catch (ROIException cause)
                {
                    ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                    Collection<ExceptionData> errorMessages = cause.GetErrorMessage(rm);
                    if (!string.Equals(errorMessages[0].ErrorCode, ROIErrorCodes.RecordNotFound))
                    {
                        if (!string.Equals(errorMessages[0].ErrorCode, ROIErrorCodes.ConnectFailure))
                            ROIViewUtility.Handle(Context, cause);
                    }                    
                }
                Application.Restart();
                return;
            }

            if (menuItem.Name == ROIConstants.ExitMenu)
            {
                ParentPane.Cleanup();
                Application.Exit();
                return;
            }

            ROIEvents.OnNavigationChange(this, ae);
        }

       /// <summary>
       /// Shows about roi help dialog window.
       /// </summary>
        private void ShowROIHelpDialog()
        {
            if (helpDialog.Controls.Count > 0)
            {
                helpDialog.Focus();
                return;
            }
            AboutROIHelpDialogUI aboutROIHelpDialogUI = new AboutROIHelpDialogUI(Context);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            helpDialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title.aboutROIHelpDialogUI"), aboutROIHelpDialogUI);
            helpDialog.StartPosition = FormStartPosition.CenterScreen;
            aboutROIHelpDialogUI.SetData();
            helpDialog.Show();
        }

        /// <summary>
        /// Change Password.
        /// </summary>
        private void ChangePassword()
        {
            ChangePasswordUI changePasswordUI = new ChangePasswordUI(this.Context);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                   rm.GetString("title.ChangePasswordUI"),
                                                   changePasswordUI);
            dialog.MinimizeBox = false;
            dialog.MaximizeBox = false;
            changePasswordUI.Localize();
            dialog.BackColor = Color.FromArgb(246, 246, 246);
            dialog.ShowDialog();
            dialog.Close();
        }

        /// <summary>
        /// Show the monitor output job dialog
        /// </summary>
        public void ShowMonitorOutputJobDialog()
        {
            MonitorOutputJobsUI monitorOutputJobDialog = new MonitorOutputJobsUI(ParentPane);
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form monitorJobdialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString(monitorOutputJobDialog.GetType().Name + ".titlebar.title"),
                                                       monitorOutputJobDialog);
            monitorOutputJobDialog.SetData(true);
            monitorJobdialog.ShowDialog();
            monitorJobdialog.Close();            
        }



        public override void Localize()
        {
            Localize(mainMenu);
        }

        private void Localize(Menu m)
        {
            foreach (MenuItem mi in m.MenuItems)
            {
                mi.Text = ((bool)mi.Tag) ? uiTextRm.GetString(mi.Name + MenuSuffix) : uiTextRm.GetString(mi.Name);
                Localize(mi);
            }
        }

        public static MenuItem GetRootMenuItem(MenuItem child)
        {
            return (child.Parent.GetType() == typeof(MenuItem)) ? GetRootMenuItem((MenuItem)child.Parent) : child;
        }

        /// <summary>
        /// Subscribe the Events.
        /// </summary>
        protected override void Subscribe()
        {
            base.Subscribe();

            //Requestor Events
            findRequestor    = new EventHandler(Process_FindRequestor);
            requestorInfo    = new EventHandler(Process_RequestorInfo);
            deleteRequestor  = new EventHandler(Process_DeleteRequestor);
            createRequestor  = new EventHandler(Process_CreateRequestor);
            requestorCreated = new EventHandler(Process_RequestorCreated);
            requestorStatusChanged = new EventHandler(Process_RequestorStatusChanged);

            RequestorEvents.NavigateFindRequestor  += findRequestor;
            RequestorEvents.RequestorSelected      += requestorInfo;
            RequestorEvents.RequestorDeleted       += deleteRequestor;
            RequestorEvents.CreateRequestor        += createRequestor;
            RequestorEvents.RequestorCreated       += requestorCreated;
            RequestorEvents.RequestorStatusChanged += requestorStatusChanged;
            RequestorEvents.AccountManagementSelected += requestorInfo;

            //Patient Events
            newPatient       = new EventHandler(Process_NewPatient);
            modifyPatient    = new EventHandler(Process_ModifyPatient);
            deletePatient    = new EventHandler(Process_DeletePatient);
            findPatientClick = new EventHandler(Process_FindPatientClick);
            patientCreated   = new EventHandler(Process_PatientCreated);

            PatientEvents.CreatePatient       += newPatient;
            PatientEvents.PatientSelected     += modifyPatient;
            PatientEvents.PatientDeleted      += deletePatient;
            PatientEvents.NavigateFindPatient += findPatientClick;
            PatientEvents.PatientCreated      += patientCreated;

            //Request Events
            requestSearched           = new EventHandler(Process_RequestSearched);
            requestSelected           = new EventHandler(Process_RequestSelected);
            createRequest             = new EventHandler(Process_CreateRequest);
            requestInfoCreated        = new EventHandler(Process_RequestInfoCreated);
            dsrChanged                = new EventHandler(Process_DsrChanged);
            requestUpdated            = new EventHandler(Process_RequestUpdated);
            
            RequestEvents.NavigateFindRequest       += requestSearched;
            RequestEvents.RequestSelected           += requestSelected;
            RequestEvents.CreateRequest             += createRequest;
            RequestEvents.RequestInfoCreated        += requestInfoCreated;
            RequestEvents.DocumentsTreeChanged      += dsrChanged;
            RequestEvents.RequestUpdated            += requestUpdated;
        }

        /// <summary>
        /// Unsubscribe the Events.
        /// </summary>
        protected override void Unsubscribe()
        {
            base.Unsubscribe();
            //Requestor Events
            RequestorEvents.NavigateFindRequestor  -= findRequestor;
            RequestorEvents.RequestorSelected      -= requestorInfo;
            RequestorEvents.RequestorDeleted       -= deleteRequestor;
            RequestorEvents.CreateRequestor        -= createRequestor;
            RequestorEvents.RequestorCreated       -= requestorCreated;
            RequestorEvents.RequestorStatusChanged -= requestorStatusChanged;
            RequestorEvents.AccountManagementSelected -= requestorInfo;

            //Patient Events
            PatientEvents.CreatePatient       -= newPatient;
            PatientEvents.PatientSelected     -= modifyPatient;
            PatientEvents.PatientDeleted      -= deletePatient;
            PatientEvents.NavigateFindPatient -= findPatientClick;
            PatientEvents.PatientCreated      -= patientCreated;

            //Request Events
            RequestEvents.NavigateFindRequest  -= requestSearched;
            RequestEvents.RequestSelected      -= requestSelected;
            RequestEvents.CreateRequest        -= createRequest;
            RequestEvents.RequestInfoCreated   -= requestInfoCreated;
            RequestEvents.DocumentsTreeChanged -= dsrChanged;
            RequestEvents.RequestUpdated       -= requestUpdated;
        }

        /// <summary>
        /// Enable the sub-menu when new requestor was created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_FindRequestor(object sender, EventArgs e)
        {
            EnableRequestorMenu(false);
        }

        /// <summary>
        /// Enable the sub-menu when modifying the existing requestor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestorInfo(object sender, EventArgs e)
        {
            EnableRequestorMenu(true);
        }

        /// <summary>
        /// Disable the sub-menu when deleting the existing requestor.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_DeleteRequestor(object sender, EventArgs e)
        {
            EnableRequestorMenu(false);
        }

        /// <summary>
        /// Invoked when New new Requestor Button clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreateRequestor(object sender, EventArgs e)
        {   
            EnableRequestorMenu(false);
            MenuItem requestorMenu = mainMenu.MenuItems[ROIConstants.RequestorModuleName];
            requestorMenu.MenuItems[ROIConstants.RequestorInformation].Enabled = true;
        }

        /// <summary>
        /// Invoked when new requestor is created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestorCreated(object sender, EventArgs e)
        {
            EnableRequestorMenu(true);
        }

        private void Process_RequestorStatusChanged(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            MenuItem requestorMenu = mainMenu.MenuItems[ROIConstants.RequestorModuleName];
            requestorMenu.MenuItems[ROIConstants.RequestorCreateRequest].Enabled = Convert.ToBoolean(ae.Info, 
                                                                                                    System.Threading.Thread.CurrentThread.CurrentUICulture);
        }

        private void Process_NewPatient(object sender, EventArgs e)
        {
            EnablePatientMenu(false);
            MenuItem patientMenu = mainMenu.MenuItems[ROIConstants.PatientsModuleName];
            patientMenu.MenuItems[ROIConstants.PatientsInformation].Enabled    = true;
        }

        private void Process_ModifyPatient(object sender, EventArgs e)
        {
            EnablePatientMenu(true);          
        }

        private void Process_DeletePatient(object sender, EventArgs e)
        {
            EnablePatientMenu(false);            
        }

        private void Process_FindPatientClick(object sender, EventArgs e)
        {
            EnablePatientMenu(false);            
        }

        /// <summary>
        /// Invoked when new non-hpf patient is created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_PatientCreated(object sender, EventArgs e)
        {
            EnablePatientMenu(true);
        }

        //Request Events
        /// <summary>
        /// Invoked when Find Request navigation Link/New Request Creation cancelled Created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestSearched(object sender, EventArgs e)
        {
            MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];
            requestMenu.MenuItems[ROIConstants.RequestInformation].Enabled        = false;
            requestMenu.MenuItems[ROIConstants.RequestPatientInformation].Enabled = false;
            EnableRequestMenu(false);
			//If false, application Restart
            ReleaseInUseRecords(false);
        }

        /// <summary>
        /// Invoked when Request selected to view/Edit.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestSelected(object sender, EventArgs e)
        {
            MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];
            requestMenu.MenuItems[ROIConstants.RequestInformation].Enabled = true;
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            if (((RequestDetails)ae.Info).Status.ToString() == "Unknown")
            {
                requestMenu.MenuItems[ROIConstants.RequestPatientInformation].Enabled = false;
                EnableRequestMenu(false);
                return;
            }
            if (((RequestDetails)ae.Info).Requestor != null)
            {
                requestMenu.MenuItems[ROIConstants.RequestPatientInformation].Enabled = true;
                EnableRequestMenu(true);
            }

            //Disable menu items if the selected request has no release
            DisableMenuItemsRequestWithNoRelease(ae);
        }

        /// <summary>
        /// Invoked when New new Request Button clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreateRequest(object sender, EventArgs e)
        {
            MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];
            requestMenu.MenuItems[ROIConstants.RequestInformation].Enabled = true;
            requestMenu.MenuItems[ROIConstants.RequestPatientInformation].Enabled = false;
            requestMenu.MenuItems[ROIConstants.BillingAndPayment].Enabled = false;
            requestMenu.MenuItems[ROIConstants.ReleaseHistory].Enabled = false;
            requestMenu.MenuItems[ROIConstants.EventHistory].Enabled = false;
            requestMenu.MenuItems[ROIConstants.Comments].Enabled = false;
            requestMenu.MenuItems[ROIConstants.Letters].Enabled = false;
        }

        /// <summary>
        /// Invoked when Request Info Created.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestInfoCreated(object sender, EventArgs e)
        {
            MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];            
            requestMenu.MenuItems[ROIConstants.RequestPatientInformation].Enabled = true;
            EnableRequestMenu(true);

            //Disable menu items if the selected request has no release
            DisableMenuItemsRequestWithNoRelease((ApplicationEventArgs)e);
            //requestPatientInfoCreated(sender, e);
        }

        /// <summary>
        /// Occurs when request is being updated
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_RequestUpdated(object sender, EventArgs e)
        {
            MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];
            requestMenu.MenuItems[ROIConstants.RequestPatientInformation].Enabled = true;

            EnableRequestMenu(true);

            //Disable links if the selected request has no release
            DisableMenuItemsRequestWithNoRelease((ApplicationEventArgs)e);
        }

        private void Process_DsrChanged(object sender, EventArgs e)
        {
            MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];            
            requestMenu.MenuItems[ROIConstants.BillingAndPayment].Enabled = (bool)((ApplicationEventArgs)e).Info;            
        }

        /// <summary>
        /// Enable or Disable the link of the Patient Menu.
        /// </summary>
        /// <param name="enable"></param>
        private void EnablePatientMenu(bool enable)
        {
            MenuItem patientMenu = mainMenu.MenuItems[ROIConstants.PatientsModuleName];
            patientMenu.MenuItems[ROIConstants.PatientsInformation].Enabled     = enable;
            patientMenu.MenuItems[ROIConstants.PatientsRecords].Enabled         = enable;
            patientMenu.MenuItems[ROIConstants.PatientsRequestHistory].Enabled = enable && canAccessRequest;
            patientMenu.MenuItems[ROIConstants.PatientCreateRequest].Enabled    = enable;
            patientMenu.MenuItems[ROIConstants.PatientRequest].Enabled          = enable;
        }

        /// <summary>
        /// Enable or Disable the link of the Request Menu.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableRequestMenu(bool enable)
        {
            MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];            
            requestMenu.MenuItems[ROIConstants.BillingAndPayment].Enabled = enable;
            requestMenu.MenuItems[ROIConstants.ReleaseHistory].Enabled    = enable;
            requestMenu.MenuItems[ROIConstants.EventHistory].Enabled      = enable;
            requestMenu.MenuItems[ROIConstants.Comments].Enabled          = enable;
            requestMenu.MenuItems[ROIConstants.Letters].Enabled           = enable;
        }

        /// <summary>
        /// Enable or Disable the link of the Requestor Menu.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableRequestorMenu(bool enable)
        {
            MenuItem requestorMenu = mainMenu.MenuItems[ROIConstants.RequestorModuleName];
            requestorMenu.MenuItems[ROIConstants.RequestorInformation].Enabled    = enable;
            requestorMenu.MenuItems[ROIConstants.RequestorRequestHistory].Enabled = enable && canAccessRequest;
            requestorMenu.MenuItems[ROIConstants.RequestorCreateRequest].Enabled  = enable;
			//CR#365477 - Enable the requestor letter menu when requestor is in context
            //requestorMenu.MenuItems[ROIConstants.RequestorLetterHistory].Enabled  = enable;            
			requestorMenu.MenuItems[ROIConstants.RequestorAccountManagement].Enabled = enable;
            requestorMenu.MenuItems[ROIConstants.RequestorAccountHistory].Enabled = enable;
        }

        private void DisableMenuItemsRequestWithNoRelease(ApplicationEventArgs ae)
        {
            RequestDetails request = (RequestDetails)ae.Info;
            if (!request.IsLocked)
            {
                if (inUseRecords.ContainsKey(ROIConstants.RequestDomainType))
                {
                    inUseRecords.Remove(ROIConstants.RequestDomainType);
                }
                inUseRecords.Add(ROIConstants.RequestDomainType, request.Id);
            }

            //CR# 368499 Enable the Billing & Payment menu item in Request main menu .
			// In some scenarios the total pages of release can still having 0 pages eventhough DraftRelease has created
			// At that time, Billing & Payment menu item is not enabled. By removing the total pages
			//checking condition of draft release, it can be enabled.
            if (request.ReleaseCount == 0 && (!request.HasDraftRelease))
            {
                MenuItem requestMenu = mainMenu.MenuItems[ROIConstants.RequestsModuleName];
                requestMenu.MenuItems[ROIConstants.BillingAndPayment].Enabled = false;
                //requestMenu.MenuItems[ROIConstants.InvoiceHistory].Enabled    = false;
                //requestMenu.MenuItems[ROIConstants.ReleaseHistory].Enabled    = false;
            }
        }

        public void ReleaseInUseRecords(bool isApplicationExit)
        {
            try
            {
                if((inUseRecords != null) && (inUseRecords.Keys != null))
                {
                    foreach (string objectType in inUseRecords.Keys)
                    {
                        ROIController.Instance.ReleaseInUseRecord(objectType, inUseRecords[objectType]);
                    }
                    inUseRecords.Clear();
                }            
            }
            catch (ROIException cause)
            {   
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                Collection<ExceptionData> errorMessages = cause.GetErrorMessage(rm);
                if (string.Equals(errorMessages[0].ErrorCode, ROIErrorCodes.ConnectFailure))
                {
                    inUseRecords.Clear();
                }
                if (!string.Equals(errorMessages[0].ErrorCode, ROIErrorCodes.RecordNotFound))
                {
                    if (isApplicationExit)
                    {
                        int count = cause.ErrorCodes.Count;
                        for (int errorCount = 0; errorCount < count; errorCount++)
                        {
                            if (cause.ErrorCodes[errorCount].ErrorCode == ROIErrorCodes.ConnectFailure)
                            {
                                ROIException exception = new ROIException(ROIErrorCodes.LogOff);
                                exception.ErrorCodes[errorCount].ErrorMessage = cause.ErrorCodes[errorCount].ErrorMessage;
                                exception.ErrorCodes[errorCount].ErrorData = cause.ErrorCodes[errorCount].ErrorData;
                                ROIViewUtility.Handle(Context, exception);
                                return;
                            }
                        }
                    }
                    ROIViewUtility.Handle(Context, cause);
                }

            }
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for menu items based on permissions tht user has.
        /// </summary>
        private void ApplySecurityRights()
        {   
            //Administration menu
            if(!ROIViewUtility.IsAllowed(ROISecurityRights.ROIAdministration))
            {
                mainMenu.MenuItems[ROIConstants.AdminModuleName].Visible = false;
            }

            if (!ROIViewUtility.IsAllowed(ROISecurityRights.ROICreateRequest))
            {
                //Patient menu
                MenuItem mi = mainMenu.MenuItems[ROIConstants.PatientsModuleName];
                mi.MenuItems[ROIConstants.PatientCreateRequest].Visible = false;
                mi.MenuItems[ROIConstants.PatientRequest].Visible       = false;
            
                //Requestor menu
                mi = mainMenu.MenuItems[ROIConstants.RequestorModuleName];
                mi.MenuItems[ROIConstants.RequestorCreateRequest].Visible = false;
            }

            //Reports menu
            if (!ROIViewUtility.IsAllowed(ROISecurityRights.ROIReports))
            {
                mainMenu.MenuItems[ROIConstants.ReportsModuleName].Visible = false;
            }

            canAccessRequest = (ROIViewUtility.IsAllowed(ROISecurityRights.ROIViewRequest) ||
                               ROIViewUtility.IsAllowed(ROISecurityRights.ROICreateRequest) ||
                               ROIViewUtility.IsAllowed(ROISecurityRights.ROIModifyRequest));
        }

        #endregion

        #region Properties
        /// <summary>
        /// Returns the view of ROIConstants.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get { return mainMenu; }
        }

        #endregion
    }
}
