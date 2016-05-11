namespace McK.EIG.ROI.Client.Reports.View
{
    partial class FindPatientDialogUI
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
            this.findPatientPanel = new System.Windows.Forms.Panel();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.SuspendLayout();
            // 
            // findPatientPanel
            // 
            this.findPatientPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.findPatientPanel.Location = new System.Drawing.Point(0, 0);
            this.findPatientPanel.Name = "findPatientPanel";
            this.findPatientPanel.Size = new System.Drawing.Size(881, 601);
            this.findPatientPanel.TabIndex = 1;
            // 
            // FindPatientDialogUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.findPatientPanel);
            this.Name = "FindPatientDialogUI";
            this.Size = new System.Drawing.Size(881, 601);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel findPatientPanel;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
