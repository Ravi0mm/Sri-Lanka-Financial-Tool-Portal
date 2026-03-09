package com.aurora.srilankafinancialtoolsportal.model.loan;

import java.math.BigDecimal;

public class HomeLoanResult {

    private final BigDecimal emi;
    private final BigDecimal totalInterestPayable;
    private final BigDecimal totalRepayment;

    public HomeLoanResult(BigDecimal emi, BigDecimal totalInterestPayable, BigDecimal totalRepayment) {
        this.emi = emi;
        this.totalInterestPayable = totalInterestPayable;
        this.totalRepayment = totalRepayment;
    }

    public BigDecimal getEmi() {
        return emi;
    }

    public BigDecimal getTotalInterestPayable() {
        return totalInterestPayable;
    }

    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }
}
