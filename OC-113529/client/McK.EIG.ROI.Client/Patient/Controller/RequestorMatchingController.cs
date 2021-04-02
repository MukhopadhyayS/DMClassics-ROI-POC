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

using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.Controller;

using McK.EIG.ROI.Client.Web_References.ROIRequestorWS;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    public partial class PatientController
    {
        /// <summary>
        /// Retrieves matching requestors for selected patients.
        /// </summary>
        /// <param name="patients"></param>
        /// <returns></returns>
        public Collection<RequestorDetails> RetrieveMatchingRequestors(Collection<PatientDetails> patients)
        {
            MatchCriteria[] serverMatchCriteria = MapModel(patients);
            object[] requestParams = new object[] { serverMatchCriteria };
            if (requestorService == null)
            {
                requestorService = new RequestorServiceWse();
            }
            object response = ROIHelper.Invoke(requestorService, "searchMatchingRequestors", requestParams);
            return MapModel(((RequestorSearchResult)response).searchResults);
        }

        /// <summary>
        /// Converts server requestors to client requestors.
        /// </summary>
        /// <param name="serverRequestors"></param>
        /// <returns></returns>
        private static Collection<RequestorDetails> MapModel(Requestor[] serverRequestors)
        {
            Collection<RequestorDetails> clientRequestors = new Collection<RequestorDetails>();

            if (serverRequestors != null)
            {
                foreach (Requestor server in serverRequestors)
                {
                    clientRequestors.Add(RequestorController.MapModel(server));
                }
            }

            return clientRequestors;
        }

        /// <summary>
        /// Creates collection of patient match criteria for server.
        /// </summary>
        /// <param name="patients"></param>
        /// <returns></returns>
        private static MatchCriteria[] MapModel(Collection<PatientDetails> patients)
        {
            MatchCriteria[] serverMatchCriteria = new MatchCriteria[patients.Count];
           
            for (int index = 0 ; index < patients.Count; index++)
            {
                serverMatchCriteria[index] = new MatchCriteria();

                serverMatchCriteria[index].epn  = patients[index].EPN;
                serverMatchCriteria[index].lastName = patients[index].LastName;
                serverMatchCriteria[index].firstName = patients[index].FirstName;
                serverMatchCriteria[index].ssn  = patients[index].SSN;
                if (patients[index].DOB.HasValue)
                {
                    serverMatchCriteria[index].dob          = patients[index].DOB.Value;
                    serverMatchCriteria[index].dobSpecified = true;
                }
                serverMatchCriteria[index].mrn      = patients[index].MRN;
                serverMatchCriteria[index].facility = patients[index].FacilityCode;
            }
            return serverMatchCriteria;
        }
    }
}
