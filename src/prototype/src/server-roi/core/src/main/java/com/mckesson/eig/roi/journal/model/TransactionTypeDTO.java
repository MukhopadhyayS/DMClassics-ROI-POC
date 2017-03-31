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

package com.mckesson.eig.roi.journal.model;

import java.util.List;

/**
 * 
 * @author edksi0l
 *
 */
public class TransactionTypeDTO {

    private String _code;
    private String _description;
    private String _name;
    private List<EntityDTO> _entities;
    private List<JournalTemplateDTO> _journalTemplates;
    
    public String getCode() {
        return _code;
    }
    public void setCode(String code) {
        _code = code;
    }
    public String getDescription() {
        return _description;
    }
    public void setDescription(String description) {
        _description = description;
    }
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }

    public List<EntityDTO> getEntities() {
        return _entities;
    }
    public void setEntities(List<EntityDTO> entities) {
        _entities = entities;
    }
    public List<JournalTemplateDTO> getJournalTemplates() {
        return _journalTemplates;
    }
    public void setJournalTemplates(List<JournalTemplateDTO> journalTemplates) {
        _journalTemplates = journalTemplates;
    }
    
}
