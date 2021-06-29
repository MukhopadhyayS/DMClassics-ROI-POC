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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;


namespace McK.EIG.ROI.Client.Admin.View
{

    public enum TransientResult
    {
        Proceed,
        SaveAndProceed,
        Cancel
    }

    /// <summary>
    /// Holds the UI of AdminBaseODP
    /// </summary>
    public abstract class AdminBaseODP : ROIBasePane, ITransientDataApprover
    {
        #region Fields

        private EventHandler mcpEntitySelectionHandler;
        private EventHandler createNewClickHandler;
        private EventHandler emptyListingHandler;

        private AdminBaseObjectDetailsUI baseODPUI;
        private bool dirty;       

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the AdminBaseODP.
        /// </summary>
        protected override void InitView()
        {
            baseODPUI = new AdminBaseObjectDetailsUI();
            baseODPUI.EntityTabUI = CreateEntityUI();
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            TransientDataValidator.AddApprover(this);
            mcpEntitySelectionHandler  = new EventHandler(Process_EntitySelection);
            createNewClickHandler      = new EventHandler(Process_CreateNewButtonClick);
            emptyListingHandler        = new EventHandler(Process_EmptyListing);

            AdminEvents.MCPEntitySelection   += mcpEntitySelectionHandler;
            AdminEvents.CreateNewButtonClick += createNewClickHandler;
            AdminEvents.EmptyListing         += emptyListingHandler;
        }

        /// <summary>
        /// Event unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            TransientDataValidator.RemoveApprover(this);
            AdminEvents.MCPEntitySelection   -= mcpEntitySelectionHandler;
            AdminEvents.CreateNewButtonClick -= createNewClickHandler;
            AdminEvents.EmptyListing         -= emptyListingHandler;
        }

        internal void MarkDirty(object sender, EventArgs e)
        {
            dirty = true;            
            baseODPUI.EnableSaveButton(baseODPUI.EntityTabUI.EnableSave);
            baseODPUI.EnableCancelButton();
            // Once data is changed need not listen to further changes
            // Consider reverting data to original value as dirty
            // DisableEvents();
            AdminEvents.OnODPDataChange(sender, e);
        }

        /// <summary>
        /// Occurs when the Row is Selected in the DataGridView of MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_EntitySelection(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = (ApplicationEventArgs)e;
            baseODPUI.Enabled = true;
            baseODPUI.SetData(ae.Info);
        }

        /// <summary>
        /// Occurs when the Create New button is clicked in MCP.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CreateNewButtonClick(object sender, EventArgs e)
        {
            dirty = false;
            baseODPUI.Enabled = true;
            baseODPUI.SetData(null);
        }

        private void Process_EmptyListing(object sender, EventArgs e)
        {
            dirty = false;
            baseODPUI.SetData(null);
            baseODPUI.Enabled = false;
        }

        public void ClearControls()
        {
            baseODPUI.ClearControls();
        }

        public void EnableEvents()
        {
            baseODPUI.EnableEvents();
        }

        public void DisableEvents()
        {
            baseODPUI.DisableEvents();
        }

        /// <summary>
        /// Create Entity Controller invocation implementation.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public virtual object CreateEntity(ROIModel model)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Update Entity Controller invocation implementation.
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public virtual object UpdateEntity(ROIModel model)
        {
            throw new NotImplementedException();
        }

        public virtual void PrePopulate(object data)
        {

        }

        #region TransientDataApprover Members

        /// <summary>
        /// Approve the user's confirmation for dirty data.
        /// </summary>
        /// <param name="ae"></param>
        /// <returns></returns>
        public bool Approve(ApplicationEventArgs ae)
        {
            if (!IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult) ROIViewUtility.DoSaveAndProceed(Context);
            
            if (result == TransientResult.SaveAndProceed)
            {
                bool saved = ((AdminBaseObjectDetailsUI)this.View).Save();
                if (saved)
                {
                    IsDirty = false;
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {
                IsDirty = false;
                baseODPUI.ResetData(ae);
                return true;
            }      

            //When cancel is clicked during dirty dialog
            return false;
        }
        #endregion

        #endregion

        #region Properties

        protected abstract IAdminBaseTabUI CreateEntityUI();

        /// <summary>
        /// Gets the view of AdminBaseODP.
        /// </summary>
        public override Component View
        {
            get { return baseODPUI; }
        }

        /// <summary>
        /// Sets true if dirty data available
        /// </summary>
        public bool IsDirty
        {
            get { return dirty; }
            set
            {
                dirty = value;
                if (!dirty)
                {
                    EnableEvents();
                }
            }
        }
        
        #endregion
    }
}
