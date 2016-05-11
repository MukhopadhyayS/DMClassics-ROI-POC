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

using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Validates ROI admin model entity
    /// </summary>
    public partial class ROIAdminValidator : BaseROIValidator
    {
        protected const int DeliveryUrlMaxLength = 256;
        protected const int RequestorTypeNameMaxLength = 256;
        protected const int DeliveryNameMaxLength = 20;

        //Reasons
        protected const int ReasonNameLength         = 256;
        protected const int ReasonDisplayText        = 250;
        protected const int RequestReasonDisplayText = 250;

        //Letter Template
        protected const int LetterTemplateNameMaxLength        = 50;
        protected const int LetterTemplateFileNameMaxLength    = 256;
        protected const int LetterTemplateDescriptionMaxLength = 256;

        //Configure Notes
        protected const int NotesNameMaxLength        = 30;
        protected const int NotesDisplayTextMaxLength = 1000;
    }
}
