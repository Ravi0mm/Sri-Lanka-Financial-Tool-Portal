package com.aurora.srilankafinancialtoolsportal.service;

import com.aurora.srilankafinancialtoolsportal.model.vehicle.VehicleDutyRequest;
import com.aurora.srilankafinancialtoolsportal.model.vehicle.VehicleDutyResult;
import com.aurora.srilankafinancialtoolsportal.util.CurrencyUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VehicleImportDutyService {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public VehicleDutyResult calculate(VehicleDutyRequest request) {
        BigDecimal cifValue = request.getCifValue();

        BigDecimal customsRate = getCustomsRate(request.getFuelType());
        BigDecimal palRate = getPalRate(request.getFuelType());
        BigDecimal exciseRate = getExciseRate(request.getFuelType(), request.getVehicleType(), request.getEngineCapacity());
        BigDecimal vatRate = BigDecimal.valueOf(18);

        BigDecimal customsDuty = percentageOf(cifValue, customsRate);
        BigDecimal pal = percentageOf(cifValue, palRate);

        BigDecimal exciseBase = cifValue.add(customsDuty).add(pal);
        BigDecimal exciseDuty = percentageOf(exciseBase, exciseRate);

        BigDecimal vatBase = cifValue.add(customsDuty).add(pal).add(exciseDuty);
        BigDecimal vat = percentageOf(vatBase, vatRate);

        BigDecimal totalTax = customsDuty.add(pal).add(exciseDuty).add(vat);
        BigDecimal finalVehicleCost = cifValue.add(totalTax);

        return new VehicleDutyResult(
                CurrencyUtil.roundMoney(customsDuty),
                CurrencyUtil.roundMoney(pal),
                CurrencyUtil.roundMoney(exciseDuty),
                CurrencyUtil.roundMoney(vat),
                CurrencyUtil.roundMoney(totalTax),
                CurrencyUtil.roundMoney(finalVehicleCost),
                customsRate,
                palRate,
                exciseRate,
                vatRate
        );
    }

    private BigDecimal percentageOf(BigDecimal amount, BigDecimal percentage) {
        return amount.multiply(percentage).divide(ONE_HUNDRED, 8, RoundingMode.HALF_UP);
    }

    private BigDecimal getCustomsRate(String fuelType) {
        return switch (fuelType) {
            case "Electric" -> BigDecimal.valueOf(10);
            case "Hybrid" -> BigDecimal.valueOf(12);
            default -> BigDecimal.valueOf(15);
        };
    }

    private BigDecimal getPalRate(String fuelType) {
        return switch (fuelType) {
            case "Electric" -> BigDecimal.valueOf(5);
            case "Hybrid" -> BigDecimal.valueOf(7.5);
            default -> BigDecimal.valueOf(10);
        };
    }

    private BigDecimal getExciseRate(String fuelType, String vehicleType, int engineCapacity) {
        BigDecimal baseRate = switch (fuelType) {
            case "Petrol" -> engineCapacity <= 1000 ? BigDecimal.valueOf(25)
                    : engineCapacity <= 1500 ? BigDecimal.valueOf(35)
                    : engineCapacity <= 2000 ? BigDecimal.valueOf(45)
                    : BigDecimal.valueOf(60);
            case "Diesel" -> engineCapacity <= 1500 ? BigDecimal.valueOf(30)
                    : engineCapacity <= 2500 ? BigDecimal.valueOf(50)
                    : BigDecimal.valueOf(70);
            case "Hybrid" -> engineCapacity <= 1500 ? BigDecimal.valueOf(15)
                    : engineCapacity <= 2000 ? BigDecimal.valueOf(20)
                    : BigDecimal.valueOf(30);
            case "Electric" -> engineCapacity <= 1000 ? BigDecimal.valueOf(5)
                    : engineCapacity <= 2000 ? BigDecimal.valueOf(10)
                    : BigDecimal.valueOf(15);
            default -> BigDecimal.valueOf(35);
        };

        BigDecimal adjustment = switch (vehicleType) {
            case "SUV" -> BigDecimal.valueOf(10);
            case "Van" -> BigDecimal.valueOf(-5);
            default -> BigDecimal.ZERO;
        };

        BigDecimal finalRate = baseRate.add(adjustment);
        if (finalRate.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return finalRate;
    }
}
