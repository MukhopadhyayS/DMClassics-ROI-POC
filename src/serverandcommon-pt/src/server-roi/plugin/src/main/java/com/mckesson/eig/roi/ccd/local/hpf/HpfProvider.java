package com.mckesson.eig.roi.ccd.local.hpf;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdFileType;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdType;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocument;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.utils.SpringUtil;

public class HpfProvider implements CcdProvider {

    private static final String SQL = "Select Top 1 CCD From cabinet.dbo.ROI_TEST_CCD (NOLOCK) "
            + "where mrn = ''{0}'' and encounter = ''{1}'' and facility = ''{2}''";
    private DataSource _ds = null;

    public HpfProvider() {
    }

    public CcdProvider newProvider() {
        return new HpfProvider();
    }
    
    /** Method to get the provider name 
     * 
     * @return String 
     */
    @Override
    public String getProviderName() {
        // TODO Auto-generated method stub
        return "HPF Provider";
    }
    
    /** Method to get the CcdType 
     * 
     * @return String 
     */
    @Override
    public CcdType getCcdType() {
       return  CcdType.CCD;
    }

    /** Method to get the Ccd information 
     * 
     * @return 
     */
    @Override
    public void retrieveCcd(ExternalSourceDocument document) throws ROIException {
        String mrn = document.getMrn();
        String encounter = document.getEncounter();
        String facility = document.getFacility();
        CcdDocument d = document.getCcdDocuments().get(0);
        getCCD(d.getOutputStream(), mrn, encounter, facility);
    }

    public boolean needPeristanceExternalSourceDocument() {
	return false;
    }
    
    /**
     * Method to send the external document
     * 
     * @param name
     *            ,data
     * @return
     */
    @Override
    public void sendExternalDocumentNotice(ExternalSourceDocument document) {
        // TODO Auto-generated method stub
    }
    
    /**
     * Method to send the statistics
     * 
     * @param name
     *            ,data
     * @return
     */
    @Override
    public void sendStatisticsNotice(MUROIOutboundStatistics statistic) {
        // TODO Auto-generated method stub
    }
    /** Method to get the datasource information
     * 
     * @param 
     * @return DataSource
     */
    private synchronized DataSource getDataSource() {
        if (_ds == null) {
            _ds = (DataSource) SpringUtil.getObjectFromSpring("dataSource");
        }
        return _ds;
    }

    /** Method to get the Ccd information 
     * 
     * @param out,mrn,encounter,facility
     * @return 
     */
    private void getCCD(OutputStream out, String mrn, String encounter, String facility)throws ROIException {
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        SqlRowSet rs = template.queryForRowSet(getSQL(mrn, encounter, facility));
        if (rs.next()) {
            try {
                Clob clobObject = (Clob) rs.getObject(1);
                InputStream in = clobObject.getAsciiStream();
                IOUtils.copy(in, out);
            } catch (Exception e) {
                throw new ROIException(e,ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
            }
        }
        else {
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_RETRIEVECCD,"Retrieval Failed");
        }
    }
    
    /** Method to get the SQL information 
     * 
     * @param mrn,encounter,facility
     * @return String 
     */
    private String getSQL(String mrn, String encounter, String facility) {
        StringBuffer buffer = new StringBuffer();
        Object[] args = {mrn, encounter, facility};
        MessageFormat mf = new MessageFormat(SQL);
        buffer.append(mf.format(args));
        return buffer.toString();
    }

    /** Method to get the configuration label information
     * 
     * @param key
     * @return String 
     */
    @Override
    public String getConfigurationLabel(String key) {
        return null;
    }

    /** Method to get the configuration keys information
     *  
     * @return List<String> 
     */
    @Override
    public List<String> getAllConfigurationKeys() {
        // TODO Auto-generated method stub
        List <String> result = new ArrayList<String>();
        return result;
    }

    /** Method to get the number of configuration items information
     * 
     * @return int
     */
    @Override
    public int getNbrConfigurationItems() {
        // TODO Auto-generated method stub
        return 0;
    }

    /** Method to set the Configuration Values information
     * 
     * @param values
     * @return int
     */
    @Override
    public void setConfigurationValues(Map<String, String> values) {
    }

    /** Method to get the Configuration Values information
     * 
     * @param key
     * @return String
     */
    public String getConfigurationValue(String key) {
        return null;
    }
    
    /**
     * Method to Test the Configurtion details
     * 
     * @param providerName
     * @param configValues
     * @return boolean
     */
    @Override
    public boolean testConfiguration(Map<String, String> values) {
        return true;
    }

    @Override
    public Set<CcdFileType> getCcdFileType() {
        Set<CcdFileType> result = new HashSet<CcdFileType>();
        result.add(CcdFileType.XML);
        return result;
    }

    @Override
    public String getCcdConvertStyleSheet() {
	return null;
    }
}
