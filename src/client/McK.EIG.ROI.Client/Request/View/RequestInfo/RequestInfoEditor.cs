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
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    public class RequestInfoEditor : ROIEditor
    {
        #region Fields

        private CreateRequestInfo createRequestInfo;
        private RequestDetails request;
        
        #endregion

        #region Constructor

        public RequestInfoEditor() 
        {   
        }

        #endregion
        
        #region Methods

        /// <summary>
        /// Retrieves all Request reason and retrieves selected Request info. 
        /// </summary>
        public override void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<ReasonDetails> reasons = ROIAdminController.Instance.RetrieveAllReasons(ReasonType.Request);
                ((RequestInfoMCP)MCP).PrePopulate(reasons);

                if (request.Id == 0)
                {
                    request.Status = RequestStatus.Logged;
                    request.StatusChanged = DateTime.Now.Date;
                }
               
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        public override void SetData(object data)
        {
            MCP.SetData(request);
            ((Control)View).Enabled = !request.IsLocked;
        }

        public override void Cleanup()
        {
            // do nothing since this instance need to reused
            base.Cleanup();
        }
        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the FindRequestEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "requestinfo.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the FindRequestEditor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get 
            {
                if (request == null || request.Id == 0)
                {
                    return "requestinfo.header.newinfo";
                }
                else
                {
                    return "requestinfo.header.editinfo";
                }
            }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(RequestInfoMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return null; }
        }

        public CreateRequestInfo CreateRequestInfo
        {
            get { return createRequestInfo; }
            set 
            { 
                createRequestInfo = value;
                request = createRequestInfo.Request;
            }
        }

        public RequestDetails Request
        {
            get { return request; }
            set { request = value; }
        }


        #endregion
    }
}
