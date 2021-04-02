namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class DocumentChargeUI
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            this.documentChargesGroupBox = new System.Windows.Forms.GroupBox();
            this.documentChargeGrid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.billingTierTotalPanel = new System.Windows.Forms.Panel();
            this.panel7 = new System.Windows.Forms.Panel();
            this.totalAmountLabel = new System.Windows.Forms.Label();
            this.totalPagesLabel = new System.Windows.Forms.Label();
            this.documentChargesGroupBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.documentChargeGrid)).BeginInit();
            this.billingTierTotalPanel.SuspendLayout();
            this.panel7.SuspendLayout();
            this.SuspendLayout();
            // 
            // documentChargesGroupBox
            // 
            this.documentChargesGroupBox.BackColor = System.Drawing.SystemColors.Window;
            this.documentChargesGroupBox.Controls.Add(this.documentChargeGrid);
            this.documentChargesGroupBox.Controls.Add(this.billingTierTotalPanel);
            this.documentChargesGroupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.documentChargesGroupBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.documentChargesGroupBox.Location = new System.Drawing.Point(0, 0);
            this.documentChargesGroupBox.Name = "documentChargesGroupBox";
            this.documentChargesGroupBox.Padding = new System.Windows.Forms.Padding(12);
            this.documentChargesGroupBox.Size = new System.Drawing.Size(502, 282);
            this.documentChargesGroupBox.TabIndex = 5;
            this.documentChargesGroupBox.TabStop = false;
            // 
            // documentChargeGrid
            // 
            this.documentChargeGrid.AllowUserToAddRows = false;
            this.documentChargeGrid.AllowUserToDeleteRows = false;
            this.documentChargeGrid.AllowUserToResizeRows = false;
            this.documentChargeGrid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.documentChargeGrid.BackgroundColor = System.Drawing.Color.White;
            this.documentChargeGrid.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.documentChargeGrid.ChangeValidator = null;
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.documentChargeGrid.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle1;
            this.documentChargeGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.documentChargeGrid.ConfirmSelection = false;
            this.documentChargeGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.documentChargeGrid.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnEnter;
            this.documentChargeGrid.EnableHeadersVisualStyles = false;
            this.documentChargeGrid.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            this.documentChargeGrid.Location = new System.Drawing.Point(12, 26);
            this.documentChargeGrid.MultiSelect = false;
            this.documentChargeGrid.Name = "documentChargeGrid";
            this.documentChargeGrid.RowHeadersVisible = false;
            this.documentChargeGrid.SelectionHandler = null;
            this.documentChargeGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;
            this.documentChargeGrid.Size = new System.Drawing.Size(478, 220);
            this.documentChargeGrid.SortEnabled = false;
            this.documentChargeGrid.TabIndex = 17;
            this.documentChargeGrid.EditingControlShowing += new System.Windows.Forms.DataGridViewEditingControlShowingEventHandler(this.documentChargeGrid_EditingControlShowing);
            this.documentChargeGrid.CellToolTipTextNeeded += new System.Windows.Forms.DataGridViewCellToolTipTextNeededEventHandler(this.documentChargeGrid_CellToolTipTextNeeded);
            // 
            // billingTierTotalPanel
            // 
            this.billingTierTotalPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(172)))), ((int)(((byte)(168)))), ((int)(((byte)(153)))));
            this.billingTierTotalPanel.Controls.Add(this.panel7);
            this.billingTierTotalPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.billingTierTotalPanel.Location = new System.Drawing.Point(12, 246);
            this.billingTierTotalPanel.Margin = new System.Windows.Forms.Padding(0);
            this.billingTierTotalPanel.Name = "billingTierTotalPanel";
            this.billingTierTotalPanel.Padding = new System.Windows.Forms.Padding(1);
            this.billingTierTotalPanel.Size = new System.Drawing.Size(478, 24);
            this.billingTierTotalPanel.TabIndex = 11;
            // 
            // panel7
            // 
            this.panel7.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(236)))), ((int)(((byte)(235)))), ((int)(((byte)(235)))));
            this.panel7.Controls.Add(this.totalAmountLabel);
            this.panel7.Controls.Add(this.totalPagesLabel);
            this.panel7.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel7.Location = new System.Drawing.Point(1, 1);
            this.panel7.Name = "panel7";
            this.panel7.Padding = new System.Windows.Forms.Padding(3);
            this.panel7.Size = new System.Drawing.Size(476, 22);
            this.panel7.TabIndex = 4;
            // 
            // totalAmountLabel
            // 
            this.totalAmountLabel.AutoSize = true;
            this.totalAmountLabel.Dock = System.Windows.Forms.DockStyle.Right;
            this.totalAmountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalAmountLabel.Location = new System.Drawing.Point(473, 3);
            this.totalAmountLabel.Name = "totalAmountLabel";
            this.totalAmountLabel.Size = new System.Drawing.Size(0, 15);
            this.totalAmountLabel.TabIndex = 5;
            // 
            // totalPagesLabel
            // 
            this.totalPagesLabel.AutoSize = true;
            this.totalPagesLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalPagesLabel.Location = new System.Drawing.Point(2, 6);
            this.totalPagesLabel.Name = "totalPagesLabel";
            this.totalPagesLabel.Size = new System.Drawing.Size(0, 15);
            this.totalPagesLabel.TabIndex = 4;
            // 
            // DocumentChargeUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.documentChargesGroupBox);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "DocumentChargeUI";
            this.Size = new System.Drawing.Size(502, 282);
            this.documentChargesGroupBox.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.documentChargeGrid)).EndInit();
            this.billingTierTotalPanel.ResumeLayout(false);
            this.panel7.ResumeLayout(false);
            this.panel7.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox documentChargesGroupBox;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid documentChargeGrid;
        private System.Windows.Forms.Panel billingTierTotalPanel;
        private System.Windows.Forms.Panel panel7;
        private System.Windows.Forms.Label totalAmountLabel;
        private System.Windows.Forms.Label totalPagesLabel;

    }
}
