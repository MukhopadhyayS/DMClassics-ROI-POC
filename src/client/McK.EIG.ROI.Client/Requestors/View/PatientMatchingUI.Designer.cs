namespace McK.EIG.ROI.Client.Requestors.View
{
    partial class PatientMatchingUI
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
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.recordCountTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.recordCountPanel = new System.Windows.Forms.Panel();
            this.countLabel = new System.Windows.Forms.Label();
            this.extraInfoLabel = new System.Windows.Forms.Label();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.continueWithNoPatientButton = new System.Windows.Forms.Button();
            this.continueWithSelectedPatientsButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.bottomPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.recordCountTableLayoutPanel.SuspendLayout();
            this.recordCountPanel.SuspendLayout();
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
            this.bottomPanel.Controls.Add(this.grid);
            this.bottomPanel.Controls.Add(this.recordCountTableLayoutPanel);
            this.bottomPanel.Location = new System.Drawing.Point(6, 95);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(771, 328);
            this.bottomPanel.TabIndex = 1;
            // 
            // grid
            // 
            this.grid.AllowUserToAddRows = false;
            this.grid.AllowUserToDeleteRows = false;
            this.grid.AllowUserToResizeRows = false;
            this.grid.BackgroundColor = System.Drawing.Color.White;
            this.grid.ChangeValidator = null;
            this.grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid.ConfirmSelection = false;
            this.grid.Location = new System.Drawing.Point(3, 59);
            this.grid.MultiSelect = false;
            this.grid.Name = "grid";
            this.grid.RowHeadersVisible = false;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(765, 266);
            this.grid.SortEnabled = false;
            this.grid.TabIndex = 2;
            this.grid.CurrentCellDirtyStateChanged += new System.EventHandler(this.grid_CurrentCellDirtyStateChanged);
            this.grid.CellValueChanged += new System.Windows.Forms.DataGridViewCellEventHandler(this.grid_CellValueChanged);
            // 
            // recordCountTableLayoutPanel
            // 
            this.recordCountTableLayoutPanel.ColumnCount = 1;
            this.recordCountTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.recordCountTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.recordCountTableLayoutPanel.Controls.Add(this.recordCountPanel, 0, 1);
            this.recordCountTableLayoutPanel.Controls.Add(this.extraInfoLabel, 0, 0);
            this.recordCountTableLayoutPanel.Location = new System.Drawing.Point(0, 3);
            this.recordCountTableLayoutPanel.Name = "recordCountTableLayoutPanel";
            this.recordCountTableLayoutPanel.RowCount = 2;
            this.recordCountTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 43.90244F));
            this.recordCountTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 56.09756F));
            this.recordCountTableLayoutPanel.Size = new System.Drawing.Size(770, 54);
            this.recordCountTableLayoutPanel.TabIndex = 0;
            // 
            // recordCountPanel
            // 
            this.recordCountPanel.BackColor = System.Drawing.Color.Gainsboro;
            this.recordCountPanel.Controls.Add(this.countLabel);
            this.recordCountPanel.Location = new System.Drawing.Point(3, 26);
            this.recordCountPanel.Name = "recordCountPanel";
            this.recordCountPanel.Size = new System.Drawing.Size(763, 25);
            this.recordCountPanel.TabIndex = 0;
            // 
            // countLabel
            // 
            this.countLabel.AutoSize = true;
            this.countLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.countLabel.Location = new System.Drawing.Point(7, 4);
            this.countLabel.Name = "countLabel";
            this.countLabel.Size = new System.Drawing.Size(0, 15);
            this.countLabel.TabIndex = 0;
            // 
            // extraInfoLabel
            // 
            this.extraInfoLabel.AutoSize = true;
            this.extraInfoLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.extraInfoLabel.Location = new System.Drawing.Point(3, 0);
            this.extraInfoLabel.Name = "extraInfoLabel";
            this.extraInfoLabel.Size = new System.Drawing.Size(0, 15);
            this.extraInfoLabel.TabIndex = 1;
            // 
            // footerPanel
            // 
            this.footerPanel.Controls.Add(this.cancelButton);
            this.footerPanel.Controls.Add(this.continueWithNoPatientButton);
            this.footerPanel.Controls.Add(this.continueWithSelectedPatientsButton);
            this.footerPanel.Location = new System.Drawing.Point(6, 423);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(771, 44);
            this.footerPanel.TabIndex = 2;
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(534, 14);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(129, 27);
            this.cancelButton.TabIndex = 2;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // continueWithNoPatientButton
            // 
            this.continueWithNoPatientButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.continueWithNoPatientButton.AutoSize = true;
            this.continueWithNoPatientButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.continueWithNoPatientButton.Location = new System.Drawing.Point(372, 14);
            this.continueWithNoPatientButton.Name = "continueWithNoPatientButton";
            this.continueWithNoPatientButton.Size = new System.Drawing.Size(158, 27);
            this.continueWithNoPatientButton.TabIndex = 1;
            this.continueWithNoPatientButton.UseVisualStyleBackColor = true;
            this.continueWithNoPatientButton.Click += new System.EventHandler(this.continueWithNoPatientButton_Click);
            // 
            // continueWithSelectedPatientsButton
            // 
            this.continueWithSelectedPatientsButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.continueWithSelectedPatientsButton.AutoSize = true;
            this.continueWithSelectedPatientsButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.continueWithSelectedPatientsButton.Location = new System.Drawing.Point(172, 14);
            this.continueWithSelectedPatientsButton.Name = "continueWithSelectedPatientsButton";
            this.continueWithSelectedPatientsButton.Size = new System.Drawing.Size(191, 27);
            this.continueWithSelectedPatientsButton.TabIndex = 0;
            this.continueWithSelectedPatientsButton.UseVisualStyleBackColor = true;
            this.continueWithSelectedPatientsButton.Click += new System.EventHandler(this.continueWithSelectedPatientsButton_Click);
            // 
            // PatientMatchingUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.bottomPanel);
            this.Controls.Add(this.topPanel);
            this.Name = "PatientMatchingUI";
            this.Size = new System.Drawing.Size(816, 487);
            this.bottomPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.recordCountTableLayoutPanel.ResumeLayout(false);
            this.recordCountTableLayoutPanel.PerformLayout();
            this.recordCountPanel.ResumeLayout(false);
            this.recordCountPanel.PerformLayout();
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.TableLayoutPanel recordCountTableLayoutPanel;
        private System.Windows.Forms.Panel recordCountPanel;
        private System.Windows.Forms.Label countLabel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button continueWithNoPatientButton;
        private System.Windows.Forms.Button continueWithSelectedPatientsButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Label extraInfoLabel;
    }
}
