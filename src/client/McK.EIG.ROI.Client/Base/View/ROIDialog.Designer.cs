namespace McK.EIG.ROI.Client.Base.View
{
    partial class ROIDialog
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

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.dialogOuterPanel = new System.Windows.Forms.Panel();
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.ignoreButton = new System.Windows.Forms.Button();
            this.okButton = new System.Windows.Forms.Button();
            this.outerPanel = new System.Windows.Forms.Panel();
            this.messageDisplayPanel = new System.Windows.Forms.Panel();
            this.messageLabel = new System.Windows.Forms.Label();
            this.errorImage = new System.Windows.Forms.PictureBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.headerLabel = new System.Windows.Forms.Label();
            this.dialogOuterPanel.SuspendLayout();
            this.flowLayoutPanel1.SuspendLayout();
            this.outerPanel.SuspendLayout();
            this.messageDisplayPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorImage)).BeginInit();
            this.SuspendLayout();
            // 
            // dialogOuterPanel
            // 
            this.dialogOuterPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(249)))), ((int)(((byte)(248)))), ((int)(((byte)(248)))));
            this.dialogOuterPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.dialogOuterPanel.Controls.Add(this.flowLayoutPanel1);
            this.dialogOuterPanel.Controls.Add(this.outerPanel);
            this.dialogOuterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dialogOuterPanel.Location = new System.Drawing.Point(0, 0);
            this.dialogOuterPanel.Name = "dialogOuterPanel";
            this.dialogOuterPanel.Size = new System.Drawing.Size(530, 196);
            this.dialogOuterPanel.TabIndex = 0;
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.flowLayoutPanel1.AutoSize = true;
            this.flowLayoutPanel1.Controls.Add(this.cancelButton);
            this.flowLayoutPanel1.Controls.Add(this.ignoreButton);
            this.flowLayoutPanel1.Controls.Add(this.okButton);
            this.flowLayoutPanel1.FlowDirection = System.Windows.Forms.FlowDirection.RightToLeft;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(194, 150);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(314, 33);
            this.flowLayoutPanel1.TabIndex = 5;
            // 
            // cancelButton
            // 
            this.cancelButton.AutoSize = true;
            this.cancelButton.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(206, 3);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(105, 27);
            this.cancelButton.TabIndex = 3;
            this.cancelButton.UseVisualStyleBackColor = false;
            // 
            // ignoreButton
            // 
            this.ignoreButton.AutoSize = true;
            this.ignoreButton.DialogResult = System.Windows.Forms.DialogResult.Ignore;
            this.ignoreButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ignoreButton.Location = new System.Drawing.Point(113, 3);
            this.ignoreButton.Name = "ignoreButton";
            this.ignoreButton.Size = new System.Drawing.Size(87, 27);
            this.ignoreButton.TabIndex = 2;
            this.ignoreButton.UseVisualStyleBackColor = true;
            // 
            // okButton
            // 
            this.okButton.AutoSize = true;
            this.okButton.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.okButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.okButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.okButton.Location = new System.Drawing.Point(8, 3);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(99, 27);
            this.okButton.TabIndex = 1;
            this.okButton.UseVisualStyleBackColor = false;
            // 
            // outerPanel
            // 
            this.outerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(145)))), ((int)(((byte)(167)))), ((int)(((byte)(180)))));
            this.outerPanel.Controls.Add(this.messageDisplayPanel);
            this.outerPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.outerPanel.Location = new System.Drawing.Point(17, 12);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.Padding = new System.Windows.Forms.Padding(1);
            this.outerPanel.Size = new System.Drawing.Size(500, 132);
            this.outerPanel.TabIndex = 3;
            // 
            // messageDisplayPanel
            // 
            this.messageDisplayPanel.BackColor = System.Drawing.Color.White;
            this.messageDisplayPanel.Controls.Add(this.headerLabel);
            this.messageDisplayPanel.Controls.Add(this.messageLabel);
            this.messageDisplayPanel.Controls.Add(this.errorImage);
            this.messageDisplayPanel.Location = new System.Drawing.Point(1, 1);
            this.messageDisplayPanel.Name = "messageDisplayPanel";
            this.messageDisplayPanel.Size = new System.Drawing.Size(498, 126);
            this.messageDisplayPanel.TabIndex = 0;
            // 
            // messageLabel
            // 
            this.messageLabel.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.messageLabel.Location = new System.Drawing.Point(115, 17);
            this.messageLabel.Name = "messageLabel";
            this.messageLabel.Size = new System.Drawing.Size(338, 96);
            this.messageLabel.TabIndex = 1;
            this.messageLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // errorImage
            // 
            this.errorImage.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.errorImage.Location = new System.Drawing.Point(15, 16);
            this.errorImage.Name = "errorImage";
            this.errorImage.Size = new System.Drawing.Size(87, 87);
            this.errorImage.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.errorImage.TabIndex = 0;
            this.errorImage.TabStop = false;
            // 
            // headerLabel
            // 
            this.headerLabel.AutoSize = true;
            this.headerLabel.Font = new System.Drawing.Font("Arial", 11.5F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.headerLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(27)))), ((int)(((byte)(81)))), ((int)(((byte)(156)))));
            this.headerLabel.Location = new System.Drawing.Point(115, 8);
            this.headerLabel.Name = "headerLabel";
            this.headerLabel.Size = new System.Drawing.Size(0, 15);
            this.headerLabel.TabIndex = 2;
            // 
            // ROIDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(530, 196);
            this.Controls.Add(this.dialogOuterPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.HelpButton = true;
            this.MaximizeBox = false;
            this.MaximumSize = new System.Drawing.Size(546, 235);
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(546, 235);
            this.Name = "ROIDialog";
            this.ShowIcon = false;
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.dialogOuterPanel.ResumeLayout(false);
            this.dialogOuterPanel.PerformLayout();
            this.flowLayoutPanel1.ResumeLayout(false);
            this.flowLayoutPanel1.PerformLayout();
            this.outerPanel.ResumeLayout(false);
            this.messageDisplayPanel.ResumeLayout(false);
            this.messageDisplayPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorImage)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel dialogOuterPanel;
        private System.Windows.Forms.Panel messageDisplayPanel;
        private System.Windows.Forms.PictureBox errorImage;
        private System.Windows.Forms.Label messageLabel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel outerPanel;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button ignoreButton;
        private System.Windows.Forms.Label headerLabel;
    }
}