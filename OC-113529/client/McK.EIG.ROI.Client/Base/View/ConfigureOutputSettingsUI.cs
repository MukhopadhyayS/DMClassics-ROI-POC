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
using System.Globalization;
using System.Net;
using System.Resources;
using System.Runtime.InteropServices;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Utility.WebServices;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;
using System.Web;

namespace McK.EIG.ROI.Client.Base.View
{
    public partial class ConfigureOutputSettingsUI : ROIBaseUI
    {
        #region Constructor

        public ConfigureOutputSettingsUI(IPane pane)
        {
            InitializeComponent();
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
            SetLoadingCircleValues();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localize the UI text.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, closeButton);
            SetLabel(rm, lblLoading); 

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, closeButton);
        }

        /// <summary>
        ///  Localize the UI text.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == closeButton)
            {
                return control.Name + "." + GetType().Name;
            }
            return control.Name;
        }

        /// <summary>
        /// Set loading circle initial values
        /// </summary>
        private void SetLoadingCircleValues()
        {
            loadingCircle.Visible = false;
            loadingCircle.Active = false;
            lblLoading.Visible = false;
        }

        /// <summary>
        /// Apply localization of UI contros for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        public void SetData(object webAddress)
        {
            CookieContainer cookieContainer = (CookieContainer)McK.EIG.ROI.Client.Base.Controller.ApplicationSession.Instance.GetSessionValue(ROIWebServiceBase.COOKIE_CONTAINER_KEY);
            CookieCollection cookies = cookieContainer.GetCookies(new Uri(global::McK.EIG.ROI.Client.Properties.Settings.Default.McK_EIG_ROI_Client_Web_References_AuthenticationWS_AuthenticationService));
            string securityInfo;
            //CR# 382989
            if (UserData.Instance.IsLdapEnabled)
            {
                securityInfo = "?" + ROIConstants.Domain + "=" + HttpUtility.UrlEncode(UserData.Instance.Domain) + "&";
                securityInfo += ROIConstants.DomainUserName + "=" + HttpUtility.UrlEncode(UserData.Instance.DomainUserName) + "&";
                securityInfo += ROIConstants.UserSecretWord + "=" + HttpUtility.UrlEncode(UserData.Instance.DomainSecretWord) + "&";
                securityInfo += ROIConstants.HpfUserName + "=" + HttpUtility.UrlEncode(UserData.Instance.UserId) + "&";
                securityInfo += ROIConstants.AppId + "=" + ROIConstants.ROI + "&";
            }
            else
            {
                securityInfo = "?" + ROIConstants.UserName + "=" + HttpUtility.UrlEncode(UserData.Instance.UserId) + "&";
                securityInfo += ROIConstants.UserSecretWord + "=" + HttpUtility.UrlEncode(UserData.Instance.SecretWord) + "&";
                securityInfo += ROIConstants.AppId + "=" + ROIConstants.ROI + "&";
            }
            // Authentication ticket hardcoded value will be removed after getting from HPF
            securityInfo += ROIConstants.AuthTicket + "=" + "1234";
            
            string url = webAddress.ToString() + securityInfo;
            NativeMethods.InternetSetCookie(url, "JSESSIONID", cookies["JSESSIONID"].Value);

            webBrowser.Navigate(new Uri(url));
        }

        /// <summary>
        /// This method will invoked while progress changed on the web browser control.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void webBrowser_ProgressChanged(object sender, WebBrowserProgressChangedEventArgs e)
        {
            if (e.MaximumProgress != e.CurrentProgress && e.CurrentProgress != -1)
            {
                loadingCircle.Active = true;
                loadingCircle.Visible = true;
                lblLoading.Visible = true;
            }
            else
            {
                loadingCircle.Active = false;
                loadingCircle.Visible = false;
                lblLoading.Visible = false;
            }
        }

        #endregion

        #region Properties
        #endregion      
    }
}
