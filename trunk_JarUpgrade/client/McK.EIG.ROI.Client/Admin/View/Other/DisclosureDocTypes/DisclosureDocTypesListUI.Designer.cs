namespace McK.EIG.ROI.Client.Admin.View.Other.DisclosureDocTypes
{
    partial class DisclosureDocTypesListUI
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            this.controlsPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.controlsSplitContainer = new System.Windows.Forms.SplitContainer();
            this.countLabel = new System.Windows.Forms.Label();
            this.codeSetCombo = new System.Windows.Forms.ComboBox();
            this.codeSetLabel = new System.Windows.Forms.Label();
            this.documentTypesGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.controlsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.controlsSplitContainer)).BeginInit();
            this.controlsSplitContainer.Panel1.SuspendLayout();
            this.controlsSplitContainer.Panel2.SuspendLayout();
            this.controlsSplitContainer.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.documentTypesGrid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // controlsPanel
            // 
            this.controlsPanel.Controls.Add(this.cancelButton);
            this.controlsPanel.Controls.Add(this.saveButton);
            this.controlsPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.controlsPanel.Location = new System.Drawing.Point(0, 644);
            this.controlsPanel.Name = "controlsPanel";
            this.controlsPanel.Size = new System.Drawing.Size(957, 43);
            this.controlsPanel.TabIndex = 5;
            // 
            // cancelButton
            // 
            this.cancelButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.cancelButton.Enabled = false;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(477, 18);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(78, 23);
            this.cancelButton.TabIndex = 4;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // saveButton
            // 
            this.saveButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.saveButton.Enabled = false;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(395, 18);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(78, 23);
            this.saveButton.TabIndex = 3;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // controlsSplitContainer
            // 
            this.controlsSplitContainer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.controlsSplitContainer.IsSplitterFixed = true;
            this.controlsSplitContainer.Location = new System.Drawing.Point(0, 0);
            this.controlsSplitContainer.Name = "controlsSplitContainer";
            this.controlsSplitContainer.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // controlsSplitContainer.Panel1
            // 
            this.controlsSplitContainer.Panel1.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.controlsSplitContainer.Panel1.Controls.Add(this.countLabel);
            this.controlsSplitContainer.Panel1.Controls.Add(this.codeSetCombo);
            this.controlsSplitContainer.Panel1.Controls.Add(this.codeSetLabel);
            this.controlsSplitContainer.Panel1MinSize = 50;
            // 
            // controlsSplitContainer.Panel2
            // 
            this.controlsSplitContainer.Panel2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(145)))), ((int)(((byte)(167)))), ((int)(((byte)(180)))));
            this.controlsSplitContainer.Panel2.Controls.Add(this.documentTypesGrid);
            this.controlsSplitContainer.Panel2.Padding = new System.Windows.Forms.Padding(1);
            this.controlsSplitContainer.Panel2MinSize = 50;
            this.controlsSplitContainer.Size = new System.Drawing.Size(957, 644);
            this.controlsSplitContainer.SplitterDistance = 72;
            this.controlsSplitContainer.TabIndex = 7;
            // 
            // countLabel
            // 
            this.countLabel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.countLabel.AutoSize = true;
            this.countLabel.Location = new System.Drawing.Point(7, 55);
            this.countLabel.Name = "countLabel";
            this.countLabel.Size = new System.Drawing.Size(0, 15);
            this.countLabel.TabIndex = 12;
            this.countLabel.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
            // 
            // codeSetCombo
            // 
            this.codeSetCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.codeSetCombo.FormattingEnabled = true;
            this.codeSetCombo.Location = new System.Drawing.Point(70, 9);
            this.codeSetCombo.Name = "codeSetCombo";
            this.codeSetCombo.Size = new System.Drawing.Size(223, 23);
            this.codeSetCombo.TabIndex = 1;
            this.codeSetCombo.SelectedIndexChanged += new System.EventHandler(this.codeSetCombo_SelectedIndexChanged);
            // 
            // codeSetLabel
            // 
            this.codeSetLabel.AutoSize = true;
            this.codeSetLabel.Location = new System.Drawing.Point(3, 12);
            this.codeSetLabel.Name = "codeSetLabel";
            this.codeSetLabel.Size = new System.Drawing.Size(0, 15);
            this.codeSetLabel.TabIndex = 9;
            // 
            // documentTypesGrid
            // 
            this.documentTypesGrid.AllowUserToAddRows = false;
            this.documentTypesGrid.AllowUserToDeleteRows = false;
            this.documentTypesGrid.AllowUserToResizeColumns = false;
            this.documentTypesGrid.AllowUserToResizeRows = false;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(237)))), ((int)(((byte)(241)))), ((int)(((byte)(246)))));
            this.documentTypesGrid.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.documentTypesGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.documentTypesGrid.BackgroundColor = System.Drawing.Color.White;
            this.documentTypesGrid.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.documentTypesGrid.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.SingleVertical;
            this.documentTypesGrid.ChangeValidator = null;
            this.documentTypesGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.documentTypesGrid.ConfirmSelection = false;
            this.documentTypesGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.documentTypesGrid.Location = new System.Drawing.Point(1, 1);
            this.documentTypesGrid.MultiSelect = false;
            this.documentTypesGrid.Name = "documentTypesGrid";
            this.documentTypesGrid.RowHeadersVisible = false;
            this.documentTypesGrid.SelectionHandler = null;
            this.documentTypesGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.documentTypesGrid.Size = new System.Drawing.Size(955, 566);
            this.documentTypesGrid.SortEnabled = false;
            this.documentTypesGrid.TabIndex = 2;
            this.documentTypesGrid.CellValueChanged += new System.Windows.Forms.DataGridViewCellEventHandler(this.documentTypesGrid_CellValueChanged);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // DisclosureDocTypesListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.controlsSplitContainer);
            this.Controls.Add(this.controlsPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "DisclosureDocTypesListUI";
            this.Size = new System.Drawing.Size(957, 687);
            this.Resize += new System.EventHandler(this.DisclosureDocTypesListUI_Resize);
            this.controlsPanel.ResumeLayout(false);
            this.controlsSplitContainer.Panel1.ResumeLayout(false);
            this.controlsSplitContainer.Panel1.PerformLayout();
            this.controlsSplitContainer.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.controlsSplitContainer)).EndInit();
            this.controlsSplitContainer.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.documentTypesGrid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel controlsPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.SplitContainer controlsSplitContainer;
        private System.Windows.Forms.ComboBox codeSetCombo;
        private System.Windows.Forms.Label codeSetLabel;
        private System.Windows.Forms.Label countLabel;
        internal McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid documentTypesGrid;
        private System.Windows.Forms.ErrorProvider errorProvider;

    }
}
