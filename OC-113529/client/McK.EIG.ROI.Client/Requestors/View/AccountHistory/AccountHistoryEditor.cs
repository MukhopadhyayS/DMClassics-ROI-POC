using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Requestors.View.AccountHistory
{
    /// <summary>
    /// This class hold the editor information of Account History
    /// </summary>
    class AccountHistoryEditor : ROIEditor
    {
        #region Fields

        private RequestorDetails requestorInfo;
        private RequestorHeaderUI requestorHeaderUI;

        private EventHandler requestorUpdated;

        #endregion

        #region Methods


        protected override void InitComponent()
        {
            base.InitComponent();

            AccountHistoryUI accountHistoryUI = (AccountHistoryUI)SubPanes[1].View;
            requestorInfo = ((RequestorRSP)ParentPane).InfoEditor.RequestorInfo;
            //requestorLetterHistoryUI.RequestorName = requestorInfo.Name;
            HeaderUI headerUI = SubPanes[0].View as HeaderUI;
            headerUI.Parent.Size = new System.Drawing.Size(128, 100);
            requestorHeaderUI = new RequestorHeaderUI();
            requestorHeaderUI.PopulateRequestorInformation(requestorInfo, Context);
            headerUI.HeaderExtension = requestorHeaderUI;
        }

        public override void PrePopulate()
        {
            try
            {
                //Navigate to Request module when clicking the Requestor -> Create Request from Requetor Letter screen
                ((AccountHistoryMCP)MCP).PrePopulate(requestorInfo);
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


        /// <summary>
        /// Localize Header UI.
        /// </summary>
        public override void Localize()
        {
            base.Localize();
            requestorHeaderUI.Localize(Context);
        }

        public override void Cleanup()
        {
            base.Cleanup();
            View.Dispose();
            // do nothing since this instance need to reused
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        protected override void Subscribe()
        {
            requestorUpdated = new EventHandler(Process_UpdateRequestorInfo);
            RequestorEvents.RequestorUpdated += requestorUpdated;
        }


        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        protected override void Unsubscribe()
        {
            RequestorEvents.RequestorUpdated -= requestorUpdated;
        }

        /// <summary>
        /// Updates the patient's information on patient information box
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateRequestorInfo(object sender, EventArgs e)
        {
            RequestorDetails requestor = (RequestorDetails)((ApplicationEventArgs)e).Info;
            requestorHeaderUI.PopulateRequestorInformation(requestor, Context);
        }

        #endregion

        #region Properties
        protected override string TitleText
        {
            get { return "accounthistory.header.title"; }
        }

        protected override string InfoText
        {
            get { return "accounthistory.header.info"; }
        }

        protected override Type MCPType
        {
            get { return typeof(AccountHistoryMCP); }
        }

        protected override Type ODPType
        {
            get { return null; }
        }

        public RequestorDetails RequestorInfo
        {
            get { return requestorInfo; }
        }

        #endregion
    }
}
