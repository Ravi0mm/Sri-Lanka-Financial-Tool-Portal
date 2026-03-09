package com.aurora.srilankafinancialtoolsportal.model.salary;

import java.math.BigDecimal;

public class TaxSlabBreakdown {

    private final String slabLabel;
    private final BigDecimal taxableAmount;
    private final BigDecimal ratePercentage;
    private final BigDecimal taxAmount;

    public TaxSlabBreakdown(String slabLabel, BigDecimal taxableAmount, BigDecimal ratePercentage, BigDecimal taxAmount) {
        this.slabLabel = slabLabel;
        this.taxableAmount = taxableAmount;
        this.ratePercentage = ratePercentage;
        this.taxAmount = taxAmount;
    }

    public String getSlabLabel() {
        return slabLabel;
    }

    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }

    public BigDecimal getRatePercentage() {
        return ratePercentage;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
}
