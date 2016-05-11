namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    partial class RequestPatientInfoUI
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
            this.patientInfoTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.selectedPatientsGroupBox = new System.Windows.Forms.GroupBox();
            this.treeTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.patientListPanel = new System.Windows.Forms.Panel();
            this.patientListUI = new McK.EIG.ROI.Client.Request.View.RequestPatientListUI();
            this.patientRecordTreePanel = new System.Windows.Forms.Panel();
            this.loadingCircle = new McK.EIG.ROI.Client.Base.View.Common.LoadingCircle();
            this.patientRecordsViewUI = new McK.EIG.ROI.Client.Patient.View.PatientRecords.PatientRecordsViewUI();
            this.dsrTreeUI = new McK.EIG.ROI.Client.Request.View.PatientInfo.ReleaseTreeUI();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.patientInfoTableLayoutPanel.SuspendLayout();
            this.selectedPatientsGroupBox.SuspendLayout();
            this.treeTableLayoutPanel.SuspendLayout();
            this.patientListPanel.SuspendLayout();
            this.patientRecordTreePanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // patientInfoTableLayoutPanel
            // 
            this.patientInfoTableLayoutPanel.ColumnCount = 2;
            this.patientInfoTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 64.47219F));
            this.patientInfoTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 35.52781F));
            this.patientInfoTableLayoutPanel.Controls.Add(this.selectedPatientsGroupBox, 0, 0);
            this.patientInfoTableLayoutPanel.Controls.Add(this.dsrTreeUI, 1, 0);
            this.patientInfoTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientInfoTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.patientInfoTableLayoutPanel.Name = "patientInfoTableLayoutPanel";
            this.patientInfoTableLayoutPanel.RowCount = 1;
            this.patientInfoTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.patientInfoTableLayoutPanel.Size = new System.Drawing.Size(881, 645);
            this.patientInfoTableLayoutPanel.TabIndex = 5;
            // 
            // selectedPatientsGroupBox
            // 
            this.selectedPatientsGroupBox.Controls.Add(this.treeTableLayoutPanel);
            this.selectedPatientsGroupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.selectedPatientsGroupBox.Location = new System.Drawing.Point(3, 3);
            this.selectedPatientsGroupBox.Name = "selectedPatientsGroupBox";
            this.selectedPatientsGroupBox.Size = new System.Drawing.Size(562, 639);
            this.selectedPatientsGroupBox.TabIndex = 10;
            this.selectedPatientsGroupBox.TabStop = false;
            // 
            // treeTableLayoutPanel
            // 
            this.treeTableLayoutPanel.ColumnCount = 1;
            this.treeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.treeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.treeTableLayoutPanel.Controls.Add(this.patientListPanel, 0, 0);
            this.treeTableLayoutPanel.Controls.Add(this.patientRecordTreePanel, 0, 1);
            this.treeTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeTableLayoutPanel.Location = new System.Drawing.Point(3, 17);
            this.treeTableLayoutPanel.Name = "treeTableLayoutPanel";
            this.treeTableLayoutPanel.RowCount = 2;
            this.treeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 27.9483F));
            this.treeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 72.0517F));
            this.treeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.treeTableLayoutPanel.Size = new System.Drawing.Size(556, 619);
            this.treeTableLayoutPanel.TabIndex = 1;
            // 
            // patientListPanel
            // 
            this.patientListPanel.BackColor = System.Drawing.Color.Silver;
            this.patientListPanel.Controls.Add(this.patientListUI);
            this.patientListPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientListPanel.Location = new System.Drawing.Point(3, 3);
            this.patientListPanel.Margin = new System.Windows.Forms.Padding(3, 3, 3, 0);
            this.patientListPanel.Name = "patientListPanel";
            this.patientListPanel.Padding = new System.Windows.Forms.Padding(1);
            this.patientListPanel.Size = new System.Drawing.Size(550, 169);
            this.patientListPanel.TabIndex = 0;
            // 
            // patientListUI
            // 
            this.patientListUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(246)))), ((int)(((byte)(236)))));
            this.patientListUI.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientListUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientListUI.Location = new System.Drawing.Point(1, 1);
            this.patientListUI.Name = "patientListUI";
            this.patientListUI.Padding = new System.Windows.Forms.Padding(3);
            this.patientListUI.PatientSelectionHandler = null;
            this.patientListUI.Size = new System.Drawing.Size(548, 167);
            this.patientListUI.TabIndex = 0;
            // 
            // patientRecordTreePanel
            // 
            this.patientRecordTreePanel.BackColor = System.Drawing.Color.Silver;
            this.patientRecordTreePanel.Controls.Add(this.loadingCircle);
            this.patientRecordTreePanel.Controls.Add(this.patientRecordsViewUI);
            this.patientRecordTreePanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientRecordTreePanel.Location = new System.Drawing.Point(3, 175);
            this.patientRecordTreePanel.Name = "patientRecordTreePanel";
            this.patientRecordTreePanel.Padding = new System.Windows.Forms.Padding(1);
            this.patientRecordTreePanel.Size = new System.Drawing.Size(550, 441);
            this.patientRecordTreePanel.TabIndex = 1;
            // 
            // loadingCircle
            // 
            this.loadingCircle.Active = false;
            this.loadingCircle.BackColor = System.Drawing.Color.Transparent;
            this.loadingCircle.Color = System.Drawing.Color.ForestGreen;
            this.loadingCircle.InnerCircleRadius = 5;
            this.loadingCircle.Location = new System.Drawing.Point(168, 134);
            this.loadingCircle.Name = "loadingCircle";
            this.loadingCircle.NumberSpoke = 12;
            this.loadingCircle.OuterCircleRadius = 11;
            this.loadingCircle.RotationSpeed = 100;
            this.loadingCircle.Size = new System.Drawing.Size(46, 38);
            this.loadingCircle.SpokeThickness = 2;
            this.loadingCircle.StylePreset = McK.EIG.ROI.Client.Base.View.Common.LoadingCircle.PresetStyle.Theme1;
            this.loadingCircle.TabIndex = 1;
            this.loadingCircle.Text = "Loading...";
            // 
            // patientRecordsViewUI
            // 
            this.patientRecordsViewUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(246)))), ((int)(((byte)(236)))));
            this.patientRecordsViewUI.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientRecordsViewUI.EnableEditDocumentButton = false;
            this.patientRecordsViewUI.EnableNewDocumentButton = false;
            this.patientRecordsViewUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientRecordsViewUI.Location = new System.Drawing.Point(1, 1);
            this.patientRecordsViewUI.Name = "patientRecordsViewUI";
            this.patientRecordsViewUI.SelectedEncounters = null;
            this.patientRecordsViewUI.ShowButtons = false;
            this.patientRecordsViewUI.Size = new System.Drawing.Size(548, 439);
            this.patientRecordsViewUI.TabIndex = 2;
            // 
            // dsrTreeUI
            // 
            this.dsrTreeUI.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dsrTreeUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dsrTreeUI.Location = new System.Drawing.Point(571, 3);
            this.dsrTreeUI.Name = "dsrTreeUI";
            this.dsrTreeUI.Size = new System.Drawing.Size(307, 639);
            this.dsrTreeUI.TabIndex = 11;
            // 
            // RequestPatientInfoUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.patientInfoTableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "RequestPatientInfoUI";
            this.Size = new System.Drawing.Size(881, 645);
            this.patientInfoTableLayoutPanel.ResumeLayout(false);
            this.selectedPatientsGroupBox.ResumeLayout(false);
            this.treeTableLayoutPanel.ResumeLayout(false);
            this.patientListPanel.ResumeLayout(false);
            this.patientRecordTreePanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel patientInfoTableLayoutPanel;
        private ReleaseTreeUI dsrTreeUI;
        private System.Windows.Forms.GroupBox selectedPatientsGroupBox;
        private System.Windows.Forms.TableLayoutPanel treeTableLayoutPanel;
        private System.Windows.Forms.Panel patientListPanel;
        private RequestPatientListUI patientListUI;
        private System.Windows.Forms.Panel patientRecordTreePanel;
        private McK.EIG.ROI.Client.Patient.View.PatientRecords.PatientRecordsViewUI patientRecordsViewUI;
        private McK.EIG.ROI.Client.Base.View.Common.LoadingCircle loadingCircle;
        private System.Windows.Forms.ToolTip toolTip;     
    }
}
