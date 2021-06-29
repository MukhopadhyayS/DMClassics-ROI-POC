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
using System.Text.RegularExpressions;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminValidator
    {
        #region Fields

        // RegularExpression that validate positive numeric value.
        // Accept only three digit that is number from 1-999 with noninclusive of zero        
        private readonly Regex numericRange = new Regex(@"(?!^*$)^\d{1,3}$");

        #endregion

        #region Methods

        /// <summary>
        /// This method is used to validate mandatory fields of the invoicedue data
        /// </summary>
        /// <returns>This method returns true if all mandatory fields are filled otherwise returns false</returns>
        public bool ValidateInvoiceDue(InvoiceDueDetails invoiceDueDetails)
        {
            ValidatePrimaryFields(invoiceDueDetails);
            return NoErrors;
        }

        /// <summary>
        /// Check the mandatory field.
        /// </summary>
        /// <param name="invoiceDueDetails"></param>
        public void ValidatePrimaryFields(InvoiceDueDetails invoiceDueDetails)
        {
            if (invoiceDueDetails == null)
            {
                AddError(ROIErrorCodes.ArgumentIsNull);
                return;
            }
            if (!numericRange.IsMatch(Convert.ToString(invoiceDueDetails.DueDateInDays, System.Threading.Thread.CurrentThread.CurrentCulture)))
            {
                AddError(ROIErrorCodes.InvoiceDueFormat);
            }
        }

       

        #endregion
    }
}

