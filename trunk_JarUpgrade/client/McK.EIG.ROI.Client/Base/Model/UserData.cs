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
using System.Text;

using McK.EIG.Common.Utility.WebServices;

using McK.EIG.ROI.Client.Admin.Model;

using Microsoft.Web.Services3.Security.Tokens;

using McK.EIG.ROI.Client.Base.Controller;
using System.Reflection;


using McK.EIG.ROI.Client.Admin.Controller;





namespace McK.EIG.ROI.Client.Base.Model
{
    /// <summary>
    /// Class to hold the user credentials.
    /// NOTE: COPIED FROM HECM
    /// </summary>
    public class UserData : ISecurityToken
    {
        #region Fields

        /// <summary>
        /// Singleton instance.
        /// </summary>
        private static UserData userData = new UserData();

        /// <summary>
        /// Stores token in use (string(userid:pwd), token).
        /// </summary>
        private object[] tokenInUse;

        /// <summary>
        /// User id.
        /// </summary>
        private string userId = string.Empty;

        private string ldapUserId = string.Empty;


        private string callingApp = "ca:ROI_CLIENT";

        /// <summary>
        /// User domain password.
        /// </summary>
        private StringBuilder domainSecure = new StringBuilder();

        private StringBuilder secure = new StringBuilder();

        /// <summary>
        /// New Password.
        /// </summary>
        private StringBuilder newSecure = new StringBuilder();

        /// <summary>
        /// User is locked(True) /unlocked(False).  
        /// </summary>
        private bool lockUnLock;

        /// <summary>
        /// User is lockedPermenantly(True) /unlocked(False).
        /// </summary>
        private bool lockedOut;

        /// <summary>
        /// Number of days within which password will expire.
        /// </summary>
        private long numDaysSecureExpires;

        /// <summary>
        /// True if password has expired.
        /// </summary>
        private bool secureExpired;

        /// <summary>
        /// True if password is going to expire.
        /// </summary>
        private bool secureExpiring;

        /// <summary>
        /// Number of attempts the user has tried logon.
        /// </summary>
        private long noOfAttemptsToLogOn;

        /// <summary>
        /// Number of attempts the user has tried reset his password.
        /// </summary>
        private long noOfAttemptsToReset;

        /// <summary>
        /// True if first time user.
        /// </summary>
        private bool isFirstTime;

        /// <summary>
        /// Additional information about the user account.
        /// </summary>
        private string additionalInfo = string.Empty;

        /// <summary>
        ///  the ticket recieved during authentication
        /// </summary>
        private string ticket;

        /// <summary>
        /// the staff login sequence number - used for auditing, etc.
        /// </summary>
        private long staffLogOnSeq;

        private int inValidLogOnCount;      

        /// <summary>
        /// Holds list of facilities
        /// </summary>
        private List<FacilityDetails> facilities;


        /// <summary>
        /// Holds list of facilities
        /// </summary>
        private List<FacilityDetails> hPFFacilities;

        /// <summary>
        /// Holds default facility
        /// </summary>
        private FacilityDetails defaultFacility;

        /// <summary>
        /// Holds list of freeform facilities
        /// </summary>
        private Dictionary<string, string> freeformFacilities;

        /// <summary>
        /// Holds list of recordviews
        /// </summary>
        private Collection<RecordViewDetails> recordViews;

        /// <summary>
        /// Holds the user id 
        /// </summary>
        private int userInstanceId;

        /// <summary>
        /// Holds list of security facilities
        /// </summary>
        private Dictionary<string, UserSecurities> securityFacilities;

        private bool isSSNMasked;

        private bool isunbillableRequest;

        private bool ischecked;

        /// <summary>
        /// Holds User login state.
        /// </summary>
        private int signInState;
      
        /// <summary>
        /// Holds configuration data object
        /// </summary>
        private ConfigurationData configurationData;

        /// <summary>
        /// Holds application idle time.
        /// </summary>
        private int idleTime;

        /// <summary>
        /// Holds application idle specification state.
        /// </summary>
        private bool idleSpecified;

        /// <summary>
        /// Holds user full name;
        /// </summary>
        private string fullName;

        /// <summary>
        /// Holds Ldap Enbled State
        /// </summary>
        private bool isLdapEnabled;

        /// <summary>
        /// Holds Self Mapping Enabled State
        /// </summary>
        private bool isSelfMappingEnabled;

        private Collection<string> domainsList;

        private string domain;

        private string hpfUserId;
        private string hpfMappedUserId;

        private string hpfSecure;

        private SortedList<string,string> userLists;

        private string domainUserName;

        //holds invoice due days options from global params
        private ArrayList invoiceduedays;

        private string secureToken;
        public string RSAToken
        {
            set { secureToken = value; }
            get { return secureToken; }
        }

        private string jsessionID;
        public string JsessionID
        {
            set { jsessionID = value; }
            get { return jsessionID; }
        }

        private string getURLFormat;
        public string GetURLFormat
        {
            set { getURLFormat = value; }
            get { return getURLFormat; }
        }
        #endregion

        #region Constructors

        /// <summary>
        /// Private Constructor to achieve singleton behaviour.
        /// For future implementation.
        /// </summary>
        private UserData()
        {
        }

        #endregion

        #region Properties

        #region IsChecked
        /// <summary>
        /// IsChecked.
        /// </summary>
        public bool IsChecked
        {
            set { ischecked = value; }
            get 
            {
                ConfigureUnbillableRequestDetails requestDetails = ROIAdminController.Instance.RetrieveConfigureUnbillableRequest();
                if (requestDetails != null)
                {
                    ischecked = requestDetails.IsUnbillableRequest;
                }
                return ischecked; 
            }

        }

        #endregion


        #region InvalidLogonCount

        /// <summary>
        /// InvalidLogOnCount.
        /// </summary>
        public int InvalidLogOnCount
        {
            get { return inValidLogOnCount; }
            set { inValidLogOnCount = value; }
        }

        #endregion

        #region IdleTime

        /// <summary>
        /// Idle Time
        /// </summary>
        public int IdleTime
        {
            get { return idleTime; }
            set { idleTime = value; }
        }

        #endregion

        #region IdleSpecified

        /// <summary>
        /// Idle Specified
        /// </summary>
        public bool IdleSpecified
        {
            get { return idleSpecified; }
            set { idleSpecified = value; }
        }

        #endregion

        #region UserId

        /// <summary>
        /// User id.
        /// </summary>
        public string UserId
        {
            get { return userId; }
            set { userId = value; }
        }

        public string LDAPUserId
        {
            get { return ldapUserId; }
            set { ldapUserId = value; }
        }

        public string CallingApp
        {
            get { return callingApp; }
            set { callingApp = value; }
        }

        #endregion

        public string SecureToken
        {
            set { secureToken = value; }
            get { return secureToken; }

        }
        public string DomainUserName
        {
            get { return domainUserName; }
            set { domainUserName = value; }
        }

        #region FullName

        public string FullName
        {
            get { return fullName; }
            set { fullName = value; }
        }

        #endregion

        #region Password

        /// <summary>
        /// Password
        /// </summary>
        public string SecretWord
        {
            get { return secure.ToString(); }
            set { secure = new StringBuilder(value); }
        }

        public string DomainSecretWord
        {
            get { return domainSecure.ToString(); }
            set { domainSecure = new StringBuilder(value); }
        }

        #endregion

        #region NewPassword

        /// <summary>
        /// New Password.
        /// </summary>
        public string NewSecretWord
        {
            get { return newSecure.ToString(); }
            set { newSecure = new StringBuilder(value); }
        }

        #endregion

        #region Locked

        /// <summary>
        /// Locked flag.
        /// </summary>
        public bool Locked
        {
            get { return lockUnLock; }
            set { lockUnLock = value; }
        }

        #endregion

        #region LockedOut

        /// <summary>
        /// Locked out flag.
        /// </summary>
        public bool LockedOut
        {
            get { return lockedOut; }
            set { lockedOut = value; }
        }

        #endregion

        #region PasswordExpired

        /// <summary>
        /// Password expired flag.
        /// </summary>
        public bool PasswordExpired
        {
            get { return secureExpired; }
            set { secureExpired = value; }
        }

        #endregion

        #region PasswordExpiring

        /// <summary>
        /// Password expiring flag.
        /// </summary>
        public bool PasswordExpiring
        {
            get { return secureExpiring; }
            set { secureExpiring = value; }
        }

        #endregion

        #region NumberDaysPasswordExpires

        /// <summary>
        /// Number of days within which password will expire.
        /// </summary>
        public long NumberDaysPasswordExpires
        {
            get { return numDaysSecureExpires; }
            set { numDaysSecureExpires = value; }
        }

        #endregion

        #region NoOfFailedLogOnAttempts

        /// <summary>
        /// Number of failed login attempts.
        /// </summary>
        public long NoOfFailedLogOnAttempts
        {
            get { return noOfAttemptsToLogOn; }
            set { noOfAttemptsToLogOn = value; }
        }

        #endregion

        #region NoOfFailedResetAttempts

        /// <summary>
        /// Number of failed reset attempts.
        /// </summary>
        public long NoOfFailedResetAttempts
        {
            get { return noOfAttemptsToReset; }
            set { noOfAttemptsToReset = value; }
        }

        #endregion

        #region FirstTimeUser

        /// <summary>
        /// First user flag.
        /// </summary>
        public bool FirstTimeUser
        {
            get { return isFirstTime; }
            set { isFirstTime = value; }
        }

        #endregion

        #region AdditionalInfo

        /// <summary>
        /// Additional info about the user account.
        /// </summary>
        public string AdditionalInfo
        {
            get { return additionalInfo; }
            set { additionalInfo = value; }
        }

        #endregion

        #region Ticket
        /// <summary>
        /// Gets or sets ticket.
        /// </summary>
        public String Ticket
        {
            get { return (ticket); }
            set { ticket = value; }
        }

        #endregion

        #region StaffLogOnSeq
        /// <summary>
        /// Gets or sets staff logon sequence
        /// </summary>
        public long StaffLogOnSeq
        {
            get { return staffLogOnSeq; }
            set { staffLogOnSeq = value; }
        }

        #endregion

        #region EpnEnabled

        /// <summary>
        /// Gets or sets the epn enabled.
        /// </summary>
        public bool EpnEnabled
        {
            get { return configurationData.EpnEnabled; }
            set { configurationData.EpnEnabled = value; }            
        }

        #endregion

        #region EpnPrefix

        /// <summary>
        /// Gets or sets the epn prefix.
        /// </summary>
        public string EpnPrefix
        {
            get { return configurationData.EpnPrefix; }
            set { configurationData.EpnPrefix = value; }            
        }

        #endregion

        #region UserInstanceId
        /// <summary>
        /// Gets or sets the user instance id.
        /// </summary>
        public int UserInstanceId
        {
            get { return userInstanceId; }
            set { userInstanceId = value; }
        }

        #endregion

        #region Facilities
        /// <summary>
        /// Gets facilities
        /// </summary>
        public IList<FacilityDetails> Facilities
        {
            get
            {
                if (facilities == null)
                {
                    facilities = new List<FacilityDetails>();
                }
                return facilities;
            }
        }

        public IList<FacilityDetails> SortedFacilities
        {
            get
            {
                facilities.Sort();
                return facilities;
            }
        }
//added for DE2121

        public IList<FacilityDetails> HPFFacilities
        {
            get
            {
                hPFFacilities = new List<FacilityDetails>();
                foreach (FacilityDetails facility in facilities)
                {
                    if (facility.Type == FacilityType.Hpf)
                    {
                        hPFFacilities.Add(facility);
                    }

                }
                hPFFacilities.Sort();
                return hPFFacilities;
            }
        }     

        public FacilityDetails DefaultFacility
        {
            get 
            {
                if (defaultFacility == null)
                {
                    return new FacilityDetails();
                }
                return defaultFacility; 
            }
            set { defaultFacility = value; }
        }

        #endregion


        #region SecurityFacilities
        /// <summary>
        /// Gets security facilities ID
        /// </summary>
        public Dictionary<string, UserSecurities> Security
        {
            get
            {
                if (securityFacilities == null)
                {
                    securityFacilities = new Dictionary<string, UserSecurities>();
                }
                return securityFacilities;
            }
        }

        #endregion

        #region RecordViews
        /// <summary>
        /// Gets recordViews
        /// </summary>
        public Collection<RecordViewDetails> RecordViews
        {
            get
            {
                if (recordViews == null)
                {
                    recordViews = new Collection<RecordViewDetails>();
                }
                return recordViews;
            }
        }

        #endregion

        #region SSNMasked
        /// <summary>
        /// Gets or Sets the value for SSNMasked property
        /// </summary>
        public bool IsSSNMasked
        {
            get { return isSSNMasked; }
            set { isSSNMasked = value; }
        }

        #endregion

        #region ConfigurationData
        /// <summary>
        /// Gets or Sets the value for configuration data object.
        /// </summary>
        public ConfigurationData ConfigurationData
        {
            get { return configurationData;  }            
            set { configurationData = value; }
        }

        #endregion

        #region SigninState

        /// <summary>
        /// Gets or Sets the value for signin state.
        /// </summary>
        public int SignInstate
        {
            get { return signInState; }
            set { signInState = value; }
        }

        #endregion        

        #region IsLdapEnabled

        /// <summary>
        /// Is LDAP Enabled.
        /// </summary>
        public bool IsLdapEnabled
        {
            get { return isLdapEnabled; }
            set { isLdapEnabled = value; }
        }

        #endregion

        #region IsSelfMappingEnabled

        /// <summary>
        /// Is Self Mapping Enabled.
        /// </summary>
        public bool IsSelfMappingEnabled
        {
            get { return isSelfMappingEnabled; }
            set { isSelfMappingEnabled = value; }
        }

        #endregion

        #region DomainList

        /// <summary>
        /// Domain Lists.
        /// </summary>
        public Collection<string> DomainList
        {
            get 
            {
                if (domainsList == null)
                {
                    domainsList = new Collection<string>();
                }
                return domainsList; 
            }
        }
       
        public SortedList<string, string> UserLists
        {
            get
            {
                if (userLists == null)
                {
                    userLists = new SortedList<string, string>();
                }
                return userLists;
            }
        }

        #endregion

        #region Domain

        /// <summary>
        /// Domain Selected
        /// </summary>
        public string Domain
        {
            get { return domain; }
            set { domain = value; }
        }

        #endregion

        #region HPF User Id

        /// <summary>
        /// HPF Username for self-mapping
        /// </summary>
        public string HpfUserId
        {
            get { return hpfUserId; }
            set { hpfUserId = value; }
        }

        /// <summary>
        /// HPF Username for self-mapping
        /// </summary>
        public string HpfMappedUserId
        {
            get { return hpfMappedUserId; }
            set { hpfMappedUserId = value; }
        }


        #endregion

        #region Hpf Password

        /// <summary>
        /// HPF Password for self-mapping
        /// </summary>
        public string HpfSecretWord
        {
            get { return hpfSecure; }
            set { hpfSecure = value; }
        }

        /// <summary>
        /// property holds the invoice due days attributes(0,30,60,90,120)
        /// </summary>
        public ArrayList InvoiceDueDays
        {
            get 
            {
                if (invoiceduedays == null)
                {
                    invoiceduedays = new ArrayList();
                }
                return invoiceduedays; 
            }
            set { invoiceduedays = value; }
        }

        #endregion

        #region ConfigureUnbillableRequest
        /// <summary>
        /// Gets or Sets the value for ConfigureUnbillableRequest property
        /// </summary>
        public bool IsUnbillableRequest
        {
            get { return isunbillableRequest; }
            set { isunbillableRequest = value; }
        }

        #endregion

        #endregion

        #region Methods

        /// <summary>
        /// Get user data instance from singleton.
        /// </summary>
        /// <returns></returns>
        public static UserData Instance
        {
            get { return UserData.userData; }
        }

        /// <summary>
        /// Set user data instance.
        /// </summary>
        /// <param name="userData"></param>
        public static void SetInstance(UserData userData)
        {
            if (userData != null)
            {
                UserData.userData = userData;
            }
            else
            {
                UserData.userData = new UserData();
            }
            
        }


        /// <summary>
        /// Returns true if the securityrightid is available.
        /// </summary>
        /// <param name="securityRightId"></param>
        /// <returns></returns>
        public bool HasAccess(string securityRightId)
        {   
            return HasAccess(securityRightId, ROISecurityRights.DefaultFacility);
        }

        /// <summary>
        /// Returns true if the securityrightid is available with the associated facility
        /// </summary>
        /// <param name="securityRightId"></param>
        /// <param name="facility"></param>
        /// <returns></returns>
        public bool HasAccess(string securityId, string facility)
        {
            if (securityFacilities == null) return false;
            return !securityFacilities.ContainsKey(facility) ? false : securityFacilities[facility].IsAllowed(securityId);
        }

        /// <summary>
        /// Reset all the fields
        /// </summary>
        public void Reset()
        {
            Reset(true);
        }

        public void Reset(bool clearConfigurationData)
        {
            inValidLogOnCount = 0;
            lockUnLock = false;
            lockedOut = false;
            secureExpired = false;
            secureExpiring = false;
            numDaysSecureExpires = 0;
            noOfAttemptsToLogOn = 0;
            noOfAttemptsToReset = 0;
            isFirstTime = false;
            additionalInfo = string.Empty;
            ticket = string.Empty;
            staffLogOnSeq = 0;
            userInstanceId = 0;
            Facilities.Clear();
            //FreeformFacilities.Clear();
            if (clearConfigurationData)
            {
                this.ConfigurationData = null;
            }
            RecordViews.Clear();
            Security.Clear();
            UserLists.Clear();
            secure = new StringBuilder();
            domainUserName = string.Empty;
            domainSecure = new StringBuilder();
        }

        ///// <summary>
        ///// Add new freeform facilities
        ///// </summary>
        ///// <param name="freeForm"></param>
        //public void AddFreeformFacility(string freeformFacility)
        //{            
        //    if (freeformFacility == null) return;

        //    freeformFacility = freeformFacility.Trim();

        //    if (!(Facilities.ContainsKey(freeformFacility) || FreeformFacilities.ContainsKey(freeformFacility)))
        //    {
        //        FreeformFacilities.Add(freeformFacility, freeformFacility);
        //    }
        //}       

        /// <summary>
        /// Get service security token.
        /// </summary>
        /// <returns></returns>
        public SecurityToken SecurityToken
        {
            get
            {
                //current key
                string key = this.UserId + ":" + this.SecretWord;

                string strUserName     = this.UserId;
                string strUserSecure = "dummy"; 
                
                if (this.isLdapEnabled)
                {
                    strUserName     = this.Domain + "\\" + this.DomainUserName + "~" + this.HpfUserId;
                    strUserSecure = this.DomainSecretWord;
                }

                if (this.tokenInUse == null)
                {
                    
                    //first time getting the token
                    SecurityToken userNameToken = new UsernameToken(strUserName,
                                                                    strUserSecure,
                                                                    PasswordOption.SendPlainText);
                    this.tokenInUse = new object[] { key, userNameToken };
                    return userNameToken;
                }

                if (!key.Equals(this.tokenInUse[0].ToString()))
                {
                    //old token is invalid b/c of userid or pwd change
                    SecurityToken userNameToken = new UsernameToken(strUserName,
                                                                    strUserSecure,
                                                                    PasswordOption.SendPlainText);
                    this.tokenInUse = new object[] { key, userNameToken };
                    return userNameToken;
                }

                //reuse token
                SecurityToken token = (SecurityToken)this.tokenInUse[1];
                return token;
            }
        }
        #endregion
    }
}
 

       
