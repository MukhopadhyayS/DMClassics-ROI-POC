using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;
using System.Reflection;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.Common.Audit.Model;
using McK.EIG.ROI.Client.Base.Controller;

namespace McK.EIG.ROI.Client.Admin.View.Configuration.TurnaroundTimeDays
{
    
    public partial class TurnaroundTimeDaysListUI : ROIBaseUI
    {
        
        #region Fields
               
        private EventHandler dirtyDataHandler;
        private Collection<TurnaroundTimeDay> turnaroundTimeDays;
        private Log log = LogFactory.GetLogger(typeof(TurnaroundTimeDaysListUI));
        private const string confirmCancelDialogMessage = "A128";
        public const string  confirmCancelDialogTitle = "TurnaroundTimeDaysCancelDialog.Title";
        private const string confirmCancelDialogOkButton = "TurnaroundTimeDaysCancelDialog.okButton";
        private const string confirmCancelDialogCancelButton = "TurnaroundTimeDaysCancelDialog.cancelButton";
        private const string confirmCancelDialogOkButtonToolTip = "TurnaroundTimeDaysCancelDialog.okButton";
        private const string confirmCancelDialogCancelButtonToolTip = "TurnaroundTimeDaysCancelDialog.cancelButton";
        private const string businessDay = "BUSINESS DAY";
        private const string auditComment = "Days Configuration for MU turn around time calculation has been modified. ";
        private const string auditFacility = "E_P_R_S";

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI controls.
        /// </summary>
        public TurnaroundTimeDaysListUI()
        {
            InitializeComponent();
            turnaroundTimeDays = new Collection<TurnaroundTimeDay>();
            dirtyDataHandler = new EventHandler(MarkDirty);
            IList status = EnumUtilities.ToList(typeof(TurnaroundTimeDay.Status));
            SundayComboBox.DataSource = status;
            SundayComboBox.DisplayMember = "value";
            SundayComboBox.ValueMember = "key";
            
            status = EnumUtilities.ToList(typeof(TurnaroundTimeDay.Status));
            MondayComboBox.DataSource = status;
            MondayComboBox.DisplayMember = "value";
            MondayComboBox.ValueMember = "key";
            
            status = EnumUtilities.ToList(typeof(TurnaroundTimeDay.Status));
            TuesdayComboBox.DataSource = status;
            TuesdayComboBox.DisplayMember = "value";
            TuesdayComboBox.ValueMember = "key";
          
            status = EnumUtilities.ToList(typeof(TurnaroundTimeDay.Status));
            WednesdayComboBox.DataSource = status;
            WednesdayComboBox.DisplayMember = "value";
            WednesdayComboBox.ValueMember = "key";
         
            status = EnumUtilities.ToList(typeof(TurnaroundTimeDay.Status));
            ThursdayComboBox.DataSource = status;
            ThursdayComboBox.DisplayMember = "value";
            ThursdayComboBox.ValueMember = "key";
         
           status = EnumUtilities.ToList(typeof(TurnaroundTimeDay.Status));
           FridayComboBox.DataSource = status;
           FridayComboBox.DisplayMember = "value";
           FridayComboBox.ValueMember = "key";
         
           status = EnumUtilities.ToList(typeof(TurnaroundTimeDay.Status));
           SaturdayComboBox.DataSource = status;
           SaturdayComboBox.DisplayMember = "value";
           SaturdayComboBox.ValueMember = "key";
        }

        #endregion

        #region Methods

        /// <summary>
        ///  This method is used to enable(subscribe)the TurnaroundTimeDaysMCPUI local events
        /// </summary>
        public void EnableEvents()
        {
          DisableEvents();
          SundayComboBox.SelectedIndexChanged += dirtyDataHandler;
          MondayComboBox.SelectedIndexChanged += dirtyDataHandler;
          TuesdayComboBox.SelectedIndexChanged += dirtyDataHandler;
          WednesdayComboBox.SelectedIndexChanged += dirtyDataHandler;
          ThursdayComboBox.SelectedIndexChanged += dirtyDataHandler;
          FridayComboBox.SelectedIndexChanged += dirtyDataHandler;
          SaturdayComboBox.SelectedIndexChanged += dirtyDataHandler;
        }

        /// <summary>
        ///  This method is used to disable(unsubscripe)the TurnaroundTimeDaysMCPUI local events
        /// </summary>
        public void DisableEvents()
        {
            SundayComboBox.SelectedIndexChanged  -= dirtyDataHandler;
            MondayComboBox.SelectedIndexChanged -= dirtyDataHandler;
            TuesdayComboBox.SelectedIndexChanged -= dirtyDataHandler;
            WednesdayComboBox.SelectedIndexChanged -= dirtyDataHandler;
            ThursdayComboBox.SelectedIndexChanged -= dirtyDataHandler;
            FridayComboBox.SelectedIndexChanged -= dirtyDataHandler;
            SaturdayComboBox.SelectedIndexChanged -= dirtyDataHandler;
        }

        /// <summary>
        /// Occurs when the user changes the selection in the combo boxes.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
             saveConfigButton.Enabled = true;
             cancelConfigButton.Enabled = true;
             (Pane as TurnaroundTimeDaysMCP).OnDataDirty(sender, e);
        }

        /// <summary>
        /// Set the Turnaroundtime day configuration values.
        /// </summary>
        /// <param name="data"></param>
        public void SetData(Collection<TurnaroundTimeDay> data)
        {
            Collection<TurnaroundTimeDay> daysStatus = new Collection<TurnaroundTimeDay>(data);
            DisableEvents();
            cancelConfigButton.Enabled = false;
            saveConfigButton.Enabled = false;
            TurnaroundTimeDay day;
            for (int i = 0; i < daysStatus.Count; i++)
            {
                day = new TurnaroundTimeDay();
                day.Day = daysStatus[i].Day;
                day.StatusOfDay = daysStatus[i].StatusOfDay;
                switch ((day.Day.Trim()).ToUpper())
                {
                    case "SUNDAY":
                        if ((day.StatusOfDay.Trim()).ToUpper().Equals(businessDay))
                            SundayComboBox.SelectedIndex = 0;
                        else
                            SundayComboBox.SelectedIndex = 1;
                        break;

                    case "MONDAY":
                        if ((day.StatusOfDay.Trim()).ToUpper().Equals(businessDay))
                            MondayComboBox.SelectedIndex = 0;
                        else
                            MondayComboBox.SelectedIndex = 1;
                        break;

                    case "TUESDAY":
                        if ((day.StatusOfDay.Trim()).ToUpper().Equals(businessDay))
                            TuesdayComboBox.SelectedIndex = 0;
                        else
                            TuesdayComboBox.SelectedIndex = 1;
                        break;

                    case "WEDNESDAY":
                        if ((day.StatusOfDay.Trim()).ToUpper().Equals(businessDay))
                            WednesdayComboBox.SelectedIndex = 0;
                        else
                            WednesdayComboBox.SelectedIndex = 1;
                        break;

                    case "THURSDAY":
                        if ((day.StatusOfDay.Trim()).ToUpper().Equals(businessDay))
                            ThursdayComboBox.SelectedIndex = 0;
                        else
                            ThursdayComboBox.SelectedIndex = 1;
                        break;

                    case "FRIDAY":
                        if ((day.StatusOfDay.Trim()).ToUpper().Equals(businessDay))
                            FridayComboBox.SelectedIndex = 0;
                        else
                            FridayComboBox.SelectedIndex = 1;
                        break;

                    case "SATURDAY":
                        if ((day.StatusOfDay.Trim()).ToUpper().Equals(businessDay))
                            SaturdayComboBox.SelectedIndex = 0;
                        else
                            SaturdayComboBox.SelectedIndex = 1;
                        break;
                }
            }
            turnaroundTimeDays = daysStatus;
            cancelConfigButton.Enabled = false;
            saveConfigButton.Enabled = false;
            EnableEvents();
        }

         /// <summary>
        /// Apply culture and set the tooltip.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, SundayLabel);
            SetLabel(rm, MondayLabel);
            SetLabel(rm, TuesdayLabel);
            SetLabel(rm, WednesdayLabel);
            SetLabel(rm, ThursdayLabel);
            SetLabel(rm, FridayLabel);
            SetLabel(rm, SaturdayLabel);
            SetLabel(rm, saveConfigButton);
            SetLabel(rm, cancelConfigButton);
            ResourceManager rt = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            System.Windows.Forms.ToolTip tooltip = new ToolTip();
            SetTooltip(rt, tooltip, saveConfigButton);
            SetTooltip(rt, tooltip, cancelConfigButton);
         }
                        
        /// <summary>
        /// Get the values configured for all the days
        /// </summary>
        /// <returns></returns>
        public Collection<TurnaroundTimeDay> GetData()
        {
            Collection<TurnaroundTimeDay> days = new Collection<TurnaroundTimeDay>();
            TurnaroundTimeDay day;
            int i = 0;
            day = new TurnaroundTimeDay();
            day.Day = GetEnumDescription((TurnaroundTimeDay.Days)i);
            if (SundayComboBox.SelectedIndex == 0)
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)0);
            else
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)1);
            days.Add(day);
            i++;

            day = new TurnaroundTimeDay();
            day.Day = GetEnumDescription((TurnaroundTimeDay.Days)i);
            if (MondayComboBox.SelectedIndex == 0)
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)0);
            else
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)1);
            days.Add(day);
            i++;

            day = new TurnaroundTimeDay();
            day.Day = GetEnumDescription((TurnaroundTimeDay.Days)i);
            if (TuesdayComboBox.SelectedIndex == 0)
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)0);
            else
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)1);
            days.Add(day);
            i++;

            day = new TurnaroundTimeDay();
            day.Day = GetEnumDescription((TurnaroundTimeDay.Days)i);
            if (WednesdayComboBox.SelectedIndex == 0)
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)0);
            else
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)1);
            days.Add(day);
            i++;

            day = new TurnaroundTimeDay();
            day.Day = GetEnumDescription((TurnaroundTimeDay.Days)i);
            if (ThursdayComboBox.SelectedIndex == 0)
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)0);
            else
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)1);
            days.Add(day);
            i++;

            day = new TurnaroundTimeDay();
            day.Day = GetEnumDescription((TurnaroundTimeDay.Days)i);
            if (FridayComboBox.SelectedIndex == 0)
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)0);
            else
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)1);
            days.Add(day);
            i++;

            day = new TurnaroundTimeDay();
            day.Day = GetEnumDescription((TurnaroundTimeDay.Days)i);
            if (SaturdayComboBox.SelectedIndex == 0)
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)0);
            else
                day.StatusOfDay = GetEnumDescription((TurnaroundTimeDay.Status)1);
            days.Add(day);

            return days;
        }

        /// <summary>
        /// Update the configuration changes
        /// </summary>
        /// <returns></returns>
        public bool Save()
        {           
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            try
            {
                Collection<TurnaroundTimeDay> days = GetData();                  
                bool update = ROIAdminController.Instance.UpdateDayStatus(days);
                (Pane as TurnaroundTimeDaysMCP).IsDirty = false;
                if (update)
                {
                    AuditEvent auditEvent = new AuditEvent();
                    auditEvent.ActionCode = ROIConstants.ConfigurationChangeActionCode;
                    auditEvent.UserId = UserData.Instance.UserInstanceId;
                    auditEvent.EventStart = System.DateTime.Now;
                    auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                    auditEvent.EventId = 1;
                    auditEvent.Facility = auditFacility;
                    auditEvent.Mrn = null;
                    auditEvent.Encounter = null;
                    auditEvent.Comment = auditComment;
                    Application.DoEvents();
                    ROIController.Instance.CreateAuditEntry(auditEvent);
                    turnaroundTimeDays = days;
                    saveConfigButton.Enabled = false;
                    cancelConfigButton.Enabled = false;
                }
                else
                {
                    CancelStatusChanges();
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                ROIViewUtility.Handle(Context, cause);
                CancelStatusChanges();
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
            
            return true;
        }
        
        /// <summary>
        /// Occurs when cancel button is clicked
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cancelButton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            string messageText = rm.GetString(confirmCancelDialogMessage);
            rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString(confirmCancelDialogTitle);
            string okButtonText = rm.GetString(confirmCancelDialogOkButton);
            string cancelButtonText = rm.GetString(confirmCancelDialogCancelButton);
            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(confirmCancelDialogOkButtonToolTip);
            string cancelButtonToolTip = rm.GetString(confirmCancelDialogCancelButtonToolTip);
            if (ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, cancelButtonText,okButtonToolTip,cancelButtonToolTip,ROIDialogIcon.Alert))
            {
                CancelStatusChanges();
            }
            ROIViewUtility.MarkBusy(false);
            log.ExitFunction();
        }

        /// <summary>
        /// Cancel the changes made in the comboboxes.
        /// </summary>
        public void CancelStatusChanges()
        {
            (Pane as TurnaroundTimeDaysMCP).IsDirty = false;
            cancelConfigButton.Enabled = false;
            saveConfigButton.Enabled = false;
            SetData(turnaroundTimeDays);
        }

        public static string GetEnumDescription(Enum value) 
        { 
            FieldInfo fi = value.GetType().GetField(value.ToString());
            DescriptionAttribute[] attributes = (DescriptionAttribute[])fi.GetCustomAttributes(typeof(DescriptionAttribute), false);
            if (attributes != null && attributes.Length > 0)     
                return attributes[0].Description.ToUpper();
            else     
                return value.ToString().ToUpper();
        }

        /// <summary>
        /// Occurs when save button is clicked
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            Save();
        }

        #endregion


    }
}

    

