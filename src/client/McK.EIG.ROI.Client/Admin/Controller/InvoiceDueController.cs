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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {
        #region Methods

        #region ServiceMethods

        public InvoiceDueDetails RetrieveInvoiceDueDays()
        {
            object response = ROIHelper.Invoke(roiAdminService, "retrieveInvoiceDue", new object[0]);
            InvoiceDueDetails dayDetails = MapModel((InvoiceDue)response);
            return dayDetails;
        }

        /// <summary>
        /// The method will update the invoice due detials.
        /// </summary>
        /// <param name="dueDetails"></param>
        /// <returns></returns>
        public InvoiceDueDetails UpdateInvoiceDueDays(InvoiceDueDetails dueDetails)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (dueDetails.IsCustomDue)
            {
                if (!validator.ValidateInvoiceDue(dueDetails))
                {
                    throw validator.ClientException;
                }
            }            
            InvoiceDue serverInvoiceDue = MapModel(dueDetails);            
            object[] requestParams = new object[] { serverInvoiceDue };
            ROIHelper.Invoke(roiAdminService, "updateInvoiceDue", requestParams);
            InvoiceDueDetails clientInvoiceDue = MapModel((InvoiceDue)requestParams[0]);
            return clientInvoiceDue;
        }
        #endregion

        #region ModelMapper
        /// <summary>
        /// Convert server invoicedue details to client invoicedue details.
        /// </summary>
        /// <param name="serverMediaType"></param>
        /// <returns></returns>
        private static InvoiceDueDetails MapModel(InvoiceDue serverDueDetails)
        {
            InvoiceDueDetails clientDueDetails = new InvoiceDueDetails();
            clientDueDetails.Id = serverDueDetails.id;
            clientDueDetails.DueDateInDays = serverDueDetails.invoiceDueDays;
            clientDueDetails.RecordVersionId = serverDueDetails.recordVersion;
            clientDueDetails.IsCustomDue = serverDueDetails.customDate;
            
            return clientDueDetails;
        }

        /// <summary>
        /// Convert Client invoicedue details to server invoicedue details.
        /// </summary>
        /// <param name="serverMediaType"></param>
        /// <returns></returns>
        private static InvoiceDue MapModel(InvoiceDueDetails clientDueDetails)
        {
            InvoiceDue serverDueDetails = new InvoiceDue();
            serverDueDetails.id = clientDueDetails.Id;
            serverDueDetails.invoiceDueDays = clientDueDetails.DueDateInDays;
            serverDueDetails.recordVersion = clientDueDetails.RecordVersionId;
            serverDueDetails.customDate = clientDueDetails.IsCustomDue;
            
            return serverDueDetails;
        }
        #endregion
        #endregion
    }
}
