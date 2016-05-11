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

using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.Common.Utility.Pagination.Controller;
using McK.EIG.Common.Utility.Pagination.Model;

using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.Controller
{
    public class RequestHistoryPageProducer : IProducer
    {
        #region Fields

        private FindRequestCriteria findRequestCriteria;
        private string sortField;
        private bool isDescending;
        private string domainType;
        private PageContent pageContent;
        private bool isPaginationInProgress = true;

        #endregion

        #region Constructor

        public RequestHistoryPageProducer(FindRequestCriteria findRequestInfo)
        {
            findRequestCriteria = findRequestInfo;
        }

        #endregion

        #region IProducer Members

        public PageContent GetPage(int startFrom, int size)
        {
            PaginationDetails paginationInfo = new PaginationDetails();
            paginationInfo.StartFrom = startFrom;
            paginationInfo.Size = size;
            paginationInfo.SortColumn = sortField;
            paginationInfo.IsDescending = isDescending;
            paginationInfo.DomainType = domainType;
            FindRequestResult retrievedRequests = RequestController.Instance.FindRequest(findRequestCriteria, paginationInfo);
            pageContent = new PageContent();
            pageContent.Data = retrievedRequests.RequestSearchResult;
            pageContent.Size = size;
            return pageContent;
        }

        public void Release()
        {
            isPaginationInProgress = false;
        }

        public int TotalSize
        {
            get
            {
                isPaginationInProgress = true;
                return Convert.ToInt32(RequestController.Instance.RetrieveRequestCount(findRequestCriteria));
            }
        }

        #endregion

        #region Methods

        public void SetSortProperties(string sortColumn, string domain, bool isDescending)
        {
            sortField = sortColumn;
            this.isDescending = isDescending;
            domainType = domain;
        }

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to set and get the IsPaginationInProgress.
        /// </summary>
        public bool IsPaginationInProgress
        {
            get { return isPaginationInProgress; }
        }

        #endregion 

    }
}
