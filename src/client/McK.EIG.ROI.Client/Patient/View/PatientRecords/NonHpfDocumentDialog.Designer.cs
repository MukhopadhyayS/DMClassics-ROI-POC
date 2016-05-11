namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    partial class NonHpfDocumentDialog
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
            this.datePictureBox = new System.Windows.Forms.PictureBox();
            this.pageCountPictureBox = new System.Windows.Forms.PictureBox();
            this.pageCountTextBox = new System.Windows.Forms.TextBox();
            this.pageCountLabel = new System.Windows.Forms.Label();
            this.subTitleTextBox = new System.Windows.Forms.TextBox();
            this.subTitleLabel = new System.Windows.Forms.Label();
            this.facilityPictureBox = new System.Windows.Forms.PictureBox();
            this.docTypePictureBox = new System.Windows.Forms.PictureBox();
            this.controlPanel = new System.Windows.Forms.Panel();
            this.deleteButton = new System.Windows.Forms.Button();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.commentTextBox = new System.Windows.Forms.TextBox();
            this.commentLabel = new System.Windows.Forms.Label();
            this.departmentComboBox = new System.Windows.Forms.ComboBox();
            this.departmentLabel = new System.Windows.Forms.Label();
            this.facilityComboBox = new System.Windows.Forms.ComboBox();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.dateOfServiceLabel = new System.Windows.Forms.Label();
            this.docTypeComboBox = new System.Windows.Forms.ComboBox();
            this.docTypeLabel = new System.Windows.Forms.Label();
            this.encounterTextBox = new System.Windows.Forms.TextBox();
            this.encounterLabel = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.dosTextBox = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.bottomPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.datePictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pageCountPictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.facilityPictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.docTypePictureBox)).BeginInit();
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
            this.bottomPanel.Controls.Add(this.dosTextBox);
            this.bottomPanel.Controls.Add(this.datePictureBox);
            this.bottomPanel.Controls.Add(this.pageCountPictureBox);
            this.bottomPanel.Controls.Add(this.pageCountTextBox);
            this.bottomPanel.Controls.Add(this.pageCountLabel);
            this.bottomPanel.Controls.Add(this.subTitleTextBox);
            this.bottomPanel.Controls.Add(this.subTitleLabel);
            this.bottomPanel.Controls.Add(this.facilityPictureBox);
            this.bottomPanel.Controls.Add(this.docTypePictureBox);
            this.bottomPanel.Controls.Add(this.controlPanel);
            this.bottomPanel.Controls.Add(this.commentTextBox);
            this.bottomPanel.Controls.Add(this.commentLabel);
            this.bottomPanel.Controls.Add(this.departmentComboBox);
            this.bottomPanel.Controls.Add(this.departmentLabel);
            this.bottomPanel.Controls.Add(this.facilityComboBox);
            this.bottomPanel.Controls.Add(this.facilityLabel);
            this.bottomPanel.Controls.Add(this.dateOfServiceLabel);
            this.bottomPanel.Controls.Add(this.docTypeComboBox);
            this.bottomPanel.Controls.Add(this.docTypeLabel);
            this.bottomPanel.Controls.Add(this.encounterTextBox);
            this.bottomPanel.Controls.Add(this.encounterLabel);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.bottomPanel.Location = new System.Drawing.Point(5, 82);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(661, 263);
            this.bottomPanel.TabIndex = 1;
            // 
            // datePictureBox
            // 
            this.datePictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.datePictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.datePictureBox.Location = new System.Drawing.Point(10, 73);
            this.datePictureBox.Name = "datePictureBox";
            this.datePictureBox.Size = new System.Drawing.Size(9, 10);
            this.datePictureBox.TabIndex = 27;
            this.datePictureBox.TabStop = false;
            // 
            // pageCountPictureBox
            // 
            this.pageCountPictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pageCountPictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pageCountPictureBox.Location = new System.Drawing.Point(10, 100);
            this.pageCountPictureBox.Name = "pageCountPictureBox";
            this.pageCountPictureBox.Size = new System.Drawing.Size(9, 10);
            this.pageCountPictureBox.TabIndex = 26;
            this.pageCountPictureBox.TabStop = false;
            // 
            // pageCountTextBox
            // 
            this.pageCountTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.pageCountTextBox.Location = new System.Drawing.Point(126, 100);
            this.pageCountTextBox.MaxLength = 5;
            this.pageCountTextBox.Name = "pageCountTextBox";
            this.pageCountTextBox.Size = new System.Drawing.Size(191, 21);
            this.pageCountTextBox.TabIndex = 4;
            // 
            // pageCountLabel
            // 
            this.pageCountLabel.AutoSize = true;
            this.pageCountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.pageCountLabel.Location = new System.Drawing.Point(25, 100);
            this.pageCountLabel.Name = "pageCountLabel";
            this.pageCountLabel.Size = new System.Drawing.Size(0, 15);
            this.pageCountLabel.TabIndex = 24;
            // 
            // subTitleTextBox
            // 
            this.subTitleTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.subTitleTextBox.Location = new System.Drawing.Point(126, 46);
            this.subTitleTextBox.MaxLength = 256;
            this.subTitleTextBox.Name = "subTitleTextBox";
            this.subTitleTextBox.Size = new System.Drawing.Size(191, 21);
            this.subTitleTextBox.TabIndex = 2;
            // 
            // subTitleLabel
            // 
            this.subTitleLabel.AutoSize = true;
            this.subTitleLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.subTitleLabel.Location = new System.Drawing.Point(25, 46);
            this.subTitleLabel.Name = "subTitleLabel";
            this.subTitleLabel.Size = new System.Drawing.Size(0, 15);
            this.subTitleLabel.TabIndex = 22;
            // 
            // facilityPictureBox
            // 
            this.facilityPictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.facilityPictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.facilityPictureBox.Location = new System.Drawing.Point(347, 22);
            this.facilityPictureBox.Name = "facilityPictureBox";
            this.facilityPictureBox.Size = new System.Drawing.Size(9, 10);
            this.facilityPictureBox.TabIndex = 20;
            this.facilityPictureBox.TabStop = false;
            // 
            // docTypePictureBox
            // 
            this.docTypePictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.docTypePictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.docTypePictureBox.Location = new System.Drawing.Point(10, 19);
            this.docTypePictureBox.Name = "docTypePictureBox";
            this.docTypePictureBox.Size = new System.Drawing.Size(9, 10);
            this.docTypePictureBox.TabIndex = 19;
            this.docTypePictureBox.TabStop = false;
            // 
            // controlPanel
            // 
            this.controlPanel.Controls.Add(this.deleteButton);
            this.controlPanel.Controls.Add(this.requiredImage);
            this.controlPanel.Controls.Add(this.requiredLabel);
            this.controlPanel.Controls.Add(this.cancelButton);
            this.controlPanel.Controls.Add(this.saveButton);
            this.controlPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.controlPanel.Location = new System.Drawing.Point(0, 225);
            this.controlPanel.Name = "controlPanel";
            this.controlPanel.Size = new System.Drawing.Size(661, 38);
            this.controlPanel.TabIndex = 11;
            // 
            // deleteButton
            // 
            this.deleteButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.deleteButton.Location = new System.Drawing.Point(389, 9);
            this.deleteButton.Name = "deleteButton";
            this.deleteButton.Size = new System.Drawing.Size(75, 23);
            this.deleteButton.TabIndex = 11;
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
            this.cancelButton.TabIndex = 10;
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
            this.saveButton.TabIndex = 9;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // commentTextBox
            // 
            this.commentTextBox.AcceptsReturn = true;
            this.commentTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.commentTextBox.Location = new System.Drawing.Point(10, 148);
            this.commentTextBox.MaxLength = 512;
            this.commentTextBox.Multiline = true;
            this.commentTextBox.Name = "commentTextBox";
            this.commentTextBox.Size = new System.Drawing.Size(616, 69);
            this.commentTextBox.TabIndex = 8;
            // 
            // commentLabel
            // 
            this.commentLabel.AutoSize = true;
            this.commentLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.commentLabel.Location = new System.Drawing.Point(7, 130);
            this.commentLabel.Name = "commentLabel";
            this.commentLabel.Size = new System.Drawing.Size(0, 15);
            this.commentLabel.TabIndex = 10;
            // 
            // departmentComboBox
            // 
            this.departmentComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.departmentComboBox.FormattingEnabled = true;
            this.departmentComboBox.Location = new System.Drawing.Point(459, 46);
            this.departmentComboBox.MaxLength = 256;
            this.departmentComboBox.Name = "departmentComboBox";
            this.departmentComboBox.Size = new System.Drawing.Size(181, 23);
            this.departmentComboBox.TabIndex = 6;
            // 
            // departmentLabel
            // 
            this.departmentLabel.AutoSize = true;
            this.departmentLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.departmentLabel.Location = new System.Drawing.Point(364, 50);
            this.departmentLabel.Name = "departmentLabel";
            this.departmentLabel.Size = new System.Drawing.Size(0, 15);
            this.departmentLabel.TabIndex = 8;
            // 
            // facilityComboBox
            // 
            this.facilityComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityComboBox.FormattingEnabled = true;
            this.facilityComboBox.Location = new System.Drawing.Point(459, 17);
            this.facilityComboBox.MaxLength = 256;
            this.facilityComboBox.Name = "facilityComboBox";
            this.facilityComboBox.Size = new System.Drawing.Size(181, 23);
            this.facilityComboBox.TabIndex = 5;
            this.facilityComboBox.SelectedIndexChanged += new System.EventHandler(this.facilityComboBox_SelectedIndexChanged);
            // 
            // facilityLabel
            // 
            this.facilityLabel.AutoSize = true;
            this.facilityLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityLabel.Location = new System.Drawing.Point(364, 22);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(0, 15);
            this.facilityLabel.TabIndex = 6;
            // 
            // dateOfServiceLabel
            // 
            this.dateOfServiceLabel.AutoSize = true;
            this.dateOfServiceLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dateOfServiceLabel.Location = new System.Drawing.Point(25, 73);
            this.dateOfServiceLabel.Name = "dateOfServiceLabel";
            this.dateOfServiceLabel.Size = new System.Drawing.Size(0, 15);
            this.dateOfServiceLabel.TabIndex = 4;
            // 
            // docTypeComboBox
            // 
            this.docTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.docTypeComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.docTypeComboBox.FormattingEnabled = true;
            this.docTypeComboBox.Location = new System.Drawing.Point(126, 17);
            this.docTypeComboBox.Name = "docTypeComboBox";
            this.docTypeComboBox.Size = new System.Drawing.Size(191, 23);
            this.docTypeComboBox.TabIndex = 1;
            // 
            // docTypeLabel
            // 
            this.docTypeLabel.AutoSize = true;
            this.docTypeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.docTypeLabel.Location = new System.Drawing.Point(25, 17);
            this.docTypeLabel.Name = "docTypeLabel";
            this.docTypeLabel.Size = new System.Drawing.Size(0, 15);
            this.docTypeLabel.TabIndex = 2;
            // 
            // encounterTextBox
            // 
            this.encounterTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.encounterTextBox.Location = new System.Drawing.Point(459, 75);
            this.encounterTextBox.MaxLength = 256;
            this.encounterTextBox.Name = "encounterTextBox";
            this.encounterTextBox.Size = new System.Drawing.Size(181, 21);
            this.encounterTextBox.TabIndex = 7;
            // 
            // encounterLabel
            // 
            this.encounterLabel.AutoSize = true;
            this.encounterLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.encounterLabel.Location = new System.Drawing.Point(364, 75);
            this.encounterLabel.Name = "encounterLabel";
            this.encounterLabel.Size = new System.Drawing.Size(0, 15);
            this.encounterLabel.TabIndex = 0;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // dosTextBox
            // 
            this.dosTextBox.FormattedDate = null;
            this.dosTextBox.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.dosTextBox.IsValidDate = false;
            this.dosTextBox.Location = new System.Drawing.Point(126, 72);
            this.dosTextBox.Mask = "AA/AA/AAAA";
            this.dosTextBox.Name = "dosTextBox";
            this.dosTextBox.PromptChar = ' ';
            this.dosTextBox.Size = new System.Drawing.Size(191, 20);
            this.dosTextBox.TabIndex = 3;
            // 
            // NonHpfDocumentDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.bottomPanel);
            this.Controls.Add(this.topPanel);
            this.Name = "NonHpfDocumentDialog";
            this.Padding = new System.Windows.Forms.Padding(5);
            this.Size = new System.Drawing.Size(671, 350);
            this.bottomPanel.ResumeLayout(false);
            this.bottomPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.datePictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pageCountPictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.facilityPictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.docTypePictureBox)).EndInit();
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
        private System.Windows.Forms.Label docTypeLabel;
        private System.Windows.Forms.TextBox commentTextBox;
        private System.Windows.Forms.Label commentLabel;
        private System.Windows.Forms.ComboBox departmentComboBox;
        private System.Windows.Forms.Label departmentLabel;
        private System.Windows.Forms.ComboBox facilityComboBox;
        private System.Windows.Forms.Label facilityLabel;
        private System.Windows.Forms.Label dateOfServiceLabel;
        private System.Windows.Forms.ComboBox docTypeComboBox;
        private System.Windows.Forms.Panel controlPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.Button deleteButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.PictureBox facilityPictureBox;
        private System.Windows.Forms.PictureBox docTypePictureBox;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.TextBox pageCountTextBox;
        private System.Windows.Forms.Label pageCountLabel;
        private System.Windows.Forms.TextBox subTitleTextBox;
        private System.Windows.Forms.Label subTitleLabel;
        private System.Windows.Forms.PictureBox pageCountPictureBox;
        private System.Windows.Forms.PictureBox datePictureBox;
        private McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl dosTextBox;
    }
}
