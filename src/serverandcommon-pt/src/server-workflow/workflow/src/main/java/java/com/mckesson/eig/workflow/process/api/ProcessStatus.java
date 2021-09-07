package com.mckesson.eig.workflow.process.api;

/**
 *  Define the values for process status
 *
 */
public final class ProcessStatus {
    /*Constraint on ProcessVersion tbl
     *WF_P_VERSION_STATUS
     *STATUS   IN ( 'New','Validated','In Progress','Deployed')
     */
    protected static final String PROCESS_STATUS_DEPLOYED = "Deployed";
    protected static final String PROCESS_STATUS_WIP      = "In Progress";
    protected static final String PROCESS_STATUS_NEW      = "New";
    protected static final String PROCESS_STATUS_VALIDATE = "Validated";

    private ProcessStatus() {
        super();
    }

}
