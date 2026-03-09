package com.aurora.srilankafinancialtoolsportal.model.salary;

import java.math.BigDecimal;
import java.util.List;

public class SalaryTaxResult {

    private final BigDecimal annualTaxableIncome;
    private final BigDecimal totalTaxPayable;
    private final BigDecimal monthlyTaxDeduction;
    private final BigDecimal netMonthlySalary;
    private final List<TaxSlabBreakdown> breakdown;

    public SalaryTaxResult(
            BigDecimal annualTaxableIncome,
            BigDecimal totalTaxPayable,
            BigDecimal monthlyTaxDeduction,
            BigDecimal netMonthlySalary,
            List<TaxSlabBreakdown> breakdown) {
        this.annualTaxableIncome = annualTaxableIncome;
        this.totalTaxPayable = totalTaxPayable;
        this.monthlyTaxDeduction = monthlyTaxDeduction;
        this.netMonthlySalary = netMonthlySalary;
        this.breakdown = breakdown;
    }

    public BigDecimal getAnnualTaxableIncome() {
        return annualTaxableIncome;
    }

    public BigDecimal getTotalTaxPayable() {
        return totalTaxPayable;
    }

    public BigDecimal getMonthlyTaxDeduction() {
        return monthlyTaxDeduction;
    }

    public BigDecimal getNetMonthlySalary() {
        return netMonthlySalary;
    }

    public List<TaxSlabBreakdown> getBreakdown() {
        return breakdown;
    }
}
