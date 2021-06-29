namespace McK.EIG.ROI.Client.Request.View.FindRequest
{
    partial class RequestSearchUI
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
            this.reqeustTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.searchCriteriaPanel = new System.Windows.Forms.Panel();
            this.completeDateLabel = new System.Windows.Forms.Label();
            this.completeDateRange = new McK.EIG.ROI.Client.Base.View.CustomDateRange();
            this.receiptDateLabel = new System.Windows.Forms.Label();
            this.customDateRange = new McK.EIG.ROI.Client.Base.View.CustomDateRange();
            this.dobTextBox = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.patientSsnTextBox = new System.Windows.Forms.MaskedTextBox();
            this.dollarLabel = new System.Windows.Forms.Label();
            this.balanceDueTextBox = new System.Windows.Forms.TextBox();
            this.balanceDueComboBox = new System.Windows.Forms.ComboBox();
            this.requestReasonComboBox = new System.Windows.Forms.ComboBox();
            this.requestReasonLabel = new System.Windows.Forms.Label();
            this.balanceDueLabel = new System.Windows.Forms.Label();
            this.epnPanel = new System.Windows.Forms.Panel();
            this.epnFlowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.epnPrefixLabel = new System.Windows.Forms.Label();
            this.patientEpnTextBox = new System.Windows.Forms.TextBox();
            this.epnLabel = new System.Windows.Forms.Label();
            this.mrnPanel = new System.Windows.Forms.Panel();
            this.mrnLabel = new System.Windows.Forms.Label();
            this.patientMrnTextBox = new System.Windows.Forms.TextBox();
            this.requestIdLabel = new System.Windows.Forms.Label();
            this.invoiceNumberLabel = new System.Windows.Forms.Label();
            this.toLabel = new System.Windows.Forms.Label();
            this.fromLabel = new System.Windows.Forms.Label();
            this.requestorTypeLabel = new System.Windows.Forms.Label();
            this.requestorNameLabel = new System.Windows.Forms.Label();
            this.requestStatusLabel = new System.Windows.Forms.Label();
            this.requestIdTextBox = new System.Windows.Forms.TextBox();
            this.invoiceNumberTextBox = new System.Windows.Forms.TextBox();
            this.requestorTypeComboBox = new System.Windows.Forms.ComboBox();
            this.requestorNameTextBox = new System.Windows.Forms.TextBox();
            this.requestStatusComboBox = new System.Windows.Forms.ComboBox();
            this.facilityComboBox = new System.Windows.Forms.ComboBox();
            this.moreInfoImage2 = new System.Windows.Forms.PictureBox();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.encounterLabel = new System.Windows.Forms.Label();
            this.patientSsnLabel = new System.Windows.Forms.Label();
            this.patientDobLabel = new System.Windows.Forms.Label();
            this.patientFirstNameLabel = new System.Windows.Forms.Label();
            this.patientEncounterTextBox = new System.Windows.Forms.TextBox();
            this.moreInfoImage1 = new System.Windows.Forms.PictureBox();
            this.patientFirstNameTextBox = new System.Windows.Forms.TextBox();
            this.patientLastNameTextBox = new System.Windows.Forms.TextBox();
            this.patientLastNameLabel = new System.Windows.Forms.Label();
            this.controlPanel = new System.Windows.Forms.TableLayoutPanel();
            this.resetRequestButton = new System.Windows.Forms.Button();
            this.newRequestButton = new System.Windows.Forms.Button();
            this.findRequestButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.reqeustTableLayoutPanel.SuspendLayout();
            this.searchCriteriaPanel.SuspendLayout();
            this.epnPanel.SuspendLayout();
            this.epnFlowLayoutPanel.SuspendLayout();
            this.mrnPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.moreInfoImage2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.moreInfoImage1)).BeginInit();
            this.controlPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // reqeustTableLayoutPanel
            // 
            this.reqeustTableLayoutPanel.BackColor = System.Drawing.Color.White;
            this.reqeustTableLayoutPanel.ColumnCount = 1;
            this.reqeustTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.reqeustTableLayoutPanel.Controls.Add(this.searchCriteriaPanel, 0, 0);
            this.reqeustTableLayoutPanel.Controls.Add(this.controlPanel, 0, 1);
            this.reqeustTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reqeustTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.reqeustTableLayoutPanel.Name = "reqeustTableLayoutPanel";
            this.reqeustTableLayoutPanel.RowCount = 2;
            this.reqeustTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.reqeustTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 39F));
            this.reqeustTableLayoutPanel.Size = new System.Drawing.Size(882, 300);
            this.reqeustTableLayoutPanel.TabIndex = 0;
            // 
            // searchCriteriaPanel
            // 
            this.searchCriteriaPanel.AutoScroll = true;
            this.searchCriteriaPanel.AutoSize = true;
            this.searchCriteriaPanel.BackColor = System.Drawing.Color.White;
            this.searchCriteriaPanel.Controls.Add(this.completeDateLabel);
            this.searchCriteriaPanel.Controls.Add(this.completeDateRange);
            this.searchCriteriaPanel.Controls.Add(this.receiptDateLabel);
            this.searchCriteriaPanel.Controls.Add(this.customDateRange);
            this.searchCriteriaPanel.Controls.Add(this.dobTextBox);
            this.searchCriteriaPanel.Controls.Add(this.patientSsnTextBox);
            this.searchCriteriaPanel.Controls.Add(this.dollarLabel);
            this.searchCriteriaPanel.Controls.Add(this.balanceDueTextBox);
            this.searchCriteriaPanel.Controls.Add(this.balanceDueComboBox);
            this.searchCriteriaPanel.Controls.Add(this.requestReasonComboBox);
            this.searchCriteriaPanel.Controls.Add(this.requestReasonLabel);
            this.searchCriteriaPanel.Controls.Add(this.balanceDueLabel);
            this.searchCriteriaPanel.Controls.Add(this.epnPanel);
            this.searchCriteriaPanel.Controls.Add(this.mrnPanel);
            this.searchCriteriaPanel.Controls.Add(this.requestIdLabel);
            this.searchCriteriaPanel.Controls.Add(this.invoiceNumberLabel);
            this.searchCriteriaPanel.Controls.Add(this.toLabel);
            this.searchCriteriaPanel.Controls.Add(this.fromLabel);
            this.searchCriteriaPanel.Controls.Add(this.requestorTypeLabel);
            this.searchCriteriaPanel.Controls.Add(this.requestorNameLabel);
            this.searchCriteriaPanel.Controls.Add(this.requestStatusLabel);
            this.searchCriteriaPanel.Controls.Add(this.requestIdTextBox);
            this.searchCriteriaPanel.Controls.Add(this.invoiceNumberTextBox);
            this.searchCriteriaPanel.Controls.Add(this.requestorTypeComboBox);
            this.searchCriteriaPanel.Controls.Add(this.requestorNameTextBox);
            this.searchCriteriaPanel.Controls.Add(this.requestStatusComboBox);
            this.searchCriteriaPanel.Controls.Add(this.facilityComboBox);
            this.searchCriteriaPanel.Controls.Add(this.moreInfoImage2);
            this.searchCriteriaPanel.Controls.Add(this.facilityLabel);
            this.searchCriteriaPanel.Controls.Add(this.encounterLabel);
            this.searchCriteriaPanel.Controls.Add(this.patientSsnLabel);
            this.searchCriteriaPanel.Controls.Add(this.patientDobLabel);
            this.searchCriteriaPanel.Controls.Add(this.patientFirstNameLabel);
            this.searchCriteriaPanel.Controls.Add(this.patientEncounterTextBox);
            this.searchCriteriaPanel.Controls.Add(this.moreInfoImage1);
            this.searchCriteriaPanel.Controls.Add(this.patientFirstNameTextBox);
            this.searchCriteriaPanel.Controls.Add(this.patientLastNameTextBox);
            this.searchCriteriaPanel.Controls.Add(this.patientLastNameLabel);
            this.searchCriteriaPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.searchCriteriaPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.searchCriteriaPanel.Location = new System.Drawing.Point(3, 3);
            this.searchCriteriaPanel.Name = "searchCriteriaPanel";
            this.searchCriteriaPanel.Size = new System.Drawing.Size(876, 255);
            this.searchCriteriaPanel.TabIndex = 0;
            // 
            // completeDateLabel
            // 
            this.completeDateLabel.AutoSize = true;
            this.completeDateLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.completeDateLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.completeDateLabel.Location = new System.Drawing.Point(317, 184);
            this.completeDateLabel.Name = "completeDateLabel";
            this.completeDateLabel.Size = new System.Drawing.Size(0, 15);
            this.completeDateLabel.TabIndex = 58;
            // 
            // completeDateRange
            // 
            this.completeDateRange.BackColor = System.Drawing.SystemColors.Control;
            this.completeDateRange.DateRangeOption = "All";
            this.completeDateRange.FromDate = null;
            this.completeDateRange.IsValidDateRange = false;
            this.completeDateRange.Location = new System.Drawing.Point(332, 174);
            this.completeDateRange.Name = "completeDateRange";
            this.completeDateRange.Size = new System.Drawing.Size(517, 29);
            this.completeDateRange.TabIndex = 18;
            this.completeDateRange.ToDate = null;
            // 
            // receiptDateLabel
            // 
            this.receiptDateLabel.AutoSize = true;
            this.receiptDateLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.receiptDateLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.receiptDateLabel.Location = new System.Drawing.Point(317, 106);
            this.receiptDateLabel.Name = "receiptDateLabel";
            this.receiptDateLabel.Size = new System.Drawing.Size(0, 15);
            this.receiptDateLabel.TabIndex = 47;
            // 
            // customDateRange
            // 
            this.customDateRange.BackColor = System.Drawing.SystemColors.Control;
            this.customDateRange.DateRangeOption = "All";
            this.customDateRange.FromDate = null;
            this.customDateRange.IsValidDateRange = false;
            this.customDateRange.Location = new System.Drawing.Point(333, 98);
            this.customDateRange.Name = "customDateRange";
            this.customDateRange.Size = new System.Drawing.Size(516, 27);
            this.customDateRange.TabIndex = 15;
            this.customDateRange.ToDate = null;
            // 
            // dobTextBox
            // 
            this.dobTextBox.FormattedDate = null;
            this.dobTextBox.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.dobTextBox.IsValidDate = false;
            this.dobTextBox.Location = new System.Drawing.Point(108, 51);
            this.dobTextBox.Mask = "AA/AA/AAAA";
            this.dobTextBox.Name = "dobTextBox";
            this.dobTextBox.PromptChar = ' ';
            this.dobTextBox.Size = new System.Drawing.Size(170, 21);
            this.dobTextBox.TabIndex = 3;
            // 
            // patientSsnTextBox
            // 
            this.patientSsnTextBox.Location = new System.Drawing.Point(108, 77);
            this.patientSsnTextBox.Name = "patientSsnTextBox";
            this.patientSsnTextBox.Size = new System.Drawing.Size(189, 21);
            this.patientSsnTextBox.TabIndex = 4;
            // 
            // dollarLabel
            // 
            this.dollarLabel.AutoSize = true;
            this.dollarLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dollarLabel.Location = new System.Drawing.Point(565, 79);
            this.dollarLabel.Name = "dollarLabel";
            this.dollarLabel.Size = new System.Drawing.Size(0, 15);
            this.dollarLabel.TabIndex = 57;
            // 
            // balanceDueTextBox
            // 
            this.balanceDueTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.balanceDueTextBox.Location = new System.Drawing.Point(574, 75);
            this.balanceDueTextBox.MaxLength = 9;
            this.balanceDueTextBox.Name = "balanceDueTextBox";
            this.balanceDueTextBox.Size = new System.Drawing.Size(166, 21);
            this.balanceDueTextBox.TabIndex = 14;
            this.balanceDueTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.balanceDueTextBox_KeyPress);
            // 
            // balanceDueComboBox
            // 
            this.balanceDueComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.balanceDueComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.balanceDueComboBox.FormattingEnabled = true;
            this.balanceDueComboBox.Location = new System.Drawing.Point(411, 75);
            this.balanceDueComboBox.Name = "balanceDueComboBox";
            this.balanceDueComboBox.Size = new System.Drawing.Size(146, 23);
            this.balanceDueComboBox.TabIndex = 13;
            // 
            // requestReasonComboBox
            // 
            this.requestReasonComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.requestReasonComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestReasonComboBox.FormattingEnabled = true;
            this.requestReasonComboBox.Location = new System.Drawing.Point(622, 50);
            this.requestReasonComboBox.Name = "requestReasonComboBox";
            this.requestReasonComboBox.Size = new System.Drawing.Size(118, 23);
            this.requestReasonComboBox.TabIndex = 12;
            // 
            // requestReasonLabel
            // 
            this.requestReasonLabel.AutoSize = true;
            this.requestReasonLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestReasonLabel.Location = new System.Drawing.Point(534, 54);
            this.requestReasonLabel.Name = "requestReasonLabel";
            this.requestReasonLabel.Size = new System.Drawing.Size(0, 15);
            this.requestReasonLabel.TabIndex = 53;
            // 
            // balanceDueLabel
            // 
            this.balanceDueLabel.AutoSize = true;
            this.balanceDueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.balanceDueLabel.Location = new System.Drawing.Point(317, 81);
            this.balanceDueLabel.Name = "balanceDueLabel";
            this.balanceDueLabel.Size = new System.Drawing.Size(0, 15);
            this.balanceDueLabel.TabIndex = 52;
            // 
            // epnPanel
            // 
            this.epnPanel.Controls.Add(this.epnFlowLayoutPanel);
            this.epnPanel.Controls.Add(this.epnLabel);
            this.epnPanel.Location = new System.Drawing.Point(6, 177);
            this.epnPanel.Name = "epnPanel";
            this.epnPanel.Size = new System.Drawing.Size(310, 31);
            this.epnPanel.TabIndex = 8;
            // 
            // epnFlowLayoutPanel
            // 
            this.epnFlowLayoutPanel.Controls.Add(this.epnPrefixLabel);
            this.epnFlowLayoutPanel.Controls.Add(this.patientEpnTextBox);
            this.epnFlowLayoutPanel.Location = new System.Drawing.Point(102, 1);
            this.epnFlowLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.epnFlowLayoutPanel.Name = "epnFlowLayoutPanel";
            this.epnFlowLayoutPanel.Size = new System.Drawing.Size(192, 27);
            this.epnFlowLayoutPanel.TabIndex = 55;
            // 
            // epnPrefixLabel
            // 
            this.epnPrefixLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.epnPrefixLabel.AutoEllipsis = true;
            this.epnPrefixLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnPrefixLabel.Location = new System.Drawing.Point(3, 4);
            this.epnPrefixLabel.Name = "epnPrefixLabel";
            this.epnPrefixLabel.Size = new System.Drawing.Size(38, 19);
            this.epnPrefixLabel.TabIndex = 57;
            this.epnPrefixLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // patientEpnTextBox
            // 
            this.patientEpnTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientEpnTextBox.Location = new System.Drawing.Point(47, 3);
            this.patientEpnTextBox.Name = "patientEpnTextBox";
            this.patientEpnTextBox.Size = new System.Drawing.Size(142, 21);
            this.patientEpnTextBox.TabIndex = 8;
            // 
            // epnLabel
            // 
            this.epnLabel.AutoSize = true;
            this.epnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnLabel.Location = new System.Drawing.Point(0, 5);
            this.epnLabel.Name = "epnLabel";
            this.epnLabel.Size = new System.Drawing.Size(0, 15);
            this.epnLabel.TabIndex = 54;
            // 
            // mrnPanel
            // 
            this.mrnPanel.BackColor = System.Drawing.Color.Transparent;
            this.mrnPanel.Controls.Add(this.mrnLabel);
            this.mrnPanel.Controls.Add(this.patientMrnTextBox);
            this.mrnPanel.Location = new System.Drawing.Point(3, 102);
            this.mrnPanel.Name = "mrnPanel";
            this.mrnPanel.Size = new System.Drawing.Size(310, 25);
            this.mrnPanel.TabIndex = 5;
            // 
            // mrnLabel
            // 
            this.mrnLabel.AutoSize = true;
            this.mrnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnLabel.Location = new System.Drawing.Point(3, 2);
            this.mrnLabel.Name = "mrnLabel";
            this.mrnLabel.Size = new System.Drawing.Size(0, 15);
            this.mrnLabel.TabIndex = 30;
            // 
            // patientMrnTextBox
            // 
            this.patientMrnTextBox.AcceptsReturn = true;
            this.patientMrnTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientMrnTextBox.Location = new System.Drawing.Point(105, 0);
            this.patientMrnTextBox.Name = "patientMrnTextBox";
            this.patientMrnTextBox.Size = new System.Drawing.Size(189, 21);
            this.patientMrnTextBox.TabIndex = 5;
            // 
            // requestIdLabel
            // 
            this.requestIdLabel.AutoSize = true;
            this.requestIdLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestIdLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.requestIdLabel.Location = new System.Drawing.Point(317, 153);
            this.requestIdLabel.Name = "requestIdLabel";
            this.requestIdLabel.Size = new System.Drawing.Size(0, 15);
            this.requestIdLabel.TabIndex = 51;
            // 
            // invoiceNumberLabel
            // 
            this.invoiceNumberLabel.AutoSize = true;
            this.invoiceNumberLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.invoiceNumberLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.invoiceNumberLabel.Location = new System.Drawing.Point(317, 128);
            this.invoiceNumberLabel.Name = "invoiceNumberLabel";
            this.invoiceNumberLabel.Size = new System.Drawing.Size(0, 15);
            this.invoiceNumberLabel.TabIndex = 50;
            // 
            // toLabel
            // 
            this.toLabel.AutoSize = true;
            this.toLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.toLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.toLabel.Location = new System.Drawing.Point(673, 133);
            this.toLabel.Name = "toLabel";
            this.toLabel.Size = new System.Drawing.Size(0, 15);
            this.toLabel.TabIndex = 49;
            this.toLabel.Visible = false;
            // 
            // fromLabel
            // 
            this.fromLabel.AutoSize = true;
            this.fromLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fromLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.fromLabel.Location = new System.Drawing.Point(523, 131);
            this.fromLabel.Name = "fromLabel";
            this.fromLabel.Size = new System.Drawing.Size(0, 15);
            this.fromLabel.TabIndex = 48;
            this.fromLabel.Visible = false;
            // 
            // requestorTypeLabel
            // 
            this.requestorTypeLabel.AutoSize = true;
            this.requestorTypeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestorTypeLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.requestorTypeLabel.Location = new System.Drawing.Point(317, 31);
            this.requestorTypeLabel.Name = "requestorTypeLabel";
            this.requestorTypeLabel.Size = new System.Drawing.Size(0, 15);
            this.requestorTypeLabel.TabIndex = 46;
            // 
            // requestorNameLabel
            // 
            this.requestorNameLabel.AutoSize = true;
            this.requestorNameLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestorNameLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.requestorNameLabel.Location = new System.Drawing.Point(317, 6);
            this.requestorNameLabel.Name = "requestorNameLabel";
            this.requestorNameLabel.Size = new System.Drawing.Size(0, 15);
            this.requestorNameLabel.TabIndex = 45;
            // 
            // requestStatusLabel
            // 
            this.requestStatusLabel.AutoSize = true;
            this.requestStatusLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestStatusLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.requestStatusLabel.Location = new System.Drawing.Point(317, 55);
            this.requestStatusLabel.Name = "requestStatusLabel";
            this.requestStatusLabel.Size = new System.Drawing.Size(0, 15);
            this.requestStatusLabel.TabIndex = 44;
            this.requestStatusLabel.Tag = "";
            // 
            // requestIdTextBox
            // 
            this.requestIdTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestIdTextBox.Location = new System.Drawing.Point(411, 152);
            this.requestIdTextBox.MaxLength = 15;
            this.requestIdTextBox.Name = "requestIdTextBox";
            this.requestIdTextBox.Size = new System.Drawing.Size(329, 21);
            this.requestIdTextBox.TabIndex = 17;
            this.requestIdTextBox.TextChanged += new System.EventHandler(this.requestIdTextBox_TextChanged);
            // 
            // invoiceNumberTextBox
            // 
            this.invoiceNumberTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.invoiceNumberTextBox.Location = new System.Drawing.Point(411, 127);
            this.invoiceNumberTextBox.MaxLength = 15;
            this.invoiceNumberTextBox.Name = "invoiceNumberTextBox";
            this.invoiceNumberTextBox.Size = new System.Drawing.Size(329, 21);
            this.invoiceNumberTextBox.TabIndex = 16;
            // 
            // requestorTypeComboBox
            // 
            this.requestorTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.requestorTypeComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestorTypeComboBox.FormattingEnabled = true;
            this.requestorTypeComboBox.Location = new System.Drawing.Point(411, 26);
            this.requestorTypeComboBox.Name = "requestorTypeComboBox";
            this.requestorTypeComboBox.Size = new System.Drawing.Size(329, 23);
            this.requestorTypeComboBox.TabIndex = 10;
            // 
            // requestorNameTextBox
            // 
            this.requestorNameTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestorNameTextBox.Location = new System.Drawing.Point(411, 2);
            this.requestorNameTextBox.Name = "requestorNameTextBox";
            this.requestorNameTextBox.Size = new System.Drawing.Size(329, 21);
            this.requestorNameTextBox.TabIndex = 9;
            // 
            // requestStatusComboBox
            // 
            this.requestStatusComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.requestStatusComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requestStatusComboBox.FormattingEnabled = true;
            this.requestStatusComboBox.Location = new System.Drawing.Point(411, 50);
            this.requestStatusComboBox.Name = "requestStatusComboBox";
            this.requestStatusComboBox.Size = new System.Drawing.Size(120, 23);
            this.requestStatusComboBox.TabIndex = 11;
            // 
            // facilityComboBox
            // 
            this.facilityComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.facilityComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityComboBox.FormattingEnabled = true;
            this.facilityComboBox.Location = new System.Drawing.Point(108, 155);
            this.facilityComboBox.Name = "facilityComboBox";
            this.facilityComboBox.Size = new System.Drawing.Size(170, 23);
            this.facilityComboBox.TabIndex = 7;
            this.facilityComboBox.Click += new System.EventHandler(this.facilityComboBox_Click);
            // 
            // moreInfoImage2
            // 
            this.moreInfoImage2.Location = new System.Drawing.Point(282, 157);
            this.moreInfoImage2.Name = "moreInfoImage2";
            this.moreInfoImage2.Size = new System.Drawing.Size(21, 18);
            this.moreInfoImage2.TabIndex = 33;
            this.moreInfoImage2.TabStop = false;
            // 
            // facilityLabel
            // 
            this.facilityLabel.AutoSize = true;
            this.facilityLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(27)))), ((int)(((byte)(81)))), ((int)(((byte)(156)))));
            this.facilityLabel.Location = new System.Drawing.Point(5, 158);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(0, 15);
            this.facilityLabel.TabIndex = 31;
            // 
            // encounterLabel
            // 
            this.encounterLabel.AutoSize = true;
            this.encounterLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.encounterLabel.Location = new System.Drawing.Point(5, 136);
            this.encounterLabel.Name = "encounterLabel";
            this.encounterLabel.Size = new System.Drawing.Size(0, 15);
            this.encounterLabel.TabIndex = 29;
            // 
            // patientSsnLabel
            // 
            this.patientSsnLabel.AutoSize = true;
            this.patientSsnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientSsnLabel.Location = new System.Drawing.Point(5, 82);
            this.patientSsnLabel.Name = "patientSsnLabel";
            this.patientSsnLabel.Size = new System.Drawing.Size(0, 15);
            this.patientSsnLabel.TabIndex = 27;
            // 
            // patientDobLabel
            // 
            this.patientDobLabel.AutoSize = true;
            this.patientDobLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientDobLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(27)))), ((int)(((byte)(81)))), ((int)(((byte)(156)))));
            this.patientDobLabel.Location = new System.Drawing.Point(5, 56);
            this.patientDobLabel.Name = "patientDobLabel";
            this.patientDobLabel.Size = new System.Drawing.Size(0, 15);
            this.patientDobLabel.TabIndex = 26;
            // 
            // patientFirstNameLabel
            // 
            this.patientFirstNameLabel.AutoSize = true;
            this.patientFirstNameLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientFirstNameLabel.Location = new System.Drawing.Point(5, 31);
            this.patientFirstNameLabel.Name = "patientFirstNameLabel";
            this.patientFirstNameLabel.Size = new System.Drawing.Size(0, 15);
            this.patientFirstNameLabel.TabIndex = 25;
            // 
            // patientEncounterTextBox
            // 
            this.patientEncounterTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientEncounterTextBox.Location = new System.Drawing.Point(108, 130);
            this.patientEncounterTextBox.Name = "patientEncounterTextBox";
            this.patientEncounterTextBox.Size = new System.Drawing.Size(189, 21);
            this.patientEncounterTextBox.TabIndex = 6;
            // 
            // moreInfoImage1
            // 
            this.moreInfoImage1.Location = new System.Drawing.Point(284, 54);
            this.moreInfoImage1.Name = "moreInfoImage1";
            this.moreInfoImage1.Size = new System.Drawing.Size(19, 18);
            this.moreInfoImage1.TabIndex = 19;
            this.moreInfoImage1.TabStop = false;
            // 
            // patientFirstNameTextBox
            // 
            this.patientFirstNameTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientFirstNameTextBox.Location = new System.Drawing.Point(108, 27);
            this.patientFirstNameTextBox.Name = "patientFirstNameTextBox";
            this.patientFirstNameTextBox.Size = new System.Drawing.Size(189, 21);
            this.patientFirstNameTextBox.TabIndex = 2;
            // 
            // patientLastNameTextBox
            // 
            this.patientLastNameTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientLastNameTextBox.Location = new System.Drawing.Point(108, 3);
            this.patientLastNameTextBox.Name = "patientLastNameTextBox";
            this.patientLastNameTextBox.Size = new System.Drawing.Size(189, 21);
            this.patientLastNameTextBox.TabIndex = 1;
            // 
            // patientLastNameLabel
            // 
            this.patientLastNameLabel.AutoSize = true;
            this.patientLastNameLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientLastNameLabel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.patientLastNameLabel.Location = new System.Drawing.Point(5, 6);
            this.patientLastNameLabel.Name = "patientLastNameLabel";
            this.patientLastNameLabel.Size = new System.Drawing.Size(0, 15);
            this.patientLastNameLabel.TabIndex = 0;
            // 
            // controlPanel
            // 
            this.controlPanel.ColumnCount = 3;
            this.controlPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.controlPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.controlPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 345F));
            this.controlPanel.Controls.Add(this.resetRequestButton, 2, 0);
            this.controlPanel.Controls.Add(this.newRequestButton, 1, 0);
            this.controlPanel.Controls.Add(this.findRequestButton, 0, 0);
            this.controlPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.controlPanel.Location = new System.Drawing.Point(3, 264);
            this.controlPanel.Name = "controlPanel";
            this.controlPanel.RowCount = 1;
            this.controlPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.controlPanel.Size = new System.Drawing.Size(876, 33);
            this.controlPanel.TabIndex = 2;
            // 
            // resetRequestButton
            // 
            this.resetRequestButton.AutoSize = true;
            this.resetRequestButton.Location = new System.Drawing.Point(534, 3);
            this.resetRequestButton.Name = "resetRequestButton";
            this.resetRequestButton.Size = new System.Drawing.Size(75, 23);
            this.resetRequestButton.TabIndex = 20;
            this.resetRequestButton.UseVisualStyleBackColor = true;
            this.resetRequestButton.Click += new System.EventHandler(this.resetButton_Click);
            // 
            // newRequestButton
            // 
            this.newRequestButton.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.newRequestButton.AutoSize = true;
            this.newRequestButton.Location = new System.Drawing.Point(449, 3);
            this.newRequestButton.Name = "newRequestButton";
            this.newRequestButton.Size = new System.Drawing.Size(79, 23);
            this.newRequestButton.TabIndex = 19;
            this.newRequestButton.UseVisualStyleBackColor = true;
            this.newRequestButton.Click += new System.EventHandler(this.newRequestButton_Click);
            // 
            // findRequestButton
            // 
            this.findRequestButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.findRequestButton.AutoSize = true;
            this.findRequestButton.Enabled = false;
            this.findRequestButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.findRequestButton.Location = new System.Drawing.Point(361, 3);
            this.findRequestButton.Name = "findRequestButton";
            this.findRequestButton.Size = new System.Drawing.Size(82, 23);
            this.findRequestButton.TabIndex = 18;
            this.findRequestButton.UseVisualStyleBackColor = true;
            this.findRequestButton.Click += new System.EventHandler(this.FindRequestButtonClick);
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // RequestSearchUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.DimGray;
            this.Controls.Add(this.reqeustTableLayoutPanel);
            this.Name = "RequestSearchUI";
            this.Size = new System.Drawing.Size(882, 300);
            this.reqeustTableLayoutPanel.ResumeLayout(false);
            this.reqeustTableLayoutPanel.PerformLayout();
            this.searchCriteriaPanel.ResumeLayout(false);
            this.searchCriteriaPanel.PerformLayout();
            this.epnPanel.ResumeLayout(false);
            this.epnPanel.PerformLayout();
            this.epnFlowLayoutPanel.ResumeLayout(false);
            this.epnFlowLayoutPanel.PerformLayout();
            this.mrnPanel.ResumeLayout(false);
            this.mrnPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.moreInfoImage2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.moreInfoImage1)).EndInit();
            this.controlPanel.ResumeLayout(false);
            this.controlPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel reqeustTableLayoutPanel;
        private System.Windows.Forms.Panel searchCriteriaPanel;
        private System.Windows.Forms.Label patientLastNameLabel;
        private System.Windows.Forms.TextBox patientFirstNameTextBox;
        private System.Windows.Forms.TextBox patientLastNameTextBox;
        private System.Windows.Forms.PictureBox moreInfoImage1;
        private System.Windows.Forms.TextBox patientEncounterTextBox;
        private System.Windows.Forms.Label patientDobLabel;
        private System.Windows.Forms.Label patientFirstNameLabel;
        private System.Windows.Forms.Label patientSsnLabel;
        private System.Windows.Forms.Label encounterLabel;
        private System.Windows.Forms.Label facilityLabel;
        private System.Windows.Forms.PictureBox moreInfoImage2;
        private System.Windows.Forms.ComboBox requestStatusComboBox;
        private System.Windows.Forms.ComboBox facilityComboBox;
        private System.Windows.Forms.TextBox requestorNameTextBox;
        private System.Windows.Forms.ComboBox requestorTypeComboBox;
        private System.Windows.Forms.TextBox invoiceNumberTextBox;
        private System.Windows.Forms.Label requestorNameLabel;
        private System.Windows.Forms.Label requestStatusLabel;
        private System.Windows.Forms.TextBox requestIdTextBox;
        private System.Windows.Forms.Label receiptDateLabel;
        private System.Windows.Forms.Label requestorTypeLabel;
        private System.Windows.Forms.Label toLabel;
        private System.Windows.Forms.Label requestIdLabel;
        private System.Windows.Forms.Label invoiceNumberLabel;
        private System.Windows.Forms.Button resetRequestButton;
        private System.Windows.Forms.Button findRequestButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Label fromLabel;
        private System.Windows.Forms.TableLayoutPanel controlPanel;
        private System.Windows.Forms.Button newRequestButton;
        private System.Windows.Forms.Panel mrnPanel;
        private System.Windows.Forms.Label mrnLabel;
        private System.Windows.Forms.TextBox patientMrnTextBox;
        private System.Windows.Forms.Panel epnPanel;
        private System.Windows.Forms.Label epnLabel;
        private System.Windows.Forms.FlowLayoutPanel epnFlowLayoutPanel;
        private System.Windows.Forms.Label epnPrefixLabel;
        private System.Windows.Forms.TextBox patientEpnTextBox;
        private System.Windows.Forms.ComboBox balanceDueComboBox;
        private System.Windows.Forms.ComboBox requestReasonComboBox;
        private System.Windows.Forms.Label requestReasonLabel;
        private System.Windows.Forms.Label balanceDueLabel;
        private System.Windows.Forms.TextBox balanceDueTextBox;
        private System.Windows.Forms.Label dollarLabel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.MaskedTextBox patientSsnTextBox;
        private System.Windows.Forms.Label completeDateLabel;
        private McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl dobTextBox;
        private McK.EIG.ROI.Client.Base.View.CustomDateRange customDateRange;
        private McK.EIG.ROI.Client.Base.View.CustomDateRange completeDateRange;
    }
}
