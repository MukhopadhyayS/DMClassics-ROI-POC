namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ReportFooterUI
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
            this.footerPanel = new System.Windows.Forms.TableLayoutPanel();
            this.leftPanel = new System.Windows.Forms.Panel();
            this.rightTableLayOutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.printButton = new System.Windows.Forms.Button();
            this.exportButton = new System.Windows.Forms.Button();
            this.printPreviewButton = new System.Windows.Forms.Button();
            this.footerPanel.SuspendLayout();
            this.rightTableLayOutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // footerPanel
            // 
            this.footerPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.footerPanel.ColumnCount = 2;
            this.footerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 52.21374F));
            this.footerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 47.78626F));
            this.footerPanel.Controls.Add(this.leftPanel, 0, 0);
            this.footerPanel.Controls.Add(this.rightTableLayOutPanel, 1, 0);
            this.footerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.footerPanel.Location = new System.Drawing.Point(0, 0);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.RowCount = 1;
            this.footerPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.footerPanel.Size = new System.Drawing.Size(655, 40);
            this.footerPanel.TabIndex = 1;
            // 
            // leftPanel
            // 
            this.leftPanel.BackColor = System.Drawing.Color.Transparent;
            this.leftPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.leftPanel.Location = new System.Drawing.Point(3, 3);
            this.leftPanel.Name = "leftPanel";
            this.leftPanel.Size = new System.Drawing.Size(336, 34);
            this.leftPanel.TabIndex = 0;
            // 
            // rightTableLayOutPanel
            // 
            this.rightTableLayOutPanel.ColumnCount = 3;
            this.rightTableLayOutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.rightTableLayOutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 103F));
            this.rightTableLayOutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 102F));
            this.rightTableLayOutPanel.Controls.Add(this.printButton, 1, 0);
            this.rightTableLayOutPanel.Controls.Add(this.exportButton, 2, 0);
            this.rightTableLayOutPanel.Controls.Add(this.printPreviewButton, 0, 0);
            this.rightTableLayOutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rightTableLayOutPanel.Location = new System.Drawing.Point(345, 3);
            this.rightTableLayOutPanel.Name = "rightTableLayOutPanel";
            this.rightTableLayOutPanel.RowCount = 1;
            this.rightTableLayOutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.rightTableLayOutPanel.Size = new System.Drawing.Size(307, 34);
            this.rightTableLayOutPanel.TabIndex = 1;
            // 
            // printButton
            // 
            this.printButton.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.printButton.BackColor = System.Drawing.Color.White;
            this.printButton.Enabled = false;
            this.printButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.printButton.Location = new System.Drawing.Point(107, 4);
            this.printButton.Name = "printButton";
            this.printButton.Size = new System.Drawing.Size(95, 25);
            this.printButton.TabIndex = 1;
            this.printButton.UseVisualStyleBackColor = false;
            // 
            // exportButton
            // 
            this.exportButton.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.exportButton.BackColor = System.Drawing.Color.White;
            this.exportButton.Enabled = false;
            this.exportButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.exportButton.Location = new System.Drawing.Point(209, 4);
            this.exportButton.Name = "exportButton";
            this.exportButton.Size = new System.Drawing.Size(95, 25);
            this.exportButton.TabIndex = 2;
            this.exportButton.UseVisualStyleBackColor = false;
            // 
            // printPreviewButton
            // 
            this.printPreviewButton.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.printPreviewButton.BackColor = System.Drawing.Color.White;
            this.printPreviewButton.Enabled = false;
            this.printPreviewButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.printPreviewButton.Location = new System.Drawing.Point(4, 4);
            this.printPreviewButton.Name = "printPreviewButton";
            this.printPreviewButton.Size = new System.Drawing.Size(95, 25);
            this.printPreviewButton.TabIndex = 0;
            this.printPreviewButton.UseVisualStyleBackColor = false;
            // 
            // ReportFooterUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.footerPanel);
            this.Name = "ReportFooterUI";
            this.Size = new System.Drawing.Size(655, 40);
            this.footerPanel.ResumeLayout(false);
            this.rightTableLayOutPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel footerPanel;
        private System.Windows.Forms.Panel leftPanel;
        internal System.Windows.Forms.Button exportButton;
        internal System.Windows.Forms.Button printButton;
        internal System.Windows.Forms.Button printPreviewButton;
        private System.Windows.Forms.TableLayoutPanel rightTableLayOutPanel;
    }
}
