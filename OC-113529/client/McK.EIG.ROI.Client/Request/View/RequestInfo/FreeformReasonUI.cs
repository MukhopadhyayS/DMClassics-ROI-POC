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
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    public partial class FreeformReasonUI : ROIBaseUI
    {
        #region Fields

        private EventHandler dirtyDataHandler;
        private EventHandler deleteFreeformReasonHandler;

        #endregion

        #region Constructor

        public FreeformReasonUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localize
        /// </summary>
        public override void Localize()
        { 
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteFreeformReasonImg);
        }

        /// <summary>
        /// Get Localize key.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        private void deleteFreeformReasonImg_Click(object sender, EventArgs e)
        {   
            DeleteFreeformReasonHandler(this, e);
            RaiseDirtyData();
        }

        private void reasonCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            RaiseDirtyData();
        }

        private void reasonTextBox_TextChanged(object sender, EventArgs e)
        {
            RaiseDirtyData();
        }

        private void RaiseDirtyData()
        {
            if (dirtyDataHandler != null)
            {
                dirtyDataHandler(null, null);
            }
        }

        public void SetFocus()
        {
            reasonTextBox.Focus();
        }

        #endregion

        #region Properties

        /// <summary>
        /// DeleteFreeformReasonHandler
        /// </summary>
        public EventHandler DeleteFreeformReasonHandler
        {
            get { return deleteFreeformReasonHandler; }
            set { deleteFreeformReasonHandler = value; }
        }

        /// <summary>
        /// FreeformCheckedHandler
        /// </summary>
        public EventHandler DirtyDataHandler
        {
            get { return dirtyDataHandler; }
            set { dirtyDataHandler = value; }
        }

        /// <summary>
        /// ReasonName
        /// </summary>
        public string ReasonName
        {
            get { return reasonTextBox.Text; }
            set { reasonTextBox.Text = value; }
        }

        /// <summary>
        /// Gets the Reason TextBox
        /// </summary>
        public TextBox ReasonTextBox
        {
            get { return reasonTextBox; }
        }

        /// <summary>
        /// Checked
        /// </summary>
        public bool Checked
        {
            get { return reasonCheckBox.Checked; }
            set { reasonCheckBox.Checked = value; }
        }

        public bool ImageRequired
        {
            get
            {
                return requiredImage.Visible;
            }
            set
            {
                if (value)
                {
                    requiredImage.Visible = true;
                }
                else
                {
                    requiredImage.Visible = false;
                }
            }
        }

        #endregion

    }
}
