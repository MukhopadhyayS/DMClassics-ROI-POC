namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ReportBaseCriteriaUI
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ReportBaseCriteriaUI));
            this.searchCriteriaPanel = new System.Windows.Forms.Panel();
            this.reportDateRangeUI = new McK.EIG.ROI.Client.Reports.View.ReportDateRangeUI();
            this.balanceDuePanel = new System.Windows.Forms.Panel();
            this.balanceDueTextBox = new System.Windows.Forms.TextBox();
            this.dollarLabel = new System.Windows.Forms.Label();
            this.balanceDueComboBox = new System.Windows.Forms.ComboBox();
            this.balanceDueLabel = new System.Windows.Forms.Label();
            this.rangeBarPanel = new System.Windows.Forms.Panel();
            this.requiredPictureBox = new System.Windows.Forms.PictureBox();
            this.agingLabel = new System.Windows.Forms.Label();
            this.rangeBar = new McK.EIG.ROI.Client.Base.View.Common.RangeBar();
            if ((reportInputFieldisRequired) && (isOutstandingReport))
                this.reportThirdCheckedListUI = new McK.EIG.ROI.Client.Reports.View.ReportCheckedListUI(true);
            else
                this.reportThirdCheckedListUI = new McK.EIG.ROI.Client.Reports.View.ReportCheckedListUI();
            this.reportInputFieldUI = new McK.EIG.ROI.Client.Reports.View.ReportInputFieldUI();
            if ((reportInputFieldisRequired) && (isOutstandingReport))
                this.reportSecondCheckedListUI = new McK.EIG.ROI.Client.Reports.View.ReportCheckedListUI(true);
            else
                this.reportSecondCheckedListUI = new McK.EIG.ROI.Client.Reports.View.ReportCheckedListUI();
            this.statusToPanel = new System.Windows.Forms.Panel();
            this.statusToLabel = new System.Windows.Forms.Label();
            this.statusToComboBox = new System.Windows.Forms.ComboBox();
            this.statusPanel = new System.Windows.Forms.Panel();
            this.fromStatusLabel = new System.Windows.Forms.Label();
            this.statusComboBox = new System.Windows.Forms.ComboBox();
            if (reportInputFieldisRequired)
                this.reportFirstCheckedListUI = new McK.EIG.ROI.Client.Reports.View.ReportCheckedListUI(true);
            else
                this.reportFirstCheckedListUI = new McK.EIG.ROI.Client.Reports.View.ReportCheckedListUI();
            this.mrnInfoPanel = new System.Windows.Forms.Panel();
            this.mrnTextBox = new System.Windows.Forms.TextBox();
            this.mrnLabel = new System.Windows.Forms.Label();
            this.topPanel = new System.Windows.Forms.Panel();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.reportCriteriaLabel = new System.Windows.Forms.Label();
            this.searchCriteriaPanel.SuspendLayout();
            this.balanceDuePanel.SuspendLayout();
            this.rangeBarPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredPictureBox)).BeginInit();
            this.statusToPanel.SuspendLayout();
            this.statusPanel.SuspendLayout();
            this.mrnInfoPanel.SuspendLayout();
            this.topPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            this.SuspendLayout();
            // 
            // searchCriteriaPanel
            // 
            this.searchCriteriaPanel.Controls.Add(this.reportDateRangeUI);
            this.searchCriteriaPanel.Controls.Add(this.balanceDuePanel);
            this.searchCriteriaPanel.Controls.Add(this.rangeBarPanel);
            this.searchCriteriaPanel.Controls.Add(this.reportThirdCheckedListUI);
            if (reportInputFieldisRequired) this.searchCriteriaPanel.Controls.Add(this.reportInputFieldUI);
            this.searchCriteriaPanel.Controls.Add(this.reportSecondCheckedListUI);
            this.searchCriteriaPanel.Controls.Add(this.statusToPanel);
            this.searchCriteriaPanel.Controls.Add(this.statusPanel);
            this.searchCriteriaPanel.Controls.Add(this.reportFirstCheckedListUI);
            this.searchCriteriaPanel.Controls.Add(this.mrnInfoPanel);
            this.searchCriteriaPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.searchCriteriaPanel.Location = new System.Drawing.Point(0, 25);
            this.searchCriteriaPanel.Name = "searchCriteriaPanel";
            this.searchCriteriaPanel.Size = new System.Drawing.Size(285, 830);
            this.searchCriteriaPanel.TabIndex = 1;
            // 
            // reportDateRangeUI
            // 
            this.reportDateRangeUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.reportDateRangeUI.Dock = System.Windows.Forms.DockStyle.Top;
            this.reportDateRangeUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportDateRangeUI.IsValidDateRange = false;
            this.reportDateRangeUI.Location = new System.Drawing.Point(0, 706);
            this.reportDateRangeUI.Margin = new System.Windows.Forms.Padding(3, 3, 3, 0);
            this.reportDateRangeUI.Name = "reportDateRangeUI";
            this.reportDateRangeUI.Size = new System.Drawing.Size(285, 124);
            this.reportDateRangeUI.TabIndex = 62;
            // 
            // balanceDuePanel
            // 
            this.balanceDuePanel.Controls.Add(this.balanceDueTextBox);
            this.balanceDuePanel.Controls.Add(this.dollarLabel);
            this.balanceDuePanel.Controls.Add(this.balanceDueComboBox);
            this.balanceDuePanel.Controls.Add(this.balanceDueLabel);
            this.balanceDuePanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.balanceDuePanel.Location = new System.Drawing.Point(0, 671);
            this.balanceDuePanel.Name = "balanceDuePanel";
            this.balanceDuePanel.Size = new System.Drawing.Size(285, 35);
            this.balanceDuePanel.TabIndex = 61;
            this.balanceDuePanel.Visible = false;
            // 
            // balanceDueTextBox
            // 
            this.balanceDueTextBox.Location = new System.Drawing.Point(179, 6);
            this.balanceDueTextBox.MaxLength = 9;
            this.balanceDueTextBox.Name = "balanceDueTextBox";
            this.balanceDueTextBox.Size = new System.Drawing.Size(53, 21);
            this.balanceDueTextBox.TabIndex = 3;
            this.balanceDueTextBox.TextChanged += new System.EventHandler(this.balanceDueTextBox_TextChanged);
            this.balanceDueTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.balanceDueTextBox_KeyPress);
            // 
            // dollarLabel
            // 
            this.dollarLabel.AutoSize = true;
            this.dollarLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dollarLabel.Location = new System.Drawing.Point(164, 6);
            this.dollarLabel.Name = "dollarLabel";
            this.dollarLabel.Size = new System.Drawing.Size(0, 15);
            this.dollarLabel.TabIndex = 2;
            // 
            // balanceDueComboBox
            // 
            this.balanceDueComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.balanceDueComboBox.FormattingEnabled = true;
            this.balanceDueComboBox.Location = new System.Drawing.Point(78, 6);
            this.balanceDueComboBox.Name = "balanceDueComboBox";
            this.balanceDueComboBox.Size = new System.Drawing.Size(85, 23);
            this.balanceDueComboBox.TabIndex = 1;
            // 
            // balanceDueLabel
            // 
            this.balanceDueLabel.AutoSize = true;
            this.balanceDueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.balanceDueLabel.Location = new System.Drawing.Point(0, 6);
            this.balanceDueLabel.Name = "balanceDueLabel";
            this.balanceDueLabel.Size = new System.Drawing.Size(0, 15);
            this.balanceDueLabel.TabIndex = 0;
            // 
            // rangeBarPanel
            // 
            if (reportInputFieldisRequired) this.rangeBarPanel.Controls.Add(this.requiredPictureBox);
            this.rangeBarPanel.Controls.Add(this.agingLabel);
            this.rangeBarPanel.Controls.Add(this.rangeBar);
            this.rangeBarPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.rangeBarPanel.Location = new System.Drawing.Point(0, 584);
            this.rangeBarPanel.Name = "rangeBarPanel";
            this.rangeBarPanel.Size = new System.Drawing.Size(285, 87);
            this.rangeBarPanel.TabIndex = 25;
            this.rangeBarPanel.Visible = false;
            // 
            // requiredPictureBox
            // 
            this.requiredPictureBox.Image = ((System.Drawing.Image)(resources.GetObject("requiredImage.Image")));
            this.requiredPictureBox.Location = new System.Drawing.Point(3, 4);
            this.requiredPictureBox.Name = "requiredPictureBox";
            this.requiredPictureBox.Size = new System.Drawing.Size(12, 12);
            this.requiredPictureBox.TabIndex = 14;
            this.requiredPictureBox.TabStop = false;
            // 
            // agingLabel
            // 
            this.agingLabel.AutoSize = true;
            this.agingLabel.Location = new System.Drawing.Point(16, 2);
            this.agingLabel.Name = "agingLabel";
            this.agingLabel.Size = new System.Drawing.Size(0, 15);
            this.agingLabel.TabIndex = 13;
            // 
            // rangeBar
            // 
            this.rangeBar.BackColor = System.Drawing.SystemColors.Window;
            this.rangeBar.DivisionNumber = 5;
            this.rangeBar.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.rangeBar.HeightOfBar = 8;
            this.rangeBar.HeightOfMark = 15;
            this.rangeBar.HeightOfTick = 0;
            this.rangeBar.InnerColor = System.Drawing.Color.CornflowerBlue;
            this.rangeBar.Left = 0D;
            this.rangeBar.Location = new System.Drawing.Point(0, 22);
            this.rangeBar.Name = "rangeBar";
            this.rangeBar.Orientation = McK.EIG.ROI.Client.Base.View.Common.RangeBar.RangeBarOrientation.Horizontal;
            this.rangeBar.RangeMaximum = 0;
            this.rangeBar.RangeMinimum = 0;
            this.rangeBar.Right = 0D;
            this.rangeBar.ScaleOrientation = McK.EIG.ROI.Client.Base.View.Common.RangeBar.TopBottomOrientation.Bottom;
            this.rangeBar.Size = new System.Drawing.Size(285, 65);
            this.rangeBar.TabIndex = 12;
            this.rangeBar.TotalMaximum = 160;
            this.rangeBar.TotalMinimum = 0;
            // 
            // reportThirdCheckedListUI
            // 
            this.reportThirdCheckedListUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.reportThirdCheckedListUI.Dock = System.Windows.Forms.DockStyle.Top;
            this.reportThirdCheckedListUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportThirdCheckedListUI.Location = new System.Drawing.Point(0, 469);
            this.reportThirdCheckedListUI.Name = "reportThirdCheckedListUI";
            this.reportThirdCheckedListUI.Size = new System.Drawing.Size(285, 115);
            this.reportThirdCheckedListUI.TabIndex = 24;
            this.reportThirdCheckedListUI.TitleKey = null;
            // 
            // reportInputFieldUI
            // 
			if (reportInputFieldisRequired)
            {
            this.reportInputFieldUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.reportInputFieldUI.Dock = System.Windows.Forms.DockStyle.Top;
            this.reportInputFieldUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportInputFieldUI.Location = new System.Drawing.Point(0, 409);
            this.reportInputFieldUI.Margin = new System.Windows.Forms.Padding(3, 3, 3, 0);
            this.reportInputFieldUI.Name = "reportInputFieldUI";
            this.reportInputFieldUI.Size = new System.Drawing.Size(285, 60);
            this.reportInputFieldUI.TabIndex = 23;
			}
            // 
            // reportSecondCheckedListUI
            // 
            this.reportSecondCheckedListUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.reportSecondCheckedListUI.Dock = System.Windows.Forms.DockStyle.Top;
            this.reportSecondCheckedListUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportSecondCheckedListUI.Location = new System.Drawing.Point(0, 294);
            this.reportSecondCheckedListUI.Name = "reportSecondCheckedListUI";
            this.reportSecondCheckedListUI.Size = new System.Drawing.Size(285, 115);
            this.reportSecondCheckedListUI.TabIndex = 19;
            this.reportSecondCheckedListUI.TitleKey = null;
            // 
            // statusToPanel
            // 
            this.statusToPanel.Controls.Add(this.statusToLabel);
            this.statusToPanel.Controls.Add(this.statusToComboBox);
            this.statusToPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.statusToPanel.Location = new System.Drawing.Point(0, 238);
            this.statusToPanel.Margin = new System.Windows.Forms.Padding(3, 3, 3, 9);
            this.statusToPanel.Name = "statusToPanel";
            this.statusToPanel.Padding = new System.Windows.Forms.Padding(0, 0, 0, 6);
            this.statusToPanel.Size = new System.Drawing.Size(285, 56);
            this.statusToPanel.TabIndex = 4;
            this.statusToPanel.Visible = false;
            // 
            // statusToLabel
            // 
            this.statusToLabel.AutoSize = true;
            this.statusToLabel.Location = new System.Drawing.Point(3, 6);
            this.statusToLabel.Name = "statusToLabel";
            this.statusToLabel.Size = new System.Drawing.Size(41, 15);
            this.statusToLabel.TabIndex = 2;
            this.statusToLabel.Text = "label2";
            // 
            // statusToComboBox
            // 
            this.statusToComboBox.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.statusToComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.statusToComboBox.FormattingEnabled = true;
            this.statusToComboBox.Location = new System.Drawing.Point(0, 27);
            this.statusToComboBox.Name = "statusToComboBox";
            this.statusToComboBox.Size = new System.Drawing.Size(285, 23);
            this.statusToComboBox.TabIndex = 1;
            // 
            // statusPanel
            // 
            this.statusPanel.Controls.Add(this.fromStatusLabel);
            this.statusPanel.Controls.Add(this.statusComboBox);
            this.statusPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.statusPanel.Location = new System.Drawing.Point(0, 182);
            this.statusPanel.Margin = new System.Windows.Forms.Padding(3, 3, 3, 9);
            this.statusPanel.Name = "statusPanel";
            this.statusPanel.Padding = new System.Windows.Forms.Padding(0, 0, 0, 6);
            this.statusPanel.Size = new System.Drawing.Size(285, 56);
            this.statusPanel.TabIndex = 3;
            this.statusPanel.Visible = false;
            // 
            // fromStatusLabel
            // 
            this.fromStatusLabel.AutoSize = true;
            this.fromStatusLabel.Location = new System.Drawing.Point(3, 6);
            this.fromStatusLabel.Name = "fromStatusLabel";
            this.fromStatusLabel.Size = new System.Drawing.Size(36, 15);
            this.fromStatusLabel.TabIndex = 2;
            this.fromStatusLabel.Text = "From";
            // 
            // statusComboBox
            // 
            this.statusComboBox.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.statusComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.statusComboBox.FormattingEnabled = true;
            this.statusComboBox.Location = new System.Drawing.Point(0, 27);
            this.statusComboBox.Name = "statusComboBox";
            this.statusComboBox.Size = new System.Drawing.Size(285, 23);
            this.statusComboBox.TabIndex = 1;
            // 
            // reportFirstCheckedListUI
            // 
            this.reportFirstCheckedListUI.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.reportFirstCheckedListUI.Dock = System.Windows.Forms.DockStyle.Top;
            this.reportFirstCheckedListUI.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportFirstCheckedListUI.Location = new System.Drawing.Point(0, 52);
            this.reportFirstCheckedListUI.Name = "reportFirstCheckedListUI";
            this.reportFirstCheckedListUI.Size = new System.Drawing.Size(285, 130);
            this.reportFirstCheckedListUI.TabIndex = 2;
            this.reportFirstCheckedListUI.TitleKey = null;
            // 
            // mrnInfoPanel
            // 
            this.mrnInfoPanel.Controls.Add(this.mrnTextBox);
            this.mrnInfoPanel.Controls.Add(this.mrnLabel);
            this.mrnInfoPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.mrnInfoPanel.Location = new System.Drawing.Point(0, 0);
            this.mrnInfoPanel.Name = "mrnInfoPanel";
            this.mrnInfoPanel.Padding = new System.Windows.Forms.Padding(0, 0, 0, 10);
            this.mrnInfoPanel.Size = new System.Drawing.Size(285, 52);
            this.mrnInfoPanel.TabIndex = 1;
            this.mrnInfoPanel.Visible = false;
            // 
            // mrnTextBox
            // 
            this.mrnTextBox.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.mrnTextBox.Location = new System.Drawing.Point(0, 21);
            this.mrnTextBox.Name = "mrnTextBox";
            this.mrnTextBox.Size = new System.Drawing.Size(285, 21);
            this.mrnTextBox.TabIndex = 1;
            // 
            // mrnLabel
            // 
            this.mrnLabel.AutoSize = true;
            this.mrnLabel.Location = new System.Drawing.Point(3, 3);
            this.mrnLabel.Name = "mrnLabel";
            this.mrnLabel.Size = new System.Drawing.Size(0, 15);
            this.mrnLabel.TabIndex = 0;
            // 
            // topPanel
            // 
            if (!reportInputFieldisRequired)
                this.topPanel.Controls.Add(this.requiredImage);
            this.topPanel.Controls.Add(this.reportCriteriaLabel);
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.topPanel.Location = new System.Drawing.Point(0, 0);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(285, 25);
            this.topPanel.TabIndex = 0;
            // 
            // requiredImage
            // 
            this.requiredImage.Image = ((System.Drawing.Image)(resources.GetObject("requiredImage.Image")));
            this.requiredImage.Location = new System.Drawing.Point(3, 6);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(12, 12);
            this.requiredImage.TabIndex = 1;
            this.requiredImage.TabStop = false;
            // 
            // reportCriteriaLabel
            // 
            this.reportCriteriaLabel.AutoSize = true;
            this.reportCriteriaLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reportCriteriaLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(77)))), ((int)(((byte)(111)))), ((int)(((byte)(161)))));
            if (!reportInputFieldisRequired)
                this.reportCriteriaLabel.Location = new System.Drawing.Point(16, 6);
            else
                this.reportCriteriaLabel.Location = new System.Drawing.Point(3, 6);

            this.reportCriteriaLabel.Name = "reportCriteriaLabel";
            this.reportCriteriaLabel.Size = new System.Drawing.Size(0, 15);
            this.reportCriteriaLabel.TabIndex = 3;
            // 
            // ReportBaseCriteriaUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.searchCriteriaPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ReportBaseCriteriaUI";
            this.Size = new System.Drawing.Size(285, 855);
            this.searchCriteriaPanel.ResumeLayout(false);
            this.balanceDuePanel.ResumeLayout(false);
            this.balanceDuePanel.PerformLayout();
            this.rangeBarPanel.ResumeLayout(false);
            this.rangeBarPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredPictureBox)).EndInit();
            this.statusToPanel.ResumeLayout(false);
            this.statusToPanel.PerformLayout();
            this.statusPanel.ResumeLayout(false);
            this.statusPanel.PerformLayout();
            this.mrnInfoPanel.ResumeLayout(false);
            this.mrnInfoPanel.PerformLayout();
            this.topPanel.ResumeLayout(false);
            this.topPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label reportCriteriaLabel;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel searchCriteriaPanel;
        private ReportCheckedListUI reportFirstCheckedListUI;
        private System.Windows.Forms.Panel mrnInfoPanel;
        private System.Windows.Forms.TextBox mrnTextBox;
        private System.Windows.Forms.Label mrnLabel;
        private System.Windows.Forms.Panel statusPanel;
        private System.Windows.Forms.ComboBox statusComboBox;
        private System.Windows.Forms.Panel statusToPanel;
        private System.Windows.Forms.ComboBox statusToComboBox;
        private ReportCheckedListUI reportSecondCheckedListUI;
        private ReportInputFieldUI reportInputFieldUI;
        private ReportCheckedListUI reportThirdCheckedListUI;
        private System.Windows.Forms.Panel rangeBarPanel;
        private Base.View.Common.RangeBar rangeBar;
        private System.Windows.Forms.PictureBox requiredPictureBox;
        private System.Windows.Forms.Label agingLabel;
        private System.Windows.Forms.Panel balanceDuePanel;
        private System.Windows.Forms.TextBox balanceDueTextBox;
        private System.Windows.Forms.Label dollarLabel;
        private System.Windows.Forms.ComboBox balanceDueComboBox;
        private System.Windows.Forms.Label balanceDueLabel;
        private ReportDateRangeUI reportDateRangeUI;
        private System.Windows.Forms.Label statusToLabel;
        private System.Windows.Forms.Label fromStatusLabel;
    }
}
