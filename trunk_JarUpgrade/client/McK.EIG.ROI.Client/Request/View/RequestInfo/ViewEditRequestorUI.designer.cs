namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    partial class ViewEditRequestorUI
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
            this.requestorProfileTabControl = new System.Windows.Forms.TabControl();
            this.requestorInfoTabPage = new System.Windows.Forms.TabPage();
            this.topPanel = new System.Windows.Forms.Panel();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.footerPanel = new System.Windows.Forms.Panel();
            this.revertButton = new System.Windows.Forms.Button();
            this.closeButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.requestorProfileTabControl.SuspendLayout();
            this.bottomPanel.SuspendLayout();
            this.footerPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            this.SuspendLayout();
            // 
            // requestorProfileTabControl
            // 
            this.requestorProfileTabControl.Controls.Add(this.requestorInfoTabPage);
            this.requestorProfileTabControl.Location = new System.Drawing.Point(3, 3);
            this.requestorProfileTabControl.Margin = new System.Windows.Forms.Padding(0);
            this.requestorProfileTabControl.Name = "requestorProfileTabControl";
            this.requestorProfileTabControl.SelectedIndex = 0;
            this.requestorProfileTabControl.Size = new System.Drawing.Size(913, 563);
            this.requestorProfileTabControl.TabIndex = 0;
            // 
            // requestorInfoTabPage
            // 
            this.requestorInfoTabPage.BackColor = System.Drawing.Color.White;
            this.requestorInfoTabPage.Location = new System.Drawing.Point(4, 24);
            this.requestorInfoTabPage.Name = "requestorInfoTabPage";
            this.requestorInfoTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.requestorInfoTabPage.Size = new System.Drawing.Size(905, 535);
            this.requestorInfoTabPage.TabIndex = 1;
            // 
            // topPanel
            // 
            this.topPanel.Location = new System.Drawing.Point(0, 3);
            this.topPanel.Name = "topPanel";
            this.topPanel.Padding = new System.Windows.Forms.Padding(5);
            this.topPanel.Size = new System.Drawing.Size(907, 58);
            this.topPanel.TabIndex = 1;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.requestorProfileTabControl);
            this.bottomPanel.Location = new System.Drawing.Point(0, 67);
            this.bottomPanel.Margin = new System.Windows.Forms.Padding(0);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(919, 566);
            this.bottomPanel.TabIndex = 2;
            // 
            // footerPanel
            // 
            this.footerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.footerPanel.Controls.Add(this.requiredLabel);
            this.footerPanel.Controls.Add(this.requiredImg);
            this.footerPanel.Controls.Add(this.revertButton);
            this.footerPanel.Controls.Add(this.closeButton);
            this.footerPanel.Controls.Add(this.saveButton);
            this.footerPanel.Location = new System.Drawing.Point(0, 636);
            this.footerPanel.Margin = new System.Windows.Forms.Padding(0);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(919, 43);
            this.footerPanel.TabIndex = 4;
            // 
            // revertButton
            // 
            this.revertButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.revertButton.Location = new System.Drawing.Point(420, 9);
            this.revertButton.Name = "revertButton";
            this.revertButton.Size = new System.Drawing.Size(87, 27);
            this.revertButton.TabIndex = 2;
            this.revertButton.UseVisualStyleBackColor = true;
            this.revertButton.Click += new System.EventHandler(this.revertButton_Click);
            // 
            // closeButton
            // 
            this.closeButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.closeButton.Location = new System.Drawing.Point(515, 9);
            this.closeButton.Name = "closeButton";
            this.closeButton.Size = new System.Drawing.Size(87, 27);
            this.closeButton.TabIndex = 1;
            this.closeButton.UseVisualStyleBackColor = true;
            this.closeButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // saveButton
            // 
            this.saveButton.AutoSize = true;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(326, 9);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(87, 27);
            this.saveButton.TabIndex = 0;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveRequestorButton_Click);
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(16, 17);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 4;
            // 
            // requiredImg
            // 
            this.requiredImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImg.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.Location = new System.Drawing.Point(5, 19);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(13, 10);
            this.requiredImg.TabIndex = 3;
            this.requiredImg.TabStop = false;
            // 
            // ViewEditRequestorUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.bottomPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ViewEditRequestorUI";
            this.Padding = new System.Windows.Forms.Padding(6);
            this.Size = new System.Drawing.Size(919, 680);
            this.requestorProfileTabControl.ResumeLayout(false);
            this.bottomPanel.ResumeLayout(false);
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        internal System.Windows.Forms.TabControl requestorProfileTabControl;
        private System.Windows.Forms.TabPage requestorInfoTabPage;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button closeButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Button revertButton;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox requiredImg;
    }
}
