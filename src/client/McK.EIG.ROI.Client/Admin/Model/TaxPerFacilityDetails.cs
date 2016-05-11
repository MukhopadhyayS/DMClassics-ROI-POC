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
using System.Collections.Generic;
using System.Drawing;
using System.Globalization;
using System.IO;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.Model
{

    /// <summary>
    /// This Class is used to hold tax details of each facility
    /// </summary>
    [Serializable]
    public class TaxPerFacilityDetails : FacilityDetails 
    {

        #region Fields

        private string desc;
        private Image image;

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the code of a faciliy
        /// </summary>
        public string FacilityCode
        {
            get { return base.Code; }
            set { base.Code = value; }
        }

        ///<summary>
        ///This property is used to get or sets whether the configured facility is default or not
        /// </summary>
        public bool IsDefault
        {
            get { return base.IsDefault; }
            set 
            {
                base.IsDefault = value;
                image = (base.IsDefault) ? ROIImages.DefaultImage : null;
            }
        }

        /// <summary>
        /// This property is used to get or sets the description of the configured facility
        /// </summary>
        public string Description
        {
            get { return CarriageReturn(desc ?? string.Empty); }
            set { desc = value; }
        }

        /// <summary>
        /// This property is used to get or sets the tax percentage of a facility
        /// </summary>
        public double TaxPercentage        
        {
            get { return base.TaxPercentage; }
            set { base.TaxPercentage = value; }
        }

        /// <summary>
        /// This property is used to get or sets isAssociated.
        /// </summary>
        public Image Image
        {
            get { return image; }
            set { image = value; }
        }

        /// <summary>
        /// This property is used to get or sets the facility name
        /// </summary>
        public string FacilityName
        {
            get { return base.Name; }
            set { base.Name = value; }
        }

        #endregion
    }
}
