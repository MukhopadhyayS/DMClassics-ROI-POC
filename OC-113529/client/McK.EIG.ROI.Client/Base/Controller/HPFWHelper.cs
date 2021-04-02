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
using System.Diagnostics;
using System.Globalization;
using System.Net;
using System.Reflection;
using System.Security.Cryptography;
using System.Text;
using System.Web.Services.Protocols;

using Microsoft.Web.Services3;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// Invokes the service methods with respect to HPFW services
    /// </summary>
    public class HPFWHelper : HPFWWebServiceBase, IWsHelper
    {
        
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(HPFWHelper));
        private const string failedMessage = "AuthenticationFailedException";
        private const string sessionExpiredMessage = "SessionExpiredException";
        private int sessionExpiredCount;

        #endregion

        #region Methods

        /// <summary>
        /// Invokes the service method after generating security information
        /// </summary>
        /// <param name="serviceProxy">service proxy</param>
        /// <param name="serviceMethod">Method to be invoked</param>
        /// <param name="requestParams">request information</param>
        /// <param name="hasSecurity">Security Verification</param>
        /// <returns>server response</returns>
        public object Invoke(CustomWebServicesClientProtocol serviceProxy, string serviceMethod, object[] requestParams)
        {
            try
            {
                return Invoke(serviceProxy, serviceMethod, requestParams, true);
            }
            catch (Exception es)
            {
                throw es;
            }
        }

        /// <summary>
        /// Invokes the service method after generating security information
        /// </summary>
        /// <param name="serviceProxy">service proxy</param>
        /// <param name="serviceMethod">Method to be invoked</param>
        /// <param name="requestParams">request information</param>
        /// <param name="hasSecurity">Security Verification</param>
        /// <returns>server response</returns>
        public object Invoke(CustomWebServicesClientProtocol serviceProxy, string serviceMethod, object[] requestParams, bool addSecurityParams)
        {
            log.EnterFunction();
            try
            {
                if (OCSecurityWrapper.IsDLLInitialized())
                {
                    try
                    {
                        OCSecurityWrapper.GetSecureToken();
                    }
                    catch (Exception es)
                    {
                        throw es;
                    }
                    if (String.IsNullOrEmpty(UserData.Instance.JsessionID) || String.IsNullOrEmpty(UserData.Instance.RSAToken))
                    {
                        return null;
                    }
                    serviceProxy.SetjSessionid(UserData.Instance.JsessionID);
                    serviceProxy.SetSecureToken(UserData.Instance.RSAToken);
                }

                MethodBase mBase = new StackFrame(2).GetMethod();
                if (addSecurityParams)
                {
                    string transactionId = Guid.NewGuid().ToString() + "." + UserData.Instance.UserId;
                    InitializeService(serviceProxy, mBase.DeclaringType.FullName.ToString() + "." + mBase.Name, transactionId);
                }
                serviceProxy.Timeout = ROIController.Timeout;

                System.Net.ServicePointManager.CertificatePolicy = new ROICertificatePolicy();
                return serviceProxy.GetType().InvokeMember(serviceMethod,
                                                           BindingFlags.InvokeMethod,
                                                           null,
                                                           serviceProxy,
                                                           requestParams,
                                                           System.Threading.Thread.CurrentThread.CurrentUICulture);

                System.Net.ServicePointManager.Expect100Continue = true;
                System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Ssl3;
            }
            catch (TargetInvocationException e)
            {
                try
                {
                    //Changes to provide specific error message instead of just system error when credentials are invalid
                    string n1 = Convert.ToString((e.InnerException.Message));
                    string errorMessage = n1.Trim();
                    if (errorMessage == ("Invalid security credentials."))
                    {
                        throw new ROIException("SigninState.InvalidUserPassword");
                    }
                    else
                    {
                        throw (e.InnerException != null) ? e.InnerException : e;
                    }
                }
                catch (WebException webEx)
                {
                    log.FunctionFailure(webEx);
                    string errorCode = webEx.Message;
                    bool doAlert = false;
                    if (webEx.Status == WebExceptionStatus.NameResolutionFailure || webEx.Status == WebExceptionStatus.ProtocolError
                        || webEx.Status == WebExceptionStatus.ConnectFailure || webEx.Status == WebExceptionStatus.ConnectionClosed)
                    {
                        doAlert = false;
                        errorCode = ROIErrorCodes.HpfConnectFailure;
                        webEx.Source = serviceProxy.Url.Replace(serviceProxy.Destination.TransportAddress.AbsolutePath, "");
                    }
                    else if (webEx.Status == WebExceptionStatus.Timeout)
                    {
                        doAlert = true;
                        errorCode = ROIErrorCodes.Timeout;
                    }
                    if (doAlert)
                    {
                        DoAlert(serviceProxy, webEx);
                    }
                    throw new ROIException(errorCode, webEx);
                }
                catch (SoapException soapEx)
                {
                    log.FunctionFailure(soapEx);
                    //AuthenticationFailedException: The system was unable to locate your account. Please verify your User name and Password and try again.ROIBUILD1
                    if (!string.IsNullOrEmpty(soapEx.Detail.InnerText))
                    {
                        if (soapEx.Detail.InnerText.StartsWith(failedMessage))
                        {
                            log.Debug(soapEx.Detail.InnerXml);
                            throw new ROIException("SigninState.InvalidUserPassword");
                        }
                    }
                   
                    //Handled SessionExpiredException in HPF Webservice.
                    //After bouncing the HPFW server, find patient will throw an session expired exception.
                    if (!string.IsNullOrEmpty(soapEx.Message))
                    {
                        if (string.Compare(soapEx.Message, sessionExpiredMessage, true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {   
                            if ((UserData.Instance.IsLdapEnabled) && (sessionExpiredCount == 0))
                            {
                                sessionExpiredCount = 1;
                                log.Debug(soapEx.Message);       
                                ROIController.Instance.LogOnLdapWithHpfUserName(UserData.Instance.UserId);
                                return Invoke(serviceProxy, serviceMethod, requestParams, addSecurityParams);
                            }
                            else if(!(UserData.Instance.IsLdapEnabled) && (sessionExpiredCount == 0))
                            {
                                sessionExpiredCount = 1;
                                log.Debug(soapEx.Message);
                                ROIController.Instance.LogOn();
                                return Invoke(serviceProxy, serviceMethod, requestParams, addSecurityParams);
                            }
                        }
                    }
                    log.Debug(soapEx.Detail.InnerXml);
                    throw new ROIException(soapEx);                    
                }
            }
            finally
            {
                MarkServiceAsFinished();
                log.ExitFunction();
            }
        }

        private static void DoAlert(WebServicesClientProtocol serviceProxy, WebException webEx)
        {
            LogEvent logEvent = new LogEvent();
            logEvent.AuthUser = UserData.Instance.UserId;
            logEvent.Code = ROIErrorCodes.HpfConnectFailure;
            logEvent.Message = webEx.Message + " - '" + serviceProxy.Url + "'";
            logEvent.Details = webEx.StackTrace;
            ROIController.SendAlert(logEvent);
        }

        #endregion
    }
}
