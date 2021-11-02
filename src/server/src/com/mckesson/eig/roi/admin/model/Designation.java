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

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.utility.util.StringUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Designation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Designation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docTypeIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="muDocTypes" type="{urn:eig.mckesson.com}MUDocTypeDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Designation", propOrder = {
    "type",
    "docTypeIds",
    "muDocTypes"
})
public class Designation
implements Serializable {

    @XmlElement(required = true)
    private String type;
    @XmlElement(type = Long.class)
    private List<Long> docTypeIds;
    //changes for mu doc type
    private List<MUDocTypeDto> muDocTypes;
    private static final String MU = "mu";

    public Designation() { }

    public Designation(String designation, List<Long> docIds) {

        setType(designation);
        setDocTypeIds(docIds);
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Long> getDocTypeIds() { return docTypeIds; }
    public void setDocTypeIds(List<Long> docTypeIds) { this.docTypeIds = docTypeIds; }

    /**
     * This method is to obtain the names of the document types
     * @return Names of the DocumentType
     */
    private String getDocTypeIdsAsCSV() {

        boolean flag = false;
        StringBuffer stbuf = new StringBuffer();

        if (docTypeIds == null) {
            return "";
        } else {

            for (long docTypeId : docTypeIds) {
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
        return muDocTypes;
    }

    public void setMuDocTypes(List<MUDocTypeDto> docTypes) {
        this.muDocTypes = docTypes;
    }
}
