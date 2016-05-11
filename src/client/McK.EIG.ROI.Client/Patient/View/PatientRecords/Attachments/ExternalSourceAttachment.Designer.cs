namespace McK.EIG.ROI.Client.Patient.View.PatientRecords.Attachments
{
    partial class ExternalSourceAttachment
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle5 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle6 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle7 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle8 = new System.Windows.Forms.DataGridViewCellStyle();
            this.EncountersGrid = new McK.EIG.ROI.Client.Admin.View.AdminDataGrid(this.components);
            this.addSelectedbutton = new System.Windows.Forms.Button();
            this.attacmentResultLabel = new System.Windows.Forms.Label();
            this.clinicalSystemLabel = new System.Windows.Forms.Label();
            this.clinicalSystemValueLabel = new System.Windows.Forms.Label();
            this.ProgressbarPanel = new System.Windows.Forms.Panel();
            this.percentageLabel = new System.Windows.Forms.Label();
            this.fileTransferProgress = new System.Windows.Forms.ProgressBar();
            this.gatheringDoclabel = new System.Windows.Forms.Label();
            this.statusLabel = new System.Windows.Forms.Label();
            this.errorTextBox = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.EncountersGrid)).BeginInit();
            this.ProgressbarPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // EncountersGrid
            // 
            this.EncountersGrid.AllowUserToAddRows = false;
            this.EncountersGrid.AllowUserToDeleteRows = false;
            this.EncountersGrid.AllowUserToResizeRows = false;
            dataGridViewCellStyle5.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(237)))), ((int)(((byte)(241)))), ((int)(((byte)(246)))));
            this.EncountersGrid.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle5;
            this.EncountersGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.EncountersGrid.BackgroundColor = System.Drawing.Color.White;
            this.EncountersGrid.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.SingleVertical;
            this.EncountersGrid.ChangeValidator = null;
            dataGridViewCellStyle6.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle6.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle6.Font = new System.Drawing.Font("Arial", 9F);
            dataGridViewCellStyle6.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle6.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle6.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle6.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.EncountersGrid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle6;
            this.EncountersGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.EncountersGrid.ConfirmSelection = false;
            dataGridViewCellStyle7.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle7.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle7.Font = new System.Drawing.Font("Arial", 9F);
            dataGridViewCellStyle7.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle7.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle7.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle7.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.EncountersGrid.DefaultCellStyle = dataGridViewCellStyle7;
            this.EncountersGrid.Location = new System.Drawing.Point(8, 75);
            this.EncountersGrid.Name = "EncountersGrid";
            this.EncountersGrid.ReadOnly = true;
            dataGridViewCellStyle8.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle8.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle8.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle8.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle8.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle8.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.EncountersGrid.RowHeadersDefaultCellStyle = dataGridViewCellStyle8;
            this.EncountersGrid.RowHeadersVisible = false;
            this.EncountersGrid.RowTemplate.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.EncountersGrid.SelectionHandler = null;
            this.EncountersGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.EncountersGrid.Size = new System.Drawing.Size(480, 158);
            this.EncountersGrid.SortEnabled = false;
            this.EncountersGrid.StandardTab = true;
            this.EncountersGrid.TabIndex = 24;
            // 
            // addSelectedbutton
            // 
            this.addSelectedbutton.Location = new System.Drawing.Point(517, 98);
            this.addSelectedbutton.Name = "addSelectedbutton";
            this.addSelectedbutton.Size = new System.Drawing.Size(105, 23);
            this.addSelectedbutton.TabIndex = 25;
            this.addSelectedbutton.UseVisualStyleBackColor = true;
            this.addSelectedbutton.Click += new System.EventHandler(this.AddSelectedbutton_Click);
            // 
            // attacmentResultLabel
            // 
            this.attacmentResultLabel.AutoSize = true;
            this.attacmentResultLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.attacmentResultLabel.Location = new System.Drawing.Point(501, 192);
            this.attacmentResultLabel.MaximumSize = new System.Drawing.Size(160, 0);
            this.attacmentResultLabel.Name = "attacmentResultLabel";
            this.attacmentResultLabel.Size = new System.Drawing.Size(0, 15);
            this.attacmentResultLabel.TabIndex = 44;
            this.attacmentResultLabel.Visible = false;
            // 
            // clinicalSystemLabel
            // 
            this.clinicalSystemLabel.AutoSize = true;
            this.clinicalSystemLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.clinicalSystemLabel.Location = new System.Drawing.Point(12, 43);
            this.clinicalSystemLabel.Name = "clinicalSystemLabel";
            this.clinicalSystemLabel.Size = new System.Drawing.Size(0, 15);
            this.clinicalSystemLabel.TabIndex = 45;
            // 
            // clinicalSystemValueLabel
            // 
            this.clinicalSystemValueLabel.AutoSize = true;
            this.clinicalSystemValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.clinicalSystemValueLabel.Location = new System.Drawing.Point(115, 43);
            this.clinicalSystemValueLabel.Name = "clinicalSystemValueLabel";
            this.clinicalSystemValueLabel.Size = new System.Drawing.Size(0, 15);
            this.clinicalSystemValueLabel.TabIndex = 46;
            // 
            // ProgressbarPanel
            // 
            this.ProgressbarPanel.Controls.Add(this.percentageLabel);
            this.ProgressbarPanel.Controls.Add(this.fileTransferProgress);
            this.ProgressbarPanel.Location = new System.Drawing.Point(494, 158);
            this.ProgressbarPanel.Name = "ProgressbarPanel";
            this.ProgressbarPanel.Size = new System.Drawing.Size(163, 20);
            this.ProgressbarPanel.TabIndex = 47;
            // 
            // percentageLabel
            // 
            this.percentageLabel.AutoSize = true;
            this.percentageLabel.BackColor = System.Drawing.Color.Transparent;
            this.percentageLabel.Location = new System.Drawing.Point(79, 2);
            this.percentageLabel.Name = "percentageLabel";
            this.percentageLabel.Size = new System.Drawing.Size(0, 13);
            this.percentageLabel.TabIndex = 1;
            // 
            // fileTransferProgress
            // 
            this.fileTransferProgress.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(240)))), ((int)(((byte)(240)))), ((int)(((byte)(240)))));
            this.fileTransferProgress.Dock = System.Windows.Forms.DockStyle.Fill;
            this.fileTransferProgress.Location = new System.Drawing.Point(0, 0);
            this.fileTransferProgress.Name = "fileTransferProgress";
            this.fileTransferProgress.Size = new System.Drawing.Size(163, 20);
            this.fileTransferProgress.Style = System.Windows.Forms.ProgressBarStyle.Continuous;
            this.fileTransferProgress.TabIndex = 0;
            // 
            // gatheringDoclabel
            // 
            this.gatheringDoclabel.AutoSize = true;
            this.gatheringDoclabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.gatheringDoclabel.Location = new System.Drawing.Point(500, 135);
            this.gatheringDoclabel.Name = "gatheringDoclabel";
            this.gatheringDoclabel.Size = new System.Drawing.Size(0, 15);
            this.gatheringDoclabel.TabIndex = 48;
            this.gatheringDoclabel.Visible = false;
            // 
            // statusLabel
            // 
            this.statusLabel.AutoSize = true;
            this.statusLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.statusLabel.ForeColor = System.Drawing.Color.Black;
            this.statusLabel.Location = new System.Drawing.Point(14, 243);
            this.statusLabel.Name = "statusLabel";
            this.statusLabel.Size = new System.Drawing.Size(0, 15);
            this.statusLabel.TabIndex = 51;
            this.statusLabel.Visible = false;
            // 
            // errorTextBox
            // 
            this.errorTextBox.AcceptsReturn = true;
            this.errorTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.errorTextBox.Location = new System.Drawing.Point(8, 264);
            this.errorTextBox.Multiline = true;
            this.errorTextBox.Name = "errorTextBox";
            this.errorTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Horizontal;
            this.errorTextBox.Size = new System.Drawing.Size(652, 79);
            this.errorTextBox.TabIndex = 52;
            this.errorTextBox.Visible = false;
            this.errorTextBox.WordWrap = false;
            // 
            // ExternalSourceAttachment
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.errorTextBox);
            this.Controls.Add(this.statusLabel);
            this.Controls.Add(this.gatheringDoclabel);
            this.Controls.Add(this.ProgressbarPanel);
            this.Controls.Add(this.clinicalSystemValueLabel);
            this.Controls.Add(this.clinicalSystemLabel);
            this.Controls.Add(this.attacmentResultLabel);
            this.Controls.Add(this.addSelectedbutton);
            this.Controls.Add(this.EncountersGrid);
            this.Name = "ExternalSourceAttachment";
            this.Size = new System.Drawing.Size(761, 343);
            ((System.ComponentModel.ISupportInitialize)(this.EncountersGrid)).EndInit();
            this.ProgressbarPanel.ResumeLayout(false);
            this.ProgressbarPanel.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        internal McK.EIG.ROI.Client.Admin.View.AdminDataGrid EncountersGrid;
        private System.Windows.Forms.Button addSelectedbutton;
        private System.Windows.Forms.Label attacmentResultLabel;
        private System.Windows.Forms.Label clinicalSystemLabel;
        private System.Windows.Forms.Label clinicalSystemValueLabel;
        private System.Windows.Forms.Panel ProgressbarPanel;
        private System.Windows.Forms.Label gatheringDoclabel;
        private System.Windows.Forms.Label percentageLabel;
        private System.Windows.Forms.ProgressBar fileTransferProgress;
        private System.Windows.Forms.Label statusLabel;
        private System.Windows.Forms.TextBox errorTextBox;
    }
}
