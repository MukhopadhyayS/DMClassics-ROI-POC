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
using System.Net;
using System.Net.NetworkInformation;
using System.Text;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Admin.View.Configuration.ExternalSources
{
  public  class ExternalSourcesEditor : ROIEditor
    {
        #region Methods

        protected override void InitComponent()
        {
            base.InitComponent();
            base.hOuterSplitContainer.SplitterDistance = 58;
        }
     
        /// <summary>
        /// Prepopulate the External sources
        /// </summary>
        public override void PrePopulate()
        {
            ExternalSourcesMCP externalSourceMCP = (ExternalSourcesMCP)SubPanes[1];
            externalSourceMCP.PrePopulate();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the ExternalSourcesEditor Editor.
        /// </summary>
        protected override string TitleText
        {
            get { return "externalsources.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the ExternalSourcesEditor Editor.
        /// </summary>
        protected override string InfoText
        {
            get { return "externalsources.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        protected override Type MCPType
        {
            get { return typeof(ExternalSourcesMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        protected override Type ODPType
        {
            get { return typeof(ExternalSourcesODP); }
        }

        #endregion
    }
}
