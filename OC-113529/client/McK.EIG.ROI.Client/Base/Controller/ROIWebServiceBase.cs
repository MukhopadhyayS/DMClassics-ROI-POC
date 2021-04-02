#region Copyright © 2007 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/*
 * Use of this material is governed by a license agreement. 
 * This material contains confidential, proprietary and trade 
 * secret information of McKesson Information Solutions and is 
 * protected under United States and international copyright and 
 * other intellectual property laws. Use, disclosure, 
 * reproduction, modification, distribution, or storage in a 
 * retrieval system in any form or by any means is prohibited 
 * without the prior express written permission of McKesson 
 * Information Solutions.
 */
#endregion

//System
using System;
using System.Collections;
using System.Globalization;
using System.Net;
using System.Web.Services.Protocols;

//MS
using Microsoft.Web.Services3;
using Microsoft.Web.Services3.Security.Tokens;

//McK
using McK.EIG.Common.Utility.Exceptions;
using McK.EIG.Common.Utility.Logging;

// Log4Net
using log4net;
using McK.EIG.Common.Utility.WebServices;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// ApplicationSesson stores sesson values during application lifetime.
    /// </summary>
    public class ApplicationSession
    {
        #region Fields

        /// <summary>
        /// Singleton instance.
        /// </summary>
        private static readonly ApplicationSession instance = new ApplicationSession();

        /// <summary>
        /// Stores session data in name/value pairs.
        /// </summary>
        private Hashtable sessionData = new Hashtable();

        #endregion

        #region Constructors

        /// <summary>
        /// Private default constructor.
        /// </summary>
        private ApplicationSession()
        {
        }

        #endregion

        #region Methods

        /// <summary>
        /// Obtain instance of ApplicationSession.
        /// </summary>
        /// <returns></returns>
        public static ApplicationSession Instance
        {
            get
            {
                return ApplicationSession.instance;
            }
        }

        /// <summary>
        /// Clear all stored session values.
        /// </summary>
        public void ClearSessionValues()
        {
            this.sessionData.Clear();
        }

        /// <summary>
        /// Get session value using key value.
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public object GetSessionValue(object key)
        {
            if (!this.sessionData.ContainsKey(key))
            {
                //for now, just return null if not present
                return null;
            }

            return this.sessionData[key];
        }

        /// <summary>
        /// Set session value. No nulls for key or value for safety.
        /// </summary>
        /// <param name="key"></param>
        /// <param name="value"></param>
        public void SetSessionValue(object key, object value)
        {
            if (key == null)
            {
                throw new ArgumentNullException("key");
            }

            if (value == null)
            {
                throw new ArgumentNullException("value");
            }

            if (!this.sessionData.ContainsKey(key))
            {
                this.sessionData.Add(key, value);
            }
            else
            {
                this.sessionData[key] = value;
            }
        }

        /// <summary>
        /// Remove session value.
        /// </summary>
        /// <param name="key"></param>
        /// <param name="value"></param>
        public void RemoveSessionValue(object key)
        {
            if (key == null)
            {
                throw new ArgumentNullException("key");
            }

            this.sessionData.Remove(key);
        }

        #endregion
    }

    /// <summary>
    /// WebServiceBase is an abstract class for all classes which act as wrappers for webservices.
    /// </summary>
    public abstract class ROIWebServiceBase
    {
        /// <summary>
        /// Key for looking up container from session.
        /// </summary>
        public const string COOKIE_CONTAINER_KEY = "COOKIE_CONTAINER_KEY";

        /// <summary>
        /// The name of the metric logger.
        /// </summary>
        private const string METRIC_LOGGER = "MetricLogger";

        /// <summary>
        /// Logger.
        /// </summary>
        private static readonly Log LOGGER = LogFactory.GetLogger(typeof(ROIWebServiceBase));

        /// <summary>
        /// Metric Logger.
        /// </summary>
        private static readonly MetricLogger metricLogger = MetricLogger.GetInstance(METRIC_LOGGER);

        /// <summary>
        /// True if in progress on a service call.
        /// </summary>
        private bool inProgress;

        /// <summary>
        /// Security token.
        /// </summary>
        private ISecurityToken securityToken;

        /// <summary>
        /// Service method for performance logging.
        /// </summary>
        private string serviceMethod = string.Empty;

        /// <summary>
        /// Service proxy object.
        /// </summary>
        private CustomWebServicesClientProtocol serviceProxy;

        /// <summary>
        /// Start time for the service.
        /// </summary>
        private long startTime;

        /// <summary>
        /// Transaction id.
        /// </summary>
        private string transactionId = string.Empty;

        /// <summary>
        /// Default constructor.
        /// </summary>
        protected ROIWebServiceBase()
        {
        }



        /// <summary>
        /// Add security token.
        /// </summary>
        /// <param name="securityToken"></param>
        /// <param name="proxyObject"></param>
        public static void AddSecurityToken(ISecurityToken securityToken, CustomWebServicesClientProtocol proxyObject)
        {
            //verify arguments
            if (securityToken == null)
            {
                throw new ArgumentNullException("securityToken");
            }

            if (proxyObject == null)
            {
                throw new ArgumentNullException("proxyObject");
            }

            ApplicationSession appSession = ApplicationSession.Instance;
            CookieContainer cookieContainer = (CookieContainer)appSession.GetSessionValue(
                ROIWebServiceBase.COOKIE_CONTAINER_KEY);

            if (cookieContainer == null)
            {
                cookieContainer = new CookieContainer();
                appSession.SetSessionValue(ROIWebServiceBase.COOKIE_CONTAINER_KEY, cookieContainer);
            }

            proxyObject.CookieContainer = cookieContainer;

            //always force the use of the new token for the proxy
            SecurityToken token = securityToken.SecurityToken;

#pragma warning disable
            proxyObject.RequestSoapContext.Security.Tokens.Clear();
            proxyObject.RequestSoapContext.Security.Tokens.Add(token);
#pragma warning restore
        }

        /// <summary>
        /// Clear any stored tokens.
        /// </summary>
        /// <param name="proxyObject"></param>
        public static void ClearSecurityTokens(WebServicesClientProtocol proxyObject)
        {
            //verify arguments
            if (proxyObject == null)
            {
                throw new ArgumentNullException("proxyObject");
            }

#pragma warning disable
            proxyObject.RequestSoapContext.Security.Tokens.Clear();
#pragma warning restore
        }

        /// <summary>
        /// Clear cookie container.
        /// </summary>
        /// <param name="proxyObject"></param>
        public static void ClearCookieContainer(HttpWebClientProtocol proxyObject)
        {
            //verify arguments
            if (proxyObject == null)
            {
                throw new ArgumentNullException("proxyObject");
            }

            proxyObject.CookieContainer = null;
        }

        /// <summary>
        /// Initialize web service and start performance timing.
        /// </summary>
        /// <param name="serviceProxy"></param>
        /// <param name="serviceMethod"></param>
        /// <param name="transactionId"></param>
        /// <param name="securityToken"></param>
        public void InitializeService(CustomWebServicesClientProtocol serviceProxy, string serviceMethod,
            Guid transactionId, ISecurityToken securityToken)
        {
            InitializeService(serviceProxy, serviceMethod, Convert.ToString(transactionId, CultureInfo.CurrentCulture), securityToken);
        }

        /// <summary>
        /// Initialize web service and start performance timing.
        /// </summary>
        /// <param name="serviceProxy"></param>
        /// <param name="serviceMethod"></param>
        /// <param name="transactionId"></param>
        /// <param name="securityToken"></param>
        public void InitializeService(CustomWebServicesClientProtocol serviceProxy, string serviceMethod,
            string transactionId, ISecurityToken securityToken)
        {
            LOGGER.EnterFunction();

            LOGGER.Debug(string.Format("serviceProxy=%0, serviceMethod=%1, transactionId=%2, securityToken=%3",
                 serviceProxy.ToString(), serviceMethod.ToString(), transactionId.ToString(), securityToken.ToString()));

            //init members
            this.serviceProxy = serviceProxy;
            this.serviceMethod = serviceMethod;
            this.transactionId = transactionId;
            this.securityToken = securityToken;

            if (this.inProgress)
            {
                LOGGER.Debug("Throwing exception: Service has already been initialized");
                throw new EIGException("Service has already been initialized");
            }

            //log start
            LOGGER.Info("ServTimerBegin", this.serviceMethod, transactionId);

            //init service
            this.inProgress = true;

            ROIWebServiceBase.AddSecurityToken(this.securityToken, this.serviceProxy);

            //get the start time 
            this.startTime = DateTime.Now.Ticks;
            LOGGER.ExitFunction();
        }

        /// <summary>
        /// Stop performance timing.
        /// </summary>
        public void MarkServiceAsFinished()
        {
            LOGGER.EnterFunction();

            if (!this.inProgress)
            {
                LOGGER.Warn("ServNotInit");
                return;
            }

            //get the end time 
            long endTime = DateTime.Now.Ticks;

            //calculate difference
            double transactionTime = TimeSpan.FromTicks(endTime - this.startTime).TotalSeconds;

            //log performance
            LOGGER.Info("ServTimerEnd", this.serviceMethod, transactionId, transactionTime);

            //Log the metric
            metricLogger.Debug("Service", this.serviceMethod, transactionId, transactionTime, string.Empty);

            this.inProgress = false;
            LOGGER.ExitFunction();
        }
    }
}
