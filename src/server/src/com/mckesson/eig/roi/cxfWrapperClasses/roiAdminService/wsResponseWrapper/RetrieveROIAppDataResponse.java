
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.ROIAppData;


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
 *         &lt;element name="roiAppData" type="{urn:eig.mckesson.com}ROIAppData"/>
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
    "roiAppData"
})
@XmlRootElement(name = "retrieveROIAppDataResponse")
public class RetrieveROIAppDataResponse {

    @XmlElement(required = true)
    protected ROIAppData roiAppData;

    /**
     * Gets the value of the roiAppData property.
     * 
     * @return
     *     possible object is
     *     {@link ROIAppData }
     *     
     */
    public ROIAppData getRoiAppData() {
        return roiAppData;
    }

    /**
     * Sets the value of the roiAppData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ROIAppData }
     *     
     */
    public void setRoiAppData(ROIAppData value) {
        this.roiAppData = value;
    }

}
