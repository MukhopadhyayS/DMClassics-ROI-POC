namespace McK.EIG.ROI.Client.Base.View
{
    partial class PresetDateRange
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
            this.rangeCombo = new System.Windows.Forms.ComboBox();
            this.fromNullableDTP = new McK.EIG.ROI.Client.Base.View.Common.NullableDateTimePicker();
            this.fromLabel = new System.Windows.Forms.Label();
            this.dateLabel = new System.Windows.Forms.Label();
            this.toNullableDTP = new McK.EIG.ROI.Client.Base.View.Common.NullableDateTimePicker();
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
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 23.33333F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 8.431373F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 20.30948F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 6.96325F));
            this.controlsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 26.07843F));
            this.controlsPanel.Controls.Add(this.toLabel, 4, 0);
            this.controlsPanel.Controls.Add(this.rangeCombo, 1, 0);
            this.controlsPanel.Controls.Add(this.fromNullableDTP, 3, 0);
            this.controlsPanel.Controls.Add(this.fromLabel, 2, 0);
            this.controlsPanel.Controls.Add(this.dateLabel, 0, 0);
            this.controlsPanel.Controls.Add(this.toNullableDTP, 5, 0);
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
            this.toLabel.Location = new System.Drawing.Point(377, 7);
            this.toLabel.Name = "toLabel";
            this.toLabel.Size = new System.Drawing.Size(0, 15);
            this.toLabel.TabIndex = 2;
            this.toLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // rangeCombo
            // 
            this.rangeCombo.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.rangeCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.rangeCombo.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rangeCombo.FormattingEnabled = true;
            this.rangeCombo.Items.AddRange(new object[] {
            "All",
            "30 days",
            "60 days",
            "90 days",
            "Custom Range"});
            this.rangeCombo.Location = new System.Drawing.Point(78, 3);
            this.rangeCombo.Name = "rangeCombo";
            this.rangeCombo.Size = new System.Drawing.Size(112, 23);
            this.rangeCombo.TabIndex = 0;
            this.rangeCombo.SelectedIndexChanged += new System.EventHandler(this.rangeCombo_SelectedIndexChanged);
            // 
            // fromNullableDTP
            // 
            this.fromNullableDTP.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.fromNullableDTP.DateValue = new System.DateTime(2008, 7, 14, 0, 0, 0, 0);
            this.fromNullableDTP.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fromNullableDTP.Format = System.Windows.Forms.DateTimePickerFormat.Short;
            this.fromNullableDTP.Location = new System.Drawing.Point(242, 4);
            this.fromNullableDTP.Name = "fromNullableDTP";
            this.fromNullableDTP.NullValue = "";
            this.fromNullableDTP.Size = new System.Drawing.Size(95, 21);
            this.fromNullableDTP.TabIndex = 1;
            this.fromNullableDTP.Value = new System.DateTime(2008, 7, 14, 0, 0, 0, 0);
            this.fromNullableDTP.Enter += new System.EventHandler(this.fromNullableDTP_Enter);
            this.fromNullableDTP.ValueChanged += new System.EventHandler(this.fromNullableDTP_ValueChanged);
            this.fromNullableDTP.MouseUp += new System.Windows.Forms.MouseEventHandler(this.fromNullableDTP_MouseUp);
            // 
            // fromLabel
            // 
            this.fromLabel.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.fromLabel.AutoSize = true;
            this.fromLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fromLabel.Location = new System.Drawing.Point(236, 7);
            this.fromLabel.Name = "fromLabel";
            this.fromLabel.Size = new System.Drawing.Size(0, 15);
            this.fromLabel.TabIndex = 1;
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
            // toNullableDTP
            // 
            this.toNullableDTP.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.toNullableDTP.DateValue = new System.DateTime(2008, 7, 14, 23, 59, 0, 0);
            this.toNullableDTP.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.toNullableDTP.Format = System.Windows.Forms.DateTimePickerFormat.Short;
            this.toNullableDTP.Location = new System.Drawing.Point(383, 4);
            this.toNullableDTP.Name = "toNullableDTP";
            this.toNullableDTP.NullValue = "";
            this.toNullableDTP.Size = new System.Drawing.Size(93, 21);
            this.toNullableDTP.TabIndex = 2;
            this.toNullableDTP.Value = new System.DateTime(2008, 7, 14, 23, 59, 0, 0);
            this.toNullableDTP.Enter += new System.EventHandler(this.toNullableDTP_Enter);
            this.toNullableDTP.ValueChanged += new System.EventHandler(this.toNullableDTP_ValueChanged);
            this.toNullableDTP.MouseUp += new System.Windows.Forms.MouseEventHandler(this.toNullableDTP_MouseUp);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkRate = 0;
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // PresetDateRange
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.Controls.Add(this.controlsPanel);
            this.Name = "PresetDateRange";
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
        internal System.Windows.Forms.ComboBox rangeCombo;
        internal McK.EIG.ROI.Client.Base.View.Common.NullableDateTimePicker fromNullableDTP;
        internal McK.EIG.ROI.Client.Base.View.Common.NullableDateTimePicker toNullableDTP;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.ErrorProvider errorProvider;
    }
}
