namespace McK.EIG.ROI.Client.Request.View.Comments
{
    partial class RequestCommentListUI
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            this.outerTableLayout = new System.Windows.Forms.TableLayoutPanel();
            this.commentPanel = new System.Windows.Forms.Panel();
            this.grid1 = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.commentCountPanel = new System.Windows.Forms.Panel();
            this.commentCountPanel1 = new System.Windows.Forms.Panel();
            this.commentCountLabel = new System.Windows.Forms.Label();
            this.filterPanel = new System.Windows.Forms.Panel();
            this.presetDateRange = new McK.EIG.ROI.Client.Base.View.PresetDateRange();
            this.authorComboBox = new System.Windows.Forms.ComboBox();
            this.authorLabel = new System.Windows.Forms.Label();
            this.outerTableLayout.SuspendLayout();
            this.commentPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid1)).BeginInit();
            this.commentCountPanel.SuspendLayout();
            this.commentCountPanel1.SuspendLayout();
            this.filterPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // outerTableLayout
            // 
            this.outerTableLayout.ColumnCount = 1;
            this.outerTableLayout.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.outerTableLayout.Controls.Add(this.commentPanel, 0, 1);
            this.outerTableLayout.Controls.Add(this.filterPanel, 0, 0);
            this.outerTableLayout.Dock = System.Windows.Forms.DockStyle.Fill;
            this.outerTableLayout.Location = new System.Drawing.Point(0, 0);
            this.outerTableLayout.Name = "outerTableLayout";
            this.outerTableLayout.RowCount = 2;
            this.outerTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 11.66667F));
            this.outerTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 88.33334F));
            this.outerTableLayout.Size = new System.Drawing.Size(780, 300);
            this.outerTableLayout.TabIndex = 20;
            // 
            // commentPanel
            // 
            this.commentPanel.BackColor = System.Drawing.Color.White;
            this.commentPanel.Controls.Add(this.grid1);
            this.commentPanel.Controls.Add(this.commentCountPanel);
            this.commentPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.commentPanel.Location = new System.Drawing.Point(3, 38);
            this.commentPanel.Name = "commentPanel";
            this.commentPanel.Size = new System.Drawing.Size(774, 259);
            this.commentPanel.TabIndex = 3;
            // 
            // grid1
            // 
            this.grid1.AllowUserToAddRows = false;
            this.grid1.AllowUserToDeleteRows = false;
            this.grid1.AllowUserToResizeRows = false;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(237)))), ((int)(((byte)(239)))), ((int)(((byte)(246)))));
            this.grid1.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.grid1.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.grid1.BackgroundColor = System.Drawing.Color.White;
            this.grid1.ChangeValidator = null;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            dataGridViewCellStyle2.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.grid1.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle2;
            this.grid1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid1.ConfirmSelection = false;
            this.grid1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.grid1.EnableHeadersVisualStyles = false;
            this.grid1.Location = new System.Drawing.Point(0, 28);
            this.grid1.MultiSelect = false;
            this.grid1.Name = "grid1";
            this.grid1.ReadOnly = true;
            this.grid1.RowHeadersVisible = false;
            this.grid1.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.grid1.SelectionHandler = null;
            this.grid1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid1.Size = new System.Drawing.Size(774, 231);
            this.grid1.SortEnabled = false;
            this.grid1.TabIndex = 3;
            // 
            // commentCountPanel
            // 
            this.commentCountPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(215)))), ((int)(((byte)(225)))));
            this.commentCountPanel.Controls.Add(this.commentCountPanel1);
            this.commentCountPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.commentCountPanel.Location = new System.Drawing.Point(0, 0);
            this.commentCountPanel.Name = "commentCountPanel";
            this.commentCountPanel.Padding = new System.Windows.Forms.Padding(0, 2, 0, 0);
            this.commentCountPanel.Size = new System.Drawing.Size(774, 28);
            this.commentCountPanel.TabIndex = 3;
            // 
            // commentCountPanel1
            // 
            this.commentCountPanel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(236)))), ((int)(((byte)(236)))));
            this.commentCountPanel1.Controls.Add(this.commentCountLabel);
            this.commentCountPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.commentCountPanel1.Location = new System.Drawing.Point(0, 2);
            this.commentCountPanel1.Name = "commentCountPanel1";
            this.commentCountPanel1.Size = new System.Drawing.Size(774, 26);
            this.commentCountPanel1.TabIndex = 3;
            // 
            // commentCountLabel
            // 
            this.commentCountLabel.AutoSize = true;
            this.commentCountLabel.Location = new System.Drawing.Point(10, 5);
            this.commentCountLabel.Name = "commentCountLabel";
            this.commentCountLabel.Size = new System.Drawing.Size(0, 15);
            this.commentCountLabel.TabIndex = 0;
            // 
            // filterPanel
            // 
            this.filterPanel.Controls.Add(this.presetDateRange);
            this.filterPanel.Controls.Add(this.authorComboBox);
            this.filterPanel.Controls.Add(this.authorLabel);
            this.filterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.filterPanel.Location = new System.Drawing.Point(3, 3);
            this.filterPanel.Name = "filterPanel";
            this.filterPanel.Size = new System.Drawing.Size(774, 29);
            this.filterPanel.TabIndex = 0;
            // 
            // presetDateRange
            // 
            this.presetDateRange.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.presetDateRange.BackColor = System.Drawing.SystemColors.Control;
            this.presetDateRange.DateRangeOption = "All";
            this.presetDateRange.FromDate = null;
            this.presetDateRange.IsValidDateRange = false;
            this.presetDateRange.Location = new System.Drawing.Point(242, 0);
            this.presetDateRange.Name = "presetDateRange";
            this.presetDateRange.Size = new System.Drawing.Size(504, 29);
            this.presetDateRange.TabIndex = 2;
            this.presetDateRange.ToDate = null;
            // 
            // authorComboBox
            // 
            this.authorComboBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.authorComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.authorComboBox.FormattingEnabled = true;
            this.authorComboBox.Location = new System.Drawing.Point(77, 4);
            this.authorComboBox.Name = "authorComboBox";
            this.authorComboBox.Size = new System.Drawing.Size(150, 23);
            this.authorComboBox.Sorted = true;
            this.authorComboBox.TabIndex = 1;
            // 
            // authorLabel
            // 
            this.authorLabel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.authorLabel.AutoSize = true;
            this.authorLabel.Location = new System.Drawing.Point(10, 7);
            this.authorLabel.Name = "authorLabel";
            this.authorLabel.Size = new System.Drawing.Size(0, 15);
            this.authorLabel.TabIndex = 0;
            // 
            // RequestCommentListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.outerTableLayout);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "RequestCommentListUI";
            this.Size = new System.Drawing.Size(780, 300);
            this.outerTableLayout.ResumeLayout(false);
            this.commentPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid1)).EndInit();
            this.commentCountPanel.ResumeLayout(false);
            this.commentCountPanel1.ResumeLayout(false);
            this.commentCountPanel1.PerformLayout();
            this.filterPanel.ResumeLayout(false);
            this.filterPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel outerTableLayout;
        private System.Windows.Forms.Panel commentPanel;
        private System.Windows.Forms.Panel commentCountPanel;
        private System.Windows.Forms.Panel commentCountPanel1;
        private System.Windows.Forms.Label commentCountLabel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid1;
        private System.Windows.Forms.Panel filterPanel;
        private System.Windows.Forms.ComboBox authorComboBox;
        private System.Windows.Forms.Label authorLabel;
        private McK.EIG.ROI.Client.Base.View.PresetDateRange presetDateRange;
    }
}
