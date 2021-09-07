package com.mckesson.eig.roi.muroioutbound;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.ROILoV;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.muroioutbound.dao.MUROIOutboundDAOImpl;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.muroioutbound.service.MUCreateROIOutboundServiceCore;
import com.mckesson.eig.roi.muroioutbound.service.MUUpdateROIOutboundServiceCore;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.service.RequestCoreService;
import com.mckesson.eig.roi.requestor.service.RequestorService;
import com.mckesson.eig.roi.test.BaseROITestCase;

public class TestMUROIOutboundService extends BaseROITestCase {
    protected static final String REQUEST_SERVICE = "com.mckesson.eig.roi.request.service.RequestCoreServiceImpl";
    protected static final String MU_CREATE_ROI_OUTBOUND_SERVICE = "com.mckesson.eig.roi.muroioutbound.service.MUCreateROIOutboundServiceCoreImpl";
    protected static final String MU_UPDATE_ROI_OUTBOUND_SERVICE = "com.mckesson.eig.roi.muroioutbound.service.MUUpdateROIOutboundServiceCoreImpl";
    protected static final String MU_DAO = "MUROIOutboundDAO";
    protected static final String REQUESTOR_SERVICE = "com.mckesson.eig.roi.requestor.service.RequestorServiceImpl";

    private static RequestCoreService _requestCoreService;
    private static MUCreateROIOutboundServiceCore _muCreateOutboundService;
    private static MUUpdateROIOutboundServiceCore _muUpdateOutboundService;
    private static MUROIOutboundDAOImpl _muROIOutboundDAOImpl;
    private static RequestorService _requestorService;
    private static List<MUROIOutboundStatistics> muROIOutboundStatistics = new ArrayList<MUROIOutboundStatistics>();
    private static Set<ROILoV> _lovList;
    private static RequestCore request;
    private static ReleaseCore release;
    private final String SELECT_REQ_EVENT = "select a from com.mckesson.eig.roi.request.model.RequestEvent a where a.requestId= ?";
    private final String SELECT_ROI_LOV = "select a from com.mckesson.eig.roi.base.model.ROILoV a where a.child= ?";
    protected static final Date SAMPLE_DATE = new Date("10/12/12");

    @Override
    public void initializeTestData() throws Exception {

        _muROIOutboundDAOImpl = (MUROIOutboundDAOImpl) getService(MU_DAO);
        _requestCoreService = (RequestCoreService) getService(REQUEST_SERVICE);
        _muCreateOutboundService = (MUCreateROIOutboundServiceCore) getService(MU_CREATE_ROI_OUTBOUND_SERVICE);
        _muUpdateOutboundService = (MUUpdateROIOutboundServiceCore) getService(MU_UPDATE_ROI_OUTBOUND_SERVICE);
        _requestorService = (RequestorService) getService(REQUESTOR_SERVICE);
        refreshTestData(null);
        request = _requestCoreService.retrieveRequest(1002, true);
        request.setReceiptDate(new Date());
        request = _requestCoreService.updateRequest(request);
    }

    public void testMUCreateOutboundService() {
        try {

            _muCreateOutboundService.createROIOutboundStatistics(request);
        } catch (ROIException e) {
            fail("MUCreateOutboundService should not throw exception"
                    + e.getErrorCode());
        }
    }

    public void testMUUpdateOutboundService() {
        try {
            release = releaseCreation();
            muROIOutboundStatistics = _muUpdateOutboundService
                    .updateROIOutboundStatistics(release);
        } catch (ROIException e) {
            fail("MUUpdateOutboundService should not throw exception"
                    + e.getErrorCode());
        }
    }

    public void testMUDeleteOutboundService() {
        try {
            if (muROIOutboundStatistics != null
                    && muROIOutboundStatistics.size() > 0) {
                Iterator i = muROIOutboundStatistics.iterator();
                while (i.hasNext()) {
                    MUROIOutboundStatistics muROIOutboundStatistics = (MUROIOutboundStatistics) i
                            .next();
                    _muROIOutboundDAOImpl.getHibernateTemplate().delete(
                            muROIOutboundStatistics);
                }
            }

            List reqEvents = _muROIOutboundDAOImpl.getHibernateTemplate().find(
                    SELECT_REQ_EVENT, request.getId());
            if (reqEvents != null && reqEvents.size() > 0) {
                Iterator it = reqEvents.iterator();
                while (it.hasNext()) {
                    RequestEvent event = (RequestEvent) it.next();
                    _muROIOutboundDAOImpl.getHibernateTemplate().delete(event);
                }
            }

            _requestCoreService.deleteRequest(request.getId());
            //_muROIOutboundDAOImpl.getHibernateTemplate().delete(request);

        } catch (ROIException e) {
           fail("MUDeleteOutboundService should not throw exception"
                    + e.getErrorCode());
        }
    }

    private ReleaseCore releaseCreation() {
        ReleaseCore rel = new ReleaseCore();
        rel.setRequestId(request.getId());
        return rel;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        // TODO Auto-generated method stub
        return null;
    }

    public void testMUROIOutboundStatistics() {

        MUROIOutboundStatistics outBndStatics = new MUROIOutboundStatistics();

        outBndStatics.setId(1);
        outBndStatics.setReqID(1);
        outBndStatics.setReqStatus("Logged");
        outBndStatics.setReqDate(SAMPLE_DATE);
        outBndStatics.setAvailDate(SAMPLE_DATE);
        outBndStatics.setFulfilledDate(SAMPLE_DATE);
        outBndStatics.setCancelledDate(SAMPLE_DATE);
        outBndStatics.setTa(1);
        outBndStatics.setTotalDays(1);
        outBndStatics.setWeekEndDays(1);
        outBndStatics.setMrn("TestM001");
        outBndStatics.setEncounter("E001");
        outBndStatics.setFacility("E");
        outBndStatics.setHpfMuDocumentType("Test");
        outBndStatics.setUserName(1);
        outBndStatics.setExternalSource("Ext");
        outBndStatics.setReportSname("Test");
        outBndStatics.setRequestFor("Testing");
        outBndStatics.setDocumentType("Test");
        outBndStatics.setPatSeq("Test");
        outBndStatics.setAvailBy(1);
        outBndStatics.setCpiSeq("A");
        outBndStatics.setDischargeDate(SAMPLE_DATE);
        outBndStatics.setPatientName("A");
        outBndStatics.setPatientFirstName("A");
        outBndStatics.setPatientLastName("A");
        outBndStatics.setPatientSex("M");
        outBndStatics.setPatientDOB(SAMPLE_DATE);
        outBndStatics.setOutbound("A");
        outBndStatics.setSelectedForRelease(true);
        outBndStatics.setType("Test");

        assertEquals(1, outBndStatics.getId());
        assertEquals(1, outBndStatics.getReqID());
        assertEquals(1.0, outBndStatics.getTa());
        assertEquals(1.0, outBndStatics.getTotalDays());
        assertEquals(1, outBndStatics.getWeekEndDays());
        assertEquals(1, outBndStatics.getAvailBy());
        assertEquals(1, outBndStatics.getUserName());
        assertEquals("Logged", outBndStatics.getReqStatus());
        assertEquals(SAMPLE_DATE, outBndStatics.getPatientDOB());
        assertEquals(SAMPLE_DATE, outBndStatics.getDischargeDate());
        assertEquals(SAMPLE_DATE, outBndStatics.getAvailDate());
        assertEquals(SAMPLE_DATE, outBndStatics.getFulfilledDate());
        assertEquals(SAMPLE_DATE, outBndStatics.getCancelledDate());
        assertEquals(SAMPLE_DATE, outBndStatics.getReqDate());
        assertEquals("TestM001", outBndStatics.getMrn());
        assertEquals("E001", outBndStatics.getEncounter());
        assertEquals("E", outBndStatics.getFacility());
        assertEquals("Test", outBndStatics.getHpfMuDocumentType());
        assertEquals("Test", outBndStatics.getReportSname());
        assertEquals("Test", outBndStatics.getPatSeq());
        assertEquals("Test", outBndStatics.getType());
        assertEquals("Test", outBndStatics.getDocumentType());
        assertEquals("Ext", outBndStatics.getExternalSource());
        assertEquals("Testing", outBndStatics.getRequestFor());
        assertEquals("A", outBndStatics.getCpiSeq());
        assertEquals("A", outBndStatics.getCpiSeq());
        assertEquals("A", outBndStatics.getPatientName());
        assertEquals("A", outBndStatics.getOutbound());
        assertEquals("A", outBndStatics.getPatientFirstName());
        assertEquals("A", outBndStatics.getPatientLastName());
        assertEquals("M", outBndStatics.getPatientSex());
        assertEquals(true, outBndStatics.isSelectedForRelease());
        assertEquals(false, outBndStatics.isOutbounded());

    }
}