package com.mckesson.eig.api;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

import junit.framework.TestCase;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

public class TestFileUploadHandler extends TestCase {
    
    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final Log LOG = LogFactory.getLogger(TestFileUploadHandler.class);
    
    /**
     * Member variable initialized.
     */
    private static final String SERVLET_NAME = 
        "http://127.0.0.1:8080/filetransfer/putContentServlet_Test";
    
    private static APIFileUploadHandler _uploadHandler;
    private static String _serverURL = null;
    
    protected void setUp() throws Exception {
        super.setUp();
        _uploadHandler = new APIFileUploadHandler("system", "hecmadmin", SERVLET_NAME);
    }
    
    public void testCreateParameters() {
        _serverURL = _uploadHandler.createParameters();
        assertNotNull(_serverURL);
    }
    
    public void testFileUpload() {
        
        String fileName = createFile();
        try {
            assertNotNull(_uploadHandler.fileUpload(fileName, _serverURL));
        } catch (Exception e) {
            fail(e.getMessage());
        }
        deleteLocalFile(fileName);
    }
    
    public void testFileUploadWithoutFile() {
        
        String fileName = null;
        try {
            assertNotNull(_uploadHandler.fileUpload(fileName, _serverURL));
        } catch (Exception e) {
            assertEquals(null, e.getMessage());
        }
        deleteLocalFile(fileName);
    }
    
    public void testCancelFileUpload() {
        _uploadHandler.cancelFileUpload();
    }
    
    /**
     * Method to create file at local location
     * @return 
     * @return
     */
    private String createFile() {

        String fileName = "text" + System.currentTimeMillis() + ".txt";

        try {

            PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
            pw.println("Test File");
            pw.println("---------");
            pw.print("The Current Date is       : ");
            pw.println(new Date().toString());
            pw.print("The file name is  : ");
            pw.println(fileName);
            pw.close();
        } catch (Exception e) {
            LOG.debug("Error in create file :" + e.getMessage());
        }
        return new File(fileName).getAbsolutePath();
    }

    /**
     * Method to delete the local file.
     * @param filePath
     */
    private void deleteLocalFile(String filePath) {

        try {
            new File(filePath).delete();
        } catch (Exception e) {
            LOG.debug("Error in delete file : " + e.getMessage());
        }
    }
}
