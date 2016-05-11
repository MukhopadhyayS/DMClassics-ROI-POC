namespace McK.EIG.ROI.Client.Patient.View.FindPatient
{
    partial class PatientListUI
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
            this.patientListTableLayout = new System.Windows.Forms.TableLayoutPanel();
            this.messagePatientLabel = new System.Windows.Forms.Label();
            this.outerPanel = new System.Windows.Forms.Panel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.searchCountPanel = new System.Windows.Forms.Panel();
            this.searchCountPanel1 = new System.Windows.Forms.Panel();
            this.searchCountLabel = new System.Windows.Forms.Label();
            this.patientListTableLayout.SuspendLayout();
            this.outerPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.searchCountPanel.SuspendLayout();
            this.searchCountPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // patientListTableLayout
            // 
            this.patientListTableLayout.BackColor = System.Drawing.Color.White;
            this.patientListTableLayout.ColumnCount = 1;
            this.patientListTableLayout.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.patientListTableLayout.Controls.Add(this.messagePatientLabel, 0, 0);
            this.patientListTableLayout.Controls.Add(this.outerPanel, 0, 1);
            this.patientListTableLayout.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientListTableLayout.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientListTableLayout.Location = new System.Drawing.Point(0, 0);
            this.patientListTableLayout.Name = "patientListTableLayout";
            this.patientListTableLayout.RowCount = 2;
            this.patientListTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 8F));
            this.patientListTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 80.73395F));
            this.patientListTableLayout.Size = new System.Drawing.Size(650, 332);
            this.patientListTableLayout.TabIndex = 0;
            // 
            // messagePatientLabel
            // 
            this.messagePatientLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.messagePatientLabel.AutoSize = true;
            this.messagePatientLabel.Location = new System.Drawing.Point(3, 7);
            this.messagePatientLabel.Name = "messagePatientLabel";
            this.messagePatientLabel.Size = new System.Drawing.Size(0, 15);
            this.messagePatientLabel.TabIndex = 0;
            // 
            // outerPanel
            // 
            this.outerPanel.BackColor = System.Drawing.SystemColors.Control;
            this.outerPanel.Controls.Add(this.grid);
            this.outerPanel.Controls.Add(this.searchCountPanel);
            this.outerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.outerPanel.Location = new System.Drawing.Point(3, 32);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.Size = new System.Drawing.Size(644, 297);
            this.outerPanel.TabIndex = 1;
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
            this.grid.Name = "grid";
            this.grid.ReadOnly = true;
            this.grid.RowHeadersVisible = false;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(644, 269);
            this.grid.SortEnabled = false;
            this.grid.StandardTab = true;
            this.grid.TabIndex = 0;
            // 
            // searchCountPanel
            // 
            this.searchCountPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(215)))), ((int)(((byte)(225)))));
            this.searchCountPanel.Controls.Add(this.searchCountPanel1);
            this.searchCountPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.searchCountPanel.Location = new System.Drawing.Point(0, 0);
            this.searchCountPanel.Name = "searchCountPanel";
            this.searchCountPanel.Padding = new System.Windows.Forms.Padding(0, 2, 0, 0);
            this.searchCountPanel.Size = new System.Drawing.Size(644, 28);
            this.searchCountPanel.TabIndex = 0;
            // 
            // searchCountPanel1
            // 
            this.searchCountPanel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(236)))), ((int)(((byte)(236)))));
            this.searchCountPanel1.Controls.Add(this.searchCountLabel);
            this.searchCountPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.searchCountPanel1.Location = new System.Drawing.Point(0, 2);
            this.searchCountPanel1.Name = "searchCountPanel1";
            this.searchCountPanel1.Size = new System.Drawing.Size(644, 26);
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
            // PatientListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.Controls.Add(this.patientListTableLayout);
            this.Name = "PatientListUI";
            this.Size = new System.Drawing.Size(650, 332);
            this.patientListTableLayout.ResumeLayout(false);
            this.patientListTableLayout.PerformLayout();
            this.outerPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.searchCountPanel.ResumeLayout(false);
            this.searchCountPanel1.ResumeLayout(false);
            this.searchCountPanel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel patientListTableLayout;
        private System.Windows.Forms.Label messagePatientLabel;
        private System.Windows.Forms.Panel outerPanel;
        private System.Windows.Forms.Panel searchCountPanel;
        private System.Windows.Forms.Panel searchCountPanel1;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.Label searchCountLabel;
    }
}
