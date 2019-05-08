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
using System.Collections.Generic;
using System.Collections.ObjectModel;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.Controller
{
    public class BaseROIValidator
    {
        #region Fields

        private Collection<ExceptionData> errors;

        #endregion

        #region Methods

        protected void AddError(string errorCode)
        {
            ExceptionData error = new ExceptionData(errorCode);
            Errors.Add(error);
        }

        protected void AddError(string errorCode, string errorData)
        {
            ExceptionData error = new ExceptionData(errorCode, errorData);
            Errors.Add(error);
        }

        public ROIException ClientException
        {
            get { return new ROIException(Errors); }
        }

        protected bool NoErrors
        {
            get { return (errors == null); }
        }

        private Collection<ExceptionData> Errors
        {
            get
            {
                if (errors == null)
                {
                    errors = new Collection<ExceptionData>();
                }
                return errors;
            }
        }

        internal bool ValidateLogOnFields(UserData userData)
        {
            if (string.IsNullOrEmpty(userData.UserId))
            {
                AddError(ROIErrorCodes.UserIdEmpty);
            }
            if (userData.IsLdapEnabled)
            {
                if (string.IsNullOrEmpty(userData.DomainSecretWord))
                {
                    AddError(ROIErrorCodes.SecretWordEmpty);
                }
                if (string.IsNullOrEmpty(userData.Domain))
                {
                    AddError(ROIErrorCodes.InvalidDomain);
                }
            }
            else
            {
                if (string.IsNullOrEmpty(userData.SecretWord))
                {
                    AddError(ROIErrorCodes.SecretWordEmpty);
                }
            }
            if (userData.UserId == ROIConstants.UserMappingRequired && userData.IsSelfMappingEnabled)
            {
                if(string.IsNullOrEmpty(userData.HpfUserId))
                {
                    AddError(ROIErrorCodes.UserIdEmpty);
                }
                if(string.IsNullOrEmpty(userData.HpfSecretWord))
                {
                    AddError(ROIErrorCodes.SecretWordEmpty);
                }
            }
            return NoErrors;
        }

        #endregion
    }
}
