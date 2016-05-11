namespace McK.EIG.ROI.Client.OverDueInvoice.View
{
    partial class OverDueInvoiceListUI
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
            this.viewButton = new System.Windows.Forms.Button();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.outputButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.outerTableLayout = new System.Windows.Forms.TableLayoutPanel();
            this.invoiceMessageLabel = new System.Windows.Forms.Label();
            this.outerPanel = new System.Windows.Forms.Panel();
            this.pastDueInvoiceGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.searchCountPanel = new System.Windows.Forms.Panel();
            this.searchCountPanel1 = new System.Windows.Forms.Panel();
            this.searchCountLabel = new System.Windows.Forms.Label();
            this.bottomPanel.SuspendLayout();
            this.outerTableLayout.SuspendLayout();
            this.outerPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pastDueInvoiceGrid)).BeginInit();
            this.searchCountPanel.SuspendLayout();
            this.searchCountPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // viewButton
            // 
            this.viewButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.viewButton.Location = new System.Drawing.Point(303, 12);
            this.viewButton.Name = "viewButton";
            this.viewButton.Size = new System.Drawing.Size(99, 27);
            this.viewButton.TabIndex = 0;
            this.viewButton.UseVisualStyleBackColor = true;
            // 
            // bottomPanel
            // 
            this.bottomPanel.BackColor = System.Drawing.Color.White;
            this.bottomPanel.Controls.Add(this.outputButton);
            this.bottomPanel.Controls.Add(this.viewButton);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.bottomPanel.Location = new System.Drawing.Point(0, 279);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(788, 47);
            this.bottomPanel.TabIndex = 12;
            // 
            // outputButton
            // 
            this.outputButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.outputButton.Location = new System.Drawing.Point(408, 12);
            this.outputButton.Name = "outputButton";
            this.outputButton.Size = new System.Drawing.Size(84, 27);
            this.outputButton.TabIndex = 1;
            this.outputButton.UseVisualStyleBackColor = true;
            // 
            // outerTableLayout
            // 
            this.outerTableLayout.BackColor = System.Drawing.Color.White;
            this.outerTableLayout.ColumnCount = 1;
            this.outerTableLayout.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.outerTableLayout.Controls.Add(this.invoiceMessageLabel, 0, 0);
            this.outerTableLayout.Controls.Add(this.outerPanel, 0, 1);
            this.outerTableLayout.Dock = System.Windows.Forms.DockStyle.Fill;
            this.outerTableLayout.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.outerTableLayout.Location = new System.Drawing.Point(0, 0);
            this.outerTableLayout.Name = "outerTableLayout";
            this.outerTableLayout.RowCount = 2;
            this.outerTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 8F));
            this.outerTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 80.73395F));
            this.outerTableLayout.Size = new System.Drawing.Size(788, 279);
            this.outerTableLayout.TabIndex = 13;
            // 
            // invoiceMessageLabel
            // 
            this.invoiceMessageLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.invoiceMessageLabel.AutoSize = true;
            this.invoiceMessageLabel.Location = new System.Drawing.Point(3, 5);
            this.invoiceMessageLabel.Name = "invoiceMessageLabel";
            this.invoiceMessageLabel.Size = new System.Drawing.Size(0, 15);
            this.invoiceMessageLabel.TabIndex = 0;
            // 
            // outerPanel
            // 
            this.outerPanel.Controls.Add(this.pastDueInvoiceGrid);
            this.outerPanel.Controls.Add(this.searchCountPanel);
            this.outerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.outerPanel.Location = new System.Drawing.Point(3, 28);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.Size = new System.Drawing.Size(782, 248);
            this.outerPanel.TabIndex = 1;
            // 
            // pastDueInvoiceGrid
            // 
            this.pastDueInvoiceGrid.AllowUserToAddRows = false;
            this.pastDueInvoiceGrid.AllowUserToDeleteRows = false;
            this.pastDueInvoiceGrid.AllowUserToResizeRows = false;
            this.pastDueInvoiceGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.pastDueInvoiceGrid.BackgroundColor = System.Drawing.Color.White;
            this.pastDueInvoiceGrid.ChangeValidator = null;
            this.pastDueInvoiceGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.pastDueInvoiceGrid.ConfirmSelection = false;
            this.pastDueInvoiceGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pastDueInvoiceGrid.Location = new System.Drawing.Point(0, 30);
            this.pastDueInvoiceGrid.Name = "pastDueInvoiceGrid";
            this.pastDueInvoiceGrid.RowHeadersVisible = false;
            this.pastDueInvoiceGrid.SelectionHandler = null;
            this.pastDueInvoiceGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.pastDueInvoiceGrid.ShowCellToolTips = false;
            this.pastDueInvoiceGrid.Size = new System.Drawing.Size(782, 218);
            this.pastDueInvoiceGrid.SortEnabled = false;
            this.pastDueInvoiceGrid.StandardTab = true;
            this.pastDueInvoiceGrid.TabIndex = 1;
            // 
            // searchCountPanel
            // 
            this.searchCountPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(215)))), ((int)(((byte)(225)))));
            this.searchCountPanel.Controls.Add(this.searchCountPanel1);
            this.searchCountPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.searchCountPanel.Location = new System.Drawing.Point(0, 0);
            this.searchCountPanel.Name = "searchCountPanel";
            this.searchCountPanel.Padding = new System.Windows.Forms.Padding(0, 2, 0, 0);
            this.searchCountPanel.Size = new System.Drawing.Size(782, 30);
            this.searchCountPanel.TabIndex = 0;
            // 
            // searchCountPanel1
            // 
            this.searchCountPanel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(236)))), ((int)(((byte)(236)))));
            this.searchCountPanel1.Controls.Add(this.searchCountLabel);
            this.searchCountPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.searchCountPanel1.Location = new System.Drawing.Point(0, 2);
            this.searchCountPanel1.Name = "searchCountPanel1";
            this.searchCountPanel1.Size = new System.Drawing.Size(782, 28);
            this.searchCountPanel1.TabIndex = 1;
            // 
            // searchCountLabel
            // 
            this.searchCountLabel.AutoSize = true;
            this.searchCountLabel.Location = new System.Drawing.Point(10, 5);
            this.searchCountLabel.Name = "searchCountLabel";
            this.searchCountLabel.Size = new System.Drawing.Size(0, 15);
            this.searchCountLabel.TabIndex = 0;
            // 
            // OverDueInvoiceListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.outerTableLayout);
            this.Controls.Add(this.bottomPanel);
            this.Name = "OverDueInvoiceListUI";
            this.Size = new System.Drawing.Size(788, 326);
            this.bottomPanel.ResumeLayout(false);
            this.outerTableLayout.ResumeLayout(false);
            this.outerTableLayout.PerformLayout();
            this.outerPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pastDueInvoiceGrid)).EndInit();
            this.searchCountPanel.ResumeLayout(false);
            this.searchCountPanel1.ResumeLayout(false);
            this.searchCountPanel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button viewButton;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.TableLayoutPanel outerTableLayout;
        internal System.Windows.Forms.Label invoiceMessageLabel;
        private System.Windows.Forms.Panel outerPanel;
        internal McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid pastDueInvoiceGrid;
        private System.Windows.Forms.Panel searchCountPanel;
        private System.Windows.Forms.Panel searchCountPanel1;
        private System.Windows.Forms.Label searchCountLabel;
        private System.Windows.Forms.Button outputButton;
    }
}
