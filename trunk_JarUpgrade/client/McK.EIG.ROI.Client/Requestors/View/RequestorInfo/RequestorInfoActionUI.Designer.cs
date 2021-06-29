namespace McK.EIG.ROI.Client.Requestors.View.RequestorInfo
{
    partial class RequestorInfoActionUI
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
            this.infoActionPanel = new System.Windows.Forms.TableLayoutPanel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.requiredLabel = new System.Windows.Forms.Label();
            this.requiredImg = new System.Windows.Forms.PictureBox();
            this.rightPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.deleteRequestorButton = new System.Windows.Forms.Button();
            this.revertButton = new System.Windows.Forms.Button();
            this.saveButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.infoActionPanel.SuspendLayout();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).BeginInit();
            this.rightPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // infoActionPanel
            // 
            this.infoActionPanel.AutoScroll = true;
            this.infoActionPanel.AutoScrollMinSize = new System.Drawing.Size(500, 0);
            this.infoActionPanel.ColumnCount = 2;
            this.infoActionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 26.63755F));
            this.infoActionPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 73.36244F));
            this.infoActionPanel.Controls.Add(this.panel1, 0, 0);
            this.infoActionPanel.Controls.Add(this.rightPanel, 1, 0);
            this.infoActionPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.infoActionPanel.Location = new System.Drawing.Point(0, 0);
            this.infoActionPanel.Name = "infoActionPanel";
            this.infoActionPanel.RowCount = 1;
            this.infoActionPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.infoActionPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.infoActionPanel.Size = new System.Drawing.Size(458, 32);
            this.infoActionPanel.TabIndex = 0;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.requiredLabel);
            this.panel1.Controls.Add(this.requiredImg);
            this.panel1.Location = new System.Drawing.Point(3, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(115, 26);
            this.panel1.TabIndex = 0;
            // 
            // requiredLabel
            // 
            this.requiredLabel.AutoSize = true;
            this.requiredLabel.Location = new System.Drawing.Point(14, 7);
            this.requiredLabel.Name = "requiredLabel";
            this.requiredLabel.Size = new System.Drawing.Size(0, 13);
            this.requiredLabel.TabIndex = 2;
            // 
            // requiredImg
            // 
            this.requiredImg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.requiredImg.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.requiredImg.Image = global::McK.EIG.ROI.Client.Resources.images.required;
            this.requiredImg.Location = new System.Drawing.Point(3, 9);
            this.requiredImg.Name = "requiredImg";
            this.requiredImg.Size = new System.Drawing.Size(13, 10);
            this.requiredImg.TabIndex = 1;
            this.requiredImg.TabStop = false;
            // 
            // rightPanel
            // 
            this.rightPanel.Controls.Add(this.deleteRequestorButton);
            this.rightPanel.Controls.Add(this.revertButton);
            this.rightPanel.Controls.Add(this.saveButton);
            this.rightPanel.FlowDirection = System.Windows.Forms.FlowDirection.RightToLeft;
            this.rightPanel.Location = new System.Drawing.Point(189, 3);
            this.rightPanel.Name = "rightPanel";
            this.rightPanel.Size = new System.Drawing.Size(331, 26);
            this.rightPanel.TabIndex = 1;
            // 
            // deleteRequestorButton
            // 
            this.deleteRequestorButton.AutoSize = true;
            this.deleteRequestorButton.BackColor = System.Drawing.Color.White;
            this.deleteRequestorButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.deleteRequestorButton.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.deleteRequestorButton.Location = new System.Drawing.Point(221, 3);
            this.deleteRequestorButton.Name = "deleteRequestorButton";
            this.deleteRequestorButton.Size = new System.Drawing.Size(107, 23);
            this.deleteRequestorButton.TabIndex = 3;
            this.deleteRequestorButton.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.deleteRequestorButton.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.deleteRequestorButton.UseVisualStyleBackColor = false;
            // 
            // revertButton
            // 
            this.revertButton.BackColor = System.Drawing.Color.White;
            this.revertButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.revertButton.Location = new System.Drawing.Point(140, 3);
            this.revertButton.Name = "revertButton";
            this.revertButton.Size = new System.Drawing.Size(75, 23);
            this.revertButton.TabIndex = 2;
            this.revertButton.UseVisualStyleBackColor = false;
            // 
            // saveButton
            // 
            this.saveButton.BackColor = System.Drawing.Color.White;
            this.saveButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.saveButton.Location = new System.Drawing.Point(59, 3);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 23);
            this.saveButton.TabIndex = 1;
            this.saveButton.UseVisualStyleBackColor = false;
            // 
            // RequestorInfoActionUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.infoActionPanel);
            this.Name = "RequestorInfoActionUI";
            this.Size = new System.Drawing.Size(458, 32);
            this.infoActionPanel.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.requiredImg)).EndInit();
            this.rightPanel.ResumeLayout(false);
            this.rightPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel infoActionPanel;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.PictureBox requiredImg;
        private System.Windows.Forms.Label requiredLabel;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.FlowLayoutPanel rightPanel;
        private System.Windows.Forms.Button deleteRequestorButton;
        private System.Windows.Forms.Button revertButton;
        private System.Windows.Forms.Button saveButton;
    }
}
