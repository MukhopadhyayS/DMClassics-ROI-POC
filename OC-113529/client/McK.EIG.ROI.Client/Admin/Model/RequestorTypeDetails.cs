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
using System.Drawing;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// This Class is used to hold RequestorType info
    /// </summary> 
    [Serializable]
    public class RequestorTypeDetails : ROIModel
    {
        #region Fields

        public const string DomainType           = "requestortype";
        public const string NonHpfBillingTierKey = "nonhpf.billingtier";
        public const string HpfBillingTierKey    = "hpf.billingtier";

        //holds the id
        private long id;
        
        //holds the name
        private string name;

        //holds the User mage if user created otherwise seed data image  
        private Image image;

        //holds the record view object model
        private RecordViewDetails recordViewDetails;

        //holds the HPF electronic billing tier object model
        private BillingTierDetails hpfBillingTier;

        //holds the collection of billing template object model
        private Collection<AssociatedBillingTemplate> associatedBillingTemplates;

        private BillingTierDetails nonHPFBillingTier;

        //holds the BillingTemplates comma seprated value
        private string billingTemplateValues;

        private bool isAssociated;        

        private string salesTax;

        private string invoiceOptional;

        public static bool invoiceOptionalFlag;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new RequestorTypeDetails instance
        /// </summary>
        public RequestorTypeDetails() 
        {
        }

        #endregion
        
        #region Properties

        #region Id

        /// <summary>
        /// This property is used to get or sets the requestorType Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set
            {
                id = value;
                image = (id < 0) ? ROIImages.SeedDataImage : ROIImages.UserDataImage;
            }
        }

        #endregion

        #region Name

        /// <summary>
        /// This property is used to get or sets the requestorType Name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        #endregion         

        #region Image

        /// <summary>
        /// This property is used to get or sets image 
        /// </summary>
        public Image Image
        {
            get
            {
                return (image == null) ? ROIImages.UserDataImage
                                        : image; 
            }
            set { image = value; }
        }

        #endregion

        #region RecordViewDetails

        /// <summary>
        /// This property is used to get or sets record view details 
        /// </summary>
        public RecordViewDetails RecordViewDetails
        {
            get { return recordViewDetails;  }
            set { recordViewDetails = value; }
        }

        #endregion

        #region HPFBillingTier

        /// <summary>
        /// This property is used to get or sets HPF billing tier details 
        /// </summary>
        public BillingTierDetails HpfBillingTier
        {
            get { return hpfBillingTier; }
            set { hpfBillingTier = value; }
        }

        #endregion

        #region Non-HPFBillingTier

        /// <summary>
        /// This property is used to get or sets Non-HPF billing tier details 
        /// </summary>
        public BillingTierDetails NonHpfBillingTier
        {
            get { return nonHPFBillingTier; }
            set { nonHPFBillingTier = value; }
        }

        #endregion

        #region SalesTax

        /// <summary>
        /// This property is used to get or sets sales tax for requestor type 
        /// </summary>
        public string SalesTax
        {
            get { return salesTax; }
            set { salesTax = value; }
        }

        #endregion

        #region InvoiceOptional

        /// <summary>
        /// This property is used to get or sets invoice for requestor type 
        /// </summary>
        public string InvoiceOptional
        {
            get {
                if (invoiceOptional == "Yes")
                    invoiceOptionalFlag = false;
                else
                    invoiceOptionalFlag = true;
                return invoiceOptional; }
            set { invoiceOptional = value;
                if (invoiceOptional == "Yes")
                    invoiceOptionalFlag = false;
                else
                    invoiceOptionalFlag = true;
            }
        }
        #endregion

        #region RecordViewName

        public string RecordViewName
        {
            get { return recordViewDetails.Name; }
        }

        #endregion

        #region HPFBillingTierName

        public string HpfBillingTierName
        {
            get { return hpfBillingTier.Name; }
        }

        #endregion

        #region Non-HPFBillingTierName

        public string NonHpfBillingTierName
        {
            get { return nonHPFBillingTier.Name; }
        }

        #endregion

        #region AssociatedBillingTemplates

        /// <summary>
        /// This property is used to get or sets associated BillingTemplate details 
        /// </summary>
        public Collection<AssociatedBillingTemplate> AssociatedBillingTemplates
        {
            get
            {
                if (associatedBillingTemplates == null)
                {
                    associatedBillingTemplates = new Collection<AssociatedBillingTemplate>();
                }
                return associatedBillingTemplates;
            }
        }

        /// <summary>
        /// This property is used to get or sets comma separated BillingTemplate values
        /// </summary>
        public string BillingTemplateValues
        {
            get { return billingTemplateValues; }
            set { billingTemplateValues = value; }
        }

        /// <summary>
        /// If true the type is associated with requestor
        /// </summary>
        public bool IsAssociated
        {
            get { return isAssociated; }
            set { isAssociated = value; }
        }

        #endregion

        #endregion
    }
}
