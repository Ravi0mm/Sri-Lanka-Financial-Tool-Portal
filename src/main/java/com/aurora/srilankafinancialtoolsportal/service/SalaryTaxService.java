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

    /*
     * 2025/2026 PAYE monthly slabs (LKR):
     *  - Up to 150,000      @ 0%
     *  - 150,001 – 233,333  @ 6%
     *  - 233,334 – 275,000  @ 12%
     *  - 275,001 – 316,667  @ 18%
     *  - 316,668 – 358,333  @ 24%
     *  - 358,334 – 400,000  @ 30%
     *  - Above 400,000      @ 36%
     *
     * The calculator works with annualized income, so each monthly band is multiplied by 12 below.
     * The slight "996/004" endings come from keeping the exact monthly thresholds rather than
     * rounding them to clean 500k blocks.
     */
    private static final List<TaxBand> TAX_BANDS = List.of(
            new TaxBand("Up to LKR 1,800,000 (LKR 150,000/mo)", BigDecimal.valueOf(1_800_000), BigDecimal.ZERO),
            new TaxBand("Next LKR 999,996 (to LKR 2,799,996 annual / 233,333/mo)", BigDecimal.valueOf(999_996), BigDecimal.valueOf(6)),
            new TaxBand("Next LKR 500,004 (to LKR 3,300,000 annual / 275,000/mo)", BigDecimal.valueOf(500_004), BigDecimal.valueOf(12)),
            new TaxBand("Next LKR 500,004 (to LKR 3,800,004 annual / 316,667/mo)", BigDecimal.valueOf(500_004), BigDecimal.valueOf(18)),
            new TaxBand("Next LKR 499,992 (to LKR 4,299,996 annual / 358,333/mo)", BigDecimal.valueOf(499_992), BigDecimal.valueOf(24)),
            new TaxBand("Next LKR 500,004 (to LKR 4,800,000 annual / 400,000/mo)", BigDecimal.valueOf(500_004), BigDecimal.valueOf(30)),
            new TaxBand("Balance above LKR 4,800,000 (above LKR 400,000/mo)", null, BigDecimal.valueOf(36))
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
