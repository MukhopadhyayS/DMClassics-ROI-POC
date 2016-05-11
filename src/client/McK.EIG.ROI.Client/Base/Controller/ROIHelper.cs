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
using System.Net.Security;
using System.Reflection;
using System.Configuration;
using System.Security.Cryptography.X509Certificates;

using System.Web.Services.Protocols;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.WebServices;

using McK.EIG.ROI.Client.Base.Model;

using Microsoft.Web.Services3;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// Invokes the service methods with respect to ROI services
    /// </summary>
    public class ROIHelper : ROIWebServiceBase, IWsHelper
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ROIHelper));
        private const string failedMessage = "AuthorizationException";
        private const string updateRequest = "updateRequest";
        private const string output = "output";
        
        #endregion

        #region Methods

         /// <summary>
        /// Invokes the service method without security information
        /// </summary>
        /// <param name="serviceProxy">service proxy</param>
        /// <param name="serviceMethod">Method to be invoked</param>
        /// <param name="requestParams">request information</param>
        /// <param name="hasSecurity">Security Verification</param>
        /// <returns>server response</returns>
        public object Invoke(CustomWebServicesClientProtocol serviceProxy, string serviceMethod, object[] requestParams)
        {
            return Invoke(serviceProxy, serviceMethod, requestParams, true);
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
                OCSecurityWrapper.GetSecureToken();
                serviceProxy.SetjSessionid(UserData.Instance.JsessionID);
                serviceProxy.SetSecureToken(UserData.Instance.RSAToken);

                MethodBase mBase = new StackFrame(2).GetMethod();
                if (addSecurityParams)
                {
                    InitSecurityData(serviceProxy, mBase.DeclaringType.FullName.ToString() + "." + mBase.Name);
                }
                serviceProxy.Timeout = ROIController.Timeout;
                System.Net.ServicePointManager.CertificatePolicy = new ROICertificatePolicy();
                return serviceProxy.GetType().InvokeMember(serviceMethod,
                                                            BindingFlags.InvokeMethod,
                                                            null,
                                                            serviceProxy,
                                                            requestParams,
                                                            System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            catch (TargetInvocationException e)
            {
                try
                {
                    throw (e.InnerException != null) ? e.InnerException : e;
                }
                catch (WebException webEx)
                {
                    log.FunctionFailure(webEx);
                    string errorCode = webEx.Message;
                    bool doAlert = false;
                    if (webEx.Status == WebExceptionStatus.NameResolutionFailure
                        || webEx.Status == WebExceptionStatus.ConnectFailure)
                    {
                        doAlert = false;
                        errorCode = ROIErrorCodes.ConnectFailure;
                        if (serviceProxy.Url.IndexOf(output) > 0)
                        {
                            errorCode = ROIErrorCodes.OutputConnectFailure;
                        }
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
                    String serverName = string.Empty;
                    char[] delimit=new char[] {'/',':'};
                    serverName = serviceProxy.Url.Substring(serviceProxy.Url.IndexOf(":") + 3);
                    serverName=serverName.Substring(0,serverName.IndexOfAny(delimit));
                      
                    if (!string.IsNullOrEmpty(soapEx.Detail.InnerText))
                    {
                        if (soapEx.Detail.InnerText.Contains(ROIErrorCodes.RetrieveCCDFailure))
                        {
                            if (soapEx.Detail.InnerText.ToUpper().EndsWith(serverName.ToUpper()))
                                soapEx.Detail.InnerText = soapEx.Detail.InnerText.Substring(0, soapEx.Detail.InnerText.Length - serverName.Length);                           
                            throw e.InnerException;
                        }
                        if (soapEx.Detail.InnerText.StartsWith(failedMessage) || soapEx.Detail.InnerText.IndexOf("T033")>0)
                        {
                            if (serviceMethod == updateRequest)
                            {
                                throw new ROIException("AR000");
                            }
                            throw new ROIException("AU000");
                        }
                        if (soapEx.Detail.InnerText.Contains(ROIErrorCodes.UnknownObjectException) 
                                && soapEx.Detail.InnerText.Contains(ROIErrorCodes.InvalidReference))
                        {
                            throw new ROIException("InvalidOutputQueue");
                        }
                    }
                    log.Debug(soapEx.Detail.InnerXml);                    
                    throw new ROIException(soapEx);
                }
            }
            finally
            {
                //Logs the response time
                MarkServiceAsFinished();
                log.ExitFunction();
            }
        }

       private static void DoAlert(WebServicesClientProtocol serviceProxy, WebException webEx)
       {
            LogEvent logEvent   = new LogEvent();
            logEvent.AuthUser   = UserData.Instance.UserId;
            logEvent.Code       = ROIErrorCodes.ConnectFailure;
            logEvent.Message    = webEx.Message + " - '" + serviceProxy.Url + "'";
            logEvent.Details    = webEx.StackTrace;
            ROIController.SendAlert(logEvent);
        }

        /// <summary>
        /// Sets the unique transaction id to service proxy for each service request
        /// </summary>
        /// <param name="serviceProxy">service proxy</param>
        /// <param name="transactionId">Unique transaction id</param>
        private static void SetServiceTxnId(WebServicesClientProtocol serviceProxy, string transactionId)
        {
            PropertyInfo txnIDProperty = serviceProxy.GetType().GetProperty("transactionId");            
            object txnIDInstance = Activator.CreateInstance(txnIDProperty.PropertyType);
            txnIDInstance.GetType().InvokeMember("Text", 
                                                 BindingFlags.SetProperty, 
                                                 null, 
                                                 txnIDInstance, 
                                                 new object[] { new string[] { transactionId.ToString() } },
                                                 System.Threading.Thread.CurrentThread.CurrentUICulture);
            txnIDProperty.SetValue(serviceProxy, txnIDInstance, null);
        }

        /// <summary>
        /// Sets the application id to service proxy for each output service.
        /// </summary>
        /// <param name="serviceProxy">Service Proxy</param>
        /// <param name="applicationId">Application Id</param>
        private static void SetApplicationId(WebServicesClientProtocol serviceProxy, string applicationId)
        {
            if (serviceProxy.GetType().GetProperty("applicationId") == null) return;            
            PropertyInfo appIdProperty = serviceProxy.GetType().GetProperty("applicationId");            
            object appIDInstance = Activator.CreateInstance(appIdProperty.PropertyType);
            appIDInstance.GetType().InvokeMember("Text",
                                                 BindingFlags.SetProperty,
                                                 null,
                                                 appIDInstance,
                                                 new object[] { new string[] { applicationId.ToString() } },
                                                 System.Threading.Thread.CurrentThread.CurrentUICulture);
            appIdProperty.SetValue(serviceProxy, appIDInstance, null);
        }

        /// <summary>
        /// Initializes security infomation and forms the security header
        /// </summary>
        /// <param name="serviceProxy">service proxy</param>
        /// <param name="callerMethod">caller method</param>
        private void InitSecurityData(CustomWebServicesClientProtocol serviceProxy, string callerMethod)
        {
            //Gets unique identifier for transaction id
            Guid transactionId = Guid.NewGuid();

            string txnId = transactionId.ToString() + "." + UserData.Instance.UserId;
            
            //Sets the transactionid to service proxy
            SetServiceTxnId(serviceProxy, txnId);

            //Gets the application id from configuration file
            string applicationId = ConfigurationManager.AppSettings["AppId"];

            //Sets the applicationid to service proxy
            SetApplicationId(serviceProxy, applicationId);

            //Forms the security header with collected security information
            InitializeService(serviceProxy, callerMethod, txnId, UserData.Instance);
        }
        #endregion

    }
}
