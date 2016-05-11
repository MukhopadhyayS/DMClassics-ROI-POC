namespace McK.EIG.ROI.Client.Admin.View.Billing.BillingTiers
{
    partial class PageRateTierGroupUI
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
            this.addNewTierButton = new System.Windows.Forms.Button();
            this.errorProvider1 = new System.Windows.Forms.ErrorProvider(this.components);
            this.rateTierTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.bottomPanel = new System.Windows.Forms.Panel();
            this.requiredImage = new System.Windows.Forms.PictureBox();
            this.perPageLabel = new System.Windows.Forms.Label();
            this.otherChargeTextBox = new System.Windows.Forms.TextBox();
            this.amountLabel = new System.Windows.Forms.Label();
            this.rateOuterPanel = new System.Windows.Forms.Panel();
            this.pageTiersContainerFlowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider1)).BeginInit();
            this.rateTierTableLayoutPanel.SuspendLayout();
            this.bottomPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).BeginInit();
            this.rateOuterPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // addNewTierButton
            // 
            this.addNewTierButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.addNewTierButton.Font = new System.Drawing.Font("Arial", 9F);
            this.addNewTierButton.Location = new System.Drawing.Point(333, 133);
            this.addNewTierButton.Name = "addNewTierButton";
            this.addNewTierButton.Size = new System.Drawing.Size(110, 23);
            this.addNewTierButton.TabIndex = 3;
            this.addNewTierButton.UseVisualStyleBackColor = true;
            this.addNewTierButton.Click += new System.EventHandler(this.AddNewTierBtn_Click);
            // 
            // errorProvider1
            // 
            this.errorProvider1.ContainerControl = this;
            // 
            // rateTierTableLayoutPanel
            // 
            this.rateTierTableLayoutPanel.ColumnCount = 1;
            this.rateTierTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.rateTierTableLayoutPanel.Controls.Add(this.bottomPanel, 0, 1);
            this.rateTierTableLayoutPanel.Controls.Add(this.addNewTierButton, 0, 2);
            this.rateTierTableLayoutPanel.Controls.Add(this.rateOuterPanel, 0, 0);
            this.rateTierTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rateTierTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.rateTierTableLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.rateTierTableLayoutPanel.Name = "rateTierTableLayoutPanel";
            this.rateTierTableLayoutPanel.RowCount = 3;
            this.rateTierTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.rateTierTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.rateTierTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.rateTierTableLayoutPanel.Size = new System.Drawing.Size(446, 160);
            this.rateTierTableLayoutPanel.TabIndex = 6;
            this.rateTierTableLayoutPanel.TabStop = true;
            // 
            // bottomPanel
            // 
            this.bottomPanel.BackColor = System.Drawing.Color.Transparent;
            this.bottomPanel.Controls.Add(this.requiredImage);
            this.bottomPanel.Controls.Add(this.perPageLabel);
            this.bottomPanel.Controls.Add(this.otherChargeTextBox);
            this.bottomPanel.Controls.Add(this.amountLabel);
            this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.bottomPanel.Location = new System.Drawing.Point(3, 103);
            this.bottomPanel.Name = "bottomPanel";
            this.bottomPanel.Size = new System.Drawing.Size(440, 24);
            this.bottomPanel.TabIndex = 2;
            // 
            // requiredImage
            // 
            this.requiredImage.BackgroundImage = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImage.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.requiredImage.ErrorImage = null;
            this.requiredImage.Location = new System.Drawing.Point(37, 9);
            this.requiredImage.Name = "requiredImage";
            this.requiredImage.Size = new System.Drawing.Size(9, 9);
            this.requiredImage.TabIndex = 6;
            this.requiredImage.TabStop = false;
            // 
            // perPageLabel
            // 
            this.perPageLabel.AutoSize = true;
            this.perPageLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.perPageLabel.Location = new System.Drawing.Point(277, 6);
            this.perPageLabel.Name = "perPageLabel";
            this.perPageLabel.Size = new System.Drawing.Size(0, 15);
            this.perPageLabel.TabIndex = 5;
            // 
            // otherChargeTextBox
            // 
            this.otherChargeTextBox.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.otherChargeTextBox.Location = new System.Drawing.Point(228, 2);
            this.otherChargeTextBox.MaxLength = 5;
            this.otherChargeTextBox.Name = "otherChargeTextBox";
            this.otherChargeTextBox.Size = new System.Drawing.Size(40, 21);
            this.otherChargeTextBox.TabIndex = 2;
            this.otherChargeTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.otherChargeTextBox_KeyPress);
            // 
            // amountLabel
            // 
            this.amountLabel.AutoSize = true;
            this.amountLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.amountLabel.Location = new System.Drawing.Point(43, 6);
            this.amountLabel.Name = "amountLabel";
            this.amountLabel.Size = new System.Drawing.Size(0, 15);
            this.amountLabel.TabIndex = 3;
            this.amountLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // rateOuterPanel
            // 
            this.rateOuterPanel.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.rateOuterPanel.Controls.Add(this.pageTiersContainerFlowLayoutPanel);
            this.rateOuterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rateOuterPanel.Location = new System.Drawing.Point(0, 0);
            this.rateOuterPanel.Margin = new System.Windows.Forms.Padding(0);
            this.rateOuterPanel.Name = "rateOuterPanel";
            this.rateOuterPanel.Padding = new System.Windows.Forms.Padding(1);
            this.rateOuterPanel.Size = new System.Drawing.Size(446, 100);
            this.rateOuterPanel.TabIndex = 1;
            // 
            // pageTiersContainerFlowLayoutPanel
            // 
            this.pageTiersContainerFlowLayoutPanel.AutoScroll = true;
            this.pageTiersContainerFlowLayoutPanel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.pageTiersContainerFlowLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pageTiersContainerFlowLayoutPanel.Location = new System.Drawing.Point(1, 1);
            this.pageTiersContainerFlowLayoutPanel.Margin = new System.Windows.Forms.Padding(0);
            this.pageTiersContainerFlowLayoutPanel.Name = "pageTiersContainerFlowLayoutPanel";
            this.pageTiersContainerFlowLayoutPanel.Size = new System.Drawing.Size(444, 98);
            this.pageTiersContainerFlowLayoutPanel.TabIndex = 0;
            // 
            // PageRateTierGroupUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.rateTierTableLayoutPanel);
            this.Name = "PageRateTierGroupUI";
            this.Size = new System.Drawing.Size(446, 160);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider1)).EndInit();
            this.rateTierTableLayoutPanel.ResumeLayout(false);
            this.bottomPanel.ResumeLayout(false);
            this.bottomPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImage)).EndInit();
            this.rateOuterPanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button addNewTierButton;
        private System.Windows.Forms.ErrorProvider errorProvider1;
        private System.Windows.Forms.TableLayoutPanel rateTierTableLayoutPanel;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.PictureBox requiredImage;
        private System.Windows.Forms.Label perPageLabel;
        private System.Windows.Forms.TextBox otherChargeTextBox;
        private System.Windows.Forms.Label amountLabel;
        private System.Windows.Forms.Panel rateOuterPanel;
        private System.Windows.Forms.FlowLayoutPanel pageTiersContainerFlowLayoutPanel;
    }
}
