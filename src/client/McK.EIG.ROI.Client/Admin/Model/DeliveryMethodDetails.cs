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
using System.Drawing;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.Model
{
    /// <summary>
    /// This class is used to hold delivery method details.
    /// </summary>
    [Serializable]
    public class DeliveryMethodDetails : ROIModel
    {
        #region Fields

        private long id;
        private string name;
        private string desc;
        private Uri url;
        private bool isDefault;
        private Image image;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new DeliveryMethodDetails instance
        /// </summary>
        public DeliveryMethodDetails()
        { 
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the DeliveryMethod Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// This property is used to get or sets the DeliveryMethod Name
        /// </summary>
        public string Name
        {
            get { return ( name == null ) ? string.Empty : name; }
            set { name = value; }
        }

        /// <summary>
        /// This property is used to get or sets the DeliveryMethod Description.
        /// </summary>
        public string Description
        {
            get { return CarriageReturn(desc ?? string.Empty); }
            set { desc = value; }
        }

        /// <summary>
        /// This property is used to get or sets the DeliveryMethod URL.
        /// </summary>
        public Uri Url
        {
            get { return url; }
            set { url = value; }
        }

        /// <summary>
        /// This property is used to get or sets status.
        /// </summary>
        public bool IsDefault
        {
            get { return isDefault; }
            set {
                isDefault = value;
                image = (isDefault) ? ROIImages.DefaultImage : null;
            }
        }
      
        /// <summary>
        /// This property is used to get or sets isAssociated.
        /// </summary>
        public Image Image
        {
            get { return image; }
            set { image = value; }
        }

        #endregion
    }
}
