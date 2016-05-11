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
using System.ComponentModel;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.ConfigurationWS;
using McK.EIG.ROI.Client.Web_References.CcdConversionServiceWS;
using McK.EIG.Common.Utility.Logging;

namespace McK.EIG.ROI.Client.Admin.Controller
{    
    public partial class ROIAdminController
    {
        private Log log = LogFactory.GetLogger(typeof(ROIAdminController));

        #region Methods

        #region Service Methods

        /// <summary>
        /// This method is used to retrieve and return a list of external sources
        /// </summary>
        /// <returns>List of external sources</returns>
        public Collection<ExternalSource> DisplayExternalSources()
        {
                LogExtSourceWebserviceCall(ccdconversionService, "getExternalSources", null);
                object response = ROIHelper.Invoke(ccdconversionService, "getExternalSources", new object[0]);
                Collection<ExternalSource> externalsources = new Collection<ExternalSource>();
                if (response != null)
                {
                    externalsources = MapModel((CcdSourceDto[])response);
                }
                return externalsources;
         }

        public Collection<ExternalSource> RetrieveProviders()
        {
            LogExtSourceWebserviceCall(ccdconversionService, "getAvailableProviders", null);
            object response = ROIHelper.Invoke(ccdconversionService, "getAvailableProviders", new object[0]);
            Collection<ExternalSource> externalsources = new Collection<ExternalSource>();
            if (response != null)
            {
                externalsources = MapModel((CcdSourceDto[])response);
            }
            return externalsources;
        }

        /// <summary>
        /// This method will update the Name/Description of an external source
        /// </summary>
        /// <param name="data"></param>
        /// <returns>Success/Failure as boolean</returns>
        public bool updateExternalSource(ExternalSource data)
        {
            ROIAdminValidator validator=new ROIAdminValidator();
            if (!validator.ValidateSourceFields(data))
            {
                throw validator.ClientException;
            }
            CcdSourceDto serverext = MapModel(data);
            object[] requestParams = new object[] { serverext };
            LogExtSourceWebserviceCall(ccdconversionService, "updateSource", data);
            object response = ROIHelper.Invoke(ccdconversionService, "updateSource", requestParams);
            return (bool)response;
         }

        public int createExternalSource(ExternalSource data)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdateSource(data))
            {
                throw validator.ClientException;
            }
            CcdSourceDto serverext = MapModel(data);
            object[] requestParams = new object[] { serverext };
            LogExtSourceWebserviceCall(ccdconversionService, "createSource", data);
            object response = ROIHelper.Invoke(ccdconversionService, "createSource", requestParams);
            return (int)response;
        }

        public bool deleteExternalSource(int sourceId)
        {
            object[] requestParams = new object[] { sourceId };
            object response = ROIHelper.Invoke(ccdconversionService, "deleteSource", requestParams);
            return (bool)response;
        }


        /// <summary> 
        /// This method will update the connection properties of external source
        /// </summary>
        /// <param name="data"></param>
        /// <returns>Success/Failure as boolean</returns>
        public bool updateConfigProperties(ExternalSource data)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdateSource(data))
            {
                throw validator.ClientException;
            }
            CcdSourceDto serverext = MapModel(data);
            object[] requestParams = new object[] { serverext };
            LogExtSourceWebserviceCall(ccdconversionService, "updateSourceConfig", data);
            object response = ROIHelper.Invoke(ccdconversionService, "updateSourceConfig", requestParams);
            return (bool)response;
         }
        
        /// <summary>
        /// This method will test the connection of external source
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public bool TestConnection(ExternalSource data)
        {
            CcdSourceDto serverext = MapModel(data);
            object[] requestParams = new object[] { serverext };
            LogExtSourceWebserviceCall(ccdconversionService, "testConfiguration", data);
            object response= ROIHelper.Invoke(ccdconversionService, "testConfiguration", requestParams);
            return (bool)response;
        }
       

        #endregion

        #region ModelMapper

        /// <summary>
       /// Convert server externalsources list to client externalsources list
       /// </summary>
       /// <param name="serverExtsources"></param>
       /// <returns>External Source</returns>
        public static Collection<ExternalSource> MapModel(CcdSourceDto[] serverExtsources)
        {
            if (serverExtsources == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            Collection<ExternalSource> clientexternalsources = new Collection<ExternalSource>();
            ExternalSource clientextsource;
            foreach (CcdSourceDto serverextsources in serverExtsources)
            {
                clientextsource = MapModel(serverextsources);
                clientexternalsources.Add(clientextsource);
            }
            return clientexternalsources;
        }

        /// <summary>
        /// Convert server externalsource(CcdSourceDto)  to client ExternalSource
        /// </summary>
        /// <param name="server"></param>
        /// <returns>External Source</returns>
        public static ExternalSource MapModel(CcdSourceDto server)
        {
            if (server == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            ExternalSource client = new ExternalSource();
            client.SourceId = server.sourceId;
            client.SourceName = server.sourceName;
            client.ProviderName = server.providerName;
            client.Description = server.description;
            if (server.ccdSourceConfigDto != null)
            {
                ConnectionProperty conn;
                for (int index = 0; index <= server.ccdSourceConfigDto.Length - 1; index++)
                {
                    conn=new ConnectionProperty();
                    conn.ConfigKey=server.ccdSourceConfigDto[index].configKey;
                    conn.ConfigValue=server.ccdSourceConfigDto[index].configValue;
                    conn.ConfigLabel = server.ccdSourceConfigDto[index].configLabel;
                    client.ConnectionProperties.Add(conn);
                }
            }
            return client;
        }

        /// <summary>
        /// Convert client ExternalSource to server ExternalSource(CcdSourceDto)
        /// </summary>
        /// <param name="client"></param>
        /// <returns>server ExternalSource(CcdSourceDto)</returns>
        public static CcdSourceDto MapModel(ExternalSource client)
        {
            if (client == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            CcdSourceDto server = new CcdSourceDto();
            server.sourceId = client.SourceId;
            server.sourceName = client.SourceName;
            server.providerName = client.ProviderName;
            server.description = client.Description;
            server.ccdSourceConfigDto = new CcdSourceConfigDto[client.ConnectionProperties.Count];
            for (int i = 0; i < client.ConnectionProperties.Count; i++)
            {
                server.ccdSourceConfigDto[i] = new CcdSourceConfigDto();
                server.ccdSourceConfigDto[i].configKey = client.ConnectionProperties[i].ConfigKey;
                server.ccdSourceConfigDto[i].configValue = client.ConnectionProperties[i].ConfigValue;
                server.ccdSourceConfigDto[i].configLabel = client.ConnectionProperties[i].ConfigLabel;
                            
            }
            return server;
        }

        /// <summary>
        /// Convert client connection properties to server connection properties(CcdSourceConfigDto)
        /// </summary>
        /// <param name="client"></param>
        /// <returns>server connection properties(CcdSourceConfigDto)</returns>
        public static CcdSourceConfigDto MapModel(ConnectionProperty client)
        {
            if (client == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            CcdSourceConfigDto server = new CcdSourceConfigDto();
            server.configKey = client.ConfigKey;
            server.configValue = client.ConfigValue;
            server.configLabel = client.ConfigLabel;
            return server;
        }

        /// <summary>
        /// Convert server connectionProperties(CcdSourceConfigDto)to client connectionProperties
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        public static ConnectionProperty MapModel(CcdSourceConfigDto server)
        {
            if (server == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            ConnectionProperty client = new ConnectionProperty();
            client.ConfigKey = server.configKey;
            client.ConfigValue = server.configValue;
            client.ConfigLabel = server.configLabel;
            return client;
        }

        #endregion

        public void LogExtSourceWebserviceCall(object serviceproxy, string method, object param)
        {
            log.Debug(logMethodName, serviceproxy.GetType().Name, method);
            if (param != null)
            {
                ExternalSource data = param as ExternalSource;
                log.Debug(logParams + data.GetType().Name);
                foreach (PropertyDescriptor descriptor in TypeDescriptor.GetProperties(data))
                {
                    if (((descriptor.GetValue(data)).GetType().Name).Equals(data.ConnectionProperties.GetType().Name)) break;
                    log.Debug("\t      [{0} = {1}]", descriptor.Name, descriptor.GetValue(data));
                }
                ConnectionProperty connectionProperty;
                for (int i = 0; i < data.ConnectionProperties.Count; i++)
                {
                    connectionProperty = new ConnectionProperty();
                    connectionProperty.ConfigKey = data.ConnectionProperties[i].ConfigKey;
                    connectionProperty.ConfigValue = data.ConnectionProperties[i].ConfigValue;
                    foreach (PropertyDescriptor descriptor in TypeDescriptor.GetProperties(connectionProperty))
                    {
                        log.Debug("\t      [{0} = {1}]", descriptor.Name, descriptor.GetValue(connectionProperty));
                    }
                }
            }
        }

        #endregion

    }
}
