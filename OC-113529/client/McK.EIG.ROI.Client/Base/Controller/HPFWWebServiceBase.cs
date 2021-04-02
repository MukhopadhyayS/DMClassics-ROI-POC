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
using System.Xml;

using McK.EIG.Common.Utility.Exceptions;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.WebServices;

using Microsoft.Web.Services3;
using Microsoft.Web.Services3.Design;
using Microsoft.Web.Services3.Security;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    ///  This class acts as a base class for the HPFWHelper class
    /// </summary>
    public abstract class HPFWWebServiceBase
    {
        #region Fields

        /// <summary>
        /// Key for looking up container from session.
        /// </summary>
        public const string CookieContainerKey = ROIWebServiceBase.COOKIE_CONTAINER_KEY; 
        
        /// <summary>
        /// Logger
        /// </summary>
        private static Log LOGGER = LogFactory.GetLogger(typeof(HPFWWebServiceBase));

        /// <summary>
        /// Service proxy object.
        /// </summary>
        private CustomWebServicesClientProtocol serviceProxy;

        /// <summary>
        /// Service method for performance logging.
        /// </summary>
        private string serviceMethod;

        /// <summary>
        /// Start time for the service.
        /// </summary>
        private long startTime;

        /// <summary>
        /// The name of the metric logger.
        /// </summary>
        private const string METRIC_LOGGER = "MetricLogger";
                
        /// <summary>
        /// Metric Logger.
        /// </summary>        
        private static MetricLogger metricLogger;

        /// <summary>
        /// Transaction id.
        /// </summary>
        private string transactionId;

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the Service
        /// </summary>
        /// <param name="serviceProxy"></param>
        /// <param name="serviceMethod"></param>
        protected void InitializeService(CustomWebServicesClientProtocol serviceProxy, string serviceMethod, string transactionId)
        {
            
            LOGGER.EnterFunction();
            if (metricLogger == null)
            {
                metricLogger = MetricLogger.GetInstance(METRIC_LOGGER);
            }
            this.serviceProxy = serviceProxy;
            this.serviceMethod = serviceMethod;
            this.transactionId = transactionId;

            LOGGER.Info("ServTimerBegin", this.serviceMethod, string.Empty);
            
            AddCookieContainer();

            //Sets Policy assertion to create custom soap header
            serviceProxy.SetPolicy(new Policy(new CustomSoapPolicyAssertion(serviceMethod)));

            //get the start time
            startTime = DateTime.Now.Ticks;
            LOGGER.ExitFunction();
        }

        private void AddCookieContainer()
        {
            ApplicationSession instance = ApplicationSession.Instance;
            CookieContainer sessionValue = (CookieContainer)instance.GetSessionValue(CookieContainerKey);
            if (sessionValue == null)
            {
                sessionValue = new CookieContainer();
                instance.SetSessionValue(CookieContainerKey, sessionValue);
            }
            serviceProxy.CookieContainer = sessionValue;
        }

        /// <summary>
        /// Stop performance timing.
        /// </summary>
        protected void MarkServiceAsFinished()
        {
            LOGGER.EnterFunction();

            //get the end time and calculate the difference
            double transactionTime = TimeSpan.FromTicks(DateTime.Now.Ticks - startTime).TotalSeconds;

            //log the performance
            LOGGER.Info("ServTimerEnd", serviceMethod, transactionId, transactionTime);

            //Log the metric
            metricLogger.Debug("HPFWService", serviceMethod, transactionId, transactionTime, string.Empty);

            LOGGER.ExitFunction();
        }

        #endregion
    }

    #region CustomSoapPolicyAssertion

    /// <summary>
    /// Allows to apply security at a higher level using a prepackaged set of security operations
    /// </summary>
    public class CustomSoapPolicyAssertion : PolicyAssertion
    {
        private string methodName;
        public CustomSoapPolicyAssertion()
        {
        }

        public CustomSoapPolicyAssertion(string methodNameIn)
        {
            methodName = methodNameIn;
        }

        public override SoapFilter CreateClientInputFilter(FilterCreationContext context)
        {
            return new ClientInputFilter(methodName);
        }

        public override SoapFilter CreateClientOutputFilter(FilterCreationContext context)
        {
            return new ClientOutputFilter();
        }

        public override SoapFilter CreateServiceInputFilter(FilterCreationContext context)
        {
            return null;
        }

        public override SoapFilter CreateServiceOutputFilter(FilterCreationContext context)
        {
            return null;
        }

        public override void ReadXml(XmlReader reader,  System.Collections.Generic.IDictionary<string, Type> extensions)
        {
        }

        public override void WriteXml(XmlWriter writer)
        {
        }
    }

    public class ClientInputFilter : SoapFilter
    {
        private string methodName;
        /// <summary>
        /// Logger
        /// </summary>
        private static Log LOGGER = LogFactory.GetLogger(typeof(HPFWWebServiceBase));

        public ClientInputFilter()
        {
        }


        public ClientInputFilter(string methodNameIn)
        {
            methodName = methodNameIn;
        }

        public override SoapFilterResult ProcessMessage(SoapEnvelope envelope)
        {
            try
            {

                if (methodName == "McK.EIG.ROI.Client.Base.Controller.ROIController.RetrieveHPFUsers")
                {
                    string str = envelope.Envelope.InnerXml;
                    int index = str.IndexOf("<getUsersResponse>");
                    str = str.Remove(index, 18);
                    index = str.IndexOf("</getUsersResponse>");
                    str = str.Remove(index, 19);

                    envelope.Envelope.InnerXml = str;

                }
            }
            catch (Exception ex)
            {
                LOGGER.Info("Failed to parse the server response", methodName, string.Empty);
                throw ex;
            }

            return SoapFilterResult.Continue;
        }
    }



    /// <summary>
    /// Provide implementation for only the ClientOutputFilter as we are trying 
    /// to modify an outgoing soap request
    /// </summary>
    public class ClientOutputFilter : SoapFilter
    {
        public ClientOutputFilter(): base() { }

        /// <summary>
        /// Adds new custom element(trackSession) in Soap Header
        /// </summary>
        /// <param name="envelope"></param>
        /// <returns></returns>
        public override SoapFilterResult ProcessMessage(SoapEnvelope envelope)
        {
            XmlElement soapHeader = envelope.Header;
            XmlElement trackSessionNode = soapHeader.OwnerDocument.CreateElement("eig", "trackSession", "urn:eig.mckesson.com");
            soapHeader.AppendChild(trackSessionNode);
            trackSessionNode.InnerText = "true";

            return SoapFilterResult.Continue;
        }
    }

    #endregion
}
