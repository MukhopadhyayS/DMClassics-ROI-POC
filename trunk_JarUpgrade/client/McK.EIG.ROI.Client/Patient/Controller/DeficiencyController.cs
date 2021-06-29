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
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Web_References.DeficiencyWS;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    public partial class PatientController
    {
        #region Method

        #region Service Method

        /// <summary>
        /// Retrive Deficiency Details for the specified encounter and facility.
        /// </summary>
        /// <param name="encounter">encounter</param>
        /// <param name="facility">facility</param>
        /// <returns></returns>
        public Collection<DeficiencyDetails> RetrieveDeficiencyDetails(string encounter, string facility)
        {
            object[] requestParams = new object[] { encounter, facility };
            if (deficiencyService == null)
            {
                deficiencyService = new DeficiencyServiceWse();
            }
            object response = HPFWHelper.Invoke(deficiencyService, "getDeficienciesByEncounter", PrepareHPFWParams(requestParams));
            return MapModel((encounter)response);
        }

        #endregion

        #region MapModel

        /// <summary>
        /// Coverts server deficiencyList to client deficiencydetails collection.
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        private static Collection<DeficiencyDetails> MapModel(encounter server)
        {            
            Collection<DeficiencyDetails> deficiencyList = new Collection<DeficiencyDetails>();
            foreach (deficiency deficiencyInfo in server.deficiencyList)
            {
                deficiencyList.Add(MapModel(deficiencyInfo));
            }
            return deficiencyList;
        }

        /// <summary>
        /// Coverts server deficiency to client DeficiencyDetails.
        /// </summary>
        /// <param name="deficiencyInfo"></param>
        /// <returns></returns>        
        private static DeficiencyDetails MapModel(deficiency deficiencyInfo)
        {
            DeficiencyDetails details = new DeficiencyDetails();
            details.Document  = deficiencyInfo.document;
            details.LockedBy   = deficiencyInfo.lockedBy;
            details.Physician = deficiencyInfo.physician;
            details.Reason    = deficiencyInfo.reason;
            details.Status    = deficiencyInfo.status;
            details.Type      = deficiencyInfo.type;           
            return details;
        }

        #endregion

        #endregion
    }
}
