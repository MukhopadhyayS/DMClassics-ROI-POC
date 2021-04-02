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
using System.Collections;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.SalesTaxPerFacility
{
    /// <summary>
    /// Holds the UI of SalesTaxPerFacility
    /// </summary>
    public class SalesTaxPerFacilityODP : AdminBaseODP
    {
        #region Methods

        /// <summary>
        /// Create new tax rate for a facility
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object CreateEntity(ROIModel model)
        {
            TaxPerFacilityDetails taxRateDetails = model as TaxPerFacilityDetails;
            taxRateDetails.Id = BillingAdminController.Instance.CreateTaxPerFacility(taxRateDetails);            
            return taxRateDetails;
        }

        /// <summary>
        /// Update the tax rate for a selected facility
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object UpdateEntity(ROIModel model)
        {
            TaxPerFacilityDetails taxRateDetails = BillingAdminController.Instance.UpdateTaxPerFacility(model as TaxPerFacilityDetails);            
            return taxRateDetails;            
        }

        /// <summary>
        /// Returns Sales Tax Per Facility ODP UI
        /// </summary>
        protected override IAdminBaseTabUI CreateEntityUI()
        {
            return new SalesTaxPerFacilityTabUI();
        }

        /// <summary>
        /// Load the data into Facility combobox.
        /// </summary>
        public void PrePopulateFacilities()
        {
            ROIViewUtility.MarkBusy(true);            
            try
            {
                ((View as AdminBaseObjectDetailsUI).EntityTabUI as SalesTaxPerFacilityTabUI).PopulateFacilities();

            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

        }

        #endregion
    }
}
