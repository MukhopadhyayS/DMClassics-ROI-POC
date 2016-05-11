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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.View.FindPatient;

namespace McK.EIG.ROI.Client.Patient.View.FindPatient
{
    /// <summary>
    /// Holds the Patient Search UI.
    /// </summary>
    public class FindPatientMCP : ROIBasePane
    {
        #region Fields

        private PatientSearchUI patientSearchUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize th Patient Search UI.
        /// </summary>
        protected override void InitView()
        {
            patientSearchUI = new PatientSearchUI();
            patientSearchUI.ApplySecurityRights();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the view of FindPatientMCP.
        /// </summary>        
        public override Component View
        {
            get { return patientSearchUI; }
        }

        #endregion
    }
}
