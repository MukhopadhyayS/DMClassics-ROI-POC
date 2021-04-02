namespace McK.EIG.ROI.Client.Request.View.PatientInfo
{
    partial class ModifyBillingTierUI
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
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.modifyBillingTierLabel = new System.Windows.Forms.Label();
            this.billingTierComboBox = new System.Windows.Forms.ComboBox();
            this.SuspendLayout();
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Location = new System.Drawing.Point(141, 118);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 7;
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // saveButton
            // 
            this.saveButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.saveButton.Location = new System.Drawing.Point(60, 118);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 23);
            this.saveButton.TabIndex = 6;
            this.saveButton.UseVisualStyleBackColor = true;
            // 
            // modifyBillingTierLabel
            // 
            this.modifyBillingTierLabel.AutoSize = true;
            this.modifyBillingTierLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.modifyBillingTierLabel.Location = new System.Drawing.Point(29, 27);
            this.modifyBillingTierLabel.Name = "modifyBillingTierLabel";
            this.modifyBillingTierLabel.Size = new System.Drawing.Size(0, 15);
            this.modifyBillingTierLabel.TabIndex = 5;
            this.modifyBillingTierLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // billingTierComboBox
            // 
            this.billingTierComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.billingTierComboBox.FormattingEnabled = true;
            this.billingTierComboBox.Location = new System.Drawing.Point(30, 47);
            this.billingTierComboBox.Name = "billingTierComboBox";
            this.billingTierComboBox.Size = new System.Drawing.Size(223, 23);
            this.billingTierComboBox.TabIndex = 4;
            this.billingTierComboBox.SelectedIndexChanged += new System.EventHandler(this.billingTierComboBox_SelectedIndexChanged);
            // 
            // ModifyBillingTierUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.saveButton);
            this.Controls.Add(this.modifyBillingTierLabel);
            this.Controls.Add(this.billingTierComboBox);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ModifyBillingTierUI";
            this.Size = new System.Drawing.Size(288, 163);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Label modifyBillingTierLabel;
        private System.Windows.Forms.ComboBox billingTierComboBox;
    }
}
