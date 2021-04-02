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

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.LetterTemplate
{
    public class LetterTemplateEditor : AdminEditor
    {
        #region Fields

        internal static EventHandler ProgressHandler;

        #endregion

        #region Methods

        /// <summary>
        /// Populates the Admin Letter Template
        /// </summary>
        public override void PrePopulate()
        {
            LetterTemplateODP odp = (LetterTemplateODP)SubPanes[2];
            odp.PrePopulateLetterType();

            LetterTemplateMCP mcp = (LetterTemplateMCP)SubPanes[1];
            mcp.PrePopulate();
        }

        #endregion

        #region Properties

        /// <summary>
        /// Used to get the Title text of the Admin Letter Template.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "lettertemplate.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the Admin Letter Template.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "lettertemplate.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(LetterTemplateMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP
        /// </summary>
        protected override Type ODPType
        {
            get { return typeof(LetterTemplateODP); }
        }

        #endregion
    }
}
