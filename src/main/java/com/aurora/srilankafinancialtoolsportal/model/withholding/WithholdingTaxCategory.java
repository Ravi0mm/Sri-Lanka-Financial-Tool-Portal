package com.aurora.srilankafinancialtoolsportal.model.withholding;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public enum WithholdingTaxCategory {
    CUSTOM(
            "CUSTOM",
            "Custom rate",
            null,
            null,
            null,
            true
    ),
    INTEREST_GENERAL(
            "INTEREST_GENERAL",
            "Interest/discount (general)",
            BigDecimal.valueOf(10),
            null,
            null,
            false
    ),
    INTEREST_SENIOR(
            "INTEREST_SENIOR",
            "Interest to senior citizen (regulation rate)",
            null,
            null,
            null,
            true
    ),
    RENT_RESIDENT(
            "RENT_RESIDENT",
            "Rent (resident)",
            BigDecimal.valueOf(10),
            null,
            null,
            false
    ),
    RENT_NON_RESIDENT(
            "RENT_NON_RESIDENT",
            "Rent (non-resident)",
            BigDecimal.valueOf(14),
            null,
            null,
            false
    ),
    DIVIDEND(
            "DIVIDEND",
            "Dividends",
            BigDecimal.valueOf(14),
            null,
            null,
            false
    ),
    ROYALTY(
            "ROYALTY",
            "Royalties",
            BigDecimal.valueOf(14),
            null,
            null,
            false
    ),
    CHARGES_PREMIUMS_RETIREMENT_NATURAL(
            "CHARGES_PREMIUMS_RETIREMENT_NATURAL",
            "Charges/natural resource/premiums/retirement",
            BigDecimal.valueOf(14),
            null,
            null,
            false
    ),
    WINNINGS(
            "WINNINGS",
            "Lottery/reward/betting winnings",
            BigDecimal.valueOf(14),
            null,
            null,
            false
    ),
    SERVICE_FEE_RESIDENT(
            "SERVICE_FEE_RESIDENT",
            "Resident services/commission/contract (non-employee)",
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(50_000),
            "Applied on amounts exceeding LKR 50,000 per month",
            false
    ),
    SERVICE_FEE_NON_RESIDENT(
            "SERVICE_FEE_NON_RESIDENT",
            "Service fees (non-resident)",
            BigDecimal.valueOf(14),
            null,
            null,
            false
    ),
    INSURANCE_PREMIUM_NON_RESIDENT(
            "INSURANCE_PREMIUM_NON_RESIDENT",
            "Insurance premiums (non-resident)",
            BigDecimal.valueOf(14),
            null,
            null,
            false
    ),
    PARTNERSHIP_SHARE(
            "PARTNERSHIP_SHARE",
            "Partner's share of partnership income",
            BigDecimal.valueOf(8),
            null,
            null,
            false
    ),
    GEM_AUCTION(
            "GEM_AUCTION",
            "Gem auctions (NGJA)",
            BigDecimal.valueOf(2.5),
            null,
            null,
            false
    ),
    TRANSPORT_TELECOM_NON_RESIDENT(
            "TRANSPORT_TELECOM_NON_RESIDENT",
            "Non-resident transport/telecom (regulation rate)",
            null,
            null,
            null,
            true
    );

    private final String code;
    private final String label;
    private final BigDecimal rate;
    private final BigDecimal thresholdAmount;
    private final String thresholdNote;
    private final boolean requiresCustomRate;

    WithholdingTaxCategory(
            String code,
            String label,
            BigDecimal rate,
            BigDecimal thresholdAmount,
            String thresholdNote,
            boolean requiresCustomRate) {
        this.code = code;
        this.label = label;
        this.rate = rate;
        this.thresholdAmount = thresholdAmount;
        this.thresholdNote = thresholdNote;
        this.requiresCustomRate = requiresCustomRate;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public String getThresholdNote() {
        return thresholdNote;
    }

    public boolean isRequiresCustomRate() {
        return requiresCustomRate;
    }

    public static Optional<WithholdingTaxCategory> fromCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(category -> category.code.equalsIgnoreCase(code))
                .findFirst();
    }
}
