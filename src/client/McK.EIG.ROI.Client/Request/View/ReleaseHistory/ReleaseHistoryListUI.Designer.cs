namespace McK.EIG.ROI.Client.Request.View.ReleaseHistory
{
    partial class ReleaseHistoryListUI
    {
        private System.ComponentModel.IContainer components = null;

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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle5 = new System.Windows.Forms.DataGridViewCellStyle();
            this.outerPanel = new System.Windows.Forms.TableLayoutPanel();
            this.filtersGroupBox = new System.Windows.Forms.GroupBox();
            this.panel2 = new System.Windows.Forms.Panel();
            this.releaseDateLabel = new System.Windows.Forms.Label();
            this.presetDateRange = new McK.EIG.ROI.Client.Base.View.PresetDateRange();
            this.usernameCombo = new System.Windows.Forms.ComboBox();
            this.userNameLabel = new System.Windows.Forms.Label();
            this.releaseListpanel = new System.Windows.Forms.Panel();
            this.listPanel = new System.Windows.Forms.Panel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.labelPanel = new System.Windows.Forms.Panel();
            this.countPageLabel = new System.Windows.Forms.Label();
            this.countReleaseLabel = new System.Windows.Forms.Label();
            this.patientsGroupBox = new System.Windows.Forms.GroupBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.patientsPanel = new System.Windows.Forms.TableLayoutPanel();
            this.patientList = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.optionPanel = new System.Windows.Forms.Panel();
            this.filterRadioButton = new System.Windows.Forms.RadioButton();
            this.allPatientRadioButton = new System.Windows.Forms.RadioButton();
            this.patientInfoPanel = new System.Windows.Forms.Panel();
            this.epnValueLabel = new System.Windows.Forms.Label();
            this.epnLabel = new System.Windows.Forms.Label();
            this.mrnValueLabel = new System.Windows.Forms.Label();
            this.mrnLabel = new System.Windows.Forms.Label();
            this.flowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.nameValueLabel = new System.Windows.Forms.Label();
            this.vipPatientIcon = new System.Windows.Forms.PictureBox();
            this.lockedPatientIcon = new System.Windows.Forms.PictureBox();
            this.facilityValueLabel = new System.Windows.Forms.Label();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.ssnValueLabel = new System.Windows.Forms.Label();
            this.dobValueLabel = new System.Windows.Forms.Label();
            this.genderValueLabel = new System.Windows.Forms.Label();
            this.ssnLabel = new System.Windows.Forms.Label();
            this.dobShortLabel = new System.Windows.Forms.Label();
            this.genderLabel = new System.Windows.Forms.Label();
            this.patientNameLabel = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.outerPanel.SuspendLayout();
            this.filtersGroupBox.SuspendLayout();
            this.panel2.SuspendLayout();
            this.releaseListpanel.SuspendLayout();
            this.listPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.labelPanel.SuspendLayout();
            this.patientsGroupBox.SuspendLayout();
            this.panel1.SuspendLayout();
            this.patientsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.patientList)).BeginInit();
            this.optionPanel.SuspendLayout();
            this.patientInfoPanel.SuspendLayout();
            this.flowLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.vipPatientIcon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.lockedPatientIcon)).BeginInit();
            this.SuspendLayout();
            // 
            // outerPanel
            // 
            this.outerPanel.ColumnCount = 2;
            this.outerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 51.05263F));
            this.outerPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 48.94737F));
            this.outerPanel.Controls.Add(this.filtersGroupBox, 1, 0);
            this.outerPanel.Controls.Add(this.releaseListpanel, 0, 1);
            this.outerPanel.Controls.Add(this.patientsGroupBox, 0, 0);
            this.outerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.outerPanel.Location = new System.Drawing.Point(0, 0);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.RowCount = 2;
            this.outerPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 34.62366F));
            this.outerPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 65.37634F));
            this.outerPanel.Size = new System.Drawing.Size(950, 465);
            this.outerPanel.TabIndex = 7;
            // 
            // filtersGroupBox
            // 
            this.filtersGroupBox.AutoSize = true;
            this.filtersGroupBox.Controls.Add(this.panel2);
            this.filtersGroupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.filtersGroupBox.Location = new System.Drawing.Point(487, 3);
            this.filtersGroupBox.Name = "filtersGroupBox";
            this.filtersGroupBox.Size = new System.Drawing.Size(460, 155);
            this.filtersGroupBox.TabIndex = 6;
            this.filtersGroupBox.TabStop = false;
            // 
            // panel2
            // 
            this.panel2.AutoScroll = true;
            this.panel2.Controls.Add(this.releaseDateLabel);
            this.panel2.Controls.Add(this.presetDateRange);
            this.panel2.Controls.Add(this.usernameCombo);
            this.panel2.Controls.Add(this.userNameLabel);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(3, 16);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(454, 136);
            this.panel2.TabIndex = 16;
            // 
            // releaseDateLabel
            // 
            this.releaseDateLabel.AutoSize = true;
            this.releaseDateLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.releaseDateLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.releaseDateLabel.Location = new System.Drawing.Point(9, 39);
            this.releaseDateLabel.Name = "releaseDateLabel";
            this.releaseDateLabel.Size = new System.Drawing.Size(0, 15);
            this.releaseDateLabel.TabIndex = 49;
            // 
            // presetDateRange
            // 
            this.presetDateRange.BackColor = System.Drawing.SystemColors.Control;
            this.presetDateRange.DateRangeOption = "All";
            this.presetDateRange.FromDate = null;
            this.presetDateRange.IsValidDateRange = false;
            this.presetDateRange.Location = new System.Drawing.Point(31, 31);
            this.presetDateRange.Name = "presetDateRange";
            this.presetDateRange.Size = new System.Drawing.Size(420, 31);
            this.presetDateRange.TabIndex = 48;
            this.presetDateRange.ToDate = null;
            // 
            // usernameCombo
            // 
            this.usernameCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.usernameCombo.FormattingEnabled = true;
            this.usernameCombo.Location = new System.Drawing.Point(92, 4);
            this.usernameCombo.Name = "usernameCombo";
            this.usernameCombo.Size = new System.Drawing.Size(166, 21);
            this.usernameCombo.TabIndex = 2;
            // 
            // userNameLabel
            // 
            this.userNameLabel.AutoSize = true;
            this.userNameLabel.Location = new System.Drawing.Point(8, 12);
            this.userNameLabel.Name = "userNameLabel";
            this.userNameLabel.Size = new System.Drawing.Size(0, 13);
            this.userNameLabel.TabIndex = 0;
            // 
            // releaseListpanel
            // 
            this.releaseListpanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.releaseListpanel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.releaseListpanel.Controls.Add(this.listPanel);
            this.releaseListpanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.releaseListpanel.Location = new System.Drawing.Point(3, 164);
            this.releaseListpanel.Name = "releaseListpanel";
            this.releaseListpanel.Size = new System.Drawing.Size(478, 298);
            this.releaseListpanel.TabIndex = 8;
            // 
            // listPanel
            // 
            this.listPanel.BackColor = System.Drawing.Color.Transparent;
            this.listPanel.Controls.Add(this.grid);
            this.listPanel.Controls.Add(this.labelPanel);
            this.listPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listPanel.Location = new System.Drawing.Point(0, 0);
            this.listPanel.Name = "listPanel";
            this.listPanel.Size = new System.Drawing.Size(478, 298);
            this.listPanel.TabIndex = 2;
            // 
            // grid
            // 
            this.grid.AllowUserToAddRows = false;
            this.grid.AllowUserToDeleteRows = false;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(237)))), ((int)(((byte)(239)))), ((int)(((byte)(246)))));
            this.grid.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.grid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.grid.BackgroundColor = System.Drawing.SystemColors.ControlLightLight;
            this.grid.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.grid.ChangeValidator = null;
            this.grid.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.Single;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            dataGridViewCellStyle2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.grid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle2;
            this.grid.ColumnHeadersHeight = 40;
            this.grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.DisableResizing;
            this.grid.ConfirmSelection = false;
            this.grid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.grid.EnableHeadersVisualStyles = false;
            this.grid.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(207)))), ((int)(((byte)(211)))), ((int)(((byte)(226)))));
            this.grid.Location = new System.Drawing.Point(0, 23);
            this.grid.Margin = new System.Windows.Forms.Padding(3, 3, 0, 3);
            this.grid.Name = "grid";
            this.grid.RowHeadersVisible = false;
            dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.grid.RowsDefaultCellStyle = dataGridViewCellStyle3;
            this.grid.RowTemplate.Height = 50;
            this.grid.RowTemplate.ReadOnly = true;
            this.grid.RowTemplate.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(478, 275);
            this.grid.SortEnabled = false;
            this.grid.TabIndex = 4;
            this.grid.CellFormatting += new System.Windows.Forms.DataGridViewCellFormattingEventHandler(grid_CellFormatting);
            // 
            // labelPanel
            // 
            this.labelPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(236)))), ((int)(((byte)(236)))));
            this.labelPanel.Controls.Add(this.countPageLabel);
            this.labelPanel.Controls.Add(this.countReleaseLabel);
            this.labelPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.labelPanel.Location = new System.Drawing.Point(0, 0);
            this.labelPanel.Name = "labelPanel";
            this.labelPanel.Size = new System.Drawing.Size(478, 23);
            this.labelPanel.TabIndex = 3;
            // 
            // countPageLabel
            // 
            this.countPageLabel.AutoSize = true;
            this.countPageLabel.Location = new System.Drawing.Point(96, 5);
            this.countPageLabel.Name = "countPageLabel";
            this.countPageLabel.Size = new System.Drawing.Size(0, 13);
            this.countPageLabel.TabIndex = 2;
            // 
            // countReleaseLabel
            // 
            this.countReleaseLabel.AutoSize = true;
            this.countReleaseLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.countReleaseLabel.Location = new System.Drawing.Point(3, 4);
            this.countReleaseLabel.Name = "countReleaseLabel";
            this.countReleaseLabel.Size = new System.Drawing.Size(0, 15);
            this.countReleaseLabel.TabIndex = 1;
            // 
            // patientsGroupBox
            // 
            this.patientsGroupBox.Controls.Add(this.panel1);
            this.patientsGroupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientsGroupBox.Location = new System.Drawing.Point(3, 3);
            this.patientsGroupBox.Name = "patientsGroupBox";
            this.patientsGroupBox.Size = new System.Drawing.Size(478, 155);
            this.patientsGroupBox.TabIndex = 4;
            this.patientsGroupBox.TabStop = false;
            // 
            // panel1
            // 
            this.panel1.AutoScroll = true;
            this.panel1.Controls.Add(this.patientsPanel);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(3, 16);
            this.panel1.Margin = new System.Windows.Forms.Padding(0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(472, 136);
            this.panel1.TabIndex = 1;
            // 
            // patientsPanel
            // 
            this.patientsPanel.ColumnCount = 3;
            this.patientsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.patientsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 48F));
            this.patientsPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 52F));
            this.patientsPanel.Controls.Add(this.patientList, 0, 0);
            this.patientsPanel.Controls.Add(this.optionPanel, 0, 0);
            this.patientsPanel.Controls.Add(this.patientInfoPanel, 2, 0);
            this.patientsPanel.Location = new System.Drawing.Point(0, 0);
            this.patientsPanel.Name = "patientsPanel";
            this.patientsPanel.RowCount = 1;
            this.patientsPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.patientsPanel.Size = new System.Drawing.Size(472, 136);
            this.patientsPanel.TabIndex = 0;
            // 
            // patientList
            // 
            this.patientList.AllowUserToAddRows = false;
            this.patientList.AllowUserToDeleteRows = false;
            this.patientList.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.patientList.BackgroundColor = System.Drawing.Color.White;
            this.patientList.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.patientList.ChangeValidator = null;
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle4.BackColor = System.Drawing.Color.WhiteSmoke;
            dataGridViewCellStyle4.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle4.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle4.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle4.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.patientList.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle4;
            this.patientList.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.patientList.ConfirmSelection = false;
            dataGridViewCellStyle5.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle5.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle5.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle5.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle5.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle5.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle5.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.patientList.DefaultCellStyle = dataGridViewCellStyle5;
            this.patientList.EnableHeadersVisualStyles = false;
            this.patientList.GridColor = System.Drawing.Color.LightSteelBlue;
            this.patientList.Location = new System.Drawing.Point(97, 3);
            this.patientList.Margin = new System.Windows.Forms.Padding(0, 3, 3, 3);
            this.patientList.Name = "patientList";
            this.patientList.ReadOnly = true;
            this.patientList.RowHeadersVisible = false;
            this.patientList.SelectionHandler = null;
            this.patientList.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.patientList.Size = new System.Drawing.Size(171, 117);
            this.patientList.SortEnabled = false;
            this.patientList.TabIndex = 43;
            // 
            // optionPanel
            // 
            this.optionPanel.Controls.Add(this.filterRadioButton);
            this.optionPanel.Controls.Add(this.allPatientRadioButton);
            this.optionPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.optionPanel.Location = new System.Drawing.Point(3, 3);
            this.optionPanel.Name = "optionPanel";
            this.optionPanel.Size = new System.Drawing.Size(91, 130);
            this.optionPanel.TabIndex = 0;
            // 
            // filterRadioButton
            // 
            this.filterRadioButton.AutoSize = true;
            this.filterRadioButton.Location = new System.Drawing.Point(3, 27);
            this.filterRadioButton.Margin = new System.Windows.Forms.Padding(3, 3, 0, 3);
            this.filterRadioButton.Name = "filterRadioButton";
            this.filterRadioButton.Size = new System.Drawing.Size(14, 13);
            this.filterRadioButton.TabIndex = 1;
            this.filterRadioButton.UseVisualStyleBackColor = true;
            // 
            // allPatientRadioButton
            // 
            this.allPatientRadioButton.AutoSize = true;
            this.allPatientRadioButton.Checked = true;
            this.allPatientRadioButton.Location = new System.Drawing.Point(2, 4);
            this.allPatientRadioButton.Margin = new System.Windows.Forms.Padding(3, 3, 0, 3);
            this.allPatientRadioButton.Name = "allPatientRadioButton";
            this.allPatientRadioButton.Size = new System.Drawing.Size(14, 13);
            this.allPatientRadioButton.TabIndex = 0;
            this.allPatientRadioButton.TabStop = true;
            this.allPatientRadioButton.UseVisualStyleBackColor = true;
            this.allPatientRadioButton.CheckedChanged += new System.EventHandler(this.allPatientRadioButton_CheckedChanged);
            // 
            // patientInfoPanel
            // 
            this.patientInfoPanel.Controls.Add(this.epnValueLabel);
            this.patientInfoPanel.Controls.Add(this.epnLabel);
            this.patientInfoPanel.Controls.Add(this.mrnValueLabel);
            this.patientInfoPanel.Controls.Add(this.mrnLabel);
            this.patientInfoPanel.Controls.Add(this.flowLayoutPanel);
            this.patientInfoPanel.Controls.Add(this.facilityValueLabel);
            this.patientInfoPanel.Controls.Add(this.facilityLabel);
            this.patientInfoPanel.Controls.Add(this.ssnValueLabel);
            this.patientInfoPanel.Controls.Add(this.dobValueLabel);
            this.patientInfoPanel.Controls.Add(this.genderValueLabel);
            this.patientInfoPanel.Controls.Add(this.ssnLabel);
            this.patientInfoPanel.Controls.Add(this.dobShortLabel);
            this.patientInfoPanel.Controls.Add(this.genderLabel);
            this.patientInfoPanel.Controls.Add(this.patientNameLabel);
            this.patientInfoPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientInfoPanel.Location = new System.Drawing.Point(280, 3);
            this.patientInfoPanel.Name = "patientInfoPanel";
            this.patientInfoPanel.Size = new System.Drawing.Size(189, 130);
            this.patientInfoPanel.TabIndex = 42;
            // 
            // epnValueLabel
            // 
            this.epnValueLabel.AutoSize = true;
            this.epnValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnValueLabel.Location = new System.Drawing.Point(62, 106);
            this.epnValueLabel.Name = "epnValueLabel";
            this.epnValueLabel.Size = new System.Drawing.Size(0, 15);
            this.epnValueLabel.TabIndex = 39;
            // 
            // epnLabel
            // 
            this.epnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnLabel.Location = new System.Drawing.Point(11, 105);
            this.epnLabel.Name = "epnLabel";
            this.epnLabel.Size = new System.Drawing.Size(37, 15);
            this.epnLabel.TabIndex = 34;
            this.epnLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // mrnValueLabel
            // 
            this.mrnValueLabel.AutoSize = true;
            this.mrnValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnValueLabel.Location = new System.Drawing.Point(62, 90);
            this.mrnValueLabel.Name = "mrnValueLabel";
            this.mrnValueLabel.Size = new System.Drawing.Size(0, 15);
            this.mrnValueLabel.TabIndex = 41;
            this.mrnValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // mrnLabel
            // 
            this.mrnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnLabel.Location = new System.Drawing.Point(11, 89);
            this.mrnLabel.Name = "mrnLabel";
            this.mrnLabel.Size = new System.Drawing.Size(43, 15);
            this.mrnLabel.TabIndex = 40;
            this.mrnLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // flowLayoutPanel
            // 
            this.flowLayoutPanel.Controls.Add(this.nameValueLabel);
            this.flowLayoutPanel.Controls.Add(this.vipPatientIcon);
            this.flowLayoutPanel.Controls.Add(this.lockedPatientIcon);
            this.flowLayoutPanel.Location = new System.Drawing.Point(62, 3);
            this.flowLayoutPanel.Name = "flowLayoutPanel";
            this.flowLayoutPanel.Size = new System.Drawing.Size(127, 18);
            this.flowLayoutPanel.TabIndex = 65;
            // 
            // nameValueLabel
            // 
            this.nameValueLabel.AutoEllipsis = true;
            this.nameValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nameValueLabel.Location = new System.Drawing.Point(3, 0);
            this.nameValueLabel.Name = "nameValueLabel";
            this.nameValueLabel.Size = new System.Drawing.Size(73, 21);
            this.nameValueLabel.TabIndex = 57;
            this.nameValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // vipPatientIcon
            // 
            this.vipPatientIcon.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.vip_patient_icon;
            this.vipPatientIcon.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.vipPatientIcon.Dock = System.Windows.Forms.DockStyle.Left;
            this.vipPatientIcon.Location = new System.Drawing.Point(82, 3);
            this.vipPatientIcon.Name = "vipPatientIcon";
            this.vipPatientIcon.Size = new System.Drawing.Size(17, 15);
            this.vipPatientIcon.TabIndex = 64;
            this.vipPatientIcon.TabStop = false;
            this.vipPatientIcon.Visible = false;
            // 
            // lockedPatientIcon
            // 
            this.lockedPatientIcon.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.locked_patient_icon;
            this.lockedPatientIcon.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.lockedPatientIcon.Dock = System.Windows.Forms.DockStyle.Left;
            this.lockedPatientIcon.Location = new System.Drawing.Point(105, 3);
            this.lockedPatientIcon.Name = "lockedPatientIcon";
            this.lockedPatientIcon.Size = new System.Drawing.Size(17, 15);
            this.lockedPatientIcon.TabIndex = 63;
            this.lockedPatientIcon.TabStop = false;
            this.lockedPatientIcon.Visible = false;
            // 
            // facilityValueLabel
            // 
            this.facilityValueLabel.AutoSize = true;
            this.facilityValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityValueLabel.Location = new System.Drawing.Point(62, 74);
            this.facilityValueLabel.Name = "facilityValueLabel";
            this.facilityValueLabel.Size = new System.Drawing.Size(0, 15);
            this.facilityValueLabel.TabIndex = 39;
            this.facilityValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // facilityLabel
            // 
            this.facilityLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityLabel.Location = new System.Drawing.Point(11, 73);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(48, 15);
            this.facilityLabel.TabIndex = 34;
            this.facilityLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // ssnValueLabel
            // 
            this.ssnValueLabel.AutoSize = true;
            this.ssnValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ssnValueLabel.Location = new System.Drawing.Point(62, 58);
            this.ssnValueLabel.Name = "ssnValueLabel";
            this.ssnValueLabel.Size = new System.Drawing.Size(0, 15);
            this.ssnValueLabel.TabIndex = 60;
            this.ssnValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // dobValueLabel
            // 
            this.dobValueLabel.AutoSize = true;
            this.dobValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dobValueLabel.Location = new System.Drawing.Point(62, 42);
            this.dobValueLabel.Name = "dobValueLabel";
            this.dobValueLabel.Size = new System.Drawing.Size(0, 15);
            this.dobValueLabel.TabIndex = 59;
            // 
            // genderValueLabel
            // 
            this.genderValueLabel.AutoSize = true;
            this.genderValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.genderValueLabel.Location = new System.Drawing.Point(62, 24);
            this.genderValueLabel.Name = "genderValueLabel";
            this.genderValueLabel.Size = new System.Drawing.Size(0, 15);
            this.genderValueLabel.TabIndex = 58;
            this.genderValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // ssnLabel
            // 
            this.ssnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ssnLabel.Location = new System.Drawing.Point(11, 57);
            this.ssnLabel.Name = "ssnLabel";
            this.ssnLabel.Size = new System.Drawing.Size(36, 15);
            this.ssnLabel.TabIndex = 56;
            this.ssnLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // dobShortLabel
            // 
            this.dobShortLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dobShortLabel.Location = new System.Drawing.Point(11, 41);
            this.dobShortLabel.Name = "dobShortLabel";
            this.dobShortLabel.Size = new System.Drawing.Size(36, 15);
            this.dobShortLabel.TabIndex = 55;
            this.dobShortLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // genderLabel
            // 
            this.genderLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.genderLabel.Location = new System.Drawing.Point(11, 25);
            this.genderLabel.Name = "genderLabel";
            this.genderLabel.Size = new System.Drawing.Size(51, 15);
            this.genderLabel.TabIndex = 54;
            this.genderLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // patientNameLabel
            // 
            this.patientNameLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientNameLabel.Location = new System.Drawing.Point(11, 8);
            this.patientNameLabel.Name = "patientNameLabel";
            this.patientNameLabel.Size = new System.Drawing.Size(45, 15);
            this.patientNameLabel.TabIndex = 53;
            // 
            // ReleaseHistoryListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.outerPanel);
            this.Name = "ReleaseHistoryListUI";
            this.Size = new System.Drawing.Size(950, 465);
            this.outerPanel.ResumeLayout(false);
            this.outerPanel.PerformLayout();
            this.filtersGroupBox.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.releaseListpanel.ResumeLayout(false);
            this.listPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.labelPanel.ResumeLayout(false);
            this.labelPanel.PerformLayout();
            this.patientsGroupBox.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.patientsPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.patientList)).EndInit();
            this.optionPanel.ResumeLayout(false);
            this.optionPanel.PerformLayout();
            this.patientInfoPanel.ResumeLayout(false);
            this.patientInfoPanel.PerformLayout();
            this.flowLayoutPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.vipPatientIcon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.lockedPatientIcon)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel outerPanel;
        private System.Windows.Forms.Panel releaseListpanel;
        private System.Windows.Forms.Panel listPanel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.Panel labelPanel;
        private System.Windows.Forms.Label countPageLabel;
        private System.Windows.Forms.Label countReleaseLabel;
        private System.Windows.Forms.GroupBox patientsGroupBox;
        private System.Windows.Forms.TableLayoutPanel patientsPanel;
        private System.Windows.Forms.Panel optionPanel;
        private System.Windows.Forms.RadioButton filterRadioButton;
        private System.Windows.Forms.RadioButton allPatientRadioButton;
        private System.Windows.Forms.Panel patientInfoPanel;
        private System.Windows.Forms.Label mrnLabel;
        private System.Windows.Forms.Label mrnValueLabel;
        private System.Windows.Forms.Label facilityLabel;
        private System.Windows.Forms.Label facilityValueLabel;
        private System.Windows.Forms.PictureBox vipPatientIcon;
        private System.Windows.Forms.PictureBox lockedPatientIcon;
        private System.Windows.Forms.Label epnLabel;
        private System.Windows.Forms.Label epnValueLabel;
        private System.Windows.Forms.Label ssnValueLabel;
        private System.Windows.Forms.Label dobValueLabel;
        private System.Windows.Forms.Label genderValueLabel;
        private System.Windows.Forms.Label nameValueLabel;
        private System.Windows.Forms.Label ssnLabel;
        private System.Windows.Forms.Label dobShortLabel;
        private System.Windows.Forms.Label genderLabel;
        private System.Windows.Forms.Label patientNameLabel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid patientList;
        private System.Windows.Forms.GroupBox filtersGroupBox;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.ComboBox usernameCombo;
        private System.Windows.Forms.Label userNameLabel;
        private System.Windows.Forms.Label releaseDateLabel;
        private McK.EIG.ROI.Client.Base.View.PresetDateRange presetDateRange;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
