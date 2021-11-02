package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReleaseHistoryList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseHistoryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="releasedPatients" type="{urn:eig.mckesson.com}ReleaseHistoryPatient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="history" type="{urn:eig.mckesson.com}ReleaseHistoryItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReleaseHistoryList", propOrder = {
    "releasedPatients",
    "history"
})
@SuppressWarnings("serial")
public class ReleaseHistoryList implements Serializable {

    List<ReleaseHistoryPatient> releasedPatients;
    List<ReleaseHistoryItem> history;
    

    public ReleaseHistoryList() {}

    public ReleaseHistoryList(List<ReleaseHistoryPatient> releasedPatients,
                              List<ReleaseHistoryItem> history) {

        this.releasedPatients = releasedPatients;
        this.history = history;
    }

    public List<ReleaseHistoryPatient> getReleasedPatients() {
        return releasedPatients;
    }

    public void setReleasedPatients(List<ReleaseHistoryPatient> releasedPatients) {
        this.releasedPatients = releasedPatients;
    }

    public List<ReleaseHistoryItem> getHistory() {
        return history;
    }

    public void setHistory(List<ReleaseHistoryItem> history) {
        this.history = history;
    }

}
