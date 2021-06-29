using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public static class BillingCache
    {
        private static Dictionary<long, BillingTierDetails> billingTierDetailsCache = new Dictionary<long, BillingTierDetails>();

        public static void AddData(long billingTierId, BillingTierDetails billingTierDetails)
        {
            if (billingTierDetailsCache.ContainsKey(billingTierId))
            {
                billingTierDetailsCache.Remove(billingTierId);
            }

            billingTierDetailsCache.Add(billingTierId, billingTierDetails);
        }

        public static bool IsKeyExist(long billingTierId)
        {
            if (billingTierDetailsCache.Count > 0)
            {
                return billingTierDetailsCache.ContainsKey(billingTierId);
            }

            return false;

        }

        public static void RemoveKey(long billingTierId)
        {
            if (billingTierDetailsCache.ContainsKey(billingTierId))
                billingTierDetailsCache.Remove(billingTierId);
        }

        public static BillingTierDetails GetPatDetails(long billingTierId)
        {
            if (billingTierDetailsCache.Count > 0)
                return billingTierDetailsCache[billingTierId];

            return null;
        }
    }
}
