namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ReportUI
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
            this.reportTypeComboBox = new System.Windows.Forms.ComboBox();
            this.reportNameLabel = new System.Windows.Forms.Label();
            this.upperPanel = new System.Windows.Forms.Panel();
            this.separaterLine = new System.Windows.Forms.GroupBox();
            this.requiredImg1 = new System.Windows.Forms.PictureBox();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.bottomButtonPanel = new System.Windows.Forms.Panel();
            this.requiredImg2 = new System.Windows.Forms.PictureBox();
            this.runReportButton = new System.Windows.Forms.Button();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.resetButton = new System.Windows.Forms.Button();
            this.reportCriteriaPanel = new System.Windows.Forms.Panel();
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.upperPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg1)).BeginInit();
            this.bottomPanel.SuspendLayout();
            this.bottomButtonPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg2)).BeginInit();
            this.tableLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // reportTypeComboBox
            // 
            this.reportTypeComboBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.reportTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.reportTypeComboBox.FormattingEnabled = true;
            this.reportTypeComboBox.Location = new System.Drawing.Point(3, 27);
            this.reportTypeComboBox.Name = "reportTypeComboBox";
            this.reportTypeComboBox.Size = new System.Drawing.Size(288, 23);
            this.reportTypeComboBox.TabIndex = 0;
            // 
            // reportNameLabel
            // 
            this.reportNameLabel.AutoSize = true;
            this.reportNameLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.reportNameLabel.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportNameLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(77)))), ((int)(((byte)(111)))), ((int)(((byte)(161)))));
            this.reportNameLabel.Location = new System.Drawing.Point(16, 7);
            this.reportNameLabel.Name = "reportNameLabel";
            this.reportNameLabel.Size = new System.Drawing.Size(0, 16);
            this.reportNameLabel.TabIndex = 1;
            // 
            // upperPanel
            // 
            this.upperPanel.Controls.Add(this.separaterLine);
            this.upperPanel.Controls.Add(this.requiredImg1);
            this.upperPanel.Controls.Add(this.reportNameLabel);
            this.upperPanel.Controls.Add(this.reportTypeComboBox);
            this.upperPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.upperPanel.Location = new System.Drawing.Point(3, 3);
            this.upperPanel.Name = "upperPanel";
            this.upperPanel.Size = new System.Drawing.Size(294, 73);
            this.upperPanel.TabIndex = 0;
            this.upperPanel.TabStop = true;
            // 
            // separaterLine
            // 
            this.separaterLine.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.separaterLine.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(178)))), ((int)(((byte)(194)))), ((int)(((byte)(231)))));
            this.separaterLine.Location = new System.Drawing.Point(3, 61);
            this.separaterLine.Name = "separaterLine";
            this.separaterLine.Size = new System.Drawing.Size(287, 2);
            this.separaterLine.TabIndex = 5;
            this.separaterLine.TabStop = false;
            // 
            // requiredImg1
            // 
            this.requiredImg1.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg1.Location = new System.Drawing.Point(3, 7);
            this.requiredImg1.Name = "requiredImg1";
            this.requiredImg1.Size = new System.Drawing.Size(12, 15);
            this.requiredImg1.TabIndex = 2;
            this.requiredImg1.TabStop = false;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.bottomButtonPanel);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.bottomPanel.Location = new System.Drawing.Point(3, 641);
            this.bottomPanel.Margin = new System.Windows.Forms.Padding(3, 0, 3, 3);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(294, 61);
            this.bottomPanel.TabIndex = 4;
            // 
            // bottomButtonPanel
            // 
            this.bottomButtonPanel.Controls.Add(this.requiredImg2);
            this.bottomButtonPanel.Controls.Add(this.runReportButton);
            this.bottomButtonPanel.Controls.Add(this.requiredLabel);
            this.bottomButtonPanel.Controls.Add(this.resetButton);
            this.bottomButtonPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.bottomButtonPanel.Location = new System.Drawing.Point(0, 0);
            this.bottomButtonPanel.Margin = new System.Windows.Forms.Padding(3, 0, 3, 3);
            this.bottomButtonPanel.Name = "bottomButtonPanel";
            this.bottomButtonPanel.Size = new System.Drawing.Size(294, 61);
            this.bottomButtonPanel.TabIndex = 2;
            // 
            // requiredImg2
            // 
            this.requiredImg2.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg2.Location = new System.Drawing.Point(28, 5);
            this.requiredImg2.Name = "requiredImg2";
            this.requiredImg2.Size = new System.Drawing.Size(16, 15);
            this.requiredImg2.TabIndex = 3;
            this.requiredImg2.TabStop = false;
            // 
            // runReportButton
            // 
            this.runReportButton.Enabled = false;
            this.runReportButton.Location = new System.Drawing.Point(109, 27);
            this.runReportButton.Name = "runReportButton";
            this.runReportButton.Size = new System.Drawing.Size(80, 25);
            this.runReportButton.TabIndex = 1;
            this.runReportButton.UseVisualStyleBackColor = true;
            this.runReportButton.Click += new System.EventHandler(this.btnRunReport_Click);
            // 
            // requiredLabel
            // 
            this.requiredLabel.Location = new System.Drawing.Point(41, 5);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(74, 15);
            this.requiredLabel.TabIndex = 4;
            // 
            // resetButton
            // 
            this.resetButton.Enabled = false;
            this.resetButton.Location = new System.Drawing.Point(28, 27);
            this.resetButton.Name = "resetButton";
            this.resetButton.Size = new System.Drawing.Size(75, 25);
            this.resetButton.TabIndex = 0;
            this.resetButton.UseVisualStyleBackColor = true;
            this.resetButton.Click += new System.EventHandler(this.resetButton_Click);
            // 
            // reportCriteriaPanel
            // 
            this.reportCriteriaPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reportCriteriaPanel.Location = new System.Drawing.Point(3, 82);
            this.reportCriteriaPanel.Name = "reportCriteriaPanel";
            this.reportCriteriaPanel.Size = new System.Drawing.Size(294, 556);
            this.reportCriteriaPanel.TabIndex = 1;
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.AutoSize = true;
            this.tableLayoutPanel.ColumnCount = 1;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.Controls.Add(this.upperPanel, 0, 0);
            this.tableLayoutPanel.Controls.Add(this.bottomPanel, 0, 2);
            this.tableLayoutPanel.Controls.Add(this.reportCriteriaPanel, 0, 1);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 3;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 11.30573F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 79.85815F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 8.936171F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(300, 705);
            this.tableLayoutPanel.TabIndex = 6;
            this.tableLayoutPanel.TabStop = true;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // ReportUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.Controls.Add(this.tableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ReportUI";
            this.Size = new System.Drawing.Size(300, 705);
            this.upperPanel.ResumeLayout(false);
            this.upperPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg1)).EndInit();
            this.bottomPanel.ResumeLayout(false);
            this.bottomButtonPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg2)).EndInit();
            this.tableLayoutPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox reportTypeComboBox;
        private System.Windows.Forms.Label reportNameLabel;
        private System.Windows.Forms.Panel upperPanel;
        private System.Windows.Forms.PictureBox requiredImg1;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.Button runReportButton;
        private System.Windows.Forms.Button resetButton;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox requiredImg2;
        private System.Windows.Forms.Panel reportCriteriaPanel;
        private System.Windows.Forms.Panel bottomButtonPanel;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.GroupBox separaterLine;
    }
}
