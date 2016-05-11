namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class FeeChargeUI
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
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.feeChargesGroupBox = new System.Windows.Forms.GroupBox();
            this.wholeUITableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.billingTypePanel = new System.Windows.Forms.Panel();
            this.billingTypeComboBox = new System.Windows.Forms.ComboBox();
            this.billingTypeLabel = new System.Windows.Forms.Label();
            this.createCustomeFeeButton = new System.Windows.Forms.Button();
            this.feeChargesTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.panel4 = new System.Windows.Forms.Panel();
            this.panel9 = new System.Windows.Forms.Panel();
            this.totalStdFeeAmountLabel = new System.Windows.Forms.Label();
            this.stdFeeTypeHeaderTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.feeTypesColumnLabel = new System.Windows.Forms.Label();
            this.amountColumnLabel = new System.Windows.Forms.Label();
            this.stdFeeChargesFlowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.customFeeTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.panel5 = new System.Windows.Forms.Panel();
            this.totalCustomFeeAmountLabel = new System.Windows.Forms.Label();
            this.customFeeHeaderTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.customAmountColumnLabel = new System.Windows.Forms.Label();
            this.customFeeColumnLabel = new System.Windows.Forms.Label();
            this.customFeeChargesFlowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.feeChargesGroupBox.SuspendLayout();
            this.wholeUITableLayoutPanel.SuspendLayout();
            this.billingTypePanel.SuspendLayout();
            this.feeChargesTableLayoutPanel.SuspendLayout();
            this.panel4.SuspendLayout();
            this.panel9.SuspendLayout();
            this.stdFeeTypeHeaderTableLayoutPanel.SuspendLayout();
            this.customFeeTableLayoutPanel.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel5.SuspendLayout();
            this.customFeeHeaderTableLayoutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // feeChargesGroupBox
            // 
            this.feeChargesGroupBox.BackColor = System.Drawing.SystemColors.Window;
            this.feeChargesGroupBox.Controls.Add(this.wholeUITableLayoutPanel);
            this.feeChargesGroupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.feeChargesGroupBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.feeChargesGroupBox.Location = new System.Drawing.Point(0, 0);
            this.feeChargesGroupBox.Name = "feeChargesGroupBox";
            this.feeChargesGroupBox.Padding = new System.Windows.Forms.Padding(10);
            this.feeChargesGroupBox.Size = new System.Drawing.Size(459, 324);
            this.feeChargesGroupBox.TabIndex = 5;
            this.feeChargesGroupBox.TabStop = false;
            // 
            // wholeUITableLayoutPanel
            // 
            this.wholeUITableLayoutPanel.ColumnCount = 1;
            this.wholeUITableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.wholeUITableLayoutPanel.Controls.Add(this.billingTypePanel, 0, 0);
            this.wholeUITableLayoutPanel.Controls.Add(this.createCustomeFeeButton, 0, 3);
            this.wholeUITableLayoutPanel.Controls.Add(this.feeChargesTableLayoutPanel, 0, 1);
            this.wholeUITableLayoutPanel.Controls.Add(this.customFeeTableLayoutPanel, 0, 2);
            this.wholeUITableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.wholeUITableLayoutPanel.Location = new System.Drawing.Point(10, 24);
            this.wholeUITableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.wholeUITableLayoutPanel.Name = "wholeUITableLayoutPanel";
            this.wholeUITableLayoutPanel.RowCount = 4;
            this.wholeUITableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 40F));
            this.wholeUITableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.wholeUITableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.wholeUITableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 29F));
            this.wholeUITableLayoutPanel.Size = new System.Drawing.Size(439, 290);
            this.wholeUITableLayoutPanel.TabIndex = 20;
            // 
            // billingTypePanel
            // 
            this.billingTypePanel.Controls.Add(this.billingTypeComboBox);
            this.billingTypePanel.Controls.Add(this.billingTypeLabel);
            this.billingTypePanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.billingTypePanel.Location = new System.Drawing.Point(3, 3);
            this.billingTypePanel.Name = "billingTypePanel";
            this.billingTypePanel.Size = new System.Drawing.Size(433, 34);
            this.billingTypePanel.TabIndex = 0;
            // 
            // billingTypeComboBox
            // 
            this.billingTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.billingTypeComboBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.billingTypeComboBox.FormattingEnabled = true;
            this.billingTypeComboBox.Location = new System.Drawing.Point(123, 3);
            this.billingTypeComboBox.Name = "billingTypeComboBox";
            this.billingTypeComboBox.Size = new System.Drawing.Size(183, 23);
            this.billingTypeComboBox.TabIndex = 14;
            // 
            // billingTypeLabel
            // 
            this.billingTypeLabel.AutoSize = true;
            this.billingTypeLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.billingTypeLabel.Location = new System.Drawing.Point(8, 6);
            this.billingTypeLabel.Name = "billingTypeLabel";
            this.billingTypeLabel.Size = new System.Drawing.Size(0, 15);
            this.billingTypeLabel.TabIndex = 13;
            // 
            // createCustomeFeeButton
            // 
            this.createCustomeFeeButton.Location = new System.Drawing.Point(3, 263);
            this.createCustomeFeeButton.Name = "createCustomeFeeButton";
            this.createCustomeFeeButton.Size = new System.Drawing.Size(152, 23);
            this.createCustomeFeeButton.TabIndex = 18;
            this.createCustomeFeeButton.UseVisualStyleBackColor = true;
            this.createCustomeFeeButton.Click += new System.EventHandler(this.createCustomeFeeButton_Click);
            // 
            // feeChargesTableLayoutPanel
            // 
            this.feeChargesTableLayoutPanel.ColumnCount = 1;
            this.feeChargesTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.feeChargesTableLayoutPanel.Controls.Add(this.panel4, 0, 2);
            this.feeChargesTableLayoutPanel.Controls.Add(this.stdFeeTypeHeaderTableLayoutPanel, 0, 0);
            this.feeChargesTableLayoutPanel.Controls.Add(this.stdFeeChargesFlowLayoutPanel, 0, 1);
            this.feeChargesTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.feeChargesTableLayoutPanel.Location = new System.Drawing.Point(0, 40);
            this.feeChargesTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.feeChargesTableLayoutPanel.Name = "feeChargesTableLayoutPanel";
            this.feeChargesTableLayoutPanel.RowCount = 3;
            this.feeChargesTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 25F));
            this.feeChargesTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.feeChargesTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 24F));
            this.feeChargesTableLayoutPanel.Size = new System.Drawing.Size(439, 110);
            this.feeChargesTableLayoutPanel.TabIndex = 1;
            // 
            // panel4
            // 
            this.panel4.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(172)))), ((int)(((byte)(168)))), ((int)(((byte)(153)))));
            this.panel4.Controls.Add(this.panel9);
            this.panel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel4.Location = new System.Drawing.Point(0, 86);
            this.panel4.Margin = new System.Windows.Forms.Padding(0);
            this.panel4.Name = "panel4";
            this.panel4.Padding = new System.Windows.Forms.Padding(1);
            this.panel4.Size = new System.Drawing.Size(439, 24);
            this.panel4.TabIndex = 12;
            // 
            // panel9
            // 
            this.panel9.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            this.panel9.Controls.Add(this.totalStdFeeAmountLabel);
            this.panel9.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel9.Location = new System.Drawing.Point(1, 1);
            this.panel9.Name = "panel9";
            this.panel9.Padding = new System.Windows.Forms.Padding(3);
            this.panel9.Size = new System.Drawing.Size(437, 22);
            this.panel9.TabIndex = 4;
            // 
            // totalStdFeeAmountLabel
            // 
            this.totalStdFeeAmountLabel.AutoSize = true;
            this.totalStdFeeAmountLabel.Dock = System.Windows.Forms.DockStyle.Right;
            this.totalStdFeeAmountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalStdFeeAmountLabel.Location = new System.Drawing.Point(434, 3);
            this.totalStdFeeAmountLabel.Name = "totalStdFeeAmountLabel";
            this.totalStdFeeAmountLabel.Size = new System.Drawing.Size(0, 15);
            this.totalStdFeeAmountLabel.TabIndex = 5;
            // 
            // stdFeeTypeHeaderTableLayoutPanel
            // 
            this.stdFeeTypeHeaderTableLayoutPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            this.stdFeeTypeHeaderTableLayoutPanel.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Single;
            this.stdFeeTypeHeaderTableLayoutPanel.ColumnCount = 2;
            this.stdFeeTypeHeaderTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.stdFeeTypeHeaderTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 108F));
            this.stdFeeTypeHeaderTableLayoutPanel.Controls.Add(this.feeTypesColumnLabel, 0, 0);
            this.stdFeeTypeHeaderTableLayoutPanel.Controls.Add(this.amountColumnLabel, 1, 0);
            this.stdFeeTypeHeaderTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.stdFeeTypeHeaderTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.stdFeeTypeHeaderTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.stdFeeTypeHeaderTableLayoutPanel.Name = "stdFeeTypeHeaderTableLayoutPanel";
            this.stdFeeTypeHeaderTableLayoutPanel.RowCount = 1;
            this.stdFeeTypeHeaderTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 55F));
            this.stdFeeTypeHeaderTableLayoutPanel.Size = new System.Drawing.Size(439, 25);
            this.stdFeeTypeHeaderTableLayoutPanel.TabIndex = 1;
            // 
            // feeTypesColumnLabel
            // 
            this.feeTypesColumnLabel.AutoSize = true;
            this.feeTypesColumnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.feeTypesColumnLabel.Location = new System.Drawing.Point(4, 1);
            this.feeTypesColumnLabel.Margin = new System.Windows.Forms.Padding(3, 0, 10, 10);
            this.feeTypesColumnLabel.Name = "feeTypesColumnLabel";
            this.feeTypesColumnLabel.Size = new System.Drawing.Size(0, 15);
            this.feeTypesColumnLabel.TabIndex = 5;
            // 
            // amountColumnLabel
            // 
            this.amountColumnLabel.AutoSize = true;
            this.amountColumnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.amountColumnLabel.Location = new System.Drawing.Point(333, 1);
            this.amountColumnLabel.Margin = new System.Windows.Forms.Padding(3, 0, 10, 10);
            this.amountColumnLabel.Name = "amountColumnLabel";
            this.amountColumnLabel.Size = new System.Drawing.Size(0, 15);
            this.amountColumnLabel.TabIndex = 6;
            this.amountColumnLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // stdFeeChargesFlowLayoutPanel
            // 
            this.stdFeeChargesFlowLayoutPanel.AutoScroll = true;
            this.stdFeeChargesFlowLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.stdFeeChargesFlowLayoutPanel.Location = new System.Drawing.Point(0, 25);
            this.stdFeeChargesFlowLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.stdFeeChargesFlowLayoutPanel.Name = "stdFeeChargesFlowLayoutPanel";
            this.stdFeeChargesFlowLayoutPanel.Size = new System.Drawing.Size(439, 61);
            this.stdFeeChargesFlowLayoutPanel.TabIndex = 13;
            // 
            // customFeeTableLayoutPanel
            // 
            this.customFeeTableLayoutPanel.ColumnCount = 1;
            this.customFeeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.customFeeTableLayoutPanel.Controls.Add(this.panel2, 0, 2);
            this.customFeeTableLayoutPanel.Controls.Add(this.customFeeHeaderTableLayoutPanel, 0, 0);
            this.customFeeTableLayoutPanel.Controls.Add(this.customFeeChargesFlowLayoutPanel, 0, 1);
            this.customFeeTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.customFeeTableLayoutPanel.Location = new System.Drawing.Point(0, 160);
            this.customFeeTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0, 10, 0, 0);
            this.customFeeTableLayoutPanel.Name = "customFeeTableLayoutPanel";
            this.customFeeTableLayoutPanel.RowCount = 3;
            this.customFeeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 25F));
            this.customFeeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.customFeeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 25F));
            this.customFeeTableLayoutPanel.Size = new System.Drawing.Size(439, 100);
            this.customFeeTableLayoutPanel.TabIndex = 19;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(172)))), ((int)(((byte)(168)))), ((int)(((byte)(153)))));
            this.panel2.Controls.Add(this.panel5);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(0, 75);
            this.panel2.Margin = new System.Windows.Forms.Padding(0);
            this.panel2.Name = "panel2";
            this.panel2.Padding = new System.Windows.Forms.Padding(1);
            this.panel2.Size = new System.Drawing.Size(439, 25);
            this.panel2.TabIndex = 13;
            // 
            // panel5
            // 
            this.panel5.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            this.panel5.Controls.Add(this.totalCustomFeeAmountLabel);
            this.panel5.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel5.Location = new System.Drawing.Point(1, 1);
            this.panel5.Name = "panel5";
            this.panel5.Padding = new System.Windows.Forms.Padding(3);
            this.panel5.Size = new System.Drawing.Size(437, 23);
            this.panel5.TabIndex = 4;
            // 
            // totalCustomFeeAmountLabel
            // 
            this.totalCustomFeeAmountLabel.AutoSize = true;
            this.totalCustomFeeAmountLabel.Dock = System.Windows.Forms.DockStyle.Right;
            this.totalCustomFeeAmountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalCustomFeeAmountLabel.Location = new System.Drawing.Point(434, 3);
            this.totalCustomFeeAmountLabel.Name = "totalCustomFeeAmountLabel";
            this.totalCustomFeeAmountLabel.Size = new System.Drawing.Size(0, 15);
            this.totalCustomFeeAmountLabel.TabIndex = 5;
            // 
            // customFeeHeaderTableLayoutPanel
            // 
            this.customFeeHeaderTableLayoutPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            this.customFeeHeaderTableLayoutPanel.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Single;
            this.customFeeHeaderTableLayoutPanel.ColumnCount = 3;
            this.customFeeHeaderTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.customFeeHeaderTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 95F));
            this.customFeeHeaderTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 56F));
            this.customFeeHeaderTableLayoutPanel.Controls.Add(this.customAmountColumnLabel, 1, 0);
            this.customFeeHeaderTableLayoutPanel.Controls.Add(this.customFeeColumnLabel, 0, 0);
            this.customFeeHeaderTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.customFeeHeaderTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.customFeeHeaderTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.customFeeHeaderTableLayoutPanel.Name = "customFeeHeaderTableLayoutPanel";
            this.customFeeHeaderTableLayoutPanel.RowCount = 1;
            this.customFeeHeaderTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 60F));
            this.customFeeHeaderTableLayoutPanel.Size = new System.Drawing.Size(439, 25);
            this.customFeeHeaderTableLayoutPanel.TabIndex = 2;
            // 
            // customAmountColumnLabel
            // 
            this.customAmountColumnLabel.AutoSize = true;
            this.customAmountColumnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.customAmountColumnLabel.Location = new System.Drawing.Point(289, 1);
            this.customAmountColumnLabel.Margin = new System.Windows.Forms.Padding(3, 0, 10, 10);
            this.customAmountColumnLabel.Name = "customAmountColumnLabel";
            this.customAmountColumnLabel.Size = new System.Drawing.Size(0, 15);
            this.customAmountColumnLabel.TabIndex = 6;
            // 
            // customFeeColumnLabel
            // 
            this.customFeeColumnLabel.AutoSize = true;
            this.customFeeColumnLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.customFeeColumnLabel.Location = new System.Drawing.Point(4, 1);
            this.customFeeColumnLabel.Margin = new System.Windows.Forms.Padding(3, 0, 10, 10);
            this.customFeeColumnLabel.Name = "customFeeColumnLabel";
            this.customFeeColumnLabel.Size = new System.Drawing.Size(0, 15);
            this.customFeeColumnLabel.TabIndex = 5;
            // 
            // customFeeChargesFlowLayoutPanel
            // 
            this.customFeeChargesFlowLayoutPanel.AutoScroll = true;
            this.customFeeChargesFlowLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.customFeeChargesFlowLayoutPanel.Location = new System.Drawing.Point(0, 25);
            this.customFeeChargesFlowLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.customFeeChargesFlowLayoutPanel.Name = "customFeeChargesFlowLayoutPanel";
            this.customFeeChargesFlowLayoutPanel.Size = new System.Drawing.Size(439, 50);
            this.customFeeChargesFlowLayoutPanel.TabIndex = 14;
            // 
            // FeeChargeUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.feeChargesGroupBox);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "FeeChargeUI";
            this.Size = new System.Drawing.Size(459, 324);
            this.feeChargesGroupBox.ResumeLayout(false);
            this.wholeUITableLayoutPanel.ResumeLayout(false);
            this.billingTypePanel.ResumeLayout(false);
            this.billingTypePanel.PerformLayout();
            this.feeChargesTableLayoutPanel.ResumeLayout(false);
            this.panel4.ResumeLayout(false);
            this.panel9.ResumeLayout(false);
            this.panel9.PerformLayout();
            this.stdFeeTypeHeaderTableLayoutPanel.ResumeLayout(false);
            this.stdFeeTypeHeaderTableLayoutPanel.PerformLayout();
            this.customFeeTableLayoutPanel.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel5.ResumeLayout(false);
            this.panel5.PerformLayout();
            this.customFeeHeaderTableLayoutPanel.ResumeLayout(false);
            this.customFeeHeaderTableLayoutPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox feeChargesGroupBox;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.TableLayoutPanel wholeUITableLayoutPanel;
        private System.Windows.Forms.Panel billingTypePanel;
        private System.Windows.Forms.ComboBox billingTypeComboBox;
        private System.Windows.Forms.Label billingTypeLabel;
        private System.Windows.Forms.Button createCustomeFeeButton;
        private System.Windows.Forms.TableLayoutPanel feeChargesTableLayoutPanel;
        private System.Windows.Forms.Panel panel4;
        private System.Windows.Forms.Panel panel9;
        private System.Windows.Forms.Label totalStdFeeAmountLabel;
        private System.Windows.Forms.TableLayoutPanel stdFeeTypeHeaderTableLayoutPanel;
        private System.Windows.Forms.FlowLayoutPanel stdFeeChargesFlowLayoutPanel;
        private System.Windows.Forms.TableLayoutPanel customFeeTableLayoutPanel;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Panel panel5;
        private System.Windows.Forms.Label totalCustomFeeAmountLabel;
        private System.Windows.Forms.TableLayoutPanel customFeeHeaderTableLayoutPanel;
        private System.Windows.Forms.Label customAmountColumnLabel;
        private System.Windows.Forms.Label customFeeColumnLabel;
        private System.Windows.Forms.FlowLayoutPanel customFeeChargesFlowLayoutPanel;
        private System.Windows.Forms.Label amountColumnLabel;
        private System.Windows.Forms.Label feeTypesColumnLabel;
        
    }
}
