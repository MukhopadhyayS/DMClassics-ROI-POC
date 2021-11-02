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
using System.Net;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    public class LogOnPane : ROIBasePane
    {
        #region Fields
                
        private const int ValidUser = 0;
        private const int InvalidUser = 1;
        private const int UserLockedOut = 2;
        private const int UserDisabled = 3;
        private const int ChangePassword = 4;
        private const int InvalidUserPassword = 5;
        private const int UnAuthorizedUser = 9;
        private const int UserAlreadyMapped = -1;

        // Will be change in future
        private const int InvalidLDAPLogOn = 6;
        private const int InvalidHPFUserMapping = 7;
        
        private LogOnUI logOnUI;
        private Form logOn;
        private string[] userLists;
        private bool isLoginCancel;

        private const string NoSelfMappingTitle = "dslogon.title";
        private const string NoSelfMappingHeaderText = "NoSelfMappingUI.Header";
        private const string NoSelfMappingMessage = "NoSelfMappingUI.Message";
        private const string NoSelfMappingOkButton = "NoSelfMappingUI.OkButton";
        private const string NoSelfMappingOkButtonToolTip = "NoSelfMappingUI.OkButton";        
        private const string SuccessSelfMappingHeaderText = "SuccessSelfMapping.Header";
        private const string SuccessSelfMappingMessage = "SuccessSelfMapping.Message";
        private const string SuccessSelfMappingOkButton = "SuccessSelfMapping.OkButton";
        private const string SuccessSelfMappingCancelButton = "SuccessSelfMapping.CancelButton";
        //CR335609
        private const string SelfMappingUITitle = "selfMappingUI.title";

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the view
        /// </summary>
        protected override void InitView()
        {
            try
            {
                new ROIPane().deleteTemporaryFiles();
                GetPreLoginDetails();
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                // CR## 366281. Application will be closed once HPFW exception is catched
                Application.Exit();
            }            
            logOnUI = new LogOnUI(new EventHandler(Process_LogonClick), new EventHandler(Process_ChangePasswordClick),
                                  UserData.Instance.IsLdapEnabled);
            logOn = new Form();            
            logOn.Size = logOnUI.Size;
            logOn.FormBorderStyle = FormBorderStyle.FixedToolWindow;
            logOn.MaximizeBox = false;
            logOn.MinimizeBox = false;
            logOn.Controls.Add(logOnUI);

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            if (!UserData.Instance.IsLdapEnabled)
            {
                logOn.Text = rm.GetString("Logon.title");
            }
            else
            {
                logOn.Text = rm.GetString("dslogon.title");
            }
            logOn.StartPosition = FormStartPosition.CenterScreen;
        }

        private static void GetPreLoginDetails()
        {
            ROIController roiController = ROIController.Instance;
            roiController.RetrieveLogonInfo();
        }

        private void Process_ChangePasswordClick(object sender, EventArgs e)
        {
            UserData userData = logOnUI.GetData();
            ROIController roiController = ROIController.Instance;
            ROIViewUtility.MarkBusy(true);
            bool doLogOn = true;
            ApplicationEventArgs ae;

			// Code changes for CR# 355137, Keane,21-Sep-2011
            try
            {
            if (e != null)
            {
                ae = (ApplicationEventArgs)e;
                if ((int)ae.Info == ChangePassword)
                {
                    doLogOn = false;
                }
            }

            if (doLogOn)
            {
                roiController.LogOn();

                if (userData.SignInstate != ValidUser)
                {
                    ShowDialog(userData.SignInstate, Context);
                    return;
                }

                if (!userData.HasAccess(ROISecurityRights.ROIAccessApplication))
                {
                    ShowDialog(UnAuthorizedUser, Context);
                    return;
                }
            }
         
            ROIViewUtility.MarkBusy(false);
            ChangePasswordUI changePasswordUI = new ChangePasswordUI(Context);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                   rm.GetString("title.ChangePasswordUI"),
                                                   changePasswordUI);
            dialog.FormBorderStyle = FormBorderStyle.FixedToolWindow;
            dialog.MinimizeBox = false;
            dialog.MaximizeBox = false;
            changePasswordUI.Localize();
            dialog.BackColor = Color.FromArgb(246, 246, 246);
            DialogResult result = dialog.ShowDialog(logOnUI);
            if ((result == DialogResult.OK) || (result == DialogResult.Cancel))
            {
                roiController.LogOff();
                dialog.Close();
                }
            Application.Restart();
            }
            catch (ROIException cause)
            {
                // Code changes for CR# 355137, Keane,21-Sep-2011
                logOnUI.SelectControl();
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!logOnUI.HandleClientError(rm, cause, logOnUI.errorProvider))
                {
                    int count = cause.ErrorCodes.Count;
                    for (int errorCount = 0; errorCount < count; errorCount++)
                    {
                        if (cause.ErrorCodes[errorCount].ErrorCode == ROIErrorCodes.ConnectFailure)
                        {
                            ROIException exception = new ROIException(ROIErrorCodes.ROIConnectFailure);
                            exception.ErrorCodes[errorCount].ErrorMessage = cause.ErrorCodes[errorCount].ErrorMessage;
                            exception.ErrorCodes[errorCount].ErrorData = cause.ErrorCodes[errorCount].ErrorData;
                            ROIViewUtility.Handle(Context, exception);
                            return;
                        }
                    }
                    ROIViewUtility.Handle(Context, cause);
                }
            }            
        }

        /// <summary>
        /// Authenticates user credentials
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_LogonClick(object sender, EventArgs e)
        {          
            string Url = string.Empty;
            try
            {
                 UserData userData = logOnUI.GetData();
                ROIViewUtility.MarkBusy(true);
                ROIController roiController = ROIController.Instance;
                try
                { 
                    if (userData.IsLdapEnabled)
                    {
                        if (roiController.LogOnLdap() == null)
                        {
                            ShowDialog(InvalidLDAPLogOn, Context);
                            return;
                        }
                    }
                    else
                    {
                    
                            if (roiController.LogOn() == null)
                            {
                                ShowDialog(InvalidLDAPLogOn, Context);
                                return;
                            }
                     }
                }
                    catch (Exception es)
                    {
                        if (String.Compare(es.Message, "Unable to login. User Account is Disabled. Please contact your System Administrator.") == 0)
                        {
                            ShowDialog(UserDisabled, Context);
                            return;
                        }
                        else if (String.Compare(es.Message, "Maximum sign in attempts exceeded. User Account is Locked. Please contact your System Administrator.") == 0)
                        {
                            ShowDialog(UserLockedOut, Context);
                            return;
                        }
                        else
                        {
                            throw es;
                        }

                    }

                
                
                if ((userData.SignInstate != ValidUser && !userData.IsLdapEnabled) || CheckValidateCode || userData.UserId == null)
                {
                    if (userData.UserId != null)
                    {
                        ShowDialog(userData.SignInstate, Context);
                    }
                    else
                    {
                        ShowDialog(InvalidLDAPLogOn, Context);
                    }
                }
                else
                {
                    System.Net.ServicePointManager.CertificatePolicy = new ROICertificatePolicy();
                    //To Checks the ROI server is alive or not
                    //Url = global::McK.EIG.ROI.Client.Properties.Settings.Default.McK_EIG_ROI_Client_Web_References_ROIAdminWS_ROIAdminService;
                    //HttpTimerRetriever.GetGmtString(new Uri(Url));

                    if ((userData.IsLdapEnabled) && (userData.UserId != ROIConstants.UserMappingRequired))
                    {
                        if (userData.UserId.Contains(","))
                        {
                            userLists = userData.UserId.Split(',');
                            string selectedUser = ShowMultipleUserMapping(userLists);
                            if(string.IsNullOrEmpty(selectedUser))
                                return;
                            try
                            {

                                roiController.LogOnLdapWithHpfUserName(selectedUser);

                                if (!userData.HasAccess(ROISecurityRights.ROIAccessApplication))
                                {
                                    ShowDialog(UnAuthorizedUser, Context);
                                    return;
                                }
                                userData.UserId = selectedUser;
                            }
                            catch (Exception ex)
                            {
                                MessageBox.Show(ex.Message);
                                return;
                            }
                        }
                    }

                    if (!userData.IsLdapEnabled && (!userData.HasAccess(ROISecurityRights.ROIAccessApplication)))
                    {
                        ShowDialog(UnAuthorizedUser, Context);
                        return;
                    }

                    if (!userData.HasAccess(ROISecurityRights.ROIAccessApplication) && userData.UserId != ROIConstants.UserMappingRequired)
                    {
                        ShowDialog(InvalidLDAPLogOn, Context);
                        return;
                    }

                    if (userData.IsLdapEnabled)
                    {
                        SaveSuccessfulUserDomainDetails();

                        if (!userData.IsSelfMappingEnabled && userData.UserId == ROIConstants.UserMappingRequired)
                        {
                            ShowSelfMappingDisabledDialog();
                            //Commented for CR# 335609
                            //Application.Exit();
                            return;
                        }
                        else if (userData.UserId == ROIConstants.UserMappingRequired)
                        {
                            DialogResult dialogResult = ShowSelfMappingDialog();
                            //If the user clicks exit in add mapping dialog
                            if (dialogResult == DialogResult.Cancel)
                            {
                                //Commented for CR# 335609
                                //Application.Exit();
                                return;
                            }
                            else
                            {
                               ShowSuccessfulDialog();
                               if (isLoginCancel)
                                   return;
                            }
                        }                        
                    }
					// CR#365250 - set the default facility for userdata after successfully verifying the valid user
                    UpdateFacilityWithSalesTax();
                    ROIViewUtility.MarkBusy(false);
                    ROIEvents.OnLogOnClick(this, e);
                }
            }
            catch (ROIException cause)
            {
                logOnUI.SelectControl();
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                int count = cause.ErrorCodes.Count;
                if (!logOnUI.HandleClientError(rm, cause, logOnUI.errorProvider))
                {
                    for (int errorCount = 0; errorCount < count; errorCount++)
                    {
                        if (cause.ErrorCodes[errorCount].ErrorCode == ROIErrorCodes.ConnectFailure)
                        {
                            ROIException exception = new ROIException(ROIErrorCodes.ROIConnectFailure);
                            exception.ErrorCodes[errorCount].ErrorMessage = cause.ErrorCodes[errorCount].ErrorMessage;
                            exception.ErrorCodes[errorCount].ErrorData = cause.ErrorCodes[errorCount].ErrorData;
                            ROIViewUtility.Handle(Context, exception);
                            return;
                        }
                    }
                    ROIViewUtility.Handle(Context, cause);
                }                
            }
            catch (WebException webEx)
            {
                if (webEx.Status == WebExceptionStatus.NameResolutionFailure || webEx.Status == WebExceptionStatus.ConnectFailure)
                {   
                    string errorCode = ROIErrorCodes.ConnectFailure;                    
                    webEx.Source = Url.Replace(new Uri(Url).AbsolutePath, "");
                    ROIViewUtility.Handle(Context, new ROIException(errorCode, webEx));
                }
            }
        }

        private static void UpdateFacilityWithSalesTax()
        {
            //RetrieveAllTaxPerFacilities method returns the facilities which has been configured by admin.
            //UserData instance facilities returns the facilities coming from HPF
            //compare both the facilities and set the default facility if any one is set in admin into userdata
            Application.DoEvents();
            Collection<TaxPerFacilityDetails> taxPerFacilityDetails = BillingAdminController.Instance.RetrieveAllTaxPerFacilities(UserData.Instance.UserId);
            UserData.Instance.DefaultFacility = null;
            foreach (TaxPerFacilityDetails fac in taxPerFacilityDetails)
            {
                foreach (FacilityDetails facilityDetails in UserData.Instance.Facilities)
                {
                    if (fac.FacilityCode.Equals(facilityDetails.Code))
                    {
                        facilityDetails.TaxPercentage = fac.TaxPercentage;
                        facilityDetails.IsDefault = fac.IsDefault;
                        if (fac.IsDefault)
                        {
                            UserData.Instance.DefaultFacility = facilityDetails;
                        }                        
                        if (UserData.Instance.DefaultFacility != null)
                        {
                            break;
                        }
                    }                 
                }
            }
        }

        private static void SaveSuccessfulUserDomainDetails()
        {
            Configuration config = ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            config.AppSettings.Settings.Remove("DomainName");
            config.AppSettings.Settings.Add("DomainName", UserData.Instance.Domain);
            config.Save(ConfigurationSaveMode.Modified);
            ConfigurationManager.RefreshSection("appSettings");
        }

        private void ShowSelfMappingDisabledDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(NoSelfMappingMessage);
            string titleText = rm.GetString(NoSelfMappingTitle);
            string headerText = rm.GetString(NoSelfMappingHeaderText);
            string okButtonText = rm.GetString(NoSelfMappingOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(NoSelfMappingOkButtonToolTip);

            ROIViewUtility.ConfirmChanges(titleText, headerText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        private DialogResult ShowSelfMappingDialog()
        {
            SelfMappingUI selfMappingUI = new SelfMappingUI(Context);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form SelfMappingForm = ROIViewUtility.ConvertToForm(null, rm.GetString(SelfMappingUITitle), selfMappingUI);
            SelfMappingForm.FormBorderStyle = FormBorderStyle.FixedToolWindow;
            SelfMappingForm.MinimizeBox = false;
            SelfMappingForm.MaximizeBox = false;
            selfMappingUI.Localize();
            SelfMappingForm.BackColor = Color.FromArgb(246, 246, 246);
            DialogResult result = SelfMappingForm.ShowDialog(logOnUI);
            selfMappingUI.Dispose();
            return result;
        }

        private string ShowMultipleUserMapping(string[] userLists)
        {
            PickAccountUI pickAccountUI = new PickAccountUI(Context);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form PickAccountForm = ROIViewUtility.ConvertToForm(null, rm.GetString("dslogon.title"), pickAccountUI);
            PickAccountForm.FormBorderStyle = FormBorderStyle.FixedToolWindow;
            PickAccountForm.MinimizeBox = false;
            PickAccountForm.MaximizeBox = false;
            PickAccountForm.ControlBox = false;
            pickAccountUI.Localize();
            PickAccountForm.BackColor = Color.FromArgb(246, 246, 246);
            foreach (string user in userLists)
            {
                pickAccountUI.UserListBox.Items.Add(user);                
            }

            pickAccountUI.UserListBox.SelectedIndex = 0;
            DialogResult result = PickAccountForm.ShowDialog(logOnUI);
            if (result == DialogResult.OK)
            {
                PickAccountForm.Close();
            }
            else
            {
                PickAccountForm.Close();
                return string.Empty;
            }
            //pickAccountUI.Dispose();
            return pickAccountUI.SelectedUser;
        }
        private DialogResult ShowMapAnotherAccountUI(SortedList<string, string> userLists)
        {
            PickAnotherAccountUI pickAccountUI = new PickAnotherAccountUI(Context);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form PickAnotherAccountForm = ROIViewUtility.ConvertToForm(null, rm.GetString(SelfMappingUITitle), pickAccountUI);
            PickAnotherAccountForm.FormBorderStyle = FormBorderStyle.FixedToolWindow;
            PickAnotherAccountForm.MinimizeBox = false;
            PickAnotherAccountForm.MaximizeBox = false;
            PickAnotherAccountForm.ControlBox = false;
            pickAccountUI.Localize();
            PickAnotherAccountForm.BackColor = Color.FromArgb(246, 246, 246);            
            foreach (string user in userLists.Values)
            {
                pickAccountUI.UserListBox.Items.Add(user);
            }

            pickAccountUI.UserListBox.SelectedIndex = 0;
            DialogResult result = PickAnotherAccountForm.ShowDialog(logOnUI);
            if (result == DialogResult.Cancel)
            {
                if (UserData.Instance.UserLists.Count > 1)
                {
                    string[] users = new string[userLists.Values.Count];
                    int count = 0;
                    foreach (string user in userLists.Values)
                    {
                        users[count] = user;
                        count++;
                    }
                    string selectedUser = ShowMultipleUserMapping(users);
                    if (string.IsNullOrEmpty(selectedUser))
                        return DialogResult.Ignore;
                    ROIController.Instance.LogOnLdapWithHpfUserName(selectedUser);
                }
                else if(UserData.Instance.UserLists.Count == 1)
                {
                    ROIController.Instance.LogOnLdapWithHpfUserName(userLists.Values[0].ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
                }
            }
             return result;

            //pickAccountUI.Dispose();
            //return pickAccountUI.SelectedUser;
        }

        private void ShowSuccessfulDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString(SelfMappingUITitle);
            string headerText = rm.GetString(SuccessSelfMappingHeaderText);
            string messageText = rm.GetString(SuccessSelfMappingMessage);
            string okButtonText = rm.GetString(SuccessSelfMappingOkButton);
            string cancelButtonText = rm.GetString(SuccessSelfMappingCancelButton);
            bool result = ROIViewUtility.ConfirmChanges(titleText, headerText, messageText, okButtonText, cancelButtonText, true, ROIDialogIcon.RightIcon);

            UserData userData = UserData.Instance;

            if (!userData.UserLists.ContainsKey(userData.UserId))
            {
                userData.UserLists.Add(userData.UserId, userData.UserId);
            }

            if (result == true && userData.UserLists.Count == 1)
            {
                ROIController.Instance.LogOnLdapWithHpfUserName(userData.UserId);
            }
            else if (result == true && userData.UserLists.Count > 1)
            {
                string[] users = new string[userData.UserLists.Values.Count];
                int count = 0;
                foreach (string user in userData.UserLists.Values)
                {
                    users[count] = user;
                    count++;
                }
                string selectedUser = ShowMultipleUserMapping(users);
                if (string.IsNullOrEmpty(selectedUser))
                {
                    isLoginCancel = true;
                    return;
                }
                ROIController.Instance.LogOnLdapWithHpfUserName(selectedUser);
            }
            else
            {
                DialogResult dialogResult = ShowMapAnotherAccountUI(userData.UserLists);
                if (dialogResult == DialogResult.OK)
                {
                    ShowSuccessfulDialog();
                }
                else if (dialogResult == DialogResult.Ignore)
                {
                    isLoginCancel = true;
                }
            }
        }

        public void Show(IWin32Window parent)
        {
            logOn.ShowDialog(parent);
        }

        public void ShowDialog(int signInState, ExecutionContext executionContext)
        {
            string titleText;
            string messageText;
            string okButtonText;
            string dialogHeaderMessage;
            ResourceManager rm;

            if (Context == null)
            {
                Context = executionContext;
            }

            rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

            switch (signInState)
            {
                case InvalidUser:
                    {
                        messageText = rm.GetString("SigninState.InvalidUserPassword");
                        break;
                    }
                case InvalidUserPassword:
                    {
                        messageText = rm.GetString("SigninState.InvalidUserPassword");
                        break;
                    }
                case UserDisabled:
                    {
                        messageText = rm.GetString("SigninState.UserDisabled");
                        break;
                    }
                case UserLockedOut:
                    {
                        messageText = rm.GetString("SigninState.UserLockedOut");
                        break;
                    }
                case UnAuthorizedUser:
                    {
                        messageText = rm.GetString("SigninState.InvalidUser");
                        break;
                    }
                case InvalidLDAPLogOn:
                    {
                        messageText = rm.GetString("SigninState.InvalidLDAPLogin");
                        break;
                    }
                case InvalidHPFUserMapping:                    
                    {
                        messageText = rm.GetString("SigninState.InvalidHPFUserMapping");
                        break;
                    }
                default:
                    {
                        messageText = rm.GetString("SigninState.ChangePassword");
                        break;
                    }
            }
           

            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            okButtonText = rm.GetString("okButton.DialogUI");
            titleText = rm.GetString("LoginFailed.title");
            if (UserData.Instance.IsLdapEnabled)
            {
                dialogHeaderMessage = rm.GetString("dslogon.title");
            }
            else
            {
                dialogHeaderMessage = rm.GetString("Logon.title");
            }

            if (signInState == UserAlreadyMapped)
            {
                dialogHeaderMessage = rm.GetString(SelfMappingUITitle);
                rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                messageText = rm.GetString("Signinstate.UserAlreadyMapped");
                rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                titleText = rm.GetString("useralreadymapped.title");
            }
            if (signInState == InvalidUserPassword || signInState == InvalidUser)
            {
                dialogHeaderMessage = rm.GetString("LoginFailed.title");
            }

            ROIViewUtility.ConfirmChanges(dialogHeaderMessage, titleText, messageText, okButtonText, "", ROIDialogIcon.Error);

            //Close the application if the logged user doesn't have the secutity to access ROI
            if (signInState == UnAuthorizedUser)
            {
                //Application.Exit();
            }

            if ((signInState == ChangePassword) && (!UserData.Instance.IsLdapEnabled))
            {
                ApplicationEventArgs ae = new ApplicationEventArgs();
                ae.Info = ChangePassword;
                Process_ChangePasswordClick(null, ae);
            }           
        }

        /// <summary>
        /// Close login form
        /// </summary>
        public void Close()
        {
            logOn.Close();
        }
          
        #endregion

        #region Properties

        /// <summary>
        /// Returns view of login form
        /// </summary>
        public override Component View
        {
            get            
            {
                return logOnUI;
            }
        }

        /// <summary>
        /// Returns the login form.
        /// </summary>
        public Form LogOnForm
        {
            get
            {
                return logOn;
            }
        }

        public static bool CheckValidateCode
        {
            get
            {
                return (UserData.Instance.IsLdapEnabled && 
                        UserData.Instance.SignInstate != ChangePassword && 
                        UserData.Instance.SignInstate != ValidUser);
            }
        }

        #endregion
    }
}
