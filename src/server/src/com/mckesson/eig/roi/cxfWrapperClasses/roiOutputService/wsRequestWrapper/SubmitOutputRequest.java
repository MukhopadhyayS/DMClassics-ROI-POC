
package com.mckesson.eig.roi.cxfWrapperClasses.roiOutputService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.output.model.OutputRequest;


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
 *         &lt;element name="OutputRequest" type="{urn:eig.mckesson.com}OutputRequest"/>
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
    "outputRequest"
})
@XmlRootElement(name = "submitOutputRequest")
public class SubmitOutputRequest {

    @XmlElement(name = "OutputRequest", required = true)
    protected OutputRequest outputRequest;

    /**
     * Gets the value of the outputRequest property.
     * 
     * @return
     *     possible object is
     *     {@link OutputRequest }
     *     
     */
    public OutputRequest getOutputRequest() {
        return outputRequest;
    }

    /**
     * Sets the value of the outputRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputRequest }
     *     
     */
    public void setOutputRequest(OutputRequest value) {
        this.outputRequest = value;
    }

}
