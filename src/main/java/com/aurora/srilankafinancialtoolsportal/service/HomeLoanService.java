package com.aurora.srilankafinancialtoolsportal.service;

import com.aurora.srilankafinancialtoolsportal.model.loan.HomeLoanRequest;
import com.aurora.srilankafinancialtoolsportal.model.loan.HomeLoanResult;
import com.aurora.srilankafinancialtoolsportal.util.CurrencyUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class HomeLoanService {

    public HomeLoanResult calculate(HomeLoanRequest request) {
        BigDecimal principal = request.getLoanAmount();
        int tenureMonths = request.getLoanTenureYears() * 12;

        BigDecimal monthlyRate = request.getInterestRate()
                .divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);

        BigDecimal emi;
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            emi = principal.divide(BigDecimal.valueOf(tenureMonths), 10, RoundingMode.HALF_UP);
        } else {
            double rate = monthlyRate.doubleValue();
            double compoundFactor = Math.pow(1 + rate, tenureMonths);
            double emiValue = principal.doubleValue() * rate * compoundFactor / (compoundFactor - 1);
            emi = BigDecimal.valueOf(emiValue);
        }

        BigDecimal totalRepayment = emi.multiply(BigDecimal.valueOf(tenureMonths));
        BigDecimal totalInterestPayable = totalRepayment.subtract(principal);

        return new HomeLoanResult(
                CurrencyUtil.roundMoney(emi),
                CurrencyUtil.roundMoney(totalInterestPayable),
                CurrencyUtil.roundMoney(totalRepayment)
        );
    }
}
