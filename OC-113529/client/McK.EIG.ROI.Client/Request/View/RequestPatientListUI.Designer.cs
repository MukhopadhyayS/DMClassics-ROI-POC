using McK.EIG.ROI.Client.Base.View.Common;
namespace McK.EIG.ROI.Client.Request.View
{
    partial class RequestPatientListUI
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(RequestPatientListUI));
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.panel1 = new System.Windows.Forms.Panel();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.patientInfopanel = new System.Windows.Forms.Panel();
            this.mrnValueLabel = new System.Windows.Forms.Label();
            this.mrnLabel = new System.Windows.Forms.Label();
            this.facilityValueLabel = new System.Windows.Forms.Label();
            this.epnLabel = new System.Windows.Forms.Label();
            this.epnValueLabel = new System.Windows.Forms.Label();
            this.facilityLabel = new System.Windows.Forms.Label();
            this.flowLayoutPanel2 = new System.Windows.Forms.FlowLayoutPanel();
            this.addPatientButton = new System.Windows.Forms.Button();
            this.addAnotherPatientButton = new System.Windows.Forms.Button();
            this.removePatientButton = new System.Windows.Forms.Button();
            this.viewEditPatientButton = new System.Windows.Forms.Button();
            this.genderLabel = new System.Windows.Forms.Label();
            this.ssnValueLabel = new System.Windows.Forms.Label();
            this.ssnLabel = new System.Windows.Forms.Label();
            this.dobShortLabel = new System.Windows.Forms.Label();
            this.genderValueLabel = new System.Windows.Forms.Label();
            this.dobValueLabel = new System.Windows.Forms.Label();
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.patientNameLabel = new System.Windows.Forms.Label();
            this.nameValueLabel = new System.Windows.Forms.Label();
            this.lockedPatientIcon = new System.Windows.Forms.PictureBox();
            this.vipPatientIcon = new System.Windows.Forms.PictureBox();
            this.patientList = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.panel1.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            this.patientInfopanel.SuspendLayout();
            this.flowLayoutPanel2.SuspendLayout();
            this.flowLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.lockedPatientIcon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.vipPatientIcon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.patientList)).BeginInit();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.Transparent;
            this.panel1.Controls.Add(this.tableLayoutPanel1);
            this.panel1.Controls.Add(this.patientList);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(3, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(531, 186);
            this.panel1.TabIndex = 44;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.BackColor = System.Drawing.Color.Transparent;
            this.tableLayoutPanel1.ColumnCount = 1;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.Controls.Add(this.patientInfopanel, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.flowLayoutPanel1, 0, 0);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(153, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 2;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 14.83517F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 85.16483F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(378, 186);
            this.tableLayoutPanel1.TabIndex = 43;
            // 
            // patientInfopanel
            // 
            this.patientInfopanel.AutoScroll = true;
            this.patientInfopanel.Controls.Add(this.mrnValueLabel);
            this.patientInfopanel.Controls.Add(this.mrnLabel);
            this.patientInfopanel.Controls.Add(this.facilityValueLabel);
            this.patientInfopanel.Controls.Add(this.epnLabel);
            this.patientInfopanel.Controls.Add(this.epnValueLabel);
            this.patientInfopanel.Controls.Add(this.facilityLabel);
            this.patientInfopanel.Controls.Add(this.flowLayoutPanel2);
            this.patientInfopanel.Controls.Add(this.genderLabel);
            this.patientInfopanel.Controls.Add(this.ssnValueLabel);
            this.patientInfopanel.Controls.Add(this.ssnLabel);
            this.patientInfopanel.Controls.Add(this.dobShortLabel);
            this.patientInfopanel.Controls.Add(this.genderValueLabel);
            this.patientInfopanel.Controls.Add(this.dobValueLabel);
            this.patientInfopanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.patientInfopanel.Location = new System.Drawing.Point(0, 27);
            this.patientInfopanel.Margin = new System.Windows.Forms.Padding(0);
            this.patientInfopanel.Name = "patientInfopanel";
            this.patientInfopanel.Size = new System.Drawing.Size(378, 159);
            this.patientInfopanel.TabIndex = 42;
            // 
            // mrnValueLabel
            // 
            this.mrnValueLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnValueLabel.Location = new System.Drawing.Point(65, 67);
            this.mrnValueLabel.Name = "mrnValueLabel";
            this.mrnValueLabel.Size = new System.Drawing.Size(421, 17);
            this.mrnValueLabel.TabIndex = 41;
            this.mrnValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // mrnLabel
            // 
            this.mrnLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mrnLabel.Location = new System.Drawing.Point(7, 67);
            this.mrnLabel.Name = "mrnLabel";
            this.mrnLabel.Size = new System.Drawing.Size(50, 17);
            this.mrnLabel.TabIndex = 40;
            this.mrnLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // facilityValueLabel
            // 
            this.facilityValueLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityValueLabel.Location = new System.Drawing.Point(65, 50);
            this.facilityValueLabel.Name = "facilityValueLabel";
            this.facilityValueLabel.Size = new System.Drawing.Size(93, 17);
            this.facilityValueLabel.TabIndex = 39;
            this.facilityValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // epnLabel
            // 
            this.epnLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnLabel.Location = new System.Drawing.Point(6, 84);
            this.epnLabel.Name = "epnLabel";
            this.epnLabel.Size = new System.Drawing.Size(43, 17);
            this.epnLabel.TabIndex = 45;
            this.epnLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // epnValueLabel
            // 
            this.epnValueLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.epnValueLabel.Location = new System.Drawing.Point(66, 88);
            this.epnValueLabel.Name = "epnValueLabel";
            this.epnValueLabel.Size = new System.Drawing.Size(420, 17);
            this.epnValueLabel.TabIndex = 46;
            // 
            // facilityLabel
            // 
            this.facilityLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.facilityLabel.Location = new System.Drawing.Point(6, 50);
            this.facilityLabel.Name = "facilityLabel";
            this.facilityLabel.Size = new System.Drawing.Size(54, 17);
            this.facilityLabel.TabIndex = 34;
            this.facilityLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // flowLayoutPanel2
            // 
            this.flowLayoutPanel2.Controls.Add(this.addPatientButton);
            this.flowLayoutPanel2.Controls.Add(this.addAnotherPatientButton);
            this.flowLayoutPanel2.Controls.Add(this.removePatientButton);
            this.flowLayoutPanel2.Controls.Add(this.viewEditPatientButton);
            this.flowLayoutPanel2.Dock = System.Windows.Forms.DockStyle.Right;
            this.flowLayoutPanel2.FlowDirection = System.Windows.Forms.FlowDirection.BottomUp;
            this.flowLayoutPanel2.Location = new System.Drawing.Point(486, 0);
            this.flowLayoutPanel2.Margin = new System.Windows.Forms.Padding(0);
            this.flowLayoutPanel2.Name = "flowLayoutPanel2";
            this.flowLayoutPanel2.Size = new System.Drawing.Size(169, 142);
            this.flowLayoutPanel2.TabIndex = 44;
            // 
            // addPatientButton
            // 
            this.addPatientButton.AutoSize = true;
            this.addPatientButton.Location = new System.Drawing.Point(3, 112);
            this.addPatientButton.Name = "addPatientButton";
            this.addPatientButton.Size = new System.Drawing.Size(87, 27);
            this.addPatientButton.TabIndex = 1;
            this.addPatientButton.UseVisualStyleBackColor = true;
            // 
            // addAnotherPatientButton
            // 
            this.addAnotherPatientButton.AutoSize = true;
            this.addAnotherPatientButton.Location = new System.Drawing.Point(3, 79);
            this.addAnotherPatientButton.Name = "addAnotherPatientButton";
            this.addAnotherPatientButton.Size = new System.Drawing.Size(124, 27);
            this.addAnotherPatientButton.TabIndex = 4;
            this.addAnotherPatientButton.UseVisualStyleBackColor = true;
            this.addAnotherPatientButton.Visible = false;
            // 
            // removePatientButton
            // 
            this.removePatientButton.AutoSize = true;
            this.removePatientButton.Location = new System.Drawing.Point(3, 46);
            this.removePatientButton.Name = "removePatientButton";
            this.removePatientButton.Size = new System.Drawing.Size(155, 27);
            this.removePatientButton.TabIndex = 3;
            this.removePatientButton.UseVisualStyleBackColor = true;
            this.removePatientButton.Visible = false;
            // 
            // viewEditPatientButton
            // 
            this.viewEditPatientButton.AutoSize = true;
            this.viewEditPatientButton.Location = new System.Drawing.Point(3, 13);
            this.viewEditPatientButton.Name = "viewEditPatientButton";
            this.viewEditPatientButton.Size = new System.Drawing.Size(146, 27);
            this.viewEditPatientButton.TabIndex = 2;
            this.viewEditPatientButton.UseVisualStyleBackColor = true;
            this.viewEditPatientButton.Visible = false;
            // 
            // genderLabel
            // 
            this.genderLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.genderLabel.Location = new System.Drawing.Point(6, -1);
            this.genderLabel.Name = "genderLabel";
            this.genderLabel.Size = new System.Drawing.Size(59, 17);
            this.genderLabel.TabIndex = 31;
            this.genderLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // ssnValueLabel
            // 
            this.ssnValueLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ssnValueLabel.Location = new System.Drawing.Point(64, 32);
            this.ssnValueLabel.Name = "ssnValueLabel";
            this.ssnValueLabel.Size = new System.Drawing.Size(140, 17);
            this.ssnValueLabel.TabIndex = 38;
            this.ssnValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // ssnLabel
            // 
            this.ssnLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ssnLabel.Location = new System.Drawing.Point(6, 33);
            this.ssnLabel.Name = "ssnLabel";
            this.ssnLabel.Size = new System.Drawing.Size(42, 17);
            this.ssnLabel.TabIndex = 33;
            this.ssnLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // dobShortLabel
            // 
            this.dobShortLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dobShortLabel.Location = new System.Drawing.Point(6, 16);
            this.dobShortLabel.Name = "dobShortLabel";
            this.dobShortLabel.Size = new System.Drawing.Size(42, 17);
            this.dobShortLabel.TabIndex = 32;
            this.dobShortLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // genderValueLabel
            // 
            this.genderValueLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.genderValueLabel.Location = new System.Drawing.Point(64, -1);
            this.genderValueLabel.Name = "genderValueLabel";
            this.genderValueLabel.Size = new System.Drawing.Size(116, 17);
            this.genderValueLabel.TabIndex = 36;
            this.genderValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // dobValueLabel
            // 
            this.dobValueLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dobValueLabel.Location = new System.Drawing.Point(64, 16);
            this.dobValueLabel.Name = "dobValueLabel";
            this.dobValueLabel.Size = new System.Drawing.Size(140, 17);
            this.dobValueLabel.TabIndex = 37;
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Controls.Add(this.patientNameLabel);
            this.flowLayoutPanel1.Controls.Add(this.nameValueLabel);
            this.flowLayoutPanel1.Controls.Add(this.lockedPatientIcon);
            this.flowLayoutPanel1.Controls.Add(this.vipPatientIcon);
            this.flowLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.flowLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(378, 27);
            this.flowLayoutPanel1.TabIndex = 43;
            // 
            // patientNameLabel
            // 
            this.patientNameLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.patientNameLabel.Location = new System.Drawing.Point(6, 0);
            this.patientNameLabel.Margin = new System.Windows.Forms.Padding(6, 0, 3, 0);
            this.patientNameLabel.Name = "patientNameLabel";
            this.patientNameLabel.Size = new System.Drawing.Size(52, 17);
            this.patientNameLabel.TabIndex = 30;
            // 
            // nameValueLabel
            // 
            this.nameValueLabel.AutoEllipsis = true;
            this.nameValueLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nameValueLabel.Location = new System.Drawing.Point(64, 0);
            this.nameValueLabel.Name = "nameValueLabel";
            this.nameValueLabel.Size = new System.Drawing.Size(200, 17);
            this.nameValueLabel.TabIndex = 35;
            this.nameValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // lockedPatientIcon
            // 
            this.lockedPatientIcon.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("lockedPatientIcon.BackgroundImage")));
            this.lockedPatientIcon.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.lockedPatientIcon.Location = new System.Drawing.Point(270, 3);
            this.lockedPatientIcon.Name = "lockedPatientIcon";
            this.lockedPatientIcon.Size = new System.Drawing.Size(20, 17);
            this.lockedPatientIcon.TabIndex = 53;
            this.lockedPatientIcon.TabStop = false;
            this.lockedPatientIcon.Visible = false;
            // 
            // vipPatientIcon
            // 
            this.vipPatientIcon.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("vipPatientIcon.BackgroundImage")));
            this.vipPatientIcon.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.vipPatientIcon.Location = new System.Drawing.Point(296, 3);
            this.vipPatientIcon.Name = "vipPatientIcon";
            this.vipPatientIcon.Size = new System.Drawing.Size(20, 17);
            this.vipPatientIcon.TabIndex = 54;
            this.vipPatientIcon.TabStop = false;
            this.vipPatientIcon.Visible = false;
            // 
            // patientList
            // 
            this.patientList.AllowUserToAddRows = false;
            this.patientList.AllowUserToDeleteRows = false;
            this.patientList.AllowUserToResizeRows = false;
            this.patientList.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.patientList.BackgroundColor = System.Drawing.Color.White;
            this.patientList.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.SingleVertical;
            this.patientList.ChangeValidator = null;
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(228)))), ((int)(((byte)(228)))), ((int)(((byte)(228)))));
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.patientList.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle1;
            this.patientList.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.patientList.ConfirmSelection = false;
            this.patientList.Dock = System.Windows.Forms.DockStyle.Left;
            this.patientList.Location = new System.Drawing.Point(0, 0);
            this.patientList.MultiSelect = false;
            this.patientList.Name = "patientList";
            this.patientList.ReadOnly = true;
            this.patientList.RowHeadersVisible = false;
            this.patientList.SelectionHandler = null;
            this.patientList.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.patientList.Size = new System.Drawing.Size(153, 186);
            this.patientList.SortEnabled = false;
            this.patientList.StandardTab = true;
            this.patientList.TabIndex = 0;
            // 
            // RequestPatientListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.panel1);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "RequestPatientListUI";
            this.Padding = new System.Windows.Forms.Padding(3);
            this.Size = new System.Drawing.Size(537, 192);
            this.panel1.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.patientInfopanel.ResumeLayout(false);
            this.flowLayoutPanel2.ResumeLayout(false);
            this.flowLayoutPanel2.PerformLayout();
            this.flowLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.lockedPatientIcon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.vipPatientIcon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.patientList)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button addPatientButton;
        private System.Windows.Forms.Button viewEditPatientButton;
        private System.Windows.Forms.Button removePatientButton;
        private System.Windows.Forms.Button addAnotherPatientButton;
        private System.Windows.Forms.Label ssnValueLabel;
        private System.Windows.Forms.Label dobValueLabel;
        private System.Windows.Forms.Label genderValueLabel;
        private System.Windows.Forms.Label nameValueLabel;
        private System.Windows.Forms.Label ssnLabel;
        private System.Windows.Forms.Label dobShortLabel;
        private System.Windows.Forms.Label genderLabel;
        private System.Windows.Forms.Label patientNameLabel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel patientInfopanel;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.PictureBox lockedPatientIcon;
        private System.Windows.Forms.PictureBox vipPatientIcon;
        private EIGDataGrid patientList;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel2;
        private System.Windows.Forms.Label mrnValueLabel;
        private System.Windows.Forms.Label mrnLabel;
        private System.Windows.Forms.Label facilityValueLabel;
        private System.Windows.Forms.Label epnLabel;
        private System.Windows.Forms.Label epnValueLabel;
        private System.Windows.Forms.Label facilityLabel;


    }
}
