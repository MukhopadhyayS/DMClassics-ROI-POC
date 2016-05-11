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
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Request.View
{
    public partial class FreeformUI : ROIBaseUI
    {
        #region Fields

        private EventHandler deleteFreeformHandler;
        private EventHandler reasonCheckedHandler;
        private bool isFreeform;
        private long noteId;

        #endregion

        #region Constructor

        public FreeformUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localizes the UI.
        /// </summary>
        public override void Localize()
        { 
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteFreeformImg);
        }

        /// <summary>
        /// Gets localize key.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        private void freeformCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            if (reasonCheckedHandler != null)
            {
                reasonCheckedHandler(this, e);
            }
        }
      
        private void deleteFreeformImg_Click(object sender, EventArgs e)
        {
            DeleteFreeformHandler(this, e);
        }

        /// <summary>
        /// Sets focus into freeform text.
        /// </summary>
        public void SetFocus()
        {
            freeformTextBox.Focus();
            freeformCheckBox.Checked = true;
        }

        /// <summary>
        /// Disable all the controls.
        /// </summary>
        public void DisableControls()
        {
            freeformCheckBox.Enabled  = false;
            freeformTextBox.Enabled   = false;
            deleteFreeformImg.Enabled = false;
        }
        
        #endregion

        #region Properties


        /// <summary>
        /// Freeform reason checked handler
        /// </summary>
        public EventHandler ReasonCheckedHandler
        {
            get { return reasonCheckedHandler; }
            set { reasonCheckedHandler = value; }
        }

        /// <summary>
        /// DeleteFreeformReasonHandler 
        /// </summary>
        public EventHandler DeleteFreeformHandler
        {
            get { return deleteFreeformHandler; }
            set { deleteFreeformHandler = value; }
        }

        /// <summary>
        /// Gets the freeform textbox.
        /// </summary>
        public TextBox FreeformTextBox
        {
            get { return freeformTextBox; }
        }

        /// <summary>
        /// Gets or Sets the freeform checkbox checked status.
        /// </summary>
        public bool Checked
        {
            get { return freeformCheckBox.Checked; }
            set { freeformCheckBox.Checked = value; }
        }

        public CheckBox FreeformCheckBox
        {
            get
            {
                return freeformCheckBox;
            }
        }

        /// <summary>
        /// Gets the delete image.
        /// </summary>
        public PictureBox DeleteImage
        {
            get { return deleteFreeformImg; }
        }

        /// <summary>
        /// Gets or sets the isFreeform value.
        /// </summary>
        public bool IsFreeform
        {
            get { return isFreeform; }
            set { isFreeform = value; }
        }

        /// <summary>
        /// Gets or sets the notes id
        /// </summary>
        public long NoteId
        {
            get { return noteId; }
            set { noteId = value; }
        }
        
        #endregion        
    }
}
