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

using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Patient.Model
{
    [Serializable]
    public class DocumentSubtitle
    {

        #region Fields

        private string name;
        private Color textColor;
        
        #endregion

        #region Constructor

        public DocumentSubtitle() 
        {
            textColor = Color.Black;
        }

        #endregion

        #region Properties

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public static System.Drawing.Image Icon
        {
            get { return ROIImages.DocumentIcon; }
        }

        public Color TextColor
        {
            get { return textColor; }
            set { textColor = value; }
        }

        #endregion
    }
}
