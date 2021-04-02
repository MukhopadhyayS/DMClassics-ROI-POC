namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    partial class AddPatientUI
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
            this.actionPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.addPatientCloseButton = new System.Windows.Forms.Button();
            this.addPatientContinueButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.successfulLabel = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.patientInfoTabPage = new System.Windows.Forms.TabPage();
            this.findPatientTabPage = new System.Windows.Forms.TabPage();
            this.patientProfileTabControl = new System.Windows.Forms.TabControl();
            this.patientProfilePanel = new System.Windows.Forms.TableLayoutPanel();
            this.footerPanel.SuspendLayout();
            this.actionPanel.SuspendLayout();
            this.patientProfileTabControl.SuspendLayout();
            this.patientProfilePanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Location = new System.Drawing.Point(3, 3);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(908, 66);
            this.topPanel.TabIndex = 1;
            // 
            // footerPanel
            // 
            this.footerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.footerPanel.Controls.Add(this.actionPanel);
            this.footerPanel.Controls.Add(this.successfulLabel);
            this.footerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.footerPanel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(31)))), ((int)(((byte)(131)))), ((int)(((byte)(1)))));
            this.footerPanel.Location = new System.Drawing.Point(3, 647);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(908, 41);
            this.footerPanel.TabIndex = 3;
            // 
            // actionPanel
            // 
            this.actionPanel.AutoSize = true;
            this.actionPanel.Controls.Add(this.addPatientCloseButton);
            this.actionPanel.Controls.Add(this.addPatientContinueButton);
            this.actionPanel.Controls.Add(this.cancelButton);
            this.actionPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.actionPanel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.actionPanel.Location = new System.Drawing.Point(293, 3);
            this.actionPanel.Name = "actionPanel";
            this.actionPanel.Size = new System.Drawing.Size(318, 34);
            this.actionPanel.TabIndex = 6;
            // 
            // addPatientCloseButton
            // 
            this.addPatientCloseButton.AutoSize = true;
            this.addPatientCloseButton.ForeColor = System.Drawing.SystemColors.ControlText;
            this.addPatientCloseButton.Location = new System.Drawing.Point(3, 3);
            this.addPatientCloseButton.Name = "addPatientCloseButton";
            this.addPatientCloseButton.Size = new System.Drawing.Size(104, 27);
            this.addPatientCloseButton.TabIndex = 2;
            this.addPatientCloseButton.UseVisualStyleBackColor = true;
            this.addPatientCloseButton.Click += new System.EventHandler(this.addPatientCloseButton_Click);
            // 
            // addPatientContinueButton
            // 
            this.addPatientContinueButton.AutoSize = true;
            this.addPatientContinueButton.ForeColor = System.Drawing.SystemColors.ControlText;
            this.addPatientContinueButton.Location = new System.Drawing.Point(113, 3);
            this.addPatientContinueButton.Name = "addPatientContinueButton";
            this.addPatientContinueButton.Size = new System.Drawing.Size(120, 27);
            this.addPatientContinueButton.TabIndex = 3;
            this.addPatientContinueButton.UseVisualStyleBackColor = true;
            this.addPatientContinueButton.Click += new System.EventHandler(this.addPatientContinueButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.ForeColor = System.Drawing.SystemColors.ControlText;
            this.cancelButton.Location = new System.Drawing.Point(239, 3);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 27);
            this.cancelButton.TabIndex = 4;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // successfulLabel
            // 
            this.successfulLabel.AutoSize = true;
            this.successfulLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.successfulLabel.Location = new System.Drawing.Point(86, 14);
            this.successfulLabel.Name = "successfulLabel";
            this.successfulLabel.Size = new System.Drawing.Size(0, 15);
            this.successfulLabel.TabIndex = 5;
            this.successfulLabel.Visible = false;
            // 
            // patientInfoTabPage
            // 
            this.patientInfoTabPage.AutoScroll = true;
            this.patientInfoTabPage.BackColor = System.Drawing.Color.White;
            this.patientInfoTabPage.Location = new System.Drawing.Point(4, 22);
            this.patientInfoTabPage.Name = "patientInfoTabPage";
            this.patientInfoTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.patientInfoTabPage.Size = new System.Drawing.Size(900, 540);
            this.patientInfoTabPage.TabIndex = 1;
            // 
            // findPatientTabPage
            // 
            this.findPatientTabPage.AutoScroll = true;
            this.findPatientTabPage.BackColor = System.Drawing.Color.White;
            this.findPatientTabPage.Location = new System.Drawing.Point(4, 22);
            this.findPatientTabPage.Name = "findPatientTabPage";
            this.findPatientTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.findPatientTabPage.Size = new System.Drawing.Size(900, 540);
            this.findPatientTabPage.TabIndex = 0;
            // 
            // patientProfileTabControl
            // 
            this.patientProfileTabControl.Controls.Add(this.findPatientTabPage);
            this.patientProfileTabControl.Controls.Add(this.patientInfoTabPage);
            this.patientProfileTabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientProfileTabControl.Location = new System.Drawing.Point(3, 75);
            this.patientProfileTabControl.Name = "patientProfileTabControl";
            this.patientProfileTabControl.SelectedIndex = 0;
            this.patientProfileTabControl.Size = new System.Drawing.Size(908, 566);
            this.patientProfileTabControl.TabIndex = 0;
            this.patientProfileTabControl.Selecting += new System.Windows.Forms.TabControlCancelEventHandler(this.patientProfileTabControl_Selecting);
            this.patientProfileTabControl.SelectedIndexChanged += new System.EventHandler(this.patientProfileTabControl_SelectedIndexChanged);
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
            this.patientProfilePanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 11.18012F));
            this.patientProfilePanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 88.81988F));
            this.patientProfilePanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 46F));
            this.patientProfilePanel.Size = new System.Drawing.Size(914, 691);
            this.patientProfilePanel.TabIndex = 4;
            // 
            // AddPatientUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.patientProfilePanel);
            this.Name = "AddPatientUI";
            this.Padding = new System.Windows.Forms.Padding(5);
            this.Size = new System.Drawing.Size(914, 693);
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            this.actionPanel.ResumeLayout(false);
            this.actionPanel.PerformLayout();
            this.patientProfileTabControl.ResumeLayout(false);
            this.patientProfilePanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.ToolTip toolTip;
        internal System.Windows.Forms.TabControl patientProfileTabControl;
        private System.Windows.Forms.TabPage findPatientTabPage;
        private System.Windows.Forms.TabPage patientInfoTabPage;
        private System.Windows.Forms.TableLayoutPanel patientProfilePanel;
        private System.Windows.Forms.Button addPatientCloseButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button addPatientContinueButton;
        private System.Windows.Forms.Label successfulLabel;
        private System.Windows.Forms.FlowLayoutPanel actionPanel;
    }
}
