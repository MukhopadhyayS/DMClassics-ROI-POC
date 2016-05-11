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
using System.ComponentModel;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{

    public enum RequestorAddressType
    {
        None = 0,

        [Description("Custom")]
        Custom = 1,

        [Description("Main")]
        Main = 2,

        [Description("Alternate")]
        Alternate = 3

    }

    public enum OutputMethod
    {
        None = 0,

        [Description("Print")]
        Print = 1,

        [Description("Save as File")]
        SaveAsFile = 2,

        [Description("Fax")]
        Fax = 3,

        [Description("Email")]
        Email = 4,

        [Description("Disc")]
        Disc = 5
    }

    /// <summary>
    /// Model contains the shipping infomation of
    /// released or unreleased(draft release) request.
    /// </summary>
    [Serializable]
    public class ShippingInfo 
    {
        # region Fields

        public const string OutputMethodKey   = "output-method";
        public const string ReleaseShippedKey = "will-release-shipped";

        public const string ShippingInfoKey     = "shipping-info";
        public const string ShippingChargeKey   = "shipping-charge";
        public const string ShippingMethodKey   = "shipping-method";
        public const string ShippingMethodIdKey = "shipping-method-id";
        public const string ShippingUrlKey      = "shipping-url";
        public const string ShippingWeightKey   = "shipping-weight";
        public const string TrackingNumberKey   = "tracking-number";

        public const string AddressTypeKey = "address-type";
        public const string Address1Key    = "address1";
        public const string Address2Key    = "address2";
        public const string Address3Key    = "address3";
        public const string CityKey        = "city";
        public const string StateKey       = "state";
        public const string PostalCodeKey  = "postalcode";
        
        private AddressDetails shippingAddress;

        private double shippingCharge;

        private string shippingMethod;
        private string shippingWebAddress;
        private string trackingNumber;

        private decimal shippingWeight;

        private RequestorAddressType addressType;

        private OutputMethod outputMethod;

        private bool willReleaseShipped;

        private long shippingMethodId;
        private String nonPrintableAttachmentQueue;
        
        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the Shipping address
        /// </summary>
        public AddressDetails ShippingAddress
        {
            get { return shippingAddress; }
            set { shippingAddress = value; }
        }

        /// <summary>
        /// Gets or sets the shipping charge
        /// </summary>
        public double ShippingCharge
        {
            get { return shippingCharge; }
            set { shippingCharge = value; }
        }

        /// <summary>
        /// Gets or sets the shipping method.
        /// </summary>
        public string ShippingMethod
        {
            get { return shippingMethod; }
            set { shippingMethod = value; }
        }

        /// <summary>
        /// Gets or sets the shipping url.
        /// </summary>
        public string ShippingWebAddress
        {
            get { return shippingWebAddress; }
            set { shippingWebAddress = value; }
        }

        /// <summary>
        /// Gets or sets the address type
        /// </summary>
        public RequestorAddressType AddressType
        {
            get { return addressType; }
            set { addressType = value; }
        }

        /// <summary>
        /// Gets or sets the output method
        /// </summary>
        public OutputMethod OutputMethod
        {
            get { return outputMethod; }
            set { outputMethod = value; }
        }

        /// <summary>
        /// Gets or sets the whether the release be shipped or not
        /// </summary>
        public bool WillReleaseShipped
        {
            get { return willReleaseShipped; }
            set { willReleaseShipped = value; }
        }

        /// <summary>
        /// Gets or sets the tracking number
        /// </summary>
        public string TrackingNumber
        {
            get { return trackingNumber; }
            set { trackingNumber = value; }
        }

        /// <summary>
        /// Gets or sets the shipping weight
        /// </summary>
        public decimal ShippingWeight
        {
            get { return shippingWeight; }
            set { shippingWeight = value; }
        }

        /// <summary>
        /// Gets or sets the shipping method id
        /// </summary>
        public long ShippingMethodId
        {
            get { return shippingMethodId; }
            set { shippingMethodId = value;}
        }

        public string NonPrintableAttachmentQueue
        {
            get { return nonPrintableAttachmentQueue; }
            set { nonPrintableAttachmentQueue = value; }
        }
        #endregion
    }
}
