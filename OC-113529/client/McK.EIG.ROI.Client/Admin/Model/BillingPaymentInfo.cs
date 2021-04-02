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
using System.Collections.Generic;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// This Class is used to hold BillingPaymentInfo
    /// </summary>
    [Serializable]
    public class BillingPaymentInfoDetails
    {
        #region Fields

        private Collection<PaymentMethodDetails> paymentMethodDetails;
        private Collection<BillingTemplateDetails> billingTemplateDetails;
        private Collection<FeeTypeDetails> feeTypeDetails;
        private Collection<DeliveryMethodDetails> deliveryMethodDetails;
        private Collection<ReasonDetails> reasonDetails;
        private List<CountryCodeDetails> countryCodeDetails;
        private RequestorTypeDetails requestorTypeDetails;
        private PageWeightDetails pageWeightDetails;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new BillingTemplate Details instance
        /// </summary>
        public BillingPaymentInfoDetails()
        {
        }

        #endregion

        #region Properties

        public Collection<PaymentMethodDetails> PaymentMethodDetails
        {
            get
            {

                if (paymentMethodDetails == null)
                {
                    paymentMethodDetails = new Collection<PaymentMethodDetails>();
                }
                return paymentMethodDetails;
            }
            set { paymentMethodDetails = value; }
        }

        public Collection<BillingTemplateDetails> BillingTemplateDetails
        {
            get
            {

                if (billingTemplateDetails == null)
                {
                    billingTemplateDetails = new Collection<BillingTemplateDetails>();
                }
                return billingTemplateDetails;
            }
            set { billingTemplateDetails = value; }
        }

        public Collection<FeeTypeDetails> FeeTypeDetails
        {
            get
            {

                if (feeTypeDetails == null)
                {
                    feeTypeDetails = new Collection<FeeTypeDetails>();
                }
                return feeTypeDetails;
            }
            set { feeTypeDetails = value; }
        }


        public Collection<DeliveryMethodDetails> DeliveryMethodDetails
        {
            get
            {

                if (deliveryMethodDetails == null)
                {
                    deliveryMethodDetails = new Collection<DeliveryMethodDetails>();
                }
                return deliveryMethodDetails;
            }
            set { deliveryMethodDetails = value; }
        }

        public Collection<ReasonDetails> ReasonDetails
        {
            get
            {

                if (reasonDetails == null)
                {
                    reasonDetails = new Collection<ReasonDetails>();
                }
                return reasonDetails;
            }
            set { reasonDetails = value; }
        }

        public RequestorTypeDetails RequestorTypeDetails
        {
            get { return requestorTypeDetails; }
            set { requestorTypeDetails = value; }
            
        }

        public List<CountryCodeDetails> CountryCodeDetails
        {
            get
            {

                if (countryCodeDetails == null)
                {
                    countryCodeDetails = new List<CountryCodeDetails>();
                }
                return countryCodeDetails;
            }
            set { countryCodeDetails = value; }
        }

        public PageWeightDetails PageWeightDetails
        {
            get { return pageWeightDetails; }
            set { pageWeightDetails = value; }
        }
        

        #endregion
    }
}
