namespace McK.EIG.ROI.Client.Base.View
{
    partial class AboutROIHelpDialogUI
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
            this.headerLabel = new System.Windows.Forms.Label();
            this.versionLabel = new System.Windows.Forms.Label();
            this.copyRightInfoLabel = new System.Windows.Forms.Label();
            this.versionValueLabel = new System.Windows.Forms.Label();
            this.closeButton = new System.Windows.Forms.Button();
            this.supportInfoButton = new System.Windows.Forms.Button();
            this.copyrightHeaderLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // headerLabel
            // 
            this.headerLabel.AutoSize = true;
            this.headerLabel.Font = new System.Drawing.Font("Arial", 15F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.headerLabel.Location = new System.Drawing.Point(10, 20);
            this.headerLabel.Name = "headerLabel";
            this.headerLabel.Size = new System.Drawing.Size(0, 23);
            this.headerLabel.TabIndex = 0;
            // 
            // versionLabel
            // 
            this.versionLabel.AutoSize = true;
            this.versionLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.versionLabel.Location = new System.Drawing.Point(16, 61);
            this.versionLabel.Name = "versionLabel";
            this.versionLabel.Size = new System.Drawing.Size(0, 15);
            this.versionLabel.TabIndex = 1;
            // 
            // copyRightInfoLabel
            // 
            this.copyRightInfoLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.copyRightInfoLabel.Location = new System.Drawing.Point(16, 118);
            this.copyRightInfoLabel.Name = "copyRightInfoLabel";
            this.copyRightInfoLabel.Size = new System.Drawing.Size(512, 228);
            this.copyRightInfoLabel.TabIndex = 2;
            // 
            // versionValueLabel
            // 
            this.versionValueLabel.AutoSize = true;
            this.versionValueLabel.Location = new System.Drawing.Point(75, 61);
            this.versionValueLabel.Name = "versionValueLabel";
            this.versionValueLabel.Size = new System.Drawing.Size(0, 15);
            this.versionValueLabel.TabIndex = 3;
            // 
            // closeButton
            // 
            this.closeButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.closeButton.Location = new System.Drawing.Point(461, 373);
            this.closeButton.Name = "closeButton";
            this.closeButton.Size = new System.Drawing.Size(87, 27);
            this.closeButton.TabIndex = 4;
            this.closeButton.UseVisualStyleBackColor = true;
            this.closeButton.Click += new System.EventHandler(this.closeButton_Click);
            // 
            // supportInfoButton
            // 
            this.supportInfoButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.supportInfoButton.Location = new System.Drawing.Point(310, 373);
            this.supportInfoButton.Name = "supportInfoButton";
            this.supportInfoButton.Size = new System.Drawing.Size(145, 27);
            this.supportInfoButton.TabIndex = 5;
            this.supportInfoButton.UseVisualStyleBackColor = true;
            this.supportInfoButton.Click += new System.EventHandler(this.supportInfoButton_Click);
            // 
            // copyrightHeaderLabel
            // 
            this.copyrightHeaderLabel.AutoSize = true;
            this.copyrightHeaderLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.copyrightHeaderLabel.Location = new System.Drawing.Point(15, 92);
            this.copyrightHeaderLabel.Name = "copyrightHeaderLabel";
            this.copyrightHeaderLabel.Size = new System.Drawing.Size(0, 15);
            this.copyrightHeaderLabel.TabIndex = 7;
            // 
            // AboutROIHelpDialogUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.copyrightHeaderLabel);
            this.Controls.Add(this.supportInfoButton);
            this.Controls.Add(this.closeButton);
            this.Controls.Add(this.versionValueLabel);
            this.Controls.Add(this.copyRightInfoLabel);
            this.Controls.Add(this.versionLabel);
            this.Controls.Add(this.headerLabel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "AboutROIHelpDialogUI";
            this.Size = new System.Drawing.Size(565, 419);
            this.ResumeLayout(false);
            this.PerformLayout();

        }



        #endregion

        private System.Windows.Forms.Label headerLabel;
        private System.Windows.Forms.Label versionLabel;
        private System.Windows.Forms.Label copyRightInfoLabel;
        private System.Windows.Forms.Label versionValueLabel;
        private System.Windows.Forms.Button closeButton;
        private System.Windows.Forms.Button supportInfoButton;
        private System.Windows.Forms.Label copyrightHeaderLabel;
    }
}