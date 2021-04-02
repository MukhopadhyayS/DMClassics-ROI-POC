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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.View.Letters
{
    public partial class LetterInfoActionUI : ROIBaseUI
    {
        #region Constructor

        public LetterInfoActionUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, requiredLabel);
            SetLabel(rm, createLetterButton);
            SetLabel(rm, cancelButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            SetTooltip(rm, toolTip, createLetterButton);
            SetTooltip(rm, toolTip, cancelButton);           
        }        

        /// <summary>
        /// Gets localized key of UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == createLetterButton)
            {
                return control.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Gets localized key of UI controls tooltip.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the createletter button.
        /// </summary>
        public Button CreateLetterButton
        {
            get { return createLetterButton; }
        }

        /// <summary>
        /// Gets the cancel button.
        /// </summary>
        public Button CancelButton
        {
            get { return cancelButton; }
        }

        #endregion

       
    }
}
