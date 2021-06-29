using McK.EIG.ROI.Client.Base.View.Common.Tree;
using McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls;

namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    partial class ReleaseTreeUI
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
            this.Column0 = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeColumn();
            this.Column1 = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeColumn();
            this.Column2 = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeColumn();
            this.Column3 = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeColumn();
            this.Column4 = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeColumn();
            this.Column5 = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeColumn();
            this.isChecked = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeCheckBox();
            this.icon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.name = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.facility = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.department = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.dateOfService = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.encounter = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.vipIcon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.lockedIcon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.deficiencyIcon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.documentsGroupBox = new System.Windows.Forms.GroupBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.topPanel = new System.Windows.Forms.Panel();
            this.collapseButton = new System.Windows.Forms.Button();
            this.expandButton = new System.Windows.Forms.Button();
            this.unReleasedRadioButton = new System.Windows.Forms.RadioButton();
            this.releasedRadioButton = new System.Windows.Forms.RadioButton();
            this.allRadioButton = new System.Windows.Forms.RadioButton();
            this.filterByLabel = new System.Windows.Forms.Label();
            this.treePanel = new System.Windows.Forms.Panel();
            this.tree = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeViewAdv();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.removeAllButton = new System.Windows.Forms.Button();
            this.modifyBillingTierButton = new System.Windows.Forms.Button();
            this.removeButton = new System.Windows.Forms.Button();
            this.resendButton = new System.Windows.Forms.Button();
            this.dsrTreeInfoLabel = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.documentsGroupBox.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            this.topPanel.SuspendLayout();
            this.treePanel.SuspendLayout();
            this.bottomPanel.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // Column0
            // 
            this.Column0.Header = "";
            this.Column0.Sorted = true;
            this.Column0.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column0.TooltipText = "";
            this.Column0.Width = 200;
            // 
            // Column1
            // 
            this.Column1.Header = "";
            this.Column1.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column1.TooltipText = "";
            // 
            // Column2
            // 
            this.Column2.Header = "";
            this.Column2.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column2.TooltipText = null;
            this.Column2.Width = 35;
            // 
            // Column3
            // 
            this.Column3.Header = "";
            this.Column3.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column3.TooltipText = null;
            this.Column3.Width = 75;
            // 
            // Column4
            // 
            this.Column4.Header = "";
            this.Column4.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column4.TooltipText = null;
            this.Column4.Width = 20;
            // 
            // Column5
            // 
            this.Column5.Header = "";
            this.Column5.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column5.TooltipText = null;
            this.Column5.Width = 30;
            // 
            // isChecked
            // 
            this.isChecked.DataPropertyName = "IsReleased";
            this.isChecked.LeftMargin = 0;
            this.isChecked.ParentColumn = this.Column0;
            this.isChecked.ThreeState = true;
            this.isChecked.VirtualMode = true;
            // 
            // icon
            // 
            this.icon.DataPropertyName = "Icon";
            this.icon.LeftMargin = 1;
            this.icon.ParentColumn = this.Column0;
            // 
            // name
            // 
            this.name.DataPropertyName = "Name";
            this.name.EditEnabled = false;
            this.name.IncrementalSearchEnabled = true;
            this.name.LeftMargin = 3;
            this.name.ParentColumn = this.Column0;
            this.name.Trimming = System.Drawing.StringTrimming.EllipsisWord;
            // 
            // facility
            // 
            this.facility.DataPropertyName = "Facility";
            this.facility.EditEnabled = false;
            this.facility.EditOnClick = true;
            this.facility.IncrementalSearchEnabled = true;
            this.facility.LeftMargin = 3;
            this.facility.ParentColumn = this.Column1;
            this.facility.Trimming = System.Drawing.StringTrimming.EllipsisWord;
            this.facility.VirtualMode = true;
            // 
            // department
            // 
            this.department.DataPropertyName = "Department";
            this.department.EditEnabled = false;
            this.department.IncrementalSearchEnabled = true;
            this.department.LeftMargin = 3;
            this.department.ParentColumn = this.Column2;
            this.department.Trimming = System.Drawing.StringTrimming.EllipsisWord;
            this.department.VirtualMode = true;
            // 
            // dateOfService
            // 
            this.dateOfService.DataPropertyName = "DateOfService";
            this.dateOfService.EditEnabled = false;
            this.dateOfService.IncrementalSearchEnabled = true;
            this.dateOfService.LeftMargin = 3;
            this.dateOfService.ParentColumn = this.Column3;
            this.dateOfService.Trimming = System.Drawing.StringTrimming.EllipsisWord;
            this.dateOfService.VirtualMode = true;
            // 
            // encounter
            // 
            this.encounter.DataPropertyName = "NonHpfEncounterId";
            this.encounter.EditEnabled = false;
            this.encounter.IncrementalSearchEnabled = true;
            this.encounter.LeftMargin = 3;
            this.encounter.ParentColumn = this.Column4;
            this.encounter.VirtualMode = true;
            // 
            // vipIcon
            // 
            this.vipIcon.DataPropertyName = "IsVip";
            this.vipIcon.LeftMargin = 1;
            this.vipIcon.ParentColumn = this.Column5;
            this.vipIcon.VirtualMode = true;
            // 
            // lockedIcon
            // 
            this.lockedIcon.DataPropertyName = "IsLocked";
            this.lockedIcon.LeftMargin = 1;
            this.lockedIcon.ParentColumn = this.Column5;
            this.lockedIcon.VirtualMode = true;
            // 
            // deficiencyIcon
            // 
            this.deficiencyIcon.DataPropertyName = "IsDeficiency";
            this.deficiencyIcon.LeftMargin = 1;
            this.deficiencyIcon.ParentColumn = this.Column5;
            this.deficiencyIcon.VirtualMode = true;
            // 
            // documentsGroupBox
            // 
            this.documentsGroupBox.Controls.Add(this.tableLayoutPanel1);
            this.documentsGroupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.documentsGroupBox.Location = new System.Drawing.Point(0, 0);
            this.documentsGroupBox.Name = "documentsGroupBox";
            this.documentsGroupBox.Size = new System.Drawing.Size(359, 475);
            this.documentsGroupBox.TabIndex = 1;
            this.documentsGroupBox.TabStop = false;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.topPanel, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.treePanel, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.bottomPanel, 0, 2);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(3, 17);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 3;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 9.602595F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 70.23588F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20.16153F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(353, 455);
            this.tableLayoutPanel1.TabIndex = 7;
            // 
            // topPanel
            // 
            this.topPanel.Controls.Add(this.collapseButton);
            this.topPanel.Controls.Add(this.expandButton);
            this.topPanel.Controls.Add(this.unReleasedRadioButton);
            this.topPanel.Controls.Add(this.releasedRadioButton);
            this.topPanel.Controls.Add(this.allRadioButton);
            this.topPanel.Controls.Add(this.filterByLabel);
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.topPanel.Location = new System.Drawing.Point(0, 0);
            this.topPanel.Margin = new System.Windows.Forms.Padding(0);
            this.topPanel.Name = "topPanel";
            this.topPanel.Padding = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.topPanel.Size = new System.Drawing.Size(353, 43);
            this.topPanel.TabIndex = 3;
            // 
            // collapseButton
            // 
            this.collapseButton.Location = new System.Drawing.Point(89, 20);
            this.collapseButton.Name = "collapseButton";
            this.collapseButton.Size = new System.Drawing.Size(75, 23);
            this.collapseButton.TabIndex = 6;
            this.collapseButton.UseVisualStyleBackColor = true;
            this.collapseButton.Click += new System.EventHandler(this.collapseButton_Click);
            // 
            // expandButton
            // 
            this.expandButton.Location = new System.Drawing.Point(8, 20);
            this.expandButton.Name = "expandButton";
            this.expandButton.Size = new System.Drawing.Size(75, 23);
            this.expandButton.TabIndex = 5;
            this.expandButton.UseVisualStyleBackColor = true;
            this.expandButton.Click += new System.EventHandler(this.expandButton_Click);
            // 
            // unReleasedRadioButton
            // 
            this.unReleasedRadioButton.AutoSize = true;
            this.unReleasedRadioButton.Location = new System.Drawing.Point(179, 2);
            this.unReleasedRadioButton.Name = "unReleasedRadioButton";
            this.unReleasedRadioButton.Size = new System.Drawing.Size(14, 13);
            this.unReleasedRadioButton.TabIndex = 2;
            this.unReleasedRadioButton.UseVisualStyleBackColor = true;
            this.unReleasedRadioButton.CheckedChanged += new System.EventHandler(this.FilterBy_CheckedChanged);
            // 
            // releasedRadioButton
            // 
            this.releasedRadioButton.AutoSize = true;
            this.releasedRadioButton.Location = new System.Drawing.Point(100, 2);
            this.releasedRadioButton.Name = "releasedRadioButton";
            this.releasedRadioButton.Size = new System.Drawing.Size(14, 13);
            this.releasedRadioButton.TabIndex = 1;
            this.releasedRadioButton.UseVisualStyleBackColor = true;
            this.releasedRadioButton.CheckedChanged += new System.EventHandler(this.FilterBy_CheckedChanged);
            // 
            // allRadioButton
            // 
            this.allRadioButton.AutoSize = true;
            this.allRadioButton.Checked = true;
            this.allRadioButton.Location = new System.Drawing.Point(59, 2);
            this.allRadioButton.Name = "allRadioButton";
            this.allRadioButton.Size = new System.Drawing.Size(14, 13);
            this.allRadioButton.TabIndex = 0;
            this.allRadioButton.TabStop = true;
            this.allRadioButton.UseVisualStyleBackColor = true;
            this.allRadioButton.CheckedChanged += new System.EventHandler(this.FilterBy_CheckedChanged);
            // 
            // filterByLabel
            // 
            this.filterByLabel.AutoSize = true;
            this.filterByLabel.Location = new System.Drawing.Point(5, 4);
            this.filterByLabel.Name = "filterByLabel";
            this.filterByLabel.Size = new System.Drawing.Size(0, 15);
            this.filterByLabel.TabIndex = 4;
            // 
            // treePanel
            // 
            this.treePanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(145)))), ((int)(((byte)(167)))), ((int)(((byte)(180)))));
            this.treePanel.Controls.Add(this.tree);
            this.treePanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treePanel.Location = new System.Drawing.Point(3, 46);
            this.treePanel.Name = "treePanel";
            this.treePanel.Padding = new System.Windows.Forms.Padding(1);
            this.treePanel.Size = new System.Drawing.Size(347, 300);
            this.treePanel.TabIndex = 3;
            // 
            // tree
            // 
            this.tree.AllowDrop = true;
            this.tree.AutoRowHeight = true;
            this.tree.BackColor = System.Drawing.SystemColors.Window;
            this.tree.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.tree.Columns.Add(this.Column0);
            this.tree.Columns.Add(this.Column1);
            this.tree.Columns.Add(this.Column2);
            this.tree.Columns.Add(this.Column3);
            this.tree.Columns.Add(this.Column4);
            this.tree.Columns.Add(this.Column5);
            this.tree.Cursor = System.Windows.Forms.Cursors.Default;
            this.tree.DefaultToolTipProvider = null;
            this.tree.DisplayDraggingNodes = true;
            this.tree.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tree.DragDropMarkColor = System.Drawing.Color.Black;
            this.tree.FullRowSelect = true;
            this.tree.LineColor = System.Drawing.SystemColors.ControlDark;
            this.tree.Location = new System.Drawing.Point(1, 1);
            this.tree.Model = null;
            this.tree.Name = "tree";
            this.tree.NodeControls.Add(this.isChecked);
            this.tree.NodeControls.Add(this.icon);
            this.tree.NodeControls.Add(this.name);
            this.tree.NodeControls.Add(this.facility);
            this.tree.NodeControls.Add(this.department);
            this.tree.NodeControls.Add(this.dateOfService);
            this.tree.NodeControls.Add(this.encounter);
            this.tree.NodeControls.Add(this.vipIcon);
            this.tree.NodeControls.Add(this.lockedIcon);
            this.tree.NodeControls.Add(this.deficiencyIcon);
            this.tree.Padding = new System.Windows.Forms.Padding(1);
            this.tree.RowHeight = 20;
            this.tree.SelectedNode = null;
            this.tree.SelectionMode = McK.EIG.ROI.Client.Base.View.Common.Tree.TreeSelectionMode.Multiple;
            this.tree.Size = new System.Drawing.Size(345, 311);
            this.tree.TabIndex = 3;
            this.tree.Text = "tree";
            this.tree.UseColumns = true;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Controls.Add(this.tableLayoutPanel2);
            this.bottomPanel.Controls.Add(this.dsrTreeInfoLabel);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.bottomPanel.Location = new System.Drawing.Point(3, 365);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(347, 95);
            this.bottomPanel.TabIndex = 4;
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 2;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 51.29683F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 48.70317F));
            this.tableLayoutPanel2.Controls.Add(this.removeAllButton, 1, 0);
            this.tableLayoutPanel2.Controls.Add(this.modifyBillingTierButton, 0, 1);
            this.tableLayoutPanel2.Controls.Add(this.removeButton, 0, 0);
            this.tableLayoutPanel2.Controls.Add(this.resendButton, 1, 1);
            this.tableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.tableLayoutPanel2.Location = new System.Drawing.Point(0, 28);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 2;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 29F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(347, 60);
            this.tableLayoutPanel2.TabIndex = 6;
            // 
            // removeAllButton
            // 
            this.removeAllButton.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.removeAllButton.Location = new System.Drawing.Point(180, 4);
            this.removeAllButton.Name = "removeAllButton";
            this.removeAllButton.Size = new System.Drawing.Size(113, 23);
            this.removeAllButton.TabIndex = 6;
            this.removeAllButton.UseVisualStyleBackColor = true;
            this.removeAllButton.Click += new System.EventHandler(this.removeAllButton_Click);
            // 
            // modifyBillingTierButton
            // 
            this.modifyBillingTierButton.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.modifyBillingTierButton.AutoEllipsis = true;
            this.modifyBillingTierButton.Location = new System.Drawing.Point(36, 34);
            this.modifyBillingTierButton.Name = "modifyBillingTierButton";
            this.modifyBillingTierButton.Size = new System.Drawing.Size(138, 23);
            this.modifyBillingTierButton.TabIndex = 5;
            this.modifyBillingTierButton.UseVisualStyleBackColor = true;
            this.modifyBillingTierButton.Click += new System.EventHandler(this.modifyBillingTierButton_Click);
            // 
            // removeButton
            // 
            this.removeButton.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.removeButton.Location = new System.Drawing.Point(36, 4);
            this.removeButton.Name = "removeButton";
            this.removeButton.Size = new System.Drawing.Size(138, 23);
            this.removeButton.TabIndex = 5;
            this.removeButton.UseVisualStyleBackColor = true;
            this.removeButton.Click += new System.EventHandler(this.removeButton_Click);
            // 
            // resendButton
            // 
            this.resendButton.Enabled = false;
            this.resendButton.Location = new System.Drawing.Point(180, 34);
            this.resendButton.Name = "resendButton";
            this.resendButton.Size = new System.Drawing.Size(113, 23);
            this.resendButton.TabIndex = 7;
            this.resendButton.UseVisualStyleBackColor = true;
            // 
            // dsrTreeInfoLabel
            // 
            this.dsrTreeInfoLabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.dsrTreeInfoLabel.Location = new System.Drawing.Point(0, 0);
            this.dsrTreeInfoLabel.Name = "dsrTreeInfoLabel";
            this.dsrTreeInfoLabel.Size = new System.Drawing.Size(347, 40);
            this.dsrTreeInfoLabel.TabIndex = 4;
            // 
            // ReleaseTreeUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.documentsGroupBox);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ReleaseTreeUI";
            this.Size = new System.Drawing.Size(359, 475);
            this.documentsGroupBox.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.topPanel.ResumeLayout(false);
            this.topPanel.PerformLayout();
            this.treePanel.ResumeLayout(false);
            this.bottomPanel.ResumeLayout(false);
            this.tableLayoutPanel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private TreeColumn Column0;
        private TreeColumn Column1;
        private TreeColumn Column2;
        private TreeColumn Column3;
        private TreeColumn Column4;
        private TreeColumn Column5;
        private NodeIcon icon;
        private NodeTextBox name;
        private NodeTextBox facility;
        private NodeTextBox department;
        private NodeTextBox dateOfService;
        private NodeTextBox encounter;
        private NodeIcon vipIcon;
        private NodeIcon lockedIcon;
        private NodeIcon deficiencyIcon;
        private NodeCheckBox isChecked;
        private System.Windows.Forms.GroupBox documentsGroupBox;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.RadioButton unReleasedRadioButton;
        private System.Windows.Forms.RadioButton releasedRadioButton;
        private System.Windows.Forms.RadioButton allRadioButton;
        private System.Windows.Forms.Label filterByLabel;
        private TreeViewAdv tree;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.Panel treePanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Button removeAllButton;
        private System.Windows.Forms.Button removeButton;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.Button collapseButton;
        private System.Windows.Forms.Button expandButton;
        private System.Windows.Forms.Label dsrTreeInfoLabel;
        private System.Windows.Forms.Button modifyBillingTierButton;
        private System.Windows.Forms.Button resendButton;
    }
}
