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
using System.Globalization;
using System.IO;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Viewer.View;
using McK.EIG.Common.Viewer.Model;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using O2S.Components.PDFView4NET;
using O2S.Components.PDFView4NET.Actions;
using O2S.Components.PDFView4NET.Graphics;
using O2S.Components.PDFView4NET.Annotations;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    public partial class ROIViewer : ROIBaseUI
    {
        #region Fields
        
        private bool releaseDialog;
        private string requestorFax;
        private string requestorEmail;
        private DestinationType destinationType;
        private OutputPropertyDetails outputPropertyDetails;
        private OutputPropertyDetails outputPropertyDetailsForDisc;
        private string letterType;
        private bool isCancelClicked;
        private string uiName;

        // to use for viewing Attachments
        // by default always display the Continue button and Cancel button
        // however, do not display for viewing Attachments
        private bool displayContinueButton = true;
        private bool displayCancelButton = true;
        private bool overDueDialog;
        private string billingPaymentInfoUI = "BillingPaymentInfoUI";
        private string releaseDialogUI = "ReleaseDialog";

        #endregion
        
        #region Constructor

        private ROIViewer()
        {
            InitializeComponent();           
        }

        public ROIViewer(IPane pane, string letterType,string formName): this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            this.letterType = letterType;
            UiName = formName; 
            Localize();
            ApplySecurityRights();

            this.continueButton.Visible = displayContinueButton;
            this.cancelButton.Visible = displayCancelButton;
        }

        public DialogResult ContinueClick()
        {
			ROIViewUtility.MarkBusy(true);
            DialogResult dialogResult = DialogResult.Cancel;

            ((Form)(this.Parent)).DialogResult = DialogResult.OK;

            if (ReleaseDialog)
            {
                if (destinationType == DestinationType.Print)
                {
                    dialogResult = ShowPrintDialog(this);
                }
                else if (destinationType == DestinationType.Fax)
                {
                    dialogResult = ShowFaxDialog(this);
                }
                else if (destinationType == DestinationType.File)
                {
                    dialogResult = ShowFileDialog(this);
                }
                else if (destinationType == DestinationType.Email)
                {
                    dialogResult = ShowEmailDialog(this);
                }
                else if (destinationType == DestinationType.Disc)
                {
                    dialogResult = ShowDiscDialog(this);
                }
            }

            else if (OverDueDialog)
            {
                if (destinationType == DestinationType.Print)
                {
                    dialogResult = ShowPrintDialog(this);
                }
                else if (destinationType == DestinationType.File)
                {
                    dialogResult = ShowFileDialog(this);
                }
            }
            if (this.Parent != null)
            {
                ((Form)this.Parent).Visible = false;

                if (dialogResult == DialogResult.OK)
                {
                    ((Form)(this.Parent)).DialogResult = DialogResult.OK;
                }
                if (dialogResult == DialogResult.None)
                {
                    dialogResult = DialogResult.None;
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                }
            }
            return dialogResult;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Localize UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            printButton.Text = rm.GetString("printRadioButton");
            faxButton.Text = rm.GetString("faxRadioButton");
            emailButton.Text = rm.GetString("emailRadioButton");
            saveAsFileButton.Text = rm.GetString("saveAsRadioButton");
            cancelButton.Text = rm.GetString("cancelButton");
            continueButton.Text = rm.GetString("continueButton.ReleaseDialog");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            if(!string.IsNullOrEmpty(letterType))
            {
                //Same Tooltip message is showed for both Cover Letter and Other
                if ((letterType == LetterType.Other.ToString() || letterType == LetterType.RequestorStatement.ToString()))
                {
                    letterType = LetterType.CoverLetter.ToString();
                }

                if (letterType != LetterType.OverdueInvoice.ToString())
                {
                printButton.ToolTipText      = rm.GetString(letterType + "." + printButton.Name);
                faxButton.ToolTipText        = rm.GetString(letterType + "." + faxButton.Name);
                saveAsFileButton.ToolTipText = rm.GetString(letterType + "." + saveAsFileButton.Name);
                emailButton.ToolTipText = rm.GetString(letterType + "." + emailButton.Name);
                }

                cancelButton.ToolTipText     = string.Format(rm.GetString(letterType + "." + cancelButton.Name),UiName);
            }
        }

        /// <summary>
        /// Occurs when user clicks on Print Button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void printButton_Click(object sender, EventArgs e)
        {   
            DialogResult result = ShowPrintDialog(this);
            destinationType = DestinationType.Print;
            if (result == DialogResult.None)
            {
                ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
            }
        }

        internal DialogResult ShowPrintDialog(ROIBaseUI parent)
        {
            OutputPrintDialog outputPrintDialog = new OutputPrintDialog(Pane);
            outputPrintDialog.ReleaseDialog = releaseDialog;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString("title.OutputPrintDialog"),
                                                       outputPrintDialog);

            dialog.BackColor = Color.FromArgb(236, 233, 216);
            dialog.KeyPreview = true;

            DialogResult dialogResult = DialogResult.OK;

            try
            {
                Application.DoEvents();
                outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.Print.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
                if (outputPropertyDetails.OutputDestinationDetails.Count > 0)
                {
                    outputPrintDialog.PrePopulate(outputPropertyDetails);


                    if ((Form)Parent != null)
                    {
                        ((Form)parent.Parent).Visible = false;
                        dialogResult = dialog.ShowDialog(((Form)(parent.Parent)));
                    }
                    else
                    {
                        dialogResult = dialog.ShowDialog(parent.ParentForm);
                    }

                    outputPropertyDetails = outputPrintDialog.OutputPropertyDetails;

                    if ((Form)Parent != null)
                    {
                        if (dialogResult == DialogResult.OK)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.OK;
                            ((Form)parent.Parent).Close();
                        }
                        else if (dialogResult == DialogResult.Cancel)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                        }
                    }
                }
                else
                {
                    rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("printdialog.title");
                    string okButtonText = rm.GetString("okButton.DialogUI");
                    string messageText = rm.GetString("printdialog.MessageText");
                    string okButtonToolTip = "";
                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                    //((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                    parent.ParentForm.DialogResult = DialogResult.Cancel;
                    dialogResult = DialogResult.Cancel;
                }
            }
            catch(ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                dialogResult = DialogResult.None;
            }

            return dialogResult;
        }

        /// <summary>
        /// Occurs when user clicks on Fax Button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void faxButton_Click(object sender, EventArgs e)
        {   
            DialogResult result = ShowFaxDialog(this);
            destinationType = DestinationType.Fax;
            if (result == DialogResult.None)
            {
                ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
            }
        }

        internal DialogResult ShowFaxDialog(ROIBaseUI parent)
        {
            OutputFaxDialog outputFaxDialog = new OutputFaxDialog(Pane);
            outputFaxDialog.ReleaseDialog = releaseDialog;
            outputFaxDialog.RequestorFax = requestorFax;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString("title.OutputFaxDialog"),
                                                       outputFaxDialog);

            dialog.BackColor = Color.FromArgb(236, 233, 216);
            dialog.KeyPreview = true;
            DialogResult dialogResult = DialogResult.OK;

            try
            {
                Application.DoEvents();
                outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.Fax.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));

                if (outputPropertyDetails.OutputDestinationDetails.Count > 0)
                {
                    outputFaxDialog.PrePopulate(outputPropertyDetails);


                    if ((Form)Parent != null)
                    {
                        ((Form)parent.Parent).Visible = false;
                        dialogResult = dialog.ShowDialog(((Form)(parent.Parent)));
                    }
                    else
                    {
                        dialogResult = dialog.ShowDialog(parent.ParentForm);
                    }

                    outputPropertyDetails = outputFaxDialog.OutputPropertyDetails;

                    if ((Form)Parent != null)
                    {
                        if (dialogResult == DialogResult.OK)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.OK;
                            ((Form)parent.Parent).Close();
                        }
                        else if (dialogResult == DialogResult.Cancel)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                        }
                    }
                }
                else
                {
                    rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("faxdialog.title");
                    string okButtonText = rm.GetString("okButton.DialogUI");
                    string messageText = rm.GetString("faxdialog.MessageText");
                    string okButtonToolTip = "";
                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                    //((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                    parent.ParentForm.DialogResult = DialogResult.Cancel;
                    dialogResult = DialogResult.Cancel;
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                dialogResult = DialogResult.None;
            }
            return dialogResult;
        }

        /// <summary>
        /// Occurs when user clicks on SaveFile Button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveAsFileButton_Click(object sender, EventArgs e)
        {   
            DialogResult result = ShowFileDialog(this);
            destinationType = DestinationType.File;
            if (result == DialogResult.None)
            {
                ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
            }
        }

        internal DialogResult ShowFileDialog(ROIBaseUI parent, int selectedIndex)
        {

            OutputFileDialog outputFileDialog = new OutputFileDialog(Pane);
            outputFileDialog.ReleaseDialog = releaseDialog;
            outputFileDialog.OverdueDialog = OverDueDialog;

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString("title.OutputFileDialog"),
                                                       outputFileDialog);

            dialog.BackColor = Color.FromArgb(236, 233, 216);
            dialog.KeyPreview = true;
            DialogResult dialogResult = DialogResult.OK;
            try
            {
                Application.DoEvents();
                outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.File.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));

                if (outputPropertyDetails.OutputDestinationDetails.Count > 0)
                {
                    outputFileDialog.PrePopulate(outputPropertyDetails, selectedIndex);

                    if ((Form)Parent != null)
                    {
                        ((Form)parent.Parent).Visible = false;
                        dialogResult = dialog.ShowDialog(((Form)(parent.Parent)));
                    }
                    else
                    {
                        dialogResult = dialog.ShowDialog(parent.ParentForm);
                    }

                    outputPropertyDetails = outputFileDialog.OutputPropertyDetails;

                    if ((Form)Parent != null)
                    {
                        if (dialogResult == DialogResult.OK)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.OK;
                            ((Form)parent.Parent).Close();
                        }
                        else if (dialogResult == DialogResult.Cancel)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                        }
                    }
                }
                else
                {
                    rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("filedialog.title");
                    string okButtonText = rm.GetString("okButton.DialogUI");
                    string messageText = rm.GetString("filedialog.MessageText");
                    string okButtonToolTip = "";
                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                    //((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                    parent.ParentForm.DialogResult = DialogResult.Cancel;
                    dialogResult = DialogResult.Cancel;
                }
            }
            catch (ROIException cause)
            {                         
                ROIViewUtility.Handle(Context, cause);
                dialogResult = DialogResult.None;
            }

            return dialogResult;
        }

        internal DialogResult ShowFileDialog(ROIBaseUI parent)
        {
            return ShowFileDialog(parent, 0);
        }

        /// <summary>
        /// Occurs when user clicks on Email Button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void emailButton_Click(object sender, EventArgs e)
        {
            DialogResult result = ShowEmailDialog(this);
            destinationType = DestinationType.Email;
            if (result == DialogResult.None)
            {
                ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
            }

        }

        internal DialogResult ShowEmailDialog(ROIBaseUI parent, int selectedIndex)
        {

            OutputEmailDialog outputEmailDialog = new OutputEmailDialog(Pane);
            outputEmailDialog.ReleaseDialog = releaseDialog;
            outputEmailDialog.RequestorEmail = RequestorEmail;


            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString("title.OutputEmailDialog"),
                                                       outputEmailDialog);

            dialog.BackColor = Color.FromArgb(236, 233, 216);
            dialog.KeyPreview = true;
            DialogResult dialogResult = DialogResult.OK;
            try
            {
                Application.DoEvents();
                outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.Email.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));


                if (outputPropertyDetails.OutputDestinationDetails.Count > 0)
                {
                    outputEmailDialog.PrePopulate(outputPropertyDetails, selectedIndex);

                    if ((Form)Parent != null)
                    {
                        ((Form)parent.Parent).Visible = false;
                        dialogResult = dialog.ShowDialog(((Form)(parent.Parent)));
                    }
                    else
                    {
                        dialogResult = dialog.ShowDialog(parent.ParentForm);
                    }

                    outputPropertyDetails = outputEmailDialog.OutputPropertyDetails;

                    if ((Form)Parent != null)
                    {
                        if (dialogResult == DialogResult.OK)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.OK;
                            ((Form)parent.Parent).Close();
                        }
                        else if (dialogResult == DialogResult.Cancel)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                        }
                    }
                }
                else
                {
                    rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("emaildialog.title");
                    string okButtonText = rm.GetString("okButton.DialogUI");
                    string messageText = rm.GetString("emaildialog.MessageText");
                    string okButtonToolTip = "";
                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                    //((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                    parent.ParentForm.DialogResult = DialogResult.Cancel;
                    dialogResult = DialogResult.Cancel;
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                dialogResult = DialogResult.None;
            }
            return dialogResult;
        }

        internal DialogResult ShowEmailDialog(ROIBaseUI parent)
        {
            return ShowEmailDialog(parent, 0);
        }

        /// <summary>
        /// Method to Display Disc Dialog
        /// </summary>
        /// <param name="parent"></param>
        /// <param name="selectedIndex"></param>
        /// <returns></returns>
        internal DialogResult ShowDiscDialog(ROIBaseUI parent, int selectedIndex)
        {
            OutputDiscDialog outputDiscDialog = new OutputDiscDialog(Pane);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form dialog = ROIViewUtility.ConvertToForm(null,
                                                       rm.GetString("title.OutputDiscDialog"),
                                                       outputDiscDialog);

            dialog.BackColor = Color.FromArgb(236, 233, 216);
            dialog.KeyPreview = true;
            DialogResult dialogResult = DialogResult.OK;
            try
            {
                Application.DoEvents();
                outputPropertyDetailsForDisc = OutputController.Instance.RetrieveDestinations(DestinationType.Disc.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
                if (outputPropertyDetailsForDisc.OutputDestinationDetails.Count > 0)
                {
                    Application.DoEvents();
                    outputPropertyDetails = OutputController.Instance.RetrieveDestinations(DestinationType.Print.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture));
                    outputDiscDialog.PrePopulate(outputPropertyDetailsForDisc, outputPropertyDetails);

                    if ((Form)Parent != null)
                    {
                        ((Form)parent.Parent).Visible = false;
                        dialogResult = dialog.ShowDialog(((Form)(parent.Parent)));
                    }
                    else
                    {
                        dialogResult = dialog.ShowDialog(parent.ParentForm);
                    }
                    outputPropertyDetails = outputDiscDialog.OutputPropertyDetails;
                    outputPropertyDetailsForDisc = outputDiscDialog.OutputPropertyDetailsForDisc;
                    if ((Form)Parent != null)
                    {
                        if (dialogResult == DialogResult.OK)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.OK;
                            ((Form)parent.Parent).Close();
                        }
                        else if (dialogResult == DialogResult.Cancel)
                        {
                            ((Form)parent.Parent).DialogResult = DialogResult.Cancel;
                        }
                    }
                }
                else
                {
                    rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                    string titleText = rm.GetString("discdialog.title");
                    string okButtonText = rm.GetString("okButton.DialogUI");
                    string messageText = rm.GetString("discdialog.MessageText");
                    string okButtonToolTip = "";
                    ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                    parent.ParentForm.DialogResult = DialogResult.Cancel;
                    dialogResult = DialogResult.Cancel;
                }

                
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
                dialogResult = DialogResult.None;
            }
            return dialogResult;
        }

        /// <summary>
        /// Method to display disc dialog
        /// </summary>
        /// <param name="parent"></param>
        /// <returns></returns>
        internal DialogResult ShowDiscDialog(ROIBaseUI parent)
        {
            return ShowDiscDialog(parent, 0);
        }

        /// <summary>
        /// Occurs when user clicks on Continue button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void continueButton_Click(object sender, EventArgs e)
        {
            ContinueClick();
        }

        /// <summary>
        /// Occurs when user clicks on Cancel button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            isCancelClicked = true;
            ((Form)this.Parent).Close();            
            ((Form)(this.Parent)).DialogResult = DialogResult.Cancel;
        }

        private bool CheckSecurityRights()
        {
            if (destinationType == DestinationType.Print || destinationType == DestinationType.Fax)
            {
                return IsAllowed(ROISecurityRights.ROIPrintFax);
            }
            if (destinationType == DestinationType.Email)
            {
                return IsAllowed(ROISecurityRights.ROIEmail);
            }
            if (destinationType == DestinationType.Disc)
            {
                return IsAllowed(ROISecurityRights.ROIReleaseToDisc);
            }
            return IsAllowed(ROISecurityRights.ROIExportToPDF);
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            if (!IsAllowed(ROISecurityRights.ROIPrintFax))
            {
                printButton.Visible = false;
                faxButton.Visible = false;
                toolStripSeparator1.Visible = false;
                toolStripSeparator2.Visible = false;
                toolStripSeparator5.Visible = false;
            }
            if (!IsAllowed(ROISecurityRights.ROIExportToPDF))
            {
                saveAsFileButton.Visible = false;
                toolStripSeparator3.Visible = false;
                toolStripSeparator5.Visible = false;
            }
            if (!IsAllowed(ROISecurityRights.ROIEmail))
            {
                emailButton.Visible = false;
                toolStripSeparator4.Visible = false;
                toolStripSeparator5.Visible = false;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the viewer.
        /// </summary>
        public WebBrowser DocumentViewer
        {
            get { return documentViewer; }            
        }
        //CR#391890
        /// <summary>
        /// Gets the PDF viewer.
        /// </summary>
        //public AxAcroPDFLib.AxAcroPDF  PDFDocumentViewer
        //{
        //    get { return axAcroPDF  ; }
        //}

        //Using PDFView4NET instead of acrobat reader to view pdf files

        public O2S.Components.PDFView4NET.PDFPageView  PDFPageViewer
        {
            get { return pdfPageView; }
 
        }

        public O2S.Components.PDFView4NET.PDFDocument PDFDocumentViewer
        {
            get { return pdfDocument; }

        }
        /// <summary>
        /// Gets the viewer toolbar.
        /// </summary>
        public ToolStrip ViewerToolStrip
        {
            get { return viewerToolStrip; }            
        }

        /// <summary>
        /// Gets or sets the releaseDialog.
        /// </summary>
        public bool ReleaseDialog
        {
            get { return releaseDialog; }
            set
            {
                if (value)
                {
                    continueButton.Visible = CheckSecurityRights();
                    printButton.Visible = false;
                    faxButton.Visible = false;
                    saveAsFileButton.Visible = false;
                    emailButton.Visible = false;
                    toolStripSeparator1.Visible = false;
                    toolStripSeparator2.Visible = false;
                    toolStripSeparator3.Visible = false;
                    toolStripSeparator4.Visible = false;
                    toolStripSeparator5.Visible = false;                 
                }
                else
                {
                    continueButton.Visible = false;
                }
                releaseDialog = value;
            }
        }

        /// <summary>
        /// Gets or sets the overduedialog.
        /// </summary>
        public bool OverDueDialog
        {
            get { return overDueDialog; }
            set
            {
                printButton.Visible = false;
                saveAsFileButton.Visible = false;
                faxButton.Visible = false;
                toolStripSeparator1.Visible = true;
                toolStripSeparator2.Visible = false;
                toolStripSeparator3.Visible = false;
                toolStripSeparator4.Visible = false;
                emailButton.Visible = false;
                overDueDialog = value;
                overDueDialog = true;
                //CR#359303
                continueButton.Visible = CheckSecurityRights();
            }
        }

        /// <summary>
        /// Gets or sets the requestor fax.
        /// </summary>
        public string RequestorFax
        {
            get { return requestorFax; }
            set { requestorFax = value; }
        }

        /// <summary>
        /// Gets or sets the destination type.
        /// </summary>
        public DestinationType DestinationType
        {
            get { return destinationType; }
            set 
            {
                destinationType = value;

                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

                if (destinationType == DestinationType.Print)
                {
                    continueButton.ToolTipText = rm.GetString(LetterType.Invoice.ToString() + "." + printButton.Name);
                }
                else if (destinationType == DestinationType.Fax)
                {
                    continueButton.ToolTipText = rm.GetString(LetterType.Invoice.ToString() + "." + faxButton.Name);
                }
                else if (destinationType == DestinationType.File)
                {
                    continueButton.ToolTipText = rm.GetString(LetterType.Invoice.ToString() + "." + saveAsFileButton.Name);
                }
                else if (destinationType == DestinationType.Email)
                {
                    continueButton.ToolTipText = rm.GetString(LetterType.Invoice.ToString() + "." + emailButton.Name);
                }
                if ((billingPaymentInfoUI.Equals(UiName)) || (releaseDialogUI.Equals(UiName)))
                {
                    cancelButton.ToolTipText = rm.GetString("release." + cancelButton.Name);
                }
            }
        }

        public string UiName
        {
            get { return uiName; }
            set { uiName = value; }
        }

        public OutputPropertyDetails OutputPropertyDetails
        {
            get { return outputPropertyDetails; }            
        }

        public OutputPropertyDetails OutputPropertyDetailsForDisc
        {
            get { return outputPropertyDetailsForDisc; }
        }

        public bool IsCancelClicked
        {
            get { return isCancelClicked; }
        }

        public bool DisplayContinueButton
        {
            get { return displayContinueButton; }
            set 
            { 
                displayContinueButton = value;
                continueButton.Visible = displayContinueButton;
            }
        }

        public bool DisplayCancelButton
        {
            get { return displayCancelButton; }
            set
            {
                displayCancelButton = value;
                this.cancelButton.Visible = displayCancelButton;
            }
        }

        /// <summary>
        /// Gets or sets the requestor email.
        /// </summary>
        public string RequestorEmail
        {
            get { return requestorEmail; }
            set
            {
                if (value != null)
                {
                    value.Trim();
                }
                requestorEmail = value;
            }
        }

        #endregion     
    }
}
