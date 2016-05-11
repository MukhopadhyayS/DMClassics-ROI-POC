namespace McK.EIG.ROI.Client.Request.View.Letters
{
    partial class LetterInfoUI
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
            this.outerPanel = new System.Windows.Forms.Panel();
            this.notesGroupPanel = new System.Windows.Forms.Panel();
            this.notesPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.notesHeaderLabel = new System.Windows.Forms.Label();
            this.createFreeformButton = new System.Windows.Forms.Button();
            this.templateComboBox = new System.Windows.Forms.ComboBox();
            this.letterTemplateLabel = new System.Windows.Forms.Label();
            this.img1 = new System.Windows.Forms.PictureBox();
            this.coverLetterRadioButton = new System.Windows.Forms.RadioButton();
            this.otherRadioButton = new System.Windows.Forms.RadioButton();
            this.letterTypeLabel = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.outerPanel.SuspendLayout();
            this.notesGroupPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // outerPanel
            // 
            this.outerPanel.BackColor = System.Drawing.Color.White;
            this.outerPanel.Controls.Add(this.notesGroupPanel);
            this.outerPanel.Controls.Add(this.templateComboBox);
            this.outerPanel.Controls.Add(this.letterTemplateLabel);
            this.outerPanel.Controls.Add(this.img1);
            this.outerPanel.Controls.Add(this.coverLetterRadioButton);
            this.outerPanel.Controls.Add(this.otherRadioButton);
            this.outerPanel.Controls.Add(this.letterTypeLabel);
            this.outerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.outerPanel.Location = new System.Drawing.Point(0, 0);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.Size = new System.Drawing.Size(746, 444);
            this.outerPanel.TabIndex = 0;
            // 
            // notesGroupPanel
            // 
            this.notesGroupPanel.Controls.Add(this.notesPanel);
            this.notesGroupPanel.Controls.Add(this.notesHeaderLabel);
            this.notesGroupPanel.Controls.Add(this.createFreeformButton);
            this.notesGroupPanel.Location = new System.Drawing.Point(19, 87);
            this.notesGroupPanel.Name = "notesGroupPanel";
            this.notesGroupPanel.Size = new System.Drawing.Size(639, 277);
            this.notesGroupPanel.TabIndex = 3;
            // 
            // notesPanel
            // 
            this.notesPanel.AutoScroll = true;
            this.notesPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;            
            this.notesPanel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.notesPanel.Location = new System.Drawing.Point(3, 25);
            this.notesPanel.Name = "notesPanel";
            this.notesPanel.Size = new System.Drawing.Size(620, 207);
            this.notesPanel.TabIndex = 4;
            // 
            // notesHeaderLabel
            // 
            this.notesHeaderLabel.AutoSize = true;
            this.notesHeaderLabel.Location = new System.Drawing.Point(3, 9);
            this.notesHeaderLabel.Name = "notesHeaderLabel";
            this.notesHeaderLabel.Size = new System.Drawing.Size(0, 13);
            this.notesHeaderLabel.TabIndex = 53;
            // 
            // createFreeformButton
            // 
            this.createFreeformButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.createFreeformButton.Location = new System.Drawing.Point(0, 238);
            this.createFreeformButton.Name = "createFreeformButton";
            this.createFreeformButton.Size = new System.Drawing.Size(138, 23);
            this.createFreeformButton.TabIndex = 5;
            this.createFreeformButton.UseVisualStyleBackColor = true;
            // 
            // templateComboBox
            // 
            this.templateComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.templateComboBox.FormattingEnabled = true;
            this.templateComboBox.Location = new System.Drawing.Point(120, 51);
            this.templateComboBox.Name = "templateComboBox";
            this.templateComboBox.Size = new System.Drawing.Size(315, 21);
            this.templateComboBox.TabIndex = 2;
            // 
            // letterTemplateLabel
            // 
            this.letterTemplateLabel.AutoSize = true;
            this.letterTemplateLabel.Location = new System.Drawing.Point(34, 54);
            this.letterTemplateLabel.Name = "letterTemplateLabel";
            this.letterTemplateLabel.Size = new System.Drawing.Size(0, 13);
            this.letterTemplateLabel.TabIndex = 51;
            // 
            // img1
            // 
            this.img1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img1.Location = new System.Drawing.Point(19, 55);
            this.img1.Name = "img1";
            this.img1.Size = new System.Drawing.Size(9, 10);
            this.img1.TabIndex = 50;
            this.img1.TabStop = false;
            // 
            // coverLetterRadioButton
            // 
            this.coverLetterRadioButton.AutoSize = true;
            this.coverLetterRadioButton.Location = new System.Drawing.Point(186, 21);
            this.coverLetterRadioButton.Name = "coverLetterRadioButton";
            this.coverLetterRadioButton.Size = new System.Drawing.Size(14, 13);
            this.coverLetterRadioButton.TabIndex = 1;
            this.coverLetterRadioButton.UseVisualStyleBackColor = true;
            // 
            // otherRadioButton
            // 
            this.otherRadioButton.AutoSize = true;
            this.otherRadioButton.Checked = true;
            this.otherRadioButton.Location = new System.Drawing.Point(120, 21);
            this.otherRadioButton.Name = "otherRadioButton";
            this.otherRadioButton.Size = new System.Drawing.Size(14, 13);
            this.otherRadioButton.TabIndex = 0;
            this.otherRadioButton.TabStop = true;
            this.otherRadioButton.UseVisualStyleBackColor = true;
            // 
            // letterTypeLabel
            // 
            this.letterTypeLabel.AutoSize = true;
            this.letterTypeLabel.Location = new System.Drawing.Point(16, 23);
            this.letterTypeLabel.Name = "letterTypeLabel";
            this.letterTypeLabel.Size = new System.Drawing.Size(0, 13);
            this.letterTypeLabel.TabIndex = 0;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // LetterInfoUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.outerPanel);
            this.Name = "LetterInfoUI";
            this.Size = new System.Drawing.Size(746, 444);
            this.outerPanel.ResumeLayout(false);
            this.outerPanel.PerformLayout();
            this.notesGroupPanel.ResumeLayout(false);
            this.notesGroupPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel outerPanel;
        private System.Windows.Forms.RadioButton coverLetterRadioButton;
        private System.Windows.Forms.RadioButton otherRadioButton;
        private System.Windows.Forms.Label letterTypeLabel;
        private System.Windows.Forms.PictureBox img1;
        private System.Windows.Forms.Label notesHeaderLabel;
        private System.Windows.Forms.ComboBox templateComboBox;
        private System.Windows.Forms.Label letterTemplateLabel;
        private System.Windows.Forms.Button createFreeformButton;
        private System.Windows.Forms.FlowLayoutPanel notesPanel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel notesGroupPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
    }
}
