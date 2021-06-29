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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.View;
using McK.EIG.ROI.Client.Patient.View.FindPatient;
using McK.EIG.ROI.Client.Reports.View;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Request.View.FindRequest;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Requestors.View.FindRequestor;
using McK.EIG.ROI.Client.OverDueInvoice.View;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// This holds all ModuleSelector Tabs.
    /// </summary>
    public class ModuleSelectorPane : ROIBaseContainerPane
    {

        #region Fields

        private AdminModulePane adminModulePane;
        private PatientModulePane patientModulePane;        
        private RequestorModulePane requestorModulePane;
        private RequestModulePane requestModulePane;
        private ReportModulePane reportModulePane;
        private OverDueInvoiceModulePane pastDueInvoiceModulePane;

        private TabControl msp;
        private TabControlCancelEventHandler deselectTab;
        private EventHandler selectTab;
        private EventHandler navigationHandler;
        private ResourceManager uiTextRM;
        private string prevTabName;
        private int prevTabIndex;

        #endregion

        #region Methods

        /// <summary>
        /// Returns all the children type of module selector.
        /// </summary>
        /// <returns></returns>        
        protected override Type[] GetChildrenTypes()
        {
            return new Type[0];
        }

        /// <summary>
        ///  Initializes the module selector.
        /// </summary>
        protected override void InitComponent()
        {
            msp = new TabControl();
            msp.TabStop = true;
            uiTextRM = CultureManager.GetCulture(CultureType.UIText.ToString());
   
            // Create module tabs
            CreateTab(ROIConstants.PatientsModuleName);
            CreateTab(ROIConstants.RequestorModuleName);
            CreateTab(ROIConstants.RequestsModuleName);

            if (ROIViewUtility.IsAllowed(ROISecurityRights.ROIReports))
            {
                CreateTab(ROIConstants.ReportsModuleName);
            }
            //Change the order of tab, display prior to Administration tab
            CreateTab(ROIConstants.OverDueInvoiceModuleName);
            if (ROIViewUtility.IsAllowed(ROISecurityRights.ROIAdministration))
            {
                CreateTab(ROIConstants.AdminModuleName);
            }            
            msp.Dock = DockStyle.Fill;            
        }

        public void ApplyDefault()
        {
            msp.SelectedIndex = 0;
            tabControl_SelectionChanged(msp, null);
        }
      
        /// <summary>
        /// Event subscription.
        /// </summary>
        protected override void Subscribe()
        {
            // initiate all the EventHandler's
            selectTab   = new EventHandler(tabControl_SelectionChanged);
            deselectTab = new TabControlCancelEventHandler(tabControl_Deselecting);
            navigationHandler   = new EventHandler(OnNavigationChange);
            

            // subsribe for events
            msp.SelectedIndexChanged   += selectTab;
            msp.Deselecting            += deselectTab;
            ROIEvents.NavigationChange += navigationHandler;
        }

        /// <summary>
        /// Event unsubscription.
        /// </summary>
        protected override void Unsubscribe()
        {
            msp.SelectedIndexChanged   -= selectTab;
            msp.Deselecting            -= deselectTab;
            ROIEvents.NavigationChange -= navigationHandler;
        }

        public override void Localize()
        {
            foreach (TabPage tab in msp.TabPages)
            {
                tab.Text = uiTextRM.GetString(tab.Name);
            }
            base.Localize();
        }

        string navPath;
        private void OnNavigationChange(object sender, EventArgs e)
        {
            ApplicationEventArgs ae = e as ApplicationEventArgs;
            string navigationName = (ae.Info as string);
            int index = navigationName.IndexOf('.');
            string moduleName = (index == -1) ? navigationName : navigationName.Substring(0, index);
            this.navPath = navigationName;
            msp.SelectTab(moduleName);
            navPath = String.Empty;
        }

        private void tabControl_SelectionChanged(object sender, EventArgs e)
        {
            string selectedModule = msp.SelectedTab.Name;
            msp.TabStop = false;

            ChangeTitle(selectedModule);
            
            if (selectedModule == ROIConstants.AdminModuleName)
            {
                if (adminModulePane == null)
                {
                    adminModulePane = new AdminModulePane();
                    adminModulePane.Init(Context);
                    adminModulePane.Localize();
                    msp.TabPages[ROIConstants.AdminModuleName].Controls.Add((Control)adminModulePane.View);
                    SubPanes.Add(adminModulePane);
                    try
                    {
                    adminModulePane.ApplyDefault(navPath);
                    }
                    catch (ROIException cause)
                    {
                        msp.SelectedIndex = prevTabIndex;
                        ChangeTitle(prevTabName);
                        msp.TabPages[ROIConstants.AdminModuleName].Controls.Remove((Control)adminModulePane.View);
                        SubPanes.Remove(adminModulePane);
                        adminModulePane.Cleanup();
                        adminModulePane = null;                        
                        ROIViewUtility.Handle(Context, cause);
                    }
                }
                else
                {
                    adminModulePane.RSP.SetFocus();
                }
            }
            else if (selectedModule == ROIConstants.PatientsModuleName)
            {
                if (patientModulePane == null)
                {
                    patientModulePane = new PatientModulePane();
                    patientModulePane.Init(Context);
                    patientModulePane.Localize();
                    msp.TabPages[ROIConstants.PatientsModuleName].Controls.Add((Control)patientModulePane.View);
                    SubPanes.Add(patientModulePane);
                    patientModulePane.ApplyDefault();
                }
                else
                {
                    patientModulePane.RSP.SetFocus();
                }
            }
            else if (selectedModule == ROIConstants.RequestorModuleName)
            {
                if (requestorModulePane == null)
                {
                    requestorModulePane = new RequestorModulePane();
                    requestorModulePane.Init(Context);
                    requestorModulePane.Localize();
                    msp.TabPages[ROIConstants.RequestorModuleName].Controls.Add((Control)requestorModulePane.View);
                    SubPanes.Add(requestorModulePane);
                    try
                    {
                        // Code changes for CR# 355137, Keane,28-Sep-2011
                    requestorModulePane.ApplyDefault();
                    }
                    catch (ROIException cause)
                    {
                        // Code changes for CR# 355137, Keane,28-Sep-2011
                        msp.SelectedIndex = prevTabIndex;
                        ChangeTitle(prevTabName);
                        msp.TabPages[ROIConstants.RequestorModuleName].Controls.Remove((Control)requestorModulePane.View);
                        SubPanes.Remove(requestorModulePane);
                        requestorModulePane.Cleanup();
                        requestorModulePane = null;                        
                        ROIViewUtility.Handle(Context, cause);
                    }                    
                }
                else
                {
                    requestorModulePane.RSP.SetFocus();
                }
            }
            else if (selectedModule == ROIConstants.RequestsModuleName)
            {
                if (requestModulePane == null)
                {
                    requestModulePane = new RequestModulePane();
                    requestModulePane.Init(Context);
                    requestModulePane.Localize();
                    msp.TabPages[ROIConstants.RequestsModuleName].Controls.Add((Control)requestModulePane.View);
                    SubPanes.Add(requestModulePane);
                    requestModulePane.ApplyDefault();
                }
                else
                {
                    requestModulePane.RSP.SetFocus();
                }
            }
            else if (selectedModule == ROIConstants.ReportsModuleName)
            {
                if (reportModulePane == null)
                {
                    reportModulePane = new ReportModulePane();
                    reportModulePane.Init(Context);
                    reportModulePane.Localize();
                    msp.TabPages[ROIConstants.ReportsModuleName].Controls.Add((Control)reportModulePane.View);
                    SubPanes.Add(reportModulePane);
                    reportModulePane.ApplyDefault(navPath);
                }
                else
                {
                    ((CollapsibleBar)reportModulePane.LSP.View).Controls[0].Controls[0].Controls[1].Focus();
                }
            }
            else if (selectedModule == ROIConstants.OverDueInvoiceModuleName)
            {
                if (pastDueInvoiceModulePane == null)
                {
                    pastDueInvoiceModulePane = new OverDueInvoiceModulePane();
                    pastDueInvoiceModulePane.Init(Context);
                    pastDueInvoiceModulePane.Localize();
                    msp.TabPages[ROIConstants.OverDueInvoiceModuleName].Controls.Add((Control)pastDueInvoiceModulePane.View);
                    SubPanes.Add(pastDueInvoiceModulePane);
                    pastDueInvoiceModulePane.ApplyDefault();
                }
                else
                {
                    pastDueInvoiceModulePane.RSP.SetFocus();
                }
            }
            navPath = String.Empty;
            prevTabName= msp.SelectedTab.Name;
            prevTabIndex = msp.SelectedIndex;
           
        }

        private void ChangeTitle(string moduleName)
        {
            ResourceManager rm = CultureManager.GetCulture(CultureType.UIText.ToString());            
            msp.Parent.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, 
                                            rm.GetString("app.title"), 
                                            new object[] { rm.GetString(moduleName) }); 
        }

        /// <summary>
        /// Occurs when deselecting of tab page.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void tabControl_Deselecting(object sender, TabControlCancelEventArgs e)
        {
            ApplicationEventArgs ae = new ApplicationEventArgs(sender, e);
            if (!TransientDataValidator.Validate(ae))
            {
                e.Cancel = true;
            }
        }

        /// <summary>
        /// Creates the TabPage and add to the TabControl.
        /// </summary>
        /// <param name="modulePane"></param>
        /// <param name="title"></param>
        private void CreateTab(string tabName)
        {
            TabPage tPage = new TabPage();

            tPage.Padding   = new Padding(3);
            tPage.BackColor = Color.FromArgb(221, 231, 253);
            tPage.Name      = tabName;
            tPage.Text      = uiTextRM.GetString(tabName);            
            msp.TabPages.Add(tPage);
        }

        #endregion

        #region Properties
        /// <summary>
        /// Returns the view of ModuleSelectorPane.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get { return msp; }
        }

        #endregion

    }
}
