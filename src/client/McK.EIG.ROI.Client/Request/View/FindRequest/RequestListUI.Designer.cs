namespace McK.EIG.ROI.Client.Request.View.FindRequest
{
    partial class RequestListUI
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
            this.requestTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.messageRequestLabel = new System.Windows.Forms.Label();
            this.listPanel = new System.Windows.Forms.Panel();
            this.requestTableLayoutPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // requestTableLayoutPanel
            // 
            this.requestTableLayoutPanel.BackColor = System.Drawing.Color.White;
            this.requestTableLayoutPanel.ColumnCount = 1;
            this.requestTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.requestTableLayoutPanel.Controls.Add(this.messageRequestLabel, 0, 0);
            this.requestTableLayoutPanel.Controls.Add(this.listPanel, 0, 1);
            this.requestTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.requestTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.requestTableLayoutPanel.Name = "requestTableLayoutPanel";
            this.requestTableLayoutPanel.RowCount = 2;
            this.requestTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 6.142506F));
            this.requestTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 93.85749F));
            this.requestTableLayoutPanel.Size = new System.Drawing.Size(707, 423);
            this.requestTableLayoutPanel.TabIndex = 0;
            // 
            // messageRequestLabel
            // 
            this.messageRequestLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.messageRequestLabel.AutoSize = true;
            this.messageRequestLabel.Location = new System.Drawing.Point(3, 6);
            this.messageRequestLabel.Name = "messageRequestLabel";
            this.messageRequestLabel.Size = new System.Drawing.Size(0, 13);
            this.messageRequestLabel.TabIndex = 0;
            // 
            // listPanel
            // 
            this.listPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listPanel.Location = new System.Drawing.Point(3, 28);
            this.listPanel.Name = "listPanel";
            this.listPanel.Size = new System.Drawing.Size(701, 392);
            this.listPanel.TabIndex = 1;
            // 
            // RequestListUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.requestTableLayoutPanel);
            this.Name = "RequestListUI";
            this.Size = new System.Drawing.Size(707, 423);
            this.requestTableLayoutPanel.ResumeLayout(false);
            this.requestTableLayoutPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel requestTableLayoutPanel;
        private System.Windows.Forms.Label messageRequestLabel;
        private System.Windows.Forms.Panel listPanel;

    }
}
