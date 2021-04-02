namespace McK.EIG.ROI.Client.Request.View
{
    partial class OutputPropertiesDialog
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
            this.okButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.headerLabel = new System.Windows.Forms.Label();
            this.headerInfoLabel = new System.Windows.Forms.Label();
            this.propertyFlowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // okButton
            // 
            this.okButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.okButton.Location = new System.Drawing.Point(152, 263);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(87, 27);
            this.okButton.TabIndex = 1;
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Location = new System.Drawing.Point(245, 263);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(87, 27);
            this.cancelButton.TabIndex = 2;
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // headerLabel
            // 
            this.headerLabel.AutoSize = true;
            this.headerLabel.Font = new System.Drawing.Font("Arial", 11.5F, System.Drawing.FontStyle.Bold);
            this.headerLabel.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(27)))), ((int)(((byte)(81)))), ((int)(((byte)(156)))));
            this.headerLabel.Location = new System.Drawing.Point(7, 11);
            this.headerLabel.Name = "headerLabel";
            this.headerLabel.Size = new System.Drawing.Size(0, 19);
            this.headerLabel.TabIndex = 5;
            // 
            // headerInfoLabel
            // 
            this.headerInfoLabel.AutoSize = true;
            this.headerInfoLabel.Font = new System.Drawing.Font("Arial", 9F);
            this.headerInfoLabel.Location = new System.Drawing.Point(9, 36);
            this.headerInfoLabel.Name = "headerInfoLabel";
            this.headerInfoLabel.Size = new System.Drawing.Size(0, 15);
            this.headerInfoLabel.TabIndex = 6;
            // 
            // propertyFlowLayoutPanel
            // 
            this.propertyFlowLayoutPanel.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.propertyFlowLayoutPanel.AutoScroll = true;
            this.propertyFlowLayoutPanel.BackColor = System.Drawing.Color.White;
            this.propertyFlowLayoutPanel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.propertyFlowLayoutPanel.Location = new System.Drawing.Point(61, 59);
            this.propertyFlowLayoutPanel.Name = "propertyFlowLayoutPanel";
            this.propertyFlowLayoutPanel.Size = new System.Drawing.Size(354, 189);
            this.propertyFlowLayoutPanel.TabIndex = 7;
            // 
            // OutputPropertiesDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.propertyFlowLayoutPanel);
            this.Controls.Add(this.headerInfoLabel);
            this.Controls.Add(this.headerLabel);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.okButton);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "OutputPropertiesDialog";
            this.Size = new System.Drawing.Size(489, 301);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Label headerLabel;
        private System.Windows.Forms.Label headerInfoLabel;
        private System.Windows.Forms.FlowLayoutPanel propertyFlowLayoutPanel;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
