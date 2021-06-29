using McK.EIG.ROI.Client.Base.Model;
namespace McK.EIG.ROI.Client.Patient.View.PatientInfo
{
    partial class PatientInfoActionUI
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.img1 = new System.Windows.Forms.PictureBox();
            this.actionPanel = new System.Windows.Forms.TableLayoutPanel();
            this.buttonPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.deletePatientButton = new System.Windows.Forms.Button();
            this.revertButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).BeginInit();
            this.actionPanel.SuspendLayout();
            this.buttonPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.panel1.Controls.Add(this.requiredLabel);
            this.panel1.Controls.Add(this.img1);
            this.panel1.Location = new System.Drawing.Point(3, 5);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(105, 29);
            this.panel1.TabIndex = 1;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(21, 8);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 16;
            // 
            // img1
            // 
            this.img1.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.img1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.img1.Location = new System.Drawing.Point(6, 9);
            this.img1.Name = "img1";
            this.img1.Size = new System.Drawing.Size(9, 10);
            this.img1.TabIndex = 15;
            this.img1.TabStop = false;
            // 
            // actionPanel
            // 
            this.actionPanel.ColumnCount = 2;
            this.actionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 20.36364F));
            this.actionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 79.63636F));
            this.actionPanel.Controls.Add(this.panel1, 0, 0);
            this.actionPanel.Controls.Add(this.buttonPanel, 1, 0);
            this.actionPanel.Location = new System.Drawing.Point(0, 0);
            this.actionPanel.Name = "actionPanel";
            this.actionPanel.RowCount = 1;
            this.actionPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.actionPanel.Size = new System.Drawing.Size(550, 37);
            this.actionPanel.TabIndex = 2;
            // 
            // buttonPanel
            // 
            this.buttonPanel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.buttonPanel.Controls.Add(this.deletePatientButton);
            this.buttonPanel.Controls.Add(this.revertButton);
            this.buttonPanel.Controls.Add(this.saveButton);
            this.buttonPanel.FlowDirection = System.Windows.Forms.FlowDirection.RightToLeft;
            this.buttonPanel.Location = new System.Drawing.Point(112, 5);
            this.buttonPanel.Margin = new System.Windows.Forms.Padding(0);
            this.buttonPanel.Name = "buttonPanel";
            this.buttonPanel.Size = new System.Drawing.Size(341, 26);
            this.buttonPanel.TabIndex = 2;
            // 
            // deletePatientButton
            // 
            this.deletePatientButton.AutoSize = true;
            this.deletePatientButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.deletePatientButton.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.deletePatientButton.Location = new System.Drawing.Point(256, 3);
            this.deletePatientButton.Name = "deletePatientButton";
            this.deletePatientButton.Size = new System.Drawing.Size(82, 23);
            this.deletePatientButton.TabIndex = 3;
            this.deletePatientButton.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.deletePatientButton.UseVisualStyleBackColor = true;
            // 
            // revertButton
            // 
            this.revertButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.revertButton.Location = new System.Drawing.Point(192, 3);
            this.revertButton.Name = "revertButton";
            this.revertButton.Size = new System.Drawing.Size(58, 23);
            this.revertButton.TabIndex = 2;
            this.revertButton.UseVisualStyleBackColor = true;
            // 
            // saveButton
            // 
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(130, 3);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(56, 23);
            this.saveButton.TabIndex = 1;
            this.saveButton.UseVisualStyleBackColor = true;
            // 
            // PatientInfoActionUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.actionPanel);
            this.Name = "PatientInfoActionUI";
            this.Size = new System.Drawing.Size(550, 37);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img1)).EndInit();
            this.actionPanel.ResumeLayout(false);
            this.buttonPanel.ResumeLayout(false);
            this.buttonPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox img1;
        private System.Windows.Forms.TableLayoutPanel actionPanel;
        private System.Windows.Forms.FlowLayoutPanel buttonPanel;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Button revertButton;
        private System.Windows.Forms.Button deletePatientButton;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
