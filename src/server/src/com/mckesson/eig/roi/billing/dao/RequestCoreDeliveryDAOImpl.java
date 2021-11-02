package com.mckesson.eig.roi.billing.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.PlainSqlBatchProcessor;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.billing.model.ChargeHistory;
import com.mckesson.eig.roi.billing.model.ChargeHistoryList;
import com.mckesson.eig.roi.billing.model.CoverLetterCore;
import com.mckesson.eig.roi.billing.model.Invoice;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties;
import com.mckesson.eig.roi.billing.model.InvoiceHistory;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.billing.model.PostPaymentReportDetails;
import com.mckesson.eig.roi.billing.model.PrebillReportDetails;
import com.mckesson.eig.roi.billing.model.ReleasePages;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesDocument;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesFee;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesShipping;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryInvoicePatients;
import com.mckesson.eig.roi.billing.model.SalesTaxReason;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RequestCoreDeliveryDAOImpl
extends ROIDAOImpl
implements RequestCoreDeliveryDAO {

    private static final OCLogger LOG = new OCLogger(RequestCoreDeliveryDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /* (non-Javadoc)
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#createRequestCoreDelivery(long)
     */
    @Override
    public long createRequestCoreDelivery(long requestCoreId) {
        final String logSM = "createRequestCoreDelivery(requestCoreId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("createRequestCoreDelivery").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("requestCoreDeliveryId", LongType.INSTANCE);
            query.setParameter("requestCoreId", requestCoreId, LongType.INSTANCE);
            Long requestCoreDeliveryId = (Long) query.uniqueResult();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return requestCoreDeliveryId;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#deleteRequestCoreDelivery(long)
     */
    @Override
    public void deleteRequestCoreDelivery(long requestCoreDeliveryId) {
        final String logSM = "deleteRequestCoreDelivery(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteRequestCoreDelivery")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO
     * #retrieveRequestIdForRequestCoreDelivery(long)
     */
    @Override
    public long retrieveRequestIdForRequestCoreDelivery(long requestCoreDeliveryId) {

        final String logSM = "retrieveRequestIdForRequestCoreDelivery(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestIdForRequestCoreDelivery")
                    .getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("requestId", LongType.INSTANCE);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);

            @SuppressWarnings("unchecked")
            List<Long> requestIds = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return (requestIds.size() > 0) ? requestIds.get(0) : 0;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#createRequestCoreDeliveryToPages(long, java.lang.Long)
     */
    @Override
    public void createRequestCoreDeliveryToPages(long requestCoreDeliveryId, ReleasePages roiPages) {

        final String logSM = "createRequestCoreDeliveryToPages(requestCoreDeliveryId, roiPages)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_Pages")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.setParameter("roiPagesId", roiPages.getPageSeq(), LongType.INSTANCE);
            query.setParameter("selfPayEncounter", roiPages.isSelfPayEncounter(), BooleanType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#deleteRequestCoreDeliveryToPages(long)
     */
    @Override
    public void deleteRequestCoreDeliveryToPages(long requestCoreDeliveryId) {
        final String logSM = "deleteRequestCoreDeliveryToPages(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {

            Session session = getSession();

            String queryString = session.getNamedQuery("retrievePagesAndReleasedCount")
                    .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("requestCoreDeliveryId", requestCoreDeliveryId);
            List<Object[]> list = sqlQuery.list();

            if (CollectionUtilities.hasContent(list)) {

                queryString = session.getNamedQuery("updatePagesReleaseStatus")
                        .getQueryString();

                for (Object[] obj : list) {

                    sqlQuery = session.createSQLQuery(queryString);
                    // if the relesed count is greater than 1 then it is already released.
                    boolean isReleased = (null == obj[1]) ? false : (((Integer) obj[1]) > 1);

                    sqlQuery.setParameter("isReleased",isReleased);
                    sqlQuery.setParameter("pagesSeq", obj[0]);

                }
            }

            String queryStr = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_Pages")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryStr);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#createRequestCoreDeliveryToInvoice(long, long)
     */
    @Override
    public void createRequestCoreDeliveryToInvoice(long requestCoreDeliveryId, long requestCoreDeliveryChagesId) {
        final String logSM = "createRequestCoreDeliveryToInvoice(requestCoreDeliveryId, requestCoreDeliveryChagesId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChagesId, LongType.INSTANCE);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#deleteRequestCoreDeliveryToInvoice(long)
     */
    @Override
    public void deleteRequestCoreDeliveryToInvoice(long requestCoreDeliveryId) {
        final String logSM = "deleteRequestCoreDeliveryToInvoice(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#retrieveNonReleasedInvoices(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> retrieveNonReleasedInvoices(long requestCoreId) {
        final String logSM = "retrieveNonReleasedInvoices(requestCoreId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveNonReleasedInvoices").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("invoiceId", LongType.INSTANCE);
            query.setParameter("requestCoreId", requestCoreId, LongType.INSTANCE);
            
            List<Long> nonReleasedInvoices = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return nonReleasedInvoices;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#retrieveNonReleasedInvoices(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean isRequestContainsNonReleasedInvoices(long requestCoreId) {
        final String logSM = "isRequestContainsNonReleasedInvoices(requestCoreId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("isRequestContainsNonReleasedInvoices")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("isInvoiced", BooleanType.INSTANCE);
            query.setParameter("requestCoreId", requestCoreId, LongType.INSTANCE);

            Boolean nonReleasedInvoices = (Boolean) query.uniqueResult();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return (nonReleasedInvoices == null) ? false: nonReleasedInvoices.booleanValue();

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#retrieveInvoicesForRelease(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> retrieveInvoicesForRelease(long releaseId) {
        final String logSM = "retrieveInvoicesForRelease(releseId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + releaseId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveInvoicesForRelease").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("invoiceId", LongType.INSTANCE);
            query.setParameter("releaseId", releaseId, LongType.INSTANCE);

            List<Long> nonReleasedInvoices = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return nonReleasedInvoices;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#updateInvoiceReleased(long, boolean)
     */
    @Override
    public void updateInvoiceReleased(long invoiceId, boolean isReleased) {
        final String logSM = "updateInvoiceReleased(invoiceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceId);
        }
        try {
            Session session = getSession();
            String queryString = "";

            if (isReleased) {
                queryString = session.getNamedQuery("updateInvoiceReleased")
                        .getQueryString();
            }  else {
                queryString = session.getNamedQuery("updateInvoiceReleaseCancelled")
                        .getQueryString();
            }
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("invoiceId", invoiceId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will create RequestCoreDeliveryCharges
     * @param invoice
     * @return long requestCoreDeliveryChargesId
     */
    @Override
    public long createRequestCoreDeliveryCharges(RequestCoreDeliveryCharges invoice) {
        final String logSM = "createRequestCoreDeliveryCharges(requestCoreDeliveryCharges)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoice);
        }
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryCharges");

            query.setParameter("requestCoreId", invoice.getRequestCoreId(), LongType.INSTANCE);
            query.setParameter("createdDate", invoice.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", invoice.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDate", invoice.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", invoice.getModifiedBy(), LongType.INSTANCE);
            query.setParameter("isReleased", invoice.getIsReleased(), BooleanType.INSTANCE);
            query.setParameter("previouslyReleasedCost", invoice.getPreviouslyReleasedCost(),
                    DoubleType.INSTANCE);
            query.setParameter("totalPagesReleased", invoice.getTotalPagesReleased() ,
                    IntegerType.INSTANCE);
            query.setParameter("totalPages", invoice.getTotalPages() , IntegerType.INSTANCE);
            query.setParameter("salesTaxAmount", invoice.getSalesTaxAmount() , DoubleType.INSTANCE);
            query.setParameter("salesTaxPercentage", invoice.getSalesTaxPercentage(),
                    DoubleType.INSTANCE);
            query.setParameter("letterTemplateName", invoice.getLetterTemplateName(),
                    StringType.INSTANCE);
            query.setParameter("requestStatus", invoice.getRequestStatus(), StringType.INSTANCE);
            query.setParameter("requestDate", invoice.getRequestDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("letterTemplateFileId", invoice.getLetterTemplateFileId(),
                    LongType.INSTANCE);
            query.setParameter("type", invoice.getType(), StringType.INSTANCE);
            query.setParameter("invoicePrebillDate", invoice.getInvoicePrebillDate(),
                    StandardBasicTypes.TIMESTAMP);
            query.setParameter("invoiceDueDate", invoice.getInvoiceDueDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("resendDate", invoice.getResendDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("outputMethod", invoice.getOutputMethod(), StringType.INSTANCE);
            query.setParameter("queuePassword", invoice.getQueuePassword(), StringType.INSTANCE);
            query.setParameter("overwriteDueDate", invoice.getOverwriteDueDate(),
                    BooleanType.INSTANCE);
            query.setParameter("invoiceSalesTax", invoice.getInvoiceSalesTax(), DoubleType.INSTANCE);
            query.setParameter("baseCharge", invoice.getBaseCharge(), DoubleType.INSTANCE);
            query.setParameter("balanceDue", invoice.getBalanceDue(), DoubleType.INSTANCE);
            query.setParameter("invoiceBillingLocCode", invoice.getInvoiceBillingLocCode(),
                    StringType.INSTANCE);
            query.setParameter("invoiceBillinglocName", invoice.getInvoiceBillinglocName(),
                    StringType.INSTANCE);
            query.setParameter("invoiceBalanceDue", invoice.getInvoiceBalanceDue(),
                    DoubleType.INSTANCE);
            query.setParameter("releaseDate", invoice.getReleaseDate() , StandardBasicTypes.TIMESTAMP);
            query.setParameter("billingType", invoice.getBillingType() , StringType.INSTANCE);
            query.setParameter("unbillable", invoice.isUnbillable() , BooleanType.INSTANCE);
            query.setParameter("unbillableAmount", invoice.getUnbillableAmount(),
                    DoubleType.INSTANCE);
            query.setParameter("applySalesTax", invoice.getApplySalesTax() , BooleanType.INSTANCE);
            query.setParameter("notes", invoice.getNotes() , StringType.INSTANCE);
            query.setParameter("invoiceDueDays", invoice.getInvoiceDueDays() , LongType.INSTANCE);
            query.setParameter("prebillStatus", invoice.getPrebillStatus() , StringType.INSTANCE);
            List<BigDecimal> requestCoreDeliveryChargesList = query.list();
            long requestCoreChargesId = 0;
            if (CollectionUtilities.hasContent(requestCoreDeliveryChargesList)) {
                requestCoreChargesId = requestCoreDeliveryChargesList.get(0).longValue();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestCoreDeliveryChargesId " + requestCoreChargesId);
            }

            return requestCoreChargesId;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * This method will create RequestCoreDeliveryChargesDocument
     * @param requestCoreDeliveryChargesDocument
     * @return long requestCoreDeliveryChargesDocumentId
     */
    @Override
    public void createRequestCoreDeliveryChargesDocument(RequestCoreDeliveryChargesDocument requestCoreDeliveryChargesDocument) {
        final String logSM = "createRequestCoreDeliveryChargesDocument(requestCoreDeliveryChargesDocument)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesDocument);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryChargesDocument");

            query.setParameter("requestCoreDeliveryChargesId",requestCoreDeliveryChargesDocument.getRequestCoreDeliveryChargesId(), LongType.INSTANCE);
            query.setParameter("amount", requestCoreDeliveryChargesDocument.getAmount(), DoubleType.INSTANCE);
            query.setParameter("copies", requestCoreDeliveryChargesDocument.getCopies(), IntegerType.INSTANCE);
            query.setParameter("billingtierName", requestCoreDeliveryChargesDocument.getBillingTierName(), StringType.INSTANCE);
            query.setParameter("totalPages", requestCoreDeliveryChargesDocument.getTotalPages(), IntegerType.INSTANCE);
            query.setParameter("pages", requestCoreDeliveryChargesDocument.getPages(), IntegerType.INSTANCE);
            query.setParameter("billingtierId", requestCoreDeliveryChargesDocument.getBillingtierId(), StringType.INSTANCE);
            query.setParameter("releaseCount", requestCoreDeliveryChargesDocument.getReleaseCount(), IntegerType.INSTANCE);
            query.setParameter("isElectronic", requestCoreDeliveryChargesDocument.getIsElectronic(), BooleanType.INSTANCE);
            query.setParameter("removeBasecharge", requestCoreDeliveryChargesDocument.getRemoveBaseCharge(), BooleanType.INSTANCE);
            query.setParameter("hasSalesTax", requestCoreDeliveryChargesDocument.getHasSalesTax(), BooleanType.INSTANCE);
            query.setParameter("salesTaxAmount", requestCoreDeliveryChargesDocument.getSalesTaxAmount(), DoubleType.INSTANCE);
            query.setParameter("createdDate", requestCoreDeliveryChargesDocument.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesDocument.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesDocument.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesDocument.getModifiedBy(), LongType.INSTANCE);

            query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will create RequestCoreDeliveryChargesFee
     * @param requestCoreDeliveryChargesFee
     * @return long requestCoreDeliveryChargesFeeId
     */
    @Override
    public void createRequestCoreDeliveryChargesFee(RequestCoreDeliveryChargesFee requestCoreDeliveryChargesFee) {
        final String logSM = "createRequestCoreDeliveryChargesFee(requestCoreDeliveryChargesFee)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesFee);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryChargesFee");

            query.setParameter("requestCoreDeliveryChargesId",requestCoreDeliveryChargesFee.getRequestCoreDeliveryChargesId(), LongType.INSTANCE);
            query.setParameter("amount", requestCoreDeliveryChargesFee.getAmount(), DoubleType.INSTANCE);
            query.setParameter("isCustomFee", requestCoreDeliveryChargesFee.getIsCustomFee(), BooleanType.INSTANCE);
            query.setParameter("feeType", requestCoreDeliveryChargesFee.getFeeType(), StringType.INSTANCE);
            query.setParameter("hasSalesTax", requestCoreDeliveryChargesFee.getHasSalesTax(), BooleanType.INSTANCE);
            query.setParameter("salesTaxAmount", requestCoreDeliveryChargesFee.getSalesTaxAmount(), DoubleType.INSTANCE);
            query.setParameter("createdDate", requestCoreDeliveryChargesFee.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesFee.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesFee.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesFee.getModifiedBy(), LongType.INSTANCE);

            query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End" );
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }


    /**
     * This method will create RequestCoreDeliveryChargesShipping
     * @param requestCoreDeliveryChargesShipping
     * @return long requestCoreDeliveryChargesShippingId
     */
    @Override
    public void createRequestCoreDeliveryChargesShipping(RequestCoreDeliveryChargesShipping requestCoreDeliveryChargesShipping) {
        final String logSM = "createRequestCoreDeliveryChargesShipping(requestCoreDeliveryChargesShipping)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesShipping);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryChargesShipping");

            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesShipping.getRequestCoreDeliveryChargesId(), LongType.INSTANCE);
            query.setParameter("shippingCharge", requestCoreDeliveryChargesShipping.getShippingCharge(), DoubleType.INSTANCE);
            query.setParameter("postalCode", requestCoreDeliveryChargesShipping.getPostalCode(), StringType.INSTANCE);
            query.setParameter("addressType", requestCoreDeliveryChargesShipping.getAddressType(), StringType.INSTANCE);
            query.setParameter("state", requestCoreDeliveryChargesShipping.getState(), StringType.INSTANCE);
            query.setParameter("shippingUrl",requestCoreDeliveryChargesShipping.getShippingUrl(), StringType.INSTANCE);
            query.setParameter("address1", requestCoreDeliveryChargesShipping.getAddress1(), StringType.INSTANCE);
            query.setParameter("address2", requestCoreDeliveryChargesShipping.getAddress2(), StringType.INSTANCE);
            query.setParameter("address3", requestCoreDeliveryChargesShipping.getAddress3(), StringType.INSTANCE);
            query.setParameter("shippingWeight", requestCoreDeliveryChargesShipping.getShippingWeight(),DoubleType.INSTANCE);
            query.setParameter("trackingNumber", requestCoreDeliveryChargesShipping.getTrackingNumber(), StringType.INSTANCE);
            query.setParameter("city", requestCoreDeliveryChargesShipping.getCity(), StringType.INSTANCE);
            query.setParameter("countryCode", requestCoreDeliveryChargesShipping.getCountryCode(), StringType.INSTANCE);
            query.setParameter("countryName", requestCoreDeliveryChargesShipping.getCountryName(), StringType.INSTANCE);
            query.setParameter("willReleaseShipped", requestCoreDeliveryChargesShipping.isWillReleaseShipped(), BooleanType.INSTANCE);
            query.setParameter("willInvoiceShipped", requestCoreDeliveryChargesShipping.isWillInvoiceShipped(), BooleanType.INSTANCE);
            query.setParameter("shippingMethod", requestCoreDeliveryChargesShipping.getShippingMethod(), StringType.INSTANCE);
            query.setParameter("outputMethod", requestCoreDeliveryChargesShipping.getOutputMethod(), StringType.INSTANCE);
            query.setParameter("shippingMethodId", requestCoreDeliveryChargesShipping.getShippingMethodId(), LongType.INSTANCE);
            query.setParameter("nonPrintableAttachmentQueue", requestCoreDeliveryChargesShipping.getNonPrintableAttachmentQueue(), StringType.INSTANCE);
            query.setParameter("createdDate", requestCoreDeliveryChargesShipping.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesShipping.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesShipping.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesShipping.getModifiedBy(), LongType.INSTANCE);

            query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }


    /**
     * This method will retrieve RequestCoreDeliveryCharges
     * @param long requestCoreDeliveryId
     * @return RequestCoreDeliveryCharges retrieveRequestCoreDeliveryCharges
     */
    @Override
    public List<RequestCoreDeliveryCharges> retrieveRequestCoreDeliveryCharges(long requestCoreId) {
        final String logSM = "retrieveRequestCoreDeliveryCharges(requestCoreDeliveryId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryCharges")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreId", requestCoreId, LongType.INSTANCE);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("isReleased", BooleanType.INSTANCE);
            query.addScalar("previouslyReleasedCost", DoubleType.INSTANCE);
            query.addScalar("releaseCost", DoubleType.INSTANCE);
            query.addScalar("totalRequestCost", DoubleType.INSTANCE);
            query.addScalar("totalPagesReleased", IntegerType.INSTANCE);
            query.addScalar("totalPages", IntegerType.INSTANCE);
            query.addScalar("salesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("salesTaxPercentage", DoubleType.INSTANCE);
            query.addScalar("letterTemplateName", StringType.INSTANCE);
            query.addScalar("requestStatus", StringType.INSTANCE);
            query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("letterTemplateFileId", LongType.INSTANCE);
            query.addScalar("type", StringType.INSTANCE);
            query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("outputMethod", StringType.INSTANCE);
            query.addScalar("queuePassword", StringType.INSTANCE);
            query.addScalar("overwriteDueDate", BooleanType.INSTANCE);
            query.addScalar("invoiceSalesTax", DoubleType.INSTANCE);
            query.addScalar("baseCharge", DoubleType.INSTANCE);
            query.addScalar("balanceDue", DoubleType.INSTANCE);
            query.addScalar("invoiceBillingLocCode", StringType.INSTANCE);
            query.addScalar("invoiceBillinglocName", StringType.INSTANCE);
            query.addScalar("invoiceBalanceDue", DoubleType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("creditAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("debitAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("applySalesTax", BooleanType.INSTANCE);
            query.addScalar("notes", StringType.INSTANCE);
            query.addScalar("unbillable", BooleanType.INSTANCE);
            query.addScalar("unbillableAmount", DoubleType.INSTANCE);
            query.addScalar("invoiceDueDays", LongType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.addScalar("prebillStatus", StringType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryCharges.class));
            List<RequestCoreDeliveryCharges> requestCoreDeliveryChargesList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return requestCoreDeliveryChargesList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * This method will retrieve RequestCoreDeliveryChargesShipping
     * @param long requestCoreDeliveryChargesId
     * @return RequestCoreDeliveryChargesShipping requestCoreDeliveryChargesShipping
     */
    @Override
    public RequestCoreDeliveryChargesShipping retrieveRequestCoreDeliveryChargesShipping(long requestCoreDeliveryChargesId) {
        final String logSM = "retrieveRequestCoreDeliveryChargesShipping(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesShipping").getQueryString();;
            NativeQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesId", ids);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestCoreDeliveryChargesId" ,LongType.INSTANCE);
            query.addScalar("shippingCharge", DoubleType.INSTANCE);
            query.addScalar("postalCode", StringType.INSTANCE);
            query.addScalar("addressType", StringType.INSTANCE);
            query.addScalar("state", StringType.INSTANCE);
            query.addScalar("shippingUrl", StringType.INSTANCE);
            query.addScalar("address1", StringType.INSTANCE);
            query.addScalar("address2", StringType.INSTANCE);
            query.addScalar("address3", StringType.INSTANCE);
            query.addScalar("shippingWeight", DoubleType.INSTANCE);
            query.addScalar("trackingNumber", StringType.INSTANCE);
            query.addScalar("city", StringType.INSTANCE);
            query.addScalar("countryCode", StringType.INSTANCE);
            query.addScalar("countryName", StringType.INSTANCE);
            query.addScalar("willReleaseShipped", BooleanType.INSTANCE);
            query.addScalar("willInvoiceShipped", BooleanType.INSTANCE);
            query.addScalar("shippingMethod", StringType.INSTANCE);
            query.addScalar("outputMethod", StringType.INSTANCE);
            query.addScalar("shippingMethodId", LongType.INSTANCE);
            query.addScalar("nonPrintableAttachmentQueue", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesShipping.class));
            List requestCoreDeliveryChargesShippingList = query.list();
            RequestCoreDeliveryChargesShipping requestCoreDeliveryChargesShipping = (RequestCoreDeliveryChargesShipping)requestCoreDeliveryChargesShippingList.get(0);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestCoreDeliveryChargesShippingId " + requestCoreDeliveryChargesShipping.getId());
            }

            return requestCoreDeliveryChargesShipping;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will retrieve RequestCoreDeliveryChargesAdjustmentPayment
     * @param List<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesId
     * @return RequestCoreDeliveryChargesAdjustmentPayment requestCoreDeliveryChargesAdjustmentPayment
     */
    @Override
    public List<RequestCoreDeliveryChargesAdjustmentPayment> retrieveRequestCoreDeliveryChargesAdjustmentPayment(long requestCoreDeliveryChargesId) {
        final String logSM = "retrieveRequestCoreDeliveryChargesAdjustmentPayment(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }

        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesAdjustmentPayment").getQueryString();;
            NativeQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesIds", ids);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestorId",LongType.INSTANCE);
            query.addScalar("requestCoreDeliveryChargesId",LongType.INSTANCE);
            query.addScalar("invoiceAppliedAmount", DoubleType.INSTANCE);
            query.addScalar("baseAmount", DoubleType.INSTANCE);
            query.addScalar("totalUnappliedAmount", DoubleType.INSTANCE);
            query.addScalar("paymentMode", StringType.INSTANCE);
            query.addScalar("description", StringType.INSTANCE);
            query.addScalar("paymentDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("transactionType", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesAdjustmentPayment.class));
            List<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesAdjustmentPaymentList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return requestCoreDeliveryChargesAdjustmentPaymentList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will retrieve RequestCoreDeliveryChargesAdjustmentPayment for prebill payment
     * @param List<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesId
     * @return RequestCoreDeliveryChargesAdjustmentPayment requestCoreDeliveryChargesAdjustmentPayment
     */
    @Override
    public List<RequestCoreDeliveryChargesAdjustmentPayment> retrieveRequestCoreDeliveryChargesPrebillAdjustmentPayment(long requestCoreDeliveryChargesId) {
        final String logSM = "retrieveRequestCoreDeliveryChargesPrebillAdjustmentPayment(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }

        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesPrebillAdjustmentPayment").getQueryString();;
            NativeQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesIds", ids);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestorId",LongType.INSTANCE);
            query.addScalar("requestCoreDeliveryChargesId",LongType.INSTANCE);
            query.addScalar("invoiceAppliedAmount", DoubleType.INSTANCE);
            query.addScalar("baseAmount", DoubleType.INSTANCE);
            query.addScalar("totalUnappliedAmount", DoubleType.INSTANCE);
            query.addScalar("paymentMode", StringType.INSTANCE);
            query.addScalar("description", StringType.INSTANCE);
            query.addScalar("paymentDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("transactionType", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesAdjustmentPayment.class));
            @SuppressWarnings("unchecked")
            List<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesAdjustmentPaymentList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return requestCoreDeliveryChargesAdjustmentPaymentList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }



    @Override
    public void deleteRequestCoreDeliveryCharges(long requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreDeliveryCharges(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteRequestCoreDeliveryCharges")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void deleteRequestCoreDeliveryChargesFee(long requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreDeliveryChargesFee(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId );
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteRequestCoreDeliveryChargesFee")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#deleteRequestCoreDeliveryChargesShipping(long)
     */
    @Override
    public void deleteRequestCoreDeliveryChargesShipping(long requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreDeliveryChargesShipping(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteRequestCoreDeliveryChargesShipping")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void deleteRequestCoreDeliveryChargesDocument(long requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreDeliveryChargesDocument(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId );
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteRequestCoreDeliveryChargesDocument")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }

    }

    @Override
    public void deleteRequestCoreDeliveryChargesInvoicePatients(long requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreDeliveryChargesInvoicePatients(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId );
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteRequestCoreDeliveryChargesInvoicePatients")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }

    }

    /**
     * This method will save the patient details as part of invoice creation
     * @param requestCoreDeliveryInvoicePatients
     */
    @Override
    public void createInvoicePatients(RequestCoreDeliveryInvoicePatients requestCoreDeliveryInvoicePatients) {
        final String logSM = "createInvoicePatients(requestCoreDeliveryInvoicePatients)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryInvoicePatients);
        }
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("createInvoicePatients");
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryInvoicePatients.getRequestCoreDeliveryChargesId(), LongType.INSTANCE);
            query.setParameter("requestHpfPatientsId", requestCoreDeliveryInvoicePatients.getRequestHpfPatientsId(), LongType.INSTANCE);
            query.setParameter("requestNonHpfPatientsId", requestCoreDeliveryInvoicePatients.getRequestNonHpfPatientsId(), LongType.INSTANCE);
            query.setParameter("createdDt", requestCoreDeliveryInvoicePatients.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryInvoicePatients.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDt", requestCoreDeliveryInvoicePatients.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryInvoicePatients.getModifiedBy(), LongType.INSTANCE);
            query.list();
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void createRequestCoreDeliveryToSupplementalAttachments(long requestCoreDeliveryId, Long supplementalAttachmentSeq) {
        final String logSM = "createRequestCoreDeliveryToSupplementalAttachments(requestCoreDeliveryId, supplementalAttachmentSeq)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementalAttachmentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.setParameter("roiSupplementalAttachmentId", supplementalAttachmentSeq, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void deleteRequestCoreDeliveryToSupplementalAttachments(long requestCoreDeliveryId) {
        final String logSM = "deleteRequestCoreDeliveryToPages(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveSupplementalAttachmentAndReleasedCount")
                    .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("requestCoreDeliveryId", requestCoreDeliveryId);
            List<Object[]> list = sqlQuery.list();

            if (CollectionUtilities.hasContent(list)) {

                queryString = session.getNamedQuery("updateSupplementalAttachmentsReleaseStatus")
                        .getQueryString();

                for (Object[] obj : list) {

                    sqlQuery = session.createSQLQuery(queryString);
                    // if the relesed count is greater than 1 then it is already released.
                    boolean isReleased = (null == obj[1]) ? false : (((Integer) obj[1]) > 1);

                    sqlQuery.setParameter("isReleased", isReleased);
                    sqlQuery.setParameter("roiSupplementalAttachmentId", obj[0]);

                }
            }

            String queryStr = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementalAttachmentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryStr);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void createRequestCoreDeliveryToSupplementalDocuments(long requestCoreDeliveryId, Long supplementalDocumentSeq) {
        final String logSM = "createRequestCoreDeliveryToSupplementalDocuments(requestCoreDeliveryId, supplementalDocumentSeq)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementalDocumentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.setParameter("roiSupplementalDocumentId", supplementalDocumentSeq, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void deleteRequestCoreDeliveryToSupplementalDocuments(long requestCoreDeliveryId) {
        final String logSM = "deleteRequestCoreDeliveryToSupplementalDocuments(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveSupplementalDocumentAndReleasedCount")
                    .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("requestCoreDeliveryId", requestCoreDeliveryId);
            List<Object[]> list = sqlQuery.list();

            if (CollectionUtilities.hasContent(list)) {

                queryString = session.getNamedQuery("updateSupplementalDocumentsReleaseStatus")
                        .getQueryString();

                for (Object[] obj : list) {

                    sqlQuery = session.createSQLQuery(queryString);
                    // if the relesed count is greater than 1 then it is already
                    // released.
                    boolean isReleased = (null == obj[1])
                            ? false
                                    : (((Integer) obj[1]) > 1);

                    sqlQuery.setParameter("isReleased", isReleased);
                    sqlQuery.setParameter("roiSupplementalDocumentId", obj[0]);

                }
            }

            String queryStr = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementalDocumentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryStr);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#createRequestCoreDeliveryToSupplementarityAttachments(long, java.lang.Long)
     */
    @Override
    public void createRequestCoreDeliveryToSupplementarityAttachments(long requestCoreDeliveryId, Long supplementarityAttachmentSeq) {
        final String logSM = "createRequestCoreDeliveryToSupplementarityAttachments(requestCoreDeliveryId, supplementarityAttachmentSeq)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementarityAttachmentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.setParameter("roiSupplementarityAttachmentId", supplementarityAttachmentSeq, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#deleteRequestCoreDeliveryToSupplementarityAttachments(long)
     */
    @Override
    public void deleteRequestCoreDeliveryToSupplementarityAttachments(long requestCoreDeliveryId) {
        final String logSM = "deleteRequestCoreDeliveryToSupplementarityAttachments(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveSupplementarityAttachmentAndReleasedCount")
                    .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("requestCoreDeliveryId",
                    requestCoreDeliveryId);
            List<Object[]> list = sqlQuery.list();

            if (CollectionUtilities.hasContent(list)) {

                queryString = session.getNamedQuery("updateSupplementarityAttachmentsReleaseStatus")
                        .getQueryString();

                for (Object[] obj : list) {

                    sqlQuery = session.createSQLQuery(queryString);
                    // if the relesed count is greater than 1 then it is already
                    // released.
                    boolean isReleased = (null == obj[1])
                            ? false
                                    : (((Integer) obj[1]) > 1);

                    sqlQuery.setParameter("isReleased", isReleased);
                    sqlQuery.setParameter("roiSupplementarityAttachmentId", obj[0]);

                }
            }

            String queryStr = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementarityAttachmentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryStr);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#createRequestCoreDeliveryToSupplementarityDocuments(long, java.lang.Long)
     */
    @Override
    public void createRequestCoreDeliveryToSupplementarityDocuments(long requestCoreDeliveryId, Long supplementarityDocumentSeq) {
        final String logSM = "createRequestCoreDeliveryToSupplementarityDocuments(requestCoreDeliveryId, supplementarityDocumentSeq)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementarityDocumentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.setParameter("roiSupplementarityDocumentId", supplementarityDocumentSeq, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#deleteRequestCoreDeliveryToSupplementarityDocuments(long)
     */
    @Override
    public void deleteRequestCoreDeliveryToSupplementarityDocuments(long requestCoreDeliveryId) {
        final String logSM = "deleteRequestCoreDeliveryToSupplementarityDocuments(requestCoreDeliveryId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveSupplementarityDocumentAndReleasedCount")
                    .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("requestCoreDeliveryId",
                    requestCoreDeliveryId);
            List<Object[]> list = sqlQuery.list();

            if (CollectionUtilities.hasContent(list)) {

                queryString = session.getNamedQuery("updateSupplementarityDocumentsReleaseStatus")
                        .getQueryString();

                for (Object[] obj : list) {

                    sqlQuery = session.createSQLQuery(queryString);
                    // if the relesed count is greater than 1 then it is already
                    // released.
                    boolean isReleased = (null == obj[1]) ? false : (((Integer) obj[1]) > 1);

                    sqlQuery.setParameter("isReleased", isReleased);
                    sqlQuery.setParameter("roiSupplementarityDocumentId", obj[0]);
                    sqlQuery.executeUpdate();

                }
            }

            String queryStr = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementarityDocumentsCore")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryStr);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will save the sales tax reason details.
     * @param SalesTaxReason salesTaxReason
     *
     */
    @Override
    public void createSalesTaxReason(SalesTaxReason salesTaxReason) {
        final String logSM = "createSalesTaxReason(salesTaxReason)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + salesTaxReason);
        }
        try {

            Session session = getSession();
            Query query = session.getNamedQuery("createSalesTaxReason");

            query.setParameter("requestCoreChargesSeq",salesTaxReason.getRequestCoreChargesSeq(), LongType.INSTANCE);
            query.setParameter("reason", salesTaxReason.getReason(), StringType.INSTANCE);
            query.setParameter("reasonDate", salesTaxReason.getReasonDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdDate", salesTaxReason.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", salesTaxReason.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDate", salesTaxReason.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", salesTaxReason.getModifiedBy(), LongType.INSTANCE);

            query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestId " + salesTaxReason);
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will retrieve the sales tax reason details.
     * @param requestCoreChargesSeq
     * @return List<SalesTaxReason>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SalesTaxReason> retrieveSalesTaxReason(long requestCoreChargesSeq) {

        final String logSM = "retrieveSalesTaxReason(requestCoreChargesSeq)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesSeq);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveSalesTaxReason").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, LongType.INSTANCE);
            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("reason", StringType.INSTANCE);
            query.addScalar("reasonDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(SalesTaxReason.class));
            List<SalesTaxReason> salesTaxReasonList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return salesTaxReasonList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * Retrieve invoice summaries.
     *
     * @param requestId the request id
     * @return the invoice history
     */
    @Override
    public InvoiceHistory retrieveInvoiceSummaries(long requestCoreId) {

        final String logSM = "retrieveInvoiceSummaries(requestCoreId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + requestCoreId);
        }

        try {

            List<RequestCoreDeliveryCharges> rcDeliveryChargesList =
                    retrieveRequestCoreDeliveryCharges(requestCoreId);

            List<Invoice> invList = new ArrayList<Invoice>();
            if (rcDeliveryChargesList != null && rcDeliveryChargesList.size() > 0) {
                for (RequestCoreDeliveryCharges info : rcDeliveryChargesList) {

                    Invoice inv = new Invoice();
                    inv.setId(info.getId());

                    if (null == info.getCreator()) {
                        info.setCreator("Deleted User - " + info.getCreatedBy());
                    } else {
                        inv.setCreatorName(info.getCreator());
                    }


                    inv.setCreatedDate(ROIConstants.DATETIME_FORMATTER.format(info.getCreatedDt()));
                    inv.setType(info.getType());
                    inv.setQueuePassword(info.getQueuePassword());
                    inv.setResendDate(ROIConstants.DATETIME_FORMATTER.format(info.getResendDate()));
                    inv.setOutputMethod(info.getOutputMethod());
                    inv.setInvoiceDueDate(ROIConstants.DATE_FORMATTER.format(
                            info.getInvoiceDueDate()));

                    //Added to show total adjustment and total payment
                    //of invoice in invoice history.

                    double creditAdj = info.getCreditAdjustmentAmount();
                    double debitAdj = info.getDebitAdjustmentAmount();
                    double adjTotal = creditAdj + debitAdj;
                    inv.setTotalAdjustments(adjTotal);
                    inv.setTotalPayments(info.getPaymentAmount());

                    //Added to show original balance and current balance
                    //of invoice in invoice history.
                    inv.setBaseCharge(info.getBaseCharge());
                    inv.setCurrentBalance(info.getInvoiceBalanceDue());

                    invList.add(inv);
                }
            }
            InvoiceHistory history =  new InvoiceHistory(invList);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return history;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    @Override
    public RequestCoreDeliveryCharges retrieveDeliveryChargesUsingInvoiceId(long rcdChargesSeq) {
        final String logSM = "retrieveRequestCoreDeliveryCharges(rcdChargesSeq)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesSeq);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesInvoice")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesId", rcdChargesSeq, LongType.INSTANCE);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestCoreId", LongType.INSTANCE);
            query.addScalar("isReleased", BooleanType.INSTANCE);
            query.addScalar("previouslyReleasedCost", DoubleType.INSTANCE);
            query.addScalar("releaseCost", DoubleType.INSTANCE);
            query.addScalar("totalRequestCost", DoubleType.INSTANCE);
            query.addScalar("totalPagesReleased", IntegerType.INSTANCE);
            query.addScalar("totalPages", IntegerType.INSTANCE);
            query.addScalar("salesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("salesTaxPercentage", DoubleType.INSTANCE);
            query.addScalar("letterTemplateName", StringType.INSTANCE);
            query.addScalar("requestStatus", StringType.INSTANCE);
            query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("letterTemplateFileId", LongType.INSTANCE);
            query.addScalar("type", StringType.INSTANCE);
            query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("outputMethod", StringType.INSTANCE);
            query.addScalar("queuePassword", StringType.INSTANCE);
            query.addScalar("overwriteDueDate", BooleanType.INSTANCE);
            query.addScalar("invoiceSalesTax", DoubleType.INSTANCE);
            query.addScalar("baseCharge", DoubleType.INSTANCE);
            query.addScalar("balanceDue", DoubleType.INSTANCE);
            query.addScalar("invoiceBillingLocCode", StringType.INSTANCE);
            query.addScalar("invoiceBillinglocName", StringType.INSTANCE);
            query.addScalar("invoiceBalanceDue", DoubleType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("creditAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("debitAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("applySalesTax", BooleanType.INSTANCE);
            query.addScalar("notes", StringType.INSTANCE);
            query.addScalar("invoiceDueDays", LongType.INSTANCE);
            query.addScalar("requestCreditAdjustment", DoubleType.INSTANCE);
            query.addScalar("requestDebitAdjustment", DoubleType.INSTANCE);
            query.addScalar("requestBalanceDue", DoubleType.INSTANCE);
            query.addScalar("requestpayment", DoubleType.INSTANCE);
            query.addScalar("unbillable", BooleanType.INSTANCE);
            query.addScalar("unbillableAmount", DoubleType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryCharges.class));
            List<RequestCoreDeliveryCharges> requestCoreDeliveryChargesList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return (CollectionUtilities.hasContent(requestCoreDeliveryChargesList))
                    ? requestCoreDeliveryChargesList.get(0) : null;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * This method retrieves the RequestCoreCharges values for Fee
     *
     * @param rcdChargesId
     * @return Set<RequestCoreDeliveryChargesFee>
     */
    @Override
    public Set<RequestCoreDeliveryChargesFee>  retrieveDeliveryChargesFee(long rcdChargesId) {
        final String logSM = "retrieveDeliveryChargesFee(rcdChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesFee").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            Set<RequestCoreDeliveryChargesFee> rcdcFeeSet = new LinkedHashSet<RequestCoreDeliveryChargesFee>();

            query.setParameter("rcdChargesSeq", rcdChargesId, LongType.INSTANCE);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("isCustomFee", BooleanType.INSTANCE);
            query.addScalar("feeType", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.addScalar("hasSalesTax", BooleanType.INSTANCE);
            query.addScalar("SalesTaxAmount", DoubleType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesFee.class));

            List<RequestCoreDeliveryChargesFee> rcChargeFeeList = query.list();
            rcdcFeeSet.addAll(rcChargeFeeList);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + rcdChargesId);
            }

            return rcdcFeeSet;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method retrieves the RequestCoreCharges values for Document
     *
     * @param rcdChargesId
     * @return Set<RequestCoreDeliveryChargesDocument>
     */
    @Override
    public Set<RequestCoreDeliveryChargesDocument> retrieveDeliveryChargesDocument(Long rcdChargesId) {
        final String logSM = "retrieveDeliveryChargesDocument(rcdChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesId);
        }

        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDocument").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            Set<RequestCoreDeliveryChargesDocument> rcdcDocumentSet = new LinkedHashSet<RequestCoreDeliveryChargesDocument>();

            query.setParameter("rcdChargesSeq", rcdChargesId, LongType.INSTANCE);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("copies", IntegerType.INSTANCE);
            query.addScalar("billingTierName", StringType.INSTANCE);
            query.addScalar("totalPages", IntegerType.INSTANCE);
            query.addScalar("pages", IntegerType.INSTANCE);
            query.addScalar("billingtierId", StringType.INSTANCE);
            query.addScalar("releaseCount", IntegerType.INSTANCE);
            query.addScalar("isElectronic", BooleanType.INSTANCE);
            query.addScalar("removeBaseCharge", BooleanType.INSTANCE);
            query.addScalar("hasSalesTax", BooleanType.INSTANCE);
            query.addScalar("SalesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesDocument.class));

            List<RequestCoreDeliveryChargesDocument> rcdChargesDocument = query.list();
            rcdcDocumentSet.addAll(rcdChargesDocument);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + rcdChargesId);
            }

            return rcdcDocumentSet;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }
    /**
     * This method will delete the RequestCoreCharges for the particular request.
     * @param requestId
     */
    @Override
    public void deleteSalesTaxReason(long  requestCoreChargesId){
        final String logSM = "deleteSalesTaxReason(requestCoreChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("deleteSalesTaxReason")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, LongType.INSTANCE);
            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method retrieves the list of invoice patients
     *
     * @param deliveryChargesId
     * @return List<RequestCoreDeliveryInvoicePatients>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RequestCoreDeliveryInvoicePatients> retrieveInvoicePatients(List<Long> invoiceIds) {
        final String logSM = "retrieveInvoicePatients(retrieveInvoicePatients)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceIds);
        }

        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveInvoicePatients").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameterList("requestCoreDeliveryChargesId", invoiceIds, LongType.INSTANCE);
            query.addScalar("requestHpfPatientsId",LongType.INSTANCE);
            query.addScalar("requestNonHpfPatientsId",LongType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryInvoicePatients.class));

            List<RequestCoreDeliveryInvoicePatients> requestInvoicePatients = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + invoiceIds);
            }

            return requestInvoicePatients;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method retrieves the RequestCoreCharges values for Fee.
     *
     * @param rcdChargesId the rcd charges id
     * @return Set<RequestCoreDeliveryChargesFee>
     */
    @Override
    public List<RequestCoreDeliveryChargesFee>  retrieveAllDeliveryChargesFeeByInvoice(
            List<Long> rcdChargesId) {

        final String logSM = "retrieveAllDeliveryChargesFeeByInvoice(rcdChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesFee")
                    .getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("isCustomFee", BooleanType.INSTANCE);
            query.addScalar("feeType", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.addScalar("hasSalesTax", BooleanType.INSTANCE);
            query.addScalar("SalesTaxAmount", DoubleType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesFee.class));

            List<RequestCoreDeliveryChargesFee> rcChargeFeeList = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + rcdChargesId);
            }

            return rcChargeFeeList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method retrieves the RequestCoreCharges values for Fee.
     *
     * @param rcdChargesId the rcd charges id
     * @return Set<RequestCoreDeliveryChargesFee>
     */
    @Override
    public List<RequestCoreDeliveryChargesFee>  retrieveAllDeliveryChargesDistinctFeeByInvoice(
            List<Long> rcdChargesId) {

        final String logSM = "retrieveAllDeliveryChargesDistinctFeeByInvoice(rcdChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDistinctFee")
                    .getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("feeType", StringType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesFee.class));

            List<RequestCoreDeliveryChargesFee> rcChargeFeeList = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + rcdChargesId);
            }

            return rcChargeFeeList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method retrieves the RequestCoreCharges values for Document.
     *
     * @param rcdChargesId the rcd charges id
     * @return Set<RequestCoreDeliveryChargesDocument>
     */
    @Override
    public List<RequestCoreDeliveryChargesDocument> retrieveAllDeliveryChargesDocument(
            List<Long> rcdChargesId) {

        final String logSM = "retrieveAllDeliveryChargesDocument(rcdChargesId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesId);
        }

        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDocument")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("copies", IntegerType.INSTANCE);
            query.addScalar("billingTierName", StringType.INSTANCE);
            query.addScalar("totalPages", IntegerType.INSTANCE);
            query.addScalar("pages", IntegerType.INSTANCE);
            query.addScalar("billingtierId", StringType.INSTANCE);
            query.addScalar("releaseCount", IntegerType.INSTANCE);
            query.addScalar("isElectronic", BooleanType.INSTANCE);
            query.addScalar("removeBaseCharge", BooleanType.INSTANCE);
            query.addScalar("hasSalesTax", BooleanType.INSTANCE);
            query.addScalar("SalesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(
                    RequestCoreDeliveryChargesDocument.class));

            List<RequestCoreDeliveryChargesDocument> rcdChargesDocument = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + rcdChargesId);
            }

            return rcdChargesDocument;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method retrieves the RequestCoreCharges values for Document.
     *
     * @param rcdChargesId the rcd charges id
     * @return Set<RequestCoreDeliveryChargesDocument>
     */
    @Override
    public List<RequestCoreDeliveryChargesDocument> retrieveAllDeliveryChargesDistinctDocument(
            List<Long> rcdChargesId) {

        final String logSM = "retrieveAllDeliveryChargesDistinctDocument(rcdChargesId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesId);
        }

        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDistinctDocument")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("billingTierName", StringType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(
                    RequestCoreDeliveryChargesDocument.class));

            List<RequestCoreDeliveryChargesDocument> rcdChargesDocument = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + rcdChargesId);
            }

            return rcdChargesDocument;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * Retrieve all delivery charges using invoice ids.
     *
     * @param rcdChargesSeq the rcd charges seq
     * @return the list
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO
     * #retrieveAllDeliveryChargesUsingInvoiceIds(java.util.List)
     */
    @Override
    public List<RequestCoreDeliveryCharges> retrieveAllDeliveryChargesUsingInvoiceIds(List<Long> rcdChargesSeq) {

        final String logSM = "retrieveAllDeliveryChargesUsingInvoiceIds(rcdChargesSeq)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + rcdChargesSeq);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesInvoice")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesId", rcdChargesSeq);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestCoreId", LongType.INSTANCE);
            query.addScalar("isReleased", BooleanType.INSTANCE);
            query.addScalar("previouslyReleasedCost", DoubleType.INSTANCE);
            query.addScalar("releaseCost", DoubleType.INSTANCE);
            query.addScalar("totalRequestCost", DoubleType.INSTANCE);
            query.addScalar("totalPagesReleased", IntegerType.INSTANCE);
            query.addScalar("totalPages", IntegerType.INSTANCE);
            query.addScalar("salesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("salesTaxPercentage", DoubleType.INSTANCE);
            query.addScalar("letterTemplateName", StringType.INSTANCE);
            query.addScalar("requestStatus", StringType.INSTANCE);
            query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("letterTemplateFileId", LongType.INSTANCE);
            query.addScalar("type", StringType.INSTANCE);
            query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("outputMethod", StringType.INSTANCE);
            query.addScalar("queuePassword", StringType.INSTANCE);
            query.addScalar("overwriteDueDate", BooleanType.INSTANCE);
            query.addScalar("invoiceSalesTax", DoubleType.INSTANCE);
            query.addScalar("baseCharge", DoubleType.INSTANCE);
            query.addScalar("balanceDue", DoubleType.INSTANCE);
            query.addScalar("invoiceBillingLocCode", StringType.INSTANCE);
            query.addScalar("invoiceBillinglocName", StringType.INSTANCE);
            query.addScalar("invoiceBalanceDue", DoubleType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("creditAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("debitAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("applySalesTax", BooleanType.INSTANCE);
            query.addScalar("notes", StringType.INSTANCE);
            query.addScalar("invoiceDueDays", LongType.INSTANCE);
            query.addScalar("creator", StringType.INSTANCE);

            query.addScalar("requestCreditAdjustment", DoubleType.INSTANCE);
            query.addScalar("requestDebitAdjustment", DoubleType.INSTANCE);
            query.addScalar("requestBalanceDue", DoubleType.INSTANCE);
            query.addScalar("requestpayment", DoubleType.INSTANCE);

            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryCharges.class));
            List<RequestCoreDeliveryCharges> requestCoreDeliveryChargesList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return requestCoreDeliveryChargesList;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#retrieveAllAdjustmentPaymentByInvoiceIds(java.util.List)
     */
    @Override
    public List<RequestCoreDeliveryChargesAdjustmentPayment> retrieveAllAdjustmentPaymentByInvoiceIds(List<Long> requestCoreDeliveryChargesId) {
        final String logSM = "retrieveAllAdjustmentPaymentByInvoiceIds(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesAdjustmentPayment")
                    .getQueryString();;
                    NativeQuery query = session.createSQLQuery(queryString);

                    query.setParameterList("requestCoreDeliveryChargesIds", requestCoreDeliveryChargesId);

                    query.addScalar("id", LongType.INSTANCE);
                    query.addScalar("requestCoreDeliveryChargesId", LongType.INSTANCE);
                    query.addScalar("invoiceAppliedAmount", DoubleType.INSTANCE);
                    query.addScalar("paymentMode", StringType.INSTANCE);
                    query.addScalar("description", StringType.INSTANCE);
                    query.addScalar("paymentDate", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("transactionType", StringType.INSTANCE);
                    query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("createdBy", LongType.INSTANCE);
                    query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("modifiedBy", LongType.INSTANCE);
                    query.addScalar("recordVersion", IntegerType.INSTANCE);
                    query.setResultTransformer(Transformers.aliasToBean(
                            RequestCoreDeliveryChargesAdjustmentPayment.class));
                    List<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesAdjustmentPaymentList = query.list();

                    if (DO_DEBUG) {
                        LOG.debug(logSM + "<<End");
                    }

                    return requestCoreDeliveryChargesAdjustmentPaymentList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#retrieveAllRequestCoreDeliveryChargesShipping(java.util.List)
     */
    @Override
    public List<RequestCoreDeliveryChargesShipping> retrieveAllRequestCoreDeliveryChargesShipping(
            List<Long> requestCoreDeliveryChargesId) {

        final String logSM = "retrieveAllRequestCoreDeliveryChargesShipping(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesShipping").getQueryString();;
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestCoreDeliveryChargesId", LongType.INSTANCE);
            query.addScalar("shippingCharge", DoubleType.INSTANCE);
            query.addScalar("postalCode", StringType.INSTANCE);
            query.addScalar("addressType", StringType.INSTANCE);
            query.addScalar("state", StringType.INSTANCE);
            query.addScalar("shippingUrl", StringType.INSTANCE);
            query.addScalar("address1", StringType.INSTANCE);
            query.addScalar("address2", StringType.INSTANCE);
            query.addScalar("address3", StringType.INSTANCE);
            query.addScalar("shippingWeight", DoubleType.INSTANCE);
            query.addScalar("trackingNumber", StringType.INSTANCE);
            query.addScalar("city", StringType.INSTANCE);
            query.addScalar("countryCode", StringType.INSTANCE);
            query.addScalar("countryName", StringType.INSTANCE);
            query.addScalar("willReleaseShipped", BooleanType.INSTANCE);
            query.addScalar("willInvoiceShipped", BooleanType.INSTANCE);
            query.addScalar("shippingMethod", StringType.INSTANCE);
            query.addScalar("outputMethod", StringType.INSTANCE);
            query.addScalar("shippingMethodId", LongType.INSTANCE);
            query.addScalar("nonPrintableAttachmentQueue", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryChargesShipping.class));

            List<RequestCoreDeliveryChargesShipping> requestCoreDeliveryChargesShippingList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return requestCoreDeliveryChargesShippingList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

   
    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO
     * #createLetterCore(com.mckesson.eig.roi.billing.model.CoverLetterCore)
     */
    @Override
    public long createLetterCore(final CoverLetterCore coverLetterCore) {

        final String logSM = "createLetterCore(coverLetterCore)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + coverLetterCore);
        }

        try {

            final Session session = getSession();
            Query query = session.getNamedQuery("CreateCoverLetterCore");
            //            query.setParameter("requestCoreChargesId", coverLetterCore.getRequestCoreChargesId(), LongType.INSTANCE);
            query.setParameter("requestId", coverLetterCore.getRequestId(), LongType.INSTANCE);
            query.setParameter("requestorId", coverLetterCore.getRequestorId(), LongType.INSTANCE);
            query.setParameter("letterTemplateId", coverLetterCore.getLetterTemplateId(), LongType.INSTANCE);
            query.setParameter("note", coverLetterCore.getNotes(), StringType.INSTANCE);
            query.setParameter("templateType", coverLetterCore.getLetterType(), StringType.INSTANCE);
            query.setParameter("queuePassword", coverLetterCore.getQueuePassword(), StringType.INSTANCE);
            query.setParameter("modifiedDt", coverLetterCore.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", coverLetterCore.getModifiedBy(), LongType.INSTANCE);
            query.setParameter("createdDt", coverLetterCore.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", coverLetterCore.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("recordVersion", coverLetterCore.getRecordVersion(), IntegerType.INSTANCE);

            BigDecimal coverLetterId = (BigDecimal) query.uniqueResult();
            if (coverLetterId == null || coverLetterId.longValue() <= 0) {
                throw new ROIException(ROIClientErrorCodes.INVALID_COVER_LETTER_ID);
            }

            long letterId = coverLetterId.longValue();
            coverLetterCore.setId(letterId);

            createCoverLetterHpfPatients(coverLetterCore);
            createCoverLetterSupplementalPatients(coverLetterCore);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return letterId;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.FAILED_TO_CREATE_COVER_LETTER,
                    e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO
     * #deleteCoverLetter(long)
     */
    @Override
    public void deleteCoverLetter(long coverLetterId) {

        final String logSM = "deleteCoverLetter(coverLetterId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + coverLetterId);
        }

        try {

            Session session = getSession();

            // deletes the hpf patients corresponding for the given cover letter
            String queryString = session.getNamedQuery("deleteCoverLetterRequestHpfPatient")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("coverLetterId", coverLetterId, LongType.INSTANCE);
            query.executeUpdate();
            
            // deletes the non-hpf patients corresponding for the given cover letter
             queryString = session.getNamedQuery("deleteCoverLetterRequestSupplementalPatient")
                    .getQueryString();
             query = session.createSQLQuery(queryString);
            query.setParameter("coverLetterId", coverLetterId, LongType.INSTANCE);
            query.executeUpdate();

            // deletes the cover letter
            queryString = session.getNamedQuery("deleteCoverLetter")
                    .getQueryString();
             query = session.createSQLQuery(queryString);
            query.setParameter("coverLetterId", coverLetterId, LongType.INSTANCE);
            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.FAILED_TO_CREATE_COVER_LETTER,
                    e.getMessage());
        }
    }

    /**
     * @param coverLetterCore
     * @param session
     * @throws SQLException
     */
    private void createCoverLetterSupplementalPatients(final CoverLetterCore coverLetterCore) {

        final String logSM = "createCoverLetterSupplementalPatients(coverLetterCore)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + coverLetterCore);
        }

        try {
            final Session session = getSession();
            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestPatient supplementalPatient = (RequestPatient) object;
                    int index = 1;
                    pStmt.setLong(index++, coverLetterCore.getId());
                    pStmt.setLong(index++, supplementalPatient.getPatientSeq());
                    pStmt.setTimestamp(index++, getSQLTimeStamp(coverLetterCore.getCreatedDt()));
                    pStmt.setLong(index++, coverLetterCore.getCreatedBy());
                    pStmt.setTimestamp(index++, getSQLTimeStamp(coverLetterCore.getModifiedDt()));
                    pStmt.setLong(index++, coverLetterCore.getModifiedBy());
                    pStmt.setInt(index++, coverLetterCore.getRecordVersion());
                }
            };

            List<RequestPatient> supplementalPatients =
                    coverLetterCore.getRequestSupplementalPatients();
            String queryString = session.getNamedQuery("insertRequestSupplementalPatient")
                    .getQueryString();
            processor.execute(supplementalPatients, queryString);

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.FAILED_TO_CREATE_COVER_LETTER_PATIENTS,
                    e.getMessage());
        }
    }

    /**
     * @param coverLetterCore
     * @param session
     * @throws SQLException
     */
    private void createCoverLetterHpfPatients(final CoverLetterCore coverLetterCore) {

        final String logSM = "createCoverLetterHpfPatients(coverLetterCore)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + coverLetterCore);
        }

        try {

            final Session session = getSession();
            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestPatient hpfPatients = (RequestPatient) object;
                    int index = 1;
                    pStmt.setLong(index++, coverLetterCore.getId());
                    pStmt.setLong(index++, hpfPatients.getPatientSeq());
                    pStmt.setTimestamp(index++, getSQLTimeStamp(coverLetterCore.getCreatedDt()));
                    pStmt.setLong(index++, coverLetterCore.getCreatedBy());
                    pStmt.setTimestamp(index++, getSQLTimeStamp(coverLetterCore.getModifiedDt()));
                    pStmt.setLong(index++, coverLetterCore.getModifiedBy());
                    pStmt.setInt(index++, coverLetterCore.getRecordVersion());

                }
            };

            List<RequestPatient> hpfPatients = coverLetterCore.getRequestHpfPatients();
            String queryString = session.getNamedQuery("insertRequestHpfPatient").getQueryString();
            processor.execute(hpfPatients, queryString);

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.FAILED_TO_CREATE_COVER_LETTER_PATIENTS,
                    e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#retrieveLetterCore(long)
     */
    @Override
    public CoverLetterCore retrieveLetterCore(long coverLetterId) {

        final String logSM = "retrieveLetterCore(coverLetterId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + coverLetterId);
        }

        try {

            final Session session = getSession();
            String queryString = session.getNamedQuery("retrieveCoverLetterCore").getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", LongType.INSTANCE);
            //            query.addScalar("requestCoreChargesId", LongType.INSTANCE);
            query.addScalar("requestId", LongType.INSTANCE);
            query.addScalar("requestorId",LongType.INSTANCE);
            query.addScalar("letterTemplateId", LongType.INSTANCE);
            query.addScalar("notes", StringType.INSTANCE);
            query.addScalar("letterType", StringType.INSTANCE);
            query.addScalar("queuePassword", StringType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);

            query.setParameter("coverLetterId", coverLetterId);
            query.setResultTransformer(Transformers.aliasToBean(CoverLetterCore.class));
            CoverLetterCore coverLetter = (CoverLetterCore) query.uniqueResult();

            // retrieves the list of hpf patients Sequences
            queryString = session.getNamedQuery("retrieveCoverLetterRequestHpfPatient")
                    .getQueryString();

            query = session.createSQLQuery(queryString);
            setScalarsForRequestPatient(query);

            query.setParameter("coverLetterId", coverLetterId, LongType.INSTANCE);
            List<RequestPatient> requestHpfPatients = query.list();
            coverLetter.setRequestHpfPatients(requestHpfPatients);

            // retrieves the list of nonhpf patients Sequences
            queryString = session.getNamedQuery("retrieveCoverLetterRequestSupplementalPatient")
                    .getQueryString();

            query = session.createSQLQuery(queryString);
            setScalarsForRequestPatient(query);

            query.setParameter("coverLetterId", coverLetterId, LongType.INSTANCE);
            List<RequestPatient> requestsupplementalPatients = query.list();
            coverLetter.setRequestSupplementalPatients(requestsupplementalPatients);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return coverLetter;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.FAILED_TO_RETRIEVE_COVER_LETTER, e.getMessage());
        }
    }

    /**
     * set the request patietns scalars for the given SQL Query
     * @param session
     * @param queryString
     */
    private void setScalarsForRequestPatient(NativeQuery query) {

        query.addScalar("patientSeq", LongType.INSTANCE);
        query.addScalar("requestId", LongType.INSTANCE);
        query.addScalar("supplementalId", LongType.INSTANCE);
        query.addScalar("mrn", StringType.INSTANCE);
        query.addScalar("facility", StringType.INSTANCE);
        query.addScalar("name", StringType.INSTANCE);
        query.addScalar("gender", StringType.INSTANCE);
        query.addScalar("epn", StringType.INSTANCE);
        query.addScalar("ssn", StringType.INSTANCE);
        query.addScalar("patientLocked", BooleanType.INSTANCE);
        query.addScalar("encounterLocked", BooleanType.INSTANCE);
        query.addScalar("vip", BooleanType.INSTANCE);
        query.addScalar("hpf", BooleanType.INSTANCE);
        query.addScalar("dob", StandardBasicTypes.TIMESTAMP);

        query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
        query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
        query.addScalar("modifiedBy", IntegerType.INSTANCE);
        query.addScalar("createdBy", IntegerType.INSTANCE);

        query.setResultTransformer(Transformers.aliasToBean(RequestPatient.class));
    }

    
    @Override
    public List<PastInvoice> retrievePastInvoices(long requestId) {

        final String logSM = "retrievePastInvoices(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId" + requestId);
        }
        try{

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveAllPastInvoices").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, LongType.INSTANCE);

            query.addScalar("invoiceId", LongType.INSTANCE);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("createdDate", StringType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(PastInvoice.class));
            List<PastInvoice> invoiceDetailsList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return invoiceDetailsList;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public ChargeHistoryList retrieveChargeHistory(long requestId) {

        final String logSM = "retrieveChargeHistory(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request Id : " + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveReleaseChargeHistory").getQueryString();;
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, LongType.INSTANCE);

            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("totalShippingCharge", DoubleType.INSTANCE);
            query.addScalar("totalSalesTax", DoubleType.INSTANCE);
            query.addScalar("totalFeeCharge", DoubleType.INSTANCE);
            query.addScalar("totalDocumentCharge", DoubleType.INSTANCE);
            query.addScalar("unbillable", BooleanType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(ChargeHistory.class));
            List<ChargeHistory> chargeHistory = query.list();


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Size" + chargeHistory.size());
            }

            return new ChargeHistoryList(chargeHistory);

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    @Override
    public void updateInvoiceOutputLetter(InvoiceAndLetterOutputProperties outputProperties) {

        final String logSM = "updateInvoiceOutputLetter(outputProperties)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:outputProperties : " + outputProperties);
        }

        try {

            Session session = getSession();

            if (outputProperties.isForInvoice()) {

                String queryString = session.getNamedQuery("updateInvoiceProperty").getQueryString();;
                NativeQuery query = session.createSQLQuery(queryString);

                query.setParameter("invoiceId", outputProperties.getInvoiceId());
                query.setParameter("queuePassword", outputProperties.getQueuePassword());
                query.setParameter("outputMethod", outputProperties.getOutputMethod());
                query.executeUpdate();
            }

            if (outputProperties.isForLetter()) {

                String queryString = session.getNamedQuery("updateLetterProperty").getQueryString();;
                NativeQuery query = session.createSQLQuery(queryString);

                query.setParameter("letterId", outputProperties.getLetterId());
                query.setParameter("queuePassword", outputProperties.getQueuePassword());
                query.executeUpdate();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * Updates queue password for the releases
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#updateReleaseOutputProperties(com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties)
     */
    @Override
    public void updateReleaseOutputProperties(InvoiceAndLetterOutputProperties outputProperties) {

        final String logSM = "updateReleaseOutputProperties(outputProperties)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:outputProperties : " + outputProperties);
        }

        try {

            Session session = getSession();

            String queryString = session.getNamedQuery("updateReleaseOutputProperty")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("releaseId", outputProperties.getReleaseId());
            query.setParameter("queuePassword", outputProperties.getQueuePassword());
            query.executeUpdate();


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * Deletes the event which is created during invoice/prebill creation.
     * The auto adjustment events are created at the time of invoice creation, which
     * should be deleted when deleting invoice/ prebill
     * this method should be called when deleting the invoice/Prebill.
     *
     * @param invoiceId - invoice Id of the event to be deleted
     */
    @Override
    public void deleteInvoiceAutoAdjEvent(long invoiceId) {

        final String logSM = "deleteInvoiceAutoAdjEvent(invoiceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        // delete the event having the description like below statement
        // debit adjustment comment
        final String description1 = "%was made by the system. Invoice # " + invoiceId + "%";
        // credit adjustment comment
        final String description2 = "%was made by the system on Invoice " + invoiceId + "%";

        try {

            Session session = getSession();

            String queryString = session.getNamedQuery("deleteInvoiceAutoAdjEvent")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("description1", description1);
            query.setParameter("description2", description2);
            query.setParameter("invoiceId", invoiceId);
            query.executeUpdate();

        } catch (DataAccessException e) {

            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATA_ACCESS_EXCEPTION);
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }


    
    /**
     * This method is used to update the invoice balance
     *
     * @param invoiceId
     * @param invoiceBalance
     * @param creditAdjAmt
     * @param adjPayTotal
     * @param date
     * @param user
     *
     */

    @Override
    public void updateInvoiceBalance(long invoiceId, double invoiceBalance, Date date,User user) {

        final String logSM = "updateInvoiceBalance";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("updateInvoiceBalance")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("invoiceId", invoiceId, LongType.INSTANCE);
            query.setParameter("invoiceBalance", invoiceBalance, DoubleType.INSTANCE);
            query.setParameter("modifiedDt", getSQLTimeStamp(date), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(), IntegerType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * This method is used to update the Prebill Status of Invoice
     *
     * @param invoice
     *
     */
    @Override
    public void updatePrebillStatusInvoice(RequestCoreDeliveryCharges invoice) {

        final String logSM = "updatePrebillStatusInvoice(invoice)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoice);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("updatePrebillStatusInvoice")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("reqCoreSeq", invoice.getRequestCoreId(), LongType.INSTANCE);
            query.setParameter("prebillStatus", invoice.getPrebillStatus(), StringType.INSTANCE);
            query.executeUpdate();
            
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * This method will retrieve Prebill records from RequestCoreDeliveryCharges
     * @param long requestCoreDeliveryId
     * @return RequestCoreDeliveryCharges retrieveRequestCoreDeliveryCharges
     */
    @Override
    public List<RequestCoreDeliveryCharges> retrieveRequestCoreDeliveryChargesPrebill(long requestCoreId) {
        final String logSM = "retrieveRequestCoreDeliveryChargesPrebill(requestCoreDeliveryId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesPrebill")
                    .getQueryString();;
                    NativeQuery query = session.createSQLQuery(queryString);
                    query.setParameter("requestCoreId", requestCoreId, LongType.INSTANCE);

                    query.addScalar("id", LongType.INSTANCE);
                    query.addScalar("isReleased", BooleanType.INSTANCE);
                    query.addScalar("previouslyReleasedCost", DoubleType.INSTANCE);
                    query.addScalar("releaseCost", DoubleType.INSTANCE);
                    query.addScalar("totalRequestCost", DoubleType.INSTANCE);
                    query.addScalar("totalPagesReleased", IntegerType.INSTANCE);
                    query.addScalar("totalPages", IntegerType.INSTANCE);
                    query.addScalar("salesTaxAmount", DoubleType.INSTANCE);
                    query.addScalar("salesTaxPercentage", DoubleType.INSTANCE);
                    query.addScalar("letterTemplateName", StringType.INSTANCE);
                    query.addScalar("requestStatus", StringType.INSTANCE);
                    query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("letterTemplateFileId", LongType.INSTANCE);
                    query.addScalar("type", StringType.INSTANCE);
                    query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("outputMethod", StringType.INSTANCE);
                    query.addScalar("queuePassword", StringType.INSTANCE);
                    query.addScalar("overwriteDueDate", BooleanType.INSTANCE);
                    query.addScalar("invoiceSalesTax", DoubleType.INSTANCE);
                    query.addScalar("baseCharge", DoubleType.INSTANCE);
                    query.addScalar("balanceDue", DoubleType.INSTANCE);
                    query.addScalar("invoiceBillingLocCode", StringType.INSTANCE);
                    query.addScalar("invoiceBillinglocName", StringType.INSTANCE);
                    query.addScalar("invoiceBalanceDue", DoubleType.INSTANCE);
                    query.addScalar("paymentAmount", DoubleType.INSTANCE);
                    query.addScalar("creditAdjustmentAmount", DoubleType.INSTANCE);
                    query.addScalar("debitAdjustmentAmount", DoubleType.INSTANCE);
                    query.addScalar("applySalesTax", BooleanType.INSTANCE);
                    query.addScalar("notes", StringType.INSTANCE);
                    query.addScalar("unbillable", BooleanType.INSTANCE);
                    query.addScalar("unbillableAmount", DoubleType.INSTANCE);
                    query.addScalar("invoiceDueDays", LongType.INSTANCE);
                    query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("createdBy", LongType.INSTANCE);
                    query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
                    query.addScalar("modifiedBy", LongType.INSTANCE);
                    query.addScalar("recordVersion", IntegerType.INSTANCE);
                    query.addScalar("prebillStatus", StringType.INSTANCE);
                    query.setResultTransformer(Transformers.aliasToBean(RequestCoreDeliveryCharges.class));
                    List<RequestCoreDeliveryCharges> requestCoreDeliveryChargesList = query.list();

                    if (DO_DEBUG) {
                        LOG.debug(logSM + "<<End");
                    }
                    return requestCoreDeliveryChargesList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     *  This method is used to retrieve details for the prebill report with requestor name
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,balancecriterion,resultType
     *  @return list
     */
    @Override
    public List<PrebillReportDetails> retrievePrebillReportDetailsWithRequestorName(String[] facility, String[] requestorTypeName, String requestorName,
            String[] reqStatus, String fromDt, String toDt, Double balance, String balanceCriterion,String resultType) {
        final String logSM = "retrievePrebillReportDetailsWithRequestorName()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try{
            Session session = getSession();
            String queryString = null;
            if("\"Summary - Requestor Type\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retrievePrebillReportDetailsFirstLevelWithRequestorName").getQueryString();
            else if("\"Detailed - Requestor name\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retrievePrebillReportDetailsSecondLevelWithRequestorName").getQueryString();
            else
                queryString = session.getNamedQuery("retrievePrebillReportDetailsThirdLevelWithRequestorName").getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility,StringType.INSTANCE);
            query.setParameterList("requestorTypeName", requestorTypeName,StringType.INSTANCE);
            query.setParameterList("requestStatus",reqStatus,StringType.INSTANCE);
            query.setParameter("balanceCriteria",balanceCriterion,StringType.INSTANCE);
            query.setParameter("requestorName","%" + getSpecialCharSearchStr(requestorName) + "%",StringType.INSTANCE);
            query.setParameter("balanceDue",balance,DoubleType.INSTANCE);
            query.setParameter("fromDt",fromDt, StringType.INSTANCE);
            query.setParameter("toDt", toDt, StringType.INSTANCE);

            query.addScalar("facility", StringType.INSTANCE);
            query.addScalar("requestorType", StringType.INSTANCE);
            query.addScalar("requestorName", StringType.INSTANCE);
            query.addScalar("requestorPhone", StringType.INSTANCE);
            query.addScalar("requestId", StringType.INSTANCE);
            query.addScalar("prebillNumber", StringType.INSTANCE);
            query.addScalar("prebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("prebillAmount", DoubleType.INSTANCE);
            query.addScalar("aging", StringType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(PrebillReportDetails.class));
            @SuppressWarnings("unchecked")
            List<PrebillReportDetails> reportList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return reportList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     *  This method is used to retrieve details for the prebill report without requestor name
     *
     *  @param facList,reqType,reqName,reqStatus,aging,fromDt,toDt,balance,balancecriterion,resultType
     *  @return list
     */
    @Override
    public List<PrebillReportDetails> retrievePrebillReportDetailsWithoutRequestorName(String[] facility, String[] requestorTypeName,
            String[] reqStatus, String fromDt, String toDt, Double balance, String balanceCriterion,String resultType) {
        final String logSM = "retrievePrebillReportDetailsWithoutRequestorName()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try{
            Session session = getSession();
            String queryString = null;
            if("\"Summary - Requestor Type\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retrievePrebillReportDetailsFirstLevelWithoutRequestorName").getQueryString();
            else if("\"Detailed - Requestor name\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retrievePrebillReportDetailsSecondLevelWithoutRequestorName").getQueryString();
            else
                queryString = session.getNamedQuery("retrievePrebillReportDetailsThirdLevelWithoutRequestorName").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility,StringType.INSTANCE);
            query.setParameterList("requestorTypeName", requestorTypeName,StringType.INSTANCE);
            query.setParameterList("requestStatus",reqStatus,StringType.INSTANCE);
            query.setParameter("balanceCriteria",balanceCriterion,StringType.INSTANCE);
            query.setParameter("balanceDue",balance,DoubleType.INSTANCE);
            query.setParameter("fromDt",fromDt, StringType.INSTANCE);
            query.setParameter("toDt", toDt, StringType.INSTANCE);

            query.addScalar("facility", StringType.INSTANCE);
            query.addScalar("requestorType", StringType.INSTANCE);
            query.addScalar("requestorName", StringType.INSTANCE);
            query.addScalar("requestorPhone", StringType.INSTANCE);
            query.addScalar("requestId", StringType.INSTANCE);
            query.addScalar("prebillNumber", StringType.INSTANCE);
            query.addScalar("prebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("prebillAmount", DoubleType.INSTANCE);
            query.addScalar("aging", StringType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(PrebillReportDetails.class));
            @SuppressWarnings("unchecked")
            List<PrebillReportDetails> reportList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return reportList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     *  This method is used to retrieve details for the PostPayment report
     *
     *  @param facList,reqType,userName,fromDt,toDt,resultType
     *  @return list
     */
    @Override
    public List<PostPaymentReportDetails> retrievePostPaymentReportDetails(
            String[] facility, List<String> userName,String[] requestorTypeName,
            Date fromDt, Date toDt,String resultType) {
        final String logSM = "retrievePostPaymentReportDetails()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try{
            Session session = getSession();
            String queryString = null;
            if("\"Posted By\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retrievePostPaymentReportDetailsFirstLevel").getQueryString();
            else
                queryString = session.getNamedQuery("retrievePostPaymentReportDetailsSecondLevel").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility);
            query.setParameterList("requestorType", requestorTypeName);
            query.setParameterList("userName", userName);
            query.setParameter("fromDt",fromDt, StandardBasicTypes.TIMESTAMP);
            query.setParameter("toDt", toDt, StandardBasicTypes.TIMESTAMP);

            query.addScalar("facility", StringType.INSTANCE);
            query.addScalar("userName", StringType.INSTANCE);
            query.addScalar("requestorType", StringType.INSTANCE);
            query.addScalar("requestorName", StringType.INSTANCE);
            query.addScalar("mrn", StringType.INSTANCE);
            query.addScalar("requestId", StringType.INSTANCE);
            query.addScalar("invoiceNumber", StringType.INSTANCE);
            query.addScalar("paymentMethod", StringType.INSTANCE);
            query.addScalar("paymentDetails", StringType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("paymentId", StringType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(PostPaymentReportDetails.class));
            @SuppressWarnings("unchecked")
            List<PostPaymentReportDetails> reportList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return reportList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void deleteAllMappedAdjustmentInvoicesByInvoiceId(long invoiceId) {

        final String logSM =
                "deleteAllMappedAdjustmentInvoicesByInvoiceId(invoiceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("deleteAllMappedAdjustmentInvoicesByInvoiceId")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("invoiceId", invoiceId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    @Override
    public void deleteAllMappedPaymentInvoicesByInvoiceId(long invoiceId) {

        final String logSM =
                "deleteAllMappedPaymentInvoicesByInvoiceId(invoiceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("deleteAllMappedPaymentInvoicesByInvoiceId")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("invoiceId", invoiceId, LongType.INSTANCE);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }

    }
    //US16834 changes to Include requests in the pre-bill status on the payments popup(This function will convert prebill payments to invoice payments).
    public void updatePrebillPaymentsToInvoice(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo) {
        final String logSM = "updatePrebillPaymentsToInvoice(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invOrPrebillPreviewInfo.getRequestCoreId());
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("updatePrebillPaymentsToInvoice").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(),LongType.INSTANCE);
            sqlQuery.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + invOrPrebillPreviewInfo.getRequestCoreId());
            }


        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    //US16834 changes to Include requests in the pre-bill status on the payments/adjustments popup(This function will convert prebill adjustments to invoice adjustments).
    public void updatePrebillAdjustmentsToInvoice(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo) {
        final String logSM = "updatePrebillAdjustmentsToInvoice(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invOrPrebillPreviewInfo.getRequestCoreId());
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("updatePrebillAdjustmentsToInvoice").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(),LongType.INSTANCE);
            sqlQuery.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + invOrPrebillPreviewInfo.getRequestCoreId());
            }


        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    public void updateInvoicePaymentsToPrebill(long requestId, long invoiceId) {
        final String logSM = "updateInvoicePaymentsToPrebill(requestId, invoiceId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("updateInvoicePaymentsToPrebill").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);
            sqlQuery.setParameter("invoiceId", invoiceId, LongType.INSTANCE);
            sqlQuery.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }


        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    //US16834 changes to Include requests in the pre-bill status on the payments/adjustments popup(This function will convert prebill adjustments to invoice adjustments).
    public void updateInvoiceAdjustmentsToPrebill(long requestId, long invoiceId) {
        final String logSM = "updateInvoiceAdjustmentsToPrebill(requestId, invoiceId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("updateInvoiceAdjustmentsToPrebill").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);
            sqlQuery.setParameter("invoiceId", invoiceId, LongType.INSTANCE);
            sqlQuery.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }


        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    
    public long retrievePaymentDetailsFromDialog(long requestId) {
        final String logSM = "retrievePaymentDetailsFromDialog(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        long paymentId = 0L;
        Long paymentValue = null;
        try {

            Session session = getSession();
            String query = session.getNamedQuery("retrievePaymentDetailsFromDialog").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);
            sqlQuery.addScalar("paymentId", LongType.INSTANCE);
            paymentValue = (Long) sqlQuery.uniqueResult();

            if (null != paymentValue) {
                paymentId = paymentValue.longValue();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
        return paymentId;
    }

    public long retrieveAdjustmentDetailsFromDialog(long requestId) {
        final String logSM = "retrieveAdjustmentDetailsFromDialog(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        long adjustmentId = 0L;
        Long adjustmentValue = null;
        try {

            Session session = getSession();
            String query = session.getNamedQuery("retrieveAdjustmentDetailsFromDialog").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);
            sqlQuery.addScalar("adjustmentId", LongType.INSTANCE);
            adjustmentValue = (Long) sqlQuery.uniqueResult();

            if (null != adjustmentValue) {
                adjustmentId = adjustmentValue.longValue();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
        return adjustmentId;
    }

    public void unmapPaymentsFromInvoiceFromDialog(long paymentId) {
        final String logSM = "unmapPaymentsFromInvoiceFromDialog(paymentId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("unmapPaymentsFromInvoiceFromDialog").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestorPaymentSeq", paymentId, LongType.INSTANCE);
            sqlQuery.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + paymentId);
            }


        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    public void unmapAdjustmentsFromInvoiceFromDialog(long adjustmentId) {
        final String logSM = "unmapAdjustmentsFromInvoiceFromDialog(adjustmentId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + adjustmentId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("unmapAdjustmentsFromInvoiceFromDialog").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestorAdjustmentSeq", adjustmentId, LongType.INSTANCE);
            sqlQuery.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + adjustmentId);
            }


        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    //US16834 changes to Include requests in the pre-bill status on the payments/adjustments popup(This function will convert prebill adjustments to invoice adjustments).
    public void activateLatestPrebill(long requestId) {
        final String logSM = "activateLatestPrebill(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("activateLatestPrebill").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId,LongType.INSTANCE);
            sqlQuery.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }


        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    //US16834 changes to Include requests in the pre-bill status on the payments popup(This function will check if there is any prebill payment).
    public boolean prebillPaymentExists(long requestId) {
        final String logSM = "IsPrebillPaymentExists(long requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("prebillPaymentExists").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);

            BigDecimal value = (BigDecimal) sqlQuery.uniqueResult();

            if (value != null && value.doubleValue() != 0.0) {
                return true;
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }
            return false;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    // US16834 changes to Include requests in the pre-bill status on the payments/adjustment popup(This function will check if there is any prebill adjustment).
    public boolean prebillAdjustmentExists(long requestId) {
        final String logSM = "prebillAdjustmentExists(long requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("prebillAdjustmentExists").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);

            BigDecimal value = (BigDecimal) sqlQuery.uniqueResult();

            if (value != null && value.doubleValue() != 0.0) {
                return true;
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }
            return false;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    //US16834 changes to Include requests in the pre-bill status on the payments popup.(this function will calculate total post prebill payments)
    public double totalPostPrebillPayments(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo){      

        final String logSM = "TotalPostPrebillPayments(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invOrPrebillPreviewInfo.getRequestCoreId());
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("totalPostPrebillPayments").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(), LongType.INSTANCE);
            sqlQuery.setParameter("Charge", invOrPrebillPreviewInfo.getBaseCharge(), DoubleType.INSTANCE);

            BigDecimal value = (BigDecimal) sqlQuery.uniqueResult();
            double Doublevalue = value.doubleValue();


            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + invOrPrebillPreviewInfo.getRequestCoreId());
            }
            return Doublevalue;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }


    }

    //US16834 changes to Include requests in the pre-bill status on the payments/adjustment popup.(this function will calculate total post prebill adjustments)
    public double totalPostPrebillAdjustments(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo){      

        final String logSM = "totalPostPrebillAdjustments(InvoiceOrPrebillAndPreviewInfo invOrPrebillPreviewInfo)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invOrPrebillPreviewInfo.getRequestCoreId());
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("totalPostPrebillAdjustments").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(), LongType.INSTANCE);
            sqlQuery.setParameter("Charge", invOrPrebillPreviewInfo.getBaseCharge(), DoubleType.INSTANCE);

            BigDecimal value = (BigDecimal) sqlQuery.uniqueResult();
            double Doublevalue = value.doubleValue();


            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + invOrPrebillPreviewInfo.getRequestCoreId());
            }
            return Doublevalue;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }


    }

}