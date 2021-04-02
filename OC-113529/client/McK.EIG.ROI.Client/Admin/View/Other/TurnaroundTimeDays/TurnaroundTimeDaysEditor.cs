using System;
using System.Collections.Generic;
using System.Text;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Configuration.TurnaroundTimeDays
{
    /// <summary>
    /// Used to group Header Pane and MCP/ODP.
    /// </summary>
    public class TurnaroundTimeDaysEditor: ROIEditor
    {
        #region Properties

        /// <summary>
        /// Used to get the Title text of the TurnaroundTimeDays Editor.
        /// </summary>
        /// <returns></returns>
        protected override string TitleText
        {
            get { return "turnaroundtime.header.title"; }
        }

        /// <summary>
        /// Used to get the Information text of the TurnaroundTimeDays Editor.
        /// </summary>
        /// <returns></returns>
        protected override string InfoText
        {
            get { return "turnaroundtime.header.info"; }
        }

        /// <summary>
        /// Returns the type of the MCP.
        /// </summary>
        /// <returns></returns>
        protected override Type MCPType
        {
            get { return typeof(TurnaroundTimeDaysMCP); }
        }

        /// <summary>
        /// Returns the type of the ODP.
        /// </summary>
        /// <returns></returns>
        protected override Type ODPType
        {
            get { return null; }
        }
        #endregion

        #region Methods

        public override void PrePopulate()
        {
            TurnaroundTimeDaysMCP turnaroundMCP = (TurnaroundTimeDaysMCP)SubPanes[1];
            turnaroundMCP.PrePopulate();
        }

        #endregion

    }
}
