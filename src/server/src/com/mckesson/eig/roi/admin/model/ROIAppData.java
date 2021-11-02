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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * @author OFS
 * @date   Aug 12, 2008
 * @since  HPF 13.5 [ROI]; Aug 12, 2008
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ROIAppData", propOrder = {
    "hasSSNMasking",
    "freeFormFacilities",
    "invoiceDueDays"
})
public class ROIAppData
implements Serializable {

    private boolean hasSSNMasking;
    //@XmlElementRef(name = "freeFormFacilities", namespace = "urn:eig.mckesson.com", type = JAXBElement.class, required = false)
    //private List<JAXBElement<List<String>>> freeFormFacilities;
    //@XmlElement(type = String.class, required = false)
    private List<String> freeFormFacilities;
    //@XmlElement(type = Integer.class)
    private List<Integer> invoiceDueDays;

    /**
     * Gets the value of the hasSSNMasking property.
     * 
     */
    public boolean getHasSSNMasking() {
        return hasSSNMasking;
    }

    /**
     * Sets the value of the hasSSNMasking property.
     * 
     */
    public void setHasSSNMasking(boolean value) {
        this.hasSSNMasking = value;
    }

    /**
     * Gets the value of the freeFormFacilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeFormFacilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeFormFacilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}
     * 
     * 
     */
    public List<String> getFreeFormFacilities() {
        if (freeFormFacilities == null) {
            freeFormFacilities = new ArrayList<String>();
        }
        return this.freeFormFacilities;
    }

    /**
     * Gets the value of the invoiceDueDays property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceDueDays property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceDueDays().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getInvoiceDueDays() {
        if (invoiceDueDays == null) {
            invoiceDueDays = new ArrayList<Integer>();
        }
        return this.invoiceDueDays;
    }

    public void setFreeFormFacilities(
            List<String> formFacilities) {
        this.freeFormFacilities = formFacilities;
    }

    public void setInvoiceDueDays(List<Integer> invoiceDueDays) {
        this.invoiceDueDays = invoiceDueDays;
    }
    
    

}
