using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Patient.Controller
{
    public static class PatientDetailsCache
    {
        private static Dictionary<string, PatientDetails> patientDetailsCache = new Dictionary<string, PatientDetails>();

        public static void AddData(string indexKey, PatientDetails patDetails)
        {
            if (patientDetailsCache.ContainsKey(indexKey))
            {
                patientDetailsCache.Remove(indexKey);
            }

            patientDetailsCache.Add(indexKey, patDetails);
        }

        public static void RemoveKey(string indexKey)
        {
            if (patientDetailsCache.ContainsKey(indexKey))
            {
                patientDetailsCache.Remove(indexKey);
            }
        }

        public static bool IsKeyExist(string indexKey)
        {
            if (patientDetailsCache.Count > 0)
            {
                return patientDetailsCache.ContainsKey(indexKey);
            }

            return false;

        }
        public static PatientDetails GetPatDetails(string indexKey)
        {
            if (patientDetailsCache.Count > 0)
                return patientDetailsCache[indexKey];

            return null;
        }
    }
}
