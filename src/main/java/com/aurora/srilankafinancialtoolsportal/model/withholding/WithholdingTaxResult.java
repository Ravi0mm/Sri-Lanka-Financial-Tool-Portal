package com.aurora.srilankafinancialtoolsportal.model.withholding;

import java.math.BigDecimal;

public class WithholdingTaxResult {

    private final BigDecimal grossPaymentAmount;
    private final BigDecimal withholdingRate;
    private final BigDecimal taxableAmount;
    private final BigDecimal taxWithheld;
    private final BigDecimal netPaymentAmount;
    private final BigDecimal thresholdAmount;

    public WithholdingTaxResult(
            BigDecimal grossPaymentAmount,
            BigDecimal withholdingRate,
            BigDecimal taxableAmount,
            BigDecimal taxWithheld,
            BigDecimal netPaymentAmount,
            BigDecimal thresholdAmount) {
        this.grossPaymentAmount = grossPaymentAmount;
        this.withholdingRate = withholdingRate;
        this.taxableAmount = taxableAmount;
        this.taxWithheld = taxWithheld;
        this.netPaymentAmount = netPaymentAmount;
        this.thresholdAmount = thresholdAmount;
    }

    public BigDecimal getGrossPaymentAmount() {
        return grossPaymentAmount;
    }

    public BigDecimal getWithholdingRate() {
        return withholdingRate;
    }

    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }

    public BigDecimal getTaxWithheld() {
        return taxWithheld;
    }

    public BigDecimal getNetPaymentAmount() {
        return netPaymentAmount;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }
}
