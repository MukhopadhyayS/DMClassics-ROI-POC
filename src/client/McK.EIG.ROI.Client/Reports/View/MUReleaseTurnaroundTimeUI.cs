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

namespace McK.EIG.ROI.Client.Reports.View
{
    public partial class MUReleaseTurnaroundTimeUI : ROIBaseUI, IReportCriteria
    {

        #region Fields

        private ArrayList muDocumentNames;
        private static string subTitle;
        private static string resultType;

        #endregion

        #region Constructor

        public MUReleaseTurnaroundTimeUI()
        {
            InitializeComponent();
            muDocumentNames = new ArrayList();
        }

        #endregion

        #region Methods

        public void ClearControls()
        {
        }

        public bool ValidatePrimaryFields()
        {
            return facilityCheckedListUI.HasCheckedItems() && docTypecomboBox.SelectedIndex!=-1;
        }

        public void PrePopulate()
        {
            try
            {
                facilityCheckedListUI.DisableEvents();
                facilityCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.SortedFacilities;
                facilityCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
                facilityCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
                for (int index = facilityCheckedListUI.CriteriaCheckedListBox.Items.Count; --index >= 0; )
                {
                    facilityCheckedListUI.CriteriaCheckedListBox.SetItemChecked(index, true);
                }
                facilityCheckedListUI.EnableEvents();
                DateRangeUI.Populate();
                string[] muDocs = ROIAdminController.Instance.GetMUDocNames();
                for (int i = 1; i < muDocs.Length; i++)
                {
                    muDocumentNames.Add(muDocs[i]);
                }
                docTypecomboBox.DataSource = muDocumentNames;
                docTypecomboBox.SelectedIndex = 0;
                
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }  
        }

        public object GetData(object parameters)
        {
            Hashtable criteriaHt = (Hashtable)parameters;
            //CR#369532 NTT DATA- 12thJune2012
            //Code change to pass Facility code to the server
            StringBuilder checkedFirstListValues = GetFacilityCodes();            
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportMuDocType, docTypecomboBox.SelectedItem.ToString());
            DateRangeUI.GetData(criteriaHt);
            if(String.IsNullOrEmpty(resultType))
               criteriaHt.Add("resultType", "Details");
            else
                criteriaHt.Add("resultType", resultType);
            StringBuilder content = new StringBuilder();
            char separator = '"';
            content.AppendLine("Facilities,MUDocument Type,Date Range,View By");
            //CR375069
            if (!(criteriaHt[ROIConstants.ReportStartDate] == null && criteriaHt[ROIConstants.ReportEndDate] == null))
            content.AppendLine(separator + criteriaHt[ROIConstants.ReportFacilities].ToString() + separator + "," + docTypecomboBox.SelectedItem.ToString() + "," + criteriaHt[ROIConstants.ReportStartDate].ToString() + " - " + criteriaHt[ROIConstants.ReportEndDate].ToString() + "," + criteriaHt["resultType"]);
            subTitle = content.ToString();
            resultType = string.Empty;
            return criteriaHt;
        }

        //CR#369532 NTT DATA-12thJune2012
        //Code changes to pass Facility code to the server
        private StringBuilder GetFacilityCodes()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < facilityCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                checkedItems.Append(ROIViewUtility.ReplaceString(((FacilityDetails)facilityCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Code));
            }

            return checkedItems;
        }
             
        public void SetResultType(string result)
        {
            resultType = result;
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
            catch(Exception e)
            {
                throw new ROIException(ROIErrorCodes.SubtitleDatasourceFailed);
            }
        }

        public override void Localize()
        {
            facilityCheckedListUI.TitleKey = "reportListBox.Facility";
            DateRangeUI.SetExecutionContext(Context);
            DateRangeUI.SetPane(Pane);
            facilityCheckedListUI.SetExecutionContext(Context);
            facilityCheckedListUI.SetPane(Pane);
            facilityCheckedListUI.Localize();
            DateRangeUI.Localize();
        }
        
        private void docTypecomboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            ((ReportPane)this.Pane).ValidatePrimaryFields();
        }

        #endregion

    }
}
