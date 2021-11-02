/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.admin.dao;


import java.sql.Timestamp;

import javax.xml.bind.JAXBException;

import com.mckesson.eig.wsfw.session.CxfWsSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.mckesson.eig.roi.admin.model.OutputServerProperties;
import com.mckesson.eig.roi.admin.model.ROIParameter;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author OFS
 * @date   Feb 24, 2010
 * @since  HPF 15.1 [ROI];
 */
@Transactional
public class OutputIntegrationDAOImpl
extends ROIDAOImpl
implements OutputIntegrationDAO {

    private static final OCLogger LOG = new OCLogger(OutputIntegrationDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String AUTHENTICATED_USER = "authenticated_roi_user";

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.OutputIntegrationDAO#enableOutputService(boolean)
     */
    public void enableOutputService(boolean doEnable) throws JAXBException {

        final String logSM = "enableOutputService(doEnable)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + doEnable);
        }

        ROIParameter param = getROIParameter();


        boolean isExist = param != null;
        param = isExist ? param : new ROIParameter();

        OutputServerProperties osp = isExist ? OutputServerProperties.valueOf(param.getItemValue())
                                             : new OutputServerProperties();

        osp.setEnabled(doEnable);

        param.setItemValue(osp.toXMLString());

        if (isExist) {
            merge(param);
        } else {
            setDefaultDetails(param);
            param.setItemName(ROIConstants.OUTPUT_SERVER_PROPERTY_KEY);
            create(param);
        }

    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.OutputIntegrationDAO#retrieveOutputServerProperties()
     */
    public OutputServerProperties retrieveOutputServerProperties() throws JAXBException {

        final String logSM = "retrieveOutputServerProperties()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        ROIParameter param = getROIParameter();


        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: retrieveOutputServerProperties() " + param);
        }
        if (param == null) {
            return null;
        }

        OutputServerProperties properties = OutputServerProperties.valueOf(param.getItemValue());
        return properties;

    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.OutputIntegrationDAO
     * #saveOutputServerProperties(com.mckesson.eig.roi.admin.model.OutputServerProperties)
     */
    public void saveOutputServerProperties(OutputServerProperties outputServerProperties)
    throws JAXBException {

        final String logSM = "saveOutputServerProperties(outputServerProperties)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + outputServerProperties);
        }

        ROIParameter param = getROIParameter();
        param.setItemValue(outputServerProperties.toXMLString());
        param.setModifiedBy(getUser().getInstanceIdValue());
        param.setModifiedDt(getDate());

        merge(param);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: retrieveOutputServerProperties() " + outputServerProperties);
        }
    }

    private ROIParameter getROIParameter() {

        Object param = getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session s) {

               return  s.createCriteria(ROIParameter.class)
                        .add(Restrictions.eq("itemName", ROIConstants.OUTPUT_SERVER_PROPERTY_KEY))
                        .uniqueResult();
            }
        });

        if (param == null) {
            return null;
        }
        return (ROIParameter) param;
    }

    private void setDefaultDetails(ROIParameter roiParam) {

        Timestamp timestamp = getDate();
        roiParam.setCreatedDt(timestamp);
        roiParam.setModifiedDt(timestamp);
        roiParam.setCreatedBy(getUser().getInstanceIdValue());
        roiParam.setModifiedBy(getUser().getInstanceIdValue());
    }

    protected User getUser() {
        return (User) CxfWsSession.getSessionData(AUTHENTICATED_USER);
    }
}
