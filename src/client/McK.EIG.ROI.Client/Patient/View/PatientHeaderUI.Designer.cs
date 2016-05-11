namespace McK.EIG.ROI.Client.Patient.View
{
    partial class PatientHeaderUI
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
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.patientInfoPanel = new McK.EIG.ROI.Client.Base.View.Common.ROIPanel(this.components);
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.nameText = new System.Windows.Forms.Label();
            this.vipLabel = new System.Windows.Forms.Label();
            this.epnText = new System.Windows.Forms.Label();
            this.epnLabel = new System.Windows.Forms.Label();
            this.dobText = new System.Windows.Forms.Label();
            this.mrnText = new System.Windows.Forms.Label();
            this.patientNameLabel = new System.Windows.Forms.Label();
            this.mrnLabel = new System.Windows.Forms.Label();
            this.facilityText = new System.Windows.Forms.Label();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.genderLabel = new System.Windows.Forms.Label();
            this.ssnText = new System.Windows.Forms.Label();
            this.genderText = new System.Windows.Forms.Label();
            this.ssnLabel = new System.Windows.Forms.Label();
            this.dobShortLabel = new System.Windows.Forms.Label();
            this.patientInfoPanel.SuspendLayout();
            this.flowLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // patientInfoPanel
            // 
            this.patientInfoPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.patientInfoPanel.Controls.Add(this.flowLayoutPanel1);
            this.patientInfoPanel.Controls.Add(this.epnText);
            this.patientInfoPanel.Controls.Add(this.epnLabel);
            this.patientInfoPanel.Controls.Add(this.dobText);
            this.patientInfoPanel.Controls.Add(this.mrnText);
            this.patientInfoPanel.Controls.Add(this.patientNameLabel);
            this.patientInfoPanel.Controls.Add(this.mrnLabel);
            this.patientInfoPanel.Controls.Add(this.facilityText);
            this.patientInfoPanel.Controls.Add(this.facilityLabel);
            this.patientInfoPanel.Controls.Add(this.genderLabel);
            this.patientInfoPanel.Controls.Add(this.ssnText);
            this.patientInfoPanel.Controls.Add(this.genderText);
            this.patientInfoPanel.Controls.Add(this.ssnLabel);
            this.patientInfoPanel.Controls.Add(this.dobShortLabel);
            this.patientInfoPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientInfoPanel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientInfoPanel.Location = new System.Drawing.Point(0, 0);
            this.patientInfoPanel.Name = "patientInfoPanel";
            this.patientInfoPanel.Size = new System.Drawing.Size(212, 68);
            this.patientInfoPanel.TabIndex = 0;
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.BackColor = System.Drawing.Color.Transparent;
            this.flowLayoutPanel1.Controls.Add(this.nameText);
            this.flowLayoutPanel1.Controls.Add(this.vipLabel);
            this.flowLayoutPanel1.Location = new System.Drawing.Point(36, 6);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(153, 17);
            this.flowLayoutPanel1.TabIndex = 43;
            // 
            // nameText
            // 
            this.nameText.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.nameText.AutoEllipsis = true;
            this.nameText.BackColor = System.Drawing.Color.Transparent;
            this.nameText.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nameText.Location = new System.Drawing.Point(0, 0);
            this.nameText.Margin = new System.Windows.Forms.Padding(0);
            this.nameText.Name = "nameText";
            this.nameText.Size = new System.Drawing.Size(115, 14);
            this.nameText.TabIndex = 31;
            // 
            // vipLabel
            // 
            this.vipLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.vipLabel.AutoSize = true;
            this.vipLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.vipLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.vipLabel.ForeColor = System.Drawing.Color.Red;
            this.vipLabel.Location = new System.Drawing.Point(118, 0);
            this.vipLabel.Name = "vipLabel";
            this.vipLabel.Size = new System.Drawing.Size(0, 15);
            this.vipLabel.TabIndex = 32;
            // 
            // epnText
            // 
            this.epnText.AutoEllipsis = true;
            this.epnText.BackColor = System.Drawing.Color.Transparent;
            this.epnText.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnText.Location = new System.Drawing.Point(140, 33);
            this.epnText.Margin = new System.Windows.Forms.Padding(0);
            this.epnText.Name = "epnText";
            this.epnText.Size = new System.Drawing.Size(57, 14);
            this.epnText.TabIndex = 42;
            // 
            // epnLabel
            // 
            this.epnLabel.AutoSize = true;
            this.epnLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.epnLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnLabel.Location = new System.Drawing.Point(114, 34);
            this.epnLabel.Name = "epnLabel";
            this.epnLabel.Size = new System.Drawing.Size(0, 14);
            this.epnLabel.TabIndex = 41;
            // 
            // dobText
            // 
            this.dobText.AutoSize = true;
            this.dobText.BackColor = System.Drawing.Color.Transparent;
            this.dobText.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dobText.Location = new System.Drawing.Point(53, 34);
            this.dobText.Margin = new System.Windows.Forms.Padding(0);
            this.dobText.Name = "dobText";
            this.dobText.Size = new System.Drawing.Size(0, 14);
            this.dobText.TabIndex = 34;
            // 
            // mrnText
            // 
            this.mrnText.AutoEllipsis = true;
            this.mrnText.BackColor = System.Drawing.Color.Transparent;
            this.mrnText.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnText.Location = new System.Drawing.Point(140, 46);
            this.mrnText.Margin = new System.Windows.Forms.Padding(0);
            this.mrnText.Name = "mrnText";
            this.mrnText.Size = new System.Drawing.Size(57, 14);
            this.mrnText.TabIndex = 40;
            // 
            // patientNameLabel
            // 
            this.patientNameLabel.AutoSize = true;
            this.patientNameLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.patientNameLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientNameLabel.Location = new System.Drawing.Point(5, 6);
            this.patientNameLabel.Name = "patientNameLabel";
            this.patientNameLabel.Size = new System.Drawing.Size(0, 14);
            this.patientNameLabel.TabIndex = 28;
            // 
            // mrnLabel
            // 
            this.mrnLabel.AutoSize = true;
            this.mrnLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.mrnLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnLabel.Location = new System.Drawing.Point(114, 46);
            this.mrnLabel.Name = "mrnLabel";
            this.mrnLabel.Size = new System.Drawing.Size(0, 14);
            this.mrnLabel.TabIndex = 39;
            // 
            // facilityText
            // 
            this.facilityText.AutoSize = true;
            this.facilityText.BackColor = System.Drawing.Color.Transparent;
            this.facilityText.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityText.Location = new System.Drawing.Point(53, 46);
            this.facilityText.Margin = new System.Windows.Forms.Padding(0);
            this.facilityText.Name = "facilityText";
            this.facilityText.Size = new System.Drawing.Size(0, 14);
            this.facilityText.TabIndex = 38;
            // 
            // facilityLabel
            // 
            this.facilityLabel.AutoSize = true;
            this.facilityLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.facilityLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityLabel.Location = new System.Drawing.Point(14, 46);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(0, 14);
            this.facilityLabel.TabIndex = 37;
            // 
            // genderLabel
            // 
            this.genderLabel.AutoSize = true;
            this.genderLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.genderLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.genderLabel.Location = new System.Drawing.Point(14, 21);
            this.genderLabel.Name = "genderLabel";
            this.genderLabel.Size = new System.Drawing.Size(0, 14);
            this.genderLabel.TabIndex = 31;
            // 
            // ssnText
            // 
            this.ssnText.AutoEllipsis = true;
            this.ssnText.BackColor = System.Drawing.Color.Transparent;
            this.ssnText.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ssnText.Location = new System.Drawing.Point(140, 22);
            this.ssnText.Margin = new System.Windows.Forms.Padding(0);
            this.ssnText.Name = "ssnText";
            this.ssnText.Size = new System.Drawing.Size(57, 14);
            this.ssnText.TabIndex = 36;
            // 
            // genderText
            // 
            this.genderText.AutoSize = true;
            this.genderText.BackColor = System.Drawing.Color.Transparent;
            this.genderText.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.genderText.Location = new System.Drawing.Point(53, 21);
            this.genderText.Margin = new System.Windows.Forms.Padding(0);
            this.genderText.Name = "genderText";
            this.genderText.Size = new System.Drawing.Size(0, 14);
            this.genderText.TabIndex = 32;
            // 
            // ssnLabel
            // 
            this.ssnLabel.AutoSize = true;
            this.ssnLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.ssnLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ssnLabel.Location = new System.Drawing.Point(114, 22);
            this.ssnLabel.Name = "ssnLabel";
            this.ssnLabel.Size = new System.Drawing.Size(0, 14);
            this.ssnLabel.TabIndex = 35;
            // 
            // dobShortLabel
            // 
            this.dobShortLabel.AutoSize = true;
            this.dobShortLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(238)))), ((int)(((byte)(245)))));
            this.dobShortLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dobShortLabel.Location = new System.Drawing.Point(14, 34);
            this.dobShortLabel.Name = "dobShortLabel";
            this.dobShortLabel.Size = new System.Drawing.Size(0, 14);
            this.dobShortLabel.TabIndex = 33;
            // 
            // PatientHeaderUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.patientInfoPanel);
            this.Name = "PatientHeaderUI";
            this.Size = new System.Drawing.Size(212, 68);
            this.patientInfoPanel.ResumeLayout(false);
            this.patientInfoPanel.PerformLayout();
            this.flowLayoutPanel1.ResumeLayout(false);
            this.flowLayoutPanel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private McK.EIG.ROI.Client.Base.View.Common.ROIPanel patientInfoPanel;
        private System.Windows.Forms.Label dobText;
        private System.Windows.Forms.Label mrnText;
        private System.Windows.Forms.Label patientNameLabel;
        private System.Windows.Forms.Label mrnLabel;
        private System.Windows.Forms.Label facilityText;
        private System.Windows.Forms.Label facilityLabel;
        private System.Windows.Forms.Label genderLabel;
        private System.Windows.Forms.Label ssnText;
        private System.Windows.Forms.Label genderText;
        private System.Windows.Forms.Label ssnLabel;
        private System.Windows.Forms.Label dobShortLabel;
        private System.Windows.Forms.Label epnText;
        private System.Windows.Forms.Label epnLabel;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.Label nameText;
        private System.Windows.Forms.Label vipLabel;
        private System.Windows.Forms.ToolTip toolTip;

    }
}
