package com.mckesson.eig.audit.dao.hpf;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;


import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.iws.orm.DaoRegistry;
import com.mckesson.eig.utility.util.SpringUtilities;

public class AuditDao4Hpf {

    private static final BeanFactory BEAN_FACTORY = SpringUtilities.getInstance().getBeanFactory();
    private DaoRegistry _daoregistry;
    private AuditTrailHibernateDao _auditTrailDao;
    private AuditFactory _auditFactory = new AuditFactory();
    private static Map<String, String> _actionKeyMap = null;

    public AuditDao4Hpf() {
        _daoregistry = (DaoRegistry) BEAN_FACTORY.getBean("daoRegistry");
        _auditTrailDao = (AuditTrailHibernateDao) _daoregistry.getDao("AuditTrailHibernateDao");
		DataSource dataSource = (DataSource) BEAN_FACTORY.getBean("defaultDataSourceMPF");
		_auditTrailDao.setDataSource(dataSource);
   }

    public boolean insertEntry(AuditEvent auditEvent) {

        AuditTrail audit = _auditFactory.createAuditTrail(auditEvent);

        if (isAuditable(audit.getActionCode() + "~^~" + audit.getFacility())) {
            _auditTrailDao.save(audit);
        }

        return true;
    }

    /**
     * This method validates whether the given action and facility combination key is
     * track enabled or not
     * @param key - combination value of action code and facility
     * @return validation result
     */
    private boolean isAuditable(String key) {

        if (_actionKeyMap == null) {
            _actionKeyMap = _auditTrailDao.getAuditTrackValues();
        }

        return  "Y".equalsIgnoreCase(_actionKeyMap.get(key));
    }
}
