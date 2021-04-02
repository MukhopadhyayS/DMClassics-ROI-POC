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

namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureCountry
{
    /// <summary>
    /// Class display the UI control of configure location
    /// </summary>
    public class ConfigureCountryMCP : ROIBasePane, ITransientDataApprover
    {
        #region Fields

        private ConfigureCountryUI configCountryUI;
        private bool isDirty;

        private EventHandler DataDirty;
        private EventHandler dirtyDataHandler;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the view of configure location mcp ui.
        /// </summary>
        protected override void InitView()
        {
            configCountryUI = new ConfigureCountryUI();
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
            configCountryUI.EnableEvents();
        }

        public void DisableEvents()
        {
            configCountryUI.DisableEvents();
        }
         
        /// <summary>
        /// Load the prerequest data
        /// </summary>
        public void PrePopulate()
        {
            configCountryUI.SetData();
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
                if (!((ConfigureCountryUI)View).Save())
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
                ((ConfigureCountryUI)this.View).Cancel();
                IsDirty = false;
                return true;
            }

            return false;
        }

        #endregion


        #endregion

        #region Properties

        /// <summary>
        ///  Returns the view of configure country mcp.
        /// </summary>
        public override Component View
        {
            get { return configCountryUI; }
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

