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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {
        #region Fields

        private const string RequestStatusKey = "RequestStatus";

        #endregion

        #region Methods

        /// <summary>
        /// This method will create a new reason.
        /// </summary>
        /// <param name="reasonDetails"></param>
        /// <returns></returns>
        public long CreateReason(ReasonDetails reasonDetails)
        {             
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateCreate(reasonDetails))
            {
                throw validator.ClientException;
            }
            Reason serverReason = MapModel(reasonDetails);
            object[] requestParams = new object[] { serverReason };
            object response = ROIHelper.Invoke(roiAdminService, "createReason", requestParams);
            long requestReasonId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);
            
            return requestReasonId;
        }

        /// <summary>
        /// This method will update the existing reason.
        /// </summary>
        /// <param name="reasonDetails"></param>
        /// <returns></returns>
        public ReasonDetails UpdateReason(ReasonDetails reasonDetails)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdate(reasonDetails))
            {
                throw validator.ClientException;
            }
            Reason serverReason = MapModel(reasonDetails);
            object[] requestParams = new object[] { serverReason };
            ROIHelper.Invoke(roiAdminService, "updateReason", requestParams);
            //It retruns the value in the serverReason object itself.
            ReasonDetails clientReasonDetails = MapModel((Reason)requestParams[0]);
            
            return clientReasonDetails; 
        }

        /// <summary>
        /// Deletes the specified reason.
        /// </summary>
        /// <param name="reasonId"></param>
        public void DeleteReason(long reasonId)
        {   
            object[] requestParams = new object[] { reasonId };
            ROIHelper.Invoke(roiAdminService, "deleteReason", requestParams);
        }

        /// <summary>
        /// Returns a list of reasons.
        /// </summary>
        /// <returns></returns>
        public Collection<ReasonDetails> RetrieveAllReasons(ReasonType type)
        {   
            object response = ROIHelper.Invoke(roiAdminService, "retrieveAllReasonsByType", new object[] { type.ToString() });
            Collection<ReasonDetails> reasonsDetails = MapModel((Reason[])response);            
            return reasonsDetails;

        }

        public string[] RetrieveReasonsByStatus(int statusId)
        {
            object response = ROIHelper.Invoke(roiAdminService, "retrieveReasonsByStatus", new object[] { statusId });
            return (string[])response;
        }

        /// <summary>
        /// Returns reasondetails for the given reasonid.
        /// </summary>
        /// <param name="reasonId"></param>
        /// <returns></returns>
        public ReasonDetails GetReason(long reasonId)
        {
            object[] requestParams = new object[] { reasonId };
            object response = ROIHelper.Invoke(roiAdminService, "retrieveReason", requestParams);
            ReasonDetails clientReasonDetails = MapModel((Reason)response);

            return clientReasonDetails;
        }

        #endregion

        #region Model Mapping

        /// <summary>
        /// Convert server reasons to client reasons.
        /// </summary>
        /// <param name="serverReason"></param>
        /// <returns></returns>
        private static ReasonDetails MapModel(Reason serverReason)
        {
            ReasonDetails clientReasons   = new ReasonDetails();
            clientReasons.Id              = serverReason.id;
            clientReasons.Name            = serverReason.name;
            clientReasons.DisplayText     = serverReason.displayText;
            clientReasons.RecordVersionId = serverReason.recordVersion;
            clientReasons.Type            = (ReasonType)Enum.Parse(typeof(ReasonType),
                                                                   serverReason.type, true);
            
            if (clientReasons.Type == ReasonType.Status)
            {
                clientReasons.RequestStatus = (RequestStatus)serverReason.status;
            }
            else if (clientReasons.Type == ReasonType.Request)
            {
                if (serverReason.tpo)
                {
                    clientReasons.Attribute = RequestAttr.Tpo;
                }
                else if (serverReason.nonTpo)
                {
                    clientReasons.Attribute = RequestAttr.NonTpo;
                }
            }
            return clientReasons;
        }

        /// <summary>
        /// Convert client reasons to server reasons.
        /// </summary>
        /// <param name="clientReason"></param>
        /// <returns></returns>
        private static Reason MapModel(ReasonDetails clientReason)
        {
            Reason serverReason = new Reason();
            
            serverReason.id            = clientReason.Id;
            serverReason.name          = clientReason.Name;
            serverReason.displayText   = clientReason.DisplayText;
            serverReason.recordVersion = clientReason.RecordVersionId;
            serverReason.type          = clientReason.Type.ToString();
            
            if (clientReason.Type == ReasonType.Status)
            {
                serverReason.status = Convert.ToInt32(clientReason.RequestStatus, System.Threading.Thread.CurrentThread.CurrentCulture);
            }
            else if (clientReason.Type == ReasonType.Request)
            {
                switch (clientReason.Attribute)
                {
                    case RequestAttr.Tpo:
                        serverReason.tpo = true;
                        break;
                    case RequestAttr.NonTpo:
                        serverReason.nonTpo = true;
                        break;
                }
            }

            return serverReason;
        }

        /// <summary>
        /// Convert server reasons list to client reasons list.
        /// </summary>
        /// <param name="serverReasons"></param>
        /// <returns></returns>
        public static Collection<ReasonDetails> MapModel(Reason[] serverReasons)
        {
            if (serverReasons == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<ReasonDetails> clientReasons = new Collection<ReasonDetails>();
            foreach (Reason serverReason in serverReasons)
            {
                clientReasons.Add(MapModel(serverReason));
            }

            return clientReasons;
        }

        #endregion

    }
}
