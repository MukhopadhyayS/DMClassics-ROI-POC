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

#region NameSpace

using System;
using System.Configuration;
using System.Collections;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Odbc;
using System.IO;
using System.Globalization;
using System.Reflection;
using System.Resources;
using System.Text;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Base.Model;

#endregion

namespace McK.EIG.ROI.DataVault.Base
{
    public static class Utility
    {
        #region Methods

        #region Execute query
        /// <summary>
        /// Query to read Excel or Csv content as DataReader during creation process
        /// </summary>
        /// <param name="dsnName">DSN Name</param>
        /// <param name="fileName">CSV file name.</param>
        /// <returns>DataTable respresent the csv file.</returns>
        public static IDataReader ReadData(string entityName)
        {   
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();

            string conString = string.Empty;
            string query     = string.Empty;

            //Build connection string and sql query.
            if (DataVaultConstants.IsExcelFile)
            {
                conString = "DSN=" + DataVaultConstants.XlsDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ExcelDataQuery,
                                      entityName + "$");
            }
            else
            {
                conString = "DSN=" + DataVaultConstants.CsvDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.CSVDataQuery,
                                      entityName);
            }
            return ExecuteQuery(entityName, conString, query);  
        }

        /// <summary>
        /// Query to read Excel or Csv content as DataReader during updateion process
        /// </summary>
        /// <param name="dsnName">DSN Name</param>
        /// <param name="fileName">CSV file name.</param>
        /// <returns>DataTable respresent the csv file.</returns>
        public static IDataReader ReadData(string entityName, int value)
        {
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();

            string conString = string.Empty;
            string query = string.Empty;

            //Build connection string and sql query.
            if (DataVaultConstants.IsExcelFile)
            {
                conString = "DSN=" + DataVaultConstants.XlsDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ExcelUpdateQuery,
                                      entityName + "$",
                                      value);
            }
            else
            {
                conString = "DSN=" + DataVaultConstants.CsvDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                     DataVaultConstants.CSVUpdateQuery,
                                     entityName,
                                     value);
            }
            return ExecuteQuery(entityName, conString, query);
        }


        /// <summary>
        /// Query CSV content as DataReader
        /// </summary>
        /// <param name="dsnName">DSN Name</param>
        /// <param name="fileName">CSV file name.</param>
        /// <returns>DataTable respresent the csv file.</returns>
        public static IDataReader ReadData(string entityName, string columnName, object condition)
        {
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();

            string conString = string.Empty;
            string query = string.Empty;

            //Build connection string and sql query.
            if (DataVaultConstants.IsExcelFile)
            {
                conString = "DSN=" + DataVaultConstants.XlsDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ExcelSelectQuery,
                                      entityName + "$",
                                      columnName,
                                      condition);
            }
            else
            {
                conString = "DSN=" + DataVaultConstants.CsvDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.CSVSelectQuery,
                                      entityName,
                                      columnName,
                                      condition);
            }
            return ExecuteQuery(entityName, conString, query);            
        }

        /// <summary>
        /// Query CSV content as DataReader
        /// </summary>
        /// <param name="dsnName">DSN Name</param>
        /// <param name="fileName">CSV file name.</param>
        /// <returns>DataTable respresent the csv file.</returns>
        public static IDataReader ReadData(string entityName, string query)
        {
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();

            string conString = string.Empty;

            //Build connection string and sql query.
            if (DataVaultConstants.IsExcelFile)
            {
                conString = "DSN=" + DataVaultConstants.XlsDataSourceName + ";Uid=Admin;Pwd=;";
            }
            else
            {
                conString = "DSN=" + DataVaultConstants.CsvDataSourceName + ";Uid=Admin;Pwd=;";
            }
            return ExecuteQuery(entityName, conString, query);
        }

        #endregion

        #region GetCount
        /// <summary>
        /// Query CSV content as DataReader
        /// </summary>
        /// <param name="dsnName">DSN Name</param>
        /// <param name="fileName">CSV file name.</param>
        /// <returns>DataTable respresent the csv file.</returns>
        public static long GetCount(string entityName, string columnName, object condition)
        {
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();

            string conString = string.Empty;
            string query = string.Empty;

            //Build connection string and sql query.
            if (DataVaultConstants.IsExcelFile)
            {
                conString = "DSN=" + DataVaultConstants.XlsDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ExcelCountQuery,
                                      entityName + "$",
                                      columnName,
                                      condition);
            }
            else
            {
                conString = "DSN=" + DataVaultConstants.CsvDataSourceName + ";Uid=Admin;Pwd=;";
                query = string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.CSVCountQuery,
                                      entityName,
                                      columnName,
                                      condition);
            }
            IDataReader reader = ExecuteQuery(entityName, conString, query);
            reader.Read();
            long count = reader.GetInt64(0);
            reader.Close();

            log.ExitFunction();
            return count;
        }

        #endregion

        private static IDataReader ExecuteQuery(string entityName, string connectionString, string query)
        {
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();

            OdbcConnection connection = new OdbcConnection();
            OdbcCommand selectCmd	  = new OdbcCommand();

            //Query Excel or CSV content as DataReader
            try
            {
                connection.ConnectionString = connectionString;
                connection.Open();
                selectCmd.CommandText = query;
                selectCmd.CommandType = CommandType.Text;
                selectCmd.Connection = connection;

                //CommandBehavior is added to close the connection once the reader is closed.
                OdbcDataReader reader = selectCmd.ExecuteReader(CommandBehavior.CloseConnection);

                log.ExitFunction();
                return reader;
            }
            catch (OdbcException cause)
            {
                log.FunctionFailure(cause);
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.OdbcError, entityName);
                throw new VaultException(message);
            }
            catch (Exception cause)
            {
                log.FunctionFailure(cause);
                throw new ROIException(DataVaultErrorCodes.Unknown);
            }
        }

        #region FileValidation

        public static bool ValidateFiles()
        {
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();
            
            StringBuilder errorMessage = new StringBuilder();
            Collection<string> list = new Collection<string>();

            #region Create File Name

            list.Add(DataVaultConstants.MediaType + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.FeeType + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.PaymentMethod + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.DeliveryMethod + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillingTemplate + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillingTier + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.PageLevelTier + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestReason + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.StatusReason + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.AdjustmentReason + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.DisclosureDocType + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestorTypeGeneral + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestorTypeBT + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.LetterTemplate + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.ConfigureNotes + "_" + VaultMode.Create);

            list.Add(DataVaultConstants.PatientInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.NonHpfDocument + "_" + VaultMode.Create);

            list.Add(DataVaultConstants.RequestorInfo + "_" + VaultMode.Create);

            list.Add(DataVaultConstants.RequestInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestRequorInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestPtsInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestSupDocs + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestItemDetails + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestComment + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.RequestStatusReason + "_" + VaultMode.Create);

            list.Add(DataVaultConstants.BillGeneralInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillDocChargeInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillFeeInfoGeneral + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillFeeChargeInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillShippingInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillAdjInfo + "_" + VaultMode.Create);
            list.Add(DataVaultConstants.BillPayInfo + "_" + VaultMode.Create);

            #endregion

            #region Update File Name

            list.Add(DataVaultConstants.MediaType + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillingTier + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.PageLevelTier + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.FeeType + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillingTemplate + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.RequestorTypeGeneral + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.RequestorTypeBT + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.DeliveryMethod + "_" + VaultMode.Update); 
            list.Add(DataVaultConstants.PaymentMethod + "_" + VaultMode.Update);

            list.Add(DataVaultConstants.PatientInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.NonHpfDocument + "_" + VaultMode.Update);

            list.Add(DataVaultConstants.RequestorInfo + "_" + VaultMode.Update);

            list.Add(DataVaultConstants.RequestInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.RequestRequorInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.RequestPtsInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.RequestSupDocs + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.RequestItemDetails + "_" + VaultMode.Update);
            //list.Add(DataVaultConstants.RequestComment + "_" + Mode.Update);
            list.Add(DataVaultConstants.RequestStatusReason + "_" + VaultMode.Update);

            list.Add(DataVaultConstants.BillGeneralInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillDocChargeInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillFeeInfoGeneral + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillFeeChargeInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillShippingInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillAdjInfo + "_" + VaultMode.Update);
            list.Add(DataVaultConstants.BillPayInfo + "_" + VaultMode.Update);

            #endregion

            if (DataVaultConstants.IsExcelFile)
            {
                ArrayList sheetName = new ArrayList();

                DataTable table = GetSchema(DataVaultConstants.CreateAdminModule);                   
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }              

                table = GetSchema(DataVaultConstants.CreatePatientModule);
                foreach (DataRow row in table.Rows)
                {                    
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.CreateRequestorModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.CreateRequestModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.CreateBillPayModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.UpdateAdminModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.UpdatePatientModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.UpdateRequestorModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.UpdateRequestModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                table = GetSchema(DataVaultConstants.UpdateBillPayModule);
                foreach (DataRow row in table.Rows)
                {
                    sheetName.Add(row["TABLE_NAME"].ToString());
                }

                foreach (string fileName in list)
                {
                    string name = "'"+fileName + "$'";
                    if (!sheetName.Contains(name))
                    {                        
                        if (errorMessage.Length == 0)
                        {
                            errorMessage.AppendLine(DataVaultErrorCodes.ExcelSheetNotFound);
                        }
                        errorMessage.AppendLine(fileName);
                    }
                }
            }
            else
            {   
                foreach (string fileName in list)
                {
                    string path = DataVaultConstants.DataSetPath + "\\" + fileName + ".csv";
                    if (!File.Exists(path))
                    {
                        if (errorMessage.Length == 0)
                        {
                            errorMessage.AppendLine(DataVaultErrorCodes.CSVFileNotFound);
                        }
                        errorMessage.AppendLine(path);
                    }
                }
            }

            if (errorMessage.Length > 0)
            {
                log.Debug(errorMessage.ToString());
                throw new VaultException(errorMessage.ToString());
            }            
            return true;
        }

        private static DataTable GetSchema(string entityName)
        {
            Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));
            log.EnterFunction();
            
            DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, entityName);

            string conString = "DSN=" + DataVaultConstants.XlsDataSourceName + ";Uid=Admin;Pwd=;";
            OdbcConnection connection = new OdbcConnection();
            DataTable table;
            //Query Excel or CSV content as DataReader
            try
            {
                connection.ConnectionString = conString;
                connection.Open();
                table = connection.GetSchema("Tables");
            }
            catch (OdbcException cause)
            {
                log.FunctionFailure(cause);
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.OdbcError, entityName);
                throw new VaultException(message);
            }
            catch (Exception cause)
            {
                log.FunctionFailure(cause);
                throw new ROIException(DataVaultErrorCodes.Unknown);
            }
            finally
            {
                if (connection != null)
                {
                    connection.Close();
                    connection.Dispose();
                }
            }
            log.ExitFunction();
            return table;
        }

        #endregion        

        #region Get Error Message
        /// <summary>
        /// This Method returns the Error Message.
        /// </summary>
        /// <param name="cause">ROIException</param>
        /// <returns>Error Message.</returns>
        public static string GetErrorMessage(ROIException cause)
        {
            StringBuilder messages = new StringBuilder();
            Assembly assembly = Assembly.Load("McK.EIG.ROI.Client");
            ResourceManager rm = new ResourceManager(assembly.GetName().Name + ".Resources.Cultures.roi.messages", assembly);
            Collection<ExceptionData> errorsMessages = cause.GetErrorMessage(rm);
            foreach (ExceptionData error in errorsMessages)
            {
                messages.Append(error.ErrorMessage);
            }
            return messages.ToString();
        }

        #endregion

        #endregion
    }
}
