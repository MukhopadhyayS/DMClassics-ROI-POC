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

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;

namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{

    /// <summary>
    /// Maintain PatientRequestor profile 
    /// </summary>
    public partial class SaveNewPatientRequestorUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;
        private RequestorInfoEditor requestorInfoEditor;
        
        private EventHandler cancelHandler;
        private EventHandler saveNewPatientRequestorHandler;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the Ui controls
        /// </summary>
        public SaveNewPatientRequestorUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Initialize the action handlers
        /// </summary>
        /// <param name="saveNewPatientRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public SaveNewPatientRequestorUI(EventHandler saveNewPatientRequestorHandler, EventHandler cancelHandler, IPane pane)
            : this()
        {
            this.saveNewPatientRequestorHandler = saveNewPatientRequestorHandler;
            this.cancelHandler = cancelHandler;

            SetPane(pane);
            SetExecutionContext(pane.Context);
            CreateDialogHeader();
            Localize();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, saveNewRequestorButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, requestorInfoTabPage);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, saveNewRequestorButton);
            SetTooltip(rm, toolTip, cancelButton);
        }

        /// <summary>
        /// Gets localized key for UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control is TabPage)
            {
                return base.GetLocalizeKey(control);
            }

            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets localized key for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Initialize the Requestor Information tab page
        /// </summary>
        /// <param name="requestorInfo"></param>
        public void InitRequestorInfoTabPage(RequestorDetails requestorInfo)
        {
            requestorInfoEditor = new RequestorInfoEditor();
            
            requestorInfoEditor.RequestorInfo = requestorInfo;
            requestorInfoEditor.Init(Context);
            requestorInfoEditor.Localize();
            requestorInfoEditor.PrePopulate();

            requestorInfoEditor.UpperComponent.HeaderPanel.Visible = false;
            requestorInfoEditor.UpperComponent.BottomPanel.Visible = false;
            
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            infoUI.HideFooter = true;
            infoUI.InactiveCheckBox.Enabled = false;
            
            SetRequestorTab(requestorInfoTabPage, requestorInfoEditor.View as Control);
            requestorProfileTabControl.SelectedIndex = 0;
        }

        /// <summary>
        /// Create a dialog header
        /// </summary>
        private void CreateDialogHeader()
        {
            Header = new HeaderUI();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Header.Title = rm.GetString("savenewpatientrequestor.header.title");
            Header.Information = rm.GetString("savenewpatientrequestor.header.info");
        }

        /// <summary>
        /// Handler is used to close the dialog.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            cancelHandler(this, null);
        }

        /// <summary>
        /// Save the patient as requestor
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveNewRequestorButton_Click(object sender, EventArgs e)
        {
            
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            try
            {
                ROIViewUtility.MarkBusy(true);
                infoUI.errorProvider.Clear();
                infoUI.RequestorInfo = null;
                RequestorDetails requestor = infoUI.SaveRequestor() as RequestorDetails;
                if (requestor != null)
                {
                    infoUI.RequestorInfo = requestor;
                    saveNewPatientRequestorHandler(this, new BaseEventArgs(requestor));
                }
            }
            catch (ROIException cause)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (!infoUI.HandleClientError(rm, cause, infoUI.errorProvider))
                {
                    ROIViewUtility.Handle(Context, cause);
                }
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }
        }

        public override void SetAcceptButton()
        {
            if (this.ParentForm != null)
                this.ParentForm.AcceptButton = saveNewRequestorButton;
        }

        private static void SetRequestorTab(TabPage tabPage, Control editor)
        {
            editor.Dock = DockStyle.Fill;
            tabPage.Controls.Clear();
            tabPage.Controls.Add(editor);
        }

        public void CleanUp()
        {
            requestorInfoEditor.MCP.Cleanup();
        }

        #endregion

        #region Properties

        public HeaderUI Header
        {
            get { return header; }
            set
            {
                header = value;
                header.Dock = DockStyle.Fill;
                topPanel.Controls.Add(header);
            }
        }
        
        #endregion
       
    }
}
