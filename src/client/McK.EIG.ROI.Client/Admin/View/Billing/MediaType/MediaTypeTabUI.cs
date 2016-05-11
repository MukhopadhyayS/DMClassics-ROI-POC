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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View.Billing.MediaType
{
    /// <summary>
    /// Display the MediaTypeODP UI.
    /// </summary>
    public partial class MediaTypeTabUI : ROIBaseUI, IAdminBaseTabUI
    {
                
        #region Fields

        private EventHandler dirtyDataHandler;
        private bool enableSave;       

        #endregion

        #region Constructor
        /// <summary>
        /// Initialize UserInterface component of MediaTypeODP
        /// </summary>
        public MediaTypeTabUI()
        {
            InitializeComponent();
            dirtyDataHandler = new EventHandler(MarkDirty);
        }
        
        #endregion
        
        #region Methods

        /// <summary>
        /// Clear control values
        /// </summary>
        public void ClearControls()
        {
            ((Pane.View) as AdminBaseObjectDetailsUI).ClearErrors();
            nameTextBox.Text        = string.Empty;
            descriptionTextBox.Text = string.Empty;             
            nameTextBox.Focus();
        }

        /// <summary>
        /// Gets the MediaTypeDetails object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {

            MediaTypeDetails mediaType = (appendTo == null) ? new MediaTypeDetails()
                                           : appendTo as MediaTypeDetails;

            mediaType.Name        = nameTextBox.Text.Trim();
            mediaType.Description = descriptionTextBox.Text.Trim();

            return mediaType;
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearControls();

            MediaTypeDetails mediaTypeDetails = data as MediaTypeDetails;

            if (mediaTypeDetails != null)
            {
                nameTextBox.Text        = mediaTypeDetails.Name;
                descriptionTextBox.Text = mediaTypeDetails.Description;
                nameTextBox.Enabled     = mediaTypeDetails.Id >= 0;

                if (mediaTypeDetails.Id >= 0)
                {
                    nameTextBox.Focus();
                }

                else
                {
                    descriptionTextBox.Select(0, 0);
                    descriptionTextBox.Focus();
                }

            }
            else
            {
                nameTextBox.Enabled = true;
                nameTextBox.Focus();
            }

            EnableEvents();
        }

        /// <summary>
        /// Returns the respective control for the given error code.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.MediaTypeNameAlreadyExist     : return nameTextBox;
                case ROIErrorCodes.MediaTypeSeedData             : return nameTextBox;
                case ROIErrorCodes.MediaTypeNameEmpty            : return nameTextBox;
                case ROIErrorCodes.MediaTypeNameMaxLength        : return nameTextBox;
                case ROIErrorCodes.MediaTypeIsAssociated         : return nameTextBox;
                case ROIErrorCodes.MediaTypeDescriptionMaxLength : return descriptionTextBox;
            }
            return null;
        }

        /// <summary>
        ///  This method is used to enable(subscribe)the MediaTypeODPUI local events
        /// </summary>
        public void EnableEvents()
        {
            DisableEvents();
            nameTextBox.TextChanged         += dirtyDataHandler;
            descriptionTextBox.TextChanged  += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscribe)the MediaTypeODPUI local events
        /// </summary>
        public void DisableEvents()
        {
            nameTextBox.TextChanged         -= dirtyDataHandler;
            descriptionTextBox.TextChanged  -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when user changes MediaName or Description.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(nameTextBox.Text.Trim()))
            {
                enableSave = false;
            }
            else
            {
                enableSave = true;
            }
           (Pane as AdminBaseODP).MarkDirty(sender, e);
            
        }

        /// <summary>
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == nameLabel)
            {
                return control.Name + "." + GetType().Name;
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, nameLabel);
            SetLabel(rm, descriptionLabel);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets enable save value.
        /// </summary>
        public bool EnableSave
        {
            get { return enableSave; }            
        }

        #endregion      
    }
}
