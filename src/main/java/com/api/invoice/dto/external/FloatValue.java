package com.api.invoice.dto.external;

public class FloatValue {
    private Double confidence;
    private String label;
    private Double value;

    public Double getConfidence() {
        return confidence;
    }

    public String getLabel() {
        return label;
    }

    public Double getValue() {
        return value;
    }

    public void setConfidence( Double confidence ) {
        this.confidence = confidence;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public void setValue( Double value ) {
        this.value = value;
    }
}
