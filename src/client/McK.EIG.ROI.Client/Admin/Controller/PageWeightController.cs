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

        #region ServiceMethods

        public PageWeightDetails RetrieveWeight()
        {
            object response = ROIHelper.Invoke(roiAdminService, "retrieveWeight", new object[0]);
            PageWeightDetails weightDetails = MapModel((Weight)response);
            return weightDetails;
        }

        /// <summary>
        /// The method will update the Page Weight detials.
        /// </summary>
        /// <param name="weightdetails">PageWeightDetails</param>
        /// <returns>PageWeightDetails</returns>
        public PageWeightDetails UpdateWeight(PageWeightDetails weightDetails)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdate(weightDetails))
            {
                throw validator.ClientException;
            }
            Weight serverWeightDetails = MapModel(weightDetails);            
            object[] requestParams = new object[] { serverWeightDetails };
            ROIHelper.Invoke(roiAdminService, "updateWeight", requestParams);
            PageWeightDetails clientWeightDetails = MapModel((Weight)requestParams[0]);
            return clientWeightDetails;
        }
        #endregion

        #region ModelMapper
        /// <summary>
        /// Convert server weight details to client weight details.
        /// </summary>
        /// <param name="serverMediaType"></param>
        /// <returns></returns>
        private static PageWeightDetails MapModel(Weight serverWeightDetail)
        {
            PageWeightDetails clientWeightDetails = new PageWeightDetails();
            clientWeightDetails.Id              = serverWeightDetail.id;
            clientWeightDetails.PageWeight      = serverWeightDetail.unitPerMeasure;
            clientWeightDetails.RecordVersionId = serverWeightDetail.recordVersion;
            
            return clientWeightDetails;
        }

        /// <summary>
        /// Convert Client Weight details to server weight details.
        /// </summary>
        /// <param name="serverMediaType"></param>
        /// <returns></returns>
        private static Weight MapModel(PageWeightDetails clientWeightDetail)
        {
            Weight serverWeightDetails = new Weight();
            serverWeightDetails.id = clientWeightDetail.Id;
            serverWeightDetails.unitPerMeasure = clientWeightDetail.PageWeight;
            serverWeightDetails.recordVersion  = clientWeightDetail.RecordVersionId;
            
            return serverWeightDetails;
        }
        #endregion

        #endregion
    }
}
