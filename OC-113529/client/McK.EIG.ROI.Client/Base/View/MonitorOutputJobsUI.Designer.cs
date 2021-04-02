namespace McK.EIG.ROI.Client.Base.View
{
    partial class MonitorOutputJobsUI
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
            this.lblLoading = new System.Windows.Forms.Label();
            this.loadingCircle = new McK.EIG.ROI.Client.Base.View.Common.LoadingCircle();
            this.SuspendLayout();
            // 
            // closeButton
            // 
            this.closeButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.closeButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.closeButton.Location = new System.Drawing.Point(405, 672);
            this.closeButton.Name = "closeButton";
            this.closeButton.Size = new System.Drawing.Size(87, 27);
            this.closeButton.TabIndex = 0;
            this.closeButton.UseVisualStyleBackColor = true;
            // 
            // webBrowser
            // 
            this.webBrowser.Location = new System.Drawing.Point(0, 0);
            this.webBrowser.MinimumSize = new System.Drawing.Size(23, 23);
            this.webBrowser.Name = "webBrowser";
            this.webBrowser.ScriptErrorsSuppressed = true;
            this.webBrowser.Size = new System.Drawing.Size(901, 666);
            this.webBrowser.TabIndex = 1;
            this.webBrowser.ProgressChanged += new System.Windows.Forms.WebBrowserProgressChangedEventHandler(this.webBrowser_ProgressChanged);
            // 
            // lblLoading
            // 
            this.lblLoading.AutoSize = true;
            this.lblLoading.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLoading.Location = new System.Drawing.Point(420, 346);
            this.lblLoading.Name = "lblLoading";
            this.lblLoading.Size = new System.Drawing.Size(61, 15);
            this.lblLoading.TabIndex = 5;
            this.lblLoading.Text = "Loading...";
            // 
            // loadingCircle
            // 
            this.loadingCircle.Active = false;
            this.loadingCircle.BackColor = System.Drawing.Color.Transparent;
            this.loadingCircle.Color = System.Drawing.Color.ForestGreen;
            this.loadingCircle.InnerCircleRadius = 5;
            this.loadingCircle.Location = new System.Drawing.Point(430, 321);
            this.loadingCircle.Name = "loadingCircle";
            this.loadingCircle.NumberSpoke = 12;
            this.loadingCircle.OuterCircleRadius = 11;
            this.loadingCircle.RotationSpeed = 100;
            this.loadingCircle.Size = new System.Drawing.Size(34, 31);
            this.loadingCircle.SpokeThickness = 2;
            this.loadingCircle.StylePreset = McK.EIG.ROI.Client.Base.View.Common.LoadingCircle.PresetStyle.Theme1;
            this.loadingCircle.TabIndex = 4;
            this.loadingCircle.Text = "Loading...";
            // 
            // MonitorOutputJobsUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.lblLoading);
            this.Controls.Add(this.loadingCircle);
            this.Controls.Add(this.webBrowser);
            this.Controls.Add(this.closeButton);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "MonitorOutputJobsUI";
            this.Size = new System.Drawing.Size(901, 698);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button closeButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.WebBrowser webBrowser;
        private System.Windows.Forms.Label lblLoading;
        private McK.EIG.ROI.Client.Base.View.Common.LoadingCircle loadingCircle;
    }
}
