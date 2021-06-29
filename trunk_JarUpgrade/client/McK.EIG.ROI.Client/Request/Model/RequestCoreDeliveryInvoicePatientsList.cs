using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Request.Model
{
    public class RequestCoreDeliveryInvoicePatientsList
    {
        private long requestCoreDeliveryChargesId;
        
        private string epn;
        
        private string mrn;
        
        private string ssn;
        
        private string name;
        
        private string facility;
        
        private bool isVIP;
        
        private string encounterFacility;
        
        public long RequestCoreDeliveryChargesId {
            get {return requestCoreDeliveryChargesId;}
            set {requestCoreDeliveryChargesId = value;}
        }
        
        public string Epn {
            get {return epn;}
            set {epn = value;}
        }
        
        public string Mrn {
            get {return mrn;}
            set {mrn = value;}
        }
        
        public string Ssn {
            get {return ssn;}
            set {ssn = value;}
        }
        
        public string Name {
            get {return name;}
            set {name = value;}
        }
        
        public string Facility {
            get {return facility;}
            set {facility = value;}
        }
        
        public bool IsVIP {
            get {return isVIP;}
            set {isVIP = value;}
        }
        
        public string EncounterFacility {
            get {return encounterFacility;}
            set {encounterFacility = value;}
        }
    }
    
}
