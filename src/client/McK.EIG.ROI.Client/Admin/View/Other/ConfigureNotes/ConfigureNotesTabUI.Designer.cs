namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureNotes
{
    partial class ConfigureNotesTabUI
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
            this.adjustmentReasonODPPanel = new System.Windows.Forms.Panel();
            this.requiredDisplayTextImage = new System.Windows.Forms.PictureBox();
            this.displayTextBox = new System.Windows.Forms.TextBox();
            this.displayTextLabel = new System.Windows.Forms.Label();
            this.nameTextBox = new System.Windows.Forms.TextBox();
            this.configureNameLabel = new System.Windows.Forms.Label();
            this.requiredNameImage = new System.Windows.Forms.PictureBox();
            this.adjustmentReasonODPPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredDisplayTextImage)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredNameImage)).BeginInit();
            this.SuspendLayout();
            // 
            // adjustmentReasonODPPanel
            // 
            this.adjustmentReasonODPPanel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.adjustmentReasonODPPanel.Controls.Add(this.requiredDisplayTextImage);
            this.adjustmentReasonODPPanel.Controls.Add(this.displayTextBox);
            this.adjustmentReasonODPPanel.Controls.Add(this.displayTextLabel);
            this.adjustmentReasonODPPanel.Controls.Add(this.nameTextBox);
            this.adjustmentReasonODPPanel.Controls.Add(this.configureNameLabel);
            this.adjustmentReasonODPPanel.Controls.Add(this.requiredNameImage);
            this.adjustmentReasonODPPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.adjustmentReasonODPPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adjustmentReasonODPPanel.Location = new System.Drawing.Point(0, 0);
            this.adjustmentReasonODPPanel.Name = "adjustmentReasonODPPanel";
            this.adjustmentReasonODPPanel.Size = new System.Drawing.Size(703, 89);
            this.adjustmentReasonODPPanel.TabIndex = 1;
            // 
            // requiredDisplayTextImage
            // 
            this.requiredDisplayTextImage.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredDisplayTextImage.Location = new System.Drawing.Point(275, 14);
            this.requiredDisplayTextImage.Name = "requiredDisplayTextImage";
            this.requiredDisplayTextImage.Size = new System.Drawing.Size(9, 10);
            this.requiredDisplayTextImage.TabIndex = 5;
            this.requiredDisplayTextImage.TabStop = false;
            // 
            // displayTextBox
            // 
            this.displayTextBox.AcceptsReturn = true;
            this.displayTextBox.Location = new System.Drawing.Point(358, 9);
            this.displayTextBox.MaxLength = 255;
            this.displayTextBox.Multiline = true;
            this.displayTextBox.Name = "displayTextBox";
            this.displayTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.displayTextBox.Size = new System.Drawing.Size(250, 40);
            this.displayTextBox.TabIndex = 2;
            // 
            // displayTextLabel
            // 
            this.displayTextLabel.AutoSize = true;
            this.displayTextLabel.Location = new System.Drawing.Point(290, 12);
            this.displayTextLabel.Name = "displayTextLabel";
            this.displayTextLabel.Size = new System.Drawing.Size(0, 15);
            this.displayTextLabel.TabIndex = 3;
            // 
            // nameTextBox
            // 
            this.nameTextBox.Location = new System.Drawing.Point(106, 12);
            this.nameTextBox.MaxLength = 30;
            this.nameTextBox.Name = "nameTextBox";
            this.nameTextBox.Size = new System.Drawing.Size(150, 21);
            this.nameTextBox.TabIndex = 1;
            // 
            // configureNameLabel
            // 
            this.configureNameLabel.AutoSize = true;
            this.configureNameLabel.Location = new System.Drawing.Point(19, 12);
            this.configureNameLabel.Name = "configureNameLabel";
            this.configureNameLabel.Size = new System.Drawing.Size(0, 15);
            this.configureNameLabel.TabIndex = 1;
            // 
            // requiredNameImage
            // 
            this.requiredNameImage.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredNameImage.Location = new System.Drawing.Point(4, 14);
            this.requiredNameImage.Name = "requiredNameImage";
            this.requiredNameImage.Size = new System.Drawing.Size(9, 10);
            this.requiredNameImage.TabIndex = 0;
            this.requiredNameImage.TabStop = false;
            // 
            // ConfigureNotesTabUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.adjustmentReasonODPPanel);
            this.Name = "ConfigureNotesTabUI";
            this.Size = new System.Drawing.Size(703, 89);
            this.adjustmentReasonODPPanel.ResumeLayout(false);
            this.adjustmentReasonODPPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredDisplayTextImage)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredNameImage)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel adjustmentReasonODPPanel;
        private System.Windows.Forms.PictureBox requiredDisplayTextImage;
        private System.Windows.Forms.TextBox displayTextBox;
        private System.Windows.Forms.Label displayTextLabel;
        private System.Windows.Forms.TextBox nameTextBox;
        private System.Windows.Forms.Label configureNameLabel;
        private System.Windows.Forms.PictureBox requiredNameImage;
    }
}
