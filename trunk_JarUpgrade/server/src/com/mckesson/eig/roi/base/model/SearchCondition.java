package com.mckesson.eig.roi.base.model;

import java.io.Serializable;

public class SearchCondition implements Serializable {
    private String _operation;
    private String _key;
    private String _value;
    private String _valueTo;
    
    public static enum OPERATION {
        Equal       ("="),
        GreaterThan (">"),
        LessThan    ("<"),
        Like        ("like"),
        In          ("in"),
        Between     ("between"),
        AtLeast     (">="),
        AtMost      ("<=");

        private final String _operation;

        private OPERATION(String operation) {
            _operation = operation; 
        }
        
        @Override
        public String toString() { return _operation; }
    }

    public String getOperation() { return _operation; }

    public void setOperation(String condition) {
        if (condition == null) {
            _operation = OPERATION.Like.toString();
        } else {
            _operation = Enum.valueOf(OPERATION.class, condition).toString();
        }
    }

    public String getKey() {
        return _key;
    }

    public void setKey(String key) {
        _key = key;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String value) {
        _value = value;
    }

    public String getValueTo() {
        return _valueTo;
    }

    public void setValueTo(String valueTo) {
        _valueTo = valueTo;
    }
}
