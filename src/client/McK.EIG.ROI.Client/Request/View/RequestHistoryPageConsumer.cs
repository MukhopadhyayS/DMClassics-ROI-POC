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

using McK.EIG.Common.Utility.Pagination.View;
using McK.EIG.Common.Utility.Pagination.Model;
using McK.EIG.Common.Utility.Pagination.Controller;

using McK.EIG.ROI.Client.Request.Model;

using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Request.View
{
    public class RequestHistoryPageConsumer : AbstractAutoConsumer
    {
        #region Fields

        private RequestsListUI listUI;
        delegate void ConsumePageCallback(Collection<RequestDetails> requests);

        #endregion

        #region Constructor

        public RequestHistoryPageConsumer(RequestsListUI requestHistoryListUI, IProducer pageProducer, int pageSize, long freq)
            : base(pageProducer, pageSize, freq)
        {
            listUI = requestHistoryListUI;
            
        }

        #endregion

        #region Methods

        public override void SetPage(PageContent page)
        {
            if (page == null || page.Data == null)
            {
                return;
            }
            if (listUI.InvokeRequired)
            {
                ConsumePageCallback callBackHandler = new ConsumePageCallback(listUI.AddHistories);
                listUI.Invoke(callBackHandler, new object[]{(Collection<RequestDetails>)page.Data});
            }
            else
            {
                listUI.AddHistories(page.Data as Collection<RequestDetails>);
            }
        }

        #endregion
    }
}
