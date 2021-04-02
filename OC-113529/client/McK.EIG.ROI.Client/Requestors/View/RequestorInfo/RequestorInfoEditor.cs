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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.Controller;

namespace McK.EIG.ROI.Client.Requestors.View.RequestorInfo
{
    public class RequestorInfoEditor : ROIEditor
    {
        #region Fields

        private RequestorDetails requestorInfo;
        
        #endregion

        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();
        }

        /// <summary>
        /// Retrieves all requestor types and retrives selected requestor info. 
        /// </summary>
        public override void PrePopulate()
        {
            RequestorInfoMCP reqInfoMCP = (RequestorInfoMCP)MCP;
            Collection<RequestorTypeDetails> requestorTypes = new Collection<RequestorTypeDetails>();
            try
            {
                requestorTypes = ROIAdminController.Instance.RetrieveAllRequestorTypes(false);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            reqInfoMCP.PrePopulate(requestorTypes);

            reqInfoMCP.SetData(requestorInfo);
        }

        public override void Cleanup()
        {
            // do nothing since this instance need to reused
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the FindRequestorEditor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "requestorinfo.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the FindRequestorEditor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "requestorinfo.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(RequestorInfoMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return null; }
        }

        public RequestorDetails RequestorInfo
        {
            get { return requestorInfo; }
            set { requestorInfo = value; }
        }

        #endregion
    }
}
