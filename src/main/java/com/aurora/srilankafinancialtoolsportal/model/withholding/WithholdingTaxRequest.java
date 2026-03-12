package com.aurora.srilankafinancialtoolsportal.model.withholding;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class WithholdingTaxRequest {

    @NotNull(message = "Gross payment amount is required")
    @DecimalMin(value = "0.01", message = "Gross payment amount must be greater than 0")
    private BigDecimal grossPaymentAmount;

    private String presetCategory = "CUSTOM";

    @DecimalMin(value = "0.0", message = "Withholding rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Withholding rate cannot exceed 100%")
    private BigDecimal withholdingRate;

    public BigDecimal getGrossPaymentAmount() {
        return grossPaymentAmount;
    }

    public void setGrossPaymentAmount(BigDecimal grossPaymentAmount) {
        this.grossPaymentAmount = grossPaymentAmount;
    }

    public BigDecimal getWithholdingRate() {
        return withholdingRate;
    }

    public void setWithholdingRate(BigDecimal withholdingRate) {
        this.withholdingRate = withholdingRate;
    }

    public String getPresetCategory() {
        return presetCategory;
    }

    public void setPresetCategory(String presetCategory) {
        this.presetCategory = presetCategory;
    }
}
