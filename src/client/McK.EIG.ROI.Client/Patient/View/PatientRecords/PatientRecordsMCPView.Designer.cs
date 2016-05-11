namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    partial class PatientRecordsMCPView
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
            this.setUpdateButton = new System.Windows.Forms.Button();
            this.filterByEncounterLabel = new System.Windows.Forms.Label();
            this.filterLabel = new System.Windows.Forms.Label();
            this.innerPanel = new System.Windows.Forms.Panel();
            this.patientRecordsGroupBox = new System.Windows.Forms.GroupBox();
            this.patientRecordsTreeUI = new McK.EIG.ROI.Client.Patient.View.PatientRecords.PatientRecordsViewUI();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.topPanel.SuspendLayout();
            this.innerPanel.SuspendLayout();
            this.patientRecordsGroupBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Controls.Add(this.setUpdateButton);
            this.topPanel.Controls.Add(this.filterByEncounterLabel);
            this.topPanel.Controls.Add(this.filterLabel);
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.topPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.topPanel.Location = new System.Drawing.Point(0, 0);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(623, 42);
            this.topPanel.TabIndex = 0;
            // 
            // setUpdateButton
            // 
            this.setUpdateButton.Enabled = false;
            this.setUpdateButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.setUpdateButton.Location = new System.Drawing.Point(126, 7);
            this.setUpdateButton.Name = "setUpdateButton";
            this.setUpdateButton.Size = new System.Drawing.Size(84, 23);
            this.setUpdateButton.TabIndex = 7;
            this.setUpdateButton.UseVisualStyleBackColor = true;
            this.setUpdateButton.Click += new System.EventHandler(this.setUpdateButton_Click);
            // 
            // filterByEncounterLabel
            // 
            this.filterByEncounterLabel.AutoSize = true;
            this.filterByEncounterLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.filterByEncounterLabel.Location = new System.Drawing.Point(3, 11);
            this.filterByEncounterLabel.Name = "filterByEncounterLabel";
            this.filterByEncounterLabel.Size = new System.Drawing.Size(0, 15);
            this.filterByEncounterLabel.TabIndex = 8;
            // 
            // filterLabel
            // 
            this.filterLabel.AutoSize = true;
            this.filterLabel.Enabled = false;
            this.filterLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.filterLabel.Location = new System.Drawing.Point(102, 11);
            this.filterLabel.Name = "filterLabel";
            this.filterLabel.Size = new System.Drawing.Size(0, 15);
            this.filterLabel.TabIndex = 9;
            // 
            // innerPanel
            // 
            this.innerPanel.AutoSize = true;
            this.innerPanel.Controls.Add(this.patientRecordsGroupBox);
            this.innerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.innerPanel.Location = new System.Drawing.Point(0, 42);
            this.innerPanel.Name = "innerPanel";
            this.innerPanel.Size = new System.Drawing.Size(623, 395);
            this.innerPanel.TabIndex = 2;
            // 
            // patientRecordsGroupBox
            // 
            this.patientRecordsGroupBox.Controls.Add(this.patientRecordsTreeUI);
            this.patientRecordsGroupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientRecordsGroupBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientRecordsGroupBox.Location = new System.Drawing.Point(0, 0);
            this.patientRecordsGroupBox.Name = "patientRecordsGroupBox";
            this.patientRecordsGroupBox.Size = new System.Drawing.Size(623, 395);
            this.patientRecordsGroupBox.TabIndex = 1;
            this.patientRecordsGroupBox.TabStop = false;
            // 
            // patientRecordsTreeUI
            // 
            this.patientRecordsTreeUI.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientRecordsTreeUI.EnableEditDocumentButton = true;
            this.patientRecordsTreeUI.EnableNewDocumentButton = true;
            this.patientRecordsTreeUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientRecordsTreeUI.Location = new System.Drawing.Point(3, 17);
            this.patientRecordsTreeUI.Name = "patientRecordsTreeUI";
            this.patientRecordsTreeUI.SelectedEncounters = null;
            this.patientRecordsTreeUI.ShowButtons = false;
            this.patientRecordsTreeUI.Size = new System.Drawing.Size(617, 375);
            this.patientRecordsTreeUI.TabIndex = 0;
            // 
            // errorProvider
            // 
            this.errorProvider.ContainerControl = this;
            // 
            // PatientRecordsMCPView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.innerPanel);
            this.Controls.Add(this.topPanel);
            this.Name = "PatientRecordsMCPView";
            this.Size = new System.Drawing.Size(623, 437);
            this.topPanel.ResumeLayout(false);
            this.topPanel.PerformLayout();
            this.innerPanel.ResumeLayout(false);
            this.patientRecordsGroupBox.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Button setUpdateButton;
        private System.Windows.Forms.Label filterByEncounterLabel;
        private System.Windows.Forms.Label filterLabel;
        private System.Windows.Forms.Panel innerPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.GroupBox patientRecordsGroupBox;
        private PatientRecordsViewUI patientRecordsTreeUI;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
