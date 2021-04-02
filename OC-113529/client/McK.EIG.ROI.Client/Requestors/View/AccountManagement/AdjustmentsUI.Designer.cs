using System.Windows.Forms;
namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    partial class AdjustmentsUI
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle5 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle6 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            this.applyAdjustmentToLabel = new System.Windows.Forms.Label();
            this.amountDetailsPanel = new System.Windows.Forms.Panel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.saveCancelPanel = new System.Windows.Forms.Panel();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.removeAdjustmentButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.eigDataGrid3 = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.eigDataGrid2 = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.applyButton = new System.Windows.Forms.Button();
            this.lblApplyAdjustmentText = new System.Windows.Forms.Label();
            this.invoiceGridPanel = new System.Windows.Forms.Panel();
            this.adjustmentGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.reasonPanel = new System.Windows.Forms.Panel();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.amountTextBox = new System.Windows.Forms.TextBox();
            this.amountLabel = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.typeComboBox = new System.Windows.Forms.ComboBox();
            this.typeLabel = new System.Windows.Forms.Label();
            this.requiredImg3 = new System.Windows.Forms.PictureBox();
            this.requiredImg2 = new System.Windows.Forms.PictureBox();
            this.requiredImg1 = new System.Windows.Forms.PictureBox();
            this.restrictedFacilityLabel = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.NoteLabel = new System.Windows.Forms.Label();
            this.eigDataGrid1 = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.availableValueLabel = new System.Windows.Forms.Label();
            this.appliedValueLabel = new System.Windows.Forms.Label();
            this.adjustmentAmountValueLabel = new System.Windows.Forms.Label();
            this.availableLabel = new System.Windows.Forms.Label();
            this.appliedLabel = new System.Windows.Forms.Label();
            this.adjustmentAmountLabel = new System.Windows.Forms.Label();
            this.dateTimePicker1 = new System.Windows.Forms.DateTimePicker();
            this.reasonComboBox = new System.Windows.Forms.ComboBox();
            this.datePanel = new System.Windows.Forms.Label();
            this.reasonLabel = new System.Windows.Forms.Label();
            this.outerTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.amountDetailsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.saveCancelPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid2)).BeginInit();
            this.invoiceGridPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.adjustmentGrid)).BeginInit();
            this.reasonPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid1)).BeginInit();
            this.outerTableLayoutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // applyAdjustmentToLabel
            // 
            this.applyAdjustmentToLabel.AutoSize = true;
            this.applyAdjustmentToLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.applyAdjustmentToLabel.Location = new System.Drawing.Point(1, 82);
            this.applyAdjustmentToLabel.Name = "applyAdjustmentToLabel";
            this.applyAdjustmentToLabel.Size = new System.Drawing.Size(112, 15);
            this.applyAdjustmentToLabel.TabIndex = 6;
            this.applyAdjustmentToLabel.Text = "ApplyAdjustmentTo";
            // 
            // amountDetailsPanel
            // 
            this.amountDetailsPanel.Controls.Add(this.applyAdjustmentToLabel);
            this.amountDetailsPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.amountDetailsPanel.Location = new System.Drawing.Point(3, 298);
            this.amountDetailsPanel.Name = "amountDetailsPanel";
            this.amountDetailsPanel.Size = new System.Drawing.Size(572, 22);
            this.amountDetailsPanel.TabIndex = 2;
            // 
            // errorProvider
            // 
            this.errorProvider.ContainerControl = this;
            // 
            // saveCancelPanel
            // 
            this.saveCancelPanel.Controls.Add(this.requiredLabel);
            this.saveCancelPanel.Controls.Add(this.requiredImg);
            this.saveCancelPanel.Controls.Add(this.removeAdjustmentButton);
            this.saveCancelPanel.Controls.Add(this.cancelButton);
            this.saveCancelPanel.Controls.Add(this.saveButton);
            this.saveCancelPanel.Location = new System.Drawing.Point(3, 350);
            this.saveCancelPanel.Name = "saveCancelPanel";
            this.saveCancelPanel.Size = new System.Drawing.Size(663, 27);
            this.saveCancelPanel.TabIndex = 5;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(16, 5);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 12;
            // 
            // requiredImg
            // 
            this.requiredImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImg.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.Location = new System.Drawing.Point(5, 6);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(13, 10);
            this.requiredImg.TabIndex = 11;
            this.requiredImg.TabStop = false;
            // 
            // removeAdjustmentButton
            // 
            this.removeAdjustmentButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.removeAdjustmentButton.Location = new System.Drawing.Point(504, 2);
            this.removeAdjustmentButton.Name = "removeAdjustmentButton";
            this.removeAdjustmentButton.Size = new System.Drawing.Size(143, 23);
            this.removeAdjustmentButton.TabIndex = 2;
            this.removeAdjustmentButton.Text = "Remove Adjustment";
            this.removeAdjustmentButton.UseVisualStyleBackColor = true;
            this.removeAdjustmentButton.Visible = false;
            this.removeAdjustmentButton.Click += new System.EventHandler(this.removeAdjustmentButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(288, 1);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 1;
            this.cancelButton.Text = "button3";
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // saveButton
            // 
            this.saveButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(208, 1);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 23);
            this.saveButton.TabIndex = 0;
            this.saveButton.Text = "button2";
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.eigDataGrid3);
            this.panel1.Controls.Add(this.eigDataGrid2);
            this.panel1.Controls.Add(this.applyButton);
            this.panel1.Controls.Add(this.lblApplyAdjustmentText);
            this.panel1.Location = new System.Drawing.Point(3, 171);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(667, 43);
            this.panel1.TabIndex = 6;
            // 
            // eigDataGrid3
            // 
            this.eigDataGrid3.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.eigDataGrid3.ChangeValidator = null;
            this.eigDataGrid3.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.eigDataGrid3.ConfirmSelection = false;
            this.eigDataGrid3.Location = new System.Drawing.Point(81, 5);
            this.eigDataGrid3.Name = "eigDataGrid3";
            this.eigDataGrid3.SelectionHandler = null;
            this.eigDataGrid3.Size = new System.Drawing.Size(580, 2);
            this.eigDataGrid3.SortEnabled = false;
            this.eigDataGrid3.TabIndex = 52;
            // 
            // eigDataGrid2
            // 
            this.eigDataGrid2.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.eigDataGrid2.ChangeValidator = null;
            this.eigDataGrid2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.eigDataGrid2.ConfirmSelection = false;
            this.eigDataGrid2.Location = new System.Drawing.Point(0, 5);
            this.eigDataGrid2.Name = "eigDataGrid2";
            this.eigDataGrid2.SelectionHandler = null;
            this.eigDataGrid2.Size = new System.Drawing.Size(580, 2);
            this.eigDataGrid2.SortEnabled = false;
            this.eigDataGrid2.TabIndex = 51;
            // 
            // applyButton
            // 
            this.applyButton.AutoSize = true;
            this.applyButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.applyButton.Location = new System.Drawing.Point(580, 13);
            this.applyButton.Name = "applyButton";
            this.applyButton.Size = new System.Drawing.Size(70, 25);
            this.applyButton.TabIndex = 21;
            this.applyButton.UseVisualStyleBackColor = true;
            this.applyButton.Click += new System.EventHandler(this.applyButton_Click);
            // 
            // lblApplyAdjustmentText
            // 
            this.lblApplyAdjustmentText.AutoSize = true;
            this.lblApplyAdjustmentText.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblApplyAdjustmentText.Location = new System.Drawing.Point(4, 17);
            this.lblApplyAdjustmentText.Margin = new System.Windows.Forms.Padding(6, 3, 3, 0);
            this.lblApplyAdjustmentText.Name = "lblApplyAdjustmentText";
            this.lblApplyAdjustmentText.Size = new System.Drawing.Size(107, 15);
            this.lblApplyAdjustmentText.TabIndex = 50;
            this.lblApplyAdjustmentText.Text = "Apply adjustment :";
            // 
            // invoiceGridPanel
            // 
            this.invoiceGridPanel.Controls.Add(this.adjustmentGrid);
            this.invoiceGridPanel.Location = new System.Drawing.Point(3, 220);
            this.invoiceGridPanel.Name = "invoiceGridPanel";
            this.invoiceGridPanel.Size = new System.Drawing.Size(667, 124);
            this.invoiceGridPanel.TabIndex = 3;
            // 
            // adjustmentGrid
            // 
            this.adjustmentGrid.AllowUserToAddRows = false;
            this.adjustmentGrid.AllowUserToDeleteRows = false;
            this.adjustmentGrid.AllowUserToResizeRows = false;
            this.adjustmentGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.adjustmentGrid.BackgroundColor = System.Drawing.Color.White;
            this.adjustmentGrid.ChangeValidator = null;
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle4.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle4.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle4.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle4.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle4.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.adjustmentGrid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle4;
            this.adjustmentGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.adjustmentGrid.ConfirmSelection = false;
            dataGridViewCellStyle5.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle5.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle5.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle5.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle5.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle5.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.adjustmentGrid.DefaultCellStyle = dataGridViewCellStyle5;
            this.adjustmentGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.adjustmentGrid.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnEnter;
            this.adjustmentGrid.Location = new System.Drawing.Point(0, 0);
            this.adjustmentGrid.MultiSelect = false;
            this.adjustmentGrid.Name = "adjustmentGrid";
            dataGridViewCellStyle6.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle6.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle6.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle6.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle6.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle6.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.adjustmentGrid.RowHeadersDefaultCellStyle = dataGridViewCellStyle6;
            this.adjustmentGrid.RowHeadersVisible = false;
            this.adjustmentGrid.SelectionHandler = null;
            this.adjustmentGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.adjustmentGrid.ShowCellToolTips = false;
            this.adjustmentGrid.Size = new System.Drawing.Size(667, 124);
            this.adjustmentGrid.SortEnabled = false;
            this.adjustmentGrid.TabIndex = 8;
            this.adjustmentGrid.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.adjustmentGrid_CellClick);
            this.adjustmentGrid.CellEndEdit += new System.Windows.Forms.DataGridViewCellEventHandler(this.adjustmentGrid_CellEndEdit);
            this.adjustmentGrid.DataError += new System.Windows.Forms.DataGridViewDataErrorEventHandler(this.adjustmentGrid_DataError);
            // 
            // reasonPanel
            // 
            this.reasonPanel.Controls.Add(this.pictureBox2);
            this.reasonPanel.Controls.Add(this.amountTextBox);
            this.reasonPanel.Controls.Add(this.amountLabel);
            this.reasonPanel.Controls.Add(this.pictureBox1);
            this.reasonPanel.Controls.Add(this.typeComboBox);
            this.reasonPanel.Controls.Add(this.typeLabel);
            this.reasonPanel.Controls.Add(this.requiredImg3);
            this.reasonPanel.Controls.Add(this.requiredImg2);
            this.reasonPanel.Controls.Add(this.requiredImg1);
            this.reasonPanel.Controls.Add(this.restrictedFacilityLabel);
            this.reasonPanel.Controls.Add(this.textBox1);
            this.reasonPanel.Controls.Add(this.NoteLabel);
            this.reasonPanel.Controls.Add(this.eigDataGrid1);
            this.reasonPanel.Controls.Add(this.availableValueLabel);
            this.reasonPanel.Controls.Add(this.appliedValueLabel);
            this.reasonPanel.Controls.Add(this.adjustmentAmountValueLabel);
            this.reasonPanel.Controls.Add(this.availableLabel);
            this.reasonPanel.Controls.Add(this.appliedLabel);
            this.reasonPanel.Controls.Add(this.adjustmentAmountLabel);
            this.reasonPanel.Controls.Add(this.dateTimePicker1);
            this.reasonPanel.Controls.Add(this.reasonComboBox);
            this.reasonPanel.Controls.Add(this.datePanel);
            this.reasonPanel.Controls.Add(this.reasonLabel);
            this.reasonPanel.Location = new System.Drawing.Point(3, 3);
            this.reasonPanel.Name = "reasonPanel";
            this.reasonPanel.Size = new System.Drawing.Size(667, 160);
            this.reasonPanel.TabIndex = 0;
            this.reasonPanel.Paint += new System.Windows.Forms.PaintEventHandler(this.reasonPanel_Paint);
            // 
            // pictureBox2
            // 
            this.pictureBox2.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox2.Location = new System.Drawing.Point(4, 43);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(10, 11);
            this.pictureBox2.TabIndex = 56;
            this.pictureBox2.TabStop = false;
            // 
            // amountTextBox
            // 
            this.amountTextBox.Location = new System.Drawing.Point(72, 41);
            this.amountTextBox.MaxLength = 256;
            this.amountTextBox.Name = "amountTextBox";
            this.amountTextBox.Size = new System.Drawing.Size(172, 20);
            this.amountTextBox.TabIndex = 1;
            this.amountTextBox.TextChanged += new System.EventHandler(this.amountTextBox_TextChanged);
            this.amountTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.amountTextBox_KeyPress);
            // 
            // amountLabel
            // 
            this.amountLabel.AutoSize = true;
            this.amountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.amountLabel.Location = new System.Drawing.Point(19, 41);
            this.amountLabel.Name = "amountLabel";
            this.amountLabel.Size = new System.Drawing.Size(0, 15);
            this.amountLabel.TabIndex = 55;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox1.Location = new System.Drawing.Point(4, 13);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(10, 11);
            this.pictureBox1.TabIndex = 53;
            this.pictureBox1.TabStop = false;
            // 
            // typeComboBox
            // 
            this.typeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.typeComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.typeComboBox.FormattingEnabled = true;
            this.typeComboBox.Location = new System.Drawing.Point(72, 8);
            this.typeComboBox.Name = "typeComboBox";
            this.typeComboBox.Size = new System.Drawing.Size(251, 23);
            this.typeComboBox.TabIndex = 0;
            this.typeComboBox.SelectedIndexChanged += new System.EventHandler(this.typeComboBox_SelectedIndexChanged);
            // 
            // typeLabel
            // 
            this.typeLabel.AutoSize = true;
            this.typeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.typeLabel.Location = new System.Drawing.Point(34, 11);
            this.typeLabel.Name = "typeLabel";
            this.typeLabel.Size = new System.Drawing.Size(0, 15);
            this.typeLabel.TabIndex = 52;
            // 
            // requiredImg3
            // 
            this.requiredImg3.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg3.Location = new System.Drawing.Point(6, 135);
            this.requiredImg3.Name = "requiredImg3";
            this.requiredImg3.Size = new System.Drawing.Size(10, 13);
            this.requiredImg3.TabIndex = 50;
            this.requiredImg3.TabStop = false;
            // 
            // requiredImg2
            // 
            this.requiredImg2.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg2.Location = new System.Drawing.Point(5, 103);
            this.requiredImg2.Name = "requiredImg2";
            this.requiredImg2.Size = new System.Drawing.Size(10, 11);
            this.requiredImg2.TabIndex = 49;
            this.requiredImg2.TabStop = false;
            // 
            // requiredImg1
            // 
            this.requiredImg1.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg1.Location = new System.Drawing.Point(5, 73);
            this.requiredImg1.Name = "requiredImg1";
            this.requiredImg1.Size = new System.Drawing.Size(10, 11);
            this.requiredImg1.TabIndex = 48;
            this.requiredImg1.TabStop = false;
            // 
            // restrictedFacilityLabel
            // 
            this.restrictedFacilityLabel.AutoSize = true;
            this.restrictedFacilityLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.restrictedFacilityLabel.ForeColor = System.Drawing.Color.Red;
            this.restrictedFacilityLabel.Location = new System.Drawing.Point(336, 93);
            this.restrictedFacilityLabel.Name = "restrictedFacilityLabel";
            this.restrictedFacilityLabel.Size = new System.Drawing.Size(0, 15);
            this.restrictedFacilityLabel.TabIndex = 17;
            this.restrictedFacilityLabel.Visible = false;
            // 
            // textBox1
            // 
            this.textBox1.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBox1.Location = new System.Drawing.Point(72, 101);
            this.textBox1.MaxLength = 256;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(250, 21);
            this.textBox1.TabIndex = 3;
            this.textBox1.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // NoteLabel
            // 
            this.NoteLabel.AutoSize = true;
            this.NoteLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.NoteLabel.Location = new System.Drawing.Point(34, 101);
            this.NoteLabel.Name = "NoteLabel";
            this.NoteLabel.Size = new System.Drawing.Size(36, 15);
            this.NoteLabel.TabIndex = 42;
            this.NoteLabel.Text = "Note:";
            // 
            // eigDataGrid1
            // 
            this.eigDataGrid1.AllowUserToAddRows = false;
            this.eigDataGrid1.AllowUserToDeleteRows = false;
            this.eigDataGrid1.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.eigDataGrid1.ChangeValidator = null;
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.eigDataGrid1.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle1;
            this.eigDataGrid1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.eigDataGrid1.ConfirmSelection = false;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle2.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.eigDataGrid1.DefaultCellStyle = dataGridViewCellStyle2;
            this.eigDataGrid1.Location = new System.Drawing.Point(395, 46);
            this.eigDataGrid1.Name = "eigDataGrid1";
            this.eigDataGrid1.ReadOnly = true;
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle3.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle3.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle3.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle3.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.eigDataGrid1.RowHeadersDefaultCellStyle = dataGridViewCellStyle3;
            this.eigDataGrid1.SelectionHandler = null;
            this.eigDataGrid1.Size = new System.Drawing.Size(220, 2);
            this.eigDataGrid1.SortEnabled = false;
            this.eigDataGrid1.TabIndex = 14;
            // 
            // availableValueLabel
            // 
            this.availableValueLabel.AutoSize = true;
            this.availableValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.availableValueLabel.Location = new System.Drawing.Point(523, 55);
            this.availableValueLabel.Name = "availableValueLabel";
            this.availableValueLabel.Size = new System.Drawing.Size(0, 15);
            this.availableValueLabel.TabIndex = 13;
            this.availableValueLabel.TextChanged += new System.EventHandler(this.availableValueLabel_TextChanged);
            // 
            // appliedValueLabel
            // 
            this.appliedValueLabel.AutoSize = true;
            this.appliedValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.appliedValueLabel.Location = new System.Drawing.Point(523, 24);
            this.appliedValueLabel.Name = "appliedValueLabel";
            this.appliedValueLabel.Size = new System.Drawing.Size(0, 15);
            this.appliedValueLabel.TabIndex = 12;
            // 
            // adjustmentAmountValueLabel
            // 
            this.adjustmentAmountValueLabel.AutoSize = true;
            this.adjustmentAmountValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adjustmentAmountValueLabel.Location = new System.Drawing.Point(523, 7);
            this.adjustmentAmountValueLabel.Name = "adjustmentAmountValueLabel";
            this.adjustmentAmountValueLabel.Size = new System.Drawing.Size(0, 15);
            this.adjustmentAmountValueLabel.TabIndex = 11;
            // 
            // availableLabel
            // 
            this.availableLabel.AutoSize = true;
            this.availableLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.availableLabel.Location = new System.Drawing.Point(452, 55);
            this.availableLabel.Name = "availableLabel";
            this.availableLabel.Size = new System.Drawing.Size(59, 15);
            this.availableLabel.TabIndex = 47;
            this.availableLabel.Text = "Available:";
            // 
            // appliedLabel
            // 
            this.appliedLabel.AutoSize = true;
            this.appliedLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.appliedLabel.Location = new System.Drawing.Point(464, 24);
            this.appliedLabel.Name = "appliedLabel";
            this.appliedLabel.Size = new System.Drawing.Size(51, 15);
            this.appliedLabel.TabIndex = 45;
            this.appliedLabel.Text = "Applied:";
            // 
            // adjustmentAmountLabel
            // 
            this.adjustmentAmountLabel.AutoSize = true;
            this.adjustmentAmountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adjustmentAmountLabel.Location = new System.Drawing.Point(398, 7);
            this.adjustmentAmountLabel.Name = "adjustmentAmountLabel";
            this.adjustmentAmountLabel.Size = new System.Drawing.Size(117, 15);
            this.adjustmentAmountLabel.TabIndex = 44;
            this.adjustmentAmountLabel.Text = "Adjustment Amount:";
            // 
            // dateTimePicker1
            // 
            this.dateTimePicker1.CustomFormat = "MM/dd/yyyy ";
            this.dateTimePicker1.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dateTimePicker1.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dateTimePicker1.Location = new System.Drawing.Point(72, 133);
            this.dateTimePicker1.Name = "dateTimePicker1";
            this.dateTimePicker1.Size = new System.Drawing.Size(213, 21);
            this.dateTimePicker1.TabIndex = 4;
            // 
            // reasonComboBox
            // 
            this.reasonComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reasonComboBox.FormattingEnabled = true;
            this.reasonComboBox.Location = new System.Drawing.Point(72, 68);
            this.reasonComboBox.Name = "reasonComboBox";
            this.reasonComboBox.Size = new System.Drawing.Size(250, 23);
            this.reasonComboBox.TabIndex = 2;
            this.reasonComboBox.SelectedIndexChanged += new System.EventHandler(this.reasonComboBox_SelectedIndexChanged);
            // 
            // datePanel
            // 
            this.datePanel.AutoSize = true;
            this.datePanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.datePanel.Location = new System.Drawing.Point(33, 133);
            this.datePanel.Name = "datePanel";
            this.datePanel.Size = new System.Drawing.Size(36, 15);
            this.datePanel.TabIndex = 41;
            this.datePanel.Text = "Date:";
            // 
            // reasonLabel
            // 
            this.reasonLabel.AutoSize = true;
            this.reasonLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reasonLabel.Location = new System.Drawing.Point(18, 71);
            this.reasonLabel.Name = "reasonLabel";
            this.reasonLabel.Size = new System.Drawing.Size(54, 15);
            this.reasonLabel.TabIndex = 40;
            this.reasonLabel.Text = "Reason:";
            // 
            // outerTableLayoutPanel
            // 
            this.outerTableLayoutPanel.BackColor = System.Drawing.SystemColors.Window;
            this.outerTableLayoutPanel.ColumnCount = 1;
            this.outerTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.outerTableLayoutPanel.Controls.Add(this.reasonPanel, 0, 0);
            this.outerTableLayoutPanel.Controls.Add(this.invoiceGridPanel, 0, 2);
            this.outerTableLayoutPanel.Controls.Add(this.panel1, 0, 1);
            this.outerTableLayoutPanel.Controls.Add(this.saveCancelPanel, 0, 3);
            this.outerTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.outerTableLayoutPanel.Name = "outerTableLayoutPanel";
            this.outerTableLayoutPanel.RowCount = 4;
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 49F));
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 130F));
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 47F));
            this.outerTableLayoutPanel.Size = new System.Drawing.Size(673, 394);
            this.outerTableLayoutPanel.TabIndex = 0;
            // 
            // AdjustmentsUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.outerTableLayoutPanel);
            this.Name = "AdjustmentsUI";
            this.Size = new System.Drawing.Size(676, 394);
            this.amountDetailsPanel.ResumeLayout(false);
            this.amountDetailsPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.saveCancelPanel.ResumeLayout(false);
            this.saveCancelPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid2)).EndInit();
            this.invoiceGridPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.adjustmentGrid)).EndInit();
            this.reasonPanel.ResumeLayout(false);
            this.reasonPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid1)).EndInit();
            this.outerTableLayoutPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel amountDetailsPanel;
        private System.Windows.Forms.Label applyAdjustmentToLabel;
        private ToolTip toolTip;
        private ErrorProvider errorProvider;
        private TableLayoutPanel outerTableLayoutPanel;
        private Panel reasonPanel;
        private PictureBox pictureBox2;
        private TextBox amountTextBox;
        private Label amountLabel;
        private PictureBox pictureBox1;
        private ComboBox typeComboBox;
        private Label typeLabel;
        private PictureBox requiredImg3;
        private PictureBox requiredImg2;
        private PictureBox requiredImg1;
        private Label restrictedFacilityLabel;
        private TextBox textBox1;
        private Label NoteLabel;
        private Base.View.Common.EIGDataGrid eigDataGrid1;
        private Label availableValueLabel;
        private Label appliedValueLabel;
        private Label adjustmentAmountValueLabel;
        private Label availableLabel;
        private Label appliedLabel;
        private Label adjustmentAmountLabel;
        private DateTimePicker dateTimePicker1;
        private ComboBox reasonComboBox;
        private Label datePanel;
        private Label reasonLabel;
        private Panel invoiceGridPanel;
        private Base.View.Common.EIGDataGrid adjustmentGrid;
        private Panel panel1;
        private Base.View.Common.EIGDataGrid eigDataGrid2;
        private Button applyButton;
        private Label lblApplyAdjustmentText;
        private Panel saveCancelPanel;
        private Label requiredLabel;
        private PictureBox requiredImg;
        private Button removeAdjustmentButton;
        private Button cancelButton;
        private Button saveButton;
        private Base.View.Common.EIGDataGrid eigDataGrid3;
    }
}
