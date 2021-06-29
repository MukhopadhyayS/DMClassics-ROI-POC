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
using System.ComponentModel;
using System.Globalization;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.Client.Web_References.HPFPatientWS;
using McK.EIG.ROI.Client.Web_References.ConfigurationWS;

namespace McK.EIG.ROI.Client.Patient.Controller
{   
    public partial class PatientController
    {
        #region Methods
        
        #region Service Methods

        /// <summary>
        /// RetrieveAllDepartments
        /// </summary>
        /// <returns></returns>
        public Collection<string> RetrieveAllDepartments()
        {
            if (configurationService == null)
            {
                configurationService = new ConfigurationServiceWse();
            }
            object response = HPFWHelper.Invoke(configurationService, "getDepartment", PrepareHPFWParams(new Object[0]));
            Collection<string> departments = MapModel((department)response);
            return departments;
        }

        #endregion

        #region Model Mapper

        private static Collection<string> MapModel(department departments)
        {
            Collection<string> departmentList = new Collection<string>();

            foreach (item department in departments.items)
            {
                departmentList.Add(department.value);
            }
            return departmentList;
        }

        #endregion

        #endregion
    }
}
