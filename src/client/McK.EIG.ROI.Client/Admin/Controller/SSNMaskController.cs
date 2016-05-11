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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {
        #region Methods

        public SSNMaskDetails RetrieveSsnMask()
        {
            object response = ROIHelper.Invoke(roiAdminService, "retrieveSSNMask", new object[0]);
            SSNMaskDetails maskDetails = MapModel((SSNMask)response);
            return maskDetails;
        }

        /// <summary>
        /// The method will update th Mask Details.
        /// </summary>
        /// <param name="MaskDetails">MaskDetails</param>
        /// <returns>MaskDetails</returns>
        public SSNMaskDetails UpdateSsnMask(SSNMaskDetails maskDetails)
        {
            SSNMask serverSSNMask = MapModel(maskDetails);
            object[] requestParams = new object[] { serverSSNMask };
            ROIHelper.Invoke(roiAdminService, "updateSSNMask", requestParams);
            return MapModel((SSNMask)requestParams[0]);
        }

        #endregion

        #region ModelMapper
        /// <summary>
        /// Convert server mask details to client mask details.
        /// </summary>
        /// <param name="serverMask"></param>
        /// <returns></returns>
        public static SSNMaskDetails MapModel(SSNMask serverSsnMask)
        {
            SSNMaskDetails clientMaskDetail = new SSNMaskDetails();            
            clientMaskDetail.IsMasking = serverSsnMask.applySSNMask;
            clientMaskDetail.RecordVersionId = serverSsnMask.recordVersion;

            return clientMaskDetail;
        }

        /// <summary>
        /// Convert client mask details to server mask details.
        /// </summary>
        /// <param name="clientMask"></param>
        /// <returns></returns>
        public static SSNMask MapModel(SSNMaskDetails clientMaskDetail)
        {
            SSNMask serverSSNMask = new SSNMask();
            serverSSNMask.applySSNMask = clientMaskDetail.IsMasking;
            serverSSNMask.recordVersion = clientMaskDetail.RecordVersionId;

            return serverSSNMask;
        }
        #endregion
    }
}
