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
using System.Collections;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Other.LetterTemplate
{
    /// <summary>
    /// Holds the UI of AdminLetterTemplatePane
    /// </summary>
    public class LetterTemplateODP : AdminBaseODP
    {
        #region Methods

        /// <summary>
        /// Create new Admin Letter Template
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object CreateEntity(ROIModel model)
        {
            return ROIAdminController.Instance.CreateLetterTemplate(model as LetterTemplateDetails, 
                                                                    LetterTemplateEditor.ProgressHandler) as ROIModel;
        }

        /// <summary>
        /// Update the details of selected Admin Letter Template
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        public override object UpdateEntity(ROIModel model)
        {
            return ROIAdminController.Instance.UpdateLetterTemplate(model as LetterTemplateDetails, LetterTemplateEditor.ProgressHandler);
        }

        /// <summary>
        /// Returns Admin Letter Template ODP UI
        /// </summary>
        protected override IAdminBaseTabUI CreateEntityUI()
        {
            return new LetterTemplateTabUI();
        }

        /// <summary>
        /// Load the data into Letter Type combobox.
        /// </summary>
        public void PrePopulateLetterType()
        {
            ROIViewUtility.MarkBusy(true);
            IList requestStatus = EnumUtilities.ToList(typeof(LetterType));
            try
            {
                //CR382752: Remove Overdue Invoice
                //Commented the hardcoded value index 6 and new logic is written in LetterTemplateTabUI.cs
                //requestStatus.RemoveAt(6); 
                if (requestStatus != null)
                {
                    ((View as AdminBaseObjectDetailsUI).EntityTabUI as LetterTemplateTabUI).PopulateLetterType(requestStatus);
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
    }
}
