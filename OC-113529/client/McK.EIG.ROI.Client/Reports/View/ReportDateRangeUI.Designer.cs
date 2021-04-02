using System;
namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ReportDateRangeUI
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ReportDateRangeUI));
            this.reportDateRangePanel = new System.Windows.Forms.Panel();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.presetRangeComboBox = new System.Windows.Forms.ComboBox();
            this.presetRangeLabel = new System.Windows.Forms.Label();
            this.dateRangeLabel = new System.Windows.Forms.Label();
            this.endDateLabel = new System.Windows.Forms.Label();
            this.startDateLabel = new System.Windows.Forms.Label();
            this.groupSeparater = new System.Windows.Forms.GroupBox();
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.dateTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.fromCustomDate = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.toCustomDate = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.reportDateRangePanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            this.tableLayoutPanel.SuspendLayout();
            this.dateTableLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // reportDateRangePanel
            // 
            this.reportDateRangePanel.Controls.Add(this.requiredImage);
            this.reportDateRangePanel.Controls.Add(this.presetRangeComboBox);
            this.reportDateRangePanel.Controls.Add(this.presetRangeLabel);
            this.reportDateRangePanel.Controls.Add(this.dateRangeLabel);
            this.reportDateRangePanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reportDateRangePanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportDateRangePanel.Location = new System.Drawing.Point(3, 11);
            this.reportDateRangePanel.Name = "reportDateRangePanel";
            this.reportDateRangePanel.Size = new System.Drawing.Size(219, 85);
            this.reportDateRangePanel.TabIndex = 0;
            // 
            // requiredImage
            // 
            this.requiredImage.Image = ((System.Drawing.Image)(resources.GetObject("requiredImage.Image")));
            this.requiredImage.Location = new System.Drawing.Point(0, 17);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(12, 12);
            this.requiredImage.TabIndex = 7;
            this.requiredImage.TabStop = false;
            // 
            // presetRangeComboBox
            // 
            this.presetRangeComboBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.presetRangeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.presetRangeComboBox.FormattingEnabled = true;
            this.presetRangeComboBox.Location = new System.Drawing.Point(1, 34);
            this.presetRangeComboBox.Name = "presetRangeComboBox";
            this.presetRangeComboBox.Size = new System.Drawing.Size(203, 23);
            this.presetRangeComboBox.TabIndex = 0;
            this.presetRangeComboBox.SelectedIndexChanged += new System.EventHandler(this.presetRangeComboBox_SelectedIndexChanged);
            // 
            // presetRangeLabel
            // 
            this.presetRangeLabel.AutoSize = true;
            this.presetRangeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.presetRangeLabel.ForeColor = System.Drawing.Color.Black;
            this.presetRangeLabel.Location = new System.Drawing.Point(18, 17);
            this.presetRangeLabel.Name = "presetRangeLabel";
            this.presetRangeLabel.Size = new System.Drawing.Size(0, 15);
            this.presetRangeLabel.TabIndex = 1;
            // 
            // dateRangeLabel
            // 
            this.dateRangeLabel.AutoSize = true;
            this.dateRangeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dateRangeLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(77)))), ((int)(((byte)(111)))), ((int)(((byte)(161)))));
            this.dateRangeLabel.Location = new System.Drawing.Point(0, -2);
            this.dateRangeLabel.Name = "dateRangeLabel";
            this.dateRangeLabel.Size = new System.Drawing.Size(0, 15);
            this.dateRangeLabel.TabIndex = 0;
            // 
            // endDateLabel
            // 
            this.endDateLabel.AutoSize = true;
            this.endDateLabel.Location = new System.Drawing.Point(108, 0);
            this.endDateLabel.Name = "endDateLabel";
            this.endDateLabel.Size = new System.Drawing.Size(0, 15);
            this.endDateLabel.TabIndex = 4;
            // 
            // startDateLabel
            // 
            this.startDateLabel.AutoSize = true;
            this.startDateLabel.Location = new System.Drawing.Point(3, 0);
            this.startDateLabel.Name = "startDateLabel";
            this.startDateLabel.Size = new System.Drawing.Size(0, 15);
            this.startDateLabel.TabIndex = 3;
            // 
            // groupSeparater
            // 
            this.groupSeparater.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(178)))), ((int)(((byte)(194)))), ((int)(((byte)(231)))));
            this.groupSeparater.Dock = System.Windows.Forms.DockStyle.Top;
            this.groupSeparater.Location = new System.Drawing.Point(3, 3);
            this.groupSeparater.Name = "groupSeparater";
            this.groupSeparater.Size = new System.Drawing.Size(219, 2);
            this.groupSeparater.TabIndex = 8;
            this.groupSeparater.TabStop = false;
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.ColumnCount = 1;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.Controls.Add(this.groupSeparater, 0, 0);
            this.tableLayoutPanel.Controls.Add(this.reportDateRangePanel, 0, 1);
            this.tableLayoutPanel.Controls.Add(this.dateTableLayoutPanel, 0, 2);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 3;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 50F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(225, 149);
            this.tableLayoutPanel.TabIndex = 9;
            // 
            // dateTableLayoutPanel
            // 
            this.dateTableLayoutPanel.ColumnCount = 2;
            this.dateTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 47.11111F));
            this.dateTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 52.88889F));
            this.dateTableLayoutPanel.Controls.Add(this.startDateLabel, 0, 0);
            this.dateTableLayoutPanel.Controls.Add(this.endDateLabel, 1, 0);
            this.dateTableLayoutPanel.Controls.Add(this.fromCustomDate, 0, 1);
            this.dateTableLayoutPanel.Controls.Add(this.toCustomDate, 1, 1);
            this.dateTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dateTableLayoutPanel.Location = new System.Drawing.Point(0, 99);
            this.dateTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.dateTableLayoutPanel.Name = "dateTableLayoutPanel";
            this.dateTableLayoutPanel.RowCount = 2;
            this.dateTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 36.36364F));
            this.dateTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 63.63636F));
            this.dateTableLayoutPanel.Size = new System.Drawing.Size(225, 50);
            this.dateTableLayoutPanel.TabIndex = 9;
            // 
            // fromCustomDate
            // 
            this.fromCustomDate.FormattedDate = null;
            this.fromCustomDate.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.fromCustomDate.IsValidDate = false;
            this.fromCustomDate.Location = new System.Drawing.Point(3, 21);
            this.fromCustomDate.Mask = "AA/AA/AAAA";
            this.fromCustomDate.Name = "fromCustomDate";
            this.fromCustomDate.PromptChar = ' ';
            this.fromCustomDate.Size = new System.Drawing.Size(99, 21);
            this.fromCustomDate.TabIndex = 5;
            this.fromCustomDate.TextChanged += new System.EventHandler(this.startDateTimePicker_TextChanged);
            // 
            // toCustomDate
            // 
            this.toCustomDate.FormattedDate = null;
            this.toCustomDate.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.toCustomDate.IsValidDate = false;
            this.toCustomDate.Location = new System.Drawing.Point(108, 21);
            this.toCustomDate.Mask = "AA/AA/AAAA";
            this.toCustomDate.Name = "toCustomDate";
            this.toCustomDate.PromptChar = ' ';
            this.toCustomDate.Size = new System.Drawing.Size(100, 21);
            this.toCustomDate.TabIndex = 6;
            this.toCustomDate.TextChanged += new System.EventHandler(this.endDateTimePicker1_TextChanged);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkRate = 0;
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // ReportDateRangeUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.Controls.Add(this.tableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ReportDateRangeUI";
            this.Size = new System.Drawing.Size(225, 149);
            this.reportDateRangePanel.ResumeLayout(false);
            this.reportDateRangePanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            this.tableLayoutPanel.ResumeLayout(false);
            this.dateTableLayoutPanel.ResumeLayout(false);
            this.dateTableLayoutPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel reportDateRangePanel;
        private System.Windows.Forms.Label endDateLabel;
        private System.Windows.Forms.Label startDateLabel;
        private System.Windows.Forms.ComboBox presetRangeComboBox;
        private System.Windows.Forms.Label presetRangeLabel;
        private System.Windows.Forms.Label dateRangeLabel;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.GroupBox groupSeparater;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.TableLayoutPanel dateTableLayoutPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        internal McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl fromCustomDate;
        internal McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl toCustomDate;
    }
}
