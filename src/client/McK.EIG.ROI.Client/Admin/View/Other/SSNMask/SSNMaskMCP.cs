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
using System.ComponentModel;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.SSNMasking
{
    /// <summary>
    /// Class display the UI control of SSN Masking
    /// </summary>
    public class SSNMaskMCP : ROIBasePane, ITransientDataApprover
    {
        #region Fields
        
        private SSNMaskUI maskingUI;

        private bool isDirty;

        private EventHandler DataDirty;
        private EventHandler dirtyDataHandler;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the view of ssn masking mcp ui.
        /// </summary>
        protected override void InitView()
        {
            maskingUI = new SSNMaskUI();
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
            maskingUI.EnableEvents();
        }

        public void DisableEvents()
        {
            maskingUI.DisableEvents();
        }

        /// <summary>
        /// Load the prerequest data
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                SSNMaskDetails maskDetails = ROIAdminController.Instance.RetrieveSsnMask();
                if (maskDetails != null)
                {
                    SetData(maskDetails);
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
            maskingUI.SetData(data);
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
                if (!((SSNMaskUI)View).Save())
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
                ((SSNMaskUI)this.View).CancelSSNMask();
                IsDirty = false;
                return true;
            }

            return false;
        }

        #endregion

        #endregion

        #region Properties

        /// <summary>
        ///  Returns the view of ssnmasking mcp.
        /// </summary>
        public override Component View
        {
            get { return maskingUI; }
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
