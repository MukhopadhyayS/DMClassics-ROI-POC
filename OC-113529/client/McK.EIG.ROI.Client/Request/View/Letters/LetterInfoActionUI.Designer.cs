namespace McK.EIG.ROI.Client.Request.View.Letters
{
    partial class LetterInfoActionUI
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
            this.infoActionPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.createLetterButton = new System.Windows.Forms.Button();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.infoActionPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            this.SuspendLayout();
            // 
            // infoActionPanel
            // 
            this.infoActionPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.infoActionPanel.Controls.Add(this.cancelButton);
            this.infoActionPanel.Controls.Add(this.createLetterButton);
            this.infoActionPanel.Controls.Add(this.requiredLabel);
            this.infoActionPanel.Controls.Add(this.requiredImg);
            this.infoActionPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.infoActionPanel.Location = new System.Drawing.Point(0, 0);
            this.infoActionPanel.Name = "infoActionPanel";
            this.infoActionPanel.Size = new System.Drawing.Size(602, 40);
            this.infoActionPanel.TabIndex = 0;
            // 
            // cancelButton
            // 
            this.cancelButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(318, 10);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 5;
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // createLetterButton
            // 
            this.createLetterButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.createLetterButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.createLetterButton.Location = new System.Drawing.Point(217, 10);
            this.createLetterButton.Name = "createLetterButton";
            this.createLetterButton.Size = new System.Drawing.Size(95, 23);
            this.createLetterButton.TabIndex = 4;
            this.createLetterButton.UseVisualStyleBackColor = true;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Location = new System.Drawing.Point(32, 15);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 13);
            this.requiredLabel.TabIndex = 3;
            // 
            // requiredImg
            // 
            this.requiredImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImg.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.Location = new System.Drawing.Point(12, 16);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(10, 10);
            this.requiredImg.TabIndex = 2;
            this.requiredImg.TabStop = false;
            // 
            // LetterInfoActionUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.infoActionPanel);
            this.Name = "LetterInfoActionUI";
            this.Size = new System.Drawing.Size(602, 40);
            this.infoActionPanel.ResumeLayout(false);
            this.infoActionPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel infoActionPanel;
        private System.Windows.Forms.PictureBox requiredImg;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button createLetterButton;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
