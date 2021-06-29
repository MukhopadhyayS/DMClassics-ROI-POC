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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportFooterUI
    /// </summary>
    public partial class ReportFooterUI : ROIBaseUI
    {
        #region Constructor

        public ReportFooterUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localize the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, printPreviewButton);
            SetLabel(rm, printButton);
            SetLabel(rm, exportButton);
        }
        #endregion

        #region Properties 

        /// <summary>
        /// Returns PrintPreviewButton
        /// </summary>
        public Button PrintPreviewButton
        {
            get { return printPreviewButton; }
        }

        /// <summary>
        /// Returns PrintPreviewButton
        /// </summary>
        public Button PrintButton
        {
            get { return printButton; }
        }

        /// <summary>
        /// Returns PrintPreviewButton
        /// </summary>
        public Button ExportButton
        {
            get { return exportButton;  }
        }

        #endregion

    }
}
