package com.mckesson.eig.roi.ccd.provider;

public final class CcdProviderConstants {
    
    public static final String ClinicalSummary = "Clinical Summary";
    public static final String DischargeSummary = "Discharge Summary";
    public static final String DischargeInstructions = "Discharge Instructions";
    public static final String PatientRequest = "Patient";
    public static final String DigitalRequest = "Digital";

    public static enum CcdType {
        /** the service is initializing */
        CCD ("CCD"),
        /** the service is running */
        CCR ("CCR");

        private final String _parameter;

        CcdType(String parameter) {
            _parameter = parameter;
        }
        
        public String toString() {
            return _parameter;
        }
    }

    public static enum CcdFileType {
        /** the service is initializing */
        PDF ("pdf"),
        /** the service is running */
        XML ("xml");

        private final String _parameter;

        CcdFileType(String parameter) {
            _parameter = parameter;
        }
        
        public String toString() {
            return _parameter;
        }
    }
    
    
    public static enum RetrieveParameter {
        /** the service is initializing */
        ENCOUNTER ("Encounter"),
        /** the service is running */
        FACILITY ("Facility"),
        /** the service has been paused */
        MRN ("Mrn"),
        ASSIGNING_AUTHORITY ("Assigning_Authority"),
        REQID("Reqid"),
        REQTYPE("ReqType"),
        RECEIPTDATE("ReceiptDate");

        private final String _parameter;
        
        
        RetrieveParameter(String parameter) {
            _parameter = parameter;
        }
        
        public String toString() {
            return _parameter;
        }
    }
}
