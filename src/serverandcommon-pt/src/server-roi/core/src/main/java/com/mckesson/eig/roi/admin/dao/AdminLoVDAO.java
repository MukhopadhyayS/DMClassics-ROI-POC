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

import java.util.List;

import com.mckesson.eig.roi.admin.model.AdminLoV;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   May 23, 2008
 * @since  HPF 13.1 [ROI]; Sep 12, 2008
 */
public interface AdminLoVDAO extends ROIDAO {

    /**
     * This method create new adminLoV
     * @param adminLoV AdminLoV details to be created
     * @return created AdminLoV id
     */
     long createNote(AdminLoV adminLoV);

    /**
     * This method fetches the adminLoV details based on the id
     * @param id id of the AdminLoV
     * @return AdminLoV
     */
    AdminLoV retrieveLoV(long id);

    /**
     * This method retrieve all AdminLoV
     * @return list of AdminLoV
     */
    List< ? extends AdminLoV> retrieveAllNotes();

    /**
     * This method deletes the notes based on the notesId
     * @param notesId id of the AdminLoV
     * @return deleted AdminLoV details
     */
    AdminLoV deleteNote(long notesId);

    /**
     * This method updates the note
     * @param lov AdminLoV details to be updated
     * @return AdminLoV
     */
    AdminLoV updateNote(AdminLoV lov);

    /**
     * This method fetches the name from the db
     * @param name adminLoV name
     * @return adminLov
     */
    AdminLoV getNoteByName(String name);
}
