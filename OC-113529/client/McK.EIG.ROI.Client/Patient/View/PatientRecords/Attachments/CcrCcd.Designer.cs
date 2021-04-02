namespace McK.EIG.ROI.Client.Patient.View.PatientRecords.Attachments
{
    partial class CcrCcd
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
            this.openRawAttachmentDialog = new System.Windows.Forms.OpenFileDialog();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.openHRAttachmentDialog = new System.Windows.Forms.OpenFileDialog();
            this.attachInstrPictureBox = new System.Windows.Forms.PictureBox();
            this.ccrAttachInstructions = new System.Windows.Forms.Label();
            this.formattedGroupBox = new System.Windows.Forms.GroupBox();
            this.hrPageCountPictureBox = new System.Windows.Forms.PictureBox();
            this.hrPageCountTextBox = new System.Windows.Forms.TextBox();
            this.hrPageCountLabel = new System.Windows.Forms.Label();
            this.hrFileLabel = new System.Windows.Forms.Label();
            this.selectHrFileButton = new System.Windows.Forms.Button();
            this.hrAttachmentTextBox = new System.Windows.Forms.TextBox();
            this.submittedByTextBox = new System.Windows.Forms.TextBox();
            this.rawGroupBox = new System.Windows.Forms.GroupBox();
            this.rawPageCountPictureBox = new System.Windows.Forms.PictureBox();
            this.rawFileLabel = new System.Windows.Forms.Label();
            this.rawPageCountLabel = new System.Windows.Forms.Label();
            this.rawAttachmentTextBox = new System.Windows.Forms.TextBox();
            this.selectRawFileButton = new System.Windows.Forms.Button();
            this.rawPageCountTextBox = new System.Windows.Forms.TextBox();
            this.submittedByLabel = new System.Windows.Forms.Label();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.attachInstrPictureBox)).BeginInit();
            this.formattedGroupBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.hrPageCountPictureBox)).BeginInit();
            this.rawGroupBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.rawPageCountPictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // openRawAttachmentDialog
            // 
            this.openRawAttachmentDialog.CheckFileExists = false;
            // 
            // attachInstrPictureBox
            // 
            this.attachInstrPictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.attachInstrPictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.attachInstrPictureBox.Location = new System.Drawing.Point(1, 31);
            this.attachInstrPictureBox.Name = "attachInstrPictureBox";
            this.attachInstrPictureBox.Size = new System.Drawing.Size(9, 10);
            this.attachInstrPictureBox.TabIndex = 28;
            this.attachInstrPictureBox.TabStop = false;
            // 
            // ccrAttachInstructions
            // 
            this.ccrAttachInstructions.AutoSize = true;
            this.ccrAttachInstructions.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ccrAttachInstructions.Location = new System.Drawing.Point(16, 31);
            this.ccrAttachInstructions.Name = "ccrAttachInstructions";
            this.ccrAttachInstructions.Size = new System.Drawing.Size(0, 13);
            this.ccrAttachInstructions.TabIndex = 8;
            // 
            // formattedGroupBox
            // 
            this.formattedGroupBox.Controls.Add(this.hrPageCountPictureBox);
            this.formattedGroupBox.Controls.Add(this.hrPageCountTextBox);
            this.formattedGroupBox.Controls.Add(this.hrPageCountLabel);
            this.formattedGroupBox.Controls.Add(this.hrFileLabel);
            this.formattedGroupBox.Controls.Add(this.selectHrFileButton);
            this.formattedGroupBox.Controls.Add(this.hrAttachmentTextBox);
            this.formattedGroupBox.Location = new System.Drawing.Point(310, 47);
            this.formattedGroupBox.Name = "formattedGroupBox";
            this.formattedGroupBox.Size = new System.Drawing.Size(305, 98);
            this.formattedGroupBox.TabIndex = 3;
            this.formattedGroupBox.TabStop = false;
            this.formattedGroupBox.Text = "Formatted(PDF,DOC,etc)";
            // 
            // hrPageCountPictureBox
            // 
            this.hrPageCountPictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.hrPageCountPictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.hrPageCountPictureBox.Location = new System.Drawing.Point(5, 54);
            this.hrPageCountPictureBox.Name = "hrPageCountPictureBox";
            this.hrPageCountPictureBox.Size = new System.Drawing.Size(9, 10);
            this.hrPageCountPictureBox.TabIndex = 27;
            this.hrPageCountPictureBox.TabStop = false;
            this.hrPageCountPictureBox.Visible = false;
            // 
            // hrPageCountTextBox
            // 
            this.hrPageCountTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.hrPageCountTextBox.Location = new System.Drawing.Point(105, 51);
            this.hrPageCountTextBox.MaxLength = 5;
            this.hrPageCountTextBox.Name = "hrPageCountTextBox";
            this.hrPageCountTextBox.Size = new System.Drawing.Size(115, 21);
            this.hrPageCountTextBox.TabIndex = 3;
            this.hrPageCountTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.hrPageCountTextBox_KeyPress);
            // 
            // hrPageCountLabel
            // 
            this.hrPageCountLabel.AutoSize = true;
            this.hrPageCountLabel.BackColor = System.Drawing.Color.Transparent;
            this.hrPageCountLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.hrPageCountLabel.Location = new System.Drawing.Point(20, 54);
            this.hrPageCountLabel.Name = "hrPageCountLabel";
            this.hrPageCountLabel.Size = new System.Drawing.Size(0, 13);
            this.hrPageCountLabel.TabIndex = 7;
            // 
            // hrFileLabel
            // 
            this.hrFileLabel.AutoSize = true;
            this.hrFileLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.hrFileLabel.Location = new System.Drawing.Point(20, 29);
            this.hrFileLabel.Name = "hrFileLabel";
            this.hrFileLabel.Size = new System.Drawing.Size(0, 13);
            this.hrFileLabel.TabIndex = 0;
            // 
            // selectHrFileButton
            // 
            this.selectHrFileButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.selectHrFileButton.Location = new System.Drawing.Point(226, 25);
            this.selectHrFileButton.Name = "selectHrFileButton";
            this.selectHrFileButton.Size = new System.Drawing.Size(75, 23);
            this.selectHrFileButton.TabIndex = 2;
            this.selectHrFileButton.UseVisualStyleBackColor = true;
            this.selectHrFileButton.Click += new System.EventHandler(this.selectHrFileButton_Click);
            // 
            // hrAttachmentTextBox
            // 
            this.hrAttachmentTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.hrAttachmentTextBox.Location = new System.Drawing.Point(105, 27);
            this.hrAttachmentTextBox.MaxLength = 256;
            this.hrAttachmentTextBox.Name = "hrAttachmentTextBox";
            this.hrAttachmentTextBox.Size = new System.Drawing.Size(115, 21);
            this.hrAttachmentTextBox.TabIndex = 1;
            this.hrAttachmentTextBox.TextChanged += new System.EventHandler(this.hrAttachmentTextBox_TextChanged);
            // 
            // submittedByTextBox
            // 
            this.submittedByTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.submittedByTextBox.Location = new System.Drawing.Point(138, 1);
            this.submittedByTextBox.MaxLength = 256;
            this.submittedByTextBox.Name = "submittedByTextBox";
            this.submittedByTextBox.Size = new System.Drawing.Size(191, 21);
            this.submittedByTextBox.TabIndex = 1;
            // 
            // rawGroupBox
            // 
            this.rawGroupBox.Controls.Add(this.rawPageCountPictureBox);
            this.rawGroupBox.Controls.Add(this.rawFileLabel);
            this.rawGroupBox.Controls.Add(this.rawPageCountLabel);
            this.rawGroupBox.Controls.Add(this.rawAttachmentTextBox);
            this.rawGroupBox.Controls.Add(this.selectRawFileButton);
            this.rawGroupBox.Controls.Add(this.rawPageCountTextBox);
            this.rawGroupBox.Location = new System.Drawing.Point(0, 47);
            this.rawGroupBox.Name = "rawGroupBox";
            this.rawGroupBox.Size = new System.Drawing.Size(305, 98);
            this.rawGroupBox.TabIndex = 2;
            this.rawGroupBox.TabStop = false;
            // 
            // rawPageCountPictureBox
            // 
            this.rawPageCountPictureBox.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.rawPageCountPictureBox.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.rawPageCountPictureBox.Location = new System.Drawing.Point(5, 54);
            this.rawPageCountPictureBox.Name = "rawPageCountPictureBox";
            this.rawPageCountPictureBox.Size = new System.Drawing.Size(9, 10);
            this.rawPageCountPictureBox.TabIndex = 27;
            this.rawPageCountPictureBox.TabStop = false;
            this.rawPageCountPictureBox.Visible = false;
            // 
            // rawFileLabel
            // 
            this.rawFileLabel.AutoSize = true;
            this.rawFileLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rawFileLabel.Location = new System.Drawing.Point(20, 29);
            this.rawFileLabel.Name = "rawFileLabel";
            this.rawFileLabel.Size = new System.Drawing.Size(0, 13);
            this.rawFileLabel.TabIndex = 1;
            // 
            // rawPageCountLabel
            // 
            this.rawPageCountLabel.AutoSize = true;
            this.rawPageCountLabel.BackColor = System.Drawing.Color.Transparent;
            this.rawPageCountLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rawPageCountLabel.Location = new System.Drawing.Point(20, 54);
            this.rawPageCountLabel.Name = "rawPageCountLabel";
            this.rawPageCountLabel.Size = new System.Drawing.Size(0, 13);
            this.rawPageCountLabel.TabIndex = 3;
            // 
            // rawAttachmentTextBox
            // 
            this.rawAttachmentTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rawAttachmentTextBox.Location = new System.Drawing.Point(105, 27);
            this.rawAttachmentTextBox.MaxLength = 256;
            this.rawAttachmentTextBox.Name = "rawAttachmentTextBox";
            this.rawAttachmentTextBox.Size = new System.Drawing.Size(115, 21);
            this.rawAttachmentTextBox.TabIndex = 1;
            this.rawAttachmentTextBox.TextChanged += new System.EventHandler(this.rawAttachmentTextBox_TextChanged);
            // 
            // selectRawFileButton
            // 
            this.selectRawFileButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.selectRawFileButton.Location = new System.Drawing.Point(226, 25);
            this.selectRawFileButton.Name = "selectRawFileButton";
            this.selectRawFileButton.Size = new System.Drawing.Size(75, 23);
            this.selectRawFileButton.TabIndex = 2;
            this.selectRawFileButton.UseVisualStyleBackColor = true;
            this.selectRawFileButton.Click += new System.EventHandler(this.selectFileButton_Click);
            // 
            // rawPageCountTextBox
            // 
            this.rawPageCountTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rawPageCountTextBox.Location = new System.Drawing.Point(105, 51);
            this.rawPageCountTextBox.MaxLength = 5;
            this.rawPageCountTextBox.Name = "rawPageCountTextBox";
            this.rawPageCountTextBox.Size = new System.Drawing.Size(115, 21);
            this.rawPageCountTextBox.TabIndex = 3;
            this.rawPageCountTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.rawPageCountTextBox_KeyPress);
            // 
            // submittedByLabel
            // 
            this.submittedByLabel.AutoSize = true;
            this.submittedByLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.submittedByLabel.Location = new System.Drawing.Point(16, 4);
            this.submittedByLabel.Name = "submittedByLabel";
            this.submittedByLabel.Size = new System.Drawing.Size(0, 15);
            this.submittedByLabel.TabIndex = 7;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // CcrCcd
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.attachInstrPictureBox);
            this.Controls.Add(this.ccrAttachInstructions);
            this.Controls.Add(this.formattedGroupBox);
            this.Controls.Add(this.submittedByTextBox);
            this.Controls.Add(this.rawGroupBox);
            this.Controls.Add(this.submittedByLabel);
            this.Name = "CcrCcd";
            this.Size = new System.Drawing.Size(616, 150);
            ((System.ComponentModel.ISupportInitialize)(this.attachInstrPictureBox)).EndInit();
            this.formattedGroupBox.ResumeLayout(false);
            this.formattedGroupBox.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.hrPageCountPictureBox)).EndInit();
            this.rawGroupBox.ResumeLayout(false);
            this.rawGroupBox.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.rawPageCountPictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.OpenFileDialog openRawAttachmentDialog;
        private System.Windows.Forms.TextBox submittedByTextBox;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.OpenFileDialog openHRAttachmentDialog;
        private System.Windows.Forms.Button selectHrFileButton;
        private System.Windows.Forms.TextBox hrAttachmentTextBox;
        private System.Windows.Forms.Label hrFileLabel;
        private System.Windows.Forms.GroupBox formattedGroupBox;
        private System.Windows.Forms.GroupBox rawGroupBox;
        private System.Windows.Forms.Label rawFileLabel;
        private System.Windows.Forms.Label rawPageCountLabel;
        private System.Windows.Forms.TextBox rawAttachmentTextBox;
        private System.Windows.Forms.Label submittedByLabel;
        private System.Windows.Forms.Button selectRawFileButton;
        private System.Windows.Forms.TextBox rawPageCountTextBox;
        private System.Windows.Forms.TextBox hrPageCountTextBox;
        private System.Windows.Forms.Label hrPageCountLabel;
        private System.Windows.Forms.PictureBox hrPageCountPictureBox;
        private System.Windows.Forms.PictureBox rawPageCountPictureBox;
        private System.Windows.Forms.Label ccrAttachInstructions;
        private System.Windows.Forms.PictureBox attachInstrPictureBox;
    }
}
