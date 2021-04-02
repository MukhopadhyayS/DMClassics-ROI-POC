namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    partial class CustomFeeUI
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
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.customFeeTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.deleteImg = new System.Windows.Forms.PictureBox();
            this.customFeeTextBox = new System.Windows.Forms.TextBox();
            this.amountPanel = new System.Windows.Forms.Panel();
            this.dollarLabel = new System.Windows.Forms.Label();
            this.amountTextBox = new System.Windows.Forms.TextBox();
            this.customFee_errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.customFeeTableLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.deleteImg)).BeginInit();
            this.amountPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.customFee_errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            // 
            // customFeeTableLayoutPanel
            // 
            this.customFeeTableLayoutPanel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.customFeeTableLayoutPanel.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Single;
            this.customFeeTableLayoutPanel.ColumnCount = 3;
            this.customFeeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.customFeeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 82F));
            this.customFeeTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 54F));
            this.customFeeTableLayoutPanel.Controls.Add(this.deleteImg, 2, 0);
            this.customFeeTableLayoutPanel.Controls.Add(this.customFeeTextBox, 0, 0);
            this.customFeeTableLayoutPanel.Controls.Add(this.amountPanel, 1, 0);
            this.customFeeTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.customFeeTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.customFeeTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.customFeeTableLayoutPanel.Name = "customFeeTableLayoutPanel";
            this.customFeeTableLayoutPanel.RowCount = 1;
            this.customFeeTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.customFeeTableLayoutPanel.Size = new System.Drawing.Size(380, 27);
            this.customFeeTableLayoutPanel.TabIndex = 23;
            // 
            // deleteImg
            // 
            this.deleteImg.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.deleteImg.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.delete;
            this.deleteImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.deleteImg.InitialImage = null;
            this.deleteImg.Location = new System.Drawing.Point(342, 5);
            this.deleteImg.Name = "deleteImg";
            this.deleteImg.Size = new System.Drawing.Size(19, 17);
            this.deleteImg.TabIndex = 17;
            this.deleteImg.TabStop = false;
            this.deleteImg.Click += new System.EventHandler(this.deleteImg_Click);
            // 
            // customFeeTextBox
            // 
            this.customFeeTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.customFeeTextBox.Location = new System.Drawing.Point(4, 4);
            this.customFeeTextBox.MaxLength = 50;
            this.customFeeTextBox.Multiline = true;
            this.customFeeTextBox.Name = "customFeeTextBox";
            this.customFeeTextBox.Size = new System.Drawing.Size(234, 19);
            this.customFeeTextBox.TabIndex = 18;
            // 
            // amountPanel
            // 
            this.amountPanel.Controls.Add(this.dollarLabel);
            this.amountPanel.Controls.Add(this.amountTextBox);
            this.amountPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.amountPanel.Location = new System.Drawing.Point(242, 1);
            this.amountPanel.Margin = new System.Windows.Forms.Padding(0);
            this.amountPanel.Name = "amountPanel";
            this.amountPanel.Size = new System.Drawing.Size(82, 25);
            this.amountPanel.TabIndex = 19;
            // 
            // dollarLabel
            // 
            this.dollarLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.dollarLabel.AutoSize = true;
            this.dollarLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dollarLabel.Location = new System.Drawing.Point(4, 5);
            this.dollarLabel.Name = "dollarLabel";
            this.dollarLabel.Size = new System.Drawing.Size(0, 15);
            this.dollarLabel.TabIndex = 15;
            // 
            // amountTextBox
            // 
            this.amountTextBox.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.amountTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.amountTextBox.Location = new System.Drawing.Point(20, 3);
            this.amountTextBox.MaxLength = 20;
            this.amountTextBox.Name = "amountTextBox";
            this.amountTextBox.Size = new System.Drawing.Size(52, 21);
            this.amountTextBox.TabIndex = 14;
            this.amountTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.amountTextBox_KeyPress);
            this.amountTextBox.Leave += new System.EventHandler(this.amountTextBox_Leave);
            // 
            // customFee_errorProvider
            // 
            this.customFee_errorProvider.ContainerControl = this;
            // 
            // CustomFeeUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.customFeeTableLayoutPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "CustomFeeUI";
            this.Size = new System.Drawing.Size(380, 27);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.customFeeTableLayoutPanel.ResumeLayout(false);
            this.customFeeTableLayoutPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.deleteImg)).EndInit();
            this.amountPanel.ResumeLayout(false);
            this.amountPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.customFee_errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.ErrorProvider errorProvider;
        private System.Windows.Forms.TableLayoutPanel customFeeTableLayoutPanel;
        private System.Windows.Forms.PictureBox deleteImg;
        private System.Windows.Forms.TextBox customFeeTextBox;
        private System.Windows.Forms.Panel amountPanel;
        private System.Windows.Forms.Label dollarLabel;
        private System.Windows.Forms.TextBox amountTextBox;
        private System.Windows.Forms.ErrorProvider customFee_errorProvider;
    }
}
