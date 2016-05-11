namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class FeeTypeUI
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
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.feeTypeTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.feeTypeLabel = new System.Windows.Forms.Label();
            this.amountPanel = new System.Windows.Forms.Panel();
            this.amountTextBox = new System.Windows.Forms.TextBox();
            this.dollarLabel = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.feeTypeTableLayoutPanel.SuspendLayout();
            this.amountPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // feeTypeTableLayoutPanel
            // 
            this.feeTypeTableLayoutPanel.BackColor = System.Drawing.SystemColors.ButtonHighlight;
            this.feeTypeTableLayoutPanel.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Single;
            this.feeTypeTableLayoutPanel.ColumnCount = 2;
            this.feeTypeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.feeTypeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 90F));
            this.feeTypeTableLayoutPanel.Controls.Add(this.feeTypeLabel, 0, 0);
            this.feeTypeTableLayoutPanel.Controls.Add(this.amountPanel, 1, 0);
            this.feeTypeTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.feeTypeTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.feeTypeTableLayoutPanel.Name = "feeTypeTableLayoutPanel";
            this.feeTypeTableLayoutPanel.RowCount = 1;
            this.feeTypeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.feeTypeTableLayoutPanel.Size = new System.Drawing.Size(396, 30);
            this.feeTypeTableLayoutPanel.TabIndex = 25;
            // 
            // feeTypeLabel
            // 
            this.feeTypeLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.feeTypeLabel.AutoSize = true;
            this.feeTypeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.feeTypeLabel.Location = new System.Drawing.Point(4, 7);
            this.feeTypeLabel.Name = "feeTypeLabel";
            this.feeTypeLabel.Size = new System.Drawing.Size(0, 15);
            this.feeTypeLabel.TabIndex = 18;
            // 
            // amountPanel
            // 
            this.amountPanel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.amountPanel.Controls.Add(this.amountTextBox);
            this.amountPanel.Controls.Add(this.dollarLabel);
            this.amountPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.amountPanel.Location = new System.Drawing.Point(307, 1);
            this.amountPanel.Margin = new System.Windows.Forms.Padding(2, 0, 0, 0);
            this.amountPanel.Name = "amountPanel";
            this.amountPanel.Size = new System.Drawing.Size(88, 28);
            this.amountPanel.TabIndex = 1;
            // 
            // amountTextBox
            // 
            this.amountTextBox.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.amountTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.amountTextBox.Location = new System.Drawing.Point(27, 4);
            this.amountTextBox.MaxLength = 20;
            this.amountTextBox.Name = "amountTextBox";
            this.amountTextBox.Size = new System.Drawing.Size(50, 21);
            this.amountTextBox.TabIndex = 16;
            this.amountTextBox.Leave += new System.EventHandler(this.amountTextBox_Leave);
            this.amountTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.amountTextBox_KeyPress);
            // 
            // dollarLabel
            // 
            this.dollarLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.dollarLabel.AutoSize = true;
            this.dollarLabel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dollarLabel.Location = new System.Drawing.Point(13, 7);
            this.dollarLabel.Name = "dollarLabel";
            this.dollarLabel.Size = new System.Drawing.Size(0, 14);
            this.dollarLabel.TabIndex = 17;
            // 
            // FeeTypeUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.feeTypeTableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "FeeTypeUI";
            this.Size = new System.Drawing.Size(396, 30);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.feeTypeTableLayoutPanel.ResumeLayout(false);
            this.feeTypeTableLayoutPanel.PerformLayout();
            this.amountPanel.ResumeLayout(false);
            this.amountPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.TableLayoutPanel feeTypeTableLayoutPanel;
        private System.Windows.Forms.Label feeTypeLabel;
        private System.Windows.Forms.Panel amountPanel;
        private System.Windows.Forms.TextBox amountTextBox;
        private System.Windows.Forms.Label dollarLabel;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
