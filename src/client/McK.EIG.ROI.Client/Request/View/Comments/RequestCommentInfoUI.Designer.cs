namespace McK.EIG.ROI.Client.Request.View.Comments
{
    partial class RequestCommentInfoUI
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
            this.commentTableLayout = new System.Windows.Forms.TableLayoutPanel();
            this.newCommentTextBox = new System.Windows.Forms.TextBox();
            this.newCommentLabel = new System.Windows.Forms.Label();
            this.buttonPanel = new System.Windows.Forms.Panel();
            this.cancelButton = new System.Windows.Forms.Button();
            this.commentSaveButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.outerPanel = new System.Windows.Forms.Panel();
            this.errorProvider = new System.Windows.Forms.ErrorProvider(this.components);
            this.commentTableLayout.SuspendLayout();
            this.buttonPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).BeginInit();
            this.SuspendLayout();
            // 
            // commentTableLayout
            // 
            this.commentTableLayout.ColumnCount = 2;
            this.commentTableLayout.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 18.93333F));
            this.commentTableLayout.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 81.06667F));
            this.commentTableLayout.Controls.Add(this.newCommentTextBox, 1, 0);
            this.commentTableLayout.Controls.Add(this.newCommentLabel, 0, 0);
            this.commentTableLayout.Controls.Add(this.buttonPanel, 1, 1);
            this.commentTableLayout.Dock = System.Windows.Forms.DockStyle.Fill;
            this.commentTableLayout.Location = new System.Drawing.Point(0, 10);
            this.commentTableLayout.Name = "commentTableLayout";
            this.commentTableLayout.RowCount = 2;
            this.commentTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 75.86207F));
            this.commentTableLayout.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 24.13793F));
            this.commentTableLayout.Size = new System.Drawing.Size(748, 138);
            this.commentTableLayout.TabIndex = 0;
            // 
            // newCommentTextBox
            // 
            this.newCommentTextBox.AcceptsReturn = true;
            this.newCommentTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)));
            this.newCommentTextBox.Location = new System.Drawing.Point(144, 3);
            this.newCommentTextBox.MaxLength = 1000;
            this.newCommentTextBox.Multiline = true;
            this.newCommentTextBox.Name = "newCommentTextBox";
            this.newCommentTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.newCommentTextBox.Size = new System.Drawing.Size(530, 98);
            this.newCommentTextBox.TabIndex = 0;
            this.newCommentTextBox.TextChanged += new System.EventHandler(this.newCommentTextBox_TextChanged);
            // 
            // newCommentLabel
            // 
            this.newCommentLabel.AutoSize = true;
            this.newCommentLabel.Location = new System.Drawing.Point(3, 0);
            this.newCommentLabel.Name = "newCommentLabel";
            this.newCommentLabel.Padding = new System.Windows.Forms.Padding(0, 6, 0, 0);
            this.newCommentLabel.Size = new System.Drawing.Size(0, 21);
            this.newCommentLabel.TabIndex = 1;
            this.newCommentLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // buttonPanel
            // 
            this.buttonPanel.Controls.Add(this.cancelButton);
            this.buttonPanel.Controls.Add(this.commentSaveButton);
            this.buttonPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.buttonPanel.Location = new System.Drawing.Point(144, 107);
            this.buttonPanel.Name = "buttonPanel";
            this.buttonPanel.Size = new System.Drawing.Size(601, 28);
            this.buttonPanel.TabIndex = 2;
            // 
            // cancelButton
            // 
            this.cancelButton.AutoSize = true;
            this.cancelButton.Location = new System.Drawing.Point(110, 2);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 25);
            this.cancelButton.TabIndex = 1;
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // commentSaveButton
            // 
            this.commentSaveButton.AutoSize = true;
            this.commentSaveButton.Location = new System.Drawing.Point(3, 2);
            this.commentSaveButton.Name = "commentSaveButton";
            this.commentSaveButton.Size = new System.Drawing.Size(102, 25);
            this.commentSaveButton.TabIndex = 0;
            this.commentSaveButton.UseVisualStyleBackColor = true;
            this.commentSaveButton.Click += new System.EventHandler(this.commentSaveButton_Click);
            // 
            // outerPanel
            // 
            this.outerPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.outerPanel.Location = new System.Drawing.Point(0, 0);
            this.outerPanel.Name = "outerPanel";
            this.outerPanel.Padding = new System.Windows.Forms.Padding(0, 50, 0, 0);
            this.outerPanel.Size = new System.Drawing.Size(748, 10);
            this.outerPanel.TabIndex = 1;
            // 
            // errorProvider
            // 
            this.errorProvider.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.NeverBlink;
            this.errorProvider.ContainerControl = this;
            this.errorProvider.RightToLeft = true;
            // 
            // RequestCommentInfoUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.commentTableLayout);
            this.Controls.Add(this.outerPanel);
            this.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "RequestCommentInfoUI";
            this.Size = new System.Drawing.Size(748, 148);
            this.commentTableLayout.ResumeLayout(false);
            this.commentTableLayout.PerformLayout();
            this.buttonPanel.ResumeLayout(false);
            this.buttonPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel commentTableLayout;
        private System.Windows.Forms.TextBox newCommentTextBox;
        private System.Windows.Forms.Label newCommentLabel;
        private System.Windows.Forms.Panel buttonPanel;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button commentSaveButton;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Panel outerPanel;
        private System.Windows.Forms.ErrorProvider errorProvider;
    }
}
