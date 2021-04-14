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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.admin.model.Designation;
import com.mckesson.eig.roi.admin.model.DocTypeDesignations;
import com.mckesson.eig.roi.admin.model.DocTypeRelation;
import com.mckesson.eig.roi.admin.model.Gender;
import com.mckesson.eig.roi.admin.model.MUDocTypeDto;
import com.mckesson.eig.roi.admin.model.MUDocTypeModel;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.utility.util.CollectionUtilities;


/**
 * @author OFS
 * @date   Jan 5, 2009
 * @since  HPF 13.1 [ROI]; May 12, 2008
 */
public class DocumentTypeDAOImpl extends ROIDAOImpl implements DocumentTypeDAO {

    private static final OCLogger LOG = new OCLogger(DocumentTypeDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String MU = "mu";

    /**
     * This method is to create, update, and delete the DesignateDocumentTypes
     * @param codeSetId Id of the CodeSet
     * @param newDesigs of DocTypeDesignations
     * @param user details of the user
     * @param currentDesigs of DocTypeDesignations
     */
    @SuppressWarnings("unchecked")
    public void designateDocumentTypes(long codeSetId,
                                       DocTypeDesignations newDesigs,
                                       DocTypeDesignations currentDesigs,
                                       User user) {

        final String logSM = "designateDocumentTypes(codeSetId, newDesigs, currentDesigs, user)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        for (Designation desi : newDesigs.getDesignation()) {

            List<Long> newIds = desi.getDocTypeIds();
            Designation des = currentDesigs.getDesignation(desi.getType());
            List<Long> oldIds = (des == null) ? new ArrayList<Long>() : des.getDocTypeIds();
            List<Long> deleteIds = new ArrayList<Long>(oldIds);
            String type = desi.getType();
            // changes for mu doc type
            List<MUDocTypeDto> muList = desi.getMuDocTypes();

            if ((newIds == null) || (newIds.size() == 0)) {

                deleteIdsFromDB(codeSetId, type);
                continue;
            }

            oldIds.retainAll(newIds);
            newIds.removeAll(oldIds);
            deleteIds.removeAll(oldIds);

            List<DocTypeRelation> docTypeRel = prepareDocTypeRelation(newIds,
                                                                      codeSetId,
                                                                      desi.getType(),
                                                                      user,
																	  muList);
            createOrUpdateDocType(docTypeRel);

            if (oldIds.size() > 0) {

                docTypeRel = retrieveDocTypeRelation(oldIds, type);
                for (DocTypeRelation relation : docTypeRel) {
                    // changes for update of mu doc type
                    if (relation != null
                            && MU.equalsIgnoreCase(relation.getType())) {

                        for (int i = 0; i < muList.size(); i++) {

                            if (relation.getDocTypeId() == muList.get(i)
                                    .getMuDocId()) {

                                String muDocName = muList.get(i).getMuDocName();

                                List<Integer> muDocId = (List<Integer>) getHibernateTemplate()
                                        .findByNamedQuery("getMUDocId",
                                                muDocName);

                                int docID = muDocId.get(0).intValue();
                                relation.setMuDocumentId(docID);

                            }

                        }
                    }

                    relation.setModifiedBy(user.getInstanceIdValue());
                    relation.setModifiedDt(getDate());
                }
                createOrUpdateDocType(docTypeRel);
            }

            if (deleteIds.size() > 0) {

                docTypeRel = retrieveDocTypeRelation(deleteIds, type);
                deleteDocType(docTypeRel);
            }
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }

    @SuppressWarnings("unchecked")
    private List<DocTypeRelation> prepareDocTypeRelation(List<Long> ids,
                                                         long codeSetId,
                                                         String type,
                                                         User user,
														 List<MUDocTypeDto> muList) {

        List<DocTypeRelation> docTypeRelations = new ArrayList<DocTypeRelation>();

        for (long id : ids) {

            DocTypeRelation docTypeRel = new DocTypeRelation(id, codeSetId, type);

            docTypeRel.setOrgId(1);
            docTypeRel.setCreatedBy(user.getInstanceId());
            docTypeRel.setModifiedDt(getDate());
            docTypeRel.setMuDocumentId(1);
            // changes for mu doc type
            if (muList != null) {
                for (int i = 0; i < muList.size(); i++) {

                    if (id == muList.get(i).getMuDocId()) {
                        String muDocName = muList.get(i).getMuDocName();

                        List<Integer> muDocId = (List<Integer>) getHibernateTemplate()
                                .findByNamedQuery("getMUDocId", muDocName);

                        int docID = muDocId.get(0).intValue();
                        docTypeRel.setMuDocumentId(docID);

                    }
                }
            }

            docTypeRelations.add(docTypeRel);
        }

        return docTypeRelations;
    }

    private void deleteIdsFromDB(long codeSetId , String type) {

        List<DocTypeRelation> delDocTypeRel = getDesignatedDocumentTypes(codeSetId, type);
        deleteDocType(delDocTypeRel);
    }

    /**
     * This method creates the document type
     * @param newDocTypeRelations list of DocTypeRelation details
     */
    private void createOrUpdateDocType(List<DocTypeRelation> docTypeRelations) {

        final String logSM = "createOrUpdateDocType(docTypeRelations)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        for (Iterator it = docTypeRelations.iterator(); it.hasNext();) {
            getHibernateTemplate().saveOrUpdate(it.next());
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:No.Of DocumentTypes: " + docTypeRelations.size());
        }
    }

    /**
     * This method deletes the Document Type
     * @param oldDocTypeRelations list of DocumentTypeRelation details to delete
     */
    private void deleteDocType(List<DocTypeRelation> oldDocTypeRelations) {

        final String logSM = "deleteDocType(oldDocTypeRelations)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        getHibernateTemplate().deleteAll(oldDocTypeRelations);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:No.Of DocumentTypes Deleted: " + oldDocTypeRelations.size());
        }
    }

    private List<DocTypeRelation> retrieveDocTypeRelation(final List<Long> docTypeId,
                                                          final  String type) {

        final String logSM = "retrieveDocTypeRelation(docTypeId, type)";
        if (DO_DEBUG) {

            LOG.debug(logSM + ">>Start: DocTypeId " + docTypeId);
            LOG.debug(logSM + ">>Start: Type " + type);
        }

        final StringBuffer hql = new StringBuffer();
        hql.append("SELECT relation FROM DocTypeRelation AS relation \n")
           .append("WHERE relation.type = ? \n")
           .append("AND relation.docTypeId IN(:IDS)\n ");

        @SuppressWarnings("unchecked") // not supported by the 3rdParty API

        List<DocTypeRelation> desi
        = (List<DocTypeRelation>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session s) {
                return s.createQuery(hql.toString())
                        .setParameter(0, type)
                        .setParameterList("IDS", docTypeId)
                        .list();
            }
        });

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }

        return desi;
    }

    private List<DocTypeRelation> getDesignatedDocumentTypes(long codeSetId, String designation) {

        final String logSM = "getDesignatedDocumentTypes(codeSetId, designation)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: codeSetId " + codeSetId);
            LOG.debug(logSM + ">>Start: designation " + designation);
        }

        Object[] values = { new Long(codeSetId), new String(designation)};

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<DocTypeRelation> docTypeRelations = (List<DocTypeRelation>) getHibernateTemplate()
                                                .findByNamedQuery("getDesignatedDocuments", values);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of DocumentType Ids:" + docTypeRelations.size());
        }

        return docTypeRelations;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.DocumentTypeDAO#retrieveDesignations(long)
     */
    @SuppressWarnings("unchecked")
    public DocTypeDesignations retrieveDesignations(final long codeSetId) {

        final String logSM = "retrieveDesignations(codeSetId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + codeSetId);
        }

        ScrollableResults rs
        = (ScrollableResults) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session s) {
                    return s.getNamedQuery("retrieveDesignations")
                            .setParameter(0, codeSetId)
                            .scroll();
               }
        });

        List<Long> docIds;
        List<MUDocTypeDto> muList = new ArrayList<MUDocTypeDto>();
        Designation desi;
        HashMap<String, List<Long>> designations = new HashMap<String, List<Long>>();
        List<Designation> designationList = new ArrayList<Designation>();
        DocTypeDesignations docDesignation = new DocTypeDesignations();
        while (rs.next()) {

            if (designations.get(rs.getString(1)) != null) {
                // changes for mu doc type
                if (MU.equalsIgnoreCase(rs.getString(1))) {
                    MUDocTypeDto muDocTypeDto = new MUDocTypeDto();
                    muDocTypeDto.setMuDocId(rs.getLong(0));

                    MUDocTypeModel muModel = new MUDocTypeModel();
                    muModel.setId(rs.getInteger(2).intValue());
                    List<String> mudocName = (List<String>) getHibernateTemplate()
                            .findByNamedQuery("getMUDocName", muModel.getId());
                    muDocTypeDto.setMuDocName(mudocName.get(0));

                    muList.add(muDocTypeDto);
                    docIds = designations.get(rs.getString(1));
                    docIds.add(toPlong(rs.getLong(0)));
                    designations.put(rs.getString(1), docIds);

                } else {
                    docIds = designations.get(rs.getString(1));
                    docIds.add(toPlong(rs.getLong(0)));
                }
            } else {

                docIds =  new ArrayList<Long>();
                docIds.add(toPlong(rs.getLong(0)));
                // changes for mu doc type
                if (MU.equalsIgnoreCase(rs.getString(1))) {
                    MUDocTypeDto muDocTypeDto = new MUDocTypeDto();
                    muDocTypeDto.setMuDocId(rs.getLong(0));

                    MUDocTypeModel muModel = new MUDocTypeModel();
                    muModel.setId(rs.getInteger(2).intValue());
                    List<String> mudocName = (List<String>) getHibernateTemplate()
                            .findByNamedQuery("getMUDocName", muModel.getId());
                    muDocTypeDto.setMuDocName(mudocName.get(0));

                    muList.add(muDocTypeDto);
                    designations.put(rs.getString(1), docIds);

                } else {
                    designations.put(rs.getString(1), docIds);
                }
            }
        }

        for (String key : designations.keySet()) {

            desi = new Designation();
            desi.setDocTypeIds(designations.get(key));
            desi.setType(key);
            // changes for mu doc type
            if (MU.equalsIgnoreCase(key)) {
                desi.setMuDocTypes(muList);
            }

            designationList.add(desi);
        }

        docDesignation.setDesignation(designationList);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }

        return docDesignation;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.DocumentTypeDAO
     * #retrieveDocTypeIdsByDesignation(java.lang.String)
     */
    public List<Long> retrieveDocTypeIdsByDesignation(String designation) {

        final String logSM = "retrieveDocTypeIdsByDesignation(designation)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Designation of documents to be retrieved : " + designation);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Long> documentIds =
            (List<Long>) getHibernateTemplate().findByNamedQuery("retrieveDocTypeIdsByDesignation", designation);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Size of the retrieved documents : " +  documentIds.size());
        }
        return documentIds;
    }

    /**
     * This method is to retrieve the MU document types ids
     * 
     * @param
     * @return List<String>
     */
    @SuppressWarnings("unchecked")
    public List<String> retrieveMUDocTypes() {
        final String logSM = "retrieveMUDocTypes";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        List<String> muDocTypes = (List<String>) getHibernateTemplate().findByNamedQuery(
                "retrieveMUDocTypes");

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Size of the retrieved documents : "
                    + muDocTypes.size());
        }
        return muDocTypes;

    }
    
    /**
     * This method is to retrieve all the gender details
     * 
     * @param
     * @return List<Gender>
     */
    @SuppressWarnings("unchecked")
    public List<Gender> retrieveAllGenders() {
        final String logSM = "retrieveAllGenders";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        Session session = getSession();
        String queryString = session.getNamedQuery("retrieveAllGenders").getQueryString();;
        NativeQuery query = session.createSQLQuery(queryString);
        query.addScalar("code", StringType.INSTANCE);
        query.addScalar("description", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(Gender.class));
        List<Gender> genderDetails = query.list();
        if (DO_DEBUG) {
            if (CollectionUtilities.hasContent(genderDetails)) {
                LOG.debug(logSM + "<<End:Size of the retrieved gender details : "
                        + genderDetails.size());
            }
        }
        return genderDetails;

    }

}
