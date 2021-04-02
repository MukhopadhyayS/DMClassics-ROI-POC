namespace McK.EIG.ROI.Client.Request.View
{
    partial class FreeformUI
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
            this.freeformCheckBox = new System.Windows.Forms.CheckBox();
            this.freeformTextBox = new System.Windows.Forms.TextBox();
            this.deleteFreeformImg = new System.Windows.Forms.PictureBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.deleteFreeformImg)).BeginInit();
            this.SuspendLayout();
            // 
            // freeformCheckBox
            // 
            this.freeformCheckBox.AutoSize = true;
            this.freeformCheckBox.Location = new System.Drawing.Point(5, 3);
            this.freeformCheckBox.Name = "freeformCheckBox";
            this.freeformCheckBox.Size = new System.Drawing.Size(15, 14);
            this.freeformCheckBox.TabIndex = 0;
            this.freeformCheckBox.UseVisualStyleBackColor = true;
            this.freeformCheckBox.CheckedChanged += new System.EventHandler(this.freeformCheckBox_CheckedChanged);
            // 
            // freeformTextBox
            // 
            this.freeformTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.freeformTextBox.Location = new System.Drawing.Point(29, 1);
            this.freeformTextBox.Name = "freeformTextBox";
            this.freeformTextBox.Size = new System.Drawing.Size(526, 21);
            this.freeformTextBox.TabIndex = 51;
            this.freeformTextBox.TabStop = false;
            // 
            // deleteFreeformImg
            // 
            this.deleteFreeformImg.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.deleteFreeformImg.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.delete;
            this.deleteFreeformImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.deleteFreeformImg.Location = new System.Drawing.Point(569, 2);
            this.deleteFreeformImg.Name = "deleteFreeformImg";
            this.deleteFreeformImg.Size = new System.Drawing.Size(19, 18);
            this.deleteFreeformImg.TabIndex = 52;
            this.deleteFreeformImg.TabStop = false;
            this.deleteFreeformImg.Click += new System.EventHandler(this.deleteFreeformImg_Click);
            // 
            // FreeformUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.deleteFreeformImg);
            this.Controls.Add(this.freeformTextBox);
            this.Controls.Add(this.freeformCheckBox);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(3, 0, 3, 0);
            this.Name = "FreeformUI";
            this.Size = new System.Drawing.Size(609, 23);
            ((System.ComponentModel.ISupportInitialize)(this.deleteFreeformImg)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.CheckBox freeformCheckBox;
        private System.Windows.Forms.TextBox freeformTextBox;
        private System.Windows.Forms.PictureBox deleteFreeformImg;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
