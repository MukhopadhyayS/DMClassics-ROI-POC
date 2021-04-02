namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    partial class SaveNewPatientRequestorUI
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
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveNewRequestorButton = new System.Windows.Forms.Button();
            this.requestorProfileTabControl.SuspendLayout();
            this.bottomPanel.SuspendLayout();
            this.footerPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // requestorProfileTabControl
            // 
            this.requestorProfileTabControl.Controls.Add(this.requestorInfoTabPage);
            this.requestorProfileTabControl.Location = new System.Drawing.Point(3, 3);
            this.requestorProfileTabControl.Name = "requestorProfileTabControl";
            this.requestorProfileTabControl.SelectedIndex = 0;
            this.requestorProfileTabControl.Size = new System.Drawing.Size(913, 545);
            this.requestorProfileTabControl.TabIndex = 0;
            // 
            // requestorInfoTabPage
            // 
            this.requestorInfoTabPage.AutoScroll = true;
            this.requestorInfoTabPage.BackColor = System.Drawing.Color.White;
            this.requestorInfoTabPage.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestorInfoTabPage.Location = new System.Drawing.Point(4, 22);
            this.requestorInfoTabPage.Name = "requestorInfoTabPage";
            this.requestorInfoTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.requestorInfoTabPage.Size = new System.Drawing.Size(905, 519);
            this.requestorInfoTabPage.TabIndex = 1;
            // 
            // topPanel
            // 
            this.topPanel.Location = new System.Drawing.Point(7, 3);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(907, 73);
            this.topPanel.TabIndex = 1;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.requestorProfileTabControl);
            this.bottomPanel.Location = new System.Drawing.Point(0, 82);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(919, 552);
            this.bottomPanel.TabIndex = 2;
            // 
            // footerPanel
            // 
            this.footerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.footerPanel.Controls.Add(this.cancelButton);
            this.footerPanel.Controls.Add(this.saveNewRequestorButton);
            this.footerPanel.Location = new System.Drawing.Point(0, 636);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(919, 40);
            this.footerPanel.TabIndex = 4;
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(495, 8);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 27);
            this.cancelButton.TabIndex = 1;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // saveNewRequestorButton
            // 
            this.saveNewRequestorButton.AutoSize = true;
            this.saveNewRequestorButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveNewRequestorButton.Location = new System.Drawing.Point(356, 8);
            this.saveNewRequestorButton.Name = "saveNewRequestorButton";
            this.saveNewRequestorButton.Size = new System.Drawing.Size(133, 27);
            this.saveNewRequestorButton.TabIndex = 0;
            this.saveNewRequestorButton.UseVisualStyleBackColor = true;
            this.saveNewRequestorButton.Click += new System.EventHandler(this.saveNewRequestorButton_Click);
            // 
            // SaveNewPatientRequestorUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.bottomPanel);
            this.Controls.Add(this.topPanel);
            this.Name = "SaveNewPatientRequestorUI";
            this.Padding = new System.Windows.Forms.Padding(5);
            this.Size = new System.Drawing.Size(922, 676);
            this.requestorProfileTabControl.ResumeLayout(false);
            this.bottomPanel.ResumeLayout(false);
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        internal System.Windows.Forms.TabControl requestorProfileTabControl;
        private System.Windows.Forms.TabPage requestorInfoTabPage;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveNewRequestorButton;
    }
}
