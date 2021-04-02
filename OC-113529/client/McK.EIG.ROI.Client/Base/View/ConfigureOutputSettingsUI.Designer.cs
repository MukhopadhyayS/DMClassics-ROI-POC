namespace McK.EIG.ROI.Client.Base.View
{
    partial class ConfigureOutputSettingsUI
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
            this.closeButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.webBrowser = new System.Windows.Forms.WebBrowser();
            this.loadingCircle = new McK.EIG.ROI.Client.Base.View.Common.LoadingCircle();
            this.lblLoading = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // closeButton
            // 
            this.closeButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.closeButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.closeButton.Location = new System.Drawing.Point(252, 672);
            this.closeButton.Name = "closeButton";
            this.closeButton.Size = new System.Drawing.Size(75, 23);
            this.closeButton.TabIndex = 0;
            this.closeButton.UseVisualStyleBackColor = true;
            // 
            // webBrowser
            // 
            this.webBrowser.Location = new System.Drawing.Point(0, 0);
            this.webBrowser.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser.Name = "webBrowser";
            this.webBrowser.Size = new System.Drawing.Size(625, 668);
            this.webBrowser.TabIndex = 1;
            this.webBrowser.ProgressChanged += new System.Windows.Forms.WebBrowserProgressChangedEventHandler(this.webBrowser_ProgressChanged);
            // 
            // loadingCircle
            // 
            this.loadingCircle.Active = false;
            this.loadingCircle.BackColor = System.Drawing.Color.Transparent;
            this.loadingCircle.Color = System.Drawing.Color.ForestGreen;
            this.loadingCircle.InnerCircleRadius = 5;
            this.loadingCircle.Location = new System.Drawing.Point(293, 320);
            this.loadingCircle.Name = "loadingCircle";
            this.loadingCircle.NumberSpoke = 12;
            this.loadingCircle.OuterCircleRadius = 11;
            this.loadingCircle.RotationSpeed = 100;
            this.loadingCircle.Size = new System.Drawing.Size(34, 31);
            this.loadingCircle.SpokeThickness = 2;
            this.loadingCircle.StylePreset = McK.EIG.ROI.Client.Base.View.Common.LoadingCircle.PresetStyle.Theme1;
            this.loadingCircle.TabIndex = 2;
            this.loadingCircle.Text = "Loading...";
            // 
            // lblLoading
            // 
            this.lblLoading.AutoSize = true;
            this.lblLoading.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLoading.Location = new System.Drawing.Point(284, 345);
            this.lblLoading.Name = "lblLoading";
            this.lblLoading.Size = new System.Drawing.Size(61, 15);
            this.lblLoading.TabIndex = 3;
            this.lblLoading.Text = "Loading...";
            // 
            // ConfigureOutputSettingsUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.lblLoading);
            this.Controls.Add(this.loadingCircle);
            this.Controls.Add(this.webBrowser);
            this.Controls.Add(this.closeButton);
            this.Name = "ConfigureOutputSettingsUI";
            this.Size = new System.Drawing.Size(605, 698);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button closeButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.WebBrowser webBrowser;
        private McK.EIG.ROI.Client.Base.View.Common.LoadingCircle loadingCircle;
        private System.Windows.Forms.Label lblLoading;
    }
}
