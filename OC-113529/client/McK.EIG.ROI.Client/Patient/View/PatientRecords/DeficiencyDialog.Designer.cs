namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    partial class DeficiencyDialog
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
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.outerTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.gridPanel = new System.Windows.Forms.Panel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.countOuterPanel = new System.Windows.Forms.Panel();
            this.countPanel = new System.Windows.Forms.Panel();
            this.countLabel = new System.Windows.Forms.Label();
            this.radioButtonPanel = new System.Windows.Forms.Panel();
            this.unLinkedRadioButton = new System.Windows.Forms.RadioButton();
            this.linkedRadioButton = new System.Windows.Forms.RadioButton();
            this.allRadioButton = new System.Windows.Forms.RadioButton();
            this.displayLabel = new System.Windows.Forms.Label();
            this.closeButton = new System.Windows.Forms.Button();
            this.topPanel = new System.Windows.Forms.Panel();
            this.outerTableLayoutPanel.SuspendLayout();
            this.gridPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.countOuterPanel.SuspendLayout();
            this.countPanel.SuspendLayout();
            this.radioButtonPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // outerTableLayoutPanel
            // 
            this.outerTableLayoutPanel.ColumnCount = 1;
            this.outerTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.outerTableLayoutPanel.Controls.Add(this.gridPanel, 0, 2);
            this.outerTableLayoutPanel.Controls.Add(this.radioButtonPanel, 0, 1);
            this.outerTableLayoutPanel.Controls.Add(this.closeButton, 0, 3);
            this.outerTableLayoutPanel.Controls.Add(this.topPanel, 0, 0);
            this.outerTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.outerTableLayoutPanel.Name = "outerTableLayoutPanel";
            this.outerTableLayoutPanel.Padding = new System.Windows.Forms.Padding(5);
            this.outerTableLayoutPanel.RowCount = 4;
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 75.86207F));
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 24.13793F));
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 267F));
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 29F));
            this.outerTableLayoutPanel.Size = new System.Drawing.Size(778, 423);
            this.outerTableLayoutPanel.TabIndex = 0;
            // 
            // gridPanel
            // 
            this.gridPanel.Controls.Add(this.grid);
            this.gridPanel.Controls.Add(this.countOuterPanel);
            this.gridPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gridPanel.Location = new System.Drawing.Point(8, 124);
            this.gridPanel.Name = "gridPanel";
            this.gridPanel.Size = new System.Drawing.Size(762, 261);
            this.gridPanel.TabIndex = 0;
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
            this.grid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.grid.Location = new System.Drawing.Point(0, 28);
            this.grid.MultiSelect = false;
            this.grid.Name = "grid";
            this.grid.ReadOnly = true;
            this.grid.RowHeadersVisible = false;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(762, 233);
            this.grid.SortEnabled = false;
            this.grid.TabIndex = 4;
            // 
            // countOuterPanel
            // 
            this.countOuterPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(215)))), ((int)(((byte)(225)))));
            this.countOuterPanel.Controls.Add(this.countPanel);
            this.countOuterPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.countOuterPanel.Location = new System.Drawing.Point(0, 0);
            this.countOuterPanel.Name = "countOuterPanel";
            this.countOuterPanel.Size = new System.Drawing.Size(762, 28);
            this.countOuterPanel.TabIndex = 3;
            // 
            // countPanel
            // 
            this.countPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(236)))), ((int)(((byte)(236)))));
            this.countPanel.Controls.Add(this.countLabel);
            this.countPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.countPanel.Location = new System.Drawing.Point(0, 2);
            this.countPanel.Name = "countPanel";
            this.countPanel.Size = new System.Drawing.Size(762, 26);
            this.countPanel.TabIndex = 2;
            // 
            // countLabel
            // 
            this.countLabel.AutoSize = true;
            this.countLabel.Location = new System.Drawing.Point(10, 5);
            this.countLabel.Name = "countLabel";
            this.countLabel.Size = new System.Drawing.Size(0, 15);
            this.countLabel.TabIndex = 0;
            // 
            // radioButtonPanel
            // 
            this.radioButtonPanel.Controls.Add(this.unLinkedRadioButton);
            this.radioButtonPanel.Controls.Add(this.linkedRadioButton);
            this.radioButtonPanel.Controls.Add(this.allRadioButton);
            this.radioButtonPanel.Controls.Add(this.displayLabel);
            this.radioButtonPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.radioButtonPanel.Location = new System.Drawing.Point(8, 96);
            this.radioButtonPanel.Name = "radioButtonPanel";
            this.radioButtonPanel.Size = new System.Drawing.Size(762, 22);
            this.radioButtonPanel.TabIndex = 1;
            // 
            // unLinkedRadioButton
            // 
            this.unLinkedRadioButton.AutoSize = true;
            this.unLinkedRadioButton.Location = new System.Drawing.Point(234, 2);
            this.unLinkedRadioButton.Name = "unLinkedRadioButton";
            this.unLinkedRadioButton.Size = new System.Drawing.Size(14, 13);
            this.unLinkedRadioButton.TabIndex = 3;
            this.unLinkedRadioButton.TabStop = true;
            this.unLinkedRadioButton.UseVisualStyleBackColor = true;
            // 
            // linkedRadioButton
            // 
            this.linkedRadioButton.AutoSize = true;
            this.linkedRadioButton.Location = new System.Drawing.Point(125, 2);
            this.linkedRadioButton.Name = "linkedRadioButton";
            this.linkedRadioButton.Size = new System.Drawing.Size(14, 13);
            this.linkedRadioButton.TabIndex = 2;
            this.linkedRadioButton.TabStop = true;
            this.linkedRadioButton.UseVisualStyleBackColor = true;
            // 
            // allRadioButton
            // 
            this.allRadioButton.AutoSize = true;
            this.allRadioButton.Checked = true;
            this.allRadioButton.Location = new System.Drawing.Point(70, 2);
            this.allRadioButton.Name = "allRadioButton";
            this.allRadioButton.Size = new System.Drawing.Size(14, 13);
            this.allRadioButton.TabIndex = 1;
            this.allRadioButton.TabStop = true;
            this.allRadioButton.UseVisualStyleBackColor = true;
            // 
            // displayLabel
            // 
            this.displayLabel.AutoSize = true;
            this.displayLabel.Location = new System.Drawing.Point(3, 4);
            this.displayLabel.Name = "displayLabel";
            this.displayLabel.Size = new System.Drawing.Size(0, 15);
            this.displayLabel.TabIndex = 0;
            // 
            // closeButton
            // 
            this.closeButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.closeButton.AutoSize = true;
            this.closeButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.closeButton.Location = new System.Drawing.Point(351, 391);
            this.closeButton.Name = "closeButton";
            this.closeButton.Size = new System.Drawing.Size(75, 23);
            this.closeButton.TabIndex = 5;
            this.closeButton.UseVisualStyleBackColor = true;
            // 
            // topPanel
            // 
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.topPanel.Location = new System.Drawing.Point(8, 8);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(762, 82);
            this.topPanel.TabIndex = 2;
            // 
            // DeficiencyDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.outerTableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "DeficiencyDialog";
            this.Size = new System.Drawing.Size(778, 423);
            this.outerTableLayoutPanel.ResumeLayout(false);
            this.outerTableLayoutPanel.PerformLayout();
            this.gridPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.countOuterPanel.ResumeLayout(false);
            this.countPanel.ResumeLayout(false);
            this.countPanel.PerformLayout();
            this.radioButtonPanel.ResumeLayout(false);
            this.radioButtonPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.TableLayoutPanel outerTableLayoutPanel;
        private System.Windows.Forms.Panel gridPanel;
        private System.Windows.Forms.Panel radioButtonPanel;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Button closeButton;
        private System.Windows.Forms.Label displayLabel;
        private System.Windows.Forms.RadioButton unLinkedRadioButton;
        private System.Windows.Forms.RadioButton linkedRadioButton;
        private System.Windows.Forms.RadioButton allRadioButton;
        private System.Windows.Forms.Panel countPanel;
        private System.Windows.Forms.Label countLabel;
        private System.Windows.Forms.Panel countOuterPanel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
    }
}
