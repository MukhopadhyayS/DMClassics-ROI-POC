namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class ROIViewer
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ROIViewer));
            this.viewerToolStrip = new System.Windows.Forms.ToolStrip();
            this.printButton = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.faxButton = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.saveAsFileButton = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator3 = new System.Windows.Forms.ToolStripSeparator();
            this.emailButton = new System.Windows.Forms.ToolStripButton();
            this.continueButton = new System.Windows.Forms.ToolStripButton();
            this.cancelButton = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator4 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripSeparator5 = new System.Windows.Forms.ToolStripSeparator();
            this.documentViewer = new System.Windows.Forms.WebBrowser();
            this.axAcroPDF = new AxAcroPDFLib.AxAcroPDF();
            this.viewerToolStrip.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.axAcroPDF)).BeginInit();
            this.SuspendLayout();
            // 
            // viewerToolStrip
            // 
            this.viewerToolStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.printButton,
            this.toolStripSeparator1,
            this.faxButton,
            this.toolStripSeparator2,
            this.saveAsFileButton,
            this.toolStripSeparator3,
            this.emailButton,
            this.continueButton,
            this.cancelButton,
            this.toolStripSeparator4,
            this.toolStripSeparator5});
            this.viewerToolStrip.Location = new System.Drawing.Point(0, 0);
            this.viewerToolStrip.Name = "viewerToolStrip";
            this.viewerToolStrip.Size = new System.Drawing.Size(896, 25);
            this.viewerToolStrip.TabIndex = 0;
            // 
            // printButton
            // 
            this.printButton.AutoToolTip = false;
            this.printButton.Image = global::McK.EIG.ROI.Client.Resources.images.print;
            this.printButton.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.printButton.Name = "printButton";
            this.printButton.Size = new System.Drawing.Size(23, 22);
            this.printButton.Click += new System.EventHandler(this.printButton_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // faxButton
            // 
            this.faxButton.AutoToolTip = false;
            this.faxButton.Image = global::McK.EIG.ROI.Client.Resources.images.fax;
            this.faxButton.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.faxButton.Name = "faxButton";
            this.faxButton.Size = new System.Drawing.Size(23, 22);
            this.faxButton.Click += new System.EventHandler(this.faxButton_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(6, 25);
            // 
            // saveAsFileButton
            // 
            this.saveAsFileButton.AutoToolTip = false;
            this.saveAsFileButton.Image = global::McK.EIG.ROI.Client.Resources.images.save_as_file;
            this.saveAsFileButton.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.saveAsFileButton.Name = "saveAsFileButton";
            this.saveAsFileButton.Size = new System.Drawing.Size(23, 22);
            this.saveAsFileButton.Click += new System.EventHandler(this.saveAsFileButton_Click);
            // 
            // toolStripSeparator3
            // 
            this.toolStripSeparator3.Name = "toolStripSeparator3";
            this.toolStripSeparator3.Size = new System.Drawing.Size(6, 25);
            // 
            // emailButton
            // 
            this.emailButton.AutoToolTip = false;
            this.emailButton.Image = global::McK.EIG.ROI.Client.Resources.images.email;
            this.emailButton.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.emailButton.Name = "emailButton";
            this.emailButton.Size = new System.Drawing.Size(23, 22);
            this.emailButton.Click += new System.EventHandler(this.emailButton_Click);
            // 
            // continueButton
            // 
            this.continueButton.AutoToolTip = false;
            this.continueButton.Image = global::McK.EIG.ROI.Client.Resources.images.Continue;
            this.continueButton.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.continueButton.Name = "continueButton";
            this.continueButton.Size = new System.Drawing.Size(23, 22);
            this.continueButton.Click += new System.EventHandler(this.continueButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.AutoToolTip = false;
            this.cancelButton.Image = global::McK.EIG.ROI.Client.Resources.images.cancel_pre_bill;
            this.cancelButton.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(23, 22);
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // toolStripSeparator4
            // 
            this.toolStripSeparator4.Name = "toolStripSeparator4";
            this.toolStripSeparator4.Size = new System.Drawing.Size(6, 25);
            // 
            // toolStripSeparator5
            // 
            this.toolStripSeparator5.Name = "toolStripSeparator5";
            this.toolStripSeparator5.Size = new System.Drawing.Size(6, 25);
            // 
            // documentViewer
            // 
            this.documentViewer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.documentViewer.IsWebBrowserContextMenuEnabled = false;
            this.documentViewer.Location = new System.Drawing.Point(0, 25);
            this.documentViewer.MinimumSize = new System.Drawing.Size(20, 20);
            this.documentViewer.Name = "documentViewer";
            this.documentViewer.Size = new System.Drawing.Size(896, 658);
            this.documentViewer.TabIndex = 1;
            // 
            // axAcroPDF
            // 
            this.axAcroPDF.Enabled = true;
            this.axAcroPDF.Location = new System.Drawing.Point(0, 25);
            this.axAcroPDF.Name = "axAcroPDF";
            this.axAcroPDF.OcxState = ((System.Windows.Forms.AxHost.State)(resources.GetObject("axAcroPDF.OcxState")));
            this.axAcroPDF.Size = new System.Drawing.Size(896, 658);
            this.axAcroPDF.TabIndex = 2;
            // 
            // ROIViewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.axAcroPDF);
            this.Controls.Add(this.documentViewer);
            this.Controls.Add(this.viewerToolStrip);
            this.Name = "ROIViewer";
            this.Size = new System.Drawing.Size(896, 683);
            this.viewerToolStrip.ResumeLayout(false);
            this.viewerToolStrip.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.axAcroPDF)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ToolStrip viewerToolStrip;
        private System.Windows.Forms.ToolStripButton printButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton faxButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripButton saveAsFileButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator3;
        private System.Windows.Forms.ToolStripButton cancelButton;
        private System.Windows.Forms.ToolStripButton continueButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator4;
        private System.Windows.Forms.WebBrowser documentViewer;
        private System.Windows.Forms.ToolStripButton emailButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator5;
        private AxAcroPDFLib.AxAcroPDF axAcroPDF;




    }
}
