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

import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.MediaTypesList;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Mar 26, 2008
 */
public class MediaTypeDAOImpl
extends ROIDAOImpl
implements MediaTypeDAO {

    private static final OCLogger LOG = new OCLogger(MediaTypeDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final int MEDIATYPEID      = 0;
    private static final int MEDIATYPENAME    = 1;
    private static final int DESCRIPTION      = 2;

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO
     * #createMediaType(com.mckesson.eig.roi.admin.model.MediaType)
     */
    public long createMediaType(MediaType mediaType) {

        final String logSM = "createMediaType(mediaType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaType.getName());
        }

        long id = toPlong((Long) create(mediaType));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Id:" + id);
        }
        return id;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO#retrieveMediaType(long)
     */
    public MediaType retrieveMediaType(long mediaTypeId) {

        final String logSM = "retrieveMediaType(mediaTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaTypeId);
        }

        MediaType mediaType = (MediaType) get(MediaType.class, mediaTypeId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Name:" + mediaType.getName());
        }
        return mediaType;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO#retrieveAllMediaTypes()
     */
    public MediaTypesList retrieveAllMediaTypes() {

        final String logSM = "retrieveAllMediaTypes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> mediaTypes = getHibernateTemplate().
                                        findByNamedQuery("retrieveAllMediaTypes");

        List<MediaType> mediaList = new ArrayList<MediaType>();
        for (Object[] values : mediaTypes) {

            MediaType mediaType = new MediaType();
            mediaType.setId(toPlong(((Long) values[MEDIATYPEID]).longValue()));
            mediaType.setName((String) values[MEDIATYPENAME]);
            mediaType.setDescription((String) values[DESCRIPTION]);
            mediaList.add(mediaType);
        }


        MediaTypesList ml = new MediaTypesList(mediaList);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of records:" + ml.getMediaTypesList().size());
        }
        return ml;

    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO#retrieveAllMediaTypes()
     */
    public MediaTypesList retrieveAllMediaTypeNames() {

        final String logSM = "retrieveAllMediaTypes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> mediaTypes = getHibernateTemplate().
                                        findByNamedQuery("retrieveAllMediaTypeName");

        List<MediaType> mediaList = new ArrayList<MediaType>();
        for (Object[] values : mediaTypes) {

            MediaType feeType = new MediaType();
            feeType.setId(toPlong(((Long) values[MEDIATYPEID]).longValue()));
            feeType.setName((String) values[MEDIATYPENAME]);
            mediaList.add(feeType);
        }

        MediaTypesList ml = new MediaTypesList(mediaList);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of records:" + ml.getMediaTypesList().size());
        }
        return ml;

    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO
     * #updateMediaType(com.mckesson.eig.roi.admin.model.MediaType,
     *                  com.mckesson.eig.roi.admin.model.MediaType)
     */
    public MediaType updateMediaType(MediaType mediaType, MediaType old) {

        final String logSM = "updateMediaType(mediaType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaType);
        }

        mediaType.copyFrom(old);
        MediaType uMT = (MediaType) merge(mediaType);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return uMT;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO#getMediaTypeByName(java.lang.String)
     */
    public MediaType getMediaTypeByName(String mediaTypeName) {

        final String logSM = "getMediaTypeByName(mediaTypeName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaTypeName);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List <MediaType> mediaTypes = getHibernateTemplate().findByNamedQuery("getMediaTypeByName",
                                                                              mediaTypeName);

        MediaType mType = null;
        if (mediaTypes.size() > 0) {
            mType = mediaTypes.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return mType;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO#deleteMediaType(long)
     */
    public MediaType deleteMediaType(long mediaTypeId) {

        final String logSM = "deleteMediaType(mediaTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaTypeId);
        }

        MediaType mediaType = retrieveMediaType(mediaTypeId);
        delete(mediaType);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return mediaType;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.MediaTypeDAO#getAssociatedBillingTierCount(long)
     */
    public long getAssociatedBillingTierCount(long mediaTypeId) {

        final String logSM = "getAssociatedBillingTierCount(mediaTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaTypeId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Long> ids = getHibernateTemplate().findByNamedQuery("getAssociatedBillingTierCount",
                                                                 new Long(mediaTypeId));

        long count = toPlong(ids.get(0).longValue());

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + count);
        }
        return count;
    }
}
