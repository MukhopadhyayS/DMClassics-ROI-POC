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
    public partial class FileAttachment : ROIBaseUI, IAttachmentDetailUI
    {

        private bool editMode;        

        public FileAttachment(bool editMode)
        {
            this.editMode = editMode;

            InitializeComponent();
        }

        private void selectFileButton_Click(object sender, EventArgs e)
        {
            if (openFileAttachmentDialog.ShowDialog() == DialogResult.OK)
            {
                fileAttachmentTextBox.Text = openFileAttachmentDialog.FileName;
                //System.IO.StreamReader sr = new
                //   System.IO.StreamReader(openFileAttachmentDialog.FileName);
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

            SetLabel(rm, fileAttachmentLabel);
            SetLabel(rm, filePageCountLabel);
            SetLabel(rm, fileMimeLabel);
            SetLabel(rm, selectFileButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, fileAttachmentTextBox);
            SetTooltip(rm, toolTip, filePageCountTextBox);
            SetTooltip(rm, toolTip, fileMimeTypeTextBox);
        }
       
        //no op
        public void SetData(object data) { }

        public object GetData(object data) 
        {
            AttachmentDetails tmpAttachment = data == null ? new AttachmentDetails() : data as AttachmentDetails;

            return tmpAttachment;
        }

        public void ShowControls() 
        {
            if (editMode)
            {
                fileAttachmentLabel.Visible = false;
                fileAttachmentTextBox.Visible = false;
                selectFileButton.Visible = false;
                filePageCountLabel.Visible = false;
                filePageCountTextBox.Visible = false;
                fileMimeTypeTextBox.Visible = false;
                fileMimeLabel.Visible = false;
                pictureBox1.Visible = false;
                hrPageCountPictureBox.Visible = false;            
            }       
        }

        public void ClearControls() { }

        public void ValidatePrimaryFields() {  }


        public List<object> UploadAttachment(EventHandler progressHandler)
        { 
            List<object> fileDetails = new List<object>();
            int pageCount = 0;

            AttachmentFileDetails tmpFileDetail =
                PatientController.Instance.UploadAttachment(fileAttachmentTextBox.Text, progressHandler);
            if (!int.TryParse(filePageCountTextBox.Text.Trim(), out pageCount) || pageCount <= 0)
            {
                throw new ROIException(ROIErrorCodes.AttachmentInvalidPageCount);
            }
            tmpFileDetail.PageCount = pageCount;

            fileDetails.Add(tmpFileDetail);


            return fileDetails;
        }

        public void EnableEvents(EventHandler eventHandler)
        {
            filePageCountTextBox.TextChanged += eventHandler;
            fileAttachmentTextBox.TextChanged += eventHandler;
        }

        public void DisableEvents(EventHandler eventHandler)
        {
            filePageCountTextBox.TextChanged -= eventHandler;
            fileAttachmentTextBox.TextChanged -= eventHandler;
        }

        public bool EnableSaveButton()
        {

            if (editMode)
            {
                return true;
            }

            bool dataSet = false;
            if (fileAttachmentTextBox.Text.Trim().Length > 0 &&
                filePageCountTextBox.Text.Trim().Length > 0)
            {
                dataSet = true;
            }

            return dataSet;
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
                case ROIErrorCodes.AttachmentInvalidPageCount: return filePageCountTextBox;
            }
            return null;
        }

    }
}
