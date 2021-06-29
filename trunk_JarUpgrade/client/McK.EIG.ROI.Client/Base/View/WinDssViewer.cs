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
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Printing;
using System.Globalization;
using System.IO;
using System.Net;
using System.Windows.Forms;

using McKesson.Capture.Model;
using McK.EIG.Common.Utility.Logging;
using McKesson.EIG.WinDSS.API;
using McKesson.EIG.WinDSS.Controller;
using McKesson.EIG.WinDSS.Model;
using McKesson.EIG.WinDSS.View;
using Microsoft.Win32;

using McK.EIG.Common.Audit.Controller;
using McK.EIG.Common.Audit.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    /// <summary>
    /// WinDssViewer;
    /// </summary>
    public class WinDocumentViewer : IDisposable
    {
        #region Fields
        private Log log = LogFactory.GetLogger(typeof(WinDocumentViewer));

        private WinDSSApi wapi;
        private int pageIndex;
        private int pageNumber;
        private string encounterName = string.Empty;
        private string encounterDocumentVersionKey = string.Empty;
        
        private DocumentContainer documentContainer;
        private IList<PageDetails> pages;
        private PatientDetails patient;
        private EncounterDetails encounterDetails;
        private DocumentDetails documentDetails;
        private VersionDetails versionDetails;
        private Hashtable encounter = new Hashtable();

        private const string global = "Global";
        private const string helpFilePath = @"\DSS WebHelp\help.htm";
        private const string HPFInstalledDir = @"SOFTWARE\IMNET\INSTALLEDAPPS";
        private const string InstalledDirKey = "InstallDir";
        private const string HPFWInstalledPath = @"C:\imnet\";

        private PrintDocument printDocument;
        private PrintDialog printDialog;
        private int pageCount;
        private long requestId;
        private bool isPrintEnabled;
        private bool authReq;
        #endregion
        
        #region Constructor

        public WinDocumentViewer()
        {
            wapi = new WinDSSApi("","");
        }

        #endregion

        #region Methods

        public void CloseViewer()
        {
            wapi.CloseAllViewers();
            wapi = null;
        }

        public void AuthReq(bool val)
        {
            authReq = val;
        }

        public void ViewPages(IList<PageDetails> pages, PatientDetails patientDetails, Hashtable encounter)
        {
            this.pages = pages;
            this.patient = patientDetails;
            this.encounter = encounter;
            pageIndex = 0;
            pageNumber = 1;
            DisplayCurrentPage();
        }

        public void ViewPages(IList<PageDetails> pages, long requestId)
        {
            isPrintEnabled = true;
            this.requestId = requestId;
            ViewPages(pages, null, null);            
        }
       
        private void DisplayCurrentPage(bool navigatePrevious, bool navigateNext)
        {
            DocumentRequest firstPage = null;
            DocumentRequest secondPage = null;
            encounterDetails = null;
            string subtitle = string.Empty;

            string firstPageHeaderText = string.Empty;
            string secondPageHeaderText = string.Empty;
            if (documentContainer != null)
            {
                CloseViewer();
            }
            if (pageIndex < 0) pageIndex = 0;

            if (pageIndex > pages.Count - 1) pageIndex = pages.Count - 1;

            if (patient != null)
            {
                if (pages[pageIndex].Parent.Parent.Parent is EncounterDetails)
                {
                    encounterDetails = (EncounterDetails)pages[pageIndex].Parent.Parent.Parent;
                    documentDetails = (DocumentDetails)pages[pageIndex].Parent.Parent;
                    versionDetails = (VersionDetails)pages[pageIndex].Parent;

                    string key = encounterDetails.Name + documentDetails.DocumentType.Id + versionDetails.Name;
                    if (key != encounterDocumentVersionKey)
                    {
                        if ((navigateNext) || (pageIndex == 0))
                        {
                            pageNumber = 1;
                        }

                        if ((navigatePrevious) || (pageIndex >= pages.Count - 1))
                        {
                            pageNumber = Convert.ToInt32(encounter[key], System.Threading.Thread.CurrentThread.CurrentUICulture);
                        }                     
                    }
                    if (pageIndex >= pages.Count - 1)
                    {
                        pageNumber = Convert.ToInt32(encounter[key], System.Threading.Thread.CurrentThread.CurrentUICulture);
                    }
                    encounterName = encounterDetails.Name;
                    encounterDocumentVersionKey = key;
                }
                else if (pages[pageIndex].Parent.Parent.Parent is PatientGlobalDocument)
                {
                    documentDetails = (DocumentDetails)pages[pageIndex].Parent.Parent;                    
                    versionDetails = (VersionDetails)pages[pageIndex].Parent;                    
                    string key = global + documentDetails.DocumentType.Id + versionDetails.Name;

                    if (encounterName != global)
                    {
                        if ((navigateNext) || (pageIndex == 0))
                        {
                            pageNumber = 1;
                        }
                    }

                    if (navigatePrevious)
                    {
                        if (key != encounterDocumentVersionKey)
                        {
                            pageNumber = Convert.ToInt32(encounter[key], System.Threading.Thread.CurrentThread.CurrentUICulture);
                        }
                    }
                    else
                    {
                        if (encounterName + documentDetails.DocumentType.Id + versionDetails.Name != encounterDocumentVersionKey)
                        {
                            pageNumber = 1;
                        }
                    }                    
                    encounterName = global;
                    encounterDocumentVersionKey = key;
                }

                //Audit the pages viewed in the windss viewer
                AuditEvent auditEvent = new AuditEvent();

                auditEvent.ActionCode = ROIConstants.ViewActionCode;
                auditEvent.UserId = UserData.Instance.UserInstanceId;
                auditEvent.EventStart = System.DateTime.Now;
                auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                auditEvent.EventId = 1;
                auditEvent.Facility = patient.FacilityCode;
                auditEvent.Mrn = patient.MRN;
                if (encounterDetails != null)
                {
                    auditEvent.Encounter = encounterDetails.Name;
                }
                subtitle = documentDetails.DocumentType.Subtitle;
                auditEvent.Comment = documentDetails.Name + " - " + subtitle + " - " +
                                     DateTime.Now.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + " - " + "Page " + pages[pageIndex].PageNumber;

                try
                {
                    Application.DoEvents();
                    ROIController.Instance.CreateAuditEntry(auditEvent);
                }
                catch (ROIException cause)
                {
                    log.FunctionFailure(cause);
                }                     
            }

            firstPage = GetDocumentRequest(pages[pageIndex]);

            firstPage.IsFirstPage = (pageIndex == 0);
            firstPage.IsLastPage = (pageIndex == pages.Count - 1);
            //firstPage.PageNumber = (pageIndex + 1);
            //firstPage.PageNumber = pages[pageIndex].PageNumber;
            firstPage.PageIndex = pageIndex;
            
            if (patient != null)
            {
                if (encounterName != global)                
                {  
                    firstPageHeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "Patient: {0} -- Facility: {1}, MRN: {2}, Encounter: {3}", patient.Name, patient.FacilityCode, patient.MRN, encounterName);
                }
                else
                {
                    firstPageHeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "Patient: {0} -- Facility: {1}, MRN: {2}, {3}", patient.Name, patient.FacilityCode, patient.MRN, encounterName);
                }
            }
            else
            {
                firstPageHeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "Authorization Request: Page: {0} of {1}", pages[pageIndex].PageNumber, pages.Count);
                //AuthorizationRequestAudit(ROIConstants.ViewActionCode);
            }

            firstPage.WindowTitle = firstPageHeaderText;
            
            if (pages.Count - 1 > pageIndex)
            {
                firstPage.HasMorePages = true;
                secondPage = GetDocumentRequest(pages[pageIndex + 1]);

                secondPage.IsFirstPage = false;
                secondPage.IsLastPage = (pageIndex + 1 == pages.Count - 1);
                secondPage.HasMorePages =  (pages.Count - 2 > pageIndex);               
                //secondPage.PageNumber = (pageIndex + 2);
                //secondPage.PageNumber = pages[pageIndex + 1].PageNumber;
                secondPage.PageIndex = pageIndex + 1;

                if (patient != null)
                {   
                    if (!(pages[pageIndex + 1].Parent.Parent.Parent is PatientGlobalDocument))
                    {                       
                        encounterDetails = (EncounterDetails)pages[pageIndex + 1].Parent.Parent.Parent;

                        secondPageHeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "Patient: {0} -- Facility: {1}, MRN: {2}, Encounter: {3}", patient.Name, patient.FacilityCode, patient.MRN, encounterDetails.Name);
                    }
                    else
                    {
                        secondPageHeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "Patient: {0} -- Facility: {1}, MRN: {2}, {3}", patient.Name, patient.FacilityCode, patient.MRN, encounterName);
                    }
                }
                else
                {
                    secondPageHeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, "Authorization Request: Page: {0} of {1}", pages[pageIndex + 1].PageNumber, pages.Count);
                }
                secondPage.WindowTitle = secondPageHeaderText;
            }

            if (patient != null)
            {
                if (pages[pageIndex].Parent.Parent is DocumentDetails)
                {
                    documentDetails = (DocumentDetails)pages[pageIndex].Parent.Parent;                   
                    firstPage.DocumentName = documentDetails.Name;                    
                    firstPage.DocDateTime = documentDetails.DocumentType.DateOfService.ToString();
                }

                if (pages.Count - 1 > pageIndex)
                {
                    documentDetails = (DocumentDetails)pages[pageIndex + 1].Parent.Parent;                    
                    secondPage.DocumentName = documentDetails.Name;                    
                    secondPage.DocDateTime = documentDetails.DocumentType.DateOfService.ToString();
                }
            }
           
            if (documentContainer == null)
            {
                DSSConfigurationHandler.GetConfiguration(typeof(LocalConfiguration));

                if (wapi == null)
                {
                    wapi = new WinDSSApi("", "");
                }

                if (OCSecurityWrapper.IsDLLInitialized())
                {
                    OCSecurityWrapper.GetSecureToken();
                }

                wapi.HPFUser=  UserData.Instance.JsessionID;
                wapi.HPFPassword = "ROI_CLIENT" + "CApp" + UserData.Instance.RSAToken;

                documentContainer = wapi.DisplayDocument(firstPage, secondPage);

                // Code changes for DM-5162 ( Annotation and Edit menu are no more exist)
                MenuItem menuItem = documentContainer.ControlsMenu.MenuItems[4].MenuItems[0];                
                documentContainer.ControlsMenu.MenuItems[4].MenuItems.Remove(menuItem);               
                MenuItem contents = new MenuItem(menuItem.Text, new EventHandler(mnuHelp_Click));
                documentContainer.ControlsMenu.MenuItems[4].MenuItems.Add(0, contents);
                
                documentContainer.OnNavigateFirstpage += new NavigateFirstpageHandler(documentContainer_OnNavigateFirstpage);
                documentContainer.OnNavigateLastpage += new NavigateLastpageHandler(documentContainer_OnNavigateLastpage);
                documentContainer.OnNavigateNextpage += new NavigateNextpageHandler(documentContainer_OnNavigateNextpage);
                documentContainer.OnNavigatePreviouspage += new NavigatePreviouspageHandler(documentContainer_OnNavigatePreviouspage);
                documentContainer.OnNavigateGoBack += new NavigateGoBackHandler(documentContainer_OnNavigateGoBack);
                documentContainer.OnPrint += new PrintHandler(documentContainer_OnPrint);                
                documentContainer.OnClose += new CloseHandler(documentContainer_OnClose);
                documentContainer.PrintEnabled = isPrintEnabled;
                documentContainer.OnNavigateGoTopage +=new NavigateGoTopageHandler(documentContainer_OnNavigateGoTopage);
                documentContainer.NavigationEnabled = (pages.Count > 1); 
            }
            else
            {
                
                if (OCSecurityWrapper.IsDLLInitialized())
                {
                    OCSecurityWrapper.GetSecureToken();
                }

                wapi.HPFUser = UserData.Instance.JsessionID;
                wapi.HPFPassword = "ROI_CLIENT" + "CApp" + UserData.Instance.RSAToken;
                documentContainer.DisplayDocument(firstPage, secondPage);
            }
        }

        private void AuthorizationRequestAudit(string actionCode, PageDetails selectedPage)
        {
            //Audit the pages viewed in the windss viewer
            AuditEvent auditEvent = new AuditEvent();

            auditEvent.ActionCode = actionCode;            
            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
            auditEvent.EventId = 1;
            auditEvent.Facility = ROIConstants.FacilityName;
			//CR# 356,927
            String comment = string.Empty;
            DocumentDetails documentDet = new DocumentDetails();
            if (null != selectedPage && null != selectedPage.Parent && null != selectedPage.Parent.Parent)
            {
                if (selectedPage.Parent.Parent is DocumentDetails)
                {
                    documentDet = (DocumentDetails)selectedPage.Parent.Parent;
                }
            }

            if (null != documentDet.Name)
            {
                comment = documentDet.Name;
            }

            comment = comment + " - ";
            if (null != documentDet.DocumentType && null != documentDet.DocumentType.Subtitle)
            {
                comment = comment + documentDet.DocumentType.Subtitle;
            }

            comment = comment + " - ";
            if (null != documentDet.DocumentDateTime)
            {
                comment = comment + documentDet.DocumentDateTime.ToString();
            }


            if (actionCode.Equals(ROIConstants.AuthRequestPrintActionCode))
            {
                comment = comment + " Local Print";
                auditEvent.Comment = comment;
            }
            else
            {
                auditEvent.Comment = comment;
            }

            try
            {
                Application.DoEvents();
                ROIController.Instance.CreateAuditEntry(auditEvent);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
            }
        }

        private void documentContainer_OnPrint()
        {
            pageCount = 0;
            printDialog = new PrintDialog();
            printDocument = new PrintDocument();
            printDialog.Document = printDocument;

            printDialog.AllowCurrentPage = true;
            printDialog.AllowSomePages = true;
            printDialog.AllowSelection = false;

            DialogResult dlgResult = printDialog.ShowDialog();            
            if (dlgResult != DialogResult.OK)
            {
                return;
            }
            else
            {
                int fromPage = printDialog.PrinterSettings.FromPage;
                int toPage = printDialog.PrinterSettings.ToPage;

                if (printDialog.PrinterSettings.PrintRange == PrintRange.AllPages)
                {
                    pageCount = 0;
                }
                else if (printDialog.PrinterSettings.PrintRange == PrintRange.SomePages)
                {
                    if ((printDialog.PrinterSettings.FromPage >= 0) || (printDialog.PrinterSettings.ToPage >= 0))
                    {
                        if ((fromPage <= pageCount) && (toPage <= pageCount))
                        {
                            pageCount = fromPage;
                        }
                    }
                }
                // CR# 356,927
                AuthorizationRequestAudit(ROIConstants.AuthRequestPrintActionCode, pages[pageCount]);                

                printDocument.PrintPage += new PrintPageEventHandler(printDocument_PrintPage);
                printDocument.Print();
            }
        }

        void printDocument_PrintPage(object sender, PrintPageEventArgs e)
        {   
            ContentRetrieval contentRetrieval = new ContentRetrieval();
            DocumentRequest documentRequest = GetDocumentRequest(pages[pageCount]);
            Content content = contentRetrieval.RetrieveContent(documentRequest);
            printContent(content, e);
        }

        private void printContent(Content content,PrintPageEventArgs e)
        {            
            Font font = new Font("Lucida Console", 11);

            if (content.DisplayType == EnumDisplayType.Image && content.Image != null)
            {
                e.Graphics.DrawImage(content.Image, e.PageSettings.Bounds.X, e.PageSettings.Bounds.Y);
            }
            else if (content.DisplayType == EnumDisplayType.Text && content.Text != null)
            {
                e.Graphics.DrawString(content.Text, font, Brushes.Black, e.PageSettings.Bounds.X, e.PageSettings.Bounds.Y);
            } 

            pageCount++;

            if(pageCount < pages.Count)
            {
                e.HasMorePages = true;                
            }
            else
            {
                e.HasMorePages = false;                
            }
        }


        private void mnuHelp_Click(object sender, EventArgs e)
        {
            ShowHelpContent();
        }

        /// <summary>
        /// Displays Help Content
        /// </summary>
        private void ShowHelpContent()
        {
            try
            {
               /* string helpURL = HPFWInstalledPath + helpFilePath;
                log.Debug("helpURL : " + helpURL);
                log.Debug("HPFInstalledDir : " + HPFInstalledDir);
                RegistryKey regKey = Registry.LocalMachine.OpenSubKey(HPFInstalledDir);
                log.Debug("regKey : " + (regKey == null ? "null" : "not null"));
                if (regKey != null)
                {
                    helpURL = Convert.ToString(regKey.GetValue(InstalledDirKey), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    
                    if (!String.IsNullOrEmpty(helpURL))
                    {
                        helpURL = Path.Combine(helpURL, helpFilePath);
                    }
                    log.Debug("helpURL in registry : " + helpURL);
                }*/

                string HelpfileURL =  McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("ROI", System.Configuration.ConfigurationManager.AppSettings["HelpWinDSSUrl"]);// Application.StartupPath + helpFilePath;
                log.Debug("HelpfileURL : " + HelpfileURL);
                
                Help.ShowHelp(this.documentContainer.ParentForm, HelpfileURL);

            }
            catch (NullReferenceException ex)
            {
                log.Debug("Exception occurred in windss help");
                log.Debug(ex.Message);
            }
        }

        private void DisplayCurrentPage()
        {
            DisplayCurrentPage(false, false);
        }

        /// <summary>
        /// Assign imnetid into document request object.
        /// </summary>
        /// <param name="imnetid"></param>
        /// <returns></returns>
        private static DocumentRequest GetDocumentRequest(PageDetails page)
        {
            DocumentRequest request = new DocumentRequest();
            request.IMNETId = page.IMNetId;
            request.PageNumber = page.ContentPageNumber;
            request.LogicalPageNumber = page.PageNumber;
            request.ParentWindowHandle = 1;
            return request;
        }

        /// <summary>
        /// Occurs when clicks on first page.
        /// </summary>
        private void documentContainer_OnNavigateFirstpage()
        {   
            //DM-133 changes to display only first page
            if (authReq)
            {
                pageIndex = 0;
                pageNumber = 1;
                DisplayCurrentPage(false, true);

                return;
            }

            // CR#365724
            int currentPageIndex = pageIndex;
            //holds the key value of the current page
            string currentPageKey = DocumentKeyName(pages[currentPageIndex]);
            //holds the key value of the previous page
            string previousPageKey = DocumentKeyName(pages[currentPageIndex - 1]);
            //holds the total number of pages in the current selected document set
            int currentTotalPages = Convert.ToInt32(encounter[currentPageKey], System.Threading.Thread.CurrentThread.CurrentUICulture);
            //holds the total number of pages in the previous document set
            int previousTotalPages = Convert.ToInt32(encounter[previousPageKey], System.Threading.Thread.CurrentThread.CurrentUICulture);
            string pageKey = string.Empty; // holds the key of the current iteration page
            bool isKeyDiffer = false;
            //used to iterate the pages until the previous page having different key with the current page
            int count1;
            // if the selected document page set doesn't having the previous page set with different key then this counter value 
            // will be set as the pageindex
            int count2;

            //if the previous page having the same key with the current page then below if loop will execute
            if (currentPageKey.Equals(previousPageKey))
            {
                count2 = 1;

                for (count1 = 0; count1 < currentTotalPages; count1++)
                {
                    //currentpageindex - count2 value should not be equal to -1 since pageindex starts with 0 index
                    if (currentPageIndex - count2 != -1)
                    {
                        pageKey = DocumentKeyName(pages[currentPageIndex - count2]);

                        if (!currentPageKey.Equals(pageKey))
                        {
                            currentPageIndex -= count1;
                            isKeyDiffer = true;
                            break;
                        }
                        count2++;
                    }
                }
            }
            else
            {
                count2 = 1;

                for (count1 = 0; count1 <= previousTotalPages; count1++)
                {
                    //currentpageindex - count2 value should not be equal to -1 since pageindex starts with 0 index
                    if (currentPageIndex - count2 != -1)
                    {
                        pageKey = DocumentKeyName(pages[currentPageIndex - count2]);

                        if (!previousPageKey.Equals(pageKey))
                        {
                            currentPageIndex -= count1;
                            isKeyDiffer = true;
                            break;
                        }
                        count2++;
                    }
                }
            }

            // If the selected page doesnt having the previous page with different key then currentpageindex will be set as count2-1 value
            if (!isKeyDiffer)
            {
                currentPageIndex -= count2 - 1;
            }

            pageIndex = currentPageIndex;
            pageNumber = pages[currentPageIndex].PageNumber;
            DisplayCurrentPage();
        }

        /// <summary>
        /// Occurs when clicks on last page.
        /// </summary>
        private void documentContainer_OnNavigateLastpage()
        {
            //DM-133 changes to display only last page
            if (authReq)
            {
                pageIndex = pages.Count - 1;
                pageNumber = pages.Count;
                DisplayCurrentPage(false, true);

                return;
            }



            // CR365724
            int currentPageIndex = pageIndex;
            //holds the key value of the current page
            string currentPageKey = DocumentKeyName(pages[currentPageIndex]);
            //holds the key value of the next page
            string nextPageKey = string.Empty;
            //holds the total number of pages in the next document set
            int nextTotalPages = 0;
            if (pages.Count != currentPageIndex + 1)
            {
                nextPageKey = DocumentKeyName(pages[currentPageIndex + 1]);
                nextTotalPages = Convert.ToInt32(encounter[nextPageKey], System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
            //holds the total number of pages in the current selected document set
            int currentTotalPages = Convert.ToInt32(encounter[currentPageKey], System.Threading.Thread.CurrentThread.CurrentUICulture);
            // holds the key value of the current iteration page
            string pageKey = string.Empty;
            bool isKeyDiffer = false;
            //used to iterate the pages until the next page having different key with the current page
            int count1;
            // if the selected document page set doesn't having the next page set with different key then this counter value 
            // will be set as the pageindex
            int count2;

            //if the next page having the same key with the current page then below if loop will execute
            if (currentPageKey.Equals(nextPageKey))
            {
                count2 = 1;

                for (count1 = 0; count1 < currentTotalPages; count1++)
                {
                    //Total page count should not be equal to the currentpageindex + count2 value
                    if (pages.Count != currentPageIndex + count2)
                    {
                        pageKey = DocumentKeyName(pages[currentPageIndex + count2]);

                        if (!currentPageKey.Equals(pageKey))
                        {
                            currentPageIndex += count1;
                            isKeyDiffer = true;
                            break;
                        }
                        count2++;
                    }
                }
            }
            else
            {
                count2 = 1;

                for (count1 = 0; count1 <= nextTotalPages; count1++)
                {

                    //Total page count should not be equal to the currentpageindex + count2 value
                    if (pages.Count != currentPageIndex + count2)
                    {
                        pageKey = DocumentKeyName(pages[currentPageIndex + count2]);

                        if (!nextPageKey.Equals(pageKey))
                        {
                            currentPageIndex += count1;
                            isKeyDiffer = true;
                            break;
                        }
                        count2++;
                    }
                }
            }

            // If the selected page doesnt having the next page with different key then currentpageindex will be set as count2-1 value
            if (!isKeyDiffer)
            {
                currentPageIndex += count2 - 1;
            }

            pageIndex = currentPageIndex;
            pageNumber = pages[currentPageIndex].PageNumber;
            DisplayCurrentPage();
        }

        /// <summary>
        /// Occurs when clicks on next page.
        /// </summary>
        private void documentContainer_OnNavigateNextpage()
        {
            pageIndex++;
            pageNumber++;
            DisplayCurrentPage(false, true);
        }

        /// <summary>
        /// Occurs when clicks on previous page.
        /// </summary>
        private void documentContainer_OnNavigatePreviouspage()
        {
            pageIndex--;
            pageNumber--;
            if (pageNumber == 0) pageNumber = 1;
            DisplayCurrentPage(true, false);
        }

        /// <summary>
        /// Occurs when clicks on go back button.
        /// </summary>
        /// <param name="docIndex"></param>
        /// <param name="pageIndex"></param>
        private void documentContainer_OnNavigateGoBack(int docIndex, int pageIndex)
        {
            if (pageIndex >= 0 && pageIndex < pages.Count)
            {
                this.pageIndex = pageIndex;
                DisplayCurrentPage();
            }
        }
        //DM-6708 added goto page functionality
        private void documentContainer_OnNavigateGoTopage()
        {
            GotoPage gopage = new GotoPage();
            gopage.ShowDialog();
            pageIndex = gopage.GotoPageNo - 1;
            pageNumber = gopage.GotoPageNo;
            DisplayCurrentPage(true, true);
                       
        }

        /// CR#365724
        /// <summary>
        /// To get the key value of the given page
        /// </summary>
        /// <param name="selectedPage"></param>
        /// <returns></returns>
        private string DocumentKeyName(PageDetails selectedPage)
        {
            string key = string.Empty;
            EncounterDetails encounterDet;
            DocumentDetails documentDet;
            VersionDetails versionDet;
            if (selectedPage.Parent.Parent.Parent is EncounterDetails)
            {
                encounterDet = (EncounterDetails)selectedPage.Parent.Parent.Parent;
                documentDet = (DocumentDetails)selectedPage.Parent.Parent;
                versionDet = (VersionDetails)selectedPage.Parent;
                key = encounterDet.Name + documentDet.DocumentType.Id + versionDet.Name;
            }
            else if (selectedPage.Parent.Parent.Parent is PatientGlobalDocument)
            {
                documentDet = (DocumentDetails)selectedPage.Parent.Parent;
                versionDet = (VersionDetails)selectedPage.Parent;
                key = global + documentDet.DocumentType.Id + versionDet.Name;
            }
            return key;
        }
		
        private void documentContainer_OnClose()
        {
            documentContainer = null;
        }

        protected virtual void Dispose(bool disposing)
        {
            if (disposing)
            {
                printDocument.Dispose();
                printDialog.Dispose();
            }
        }



        #endregion

        #region IDisposable Members

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        #endregion
    }
}
