namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    partial class ChangeRequestStatusUI
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
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.footerPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.img1 = new System.Windows.Forms.PictureBox();
            this.statusLabel = new System.Windows.Forms.Label();
            this.statusCombo = new System.Windows.Forms.ComboBox();
            this.reasonsGroupBox = new System.Windows.Forms.GroupBox();
            this.groupBoxRequiredImg = new System.Windows.Forms.PictureBox();
            this.addFreeFormReasonButton = new System.Windows.Forms.Button();
            this.reasonPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.reminderLabel = new System.Windows.Forms.Label();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.topPanel.SuspendLayout();
            this.footerPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).BeginInit();
            this.reasonsGroupBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.groupBoxRequiredImg)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // topPanel
            // 
            this.topPanel.Controls.Add(this.bottomPanel);
            this.topPanel.Location = new System.Drawing.Point(13, 8);
            this.topPanel.Name = "topPanel";
            this.topPanel.Size = new System.Drawing.Size(766, 59);
            this.topPanel.TabIndex = 0;
            // 
            // bottomPanel
            // 
            this.bottomPanel.Location = new System.Drawing.Point(0, 65);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(768, 267);
            this.bottomPanel.TabIndex = 1;
            // 
            // footerPanel
            // 
            this.footerPanel.Controls.Add(this.cancelButton);
            this.footerPanel.Controls.Add(this.saveButton);
            this.footerPanel.Controls.Add(this.requiredLabel);
            this.footerPanel.Controls.Add(this.requiredImg);
            this.footerPanel.Location = new System.Drawing.Point(-1, 366);
            this.footerPanel.Name = "footerPanel";
            this.footerPanel.Size = new System.Drawing.Size(782, 44);
            this.footerPanel.TabIndex = 0;
            // 
            // cancelButton
            // 
            this.cancelButton.Location = new System.Drawing.Point(374, 12);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 4;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // saveButton
            // 
            this.saveButton.Location = new System.Drawing.Point(293, 12);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 23);
            this.saveButton.TabIndex = 3;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(28, 14);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 17;
            // 
            // requiredImg
            // 
            this.requiredImg.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImg.Location = new System.Drawing.Point(13, 17);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(9, 10);
            this.requiredImg.TabIndex = 16;
            this.requiredImg.TabStop = false;
            // 
            // img1
            // 
            this.img1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img1.Location = new System.Drawing.Point(13, 101);
            this.img1.Name = "img1";
            this.img1.Size = new System.Drawing.Size(9, 10);
            this.img1.TabIndex = 48;
            this.img1.TabStop = false;
            // 
            // statusLabel
            // 
            this.statusLabel.AutoSize = true;
            this.statusLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.statusLabel.Location = new System.Drawing.Point(28, 98);
            this.statusLabel.Name = "statusLabel";
            this.statusLabel.Size = new System.Drawing.Size(0, 15);
            this.statusLabel.TabIndex = 49;
            // 
            // statusCombo
            // 
            this.statusCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.statusCombo.FormattingEnabled = true;
            this.statusCombo.Location = new System.Drawing.Point(100, 97);
            this.statusCombo.Name = "statusCombo";
            this.statusCombo.Size = new System.Drawing.Size(188, 23);
            this.statusCombo.TabIndex = 0;
            // 
            // reasonsGroupBox
            // 
            this.reasonsGroupBox.Controls.Add(this.groupBoxRequiredImg);
            this.reasonsGroupBox.Controls.Add(this.addFreeFormReasonButton);
            this.reasonsGroupBox.Controls.Add(this.reasonPanel);
            this.reasonsGroupBox.Location = new System.Drawing.Point(100, 136);
            this.reasonsGroupBox.Name = "reasonsGroupBox";
            this.reasonsGroupBox.Size = new System.Drawing.Size(442, 161);
            this.reasonsGroupBox.TabIndex = 1;
            this.reasonsGroupBox.TabStop = false;
            // 
            // groupBoxRequiredImg
            // 
            this.groupBoxRequiredImg.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.groupBoxRequiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.groupBoxRequiredImg.Location = new System.Drawing.Point(10, 2);
            this.groupBoxRequiredImg.Name = "groupBoxRequiredImg";
            this.groupBoxRequiredImg.Size = new System.Drawing.Size(9, 10);
            this.groupBoxRequiredImg.TabIndex = 52;
            this.groupBoxRequiredImg.TabStop = false;
            this.groupBoxRequiredImg.Visible = false;
            // 
            // addFreeFormReasonButton
            // 
            this.addFreeFormReasonButton.AutoSize = true;
            this.addFreeFormReasonButton.Location = new System.Drawing.Point(6, 132);
            this.addFreeFormReasonButton.Name = "addFreeFormReasonButton";
            this.addFreeFormReasonButton.Size = new System.Drawing.Size(123, 23);
            this.addFreeFormReasonButton.TabIndex = 2;
            this.addFreeFormReasonButton.UseVisualStyleBackColor = true;
            this.addFreeFormReasonButton.Click += new System.EventHandler(this.addFreeFormReasonButton_Click);
            // 
            // reasonPanel
            // 
            this.reasonPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)));
            this.reasonPanel.AutoScroll = true;
            this.reasonPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.reasonPanel.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.reasonPanel.Location = new System.Drawing.Point(6, 20);
            this.reasonPanel.Name = "reasonPanel";
            this.reasonPanel.Size = new System.Drawing.Size(430, 97);
            this.reasonPanel.TabIndex = 1;
            this.reasonPanel.WrapContents = false;
            // 
            // reminderLabel
            // 
            this.reminderLabel.AutoSize = true;
            this.reminderLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.reminderLabel.Location = new System.Drawing.Point(28, 326);
            this.reminderLabel.Name = "reminderLabel";
            this.reminderLabel.Size = new System.Drawing.Size(0, 15);
            this.reminderLabel.TabIndex = 52;
            this.reminderLabel.Visible = false;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // ChangeRequestStatusUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.reminderLabel);
            this.Controls.Add(this.reasonsGroupBox);
            this.Controls.Add(this.statusCombo);
            this.Controls.Add(this.statusLabel);
            this.Controls.Add(this.img1);
            this.Controls.Add(this.footerPanel);
            this.Controls.Add(this.topPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ChangeRequestStatusUI";
            this.Size = new System.Drawing.Size(784, 415);
            this.topPanel.ResumeLayout(false);
            this.footerPanel.ResumeLayout(false);
            this.footerPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).EndInit();
            this.reasonsGroupBox.ResumeLayout(false);
            this.reasonsGroupBox.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.groupBoxRequiredImg)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel topPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.Panel footerPanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.PictureBox img1;
        private System.Windows.Forms.Label statusLabel;
        private System.Windows.Forms.ComboBox statusCombo;
        private System.Windows.Forms.GroupBox reasonsGroupBox;
        private System.Windows.Forms.Button addFreeFormReasonButton;
        private System.Windows.Forms.FlowLayoutPanel reasonPanel;
        private System.Windows.Forms.PictureBox requiredImg;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox groupBoxRequiredImg;
        private System.Windows.Forms.Label reminderLabel;
        private System.Windows.Forms.ErrorProvider errorProvider;
    }
}
