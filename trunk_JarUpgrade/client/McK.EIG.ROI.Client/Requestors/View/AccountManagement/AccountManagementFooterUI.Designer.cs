namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    partial class AccountManagementFooterUI
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
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.statementsButton = new System.Windows.Forms.Button();
            this.refundButton = new System.Windows.Forms.Button();
            this.adjustmentsButton = new System.Windows.Forms.Button();
            this.paymentsButton = new System.Windows.Forms.Button();
            this.tableLayoutPanel.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.ColumnCount = 1;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel.Controls.Add(this.panel1, 0, 0);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 1;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 61.78862F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(641, 45);
            this.tableLayoutPanel.TabIndex = 0;
            // 
            // panel1
            // 
            this.panel1.AutoScroll = true;
            this.panel1.AutoScrollMinSize = new System.Drawing.Size(750, 0);
            this.panel1.Controls.Add(this.refundButton);
            this.panel1.Controls.Add(this.statementsButton);
            this.panel1.Controls.Add(this.adjustmentsButton);
            this.panel1.Controls.Add(this.paymentsButton);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.panel1.Location = new System.Drawing.Point(3, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(635, 39);
            this.panel1.TabIndex = 1;
            // 
            // refundButton
            // 
            this.refundButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.refundButton.Enabled = false;
            this.refundButton.Location = new System.Drawing.Point(475, 0);
            this.refundButton.Name = "refundButton";
            this.refundButton.Size = new System.Drawing.Size(75, 23);
            this.refundButton.TabIndex = 3;
            this.refundButton.Text = "Refund";
            this.refundButton.UseVisualStyleBackColor = true;
            // 
            // statementsButton
            // 
            this.statementsButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.statementsButton.Location = new System.Drawing.Point(398, 0);
            this.statementsButton.Name = "statementsButton";
            this.statementsButton.Size = new System.Drawing.Size(71, 23);
            this.statementsButton.TabIndex = 2;
            this.statementsButton.Text = "Statements";
            this.statementsButton.UseVisualStyleBackColor = true;
            // 
            // adjustmentsButton
            // 
            this.adjustmentsButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adjustmentsButton.Location = new System.Drawing.Point(290, 0);
            this.adjustmentsButton.Name = "adjustmentsButton";
            this.adjustmentsButton.Size = new System.Drawing.Size(102, 23);
            this.adjustmentsButton.TabIndex = 1;
            this.adjustmentsButton.Text = "AddAdjustment";
            this.adjustmentsButton.UseVisualStyleBackColor = true;
            // 
            // paymentsButton
            // 
            this.paymentsButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.paymentsButton.Location = new System.Drawing.Point(190, 0);
            this.paymentsButton.Name = "paymentsButton";
            this.paymentsButton.Size = new System.Drawing.Size(94, 23);
            this.paymentsButton.TabIndex = 0;
            this.paymentsButton.Text = "AddPayment";
            this.paymentsButton.UseVisualStyleBackColor = true;
            // 
            // AccountManagementFooterUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.tableLayoutPanel);
            this.Name = "AccountManagementFooterUI";
            this.Size = new System.Drawing.Size(641, 45);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.AccountManagementFooterUI_Paint);
            this.tableLayoutPanel.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button statementsButton;
        private System.Windows.Forms.Button refundButton;
        private System.Windows.Forms.Button adjustmentsButton;
        private System.Windows.Forms.Button paymentsButton;
    }
}
