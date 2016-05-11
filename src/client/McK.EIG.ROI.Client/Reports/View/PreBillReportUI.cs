using System;
using System.Collections;
using System.Globalization;
using System.Resources;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.IO;

using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Reports.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;


namespace McK.EIG.ROI.Client.Reports.View
{
    public partial class PreBillReportUI : ROIBaseUI, IReportCriteria
    {
        private static string subTitle;
        private static string resultType;

        public PreBillReportUI()
        {
            InitializeComponent();
            rangeBar1.MaxTextBox.Leave += new EventHandler(TextBox_TextChanged);
            rangeBar1.MinTextBox.Leave += new EventHandler(TextBox_TextChanged);
            
        }

        void TextBox_TextChanged(object sender, EventArgs e)
        {
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        } 

                public void ClearControls()
        {
        }
        public bool ValidatePrimaryFields()
        {
            //CR379025
            //CR#388270
            bool returnVal = FirstCheckedListUI.HasCheckedItems() && SecondCheckedListUI.HasCheckedItems(); 
                //&& ((rangeBar1.RangeMaximum - rangeBar1.RangeMinimum) > 0);
            return returnVal;
        }

        public void  PrePopulate()
        {

            
            PopulateBillingLocation();
            PopulateRequestorTypes();
            PopulateBalanceOptions();
            panel1.VerticalScroll.Enabled = true;
        }

        private void PopulateBalanceOptions()
        {
            cmbBoxBalance.Items.Insert(0, "AtLeast");
            cmbBoxBalance.Items.Add("AtMost");
            cmbBoxBalance.Items.Add("Equal");
            cmbBoxBalance.SelectedIndex = 0;
            cmbBoxBalance.DropDownStyle = ComboBoxStyle.DropDownList;
        }


      
        public override void Localize()
        {
            base.Localize();
            FirstCheckedListUI.TitleKey = "reportListBox.BillingLocation";
            SecondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            FirstCheckedListUI.SetPane(Pane);
            FirstCheckedListUI.SetExecutionContext(Context);
            FirstCheckedListUI.Localize();
            SecondCheckedListUI.SetPane(Pane);
            SecondCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.Localize();
          
        }
        /// <summary>
        /// Gets all requestor types
        /// </summary>
        private void PopulateRequestorTypes()
        {
            SecondCheckedListUI.DisableEvents();
            ReportBaseCriteriaUI reportBase = new ReportBaseCriteriaUI();
            SecondCheckedListUI.CriteriaCheckedListBox.DataSource = reportBase.RetrieveRequestorTypes();
            SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            SecondCheckedListUI.CriteriaCheckedListBox.ValueMember = "id";
            SecondCheckedListUI.EnableEvents();
        }


        /// <summary>
        /// Gets all facilities and display in checked list box.
        /// </summary>
        private void PopulateBillingLocation()
        {
            FirstCheckedListUI.DisableEvents();
            FirstCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.HPFFacilities;   //RetrieveHPFFacilities();
            FirstCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            FirstCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
            FirstCheckedListUI.EnableEvents();
        }

        public object GetData(object parameters)
        {
            Hashtable criteriaHt = (Hashtable)parameters;
            StringBuilder checkedFirstListValues = GetFacilityCodes();
            StringBuilder checkedSecondListValues = SecondCheckedListUI.GetData();
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedSecondListValues);
            criteriaHt.Add(ROIConstants.ReportRequestorName, textBoxRequestorName.Text);
            criteriaHt.Add(ROIConstants.ReportRequestStatus, "PreBilled");
            criteriaHt.Add(ROIConstants.ReportAgingStart,rangeBar1.RangeMaximum.ToString());
            criteriaHt.Add(ROIConstants.ReportAgingEnd, rangeBar1.RangeMinimum.ToString());
            criteriaHt.Add(ROIConstants.ReportBalance, textBoxBalance.Text);
            if (textBoxBalance.Text == "")
                criteriaHt.Add(ROIConstants.ReportBalanceCriterion, "");
            else
                criteriaHt.Add(ROIConstants.ReportBalanceCriterion, cmbBoxBalance.Text);

            if (String.IsNullOrEmpty(resultType))
                criteriaHt.Add("resultType", "\"Summary - Requestor Type\"");
            else
            {
                criteriaHt.Add("resultType", "\"" + resultType + "\"");
            }

            StringBuilder content = new StringBuilder();
            char separator = '"';
            content.AppendLine("Facilities, View By");
            content.AppendLine(separator + checkedFirstListValues.ToString() + separator + "," + criteriaHt["resultType"]);
            subTitle = content.ToString();
            resultType = string.Empty;
            return criteriaHt;
        }

        public void CreateSubtitleFile(string filePath)
        {
            try
            {
                if (File.Exists(filePath))
                {
                    File.Delete(filePath);
                }
                File.AppendAllText(filePath, subTitle);
            }
            catch (Exception e)
            {
                throw new ROIException(ROIErrorCodes.SubtitleDatasourceFailed);
            }
        }
        public void SetResultType(string result)
        {
            resultType = result;
        }

        private StringBuilder GetFacilityCodes()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)FirstCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Name));
            }

            return checkedItems;

        }

        private void textBoxRequestorName_TextChanged(object sender, EventArgs e)
        {
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        }

        private void rangeBar1_RangeChanged(object sender, EventArgs e)
        {
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        }

        private void pictureBox2_Click(object sender, EventArgs e)
        {

        }

        
       

    }

    
}
