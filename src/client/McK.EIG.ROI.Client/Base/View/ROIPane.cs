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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Configuration;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Reflection;
using System.Resources;
using System.Security.Permissions;
using System.Windows.Forms;
using System.Xml;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Web_References.SigninWS;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// This is Main Pane Which Hold all panes.
    /// </summary>
    public class ROIPane : ROIBaseContainerPane, IMessageFilter
    {
        private Log log = LogFactory.GetLogger(typeof(ROIPane));

        #region Fields

        const int WM_KEYDOWN = 0x0100;
        const int WM_LBUTTONDOWN = 0x201;
        const int WM_LBUTTONUP = 0x202;
        const int WM_MOUSEMOVE = 0x200;

        private const int idleSeconds = 60000; //milliseconds

        private Timer idleTimer;
        private Timer HPFRefreshTimer;
        private ArrayList supportedCultures;

        private EventHandler cultureChangeHandler;
        private EventHandler logonClickHandler;

        private Form appPane;
        private Form helpDialog;

        private string helpUrl;
        private WebBrowser browser;
        private Hashtable helpIndices;        

        #endregion

        #region Methods
        /// <summary>
        /// This method will initialize application context, view and subscriptions for a pane.
        /// </summary>
        /// <param name="exeContext"></param>
        public override void Init(ExecutionContext context)
        {

            GradientLabel.SelectedImage = global::McK.EIG.ROI.Client.Resources.images.nav_selected;
            CollapsiblePanel.CollapsedImage = global::McK.EIG.ROI.Client.Resources.images.nav_collapsed;
            CollapsiblePanel.ExpandedImage = global::McK.EIG.ROI.Client.Resources.images.nav_expanded;

            Context = new ExecutionContext();
            Context.Init();
            InitCulture();
            base.Init(this.Context);
            InitHelpContext();
            Localize();
        }

        /// <summary>
        /// Creates an Instance of the CultureManager class and loads all Resource Managers and add to culture manager.
        /// This method gets the supported culture entries used by ROI application. 
        /// This method checks the system culture with the supported cultures.
        /// If the system culture matches with the supported culture, then determines the system UI culture as system culture.
        /// Otherwise, this method determines the system UI culture as application default culture.
        /// It will check the current system culture with the available culture resources used in the application. 
        /// If it is available then it will apply the system culture to the application, 
        /// Otherwise it will get the application default culture.
        /// </summary>
        private void InitCulture()
        {
            CultureManager = Context.CultureManager;


            string[] cultures = Context.Configuration.GetProperty("SupportedCultures").Split(new char[] { ',' });

            supportedCultures = new ArrayList(cultures.Length);

            foreach (string culture in cultures)
            {
                supportedCultures.Add(culture);
            }

            CultureInfo currentCulture = System.Threading.Thread.CurrentThread.CurrentCulture;

            if (SupportedCultures.Contains(currentCulture.Name))
            {
                System.Threading.Thread.CurrentThread.CurrentUICulture = currentCulture;
            }
            else
            {
                System.Threading.Thread.CurrentThread.CurrentUICulture = new CultureInfo(SupportedCultures[0].ToString());
            }

            Assembly assembly = GetType().Assembly;
            string baseName = assembly.GetName().Name;

            ResourceManager rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentCulture,
                                                   "{0}{1}", baseName, Context.Configuration.GetProperty("roi.uitext.resource")),
                                                   assembly);
            CultureManager.AddCulture(CultureType.UIText.ToString(), rm);

            rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentCulture,
                                                   "{0}{1}", baseName, Context.Configuration.GetProperty("roi.messages.resource")),
                                                   assembly);
            CultureManager.AddCulture(CultureType.Message.ToString(), rm);

            rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentCulture,
                                                   "{0}{1}", baseName, Context.Configuration.GetProperty("roi.tooltip.resource")),
                                                   assembly);
            CultureManager.AddCulture(CultureType.ToolTip.ToString(), rm);
        }

        /// <summary>
        /// Returns all the Children type of ROIPane.
        /// </summary>
        /// <returns></returns>
        protected override Type[] GetChildrenTypes()
        {
            return new Type[0];
        }

        /// <summary>
        /// Initializes the ROIPane.
        /// </summary>
        protected override void InitComponent()
        {
            idleTimer = new Timer();
            HPFRefreshTimer = new Timer();
            HPFRefreshTimer.Interval = Convert.ToInt32(ConfigurationManager.AppSettings["HPFRefreshTime"], System.Threading.Thread.CurrentThread.CurrentCulture);
           
            appPane = new Form();
            //appPane.Menu = (MainMenu)SubPanes[0].View;
            appPane.Padding = new Padding(0, 10, 0, 0);
            appPane.WindowState = FormWindowState.Maximized;

            appPane.Shown += new EventHandler(appPane_Shown);
            appPane.FormClosing += new FormClosingEventHandler(appPane_FormClosing);            
        }

        private void HPFServiceCallTimer_Tick(object sender, EventArgs e)
        {   
            try
            {
                ROIController.Instance.GetConfiguration();          
            }
            catch (ROIException cause)
            {   
                log.Debug(cause.Message);
            }
        }

        protected override void Subscribe()
        {
            if (cultureChangeHandler == null)
            {
                cultureChangeHandler = new EventHandler(Process_MenuClick);
            }

            logonClickHandler = new EventHandler(Process_LogonClick);
            ROIEvents.NavigationChange += cultureChangeHandler;
            ROIEvents.LogOnClick       += logonClickHandler;
            appPane.HelpRequested   += new HelpEventHandler(HelpRequested);
        }

        protected override void Unsubscribe()
        {
            ROIEvents.NavigationChange -= cultureChangeHandler;
            ROIEvents.LogOnClick       -= logonClickHandler;
        }

        private void appPane_FormClosing(object sender, FormClosingEventArgs e)
        {
            try
            {
                deleteTemporaryFiles();
                if (SubPanes.Count > 0)
                {
					//If false, application will Restart
                    ((MenuPane)SubPanes[0]).ReleaseInUseRecords(false);
                }
            }
            catch (InvalidOperationException ex)
            {
                appPane.FormClosing -= new FormClosingEventHandler(appPane_FormClosing);
                log.Info(ex.Message);
                Application.Restart();
            }
            catch (IOException cause)
            {
                Collection<ExceptionData> errors = new Collection<ExceptionData>();
                log.FunctionFailure(cause);
                errors.Add(new ExceptionData(ROIErrorCodes.FileInUse));
                ROIViewUtility.Handle(Context, new ROIException(errors));               
                return;
            }
        }

        public void deleteTemporaryFiles() 
        {
            string tempPath = Path.Combine(Environment.CurrentDirectory, "temp");
            string csvTempPath = Path.Combine(tempPath, System.Configuration.ConfigurationSettings.AppSettings["CacheFolder"]);
            string previewPDFFilePath = tempPath + BillingController.DirectoryPath;                
            try
            {
                log.Info("Deleting cached report CSV files");
                if (Directory.Exists(csvTempPath))
                {
                    Directory.Delete(csvTempPath, true);
                }
            }
            catch (Exception ex)
            {
                log.Error("Unable to delete cached report CSV file " + csvTempPath + " Error : " + ex.Message);
            }

            log.Info("Deleting cached temp files");

            if (Directory.Exists(previewPDFFilePath))
            {
                string[] filePaths = Directory.GetFiles(previewPDFFilePath);
                foreach (string filePath in filePaths)
                {
                    try
                    {
                        if (Validator.Validate(filePath, ROIConstants.FilepathValidation)  && filePath.StartsWith(Environment.CurrentDirectory))
                        {
                            File.Delete(filePath);
                        }
                        else
                        {
                            log.Error("Unable to delete cached temporary file " + filePath);
                        }
                    }
                    catch (Exception ex)
                    {
                        log.Error("Unable to delete cached temporary file " + previewPDFFilePath + " Error : " + ex.Message);
                    }
                }
            }
                
        }

        public override void Localize()
        {
            ResourceManager rm = CultureManager.GetCulture(CultureType.UIText.ToString());
            appPane.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentCulture,
                                         rm.GetString("app.title"),
                                         new object[] { rm.GetString("roi.title") });
            base.Localize();
        }

        private void appPane_Shown(object sender, EventArgs args)
        {
            //CreateClickOnceShortcut();
            LogOnPane logonPane = new LogOnPane();
            logonPane.Init(Context);
            logonPane.Localize();
            logonPane.ParentPane = this;
            logonPane.LogOnForm.FormClosed += new FormClosedEventHandler(LogOnUI_FormClosed);            
            logonPane.Show(appPane);
        }

        void LogOnUI_FormClosed(object sender, FormClosedEventArgs e)
        {
            try
            {
                Application.Exit();
            }
            catch (InvalidOperationException ex)
            {
                log.Info(ex.Message);
            }
        }

        private void Process_LogonClick(object sender, EventArgs args)
        {
            LogOnPane logOnPane = sender as LogOnPane;
            (logOnPane).LogOnForm.FormClosed -= new FormClosedEventHandler(LogOnUI_FormClosed);
            logOnPane.Close();
            
            MenuPane menuPane = new MenuPane();
            menuPane.Init(Context);
           //menuPane.Localize();
            menuPane.ParentPane = this;
            SubPanes.Add(menuPane);
            appPane.Menu = (MainMenu)SubPanes[0].View;

            ModuleSelectorPane msp = new ModuleSelectorPane();
            msp.Init(Context);
            msp.Localize();
            msp.ParentPane = this;
            SubPanes.Add(msp);
            appPane.Controls.Add((Control)msp.View);
            msp.ApplyDefault();

            //TODO : All the UI controls's position needs to be readjusted if the status bar to be shown

            //StatusStrip st = new StatusStrip();
            //st.Dock = DockStyle.Bottom;
            
            //ToolStripStatusLabel jobsLabel = new ToolStripStatusLabel();
            //jobsLabel.Image = McK.EIG.ROI.Client.Resources.images.print;
            //jobsLabel.Click += delegate { menuPane.ShowMonitorOutputJobDialog(); };
            //jobsLabel.Spring = true;
            //jobsLabel.ImageAlign = ContentAlignment.MiddleRight;
            //jobsLabel.DisplayStyle = ToolStripItemDisplayStyle.Image;
            //jobsLabel.BackgroundImageLayout = ImageLayout.Center;
            //st.Items.Add(jobsLabel);

            //appPane.Controls.Add(st);

            idleTimer.Tick += new EventHandler(IdleTimer_Tick);
            HPFRefreshTimer.Tick += new EventHandler(HPFServiceCallTimer_Tick);
            HPFRefreshTimer.Enabled = true;
            Application.AddMessageFilter(this);
        }

        /// <summary>
        /// Occurs when the specified timer interval has elapsed and the timer is enabled.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void IdleTimer_Tick(object sender, EventArgs e)
        {
            try
            {
                idleTimer.Enabled = false;
                idleTimer.Stop();

                WarningDialog dialog = new WarningDialog(ROIDialogIcon.Alert);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                dialog.DialogTitle = rm.GetString("warningDialog.title");
                dialog.DisplayMessageText = rm.GetString("warningDialog.warningMessage");
                dialog.OkButtonText = rm.GetString("okButton.DialogUI");
                dialog.WarningEnabled = true;
                DialogResult dialogResult = dialog.Display();
                if ((dialogResult == DialogResult.OK) || (dialogResult == DialogResult.Cancel))
                {
                    TimerInitialization();
                }
            }
            catch (InvalidOperationException ex)
            {
                idleTimer.Dispose();
                appPane.FormClosing -= new FormClosingEventHandler(appPane_FormClosing);
                log.Info(ex.Message);
                Application.Restart();
            }
        }

        private void Process_MenuClick(object sender, EventArgs args)
        {
            string name = (string)((ApplicationEventArgs)args).Info;
            if (SupportedCultures.Contains(name))
            {
                if (!System.Threading.Thread.CurrentThread.CurrentUICulture.ToString().Equals(name))
                {
                    System.Threading.Thread.CurrentThread.CurrentUICulture = new CultureInfo(name);
                    Localize();
                }
                return;
            }

            //if (ROIConstants.ExitMenu.Equals(name))
            //{
            //    Cleanup();
            //    Application.Exit();
            //}
        }


        #region HelpContext

        /// <summary>
        /// Creates an Instance of the HelpContext.
        /// </summary>
        private void InitHelpContext()
        {
            LoadHelpConfig();
            helpUrl = McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("ROI", System.Configuration.ConfigurationManager.AppSettings["HelpUrl"]);//Path.Combine(Environment.CurrentDirectory, Context.Configuration.GetProperty("HelpUrl"));
        }

        private void LoadHelpConfig()
        {
            Assembly assembly = GetType().Assembly;
            string baseName = assembly.GetName().Name;

            XmlDocument doc = new XmlDocument();
            doc.Load(Assembly.GetExecutingAssembly().GetManifestResourceStream(baseName + ".Resources.HelpConfig.xml"));
            XmlElement root = doc.DocumentElement;
            XmlNodeList eHelpIndicesList = root.SelectNodes("help-index");
            helpIndices = new Hashtable(eHelpIndicesList.Count);
            foreach (XmlElement eHelpIndice in eHelpIndicesList)
            {
                helpIndices.Add(eHelpIndice.Attributes["control"].Value, eHelpIndice.Attributes["topic-id"].Value);
            }
        }

        /// <summary>
        /// Gets the selected control for help.
        /// </summary>
        /// <param name="parent"></param>
        /// <param name="at"></param>
        /// <returns></returns>
        private Control GetActualControl(Control parent, Point at)
        {
            TabControl tabCtrl = parent as TabControl;
            if (tabCtrl == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            TabPage selectedTabPage = tabCtrl.SelectedTab;
            Control child = selectedTabPage.GetChildAtPoint(parent.PointToClient(at));
            return (child == null) ? selectedTabPage : GetActualControl(child, at);
        }

        /// <summary>
        /// Gets the help map id for the selected control.
        /// </summary>
        /// <param name="pCtrl"></param>
        /// <returns></returns>
        private string GetHelpMapId(Control pCtrl)
        {
            if (pCtrl.Parent == null)
            {
                return null;
            }

            string baseName = GetType().FullName;
            string key = baseName + "." + pCtrl.Name;

            return (helpIndices.ContainsKey(key)) ? helpIndices[key].ToString() : GetHelpMapId(pCtrl.Parent);
        }

        /// <summary>
        /// Gets the selected control for help.
        /// </summary>
        /// <param name="Controls"></param>
        /// <returns></returns>
        private Control GetFocusedControl(Control Controls)
        {
            if (Controls == null) return null;
            foreach (Control formControl in Controls.Controls)
            {
                if (formControl.Focused)
                {
                    return formControl;
                }
                if (formControl.ContainsFocus)
                {
                    return (formControl.Controls.Count == 0) ? formControl : GetFocusedControl(formControl);
                }
            }
            return null;
        }

        /// <summary>
        /// When user is requested for invoking help by pressing F1 or selecting help from menu or help icon on stats bar.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="hlpevent"></param>
        internal void HelpRequested(object sender, HelpEventArgs helpEvent)
        {
            string sMapId = string.Empty;
            Control ctrl = null;
            if (appPane != null)
            {
                ctrl = (Control.MouseButtons == MouseButtons.None) ? GetFocusedControl(appPane.ActiveControl): GetActualControl(appPane, helpEvent.MousePos);
            }
            else
            {
                InitHelpContext();
            }
            if (ctrl != null)
            {
                sMapId = GetHelpMapId(ctrl);
            }
            string hlpurl = helpUrl;
            //if (!string.IsNullOrEmpty(sMapId))
            //{
                hlpurl += "#<id=" + sMapId;
            //}
            Uri url = new Uri(hlpurl);
            ShowROIAboutHelpDialog(url.OriginalString);
        }

        /// <summary>
        /// Shows about roi help dialog window.
        /// </summary>
        private void ShowROIAboutHelpDialog(String url)
        {

            if ((helpDialog != null) && (helpDialog.Controls.Count > 0))
            {
                helpDialog.Focus();
                return;
            }
            else
            {
                helpDialog = new Form();
                helpDialog.Size = Screen.PrimaryScreen.WorkingArea.Size;
                helpDialog.StartPosition = FormStartPosition.CenterScreen;
                browser = new System.Windows.Forms.WebBrowser();
                browser.ScriptErrorsSuppressed = true;
                browser.Dock = DockStyle.Fill;
                helpDialog.Controls.Clear();
                helpDialog.Controls.Add(browser);
                browser.Navigate(url);
                helpDialog.Show();
            }
        }

        #region IMessageFilter Members

        [SecurityPermission(SecurityAction.LinkDemand, Flags = SecurityPermissionFlag.UnmanagedCode)]
        public bool PreFilterMessage(ref Message m)
        {
            if (m.Msg == WM_KEYDOWN || m.Msg == WM_LBUTTONDOWN || m.Msg == WM_LBUTTONUP || m.Msg == WM_MOUSEMOVE)
            {
                TimerInitialization();
            }
            return false;
        }

        #endregion

        private void TimerInitialization()
        {
            idleTimer.Stop();
            if (UserData.Instance.IdleSpecified)
            {   
               idleTimer.Interval = UserData.Instance.IdleTime * idleSeconds - 30000;                
            }
            else
            {
                idleTimer.Interval = Convert.ToInt32(ConfigurationManager.AppSettings["ApplicationIdle"], System.Threading.Thread.CurrentThread.CurrentCulture) - 300000;
            }
            idleTimer.Start();
        }

        #endregion

        #endregion


        //#region Click Once

        //private static void CreateClickOnceShortcut()
        //{
        //    if (ApplicationDeployment.IsNetworkDeployed)
        //    {
        //        ApplicationDeployment ad = ApplicationDeployment.CurrentDeployment;
        //        if (ad.IsFirstRun)
        //        {
        //            Assembly code = Assembly.GetExecutingAssembly();
        //            string product = string.Empty;
        //            string publisherName = string.Empty;

        //            if (Attribute.IsDefined(code, typeof(AssemblyProductAttribute)))
        //            {
        //                product = ((AssemblyProductAttribute)Attribute.GetCustomAttribute(code, typeof(AssemblyProductAttribute))).Product;
        //            }

        //            if (Attribute.IsDefined(code, typeof(AssemblyDescriptionAttribute)))
        //            {
        //                publisherName = ((AssemblyDescriptionAttribute)Attribute.GetCustomAttribute(code, typeof(AssemblyDescriptionAttribute))).Description;
        //            }

        //            string desktopPath = string.Concat(Environment.GetFolderPath(Environment.SpecialFolder.Desktop),
        //                   "\\", product, ".appref-ms");
        //            string shortcutName = string.Concat(Environment.GetFolderPath(Environment.SpecialFolder.Programs),
        //                  "\\", "ROI", "\\", product, ".appref-ms");
        //            System.IO.File.Copy(shortcutName, desktopPath, true);
        //        }
        //    }
        //}

        //#endregion

        #region Properties
        /// <summary>
        /// Returns the view of ROIPane.
        /// </summary>
        /// <returns></returns>
        public override Component View
        {
            get { return appPane; }
        }

        /// <summary>
        /// Supported Cultures
        /// </summary>
        public ArrayList SupportedCultures
        {
            get { return supportedCultures; }
        }

        #endregion     
    }
}
