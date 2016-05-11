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
    /// Maintain Requestor profile 
    /// </summary>
    public partial class ViewEditRequestorUI : ROIBaseUI
    {
        #region Fields

        private HeaderUI header;
        private RequestorInfoEditor requestorInfoEditor;
        
        private EventHandler cancelHandler;
        private EventHandler saveRequestorHandler;

        private RequestorDetails requestor;

        private bool requestReleased;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the Ui controls
        /// </summary>
        public ViewEditRequestorUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Initialize the action handlers
        /// </summary>
        /// <param name="saveNewPatientRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public ViewEditRequestorUI(EventHandler saveRequestorHandler, EventHandler cancelHandler, IPane pane)
            : this()
        {
            this.saveRequestorHandler = saveRequestorHandler;
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
            SetLabel(rm, requiredLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, revertButton);
            SetLabel(rm, closeButton);
            SetLabel(rm, requestorInfoTabPage);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, revertButton);
            SetTooltip(rm, toolTip, closeButton);
        }

        /// <summary>
        /// Gets localized key for UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control is TabPage || control is Label)
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
        public void InitRequestorInfoTabPage(RequestorDetails requestorInfo, bool requestReleased)
        {
            requestorInfoEditor = new RequestorInfoEditor();
            this.requestReleased = requestReleased;

            requestor = RetrieveRequestorInfo(requestorInfo); ;

            requestorInfoEditor.RequestorInfo = requestor;

            requestorInfoEditor.Init(Context);
            requestorInfoEditor.Localize();
            requestorInfoEditor.PrePopulate();

            requestorInfoEditor.UpperComponent.HeaderPanel.Visible = false;
            requestorInfoEditor.UpperComponent.BottomPanel.Visible = false;

            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            infoUI.DirtyDataHandler = new EventHandler(MarkDirty);
            infoUI.EnableEvents();
            infoUI.RequestorTypeCombo.Enabled = !requestReleased && requestor.IsActive;
            infoUI.InactiveCheckBox.Enabled = false;
            if (ROIConstants.MigrationRequest.Equals(requestor.TypeName))
            {
                infoUI.RequestorTypeCombo.Enabled = false;
            }

            infoUI.HideFooter = true;
            EnableButtons(false);
            SetRequestorTab(requestorInfoTabPage, requestorInfoEditor.View as Control);
            requestorProfileTabControl.SelectedIndex = 0;

            ApplySecurityRights();
        }

        private RequestorDetails RetrieveRequestorInfo(RequestorDetails requestor)
        {
            try
            {
                ROIViewUtility.MarkBusy(true);
                requestor = RequestorController.Instance.RetrieveRequestor(requestor.Id, false);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            }

            return requestor;
        }

        /// <summary>
        /// Create a dialog header
        /// </summary>
        private void CreateDialogHeader()
        {
            Header = new HeaderUI();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Header.Title = rm.GetString("vieweditrequestor.header.title");
            Header.Information = rm.GetString("vieweditrequestor.header.info");
        }

        /// <summary>
        /// Handler is used to close the dialog.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            if (infoUI.IsDirty && !ROIViewUtility.ConfirmDiscardChanges(Context))
            {
                return;
            }
            cancelHandler(this, null);
        }


        /// <summary>
        /// Save the requestor details
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveRequestorButton_Click(object sender, EventArgs e)
        {
            
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            try
            {
                ROIViewUtility.MarkBusy(true);
                infoUI.errorProvider.Clear();
                RequestorDetails requestor = infoUI.SaveRequestor();
                if (requestor != null)
                {
                    infoUI.SetData(requestor);
                    infoUI.RequestorInfo = requestor;
                    infoUI.DisableControls(requestor.IsActive);
                    infoUI.RequestorTypeCombo.Enabled = !requestReleased && requestor.IsActive;

                    changedRequestorIsPatient = infoUI.RequestorInfo.Type == RequestorDetails.PatientRequestorType && 
                                                this.requestor.Type != RequestorDetails.PatientRequestorType;

                    if (infoUI.ContactAddressUpdated)
                    {
                        doUpdateContactAndAddressInfo = RequestInfoUI.ShowOverrideRequestInformationDialog(Context);
                    }

                    this.requestor = requestor;
                    saveRequestorHandler(this, new BaseEventArgs(requestor));
                }

                EnableButtons(false);
                if (ROIConstants.MigrationRequest.Equals(requestor.TypeName))
                {
                    infoUI.RequestorTypeCombo.Enabled = false;
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

        private void MarkDirty(object sender, EventArgs e)
        {
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            infoUI.IsDirty = true;
            EnableButtons(true);
        }

        private void EnableButtons(bool enable)
        {
            revertButton.Enabled = saveButton.Enabled = enable;
        }

        private void revertButton_Click(object sender, EventArgs e)
        {
            RequestorInfoUI infoUI = (RequestorInfoUI)requestorInfoEditor.MCP.View;
            infoUI.RevertRequestorInfo();
            infoUI.IsDirty = false;
            EnableButtons(false);
            infoUI.RequestorTypeCombo.Enabled = !requestReleased && requestor.IsActive;
            if (ROIConstants.MigrationRequest.Equals(requestor.TypeName))
            {
                infoUI.RequestorTypeCombo.Enabled = false;
            }
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

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        private void ApplySecurityRights()
        {
            ((RequestorInfoUI)requestorInfoEditor.MCP.View).InfoPanel.Enabled = IsAllowed(ROISecurityRights.ROIModifyRequest);
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

        private bool doUpdateContactAndAddressInfo;
        public bool DoUpdateContactAndAddressInfo
        {
            get { return doUpdateContactAndAddressInfo; }
            set { doUpdateContactAndAddressInfo = value; }
        }


        private bool changedRequestorIsPatient;
        public bool ChangedRequestorIsPatient
        {
            get { return changedRequestorIsPatient; }
            set { changedRequestorIsPatient = value; }
        }
        
        #endregion

    }
}
