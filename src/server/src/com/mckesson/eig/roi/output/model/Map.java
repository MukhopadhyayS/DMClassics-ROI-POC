package com.mckesson.eig.roi.output.model;

import javax.xml.bind.annotation.XmlElement;

public class Map {
    @XmlElement
    private String key;
    @XmlElement
    private String value;

    private Map() {
    } // Required by JAXB

    public Map(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

}