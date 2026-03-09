package com.aurora.srilankafinancialtoolsportal.model.epf;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class EpfRequest {

    @NotNull(message = "Monthly salary is required")
    @DecimalMin(value = "0.01", message = "Monthly salary must be greater than 0")
    private BigDecimal monthlySalary;

    @NotNull(message = "Years of service is required")
    @Min(value = 1, message = "Years of service must be at least 1")
    @Max(value = 60, message = "Years of service must be less than or equal to 60")
    private Integer yearsOfService;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "50.0", message = "Interest rate seems too high")
    private BigDecimal annualInterestRate = BigDecimal.valueOf(8.0);

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Integer getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(Integer yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate == null ? BigDecimal.valueOf(8.0) : annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
}
