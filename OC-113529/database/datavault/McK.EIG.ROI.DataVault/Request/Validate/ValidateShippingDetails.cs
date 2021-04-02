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
using System.Data;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    public partial class ValidateBillingPayment
    {
        #region Fields

        //Shipping Method Info.
        private const string ShipOutMeth  = "Output_Method";
        private const string ShipRelease  = "Release_Shipped";
        private const string ShipAddType  = "Address_Type";
        private const string ShipAdd1     = "Addr1";
        private const string ShipAdd2     = "Addr2";
        private const string ShipAdd3     = "Addr3";
        private const string ShipcCity    = "City";
        private const string ShipState    = "State";
        private const string ShipZip      = "Zip";
        private const string ShipMeth     = "Shipping_Method";
        private const string ShipTrackNum = "Tracking_Number";
        private const string ShipCharge   = "Shipping_Charges";

        #endregion

        #region Method

        /// <summary>
        /// Get the Release Shipping information from the excel sheet or csv file.
        /// </summary>
        /// <param name="releaseDetail"></param>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private ReleaseDetails GetShippingDetails(ReleaseDetails releaseDetail, string releaseRefId)
        {
            log.EnterFunction();
            long count = ValidateShippingDocument(releaseRefId);
            if (count == 0)
            {
                releaseDetail.ShippingDetails = new ShippingInfo();
            }
            else
            {

                string query;
                string entity = DataVaultConstants.BillShippingInfo + "_" + vaultModeType;
                query = (DataVaultConstants.IsExcelFile)
                         ? "SELECT * FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "'"
                         : "SELECT * FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "'";

                using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillShippingInfo + "_" + vaultModeType, query))
                {
                    while (reader.Read())
                    {
                        ShippingInfo shippingInfo = new ShippingInfo();
                        string shippingAddress = Convert.ToString(reader[ShipAddType], CultureInfo.CurrentCulture);
                        shippingInfo.AddressType = (shippingAddress.Length == 0) ? RequestorAddressType.None : (RequestorAddressType)Enum.Parse(typeof(RequestorAddressType),
                                                                                                               reader[ShipAddType].ToString(),
                                                                                                               true);

                        shippingInfo.OutputMethod = (OutputMethod)Enum.Parse(typeof(OutputMethod),
                                                                             reader[ShipOutMeth].ToString(),
                                                                             true);
                        shippingInfo.WillReleaseShipped = Convert.ToBoolean(reader[ShipRelease], CultureInfo.CurrentCulture);

                        if (shippingInfo.AddressType == RequestorAddressType.Custom
                            && shippingInfo.OutputMethod != OutputMethod.Fax
                            && shippingInfo.WillReleaseShipped)
                        {
                            AddressDetails addressDetail = new AddressDetails();
                            addressDetail.Address1 = Convert.ToString(reader[ShipAdd1],
                                                                            CultureInfo.CurrentCulture);
                            addressDetail.Address2 = Convert.ToString(reader[ShipAdd2],
                                                                            CultureInfo.CurrentCulture);
                            addressDetail.Address3 = Convert.ToString(reader[ShipAdd3],
                                                                            CultureInfo.CurrentCulture);
                            addressDetail.City = Convert.ToString(reader[ShipcCity],
                                                                            CultureInfo.CurrentCulture);
                            addressDetail.State = Convert.ToString(reader[ShipState],
                                                                            CultureInfo.CurrentCulture);
                            addressDetail.PostalCode = Convert.ToString(reader[ShipZip],
                                                                            CultureInfo.CurrentCulture);
                            shippingInfo.ShippingAddress = addressDetail;
                        }
                        else if (shippingInfo.AddressType == RequestorAddressType.Main
                            && shippingInfo.OutputMethod != OutputMethod.Fax
                            && shippingInfo.WillReleaseShipped)
                        {
                            shippingInfo.ShippingAddress = new AddressDetails();
                        }
                        else if (shippingInfo.AddressType == RequestorAddressType.Alternate
                            && shippingInfo.OutputMethod != OutputMethod.Fax
                            && shippingInfo.WillReleaseShipped)
                        {
                            shippingInfo.ShippingAddress = new AddressDetails();
                        }
                        DeliveryMethodDetails deliveryDetails = GetDeliveryMethod(Convert.ToString(reader[ShipMeth], CultureInfo.CurrentCulture));

                        shippingInfo.ShippingMethodId = deliveryDetails.Id;
                        shippingInfo.ShippingMethod = deliveryDetails.Name;
                        shippingInfo.TrackingNumber = Convert.ToString(reader[ShipTrackNum],
                                                                         CultureInfo.CurrentCulture);
                        shippingInfo.ShippingCharge = Convert.ToDouble(reader[ShipCharge],
                                                                         CultureInfo.CurrentCulture);
                        releaseDetail.ShippingDetails = shippingInfo;
                        break;
                    }
                    if (!reader.IsClosed)
                    {
                        reader.Close();
                    }
                }
            }
            log.ExitFunction();
            return releaseDetail;
        }

        private DeliveryMethodDetails GetDeliveryMethod(string deliveryMethodId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);

            string entity = DataVaultConstants.DeliveryMethod + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$] WHERE " + RefId + "= '" + deliveryMethodId + "'"
                            : "SELECT * FROM " + entity + ".csv WHERE " + RefId + "= '" + deliveryMethodId + "'";

            DeliveryMethodDetails deliveryDetails = new DeliveryMethodDetails();
            IDataReader requestReader = Utility.ReadData(entity, query);
            while (requestReader.Read())
            {
                deliveryDetails.Id = 1;
                deliveryDetails.Name = requestReader["Name"].ToString();
                break;
            }
            if (!requestReader.IsClosed)
            {
                requestReader.Close();
            }
            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
                }
            }
            return deliveryDetails;
        }

        

        /// <summary>
        /// Validate the shipping method details.
        /// </summary>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private long ValidateShippingDocument(string releaseRefId)
        {
            log.EnterFunction();
            string query;
            string entity = DataVaultConstants.BillShippingInfo + "_" + vaultModeType;            
            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "'"
                    : "SELECT COUNT(*) FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "'";
            //Validation of the Billing Template sheet.
            IDataReader countReader = Utility.ReadData(DataVaultConstants.BillShippingInfo + "_" + vaultModeType, query);
            countReader.Read();
            long count = countReader.GetInt64(0);
            countReader.Close();
            
            if (count > 1)
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidShippingInfo, releaseRefId);
                errorMessage.AppendLine(message);
            }
            log.ExitFunction();
            return count;
        }

        #endregion
    }
}
