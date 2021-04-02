namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class PropertyDialogUI
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
            this.outerTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.innerFlowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.propertyLabel = new System.Windows.Forms.Label();
            this.outerTableLayoutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // outerTableLayoutPanel
            // 
            this.outerTableLayoutPanel.ColumnCount = 2;
            this.outerTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 33.33333F));
            this.outerTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 66.66666F));
            this.outerTableLayoutPanel.Controls.Add(this.innerFlowLayoutPanel, 1, 0);
            this.outerTableLayoutPanel.Controls.Add(this.propertyLabel, 0, 0);
            this.outerTableLayoutPanel.Location = new System.Drawing.Point(3, 3);
            this.outerTableLayoutPanel.Name = "outerTableLayoutPanel";
            this.outerTableLayoutPanel.RowCount = 1;
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.outerTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.outerTableLayoutPanel.Size = new System.Drawing.Size(321, 29);
            this.outerTableLayoutPanel.TabIndex = 0;
            // 
            // innerFlowLayoutPanel
            // 
            this.innerFlowLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.innerFlowLayoutPanel.Location = new System.Drawing.Point(109, 3);
            this.innerFlowLayoutPanel.Name = "innerFlowLayoutPanel";
            this.innerFlowLayoutPanel.Size = new System.Drawing.Size(209, 23);
            this.innerFlowLayoutPanel.TabIndex = 0;
            // 
            // propertyLabel
            // 
            this.propertyLabel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)));
            this.propertyLabel.AutoSize = true;
            this.propertyLabel.ImageAlign = System.Drawing.ContentAlignment.TopLeft;
            this.propertyLabel.Location = new System.Drawing.Point(53, 0);
            this.propertyLabel.Name = "propertyLabel";
            this.propertyLabel.Size = new System.Drawing.Size(0, 29);
            this.propertyLabel.TabIndex = 1;
            this.propertyLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // PropertyDialogUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.outerTableLayoutPanel);
            this.Name = "PropertyDialogUI";
            this.Size = new System.Drawing.Size(327, 36);
            this.outerTableLayoutPanel.ResumeLayout(false);
            this.outerTableLayoutPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel outerTableLayoutPanel;
        private System.Windows.Forms.FlowLayoutPanel innerFlowLayoutPanel;
        private System.Windows.Forms.Label propertyLabel;

    }
}
