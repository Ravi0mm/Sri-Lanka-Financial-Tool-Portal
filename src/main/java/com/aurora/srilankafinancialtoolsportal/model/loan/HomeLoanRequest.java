package com.aurora.srilankafinancialtoolsportal.model.loan;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class HomeLoanRequest {

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "0.01", message = "Loan amount must be greater than 0")
    private BigDecimal loanAmount;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "40.0", message = "Interest rate seems too high")
    private BigDecimal interestRate;

    @NotNull(message = "Loan tenure is required")
    @Min(value = 1, message = "Tenure should be at least 1 year")
    @Max(value = 40, message = "Tenure should be less than or equal to 40 years")
    private Integer loanTenureYears;

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getLoanTenureYears() {
        return loanTenureYears;
    }

    public void setLoanTenureYears(Integer loanTenureYears) {
        this.loanTenureYears = loanTenureYears;
    }
}
