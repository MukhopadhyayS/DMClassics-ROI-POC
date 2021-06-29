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

namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureNotes
{
    /// <summary>
    /// ConfigureNotesEditor
    /// </summary>
    public class ConfigureNotesEditor : AdminEditor
    {
        #region Methods

        public override void PrePopulate()
        {
            ConfigureNotesMCP mcp = (ConfigureNotesMCP)SubPanes[1];
            mcp.PrePopulate();
        }

        #endregion

        # region Properties

        protected override string TitleText
        {
            get {return "configureNotes.header.title"; }
        }

        protected override string InfoText
        {
            get { return "configureNotes.header.info"; }
        }

        protected override Type MCPType
        {
            get { return typeof(ConfigureNotesMCP); }
        }

        protected override Type ODPType
        {
            get { return typeof(ConfigureNotesODP); }
        }
        
        #endregion
    }
}
