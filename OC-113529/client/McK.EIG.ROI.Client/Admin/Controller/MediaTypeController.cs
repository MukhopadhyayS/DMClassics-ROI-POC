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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Handles Mediaype service invocations
    /// </summary>
    public partial class BillingAdminController
    {
        #region Methods

        #region Service Methods

        /// <summary>
        /// This method will creates a new mediatype
        /// </summary>
        /// <param name="mediaTypeDetails"> mediatype details which has to be created</param>
        /// <returns>The Newly Created mediatype Id</returns> 
        public long CreateMediaType(MediaTypeDetails mediaTypeDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateCreate(mediaTypeDetails))
            {
                throw validator.ClientException;
            }
            MediaType serverMediaType = MapModel(mediaTypeDetails);
            object[] requestParams = new object[] { serverMediaType };
            object response = ROIHelper.Invoke(billingAdminService, "createMediaType", requestParams);
            long mediaTypeId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);

            return mediaTypeId;
        }

        /// <summary>
        /// This method will update an existing mediaType
        /// </summary>
        /// <param name="mediaTypeDetails">mediatype details which has to be updated.</param>
        /// <returns>Returns updated mediatype details</returns>
        public MediaTypeDetails UpdateMediaType(MediaTypeDetails mediaTypeDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateUpdate(mediaTypeDetails))
            {
                throw validator.ClientException;
            }
            MediaType serverMediaType = MapModel(mediaTypeDetails);
            object[] requestParams = new object[] { serverMediaType };
            ROIHelper.Invoke(billingAdminService, "updateMediaType", requestParams);
            MediaTypeDetails clientMediaTypeDetails = MapModel((MediaType)requestParams[0]);

            return clientMediaTypeDetails;
        }
        
        /// <summary>
        /// Deletes all specified mediatype from the database.
        /// </summary>
        /// <param name="mediaTypeIds">The array of the mediatype ids which has to be deleted</param>
        /// <returns>Returns an array of mediatype ids which were  deleted</returns>
        public void DeleteMediaType(long mediaTypeId)
        {
            object[] requestParams = new object[] { mediaTypeId };
            ROIHelper.Invoke(billingAdminService, "deleteMediaType", requestParams);
        }

        /// <summary>
        /// Returns a list of mediaType for the specified domain, startIndex, count.
        /// </summary>
        /// <param name="startIndex">startIndex</param>
        /// <param name="count">count</param>
        /// <returns>List of MediaTypes</returns>
        public Collection<MediaTypeDetails> RetrieveAllMediaTypes()
        {
            return RetrieveAllMediaTypes(true);
        }

        /// <summary>
        /// Returns a list of mediaType for the specified domain, startIndex, count.
        /// </summary>
        /// <param name="startIndex">startIndex</param>
        /// <param name="count">count</param>
        /// <returns>List of MediaTypes</returns>
        public Collection<MediaTypeDetails> RetrieveAllMediaTypes(bool loadAssociations)
        {
            object response = ROIHelper.Invoke(billingAdminService, "retrieveAllMediaTypes", new object[] { loadAssociations });
            Collection<MediaTypeDetails> mediaTypes = MapModel((MediaType[])response);

            return mediaTypes;
        }

        /// <summary>
        /// Returns mediatype details for the given mediaTypeId.
        /// </summary>
        /// <param name="mediaTypeId">The id of the mediaType which has to be retrived</param>
        /// <returns>Returns a mediaType details </returns>
        public MediaTypeDetails GetMediaType(long mediaTypeId)
        {
            object[] requestParams = new object[] { mediaTypeId };
            object response = ROIHelper.Invoke(billingAdminService, "retrieveMediaType", requestParams);
            MediaTypeDetails clientMediaTypeDetails = MapModel((MediaType)response);

            return clientMediaTypeDetails;
        }
        
        #endregion

        #region Model Mapping

        /// <summary>
        /// Convert server mediatype to client mediatype
        /// </summary>
        /// <param name="serverMediaType"></param>
        /// <returns></returns>
        public static MediaTypeDetails MapModel(MediaType serverMediaType)
        {
            if (serverMediaType == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            MediaTypeDetails clientMediaType = new MediaTypeDetails();
            clientMediaType.Id               = serverMediaType.id;
            clientMediaType.Name             = serverMediaType.name;
            clientMediaType.Description      = serverMediaType.description;
            clientMediaType.IsAssociated     = serverMediaType.isAssociated;
            clientMediaType.RecordVersionId  = serverMediaType.recordVersion;
           
            return clientMediaType;
        }

        /// <summary>
        /// Convert client mediatype to server mediatype
        /// </summary>
        /// <param name="clientMediaType"></param>
        /// <returns></returns>
        public static MediaType MapModel(MediaTypeDetails clientMediaType)
        {
            if (clientMediaType == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            MediaType serverMediaType = new MediaType();
            serverMediaType.id            = clientMediaType.Id;
            serverMediaType.name          = clientMediaType.Name;
            serverMediaType.description   = clientMediaType.Description;
            serverMediaType.recordVersion = clientMediaType.RecordVersionId;

            return serverMediaType;
        }

        /// <summary>
        /// Convert server mediatypelist to client mediatypelist
        /// </summary>
        /// <param name="serverMediaTypes"></param>
        /// <returns></returns>
        public static Collection<MediaTypeDetails> MapModel(MediaType[] serverMediaTypes)
        {
            if (serverMediaTypes == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<MediaTypeDetails> clientMediaTypes = new Collection<MediaTypeDetails>();
            MediaTypeDetails clientMediaType;
            foreach (MediaType serverMediaType in serverMediaTypes)
            {
                clientMediaType = MapModel(serverMediaType);
                clientMediaTypes.Add(clientMediaType);
            }

            return clientMediaTypes;
        }

        #endregion

        #endregion
    }
}

