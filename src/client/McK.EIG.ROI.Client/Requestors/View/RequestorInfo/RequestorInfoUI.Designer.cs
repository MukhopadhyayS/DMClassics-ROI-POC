namespace McK.EIG.ROI.Client.Requestors.View.RequestorInfo
{
    partial class RequestorInfoUI
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
            this.infoPanel = new System.Windows.Forms.Panel();
            this.nonUniqueRequestorIcon = new System.Windows.Forms.PictureBox();
            this.patientReqPanel = new System.Windows.Forms.Panel();
            this.pictureBox4 = new System.Windows.Forms.PictureBox();
            this.reqFirstNameTextBox = new System.Windows.Forms.TextBox();
            this.reqFirstNameLabel = new System.Windows.Forms.Label();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.reqLastNameTextBox = new System.Windows.Forms.TextBox();
            this.reqLastNameLabel = new System.Windows.Forms.Label();
            this.nonPatientReqPanel = new System.Windows.Forms.Panel();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.nameTextBox = new System.Windows.Forms.TextBox();
            this.reqNameLabel = new System.Windows.Forms.Label();
            this.epnPanel = new System.Windows.Forms.Panel();
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.prefixLabel = new System.Windows.Forms.Label();
            this.epnTextBox = new System.Windows.Forms.TextBox();
            this.patientEpnLabel = new System.Windows.Forms.Label();
            this.statusCheckBox = new System.Windows.Forms.CheckBox();
            this.contactInfoGroupBox = new System.Windows.Forms.GroupBox();
            this.contactEmailTextBox = new System.Windows.Forms.TextBox();
            this.contactPhoneTextBox = new System.Windows.Forms.TextBox();
            this.contactNameTextBox = new System.Windows.Forms.TextBox();
            this.faxTextBox = new System.Windows.Forms.TextBox();
            this.emailTextBox = new System.Windows.Forms.TextBox();
            this.cellPhoneTextBox = new System.Windows.Forms.TextBox();
            this.workPhoneTextBox = new System.Windows.Forms.TextBox();
            this.homePhoneTextBox = new System.Windows.Forms.TextBox();
            this.contactPhoneLabel = new System.Windows.Forms.Label();
            this.contactEmailLabel = new System.Windows.Forms.Label();
            this.contactNameLabel = new System.Windows.Forms.Label();
            this.faxLabel = new System.Windows.Forms.Label();
            this.emailLabel = new System.Windows.Forms.Label();
            this.cellPhoneLabel = new System.Windows.Forms.Label();
            this.requestorWorkPhoneLabel = new System.Windows.Forms.Label();
            this.requestorHomePhoneLabel = new System.Windows.Forms.Label();
            this.alternateAddressGroupUI = new McK.EIG.ROI.Client.Requestors.View.RequestorInfo.RequestorAddressGroupUI();
            this.mainAddressGroupUI = new McK.EIG.ROI.Client.Requestors.View.RequestorInfo.RequestorAddressGroupUI();
            this.patientPanel = new System.Windows.Forms.Panel();
            this.ssnTextBox = new System.Windows.Forms.MaskedTextBox();
            this.facilityComboBox = new System.Windows.Forms.ComboBox();
            this.mrnTextBox = new System.Windows.Forms.TextBox();
            this.patientMrnLabel = new System.Windows.Forms.Label();
            this.patientFacilityLabel = new System.Windows.Forms.Label();
            this.patientSsnLabel = new System.Windows.Forms.Label();
            this.patientDobLabel = new System.Windows.Forms.Label();
            this.requestorTypeCombo = new System.Windows.Forms.ComboBox();
            this.typeLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.letterRequiredCheckBox = new System.Windows.Forms.CheckBox();
            this.paymentRequiredCheckBox = new System.Windows.Forms.CheckBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.dobTextBox = new McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl();
            this.infoPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nonUniqueRequestorIcon)).BeginInit();
            this.patientReqPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox4)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            this.nonPatientReqPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.epnPanel.SuspendLayout();
            this.flowLayoutPanel1.SuspendLayout();
            this.contactInfoGroupBox.SuspendLayout();
            this.patientPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // infoPanel
            // 
            this.infoPanel.Controls.Add(this.nonUniqueRequestorIcon);
            this.infoPanel.Controls.Add(this.patientReqPanel);
            this.infoPanel.Controls.Add(this.nonPatientReqPanel);
            this.infoPanel.Controls.Add(this.epnPanel);
            this.infoPanel.Controls.Add(this.statusCheckBox);
            this.infoPanel.Controls.Add(this.contactInfoGroupBox);
            this.infoPanel.Controls.Add(this.alternateAddressGroupUI);
            this.infoPanel.Controls.Add(this.mainAddressGroupUI);
            this.infoPanel.Controls.Add(this.patientPanel);
            this.infoPanel.Controls.Add(this.requestorTypeCombo);
            this.infoPanel.Controls.Add(this.typeLabel);
            this.infoPanel.Controls.Add(this.requiredImg);
            this.infoPanel.Controls.Add(this.letterRequiredCheckBox);
            this.infoPanel.Controls.Add(this.paymentRequiredCheckBox);
            this.infoPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.infoPanel.Location = new System.Drawing.Point(0, 0);
            this.infoPanel.Name = "infoPanel";
            this.infoPanel.Size = new System.Drawing.Size(728, 514);
            this.infoPanel.TabIndex = 0;
            // 
            // nonUniqueRequestorIcon
            // 
            this.nonUniqueRequestorIcon.Image = global::McK.EIG.ROI.Client.Resources.images.non_unique_patient;
            this.nonUniqueRequestorIcon.Location = new System.Drawing.Point(245, 66);
            this.nonUniqueRequestorIcon.Name = "nonUniqueRequestorIcon";
            this.nonUniqueRequestorIcon.Size = new System.Drawing.Size(20, 21);
            this.nonUniqueRequestorIcon.TabIndex = 18;
            this.nonUniqueRequestorIcon.TabStop = false;
            this.nonUniqueRequestorIcon.Visible = false;
            // 
            // patientReqPanel
            // 
            this.patientReqPanel.Controls.Add(this.pictureBox4);
            this.patientReqPanel.Controls.Add(this.reqFirstNameTextBox);
            this.patientReqPanel.Controls.Add(this.reqFirstNameLabel);
            this.patientReqPanel.Controls.Add(this.pictureBox2);
            this.patientReqPanel.Controls.Add(this.reqLastNameTextBox);
            this.patientReqPanel.Controls.Add(this.reqLastNameLabel);
            this.patientReqPanel.Location = new System.Drawing.Point(3, 63);
            this.patientReqPanel.Name = "patientReqPanel";
            this.patientReqPanel.Size = new System.Drawing.Size(328, 65);
            this.patientReqPanel.TabIndex = 1;
            // 
            // pictureBox4
            // 
            this.pictureBox4.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox4.Location = new System.Drawing.Point(6, 35);
            this.pictureBox4.Name = "pictureBox4";
            this.pictureBox4.Size = new System.Drawing.Size(10, 11);
            this.pictureBox4.TabIndex = 27;
            this.pictureBox4.TabStop = false;
            // 
            // reqFirstNameTextBox
            // 
            this.reqFirstNameTextBox.Font = new System.Drawing.Font("Arial", 9F);
            this.reqFirstNameTextBox.Location = new System.Drawing.Point(86, 29);
            this.reqFirstNameTextBox.MaxLength = 256;
            this.reqFirstNameTextBox.Name = "reqFirstNameTextBox";
            this.reqFirstNameTextBox.Size = new System.Drawing.Size(150, 21);
            this.reqFirstNameTextBox.TabIndex = 24;
            // 
            // reqFirstNameLabel
            // 
            this.reqFirstNameLabel.AutoSize = true;
            this.reqFirstNameLabel.Font = new System.Drawing.Font("Arial", 9F);
            this.reqFirstNameLabel.Location = new System.Drawing.Point(18, 32);
            this.reqFirstNameLabel.Name = "reqFirstNameLabel";
            this.reqFirstNameLabel.Size = new System.Drawing.Size(0, 15);
            this.reqFirstNameLabel.TabIndex = 25;
            // 
            // pictureBox2
            // 
            this.pictureBox2.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox2.Location = new System.Drawing.Point(6, 8);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(10, 11);
            this.pictureBox2.TabIndex = 19;
            this.pictureBox2.TabStop = false;
            // 
            // reqLastNameTextBox
            // 
            this.reqLastNameTextBox.Font = new System.Drawing.Font("Arial", 9F);
            this.reqLastNameTextBox.Location = new System.Drawing.Point(86, 2);
            this.reqLastNameTextBox.MaxLength = 256;
            this.reqLastNameTextBox.Name = "reqLastNameTextBox";
            this.reqLastNameTextBox.Size = new System.Drawing.Size(150, 21);
            this.reqLastNameTextBox.TabIndex = 3;
            // 
            // reqLastNameLabel
            // 
            this.reqLastNameLabel.AutoSize = true;
            this.reqLastNameLabel.Font = new System.Drawing.Font("Arial", 9F);
            this.reqLastNameLabel.Location = new System.Drawing.Point(18, 5);
            this.reqLastNameLabel.Name = "reqLastNameLabel";
            this.reqLastNameLabel.Size = new System.Drawing.Size(0, 15);
            this.reqLastNameLabel.TabIndex = 17;
            // 
            // nonPatientReqPanel
            // 
            this.nonPatientReqPanel.Controls.Add(this.pictureBox1);
            this.nonPatientReqPanel.Controls.Add(this.nameTextBox);
            this.nonPatientReqPanel.Controls.Add(this.reqNameLabel);
            this.nonPatientReqPanel.Location = new System.Drawing.Point(3, 67);
            this.nonPatientReqPanel.Name = "nonPatientReqPanel";
            this.nonPatientReqPanel.Size = new System.Drawing.Size(257, 33);
            this.nonPatientReqPanel.TabIndex = 1;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox1.Location = new System.Drawing.Point(6, 8);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(10, 11);
            this.pictureBox1.TabIndex = 19;
            this.pictureBox1.TabStop = false;
            // 
            // nameTextBox
            // 
            this.nameTextBox.Font = new System.Drawing.Font("Arial", 9F);
            this.nameTextBox.Location = new System.Drawing.Point(86, 2);
            this.nameTextBox.MaxLength = 256;
            this.nameTextBox.Name = "nameTextBox";
            this.nameTextBox.Size = new System.Drawing.Size(150, 21);
            this.nameTextBox.TabIndex = 1;
            // 
            // reqNameLabel
            // 
            this.reqNameLabel.AutoSize = true;
            this.reqNameLabel.Font = new System.Drawing.Font("Arial", 9F);
            this.reqNameLabel.Location = new System.Drawing.Point(18, 5);
            this.reqNameLabel.Name = "reqNameLabel";
            this.reqNameLabel.Size = new System.Drawing.Size(0, 15);
            this.reqNameLabel.TabIndex = 17;
            // 
            // epnPanel
            // 
            this.epnPanel.Controls.Add(this.flowLayoutPanel1);
            this.epnPanel.Controls.Add(this.patientEpnLabel);
            this.epnPanel.Location = new System.Drawing.Point(361, 2);
            this.epnPanel.Name = "epnPanel";
            this.epnPanel.Size = new System.Drawing.Size(355, 36);
            this.epnPanel.TabIndex = 3;
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Controls.Add(this.prefixLabel);
            this.flowLayoutPanel1.Controls.Add(this.epnTextBox);
            this.flowLayoutPanel1.Location = new System.Drawing.Point(92, 5);
            this.flowLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(240, 26);
            this.flowLayoutPanel1.TabIndex = 3;
            // 
            // prefixLabel
            // 
            this.prefixLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.prefixLabel.AutoEllipsis = true;
            this.prefixLabel.Location = new System.Drawing.Point(3, 3);
            this.prefixLabel.Name = "prefixLabel";
            this.prefixLabel.Size = new System.Drawing.Size(42, 20);
            this.prefixLabel.TabIndex = 17;
            // 
            // epnTextBox
            // 
            this.epnTextBox.Location = new System.Drawing.Point(51, 3);
            this.epnTextBox.MaxLength = 253;
            this.epnTextBox.Name = "epnTextBox";
            this.epnTextBox.Size = new System.Drawing.Size(97, 21);
            this.epnTextBox.TabIndex = 4;
            this.epnTextBox.WordWrap = false;
            // 
            // patientEpnLabel
            // 
            this.patientEpnLabel.AutoSize = true;
            this.patientEpnLabel.Location = new System.Drawing.Point(5, 10);
            this.patientEpnLabel.Name = "patientEpnLabel";
            this.patientEpnLabel.Size = new System.Drawing.Size(0, 15);
            this.patientEpnLabel.TabIndex = 15;
            // 
            // statusCheckBox
            // 
            this.statusCheckBox.AutoSize = true;
            this.statusCheckBox.Location = new System.Drawing.Point(257, 40);
            this.statusCheckBox.Name = "statusCheckBox";
            this.statusCheckBox.Size = new System.Drawing.Size(15, 14);
            this.statusCheckBox.TabIndex = 2;
            this.statusCheckBox.UseVisualStyleBackColor = true;
            // 
            // contactInfoGroupBox
            // 
            this.contactInfoGroupBox.Controls.Add(this.contactEmailTextBox);
            this.contactInfoGroupBox.Controls.Add(this.contactPhoneTextBox);
            this.contactInfoGroupBox.Controls.Add(this.contactNameTextBox);
            this.contactInfoGroupBox.Controls.Add(this.faxTextBox);
            this.contactInfoGroupBox.Controls.Add(this.emailTextBox);
            this.contactInfoGroupBox.Controls.Add(this.cellPhoneTextBox);
            this.contactInfoGroupBox.Controls.Add(this.workPhoneTextBox);
            this.contactInfoGroupBox.Controls.Add(this.homePhoneTextBox);
            this.contactInfoGroupBox.Controls.Add(this.contactPhoneLabel);
            this.contactInfoGroupBox.Controls.Add(this.contactEmailLabel);
            this.contactInfoGroupBox.Controls.Add(this.contactNameLabel);
            this.contactInfoGroupBox.Controls.Add(this.faxLabel);
            this.contactInfoGroupBox.Controls.Add(this.emailLabel);
            this.contactInfoGroupBox.Controls.Add(this.cellPhoneLabel);
            this.contactInfoGroupBox.Controls.Add(this.requestorWorkPhoneLabel);
            this.contactInfoGroupBox.Controls.Add(this.requestorHomePhoneLabel);
            this.contactInfoGroupBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.contactInfoGroupBox.Location = new System.Drawing.Point(7, 336);
            this.contactInfoGroupBox.Name = "contactInfoGroupBox";
            this.contactInfoGroupBox.Size = new System.Drawing.Size(686, 164);
            this.contactInfoGroupBox.TabIndex = 12;
            this.contactInfoGroupBox.TabStop = false;
            // 
            // contactEmailTextBox
            // 
            this.contactEmailTextBox.Location = new System.Drawing.Point(458, 72);
            this.contactEmailTextBox.Name = "contactEmailTextBox";
            this.contactEmailTextBox.Size = new System.Drawing.Size(150, 21);
            this.contactEmailTextBox.TabIndex = 18;
            // 
            // contactPhoneTextBox
            // 
            this.contactPhoneTextBox.Location = new System.Drawing.Point(458, 46);
            this.contactPhoneTextBox.MaxLength = 256;
            this.contactPhoneTextBox.Name = "contactPhoneTextBox";
            this.contactPhoneTextBox.Size = new System.Drawing.Size(150, 21);
            this.contactPhoneTextBox.TabIndex = 17;
            // 
            // contactNameTextBox
            // 
            this.contactNameTextBox.Location = new System.Drawing.Point(458, 20);
            this.contactNameTextBox.MaxLength = 256;
            this.contactNameTextBox.Name = "contactNameTextBox";
            this.contactNameTextBox.Size = new System.Drawing.Size(150, 21);
            this.contactNameTextBox.TabIndex = 16;
            // 
            // faxTextBox
            // 
            this.faxTextBox.Location = new System.Drawing.Point(150, 129);
            this.faxTextBox.MaxLength = 20;
            this.faxTextBox.Name = "faxTextBox";
            this.faxTextBox.Size = new System.Drawing.Size(150, 21);
            this.faxTextBox.TabIndex = 15;
            // 
            // emailTextBox
            // 
            this.emailTextBox.Location = new System.Drawing.Point(150, 102);
            this.emailTextBox.Name = "emailTextBox";
            this.emailTextBox.Size = new System.Drawing.Size(150, 21);
            this.emailTextBox.TabIndex = 14;
            // 
            // cellPhoneTextBox
            // 
            this.cellPhoneTextBox.Location = new System.Drawing.Point(150, 76);
            this.cellPhoneTextBox.MaxLength = 20;
            this.cellPhoneTextBox.Name = "cellPhoneTextBox";
            this.cellPhoneTextBox.Size = new System.Drawing.Size(150, 21);
            this.cellPhoneTextBox.TabIndex = 13;
            // 
            // workPhoneTextBox
            // 
            this.workPhoneTextBox.Location = new System.Drawing.Point(150, 49);
            this.workPhoneTextBox.MaxLength = 256;
            this.workPhoneTextBox.Name = "workPhoneTextBox";
            this.workPhoneTextBox.Size = new System.Drawing.Size(150, 21);
            this.workPhoneTextBox.TabIndex = 12;
            // 
            // homePhoneTextBox
            // 
            this.homePhoneTextBox.Location = new System.Drawing.Point(150, 22);
            this.homePhoneTextBox.MaxLength = 256;
            this.homePhoneTextBox.Name = "homePhoneTextBox";
            this.homePhoneTextBox.Size = new System.Drawing.Size(150, 21);
            this.homePhoneTextBox.TabIndex = 11;
            // 
            // contactPhoneLabel
            // 
            this.contactPhoneLabel.AutoSize = true;
            this.contactPhoneLabel.Location = new System.Drawing.Point(366, 51);
            this.contactPhoneLabel.Name = "contactPhoneLabel";
            this.contactPhoneLabel.Size = new System.Drawing.Size(0, 15);
            this.contactPhoneLabel.TabIndex = 7;
            // 
            // contactEmailLabel
            // 
            this.contactEmailLabel.AutoSize = true;
            this.contactEmailLabel.Location = new System.Drawing.Point(366, 76);
            this.contactEmailLabel.Name = "contactEmailLabel";
            this.contactEmailLabel.Size = new System.Drawing.Size(0, 15);
            this.contactEmailLabel.TabIndex = 6;
            // 
            // contactNameLabel
            // 
            this.contactNameLabel.AutoSize = true;
            this.contactNameLabel.Location = new System.Drawing.Point(367, 25);
            this.contactNameLabel.Name = "contactNameLabel";
            this.contactNameLabel.Size = new System.Drawing.Size(0, 15);
            this.contactNameLabel.TabIndex = 5;
            // 
            // faxLabel
            // 
            this.faxLabel.AutoSize = true;
            this.faxLabel.Location = new System.Drawing.Point(7, 132);
            this.faxLabel.Name = "faxLabel";
            this.faxLabel.Size = new System.Drawing.Size(0, 15);
            this.faxLabel.TabIndex = 4;
            // 
            // emailLabel
            // 
            this.emailLabel.AutoSize = true;
            this.emailLabel.Location = new System.Drawing.Point(8, 108);
            this.emailLabel.Name = "emailLabel";
            this.emailLabel.Size = new System.Drawing.Size(0, 15);
            this.emailLabel.TabIndex = 3;
            // 
            // cellPhoneLabel
            // 
            this.cellPhoneLabel.AutoSize = true;
            this.cellPhoneLabel.Location = new System.Drawing.Point(8, 82);
            this.cellPhoneLabel.Name = "cellPhoneLabel";
            this.cellPhoneLabel.Size = new System.Drawing.Size(0, 15);
            this.cellPhoneLabel.TabIndex = 2;
            // 
            // requestorWorkPhoneLabel
            // 
            this.requestorWorkPhoneLabel.AutoSize = true;
            this.requestorWorkPhoneLabel.Location = new System.Drawing.Point(8, 54);
            this.requestorWorkPhoneLabel.Name = "requestorWorkPhoneLabel";
            this.requestorWorkPhoneLabel.Size = new System.Drawing.Size(0, 15);
            this.requestorWorkPhoneLabel.TabIndex = 1;
            // 
            // requestorHomePhoneLabel
            // 
            this.requestorHomePhoneLabel.AutoSize = true;
            this.requestorHomePhoneLabel.Location = new System.Drawing.Point(8, 24);
            this.requestorHomePhoneLabel.Name = "requestorHomePhoneLabel";
            this.requestorHomePhoneLabel.Size = new System.Drawing.Size(0, 15);
            this.requestorHomePhoneLabel.TabIndex = 0;
            // 
            // alternateAddressGroupUI
            // 
            this.alternateAddressGroupUI.IsDefaultSel = false;
            this.alternateAddressGroupUI.IsDirty = false;
            this.alternateAddressGroupUI.Location = new System.Drawing.Point(363, 138);
            this.alternateAddressGroupUI.Name = "alternateAddressGroupUI";
            this.alternateAddressGroupUI.Size = new System.Drawing.Size(353, 205);
            this.alternateAddressGroupUI.TabIndex = 10;
            this.alternateAddressGroupUI.Type = McK.EIG.ROI.Client.Requestors.Model.AddressType.AlternateAddress;
            // 
            // mainAddressGroupUI
            // 
            this.mainAddressGroupUI.IsDefaultSel = false;
            this.mainAddressGroupUI.IsDirty = false;
            this.mainAddressGroupUI.Location = new System.Drawing.Point(7, 138);
            this.mainAddressGroupUI.Name = "mainAddressGroupUI";
            this.mainAddressGroupUI.Size = new System.Drawing.Size(348, 205);
            this.mainAddressGroupUI.TabIndex = 9;
            this.mainAddressGroupUI.Type = McK.EIG.ROI.Client.Requestors.Model.AddressType.MainAddress;
            // 
            // patientPanel
            // 
            this.patientPanel.Controls.Add(this.dobTextBox);
            this.patientPanel.Controls.Add(this.ssnTextBox);
            this.patientPanel.Controls.Add(this.facilityComboBox);
            this.patientPanel.Controls.Add(this.mrnTextBox);
            this.patientPanel.Controls.Add(this.patientMrnLabel);
            this.patientPanel.Controls.Add(this.patientFacilityLabel);
            this.patientPanel.Controls.Add(this.patientSsnLabel);
            this.patientPanel.Controls.Add(this.patientDobLabel);
            this.patientPanel.Enabled = false;
            this.patientPanel.Location = new System.Drawing.Point(361, 35);
            this.patientPanel.Name = "patientPanel";
            this.patientPanel.Size = new System.Drawing.Size(344, 112);
            this.patientPanel.TabIndex = 4;
            // 
            // dobTextBox
            // 
            this.dobTextBox.FormattedDate = null;
            this.dobTextBox.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
            this.dobTextBox.IsValidDate = false;
            this.dobTextBox.Location = new System.Drawing.Point(92, 4);
            this.dobTextBox.Mask = "AA/AA/AAAA";
            this.dobTextBox.Name = "dobTextBox";
            this.dobTextBox.PromptChar = ' ';
            this.dobTextBox.Size = new System.Drawing.Size(148, 21);
            this.dobTextBox.TabIndex = 5;
            // 
            // ssnTextBox
            // 
            this.ssnTextBox.Location = new System.Drawing.Point(92, 31);
            this.ssnTextBox.Name = "ssnTextBox";
            this.ssnTextBox.Size = new System.Drawing.Size(150, 21);
            this.ssnTextBox.TabIndex = 6;
            // 
            // facilityComboBox
            // 
            this.facilityComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.facilityComboBox.Font = new System.Drawing.Font("Arial", 9F);
            this.facilityComboBox.FormattingEnabled = true;
            this.facilityComboBox.Location = new System.Drawing.Point(92, 55);
            this.facilityComboBox.Name = "facilityComboBox";
            this.facilityComboBox.Size = new System.Drawing.Size(150, 23);
            this.facilityComboBox.TabIndex = 7;
            this.facilityComboBox.Click += new System.EventHandler(this.facilityComboBox_Click);
            // 
            // mrnTextBox
            // 
            this.mrnTextBox.Location = new System.Drawing.Point(92, 82);
            this.mrnTextBox.MaxLength = 256;
            this.mrnTextBox.Name = "mrnTextBox";
            this.mrnTextBox.Size = new System.Drawing.Size(150, 21);
            this.mrnTextBox.TabIndex = 8;
            // 
            // patientMrnLabel
            // 
            this.patientMrnLabel.AutoSize = true;
            this.patientMrnLabel.Location = new System.Drawing.Point(5, 84);
            this.patientMrnLabel.Name = "patientMrnLabel";
            this.patientMrnLabel.Size = new System.Drawing.Size(0, 15);
            this.patientMrnLabel.TabIndex = 22;
            // 
            // patientFacilityLabel
            // 
            this.patientFacilityLabel.AutoSize = true;
            this.patientFacilityLabel.Location = new System.Drawing.Point(5, 58);
            this.patientFacilityLabel.Name = "patientFacilityLabel";
            this.patientFacilityLabel.Size = new System.Drawing.Size(0, 15);
            this.patientFacilityLabel.TabIndex = 20;
            // 
            // patientSsnLabel
            // 
            this.patientSsnLabel.AutoSize = true;
            this.patientSsnLabel.Location = new System.Drawing.Point(5, 33);
            this.patientSsnLabel.Name = "patientSsnLabel";
            this.patientSsnLabel.Size = new System.Drawing.Size(0, 15);
            this.patientSsnLabel.TabIndex = 10;
            // 
            // patientDobLabel
            // 
            this.patientDobLabel.AutoSize = true;
            this.patientDobLabel.Location = new System.Drawing.Point(5, 7);
            this.patientDobLabel.Name = "patientDobLabel";
            this.patientDobLabel.Size = new System.Drawing.Size(0, 15);
            this.patientDobLabel.TabIndex = 9;
            // 
            // requestorTypeCombo
            // 
            this.requestorTypeCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.requestorTypeCombo.Font = new System.Drawing.Font("Arial", 9F);
            this.requestorTypeCombo.FormattingEnabled = true;
            this.requestorTypeCombo.Location = new System.Drawing.Point(89, 38);
            this.requestorTypeCombo.Name = "requestorTypeCombo";
            this.requestorTypeCombo.Size = new System.Drawing.Size(150, 23);
            this.requestorTypeCombo.TabIndex = 0;
            this.requestorTypeCombo.SelectedIndexChanged += new System.EventHandler(this.requestorTypeCombo_SelectedIndexChanged);
            // 
            // typeLabel
            // 
            this.typeLabel.AutoSize = true;
            this.typeLabel.Font = new System.Drawing.Font("Arial", 9F);
            this.typeLabel.Location = new System.Drawing.Point(21, 43);
            this.typeLabel.Name = "typeLabel";
            this.typeLabel.Size = new System.Drawing.Size(0, 15);
            this.typeLabel.TabIndex = 3;
            // 
            // requiredImg
            // 
            this.requiredImg.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.Location = new System.Drawing.Point(9, 46);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(10, 11);
            this.requiredImg.TabIndex = 2;
            this.requiredImg.TabStop = false;
            // 
            // letterRequiredCheckBox
            // 
            this.letterRequiredCheckBox.AutoSize = true;
            this.letterRequiredCheckBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.letterRequiredCheckBox.Location = new System.Drawing.Point(178, 10);
            this.letterRequiredCheckBox.Name = "letterRequiredCheckBox";
            this.letterRequiredCheckBox.Size = new System.Drawing.Size(15, 14);
            this.letterRequiredCheckBox.TabIndex = 18;
            this.letterRequiredCheckBox.UseVisualStyleBackColor = true;
            // 
            // paymentRequiredCheckBox
            // 
            this.paymentRequiredCheckBox.AutoSize = true;
            this.paymentRequiredCheckBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.paymentRequiredCheckBox.Location = new System.Drawing.Point(9, 10);
            this.paymentRequiredCheckBox.Name = "paymentRequiredCheckBox";
            this.paymentRequiredCheckBox.Size = new System.Drawing.Size(15, 14);
            this.paymentRequiredCheckBox.TabIndex = 17;
            this.paymentRequiredCheckBox.UseVisualStyleBackColor = true;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // RequestorInfoUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.infoPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "RequestorInfoUI";
            this.Size = new System.Drawing.Size(728, 514);
            this.infoPanel.ResumeLayout(false);
            this.infoPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nonUniqueRequestorIcon)).EndInit();
            this.patientReqPanel.ResumeLayout(false);
            this.patientReqPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox4)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            this.nonPatientReqPanel.ResumeLayout(false);
            this.nonPatientReqPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.epnPanel.ResumeLayout(false);
            this.epnPanel.PerformLayout();
            this.flowLayoutPanel1.ResumeLayout(false);
            this.flowLayoutPanel1.PerformLayout();
            this.contactInfoGroupBox.ResumeLayout(false);
            this.contactInfoGroupBox.PerformLayout();
            this.patientPanel.ResumeLayout(false);
            this.patientPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel infoPanel;
        private System.Windows.Forms.CheckBox letterRequiredCheckBox;
        private System.Windows.Forms.CheckBox paymentRequiredCheckBox;
        private System.Windows.Forms.PictureBox requiredImg;
        private System.Windows.Forms.ComboBox requestorTypeCombo;
        private System.Windows.Forms.Label typeLabel;
        private System.Windows.Forms.Panel patientPanel;
        private System.Windows.Forms.Label patientSsnLabel;
        private System.Windows.Forms.Label patientDobLabel;
        private McK.EIG.ROI.Client.Requestors.View.RequestorInfo.RequestorAddressGroupUI alternateAddressGroupUI;
        private McK.EIG.ROI.Client.Requestors.View.RequestorInfo.RequestorAddressGroupUI mainAddressGroupUI;
        private System.Windows.Forms.GroupBox contactInfoGroupBox;
        private System.Windows.Forms.TextBox contactNameTextBox;
        private System.Windows.Forms.TextBox faxTextBox;
        private System.Windows.Forms.TextBox emailTextBox;
        private System.Windows.Forms.TextBox cellPhoneTextBox;
        private System.Windows.Forms.TextBox workPhoneTextBox;
        private System.Windows.Forms.TextBox homePhoneTextBox;
        private System.Windows.Forms.Label contactPhoneLabel;
        private System.Windows.Forms.Label contactEmailLabel;
        private System.Windows.Forms.Label contactNameLabel;
        private System.Windows.Forms.Label faxLabel;
        private System.Windows.Forms.Label emailLabel;
        private System.Windows.Forms.Label cellPhoneLabel;
        private System.Windows.Forms.Label requestorWorkPhoneLabel;
        private System.Windows.Forms.Label requestorHomePhoneLabel;
        private System.Windows.Forms.TextBox contactEmailTextBox;
        private System.Windows.Forms.TextBox contactPhoneTextBox;
        private System.Windows.Forms.ToolTip toolTip;
        internal System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.CheckBox statusCheckBox;
        private RequestorFooterUI requestorFooterUI;
        private System.Windows.Forms.Panel epnPanel;
        private System.Windows.Forms.Label prefixLabel;
        private System.Windows.Forms.TextBox epnTextBox;
        private System.Windows.Forms.Label patientEpnLabel;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.ComboBox facilityComboBox;
        private System.Windows.Forms.TextBox mrnTextBox;
        private System.Windows.Forms.Label patientMrnLabel;
        private System.Windows.Forms.Label patientFacilityLabel;
        private System.Windows.Forms.MaskedTextBox ssnTextBox;
        private System.Windows.Forms.Panel nonPatientReqPanel;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.PictureBox nonUniqueRequestorIcon;
        private System.Windows.Forms.TextBox nameTextBox;
        private System.Windows.Forms.Label reqNameLabel;
        private System.Windows.Forms.Panel patientReqPanel;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.TextBox reqLastNameTextBox;
        private System.Windows.Forms.Label reqLastNameLabel;
        private System.Windows.Forms.PictureBox pictureBox4;
        private System.Windows.Forms.TextBox reqFirstNameTextBox;
        private System.Windows.Forms.Label reqFirstNameLabel;
        private McK.EIG.ROI.Client.Base.View.Common.MaskedEditDateControl dobTextBox;
    }
}
