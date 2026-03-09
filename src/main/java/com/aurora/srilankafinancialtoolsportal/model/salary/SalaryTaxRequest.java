package com.aurora.srilankafinancialtoolsportal.model.salary;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SalaryTaxRequest {

    @NotNull(message = "Monthly salary is required")
    @DecimalMin(value = "0.01", message = "Monthly salary must be greater than 0")
    private BigDecimal monthlySalary;

    @DecimalMin(value = "0.0", message = "Annual bonus cannot be negative")
    private BigDecimal annualBonus = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "Other allowances cannot be negative")
    private BigDecimal otherAllowances = BigDecimal.ZERO;

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public BigDecimal getAnnualBonus() {
        return annualBonus == null ? BigDecimal.ZERO : annualBonus;
    }

    public void setAnnualBonus(BigDecimal annualBonus) {
        this.annualBonus = annualBonus;
    }

    public BigDecimal getOtherAllowances() {
        return otherAllowances == null ? BigDecimal.ZERO : otherAllowances;
    }

    public void setOtherAllowances(BigDecimal otherAllowances) {
        this.otherAllowances = otherAllowances;
    }
}
