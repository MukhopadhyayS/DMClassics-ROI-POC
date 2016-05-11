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

using McK.EIG.Common.FileTransfer.Controller.Upload;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.LetterTemplate
{
    /// <summary>
    /// Holds the UI of Letter Template List Pane
    /// </summary>
    public class LetterTemplateMCP : AdminMCP
    {
        #region Methods

        /// <summary>
        /// Subscribe the envet to handle the progress bar.
        /// </summary>
        protected override void Subscribe()
        {
            base.Subscribe();
            LetterTemplateEditor.ProgressHandler = new EventHandler(Process_NotifyProgress);
        }
        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            (adminListUI as LetterTemplateListUI).ShowProgress((FileTransferEventArgs)e);
        }

        /// <summary>
        /// Populates Letter Templates
        /// </summary>
        public void PrePopulate()
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                Collection<LetterTemplateDetails> letterTemplates = ROIAdminController.Instance.RetrieveAllLetterTemplates();
                if (letterTemplates != null)
                {
                    ComparableCollection<LetterTemplateDetails> list = new ComparableCollection<LetterTemplateDetails>(letterTemplates, new LetterTemplateComparer());
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(LetterTemplateDetails))["LetterTypeName"], ListSortDirection.Ascending);
                    adminListUI.SetData(list);
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

        #region Properties

        /// <summary>
        /// Returns the view of LetterTemplateMCP.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get 
            {
                return (adminListUI == null) ? new LetterTemplateListUI() : adminListUI;
            }
        }

        #endregion
    }
}
