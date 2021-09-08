package com.mckesson.eig.workflow.process.api;

import junit.framework.TestCase;

/**
 * class tests the methods of ProcessVersion and  ProcessStatus class
 *
 */
public class TestProcessStatus extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDeployedStatus() {
        String processStatusToTest = ProcessStatus.PROCESS_STATUS_DEPLOYED;
        ProcessVersion pv1 = new ProcessVersion();
        pv1.setDeployedStatus();
        assertEquals(pv1.getStatus(), processStatusToTest);

        verifyStatusSetByDifferentAccessorsAreSame(pv1, processStatusToTest);
    }

    public void testNewStatus() {
        String processStatusToTest = ProcessStatus.PROCESS_STATUS_NEW;
        ProcessVersion pv1 = new ProcessVersion();
        pv1.setNewStatus();
        assertEquals(pv1.getStatus(), processStatusToTest);

        verifyStatusSetByDifferentAccessorsAreSame(pv1, processStatusToTest);
    }

    public void testValidateStatus() {
        String processStatusToTest = ProcessStatus.PROCESS_STATUS_VALIDATE;
        ProcessVersion pv1 = new ProcessVersion();
        pv1.setValidatedStatus();
        assertEquals(pv1.getStatus(), processStatusToTest);

        verifyStatusSetByDifferentAccessorsAreSame(pv1, processStatusToTest);
    }

    public void testInProgressStatus() {
        String processStatusToTest = ProcessStatus.PROCESS_STATUS_WIP;
        ProcessVersion pv1 = new ProcessVersion();
        pv1.setInProgressStatus();
        assertEquals(pv1.getStatus(), processStatusToTest);

        verifyStatusSetByDifferentAccessorsAreSame(pv1, processStatusToTest);
    }

    private void verifyStatusSetByDifferentAccessorsAreSame(ProcessVersion pv1, String status) {
        ProcessVersion pv2 = new ProcessVersion();
        pv2.setStatus(status);
        assertEquals(pv1.getStatus(), pv2.getStatus());
    }
}
