#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
#endregion
using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;
using System.Net;
using System.Security.Cryptography;
using System.Text;

using CommonAuditEvent = McK.EIG.Common.Audit.Model;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Patient.Model;

using McK.EIG.ROI.Client.Web_References.AuthenticationWS;
using McK.EIG.ROI.Client.Web_References.ConfigurationWS;
using McK.EIG.ROI.Client.Web_References.InUseWS;
using McK.EIG.ROI.Client.Web_References.LogoutWS;
using McK.EIG.ROI.Client.Web_References.PasswordWS;
using admin = McK.EIG.ROI.Client.Web_References.ROIAdminWS;
using McK.EIG.ROI.Client.Web_References.SecurityLogoffWS;
using McK.EIG.ROI.Client.Web_References.SigninWS;
using McK.EIG.ROI.Client.Web_References.OutputServiceWS;
using McK.EIG.ROI.Client.Web_References.ROIRequestCoreWS;
using McK.EIG.ROI.Client.Web_References.AlertServiceWS;

using McK.EIG.Common.Utility.WebServices.Encryption;
using McK.EIG.Common.Utility.WebServices;
using System.Reflection;

namespace McK.EIG.ROI.Client.Base.Controller
{
    /// <summary>
    /// ROI Application Level controller.
    /// </summary>
    public class ROIController : IDisposable
    {

        #region Fields

        private static volatile ROIController roiController;
        private static object syncRoot = new Object();

        private static int timeOut;

        private static UserData userData;
        
        private InUseServiceWse inUseService;
        private ConfigurationServiceWse configurationService;
        private SigninServiceWse signinService;
        private PasswordServiceWse secretWordService;
        private LogoutServiceWse logoutService;
        private SecurityLogoffServiceWse roiLogoutService;        
        private admin.ROIAdminServiceWse roiAdminService;
        private AuthenticationServiceWse auhtenticationService;
        private RequestCoreServiceWse requestCoreService;
        private const int ValidUser = 0;
        private const int INIT_VECTOR_LENGTH = 16;
        private static List<GenderDetails> GenderListDetails;
//        private static List<GenderDetails> newGenderList;
//        public List<GenderDetails> GenderCodeInformation;

        private static string loginId;

        static AESUtility encryptor;
       
        static ROIController()
        {
            encryptor = new AESUtility();
            //Checkmarx changes for Use of Hardcoded CryptographicKey
            ROIConstants objEncryptionKey = new ROIConstants();
            byte[] vectorSrc = Encoding.UTF8.GetBytes(ROIConstants.Encryption_IV);
            byte[] vectorDest = new byte[INIT_VECTOR_LENGTH];
            System.Array.Copy(vectorSrc, vectorDest, vectorSrc.Length);

            encryptor.SetInitializationVector(vectorDest);
            //encryptor.PassPhrase = ROIConstants.Encryption_Key;
            //US18631 - Veracode fix.
            encryptor.setKey(ROIConstants.Encryption_Key);  
            
        }

        #endregion

        #region Constructor
        /// <summary>
        /// Initializes service proxies
        /// </summary>
        public ROIController()
        {  
            inUseService = new InUseServiceWse();           
            requestCoreService = new RequestCoreServiceWse();
        }

        #endregion

        #region Methods
        
        protected static object[] PrepareHPFWParams(object[] requestParams)
        {
            object[] newParams = new object[4 + requestParams.Length];
            UserData userData = UserData.Instance;

            string userName = userData.UserId;
            string userSecretWord = userData.SecretWord;

            if (!userData.ConfigurationData.PasswordEnabled)
            {
                userSecretWord = "";
            }
            if (userData.IsLdapEnabled)
            {
                userName = userData.Domain + "\\" + userData.DomainUserName + "~" + userData.UserId;
                userSecretWord = userData.DomainSecretWord;
            }

            newParams[0] = userName;
            newParams[1] = userSecretWord;
            Uri timeServerUrl = new Uri(McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("PORTAL", ConfigurationManager.AppSettings["ContentServiceUrl"]));
            string serverDateTime = HttpTimerRetriever.GetGmtString(HttpTimerRetriever.GetTimeserverUrl(timeServerUrl));
            newParams[2] = serverDateTime;
            if (userData.ConfigurationData.PasswordEnabled || userData.IsLdapEnabled)
            {
                newParams[3] = Encrypt(new string[] { userName, userSecretWord, serverDateTime, userData.ConfigurationData.PrivateKeyToken });
            }
            else
            {
                newParams[3] = Encrypt(new string[] { userName, serverDateTime, userData.ConfigurationData.PrivateKeyToken });
            }
            Array.Copy(requestParams, 0, newParams, 4, requestParams.Length);
            return newParams;
        }

        /// <summary>
        /// Method returns the user model if authentication is succesfully done.
        /// </summary>
        /// <returns></returns>
        public UserData LogOn()
        {
            
            object[] requestParams = new Object[] { "ca:ROI_CLIENT tenantid:mpf" + " " + UserData.Instance.JsessionID, UserData.Instance.RSAToken };
            BaseROIValidator roiValidator = new BaseROIValidator();

            if (!roiValidator.ValidateLogOnFields(UserData.Instance))
            {
                throw roiValidator.ClientException;
            }          
            if (signinService == null)
            {               
                signinService = new SigninServiceWse();                
            }


            if (!int.TryParse(ConfigurationManager.AppSettings["TimeOut"], out timeOut) ||
               timeOut < 100000)
            {
                timeOut = 100000;
            }
            try
            {
                object response = HPFWHelper.Invoke(signinService, "signin", requestParams);
                if (response == null)
                {
                    return null;
                }



                //UserData.Instance.Reset();



                MapModel((user)response);
                if (UserData.Instance.SignInstate == 0)
                {
                    GetConfiguration();
                }
            }

            catch (Exception es)
            {

                throw es;
            }
             return UserData.Instance;
        }

        /// <summary>
        /// Method returns the user model if authentication is succesfully done using LDAP.
        /// </summary>
        /// <returns></returns>
        public UserData LogOnLdap()
        {
            loginId = UserData.Instance.UserId;
            object[] requestParams = new object[] { UserData.Instance.UserId, UserData.Instance.DomainSecretWord, UserData.Instance.Domain };
            BaseROIValidator roiValidator = new BaseROIValidator();

            if (!roiValidator.ValidateLogOnFields(UserData.Instance))
            {
                throw roiValidator.ClientException;
            }
            if (signinService == null)
            {   
                signinService = new SigninServiceWse();
            }

            try
            {
                object response = HPFWHelper.Invoke(signinService, "signinLdap", requestParams);

                if (userData.IsLdapEnabled)
                {
                    userData.DomainUserName = UserData.Instance.UserId;
                    userData.DomainSecretWord = UserData.Instance.DomainSecretWord;
                }

                if (response == null)
                {
                    return null;
                }

                MapModel((user)response);
                if (UserData.Instance.SignInstate == 0)
                {
                    GetConfiguration();
                }
            }
            catch (Exception es)
            {
                throw es;
            }
            return UserData.Instance;
        }

        /// <summary>
        /// Method returns the user model if authentication is succesfully done using LDAP.
        /// </summary>
        /// <returns></returns>
        public UserData LogOnLdapWithHpfUserName(string hpfUserName)
        {
            //loginId = UserData.Instance.UserId;
            UserData.Instance.HpfMappedUserId = hpfUserName;
            object[] requestParams = new object[] { UserData.Instance.Domain + "\\" + loginId + "~" + hpfUserName, UserData.Instance.DomainSecretWord, hpfUserName };
            BaseROIValidator roiValidator = new BaseROIValidator();

            if (!roiValidator.ValidateLogOnFields(UserData.Instance))
            {
                throw roiValidator.ClientException;
            }
            if (signinService == null)
            {   
                signinService = new SigninServiceWse();
            }
            try
            {

                object response = HPFWHelper.Invoke(signinService, "signinWithLdap", requestParams);

                MapModel((user)response);
                return UserData.Instance;
            }
            catch (Exception ex)
            {
                throw ex;              
                
            }
            
        }


        public UserData UserSelfMapping(string hpfUserName)
        {
            UserData.Instance.HpfMappedUserId = hpfUserName;
            object[] requestParams = new object[] { UserData.Instance.Domain + "\\" + loginId + "~" + hpfUserName, hpfUserName, UserData.Instance.HpfSecretWord };


            if (OCSecurityWrapper.IsDLLInitialized())
            {
                OCSecurityWrapper.GetSecureToken();
               // serviceProxy.SetjSessionid(UserData.Instance.JsessionID);
                //serviceProxy.SetSecureToken(UserData.Instance.RSAToken);
            }



            if (signinService == null)
            {
                signinService = new SigninServiceWse();
            }

           object response = HPFWHelper.Invoke(signinService, "mapUser", requestParams);

            user user = ((user)response);
            UserData.Instance.SignInstate = user.validateCode;

            if ((UserData.Instance.SignInstate != -1) || (UserData.Instance.SignInstate != 0))
            {
                UserData.Instance.UserId = UserData.Instance.HpfUserId;
            }
            else
            {
                UserData.Instance.UserId = null;
            }
         
            return UserData.Instance;
        }
        public UserData UserSelfMapping()
        {
            object[] requestParams = new object[] { UserData.Instance.Domain +"\\" + loginId , UserData.Instance.HpfUserId, UserData.Instance.HpfSecretWord };

            if (signinService == null)
            {
                signinService = new SigninServiceWse();
            }
            object response = HPFWHelper.Invoke(signinService, "mapUser", requestParams);

            user user = ((user)response);
            UserData.Instance.SignInstate = user.validateCode;

            if ((user.loginId != null) || (UserData.Instance.SignInstate != -1) || (UserData.Instance.SignInstate != 0))
            {
                UserData.Instance.UserId = UserData.Instance.HpfUserId;
            }
            else
            {
                UserData.Instance.UserId = null;
            }
         
            return UserData.Instance;
        }        

        /// <summary>
        /// HPFLogoff.         
        /// </summary>
        public void LogOff()
        {
            try
            {
           logoutService = new LogoutServiceWse();
           roiLogoutService = new SecurityLogoffServiceWse();
           HPFWHelper.Invoke(logoutService, "logout", new Object[0]);
           ROIHelper.Invoke(roiLogoutService, "logoff", new Object[0]);           
           UserData.Instance.Reset();
            }
            catch (ROIException cause)
            {
                throw cause;    
            }
        }

        /// <summary>
        /// Method used to change password in HPF.
        /// </summary>
        /// <returns></returns>
        public string ChangePassword(UserData userData)
        {
            GetConfiguration();

            object[] requestParams = new object[] { userData.UserId,
                                                    userData.SecretWord,
                                                    userData.NewSecretWord,
                                                    "",
                                                    "" };

            if (secretWordService == null)
            {
                secretWordService = new PasswordServiceWse();
            }
            object response = HPFWHelper.Invoke(secretWordService, "changePassword", requestParams);
            return Convert.ToString(response, System.Threading.Thread.CurrentThread.CurrentUICulture);
        }
       
        /// <summary>
        /// Method returns the Configuration details from the HPFW.
        /// </summary>
        /// <param name="UserId"></param>
        /// <param name="Password"></param>
        /// <returns></returns>
        public void GetConfiguration()
        {
            string userName = UserData.Instance.UserId;
            string userSecretWord = UserData.Instance.SecretWord; 

            // US7773 - GetConfiguration - added security to webservice
            if (userData.IsLdapEnabled)
            {
                userName = UserData.Instance.Domain + "\\" + loginId + "~" + UserData.Instance.HpfUserId;
                userSecretWord = userData.DomainSecretWord;
            }
            
            
            object[] requestParams = new object[] { userName, userSecretWord }; //UserData.Instance.Password};            
            if (configurationService == null)
            {
                configurationService = new ConfigurationServiceWse();
            }
            object response = HPFWHelper.Invoke(configurationService, "getConfiguration", requestParams);
            MapModel((configuration)response);
        }

        /// <summary>
        /// Methods returns the ROI Freeform facilities.
        /// </summary>
        public void RetrieveFreeformFacilities()
        {
            object[] requestParams = new object[] {  };
            if (roiAdminService == null)
            {
                roiAdminService = new admin.ROIAdminServiceWse();
            }
            object response = ROIHelper.Invoke(roiAdminService, "retrieveROIAppData", requestParams);
            MapModel((admin.ROIAppData)response);        
        }

       
       
               
        public static void SendAlert(LogEvent logEvent)
        {
            AlertServiceWse alertService = new AlertServiceWse();
            object[] requestParams = new object[] {logEvent.ToXml()};
            ROIHelper.Invoke(alertService, "alert", requestParams);
        }

        public void CreateAuditEntry(CommonAuditEvent.AuditEvent auditEvent)
        {
            AuditEvent server = MapModel(auditEvent);
            AuditAndEventList serverAuditAndEventList = new AuditAndEventList();
            List<AuditEvent> serverAuditEventList = new List<AuditEvent>();
            serverAuditEventList.Add(server);
            serverAuditAndEventList.auditEvent = serverAuditEventList.ToArray();
            object[] requestParams = new object[] { serverAuditAndEventList };
            ROIHelper.Invoke(requestCoreService, "addAuditAndEvent", requestParams);
        }
        
        /// <summary>
        /// CR#365599 - Create multiple audit entries.
        /// </summary>
        /// <param name="auditEvent"></param>
        public void CreateAuditEntryList(List<CommonAuditEvent.AuditEvent> auditEvents)
        {
            List<AuditEvent> serverAuditEvents = MapModel(auditEvents);
            AuditAndEventList serverAuditAndEventList = new AuditAndEventList();
            serverAuditAndEventList.auditEvent = serverAuditEvents.ToArray();
            object[] requestParams = new object[] { serverAuditAndEventList };
            ROIHelper.Invoke(requestCoreService, "addAuditAndEvent", requestParams);
        }

        /// <summary>
        /// Method is used to mark the status of the object as (in-use) 
        /// for the user who have logged on.
        /// </summary>
        /// <param name="objectType">Type of the object</param>
        /// <param name="objectId">Identifier of the object</param>
        public void CreateInUseRecord(string objectType, long objectId)
        {
            string applicationID = ApplicationId + ROIConstants.Delimiter + HostAddress;
            int expiresMinutes = Convert.ToInt32(ConfigurationManager.AppSettings["IntervalToExpire"], System.Threading.Thread.CurrentThread.CurrentUICulture);

            object[] requestParams = new object[] { objectType, Convert.ToString(objectId, System.Threading.Thread.CurrentThread.CurrentUICulture), 
                                                    applicationID, UserData.Instance.UserId, expiresMinutes };

            ROIHelper.Invoke(inUseService, "createInUseRecord", requestParams);
        }

        /// <summary>
        /// Method is used to find whether the given object is used by another user or not
        /// </summary>
        /// <param name="objectType">Type of the object</param>
        /// <param name="objectId">Identifier of the object</param>
        /// <returns></returns>
        public InUseRecordDetails RetrieveInUseRecord(string objectType, long objectId)
        {
            object[] requestParams = new object[] { objectType, Convert.ToString(objectId, System.Threading.Thread.CurrentThread.CurrentUICulture) };
            object response = ROIHelper.Invoke(inUseService, "retrieveInUseRecord", requestParams);
            if (response == null)
            {
                return null;
            }
            return MapModel((InUseRecord)response);
        }

        /// <summary>
        /// Method used to retrieve all the records which is used by someone based on object type given.
        /// </summary>
        /// <param name="objectType">Type of the object</param>
        /// <param name="objectIds">the collection will have all the object ids to check</param>
        /// <returns></returns>
        public SortedList<string, InUseRecordDetails> RetrieveInUseRecords(string objectType, Collection<string> objectIds)
        {
            string applicationID = ApplicationId + ROIConstants.Delimiter + HostAddress;
            string ids = ROIViewUtility.ConvertToCsv(objectIds);
            object[] requestParams = new object[] { objectType, ids, applicationID, UserData.Instance.UserId };
            object response = ROIHelper.Invoke(inUseService, "retrieveInUseRecordsByIDs", requestParams);          

            return MapModel((InUseRecord[])response);
        }

        /// <summary>
        /// Method used to mark the record's status as unlocked.
        /// </summary>
        /// <param name="objectType"></param>
        /// <param name="objectId"></param>
        public void ReleaseInUseRecord(string objectType, long objectId)
        {
            string applicationID = ApplicationId + ROIConstants.Delimiter + HostAddress;
            object[] requestParams = new object[] { objectType, Convert.ToString(objectId, System.Threading.Thread.CurrentThread.CurrentUICulture), 
                                                    applicationID, UserData.Instance.UserId };

            ROIHelper.Invoke(inUseService, "releaseInUseRecord", requestParams);
        }

        /// <summary>
        /// Retrieves users
        /// </summary>
        /// <returns></returns>
        public IList RetrieveUsers(int[] securityIds)
        {
            return MapModel((userList)RetrieveHPFUsers(securityIds));
        } 

        /// <summary>
        /// Retreives HPF users fullname.
        /// </summary>
        /// <param name="securityIds"></param>
        /// <returns></returns>
        public Hashtable RetrieveUsersFullName(int[] securityIds)
        {
            userList serverUsers = (userList)RetrieveHPFUsers(securityIds);
            Hashtable users = new Hashtable();
            
            foreach (user serverUser in serverUsers.users)
            {
                if (serverUser.fullName.Trim() != string.Empty && serverUser.loginId.Trim() != string.Empty)
                {
                   users.Add(serverUser.instanceId, serverUser.fullName);
                }
            }
            return users;
        }

        /// <summary>
        /// Retrieve HPF Users.
        /// </summary>
        /// <param name="securityIds"></param>
        /// <returns></returns>
        public object RetrieveHPFUsers(int[] securityIds)
        {
            codeList codes = new codeList();
            codes.codes = securityIds;

            object[] requestParams = new object[] { codes };

            signinService = new SigninServiceWse();            
            object response = HPFWHelper.Invoke(signinService, "getUsers", PrepareHPFWParams(requestParams));

            return response;
        }  

        public UserData RetrieveLogonInfo()
        {
            if (!int.TryParse(ConfigurationManager.AppSettings["TimeOut"], out timeOut) ||
                timeOut < 100000)
            {
                timeOut = 100000;
            }

            UserData.Instance.Reset();
            auhtenticationService = new AuthenticationServiceWse();
            object response = HPFWHelper.Invoke(auhtenticationService, "preLoginInfo", new Object[0]);
            return MapModel((preLoginInfoType)response);            
        }

        public static string Truncate(string value)
        {
            if (value.Length > ROIConstants.LovMaxLength)
            {
                return value.Substring(0, ROIConstants.LovMaxLength);
            }
            return value;
        }
        //ROI16.0 zipcode
        public List<CountryCodeDetails> RetrieveCountryList()
        {
            object[] requestParams = new object[] { };
            object response = ROIHelper.Invoke(roiAdminService, "retrieveAllCountries", requestParams);
            List<CountryCodeDetails> CountryDetails = new List<CountryCodeDetails>();
            if (response == null) return CountryDetails;
            CountryDetails = MapModel((McK.EIG.ROI.Client.Web_References.ROIAdminWS.Country[])response);
            
            return CountryDetails;
        }

        //SOGI OC-111171
        //Retrive All Gender       
        public List<GenderDetails> RetrieveGenderList()
        {
            object[] requestParams = new object[] { };
            object response = ROIHelper.Invoke(roiAdminService, "retrieveAllGenders", requestParams);
            List<GenderDetails> GenderDetails = new List<GenderDetails>();
            //List<GenderDetails> GenderDetails = new List<GenderDetails>();
            //GenderDetails.Add("Selected Gender");
            //GenderDetails.Add("Female");
            if (response == null) return GenderDetails;

            GenderDetails = MapModel((McK.EIG.ROI.Client.Web_References.ROIAdminWS.Gender[])response);
            return GenderDetails;
        }

        public void UpdateCountryCode(CountryCodeDetails defaultCountryDetails)
        {
            McK.EIG.ROI.Client.Web_References.ROIAdminWS.Country updateCountrycode = new McK.EIG.ROI.Client.Web_References.ROIAdminWS.Country();
            updateCountrycode.countryCode = defaultCountryDetails.CountryCode;
            updateCountrycode.countryName = defaultCountryDetails.CountryName;
            updateCountrycode.defaultCountry = defaultCountryDetails.DefaultCountry;
            updateCountrycode.countrySeq = defaultCountryDetails.CountrySeq;
            object[] updateParams = new object[] { updateCountrycode };
            object response = ROIHelper.Invoke(roiAdminService, "updateCountryCode", updateParams);
        }

        #endregion

        #region Model Mapping

        public static List<GenderDetails> MapModel(McK.EIG.ROI.Client.Web_References.ROIAdminWS.Gender[] server)
        {
            List<GenderDetails> client = new List<GenderDetails>();

            GenderDetails genderDetails;
            foreach (McK.EIG.ROI.Client.Web_References.ROIAdminWS.Gender gender in server)
            {
                genderDetails = new GenderDetails();
                genderDetails.GenderCode = gender.code;
                genderDetails.GenderDesc = gender.description;
                client.Add(genderDetails);
            }
        
            return client;
        }

        public static List<CountryCodeDetails> MapModel(McK.EIG.ROI.Client.Web_References.ROIAdminWS.Country[] server)
        {
            List<CountryCodeDetails> client = new List<CountryCodeDetails>();
            CountryCodeDetails countryCode;
            foreach (McK.EIG.ROI.Client.Web_References.ROIAdminWS.Country count in server)
            {
                countryCode = new CountryCodeDetails();
                countryCode.CountryCode = count.countryCode;
                countryCode.CountryName = count.countryName;
                countryCode.DefaultCountry = count.defaultCountry;
                countryCode.CountrySeq = count.countrySeq;
                client.Add(countryCode);
            }
            return client;
        }

        private static UserData MapModel(preLoginInfoType result)
        {
            userData = UserData.Instance;
            userData.IsLdapEnabled = result.isLdapEnabled;
            userData.IsSelfMappingEnabled = result.isSelfMappingEnabled;

            if (result.domains != null)
            {
                foreach (String domain in result.domains)
                {
                    userData.DomainList.Add(domain);
                }
            }

            return userData;
        }

        /// <summary>
        /// Returns list of available users.
        /// </summary>
        /// <param name="serverUsers"></param>
        /// <returns></returns>
        private static IList MapModel(userList serverUsers)
        {
            ArrayList clientUsers = new ArrayList();

            foreach(user serverUser in serverUsers.users)
            {
                if (serverUser.fullName.Trim() != string.Empty && serverUser.loginId.Trim() != string.Empty)
                {
                    clientUsers.Add(new KeyValuePair<int, string>(serverUser.instanceId, serverUser.fullName.Trim() + "  " + serverUser.loginId));
                }
            }

            return clientUsers;
        }

        private static void MapModel(admin.ROIAppData roiApplicationData)
        {
            if (roiApplicationData.freeFormFacilities != null)
            {
                FacilityDetails facility;
                foreach (string freeformFacility in roiApplicationData.freeFormFacilities)
                {
                    if (!string.IsNullOrEmpty(freeformFacility))
                    {
                        facility = new FacilityDetails(freeformFacility, freeformFacility, FacilityType.NonHpf);
                        if (!UserData.Instance.Facilities.Contains(facility))
                        {
                            UserData.Instance.Facilities.Add(facility);
                        }
                    }
                }
            }
            
            //adding invoice due dates attributes(0,30,60,90,120) to userdata
            if (roiApplicationData.invoiceDueDays != null)
            {
                ArrayList list = new ArrayList();
                foreach (int dueDays in roiApplicationData.invoiceDueDays)
                {
                    list.Add(new KeyValuePair<int, string>(dueDays,dueDays.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));
                }
                UserData.Instance.InvoiceDueDays = list;
            }

            UserData.Instance.IsSSNMasked = roiApplicationData.hasSSNMasking;
        }      

        /// <summary>
        /// Converts server model into client
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        private static UserData MapModel(user user)
        {
            //UserData userData = UserData.Instance;
            ConfigurationData configurationData = ConfigurationData.Instance;
            userData.ConfigurationData = configurationData;
            userData.InvalidLogOnCount  = 0;
            userData.UserInstanceId     = user.instanceId;
            userData.SignInstate        = user.validateCode;
            userData.IdleTime           = user.idle;
            userData.IdleSpecified      = user.idleSpecified;
            userData.FullName           = user.fullName;            
           // userData.UserId             = user.loginId;

            if (userData.IsLdapEnabled)
            {
                userData.UserId = user.loginId.Trim();
                userData.SecretWord = user.password;
                userData.HpfUserId = user.loginId.Trim();
            }

            if (user.facilities != null)
            {
                FacilityDetails fac;
                foreach (facility facility in user.facilities)
                {
                    fac = new FacilityDetails(facility.name, facility.code, FacilityType.Hpf);
                    if (!userData.Facilities.Contains(fac))
                    {
                        userData.Facilities.Add(fac);
                    }
                }
            }

            UserSecurities rights;

            if (user.securities != null)
            {
                foreach (security security in user.securities)
                {
                    rights = new UserSecurities();

                    foreach (int securityId in security.codes)
                    {   
                        rights.Add(Convert.ToString(securityId, System.Threading.Thread.CurrentThread.CurrentUICulture), true);
                    }
                    if (!userData.Security.ContainsKey(security.facility))
                    {
                        userData.Security.Add(security.facility, rights);
                    }
                }
            }

            if (user.recordViews != null)
            {
                MapModel(user.recordViews);
            }
            return userData;
        }

        private static void MapModel(recordView[] recordViews)
        {
            RecordViewDetails recordView;
            for (int index = 0; index <= recordViews.Length - 1; index++)
            {
                recordView = MapModel(recordViews[index]);
                UserData.Instance.RecordViews.Add(recordView);
            }
        }
        private static bool hasDocType(Collection<string> docTypes, string docType)
        {
            bool isFound = false;
            for (int index = 0; index <= docTypes.Count - 1; index++)
            {
                if (string.Compare(docTypes[index].Trim(), docType.Trim(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                {
                    isFound = true;
                    break;
                }
            }
            return isFound;
        }
        private static RecordViewDetails MapModel(recordView recordView)
        {
                RecordViewDetails clientRecordViews = new RecordViewDetails();

                clientRecordViews.Name = recordView.name.Trim();

                if (recordView.documentSpecs != null)
                {
                    foreach (documentSpec serverDocSpec in recordView.documentSpecs)
                    {
                        if (!hasDocType(clientRecordViews.DocTypes, serverDocSpec.name))
                        {
                            clientRecordViews.DocTypes.Add(serverDocSpec.name);
                        }
                    }
            }
            return clientRecordViews;
        }

        /// <summary>
        /// Converts server configuration model into client model.
        /// </summary>
        /// <param name="configuration"></param>
        /// <returns></returns>
        private static void MapModel(configuration configuration)
        {
            ConfigurationData configurationData = UserData.Instance.ConfigurationData;

            foreach (item item in configuration.items)
            {
                switch (item.name)
                {
                    case ConfigurationData.PrivateKey:
                        {
                            configurationData.PrivateKeyToken = item.value;
                            break;
                        }
                    case ConfigurationData.SecureRequiredKey:
                        {
                            configurationData.PasswordEnabled = Convert.ToBoolean(item.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                            break;
                        }
                    case ConfigurationData.TokenRequiredKey:
                        {
                            configurationData.TokenEnabled = Convert.ToBoolean(item.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                            break;
                        }
                    case ConfigurationData.EpnEnabledKey:
                        {
                            configurationData.EpnEnabled = Convert.ToBoolean(item.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                            break;
                        }
                    case ConfigurationData.EpnPrefixKey:
                        {
                            configurationData.EpnPrefix = item.value;
                            break;
                        }
                    case ConfigurationData.LoadLevelerKey:
                        {
                            configurationData.LoadLeveler = item.value;
                            break;
                        }
                    case ConfigurationData.LoadLevelerUrlKey:
                        {
                            configurationData.LoadLevelerUrl = new Uri(item.value);
                            break;
                        }
                    case ConfigurationData.ContentWebServiceUrlKey:
                        {
                            configurationData.ContentWebServiceUrl = new Uri(item.value);
                            break;
                        }
                    case ConfigurationData.PageCountUrlKey:
                        {
                            configurationData.PageCountUrl = new Uri(item.value);
                            break;
                        }
                }
            }
        }

        private static InUseRecordDetails MapModel(InUseRecord server)
        {
            InUseRecordDetails client = new InUseRecordDetails();
            client.ApplicationId = server.applicationID;
            client.UserId = server.userID;
            client.ObjectId = server.objectID;
            client.ObjectType = server.objectType;

            return client;
        }

        private static SortedList<string, InUseRecordDetails> MapModel(InUseRecord[] records)
        {
            if (records.Length == 0) return null;

            SortedList<string, InUseRecordDetails> inUseRecords = new SortedList<string, InUseRecordDetails>(records.Length);
            foreach (InUseRecord serverRecord in records)
            {
                inUseRecords.Add(serverRecord.objectID, MapModel(serverRecord));
            }

            return inUseRecords;
        }

        public static string EncryptPassword(string password)
        {
            return Encrypt(new string[1] { password });
        }

        private static string Encrypt(string[] dataToEncrypt)
        {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < dataToEncrypt.Length; i++)
            {
                if (dataToEncrypt[i] != null)
                {
                    strBuilder.Append(dataToEncrypt[i]);
                }
            }

           /* MD5 md5 = MD5.Create();

            byte[] hashBytes = md5.ComputeHash(Encoding.Default.GetBytes(strBuilder.ToString()));
            StringBuilder encryptedKey = new StringBuilder();
            foreach (byte hashByte in hashBytes)
            {
                encryptedKey.Append(hashByte.ToString("x2", System.Threading.Thread.CurrentThread.CurrentUICulture));
            }*/
            //return strBuilder.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture);
            //Fix for DM-9083(Can't Login to ROI with lowercase passwords)
            return strBuilder.ToString();
        }

        /// <summary>
        /// Encrypts plaintext using AES 128bit key and a Chain Block Cipher and returns a base64 encoded string
        /// </summary>
        /// <param name="plainText">Plain text to encrypt</param>
        /// <param name="key">Secret key</param>
        /// <returns>Base64 encoded string</returns>
        public static string EncryptAES(String plainText)
        {
            if (!string.IsNullOrEmpty(plainText))
            {
                return Convert.ToBase64String(encryptor.Encrypt(Encoding.UTF8.GetBytes(plainText)));
            }
            return string.Empty;

        }


        public static string DecryptAES(string encryptedText)
        {
            if (!string.IsNullOrEmpty(encryptedText))
            {
                try
                {
                    return encryptor.DecryptMessageToString(Convert.FromBase64String(encryptedText));
                }
                catch (Exception cause)
                {
                    return encryptedText;
                }
            }

            return string.Empty;
        }


        /// <summary>
        /// Converts the client audit model to server.
        /// </summary>
        /// <param name="client"></param>
        /// <returns></returns>
        private static List<AuditEvent> MapModel(List<CommonAuditEvent.AuditEvent> clientAuditEvents)
        {
            List<AuditEvent> serverAuditEvents = new List<AuditEvent>();
            foreach (CommonAuditEvent.AuditEvent clientAuditEvent in clientAuditEvents)
            {
                serverAuditEvents.Add(MapModel(clientAuditEvent));
            }
            return serverAuditEvents;
        }

        /// <summary>
        /// Converts the client audit model to server.
        /// </summary>
        /// <param name="client"></param>
        /// <returns></returns>
        private static AuditEvent MapModel(CommonAuditEvent.AuditEvent client)
        {
            AuditEvent server = new AuditEvent();

            server.userIdSpecified = true;
            server.userId = client.UserId;
            server.eventStart = client.EventStart;
            server.eventStatus = client.EventStatus;
            server.eventId = client.EventId;
            server.facility = client.Facility;
            server.actionCode = client.ActionCode;
            server.comment = client.Comment;
            server.encounter = client.Encounter;
            server.mrn = client.Mrn;
            server.facility = client.Facility;

            return server;
        }


        /// <summary>
        /// Encrypts plaintext using RSA key and returns a base64 encoded string
        /// </summary>
        /// <param name="plainText">Plain text to encrypt</param>
         /// <returns>Base64 encoded string</returns>
        public static string EncryptOcSecurity(String plainText)
        {
            String encryptedRtnStr = null;
            IntPtr secureStrPtr= IntPtr.Zero;

            try
            {
                //Create a secureString for plainText string
                System.Security.SecureString secureStr = new System.Security.SecureString();
                foreach (char secretWord in plainText)
                    secureStr.AppendChar(secretWord);

                System.Security.SecureString encryptedString = OCSecurityWrapper.encryptData(secureStr);
                secureStrPtr = System.Runtime.InteropServices.Marshal.SecureStringToBSTR(encryptedString);
                encryptedRtnStr = System.Runtime.InteropServices.Marshal.PtrToStringAuto(secureStrPtr);

            }
            finally
            {
                System.Runtime.InteropServices.Marshal.FreeBSTR(secureStrPtr);
            }

            return encryptedRtnStr;
        }

        //SOGI OC-111171
        /// <summary>
        /// Fetches the relate Gender Code if Gender Description is passed as input. 
        /// This is to save the Gender Code in DB field
        /// Fetches the related Gender Description if Gender Code is passed as input.
        /// This is to display the Gender Description to the User.
        /// </summary>
        /// <param name="currentGenderValue">Gender Code or Description to fecth its related Description or Code</param>
        /// <param name="genderListDetails">List of Gender details</param>
        /// <returns>Gender Code or Description based on the input</returns>
        public static string FetchGenderCodeOrDesc (string currentGenderValue, List<GenderDetails> genderListDetails)
        {
            int i = 0;
            string genderCodeOrDesc = "";
            int genderLength = currentGenderValue.Length;
                                    
            if (genderLength > 1)
            {
                foreach (GenderDetails currentGenderDetails in genderListDetails)
                {
                    if (currentGenderValue == genderListDetails[i].GenderDesc)
                    {
                        genderCodeOrDesc = genderListDetails[i].GenderCode;
                        break;
                    }
                    i++;
                }
            }
            else
            {
                foreach (GenderDetails currentGenderDetails in genderListDetails)
                {
                    if (currentGenderValue == genderListDetails[i].GenderCode)
                    {
                        genderCodeOrDesc = genderListDetails[i].GenderDesc;
                        break;
                    }
                    i++;
                }
            }
            if (String.IsNullOrEmpty (genderCodeOrDesc))
            {
                genderCodeOrDesc = null;
            }
            return genderCodeOrDesc;
        }

        #endregion

        #region IDisposable Members

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            if (disposing)
            {   
                inUseService.Dispose();
                signinService.Dispose();
                configurationService.Dispose();
                secretWordService.Dispose();
                logoutService.Dispose();
                roiLogoutService.Dispose();
                roiAdminService.Dispose();
                auhtenticationService.Dispose();
                requestCoreService.Dispose();
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Get the ROIController Instance
        /// </summary>
        public static ROIController Instance
        {
            get
            {
                lock (syncRoot)
                {
                    if (roiController == null)
                        roiController = new ROIController();
                }
                return roiController;
            }
        }

        /// <summary>
        /// Returns the instance of ROIHelper
        /// </summary>
        protected static IWsHelper ROIHelper
        {
            get { return HelperFactory.GetHelper(HelperFactory.RoiServiceType); }
        }

        /// <summary>
        /// Returns the instance of HPFWHelper
        /// </summary>
        protected static IWsHelper HPFWHelper
        {
            get { return HelperFactory.GetHelper(HelperFactory.HpfwServiceType); }
        }

        public static string ApplicationId
        {
            get { return ConfigurationManager.AppSettings[ROIConstants.ApplicationId]; }
        }

        public static string HostAddress
        {
            get { return Dns.GetHostAddresses(Dns.GetHostName())[0].ToString(); }
        }

        public static int Timeout
        {
            get { return timeOut; }
            set { timeOut = value; }
        }

        #endregion
        
    }
}
