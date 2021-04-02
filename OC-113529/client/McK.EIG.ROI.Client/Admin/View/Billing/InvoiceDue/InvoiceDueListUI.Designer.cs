namespace McK.EIG.ROI.Client.Admin.View.Billing.InvoiceDue
{
    partial class InvoiceDueListUI
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
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.requiredImage2 = new System.Windows.Forms.PictureBox();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.saveButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.textFieldsPanel = new System.Windows.Forms.Panel();
            this.daysLabel = new System.Windows.Forms.Label();
            this.customDateLabel = new System.Windows.Forms.Label();
            this.customDateTextBox = new System.Windows.Forms.TextBox();
            this.dueDaysComboBox = new System.Windows.Forms.ComboBox();
            this.dueDateLabel = new System.Windows.Forms.Label();
            this.requiredImage1 = new System.Windows.Forms.PictureBox();
            this.controlsPanel = new System.Windows.Forms.Panel();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage2)).BeginInit();
            this.textFieldsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage1)).BeginInit();
            this.controlsPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkRate = 0;
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // requiredImage2
            // 
            this.requiredImage2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImage2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImage2.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage2.Location = new System.Drawing.Point(2, 26);
            this.requiredImage2.Name = "requiredImage2";
            this.requiredImage2.Size = new System.Drawing.Size(10, 12);
            this.requiredImage2.TabIndex = 0;
            this.requiredImage2.TabStop = false;
            // 
            // requiredLabel
            // 
            this.requiredLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(15, 26);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 1;
            // 
            // saveButton
            // 
            this.saveButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(260, 18);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(78, 23);
            this.saveButton.TabIndex = 2;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(342, 18);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(78, 23);
            this.cancelButton.TabIndex = 3;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // textFieldsPanel
            // 
            this.textFieldsPanel.Controls.Add(this.daysLabel);
            this.textFieldsPanel.Controls.Add(this.customDateLabel);
            this.textFieldsPanel.Controls.Add(this.customDateTextBox);
            this.textFieldsPanel.Controls.Add(this.dueDaysComboBox);
            this.textFieldsPanel.Controls.Add(this.dueDateLabel);
            this.textFieldsPanel.Controls.Add(this.requiredImage1);
            this.textFieldsPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.textFieldsPanel.Location = new System.Drawing.Point(0, 0);
            this.textFieldsPanel.Name = "textFieldsPanel";
            this.textFieldsPanel.Size = new System.Drawing.Size(687, 65);
            this.textFieldsPanel.TabIndex = 1;
            // 
            // daysLabel
            // 
            this.daysLabel.AutoSize = true;
            this.daysLabel.Location = new System.Drawing.Point(173, 32);
            this.daysLabel.Name = "daysLabel";
            this.daysLabel.Size = new System.Drawing.Size(0, 15);
            this.daysLabel.TabIndex = 6;
            // 
            // customDateLabel
            // 
            this.customDateLabel.AutoSize = true;
            this.customDateLabel.Location = new System.Drawing.Point(173, 5);
            this.customDateLabel.Name = "customDateLabel";
            this.customDateLabel.Size = new System.Drawing.Size(0, 15);
            this.customDateLabel.TabIndex = 5;
            // 
            // customDateTextBox
            // 
            this.customDateTextBox.Location = new System.Drawing.Point(96, 29);
            this.customDateTextBox.MaxLength = 3;
            this.customDateTextBox.Name = "customDateTextBox";
            this.customDateTextBox.Size = new System.Drawing.Size(70, 21);
            this.customDateTextBox.TabIndex = 4;
            this.customDateTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.customDateTextField_KeyPress);
            // 
            // dueDaysComboBox
            // 
            this.dueDaysComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.dueDaysComboBox.FormattingEnabled = true;
            this.dueDaysComboBox.Items.AddRange(new object[] {
            "30"});
            this.dueDaysComboBox.Location = new System.Drawing.Point(96, 0);
            this.dueDaysComboBox.Name = "dueDaysComboBox";
            this.dueDaysComboBox.Size = new System.Drawing.Size(70, 23);
            this.dueDaysComboBox.TabIndex = 3;
            // 
            // dueDateLabel
            // 
            this.dueDateLabel.AutoSize = true;
            this.dueDateLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dueDateLabel.Location = new System.Drawing.Point(22, 3);
            this.dueDateLabel.Name = "dueDateLabel";
            this.dueDateLabel.Size = new System.Drawing.Size(0, 15);
            this.dueDateLabel.TabIndex = 2;
            // 
            // requiredImage1
            // 
            this.requiredImage1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImage1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImage1.ErrorImage = null;
            this.requiredImage1.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage1.Location = new System.Drawing.Point(6, 6);
            this.requiredImage1.Name = "requiredImage1";
            this.requiredImage1.Size = new System.Drawing.Size(10, 12);
            this.requiredImage1.TabIndex = 0;
            this.requiredImage1.TabStop = false;
            // 
            // controlsPanel
            // 
            this.controlsPanel.Controls.Add(this.requiredLabel);
            this.controlsPanel.Controls.Add(this.cancelButton);
            this.controlsPanel.Controls.Add(this.requiredImage2);
            this.controlsPanel.Controls.Add(this.saveButton);
            this.controlsPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.controlsPanel.Location = new System.Drawing.Point(0, 71);
            this.controlsPanel.Name = "controlsPanel";
            this.controlsPanel.Size = new System.Drawing.Size(687, 43);
            this.controlsPanel.TabIndex = 4;
            // 
            // InvoiceDueListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.controlsPanel);
            this.Controls.Add(this.textFieldsPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "InvoiceDueListUI";
            this.Size = new System.Drawing.Size(687, 114);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage2)).EndInit();
            this.textFieldsPanel.ResumeLayout(false);
            this.textFieldsPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage1)).EndInit();
            this.controlsPanel.ResumeLayout(false);
            this.controlsPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel textFieldsPanel;
        private System.Windows.Forms.Label dueDateLabel;
        private System.Windows.Forms.PictureBox requiredImage1;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox requiredImage2;
        private System.Windows.Forms.Panel controlsPanel;
        private System.Windows.Forms.ComboBox dueDaysComboBox;
        private System.Windows.Forms.Label customDateLabel;
        private System.Windows.Forms.TextBox customDateTextBox;
        private System.Windows.Forms.Label daysLabel;

    }
}
