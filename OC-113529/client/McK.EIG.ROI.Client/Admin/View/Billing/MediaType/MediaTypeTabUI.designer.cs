namespace McK.EIG.ROI.Client.Admin.View.Billing.MediaType
{
    partial class MediaTypeTabUI
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
            this.mediaTypeODPPanel = new System.Windows.Forms.Panel();
            this.descriptionLabel = new System.Windows.Forms.Label();
            this.img1 = new System.Windows.Forms.PictureBox();
            this.descriptionTextBox = new System.Windows.Forms.TextBox();
            this.nameLabel = new System.Windows.Forms.Label();
            this.nameTextBox = new System.Windows.Forms.TextBox();
            this.mediaTypeODPPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).BeginInit();
            this.SuspendLayout();
            // 
            // mediaTypeODPPanel
            // 
            this.mediaTypeODPPanel.AutoSize = true;
            this.mediaTypeODPPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.mediaTypeODPPanel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.mediaTypeODPPanel.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.mediaTypeODPPanel.Controls.Add(this.descriptionLabel);
            this.mediaTypeODPPanel.Controls.Add(this.img1);
            this.mediaTypeODPPanel.Controls.Add(this.descriptionTextBox);
            this.mediaTypeODPPanel.Controls.Add(this.nameLabel);
            this.mediaTypeODPPanel.Controls.Add(this.nameTextBox);
            this.mediaTypeODPPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.mediaTypeODPPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mediaTypeODPPanel.Location = new System.Drawing.Point(0, 0);
            this.mediaTypeODPPanel.Name = "mediaTypeODPPanel";
            this.mediaTypeODPPanel.Size = new System.Drawing.Size(834, 69);
            this.mediaTypeODPPanel.TabIndex = 2;
            // 
            // descriptionLabel
            // 
            this.descriptionLabel.AutoSize = true;
            this.descriptionLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.descriptionLabel.Location = new System.Drawing.Point(294, 12);
            this.descriptionLabel.Name = "descriptionLabel";
            this.descriptionLabel.Size = new System.Drawing.Size(0, 15);
            this.descriptionLabel.TabIndex = 3;
            // 
            // img1
            // 
            this.img1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img1.Location = new System.Drawing.Point(4, 14);
            this.img1.Name = "img1";
            this.img1.Size = new System.Drawing.Size(9, 10);
            this.img1.TabIndex = 10;
            this.img1.TabStop = false;
            // 
            // descriptionTextBox
            // 
            this.descriptionTextBox.AcceptsReturn = true;
            this.descriptionTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.descriptionTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.descriptionTextBox.Location = new System.Drawing.Point(365, 14);
            this.descriptionTextBox.MaxLength = 256;
            this.descriptionTextBox.Multiline = true;
            this.descriptionTextBox.Name = "descriptionTextBox";
            this.descriptionTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.descriptionTextBox.Size = new System.Drawing.Size(448, 40);
            this.descriptionTextBox.TabIndex = 2;
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nameLabel.Location = new System.Drawing.Point(16, 12);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(0, 15);
            this.nameLabel.TabIndex = 1;
            // 
            // nameTextBox
            // 
            this.nameTextBox.BackColor = System.Drawing.SystemColors.HighlightText;
            this.nameTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nameTextBox.Location = new System.Drawing.Point(92, 14);
            this.nameTextBox.MaxLength = 30;
            this.nameTextBox.Name = "nameTextBox";
            this.nameTextBox.Size = new System.Drawing.Size(150, 21);
            this.nameTextBox.TabIndex = 1;
            // 
            // MediaTypeTabUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.Controls.Add(this.mediaTypeODPPanel);
            this.DoubleBuffered = true;
            this.Name = "MediaTypeTabUI";
            this.Size = new System.Drawing.Size(834, 69);
            this.mediaTypeODPPanel.ResumeLayout(false);
            this.mediaTypeODPPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel mediaTypeODPPanel;
        private System.Windows.Forms.Label descriptionLabel;
        private System.Windows.Forms.PictureBox img1;
        private System.Windows.Forms.TextBox descriptionTextBox;
        private System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.TextBox nameTextBox;
    }
}
