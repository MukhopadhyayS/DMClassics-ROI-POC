package com.mckesson.eig.roi.base.model;

public class ROIAuditConstants {
    public static final String GLOBAL_FACILITY = "E_P_R_S";
    
    public static enum SupplementaryAudit {
        /** the service is initializing */
        CREATE_PATIENT ("2", "ROI user {0} created a new non-HPF patient {1}"),
        EDIT_PATIENT ("2", "{0} modified the patient's information :'{1}','{2}','{3}' to '{4}','{5}','{6}'"),
        DELETE_PATIENT ("2", "ROI user {0} deleted a non-HPF patient {1}"),
        
        CREATE_DOCUMENT ("2", "the {0} created a new non-HPF document {1}."),
        EDIT_DOCUMENT ("2", "The {0} modified the non-HPF document '{1}'-'{2}'-'{3}'-'{4}'-'{5}' to '{6}'-'{7}'-'{8}'-'{9}'-'{10}'"),
        DELETE_DOCUMENT ("2", "the {0} deleted non-HPF document {1}"),

        CREATE_ATTACHMENT ("N", "{0} attachment - {1} added"),
        CREATE_ATTACHMENT_EXT_SOURCE ("N", "{0} attachment - {1} acquired from {2}"),
        EDIT_ATTACHMENT ("U", "{0} attachment - {1} metadata updated"),
        DELETE_ATTACHMENT ("X", "{0} attachment - {1} deleted");

        private final String _auditString;
        
        private final String _auditCode;

        SupplementaryAudit(String auditCode,  String auditString) {
            _auditString = auditString;
            _auditCode = auditCode;
        }
        
        /**
         * The descriptive string for the status
         *
         * @return not <code>null</code>
         */
        public String auditString() {
            return _auditString;
        }

        /**
         * The code value for the status
         *
         * @return the code
         */
        public String auditCode() {
            return _auditCode;
        }

    }

}
