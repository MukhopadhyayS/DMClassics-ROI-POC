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
using System.Collections.ObjectModel;
using System.Globalization;
using System.Net;
using System.Resources;
using System.Runtime.Serialization;
using System.Security.Permissions;
using System.Web.Services.Protocols;

using McK.EIG.Common.Utility.Exceptions;
using McK.EIG.Common.Utility.WebServices;

using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.Base.Model
{
    /// <summary>
    /// To handle the ROIException.
    /// </summary>
    [Serializable] 
    public class ROIException : EIGException
    {
        #region Fields
        private Collection<ExceptionData> errors;
        #endregion

        #region Constructors

        public ROIException() { }

        public ROIException(string errorCode, Exception cause)
            : base(errorCode, cause)
        {
            errors = new Collection<ExceptionData>();
            if (errorCode != null)
            {
                ExceptionData error = new ExceptionData(errorCode, cause);
                errors.Add(error);
            }
        }

        public ROIException(SoapException cause) : base(null, cause) { }

        protected ROIException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
            if (info != null)
            {
                errors = new Collection<ExceptionData>();
                errors.Add((info.GetValue("errorCode", typeof(ExceptionData))) as ExceptionData);
            }
        }

        public ROIException(string errorCode, WebException cause)
            : base(null, cause)
        {
            ExceptionData error = new ExceptionData(errorCode, (errorCode == ROIErrorCodes.ConnectFailure || errorCode == ROIErrorCodes.HpfConnectFailure) ? cause.Source : null);
            errors = new Collection<ExceptionData>();
            errors.Add(error);
        }

        public ROIException(string errorCode)
            : base(errorCode)
        {
            ExceptionData error = new ExceptionData(errorCode);
            errors = new Collection<ExceptionData>();
            errors.Add(error);
        }

        public ROIException(string errorCode, object errorData)
            : base(errorCode)
        {
            ExceptionData error = new ExceptionData(errorCode, errorData);
            errors = new Collection<ExceptionData>();
            errors.Add(error);
        }

        public ROIException(Collection<ExceptionData> errors)
        {
            this.errors = errors;
        }

        #endregion

        #region Methods

        public Collection<ExceptionData> GetErrorMessage(ResourceManager rm)
        {
            if (GetBaseException() is SoapException)
            {
                Collection<ExceptionData> exceptiondata = new Collection<ExceptionData>();
                Collection<SoapExceptionData> soapErrors = SoapExceptionHandler.GetSoapErrorMessages((SoapException)GetBaseException(), rm);
                ExceptionData error;
                foreach (SoapExceptionData soapError in soapErrors)
                {
                    error = new ExceptionData();
                    error.ErrorCode = soapError.ErrorCode;
                    error.ErrorMessage = soapError.ErrorCodeText;
                    exceptiondata.Add(error);
                }
                return exceptiondata;
            }
            else
            {
                Exception cause;
                foreach (ExceptionData error in errors)
                {
                    if ((error.ErrorCode == ROIErrorCodes.ConnectFailure) || (error.ErrorCode == ROIErrorCodes.HpfConnectFailure) ||
                         (error.ErrorCode == ROIErrorCodes.ROIConnectFailure) || (error.ErrorCode == ROIErrorCodes.LogOff))
                    {
                        if (error.ErrorData != null)
                        {
                            error.ErrorMessage = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                               rm.GetString(error.ErrorCode),
                                                               error.ErrorData);

                            error.ErrorData = null;
                        }
                    }
                    else if (error.ErrorCode == ROIErrorCodes.Unknown)
                    {
                        cause = error.ErrorData as Exception;
                        error.ErrorMessage = cause.Message;
                        error.ErrorData = cause.StackTrace;
                    }
                    else
                    {
                        error.ErrorMessage = rm.GetString(error.ErrorCode);
                    }
                }
                return errors;
            }
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public override void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            base.GetObjectData(info, context);
            if (info != null)
            {
                info.AddValue("errorCode", ErrorCodes[0]);
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Returns the errorcodes
        /// </summary>
        public Collection<ExceptionData> ErrorCodes
        {
            get { return errors; }
        }

        #endregion
    }

}
