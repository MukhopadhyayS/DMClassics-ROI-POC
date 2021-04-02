namespace McK.EIG.ROI.Client.Admin.View.Other.ConfigureUnbillableRequest
{
    partial class ConfigureUnbillableRequestUI
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
            this.controlsPanel = new System.Windows.Forms.Panel();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.requiredImage2 = new System.Windows.Forms.PictureBox();
            this.saveButton = new System.Windows.Forms.Button();
            this.textFieldsPanel = new System.Windows.Forms.Panel();
            this.unbillableButton = new System.Windows.Forms.RadioButton();
            this.billableButton = new System.Windows.Forms.RadioButton();
            this.requiredImage1 = new System.Windows.Forms.PictureBox();
            this.controlsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage2)).BeginInit();
            this.textFieldsPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage1)).BeginInit();
            this.SuspendLayout();
            // 
            // controlsPanel
            // 
            this.controlsPanel.Controls.Add(this.requiredLabel);
            this.controlsPanel.Controls.Add(this.cancelButton);
            this.controlsPanel.Controls.Add(this.requiredImage2);
            this.controlsPanel.Controls.Add(this.saveButton);
            this.controlsPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.controlsPanel.Location = new System.Drawing.Point(0, 63);
            this.controlsPanel.Name = "controlsPanel";
            this.controlsPanel.Size = new System.Drawing.Size(941, 50);
            this.controlsPanel.TabIndex = 6;
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
            // cancelButton
            // 
            this.cancelButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.cancelButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(469, 21);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(91, 27);
            this.cancelButton.TabIndex = 2;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.btnCancel_Click);
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
            // saveButton
            // 
            this.saveButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(373, 21);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(91, 27);
            this.saveButton.TabIndex = 1;
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // textFieldsPanel
            // 
            this.textFieldsPanel.Controls.Add(this.unbillableButton);
            this.textFieldsPanel.Controls.Add(this.billableButton);
            this.textFieldsPanel.Controls.Add(this.requiredImage1);
            this.textFieldsPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.textFieldsPanel.Location = new System.Drawing.Point(0, 0);
            this.textFieldsPanel.Name = "textFieldsPanel";
            this.textFieldsPanel.Size = new System.Drawing.Size(941, 47);
            this.textFieldsPanel.TabIndex = 5;
            // 
            // unbillableButton
            // 
            this.unbillableButton.AutoSize = true;
            this.unbillableButton.Location = new System.Drawing.Point(92, 7);
            this.unbillableButton.Name = "unbillableButton";
            this.unbillableButton.Size = new System.Drawing.Size(81, 19);
            this.unbillableButton.TabIndex = 4;
            this.unbillableButton.TabStop = true;
            this.unbillableButton.Text = "Unbillable";
            this.unbillableButton.UseVisualStyleBackColor = true;
            // 
            // billableButton
            // 
            this.billableButton.AutoSize = true;
            this.billableButton.Location = new System.Drawing.Point(20, 7);
            this.billableButton.Name = "billableButton";
            this.billableButton.Size = new System.Drawing.Size(66, 19);
            this.billableButton.TabIndex = 3;
            this.billableButton.TabStop = true;
            this.billableButton.Text = "Billable";
            this.billableButton.UseVisualStyleBackColor = true;
            // 
            // requiredImage1
            // 
            this.requiredImage1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImage1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImage1.ErrorImage = null;
            this.requiredImage1.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage1.Location = new System.Drawing.Point(2, 7);
            this.requiredImage1.Name = "requiredImage1";
            this.requiredImage1.Size = new System.Drawing.Size(12, 14);
            this.requiredImage1.TabIndex = 0;
            this.requiredImage1.TabStop = false;
            // 
            // ConfigureUnbillableRequestUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.controlsPanel);
            this.Controls.Add(this.textFieldsPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ConfigureUnbillableRequestUI";
            this.Size = new System.Drawing.Size(941, 113);
            this.controlsPanel.ResumeLayout(false);
            this.controlsPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage2)).EndInit();
            this.textFieldsPanel.ResumeLayout(false);
            this.textFieldsPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel controlsPanel;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.PictureBox requiredImage2;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Panel textFieldsPanel;
        //private System.Windows.Forms.Label configuredefaultunbillablerequestLabel;
        private System.Windows.Forms.PictureBox requiredImage1;
        //private System.Windows.Forms.CheckBox configureunbillablerequestCheckBox;
        private System.Windows.Forms.RadioButton billableButton;
        private System.Windows.Forms.RadioButton unbillableButton;
    }
}
