using McK.EIG.ROI.Client.Base.View.Common.Tree;
using McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords
{
    partial class PatientRecordsViewUI
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
            this.icon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.name = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.facility = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.department = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.dateOfService = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.encounter = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeTextBox();
            this.vipIcon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.lockedIcon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.deficiencyIcon = new McK.EIG.ROI.Client.Base.View.Common.Tree.NodeControls.NodeIcon();
            this.addButton = new System.Windows.Forms.Button();
            this.addAllButton = new System.Windows.Forms.Button();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.addAllButtonsPanel = new System.Windows.Forms.Panel();
            this.treePanel = new System.Windows.Forms.Panel();
            this.tree = new McK.EIG.ROI.Client.Base.View.Common.Tree.TreeViewAdv();
            this.tableLayoutPanel3 = new System.Windows.Forms.TableLayoutPanel();
            this.topPanel = new System.Windows.Forms.Panel();
            this.recordViewComboBox = new System.Windows.Forms.ComboBox();
            this.encounterRadioButton = new System.Windows.Forms.RadioButton();
            this.sortByLabel = new System.Windows.Forms.Label();
            this.disclosureCheckBox = new System.Windows.Forms.CheckBox();
            this.recordViewLabel = new System.Windows.Forms.Label();
            this.documentRadioButton = new System.Windows.Forms.RadioButton();
            this.showVersionCheckBox = new System.Windows.Forms.CheckBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.collapseButton = new System.Windows.Forms.Button();
            this.expandButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.button2 = new System.Windows.Forms.Button();
            this.eeditDocumentButton = new System.Windows.Forms.Button();
            this.newDocumentButton = new System.Windows.Forms.Button();
            this.editDocumentButton = new System.Windows.Forms.Button();
            this.viewAllButton = new System.Windows.Forms.Button();
            this.editAttachmentButton = new System.Windows.Forms.Button();
            this.viewSelectedButton = new System.Windows.Forms.Button();
            this.newAttachmentButton = new System.Windows.Forms.Button();
            this.encounterDetailsButton = new System.Windows.Forms.Button();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.tableLayoutPanel1.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.addAllButtonsPanel.SuspendLayout();
            this.treePanel.SuspendLayout();
            this.tableLayoutPanel3.SuspendLayout();
            this.topPanel.SuspendLayout();
            this.panel1.SuspendLayout();
            this.footerPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // Column0
            // 
            this.Column0.Header = "";
            this.Column0.Sorted = true;
            this.Column0.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column0.TooltipText = "";
            this.Column0.Width = 190;
            // 
            // Column1
            // 
            this.Column1.Header = "";
            this.Column1.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column1.TooltipText = "";
            this.Column1.Width = 130;
            // 
            // Column2
            // 
            this.Column2.Header = "";
            this.Column2.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column2.TooltipText = null;
            this.Column2.Width = 130;
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
            this.Column4.Width = 70;
            // 
            // Column5
            // 
            this.Column5.Header = "";
            this.Column5.SortOrder = System.Windows.Forms.SortOrder.None;
            this.Column5.TooltipText = null;
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
            this.name.EditOnClick = true;
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
            this.encounter.DataPropertyName = "Encounter";
            this.encounter.EditEnabled = false;
            this.encounter.IncrementalSearchEnabled = true;
            this.encounter.LeftMargin = 3;
            this.encounter.ParentColumn = this.Column4;
            this.encounter.Trimming = System.Drawing.StringTrimming.EllipsisWord;
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
            // addButton
            // 
            this.addButton.Enabled = false;
            this.addButton.Font = new System.Drawing.Font("Webdings", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(2)));
            this.addButton.Location = new System.Drawing.Point(18, 36);
            this.addButton.Name = "addButton";
            this.addButton.Size = new System.Drawing.Size(25, 27);
            this.addButton.TabIndex = 12;
            this.addButton.Text = "4";
            this.addButton.UseVisualStyleBackColor = true;
            // 
            // addAllButton
            // 
            this.addAllButton.Font = new System.Drawing.Font("Webdings", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(2)));
            this.addAllButton.Location = new System.Drawing.Point(18, 69);
            this.addAllButton.Name = "addAllButton";
            this.addAllButton.Size = new System.Drawing.Size(25, 27);
            this.addAllButton.TabIndex = 13;
            this.addAllButton.Text = "8";
            this.addAllButton.UseVisualStyleBackColor = true;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Controls.Add(this.tableLayoutPanel2, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.tableLayoutPanel3, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.footerPanel, 0, 2);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 3;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 66F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(709, 393);
            this.tableLayoutPanel1.TabIndex = 20;
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 2;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 70F));
            this.tableLayoutPanel2.Controls.Add(this.addAllButtonsPanel, 1, 0);
            this.tableLayoutPanel2.Controls.Add(this.treePanel, 0, 0);
            this.tableLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel2.Location = new System.Drawing.Point(3, 79);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 1;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(703, 201);
            this.tableLayoutPanel2.TabIndex = 20;
            // 
            // addAllButtonsPanel
            // 
            this.addAllButtonsPanel.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.addAllButtonsPanel.Controls.Add(this.addAllButton);
            this.addAllButtonsPanel.Controls.Add(this.addButton);
            this.addAllButtonsPanel.Location = new System.Drawing.Point(636, 44);
            this.addAllButtonsPanel.Name = "addAllButtonsPanel";
            this.addAllButtonsPanel.Size = new System.Drawing.Size(64, 112);
            this.addAllButtonsPanel.TabIndex = 18;
            this.addAllButtonsPanel.Visible = false;
            // 
            // treePanel
            // 
            this.treePanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(145)))), ((int)(((byte)(167)))), ((int)(((byte)(180)))));
            this.treePanel.Controls.Add(this.tree);
            this.treePanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treePanel.Location = new System.Drawing.Point(3, 3);
            this.treePanel.Name = "treePanel";
            this.treePanel.Padding = new System.Windows.Forms.Padding(1);
            this.treePanel.Size = new System.Drawing.Size(627, 195);
            this.treePanel.TabIndex = 11;
            // 
            // tree
            // 
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
            this.tree.LineColor = System.Drawing.SystemColors.ControlDarkDark;
            this.tree.Location = new System.Drawing.Point(1, 1);
            this.tree.Model = null;
            this.tree.Name = "tree";
            this.tree.NodeControls.Add(this.icon);
            this.tree.NodeControls.Add(this.name);
            this.tree.NodeControls.Add(this.facility);
            this.tree.NodeControls.Add(this.department);
            this.tree.NodeControls.Add(this.dateOfService);
            this.tree.NodeControls.Add(this.encounter);
            this.tree.NodeControls.Add(this.vipIcon);
            this.tree.NodeControls.Add(this.lockedIcon);
            this.tree.NodeControls.Add(this.deficiencyIcon);
            this.tree.RowHeight = 20;
            this.tree.SelectedNode = null;
            this.tree.SelectionMode = McK.EIG.ROI.Client.Base.View.Common.Tree.TreeSelectionMode.Multiple;
            this.tree.Size = new System.Drawing.Size(625, 193);
            this.tree.TabIndex = 6;
            this.tree.Text = "tree";
            this.tree.UseColumns = true;
            this.tree.NodeMouseClick += new System.EventHandler<McK.EIG.ROI.Client.Base.View.Common.Tree.TreeNodeAdvMouseEventArgs>(this.tree_NodeMouseClick);
            // 
            // tableLayoutPanel3
            // 
            this.tableLayoutPanel3.ColumnCount = 2;
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 71F));
            this.tableLayoutPanel3.Controls.Add(this.topPanel, 0, 0);
            this.tableLayoutPanel3.Controls.Add(this.panel1, 1, 0);
            this.tableLayoutPanel3.Location = new System.Drawing.Point(3, 3);
            this.tableLayoutPanel3.Name = "tableLayoutPanel3";
            this.tableLayoutPanel3.RowCount = 1;
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.Size = new System.Drawing.Size(583, 70);
            this.tableLayoutPanel3.TabIndex = 22;
            // 
            // topPanel
            // 
            this.topPanel.Controls.Add(this.recordViewComboBox);
            this.topPanel.Controls.Add(this.encounterRadioButton);
            this.topPanel.Controls.Add(this.sortByLabel);
            this.topPanel.Controls.Add(this.disclosureCheckBox);
            this.topPanel.Controls.Add(this.recordViewLabel);
            this.topPanel.Controls.Add(this.documentRadioButton);
            this.topPanel.Controls.Add(this.showVersionCheckBox);
            this.topPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.topPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.topPanel.Location = new System.Drawing.Point(3, 3);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(506, 64);
            this.topPanel.TabIndex = 16;
            // 
            // recordViewComboBox
            // 
            this.recordViewComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.recordViewComboBox.FormattingEnabled = true;
            this.recordViewComboBox.Location = new System.Drawing.Point(94, 12);
            this.recordViewComboBox.Name = "recordViewComboBox";
            this.recordViewComboBox.Size = new System.Drawing.Size(160, 23);
            this.recordViewComboBox.TabIndex = 1;
            this.recordViewComboBox.SelectedIndexChanged += new System.EventHandler(this.recordViewComboBox_SelectedIndexChanged);
            // 
            // encounterRadioButton
            // 
            this.encounterRadioButton.AutoSize = true;
            this.encounterRadioButton.Checked = true;
            this.encounterRadioButton.Location = new System.Drawing.Point(93, 45);
            this.encounterRadioButton.Name = "encounterRadioButton";
            this.encounterRadioButton.Size = new System.Drawing.Size(14, 13);
            this.encounterRadioButton.TabIndex = 3;
            this.encounterRadioButton.TabStop = true;
            this.encounterRadioButton.UseVisualStyleBackColor = true;
            this.encounterRadioButton.CheckedChanged += new System.EventHandler(this.encounterRadioButton_CheckedChanged);
            // 
            // sortByLabel
            // 
            this.sortByLabel.AutoSize = true;
            this.sortByLabel.Location = new System.Drawing.Point(8, 45);
            this.sortByLabel.Name = "sortByLabel";
            this.sortByLabel.Size = new System.Drawing.Size(0, 15);
            this.sortByLabel.TabIndex = 2;
            // 
            // disclosureCheckBox
            // 
            this.disclosureCheckBox.AutoSize = true;
            this.disclosureCheckBox.Location = new System.Drawing.Point(265, 20);
            this.disclosureCheckBox.Name = "disclosureCheckBox";
            this.disclosureCheckBox.Size = new System.Drawing.Size(15, 14);
            this.disclosureCheckBox.TabIndex = 2;
            this.disclosureCheckBox.UseVisualStyleBackColor = true;
            this.disclosureCheckBox.CheckedChanged += new System.EventHandler(this.disclosureCheckBox_CheckedChanged);
            // 
            // recordViewLabel
            // 
            this.recordViewLabel.AutoSize = true;
            this.recordViewLabel.Location = new System.Drawing.Point(8, 14);
            this.recordViewLabel.Name = "recordViewLabel";
            this.recordViewLabel.Size = new System.Drawing.Size(0, 15);
            this.recordViewLabel.TabIndex = 0;
            // 
            // documentRadioButton
            // 
            this.documentRadioButton.AutoSize = true;
            this.documentRadioButton.Location = new System.Drawing.Point(174, 45);
            this.documentRadioButton.Name = "documentRadioButton";
            this.documentRadioButton.Size = new System.Drawing.Size(14, 13);
            this.documentRadioButton.TabIndex = 4;
            this.documentRadioButton.TabStop = true;
            this.documentRadioButton.UseVisualStyleBackColor = true;
            this.documentRadioButton.CheckedChanged += new System.EventHandler(this.documentRadioButton_CheckedChanged);
            // 
            // showVersionCheckBox
            // 
            this.showVersionCheckBox.AutoSize = true;
            this.showVersionCheckBox.Location = new System.Drawing.Point(265, 46);
            this.showVersionCheckBox.Name = "showVersionCheckBox";
            this.showVersionCheckBox.Size = new System.Drawing.Size(15, 14);
            this.showVersionCheckBox.TabIndex = 5;
            this.showVersionCheckBox.UseVisualStyleBackColor = true;
            this.showVersionCheckBox.CheckedChanged += new System.EventHandler(this.showVersionsCheckBox_CheckedChanged);
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.collapseButton);
            this.panel1.Controls.Add(this.expandButton);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(515, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(65, 64);
            this.panel1.TabIndex = 17;
            // 
            // collapseButton
            // 
            this.collapseButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.collapseButton.Location = new System.Drawing.Point(1, 35);
            this.collapseButton.Name = "collapseButton";
            this.collapseButton.Size = new System.Drawing.Size(64, 23);
            this.collapseButton.TabIndex = 7;
            this.collapseButton.UseVisualStyleBackColor = true;
            this.collapseButton.Click += new System.EventHandler(this.collapseButton_Click);
            // 
            // expandButton
            // 
            this.expandButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.expandButton.Location = new System.Drawing.Point(0, 6);
            this.expandButton.Name = "expandButton";
            this.expandButton.Size = new System.Drawing.Size(65, 23);
            this.expandButton.TabIndex = 6;
            this.expandButton.UseVisualStyleBackColor = true;
            this.expandButton.Click += new System.EventHandler(this.expandButton_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(519, 35);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(67, 23);
            this.button2.TabIndex = 7;
            this.button2.Text = "button2";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // eeditDocumentButton
            // 
            this.eeditDocumentButton.AutoSize = true;
            this.eeditDocumentButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.eeditDocumentButton.Location = new System.Drawing.Point(362, 3);
            this.eeditDocumentButton.Name = "eeditDocumentButton";
            this.eeditDocumentButton.Size = new System.Drawing.Size(98, 27);
            this.eeditDocumentButton.TabIndex = 11;
            this.eeditDocumentButton.UseVisualStyleBackColor = true;
            this.eeditDocumentButton.Click += new System.EventHandler(this.editDocumentButton_Click);
            // 
            // newDocumentButton
            // 
            this.newDocumentButton.AutoSize = true;
            this.newDocumentButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.newDocumentButton.Location = new System.Drawing.Point(198, 2);
            this.newDocumentButton.Name = "newDocumentButton";
            this.newDocumentButton.Size = new System.Drawing.Size(102, 27);
            this.newDocumentButton.TabIndex = 10;
            this.newDocumentButton.UseVisualStyleBackColor = true;
            this.newDocumentButton.Click += new System.EventHandler(this.newDocumentButton_Click);
            // 
            // editDocumentButton
            // 
            this.editDocumentButton.AutoSize = true;
            this.editDocumentButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.editDocumentButton.Location = new System.Drawing.Point(198, 32);
            this.editDocumentButton.Name = "editDocumentButton";
            this.editDocumentButton.Size = new System.Drawing.Size(102, 27);
            this.editDocumentButton.TabIndex = 11;
            this.editDocumentButton.UseVisualStyleBackColor = true;
            this.editDocumentButton.Click += new System.EventHandler(this.editDocumentButton_Click);
            // 
            // viewAllButton
            // 
            this.viewAllButton.AutoSize = true;
            this.viewAllButton.Enabled = false;
            this.viewAllButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.viewAllButton.Location = new System.Drawing.Point(87, 32);
            this.viewAllButton.Name = "viewAllButton";
            this.viewAllButton.Size = new System.Drawing.Size(102, 27);
            this.viewAllButton.TabIndex = 9;
            this.viewAllButton.UseVisualStyleBackColor = true;
            this.viewAllButton.Click += new System.EventHandler(this.viewAllButton_Click);
            // 
            // editAttachmentButton
            // 
            this.editAttachmentButton.AutoSize = true;
            this.editAttachmentButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.editAttachmentButton.Location = new System.Drawing.Point(315, 32);
            this.editAttachmentButton.Name = "editAttachmentButton";
            this.editAttachmentButton.Size = new System.Drawing.Size(102, 27);
            this.editAttachmentButton.TabIndex = 13;
            this.editAttachmentButton.UseVisualStyleBackColor = true;
            this.editAttachmentButton.Click += new System.EventHandler(this.editAttachmentButton_Click);
            // 
            // viewSelectedButton
            // 
            this.viewSelectedButton.AutoSize = true;
            this.viewSelectedButton.Enabled = false;
            this.viewSelectedButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.viewSelectedButton.Location = new System.Drawing.Point(87, 2);
            this.viewSelectedButton.Name = "viewSelectedButton";
            this.viewSelectedButton.Size = new System.Drawing.Size(102, 27);
            this.viewSelectedButton.TabIndex = 8;
            this.viewSelectedButton.UseVisualStyleBackColor = true;
            this.viewSelectedButton.Click += new System.EventHandler(this.viewSelectedButton_Click);
            // 
            // newAttachmentButton
            // 
            this.newAttachmentButton.AutoSize = true;
            this.newAttachmentButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.newAttachmentButton.Location = new System.Drawing.Point(315, 2);
            this.newAttachmentButton.Name = "newAttachmentButton";
            this.newAttachmentButton.Size = new System.Drawing.Size(102, 27);
            this.newAttachmentButton.TabIndex = 12;
            this.newAttachmentButton.UseVisualStyleBackColor = true;
            this.newAttachmentButton.Click += new System.EventHandler(this.newAttachmentButton_Click);
            // 
            // encounterDetailsButton
            // 
            this.encounterDetailsButton.AutoSize = true;
            this.encounterDetailsButton.Enabled = false;
            this.encounterDetailsButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.encounterDetailsButton.Location = new System.Drawing.Point(1, 2);
            this.encounterDetailsButton.Name = "encounterDetailsButton";
            this.encounterDetailsButton.Size = new System.Drawing.Size(80, 27);
            this.encounterDetailsButton.TabIndex = 7;
            this.encounterDetailsButton.UseVisualStyleBackColor = true;
            this.encounterDetailsButton.Click += new System.EventHandler(this.encounterDetailsButton_Click);
            // 
            // footerPanel
            // 
            this.footerPanel.Controls.Add(this.encounterDetailsButton);
            this.footerPanel.Controls.Add(this.newAttachmentButton);
            this.footerPanel.Controls.Add(this.viewSelectedButton);
            this.footerPanel.Controls.Add(this.editAttachmentButton);
            this.footerPanel.Controls.Add(this.viewAllButton);
            this.footerPanel.Controls.Add(this.editDocumentButton);
            this.footerPanel.Controls.Add(this.newDocumentButton);
            this.footerPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.footerPanel.Location = new System.Drawing.Point(3, 330);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(703, 60);
            this.footerPanel.TabIndex = 23;
            // 
            // PatientRecordsViewUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.tableLayoutPanel1);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "PatientRecordsViewUI";
            this.Size = new System.Drawing.Size(709, 393);
            this.Load += new System.EventHandler(this.PatientRecordsView_Load);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel2.ResumeLayout(false);
            this.addAllButtonsPanel.ResumeLayout(false);
            this.treePanel.ResumeLayout(false);
            this.tableLayoutPanel3.ResumeLayout(false);
            this.topPanel.ResumeLayout(false);
            this.topPanel.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
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
        private System.Windows.Forms.Button addButton;
        private System.Windows.Forms.Button addAllButton;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.Panel addAllButtonsPanel;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.Panel treePanel;
        private TreeViewAdv tree;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Button collapseButton;
        private System.Windows.Forms.Button expandButton;
        private System.Windows.Forms.ComboBox recordViewComboBox;
        private System.Windows.Forms.RadioButton encounterRadioButton;
        private System.Windows.Forms.Label sortByLabel;
        private System.Windows.Forms.CheckBox disclosureCheckBox;
        private System.Windows.Forms.Label recordViewLabel;
        private System.Windows.Forms.RadioButton documentRadioButton;
        private System.Windows.Forms.CheckBox showVersionCheckBox;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel3;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button eeditDocumentButton;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.Button encounterDetailsButton;
        private System.Windows.Forms.Button newAttachmentButton;
        private System.Windows.Forms.Button viewSelectedButton;
        private System.Windows.Forms.Button editAttachmentButton;
        private System.Windows.Forms.Button viewAllButton;
        private System.Windows.Forms.Button editDocumentButton;
        private System.Windows.Forms.Button newDocumentButton;
    }
}
