/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.exception;

import java.util.HashMap;

/**
 * This class holds the set of error codes used by the server to return error
 * conditions to a client. Many of the codes listed here are keyed to message
 * code in the HECM User Interface Styles & Standards Guide, Message Inventory
 * section.
 */
public class ClientErrorCodes {

    // items in the HECM User Interface Styles & Standards Guide
    // Message Inventory
    public static final String ACL_ALREADY_EXISTS = "A005";
    public static final String ROLE_ALREADY_EXISTS = "A010";
    public static final String INVALID_WINDOWS_FILE_NAME = "A111";
    public static final String FILE_NAME_EXISTS = "A112";
    public static final String FILE_NAME_LOCKED = "A113";
    public static final String RENAME_CONTENT_OPERATION_FAILED = "A114";
    public static final String MISSING_VALUE = "A022";
    public static final String USER_ID_IS_ALREADY_IN_USE = "A025";
    public static final String DOMAIN_CONTENT_TYPE_INUSE = "A029";
    public static final String DUPLICATE_DOMAIN_CONTENT_TYPE_NAME = "A030";
    public static final String FILE_ALREADY_EXISTS = "C001";
    public static final String URL_ALREADY_EXISTS = "C002";
    public static final String URL_OR_URL_NAME_IS_INVALID = "C003";
    public static final String SYSTEM_COULD_NOT_LOG_YOU_ON = "S001";
    public static final String ACCOUNT_IS_LOCKED = "S002";
    public static final String ACCOUNT_IS_DISABLED = "S004";
    //hardcoded password veracode fix changing Password varibale to PD
    public static final String PD_HAS_EXPIRED = "S005";
    public static final String VALUE_CHARACTER_STRING_TOO_LONG = "S008";
    public static final String VALUE_IS_INVALID = "S015";
    public static final String VALUE_IS_OUT_OF_RANGE = "S016";
    public static final String MIN_NUM_REQ_SPECIAL_CHARS_EXCEEDS_MAX_PD_LENGTH = "S017";
    public static final String THE_MINIMUM_LENGTH_EXCEEDS_THE_MAXIMUM_PD_LENGTH = "S018";
    public static final String SECURITY_ANSWER_INCORRECT = "S022";
    public static final String INVALID_AUTO_LOG_OFF_DURATION = "S025";
    public static final String ERROR_IN_CREATING_YOUR_NEW_PD = "S030";
    public static final String OLD_PD_IS_INCORRECT = "S030a";
    public static final String NEW_PD_AND_CONFIRMATION_DO_NOT_MATCH = "S030b";
    public static final String PD_LENGTH_TO_SHORT = "S030c";
    public static final String PD_LENGTH_TO_LONG = "S030c";
    public static final String PD_NOT_COMPLEX_ENOUGH = "S030d";
    public static final String PD_TOO_CLOSELY_RESEMBLES_AN_OLD_ONE = "S030e";
    public static final String PD_AND_USER_ID_CANNOT_BE_THE_SAME = "S030f";
    public static final String MULTIPLE_FILE_OPERATIONS_NOT_SUPPORTED = "X001";
    public static final String CONTENT_CANNOT_BE_DELETED = "RC15";

    // configure domain error codes
    public static final String DOMAIN_NAME_ALREADY_EXISTS  = "T210";
    public static final String DOMAIN_NAME_IS_MANDATORY    = "T211";
    public static final String INVALID_DOMAIN_NAME_LEN     = "T212";
    public static final String INVALID_PRIMARY_STORAGE_LEN = "T213";
    public static final String INVALID_ARCHIVE_STORAGE_LEN = "T214";
    public static final String INVALID_CONATCT_PERSON_LEN  = "T215";
    public static final String INVALID_CONTACT_PHONE_LEN   = "T216";
    public static final String INVALID_ADDRESS_ONE_LEN     = "T217";
    public static final String INVALID_ADDRESS_TWO_LEN     = "T218";
    public static final String INVALID_CITY_LEN            = "T219";
    public static final String INVALID_STATE_LEN           = "T220";
    public static final String INVALID_ZIP_LEN             = "T221";
    public static final String INVALID_COUNTRY_LEN         = "T222";
    public static final String DOMAIN_NULL                 = "T223";
    public static final String INVALID_DOMAIN_ID           = "T224";
    public static final String DOMAIN_WITH_SPECIFIED_ID_UNAVAILABLE = "T225";
    public static final String DOMAIN_POOL_PATH_ALREADY_IN_USE      = "T226";
    public static final String INVALID_SIGN_TEXT           = "T227";
    

    //configure domain metafields error codes
    public static final String FIELD_NAME_IS_MANDATORY     = "T250";
    public static final String INVALID_FIELD_NAME_LEN      = "T251";
    public static final String INVALID_DESC_NAME_LEN       = "T252";
    public static final String INVALID_METADATA_FIELD_ID   = "T253";
    public static final String METADATA_FIELD_NULL         = "T254";
    public static final String METADATA_FIELD_NAME_NULL    = "T255";
    public static final String INVALID_METADATA_FIELD_TYPE = "T256";
    public static final String METADATA_FIELD_DOES_NOT_EXISTS = "T257";
    public static final String METADATA_FIELD_NAME_ALREADY_EXISTS = "T258";
    public static final String METADATA_FIELD_ASSOCIATED_WITH_TEMPLATE = "T259";
    public static final String METADATA_FIELD_WITH_NO_CHOICE = "T260";
    public static final String METADATA_FIELD_WITH_INVALID_CHOICE_LEN = "T261";
    public static final String METADATA_FIELD_WITH_DUPLICATE_CHOICE = "T262";

    // move content error codes
    public static final String MOVE_SOME_METADATA_FIELDS_NOT_SUPPORTED = "MV018";
    public static final String MOVE_REQUIRED_METADATA_FIELDS_MISSING = "MV006";
    public static final String MOVE_METADATA_FIELDS_MISMATCH = "MV011";
    public static final String MOVE_AT_LEAST_ONE_DUPLICATED_CONTENT_NAME = "MV007";
    public static final String MOVE_ALL_CONTENT_NAMES_DUPLICATED = "MV023";
    public static final String MOVE_FOLDER_TO_DESCENDANT = "MV015";

    // items NOT in the HECM User Interface Styles & Standards Guide
    // Message Inventory
    public static final String OTHER_SERVER_ERROR = "T000";
    public static final String UNABLE_TO_FIND_OBJECT = "T001";
    public static final String DATABASE_ACCESS_ERROR = "T002";
    public static final String CONTENT_WITH_THE_SAME_NAME_ALREADY_EXISTS_IN_THIS_FOLDER = "T003";
    public static final String NO_DATA_FOR_REQUEST_FOUND = "T007";
    public static final String NO_CONTENT_MATCHING_YOUR_CRITERIA_WAS_FOUND = "T067";
    public static final String YOU_ALREADY_SUBSCRIBE_TO_THIS_FOLDER = "T011";
    public static final String CONTENT_CHECKED_OUT_BY_ANOTHER_USER_AND_CANNOT_BE_DELETED = "T016";
    public static final String FOLDER_NOT_SHARED_OR_NOT_HAVING_ACCESS_EXCEPTION = "T017";

    //Log Configuration
    public static final String UNABLE_TO_READ_FILE = "T050";
    public static final String UNABLE_TO_FIND_FILE = "T051";
    public static final String UNABLE_TO_RETRIEVE_LOG_INFORMATION = "T052";
    public static final String UNABLE_TO_SAVE_LOG_INFORMATION = "T053";
    public static final String FILE_SECURITY = "T054";
    public static final String INVALID_FILE_SIZE = "T055";
    public static final String INVALID_MAX_FILES = "T056";
    public static final String INVALID_LOGGING_LEVEL = "T057";
    public static final String INVALID_COMPONENT_NAME = "T058";
    public static final String INVALID_COMPONENT_SEQ = "T059";
    public static final String EMPTY_LOG_INFORMATION = "T060";
    public static final String NIL_COMPONENT_INSTALLED = "T061";
    public static final String ELEMENT_NOT_FOUND = "T062";
    public static final String INVALID_SESSION = "T063";
    public static final String INVALID_FILE_NAME = "T064";
    public static final String INVALID_LOG4J_ELEMENT = "T065";
    public static final String INVALID_FILE_TYPE = "T066";
    public static final String FILE_LEGTH_EXCEEDED = "T068";

    public static final String INVALID_COMPONENT_ID = "SV_EC_01";
    public static final String NULL_COMPONENT_ID = "SV_EC_02";

    // Error codes related to configure roles usecase
    public static final String FAILED_TO_CREATE_NEW_ROLE = "T018";
    public static final String ROLE_DOES_NOT_EXIST = "T019";
    public static final String CANNOT_CREATE_SYSTEM_DEFINED_ROLE = "T020";
    public static final String CANNOT_MODIFY_SYSTEM_DEFINED_ROLE = "T021";
    public static final String CANNOT_MODIFY_SYSTEM_DEFINED_ACL = "T040";

    // Security Related Error Codes
    public static final String ACCOUNT_NOT_ENABLED = "T022";
    public static final String PD_CONTAINS_REPEATED_SEQUENCES = "T023";
    public static final String PD_FAILS_DICTIONARY_CHECK = "T024";
    public static final String INVALID_TICKET = "T025";
    public static final String SECURITY_TOKEN_MISSING_USERNAME = "T026";
    public static final String SECURITY_TOKEN_MISSING_INFORMATION = "T027";
    public static final String REAUTHENTICATE_USERNAME_CHECK = "T028";
    public static final String INVALID_APPLICATION_ID = "T250";
    public static final String USER_SECURITY_INSUFFICIENT = "T300";

    // General errors
    public static final String SYSTEM_ERROR = "T029";
    public static final String DISPLAY_ONLY_FIELD_CANNOT_BE_UPDATED = "T030";
    public static final String INVALID_ID = "T031";
    public static final String VALIDATION_FAILED = "T032";
    public static final String OPTIMISTIC_LOCKING_COLLISION = "T033";
    public static final String DATA_INTEGRITY_VIOLATION = "T034";
    public static final String UNABLE_TO_EMAIL_MESSAGE = "T035";
    public static final String DATA_OUT_OF_DATE = "T036";
    public static final String UNIQUE_CONSTRAINT_VIOLATION = "T037";
    
    // LINK AND UNLINK CONTENTS ERROR CODES
    public static final String UNABLE_TO_RETRIEVE_CONTENTS = "T201";
    public static final String CONTENT_NOT_LINKED_TO_TASK  = "T202";
    public static final String INVALID_CONTENT_FOR_LINKING = "T203";
    public static final String INSUFFICIENT_PRIVILEGE_TO_LINK_CONTENT = "T204";

    // CONFIGURE DOMAIN FOLDERS
    public static final String FOLDER_NAME_ALREADY_EXISTS     = "T231";
    public static final String FOLDER_NAME_IS_MANDATORY       = "T232";
    public static final String FOLDER_NAME_EXCEEDED_THE_LIMIT = "T233";
    public static final String DESCRIPTION_EXCEEDED_THE_LIMIT = "T234";
    public static final String FOLDER_IS_NULL                 = "T235";
    public static final String FOLDER_ID_IS_INVALID           = "T236";
    public static final String FOLDER_DOES_NOT_EXIST          = "T237";
    public static final String FOLDER_NAME_IS_INVALID         = "T238";
    public static final String FOLDER_NAME_MATCHES_WITH_FILE  = "T239";

    //CONFIGURE DOMAIN METADATA TEMPLATES
    public static final String TEMPLATE_INVALID_FIELD_COMBINATION               = "T240";
    public static final String TEMPLATE_NAME_EXCEEDS_THE_LIMIT                  = "T241";
    public static final String TEMPLATE_DESCRIPTION_EXCEEDS_THE_LIMIT           = "T242";
    public static final String TEMPLATE_NAME_ALREADY_EXISTS                     = "T243";
    public static final String TEMPLATE_ASSOCIATED_WITH_THE_DOMAIN_FOLDER       = "T244";
    public static final String ONLY_ONE_TEMPLATE_AVAILABLE_FOR_THIS_DOMAIN      = "T245";
    public static final String TEMPLATE_WITH_THE_SPECIFIC_ID_DOES_NOT_AVAILABLE = "T246";
    public static final String METADATA_TEMPLATE_IS_NULL                        = "T247";
    public static final String TEMPLATE_NAME_IS_NULL                            = "T248";
    public static final String INVALID_TEMPLATE_ID                              = "T249";

    public static final String SIGNATURE_MGR_COMPONENETS_NOT_INSTALLED          = "T250";
    
    public static final String LOAD_CONTENT_BATCH_XML_BAD                       = "T270";
    public static final String SOAP_MESSAGE_TIMED_OUT                           = "T271";
    public static final String INVALID_SOAP_MESSAGE                             = "T272";
    
    // APPLICATION(OUTPUT) INTEGRATION 
    public static final String HOST_NAME_SHOULD_NOT_BE_EMPTY                    = "OU001";
    public static final String INVALID_HOST_NAME                                = "OU002";
    public static final String PORT_NUMBER_SHOULD_NOT_BE_EMPTY                  = "OU003";
    public static final String INVALID_PORT_NUMBER                              = "OU004";
    public static final String PROTOCOL_SHOULD_NOT_BE_EMPTY                     = "OU005";
    public static final String INVALID_PROTOCOL                                 = "OU006";
    public static final String OUTPUT_SERVICE_SHOULD_BE_ENABLED                 = "OU007";
    
    
    public static final String MSG_SIGNATURE_MGR_COMPONENETS_NOT_INSTALLED
                        = "Third Party Signature Component not installed";

    public static final String MSG_UNIQUE_CONSTRAINT_VIOLATION = "UNIQUE_CONSTRAINT_VIOLATION";

    // never to be actually instantiated
    protected ClientErrorCodes() {
        // changed to public for CheckStyle
        throw new UnsupportedOperationException("Not to be instantiated");
    }

    public static final HashMap<String, String> ERROR_CODE_DESC_MAP = new HashMap<String, String>();

    static
    {
        ERROR_CODE_DESC_MAP.put(OTHER_SERVER_ERROR, "Other Server Error");
        ERROR_CODE_DESC_MAP.put(SYSTEM_ERROR, "System Error");
        ERROR_CODE_DESC_MAP.put(UNABLE_TO_FIND_OBJECT, "Unable to find object");
        ERROR_CODE_DESC_MAP.put(DATABASE_ACCESS_ERROR, "Database Access Error");
        ERROR_CODE_DESC_MAP.put(INVALID_ID, "Invalid ID");
        ERROR_CODE_DESC_MAP.put(INVALID_DOMAIN_ID, "Invalid Domain ID");
        ERROR_CODE_DESC_MAP.put(DOMAIN_NAME_ALREADY_EXISTS, "Domain Name already exists");
        ERROR_CODE_DESC_MAP.put(DOMAIN_WITH_SPECIFIED_ID_UNAVAILABLE, "Domain not exists");
        ERROR_CODE_DESC_MAP.put(FOLDER_NAME_ALREADY_EXISTS, "Folder already exists");
        ERROR_CODE_DESC_MAP.put(FOLDER_NAME_EXCEEDED_THE_LIMIT, "Folder name exceed the limit");
        ERROR_CODE_DESC_MAP.put(FOLDER_ID_IS_INVALID, "Invalid FolderID");
        ERROR_CODE_DESC_MAP.put(FOLDER_IS_NULL, "domain folder cannot be null");
        ERROR_CODE_DESC_MAP.put(FOLDER_NAME_IS_MANDATORY, "Folder name is mandatory");
        ERROR_CODE_DESC_MAP.put(FOLDER_NAME_IS_INVALID, "Folder name is invalid");
        ERROR_CODE_DESC_MAP.put(FOLDER_DOES_NOT_EXIST, "Folder does not exist");
        ERROR_CODE_DESC_MAP.put(DESCRIPTION_EXCEEDED_THE_LIMIT,
                                "Folder description exceed the limit");

        ERROR_CODE_DESC_MAP.put(DOMAIN_WITH_SPECIFIED_ID_UNAVAILABLE,
                                "Domain with specified ID is unavailable");
        ERROR_CODE_DESC_MAP.put(TEMPLATE_NAME_EXCEEDS_THE_LIMIT,
                                "Template name exceeds the limit");
        ERROR_CODE_DESC_MAP.put(TEMPLATE_DESCRIPTION_EXCEEDS_THE_LIMIT,
                                "Template description exceeds the limit");
        ERROR_CODE_DESC_MAP.put(TEMPLATE_NAME_ALREADY_EXISTS, "Template name already exists");

        ERROR_CODE_DESC_MAP.put(TEMPLATE_ASSOCIATED_WITH_THE_DOMAIN_FOLDER,
                                "Invalid operation, template associated with the domain folder.");
        ERROR_CODE_DESC_MAP.put(ONLY_ONE_TEMPLATE_AVAILABLE_FOR_THIS_DOMAIN,
                                "Invalid operation, this is the only template for the domain.");
        ERROR_CODE_DESC_MAP.put(TEMPLATE_WITH_THE_SPECIFIC_ID_DOES_NOT_AVAILABLE,
                                "Template with the specific ID does not available");
        ERROR_CODE_DESC_MAP.put(METADATA_TEMPLATE_IS_NULL, "Metadata Template is null");
        ERROR_CODE_DESC_MAP.put(TEMPLATE_NAME_IS_NULL, "Template name is null");
        ERROR_CODE_DESC_MAP.put(INVALID_TEMPLATE_ID, "Invalid Template ID");

        ERROR_CODE_DESC_MAP.put(FIELD_NAME_IS_MANDATORY, "Field Name is mandatory");
        ERROR_CODE_DESC_MAP.put(INVALID_DESC_NAME_LEN, "Description exceeded the limit");
        ERROR_CODE_DESC_MAP.put(INVALID_METADATA_FIELD_ID , "Invalid Metadata Field ID");
        ERROR_CODE_DESC_MAP.put(METADATA_FIELD_NULL, "Meta Data Field is null");
        ERROR_CODE_DESC_MAP.put(METADATA_FIELD_NAME_NULL, "Field Name is Null");
        ERROR_CODE_DESC_MAP.put(INVALID_METADATA_FIELD_TYPE, "Invalid Meta Data Field Type");
        ERROR_CODE_DESC_MAP.put(METADATA_FIELD_DOES_NOT_EXISTS,
                                "Field with specified ID is unavailable");
        ERROR_CODE_DESC_MAP.put(METADATA_FIELD_NAME_ALREADY_EXISTS, "Field Name already exists");
        ERROR_CODE_DESC_MAP.put(METADATA_FIELD_ASSOCIATED_WITH_TEMPLATE,
                               "Meta Data Field associated with template");
        ERROR_CODE_DESC_MAP.put(SIGNATURE_MGR_COMPONENETS_NOT_INSTALLED,
                MSG_SIGNATURE_MGR_COMPONENETS_NOT_INSTALLED);
        ERROR_CODE_DESC_MAP.put(INVALID_APPLICATION_ID, "Invalid Application Id");
        ERROR_CODE_DESC_MAP.put(DOMAIN_POOL_PATH_ALREADY_IN_USE, 
                                "Domain Pool Path is already in use");
        ERROR_CODE_DESC_MAP.put(INVALID_SIGN_TEXT, 
        "Sign Text exceeded the limit");

        ERROR_CODE_DESC_MAP.put(INVALID_PORT_NUMBER, "Invalid Port Number");
        ERROR_CODE_DESC_MAP.put(INVALID_PROTOCOL, "Invalid Protocol");
        ERROR_CODE_DESC_MAP.put(INVALID_HOST_NAME, "Invalid Host Name");
        ERROR_CODE_DESC_MAP.put(PORT_NUMBER_SHOULD_NOT_BE_EMPTY, 
                                "Port Number should not be empty");
        ERROR_CODE_DESC_MAP.put(PROTOCOL_SHOULD_NOT_BE_EMPTY, 
                                "Protocol should not be empty");
        ERROR_CODE_DESC_MAP.put(HOST_NAME_SHOULD_NOT_BE_EMPTY, 
                                "Host Name should not be empty");
        ERROR_CODE_DESC_MAP.put(OUTPUT_SERVICE_SHOULD_BE_ENABLED, 
            "Output Service should be enabled, before configuring output server properties");
        
    }
}
