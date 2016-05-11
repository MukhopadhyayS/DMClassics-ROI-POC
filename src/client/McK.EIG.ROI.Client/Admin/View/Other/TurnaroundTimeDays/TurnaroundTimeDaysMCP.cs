using System;
using System.ComponentModel;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.Common.Utility.Logging;

namespace McK.EIG.ROI.Client.Admin.View.Configuration.TurnaroundTimeDays
{
    public class TurnaroundTimeDaysMCP : ROIBasePane, ITransientDataApprover
    {
        #region Fields

        private static TurnaroundTimeDaysListUI turnaroundTimeUI;
        private EventHandler DataDirty;
        private EventHandler dirtyDataHandler;
        private bool isDirty;
        private Log log = LogFactory.GetLogger(typeof(TurnaroundTimeDaysMCP));

        #endregion

        #region Properties

        /// <summary>
        ///  Returns the view of TurnaroundtimedaysMcp.
        /// </summary>
        public override Component View
        {
            get { return turnaroundTimeUI; }
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

        #region Methods

        /// <summary>
        /// Initialize the view of TurnaroundTimeDaysMCP
        /// </summary>
        protected override void InitView()
        {
            turnaroundTimeUI = new TurnaroundTimeDaysListUI();
            isDirty = false;
        }

        /// <summary>
        /// Load the prerequest data
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<TurnaroundTimeDay> daysStatus = ROIAdminController.Instance.retrieveDaysStatus();
                turnaroundTimeUI.SetData(daysStatus);
            }
            catch(ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
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
            // Once data is changed need not listen to further changes
            // Consider reverting data to original value as dirty
            DisableEvents();
            AdminEvents.OnODPDataChange(sender, e);
        }

        public void EnableEvents()
        {
            turnaroundTimeUI.EnableEvents();
        }

        public void DisableEvents()
        {
            turnaroundTimeUI.DisableEvents();
        }

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
                if (!((TurnaroundTimeDaysListUI)this.View).Save())
                {
                    isDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {
                ((TurnaroundTimeDaysListUI)this.View).CancelStatusChanges();
                IsDirty = false;
                return true;
            }
            return false;
        }

        #endregion

       

    }
}
