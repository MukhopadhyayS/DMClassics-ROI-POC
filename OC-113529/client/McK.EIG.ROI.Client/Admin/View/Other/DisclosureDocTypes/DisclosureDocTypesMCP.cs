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
using System.Collections.ObjectModel;
using System.ComponentModel;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.DisclosureDocTypes
{
    public class DisclosureDocTypesMCP : ROIBasePane, ITransientDataApprover 
    {
        #region Fields

        private DisclosureDocTypesListUI disclosureDocTypesListUI;

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the view of DisclosureDocTypesListUI.
        /// </summary>
        protected override void InitView()
        {
            disclosureDocTypesListUI = new DisclosureDocTypesListUI();
        }

        /// <summary>
        /// Event subscriptions.
        /// </summary>
        protected override void Subscribe()
        {
            TransientDataValidator.AddApprover(this);
        }

        /// <summary>
        /// Event Unsubscriptions.
        /// </summary>
        protected override void Unsubscribe()
        {
            TransientDataValidator.RemoveApprover(this);
        }
        
        /// <summary>
        /// Load the prerequest data
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<CodeSetDetails> codeSets = ROIAdminController.Instance.RetrieveAllCodeSets();
                if (codeSets != null)
                {
                    disclosureDocTypesListUI.PrePopulate(codeSets);
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

        #endregion

        #region ITransientDataApprover Members
        /// <summary>
        /// Check whether the data is modified and display the
        /// confirm data loss dialog box for further processing
        /// </summary>
        /// <param name="ae">Event raised in the application</param>
        /// <returns>Return true if user click OK button in dialog box.</returns>
        public bool Approve(ApplicationEventArgs ae)
        {  
            if (!disclosureDocTypesListUI.IsDirty)
            {
                return true;
            }

            TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

            if (result == TransientResult.SaveAndProceed)
            {
                disclosureDocTypesListUI.IsDirty = false;
                if (!((DisclosureDocTypesListUI)this.View).SaveDocumentTypes())
                {
                    disclosureDocTypesListUI.IsDirty = true;
                }
                else
                {
                    return true;
                }
            }
            else if (result == TransientResult.Proceed)
            {
                disclosureDocTypesListUI.IsDirty = false;
                disclosureDocTypesListUI.ResetData();
                return true;
            }

            return false;
        }
       
        #endregion

        #region Properties

        /// <summary>
        ///  Returns the view of DisclosureDocTypesMCP.
        /// </summary>
        public override Component View
        {
            get { return disclosureDocTypesListUI; }
        }

        #endregion
    }
}
