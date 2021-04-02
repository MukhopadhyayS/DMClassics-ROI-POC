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
    /// This Class is used to hold mediaType info
    /// </summary>
    [Serializable]
    public class MediaTypeDetails : ROIModel
    {
        #region Fields

        //holds the id
        private long id;

        //holds the name
        private string name;

        //holds the desc
        private string desc;

        //holds the isAssociated flag
        private bool isAssociated;

        //holda the User mage if user created otherwise seed data image  
        private Image image;

        #endregion

        #region Constructors

        /// <summary>
        /// Create an new MediaTypeDetails instance
        /// </summary>
        public MediaTypeDetails() 
        {
        }

        #endregion

        #region Properties

        #region Id

        /// <summary>
        /// This property is used to get or sets the mediaType Id.
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
        /// This property is used to get or sets the mediaType Name
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        #endregion

        #region Description

        /// <summary>
        /// This property is used to get or sets the mediaType Description.
        /// </summary>
        public string Description
        {
            get { return CarriageReturn(desc ?? string.Empty); }
            set { desc = value; }
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
                return (image == null) ? ROIImages.UserDataImage
                                       : image; 
            }
            set { image = value; }
        }

        #endregion

        #endregion
    }
}
