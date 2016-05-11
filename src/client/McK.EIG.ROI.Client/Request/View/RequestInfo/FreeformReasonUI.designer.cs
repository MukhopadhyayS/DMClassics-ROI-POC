namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    partial class FreeformReasonUI
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
            this.reasonCheckBox = new System.Windows.Forms.CheckBox();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.reasonTextBox = new System.Windows.Forms.TextBox();
            this.deleteFreeformReasonImg = new System.Windows.Forms.PictureBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.deleteFreeformReasonImg)).BeginInit();
            this.SuspendLayout();
            // 
            // reasonCheckBox
            // 
            this.reasonCheckBox.AutoSize = true;
            this.reasonCheckBox.Location = new System.Drawing.Point(0, 6);
            this.reasonCheckBox.Name = "reasonCheckBox";
            this.reasonCheckBox.Size = new System.Drawing.Size(15, 14);
            this.reasonCheckBox.TabIndex = 0;
            this.reasonCheckBox.UseVisualStyleBackColor = true;
            this.reasonCheckBox.CheckedChanged += new System.EventHandler(this.reasonCheckBox_CheckedChanged);
            // 
            // requiredImage
            // 
            this.requiredImage.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImage.Location = new System.Drawing.Point(15, 8);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(9, 10);
            this.requiredImage.TabIndex = 49;
            this.requiredImage.TabStop = false;
            // 
            // reasonTextBox
            // 
            this.reasonTextBox.Location = new System.Drawing.Point(31, 3);
            this.reasonTextBox.Name = "reasonTextBox";
            this.reasonTextBox.Size = new System.Drawing.Size(279, 20);
            this.reasonTextBox.TabIndex = 50;
            this.reasonTextBox.TextChanged += new System.EventHandler(this.reasonTextBox_TextChanged);
            // 
            // deleteFreeformReasonImg
            // 
            this.deleteFreeformReasonImg.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.deleteFreeFormReason;
            this.deleteFreeformReasonImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.deleteFreeformReasonImg.Location = new System.Drawing.Point(323, 4);
            this.deleteFreeformReasonImg.Name = "deleteFreeformReasonImg";
            this.deleteFreeformReasonImg.Size = new System.Drawing.Size(16, 16);
            this.deleteFreeformReasonImg.TabIndex = 51;
            this.deleteFreeformReasonImg.TabStop = false;
            this.deleteFreeformReasonImg.Click += new System.EventHandler(this.deleteFreeformReasonImg_Click);
            // 
            // FreeformReasonUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.deleteFreeformReasonImg);
            this.Controls.Add(this.reasonTextBox);
            this.Controls.Add(this.requiredImage);
            this.Controls.Add(this.reasonCheckBox);
            this.Name = "FreeformReasonUI";
            this.Size = new System.Drawing.Size(343, 26);
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.deleteFreeformReasonImg)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.CheckBox reasonCheckBox;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.TextBox reasonTextBox;
        private System.Windows.Forms.PictureBox deleteFreeformReasonImg;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
