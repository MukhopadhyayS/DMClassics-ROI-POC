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
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Resources;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Admin.View
{
    /// <summary>
    /// Display the AdminBaseObjectDetailsUI.
    /// </summary>
    public partial class AdminBaseObjectDetailsUI : ROIBaseUI
    {
        private Log log = LogFactory.GetLogger(typeof(AdminBaseObjectDetailsUI));

        #region Fields

        private string mode;

        private ROIModel modelDetails;

        private IAdminBaseTabUI entityTabUI;        

        #endregion

        #region Constructor
        /// <summary>
        /// Initialize UserInterface component
        /// </summary>
        public AdminBaseObjectDetailsUI()
        {
            InitializeComponent();
            mode    = "create";
            odpOuterPanel.Paint += new PaintEventHandler(Panel_Paint);
        }

        #endregion

        #region Methods

        private void Panel_Paint(object sender, PaintEventArgs e)
        {
            base.OnPaint(e);
            ControlPaint.DrawBorder(e.Graphics,
                                    (sender as Control).ClientRectangle,
                                    Color.FromArgb(85, 104, 129),
                                    ButtonBorderStyle.Solid);

        }

        public void ClearErrors()
        {
            errorProvider.Clear();
        }

        public void ClearControls()
        {
            entityTabUI.ClearControls();
        }

        public void EnableEvents()
        {
            entityTabUI.EnableEvents();
        }

        public void DisableEvents()
        {
            entityTabUI.DisableEvents();
        }

        public void ResetData(EventArgs ae)
        {
            SetData(modelDetails);
            if (modelDetails != null)
            {
                //AdminEvents.OnCancelModify(Pane, new ApplicationEventArgs(modelDetails, this));
                AdminEvents.OnCancelModify(Pane, ae);
            }
            else
            {
                //AdminEvents.OnCancelCreate(Pane, new ApplicationEventArgs(modelDetails, this));
                AdminEvents.OnCancelCreate(Pane, ae);
            }
        }

        /// <summary>
        /// Gets the model object
        /// </summary>
        /// <param name="appendTo"></param>
        /// <returns></returns>
        public object GetData(object appendTo)
        {
            return entityTabUI.GetData(appendTo);
        }

        /// <summary>
        /// Set the data into view
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            entityTabUI.DisableEvents();
            entityTabUI.ClearControls();

            modelDetails = data as ROIModel;

            if (modelDetails != null)
            {
                mode = "modify";
                entityTabUI.SetData(modelDetails);
                cancelButton.Enabled = false;
            }
            else
            {
                mode = "create";
                entityTabUI.SetData(modelDetails);
                cancelButton.Enabled = true;
            }

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, titleLabel);

            entityTabUI.EnableEvents();
            saveButton.Enabled = false;
        }

        public void EnableSaveButton(bool enableSave)
        {  
            saveButton.Enabled = enableSave;
        }

        public void EnableCancelButton()
        {
            cancelButton.Enabled = true;
        }

        /// <summary>
        /// Save the ODP details
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnSave_Click(object sender, EventArgs e)
        {
            Save();
        }

        public bool Save()
        {
            log.EnterFunction();
            errorProvider.Clear();

            try
            {
                ROIViewUtility.MarkBusy(true);
                // modelDetails object will hold recordVersionId and Id attribute.
                // clone the local cache so as to avoid updatind MCP row
                ROIModel roiModel = GetData(ROIViewUtility.DeepClone(modelDetails)) as ROIModel;
                if (modelDetails == null)
                {
                    roiModel = (Pane as AdminBaseODP).CreateEntity(roiModel) as ROIModel;
                    //Clears the dirty flag, when create entity is succcess.
                    //Cannot be moved out, because before firing event the flag has to be cleared
                    (Pane as AdminBaseODP).IsDirty = false;
                    AdminEvents.OnCreate(Pane, new ApplicationEventArgs(roiModel, this));
                }
                else
                {
                    roiModel = (Pane as AdminBaseODP).UpdateEntity(roiModel) as ROIModel;
                    //Clears the dirty flag, when update entity is succcess.
                    //Cannot be moved out, because before firing event the flag has to be cleared
                    (Pane as AdminBaseODP).IsDirty = false;
                    AdminEvents.OnModify(Pane, new ApplicationEventArgs(roiModel, this));
                }
                //Again data will be set in each controls.
                SetData(roiModel);
                // update the local cache instance with latest from server
                modelDetails = roiModel;

                saveButton.Enabled = false;
                cancelButton.Enabled = false;
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!(entityTabUI as ROIBaseUI).HandleClientError(rm, cause, errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
                return false;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();                
            }
            return true;
        }

		private void btnCancel_Click(object sender, EventArgs e)
        {
            (Pane as AdminBaseODP).IsDirty = false;
            errorProvider.Clear();
            cancelButton.Enabled = false;
            if (modelDetails == null)
            {
                entityTabUI.DisableEvents();
                entityTabUI.ClearControls();
                entityTabUI.EnableEvents();
                AdminEvents.OnCancelCreate(null, null);
            }
            else
            {
                //reset the ODP to the old state.
                SetData(modelDetails);
                saveButton.Enabled = false;
                AdminEvents.OnCancelModify(null, null);
            }
        }

        /// <summary>
        /// Gets the LocalizeKey of each UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == titleLabel)
            {
                return control.Name + "." + Pane.GetType().Name + "." + mode;
            }

            return base.GetLocalizeKey(control);
        }

        /// <summary>
        ///  Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + Pane.GetType().Name;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, titleLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requiredLabel);

            //Localize the the inner entity UI.
            entityTabUI.Localize();

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        ///Sets the Pane to the EntityTabUI as the part of lifecycle.
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            entityTabUI.SetExecutionContext(Context);
            entityTabUI.SetPane(Pane);
        }

        public override void SetAcceptButton()
        {
            this.ParentForm.AcceptButton = saveButton;
        }

        #endregion

        #region Properties

        public Panel InnerPanel
        {
            get
            {
                return innerPanel;
            }
        }

        /// <summary>
        /// Returns the instance of Entity ODP UI
        /// </summary>
        public IAdminBaseTabUI EntityTabUI
        {
            get { return entityTabUI; }
            set
            {
                entityTabUI = value;
                Control entityUICtrl = (Control)entityTabUI;
                entityUICtrl.Dock = DockStyle.Fill;
                entityUICtrl.BackColor = Color.FromArgb(255, 255, 255);
                innerPanel.Controls.Add(entityUICtrl);
            }
        }

        public Button SaveButton
        {
            get { return saveButton; }
        }
        #endregion
    }
}
