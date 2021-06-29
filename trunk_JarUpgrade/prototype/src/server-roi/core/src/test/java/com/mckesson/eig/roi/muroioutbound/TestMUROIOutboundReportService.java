package com.mckesson.eig.roi.muroioutbound;

import com.mckesson.eig.roi.muroioutbound.service.MUROIOutboundServiceImpl;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * Test Class to test the MUROIOutboundServiceImpl for reports generation
 *
 */
public class TestMUROIOutboundReportService extends BaseROITestCase {
    protected static final String MUROIOUTBOUND_SERVICE = "com.mckesson.eig.roi.muroioutbound.service.MUROIOutboundServiceImpl";

    private static MUROIOutboundServiceImpl _muROIOutboundServiceImpl;
    public void initializeTestData() throws Exception {
        _muROIOutboundServiceImpl = (MUROIOutboundServiceImpl) getService(MUROIOUTBOUND_SERVICE);

    }

    @Override
    protected String getServiceURL(String serviceMethod) {

        return null;
    }

    /**
     * Method to test the details for Details Screen for Report Generation
     */
    /*public void testRetriveMURequestDetailsForReport() {
        String fromDisplayDate = "2012-01-01";
        String toDisplayDate = "2012-01-30";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fromDate = null;
        Date toDate = null;

        String mudocname = "Clinical Summary";

        String facility = "A,AD";
        String[] array = facility.split(",");

        try {
            fromDate = dateFormat.parse(fromDisplayDate);
            toDate = dateFormat.parse(toDisplayDate);
        } catch (ParseException e) {
            throw new ROIException(ROIClientErrorCodes.MU_REPORT_GENERATION,
                    "XML Parsing file");

        }

        List<Object[]> results = _muROIOutboundServiceImpl
                .retriveMURequestDetailsForReport(fromDate, toDate, array,
                        mudocname);
        assertNotNull(results);

    }*/
    /**
     * Method to test the details for totalsPerFacility view
     */
   /* public void grandTotalsPerFacility() {
        String fromDisplayDate = "2012-01-01";
        String toDisplayDate = "2012-01-30";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fromDate = null;
        Date toDate = null;

        String mudocname = "Clinical Summary";

        String facility = "A,AD";
        String[] array = facility.split(",");

        try {
            fromDate = dateFormat.parse(fromDisplayDate);
            toDate = dateFormat.parse(toDisplayDate);
        } catch (ParseException e) {
            throw new ROIException(ROIClientErrorCodes.MU_REPORT_GENERATION,
                    "XML Parsing file");
        }

        List<Object[]> results = _muROIOutboundServiceImpl
                .grandTotalsPerFacility(fromDate, toDate, array, mudocname);
        assertNotNull(results);

    }*/
    /*public void testDate()
    {
        SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy");
        String dateString="04/21/1945";
        Date formattedDate = null;
        try {
            formattedDate = sdf.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(formattedDate);
    }*/

    public void testDivision()
    {
        int a=2;
        int b=16;
        double c=a/b;
        System.out.println(c);
    }

}
