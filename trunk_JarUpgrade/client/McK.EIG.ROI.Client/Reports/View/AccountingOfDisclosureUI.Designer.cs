namespace McK.EIG.ROI.Client.Reports.View
{
    partial class AccountingOfDisclosureUI
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AccountingOfDisclosureUI));
            this.topPanel = new System.Windows.Forms.Panel();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.reportCriteriaLabel = new System.Windows.Forms.Label();
            this.searchCriteriaPanel = new System.Windows.Forms.Panel();
            this.reportDateRangeUI = new McK.EIG.ROI.Client.Reports.View.ReportDateRangeUI();
            this.patientPanel = new System.Windows.Forms.Panel();
            this.requestTypeLabel = new System.Windows.Forms.Label();
            this.patientInfoPanel = new System.Windows.Forms.Panel();
            this.mrnPanel = new System.Windows.Forms.Panel();
            this.patientMRNLabel = new System.Windows.Forms.Label();
            this.mrnLabel = new System.Windows.Forms.Label();
            this.patientEPNLabel = new System.Windows.Forms.Label();
            this.epnLabel = new System.Windows.Forms.Label();
            this.patientFacilityLabel = new System.Windows.Forms.Label();
            this.patientSSNLabel = new System.Windows.Forms.Label();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.patientGenderLabel = new System.Windows.Forms.Label();
            this.patientDOBLabel = new System.Windows.Forms.Label();
            this.patientNameLabel = new System.Windows.Forms.Label();
            this.ssnLabel = new System.Windows.Forms.Label();
            this.genderLabel = new System.Windows.Forms.Label();
            this.dobLabel = new System.Windows.Forms.Label();
            this.nameLabel = new System.Windows.Forms.Label();
            this.patientInfoLabel = new System.Windows.Forms.Label();
            this.selectPatientButton = new System.Windows.Forms.Button();
            this.findPatientLabel = new System.Windows.Forms.Label();
            this.requestTypeCombo = new System.Windows.Forms.ComboBox();
            this.statustoStatusLabel = new System.Windows.Forms.Label();
            this.topPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            this.searchCriteriaPanel.SuspendLayout();
            this.patientPanel.SuspendLayout();
            this.patientInfoPanel.SuspendLayout();
            this.mrnPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Controls.Add(this.requiredImage);
            this.topPanel.Controls.Add(this.reportCriteriaLabel);
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.topPanel.Location = new System.Drawing.Point(0, 0);
            this.topPanel.Margin = new System.Windows.Forms.Padding(4);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(332, 29);
            this.topPanel.TabIndex = 0;
            // 
            // requiredImage
            // 
            this.requiredImage.Image = ((System.Drawing.Image)(resources.GetObject("requiredImage.Image")));
            this.requiredImage.Location = new System.Drawing.Point(4, 7);
            this.requiredImage.Margin = new System.Windows.Forms.Padding(4);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(14, 14);
            this.requiredImage.TabIndex = 8;
            this.requiredImage.TabStop = false;
            // 
            // reportCriteriaLabel
            // 
            this.reportCriteriaLabel.AutoSize = true;
            this.reportCriteriaLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportCriteriaLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(77)))), ((int)(((byte)(111)))), ((int)(((byte)(161)))));
            this.reportCriteriaLabel.Location = new System.Drawing.Point(18, 7);
            this.reportCriteriaLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.reportCriteriaLabel.Name = "reportCriteriaLabel";
            this.reportCriteriaLabel.Size = new System.Drawing.Size(0, 15);
            this.reportCriteriaLabel.TabIndex = 3;
            // 
            // searchCriteriaPanel
            // 
            this.searchCriteriaPanel.Controls.Add(this.reportDateRangeUI);
            this.searchCriteriaPanel.Controls.Add(this.patientPanel);
            this.searchCriteriaPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.searchCriteriaPanel.Location = new System.Drawing.Point(0, 29);
            this.searchCriteriaPanel.Margin = new System.Windows.Forms.Padding(4);
            this.searchCriteriaPanel.Name = "searchCriteriaPanel";
            this.searchCriteriaPanel.Size = new System.Drawing.Size(332, 552);
            this.searchCriteriaPanel.TabIndex = 11;
            // 
            // reportDateRangeUI
            // 
            this.reportDateRangeUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.reportDateRangeUI.Dock = System.Windows.Forms.DockStyle.Top;
            this.reportDateRangeUI.Enabled = false;
            this.reportDateRangeUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportDateRangeUI.IsValidDateRange = false;
            this.reportDateRangeUI.Location = new System.Drawing.Point(0, 288);
            this.reportDateRangeUI.Margin = new System.Windows.Forms.Padding(4);
            this.reportDateRangeUI.Name = "reportDateRangeUI";
            this.reportDateRangeUI.Size = new System.Drawing.Size(332, 127);
            this.reportDateRangeUI.TabIndex = 5;
            // 
            // patientPanel
            // 
            this.patientPanel.Controls.Add(this.requestTypeLabel);
            this.patientPanel.Controls.Add(this.patientInfoPanel);
            this.patientPanel.Controls.Add(this.patientInfoLabel);
            this.patientPanel.Controls.Add(this.selectPatientButton);
            this.patientPanel.Controls.Add(this.findPatientLabel);
            this.patientPanel.Controls.Add(this.requestTypeCombo);
            this.patientPanel.Controls.Add(this.statustoStatusLabel);
            this.patientPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.patientPanel.Location = new System.Drawing.Point(0, 0);
            this.patientPanel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 11);
            this.patientPanel.Name = "patientPanel";
            this.patientPanel.Padding = new System.Windows.Forms.Padding(0, 0, 0, 7);
            this.patientPanel.Size = new System.Drawing.Size(332, 288);
            this.patientPanel.TabIndex = 4;
            // 
            // requestTypeLabel
            // 
            this.requestTypeLabel.AutoSize = true;
            this.requestTypeLabel.Location = new System.Drawing.Point(1, 242);
            this.requestTypeLabel.Name = "requestTypeLabel";
            this.requestTypeLabel.Size = new System.Drawing.Size(0, 15);
            this.requestTypeLabel.TabIndex = 6;
            // 
            // patientInfoPanel
            // 
            this.patientInfoPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.patientInfoPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.patientInfoPanel.Controls.Add(this.mrnPanel);
            this.patientInfoPanel.Controls.Add(this.patientFacilityLabel);
            this.patientInfoPanel.Controls.Add(this.patientSSNLabel);
            this.patientInfoPanel.Controls.Add(this.facilityLabel);
            this.patientInfoPanel.Controls.Add(this.patientGenderLabel);
            this.patientInfoPanel.Controls.Add(this.patientDOBLabel);
            this.patientInfoPanel.Controls.Add(this.patientNameLabel);
            this.patientInfoPanel.Controls.Add(this.ssnLabel);
            this.patientInfoPanel.Controls.Add(this.genderLabel);
            this.patientInfoPanel.Controls.Add(this.dobLabel);
            this.patientInfoPanel.Controls.Add(this.nameLabel);
            this.patientInfoPanel.Location = new System.Drawing.Point(0, 78);
            this.patientInfoPanel.Margin = new System.Windows.Forms.Padding(4);
            this.patientInfoPanel.Name = "patientInfoPanel";
            this.patientInfoPanel.Size = new System.Drawing.Size(329, 162);
            this.patientInfoPanel.TabIndex = 2;
            // 
            // mrnPanel
            // 
            this.mrnPanel.Controls.Add(this.patientMRNLabel);
            this.mrnPanel.Controls.Add(this.mrnLabel);
            this.mrnPanel.Controls.Add(this.patientEPNLabel);
            this.mrnPanel.Controls.Add(this.epnLabel);
            this.mrnPanel.Location = new System.Drawing.Point(7, 113);
            this.mrnPanel.Name = "mrnPanel";
            this.mrnPanel.Size = new System.Drawing.Size(220, 48);
            this.mrnPanel.TabIndex = 10;
            // 
            // patientMRNLabel
            // 
            this.patientMRNLabel.AutoSize = true;
            this.patientMRNLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientMRNLabel.Location = new System.Drawing.Point(74, 0);
            this.patientMRNLabel.Name = "patientMRNLabel";
            this.patientMRNLabel.Size = new System.Drawing.Size(0, 15);
            this.patientMRNLabel.TabIndex = 3;
            // 
            // mrnLabel
            // 
            this.mrnLabel.AutoSize = true;
            this.mrnLabel.Location = new System.Drawing.Point(-3, 0);
            this.mrnLabel.Name = "mrnLabel";
            this.mrnLabel.Size = new System.Drawing.Size(0, 15);
            this.mrnLabel.TabIndex = 1;
            // 
            // patientEPNLabel
            // 
            this.patientEPNLabel.AutoSize = true;
            this.patientEPNLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientEPNLabel.Location = new System.Drawing.Point(75, 21);
            this.patientEPNLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.patientEPNLabel.Name = "patientEPNLabel";
            this.patientEPNLabel.Size = new System.Drawing.Size(0, 15);
            this.patientEPNLabel.TabIndex = 9;
            // 
            // epnLabel
            // 
            this.epnLabel.AutoSize = true;
            this.epnLabel.Location = new System.Drawing.Point(-3, 21);
            this.epnLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.epnLabel.Name = "epnLabel";
            this.epnLabel.Size = new System.Drawing.Size(0, 15);
            this.epnLabel.TabIndex = 4;
            // 
            // patientFacilityLabel
            // 
            this.patientFacilityLabel.AutoSize = true;
            this.patientFacilityLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientFacilityLabel.Location = new System.Drawing.Point(82, 92);
            this.patientFacilityLabel.Name = "patientFacilityLabel";
            this.patientFacilityLabel.Size = new System.Drawing.Size(0, 15);
            this.patientFacilityLabel.TabIndex = 2;
            // 
            // patientSSNLabel
            // 
            this.patientSSNLabel.AutoSize = true;
            this.patientSSNLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientSSNLabel.Location = new System.Drawing.Point(81, 68);
            this.patientSSNLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.patientSSNLabel.Name = "patientSSNLabel";
            this.patientSSNLabel.Size = new System.Drawing.Size(0, 15);
            this.patientSSNLabel.TabIndex = 8;
            // 
            // facilityLabel
            // 
            this.facilityLabel.AutoSize = true;
            this.facilityLabel.Location = new System.Drawing.Point(4, 92);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(0, 15);
            this.facilityLabel.TabIndex = 0;
            // 
            // patientGenderLabel
            // 
            this.patientGenderLabel.AutoSize = true;
            this.patientGenderLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientGenderLabel.Location = new System.Drawing.Point(82, 47);
            this.patientGenderLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.patientGenderLabel.Name = "patientGenderLabel";
            this.patientGenderLabel.Size = new System.Drawing.Size(0, 15);
            this.patientGenderLabel.TabIndex = 7;
            // 
            // patientDOBLabel
            // 
            this.patientDOBLabel.AutoSize = true;
            this.patientDOBLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientDOBLabel.Location = new System.Drawing.Point(83, 26);
            this.patientDOBLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.patientDOBLabel.Name = "patientDOBLabel";
            this.patientDOBLabel.Size = new System.Drawing.Size(0, 15);
            this.patientDOBLabel.TabIndex = 6;
            // 
            // patientNameLabel
            // 
            this.patientNameLabel.AutoSize = true;
            this.patientNameLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientNameLabel.Location = new System.Drawing.Point(83, 3);
            this.patientNameLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.patientNameLabel.Name = "patientNameLabel";
            this.patientNameLabel.Size = new System.Drawing.Size(0, 15);
            this.patientNameLabel.TabIndex = 5;
            // 
            // ssnLabel
            // 
            this.ssnLabel.AutoSize = true;
            this.ssnLabel.Location = new System.Drawing.Point(4, 68);
            this.ssnLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.ssnLabel.Name = "ssnLabel";
            this.ssnLabel.Size = new System.Drawing.Size(0, 15);
            this.ssnLabel.TabIndex = 3;
            // 
            // genderLabel
            // 
            this.genderLabel.AutoSize = true;
            this.genderLabel.Location = new System.Drawing.Point(4, 47);
            this.genderLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.genderLabel.Name = "genderLabel";
            this.genderLabel.Size = new System.Drawing.Size(0, 15);
            this.genderLabel.TabIndex = 2;
            // 
            // dobLabel
            // 
            this.dobLabel.AutoSize = true;
            this.dobLabel.Location = new System.Drawing.Point(4, 26);
            this.dobLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.dobLabel.Name = "dobLabel";
            this.dobLabel.Size = new System.Drawing.Size(0, 15);
            this.dobLabel.TabIndex = 1;
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Location = new System.Drawing.Point(4, 3);
            this.nameLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(0, 15);
            this.nameLabel.TabIndex = 0;
            // 
            // patientInfoLabel
            // 
            this.patientInfoLabel.AutoSize = true;
            this.patientInfoLabel.Location = new System.Drawing.Point(1, 58);
            this.patientInfoLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.patientInfoLabel.Name = "patientInfoLabel";
            this.patientInfoLabel.Size = new System.Drawing.Size(0, 15);
            this.patientInfoLabel.TabIndex = 4;
            // 
            // selectPatientButton
            // 
            this.selectPatientButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.selectPatientButton.Location = new System.Drawing.Point(0, 23);
            this.selectPatientButton.Margin = new System.Windows.Forms.Padding(4);
            this.selectPatientButton.Name = "selectPatientButton";
            this.selectPatientButton.Size = new System.Drawing.Size(329, 26);
            this.selectPatientButton.TabIndex = 1;
            this.selectPatientButton.UseVisualStyleBackColor = true;
            this.selectPatientButton.Click += new System.EventHandler(this.selectPatientButton_Click);
            // 
            // findPatientLabel
            // 
            this.findPatientLabel.AutoSize = true;
            this.findPatientLabel.Location = new System.Drawing.Point(4, 0);
            this.findPatientLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.findPatientLabel.Name = "findPatientLabel";
            this.findPatientLabel.Size = new System.Drawing.Size(0, 15);
            this.findPatientLabel.TabIndex = 2;
            // 
            // requestTypeCombo
            // 
            this.requestTypeCombo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.requestTypeCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.requestTypeCombo.Enabled = false;
            this.requestTypeCombo.FormattingEnabled = true;
            this.requestTypeCombo.Location = new System.Drawing.Point(0, 261);
            this.requestTypeCombo.Margin = new System.Windows.Forms.Padding(4);
            this.requestTypeCombo.Name = "requestTypeCombo";
            this.requestTypeCombo.Size = new System.Drawing.Size(332, 23);
            this.requestTypeCombo.TabIndex = 4;
            // 
            // statustoStatusLabel
            // 
            this.statustoStatusLabel.AutoSize = true;
            this.statustoStatusLabel.Location = new System.Drawing.Point(4, 7);
            this.statustoStatusLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.statustoStatusLabel.Name = "statustoStatusLabel";
            this.statustoStatusLabel.Size = new System.Drawing.Size(0, 15);
            this.statustoStatusLabel.TabIndex = 0;
            // 
            // AccountingOfDisclosureUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.searchCriteriaPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "AccountingOfDisclosureUI";
            this.Size = new System.Drawing.Size(332, 581);
            this.topPanel.ResumeLayout(false);
            this.topPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            this.searchCriteriaPanel.ResumeLayout(false);
            this.patientPanel.ResumeLayout(false);
            this.patientPanel.PerformLayout();
            this.patientInfoPanel.ResumeLayout(false);
            this.patientInfoPanel.PerformLayout();
            this.mrnPanel.ResumeLayout(false);
            this.mrnPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Label reportCriteriaLabel;
        private System.Windows.Forms.Panel searchCriteriaPanel;
        private ReportDateRangeUI reportDateRangeUI;
        private System.Windows.Forms.Panel patientPanel;
        private System.Windows.Forms.ComboBox requestTypeCombo;
        private System.Windows.Forms.Label statustoStatusLabel;
        private System.Windows.Forms.Button selectPatientButton;
        private System.Windows.Forms.Label findPatientLabel;
        private System.Windows.Forms.Panel patientInfoPanel;
        private System.Windows.Forms.Label patientInfoLabel;
        private System.Windows.Forms.Label epnLabel;
        private System.Windows.Forms.Label ssnLabel;
        private System.Windows.Forms.Label genderLabel;
        private System.Windows.Forms.Label dobLabel;
        private System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.Label patientEPNLabel;
        private System.Windows.Forms.Label patientSSNLabel;
        private System.Windows.Forms.Label patientGenderLabel;
        private System.Windows.Forms.Label patientDOBLabel;
        private System.Windows.Forms.Label patientNameLabel;
        private System.Windows.Forms.Label requestTypeLabel;
        private System.Windows.Forms.Panel mrnPanel;
        private System.Windows.Forms.Label facilityLabel;
        private System.Windows.Forms.Label mrnLabel;
        private System.Windows.Forms.Label patientMRNLabel;
        private System.Windows.Forms.Label patientFacilityLabel;
    }
}
