namespace McK.EIG.ROI.Client.Patient.View
{
    partial class EncounterFilterDialog
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
            this.topPanel = new System.Windows.Forms.Panel();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.recordCountTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.recordCountPanel = new System.Windows.Forms.Panel();
            this.countLabel = new System.Windows.Forms.Label();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.filterOffEncounterButton = new System.Windows.Forms.Button();
            this.filterOnEncounterButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.bottomPanel.SuspendLayout();
            this.recordCountTableLayoutPanel.SuspendLayout();
            this.recordCountPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.footerPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Location = new System.Drawing.Point(6, 6);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(771, 89);
            this.topPanel.TabIndex = 0;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.recordCountTableLayoutPanel);
            this.bottomPanel.Controls.Add(this.grid);
            this.bottomPanel.Location = new System.Drawing.Point(6, 95);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(771, 267);
            this.bottomPanel.TabIndex = 1;
            // 
            // recordCountTableLayoutPanel
            // 
            this.recordCountTableLayoutPanel.ColumnCount = 1;
            this.recordCountTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.recordCountTableLayoutPanel.Controls.Add(this.recordCountPanel, 0, 1);
            this.recordCountTableLayoutPanel.Location = new System.Drawing.Point(1, 3);
            this.recordCountTableLayoutPanel.Name = "recordCountTableLayoutPanel";
            this.recordCountTableLayoutPanel.RowCount = 2;
            this.recordCountTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 37.5F));
            this.recordCountTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 62.5F));
            this.recordCountTableLayoutPanel.Size = new System.Drawing.Size(769, 44);
            this.recordCountTableLayoutPanel.TabIndex = 2;
            // 
            // recordCountPanel
            // 
            this.recordCountPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(215)))), ((int)(((byte)(225)))));
            this.recordCountPanel.Controls.Add(this.countLabel);
            this.recordCountPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.recordCountPanel.Location = new System.Drawing.Point(3, 19);
            this.recordCountPanel.Name = "recordCountPanel";
            this.recordCountPanel.Size = new System.Drawing.Size(763, 22);
            this.recordCountPanel.TabIndex = 0;
            // 
            // countLabel
            // 
            this.countLabel.AutoSize = true;
            this.countLabel.Location = new System.Drawing.Point(3, 7);
            this.countLabel.Name = "countLabel";
            this.countLabel.Size = new System.Drawing.Size(0, 15);
            this.countLabel.TabIndex = 0;
            // 
            // grid
            // 
            this.grid.AllowUserToAddRows = false;
            this.grid.AllowUserToDeleteRows = false;
            this.grid.AllowUserToResizeRows = false;
            this.grid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.grid.BackgroundColor = System.Drawing.Color.White;
            this.grid.ChangeValidator = null;
            this.grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid.ConfirmSelection = false;
            this.grid.Location = new System.Drawing.Point(0, 50);
            this.grid.Name = "grid";
            this.grid.RowHeadersVisible = false;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(765, 214);
            this.grid.SortEnabled = false;
            this.grid.TabIndex = 1;
            // 
            // footerPanel
            // 
            this.footerPanel.Controls.Add(this.cancelButton);
            this.footerPanel.Controls.Add(this.filterOffEncounterButton);
            this.footerPanel.Controls.Add(this.filterOnEncounterButton);
            this.footerPanel.Location = new System.Drawing.Point(3, 361);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(771, 44);
            this.footerPanel.TabIndex = 0;
            // 
            // cancelButton
            // 
            this.cancelButton.Location = new System.Drawing.Point(513, 14);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(129, 27);
            this.cancelButton.TabIndex = 2;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // filterOffEncounterButton
            // 
            this.filterOffEncounterButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.filterOffEncounterButton.AutoSize = true;
            this.filterOffEncounterButton.Location = new System.Drawing.Point(378, 14);
            this.filterOffEncounterButton.Name = "filterOffEncounterButton";
            this.filterOffEncounterButton.Size = new System.Drawing.Size(129, 27);
            this.filterOffEncounterButton.TabIndex = 1;
            this.filterOffEncounterButton.UseVisualStyleBackColor = true;
            this.filterOffEncounterButton.Click += new System.EventHandler(this.filterOffEncounterButton_Click);
            // 
            // filterOnEncounterButton
            // 
            this.filterOnEncounterButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.filterOnEncounterButton.AutoSize = true;
            this.filterOnEncounterButton.Location = new System.Drawing.Point(243, 14);
            this.filterOnEncounterButton.Name = "filterOnEncounterButton";
            this.filterOnEncounterButton.Size = new System.Drawing.Size(129, 27);
            this.filterOnEncounterButton.TabIndex = 0;
            this.filterOnEncounterButton.UseVisualStyleBackColor = true;
            this.filterOnEncounterButton.Click += new System.EventHandler(this.filterOnEncounterButton_Click);
            // 
            // EncounterFilterDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.bottomPanel);
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "EncounterFilterDialog";
            this.Size = new System.Drawing.Size(774, 411);
            this.bottomPanel.ResumeLayout(false);
            this.recordCountTableLayoutPanel.ResumeLayout(false);
            this.recordCountPanel.ResumeLayout(false);
            this.recordCountPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button filterOffEncounterButton;
        private System.Windows.Forms.Button filterOnEncounterButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.TableLayoutPanel recordCountTableLayoutPanel;
        private System.Windows.Forms.Panel recordCountPanel;
        private System.Windows.Forms.Label countLabel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
