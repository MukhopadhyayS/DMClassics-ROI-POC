using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.Controller
{
    public static class RequestPatientsCache
    {
        private static Dictionary<long, RequestPatients> reqPatientDetailsCache = new Dictionary<long, RequestPatients>();

        public static void AddData(long requestId, RequestPatients reqPatDetails)
        {
            if (reqPatientDetailsCache.ContainsKey(requestId))
            {
                reqPatientDetailsCache.Remove(requestId);
            }

            reqPatientDetailsCache.Add(requestId, reqPatDetails);
        }

        public static bool IsKeyExist(long requestId)
        {
            if (reqPatientDetailsCache.Count > 0)
            {
                return reqPatientDetailsCache.ContainsKey(requestId);
            }

            return false;
        }
        public static void RemoveKey(long requestId)
        {
            if (reqPatientDetailsCache.ContainsKey(requestId))
                reqPatientDetailsCache.Remove(requestId);
        }
        public static RequestPatients GetReqPatients(long requestId)
        {
            if (reqPatientDetailsCache.Count > 0)
                return reqPatientDetailsCache[requestId];
            return null;
        }
    }
    public static class OutputPropertyDetailsCache
    {
        private static Dictionary<string, OutputPropertyDetails> outputPropertyDetailsCache = new Dictionary<string, OutputPropertyDetails>();

        public static void AddData(string outputMethod, OutputPropertyDetails reqPatDetails)
        {
            if (outputPropertyDetailsCache.ContainsKey(outputMethod))
            {
                outputPropertyDetailsCache.Remove(outputMethod);
            }

            outputPropertyDetailsCache.Add(outputMethod, reqPatDetails);
        }

        public static bool IsKeyExist(string outputMethod)
        {
            if (outputPropertyDetailsCache.Count > 0)
            {
                return outputPropertyDetailsCache.ContainsKey(outputMethod);
            }

            return false;
        }
        public static void RemoveKey(string outputMethod)
        {
            if (outputPropertyDetailsCache.ContainsKey(outputMethod))
                outputPropertyDetailsCache.Remove(outputMethod);
        }
        public static OutputPropertyDetails GetOutputPropertyDetails(string outputMethod)
        {
            if (outputPropertyDetailsCache.Count > 0)
                return outputPropertyDetailsCache[outputMethod];
            return null;
        }

    }

    public static class RequestBillingInfoCache
    {
        private static Dictionary<long, RequestBillingInfo> requestBillingInfoCache = new Dictionary<long, RequestBillingInfo>();

        public static void AddData(long requestId, RequestBillingInfo requestBillingInfo)
        {
            if (requestBillingInfoCache.ContainsKey(requestId))
            {
                requestBillingInfoCache.Remove(requestId);
            }

            requestBillingInfoCache.Add(requestId, requestBillingInfo);
        }

        public static bool IsKeyExist(long requestId)
        {
            if (requestBillingInfoCache.Count > 0)
            {
                return requestBillingInfoCache.ContainsKey(requestId);
            }

            return false;
        }
        public static void RemoveKey(long requestId)
        {
            if (requestBillingInfoCache.ContainsKey(requestId))
                requestBillingInfoCache.Remove(requestId);
        }
        public static RequestBillingInfo GetRequestBillingInfo(long requestId)
        {
            if (requestBillingInfoCache.Count > 0)
                return requestBillingInfoCache[requestId];
            return null;
        }
    }

    public static class HasSecurityRights
    {
        private static bool hasSecurityRights;
        private static bool invokedflag;

        public static void UpdateSecurityRights(bool value)
        {
            hasSecurityRights = value;
            invokedflag = true;
        }

        public static bool IsSecurityRightsInvoked()
        {
            return invokedflag;
        }
        public static bool GetSecurityRights()
        {
            return hasSecurityRights;
        }
    }
}
