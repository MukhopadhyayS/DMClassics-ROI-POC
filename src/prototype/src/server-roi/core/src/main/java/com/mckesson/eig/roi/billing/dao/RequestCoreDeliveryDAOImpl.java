package com.mckesson.eig.roi.billing.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

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
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;


public class RequestCoreDeliveryDAOImpl
extends ROIDAOImpl
implements RequestCoreDeliveryDAO {

    private static final Log LOG = LogFactory.getLogger(RequestCoreDeliveryDAOImpl.class);
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
			Session session = getSessionFactory().getCurrentSession();
			String queryString = session.getNamedQuery("createRequestCoreDelivery").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
			query.addScalar("requestCoreDeliveryId", StandardBasicTypes.LONG);
			query.setParameter("requestCoreId", requestCoreId, StandardBasicTypes.LONG);
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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("deleteRequestCoreDelivery");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestIdForRequestCoreDelivery")
                                        .getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);
            query.addScalar("requestId", StandardBasicTypes.LONG);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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

			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_Pages");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);
			query.setParameter("roiPagesId", roiPages.getPageSeq(), StandardBasicTypes.LONG);
			query.setParameter("selfPayEncounter", roiPages.isSelfPayEncounter(), StandardBasicTypes.BOOLEAN);

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

			Session session = getSessionFactory().getCurrentSession();

			String queryString = session.getNamedQuery("retrievePagesAndReleasedCount")
			                            .getQueryString();
			SQLQuery sqlQuery = session.createSQLQuery(queryString);
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
    			    sqlQuery.executeUpdate();

    			}
			}

			Query query = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_Pages");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges");
			query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChagesId, StandardBasicTypes.LONG);
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			String queryString = session.getNamedQuery("retrieveNonReleasedInvoices").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.addScalar("invoiceId", StandardBasicTypes.LONG);
			query.setParameter("requestCoreId", requestCoreId);

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
	        Session session = getSessionFactory().getCurrentSession();
	        String queryString = session.getNamedQuery("isRequestContainsNonReleasedInvoices")
	                                    .getQueryString();
	        SQLQuery query = session.createSQLQuery(queryString);
	        query.addScalar("isInvoiced", StandardBasicTypes.BOOLEAN);
	        query.setParameter("requestCoreId", requestCoreId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			String queryString = session.getNamedQuery("retrieveInvoicesForRelease").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.addScalar("invoiceId", StandardBasicTypes.LONG);
			query.setParameter("releaseId", releaseId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			Query query;
			if (isReleased) {
				query = session.getNamedQuery("updateInvoiceReleased");
			}  else {
				query = session.getNamedQuery("updateInvoiceReleaseCancelled");
			}
			query.setParameter("invoiceId", invoiceId, StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryCharges");

            query.setParameter("requestCoreId", invoice.getRequestCoreId(), StandardBasicTypes.LONG);
            query.setParameter("createdDate", invoice.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", invoice.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDate", invoice.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", invoice.getModifiedBy(), StandardBasicTypes.LONG);
            query.setParameter("isReleased", invoice.getIsReleased(), StandardBasicTypes.BOOLEAN);
            query.setParameter("previouslyReleasedCost", invoice.getPreviouslyReleasedCost(),
                                StandardBasicTypes.DOUBLE);
            query.setParameter("totalPagesReleased", invoice.getTotalPagesReleased() ,
                                StandardBasicTypes.INTEGER);
            query.setParameter("totalPages", invoice.getTotalPages() , StandardBasicTypes.INTEGER);
            query.setParameter("salesTaxAmount", invoice.getSalesTaxAmount() , StandardBasicTypes.DOUBLE);
            query.setParameter("salesTaxPercentage", invoice.getSalesTaxPercentage(),
                                StandardBasicTypes.DOUBLE);
            query.setParameter("letterTemplateName", invoice.getLetterTemplateName(),
                                StandardBasicTypes.STRING);
            query.setParameter("requestStatus", invoice.getRequestStatus(), StandardBasicTypes.STRING);
            query.setParameter("requestDate", invoice.getRequestDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("letterTemplateFileId", invoice.getLetterTemplateFileId(),
                               StandardBasicTypes.LONG);
            query.setParameter("type", invoice.getType(), StandardBasicTypes.STRING);
            query.setParameter("invoicePrebillDate", invoice.getInvoicePrebillDate(),
                                StandardBasicTypes.TIMESTAMP);
            query.setParameter("invoiceDueDate", invoice.getInvoiceDueDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("resendDate", invoice.getResendDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("outputMethod", invoice.getOutputMethod(), StandardBasicTypes.STRING);
            query.setParameter("queuePassword", invoice.getQueuePassword(), StandardBasicTypes.STRING);
            query.setParameter("overwriteDueDate", invoice.getOverwriteDueDate(),
                                StandardBasicTypes.BOOLEAN);
            query.setParameter("invoiceSalesTax", invoice.getInvoiceSalesTax(), StandardBasicTypes.DOUBLE);
            query.setParameter("baseCharge", invoice.getBaseCharge(), StandardBasicTypes.DOUBLE);
            query.setParameter("balanceDue", invoice.getBalanceDue(), StandardBasicTypes.DOUBLE);
            query.setParameter("invoiceBillingLocCode", invoice.getInvoiceBillingLocCode(),
                                StandardBasicTypes.STRING);
            query.setParameter("invoiceBillinglocName", invoice.getInvoiceBillinglocName(),
                                StandardBasicTypes.STRING);
            query.setParameter("invoiceBalanceDue", invoice.getInvoiceBalanceDue(),
                                StandardBasicTypes.DOUBLE);
            query.setParameter("releaseDate", invoice.getReleaseDate() , StandardBasicTypes.TIMESTAMP);
            query.setParameter("billingType", invoice.getBillingType() , StandardBasicTypes.STRING);
            query.setParameter("unbillable", invoice.isUnbillable() , StandardBasicTypes.BOOLEAN);
            query.setParameter("unbillableAmount", invoice.getUnbillableAmount(),
                               StandardBasicTypes.DOUBLE);
            query.setParameter("applySalesTax", invoice.getApplySalesTax() , StandardBasicTypes.BOOLEAN);
            query.setParameter("notes", invoice.getNotes() , StandardBasicTypes.STRING);
            query.setParameter("invoiceDueDays", invoice.getInvoiceDueDays() , StandardBasicTypes.LONG);
            query.setParameter("prebillStatus", invoice.getPrebillStatus() , StandardBasicTypes.STRING);
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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryChargesDocument");

            query.setParameter("requestCoreDeliveryChargesId",requestCoreDeliveryChargesDocument.getRequestCoreDeliveryChargesId(), StandardBasicTypes.LONG);
            query.setParameter("amount", requestCoreDeliveryChargesDocument.getAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("copies", requestCoreDeliveryChargesDocument.getCopies(), StandardBasicTypes.INTEGER);
            query.setParameter("billingtierName", requestCoreDeliveryChargesDocument.getBillingTierName(), StandardBasicTypes.STRING);
            query.setParameter("totalPages", requestCoreDeliveryChargesDocument.getTotalPages(), StandardBasicTypes.INTEGER);
            query.setParameter("pages", requestCoreDeliveryChargesDocument.getPages(), StandardBasicTypes.INTEGER);
            query.setParameter("billingtierId", requestCoreDeliveryChargesDocument.getBillingtierId(), StandardBasicTypes.STRING);
            query.setParameter("releaseCount", requestCoreDeliveryChargesDocument.getReleaseCount(), StandardBasicTypes.INTEGER);
            query.setParameter("isElectronic", requestCoreDeliveryChargesDocument.getIsElectronic(), StandardBasicTypes.BOOLEAN);
            query.setParameter("removeBasecharge", requestCoreDeliveryChargesDocument.getRemoveBaseCharge(), StandardBasicTypes.BOOLEAN);
            query.setParameter("hasSalesTax", requestCoreDeliveryChargesDocument.getHasSalesTax(), StandardBasicTypes.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreDeliveryChargesDocument.getSalesTaxAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("createdDate", requestCoreDeliveryChargesDocument.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesDocument.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesDocument.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesDocument.getModifiedBy(), StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryChargesFee");

            query.setParameter("requestCoreDeliveryChargesId",requestCoreDeliveryChargesFee.getRequestCoreDeliveryChargesId(), StandardBasicTypes.LONG);
            query.setParameter("amount", requestCoreDeliveryChargesFee.getAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("isCustomFee", requestCoreDeliveryChargesFee.getIsCustomFee(), StandardBasicTypes.BOOLEAN);
            query.setParameter("feeType", requestCoreDeliveryChargesFee.getFeeType(), StandardBasicTypes.STRING);
            query.setParameter("hasSalesTax", requestCoreDeliveryChargesFee.getHasSalesTax(), StandardBasicTypes.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreDeliveryChargesFee.getSalesTaxAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("createdDate", requestCoreDeliveryChargesFee.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesFee.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesFee.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesFee.getModifiedBy(), StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryChargesShipping");

            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesShipping.getRequestCoreDeliveryChargesId(), StandardBasicTypes.LONG);
            query.setParameter("shippingCharge", requestCoreDeliveryChargesShipping.getShippingCharge(), StandardBasicTypes.DOUBLE);
            query.setParameter("postalCode", requestCoreDeliveryChargesShipping.getPostalCode(), StandardBasicTypes.STRING);
            query.setParameter("addressType", requestCoreDeliveryChargesShipping.getAddressType(), StandardBasicTypes.STRING);
            query.setParameter("state", requestCoreDeliveryChargesShipping.getState(), StandardBasicTypes.STRING);
            query.setParameter("shippingUrl",requestCoreDeliveryChargesShipping.getShippingUrl(), StandardBasicTypes.STRING);
            query.setParameter("address1", requestCoreDeliveryChargesShipping.getAddress1(), StandardBasicTypes.STRING);
            query.setParameter("address2", requestCoreDeliveryChargesShipping.getAddress2(), StandardBasicTypes.STRING);
            query.setParameter("address3", requestCoreDeliveryChargesShipping.getAddress3(), StandardBasicTypes.STRING);
            query.setParameter("shippingWeight", requestCoreDeliveryChargesShipping.getShippingWeight(),StandardBasicTypes.DOUBLE);
            query.setParameter("trackingNumber", requestCoreDeliveryChargesShipping.getTrackingNumber(), StandardBasicTypes.STRING);
            query.setParameter("city", requestCoreDeliveryChargesShipping.getCity(), StandardBasicTypes.STRING);
            query.setParameter("countryCode", requestCoreDeliveryChargesShipping.getCountryCode(), StandardBasicTypes.STRING);
            query.setParameter("countryName", requestCoreDeliveryChargesShipping.getCountryName(), StandardBasicTypes.STRING);
            query.setParameter("willReleaseShipped", requestCoreDeliveryChargesShipping.isWillReleaseShipped(), StandardBasicTypes.BOOLEAN);
            query.setParameter("shippingMethod", requestCoreDeliveryChargesShipping.getShippingMethod(), StandardBasicTypes.STRING);
            query.setParameter("outputMethod", requestCoreDeliveryChargesShipping.getOutputMethod(), StandardBasicTypes.STRING);
            query.setParameter("shippingMethodId", requestCoreDeliveryChargesShipping.getShippingMethodId(), StandardBasicTypes.LONG);
            query.setParameter("nonPrintableAttachmentQueue", requestCoreDeliveryChargesShipping.getNonPrintableAttachmentQueue(), StandardBasicTypes.STRING);
            query.setParameter("createdDate", requestCoreDeliveryChargesShipping.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesShipping.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesShipping.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesShipping.getModifiedBy(), StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryCharges")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreId", requestCoreId, StandardBasicTypes.LONG);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("isReleased", StandardBasicTypes.BOOLEAN);
            query.addScalar("previouslyReleasedCost", StandardBasicTypes.DOUBLE);
            query.addScalar("releaseCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalRequestCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalPagesReleased", StandardBasicTypes.INTEGER);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("salesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("salesTaxPercentage", StandardBasicTypes.DOUBLE);
            query.addScalar("letterTemplateName", StandardBasicTypes.STRING);
            query.addScalar("requestStatus", StandardBasicTypes.STRING);
            query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("letterTemplateFileId", StandardBasicTypes.LONG);
            query.addScalar("type", StandardBasicTypes.STRING);
            query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("queuePassword", StandardBasicTypes.STRING);
            query.addScalar("overwriteDueDate", StandardBasicTypes.BOOLEAN);
            query.addScalar("invoiceSalesTax", StandardBasicTypes.DOUBLE);
            query.addScalar("baseCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("balanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("invoiceBillingLocCode", StandardBasicTypes.STRING);
            query.addScalar("invoiceBillinglocName", StandardBasicTypes.STRING);
            query.addScalar("invoiceBalanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("creditAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("debitAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("applySalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("notes", StandardBasicTypes.STRING);
            query.addScalar("unbillable", StandardBasicTypes.BOOLEAN);
            query.addScalar("unbillableAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("invoiceDueDays", StandardBasicTypes.LONG);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
            query.addScalar("prebillStatus", StandardBasicTypes.STRING);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesShipping").getQueryString();;
            SQLQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesId", ids);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestCoreDeliveryChargesId" ,StandardBasicTypes.LONG);
            query.addScalar("shippingCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("postalCode", StandardBasicTypes.STRING);
            query.addScalar("addressType", StandardBasicTypes.STRING);
            query.addScalar("state", StandardBasicTypes.STRING);
            query.addScalar("shippingUrl", StandardBasicTypes.STRING);
            query.addScalar("address1", StandardBasicTypes.STRING);
            query.addScalar("address2", StandardBasicTypes.STRING);
            query.addScalar("address3", StandardBasicTypes.STRING);
            query.addScalar("shippingWeight", StandardBasicTypes.DOUBLE);
            query.addScalar("trackingNumber", StandardBasicTypes.STRING);
            query.addScalar("city", StandardBasicTypes.STRING);
            query.addScalar("countryCode", StandardBasicTypes.STRING);
            query.addScalar("countryName", StandardBasicTypes.STRING);
            query.addScalar("willReleaseShipped", StandardBasicTypes.BOOLEAN);
            query.addScalar("shippingMethod", StandardBasicTypes.STRING);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("shippingMethodId", StandardBasicTypes.LONG);
            query.addScalar("nonPrintableAttachmentQueue", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesAdjustmentPayment").getQueryString();;
            SQLQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesIds", ids);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestorId",StandardBasicTypes.LONG);
            query.addScalar("requestCoreDeliveryChargesId",StandardBasicTypes.LONG);
            query.addScalar("invoiceAppliedAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("baseAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("totalUnappliedAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentMode", StandardBasicTypes.STRING);
            query.addScalar("description", StandardBasicTypes.STRING);
            query.addScalar("paymentDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("transactionType", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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

    /*public void deleteRequestCoreDeliveryChargesAdjustmentPayment(long  requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreChargesAdjustmentPayment(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try{
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesAdjustmentPayment");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, StandardBasicTypes.LONG);
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
    }*/

    @Override
    public void deleteRequestCoreDeliveryCharges(long requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreDeliveryCharges(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try{
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryCharges");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, StandardBasicTypes.LONG);
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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesFee");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, StandardBasicTypes.LONG);
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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesShipping");
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, StandardBasicTypes.LONG);
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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesDocument");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, StandardBasicTypes.LONG);
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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesInvoicePatients");
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, StandardBasicTypes.LONG);
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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("createInvoicePatients");
			query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryInvoicePatients.getRequestCoreDeliveryChargesId(), StandardBasicTypes.LONG);
			query.setParameter("requestHpfPatientsId", requestCoreDeliveryInvoicePatients.getRequestHpfPatientsId(), StandardBasicTypes.LONG);
			query.setParameter("requestNonHpfPatientsId", requestCoreDeliveryInvoicePatients.getRequestNonHpfPatientsId(), StandardBasicTypes.LONG);
			query.setParameter("createdDt", requestCoreDeliveryInvoicePatients.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
			query.setParameter("createdBy", requestCoreDeliveryInvoicePatients.getCreatedBy(), StandardBasicTypes.LONG);
			query.setParameter("modifiedDt", requestCoreDeliveryInvoicePatients.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
			query.setParameter("modifiedBy", requestCoreDeliveryInvoicePatients.getModifiedBy(), StandardBasicTypes.LONG);
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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementalAttachmentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);
			query.setParameter("roiSupplementalAttachmentId", supplementalAttachmentSeq, StandardBasicTypes.LONG);

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

		    Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveSupplementalAttachmentAndReleasedCount")
                                        .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
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
                    sqlQuery.executeUpdate();

                }
            }

			Query query = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementalAttachmentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementalDocumentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);
			query.setParameter("roiSupplementalDocumentId", supplementalDocumentSeq, StandardBasicTypes.LONG);

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

			Session session = getSessionFactory().getCurrentSession();
			String queryString = session.getNamedQuery("retrieveSupplementalDocumentAndReleasedCount")
                                         .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
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
                    sqlQuery.executeUpdate();

                }
            }

			Query query = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementalDocumentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementarityAttachmentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);
			query.setParameter("roiSupplementarityAttachmentId", supplementarityAttachmentSeq, StandardBasicTypes.LONG);

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

			Session session = getSessionFactory().getCurrentSession();
			String queryString = session.getNamedQuery("retrieveSupplementarityAttachmentAndReleasedCount")
                                        .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
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
                    sqlQuery.executeUpdate();

                }
            }

			Query query = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementarityAttachmentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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
			Session session = getSessionFactory().getCurrentSession();
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementarityDocumentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);
			query.setParameter("roiSupplementarityDocumentId", supplementarityDocumentSeq, StandardBasicTypes.LONG);

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

			Session session = getSessionFactory().getCurrentSession();
			String queryString = session.getNamedQuery("retrieveSupplementarityDocumentAndReleasedCount")
                    .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
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

			Query query = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_SupplementarityDocumentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createSalesTaxReason");

            query.setParameter("requestCoreChargesSeq",salesTaxReason.getRequestCoreChargesSeq(), StandardBasicTypes.LONG);
            query.setParameter("reason", salesTaxReason.getReason(), StandardBasicTypes.STRING);
            query.setParameter("reasonDate", salesTaxReason.getReasonDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdDate", salesTaxReason.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", salesTaxReason.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDate", salesTaxReason.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", salesTaxReason.getModifiedBy(), StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveSalesTaxReason").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, StandardBasicTypes.LONG);
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("reason", StandardBasicTypes.STRING);
            query.addScalar("reasonDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesInvoice")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesId", rcdChargesSeq, StandardBasicTypes.LONG);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestCoreId", StandardBasicTypes.LONG);
            query.addScalar("isReleased", StandardBasicTypes.BOOLEAN);
            query.addScalar("previouslyReleasedCost", StandardBasicTypes.DOUBLE);
            query.addScalar("releaseCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalRequestCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalPagesReleased", StandardBasicTypes.INTEGER);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("salesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("salesTaxPercentage", StandardBasicTypes.DOUBLE);
            query.addScalar("letterTemplateName", StandardBasicTypes.STRING);
            query.addScalar("requestStatus", StandardBasicTypes.STRING);
            query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("letterTemplateFileId", StandardBasicTypes.LONG);
            query.addScalar("type", StandardBasicTypes.STRING);
            query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("queuePassword", StandardBasicTypes.STRING);
            query.addScalar("overwriteDueDate", StandardBasicTypes.BOOLEAN);
            query.addScalar("invoiceSalesTax", StandardBasicTypes.DOUBLE);
            query.addScalar("baseCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("balanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("invoiceBillingLocCode", StandardBasicTypes.STRING);
            query.addScalar("invoiceBillinglocName", StandardBasicTypes.STRING);
            query.addScalar("invoiceBalanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("creditAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("debitAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("applySalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("notes", StandardBasicTypes.STRING);
            query.addScalar("invoiceDueDays", StandardBasicTypes.LONG);
            query.addScalar("requestCreditAdjustment", StandardBasicTypes.DOUBLE);
            query.addScalar("requestDebitAdjustment", StandardBasicTypes.DOUBLE);
            query.addScalar("requestBalanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("requestpayment", StandardBasicTypes.DOUBLE);
            query.addScalar("unbillable", StandardBasicTypes.BOOLEAN);
            query.addScalar("unbillableAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesFee").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            Set<RequestCoreDeliveryChargesFee> rcdcFeeSet = new LinkedHashSet<RequestCoreDeliveryChargesFee>();

            query.setParameter("rcdChargesSeq", rcdChargesId, StandardBasicTypes.LONG);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("isCustomFee", StandardBasicTypes.BOOLEAN);
            query.addScalar("feeType", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
            query.addScalar("hasSalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("SalesTaxAmount", StandardBasicTypes.DOUBLE);

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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDocument").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            Set<RequestCoreDeliveryChargesDocument> rcdcDocumentSet = new LinkedHashSet<RequestCoreDeliveryChargesDocument>();

            query.setParameter("rcdChargesSeq", rcdChargesId, StandardBasicTypes.LONG);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("copies", StandardBasicTypes.INTEGER);
            query.addScalar("billingTierName", StandardBasicTypes.STRING);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("pages", StandardBasicTypes.INTEGER);
            query.addScalar("billingtierId", StandardBasicTypes.STRING);
            query.addScalar("releaseCount", StandardBasicTypes.INTEGER);
            query.addScalar("isElectronic", StandardBasicTypes.BOOLEAN);
            query.addScalar("removeBaseCharge", StandardBasicTypes.BOOLEAN);
            query.addScalar("hasSalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("SalesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteSalesTaxReason");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveInvoicePatients").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("requestCoreDeliveryChargesId", invoiceIds, StandardBasicTypes.LONG);
            query.addScalar("requestHpfPatientsId",StandardBasicTypes.LONG);
            query.addScalar("requestNonHpfPatientsId",StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesFee")
                                        .getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("isCustomFee", StandardBasicTypes.BOOLEAN);
            query.addScalar("feeType", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
            query.addScalar("hasSalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("SalesTaxAmount", StandardBasicTypes.DOUBLE);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDistinctFee")
                                        .getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("feeType", StandardBasicTypes.STRING);

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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDocument")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("copies", StandardBasicTypes.INTEGER);
            query.addScalar("billingTierName", StandardBasicTypes.STRING);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("pages", StandardBasicTypes.INTEGER);
            query.addScalar("billingtierId", StandardBasicTypes.STRING);
            query.addScalar("releaseCount", StandardBasicTypes.INTEGER);
            query.addScalar("isElectronic", StandardBasicTypes.BOOLEAN);
            query.addScalar("removeBaseCharge", StandardBasicTypes.BOOLEAN);
            query.addScalar("hasSalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("SalesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);

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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveDeliveryChargesDistinctDocument")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("billingTierName", StandardBasicTypes.STRING);

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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesInvoice")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesId", rcdChargesSeq);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestCoreId", StandardBasicTypes.LONG);
            query.addScalar("isReleased", StandardBasicTypes.BOOLEAN);
            query.addScalar("previouslyReleasedCost", StandardBasicTypes.DOUBLE);
            query.addScalar("releaseCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalRequestCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalPagesReleased", StandardBasicTypes.INTEGER);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("salesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("salesTaxPercentage", StandardBasicTypes.DOUBLE);
            query.addScalar("letterTemplateName", StandardBasicTypes.STRING);
            query.addScalar("requestStatus", StandardBasicTypes.STRING);
            query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("letterTemplateFileId", StandardBasicTypes.LONG);
            query.addScalar("type", StandardBasicTypes.STRING);
            query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("queuePassword", StandardBasicTypes.STRING);
            query.addScalar("overwriteDueDate", StandardBasicTypes.BOOLEAN);
            query.addScalar("invoiceSalesTax", StandardBasicTypes.DOUBLE);
            query.addScalar("baseCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("balanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("invoiceBillingLocCode", StandardBasicTypes.STRING);
            query.addScalar("invoiceBillinglocName", StandardBasicTypes.STRING);
            query.addScalar("invoiceBalanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("creditAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("debitAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("applySalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("notes", StandardBasicTypes.STRING);
            query.addScalar("invoiceDueDays", StandardBasicTypes.LONG);
            query.addScalar("creator", StandardBasicTypes.STRING);

            query.addScalar("requestCreditAdjustment", StandardBasicTypes.DOUBLE);
            query.addScalar("requestDebitAdjustment", StandardBasicTypes.DOUBLE);
            query.addScalar("requestBalanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("requestpayment", StandardBasicTypes.DOUBLE);

            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesAdjustmentPayment")
                                        .getQueryString();;
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesIds", requestCoreDeliveryChargesId);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestCoreDeliveryChargesId", StandardBasicTypes.LONG);
            query.addScalar("invoiceAppliedAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentMode", StandardBasicTypes.STRING);
            query.addScalar("description", StandardBasicTypes.STRING);
            query.addScalar("paymentDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("transactionType", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesShipping").getQueryString();;
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestCoreDeliveryChargesId", StandardBasicTypes.LONG);
            query.addScalar("shippingCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("postalCode", StandardBasicTypes.STRING);
            query.addScalar("addressType", StandardBasicTypes.STRING);
            query.addScalar("state", StandardBasicTypes.STRING);
            query.addScalar("shippingUrl", StandardBasicTypes.STRING);
            query.addScalar("address1", StandardBasicTypes.STRING);
            query.addScalar("address2", StandardBasicTypes.STRING);
            query.addScalar("address3", StandardBasicTypes.STRING);
            query.addScalar("shippingWeight", StandardBasicTypes.DOUBLE);
            query.addScalar("trackingNumber", StandardBasicTypes.STRING);
            query.addScalar("city", StandardBasicTypes.STRING);
            query.addScalar("countryCode", StandardBasicTypes.STRING);
            query.addScalar("countryName", StandardBasicTypes.STRING);
            query.addScalar("willReleaseShipped", StandardBasicTypes.BOOLEAN);
            query.addScalar("shippingMethod", StandardBasicTypes.STRING);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("shippingMethodId", StandardBasicTypes.LONG);
            query.addScalar("nonPrintableAttachmentQueue", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
     * This method is used to update the RequestCoreDeliveryCharges
     * @param requestCoreDeliveryChargesId
     * @param updatedInvoicedAmount
     */
    /*@Override
      public void updateRequestCoreDeliveryCharges(long requestCoreDeliveryChargesId, double updatedInvoicedAmount, double paymentAmount, double creditAdjAmount,
            double debitAdjAmount) {
        final String logSM = "updateRequestCoreDeliveryCharges(requestCoreDeliveryChargesId, updatedInvoicedAmount, paymentAmouont, creditAdjAmount, debitAdjamount)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId +updatedInvoicedAmount);
        }
        try {
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("updateRequestCoreDeliveryCharges");

            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, StandardBasicTypes.LONG);
            query.setParameter("invoicedAmount",updatedInvoicedAmount,StandardBasicTypes.DOUBLE);
            query.setParameter("paymentAmount", paymentAmount, StandardBasicTypes.DOUBLE);
            query.setParameter("creditAdjAmount", creditAdjAmount, StandardBasicTypes.DOUBLE);
            query.setParameter("debitAdjAmount", debitAdjAmount, StandardBasicTypes.DOUBLE);

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

    }*/

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

            final Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("CreateCoverLetterCore");
//            query.setParameter("requestCoreChargesId", coverLetterCore.getRequestCoreChargesId(), StandardBasicTypes.LONG);
            query.setParameter("requestId", coverLetterCore.getRequestId(), StandardBasicTypes.LONG);
            query.setParameter("requestorId", coverLetterCore.getRequestorId(), StandardBasicTypes.LONG);
            query.setParameter("letterTemplateId", coverLetterCore.getLetterTemplateId(), StandardBasicTypes.LONG);
            query.setParameter("note", coverLetterCore.getNotes(), StandardBasicTypes.STRING);
            query.setParameter("templateType", coverLetterCore.getLetterType(), StandardBasicTypes.STRING);
            query.setParameter("queuePassword", coverLetterCore.getQueuePassword(), StandardBasicTypes.STRING);
            query.setParameter("modifiedDt", coverLetterCore.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", coverLetterCore.getModifiedBy(), StandardBasicTypes.LONG);
            query.setParameter("createdDt", coverLetterCore.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", coverLetterCore.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("recordVersion", coverLetterCore.getRecordVersion(), StandardBasicTypes.INTEGER);

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

            Session session = getSessionFactory().getCurrentSession();

            // deletes the hpf patients corresponding for the given cover letter
            Query query = session.getNamedQuery("deleteCoverLetterRequestHpfPatient");
            query.setParameter("coverLetterId", coverLetterId, StandardBasicTypes.LONG);
            query.executeUpdate();

            // deletes the non-hpf patients corresponding for the given cover letter
            query = session.getNamedQuery("deleteCoverLetterRequestSupplementalPatient");
            query.setParameter("coverLetterId", coverLetterId, StandardBasicTypes.LONG);
            query.executeUpdate();

            // deletes the cover letter
            query = session.getNamedQuery("deleteCoverLetter");
            query.setParameter("coverLetterId", coverLetterId, StandardBasicTypes.LONG);
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
            final Session session = getSessionFactory().getCurrentSession();
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

            final Session session = getSessionFactory().getCurrentSession();
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

            final Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveCoverLetterCore").getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", StandardBasicTypes.LONG);
//            query.addScalar("requestCoreChargesId", StandardBasicTypes.LONG);
            query.addScalar("requestId", StandardBasicTypes.LONG);
            query.addScalar("requestorId",StandardBasicTypes.LONG);
            query.addScalar("letterTemplateId", StandardBasicTypes.LONG);
            query.addScalar("notes", StandardBasicTypes.STRING);
            query.addScalar("letterType", StandardBasicTypes.STRING);
            query.addScalar("queuePassword", StandardBasicTypes.STRING);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);

            query.setParameter("coverLetterId", coverLetterId);
            query.setResultTransformer(Transformers.aliasToBean(CoverLetterCore.class));
            CoverLetterCore coverLetter = (CoverLetterCore) query.uniqueResult();

            // retrieves the list of hpf patients Sequences
            queryString = session.getNamedQuery("retrieveCoverLetterRequestHpfPatient")
                                 .getQueryString();

            query = session.createSQLQuery(queryString);
            setScalarsForRequestPatient(query);

            query.setParameter("coverLetterId", coverLetterId, StandardBasicTypes.LONG);
            List<RequestPatient> requestHpfPatients = query.list();
            coverLetter.setRequestHpfPatients(requestHpfPatients);

            // retrieves the list of nonhpf patients Sequences
            queryString = session.getNamedQuery("retrieveCoverLetterRequestSupplementalPatient")
                                 .getQueryString();

            query = session.createSQLQuery(queryString);
            setScalarsForRequestPatient(query);

            query.setParameter("coverLetterId", coverLetterId, StandardBasicTypes.LONG);
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
    private void setScalarsForRequestPatient(SQLQuery query) {

        query.addScalar("patientSeq", StandardBasicTypes.LONG);
        query.addScalar("requestId", StandardBasicTypes.LONG);
        query.addScalar("supplementalId", StandardBasicTypes.LONG);
        query.addScalar("mrn", StandardBasicTypes.STRING);
        query.addScalar("facility", StandardBasicTypes.STRING);
        query.addScalar("name", StandardBasicTypes.STRING);
        query.addScalar("gender", StandardBasicTypes.STRING);
        query.addScalar("epn", StandardBasicTypes.STRING);
        query.addScalar("ssn", StandardBasicTypes.STRING);
        query.addScalar("patientLocked", StandardBasicTypes.BOOLEAN);
        query.addScalar("encounterLocked", StandardBasicTypes.BOOLEAN);
        query.addScalar("vip", StandardBasicTypes.BOOLEAN);
        query.addScalar("hpf", StandardBasicTypes.BOOLEAN);
        query.addScalar("dob", StandardBasicTypes.TIMESTAMP);

        query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
        query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
        query.addScalar("modifiedBy", StandardBasicTypes.INTEGER);
        query.addScalar("createdBy", StandardBasicTypes.INTEGER);

        query.setResultTransformer(Transformers.aliasToBean(RequestPatient.class));
    }

   /* @Override
    public RegeneratedInvoiceDetails retrieveRegeneratedInvoiceUsingRegeneratedId(long regInvoiceId) {
        final String logSM = "retrieveRegeneratedInvoices(rcdChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + regInvoiceId);
        }
        try{
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRegeneratedInvoiceUsingRegeneratedId")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("regeneratedInvoiceId", regInvoiceId, StandardBasicTypes.LONG);

            query.addScalar("resendDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("requestCoreDeliveryChargesId", StandardBasicTypes.LONG);
            query.addScalar("letterTemplateName", StandardBasicTypes.STRING);
            query.addScalar("letterTemplateFileId", StandardBasicTypes.LONG);
            query.addScalar("requestBalanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("queuePassword", StandardBasicTypes.STRING);
            query.addScalar("requestCreditAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("requestDebitAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("requestPaymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("requestStatus", StandardBasicTypes.STRING);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("amountPaid", StandardBasicTypes.DOUBLE);
            query.addScalar("balanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("baseCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("originalBalance", StandardBasicTypes.DOUBLE);

            query.setResultTransformer(Transformers.aliasToBean(RegeneratedInvoiceDetails.class));

            List<RegeneratedInvoiceDetails> invoiceDetailsList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return (invoiceDetailsList != null && invoiceDetailsList.size() > 0) ? invoiceDetailsList.get(0) : null;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }*/

    @Override
    public List<PastInvoice> retrievePastInvoices(long requestId) {

        final String logSM = "retrievePastInvoices(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId" + requestId);
        }
        try{

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveAllPastInvoices").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);

            query.addScalar("invoiceId", StandardBasicTypes.LONG);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("createdDate", StandardBasicTypes.STRING);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveReleaseChargeHistory").getQueryString();;
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);

            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("totalShippingCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("totalSalesTax", StandardBasicTypes.DOUBLE);
            query.addScalar("totalFeeCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("totalDocumentCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("unbillable", StandardBasicTypes.BOOLEAN);

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

            Session session = getSessionFactory().getCurrentSession();

            if (outputProperties.isForInvoice()) {

                String queryString = session.getNamedQuery("updateInvoiceProperty").getQueryString();;
                SQLQuery query = session.createSQLQuery(queryString);

                query.setParameter("invoiceId", outputProperties.getInvoiceId());
                query.setParameter("queuePassword", outputProperties.getQueuePassword());
                query.setParameter("outputMethod", outputProperties.getOutputMethod());

                query.executeUpdate();
            }

            if (outputProperties.isForLetter()) {

                String queryString = session.getNamedQuery("updateLetterProperty").getQueryString();;
                SQLQuery query = session.createSQLQuery(queryString);

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

            Session session = getSessionFactory().getCurrentSession();

            String queryString = session.getNamedQuery("updateReleaseOutputProperty")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

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

            Session session = getSessionFactory().getCurrentSession();

            String queryString = session.getNamedQuery("deleteInvoiceAutoAdjEvent")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("description1", description1);
            query.setParameter("description2", description2);
            query.setParameter("invoiceId", invoiceId);
            int executeUpdate = query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:No Of Rows Deleted:" + executeUpdate);
            }

        } catch (DataAccessException e) {

            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATA_ACCESS_EXCEPTION);
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }


    /*@Override
    public List<RequestCoreCharges> retrieveRequestCharges(List<Long> invoiceIds) {

        final String logSM = "retrieveRequestCharges(invoiceIds)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: InvoiceIds" + invoiceIds);
        }

        try {

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestChargesByInvoice")
                                        .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameterList("invoiceIds", invoiceIds);

            sqlQuery.addScalar("requestCoreSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("balanceDue", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("invoicesBalance", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("creditAdjustmentAmount", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("debitAdjustmentAmount", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);

            sqlQuery.setResultTransformer(Transformers.aliasToBean(RequestCoreCharges.class));
            @SuppressWarnings("unchecked")
            List<RequestCoreCharges> requestCharges = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return requestCharges;

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
*/
   /* @Override
    public void updateRequestCharges(List<RequestCoreCharges> requestCharges) {

        final String logSM = "updateRequestCharges(requestCharges)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCharges);
        }

        try {

            final Session session = getSessionFactory().getCurrentSession();
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

                    RequestCoreCharges requestCharge = (RequestCoreCharges) object;

                    int index = 1;
                    pStmt.setDouble(index++, requestCharge.getPaymentAmount());
                    pStmt.setDouble(index++,  requestCharge.getCreditAdjustmentAmount());
                    pStmt.setDouble(index++, requestCharge.getDebitAdjustmentAmount());
                    pStmt.setDouble(index++, requestCharge.getBalanceDue());
                    pStmt.setTimestamp(index++, getSQLTimeStamp(requestCharge.getModifiedDt()));
                    pStmt.setLong(index++, requestCharge.getModifiedBy());
                    pStmt.setLong(index++, requestCharge.getRequestCoreSeq());

                }
            };

            String query = session.getNamedQuery("updateRequestBalances").getQueryString();
            processor.execute(requestCharges, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
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
    }*/

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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("updateInvoiceBalance");

            query.setParameter("invoiceId", invoiceId, StandardBasicTypes.LONG);
            query.setParameter("invoiceBalance", invoiceBalance, StandardBasicTypes.DOUBLE);
            query.setParameter("modifiedDt", getSQLTimeStamp(date), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(), StandardBasicTypes.INTEGER);
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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("updatePrebillStatusInvoice");

            query.setParameter("reqCoreSeq", invoice.getRequestCoreId(), StandardBasicTypes.LONG);
            query.setParameter("prebillStatus", invoice.getPrebillStatus(), StandardBasicTypes.STRING);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreDeliveryChargesPrebill")
                                        .getQueryString();;
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreId", requestCoreId, StandardBasicTypes.LONG);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("isReleased", StandardBasicTypes.BOOLEAN);
            query.addScalar("previouslyReleasedCost", StandardBasicTypes.DOUBLE);
            query.addScalar("releaseCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalRequestCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalPagesReleased", StandardBasicTypes.INTEGER);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("salesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("salesTaxPercentage", StandardBasicTypes.DOUBLE);
            query.addScalar("letterTemplateName", StandardBasicTypes.STRING);
            query.addScalar("requestStatus", StandardBasicTypes.STRING);
            query.addScalar("requestDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("letterTemplateFileId", StandardBasicTypes.LONG);
            query.addScalar("type", StandardBasicTypes.STRING);
            query.addScalar("invoicePrebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("invoiceDueDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("resendDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("queuePassword", StandardBasicTypes.STRING);
            query.addScalar("overwriteDueDate", StandardBasicTypes.BOOLEAN);
            query.addScalar("invoiceSalesTax", StandardBasicTypes.DOUBLE);
            query.addScalar("baseCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("balanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("invoiceBillingLocCode", StandardBasicTypes.STRING);
            query.addScalar("invoiceBillinglocName", StandardBasicTypes.STRING);
            query.addScalar("invoiceBalanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("creditAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("debitAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("applySalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("notes", StandardBasicTypes.STRING);
            query.addScalar("unbillable", StandardBasicTypes.BOOLEAN);
            query.addScalar("unbillableAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("invoiceDueDays", StandardBasicTypes.LONG);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
            query.addScalar("prebillStatus", StandardBasicTypes.STRING);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = null;
            if("\"Summary - Requestor Type\"".equalsIgnoreCase(resultType))
               queryString = session.getNamedQuery("retrievePrebillReportDetailsFirstLevelWithRequestorName").getQueryString();
            else if("\"Detailed - Requestor name\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retrievePrebillReportDetailsSecondLevelWithRequestorName").getQueryString();
            else
                queryString = session.getNamedQuery("retrievePrebillReportDetailsThirdLevelWithRequestorName").getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility,StandardBasicTypes.STRING);
            query.setParameterList("requestorTypeName", requestorTypeName,StandardBasicTypes.STRING);
            query.setParameterList("requestStatus",reqStatus,StandardBasicTypes.STRING);
            query.setParameter("balanceCriteria",balanceCriterion,StandardBasicTypes.STRING);
            query.setParameter("requestorName","%" + getSpecialCharSearchStr(requestorName) + "%",StandardBasicTypes.STRING);
            query.setParameter("balanceDue",balance,StandardBasicTypes.DOUBLE);
            query.setParameter("fromDt",fromDt, StandardBasicTypes.STRING);
            query.setParameter("toDt", toDt, StandardBasicTypes.STRING);

            query.addScalar("facility", StandardBasicTypes.STRING);
            query.addScalar("requestorType", StandardBasicTypes.STRING);
            query.addScalar("requestorName", StandardBasicTypes.STRING);
            query.addScalar("requestorPhone", StandardBasicTypes.STRING);
            query.addScalar("requestId", StandardBasicTypes.STRING);
            query.addScalar("prebillNumber", StandardBasicTypes.STRING);
            query.addScalar("prebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("prebillAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("aging", StandardBasicTypes.STRING);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = null;
            if("\"Summary - Requestor Type\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retrievePrebillReportDetailsFirstLevelWithoutRequestorName").getQueryString();
             else if("\"Detailed - Requestor name\"".equalsIgnoreCase(resultType))
                 queryString = session.getNamedQuery("retrievePrebillReportDetailsSecondLevelWithoutRequestorName").getQueryString();
             else
                 queryString = session.getNamedQuery("retrievePrebillReportDetailsThirdLevelWithoutRequestorName").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility,StandardBasicTypes.STRING);
            query.setParameterList("requestorTypeName", requestorTypeName,StandardBasicTypes.STRING);
            query.setParameterList("requestStatus",reqStatus,StandardBasicTypes.STRING);
            query.setParameter("balanceCriteria",balanceCriterion,StandardBasicTypes.STRING);
            query.setParameter("balanceDue",balance,StandardBasicTypes.DOUBLE);
            query.setParameter("fromDt",fromDt, StandardBasicTypes.STRING);
            query.setParameter("toDt", toDt, StandardBasicTypes.STRING);

            query.addScalar("facility", StandardBasicTypes.STRING);
            query.addScalar("requestorType", StandardBasicTypes.STRING);
            query.addScalar("requestorName", StandardBasicTypes.STRING);
            query.addScalar("requestorPhone", StandardBasicTypes.STRING);
            query.addScalar("requestId", StandardBasicTypes.STRING);
            query.addScalar("prebillNumber", StandardBasicTypes.STRING);
            query.addScalar("prebillDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("prebillAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("aging", StandardBasicTypes.STRING);
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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = null;
            if("\"Posted By\"".equalsIgnoreCase(resultType))
               queryString = session.getNamedQuery("retrievePostPaymentReportDetailsFirstLevel").getQueryString();
            else
                queryString = session.getNamedQuery("retrievePostPaymentReportDetailsSecondLevel").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility);
            query.setParameterList("requestorType", requestorTypeName);
            query.setParameterList("userName", userName);
            query.setParameter("fromDt",fromDt, StandardBasicTypes.TIMESTAMP);
            query.setParameter("toDt", toDt, StandardBasicTypes.TIMESTAMP);

            query.addScalar("facility", StandardBasicTypes.STRING);
            query.addScalar("userName", StandardBasicTypes.STRING);
            query.addScalar("requestorType", StandardBasicTypes.STRING);
            query.addScalar("requestorName", StandardBasicTypes.STRING);
            query.addScalar("mrn", StandardBasicTypes.STRING);
            query.addScalar("requestId", StandardBasicTypes.STRING);
            query.addScalar("invoiceNumber", StandardBasicTypes.STRING);
            query.addScalar("paymentMethod", StandardBasicTypes.STRING);
            query.addScalar("paymentDetails", StandardBasicTypes.STRING);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("paymentId", StandardBasicTypes.STRING);
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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteAllMappedAdjustmentInvoicesByInvoiceId");
            query.setParameter("invoiceId", invoiceId, StandardBasicTypes.LONG);
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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteAllMappedPaymentInvoicesByInvoiceId");
            query.setParameter("invoiceId", invoiceId, StandardBasicTypes.LONG);
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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("updatePrebillPaymentsToInvoice").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(),StandardBasicTypes.LONG);
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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("updatePrebillAdjustmentsToInvoice").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(),StandardBasicTypes.LONG);
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
    
	//US16834 changes to Include requests in the pre-bill status on the payments popup(This function will check if there is any prebill payment).
    public boolean prebillPaymentExists(long requestId) {
        final String logSM = "IsPrebillPaymentExists(long requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("prebillPaymentExists").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", requestId, StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("prebillAdjustmentExists").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", requestId, StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("totalPostPrebillPayments").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(), StandardBasicTypes.LONG);
            sqlQuery.setParameter("Charge", invOrPrebillPreviewInfo.getBaseCharge(), StandardBasicTypes.DOUBLE);

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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("totalPostPrebillAdjustments").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(), StandardBasicTypes.LONG);
            sqlQuery.setParameter("Charge", invOrPrebillPreviewInfo.getBaseCharge(), StandardBasicTypes.DOUBLE);

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