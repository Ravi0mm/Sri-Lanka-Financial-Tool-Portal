package com.aurora.srilankafinancialtoolsportal.model.vehicle;

import java.math.BigDecimal;

public class VehicleDutyResult {

    private final BigDecimal customsDuty;
    private final BigDecimal pal;
    private final BigDecimal exciseDuty;
    private final BigDecimal vat;
    private final BigDecimal totalPayableTax;
    private final BigDecimal finalVehicleCost;
    private final BigDecimal customsRate;
    private final BigDecimal palRate;
    private final BigDecimal exciseRate;
    private final BigDecimal vatRate;

    public VehicleDutyResult(
            BigDecimal customsDuty,
            BigDecimal pal,
            BigDecimal exciseDuty,
            BigDecimal vat,
            BigDecimal totalPayableTax,
            BigDecimal finalVehicleCost,
            BigDecimal customsRate,
            BigDecimal palRate,
            BigDecimal exciseRate,
            BigDecimal vatRate) {
        this.customsDuty = customsDuty;
        this.pal = pal;
        this.exciseDuty = exciseDuty;
        this.vat = vat;
        this.totalPayableTax = totalPayableTax;
        this.finalVehicleCost = finalVehicleCost;
        this.customsRate = customsRate;
        this.palRate = palRate;
        this.exciseRate = exciseRate;
        this.vatRate = vatRate;
    }

    public BigDecimal getCustomsDuty() {
        return customsDuty;
    }

    public BigDecimal getPal() {
        return pal;
    }

    public BigDecimal getExciseDuty() {
        return exciseDuty;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getTotalPayableTax() {
        return totalPayableTax;
    }

    public BigDecimal getFinalVehicleCost() {
        return finalVehicleCost;
    }

    public BigDecimal getCustomsRate() {
        return customsRate;
    }

    public BigDecimal getPalRate() {
        return palRate;
    }

    public BigDecimal getExciseRate() {
        return exciseRate;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }
}
