namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    partial class SelectRequestorUI
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
            this.findRequestorTabPage = new System.Windows.Forms.TabPage();
            this.requestorInfoTabPage = new System.Windows.Forms.TabPage();
            this.topPanel = new System.Windows.Forms.Panel();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.selectRequestorButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.requestorProfileTabControl.SuspendLayout();
            this.bottomPanel.SuspendLayout();
            this.footerPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // requestorProfileTabControl
            // 
            this.requestorProfileTabControl.Controls.Add(this.findRequestorTabPage);
            this.requestorProfileTabControl.Controls.Add(this.requestorInfoTabPage);
            this.requestorProfileTabControl.Location = new System.Drawing.Point(3, 3);
            this.requestorProfileTabControl.Margin = new System.Windows.Forms.Padding(0);
            this.requestorProfileTabControl.Name = "requestorProfileTabControl";
            this.requestorProfileTabControl.SelectedIndex = 0;
            this.requestorProfileTabControl.Size = new System.Drawing.Size(913, 569);
            this.requestorProfileTabControl.TabIndex = 0;
            this.requestorProfileTabControl.Selecting += new System.Windows.Forms.TabControlCancelEventHandler(this.requestorProfileTabControl_Selecting);
            this.requestorProfileTabControl.SelectedIndexChanged += new System.EventHandler(this.requestorProfileTabControl_SelectedIndexChanged);
            // 
            // findRequestorTabPage
            // 
            this.findRequestorTabPage.AutoScroll = true;
            this.findRequestorTabPage.BackColor = System.Drawing.Color.White;
            this.findRequestorTabPage.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.findRequestorTabPage.Location = new System.Drawing.Point(4, 22);
            this.findRequestorTabPage.Name = "findRequestorTabPage";
            this.findRequestorTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.findRequestorTabPage.Size = new System.Drawing.Size(905, 543);
            this.findRequestorTabPage.TabIndex = 0;
            this.findRequestorTabPage.Enter += new System.EventHandler(this.findRequestorTabPage_Enter);
            // 
            // requestorInfoTabPage
            // 
            this.requestorInfoTabPage.AutoScroll = true;
            this.requestorInfoTabPage.AutoScrollMargin = new System.Drawing.Size(200, 300);
            this.requestorInfoTabPage.AutoScrollMinSize = new System.Drawing.Size(200, 300);
            this.requestorInfoTabPage.BackColor = System.Drawing.Color.White;
            this.requestorInfoTabPage.Location = new System.Drawing.Point(4, 22);
            this.requestorInfoTabPage.Name = "requestorInfoTabPage";
            this.requestorInfoTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.requestorInfoTabPage.Size = new System.Drawing.Size(905, 543);
            this.requestorInfoTabPage.TabIndex = 1;
            // 
            // topPanel
            // 
            this.topPanel.Location = new System.Drawing.Point(0, 3);
            this.topPanel.Name = "topPanel";
            this.topPanel.Padding = new System.Windows.Forms.Padding(5);
            this.topPanel.Size = new System.Drawing.Size(919, 75);
            this.topPanel.TabIndex = 1;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.requestorProfileTabControl);
            this.bottomPanel.Location = new System.Drawing.Point(0, 81);
            this.bottomPanel.Margin = new System.Windows.Forms.Padding(0);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(919, 574);
            this.bottomPanel.TabIndex = 2;
            // 
            // footerPanel
            // 
            this.footerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.footerPanel.Controls.Add(this.cancelButton);
            this.footerPanel.Controls.Add(this.selectRequestorButton);
            this.footerPanel.Location = new System.Drawing.Point(0, 658);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(919, 37);
            this.footerPanel.TabIndex = 3;
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(514, 5);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 27);
            this.cancelButton.TabIndex = 1;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // selectRequestorButton
            // 
            this.selectRequestorButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.selectRequestorButton.Location = new System.Drawing.Point(329, 5);
            this.selectRequestorButton.Name = "selectRequestorButton";
            this.selectRequestorButton.Size = new System.Drawing.Size(179, 27);
            this.selectRequestorButton.TabIndex = 0;
            this.selectRequestorButton.UseVisualStyleBackColor = true;
            this.selectRequestorButton.Click += new System.EventHandler(this.selectRequestorButton_Click);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // SelectRequestorUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.bottomPanel);
            this.Controls.Add(this.topPanel);
            this.Name = "SelectRequestorUI";
            this.Padding = new System.Windows.Forms.Padding(5);
            this.Size = new System.Drawing.Size(919, 695);
            this.requestorProfileTabControl.ResumeLayout(false);
            this.bottomPanel.ResumeLayout(false);
            this.footerPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        internal System.Windows.Forms.TabControl requestorProfileTabControl;
        private System.Windows.Forms.TabPage findRequestorTabPage;
        private System.Windows.Forms.TabPage requestorInfoTabPage;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button selectRequestorButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.ErrorProvider errorProvider;
    }
}
