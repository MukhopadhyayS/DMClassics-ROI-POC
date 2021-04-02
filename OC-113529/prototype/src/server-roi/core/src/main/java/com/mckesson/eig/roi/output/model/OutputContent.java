/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.roi.output.model;
/**
 * This class contains the output content
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class OutputContent
extends AbstractOutputAttributes {
    
    /**This holds the content type*/
    private String _contentType;
    
    public String getContentType() {
        return _contentType;
    }
    
    public void setContentType(String contentType) {
        _contentType = contentType;
    }

}
