namespace McK.EIG.ROI.Client.Patient.View.RequestHistory
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
            this.components = new System.ComponentModel.Container();
            this.requestHistoryPanel = new System.Windows.Forms.TableLayoutPanel();
            this.listPanel = new System.Windows.Forms.Panel();
            this.encounterPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.filterByEncounterLabel = new System.Windows.Forms.Label();
            this.filterLabel = new System.Windows.Forms.Label();
            this.setUpdateButton = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.requestHistoryPanel.SuspendLayout();
            this.encounterPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // requestHistoryPanel
            // 
            this.requestHistoryPanel.ColumnCount = 1;
            this.requestHistoryPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.requestHistoryPanel.Controls.Add(this.listPanel, 0, 1);
            this.requestHistoryPanel.Controls.Add(this.encounterPanel, 0, 0);
            this.requestHistoryPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.requestHistoryPanel.Location = new System.Drawing.Point(0, 0);
            this.requestHistoryPanel.Name = "requestHistoryPanel";
            this.requestHistoryPanel.RowCount = 2;
            this.requestHistoryPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 7.630522F));
            this.requestHistoryPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 92.36948F));
            this.requestHistoryPanel.Size = new System.Drawing.Size(768, 498);
            this.requestHistoryPanel.TabIndex = 0;
            // 
            // listPanel
            // 
            this.listPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listPanel.Location = new System.Drawing.Point(3, 40);
            this.listPanel.Name = "listPanel";
            this.listPanel.Size = new System.Drawing.Size(762, 455);
            this.listPanel.TabIndex = 1;
            // 
            // encounterPanel
            // 
            this.encounterPanel.Controls.Add(this.filterByEncounterLabel);
            this.encounterPanel.Controls.Add(this.filterLabel);
            this.encounterPanel.Controls.Add(this.setUpdateButton);
            this.encounterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.encounterPanel.Location = new System.Drawing.Point(3, 3);
            this.encounterPanel.Name = "encounterPanel";
            this.encounterPanel.Size = new System.Drawing.Size(762, 31);
            this.encounterPanel.TabIndex = 15;
            // 
            // filterByEncounterLabel
            // 
            this.filterByEncounterLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.filterByEncounterLabel.AutoSize = true;
            this.filterByEncounterLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.filterByEncounterLabel.Location = new System.Drawing.Point(3, 7);
            this.filterByEncounterLabel.Name = "filterByEncounterLabel";
            this.filterByEncounterLabel.Size = new System.Drawing.Size(0, 15);
            this.filterByEncounterLabel.TabIndex = 12;
            // 
            // filterLabel
            // 
            this.filterLabel.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.filterLabel.AutoSize = true;
            this.filterLabel.Enabled = false;
            this.filterLabel.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.filterLabel.Location = new System.Drawing.Point(9, 7);
            this.filterLabel.Name = "filterLabel";
            this.filterLabel.Size = new System.Drawing.Size(0, 15);
            this.filterLabel.TabIndex = 15;
            // 
            // setUpdateButton
            // 
            this.setUpdateButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.setUpdateButton.Enabled = false;
            this.setUpdateButton.Font = new System.Drawing.Font("Arial", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.setUpdateButton.Location = new System.Drawing.Point(15, 3);
            this.setUpdateButton.Name = "setUpdateButton";
            this.setUpdateButton.Size = new System.Drawing.Size(84, 23);
            this.setUpdateButton.TabIndex = 0;
            this.setUpdateButton.UseVisualStyleBackColor = true;
            this.setUpdateButton.Click += new System.EventHandler(this.setUpdateButton_Click);
            // 
            // RequestHistoryUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.Controls.Add(this.requestHistoryPanel);
            this.Name = "RequestHistoryUI";
            this.Size = new System.Drawing.Size(768, 498);
            this.requestHistoryPanel.ResumeLayout(false);
            this.encounterPanel.ResumeLayout(false);
            this.encounterPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel requestHistoryPanel;
        private System.Windows.Forms.Panel listPanel;
        private System.Windows.Forms.FlowLayoutPanel encounterPanel;
        private System.Windows.Forms.Label filterLabel;
        private System.Windows.Forms.Label filterByEncounterLabel;
        private System.Windows.Forms.Button setUpdateButton;
        private System.Windows.Forms.ToolTip toolTip;
    }
}
