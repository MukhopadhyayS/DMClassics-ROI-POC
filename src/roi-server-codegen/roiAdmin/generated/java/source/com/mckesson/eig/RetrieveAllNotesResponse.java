
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="notes" type="{urn:eig.mckesson.com}NotesList"/>
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
    "notes"
})
@XmlRootElement(name = "retrieveAllNotesResponse")
public class RetrieveAllNotesResponse {

    @XmlElement(required = true)
    protected NotesList notes;

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link NotesList }
     *     
     */
    public NotesList getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotesList }
     *     
     */
    public void setNotes(NotesList value) {
        this.notes = value;
    }

}
