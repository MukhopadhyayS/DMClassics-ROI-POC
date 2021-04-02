namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    partial class ViewEditPatientUI
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
            this.footerPanel = new System.Windows.Forms.Panel();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.actionPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.saveButton = new System.Windows.Forms.Button();
            this.revertButton = new System.Windows.Forms.Button();
            this.closeButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.patientProfilePanel = new System.Windows.Forms.TableLayoutPanel();
            this.patientProfileTabControl = new System.Windows.Forms.TabControl();
            this.patientInfoTabPage = new System.Windows.Forms.TabPage();
            this.footerPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            this.actionPanel.SuspendLayout();
            this.patientProfilePanel.SuspendLayout();
            this.patientProfileTabControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Location = new System.Drawing.Point(3, 3);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(908, 63);
            this.topPanel.TabIndex = 1;
            // 
            // footerPanel
            // 
            this.footerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.footerPanel.Controls.Add(this.requiredLabel);
            this.footerPanel.Controls.Add(this.requiredImg);
            this.footerPanel.Controls.Add(this.actionPanel);
            this.footerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.footerPanel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.footerPanel.Location = new System.Drawing.Point(3, 647);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(908, 41);
            this.footerPanel.TabIndex = 3;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(16, 12);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 8;
            // 
            // requiredImg
            // 
            this.requiredImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImg.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.Location = new System.Drawing.Point(3, 15);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(13, 10);
            this.requiredImg.TabIndex = 7;
            this.requiredImg.TabStop = false;
            // 
            // actionPanel
            // 
            this.actionPanel.AutoSize = true;
            this.actionPanel.Controls.Add(this.saveButton);
            this.actionPanel.Controls.Add(this.revertButton);
            this.actionPanel.Controls.Add(this.closeButton);
            this.actionPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.actionPanel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.actionPanel.Location = new System.Drawing.Point(331, 3);
            this.actionPanel.Name = "actionPanel";
            this.actionPanel.Size = new System.Drawing.Size(244, 34);
            this.actionPanel.TabIndex = 6;
            // 
            // saveButton
            // 
            this.saveButton.AutoSize = true;
            this.saveButton.ForeColor = System.Drawing.SystemColors.ControlText;
            this.saveButton.Location = new System.Drawing.Point(3, 3);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 27);
            this.saveButton.TabIndex = 2;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // revertButton
            // 
            this.revertButton.AutoSize = true;
            this.revertButton.ForeColor = System.Drawing.SystemColors.ControlText;
            this.revertButton.Location = new System.Drawing.Point(84, 3);
            this.revertButton.Name = "revertButton";
            this.revertButton.Size = new System.Drawing.Size(75, 27);
            this.revertButton.TabIndex = 3;
            this.revertButton.UseVisualStyleBackColor = true;
            this.revertButton.Click += new System.EventHandler(this.revertButton_Click);
            // 
            // closeButton
            // 
            this.closeButton.ForeColor = System.Drawing.SystemColors.ControlText;
            this.closeButton.Location = new System.Drawing.Point(165, 3);
            this.closeButton.Name = "closeButton";
            this.closeButton.Size = new System.Drawing.Size(75, 27);
            this.closeButton.TabIndex = 4;
            this.closeButton.UseVisualStyleBackColor = true;
            this.closeButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // patientProfilePanel
            // 
            this.patientProfilePanel.ColumnCount = 1;
            this.patientProfilePanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.patientProfilePanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.patientProfilePanel.Controls.Add(this.patientProfileTabControl, 0, 1);
            this.patientProfilePanel.Controls.Add(this.footerPanel, 0, 2);
            this.patientProfilePanel.Controls.Add(this.topPanel, 0, 0);
            this.patientProfilePanel.Location = new System.Drawing.Point(0, 0);
            this.patientProfilePanel.Name = "patientProfilePanel";
            this.patientProfilePanel.RowCount = 3;
            this.patientProfilePanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 10.71429F));
            this.patientProfilePanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 89.28571F));
            this.patientProfilePanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 46F));
            this.patientProfilePanel.Size = new System.Drawing.Size(914, 691);
            this.patientProfilePanel.TabIndex = 4;
            // 
            // patientProfileTabControl
            // 
            this.patientProfileTabControl.Controls.Add(this.patientInfoTabPage);
            this.patientProfileTabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientProfileTabControl.Location = new System.Drawing.Point(3, 72);
            this.patientProfileTabControl.Name = "patientProfileTabControl";
            this.patientProfileTabControl.SelectedIndex = 0;
            this.patientProfileTabControl.Size = new System.Drawing.Size(908, 569);
            this.patientProfileTabControl.TabIndex = 0;
            // 
            // patientInfoTabPage
            // 
            this.patientInfoTabPage.AutoScroll = true;
            this.patientInfoTabPage.BackColor = System.Drawing.Color.White;
            this.patientInfoTabPage.Location = new System.Drawing.Point(4, 22);
            this.patientInfoTabPage.Name = "patientInfoTabPage";
            this.patientInfoTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.patientInfoTabPage.Size = new System.Drawing.Size(900, 543);
            this.patientInfoTabPage.TabIndex = 1;
            // 
            // ViewEditPatientUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.patientProfilePanel);
            this.Name = "ViewEditPatientUI";
            this.Padding = new System.Windows.Forms.Padding(5);
            this.Size = new System.Drawing.Size(914, 693);
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            this.actionPanel.ResumeLayout(false);
            this.actionPanel.PerformLayout();
            this.patientProfilePanel.ResumeLayout(false);
            this.patientProfileTabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.TableLayoutPanel patientProfilePanel;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Button closeButton;
        private System.Windows.Forms.Button revertButton;
        private System.Windows.Forms.FlowLayoutPanel actionPanel;
        internal System.Windows.Forms.TabControl patientProfileTabControl;
        private System.Windows.Forms.TabPage patientInfoTabPage;
        private System.Windows.Forms.PictureBox requiredImg;
        private System.Windows.Forms.Label requiredLabel;
    }
}
