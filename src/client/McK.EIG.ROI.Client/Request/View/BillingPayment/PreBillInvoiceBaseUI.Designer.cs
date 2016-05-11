namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class PreBillInvoiceBaseUI
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
            this.topPanel = new System.Windows.Forms.Panel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.cancelButton = new System.Windows.Forms.Button();
            this.continueButton = new System.Windows.Forms.Button();
            this.pictureBox3 = new System.Windows.Forms.PictureBox();
            this.templateLabel = new System.Windows.Forms.Label();
            this.notesLabel = new System.Windows.Forms.Label();
            this.createFreeformButton = new System.Windows.Forms.Button();
            this.notesPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.notesGroupPanel = new System.Windows.Forms.Panel();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.invoiceDueLabel = new System.Windows.Forms.Label();
            this.invoiceDueTextBox = new System.Windows.Forms.TextBox();
            this.daysLabel = new System.Windows.Forms.Label();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.templateComboBox = new System.Windows.Forms.ComboBox();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox3)).BeginInit();
            this.notesGroupPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.topPanel.ImeMode = System.Windows.Forms.ImeMode.Off;
            this.topPanel.Location = new System.Drawing.Point(6, 6);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(714, 54);
            this.topPanel.TabIndex = 0;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.requiredLabel);
            this.panel1.Controls.Add(this.pictureBox1);
            this.panel1.Controls.Add(this.cancelButton);
            this.panel1.Controls.Add(this.continueButton);
            this.panel1.Location = new System.Drawing.Point(6, 436);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(714, 41);
            this.panel1.TabIndex = 3;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(30, 12);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 51;
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pictureBox1.Location = new System.Drawing.Point(14, 15);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(9, 10);
            this.pictureBox1.TabIndex = 50;
            this.pictureBox1.TabStop = false;
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(369, 15);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 5;
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // continueButton
            // 
            this.continueButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.continueButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.continueButton.Location = new System.Drawing.Point(287, 15);
            this.continueButton.Name = "continueButton";
            this.continueButton.Size = new System.Drawing.Size(75, 23);
            this.continueButton.TabIndex = 4;
            this.continueButton.UseVisualStyleBackColor = true;
            this.continueButton.Click += new System.EventHandler(this.continueButton_Click);
            // 
            // pictureBox3
            // 
            this.pictureBox3.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox3.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pictureBox3.Location = new System.Drawing.Point(20, 73);
            this.pictureBox3.Name = "pictureBox3";
            this.pictureBox3.Size = new System.Drawing.Size(9, 10);
            this.pictureBox3.TabIndex = 49;
            this.pictureBox3.TabStop = false;
            // 
            // templateLabel
            // 
            this.templateLabel.AutoSize = true;
            this.templateLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.templateLabel.Location = new System.Drawing.Point(29, 104);
            this.templateLabel.Name = "templateLabel";
            this.templateLabel.Size = new System.Drawing.Size(0, 15);
            this.templateLabel.TabIndex = 50;
            // 
            // notesLabel
            // 
            this.notesLabel.AutoSize = true;
            this.notesLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.notesLabel.Location = new System.Drawing.Point(23, 7);
            this.notesLabel.Name = "notesLabel";
            this.notesLabel.Size = new System.Drawing.Size(0, 15);
            this.notesLabel.TabIndex = 52;
            // 
            // createFreeformButton
            // 
            this.createFreeformButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.createFreeformButton.ForeColor = System.Drawing.SystemColors.ControlText;
            this.createFreeformButton.Location = new System.Drawing.Point(91, 265);
            this.createFreeformButton.Name = "createFreeformButton";
            this.createFreeformButton.Size = new System.Drawing.Size(138, 23);
            this.createFreeformButton.TabIndex = 2;
            this.createFreeformButton.UseVisualStyleBackColor = true;
            this.createFreeformButton.Click += new System.EventHandler(this.createFreeformButton_Click);
            // 
            // notesPanel
            // 
            this.notesPanel.AutoScroll = true;
            this.notesPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.notesPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.notesPanel.ForeColor = System.Drawing.SystemColors.ControlText;
            this.notesPanel.Location = new System.Drawing.Point(91, 7);
            this.notesPanel.Name = "notesPanel";
            this.notesPanel.Size = new System.Drawing.Size(620, 252);
            this.notesPanel.TabIndex = 1;
            // 
            // notesGroupPanel
            // 
            this.notesGroupPanel.Controls.Add(this.createFreeformButton);
            this.notesGroupPanel.Controls.Add(this.notesLabel);
            this.notesGroupPanel.Controls.Add(this.notesPanel);
            this.notesGroupPanel.Location = new System.Drawing.Point(6, 131);
            this.notesGroupPanel.Name = "notesGroupPanel";
            this.notesGroupPanel.Size = new System.Drawing.Size(714, 300);
            this.notesGroupPanel.TabIndex = 1;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // invoiceDueLabel
            // 
            this.invoiceDueLabel.AutoSize = true;
            this.invoiceDueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.invoiceDueLabel.Location = new System.Drawing.Point(29, 71);
            this.invoiceDueLabel.Name = "invoiceDueLabel";
            this.invoiceDueLabel.Size = new System.Drawing.Size(0, 15);
            this.invoiceDueLabel.TabIndex = 52;
            // 
            // invoiceDueTextBox
            // 
            this.invoiceDueTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.invoiceDueTextBox.Location = new System.Drawing.Point(100, 69);
            this.invoiceDueTextBox.MaxLength = 3;
            this.invoiceDueTextBox.Name = "invoiceDueTextBox";
            this.invoiceDueTextBox.Size = new System.Drawing.Size(58, 21);
            this.invoiceDueTextBox.TabIndex = 53;
            this.invoiceDueTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.invoiceDueTextBox_KeyPress);
            // 
            // daysLabel
            // 
            this.daysLabel.AutoSize = true;
            this.daysLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.daysLabel.Location = new System.Drawing.Point(175, 74);
            this.daysLabel.Name = "daysLabel";
            this.daysLabel.Size = new System.Drawing.Size(0, 15);
            this.daysLabel.TabIndex = 54;
            // 
            // pictureBox2
            // 
            this.pictureBox2.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pictureBox2.Location = new System.Drawing.Point(20, 106);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(9, 10);
            this.pictureBox2.TabIndex = 55;
            this.pictureBox2.TabStop = false;
            // 
            // templateComboBox
            // 
            this.templateComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.templateComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.templateComboBox.FormattingEnabled = true;
            this.templateComboBox.Location = new System.Drawing.Point(100, 102);
            this.templateComboBox.Name = "templateComboBox";
            this.templateComboBox.Size = new System.Drawing.Size(188, 23);
            this.templateComboBox.TabIndex = 0;
            // 
            // PreBillInvoiceBaseUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.pictureBox2);
            this.Controls.Add(this.daysLabel);
            this.Controls.Add(this.invoiceDueTextBox);
            this.Controls.Add(this.invoiceDueLabel);
            this.Controls.Add(this.notesGroupPanel);
            this.Controls.Add(this.templateComboBox);
            this.Controls.Add(this.templateLabel);
            this.Controls.Add(this.pictureBox3);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "PreBillInvoiceBaseUI";
            this.Size = new System.Drawing.Size(746, 484);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox3)).EndInit();
            this.notesGroupPanel.ResumeLayout(false);
            this.notesGroupPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.PictureBox pictureBox3;
        private System.Windows.Forms.Label templateLabel;
        private System.Windows.Forms.Label notesLabel;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button continueButton;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.Button createFreeformButton;
        private System.Windows.Forms.FlowLayoutPanel notesPanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel notesGroupPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.Label invoiceDueLabel;
        private System.Windows.Forms.TextBox invoiceDueTextBox;
        private System.Windows.Forms.Label daysLabel;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.ComboBox templateComboBox;
    }
}
