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
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;

using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Admin.View.Other.LetterTemplate
{
    /// <summary>
    /// The class represent the LetterTemplate ListUI.
    /// </summary>
    public partial class LetterTemplateListUI : AdminBaseListUI
    {        
        #region Fields

        private const string ImageColumn       = "ImageColumn";
        private const string LetterType        = "LetterType";
        private const string NameColumn        = "Name";
        private const string DescriptionColumn = "Description";

        private ROIProgressBar fileTransferProgress;

        private Button downloadButton;

        #endregion

        #region Constructor

        public LetterTemplateListUI()
        {
            InitializeComponent();
            Init();            
            
        }

        #endregion

        #region Methods

        /// <summary>
        /// Download Button Initilized
        /// </summary>
        private void Init()
        {
            downloadButton = new Button();
            downloadButton.Name = "downloadButton";
            downloadButton.BackColor = Color.FromArgb(243,243,238);            
            downloadButton.Click += new EventHandler(Process_DownloadButtonClick);
            ButtonBar.Controls.Add(downloadButton);
            ButtonBar.Controls.SetChildIndex(downloadButton, 5);

            InitProgress();
        }

        /// <summary>
        /// Method initilize the progress bar.
        /// </summary>
        private void InitProgress()
        {   
            fileTransferProgress = new ROIProgressBar();
            fileTransferProgress.Location = new Point(((outerPanel.Width / 2) - 50) - fileTransferProgress.Width / 2, 
                                                     (outerPanel.Height / 2) - fileTransferProgress.Height / 2);
            outerPanel.Controls.Add(fileTransferProgress);
            fileTransferProgress.BringToFront();
            fileTransferProgress.MessageText.BringToFront();
            fileTransferProgress.PercentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
        }

        /// <summary>
        /// Initializes the datagrid.
        /// </summary>
        protected override void InitGrid()
        {
            //Add columns to gridview.
            DataGridViewImageColumn dgvImageColumn = grid.AddImageColumn(ImageColumn, string.Empty, "Image", 75);

            dgvImageColumn.DefaultCellStyle.NullValue = null;

            grid.AddTextBoxColumn(LetterType, "Letter Type", "LetterTypeName", 150);
            grid.AddTextBoxColumn(NameColumn, "Name", "Name", 150);
            DataGridViewTextBoxColumn dgvDescriptionColumn = grid.AddTextBoxColumn(DescriptionColumn, "Description", "Description", ColumnWidth);

            dgvDescriptionColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDescriptionColumn.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
        }

        public override void SetData(object data)
        {
            base.SetData(data);
            downloadButton.Enabled = (grid.SelectedRows.Count > 0);
        }
           
        /// <summary>
        /// Method add image icon for LetterTemplate.
        /// </summary>
        /// <param name="data"></param>
        public override void AddRow(object data)
        {
            LetterTemplateDetails letterTemplateDetails = (LetterTemplateDetails)data;

            if (letterTemplateDetails.IsDefault)
            {
                ComparableCollection<LetterTemplateDetails> gridDetails = (ComparableCollection<LetterTemplateDetails>)grid.Items;
                UpdateLetterTemplateDefaultValue(gridDetails, letterTemplateDetails.LetterType);
            }
            base.AddRow(letterTemplateDetails);
            downloadButton.Enabled = true;
        }

        /// <summary>
        /// Method update image LetterTemplate.
        /// </summary>
        /// <param name="data"></param>
        public override void UpdateRow(object data)
        {
            LetterTemplateDetails letterTemplateDetails = (LetterTemplateDetails)data;

            if (letterTemplateDetails.IsDefault)
            {
                ComparableCollection<LetterTemplateDetails> letterTemplates = (ComparableCollection<LetterTemplateDetails>)grid.Items;
                UpdateLetterTemplateDefaultValue(letterTemplates, letterTemplateDetails.LetterType);
            }
            base.UpdateRow(letterTemplateDetails);
        }

        /// <summary>
        /// To updated the letter tempalte default value.
        /// </summary>
        /// <param name="gridLetterTemplate"></param>
        /// <param name="letterType"></param>
        public static void UpdateLetterTemplateDefaultValue(ComparableCollection<LetterTemplateDetails> gridLetterTemplate, LetterType letterType)
        {
            foreach (LetterTemplateDetails details in gridLetterTemplate)
            {
                if (details.LetterType == letterType)
                {
                    details.IsDefault = false;
                }
            }
        }

        /// <summary>
        /// Delete the letter Template.
        /// </summary>
        /// <param name="data"></param>
        protected override void DeleteEntity(object data)
        {
            LetterTemplateDetails letterTemplateToDelete = (LetterTemplateDetails)data;
            ROIAdminController.Instance.DeleteLetterTemplate(letterTemplateToDelete.Id);
        }

        /// <summary>
        /// Select the first row and disable or enable the download button.
        /// </summary>
        protected override void SelectFirstRow()
        {
            base.SelectFirstRow();
            downloadButton.Enabled = (grid.Rows.Count == 0) ? false : true;
        }

        protected override object RefreshEntityData(object data)
        {
            LetterTemplateDetails letterTemplate = (LetterTemplateDetails)data;
            letterTemplate = ROIAdminController.Instance.GetLetterTemplate(letterTemplate.Id);
            return letterTemplate;            
        }
               
        /// <summary>
        ///  Set Culture text to the controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, deleteButton);
            SetLabel(rm, createButton);
            SetLabel(rm, downloadButton);
            countLabel.Tag = rm.GetString("countLabel." + GetType().Name);
            UpdateRowCount();

            grid.Columns[ImageColumn].HeaderText       = rm.GetString("default.columnHeader");
            grid.Columns[LetterType].HeaderText        = rm.GetString("lettertype.columnHeader");
            grid.Columns[NameColumn].HeaderText        = rm.GetString("name.columnHeader");
            grid.Columns[DescriptionColumn].HeaderText = rm.GetString("description.columnHeader");

            //Progress Bar.
            fileTransferProgress.MessageText.Text = rm.GetString("LetterTemplate.ProgressMessage");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, deleteButton);
            SetTooltip(rm, toolTip, createButton);
            SetTooltip(rm, toolTip, downloadButton);
        }

        /// <summary>
        /// Gets the LocalizeKey of UI controls to show tooltip message.
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Download the Letter Template file.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_DownloadButtonClick(object sender, EventArgs e)
        {
            if (grid.SelectedRows.Count > 0)
            {
                LetterTemplateDetails letterTemplate = (LetterTemplateDetails)grid.SelectedRows[0].DataBoundItem;
                if (letterTemplate.DocumentId == 0)
                {
                    ROIException invalidDocuemntIdException = new ROIException(ROIErrorCodes.LetterTemplateInvalidDocumentId);
                    ROIViewUtility.Handle(Context, invalidDocuemntIdException);
                    return;
                }
                SaveFileDialog saveFileDialog = new SaveFileDialog();
                saveFileDialog.Filter = "Word Document (*.docx)|*.docx";
                saveFileDialog.FilterIndex = 1;

                if (saveFileDialog.ShowDialog() == DialogResult.OK)
                {
                    ROIAdminController.DownloadLetterTemplate(letterTemplate, saveFileDialog.FileName);
                }
            }
        }

        /// <summary>
        /// Method call the progress bar to display.
        /// </summary>
        /// <param name="e"></param>
        public void ShowProgress(FileTransferEventArgs e)
        {
            switch (e.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    fileTransferProgress.Visible = true;
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.InProgress:
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.Finish:
                    fileTransferProgress.ShowProgress(e);
                    fileTransferProgress.Visible = false;
                    break;
            }
        }

        public override void EnableCreate()
        {
            base.EnableCreate();
            downloadButton.Enabled = (grid.SelectedRows.Count > 0);
        }

        public override void DisableCreate()
        {
            base.DisableCreate();
            downloadButton.Enabled = false;
        }
        
        #endregion

        #region Properties
        
        /// <summary>
        /// Gets the key of delete confirm message
        /// </summary>
        public override string DeleteMessageKey
        {
            get { return ROIErrorCodes.LetterTemplateDeleteMessage; }
        }

        #endregion
    }
}
