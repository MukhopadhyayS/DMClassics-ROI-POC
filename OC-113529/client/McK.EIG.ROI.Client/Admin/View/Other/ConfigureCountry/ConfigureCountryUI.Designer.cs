namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureCountry
{
    partial class ConfigureCountryUI
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
            this.maskingLabel = new System.Windows.Forms.Label();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImage1 = new System.Windows.Forms.PictureBox();
            this.cancelButton = new System.Windows.Forms.Button();
            this.requiredImage2 = new System.Windows.Forms.PictureBox();
            this.ConfigureCountryComboBox = new System.Windows.Forms.ComboBox();
            this.defaultCountryLabel = new System.Windows.Forms.Label();
            this.controlsPanel = new System.Windows.Forms.Panel();
            this.saveButton = new System.Windows.Forms.Button();
            this.textFieldsPanel = new System.Windows.Forms.Panel();
            //this.ISOlabel = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage2)).BeginInit();
            this.controlsPanel.SuspendLayout();
            this.textFieldsPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // maskingLabel
            // 
            this.maskingLabel.AutoSize = true;
            this.maskingLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.maskingLabel.Location = new System.Drawing.Point(64, 7);
            this.maskingLabel.Name = "maskingLabel";
            this.maskingLabel.Size = new System.Drawing.Size(0, 15);
            this.maskingLabel.TabIndex = 2;
            // 
            // requiredLabel
            // 
            this.requiredLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(17, 30);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 1;
            // 
            // requiredImage1
            // 
            this.requiredImage1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImage1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImage1.ErrorImage = null;
            this.requiredImage1.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage1.Location = new System.Drawing.Point(3, 26);
            this.requiredImage1.Name = "requiredImage1";
            this.requiredImage1.Size = new System.Drawing.Size(12, 14);
            this.requiredImage1.TabIndex = 0;
            this.requiredImage1.TabStop = false;
            // 
            // cancelButton
            // 
            this.cancelButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(363, 21);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(91, 27);
            this.cancelButton.TabIndex = 2;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // requiredImage2
            // 
            this.requiredImage2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImage2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImage2.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage2.Location = new System.Drawing.Point(2, 30);
            this.requiredImage2.Name = "requiredImage2";
            this.requiredImage2.Size = new System.Drawing.Size(12, 14);
            this.requiredImage2.TabIndex = 0;
            this.requiredImage2.TabStop = false;
            // 
            // ConfigureCountryComboBox
            // 
            this.ConfigureCountryComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.ConfigureCountryComboBox.FormattingEnabled = true;
            this.ConfigureCountryComboBox.Location = new System.Drawing.Point(172, 23);
            this.ConfigureCountryComboBox.MaxDropDownItems = 20;
            this.ConfigureCountryComboBox.Name = "ConfigureCountryComboBox";
            this.ConfigureCountryComboBox.Size = new System.Drawing.Size(181, 21);
            this.ConfigureCountryComboBox.Sorted = true;
            this.ConfigureCountryComboBox.TabIndex = 3;
            this.ConfigureCountryComboBox.SelectedIndexChanged += new System.EventHandler(this.ConfigureCountryComboBox_SelectedIndexChanged);
            // 
            // defaultCountryLabel
            // 
            this.defaultCountryLabel.AutoSize = true;
            this.defaultCountryLabel.Location = new System.Drawing.Point(52, 25);
            this.defaultCountryLabel.Name = "defaultCountryLabel";
            this.defaultCountryLabel.Size = new System.Drawing.Size(0, 13);
            this.defaultCountryLabel.TabIndex = 4;
            // 
            // controlsPanel
            // 
            this.controlsPanel.Controls.Add(this.requiredLabel);
            this.controlsPanel.Controls.Add(this.cancelButton);
            this.controlsPanel.Controls.Add(this.requiredImage2);
            this.controlsPanel.Controls.Add(this.saveButton);
            this.controlsPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.controlsPanel.Location = new System.Drawing.Point(0, 473);
            this.controlsPanel.Name = "controlsPanel";
            this.controlsPanel.Size = new System.Drawing.Size(728, 50);
            this.controlsPanel.TabIndex = 10;
            // 
            // saveButton
            // 
            this.saveButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(267, 21);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(91, 27);
            this.saveButton.TabIndex = 1;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // textFieldsPanel
            // 
            this.textFieldsPanel.Controls.Add(this.defaultCountryLabel);
            this.textFieldsPanel.Controls.Add(this.ConfigureCountryComboBox);
            this.textFieldsPanel.Controls.Add(this.maskingLabel);
            this.textFieldsPanel.Controls.Add(this.requiredImage1);
            this.textFieldsPanel.Location = new System.Drawing.Point(-3, 0);
            this.textFieldsPanel.Name = "textFieldsPanel";
            this.textFieldsPanel.Size = new System.Drawing.Size(719, 47);
            this.textFieldsPanel.TabIndex = 9;

            // 
            // ConfigureCountryUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.ISOlabel);
            this.Controls.Add(this.controlsPanel);
            this.Controls.Add(this.textFieldsPanel);
            this.Name = "ConfigureCountryUI";
            this.Size = new System.Drawing.Size(728, 523);
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage2)).EndInit();
            this.controlsPanel.ResumeLayout(false);
            this.controlsPanel.PerformLayout();
            this.textFieldsPanel.ResumeLayout(false);
            this.textFieldsPanel.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label maskingLabel;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox requiredImage1;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.PictureBox requiredImage2;
        private System.Windows.Forms.ComboBox ConfigureCountryComboBox;
        private System.Windows.Forms.Label defaultCountryLabel;
        private System.Windows.Forms.Panel controlsPanel;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Panel textFieldsPanel;
        private System.Windows.Forms.Label ISOlabel;
    }
}
