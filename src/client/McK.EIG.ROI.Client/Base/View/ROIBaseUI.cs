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
using System.Collections.ObjectModel;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;


namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// Builds the MainMenu for the application.
    /// </summary>
    public class ROIBaseUI : UserControl, IBaseUI
    {
        #region Fields

        public const string ColumnHeader = ".columnHeader";

        private ExecutionContext context;
        private IPane pane;

        #endregion

        #region Constructor

        public ROIBaseUI()
        {
            this.GotFocus += new EventHandler(ROIBaseUI_GotFocus);
        }

        #endregion


        #region Methods

        /// <summary>
        /// When user control got focus, if all child controls of this are disabled then the focus takes to next active control of the parent form. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ROIBaseUI_GotFocus(object sender, EventArgs e)
        {
            if (this.ParentForm == null) return;
            this.ParentForm.SelectNextControl(this.ParentForm.ActiveControl, true, true, true, true);
        }

        /// <summary>
        /// Gets the localize key of the control for showing tooltip with current culture
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected virtual string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name;
        }

        /// <summary>
        /// Gets the localize key of the control for displaying control's text with current culture
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected virtual string GetLocalizeKey(Control control)
        {
            return control.Name;
        }

        protected void SetLabel(ResourceManager rm, Control control)
        {
             control.Text = rm.GetString(GetLocalizeKey(control));
        }

        public static void SetLabel(DataGridView grid, ResourceManager rm, string colName)
        {
            grid.Columns[colName].HeaderText = rm.GetString(colName + ColumnHeader);
        }

        protected void SetTooltip(ResourceManager rm, ToolTip toolTip, Control control)
        {
            toolTip.SetToolTip(control, rm.GetString(GetLocalizeKey(control, toolTip)));
        }

        public bool HandleClientError(ResourceManager rm, ROIException cause, ErrorProvider ep)
        {
            int processErrorCount = 0;
            Control errorControl;

            Collection<ExceptionData> errors = cause.GetErrorMessage(rm);
            foreach (ExceptionData error in errors)
            {
                errorControl = GetErrorControl(error);
                if (errorControl == null)
                {
                    continue;
                }

                processErrorCount++;
                SetError(error.ErrorMessage, errorControl, ep);
            }
            return (processErrorCount == errors.Count);
        }

        private static void SetError(string message, Control control, ErrorProvider ep)
        { 
            ep.SetError(control, message);
        }

        /// <summary>
        /// Based on errorCode switch case has to be done and control should be returned.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public virtual Control GetErrorControl(ExceptionData error)
        {
            return null;
        }

        public void SetExecutionContext(ExecutionContext context)
        {
            this.context = context;
        }

        public virtual void SetPane(IPane pane)
        {
            this.pane = pane;
        }
       
        public virtual void Localize()
        {
        }

        /// <summary>
        /// Occurs when control becomes the active control of the form.
        /// 
        /// </summary>
        /// <param name="e"></param>
        protected override void OnEnter(EventArgs e)
        {
            SetAcceptButton();
        }

        /// <summary>
        /// Sets the button on the form that is clicked when the presses the Enter key
        /// </summary>
        public virtual void SetAcceptButton()
        {
        }

        #endregion

        #region Security Rights

        protected static bool IsAllowed(string securityRightId)
        {
            return IsAllowed(securityRightId, ROISecurityRights.DefaultFacility);
        }

        protected static bool IsAllowed(string securityRightId, string facility)
        {
            return ROIViewUtility.IsAllowed(securityRightId, facility);
        }

        protected static bool IsAllowed(Collection<string> securityRights)
        {
            return IsAllowed(securityRights, true, ROISecurityRights.DefaultFacility);
        }

        protected static bool IsAllowed(Collection<string> securityRights, bool checkAll)
        {
            return IsAllowed(securityRights, checkAll, ROISecurityRights.DefaultFacility);
        }

        protected static bool IsAllowed(Collection<string> securityRights, string facility)
        {
            return IsAllowed(securityRights, true, facility);
        }

        protected static bool IsAllowed(Collection<string> securityRights, bool checkAll, string facility)
        {
            return ROIViewUtility.IsAllowed(securityRights, checkAll, facility);
        }

        #endregion

        #region Properties

        protected ExecutionContext Context
        {
            get { return context; }
            set { context = value; }
        }

        protected IPane Pane
        {
            get { return pane; }
        }
        #endregion
    }
}
