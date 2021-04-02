namespace McK.EIG.ROI.Client.Request.View.Comments
{
    partial class EventHistoryListUI
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
            this.commentPanel = new System.Windows.Forms.Panel();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.eventCountLabel = new McK.EIG.ROI.Client.Base.View.Common.ROILabel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.filterPanel = new System.Windows.Forms.Panel();
            this.eventPanel = new System.Windows.Forms.Panel();
            this.eventLabel = new System.Windows.Forms.Label();
            this.eventComboBox = new System.Windows.Forms.ComboBox();
            this.presetDateRange = new McK.EIG.ROI.Client.Base.View.PresetDateRange();
            this.originatorPanel = new System.Windows.Forms.Panel();
            this.originatorComboBox = new System.Windows.Forms.ComboBox();
            this.originatorLabel = new System.Windows.Forms.Label();
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.commentPanel.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.filterPanel.SuspendLayout();
            this.eventPanel.SuspendLayout();
            this.originatorPanel.SuspendLayout();
            this.tableLayoutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // commentPanel
            // 
            this.commentPanel.Controls.Add(this.tableLayoutPanel1);
            this.commentPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.commentPanel.Location = new System.Drawing.Point(3, 81);
            this.commentPanel.Name = "commentPanel";
            this.commentPanel.Size = new System.Drawing.Size(765, 223);
            this.commentPanel.TabIndex = 2;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.eventCountLabel, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.grid, 0, 1);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 2;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.Size = new System.Drawing.Size(765, 223);
            this.tableLayoutPanel1.TabIndex = 0;
            // 
            // eventCountLabel
            // 
            this.eventCountLabel.AutoEllipsis = true;
            this.eventCountLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.eventCountLabel.FlowDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.eventCountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.eventCountLabel.GradientFrom = System.Drawing.Color.White;
            this.eventCountLabel.GradientTo = System.Drawing.Color.FromArgb(((int)(((byte)(234)))), ((int)(((byte)(234)))), ((int)(((byte)(234)))));
            this.eventCountLabel.Location = new System.Drawing.Point(0, 0);
            this.eventCountLabel.Margin = new System.Windows.Forms.Padding(0);
            this.eventCountLabel.Name = "eventCountLabel";
            this.eventCountLabel.NormalBorderColor = System.Drawing.Color.Empty;
            this.eventCountLabel.Size = new System.Drawing.Size(765, 22);
            this.eventCountLabel.TabIndex = 4;
            this.eventCountLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // grid
            // 
            this.grid.AllowUserToAddRows = false;
            this.grid.AllowUserToDeleteRows = false;
            this.grid.AllowUserToResizeRows = false;
            dataGridViewCellStyle3.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(237)))), ((int)(((byte)(239)))), ((int)(((byte)(246)))));
            this.grid.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle3;
            this.grid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.grid.BackgroundColor = System.Drawing.Color.White;
            this.grid.ChangeValidator = null;
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle4.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            dataGridViewCellStyle4.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle4.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle4.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle4.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.grid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle4;
            this.grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid.ConfirmSelection = false;
            this.grid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.grid.EnableHeadersVisualStyles = false;
            this.grid.Location = new System.Drawing.Point(0, 22);
            this.grid.Margin = new System.Windows.Forms.Padding(0);
            this.grid.MultiSelect = false;
            this.grid.Name = "grid";
            this.grid.ReadOnly = true;
            this.grid.RowHeadersVisible = false;
            this.grid.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(765, 201);
            this.grid.SortEnabled = false;
            this.grid.TabIndex = 3;
            // 
            // filterPanel
            // 
            this.filterPanel.Controls.Add(this.eventPanel);
            this.filterPanel.Controls.Add(this.originatorPanel);
            this.filterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.filterPanel.Location = new System.Drawing.Point(3, 3);
            this.filterPanel.Name = "filterPanel";
            this.filterPanel.Size = new System.Drawing.Size(765, 72);
            this.filterPanel.TabIndex = 3;
            // 
            // eventPanel
            // 
            this.eventPanel.Controls.Add(this.eventLabel);
            this.eventPanel.Controls.Add(this.eventComboBox);
            this.eventPanel.Controls.Add(this.presetDateRange);
            this.eventPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.eventPanel.Location = new System.Drawing.Point(0, 0);
            this.eventPanel.Name = "eventPanel";
            this.eventPanel.Size = new System.Drawing.Size(765, 38);
            this.eventPanel.TabIndex = 6;
            // 
            // eventLabel
            // 
            this.eventLabel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.eventLabel.AutoSize = true;
            this.eventLabel.Location = new System.Drawing.Point(17, 16);
            this.eventLabel.Name = "eventLabel";
            this.eventLabel.Size = new System.Drawing.Size(0, 15);
            this.eventLabel.TabIndex = 0;
            // 
            // eventComboBox
            // 
            this.eventComboBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.eventComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.eventComboBox.FormattingEnabled = true;
            this.eventComboBox.Location = new System.Drawing.Point(107, 13);
            this.eventComboBox.Name = "eventComboBox";
            this.eventComboBox.Size = new System.Drawing.Size(164, 23);
            this.eventComboBox.Sorted = true;
            this.eventComboBox.TabIndex = 1;
            // 
            // presetDateRange
            // 
            this.presetDateRange.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.presetDateRange.BackColor = System.Drawing.SystemColors.Control;
            this.presetDateRange.DateRangeOption = "All";
            this.presetDateRange.FromDate = null;
            this.presetDateRange.IsValidDateRange = false;
            this.presetDateRange.Location = new System.Drawing.Point(262, 6);
            this.presetDateRange.Name = "presetDateRange";
            this.presetDateRange.Size = new System.Drawing.Size(499, 26);
            this.presetDateRange.TabIndex = 1;
            this.presetDateRange.ToDate = null;
            // 
            // originatorPanel
            // 
            this.originatorPanel.Controls.Add(this.originatorComboBox);
            this.originatorPanel.Controls.Add(this.originatorLabel);
            this.originatorPanel.Location = new System.Drawing.Point(0, 41);
            this.originatorPanel.Name = "originatorPanel";
            this.originatorPanel.Size = new System.Drawing.Size(761, 28);
            this.originatorPanel.TabIndex = 6;
            // 
            // originatorComboBox
            // 
            this.originatorComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.originatorComboBox.FormattingEnabled = true;
            this.originatorComboBox.Location = new System.Drawing.Point(107, 3);
            this.originatorComboBox.Name = "originatorComboBox";
            this.originatorComboBox.Size = new System.Drawing.Size(164, 23);
            this.originatorComboBox.TabIndex = 2;
            // 
            // originatorLabel
            // 
            this.originatorLabel.AutoSize = true;
            this.originatorLabel.Location = new System.Drawing.Point(17, 3);
            this.originatorLabel.Name = "originatorLabel";
            this.originatorLabel.Size = new System.Drawing.Size(0, 15);
            this.originatorLabel.TabIndex = 0;
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.ColumnCount = 1;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.Controls.Add(this.filterPanel, 0, 0);
            this.tableLayoutPanel.Controls.Add(this.commentPanel, 0, 1);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 2;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(771, 307);
            this.tableLayoutPanel.TabIndex = 7;
            // 
            // EventHistoryListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.tableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "EventHistoryListUI";
            this.Size = new System.Drawing.Size(771, 307);
            this.commentPanel.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.filterPanel.ResumeLayout(false);
            this.eventPanel.ResumeLayout(false);
            this.eventPanel.PerformLayout();
            this.originatorPanel.ResumeLayout(false);
            this.originatorPanel.PerformLayout();
            this.tableLayoutPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel commentPanel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.Panel filterPanel;
        private System.Windows.Forms.ComboBox eventComboBox;
        private System.Windows.Forms.Label eventLabel;
        private McK.EIG.ROI.Client.Base.View.PresetDateRange presetDateRange;
        private System.Windows.Forms.Panel originatorPanel;
        private System.Windows.Forms.ComboBox originatorComboBox;
        private System.Windows.Forms.Label originatorLabel;
        private System.Windows.Forms.Panel eventPanel;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private McK.EIG.ROI.Client.Base.View.Common.ROILabel eventCountLabel;
    }
}
