namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    partial class AccountManagementUI
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
            this.accountManagementListPanel = new System.Windows.Forms.TableLayoutPanel();
            this.listPanel = new System.Windows.Forms.Panel();
            this.grid = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.panel3 = new System.Windows.Forms.Panel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.UnbillableAmtValueLabel = new System.Windows.Forms.Label();
            this.UnbillableAmount = new System.Windows.Forms.Label();
            this.TotalPayAdjLabel = new System.Windows.Forms.Label();
            this.totalChargesLabel = new System.Windows.Forms.Label();
            this.eigDataGrid2 = new McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid();
            this.accountBalanceLabel = new System.Windows.Forms.Label();
            this.accountBalanceValueLable = new System.Windows.Forms.Label();
            this.totalChargesValueLabel = new System.Windows.Forms.Label();
            this.totalAdjPayLabelValue = new System.Windows.Forms.Label();
            this.ViewButton = new System.Windows.Forms.Button();
            this.accountManagementListPanel.SuspendLayout();
            this.listPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grid)).BeginInit();
            this.panel3.SuspendLayout();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid2)).BeginInit();
            this.SuspendLayout();
            // 
            // accountManagementListPanel
            // 
            this.accountManagementListPanel.ColumnCount = 1;
            this.accountManagementListPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.accountManagementListPanel.Controls.Add(this.listPanel, 0, 0);
            this.accountManagementListPanel.Controls.Add(this.panel3, 0, 2);
            this.accountManagementListPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.accountManagementListPanel.Location = new System.Drawing.Point(0, 0);
            this.accountManagementListPanel.Name = "accountManagementListPanel";
            this.accountManagementListPanel.RowCount = 3;
            this.accountManagementListPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.accountManagementListPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.accountManagementListPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.accountManagementListPanel.Size = new System.Drawing.Size(668, 409);
            this.accountManagementListPanel.TabIndex = 0;
            // 
            // listPanel
            // 
            this.listPanel.Controls.Add(this.grid);
            this.listPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listPanel.Location = new System.Drawing.Point(3, 3);
            this.listPanel.Name = "listPanel";
            this.listPanel.Size = new System.Drawing.Size(662, 325);
            this.listPanel.TabIndex = 1;
            // 
            // grid
            // 
            this.grid.AllowUserToAddRows = false;
            this.grid.AllowUserToDeleteRows = false;
            this.grid.AllowUserToResizeRows = false;
            this.grid.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.grid.BackgroundColor = System.Drawing.Color.White;
            this.grid.ChangeValidator = null;
            this.grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.grid.ConfirmSelection = false;
            this.grid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.grid.Location = new System.Drawing.Point(0, 0);
            this.grid.MultiSelect = false;
            this.grid.Name = "grid";
            this.grid.ReadOnly = true;
            this.grid.RowHeadersVisible = false;
            this.grid.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.grid.SelectionHandler = null;
            this.grid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grid.Size = new System.Drawing.Size(662, 325);
            this.grid.SortEnabled = false;
            this.grid.TabIndex = 5;
            this.grid.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.grid_MouseDoubleClick);
            // 
            // panel3
            // 
            this.panel3.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.panel3.Controls.Add(this.panel1);
            this.panel3.Controls.Add(this.ViewButton);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel3.Location = new System.Drawing.Point(3, 334);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(662, 72);
            this.panel3.TabIndex = 3;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.UnbillableAmtValueLabel);
            this.panel1.Controls.Add(this.UnbillableAmount);
            this.panel1.Controls.Add(this.TotalPayAdjLabel);
            this.panel1.Controls.Add(this.totalChargesLabel);
            this.panel1.Controls.Add(this.eigDataGrid2);
            this.panel1.Controls.Add(this.accountBalanceLabel);
            this.panel1.Controls.Add(this.accountBalanceValueLable);
            this.panel1.Controls.Add(this.totalChargesValueLabel);
            this.panel1.Controls.Add(this.totalAdjPayLabelValue);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Right;
            this.panel1.Location = new System.Drawing.Point(353, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(309, 72);
            this.panel1.TabIndex = 23;
            // 
            // UnbillableAmtValueLabel
            // 
            this.UnbillableAmtValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.UnbillableAmtValueLabel.Location = new System.Drawing.Point(202, 33);
            this.UnbillableAmtValueLabel.Name = "UnbillableAmtValueLabel";
            this.UnbillableAmtValueLabel.Size = new System.Drawing.Size(99, 16);
            this.UnbillableAmtValueLabel.TabIndex = 23;
            this.UnbillableAmtValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // UnbillableAmount
            // 
            this.UnbillableAmount.AutoSize = true;
            this.UnbillableAmount.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.UnbillableAmount.Location = new System.Drawing.Point(83, 34);
            this.UnbillableAmount.Name = "UnbillableAmount";
            this.UnbillableAmount.Size = new System.Drawing.Size(111, 15);
            this.UnbillableAmount.TabIndex = 22;
            this.UnbillableAmount.Text = "Unbillable Amount:";
            this.UnbillableAmount.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // TotalPayAdjLabel
            // 
            this.TotalPayAdjLabel.AutoSize = true;
            this.TotalPayAdjLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.TotalPayAdjLabel.Location = new System.Drawing.Point(36, 17);
            this.TotalPayAdjLabel.Name = "TotalPayAdjLabel";
            this.TotalPayAdjLabel.Size = new System.Drawing.Size(167, 15);
            this.TotalPayAdjLabel.TabIndex = 16;
            this.TotalPayAdjLabel.Text = "Total Payments/Adjustments:";
            this.TotalPayAdjLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // totalChargesLabel
            // 
            this.totalChargesLabel.AutoSize = true;
            this.totalChargesLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalChargesLabel.Location = new System.Drawing.Point(104, 1);
            this.totalChargesLabel.Name = "totalChargesLabel";
            this.totalChargesLabel.Size = new System.Drawing.Size(88, 15);
            this.totalChargesLabel.TabIndex = 15;
            this.totalChargesLabel.Text = "Total Charges:";
            this.totalChargesLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // eigDataGrid2
            // 
            this.eigDataGrid2.AllowUserToAddRows = false;
            this.eigDataGrid2.AllowUserToDeleteRows = false;
            this.eigDataGrid2.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;
            this.eigDataGrid2.ChangeValidator = null;
            this.eigDataGrid2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.eigDataGrid2.ConfirmSelection = false;
            this.eigDataGrid2.Location = new System.Drawing.Point(37, 53);
            this.eigDataGrid2.Name = "eigDataGrid2";
            this.eigDataGrid2.ReadOnly = true;
            this.eigDataGrid2.SelectionHandler = null;
            this.eigDataGrid2.Size = new System.Drawing.Size(268, 2);
            this.eigDataGrid2.SortEnabled = false;
            this.eigDataGrid2.TabIndex = 21;
            // 
            // accountBalanceLabel
            // 
            this.accountBalanceLabel.AutoSize = true;
            this.accountBalanceLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.accountBalanceLabel.Location = new System.Drawing.Point(94, 57);
            this.accountBalanceLabel.Name = "accountBalanceLabel";
            this.accountBalanceLabel.Size = new System.Drawing.Size(101, 15);
            this.accountBalanceLabel.TabIndex = 17;
            this.accountBalanceLabel.Text = "Account Balance:";
            // 
            // accountBalanceValueLable
            // 
            this.accountBalanceValueLable.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.accountBalanceValueLable.Location = new System.Drawing.Point(205, 56);
            this.accountBalanceValueLable.Name = "accountBalanceValueLable";
            this.accountBalanceValueLable.Size = new System.Drawing.Size(96, 20);
            this.accountBalanceValueLable.TabIndex = 20;
            this.accountBalanceValueLable.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // totalChargesValueLabel
            // 
            this.totalChargesValueLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalChargesValueLabel.Location = new System.Drawing.Point(202, 1);
            this.totalChargesValueLabel.Name = "totalChargesValueLabel";
            this.totalChargesValueLabel.Size = new System.Drawing.Size(99, 17);
            this.totalChargesValueLabel.TabIndex = 18;
            this.totalChargesValueLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // totalAdjPayLabelValue
            // 
            this.totalAdjPayLabelValue.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalAdjPayLabelValue.Location = new System.Drawing.Point(202, 17);
            this.totalAdjPayLabelValue.Name = "totalAdjPayLabelValue";
            this.totalAdjPayLabelValue.Size = new System.Drawing.Size(103, 15);
            this.totalAdjPayLabelValue.TabIndex = 19;
            this.totalAdjPayLabelValue.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // ViewButton
            // 
            this.ViewButton.Location = new System.Drawing.Point(6, 5);
            this.ViewButton.Name = "ViewButton";
            this.ViewButton.Size = new System.Drawing.Size(72, 23);
            this.ViewButton.TabIndex = 22;
            this.ViewButton.Text = "View";
            this.ViewButton.UseVisualStyleBackColor = true;
            this.ViewButton.Click += new System.EventHandler(this.buttonView_Click);
            // 
            // AccountManagementUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.accountManagementListPanel);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "AccountManagementUI";
            this.Size = new System.Drawing.Size(668, 409);
            this.accountManagementListPanel.ResumeLayout(false);
            this.listPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grid)).EndInit();
            this.panel3.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.eigDataGrid2)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel accountManagementListPanel;
        private System.Windows.Forms.Panel listPanel;
        private McK.EIG.ROI.Client.Base.View.Common.EIGDataGrid grid;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.Button ViewButton;
        private Base.View.Common.EIGDataGrid eigDataGrid2;
        private System.Windows.Forms.Label accountBalanceValueLable;
        private System.Windows.Forms.Label totalAdjPayLabelValue;
        private System.Windows.Forms.Label totalChargesValueLabel;
        private System.Windows.Forms.Label accountBalanceLabel;
        private System.Windows.Forms.Label TotalPayAdjLabel;
        private System.Windows.Forms.Label totalChargesLabel;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label UnbillableAmount;
        private System.Windows.Forms.Label UnbillableAmtValueLabel;
       
    }
}
