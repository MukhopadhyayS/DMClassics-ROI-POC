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
            this.components = new System.ComponentModel.Container();
            this.documentViewer = new System.Windows.Forms.WebBrowser();
            this.pdfPageView = new O2S.Components.PDFView4NET.PDFPageView();
            this.pdfDocument = new O2S.Components.PDFView4NET.PDFDocument(this.components);
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
            this.viewerToolStrip = new System.Windows.Forms.ToolStrip();
            this.viewerToolStrip.SuspendLayout();
            this.SuspendLayout();
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
            // pdfPageView
            // 
            this.pdfPageView.AutoScroll = true;
            this.pdfPageView.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pdfPageView.DefaultEllipseAnnotationBorderWidth = 1D;
            this.pdfPageView.DefaultInkAnnotationWidth = 1D;
            this.pdfPageView.DefaultRectangleAnnotationBorderWidth = 1D;
            this.pdfPageView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pdfPageView.Document = this.pdfDocument;
            this.pdfPageView.DownscaleLargeImages = false;
            this.pdfPageView.EnableRepeatedKeys = false;
            this.pdfPageView.Location = new System.Drawing.Point(0, 25);
            this.pdfPageView.Name = "pdfPageView";
            this.pdfPageView.PageDisplayLayout = O2S.Components.PDFView4NET.PDFPageDisplayLayout.OneColumn;
            this.pdfPageView.PageNumber = 0;
            this.pdfPageView.RenderingProgressColor = System.Drawing.Color.Empty;
            this.pdfPageView.RenderingProgressDisplay = O2S.Components.PDFView4NET.PDFRenderingProgressDisplayMode.None;
            this.pdfPageView.RequiredFormFieldHighlightColor = System.Drawing.Color.Empty;
            this.pdfPageView.ScrollPosition = new System.Drawing.Point(0, 0);
            this.pdfPageView.Size = new System.Drawing.Size(1219, 797);
            this.pdfPageView.SubstituteFonts = null;
            this.pdfPageView.TabIndex = 1;
            this.pdfPageView.WorkMode = O2S.Components.PDFView4NET.UserInteractiveWorkMode.None;
            // 
            // pdfDocument
            // 
            this.pdfDocument.Metadata = null;
            this.pdfDocument.PageLayout = O2S.Components.PDFView4NET.PDFPageLayout.SinglePage;
            this.pdfDocument.PageMode = O2S.Components.PDFView4NET.PDFPageMode.UseNone;
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
            // ROIViewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.Controls.Add(this.pdfPageView);
            this.Controls.Add(this.documentViewer);
            this.Controls.Add(this.viewerToolStrip);
            this.Name = "ROIViewer";
            this.Size = new System.Drawing.Size(1219, 822);
            this.viewerToolStrip.ResumeLayout(false);
            this.viewerToolStrip.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.WebBrowser documentViewer;
        private O2S.Components.PDFView4NET.PDFPageView pdfPageView;
        private System.Windows.Forms.ToolStripButton printButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripButton faxButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripButton saveAsFileButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator3;
        private System.Windows.Forms.ToolStripButton emailButton;
        private System.Windows.Forms.ToolStripButton continueButton;
        private System.Windows.Forms.ToolStripButton cancelButton;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator4;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator5;
        private System.Windows.Forms.ToolStrip viewerToolStrip;
        private O2S.Components.PDFView4NET.PDFDocument pdfDocument;




    }
}
