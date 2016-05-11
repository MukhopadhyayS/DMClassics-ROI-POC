namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    partial class StatementDialog
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
            this.requestorTypeLabel = new System.Windows.Forms.Label();
            this.requestorLabel = new System.Windows.Forms.Label();
            this.addLetterGroupBox = new System.Windows.Forms.GroupBox();
            this.progressPanel = new System.Windows.Forms.Panel();
            this.pastInvoiceGroupBox = new System.Windows.Forms.GroupBox();
            this.pastInvoiceGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.pastInvoiceCheckBox = new System.Windows.Forms.CheckBox();
            this.statementGroupBox = new System.Windows.Forms.GroupBox();
            this.statementMessageLabel = new System.Windows.Forms.Label();
            this.statementPanel = new System.Windows.Forms.Panel();
            this.dateRangecomboBox = new System.Windows.Forms.ComboBox();
            this.dateRangeLabel = new System.Windows.Forms.Label();
            this.statementNotesGroupPanel = new System.Windows.Forms.Panel();
            this.createFreeformButton = new System.Windows.Forms.Button();
            this.coverLetterNotesPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.statementHeaderLabel = new System.Windows.Forms.Label();
            this.statementComboBox = new System.Windows.Forms.ComboBox();
            this.statementCheckBox = new System.Windows.Forms.CheckBox();
            this.img2 = new System.Windows.Forms.PictureBox();
            this.img1 = new System.Windows.Forms.PictureBox();
            this.statementLabel = new System.Windows.Forms.Label();
            this.headerLabel = new System.Windows.Forms.Label();
            this.topPanel = new System.Windows.Forms.Panel();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.cancelButton = new System.Windows.Forms.Button();
            this.continueButton = new System.Windows.Forms.Button();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.addLetterGroupBox.SuspendLayout();
            this.pastInvoiceGroupBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pastInvoiceGrid)).BeginInit();
            this.statementGroupBox.SuspendLayout();
            this.statementPanel.SuspendLayout();
            this.statementNotesGroupPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).BeginInit();
            this.bottomPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // requestorTypeLabel
            // 
            this.requestorTypeLabel.AutoSize = true;
            this.requestorTypeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestorTypeLabel.Location = new System.Drawing.Point(78, 28);
            this.requestorTypeLabel.Name = "requestorTypeLabel";
            this.requestorTypeLabel.Size = new System.Drawing.Size(55, 15);
            this.requestorTypeLabel.TabIndex = 14;
            this.requestorTypeLabel.Text = "Attroney";
            // 
            // requestorLabel
            // 
            this.requestorLabel.AutoSize = true;
            this.requestorLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestorLabel.Location = new System.Drawing.Point(8, 28);
            this.requestorLabel.Name = "requestorLabel";
            this.requestorLabel.Size = new System.Drawing.Size(69, 15);
            this.requestorLabel.TabIndex = 13;
            this.requestorLabel.Text = "Requestor:";
            // 
            // addLetterGroupBox
            // 
            this.addLetterGroupBox.Controls.Add(this.progressPanel);
            this.addLetterGroupBox.Controls.Add(this.pastInvoiceGroupBox);
            this.addLetterGroupBox.Controls.Add(this.statementGroupBox);
            this.addLetterGroupBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.addLetterGroupBox.Location = new System.Drawing.Point(3, 105);
            this.addLetterGroupBox.Name = "addLetterGroupBox";
            this.addLetterGroupBox.Size = new System.Drawing.Size(1001, 468);
            this.addLetterGroupBox.TabIndex = 1;
            this.addLetterGroupBox.TabStop = false;
            // 
            // progressPanel
            // 
            this.progressPanel.Location = new System.Drawing.Point(282, 164);
            this.progressPanel.Name = "progressPanel";
            this.progressPanel.Size = new System.Drawing.Size(455, 59);
            this.progressPanel.TabIndex = 2;
            // 
            // pastInvoiceGroupBox
            // 
            this.pastInvoiceGroupBox.Controls.Add(this.pastInvoiceGrid);
            this.pastInvoiceGroupBox.Controls.Add(this.pastInvoiceCheckBox);
            this.pastInvoiceGroupBox.Location = new System.Drawing.Point(508, 22);
            this.pastInvoiceGroupBox.Name = "pastInvoiceGroupBox";
            this.pastInvoiceGroupBox.Size = new System.Drawing.Size(487, 442);
            this.pastInvoiceGroupBox.TabIndex = 8;
            this.pastInvoiceGroupBox.TabStop = false;
            // 
            // pastInvoiceGrid
            // 
            this.pastInvoiceGrid.AllowUserToAddRows = false;
            this.pastInvoiceGrid.AllowUserToDeleteRows = false;
            this.pastInvoiceGrid.AllowUserToResizeRows = false;
            this.pastInvoiceGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.pastInvoiceGrid.BackgroundColor = System.Drawing.Color.White;
            this.pastInvoiceGrid.ChangeValidator = null;
            this.pastInvoiceGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.pastInvoiceGrid.ConfirmSelection = false;
            this.pastInvoiceGrid.Location = new System.Drawing.Point(3, 36);
            this.pastInvoiceGrid.MultiSelect = false;
            this.pastInvoiceGrid.Name = "pastInvoiceGrid";
            this.pastInvoiceGrid.ReadOnly = true;
            this.pastInvoiceGrid.RowHeadersVisible = false;
            this.pastInvoiceGrid.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.pastInvoiceGrid.SelectionHandler = null;
            this.pastInvoiceGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.pastInvoiceGrid.ShowCellToolTips = false;
            this.pastInvoiceGrid.Size = new System.Drawing.Size(478, 399);
            this.pastInvoiceGrid.SortEnabled = false;
            this.pastInvoiceGrid.TabIndex = 6;
            // 
            // pastInvoiceCheckBox
            // 
            this.pastInvoiceCheckBox.AutoSize = true;
            this.pastInvoiceCheckBox.Location = new System.Drawing.Point(4, 16);
            this.pastInvoiceCheckBox.Name = "pastInvoiceCheckBox";
            this.pastInvoiceCheckBox.Size = new System.Drawing.Size(85, 19);
            this.pastInvoiceCheckBox.TabIndex = 0;
            this.pastInvoiceCheckBox.Text = "checkBox1";
            this.pastInvoiceCheckBox.UseVisualStyleBackColor = true;
            // 
            // statementGroupBox
            // 
            this.statementGroupBox.Controls.Add(this.statementMessageLabel);
            this.statementGroupBox.Controls.Add(this.statementPanel);
            this.statementGroupBox.Location = new System.Drawing.Point(21, 22);
            this.statementGroupBox.Name = "statementGroupBox";
            this.statementGroupBox.Size = new System.Drawing.Size(470, 442);
            this.statementGroupBox.TabIndex = 2;
            this.statementGroupBox.TabStop = false;
            // 
            // statementMessageLabel
            // 
            this.statementMessageLabel.Location = new System.Drawing.Point(11, 39);
            this.statementMessageLabel.Name = "statementMessageLabel";
            this.statementMessageLabel.Size = new System.Drawing.Size(447, 115);
            this.statementMessageLabel.TabIndex = 56;
            this.statementMessageLabel.Visible = false;
            // 
            // statementPanel
            // 
            this.statementPanel.Controls.Add(this.dateRangecomboBox);
            this.statementPanel.Controls.Add(this.dateRangeLabel);
            this.statementPanel.Controls.Add(this.statementNotesGroupPanel);
            this.statementPanel.Controls.Add(this.statementComboBox);
            this.statementPanel.Controls.Add(this.statementCheckBox);
            this.statementPanel.Controls.Add(this.img2);
            this.statementPanel.Controls.Add(this.img1);
            this.statementPanel.Controls.Add(this.statementLabel);
            this.statementPanel.Location = new System.Drawing.Point(4, 20);
            this.statementPanel.Name = "statementPanel";
            this.statementPanel.Size = new System.Drawing.Size(462, 415);
            this.statementPanel.TabIndex = 51;
            // 
            // dateRangecomboBox
            // 
            this.dateRangecomboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.dateRangecomboBox.FormattingEnabled = true;
            this.dateRangecomboBox.Location = new System.Drawing.Point(161, 74);
            this.dateRangecomboBox.Name = "dateRangecomboBox";
            this.dateRangecomboBox.Size = new System.Drawing.Size(239, 23);
            this.dateRangecomboBox.TabIndex = 55;
            this.dateRangecomboBox.SelectedIndexChanged += new System.EventHandler(this.dateRangecomboBox_SelectedIndexChanged);
            // 
            // dateRangeLabel
            // 
            this.dateRangeLabel.AutoSize = true;
            this.dateRangeLabel.Location = new System.Drawing.Point(27, 74);
            this.dateRangeLabel.Name = "dateRangeLabel";
            this.dateRangeLabel.Size = new System.Drawing.Size(0, 15);
            this.dateRangeLabel.TabIndex = 54;
            // 
            // statementNotesGroupPanel
            // 
            this.statementNotesGroupPanel.Controls.Add(this.createFreeformButton);
            this.statementNotesGroupPanel.Controls.Add(this.coverLetterNotesPanel);
            this.statementNotesGroupPanel.Controls.Add(this.statementHeaderLabel);
            this.statementNotesGroupPanel.Location = new System.Drawing.Point(11, 122);
            this.statementNotesGroupPanel.Name = "statementNotesGroupPanel";
            this.statementNotesGroupPanel.Size = new System.Drawing.Size(447, 274);
            this.statementNotesGroupPanel.TabIndex = 20;
            // 
            // createFreeformButton
            // 
            this.createFreeformButton.Location = new System.Drawing.Point(3, 246);
            this.createFreeformButton.Name = "createFreeformButton";
            this.createFreeformButton.Size = new System.Drawing.Size(147, 25);
            this.createFreeformButton.TabIndex = 7;
            this.createFreeformButton.UseVisualStyleBackColor = true;
            this.createFreeformButton.Click += new System.EventHandler(this.createFreeformButton_Click);
            // 
            // coverLetterNotesPanel
            // 
            this.coverLetterNotesPanel.AutoScroll = true;
            this.coverLetterNotesPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.coverLetterNotesPanel.Location = new System.Drawing.Point(3, 27);
            this.coverLetterNotesPanel.Name = "coverLetterNotesPanel";
            this.coverLetterNotesPanel.Size = new System.Drawing.Size(440, 213);
            this.coverLetterNotesPanel.TabIndex = 5;
            // 
            // statementHeaderLabel
            // 
            this.statementHeaderLabel.AutoSize = true;
            this.statementHeaderLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.statementHeaderLabel.Location = new System.Drawing.Point(3, 8);
            this.statementHeaderLabel.Name = "statementHeaderLabel";
            this.statementHeaderLabel.Size = new System.Drawing.Size(0, 15);
            this.statementHeaderLabel.TabIndex = 3;
            // 
            // statementComboBox
            // 
            this.statementComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.statementComboBox.FormattingEnabled = true;
            this.statementComboBox.Location = new System.Drawing.Point(161, 37);
            this.statementComboBox.Name = "statementComboBox";
            this.statementComboBox.Size = new System.Drawing.Size(239, 23);
            this.statementComboBox.TabIndex = 4;
            this.statementComboBox.SelectedIndexChanged += new System.EventHandler(this.statementComboBox_SelectedIndexChanged);
            // 
            // statementCheckBox
            // 
            this.statementCheckBox.AutoSize = true;
            this.statementCheckBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.statementCheckBox.Location = new System.Drawing.Point(11, 6);
            this.statementCheckBox.Name = "statementCheckBox";
            this.statementCheckBox.Size = new System.Drawing.Size(15, 14);
            this.statementCheckBox.TabIndex = 3;
            this.statementCheckBox.UseVisualStyleBackColor = true;
            this.statementCheckBox.CheckedChanged += new System.EventHandler(this.statementCheckBox_CheckedChanged);
            // 
            // img2
            // 
            this.img2.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img2.Location = new System.Drawing.Point(12, 74);
            this.img2.Name = "img2";
            this.img2.Size = new System.Drawing.Size(9, 11);
            this.img2.TabIndex = 53;
            this.img2.TabStop = false;
            // 
            // img1
            // 
            this.img1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img1.Location = new System.Drawing.Point(12, 45);
            this.img1.Name = "img1";
            this.img1.Size = new System.Drawing.Size(9, 11);
            this.img1.TabIndex = 50;
            this.img1.TabStop = false;
            // 
            // statementLabel
            // 
            this.statementLabel.AutoSize = true;
            this.statementLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.statementLabel.Location = new System.Drawing.Point(27, 42);
            this.statementLabel.Name = "statementLabel";
            this.statementLabel.Size = new System.Drawing.Size(73, 15);
            this.statementLabel.TabIndex = 1;
            this.statementLabel.Text = "Cover Letter";
            // 
            // headerLabel
            // 
            this.headerLabel.BackColor = System.Drawing.Color.White;
            this.headerLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.headerLabel.Location = new System.Drawing.Point(5, 48);
            this.headerLabel.Name = "headerLabel";
            this.headerLabel.Size = new System.Drawing.Size(999, 47);
            this.headerLabel.TabIndex = 2;
            // 
            // topPanel
            // 
            this.topPanel.BackColor = System.Drawing.Color.White;
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.topPanel.Location = new System.Drawing.Point(0, 0);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(1004, 20);
            this.topPanel.TabIndex = 0;
            // 
            // bottomPanel
            // 
            this.bottomPanel.BackColor = System.Drawing.Color.White;
            this.bottomPanel.Controls.Add(this.requiredLabel);
            this.bottomPanel.Controls.Add(this.pictureBox2);
            this.bottomPanel.Controls.Add(this.cancelButton);
            this.bottomPanel.Controls.Add(this.continueButton);
            this.bottomPanel.Location = new System.Drawing.Point(0, 602);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(1004, 36);
            this.bottomPanel.TabIndex = 12;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(23, 9);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 52;
            // 
            // pictureBox2
            // 
            this.pictureBox2.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pictureBox2.Location = new System.Drawing.Point(7, 11);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(9, 11);
            this.pictureBox2.TabIndex = 51;
            this.pictureBox2.TabStop = false;
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(500, 4);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 25);
            this.cancelButton.TabIndex = 14;
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // continueButton
            // 
            this.continueButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.continueButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.continueButton.Location = new System.Drawing.Point(419, 4);
            this.continueButton.Name = "continueButton";
            this.continueButton.Size = new System.Drawing.Size(75, 25);
            this.continueButton.TabIndex = 13;
            this.continueButton.UseVisualStyleBackColor = true;
            this.continueButton.Click += new System.EventHandler(this.continueButton_Click);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // StatementDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 14F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.requestorTypeLabel);
            this.Controls.Add(this.requestorLabel);
            this.Controls.Add(this.addLetterGroupBox);
            this.Controls.Add(this.headerLabel);
            this.Controls.Add(this.topPanel);
            this.Controls.Add(this.bottomPanel);
            this.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "StatementDialog";
            this.Size = new System.Drawing.Size(1004, 640);
            this.addLetterGroupBox.ResumeLayout(false);
            this.pastInvoiceGroupBox.ResumeLayout(false);
            this.pastInvoiceGroupBox.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pastInvoiceGrid)).EndInit();
            this.statementGroupBox.ResumeLayout(false);
            this.statementPanel.ResumeLayout(false);
            this.statementPanel.PerformLayout();
            this.statementNotesGroupPanel.ResumeLayout(false);
            this.statementNotesGroupPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).EndInit();
            this.bottomPanel.ResumeLayout(false);
            this.bottomPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Label headerLabel;
        private System.Windows.Forms.GroupBox addLetterGroupBox;
        private System.Windows.Forms.GroupBox statementGroupBox;
        private System.Windows.Forms.ComboBox statementComboBox;
        private System.Windows.Forms.Label statementLabel;
        private System.Windows.Forms.CheckBox statementCheckBox;
        private System.Windows.Forms.Label statementHeaderLabel;
        private System.Windows.Forms.PictureBox img1;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button continueButton;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel statementNotesGroupPanel;
        private System.Windows.Forms.Panel statementPanel;
        private System.Windows.Forms.FlowLayoutPanel coverLetterNotesPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.GroupBox pastInvoiceGroupBox;
        private System.Windows.Forms.CheckBox pastInvoiceCheckBox;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid pastInvoiceGrid;
        private System.Windows.Forms.Panel progressPanel;
        private System.Windows.Forms.Label requestorTypeLabel;
        private System.Windows.Forms.Label requestorLabel;
        private System.Windows.Forms.Label statementMessageLabel;
        private System.Windows.Forms.ComboBox dateRangecomboBox;
        private System.Windows.Forms.Label dateRangeLabel;
        private System.Windows.Forms.PictureBox img2;
        private System.Windows.Forms.Button createFreeformButton;
        
    }
}
