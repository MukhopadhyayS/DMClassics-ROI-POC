using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureUnbillableRequest
{
    
         public class ConfigureUnbillableRequestEditor : ROIEditor
    {
        #region Methods

        /// <summary>
        /// Display the UnbillableRequest status
        /// </summary>
        public override void PrePopulate()
        {
            ConfigureUnbillableRequestMCP mcp = (ConfigureUnbillableRequestMCP)MCP;
            mcp.PrePopulate();
        }

        #endregion

        #region Properties

        protected override string TitleText
        {
            get { return "configuredefaultunbillablerequest.header.title"; }
        }

        protected override string InfoText
        {
            get { return "configuredefaultunbillablerequest.header.info"; }
        }

        protected override Type MCPType
        {
            get { return typeof(ConfigureUnbillableRequestMCP); }
        }

        protected override Type ODPType
        {
            get { return null; }
        }

        #endregion
    }
}
