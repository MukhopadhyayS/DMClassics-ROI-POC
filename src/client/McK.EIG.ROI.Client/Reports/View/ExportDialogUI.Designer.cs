namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ExportDialogUI
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
            this.exportTypeCombo = new System.Windows.Forms.ComboBox();
            this.formatLabel = new System.Windows.Forms.Label();
            this.fileNameTextBox = new System.Windows.Forms.TextBox();
            this.fileNameLabel = new System.Windows.Forms.Label();
            this.saveLabel = new System.Windows.Forms.Label();
            this.driveListBox = new Microsoft.VisualBasic.Compatibility.VB6.DriveListBox();
            this.dirListBox = new Microsoft.VisualBasic.Compatibility.VB6.DirListBox();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // exportTypeCombo
            // 
            this.exportTypeCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.exportTypeCombo.FormattingEnabled = true;
            this.exportTypeCombo.Location = new System.Drawing.Point(99, 77);
            this.exportTypeCombo.Name = "exportTypeCombo";
            this.exportTypeCombo.Size = new System.Drawing.Size(203, 23);
            this.exportTypeCombo.TabIndex = 7;
            // 
            // formatLabel
            // 
            this.formatLabel.AutoSize = true;
            this.formatLabel.Location = new System.Drawing.Point(10, 80);
            this.formatLabel.Name = "formatLabel";
            this.formatLabel.Size = new System.Drawing.Size(0, 15);
            this.formatLabel.TabIndex = 6;
            // 
            // fileNameTextBox
            // 
            this.fileNameTextBox.Location = new System.Drawing.Point(99, 29);
            this.fileNameTextBox.Name = "fileNameTextBox";
            this.fileNameTextBox.Size = new System.Drawing.Size(203, 21);
            this.fileNameTextBox.TabIndex = 5;
            // 
            // fileNameLabel
            // 
            this.fileNameLabel.AutoSize = true;
            this.fileNameLabel.Location = new System.Drawing.Point(10, 32);
            this.fileNameLabel.Name = "fileNameLabel";
            this.fileNameLabel.Size = new System.Drawing.Size(0, 15);
            this.fileNameLabel.TabIndex = 4;
            // 
            // saveLabel
            // 
            this.saveLabel.AutoSize = true;
            this.saveLabel.Location = new System.Drawing.Point(10, 131);
            this.saveLabel.Name = "saveLabel";
            this.saveLabel.Size = new System.Drawing.Size(0, 15);
            this.saveLabel.TabIndex = 9;
            // 
            // driveListBox
            // 
            this.driveListBox.FormattingEnabled = true;
            this.driveListBox.Location = new System.Drawing.Point(99, 124);
            this.driveListBox.Name = "driveListBox";
            this.driveListBox.Size = new System.Drawing.Size(203, 22);
            this.driveListBox.TabIndex = 8;
            this.driveListBox.SelectedIndexChanged += new System.EventHandler(this.driveListBox_SelectedIndexChanged);
            // 
            // dirListBox
            // 
            this.dirListBox.FormattingEnabled = true;
            this.dirListBox.IntegralHeight = false;
            this.dirListBox.Location = new System.Drawing.Point(13, 164);
            this.dirListBox.Name = "dirListBox";
            this.dirListBox.Size = new System.Drawing.Size(289, 134);
            this.dirListBox.TabIndex = 10;
            this.dirListBox.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.dirListBox_MouseDoubleClick);
            this.dirListBox.SelectedValueChanged += new System.EventHandler(this.dirListBox_SelectedValueChanged);
            // 
            // cancelButton
            // 
            this.cancelButton.Location = new System.Drawing.Point(163, 317);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(95, 25);
            this.cancelButton.TabIndex = 12;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // saveButton
            // 
            this.saveButton.Location = new System.Drawing.Point(62, 317);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(95, 25);
            this.saveButton.TabIndex = 11;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // ExportDialogUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.saveButton);
            this.Controls.Add(this.dirListBox);
            this.Controls.Add(this.saveLabel);
            this.Controls.Add(this.driveListBox);
            this.Controls.Add(this.exportTypeCombo);
            this.Controls.Add(this.formatLabel);
            this.Controls.Add(this.fileNameTextBox);
            this.Controls.Add(this.fileNameLabel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ExportDialogUI";
            this.Size = new System.Drawing.Size(325, 346);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox exportTypeCombo;
        private System.Windows.Forms.Label formatLabel;
        private System.Windows.Forms.TextBox fileNameTextBox;
        private System.Windows.Forms.Label fileNameLabel;
        private System.Windows.Forms.Label saveLabel;
        private Microsoft.VisualBasic.Compatibility.VB6.DriveListBox driveListBox;
        private Microsoft.VisualBasic.Compatibility.VB6.DirListBox dirListBox;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.ErrorProvider errorProvider;

    }
}
