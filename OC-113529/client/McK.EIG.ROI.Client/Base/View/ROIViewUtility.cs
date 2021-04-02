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
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Configuration;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Resources;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Base.View
{

    public enum TransientResult
    {
        Proceed,
        SaveAndProceed,
        Cancel,       
    }

    /// <summary>
    /// Used to display the dialogs in ROI
    /// </summary>
    public static class ROIViewUtility
    {
        #region Fields

        private static Cursor original;
        //CR# 341396 & CR#346831
        private const string invalidDirErr = "invalidDir";
        private const string invalidSourceErr = "invalidSource";
        private const string invalidDiscDevice = "invalidDiscDevice";
        private static int connectionFailureCount = 0;
        private static Log log = LogFactory.GetLogger(typeof(ROIViewUtility));
        #endregion 

        #region Methods

        public static void Handle(IPane pane, ROIException cause)
        {
            Handle(pane.Context, cause);
        }

        public static void Handle(ExecutionContext context, ROIException cause)
        {
            Collection<ExceptionData> errorMessages = cause.GetErrorMessage(context.CultureManager.GetCulture(CultureType.Message.ToString()));
            StringBuilder messages = new StringBuilder();
            string errorType = string.Empty;
            bool checkConnectionFailure = false;
            string errorCode = string.Empty;
            foreach (ExceptionData exceptionData in errorMessages)
            {

                //Added for CR# 341396 & CR#346831
                if (ROIErrorCodes.InvalidDirectory.Equals(exceptionData.ErrorCode))
                {
                    errorType = invalidDirErr;
                }

                if (ROIErrorCodes.InvalidSource.Equals(exceptionData.ErrorCode))
                {
                    errorType = invalidSourceErr;
                }
                //----------
                if (ROIErrorCodes.InvalidDiscDevice.Equals(exceptionData.ErrorCode))
                {
                    errorType = invalidDiscDevice;
                }

                if(ROIErrorCodes.PatientInUse.Equals(exceptionData.ErrorCode) || 
                   ROIErrorCodes.RequestorInUse.Equals(exceptionData.ErrorCode))
                {
                    ShowInUseDialog(context, exceptionData);
                    return;
                }
                messages.Append("\n");
                if (exceptionData.ErrorData == null || exceptionData.ErrorData.ToString().Length == 0)
                {
                    messages.Append(exceptionData.ErrorMessage);
                }
                else
                {
                    //log original error message
                    log.ErrorMsg(exceptionData.ErrorMessage + " :" + exceptionData.ErrorData);
                    ROIException serverErrorMsg = new ROIException(ROIErrorCodes.SystemError);
                    Collection<ExceptionData> tmpErrorMessages = serverErrorMsg.GetErrorMessage(context.CultureManager.GetCulture(CultureType.Message.ToString()));

                    if (tmpErrorMessages.Count > 0)
                    {
                        messages.Append(tmpErrorMessages[0].ErrorMessage);
                    }
                    else
                    { 
                        messages.Append("Unknow system error.  Please contact your system administrator");
                    }
                }
                if (ROIErrorCodes.ConnectFailure.Equals(exceptionData.ErrorCode))
                {
                    checkConnectionFailure = true;
                    connectionFailureCount++;
                    errorCode = exceptionData.ErrorCode;
                }
            }
            //For Logging the error message
            log.ErrorMsg(messages.ToString());

            //errorType argument added for CR# 341396 & CR#346831
            if (connectionFailureCount <= 1)
            {
                DialogUI errorDialog = new DialogUI(messages.ToString(), context, errorType, errorCode);
                errorDialog.ShowDialog();
            }
            if ((checkConnectionFailure) && (connectionFailureCount == 1))
            {
                Application.Restart();
            }
        }

        public static void ShowInUseDialog(ExecutionContext context, ExceptionData error)
        {
            
            string titleText = string.Empty;
            string okButtonText = string.Empty;
            string messageText = string.Empty;
            string okButtonToolTip = string.Empty;

            ResourceManager rm = context.CultureManager.GetCulture(CultureType.UIText.ToString());

            if (ROIErrorCodes.PatientInUse.Equals(error.ErrorCode))
            {   
                titleText = rm.GetString("PatientRecordInUseDialog.Title");
                okButtonText = rm.GetString("PatientRecordInUseDialog.OkButton");
                messageText = rm.GetString("PatientRecordInUseDialog.Message");

                rm = context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                okButtonToolTip = rm.GetString("PatientRecordInUseDialog.OkButton");
            }
            else if (ROIErrorCodes.RequestorInUse.Equals(error.ErrorCode))
            {
                titleText = rm.GetString("RequestorRecordInUseDialog.Title");
                okButtonText = rm.GetString("RequestorRecordInUseDialog.OkButton");
                messageText = rm.GetString("RequestorRecordInUseDialog.Message");

                rm = context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
                okButtonToolTip = rm.GetString("RequestorRecordInUseDialog.OkButton");
            }

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        public static TransientResult DoSaveAndProceed(ExecutionContext ec)
        {
            ResourceManager rm = ec.CultureManager.GetCulture(CultureType.Message.ToString());
            string message = rm.GetString("A100");

            ROIDialog confirmDialog = new ROIDialog(ROIDialogIcon.Alert);

            rm = ec.CultureManager.GetCulture(CultureType.UIText.ToString());            
            confirmDialog.DialogTitle = rm.GetString("dialog.title.unsaved");
            confirmDialog.DisplayMessageText = message;
            confirmDialog.OkButtonText = rm.GetString("save");
            confirmDialog.CancelButtonText = rm.GetString("cancelButton");
            confirmDialog.IgnoreButtonText = rm.GetString("dontsave");

            rm = ec.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            confirmDialog.OkButtonToolTip = rm.GetString("save.NavigateOut"); 
            confirmDialog.CancelButtonToolTip = rm.GetString("cancel.NavigateOut");
            confirmDialog.IgnoreButtonToolTip = rm.GetString("dontSave.NavigateOut");

            DialogResult result = confirmDialog.Display();

            switch (result)
            {
                case DialogResult.OK: return TransientResult.SaveAndProceed;
                case DialogResult.Ignore: return TransientResult.Proceed;
            }
            return TransientResult.Cancel;  
        }

        public static bool ConfirmDiscardChanges(ExecutionContext ec)
        {
            ResourceManager rm = ec.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString(ROIConstants.DiscardChangesDialogTitle);
            string messageText = rm.GetString(ROIConstants.DiscardChangesDialogMessage);
            string okButtonText = rm.GetString(ROIConstants.DiscardChangesDialogOkButton);
            string cancelButtonText = rm.GetString(ROIConstants.DiscardChangesDialogCancelButton);

            rm = ec.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(ROIConstants.DiscardChangesDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(ROIConstants.DiscardChangesDialogCancelButtonToolTip);
            
            return ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, ROIDialogIcon.Alert);
        }

        public static DialogResult ConfirmChanges(string titleText,
                                          string messageText,
                                          string okButtonText,
                                          string ignoreButtonText,
                                          string cancelButtonText,
                                          string okButtonToolTip,
                                          string ignoreButtonToolTip,
                                          string cancelButtonToolTip,
                                          ROIDialogIcon icon)
        {

            ROIDialog confirmDialog = new ROIDialog(icon);

            confirmDialog.DialogTitle = titleText;
            confirmDialog.DisplayMessageText = messageText;
            confirmDialog.OkButtonText = okButtonText;
            confirmDialog.IgnoreButtonText = ignoreButtonText;
            confirmDialog.CancelButtonText = cancelButtonText;
            confirmDialog.OkButtonToolTip = okButtonToolTip;
            confirmDialog.IgnoreButtonToolTip = ignoreButtonToolTip;
            confirmDialog.CancelButtonToolTip = cancelButtonToolTip;
            DialogResult result = confirmDialog.Display();
            return result;
        }

        public static bool ConfirmChanges(string titleText,
                                 string messageText,
                                 string okButtonText,
                                 string cancelButtonText,
                                 string okButtonToolTip,
                                 string cancelButtonToolTip,
                                 ROIDialogIcon icon)
        {
            return ConfirmChanges(titleText, string.Empty, messageText, okButtonText, cancelButtonText, okButtonToolTip, cancelButtonToolTip, icon, DefaultSelectButton.None);
        }
       
        public static bool ConfirmChanges(string titleText,
                                         string messageText,
                                         string okButtonText,
                                         string okButtonToolTip,
                                         ROIDialogIcon icon)
        {
            return ConfirmChanges(titleText, string.Empty, messageText, okButtonText, okButtonToolTip, icon);
        }

        public static bool ConfirmChanges(string titleText,
                                         string headerText,
                                         string messageText,
                                         string okButtonText,
                                         string okButtonToolTip,
                                         ROIDialogIcon icon)
        {

            ROIDialog confirmDialog = new ROIDialog(icon);

            confirmDialog.DialogTitle = titleText;
            confirmDialog.MessageHeaderText = headerText;
            confirmDialog.DisplayMessageText = messageText;
            confirmDialog.OkButtonText = okButtonText;
            confirmDialog.OkButtonToolTip = okButtonToolTip;
            confirmDialog.HideCancelButton = true;
            confirmDialog.IgnoreButton.Visible = false;
            DialogResult result = confirmDialog.Display();
            confirmDialog.IgnoreButton.Visible = false;
            return (result == DialogResult.OK);
        }

        public static bool ConfirmChanges(string titleText,
                                       string headerText,
                                       string messageText,
                                       string okButtonText,
                                       string cancelButtonText,
                                       bool okButtonEnabled,
                                       ROIDialogIcon icon)
        {

            ROIDialog confirmDialog = new ROIDialog(icon);

            confirmDialog.DialogTitle = titleText;
            confirmDialog.MessageHeaderText = headerText;
            confirmDialog.DisplayMessageText = messageText;
            confirmDialog.OkButtonText = okButtonText;
            confirmDialog.CancelButtonText = cancelButtonText;
            confirmDialog.OkButton.Enabled = okButtonEnabled;
            confirmDialog.IgnoreButton.Visible = false;
            DialogResult result = confirmDialog.Display();
            return (result == DialogResult.Cancel);
        }

        public static bool ConfirmChanges(string titleText,
                                 string headerText,
                                 string messageText,
                                 string okButtonText,
                                 string cancelButtonText,
                                 string okButtonToolTip,
                                 string cancelButtonToolTip,
                                 ROIDialogIcon icon,
                                 DefaultSelectButton defaultSelectButton
                                 )
        {

            ROIDialog confirmDialog = new ROIDialog(icon);

            confirmDialog.DialogTitle = titleText;
            confirmDialog.MessageHeaderText = headerText;
            confirmDialog.DisplayMessageText = messageText;
            confirmDialog.OkButtonText = okButtonText;
            confirmDialog.CancelButtonText = cancelButtonText;
            confirmDialog.OkButtonToolTip = okButtonToolTip;
            confirmDialog.CancelButtonToolTip = cancelButtonToolTip;
            confirmDialog.IgnoreButton.Visible = false;            
            if(defaultSelectButton == DefaultSelectButton.No)
            {
                confirmDialog.CancelButton.Select();
            }
            DialogResult result = confirmDialog.Display();
            return (result == DialogResult.OK);
        }


        public static bool ConfirmDelete(ExecutionContext ec, string msgKey, string className) 
        {
            string deleteButtonToolTip     = ec.CultureManager.GetResource(CultureType.ToolTip.ToString(), "deleteButton." + className);
            string dontDeleteButtonToolTip = ec.CultureManager.GetResource(CultureType.ToolTip.ToString(), "dontDelete");

            ROIDialog confirmDialog = new ROIDialog(ROIDialogIcon.Alert);

            confirmDialog.DialogTitle          = ec.CultureManager.GetResource(CultureType.UIText.ToString(), "dialog.confirm");
            confirmDialog.DisplayMessageText   = ec.CultureManager.GetResource(CultureType.Message.ToString(), msgKey);;
            confirmDialog.OkButtonText         = ec.CultureManager.GetResource(CultureType.UIText.ToString(), "deleteButton");
            confirmDialog.CancelButtonText     = ec.CultureManager.GetResource(CultureType.UIText.ToString(), "dontDeleteButton");
            confirmDialog.OkButtonToolTip      = deleteButtonToolTip;
            confirmDialog.CancelButtonToolTip  = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, dontDeleteButtonToolTip, 
                                                 new object[] { deleteButtonToolTip.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture) });
            confirmDialog.IgnoreButton.Visible = false;
            DialogResult result = confirmDialog.Display();
            return (result == DialogResult.OK);
        }

        /// <summary>
        /// Returns true if the date value is less than or equal to "01-01-1753" otherwise false
        /// </summary>
        /// <param name="Date"></param>
        /// <returns></returns>
        public static bool IsValidDate(string date)
        {
            if ((date.Trim()).Length == 4) return true;
            try
            {
                string[] inputDate = date.Split('/');
                int monthValue = Convert.ToInt16(inputDate[0], theCultureInfo);
                int dateValue = Convert.ToInt16(inputDate[1], theCultureInfo);
                int yearValue = Convert.ToInt16(inputDate[2], theCultureInfo);

                if (yearValue < 1753)
                {
                    return false;
                }
                if (monthValue == 1 && dateValue == 1 && yearValue == 1753)
                {
                    return false;
                }
                return true;
            }
            catch (FormatException)
            {
                return false;
            }
        }

        private const string CustomDateFormat = "MM/dd/yyyy";
        private static IFormatProvider theCultureInfo = new System.Globalization.CultureInfo("en-GB", true);
        /// <summary>
        /// Returns true if the given date has valid format like "MM/dd/yyyy" otherwise false
        /// </summary>
        /// <param name="date"></param>
        /// <returns></returns>
        public static bool IsValidFormat(string date)
        {
            if ((date.Trim()).Length==4) return true;
            try
            {
                date = GetFormattedDate(date);
                DateTime dt = DateTime.ParseExact(date, CustomDateFormat, theCultureInfo);
            }
            catch (FormatException)
            {
                return false;
            }
            return true;
        }

        public static string GetFormattedDate(string date)
        {
            string[] strarray = new string[8];
            strarray[0] = "MM/dd/yyyy";
            strarray[1] = "M/dd/yyyy";
            strarray[2] = "MM/d/yyyy";
            strarray[3] = "M/d/yyyy";
            strarray[4] = "MM/dd/yy";
            strarray[5] = "M/dd/yy";
            strarray[6] = "MM/d/yy";
            strarray[7] = "M/d/yy";
            date = date.Replace(" ", "");
            return DateTime.ParseExact(date, strarray,
                    CultureInfo.InvariantCulture, DateTimeStyles.None).ToString(@"MM/dd/yyyy", CultureInfo.InvariantCulture);
        }

        /// <summary>
        /// Mark the cursor to wait or default
        /// </summary>
        /// <param name="busy"></param>
        public static void MarkBusy(bool busy)
        {
            if (busy)
            {
                original = Cursor.Current;
                Cursor.Current = Cursors.WaitCursor;
            }
            else
            {
                Cursor.Current = original;
            }
        }


        public static string ConvertToCsv(Collection<string> collection)
        {
            string result = string.Empty;
            if (collection == null) return result;

            string[] array = new string[collection.Count];
            collection.CopyTo(array, 0);
            result = string.Join(",", array);
            return result; 
        }

        public static string BuildAsterisk(string inputValue, bool isPatientVip, bool isPatientLocked, bool isVip, bool isLocked)
        {   
            string outputValue = string.Empty;

            if (( isPatientVip == true &&  isVip != true ) || (isPatientLocked == true && isLocked != true))
            {
                outputValue = new string('*', inputValue.Length);
            }
            else
            {
                outputValue  = inputValue;
            }
            return outputValue;
        }

        public static string ConvertToCsv(IList<FacilityDetails> facilities)
        {

            string result = string.Empty;
            if (facilities == null) return result;
            foreach (FacilityDetails facility in facilities)
            {
                result += facility.Code + ",";
            }
            return result;
        }

        public static object DeepClone(object model)
        {
            if (model == null)
            {
                return null;
            }

            IFormatter formatter = new BinaryFormatter();
            Stream stream = new MemoryStream();
            using (stream)
            {
                formatter.Serialize(stream, model);
                stream.Seek(0, SeekOrigin.Begin);
                return formatter.Deserialize(stream);
            }
        }

        public static Form ConvertToForm(CancelEventHandler closeHandler, string title, UserControl userControl)
        {
            return ConvertToDialog(null, closeHandler, title, userControl);
        }

        public static Form ConvertToDialog(Form form, CancelEventHandler closeHandler, string title, UserControl userControl)
        {
            if (form == null)
            {
                form = CreateForm(title, closeHandler);
            }
            else
            {
                form.Controls.Clear();
            }

            userControl.Dock = DockStyle.None;
            form.Controls.Add(userControl);
            form.Size = userControl.PreferredSize;
            form.Text = title;

            return form;
        }

        private static Form CreateForm( string title, CancelEventHandler closeHandler)
        {
            Form form = new Form();

            form.MinimizeBox = false;
            form.MaximizeBox = false;
            form.BackColor = Color.White;
            form.FormBorderStyle = FormBorderStyle.FixedSingle;
            form.SizeGripStyle = SizeGripStyle.Hide;
            form.StartPosition = FormStartPosition.CenterParent;
            //userControl.Dock = DockStyle.None;
            //dialogForm.Controls.Add(userControl);
            //dialogForm.Size = userControl.PreferredSize;
            form.AutoSize = true;
            form.Text = title;

            if (closeHandler != null)
            {
                form.Closing += closeHandler;
            }
            return form;
        }

        /// <summary>
        /// Apply the mask
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static string ApplyMask(string value)
        {
            if (string.IsNullOrEmpty(value) || !UserData.Instance.IsSSNMasked) return value;

            MaskedTextProvider txtProvider = new MaskedTextProvider("999-99-9999", System.Threading.Thread.CurrentThread.CurrentUICulture);
            txtProvider.PromptChar = 'x';
            txtProvider.IncludePrompt = true;
            txtProvider.Set(value.PadRight(value.Length * 2, ROIConstants.MaskChar).Substring(value.Length));
            return txtProvider.ToString();
        }

        /// <summary>
        /// Apply mask when the control is no longer the active control of the view 
        /// </summary>
        /// <param name="control"></param>
        public static void ApplyMask(MaskedTextBox control)
        {
            control.PasswordChar = ROIConstants.MaskChar;
        }

        /// <summary>
        /// Remove the mask when the control becomes active control of the view
        /// </summary>
        /// <param name="control"></param>
        public static void RemoveMask(MaskedTextBox control)
        {
            control.PasswordChar = char.MinValue;
        }

        public static void SetCurrentDate(NullableDateTimePicker datePicker)
        {
            if (datePicker.DateValue == null)
            {
                datePicker.DateValue = DateTime.Now.Date;
            }
        }

        /// <summary>
        /// Method that returns the value of formatted amount
        /// </summary>
        /// <param name="amount"></param>
        /// <returns></returns>
        public static string FormattedAmount(double amount)
        {
            return amount.ToString("0.00", System.Threading.Thread.CurrentThread.CurrentUICulture);
        }

        public static string ReplaceString(string value)
        {
            if(string.IsNullOrEmpty(value)) return string.Empty;

            value = value.Replace("&", "&amp;");
	        value = value.Replace("\"", "&quot;");
	        value = value.Replace("<", "&#60;");
	        value = value.Replace(">", "&#62;");
        	
	        return value;
        }

        public static string OriginalString(string value)
        {
            if(string.IsNullOrEmpty(value)) return string.Empty;

            value = value.Replace("&quot;", "\"");	
	        value = value.Replace("&amp;", "&");	       	       
	        value = value.Replace("&#60;", "<");
            value = value.Replace("&#62;", ">");

	        return value;
        }

        //CR#353535
        public static string ReplaceAmpersand(string value)
        {
            if (string.IsNullOrEmpty(value)) return string.Empty;
            value = value.Replace("&", "&amp;");
            return value;
        }

        public static string OriginalAmpersand(string value)
        {
            if (string.IsNullOrEmpty(value)) return string.Empty;
            value = value.Replace("&amp;", "&");
            return value;
        }

        public static string ReplaceAmpersandForLabel(string value)
        {
            if (string.IsNullOrEmpty(value)) return string.Empty;
            value = value.Replace("&", "&&");
            return value;
        }

        //CR#353535

        public static bool WaitForFileInUse(string fileName, TimeSpan timeToWait)
        {
            bool ready = false;
            while (!ready)
                try
                {
                    File.Open(fileName, FileMode.Open, FileAccess.Read, FileShare.None).Dispose();
                    ready = true;
                }
                catch (IOException)
                {
                    if (timeToWait.TotalMilliseconds <= 0)
                        break;
                    int wait = (int)Math.Min(timeToWait.TotalMilliseconds, 1000.0);
                    timeToWait -= TimeSpan.FromMilliseconds(wait);
                    System.Threading.Thread.Sleep(wait); // sleep one second
                }

            return ready;
        }

        /// <summary>
        /// Retrieve all letter templates.
        /// </summary>
        /// <param name="letterTemplateType"></param>
        /// <returns></returns>
        public static IList<LetterTemplateDetails> RetrieveLetterTemplates(Collection<LetterTemplateDetails> letterTemplateList, string letterTemplateType)
        {
            List<LetterTemplateDetails> list = new List<LetterTemplateDetails>(letterTemplateList);
            List<LetterTemplateDetails> letterTemplates = list.FindAll(delegate(LetterTemplateDetails item) { return item.LetterType.ToString() == letterTemplateType; });

            return letterTemplates;
        }

        public static long RetrieveDefaultId(IList<LetterTemplateDetails> letterTemplates)
        {
            long defaultId = 0;

            List<LetterTemplateDetails> list = new List<LetterTemplateDetails>(letterTemplates);
            List<LetterTemplateDetails> defaultTemplates = list.FindAll(delegate(LetterTemplateDetails item) { return item.IsDefault == true; });

            if (defaultTemplates.Count > 0)
            {
                defaultId = defaultTemplates[0].DocumentId;
            }

            return defaultId;
        }

        #region Security Rights

        public static bool IsAllowed(string securityRightId, string facility)
        {
            return UserData.Instance.HasAccess(securityRightId, facility);
        }

        public static bool IsAllowed(string securityRightId)
        {
            return UserData.Instance.HasAccess(securityRightId, ROISecurityRights.DefaultFacility);
        }

        public static bool IsAllowed(Collection<string> securityRights)
        {
            return IsAllowed(securityRights, true, ROISecurityRights.DefaultFacility);
        }

        public static bool IsAllowed(Collection<string> securityRights, string facility)
        {
            return IsAllowed(securityRights, true, facility);
        }

        public static bool IsAllowed(Collection<string> securityRights, bool checkAll)
        {
            return IsAllowed(securityRights, checkAll, ROISecurityRights.DefaultFacility);
        }

        public static bool IsAllowed(Collection<string> securityRights, bool checkAll, string facility)
        {
            if (checkAll)
            {
                foreach (string securityRightId in securityRights)
                {
                    if (!IsAllowed(securityRightId, facility)) return false;
                }
                return true;
            }
            else
            {
                foreach (string securityRightId in securityRights)
                {
                    if (IsAllowed(securityRightId, facility)) return true;
                }
                return false;
            }
        }

        // CR# 377,374 - Fix 
        /// <summary>
        /// round off the given decimal value
        /// </summary>
        /// <returns>rounded value</returns>
        public static double RoundOffValue(double value, int fractionDigits)
        {
            if (value == null)
            {
                return Math.Round(0.00, fractionDigits);
            }

            String strValue = value.ToString();
            int index = strValue.IndexOf('.');

            if (index == -1)
            {
                strValue = strValue + ".";
                for (int position = 0; position < fractionDigits; position++)
                {
                    strValue = strValue + "0";
                }
            }
            else if ((strValue.Length - (index + 1)) <= fractionDigits)
            {

                for (int position = (strValue.Length - (index + 1)); position < fractionDigits; position++)
                {
                    strValue = strValue + "0";
                }
            }
            else
            {
                String modifiedStr = strValue.Substring(0, (index + fractionDigits));

                int fractionDigitPositionValue = Convert.ToInt32(strValue.Substring((index + fractionDigits), 1));
                int fractionDigitNextPositionValue = Convert.ToInt32(strValue.Substring((index + fractionDigits + 1), 1));
                if (fractionDigitPositionValue == 9 && fractionDigitNextPositionValue >= 5)
                {
                    string newString = "0";
                    int charIndex = modifiedStr.Length;
                    while (charIndex > 0)
                    {

                        string stringchar = modifiedStr.Substring((charIndex - 1), 1);
                        if (stringchar == ".")                        
                        {
                            newString = stringchar + newString;
                            charIndex--;
                            continue;
                        }

                        int a = Convert.ToInt32(stringchar);
                        if (a < 9)
                        {
                            newString = modifiedStr.Substring(0, (charIndex - 1)) + (++a) + newString;
                            break;
                        }

                        newString = '0' + newString;
                        charIndex--;
                        if (charIndex <= 0)
                        {

                            newString = "1" + newString;
                            break;
                        }

                    }
                    modifiedStr = newString;
                }
                else if (fractionDigitNextPositionValue >= 5)
                {
                    ++fractionDigitPositionValue;
                    modifiedStr = modifiedStr + fractionDigitPositionValue;
                }
                else
                {
                    modifiedStr = modifiedStr + fractionDigitPositionValue;
                }
                
                strValue = modifiedStr;
            }

            return Math.Round((Convert.ToDouble(strValue)), fractionDigits);
        }


        #endregion      

        #endregion
    }
}

