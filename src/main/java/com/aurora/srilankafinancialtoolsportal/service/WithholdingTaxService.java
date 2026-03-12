package com.aurora.srilankafinancialtoolsportal.service;

import com.aurora.srilankafinancialtoolsportal.model.withholding.WithholdingTaxRequest;
import com.aurora.srilankafinancialtoolsportal.model.withholding.WithholdingTaxResult;
import com.aurora.srilankafinancialtoolsportal.model.withholding.WithholdingTaxCategory;
import com.aurora.srilankafinancialtoolsportal.util.CurrencyUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class WithholdingTaxService {

    public WithholdingTaxResult calculate(WithholdingTaxRequest request) {
        BigDecimal grossAmount = request.getGrossPaymentAmount();
        Optional<WithholdingTaxCategory> category = WithholdingTaxCategory.fromCode(request.getPresetCategory());
        BigDecimal rate = category.map(WithholdingTaxCategory::getRate)
                .orElse(request.getWithholdingRate());
        BigDecimal thresholdAmount = category.map(WithholdingTaxCategory::getThresholdAmount)
                .orElse(null);

        BigDecimal taxableAmount = grossAmount;
        if (thresholdAmount != null) {
            taxableAmount = grossAmount.subtract(thresholdAmount);
            if (taxableAmount.compareTo(BigDecimal.ZERO) < 0) {
                taxableAmount = BigDecimal.ZERO;
            }
        }

        BigDecimal taxWithheld = taxableAmount
                .multiply(rate)
                .divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
        BigDecimal netPayment = grossAmount.subtract(taxWithheld);

        return new WithholdingTaxResult(
                CurrencyUtil.roundMoney(grossAmount),
                rate,
                CurrencyUtil.roundMoney(taxableAmount),
                CurrencyUtil.roundMoney(taxWithheld),
                CurrencyUtil.roundMoney(netPayment),
                thresholdAmount == null ? null : CurrencyUtil.roundMoney(thresholdAmount)
        );
    }
}
