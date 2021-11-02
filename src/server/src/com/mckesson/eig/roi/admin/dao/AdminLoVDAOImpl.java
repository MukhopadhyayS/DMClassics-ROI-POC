/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mckesson.eig.roi.admin.model.AdminLoV;
import com.mckesson.eig.roi.admin.model.Note;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Mar 23, 2009
 * @since  HPF 13.1 [ROI]; Sep 12, 2008
 */
@Transactional
public class AdminLoVDAOImpl
extends ROIDAOImpl
implements AdminLoVDAO {

    private static final OCLogger LOG = new OCLogger(AdminLoVDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * @see com.mckesson.eig.roi.admin.dao.AdminLoVDAO
     * #createNote(com.mckesson.eig.roi.admin.model.AdminLoV)
     */
    public long createNote(AdminLoV adminLoV) {

        final String logSM = "createNote(adminLoV)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + adminLoV);
        }

        long id = toPlong((Long) create(adminLoV));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }

        return id;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.AdminLoVDAO#retrieveLoV(long)
     */
    public AdminLoV retrieveLoV(long id) {

        final String logSM = "retrieveLoV(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        AdminLoV lov = (AdminLoV) get(AdminLoV.class, id);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + lov);
        }

        return  lov;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.AdminLoVDAO#retrieveAllNotes(java.lang.String)
     */
    public List< ? extends AdminLoV> retrieveAllNotes() {

        final String logSM = "retrieveAllNotes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked")  //not supported by 3PartyAPI
        List<Object[]> notes = (List<Object[]>) getHibernateTemplate().findByNamedQuery("retrieveAllNotes");

        List<Note> res = new ArrayList <Note>();
        for (Object[] value : notes) {

            Note note = new Note();
            note.setId(toPlong((Long) value[0]));
            note.setName((String) value[1]);
            note.setDescription((String) value[2]);

            res.add(note);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + notes.size());
        }
        return res;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.AdminLoVDAO#deleteNote(long)
     */
    public AdminLoV deleteNote(long noteId) {

        final String logSM = "deleteNote(long noteId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + noteId);
        }

        AdminLoV lov = retrieveLoV(noteId);
        delete(lov);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }

        return lov;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.AdminLoVDAO
     * #updateNote(com.mckesson.eig.roi.admin.model.AdminLoV)
     */
    public AdminLoV updateNote(AdminLoV lov) {

        final String logSM = "updateNote(AdminLoV lov)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + lov);
        }

        AdminLoV updatedLov = (AdminLoV) merge(lov);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + updatedLov);
        }

        return updatedLov;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.AdminLoVDAO#getNoteByName(java.lang.String)
     */
    public AdminLoV getNoteByName(String name) {

        final String logSM = "getNoteByName(String name)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + name);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<AdminLoV> notes = (List<AdminLoV>) getHibernateTemplate().findByNamedQuery("getNoteByName", name);

        AdminLoV lov = null;
        if (notes.size() > 0) {
            lov = notes.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + lov);
        }
        return lov;
    }
}
