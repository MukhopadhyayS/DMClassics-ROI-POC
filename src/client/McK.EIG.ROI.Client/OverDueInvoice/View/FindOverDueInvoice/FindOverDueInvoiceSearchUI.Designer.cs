namespace McK.EIG.ROI.Client.OverDueInvoice.View.FindOverDueInvoice
{
    partial class FindOverDueInvoiceSearchUI
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
            this.searchInvoiceButtonPanel = new System.Windows.Forms.Panel();
            this.pictureBox3 = new System.Windows.Forms.PictureBox();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.resetButton = new System.Windows.Forms.Button();
            this.findInvoiceButton = new System.Windows.Forms.Button();
            this.searchCriteriaPanel = new System.Windows.Forms.Panel();
            this.pictureBox4 = new System.Windows.Forms.PictureBox();
            this.overDueDaysRange = new McK.EIG.ROI.Client.Base.View.Common.RangeBar();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.clearAllReqButton = new System.Windows.Forms.Button();
            this.selectAllReqButton = new System.Windows.Forms.Button();
            this.clearAllFacilityButton = new System.Windows.Forms.Button();
            this.selectAllFacilityButton = new System.Windows.Forms.Button();
            this.requestorCheckedListBox = new System.Windows.Forms.CheckedListBox();
            this.requestorNameTextBox = new System.Windows.Forms.TextBox();
            this.requestorNameLabel = new System.Windows.Forms.Label();
            this.requestorTypeLabel = new System.Windows.Forms.Label();
            this.overDueLabel = new System.Windows.Forms.Label();
            this.facilityCheckedListBox = new System.Windows.Forms.CheckedListBox();
            this.billingLocationLabel = new System.Windows.Forms.Label();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.searchInvoiceButtonPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox3)).BeginInit();
            this.searchCriteriaPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox4)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // searchInvoiceButtonPanel
            // 
            this.searchInvoiceButtonPanel.BackColor = System.Drawing.Color.White;
            this.searchInvoiceButtonPanel.Controls.Add(this.pictureBox3);
            this.searchInvoiceButtonPanel.Controls.Add(this.requiredLabel);
            this.searchInvoiceButtonPanel.Controls.Add(this.resetButton);
            this.searchInvoiceButtonPanel.Controls.Add(this.findInvoiceButton);
            this.searchInvoiceButtonPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.searchInvoiceButtonPanel.Location = new System.Drawing.Point(0, 190);
            this.searchInvoiceButtonPanel.Name = "searchInvoiceButtonPanel";
            this.searchInvoiceButtonPanel.Size = new System.Drawing.Size(867, 50);
            this.searchInvoiceButtonPanel.TabIndex = 1;
            // 
            // pictureBox3
            // 
            this.pictureBox3.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox3.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pictureBox3.Location = new System.Drawing.Point(3, 25);
            this.pictureBox3.Name = "pictureBox3";
            this.pictureBox3.Size = new System.Drawing.Size(10, 12);
            this.pictureBox3.TabIndex = 23;
            this.pictureBox3.TabStop = false;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Location = new System.Drawing.Point(20, 21);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 2;
            // 
            // resetButton
            // 
            this.resetButton.Location = new System.Drawing.Point(460, 15);
            this.resetButton.Name = "resetButton";
            this.resetButton.Size = new System.Drawing.Size(121, 27);
            this.resetButton.TabIndex = 1;
            this.resetButton.UseVisualStyleBackColor = true;
            this.resetButton.Click += new System.EventHandler(this.resetButton_Click);
            // 
            // findInvoiceButton
            // 
            this.findInvoiceButton.Enabled = false;
            this.findInvoiceButton.Location = new System.Drawing.Point(311, 15);
            this.findInvoiceButton.Name = "findInvoiceButton";
            this.findInvoiceButton.Size = new System.Drawing.Size(121, 27);
            this.findInvoiceButton.TabIndex = 0;
            this.findInvoiceButton.UseVisualStyleBackColor = true;
            this.findInvoiceButton.Click += new System.EventHandler(this.findInvoiceButton_Click);
            // 
            // searchCriteriaPanel
            // 
            this.searchCriteriaPanel.AutoScroll = true;
            this.searchCriteriaPanel.BackColor = System.Drawing.Color.White;
            this.searchCriteriaPanel.Controls.Add(this.pictureBox4);
            this.searchCriteriaPanel.Controls.Add(this.overDueDaysRange);
            this.searchCriteriaPanel.Controls.Add(this.pictureBox2);
            this.searchCriteriaPanel.Controls.Add(this.pictureBox1);
            this.searchCriteriaPanel.Controls.Add(this.clearAllReqButton);
            this.searchCriteriaPanel.Controls.Add(this.selectAllReqButton);
            this.searchCriteriaPanel.Controls.Add(this.clearAllFacilityButton);
            this.searchCriteriaPanel.Controls.Add(this.selectAllFacilityButton);
            this.searchCriteriaPanel.Controls.Add(this.requestorCheckedListBox);
            this.searchCriteriaPanel.Controls.Add(this.requestorNameTextBox);
            this.searchCriteriaPanel.Controls.Add(this.requestorNameLabel);
            this.searchCriteriaPanel.Controls.Add(this.requestorTypeLabel);
            this.searchCriteriaPanel.Controls.Add(this.overDueLabel);
            this.searchCriteriaPanel.Controls.Add(this.facilityCheckedListBox);
            this.searchCriteriaPanel.Controls.Add(this.billingLocationLabel);
            this.searchCriteriaPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.searchCriteriaPanel.Location = new System.Drawing.Point(0, 0);
            this.searchCriteriaPanel.Name = "searchCriteriaPanel";
            this.searchCriteriaPanel.Size = new System.Drawing.Size(867, 190);
            this.searchCriteriaPanel.TabIndex = 2;
            // 
            // pictureBox4
            // 
            this.pictureBox4.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox4.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pictureBox4.Location = new System.Drawing.Point(295, 19);
            this.pictureBox4.Name = "pictureBox4";
            this.pictureBox4.Size = new System.Drawing.Size(10, 12);
            this.pictureBox4.TabIndex = 24;
            this.pictureBox4.TabStop = false;
            // 
            // overDueDaysRange
            // 
            this.overDueDaysRange.BackColor = System.Drawing.SystemColors.Window;
            this.overDueDaysRange.DivisionNumber = 5;
            this.overDueDaysRange.HeightOfBar = 8;
            this.overDueDaysRange.HeightOfMark = 15;
            this.overDueDaysRange.HeightOfTick = 0;
            this.overDueDaysRange.InnerColor = System.Drawing.Color.CornflowerBlue;
            this.overDueDaysRange.Left = 0;
            this.overDueDaysRange.Location = new System.Drawing.Point(600, 28);
            this.overDueDaysRange.Name = "overDueDaysRange";
            this.overDueDaysRange.Orientation = McK.EIG.ROI.Client.Base.View.Common.RangeBar.RangeBarOrientation.Horizontal;
            this.overDueDaysRange.RangeMaximum = 0;
            this.overDueDaysRange.RangeMinimum = 0;
            this.overDueDaysRange.Right = 0;
            this.overDueDaysRange.ScaleOrientation = McK.EIG.ROI.Client.Base.View.Common.RangeBar.TopBottomOrientation.Bottom;
            this.overDueDaysRange.Size = new System.Drawing.Size(212, 72);
            this.overDueDaysRange.TabIndex = 23;
            this.overDueDaysRange.TotalMaximum = 160;
            this.overDueDaysRange.TotalMinimum = 0;
            // 
            // pictureBox2
            // 
            this.pictureBox2.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pictureBox2.Location = new System.Drawing.Point(601, 15);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(10, 12);
            this.pictureBox2.TabIndex = 22;
            this.pictureBox2.TabStop = false;
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pictureBox1.Location = new System.Drawing.Point(3, 19);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(10, 12);
            this.pictureBox1.TabIndex = 21;
            this.pictureBox1.TabStop = false;
            // 
            // clearAllReqButton
            // 
            this.clearAllReqButton.Location = new System.Drawing.Point(503, 115);
            this.clearAllReqButton.Name = "clearAllReqButton";
            this.clearAllReqButton.Size = new System.Drawing.Size(87, 27);
            this.clearAllReqButton.TabIndex = 20;
            this.clearAllReqButton.UseVisualStyleBackColor = true;
            this.clearAllReqButton.Click += new System.EventHandler(this.ClearAllReqButton_Click);
            // 
            // selectAllReqButton
            // 
            this.selectAllReqButton.Location = new System.Drawing.Point(402, 116);
            this.selectAllReqButton.Name = "selectAllReqButton";
            this.selectAllReqButton.Size = new System.Drawing.Size(87, 27);
            this.selectAllReqButton.TabIndex = 19;
            this.selectAllReqButton.UseVisualStyleBackColor = true;
            this.selectAllReqButton.Click += new System.EventHandler(this.selectAllReqButton_Click);
            // 
            // clearAllFacilityButton
            // 
            this.clearAllFacilityButton.Location = new System.Drawing.Point(206, 116);
            this.clearAllFacilityButton.Name = "clearAllFacilityButton";
            this.clearAllFacilityButton.Size = new System.Drawing.Size(87, 27);
            this.clearAllFacilityButton.TabIndex = 18;
            this.clearAllFacilityButton.UseVisualStyleBackColor = true;
            this.clearAllFacilityButton.Click += new System.EventHandler(this.clearAllFacilityButton_Click);
            // 
            // selectAllFacilityButton
            // 
            this.selectAllFacilityButton.Location = new System.Drawing.Point(108, 115);
            this.selectAllFacilityButton.Name = "selectAllFacilityButton";
            this.selectAllFacilityButton.Size = new System.Drawing.Size(87, 27);
            this.selectAllFacilityButton.TabIndex = 17;
            this.selectAllFacilityButton.UseVisualStyleBackColor = true;
            this.selectAllFacilityButton.Click += new System.EventHandler(this.selectAllFacilityButton_Click);
            // 
            // requestorCheckedListBox
            // 
            this.requestorCheckedListBox.CheckOnClick = true;
            this.requestorCheckedListBox.FormattingEnabled = true;
            this.requestorCheckedListBox.Location = new System.Drawing.Point(402, 17);
            this.requestorCheckedListBox.Name = "requestorCheckedListBox";
            this.requestorCheckedListBox.Size = new System.Drawing.Size(185, 84);
            this.requestorCheckedListBox.TabIndex = 16;
            // 
            // requestorNameTextBox
            // 
            this.requestorNameTextBox.Location = new System.Drawing.Point(405, 154);
            this.requestorNameTextBox.Name = "requestorNameTextBox";
            this.requestorNameTextBox.Size = new System.Drawing.Size(185, 21);
            this.requestorNameTextBox.TabIndex = 15;
            // 
            // requestorNameLabel
            // 
            this.requestorNameLabel.AutoSize = true;
            this.requestorNameLabel.Location = new System.Drawing.Point(292, 159);
            this.requestorNameLabel.Name = "requestorNameLabel";
            this.requestorNameLabel.Size = new System.Drawing.Size(0, 15);
            this.requestorNameLabel.TabIndex = 14;
            // 
            // requestorTypeLabel
            // 
            this.requestorTypeLabel.AutoSize = true;
            this.requestorTypeLabel.Location = new System.Drawing.Point(308, 19);
            this.requestorTypeLabel.Name = "requestorTypeLabel";
            this.requestorTypeLabel.Size = new System.Drawing.Size(0, 15);
            this.requestorTypeLabel.TabIndex = 13;
            // 
            // overDueLabel
            // 
            this.overDueLabel.AutoSize = true;
            this.overDueLabel.Location = new System.Drawing.Point(618, 12);
            this.overDueLabel.Name = "overDueLabel";
            this.overDueLabel.Size = new System.Drawing.Size(0, 15);
            this.overDueLabel.TabIndex = 5;
            // 
            // facilityCheckedListBox
            // 
            this.facilityCheckedListBox.CheckOnClick = true;
            this.facilityCheckedListBox.FormattingEnabled = true;
            this.facilityCheckedListBox.Location = new System.Drawing.Point(108, 17);
            this.facilityCheckedListBox.Name = "facilityCheckedListBox";
            this.facilityCheckedListBox.Size = new System.Drawing.Size(185, 84);
            this.facilityCheckedListBox.TabIndex = 3;
            // 
            // billingLocationLabel
            // 
            this.billingLocationLabel.AutoSize = true;
            this.billingLocationLabel.Location = new System.Drawing.Point(17, 19);
            this.billingLocationLabel.Name = "billingLocationLabel";
            this.billingLocationLabel.Size = new System.Drawing.Size(0, 15);
            this.billingLocationLabel.TabIndex = 2;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // toolTip
            // 
            this.toolTip.ShowAlways = true;
            // 
            // FindOverDueInvoiceSearchUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.searchCriteriaPanel);
            this.Controls.Add(this.searchInvoiceButtonPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "FindOverDueInvoiceSearchUI";
            this.Size = new System.Drawing.Size(867, 240);
            this.searchInvoiceButtonPanel.ResumeLayout(false);
            this.searchInvoiceButtonPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox3)).EndInit();
            this.searchCriteriaPanel.ResumeLayout(false);
            this.searchCriteriaPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox4)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel searchInvoiceButtonPanel;
        private System.Windows.Forms.Button resetButton;
        private System.Windows.Forms.Button findInvoiceButton;
        private System.Windows.Forms.Panel searchCriteriaPanel;
        private System.Windows.Forms.CheckedListBox facilityCheckedListBox;
        private System.Windows.Forms.Label billingLocationLabel;
        private System.Windows.Forms.Label overDueLabel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.CheckedListBox requestorCheckedListBox;
        private System.Windows.Forms.TextBox requestorNameTextBox;
        private System.Windows.Forms.Label requestorNameLabel;
        private System.Windows.Forms.Label requestorTypeLabel;
        private System.Windows.Forms.Button clearAllReqButton;
        private System.Windows.Forms.Button selectAllReqButton;
        private System.Windows.Forms.Button clearAllFacilityButton;
        private System.Windows.Forms.Button selectAllFacilityButton;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.PictureBox pictureBox3;
        private System.Windows.Forms.Label requiredLabel;
        private McK.EIG.ROI.Client.Base.View.Common.RangeBar overDueDaysRange;
        private System.Windows.Forms.PictureBox pictureBox4;
    }
}
