namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    partial class AttachmentDialog
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.topPanel = new System.Windows.Forms.Panel();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.attachmentLocationlabel = new System.Windows.Forms.Label();
            this.attchmentLocationComboBox = new System.Windows.Forms.ComboBox();
            this.docTypePictureBox = new System.Windows.Forms.PictureBox();
            this.lowerPanel = new System.Windows.Forms.Panel();
            this.attachmentDetailPanel = new System.Windows.Forms.Panel();
            this.commentTextBox = new System.Windows.Forms.TextBox();
            this.commentLabel = new System.Windows.Forms.Label();
            this.upperPanel = new System.Windows.Forms.Panel();
            this.filePageCountTextBox = new System.Windows.Forms.TextBox();
            this.fileUploadDateTextBox = new System.Windows.Forms.TextBox();
            this.filePageCountLabel = new System.Windows.Forms.Label();
            this.pageCountPictureBox = new System.Windows.Forms.PictureBox();
            this.attachmentDateUploadedLabel = new System.Windows.Forms.Label();
            this.subTitleTextBox = new System.Windows.Forms.TextBox();
            this.fileNameTextBox = new System.Windows.Forms.TextBox();
            this.attachmentFileNameLabel = new System.Windows.Forms.Label();
            this.attachmentTitleLabel = new System.Windows.Forms.Label();
            this.facilityPictureBox = new System.Windows.Forms.PictureBox();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.facilityComboBox = new System.Windows.Forms.ComboBox();
            this.dosTextBox = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.datePictureBox = new System.Windows.Forms.PictureBox();
            this.dateOfServiceLabel = new System.Windows.Forms.Label();
            this.encounterTextBox = new System.Windows.Forms.TextBox();
            this.encounterLabel = new System.Windows.Forms.Label();
            this.controlPanel = new System.Windows.Forms.Panel();
            this.deleteButton = new System.Windows.Forms.Button();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.bottomPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.docTypePictureBox)).BeginInit();
            this.lowerPanel.SuspendLayout();
            this.upperPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pageCountPictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.facilityPictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.datePictureBox)).BeginInit();
            this.controlPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.topPanel.Location = new System.Drawing.Point(5, 5);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(661, 77);
            this.topPanel.TabIndex = 0;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.attachmentLocationlabel);
            this.bottomPanel.Controls.Add(this.attchmentLocationComboBox);
            this.bottomPanel.Controls.Add(this.docTypePictureBox);
            this.bottomPanel.Controls.Add(this.lowerPanel);
            this.bottomPanel.Controls.Add(this.upperPanel);
            this.bottomPanel.Controls.Add(this.controlPanel);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.bottomPanel.Location = new System.Drawing.Point(5, 82);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(661, 433);
            this.bottomPanel.TabIndex = 1;
            // 
            // attachmentLocationlabel
            // 
            this.attachmentLocationlabel.AutoSize = true;
            this.attachmentLocationlabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.attachmentLocationlabel.Location = new System.Drawing.Point(23, 5);
            this.attachmentLocationlabel.Name = "attachmentLocationlabel";
            this.attachmentLocationlabel.Size = new System.Drawing.Size(0, 15);
            this.attachmentLocationlabel.TabIndex = 41;
            // 
            // attchmentLocationComboBox
            // 
            this.attchmentLocationComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.attchmentLocationComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.attchmentLocationComboBox.FormattingEnabled = true;
            this.attchmentLocationComboBox.Location = new System.Drawing.Point(145, 2);
            this.attchmentLocationComboBox.Name = "attchmentLocationComboBox";
            this.attchmentLocationComboBox.Size = new System.Drawing.Size(191, 23);
            this.attchmentLocationComboBox.TabIndex = 1;
            this.attchmentLocationComboBox.SelectedIndexChanged += new System.EventHandler(this.attchmentLocationComboBox_SelectedIndexChanged);
            // 
            // docTypePictureBox
            // 
            this.docTypePictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.docTypePictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.docTypePictureBox.Location = new System.Drawing.Point(9, 7);
            this.docTypePictureBox.Name = "docTypePictureBox";
            this.docTypePictureBox.Size = new System.Drawing.Size(9, 10);
            this.docTypePictureBox.TabIndex = 19;
            this.docTypePictureBox.TabStop = false;
            // 
            // lowerPanel
            // 
            this.lowerPanel.Controls.Add(this.attachmentDetailPanel);
            this.lowerPanel.Controls.Add(this.commentTextBox);
            this.lowerPanel.Controls.Add(this.commentLabel);
            this.lowerPanel.Location = new System.Drawing.Point(3, 138);
            this.lowerPanel.Name = "lowerPanel";
            this.lowerPanel.Size = new System.Drawing.Size(648, 251);
            this.lowerPanel.TabIndex = 0;
            // 
            // attachmentDetailPanel
            // 
            this.attachmentDetailPanel.Location = new System.Drawing.Point(4, 3);
            this.attachmentDetailPanel.Name = "attachmentDetailPanel";
            this.attachmentDetailPanel.Size = new System.Drawing.Size(641, 147);
            this.attachmentDetailPanel.TabIndex = 11;
            // 
            // commentTextBox
            // 
            this.commentTextBox.AcceptsReturn = true;
            this.commentTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.commentTextBox.Location = new System.Drawing.Point(4, 169);
            this.commentTextBox.Multiline = true;
            this.commentTextBox.Name = "commentTextBox";
            this.commentTextBox.Size = new System.Drawing.Size(644, 63);
            this.commentTextBox.TabIndex = 12;
            // 
            // commentLabel
            // 
            this.commentLabel.AutoSize = true;
            this.commentLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.commentLabel.Location = new System.Drawing.Point(3, 153);
            this.commentLabel.Name = "commentLabel";
            this.commentLabel.Size = new System.Drawing.Size(0, 15);
            this.commentLabel.TabIndex = 10;
            // 
            // upperPanel
            // 
            this.upperPanel.Controls.Add(this.filePageCountTextBox);
            this.upperPanel.Controls.Add(this.fileUploadDateTextBox);
            this.upperPanel.Controls.Add(this.filePageCountLabel);
            this.upperPanel.Controls.Add(this.pageCountPictureBox);
            this.upperPanel.Controls.Add(this.attachmentDateUploadedLabel);
            this.upperPanel.Controls.Add(this.subTitleTextBox);
            this.upperPanel.Controls.Add(this.fileNameTextBox);
            this.upperPanel.Controls.Add(this.attachmentFileNameLabel);
            this.upperPanel.Controls.Add(this.attachmentTitleLabel);
            this.upperPanel.Controls.Add(this.facilityPictureBox);
            this.upperPanel.Controls.Add(this.facilityLabel);
            this.upperPanel.Controls.Add(this.facilityComboBox);
            this.upperPanel.Controls.Add(this.dosTextBox);
            this.upperPanel.Controls.Add(this.datePictureBox);
            this.upperPanel.Controls.Add(this.dateOfServiceLabel);
            this.upperPanel.Controls.Add(this.encounterTextBox);
            this.upperPanel.Controls.Add(this.encounterLabel);
            this.upperPanel.Location = new System.Drawing.Point(3, 25);
            this.upperPanel.Name = "upperPanel";
            this.upperPanel.Size = new System.Drawing.Size(648, 114);
            this.upperPanel.TabIndex = 41;
            // 
            // filePageCountTextBox
            // 
            this.filePageCountTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.filePageCountTextBox.Location = new System.Drawing.Point(142, 89);
            this.filePageCountTextBox.MaxLength = 5;
            this.filePageCountTextBox.Name = "filePageCountTextBox";
            this.filePageCountTextBox.Size = new System.Drawing.Size(191, 21);
            this.filePageCountTextBox.TabIndex = 6;
            this.filePageCountTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.filePageCountTextBox_KeyPress);
            // 
            // fileUploadDateTextBox
            // 
            this.fileUploadDateTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fileUploadDateTextBox.Location = new System.Drawing.Point(461, 60);
            this.fileUploadDateTextBox.MaxLength = 256;
            this.fileUploadDateTextBox.Name = "fileUploadDateTextBox";
            this.fileUploadDateTextBox.Size = new System.Drawing.Size(181, 21);
            this.fileUploadDateTextBox.TabIndex = 9;
            // 
            // filePageCountLabel
            // 
            this.filePageCountLabel.AutoSize = true;
            this.filePageCountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.filePageCountLabel.Location = new System.Drawing.Point(20, 90);
            this.filePageCountLabel.Name = "filePageCountLabel";
            this.filePageCountLabel.Size = new System.Drawing.Size(0, 15);
            this.filePageCountLabel.TabIndex = 34;
            // 
            // pageCountPictureBox
            // 
            this.pageCountPictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pageCountPictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pageCountPictureBox.Location = new System.Drawing.Point(5, 89);
            this.pageCountPictureBox.Name = "pageCountPictureBox";
            this.pageCountPictureBox.Size = new System.Drawing.Size(9, 10);
            this.pageCountPictureBox.TabIndex = 35;
            this.pageCountPictureBox.TabStop = false;
            // 
            // attachmentDateUploadedLabel
            // 
            this.attachmentDateUploadedLabel.AutoSize = true;
            this.attachmentDateUploadedLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.attachmentDateUploadedLabel.Location = new System.Drawing.Point(370, 63);
            this.attachmentDateUploadedLabel.Name = "attachmentDateUploadedLabel";
            this.attachmentDateUploadedLabel.Size = new System.Drawing.Size(0, 15);
            this.attachmentDateUploadedLabel.TabIndex = 40;
            // 
            // subTitleTextBox
            // 
            this.subTitleTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.subTitleTextBox.Location = new System.Drawing.Point(142, 6);
            this.subTitleTextBox.MaxLength = 256;
            this.subTitleTextBox.Name = "subTitleTextBox";
            this.subTitleTextBox.Size = new System.Drawing.Size(191, 21);
            this.subTitleTextBox.TabIndex = 2;
            // 
            // fileNameTextBox
            // 
            this.fileNameTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fileNameTextBox.Location = new System.Drawing.Point(142, 61);
            this.fileNameTextBox.MaxLength = 256;
            this.fileNameTextBox.Name = "fileNameTextBox";
            this.fileNameTextBox.Size = new System.Drawing.Size(190, 21);
            this.fileNameTextBox.TabIndex = 4;
            // 
            // attachmentFileNameLabel
            // 
            this.attachmentFileNameLabel.AutoSize = true;
            this.attachmentFileNameLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.attachmentFileNameLabel.Location = new System.Drawing.Point(21, 60);
            this.attachmentFileNameLabel.Name = "attachmentFileNameLabel";
            this.attachmentFileNameLabel.Size = new System.Drawing.Size(0, 15);
            this.attachmentFileNameLabel.TabIndex = 38;
            // 
            // attachmentTitleLabel
            // 
            this.attachmentTitleLabel.AutoSize = true;
            this.attachmentTitleLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.attachmentTitleLabel.Location = new System.Drawing.Point(20, 5);
            this.attachmentTitleLabel.Name = "attachmentTitleLabel";
            this.attachmentTitleLabel.Size = new System.Drawing.Size(0, 15);
            this.attachmentTitleLabel.TabIndex = 22;
            // 
            // facilityPictureBox
            // 
            this.facilityPictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.facilityPictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.facilityPictureBox.Location = new System.Drawing.Point(355, 12);
            this.facilityPictureBox.Name = "facilityPictureBox";
            this.facilityPictureBox.Size = new System.Drawing.Size(9, 10);
            this.facilityPictureBox.TabIndex = 36;
            this.facilityPictureBox.TabStop = false;
            // 
            // facilityLabel
            // 
            this.facilityLabel.AutoSize = true;
            this.facilityLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityLabel.Location = new System.Drawing.Point(370, 9);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(0, 15);
            this.facilityLabel.TabIndex = 6;
            // 
            // facilityComboBox
            // 
            this.facilityComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityComboBox.FormattingEnabled = true;
            this.facilityComboBox.Location = new System.Drawing.Point(461, 4);
            this.facilityComboBox.MaxLength = 256;
            this.facilityComboBox.Name = "facilityComboBox";
            this.facilityComboBox.Size = new System.Drawing.Size(181, 23);
            this.facilityComboBox.TabIndex = 7;
            // 
            // dosTextBox
            // 
            this.dosTextBox.FormattedDate = null;
            this.dosTextBox.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.dosTextBox.IsValidDate = false;
            this.dosTextBox.Location = new System.Drawing.Point(142, 34);
            this.dosTextBox.Mask = "AA/AA/AAAA";
            this.dosTextBox.Name = "dosTextBox";
            this.dosTextBox.PromptChar = ' ';
            this.dosTextBox.Size = new System.Drawing.Size(191, 20);
            this.dosTextBox.TabIndex = 3;
            // 
            // datePictureBox
            // 
            this.datePictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.datePictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.datePictureBox.Location = new System.Drawing.Point(5, 36);
            this.datePictureBox.Name = "datePictureBox";
            this.datePictureBox.Size = new System.Drawing.Size(9, 10);
            this.datePictureBox.TabIndex = 27;
            this.datePictureBox.TabStop = false;
            // 
            // dateOfServiceLabel
            // 
            this.dateOfServiceLabel.AutoSize = true;
            this.dateOfServiceLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dateOfServiceLabel.Location = new System.Drawing.Point(21, 32);
            this.dateOfServiceLabel.Name = "dateOfServiceLabel";
            this.dateOfServiceLabel.Size = new System.Drawing.Size(0, 15);
            this.dateOfServiceLabel.TabIndex = 4;
            // 
            // encounterTextBox
            // 
            this.encounterTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.encounterTextBox.Location = new System.Drawing.Point(461, 33);
            this.encounterTextBox.MaxLength = 256;
            this.encounterTextBox.Name = "encounterTextBox";
            this.encounterTextBox.Size = new System.Drawing.Size(181, 21);
            this.encounterTextBox.TabIndex = 8;
            // 
            // encounterLabel
            // 
            this.encounterLabel.AutoSize = true;
            this.encounterLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.encounterLabel.Location = new System.Drawing.Point(370, 35);
            this.encounterLabel.Name = "encounterLabel";
            this.encounterLabel.Size = new System.Drawing.Size(0, 15);
            this.encounterLabel.TabIndex = 0;
            // 
            // controlPanel
            // 
            this.controlPanel.Controls.Add(this.deleteButton);
            this.controlPanel.Controls.Add(this.requiredImage);
            this.controlPanel.Controls.Add(this.requiredLabel);
            this.controlPanel.Controls.Add(this.cancelButton);
            this.controlPanel.Controls.Add(this.saveButton);
            this.controlPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.controlPanel.Location = new System.Drawing.Point(0, 395);
            this.controlPanel.Name = "controlPanel";
            this.controlPanel.Size = new System.Drawing.Size(661, 38);
            this.controlPanel.TabIndex = 13;
            // 
            // deleteButton
            // 
            this.deleteButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.deleteButton.Location = new System.Drawing.Point(389, 9);
            this.deleteButton.Name = "deleteButton";
            this.deleteButton.Size = new System.Drawing.Size(75, 23);
            this.deleteButton.TabIndex = 3;
            this.deleteButton.UseVisualStyleBackColor = true;
            this.deleteButton.Click += new System.EventHandler(this.deleteButton_Click);
            // 
            // requiredImage
            // 
            this.requiredImage.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImage.Location = new System.Drawing.Point(10, 16);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(9, 10);
            this.requiredImage.TabIndex = 18;
            this.requiredImage.TabStop = false;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(23, 14);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 17;
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(308, 9);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 2;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // saveButton
            // 
            this.saveButton.Enabled = false;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(227, 9);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 23);
            this.saveButton.TabIndex = 1;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // AttachmentDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.bottomPanel);
            this.Controls.Add(this.topPanel);
            this.Name = "AttachmentDialog";
            this.Padding = new System.Windows.Forms.Padding(5);
            this.Size = new System.Drawing.Size(671, 520);
            this.bottomPanel.ResumeLayout(false);
            this.bottomPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.docTypePictureBox)).EndInit();
            this.lowerPanel.ResumeLayout(false);
            this.lowerPanel.PerformLayout();
            this.upperPanel.ResumeLayout(false);
            this.upperPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pageCountPictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.facilityPictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.datePictureBox)).EndInit();
            this.controlPanel.ResumeLayout(false);
            this.controlPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.TextBox encounterTextBox;
        private System.Windows.Forms.Label encounterLabel;
        private System.Windows.Forms.TextBox commentTextBox;
        private System.Windows.Forms.Label commentLabel;
        private System.Windows.Forms.ComboBox facilityComboBox;
        private System.Windows.Forms.Label facilityLabel;
        private System.Windows.Forms.Label dateOfServiceLabel;
        private System.Windows.Forms.ComboBox attchmentLocationComboBox;
        private System.Windows.Forms.Panel controlPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.Button deleteButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.PictureBox docTypePictureBox;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.TextBox subTitleTextBox;
        private System.Windows.Forms.Label attachmentTitleLabel;
        private System.Windows.Forms.PictureBox datePictureBox;
        private McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl dosTextBox;
        private System.Windows.Forms.Panel attachmentDetailPanel;
        private System.Windows.Forms.PictureBox pageCountPictureBox;
        private System.Windows.Forms.Label filePageCountLabel;
        private System.Windows.Forms.TextBox filePageCountTextBox;
        private System.Windows.Forms.PictureBox facilityPictureBox;
        private System.Windows.Forms.TextBox fileNameTextBox;
        private System.Windows.Forms.Label attachmentFileNameLabel;
        private System.Windows.Forms.TextBox fileUploadDateTextBox;
        private System.Windows.Forms.Label attachmentDateUploadedLabel;
        private System.Windows.Forms.Panel upperPanel;
        private System.Windows.Forms.Panel lowerPanel;
        private System.Windows.Forms.Label attachmentLocationlabel;
    }
}
