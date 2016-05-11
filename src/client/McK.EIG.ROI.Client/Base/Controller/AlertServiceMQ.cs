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
using System.Configuration;
using System.Globalization;

using Apache.NMS.ActiveMQ;
using Apache.NMS.ActiveMQ.Commands;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;

using Apache.NMS;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// AlertServiceMQ
    /// </summary>
    public class AlertServiceMQ : IDisposable
    {

        #region Fields

        private static volatile AlertServiceMQ alertServiceMQ;
        private static object syncRoot = new Object();
        private static readonly Log log = LogFactory.GetLogger(typeof(AlertServiceMQ));

        private IConnection      connection;
        private ISession         session;
        private IMessageProducer producer;

        #endregion

        #region Constructor

        private AlertServiceMQ()
        {
            CreateConnection();
        }
        #endregion

        #region Methods

        #region CreateConnection

        private void CreateConnection()
        {
            log.EnterFunction();
            string uri = string.Empty;
            try
            {
                string alertServer = McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "ip");
                string alertPort = McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "port");
                alertServer = string.IsNullOrEmpty(alertServer) ? "localhost" : alertServer;
                alertPort   = string.IsNullOrEmpty(alertPort) ? "61616" : alertPort;

                uri = string.Format(CultureInfo.InvariantCulture, "tcp://{0}:{1}",
                        alertServer,
                        alertPort);
                log.Debug(string.Format(CultureInfo.InvariantCulture, "Connecting to Alert JMS Queue at {0}", uri));
                ConnectionFactory connectionFactory = new ConnectionFactory(new Uri(uri));
                connection = connectionFactory.CreateConnection();

                session = connection.CreateSession();

                ActiveMQQueue destination = (ActiveMQQueue)session.GetQueue(ConfigurationManager.AppSettings["AlertQueueName"]);
                destination.PhysicalName = ConfigurationManager.AppSettings["AlertPhysicalName"];

                //creating the producer            
                producer = session.CreateProducer(destination);
                //producer.Persistent = true;
            }
            catch (System.Net.Sockets.SocketException e)
            {
                log.FunctionFailure(e);
                throw new ROIException(ROIErrorCodes.ConnectAlertServer, uri);
            }

            log.ExitFunction();
        }

        #endregion

        #region Disconnect

        /// <summary>
        /// Must disconnect before exiting from application otherwise the application hangs
        /// </summary>
        private void Disconnect()
        {
            log.EnterFunction();

            session.Close();
            session = null;
            connection.Close();
            connection = null;
            alertServiceMQ = null;
            log.ExitFunction();
        }
        #endregion

        #region CreateAlertEntry

        /// <summary>
        /// Method used to create alert entry to the server.
        /// </summary>
        /// <param name="logEvent">LogEvent</param>
        /// <returns>bool</returns>
        public bool CreateAlertEntry(LogEvent logEvent)
        {
            log.EnterFunction();
            bool ret = true;

            try
            {   
                SendMessage(logEvent);
            }
            catch (ROIException ex)
            {
                ret = false;
                log.FunctionFailure(ex);
            }
            finally
            {
                log.ExitFunction();
            }

            return (ret);
        }
        #endregion

        #region SendMessage

        /// <summary>
        /// Sends the message to the queue
        /// </summary>
        /// <param name="logEvent"></param>
        private void SendMessage(LogEvent logEvent)
        {
            log.EnterFunction();

            if (logEvent == null)
            {
                throw new ROIException("Invalid alert event");
            }

            string strMessage = logEvent.ToXml();

            log.Debug(strMessage);

            ActiveMQTextMessage message = (ActiveMQTextMessage)session.CreateTextMessage(strMessage);
            // message.NMSPersistent = true;
            message.Persistent = true;
            producer.Send(message);
            log.ExitFunction();
        }

        #endregion

        #region IDisposable Members

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            log.EnterFunction();
            if (disposing)
            {
                Disconnect();
            }
            log.ExitFunction();
        }

        #endregion

        #endregion

        #region Properties

        /// <summary>
        /// Get the MediaTypeController Instance
        /// </summary>
        public static AlertServiceMQ Instance
        {
            get
            {
                lock (syncRoot)
                {
                    if (alertServiceMQ == null)
                    {
                        alertServiceMQ = new AlertServiceMQ();
                    }
                }
                return alertServiceMQ;
            }
        }
        #endregion
    }
}
