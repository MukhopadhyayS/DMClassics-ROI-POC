namespace McK.EIG.ROI.Client.Patient.View
{
    partial class MismatchPatientUI
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
            this.patientMismatchInfoLabel = new System.Windows.Forms.Label();
            this.gridPanel = new System.Windows.Forms.Panel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.continueSelectedPatientsButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.gridMesssagePanel = new System.Windows.Forms.Panel();
            this.countLabel = new McK.EIG.ROI.Client.Base.View.Common.ROILabel();
            this.topPanel.SuspendLayout();
            this.gridPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.footerPanel.SuspendLayout();
            this.gridMesssagePanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Controls.Add(this.patientMismatchInfoLabel);
            this.topPanel.Location = new System.Drawing.Point(6, 6);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(765, 89);
            this.topPanel.TabIndex = 0;
            // 
            // patientMismatchInfoLabel
            // 
            this.patientMismatchInfoLabel.AutoSize = true;
            this.patientMismatchInfoLabel.Location = new System.Drawing.Point(3, 74);
            this.patientMismatchInfoLabel.Name = "patientMismatchInfoLabel";
            this.patientMismatchInfoLabel.Size = new System.Drawing.Size(0, 15);
            this.patientMismatchInfoLabel.TabIndex = 0;
            // 
            // gridPanel
            // 
            this.gridPanel.Controls.Add(this.grid);
            this.gridPanel.Location = new System.Drawing.Point(6, 134);
            this.gridPanel.Name = "gridPanel";
            this.gridPanel.Size = new System.Drawing.Size(765, 224);
            this.gridPanel.TabIndex = 3;
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
            this.grid.Location = new System.Drawing.Point(0, 0);
            this.grid.MultiSelect = false;
            this.grid.Name = "grid";
            this.grid.RowHeadersVisible = false;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(765, 224);
            this.grid.SortEnabled = false;
            this.grid.StandardTab = true;
            this.grid.TabIndex = 3;
            this.grid.CurrentCellDirtyStateChanged += new System.EventHandler(this.grid_CurrentCellDirtyStateChanged);
            this.grid.CellValueChanged += new System.Windows.Forms.DataGridViewCellEventHandler(this.grid_CellValueChanged);
            // 
            // footerPanel
            // 
            this.footerPanel.Controls.Add(this.cancelButton);
            this.footerPanel.Controls.Add(this.continueSelectedPatientsButton);
            this.footerPanel.Location = new System.Drawing.Point(3, 361);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(768, 44);
            this.footerPanel.TabIndex = 0;
            // 
            // cancelButton
            // 
            this.cancelButton.AutoSize = true;
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Location = new System.Drawing.Point(415, 9);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(98, 27);
            this.cancelButton.TabIndex = 1;
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // continueSelectedPatientsButton
            // 
            this.continueSelectedPatientsButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.continueSelectedPatientsButton.AutoSize = true;
            this.continueSelectedPatientsButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.continueSelectedPatientsButton.Enabled = false;
            this.continueSelectedPatientsButton.Location = new System.Drawing.Point(208, 9);
            this.continueSelectedPatientsButton.Name = "continueSelectedPatientsButton";
            this.continueSelectedPatientsButton.Size = new System.Drawing.Size(129, 27);
            this.continueSelectedPatientsButton.TabIndex = 0;
            this.continueSelectedPatientsButton.UseVisualStyleBackColor = true;
            // 
            // gridMesssagePanel
            // 
            this.gridMesssagePanel.Controls.Add(this.countLabel);
            this.gridMesssagePanel.Location = new System.Drawing.Point(6, 99);
            this.gridMesssagePanel.Name = "gridMesssagePanel";
            this.gridMesssagePanel.Size = new System.Drawing.Size(765, 32);
            this.gridMesssagePanel.TabIndex = 4;
            // 
            // countLabel
            // 
            this.countLabel.AutoEllipsis = true;
            this.countLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.countLabel.Enabled = false;
            this.countLabel.FlowDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.countLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.countLabel.GradientFrom = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.countLabel.GradientTo = System.Drawing.Color.FromArgb(((int)(((byte)(234)))), ((int)(((byte)(234)))), ((int)(((byte)(234)))));
            this.countLabel.Location = new System.Drawing.Point(0, 0);
            this.countLabel.Margin = new System.Windows.Forms.Padding(0);
            this.countLabel.Name = "countLabel";
            this.countLabel.NormalBorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(234)))), ((int)(((byte)(234)))), ((int)(((byte)(234)))));
            this.countLabel.Size = new System.Drawing.Size(765, 32);
            this.countLabel.TabIndex = 0;
            this.countLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // MismatchPatientUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.gridMesssagePanel);
            this.Controls.Add(this.gridPanel);
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "MismatchPatientUI";
            this.Size = new System.Drawing.Size(774, 411);
            this.topPanel.ResumeLayout(false);
            this.topPanel.PerformLayout();
            this.gridPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            this.gridMesssagePanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel gridPanel;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button continueSelectedPatientsButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.ToolTip toolTip;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.Label patientMismatchInfoLabel;
        private System.Windows.Forms.Panel gridMesssagePanel;
        private McK.EIG.ROI.Client.Base.View.Common.ROILabel countLabel;
        
    }
}
