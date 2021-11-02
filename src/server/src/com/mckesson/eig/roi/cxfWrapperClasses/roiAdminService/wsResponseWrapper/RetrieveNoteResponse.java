
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.Note;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noteDetails" type="{urn:eig.mckesson.com}Note"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "noteDetails"
})
@XmlRootElement(name = "retrieveNoteResponse")
public class RetrieveNoteResponse {

    @XmlElement(required = true)
    protected Note noteDetails;

    /**
     * Gets the value of the noteDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getNoteDetails() {
        return noteDetails;
    }

    /**
     * Sets the value of the noteDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setNoteDetails(Note value) {
        this.noteDetails = value;
    }

}
