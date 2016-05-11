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
using System.Linq;
using System.Text;
using System.Windows.Forms;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    public interface IAttachmentDetailUI
    {
        void PrePopulate();
        object GetData(object data);
        void SetData(object data);
        void ClearControls();
        void ShowControls();
        void ValidatePrimaryFields();
        List<Object> UploadAttachment(EventHandler progressHandler);
        bool EnableSaveButton();
        void EnableEvents(EventHandler eventHandler);
        void DisableEvents(EventHandler eventHandler);
        Control GetErrorControl(ExceptionData error);
        
    }
}
