package com.aurora.srilankafinancialtoolsportal.service;

import com.aurora.srilankafinancialtoolsportal.model.salary.SalaryTaxRequest;
import com.aurora.srilankafinancialtoolsportal.model.salary.SalaryTaxResult;
import com.aurora.srilankafinancialtoolsportal.model.salary.TaxSlabBreakdown;
import com.aurora.srilankafinancialtoolsportal.util.CurrencyUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryTaxService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    private static final List<TaxBand> TAX_BANDS = List.of(
            new TaxBand("First LKR 1,200,000", BigDecimal.valueOf(1_200_000), BigDecimal.ZERO),
            new TaxBand("Next LKR 500,000", BigDecimal.valueOf(500_000), BigDecimal.valueOf(6)),
            new TaxBand("Next LKR 500,000", BigDecimal.valueOf(500_000), BigDecimal.valueOf(12)),
            new TaxBand("Next LKR 500,000", BigDecimal.valueOf(500_000), BigDecimal.valueOf(18)),
            new TaxBand("Next LKR 500,000", BigDecimal.valueOf(500_000), BigDecimal.valueOf(24)),
            new TaxBand("Next LKR 500,000", BigDecimal.valueOf(500_000), BigDecimal.valueOf(30)),
            new TaxBand("Balance above LKR 3,700,000", null, BigDecimal.valueOf(36))
    );

    public SalaryTaxResult calculate(SalaryTaxRequest request) {
        BigDecimal annualTaxableIncome = request.getMonthlySalary().multiply(MONTHS_IN_YEAR)
                .add(request.getAnnualBonus())
                .add(request.getOtherAllowances());

        BigDecimal remaining = annualTaxableIncome;
        BigDecimal totalTax = BigDecimal.ZERO;
        List<TaxSlabBreakdown> breakdown = new ArrayList<>();

        for (TaxBand band : TAX_BANDS) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal taxableAmount;
            if (band.width() == null) {
                taxableAmount = remaining;
            } else {
                taxableAmount = remaining.min(band.width());
            }

            BigDecimal taxAmount = taxableAmount
                    .multiply(band.rate())
                    .divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);

            breakdown.add(new TaxSlabBreakdown(
                    band.label(),
                    CurrencyUtil.roundMoney(taxableAmount),
                    band.rate(),
                    CurrencyUtil.roundMoney(taxAmount)
            ));

            totalTax = totalTax.add(taxAmount);
            remaining = remaining.subtract(taxableAmount);
        }

        BigDecimal monthlyTaxDeduction = totalTax.divide(MONTHS_IN_YEAR, 8, RoundingMode.HALF_UP);
        BigDecimal netMonthlySalary = annualTaxableIncome.subtract(totalTax)
                .divide(MONTHS_IN_YEAR, 8, RoundingMode.HALF_UP);

        return new SalaryTaxResult(
                CurrencyUtil.roundMoney(annualTaxableIncome),
                CurrencyUtil.roundMoney(totalTax),
                CurrencyUtil.roundMoney(monthlyTaxDeduction),
                CurrencyUtil.roundMoney(netMonthlySalary),
                breakdown
        );
    }

    private record TaxBand(String label, BigDecimal width, BigDecimal rate) {
    }
}
