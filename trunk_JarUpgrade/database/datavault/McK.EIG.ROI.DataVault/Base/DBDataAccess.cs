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
using System.Data;
using System.Data.Common;
using McK.EIG.Common.Utility.Logging;

namespace McK.EIG.ROI.DataVault.Base
{
    public static class DBDataAccess
    {
        #region Fields

        private static string Procedure       = "Procedure Name :";
        private static string ParameterName   = "Parameter Name :";
        private static string ParameterValue  = "Parameter Value :";

        #endregion

        #region Methods

        /// <summary>
        /// Execute the stored Procedure.
        /// </summary>
        /// <param name="procedureName">Stored Procedure Name</param>
        /// <param name="parameter">Parameters</param>
        /// <returns>IDataReader</returns>
        public static IDataReader ExecuteStoredProcedure(string procedureName, IDbDataParameter[] parameter)
        {
            Log log = LogFactory.GetLogger(typeof(DBDataAccess));
            log.EnterFunction();

            log.Debug(Procedure + procedureName);
            foreach (IDbDataParameter param in parameter)
            {
                log.Debug(ParameterName + param.ParameterName);
                log.Debug(ParameterValue + param.Value);
            }

            DbConnection connection = null;
            try
            {
                connection = GetConnection();

                DbCommand command   = connection.CreateCommand();
                command.CommandTimeout = 3600;
                command.Connection     = connection;
                command.CommandType    = CommandType.StoredProcedure;                
                command.CommandText    = procedureName;
                command.Parameters.AddRange(parameter);                
                DbDataReader reader = command.ExecuteReader(CommandBehavior.CloseConnection);
                return reader;
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message, cause);
            }
        }

        /// <summary>
        /// Get the DbConnection.
        /// </summary>
        /// <returns>DbConnection</returns>
        private static DbConnection GetConnection()
        {
            DbProviderFactory factory = DbProviderFactories.GetFactory(DataVaultConstants.ProviderName);
            DbConnection connection = factory.CreateConnection();
            connection.ConnectionString = DataVaultConstants.ConnectionString;
            connection.Open();            
            return connection;
        }

        public static DbParameter CreateParameter(string parameterName, DbType type, int parameterSize)
        {
            DbProviderFactory factory = DbProviderFactories.GetFactory(DataVaultConstants.ProviderName);
            DbParameter param = factory.CreateParameter();
            param.ParameterName = parameterName;
            param.Size = parameterSize;
            param.DbType = type;
            return param;
        }

        #endregion
    }
}
