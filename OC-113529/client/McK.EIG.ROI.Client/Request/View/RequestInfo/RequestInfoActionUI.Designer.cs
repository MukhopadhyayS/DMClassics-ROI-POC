namespace McK.EIG.ROI.Client.Request.View.RequestInfo
{
    partial class RequestInfoActionUI
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
            this.infoActionPanel = new System.Windows.Forms.TableLayoutPanel();
            this.leftPanel = new System.Windows.Forms.Panel();
            this.requestLockedImg = new System.Windows.Forms.PictureBox();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.rightPanel = new System.Windows.Forms.Panel();
            this.viewAuthRequestButton = new System.Windows.Forms.Button();
            this.deleteRequestButton = new System.Windows.Forms.Button();
            this.revertButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.infoActionPanel.SuspendLayout();
            this.leftPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requestLockedImg)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            this.rightPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // infoActionPanel
            // 
            this.infoActionPanel.AutoScroll = true;
            this.infoActionPanel.AutoScrollMinSize = new System.Drawing.Size(700, 0);
            this.infoActionPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(221)))), ((int)(((byte)(231)))), ((int)(((byte)(253)))));
            this.infoActionPanel.ColumnCount = 2;
            this.infoActionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 27.57475F));
            this.infoActionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 72.42525F));
            this.infoActionPanel.Controls.Add(this.leftPanel, 0, 0);
            this.infoActionPanel.Controls.Add(this.rightPanel, 1, 0);
            this.infoActionPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.infoActionPanel.Location = new System.Drawing.Point(0, 0);
            this.infoActionPanel.Name = "infoActionPanel";
            this.infoActionPanel.RowCount = 1;
            this.infoActionPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.infoActionPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.infoActionPanel.Size = new System.Drawing.Size(602, 40);
            this.infoActionPanel.TabIndex = 1;
            // 
            // leftPanel
            // 
            this.leftPanel.Controls.Add(this.requestLockedImg);
            this.leftPanel.Controls.Add(this.requiredLabel);
            this.leftPanel.Controls.Add(this.requiredImg);
            this.leftPanel.Location = new System.Drawing.Point(3, 3);
            this.leftPanel.Name = "leftPanel";
            this.leftPanel.Size = new System.Drawing.Size(124, 34);
            this.leftPanel.TabIndex = 0;
            // 
            // requestLockedImg
            // 
            this.requestLockedImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requestLockedImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requestLockedImg.Image = global::McK.EIG.ROI.Client.Resources.images.in_use_icon;
            this.requestLockedImg.Location = new System.Drawing.Point(82, 9);
            this.requestLockedImg.Name = "requestLockedImg";
            this.requestLockedImg.Size = new System.Drawing.Size(18, 19);
            this.requestLockedImg.TabIndex = 3;
            this.requestLockedImg.TabStop = false;
            this.requestLockedImg.Visible = false;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.requiredLabel.Location = new System.Drawing.Point(14, 11);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 15);
            this.requiredLabel.TabIndex = 2;
            // 
            // requiredImg
            // 
            this.requiredImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImg.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.Location = new System.Drawing.Point(3, 13);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(13, 10);
            this.requiredImg.TabIndex = 1;
            this.requiredImg.TabStop = false;
            // 
            // rightPanel
            // 
            this.rightPanel.Controls.Add(this.viewAuthRequestButton);
            this.rightPanel.Controls.Add(this.deleteRequestButton);
            this.rightPanel.Controls.Add(this.revertButton);
            this.rightPanel.Controls.Add(this.saveButton);
            this.rightPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rightPanel.Location = new System.Drawing.Point(196, 3);
            this.rightPanel.Name = "rightPanel";
            this.rightPanel.Size = new System.Drawing.Size(501, 34);
            this.rightPanel.TabIndex = 1;
            // 
            // viewAuthRequestButton
            // 
            this.viewAuthRequestButton.Enabled = false;
            this.viewAuthRequestButton.Location = new System.Drawing.Point(3, 9);
            this.viewAuthRequestButton.Name = "viewAuthRequestButton";
            this.viewAuthRequestButton.Size = new System.Drawing.Size(151, 23);
            this.viewAuthRequestButton.TabIndex = 0;
            this.viewAuthRequestButton.UseVisualStyleBackColor = true;
            // 
            // deleteRequestButton
            // 
            this.deleteRequestButton.AutoSize = true;
            this.deleteRequestButton.Enabled = false;
            this.deleteRequestButton.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.deleteRequestButton.Location = new System.Drawing.Point(320, 9);
            this.deleteRequestButton.Name = "deleteRequestButton";
            this.deleteRequestButton.Size = new System.Drawing.Size(107, 23);
            this.deleteRequestButton.TabIndex = 3;
            this.deleteRequestButton.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.deleteRequestButton.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.deleteRequestButton.UseVisualStyleBackColor = true;
            // 
            // revertButton
            // 
            this.revertButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.revertButton.Location = new System.Drawing.Point(239, 9);
            this.revertButton.Name = "revertButton";
            this.revertButton.Size = new System.Drawing.Size(75, 23);
            this.revertButton.TabIndex = 2;
            this.revertButton.UseVisualStyleBackColor = true;
            // 
            // saveButton
            // 
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(158, 9);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 23);
            this.saveButton.TabIndex = 1;
            this.saveButton.UseVisualStyleBackColor = true;
            // 
            // RequestInfoActionUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.infoActionPanel);
            this.Name = "RequestInfoActionUI";
            this.Size = new System.Drawing.Size(602, 40);
            this.infoActionPanel.ResumeLayout(false);
            this.leftPanel.ResumeLayout(false);
            this.leftPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requestLockedImg)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            this.rightPanel.ResumeLayout(false);
            this.rightPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel infoActionPanel;
        private System.Windows.Forms.Panel leftPanel;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.PictureBox requiredImg;
        private System.Windows.Forms.Panel rightPanel;
        private System.Windows.Forms.Button deleteRequestButton;
        private System.Windows.Forms.Button revertButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.PictureBox requestLockedImg;
        private System.Windows.Forms.Button viewAuthRequestButton;
    }
}
