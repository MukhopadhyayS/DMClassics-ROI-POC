namespace McK.EIG.ROI.Client.Requestors.View.RequestHistory
{
    partial class RequestHistoryUI
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
            this.listPanel = new System.Windows.Forms.Panel();
            this.SuspendLayout();
            // 
            // listPanel
            // 
            this.listPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listPanel.Location = new System.Drawing.Point(0, 0);
            this.listPanel.Name = "listPanel";
            this.listPanel.Size = new System.Drawing.Size(768, 498);
            this.listPanel.TabIndex = 0;
            // 
            // RequestHistoryUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.listPanel);
            this.Name = "RequestHistoryUI";
            this.Size = new System.Drawing.Size(768, 498);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel listPanel;
    }
}
