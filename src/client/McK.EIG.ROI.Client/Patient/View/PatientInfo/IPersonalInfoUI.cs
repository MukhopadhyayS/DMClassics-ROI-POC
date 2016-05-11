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
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    public interface IPersonalInfoUI : IBaseUI
    {
        #region Methods
        
        void PrePopulate();

        void ClearData();
        void SetData(object data);
        object GetData(object appendTo);

        void EnableEvents();
        void DisableEvents();
        Control GetErrorControl(ExceptionData error);
        
        #endregion

        #region Properties

        /// <summary>
        /// Returns patient name entered in the textbox
        /// </summary>
        string PatientFirstName { get; }
        string PatientLastName { get; }

        #endregion


    }
}
