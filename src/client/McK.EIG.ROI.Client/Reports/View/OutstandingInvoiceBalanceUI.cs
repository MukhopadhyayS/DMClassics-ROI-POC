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
    public partial class OutstandingInvoiceBalanceUI : ROIBaseUI, IReportCriteria
    {

        private static string subTitle;
        private static string resultType;
        //CR#377377
        private static string atmostValue;

        public OutstandingInvoiceBalanceUI()
        {
            InitializeComponent();
            RangeBar1.MaxTextBox.Leave += new EventHandler(TextBox_TextChanged);
            RangeBar1.MinTextBox.Leave += new EventHandler(TextBox_TextChanged);
        }

        public void PrePopulate()
        {
            PopulateBillingLocation();
            PopulateRequestorTypes();
            PopulateRequestorStatus();
            //PopulateBalanceOptions();
            PopulateBalanceDueOperators();
            panel1.VerticalScroll.Enabled = true;
         }

        /// <summary>
        /// Populate balance due operator list.
        /// </summary>
        private void PopulateBalanceDueOperators()
        {
            IList balanceDueOperators = EnumUtilities.ToList(typeof(BalanceDueOperator));

            cmbBoxBal.DisplayMember = "value";
            cmbBoxBal.ValueMember = "key";
            cmbBoxBal.DataSource = balanceDueOperators;
            cmbBoxBal.SelectedIndex = 0;
        }
        public override void Localize()
        {
            base.Localize();
            firstCheckedListUI.TitleKey = "reportListBox.BillingLocation";
            secondCheckedListUI.TitleKey = "reportListBox.RequestorType";
            thirdCheckedListUI.TitleKey = "reportListBox.RequestStatus";
            firstCheckedListUI.SetPane(Pane);
            firstCheckedListUI.SetExecutionContext(Context);
            firstCheckedListUI.Localize();
            secondCheckedListUI.SetPane(Pane);
            secondCheckedListUI.SetExecutionContext(Context);
            secondCheckedListUI.Localize();
            thirdCheckedListUI.SetPane(Pane);
            thirdCheckedListUI.SetExecutionContext(Context);
            thirdCheckedListUI.Localize();

        }

        public object GetData(object parameters)
        {
            Hashtable criteriaHt = (Hashtable)parameters;
            StringBuilder checkedFirstListValues = GetFacilityCodes();
            StringBuilder checkedSecondListValues = secondCheckedListUI.GetData();
            StringBuilder checkedThirdValues = thirdCheckedListUI.GetData();
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedSecondListValues);
            criteriaHt.Add(ROIConstants.ReportRequestorName, TextBoxRequestorName.Text);
            criteriaHt.Add(ROIConstants.ReportRequestStatus, checkedThirdValues.Replace("Pre-Billed", "PreBilled"));

            criteriaHt.Add(ROIConstants.ReportInvoiceFromAge,RangeBar1.RangeMinimum.ToString());
            //criteriaHt.Add(ROIConstants.ReportInvoiceToAge, RangeBar1.RangeMaximum.ToString());
            if (RangeBar1.RangeMaximum <= 120)
            {
                criteriaHt.Add(ROIConstants.ReportInvoiceToAge, RangeBar1.RangeMaximum.ToString());
            }
            
            criteriaHt.Add(ROIConstants.ReportBalance, textBoxBal.Text);
            if (textBoxBal.Text == "")
                criteriaHt.Add(ROIConstants.ReportBalanceCriterion, "");
            else
                criteriaHt.Add(ROIConstants.ReportBalanceCriterion, cmbBoxBal.Text);

            if (String.IsNullOrEmpty(resultType))
                criteriaHt.Add("resultType", "\"Summary - Requestor Type\"");
            else
            {
                criteriaHt.Add("resultType", "\"" + resultType + "\"");
            }
            
            StringBuilder balanceDueLevelValue = new StringBuilder();
            string balanceDueLevel = cmbBoxBal.SelectedValue.ToString();
            balanceDueLevelValue.Append(balanceDueLevel);
            //CR#377377
            char separator = '"';
            StringBuilder content = new StringBuilder();
            content.AppendLine("BalnceCriteria,Value,Default");
            if(textBoxBal.Text==string.Empty)
                content.AppendLine(separator + balanceDueLevel + separator + "," + 0.ToString() + ","+1.ToString());
            else
                content.AppendLine(separator + balanceDueLevel + separator + "," + textBoxBal.Text.ToString() + "," + 0.ToString());
            atmostValue = content.ToString();
            StringBuilder balanceDueValue = new StringBuilder();
            if (!string.IsNullOrEmpty(textBoxBal.Text.Trim()))
            {
                string balanceDue = textBoxBal.Text.Trim();
                balanceDueValue.Append(balanceDue);
            }
            if (!string.IsNullOrEmpty(textBoxBal.Text.Trim()))
            {
                criteriaHt.Add(ROIConstants.ReportBalanceLevel, balanceDueLevelValue);
                criteriaHt.Add(ROIConstants.ReportBalanceDue, balanceDueValue);
            }
           
            resultType = string.Empty;
            return criteriaHt;
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
            bool returnVal = firstCheckedListUI.HasCheckedItems() && secondCheckedListUI.HasCheckedItems() && thirdCheckedListUI.HasCheckedItems();

            return returnVal;
        }


        private void PopulateBalanceOptions()
        {
            cmbBoxBal.Items.Insert(0, "AtLeast");
            cmbBoxBal.Items.Add("AtMost");
            cmbBoxBal.Items.Add("Equal");
            cmbBoxBal.SelectedIndex = 0;
            cmbBoxBal.DropDownStyle = ComboBoxStyle.DropDownList;
        }

        private void PopulateRequestorStatus()
        {
            thirdCheckedListUI.DisableEvents();
            IList requestStatus = EnumUtilities.ToList(typeof(RequestStatus));

            if (this.GetType() != typeof(PostedPaymentReportUI))
            {
                requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Denied));
                requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Canceled));
            }

            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.None));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.AuthReceived));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.AuthReceivedDenied));
            requestStatus.Remove(ReportHelper.GetEnum(RequestStatus.Unknown));
            thirdCheckedListUI.CriteriaCheckedListBox.DataSource = requestStatus;
            thirdCheckedListUI.CriteriaCheckedListBox.DisplayMember = "value";
            thirdCheckedListUI.CriteriaCheckedListBox.ValueMember = "key";
            thirdCheckedListUI.EnableEvents();
        }

       


        /// <summary>
        /// Gets all requestor types
        /// </summary>
        private void PopulateRequestorTypes()
        {
            secondCheckedListUI.DisableEvents();
            ReportBaseCriteriaUI reportBase = new ReportBaseCriteriaUI();
            secondCheckedListUI.CriteriaCheckedListBox.DataSource = reportBase.RetrieveRequestorTypes();
            secondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            secondCheckedListUI.CriteriaCheckedListBox.ValueMember = "id";
            secondCheckedListUI.EnableEvents();
        }


        /// <summary>
        /// Gets all facilities and display in checked list box.
        /// </summary>
        private void PopulateBillingLocation()
        {
            firstCheckedListUI.DisableEvents();
            firstCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.HPFFacilities;   //RetrieveHPFFacilities();
            firstCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            firstCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
            firstCheckedListUI.EnableEvents();
        }

        private StringBuilder GetFacilityCodes()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < firstCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)firstCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Code));
            }

            return checkedItems;

        }

        //CR#377377
        public void CreateSubtitleFile(string filePath)
        {
            try
            {
                if (File.Exists(filePath))
                {
                    File.Delete(filePath);
                }
                File.AppendAllText(filePath, atmostValue);
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

        private void TextBoxRequestorName_TextChanged(object sender, EventArgs e)
        {
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        }

        private void RangeBar1_RangeChanged(object sender, EventArgs e)
        {
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        }

        private void textBoxBal_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

    }
}
