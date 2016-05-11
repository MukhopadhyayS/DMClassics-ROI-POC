namespace McK.EIG.ROI.Client.Base.View.Common
{
    partial class AmountTextBoxClass
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
            this.dollarLabel = new System.Windows.Forms.Label();
            this.amountTextBox = new System.Windows.Forms.TextBox();
            this.alertImg = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.alertImg)).BeginInit();
            this.SuspendLayout();
            // 
            // dollarLabel
            // 
            this.dollarLabel.AutoSize = true;
            this.dollarLabel.Location = new System.Drawing.Point(3, 3);
            this.dollarLabel.Name = "dollarLabel";
            this.dollarLabel.Size = new System.Drawing.Size(13, 13);
            this.dollarLabel.TabIndex = 0;
            this.dollarLabel.Text = "$";
            // 
            // amountTextBox
            // 
            this.amountTextBox.Font = new System.Drawing.Font("Arial", 9F);
            this.amountTextBox.Location = new System.Drawing.Point(19, 0);
            this.amountTextBox.MaxLength = 9;
            this.amountTextBox.Name = "amountTextBox";
            this.amountTextBox.Size = new System.Drawing.Size(80, 21);
            this.amountTextBox.TabIndex = 1;
            this.amountTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.amountTextBox_KeyPress);
            this.amountTextBox.Enter += new System.EventHandler(this.amountTextBox_Enter);
            this.amountTextBox.Leave += new System.EventHandler(this.amountTextBox_Leave);
            // 
            // alertImg
            // 
            this.alertImg.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.nopayment;
            this.alertImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.alertImg.Location = new System.Drawing.Point(100, 1);
            this.alertImg.Name = "alertImg";
            this.alertImg.Size = new System.Drawing.Size(20, 18);
            this.alertImg.TabIndex = 2;
            this.alertImg.TabStop = false;
            this.alertImg.Visible = false;
            // 
            // AmountTextBoxClass
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.alertImg);
            this.Controls.Add(this.amountTextBox);
            this.Controls.Add(this.dollarLabel);
            this.Name = "AmountTextBoxClass";
            this.Size = new System.Drawing.Size(121, 23);
            ((System.ComponentModel.ISupportInitialize)(this.alertImg)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label dollarLabel;
        private System.Windows.Forms.TextBox amountTextBox;
        private System.Windows.Forms.PictureBox alertImg;
    }
}
