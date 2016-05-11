namespace McK.EIG.ROI.Client.Base.View
{
    partial class ROIProgressBar
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
            this.progressBarPanel = new System.Windows.Forms.Panel();
            this.percentageLabel = new System.Windows.Forms.Label();
            this.messageLabel = new System.Windows.Forms.Label();
            this.fileTransferProgressBar = new System.Windows.Forms.ProgressBar();
            this.outerPanel = new System.Windows.Forms.Panel();
            this.progressBarPanel.SuspendLayout();
            this.outerPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // progressBarPanel
            // 
            this.progressBarPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(240)))), ((int)(((byte)(240)))));
            this.progressBarPanel.Controls.Add(this.percentageLabel);
            this.progressBarPanel.Controls.Add(this.messageLabel);
            this.progressBarPanel.Controls.Add(this.fileTransferProgressBar);
            this.progressBarPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.progressBarPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.progressBarPanel.Location = new System.Drawing.Point(1, 1);
            this.progressBarPanel.Name = "progressBarPanel";
            this.progressBarPanel.Size = new System.Drawing.Size(350, 58);
            this.progressBarPanel.TabIndex = 0;
            // 
            // percentageLabel
            // 
            this.percentageLabel.AutoSize = true;
            this.percentageLabel.BackColor = System.Drawing.Color.Transparent;
            this.percentageLabel.Location = new System.Drawing.Point(181, 30);
            this.percentageLabel.Name = "percentageLabel";
            this.percentageLabel.Size = new System.Drawing.Size(0, 15);
            this.percentageLabel.TabIndex = 2;
            // 
            // messageLabel
            // 
            this.messageLabel.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.messageLabel.AutoSize = true;
            this.messageLabel.FlatStyle = System.Windows.Forms.FlatStyle.System;
            this.messageLabel.Location = new System.Drawing.Point(128, 1);
            this.messageLabel.Name = "messageLabel";
            this.messageLabel.Size = new System.Drawing.Size(0, 15);
            this.messageLabel.TabIndex = 1;
            // 
            // fileTransferProgressBar
            // 
            this.fileTransferProgressBar.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.fileTransferProgressBar.Location = new System.Drawing.Point(17, 20);
            this.fileTransferProgressBar.Name = "fileTransferProgressBar";
            this.fileTransferProgressBar.Size = new System.Drawing.Size(313, 27);
            this.fileTransferProgressBar.Style = System.Windows.Forms.ProgressBarStyle.Continuous;
            this.fileTransferProgressBar.TabIndex = 0;
            // 
            // outerPanel
            // 
            this.outerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(145)))), ((int)(((byte)(167)))), ((int)(((byte)(180)))));
            this.outerPanel.Controls.Add(this.progressBarPanel);
            this.outerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.outerPanel.Location = new System.Drawing.Point(0, 0);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.Padding = new System.Windows.Forms.Padding(1);
            this.outerPanel.Size = new System.Drawing.Size(352, 60);
            this.outerPanel.TabIndex = 1;
            // 
            // ROIProgressBar
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.outerPanel);
            this.Name = "ROIProgressBar";
            this.Size = new System.Drawing.Size(352, 60);
            this.progressBarPanel.ResumeLayout(false);
            this.progressBarPanel.PerformLayout();
            this.outerPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel progressBarPanel;
        private System.Windows.Forms.Label messageLabel;
        private System.Windows.Forms.ProgressBar fileTransferProgressBar;
        private System.Windows.Forms.Label percentageLabel;
        private System.Windows.Forms.Panel outerPanel;
    }
}
