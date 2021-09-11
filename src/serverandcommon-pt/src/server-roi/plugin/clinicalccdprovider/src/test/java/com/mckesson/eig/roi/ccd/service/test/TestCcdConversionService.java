package com.mckesson.eig.roi.ccd.service.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.ccd.model.CcdConvertValue;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceConfigDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDtoList;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDto;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDtoList;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocumentList;
import com.mckesson.eig.roi.ccd.service.CcdConversionServiceImpl;
import com.mckesson.eig.roi.ccd.service.CcdFileUploadServlet;
import com.mckesson.eig.roi.ccd.service.PdfEventHandler;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class TestCcdConversionService extends BaseROITestCase {

    protected static final String CCD_SERVICE =
                                        "com.mckesson.eig.roi.ccd.service.CcdConversionServiceImpl";
    protected static final String CCD_DAO = "CcdProviderDAO";
    private static final String CCD_FILE_UPLOAD_SERVLET = "FileUploadServlet";
    private static CcdConversionServiceImpl _ccdConversionService;
    private static ServletUnitClient _servletUntClient;
    private static CcdProvider provider = new MockClinicalProvider();
    private static Properties props = new Properties();


    public void initializeTestData() throws Exception {

        _ccdConversionService = (CcdConversionServiceImpl) getService(CCD_SERVICE);

        ServletRunner servletRunner = new ServletRunner();
        servletRunner.registerServlet(CCD_FILE_UPLOAD_SERVLET, CcdFileUploadServlet.class.getName());

        _servletUntClient = servletRunner.newClient();

        String name = "bin/META-INF/services/" + CcdProvider.class.getName();
        File f = AccessFileLoader.getFile(name);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }

        OutputStream outputStream = AccessFileLoader.getFileOutputStream(name);
        outputStream.write(provider.getClass().getName().getBytes());
        outputStream.close();

        insertDataSet("test/resources/reports/reportsDataSet.xml");

        executeSqlQuery("INSERT INTO ASSIGNING_AUTHORITY (MRN_AA, FACILITY_CODE, GPI_AA, HOST_SENDINGSYSTEM, HOST_FACILITYCODE)" +
                " VALUES('AWL', 'AWL', 'AWL', 'DV', 'AWL')");

        createTempCCDDocument(UUID.randomUUID().toString());

        InputStream in = BaseROITestCase.class.getResourceAsStream("ds.properties");
        props.load(in);
        in.close();

    }

    /*
     * Test method for getExternalResources()
     */

    public void testGetExternalResources() {

        try {

            CcdSourceDtoList ccd = _ccdConversionService.getExternalSources();
            List<CcdSourceDto> list = ccd.getCcdSourceDtos();
            assertTrue(list.size() > 0);

        } catch (ROIException e) {
            fail("Retrieving External names should not thrown exception."
                    + e.getErrorCode());
        }

    }

    /*
     * Test method for updateProvider(CcdProviderDto) - Primary Key considered
     * for CcdProviderDto object is "TestCcd Provider"
     */

    public void testUpdateProvider() {

        try {

            CcdSourceDto ccdProviderDto = new CcdSourceDto();
            ccdProviderDto.setProviderName(provider.getProviderName());
            ccdProviderDto.setSourceName("Clinical Provider1" + System.currentTimeMillis());
            ccdProviderDto.setDescription("Clinical PROVIDER TESTING CREATED");
            int sourceId = _ccdConversionService.createSource(ccdProviderDto);

            ccdProviderDto.setSourceName("Clinical Provider" + System.currentTimeMillis());
            ccdProviderDto.setSourceId(sourceId);
            ccdProviderDto.setDescription("Clinical PROVIDER TESTING UPDATED");
            boolean ccdProviderDtoTest = _ccdConversionService.updateSource(ccdProviderDto);

            assertTrue(ccdProviderDtoTest);

        } catch (Exception ex) {
            fail("Update provider should not throw exception");
        }
    }

    /*
     * Test method for updateProvider(CcdProviderDto) - Primary Key considered
     * for CcdProviderDto object is "TestCcd Provider"
     */

    public void testDeleteProvider() {

        try {

            CcdSourceDto ccdProviderDto = new CcdSourceDto();
            ccdProviderDto.setProviderName(provider.getProviderName());
            ccdProviderDto.setSourceName("Clinical Provider1" + System.currentTimeMillis());
            ccdProviderDto.setDescription("Clinical PROVIDER TESTING CREATED");
            int sourceId = _ccdConversionService.createSource(ccdProviderDto);

            boolean ccdProviderDtoTest = _ccdConversionService.deleteSource(sourceId);
            assertTrue(ccdProviderDtoTest);

        } catch (Exception ex) {
            fail("Update provider should not throw exception");
        }



    }

    /*
     * Test method for updateProviderConfig(String, CcdProviderConfigDto)
     *
     */

    public void testUpdateProviderConfig() {

        CcdSourceDto ccdProviderDto = new CcdSourceDto();
        ccdProviderDto.setProviderName(provider.getProviderName());
        ccdProviderDto.setSourceName("Clinical Provider1" + System.currentTimeMillis());
        ccdProviderDto.setDescription("Clinical PROVIDER TESTING UPDATED");

        List<CcdSourceConfigDto> ccdProviderConfigDtoList = new ArrayList<CcdSourceConfigDto>();
        CcdSourceConfigDto ccdProviderConfigDto = new CcdSourceConfigDto();

        ccdProviderConfigDtoList = getCcdSourceConfigDto();
        ccdProviderDto.setCcdSourceConfigDto(ccdProviderConfigDtoList);
        int sourceId = _ccdConversionService.createSource(ccdProviderDto);
        ccdProviderDto.setSourceId(sourceId);

        ccdProviderConfigDto =  ccdProviderConfigDtoList.get(ccdProviderConfigDtoList.size() - 1);
        ccdProviderConfigDto.setConfigValue("2");
        ccdProviderConfigDtoList.add(ccdProviderConfigDto);

        boolean ccdProviderConfigDtoTest = _ccdConversionService.updateSourceConfig(ccdProviderDto);
        assertEquals(true, ccdProviderConfigDtoTest);

    }

    /**
     * @param ccdProviderConfigDtoList
     */
    private List<CcdSourceConfigDto> getCcdSourceConfigDto() {

        List<CcdSourceConfigDto> ccdProviderConfigDtoList = new ArrayList<CcdSourceConfigDto>();

        CcdSourceConfigDto ccdProviderConfigDto;
        ccdProviderConfigDto = new CcdSourceConfigDto();
        ccdProviderConfigDto.setConfigKey("database.server");
        ccdProviderConfigDto.setConfigValue(props.getProperty("DATABASE_SERVER"));
        ccdProviderConfigDtoList.add(ccdProviderConfigDto);

        ccdProviderConfigDto = new CcdSourceConfigDto();
        ccdProviderConfigDto.setConfigKey("database");
        ccdProviderConfigDto.setConfigValue(props.getProperty("DATABASE"));
        ccdProviderConfigDtoList.add(ccdProviderConfigDto);

        ccdProviderConfigDto = new CcdSourceConfigDto();
        ccdProviderConfigDto.setConfigKey("database.username");
        ccdProviderConfigDto.setConfigValue(props.getProperty("DATABASE_USER"));
        ccdProviderConfigDtoList.add(ccdProviderConfigDto);

        ccdProviderConfigDto = new CcdSourceConfigDto();
        ccdProviderConfigDto.setConfigKey("database.password.masked");
        ccdProviderConfigDto.setConfigValue(props.getProperty("DATABASE_PASSWORD"));
        ccdProviderConfigDtoList.add(ccdProviderConfigDto);

        ccdProviderConfigDto = new CcdSourceConfigDto();
        ccdProviderConfigDto.setConfigKey("staffId");
        ccdProviderConfigDto.setConfigValue("1");
        ccdProviderConfigDtoList.add(ccdProviderConfigDto);

        return ccdProviderConfigDtoList;
    }

    /*
     * Test method for retrieveCCD(String, String, String, String)
     *
     */

    public void testRetrieveCCD() {

        RetrieveCCDDto retrieveCCDDto = new RetrieveCCDDto();
        retrieveCCDDto.setRetrieveCCDKey("mrn");
        retrieveCCDDto.setRetrieveCCDValue("0000321338");
        RetrieveCCDDto retrieveCCDDto1 = new RetrieveCCDDto();
        retrieveCCDDto1.setRetrieveCCDKey("encounter");
        retrieveCCDDto1.setRetrieveCCDValue("0003169273");
        RetrieveCCDDto retrieveCCDDto2 = new RetrieveCCDDto();
        retrieveCCDDto2.setRetrieveCCDKey("facility");
        retrieveCCDDto2.setRetrieveCCDValue("AWL");
        RetrieveCCDDto retrieveCCDDto3 = new RetrieveCCDDto();
        retrieveCCDDto3.setRetrieveCCDKey("reqid");
        retrieveCCDDto3.setRetrieveCCDValue("1001");

        List<RetrieveCCDDto> retrieveList = new ArrayList<RetrieveCCDDto>();

        retrieveList.add(0, retrieveCCDDto);
        retrieveList.add(1, retrieveCCDDto1);
        retrieveList.add(2, retrieveCCDDto2);
        retrieveList.add(2, retrieveCCDDto3);

        RetrieveCCDDtoList retrieveCCDDtoList = new RetrieveCCDDtoList();

        retrieveCCDDtoList.setRetrieveParameters(retrieveList);

        CcdDocumentList docs = _ccdConversionService.retrieveCCD(retrieveCCDDtoList);
        assertNotNull(docs);

    }

    /*
     * Test method for testConfiguration(CcdProviderDto)
     *
     */

    public void testTestConfiguration() {
        CcdSourceDto ccdProviderDto = new CcdSourceDto();
        ccdProviderDto.setProviderName(provider.getProviderName());
        ccdProviderDto.setSourceName("Clinical Provider1");
        ccdProviderDto.setDescription("Clinical PROVIDER TESTING UPDATED");

        CcdSourceConfigDto ccdconfig = new CcdSourceConfigDto();

        ccdconfig.setConfigKey("TEST CONFIG KEY");
        ccdconfig.setConfigValue("TEST CONFIG VALUE");

        List<CcdSourceConfigDto> ccdProviderConfigDtoList = new ArrayList<CcdSourceConfigDto>();

        ccdProviderConfigDtoList.add(ccdconfig);
        ccdProviderDto.setCcdSourceConfigDto(ccdProviderConfigDtoList);

        boolean testConfig = _ccdConversionService
                .testConfiguration(ccdProviderDto);
        assertEquals(true, testConfig);

    }

    /*
     *  Test method for getExternalName based on the facility
     *
     */

    public void testGetExternalSourceNameForFacility() {
        String extName = _ccdConversionService
                .getExternalSourceNameForFacility("AWL");
        boolean flag = extName != null;
        assertEquals(true, flag);

    }

    public void testCcdConvertValue() {

        CcdConvertValue ccdValue = new CcdConvertValue();
        ccdValue.setFileName("A");
        ccdValue.setPageNumber(1);
        ccdValue.setType("A");

        assertEquals("A", ccdValue.getFileName());
        assertEquals(1, ccdValue.getPageNumber());
        assertEquals("A", ccdValue.getType());
    }

    public void testgetAvailableProviders() {

        try {

            _ccdConversionService.getAvailableProviders();
            _ccdConversionService.getPageCount(AccessFileLoader.getFile(getClass(). getResource("ccd.pdf").toURI()));

        } catch (Exception ex) {
            fail("CCD Conversion should not throw exception");
        }
    }

    /**
     * @param uuid
     * @throws FileNotFoundException
     * @throws IOException
     */
    private String createTempCCDDocument(String uuid) {

        String s = "";
        try {

            File f = AccessFileLoader.getFile("bin/com/mckesson/eig/roi/ccd/service/test/ccd.xml");
            FileReader r = AccessFileLoader.getFileReader(f.getPath());
            FileWriter w = AccessFileLoader.getFileWriter(_ccdConversionService.getCachFile(uuid));
            char[] cbuf = new char[256];
            while(r.read(cbuf) > 0) {
                w.write(cbuf);
                s += new String(cbuf);
            }
            w.close();
            r.close();

        } catch (Exception e) { }
        return s;
    }

    public void testCCDFileUpload() {

        try {

            String strURLaddress = constructURL("CCDFileUpload",
                                     0,
                                     "ccd.xml",
                                     true,
                                     true);

            WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                      TestCcdConversionService.class.getResourceAsStream("ccd.xml"),
                                                      "application/xml");

            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);

        } catch (Exception ex) {
            fail("Should not throw exceprion");
        }

    }

    public void testPdfEventHandler() {


        try {

            FopFactory _fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent _foUserAgent = _fopFactory.newFOUserAgent();
            PdfEventHandler handler = new PdfEventHandler(_foUserAgent, AccessFileLoader.getFileOutputStream("ccd"));

            String s = new String("<p>testhtml</p><span id='test'><div>test</div></span>");

            char[] data = new char[s.length()];
            s.getChars(0, s.length(), data, 0);

            handler.characters(data, 0, data.length);
            handler.character(new org.apache.fop.fo.flow.Character(null));

        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    public void testAuditEvent() {

        AuditEvent ae = new AuditEvent();
        ae.setActionCode("1");
        ae.setFacility("Test");
        ae.setComment("Test");
        ae.setUserId(getUser().getInstanceId().longValue());
        _ccdConversionService.audit(ae);
    }

    public void testCCDConvertWithInvalidData() {

        String uuid = UUID.randomUUID().toString();
        try {

            _ccdConversionService.ccdConvert(uuid);
            fail("Should throw exception");

        } catch (Exception ex) {

            if (!FileNotFoundException.class.equals(ex.getClass())) {
                fail("Should throw FileNotFoundException");
            }
        }

        try {

            createTempCCDDocument(uuid);
            _ccdConversionService.ccdConvert(uuid);
            fail("Should throw exception");

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.CCD_CCR_INVALID_FILE_FORMAT);
        } catch (Exception ex) {
            fail("Should not throw Unknown Exception");
        }
    }

    public void testCCDConvertStyleWithInvalidData() {

        String uuid = UUID.randomUUID().toString();
        try {

            _ccdConversionService.ccdConvert("ccd.xslt.file", uuid);
            fail("Should throw exception");

        } catch (Exception ex) {

            if (!FileNotFoundException.class.equals(ex.getClass())) {
                fail("Should throw FileNotFoundException");
            }
        }

        try {

            createTempCCDDocument(uuid);
            _ccdConversionService.ccdConvert("ccd.xslt.file", uuid);
            fail("Should throw exception");

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.CCD_CCR_INVALID_FILE_FORMAT);
        } catch (Exception ex) {
            fail("Should not throw Unknown Exception");
        }
    }

    public void testPageCountWithInvalidFile() {

        try {
            _ccdConversionService.getPageCount(null);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.CCD_CCR_INVALID_PAGE_COUNT);
        }
    }

    public void testUpdateSourceWithInvalidData() {

        try {
            _ccdConversionService.updateSource(null);
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.CCD_CCR_UPDATE_PROVIDER);
        }
    }

    public void testUpdateSourceConfigWithInvalidData() {

        try {
            _ccdConversionService.updateSourceConfig(null);
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.CCD_CCR_UPDATE_PROVIDERCONFIG);
        }
    }

    public void testRetrieveCCDWithInvalidData() {

        try {
            _ccdConversionService.retrieveCCD(null);
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
        }
    }

    public void testTestConfigurationWithInvalidData() {

        try {
            _ccdConversionService.testConfiguration(null);
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.CCD_CCR_TESTCONFIGURATION);
        }
    }

    public void testgetExternalSourceNameForFacilityWithInvalidData() {

        try {
            _ccdConversionService.getExternalSourceNameForFacility("TestFacility");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.CCD_CCR_GETEXTERNALSOURCENAMEFORFACILITY);
        }
    }

    public void testCreateSourceWithInvalidConfiguartion() {

        try {

            CcdSourceDto ccdProviderDto = new CcdSourceDto();
            ccdProviderDto.setProviderName(provider.getProviderName());
            ccdProviderDto.setSourceName("Clinical Provider1" + System.currentTimeMillis());
            ccdProviderDto.setDescription("Clinical PROVIDER TESTING UPDATED");

            List<CcdSourceConfigDto> ccdProviderConfigDtoList = new ArrayList<CcdSourceConfigDto>();
            ccdProviderConfigDtoList.add(null);
            ccdProviderDto.setCcdSourceConfigDto(ccdProviderConfigDtoList);
            _ccdConversionService.createSource(ccdProviderDto);
            fail("Should throw Exception");

        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.CCD_CCR_UPDATE_PROVIDERCONFIG);
        }
    }

    private String constructURL(String ownerType, long docId, String fileName,
                                boolean isChunk, boolean isFinal) {

        String strURLaddress = "http://localhost:8080/" + CCD_FILE_UPLOAD_SERVLET;
        strURLaddress += "?" + BaseFileTransferData.PARAMETER_USER + "="
                + DEFAULT_TEST_USER;

        strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD + "="
                + DEFAULT_TEST_PWD;

        strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "="
                + new Timestamp(System.currentTimeMillis());

        strURLaddress += "&" + "OWNER_TYPE=" + ownerType;
        strURLaddress += "&" + "DOC_ID" + "=" + Long.toString(docId);
        strURLaddress += "&" + "FILE_NAME=" + fileName;
        strURLaddress += "&" + "CHUNKENABLED=" + Boolean.toString(isChunk);
        strURLaddress += "&" + "FINALCHUNK=" + Boolean.toString(isFinal);
        strURLaddress += "&" + "USER_ID=" + Long.toString(0);
        return strURLaddress;

    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return null;
    }

}
