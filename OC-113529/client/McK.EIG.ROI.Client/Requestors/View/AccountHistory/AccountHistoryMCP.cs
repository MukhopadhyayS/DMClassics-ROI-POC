using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Request.View;
namespace McK.EIG.ROI.Client.Requestors.View.AccountHistory
{
    class AccountHistoryMCP : ROIBasePane
    {

        #region Fields

        private AccountHistoryUI accountHistoryUI;
        private bool isDirty;

        private EventHandler DataDirty;
        private EventHandler dirtyDataHandler;

        #endregion



        /// <summary>
        /// Initilize the view of configure location mcp ui.
        /// </summary>
        protected override void InitView()
        {
            accountHistoryUI = new AccountHistoryUI();
            IsDirty = false;
        }

        /// <summary>
        /// Load the prerequest data
        /// </summary>
        public void PrePopulate(RequestorDetails requestor)
        {
            Collection<RequestorHistoryDetail> reqHistory = new Collection<RequestorHistoryDetail>();
            reqHistory = RequestorController.Instance.RetrieveRequestorSummaries(requestor.Id);
            accountHistoryUI.SetData(reqHistory,requestor);
        }


        #region Properties

        /// <summary>
        ///  Returns the view of configure country mcp.
        /// </summary>
        public override Component View
        {
            get { return accountHistoryUI; }
        }

        /// <summary>
        /// Sets true if dirty data available.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set
            {
                isDirty = value;
                if (!isDirty)
                {
                    //EnableEvents();
                }
            }
        }

        #endregion
    }
}
