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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// This Class is used to hold BillingTier info
    /// </summary>
    [Serializable]
    public class BillingTierDetails : ROIModel
    {
        [Serializable]
        private class BillingTierSorter : IComparer<BillingTierDetails>
        {
            #region IComparer<BillingTierDetails> Members

            public int Compare(BillingTierDetails x, BillingTierDetails y)
            {
                return x.Name.CompareTo(y.Name);
            }

            #endregion
        }

        #region Fields
       
        private long id;
        private string name;
        private string desc;
        private string salesTax;
        private bool isAssociated;
        private double baseCharge;
        private Image image;
        private MediaTypeDetails mediatype;
        private Collection<PageLevelTierDetails> pageTiers;
        private double otherPagesCharge;
        private static BillingTierSorter sorter;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new BillingTierDetails instance
        /// </summary>
        public BillingTierDetails()
        {
        }

        #endregion
      
        #region Properties

        #region Id

        /// <summary>
        /// This property is used to get or sets the BillingTier Id.
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
        /// This property is used to get or sets the BillingTier Name
        /// </summary>
        public string Name
        {
            get { return (name == null) ? string.Empty : name; }
            set { name = value; }
        }

        #endregion

        #region Description

        /// <summary>
        /// This property is used to get or sets the BillingTier Description.
        /// </summary>
        public string Description
        {
            get { return CarriageReturn(desc ?? string.Empty); }
            set { desc = value; }
        }
		
		/// <summary>
        /// This property is used to get or sets the BillingTier sales tax.
        /// </summary>
        public string SalesTax
        {
            get { return salesTax; }
            set { salesTax = value; }
        }

        #endregion

        #region IsAssociated

        /// <summary>
        /// This property is used to get or sets isAssociated.
        /// </summary>
        public bool IsAssociated
        {
            get { return isAssociated; }
            set { isAssociated = value; }
        }

        #endregion

        #region Image

        /// <summary>
        /// This property is used to get or sets isAssociated.
        /// </summary>
        public Image Image
        {
            get
            {
                return (image == null) ? ROIImages.UserDataImage : image;
            }
            set { image = value; }
        }

        #endregion
        
        #region BaseCharge
        /// <summary>
        /// This property is used to gets the Base charge
        /// </summary>
        public double BaseCharge
        {
            get { return baseCharge; }
            set { baseCharge = value; }
        }
        #endregion

        #region OtherPageCharge
        /// <summary>
        /// This property is used to gets the Other charge
        /// </summary>
        public double OtherPageCharge
        {
            get { return otherPagesCharge; }
            set { otherPagesCharge = value; }
        }

        #endregion

        #region MediaType
        public MediaTypeDetails MediaType
        {
            get { return mediatype; }
            set { mediatype = value; }
        }
        #endregion

        #region MediaTypeName

        public string MediaTypeName
        {
            get { return mediatype.Name; }
        }

        public static IComparer<BillingTierDetails> Sorter
        {
            get
            {
                if (sorter == null)
                {
                    sorter = new BillingTierSorter();
                }
                return sorter;
            }
        }

        #endregion

        #region PageTiers
        public Collection<PageLevelTierDetails> PageTiers
        {
            get 
            {
                if (pageTiers == null)
                {
                    pageTiers = new Collection<PageLevelTierDetails>();
                }
                return pageTiers;
            }
        }
        #endregion

       #endregion
    }
}
