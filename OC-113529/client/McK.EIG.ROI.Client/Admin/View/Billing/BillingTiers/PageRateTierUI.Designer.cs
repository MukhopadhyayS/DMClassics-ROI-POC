namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{
    partial class PageRateTierUI
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(PageRateTierUI));
            this.pageLabel = new System.Windows.Forms.Label();
            this.fromPageTextBox = new System.Windows.Forms.TextBox();
            this.throughPageLabel = new System.Windows.Forms.Label();
            this.toPageTextBox = new System.Windows.Forms.TextBox();
            this.dollarLabel = new System.Windows.Forms.Label();
            this.pricePerPageTextBox = new System.Windows.Forms.TextBox();
            this.deleteImg = new System.Windows.Forms.PictureBox();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.perPageLabel = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.deleteImg)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // pageLabel
            // 
            this.pageLabel.AutoSize = true;
            this.pageLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.pageLabel.Location = new System.Drawing.Point(14, 3);
            this.pageLabel.Name = "pageLabel";
            this.pageLabel.Size = new System.Drawing.Size(0, 15);
            this.pageLabel.TabIndex = 1;
            // 
            // fromPageTextBox
            // 
            this.fromPageTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fromPageTextBox.Location = new System.Drawing.Point(45, 2);
            this.fromPageTextBox.MaxLength = 4;
            this.fromPageTextBox.Name = "fromPageTextBox";
            this.fromPageTextBox.Size = new System.Drawing.Size(35, 21);
            this.fromPageTextBox.TabIndex = 1;
            this.fromPageTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.OnKeyPress);
            // 
            // throughPageLabel
            // 
            this.throughPageLabel.AutoSize = true;
            this.throughPageLabel.Font = new System.Drawing.Font("Arial", 9F);
            this.throughPageLabel.Location = new System.Drawing.Point(90, 5);
            this.throughPageLabel.Name = "throughPageLabel";
            this.throughPageLabel.Size = new System.Drawing.Size(0, 15);
            this.throughPageLabel.TabIndex = 3;
            // 
            // toPageTextBox
            // 
            this.toPageTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.toPageTextBox.Location = new System.Drawing.Point(159, 3);
            this.toPageTextBox.MaxLength = 4;
            this.toPageTextBox.Name = "toPageTextBox";
            this.toPageTextBox.Size = new System.Drawing.Size(35, 21);
            this.toPageTextBox.TabIndex = 2;
            this.toPageTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.OnKeyPress);
            // 
            // dollarLabel
            // 
            this.dollarLabel.AutoSize = true;
            this.dollarLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dollarLabel.Location = new System.Drawing.Point(205, 6);
            this.dollarLabel.Name = "dollarLabel";
            this.dollarLabel.Size = new System.Drawing.Size(0, 15);
            this.dollarLabel.TabIndex = 5;
            // 
            // pricePerPageTextBox
            // 
            this.pricePerPageTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.pricePerPageTextBox.Location = new System.Drawing.Point(228, 3);
            this.pricePerPageTextBox.MaxLength = 5;
            this.pricePerPageTextBox.Name = "pricePerPageTextBox";
            this.pricePerPageTextBox.Size = new System.Drawing.Size(40, 21);
            this.pricePerPageTextBox.TabIndex = 3;
            this.pricePerPageTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.pricePerPageTextBox_KeyPress);
            // 
            // deleteImg
            // 
            this.deleteImg.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("deleteImg.BackgroundImage")));
            this.deleteImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.deleteImg.Location = new System.Drawing.Point(326, 5);
            this.deleteImg.Name = "deleteImg";
            this.deleteImg.Size = new System.Drawing.Size(16, 16);
            this.deleteImg.TabIndex = 7;
            this.deleteImg.TabStop = false;
            this.deleteImg.Click += new System.EventHandler(this.DeleteImg_Click);
            // 
            // requiredImg
            // 
            this.requiredImg.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.ErrorImage = null;
            this.requiredImg.Location = new System.Drawing.Point(5, 5);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(9, 9);
            this.requiredImg.TabIndex = 0;
            this.requiredImg.TabStop = false;
            // 
            // perPageLabel
            // 
            this.perPageLabel.AutoSize = true;
            this.perPageLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.perPageLabel.Location = new System.Drawing.Point(278, 5);
            this.perPageLabel.Name = "perPageLabel";
            this.perPageLabel.Size = new System.Drawing.Size(0, 15);
            this.perPageLabel.TabIndex = 8;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // PageRateTierUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.perPageLabel);
            this.Controls.Add(this.deleteImg);
            this.Controls.Add(this.pricePerPageTextBox);
            this.Controls.Add(this.dollarLabel);
            this.Controls.Add(this.toPageTextBox);
            this.Controls.Add(this.throughPageLabel);
            this.Controls.Add(this.fromPageTextBox);
            this.Controls.Add(this.pageLabel);
            this.Controls.Add(this.requiredImg);
            this.Name = "PageRateTierUI";
            this.Size = new System.Drawing.Size(343, 26);
            ((System.ComponentModel.ISupportInitialize)(this.deleteImg)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox requiredImg;
        private System.Windows.Forms.Label pageLabel;
        private System.Windows.Forms.TextBox fromPageTextBox;
        private System.Windows.Forms.Label throughPageLabel;
        private System.Windows.Forms.TextBox toPageTextBox;
        private System.Windows.Forms.Label dollarLabel;
        private System.Windows.Forms.TextBox pricePerPageTextBox;
        private System.Windows.Forms.PictureBox deleteImg;
        private System.Windows.Forms.Label perPageLabel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.ErrorProvider errorProvider;
    }
}
