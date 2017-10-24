package com.mckesson.eig.roi.billing.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

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
            SQLQuery query = session.createSQLQuery(queryString);
			query.addScalar("requestCoreDeliveryId", Hibernate.LONG);
			query.setParameter("requestCoreId", requestCoreId, Hibernate.LONG);
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
			Query query = session.getNamedQuery("deleteRequestCoreDelivery");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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

            SQLQuery query = session.createSQLQuery(queryString);
            query.addScalar("requestId", Hibernate.LONG);
            query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_Pages");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);
			query.setParameter("roiPagesId", roiPages.getPageSeq(), Hibernate.LONG);
			query.setParameter("selfPayEncounter", roiPages.isSelfPayEncounter(), Hibernate.BOOLEAN);

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
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges");
			query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChagesId, Hibernate.LONG);
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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
			Query query = session.getNamedQuery("deleteROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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
            SQLQuery query = session.createSQLQuery(queryString);
            query.addScalar("invoiceId", Hibernate.LONG);
			query.setParameter("requestCoreId", requestCoreId, Hibernate.LONG);

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
	        SQLQuery query = session.createSQLQuery(queryString);
	        query.addScalar("isInvoiced", Hibernate.BOOLEAN);
	        query.setParameter("requestCoreId", requestCoreId, Hibernate.LONG);

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
            SQLQuery query = session.createSQLQuery(queryString);
            query.addScalar("invoiceId", Hibernate.LONG);
			query.setParameter("releaseId", releaseId, Hibernate.LONG);

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
			Query query;
			if (isReleased) {
				query = session.getNamedQuery("updateInvoiceReleased");
			}  else {
				query = session.getNamedQuery("updateInvoiceReleaseCancelled");
			}
			query.setParameter("invoiceId", invoiceId, Hibernate.LONG);

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

            query.setParameter("requestCoreId", invoice.getRequestCoreId(), Hibernate.LONG);
            query.setParameter("createdDate", invoice.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", invoice.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", invoice.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", invoice.getModifiedBy(), Hibernate.LONG);
            query.setParameter("isReleased", invoice.getIsReleased(), Hibernate.BOOLEAN);
            query.setParameter("previouslyReleasedCost", invoice.getPreviouslyReleasedCost(),
                                Hibernate.DOUBLE);
            query.setParameter("totalPagesReleased", invoice.getTotalPagesReleased() ,
                                Hibernate.INTEGER);
            query.setParameter("totalPages", invoice.getTotalPages() , Hibernate.INTEGER);
            query.setParameter("salesTaxAmount", invoice.getSalesTaxAmount() , Hibernate.DOUBLE);
            query.setParameter("salesTaxPercentage", invoice.getSalesTaxPercentage(),
                                Hibernate.DOUBLE);
            query.setParameter("letterTemplateName", invoice.getLetterTemplateName(),
                                Hibernate.STRING);
            query.setParameter("requestStatus", invoice.getRequestStatus(), Hibernate.STRING);
            query.setParameter("requestDate", invoice.getRequestDate(), Hibernate.TIMESTAMP);
            query.setParameter("letterTemplateFileId", invoice.getLetterTemplateFileId(),
                               Hibernate.LONG);
            query.setParameter("type", invoice.getType(), Hibernate.STRING);
            query.setParameter("invoicePrebillDate", invoice.getInvoicePrebillDate(),
                                Hibernate.TIMESTAMP);
            query.setParameter("invoiceDueDate", invoice.getInvoiceDueDate(), Hibernate.TIMESTAMP);
            query.setParameter("resendDate", invoice.getResendDate(), Hibernate.TIMESTAMP);
            query.setParameter("outputMethod", invoice.getOutputMethod(), Hibernate.STRING);
            query.setParameter("queuePassword", invoice.getQueuePassword(), Hibernate.STRING);
            query.setParameter("overwriteDueDate", invoice.getOverwriteDueDate(),
                                Hibernate.BOOLEAN);
            query.setParameter("invoiceSalesTax", invoice.getInvoiceSalesTax(), Hibernate.DOUBLE);
            query.setParameter("baseCharge", invoice.getBaseCharge(), Hibernate.DOUBLE);
            query.setParameter("balanceDue", invoice.getBalanceDue(), Hibernate.DOUBLE);
            query.setParameter("invoiceBillingLocCode", invoice.getInvoiceBillingLocCode(),
                                Hibernate.STRING);
            query.setParameter("invoiceBillinglocName", invoice.getInvoiceBillinglocName(),
                                Hibernate.STRING);
            query.setParameter("invoiceBalanceDue", invoice.getInvoiceBalanceDue(),
                                Hibernate.DOUBLE);
            query.setParameter("releaseDate", invoice.getReleaseDate() , Hibernate.TIMESTAMP);
            query.setParameter("billingType", invoice.getBillingType() , Hibernate.STRING);
            query.setParameter("unbillable", invoice.isUnbillable() , Hibernate.BOOLEAN);
            query.setParameter("unbillableAmount", invoice.getUnbillableAmount(),
                               Hibernate.DOUBLE);
            query.setParameter("applySalesTax", invoice.getApplySalesTax() , Hibernate.BOOLEAN);
            query.setParameter("notes", invoice.getNotes() , Hibernate.STRING);
            query.setParameter("invoiceDueDays", invoice.getInvoiceDueDays() , Hibernate.LONG);
            query.setParameter("prebillStatus", invoice.getPrebillStatus() , Hibernate.STRING);
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

            query.setParameter("requestCoreDeliveryChargesId",requestCoreDeliveryChargesDocument.getRequestCoreDeliveryChargesId(), Hibernate.LONG);
            query.setParameter("amount", requestCoreDeliveryChargesDocument.getAmount(), Hibernate.DOUBLE);
            query.setParameter("copies", requestCoreDeliveryChargesDocument.getCopies(), Hibernate.INTEGER);
            query.setParameter("billingtierName", requestCoreDeliveryChargesDocument.getBillingTierName(), Hibernate.STRING);
            query.setParameter("totalPages", requestCoreDeliveryChargesDocument.getTotalPages(), Hibernate.INTEGER);
            query.setParameter("pages", requestCoreDeliveryChargesDocument.getPages(), Hibernate.INTEGER);
            query.setParameter("billingtierId", requestCoreDeliveryChargesDocument.getBillingtierId(), Hibernate.STRING);
            query.setParameter("releaseCount", requestCoreDeliveryChargesDocument.getReleaseCount(), Hibernate.INTEGER);
            query.setParameter("isElectronic", requestCoreDeliveryChargesDocument.getIsElectronic(), Hibernate.BOOLEAN);
            query.setParameter("removeBasecharge", requestCoreDeliveryChargesDocument.getRemoveBaseCharge(), Hibernate.BOOLEAN);
            query.setParameter("hasSalesTax", requestCoreDeliveryChargesDocument.getHasSalesTax(), Hibernate.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreDeliveryChargesDocument.getSalesTaxAmount(), Hibernate.DOUBLE);
            query.setParameter("createdDate", requestCoreDeliveryChargesDocument.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesDocument.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesDocument.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesDocument.getModifiedBy(), Hibernate.LONG);

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

            query.setParameter("requestCoreDeliveryChargesId",requestCoreDeliveryChargesFee.getRequestCoreDeliveryChargesId(), Hibernate.LONG);
            query.setParameter("amount", requestCoreDeliveryChargesFee.getAmount(), Hibernate.DOUBLE);
            query.setParameter("isCustomFee", requestCoreDeliveryChargesFee.getIsCustomFee(), Hibernate.BOOLEAN);
            query.setParameter("feeType", requestCoreDeliveryChargesFee.getFeeType(), Hibernate.STRING);
            query.setParameter("hasSalesTax", requestCoreDeliveryChargesFee.getHasSalesTax(), Hibernate.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreDeliveryChargesFee.getSalesTaxAmount(), Hibernate.DOUBLE);
            query.setParameter("createdDate", requestCoreDeliveryChargesFee.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesFee.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesFee.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesFee.getModifiedBy(), Hibernate.LONG);

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

            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesShipping.getRequestCoreDeliveryChargesId(), Hibernate.LONG);
            query.setParameter("shippingCharge", requestCoreDeliveryChargesShipping.getShippingCharge(), Hibernate.DOUBLE);
            query.setParameter("postalCode", requestCoreDeliveryChargesShipping.getPostalCode(), Hibernate.STRING);
            query.setParameter("addressType", requestCoreDeliveryChargesShipping.getAddressType(), Hibernate.STRING);
            query.setParameter("state", requestCoreDeliveryChargesShipping.getState(), Hibernate.STRING);
            query.setParameter("shippingUrl",requestCoreDeliveryChargesShipping.getShippingUrl(), Hibernate.STRING);
            query.setParameter("address1", requestCoreDeliveryChargesShipping.getAddress1(), Hibernate.STRING);
            query.setParameter("address2", requestCoreDeliveryChargesShipping.getAddress2(), Hibernate.STRING);
            query.setParameter("address3", requestCoreDeliveryChargesShipping.getAddress3(), Hibernate.STRING);
            query.setParameter("shippingWeight", requestCoreDeliveryChargesShipping.getShippingWeight(),Hibernate.DOUBLE);
            query.setParameter("trackingNumber", requestCoreDeliveryChargesShipping.getTrackingNumber(), Hibernate.STRING);
            query.setParameter("city", requestCoreDeliveryChargesShipping.getCity(), Hibernate.STRING);
            query.setParameter("countryCode", requestCoreDeliveryChargesShipping.getCountryCode(), Hibernate.STRING);
            query.setParameter("countryName", requestCoreDeliveryChargesShipping.getCountryName(), Hibernate.STRING);
            query.setParameter("willReleaseShipped", requestCoreDeliveryChargesShipping.isWillReleaseShipped(), Hibernate.BOOLEAN);
            query.setParameter("willInvoiceShipped", requestCoreDeliveryChargesShipping.isWillInvoiceShipped(), Hibernate.BOOLEAN);
            query.setParameter("shippingMethod", requestCoreDeliveryChargesShipping.getShippingMethod(), Hibernate.STRING);
            query.setParameter("outputMethod", requestCoreDeliveryChargesShipping.getOutputMethod(), Hibernate.STRING);
            query.setParameter("shippingMethodId", requestCoreDeliveryChargesShipping.getShippingMethodId(), Hibernate.LONG);
            query.setParameter("nonPrintableAttachmentQueue", requestCoreDeliveryChargesShipping.getNonPrintableAttachmentQueue(), Hibernate.STRING);
            query.setParameter("createdDate", requestCoreDeliveryChargesShipping.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestCoreDeliveryChargesShipping.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", requestCoreDeliveryChargesShipping.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreDeliveryChargesShipping.getModifiedBy(), Hibernate.LONG);

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
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreId", requestCoreId, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("isReleased", Hibernate.BOOLEAN);
            query.addScalar("previouslyReleasedCost", Hibernate.DOUBLE);
            query.addScalar("releaseCost", Hibernate.DOUBLE);
            query.addScalar("totalRequestCost", Hibernate.DOUBLE);
            query.addScalar("totalPagesReleased", Hibernate.INTEGER);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("salesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("salesTaxPercentage", Hibernate.DOUBLE);
            query.addScalar("letterTemplateName", Hibernate.STRING);
            query.addScalar("requestStatus", Hibernate.STRING);
            query.addScalar("requestDate", Hibernate.TIMESTAMP);
            query.addScalar("releaseDate", Hibernate.TIMESTAMP);
            query.addScalar("letterTemplateFileId", Hibernate.LONG);
            query.addScalar("type", Hibernate.STRING);
            query.addScalar("invoicePrebillDate", Hibernate.TIMESTAMP);
            query.addScalar("invoiceDueDate", Hibernate.TIMESTAMP);
            query.addScalar("resendDate", Hibernate.TIMESTAMP);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("queuePassword", Hibernate.STRING);
            query.addScalar("overwriteDueDate", Hibernate.BOOLEAN);
            query.addScalar("invoiceSalesTax", Hibernate.DOUBLE);
            query.addScalar("baseCharge", Hibernate.DOUBLE);
            query.addScalar("balanceDue", Hibernate.DOUBLE);
            query.addScalar("invoiceBillingLocCode", Hibernate.STRING);
            query.addScalar("invoiceBillinglocName", Hibernate.STRING);
            query.addScalar("invoiceBalanceDue", Hibernate.DOUBLE);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("creditAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("debitAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("applySalesTax", Hibernate.BOOLEAN);
            query.addScalar("notes", Hibernate.STRING);
            query.addScalar("unbillable", Hibernate.BOOLEAN);
            query.addScalar("unbillableAmount", Hibernate.DOUBLE);
            query.addScalar("invoiceDueDays", Hibernate.LONG);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("prebillStatus", Hibernate.STRING);
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
            SQLQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesId", ids);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestCoreDeliveryChargesId" ,Hibernate.LONG);
            query.addScalar("shippingCharge", Hibernate.DOUBLE);
            query.addScalar("postalCode", Hibernate.STRING);
            query.addScalar("addressType", Hibernate.STRING);
            query.addScalar("state", Hibernate.STRING);
            query.addScalar("shippingUrl", Hibernate.STRING);
            query.addScalar("address1", Hibernate.STRING);
            query.addScalar("address2", Hibernate.STRING);
            query.addScalar("address3", Hibernate.STRING);
            query.addScalar("shippingWeight", Hibernate.DOUBLE);
            query.addScalar("trackingNumber", Hibernate.STRING);
            query.addScalar("city", Hibernate.STRING);
            query.addScalar("countryCode", Hibernate.STRING);
            query.addScalar("countryName", Hibernate.STRING);
            query.addScalar("willReleaseShipped", Hibernate.BOOLEAN);
            query.addScalar("willInvoiceShipped", Hibernate.BOOLEAN);
            query.addScalar("shippingMethod", Hibernate.STRING);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("shippingMethodId", Hibernate.LONG);
            query.addScalar("nonPrintableAttachmentQueue", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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
            SQLQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesIds", ids);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestorId",Hibernate.LONG);
            query.addScalar("requestCoreDeliveryChargesId",Hibernate.LONG);
            query.addScalar("invoiceAppliedAmount", Hibernate.DOUBLE);
            query.addScalar("baseAmount", Hibernate.DOUBLE);
            query.addScalar("totalUnappliedAmount", Hibernate.DOUBLE);
            query.addScalar("paymentMode", Hibernate.STRING);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("paymentDate", Hibernate.TIMESTAMP);
            query.addScalar("transactionType", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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
            SQLQuery query = session.createSQLQuery(queryString);

            List<Long> ids = new ArrayList<Long>();
            ids.add(requestCoreDeliveryChargesId);
            query.setParameterList("requestCoreDeliveryChargesIds", ids);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestorId",Hibernate.LONG);
            query.addScalar("requestCoreDeliveryChargesId",Hibernate.LONG);
            query.addScalar("invoiceAppliedAmount", Hibernate.DOUBLE);
            query.addScalar("baseAmount", Hibernate.DOUBLE);
            query.addScalar("totalUnappliedAmount", Hibernate.DOUBLE);
            query.addScalar("paymentMode", Hibernate.STRING);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("paymentDate", Hibernate.TIMESTAMP);
            query.addScalar("transactionType", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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

    /*public void deleteRequestCoreDeliveryChargesAdjustmentPayment(long  requestCoreDeliveryChargesId) {
        final String logSM = "deleteRequestCoreChargesAdjustmentPayment(requestCoreDeliveryChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreDeliveryChargesId);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesAdjustmentPayment");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, Hibernate.LONG);
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
            Session session = getSession();
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryCharges");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, Hibernate.LONG);
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
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesFee");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, Hibernate.LONG);
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
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesShipping");
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, Hibernate.LONG);
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
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesDocument");
            query.setParameter("requestCoreDeliveryChargesSeq", requestCoreDeliveryChargesId, Hibernate.LONG);
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
            Query query = session.getNamedQuery("deleteRequestCoreDeliveryChargesInvoicePatients");
            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, Hibernate.LONG);
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
			query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryInvoicePatients.getRequestCoreDeliveryChargesId(), Hibernate.LONG);
			query.setParameter("requestHpfPatientsId", requestCoreDeliveryInvoicePatients.getRequestHpfPatientsId(), Hibernate.LONG);
			query.setParameter("requestNonHpfPatientsId", requestCoreDeliveryInvoicePatients.getRequestNonHpfPatientsId(), Hibernate.LONG);
			query.setParameter("createdDt", requestCoreDeliveryInvoicePatients.getCreatedDt(), Hibernate.TIMESTAMP);
			query.setParameter("createdBy", requestCoreDeliveryInvoicePatients.getCreatedBy(), Hibernate.LONG);
			query.setParameter("modifiedDt", requestCoreDeliveryInvoicePatients.getModifiedDt(), Hibernate.TIMESTAMP);
			query.setParameter("modifiedBy", requestCoreDeliveryInvoicePatients.getModifiedBy(), Hibernate.LONG);
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
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementalAttachmentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);
			query.setParameter("roiSupplementalAttachmentId", supplementalAttachmentSeq, Hibernate.LONG);

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
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementalDocumentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);
			query.setParameter("roiSupplementalDocumentId", supplementalDocumentSeq, Hibernate.LONG);

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
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementarityAttachmentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);
			query.setParameter("roiSupplementarityAttachmentId", supplementarityAttachmentSeq, Hibernate.LONG);

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
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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
			Query query = session.getNamedQuery("createROI_RequestCoreDeliverytoROI_SupplementarityDocumentsCore");
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);
			query.setParameter("roiSupplementarityDocumentId", supplementarityDocumentSeq, Hibernate.LONG);

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
			query.setParameter("requestCoreDeliveryId", requestCoreDeliveryId, Hibernate.LONG);

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

            query.setParameter("requestCoreChargesSeq",salesTaxReason.getRequestCoreChargesSeq(), Hibernate.LONG);
            query.setParameter("reason", salesTaxReason.getReason(), Hibernate.STRING);
            query.setParameter("reasonDate", salesTaxReason.getReasonDate(), Hibernate.TIMESTAMP);
            query.setParameter("createdDate", salesTaxReason.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", salesTaxReason.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", salesTaxReason.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", salesTaxReason.getModifiedBy(), Hibernate.LONG);

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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, Hibernate.LONG);
            query.addScalar("id", Hibernate.LONG);
            query.addScalar("reason", Hibernate.STRING);
            query.addScalar("reasonDate", Hibernate.TIMESTAMP);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreDeliveryChargesId", rcdChargesSeq, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestCoreId", Hibernate.LONG);
            query.addScalar("isReleased", Hibernate.BOOLEAN);
            query.addScalar("previouslyReleasedCost", Hibernate.DOUBLE);
            query.addScalar("releaseCost", Hibernate.DOUBLE);
            query.addScalar("totalRequestCost", Hibernate.DOUBLE);
            query.addScalar("totalPagesReleased", Hibernate.INTEGER);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("salesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("salesTaxPercentage", Hibernate.DOUBLE);
            query.addScalar("letterTemplateName", Hibernate.STRING);
            query.addScalar("requestStatus", Hibernate.STRING);
            query.addScalar("requestDate", Hibernate.TIMESTAMP);
            query.addScalar("releaseDate", Hibernate.TIMESTAMP);
            query.addScalar("letterTemplateFileId", Hibernate.LONG);
            query.addScalar("type", Hibernate.STRING);
            query.addScalar("invoicePrebillDate", Hibernate.TIMESTAMP);
            query.addScalar("invoiceDueDate", Hibernate.TIMESTAMP);
            query.addScalar("resendDate", Hibernate.TIMESTAMP);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("queuePassword", Hibernate.STRING);
            query.addScalar("overwriteDueDate", Hibernate.BOOLEAN);
            query.addScalar("invoiceSalesTax", Hibernate.DOUBLE);
            query.addScalar("baseCharge", Hibernate.DOUBLE);
            query.addScalar("balanceDue", Hibernate.DOUBLE);
            query.addScalar("invoiceBillingLocCode", Hibernate.STRING);
            query.addScalar("invoiceBillinglocName", Hibernate.STRING);
            query.addScalar("invoiceBalanceDue", Hibernate.DOUBLE);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("creditAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("debitAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("applySalesTax", Hibernate.BOOLEAN);
            query.addScalar("notes", Hibernate.STRING);
            query.addScalar("invoiceDueDays", Hibernate.LONG);
            query.addScalar("requestCreditAdjustment", Hibernate.DOUBLE);
            query.addScalar("requestDebitAdjustment", Hibernate.DOUBLE);
            query.addScalar("requestBalanceDue", Hibernate.DOUBLE);
            query.addScalar("requestpayment", Hibernate.DOUBLE);
            query.addScalar("unbillable", Hibernate.BOOLEAN);
            query.addScalar("unbillableAmount", Hibernate.DOUBLE);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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
            SQLQuery query = session.createSQLQuery(queryString);

            Set<RequestCoreDeliveryChargesFee> rcdcFeeSet = new LinkedHashSet<RequestCoreDeliveryChargesFee>();

            query.setParameter("rcdChargesSeq", rcdChargesId, Hibernate.LONG);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("isCustomFee", Hibernate.BOOLEAN);
            query.addScalar("feeType", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("hasSalesTax", Hibernate.BOOLEAN);
            query.addScalar("SalesTaxAmount", Hibernate.DOUBLE);

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
            SQLQuery query = session.createSQLQuery(queryString);

            Set<RequestCoreDeliveryChargesDocument> rcdcDocumentSet = new LinkedHashSet<RequestCoreDeliveryChargesDocument>();

            query.setParameter("rcdChargesSeq", rcdChargesId, Hibernate.LONG);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("copies", Hibernate.INTEGER);
            query.addScalar("billingTierName", Hibernate.STRING);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("pages", Hibernate.INTEGER);
            query.addScalar("billingtierId", Hibernate.STRING);
            query.addScalar("releaseCount", Hibernate.INTEGER);
            query.addScalar("isElectronic", Hibernate.BOOLEAN);
            query.addScalar("removeBaseCharge", Hibernate.BOOLEAN);
            query.addScalar("hasSalesTax", Hibernate.BOOLEAN);
            query.addScalar("SalesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);

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
            Query query = session.getNamedQuery("deleteSalesTaxReason");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, Hibernate.LONG);

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
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("requestCoreDeliveryChargesId", invoiceIds, Hibernate.LONG);
            query.addScalar("requestHpfPatientsId",Hibernate.LONG);
            query.addScalar("requestNonHpfPatientsId",Hibernate.LONG);

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

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("isCustomFee", Hibernate.BOOLEAN);
            query.addScalar("feeType", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("hasSalesTax", Hibernate.BOOLEAN);
            query.addScalar("SalesTaxAmount", Hibernate.DOUBLE);

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

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("feeType", Hibernate.STRING);

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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("copies", Hibernate.INTEGER);
            query.addScalar("billingTierName", Hibernate.STRING);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("pages", Hibernate.INTEGER);
            query.addScalar("billingtierId", Hibernate.STRING);
            query.addScalar("releaseCount", Hibernate.INTEGER);
            query.addScalar("isElectronic", Hibernate.BOOLEAN);
            query.addScalar("removeBaseCharge", Hibernate.BOOLEAN);
            query.addScalar("hasSalesTax", Hibernate.BOOLEAN);
            query.addScalar("SalesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);

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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("rcdChargesSeq", rcdChargesId);
            query.addScalar("billingTierName", Hibernate.STRING);

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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesId", rcdChargesSeq);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestCoreId", Hibernate.LONG);
            query.addScalar("isReleased", Hibernate.BOOLEAN);
            query.addScalar("previouslyReleasedCost", Hibernate.DOUBLE);
            query.addScalar("releaseCost", Hibernate.DOUBLE);
            query.addScalar("totalRequestCost", Hibernate.DOUBLE);
            query.addScalar("totalPagesReleased", Hibernate.INTEGER);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("salesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("salesTaxPercentage", Hibernate.DOUBLE);
            query.addScalar("letterTemplateName", Hibernate.STRING);
            query.addScalar("requestStatus", Hibernate.STRING);
            query.addScalar("requestDate", Hibernate.TIMESTAMP);
            query.addScalar("releaseDate", Hibernate.TIMESTAMP);
            query.addScalar("letterTemplateFileId", Hibernate.LONG);
            query.addScalar("type", Hibernate.STRING);
            query.addScalar("invoicePrebillDate", Hibernate.TIMESTAMP);
            query.addScalar("invoiceDueDate", Hibernate.TIMESTAMP);
            query.addScalar("resendDate", Hibernate.TIMESTAMP);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("queuePassword", Hibernate.STRING);
            query.addScalar("overwriteDueDate", Hibernate.BOOLEAN);
            query.addScalar("invoiceSalesTax", Hibernate.DOUBLE);
            query.addScalar("baseCharge", Hibernate.DOUBLE);
            query.addScalar("balanceDue", Hibernate.DOUBLE);
            query.addScalar("invoiceBillingLocCode", Hibernate.STRING);
            query.addScalar("invoiceBillinglocName", Hibernate.STRING);
            query.addScalar("invoiceBalanceDue", Hibernate.DOUBLE);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("creditAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("debitAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("applySalesTax", Hibernate.BOOLEAN);
            query.addScalar("notes", Hibernate.STRING);
            query.addScalar("invoiceDueDays", Hibernate.LONG);
            query.addScalar("creator", Hibernate.STRING);

            query.addScalar("requestCreditAdjustment", Hibernate.DOUBLE);
            query.addScalar("requestDebitAdjustment", Hibernate.DOUBLE);
            query.addScalar("requestBalanceDue", Hibernate.DOUBLE);
            query.addScalar("requestpayment", Hibernate.DOUBLE);

            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesIds", requestCoreDeliveryChargesId);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestCoreDeliveryChargesId", Hibernate.LONG);
            query.addScalar("invoiceAppliedAmount", Hibernate.DOUBLE);
            query.addScalar("paymentMode", Hibernate.STRING);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("paymentDate", Hibernate.TIMESTAMP);
            query.addScalar("transactionType", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameterList("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestCoreDeliveryChargesId", Hibernate.LONG);
            query.addScalar("shippingCharge", Hibernate.DOUBLE);
            query.addScalar("postalCode", Hibernate.STRING);
            query.addScalar("addressType", Hibernate.STRING);
            query.addScalar("state", Hibernate.STRING);
            query.addScalar("shippingUrl", Hibernate.STRING);
            query.addScalar("address1", Hibernate.STRING);
            query.addScalar("address2", Hibernate.STRING);
            query.addScalar("address3", Hibernate.STRING);
            query.addScalar("shippingWeight", Hibernate.DOUBLE);
            query.addScalar("trackingNumber", Hibernate.STRING);
            query.addScalar("city", Hibernate.STRING);
            query.addScalar("countryCode", Hibernate.STRING);
            query.addScalar("countryName", Hibernate.STRING);
            query.addScalar("willReleaseShipped", Hibernate.BOOLEAN);
            query.addScalar("willInvoiceShipped", Hibernate.BOOLEAN);
            query.addScalar("shippingMethod", Hibernate.STRING);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("shippingMethodId", Hibernate.LONG);
            query.addScalar("nonPrintableAttachmentQueue", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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
            Session session = getSession();
            Query query = session.getNamedQuery("updateRequestCoreDeliveryCharges");

            query.setParameter("requestCoreDeliveryChargesId", requestCoreDeliveryChargesId, Hibernate.LONG);
            query.setParameter("invoicedAmount",updatedInvoicedAmount,Hibernate.DOUBLE);
            query.setParameter("paymentAmount", paymentAmount, Hibernate.DOUBLE);
            query.setParameter("creditAdjAmount", creditAdjAmount, Hibernate.DOUBLE);
            query.setParameter("debitAdjAmount", debitAdjAmount, Hibernate.DOUBLE);

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

            final Session session = getSession();
            Query query = session.getNamedQuery("CreateCoverLetterCore");
//            query.setParameter("requestCoreChargesId", coverLetterCore.getRequestCoreChargesId(), Hibernate.LONG);
            query.setParameter("requestId", coverLetterCore.getRequestId(), Hibernate.LONG);
            query.setParameter("requestorId", coverLetterCore.getRequestorId(), Hibernate.LONG);
            query.setParameter("letterTemplateId", coverLetterCore.getLetterTemplateId(), Hibernate.LONG);
            query.setParameter("note", coverLetterCore.getNotes(), Hibernate.STRING);
            query.setParameter("templateType", coverLetterCore.getLetterType(), Hibernate.STRING);
            query.setParameter("queuePassword", coverLetterCore.getQueuePassword(), Hibernate.STRING);
            query.setParameter("modifiedDt", coverLetterCore.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", coverLetterCore.getModifiedBy(), Hibernate.LONG);
            query.setParameter("createdDt", coverLetterCore.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", coverLetterCore.getCreatedBy(), Hibernate.LONG);
            query.setParameter("recordVersion", coverLetterCore.getRecordVersion(), Hibernate.INTEGER);

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
            Query query = session.getNamedQuery("deleteCoverLetterRequestHpfPatient");
            query.setParameter("coverLetterId", coverLetterId, Hibernate.LONG);
            query.executeUpdate();

            // deletes the non-hpf patients corresponding for the given cover letter
            query = session.getNamedQuery("deleteCoverLetterRequestSupplementalPatient");
            query.setParameter("coverLetterId", coverLetterId, Hibernate.LONG);
            query.executeUpdate();

            // deletes the cover letter
            query = session.getNamedQuery("deleteCoverLetter");
            query.setParameter("coverLetterId", coverLetterId, Hibernate.LONG);
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

            SQLQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", Hibernate.LONG);
//            query.addScalar("requestCoreChargesId", Hibernate.LONG);
            query.addScalar("requestId", Hibernate.LONG);
            query.addScalar("requestorId",Hibernate.LONG);
            query.addScalar("letterTemplateId", Hibernate.LONG);
            query.addScalar("notes", Hibernate.STRING);
            query.addScalar("letterType", Hibernate.STRING);
            query.addScalar("queuePassword", Hibernate.STRING);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);

            query.setParameter("coverLetterId", coverLetterId);
            query.setResultTransformer(Transformers.aliasToBean(CoverLetterCore.class));
            CoverLetterCore coverLetter = (CoverLetterCore) query.uniqueResult();

            // retrieves the list of hpf patients Sequences
            queryString = session.getNamedQuery("retrieveCoverLetterRequestHpfPatient")
                                 .getQueryString();

            query = session.createSQLQuery(queryString);
            setScalarsForRequestPatient(query);

            query.setParameter("coverLetterId", coverLetterId, Hibernate.LONG);
            List<RequestPatient> requestHpfPatients = query.list();
            coverLetter.setRequestHpfPatients(requestHpfPatients);

            // retrieves the list of nonhpf patients Sequences
            queryString = session.getNamedQuery("retrieveCoverLetterRequestSupplementalPatient")
                                 .getQueryString();

            query = session.createSQLQuery(queryString);
            setScalarsForRequestPatient(query);

            query.setParameter("coverLetterId", coverLetterId, Hibernate.LONG);
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

        query.addScalar("patientSeq", Hibernate.LONG);
        query.addScalar("requestId", Hibernate.LONG);
        query.addScalar("supplementalId", Hibernate.LONG);
        query.addScalar("mrn", Hibernate.STRING);
        query.addScalar("facility", Hibernate.STRING);
        query.addScalar("name", Hibernate.STRING);
        query.addScalar("gender", Hibernate.STRING);
        query.addScalar("epn", Hibernate.STRING);
        query.addScalar("ssn", Hibernate.STRING);
        query.addScalar("patientLocked", Hibernate.BOOLEAN);
        query.addScalar("encounterLocked", Hibernate.BOOLEAN);
        query.addScalar("vip", Hibernate.BOOLEAN);
        query.addScalar("hpf", Hibernate.BOOLEAN);
        query.addScalar("dob", Hibernate.TIMESTAMP);

        query.addScalar("createdDt", Hibernate.TIMESTAMP);
        query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
        query.addScalar("modifiedBy", Hibernate.INTEGER);
        query.addScalar("createdBy", Hibernate.INTEGER);

        query.setResultTransformer(Transformers.aliasToBean(RequestPatient.class));
    }

   /* @Override
    public RegeneratedInvoiceDetails retrieveRegeneratedInvoiceUsingRegeneratedId(long regInvoiceId) {
        final String logSM = "retrieveRegeneratedInvoices(rcdChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + regInvoiceId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRegeneratedInvoiceUsingRegeneratedId")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("regeneratedInvoiceId", regInvoiceId, Hibernate.LONG);

            query.addScalar("resendDt", Hibernate.TIMESTAMP);
            query.addScalar("requestCoreDeliveryChargesId", Hibernate.LONG);
            query.addScalar("letterTemplateName", Hibernate.STRING);
            query.addScalar("letterTemplateFileId", Hibernate.LONG);
            query.addScalar("requestBalanceDue", Hibernate.DOUBLE);
            query.addScalar("queuePassword", Hibernate.STRING);
            query.addScalar("requestCreditAmount", Hibernate.DOUBLE);
            query.addScalar("requestDebitAmount", Hibernate.DOUBLE);
            query.addScalar("requestPaymentAmount", Hibernate.DOUBLE);
            query.addScalar("requestStatus", Hibernate.STRING);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("amountPaid", Hibernate.DOUBLE);
            query.addScalar("balanceDue", Hibernate.DOUBLE);
            query.addScalar("baseCharge", Hibernate.DOUBLE);
            query.addScalar("originalBalance", Hibernate.DOUBLE);

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

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveAllPastInvoices").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, Hibernate.LONG);

            query.addScalar("invoiceId", Hibernate.LONG);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("createdDate", Hibernate.STRING);

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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, Hibernate.LONG);

            query.addScalar("releaseDate", Hibernate.TIMESTAMP);
            query.addScalar("totalShippingCharge", Hibernate.DOUBLE);
            query.addScalar("totalSalesTax", Hibernate.DOUBLE);
            query.addScalar("totalFeeCharge", Hibernate.DOUBLE);
            query.addScalar("totalDocumentCharge", Hibernate.DOUBLE);
            query.addScalar("unbillable", Hibernate.BOOLEAN);

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

            Session session = getSession();

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

            Session session = getSession();

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

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestChargesByInvoice")
                                        .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameterList("invoiceIds", invoiceIds);

            sqlQuery.addScalar("requestCoreSeq", Hibernate.LONG);
            sqlQuery.addScalar("balanceDue", Hibernate.DOUBLE);
            sqlQuery.addScalar("invoicesBalance", Hibernate.DOUBLE);
            sqlQuery.addScalar("creditAdjustmentAmount", Hibernate.DOUBLE);
            sqlQuery.addScalar("debitAdjustmentAmount", Hibernate.DOUBLE);
            sqlQuery.addScalar("paymentAmount", Hibernate.DOUBLE);

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

            Session session = getSession();
            Query query = session.getNamedQuery("updateInvoiceBalance");

            query.setParameter("invoiceId", invoiceId, Hibernate.LONG);
            query.setParameter("invoiceBalance", invoiceBalance, Hibernate.DOUBLE);
            query.setParameter("modifiedDt", getSQLTimeStamp(date), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(), Hibernate.INTEGER);
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
            Query query = session.getNamedQuery("updatePrebillStatusInvoice");

            query.setParameter("reqCoreSeq", invoice.getRequestCoreId(), Hibernate.LONG);
            query.setParameter("prebillStatus", invoice.getPrebillStatus(), Hibernate.STRING);
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
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreId", requestCoreId, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("isReleased", Hibernate.BOOLEAN);
            query.addScalar("previouslyReleasedCost", Hibernate.DOUBLE);
            query.addScalar("releaseCost", Hibernate.DOUBLE);
            query.addScalar("totalRequestCost", Hibernate.DOUBLE);
            query.addScalar("totalPagesReleased", Hibernate.INTEGER);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("salesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("salesTaxPercentage", Hibernate.DOUBLE);
            query.addScalar("letterTemplateName", Hibernate.STRING);
            query.addScalar("requestStatus", Hibernate.STRING);
            query.addScalar("requestDate", Hibernate.TIMESTAMP);
            query.addScalar("releaseDate", Hibernate.TIMESTAMP);
            query.addScalar("letterTemplateFileId", Hibernate.LONG);
            query.addScalar("type", Hibernate.STRING);
            query.addScalar("invoicePrebillDate", Hibernate.TIMESTAMP);
            query.addScalar("invoiceDueDate", Hibernate.TIMESTAMP);
            query.addScalar("resendDate", Hibernate.TIMESTAMP);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("queuePassword", Hibernate.STRING);
            query.addScalar("overwriteDueDate", Hibernate.BOOLEAN);
            query.addScalar("invoiceSalesTax", Hibernate.DOUBLE);
            query.addScalar("baseCharge", Hibernate.DOUBLE);
            query.addScalar("balanceDue", Hibernate.DOUBLE);
            query.addScalar("invoiceBillingLocCode", Hibernate.STRING);
            query.addScalar("invoiceBillinglocName", Hibernate.STRING);
            query.addScalar("invoiceBalanceDue", Hibernate.DOUBLE);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("creditAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("debitAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("applySalesTax", Hibernate.BOOLEAN);
            query.addScalar("notes", Hibernate.STRING);
            query.addScalar("unbillable", Hibernate.BOOLEAN);
            query.addScalar("unbillableAmount", Hibernate.DOUBLE);
            query.addScalar("invoiceDueDays", Hibernate.LONG);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("prebillStatus", Hibernate.STRING);
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

            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility,Hibernate.STRING);
            query.setParameterList("requestorTypeName", requestorTypeName,Hibernate.STRING);
            query.setParameterList("requestStatus",reqStatus,Hibernate.STRING);
            query.setParameter("balanceCriteria",balanceCriterion,Hibernate.STRING);
            query.setParameter("requestorName","%" + getSpecialCharSearchStr(requestorName) + "%",Hibernate.STRING);
            query.setParameter("balanceDue",balance,Hibernate.DOUBLE);
            query.setParameter("fromDt",fromDt, Hibernate.STRING);
            query.setParameter("toDt", toDt, Hibernate.STRING);

            query.addScalar("facility", Hibernate.STRING);
            query.addScalar("requestorType", Hibernate.STRING);
            query.addScalar("requestorName", Hibernate.STRING);
            query.addScalar("requestorPhone", Hibernate.STRING);
            query.addScalar("requestId", Hibernate.STRING);
            query.addScalar("prebillNumber", Hibernate.STRING);
            query.addScalar("prebillDate", Hibernate.TIMESTAMP);
            query.addScalar("prebillAmount", Hibernate.DOUBLE);
            query.addScalar("aging", Hibernate.STRING);
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
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility,Hibernate.STRING);
            query.setParameterList("requestorTypeName", requestorTypeName,Hibernate.STRING);
            query.setParameterList("requestStatus",reqStatus,Hibernate.STRING);
            query.setParameter("balanceCriteria",balanceCriterion,Hibernate.STRING);
            query.setParameter("balanceDue",balance,Hibernate.DOUBLE);
            query.setParameter("fromDt",fromDt, Hibernate.STRING);
            query.setParameter("toDt", toDt, Hibernate.STRING);

            query.addScalar("facility", Hibernate.STRING);
            query.addScalar("requestorType", Hibernate.STRING);
            query.addScalar("requestorName", Hibernate.STRING);
            query.addScalar("requestorPhone", Hibernate.STRING);
            query.addScalar("requestId", Hibernate.STRING);
            query.addScalar("prebillNumber", Hibernate.STRING);
            query.addScalar("prebillDate", Hibernate.TIMESTAMP);
            query.addScalar("prebillAmount", Hibernate.DOUBLE);
            query.addScalar("aging", Hibernate.STRING);
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
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facility);
            query.setParameterList("requestorType", requestorTypeName);
            query.setParameterList("userName", userName);
            query.setParameter("fromDt",fromDt, Hibernate.TIMESTAMP);
            query.setParameter("toDt", toDt, Hibernate.TIMESTAMP);

            query.addScalar("facility", Hibernate.STRING);
            query.addScalar("userName", Hibernate.STRING);
            query.addScalar("requestorType", Hibernate.STRING);
            query.addScalar("requestorName", Hibernate.STRING);
            query.addScalar("mrn", Hibernate.STRING);
            query.addScalar("requestId", Hibernate.STRING);
            query.addScalar("invoiceNumber", Hibernate.STRING);
            query.addScalar("paymentMethod", Hibernate.STRING);
            query.addScalar("paymentDetails", Hibernate.STRING);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("createdDate", Hibernate.TIMESTAMP);
            query.addScalar("paymentId", Hibernate.STRING);
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
            Query query = session.getNamedQuery("deleteAllMappedAdjustmentInvoicesByInvoiceId");
            query.setParameter("invoiceId", invoiceId, Hibernate.LONG);
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
            Query query = session.getNamedQuery("deleteAllMappedPaymentInvoicesByInvoiceId");
            query.setParameter("invoiceId", invoiceId, Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(),Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(),Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, Hibernate.LONG);
            sqlQuery.setParameter("invoiceId", invoiceId, Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, Hibernate.LONG);
            sqlQuery.setParameter("invoiceId", invoiceId, Hibernate.LONG);
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
    
    /*public void updateUnappliedToAppliedPaymentsToPrebill(long requestId) {
        final String logSM = "updateUnappliedToAppliedPaymentsToPrebill(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("updateUnappliedToAppliedPaymentsToPrebill").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId,Hibernate.LONG);
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
    }*/
    
    /*public void updateUnappliedToAppliedAdjustmentsToPrebill(long requestId) {
        final String logSM = "updateUnappliedToAppliedAdjustmentsToPrebill(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("updateUnappliedToAppliedAdjustmentsToPrebill").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId,Hibernate.LONG);
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
    }*/
    
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, Hibernate.LONG);
            sqlQuery.addScalar("paymentId", Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId, Hibernate.LONG);
            sqlQuery.addScalar("adjustmentId", Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestorPaymentSeq", paymentId, Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestorAdjustmentSeq", adjustmentId, Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId,Hibernate.LONG);
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
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", requestId, Hibernate.LONG);

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
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", requestId, Hibernate.LONG);

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
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(), Hibernate.LONG);
            sqlQuery.setParameter("Charge", invOrPrebillPreviewInfo.getBaseCharge(), Hibernate.DOUBLE);

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
            SQLQuery sqlQuery = session.createSQLQuery(query);
           
            sqlQuery.setParameter("requestId", invOrPrebillPreviewInfo.getRequestCoreId(), Hibernate.LONG);
            sqlQuery.setParameter("Charge", invOrPrebillPreviewInfo.getBaseCharge(), Hibernate.DOUBLE);

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