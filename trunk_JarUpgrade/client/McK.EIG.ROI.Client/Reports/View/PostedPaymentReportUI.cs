using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;
using System.IO;



namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// PostedPaymentSummaryUI - Follows the same view as 'Outstanding Invoice balances' report type.
    /// </summary>
    public partial class PostedPaymentReportUI : ReportBaseCriteriaUI,IReportCriteria
    {

        #region Fields

        private static string subTitle;
        private static string resultType;

        #endregion

        #region Constructor

        public PostedPaymentReportUI()
        {
            InitializeComponent();
            ThirdCheckedListUI.Visible = true;
            base.SearchCriteriaPanel.AutoScroll = true;

          }
        public void PrePopulate()
        {
            PopulateRequestorTypes();
            PopulateUsers();
           
            PopulateFacilities();
            DateRangeUI.Populate();
            //Increase the height of user listbox
            //CR#379408
            FirstCheckedListUI.Size = new System.Drawing.Size(285, 115);
            SecondCheckedListUI.Size = new System.Drawing.Size(285, 135);
        }

        /// <summary>
        /// Gets the selected data.
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public object GetData(object parameters)
        {
            string[] statusValues = StatusCombo.Text.Split(new char[] { ' ' });
            Hashtable criteriaHt = (Hashtable)parameters;
            StringBuilder checkedFirstListValues = FirstCheckedListUI.GetData();
            StringBuilder checkedSecondListValues = GetSelectedUsers();
            StringBuilder checkedThirdValues = ThirdCheckedListUI.GetData();
            criteriaHt.Add(ROIConstants.ReportFacilities, checkedFirstListValues);
            criteriaHt.Add(ROIConstants.ReportRequestorType, checkedThirdValues);
            criteriaHt.Add(ROIConstants.ActorIds, checkedSecondListValues);
//            criteriaHt.Add(ROIConstants.ReportUserNames, GetSelectedUsersFullName());
            DateRangeUI.GetData(criteriaHt);
            if (String.IsNullOrEmpty(resultType))
                criteriaHt.Add("resultType", "\"Posted By\"");
            else
                criteriaHt.Add("resultType", "\"" + resultType + "\"");
            StringBuilder content = new StringBuilder();
            char separator = '"';
            content.AppendLine("Posted By,Date Range,View By");
            if(!(criteriaHt[ROIConstants.ReportStartDate]==null && criteriaHt[ROIConstants.ReportEndDate]==null))
                content.AppendLine(separator + checkedSecondListValues.ToString() + separator + "," + criteriaHt[ROIConstants.ReportStartDate].ToString() + " - " + criteriaHt[ROIConstants.ReportEndDate].ToString() + "," + criteriaHt["resultType"]);
            subTitle = content.ToString();
            resultType = string.Empty;
           

            return criteriaHt;
        }

        public void SetResultType(string result)
        {
            resultType = result;
        }

        private StringBuilder GetFacilityCodes(string p)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <returns></returns>
        internal StringBuilder GetSelectedUsers()
        {
            StringBuilder checkedItems = new StringBuilder();
            for (int index = 0; index < SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }
                checkedItems.Append(((KeyValuePair<int,string>)SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Key);
            }

            return checkedItems;
        }

        /// <summary>
        /// Gets data.
        /// </summary>
        /// <returns></returns>
        internal StringBuilder GetSelectedUsersFullName()
        {
            StringBuilder checkedItems = new StringBuilder();
            Hashtable users =  ROIController.Instance.RetrieveUsersFullName(new int[] { Convert.ToInt32(ROISecurityRights.ROIAccessApplication, System.Threading.Thread.CurrentThread.CurrentUICulture), 
                                                                                        Convert.ToInt32(ROISecurityRights.ROIAdministration, System.Threading.Thread.CurrentThread.CurrentUICulture)});

            for (int index = 0; index < SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems.Count; index++)
            {
                if (index != 0)
                {
                    checkedItems.Append(",");
                }

                int userInstanceID = (int)((KeyValuePair<int, string>)SecondCheckedListUI.CriteriaCheckedListBox.CheckedItems[index]).Key;               

                string userFullname = users[userInstanceID].ToString();

                if(!string.IsNullOrEmpty(userFullname))
                {                    
                    checkedItems.Append(userFullname);
                }
            }

            return checkedItems;
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
       
        public override void Localize()
        {
            base.Localize();
            SecondCheckedListUI.TitleKey  = "reportListBox.user";
            ThirdCheckedListUI.TitleKey = "reportListBox.RequestorType";
            FirstCheckedListUI.TitleKey = "reportListBox.BillingLocation";
            FirstCheckedListUI.SetExecutionContext(Context);
            SecondCheckedListUI.SetExecutionContext(Context);
            ThirdCheckedListUI.SetExecutionContext(Context);
            FirstCheckedListUI.Localize();
            SecondCheckedListUI.Localize();
            ThirdCheckedListUI.Localize();

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            //SetLabel(rm, StatusLabel);
        }

       private void PopulateRequestorTypes()
        {
            ThirdCheckedListUI.DisableEvents();
            ThirdCheckedListUI.CriteriaCheckedListBox.DataSource = RetrieveRequestorTypes();
            ThirdCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
            ThirdCheckedListUI.CriteriaCheckedListBox.ValueMember = "id";
            ThirdCheckedListUI.EnableEvents();
        }

        /// <summary>
        /// Populates User names and IDs.
        /// </summary>
        /// 
       private void PopulateUsers()
       {
           try
           {
               SecondCheckedListUI.DisableEvents();
               //Sort the user listbox
               SecondCheckedListUI.CriteriaCheckedListBox.Sorted = true;
               SecondCheckedListUI.CriteriaCheckedListBox.DataSource = ROIController.Instance.RetrieveUsers(new int[] { Convert.ToInt32(ROISecurityRights.ROIAccessApplication, System.Threading.Thread.CurrentThread.CurrentUICulture), 
                                                                                                                    Convert.ToInt32(ROISecurityRights.ROIAdministration, System.Threading.Thread.CurrentThread.CurrentUICulture) 
                                                                                                                  }
                                                                                                          );

               SecondCheckedListUI.CriteriaCheckedListBox.ValueMember = "key";
               SecondCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Value";
               SecondCheckedListUI.EnableEvents();
           }
           catch (ROIException cause) //Handled exception if HPFW server is not connected.
           {
               ROIViewUtility.Handle(Context, cause);
           }
       }

       private void PopulateFacilities()
       {
           FirstCheckedListUI.DisableEvents();
           FirstCheckedListUI.CriteriaCheckedListBox.DataSource = UserData.Instance.HPFFacilities;   //RetrieveHPFFacilities();
           FirstCheckedListUI.CriteriaCheckedListBox.DisplayMember = "Name";
           FirstCheckedListUI.CriteriaCheckedListBox.ValueMember = "code";
           FirstCheckedListUI.EnableEvents();
       }

     
       

        #endregion


        
    }
}
