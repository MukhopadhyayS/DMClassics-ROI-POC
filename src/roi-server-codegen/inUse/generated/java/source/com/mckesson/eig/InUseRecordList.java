
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InUseRecordList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InUseRecordList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inUseRecordList" type="{urn:eig.mckesson.com}InUseRecord" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InUseRecordList", propOrder = {
    "inUseRecordList"
})
public class InUseRecordList {

    protected List<InUseRecord> inUseRecordList;

    /**
     * Gets the value of the inUseRecordList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inUseRecordList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInUseRecordList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InUseRecord }
     * 
     * 
     */
    public List<InUseRecord> getInUseRecordList() {
        if (inUseRecordList == null) {
            inUseRecordList = new ArrayList<InUseRecord>();
        }
        return this.inUseRecordList;
    }

}
