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
using System.Globalization;
using System.IO;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;

using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Utility.Logging;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ExportDialogUI
    /// </summary>
    public partial class ExportDialogUI : ROIBaseUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ExportDialogUI));     

        private EventHandler exportHandler;
        private const string xlsFileExt = "xls";
        private int dirIndex;

        #endregion

        #region Constructors

        public ExportDialogUI()
        {
            InitializeComponent();
            dirListBox.Path = driveListBox.SelectedItem.ToString().Substring(0,1) + @":\";
            dirIndex        = dirListBox.DirListIndex;
            LoadExportTypes();
        }

        public ExportDialogUI(IPane pane, EventHandler exportHandler)
            : this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
            this.exportHandler = exportHandler;
            errorProvider.Clear();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Loads reports export types.
        /// </summary>
        private void LoadExportTypes()
        {
            IList exportTypes             = EnumUtilities.ToList(typeof(ExportFormat));
            exportTypeCombo.DataSource    = exportTypes;
            exportTypeCombo.DisplayMember = "value";
            exportTypeCombo.ValueMember   = "key";
        }

        /// <summary>
        /// DriveListBox selected index changed event.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void driveListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
         Collection<ExceptionData> errors = new Collection<ExceptionData>();
         log.EnterFunction();

         try
         {
             dirListBox.Path = driveListBox.SelectedItem.ToString();
             dirListBox.SetSelected(0, true);
             dirListBox.Path = dirListBox.Text;
             saveButton.Enabled = true;
             dirIndex = dirListBox.SelectedIndex;
         }

         catch(IOException cause)
         {
             log.FunctionFailure(cause);
             errors.Add(new ExceptionData(ROIErrorCodes.DeviceNotValid));
             ROIViewUtility.Handle(Context, new ROIException(errors));
             driveListBox.SelectedIndex = 0;
             return;
         }
         log.ExitFunction();
       }

        /// <summary>
        /// Localizes the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, fileNameLabel);
            SetLabel(rm, formatLabel);
            SetLabel(rm, saveLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
        }

        /// <summary>
        /// Export the report to the selected path.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            ResourceManager rm;
            rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            errorProvider.Clear();
            if (fileNameTextBox.Text.Trim().Length == 0
               || fileNameTextBox.Text.IndexOfAny(Path.GetInvalidFileNameChars()) != -1)
            {
                errorProvider.SetError(fileNameTextBox, rm.GetString("fileNameEmpty"));
                return;
            }

            if (!Validator.Validate(ExportPath, ROIConstants.FilepathValidation))
            {
                rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(dirListBox, string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("ROI.13.3.08"), ExportPath));
                return;
            }

            if(File.Exists(ExportPath))
            {
                rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                errorProvider.SetError(fileNameTextBox, rm.GetString("fileExists"));
                return;                
            }

            if (File.Exists(ExportPath))
            {
                if (isFileOpenOrReadOnly(ExportPath))
                {
                    rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                    errorProvider.SetError(fileNameTextBox, string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("fileAlreadyOpen"), ExportPath));
                    return;
                }
            }
            //CR# 368492
            if (!HasAccessToWrite(dirListBox.Path))
            {
                rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                errorProvider.SetError(dirListBox, string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("fileWritePermission"), ExportPath));
                return;
            }
       
            exportHandler(null, null);

            ((Form)(this.Parent)).Close();
        }

        /// <summary>
        /// Closes the window.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            ((Form)(this.Parent)).Close();
        }

        private void dirListBox_SelectedValueChanged(object sender, EventArgs e)
        {
            if (dirListBox.SelectedIndex <= dirIndex)
            {
                string path = dirListBox.Path;
                int index = 0;
                for (int i = 0; i < path.Length; i++)
                {
                    if (path[i] == '\\')
                    {
                        index++;
                        if (index == dirListBox.SelectedIndex+1)
                        {
                            path = path.Substring(0, i);
                        }
                    }
                }
                dirListBox.Path = path + "\\";
            }

            else
            {
                if (Directory.Exists(Path.Combine(dirListBox.Path, dirListBox.Text)))
                {
                    dirListBox.Path = Path.Combine(dirListBox.Path, dirListBox.Text);
                }
            }

            dirIndex = dirListBox.SelectedIndex;
        }

        private void dirListBox_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            saveButton.Enabled = true;
            dirIndex = dirListBox.SelectedIndex;
        }

        /// <summary>
        /// method to check not only if a file is already open, but if the
        /// but also if read and write permissions exist
        /// </summary>
        /// <param name="file">the file we wish to check</param>
        /// <returns></returns>
        private static bool isFileOpenOrReadOnly(string file)
        {
            try
            {
                //first make sure it's not a read only file
                if ((File.GetAttributes(file) & FileAttributes.ReadOnly) != FileAttributes.ReadOnly)
                {
                    //first we open the file with a FileStream
                    using (FileStream stream = new FileStream(file, FileMode.OpenOrCreate, FileAccess.Read, FileShare.None))
                    {
                        try
                        {
                            stream.ReadByte();
                            return false;
                        }
                        catch (IOException)
                        {
                            return true;
                        }
                    }
                }
                else
                    return true;
            }
            catch (IOException)
            {
                return true;
            }
        } 

        //CR# 368492
        /// <summary>
        /// method to ensure the selected directory have write permissions to create the export file
        /// </summary>
        /// <param name="file">file path</param>
        /// <returns></returns>
        private bool HasAccessToWrite(string path)
        {
            try
            {
                if (!Validator.Validate(path, ROIConstants.FilepathValidation))
                {
                    return false;
                }
                using (FileStream fs = File.Create(Path.Combine(path, ExportFileName), 1, FileOptions.DeleteOnClose))
                {

                }

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Reports selected output path of report to be exported.
        /// </summary>
        public string ExportPath
        {
            get { return Path.Combine(dirListBox.Path, ExportFileName); }
        }

        public string ExportFileName
        {
            get 
            {
                if (exportTypeCombo.SelectedValue.ToString() == ExportFormat.Excel.ToString())
                {
                    return  fileNameTextBox.Text.Trim() + "." + xlsFileExt;
                }
                return fileNameTextBox.Text.Trim() + "." + exportTypeCombo.SelectedValue.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture); 
            }
        }

        /// <summary>
        /// Selected export type.
        /// </summary>
        public ExportFormat ExportType
        {
            get { return (ExportFormat)exportTypeCombo.SelectedValue; }
        }

        #endregion
    }
}
