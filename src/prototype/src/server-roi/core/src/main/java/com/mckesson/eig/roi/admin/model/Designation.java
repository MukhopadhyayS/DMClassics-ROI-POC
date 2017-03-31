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

package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Dec 19, 2008
 * @since  HPF 13.1 [ROI]; Sep 10, 2008
 */
public class Designation
implements Serializable {

    private String _type;
    private List<Long> _docTypeIds;
    //changes for mu doc type
    private List<MUDocTypeDto> _muDocTypes;
    private static final String MU = "mu";

    public Designation() { }

    public Designation(String designation, List<Long> docIds) {

        setType(designation);
        setDocTypeIds(docIds);
    }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public List<Long> getDocTypeIds() { return _docTypeIds; }
    public void setDocTypeIds(List<Long> docTypeIds) { _docTypeIds = docTypeIds; }

    /**
     * This method is to obtain the names of the document types
     * @return Names of the DocumentType
     */
    private String getDocTypeIdsAsCSV() {

        boolean flag = false;
        StringBuffer stbuf = new StringBuffer();

        if (_docTypeIds == null) {
            return "";
        } else {

            for (long docTypeId : _docTypeIds) {
                if (flag) {
                    stbuf.append(", ");
                }
                stbuf.append(docTypeId);
                flag = true;
            }
        }

        return stbuf.toString();
    }

    public List<MUDocTypeDto> getMuDocTypes() {
        return _muDocTypes;
    }

    public void setMuDocTypes(List<MUDocTypeDto> docTypes) {
        _muDocTypes = docTypes;
    }
}
