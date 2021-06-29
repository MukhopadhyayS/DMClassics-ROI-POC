using System;
using System.Globalization;
using System.ComponentModel;
using System.Drawing;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;
using System.Windows.Forms;
using System.Resources;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Request.View.PatientInfo;
using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Patient.View.PatientRecords;
using System.Web.Services.Protocols;

namespace McK.EIG.ROI.Client.Patient.View.PatientRecords.Attachments
{
    public partial class ExternalSourceAttachment : ROIBaseUI,IAttachmentDetailUI
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ExternalSourceAttachment));
        private bool editMode;
        private bool isReleased;
        private const string EncounterName = "Encounter";
        private const string PatientType = "Type";
        private const string AdmitDateTime = "Date|Time";
        private const string  DischargeDateTime= "DischargeDate";
        private const string commentField="Query";
        private const string AddDocText = "Adding documents to patient";
        private const string SubmittedByKey = "submitted-by";
        private const string mrnKey = "MRN";
        private const string encounterKey = "Encounter";
        private const string facilityKey = "Facility";
        private const string requestIdKey = "Reqid";
        private const string reqTypeKey= "ReqType";
        private const string reqReceiptDtKey = "ReceiptDate";
        private const string gatheringDocText = "  Gathering Documents";
        private const string addattachmentFailure = "Attachment retrieval from external source failed for encounter {0}.\nError: ";
        private const string noFileMsg="No file retrieved for the selected encounter(s)";
        internal static EventHandler ProgressHandler;
        private const string datetime24hr = "MM/dd/yyyy HH:mm:ss";
        private int attachmentUploadProgress;
        private int errorMsgCount;
        PatientDetails patientInfo;
        internal static string requestId;
        internal static string reqType;
        internal static string reqReceiptDt;
        private static EventHandler _createEditHandler;
        private static EventHandler _deleteHandler;
        private static CancelEventHandler _cancelHandler;
        private static AttachmentDetails attachment;
        private int encounterCount;
        private int fileCount;
        
        #endregion

        #region Constructor

        public ExternalSourceAttachment(bool editMode, bool isReleased)
        {
            this.editMode = editMode;
            this.isReleased = isReleased;
            InitializeComponent();
            EncountersGrid.AddTextBoxColumn(EncounterName, "Encounter", "SelfPayEncounterID", 150);
            EncountersGrid.AddTextBoxColumn(PatientType, "Type", "PatientType", 40);
            DataGridViewTextBoxColumn dgvAdmitDateColumn = EncountersGrid.AddTextBoxColumn(AdmitDateTime, "Admit Date", "AdmitDate", 160);
            dgvAdmitDateColumn.DefaultCellStyle.Format = ROIConstants.DateTimeAMPMDesignateFormat;
            DataGridViewTextBoxColumn dgvDischargeDateColumn = EncountersGrid.AddTextBoxColumn(DischargeDateTime,
                                                                                             "Discharge Date",

                                                                                "DischargeDate",
                                                                                ColumnWidth);
            dgvDischargeDateColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDischargeDateColumn.DefaultCellStyle.Format = ROIConstants.DateTimeAMPMDesignateFormat;
            ProgressHandler = new EventHandler(Process_NotifyProgress);
            encounterCount = 0;
            InitProgress();
        }

        public ExternalSourceAttachment()
        {
        }

        #endregion

        #region methods

        /// <summary>
        /// To set the event for create attachment. 
        /// </summary>
        /// <param name="createEditHandler"></param>
        public static void SetAttachmentEvents(EventHandler createEditHandler)
        {
            _createEditHandler = createEditHandler;
        }

        /// <summary>
        /// To fill the last column width.
        /// </summary>
        public int ColumnWidth
        {
            get
            {
                int sum = 0;
                foreach (DataGridViewColumn column in EncountersGrid.Columns)
                {
                    sum += column.Width;
                }
                sum = EncountersGrid.Width - sum;
                sum = EncountersGrid.Columns[EncountersGrid.Columns.Count - 1].Width + sum - 3;
                return sum;
            }
        }

        /// <summary>
        /// Method to initilize the progress bar.
        /// </summary>
        private void InitProgress()
        {
            percentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
            percentageLabel.Visible = false;
        }

        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            this.ShowProgress((FileTransferEventArgs)e);
        }

        /// <summary>
        /// Method to update the progress bar.
        /// </summary>
        /// <param name="e"></param>
        private void ShowProgress(FileTransferEventArgs e)
        {            
            long completion = 0;
            switch (e.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    fileTransferProgress.Value = Convert.ToInt32(completion);
                    break;
                case FileTransferEventArgs.Status.InProgress:
                    completion = (e.TransferredSize * 100) / e.FileSize;
                    fileTransferProgress.Value += Convert.ToInt32(completion) - fileTransferProgress.Value;
                    break;
                case FileTransferEventArgs.Status.Finish:
                    completion = (e.TransferredSize * 100) / e.FileSize;
                    fileTransferProgress.Value += Convert.ToInt32(completion) - fileTransferProgress.Value;
                    break;
            }
            percentageLabel.Text = completion.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture) + "%";
            percentageLabel.Visible = true;
            this.Refresh();
        }

        /// <summary>
        /// Method to display the progress bar.
        /// </summary>
        /// <param name="progressIndicator"></param>
        /// <param name="finish"></param>
        private void ShowProgress(int progressIndicator, bool finish)
        {
            fileTransferProgress.Visible = true;
            FileTransferEventArgs tmpEvent = new FileTransferEventArgs((int)FileTransferEventArgs.Status.InProgress);
            tmpEvent.FileSize = 100;
            tmpEvent.TransferredSize = progressIndicator;
            ShowProgress(tmpEvent);
            if (finish)
            {
                fileTransferProgress.Visible = false;
                percentageLabel.Visible = false;
            }
         }

        /// <summary>
        /// Populate the encounters grid, if patient information is set.
        /// </summary>
        public void PrePopulate()
        {
            if (patientInfo != null)
            {
                PatientDetails patient = PatientController.Instance.RetrieveEncounters(patientInfo);
                ComparableCollection<EncounterDetails> listEncounters = new ComparableCollection<EncounterDetails>(patient.Encounters);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                string selfPayEncounter = rm.GetString("Self Pay Encounter");
                listEncounters.SetSortedInfo(TypeDescriptor.GetProperties(typeof(EncounterDetails))["AdmitDate"], ListSortDirection.Descending);
                List<EncounterDetails> encounterDetailList = new List<EncounterDetails>(listEncounters);
                List<EncounterDetails> listEncounterDetails = encounterDetailList.FindAll(delegate(EncounterDetails item)
                {
                    if (item.IsSelfPay) { item.SelfPayEncounterID = item.EncounterId + selfPayEncounter; } return item is EncounterDetails;
                });
                ComparableCollection<EncounterDetails> encounterDetails = new ComparableCollection<EncounterDetails>(listEncounterDetails);
                EncountersGrid.SetItems((IFunctionCollection)encounterDetails);
                
                addSelectedbutton.Enabled = listEncounters.Count > 0 ? true : false;
                try
                {
                    clinicalSystemValueLabel.Text = getMappedSource(patientInfo.FacilityCode);
                }
                catch (ROIException cause)
                {
                    addSelectedbutton.Enabled = false;
                    Collection<ExceptionData> errorMessages = cause.GetErrorMessage(Context.CultureManager.GetCulture(CultureType.Message.ToString()));
                    StringBuilder sb = new StringBuilder();
                    foreach (ExceptionData exceptionData in errorMessages)
                    {
                        sb.Append(exceptionData.ErrorMessage);
                    }
                    clinicalSystemValueLabel.Text = sb.ToString();
                }
            }
        }

        /// <summary>
        /// Method to set the patient information.
        /// </summary>
        /// <param name="patient"></param>
        public void SetPatientInfo(PatientDetails patient)
        {
            patientInfo = patient;
            
        }

        /// <summary>
        /// Apply Localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, clinicalSystemLabel);
            SetLabel(rm, addSelectedbutton);
            SetLabel(rm, statusLabel);
        }
       
        public void ShowControls()
        {
        }
        public void ClearControls() { }

        public void ValidatePrimaryFields() { }
        
        public bool EnableSaveButton()
        {
            return false;
        }

        public void EnableEvents(EventHandler eventHandler)
        {
            addSelectedbutton.Click += eventHandler;
        }

        public void DisableEvents(EventHandler eventHandler)
        {
            addSelectedbutton.Click -= eventHandler;
        }

        /// <summary>
        /// Sets the data(patient details).
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            ROIViewUtility.MarkBusy(true);
            patientInfo = new PatientDetails();
            patientInfo = data as PatientDetails;
            PrePopulate();
            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Method to get the external source name that is mapped to a facility.
        /// </summary>
        /// <param name="facility"></param>
        /// <returns></returns>
        public string getMappedSource(string facility)
        {
            string source = PatientController.Instance.getExternalSource(facility);
            return source;
        }

        /// <summary>
        /// Sets the attachment data.
        /// </summary>
        /// <param name="data"></param>
        public static void SetAttachmentDetails(object data)
        {
            attachment = data as AttachmentDetails;
        }

        /// <summary>
        /// Gets attachment related data.
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            AttachmentDetails tmpAttachment = data == null ? new AttachmentDetails() : data as AttachmentDetails;
            tmpAttachment.AddAttachmentTypeDetail(SubmittedByKey, getMappedSource(patientInfo.FacilityCode));
            tmpAttachment.Comment = commentField;
            tmpAttachment.AttachmentDate = DateTime.Now;
            return tmpAttachment;
        }

        /// <summary>
        /// This method is used to gather the required information for adding attachment and to call the method for retrieving the attachment.
        /// </summary>
        /// <param name="progressHandler"></param>
        /// <returns></returns>
        public List<object> UploadAttachment(EventHandler progressHandler)
        {
            log.EnterFunction();
             List<object> fileDetails = new List<object>();
            ExternalSourceAttachmentDetails externalSourceAttachmentDetails;
            EncounterDetails encounterDetails;
            string encouunter;
            string facility;
            string mrn;
            string source; 
            Collection<RetrieveCCDDetails> retrieveccdList; 
            RetrieveCCDDetails retrieveccdDetails;
            List<object> addedFiles=new List<object>();
            gatheringDoclabel.Text = gatheringDocText;
            gatheringDoclabel.Visible = true;
            this.Refresh();
            addSelectedbutton.Enabled = false;
            attacmentResultLabel.Visible = false;
            ShowProgress(25, false);
            int progressIndicator = 30;
            errorTextBox.Text = "";
            errorTextBox.ScrollBars = ScrollBars.Horizontal;
            statusLabel.Visible = false;
            errorTextBox.Visible = false;
            errorMsgCount = 0;
            string errorText=string.Empty;
            
            try
            {
                for (int i = 0; i < EncountersGrid.SelectedRows.Count; i++)
                {
                    retrieveccdList = new Collection<RetrieveCCDDetails>();
                    encounterDetails = new EncounterDetails();
                    encounterDetails = (EncounterDetails)EncountersGrid.SelectedRows[i].DataBoundItem;
                    encouunter = encounterDetails.EncounterId;
                    facility = patientInfo.FacilityCode;
                    mrn = patientInfo.MRN;
                    source = clinicalSystemValueLabel.Text.Trim();
                    retrieveccdDetails = new RetrieveCCDDetails(mrnKey, mrn);
                    retrieveccdList.Add(retrieveccdDetails);
                    retrieveccdDetails = new RetrieveCCDDetails(encounterKey, encouunter);
                    retrieveccdList.Add(retrieveccdDetails);
                    retrieveccdDetails = new RetrieveCCDDetails(facilityKey, facility);
                    retrieveccdList.Add(retrieveccdDetails);
                    retrieveccdDetails = new RetrieveCCDDetails(requestIdKey, requestId);
                    retrieveccdList.Add(retrieveccdDetails);
                    retrieveccdDetails = new RetrieveCCDDetails(reqReceiptDtKey, reqReceiptDt);
                    retrieveccdList.Add(retrieveccdDetails);
                    retrieveccdDetails = new RetrieveCCDDetails(reqTypeKey, reqType);
                    retrieveccdList.Add(retrieveccdDetails);
                    List<object> filesList = new List<object>();
                    try
                    {
                        filesList = PatientController.Instance.UploadAttachmentFromSource(retrieveccdList);

                    }
                    catch (SoapException ex)
                    {
                        errorText = ex.Detail.InnerText.ToString();
                        if (!(string.IsNullOrEmpty(errorText)))
                        {
                            int index = errorText.IndexOf(ROIErrorCodes.RetrieveCCDFailure);
                            index = index + ROIErrorCodes.RetrieveCCDFailure.ToString().Length;
                            errorText = errorText.Substring(index);
                        }
                        DisplayStatus(errorText, encouunter);
                     }
                    catch (ROIException cause)
                    {
                        Collection<ExceptionData> errorMessages = cause.GetErrorMessage(Context.CultureManager.GetCulture(CultureType.Message.ToString()));
                        StringBuilder sb = new StringBuilder();
                        foreach (ExceptionData exceptionData in errorMessages)
                        {
                            sb.Append(exceptionData.ErrorMessage);
                        }
                        errorText = sb.ToString();
                        DisplayStatus(errorText, encouunter);
                    }
                    catch (Exception e)
                    {
                       DisplayStatus(e.Message, encouunter);
                    }
                    
                    progressIndicator += 45 / EncountersGrid.SelectedRows.Count;
                    externalSourceAttachmentDetails = new ExternalSourceAttachmentDetails();
                    if (filesList.Count > 0)
                    {
                        ShowProgress(progressIndicator, false);
                        externalSourceAttachmentDetails.Encounter = encounterDetails.EncounterId;
                        externalSourceAttachmentDetails.AttachmentFiles = filesList;
                        fileDetails.Add(externalSourceAttachmentDetails);
                    }
                }
                ShowProgress(100, false);
                ShowProgress(100, false);
                ShowProgress(100, false);
                ShowProgress(100, true);
                gatheringDoclabel.Visible = false;
                return fileDetails;
            }
            catch (Exception e)
            {
                log.FunctionFailure(e);
                throw new ROIException(ROIErrorCodes.ErrorAddingFile);
            }
            finally
            {
                log.ExitFunction();
            }
           
        }

        /// <summary>
        /// Occurs when user clicks Add Selected button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AddSelectedbutton_Click(object sender, EventArgs e)
        {
            log.EnterFunction();
            ROIViewUtility.MarkBusy(true);
            encounterCount = 0;
            fileCount = 0;
            List<object> fileDetailList = UploadAttachment(ProgressHandler);
            List<object> tmpFiles;
            string message;
            if (fileDetailList.Count > 0)
            {
                gatheringDoclabel.Text = AddDocText;
                gatheringDoclabel.Visible = true;
            }
            this.Refresh();
            AttachmentDetails tmpAttachment = (AttachmentDetails)GetData(null);
            AttachmentDetails newAttachment = new AttachmentDetails();
            ExternalSourceAttachmentDetails externalSourceAttacment;
            int progressIndicator = 20;
            try
            {
                foreach (object tmpFileDetail in fileDetailList)
                {

                    externalSourceAttacment = (ExternalSourceAttachmentDetails)tmpFileDetail;
                    tmpAttachment.AttachmentType = (AttachmentHelper.GetEnumDescription(AttachmentHelper.AttachmentLocation.ExternalSourceAttachment)).ToString();
                    tmpAttachment.FacilityCode = patientInfo.FacilityCode;
                    tmpAttachment.Encounter = externalSourceAttacment.Encounter;
                    tmpFiles = (List<object>)(externalSourceAttacment.AttachmentFiles);
                    encounterCount++;

                    int index = 0;
                    string dateString = tmpAttachment.AttachmentDate.ToString();
                    string[] date = dateString.Split(new char[] { ' ' });
                    attachmentUploadProgress = 95;
                    foreach (object tmpFile in tmpFiles)
                    {
                        progressIndicator += (45 / tmpFiles.Count) / fileDetailList.Count;
                        ShowProgress(progressIndicator, false);
                        tmpAttachment.Id = 0;
                        tmpAttachment.FileDetails = (AttachmentFileDetails)tmpFile;
                        tmpAttachment.FileDetails.FileName = tmpAttachment.FileDetails.FileType.ToUpper() + " " + patientInfo.Name + " " + date[0].Replace('/','-');
                        tmpAttachment.Subtitle = tmpAttachment.FileDetails.FileType.ToUpper() + " " + patientInfo.Name + " " + date[0].Replace('/','-');
                        tmpAttachment.PageCount = tmpAttachment.FileDetails.PageCount;
                        if (tmpAttachment.FileDetails.ServiceDate != null)
                            tmpAttachment.DateOfService = tmpAttachment.FileDetails.ServiceDate.Value.Date;
                        else
                        {
                            for (int k = 0; k < patientInfo.Encounters.Count; k++)
                            {
                                if ((patientInfo.Encounters[k].Name).Equals(tmpAttachment.Encounter))
                                {
                                    if (patientInfo.Encounters[k].AdmitDate != null)
                                        tmpAttachment.DateOfService = patientInfo.Encounters[k].AdmitDate.Value.Date;
                                    else
                                        tmpAttachment.DateOfService = System.DateTime.Now.Date;
                                }
                            }
                            if ((tmpAttachment.DateOfService> DateTime.Now.Date))
                            {
                                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                                DisplayStatus(rm.GetString(ROIErrorCodes.InvalidDateOfService), tmpAttachment.Encounter);                                
                            }
                            else
                            {
                                _createEditHandler(tmpAttachment, e);
                                fileCount++;
                                message = tmpAttachment.FileDetails.FileType + " - " + tmpAttachment.FileDetails.DateReceived.Value.ToString(datetime24hr) + " - " + tmpAttachment.FileDetails.Extension + " is added";
                                DisplayStatus(message, tmpAttachment.Encounter);
                                index++;
                            }
                        }
                        if (!string.IsNullOrEmpty(tmpAttachment.FacilityCode))
                        {
                            FacilityDetails facility = FacilityDetails.GetFacility(tmpAttachment.FacilityCode, tmpAttachment.FacilityType);
                            if (facility == null)
                            {
                                UserData.Instance.Facilities.Add(new FacilityDetails(tmpAttachment.FacilityCode, tmpAttachment.FacilityCode, FacilityType.NonHpf));
                            }
                        }
                    }
                }
            }
            catch (ROIException cause)
            {
                Collection<ExceptionData> errorMessages = cause.GetErrorMessage(Context.CultureManager.GetCulture(CultureType.Message.ToString()));
                StringBuilder sb = new StringBuilder();
                foreach (ExceptionData exceptionData in errorMessages)
                {
                    sb.Append(exceptionData.ErrorMessage);
                }
                string errorMsg = sb.ToString();
                log.FunctionFailure(errorMsg);
                DisplayStatus(errorMsg, tmpAttachment.Encounter);
            }
            finally
            {
                ShowProgress(100, false);
                ShowProgress(100, false);
                ShowProgress(100, false);
                ShowProgress(100, true);
                // attacmentResultLabel.Visible = true;
                if (fileDetailList.Count > 0)
                    attacmentResultLabel.Text = "<" + fileCount + "> " + "files successfully added for <" + encounterCount + ">" + " encounter(s)";
                else
                    attacmentResultLabel.Text = noFileMsg;
                addSelectedbutton.Enabled = true;
                gatheringDoclabel.Visible = false;
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        private void DisplayStatus(string message,string encounter)
        {
            errorMsgCount++;
            if (string.IsNullOrEmpty(message))
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                message = rm.GetString(ROIErrorCodes.CcdRetrievalFailed);
            }
            message = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,"Encounter - "+ encounter + " : " + message);
            if (!(errorMsgCount == 1))
                message = String.Format("{0}{1}", Environment.NewLine, message);
            errorTextBox.AppendText(message);
            if (errorMsgCount >= 4)
                errorTextBox.ScrollBars = ScrollBars.Both;
            statusLabel.Visible = true;
            errorTextBox.Visible = true;
            log.Debug(message);
        }
        
        #endregion

    }
}
