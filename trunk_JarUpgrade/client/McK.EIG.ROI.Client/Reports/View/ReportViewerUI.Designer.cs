namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ReportViewerUI
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
            this.reportViewerPanel = new System.Windows.Forms.Panel();
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.reportTitleLabel = new System.Windows.Forms.Label();
            this.crystalReportViewer = new CrystalDecisions.Windows.Forms.CrystalReportViewer();
            this.topPanel = new System.Windows.Forms.Panel();
            this.separaterLine = new System.Windows.Forms.GroupBox();
            this.filterComboBox = new System.Windows.Forms.ComboBox();
            this.viewByLabel = new System.Windows.Forms.Label();
            this.reportViewerPanel.SuspendLayout();
            this.tableLayoutPanel.SuspendLayout();
            this.topPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // reportViewerPanel
            // 
            this.reportViewerPanel.Controls.Add(this.tableLayoutPanel);
            this.reportViewerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reportViewerPanel.Location = new System.Drawing.Point(0, 37);
            this.reportViewerPanel.Name = "reportViewerPanel";
            this.reportViewerPanel.Size = new System.Drawing.Size(691, 593);
            this.reportViewerPanel.TabIndex = 1;
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(227)))), ((int)(((byte)(232)))), ((int)(((byte)(248)))));
            this.tableLayoutPanel.ColumnCount = 1;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel.Controls.Add(this.reportTitleLabel, 0, 0);
            this.tableLayoutPanel.Controls.Add(this.crystalReportViewer, 0, 1);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 2;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 5.227656F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 94.77235F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(691, 593);
            this.tableLayoutPanel.TabIndex = 2;
            // 
            // reportTitleLabel
            // 
            this.reportTitleLabel.AutoSize = true;
            this.reportTitleLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(227)))), ((int)(((byte)(232)))), ((int)(((byte)(248)))));
            this.reportTitleLabel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.reportTitleLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportTitleLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(77)))), ((int)(((byte)(111)))), ((int)(((byte)(161)))));
            this.reportTitleLabel.Location = new System.Drawing.Point(3, 15);
            this.reportTitleLabel.Name = "reportTitleLabel";
            this.reportTitleLabel.Size = new System.Drawing.Size(685, 15);
            this.reportTitleLabel.TabIndex = 2;
            this.reportTitleLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // crystalReportViewer
            // 
            this.crystalReportViewer.ActiveViewIndex = -1;
            this.crystalReportViewer.AutoValidate = System.Windows.Forms.AutoValidate.EnablePreventFocusChange;
            this.crystalReportViewer.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.crystalReportViewer.Cursor = System.Windows.Forms.Cursors.Default;
            this.crystalReportViewer.DisplayBackgroundEdge = false;
            this.crystalReportViewer.DisplayStatusBar = false;
            this.crystalReportViewer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.crystalReportViewer.EnableDrillDown = false;
            this.crystalReportViewer.Location = new System.Drawing.Point(3, 33);
            this.crystalReportViewer.Name = "crystalReportViewer";
            this.crystalReportViewer.ShowCloseButton = false;
            this.crystalReportViewer.ShowExportButton = false;
            this.crystalReportViewer.ShowGotoPageButton = false;
            this.crystalReportViewer.ShowGroupTreeButton = false;
            this.crystalReportViewer.ShowParameterPanelButton = false;
            this.crystalReportViewer.ShowPrintButton = false;
            this.crystalReportViewer.ShowRefreshButton = false;
            this.crystalReportViewer.ShowTextSearchButton = false;
            this.crystalReportViewer.ShowZoomButton = false;
            this.crystalReportViewer.Size = new System.Drawing.Size(685, 557);
            this.crystalReportViewer.TabIndex = 1;
            this.crystalReportViewer.TabStop = false;
            this.crystalReportViewer.ClickPage += new CrystalDecisions.Windows.Forms.PageMouseEventHandler(this.crystalReportViewer_ClickPage);
            // 
            // topPanel
            // 
            this.topPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(227)))), ((int)(((byte)(232)))), ((int)(((byte)(248)))));
            this.topPanel.Controls.Add(this.separaterLine);
            this.topPanel.Controls.Add(this.filterComboBox);
            this.topPanel.Controls.Add(this.viewByLabel);
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.topPanel.Location = new System.Drawing.Point(0, 0);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(691, 37);
            this.topPanel.TabIndex = 0;
            // 
            // separaterLine
            // 
            this.separaterLine.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.separaterLine.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(147)))), ((int)(((byte)(181)))), ((int)(((byte)(220)))));
            this.separaterLine.Location = new System.Drawing.Point(3, 35);
            this.separaterLine.Name = "separaterLine";
            this.separaterLine.Size = new System.Drawing.Size(688, 2);
            this.separaterLine.TabIndex = 6;
            this.separaterLine.TabStop = false;
            // 
            // filterComboBox
            // 
            this.filterComboBox.Dock = System.Windows.Forms.DockStyle.Right;
            this.filterComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.filterComboBox.FormattingEnabled = true;
            this.filterComboBox.Location = new System.Drawing.Point(369, 0);
            this.filterComboBox.Name = "filterComboBox";
            this.filterComboBox.Size = new System.Drawing.Size(322, 23);
            this.filterComboBox.TabIndex = 0;
            // 
            // viewByLabel
            // 
            this.viewByLabel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.viewByLabel.AutoSize = true;
            this.viewByLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.viewByLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(77)))), ((int)(((byte)(111)))), ((int)(((byte)(161)))));
            this.viewByLabel.Location = new System.Drawing.Point(293, 3);
            this.viewByLabel.Name = "viewByLabel";
            this.viewByLabel.Size = new System.Drawing.Size(0, 15);
            this.viewByLabel.TabIndex = 2;
            this.viewByLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // ReportViewerUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.reportViewerPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ReportViewerUI";
            this.Size = new System.Drawing.Size(691, 630);
            this.reportViewerPanel.ResumeLayout(false);
            this.tableLayoutPanel.ResumeLayout(false);
            this.tableLayoutPanel.PerformLayout();
            this.topPanel.ResumeLayout(false);
            this.topPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private ReportFooterUI reportFooterUI;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.ComboBox filterComboBox;
        private System.Windows.Forms.Label viewByLabel;
        private System.Windows.Forms.Panel reportViewerPanel;
    
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.GroupBox separaterLine;
        private System.Windows.Forms.Label reportTitleLabel;
        private CrystalDecisions.Windows.Forms.CrystalReportViewer crystalReportViewer;
    }
}
