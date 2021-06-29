using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using McK.EIG.ROI.Client.Requestors.Model;
using System.Collections.ObjectModel;

namespace McK.EIG.ROI.Client.Requestors.Controller
{
    public static class RequestorCache
    {

        private static Dictionary<long, Collection<RequestInvoiceDetail>> requestorInvDetailsCache = new Dictionary<long, Collection<RequestInvoiceDetail>>();

        public static void AddData(long requestorId, Collection<RequestInvoiceDetail> reqInvDetails)
        {
            if (requestorInvDetailsCache.ContainsKey(requestorId))
            {
                requestorInvDetailsCache.Remove(requestorId);
            }

            requestorInvDetailsCache.Add(requestorId, reqInvDetails);
        }

        public static bool IsKeyExist(long requestorId)
        {
            if (requestorInvDetailsCache.Count > 0)
            {
                return requestorInvDetailsCache.ContainsKey(requestorId);
            }

            return false;
        }
        public static void RemoveKey(long requestorId)
        {
            if (requestorInvDetailsCache.ContainsKey(requestorId))
                requestorInvDetailsCache.Remove(requestorId);
        }
        public static Collection<RequestInvoiceDetail> GetRequestorInvoDetails(long requestorId)
        {
            if (requestorInvDetailsCache.Count > 0)
                return requestorInvDetailsCache[requestorId];
            return null;
        }

    }
}
