namespace McK.EIG.ROI.Client.Reports.View
{
    partial class ReportCheckedListUI
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
            int nPosAdjust = 0;
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ReportCheckedListUI));
            this.components = new System.ComponentModel.Container();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.checkedListBoxTitleLabel = new System.Windows.Forms.Label();
            this.criteriaCheckedListBox = new System.Windows.Forms.CheckedListBox();
            this.topPanel = new System.Windows.Forms.Panel();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.selectAllButton = new System.Windows.Forms.Button();
            this.clearAllButton = new System.Windows.Forms.Button();
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.topPanel.SuspendLayout();
            this.bottomPanel.SuspendLayout();
            this.tableLayoutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // checkedListBoxTitleLabel
            // 
            if (isRequired)
            {
                this.requiredImage.Image = ((System.Drawing.Image)(resources.GetObject("requiredImage.Image")));
                this.requiredImage.Location = new System.Drawing.Point(2, 2);
                this.requiredImage.Name = "requiredImage";
                this.requiredImage.Size = new System.Drawing.Size(12, 12);
                this.requiredImage.TabIndex = 0;
                this.requiredImage.TabStop = false;
            }

            this.checkedListBoxTitleLabel.AutoSize = true;
            this.checkedListBoxTitleLabel.Dock = System.Windows.Forms.DockStyle.Bottom;
            if (isRequired)
                nPosAdjust = 18;
            this.checkedListBoxTitleLabel.Location = new System.Drawing.Point(nPosAdjust+2, 0);
            this.checkedListBoxTitleLabel.Name = "checkedListBoxTitleLabel";
            this.checkedListBoxTitleLabel.Size = new System.Drawing.Size(202, 15);
            this.checkedListBoxTitleLabel.TabIndex = 0;

            // 
            // topPanel
            // 
            if (isRequired)
                this.topPanel.Controls.Add(this.requiredImage);
            this.topPanel.Controls.Add(this.checkedListBoxTitleLabel);
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Left;
            this.topPanel.Location = new System.Drawing.Point(6, 5);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(199, 14);
            this.topPanel.TabIndex = 4;

            // 
            // criteriaCheckedListBox
            // 
            this.criteriaCheckedListBox.CheckOnClick = true;
            this.criteriaCheckedListBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.criteriaCheckedListBox.FormattingEnabled = true;
            this.criteriaCheckedListBox.HorizontalScrollbar = true;
            this.criteriaCheckedListBox.Location = new System.Drawing.Point(3, 18);
            this.criteriaCheckedListBox.Name = "criteriaCheckedListBox";
            this.criteriaCheckedListBox.Size = new System.Drawing.Size(202, 84);
            this.criteriaCheckedListBox.TabIndex = 0;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.selectAllButton);
            this.bottomPanel.Controls.Add(this.clearAllButton);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Right;
            this.bottomPanel.Location = new System.Drawing.Point(6, 114);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(199, 25);
            this.bottomPanel.TabIndex = 4;
            // 
            // selectAllButton
            // 
            this.selectAllButton.Location = new System.Drawing.Point(19, 1);
            this.selectAllButton.Name = "selectAllButton";
            this.selectAllButton.Size = new System.Drawing.Size(75, 24);
            this.selectAllButton.TabIndex = 1;
            this.selectAllButton.UseVisualStyleBackColor = true;
            this.selectAllButton.Click += new System.EventHandler(this.selectAllButton_Click);
            // 
            // clearAllButton
            // 
            this.clearAllButton.Location = new System.Drawing.Point(97, 1);
            this.clearAllButton.Name = "clearAllButton";
            this.clearAllButton.Size = new System.Drawing.Size(91, 24);
            this.clearAllButton.TabIndex = 2;
            this.clearAllButton.UseVisualStyleBackColor = true;
            this.clearAllButton.Click += new System.EventHandler(this.clearAllButton_Click);
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.ColumnCount = 1;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.Controls.Add(this.criteriaCheckedListBox, 0, 1);
            this.tableLayoutPanel.Controls.Add(this.bottomPanel, 0, 2);
            this.tableLayoutPanel.Controls.Add(this.topPanel, 0, 0);
            //this.tableLayoutPanel.Controls.Add(this.checkedListBoxTitleLabel, 0, 0);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 3;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 31F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(208, 142);
            this.tableLayoutPanel.TabIndex = 6;
            // 
            // ReportCheckedListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(213)))), ((int)(((byte)(239)))));
            this.Controls.Add(this.tableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ReportCheckedListUI";
            this.Size = new System.Drawing.Size(208, 142);
            this.bottomPanel.ResumeLayout(false);
            this.tableLayoutPanel.ResumeLayout(false);
            this.tableLayoutPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Label checkedListBoxTitleLabel;
        private System.Windows.Forms.CheckedListBox criteriaCheckedListBox;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.Button selectAllButton;
        private System.Windows.Forms.Button clearAllButton;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
