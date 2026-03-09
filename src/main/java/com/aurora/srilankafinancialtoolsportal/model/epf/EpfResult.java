package com.aurora.srilankafinancialtoolsportal.model.epf;

import java.math.BigDecimal;

public class EpfResult {

    private final BigDecimal totalContribution;
    private final BigDecimal totalInterestEarned;
    private final BigDecimal finalMaturityAmount;

    public EpfResult(BigDecimal totalContribution, BigDecimal totalInterestEarned, BigDecimal finalMaturityAmount) {
        this.totalContribution = totalContribution;
        this.totalInterestEarned = totalInterestEarned;
        this.finalMaturityAmount = finalMaturityAmount;
    }

    public BigDecimal getTotalContribution() {
        return totalContribution;
    }

    public BigDecimal getTotalInterestEarned() {
        return totalInterestEarned;
    }

    public BigDecimal getFinalMaturityAmount() {
        return finalMaturityAmount;
    }
}
