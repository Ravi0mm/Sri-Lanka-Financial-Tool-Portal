package com.aurora.srilankafinancialtoolsportal.service;

import com.aurora.srilankafinancialtoolsportal.model.epf.EpfRequest;
import com.aurora.srilankafinancialtoolsportal.model.epf.EpfResult;
import com.aurora.srilankafinancialtoolsportal.util.CurrencyUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class EpfCalculatorService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);
    private static final BigDecimal EMPLOYEE_RATE = BigDecimal.valueOf(0.08);
    private static final BigDecimal EMPLOYER_RATE = BigDecimal.valueOf(0.12);

    public EpfResult calculate(EpfRequest request) {
        MathContext mc = MathContext.DECIMAL64;

        BigDecimal totalRate = EMPLOYEE_RATE.add(EMPLOYER_RATE, mc);
        BigDecimal annualContribution = request.getMonthlySalary()
                .multiply(MONTHS_IN_YEAR, mc)
                .multiply(totalRate, mc);

        BigDecimal annualInterestRate = request.getAnnualInterestRate()
                .divide(BigDecimal.valueOf(100), mc);

        BigDecimal totalContribution = BigDecimal.ZERO;
        BigDecimal balance = BigDecimal.ZERO;

        for (int year = 1; year <= request.getYearsOfService(); year++) {
            balance = balance.add(annualContribution, mc);
            totalContribution = totalContribution.add(annualContribution, mc);

            BigDecimal interestForYear = balance.multiply(annualInterestRate, mc);
            balance = balance.add(interestForYear, mc);
        }

        BigDecimal totalInterestEarned = balance.subtract(totalContribution, mc);

        return new EpfResult(
                CurrencyUtil.roundMoney(totalContribution),
                CurrencyUtil.roundMoney(totalInterestEarned),
                CurrencyUtil.roundMoney(balance)
        );
    }
}
