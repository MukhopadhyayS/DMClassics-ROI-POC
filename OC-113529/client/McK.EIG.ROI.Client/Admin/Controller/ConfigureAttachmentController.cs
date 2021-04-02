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
using McK.EIG.ROI.Client.Web_References.ConfigurationWS;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {
        #region Methods

        #region ServiceMethods

        public AttachmentLocation RetrieveAttachmentLocation()
        {
            AttachmentLocation attachmentDetails = new AttachmentLocation();
            object response = ROIHelper.Invoke(roiAdminService, "retrieveAttachmentLocation", new object[0]);
            attLocation loc = (attLocation)response;
            attachmentDetails.Id = loc.attachmentID;
            attachmentDetails.Location= loc.attachmentLocation;
            return attachmentDetails;
        }

        /// <summary>
        /// The method will update the Attachment Location details.
        /// </summary>
        /// <param name="weightdetails">AttachmentLocation</param>
        /// <returns>PageWeightDetails</returns>
        public void UpdateAttachmentLocation(AttachmentLocation attachmentDetails)
        {

            attLocation loc = new attLocation();
            loc.attachmentID = attachmentDetails.Id;
            loc.attachmentLocation = attachmentDetails.Location;
            object[] requestParams = new object[] { loc };
            ROIHelper.Invoke(roiAdminService, "updateAttachmentLocation", requestParams);
        }
        #endregion

        #region ModelMapper
        /// <summary>
        /// Convert server weight details to client weight details.
        /// </summary>
        /// <param name="serverMediaType"></param>
        /// <returns></returns>
        //private static PageWeightDetails MapModel(Weight serverWeightDetail)
        //{
        //    PageWeightDetails clientWeightDetails = new PageWeightDetails();
        //    clientWeightDetails.Id              = serverWeightDetail.id;
        //    clientWeightDetails.PageWeight      = serverWeightDetail.unitPerMeasure;
        //    clientWeightDetails.RecordVersionId = serverWeightDetail.recordVersion;
            
        //    return clientWeightDetails;
        //}

        /// <summary>
        /// Convert Client Weight details to server weight details.
        /// </summary>
        /// <param name="serverMediaType"></param>
        /// <returns></returns>
        //private static Weight MapModel(PageWeightDetails clientWeightDetail)
        //{
        //    Weight serverWeightDetails = new Weight();
        //    serverWeightDetails.id = clientWeightDetail.Id;
        //    serverWeightDetails.unitPerMeasure = clientWeightDetail.PageWeight;
        //    serverWeightDetails.recordVersion  = clientWeightDetail.RecordVersionId;
            
        //    return serverWeightDetails;
        //}
        #endregion

        #endregion
    }
}

