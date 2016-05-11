namespace McK.EIG.ROI.Client.Request.View
{
    partial class RequestsListUI
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
            this.reqHistoryListPanel = new System.Windows.Forms.TableLayoutPanel();
            this.listPanel = new System.Windows.Forms.Panel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.labelPanel = new System.Windows.Forms.Panel();
            this.searchCountLabel = new System.Windows.Forms.Label();
            this.buttonPanel = new System.Windows.Forms.Panel();
            this.exportRequestButton = new System.Windows.Forms.Button();
            this.viewEditRequestButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.reqHistoryListPanel.SuspendLayout();
            this.listPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.labelPanel.SuspendLayout();
            this.buttonPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // reqHistoryListPanel
            // 
            this.reqHistoryListPanel.ColumnCount = 1;
            this.reqHistoryListPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.reqHistoryListPanel.Controls.Add(this.listPanel, 0, 0);
            this.reqHistoryListPanel.Controls.Add(this.buttonPanel, 0, 2);
            this.reqHistoryListPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reqHistoryListPanel.Location = new System.Drawing.Point(0, 0);
            this.reqHistoryListPanel.Name = "reqHistoryListPanel";
            this.reqHistoryListPanel.RowCount = 3;
            this.reqHistoryListPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.reqHistoryListPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.reqHistoryListPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 37F));
            this.reqHistoryListPanel.Size = new System.Drawing.Size(668, 409);
            this.reqHistoryListPanel.TabIndex = 0;
            // 
            // listPanel
            // 
            this.listPanel.Controls.Add(this.grid);
            this.listPanel.Controls.Add(this.labelPanel);
            this.listPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listPanel.Location = new System.Drawing.Point(3, 3);
            this.listPanel.Name = "listPanel";
            this.listPanel.Size = new System.Drawing.Size(662, 366);
            this.listPanel.TabIndex = 1;
            // 
            // grid
            // 
            this.grid.AllowUserToAddRows = false;
            this.grid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.grid.BackgroundColor = System.Drawing.SystemColors.ControlLightLight;
            this.grid.ChangeValidator = null;
            this.grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid.ConfirmSelection = false;
            this.grid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.grid.Location = new System.Drawing.Point(0, 23);
            this.grid.Name = "grid";
            this.grid.RowHeadersVisible = false;
            this.grid.RowTemplate.ReadOnly = true;
            this.grid.RowTemplate.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(662, 343);
            this.grid.SortEnabled = false;
            this.grid.StandardTab = true;
            this.grid.TabIndex = 1;
            this.grid.CellFormatting += new System.Windows.Forms.DataGridViewCellFormattingEventHandler(this.grid_CellFormatting);
            // 
            // labelPanel
            // 
            this.labelPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(236)))), ((int)(((byte)(236)))));
            this.labelPanel.Controls.Add(this.searchCountLabel);
            this.labelPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.labelPanel.Location = new System.Drawing.Point(0, 0);
            this.labelPanel.Name = "labelPanel";
            this.labelPanel.Size = new System.Drawing.Size(662, 23);
            this.labelPanel.TabIndex = 3;
            // 
            // searchCountLabel
            // 
            this.searchCountLabel.AutoSize = true;
            this.searchCountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.searchCountLabel.Location = new System.Drawing.Point(3, 4);
            this.searchCountLabel.Name = "searchCountLabel";
            this.searchCountLabel.Size = new System.Drawing.Size(0, 15);
            this.searchCountLabel.TabIndex = 1;
            // 
            // buttonPanel
            // 
            this.buttonPanel.Controls.Add(this.exportRequestButton);
            this.buttonPanel.Controls.Add(this.viewEditRequestButton);
            this.buttonPanel.Location = new System.Drawing.Point(3, 375);
            this.buttonPanel.Name = "buttonPanel";
            this.buttonPanel.Size = new System.Drawing.Size(662, 31);
            this.buttonPanel.TabIndex = 2;
            // 
            // exportRequestButton
            // 
            this.exportRequestButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.exportRequestButton.Location = new System.Drawing.Point(520, 4);
            this.exportRequestButton.Name = "exportRequestButton";
            this.exportRequestButton.Size = new System.Drawing.Size(128, 27);
            this.exportRequestButton.TabIndex = 4;
            this.exportRequestButton.UseVisualStyleBackColor = true;
            this.exportRequestButton.Click += new System.EventHandler(this.exportRequestButton_Click);
            // 
            // viewEditRequestButton
            // 
            this.viewEditRequestButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.viewEditRequestButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.viewEditRequestButton.Location = new System.Drawing.Point(340, 4);
            this.viewEditRequestButton.Name = "viewEditRequestButton";
            this.viewEditRequestButton.Size = new System.Drawing.Size(128, 27);
            this.viewEditRequestButton.TabIndex = 3;
            this.viewEditRequestButton.UseVisualStyleBackColor = true;
            this.viewEditRequestButton.Click += new System.EventHandler(this.viewEditReqButton_Click);
            // 
            // RequestsListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.reqHistoryListPanel);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "RequestsListUI";
            this.Size = new System.Drawing.Size(668, 409);
            this.reqHistoryListPanel.ResumeLayout(false);
            this.listPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.labelPanel.ResumeLayout(false);
            this.labelPanel.PerformLayout();
            this.buttonPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel reqHistoryListPanel;
        private System.Windows.Forms.Panel listPanel;
        private System.Windows.Forms.Label searchCountLabel;
        private System.Windows.Forms.Panel labelPanel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel buttonPanel;
        private System.Windows.Forms.Button exportRequestButton;
        private System.Windows.Forms.Button viewEditRequestButton;
    }
}
