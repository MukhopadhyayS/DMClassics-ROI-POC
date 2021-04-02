namespace McK.EIG.ROI.Client.Patient.View
{
    partial class MatchingRequestorUI
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
            this.matchingRequstorInfoLabel = new System.Windows.Forms.Label();
            this.gridPanel = new System.Windows.Forms.Panel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.newRequestorButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.selectRequestorButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.panel1 = new System.Windows.Forms.Panel();
            this.countLabel = new McK.EIG.ROI.Client.Base.View.Common.ROILabel();
            this.topPanel.SuspendLayout();
            this.gridPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.footerPanel.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Controls.Add(this.matchingRequstorInfoLabel);
            this.topPanel.Location = new System.Drawing.Point(6, 6);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(858, 117);
            this.topPanel.TabIndex = 0;
            // 
            // matchingRequstorInfoLabel
            // 
            this.matchingRequstorInfoLabel.Location = new System.Drawing.Point(4, 81);
            this.matchingRequstorInfoLabel.Name = "matchingRequstorInfoLabel";
            this.matchingRequstorInfoLabel.Size = new System.Drawing.Size(851, 29);
            this.matchingRequstorInfoLabel.TabIndex = 0;
            // 
            // gridPanel
            // 
            this.gridPanel.Controls.Add(this.grid);
            this.gridPanel.Location = new System.Drawing.Point(3, 153);
            this.gridPanel.Name = "gridPanel";
            this.gridPanel.Size = new System.Drawing.Size(861, 225);
            this.gridPanel.TabIndex = 1;
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
            this.grid.Location = new System.Drawing.Point(0, 3);
            this.grid.MultiSelect = false;
            this.grid.Name = "grid";
            this.grid.ReadOnly = true;
            this.grid.RowHeadersVisible = false;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(858, 214);
            this.grid.SortEnabled = false;
            this.grid.TabIndex = 4;
            this.grid.TabStop = false;
            // 
            // footerPanel
            // 
            this.footerPanel.Controls.Add(this.newRequestorButton);
            this.footerPanel.Controls.Add(this.cancelButton);
            this.footerPanel.Controls.Add(this.selectRequestorButton);
            this.footerPanel.Location = new System.Drawing.Point(3, 384);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(861, 44);
            this.footerPanel.TabIndex = 0;
            // 
            // newRequestorButton
            // 
            this.newRequestorButton.DialogResult = System.Windows.Forms.DialogResult.Retry;
            this.newRequestorButton.Location = new System.Drawing.Point(364, 9);
            this.newRequestorButton.Name = "newRequestorButton";
            this.newRequestorButton.Size = new System.Drawing.Size(129, 27);
            this.newRequestorButton.TabIndex = 2;
            this.newRequestorButton.UseVisualStyleBackColor = true;
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Location = new System.Drawing.Point(499, 9);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(129, 27);
            this.cancelButton.TabIndex = 3;
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // selectRequestorButton
            // 
            this.selectRequestorButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.selectRequestorButton.AutoSize = true;
            this.selectRequestorButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.selectRequestorButton.Location = new System.Drawing.Point(229, 9);
            this.selectRequestorButton.Name = "selectRequestorButton";
            this.selectRequestorButton.Size = new System.Drawing.Size(129, 27);
            this.selectRequestorButton.TabIndex = 0;
            this.selectRequestorButton.UseVisualStyleBackColor = true;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.countLabel);
            this.panel1.Location = new System.Drawing.Point(6, 119);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(855, 37);
            this.panel1.TabIndex = 2;
            // 
            // countLabel
            // 
            this.countLabel.AutoEllipsis = true;
            this.countLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.countLabel.FlowDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.countLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.countLabel.GradientFrom = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.countLabel.GradientTo = System.Drawing.Color.FromArgb(((int)(((byte)(234)))), ((int)(((byte)(234)))), ((int)(((byte)(234)))));
            this.countLabel.Location = new System.Drawing.Point(0, 0);
            this.countLabel.Margin = new System.Windows.Forms.Padding(0);
            this.countLabel.Name = "countLabel";
            this.countLabel.NormalBorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(234)))), ((int)(((byte)(234)))), ((int)(((byte)(234)))));
            this.countLabel.Size = new System.Drawing.Size(855, 37);
            this.countLabel.TabIndex = 2;
            this.countLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // MatchingRequestorUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.gridPanel);
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "MatchingRequestorUI";
            this.Size = new System.Drawing.Size(864, 439);
            this.topPanel.ResumeLayout(false);
            this.gridPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel gridPanel;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button selectRequestorButton;
        private System.Windows.Forms.Button cancelButton;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Button newRequestorButton;
        private System.Windows.Forms.Label matchingRequstorInfoLabel;
        private System.Windows.Forms.Panel panel1;
        private McK.EIG.ROI.Client.Base.View.Common.ROILabel countLabel;
    }
}
