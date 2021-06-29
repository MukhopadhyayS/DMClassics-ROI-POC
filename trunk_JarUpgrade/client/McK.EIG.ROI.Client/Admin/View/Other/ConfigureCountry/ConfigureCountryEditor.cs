using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureCountry
{
    /// <summary>
    /// This class hold the editor information of country location
    /// </summary>
    public class ConfigureCountryEditor : ROIEditor
    {
        #region Methods

        /// <summary>
        /// Display the Country location
        /// </summary>
        public override void PrePopulate()
        {
            ConfigureCountryMCP mcp = (ConfigureCountryMCP)MCP;
            mcp.PrePopulate();
        }

        #endregion

        #region Properties

        protected override string TitleText
        {
            get { return "configurecountry.header.title"; }
        }

        protected override string InfoText
        {
            get { return "configurecountry.header.info"; }
        }

        protected override Type MCPType
        {
            get { return typeof(ConfigureCountryMCP); }
        }

        protected override Type ODPType
        {
            get { return null; }
        }

        #endregion
    }
}
