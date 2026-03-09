package com.aurora.srilankafinancialtoolsportal.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CurrencyUtil {

    private CurrencyUtil() {
    }

    public static BigDecimal roundMoney(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
