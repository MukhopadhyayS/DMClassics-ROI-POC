namespace McK.EIG.ROI.Client.Reports.View
{
    partial class MUReleaseTurnaroundTimeUI
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MUReleaseTurnaroundTimeUI));
            this.toppanel = new System.Windows.Forms.Panel();
            this.reportCriterialabel = new System.Windows.Forms.Label();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.searchCriteriapanel = new System.Windows.Forms.Panel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.DateRangeUI = new McK.EIG.ROI.Client.Reports.View.ReportDateRangeUI();
            this.docTypepanel = new System.Windows.Forms.Panel();
            this.documentTypelabel = new System.Windows.Forms.Label();
            this.docTypecomboBox = new System.Windows.Forms.ComboBox();
            this.facilitypanel = new System.Windows.Forms.Panel();
            this.facilitylabel = new System.Windows.Forms.Label();
            this.facilityCheckedListUI = new McK.EIG.ROI.Client.Reports.View.ReportCheckedListUI();
            this.toppanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            this.searchCriteriapanel.SuspendLayout();
            this.panel1.SuspendLayout();
            this.docTypepanel.SuspendLayout();
            this.facilitypanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // toppanel
            // 
            this.toppanel.Controls.Add(this.reportCriterialabel);
            this.toppanel.Controls.Add(this.requiredImage);
            this.toppanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.toppanel.Location = new System.Drawing.Point(0, 0);
            this.toppanel.Name = "toppanel";
            this.toppanel.Size = new System.Drawing.Size(249, 25);
            this.toppanel.TabIndex = 0;
            // 
            // reportCriterialabel
            // 
            this.reportCriterialabel.AutoSize = true;
            this.reportCriterialabel.Location = new System.Drawing.Point(20, 4);
            this.reportCriterialabel.Name = "reportCriterialabel";
            this.reportCriterialabel.Size = new System.Drawing.Size(74, 13);
            this.reportCriterialabel.TabIndex = 0;
            this.reportCriterialabel.Text = "Report Criteria";
            // 
            // requiredImage
            // 
            this.requiredImage.Image = ((System.Drawing.Image)(resources.GetObject("requiredImage.Image")));
            this.requiredImage.Location = new System.Drawing.Point(4, 4);
            this.requiredImage.Margin = new System.Windows.Forms.Padding(4);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(14, 14);
            this.requiredImage.TabIndex = 9;
            this.requiredImage.TabStop = false;
            // 
            // searchCriteriapanel
            // 
            this.searchCriteriapanel.Controls.Add(this.panel1);
            this.searchCriteriapanel.Controls.Add(this.docTypepanel);
            this.searchCriteriapanel.Controls.Add(this.facilitypanel);
            this.searchCriteriapanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.searchCriteriapanel.Location = new System.Drawing.Point(0, 25);
            this.searchCriteriapanel.Name = "searchCriteriapanel";
            this.searchCriteriapanel.Size = new System.Drawing.Size(249, 432);
            this.searchCriteriapanel.TabIndex = 1;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.DateRangeUI);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 194);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(249, 148);
            this.panel1.TabIndex = 6;
            // 
            // DateRangeUI
            // 
            this.DateRangeUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.DateRangeUI.Dock = System.Windows.Forms.DockStyle.Top;
            this.DateRangeUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.DateRangeUI.IsValidDateRange = false;
            this.DateRangeUI.Location = new System.Drawing.Point(0, 0);
            this.DateRangeUI.Name = "DateRangeUI";
            this.DateRangeUI.Size = new System.Drawing.Size(249, 123);
            this.DateRangeUI.TabIndex = 3;
            // 
            // docTypepanel
            // 
            this.docTypepanel.Controls.Add(this.documentTypelabel);
            this.docTypepanel.Controls.Add(this.docTypecomboBox);
            this.docTypepanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.docTypepanel.Location = new System.Drawing.Point(0, 157);
            this.docTypepanel.Name = "docTypepanel";
            this.docTypepanel.Size = new System.Drawing.Size(249, 37);
            this.docTypepanel.TabIndex = 5;
            // 
            // documentTypelabel
            // 
            this.documentTypelabel.AutoSize = true;
            this.documentTypelabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.documentTypelabel.Location = new System.Drawing.Point(0, 0);
            this.documentTypelabel.Name = "documentTypelabel";
            this.documentTypelabel.Size = new System.Drawing.Size(83, 13);
            this.documentTypelabel.TabIndex = 2;
            this.documentTypelabel.Text = "Document Type";
            // 
            // docTypecomboBox
            // 
            this.docTypecomboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.docTypecomboBox.FormattingEnabled = true;
            this.docTypecomboBox.Location = new System.Drawing.Point(0, 16);
            this.docTypecomboBox.Name = "docTypecomboBox";
            this.docTypecomboBox.Size = new System.Drawing.Size(209, 21);
            this.docTypecomboBox.TabIndex = 1;
            this.docTypecomboBox.SelectedIndexChanged += new System.EventHandler(this.docTypecomboBox_SelectedIndexChanged);
            // 
            // facilitypanel
            // 
            this.facilitypanel.Controls.Add(this.facilitylabel);
            this.facilitypanel.Controls.Add(this.facilityCheckedListUI);
            this.facilitypanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.facilitypanel.Location = new System.Drawing.Point(0, 0);
            this.facilitypanel.Name = "facilitypanel";
            this.facilitypanel.Size = new System.Drawing.Size(249, 157);
            this.facilitypanel.TabIndex = 4;
            // 
            // facilitylabel
            // 
            this.facilitylabel.AutoSize = true;
            this.facilitylabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.facilitylabel.Location = new System.Drawing.Point(0, 0);
            this.facilitylabel.Name = "facilitylabel";
            this.facilitylabel.Size = new System.Drawing.Size(39, 13);
            this.facilitylabel.TabIndex = 5;
            this.facilitylabel.Text = "Facility";
            // 
            // facilityCheckedListUI
            // 
            this.facilityCheckedListUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.facilityCheckedListUI.Dock = System.Windows.Forms.DockStyle.Fill;
            this.facilityCheckedListUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityCheckedListUI.Location = new System.Drawing.Point(0, 0);
            this.facilityCheckedListUI.Name = "facilityCheckedListUI";
            this.facilityCheckedListUI.Size = new System.Drawing.Size(249, 157);
            this.facilityCheckedListUI.TabIndex = 0;
            this.facilityCheckedListUI.TitleKey = null;
            // 
            // MUReleaseTurnaroundTimeUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.searchCriteriapanel);
            this.Controls.Add(this.toppanel);
            this.Name = "MUReleaseTurnaroundTimeUI";
            this.Size = new System.Drawing.Size(249, 457);
            this.toppanel.ResumeLayout(false);
            this.toppanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            this.searchCriteriapanel.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.docTypepanel.ResumeLayout(false);
            this.docTypepanel.PerformLayout();
            this.facilitypanel.ResumeLayout(false);
            this.facilitypanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel toppanel;
        private System.Windows.Forms.Panel searchCriteriapanel;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Label reportCriterialabel;
        private ReportCheckedListUI facilityCheckedListUI;
        private System.Windows.Forms.Label documentTypelabel;
        private ReportDateRangeUI DateRangeUI;
        private System.Windows.Forms.Panel facilitypanel;
        private System.Windows.Forms.Panel docTypepanel;
        private System.Windows.Forms.Label facilitylabel;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.ComboBox docTypecomboBox;
    }
}
