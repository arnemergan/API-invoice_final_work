package com.api.invoice.dto.external;

public class StringValue {
    private Double confidence;
    private String label;
    private String value;

    public Double getConfidence() {
        return confidence;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public void setConfidence( Double confidence ) {
        this.confidence = confidence;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public void setValue( String value ) {
        this.value = value;
    }
}
