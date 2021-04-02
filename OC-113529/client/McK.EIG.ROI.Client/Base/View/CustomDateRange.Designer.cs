namespace McK.EIG.ROI.Client.Base.View
{
    partial class CustomDateRange
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
            this.controlsPanel = new System.Windows.Forms.TableLayoutPanel();
            this.toLabel = new System.Windows.Forms.Label();
            this.customRangeCombo = new System.Windows.Forms.ComboBox();
            this.fromLabel = new System.Windows.Forms.Label();
            this.dateLabel = new System.Windows.Forms.Label();
            this.fromCustomDate = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.toCustomDate = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.controlsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // controlsPanel
            // 
            this.controlsPanel.AutoSize = true;
            this.controlsPanel.BackColor = System.Drawing.Color.White;
            this.controlsPanel.ColumnCount = 6;
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 14.5098F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 21.85687F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 8.704062F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 13.53965F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 5.609284F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 35.39652F));
            this.controlsPanel.Controls.Add(this.toLabel, 4, 0);
            this.controlsPanel.Controls.Add(this.customRangeCombo, 1, 0);
            this.controlsPanel.Controls.Add(this.fromLabel, 2, 0);
            this.controlsPanel.Controls.Add(this.dateLabel, 0, 0);
            this.controlsPanel.Controls.Add(this.fromCustomDate, 3, 0);
            this.controlsPanel.Controls.Add(this.toCustomDate, 5, 0);
            this.controlsPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.controlsPanel.Location = new System.Drawing.Point(0, 0);
            this.controlsPanel.Name = "controlsPanel";
            this.controlsPanel.RowCount = 1;
            this.controlsPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.controlsPanel.Size = new System.Drawing.Size(517, 29);
            this.controlsPanel.TabIndex = 0;
            // 
            // toLabel
            // 
            this.toLabel.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.toLabel.AutoSize = true;
            this.toLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.toLabel.Location = new System.Drawing.Point(308, 7);
            this.toLabel.Name = "toLabel";
            this.toLabel.Size = new System.Drawing.Size(21, 15);
            this.toLabel.TabIndex = 2;
            //this.toLabel.Text = "To";
            this.toLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // customRangeCombo
            // 
            this.customRangeCombo.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.customRangeCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.customRangeCombo.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.customRangeCombo.FormattingEnabled = true;
            this.customRangeCombo.Items.AddRange(new object[] {
            "All",
            "30 days",
            "60 days",
            "90 days",
            "Custom Range"});
            this.customRangeCombo.Location = new System.Drawing.Point(78, 3);
            this.customRangeCombo.Name = "customRangeCombo";
            this.customRangeCombo.Size = new System.Drawing.Size(107, 23);
            this.customRangeCombo.TabIndex = 0;
            this.customRangeCombo.SelectedIndexChanged += new System.EventHandler(this.rangeCombo_SelectedIndexChanged);
            // 
            // fromLabel
            // 
            this.fromLabel.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.fromLabel.AutoSize = true;
            this.fromLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fromLabel.Location = new System.Drawing.Point(194, 7);
            this.fromLabel.Name = "fromLabel";
            this.fromLabel.Size = new System.Drawing.Size(36, 15);
            this.fromLabel.TabIndex = 1;
            //this.fromLabel.Text = "From";
            this.fromLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // dateLabel
            // 
            this.dateLabel.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.dateLabel.AutoSize = true;
            this.dateLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dateLabel.Location = new System.Drawing.Point(72, 7);
            this.dateLabel.Name = "dateLabel";
            this.dateLabel.Size = new System.Drawing.Size(0, 15);
            this.dateLabel.TabIndex = 0;
            this.dateLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // fromCustomDate
            // 
            this.fromCustomDate.FormattedDate = null;
            this.fromCustomDate.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.fromCustomDate.IsValidDate = false;
            this.fromCustomDate.Location = new System.Drawing.Point(236, 3);
            this.fromCustomDate.Mask = "AA/AA/AAAA";
            this.fromCustomDate.Name = "fromCustomDate";
            this.fromCustomDate.PromptChar = ' ';
            this.fromCustomDate.Size = new System.Drawing.Size(60, 20);
            this.fromCustomDate.TabIndex = 3;
            // 
            // toCustomDate
            // 
            this.toCustomDate.FormattedDate = null;
            this.toCustomDate.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.toCustomDate.IsValidDate = false;
            this.toCustomDate.Location = new System.Drawing.Point(335, 3);
            this.toCustomDate.Mask = "AA/AA/AAAA";
            this.toCustomDate.Name = "toCustomDate";
            this.toCustomDate.PromptChar = ' ';
            this.toCustomDate.Size = new System.Drawing.Size(60, 20);
            this.toCustomDate.TabIndex = 4;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkRate = 0;
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // CustomDateRange
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.Controls.Add(this.controlsPanel);
            this.Name = "CustomDateRange";
            this.Size = new System.Drawing.Size(517, 29);
            this.controlsPanel.ResumeLayout(false);
            this.controlsPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel controlsPanel;
        private System.Windows.Forms.Label fromLabel;
        private System.Windows.Forms.Label toLabel;
        internal System.Windows.Forms.Label dateLabel;
        internal System.Windows.Forms.ComboBox customRangeCombo;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.ErrorProvider errorProvider;
        internal McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl fromCustomDate;
        internal McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl toCustomDate;
    }
}
