namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    partial class PersonalInfoNonHpfUI
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
            this.vipCheckBox = new System.Windows.Forms.CheckBox();
            this.firstNameLabel = new System.Windows.Forms.Label();
            this.genderLabel = new System.Windows.Forms.Label();
            this.dobLabel = new System.Windows.Forms.Label();
            this.ssnLabel = new System.Windows.Forms.Label();
            this.firstPatientNameTextBox = new System.Windows.Forms.TextBox();
            this.genderComboBox = new System.Windows.Forms.ComboBox();
            this.img1 = new System.Windows.Forms.PictureBox();
            this.nameNonUniqueIcon = new System.Windows.Forms.PictureBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.epnEnablePanel = new System.Windows.Forms.FlowLayoutPanel();
            this.epnPanel = new System.Windows.Forms.Panel();
            this.epnLabel = new System.Windows.Forms.Label();
            this.epnTextBox = new System.Windows.Forms.TextBox();
            this.facilityPanel = new System.Windows.Forms.Panel();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.facilityComboBox = new System.Windows.Forms.ComboBox();
            this.mrnPanel = new System.Windows.Forms.Panel();
            this.mrnLabel = new System.Windows.Forms.Label();
            this.mrnTextBox = new System.Windows.Forms.TextBox();
            this.ssnTextBox = new System.Windows.Forms.MaskedTextBox();
            this.img2 = new System.Windows.Forms.PictureBox();
            this.lastPatientNameTextBox = new System.Windows.Forms.TextBox();
            this.lastNameLabel = new System.Windows.Forms.Label();
            this.dobTextBox = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nameNonUniqueIcon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.epnEnablePanel.SuspendLayout();
            this.epnPanel.SuspendLayout();
            this.facilityPanel.SuspendLayout();
            this.mrnPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img2)).BeginInit();
            this.SuspendLayout();
            // 
            // vipCheckBox
            // 
            this.vipCheckBox.AutoSize = true;
            this.vipCheckBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.vipCheckBox.Location = new System.Drawing.Point(112, 0);
            this.vipCheckBox.Name = "vipCheckBox";
            this.vipCheckBox.Size = new System.Drawing.Size(15, 14);
            this.vipCheckBox.TabIndex = 7;
            this.vipCheckBox.UseVisualStyleBackColor = true;
            // 
            // firstNameLabel
            // 
            this.firstNameLabel.AutoSize = true;
            this.firstNameLabel.Location = new System.Drawing.Point(17, 46);
            this.firstNameLabel.Name = "firstNameLabel";
            this.firstNameLabel.Size = new System.Drawing.Size(0, 13);
            this.firstNameLabel.TabIndex = 1;
            // 
            // genderLabel
            // 
            this.genderLabel.AutoSize = true;
            this.genderLabel.Location = new System.Drawing.Point(17, 72);
            this.genderLabel.Name = "genderLabel";
            this.genderLabel.Size = new System.Drawing.Size(0, 13);
            this.genderLabel.TabIndex = 2;
            // 
            // dobLabel
            // 
            this.dobLabel.AutoSize = true;
            this.dobLabel.Location = new System.Drawing.Point(17, 100);
            this.dobLabel.Name = "dobLabel";
            this.dobLabel.Size = new System.Drawing.Size(0, 13);
            this.dobLabel.TabIndex = 3;
            // 
            // ssnLabel
            // 
            this.ssnLabel.AutoSize = true;
            this.ssnLabel.Location = new System.Drawing.Point(17, 123);
            this.ssnLabel.Name = "ssnLabel";
            this.ssnLabel.Size = new System.Drawing.Size(0, 13);
            this.ssnLabel.TabIndex = 4;
            // 
            // firstPatientNameTextBox
            // 
            this.firstPatientNameTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.firstPatientNameTextBox.Location = new System.Drawing.Point(112, 44);
            this.firstPatientNameTextBox.MaxLength = 256;
            this.firstPatientNameTextBox.Name = "firstPatientNameTextBox";
            this.firstPatientNameTextBox.Size = new System.Drawing.Size(150, 21);
            this.firstPatientNameTextBox.TabIndex = 1;
            // 
            // genderComboBox
            // 
            this.genderComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.genderComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.genderComboBox.FormattingEnabled = true;
            this.genderComboBox.Location = new System.Drawing.Point(112, 70);
            this.genderComboBox.Name = "genderComboBox";
            this.genderComboBox.Size = new System.Drawing.Size(96, 23);
            this.genderComboBox.TabIndex = 2;
            // 
            // img1
            // 
            this.img1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img1.Location = new System.Drawing.Point(4, 49);
            this.img1.Name = "img1";
            this.img1.Size = new System.Drawing.Size(9, 10);
            this.img1.TabIndex = 14;
            this.img1.TabStop = false;
            // 
            // nameNonUniqueIcon
            // 
            this.nameNonUniqueIcon.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.non_unique_patient;
            this.nameNonUniqueIcon.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.nameNonUniqueIcon.Location = new System.Drawing.Point(266, 18);
            this.nameNonUniqueIcon.Name = "nameNonUniqueIcon";
            this.nameNonUniqueIcon.Size = new System.Drawing.Size(17, 18);
            this.nameNonUniqueIcon.TabIndex = 16;
            this.nameNonUniqueIcon.TabStop = false;
            this.nameNonUniqueIcon.Visible = false;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // epnEnablePanel
            // 
            this.epnEnablePanel.Controls.Add(this.epnPanel);
            this.epnEnablePanel.Controls.Add(this.facilityPanel);
            this.epnEnablePanel.Controls.Add(this.mrnPanel);
            this.epnEnablePanel.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.epnEnablePanel.Location = new System.Drawing.Point(365, 40);
            this.epnEnablePanel.Name = "epnEnablePanel";
            this.epnEnablePanel.Size = new System.Drawing.Size(296, 101);
            this.epnEnablePanel.TabIndex = 17;
            // 
            // epnPanel
            // 
            this.epnPanel.Controls.Add(this.epnLabel);
            this.epnPanel.Controls.Add(this.epnTextBox);
            this.epnPanel.Location = new System.Drawing.Point(0, 0);
            this.epnPanel.Margin = new System.Windows.Forms.Padding(0);
            this.epnPanel.Name = "epnPanel";
            this.epnPanel.Size = new System.Drawing.Size(293, 26);
            this.epnPanel.TabIndex = 51;
            // 
            // epnLabel
            // 
            this.epnLabel.AutoSize = true;
            this.epnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnLabel.Location = new System.Drawing.Point(15, 6);
            this.epnLabel.Name = "epnLabel";
            this.epnLabel.Size = new System.Drawing.Size(0, 15);
            this.epnLabel.TabIndex = 46;
            // 
            // epnTextBox
            // 
            this.epnTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnTextBox.Location = new System.Drawing.Point(96, 3);
            this.epnTextBox.Name = "epnTextBox";
            this.epnTextBox.Size = new System.Drawing.Size(150, 21);
            this.epnTextBox.TabIndex = 5;
            // 
            // facilityPanel
            // 
            this.facilityPanel.Controls.Add(this.facilityLabel);
            this.facilityPanel.Controls.Add(this.facilityComboBox);
            this.facilityPanel.Location = new System.Drawing.Point(0, 26);
            this.facilityPanel.Margin = new System.Windows.Forms.Padding(0);
            this.facilityPanel.Name = "facilityPanel";
            this.facilityPanel.Size = new System.Drawing.Size(293, 30);
            this.facilityPanel.TabIndex = 52;
            // 
            // facilityLabel
            // 
            this.facilityLabel.AutoSize = true;
            this.facilityLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityLabel.Location = new System.Drawing.Point(15, 7);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(0, 15);
            this.facilityLabel.TabIndex = 50;
            // 
            // facilityComboBox
            // 
            this.facilityComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityComboBox.FormattingEnabled = true;
            this.facilityComboBox.ItemHeight = 15;
            this.facilityComboBox.Location = new System.Drawing.Point(96, 4);
            this.facilityComboBox.MaxLength = 256;
            this.facilityComboBox.Name = "facilityComboBox";
            this.facilityComboBox.Size = new System.Drawing.Size(150, 23);
            this.facilityComboBox.TabIndex = 6;
            // 
            // mrnPanel
            // 
            this.mrnPanel.Controls.Add(this.mrnLabel);
            this.mrnPanel.Controls.Add(this.mrnTextBox);
            this.mrnPanel.Location = new System.Drawing.Point(0, 56);
            this.mrnPanel.Margin = new System.Windows.Forms.Padding(0);
            this.mrnPanel.Name = "mrnPanel";
            this.mrnPanel.Size = new System.Drawing.Size(293, 27);
            this.mrnPanel.TabIndex = 53;
            // 
            // mrnLabel
            // 
            this.mrnLabel.AutoSize = true;
            this.mrnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnLabel.Location = new System.Drawing.Point(15, 6);
            this.mrnLabel.Name = "mrnLabel";
            this.mrnLabel.Size = new System.Drawing.Size(0, 15);
            this.mrnLabel.TabIndex = 46;
            // 
            // mrnTextBox
            // 
            this.mrnTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnTextBox.Location = new System.Drawing.Point(96, 3);
            this.mrnTextBox.MaxLength = 256;
            this.mrnTextBox.Name = "mrnTextBox";
            this.mrnTextBox.Size = new System.Drawing.Size(150, 21);
            this.mrnTextBox.TabIndex = 7;
            // 
            // ssnTextBox
            // 
            this.ssnTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ssnTextBox.Location = new System.Drawing.Point(112, 124);
            this.ssnTextBox.Name = "ssnTextBox";
            this.ssnTextBox.Size = new System.Drawing.Size(150, 21);
            this.ssnTextBox.TabIndex = 4;
            // 
            // img2
            // 
            this.img2.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img2.Location = new System.Drawing.Point(4, 22);
            this.img2.Name = "img2";
            this.img2.Size = new System.Drawing.Size(9, 10);
            this.img2.TabIndex = 20;
            this.img2.TabStop = false;
            // 
            // lastPatientNameTextBox
            // 
            this.lastPatientNameTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lastPatientNameTextBox.Location = new System.Drawing.Point(112, 17);
            this.lastPatientNameTextBox.MaxLength = 256;
            this.lastPatientNameTextBox.Name = "lastPatientNameTextBox";
            this.lastPatientNameTextBox.Size = new System.Drawing.Size(150, 21);
            this.lastPatientNameTextBox.TabIndex = 0;
            // 
            // lastNameLabel
            // 
            this.lastNameLabel.AutoSize = true;
            this.lastNameLabel.Location = new System.Drawing.Point(17, 19);
            this.lastNameLabel.Name = "lastNameLabel";
            this.lastNameLabel.Size = new System.Drawing.Size(0, 13);
            this.lastNameLabel.TabIndex = 19;
            // 
            // dobTextBox
            // 
            this.dobTextBox.FormattedDate = null;
            this.dobTextBox.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.dobTextBox.IsValidDate = false;
            this.dobTextBox.Location = new System.Drawing.Point(112, 96);
            this.dobTextBox.Mask = "AA/AA/AAAA";
            this.dobTextBox.Name = "dobTextBox";
            this.dobTextBox.PromptChar = ' ';
            this.dobTextBox.Size = new System.Drawing.Size(150, 20);
            this.dobTextBox.TabIndex = 3;
            // 
            // PersonalInfoNonHpfUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.dobTextBox);
            this.Controls.Add(this.img2);
            this.Controls.Add(this.lastPatientNameTextBox);
            this.Controls.Add(this.lastNameLabel);
            this.Controls.Add(this.ssnTextBox);
            this.Controls.Add(this.epnEnablePanel);
            this.Controls.Add(this.nameNonUniqueIcon);
            this.Controls.Add(this.img1);
            this.Controls.Add(this.genderComboBox);
            this.Controls.Add(this.firstPatientNameTextBox);
            this.Controls.Add(this.ssnLabel);
            this.Controls.Add(this.dobLabel);
            this.Controls.Add(this.genderLabel);
            this.Controls.Add(this.firstNameLabel);
            this.Controls.Add(this.vipCheckBox);
            this.Name = "PersonalInfoNonHpfUI";
            this.Size = new System.Drawing.Size(682, 153);
            ((System.ComponentModel.ISupportInitialize)(this.img1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nameNonUniqueIcon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.epnEnablePanel.ResumeLayout(false);
            this.epnPanel.ResumeLayout(false);
            this.epnPanel.PerformLayout();
            this.facilityPanel.ResumeLayout(false);
            this.facilityPanel.PerformLayout();
            this.mrnPanel.ResumeLayout(false);
            this.mrnPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.CheckBox vipCheckBox;
        private System.Windows.Forms.Label firstNameLabel;
        private System.Windows.Forms.Label genderLabel;
        private System.Windows.Forms.Label dobLabel;
        private System.Windows.Forms.Label ssnLabel;
        private System.Windows.Forms.TextBox firstPatientNameTextBox;
        private System.Windows.Forms.ComboBox genderComboBox;
        private System.Windows.Forms.PictureBox img1;
        private System.Windows.Forms.PictureBox nameNonUniqueIcon;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.FlowLayoutPanel epnEnablePanel;
        private System.Windows.Forms.Panel epnPanel;
        private System.Windows.Forms.Label epnLabel;
        private System.Windows.Forms.TextBox epnTextBox;
        private System.Windows.Forms.Panel facilityPanel;
        private System.Windows.Forms.Label facilityLabel;
        private System.Windows.Forms.ComboBox facilityComboBox;
        private System.Windows.Forms.Panel mrnPanel;
        private System.Windows.Forms.Label mrnLabel;
        private System.Windows.Forms.TextBox mrnTextBox;
        private System.Windows.Forms.MaskedTextBox ssnTextBox;
        private System.Windows.Forms.PictureBox img2;
        private System.Windows.Forms.TextBox lastPatientNameTextBox;
        private System.Windows.Forms.Label lastNameLabel;
        private McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl dobTextBox;
    }
}
