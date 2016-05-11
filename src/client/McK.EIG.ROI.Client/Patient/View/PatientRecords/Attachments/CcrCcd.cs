using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Resources;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords.Attachments
{
    public partial class CcrCcd : ROIBaseUI, IAttachmentDetailUI
    {
        private const string SubmittedByKey = "submitted-by";
        private bool editMode;
        private bool isReleased;

        public CcrCcd(bool editMode, bool isReleased)
        {
            this.editMode = editMode;
            this.isReleased = isReleased;

            InitializeComponent();
        }

        private void selectFileButton_Click(object sender, EventArgs e)
        {
            if (openRawAttachmentDialog.ShowDialog() == DialogResult.OK)
            {
                rawAttachmentTextBox.Text = openRawAttachmentDialog.FileName;
                //System.IO.StreamReader sr = new
                //   System.IO.StreamReader(openRawAttachmentDialog.FileName);
                //MessageBox.Show(sr.ReadToEnd());
                //sr.Close();
            }
        }

        /// <summary>
        /// PrePopulate
        /// </summary>
        public void PrePopulate()
        {
        }


        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, rawFileLabel);
            SetLabel(rm, rawPageCountLabel);
            SetLabel(rm, hrFileLabel);
            SetLabel(rm, hrPageCountLabel);
            SetLabel(rm, submittedByLabel);
            SetLabel(rm, selectRawFileButton);
            SetLabel(rm, selectHrFileButton);
            SetLabel(rm, ccrAttachInstructions);
            SetLabel(rm, formattedGroupBox);
            SetLabel(rm, rawGroupBox);            

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, rawAttachmentTextBox);
            SetTooltip(rm, toolTip, hrAttachmentTextBox);
            SetTooltip(rm, toolTip, submittedByTextBox);
        }

        private void selectHrFileButton_Click(object sender, EventArgs e)
        {
            if (openHRAttachmentDialog.ShowDialog() == DialogResult.OK)
            {
                hrAttachmentTextBox.Text = openHRAttachmentDialog.FileName;
                //System.IO.StreamReader sr = new
                //   System.IO.StreamReader(openHrAttachmentDialog.FileName);
                //MessageBox.Show(sr.ReadToEnd());
                //sr.Close();
            }

        }

        public void SetData(object data) 
        {
            AttachmentDetails tmpAttachment = data == null ? new AttachmentDetails() : data as AttachmentDetails;

            submittedByTextBox.Text = tmpAttachment.GetAttachmentTypeDetail(SubmittedByKey);
            if (String.IsNullOrEmpty(submittedByTextBox.Text))
                submittedByTextBox.Text = tmpAttachment.DocumentSource;
        }

        public object GetData(object data) {
            AttachmentDetails tmpAttachment = data == null ? new AttachmentDetails() : data as AttachmentDetails;
            tmpAttachment.AddAttachmentTypeDetail(SubmittedByKey, submittedByTextBox.Text.Trim());

            tmpAttachment.DocumentSource = submittedByTextBox.Text.Trim();
            return tmpAttachment;

        }
        public void ClearControls() { }

        public void ShowControls() 
        {
            if (editMode)
            {
                this.rawGroupBox.Visible = false;
                this.formattedGroupBox.Visible = false;
                this.attachInstrPictureBox.Visible = false;
                this.ccrAttachInstructions.Visible = false;
            }
            if (isReleased)
            {
                this.submittedByTextBox.Enabled = false;
            }
        }


        public void ValidatePrimaryFields() {
            if (!editMode)
            {
                int pageCount = 0;
                if (rawAttachmentTextBox.Text.Length > 0)
                {
                    try
                    {
                        System.IO.FileInfo fi = new System.IO.FileInfo(rawAttachmentTextBox.Text);
                        if (!fi.Exists)
                        {
                            throw new ROIException(ROIErrorCodes.CcrCcdRawInvalidFile);
                        }
                    }
                    catch
                    {
                        throw new ROIException(ROIErrorCodes.CcrCcdRawInvalidFile);
                    }

                    if (!int.TryParse(rawPageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
                    {
                        throw new ROIException(ROIErrorCodes.CcrCcdRawInvalidPageCount);
                    }
                }

                if (hrAttachmentTextBox.Text.Length > 0)
                {
                    try
                    {
                        System.IO.FileInfo fi = new System.IO.FileInfo(hrAttachmentTextBox.Text);
                        if (!fi.Exists)
                        {
                            throw new ROIException(ROIErrorCodes.CcrCcdFormattedInvalidFile);
                        }
                    }
                    catch
                    {
                        throw new ROIException(ROIErrorCodes.CcrCcdFormattedInvalidFile);
                    }

                    pageCount = 0;
                    if (!int.TryParse(hrPageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
                    {
                        throw new ROIException(ROIErrorCodes.CcrCcdFormattedInvalidPageCount);
                    }
                }
            }

        }

        public List<object> UploadAttachment(EventHandler progressHandler)
        {
            List<object> fileDetails = new List<object>();

            AttachmentFileDetails rawFileDetail = null;
            AttachmentFileDetails hrFileDetail = null;

            //upload XML Doc
            if (rawAttachmentTextBox.Text.Length > 0)
            {
                int pageCount = 0;
                if (!int.TryParse(rawPageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
                {
                    throw new ROIException(ROIErrorCodes.CcrCcdRawInvalidPageCount);
                }

                rawFileDetail =
                    PatientController.Instance.UploadCCRCCDXMLAttachment(rawAttachmentTextBox.Text, progressHandler);

                rawFileDetail.PageCount = pageCount;
                fileDetails.Add(rawFileDetail);
            }

            //upload HumanReadable Doc
            if (hrAttachmentTextBox.Text.Length > 0)
            {
                int pageCount = 0;
                if (!int.TryParse(hrPageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
                {
                    throw new ROIException(ROIErrorCodes.CcrCcdFormattedInvalidPageCount);
                }

                hrFileDetail =
                    PatientController.Instance.UploadAttachment(hrAttachmentTextBox.Text, progressHandler);
                hrFileDetail.PageCount = pageCount;
                fileDetails.Add(hrFileDetail);
            }

            //request HumanReadable file creation if HR not available
            if (hrFileDetail == null)
            {
                hrFileDetail =
                    PatientController.Instance.GenerateCcrCcdPdf(rawFileDetail.Uuid);
                hrFileDetail.FileName = rawFileDetail.FileName;
                fileDetails.Add(hrFileDetail);
            }


            return fileDetails;
        }

        private void rawAttachmentTextBox_TextChanged(object sender, EventArgs e)
        {
            if (rawAttachmentTextBox.Text.Length == 0)
            {
                rawPageCountPictureBox.Visible = false;
                rawPageCountTextBox.Text = string.Empty;
            }
            else
            {
                rawPageCountPictureBox.Visible = true;
                if (string.IsNullOrEmpty(rawPageCountTextBox.Text))
                {
                    rawPageCountTextBox.Text = "1";
                }
            }
        }

        private void hrAttachmentTextBox_TextChanged(object sender, EventArgs e)
        {
            if (hrAttachmentTextBox.Text.Length == 0)
            {
                hrPageCountPictureBox.Visible = false;
            }
            else
            {
                hrPageCountPictureBox.Visible = true;
            }
        }

        public bool EnableSaveButton()
        {
            bool dataSet = false;

            if (editMode)
            {
                return true;
            }

            if (rawAttachmentTextBox.Text.Trim().Length > 0 &&
                rawPageCountTextBox.Text.Trim().Length > 0)
            {
                dataSet = true;
            }
            else
            {
                if (hrAttachmentTextBox.Text.Trim().Length > 0 &&
                    hrPageCountTextBox.Text.Trim().Length > 0)
                {
                    dataSet = true;
                }
            }

            return dataSet;
        }

        public void EnableEvents(EventHandler eventHandler)
        {
            rawPageCountTextBox.TextChanged += eventHandler;
            rawAttachmentTextBox.TextChanged += eventHandler;
            hrPageCountTextBox.TextChanged += eventHandler;
            hrAttachmentTextBox.TextChanged += eventHandler;
            submittedByTextBox.TextChanged += eventHandler;
        }

        public void DisableEvents(EventHandler eventHandler)
        {
            rawPageCountTextBox.TextChanged -= eventHandler;
            rawAttachmentTextBox.TextChanged -= eventHandler;
            hrPageCountTextBox.TextChanged -= eventHandler;
            hrAttachmentTextBox.TextChanged -= eventHandler;
            submittedByTextBox.TextChanged -= eventHandler;
        }

        /// <summary>
        /// Returns the respective control for the given error code.
        /// </summary>
        /// <param name="errorCode"></param>
        /// <returns></returns>
        public override Control GetErrorControl(ExceptionData error)
        {
            switch (error.ErrorCode)
            {
                case ROIErrorCodes.CcrCcdRawInvalidPageCount: return rawPageCountTextBox;
                case ROIErrorCodes.CcrCcdFormattedInvalidPageCount: return hrPageCountTextBox;
                case ROIErrorCodes.CcrCcdRawInvalidFile: return rawAttachmentTextBox;
                case ROIErrorCodes.CcrCcdFormattedInvalidFile: return hrAttachmentTextBox;
            }
            return null;
        }

        //CR# 368487 Page count error message is invalid.
        /// <summary>
        /// Occurs when user has changed the page count for raw xml files
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void rawPageCountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back)
            {
                e.Handled = true;
            }
        }

        //CR# 368487 Page count error message is invalid.
        /// <summary>
        /// Occurs when user has changed the page count for pdf format files
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void hrPageCountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back)
            {
                e.Handled = true;
            }

        }

   }
}
