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
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class BillingAdminController
    {
        #region Methods

        #region Service Methods
        /// <summary>
        /// This method will create a new tax rate.
        /// </summary>
        /// <param name="deliveryMethodDetails"></param>
        /// <returns></returns>
        public long CreateTaxPerFacility(TaxPerFacilityDetails taxPerFacilityDetails)
        {            
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateCreate(taxPerFacilityDetails))
            {
                throw validator.ClientException;
            }
            TaxPerFacility serverTaxPerFacility = MapModel(taxPerFacilityDetails);
            object[] requestParams = new object[] { serverTaxPerFacility };
            object response = ROIHelper.Invoke(billingAdminService, "createTaxPerFacility", requestParams);
            long taxPerFacilityId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);

            return taxPerFacilityId;            
        }

        /// <summary>
        /// This method will update an existing tax rate.
        /// </summary>
        /// <param name="deliveryMethodDetails"></param>
        public TaxPerFacilityDetails UpdateTaxPerFacility(TaxPerFacilityDetails taxPerFacilityDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateUpdate(taxPerFacilityDetails))
            {
                throw validator.ClientException;
            }            
            TaxPerFacility serverTaxPerFacility = MapModel(taxPerFacilityDetails);
            object[] requestParams = new object[] { serverTaxPerFacility };
            ROIHelper.Invoke(billingAdminService, "updateTaxPerFacility", requestParams);
            TaxPerFacilityDetails clientTaxPerFacilityDetails = MapModel((TaxPerFacility)requestParams[0]);

            return clientTaxPerFacilityDetails;
            
        }

        /// <summary>
        /// Deletes configured tax rate for a facility from database.
        /// </summary>
        /// <param name="deliveryMethodId"></param>
        public void DeleteTaxPerFacility(long id)
        {
            object[] requestParams = new object[] { id };
            ROIHelper.Invoke(billingAdminService, "deleteTaxPerFacility", requestParams);
        }

        /// <summary>
        /// Returns a list of configured tax rates for each facility.
        /// </summary>
        /// <param name="detailedFetch">true - the fields that required to be fetched, 
        /// false - name and id alone are to be fetched</param>
        /// <returns></returns>
        public Collection<TaxPerFacilityDetails> RetrieveAllTaxPerFacilities()
        {
            object response = ROIHelper.Invoke(billingAdminService, "retrieveAllTaxPerFacilities", new object[0]);
            Collection<TaxPerFacilityDetails> taxRates = MapModel((TaxPerFacility[])response);
            return taxRates;            
        }

        /// <summary>
        /// Returns a list of configured tax rates for each facility for the user.
        /// </summary>
        /// <param name="detailedFetch">true - the fields that required to be fetched, 
        /// false - name and id alone are to be fetched</param>
        /// <returns></returns>

        public Collection<TaxPerFacilityDetails> RetrieveAllTaxPerFacilities(string userName)
        {
            object[] requestParams = new object[] {userName };
            object response = ROIHelper.Invoke(billingAdminService, "retrieveAllTaxPerFacilitiesByUser", requestParams);
            Collection<TaxPerFacilityDetails> taxRates = MapModel((TaxPerFacility[])response);

            return taxRates;

        }


        /// <summary>
        /// Returns tax rate for the given facility.
        /// </summary>
        /// <param name="FacilityCode"></param>
        /// <returns></returns>
        public TaxPerFacilityDetails RetrieveTaxPerFacility(long id)
        {
            object[] requestParams = new object[] { id };
            object response = ROIHelper.Invoke(billingAdminService, "retrieveTaxPerFacility", requestParams);
            TaxPerFacilityDetails clientTaxPerFacilityDetails = MapModel((TaxPerFacility)response);

            return clientTaxPerFacilityDetails;            
        }

        #endregion

        #region Model Mapper

        /// <summary>
        /// Convert Server tax rate to Client tax rate.
        /// </summary>
        /// <param name="serverTaxRate"></param>
        /// <returns></returns>
        public static TaxPerFacilityDetails MapModel(TaxPerFacility serverTaxPerFacility)
        {
            if (serverTaxPerFacility == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            TaxPerFacilityDetails clientTaxPerFacilityDetails = new TaxPerFacilityDetails();
            clientTaxPerFacilityDetails.Id = serverTaxPerFacility.id;
            clientTaxPerFacilityDetails.IsDefault = string.Compare(serverTaxPerFacility.@default, "y", true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0 ? true : false;            
            clientTaxPerFacilityDetails.Description = serverTaxPerFacility.description;
            clientTaxPerFacilityDetails.FacilityCode = serverTaxPerFacility.code.Trim();
            clientTaxPerFacilityDetails.TaxPercentage = Convert.ToDouble(serverTaxPerFacility.taxPercentage.ToString("F", System.Threading.Thread.CurrentThread.CurrentCulture), System.Threading.Thread.CurrentThread.CurrentCulture);
            clientTaxPerFacilityDetails.RecordVersionId = serverTaxPerFacility.recordVersion;
            clientTaxPerFacilityDetails.Name = serverTaxPerFacility.name.Trim();

            return clientTaxPerFacilityDetails;
        }

        ///// <summary>
        ///// Convert client tax rate to server tax rate.
        ///// </summary>
        ///// <param name="clientTaxDetails"></param>
        ///// <returns></returns>
        public static TaxPerFacility MapModel(TaxPerFacilityDetails clientTaxPerFacility)
        {
            if (clientTaxPerFacility == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            TaxPerFacility serverTaxPerFacility = new TaxPerFacility();
            serverTaxPerFacility.id = clientTaxPerFacility.Id;
            serverTaxPerFacility.@default = clientTaxPerFacility.IsDefault ? "Y" : "N";
            serverTaxPerFacility.description = clientTaxPerFacility.Description;
            serverTaxPerFacility.code = clientTaxPerFacility.FacilityCode.Trim();
            serverTaxPerFacility.taxPercentage = (float)clientTaxPerFacility.TaxPercentage;
            serverTaxPerFacility.recordVersion = clientTaxPerFacility.RecordVersionId;
            serverTaxPerFacility.name = clientTaxPerFacility.Name.Trim();

            return serverTaxPerFacility;
        }

        ///// <summary>
        ///// Convert server tax rates list to client tax rates list.
        ///// </summary>
        ///// <param name="serverTaxRatess"></param>
        ///// <returns></returns>
        public static Collection<TaxPerFacilityDetails> MapModel(TaxPerFacility[] serverTaxPerFacilities)
        {
            if (serverTaxPerFacilities == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<TaxPerFacilityDetails> clientTaxPerFacilityDetails = new Collection<TaxPerFacilityDetails>();

            TaxPerFacilityDetails clientTaxRate;
            foreach (TaxPerFacility serverTaxPerFacility in serverTaxPerFacilities)
            {
                if (!string.IsNullOrEmpty(serverTaxPerFacility.name))
                {
                    clientTaxRate = MapModel(serverTaxPerFacility);
                    clientTaxPerFacilityDetails.Add(clientTaxRate);
                }
            }

            return clientTaxPerFacilityDetails;
        }

        #endregion

        #endregion
    }
}
