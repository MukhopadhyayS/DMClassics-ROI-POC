namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class BillingTierUI
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
            this.billingTierTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.amountLabel = new System.Windows.Forms.Label();
            this.billingTierLabel = new System.Windows.Forms.Label();
            this.copiesTextBox = new System.Windows.Forms.TextBox();
            this.pagesTextBox = new System.Windows.Forms.TextBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.billingTierTableLayoutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // billingTierTableLayoutPanel
            // 
            this.billingTierTableLayoutPanel.BackColor = System.Drawing.SystemColors.Window;
            this.billingTierTableLayoutPanel.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Single;
            this.billingTierTableLayoutPanel.ColumnCount = 4;
            this.billingTierTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 56F));
            this.billingTierTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 56F));
            this.billingTierTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 235F));
            this.billingTierTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 80F));
            this.billingTierTableLayoutPanel.Controls.Add(this.amountLabel, 3, 0);
            this.billingTierTableLayoutPanel.Controls.Add(this.billingTierLabel, 0, 0);
            this.billingTierTableLayoutPanel.Controls.Add(this.copiesTextBox, 0, 0);
            this.billingTierTableLayoutPanel.Controls.Add(this.pagesTextBox, 0, 0);
            this.billingTierTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.billingTierTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.billingTierTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.billingTierTableLayoutPanel.Name = "billingTierTableLayoutPanel";
            this.billingTierTableLayoutPanel.RowCount = 1;
            this.billingTierTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.billingTierTableLayoutPanel.Size = new System.Drawing.Size(429, 30);
            this.billingTierTableLayoutPanel.TabIndex = 24;
            // 
            // amountLabel
            // 
            this.amountLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.amountLabel.AutoSize = true;
            this.amountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.amountLabel.Location = new System.Drawing.Point(351, 7);
            this.amountLabel.Name = "amountLabel";
            this.amountLabel.Size = new System.Drawing.Size(0, 15);
            this.amountLabel.TabIndex = 24;
            // 
            // billingTierLabel
            // 
            this.billingTierLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.billingTierLabel.AutoSize = true;
            this.billingTierLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.billingTierLabel.Location = new System.Drawing.Point(118, 7);
            this.billingTierLabel.Name = "billingTierLabel";
            this.billingTierLabel.Size = new System.Drawing.Size(0, 15);
            this.billingTierLabel.TabIndex = 22;
            // 
            // copiesTextBox
            // 
            this.copiesTextBox.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.copiesTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.copiesTextBox.Location = new System.Drawing.Point(64, 4);
            this.copiesTextBox.MaxLength = 3;
            this.copiesTextBox.Name = "copiesTextBox";
            this.copiesTextBox.Size = new System.Drawing.Size(44, 21);
            this.copiesTextBox.TabIndex = 21;
            this.copiesTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // pagesTextBox
            // 
            this.pagesTextBox.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.pagesTextBox.Enabled = false;
            this.pagesTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.pagesTextBox.Location = new System.Drawing.Point(6, 4);
            this.pagesTextBox.MaxLength = 5;
            this.pagesTextBox.Name = "pagesTextBox";
            this.pagesTextBox.Size = new System.Drawing.Size(45, 21);
            this.pagesTextBox.TabIndex = 20;
            this.pagesTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // BillingTierUI
            // 
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.billingTierTableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "BillingTierUI";
            this.Size = new System.Drawing.Size(429, 30);
            this.billingTierTableLayoutPanel.ResumeLayout(false);
            this.billingTierTableLayoutPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel billingTierTableLayoutPanel;
        private System.Windows.Forms.Label amountLabel;
        private System.Windows.Forms.Label billingTierLabel;
        private System.Windows.Forms.TextBox copiesTextBox;
        private System.Windows.Forms.TextBox pagesTextBox;
        private System.Windows.Forms.ToolTip toolTip;

    }
}
