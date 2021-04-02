using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureUnbillableRequest
{
    public class ConfigureUnbillableRequestMCP : ROIBasePane, ITransientDataApprover    
    {
        #region Fields

        private ConfigureUnbillableRequestUI UnbillableRequestUI;

        private bool isDirty;

        private EventHandler DataDirty;
        private EventHandler dirtyDataHandler;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the view of UnbillableRequest mcp ui.
        /// </summary>
        protected override void InitView()
        {
            UnbillableRequestUI = new ConfigureUnbillableRequestUI();
            IsDirty = false;
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            TransientDataValidator.AddApprover(this);
            dirtyDataHandler = new EventHandler(MarkDirty);
            DataDirty += dirtyDataHandler;
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            TransientDataValidator.RemoveApprover(this);
            DataDirty -= dirtyDataHandler;
        }

        internal void OnDataDirty(object sender, EventArgs e)
        {
            if (DataDirty != null)
            {
                DataDirty(sender, e);
            }
        }

        private void MarkDirty(object sender, EventArgs e)
        {
            isDirty = true;
        }

        public void EnableEvents()
        {
            UnbillableRequestUI.EnableEvents();
        }

        public void DisableEvents()
        {
            UnbillableRequestUI.DisableEvents();
        }

        /// <summary>
        /// Load the prerequest data
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                ConfigureUnbillableRequestDetails requestDetails = ROIAdminController.Instance.RetrieveConfigureUnbillableRequest();
                if (requestDetails != null)
                {
                    SetData(requestDetails);
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

        /// <summary>
        /// Sets the data into view.
        /// </summary>
        /// <param name="data"></param>
        public override void SetData(object data)
        {
            UnbillableRequestUI.SetData(data);
        }

        #region ITransientDataApprover Members
        /// <summary>
        /// Check wether the data is modified and display the
        /// confirm data loss dialog box wether for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true if user click OK button in dialog box.</returns>
        public bool Approve(ApplicationEventArgs ae)
        {
            if (!IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                IsDirty = false;
                if (!((ConfigureUnbillableRequestUI)View).Save())
                {
                    IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {
                ((ConfigureUnbillableRequestUI)this.View).CancelConfigureUnbillableRequest();
                IsDirty = false;
                return true;
            }

            return false;
        }

        #endregion

        #endregion

        #region Properties

        /// <summary>
        ///  Returns the view of UnbillableRequest mcp.
        /// </summary>
        public override Component View
        {
            get { return UnbillableRequestUI; }
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
                    EnableEvents();
                }
            }
        }

        #endregion
    }
}
