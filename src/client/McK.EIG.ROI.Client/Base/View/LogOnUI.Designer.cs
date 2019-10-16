namespace McK.EIG.ROI.Client.Base.View
{
    partial class LogOnUI
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

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.changePasswordButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.logonButton = new System.Windows.Forms.Button();
            this.secretWordTextBox = new System.Windows.Forms.TextBox();
            this.userIdTextBox = new System.Windows.Forms.TextBox();
            this.passwordLabel = new System.Windows.Forms.Label();
            this.userIdLabel = new System.Windows.Forms.Label();
            this.outerPanel = new System.Windows.Forms.Panel();
            this.outerWhitePanel = new System.Windows.Forms.Panel();
            this.flowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.lockPictureBox = new System.Windows.Forms.PictureBox();
            this.panel3 = new System.Windows.Forms.Panel();
            this.domainComboBox = new System.Windows.Forms.ComboBox();
            this.domainLabel = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.pictureBox3 = new System.Windows.Forms.PictureBox();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.headerLabel = new System.Windows.Forms.Label();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.img1 = new System.Windows.Forms.PictureBox();
            this.outerPanel.SuspendLayout();
            this.outerWhitePanel.SuspendLayout();
            this.flowLayoutPanel.SuspendLayout();
            this.panel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.lockPictureBox)).BeginInit();
            this.panel3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).BeginInit();
            this.SuspendLayout();
            // 
            // changePasswordButton
            // 
            this.changePasswordButton.Location = new System.Drawing.Point(268, 209);
            this.changePasswordButton.Name = "changePasswordButton";
            this.changePasswordButton.Size = new System.Drawing.Size(139, 27);
            this.changePasswordButton.TabIndex = 4;
            this.changePasswordButton.UseVisualStyleBackColor = true;
            this.changePasswordButton.Visible = false;
            // 
            // cancelButton
            // 
            this.cancelButton.Location = new System.Drawing.Point(413, 209);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(87, 27);
            this.cancelButton.TabIndex = 5;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // logonButton
            // 
            this.logonButton.Location = new System.Drawing.Point(175, 209);
            this.logonButton.Name = "logonButton";
            this.logonButton.Size = new System.Drawing.Size(87, 27);
            this.logonButton.TabIndex = 3;
            this.logonButton.UseVisualStyleBackColor = true;
            // 
            // secretWordTextBox
            // 
            this.secretWordTextBox.AccessibleName = "secretWordTextBox";
            this.secretWordTextBox.Location = new System.Drawing.Point(130, 68);
            this.secretWordTextBox.MaxLength = 32;
            this.secretWordTextBox.Name = "secretWordTextBox";
            this.secretWordTextBox.PasswordChar = '*';
            this.secretWordTextBox.Size = new System.Drawing.Size(210, 21);
            this.secretWordTextBox.TabIndex = 2;
            // 
            // userIdTextBox
            // 
            this.userIdTextBox.AccessibleName = "userIdTextBox";
            this.userIdTextBox.Location = new System.Drawing.Point(130, 35);
            this.userIdTextBox.MaxLength = 32;
            this.userIdTextBox.Name = "userIdTextBox";
            this.userIdTextBox.Size = new System.Drawing.Size(210, 21);
            this.userIdTextBox.TabIndex = 1;
            // 
            // passwordLabel
            // 
            this.passwordLabel.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.passwordLabel.Location = new System.Drawing.Point(29, 71);
            this.passwordLabel.Name = "passwordLabel";
            this.passwordLabel.Size = new System.Drawing.Size(95, 16);
            this.passwordLabel.TabIndex = 1;
            this.passwordLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // userIdLabel
            // 
            this.userIdLabel.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.userIdLabel.Location = new System.Drawing.Point(29, 40);
            this.userIdLabel.Name = "userIdLabel";
            this.userIdLabel.Size = new System.Drawing.Size(95, 16);
            this.userIdLabel.TabIndex = 0;
            this.userIdLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // outerPanel
            // 
            this.outerPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.outerPanel.Controls.Add(this.outerWhitePanel);
            this.outerPanel.Location = new System.Drawing.Point(14, 13);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.Size = new System.Drawing.Size(476, 176);
            this.outerPanel.TabIndex = 1;
            // 
            // outerWhitePanel
            // 
            this.outerWhitePanel.BackColor = System.Drawing.Color.White;
            this.outerWhitePanel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.outerWhitePanel.Controls.Add(this.flowLayoutPanel);
            this.outerWhitePanel.Location = new System.Drawing.Point(3, 3);
            this.outerWhitePanel.Name = "outerWhitePanel";
            this.outerWhitePanel.Size = new System.Drawing.Size(468, 167);
            this.outerWhitePanel.TabIndex = 0;
            // 
            // flowLayoutPanel
            // 
            this.flowLayoutPanel.Controls.Add(this.panel2);
            this.flowLayoutPanel.Controls.Add(this.panel3);
            this.flowLayoutPanel.Location = new System.Drawing.Point(3, 9);
            this.flowLayoutPanel.Name = "flowLayoutPanel";
            this.flowLayoutPanel.Size = new System.Drawing.Size(459, 150);
            this.flowLayoutPanel.TabIndex = 53;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.lockPictureBox);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Left;
            this.panel2.Location = new System.Drawing.Point(3, 3);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(89, 140);
            this.panel2.TabIndex = 0;
            // 
            // lockPictureBox
            // 
            this.lockPictureBox.Location = new System.Drawing.Point(5, 27);
            this.lockPictureBox.Name = "lockPictureBox";
            this.lockPictureBox.Size = new System.Drawing.Size(79, 76);
            this.lockPictureBox.TabIndex = 0;
            this.lockPictureBox.TabStop = false;
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.domainComboBox);
            this.panel3.Controls.Add(this.userIdLabel);
            this.panel3.Controls.Add(this.domainLabel);
            this.panel3.Controls.Add(this.secretWordTextBox);
            this.panel3.Controls.Add(this.pictureBox1);
            this.panel3.Controls.Add(this.passwordLabel);
            this.panel3.Controls.Add(this.pictureBox3);
            this.panel3.Controls.Add(this.userIdTextBox);
            this.panel3.Controls.Add(this.pictureBox2);
            this.panel3.Controls.Add(this.headerLabel);
            this.panel3.Location = new System.Drawing.Point(98, 3);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(356, 140);
            this.panel3.TabIndex = 53;
            // 
            // domainComboBox
            // 
            this.domainComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.domainComboBox.FormattingEnabled = true;
            this.domainComboBox.Location = new System.Drawing.Point(130, 103);
            this.domainComboBox.Name = "domainComboBox";
            this.domainComboBox.Size = new System.Drawing.Size(210, 23);
            this.domainComboBox.TabIndex = 57;
            // 
            // domainLabel
            // 
            this.domainLabel.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.domainLabel.Location = new System.Drawing.Point(29, 105);
            this.domainLabel.Name = "domainLabel";
            this.domainLabel.Size = new System.Drawing.Size(95, 16);
            this.domainLabel.TabIndex = 56;
            this.domainLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pictureBox1.Location = new System.Drawing.Point(6, 103);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(10, 12);
            this.pictureBox1.TabIndex = 55;
            this.pictureBox1.TabStop = false;
            // 
            // pictureBox3
            // 
            this.pictureBox3.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox3.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pictureBox3.Location = new System.Drawing.Point(6, 74);
            this.pictureBox3.Name = "pictureBox3";
            this.pictureBox3.Size = new System.Drawing.Size(10, 12);
            this.pictureBox3.TabIndex = 53;
            this.pictureBox3.TabStop = false;
            // 
            // pictureBox2
            // 
            this.pictureBox2.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.pictureBox2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.pictureBox2.Location = new System.Drawing.Point(6, 44);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(10, 12);
            this.pictureBox2.TabIndex = 52;
            this.pictureBox2.TabStop = false;
            // 
            // headerLabel
            // 
            this.headerLabel.AutoSize = true;
            this.headerLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.headerLabel.Location = new System.Drawing.Point(11, 11);
            this.headerLabel.Name = "headerLabel";
            this.headerLabel.Size = new System.Drawing.Size(0, 15);
            this.headerLabel.TabIndex = 1;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(33, 215);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 52;
            // 
            // img1
            // 
            this.img1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img1.Location = new System.Drawing.Point(17, 218);
            this.img1.Name = "img1";
            this.img1.Size = new System.Drawing.Size(10, 12);
            this.img1.TabIndex = 51;
            this.img1.TabStop = false;
            // 
            // LogOnUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(246)))), ((int)(((byte)(246)))));
            this.Controls.Add(this.requiredLabel);
            this.Controls.Add(this.img1);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.changePasswordButton);
            this.Controls.Add(this.outerPanel);
            this.Controls.Add(this.logonButton);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "LogOnUI";
            this.Size = new System.Drawing.Size(522, 288);
            this.outerPanel.ResumeLayout(false);
            this.outerWhitePanel.ResumeLayout(false);
            this.flowLayoutPanel.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.lockPictureBox)).EndInit();
            this.panel3.ResumeLayout(false);
            this.panel3.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button logonButton;
        private System.Windows.Forms.TextBox secretWordTextBox;
        private System.Windows.Forms.TextBox userIdTextBox;
        private System.Windows.Forms.Label passwordLabel;
        private System.Windows.Forms.Label userIdLabel;
        private System.Windows.Forms.Button changePasswordButton;
        private System.Windows.Forms.Panel outerPanel;
        private System.Windows.Forms.Panel outerWhitePanel;
        private System.Windows.Forms.Label headerLabel;
        private System.Windows.Forms.PictureBox lockPictureBox;
        private System.Windows.Forms.PictureBox img1;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox pictureBox3;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.Label domainLabel;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.ComboBox domainComboBox;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Panel panel3;
    }
}